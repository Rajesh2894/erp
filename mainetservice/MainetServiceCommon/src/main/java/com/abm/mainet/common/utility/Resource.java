package com.abm.mainet.common.utility;

public class Resource {

	private String pageId;
	private String fieldId;
	private boolean isMandatory;
	private boolean isVisible;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
