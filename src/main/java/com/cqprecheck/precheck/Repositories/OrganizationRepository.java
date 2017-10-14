package com.cqprecheck.precheck.Repositories;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findByName(String name);
    Optional<Organization> findById(Long id);
}
