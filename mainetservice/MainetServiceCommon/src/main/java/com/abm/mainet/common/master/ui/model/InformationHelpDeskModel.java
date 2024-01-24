package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.InformationDeskDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.ui.validator.InformationHelpDeskValidator;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;

@Component
@Scope("session")
public class InformationHelpDeskModel extends AbstractFormModel{

	private static final long serialVersionUID = 3973198650422672497L;

	private List<TbDepartment> departmentList;
	private List<TbServicesMst> tbServicesMsts;
	private int langId;
	InformationDeskDto informationDeskDto = new InformationDeskDto();
    private String  mode;
    private String checkListApplicable;
    
    private List<LookUp> categoryList = new ArrayList<>();
    private String serviceCode;
    private String deptCode;  
    List<Object> objList = new ArrayList<>();
	
	
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}
	
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}
	
	public int getLangId() {
		return langId;
	}
	
	public void setLangId(int langId) {
		this.langId = langId;
	}

	public List<TbServicesMst> getTbServicesMsts() {
		return tbServicesMsts;
	}

	public void setTbServicesMsts(List<TbServicesMst> tbServicesMsts) {
		this.tbServicesMsts = tbServicesMsts;
	}

	public InformationDeskDto getInformationDeskDto() {
		return informationDeskDto;
	}

	public void setInformationDeskDto(InformationDeskDto informationDeskDto) {
		this.informationDeskDto = informationDeskDto;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCheckListApplicable() {
		return checkListApplicable;
	}

	public void setCheckListApplicable(String checkListApplicable) {
		this.checkListApplicable = checkListApplicable;
	}

	public boolean validateInputs() {
		validateBean(informationDeskDto, InformationHelpDeskValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<LookUp> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<LookUp> categoryList) {
		this.categoryList = categoryList;
	}


	public List<Object> getObjList() {
		return objList;
	}

	public void setObjList(List<Object> objList) {
		this.objList = objList;
	}
}
