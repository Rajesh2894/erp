package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.dto.LocationDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareRequestDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long department;
    private String departmentDesc;
    private Long complaintType;
    private String complaintTypeDesc;
    private String description;
    private Integer pincode;
    private LocationDTO location;
    private String landmark;
    private byte[] document;
    private String documentType;
    private String fileName;
    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(final Long department) {
        this.department = department;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(final Long complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintTypeDesc() {
        return complaintTypeDesc;
    }

    public void setComplaintTypeDesc(final String complaintTypeDesc) {
        this.complaintTypeDesc = complaintTypeDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(final Integer pincode) {
        this.pincode = pincode;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(final String landmark) {
        this.landmark = landmark;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(final byte[] document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "CareRequestDetailsDTO [id=" + id + ", department=" + department + ", departmentDesc=" + departmentDesc
                + ", complaintType=" + complaintType + ", complaintTypeDesc=" + complaintTypeDesc + ", description="
                + description + ", pincode=" + pincode + ", location=" + location + ", landmark=" + landmark
                + ", document=" + Arrays.toString(document) + ", documentType=" + documentType + ", fileName="
                + fileName + ", file=" + file + "]";
    }

}
