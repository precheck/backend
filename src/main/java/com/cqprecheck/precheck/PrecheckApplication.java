package com.cqprecheck.precheck;

import com.cqprecheck.precheck.Models.Account;
import com.cqprecheck.precheck.Models.Entity;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.AccountRepository;
import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Repositories.OrganizationRepository;
import com.cqprecheck.precheck.Service.GoogleApiService;
import com.cqprecheck.precheck.Storage.StorageProperties;
import com.cqprecheck.precheck.Storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
@ComponentScan(basePackages = "com.cqprecheck.precheck")
public class PrecheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrecheckApplication.class, args);
	}

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           OrganizationRepository organizationRepository,
                           EntityRepository entityRepository,
                           StorageService storageService) {
        Organization org = organizationRepository.save(new Organization("Org 1"));
        Entity entity = new Entity();
        entity.setName("Google");
        entity.setUrl("http://google.com");
        entity.setOrganization(org);
        entityRepository.save(entity);
        storageService.deleteAll();
        storageService.init();
        return (evt) -> Arrays.asList(
                "teddy,michael".split(","))
                .forEach(
                        a -> {
                            Account accountToSave = new Account(a, "password");
                            accountToSave.setOrganization(org);
                            Account account = accountRepository.save(accountToSave);
                        });
    }
}
