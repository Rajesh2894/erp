/**
 * 
 */
package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;
import com.abm.mainet.materialmgmt.domain.GoodsreceivedNotesitemEntity;

/**
 * @author
 *
 */
@Repository
public interface GoodsReceivedNotesItemRepository extends JpaRepository<GoodsreceivedNotesitemEntity, Long>{
	
	
	
	
}
