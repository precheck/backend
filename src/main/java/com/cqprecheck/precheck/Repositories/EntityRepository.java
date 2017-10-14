package com.cqprecheck.precheck.Repositories;

import com.cqprecheck.precheck.Models.Entity;
import com.cqprecheck.precheck.Models.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository extends CrudRepository <Entity, Long> {
    List<Entity> findByName(String name);
    Optional<Entity> findById(Long id);
    List<Entity> findByOrganization(Organization organization);
}
