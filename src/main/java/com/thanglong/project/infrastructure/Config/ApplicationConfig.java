package com.thanglong.project.infrastructure.Config;

import com.thanglong.project.infrastructure.Entity.Provider;
import com.thanglong.project.infrastructure.Entity.Role;
import com.thanglong.project.infrastructure.repository.ProviderJpaRepository;
import com.thanglong.project.infrastructure.repository.RoleJpaRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    ApplicationRunner applicationRunner(
            RoleJpaRepository roleRepository, ProviderJpaRepository providerRepository
    ){
        return args -> {
            if(roleRepository.count() == 0) {
                Role userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);

                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }
            if(providerRepository.count()==0){
                providerRepository.save(new Provider(1,"LOCAL"));
                providerRepository.save(new Provider(2,"GITHUB"));
                providerRepository.save(new Provider(3,"GOOGLE"));

            }

        };
    }
}
