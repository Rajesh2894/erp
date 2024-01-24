package com.abm.mainet.bnd.service;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;

@WebService
public interface InclusionOfChildNameService {

	public BirthRegistrationDTO saveInclusionOfChild(BirthRegistrationDTO birthRegDto);

	public BirthRegistrationDTO getBirthByID(Long brId);
	
	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto);

}
