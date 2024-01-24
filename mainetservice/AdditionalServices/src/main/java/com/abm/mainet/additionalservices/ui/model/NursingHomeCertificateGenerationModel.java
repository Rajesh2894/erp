package com.abm.mainet.additionalservices.ui.model;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.CFCSonographyMastDto;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NursingHomeCertificateGenerationModel extends AbstractFormModel {

	private static final long serialVersionUID = -2732964179428439834L;

	private CFCNursingHomeInfoDTO cfcNuringHomeInfoDto;

	private TbCfcApplicationMst cfcApplicationMst;
	private Date newDate;
	private int year;
	private String yearWord;
	private String serviceCode;
	private CFCSonographyMastDto cfcSonographyMastDto;
	private String expDate;
	private Date todate;
	
	public String getYearWord() {
		return yearWord;
	}

	public void setYearWord(String yearWord) {
		this.yearWord = yearWord;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public CFCNursingHomeInfoDTO getCfcNuringHomeInfoDto() {
		return cfcNuringHomeInfoDto;
	}

	public void setCfcNuringHomeInfoDto(CFCNursingHomeInfoDTO cfcNuringHomeInfoDto) {
		this.cfcNuringHomeInfoDto = cfcNuringHomeInfoDto;
	}

	public TbCfcApplicationMst getCfcApplicationMst() {
		return cfcApplicationMst;
	}

	public void setCfcApplicationMst(TbCfcApplicationMst cfcApplicationMst) {
		this.cfcApplicationMst = cfcApplicationMst;
	}

	public CFCSonographyMastDto getCfcSonographyMastDto() {
		return cfcSonographyMastDto;
	}

	public void setCfcSonographyMastDto(CFCSonographyMastDto cfcSonographyMastDto) {
		this.cfcSonographyMastDto = cfcSonographyMastDto;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}


	
	

}
