package com.profolio.service;

import com.profolio.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void alarmByMessage(ContactDto contactDto) {
        messagingTemplate.convertAndSend("/sub/" + contactDto.getReceiverId(), contactDto);
    }

}