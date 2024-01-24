package com.abm.mainet.bnd.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.BirthRegisterDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;


@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class BirthRegisterModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 50826054888626598L;

	private BirthRegisterDTO birthRegisterDTO;

	public BirthRegisterDTO getBirthRegisterDTO() {
		return birthRegisterDTO;
	}

	public void setBirthRegisterDTO(BirthRegisterDTO birthRegisterDTO) {
		this.birthRegisterDTO = birthRegisterDTO;
	}
	
	
}
