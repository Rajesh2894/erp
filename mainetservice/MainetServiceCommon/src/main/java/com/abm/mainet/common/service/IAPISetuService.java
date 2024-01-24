package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.integration.dto.DirectorDetails;
import com.abm.mainet.common.integration.dto.MCACompany;
import com.abm.mainet.common.integration.dto.PanCard;

public interface IAPISetuService {
	
	Map<String, String> verifyPanCard(PanCard root);
	MCACompany fetchCompanyDetails(String cinNo);
	DirectorDetails fetchDirectorDetails(String cinNo);

}
