package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.entity.RarPay;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @classNameAnnouncementController
 * @Date 2023/3/23 15:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/info")
public class AnnouncementController extends BaseController implements ConstShiro {

    @Resource
    private RarPay rarPay;
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
    @GetMapping("/test")
    public AjaxResult test(){
//        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/applicationContext-PayConfig.xml");
//        RarPay rarPay = appContext.getBean(RarPay.class);
        LOGGER.error(JSON.toJSONString(rarPay));
        return json(rarPay);
    }
}
