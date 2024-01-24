package com.abm.mainet.tradeLicense.service;

import java.util.Map;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface ITransferLicenseService {
	TradeMasterDetailDTO saveTransferLicenseService(TradeMasterDetailDTO masDto);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	TradeMasterDetailDTO updateTransferLicenseService(TradeMasterDetailDTO masDto);

	TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	CommonChallanDTO getDepartmentWiseLoiData(Long applicationNo, Long orgId);

	void sendSmsEmail(TradeMasterDetailDTO masDto);
}
