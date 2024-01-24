/**
 *
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author vishnu.jagdale
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class DocumentDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long attachmentId;
    private Long documentId;
    private String documentName;
    private Long documentSerialNo;
    private String descriptionType;
    private String documentType;
    private String uploadedDocumentPath;
    private String doc_DESC_Mar;
    private String doc_DESC_ENGL;
    private String documentByteCode;
    private String checkkMANDATORY;
    private Long docSize;
    private String docName;
    private String docDescription;
    private String clmAprStatus;
    private String docDes;
    
    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(final Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * @return the documentId
     */
    public Long getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(final Long documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(final String documentName) {
        this.documentName = documentName;
    }

    /**
     * @return the descriptionType
     */
    public String getDescriptionType() {
        return descriptionType;
    }

    /**
     * @param descriptionType the descriptionType to set
     */
    public void setDescriptionType(final String descriptionType) {
        this.descriptionType = descriptionType;
    }

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the uploadedDocumentPath
     */
    public String getUploadedDocumentPath() {
        return uploadedDocumentPath;
    }

    /**
     * @param uploadedDocumentPath the uploadedDocumentPath to set
     */
    public void setUploadedDocumentPath(final String uploadedDocumentPath) {
        this.uploadedDocumentPath = uploadedDocumentPath;
    }

    /**
     * @return the doc_DESC_Mar
     */
    public String getDoc_DESC_Mar() {
        return doc_DESC_Mar;
    }

    /**
     * @param doc_DESC_Mar the doc_DESC_Mar to set
     */
    public void setDoc_DESC_Mar(final String doc_DESC_Mar) {
        this.doc_DESC_Mar = doc_DESC_Mar;
    }

    /**
     * @return the doc_DESC_ENGL
     */
    public String getDoc_DESC_ENGL() {
        return doc_DESC_ENGL;
    }

    /**
     * @param doc_DESC_ENGL the doc_DESC_ENGL to set
     */
    public void setDoc_DESC_ENGL(final String doc_DESC_ENGL) {
        this.doc_DESC_ENGL = doc_DESC_ENGL;
    }

    /**
     * @return the documentByteCode
     */
    public String getDocumentByteCode() {
        return documentByteCode;
    }

    /**
     * @param documentByteCode the documentByteCode to set
     */
    public void setDocumentByteCode(final String documentByteCode) {
        this.documentByteCode = documentByteCode;
    }

    /**
     * @return the checkkMANDATORY
     */
    public String getCheckkMANDATORY() {
        return checkkMANDATORY;
    }

    /**
     * @param checkkMANDATORY the checkkMANDATORY to set
     */
    public void setCheckkMANDATORY(final String checkkMANDATORY) {
        this.checkkMANDATORY = checkkMANDATORY;
    }

    public Long getDocumentSerialNo() {
        return documentSerialNo;
    }

    public void setDocumentSerialNo(final Long documentSerialNo) {
        this.documentSerialNo = documentSerialNo;
    }

	public Long getDocSize() {
		return docSize;
	}

	public void setDocSize(Long docSize) {
		this.docSize = docSize;
	}


   public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getClmAprStatus() {
		return clmAprStatus;
	}

	public void setClmAprStatus(String clmAprStatus) {
		this.clmAprStatus = clmAprStatus;
	}

	public String getDocDes() {
		return docDes;
	}

	public void setDocDes(String docDes) {
		this.docDes = docDes;
	}
	

}
