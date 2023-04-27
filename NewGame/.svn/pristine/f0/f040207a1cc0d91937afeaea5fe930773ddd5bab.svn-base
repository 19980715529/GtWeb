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
import com.smallchill.db.config.model.TurnTable;
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
 * @Description TODO
 * @classNameTurnTableController
 * @Date 2023/2/23 15:02
 * @Version 1.0
 **/
@Controller
@RequestMapping("/turntable")
public class TurnTableController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/turntable/";
    private static String CODE = "turntable";
    private static String LIST_SOURCE = "turntable.new_list";
    private static String PREFIX = "turntable";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入充值配置列表页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "turntable.html";
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
        return BASE_PATH + "turntable_add.html";
    }
    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        TurnTable turnTable = mapping(PREFIX, TurnTable.class);
        int temp = Db.insert("insert into [QPServerInfoDB].[dbo].[TurntableRewardTypeConfig](SourceType,RewardType,MediumWeight,AmountOfReward)" +
                " values(#{sourceType},#{rewardType},#{mediumWeight},#{amountOfReward})", turnTable);
        mm.put("code",CODE);
        if (temp>0){
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
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map active = commonService.getInfoByOne("turntable.one_turntable", map);
        mm.put("turntable",active);
        mm.put("code",CODE);
        return BASE_PATH + "turntable_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        TurnTable turnTable = mapping(PREFIX, TurnTable.class);
        int update = Db.update("update [QPServerInfoDB].[dbo].[TurntableRewardTypeConfig] set " +
                "SourceType=#{sourceType},RewardType=#{rewardType},MediumWeight=#{mediumWeight},AmountOfReward=#{amountOfReward} where Id =#{id}", turnTable);
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
        int cnt =Blade.create(TurnTable.class).deleteByIds(ids);
        mm.put("code",CODE);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
}
