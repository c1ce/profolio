package com.profolio.controller;

import com.profolio.dto.CompanyFormDto;
import com.profolio.entity.Company;
import com.profolio.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/company")
@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String companyForm(Model model){
        model.addAttribute("companyFormDto", new CompanyFormDto());
        return "company/companyForm";
    }

    @PostMapping(value = "/new")
    public String newCompany(@Valid CompanyFormDto companyFormDto, BindingResult bindingResult, Model model){

        System.out.println(companyFormDto.toString()+bindingResult.toString());
        if(bindingResult.hasErrors()){
            return "company/companyForm";
        }

        try {
            Company company = Company.createCompany(companyFormDto, passwordEncoder);
            companyService.saveCompany(company);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "company/companyForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginCompany(){return "/company/companyLoginForm";
    }


    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/company/companyLoginForm";
    }

}