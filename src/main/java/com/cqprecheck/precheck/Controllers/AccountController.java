package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/accounts")
public class AccountController {

    private AccountRepository accountRepository;
    private OrganizationRepository organizationRepository;

    public AccountController(AccountRepository accountRepository, OrganizationRepository organizationRepository){
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
    }

    @GetMapping(path = "/retrieve")
    public List<Account> retrieveAccountsInOrganization(@RequestBody Organization input) {
        return accountRepository.findByOrganization(input);
    }

    @PostMapping (path = "add-existing/organization")
    public Account createAccount(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Organization input){

        Account currentAccount = principal.getAccount();

        Optional<Organization> existingOrganization = organizationRepository.findById(input.getId());
        if(existingOrganization.isPresent()) {
            Organization organization = existingOrganization.get();
            organization.setId(organization.getId());
            currentAccount.setOrganization(organization);
            return accountRepository.save(currentAccount);
        }
        return new Account();
    }

    @PostMapping (path = "/new")
    public Account createAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<?> removeAccount(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Long organization_id){

        Account currentAccount = principal.getAccount();
        Optional<Organization> existingOrganization = organizationRepository.findById(organization_id);
        if(existingOrganization.isPresent()) {
            accountRepository.delete(currentAccount);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
