package com.zq.controller;

import com.zq.enums.YesOrNo;
import com.zq.pojo.Carousel;
import com.zq.service.CarouselService;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation(value = "轮播图", notes = "轮播图",httpMethod = "GET")
    @GetMapping("/carousel")
    public ZQJSONResult carousel(){

        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

        return ZQJSONResult.ok(carousels);
    }

    //
}
