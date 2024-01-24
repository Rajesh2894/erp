package com.abm.mainet.common.master.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountDeposit;
import com.abm.mainet.common.domain.TbDepositEntity;

@Repository
public class TbDepositReceiptRepositoryImpl implements TbDepositReceiptRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<TbDepositEntity> getDepositRefundList(Long depNo, Long vmVendorid, Long cpdDepositType, Long sacHeadId,
            Date date, BigDecimal depositAmount, Long orgId) {

		List<TbDepositEntity> result = new ArrayList<>();
		String queryString = "select de from TbDepositEntity de where de.orgId =:orgId ";

		if (depNo != null) {
			queryString += " and de.depNo =:depNo";
		}
		if (vmVendorid != null) {
			queryString += " and de.vendorId =:vmVendorid";
		}
		if (cpdDepositType != null) {
			queryString += " and de.depType =:cpdDepositType";
		}
		if (sacHeadId != null) {
			queryString += " and de.sacHeadId =:sacHeadId";
		}
		if (date != null) {
			queryString += " and de.depDate =:date";
		}
		if (depositAmount != null) {
			queryString += " and de.depAmount =:depAmt";
		}

		queryString += " order by 1 desc";

		final Query query = entityManager.createQuery(queryString);

		query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, orgId);

		if (depNo != null) {
			query.setParameter(AccountDeposit.DEPNO, depNo);
		}
		if (vmVendorid != null) {
			query.setParameter(AccountDeposit.VM_VENDORID, vmVendorid);
		}
		if (cpdDepositType != null) {
			query.setParameter(AccountDeposit.CPD_DEPOSIT_TYPE, cpdDepositType);
		}
		if (sacHeadId != null) {
			query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID, sacHeadId);
		}
		if (date != null) {
			query.setParameter(MainetConstants.DATE, date);
		}
		if (depositAmount != null) {
			query.setParameter(AccountDeposit.DEP_AMT, depositAmount);
		}
		result = query.getResultList();
		
		return result;
	}

}
