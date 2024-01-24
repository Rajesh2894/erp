package com.abm.mainet.common.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author Rajendra.Bhujbal
 *
 */

@Repository
public class CFCApplicationMasterDAO extends AbstractDAO<TbCfcApplicationMstEntity> implements ICFCApplicationMasterDAO {

    private static final Logger logger = LoggerFactory.getLogger(CFCApplicationMasterDAO.class);

    @Autowired
    private IOrganisationDAO organisationDAO;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.cfc.DAO.ICFCApplicationMasterDAO#getCFCApplicationMasterByApplicationId(long)
     */
    @Override
    public TbCfcApplicationMstEntity getCFCApplicationMasterByApplicationId(final long applicationId, final long orgId) {

        final Query query = entityManager.createQuery(
                "SELECT am FROM TbCfcApplicationMstEntity am WHERE am.apmApplicationId=?1 AND am.tbOrganisation.orgid=?2");
        query.setParameter(1, applicationId);
        query.setParameter(2, orgId);
        return (TbCfcApplicationMstEntity) query.getSingleResult();
    }

    @Override
    public long getServiceIdByApplicationId(final long applicationId, final long orgId) {

        final Query query = entityManager.createQuery(
                "SELECT am.tbServicesMst.smServiceId FROM TbCfcApplicationMstEntity am WHERE am.apmApplicationId=?1 AND am.tbOrganisation.orgid=?2");
        query.setParameter(1, applicationId);
        query.setParameter(2, orgId);

        final Object serviceID = query.getSingleResult();

        return (long) serviceID;
    }

    @Override
    public CFCApplicationAddressEntity getApplicantsDetailsDao(final long apmApplicationId) {
    	CFCApplicationAddressEntity entity = null;
    	final Query query = entityManager.createQuery("FROM CFCApplicationAddressEntity aa WHERE aa.apmApplicationId=?1 ");
        query.setParameter(1, apmApplicationId);

        try{
        	entity = (CFCApplicationAddressEntity) query.getSingleResult();
        }
        catch (NoResultException nre){
        	
        }
        return entity;
    }

    @Override
    public TbCfcApplicationMstEntity getCFCApplicationByApplicationId(final long applicationId, final long orgId) {
        TbCfcApplicationMstEntity entity = null;
        Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
        boolean moalFlag = false;
        final StringBuilder query = new StringBuilder(
                "SELECT am FROM TbCfcApplicationMstEntity am WHERE am.apmApplicationId=?1 ");
        LookUp multiOrgDataList = null;
        try {
            logger.error("Fetching MOAL prefix");
            multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL, MainetConstants.ENV, org);
            logger.error("MOAL prefix is available");
        } catch (Exception e) {
            logger.error("No Prefix found for ENV(MOAL)");
        }

        if (!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField())
                && StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
            query.append(" AND am.tbOrganisation.orgid=:orgId");
            moalFlag = true;
            logger.error("Appending org id in query: " + orgId);
        }
        final Query queryData = entityManager.createQuery(query.toString());
        queryData.setParameter(1, applicationId);
        if (moalFlag == true) {
            logger.error("Setting orgId parameter in  Query setParameter field: " + query.toString());
            queryData.setParameter("orgId", orgId);
        }

        try {
            entity = (TbCfcApplicationMstEntity) queryData.getSingleResult();
        } catch (final NoResultException nre) {
            // Ignore this because as per logic this is ok!
        }
        return entity;
    }

    /**
     *
     */
    @Override
    public boolean updateApprovedBy(final TbCfcApplicationMstEntity entity) {

        final Query query = entityManager.createQuery(
                "UPDATE TbCfcApplicationMstEntity am SET am.apmApprovedBy=:apmApprovedBy, am.apmApprovedDate=:apmApprovedDate,"
                        + "am.updatedBy=:updatedBy, am.updatedDate=:updatedDate, am.lgIpMacUpd=:lgIpMacUpd, am.apmApplClosedFlag=:apmApplClosedFlag WHERE am.apmApplicationId=:apmApplicationId");

        query.setParameter("apmApprovedBy", entity.getApmApprovedBy());
        query.setParameter("apmApprovedDate", entity.getApmApprovedDate());
        query.setParameter("updatedBy", entity.getUpdatedBy());
        query.setParameter("updatedDate", new Date());
        query.setParameter("lgIpMacUpd", entity.getLgIpMacUpd());
        query.setParameter("apmApplClosedFlag", "Y");
        query.setParameter("apmApplicationId", entity.getApmApplicationId());

        return query.executeUpdate() == 1 ? true : false;
    }

    @Override
    public boolean updateCFCApplicationMasterPaymentStatus(final long applicationId, final String paid, final long orgid) {
        TbCfcApplicationMstEntity applicationStatus = null;
        final Query query = entityManager.createQuery(
                "select s from TbCfcApplicationMstEntity s where s.tbOrganisation.orgid=?1 and s.apmApplicationId=?2");
        query.setParameter(1, orgid);
        query.setParameter(2, applicationId);
        try {
            applicationStatus = (TbCfcApplicationMstEntity) query.getSingleResult();
        } catch (final NoResultException nre) {
        }
        if (applicationStatus != null) {
            applicationStatus.setApmPayStatFlag(paid);
            try {
                entityManager.merge(applicationStatus);
                return true;
            } catch (final Exception e) {
                logger.info("Norecord avilable in TbCfcApplicationMstEntity for update:" + applicationId);
                return false;
            }
        }
        return false;
    }

    @Override
    public TbCfcApplicationMstEntity getApplicationMaster(final long applicationId, final Organisation orgnisation) {
        TbCfcApplicationMstEntity master = null;
        final Query query = createQuery(
                "select s from TbCfcApplicationMstEntity s where s.tbOrganisation = ?1 and s.apmApplicationId = ?2 ");
        query.setParameter(1, orgnisation);
        query.setParameter(2, applicationId);
        try {
            master = (TbCfcApplicationMstEntity) query.getSingleResult();
        } catch (final NoResultException nre) {
            logger.info("Norecord avilable in TbCfcApplicationMstEntity for:" + applicationId);
        }
        return master;
    }

    @Override
    public void updateCFCApplicationStatus(final String status, final long applicationId) {
        final String HQL = "UPDATE TbCfcApplicationMstEntity as s set s.apmChklstVrfyFlag = :apmChklstVrfyFlag, s.apmApplSuccessFlag = :apmApplSuccessFlag, s.apmApplClosedFlag = :apmApplClosedFlag   WHERE s.apmApplicationId = :apmApplicationId ";
        final Query query = createQuery(HQL);

        query.setParameter("apmApplicationId", applicationId);

        if (status.equals("A")) {
            query.setParameter("apmChklstVrfyFlag", "Y");
            query.setParameter("apmApplSuccessFlag", "");
            query.setParameter("apmApplClosedFlag", "");
        }

        if (status.equals("R")) {
            query.setParameter("apmChklstVrfyFlag", "N");
            query.setParameter("apmApplSuccessFlag", "N");
            query.setParameter("apmApplClosedFlag", "Y");
        }

        if (status.equals("H")) {
            query.setParameter("apmChklstVrfyFlag", "H");
            query.setParameter("apmApplSuccessFlag", "N");
            query.setParameter("apmApplClosedFlag", "");
        }

        query.executeUpdate();

    }

    @Override
    public TbCfcApplicationMstEntity getCFCApplicationByRefNoOrAppNo(String refNumber, Long applicationId, Long orgid) {
        final Query query = entityManager.createNativeQuery(
                "SELECT * FROM TB_CFC_APPLICATION_MST  WHERE (REF_NO=?1 OR APM_APPLICATION_ID=?2) AND ORGID=?3",
                TbCfcApplicationMstEntity.class);

        query.setParameter(1, refNumber);
        query.setParameter(2, applicationId);
        query.setParameter(3, orgid);
        return (TbCfcApplicationMstEntity) query.getSingleResult();
    }

    //#139419
    @Transactional
    public void updateIssuanceDataInCFCApplication(Long applicationId, Long issuedBy, Long orgId) {
        final Query query = createQuery(
                "UPDATE TbCfcApplicationMstEntity c SET  c.issuedDate=CURRENT_TIMESTAMP, c.issuedBy=?1  where c.apmApplicationId=?2 and c.tbOrganisation.orgid=?3");
        query.setParameter(1, issuedBy);
        query.setParameter(2, applicationId);
        query.setParameter(3, orgId);
        query.executeUpdate();
    }
    @Override
    public TbCfcApplicationMstEntity getCFCApplicationByOnlyApplicationId(final long applicationId) {
        TbCfcApplicationMstEntity entity = null;
        
        final StringBuilder query = new StringBuilder(
                "SELECT am FROM TbCfcApplicationMstEntity am WHERE am.apmApplicationId=?1 ");
       

        final Query queryData = entityManager.createQuery(query.toString());
        queryData.setParameter(1, applicationId);

        try {
            entity = (TbCfcApplicationMstEntity) queryData.getSingleResult();
        } catch (final NoResultException nre) {
            // Ignore this because as per logic this is ok!
        }
        return entity;
    }
    
    @Override
   	public List<TbCfcApplicationMstEntity> getApplicantList(Long orgid) {

   		final StringBuilder queryString = new StringBuilder();
   		queryString.append("select c from TbCfcApplicationMstEntity c, TbLoiMasEntity l  where c.tbOrganisation.orgid=:orgid and c.apmApplicationId=l.loiApplicationId");

   		Query query = this.createQuery(queryString.toString());
   		query.setParameter("orgid", orgid);
   		
   		
   		@SuppressWarnings("unchecked")
   		List<TbCfcApplicationMstEntity> entityList= query.getResultList();
   		return entityList;
   	}
    
    @Override
   	public Long getTransactionsList(Long orgid, Long deptId, Date daten) {
    	final StringBuilder queryString = new StringBuilder();
   		queryString.append("select count(1) from TbCfcApplicationMstEntity a,ServiceMaster b  where date(a.apmApplicationDate)=:daten and b.tbDepartment.dpDeptid=:deptId and a.tbServicesMst.smServiceId=b.smServiceId and a.tbOrganisation.orgid=:orgid group by a.tbOrganisation.orgid");
   		Query query = this.createQuery(queryString.toString());
   		query.setParameter("orgid", orgid);
   		query.setParameter("deptId", deptId);
   		query.setParameter("daten", daten);
   		Object entityList;
   		try {
   		 entityList= query.getSingleResult();
   		}catch(final NoResultException nre) {
   			logger.info("No record available for date" + daten);
   			return 0L;
   		}
		return Long.valueOf(entityList.toString());
   	}
    
   
    @Override
   	public Long getCountClosedApplications(Long orgid, Long deptId, String dateSet) {

   		final StringBuilder queryString = new StringBuilder();
   		queryString.append("select count(distinct a.APM_APPLICATION_ID) from tb_cfc_application_mst a,tb_services_mst b,TB_WORKFLOW_REQUEST c\r\n" + 
   				"where date(a.APM_APPLICATION_DATE)=:dateN and a.APM_APPLICATION_ID=c.APM_APPLICATION_ID and\r\n" + 
   				"CDM_DEPT_ID=:deptId and a.SM_SERVICE_ID=b.SM_SERVICE_ID and a.orgid=:orgid and c.STATUS = 'CLOSED'\r\n" + 
   				"group by a.orgid");
   		Query query = this.createNativeQuery(queryString.toString());
   		query.setParameter("orgid", orgid);
   		query.setParameter("deptId", deptId);
   		query.setParameter("dateN", dateSet);
   		Object entityList;
   		try {
   		 entityList=query.getSingleResult();
   		}catch(final NoResultException nre) {
   			logger.info("No record available for date" + dateSet);
   			return 0L;
   		}
		return Long.valueOf(entityList.toString());
   	}
    
    @Override
   	public Long getTodaysApprovedApplications(Long orgId, Long deptId, String dateSet) {

   		final StringBuilder queryString = new StringBuilder();
   		queryString.append("select count(distinct a.APM_APPLICATION_ID) from tb_cfc_application_mst a,tb_services_mst b,TB_WORKFLOW_REQUEST c\r\n" + 
   				"where date(APM_APPLICATION_DATE)=:dateN and\r\n" + 
   				"a.APM_APPLICATION_ID=c.APM_APPLICATION_ID and\r\n" + 
   				"CDM_DEPT_ID=:deptId and a.SM_SERVICE_ID=b.SM_SERVICE_ID and a.orgid=:orgId and c.STATUS = 'CLOSED'\r\n" + 
   				"group by a.orgid;");
   		Query query = this.createNativeQuery(queryString.toString());
   		query.setParameter("orgId", orgId);
   		query.setParameter("deptId", deptId);
   		query.setParameter("dateN", dateSet);
   		Object entityList;
		try {
   		 entityList=query.getSingleResult();
   		}catch(final NoResultException nre) {
   			logger.info("No record available for date" + dateSet);
   			return 0L;
   		}
		return Long.valueOf(entityList.toString());
   	}
    
    @Override
   	public List<Object[]> getPropTodaysMovedAppl(Long orgId, Long deptId, String dateSet) {

   		final StringBuilder queryString = new StringBuilder();
   		queryString.append("select tb7.LAST_DECISION,count(distinct a.apm_application_id ) counts\r\n" + 
   				"from tb_workflow_request a \r\n" + 
   				"inner join tb_workflow_mas tb5 on tb5.WF_ID = a.WORFLOW_TYPE_ID \r\n" + 
   				"inner join tb_workflow_task tb6 on tb6.apm_application_id = a.apm_application_id \r\n" + 
   				"inner join tb_workflow_request tb7 on tb7.apm_application_id = a.apm_application_id \r\n" + 
   				"inner join tb_cfc_application_mst  cfc on  cfc.apm_application_id=a.apm_application_id\r\n" + 
   				"-- inner join tb_services_mst sm on sm.SM_SERVICE_ID =cfc.SM_SERVICE_ID\r\n" + 
   				"-- inner join   tb_department dp on dp.DP_DEPTID=sm.CDM_DEPT_ID and DP_DEPTCODE='WT'\r\n" + 
   				"WHERE cfc.ORGID=:orgId and tb6.dp_deptid=:deptId\r\n" + 
   				" AND date(tb7.LAST_DATE_OF_ACTION) =:dateN\r\n" + 
   				"group by tb7.LAST_DECISION");
   		Query query = this.createNativeQuery(queryString.toString());
   		query.setParameter("orgId", orgId);
   		query.setParameter("deptId", deptId);
   		query.setParameter("dateN", dateSet);
   		List<Object[]> entityList=query.getResultList();
   		return entityList;
   	}
	
   	
}
