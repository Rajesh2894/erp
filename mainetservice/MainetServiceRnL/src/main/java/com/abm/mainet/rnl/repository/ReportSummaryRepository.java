package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.dto.ReportDTO;

public interface ReportSummaryRepository extends PagingAndSortingRepository<TbServiceReceiptMasEntity, Long> {

    @Query("select NEW com.abm.mainet.rnl.dto.ReportDTO(rl.contId AS contractId, sum(rl.balanceAmount) AS total) from RLBillMaster rl where rl.paidFlag = 'N' AND rl.dueDate <= :date AND rl.orgId = :orgId group by rl.contId")
    List<ReportDTO> fetchOutstandingBillByDate(@Param("date") Date date, @Param("orgId") Long orgId);

    @Query("select CM.estatePropertyEntity.name, C.contNo, CM.estateEntity.address from EstateContractMapping CM  , ContractMastEntity C where CM.contractMastEntity= C.contId AND CM.contractMastEntity.contId = :contId AND CM.orgId = :orgId")
    Object[] getContractPropertyData(@Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("select v.vmVendorname from TbAcVendormasterEntity v where v.vmVendorid in (select vmVendorid from ContractPart2DetailEntity where contId.contId = :contId) AND v.orgid =:orgId ")
    List<String> getContractorNames(@Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("select cp.contId.contId,cp.contId.contNo ,v.vmVendorname from ContractPart2DetailEntity cp ,TbAcVendormasterEntity v where cp.vmVendorid = :vmVendorid  AND cp.vmVendorid = v.vmVendorid  AND cp.orgId =:orgId ")
    List<Object[]> fetchContractByVendor(@Param("vmVendorid") Long vmVendorid, @Param("orgId") Long orgId);

    @Query("from ContractPart2DetailEntity cp where cp.contId.contId = :contId AND cp.vmVendorid = :vmVendorid")
    ContractPart2DetailEntity getContractPart2DetailEntity(@Param("contId") Long contId, @Param("vmVendorid") Long vmVendorid);

    @Query("from ContractMastEntity c where c.contId = ?1 and c.orgId = ?2")
    ContractMastEntity getContractMas(Long contId, Long orgId);

    @Query("select r from RLBillMaster r ,TbTaxMasEntity t where r.taxId =  t.taxId and r.contId = ?1 and r.orgId = ?2 ")
    List<RLBillMaster> findContractInstallmentByIds(Long contId, Long orgId);

    @Query("select rl.contId , sum(rl.amount) from RLBillMaster rl where  rl.dueDate >= :financeFromDate AND rl.dueDate <= :financeToDate AND rl.contId =:contId AND rl.orgId = :orgId group by rl.contId")
    Object[] getCurrentAmtBetweenDate(@Param("financeFromDate") Date financeFromDate, @Param("financeToDate") Date financeToDate,
            @Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("from ContractPart2DetailEntity cp where cp.contId.contId = :contId AND cp.orgId = :orgId")
    List<ContractPart2DetailEntity> getContractPart2DataByIds(@Param("contId") Long contId, @Param("orgId") Long orgId);
    
    @Query("from ContractInstalmentDetailEntity i where i.contId.contId =?1 and i.conttActive =?2 and i.conitId NOT IN (select conitId from RLBillMaster) order by i.conitId asc")
	List<ContractInstalmentDetailEntity> finAllRecordsNotInBill(Long contId, String flag);
    
    @Query("select e.esId,e.code,e.nameEng,e.nameReg from EstateEntity e where e.orgId =?1 and e.isActive = 'Y' order by e.nameEng asc")
	List<Object[]> findEstateRecordsForProperty(long orgId);
	
	 @Query("select v.vmGstNo from TbAcVendormasterEntity v where v.vmVendorid in (select vmVendorid from ContractPart2DetailEntity where contId.contId = :contId) AND v.orgid =:orgId ")
	 String getContractorGSTNo(@Param("contId") Long contId, @Param("orgId") Long orgId);
	 
	 @Query("select v.mobileNo from TbAcVendormasterEntity v where v.vmVendorid in (select vmVendorid from ContractPart2DetailEntity where contId.contId = :contId) AND v.orgid =:orgId ")
	 String getContractorMobileNo(@Param("contId") Long contId, @Param("orgId") Long orgId);
}
