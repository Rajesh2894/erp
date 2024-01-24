package com.abm.mainet.dms.dto;

import java.io.Serializable;
import java.util.Map;


public class DocumentDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String documentId;
    private String folderPath;
    private String fileName;
    private String fileContent;
    private String contentType;
    private String protocol;
    private String departmentName;
    private Map<String, String> dmsServiceMap;
    private String departmentCode;
    private String docTypeDescription;
    //private Long applicationId;
    //private String referenceId;  

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Map<String, String> getDmsServiceMap() {
		return dmsServiceMap;
	}

	public void setDmsServiceMap(Map<String, String> dmsServiceMap) {
		this.dmsServiceMap = dmsServiceMap;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDocTypeDescription() {
		return docTypeDescription;
	}

	public void setDocTypeDescription(String docTypeDescription) {
		this.docTypeDescription = docTypeDescription;
	} 
    
	/*
	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	*/
}
