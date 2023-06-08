package com.smallchill.system.treasure.utils;

import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.ThreadKit;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.model.WalletRecords;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CallBackUtils {
    // 执行存储过程
    public static void storedProcedure(Map<String,Object> map){
        SQLManager dao = Blade.dao("gameroomitemdb");
        Object o = dao.executeOnConnection(new OnConnection<Object>() {
            @Override
            public Object call(Connection connection) throws SQLException {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [QPServerInfoDB].[dbo].[RechargeRecord](?,?,?,?,?)}");
                    callableStatement.setInt("UserID",Integer.parseInt(map.get("userId").toString()));
                    callableStatement.setInt("Gold",Integer.parseInt(map.get("Gold").toString()));
                    callableStatement.setInt("GameCoin",Integer.parseInt(map.get("GameCoin").toString()));
                    int type = Integer.parseInt(map.get("type").toString());
                    callableStatement.setInt("Type",type);
//                    callableStatement.setString("OrderNum","");
                    callableStatement.setString("OrderNum",map.get("OrderNum").toString());
                    callableStatement.execute();
                    return "";
                }catch (Exception e){
//                    LOGGER.error(e.getMessage());
                    return e.getMessage();
                }
            }
        });
    }

    // 充值成功需要执行的存储过程 SharePlayerRechargeRebate
    public static Object rechargeStored(Map<String,Object> map){
        SQLManager dao = Blade.dao("gameuserdb");
        Object o = dao.executeOnConnection(new OnConnection<Object>() {
            @Override
            public Object call(Connection connection) {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [QPGameUserDB].[dbo].[SharePlayerRechargeRebate](?,?)}");
                    callableStatement.setInt("UserID", Integer.valueOf(map.get("userId").toString()));
                    callableStatement.setInt("RechargeGold", Integer.valueOf(map.get("GameCoin").toString()));
                    callableStatement.execute();
                    return "";
                }catch (Exception e){
//                    LOGGER.error(e.getMessage());
                    return e.getMessage();
                }
            }
        });
        return o;
    }
    /**
     * 充值成功后执行后需要存储过程
     * @param rechargeRecords
     */
    private static void extracted(RechargeRecords rechargeRecords) {
        // 执行存储过程
        HashMap<String, Object> stored = new HashMap<>();
        stored.put("userId", rechargeRecords.getUserId());
        stored.put("Gold", rechargeRecords.getTopUpAmount());
        stored.put("GameCoin", rechargeRecords.getGold());
        stored.put("type",0);
        stored.put("OrderNum",rechargeRecords.getOrderNumber());
        // 执行分享存储过程
        storedProcedure(stored);
        // 充值成功需要执行的存储过程，充值兑换记录
        rechargeStored(stored);
    }

    /**
     * 兑换需要执行的存储过程
     * @param review
     */
    private static void extracted(ExchangeReview review) {
        HashMap<String, Object> stored = new HashMap<>();
        stored.put("userId", review.getUserId());
        stored.put("Gold", review.getAmount());
        stored.put("GameCoin", review.getGold());
        stored.put("OrderNum",review.getOrderNumber());
        stored.put("type",1);
        storedProcedure(stored);
        // 需要向游戏服务器发送请求
        Map<String, Object> gameParam = new HashMap<>();
        gameParam.put("Userid", review.getUserId());
        gameParam.put("gameCoin", review.getGold());
        gameParam.put("gold", review.getAmount());
        gameParam.put("Type",1);
        SendHttp.sendGame1002(gameParam);
    }


    /**
     * 成功需要执行的代码
     * @param orderNum
     * @param rechargeRecords
     */
    public static void successRecExecuted(String orderNum, RechargeRecords rechargeRecords) {
        // 保证原子性将上面放到存储过程中执行
        int status = RechargeExchangeCommon.successRecExecute(orderNum);
        if (status==0){
            // 使用后线程执行
            Runnable runnable = () -> {
                extracted(rechargeRecords);
                // 回调成功,根据超时订单号将订单从延迟列表中取消
                GlobalDelayQueue.cancelOrder(orderNum);
                // 向游戏服务器发送请求
                Map<String, Object> gameParam = new HashMap<>();
                gameParam.put("Userid", rechargeRecords.getUserId());
                gameParam.put("gameCoin", rechargeRecords.getGold());
                gameParam.put("gold", rechargeRecords.getTopUpAmount());
                gameParam.put("Type", 0);
                gameParam.put("IsFirstRecharge", rechargeRecords.getIsFirstCharge());
                // 获取充值成功的邮件
                Map emailParam = RechargeExchangeCommon.getEmailConf(2);
                // 普通充值类型
                emailParam.put("goldType", 5);
                // 判断是否首充
                if (rechargeRecords.getIsFirstCharge() == 1) {
                    // 邮件类型首充
                    emailParam.put("goldType", 207);
                }
                SendHttp.sendGame1002(gameParam);
                emailParam.put("gold", 0);
                emailParam.put("toUserid", rechargeRecords.getUserId());
                SendHttp.sendEmail(emailParam);
                // 修改钱包记录
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                RechargeExchangeCommon.walletStatistics(rechargeRecords.getOrderNumber(),rechargeRecords.getPackageName(),
                        rechargeRecords.getChannel(),rechargeRecords.getChannelPid(),dateFormat.format(rechargeRecords.getCreateTime()),0);
            };
            ThreadKit.excAsync(runnable,false);
        }

    }

    /**
     * 兑换回调成功需要执行
     * @param exchangeReview
     */
    public static void successExcExecuted(ExchangeReview exchangeReview) {
        // 回调成功
        exchangeReview.setStatus(4);
        exchangeReview.setEndTime(new Date());
        // 执行存储过程
        extracted(exchangeReview);
        // 兑换成功发送邮件
        Map emailParam = RechargeExchangeCommon.getEmailConf(4);
        emailParam.put("gold", 0);
        emailParam.put("toUserid", exchangeReview.getUserId());
        emailParam.put("goldType",206);
        SendHttp.sendEmail(emailParam);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        RechargeExchangeCommon.walletStatistics(exchangeReview.getOrderNumber(),
                exchangeReview.getSourcePlatform(),exchangeReview.getChannelName(),exchangeReview.getChannelId(),
                dateFormat.format(exchangeReview.getCreateTime()),1);
    }

    /**
     * 充值回调失败执行
     */
    public static void failRecExecuted(RechargeRecords rechargeRecords){
        // 充值失败将状态修改为已关闭
        rechargeRecords.setOrderStatus(3);
        // 将订单从队列中移除
        GlobalDelayQueue.cancelOrder(rechargeRecords.getOrderNumber());
        rechargeRecords.setEndTime(new Date());
        Blade blade = Blade.create(RechargeRecords.class);
        blade.update(rechargeRecords);
        if (rechargeRecords.getIsFirstCharge()==2){
            // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
            Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=0 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
                    CMap.init().set("userId",rechargeRecords.getUserId()));
        }
    }
}
