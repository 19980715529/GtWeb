package com.smallchill.game.player.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * @Description TODO
 * @classNamePlayerExchangeRecordController
 * @Date 2023/3/13 10:20
 * @Version 1.0
 **/
@Controller
@RequestMapping("/player")
public class PlayerExchangeRecordController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "player";
    @Autowired
    private CommonService commonService;
    @DoControllerLog(name = "进入玩家兑换记录界面")
    @RequestMapping("/exchange_record")
    public String exchangeRecord(@RequestParam(name = "id",required = false) Integer id, ModelMap mm){
        mm.put("code",CODE);
        mm.put("userId",id);
        return BASE_PATH+"player_exchange_record.html";
    }
    /**
     * 兑换记录查询
     */
    @Json
    @RequestMapping("/erlist")
    public Object list(){
        // 更加用户id查询兑换订单
        Object gird = new Object();
        String parameter = HttpKit.getRequest().getParameter("where");
        if(StrKit.isBlank(parameter)) {
            return gird;
        }
        if(parameter.contains("%")){
            parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
        }
        // 解析查询条件
        Map paras = JSON.parseObject(parameter, Map.class);
        gird = commonService.getInfoList("player_exchange_record.player_list", paras);
        return gird;
    }
}
