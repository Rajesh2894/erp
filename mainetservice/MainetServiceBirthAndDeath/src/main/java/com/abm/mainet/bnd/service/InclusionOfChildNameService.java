package com.abm.mainet.bnd.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.ui.model.InclusionOfChildNameModel;
import com.abm.mainet.common.dto.CommonChallanDTO;

@WebService
public interface InclusionOfChildNameService {

	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(String certNo, String regNo,String regDate,Date brDob,
			String applicnId, Long orgId);

	public BirthRegistrationDTO saveInclusionOfChild(BirthRegistrationDTO birthRegDto, InclusionOfChildNameModel model);

	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto);
	
	public BirthRegistrationDTO saveInclusionOfChildOnApproval(BirthRegistrationDTO birthRegDto);
	 public BirthRegistrationDTO getBirthByID(Long brId);

	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,InclusionOfChildNameModel birthRegModel);

	public void smsAndEmailApproval(BirthRegistrationDTO dto, String decision);
	
}
