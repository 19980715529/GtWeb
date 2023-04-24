package com.smallchill.game.player.controller;

import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.game.model.request.ServerGenerated;

import com.smallchill.game.newmodel.Accountsinfo;
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

import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/NoviceCard")
public class PlayerNoviceCard extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "NoviceCard";
    private static String PREFIX = "serverGenerated";


    @DoControllerLog(name = "进入新手卡页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        try {
            Map allUsered = Db.selectOne("select COUNT(*) AllUsered from [QPGameUserDB].[dbo].[AA_ExchangeCode] where ExchangeState = 1 ");
            mm.put("AllUsered", allUsered.get("AllUsered"));
            Map maxcode = Db.selectOne("select MAX(Exchange_Id) MaxCode from [QPGameUserDB].[dbo].[AA_ExchangeCode]");
            mm.put("MaxCode", maxcode.get("MaxCode"));
            Map sumnum = Db.selectOne("select ISNULL(sum(ExchangeNum),0) SumNum from [QPGameUserDB].[dbo].[AA_ExchangeCode_Generate]");
            mm.put("SumNum", sumnum.get("SumNum"));
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            System.out.println(sumnum);
            String kulv = numberFormat.format((Double.parseDouble(sumnum.get("SumNum").toString()) / Double.parseDouble(maxcode.get("MaxCode").toString())) * 100);
            mm.put("kulv", kulv + "%");
        }catch (RuntimeException e){

        }finally {
            return BASE_PATH + "player_NoviceCard.html";
        }
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginateBySelf("player_exchange.Generate_list");
        return gird;
    }

    @RequestMapping("/NoviceCardinfo" + "/{pici}")
    public String exchaneinfo(@PathVariable Integer pici, ModelMap mm) {
        mm.put("ExchangeBatch", pici);
        mm.put("code", CODE);
        return BASE_PATH + "player_exdetails.html";
    }

    @Json
    @RequestMapping("/exlist")
    public Object exdetailslist() {
        Object gird = paginateBySelf("player_exchange.batch_card_list");
//        Object gird = paginateBySelf("player_exchange.Exdetails_list");
        return gird;
    }

    @RequestMapping("/exadd")
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "player_NoviceCard_add.html";
    }

    @DoControllerLog(name = "新增新手卡")
    @Json
    @Transactional
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        ServerGenerated boxItem = mapping(PREFIX, ServerGenerated.class);
        if(boxItem.getExchangeNum()>10000){
            return error("新手卡不能大于1万条");
        }
        Accountsinfo newUser = Blade.create(Accountsinfo.class).findFirst("SELECT * FROM [QPGameUserDB].[dbo].[AccountsInfo] WHERE [BeautifulID] ="+boxItem.getUserID() +" or [UserID] ="+boxItem.getUserID(),null);
        if(newUser.getBusinessman() == null || newUser.getBusinessman() == 0){
            return error("请填写商人账号");
        }else {
            Map maxp = Db.selectOne("SELECT MAX([ExchangeBatch]) maxp From [QPGameUserDB].[dbo].[AA_ExchangeCode]");
            int maxeb = (int) maxp.get("maxp");
            int neb = maxeb + 1;
            int meid = 0;
            if(maxeb==0) {
            } else {
            	Map eid = Db.selectOne("SELECT MAX([Exchange_Id]) eid FROM [QPGameUserDB].[dbo].[AA_ExchangeCode] WHERE [ExchangeBatch] = " + maxeb);
            	meid = (int) eid.get("eid");
            }
            int end = meid + boxItem.getExchangeNum();
            int result = Db.update("update [QPGameUserDB].[dbo].[AA_ExchangeCode] set [ExchangeBatch] = " + neb + " where Exchange_Id >" + meid + " and Exchange_Id <= " + end, null);
            if (result != 0) {
                boxItem.setExchangeBatch(String.valueOf(neb));
                Date date = DateTimeKit.date();
                boxItem.setUserID(String.valueOf(newUser.getUserid()));
                boxItem.setExchangeGenerate(date);
                Boolean temp = Blade.create(ServerGenerated.class).save(boxItem);
                if (temp) {
                    return success(SAVE_SUCCESS_MSG);
                } else {
                    throw new RuntimeException(SAVE_FAIL_MSG);
                }
            } else {
                return error("生成失败,新手卡已用完.");
            }
        }
    }

}
