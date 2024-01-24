/**
 * 
 */
package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.rnl.domain.RLBillMaster;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public interface TransferOfLeaseRepository extends JpaRepository<ContractMastEntity, Long>{

	@Query("select s from ContractMastEntity s where s.contNo=:contNo and s.contDept=:contDept and s.orgId=:orgId")
	ContractMastEntity getContractDetailsByContractNo(@Param("contNo") String contNo,@Param("contDept") Long contDept, @Param("orgId") Long orgId);
	
	@Query("select s from ContractDetailEntity s where s.contId.contId=:contId and s.orgId=:orgId")
	List<ContractDetailEntity> getContractDetailsListData(@Param("contId") Long contId,@Param("orgId") Long orgId);
	
	@Query("select s from ContractPart2DetailEntity s where s.contId.contId=:contId and s.orgId=:orgId")
	List<ContractPart2DetailEntity> getContractPart2DetailsListData(@Param("contId") Long contId,@Param("orgId") Long orgId);

	@Modifying
	@Transactional
	@Query("update ContractInstalmentDetailEntity e set e.conitValue =:appreciationAmount "
			+ "where e.conitDueDate >=:currentDate and e.contId.contId =:contId and e.orgId =:orgId")
	void updateContractInstallmentDetails(@Param("appreciationAmount")Double appreciationAmount, @Param("currentDate")Date currentDate,
			@Param("contId") Long contId, @Param("orgId") Long orgId);
	
	@Modifying
	@Transactional
	@Query("update RLBillMaster e set e.balanceAmount =:appreciationAmount "
			+ "where e.paidFlag='N' and e.dueDate >=:currentDate and e.contId.contId =:contId and e.orgId =:orgId")
	void updateRLBillMaster(@Param("appreciationAmount")Double appreciationAmount, @Param("currentDate")Date currentDate,
			@Param("contId") Long contId, @Param("orgId") Long orgId);
	
	@Modifying
	@Transactional
	@Query("update ContractPart2DetailEntity e set e.vmVendorid =:vmVendorid "
			+ "where e.contId.contId =:contId and e.orgId =:orgId")
	void updateContractPart2Details(@Param("vmVendorid")Long vmVendorid, 
			@Param("contId") Long contId, @Param("orgId") Long orgId);
	
	@Query("SELECT am FROM  ContractInstalmentDetailEntity am WHERE am.conitDueDate >=:currentDate "
			+ "and am.contId.contId =:contId and am.orgId=:orgId")
	List<ContractInstalmentDetailEntity> findContractInstalmentDetailByOrgIdAndContractId(@Param("currentDate")Date currentDate,
			@Param("contId") Long contId,@Param("orgId") Long orgId);

	@Query("SELECT am FROM  RLBillMaster am WHERE am.paidFlag='N' and am.dueDate >=:currentDate and"
			+ " am.contId.contId =:contId and am.orgId=:orgId")
	List<RLBillMaster> findRLBillMasterByOrgIdAndContractId(@Param("currentDate")Date currentDate, 
			@Param("contId") Long contId,@Param("orgId") Long orgId);
	
	@Query("SELECT am FROM  ContractPart2DetailEntity am WHERE am.contId.contId =:contId and am.orgId=:orgId")
	List<ContractPart2DetailEntity> findContractPart2DetailEntityByOrgIdAndContractId(@Param("contId") Long contId,@Param("orgId") Long orgId);

	@Query("select SUM(balanceAmount) from RLBillMaster s where s.contId=:contId and s.orgId=:orgId")
	Double getBalanceAmount(@Param("contId") Long contId, @Param("orgId") Long orgId);
}
