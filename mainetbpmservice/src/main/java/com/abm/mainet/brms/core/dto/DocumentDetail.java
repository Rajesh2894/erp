/**
 * 
 */
package com.abm.mainet.brms.core.dto;

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
public class DocumentDetail implements Serializable{

	/**
	 * 
	 */
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
	/**
	 * @return the attachmentId
	 */
	public Long getAttachmentId() {
		return attachmentId;
	}
	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(Long attachmentId) {
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
	public void setDocumentId(Long documentId) {
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
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	/**
	 * @return the documentSerialNo
	 */
	public Long getDocumentSerialNo() {
		return documentSerialNo;
	}
	/**
	 * @param documentSerialNo the documentSerialNo to set
	 */
	public void setDocumentSerialNo(Long documentSerialNo) {
		this.documentSerialNo = documentSerialNo;
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
	public void setDescriptionType(String descriptionType) {
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
	public void setDocumentType(String documentType) {
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
	public void setUploadedDocumentPath(String uploadedDocumentPath) {
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
	public void setDoc_DESC_Mar(String doc_DESC_Mar) {
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
	public void setDoc_DESC_ENGL(String doc_DESC_ENGL) {
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
	public void setDocumentByteCode(String documentByteCode) {
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
	public void setCheckkMANDATORY(String checkkMANDATORY) {
		this.checkkMANDATORY = checkkMANDATORY;
	}
	
	
	
}
