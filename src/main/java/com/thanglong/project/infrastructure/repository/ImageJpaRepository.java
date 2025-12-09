package com.thanglong.project.infrastructure.repository;

import com.thanglong.project.infrastructure.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image,Long> {
}
