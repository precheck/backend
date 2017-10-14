package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Security.UserPrincipal;
import com.cqprecheck.precheck.Service.GoogleApiService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/document/analyze")
public class DocumentController {
    private GoogleApiService service;

    public DocumentController() {
        this.service = new GoogleApiService();
    }


    @PostMapping
    public List<com.cqprecheck.precheck.Models.Entity> analyzeDocument(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String text){
        System.out.println(principal.getAccount().getUsername());
        return service.analyzeDocument(text)
                .stream()
                .map(com.cqprecheck.precheck.Models.Entity::new)
                .collect(Collectors.toList());
    }
}
