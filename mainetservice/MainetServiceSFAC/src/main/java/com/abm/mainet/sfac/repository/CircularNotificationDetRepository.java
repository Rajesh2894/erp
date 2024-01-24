package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CircularNotificationMasterEntity;
import com.abm.mainet.sfac.domain.CircularNotiicationDetEntity;

@Repository
public interface CircularNotificationDetRepository extends JpaRepository<CircularNotiicationDetEntity, Long> {

	List<CircularNotiicationDetEntity> findByMasterEntity(CircularNotificationMasterEntity circularNotificationMasterEntity);

}
