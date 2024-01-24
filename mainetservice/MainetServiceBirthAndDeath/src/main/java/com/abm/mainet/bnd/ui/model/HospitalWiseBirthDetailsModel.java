package com.abm.mainet.bnd.ui.model;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.bnd.dto.HospitalWiseBirthDetailsDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;



@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class HospitalWiseBirthDetailsModel extends AbstractFormModel {
	
	
	
	private static final long serialVersionUID = 1L;
	
	private HospitalWiseBirthDetailsDTO hospitalWiseBirthDetailsDTO = new HospitalWiseBirthDetailsDTO();

	public HospitalWiseBirthDetailsDTO getHospitalWiseBirthDetailsDTO() {
		return hospitalWiseBirthDetailsDTO;
	}

	public void setHospitalWiseBirthDetailsDTO(HospitalWiseBirthDetailsDTO hospitalWiseBirthDetailsDTO) {
		this.hospitalWiseBirthDetailsDTO = hospitalWiseBirthDetailsDTO;
	}

	
	


}
