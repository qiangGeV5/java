package com.zq.service;

import com.zq.pojo.Items;
import com.zq.pojo.ItemsImg;
import com.zq.pojo.ItemsParam;
import com.zq.pojo.ItemsSpec;
import com.zq.pojo.vo.CommentLevelCountsVO;
import com.zq.pojo.vo.ItemCommentVO;
import com.zq.utils.PagedGridResult;

import java.util.List;

public interface ItemService {


    /**
     * 商品详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgfList(String itemId);

    /**
     * 商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);


    /**
     * 根据商品id查询商品
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);


    /**
     * 根据商品id查询商品评价等级数量
     * @param itemId
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品评价
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);


}
