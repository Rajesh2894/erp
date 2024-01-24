package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;

/**
 * The Interface UserRegistrationRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 19-Jun-2018
 */
@Repository
public interface SolidWasteConsumerRepository extends JpaRepository<SolidWasteConsumerMaster, Long> {

    @Modifying
    @Query("DELETE from  HomeComposeDetails a where a.swHomeCompId in (:swHomeCompId)")
    void deleteAllRecords(@Param("swHomeCompId") List<Long> swHomeCompId);

}
