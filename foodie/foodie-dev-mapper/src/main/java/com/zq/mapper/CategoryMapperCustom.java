package com.zq.mapper;


import com.zq.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

}