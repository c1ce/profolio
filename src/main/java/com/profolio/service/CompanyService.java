package com.profolio.service;

import com.profolio.entity.Company;
import com.profolio.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService{

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company){
        validateDuplicateCompany(company);
        return companyRepository.save(company);
    }

    private void validateDuplicateCompany(Company company){
        Company findCompany = companyRepository.findByEmail(company.getEmail());
        if(findCompany != null){
            throw new IllegalStateException("이미 가입된 기업회원입니다.");
        }
    }



}