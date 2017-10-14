package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.DefaultToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountController {

    private AccountRepository accountRepository;
    private OrganizationRepository organizationRepository;

    public AccountController(AccountRepository accountRepository, OrganizationRepository organizationRepository){
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
    }

    @GetMapping(path = "/retrieve")
    public List<Account> retrieveAccountsInOrganization(@AuthenticationPrincipal UserPrincipal principal) {
        return accountRepository.findByOrganization(principal.getAccount().getOrganization());
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

    @DeleteMapping(path = "/remove")
    public ResponseEntity<?> removeAccount(@AuthenticationPrincipal UserPrincipal principal, @Autowired ConsumerTokenServices consumerTokenServices, HttpServletRequest request){
        Account currentAccount = principal.getAccount();
        String authHeader = request.getHeader("Authorization");
        Optional<Organization> existingOrganization = organizationRepository.findById(currentAccount.getOrganization().getId());
        if(existingOrganization.isPresent()) {
            accountRepository.delete(currentAccount);
            consumerTokenServices.revokeToken(authHeader.replace("Bearer", "").trim());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
