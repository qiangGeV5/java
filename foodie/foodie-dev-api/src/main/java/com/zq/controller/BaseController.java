package com.zq.controller;

import com.zq.pojo.Orders;
import com.zq.pojo.Users;
import com.zq.pojo.vo.UsersVO;
import com.zq.service.center.MyOrdersService;
import com.zq.utils.RedisOperator;
import com.zq.utils.ZQJSONResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String FOODIE_SHOPCART = "shopcart";

    //微信支付成功 -> 支付中心 -> 天天吃货平台
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid?merchantOrderId=210114B832XN3GHH";
    //支付中心地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
//    command+shlft+u 快捷键
    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String IMAGE_USER_FACE = File.separator + "Users" +
                                                    File.separator + "admin" +
                                                    File.separator + "Desktop" +
                                                    File.separator + "image";

    @Autowired
    private MyOrdersService myOrdersService;

    @Autowired
    private RedisOperator redisOperator;
    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public ZQJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return ZQJSONResult.errorMsg("订单不存在！");
        }
        return ZQJSONResult.ok(order);
    }

    public UsersVO conventUserVO(Users users){
        //实现用户redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN+":"+users.getId(),uniqueToken);


        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users,usersVO);
        usersVO.setUniqueToken(uniqueToken);
        return usersVO;
    }
}
