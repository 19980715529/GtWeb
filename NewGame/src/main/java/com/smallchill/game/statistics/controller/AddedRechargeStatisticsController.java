package com.smallchill.game.statistics.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/statistics")
public class AddedRechargeStatisticsController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/statistics/";
    private static String CODE = "statistics";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入日注册统计页面")
    @RequestMapping("/newrecharge/")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "newrecharge_statistics.html";
    }
}
