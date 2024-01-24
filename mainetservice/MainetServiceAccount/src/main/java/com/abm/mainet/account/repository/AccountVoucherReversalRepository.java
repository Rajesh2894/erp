package com.abm.mainet.account.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 *
 * @author Vivek.Kumar
 * @since 19 May 2017
 */
public interface AccountVoucherReversalRepository
        extends CrudRepository<TbServiceReceiptMasEntity, Serializable>, AccountVoucherReversalRepositoryCustom {

	@Query("SELECT deposit.tbComparamDetEntity3 FROM AccountDepositEntity deposit WHERE deposit.tbServiceReceiptMas.rmRcptid=:recptId AND deposit.orgid=:orgId")
	TbComparamDetEntity getDepositCpdStatusByRecptId(@Param("recptId") Long recptId, @Param("orgId") Long orgId);
	
	@Query("SELECT deposit.tbComparamDetEntity3 FROM AccountDepositEntity deposit WHERE deposit.accountBillEntryMaster.id=:billId AND deposit.orgid=:orgId")
	TbComparamDetEntity getDepositCpdStatusByBillId(@Param("billId") Long billId, @Param("orgId") Long orgId);
	
	@Query("SELECT deposit.tbComparamDetEntity3 FROM AccountDepositEntity deposit WHERE deposit.depReferenceNo=:referNo AND deposit.orgid=:orgId")
	TbComparamDetEntity getDepositCpdStatusByDepRefNo(@Param("referNo") String referNo, @Param("orgId") Long orgId);
}
