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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/organizations")
public class OrganizationController {


    private OrganizationRepository organizationRepository;
    private AccountRepository accountRepository;

    public OrganizationController(OrganizationRepository organizationRepository, AccountRepository accountRepository){
        this.organizationRepository = organizationRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public Organization create(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Organization organization){
        Organization savedOrganization = organizationRepository.save(organization);
        Account updatedAccount = principal.getAccount();
        updatedAccount.setOrganization_id(organization);
        accountRepository.save(updatedAccount);

        return savedOrganization;
    }

    @GetMapping
    public List<Organization> view(@AuthenticationPrincipal UserPrincipal principal){
        return StreamSupport.stream(this.organizationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/{organization}")
    public ResponseEntity<?> destroy(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long organization){
        if(principal.getAccount().getOrganization().getId().equals(organization)){
            this.organizationRepository.delete(organization);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Organization organization){
        if(principal.getAccount().getOrganization().getId().equals(organization.getId())){
            this.organizationRepository.save(organization);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/accounts")
    public List<Account> accounts(@AuthenticationPrincipal UserPrincipal principal){
        return accountRepository.findByOrganization(principal.getAccount().getOrganization());
    }
}
