package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.model.AnnouncementManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @classNameAnnouncementManagementController
 * @Date 2023/3/21 10:39
 * @Version 1.0
 **/
@Controller
@RequestMapping("/announcement")
public class AnnouncementManagementController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/announcement/";
    private static String CODE = "announcement";
    private static String LIST_SOURCE = "db_announcement.list";
    private static String PREFIX = "announcement";
    @Autowired
    private CommonService commonService;
    @DoControllerLog(name = "进入公告管理界面")
    @RequestMapping("/")
    @Permission(ADMINISTRATOR)
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"announcement.html";
    }
    /**
     * 获取公告列表
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object grid = paginateBySelf(LIST_SOURCE);
        return grid;
    }
    /**
     * 进入添加公告界面
     */
    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"announcement_add.html";
    }

    /**
     * 添加操作
     */
    @Json
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        AnnouncementManagement mapping = mapping(PREFIX, AnnouncementManagement.class);
        int temp = Blade.create(AnnouncementManagement.class).saveRtId(mapping);
        if (temp>0){
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }
    /**
     * 进入修该界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id,ModelMap mm){
        Object ann = Blade.create(AnnouncementManagement.class).findById(id);
        mm.put("announcement",ann);
        mm.put("code",CODE);
        return BASE_PATH+"announcement_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update(){
        AnnouncementManagement mapping = mapping(PREFIX, AnnouncementManagement.class);
        boolean temp = Blade.create(AnnouncementManagement.class).update(mapping);
        if (temp){
            return success(UPDATE_SUCCESS_MSG);
        }else {
            return fail(UPDATE_FAIL_MSG);
        }
    }
    /**
     * 删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    public AjaxResult remove(@RequestParam String ids){
        int i = Blade.create(AnnouncementManagement.class).deleteByIds(ids);
        if (i>0){
            return success(DEL_SUCCESS_MSG);
        }else {
            return fail(DEL_FAIL_MSG);
        }
    }
    /**
     * 查看
     */
    @RequestMapping(KEY_VIEW+"/{id}")
    public String view(@PathVariable Integer id,ModelMap mm){
        Object ann = Blade.create(AnnouncementManagement.class).findById(id);
        mm.put("announcement",ann);
        mm.put("code",CODE);
        return BASE_PATH+"announcement_view.html";
    }

}
