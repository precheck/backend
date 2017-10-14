package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import com.cqprecheck.precheck.Service.GoogleApiService;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/document/analyze")
public class DocumentController {
    private GoogleApiService service;

    private EntityRepository entityRepository;

    public DocumentController(EntityRepository entityRepository) {
        this.service = new GoogleApiService();
        this.entityRepository = entityRepository;
    }


    @PostMapping
    public Map<String, List<com.cqprecheck.precheck.Models.Entity>> analyzeDocument(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String text){
        System.out.println(principal.getAccount().getUsername());
        List<com.cqprecheck.precheck.Models.Entity> entities = service.analyzeDocument(text)
                .stream()
                .filter((Entity e) -> e.getMentionsList()
                        .stream()
                        .filter((m) -> m.getType() == EntityMention.Type.PROPER).collect(Collectors.toList()).size() > 0)
                .map(com.cqprecheck.precheck.Models.Entity::new)
                .collect(Collectors.toList());

        Map<String, List<com.cqprecheck.precheck.Models.Entity>> entityMap = new HashMap<>();
        for(com.cqprecheck.precheck.Models.Entity entity : entities){
            entityMap.put(entity.getName(), entityRepository.findByName(entity.getName()));
        }

        return entityMap;
    }
}
