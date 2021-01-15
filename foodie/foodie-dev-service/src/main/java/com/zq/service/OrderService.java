package com.zq.service;

import com.zq.pojo.Carousel;
import com.zq.pojo.OrderStatus;
import com.zq.pojo.bo.SubmitOrderBO;
import com.zq.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
   public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
   public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
   public OrderStatus queryOrderStatusInfo(String orderId);
}
