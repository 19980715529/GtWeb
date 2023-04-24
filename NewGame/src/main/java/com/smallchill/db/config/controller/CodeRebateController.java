package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.base.model.BaseModel;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @classNameCodeRebateController
 * @Date 2023/2/24 10:09
 * @Version 1.0
 **/
@Controller
@RequestMapping("/coderebate")
public class CodeRebateController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/coderebate/";
    private static String CODE = "coderebate";
    private static String LIST_SOURCE = "coderebate.new_list";
    private static String PREFIX = "coderebate";

    @Autowired
    private CommonService commonService;

    @DoControllerLog(name="/进入打码返利界面")
    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH +"coderebate.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    /**
     * 进入修改界面
     */
    @Permission({ADMINISTRATOR,ADMIN})
    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id,ModelMap mm){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map infoByOne = commonService.getInfoByOne("coderebate.one_active", map);
        mm.put("code",CODE);
        mm.put("coderebate",infoByOne);
        return BASE_PATH +"coderebate_edit.html";
    }
    /**
     * 修改
     */
    @Json
    @Permission({ADMINISTRATOR,ADMIN})
    @RequestMapping(KEY_UPDATE)
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




}
