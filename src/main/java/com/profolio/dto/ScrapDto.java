package com.profolio.dto;

import com.profolio.entity.Item;
import com.profolio.entity.Member;
import com.profolio.entity.Scrap;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScrapDto {

    private Long scrapCode;

    private Long itemId;

    private String Title;
    private String scrapUserId;
    private String scrapUrl;
    private LocalDateTime regTime;

    private LocalDateTime updateTime;
    private static ModelMapper modelMapper = new ModelMapper();
    public static ScrapDto of(Scrap scrap){
        return modelMapper.map(scrap,ScrapDto.class);
    }
}
