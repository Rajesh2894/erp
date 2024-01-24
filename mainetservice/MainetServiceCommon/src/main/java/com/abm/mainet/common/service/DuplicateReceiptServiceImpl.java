/**
 * 
 */
package com.abm.mainet.common.service;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.mapper.TbReceiptduplicateMapper;
import com.abm.mainet.common.repository.DuplicateReceiptRepository;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class DuplicateReceiptServiceImpl implements IDuplicateReceiptService {

    @Autowired
    private TbReceiptduplicateMapper mapper;
    @Autowired
    private DuplicateReceiptRepository repository;
    
    @Autowired
    private IReceiptEntryService receiptEntryService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DuplicateReceiptServiceImpl.class);


    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.service.IDuplicateReceiptService#save(com.abm.mainet.
     * common.integration.dto.TbReceiptDuplicateDTO)
     */

    @Override
    public void saveDuplicateReceipt(final ChallanReceiptPrintDTO receiptDto, final TbServiceReceiptMasEntity receiptMaster,
            final CommonChallanDTO offline) {
        TbReceiptDuplicateDTO dto = new TbReceiptDuplicateDTO();
        dto.setRmRcpId(receiptMaster.getRmRcptid());
        dto.setRmRcpNo(receiptMaster.getRmRcptno());
        dto.setApplicationId(receiptMaster.getApmApplicationId());
        dto.setAdditionalRefNo(receiptMaster.getAdditionalRefNo());
        dto.setRmDate(receiptMaster.getRmDate());
        dto.setCreatedBy(receiptMaster.getCreatedBy());
        dto.setCreatedDate(new Date());
        dto.setLgIpMac(receiptMaster.getLgIpMac());
        dto.setDeptCode(offline.getDeptCode());
        dto.setServiceCode(offline.getServiceCode());
        dto.setDupReceiptData(receiptMaster.getRmDate().toString());
        dto.setOrgId(receiptMaster.getOrgId());
        try {
            dto.setDupReceiptData(new ObjectMapper().writeValueAsString(receiptDto));
        } catch (JsonProcessingException e) {
            throw new FrameworkException("Error While Casting Object in String for receciptDuplicateBill() " + e);
        }
        save(dto);
    }

    @Override
    public void save(TbReceiptDuplicateDTO dto) {
        TbReceiptDuplicateEntity entity = mapper.mapTbReceiptDuplicateDTOToTbReceiptDuplicateEntity(dto);
        repository.save(entity);
    }

	public ChallanReceiptPrintDTO getRevenueReceiptDetails(Long reciptId, Long receiptNo, String assNo,Long orgId, int langId) {
		ChallanReceiptPrintDTO dataDTO = null;
		try {
			TbReceiptDuplicateEntity entity = repository.findByDupRcptByRcptIdAndrcptNoAndRefNo(reciptId, receiptNo,
					assNo);
			if (entity != null) {
				TbReceiptDuplicateDTO rcptPrintDto = new TbReceiptDuplicateDTO();
				BeanUtils.copyProperties(entity, rcptPrintDto);
				dataDTO = new ObjectMapper().readValue(rcptPrintDto.getDupReceiptData(), ChallanReceiptPrintDTO.class);
				if (entity.getRmDate() != null) {
					dataDTO.setRcptDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getRmDate()));
					// #148057
					if (StringUtils.isNotEmpty(entity.getServiceCode()))
						dataDTO.setServiceCode(entity.getServiceCode());
				}
				
				if (dataDTO.getEarlyPaymentDiscount() <= 0 && StringUtils.equals(entity.getDeptCode(), "AS")) {
					Double rebateAmountByPropNo = ApplicationContextProvider.getApplicationContext().getBean(ReceiptRepository.class).getRebateAmountByPropNo(assNo, entity.getRmDate());
					if(rebateAmountByPropNo != null && rebateAmountByPropNo > 0) {
						dataDTO.setEarlyPaymentDiscount(rebateAmountByPropNo);
					}
				}
			}else {
					dataDTO = receiptEntryService.setValuesAndPrintReport(reciptId, orgId, langId);
			}
		} catch (IOException e) {
			LOGGER.error("Problem while getting respose of duplicate receipt");
			throw new FrameworkException(e);
		}
		return dataDTO;
	}

	//#99721
	public ChallanReceiptPrintDTO getReceiptDetails(Long receiptId, Long receiptNo, Long applcationId) {		
		ChallanReceiptPrintDTO dataDTO=null;
		try {
		TbReceiptDuplicateEntity entity = repository.findByDupRcptByRcptIdAndrcptNoAndApplId(receiptId,receiptNo,applcationId);		
		if(entity!=null) {
		TbReceiptDuplicateDTO rcptPrintDto= new TbReceiptDuplicateDTO();
		BeanUtils.copyProperties(entity,rcptPrintDto);	
		dataDTO = new ObjectMapper().readValue(rcptPrintDto.getDupReceiptData(),
					  ChallanReceiptPrintDTO.class);
		if(entity.getRmDate()!=null) {
		dataDTO.setRcptDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getRmDate()));
				}
			}
		}
		catch (IOException e) {
			LOGGER.error("Problem while getting respose of duplicate receipt");
			throw new FrameworkException(e);
		}
		return dataDTO;
	}

}
