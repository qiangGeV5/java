package com.zq.controller;


import com.zq.pojo.bo.SubmitOrderBO;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "订单相关相关",tags = {"订单相关相关"})
@RestController
@RequestMapping("orders")
@Log4j
public class OrdersController {

    @ApiOperation(value = "用户下单", notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public ZQJSONResult list(@RequestBody SubmitOrderBO submitOrderBO){

        if (submitOrderBO.)

        System.out.print(submitOrderBO);

        //1、创建订单
        //2、创建订单后移除购物车中以提交商品
        //3、向支付中心发送当前订单，用于保存支付中心的数据
        return ZQJSONResult.ok();
    }
}