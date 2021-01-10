package com.zq.service;

import com.zq.pojo.Category;
import com.zq.pojo.vo.CategoryVO;
import com.zq.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 跟具一级分类ID，查询子分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCategoryList(Integer rootCatId);

    /**
     * 查询首页一级分类下是实现
     * @param rootCatId
     * @return
     */
    public  List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
