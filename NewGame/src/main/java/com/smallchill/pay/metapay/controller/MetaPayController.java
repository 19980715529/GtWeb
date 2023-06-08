package com.smallchill.pay.metapay.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.RechargeRecordsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pay/MetaPay")
public class MetaPayController extends BaseController implements ConstShiro {

    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;
}
