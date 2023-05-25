package com.smallchill.system.treasure.utils;

import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
     * @param blade
     * @param orderNum
     * @param rechargeRecords
     * @param globalDelayQueue
     */

    public static void successRecExecuted(Blade blade, String orderNum, RechargeRecords rechargeRecords, GlobalDelayQueue globalDelayQueue) {
//        // 充值成功将状态修改为已完成
//        rechargeRecords.setOrderStatus(2);
//        // 执行存储过程

//        // 金币变动记录
//        if (rechargeRecords.getIsFirstCharge()==1){
//            RechargeExchangeCommon.AddGoldChangeRecords(rechargeRecords.getUserId(),207, rechargeRecords.getGold());
//        }else {
//            RechargeExchangeCommon.AddGoldChangeRecords(rechargeRecords.getUserId(),5, rechargeRecords.getGold());
//        }
//        // 判断是否首充
//        if (rechargeRecords.getIsFirstCharge()==1){
//            Db.update("update [QPGameUserDB].[dbo].[AccountsInfo] set IsFirstRecharge=#{IsFirstRecharge} where UserID=#{UserID}",
//                    CMap.init().set("IsFirstRecharge",1).set("UserID", rechargeRecords.getUserId()));
//        }
//        // 判断是否用户第一笔成功的充值
//        int re = Db.queryInt("select count(1) from Recharge_records where userId=#{userId} and orderStatus=2",
//                CMap.init().set("userId", rechargeRecords.getUserId()));
//        if (re<1){
//            rechargeRecords.setIsThatTay(1);
//        }
//        rechargeRecords.setEndTime(new Date());
//        blade.update(rechargeRecords);
        // 保证原子性将上面放到存储过程中执行
        int status = RechargeExchangeCommon.successRecExecute(orderNum);
        if (status==0){
            extracted(rechargeRecords);
            // 回调成功,根据超时订单号将订单从延迟列表中取消
            globalDelayQueue.cancelOrder(orderNum);
            // 向游戏服务器发送请求
            Map<String, Object> gameParam = new HashMap<>();
            gameParam.put("Userid", rechargeRecords.getUserId());
            gameParam.put("gameCoin", rechargeRecords.getGold());
            gameParam.put("gold", rechargeRecords.getTopUpAmount());
            gameParam.put("Type",0);
            gameParam.put("IsFirstRecharge",0);
            // 获取充值成功的邮件
            Map emailParam = RechargeExchangeCommon.getEmailConf(2);
            // 普通充值类型
            emailParam.put("goldType",5);
            // 判断是否首充
            if (rechargeRecords.getIsFirstCharge()==1){
                gameParam.put("IsFirstRecharge",1);
                // 邮件类型首充
                emailParam.put("goldType",207);
            }
            SendHttp.sendGame1002(gameParam);
            emailParam.put("gold",0);
            emailParam.put("toUserid", rechargeRecords.getUserId());
            SendHttp.sendEmail(emailParam);
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
    }
}
