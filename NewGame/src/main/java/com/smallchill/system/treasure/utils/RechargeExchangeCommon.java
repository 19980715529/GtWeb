package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.model.User;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.GoldChangeRecord;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.model.WalletRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * @Description 充值兑换公共类
 * @classNameRechargeExchangeUtils
 * @Date 2023/3/30 15:58
 * @Version 1.0
 **/
public class RechargeExchangeCommon {
    private static final Logger LOGGER = LogManager.getLogger(RechargeExchangeCommon.class);
    /**
     * 获取用户金币
     * @param userId
     * @return
     */
    public static String getGold(Integer userId) {
        String amount = Db.queryStr("SELECT Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] where User_Id = #{UserId} and Prop_Id = 1",
                CMap.init().set("UserId",userId));
        if (amount == null){
            return "";
        }
        return amount;
    }
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
        // 用户的最终总赢 2680000
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
     * @UserID int,
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
                    // 当日充值次数
                    map.put("drTotalRecCount",res.getLong("drTotalRecCount"));
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


    /**
     * 获取随机用户名
     */
    public static String RandomUsername(){
        List<String> list = Arrays.asList("Primeveire","Jalynn","Philippine","Amour","Hari","Daan","Mary","Marygrace","Charmaine","Yue",
                "Vicenzo","Lorinda","Lyydia","Einan","Josefina","Abiodun","Filimena","Gull","Viv","Rolfe","Wieland","Jorine","Sirena",
                "Mose","Khariton","Manaia");
        Random random = new Random();
        int size = list.size();
        int randomIndex = random.nextInt(size);
        return list.get(randomIndex);
    }

    /**
     * 获取随机邮箱
     * @return
     */
    public static String RandomEmail(){
        List<String> list = Arrays.asList("Kalim1997@163.com","Russell1996@139.com","Devonte2003@Yeah.com","Recto2006@139.com","Ulises2014@bing.com",
                "Jude2017@Outlook.com","Balbutin2019@189.com","Alarico1990@Yeah.com","Tyson1992@263.com","Lavares1998@Hotmail.com","Elmer2000@163.com",
                "Meneses2001@gmail.com","Sanqui2005@263.com");
        Random random = new Random();
        int size = list.size();
        int randomIndex = random.nextInt(size);
        return list.get(randomIndex);
    }
    /**
     * 充值回调成功需要执行的代码
     */
    public static Integer successRecExecute(String orderNum){
        SQLManager dao = Blade.dao();
        return dao.executeOnConnection(new OnConnection<Integer>() {
            @Override
            public Integer call(Connection connection){
                try {
                    CallableStatement statement = connection.prepareCall("{? = call [RYPlatformManagerDB].[dbo].[SuccessRecExecute](?)}");
                    // 注册输出参数
                    statement.registerOutParameter(1, Types.INTEGER);
                    // 设置参数
                    statement.setString(2, orderNum);
                    statement.execute();
                    return statement.getInt(1);
                }catch (Exception e){
                    return 3;
                }
            }
        });
    }

    /**
     * 充值工具
     */

    public static Map<String,Object> recharge(RechargeRecords rechargeRecords, JSONObject resultMap, Map user, CommonService commonService,Integer channelId){
        Map<String, Object> reMap = new HashMap<>();
        if (user==null){
            reMap.put("code",1);
            reMap.put("msg","104010");
            return reMap;
        }
        String phone = user.get("bindPhone").toString();
        if (phone!=null){
            rechargeRecords.setPhone(phone);
        }
        rechargeRecords.setPackageName(Integer.valueOf(user.get("ClientType").toString()));
        Map channel = commonService.getInfoByOne("channel_list.recharge_one",
                CMap.init().set("id",channelId));
        // 将渠道数据存储到订单里面
        rechargeRecords.setChannel_type(String.valueOf(channel.get("name")));
        // 大渠道
        rechargeRecords.setChannel(String.valueOf(channel.get("channelName")));
        BigDecimal fee= rechargeRecords.getTopUpAmount();
        // 判断是否首充
        if(rechargeRecords.getIsFirstCharge()==1){
            // 判断是否已经首充过  QPGameUserDB   AccountsInfo
            Integer IsFirstRecharge = Db.queryInt("select IsFirstRecharge from [QPGameUserDB].[dbo].[AccountsInfo] where UserID = #{UserId}",
                    CMap.init().set("UserId", rechargeRecords.getUserId()));
            if (IsFirstRecharge==1){
                reMap.put("code",1);
                reMap.put("msg","105010");
                return reMap;
            }
            // 获取首充配置
            Map first = commonService.getInfoByOne("recharge_channel.first_list", null);
            BigDecimal gold = new BigDecimal(String.valueOf(first.get("gold")));
            BigDecimal gold_give = new BigDecimal(String.valueOf(first.get("give_gold")));
            rechargeRecords.setGold(gold.add(gold_give).longValue());
            // 修改
        }else if (rechargeRecords.getIsFirstCharge()==0){
            // 普通充值
            BigDecimal max = new BigDecimal(String.valueOf(channel.get("max")));
            BigDecimal min = new BigDecimal(String.valueOf(channel.get("min")));
            // 判断充值的钱是否满足渠道条件
            if (fee.intValue() <min.intValue() || fee.intValue()>max.intValue()){
                reMap.put("code",1);
                reMap.put("msg","104012");
                return reMap;
            }
            // 获取渠道外赠送比例
            BigDecimal give = new BigDecimal(String.valueOf(channel.get("give")));
            // 渠道倍率goldProportion
            BigDecimal gpr = new BigDecimal(String.valueOf(channel.get("goldProportion")));
            //  充值的钱*金币倍率*赠送比例
            BigDecimal give_gold = fee.multiply(gpr).multiply(give);
            //  充值的钱*10000*金币倍率+渠道外赠送
            BigDecimal get_Gold = fee.multiply(gpr).add(give_gold).setScale(0,RoundingMode.HALF_UP);
            // 将充值金币存储到订单里面
            rechargeRecords.setGold(get_Gold.longValue());
        }else if (rechargeRecords.getIsFirstCharge()==2){
            // 随机充值，查询随机充值配置
            Map map = Db.selectOne("select * from [QPGameUserDB].[dbo].[PlayerActiveInfo_RandomRecharge] where UserId=#{userId}",
                    CMap.init().set("userId",rechargeRecords.getUserId()));
            // 计算随机充值金额是否正确
            BigDecimal money = new BigDecimal(map.get("RechargeGold").toString());
            if (money.intValue()!=rechargeRecords.getTopUpAmount().intValue()){
                reMap.put("code",1);
                reMap.put("msg","105011");
                return reMap;
            }
            // 渠道倍率goldProportion
            BigDecimal gpr = new BigDecimal(String.valueOf(channel.get("goldProportion")));
            // 奖励百分比
            BigDecimal percentId = new BigDecimal(map.get("PercentId").toString()).divide(new BigDecimal("100"), 2, RoundingMode.DOWN);
            // 计算获得金币
            BigDecimal get_gold = money.multiply(gpr).multiply(percentId).setScale(0, RoundingMode.DOWN);
            rechargeRecords.setGold(get_gold.longValue());
        }else {
           reMap.put("code",1);
           reMap.put("msg","105011");
           return reMap;
        }
        // 生成订单号
        String orderNo = Utils.getOrderNum(rechargeRecords.getUserId());
        // 设置订单号
        rechargeRecords.setOrderNumber(orderNo);
        // 设置时间
        rechargeRecords.setCreateTime(new Date());
        int pid = Integer.parseInt(channel.get("pid").toString());
        rechargeRecords.setChannelPid(pid);
        // 钱包统计
        RechargeExchangeCommon.rec(rechargeRecords,channel);
        // 设置响应结果
        resultMap.put("Userid",rechargeRecords.getUserId());
        resultMap.put("gameCoin",rechargeRecords.getGold()); //  充值的游戏币
        resultMap.put("gold",fee.setScale(2,RoundingMode.HALF_UP)); //  充值的钱
        resultMap.put("type",0);// 充值类型
        rechargeRecords.setIsThatTay(0);
        reMap.put("code",0);
        reMap.put("channel",channel);
        return reMap;
    }

    /**
     * 充值时执行
     */
    public static void rec(RechargeRecords rechargeRecords, Map channel){
        Blade blade = Blade.create(WalletRecords.class);
        // 生成钱包统计记录
        HashMap<String, Object> map = new HashMap<>();
        // 包id
        map.put("clientType", rechargeRecords.getPackageName());
        // 大渠道名称
        map.put("channelName",rechargeRecords.getChannel());
        // 小渠道名称
        map.put("mcId",rechargeRecords.getChannelPid());
        // 今日日期
        map.put("createTime", LocalDate.now());
        WalletRecords walletRecords = blade.findFirstBy("clientType=#{clientType} and channelName=#{channelName} and mcId=#{mcId} and createTime=#{createTime}", map);
        if (walletRecords!=null){
            // 存在就进行修改 这里修改代收次数
            Integer collectNum = walletRecords.getCollectNum();
            walletRecords.setCollectNum(collectNum+1);
            blade.update(walletRecords);
        }else {
            // 不存在就添加
            WalletRecords wallet = new WalletRecords();
            wallet.setClientType(rechargeRecords.getPackageName());
            wallet.setChannelName(rechargeRecords.getChannel());
            wallet.setMcId(rechargeRecords.getChannelPid());
            wallet.setCollectNum(1);
            wallet.setCollectRate(new BigDecimal(channel.get("collectRate").toString()));
            wallet.setPayFee(new BigDecimal(channel.get("payFee").toString()));
            wallet.setPaymentRate(new BigDecimal(channel.get("paymentRate").toString()));
            blade.save(wallet);
        }
    }

    /**
     * 发起兑换时执行
     */
    public static void exc(ExchangeReview exchangeReview, Map channel){
        Blade blade = Blade.create(WalletRecords.class);
        // 生成钱包统计记录
        HashMap<String, Object> map = new HashMap<>();
        // 包id
        map.put("clientType", exchangeReview.getSourcePlatform());
        // 大渠道名称
        map.put("channelName",exchangeReview.getChannelName());
        // 小渠道名称
        map.put("mcId",exchangeReview.getChannelId());
        // 今日日期
        map.put("createTime", LocalDate.now());
        WalletRecords walletRecords = blade.findFirstBy("clientType=#{clientType} and channelName=#{channelName} and mcId=#{mcId} and createTime=#{createTime}", map);
        if (walletRecords==null){
            // 不存在就添加
            WalletRecords wallet = new WalletRecords();
            wallet.setClientType(exchangeReview.getSourcePlatform());
            wallet.setChannelName(exchangeReview.getChannelName());
            wallet.setMcId(exchangeReview.getChannelId());
            wallet.setCollectNum(0);
            wallet.setCollectRate(new BigDecimal(channel.get("collectRate").toString()));
            wallet.setPayFee(new BigDecimal(channel.get("payFee").toString()));
            wallet.setPaymentRate(new BigDecimal(channel.get("paymentRate").toString()));
            blade.save(wallet);
        }
    }
    /**
     * 钱包统计
     * @param orderNum
     * @param clientType
     * @param channelName
     * @param mcId
     * @param createTime
     * @param type
     */
    public static void walletStatistics(String orderNum,Integer clientType,String channelName,Integer mcId,String createTime,Integer type){
        LOGGER.error("orderNum="+orderNum+",clientType="+clientType+",channelName="+channelName+",mcId="+mcId+",createTime="+createTime+",type="+type);
        SQLManager dao = Blade.dao();
        dao.executeOnConnection(new OnConnection<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [RYPlatformManagerDB].[dbo].[WalletStatistics](?,?,?,?,?,?)}");
                    callableStatement.setString(1,orderNum);
                    callableStatement.setInt(2,clientType);
                    callableStatement.setString(3,channelName);
                    callableStatement.setInt(4,mcId);
                    callableStatement.setInt(5,type);
                    callableStatement.setString(6,createTime);
                    callableStatement.execute();
                    return 1;
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                    return 1;
                }
            }
        });
    }

    public static Map<String,Object> queryWallet(String startTime,String endTime,Integer clientType){
        SQLManager dao = Blade.dao();
        return dao.executeOnConnection(new OnConnection<Map<String,Object>>() {
            @Override
            public Map<String, Object> call(Connection connection) throws SQLException {
                HashMap<String, Object> map = new HashMap<>();
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [RYPlatformManagerDB].[dbo].[RealTimeWalletStatistics](?,?,?)}");
                    callableStatement.setString(1,startTime);
                    callableStatement.setString(2,endTime);
                    callableStatement.setInt(3,clientType);
                    ResultSet resultSet = callableStatement.executeQuery();
                    while (resultSet.next()){
                        // 充值金额
                        map.put("recMoney",resultSet.getBigDecimal("recMoney"));
                        // 兑换金额
                        map.put("excMoney",resultSet.getBigDecimal("excMoney"));
                        // 充提差
                        map.put("reMoney",resultSet.getBigDecimal("reMoney"));
                        // 代收费用
                        map.put("collectAmounts",resultSet.getBigDecimal("collectAmounts"));
                        // 代付手续费
                        map.put("collectFees",resultSet.getBigDecimal("collectFees"));
                        // 代付金额
                        map.put("paymentAmount",resultSet.getBigDecimal("paymentAmount"));
                        // 代付手续费
                        map.put("paymentFees",resultSet.getBigDecimal("paymentFees"));
                        // 中手续费
                        map.put("totalFees",resultSet.getBigDecimal("totalFees"));
                        // 净利润
                        map.put("netProfits",resultSet.getBigDecimal("netProfits"));
                    }
                    return map;
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                    return map;
                }
            }
        });
    }

}
