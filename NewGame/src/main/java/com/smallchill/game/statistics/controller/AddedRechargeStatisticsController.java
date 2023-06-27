package com.smallchill.game.statistics.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/newrecharge")
public class AddedRechargeStatisticsController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/statistics/";
    private static String CODE = "newrecharge";
    protected Logger LOGGER = LogManager.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;

    @DoControllerLog(name="进入用户充值统计页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "newrecharge_statistics.html";
    }

    @DoControllerLog(name="进入用户充值统计页面")
    @RequestMapping("/details/{id}")
    public String details(@PathVariable Integer id, ModelMap mm) {
        mm.put("code", CODE);
        mm.put("id", id);
        // 查询所有
        Map newrecharge = commonService.getInfoByOne("newrecharge.find_one", CMap.init().set("id", id));
        mm.put("newrecharge", newrecharge);
        return BASE_PATH + "newrecharge_statistics_details.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        return paginateBySelf("newrecharge.all_list");
    }

    @Json
    @RequestMapping("/details/list")
    public Object detailsList() {
        return paginateBySelf("newrecharge.details_list");
    }

    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id,ModelMap mm) {
        Map map = Db.selectOne("select id,deliveryCost from [QPGameRecordDB].dbo.NewRechargeRecord where id=#{id}", CMap.init().set("id", id));
        mm.put("code", CODE);
        mm.put("newrecharge", map);
        return BASE_PATH + "newrecharge_statistics_edit.html";
    }

    @Json
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update() {
        String id = HttpKit.getRequest().getParameter("newrecharge.id");
        BigDecimal deliveryCost = new BigDecimal(HttpKit.getRequest().getParameter("newrecharge.deliveryCost"));
        // 查询
        Map map = Db.selectOne("select id,newRecUser from [QPGameRecordDB].dbo.NewRechargeRecord where id=#{id}", CMap.init().set("id", id));
        //计算
        BigDecimal cost=new BigDecimal(0);
        BigDecimal newRecUser = new BigDecimal(map.get("newRecUser").toString());
        if (newRecUser.intValue()==0){
            cost =new BigDecimal(0);
        }else {
           cost = deliveryCost.divide(newRecUser, 2,RoundingMode.HALF_UP);
        }
        int update = Db.update("[QPGameRecordDB].dbo.NewRechargeRecord", "id", CMap.init().set("id", id).set("cost", cost).set("deliveryCost", deliveryCost));
        if (update>0){
            return success(UPDATE_SUCCESS_MSG);
        }else {
            return fail(UPDATE_FAIL_MSG);
        }
    }

}
