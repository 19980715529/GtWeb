package com.smallchill.activity.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.model.UserPack;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/totalactivity")
public class TotalActivityController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/activity/totalactivity/";
    private static String CODE = "totalactivity";
    private static String LIST_SOURCE = "totalactivity.all_list";
    private static String PREFIX = "totalactivity";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="整体数据界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        ShiroUser user = ShiroKit.getUser();
        Integer id =(Integer) user.getId();
        // 查询包id
        Blade blade = Blade.create(UserPack.class);
        UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
        if (pack!=null){
            String clientType = pack.getClientType();
            Integer[] ids = Convert.toIntArray(clientType);
            mm.put("clientType", ids[0]);
        }else {
            mm.put("clientType", -9);
        }
        mm.put("code", CODE);
        return BASE_PATH+"totalactivity.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird;
        // 解析查询条件
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
}
