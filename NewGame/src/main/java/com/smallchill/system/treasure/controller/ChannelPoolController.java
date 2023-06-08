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
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.meta.intercept.ChannelPoolValidator;
import com.smallchill.system.treasure.model.PayChannelPool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/channelpool")
public class ChannelPoolController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/modules/platform/channelpool/";
    private static String CODE = "channelpool";
    private static String LIST_SOURCE = "channel_pool.all_list";
    private static String PREFIX = "channelpool";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入商户渠道池关联界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"channelpool.html";
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
        Object firstBy = Blade.create(PayChannelPool.class).findFirstBy("id=#{id}", map);
        mm.put("channelpool",firstBy);
        mm.put("code",CODE);
        return BASE_PATH + "channelpool_edit.html";
    }

    @DoControllerLog(name="进入渠道添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"channelpool_add.html";
    }

    /**
     * 添加保存
     */
    @Json
    @Before(ChannelPoolValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        PayChannelPool mapping = mapping(PREFIX, PayChannelPool.class);
        Blade blade = Blade.create(PayChannelPool.class);
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
    @Before(ChannelPoolValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        PayChannelPool channel = mapping(PREFIX, PayChannelPool.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("Pay_ChannelPool", "id", parse);
        if (temp>0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        Blade blade = Blade.create(PayChannelPool.class);
        int cnt = blade.deleteByIds(ids);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
    /**
     * 获取所有商户名
     */
    @Json
    @RequestMapping("/findMcName")
    public AjaxResult findMcName(){
        List<Map> infoList = commonService.getInfoList("channel_pool.mc_list", null);
        return json(infoList);
    }
    /**
     * 获取所有商户名
     */
    @Json
    @RequestMapping("/findMcName1")
    public AjaxResult findMcNames(){
        List<Map> infoList = commonService.getInfoList("channel_pool.mc_list1", null);
        return json(infoList);
    }
    /**
     * 根据条件获取存在的商户名 query_mc_list
     */
    @Json
    @RequestMapping("/queryMcName")
    public AjaxResult queryMcName(@RequestParam Integer clientType,@RequestParam String chName){
        List<Map> infoList = commonService.getInfoList("channel_list.query_min_list",
                CMap.init().set("clientType",clientType).set("chName",chName));
        return json(infoList);
    }
}
