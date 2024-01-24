/**
 *
 */
package com.abm.mainet.common.integration.dms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author vishnu.jagdale
 *
 */
public interface IFileUploadService {

    boolean saveFileToPath(List<DocumentDetailsVO> docList, FileUploadDTO fileUploadDTO);

    boolean saveMasterFileToPath(List<DocumentDetailsVO> docdetailsList, FileUploadDTO fileUploadDTOs);

    String encodeFile(String filePath) throws FileNotFoundException, IOException;

    boolean doFileUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO);

    void sessionCleanUpForFileUpload();

    /**
     * @param cfcAttachment
     * @param guidRanDNum
     * @param fileNetApplicationClient
     */
    void downLoadFilesFromServer(CFCAttachment cfcAttachment,
            String guidRanDNum,
            FileNetApplicationClient fileNetApplicationClient);

    /**
     * @param ddVO
     * @param fileNetPath
     * @param dirPath
     * @param fileName
     * @return
     */
    boolean convertAndSaveFile(DocumentDetailsVO ddVO, String fileNetPath,
            String dirPath, String fileName);

    /**
     * @param bindingResult
     */
    void validateUpload(BindingResult bindingResult);

    public void downLoadFilesFromServer(AttachDocs attachDocs, String guidRanDNum,
            FileNetApplicationClient fileNetApplicationClient);

    public List<DocumentDetailsVO> prepareFileUpload(final List<DocumentDetailsVO> docs);

    public List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs);

    boolean doMasterFileUpload(List<DocumentDetailsVO> documentList, RequestDTO requestDTO);

    boolean doMasterFileUpload(List<DocumentDetailsVO> documentList, FileUploadDTO fileUploadDTO);

    List<DocumentDetailsVO> getUploadedDocForPreview(List<DocumentDetailsVO> docs);

}
