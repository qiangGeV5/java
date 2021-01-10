package com.zq.controller;

import com.zq.enums.YesOrNo;
import com.zq.pojo.Carousel;
import com.zq.pojo.Category;
import com.zq.pojo.vo.CategoryVO;
import com.zq.pojo.vo.NewItemsVO;
import com.zq.service.CarouselService;
import com.zq.service.CategoryService;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "首页",tags = "首页相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "轮播图", notes = "轮播图",httpMethod = "GET")
    @GetMapping("/carousel")
    public ZQJSONResult carousel(){

        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

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

        List<Category> categories = categoryService.queryAllRootLevelCat();

        return ZQJSONResult.ok(categories);
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
