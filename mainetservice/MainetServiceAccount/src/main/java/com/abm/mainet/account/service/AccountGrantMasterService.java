package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.account.dto.AccountGrantMasterDto;



@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface AccountGrantMasterService {

	AccountGrantMasterDto saveGrantMaster(AccountGrantMasterDto accountGrantMasterDto);

	List<AccountGrantMasterDto> findByGrntIdAndOrgId(Long grntId, Long orgId);

	List<AccountGrantMasterDto> findByNameAndNature(String grtType, String grtName,String grtNo,Long fundId,String faYearId,Date frmDate, Date tDate,Long orgId);
	
	AccountGrantMasterDto getGrantDetailsByGrntIdAndOrgId(Long grantId, Long orgId);

}
