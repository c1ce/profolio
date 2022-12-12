package com.profolio.dto;

import com.profolio.constant.ConatctStatus;
import com.profolio.entity.Contact;
import com.profolio.entity.Item;
import com.profolio.service.AlarmService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class ContactDto {

    private Long id;

    private String sendName;

    private String recvName;

    private String Text;

    private ConatctStatus conatctStatus; //읽음여부

    private LocalDateTime regTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public Contact createContact(){
        return modelMapper.map(this, Contact.class);
    }

    public static ContactDto of(Contact contact){
        return modelMapper.map(contact,ContactDto.class);
    }

    private AlarmService alarmService;
    public ContactDto createMessage(ContactDto contactDto) {

        alarmService.alarmByMessage(contactDto);
        return contactDto;
    }
    public Long getReceiverId() {

        return id;
    }

}
