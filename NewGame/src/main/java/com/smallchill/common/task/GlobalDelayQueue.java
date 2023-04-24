package com.smallchill.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Description TODO
 * @classNameGlobalDelayQueue
 * @Date 2023/2/21 15:10
 * @Version 1.0
 **/
@Component()
public class GlobalDelayQueue {
    /**
     * 定义应该单列的延时队列
     */
    @Autowired
    private RechargeRecordsService rechargeRecordsService;
    @Autowired
    private CommonService commonService;
    public volatile static DelayQueue<RechargeRecords> orderQueue = new DelayQueue();
    //订单过期时间间隔定义（毫秒），这里设置5分钟
    public static final long expireTime = TimeUnit.MINUTES.toMillis(5);
    // 订单取消，数据库改变订单状态，DelayQueue容器移除该订单记录
    public void cancelOrder(String orderNum,Integer status){
        // 修改订单状态，根据订单id查询  订单状态，1：待支付，2：已完成，3：已关闭
        Map<String, Object> map = new HashMap<>();
        map.put("orderNum",orderNum);
        Map infoByOne = commonService.getInfoByOne("recharge.query_orderNum", map);
        RechargeRecords rechargeRecords1 = JSONObject.parseObject(JSONObject.toJSONString(infoByOne), RechargeRecords.class);
        rechargeRecords1.setEndTime(new Date());
        // 设置取消支付
        rechargeRecords1.setOrderStatus(status);
        // 跟新到数据库
        boolean temp = rechargeRecordsService.update(rechargeRecords1);
        if (temp){
            CacheKit.removeAll("SYS_CACHE");
            Iterator<RechargeRecords> iterator = orderQueue.iterator();
            while (iterator.hasNext()){
                RechargeRecords rechargeRecords = iterator.next();
                if (rechargeRecords.getOrderNumber().equals(orderNum)){
                    // 移除超时订单
                    orderQueue.remove(rechargeRecords);
                }
            }
        }else {
            throw new RuntimeException("执行失败");
        }

    }
    public void cancelOrder(String orderNum){
        // 修改订单状态，根据订单id查询  订单状态，1：待支付，2：已完成，3：已失败
        Iterator<RechargeRecords> iterator = orderQueue.iterator();
        while (iterator.hasNext()){
            RechargeRecords rechargeRecords = iterator.next();
            if (rechargeRecords.getOrderNumber().equals(orderNum)){
                // 移除超时订单
                orderQueue.remove(rechargeRecords);
            }
        }
    }
    // 往队列中添加订单
    private static void addOrder(RechargeRecords rechargeRecords){
        orderQueue.put(rechargeRecords);
    }
    /**
     * 服务器启动时，自动加载待支付订单 @PostConstruct 再项目启动的时候就执行，但是方法里面有未加载的，使用会导致报错
     * 每天凌晨两点执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void initOrderStatus() {
        // 查询未支付订单
        List<Map> infoList = commonService.getInfoList("recharge.get_unPay_recharge_list",null);
        if (infoList.size()>0){
            // 遍历存储到队列中
            for (Map order:infoList){
                RechargeRecords rechargeRecords = JSONObject.parseObject(JSONObject.toJSONString(order), RechargeRecords.class);
                orderQueue.add(rechargeRecords);
            }
            // 启动线程执行超时订单
            try {
                while (true){
                    if (orderQueue.size()>0){
                        // 读取超时订单
                        RechargeRecords records = orderQueue.take();
                        records.setEndTime(new Date());
                        cancelOrder(records.getOrderNumber(),3);
                    }
                }
            }catch (InterruptedException e) {
                return;
            }
        }
    }

    // 判断玩家5分钟内是否重复提交订单
    public static RechargeRecords repeatSubmit(Integer uid){
        if (orderQueue.size()>0){
            Iterator<RechargeRecords> iterator = orderQueue.iterator();
            while (iterator.hasNext()){
                RechargeRecords records = iterator.next();
                if (records.getUserId().equals(uid)){
                    return records;
                }
            }
        }
        return null;
    }

    /**
     * 根据订单号查询订单
     */
    public static RechargeRecords queryRecharge(String orderNum){
        if (orderQueue.size()>0){
            Iterator<RechargeRecords> iterator = orderQueue.iterator();
            while (iterator.hasNext()){
                RechargeRecords records = iterator.next();
                if (records.getOrderNumber().equals(orderNum)){
                    return records;
                }
            }
        }
        return null;
    }

    /**
     * 每秒中执行一次
     */
    @Scheduled(cron = "* * * * * ? ")
    public void processOrder() {
        try {
            while (orderQueue.size()>0){
                // 读取超时订单
                RechargeRecords records = orderQueue.take();
                records.setEndTime(new Date());
                // 取消超时订单
                cancelOrder(records.getOrderNumber(),3);
            }
        }catch (Exception ignored){
        }

    }
}
