package com.abm.mainet.cfc.checklist.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;

public interface IChecklistVerificationService extends Serializable {
    public List<CFCAttachment> findAttachmentsForAppId(long appId, FileNetApplicationClient fnap, long orgid);

    public void updateCFCAttachment(List<CFCAttachment> list, String status);

    public void updateCFCRejection(List<CFCAttachment> rejectionArray, String status);

    List<CFCAttachment> getAttachDocumentByDocumentStatus(long appId, String docStatus, long orgId);

    /**
     * @param apmApplicationId
     * @param orgid
     * @return
     */
    public List<CFCAttachment> getDocumentUploaded(Long apmApplicationId, Long orgid);

    /**
     * @param appId
     * @param docStatus
     * @param orgId
     * @return
     */
    List<CFCAttachmentsDTO> getUploadedDocumentByDocumentStatus(long appId, String docStatus, long orgId);

    public List<Long> fetchAttachmentIdByAppid(Long applNo, Long orgId);

    /**
     * This Method Is Used to Get List of Attachements Id With the Help of Reference Id
     * 
     * @param referenceId
     * @param orgId
     * @return
     */
    public List<Long> fetchAllAttachIdByReferenceId(String referenceId, Long orgId);

    List<CFCAttachment> getDocumentUploadedByRefNo(String referenceId, long orgId);

    public List<CFCAttachment> getDocumentUploadedForApplications(List<Long> applicaionids, long orgId);
    
    public List<CFCAttachment> getDocumentUploadedForAppId(Long applicaionids, long orgId);
    /**
     * This method is used for fetching documents as per emplyee id refno and orgId
     * @param referenceId
     * @param orgId
     * @param userId
     * @return
     */

	List<CFCAttachment> getDocumentUploadedByRefNoAndUserId(String referenceId, long orgId, Long userId);
	
	List<CFCAttachment> getDocumentUploadedAtApprovalTimeInAgencyApproval(final Long apmApplicationId, final Long orgid);

	List<CFCAttachment> getDocumentUploadedByRefNoAndDeptId(String referenceId, Long dept, long orgId);
	
	CFCAttachment getDocumentUploadedList(final Long apmApplicationId, final Long clmId);

	List<CFCAttachment> getDocumentUploadedByAppNoAndRefId(Long apmApplicationId, String referenceId);

	CFCAttachment getDocumentUploadedListByClmIdRefId(Long apmApplicationId, Long clmId, String refId);

}
