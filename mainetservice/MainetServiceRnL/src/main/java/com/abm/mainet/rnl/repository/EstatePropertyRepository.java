package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.rnl.domain.EstatePropertyEntity;
import com.abm.mainet.rnl.domain.EstatePropertyEvent;
import com.abm.mainet.rnl.domain.EstatePropertyShift;

/**
 * @author ritesh.patil
 *
 */
public interface EstatePropertyRepository extends CrudRepository<EstatePropertyEntity, Long> {

    @Query("select p.propId,p.name,p.unitNo,p.occupancy,p.usage,p.floor,p.totalArea from EstatePropertyEntity p where p.orgId =?1 and p.status = ?2")
    List<Object[]> findAllRecords(Long orgId, Character activeFlag);

    @Query("select p.propId,p.name,p.unitNo,p.occupancy,p.usage,p.floor,p.totalArea from EstatePropertyEntity p where p.orgId =?1 and p.status = 'Y' and  p.estateEntity.esId = ?2)")
    List<Object[]> findFilterRecords(Long orgId, Long estateId);

    @Modifying
    @Query("update EstatePropertyEntity e set e.status = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.propId = ?3")
    void deleteRecord(Character flag, Long empId, Long id);

    @Modifying
    @Query("update EstatePropertyDetails e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.estatePropertyEntity.propId = ?3")
    void deleteDetails(Character flag, Long empId, Long id);

    @Modifying
    @Query("update EstatePropertyDetails e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.propDetId in ?3")
    void deleteRecordDetails(Character flag, Long empId, List<Long> id);

    @Query("select a.category from EstateEntity a, EstatePropertyEntity b where a.esId=b.estateEntity.esId and a.esId = ?1")
    List<Character> checkPropertyAgainstEstate(Long estateId);

    @Query("select p from EstatePropertyEntity p,TbComparamMasEntity c ,TbComparamDetEntity cd where "
            + "p.occupancy = cd.cpdId and cd.cpdValue = ?1 and c.cpmPrefix = ?2 and p.orgId =?3")
    List<EstatePropertyEntity> getAllRentedProperties(String subCategoryName, String prefixName, Long orgId);

    @Query("select p from EstatePropertyEntity p where p.estateEntity.esId "
            + "in(select e.esId from EstateEntity e where e.type2= ?1 and e.orgId= ?5)"
            + " and p.propId in (select ev.estatePropertyMasterEvent.propId from EstatePropertyEvent ev "
            + "where ev.propEvent= ?2 and ev.propAllowFlag='A' and ev.eventStatus ='Y' and ev.orgId= ?5) and p.orgId= ?5 and p.propCapacity >= ?3 and p.propCapacity <= ?4")
    List<EstatePropertyEntity> getFilteredRentedProperties(Integer categoryId, Long eventId, Long capacityFrom,
            Long capacityTo, Long orgId);

    @Query("select p from EstatePropertyEntity p join p.estateEntity e where p.propId=?1")
    EstatePropertyEntity findPropertyForBooking(Long propId);

    @Query("select p.name,p.propLatitude,p.propLongitude from  EstatePropertyEntity p where p.propId=?1 and p.orgId=?2")
    List<String[]> getLatLang(Long propId, Long orgId);

    @Query("select p.propId,p.name, p.status from EstatePropertyEntity p,TbComparamMasEntity c ,TbComparamDetEntity cd where "
            + "p.occupancy = cd.cpdId and p.status = 'Y' and p.orgId =?1 and p.estateEntity.esId = ?2 and cd.cpdValue = ?3 and c.cpmPrefix = ?4")
    List<Object[]> findPropertiesForEstate(Long orgId, Long esId, String subCategoryName, String prefixName);

    @Modifying
    @Query("UPDATE EstatePropertyAminity a SET a.amiFacStatus = 'N' where propAmenityId in :removeAminities ")
    void updateAminityRemoveFlag(@Param("removeAminities") List<Long> removeAminities);

    @Modifying
    @Query("UPDATE EstatePropertyAminity f SET f.amiFacStatus = 'N' where propAmenityId in :removeFacility ")
    void updateFacilityRemoveFlag(@Param("removeFacility") List<Long> removeFacility);

    @Modifying
    @Query("UPDATE EstatePropertyEvent e SET e.eventStatus = 'N' where propEventId in :removeEvent")
    void updateEventRemoveFlag(@Param("removeEvent") List<Long> removeEvent);

    @Modifying
    @Query("UPDATE EstatePropertyShift s SET s.shiftStatus = 'N' where propShifId in :removeShift")
    void updateShiftRemoveFlag(@Param("removeShift") List<Long> removeShift);

    @Query("select p.propId,p.code,p.name,p.unitNo,p.occupancy,p.usage,p.floor,p.totalArea from EstatePropertyEntity p where p.orgId =?1 ")
    List<Object[]> findAllRecords(Long orgId);
    
    @Query("SELECT ps from EstatePropertyShift ps where ps.propShift =:propShift and ps.estatePropertyMasterShift.propId =:propId and ps.orgId =:orgId and  shiftStatus = 'Y' ")
    EstatePropertyShift getPropShiftTiming(@Param("propShift") Long propShift, @Param("propId") Long propId,
            @Param("orgId") Long orgId);

    @Query("SELECT pe from EstatePropertyEvent pe where pe.estatePropertyMasterEvent.propId =:propId and pe.propAllowFlag = 'A' AND pe.eventStatus='Y'")
    List<EstatePropertyEvent> getAllPropEventDetails(@Param("propId") Long propId);

    @Query("SELECT p from EstatePropertyEntity p where p.propId =:propId and p.orgId =:orgId ")
    EstatePropertyEntity findAmenitiesAndFacilities(@Param("propId") Long propId, @Param("orgId") Long orgId);

    @Query("select distinct s.propEvent,p.orgId,r.cpdDesc,r.cpdDescMar from EstateEntity p,EstatePropertyEntity q,EstatePropertyEvent s,TbComparamDetEntity r "
            + "where p.type2=:categoryId and s.propEvent=r.cpdId and p.orgId=:orgId and p.esId=q.estateEntity.esId and q.propId=s.estatePropertyMasterEvent.propId and s.propAllowFlag='A' ")
    List<Object[]> getEventOrPropertyId(@Param("categoryId") Integer categoryId, @Param("orgId") Long orgId);

    @Query("SELECT et.propEvent from EstatePropertyEvent et where et.propEventId in :propEventId ")
    List<Long> fetchEventIds(@Param("propEventId") List<Long> propEventId);

    @Query("SELECT p from EstatePropertyEntity p where p.orgId =:orgId ")
	List<EstatePropertyEntity> getAllPropertyDetByOrgId( @Param("orgId") Long orgId);
    
    @Query("SELECT  p.propId from EstatePropertyEntity p where p.name=:propertyName and  p.orgId =:orgId ")
	Long getPropertyIdByName(@Param("propertyName") String propertyName, @Param("orgId") Long orgId);
    
    @Query("select count(e) from EstatePropertyEntity e where e.estateEntity.esId=:esId and e.orgId=:orgId")
	Long getEstateCount(@Param("esId") Long esId,@Param("orgId") Long orgId);
    
    @Query("select p.propId,p.code,p.name,p.unitNo,p.occupancy,p.usage,p.floor,p.totalArea from EstatePropertyEntity p where p.orgId =?1 and  p.estateEntity.esId = ?2 ")
    List<Object[]> findAllFilterRecords(Long orgId, Long estateId);

    @Query("select p.propId,p.code,p.name,p.unitNo,p.occupancy,p.usage,p.floor,p.totalArea from EstatePropertyEntity p where p.orgId =?1 and  p.estateEntity.esId = ?2 and p.status = 'Y' ")
	List<Object[]> findAllFilterRecordsWithActiveStatus(long orgId, Long estatePropGridId);

	@Modifying
	@Transactional
    @Query("update EstatePropertyEntity p set p.flag =:flag where p.propId=:propId")
	void updateStatus(@Param("flag") Character flag,@Param("propId") Long propId);

	 @Query("select p from EstatePropertyEntity p where p.estateEntity.esId "
	            + "in(select e.esId from EstateEntity e where e.type2= ?1 and e.orgId= ?3)"
	            + " and p.propId in (select ev.estatePropertyMasterEvent.propId from EstatePropertyEvent ev "
	            + "where ev.propEvent= ?2 and ev.propAllowFlag='A' and ev.eventStatus ='Y' and ev.orgId= ?3) and p.orgId= ?3 and p.flag='O' ")
	List<EstatePropertyEntity> getFilteredWaterTanker(Integer categoryId, Long eventId, long orgid);
	 
	@Query("SELECT  p.propId from EstatePropertyEntity p where p.code=:propertyNo and  p.orgId =:orgId ")
	Long getPropertyIdByPropNo(@Param("propertyNo") String propertyNo, @Param("orgId") Long orgId);
	
	 @Query("SELECT  ps.propShift from EstatePropertyShift ps where ps.estatePropertyMasterShift.propId =:propId and ps.orgId =:orgId and  shiftStatus = 'Y' ")
	 List<Long> getPropShiftInfo(@Param("propId") Long propId,
	            @Param("orgId") Long orgId);
	
}
