/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * @author satish.rathore
 *
 */
public class DocumentDto {

    List<DocumentDetailsVO> documentAttached;
    List<Long> documentId;

    /**
     * @return the documentAttached
     */
    public List<DocumentDetailsVO> getDocumentAttached() {
        return documentAttached;
    }

    /**
     * @param documentAttached the documentAttached to set
     */
    public void setDocumentAttached(List<DocumentDetailsVO> documentAttached) {
        this.documentAttached = documentAttached;
    }

    /**
     * @return the documentId
     */
    public List<Long> getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(List<Long> documentId) {
        this.documentId = documentId;
    }

}
