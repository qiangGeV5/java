package com.zq.service;

import com.zq.pojo.Carousel;
import com.zq.pojo.bo.SubmitOrderBO;

import java.util.List;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
   public void createOrder(SubmitOrderBO submitOrderBO);
}
