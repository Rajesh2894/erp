package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;

@Component
@Scope("session")
public class RtiRegisterBirtReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private RtiApplicationFormDetailsReqDTO rtiAppFormDto = new RtiApplicationFormDetailsReqDTO();
	
	private List<RtiApplicationFormDetailsReqDTO> rtiAppFormDtoList = new ArrayList<>();

	public RtiApplicationFormDetailsReqDTO getRtiAppFormDto() {
		return rtiAppFormDto;
	}

	public List<RtiApplicationFormDetailsReqDTO> getRtiAppFormDtoList() {
		return rtiAppFormDtoList;
	}

	public void setRtiAppFormDto(RtiApplicationFormDetailsReqDTO rtiAppFormDto) {
		this.rtiAppFormDto = rtiAppFormDto;
	}

	public void setRtiAppFormDtoList(List<RtiApplicationFormDetailsReqDTO> rtiAppFormDtoList) {
		this.rtiAppFormDtoList = rtiAppFormDtoList;
	}
	

}
