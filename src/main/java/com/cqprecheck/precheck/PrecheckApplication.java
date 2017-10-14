package com.cqprecheck.precheck;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Entity;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Service.GoogleApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.cqprecheck.precheck")
public class PrecheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrecheckApplication.class, args);
	}

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           OrganizationRepository organizationRepository,
                           EntityRepository entityRepository) {
        Organization org = organizationRepository.save(new Organization("Org 1"));
        Entity entity = new Entity();
        entity.setName("Google");
        entity.setUrl("http://google.com");
        entity.setOrganization(org);
        entityRepository.save(entity);
        return (evt) -> Arrays.asList(
                "teddy,michael".split(","))
                .forEach(
                        a -> {
                            Account accountToSave = new Account(a, "password");
                            accountToSave.setOrganization_id(org);
                            Account account = accountRepository.save(accountToSave);
                        });
    }
}
