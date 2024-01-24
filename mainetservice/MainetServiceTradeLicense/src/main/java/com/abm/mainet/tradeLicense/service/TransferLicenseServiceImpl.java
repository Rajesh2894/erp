package com.abm.mainet.tradeLicense.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ReceiptBookHistoryEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
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
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Service(value = "TransferLicenseService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.ITransferLicenseService")
@Api(value = "/TransferLicenseApplicationService")
@Path("/TransferLicenseApplicationService")
public class TransferLicenseServiceImpl implements ITransferLicenseService {

	private static final Logger LOGGER = Logger.getLogger(TransferLicenseServiceImpl.class);

	@Autowired
	private CommonService commonService;
	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;

	@Autowired
	IFileUploadService fileUploadService;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	AuditService auditService;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
		
	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "Save Transfer License Service", notes = "Save Transfer License Service", response = TradeMasterDetailDTO.class)
	@Path("/saveTransferService")
	@Transactional
	public TradeMasterDetailDTO saveTransferLicenseService(TradeMasterDetailDTO tradeMasterDto) {
		Long appicationId = null;
		// for changing status of license fro Issued to Tansfer In progress when
		// application punched from Mobile
		// 123021
		Organisation organisation = iOrganisationService.getOrganisationById(tradeMasterDto.getOrgid());
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS", organisation);
		tradeMasterDto.setTrdStatus(lookUp.getLookUpId());

		// end
		TbMlTradeMast masEntity = mapDtoToEntity(tradeMasterDto);

		try {

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("TLA", tradeMasterDto.getOrgid());

			RequestDTO requestDto = setApplicantRequestDto(tradeMasterDto, sm);

			requestDto.setFree(true);
			/* if (masEntity.getApmApplicationId() == null) */ {

				// create application Common table entry where application no is generated
				appicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
						.createApplication(requestDto);

				LOGGER.info("application number for transfer of  trade licence : " + appicationId);
				masEntity.setApmApplicationId(appicationId);
				tradeMasterDto.setApmApplicationId(appicationId);
				requestDto.setApplicationId(appicationId);

				// 125706 to save application id in owner and item detail table
				masEntity.getItemDetails().forEach(itemEntity -> {
					itemEntity.setApmApplicationId(tradeMasterDto.getApmApplicationId());
				});
				masEntity.getOwnerDetails().forEach(ownerEntity -> {
					ownerEntity.setApmApplicationId(tradeMasterDto.getApmApplicationId());
				});
			}

			// application saved in application specific table
			masEntity = tradeLicenseApplicationRepository.save(masEntity);

			saveHistoryData(masEntity);

			boolean checklist = false;
			if ((tradeMasterDto.getDocumentList() != null) && !tradeMasterDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(appicationId);
				checklist = fileUploadService.doFileUpload(tradeMasterDto.getDocumentList(), requestDto);
				checklist = true;
			}

			int i = 0;
			if ((tradeMasterDto.getAttachments() != null) && !tradeMasterDto.getAttachments().isEmpty()) {
				List<DocumentDetailsVO> getImgList = null;
				for (final TbMlOwnerDetail d : masEntity.getOwnerDetails().stream()
						.filter(k -> k.getTroPr().equalsIgnoreCase("Y")).collect(Collectors.toList())) {
					getImgList = new ArrayList<>();

					requestDto.setReferenceId(d.getTroId().toString());
					requestDto.setApplicationId(d.getTroId());
					List<DocumentDetailsVO> getList = tradeMasterDto.getAttachments();
					for (int j = i; j < getList.size(); j++) {
						DocumentDetailsVO img = getList.get(i);
						getImgList.add(img);
						break;

					}

					i++;
					fileUploadService.doFileUpload(getImgList, requestDto);

				}

			}

			// initiate workflow

			if (tradeMasterDto.isFree()) {

				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(tradeMasterDto.getApmApplicationId());
				applicationData.setOrgId(tradeMasterDto.getOrgid());
				applicationData.setIsCheckListApplicable(checklist);
				applicationData.setIsFreeService(true);

				ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();

				applicantDetailDTO.setUserId(tradeMasterDto.getUserId());
				applicantDetailDTO.setServiceId(sm.getSmServiceId());
				applicantDetailDTO.setDepartmentId(sm.getTbDepartment().getDpDeptid());

				tradeMasterDto.getApplicantDetailDto().setUserId(tradeMasterDto.getUserId());
				tradeMasterDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				tradeMasterDto.getApplicantDetailDto().setDepartmentId(sm.getTbDepartment().getDpDeptid());

				if (tradeMasterDto.getTrdWard1() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid1(tradeMasterDto.getTrdWard1());
				}
				if (tradeMasterDto.getTrdWard2() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid2(tradeMasterDto.getTrdWard2());
				}
				if (tradeMasterDto.getTrdWard3() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid3(tradeMasterDto.getTrdWard3());
				}
				if (tradeMasterDto.getTrdWard4() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid4(tradeMasterDto.getTrdWard4());
				}
				if (tradeMasterDto.getTrdWard5() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid5(tradeMasterDto.getTrdWard5());
				}
    //Defect #131715
				LookUp lookup1 = null;
				try {
					lookup1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WWC", "LCC", 1, organisation);
				} catch (Exception e) {
					LOGGER.error("No prefix found for for workflow based on category ", e);
				}
				WorkflowMas mas = null;
				if (lookup1 != null && lookup1.getOtherField() != null && lookup1.getOtherField().equals("Y")
						&& (tradeMasterDto.isFree())) {
					tradeMasterDto.setOrgid(organisation.getOrgid());
					String processName = serviceMasterService.getProcessName(sm.getSmServiceId(),organisation.getOrgid());
			        if (processName != null) {
			            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			            boolean autoescalate = false;
			            mas = workflowTyepResolverService.resolveServiceWorkflowType(tradeMasterDto.getOrgid(),
								Long.valueOf(sm.getTbDepartment().getDpDeptid()), sm.getSmServiceId(), null, null,
								tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), tradeMasterDto.getTrdWard1(), tradeMasterDto.getTrdWard2(),
								tradeMasterDto.getTrdWard3(), tradeMasterDto.getTrdWard4(), tradeMasterDto.getTrdWard5());
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
				  else if (tradeMasterDto.isFree()) {
					commonService.initiateWorkflowfreeService(applicationData, applicantDetailDTO);
				}
			}
		} catch (Exception exception) {
			// LOGGER.error("Exception occur while saving Trade License Application ",
			// exception);
			throw new FrameworkException("Exception occur while saving transfer of Trade License Application ",
					exception);
		}
		sendSmsEmail(tradeMasterDto);
		return tradeMasterDto;
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
		// 125706 to set history status in history table
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
		// end history
	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();
		TradeLicenseOwnerDetailDTO ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().get(0);
		// Defect#122723
		List<TradeLicenseOwnerDetailDTO> ownerNameDto = tradeMasterDto.getTradeLicenseOwnerdetailDTO().stream()
				.filter(ownDto -> ownDto != null && ownDto.getTroPr().equals(MainetConstants.FlagY))
				.collect(Collectors.toList());
		String ownerName = "";
		if (!CollectionUtils.isEmpty(ownerNameDto)) {
			StringBuilder ownName = new StringBuilder();

			for (TradeLicenseOwnerDetailDTO ownDto : ownerNameDto) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (StringUtils.isNotBlank(ownName.toString())) {
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
//Defect #131715
			requestDto.setMobileNo(ownerNameDto.get(0).getTroMobileno());
		}

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(ownerDetails.getCreatedBy());
		requestDto.setOrgId(tradeMasterDto.getOrgid());
		requestDto.setLangId((long) tradeMasterDto.getLangId());
		requestDto.setPayStatus(MainetConstants.PAYMENT.FREE);
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setfName(ownerName);
		// Defect #110925
		requestDto.setEmail(ownerDetails.getTroEmailid());
		requestDto.setAreaName(ownerDetails.getTroAddress());
		requestDto.setWardNo(tradeMasterDto.getTrdWard1());
		requestDto.setZoneNo(tradeMasterDto.getTrdWard2());

		return requestDto;

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
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		TradeMasterDetailDTO entity = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("TLA", orgId);

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		double amount = entity.getTradeLicenseItemDetailDTO().stream().filter(c -> c.getTriRate() != null)
				.mapToDouble(c -> c.getTriRate().doubleValue()).sum();

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			chargeMap.put(tbTaxMas.getTaxId(), amount);
		}

		TradeMasterDetailDTO tradeLicenceCharges = getTradeLicenceChargesFromBrmsRule(entity);
		return tradeLicenceCharges.getFeeIds();
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
		// D#122130 for setting ParentTax value as License Fee
		Organisation orgs = UserSession.getCurrent().getOrganisation();
		Boolean envFlag = false;
		if (Utility.isEnvPrefixAvailable(orgs, MainetConstants.ENV_SKDCL)
				|| Utility.isEnvPrefixAvailable(orgs, MainetConstants.ENV_SUDA) || Utility.isEnvPrefixAvailable(orgs, MainetConstants.ENV_TSCL)) {
			envFlag = true;
		}


		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("TLA", masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());
		
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			//LocalDate applicationDate = LocalDate.now();l
			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				    		masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TRC")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				    		masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SRC")) {
				        iterator.remove();
				    }
				}
			}
		}
		List<MLNewTradeLicense> masterList = new ArrayList<>();

		if (masDto.getTrdId() != null) {

			final LookUp chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
					organisation);

			ServiceMaster smm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("TLA", masDto.getOrgid());

			final List<TbTaxMasEntity> taxesMasterr = ApplicationContextProvider.getApplicationContext()
					.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(smm.getSmServiceId(),
							organisation.getOrgid(), chargeApplicableAtScrutiny.getLookUpId());
			
			if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
				Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
				LookUp lookUp = CommonMasterUtility
						.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

				//LocalDate applicationDate = LocalDate.now();l
				if (lookUp.getLookUpCode().equals("STR")) {
					while (iterator.hasNext()) {
					    TbTaxMasEntity taxdto = iterator.next();
					    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
					    		masDto.getOrgid(), "TXN");
					    if (taxLookUp.getLookUpCode().equals("TRC")) {
					        iterator.remove();
					    }
					}
				} else if (lookUp.getLookUpCode().equals("TL")) {
					while (iterator.hasNext()) {
					    TbTaxMasEntity taxdto = iterator.next();
					    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
					    		masDto.getOrgid(), "TXN");
					    if (taxLookUp.getLookUpCode().equals("SRC")) {
					        iterator.remove();
					    }
					}
				}
			}

			List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
			List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
			List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
			List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
			List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
			List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
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

			for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
				MLNewTradeLicense license = new MLNewTradeLicense();
				Organisation org = UserSession.getCurrent().getOrganisation();
				license.setOrgId(dto.getOrgid());
				license.setServiceCode("TLA");
				Department dept = tbDepartmentService.findDepartmentByCode("ML");
				TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(org.getOrgid(), dept.getDpDeptid(),
						taxesMaster.get(0).getTaxCode());
				String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
						MainetConstants.FlagE, dto.getOrgid());
				license.setTaxType(taxType);
				license.setTaxCode(taxesMasterr.get(0).getTaxCode()); // for getting scrutiny level tax code(category
				// adding code for handling NPE when Parent Tax cod is not set in Tax master
				// table Defect#102468
				if ((taxesMasterr != null && !taxesMasterr.isEmpty()) && taxesMasterr.get(0).getParentCode() != null) {
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxesMasterr.get(0).getParentCode(),
							dto.getOrgid());

					license.setParentTaxCode(ParentTax.getTaxCode()); // subcategory)
				}
				license.setTaxCategory(CommonMasterUtility
						.getHierarchicalLookUp(taxesMasterr.get(0).getTaxCategory1(), organisation).getDescLangFirst());

				license.setTaxSubCategory(CommonMasterUtility
						.getHierarchicalLookUp(taxesMasterr.get(0).getTaxCategory2(), organisation).getDescLangFirst());

				CommonMasterUtility.getHierarchicalLookUp(taxesMasterr.get(0).getTaxCategory2(), organisation)
						.getDescLangFirst();
				license.setRateStartDate(todayDate.getTime());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				license.setApplicableAt(chargeApplicableAtScrutiny.getDescLangFirst());
				LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
						organisation);
				license.setLicenseType(licenseType.getDescLangFirst());
				license.setArea(dto.getTrdUnit());

				if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
					List<LookUp> level1 = lookupListLevel1.parallelStream()
							.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
							.collect(Collectors.toList());
					if (level1 != null && !level1.isEmpty()) {
						license.setItemCategory1(level1.get(0).getDescLangFirst());
						dto.setItemCategory1(level1.get(0).getDescLangFirst());
					}
				} else {
					dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
				}
				if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
					List<LookUp> level2 = lookupListLevel2.parallelStream()
							.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
							.collect(Collectors.toList());
					if (level2 != null && !level2.isEmpty()) {
						license.setItemCategory2(level2.get(0).getDescLangFirst());
						dto.setItemCategory2(level2.get(0).getDescLangFirst());
					}
				} else {
					dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
				}
				if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
					List<LookUp> level3 = lookupListLevel3.parallelStream()
							.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
							.collect(Collectors.toList());
					if (level3 != null && !level3.isEmpty()) {
						license.setItemCategory3(level3.get(0).getDescLangFirst());
						dto.setItemCategory3(level3.get(0).getDescLangFirst());
					}
				} else {
					dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
				}
				if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
					List<LookUp> level4 = lookupListLevel4.parallelStream()
							.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
							.collect(Collectors.toList());
					if (level4 != null && !level4.isEmpty()) {
						license.setItemCategory4(level4.get(0).getDescLangFirst());
						dto.setItemCategory4(level4.get(0).getDescLangFirst());
					}
				} else {
					dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
				}
				if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
					List<LookUp> level5 = lookupListLevel5.parallelStream()
							.filter(clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
							.collect(Collectors.toList());
					if (level5 != null && !level5.isEmpty()) {
						license.setItemCategory5(level5.get(0).getDescLangFirst());
						dto.setItemCategory5(level5.get(0).getDescLangFirst());
					}
				} else {
					dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
				}
				// D#122130 for setting ParentTax value as License Fee
				if (envFlag) {
					try {
 						license.setParentTaxValue(iTradeLicenseApplicationService.getParentTaxValue(masDto));
					} catch (Exception e) {
						LOGGER.error("Error ocurred while setting parent tax value in service TLA"+ e);
					}
				}
				masterList.add(license);
				// D#122130 for setting ParentTax value as License Fee
				if (envFlag) {
					break;
				}

			}

		} else {

			for (TbTaxMasEntity taxes : taxesMaster) {
				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.SERVICE_CODE);
				license.setTaxType(MainetConstants.TradeLicense.TAX_TYPE);
				license.setTaxCode(taxes.getTaxCode());
				license.setParentTaxCode("ML20");
				settingTaxCategories(license, taxes, organisation);
				license.setRateStartDate(todayDate.getTime());
				license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				// D#122130 for setting ParentTax value as License Fee
				if (envFlag) {
					license.setParentTaxValue(iTradeLicenseApplicationService.getParentTaxValue(masDto));
				}
				masterList.add(license);
				// D#122130 for setting ParentTax value as License Fee
				if (envFlag) {
					break;
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

		setChargeToItemsDtoList(master, masDto);

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			masDto.getFeeIds().put(tbTaxMas.getTaxId(), masDto.getTotalApplicationFee().doubleValue());
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

	private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense> master, TradeMasterDetailDTO masDto) {

		Organisation organisation = iOrganisationService.getOrganisationById(masDto.getOrgid());

		if (masDto.getApmApplicationId() != null)
			masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
				master.forEach(model -> {
					if (entity.getItemCategory1().equals(model.getItemCategory1())
							&& entity.getItemCategory2().equals(model.getItemCategory2())
							&& entity.getItemCategory3().equals(model.getItemCategory3())
							&& entity.getItemCategory4().equals(model.getItemCategory4())) {

						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {
							entity.setTriRate(BigDecimal.valueOf(model.getSlabRate1()));
						}
						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
							entity.setTriRate(BigDecimal.valueOf(model.getFlatRate()));
						}
					}

				});
			});

		masDto.setTotalApplicationFee(BigDecimal.valueOf(
				masDto.getTradeLicenseItemDetailDTO().stream().mapToDouble(c -> c.getTriRate().doubleValue()).sum()));

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
	public TradeMasterDetailDTO updateTransferLicenseService(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = mapDtoToEntity(tradeMasterDto);

		tradeLicenseApplicationRepository.save(masEntity);

		return tradeMasterDto;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getApplChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {
		// app
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("TLA", masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility
				.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid())
				.getLookUpId();

		for (TbTaxMasEntity taxes : taxesMaster) {

			if ((taxes.getTaxCategory2() == appChargetaxId)) {

				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode("TLA");
				TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(),
						sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
				String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
						MainetConstants.FlagE, masDto.getOrgid());

				license.setTaxType(taxType);
				license.setTaxCode(taxes.getTaxCode());
				settingTaxCategories(license, taxes, organisation);
				// license.setRateStartDate(todayDate.getTime());
				license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				
				//#142577-set parentTaxValue for TSCL project
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && taxes.getParentCode() != null) {
				try {
					masDto.setCheckApptimeCharge(MainetConstants.FlagY);
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxes.getParentCode(),
							masDto.getOrgid());
					license.setParentTaxCode(ParentTax.getTaxCode());
					license.setParentTaxValue(iTradeLicenseApplicationService.getParentTaxValue(masDto));
				 } catch (Exception e) {
					LOGGER.error("Error ocurred while setting parent tax value in service TLA at application time"+ e);
				 }
				}
				masterList.add(license);
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

		setApplicationChargeToDtoList(master, masDto);

		return masDto;
	}

	private TradeMasterDetailDTO setApplicationChargeToDtoList(List<MLNewTradeLicense> master,
			TradeMasterDetailDTO masDto) {

		master.forEach(model -> {

			if (model.getTaxSubCategory().equalsIgnoreCase("Application Charge")) {

				masDto.setApplicationCharge(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()).toString());
				masDto.setTotalApplicationFee(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
			}

		});

		return masDto;
	}

	// add method for defect#38964
	@WebMethod(exclude = true)
	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		TradeMasterDetailDTO entity = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (entity != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (entity.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(entity.getTrdWard1());
			}
			if (entity.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(entity.getTrdWard2());
			}
			if (entity.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(entity.getTrdWard3());
			}
			if (entity.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(entity.getTrdWard4());
			}
			if (entity.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(entity.getTrdWard5());
			}
		}

		return wardZoneDTO;
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
				challanDto.setLicNo(tradeMast.getTrdLicno());
				challanDto.setFromedate(tradeMast.getTrdLicfromDate());
				challanDto.setToDate(tradeMast.getTrdLictoDate());
				challanDto.setOrgId(orgId);
			}
		} catch (Exception e) {
			throw new FrameworkException(e.getMessage());
		}
		return challanDto;
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
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.TRANSFER_SERVICE_SHORT_CODE, org.getOrgid());
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "TransperLicense.html";
		org.setOrgid(org.getOrgid());
		int langId = masDto.getLangId().intValue();
		smsDto.setUserId(masDto.getUserId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
	}
}
