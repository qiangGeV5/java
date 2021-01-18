package com.zq.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.enums.OrderStatusEnum;
import com.zq.mapper.OrderMapperCustom;
import com.zq.mapper.OrderStatusMapper;
import com.zq.mapper.UsersMapper;
import com.zq.pojo.OrderStatus;
import com.zq.pojo.Users;
import com.zq.pojo.bo.center.CenterUserBO;
import com.zq.pojo.vo.ItemCommentVO;
import com.zq.pojo.vo.MyOrdersVO;
import com.zq.service.center.CenterUserService;
import com.zq.service.center.MyOrdersService;
import com.zq.utils.DesensitizationUtil;
import com.zq.utils.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    private OrderMapperCustom orderMapperCustom;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String UserId, Integer orderStatus, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",UserId);
        if (orderStatus != null){
            map.put("orderStatus",orderStatus);
        }

        PageHelper.startPage(page,pageSize);

        List<MyOrdersVO> myOrdersVOS = orderMapperCustom.queryMyOrders(map);

        return setterPagedGrid(myOrdersVOS,page);


    }

    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }




}



































