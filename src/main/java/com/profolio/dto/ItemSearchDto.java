package com.profolio.dto;

import com.profolio.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus ;

    private String searchBy = "itemNm";

    private String searchQuery = "";

}