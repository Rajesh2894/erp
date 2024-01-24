package com.abm.mainet.audit.dto;

import java.util.List;

import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.utility.LookUp;



public class SearchParaRestResponseDto {
	
	private List<AuditParaEntryDto> searchAuditParaEntryDtoList = null;
	
	private List<TbLocationMas> locLst = null;
	
	private List<TbDepartment> deptLst = null;
	
	private List<LookUp> statusLst = null;


	public List<LookUp> getStatusLst() {
		return statusLst;
	}

	public void setStatusLst(List<LookUp> statusLst) {
		this.statusLst = statusLst;
	}

	public List<TbLocationMas> getLocLst() {
		return locLst;
	}

	public void setLocLst(List<TbLocationMas> locLst) {
		this.locLst = locLst;
	}
	
	public List<TbDepartment> getDeptLst() {
		return deptLst;
	}

	public void setDeptLst(List<TbDepartment> deptLst) {
		this.deptLst = deptLst;
	}


	public List<AuditParaEntryDto> getSearchAuditParaEntryDtoList() {
		return searchAuditParaEntryDtoList;
	}

	public void setSearchAuditParaEntryDtoList(List<AuditParaEntryDto> searchAuditParaEntryDtoList) {
		this.searchAuditParaEntryDtoList = searchAuditParaEntryDtoList;
	}

}
