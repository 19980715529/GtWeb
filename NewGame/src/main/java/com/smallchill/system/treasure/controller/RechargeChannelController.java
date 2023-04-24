package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.ChannelService;
import com.smallchill.system.treasure.meta.intercept.ChannelAddValidator;
import com.smallchill.system.treasure.meta.intercept.ChannelEditValidator;
import com.smallchill.system.treasure.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 充值渠道接口
 */
@Controller
@RequestMapping("/rechargechannel")
public class RechargeChannelController extends BaseController implements ConstShiro {
    @Autowired
    private CommonService commonService;
    @Autowired
    private ChannelService service;
    private static String BASE_PATH = "/system/rechargechannel/";
    private static String CODE = "rechargechannel";
    private static String LIST_SOURCE = "recharge_channel.all_list";
    private static String PREFIX = "channel"; // 与前端请求体名字匹配
    @DoControllerLog(name="进入添加支付渠道管理界面")
    @RequestMapping("/")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return "/modules/platform/plog/platform_recharge_channel.html";
    }

    /*
    获取充值列表
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
    /*
    添加/system/rechargechannel/rechargechannel_add.html
    rechargechannel
     */
    @DoControllerLog(name="进入充值渠道的添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"rechargechannel_add.html";
    }

    @DoControllerLog(name="进入充值渠道的添加商户界面")
    @RequestMapping(KEY_ADD+"/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"rechargechannel_max_add.html";
    }
    /*
    进入修改界面
     */
    @RequestMapping(KEY_EDIT + "/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Map map = new HashMap();
        map.put("id", id);
        Map channel = commonService.getInfoByOne("recharge_channel.by_id_one_channel", map);
        mm.put("channel", channel);
        mm.put("code", CODE);
        return BASE_PATH + "rechargechannel_edit.html";
    }
    /*
    删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int cnt = service.deleteByIds(ids);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @Json
    @Before(ChannelEditValidator.class)
    @RequestMapping(KEY_UPDATE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        Channel channel = mapping(PREFIX, Channel.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("Channel", "id", parse);
        if (temp>0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
    // 添加保存
    @Json
    @RequestMapping(KEY_SAVE)
    @Before(ChannelAddValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult save() {
        Channel channel = mapping(PREFIX, Channel.class);
        // 判断pid是否等于空
        int temp =0;
        if(channel.getPid() == 0){
            // 为空代表新商户
            channel.setIsOpen(1);
            channel.setPid(0);
            CMap parse = CMap.parse(channel);
            temp = Db.save("Channel", "id", parse);
        }else {
            // 查询排序最大值
            Map sortMap = commonService.getInfoByOne("recharge_channel.get_max_sort", channel);
            Integer sorts =null;
            if (sortMap==null){
                sorts=0;
            }
            sorts = Integer.valueOf(sortMap.get("sorts").toString())+1;
            channel.setSort(sorts);
            channel.setName(channel.getChannelName()+sorts);
            // 查询商户名
            Map map = Db.selectOne("select mcName from Channel where id =#{pid}", channel);
            channel.setIsOpen(1);
            channel.setMcName(map.get("mcName").toString());
            CMap parse = CMap.parse(channel);
            temp = Db.save("Channel", "id", parse);
        }
        if (temp> 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }
    /**
     *查看
     */
    @RequestMapping(KEY_VIEW + "/{id}")
    @Permission(ADMINISTRATOR)
    public String view(@PathVariable Integer id, ModelMap mm) {
        Map map = new HashMap();
        map.put("id", id);
        Map recharge = commonService.getInfoByOne("recharge_channel.by_id_one_channel", map);
        mm.put("channel", recharge);
        mm.put("code", CODE);
        return BASE_PATH + "rechargechannel_view.html";
    }
    /**
     * 获取父渠道
     */
    @Json
    @RequestMapping("/getChannel")
    public AjaxResult getChannel(){
        Object channel = paginateBySelf("recharge_channel.all_parent_list");
        return json(channel);
    }
    /**
     * 获取所有兑换小渠道数据
     */
    @Json
    @RequestMapping("/getExchangeChannelMin")
    public AjaxResult getExchangeChannelMin(){
        Object channel = paginateBySelf("recharge_channel.all_exchange_channel_min");
        return json(channel);
    }
}
