package com.cqprecheck.precheck.Controllers;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Entity;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/entity")
public class EntityController {

    private OrganizationRepository organizationRepository;
    private EntityRepository entityRepository;

    public EntityController(OrganizationRepository organizationRepository, EntityRepository entityRepository){
        this.entityRepository = entityRepository;
        this.organizationRepository = organizationRepository;
    }

    @PostMapping(path = "/{organization_id}")
    public Entity addEntity(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Entity entity, @PathVariable Long organization_id){

        Account currentAccount = principal.getAccount();
        Organization currentAccountOrganization = currentAccount.getOrganization();
        Long currentAccountOrganizationId = currentAccountOrganization.getId();

        if(currentAccountOrganizationId.equals(organization_id)) {
            Entity savedEntity = entityRepository.save(entity);
            savedEntity.setOrganization(currentAccountOrganization);
            return savedEntity;
        }
        return null;
    }

    @GetMapping
    public List<Entity> getEntities(@AuthenticationPrincipal UserPrincipal principal) {

        Account currentAccount = principal.getAccount();
        Organization currentAccountOrganization = currentAccount.getOrganization();
        return entityRepository.findByOrganization(currentAccountOrganization);
    }

    @DeleteMapping(path = "/{entity_id}")
    public void deleteEntities(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long entity_id){

        Account currentAccount = principal.getAccount();
        Organization currentAccountOrganization = currentAccount.getOrganization();

        Optional<Entity> entity =  entityRepository.findById(entity_id);
        if(entity.isPresent()) {
            if (entityInOrganization(currentAccountOrganization, entity.get())) {
                entityRepository.delete(entity_id);
            }

        }
    }

    private Boolean entityInOrganization(Organization organization, Entity entity){
        return organization.getId().equals(entity.getOrganization().getId());
    }
}
