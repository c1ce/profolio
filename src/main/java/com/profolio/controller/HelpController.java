package com.profolio.controller;

import com.profolio.dto.ItemFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HelpController {

    @GetMapping(value = {"/admin/help","/members/help","/company/help"})
    public String helpList(){
        return "help/FAQList";
    }
}
