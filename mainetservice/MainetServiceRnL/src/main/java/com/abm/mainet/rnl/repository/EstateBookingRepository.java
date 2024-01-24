package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.BookingResDTO;

/**
 * @author ritesh.patil
 *
 */
public interface EstateBookingRepository extends CrudRepository<EstateBooking, Long> {

    @Query("select rv.codIdRevLevel1 from EstateBooking b, EstatePropertyEntity p , EstateEntity e , LocationRevenueWZMapping rv where "
            +
            " e.esId = p.estateEntity.esId and e.locationMas.locId = rv.locationMasEntity.locId and p.propId=b.estatePropertyEntity.propId and "
            +
            " b.applicationId = ?1 and b.orgId = ?2")
    Long getApplicantInformationById(Long appId, Long orgId);

    @Modifying
    @Query("update TbCfcApplicationMstEntity cfcApp set cfcApp.apmApplSuccessFlag ='Y',cfcApp.apmApplClosedFlag='Y' where cfcApp.apmApplicationId=:appId")
    int updateAppliactionMst(@Param("appId") Long appId);

    List<EstateBooking> findByEstatePropertyEntityPropIdAndOrgIdAndBookingStatusNotIn(Long propId, Long orgId, String flag);

    @Query("select b.fromDate,b.toDate from  EstateBooking b where b.estatePropertyEntity.propId=?1 and b.shiftId=?2 and b.orgId =?3 and b.bookingStatus!='U'")
    List<Object[]> getFromAndToDates(Long propId, Long shiftId, Long orgId);

    @Query("select b.fromDate,b.toDate from  EstateBooking b where b.estatePropertyEntity.propId=?1 and b.orgId=?2 and b.bookingStatus!='U'")
    List<Object[]> getFromAndToDatesForGeneral(Long propId, Long orgId);

    @Query("select b.fromDate,b.toDate from  EstateBooking b where b.estatePropertyEntity.propId=?1 and b.shiftId in ?2 and b.orgId =?3 and b.bookingStatus='Y'")
    List<Object[]> getBookedFromAndToDates(Long propId, List<Long> shiftIds, Long orgId);

    @Query("select b.fromDate,b.toDate from  EstateBooking b where b.estatePropertyEntity.propId=?1 and b.orgId=?2 and b.bookingStatus='Y'")
    List<Object[]> getBookedFromAndToDatesForGeneral(Long propId, Long orgId);

    @Query("select b.shiftId from  EstateBooking b where b.estatePropertyEntity.propId=?1 and b.fromDate=?2 and b.orgId=?3 and b.bookingStatus NOT IN ('U', 'C','N') ")
    List<Long> getShitIdFromDate(Long propId, Date date, Long orgId);

    EstateBooking findByIdAndOrgId(Long bookId, Long orgId);

    List<EstateBooking> findByOrgIdAndBookingStatus(Long orgId, String bookingStatus);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus ='U', e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.id = ?1")
    int updateFreezeProperty(Long id, Long empId);

    @Query("select count(b) from EstateBooking b where b.orgId =?1 and b.estatePropertyEntity.propId = ?2)")
    Long findCountForProperty(Long orgId, Long propId);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus =:status, e.payFlag =:payFlag, e.updatedDate = CURRENT_DATE where e.applicationId =:applicationId")
    int updateBookingStatus(@Param("applicationId") Long applicationId, @Param("status") String status,
            @Param("payFlag") Boolean payFlag);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus = 'Y' ,e.updatedBy =:empId, e.updatedDate = CURRENT_DATE where e.applicationId =:applicationNo ")
    void updateEstateBookingStatus(@Param("applicationNo") Long applicationNo, @Param("empId") Long empId);

    @Query("select rd from TbSrcptFeesDetEntity rd where rd.rmRcptid in (select r.rmRcptid from TbServiceReceiptMasEntity r where r.apmApplicationId =?1 AND r.orgId =?2 )")
    List<TbSrcptFeesDetEntity> fetchChargesDetails(Long apmApplicationId, Long orgId);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus ='C', e.updatedBy = ?2,e.updatedDate = CURRENT_DATE,e.cancelReason =?3,e.cancelDate=CURRENT_DATE  where e.id = ?1")
    void updateCancelData(Long bookingId, Long empId, String cancelReason);

    @Query("select r from EstateBooking r where r.bookingNo=:bookingNo  and r.orgId=:orgId")
    EstateBooking getbookingIdbyBookingNo(@Param("bookingNo") String bookingNo, @Param("orgId") Long orgId);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus = 'C',e.cancelDate = CURRENT_DATE where e.bookingNo=:bookingNo and e.orgId=:orgId")
    void cancelEstateBooking(@Param("bookingNo") String bookingNo, @Param("orgId") Long orgId);

    @Query("select e.estatePropertyEntity.propId from EstateBooking e where e.id=:id and e.orgId=:orgId")
    int findAllDetailsByBookingId(@Param("id") Long id, @Param("orgId") Long orgId);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus = 'N',e.fromDate = :fromDate,e.toDate=:toDate where e.bookingNo=:bookingNo and e.orgId=:orgId")
    void enableBookingStatus(@Param("bookingNo") String bookingNo, @Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("select r from EstateBooking r where r.orgId=:orgId  and r.applicationId=:applicationId")
    EstateBooking getForChallanVerification(@Param("orgId") Long orgId, @Param("applicationId") Long applicationId);

    @Query("select s.name from EstatePropertyEntity s,EstateBooking p where s.propId = p.estatePropertyEntity.propId and p.applicationId=:applicationId and s.orgId=:orgId")
    String getPropertyName(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

    @Query("select cm.challanRcvdFlag,cm.apmApplicationId,b.bookingDate,b.bookingNo,b.fromDate,b.toDate from ChallanMaster cm ,"
            + " EstateBooking b where cm.apmApplicationId =b.applicationId and b.estatePropertyEntity.propId =:propId and b.orgId =:orgId and cm.challanRcvdFlag ='N' OR cm.challanRcvdFlag ='C'")
    List<Object[]> checkedReceiptValiadtion(@Param("orgId") Long orgId, @Param("propId") Long propId);

    List<EstateBooking> findByEstatePropertyEntityPropIdAndOrgIdAndBookingStatus(Long propId, Long orgId, String flag);

    @Modifying
    @Query("update EstateBooking e set e.bookingStatus =:bookingStatus,  e.updatedDate = CURRENT_DATE where e.applicationId in "
            + "(select p.referenceId from PaymentTransactionMas p where p.recvStatus != 'success'  AND p.orgId =:orgId and p.referenceId in (:referenceIds) )")
    void updateTheBookingStatusIfOnlineNotPaid(@Param("bookingStatus") String bookingStatus, @Param("orgId") Long orgId,
            @Param("referenceIds") List<String> referenceId);

    @Query("select case when count(eb)>0 THEN true ELSE false END from EstateBooking eb where eb.estatePropertyEntity.propId =:propId AND eb.toDate > CURRENT_DATE AND eb.bookingStatus='Y' AND eb.purpose in (:eventIds) AND eb.orgId =:orgId")
    Boolean checkPropertyBookedByEventId(@Param("propId") Long propId, @Param("eventIds") List<Long> eventIds,
            @Param("orgId") Long orgId);
    
    @Query("Select e from  EstateBooking e where  e.fromDate >= :fromDate and e.toDate <= :toDate and e.estatePropertyEntity.propId=:propId and e.orgId=:orgId")
   	List<EstateBooking> findByDatePropIdAndOrgId(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,  @Param("propId") Long propId , @Param("orgId") Long orgId);
	
	
	@Query("Select e from  EstateBooking e where e.applicationId=:apmApplicationId and e.orgId=:orgId")
   	List<EstateBooking> getBookingDetByApplIdAndOrgId(@Param("apmApplicationId") Long apmApplicationId,  @Param("orgId") Long orgId);
	
	@Query("Select e.estatePropertyEntity.propId from  EstateBooking e where e.applicationId=:apmApplicationId and e.orgId=:orgId")
	Long getPropIdByAppId(@Param("apmApplicationId") Long apmApplicationId,  @Param("orgId") Long orgId);
	       
	@Modifying
    @Query("update TbCfcApplicationMstEntity am set am.apmAppRejFlag =:approvalStatus ,am.apmApprovedBy =:approvedBy where am.apmApplicationId =:applicationId")
    void WaterTankerApprovalWorkflow(@Param("approvalStatus") String approvalStatus,
            @Param("approvedBy") Long approvedBy, @Param("applicationId") Long applicationId);

}
