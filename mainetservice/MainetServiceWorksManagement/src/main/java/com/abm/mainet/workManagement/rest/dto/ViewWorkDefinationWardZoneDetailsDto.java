package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class ViewWorkDefinationWardZoneDetailsDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2876931256935527269L;

    private String wardZoneId;
    private String codId1;
    private String codId2;
    private String codId3;
    private String codId4;
    private String codId5;
    private ViewWorkDefinitionDto workDefinitionDto;
    public ViewWorkDefinitionDto getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(ViewWorkDefinitionDto workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public String getWardZoneId() {
		return wardZoneId;
	}

	public void setWardZoneId(String wardZoneId) {
		this.wardZoneId = wardZoneId;
	}

	public String getCodId1() {
		return codId1;
	}

	public void setCodId1(String codId1) {
		this.codId1 = codId1;
	}

	public String getCodId2() {
		return codId2;
	}

	public void setCodId2(String codId2) {
		this.codId2 = codId2;
	}

	public String getCodId3() {
		return codId3;
	}

	public void setCodId3(String codId3) {
		this.codId3 = codId3;
	}

	public String getCodId4() {
		return codId4;
	}

	public void setCodId4(String codId4) {
		this.codId4 = codId4;
	}

	public String getCodId5() {
		return codId5;
	}

	public void setCodId5(String codId5) {
		this.codId5 = codId5;
	}



   
}
