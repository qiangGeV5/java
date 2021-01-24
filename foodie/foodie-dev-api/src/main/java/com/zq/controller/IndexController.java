package com.zq.controller;

import com.zq.enums.YesOrNo;
import com.zq.pojo.Carousel;
import com.zq.pojo.Category;
import com.zq.pojo.vo.CategoryVO;
import com.zq.pojo.vo.NewItemsVO;
import com.zq.service.CarouselService;
import com.zq.service.CategoryService;
import com.zq.utils.JsonUtils;
import com.zq.utils.RedisOperator;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Api(value = "首页",tags = "首页相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "轮播图", notes = "轮播图",httpMethod = "GET")
    @GetMapping("/carousel")
    public ZQJSONResult carousel(){

        List<Carousel> carousels = null;
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)){
            carousels = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(carousels));
        }else {

            carousels = JsonUtils.jsonToList(carouselStr,Carousel.class);
        }

        /**
         * 1.后台运营系统，一旦发生更改就删除缓存
         * 2.定时重置 （大量缓存时间错开）
         * 3.每个轮播图都有时间段，每个广告都会有过期时间，过期时间到了就开始重置
         */



        return ZQJSONResult.ok(carousels);
    }

    /**
     * 首页需求显示
     * 第一次查询大分类
     * 跟具父id加载子分类
     */

    @ApiOperation(value = "获取商品分类", notes = "获取商品分类",httpMethod = "GET")
    @GetMapping("/cats")
    public ZQJSONResult category(){


        List<Category> list = new ArrayList<>();
        String catsStr = redisOperator.get("cats");
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set("cats", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(catsStr, Category.class);
        }


        return ZQJSONResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public ZQJSONResult subcategory(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){

        if (rootCatId == null){
            return ZQJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCategoryList = categoryService.getSubCategoryList(rootCatId);

        return ZQJSONResult.ok(subCategoryList);
    }

    @ApiOperation(value = "查询每个一级分类下的六条最新数据", notes = "查询每个一级分类下的六条最新数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ZQJSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){

        if (rootCatId == null){
            return ZQJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);

        return ZQJSONResult.ok(sixNewItemsLazy);
    }
    //
}
