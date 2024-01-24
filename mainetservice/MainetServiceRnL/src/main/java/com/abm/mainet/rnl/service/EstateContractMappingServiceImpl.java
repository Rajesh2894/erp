package com.abm.mainet.rnl.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dao.IContractAgreementDao;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractInstalmentDetailDTO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rnl.dao.EstateContractMappingDao;
import com.abm.mainet.rnl.domain.EstateContractMapping;
import com.abm.mainet.rnl.domain.EstateEntity;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.dto.ContractPropListDTO;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstateContPrintDTO;
import com.abm.mainet.rnl.repository.EstateContractMappingRepository;
import com.abm.mainet.rnl.repository.EstatePropertyRepository;
import com.abm.mainet.rnl.repository.RLBillMasterRepository;

/**
 * @author ritesh.patil
 *
 */
@Service
@Repository
public class EstateContractMappingServiceImpl implements IEstateContractMappingService {

    private static Logger logger = Logger.getLogger(EstateContractMappingServiceImpl.class);

    @Autowired
    private EstateContractMappingRepository estateContractMappingRepository;

    @Autowired
    private ContractAgreementRepository contractAgreementRepository;
    
    @Autowired
    private RLBillMasterRepository rLBillMasterRepository;

    @Autowired
    private IRLBILLMasterService iRLBILLMasterService;

    @Autowired
    private IContractAgreementDao iContractAgreementDao;
    
    @Resource
    private ServiceMasterService serviceMasterService;
    
    @Resource
    private IServiceMasterDAO iServiceMasterDAO;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private EstateContractMappingDao estateContractMappingDao;
    
    @Autowired
    private EstatePropertyRepository estatePropertyRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#save()
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(final EstateContMappingDTO estateContMappingDTO) {
        final List<EstateContractMapping> estateContractMappings = new ArrayList<>();
        EstatePropertyEntity estatePropertyEntity = null;
        ContractMastEntity contractMastEntity = null;
        EstateEntity estateEntity = null;
        EstateContractMapping entity = null;
        
        Organisation organisation = new Organisation();
		organisation.setOrgid(estateContMappingDTO.getOrgId());
		
        for (final ContractPropListDTO contractPropListDTO : estateContMappingDTO.getContractPropListDTO()) {
            estatePropertyEntity = new EstatePropertyEntity();
            contractMastEntity = new ContractMastEntity();
            estateEntity = new EstateEntity();
            entity = new EstateContractMapping();
            estatePropertyEntity.setPropId(contractPropListDTO.getPropId());
            entity.setEstatePropertyEntity(estatePropertyEntity);
            estateEntity.setEsId(estateContMappingDTO.getEsId());
            entity.setEstateEntity(estateEntity);
            contractMastEntity.setContId(estateContMappingDTO.getContractId());
            entity.setContractMastEntity(contractMastEntity);
            entity.setActive(MainetConstants.RnLCommon.Y_FLAG);
            entity.setCreatedDate(new Date());
            entity.setLgIpMac(estateContMappingDTO.getLgIpMac());
            entity.setCreatedBy(estateContMappingDTO.getCreatedBy());
            entity.setOrgId(estateContMappingDTO.getOrgId());
            
            if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL) 
            		&& estateContMappingDTO.getContractMappingDTO().getTypeOfAffected() != null) {
            	entity.setTypesOfAffected(estateContMappingDTO.getContractMappingDTO().getTypeOfAffected());
            }
            
            estateContractMappings.add(entity);
        }
        estateContractMappingRepository.save(estateContractMappings);
        final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
                .finAllRecords(estateContMappingDTO.getContractId(), MainetConstants.RnLCommon.Y_FLAG);

        RLBillMaster billMaster = null;
        final List<RLBillMaster> rLBillMasterList = new ArrayList<>();

        for (final ContractInstalmentDetailEntity contractInstalmentDetailEntity : contractInstalmentDetailEntities) {
            billMaster = new RLBillMaster();
            billMaster.setActive(MainetConstants.RnLCommon.Y_FLAG);
            billMaster.setCreatedDate(new Date());
            billMaster.setLgIpMac(estateContMappingDTO.getLgIpMac());
            billMaster.setOrgId(estateContMappingDTO.getOrgId());
            billMaster.setCreatedBy(estateContMappingDTO.getCreatedBy());
            billMaster.setContId(estateContMappingDTO.getContractId());

            billMaster.setBillDate(new Date());
            billMaster.setBalanceAmount(contractInstalmentDetailEntity.getConitAmount());
            billMaster.setAmount(contractInstalmentDetailEntity.getConitAmount());
            billMaster.setConitId(contractInstalmentDetailEntity.getConitId());
            billMaster.setPaidFlag(MainetConstants.RnLCommon.N_FLAG);
            billMaster.setPartialPaidFlag(MainetConstants.RnLCommon.N_FLAG);
            billMaster.setTaxId(contractInstalmentDetailEntity.getTaxId());

            billMaster.setStartDate(contractInstalmentDetailEntity.getConitStartDate());
            billMaster.setDueDate(contractInstalmentDetailEntity.getConitDueDate());
            billMaster.setBmType("B");
            rLBillMasterList.add(billMaster);
        }

        iRLBILLMasterService.save(rLBillMasterList);
        contractAgreementRepository.updateContractMapFlag(estateContMappingDTO.getContractId(),
                estateContMappingDTO.getCreatedBy());
        
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL)) {
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CTA,
					estateContMappingDTO.getOrgId());
			if (serviceMas.getSmFeesSchedule() == 0) {
				String wfRefno = initializeWorkFlowForFreeService(estateContMappingDTO);
				entity.setWfRefno(wfRefno);
			}
		}
        
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public EstateContMappingDTO findByContractId(final Long contId) {
        final List<EstateContractMapping> estateContractMappingList = estateContractMappingRepository
                .findByContractMastEntityContId(contId);
        final EstateContMappingDTO mappingDTO = new EstateContMappingDTO();
        final List<ContractPropListDTO> contractPropListDTOs = new ArrayList<>();
        ContractPropListDTO contractPropListDTO = null;
        for (final EstateContractMapping estateContractMapping : estateContractMappingList) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(estateContractMapping.getOrgId());
            contractPropListDTO = new ContractPropListDTO();
            contractPropListDTO.setPropId(estateContractMapping.getEstatePropertyEntity().getPropId());
            contractPropListDTO.setPropertyNo(estateContractMapping.getEstatePropertyEntity().getCode());
            // D#39604
            if (estateContractMapping.getEstatePropertyEntity().getFloor() != null) {
                contractPropListDTO.setFloor(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estateContractMapping.getEstatePropertyEntity().getFloor(), organisation)
                        .getLookUpDesc());
            } else {
                contractPropListDTO.setFloor(StringUtils.EMPTY);
            }
            // D#77620
            contractPropListDTO.setUsage(CommonMasterUtility
                    .getHierarchicalLookUp(estateContractMapping.getEstatePropertyEntity().getUsage(), organisation)
                    .getLookUpDesc());
            contractPropListDTO.setUnit(estateContractMapping.getEstatePropertyEntity().getUnitNo() != null
                    ? estateContractMapping.getEstatePropertyEntity().getUnitNo().toString()
                    : "");
            contractPropListDTO.setTotalArea(estateContractMapping.getEstatePropertyEntity().getTotalArea().toString());
            contractPropListDTO.setPropName(estateContractMapping.getEstatePropertyEntity().getName());
            contractPropListDTOs.add(contractPropListDTO);
        }
        for (final EstateContractMapping estateContractMapping : estateContractMappingList) {
            mappingDTO.setEsId(estateContractMapping.getEstateEntity().getEsId());
            mappingDTO.setCode(estateContractMapping.getEstateEntity().getCode());
            mappingDTO.setNameEng(estateContractMapping.getEstateEntity().getNameEng());
            mappingDTO.setNameReg(estateContractMapping.getEstateEntity().getNameReg());
            break;
        }
        mappingDTO.setContractPropListDTO(contractPropListDTOs);
        logger.info("findByContractId(Long contId) execution ends");
        return mappingDTO;
    }

    @Override
    public EstateContPrintDTO findContractPrintValues(final Long orgId, final Long contId) {
        final List<Object[]> list = iContractAgreementDao.findPrintContractAgreementByContId(orgId, contId);
        EstateContPrintDTO estateContPrintDTO = null;
        for (final Object[] object : list) {
            estateContPrintDTO = new EstateContPrintDTO();
            estateContPrintDTO.setOrgName(object[0].toString());
            estateContPrintDTO.setOrgNameReg(object[1].toString());
            estateContPrintDTO.setOrgCode(object[2].toString());
            estateContPrintDTO.setContId(object[3].toString());
            estateContPrintDTO.setTendorNo(object[4].toString());
            estateContPrintDTO.setToDate(object[5].toString());
            estateContPrintDTO.setRsoNo(object[6].toString());
            estateContPrintDTO.setRsoDate(Utility.dateToString((Date) object[7]));
            estateContPrintDTO.setFromDate(Utility.dateToString((Date) object[8]));
            estateContPrintDTO.setToDate(Utility.dateToString((Date) object[9]));
            if (object[10] != null) {
                estateContPrintDTO.setAmount(object[10].toString());
            }
            estateContPrintDTO.setRepresentedBy(object[11].toString());
            estateContPrintDTO.setDesgName(object[12].toString());
            break;
        }
        return estateContPrintDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findCountForProperty(final Long orgId, final Long propId) {
        boolean count = true;
        if (estateContractMappingRepository.findCountForProperty(orgId, propId) > 0L) {
            count = false;
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContractDeptWise(final Long orgId, final TbDepartment tbDepartment,
            final String flag) {

        List<ContractMastEntity> contractMastList = null;
        if (flag.equals("E")) {
            contractMastList = estateContractMappingRepository.findContractDeptWiseExist(orgId, tbDepartment.getDpDeptid());
        } else {
            contractMastList = estateContractMappingRepository.findContractDeptWiseNotExist(orgId,
                    tbDepartment.getDpDeptid());
        }
        return getContactList(contractMastList, tbDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContract(final Long orgId, final String contractNo, final Date contactDate,
            final TbDepartment tbDepartment) {
        final List<ContractMastEntity> contractMastList = estateContractMappingRepository.findContractsExist(orgId,
                tbDepartment.getDpDeptid(), contractNo, contactDate);
        return getContactList(contractMastList, tbDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractMappingDTO> findContractsByContractId(final Long orgId, final Long contId,
            final TbDepartment tbDepartment) {
        final List<ContractMastEntity> contractMastList = contractAgreementRepository.findContractsView(orgId, contId);
        return getContactList(contractMastList, tbDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getCountContractDeptWiseNotExist(final Long orgId, final Long deptId) {
        boolean val = false;
        final Long count = estateContractMappingRepository.getCountContractDeptWiseNotExist(orgId, deptId);
        if (count > 0) {
            val = true;
        }
        return val;
    }

    private List<ContractMappingDTO> getContactList(final List<ContractMastEntity> contractMastEntity,
            final TbDepartment tbDepartment) {
        ContractMappingDTO contractMappingDTO = null;
        List<ContractPart2DetailEntity> contractPart2DetailEntities = null;
        List<ContractDetailEntity> contractDetailEntities = null;
        final List<ContractMappingDTO> contractMastDTOs = new ArrayList<>();
        for (final ContractMastEntity contractMastEntity2 : contractMastEntity) {
            contractMappingDTO = new ContractMappingDTO();
            contractMappingDTO.setContId(contractMastEntity2.getContId());
            contractMappingDTO.setContractNo(contractMastEntity2.getContNo());
            contractMappingDTO.setContDate(Utility.dateToString(contractMastEntity2.getContDate()));
            contractMappingDTO.setDeptName(tbDepartment.getDpDeptdesc());
            contractMappingDTO.setDeptNameReg(tbDepartment.getDpNameMar());
            contractPart2DetailEntities = contractMastEntity2.getContractPart2List();
            for (final ContractPart2DetailEntity contractPart2DetailEntity : contractPart2DetailEntities) {
            	if(contractPart2DetailEntity.getContp2Type()!=null && !contractPart2DetailEntity.getContp2Type().isEmpty() && contractPart2DetailEntity.getContp2Primary()!=null && !contractPart2DetailEntity.getContp2Primary().isEmpty()) {
                if (contractPart2DetailEntity.getContp2Type().equals("V")
                        && contractPart2DetailEntity.getContp2Primary().equals("Y")) {
                	if(contractPart2DetailEntity.getContp2Name()!=null && !contractPart2DetailEntity.getContp2Name().isEmpty()) {
                    contractMappingDTO.setRepresentedBy(contractPart2DetailEntity.getContp2Name());
                	}
                    // contractMappingDTO.setVendorName(contractPart2DetailEntity.getVmVendorid().toString());
                    // defect #30322
                    String vendorName = ApplicationContextProvider.getApplicationContext()
                            .getBean(TbAcVendormasterService.class)
                            .getVendorNameById(contractPart2DetailEntity.getVmVendorid(), contractPart2DetailEntity.getOrgId());
                    if(StringUtils.isNotEmpty(vendorName) || contractPart2DetailEntity.getVmVendorid() != null) {
                    contractMappingDTO.setVendorName(
                            vendorName != null ? vendorName : contractPart2DetailEntity.getVmVendorid().toString());
                    }
                    break;
                }
            }
            }
            contractDetailEntities = contractMastEntity2.getContractDetailList();
            for (final ContractDetailEntity contractDetailEntity : contractDetailEntities) {
                contractMappingDTO.setToDate(CommonUtility.dateToString(contractDetailEntity.getContToDate()));
                contractMappingDTO.setFromDate(CommonUtility.dateToString(contractDetailEntity.getContFromDate()));
            }
            contractMastDTOs.add(contractMappingDTO);
        }
        return contractMastDTOs;
    }

    @Transactional
    public void updateContractCloseFlag(String contCloseFlag, Long contId, Long empId) {
        estateContractMappingRepository.updateContractCloseFlag(contCloseFlag, contId, empId);
    }

    @Override
    public List<EstateContMappingDTO> findByPropertyId(Long propId) {
        List<EstateContMappingDTO> list = new ArrayList<>();
        List<EstateContractMapping> contractMaps = estateContractMappingRepository.findByEstatePropertyEntityPropId(propId);
        for (EstateContractMapping contractMap : contractMaps) {
            EstateContMappingDTO mappingDTO = new EstateContMappingDTO();
            mappingDTO.setContractId(contractMap.getContractMastEntity().getContId());
            mappingDTO.setEsId(contractMap.getEstateEntity().getEsId());
            mappingDTO.setMappingId(contractMap.getMappingId());
           
            list.add(mappingDTO);
        }
        return list;
    }

    @Override
    public List<Object[]> fetchEstatePropertyForBillPay(String status, Long orgId) {
        List<Object[]> objArrays = new ArrayList<>();
        List<EstateContractMapping> estateProperties = estateContractMappingRepository.fetchPropertiesForBillPay(orgId, status);
        for (EstateContractMapping cm : estateProperties) {
            Object[] obj = new Object[6];
            obj[0] = cm.getContractMastEntity().getContId();
            obj[1] = cm.getContractMastEntity().getContNo();
            obj[2] = cm.getEstatePropertyEntity().getPropId();
            obj[3] = cm.getEstatePropertyEntity().getCode();
            obj[4] = cm.getEstatePropertyEntity().getName();
            obj[5] = cm.getEstatePropertyEntity().getUsage();
            objArrays.add(obj);
        }
        return objArrays;
    }

    @Override
    public boolean propertyExistInContractPeriosd(Long orgId, Long propId) {
        List<EstateContractMapping> contractMaps = estateContractMappingRepository.findByEstatePropertyEntityPropId(propId);
        // check in between date or not ask to sir
        for (EstateContractMapping ec : contractMaps) {
            // get contract data for start date and to date
            ContractDetailDTO contractDetails = ApplicationContextProvider.getApplicationContext()
                    .getBean(IContractAgreementService.class).getContractDetail(ec.getContractMastEntity().getContId());
            if (contractDetails != null) {
                Date contFromDate = contractDetails.getContFromDate();
                Date contEndDate = contractDetails.getContToDate();
                if (contFromDate.compareTo(new Date()) * new Date().compareTo(contEndDate) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Transactional
	private String initializeWorkFlowForFreeService(EstateContMappingDTO estateContMappingDTO) {
		boolean checkList = false;
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(MainetConstants.EstateContract.CTA,
				estateContMappingDTO.getOrgId());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(UserSession.getCurrent().getEmployee().getEmpname());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode("RL"));
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(estateContMappingDTO.getOrgId());
		
		String workflowRefNumber = generateWorkflowRefNumber(estateContMappingDTO.getOrgId());
		applicationMetaData.setApplicationId(Long.parseLong(workflowRefNumber));
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
		return workflowRefNumber;
    }
    
    @Override
    @WebMethod(exclude = true)
    public String generateWorkflowRefNumber(Long orgId) {
		String workflowRefNo = null;
		Long generateSequenceNo = ApplicationContextProvider.getApplicationContext()
                .getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(MainetConstants.RNL_DEPT_CODE, "TB_RL_EST_CONTRACT_MAPPING",
                		"WF_REFNO", orgId, null, null);
		
		FinancialYear financialYear = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		
		//int noticeFinYear = 0;
        StringBuilder refNumber = new StringBuilder();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String currentDate = dateFormat.format(new Date());
       
//        if (financialYear != null) {
//        	noticeFinYear = Utility.getCurrentYear();
//        }
		
        refNumber.append(orgId);
        //refNumber.append(noticeFinYear);
        refNumber.append(currentDate);
        refNumber.append(String.format("%05d", generateSequenceNo));
        
        workflowRefNo = refNumber.toString();
		return workflowRefNo;
	}
    
    @Override
	public EstateContMappingDTO findPropIdByAppId(String applicationId, long orgId) {
    	EstateContractMapping entity = estateContractMappingRepository.getPropIdByAppId(applicationId,orgId);
    	EstateContMappingDTO dto = new EstateContMappingDTO();
    	BeanUtils.copyProperties(entity, dto);
    	
    	dto.setEsId(entity.getEstateEntity().getEsId());
    	dto.setNameEng(entity.getEstateEntity().getNameEng());
    	dto.setNameReg(entity.getEstateEntity().getNameReg());
    	dto.setPropId(entity.getEstatePropertyEntity().getPropId());
    	dto.setPropName(entity.getEstatePropertyEntity().getName());
    	dto.setAssesmentPropId(entity.getEstatePropertyEntity().getAssesmentPropId());
    	dto.setUsage(entity.getEstatePropertyEntity().getUsage());
    	dto.setUnitNo(entity.getEstatePropertyEntity().getUnitNo());
    	dto.setFloor(entity.getEstatePropertyEntity().getFloor());
    	dto.setTotalArea(entity.getEstatePropertyEntity().getTotalArea());
    	dto.setCode(entity.getEstatePropertyEntity().getCode());
    	dto.getContractMappingDTO().setContractNo(entity.getContractMastEntity().getContNo());
    	dto.getContractMappingDTO().setContractDate(entity.getContractMastEntity().getContDate());
    	dto.getContractMappingDTO().setContId(entity.getContractMastEntity().getContId());
		 
		return dto;
	}
    
	@Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
            String serviceShortCode) {

        boolean updateFlag = false;
        try {
            if (StringUtils.equalsIgnoreCase(eventName, serviceShortCode)) {

                if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)) {
                	estateContractMappingRepository.contactMappingApprovalWorkflow(MainetConstants.FlagA,
                            taskAction.getEmpId(), taskAction.getApplicationId());
                } else if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
                        MainetConstants.WorkFlow.Decision.REJECTED)) {
                	estateContractMappingRepository.contactMappingApprovalWorkflow(MainetConstants.FlagR,
                            taskAction.getEmpId(), taskAction.getApplicationId());
                }
                updateWorkflowTaskAction(taskAction, serviceId);
                updateFlag = true;

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return updateFlag;
    }
	
	@Transactional
    @WebMethod(exclude = true)
    private WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {

        WorkflowTaskActionResponse workflowResponse = null;
        try {
            String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getProcessName(serviceId, taskAction.getOrgId());
            if (StringUtils.isNotBlank(processName)) {

                WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
                workflowDto.setProcessName(processName);
                workflowDto.setWorkflowTaskAction(taskAction);
                workflowResponse = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return workflowResponse;
    }
	
	@Override
	public EstateContMappingDTO findContractsByContractNo(Long orgId,String contNo) {
		EstateContMappingDTO dto = new EstateContMappingDTO();
		ContractMastEntity contractMastentity = estateContractMappingRepository.findContractsByContractNo(orgId, contNo);
		EstateContractMapping entity = estateContractMappingRepository.findContractsByContractId(orgId, contractMastentity.getContId());
	    BeanUtils.copyProperties(entity, dto);
	    return dto;
	}
	
	@Override
    public List<Object[]> fetchEstateDetails(String status, Long orgId, Long langId) {
        List<Object[]> objArrays = new ArrayList<>();
        	List<EstateContractMapping> estateDetails = estateContractMappingRepository.fetchEstateDetailsList(orgId, status);
            for (EstateContractMapping cm : estateDetails) {
                Object[] obj = new Object[6];
                obj[0] = cm.getContractMastEntity().getContId();
                obj[1] = cm.getContractMastEntity().getContNo();
                obj[2] = cm.getEstateEntity().getEsId();
                obj[3] = cm.getEstateEntity().getCode();
                if(langId.equals(1L)) {
                	obj[4] = cm.getEstateEntity().getNameEng();
                }else {
                	obj[4] = cm.getEstateEntity().getNameReg();
                }
                obj[5] = cm.getEstatePropertyEntity().getCode();
     
                objArrays.add(obj);
            }
        
        return objArrays;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> searchByData(String contNo, String propertyContractNo, String estateName, String mobileNo, Long orgId ) {
    	List<Object[]> obj = estateContractMappingDao.searchData(contNo, propertyContractNo, estateName, mobileNo, orgId);
    	return obj;
    }
    
    @Override
    public List<Object[]> fetchPropertyByEstateName(String estateName, Long orgId) {
        List<Object[]> objArrays = new ArrayList<>();
        List<EstateContractMapping> estateProperties = estateContractMappingRepository.fetchPropertyByEstateName(estateName, orgId);
        for (EstateContractMapping cm : estateProperties) {
            Object[] obj = new Object[6];
            obj[0] = cm.getContractMastEntity().getContId();
            obj[1] = cm.getContractMastEntity().getContNo();
            obj[2] = cm.getEstatePropertyEntity().getPropId();
            obj[3] = cm.getEstatePropertyEntity().getCode();
            obj[4] = cm.getEstatePropertyEntity().getName();
            obj[5] = cm.getEstateEntity().getCode();
            objArrays.add(obj);
        }
        return objArrays;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
	public void saveContractAdjustment(ContractMastDTO contractMastDTO, Long orgId, int languageId, Long empId,
			String ipAddress, List<Long> removeIds) {
		final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntityList = new ArrayList<>();
		ContractInstalmentDetailEntity contractInstalmentDetailEntity = null;
		final LookUp amtLook = CommonMasterUtility.getValueFromPrefixLookUp("AMT", "VTY");
		ContractMastEntity entity = contractAgreementRepository.getContractByContId(contractMastDTO.getContId(), orgId);
		for (final ContractInstalmentDetailDTO contractInstalmentDetailDTO : contractMastDTO
				.getContractInstalmentDetailList()) {

			if ((contractInstalmentDetailDTO.getActive() != null)
					&& contractInstalmentDetailDTO.getActive().equals(MainetConstants.FlagY)) {
				contractInstalmentDetailEntity = new ContractInstalmentDetailEntity();
				BeanUtils.copyProperties(contractInstalmentDetailDTO, contractInstalmentDetailEntity);
				contractInstalmentDetailEntity.setConitAmtType(amtLook.getLookUpId());
				contractInstalmentDetailEntity.setConitAmount(contractInstalmentDetailDTO.getContAmt());
				contractInstalmentDetailEntity.setConitValue(contractInstalmentDetailDTO.getContAmt());
				contractInstalmentDetailEntity.setConitDueDate(contractInstalmentDetailDTO.getConitDueDate());
				contractInstalmentDetailEntity.setTaxId(contractInstalmentDetailDTO.getTaxId());
				contractInstalmentDetailEntity.setContId(entity);
				contractInstalmentDetailEntity.setOrgId(orgId);
				contractInstalmentDetailEntity.setConttActive(MainetConstants.FlagY);
				contractInstalmentDetailEntity.setConitPrFlag("A");
				if (contractInstalmentDetailEntity.getConitId() == 0L) {
					contractInstalmentDetailEntity.setCreatedBy(empId);
					contractInstalmentDetailEntity.setLgIpMac(ipAddress);
					contractInstalmentDetailEntity.setLmodDate(new Date());
				} else {
					contractInstalmentDetailEntity.setUpdatedBy(empId);
					contractInstalmentDetailEntity.setLgIpMacUpd(ipAddress);
					contractInstalmentDetailEntity.setUpdatedDate(new Date());
				}
				contractInstalmentDetailEntity.setConitStartDate(new Date());

				contractInstalmentDetailEntityList.add(contractInstalmentDetailEntity);
			}
			entity.setContractInstalmentDetailList(contractInstalmentDetailEntityList);
		}
		contractAgreementRepository.save(entity);

	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void saveRlBillAdjustment(Long contId, Long orgId, int languageId, Long empId, String ipAddress,List<Long> removeIds) {
		RLBillMaster billMaster = null;
		final List<RLBillMaster> rLBillMasterList = new ArrayList<>();
		if(removeIds!=null && removeIds.size()>0) {
			for(Long conitId : removeIds) {
				contractAgreementRepository.deleteInstalmentRow(conitId);
				rLBillMasterRepository.deleteInstalmentRow(conitId);
			}
		}
		final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = rLBillMasterRepository
				.findAllAdjRecords(contId, MainetConstants.RnLCommon.Y_FLAG, "A");
		for (final ContractInstalmentDetailEntity contractInstalmentEntity : contractInstalmentDetailEntities) {
			RLBillMaster rlbill = rLBillMasterRepository.getRLBillByConitId(contractInstalmentEntity.getConitId(),
					orgId);
			billMaster = new RLBillMaster();
			billMaster.setActive(MainetConstants.RnLCommon.Y_FLAG);
			if (rlbill!=null && rlbill.getBillId()>0) {
				BeanUtils.copyProperties(rlbill, billMaster);
				billMaster.setUpdatedDate(new Date());
				billMaster.setLgIpMacUp(ipAddress);
				billMaster.setUpdatedBy(empId);
			} else {
				billMaster.setCreatedDate(new Date());
				billMaster.setLgIpMac(ipAddress);
				billMaster.setCreatedBy(empId);
			}

			billMaster.setOrgId(orgId);
			billMaster.setContId(contId);
			billMaster.setBillDate(new Date());
			billMaster.setBalanceAmount(contractInstalmentEntity.getConitAmount());
			billMaster.setAmount(contractInstalmentEntity.getConitAmount());
			billMaster.setConitId(contractInstalmentEntity.getConitId());
			billMaster.setContId(contId);
			billMaster.setPaidFlag(MainetConstants.RnLCommon.N_FLAG);
			billMaster.setPartialPaidFlag(MainetConstants.RnLCommon.N_FLAG);
			billMaster.setTaxId(contractInstalmentEntity.getTaxId());
			billMaster.setStartDate(contractInstalmentEntity.getConitStartDate());
			billMaster.setDueDate(contractInstalmentEntity.getConitDueDate());
			billMaster.setBmType("B");
			rLBillMasterList.add(billMaster);
		}

		iRLBILLMasterService.save(rLBillMasterList);

	}
	
	
	@Override
    public List<Object[]> fetchEstatePropertyDetails(String contNo, Long orgId) {
        return estateContractMappingRepository.fetchEstatePropertyDetails(contNo, orgId);
    }

    @Override
    public void updatePropertyMappingStatus(char flag, Long propId) {
    	estatePropertyRepository.updateStatus(flag, propId);
    }
    
    @Override
    public boolean existsBySmShortdescAndAdditionalRefNo(Long contId) {
        Long count = estateContractMappingRepository.countBySmShortdescAndAdditionalRefNo(contId);
        return count>0;
        
        
    }

}
