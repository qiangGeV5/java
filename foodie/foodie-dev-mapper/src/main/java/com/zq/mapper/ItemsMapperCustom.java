package com.zq.mapper;


import com.zq.pojo.vo.ItemCommentVO;
import com.zq.pojo.vo.SearchItemsVO;
import com.zq.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);
    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);


}