package com.smallchill.game.player.controller;

import java.util.*;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.DateTime;
import com.smallchill.game.newmodel.AaShopPropUserprop;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.Gamescoreinfo;
import com.smallchill.game.newmodel.treasuredb.SpecialAccounts;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.model.request.SpecialaccountsRequest;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/specialAccounts")
public class PlayerSpecialAccountsController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "specialAccounts";
    private static String PREFIX = "specialAccounts";

    @Autowired
    CommonService commonService;

    @DoControllerLog(name = "进入靓号设置页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "player_special_accounts.html";
    }

    @Json
    @RequestMapping("/list")
    public Object specialAccountsList() {
        JqGrid gird = new JqGrid();
        gird = (JqGrid) paginateBySelf("player_special_accounts.list");
        return gird;
    }

    @Json
    @RequestMapping("/blist")
    public Object BeautifulList(){
        String req = getRequest().getParameter("where");
        Object gird = paginateBySelf("player_special_accounts.blist");
        return gird;
    }

    @Json
    @RequestMapping("/findUser")
    public Object FindspecialAccounts(HttpServletRequest req) {
        String UserId = req.getParameter("UserID");
        Map map = new HashMap();
        map.put("UserID", UserId);
        List list = commonService.getInfoList("player_special_accounts.find_list", map);
        JqGrid gird = new JqGrid();
        gird.setRows(list);
        gird.setTotal(1);
        gird.setPage(1);
        gird.setRecords(list.size());
        return gird;
    }

    @Json
    @RequestMapping("/findBeautID")
    public Object FindBeautID(HttpServletRequest req) {
        String UserId = req.getParameter("UserID");
        Map map = new HashMap();
        map.put("UserID", UserId);
        List list = commonService.getInfoList("player_special_accounts.find_Blist", map);
        JqGrid gird = new JqGrid();
        gird.setRows(list);
        gird.setTotal(1);
        gird.setPage(1);
        gird.setRecords(list.size());
        return gird;
    }

    @RequestMapping(KEY_ADD)
    @Permission(ADMINISTRATOR)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "special_accounts_add.html";
    }

    @DoControllerLog(name = "新增靓号信息")
    @Json
    @Transactional
    /*@RequestMapping(KEY_SAVE)*/
    @Permission(ADMINISTRATOR)
    public AjaxResult save() {
        //取得原本账号ID
        SpecialaccountsRequest boxItem = mapping(PREFIX, SpecialaccountsRequest.class);
        SpecialAccounts news = Blade.create(SpecialAccounts.class).findFirstBy("[NowHasID]="+boxItem.getOldID()+"or [NowHasID] ="+boxItem.getNewID(),null);
        if(news!=null){
            return error(SAVE_FAIL_MSG+"该用户已绑定");
        }
        //查询用户数据
        Accountsinfo newUser = Blade.create(Accountsinfo.class).findById(boxItem.getNewID());
        Accountsinfo oldUser = Blade.create(Accountsinfo.class).findById(boxItem.getOldID());
        Gamescoreinfo oldGame = Blade.create(Gamescoreinfo.class).findById(boxItem.getOldID());
        Gamescoreinfo newGame = Blade.create(Gamescoreinfo.class).findById(boxItem.getNewID());
        AaShopPropUserprop oldScoreUser = Blade.create(AaShopPropUserprop.class).findFirstBy(" User_Id="+boxItem.getOldID()+" and Prop_Id=1", null);
        System.out.println(oldUser);
        if (oldUser == null || oldGame == null || isEmpty(oldUser.getLogonpass().replace(" ","")) || oldUser.getIsRobit() == 1) {
            return error(SAVE_FAIL_MSG + "用户不存在");
        } else if (newUser == null) {
            return error(SAVE_FAIL_MSG + "靓号机器人不存在");
        } else {
            //新用户
            Accountsinfo info = oldUser;
            info.setUserid(newUser.getUserid());
            //info.setLogonpass("123456");不初始密码
            info.setIsRobit(0);
            info.setBusinessman(1);
            boolean temp = Blade.create(Accountsinfo.class).update(info);
            //原有变为机器人游客清空
            Accountsinfo guest = new Accountsinfo();
            guest.setUserid(Integer.valueOf(boxItem.getOldID()));
            guest.setLimitlogin(1);//禁止登录
            guest.setBusinessman(0);
            guest.setIsRobit(1);//改为机器人
            guest.setLogonpass("@fr236w$");
            guest.setBindphone("12345678901");
            guest.setAccounts("Guest"+Integer.valueOf(boxItem.getOldID()));
            boolean temp2 = Blade.create(Accountsinfo.class).update(guest);
            //添加游戏列表数据
            Gamescoreinfo newG = oldGame;
            newG.setUserid(newUser.getUserid());
            boolean temp3 = false;
            if(newGame == null){
                temp3  = Blade.create(Gamescoreinfo.class).save(newG);
            }else{
                temp3 = Blade.create(Gamescoreinfo.class).update(newG);
            }
            //更新用户携带金币
            boolean temp4 = false;
            if(oldScoreUser == null){//如果这个是新用户
                temp4 = Blade.create(AaShopPropUserprop.class).updateBy(" Amount=0", " User_Id="+boxItem.getNewID()+" and Prop_Id=1", null);
            }else{
                temp4 = Blade.create(AaShopPropUserprop.class).updateBy(" Amount=" + oldScoreUser.getAmount(), " User_Id="+boxItem.getNewID()+" and Prop_Id=1", null);
            }

            Calendar c = Calendar.getInstance();
            if (temp && temp2 && temp3 && temp4) {
                SpecialAccounts spa = new SpecialAccounts();
                spa.setNowHasID(newUser.getUserid());
                spa.setOnceID(Integer.valueOf(boxItem.getOldID()));
                spa.setUpdateDate(c.getTime());
                boolean temp5 = Blade.create(SpecialAccounts.class).save(spa);
                if (temp5) {
                    return success(SAVE_SUCCESS_MSG);
                } else {
                    throw new RuntimeException(SAVE_FAIL_MSG);
                }
            } else {
                throw new RuntimeException(SAVE_FAIL_MSG);
            }
        }
    }

    @DoControllerLog(name = "新增靓号信息")
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult Threesave(){
        SpecialaccountsRequest boxItem = mapping(PREFIX, SpecialaccountsRequest.class);
        Accountsinfo oldUser = Blade.create(Accountsinfo.class).findFirstBy("[UserID]="+boxItem.getOldID(),null);
        SpecialAccounts news = Blade.create(SpecialAccounts.class).findFirstBy("[NowHasID]="+boxItem.getOldID()+" or [NowHasID] ="+boxItem.getNewID(),null);
        if(news!=null||!oldUser.getBeautifulID().equals(oldUser.getUserid())){
            return error(SAVE_FAIL_MSG+"该用户已绑定");
        }
        boolean temp = false;
        temp = Blade.create(Accountsinfo.class).updateBy("BeautifulID ="+boxItem.getNewID() +",Businessman =" + 1,"UserID ="+boxItem.getOldID(),null);
        Calendar c = Calendar.getInstance();
        if(temp){
            SpecialAccounts spa = new SpecialAccounts();
            spa.setNowHasID(Integer.parseInt(boxItem.getNewID()));
            spa.setOnceID(oldUser.getUserid());
            spa.setUpdateDate(c.getTime());
            boolean temp2 = Blade.create(SpecialAccounts.class).save(spa);
            if (temp2) {
                Map beaut = Db.selectOne("select * from [QPGameUserDB].[dbo].[ReserveBeautifulID] where [BeautifulID] ="+boxItem.getNewID());
                if(beaut!=null)Db.update("update [QPGameUserDB].[dbo].[ReserveBeautifulID] set [UserID] = "+oldUser.getUserid()+"where [BeautifulID] ="+boxItem.getNewID(),null);
                return success(SAVE_SUCCESS_MSG);
            } else {
                throw new RuntimeException(SAVE_FAIL_MSG);
            }
        }else {
            throw new RuntimeException(SAVE_FAIL_MSG);
        }
    }


    @RequestMapping("/findBeaut")
    @Permission(ADMINISTRATOR)
    public String findBeaut(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "player_beautifulID.html";
    }
}