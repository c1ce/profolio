package com.profolio.controller;

import com.profolio.dto.ContactDto;
import com.profolio.entity.Contact;
import com.profolio.service.CompanyPopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CompanyPopupController {

    private final CompanyPopupService companyPopupService;

    @GetMapping(value = "/company/share/contact")
    public String companyContact(){
        return "fragments/contact";
    }

    @PostMapping(value = "/company/share/contact/send")
    public String companyContactSend(ContactDto contactDto){
        companyPopupService.save(contactDto);
        return "redirect:/company/portfolios/share";
    }

    @PostMapping(value = {"/company/contact/delete","/members/contact/delete"})
    public String companyContactSend(@RequestParam(value="deleteList[]") List<Long> deleteList){
        companyPopupService.delete(deleteList);
        return "redirect:/company/portfolios/share";
    }

    @GetMapping(value={"/company/contact","/company/contact/list","/company/contact/list/{page}"
            ,"/members/contact/list","/members/contact/list/{page}"})
    public String companyList(@PathVariable("page") Optional<Integer> page,Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        if(userDetails.getAuthorities().toString().equals("[ROLE_USER]")){
            Page<Contact> list = companyPopupService.getConatctUserList(username,pageable);
            model.addAttribute("list",list);
        }else{
            Page<Contact> list = companyPopupService.getConatctCompanyList(username,pageable);
            model.addAttribute("list",list);
        }
        model.addAttribute("maxPage", 5);
        return "/contact/contactList";
    }
    @GetMapping(value={"/company/contact/{id}","/members/contact/{id}"})
    public String companyGetContact(@PathVariable("id") Long id,Model model){


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        if(userDetails.getAuthorities().toString().equals("[ROLE_USER]")){
            companyPopupService.changeRead(id);
        }
        Contact contact = companyPopupService.getContact(id);
        model.addAttribute("contact",contact);
        return "/contact/contactItem";
    }

}
