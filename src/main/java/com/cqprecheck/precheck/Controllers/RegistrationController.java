package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/register")
public class RegistrationController {

    private AccountRepository accountRepository;

    public RegistrationController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }
}
