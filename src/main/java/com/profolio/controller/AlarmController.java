package com.profolio.controller;

import com.profolio.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    //Path는 되는데 Dto는 왜안됌? => @Dto생성자에 set이필요해서 안됌 차라리 Map으로 받는게 편할듯...
    @MessageMapping("/messages/{ids}")
    public void sendMsg(@DestinationVariable("ids") String ids,@Payload Map<String,Object> data){
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + ids,ids);

    }



}
