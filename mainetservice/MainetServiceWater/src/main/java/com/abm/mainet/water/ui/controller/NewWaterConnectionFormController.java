package com.abm.mainet.water.ui.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbWorkOrderService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.MeterDetailEntryJpaRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.NewWaterConnectionFormModel;
import com.abm.mainet.water.utility.WaterCommonUtility;

@Controller
@RequestMapping("/NewWaterConnectionForm.html")
public class NewWaterConnectionFormController extends AbstractFormController<NewWaterConnectionFormModel> {

	@Autowired
	NewWaterConnectionService waterService;

	@Autowired
	ServiceMasterService serviceMaster;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	WaterCommonService waterCommonService;

	@Resource
	private NewWaterConnectionService waterConnectionService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private BRMSWaterService brmsWaterService;

	@Autowired
	TbWorkOrderService workOrderService;

	@Autowired
	DesignationService designationService;
	
	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	BillMasterService billMasterService;
	
	@Autowired
    private TbCfcApplicationMstService tbCfcApplicationMstService;
	    
    @Autowired
    private IReceiptEntryService receiptEntryService;
    
	@Resource
    private NewWaterRepository waterRepository;

	@Resource
    private MeterDetailEntryJpaRepository meterDetailEntryJpaRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NewWaterConnectionFormController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setCommonHelpDocs("NewWaterConnectionForm.html");
		final NewWaterConnectionFormModel model = getModel();
		setCommonFields(model);
		 LookUp propNoOptional = null;
			try {
				propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
			}catch (Exception exception) {
				LOGGER.error("No prefix found for WEV(PNO)");
			}
			if(propNoOptional != null) {
				model.setPropNoOptionalFlag(propNoOptional.getOtherField());
			}else {
				model.setPropNoOptionalFlag(MainetConstants.FlagN);
			}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			model.setSudaEnv(MainetConstants.FlagY);
		}
		ModelAndView mv = null;
		mv = new ModelAndView("NewWaterConnectionForm", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	/**
	 * @param model
	 */
	private void setCommonFields(final NewWaterConnectionFormModel model) {
		final TbCsmrInfoDTO dto = model.getCsmrInfo();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		final ServiceMaster service = serviceMaster.getServiceByShortName(PrefixConstants.NewWaterServiceConstants.WNC,
				orgId);
		model.setServiceMaster(service);
		final Long deptId = service.getTbDepartment().getDpDeptid();
		model.setDeptId(deptId);
		if (service != null) {
			model.setServiceId(service.getSmServiceId());
			model.getReqDTO().setServiceId(service.getSmServiceId());
		}
		model.setServiceName(service.getSmServiceName());
		
		List<PlumberMaster> allPlumberList = new ArrayList<>();
		List<PlumberMaster> plumberList = new ArrayList<>();
		
		
		allPlumberList = waterCommonService.listofplumber(orgId);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)){
			model.setIsBillingSame(MainetConstants.NO);
			allPlumberList = allPlumberList.stream()
					.filter(x -> MainetConstants.NewWaterServiceConstants.ANY_PLUMBER.equals(x.getPlumFname()))
					.collect(Collectors.toList());
			plumberList.addAll(allPlumberList);
			if(allPlumberList != null && allPlumberList.size() > 0  ) {
				model.getCsmrInfo().setPlumId(Long.valueOf(allPlumberList.get(0).getPlumId()));
				model.getReqDTO().setPlumberName(allPlumberList.get(0).getPlumFname());
			}
				
		}

		model.setPlumberList(allPlumberList);
		
		Designation dsg = designationService.findByShortname("PLM");

		PlumberMaster master = null;
		if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)){
			if (dsg != null) {

				List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getDsgid(), orgId);
				if (!empList.isEmpty()) {
					for (final Object empObj[] : empList) {
						master = new PlumberMaster();
						master.setPlumId(Long.valueOf(empObj[0].toString()));
						master.setPlumFname(empObj[1].toString());
						master.setPlumMname(empObj[2].toString());
						master.setPlumLname(empObj[3].toString());
						plumberList.add(master);
					}

				}
			}
		}
		
		model.setUlbPlumberList(plumberList);
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			this.getModel().getCsmrInfo().setBplFlag(MainetConstants.FlagN);
			 LookUp propDuesCheck = null;
				try {
					propDuesCheck = CommonMasterUtility.getDefaultValue(PrefixConstants.NewWaterServiceConstants.CHECK_PROPERTY_DUES, UserSession.getCurrent().getOrganisation());
					if(propDuesCheck!=null) {
						if(PrefixConstants.NewWaterServiceConstants.AT_TIME_OF_APPLICATION.equals(propDuesCheck.getLookUpCode()) || PrefixConstants.NewWaterServiceConstants.BOTH.equals(propDuesCheck.getLookUpCode())){
							this.getModel().setPropDuesCheck(true);
						}
					}
				}catch (Exception exception) {
					LOGGER.error("No prefix found for CPD(Check Property Dues)");
				}
				
		}
	}

	@Override
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		if (getModel().getCsmrInfo().getOwnerList() != null) {
			getModel().getCsmrInfo().getOwnerList().clear();
		}
		if (getModel().getCsmrInfo().getLinkDetails() != null) {
			getModel().getCsmrInfo().getLinkDetails().clear();
		}
		bindModel(httpServletRequest);
		if (getModel().saveForm()) {
			return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("water.save")));
		}
		return defaultMyResult();
	}

	@RequestMapping(params = "ShowViewForm", method = RequestMethod.POST)
	public ModelAndView ShowViewForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();
		ModelAndView mv = null;
		model.setShowScrutinyButton(MainetConstants.FlagY);
		if (!MainetConstants.FlagY.equals(model.getScrutinyFlag())) {
			List<DocumentDetailsVO> docs = model.getCheckList();
			docs = fileUpload.prepareFileUpload(docs);
			model.setCheckListForPreview(fileUpload.getUploadedDocForPreview(docs));
			final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
			reqDTO.setDocumentList(docs);
			reqDTO.setCsmrInfo(model.getCsmrInfo());
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());
			setRequestApplicantDetails(model);
			setUpdateFields(model.getCsmrInfo());
			getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
			setCsmrInfoApplicantDetails(reqDTO);
			
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)|| 
					Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				
				model.getCsmrInfo().setCsPanNo(model.getReqDTO().getApplicantDTO().getPanNo());
				
				final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
				for (final LookUp lookUp : lookUps) {
					if ((model.getCsmrInfo().getCsGender() != null) && model.getCsmrInfo().getCsGender() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsGender()) {
							model.getCsmrInfo().setGenderDesc(lookUp.getLookUpDesc());
							break;
						}
					}

				}
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
				final List<LookUp> lookUpDis = CommonMasterUtility.getLookUps("DIS", UserSession.getCurrent().getOrganisation());
				for (final LookUp lookUp : lookUpDis) {
					if (model.getCsmrInfo().getCsDistrict() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsDistrict()) {
							model.getCsmrInfo().setCsDistrictDesc(lookUp.getLookUpDesc());
							break;
						}
					}

				}
				}
//				final List<LookUp> wardLookUps = CommonMasterUtility.getNextLevelData("WWZ", 1, UserSession.getCurrent().getOrganisation().getOrgid());
//				for (final LookUp lookUp : wardLookUps) {
//					if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
//						if (lookUp.getLookUpId() ==model.getCsmrInfo().getCodDwzid1()) {
//							model.getReqDTO().setWard(lookUp.getDescLangFirst());
//							break;
//						}
//					}
//
//				}
//				
			if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) { 
				final List<LookUp> categoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 1,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : categoryLookUps) {
					if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory1()) {
							model.getReqDTO().setCategory(lookUp.getDescLangFirst());
							if("T".equals(lookUp.getLookUpCode()) )
								model.getCsmrInfo().setTypeOfApplication(lookUp.getLookUpCode());
							break;
						}
					}
				  
				 }
				
				final List<LookUp> subCategoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 2,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : subCategoryLookUps) {
					if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory2()) {
							model.getReqDTO().setSubCategory(lookUp.getDescLangFirst());
							break;
						}
					}

				}
			}
				
				final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 1,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : tarrifCategoryLookUps) {
					if ((model.getCsmrInfo().getTrmGroup1() != null) && model.getCsmrInfo().getTrmGroup1() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getTrmGroup1()) {
							model.getReqDTO().setTarrifCategory(lookUp.getDescLangFirst());
							break;
						}
					}

				}
				
				final List<LookUp> connsectionSizeLookUps = CommonMasterUtility.getLookUps("CSZ", UserSession.getCurrent().getOrganisation());
				for (final LookUp lookUp : connsectionSizeLookUps) {
					if ((model.getCsmrInfo().getCsCcnsize() != null) && model.getCsmrInfo().getCsCcnsize() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcnsize()) {
							model.getReqDTO().setConnSize(lookUp.getLookUpDesc());
							break;
						}
					}

				}
				
				 
				
			}
			
		}
		if (model.validateInputs()) {
			if (MainetConstants.FlagY.equals(model.getScrutinyFlag())) {
				model.saveForm();
			}
			mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME, getModel());
		} else {
			mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = "deletedLinkCCnRow", method = RequestMethod.POST)
	public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRow") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		TbKLinkCcnDTO detDto = this.getModel().getCsmrInfo().getLinkDetails().get(deletedRowCount);
		if (detDto != null) {
			detDto.setIsDeleted("Y");
			// this.getModel().getCsmrInfo().getLinkDetails().remove(detDto);
		}
	}

	@RequestMapping(params = "EditApplicationForm", method = RequestMethod.POST)
	public ModelAndView EditApplicationForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		NewWaterConnectionFormModel model = this.getModel();
		model.bind(httpServletRequest);
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		if(model.getReqDTO().getPropertyNo()==null) {
			model.getReqDTO().setPropertyNo(model.getCsmrInfo().getPropertyNo());
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			List<PlumberMaster>allPlumberList = waterCommonService.listofplumber(UserSession.getCurrent().getOrganisation().getOrgid());
			allPlumberList = allPlumberList.stream()
					.filter(x -> MainetConstants.NewWaterServiceConstants.ANY_PLUMBER.equals(x.getPlumFname()))
					.collect(Collectors.toList());
			if(allPlumberList != null && allPlumberList.size() > 0  ) {
				model.getCsmrInfo().setPlumId(allPlumberList.get(0).getPlumId());
				model.getReqDTO().setPlumberName(allPlumberList.get(0).getPlumFname());
			}
			model.setPlumberList(allPlumberList);
					
		}else {
			model.setPlumberList(waterCommonService.listofplumber(UserSession.getCurrent().getOrganisation().getOrgid()));
		}
		
		return defaultMyResult();
	}

	@RequestMapping(params = "SaveAndViewApplication", method = RequestMethod.POST)
	public ModelAndView SaveAndViewApplication(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME,
				getModel());
		final NewWaterConnectionFormModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO = model.getWaterApplicationAcknowledgementDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();

			outPutObject = waterService.saveWaterApplication(reqDTO);
			if (outPutObject != null && (outPutObject.getStatus() != null)
					&& outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {
				LookUp lookUp=null;
	        	try {
	        		lookUp =CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.CWF, MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
	        		if(lookUp != null && StringUtils.equals(MainetConstants.FlagY, lookUp.getOtherField())) {
	        			lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(model.getCsmrInfo().getCsCcnsize(), org);
						Double d=Double.valueOf(lookUp.getLookUpDesc());
						reqDTO.getApplicantDTO().setConnectonSize(new BigDecimal(d));
	        		}
	        		
			    } catch (Exception e) {
			        LOGGER.error("No prefix found for ENV - CWF ", e);
			    }
				
				if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(outPutObject.getStatus())) { // free
					waterService.initiateWorkFlowForFreeService(reqDTO);	
				}
				model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
				reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
				reqDTO.getApplicantDTO().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				WaterCommonUtility.sendSMSandEMail(reqDTO.getApplicantDTO(), reqDTO.getApplicationId(),
						reqDTO.getPayAmount(), MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION,
						UserSession.getCurrent().getOrganisation());
				if ((model.getFree() != null) && model.getFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
					model.save();

				}
				long duration = 0;
				if (model.getServiceMaster().getSmServiceDuration() != null) {
					duration = model.getServiceMaster().getSmServiceDuration();
					//Defect #138346
					model.setWtConnDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(Utility.getAddedDateBy2(new Date(), (int)duration)));
				}
				model.setServiceDuration(
						LocalDate.now().plusDays(duration).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				model.setDistrictDesc(
						CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdDis(), org).getLookUpDesc());
				model.setStateDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdState(), org)
						.getLookUpDesc());
				model.setTalukaDesc(
						CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdId(), org).getLookUpDesc());

				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					waterApplicationAcknowledgementDTO.setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
					waterApplicationAcknowledgementDTO.setServiceName(model.getServiceName());
					
					StringBuilder applicantName = new StringBuilder();
					if(model.getApplicantDetailDto().getApplicantFirstName() != null)
						applicantName.append(model.getApplicantDetailDto().getApplicantFirstName());
					if(model.getApplicantDetailDto().getApplicantMiddleName() != null) {
						applicantName.append(MainetConstants.WHITE_SPACE);
						applicantName.append(model.getApplicantDetailDto().getApplicantMiddleName());
					}
					
					if(model.getApplicantDetailDto().getApplicantLastName() != null) {
						applicantName.append(MainetConstants.WHITE_SPACE);
						applicantName.append(model.getApplicantDetailDto().getApplicantLastName());
					}
						
					waterApplicationAcknowledgementDTO.setApplicantName(applicantName.toString());
					
					waterApplicationAcknowledgementDTO.setApplicationId(outPutObject.getApplicationNo().toString());
					waterApplicationAcknowledgementDTO.setWtConnDueDt(model.getWtConnDueDt());
					waterApplicationAcknowledgementDTO.setAppTime(new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(new Date()));
					fetchCheckListBySTA(model);
					waterApplicationAcknowledgementDTO.setCheckList(model.getCheckList());
				}
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
					
					ProvisionalCertificateDTO provisionalCertificateDTO = new ProvisionalCertificateDTO();
					getProvisionalCertificateData(getModel().getApplicationId(), provisionalCertificateDTO);
					mv = new ModelAndView("NewWaterConnProvisionalCertificate", MainetConstants.FORM_NAME, getModel());
					 
				} else {
					mv = new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME, getModel());
				}
				
			} else {
				if (!outPutObject.getErrorList().isEmpty()) {
					for (final String msg : outPutObject.getErrorList()) {
						model.addValidationError(msg);
					}
				} else {
					return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
				}
			}
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setDocumentList(docs);
		reqDTO.setCsmrInfo(model.getCsmrInfo());
		reqDTO.setApplicantDTO(model.getApplicantDetailDto());
		setRequestApplicantDetails(model);
		setUpdateFields(model.getCsmrInfo());
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		setCsmrInfoApplicantDetails(reqDTO);
		if (model.validateInputs()) {

			NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();

			// Display to console

			outPutObject = waterService.saveWaterApplication(reqDTO);

			try {
				if (outPutObject != null) {
					if ((outPutObject.getStatus() != null)
							&& outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {
						model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
						reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
						WaterCommonUtility.sendSMSandEMail(reqDTO.getApplicantDTO(), reqDTO.getApplicationId(),
								reqDTO.getPayAmount(), MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION,
								UserSession.getCurrent().getOrganisation());
						if ((model.getFree() != null)
								&& model.getFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
							if (model.save()) {
								final CommonChallanDTO offline = model.getOfflineDTO();
								if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck()
										.equals(PrefixConstants.NewWaterServiceConstants.NO)) {
									return jsonResult(JsonViewObject
											.successResult(getApplicationSession().getMessage("continue.forchallan")));
								} else {
									return jsonResult(JsonViewObject
											.successResult(getApplicationSession().getMessage("continue.forpayment")));
								}
							}
						} else {
							return jsonResult(
									JsonViewObject.successResult(getApplicationSession().getMessage("water.success"),
											new Object[] { outPutObject.getApplicationNo() }));
						}
					} else {
						if (!outPutObject.getErrorList().isEmpty()) {
							for (final String msg : outPutObject.getErrorList()) {
								model.addValidationError(msg);
							}
						} else {
							return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
						}
					}
				}

			} catch (final Exception exception) {
				logger.error("Exception found in save method: ", exception);
				return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

			}
		}
		mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	/**
	 * @param model
	 */
	private void setRequestApplicantDetails(final NewWaterConnectionFormModel model) {
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final ApplicantDetailDTO appDTO = model.getApplicantDetailDto();
		reqDTO.setApplicationType(model.getCsmrInfo().getApplicantType());
		reqDTO.setmName(appDTO.getApplicantMiddleName());
		reqDTO.setfName(appDTO.getApplicantFirstName());
		reqDTO.setlName(appDTO.getApplicantLastName());
		reqDTO.setEmail(appDTO.getEmailId());
		reqDTO.setMobileNo(appDTO.getMobileNo());
		// reqDTO.setTitleId(appDTO.getApplicantTitle());
		reqDTO.setBldgName(appDTO.getBuildingName());
		reqDTO.setRoadName(appDTO.getRoadName());
		reqDTO.setAreaName(appDTO.getAreaName());
		if ((appDTO.getPinCode() != null) && !appDTO.getPinCode().isEmpty()) {
			reqDTO.setPincodeNo(Long.valueOf(appDTO.getPinCode()));
		}
		reqDTO.setWing(appDTO.getWing());
		reqDTO.setBplNo(appDTO.getBplNo());
		reqDTO.setDeptId(model.getDeptId());
		reqDTO.setFloor(appDTO.getFloorNo());
		reqDTO.setWardNo(appDTO.getDwzid2());
		reqDTO.setZoneNo(appDTO.getDwzid1());
		reqDTO.setCityName(appDTO.getVillageTownSub());
		reqDTO.setBlockName(appDTO.getBlockName());
		reqDTO.setHouseComplexName(appDTO.getHousingComplexName());
		reqDTO.setFlatBuildingNo(appDTO.getFlatBuildingNo());
		if ((appDTO.getAadharNo() != null) && !appDTO.getAadharNo().equals(MainetConstants.BLANK)) {
			reqDTO.setUid(Long.valueOf(appDTO.getAadharNo()));
		}
		reqDTO.setGender(appDTO.getGender());
	}

	/**
	 * @param csmrInfo
	 */
	/**
	 * @param csmrInfo
	 */
	private void setUpdateFields(final TbCsmrInfoDTO csmrInfo) {
		final NewWaterConnectionFormModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final UserSession session = UserSession.getCurrent();
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
		final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
		reqDTO.setUserId(session.getEmployee().getEmpId());
		reqDTO.setLangId((long) session.getLanguageId());
		reqDTO.setOrgId(session.getOrganisation().getOrgid());
		reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		dto.setOrgId(reqDTO.getOrgId());
		dto.setLmodDate(new Date());
		model.getApplicantDetailDto().setOrgId(session.getOrganisation().getOrgid());
		dto.setUserId(session.getEmployee().getEmpId());
		dto.setLangId(session.getLanguageId());
		dto.setLgIpMac(reqDTO.getLgIpMac());
		dto.setFlatNo(reqDTO.getFlatNo());
		if ((dto.getLinkDetails() != null) && !dto.getLinkDetails().isEmpty()) {
			for (final TbKLinkCcnDTO link : dto.getLinkDetails()) {
				link.setOrgIds(reqDTO.getOrgId());
				link.setUserIds(reqDTO.getUserId());
				link.setLangId(dto.getLangId());
				link.setLgIpMac(reqDTO.getLgIpMac());
				link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
				tempLinkList.add(link);
			}
			dto.setLinkDetails(tempLinkList);
		} else {
			dto.setLinkDetails(tempLinkList);
		}
		if ((dto.getOwnerList() != null) && !dto.getOwnerList().isEmpty()) {
			for (final AdditionalOwnerInfoDTO owner : dto.getOwnerList()) {
				owner.setOrgid(reqDTO.getOrgId());
				owner.setUserId(reqDTO.getUserId());
				owner.setLangId(reqDTO.getLangId());
				owner.setLgIpMac(reqDTO.getLgIpMac());
				owner.setLmoddate(new Date());
				owner.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
				tempOwnerList.add(owner);
			}
			dto.setOwnerList(tempOwnerList);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		final NewWaterConnectionFormModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		setApplicantDetails();
		if (model.validateInputs()) {
			// [START] BRMS call initialize model
			// final WSResponseDTO response = checklistAndChargeService.initializeModel();
			final WSRequestDTO requestDTO = new WSRequestDTO();
			requestDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			// List<DocumentDetailsVO> checkListList = new ArrayList<>();
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
				final List<Object> waterRateMasterList = this.castResponse(response, WaterRateMaster.class, 1);
				final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
				final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
				populateCheckListModel(model, checkListModel2);
				// checkListList = checklistAndChargeService.getChecklist(checkListModel2);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
				checklistReqDto.setDataModel(checkListModel2);
				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
						|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
						List<DocumentDetailsVO> checkListList = Collections.emptyList();
						// final List<?> docs = RestClient.castResponse(checklistRespDto,
						// DocumentDetailsVO.class);
						checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

						long cnt = 1;
						for (final DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
						if ((checkListList != null) && !checkListList.isEmpty()) {
							model.setCheckList(checkListList);
						}
						mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
					} else {
						final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
						reqDTO.setCsmrInfo(model.getCsmrInfo());
						if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)||
								Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)){
							if(reqDTO.getCsmrInfo().getCsGender() != null && reqDTO.getCsmrInfo().getCsGender() > 0) {
								reqDTO.getCsmrInfo().setGenderDesc(CommonMasterUtility
				                    .getNonHierarchicalLookUpObject(reqDTO.getCsmrInfo().getCsGender(), UserSession.getCurrent().getOrganisation())
					                    .getDescLangFirst());
					        }
							if(reqDTO.getCsmrInfo().getCsCcnsize()!=null) {
								reqDTO.getCsmrInfo().setConnectionSize(Double.valueOf(CommonMasterUtility
				                    .getNonHierarchicalLookUpObject(reqDTO.getCsmrInfo().getCsCcnsize(), UserSession.getCurrent().getOrganisation())
					                    .getDescLangFirst()));
							}
							if(reqDTO.getCsmrInfo().getPlumId()!=null) {
					            final PlumberMaster plumber = waterCommonService.getPlumberDetailsById(reqDTO.getCsmrInfo().getPlumId());
					            if(plumber != null){
					            	 String plumName = (plumber.getPlumFname()!=null && !plumber.getPlumFname().isEmpty()? plumber.getPlumFname() + " ": "");
					            			 /*+(plumber.getPlumMname()!=null && !plumber.getPlumMname().isEmpty()? plumber.getPlumMname() + " " : " ") + 
						            			(plumber.getPlumLname()!=null && !plumber.getPlumLname().isEmpty()? plumber.getPlumLname(): "");*/
									reqDTO.setPlumberName(plumName); 	
					            }
							}
							
						if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)){
							final List<LookUp> lookUpDis = CommonMasterUtility.getLookUps("DIS", UserSession.getCurrent().getOrganisation());
							for (final LookUp lookUp : lookUpDis) {
								if (model.getCsmrInfo().getCsDistrict() != 0l) {
									if (lookUp.getLookUpId() == model.getCsmrInfo().getCsDistrict()) {
										model.getCsmrInfo().setCsDistrictDesc(lookUp.getLookUpDesc());
										break;
									}
								}
							}
							
							final List<LookUp> categoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 1,
									UserSession.getCurrent().getOrganisation().getOrgid());
							for (final LookUp lookUp : categoryLookUps) {
								if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
									if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory1()) {
										model.getReqDTO().setCategory(lookUp.getDescLangFirst());
										if("T".equals(lookUp.getLookUpCode()) )
											model.getCsmrInfo().setTypeOfApplication(lookUp.getLookUpCode());
										break;
									}
								}							  
							 }
							
							final List<LookUp> subCategoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 2,
									UserSession.getCurrent().getOrganisation().getOrgid());
							for (final LookUp lookUp : subCategoryLookUps) {
								if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
									if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory2()) {
										model.getReqDTO().setSubCategory(lookUp.getDescLangFirst());
										break;
									}
								}
							}
						}
							final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 1,
									UserSession.getCurrent().getOrganisation().getOrgid());
							for (final LookUp lookUp : tarrifCategoryLookUps) {
								if ((model.getCsmrInfo().getTrmGroup1() != null) && model.getCsmrInfo().getTrmGroup1() != 0l) {
									if (lookUp.getLookUpId() == model.getCsmrInfo().getTrmGroup1()) {
										model.getReqDTO().setTarrifCategory(lookUp.getDescLangFirst());
										break;
									}
								}
							}
							
							final List<LookUp> connsectionSizeLookUps = CommonMasterUtility.getLookUps("CSZ", UserSession.getCurrent().getOrganisation());
							for (final LookUp lookUp : connsectionSizeLookUps) {
								if ((model.getCsmrInfo().getCsCcnsize() != null) && model.getCsmrInfo().getCsCcnsize() != 0l) {
									if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcnsize()) {
										model.getReqDTO().setConnSize(lookUp.getLookUpDesc());
										break;
									}
								}
							}					
						
						}
						reqDTO.setApplicantDTO(model.getApplicantDetailDto());
						setRequestApplicantDetails(model);
						setUpdateFields(model.getCsmrInfo());
						getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
						setCsmrInfoApplicantDetails(reqDTO);
						mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME,
								getModel());
					}

					// checklist done
					// WSResponseDTO responseDTO = null;
					final WSRequestDTO taxRequestDto = new WSRequestDTO();
					waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					waterRateMaster.setServiceCode(PrefixConstants.NewWaterServiceConstants.WNC);
					waterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(
							MainetConstants.NewWaterServiceConstants.APL, MainetConstants.NewWaterServiceConstants.CAA,
							UserSession.getCurrent().getOrganisation()).getLookUpId()));
					LookUp usageSubtype1 = CommonMasterUtility.getHierarchicalLookUp(model.getCsmrInfo().getTrmGroup1()
							, UserSession.getCurrent().getOrganisation().getOrgid());
					waterRateMaster.setUsageSubtype1(usageSubtype1.getDescLangFirst());
					
					
					taxRequestDto.setDataModel(waterRateMaster);
					WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);

					if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
						if (!res.isFree()) {
							// final List<?> rates = this.castResponse(res, WaterRateMaster.class);
							final List<Object> rates = (List<Object>) res.getResponseObj();
							final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
							for (final Object rate : rates) {
								WaterRateMaster master1 = (WaterRateMaster) rate;
								master1 = populateChargeModel(model, master1);
								requiredCHarges.add(master1);
							}
							WSRequestDTO chargeReqDto = new WSRequestDTO();
							chargeReqDto.setModelName("WaterRateMaster");
							chargeReqDto.setDataModel(requiredCHarges);
							WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
							List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges
									.getResponseObj();

							/*
							 * final List<ChargeDetailDTO> detailDTOs = checklistAndChargeService
							 * .getApplicableCharges(requiredCHarges);
							 */
							model.setFree(PrefixConstants.NewWaterServiceConstants.NO);
							model.getReqDTO().setFree(false);
							model.setChargesInfo(detailDTOs);
							model.setCharges((chargesToPay(detailDTOs)));
							setChargeMap(model, detailDTOs);
							model.getOfflineDTO().setAmountToShow(model.getCharges());
						} else {
							model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
							model.getReqDTO().setFree(true);
							model.getReqDTO().setCharges(0.0d);
						}
					} else {
						mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
					}

				} else {
					mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
				}
			}

			// [END]
			else {
				mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
			// [END]
		} else {
			mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void populateCheckListModel(final NewWaterConnectionFormModel model, final CheckListModel checklistModel) {
		checklistModel.setOrgId(model.getOrgId());
		checklistModel.setServiceCode(PrefixConstants.NewWaterServiceConstants.WNC);
		checklistModel.setIsBPL(model.getCsmrInfo().getBplFlag());

		final TbCsmrInfoDTO data = model.getCsmrInfo();
		final Organisation org = UserSession.getCurrent().getOrganisation();		
        LookUp lookUp=null;
        	try {
        		lookUp =CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.CCG, MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
		    } catch (Exception e) {
		        LOGGER.error("No prefix found for ENV - CCG ", e);
		    }
        if(lookUp != null && StringUtils.equals( MainetConstants.FlagY,lookUp.getOtherField())) {
        	if (data.getCsCcncategory1() != null) {
				checklistModel.setUsageSubtype1(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory1(), org).getDescLangFirst());		
			}
			if (data.getCsCcncategory2() != null) {
				checklistModel.setUsageSubtype2(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory2(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory3() != null) {
				checklistModel.setUsageSubtype3(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory3(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory4() != null) {
				checklistModel.setUsageSubtype4(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory4(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory5() != null) {
				checklistModel.setUsageSubtype5(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory5(), org).getDescLangFirst());
			}
        	
        }else {
	        if (data.getTrmGroup1() != null) {
				checklistModel.setUsageSubtype1(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1(), org).getDescLangFirst());
			}
			if (data.getTrmGroup2() != null) {
				checklistModel.setUsageSubtype2(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2(), org).getDescLangFirst());
			}
			if (data.getTrmGroup3() != null) {
				checklistModel.setUsageSubtype3(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3(), org).getDescLangFirst());
			}
			if (data.getTrmGroup4() != null) {
				checklistModel.setUsageSubtype4(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4(), org).getDescLangFirst());
			}
			if (data.getTrmGroup5() != null) {
				checklistModel.setUsageSubtype5(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5(), org).getDescLangFirst());
			}
			if ((model.getReqDTO() != null) && (model.getReqDTO().getExistingConsumerNumber() == null)) {
				checklistModel.setIsExistingConnectionOrConsumerNo(PrefixConstants.NewWaterServiceConstants.NO);

			} else {
				checklistModel.setIsExistingConnectionOrConsumerNo(PrefixConstants.NewWaterServiceConstants.YES);

			}
			if ((model.getReqDTO() != null) && (model.getReqDTO().getPropertyNo() == null)) {
				checklistModel.setIsExistingProperty(PrefixConstants.NewWaterServiceConstants.NO);
			} else {
				checklistModel.setIsExistingProperty(PrefixConstants.NewWaterServiceConstants.YES);
			}
		}

		/*
		 * final LookUp applicantType = CommonMasterUtility
		 * .getNonHierarchicalLookUpObject(Long.valueOf(model.getCsmrInfo().
		 * getApplicantType()));
		 * checklistModel.setApplicantType(applicantType.getDescLangFirst());
		 */

		// checklistModel.setDeptCode(MainetConstants.WATER_DEPT);
		

	}

	private WaterRateMaster populateChargeModel(final NewWaterConnectionFormModel model,
			final WaterRateMaster chargeModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		chargeModel.setOrgId(model.getOrgId());
		chargeModel.setServiceCode(PrefixConstants.NewWaterServiceConstants.WNC);

		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		final TbCsmrInfoDTO data = model.getCsmrInfo();

		if (CollectionUtils.isNotEmpty(chargeModel.getDependsOnFactorList())) {
			chargeModel.getDependsOnFactorList().forEach(dependFactor -> {
				if (StringUtils.equalsIgnoreCase(dependFactor, "BPL")) {
					chargeModel.setIsBPL(model.getCsmrInfo().getBplFlag());
				}

				if (StringUtils.equalsIgnoreCase(dependFactor, "NOF")) {
					if (data.getNoOfFamilies() != null) {
						chargeModel.setNoOfFamilies(data.getNoOfFamilies().intValue());
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactor, "CON")) {
					if (data.getCsCcnsize() != null) {
						chargeModel.setConnectionSize(Double.valueOf(CommonMasterUtility
								.getNonHierarchicalLookUpObject(data.getCsCcnsize(), org).getDescLangFirst()));
					}
				}

				if (StringUtils.equalsIgnoreCase(dependFactor, "TAP")) {
					if (data.getCsTaxPayerFlag() == null || data.getCsTaxPayerFlag().isEmpty()) {
						chargeModel.setTaxPayer(MainetConstants.FlagN);
					} else {
						chargeModel.setTaxPayer(data.getCsTaxPayerFlag());
					}
				}

				if (StringUtils.equalsIgnoreCase(dependFactor, "WTC")) {
					if (data.getTrmGroup1() != null) {
						chargeModel.setUsageSubtype1(
								CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1(), org).getDescLangFirst());
					}
					if (data.getTrmGroup2() != null) {
						chargeModel.setUsageSubtype2(
								CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2(), org).getDescLangFirst());
					}
					if (data.getTrmGroup3() != null) {
						chargeModel.setUsageSubtype3(
								CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3(), org).getDescLangFirst());
					}
					if (data.getTrmGroup4() != null) {
						chargeModel.setUsageSubtype4(
								CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4(), org).getDescLangFirst());
					}
					if (data.getTrmGroup5() != null) {
						chargeModel.setUsageSubtype5(
								CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5(), org).getDescLangFirst());
					}
				}
			});
		}
		return chargeModel;

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setCsmrInfoApplicantDetails(final NewWaterConnectionReqDTO reqDTO) {
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final ApplicantDetailDTO applicantDTO = reqDTO.getApplicantDTO();
		reqDTO.setfName(applicantDTO.getApplicantFirstName());

		if (reqDTO.getIsConsumer() != null && !reqDTO.getIsConsumer().isEmpty()) {
			setConsumerDetails(applicantDTO, dto);
		}
		if (reqDTO.getIsBillingAddressSame() != null && !reqDTO.getIsBillingAddressSame().isEmpty()) {
			setBillingDetails(dto);
		}

		reqDTO.setTitleId(applicantDTO.getApplicantTitle());
		if ((applicantDTO.getAadharNo() != null) && !applicantDTO.getAadharNo().isEmpty()) {
			dto.setCsUid(Long.valueOf(applicantDTO.getAadharNo()));
		}
		dto.setCsApldate(new Date());
		if (dto.getBplFlag().equals(MainetConstants.FlagY))
			applicantDTO.setBplNo(dto.getBplNo());
		/*
		 * dto.setCodDwzid1(applicantDTO.getDwzid1());
		 * dto.setCodDwzid2(applicantDTO.getDwzid2());
		 * dto.setCodDwzid3(applicantDTO.getDwzid3());
		 * dto.setCodDwzid4(applicantDTO.getDwzid4());
		 * dto.setCodDwzid5(applicantDTO.getDwzid5());
		 * dto.setBplNo(applicantDTO.getBplNo());
		 * dto.setBplFlag(applicantDTO.getIsBPL());
		 */

		if (reqDTO.getCsmrInfo().getOwnerList() != null) {
			if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(MainetConstants.ZERO)) {
				reqDTO.getCsmrInfo().setOwnerList(null);
			}
		}
		if (reqDTO.getCsmrInfo().getLinkDetails() != null) {
			if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
				reqDTO.getCsmrInfo().setLinkDetails(null);
			}
		}
	}

	private void setConsumerDetails(ApplicantDetailDTO applicantDTO, TbCsmrInfoDTO tbCsmrInfoDTO) {
		// tbCsmrInfoDTO.setCsTitle(applicantDTO.getApplicantTitle().toString());
		tbCsmrInfoDTO.setCsName(applicantDTO.getApplicantFirstName());
		tbCsmrInfoDTO.setCsMname(applicantDTO.getApplicantMiddleName());
		tbCsmrInfoDTO.setCsLname(applicantDTO.getApplicantLastName());
		tbCsmrInfoDTO.setCsAdd(applicantDTO.getAreaName());
		tbCsmrInfoDTO.setCsBldplt(applicantDTO.getBuildingName());
		tbCsmrInfoDTO.setCsLanear(applicantDTO.getFlatBuildingNo());
		tbCsmrInfoDTO.setCsRdcross(applicantDTO.getRoadName());
		tbCsmrInfoDTO.setCsCcityName(applicantDTO.getVillageTownSub());
		if (applicantDTO.getPinCode() != null && !applicantDTO.getPinCode().isEmpty())
			tbCsmrInfoDTO.setCsCpinCode(Long.valueOf(applicantDTO.getPinCode()));
	}

	private void setBillingDetails(final TbCsmrInfoDTO tbCsmrInfoDTO) {
		tbCsmrInfoDTO.setCsBadd(tbCsmrInfoDTO.getCsAdd());
		tbCsmrInfoDTO.setCsBbldplt(tbCsmrInfoDTO.getCsBldplt());
		tbCsmrInfoDTO.setCsBlanear(tbCsmrInfoDTO.getCsLanear());
		tbCsmrInfoDTO.setCsBrdcross(tbCsmrInfoDTO.getCsRdcross());
		tbCsmrInfoDTO.setCsBcityName(tbCsmrInfoDTO.getCsCcityName());
		tbCsmrInfoDTO.setBpincode(tbCsmrInfoDTO.getCsCpinCode().toString());
	}

	private void setChargeMap(final NewWaterConnectionFormModel model, final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				for (final Object object : list) {
					responseMap = (LinkedHashMap<Long, Object>) object;
					final String jsonString = new JSONObject(responseMap).toString();
					dataModel = new ObjectMapper().readValue(jsonString, clazz);
					dataModelList.add(dataModel);
				}
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@RequestMapping(params = "clearCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView clearCheckListAndChrages(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();
		model.setFree(PrefixConstants.SMS_EMAIL.OTP_MSG);
		model.setChargesInfo(null);
		model.setCharges(0.0d);
		model.getChargesMap().clear();
		model.getOfflineDTO().setAmountToShow(0.0d);
		model.getReqDTO().setFree(true);
		model.getReqDTO().setCharges(0.0d);
		model.getCheckList().clear();
		model.getReqDTO().getDocumentList().clear();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		fileUpload.sessionCleanUpForFileUpload();
		final ModelAndView mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest request) {
		bindModel(request);
		NewWaterConnectionFormModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		TbCsmrInfoDTO propInfoDTO = model.getPropertyDetailsByPropertyNumber(reqDTO);
		String respMsg = "";
		if (propInfoDTO != null) {
			infoDTO.setCsOname(propInfoDTO.getCsOname());
			infoDTO.setCsOcontactno(propInfoDTO.getCsOcontactno());
			infoDTO.setCsOEmail(propInfoDTO.getCsOEmail());
			infoDTO.setOpincode(propInfoDTO.getOpincode());
			infoDTO.setCsOadd(propInfoDTO.getCsOadd());
			// infoDTO.setCsOrdcross(propInfoDTO.getCsOrdcross());
			infoDTO.setPropertyUsageType(propInfoDTO.getPropertyUsageType());
			if (propInfoDTO.getArv() != 0) {
                infoDTO.setArv(propInfoDTO.getArv());
            }
			if (propInfoDTO.getCsOGender() != null && propInfoDTO.getCsOGender() != 0l) {
				infoDTO.setCsOGender(propInfoDTO.getCsOGender());
			}
			reqDTO.setApplicantDTO(new ApplicantDetailDTO());
			infoDTO.setPropertyNo(propInfoDTO.getPropertyNo());
			infoDTO.setTotalOutsatandingAmt(propInfoDTO.getTotalOutsatandingAmt());
			if (infoDTO.getTotalOutsatandingAmt() > 0) {
				model.setPropOutStanding(MainetConstants.FlagY);
			} else {
				model.setPropOutStanding(MainetConstants.FlagN);
			}
		} else {
			respMsg = ApplicationSession.getInstance().getMessage("water.dataentry.validation.property.not.found");
			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		}
		return defaultMyResult();
	}

	/**
	 * Get Connection Details
	 * 
	 * @param connectionNo
	 * @return TbCsmrInfoDTO
	 */
	@ResponseBody
	@RequestMapping(params = "getConnectionDetails", method = RequestMethod.POST)
	public TbCsmrInfoDTO getConnectionDetails(@RequestParam("ccnNo") String ccnNo) {
		try {
			TbCsmrInfoDTO infoDto = waterCommonService.fetchConnectionDetailsByConnNo(ccnNo,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
			if(infoDto!=null) {
				List<TbBillMas> bill = billMasterService.getBillMasterListByUniqueIdentifier(infoDto.getCsIdn(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (!bill.isEmpty()) {
					infoDto.setCcnOutStandingAmt(Double.toString(bill.get(bill.size() - 1).getBmTotalOutstanding()));
				} else {
					infoDto.setCcnOutStandingAmt("0");
				}
	        	List<TbMeterMasEntity> meterMasEntities = meterDetailEntryJpaRepository.getMeterMasEntities(infoDto.getCsIdn(), 
	        			 UserSession.getCurrent().getOrganisation().getOrgid());
	        	if(meterMasEntities!=null && !meterMasEntities.isEmpty()) {
		        	infoDto.setMeterNumber(meterMasEntities.get(meterMasEntities.size()-1).getMmMtrno());
	        	}
			}
			return infoDto;
		} catch (Exception e) {
			return new TbCsmrInfoDTO();
		}

	}

	private void setApplicantDetails() {
		NewWaterConnectionFormModel model = this.getModel();
		UserSession session = UserSession.getCurrent();
		NewWaterConnectionReqDTO connectionDTO = model.getReqDTO();
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		connectionDTO.setUserId(session.getEmployee().getEmpId());
		connectionDTO.setOrgId(session.getOrganisation().getOrgid());
		connectionDTO.setLangId((long) session.getLanguageId());
		connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

		if (model.getIsConsumerSame().equalsIgnoreCase(MainetConstants.FlagY)) {
			connectionDTO.setIsConsumer(MainetConstants.FlagY);
			infoDTO.setCsName(infoDTO.getCsOname());
			if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
				infoDTO.setCsGender(infoDTO.getCsOGender());
			}
			infoDTO.setCsContactno(infoDTO.getCsOcontactno());
			infoDTO.setCsEmail(infoDTO.getCsOEmail());
			infoDTO.setCsAdd(infoDTO.getCsOadd());
			if (infoDTO.getOpincode() != null) {
				infoDTO.setCsCpinCode(Long.valueOf(infoDTO.getOpincode()));
			}
		}
		if (model.getIsBillingSame().equalsIgnoreCase(MainetConstants.FlagY)) {
			connectionDTO.setIsBillingAddressSame(MainetConstants.FlagY);
			infoDTO.setCsBadd(infoDTO.getCsAdd());
			if (infoDTO.getCsCpinCode() != null) {
				infoDTO.setBpincode(infoDTO.getCsCpinCode().toString());
			}
		}

		connectionDTO.setCityName(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		connectionDTO.setRoadName(infoDTO.getCsAdd());
		if (infoDTO.getCsCpinCode() != null) {
			connectionDTO.setPinCode(infoDTO.getCsCpinCode());
			connectionDTO.setPincodeNo(infoDTO.getCsCpinCode());
		}
		connectionDTO.setAreaName(infoDTO.getCsAdd());
		connectionDTO.setBldgName(infoDTO.getCsAdd());
		connectionDTO.setBlockName(infoDTO.getCsAdd());
		//connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsAdd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		appDTO.setIsBPL(infoDTO.getBplFlag());
		if (infoDTO.getBplFlag().equals(MainetConstants.FlagY))
			appDTO.setBplNo(infoDTO.getBplNo());
		// Need To Check
		appDTO.setAadharNo(connectionDTO.getApplicantDTO().getAadharNo());
		infoDTO.setCsAadhar(connectionDTO.getCsmrInfo().getCsAadhar());
		if (infoDTO.getCsCpinCode() != null) {
			appDTO.setPinCode(infoDTO.getCsCpinCode().toString());
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((infoDTO.getCsOGender() != null) && infoDTO.getCsOGender() != 0l) {
				if (lookUp.getLookUpId() == infoDTO.getCsOGender()) {
					appDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		appDTO.setAreaName(infoDTO.getCsAdd());
		model.setApplicantDetailDto(appDTO);
		connectionDTO.setCsmrInfo(infoDTO);

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.VIEW_TERMS_CONDITION)
	public ModelAndView tradeTermsCondition(final HttpServletRequest request) {
		bindModel(request);

		return new ModelAndView("NewWaterTermsDetail", MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@RequestMapping(params = "printWaterConAckw", method = { RequestMethod.POST })
	public ModelAndView printWaterConnAcknowledgement(HttpServletRequest request) {
		NewWaterConnectionFormModel model = this.getModel();
		String docStatus = new String();
		Long appId = null;
		if (model.getCsmrInfo().getApplicationNo()!=null) {
			appId = model.getCsmrInfo().getApplicationNo();
		}
		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			if (model.getCsmrInfo() != null && appId != null) {
				List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
						.getBean(IChecklistVerificationService.class).getAttachDocumentByDocumentStatus(appId,
								docStatus, UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(documentUploaded)) {
					model.setDocumentList(documentUploaded);
				}
			}
			if (appId != null) {
				TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService
						.getCFCApplicationByApplicationId(appId, UserSession.getCurrent().getOrganisation().getOrgid());
				model.setCfcEntity(cfcEntity);
			}
			model.setApplicationId(model.getCsmrInfo().getApplicationNo());
			model.setApplicantName(model.getCsmrInfo().getCsName());
			model.setServiceName(model.getServiceMaster().getSmServiceName());
		}
		return new ModelAndView("waterAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}
	
	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public Map<String, List<String>> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo,
			HttpServletRequest request) throws ClassNotFoundException, LinkageError {
		this.getModel().bind(request);
		Map<String, List<String>> resultMap = new HashMap<>();
		String billingMethod =  getPropertyBillingMethod(propNo);		
		List<String> flatNoList = getPropertyFlatList(propNo);
		
		if (StringUtils.isEmpty(billingMethod)) {
			resultMap.put("NULL", flatNoList);
		} else {
			resultMap.put(billingMethod, flatNoList);
		}

		return resultMap;
	}
	
	private String getPropertyBillingMethod(final String propNo) throws ClassNotFoundException, LinkageError {
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		serviceClassName = "com.abm.mainet.property.service.PropertyNoDuesCertificateServiceImpl";
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());
		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 2, false);
		final Method method = ReflectionUtils.findMethod(clazz,MainetConstants.Property.GET_PROPERTY_BILLING_METHOD,
				new Class[] { String.class, Long.class });
		Object obj = (Object) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { propNo, orgId });

		if (obj != null) {
			return  (String) new JSONObject(obj.toString()).get("billingMethod");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getPropertyFlatList(final String propNo) throws ClassNotFoundException, LinkageError {
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		serviceClassName = "com.abm.mainet.property.service.PropertyNoDuesCertificateServiceImpl";
		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());
		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 2, false);
		final Method method = ReflectionUtils.findMethod(clazz,MainetConstants.Property.GET_PROPERTY_FLAT_LIST,
				new Class[] { String.class, String.class });
		List<String> list = (List<String>)ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { propNo, String.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()) });

		if (list != null && !list.isEmpty())
			return list;
		return null;
	}
	
	private void fetchCheckListBySTA(NewWaterConnectionFormModel model) {
        final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
        checkListRateRequestModel.setModelName(MainetConstants.MRM.CHECKLIST_MRMRATEMASTER);
        WSResponseDTO checkListRateResponseModel = brmsCommonService.initializeModel(checkListRateRequestModel);

        if (StringUtils.equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS,
                checkListRateResponseModel.getWsStatus())) {
            final WSRequestDTO requestDTO = new WSRequestDTO();
			requestDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
				final List<Object> waterRateMasterList = this.castResponse(response, WaterRateMaster.class, 1);
				final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
				final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
				populateCheckListModel(model, checkListModel2);
				
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
				checklistReqDto.setDataModel(checkListModel2);
				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
				
				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					// final List<?> docs = RestClient.castResponse(checklistRespDto,
					// DocumentDetailsVO.class);
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;
				
					if ((checkListList != null) && !checkListList.isEmpty()) {
						model.setCheckList(checkListList);
					}
				}
				else {
					model.addValidationError(getApplicationSession().getMessage("mrm.checklist.not.found"));
				}
			}
        }
           
    }
	
	/*
	 * @RequestMapping(params = "getProvisionalCertificateData", method =
	 * RequestMethod.POST)
	 */
	private ProvisionalCertificateDTO getProvisionalCertificateData(Long applicationNo, 
			ProvisionalCertificateDTO provisionCertificateDTO) {
		LOGGER.info("Begin getProvisionalCertificateData--> " + this.getClass().getSimpleName());
		try {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			
			applicationNo = getModel().getResponseDTO().getApplicationNo();
			TbKCsmrInfoMH csmrInfo = waterRepository.getApplicantInformationById(applicationNo, orgId);
			if(csmrInfo!=null) {
				provisionCertificateDTO.setApplicationNo(csmrInfo.getApplicationNo());
				try {
					TbCfcApplicationMst applicationMst = tbCfcApplicationMstService.findById(csmrInfo.getApplicationNo());
					provisionCertificateDTO.setApplicationDate(applicationMst.getApmApplicationDate());
					provisionCertificateDTO.setProvApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(applicationMst.getApmApplicationDate()));

				}catch(Exception ex) {
					LOGGER.error(ex.getMessage());
				}
				provisionCertificateDTO.setApplicationFee(getModel().getCharges().doubleValue());
				
				String firstName = csmrInfo.getCsName() != null && !csmrInfo.getCsName().isEmpty() ? csmrInfo.getCsName(): "";
				String middleName = csmrInfo.getCsMname() != null && !csmrInfo.getCsMname().isEmpty() ? csmrInfo.getCsMname(): "";
				String lastName = csmrInfo.getCsLname() != null && !csmrInfo.getCsLname().isEmpty() ? csmrInfo.getCsLname(): "";
				provisionCertificateDTO.setFullName(String.join(" ", Arrays.asList(firstName, middleName, lastName)));
				
		        StringBuilder address1 = new StringBuilder(csmrInfo.getCsAdd()!=null && !csmrInfo.getCsAdd().isEmpty() ?
		        		csmrInfo.getCsAdd(): StringUtils.EMPTY);
		        address1.append(csmrInfo.getFlatNo()!=null && !csmrInfo.getFlatNo().isEmpty() ? 
		        		csmrInfo.getFlatNo() + StringUtils.EMPTY : StringUtils.EMPTY);
		        address1.append(csmrInfo.getCsBldplt()!=null && !csmrInfo.getCsBldplt().isEmpty() ?
		        		csmrInfo.getCsBldplt() + StringUtils.EMPTY : StringUtils.EMPTY);
		        
		        StringBuilder address2 = new StringBuilder(csmrInfo.getCsLanear()!=null && !csmrInfo.getCsLanear().isEmpty() ?
		        		csmrInfo.getCsLanear() + StringUtils.EMPTY : StringUtils.EMPTY);		        
		        address2.append(csmrInfo.getCsRdcross()!=null && !csmrInfo.getCsRdcross().isEmpty() ?
		        		csmrInfo.getCsRdcross() + StringUtils.EMPTY : StringUtils.EMPTY);
		        provisionCertificateDTO.setAddress1(address1.toString());
		        provisionCertificateDTO.setAddress2(address2.toString());

		        provisionCertificateDTO.setPincode(csmrInfo.getCsCpinCode());
		        provisionCertificateDTO.setConNo(csmrInfo.getCsCcn());
		        provisionCertificateDTO.setConsumerHouseNumber(csmrInfo.getHouseNumber() != null && !csmrInfo.getHouseNumber().isEmpty() ? 
		        		csmrInfo.getHouseNumber() : StringUtils.EMPTY );
		        provisionCertificateDTO.setConsumerLandmark(csmrInfo.getLandmark() != null && !csmrInfo.getLandmark().isEmpty() ? 
		        		csmrInfo.getLandmark() : StringUtils.EMPTY );
		        provisionCertificateDTO.setWard(CommonMasterUtility.getHierarchicalLookUp(
                        Long.valueOf(csmrInfo.getCodDwzid2() != null ? csmrInfo.getCodDwzid2() : csmrInfo.getCodDwzid1()), UserSession.getCurrent().getOrganisation().getOrgid()).getDescLangFirst());
		        provisionCertificateDTO.setConsumerMobile(csmrInfo.getCsContactno() != null && !csmrInfo.getCsContactno().isEmpty() ? 
		        		csmrInfo.getCsContactno() : StringUtils.EMPTY);
		        provisionCertificateDTO.setConsumerEmail(csmrInfo.getCsEmail() != null && !csmrInfo.getCsEmail().isEmpty() ? 
		        		csmrInfo.getCsEmail() : StringUtils.EMPTY);
		        provisionCertificateDTO.setFatherHusbandName(csmrInfo.getFatherName());
		        
//		        provisionCertificateDTO.setTapTypeConnection(CommonMasterUtility
//	                    .getHierarchicalLookUp(csmrInfo.getCsCcncategory1(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
//		        provisionCertificateDTO.setTapUsage(CommonMasterUtility.getHierarchicalLookUp(
//                        Long.valueOf(csmrInfo.getTrmGroup1()), orgId).getDescLangFirst());
		        
		        
//				final List<LookUp> wardLookUps = CommonMasterUtility.getNextLevelData("WWZ", 2, UserSession.getCurrent().getOrganisation().getOrgid());
//				for (final LookUp lookUp : wardLookUps) {
//					if ((getModel().getCsmrInfo().getCodDwzid1() != null) && getModel().getCsmrInfo().getCodDwzid1() != 0l) {
//						if (lookUp.getLookUpId() ==getModel().getCsmrInfo().getCodDwzid1()) {
//							provisionCertificateDTO.setWard(lookUp.getDescLangFirst());
//							break;
//						}
//					}
//
//				}
				
				  
				final List<LookUp> categoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 1,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : categoryLookUps) {
					if ((getModel().getCsmrInfo().getCodDwzid1() != null) && getModel().getCsmrInfo().getCodDwzid1() != 0l) {
						if (lookUp.getLookUpId() == getModel().getCsmrInfo().getCsCcncategory1()) {
							provisionCertificateDTO.setTapTypeConnection(lookUp.getDescLangFirst());
							break;
						}
					}
				  
				 }
				
				
				
				final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 1,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : tarrifCategoryLookUps) {
					if ((getModel().getCsmrInfo().getTrmGroup1() != null) && getModel().getCsmrInfo().getTrmGroup1() != 0l) {
						if (lookUp.getLookUpId() == getModel().getCsmrInfo().getTrmGroup1()) {
							provisionCertificateDTO.setTapUsage(lookUp.getDescLangFirst());
							break;
						}
					}

				}
				
				
                try{
                	TbServiceReceiptMasEntity receiptDetailsByAppId = receiptEntryService.getReceiptDetailsByAppId(applicationNo, orgId);
                	if(receiptDetailsByAppId!=null) {
                		provisionCertificateDTO.setPaymentDate(receiptDetailsByAppId.getRmDate());
                		provisionCertificateDTO.setProvPaymentDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(receiptDetailsByAppId.getRmDate()));
                    	if(receiptDetailsByAppId.getReceiptModeDetail()!=null && !receiptDetailsByAppId.getReceiptModeDetail().isEmpty()) {
                    		Long cpdFeemode = receiptDetailsByAppId.getReceiptModeDetail().get(0).getCpdFeemode();       
                        	provisionCertificateDTO.setPaymentMode(cpdFeemode!=null ? CommonMasterUtility.getCPDDescription(cpdFeemode,
                    			PrefixConstants.D2KFUNCTION.ENGLISH_DESC): StringUtils.EMPTY);
                    	}
                	}else {
                		LOGGER.error("receipt obj : "+ receiptDetailsByAppId +" for application no "+ applicationNo);
                	}
                }catch(Exception ex) {
                	LOGGER.error("Payment receipt not found for app no "+ applicationNo + " " + ex.getMessage());
                }
			}else {
				LOGGER.error("csmr info " + csmrInfo);
			}
		}catch(Exception ex) {
			LOGGER.error("Exception occured fetching csmr info object for application no. "+applicationNo);
		}
		getModel().setProvisionCertificateDTO(provisionCertificateDTO);
		return provisionCertificateDTO;
	}
	@RequestMapping(params = "printWaterConCompltCert", method = { RequestMethod.POST })
	public ModelAndView printWaterCompletionCert(HttpServletRequest request,@RequestParam("appNo") Long appNo) {
		ProvisionalCertificateDTO provisionalCertificateDTO = new ProvisionalCertificateDTO();
		getModel().getResponseDTO().setApplicationNo(appNo);
		getProvisionalCertificateData(appNo, provisionalCertificateDTO);
		return new ModelAndView("newWaterConCOmpleteCert", MainetConstants.FORM_NAME, getModel());

	}
	
}
