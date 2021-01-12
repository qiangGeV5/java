package com.zq.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.enums.CommentLevel;
import com.zq.mapper.*;
import com.zq.pojo.*;
import com.zq.pojo.vo.CommentLevelCountsVO;
import com.zq.pojo.vo.ItemCommentVO;
import com.zq.pojo.vo.SearchItemsVO;
import com.zq.service.ItemService;
import com.zq.utils.DesensitizationUtil;
import com.zq.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgfList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsImg> itemsImgs = itemsImgMapper.selectByExample(example);

        return itemsImgs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsSpec> itemsSpecs = itemsSpecMapper.selectByExample(example);
        return itemsSpecs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        ItemsParam itemsParam = itemsParamMapper.selectOneByExample(example);
        return itemsParam;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setTotalCounts(totalCounts);

        return commentLevelCountsVO;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCommentCounts(String itemId, Integer level){
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null){
            itemsComments.setCommentLevel(level);
        }
        int i = itemsCommentsMapper.selectCount(itemsComments);
        return i;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,Integer page ,Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);

        PageHelper.startPage(page,pageSize);

        List<ItemCommentVO> itemCommentVOS = itemsMapperCustom.queryItemComments(map);
        for(ItemCommentVO vo:itemCommentVOS){
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        return setterPagedGrid(itemCommentVOS,page);
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);

        PageHelper.startPage(page,pageSize);

        List<SearchItemsVO> searchItemsVOS = itemsMapperCustom.searchItems(map);

        return setterPagedGrid(searchItemsVOS,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);

        PageHelper.startPage(page,pageSize);

        List<SearchItemsVO> searchItemsVOS = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPagedGrid(searchItemsVOS,page);
    }


}
