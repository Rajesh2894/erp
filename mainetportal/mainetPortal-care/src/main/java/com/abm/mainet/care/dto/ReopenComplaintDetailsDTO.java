package com.abm.mainet.care.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReopenComplaintDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private byte[] document;
    private String fileName;
    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(final byte[] document) {
        this.document = document;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

}
