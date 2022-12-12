package com.profolio.controller;

import com.profolio.dto.CompanyFormDto;
import com.profolio.entity.Account;
import com.profolio.entity.Company;
import com.profolio.service.AccountService;
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
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String companyForm(Model model){
        model.addAttribute("companyFormDto", new CompanyFormDto());
        return "contactList";
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
            Account account = new Account();
            account.setEmail(company.getEmail());
            account.setPassword(company.getPassword());
            account.setName(company.getName());
            account.setRole(company.getRole());
            accountService.saveAccount(account);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "company/companyForm";
        }

        return "redirect:/";
    }

    //Member쪽에서 Account객체로 로그인
    @GetMapping(value = "/login")
    public String loginCompany(){return "/member/memberLoginForm";
    }


    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

}