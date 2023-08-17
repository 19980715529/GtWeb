package com.smallchill.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.Dict;
import com.smallchill.system.model.DictData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict_data")
public class DictDataController extends BaseController {
    private static String LIST_SOURCE = "dict_details.list";
    private static String BASE_PATH = "/system/dict_data/";
    private static String CODE = "dict_data";
    private static String PREFIX = "dict_data";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict_data.html";
    }


    @Json
    @RequestMapping(KEY_LIST)
    public AjaxResult list() {
        Object gird = paginate("dict_details.dict_data_list");
        JSONObject parse = JsonKit.parse(JsonKit.toJson(gird));
        List rows =(List) parse.get("rows");
        List list = treeList(rows, "0");
        parse.put("rows",list);
        return json(parse);
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict_data_add.html";
    }

    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            DictData dict = Blade.create(DictData.class).findById(id);
            mm.put("dictcode", dict.getCode());
            mm.put("pId", id);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_data_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        DictData dict = Blade.create(DictData.class).findById(id);
        mm.put("model", JsonKit.toJson(dict));
        mm.put("code", CODE);
        return BASE_PATH + "dict_edit.html";
    }

    /**
     * 生成树状结构
     */

    public List<Map<String,Object>> treeList(List<Map<String,Object>> list,String pcode){
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        if (list.size()>0){
            for (Map<String,Object> map:list){
                if (pcode.equals(map.get("pcode").toString())){
                    List<Map<String, Object>> children = treeList(list, map.get("code").toString());
                    if (children.size()>0){
                        map.put("children",children);
                        map.put("isParent",true);
                        maps.add(map);
                    }else {
                        map.put("isParent",false);
                        maps.add(map);
                    }
                }
            }
            return list;
        }
        return maps;
    }

}
