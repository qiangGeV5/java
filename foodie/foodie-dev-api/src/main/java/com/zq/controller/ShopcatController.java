package com.zq.controller;

import com.zq.pojo.bo.ShopcartBO;
import com.zq.utils.JsonUtils;
import com.zq.utils.RedisOperator;
import com.zq.utils.ZQJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车接口",tags = {"购物车接口"})
@RestController
@RequestMapping("shopcart")
@Log4j
public class ShopcatController extends BaseController{

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "购物车添加商品接口", notes = "购物车添加商品接口",httpMethod = "POST")
    @PostMapping("/add")
    public ZQJSONResult add(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId,
            @ApiParam(name = "shopcartBO",value = "shopcartBO",required = false)
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
            ){

        if (StringUtils.isBlank(userId)){
            return ZQJSONResult.errorMsg("");
        }

//        TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopcartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShopcartBO sc: shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopcartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(shopcartBO);
            }
        } else {
            // redis中没有购物车
            shopcartList = new ArrayList<>();
            // 直接添加到购物车中
            shopcartList.add(shopcartBO);
        }

        // 覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));

        return ZQJSONResult.ok();
    }

    @ApiOperation(value = "购物车删除商品接口", notes = "购物车删除商品接口",httpMethod = "POST")
    @PostMapping("/del")
    public ZQJSONResult del(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam String userId,
            @ApiParam(name = "itemSpecId",value = "shopcartBO",required = false)
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)){
            return ZQJSONResult.errorMsg("");
        }

        //TODO 前端用户在登录的情况下，购物车中删除商品，会同时在后端同步购物车到redis缓存


        return ZQJSONResult.ok();
    }

}
