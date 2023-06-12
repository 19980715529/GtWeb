package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exchangerecords")
public class ExchangeRecordsController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private ExchangeReviewService service;
    private static String BASE_PATH = "/system/exchangereview/";
    private static String CODE = "exchangerecords";
    private static String LIST_SOURCE = "exchange_records.all_list";
    private static String PREFIX = "exchange_records";
    /*
    进入兑换记录界面
     */
    @DoControllerLog(name="进入兑换界面")
    @RequestMapping("/")
    @Permission(ADMINISTRATOR)
    public String records(ModelMap mm){
        mm.put("code",CODE);
        return "/modules/platform/plog/platform_exchange_records.html";
    }

    /*
    查询兑换列表
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = null;
        String parameter = HttpKit.getRequest().getParameter("where");
        if(StrKit.isBlank(parameter)) {
            return null;
        }
        if(parameter.contains("%")){
            parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
        }
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
    /*
    获取兑换数据
     */
    @Json
    @RequestMapping("/data")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public AjaxResult data() {
        // 当天查询兑换申请的金额和人数
        Map current_gird = commonService.getInfoByOne("exchange_records.current_exchange_application_list", null);

        // 当天兑换成功的金额和人数
        Map current_girds = commonService.getInfoByOne("exchange_records.current_exchange_success_list", null);

        Map currentrecharge = commonService.getInfoByOne("player_recharge_log1.current_recharge",null);

        BigDecimal recharge_money = new BigDecimal(currentrecharge.get("money").toString());
        BigDecimal exchange_money = new BigDecimal(current_girds.get("current_moneys").toString());
        BigDecimal difference = recharge_money.subtract(exchange_money);
        current_girds.put("difference",difference.setScale(2));

        // 所有查询兑换申请的金额和人数
        Map gird = commonService.getInfoByOne("exchange_records.exchange_application_list", null);

        // 所有兑换成功的金额和人数
        Map girds = commonService.getInfoByOne("exchange_records.exchange_success_list", null);

        // 获取所有充值成功的
        Map recharge = commonService.getInfoByOne("player_recharge_log1.all_recharge",null);

        BigDecimal recharge_moneys = new BigDecimal(recharge.get("money").toString());
        BigDecimal exchange_moneys = new BigDecimal(girds.get("moneys").toString());
        BigDecimal differences = recharge_moneys.subtract(exchange_moneys);
        girds.put("difference",differences.setScale(2));

        Map map = new HashMap();
        map.put("current_gird", current_gird);
        map.put("current_girds", current_girds);
        map.put("gird", gird);
        map.put("girds", girds);
        return json(map);
    }

}
