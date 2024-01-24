package com.abm.mainet.additionalservices.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.TreeCutingTrimingSummaryDto;
import com.abm.mainet.additionalservices.dto.TreeCuttingInfoDto;
import com.abm.mainet.additionalservices.service.TreeCuttingPermissionService;
import com.abm.mainet.additionalservices.ui.model.TreeCuttingTrimmingPermissionModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("TreeCuttingTrimmingPermission.html")
public class TreeCuttingTrimmingPermissionController
		extends AbstractFormController<TreeCuttingTrimmingPermissionModel> {

	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMstService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	TreeCuttingPermissionService treeCuttingService;

	@Autowired
	private TbCfcApplicationAddressJpaRepository cfcAddressRepo;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		populateData();
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		return index();

	}

	@ResponseBody
	@RequestMapping(params = "summary", method = RequestMethod.POST)
	public ModelAndView resetSummaryForm(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		populateData();
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		return new ModelAndView("TreeCuttingTrimmingPermission/summary", MainetConstants.FORM_NAME, this.getModel());

	}

	private void populateData() {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Tree_Cutting_service,
				UserSession.getCurrent().getOrganisation().getOrgid());
		//#150170-Service names should be shown for related services in drop down field
		 List<TbServicesMst> serviceMstList = new ArrayList<>();
		final List<TbServicesMst> serviceMastList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		if (CollectionUtils.isNotEmpty(serviceMastList)) {
		for (TbServicesMst tbServicesMst : serviceMastList) {
     	   if (tbServicesMst.getSmShortdesc().equals(serviceMaster.getSmShortdesc()))
     		  serviceMstList.add(tbServicesMst);
          }
		}
		this.getModel().setTbServicesMsts(serviceMstList);

		this.getModel()
				.setRefIds(tbCFCApplicationMstService.getApplicationIdsByServiceId(serviceMaster.getSmServiceId()));
        //Defect #145348
		this.getModel().setCfcApplicationMst(new TbCfcApplicationMst());
		this.getModel().getCfcApplicationMst().setSmServiceId(serviceMaster.getSmServiceId());
	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest,Model model) {
		populateModel();

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Tree_Cutting_service,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());


		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		final int langId = UserSession.getCurrent().getLanguageId();
		this.getModel().setLangId(langId);

		return new ModelAndView("TreeCuttingTrimmingPermission/create", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public ModelAndView form(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		populateModel();

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Tree_Cutting_service,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());

		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		final int langId = UserSession.getCurrent().getLanguageId();
		this.getModel().setLangId(langId);
		return new ModelAndView("TreeCuttingTrimmingPermission/create", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "treeDetailEntryForm", method = RequestMethod.POST)
	public ModelAndView treeDetailEntryForm(final HttpServletRequest httpServletRequest) {
		this.bindModel(httpServletRequest);
		final int langId = UserSession.getCurrent().getLanguageId();
		this.getModel().setLangId(langId);
		TreeCuttingInfoDto dto = new TreeCuttingInfoDto();
		String appName = null;
		if(this.getModel().getCfcApplicationMst() != null){
		if (StringUtils.isNotEmpty(this.getModel().getCfcApplicationMst().getApmFname()) && StringUtils.isNotEmpty(this.getModel().getCfcApplicationMst().getApmMname()) &&  StringUtils.isNotEmpty(this.getModel().getCfcApplicationMst().getApmLname()))
			appName = this.getModel().getCfcApplicationMst().getApmFname() + MainetConstants.WHITE_SPACE + this.getModel().getCfcApplicationMst().getApmLname() + MainetConstants.WHITE_SPACE  + this.getModel().getCfcApplicationMst().getApmLname();
		else
			appName = this.getModel().getCfcApplicationMst().getApmFname() + MainetConstants.WHITE_SPACE  + this.getModel().getCfcApplicationMst().getApmLname();
		   dto.setAppName(appName);
		
		}
		if (this.getModel().getCfcApplicationAddressEntity() != null){
			dto.setMobNumber(Long.valueOf(this.getModel().getCfcApplicationAddressEntity().getApaMobilno()));
			dto.setAddress(this.getModel().getCfcApplicationAddressEntity().getApaCityName());
			dto.setEmailId(this.getModel().getCfcApplicationAddressEntity().getApaEmail());
		}
		this.getModel().setTreeCuttingInfoDto(dto);
		return new ModelAndView("TreeCuttingTrimmingPermission/treeDetailForm", MainetConstants.FORM_NAME,
				this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView searchData(final HttpServletRequest httpServletRequest, @RequestParam Long serviceId,
			@RequestParam String refId) {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		this.getModel().setFlag(MainetConstants.FlagN);
		// this.getModel().setAppIds(cfcNursingHomeService.findAllApplicationNo());

		this.getModel().setCutingTrimingSummaryDtos(
				treeCuttingService.getAllByServiceIdAndAppId(serviceId, refId, organisation.getOrgid()));
		if (this.getModel().getCutingTrimingSummaryDtos().isEmpty())
			this.getModel().setFlag(MainetConstants.FlagE);

		this.getModel().setRefId(refId);
		this.getModel().setServiceId(serviceId);
		return new ModelAndView("TreeCuttingTrimmingPermission/search", MainetConstants.FORM_NAME, this.getModel());

	}

	private void populateModel() {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		this.getModel().setTbServicesMsts(serviceMstList);
	}

	@ResponseBody
	@RequestMapping(params = "proceedToCheckList", method = RequestMethod.POST)
	public ModelAndView proceedToCheckList(final HttpServletRequest request) {
		this.bindModel(request);
		// this.getModel().getCheckListFromBrms();

		if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getCheckListFromBrms();
		}

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Tree_Cutting_service,
				UserSession.getCurrent().getOrganisation().getOrgid());

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {

			/*
			 * try { this.getModel().setTradeMasterDetailDTO(
			 * tradeLicenseApplicationService.getTradeLicenceAppChargesFromBrmsRule(masDto))
			 * ;
			 * 
			 * this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(
			 * MainetConstants.FlagY); } catch (FrameworkException e) { ModelAndView mv =
			 * defaultResult(); mv.addObject(BindingResult.MODEL_KEY_PREFIX +
			 * MainetConstants.FORM_NAME, getModel().getBindingResult());
			 * model.addValidationError(ApplicationSession.getInstance().getMessage(
			 * "renewal.brms.msg"));
			 * 
			 * return mv; }
			 * 
			 * this.getModel().getOfflineDTO()
			 * .setAmountToShow(Double.valueOf(this.getModel().getTradeMasterDetailDTO().
			 * getApplicationCharge())); this.getModel().getOfflineDTO()
			 * .setAmountToPay(this.getModel().getTradeMasterDetailDTO().
			 * getApplicationCharge());
			 */
		}
		this.getModel().setServiceMaster(serviceMaster);
		this.getModel().setPaymentCheck(MainetConstants.FlagY);

		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		this.getModel().setDownloadMode(MainetConstants.FlagM);
		this.getModel().setHideshowAddBtn(MainetConstants.FlagY);
		this.getModel().setHideshowDeleteBtn(MainetConstants.FlagY);
		this.getModel().setTemporaryDateHide(MainetConstants.FlagD);
		this.getModel().getdataOfUploadedImage();

		return new ModelAndView("TreeCuttingTrimmingPermission/checklist", MainetConstants.FORM_NAME, this.getModel());
		// return defaultResult();
	}

	@ResponseBody
	@RequestMapping(params = "generateChallanAndPayement", method = RequestMethod.POST)
	public Map<String, Object> generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		TreeCuttingTrimmingPermissionModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		List<DocumentDetailsVO> ownerDocs = model.getTreeCutingTrimingSummaryDto().getAttachments();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (ownerDocs != null) {
			ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
		}

		model.getTreeCutingTrimingSummaryDto().setDocumentList(docs);
		model.getTreeCutingTrimingSummaryDto().setAttachments(ownerDocs);
		fileUpload.validateUpload(model.getBindingResult());

		/*
		 * try { if (model.validateInputs()) { if (model.saveForm()) { return
		 * jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		 * 
		 * } else return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		 * } } catch (FrameworkException e) {
		 * 
		 * }
		 */
		String msg=new String();
		try {
			if (model.validateInputs()) {
				if (model.saveForm()) {
					object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());

				} else{
					object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
				}
			}
			else{
				object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
			}
		} catch (FrameworkException e) {

		}
		return object;
		/*
		 * ModelAndView mv = new ModelAndView("TreeCuttingTrimmingPermission/checklist",
		 * MainetConstants.FORM_NAME, this.getModel());
		 * mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
		 * 
		 * getModel().getBindingResult()); return mv;
		 */
	}

	@ResponseBody
	@RequestMapping(params = "viewTreeCuttingInfo", method = RequestMethod.POST)
	public ModelAndView viewTreeCuttingInfo(final HttpServletRequest request, @RequestParam String appId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		populateModel();
		Long applicationId = Long.parseLong(appId);
		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMstService.findById(applicationId);

		this.getModel().setCfcApplicationMst(cfcApplicationMst);
		CFCApplicationAddressEntity cfcApplicationAddressEntity = cfcAddressRepo.findOne(applicationId);
		this.getModel().setCfcApplicationAddressEntity(cfcApplicationAddressEntity);

		TreeCutingTrimingSummaryDto summaryDto = new TreeCutingTrimingSummaryDto();
		summaryDto.setCfcWard1(cfcApplicationAddressEntity.getApaZoneNo());
		summaryDto.setCfcWard2(cfcApplicationAddressEntity.getApaWardNo());
		this.getModel().setTreeCutingTrimingSummaryDto(summaryDto);

		this.getModel().setTreeCuttingInfoDto(treeCuttingService.getTreeCuttingInfo(applicationId));

		this.getModel().setDocumentList(checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setSaveMode(MainetConstants.FlagV);

		return new ModelAndView("TreeCuttingTrimmingPermission/view", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewTreeInfo", method = RequestMethod.POST)
	public ModelAndView viewTreeInfo(final HttpServletRequest request) {

		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		this.getModel().setSaveMode(MainetConstants.FlagV);

		return new ModelAndView("TreeCuttingTrimmingPermission/treeDetailForm", MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@RequestMapping(params = "printCFCAckRcpt", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		TreeCuttingTrimmingPermissionModel model = this.getModel();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Tree_Cutting_service, UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(this.getModel().getCheckList())) {
			List<CFCAttachment> downloadDocs = new ArrayList<>();
			List<CFCAttachment> preparePreviewOfFileUpload = model.preparePreviewOfFileUpload(downloadDocs,
					this.getModel().getCheckList());

			if (CollectionUtils.isNotEmpty(preparePreviewOfFileUpload)) {
				this.getModel().setDocumentList(preparePreviewOfFileUpload);
			}

		}

		String applicantName = model.getCfcApplicationMst().getApmFname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmMname() == null ? MainetConstants.BLANK
				: model.getCfcApplicationMst().getApmMname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmLname();
		this.getModel().getAckDto().setApplicationId(model.getCfcApplicationAddressEntity().getApmApplicationId());
		this.getModel().getAckDto().setApplicantName(applicantName);
		this.getModel().getAckDto().setServiceName(model.getServiceMaster().getSmServiceName());
		model.getAckDto().setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
		model.getAckDto().setAppDate(new Date());
		model.getAckDto().setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
		model.getAckDto().setDueDate(Utility.getAddedDateBy2(model.getAckDto().getAppDate(), serviceMas.getSmServiceDuration().intValue()));
		return new ModelAndView(MainetConstants.COMMON.COMMON_ACKNOWLEDGEMENT,
				MainetConstants.FORM_NAME, this.getModel());

	}
}
