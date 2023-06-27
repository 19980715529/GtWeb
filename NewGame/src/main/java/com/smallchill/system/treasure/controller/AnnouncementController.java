package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Redis;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.redis.IJedis;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.model.ChannelVo;
import com.smallchill.system.treasure.model.RechargeGear;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @classNameAnnouncementController
 * @Date 2023/3/23 15:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/info")
public class AnnouncementController extends BaseController implements ConstShiro {

    /**
     * 公告接口
     */
    @Resource
    private CommonService commonService;
    @PostMapping("/notice")
    public AjaxResult notice(@RequestParam(value = "cid",required = false,defaultValue = "6") Integer cid){
        List<Map> infoList = commonService.getInfoList("db_announcement.all_list", CMap.init().set("clientType",cid));
        return json(infoList);
    }
    /**
     * 发件人列表接口
     */
    @PostMapping("/senders")
    public AjaxResult senders(){
        List<Map> infoList = commonService.getInfoList("emailmodel.sender_list", null);
        return json(infoList);
    }
    /**
     * 客服列表
     */
    @PostMapping("/customer")
    public AjaxResult customer(){
        List<Map> list = Db.selectList("select id,name,resources from CustomerService");
        return json(list);
    }
    /**
     * 获取包配置
     */
    @PostMapping("/clientConf")
    public AjaxResult clientConf(@RequestParam Integer cid){
        if (cid==null){
            return fail("包id错误");
        }
        Map map = Db.selectOne("select clientType,ratio,isLog from login.dbo.ClientPos where clientType=#{clientType}",
                CMap.init().set("clientType", cid));
        if (map==null){
            return fail("包id错误");
        }
        // 获取包中游戏  blade_dict 56
        List<Map> games = Db.selectList("select id,sort,state,gameId,name,icon,(select name from blade_dict where a.type=id) as type " +
                "from [RYPlatformManagerDB].[dbo].[game_conf] as a where isOpen=1 and clientType=#{clientType} order by sort", CMap.init().set("clientType", cid));
        map.put("games",games);
        return json(map);
    }
    /**
     * 用于回调数据统计  RechRecord
     */
    @GetMapping("/apiCallback")
    public String apiCallback(@RequestParam Map<Object,Object> map){
        if (map == null){
            return "fail";
        }
        if (map.get("userId") !=null && StrKit.notBlank(map.get("userId").toString())){
            map.put("createTime",new Date());
            Db.insert("insert into QPGameRecordDB.dbo.RechRecord (userId,Money,type,createTime,clientType) " +
                    "values (#{userId},#{Money},#{type},#{createTime},#{clientType})", map);
            return "success";
        }else {
            return "fail";
        }

    }

    /**
     * 充值兑换渠道获取
     * @param UserId
     * @param type
     * @param cid
     * @return
     */
    @PostMapping("/all_channel")
    public AjaxResult getAllChannel(@RequestParam Integer UserId,@RequestParam Integer type,@RequestParam Integer cid){
        //判断用户是否存在
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserId",UserId));
        if (user==null){
            // 用户不存在
            return fail("Userid does not exist");
        }
        ArrayList<ChannelVo> channelVos = new ArrayList<>();
        // 获取大渠道 Pay_Channel
        if (type==1){
            // 查询用户用户总赢
            BigDecimal totalWin = RechargeExchangeCommon.getUserWin(Integer.valueOf(UserId));
            String amount = RechargeExchangeCommon.getGold(Integer.valueOf(UserId));
            if ("".equals(amount)){
                return json(null,"用户不存在",1);
            }
            List<Map> payChannel = Db.selectList("select id,cname channel_name,exchangeGear,isRecharge,isExchange from Pay_Channel order by sort");
            for (Map map:payChannel) {
                ChannelVo channelVo = JSON.parseObject(JSON.toJSONString(map), ChannelVo.class);
                // 兑换时需要兑换比率
                String exchangeGear = map.get("exchangeGear").toString().trim();
                String[] split = exchangeGear.split(",");
                List<Integer> gear = Arrays.stream(split)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                channelVo.setExchangeGear(gear);
                channelVos.add(channelVo);
                // 获取最大参数
                CMap param =CMap.init().set("clientType", cid).set("type", type).set("cid", map.get("id"));
                Map max_param = commonService.getInfoByOne("channel_list.param_max", param);
                if (max_param==null){
                    continue;
                }
                // 获取其中一条
                Map ch = commonService.getInfoByOne("channel_list.param_one", param);
                if (ch==null){
                    continue;
                }
                max_param.put("name",map.get("channel_name"));
                max_param.put("isLabel",ch.get("isLabel"));
                max_param.put("unit",ch.get("unit"));
                max_param.put("mcName",ch.get("mcName"));
                // 获取金币
                BigDecimal gpt = new BigDecimal(max_param.get("goldProportion").toString());
                // 根据用户总赢计算可以兑换的钱  用户总赢/10000/1.5
                BigDecimal fee1 = totalWin.divide(gpt, RoundingMode.DOWN).divide(new BigDecimal(max_param.get("winConf").toString()), RoundingMode.DOWN);
                // 根据用户金币计算能够兑换的钱  用户金币/金币倍率
                BigDecimal am = new BigDecimal(amount).divide(gpt,RoundingMode.DOWN);
                int fee = Math.min(fee1.intValue(), am.intValue());
                if (fee<0.01){
                    fee = 0;
                }
                max_param.put("money",fee);
                channelVo.getTypes().add(max_param);
            }
        }else {
            List<Map> payChannel = Db.selectList("select id,cname channel_name,isRecharge,isExchange from Pay_Channel order by sort");
            for (Map map:payChannel) {
                ChannelVo channelVo = JSON.parseObject(JSON.toJSONString(map), ChannelVo.class);
                // 根据订单类型，包id进行查询
                List<Map> infoList = commonService.getInfoList("channel_list.new_list", CMap.init().set("clientType",cid).set("type",type).set("cid",map.get("id")));
                channelVo.setTypes(infoList);
                channelVos.add(channelVo);
            }
        }
        return json(channelVos);
    }

    // 获取充值挡位
    @GetMapping("/recharge/gear")
    public AjaxResult getGear(){
        List<Map> payChannel = Db.selectList("select * from Pay_RechargeGear order by sort");
        return json(payChannel);
    }

    // 获取转盘配置
    @GetMapping("/turntable")
    public AjaxResult turntable(){
        List<Map> config = Db.selectList("select turntable_index,award,type from [QPServerInfoDB].[dbo].[Turntable_TurntableConfig]");
        List<Map> BaseConfig = Db.selectList("select * from [QPServerInfoDB].[dbo].[Turntable_TurntableBaseConfig]");
        HashMap<String, Object> map = new HashMap<>();
        map.put("config",config);
        map.put("BaseConfig",BaseConfig);
        return json(map);
    }

    /**
     * 签到奖励配置
     */
    @GetMapping("/checkConfig")
    public AjaxResult check(){
        List<Map> maps = Db.selectList("select Day,Award from [QPServerInfoDB].[dbo].[CheckIn_CheckInAward] where AwardType=1");
        return json(maps);
    }

}
