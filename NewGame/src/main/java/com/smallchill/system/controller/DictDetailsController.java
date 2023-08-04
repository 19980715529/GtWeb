package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.Dict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dict_details")
public class DictDetailsController extends BaseController {
    private static String LIST_SOURCE = "dict_details.list";
    private static String BASE_PATH = "/system/dict_details/";
    private static String CODE = "dict_details";
    private static String PREFIX = "dict_details";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict_details.html";
    }


    @Json
    @RequestMapping(KEY_LIST)
    public AjaxResult list() {
        Object gird = paginate("dict_details.dict_type_list");
        return json(gird);
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            Dict dict = Blade.create(Dict.class).findById(id);
            mm.put("dictcode", dict.getCode());
            mm.put("pId", id);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Dict dict = Blade.create(Dict.class).findById(id);
        mm.put("model", JsonKit.toJson(dict));
        mm.put("code", CODE);
        return BASE_PATH + "dict_edit.html";
    }
}
