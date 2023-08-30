package com.smallchill.pay.google.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.pay.google.intercept.GooglePayValidate;
import com.smallchill.pay.google.model.GooglePayDto;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.smallchill.core.constant.ConstConfig.APPLICATION_NAME;
import static com.smallchill.system.treasure.utils.CallBackUtils.successRecExecuted;

@RestController
@RequestMapping("/callback/GooglePay")
public class GooglePayCallbackController extends BaseController implements ConstShiro {
    @Autowired
    private RechargeRecordsService rechargeRecordsService;

    //    packageName为应用程序包名、productId商品id、purchaseToken谷歌返回的收据
    @Before(GooglePayValidate.class)
    @PostMapping("/recharge")
    public AjaxResult checkOrder(GooglePayDto googlePayDto) {
        LOGGER.error(JsonKit.toJson(googlePayDto));
        try {
            RechargeRecords recharge;
            // 判断我订单号是否存在
            if (StrKit.isBlank(googlePayDto.getOrderNum())){
                // 订单号不存在
                recharge = rechargeRecordsService.findFirstBy("orderStatus in (1,3) and userId=#{userId} and channelPid=3",
                        CMap.init().set("userId", googlePayDto.getUserId()));
            }else {
                // 订单存在
                recharge = rechargeRecordsService.findFirstBy("orderNumber=#{orderNumber}", CMap.init().set("orderNumber", googlePayDto.getOrderNum()));
            }
            if (recharge==null){
                return fail("订单号不存在");
            }
            // 判断是否取消订单号
            if (googlePayDto.getCancel()==1){
                // 关闭订单
                recharge.setOrderStatus(4);
                rechargeRecordsService.update(recharge);
                return success("用户取消订单");
            }

            //使用服务帐户Json文件获取Google凭据
            ProductPurchase purchase = iniProductPurchase(googlePayDto.getPackageName(),googlePayDto.getProductId(),googlePayDto.getPurchaseToken());
            if (purchase==null){
                return fail("无效凭证");
            }
            /**
             * 返回参数
             * {
             * "purchaseTimeMillis": "16239806",//购买产品的时间，自纪元（1970 年 1 月 1 日）以来的毫秒数。
             * "purchaseState": 0,//订单的购买状态。可能的值为：0. 已购买 1. 已取消 2. 待定
             * "consumptionState": 0,//产品的消费状态。可能的值为： 0. 尚未消耗 1. 已消耗
             * "developerPayload": "",透传的订单号
             * "orderId": "GPA.XXXXXXX8",//google订单号
             * "purchaseType": 0,
             * "kind": "androidpublisher#productPurchase",
             * }
             */
            //订单的购买状态。可能的值为：0. 已购买 1. 已取消 2. 待定
            Integer purchaseState = purchase.getPurchaseState();
            LOGGER.error(JsonKit.toJson(purchase));
            // 根据谷歌订单进行出现是否存在
            RechargeRecords googleOrder = rechargeRecordsService.findFirstBy("pfOrderNum=#{orderNumber}", CMap.init().set("orderNumber", purchase.getOrderId()));
            // 判断向谷歌查询的订单和游戏端是否一样，不一样不处理
            if (purchase.getOrderId().equals(googlePayDto.getOrderId())){
                return fail("订单错误");
            }
            // 用户重复回调检测
            if (googleOrder!=null){
                // 判断支付状态
                Integer orderStatus = googleOrder.getOrderStatus();
                if (orderStatus==2){
                    return success("用户该买未消耗");
                }else if(orderStatus==1 || orderStatus==3){
                    return fail("待支付");
                }else {
                    return fail("用户取消支付");
                }
            }
            // 设置谷歌订单
            recharge.setPfOrderNum(purchase.getOrderId());
            if(purchaseState==1){
                // 关闭订单
                recharge.setOrderStatus(4);
                rechargeRecordsService.update(recharge);
                return fail("用户取消支付");
            }
            if(purchaseState==2){
                return fail("待支付");
            }
            // 是否消耗 0. 尚未消耗 1. 已消耗
            Integer consumptionState = purchase.getConsumptionState();
            if (consumptionState==1 && purchaseState==0){
                return success("用户购买已经消耗");
            }
            // 充值成功修改订单状态
            recharge.setOrderStatus(2);
            successRecExecuted(recharge.getOrderNumber(),recharge);
            rechargeRecordsService.update(recharge);
            return success("用户该买未消耗");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail("服务器异常");
        }
    }


    private ProductPurchase iniProductPurchase(String packageName,String subscriptionId,String purchaseToken) {
        try {
            //使用服务帐户Json文件获取Google凭据
            List<String> scopes = new ArrayList<>();
            scopes.add(AndroidPublisherScopes.ANDROIDPUBLISHER);
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:google.json");
            GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream())
                    .createScoped(scopes);
            // 使用谷歌凭据和收据从谷歌获取购买信息
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jsonFactory = new JacksonFactory();
            AndroidPublisher publisher = new AndroidPublisher.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(APPLICATION_NAME).build();
            AndroidPublisher.Purchases purchases = publisher.purchases();
            final AndroidPublisher.Purchases.Products.Get request = purchases.products().get(packageName, subscriptionId,purchaseToken);
            return request.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }


    }
}
