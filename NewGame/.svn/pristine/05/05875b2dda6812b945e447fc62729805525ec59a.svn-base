package com.smallchill.game.player.controller;

import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.game.model.request.ServerGenerated;

import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.gameuserdb.AaExchangecode;
import com.smallchill.game.newmodel.gameuserdb.AaExchangecodeProp;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.beetl.sql.core.engine.TrimTag.PREFIX;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "exchange";
    private static String LIST_SOURCE = "player_exchange.list";


    @DoControllerLog(name = "进入兑换码兑换页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "player_exchange.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
    
	@Json
	@RequestMapping("/checkCode")
	public AjaxResult checkCode() {
		String code = getParameter("code");
		System.out.println(code);
		AaExchangecode ec = Blade.create(AaExchangecode.class).findFirstBy("ExchangeCode='"+code+"'", null);
		if(ec==null) {
			return error("该兑换码不存在.");
		} else if(ec.getExchangestate()==0){
			return error("该兑换码未被兑换过，批次号："+ec.getExchangebatch());
		}
		return success("");
	}

}
