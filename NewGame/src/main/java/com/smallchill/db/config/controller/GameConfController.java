package com.smallchill.db.config.controller;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.db.config.meta.intercept.GameConfValidator;
import com.smallchill.db.config.model.GameConf;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 活动列表后台配置
 * @classNameActiveListController
 * @Date 2023/2/20 17:37
 * @Version 1.0
 **/
@Controller
@RequestMapping("/gameconf")
public class GameConfController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/gameconf/";
    private static String CODE = "gameconf";
    private static String LIST_SOURCE = "gameconf.new_list";
    private static String PREFIX = "gameconf";

    @Resource
    private CommonService commonService;
    @DoControllerLog(name="进入充值配置列表页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "gameconf.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        return paginateBySelf(LIST_SOURCE);
    }

    /**
     * 添加
     */
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "gameconf_add.html";
    }
    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Before(GameConfValidator.class)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        GameConf gameConf = mapping(PREFIX, GameConf.class);
        // 根据id查询游戏名字
        Map game = Db.findById("[QPServerInfoDB].[dbo].[GameKindItem]","KindID",gameConf.getGameId());
        gameConf.setName(game.get("KindName").toString());
        int i = Blade.create(GameConf.class).saveRtId(gameConf);
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
        Map gameconf = commonService.getInfoByOne("gameconf.one_list", map);
        mm.put("gameconf",gameconf);
        mm.put("code",CODE);
        return BASE_PATH + "gameconf_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Before(GameConfValidator.class)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        GameConf gameConf = mapping(PREFIX, GameConf.class);
        boolean update = Blade.create(GameConf.class).update(gameConf);
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
        int cnt =Blade.create(GameConf.class).deleteByIds(ids);
        mm.put("code",CODE);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
    /**
     *  获取所有游戏名称以及id
     */
    @Json
    @RequestMapping("/findGame")
    public AjaxResult findGameAll(){
        List<Map> list = commonService.getInfoList("gameconf.game_list", null);
//        ArrayList<Map> maps = list.stream()
//                .collect(Collectors.collectingAndThen(
//                        Collectors.toCollection(() ->
//                                new TreeSet<>(Comparator.comparing((o) -> o.get("KindID").toString()))), ArrayList::new));
//        List<Object> collect = maps.stream().map((Map i) -> getMap(i)).collect(Collectors.toList());
        return json(list);
    }
//    private static Map getMap(Map item){
//        String name = item.get("RoomName").toString().split("-")[0];
//        item.put("RoomName", name);
//        return item;
//    }
    /**
     * 获取游戏类型
     */
    @Json
    @RequestMapping("/gemaType")
    public AjaxResult getGameType(){
        List<Map> maps = Db.selectList("select id,name from blade_dict where pid = 56", null);
        return json(maps);
    }
}
