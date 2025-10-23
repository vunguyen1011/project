package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderJpaRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider>  findByName(String name);
}
