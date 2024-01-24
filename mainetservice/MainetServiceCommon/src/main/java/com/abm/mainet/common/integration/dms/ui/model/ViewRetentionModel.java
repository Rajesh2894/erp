package com.abm.mainet.common.integration.dms.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.integration.dms.dto.DmsManagementDto;
import com.abm.mainet.common.integration.dms.dto.DmsRetentionDto;
import com.abm.mainet.common.integration.dms.dto.ViewRetentionDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ViewRetentionModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private String metadataValue;
	private ViewRetentionDto metadatDto = new ViewRetentionDto();
	List<DmsRetentionDto> dmsRetentionDtoList = new ArrayList<DmsRetentionDto>();
	private List<LookUp> docTypeList = new ArrayList<>();
	private String errorMsg;
	private Long ward1;

	private Long ward2;

	private Long ward3;

	private String docRefNo;

	private String deptCode;

	public String getMetadataValue() {
		return metadataValue;
	}

	public void setMetadataValue(String metadataValue) {
		this.metadataValue = metadataValue;
	}

	public Long getWard1() {
		return ward1;
	}

	public void setWard1(Long ward1) {
		this.ward1 = ward1;
	}

	public Long getWard2() {
		return ward2;
	}

	public void setWard2(Long ward2) {
		this.ward2 = ward2;
	}

	public Long getWard3() {
		return ward3;
	}

	public void setWard3(Long ward3) {
		this.ward3 = ward3;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<LookUp> getDocTypeList() {
		return docTypeList;
	}

	public void setDocTypeList(List<LookUp> docTypeList) {
		this.docTypeList = docTypeList;
	}

	public ViewRetentionDto getMetadatDto() {
		return metadatDto;
	}

	public void setMetadatDto(ViewRetentionDto metadatDto) {
		this.metadatDto = metadatDto;
	}

	public List<DmsRetentionDto> getDmsRetentionDtoList() {
		return dmsRetentionDtoList;
	}

	public void setDmsRetentionDtoList(List<DmsRetentionDto> dmsRetentionDtoList) {
		this.dmsRetentionDtoList = dmsRetentionDtoList;
	}
}
