package com.zq.controller;


import com.zq.enums.OrderStatusEnum;
import com.zq.enums.PayMethod;
import com.zq.pojo.bo.SubmitOrderBO;
import com.zq.pojo.vo.MerchantOrdersVO;
import com.zq.pojo.vo.OrderVO;
import com.zq.service.OrderService;
import com.zq.utils.CookieUtils;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "订单相关相关",tags = {"订单相关相关"})
@RestController
@RequestMapping("orders")
@Log4j
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "用户下单", notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public ZQJSONResult list(@RequestBody SubmitOrderBO submitOrderBO,
                             HttpServletRequest request,
                             HttpServletResponse response){

        //1、创建订单
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type ||
                submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type){
            ZQJSONResult.errorMsg("支付方式不支持");
        }

        System.out.print(submitOrderBO);

        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();


        //2、创建订单后移除购物车中以提交商品
        //todo 整合redis之后完善，购物车中的商品清楚，并且同步到前端cookis
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);
        //3、向支付中心发送当前订单，用于保存支付中心的数据

        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        merchantOrdersVO.setAmount(1);//为了测试
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId","imooc");
        httpHeaders.add("password","imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO,httpHeaders);
        ResponseEntity<ZQJSONResult>  resultResponseEntity = restTemplate.postForEntity(paymentUrl,entity,ZQJSONResult.class);
        ZQJSONResult zqjsonResult = resultResponseEntity.getBody();
        if (zqjsonResult.getStatus() != 200){
            return ZQJSONResult.errorMsg("支付中心创建订单失败");
        }



        return ZQJSONResult.ok(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){

        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();

    }
}
