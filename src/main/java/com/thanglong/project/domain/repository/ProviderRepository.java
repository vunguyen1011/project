package com.thanglong.project.domain.repository;

import com.thanglong.project.domain.model.ProviderModel;


import java.util.Optional;

public interface ProviderRepository {
    Optional<ProviderModel> findByName(String name);
}
