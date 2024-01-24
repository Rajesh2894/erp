package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.TbDepositEntity;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbDepositReceiptRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.domain.WmsRaBillTaxDetails;
import com.abm.mainet.workManagement.domain.WorksRABill;
import com.abm.mainet.workManagement.dto.WmsRaBillTaxDetailsDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.repository.TenderInitiationRepository;
import com.abm.mainet.workManagement.repository.WorksRABillRepository;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class WorksRABillServiceImpl implements WorksRABillService {

    @Autowired
    private WorksRABillRepository worksRABillRepository;
    
    @Autowired
    private IOrganisationService orgService;
    
    @Resource
    private TenderInitiationRepository tenderInitiationRepository;
    
    @Resource
    private TbDepositReceiptRepository depositReceiptRepository;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Override
    @Transactional(readOnly = true)
    public WorksRABillDto getRaBillByRaId(Long raId) {

        WorksRABillDto billDto = new WorksRABillDto();
        WmsRaBillTaxDetailsDto billTaxDetailsDto = null;
        List<WmsRaBillTaxDetailsDto> billTaxDetailsDtoList = new ArrayList<>();
        List<WmsRaBillTaxDetailsDto> billTaxDetailsListDto = new ArrayList<>();
        BigDecimal totalTaxAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        WorksRABill worksRABill = worksRABillRepository.findOne(raId);

        if (worksRABill != null) {
            BeanUtils.copyProperties(worksRABill, billDto);
            billDto.setRaDate(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(worksRABill.getRaGeneratedDate()));

            List<TbTaxMas> tdsList = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                    .findAllByOrgId(worksRABill.getOrgId());      
            for (WmsRaBillTaxDetails wmsRaBillTaxDetails : worksRABill.getRaBillTaxDetails()) {

                billTaxDetailsDto = new WmsRaBillTaxDetailsDto();
                BeanUtils.copyProperties(wmsRaBillTaxDetails, billTaxDetailsDto);

                for (TbTaxMas tbTaxMas : tdsList) {
                	if(wmsRaBillTaxDetails.getTaxId() != null) {
                    if (tbTaxMas.getTaxId().longValue() == wmsRaBillTaxDetails.getTaxId().longValue()) {
                        billTaxDetailsDto.setTaxDesc(tbTaxMas.getTaxDesc());
                        if (billTaxDetailsDto.getRaTaxType() != null) {
                            if (billTaxDetailsDto.getRaTaxType().equals(MainetConstants.FlagA)) {
                                totalTaxAmount = totalTaxAmount.add(billTaxDetailsDto.getRaTaxValue());
                            } else {
                                totalTaxAmount = totalTaxAmount.subtract(billTaxDetailsDto.getRaTaxValue());
                            }
                            break;
                        } else {
                        	billTaxDetailsListDto.add(billTaxDetailsDto);
                        }

                    }
                	}
                	
                }
                if (billTaxDetailsDto.getRaTaxType() != null)
                    billTaxDetailsDtoList.add(billTaxDetailsDto);
            }            
            billDto.setRaBillTaxDetails(billTaxDetailsDtoList);
            billDto.setRaBillTaxDtoWith(billTaxDetailsListDto);

            billDto.setTotalTaxAmount(totalTaxAmount);

            if (billDto.getRaTaxAmt() != null) {
                billDto.setAbsoluteTaxAmt(billDto.getRaTaxAmt().abs());
            } else {
                billDto.setAbsoluteTaxAmt(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
            }

            BigDecimal previosWithHeldAmount = getAllPreviousWithHeldAmount(billDto.getWorkId(), billDto.getOrgId(),
                    billDto.getRaSerialNo());
            if (previosWithHeldAmount != null) {
                billDto.setTotalPreviosWithHeldAmount(previosWithHeldAmount);
            } else {
                billDto.setTotalPreviosWithHeldAmount(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
            }

            String ids = billDto.getRaMbIds();
            if (ids != null && !ids.isEmpty()) {
                String array[] = ids.split(MainetConstants.operator.COMMA);
                for (String id : array) {
                    billDto.getMbId().add(Long.parseLong(id));
                }
            }
        }

        return billDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorksRABillDto> getRaBillListByProjAndWorkNumber(Long projId, Long workId, Long orgId) {

        List<WorksRABillDto> billDtos = new ArrayList<>();
        WorksRABillDto billDto = null;
        List<WorksRABill> raBills = worksRABillRepository.getRaBillListByProjAndWorkNumber(projId, workId, orgId);

        for (WorksRABill worksRABill : raBills) {
            billDto = new WorksRABillDto();
            BeanUtils.copyProperties(worksRABill, billDto);
            billDto.setRaDate(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(worksRABill.getRaGeneratedDate()));
            billDtos.add(billDto);
        }
        return billDtos;
    }

    @Override
    @Transactional
    public WorksRABillDto saveAndUpdateRaBill(WorksRABillDto worksRABillDto, List<Long> raTaxIds, Long deptId,List<WmsRaBillTaxDetailsDto> billTaxDetailsDtos) {

        WorksRABill billEntity = new WorksRABill();
        WmsRaBillTaxDetails billTaxDetails = null;
        List<WmsRaBillTaxDetails> billTaxDetailsList = new ArrayList<>();

        // used to generate serial number for RA Bill
        if (worksRABillDto.getRaSerialNo() == null) {
            final Long sequence = seqGenFunctionUtility.generateSequenceNo(
                    MainetConstants.WorksManagement.WORKS_MANAGEMENT, MainetConstants.WorksManagement.TB_WMS_RABILL,
                    MainetConstants.WorksManagement.RA_SERIAL_NO, worksRABillDto.getOrgId(), MainetConstants.FlagC,
                    worksRABillDto.getWorkId());
            worksRABillDto.setRaSerialNo(sequence);
        }

        if (worksRABillDto.getRaStatus()!=null && worksRABillDto.getRaStatus().equals(MainetConstants.FlagS) && worksRABillDto.getRaCode() == null) {
            String workRaNo = generateRaNumber(worksRABillDto.getRaGeneratedDate(), deptId, worksRABillDto.getOrgId());
            worksRABillDto.setRaCode(workRaNo);
        }
        BeanUtils.copyProperties(worksRABillDto, billEntity);
        StringJoiner joiner = new StringJoiner(MainetConstants.operator.COMMA);

        if (worksRABillDto.getMbId() != null && !worksRABillDto.getMbId().isEmpty()) {
            for (Long value : worksRABillDto.getMbId()) {
                joiner.add(value.toString());
            }
            String joined = joiner.toString();
            billEntity.setRaMbIds(joined);
        }

        for (WmsRaBillTaxDetailsDto wmsRaBillTaxDetails : worksRABillDto.getRaBillTaxDetails()) {
            billTaxDetails = new WmsRaBillTaxDetails();
            BeanUtils.copyProperties(wmsRaBillTaxDetails, billTaxDetails);
            billTaxDetails.setWorksRABill(billEntity);
            billTaxDetailsList.add(billTaxDetails);
        }
        billEntity.setRaBillTaxDetails(billTaxDetailsList);

        WorksRABill savedEntity = worksRABillRepository.save(billEntity);
        WorksRABillDto savedDto = new WorksRABillDto();
        BeanUtils.copyProperties(savedEntity, savedDto);

        if (raTaxIds != null && !raTaxIds.isEmpty()) {
            worksRABillRepository.deleteRaTaxDetails(raTaxIds);
        }
        
        if (worksRABillDto.getRaStatus()!=null && worksRABillDto.getRaStatus().equals(MainetConstants.FlagS) && worksRABillDto.getRaCode() != null && !billTaxDetailsDtos.isEmpty()) {
        	billTaxDetailsDtos.forEach(entity->{
        		if(entity.getRaTaxType()!=null) {
	        		TbDepositEntity depositEntity=new TbDepositEntity();
	            	depositEntity.setDepNo(getDepositNumber(worksRABillDto.getOrgId(),deptId));
	            	depositEntity.setOrgId(entity.getOrgId());
	            	depositEntity.setCreatedBy(savedEntity.getCreatedBy());
	            	depositEntity.setCreatedDate(new Date());
	            	depositEntity.setLgIpMac(savedEntity.getLgIpMac());
	            	depositEntity.setDepAmount(entity.getRaTaxValue());
	            	depositEntity.setDepDate(new Date());
	            	depositEntity.setDpDeptId(deptId);
	            	depositEntity.setDepRefId(savedEntity.getWorkId());
	            	depositEntity.setVendorId(tenderInitiationRepository.getVendorByWorkId(savedEntity.getWorkId()));
	            	depositEntity.setDepType(CommonMasterUtility.getLookUpFromPrefixLookUpValue("S", "DTY", 0,orgService.getOrganisationById(entity.getOrgId())).getLookUpId());
	            	depositEntity.setDepNarration("");
	            	Long status=CommonMasterUtility.getLookUpFromPrefixLookUpValue("O", "RBY", 0,orgService.getOrganisationById(entity.getOrgId())).getLookUpId();
	            	depositEntity.setDepStatus(status.toString());
	            	depositReceiptRepository.save(depositEntity);
        		}
            	
        	});
        	
        }

        return savedDto;

    }
    
    public Long getDepositNumber(Long orgId,Long deptId) {
        return seqGenFunctionUtility.generateSequenceNo("COM", "TB_DEPOSIT", "DEP_NO", orgId,
                MainetConstants.FlagF, deptId);
    }

    private String generateRaNumber(Date raGeneratedDate, Long deptId, Long orgId) {

        // get financial by date
        FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
                .getFinanciaYearByDate(raGeneratedDate);

        // get financial year formate
        String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

        // gerenerate sequence
        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
                MainetConstants.WorksManagement.TB_WMS_RABILL, MainetConstants.WorksManagement.RA_CODE, orgId,
                MainetConstants.FlagC, financiaYear.getFaYear());
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class).getDeptCode(deptId);

        String workRaNumber = MainetConstants.WorksManagement.RATE_TYPE + MainetConstants.WINDOWS_SLASH + deptCode
                + MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
                + String.format(MainetConstants.CommonMasterUi.PADDING_SIX, sequence);

        return workRaNumber;

    }

    @Override
    @Transactional
    public void updateBillDetails(Long raNumber, String billNumber) {
        worksRABillRepository.updateBillDetails(raNumber, billNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public WorksRABillDto getRABillDetailsByRaCode(String raCode, Long orgId) {
        WorksRABillDto billDto = new WorksRABillDto();
        WmsRaBillTaxDetailsDto billTaxDetailsDto = null;
        List<WmsRaBillTaxDetailsDto> billTaxDetailsDtoList = new ArrayList<>();

        WorksRABill worksRABill = worksRABillRepository.getRaDetailsByRaCode(raCode, orgId);
        BeanUtils.copyProperties(worksRABill, billDto);
        billDto.setRaDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(worksRABill.getRaGeneratedDate()));
        for (WmsRaBillTaxDetails wmsRaBillTaxDetails : worksRABill.getRaBillTaxDetails()) {
            billTaxDetailsDto = new WmsRaBillTaxDetailsDto();
            BeanUtils.copyProperties(wmsRaBillTaxDetails, billTaxDetailsDto);
            billTaxDetailsDtoList.add(billTaxDetailsDto);
        }
        billDto.setRaBillTaxDetails(billTaxDetailsDtoList);
        String ids = billDto.getRaMbIds();
        if (ids != null && !ids.isEmpty()) {
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                billDto.getMbId().add(Long.parseLong(id));
            }
        }
        return billDto;
    }

    @Override
    @Transactional
    public void updateStatusByRaId(Long raId, String flag) {
        worksRABillRepository.updateRaStatusById(raId, flag);
    }

    @Override
    @Transactional(readOnly = true)
    public WorksRABillDto getPreviousRaBillDetails(Long workId, Long orgId, Long currentRaId) {
        WorksRABillDto billDto = new WorksRABillDto();
        WorksRABill worksRABill = worksRABillRepository.getPreviousRaBillDetails(workId, orgId, currentRaId);
        if (worksRABill != null) {
            BeanUtils.copyProperties(worksRABill, billDto);
            billDto.setRaDate(
                    new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(worksRABill.getRaGeneratedDate()));
            billDto.setRaBillAmtStr(Utility.convertBigNumberToWord(worksRABill.getRaBillAmt()));
        }
        return billDto;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAllPreviousWithHeldAmount(Long workId, Long orgId, Long serialNo) {
        return worksRABillRepository.getAllPreviousWithHeldAmount(workId, orgId, serialNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorksRABillDto> getRaBillByRaCode(Long orgId) {
        WorksRABillDto billDto = null;
        List<WorksRABillDto> dtolist = new ArrayList<>();
        List<WorksRABill> worksRACodeEntity = worksRABillRepository.getRaBillByRaCode(orgId);
        for (WorksRABill raBillEntity : worksRACodeEntity) {
            billDto = new WorksRABillDto();
            BeanUtils.copyProperties(raBillEntity, billDto);
            dtolist.add(billDto);
        }
        return dtolist;

    }
    

    @Override
	public List<WorksRABillDto> getAllRaDetailforSchedular(Long orgId) {
		List<WorksRABillDto> worksRABillDtos=new ArrayList<WorksRABillDto>();
		List<WorksRABill> worksRABills=worksRABillRepository.getAllRaDetailforSchedular(orgId);
		
		for (WorksRABill raBillEntity : worksRABills) {
			WorksRABillDto billDto = new WorksRABillDto();
            BeanUtils.copyProperties(raBillEntity, billDto);
            worksRABillDtos.add(billDto);
        }
		
		return worksRABillDtos;
	}
    
    @Override
    @Transactional
    public void updateRaBillTypeById(Long raId, String flag) {
        worksRABillRepository.updateRaBillTypeById(raId, flag);
    }
}
