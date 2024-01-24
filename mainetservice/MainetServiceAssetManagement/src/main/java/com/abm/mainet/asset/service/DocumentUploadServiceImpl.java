package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class DocumentUploadServiceImpl implements IDocumentUploadService {

    @Autowired
    private IFileUploadService assetFileUpload;
    @Autowired
    IAttachDocsDao iAttachDocsDao;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.DocumentUploadService#documentUpload(java.util.List,
     * com.abm.mainet.common.integration.dms.dto.FileUploadDTO, java.lang.Long, java.lang.Long) this method is for document upload
     */
    @Override
    @Transactional
    public boolean documentUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO, Long orgId, Long empId) {
        boolean fileuploadStatus = false;
        if (orgId != null) {
            requestDTO.setOrgId(orgId);
        }
        if (empId != null) {
            requestDTO.setUserId(empId);
        }
        requestDTO.setStatus(MainetConstants.FlagA);
        // fileUploadDTO.setDepartmentName(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        // fileuploadStatus = assetFileUpload.doMasterFileUpload(documentList, fileUploadDTO);
        fileuploadStatus = assetFileUpload.doFileUpload(documentList, requestDTO);

        return fileuploadStatus;
    }

    @Override
    @Transactional
    public boolean documentDelete(Long empId, String deleteByAtdId) {
        List<Long> deAtdIdList = null;
        String fileId = deleteByAtdId;
        if (fileId != null && !fileId.isEmpty()) {
            deAtdIdList = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                deAtdIdList.add(Long.valueOf(fields));
            }
        }
        if (deAtdIdList != null && !deAtdIdList.isEmpty())
            iAttachDocsDao.updateRecord(deAtdIdList, empId, MainetConstants.FlagD);
        return false;
    }

    @Override
    @Transactional
    public boolean documentDelete(Long empId, List<Long> deleteByAtdIdList) {
        boolean flag = false;
        if (deleteByAtdIdList != null && !deleteByAtdIdList.isEmpty())
            flag = iAttachDocsDao.updateRecord(deleteByAtdIdList, empId, MainetConstants.FlagD);
        return flag;
    }
}
