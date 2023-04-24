package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 公告接口
     */
    @Autowired
    private CommonService commonService;
    @PostMapping("/notice")
    public AjaxResult notice(){
        List<Map> infoList = commonService.getInfoList("db_announcement.all_list", null);
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
     * 或许包配置
     */
    @PostMapping("/clientConf")
    public AjaxResult clientConf(@RequestParam Integer cid){
        if (cid==null){
            return fail("包id错误");
        }
        Map map = Db.selectOne("select clientType,ratio from login.dbo.ClientPos where clientType=#{clientType}", CMap.init().set("clientType", cid));
        if (map==null){
            return fail("包id错误");
        }
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
            Db.insert("insert into QPGameRecordDB.dbo.RechRecord (userId,Money,type,createTime,clientType) values (#{userId},#{Money},#{type},#{createTime},#{clientType})",
                    map);
            return "success";
        }else {
            return "fail";
        }

    }
}
