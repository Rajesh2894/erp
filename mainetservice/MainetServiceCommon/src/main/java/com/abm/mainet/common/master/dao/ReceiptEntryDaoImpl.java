package com.abm.mainet.common.master.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.CFCApplicationStatusDaoImpl;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dao.RestDaoImpl;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Repository
public class ReceiptEntryDaoImpl extends RestDaoImpl<TbServiceReceiptMasEntity> implements IReceiptEntryDao {
	private static final Logger LOGGER = Logger.getLogger(ReceiptEntryDaoImpl.class);

    @Override
    public Iterable<TbServiceReceiptMasEntity> getReceiptDetail(Long orgId, BigDecimal rmAmount, Long rmRcptno,
            String rmReceivedfrom, Date rmDate, Long deptId) {

        String queryString = "select de from TbServiceReceiptMasEntity de where de.orgId =:orgId";

        if (rmAmount != null && rmAmount != BigDecimal.ZERO) {
            queryString += " and de.rmAmount =:rmAmount";
        }
        if (rmRcptno != null && rmRcptno != 0L) {
            queryString += " and de.rmRcptno =:rmRcptno";
        }
        if (rmReceivedfrom != null && !rmReceivedfrom.isEmpty()) {
            queryString += " and de.rmReceivedfrom =:rmReceivedfrom";
        }
        if (deptId != null) {
            queryString += " and de.deptId =:deptId";
        }
        if (rmDate != null) {
            queryString += " and de.rmDate =:rmDate";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, orgId);
       // query.setParameter("deptId", deptId);
        if (rmAmount != null && rmAmount != BigDecimal.ZERO) {
            query.setParameter("rmAmount", rmAmount);
        }
        if (rmRcptno != null && rmRcptno != 0L) {
            query.setParameter("rmRcptno", rmRcptno);
        }
        if (deptId != null && deptId != 0L) {
            query.setParameter("dpDeptId", deptId);
        }
        if (rmReceivedfrom != null && !rmReceivedfrom.isEmpty()) {
            query.setParameter("rmReceivedfrom", rmReceivedfrom);
        }
        if (rmDate != null) {
            query.setParameter("rmDate", rmDate);
        }
        List<TbServiceReceiptMasEntity> result = null;
        result = query.getResultList();
        return result;
    }

	@Override
	public Iterable<TbServiceReceiptMasEntity> getReceiptDet(TbServiceReceiptMasBean receiptMasBean) {
		List<TbServiceReceiptMasEntity> receiptMasEntity = new ArrayList<TbServiceReceiptMasEntity>();
		try {
			StringBuilder hql = new StringBuilder("select rm from TbServiceReceiptMasEntity rm where "
					);
			if(receiptMasBean.getOrgId() != null && receiptMasBean.getOrgId() != 0L) {
				hql.append(" rm.orgId ='" + receiptMasBean.getOrgId() + "'");
			}
			if(receiptMasBean.getDpDeptId() != null && receiptMasBean.getDpDeptId()  != 0L) {
				hql.append(" and rm.dpDeptId ='" + receiptMasBean.getDpDeptId() + "'");
			}
			if (receiptMasBean.getRmRcptno() != null && receiptMasBean.getRmRcptno() != 0L) {
				hql.append(" and rm.rmRcptno ='" + receiptMasBean.getRmRcptno() + "'");
			}
			if (receiptMasBean.getRmAmount() != null && !receiptMasBean.getRmAmount().isEmpty()) {
				hql.append(" and rm.rmAmount ='" + receiptMasBean.getRmAmount() + "'");
			}
			if (receiptMasBean.getRmReceivedfrom()  != null && !receiptMasBean.getRmReceivedfrom().isEmpty()) {
				hql.append(" and rm.rmReceivedfrom ='" + receiptMasBean.getRmReceivedfrom() + "'");
			}
			if (receiptMasBean.getRmDatetemp() != null && !receiptMasBean.getRmDatetemp().isEmpty()) {
				hql.append(" and rm.rmDate ='" + receiptMasBean.getRmDatetemp() + "'");
			}
			if (receiptMasBean.getApmApplicationId()!= null && receiptMasBean.getApmApplicationId() != 0L) {
				hql.append(" and rm.apmApplicationId ='" + receiptMasBean.getApmApplicationId() + "'");
			}
			if (receiptMasBean.getRmLoiNo()!= null && !receiptMasBean.getRmLoiNo().isEmpty()) {
				hql.append(" and rm.rmLoiNo ='" + receiptMasBean.getRmLoiNo() + "'");
			}
			if (receiptMasBean.getRefId() != null) {
				hql.append(" and rm.refId ='" + receiptMasBean.getRefId() + "'");
			}
			hql.append(" order by 1 desc");

			
			final Query query = this.createQuery(hql.toString());
			receiptMasEntity = (List<TbServiceReceiptMasEntity>) query.getResultList();
			
		}catch (Exception e) {
			LOGGER.info("Error occure while fetching the data from method TbServiceReceiptMasEntity " + e);
		}
		return receiptMasEntity;
	}

   //#141730
	@Override
	public TbServiceReceiptMasEntity getReceiptDetailByIds(Long rcptNo, Long orgId, Long deptId,
			String loiNo,Date rmDate,String rmAmount,Long appNo,Long refNo,String rmReceivedfrom,String date) {
		TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();
		try {
			StringBuilder hql = new StringBuilder("select rm from TbServiceReceiptMasEntity rm where ");
			if (orgId != null && orgId != 0L) {
				hql.append(" rm.orgId ='" + orgId + "'");
			}
			if (deptId != null && deptId != 0L) {
				hql.append(" and rm.dpDeptId ='" + deptId + "'");
			}
			if (rcptNo != null && rcptNo != 0L) {
				hql.append(" and rm.rmRcptno ='" + rcptNo + "'");
			}
			if (loiNo != null && StringUtils.isNotEmpty(loiNo)) {
				hql.append(" and rm.rmLoiNo ='" + loiNo + "'");
			}
			if (rmDate != null) {
				hql.append(" and rm.rmDate ='" + rmDate + "'");
			}
			if (rmAmount != null && StringUtils.isNotEmpty(rmAmount)) {
				hql.append(" and rm.rmAmount ='" + rmAmount + "'");
			}
			if (appNo != null && appNo != 0L) {
				hql.append(" and rm.apmApplicationId ='" + appNo + "'");
			}
			if (refNo != null && refNo != 0L) {
				hql.append(" and rm.refId ='" + refNo + "'");
			}
			if (rmReceivedfrom != null && StringUtils.isNotEmpty(rmReceivedfrom)) {
				hql.append(" and rm.rmReceivedfrom ='" + rmReceivedfrom + "'");
			}
            //Defect #145245
			if(StringUtils.isNotBlank(date)) {
				hql.append(" and rm.rmDate between "+date);
			}
			

			final Query query = this.createQuery(hql.toString());
			receiptMasEntity = (TbServiceReceiptMasEntity) query.getSingleResult();

		} catch (Exception e) {
			LOGGER.info(
					"Error occure while fetching the data from method TbServiceReceiptMasEntity in method getReceiptDetailByRcptNoAndDeptId"
							+ e);
		}
		return receiptMasEntity;
	}
   
	@Override
	public int getRecieptENTRYcount(Long orgId, Long userId, Long wardId) {
		try {
			StringBuilder hql = new StringBuilder(
					"select count(rm.rmRcptid) from TbServiceReceiptMasEntity rm where date(rm.createdDate)=cast(now() as date)");
			if (orgId != null && orgId != 0L) {
				hql.append(" and rm.orgId ='" + orgId + "'");
			}
			if (userId != null && userId != 0L) {
				hql.append(" and rm.createdBy ='" + userId + "'");
			}
			if (wardId != null && wardId != 0L) {
				hql.append(" and rm.rmCFfcCntrNo ='" + wardId + "'");
			}

			final Query query = this.createQuery(hql.toString());
			Long count = (Long) query.getSingleResult();
			return count.intValue();
		} catch (Exception e) {
			LOGGER.info(
					"Error occure while fetching the data from method TbServiceReceiptMasEntity in method getRecieptENTRYcount"
							+ e);
		}
		return 0;
	}
	
}
