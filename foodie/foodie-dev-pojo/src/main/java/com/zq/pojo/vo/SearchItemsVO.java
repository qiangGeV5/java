package com.zq.pojo.vo;

import lombok.Data;

/**
 * 商品搜索列表
 */

@Data
public class SearchItemsVO {

    private String itemId;
    private String itemsName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;

}
