package com.profolio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {

    private Long cartItemId; //장바구니 상품 아이디

    private String itemNm; //상품명

    private String imgUrl; //상품 이미지 경로

    public CartDetailDto(Long cartItemId, String itemNm, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.imgUrl = imgUrl;
    }

}