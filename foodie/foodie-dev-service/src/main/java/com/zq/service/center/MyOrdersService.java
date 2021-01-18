package com.zq.service.center;


import com.zq.pojo.Users;
import com.zq.pojo.bo.center.CenterUserBO;
import com.zq.utils.PagedGridResult;


public interface MyOrdersService {

    /**
     * 我的订单
     * @param UserId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String UserId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);


    /**
     * @Description: 订单状态 --> 商家发货
     */
    public void updateDeliverOrderStatus(String orderId);
}
