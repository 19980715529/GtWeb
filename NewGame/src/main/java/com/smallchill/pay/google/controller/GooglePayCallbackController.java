package com.smallchill.pay.google.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.pay.google.model.GooglePayDto;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.smallchill.system.treasure.utils.CallBackUtils.successRecExecuted;

@RestController
@RequestMapping("/callback/GooglePay")
public class GooglePayCallbackController extends BaseController implements ConstShiro {
    @Autowired
    private RechargeRecordsService rechargeRecordsService;

    //    packageName为应用程序包名、productId商品id、purchaseToken谷歌返回的收据
    @PostMapping("/recharge")
    public AjaxResult checkOrder(@RequestBody GooglePayDto googlePayDto) {
        try {
            //使用服务帐户Json文件获取Google凭据
            List<String> scopes = new ArrayList<>();
            scopes.add(AndroidPublisherScopes.ANDROIDPUBLISHER);
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:static/刚下载的json文件，这里放到了static目录下");
            GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream())
                    .createScoped(scopes);
            // 使用谷歌凭据和收据从谷歌获取购买信息
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jsonFactory = new JacksonFactory();
            AndroidPublisher publisher = new AndroidPublisher.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("应用程序名").build();
            AndroidPublisher.Purchases purchases = publisher.purchases();
            final AndroidPublisher.Purchases.Products.Get request = purchases.products().get(googlePayDto.getPackageName(),
                    googlePayDto.getProductId(),googlePayDto.getPurchaseToken());
            System.out.println("==============="+request+"================");
            final ProductPurchase purchase = request.execute();
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
            // 订单号
            String orderNum = purchase.getDeveloperPayload();
            RechargeRecords recharge = rechargeRecordsService.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
            if (recharge==null){
                return fail("订单号不存在");
            }
            // 设置谷歌订单
            recharge.setPfOrderNum(purchase.getOrderId());
            if(purchaseState==1){
                // 关闭订单
                recharge.setOrderStatus(3);
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
            successRecExecuted(orderNum,recharge);
            rechargeRecordsService.update(recharge);
            return success("用户该买未消耗");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail("服务器异常");
        }
    }
}
