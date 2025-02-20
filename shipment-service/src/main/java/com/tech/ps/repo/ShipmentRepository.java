package com.tech.ps.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.ps.entity.ShipmentEntity;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
}
