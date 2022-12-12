package com.profolio.entity;

import com.profolio.constant.Role;
import com.profolio.dto.CompanyFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="company")
@Getter @Setter
@ToString
public class Company extends BaseTimeEntity {

    @Id
    @Column(name="company_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String manager;
    private String represent;
    private String companyNumber;
    private LocalDate companyFoundingDate;


    public static Company createCompany(CompanyFormDto companyFormDto, PasswordEncoder passwordEncoder){
        Company company = new Company();
        company.setName(companyFormDto.getName());
        company.setEmail(companyFormDto.getEmail());
        company.setAddress(companyFormDto.getAddress());
        String password = passwordEncoder.encode(companyFormDto.getPassword());
        company.setPassword(password);
        company.setPhoneNumber(companyFormDto.getPhoneNumber());
        company.setRole(Role.ENTERPRISE);
        company.setManager(companyFormDto.getManager());
        company.setRepresent(companyFormDto.getRepresent());
        company.setCompanyNumber(companyFormDto.getCompanyNumber());
        company.setCompanyFoundingDate(companyFormDto.getCompanyFoundingDate());

        return company;
    }

}
