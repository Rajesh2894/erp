package com.abm.mainet.water.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@WebService
public interface INewWaterConnectionService extends Serializable {

	/**
	 * To save Or Update New Water Connection
	 * 
	 * @param reqDTO
	 * @return NewWaterConnectionResponseDTO
	 */
	NewWaterConnectionResponseDTO saveOrUpdateNewConnection(NewWaterConnectionReqDTO reqDTO);

	/**
	 * To find No. Of Days Calculation
	 * 
	 * @param csmrDto
	 * @param organisation
	 */
	void findNoOfDaysCalculation(TbCsmrInfoDTO csmrDto, Organisation organisation);

	TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO);

	/**
	 * get List of plumber
	 * 
	 * @param orgid
	 * @return List<PlumberDTO>
	 */
	List<PlumberDTO> getListofplumber(Long orgid);

	/**
	 * get All Connection By Mobile No.
	 * 
	 * @param mobileNo
	 * @param orgid
	 * @return List<TbCsmrInfoDTO>
	 */
	List<TbCsmrInfoDTO> getAllConnectionByMobileNo(String mobileNo, Long orgid);

	/**
	 * fetch Connection Details By Connection No.
	 * 
	 * @param csCcnNo
	 * @param orgid
	 * @return TbCsmrInfoDTO
	 */
	public TbCsmrInfoDTO fetchConnectionDetailsByConnNo(String csCcnNo, Long orgid);

	NewWaterConnectionReqDTO getApplicationData(NewWaterConnectionReqDTO requestVo)
			throws JsonParseException, JsonMappingException, IOException;

	/**
	 * Fetch Connection By Illegal Notice No
	 * 
	 * @param csCcn
	 * @param orgId
	 * @return TbCsmrInfoDTO
	 */
	TbCsmrInfoDTO fetchConnectionByIllegalNoticeNo(TbCsmrInfoDTO infoDto);

	/**
	 * To save Illegal To Legal Connection Application
	 * 
	 * @param reqDTO
	 * @return NewWaterConnectionResponseDTO
	 */
	NewWaterConnectionResponseDTO saveIllegalToLegalConnectionApplication(NewWaterConnectionReqDTO reqDTO);

	ProvisionalCertificateDTO getProvisionalCertificateData(ProvisionalCertificateDTO reqDTO);
}
