package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author satish.rathore
 *
 */
public interface IDocumentUploadService {

    /**
     * @param documentList
     * @param fileUploadDTO
     * @param orgId
     * @param empId
     * @return this method returns true if and only if document save otherwise that returns false
     */
    public boolean documentUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO, Long orgId, Long empId);

    public boolean documentDelete(Long empId, String deleteByAtdId);
    
    public boolean documentDelete(Long empId, List<Long> deleteByAtdIdList);

}
