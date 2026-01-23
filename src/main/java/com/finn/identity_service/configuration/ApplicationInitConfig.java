package com.finn.identity_service.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finn.identity_service.entity.Role;
import com.finn.identity_service.entity.User;
import com.finn.identity_service.repository.RoleRepository;
import com.finn.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Init application...");

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                roleRepository.save(
                        Role.builder().name("USER").description("User role").build());

                Role adminRole = roleRepository.save(
                        Role.builder().name("ADMIN").description("Admin role").build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode(("admin")))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password admin, please change it");
            }
        };
    }
}
