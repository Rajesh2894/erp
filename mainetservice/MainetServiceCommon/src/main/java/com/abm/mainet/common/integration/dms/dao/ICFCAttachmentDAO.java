package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

public interface ICFCAttachmentDAO {

    public abstract void updateCFCAttachment(List<CFCAttachment> list, String status);

    public abstract void updateCFCRejection(List<CFCAttachment> rejectionArray, String status);

    public abstract List<Object> getEmployeeDetails(Long appid);

    List<CFCAttachment> findAllAttachmentsByAppId(long appId, String docStatus, long orgId);

    public abstract List<CFCAttachment> getDocumentUploaded(Long apmApplicationId, Long orgid);

    public abstract List<Long> fetchAttachmentIdByAppid(Long applNo, Long orgId);

    public List<CFCAttachment> fetchAttachmentByWorkflowActionId(Long workflowActId, Long orgId);

    /**
     * this method is used to get List of attachment Id with referenced Id
     * 
     * @param referenceId
     * @param orgId
     * @return
     */
    public List<Long> fetchAllAttachIdByReferencedId(String referenceId, Long orgId);

    List<CFCAttachment> getDocumentUploadedByRefNo(String referenceId, Long orgid);

    public abstract List<CFCAttachment> getDocumentUploadedForApplications(List<Long> applicaionids, long orgId);
    
    public List<CFCAttachment> getDocumentUploadedByApplicationId(final Long apmApplicationId,
            final Long orgid);

    /**
     * this method is used to get List of attachment Id with referenced Id and empid
     * 
     * @param referenceId
     * @param orgId
     * @return
     */
	public abstract List<CFCAttachment> getDocumentUploadedByRefNoAndUserId(String referenceId, long orgId, Long userId);
	
	public List<CFCAttachment> getDocumentUploadedAtApprovalTimeInAgencyApproval(final Long apmApplicationId, final Long orgid);

	List<CFCAttachment> getDocumentUploadedByRefNoAndDeptId(String referenceId, Long dept, Long orgid);

}