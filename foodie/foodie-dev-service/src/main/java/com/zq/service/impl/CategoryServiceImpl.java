package com.zq.service.impl;


import com.zq.mapper.CategoryMapper;
import com.zq.mapper.CategoryMapperCustom;
import com.zq.pojo.Category;
import com.zq.pojo.vo.CategoryVO;
import com.zq.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String ROOT_LEVEL="1";

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("type",ROOT_LEVEL);

        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCategoryList(Integer rootCatId) {
        List<CategoryVO> subCatList = categoryMapperCustom.getSubCatList(rootCatId);

        return subCatList;
    }


}
