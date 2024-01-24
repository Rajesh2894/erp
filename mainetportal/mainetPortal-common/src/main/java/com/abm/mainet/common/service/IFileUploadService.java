/**
 *
 */
package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.FileUploadDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.dms.client.FileNetApplicationClient;

public interface IFileUploadService {

    void validateUpload(BindingResult bindingResult);
    
    List<DocumentDetailsVO> setFileUploadMethod(List<DocumentDetailsVO> docs);

    boolean doMasterFileUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO);

    boolean saveMasterFileToPath(List<DocumentDetailsVO> docdetailsList, FileUploadDTO fileUploadDTOs);

    boolean convertAndSaveFile(DocumentDetailsVO ddVO, String fileNetPath, String dirPath, String fileName);

    void downLoadFilesFromServer(AttachDocs attachDocs, String guidRanDNum, FileNetApplicationClient fileNetApplicationClient);

    List<AttachDocs> findByCode(Long orgId, String Identifier);

    int updateMasterDocumentStatus(String uniqueId, String status);

    int updateAllDocumentStatus(String status, Long empId, List<Long> attList);

    List<DocumentDetailsVO> getUploadedDocForPreview(List<DocumentDetailsVO> docs);

    List<DocumentDetailsVO> convertFileToByteString(List<DocumentDetailsVO> docs);
    
    public List<DocumentDetailsVO> prepareFileUpload(final List<DocumentDetailsVO> docs);
    
}
