package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.EntityHolder;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import com.cqprecheck.precheck.Service.GoogleApiService;
import com.cqprecheck.precheck.Storage.StorageService;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/document")
public class DocumentController {
    private GoogleApiService service;

    private EntityRepository entityRepository;

    private final StorageService storageService;

    public DocumentController(EntityRepository entityRepository, StorageService storageService) {
        this.service = new GoogleApiService();
        this.entityRepository = entityRepository;
        this.storageService = storageService;
    }


    @PostMapping(path = "/analyze")
    public Map<String, EntityHolder> analyzeDocument(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String text){
        Organization organization = principal.getAccount().getOrganization();
        List<com.cqprecheck.precheck.Models.Entity> entities = service.analyzeDocument(text)
                .stream()
                .filter((Entity e) -> e.getMentionsList()
                        .stream()
                        .filter((m) ->m.getType() == EntityMention.Type.PROPER).collect(Collectors.toList()).size() > 0)
                .map(com.cqprecheck.precheck.Models.Entity::new)
                .collect(Collectors.toList());

        Map<String, EntityHolder> entityMap = new HashMap<>();
        for(com.cqprecheck.precheck.Models.Entity entity : entities){
            EntityHolder holder = new EntityHolder(entityRepository.findByNameAndOrganization(entity.getName(), organization), entity.getLocations());
            entityMap.put(entity.getName(), holder);
        }

        return entityMap;
    }

    @PostMapping("/file")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);

    }
}
