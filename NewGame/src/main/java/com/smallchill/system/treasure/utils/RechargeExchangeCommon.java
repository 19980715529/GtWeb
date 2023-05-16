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
import java.sql.*;
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
        if (user_win==null){
            user_win="0";
        }
        // 查询用户消耗的总赢    1：待支付，2：待审合，3：完成已关闭，4：已完成,5：已退回，6: 支付失败，7：退回关闭，8：待发送
        String consume = Db.queryStr("select isnull(sum(consumptionCode),0) from RYPlatformManagerDB.dbo.Exchange_review " +
                        "where userId=#{userId} and status in (1,2,3,4,6,8)", cMap);
        //
        if (consume==null){
            consume="0";
        }
        // 用户的最终总赢
        System.out.println(consume+"user_TotalWin"+user_win);
        return new BigDecimal(user_win).subtract(new BigDecimal(consume)).setScale(0, RoundingMode.FLOOR);
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
    /**
     *  @UserID int,
     * @ExchangeGold bigint,			--兑换金币
     * @TotalWinRate float				--总赢倍率
     */
    public static Integer ExchangeAmount(Integer userId,Long ExchangeGold,Integer TotalWinRate){
        SQLManager dao = Blade.dao();
        return dao.executeOnConnection(new OnConnection<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException {
                CallableStatement statement = connection.prepareCall("{? = call [RYPlatformManagerDB].[dbo].[ExchangeAmount](?,?,?)}");
                // 注册输出参数
                statement.registerOutParameter(1, Types.INTEGER);
                // 设置参数
                statement.setInt(2, userId);
                statement.setLong(3, ExchangeGold);
                statement.setInt(4, TotalWinRate);
                statement.execute();
                return statement.getInt(1);
            }
        });
    }

    /**
     * 订单记录统计
     */
    public static Map<String,Object> OrderStatistics(){
        SQLManager dao = Blade.dao();
        return dao.executeOnConnection(new OnConnection<Map<String, Object>>() {
            @Override
            public Map<String, Object> call(Connection connection) throws SQLException {
                CallableStatement statement = connection.prepareCall("{call [RYPlatformManagerDB].[dbo].[TodayOrderStatistics]}");
                ResultSet res = statement.executeQuery();
                HashMap<String, Object> map = new HashMap<>();
                while (res.next()){
                    // 当日充值人数
                    map.put("drRecUserCount",res.getLong("drRecUserCount"));
                    // 当日充值金额
                    map.put("drRecMoney",res.getLong("drRecMoney"));
                    // 新增充值人数
                    map.put("drNewRecUserCount",res.getLong("drNewRecUserCount"));
                    // 新增充值金额
                    map.put("drNewRecMoney",res.getLong("drNewRecMoney"));
                    // 当日兑换人数
                    map.put("drExcUserCount",res.getLong("drExcUserCount"));
                    // 当日兑换金额
                    map.put("drExcMoney",res.getLong("drExcMoney"));
                    // 所有充值成功人数
                    map.put("totalRecUserCount",res.getLong("totalRecUserCount"));
                    // 总充值金额
                    map.put("totalRecMoney",res.getLong("totalRecMoney"));
                    // 总充值次数
                    map.put("totalRecCount",res.getLong("totalRecCount"));
                    // 总兑换金额
                    map.put("totalExcMoney",res.getLong("totalExcMoney"));
                    // 总兑换人数
                    map.put("totalExcUserCount",res.getLong("totalExcUserCount"));
                    // 总兑换次数
                    map.put("totalExcCount",res.getLong("totalExcCount"));
                    // 当日充提差
                    map.put("drRE",res.getLong("drRE"));
                    // 总充提差
                    map.put("RE",res.getLong("RE"));
                    // 当日arpu
                    map.put("drarpu",res.getBigDecimal("drarpu"));
                    // 总arpu
                    map.put("arpu",res.getBigDecimal("arpu"));

                }
                return map;
            }
        });
    }

}
