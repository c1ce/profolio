package com.profolio.entity;

import com.profolio.constant.ConatctStatus;
import com.profolio.constant.ItemSellStatus;
import com.profolio.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name="contact")
@Getter
@Setter
@ToString
public class Contact{

    @Id
    @Column(name="contact_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedBy
    @Column(updatable = false)
    private String sendName;

    @Column(nullable = false)
    private String recvName;

    @Column(nullable = false, length = 1000)
    private String Text;

    @Enumerated(EnumType.STRING)
    private ConatctStatus conatctStatus; //읽음여부

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;


    public void updateItem(ItemFormDto itemFormDto){

    }


}