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
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.treasure.meta.intercept.PayChannelListValidator;
import com.smallchill.system.treasure.model.PayChannelList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/channellist")
public class PayChannelListController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    private static String BASE_PATH = "/modules/platform/channellist/";
    private static String CODE = "channellist";
    private static String LIST_SOURCE = "channel_list.all_list";
    private static String PREFIX = "channellist";

    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"channellist.html";
    }

    /**
     *
     * 获取渠道分包充值列表
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        return paginateBySelf(LIST_SOURCE);
    }

    @DoControllerLog(name="进入分包充值渠道的添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"channellist_add.html";
    }

    /**
    进入修改界面
     */
    @RequestMapping(KEY_EDIT + "/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(PayChannelList.class);
        Object channel_list = blade.findById(id);
        mm.put("channellist", channel_list);
        mm.put("code", CODE);
        return BASE_PATH + "channellist_edit.html";
    }
    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int temp = Blade.create(PayChannelList.class).deleteByIds(ids);
        if (temp > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @Json
    @Before(PayChannelListValidator.class)
    @RequestMapping(KEY_UPDATE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        PayChannelList channel = mapping(PREFIX, PayChannelList.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("Pay_channelList", "id", parse);
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
    @Before(PayChannelListValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult save() {
        PayChannelList channel = mapping(PREFIX, PayChannelList.class);
        boolean save = Blade.create(PayChannelList.class).save(channel);
        if (save){
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
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
     * 获取所有渠道id和提示
     */
    @Json
    @RequestMapping("/getChannel")
    public AjaxResult getChannel(){
        Object channel = commonService.getInfoList("channel_list.find_channel",null);
        return json(channel);
    }
}
