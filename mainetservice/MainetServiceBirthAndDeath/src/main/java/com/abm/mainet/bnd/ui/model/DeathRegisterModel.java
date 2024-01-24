package com.abm.mainet.bnd.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.DeathRegisterDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;


@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class DeathRegisterModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005509924724678790L;


	
	private DeathRegisterDTO deathRegisterDTO;

	public DeathRegisterDTO getDeathRegisterDTO() {
		return deathRegisterDTO;
	}

	public void setDeathRegisterDTO(DeathRegisterDTO deathRegisterDTO) {
		this.deathRegisterDTO = deathRegisterDTO;
	}
	
}
