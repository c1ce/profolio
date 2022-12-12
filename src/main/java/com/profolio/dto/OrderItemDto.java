package com.profolio.dto;

import com.profolio.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.imgUrl = imgUrl;
    }

    private String itemNm; //상품명
    private String imgUrl; //상품 이미지 경로

}