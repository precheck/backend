package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Security.UserPrincipal;
import com.cqprecheck.precheck.Service.GoogleApiService;
import com.google.cloud.language.v1beta2.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/document/analyze")
public class DocumentController {
    private GoogleApiService service;
//    @Autowired
//    private Account account;

    public DocumentController() {
        this.service = new GoogleApiService();
        //this.account = account;
    }

    //(Entitiy e) -> { }
    @PostMapping
    public List<com.cqprecheck.precheck.Models.Entity> analyzeDocument(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String text){
        System.out.println(principal.getAccount().getUsername());
        return service.analyzeDocument(text)
                .stream()
                .map(com.cqprecheck.precheck.Models.Entity::new)
                .collect(Collectors.toList());
    }
}
