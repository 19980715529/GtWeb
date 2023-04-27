package com.smallchill.game.player.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.game.model.request.WithdrawalRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/*用户提现功能*/
@RequestMapping("/Withdrawal")
@Controller
public class PlayerWithdrawal  extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "Withdrawal";
    private static String PREFIX = "WithdrawalRequest";

    @DoControllerLog(name = "进入提现页面")
    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH +"player_Withdrawal.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginateBySelf("player_Withdrawal.list");
        return gird;
    }

    @RequestMapping(KEY_ADD)
    @Permission(ADMINISTRATOR)
    public String add(@RequestParam String id ,@RequestParam String Userid, ModelMap mm) {
        System.out.println(getRequest().getParameter("id"));
        mm.put("code", CODE);
        mm.put("id",id);
        mm.put("Userid",Userid);
        return BASE_PATH + "player_Withdrawal_add.html";
    }


    @Json
    @DoControllerLog(name = "发货")
    @RequestMapping(KEY_SAVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        WithdrawalRequest boxItem = mapping(PREFIX, WithdrawalRequest.class);
        Map map= Db.selectOne("select [Withdrawal_State] from [QPGameUserDB].[dbo].[AA_Recharge_By_WithdrawalRecord] where [ID] ="+boxItem.getOrderID()+" and [User_ID] ="+boxItem.getUserID());
        if((int)map.get("Withdrawal_State") == 1){
            return error(SAVE_FAIL_MSG+"该订单已提现！");
        }else{
            String nowtime = DateTimeKit.now();
            int result = Db.update("  update [QPGameUserDB].[dbo].[AA_Recharge_By_WithdrawalRecord] set [Withdrawal_State] = 1 ,[Over_time] = '"+ nowtime +"' where [ID] = "+boxItem.getOrderID()+" and [User_ID] ="+boxItem.getUserID(),null);
            if (result != 1) {
                return error(SAVE_FAIL_MSG+"提现修改失败！");
            }
        }
        return success("提现修改成功！");
    }

}
