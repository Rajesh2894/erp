package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrgDesignationService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.constants.WorkManagementConstant;
import com.abm.mainet.workManagement.datamodel.WMSRateMaster;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.rest.dto.ExpiryItemsDto;
import com.abm.mainet.workManagement.rest.dto.PurchaseRequistionDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.TenderInitiationModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
@Controller
@RequestMapping(MainetConstants.WorksManagement.TENDER_INITIATION_HTML)
public class TenderInitiationController extends AbstractFormController<TenderInitiationModel> {

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private TbDepartmentService iTbDepartmentService;

	@Autowired
	private TbAcVendormasterService vendorMasterService;

	@Autowired
	private TenderInitiationService tenderInitiationService;

	@Autowired
	private TbServicesMstService servicesMstService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	TbOrgDesignationService tbOrgDesignationService;
	

	private static final Logger LOGGER = Logger.getLogger(TenderInitiationController.class);

	/**
	 * default summary page view for tender initiation form
	 * 
	 * @param httpServletRequest
	 * @return summary page view
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final Model model, HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		List<TenderMasterDto> tenderList = new ArrayList<TenderMasterDto>();
		this.getModel().setCommonHelpDocs("TenderInitiation.html");
		setCpdMode();
		ServiceMaster master = servicesMstService.findShortCodeByOrgId(MainetConstants.WorksManagement.TND_SHORT_CODE,
				orgId);
		if (master != null) {
			Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			
			String status = MainetConstants.BLANK;
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
				 status = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getSmServActive(), organisation).getDescLangFirst();
			else
				 status = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getSmServActive(), organisation).getLookUpDesc();
				
			if (status.equals(MainetConstants.Common_Constant.ACTIVE)) {
				this.getModel().setServiceMaster(master);
				this.getModel().setServiceFlag(MainetConstants.FlagA);
				tenderList = tenderInitiationService.findAllTenderList(orgId);
				model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
						tenderInitiationService.getAllActiveProjectsForTndServices(orgId));
			} else {
				tenderList = tenderInitiationService.getAllTenders(orgId);
				model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
						tenderInitiationService.getAllActiveTenderProjects(orgId));
			}
		} else {
			tenderList = tenderInitiationService.getAllTenders(orgId);
			model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
					tenderInitiationService.getAllActiveTenderProjects(orgId));
		}
		model.addAttribute(MainetConstants.WorksManagement.TENDER_LIST, tenderList);

		return defaultResult();
	}

	/**
	 * used for prepare tender initiation form details
	 * 
	 * @return tender initiation form
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
	public ModelAndView tenderInitiationForm(HttpServletRequest httpServletRequest,
			@RequestParam(value = MainetConstants.WorksManagement.TD_ID, required = false) Long tdId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = false) String formMode) {
		TenderInitiationModel tenderModel = this.getModel();
		populateModel(tenderModel, tdId, formMode);
		ServiceMaster master = servicesMstService.findShortCodeByOrgId(MainetConstants.WorksManagement.TND_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		String processName = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getSmProcessId(), UserSession.getCurrent().getOrganisation())
				.getLookUpDesc();
		this.getModel().getInitiationDto().setProcessName(processName);
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_INITIATION_FORM, MainetConstants.FORM_NAME,
				tenderModel);

	}

	/**
	 * populate common field data for create,update and view mode
	 * 
	 * @param tenderModel
	 * @param tdId
	 * @param formMode
	 */
	private void populateModel(TenderInitiationModel tenderModel, Long tdId, String formMode) {
		this.getModel().setDepartmentList(
				iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		tenderModel.setVenderCategoryList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VEC,
				UserSession.getCurrent().getOrganisation()));
		tenderModel.setWorkDurationUnit(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.UTS,
				UserSession.getCurrent().getOrganisation()));
		tenderModel.setTenderTpyes(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		tenderModel.setTenderPercentageAmount(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.TPA,
				UserSession.getCurrent().getOrganisation()));

		// Changes by Suhel
		List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
				UserSession.getCurrent().getOrganisation());
		if (lookUpList != null && !lookUpList.isEmpty()) {
			tenderModel.setModeCpd(lookUpList.get(0).getLookUpCode());
		} else {
			tenderModel.setModeCpd(null);
		}

		if (tdId == null) {
			this.getModel().setMode(MainetConstants.MODE_CREATE);
		} else {
			TenderMasterDto masDto = tenderInitiationService.getPreparedTenderDetails(tdId);
			String shortCode=iTbDepartmentService.findById(masDto.getDeptId()).getDpDeptcode();
			masDto.setDeptCode(shortCode);
			tenderModel.setInitiationDto(masDto);
			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
							masDto.getProjectCode() + MainetConstants.WINDOWS_SLASH + tdId);
			this.getModel().setAttachDocsList(attachDocs);

			if (formMode.equals(MainetConstants.MODE_EDIT)) {
				this.getModel().setMode(MainetConstants.MODE_EDIT);
				this.getModel()
						.setWorkList(workDefinitionService.findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(
								masDto.getProjId(), UserSession.getCurrent().getOrganisation().getOrgid(), tdId));

				// mark tender work as initiated in given work list.
				masDto.getWorkDto().forEach(tenderWork -> {
					this.getModel().getWorkList().forEach(work -> {
						if (work.getWorkId().longValue() == tenderWork.getWorkId().longValue()) {
							work.setTenderInitiated(true);
						}
					});
				});
				// sort tender work list by initiation order
				this.getModel().getWorkList()
						.sort(Comparator.comparing(WorkDefinitionDto::isTenderInitiated).reversed());
			} else {
				// set work for view mode
				if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(shortCode)) {
					  getPurchaseRequistionData(masDto);
				 }else {
				
				   List<Long> workIds = new ArrayList<>();
					masDto.getWorkDto().forEach(work -> {
						workIds.add(work.getWorkId());
					});
					this.getModel().setWorkList(workDefinitionService
							.findAllWorkByWorkList(UserSession.getCurrent().getOrganisation().getOrgid(), workIds));
				
					masDto.getWorkDto().forEach(tenderWork -> {
						this.getModel().getWorkList().forEach(work -> {
							work.setTenderInitiated(true);
							if (work.getWorkId().longValue() == tenderWork.getWorkId().longValue()) {
								String cpdValue = CommonMasterUtility
										.getCPDDescription(tenderWork.getTenderType().longValue(), MainetConstants.FlagV);
								if (cpdValue.equals(MainetConstants.WorksManagement.PERCENT)) {
									work.setTenderpercent(cpdValue);
								}
								if (cpdValue.equals(MainetConstants.WorksManagement.AMT)) {
									work.setTenderpercent(cpdValue);
								}
							}
						});
					});
				  }
				this.getModel().setMode(MainetConstants.MODE_VIEW);
				this.getModel().setAmountToCheckValidation(new BigDecimal(MainetConstants.WorksManagement.AMOUNT));

			}

		}

	}

	/**
	 * get All Active Projects by department
	 * 
	 * @param httpServletReuest
	 * @param deptId
	 * @return List<WmsProjectMasterDto>
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_PROJECT_LIST, method = RequestMethod.POST)
	public List<WmsProjectMasterDto> getProjectList(HttpServletRequest httpServletReuest,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {

		return ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
				.getAllActiveProjectsByDeptIdAndOrgId(deptId, UserSession.getCurrent().getOrganisation().getOrgid());
	}

	/**
	 * get All approved works of project
	 * 
	 * @param httpServletReuest
	 * @param projId
	 * @return estimated work form
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.GET_ESTIMATED_WORK, method = RequestMethod.POST)
	public ModelAndView getAllEstimatedWorkOfProject(HttpServletRequest httpServletReuest,
			@RequestParam(name=MainetConstants.WorksManagement.PROJ_ID,required=false) Long projId,
			@RequestParam(required=false,name="deptId") Long deptId,@RequestParam(required=false,name="deptCode") String deptCode,
			@RequestParam(required=false,name="projCode") String projCode) {
		if((MainetConstants.DEPT_SHORT_NAME.STORE).equals(deptCode)) {
			fetchStoreTenderList(deptId,projCode);
		}else {
			this.getModel().setWorkList(workDefinitionService.findAllApprovedNotInitiatedWorkByProjIdAndOrgId(projId,
					UserSession.getCurrent().getOrganisation().getOrgid()));
		}
		if (this.getModel().getWorkList() != null && !this.getModel().getWorkList().isEmpty()) {
			return new ModelAndView(MainetConstants.WorksManagement.TENDER_ESTIMATED_PROJECT, MainetConstants.FORM_NAME,
					getModel());
		} else {
			return null;
		}
	}

	/**
	 * @param deptId
	 */
	private void fetchStoreTenderList(Long deptId,String projCode) {
		List<WorkDefinitionDto> defintionList =new ArrayList<>();

		List<PurchaseRequistionDto> purchaseRequistionDtoList =new ArrayList<>();
		List<ExpiryItemsDto> expiryItemsDtoList =new ArrayList<>();
		
		String deptRestCallKey;
		if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(projCode))
			deptRestCallKey = ServiceEndpoints.FETCH_PURCHASE_REQUISATION;
		else
			deptRestCallKey = ServiceEndpoints.FETCH_EXPIRY_DISPOSAL;
		
		ResponseEntity<?> responseEntity = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("deptId", String.valueOf(deptId));
		requestParam.put("orgId", String.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
		URI uri = dd.expand(deptRestCallKey, requestParam);
		try {
			responseEntity = RestClient.callRestTemplateClient(new ArrayList<>(), uri.toString());
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				//Getting all records with Status A 
				final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) responseEntity.getBody();
				try {
					for (LinkedHashMap<Long, Object> obj : responseVo) {
						final String d = new JSONObject(obj).toString();
						if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(projCode)) {
						   PurchaseRequistionDto purRequistionDto = new ObjectMapper().readValue(d, PurchaseRequistionDto.class);
						   purchaseRequistionDtoList.add(purRequistionDto);
						}else {
							ExpiryItemsDto expiryItemsDto = new ObjectMapper().readValue(d, ExpiryItemsDto.class);
							expiryItemsDtoList.add(expiryItemsDto);
						}
					}
				} catch (JsonParseException e) {
					throw new FrameworkException(e);
				} catch (JsonMappingException e) {
					throw new FrameworkException(e);
				} catch (IOException e) {
					throw new FrameworkException(e);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception occured while fetching work order details : ", ex);
			throw new FrameworkException(ex);
		}
		if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(projCode)) {
			/*list.stream().forEach(purchaseRequistionDto -> {
				WorkDefinitionDto workDefinitionDto =new WorkDefinitionDto();
				workDefinitionDto.setWorkId(purchaseRequistionDto.getPrId());
				workDefinitionDto.setWorkcode(purchaseRequistionDto.getPrNo());
				defintionList.add(workDefinitionDto);
			});*/
			defintionList = purchaseRequistionDtoList.stream().map(purReqDto -> new WorkDefinitionDto(purReqDto.getPrId(), purReqDto.getPrNo(),
					purReqDto.getTotalYeBugAmount())).collect(Collectors.toList());
		}else {
			// parameterized constructor used
			defintionList = expiryItemsDtoList.stream().map(expiryDto -> new WorkDefinitionDto(expiryDto.getExpiryId(), expiryDto.getScrapNo()))
					.collect(Collectors.toList());
		}
		this.getModel().setWorkList(defintionList);
	}

	/**
	 * filter tender details record based on search criteria
	 * 
	 * @param request
	 * @param projId
	 * @param initiationNo
	 * @param initiationDate
	 * @param tenderCategory
	 * @param venderClassId
	 * @return TenderMasterDto
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.FILTER_TENDER_DATA, method = RequestMethod.POST)
	public @ResponseBody List<TenderMasterDto> searchTenderDetails(final HttpServletRequest request,
			@RequestParam(value = MainetConstants.WorksManagement.PROJ_ID, required = false) Long projId,
			@RequestParam(value = MainetConstants.WorksManagement.INITIATION_NO, required = false) String initiationNo,
			@RequestParam(value = MainetConstants.WorksManagement.INITIAITON_DATE, required = false) Date initiationDate,
			@RequestParam(value = MainetConstants.WorksManagement.TENDER_CATEGORY, required = false) Long tenderCategory) {
		String flag = null;
		if (this.getModel().getServiceFlag() != null) {
			flag = "A";
		}
		return tenderInitiationService.searchTenderDetails(UserSession.getCurrent().getOrganisation().getOrgid(),
				projId, initiationNo, initiationDate, tenderCategory, flag);
	}

	/**
	 * delete tender details
	 * 
	 * @param tndId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.DELETE_TENDER, method = RequestMethod.POST)
	public boolean deleteTender(@RequestParam(MainetConstants.WorksManagement.TND_ID) Long tndId) {
		tenderInitiationService.deleteTender(tndId);
		return true;
	}

	/**
	 * it is used to initiate tender.
	 * 
	 * @param tenderId
	 * @return if successfully initiated than return 'Y' else return error message
	 *         as result.
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.INITIATE_TENDER, method = RequestMethod.POST)
	public String initiateTender(@RequestParam(MainetConstants.WorksManagement.TENDER_ID) Long tenderId) {
		return tenderInitiationService.initiateTender(tenderId, UserSession.getCurrent().getOrganisation().getOrgid());
	}

	/**
	 * it is used to update initiated tender form details like vendor name.tender
	 * amount and tender date
	 * 
	 * @param httpServletRequest
	 * @param model
	 * @param tdId
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.UPDATE_TENDER, method = RequestMethod.POST)
	public ModelAndView updateTenderDetails(HttpServletRequest httpServletRequest, final Model model,
			@RequestParam(value = MainetConstants.WorksManagement.TND_ID) Long tdId,
			@RequestParam(value = MainetConstants.WorksManagement.LOA_MODE, required = false) String loaMode,
			@RequestParam(value = "mode", required = false) String mode) {
		TenderInitiationModel tenderModel = this.getModel();
		this.getModel().setTndId(tdId);
		tenderModel.setLoaMode(loaMode);
		this.getModel().setDepartmentList(
				iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setDesignationList(
				tbOrgDesignationService.findAllByOrigId(UserSession.getCurrent().getOrganisation().getOrgid()));
		TenderMasterDto tenderDto = tenderInitiationService.getPreparedTenderDetails(tdId);
		List<TenderWorkDto> workDtoList = new ArrayList<TenderWorkDto>();
		tenderDto.getWorkDto().forEach(dto -> {
			if (dto.getTenderType() != null) {
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getTenderType(),
						UserSession.getCurrent().getOrganisation());
				dto.setTenderTypeCode(lookUp.getLookUpCode());
			}
			workDtoList.add(dto);

		});
		tenderDto.setWorkDto(workDtoList);
		String shortCode=iTbDepartmentService.findById(tenderDto.getDeptId()).getDpDeptcode();
		tenderDto.setDeptCode(shortCode);
		if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(shortCode)) {
			  getPurchaseRequistionData(tenderDto);
		}
		tenderModel.setInitiationDto(tenderDto);
		if (tenderDto.isLoaGenerated() == true) {
			tenderModel.setTenderUpdated(MainetConstants.FlagY);
		} else {
			tenderModel.setTenderUpdated(MainetConstants.FlagN);
		}

		final Long vendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
						UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
				.getLookUpId();
		model.addAttribute(MainetConstants.WorksManagement.VENDOR_LIST,
				ApplicationContextProvider.getApplicationContext().getBean(TbAcVendormasterService.class)
						.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));

		this.getModel().setMode(MainetConstants.FlagU);// tender update mode
		// #81911
		if (mode != null) {
			this.getModel().setMode(MainetConstants.FlagV);
		}
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_DETAIL_UPDATE, MainetConstants.FORM_NAME,
				tenderModel);

	}

	/**
	 * @param tenderDto
	 */
	private void getPurchaseRequistionData(TenderMasterDto tenderDto) {
		List<Long> workIds =new ArrayList<>();		  
		final ResponseEntity<?> responseEntity;

		if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(tenderDto.getProjectCode())) {
			tenderDto.getWorkDto().forEach(dto -> workIds.add(dto.getPrId()));
			
			PurchaseRequistionDto purRequistionDto =new PurchaseRequistionDto();
			purRequistionDto.setOrgId(tenderDto.getOrgId());
			purRequistionDto.setPrIds(workIds);
			responseEntity = RestClient.callRestTemplateClient(purRequistionDto, ServiceEndpoints.FETCH_PURCHASE_REQ);
		}else {
			tenderDto.getWorkDto().forEach(dto -> workIds.add(dto.getExpiryId()));
			  
			ExpiryItemsDto expiryItemsDto =new ExpiryItemsDto();
			expiryItemsDto.setOrgId(tenderDto.getOrgId());
			expiryItemsDto.setExpiryIds(workIds);
			responseEntity = RestClient.callRestTemplateClient(expiryItemsDto, ServiceEndpoints.FETCH_EXPIRY_DISPOSAL_CODES);
		}
		
		if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
			final LinkedHashMap<String, String> responseMap = (LinkedHashMap<String, String>) responseEntity.getBody();
			tenderDto.getWorkDto().forEach(tenderWork -> {
				if(null != tenderWork.getPrId()) {
					tenderWork.setWorkCode(responseMap.get(tenderWork.getPrId().toString()));
				} else
					tenderWork.setWorkCode(responseMap.get(tenderWork.getExpiryId().toString()));

				tenderWork.setWorkName(MainetConstants.BLANK);
			});
		} else {
			tenderDto.getWorkDto().forEach(tenderWork -> {
				tenderWork.setWorkCode(MainetConstants.BLANK);
				tenderWork.setWorkName(MainetConstants.BLANK);
			});
		}
	}

	/**
	 * save update tender details form
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.SAVE_UPDATED_TENDER, method = RequestMethod.POST)
	public ModelAndView saveUpdateTender(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		final TenderInitiationModel model = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		ModelAndView mv = null;
		try {
			if (model.validateUpdateForm(model.getInitiationDto())) {
				tenderInitiationService.saveTenderUpdateDetails(model.getInitiationDto(), org);
				return jsonResult(JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("tender.award.success")));
			} else {
				mv = new ModelAndView(MainetConstants.WorksManagement.TENDER_DETAIL_VALIDN, MainetConstants.FORM_NAME,
						getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;
			}
		} catch (Exception ex) {
			throw new FrameworkException("Exception while saving update tender details.");
		}
	}

	/**
	 * this method is used to open work estimate details form for particular work
	 * 
	 * @param httpServletRequest
	 * @param workId
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_ESTIMATION, method = RequestMethod.POST)
	public ModelAndView openEstimationForm(HttpServletRequest httpServletRequest,
			@RequestParam(MainetConstants.WorkDefination.WORK_ID) Long workId) {
		getModel().bind(httpServletRequest);
		ModelAndView modelAndView=null;
		httpServletRequest.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE, MainetConstants.WorksManagement.TNDR);
	    this.getModel().getInitiationDto().setTenderFormMode(this.getModel().getMode());
		if(MainetConstants.DEPT_SHORT_NAME.STORE.equals(this.getModel().getInitiationDto().getDeptCode())){
			if(WorkManagementConstant.Tender.PURCHASE_REQ.equals(this.getModel().getInitiationDto().getProjectCode())) {
				modelAndView =new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_PURCHASE_REQ_1+workId+MainetConstants.WorksManagement.REDIRECTTO_PURCHASE_REQ_2);
			}else {
				modelAndView =new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_DISPOSAL_REQ_1+workId+MainetConstants.WorksManagement.REDIRECTTO_PURCHASE_REQ_2);
			}
		}else {
			modelAndView =new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_WORKESTIMATE + workId
					+ MainetConstants.WorksManagement.AND_MODE + MainetConstants.MODE_VIEW);
		}
		return modelAndView;
	}
	

	/**
	 * to view current form with enter details while back from estimate form
	 * 
	 * @param httpServletRequest
	 * @return current form
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_FORM, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showCurrentForm(HttpServletRequest httpServletRequest, final Model models) {
		getModel().bind(httpServletRequest);
		this.getModel().setMode(this.getModel().getInitiationDto().getTenderFormMode());;
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_INITIATION_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.GEN_PRINT_LOA, method = RequestMethod.POST)
	public ModelAndView generateAndPrintLOA(HttpServletRequest httpServletRequest,
			@RequestParam(value = MainetConstants.WorksManagement.TND_ID) Long tndId) {
		TenderWorkDto tenderDTO = tenderInitiationService.generateLOA(tndId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (tenderDTO.getTndLOADate() != null) {
			tenderDTO.setTndLoaDateFormat(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tenderDTO.getTndLOADate()));
		}
		if (tenderDTO.getTenderDate() != null) {
		 tenderDTO.setTndDateFormat(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tenderDTO.getTenderDate()));
		}
		this.getModel().setLoaTenderDetails(tenderDTO);
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_LOA_LATTER, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.PRINT_NOTICE, method = RequestMethod.POST)
	public ModelAndView printNoticeInvitingTender(HttpServletRequest httpServletRequest,
			@RequestParam(value = MainetConstants.WorksManagement.TND_WORKID) Long tndAndWorkId) {
		TenderWorkDto tenderDTO = tenderInitiationService.findNITAndPqDocFormDetailsByWorkId(tndAndWorkId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setPrintNoticeInvintingTender(tenderDTO);

		return new ModelAndView(MainetConstants.WorksManagement.NOTICE_INVITING_TENDER, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.PRE_QUALIFICATION_DOCUMENT, method = RequestMethod.POST)
	public ModelAndView preQualificationDocument(HttpServletRequest httpServletRequest,
			@RequestParam(value = MainetConstants.WorksManagement.TND_WORKID) Long tndAndWorkId) {
		TenderWorkDto tenderDTO = tenderInitiationService.findNITAndPqDocFormDetailsByWorkId(tndAndWorkId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setPreQualDocument(tenderDTO);
		return new ModelAndView(MainetConstants.WorksManagement.PRE_QUALIFICATION_DOCUMENT, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * print Form A and Form B
	 * 
	 * @param httpServletRequest
	 * @param workId
	 * @param flagForms
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.PRINT_FORM_A_AND_B, method = RequestMethod.POST)
	public ModelAndView printFormAandB(HttpServletRequest httpServletRequest,
			@RequestParam(value = MainetConstants.WorksManagement.WORK_ID) Long workId,
			@RequestParam(value = MainetConstants.WorksManagement.FLAG_FORMS) final String flagForms) {

		TenderWorkDto tenderDTO = tenderInitiationService.findNITAndPqDocFormDetailsByWorkId(workId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setTenderWorksForms(tenderDTO);
		ModelAndView modelAndView = null;
		if (flagForms.equals(MainetConstants.FlagA)) {
			modelAndView = new ModelAndView(MainetConstants.WorksManagement.PRINT_FORM_A, MainetConstants.FORM_NAME,
					this.getModel());
		} else if (flagForms.equals(MainetConstants.FlagB)) {
			modelAndView = new ModelAndView(MainetConstants.WorksManagement.PRINT_FORM_B, MainetConstants.FORM_NAME,
					this.getModel());
		} else {
			modelAndView = new ModelAndView(MainetConstants.WorksManagement.PRINT_FORM_F, MainetConstants.FORM_NAME,
					this.getModel());
		}
		return modelAndView;
	}

	private void setCpdMode() {
		// check for HSF prefix is active or not
		List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
				UserSession.getCurrent().getOrganisation());
		if (lookUpList != null && !lookUpList.isEmpty()) {
			this.getModel().setModeCpd(lookUpList.get(0).getLookUpCode());
		} else {
			this.getModel().setModeCpd(null);
		}
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.FILTER_VENDOR_CLASS, method = RequestMethod.POST)
	public List<LookUp> filterVendorClass(HttpServletRequest httpServletReuest,
			@RequestParam(MainetConstants.WorksManagement.ESTIMATED_AMOUNT) BigDecimal estimatedAmount) {
		return CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VEC, UserSession.getCurrent().getOrganisation()).stream()
				.filter(c -> c.getOtherField() == null || c.getOtherField().isEmpty()
						|| (new BigDecimal(c.getOtherField()).compareTo(estimatedAmount)) > 0)
				.collect(Collectors.toList());
	}

	/**
	 * to view current form with enter details while back Report Form
	 * 
	 * @param httpServletRequest
	 * @return current form
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_TENDER_VIEFORM, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentReport(HttpServletRequest httpServletRequest, final Model models) {
		getModel().bind(httpServletRequest);
		final TenderInitiationModel model = this.getModel();
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_INITIATION_FORM, MainetConstants.FORM_NAME,
				model);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_EMD_AMOUNT, method = RequestMethod.POST)
	public WMSRateMaster getEmdAmount(HttpServletRequest httpServletReuest,
			@RequestParam(MainetConstants.WorksManagement.ESTIMATED_AMOUNT) BigDecimal estimatedAmount) {
		return tenderInitiationService.getEmdAmountFromBrmsRule(UserSession.getCurrent().getOrganisation().getOrgid(),
				estimatedAmount);
	}

	@RequestMapping(method = RequestMethod.POST, params = "getApplicableCheckListAndCharges")
	public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
		this.getModel().bind(request);
		ModelAndView modelAndView = null;
		if (this.getModel().validateInputs()) {
			findApplicableCheckList(request);
			this.getModel().setEnableSubmit(MainetConstants.FlagY);
			modelAndView = new ModelAndView(MainetConstants.WorksManagement.TENDER_INITIATION_FORM,
					MainetConstants.FORM_NAME, this.getModel());
		}
		if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
			modelAndView = new ModelAndView("TenderInitiationValidn", MainetConstants.CommonConstants.COMMAND,
					this.getModel());
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					getModel().getBindingResult());
			this.getModel().setEnableSubmit(MainetConstants.FlagN);
			if (!this.getModel().getCheckList().isEmpty())
				this.getModel().getCheckList().clear();
		}
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	private void findApplicableCheckList(HttpServletRequest request) {
		getModel().bind(request);
		TenderInitiationModel initiationModel = this.getModel();
		initiationModel.getInitiationDto().setProjId(this.getModel().getInitiationDto().getProjId());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO initRequestDto = new WSRequestDTO();
		String chkApplicableOrNot = CommonMasterUtility.getCPDDescription(
				this.getModel().getServiceMaster().getSmChklstVerify(), MainetConstants.FlagV,
				UserSession.getCurrent().getOrganisation().getOrgid());
		initRequestDto.setModelName("ChecklistModel");
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			if (chkApplicableOrNot.equalsIgnoreCase(MainetConstants.FlagA)) {
				List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
				final WSRequestDTO checkRequestDto = new WSRequestDTO();
				checkRequestDto.setDataModel(checkListModel);
				WSResponseDTO checkListResponse = brmsCommonService.getChecklist(checkRequestDto);
				List<DocumentDetailsVO> checklistDoc = Collections.emptyList();
				if (checkListResponse.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checkListResponse.getWsStatus())) {
					checklistDoc = (List<DocumentDetailsVO>) checkListResponse.getResponseObj();
					if (checklistDoc != null && !checklistDoc.isEmpty()) {
						long cnt = 1;
						for (final DocumentDetailsVO doc : checklistDoc) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
					}
					this.getModel().setCheckList(checklistDoc);
				} else {
					initiationModel.addValidationError(
							getApplicationSession().getMessage("wms.problem.while.getting.checklist"));
					LOGGER.error("Problem while getting CheckList");
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(params = "getTndFees", method = RequestMethod.POST)
	public WMSRateMaster getTndFeesAmount(HttpServletRequest httpServletReuest,
			@RequestParam(MainetConstants.WorksManagement.ESTIMATED_AMOUNT) BigDecimal estimatedAmount) {
		return tenderInitiationService.getTndFessFromBrmsRule(UserSession.getCurrent().getOrganisation().getOrgid(),
				estimatedAmount);
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_BID_FORM, method = RequestMethod.POST)
	public ModelAndView openBIDAddForm(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setBidMasterDto(new BidMasterDto());
		List<TbAcVendormaster> acVendormasters = vendorMasterService
				.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setVendorList(acVendormasters);
		this.getModel().setBidMode(MainetConstants.FlagA);
		return new ModelAndView("bidMasterDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_BID_DETAIL_FORM, method = RequestMethod.POST)
	public ModelAndView openAddBIDDetailsForm(HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		BidMasterDto bidMasterDto = this.getModel().getBidMasterDto();
		List<BidMasterDto> savebidMasterDto = tenderInitiationService
				.getBidMAsterByTndId(UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getTndId());
		List<String> techParam = new ArrayList<>();
		List<String> commParam = new ArrayList<>();
		List<Map<String, Long>> techParamMarkList = new ArrayList<Map<String, Long>>();
		List<Map<String, Long>> commParamMarkList = new ArrayList<Map<String, Long>>();

		if (!savebidMasterDto.isEmpty()) {

			savebidMasterDto.get(0).getCommercialBIDDetailDtos().forEach(dto -> {
				commParam.add(dto.getParamDescId());
				Map<String, Long> commParamMark = new HashMap<String, Long>();
				commParamMark.put(dto.getParamDescId(), dto.getBaseRate());
				commParamMarkList.add(commParamMark);
			});

			savebidMasterDto.get(0).getTechnicalBIDDetailDtos().forEach(dto -> {
				techParam.add(dto.getParamDescId());
				Map<String, Long> techParamMark = new HashMap<String, Long>();
				techParamMark.put(dto.getParamDescId(), dto.getMark());
				techParamMarkList.add(techParamMark);
			});
			this.getModel().setTechParamMarks(techParamMarkList);
			this.getModel().setCommParamRate(commParamMarkList);
			this.getModel().setTechParamList(techParam);
			this.getModel().setCommParamList(commParam);

		}

		return new ModelAndView("bidMasterDetails/addDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SAVE_BID_DETAILS, method = RequestMethod.POST)
	public ModelAndView saveBIDDetails(HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		BidMasterDto bidMasterDto = this.getModel().getBidMasterDto();
		//D#138367
        try {
			if (bidMasterDto.getTernitDoc() != null) {
				bidMasterDto.setTernitDoc(prepareFileUploadForImg(bidMasterDto.getTernitDoc()));
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure at the time of ternity document read");
		}

		if (bidMasterDto.getBidId() == null) {
			bidMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			bidMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			bidMasterDto.setCreationDate(new Date());
			bidMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			bidMasterDto.setTndId(this.getModel().getTndId());
			bidMasterDto.setHistFlag(MainetConstants.FlagA);
		} else {
			bidMasterDto.setUpdatedDate(new Date());
			bidMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			bidMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			bidMasterDto.setHistFlag(MainetConstants.FlagU);
		}
		bidMasterDto.getTechnicalBIDDetailDtos().forEach(dto -> {
			if (dto.getTechBidId() == null) {
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setCreationDate(new Date());
				dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				dto.setUpdatedDate(new Date());
				dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			// dto.setBidMasterDto(bidMasterDto);
		});
		bidMasterDto.getCommercialBIDDetailDtos().forEach(dto -> {
			if (dto.getCommBidId() == null) {
				dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setCreationDate(new Date());
				dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				dto.setUpdatedDate(new Date());
				dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			// dto.setBidMasterDto(bidMasterDto);
		});
		bidMasterDto.setTndId(this.getModel().getTndId());
		boolean flag = tenderInitiationService.saveBIDDetail(bidMasterDto);
		if (flag == true)
			this.getModel().setBidMasterDto(new BidMasterDto());
		return new ModelAndView("bidMasterDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.GET_BID_DETAILS, method = RequestMethod.POST)
	public ModelAndView getDetailsOfBID(HttpServletRequest httpServletRequest) {

		List<BidMasterDto> bidMasterDtos = tenderInitiationService
				.getBidMAsterByTndId(UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getTndId());

		if (!bidMasterDtos.isEmpty()) {
			bidMasterDtos.forEach(dto -> {
				dto.setVendorName(vendorMasterService.getVendorNameById(dto.getVendorId(), dto.getOrgId()));

				if (dto.getStatus().equals(MainetConstants.FlagA)) {
					dto.setStatus("Accepted");
				} else if (dto.getStatus().equals(MainetConstants.FlagR)) {
					dto.setStatus("Rejected");
				} else if (dto.getStatus().equals(MainetConstants.FlagL)) {
					dto.setStatus("Lost");
				}
			});
		}
		this.getModel().setBidMasterDtos(bidMasterDtos);

		return new ModelAndView("bidMasterDetails/bidDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_BID_FORM, method = RequestMethod.POST)
	public ModelAndView editBIDForm(final HttpServletRequest request, @RequestParam Long bidId,
			@RequestParam String mode) {
		// sessionCleanup(request);
		BidMasterDto bidMasterDto = new BidMasterDto();

		bidMasterDto = tenderInitiationService
				.getBidMAsterByBIDId(UserSession.getCurrent().getOrganisation().getOrgid(), bidId);
		this.getModel().setBidMasterDto(bidMasterDto);
		this.getModel().setBidMode(mode);
		return new ModelAndView("bidMasterDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_TENDER_UPDATE_FOR_BID, method = RequestMethod.POST)
	public ModelAndView openTenderUpdateForm(HttpServletRequest httpServletRequest, final Model model) {
		TenderInitiationModel tenderModel = this.getModel();
		List<TenderWorkDto> workDtoList = new ArrayList<TenderWorkDto>();
		tenderModel.getInitiationDto().getWorkDto().forEach(dto -> {
			if (dto.getTenderType() != null) {
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getTenderType(),
						UserSession.getCurrent().getOrganisation());
				dto.setTenderTypeCode(lookUp.getLookUpCode());
			}
			workDtoList.add(dto);

		});
		tenderModel.getInitiationDto().setWorkDto(workDtoList);

		final Long vendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
						UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
				.getLookUpId();
		if (!tenderModel.getInitiationDto().isLoaGenerated() == true) {
			this.getModel().getInitiationDto().getWorkDto().get(0).setTenderInitiated(false);
		}
		model.addAttribute(MainetConstants.WorksManagement.VENDOR_LIST,
				ApplicationContextProvider.getApplicationContext().getBean(TbAcVendormasterService.class)
						.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		return new ModelAndView(MainetConstants.WorksManagement.TENDER_DETAIL_UPDATE, MainetConstants.FORM_NAME,
				tenderModel);

	}

	@ResponseBody
	@RequestMapping(params = "getLongValueForTechParam", method = RequestMethod.POST)
	public Long getLongValueForTechParam(HttpServletRequest httpServletRequest, final Model model,
			@RequestParam(name = "paramName") final String paramName) {
		bindModel(httpServletRequest);
		Long marks = new Long(0);
		for (Map<String, Long> techParam : this.getModel().getTechParamMarks()) {
			for (Map.Entry<String, Long> entry : techParam.entrySet()) {
				if (entry.getKey().equals(paramName)) {
					marks = entry.getValue();
				}
			}
		}

		return marks;
	}

	@ResponseBody
	@RequestMapping(params = "getLongValueforCommParam", method = RequestMethod.POST)
	public Long getLongValueforCommParam(HttpServletRequest httpServletRequest, final Model model,
			@RequestParam(name = "paramName") final String paramName) {
		bindModel(httpServletRequest);
		Long marks = new Long(0);
		for (Map<String, Long> commParam : this.getModel().getCommParamRate()) {
			for (Map.Entry<String, Long> entry : commParam.entrySet()) {
				if (entry.getKey().equals(paramName)) {
					marks = entry.getValue();
				}
			}
		}
		return marks;
	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> document) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}

		List<DocumentDetailsVO> document1 = new ArrayList<DocumentDetailsVO>();
		if (!listOfString.isEmpty()) {
			long count = 500;
			if (listOfString.containsKey(count) && fileName.containsKey(count)) {
				DocumentDetailsVO vo = new DocumentDetailsVO();
				vo.setDocumentByteCode(listOfString.get(count));
				vo.setDocumentName(fileName.get(count));
				document1.add(vo);
			}

		}

		return document1;
	}
}
