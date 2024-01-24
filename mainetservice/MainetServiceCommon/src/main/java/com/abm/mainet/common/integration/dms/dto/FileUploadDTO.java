/**
 *
 */
package com.abm.mainet.common.integration.dms.dto;

/**
 * This dto being used for FileUpload
 * 
 * @author Vivek.Kumar
 * @since 08-Jan-2016
 */
public class FileUploadDTO {

	private String fileNetPath;
	private String dirPath;
	private String fileName;
	private String documentByteCode;
	private Long deptId;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Long applicationId;
	private Long serviceId;
	private String idfId;
	private String status;
	private String departmentName;
	// added for work estimate approval
	private String referenceId;	
	private DmsDocsMetadataDto dmsDocsDto;
	private String departmentFullName;
	private String documentTypeDesc;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdfId() {
		return idfId;
	}

	public void setIdfId(String idfId) {
		this.idfId = idfId;
	}

	public String getFileNetPath() {
		return fileNetPath;
	}

	public void setFileNetPath(final String fileNetPath) {
		this.fileNetPath = fileNetPath;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(final String dirPath) {
		this.dirPath = dirPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentByteCode() {
		return documentByteCode;
	}

	public void setDocumentByteCode(final String documentByteCode) {
		this.documentByteCode = documentByteCode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(final Long langId) {
		this.langId = langId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public DmsDocsMetadataDto getDmsDocsDto() {
		return dmsDocsDto;
	}

	public void setDmsDocsDto(DmsDocsMetadataDto dmsDocsDto) {
		this.dmsDocsDto = dmsDocsDto;
	}

	public String getDepartmentFullName() {
		return departmentFullName;
	}

	public void setDepartmentFullName(String departmentFullName) {
		this.departmentFullName = departmentFullName;
	}

	public String getDocumentTypeDesc() {
		return documentTypeDesc;
	}

	public void setDocumentTypeDesc(String documentTypeDesc) {
		this.documentTypeDesc = documentTypeDesc;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FileUploadDTO [fileNetPath=");
		builder.append(fileNetPath);
		builder.append(", dirPath=");
		builder.append(dirPath);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", documentByteCode=");
		builder.append(documentByteCode);
		builder.append(", deptId=");
		builder.append(deptId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", langId=");
		builder.append(langId);
		builder.append(", applicationId=");
		builder.append(applicationId);
		builder.append(", serviceId=");
		builder.append(serviceId);
		builder.append("]");
		return builder.toString();
	}

}
