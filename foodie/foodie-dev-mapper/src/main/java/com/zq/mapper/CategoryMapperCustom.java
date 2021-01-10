package com.zq.mapper;


import com.zq.pojo.vo.CategoryVO;
import com.zq.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap")Map<String,Object> map);
}