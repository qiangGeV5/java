package com.zq.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户添加商品到购物车BO", description = "用户添加商品到购物车BO，由用户传入的数据的数据封装在此entity")
public class ShopcartBO {

//    @ApiModelProperty(value = "用户名",name = "username",example = "zq",required = true)
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;





}
