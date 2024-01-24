package com.abm.mainet.common.integration.dms.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DMSRequestDTO {
	
	private String documentName;
	private String documentExtension;
	private String parentFolderName;
	private String pathToRoot;	
	private String dataDefName;	
	
	private List<DocField> DocFields = new ArrayList<DocField>();
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentExtension() {
		return documentExtension;
	}
	public void setDocumentExtension(String documentExtension) {
		this.documentExtension = documentExtension;
	}
	public String getParentFolderName() {
		return parentFolderName;
	}
	public void setParentFolderName(String parentFolderName) {
		this.parentFolderName = parentFolderName;
	}
	public String getPathToRoot() {
		return pathToRoot;
	}
	public void setPathToRoot(String pathToRoot) {
		this.pathToRoot = pathToRoot;
	}
	public String getDataDefName() {
		return dataDefName;
	}
	public void setDataDefName(String dataDefName) {
		this.dataDefName = dataDefName;
	}
	public List<DocField> getDocFields() {
		return DocFields;
	}
	public void setDocFields(List<DocField> docFields) {
		DocFields = docFields;
	}
	
	
	
}
