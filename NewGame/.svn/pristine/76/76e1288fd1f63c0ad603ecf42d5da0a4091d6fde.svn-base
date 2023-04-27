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
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.db.config.model.ActiveList;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 客服管理
 * @classNameCustomerServiceManaController
 * @Date 2023/3/29 13:46
 * @Version 1.0
 **/
@Controller
@RequestMapping("/customer")
public class CustomerServiceManaController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/customer/";
    private static String CODE = "customer";

    @DoControllerLog(name="进入客服管理配置页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "customer.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        List<Map> list = Db.selectList("select id,name,resources from CustomerService");
        return list;
    }

    /**
     * 添加
     */
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "customer_add.html";
    }
    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        String name = HttpKit.getRequest().getParameter("customer.name");
        String resources = HttpKit.getRequest().getParameter("customer.resources");
        int i = Db.save("CustomerService", "id", CMap.init().set("name", name).set("resources", resources));
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
    public String update(@PathVariable Integer id, ModelMap mm){
        Map active = Db.findById("CustomerService", id);
        mm.put("customer",active);
        mm.put("code",CODE);
        return BASE_PATH + "customer_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        String id = HttpKit.getRequest().getParameter("customer.id");
        String name = HttpKit.getRequest().getParameter("customer.name");
        String resources = HttpKit.getRequest().getParameter("customer.resources");
        int update = Db.update("CustomerService", "id", CMap.init().set("name", name).set("resources", resources).set("id",id));
        mm.put("code",CODE);
        if (update<0){
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
    public AjaxResult remove(@RequestParam String ids, ModelMap mm) {
        int cnt =Db.deleteByIds("CustomerService","id",ids);
        mm.put("code",CODE);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
}
