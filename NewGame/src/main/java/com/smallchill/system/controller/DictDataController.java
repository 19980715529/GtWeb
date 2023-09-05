package com.smallchill.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.meta.intercept.DictDataValidator;
import com.smallchill.system.meta.intercept.DictValidator;
import com.smallchill.system.model.Dict;
import com.smallchill.system.model.DictData;
import com.smallchill.system.treasure.model.PayChannelPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smallchill.core.constant.ConstShiro.ADMINISTRATOR;

@Controller
@RequestMapping("/dict_data")
public class DictDataController extends BaseController {
    private static String LIST_SOURCE = "dict_details.list";
    private static String BASE_PATH = "/system/dict_data/";
    private static String CODE = "dict_data";
    private static String PREFIX = "dict_data";

    @Autowired
    private CommonService commonService;

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict_data.html";
    }


    @Json
    @RequestMapping(KEY_LIST)
    public AjaxResult list() {
        Map<String, Object> map = new HashMap<>();
        List<Map> infoList = commonService.getInfoList("dict_details.dict_data_list", null);
        List list = treeList(infoList, 0);
        map.put("page",0);
        map.put("records",0);
        map.put("total",0);
        map.put("rows",list);
        return json(map);
    }

    @RequestMapping(KEY_ADD)
    public String add(@RequestParam(required = false) Integer id, ModelMap mm) {
        if (null != id) {
            mm.put("pId", id);
//            Map dict = Blade.create(DictData.class).findFirst("select * from [dbo].[blade_dict_data] where id = #{id}",
//                    CMap.init().set("id", id));
            DictData dict = Blade.create(DictData.class).findById(id);
            mm.put("pcode", dict.getCode());
        }else {
            mm.put("pId", 0);
            mm.put("pcode", "0");
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_data_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        LOGGER.error(id);
//        Object dict = Blade.create(DictData.class).findFirst("select * from [dbo].[blade_dict_data] where id = #{id}",
//                CMap.init().set("id", id));
        Object dict = Blade.create(DictData.class).findById(id);
        mm.put("model", dict);
        mm.put("code", CODE);
        return BASE_PATH + "dict_data_edit.html";
    }

    @Json
    @Before(DictDataValidator.class)
    @PostMapping(KEY_UPDATE)
    public AjaxResult update(DictData dictData){
        LOGGER.error(JsonKit.toJson(dictData));
        boolean temp = Blade.create(DictData.class).update(dictData);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }


    @Json
    @Before(DictDataValidator.class)
    @PostMapping(value = KEY_SAVE)
    public AjaxResult save(DictData dictData) {
        LOGGER.error(JsonKit.toJson(dictData));
        boolean temp = Blade.create(DictData.class).save(dictData);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        Integer[] Ids = Convert.toIntArray(ids);
        Blade blade = Blade.create(DictData.class);
        boolean b = blade.updateBy("isDel=1", "id IN (#{join(ids)})", CMap.init().set("ids", Ids));
        if (b) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
    /**
     * 更加父编码查询
     */
    @Json
    @RequestMapping("/data")
    public AjaxResult data(@RequestParam String pcode) {
        List<Object> list = Blade.create(DictData.class).findBy("pcode=#{pcode}", CMap.init().set("pcode", pcode));
        return json(list);
    }

    /**
     * 生成树状结构
     */

    public List<Map<String,Object>> treeList(List<Map> list,Integer pid){
        List<Map<String, Object>> maps = new ArrayList<>();
        if (list.size()>0){
            for (Map<String,Object> map:list){
                if (pid == Integer.parseInt(map.get("pid").toString())){
                    List<Map<String, Object>> children = treeList(list, Integer.parseInt(map.get("id").toString()));
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
        }
        return maps;
    }

}
