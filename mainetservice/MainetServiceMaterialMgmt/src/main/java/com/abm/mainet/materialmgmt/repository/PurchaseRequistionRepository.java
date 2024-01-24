package com.abm.mainet.materialmgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.materialmgmt.domain.PurchaseRequistionDetEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionYearDetEntity;

@Repository
public interface PurchaseRequistionRepository extends JpaRepository<PurchaseRequistionEntity, Long> {

	@Query("select p from PurchaseRequistionEntity p where  p.orgId =:orgId order by p.prId desc")
	public List<PurchaseRequistionEntity> getAllPurReqDataByOrgId(@Param("orgId") Long orgId);

	@Query("select p from PurchaseRequistionEntity p where  p.orgId =:orgId and p.prId =:prId order by p.prId asc")
	public PurchaseRequistionEntity getAllPurReqDataByOrgIdAndPrId(@Param("orgId") Long orgId,
			@Param("prId") Long prId);

	@Modifying
	@Query("DELETE from  PurchaseRequistionEntity a where a.prId=:prId and a.orgId=:orgId")
	void deleteAllRecords(@Param("prId") Long prId, @Param("orgId") Long orgId);

	public List<PurchaseRequistionEntity> findByDepartmentAndOrgIdAndStatusOrderByPrId(
			@Param("department") Long department, @Param("orgId") Long orgId, @Param("status") String status);

	@Query("select p.prId from PurchaseRequistionEntity p where  p.prNo=:appNo and p.orgId=:orgId order by p.prId asc")
	public Long getDataById(@Param("appNo") String appNo, @Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE PurchaseRequistionEntity  pr  SET pr.status =:status WHERE pr.orgId =:orgId and pr.prNo=:appNo")
	void updatePurchaseStatus(@Param("orgId") Long orgId, @Param("appNo") String appNo, @Param("status") String status);

	@Modifying
	@Query("update PurchaseRequistionEntity pr set pr.status = :status, pr.updatedBy = :empId,pr.updatedDate = CURRENT_DATE where pr.prId in (:prIds)")
	public void updateStatusPurchaseReq(@Param("prIds") List<Long> prIds, @Param("status") String status,
			@Param("empId") Long empId);

	public List<PurchaseRequistionEntity> findByOrgIdAndPrIdIn(@Param("orgId") Long orgId,
			@Param("prIds") List<Long> prIds);

	@Query("select y from PurchaseRequistionYearDetEntity y where  y.orgId =:orgId and y.purchaseRequistionEntity.prId=:prId and y.yeActive='Y' order by y.yearId desc")
	public List<PurchaseRequistionYearDetEntity> getAllYearDetEntity(@Param("orgId") Long orgId,
			@Param("prId") Long prId);

	@Transactional
	@Modifying
	@Query("update PurchaseRequistionDetEntity de set de.status = '1', de.updatedBy = ?1,de.updatedDate = CURRENT_DATE where de.prdetId in ?2")
	public void updateStatusForDetails(Long empId, List<Long> prdetids);

	@Transactional
	@Modifying
	@Query("update PurchaseRequistionYearDetEntity de set de.yeActive = 'N', de.updatedBy = ?1,de.updatedDate = CURRENT_DATE where de.yearId in ?2")
	public void updateStatusForYears(Long empId, List<Long> years);


	@Query("select distinct p.prDate from PurchaseRequistionEntity p where  p.orgId =:orgId and p.prNo=:prNo")
	public Date findPrDateByOrgIdAndPrNo(@Param("orgId") Long orgId, @Param("prNo") String prNo);

	@Query("select p.prNo, p.prDate from PurchaseRequistionEntity p where p.orgId =:orgId and p.prId=:prId")
	public Object[] findPrNoAndDateByPrId(@Param("orgId") Long orgId, @Param("prId") Long prId);

	public PurchaseRequistionEntity findByPrNoAndOrgId(String prNo, Long orgId);
	
	/*Note : Keep this Native Query, Join with Works Table*/
	@Query(value = "SELECT s.storename, v.VM_VENDORID, v.VM_VENDORNAME from TB_VENDORMASTER v join TB_WMS_TENDER_WORK t "
			+ " join MM_STOREMASTER s join MM_REQUISITION pr where v.VM_VENDORID=t.TND_VENDER and t.PR_ID=pr.prid and "
			+ " pr.storeid=s.storeid and pr.prid=:prId and pr.ORGID=:orgId order by t.TNDW_ID desc limit 1", nativeQuery = true)
	public Object[] getRequisitionStoreVenderDetail(@Param("prId") Long prId, @Param("orgId") Long orgId);

	@Query(value = "select r.prNo, r.prDate, s.storeName, v.vmVendorname from PurchaseRequistionEntity r, StoreMaster s, "
			+ " TbAcVendormasterEntity v where r.prId=:prId and s.storeId=r.storeId and v.vmVendorid=:vendorId and "
			+ " s.orgId=:orgId ")
	public Object[] getRequisitionStoreVender(@Param("prId") Long prId, @Param("vendorId") Long vendorId, 
			@Param("orgId") Long orgId);

	@Query("SELECT pdet FROM PurchaseRequistionDetEntity pdet WHERE pdet.purchaseRequistionEntity.prId IN :prIds AND pdet.orgId = :orgId")
	List<PurchaseRequistionDetEntity> findPurchaseRequistionDetEntitiesByOrgIdAndPridIn(@Param("orgId") Long orgId, @Param("prIds") List<Long> prIds);

	
	@Query("select p.prId, p.prNo from PurchaseRequistionEntity p where p.orgId=:orgId and p.status=:status and "
			+ " p.prNo not in(select prId from PurchaseOrderDetEntity where orgId=:orgId and prId is not NULL)")
	public List<Object[]> getPrIdNumbersForPurchaseOrder(@Param("orgId") Long orgId, @Param("status") String status);
}
