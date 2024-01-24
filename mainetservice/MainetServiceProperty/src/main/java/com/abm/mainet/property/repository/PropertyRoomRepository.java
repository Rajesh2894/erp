package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.PropertyRoomDetailEntity;

@Repository
public interface PropertyRoomRepository extends JpaRepository<PropertyRoomDetailEntity, Long> {

}
