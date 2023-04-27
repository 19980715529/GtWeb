package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.db.config.model.ActiveList;
import com.smallchill.db.config.model.EmailModel;
import com.smallchill.db.config.model.EmailRecords;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @classNameEmailModelController
 * @Date 2023/3/14 16:20
 * @Version 1.0
 **/
@Controller
@RequestMapping("/emailmodel")
public class EmailModelController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/email/emailmodel/";
    private static String CODE = "emailmodel";
    private static String LIST_SOURCE = "emailmodel.new_list";
    private static String PREFIX = "emailmodel";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入邮件记录页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "emailmodel.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object grid = paginateBySelf(LIST_SOURCE);
        return grid;
    }

    /**
     * 进入修改界面
     * @param mm
     * @return
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map email = commonService.getInfoByOne("emailmodel.one_emailmodel", map);
        mm.put("emailmodel",email);
        mm.put("code",CODE);
        return BASE_PATH+"emailmodel_edit.html";
    }
    /**
     * 进入添加界面
     */
    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"emailmodel_add.html";
    }
    /**
     *
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        EmailModel emailModel = mapping(PREFIX, EmailModel.class);
        int i = Blade.create(EmailModel.class).saveRtId(emailModel);
        mm.put("code",CODE);
        if (i>0){
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }

    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        EmailModel emailModel = mapping(PREFIX, EmailModel.class);
        boolean update = Blade.create(EmailModel.class).update(emailModel);
        mm.put("code",CODE);
        if (!update){
            return fail(UPDATE_FAIL_MSG);
        }
        CacheKit.removeAll(SYS_CACHE);
        return success(UPDATE_SUCCESS_MSG);
    }
    /**
     * 删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    public AjaxResult remove(@RequestParam String ids){
        int cnt = Db.deleteByStrIds("email_model","id",ids);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
    /**
     * 进入发送邮件界面
     */
    @RequestMapping(KEY_VIEW + "/{id}")
    @Permission(ADMINISTRATOR)
    public String view(@PathVariable Integer id, ModelMap mm) {
        Map map = new HashMap();
        map.put("id", id);
        Map email = commonService.getInfoByOne("emailmodel.one_emailmodel", map);
        mm.put("emailmodel", email);
        mm.put("code", CODE);
        return BASE_PATH + "emailmodel_view.html";
    }
    /**
     * 获取所有发件人
     */
    @Json
    @RequestMapping("/senders")
    public AjaxResult getSenders(){
        List<Map> infoList = commonService.getInfoList("emailmodel.sender_list", null);
        return json(infoList);
    }
}
