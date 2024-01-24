package com.abm.mainet.tradeLicense.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.repository.DishonurChargeEntryRepository;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.objection.repository.ObjectionMastRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.datamodel.MLNewTradeLicense;
import com.abm.mainet.tradeLicense.domain.LicenseDemandRegisterEntity;
import com.abm.mainet.tradeLicense.domain.LicenseDmandRegErrorEntity;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlRenewalMast;
import com.abm.mainet.tradeLicense.domain.TbMlRenewalMastHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.LicenseValidityDto;
import com.abm.mainet.tradeLicense.dto.RenewalHistroyDetails;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.LicenseDemandRegRepo;
import com.abm.mainet.tradeLicense.repository.LicenseDmandRegErrorRepo;
import com.abm.mainet.tradeLicense.repository.RenewalLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.repository.RenewalMasterHistoryRepository;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.repository.TradeMasterHistoryDetailsRepository;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created Date:08/02/2019
 * 
 * @author Gayatri.Kokane
 *
 */

@Service(value = "RenewalLicenseService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService")
@Api(value = "/renewalLicenseApplicationService")
@Path("/renewalLicenseApplicationService")
public class RenewalLicenseApplicationServiceImpl implements IRenewalLicenseApplicationService {

	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;

	@Autowired
	private RenewalLicenseApplicationRepository renewalLicenseApplicationRepository;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private CommonService commonService;
	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	AuditService auditService;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;

	@Autowired
	private TradeMasterHistoryDetailsRepository histRepository;

	@Autowired
	ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private TbLoiMasService tbLoiMasService;
	@Autowired
	TbLoiDetService tbLoiDetService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;
	@Autowired
	private RenewalMasterHistoryRepository renHistortRepo;

	@Autowired
	private ObjectionMastRepository objectionMastRepository;

	@Autowired
	private IReceiptEntryService receiptEntryService;
	@Autowired
	private DishonurChargeEntryRepository tbDisHnrRepo;
	@Autowired
	private TradeMasterHistoryDetailsRepository tradeDetailsHistRepo;

	@Autowired
	private LicenseDemandRegRepo licenseDemandRegRepo;

	@Autowired
	private LicenseDmandRegErrorRepo licenseDmandRegErrorRepo;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
	
	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	private static final Logger LOGGER = Logger.getLogger(RenewalLicenseApplicationServiceImpl.class);

	@Override
	@Transactional
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_UPDATE_RENEWAL_LICENSE_APPLICATION, notes = MainetConstants.TradeLicense.SAVE_UPDATE_RENEWAL_LICENSE_APPLICATION)
	@Path("/saveRenewalLicenseApplication")
	public TradeMasterDetailDTO saveAndUpdateApplication(@RequestBody TradeMasterDetailDTO dto) {

		try {
			Long appicationId = null;
			LOGGER.info("saveAndUpdateApplication started");

			// for changing status of license fro Issued to Tansfer In progress when
			// application punched from Mobile
			Organisation org = null;
			if (dto.getOrgid() != null) {
				org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
						.getOrganisationById(dto.getOrgid());
			} else {
				org = UserSession.getCurrent().getOrganisation();
			}
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", PrefixConstants.LookUp.LIS, org);
			dto.setTrdStatus(lookUp.getLookUpId());

			ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
					MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, dto.getOrgid());
			dto.setServiceId(sm.getSmServiceId());
			// end
//Defect #133557
			DishonourChargeEntity disHnrEnt = null;
			List<Long> appList = tradeDetailsHistRepo.getTradeRenewalAppHist(dto.getTrdLicno(), sm.getSmShortdesc());
			if (CollectionUtils.isNotEmpty(appList)) {
				disHnrEnt = tbDisHnrRepo.getDishonourChargeData(appList.get(0));
				if (disHnrEnt != null) {
					disHnrEnt.setIsDishnrChgPaid(MainetConstants.FlagY);
					tbDisHnrRepo.save(disHnrEnt);
				}
			}

			TbMlRenewalMast renewEntity = mapDtoToEntity(dto, dto.getRenewalMasterDetailDTO());

			TbMlTradeMast masEntity = mapDtoToEntity(dto);

			RequestDTO requestDto = setApplicantRequestDto(dto, sm, dto.getRenewalMasterDetailDTO());
			requestDto.setPayStatus(MainetConstants.PAYMENT.FREE);

			appicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
					.createApplication(requestDto);
			LOGGER.info("application number for new trade licence : " + appicationId);
			renewEntity.setApmApplicationId(appicationId);
			dto.getRenewalMasterDetailDTO().setApmApplicationId(appicationId);
			dto.setApmApplicationId(appicationId);
			masEntity.setApmApplicationId(appicationId);
			// 125706 to save application id in owner and item detail table
			masEntity.getItemDetails().forEach(itemEntity -> {
				itemEntity.setApmApplicationId(renewEntity.getApmApplicationId());
			});
			masEntity.getOwnerDetails().forEach(ownerEntity -> {
				ownerEntity.setApmApplicationId(renewEntity.getApmApplicationId());
			});

			masEntity = tradeLicenseApplicationRepository.save(masEntity);

			renewalLicenseApplicationRepository.save(renewEntity);

			saveHistoryData(masEntity);

			// #129518 to update renewal dates at time of save
			getRenewalFromDateToDatePeriod(dto);

			boolean checklist = false;
			if ((dto.getDocumentList() != null) && !dto.getDocumentList().isEmpty()
					&& dto.getChecklistAppFlag().equalsIgnoreCase(MainetConstants.FlagY)) {
				requestDto.setApplicationId(appicationId);
				requestDto.setPayStatus(MainetConstants.PAYMENT.FREE);
				checklist = fileUploadService.doFileUpload(dto.getDocumentList(), requestDto);
				checklist = true;
			}

			ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();
			ApplicationMetadata applicationData = new ApplicationMetadata();
			applicationData.setApplicationId(appicationId);
			applicationData.setOrgId(dto.getOrgid());
			applicationData.setIsCheckListApplicable(checklist);
			applicationData.setIsFreeService(true);
			// applicationData.setPaymentMode(paymentMode);
			

			if (dto.getRenewalMasterDetailDTO().getCreatedBy() == null)
				applicantDetailDTO.setUserId(dto.getRenewalMasterDetailDTO().getUpdatedBy());
			else
				applicantDetailDTO.setUserId(dto.getRenewalMasterDetailDTO().getCreatedBy());

			applicantDetailDTO.setServiceId(sm.getSmServiceId());
			applicantDetailDTO.setDepartmentId(sm.getTbDepartment().getDpDeptid());

			applicantDetailDTO.setServiceId(sm.getSmServiceId());
			applicantDetailDTO.setDepartmentId(sm.getTbDepartment().getDpDeptid());

			if (dto.getTrdWard1() != null) {
				applicantDetailDTO.setDwzid1(dto.getTrdWard1());
			}
			if (dto.getTrdWard2() != null) {
				applicantDetailDTO.setDwzid2(dto.getTrdWard2());
			}
			if (dto.getTrdWard3() != null) {
				applicantDetailDTO.setDwzid3(dto.getTrdWard3());
			}
			if (dto.getTrdWard4() != null) {
				applicantDetailDTO.setDwzid4(dto.getTrdWard4());
			}
			if (dto.getTrdWard5() != null) {
				applicantDetailDTO.setDwzid5(dto.getTrdWard5());
			}
//Defect #131715
			if (requestDto != null && requestDto.getMobileNo() != null) {
				applicantDetailDTO.setMobileNo(requestDto.getMobileNo());
			}
			// initiate workflow
			LookUp lookup1 = null;
			try {
				lookup1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WWC", "LCC", 1, org);
			} catch (Exception e) {
				LOGGER.error("No prefix found for for workflow based on category ", e);
			}
			WorkflowMas mas = null;
			if (lookup1 != null && lookup1.getOtherField() != null && lookup1.getOtherField().equals("Y")
					&& (dto.isFree())) {
				dto.setOrgid(org.getOrgid());
				String processName = serviceMasterService.getProcessName(sm.getSmServiceId(),org.getOrgid());
		        if (processName != null) {
		            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
		            boolean autoescalate = false;
		            mas = workflowTyepResolverService.resolveServiceWorkflowType(dto.getOrgid(),
							Long.valueOf(sm.getTbDepartment().getDpDeptid()), sm.getSmServiceId(), null, null,
							dto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), dto.getTrdWard1(), dto.getTrdWard2(),
							dto.getTrdWard3(), dto.getTrdWard4(), dto.getTrdWard5());
		            if (mas != null) {
		                String code = CommonMasterUtility
		                        .getNonHierarchicalLookUpObject(mas.getWorkflowMode(), mas.getOrganisation())
		                        .getLookUpCode();
		                if (code.equals("AE")) {
		                    autoescalate = true;
		                }
		                applicationData.setWorkflowId(mas.getWfId());
		            }
		            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
		            applicationData.setIsAutoEscalate(autoescalate);
		            applicationData.setIsFreeService(true);
		            if (MainetConstants.Y_FLAG.equalsIgnoreCase(sm.getSmScrutinyApplicableFlag())) {
		                applicationData.setIsScrutinyApplicable(true);
		            } else {
		                applicationData.setIsScrutinyApplicable(false);
		            }
		            TaskAssignment requesterTaskAssignment = setRequesterTask(applicationData,
		            		applicantDetailDTO);
		            workflowdto.setRequesterTaskAssignment(requesterTaskAssignment);
		            applicationData.setPaymentMode(MainetConstants.PAYMENT.FREE);
		            workflowdto.setApplicationMetadata(applicationData);
		            workflowdto
		                    .setProcessName(processName);
		            if(applicationData.getApplicationId() !=null && applicationData.getReferenceId() ==null) {
		            	
		            	 workflowAction.setApplicationId(applicationData.getApplicationId());
		                 workflowAction.setReferenceId(applicationData.getApplicationId().toString());
		            }else {
						workflowAction.setApplicationId(applicationData.getApplicationId());
						workflowAction.setReferenceId(applicationData.getReferenceId());
		            	
		            }
		            
		            workflowAction.setDateOfAction(new Date());
		            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
		            workflowAction.setOrgId(applicationData.getOrgId());
		            workflowAction.setEmpId(applicantDetailDTO.getUserId());
		            workflowAction.setCreatedBy(applicantDetailDTO.getUserId());
		            workflowAction.setCreatedDate(new Date());
		            workflowdto.setWorkflowTaskAction(workflowAction);
		            try {
		                workflowExecutionService.initiateWorkflow(workflowdto);
		            } catch (Exception e) {
		                throw new FrameworkException("Exception while creating workflow for free services", e);
		            }
		        }	
			}
			  else if (dto.isFree()) {
				commonService.initiateWorkflowfreeService(applicationData, applicantDetailDTO);
			}

			LOGGER.info("saveAndUpdateApplication End");
		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Renewal License Application ", exception);
			throw new FrameworkException("Exception occur while saving Renewal License Application ", exception);
		}
		sendSmsEmail(dto);
		return dto;

	}
	
	private TaskAssignment setRequesterTask(final ApplicationMetadata applicationData,
            final ApplicantDetailDTO applicantDetailsDto) {
        TaskAssignment assignment = new TaskAssignment();
        Set<String> actorIds = new HashSet<>();
        assignment
                .setActorId(applicantDetailsDto.getUserId() + MainetConstants.operator.COMMA + applicantDetailsDto.getMobileNo());
        actorIds.add(Long.toString(applicantDetailsDto.getUserId()));
        actorIds.add(applicantDetailsDto.getMobileNo());
        assignment.setActorIds(actorIds);
        assignment.setOrgId(applicationData.getOrgId());
        return assignment;
    }

	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);
		TbMlTradeMastHist.setHistoryStatus(MainetConstants.FlagU);
		List<TbMlOwnerDetailHist> tbMlOwnerDetailHistList = new ArrayList<>();
		List<TbMlItemDetailHist> tbMlItemDetailHistList = new ArrayList<>();
		masEntity.getOwnerDetails().forEach(ownerEntity -> {

			TbMlOwnerDetailHist tbMlOwnerDetailHistEntity = new TbMlOwnerDetailHist();

			BeanUtils.copyProperties(ownerEntity, tbMlOwnerDetailHistEntity);
			tbMlOwnerDetailHistEntity.setHistoryStatus(MainetConstants.FlagU);
			tbMlOwnerDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlOwnerDetailHistList.add(tbMlOwnerDetailHistEntity);

		});
		masEntity.getItemDetails().forEach(itemEntity -> {

			TbMlItemDetailHist TbMlItemDetailHistEntity = new TbMlItemDetailHist();

			BeanUtils.copyProperties(itemEntity, TbMlItemDetailHistEntity);
			TbMlItemDetailHistEntity.setHistoryStatus(MainetConstants.FlagU);
			TbMlItemDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		auditService.createHistoryForObject(TbMlTradeMastHist);
	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm,
			RenewalMasterDetailDTO renewalDTO) {
//Defect #131715
		List<TradeLicenseOwnerDetailDTO> ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().stream().filter(
				trd -> trd.getTroPr().equals(MainetConstants.FlagA) || trd.getTroPr().equals(MainetConstants.FlagD))
				.collect(Collectors.toList());
		RequestDTO requestDto = new RequestDTO();
		if (CollectionUtils.isNotEmpty(ownerDetails)) {
			requestDto.setEmail(ownerDetails.get(0).getTroEmailid());
			requestDto.setMobileNo(ownerDetails.get(0).getTroMobileno());
			requestDto.setAreaName(ownerDetails.get(0).getTroAddress());
			requestDto.setUserId(ownerDetails.get(0).getCreatedBy());
		}

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setOrgId(renewalDTO.getOrgid());
		requestDto.setLangId((long) renewalDTO.getLangId());

		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(tradeLicenseApplicationService.getOwnersName(tradeMasterDto, MainetConstants.FlagA));

		// requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue());

		return requestDto;
	}

	private TbMlRenewalMast mapDtoToEntity(TradeMasterDetailDTO dto, RenewalMasterDetailDTO renewalDTO) {
		TbMlRenewalMast masEntity = new TbMlRenewalMast();

		BeanUtils.copyProperties(renewalDTO, masEntity);

		if (renewalDTO.getTreId() != null) {
			masEntity.setTreId(renewalDTO.getTreId());
		}
		masEntity.setApmApplicationId(dto.getApmApplicationId());
		masEntity.setTrdId(dto.getTrdId());

		masEntity.setTreLicfromDate(dto.getTrdLicfromDate());
		masEntity.setTreLictoDate(dto.getTrdLictoDate());
		masEntity.setTreStatus(renewalDTO.getTreStatus());
		masEntity.setRenewalPeriod(renewalDTO.getRenewalPeriod());
		masEntity.setCreatedBy(renewalDTO.getCreatedBy());
		masEntity.setCreatedDate(renewalDTO.getCreatedDate());
		masEntity.setLgIpMac(renewalDTO.getLgIpMac());
		masEntity.setLgIpMacUpd(renewalDTO.getLgIpMacUpd());
		masEntity.setOrgid(renewalDTO.getOrgid());

		return masEntity;
	}

	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();
		List<TbMlOwnerDetail> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});
		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setItemDetails(itemdDetailsList);
		masEntity.setTrdLicno(tradeMasterDto.getTrdLicno());
		return masEntity;
	}

	@Override
	@Transactional(readOnly = true)
	public RenewalMasterDetailDTO getRenewalLicenseDetailsByApplicationId(Long applicationId) {
		RenewalMasterDetailDTO masterDto = null;
		try {
			TbMlRenewalMast entity = renewalLicenseApplicationRepository
					.getLicenseDetailsByApplicationId(applicationId);

			LOGGER.info("Renewal Licence Data fetched for application id " + applicationId);
			masterDto = mapEntityToDto(entity);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Renewal License Details ", exception);
			throw new FrameworkException("Exception occur while fetching Renewal License Details ", exception);
		}
		return masterDto;

	}

	private RenewalMasterDetailDTO mapEntityToDto(TbMlRenewalMast masEntity) {

		RenewalMasterDetailDTO masDto = new RenewalMasterDetailDTO();
		List<RenewalMasterDetailDTO> list = new ArrayList<RenewalMasterDetailDTO>();
		BeanUtils.copyProperties(masEntity, masDto);
		list.add(masDto);
		return masDto;

	}

	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		RenewalMasterDetailDTO entity = getRenewalLicenseDetailsByApplicationId(applicationId);
		TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService.getTradeDetailsByTrdId(entity.getTrdId(),
				orgId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (tradeDetail != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (tradeDetail.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(tradeDetail.getTrdWard1());
			}
			if (tradeDetail.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(tradeDetail.getTrdWard2());
			}
			if (tradeDetail.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(tradeDetail.getTrdWard3());
			}
			if (tradeDetail.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(tradeDetail.getTrdWard4());
			}
			if (tradeDetail.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(tradeDetail.getTrdWard5());
			}
		}

		return wardZoneDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public int validateRenewalLicenseCount(Long trdId, Long orgId) {
		return renewalLicenseApplicationRepository.getRenewalCountByLicenseNo(trdId, orgId);
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("RTL", masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class)
				.fetchAllApplicableServiceCharge(sm.getSmServiceId(), organisation.getOrgid(),
						chargeApplicableAt.getLookUpId())
				.stream().sorted(Comparator.comparingLong(TbTaxMasEntity::getTaxDisplaySeq))
				.collect(Collectors.toList());
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			
			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TRC") || taxLookUp.getLookUpCode().equals("TP")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SRC") || taxLookUp.getLookUpCode().equals("SP")) {
				        iterator.remove();
				    }
				}
			}
		}

		List<MLNewTradeLicense> masterList = new ArrayList<>();
		List<MLNewTradeLicense> list = new ArrayList<>();

		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();

		if (masDto.getTrdId() != null) {

			try {
				lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
						masDto.getOrgid());
				lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2,
						masDto.getOrgid());
				lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3,
						masDto.getOrgid());
				lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4,
						masDto.getOrgid());
				lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5,
						masDto.getOrgid());
			} catch (Exception e) {
				LOGGER.info("prefix level not found");
			}

			for (TbTaxMasEntity taxdto : taxesMaster) {

				if (taxdto.getParentCode() == null) {

					for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
						MLNewTradeLicense license = new MLNewTradeLicense();
						Organisation org = UserSession.getCurrent().getOrganisation();
						license.setOrgId(dto.getOrgid());
						license.setServiceCode("RTL");
						Department dept = tbDepartmentService.findDepartmentByCode("ML");
						TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(org.getOrgid(), dept.getDpDeptid(),
								taxdto.getTaxCode());
						String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
								MainetConstants.FlagE, dto.getOrgid());
						license.setTaxType(taxType);
						license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																	// code(category
						license.setTaxCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

						license.setTaxSubCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());

						license.setRateStartDate(todayDate.getTime());
						license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
						license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
						LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
								organisation);
						license.setLicenseType(licenseType.getDescLangFirst());
						license.setArea(dto.getTrdUnit());

						if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
							List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
									.collect(Collectors.toList());
							if (level1 != null && !level1.isEmpty()) {
								license.setItemCategory1(level1.get(0).getDescLangFirst());
								dto.setItemCategory1(level1.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
							List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
									.collect(Collectors.toList());
							if (level2 != null && !level2.isEmpty()) {
								license.setItemCategory2(level2.get(0).getDescLangFirst());
								dto.setItemCategory2(level2.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
							List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
									.collect(Collectors.toList());
							if (level3 != null && !level3.isEmpty()) {
								license.setItemCategory3(level3.get(0).getDescLangFirst());
								dto.setItemCategory3(level3.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
							List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
									.collect(Collectors.toList());
							if (level4 != null && !level4.isEmpty()) {
								license.setItemCategory4(level4.get(0).getDescLangFirst());
								dto.setItemCategory4(level4.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
							List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
									.collect(Collectors.toList());
							if (level5 != null && !level5.isEmpty()) {
								license.setItemCategory5(level5.get(0).getDescLangFirst());
								dto.setItemCategory5(level5.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}

						if (masDto.getRenewalPendingDays() != null)
							license.setSlab2(masDto.getRenewalPendingDays());// pending cycle
						if (masDto.getRenewCycle() != null)
							license.setSlab3(masDto.getRenewCycle());// select cycle

						masterList.add(license);

					}

				}

			}

		}
		List<MLNewTradeLicense> master = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(masterList)) {

			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
			WSResponseDTO responseDTO = new WSResponseDTO();
			WSRequestDTO wsRequestDTO = new WSRequestDTO();

			wsRequestDTO.setDataModel(masterList);

			try {
				LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
				responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					master = setTradeLicenceChargesDTO(responseDTO);
				} else {
					throw new FrameworkException(responseDTO.getErrorMessage());
				}
			} catch (Exception ex) {
				throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
			}
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");

			setChargeToItemsDtoList(master, taxesMaster, masDto);
		}

		for (TbTaxMasEntity taxdto : taxesMaster) {

			if (taxdto.getParentCode() != null) {

				for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
					MLNewTradeLicense license = new MLNewTradeLicense();
					Organisation org = UserSession.getCurrent().getOrganisation();
					license.setOrgId(masDto.getOrgid());
					license.setServiceCode("RTL");
					Department dept = tbDepartmentService.findDepartmentByCode("ML");
					TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(org.getOrgid(), dept.getDpDeptid(),
							taxdto.getTaxCode());
					String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
							MainetConstants.FlagE, masDto.getOrgid());
					license.setTaxType(taxType);
					license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																// code(category
					// and
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxdto.getParentCode(),
							masDto.getOrgid());

					license.setParentTaxCode(ParentTax.getTaxCode()); // subcategory)
					license.setTaxCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

					license.setTaxSubCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());

					master.forEach(model -> {

						if (dto.getItemCategory1().equals(model.getItemCategory1())
								&& dto.getItemCategory2().equals(model.getItemCategory2())
								&& dto.getItemCategory3().equals(model.getItemCategory3())
								&& dto.getItemCategory4().equals(model.getItemCategory4())
								&& dto.getItemCategory5().equals(model.getItemCategory5())
								&& model.getTaxCode().equals(ParentTax.getTaxCode())) {

							if (!Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
								if (model.getTaxType().equals(CommonMasterUtility
										.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {

									license.setParentTaxValue(model.getSlabRate1());

								}
								if (model.getTaxType().equals(CommonMasterUtility
										.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
									license.setParentTaxValue(model.getFlatRate());
								}
							}

						}
					});

					license.setRateStartDate(todayDate.getTime());
					license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
					license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
					LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
							organisation);
					license.setLicenseType(licenseType.getDescLangFirst());
					// #142577-set parentTaxValue for TSCL project
					try {
						if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL))
							license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));
					} catch (Exception e) {
						LOGGER.error("Error ocurred while setting parent tax value in service RTL" + e);
					}
					// category code

					if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
						List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
								.collect(Collectors.toList());
						if (level1 != null && !level1.isEmpty()) {
							license.setItemCategory1(level1.get(0).getDescLangFirst());
							dto.setItemCategory1(level1.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
						List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
								.collect(Collectors.toList());
						if (level2 != null && !level2.isEmpty()) {
							license.setItemCategory2(level2.get(0).getDescLangFirst());
							dto.setItemCategory2(level2.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
						List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
								.collect(Collectors.toList());
						if (level3 != null && !level3.isEmpty()) {
							license.setItemCategory3(level3.get(0).getDescLangFirst());
							dto.setItemCategory3(level3.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
						List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
								.collect(Collectors.toList());
						if (level4 != null && !level4.isEmpty()) {
							license.setItemCategory4(level4.get(0).getDescLangFirst());
							dto.setItemCategory4(level4.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
						List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
								.collect(Collectors.toList());
						if (level5 != null && !level5.isEmpty()) {
							license.setItemCategory5(level5.get(0).getDescLangFirst());
							dto.setItemCategory5(level5.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					// Defect #117408
					// Defect #134494
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)
							|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SUDA)
							|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)
							|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
						long l = Utility.getDaysBetDates(masDto.getTrdLictoDate(), todayDate);
						if (l > 0) {
							license.setNoOfDays(l);
						}
						license.setArea(dto.getTrdUnit());

					}

					if (masDto.getRenewalPendingDays() != null)
						license.setSlab2(masDto.getRenewalPendingDays());// pending cycle
					if (masDto.getRenewCycle() != null)
						license.setSlab3(masDto.getRenewCycle());// select cycle

					list.add(license);

				}
			}
		}

		if (CollectionUtils.isNotEmpty(list)) {
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
			WSResponseDTO responseDto = new WSResponseDTO();
			WSRequestDTO wsRequestDto = new WSRequestDTO();
			List<MLNewTradeLicense> masterResponseList = new ArrayList<>();

			wsRequestDto.setDataModel(list);

			try {
				LOGGER.info("brms ML request DTO  is :" + wsRequestDto.toString());
				responseDto = RestClient.callBRMS(wsRequestDto, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
				if (responseDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					masterResponseList = setTradeLicenceChargesDTO(responseDto);
				} else {
					throw new FrameworkException(responseDto.getErrorMessage());
				}
			} catch (Exception ex) {
				throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
			}
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");
			setChargeToDtoList(masterResponseList, taxesMaster, masDto);
		}

		return masDto;
	}

	private MLNewTradeLicense settingTaxCategories(MLNewTradeLicense mlNewTradeLicense, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				mlNewTradeLicense.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				mlNewTradeLicense.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return mlNewTradeLicense;

	}

	private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense> master,
			List<TbTaxMasEntity> taxesMaster, TradeMasterDetailDTO masDto) {

		Organisation organisation = iOrganisationService.getOrganisationById(masDto.getOrgid());
		if (masDto.getApmApplicationId() != null)
			masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
				master.forEach(model -> {

					double currVal = 0.0d;

					if (entity.getItemCategory1().equals(model.getItemCategory1())
							&& entity.getItemCategory2().equals(model.getItemCategory2())
							&& entity.getItemCategory3().equals(model.getItemCategory3())
							&& entity.getItemCategory4().equals(model.getItemCategory4())
							&& entity.getItemCategory5().equals(model.getItemCategory5())) {

						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {

							entity.setTriRate(BigDecimal.valueOf(model.getSlabRate1()));

							currVal = model.getSlabRate1();

						}
						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
							entity.setTriRate(BigDecimal.valueOf(model.getFlatRate()));
							currVal = model.getFlatRate();
						}

					}

					for (TbTaxMasEntity taxdto : taxesMaster) {
						if (model.getTaxCode().equals(taxdto.getTaxCode())) {
							double totalAmount = masDto.getFeeIds().containsKey(taxdto.getTaxId())
									? masDto.getFeeIds().get(taxdto.getTaxId())
									: 0.0d;
							totalAmount = currVal + totalAmount;
							masDto.getFeeIds().put(taxdto.getTaxId(), totalAmount);

						}

					}

				});
			});

		masDto.setTotalApplicationFee(BigDecimal.valueOf(
				masDto.getTradeLicenseItemDetailDTO().stream().mapToDouble(c -> c.getTriRate().doubleValue()).sum()));

		return masDto;
	}

	private TradeMasterDetailDTO setChargeToDtoList(List<MLNewTradeLicense> master, List<TbTaxMasEntity> taxesMaster,
			TradeMasterDetailDTO masDto) {

		Organisation organisation = iOrganisationService.getOrganisationById(masDto.getOrgid());
		// D#129853 for round off the charge value
		boolean rndOfFlag = Utility.isRoundedOffApplicable(organisation);
		boolean flag = Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL);
		boolean flag1 = Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SUDA);
		if (masDto.getApmApplicationId() != null)

			master.forEach(model -> {
				double currVal = 0.0d;

				if (model.getTaxType().equals(
						CommonMasterUtility.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {
					if (flag || flag1) {
						// D#129853 for round off the charge value
						if (rndOfFlag) {
							currVal = Math.round(model.getSlabRate2());

						} else {
							currVal = model.getSlabRate2();
						}
					} else {
						if (rndOfFlag) {
							currVal = Math.round(model.getSlabRate1());
						} else {
							currVal = model.getSlabRate1();
						}
					}
				}
				if (model.getTaxType().equals(
						CommonMasterUtility.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
					if (rndOfFlag) {
						currVal = Math.round(model.getFlatRate());

					} else {
						currVal = model.getFlatRate();
					}
				}
				if (model.getTaxType().equals(
						CommonMasterUtility.getValueFromPrefixLookUp("P", "FSD", organisation).getDescLangFirst())) {
					if (rndOfFlag) {
						currVal = Math.round(model.getFlatRate());

					} else {
						currVal = model.getFlatRate();
					}
				}

				for (TbTaxMasEntity taxdto : taxesMaster) {
					if (model.getTaxCode().equals(taxdto.getTaxCode())) {

						double totalAmount = masDto.getFeeIds().containsKey(taxdto.getTaxId())
								? masDto.getFeeIds().get(taxdto.getTaxId())
								: 0.0d;
						totalAmount = currVal + totalAmount;

						masDto.getFeeIds().put(taxdto.getTaxId(), totalAmount);

					}

				}

			});

		return masDto;
	}

	private List<MLNewTradeLicense> setTradeLicenceChargesDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setTradeLicenceChargesDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, MLNewTradeLicense.class);
		List<MLNewTradeLicense> finalRateMaster = new ArrayList<>();
		for (Object rate : charges) {
			MLNewTradeLicense masterRate = (MLNewTradeLicense) rate;
			finalRateMaster.add(masterRate);
		}
		LOGGER.info("setTradeLicenceChargesDTO execution end..");
		return finalRateMaster;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		TradeMasterDetailDTO entity = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		TradeMasterDetailDTO tradeLicenceCharges = getTradeLicenceChargesFromBrmsRule(entity);

		return tradeLicenceCharges.getFeeIds();
	}

	@Override
	public void updateRenewalFormData(TradeMasterDetailDTO masDto) throws FrameworkException {

		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();

		masDto.setTradeLicenseItemDetailDTO(tradeLicenseItemDetailDTO);
		TbMlRenewalMast masEntity = mapDtoToEntity(masDto, masDto.getRenewalMasterDetailDTO());

		renewalLicenseApplicationRepository.save(masEntity);

		TbMlTradeMast Entity = mapDtoToEntity(masDto);

		tradeLicenseApplicationRepository.save(Entity);

	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getAppChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceChargesAtApplicationFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {
		// app
		Organisation organisation = new Organisation();

		Organisation org = iOrganisationService.getOrganisationById(masDto.getOrgid());
		organisation.setOrgid(masDto.getOrgid());
		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("RTL", masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility
				.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2, masDto.getOrgid()).getLookUpId();
//Defect #128195
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			setRequestDataForSkdclENV(taxesMaster, masDto, org, masterList, chargeApplicableAt.getDescLangFirst());
		} else {

			for (TbTaxMasEntity taxes : taxesMaster) {

				if ((taxes.getTaxCategory2() == appChargetaxId)) {

					MLNewTradeLicense license = new MLNewTradeLicense();
					license.setOrgId(masDto.getOrgid());
					license.setServiceCode("RTL");
					TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(),
							sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
					String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
							MainetConstants.FlagE, masDto.getOrgid());

					license.setTaxType(taxType);
					license.setTaxCode(taxes.getTaxCode());
					settingTaxCategories(license, taxes, organisation);
					license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
					license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
					if (masDto.getRenewalPendingDays() != null)
						license.setSlab2(masDto.getRenewalPendingDays());// pending cycle
					if (masDto.getRenewCycle() != null)
						license.setSlab3(masDto.getRenewCycle());// select cycle

					// #142577-set parentTaxValue for TSCL project
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_TSCL) && taxes.getParentCode() != null) {
						try {
							masDto.setCheckApptimeCharge(MainetConstants.FlagY);
							TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxes.getParentCode(),
									masDto.getOrgid());
							license.setParentTaxCode(ParentTax.getTaxCode());
							license.setParentTaxValue(tradeLicenseApplicationService.getParentTaxValue(masDto));
						} catch (Exception e) {
							LOGGER.error("Error ocurred while setting parent tax value in service RTL at application time"+ e);
						}
					}
					masterList.add(license);
				}

			}
		}

		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
		WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		List<MLNewTradeLicense> master = new ArrayList<>();

		wsRequestDTO.setDataModel(masterList);

		try {
			LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setTradeLicenceChargesDTO(responseDTO);
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
		}
		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");

		setApplicationChargeToDtoList(master, masDto, taxesMaster);
		// Defect #133557
		setDishonurChargeAmt(masDto);

		return masDto;
	}

//Defect #133557
	private void setDishonurChargeAmt(TradeMasterDetailDTO masDto) {
		tradeLicenseApplicationService.setApplTimeDishonurChargeAmt(masDto,
				MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE);

	}

//Defect #128195
	private void setRequestDataForSkdclENV(List<TbTaxMasEntity> taxesMaster, TradeMasterDetailDTO masDto,
			Organisation organisation, List<MLNewTradeLicense> masterList, String chargeAplAt) {

		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		List<MLNewTradeLicense> master1 = null;

		if (masDto.getTrdId() != null) {

			try {
				lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
						masDto.getOrgid());
				lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2,
						masDto.getOrgid());
				lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3,
						masDto.getOrgid());
				lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4,
						masDto.getOrgid());
				lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5,
						masDto.getOrgid());
			} catch (Exception e) {
				LOGGER.info("prefix level not found");
			}

			for (TbTaxMasEntity taxdto : taxesMaster) {

				if (taxdto.getParentCode() == null) {

					for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
						MLNewTradeLicense license = new MLNewTradeLicense();
						// Organisation org = UserSession.getCurrent().getOrganisation();
						license.setOrgId(dto.getOrgid());
						license.setServiceCode("RTL");
						Department dept = tbDepartmentService.findDepartmentByCode("ML");
						TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(organisation.getOrgid(),
								dept.getDpDeptid(), taxdto.getTaxCode());
						String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
								MainetConstants.FlagE, dto.getOrgid());
						license.setTaxType(taxType);
						license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																	// code(category
						license.setTaxCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

						license.setTaxSubCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());

						license.setRateStartDate(new Date().getTime());
						license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
						license.setApplicableAt(chargeAplAt);

						license.setArea(dto.getTrdUnit());

						if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
							List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
									.collect(Collectors.toList());
							if (level1 != null && !level1.isEmpty()) {
								license.setItemCategory1(level1.get(0).getDescLangFirst());
								dto.setItemCategory1(level1.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
							List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
									.collect(Collectors.toList());
							if (level2 != null && !level2.isEmpty()) {
								license.setItemCategory2(level2.get(0).getDescLangFirst());
								dto.setItemCategory2(level2.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
							List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
									.collect(Collectors.toList());
							if (level3 != null && !level3.isEmpty()) {
								license.setItemCategory3(level3.get(0).getDescLangFirst());
								dto.setItemCategory3(level3.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
							List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
									.collect(Collectors.toList());
							if (level4 != null && !level4.isEmpty()) {
								license.setItemCategory4(level4.get(0).getDescLangFirst());
								dto.setItemCategory4(level4.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
							List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
									.collect(Collectors.toList());
							if (level5 != null && !level5.isEmpty()) {
								license.setItemCategory5(level5.get(0).getDescLangFirst());
								dto.setItemCategory5(level5.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						//#146917
						double renPendPer = Utility.getDaysBetDates(masDto.getTrdLictoDate(), new Date());			
						Long period = (long) Math.ceil((renPendPer / MainetConstants.DAS_IN_YEAR));
						if (StringUtils.isNotBlank(masDto.getSource())
								&& masDto.getSource().equals(MainetConstants.FlagR)) {
							license.setSlab3(period);
							license.setSlab2(period);
						}
					
						if (masDto.getRenewalPendingDays() != null)
							license.setSlab2(masDto.getRenewalPendingDays());// pending cycle
						if (masDto.getRenewCycle() != null)
							license.setSlab3(masDto.getRenewCycle());// select cycle
						masterList.add(license);
					}
					// D#132374 for setting parentTaxvalue for fetching Penalty charge
					if (CollectionUtils.isNotEmpty(masterList)) {
						LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
						WSResponseDTO responseDTO = new WSResponseDTO();
						WSRequestDTO wsRequestDTO = new WSRequestDTO();
						List<MLNewTradeLicense> master = new ArrayList<>();

						wsRequestDTO.setDataModel(masterList);

						try {
							LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
							responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
							if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
								master1 = setTradeLicenceChargesDTO(responseDTO);
							} else {
								throw new FrameworkException(responseDTO.getErrorMessage());
							}
						} catch (Exception ex) {
							throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
						}
						LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");
					}

				}

			}

		}
		for (TbTaxMasEntity taxdto : taxesMaster) {

			if (taxdto.getParentCode() != null) {

				for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
					MLNewTradeLicense license = new MLNewTradeLicense();
					// Organisation org = UserSession.getCurrent().getOrganisation();
					license.setOrgId(masDto.getOrgid());
					license.setServiceCode("RTL");
					Department dept = tbDepartmentService.findDepartmentByCode("ML");
					TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(organisation.getOrgid(), dept.getDpDeptid(),
							taxdto.getTaxCode());
					String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
							MainetConstants.FlagE, masDto.getOrgid());
					license.setTaxType(taxType);
					license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																// code(category
					// and
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxdto.getParentCode(),
							masDto.getOrgid());

					license.setParentTaxCode(ParentTax.getTaxCode()); // subcategory)
					license.setTaxCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

					license.setTaxSubCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());
					license.setRateStartDate(new Date().getTime());
					license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
					license.setApplicableAt(chargeAplAt);

					if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
						List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
								.collect(Collectors.toList());
						if (level1 != null && !level1.isEmpty()) {
							license.setItemCategory1(level1.get(0).getDescLangFirst());
							dto.setItemCategory1(level1.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
						List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
								.collect(Collectors.toList());
						if (level2 != null && !level2.isEmpty()) {
							license.setItemCategory2(level2.get(0).getDescLangFirst());
							dto.setItemCategory2(level2.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
						List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
								.collect(Collectors.toList());
						if (level3 != null && !level3.isEmpty()) {
							license.setItemCategory3(level3.get(0).getDescLangFirst());
							dto.setItemCategory3(level3.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
						List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
								.collect(Collectors.toList());
						if (level4 != null && !level4.isEmpty()) {
							license.setItemCategory4(level4.get(0).getDescLangFirst());
							dto.setItemCategory4(level4.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
						List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
								.collect(Collectors.toList());
						if (level5 != null && !level5.isEmpty()) {
							license.setItemCategory5(level5.get(0).getDescLangFirst());
							dto.setItemCategory5(level5.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					// D#132374 for setting parentTaxvalue for fetching Penalty charge
					if (CollectionUtils.isNotEmpty(master1)) {
						master1.parallelStream().forEach(mast -> {
							if (mast.getItemCategory2().equals(license.getItemCategory2())) {
								license.setParentTaxValue(mast.getSlabRate1());
							}
						});
					}
					// User Story #133712
					double renPendPer = Utility.getDaysBetDates(masDto.getTrdLictoDate(), new Date());
					//#146917
					Long period = (long) Math.ceil((renPendPer / MainetConstants.DAS_IN_YEAR));
					//User Story #137413
					if (StringUtils.isNotBlank(masDto.getSource())
							&& masDto.getSource().equals(MainetConstants.FlagR)) {
						license.setSlab3(period);
						license.setSlab2(period);
					}
					if (masDto.getRenewalPendingDays() != null && masDto.getRenewCycle() != null
							&& masDto.getRenewalPendingDays().equals(masDto.getRenewCycle())) {
						double pendDays = renPendPer / MainetConstants.DAS_IN_YEAR;
						if (masDto.getRenewalPendingDays() - pendDays != 0) {
							double noOfdays = pendDays - (masDto.getRenewalPendingDays() - 1);
							license.setNoOfDays(Math.round(noOfdays * MainetConstants.DAS_IN_YEAR));
						}
					}else {
						double pendDays = 0;
						if (renPendPer > 0 )
							pendDays = renPendPer %365;
						if (pendDays > 0)
							license.setNoOfDays(pendDays);	
					}
					// User Story #133712
					if (masDto.getRenewalPendingDays() != null)
						license.setSlab2(masDto.getRenewalPendingDays());// pending cycle
					if (masDto.getRenewCycle() != null)
						license.setSlab3(masDto.getRenewCycle());// select cycle
					license.setArea(dto.getTrdUnit());

					masterList.add(license);

				}
			}
		}

	}

	private TradeMasterDetailDTO setApplicationChargeToDtoList(List<MLNewTradeLicense> master,
			TradeMasterDetailDTO masDto, List<TbTaxMasEntity> taxesMaster) {

		Organisation org = iOrganisationService.getOrganisationById(masDto.getOrgid());
		// D#129853 for round off the charge value
		Boolean flag = Utility.isRoundedOffApplicable(org);
		// 128195 code added to get penalty charges at application time
		double totalAmount = 0.0d;
		for (MLNewTradeLicense model : master) {
			double currVal = 0.0d;
			if (model.getTaxType()
					.equals(CommonMasterUtility.getValueFromPrefixLookUp("S", "FSD", org).getDescLangFirst())) {
				if (flag) {
					currVal = Math.round(model.getSlabRate1());
				} else {
					currVal = model.getSlabRate1();
				}

			}
			if (model.getTaxType()
					.equals(CommonMasterUtility.getValueFromPrefixLookUp("F", "FSD", org).getDescLangFirst())) {
				if (flag) {
					currVal = Math.round(model.getFlatRate());
				} else {
					currVal = model.getFlatRate();
				}

			}
			// Defect #128195
			for (TbTaxMasEntity taxdto : taxesMaster) {
				if (model.getTaxCode().equals(taxdto.getTaxCode())) {
					totalAmount = currVal + totalAmount;
					if (masDto.getFeeIds().get(taxdto.getTaxId()) == null) {
						masDto.getFeeIds().put(taxdto.getTaxId(), currVal);
						masDto.getChargesInfo().put(taxdto.getTaxDesc(), masDto.getFeeIds().get(taxdto.getTaxId()));
					} else {
						masDto.getFeeIds().put(taxdto.getTaxId(), currVal + masDto.getFeeIds().get(taxdto.getTaxId()));
						masDto.getChargesInfo().put(taxdto.getTaxDesc(), masDto.getFeeIds().get(taxdto.getTaxId()));
					}
				}

			}
			masDto.setApplicationCharge(Double.valueOf(totalAmount).toString());

			masDto.setTotalApplicationFee(BigDecimal.valueOf(Double.valueOf(totalAmount)));
		}
		return masDto;
	}

	public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId,
			TradeMasterDetailDTO tradeMasterDto, long renewalPeriod) {
		// User Story #133712
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", org);
		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();
		// #142368
		if (licToDate != null) {
			currentDate = licToDate;
		}

		List<LicenseValidityMasterDto> licValMasterDtoList = new ArrayList<>();
		List<LicenseValidityMasterDto> licValidityMster = new ArrayList<LicenseValidityMasterDto>();
		// For Setting in case of SKDCL ENV.Defect #102173
		// start
		// User Story #133712
		double renPendCycle = 0d;
		boolean isSkdcl = false;
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			isSkdcl = true;
			Long cat = null;
			if (tradeMasterDto != null && tradeMasterDto.getTradeLicenseItemDetailDTO() != null && tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1() != null)
				cat = tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1();
			else
				cat = MainetConstants.ZERO_LONG;

			double renPendDays = Utility.getDaysBetDates(tradeMasterDto.getTrdLictoDate(), new Date());
			renPendCycle = Long.valueOf(Math.round(renPendDays / MainetConstants.DAS_IN_YEAR));
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId, cat,
					MainetConstants.ZERO_LONG);

		} else {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					MainetConstants.ZERO_LONG, MainetConstants.ZERO_LONG);
		}
		if (CollectionUtils.isNotEmpty(licValidityMster)) {

			licValMasterDtoList = licValidityMster.stream()
					.filter(k -> (k.getLicType() == tradeMasterDto.getTrdLictype().longValue()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(licValMasterDtoList) || licValMasterDtoList.size() <= 0) {

				return null;
			}

			LicenseValidityMasterDto licValMasterDto = licValMasterDtoList.get(0);
			licValMasterDto.setLicTenure(String.valueOf(renewalPeriod));

			Organisation organisationById = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getOrganisationById(orgId);
			LookUp dependsOnLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					organisationById);

			Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
				licenseMaxTenureDays = tenure - 1;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
				licenseMaxTenureDays = 1l;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
				int currentYear = Integer.valueOf(Year.now().toString());
				Month monthObject = Month.from(LocalDate.now());
				int month = monthObject.getValue();
				licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
				if (tenure > 1) {
					for (int i = 2; i <= tenure; i++) {
						licenseMaxTenureDays = licenseMaxTenureDays
								+ Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
						if (month == 12) {
							month = 0;
							currentYear++;
						}
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
				if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
					int month = 0;
					int currentYear = Integer.valueOf(Year.now().toString());
					TbFinancialyear financialYear;
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int monthValue = currLocalDate.getMonthValue();
					int currentMonthValue = currLocalDate.getMonthValue();

					if (monthValue >= 3 && monthValue <= 12) {

						for (int i = monthValue; i <= 15; i++) {

							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;

								monthValue++;

							} else {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}

							}
						}

					} else {
						for (int i = monthValue; i <= 3; i++) {
							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;
								monthValue++;
								// Long currMonthDays = Long.valueOf(YearMonth.of(currentYear,
								// monthValue).lengthOfMonth());
							} else {
								licenseMaxTenureDays = licenseMaxTenureDays
										+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
								monthValue++;
							}

						}
					}
					if (tenure > 1) {
						for (int i = 2; i <= tenure; i++) {
							monthValue = 4;
							currentYear++;
							month = 0;
							for (int j = monthValue; j <= 15; j++) {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}
							}
						}
					}
					licenseMaxTenureDays = licenseMaxTenureDays - 1; // #142368-to get finacial yr wise toDate
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
					int currentYear = Integer.valueOf(Year.now().toString());
					LocalDate currLocalDate = LocalDate.now();
					LocalDate with = currLocalDate.with(lastDayOfYear());
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
							Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
					// User Story #133712
					if (isSkdcl) {
						// if (renPendCycle != tradeMasterDto.getRenewCycle()) {
						licenseMaxTenureDays = 0L;
						tenure = tenure + 1;
						try {
							currentYear = LocalDate.parse(new SimpleDateFormat(MainetConstants.DATE_FORMATS)
									.format(tradeMasterDto.getTrdLictoDate())).getYear();
						} catch (Exception e) {
							LOGGER.error("Exception at the time of date format to simple date format");

						}
						// }

					}

					if (tenure > 1) {

						for (int i = 2; i <= tenure; i++) {
							Year year = Year.of(++currentYear);
							licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
						}

					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth())
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));

				}
			}
		}

		return licenseMaxTenureDays;

	}

	// Defect #112686 -To print license no on Loi payment receipt
	@Override
	@WebMethod(exclude = true)
	public CommonChallanDTO getDepartmentWiseLoiData(Long applicationNo, Long orgId) {
		CommonChallanDTO challanDto = new CommonChallanDTO();
		try {
			TbMlTradeMast tradeMast = tradeLicenseApplicationRepository.getLicenseDetailsByAppIdAndOrgId(applicationNo,
					orgId);
			if (tradeMast != null) {
				// #142368 -to get license fromDate and Todate on LOI receipt
				TradeMasterDetailDTO dto = new TradeMasterDetailDTO();
				BeanUtils.copyProperties(tradeMast, dto);
				ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
						MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, dto.getOrgid());
				dto.setServiceId(sm.getSmServiceId());
				List<TradeLicenseItemDetailDTO> itemDtoList = new ArrayList<>();
				List<TbMlItemDetail> entity = tradeLicenseApplicationRepository.getItemDetailsByTrdId(tradeMast.getTrdId(), dto.getOrgid());
				if (CollectionUtils.isNotEmpty(entity)) {
					for (TbMlItemDetail itemDet : entity) {
						TradeLicenseItemDetailDTO dtos = new TradeLicenseItemDetailDTO();
						BeanUtils.copyProperties(itemDet, dtos);
						itemDtoList.add(dtos);
					}
				}
				if (CollectionUtils.isNotEmpty(itemDtoList))
					dto.setTradeLicenseItemDetailDTO(itemDtoList);
				challanDto = getRenewalLicenseDates(dto);
				challanDto.setFromedate(challanDto.getFromedate());
				challanDto.setToDate(challanDto.getToDate());
				challanDto.setLicNo(tradeMast.getTrdLicno());
				challanDto.setOrgId(orgId);
			}
		} catch (Exception e) {
			throw new FrameworkException(e.getMessage());
		}
		return challanDto;
	}

	@Override
	@WebMethod(exclude = true)
	public List<RenewalHistroyDetails> getRenewalHistoryDetails(String licNo, Long orgId) {
		List<RenewalHistroyDetails> renHist = new ArrayList<RenewalHistroyDetails>();
		Organisation organisation = iOrganisationService.getOrganisationById(orgId);
		try {
			List<Long> list = histRepository.getTradeDetailsHistoryByLicNoAndAppId(licNo);
			for (Long appId : list) {
				long serviceIdByApplicationId = 0l;
				RenewalHistroyDetails renHisDetails = new RenewalHistroyDetails();
				try {
					serviceIdByApplicationId = cfcApplicationService.getServiceIdByApplicationId(appId, orgId);
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error("No data found for appId::" + appId);
				}
				if (serviceIdByApplicationId != 0L) {
					String servShortCode = ApplicationContextProvider.getApplicationContext()
							.getBean(ServiceMasterService.class).fetchServiceShortCode(serviceIdByApplicationId, orgId);
					if (servShortCode.equals(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE)
							|| servShortCode.equals(MainetConstants.TradeLicense.SERVICE_CODE)) {
						List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(appId, serviceIdByApplicationId,
								orgId);
						LookUp chargeApplicableAt = null;
						if (servShortCode.equals(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE)) {
							chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
									MainetConstants.TradeLicense.APL, PrefixConstants.NewWaterServiceConstants.CAA,
									organisation);
						} else {
							chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
									PrefixConstants.NewWaterServiceConstants.SCRUTINY,
									PrefixConstants.NewWaterServiceConstants.CAA, organisation);
						}

						ServiceMaster sm = ApplicationContextProvider.getApplicationContext()
								.getBean(ServiceMasterService.class).getServiceMasterByShortCode(servShortCode, orgId);

						final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
								.getBean(TbTaxMasService.class)
								.fetchAllApplicableServiceCharge(sm.getSmServiceId(), orgId,
										chargeApplicableAt.getLookUpId())
								.stream().sorted(Comparator.comparingLong(TbTaxMasEntity::getTaxDisplaySeq))
								.collect(Collectors.toList());

						Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();
						if (CollectionUtils.isNotEmpty(tbLoiMas)) {
							List<TbLoiDetEntity> loiDetails = tbLoiDetService
									.findLoiDetailsByLoiIdAndOrgId(tbLoiMas.get(0).getLoiId(), orgId);

							double loiAmount = 0d;
							for (TbTaxMasEntity taxesMasterdto : taxesMaster) {

								for (TbLoiDetEntity loiDetailDto : loiDetails) {

									if (loiDetailDto.getLoiChrgid().longValue() == taxesMasterdto.getTaxId()
											.longValue()) {

										/*
										 * String taxCategoryDesc = CommonMasterUtility
										 * .getHierarchicalLookUp(taxesMasterdto.getTaxCategory2(),
										 * wfmass.getOrganisation()) .getDescLangFirst();
										 */
										if (loiDetailDto.getLoiAmount() == null) {
											loiDetailDto.setLoiAmount(new BigDecimal(0));
										}
										loiAmount = loiAmount
												+ Double.valueOf(Math.round(loiDetailDto.getLoiAmount().doubleValue()));
										chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(),
												Double.valueOf(Math.round(loiDetailDto.getLoiAmount().doubleValue())));

									}

								}
							}

							renHisDetails.setTotalAmt(Double.valueOf(Math.round(loiAmount)));
							List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
									tbLoiMas.get(0).getLoiNo());
							renHisDetails.setChargeDesc(chargeDescAndAmount);
							renHisDetails.setServiceShrtCode(servShortCode);
							renHisDetails.setRcptNo(dto.get(0).getRmRcptno());
						} else {
							TbServiceReceiptMasEntity entity = receiptEntryService.getReceiptDetailsByAppId(appId,
									orgId);
							for (TbTaxMasEntity taxesMasterdto : taxesMaster) {
								for (TbSrcptFeesDetEntity det : entity.getReceiptFeeDetail()) {
									if (taxesMasterdto.getTaxId().equals(det.getTaxId()))
										chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(),
												Double.valueOf(Math.round(det.getRfFeeamount().doubleValue())));
								}
							}
							if (entity != null)
								renHisDetails.setRcptNo(entity.getRmRcptno());
							renHisDetails.setChargeDesc(chargeDescAndAmount);
							renHisDetails.setTotalAmt(entity.getRmAmount().doubleValue());
							renHisDetails.setServiceShrtCode(servShortCode);
						}
						List<TbMlRenewalMastHist> renMast = renHistortRepo.getLicenseDetailsByApplicationId(appId);
						if (renMast != null && !renMast.isEmpty()) {
							renHisDetails
									.setRenewalFromDateDesc(Utility.dateToString(renMast.get(0).getTreLicfromDate()));
							renHisDetails.setRenewalTodDateDesc(Utility.dateToString(renMast.get(0).getTreLictoDate()));
							renHisDetails.setUpdatedDate(renMast.get(0).getUpdatedDate());
						}
					}
					renHist.add(renHisDetails);
				}
			}
		} catch (Exception e) {
			throw new FrameworkException(e.getMessage());
		}
		return renHist;
	}

	// 126164
	@Override
	public void sendSmsEmail(TradeMasterDetailDTO masDto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = iOrganisationService.getOrganisationById(masDto.getOrgid());
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, org.getOrgid());

		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "RenewalLicenseForm.html";
		org.setOrgid(org.getOrgid());
		int langId = masDto.getLangId().intValue();
		smsDto.setUserId(masDto.getUserId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
	}

	// #129518- to check if license present under objection process
	@Override
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To Check objection present against given license", notes = "To Check objection present against given license")
	@Path("/checkLicenseNoExist/{trdLicno}/{orgId}")
	public Boolean checkLicenseNoExist(@PathParam(value = "trdLicno") String refNo,
			@PathParam(value = "orgId") Long orgId) {
		return objectionMastRepository.checkLicenseNoExist(refNo, orgId);
	}

	// #129518 to get license from date to date to print on receipt
	private void getRenewalFromDateToDatePeriod(TradeMasterDetailDTO dto) {
		Organisation org = iOrganisationService.getOrganisationById(dto.getOrgid());
		LookUp lookUpObject = CommonMasterUtility
				.getNonHierarchicalLookUpObject(Long.valueOf(dto.getRenewalMasterDetailDTO().getRenewalPeriod()), org);
		// #142368
		Long renewalPeriod = null;
		if (dto.getRenewCycle() != null) {
			renewalPeriod = dto.getRenewCycle().longValue();
		} else {
			renewalPeriod = Long.valueOf(lookUpObject.getLookUpCode());
		}
		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(dto.getOrgid(), MainetConstants.FlagA, "ML");
		Long calculateLicMaxTenureDays = calculateLicMaxTenureDays(department.getDpDeptid(), dto.getServiceId(),
				dto.getTrdLictoDate(), dto.getOrgid(), dto, renewalPeriod);

		if (calculateLicMaxTenureDays == null || calculateLicMaxTenureDays == 0l) {

			throw new FrameworkException("No range found in License Validity Master");
		}
		Date renwalToDate = Utility.getAddedDateBy2(dto.getTrdLictoDate(), calculateLicMaxTenureDays.intValue());

		dto.getRenewalMasterDetailDTO().setTreLictoDate(renwalToDate);
		if (dto.getTrdLictoDate() != null)
			dto.getRenewalMasterDetailDTO().setTreLicfromDate(Utility.getAddedDateBy2(dto.getTrdLictoDate(), 1));

	}

	@Override
	@Transactional
	public Boolean updateStatusAfterDishonurEntry(Long appId, Long orgId) {
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", PrefixConstants.LookUp.LIS, org);
		TbMlTradeMast entity = tradeLicenseApplicationRepository.getLicenseDetailsByAppIdAndOrgId(appId, orgId);
		entity.setTrdStatus(lookUp.getLookUpId());
		tradeLicenseApplicationRepository.save(entity);
		return true;
	}

	@Override
	public CommonChallanDTO getRenewalLicenseDates(TradeMasterDetailDTO dto) {
		CommonChallanDTO challanDTO = new CommonChallanDTO();
		try {
			Organisation org = iOrganisationService.getOrganisationById(dto.getOrgid());
			Long renewalPeriod = null;
			if (dto.getRenewCycle() != null) {
				renewalPeriod = dto.getRenewCycle().longValue();
			} else {
				LookUp lookUpObject = CommonMasterUtility.getNonHierarchicalLookUpObject(
						Long.valueOf(dto.getRenewalMasterDetailDTO().getRenewalPeriod()), org);
				renewalPeriod = Long.valueOf(lookUpObject.getLookUpCode());
			}
			TbDepartment department = ApplicationContextProvider.getApplicationContext()
					.getBean(DepartmentService.class).findDeptByCode(dto.getOrgid(), MainetConstants.FlagA, "ML");
			Long calculateLicMaxTenureDays = calculateLicMaxTenureDays(department.getDpDeptid(), dto.getServiceId(),
					dto.getTrdLictoDate(), dto.getOrgid(), dto, renewalPeriod);

			if (calculateLicMaxTenureDays == null || calculateLicMaxTenureDays == 0l) {

				throw new FrameworkException("No range found in License Validity Master");
			}
			Date renwalToDate = Utility.getAddedDateBy2(dto.getTrdLictoDate(), calculateLicMaxTenureDays.intValue());
			challanDTO.setFromedate(Utility.getAddedDateBy2(dto.getTrdLictoDate(), 1));
			challanDTO.setToDate(renwalToDate);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return challanDTO;
	}

	@Override
	public String geneateDemandForLicense(List<TradeMasterDetailDTO> dtoList) {
		final ExecutorService executorService = Executors.newFixedThreadPool(
				Integer.valueOf(ApplicationSession.getInstance().getMessage("bill.thread.pool.size")));
		List<TradeMasterDetailDTO> tradeDtoList = new ArrayList<TradeMasterDetailDTO>();
		List<TradeMasterDetailDTO> tradeDtoList12 = new ArrayList<TradeMasterDetailDTO>();

		for (TradeMasterDetailDTO dtos : dtoList) {
			executorService.execute(new Runnable() {
				// D#140622
				public void run() {
					try {
						dtos.setSource(MainetConstants.FlagR);
						TradeMasterDetailDTO dto = getTradeLicenceChargesAtApplicationFromBrmsRule(dtos);
						tradeDtoList.add(dto);
						saveDemandRegisterData(dto);
					} catch (Exception e) {
						tradeDtoList12.add(dtos);

						saveDemandRegisterErrorData(dtos, e.getMessage());

					}
					LOGGER.info(String.format("starting expensive after task thread %s after",
							Thread.currentThread().getName()));
				}

			});

		}
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		LOGGER.info("\nFinished all threads" + tradeDtoList.size() + "error   " + tradeDtoList12.size());
		String succMsg = "License demand register  data updatation successfully completed with scusess" + "    "
				+ tradeDtoList.size() + "    and error   " + tradeDtoList12.size();

		return succMsg;
	}

	private void saveDemandRegisterErrorData(TradeMasterDetailDTO dtos, String errMsg) {
		LicenseDmandRegErrorEntity errEnt = new LicenseDmandRegErrorEntity();
		LicenseDmandRegErrorEntity errEntity = licenseDmandRegErrorRepo
				.getDemandLicenseRegErrByLiscenseNo(dtos.getTrdLicno());
		if (errEntity != null) {
			BeanUtils.copyProperties(errEntity, errEnt);
			errEnt.setUpdatedDate(new Date());
			errEnt.setUpdatedBy(88L);
		}
		errEnt.setTrdLicNo(dtos.getTrdLicno());
		errEnt.setTrderrMsg(errMsg);
		errEnt.setCreatedDate(new Date());
		errEnt.setOrgId(dtos.getOrgid());
		licenseDmandRegErrorRepo.save(errEnt);

	}

	private void saveDemandRegisterData(TradeMasterDetailDTO dto) {
		if (dto != null) {
			LicenseDemandRegisterEntity entity = new LicenseDemandRegisterEntity();
			LicenseDemandRegisterEntity ent = licenseDemandRegRepo.getDemandLicenseRegByLiscenseNo(dto.getTrdLicno());
			if (ent != null) {
				BeanUtils.copyProperties(ent, entity);
				entity.setUpdatedDate(new Date());
			}
			entity.setCreatedBy(0L);
			entity.setCreatedDate(new Date());

			if (dto.getApplicationCharge() != null)
				entity.setTotPendFee(Double.valueOf(dto.getApplicationCharge()).longValue());
			double renPendPer = Utility.getDaysBetDates(dto.getTrdLictoDate(), new Date());
			entity.setLicexpRenYr((long) renPendPer / 365);
			entity.setLicPendRenYr((long) renPendPer / 365);
			if (dto.getChargesInfo() != null && !dto.getChargesInfo().isEmpty()) {
				if (dto.getChargesInfo().get("Late Fee") != null) {
					entity.setTotLateFee(dto.getChargesInfo().get("Late Fee").longValue());
					if (entity.getLicexpRenYr() != null && entity.getLicexpRenYr() != 0)
						entity.setCurrYrLateFee(entity.getTotLateFee() / entity.getLicexpRenYr());
				}
				if (dto.getChargesInfo().get("License Fee") != null) {
					entity.setTotRenFee(dto.getChargesInfo().get("License Fee").longValue());
					if (entity.getLicexpRenYr() != null && entity.getLicexpRenYr() != 0)
						entity.setCurrYrLicFee(entity.getTotRenFee() / entity.getLicexpRenYr());
				}
			}
			entity.setTrdOldlicno(dto.getTrdLicno());
			entity.setCreatedDate(new Date());
			entity.setOrgId(dto.getOrgid());
			entity.setTrdBusadd(dto.getTrdBusadd());
			entity.setTrdBusnm(dto.getTrdBusnm());
			entity.setTrdLictoDate(dto.getTrdLictoDate());
			if (CollectionUtils.isNotEmpty(dto.getTradeLicenseOwnerdetailDTO())) {
				entity.setTrdOwnerName(dto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			}

			licenseDemandRegRepo.save(entity);

		}

	}
	@Override
	@Transactional
	@Consumes("application/json")
	@POST
	@ApiOperation(value ="getLicenseMaxTenureDays", notes ="getLicenseMaxTenureDays",response = Object.class)
	@Path("/getLicenseMaxTenureDays")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Map<String,String> getLicenseMaxTenureDays(@RequestBody LicenseValidityDto  dto) {
		// User Story #133712
		Organisation org = iOrganisationService.getOrganisationById(dto.getOrgId());
		Map<String,String> map=new HashMap<String, String>();
		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();
		List<LicenseValidityMasterDto> licValMasterDtoList = new ArrayList<>();
		List<LicenseValidityMasterDto> licValidityMster = new ArrayList<LicenseValidityMasterDto>();
		try {
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			Long cat = null;
			if (dto.getCatId1()!=null)
				cat = dto.getCatId1();
			else
				cat = MainetConstants.ZERO_LONG;

			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(dto.getOrgId(), dto.getDeptId(), dto.getServiceId(), cat,
					dto.getLicType());

		} else {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(dto.getOrgId(), dto.getDeptId(), dto.getServiceId(),
					MainetConstants.ZERO_LONG, dto.getLicType());
		}
		}
		catch (Exception e) {
			LOGGER.info("Throwing Exception at the time of fetching loicense validity data");
		}
		if (CollectionUtils.isNotEmpty(licValidityMster)) {

			licValMasterDtoList = licValidityMster.stream()
					.filter(k -> (k.getLicType() == dto.getLicType().longValue()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(licValMasterDtoList) || licValMasterDtoList.size() <= 0) {

				return null;
			}

			LicenseValidityMasterDto licValMasterDto = licValMasterDtoList.get(0);

			Organisation organisationById = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getOrganisationById(dto.getOrgId());
			LookUp dependsOnLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					organisationById);

			Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
				licenseMaxTenureDays = tenure - 1;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
				licenseMaxTenureDays = 1l;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
				int currentYear = Integer.valueOf(Year.now().toString());
				Month monthObject = Month.from(LocalDate.now());
				int month = monthObject.getValue();
				licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
				if (tenure > 1) {
					for (int i = 2; i <= tenure; i++) {
						licenseMaxTenureDays = licenseMaxTenureDays
								+ Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
						if (month == 12) {
							month = 0;
							currentYear++;
						}
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
				if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
					int month = 0;
					int currentYear = Integer.valueOf(Year.now().toString());
					TbFinancialyear financialYear;
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int monthValue = currLocalDate.getMonthValue();
					int currentMonthValue = currLocalDate.getMonthValue();

					if (monthValue > 3 && monthValue <= 12) {

						for (int i = monthValue; i <= 15; i++) {

							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;

								monthValue++;

							} else {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}

							}
						}

					} else {
						for (int i = monthValue; i <= 3; i++) {
							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;
								monthValue++;
							} else {
								licenseMaxTenureDays = licenseMaxTenureDays
										+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
								monthValue++;
							}

						}
					}
					if (tenure > 1) {
						for (int i = 2; i <= tenure; i++) {
							monthValue = 4;
							currentYear++;
							month = 0;
							for (int j = monthValue; j <= 15; j++) {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}
							}
						}
					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
					int currentYear = Integer.valueOf(Year.now().toString());
					LocalDate currLocalDate = LocalDate.now();
					LocalDate with = currLocalDate.with(lastDayOfYear());
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
							Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
					

					if (tenure > 1) {

						for (int i = 2; i <= tenure; i++) {
							Year year = Year.of(++currentYear);
							licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
						}

					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth())
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1));

				}
			}
		}
		else {
			map.put("Error Msg", "No validity configure in license validity master ,Please contact adminstrotor");
			return map;
		}
		Date date = Utility.getAddedDateBy2(new Date(), licenseMaxTenureDays.intValue());
		String strDate =Utility.dateToString(date);
		
		map.put("Max_date", strDate);
		
		return map;

	}

}
