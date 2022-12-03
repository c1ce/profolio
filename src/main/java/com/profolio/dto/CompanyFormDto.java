package com.profolio.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.PostLoad;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Getter @Setter
public class CompanyFormDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotEmpty(message = "분야는 필수 입력 값입니다.")
    @Positive(message = "숫자만입력해주세요") // 양수만 허용
    private String phoneNumber;

    @NotEmpty(message = "인사담당자는 필수 입력 값입니다.")
    private String manager;
    @NotEmpty(message = "대표이름는 필수 입력 값입니다.")
    private String represent;
    @NotEmpty(message = "사업자번호는 필수 입력 값입니다.")
    @Positive(message = "숫자만입력해주세요") // 양수만 허용
    private String companyNumber;

    @NotNull(message = "창업일자는 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate companyFoundingDate;

    //사업자번호

//    @PostLoad
    public void makeCompanyNumber(String companyNumber) {

        String regEx = "(\\d{3})(\\d{2})(\\d{5})";

        if(Pattern.matches(regEx, companyNumber)) {
            this.companyNumber = companyNumber.replaceAll(regEx, "$1-$2-$3");
        }

    }

}