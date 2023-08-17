package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
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
import com.smallchill.system.model.UserPack;
import com.smallchill.system.treasure.model.WalletRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/walletRecords")
public class WalletRecordsController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    private static String BASE_PATH = "/modules/platform/walletRecords/";
    private static String CODE = "walletRecords";
    private static String LIST_SOURCE = "walletRecords.all_list";
    private static String PREFIX = "walletRecords";

    @RequestMapping("/")
    public String index(ModelMap mm){
        ShiroUser user = ShiroKit.getUser();
        Integer id =(Integer) user.getId();
        // 查询包id
        Blade blade = Blade.create(UserPack.class);
        UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
        if (pack!=null){
            String clientType = pack.getClientType();
            Integer[] ids = Convert.toIntArray(clientType);
            mm.put("clientType", ids[0]);
        }else {
            mm.put("clientType", -9);
        }
        mm.put("code",CODE);
        return BASE_PATH+"walletRecords.html";
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

    @DoControllerLog(name="进入钱包管理界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"walletRecords_add.html";
    }

    /**
     进入修改界面
     */
    @RequestMapping(KEY_EDIT + "/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(WalletRecords.class);
        Object channel_list = blade.findById(id);
        mm.put("walletRecords", channel_list);
        mm.put("code", CODE);
        return BASE_PATH + "walletRecords_edit.html";
    }
    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int temp = Blade.create(WalletRecords.class).deleteByIds(ids);
        if (temp > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @Json
//    @Before(PaywalletRecordsValidator.class)
    @RequestMapping(KEY_UPDATE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        WalletRecords channel = mapping(PREFIX, WalletRecords.class);
        CMap parse = CMap.parse(channel);
        int temp = Db.update("Pay_walletRecords", "id", parse);
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
//    @Before(PaywalletRecordsValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult save() {
        WalletRecords channel = mapping(PREFIX, WalletRecords.class);
        boolean save = Blade.create(WalletRecords.class).save(channel);
        if (save){
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }
    @Json
    @RequestMapping("/query")
    public AjaxResult query(@RequestParam String startTime,@RequestParam String endTime,@RequestParam Integer clientType){
        Map<String, Object> stringObjectMap = RechargeExchangeCommon.queryWallet(startTime, endTime, clientType);
        return json(stringObjectMap);
    }
}


