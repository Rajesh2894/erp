package com.abm.mainet.firemanagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.firemanagement.dto.CallDetailsDTO;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CallDetailsModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -607588273450347275L;
	CallDetailsDTO callDetailsDTO=new CallDetailsDTO();
	public CallDetailsDTO getCallDetailsDTO() {
		return callDetailsDTO;
	}
	public void setCallDetailsDTO(CallDetailsDTO callDetailsDTO) {
		this.callDetailsDTO = callDetailsDTO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
