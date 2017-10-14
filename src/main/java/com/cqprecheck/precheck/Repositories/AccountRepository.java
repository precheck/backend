package com.cqprecheck.precheck.Repositories;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository  extends CrudRepository<Account, Long>{
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
    Optional<Account> findByEmail(String email);
    List<Account> findByOrganization(Organization organization);
}
