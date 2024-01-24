package com.abm.mainet.cfc.checklist.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.checklist.modelmapper.ChecklistMapper;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.dao.ICFCAttachmentDAO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Vinay.Jangir
 *
 */

@Service
public class ChecklistVerificationService implements IChecklistVerificationService {
    private static final long serialVersionUID = -6468116401165427472L;

    @Autowired
    private ICFCAttachmentDAO cfcAttachmentDAO;

    @Autowired
    private ServiceMasterRepository serviceMasterDAO;

    @Autowired
    private CFCAttechmentRepository attechDocumentRepository;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private ChecklistMapper mapper;

    @Override
    @Transactional
    public List<CFCAttachment> findAttachmentsForAppId(final long appId, final FileNetApplicationClient fnap,
            final long orgid) {
        List<CFCAttachment> attachmentList = new ArrayList<>(0);
        attachmentList = attechDocumentRepository.findAllAttachmentsByAppId(orgid, appId);

        final String guidRanDNum = Utility.getGUIDNumber();

        final Iterator<CFCAttachment> cfcAttachmentIterator = attachmentList.iterator();
        if (null != fnap) {
            while (cfcAttachmentIterator.hasNext()) {
                final CFCAttachment singleAttachment = cfcAttachmentIterator.next();

                fileUploadService.downLoadFilesFromServer(singleAttachment, guidRanDNum, fnap);
            }

            FileUploadUtility.getCurrent()
                    .setExistingFolderPath(Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                            + MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR);
        }
        return attachmentList;
    }

    @Override
    @Transactional
    public void updateCFCAttachment(final List<CFCAttachment> list, final String status) {
        cfcAttachmentDAO.updateCFCAttachment(list, status);
    }

    @Override
    @Transactional
    public void updateCFCRejection(final List<CFCAttachment> rejectionArray, final String status) {
        cfcAttachmentDAO.updateCFCRejection(rejectionArray, status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CFCAttachment> getAttachDocumentByDocumentStatus(final long appId, final String docStatus,
            final long orgId) {

        return cfcAttachmentDAO.findAllAttachmentsByAppId(appId, docStatus, orgId);
    }

    @Override
    public List<CFCAttachmentsDTO> getUploadedDocumentByDocumentStatus(final long appId, final String docStatus,
            final long orgId) {

        return mapper.mapCFCAttachmentToCFCAttachmentDTO(
                cfcAttachmentDAO.findAllAttachmentsByAppId(appId, docStatus, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.service.IChecklistVerificationService# getDocumentUploaded(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<CFCAttachment> getDocumentUploaded(final Long apmApplicationId, final Long orgid) {
        return cfcAttachmentDAO.getDocumentUploaded(apmApplicationId, orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> fetchAttachmentIdByAppid(Long applNo, Long orgId) {
        return cfcAttachmentDAO.fetchAttachmentIdByAppid(applNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> fetchAllAttachIdByReferenceId(String referenceId, Long orgId) {

        return cfcAttachmentDAO.fetchAllAttachIdByReferencedId(referenceId, orgId);
    }

    @Override
    public List<CFCAttachment> getDocumentUploadedByRefNo(final String referenceId,
            final long orgId) {
        return cfcAttachmentDAO.getDocumentUploadedByRefNo(referenceId, orgId);

    }
    
    @Override
    public List<CFCAttachment> getDocumentUploadedByRefNoAndDeptId(final String referenceId, Long dept ,final long orgId) {
        return cfcAttachmentDAO.getDocumentUploadedByRefNoAndDeptId(referenceId, dept , orgId);
    }

    @Override
    public List<CFCAttachment> getDocumentUploadedForApplications(List<Long> applicaionids, long orgId) {
        return cfcAttachmentDAO.getDocumentUploadedForApplications(applicaionids, orgId);

    }

    @Override
    public List<CFCAttachment> getDocumentUploadedForAppId(Long applicaionids, long orgId) {
        return cfcAttachmentDAO.getDocumentUploadedByApplicationId(applicaionids, orgId);

    }
    //adding method for fetching documents as per employeeId
    @Override
    public List<CFCAttachment> getDocumentUploadedByRefNoAndUserId(final String referenceId,
            final long orgId,final Long userId) {
        return cfcAttachmentDAO.getDocumentUploadedByRefNoAndUserId(referenceId, orgId,userId);

    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CFCAttachment> getDocumentUploadedAtApprovalTimeInAgencyApproval(final Long apmApplicationId, final Long orgid) {
        return cfcAttachmentDAO.getDocumentUploadedAtApprovalTimeInAgencyApproval(apmApplicationId, orgid);
    }

	@Override
	public CFCAttachment getDocumentUploadedList(Long apmApplicationId, Long clmId) {
		CFCAttachment documentUploadedListByAppId = attechDocumentRepository.getDocumentUploadedListByAppId(apmApplicationId, clmId);
		return documentUploadedListByAppId;
	}
	
	@Override
	public List<CFCAttachment> getDocumentUploadedByAppNoAndRefId(Long apmApplicationId, String referenceId) {
		List<CFCAttachment> docUploadedListByAppIdAndRefId = attechDocumentRepository.getDocumentByAppIdAndReferenceId(apmApplicationId, referenceId);
		return docUploadedListByAppIdAndRefId;
	}
	
	@Override
	public CFCAttachment getDocumentUploadedListByClmIdRefId(Long apmApplicationId, Long clmId, String refId) {
		CFCAttachment documentUploadedListByClmIdRefId = attechDocumentRepository.getDocumentUploadedListByClmIdRefId(apmApplicationId, clmId, refId);
		return documentUploadedListByClmIdRefId;
	}

}
