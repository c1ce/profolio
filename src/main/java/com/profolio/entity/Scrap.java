package com.profolio.entity;

import com.profolio.dto.ItemFormDto;
import com.profolio.dto.ScrapDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Scrap extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키 자동생성전략
    @Column(name="scrap_code")
    private Long scrapCode;

    private Long itemId;

    private String Title;
    private String scrapUserId;

    private String scrapUrl;

    public void updateItem(ScrapDto scrapDto){
        this.itemId = scrapDto.getItemId();
        this.scrapUserId = scrapDto.getScrapUserId();
        this.Title = scrapDto.getTitle();
        this.scrapUrl = scrapDto.getScrapUrl();
    }
}
