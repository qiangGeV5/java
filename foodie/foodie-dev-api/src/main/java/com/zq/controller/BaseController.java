package com.zq.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    //微信支付成功 -> 支付中心 -> 天天吃货平台
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid?merchantOrderId=210114B832XN3GHH";
    //支付中心地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
//    command+shlft+u 快捷键
    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
}
