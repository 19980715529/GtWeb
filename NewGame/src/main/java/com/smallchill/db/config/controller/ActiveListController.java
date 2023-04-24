package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.db.config.model.ActiveList;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 活动列表后台配置
 * @classNameActiveListController
 * @Date 2023/2/20 17:37
 * @Version 1.0
 **/
@Controller
@RequestMapping("/activelist")
public class ActiveListController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/activelist/";
    private static String CODE = "activelist";
    private static String LIST_SOURCE = "activelist.new_list";
    private static String PREFIX = "activelist";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入充值配置列表页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "activelist.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    /**
     * 添加
     */
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "activelist_add.html";
    }
    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        ActiveList activeList = mapping(PREFIX, ActiveList.class);
        int i = Blade.create(ActiveList.class).saveRtId(activeList);
        mm.put("code",CODE);
        if (i>0){
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }
    /**
     * 打开修改界面gameroomitem
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission({ADMINISTRATOR,ADMIN})
    public String update(@PathVariable Integer id,ModelMap mm){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map active = commonService.getInfoByOne("activelist.one_active", map);
        mm.put("active",active);
        mm.put("code",CODE);
        return BASE_PATH + "activelist_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        ActiveList activeList = mapping(PREFIX, ActiveList.class);
        boolean update = Blade.create(ActiveList.class).update(activeList);
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
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids,ModelMap mm) {
        int cnt =Blade.create(ActiveList.class).deleteByIds(ids);
        mm.put("code",CODE);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

}
