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
import com.smallchill.system.treasure.meta.intercept.ChannelAddValidator;
import com.smallchill.system.treasure.meta.intercept.ChannelEditValidator;
import com.smallchill.system.treasure.meta.intercept.PaymentChannelValidator;
import com.smallchill.system.treasure.model.Channel;
import com.smallchill.system.treasure.model.PaymentChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/paymentChannel")
public class ClientTypePaymentChannelController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private ExchangeReviewService service;
    private static String BASE_PATH = "/modules/platform/paymentChannel/";
    private static String CODE = "paymentChannel";
    private static String LIST_SOURCE = "payment_channel.all_list";
    private static String PREFIX = "paymentChannel";

    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"paymentChannel.html";
    }

    /**
     *
     * 获取充值列表
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
        return BASE_PATH+"paymentChannel_add.html";
    }

    /**
    进入修改界面
     */
    @RequestMapping(KEY_EDIT + "/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Map paymentChannel = commonService.getInfoByOne("payment_channel.find_one", CMap.init().set("id", id));
        System.out.println(paymentChannel);
        mm.put("paymentChannel", paymentChannel);
        mm.put("code", CODE);
        return BASE_PATH + "paymentChannel_edit.html";
    }
    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int temp = Blade.create(PaymentChannel.class).deleteByIds(ids);
        if (temp > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @Json
    @Before(PaymentChannelValidator.class)
    @RequestMapping(KEY_UPDATE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        PaymentChannel channel = mapping(PREFIX, PaymentChannel.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("paymentChannel", "id", parse);
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
    @Before(PaymentChannelValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult save() {
        PaymentChannel paymentChannel = mapping(PREFIX, PaymentChannel.class);
        boolean save = Blade.create(PaymentChannel.class).save(paymentChannel);
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
        Object channel = paginateBySelf("payment_channel.find_channel");
        return json(channel);
    }
}
