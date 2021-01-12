package com.zq.controller;


import com.zq.enums.CommentLevel;
import com.zq.pojo.Items;
import com.zq.pojo.ItemsImg;
import com.zq.pojo.ItemsParam;
import com.zq.pojo.ItemsSpec;
import com.zq.pojo.vo.CommentLevelCountsVO;
import com.zq.pojo.vo.ItemCommentVO;
import com.zq.pojo.vo.ItemInfoVO;
import com.zq.utils.PagedGridResult;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import com.zq.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口",tags = "商品信息相关接口")
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemService itemService;
    @ApiOperation(value = "商品详情", notes = "商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public ZQJSONResult info(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return ZQJSONResult.errorMsg(null);
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgfList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return ZQJSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "商品评价等级", notes = "商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public ZQJSONResult commentLevel(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return ZQJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return ZQJSONResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "商品评价", notes = "商品评价",httpMethod = "GET")
    @GetMapping("/comments")
    public ZQJSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评价等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "查询的页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(itemId)){
            return ZQJSONResult.errorMsg("itemId==NUll");
        }
        if (level == null){
            level = CommentLevel.GOOD.type;
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return ZQJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品", notes = "搜索商品",httpMethod = "GET")
    @GetMapping("/search")
    public ZQJSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "查询的页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(keywords)){
            return ZQJSONResult.errorMsg("keyWords==NUll");
        }



        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return ZQJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "通过分类id搜索商品", notes = "通过分类id搜索商品",httpMethod = "GET")
    @GetMapping("/catItems")
    public ZQJSONResult catItems(
            @ApiParam(name = "catId",value = "商品id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "查询的页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize
    ){
        if (catId == null){
            return ZQJSONResult.errorMsg("catId==NUll");
        }



        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return ZQJSONResult.ok(pagedGridResult);
    }

}
