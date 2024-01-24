package com.abm.mainet.common.master.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.dto.CFCApplicationStatusDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CFCApplicationStsModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4686539111539292946L;
	private List<TbDepartment> departmentList;

	private List<ApplicationStatusDTO> applicationStatusDTOs;
	private CFCApplicationStatusDto applicationStatusDto;
	private List<TbServicesMst> serviceMstList;
	private int langId;

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}
	
	public List<TbServicesMst> getServiceMstList() {
		return serviceMstList;
	}

	public void setServiceMstList(List<TbServicesMst> serviceMstList) {
		this.serviceMstList = serviceMstList;
	}

	public CFCApplicationStatusDto getApplicationStatusDto() {
		return applicationStatusDto;
	}

	public void setApplicationStatusDto(CFCApplicationStatusDto applicationStatusDto) {
		this.applicationStatusDto = applicationStatusDto;
	}

	public List<ApplicationStatusDTO> getApplicationStatusDTOs() {
		return applicationStatusDTOs;
	}

	public void setApplicationStatusDTOs(List<ApplicationStatusDTO> applicationStatusDTOs) {
		this.applicationStatusDTOs = applicationStatusDTOs;
	}

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

}
