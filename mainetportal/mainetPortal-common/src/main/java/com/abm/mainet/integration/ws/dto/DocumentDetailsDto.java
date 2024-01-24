package com.abm.mainet.integration.ws.dto;

import java.io.Serializable;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class DocumentDetailsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
     * String protocol, @RequestParam String folderPath, @RequestParam String fileName, @RequestParam byte [] content
     * , @RequestParam String contentType
     */
    private String documentId;
    private String protocol;
    private String folderPath;
    private String fileName;
    private byte[] fileContent;
    private String contentType;
    private String departmentName;
    private Map<String, String> dmsServiceMap;
    private String departmentCode;
    private String docTypeDescription;

    public DocumentDetailsDto() {
        super();
    }

    public DocumentDetailsDto(final String documentId, final String protocol, final String folderPath, final String fileName,
            final byte[] fileContent,
            final String contentType) {
        super();
        this.documentId = documentId;
        this.protocol = protocol;
        this.folderPath = folderPath;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.contentType = contentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(final String documentId) {
        this.documentId = documentId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(final String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(final byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
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

    
}
