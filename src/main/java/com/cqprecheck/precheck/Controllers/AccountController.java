package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/add-account")
public class AccountController {

    private AccountRepository accountRepository;
    private OrganizationRepository organizationRepository;

    public AccountController(AccountRepository accountRepository, OrganizationRepository organizationRepository){
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
    }

    @PostMapping (path = "/existing/organization")
    public Account createAccount(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Long organization_id){

        Account currentAccount = principal.getAccount();

        Optional<Organization> existingOrganization = organizationRepository.findById(organization_id);
        if(existingOrganization.isPresent()) {
            Organization organization = existingOrganization.get();
            organization.setId(organization_id);
            currentAccount.setOrganization(organization);
            return accountRepository.save(currentAccount);
        }
        return new Account();
    }

    @PostMapping (path = "/new")
    public Account createAccount(@RequestBody String username, @RequestBody String password){

        Account account = new Account(username,password);
        return accountRepository.save(account);
    }
}
