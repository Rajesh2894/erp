/**
 *
 */
package com.abm.mainet.common.integration.dms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author rajendra.bhujbal
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class CFCAttachmentDAO extends AbstractDAO<CFCAttachment> implements ICFCAttachmentDAO {

	@Autowired
	private IOrganisationDAO organisationDAO;

	@Override
	public List<CFCAttachment> findAllAttachmentsByAppId(final long appId, final String docStatus, final long orgId) {
		// Defect#122930 changes done due to orgId dependancy on Doon portal
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);

		Query query = null;
		StringBuilder qryBuilder = new StringBuilder();
		if (docStatus.isEmpty()) {
			qryBuilder = new StringBuilder(QueryConstants.QUERY.CFC_ATTACHMENT_QUERY_RESUBMIT);

		} else {
			qryBuilder = new StringBuilder(QueryConstants.QUERY.CFC_ATTACHMENT_QUERY_BY_STATUS);

		}
		if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_DSCL)) {
			qryBuilder.append("  and d.orgid=:orgId ");
		}

		qryBuilder.append(" group by d.applicationId,d.clmId) order by c.clmSrNo");

		query = createQuery(qryBuilder.toString());
		if (!docStatus.isEmpty()) {
			query.setParameter("clmAprStatus", docStatus);
		}
		query.setParameter("applicationId", appId);
		if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_DSCL)) {
			query.setParameter("orgId", orgId);
		}

		final List<Object[]> attechmentList = query.getResultList();
		if (attechmentList.isEmpty()) {
			throw new RuntimeException("No Result Found");
		} else {
			final List<CFCAttachment> cfcattachment = transformResult(attechmentList);
			return cfcattachment;
		}
	}

	private List<CFCAttachment> transformResult(final List<Object[]> rejectedAttach) {

		final List<CFCAttachment> cfcattachment = new ArrayList<>();
		CFCAttachment attach = null;
		for (final Object[] obj : rejectedAttach) {
			attach = new CFCAttachment();

			attach.setAttId((long) obj[0]);
			attach.setUserId((long) obj[1]);
			attach.setOrgid((long) obj[2]);
			attach.setAttFname((String) obj[3]);
			attach.setAttPath((String) obj[4]);
			attach.setAttBy((String) obj[5]);
			attach.setAttFromPath((String) obj[6]);
			attach.setClmId((Long) obj[7]);
			attach.setClmDesc((String) obj[8]);
			attach.setClmStatus((String) obj[9]);
			attach.setClmSrNo((Long) obj[10]);
			attach.setChkStatus((Long) obj[11]);
			attach.setServiceId((Long) obj[13]);
			attach.setApplicationId((Long) obj[14]);
			attach.setAttDate((Date) obj[15]);
			attach.setDept((Long) obj[16]);
			if (null != obj[17]) {
				attach.setClmRemark(obj[17].toString());
			}
			if (null != obj[18]) {
				attach.setClmDescEngl((String) obj[18]);
			}
			if (null != obj[19]) {
				attach.setMandatory((String) obj[19]);
			}
			if (null != obj[20]) {
				attach.setDmsDocId((String) obj[20]);
			}
			if(null != obj[21]) {
			    attach.setDocDescription((String) obj[21]);
			}
			cfcattachment.add(attach);
		}
		return cfcattachment;
	}

	@Override
	public void updateCFCAttachment(final List<CFCAttachment> list, final String status) {
		CFCAttachment attachment = null;

		for (final ListIterator<CFCAttachment> j = list.listIterator(); j.hasNext();) {
			attachment = j.next();

			if (!status.isEmpty() && !status.equals("A")) {
				if ((attachment.getClmAprStatus() == null)
						|| attachment.getClmAprStatus().equals(MainetConstants.BLANK)) {
					attachment.setClmAprStatus("N");
				}
			} else {
				if ((attachment.getClmAprStatus() == null)
						|| attachment.getClmAprStatus().equals(MainetConstants.BLANK)) {
					attachment.setClmAprStatus("Y");
				}
			}
			entityManager.merge(attachment);
		}

	}

	@Override
	public List<Object> getEmployeeDetails(final Long appid) {
		final String queryString = "select cm.apmTitle,cm.apmFname,cm.apmLname,a.apaMobilno, a.apaEmail from CFCApplicationAddressEntity a,TbCfcApplicationMstEntity cm where cm.tbOrganisation=a.orgId  and cm.apmApplicationId=a.apmApplicationId  and cm.apmApplicationId=:apmApplicationId and cm.tbOrganisation=:orgId ";

		final Query query = createQuery(queryString);

		query.setParameter("orgId", UserSession.getCurrent().getOrganisation());

		query.setParameter("apmApplicationId", appid);

		final List<Object> result = query.getResultList();

		return result;

	}

	@Override
	public void updateCFCRejection(final List<CFCAttachment> rejectionArray, final String status) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.common.dao.ICFCAttachmentDAO#getDocumentUploaded(
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<CFCAttachment> getDocumentUploaded(final Long apmApplicationId, final Long orgid) {
		final Query query = createQuery(
				"select c from CFCAttachment c where c.applicationId = ?1 and c.orgid = ?2 and c.clmAprStatus='Y' and c.smScrutinyId is null ");
		query.setParameter(1, apmApplicationId);
		query.setParameter(2, orgid);
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}

	@Override
	public List<CFCAttachment> getDocumentUploadedByApplicationId(final Long apmApplicationId, final Long orgid) {
		final Query query = createQuery("select c from CFCAttachment c where c.applicationId = ?1 and c.orgid = ?2");
		query.setParameter(1, apmApplicationId);
		query.setParameter(2, orgid);
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}

	@Override
	public List<CFCAttachment> getDocumentUploadedByRefNo(final String referenceId, final Long orgid) {
		final Query query = createQuery("select c from CFCAttachment c where c.referenceId = ?1 and c.orgid = ?2");
		query.setParameter(1, referenceId);
		query.setParameter(2, orgid);
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}
	
	@Override
	public List<CFCAttachment> getDocumentUploadedByRefNoAndDeptId(final String referenceId,Long dept, final Long orgid) {
		final Query query = createQuery("select c from CFCAttachment c where c.referenceId = ?1 and c.dept = ?2 and c.orgid = ?3");
		query.setParameter(1, referenceId);
		query.setParameter(2, dept);
		query.setParameter(3, orgid);
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}

	@Override
	public List<Long> fetchAttachmentIdByAppid(Long applNo, Long orgId) {
		final Query query = createQuery(
				"select c.attId from CFCAttachment c where c.applicationId = ?1 and c.orgid = ?2 ");
		query.setParameter(1, applNo);
		query.setParameter(2, orgId);
		final List<Long> ids = query.getResultList();
		return ids;
	}

	@Override
	public List<CFCAttachment> fetchAttachmentByWorkflowActionId(Long workflowActId, Long orgId) {
		final Query query = createQuery("select c from CFCAttachment c where c.workflowActId = ?1 and c.orgid = ?2");
		query.setParameter(1, workflowActId);
		query.setParameter(2, orgId);
		final List<CFCAttachment> attachment = query.getResultList();
		return attachment;
	}

	@Override
	public List<Long> fetchAllAttachIdByReferencedId(String referenceId, Long orgId) {

		final Query query = createQuery("Select c.attId from CFCAttachment c where c.referenceId =?1 and c.orgid = ?2");
		query.setParameter(1, referenceId);
		query.setParameter(2, orgId);
		final List<Long> attachments = query.getResultList();
		return attachments;
	}

	@Override
	public List<CFCAttachment> getDocumentUploadedForApplications(List<Long> applicaionids, long orgId) {
		final Query query = createQuery(
				"select c from CFCAttachment c where c.applicationId in(?1) and c.orgid = ?2 and c.clmAprStatus='Y' and c.smScrutinyId is null ");
		query.setParameter(1, applicaionids);
		query.setParameter(2, orgId);
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}

	@Override
	public List<CFCAttachment> getDocumentUploadedByRefNoAndUserId(String referenceId, long orgId, Long userId) {
		// TODO Auto-generated method stub
		final Query query = createQuery(
				"Select c from CFCAttachment c where c.referenceId =?1 and c.orgid = ?2 and c.userId=?3");
		query.setParameter(1, referenceId);
		query.setParameter(2, orgId);
		query.setParameter(3, userId);
		final List<CFCAttachment> attachments = query.getResultList();
		return attachments;
	}
	
	@Override
	public List<CFCAttachment> getDocumentUploadedAtApprovalTimeInAgencyApproval(final Long apmApplicationId, final Long orgid) {
		final Query query = createQuery(
				"select c from CFCAttachment c where c.applicationId = ?1 and c.orgid = ?2 and c.referenceId =?3 and c.clmAprStatus is null and c.smScrutinyId is null ");
		query.setParameter(1, apmApplicationId);
		query.setParameter(2, orgid);
		query.setParameter(3, String.valueOf(apmApplicationId.longValue()));
		final List<CFCAttachment> masters = query.getResultList();
		return masters;
	}

}
