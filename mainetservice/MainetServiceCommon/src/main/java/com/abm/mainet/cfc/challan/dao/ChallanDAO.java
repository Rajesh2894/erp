package com.abm.mainet.cfc.challan.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.challan.domain.ChallanDetails;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author kavali.kiran
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class ChallanDAO extends AbstractDAO<ChallanMaster> implements IChallanDAO {

    private static final Logger logger = LoggerFactory.getLogger(ChallanDAO.class);
    
	 @Autowired
	 private IOrganisationDAO organisationDAO;

    @Override
    public ChallanMaster getChallanMasters(final Long challanNo, final Long applNo) {

        final Long paymentMode = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.PRIFIX_CODE.PAY_AT_BANK,
                PrefixConstants.PRIFIX.ONLINE_OFFLINE).getLookUpId();

        final StringBuilder challanAtBank = new StringBuilder(QueryConstants.QUERY.CHALLAN_AT_BANK);

        if (challanNo != null) {
            challanAtBank.append(QueryConstants.QUERY.CHALLAN_RESTRICTION);
        }
        if (applNo != null) {
            challanAtBank.append(QueryConstants.QUERY.SERVICE_RESTRICTION);
        }
        challanAtBank.append(QueryConstants.QUERY.CHALLAN_ORDER);
        final Query query = entityManager.createQuery(challanAtBank.toString());
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION,
                UserSession.getCurrent().getOrganisation().getOrgid())
                .setParameter(MainetConstants.TABLE_COLUMN.PAYMENT_MODE, paymentMode)
                .setParameter(MainetConstants.TABLE_COLUMN.RECEIVE_FLAG, "N")
                .setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_STATUS, MainetConstants.CHALLAN_STATUS.ACTIVE);

        if (challanNo != null) {
            query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_NO, String.valueOf(challanNo));
        }
        if (applNo != null) {
            query.setParameter(MainetConstants.TABLE_COLUMN.APPLICATION_ID, applNo);
        }

        final List<ChallanMaster> challanMasters = query.getResultList();
        if ((challanMasters != null) && !challanMasters.isEmpty()) {
            return challanMasters.get(0);
        }
        return null;
    }

    @Override
    public boolean getChallanMasterTransId(final String bankTransId) {
        final Query query = entityManager.createQuery("select s from ChallanMaster s where s.bankTransId=?1");
        query.setParameter(1, bankTransId);
        final List<ChallanMaster> masters = query.getResultList();
        if ((masters != null) && !masters.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public ChallanMaster getChallanMasterById(final Long challanid) {
        final Query query = entityManager.createQuery(
                "select s from ChallanMaster s where s.organisationId=?1 and s.challanId=?2 order by s.challanId desc");
        query.setParameter(1, UserSession.getCurrent().getOrganisation().getOrgid());
        query.setParameter(2, challanid);

        final List<ChallanMaster> masters = query.getResultList();
        if ((masters != null) && !masters.isEmpty()) {
            return masters.get(0);
        }
        return null;
    }

    @Override
    public ChallanMaster updateChallanDetails(final ChallanMaster challanMaster) {

        final Query query = entityManager.createNamedQuery("updateChallanVerification");

        query.setParameter("challanRcvdDate", new Date());

        query.setParameter("challanRcvdBy", UserSession.getCurrent().getEmployee().getEmpId());

        query.setParameter("challanRcvdFlag", "Y");

        query.setParameter("bankTransId", challanMaster.getBankTransId());

        query.setParameter("updatedBy", UserSession.getCurrent().getEmployee());

        query.setParameter("updatedDate", new Date());

        query.setParameter("lgIpMacUpd", Utility.getMacAddress());

        query.setParameter("challanNo", challanMaster.getChallanNo());

        query.setParameter("challanId", challanMaster.getChallanId());

        query.setParameter("organisationId", UserSession.getCurrent().getOrganisation().getOrgid());
        query.executeUpdate();

        return challanMaster;

    }

    @Override
    public ChallanMaster InvokeGenerateChallan(final ChallanMaster challanMaster) {

        entityManager.persist(challanMaster);
        return challanMaster;
    }

    @Override
    public int getdurationDays(final Long serviceId, final Long organisation) {
        final Query query = createQuery("select s from ServiceMaster s where s.orgid = ?1 and s.smServiceId = ?2 ");
        query.setParameter(1, organisation);
        query.setParameter(2, serviceId);
        final ServiceMaster master = (ServiceMaster) query.getSingleResult();
        if ((master != null) && (master.getComN1() != null)) {
            return master.getComN1().intValue();
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanDAO#saveChallanDetails(java.util.List)
     */
    @Override
    public boolean saveChallanDetails(final List<ChallanDetails> challanDetails) {
        for (final ChallanDetails details : challanDetails) {
            entityManager.persist(details);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanDAO#getChallanDetails(java.lang.String, long)
     */
    @Override
    public List<ChallanDetails> getChallanDetails(final String challanNo, final long orgid) {
        final Query query = createQuery("select c from ChallanDetails c where c.orgId = ?1 and c.challanNo = ?2 ");
        query.setParameter(1, orgid);
        query.setParameter(2, challanNo);
        final List<ChallanDetails> masters = query.getResultList();
        return masters;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanDAO#getchallanOfflineType(long, long)
     */
    @Override
    public ChallanMaster getchallanOfflineType(final long applicationId, final long orgid) {
        final Query query = createQuery(
                "select c from ChallanMaster c where c.organisationId = ?1 and c.apmApplicationId = ?2 order by c.challanId desc");
        query.setParameter(1, orgid);
        query.setParameter(2, applicationId);
        final List<ChallanMaster> masters = query.getResultList();
        if ((masters == null) || masters.isEmpty()) {
            return null;
        } else {
            return masters.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanDAO#getBankDetailsList(java.lang.Long, long)
     */
    @Override
    public List<String> getBankDetailsList(final Long bankAccID, final long organisation) {

        try {
            final List<String> bankList = new ArrayList<>();
            final StringBuilder stringBuilder = new StringBuilder(
                    "select b.bank,b.branch,e.baAccountNo,e.baAccountId,b.bankId from UlbBankMasterEntity u"
                            + ",BankMasterEntity b,BankAccountMasterEntity e   where  u.orgId= ?1"
                            + " and u.bankId=b.bankId and e.ulbBankId.ulbBankId=u.ulbBankId and e.baAccountNo=?2 ");
            final Query query = createQuery(stringBuilder.toString());
            query.setParameter(1, organisation);
            query.setParameter(2, bankAccID.toString());
            final Object[] bankMasters = (Object[]) query.getSingleResult();
            if ((bankMasters != null) && (bankMasters.length > 0)) {
                bankList.add(bankMasters[0].toString());
                bankList.add(bankMasters[1].toString());
                bankList.add(bankMasters[2].toString());
                bankList.add(bankMasters[3].toString());
                bankList.add(bankMasters[4].toString());
                return bankList;
            }
        } catch (final NoResultException nre) {
            logger.info("Norecord avilable in UlbBankMasterEntity for bankID:" + bankAccID.toString());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanDAO#getChargeDescByChgId(java.util.List)
     */
    @Override
    public List<TbTaxMasEntity> getChargeDescByChgId(final List<Long> ids, final long orgId) {
   //#120105-> code updated for dashboard data based on env prefix
        Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
        boolean moalFlag = false;
        final StringBuilder query = new StringBuilder(
                "select c from TbTaxMasEntity c where c.taxId in (?2) ");
        LookUp multiOrgDataList  = null;
     	try {
     		logger.error("Fetching MOAL prefix");
     		multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL,  MainetConstants.ENV, org);
     		logger.error("MOAL prefix is available");
     	}catch (Exception e) {
     		logger.error("No Prefix found for ENV(MOAL)");
 		}
     	
     	if(!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField()) && StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
     		query.append(" and c.orgid=:orgId");
     		moalFlag=true;
     		logger.error("Appending org id in query: "+ orgId);
     	}
     	query.append(" order by c.collSeq asc");
     	final Query queryData = entityManager.createQuery(query.toString());
        if (moalFlag == true) {
        	logger.error("Setting orgId parameter in  Query setParameter field: " + query.toString());
			queryData.setParameter("orgId", orgId);
		} 
        queryData.setParameter(2, ids);
        final List<TbTaxMasEntity> masters = queryData.getResultList();
        return masters;

    }

}
