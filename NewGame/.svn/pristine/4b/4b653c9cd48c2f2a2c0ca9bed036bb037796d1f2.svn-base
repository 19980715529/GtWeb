package com.smallchill.system.treasure.utils;

import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.GoldChangeRecord;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 充值兑换公共类
 * @classNameRechargeExchangeUtils
 * @Date 2023/3/30 15:58
 * @Version 1.0
 **/
public class RechargeExchangeCommon {
    /**
     * 查询发送成功邮件
     */
    public static Map getEmailConf(Integer id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map infoByOne = Db.selectOne("select title,content,senderId from [RYPlatformManagerDB].[dbo].[email_model] where id =#{id}", map);
        return infoByOne;
    }
    /**
     * 记录兑换发生的金币变动记录
     */
    public static void addGoldChangeRecord(ExchangeReview exchangeReview, Long preGold, Long aftGold) {
        GoldChangeRecord goldChangeRecord = new GoldChangeRecord();
        goldChangeRecord.setAmount(exchangeReview.getGold());
        goldChangeRecord.setPreAmount(preGold);
        goldChangeRecord.setAftAmount(aftGold);
        goldChangeRecord.setUser_Id(exchangeReview.getUserId());
        goldChangeRecord.setChangeType_Id(206);
        goldChangeRecord.setLogTime(new Date());
        Blade.create(GoldChangeRecord.class).save(goldChangeRecord);
    }
    /**
     * 查询用户总赢
     */
    public static BigDecimal getUserWin(Integer userId){
        CMap cMap=CMap.init().set("userId", userId);
        String user_win = Db.queryStr("select TotalWin from [QPGameUserDB].[dbo].[PlayerSocreInfo] where Userid=#{userId}", cMap);
        // 查询用户消耗的总赢    1：待支付，2：待审合，3：完成已关闭，4：已完成,5：已退回，6: 支付失败，7：退回关闭，8：待发送
        String consume = Db.queryStr("select isnull(sum(consumptionCode),0) from RYPlatformManagerDB.dbo.Exchange_review " +
                        "where userId=#{userId} and status in (1,2,3,4,6,8)", cMap);
        // 用户的最终总赢
        BigDecimal user_totalWin = new BigDecimal(String.valueOf(user_win)).subtract(new BigDecimal(consume)).setScale(0, RoundingMode.FLOOR);
        System.out.println(consume+"user_TotalWin"+user_totalWin);
        return user_totalWin;
    }

    /**
     * 使用存储过程存储金币变动记录，同时添加金币
     * @param userId 玩家id
     * @param goldType 变动类型
     * @param gold 变化金币
     */
    public static Object AddGoldChangeRecords(Integer userId,Integer goldType,Long gold){
        SQLManager dao = Blade.dao("gameuserdb");
        Object o = dao.executeOnConnection(new OnConnection<Object>() {
            @Override
            public Object call(Connection connection) {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [QPGameUserDB].[dbo].[Gold_AddPlayerGold](?,?,?)}");
                    callableStatement.setInt("userId",userId );
                    callableStatement.setInt("goldType", goldType);
                    callableStatement.setLong("gold", gold);
                    ResultSet resultSet = callableStatement.executeQuery();
                    return resultSet;
                }catch (Exception e){
                    return null;
                }
            }
        });
        return o;
    }
}
