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
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.meta.intercept.PayChannelValidator;
import com.smallchill.system.treasure.model.PayChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大渠道管理
 */
@Controller
@RequestMapping("/paychannel")
public class PayChannelController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/modules/platform/paychannel/";
    private static String CODE = "paychannel";
    private static String LIST_SOURCE = "pay_channel.all_list";
    private static String PREFIX = "paychannel";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入渠道界面界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"/paychannel.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = new Object();
        // 解析查询条件
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
    /**
    进入修改界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm){
        Map map = new HashMap();
        map.put("id",id);
        Object firstBy = Blade.create(PayChannel.class).findFirstBy("id=#{id}", map);
        mm.put("paychannel",firstBy);
        mm.put("code",CODE);
        return BASE_PATH + "paychannel_edit.html";
    }

    @DoControllerLog(name="进入渠道添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"paychannel_add.html";
    }

    /**
     * 添加保存
     */
    @Json
    @Before(PayChannelValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        PayChannel mapping = mapping(PREFIX,PayChannel.class);
        Blade blade = Blade.create(PayChannel.class);
        boolean temp = blade.save(mapping);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @Json
    @RequestMapping(KEY_UPDATE)
    @Before(PayChannelValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        PayChannel channel = mapping(PREFIX, PayChannel.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("Pay_Channel", "id", parse);
        if (temp>0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    /**
    逻辑删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        // 判断是否有关联的的数据，有不可以删除
        Integer[] Ids = Convert.toIntArray(ids);
        for (int id : Ids) {
            boolean temp = Db.isExist("select * from Pay_ChannelPool where cid=#{cid}", CMap.init().set("cid", id));
            if (temp){
                return fail("请先删除关联数据");
            }
        }
        Blade blade = Blade.create(PayChannel.class);
        boolean b = blade.updateBy("isDel=1", "id IN (#{join(ids)})", CMap.init().set("ids", Ids));
        if (b) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
    /**
     * 获取所有大渠道
     */
    @Json
    @RequestMapping("/findChannel")
    public AjaxResult findChannel(){
        List<Map> infoList = commonService.getInfoList("pay_channel.find_list", null);
        return json(infoList);
    }

}
