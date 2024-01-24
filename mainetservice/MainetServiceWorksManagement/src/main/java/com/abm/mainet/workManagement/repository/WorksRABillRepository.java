package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorksRABill;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public interface WorksRABillRepository extends CrudRepository<WorksRABill, Long> {

	/**
	 * used to get Ra Bill List By Project And Work Number
	 * 
	 * @param projId
	 * @param workId
	 * @param orgId
	 * @return
	 */
	@Query("select w from WorksRABill w where w.projId=:projId and w.workId=:workId and w.orgId=:orgId ")
	List<WorksRABill> getRaBillListByProjAndWorkNumber(@Param("projId") Long projId, @Param("workId") Long workId,
			@Param("orgId") Long orgId);

	/**
	 * used to update bill number and bill date by ra number
	 * 
	 * @param raNumber
	 * @param billNumber
	 * @param billDate
	 */
	@Modifying
	@Query("UPDATE WorksRABill wr set wr.raBillno =:raBillno, wr.raBillDate = CURRENT_DATE where wr.raId =:raId")
	void updateBillDetails(@Param("raId") Long raNumber, @Param("raBillno") String billNumber);

	/**
	 * used to delete Ra Tax Details
	 * 
	 * @param raTaxIds
	 */
	@Modifying
	@Query("Delete from WmsRaBillTaxDetails wt where wt.raTaxId in (:raTaxIds)")
	void deleteRaTaxDetails(@Param("raTaxIds") List<Long> raTaxIds);

	/**
	 * get RA Details By Ra Code
	 * 
	 * @param raCode
	 * @return WorksRABill
	 */
	@Query("SELECT wRa FROM WorksRABill wRa WHERE wRa.raCode= :raCode and wRa.orgId=:orgId ")
	WorksRABill getRaDetailsByRaCode(@Param("raCode") String raCode, @Param("orgId") Long orgId);

	/**
	 * update RA Status By Id
	 * 
	 * @param raId
	 * @param flag
	 */
	@Modifying
	@Query("UPDATE WorksRABill wRa SET wRa.raStatus =:flag where wRa.raId =:raId")
	void updateRaStatusById(@Param("raId") Long raId, @Param("flag") String flag);

	@Query("select ra from WorksRABill ra where ra.raId in (select max(r.raId) from WorksRABill r where r.workId=:workId and r.orgId=:orgId and r.raId < :raId group by r.orgId,r.workId)")
	WorksRABill getPreviousRaBillDetails(@Param("workId") Long workId, @Param("orgId") Long orgId,
			@Param("raId") Long currentRaId);

	/**
	 * used to get sum of all previous WithHeld Amount
	 * 
	 * @param workId
	 * @param orgId
	 * @param serialNo
	 * @return
	 */
	@Query("SELECT SUM(n.raTaxValue) from TbTaxMasEntity m, WmsRaBillTaxDetails n,WorksRABill o where m.taxId=n.taxId and n.worksRABill.raId=o.raId and o.raSerialNo < :serialNo and "
			+ "o.workId =:workId and m.taxDesc like 'Withheld%' and m.orgid=:orgId group by o.workId")
	BigDecimal getAllPreviousWithHeldAmount(@Param("workId") Long workId, @Param("orgId") Long orgId,
			@Param("serialNo") Long serialNo);

	
	  @Query("select r from WorksRABill r where  r.orgId =:orgId ")
   	public List<WorksRABill>getRaBillByRaCode(@Param("orgId") Long orgId);
	  
	 //To get all WorksRABill details which are not approved  
	 @Query("select r from WorksRABill r where  r.orgId =:orgId and r.raStatus='P'")
	 public List<WorksRABill> getAllRaDetailforSchedular(@Param("orgId") Long orgId);
	 
	 @Modifying
	 @Query("UPDATE WorksRABill wRa SET wRa.raBillType =:flag where wRa.raId =:raId")
	 void updateRaBillTypeById(@Param("raId") Long raId, @Param("flag") String flag);
}
