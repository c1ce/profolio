package com.profolio.service;

import com.profolio.entity.Account;
import com.profolio.entity.Company;
import com.profolio.entity.Account;
import com.profolio.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService  implements UserDetailsService {
    private final AccountRepository accountRepository ;
    
    public Account saveAccount(Account account){
        validateDuplicateAccount(account);
        return accountRepository.save(account);
    }

    private void validateDuplicateAccount(Account account){
        Optional<Account> findAccount = accountRepository.findById(account.getEmail());
        if(findAccount.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findById(email);

        if(!account.isPresent()){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(account.get().getEmail())
                .password(account.get().getPassword())
                .roles(account.get().getRole().toString())
                .build();
    }
}
