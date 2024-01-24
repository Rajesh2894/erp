package com.abm.mainet.tradeLicense.service;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

public interface iTransferLicenseService {

	TradeMasterDetailDTO saveTransferLicenseService(TradeMasterDetailDTO masDto);
	// TODO Auto-generated method stub

	TradeMasterDetailDTO getTransferChargesFromBrmsRule(TradeMasterDetailDTO masDto);

}
