/**
 * 
 */
package com.abm.mainet.adh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.InspectionEntryEntity;

/**
 * @author Anwarul.Hassan
 * @since 23-Oct-2019
 */
@Repository
public interface InspectionEntryRepository extends JpaRepository<InspectionEntryEntity, Long> {

}
