package com.zq.mapper;

import com.zq.my.mapper.MyMapper;
import com.zq.pojo.Category;
import com.zq.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapperCustom extends MyMapper<Category> {
    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> map);
}