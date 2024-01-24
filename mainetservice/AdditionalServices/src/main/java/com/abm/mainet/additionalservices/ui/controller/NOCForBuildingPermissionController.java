package com.abm.mainet.additionalservices.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.service.NOCForBuildingPermissionService;
import com.abm.mainet.additionalservices.ui.model.NOCForBuildingPermissionModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = { "/NOCForBuildingPermission.html", NOCForBuildPermissionConstant.NOC_PRINTING })
public class NOCForBuildingPermissionController extends AbstractFormController<NOCForBuildingPermissionModel> {

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private IAttachDocsService attachDocsService;
	private List<TbDepartment> deptList = Collections.emptyList();
	private List<TbServicesMst> serviceMstList = Collections.emptyList();

	@Resource
	private NOCForBuildingPermissionService nOCForBuildingPermissionService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		String formName = "";
		if (httpServletRequest.getServletPath().equalsIgnoreCase(NOCForBuildPermissionConstant.NOC_PRINTING)) {
			this.sessionCleanup(httpServletRequest);
			formName = "NOCPrinting";
		} else {
			List<NOCForBuildingPermissionDTO> nocBuildList = nOCForBuildingPermissionService.getAllData();
			model.addAttribute("data", nocBuildList);
			formName = "NOCForBuildingPermissionSummary";
			/*
			 * int count = 0; int n = 0; List<AttachDocs> attachsList = new ArrayList<>();
			 * List<AttachDocs> att = new ArrayList<>(); for (int i = 0; i <=
			 * nocBuildList.size() - 1; i++) { if(nocBuildList.get(i).getStatus()!= null &&
			 * nocBuildList.get(i).getStatus().equals(MainetConstants.FlagA)) { att =
			 * (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().
			 * getOrgid(), nocBuildList.get(i).getApmApplicationId().toString())); if
			 * (!att.isEmpty()) { attachsList.addAll(att); } } }
			 * this.getModel().setAttachDocsList(attachsList);
			 */
		}
		return new ModelAndView(formName, MainetConstants.FORM_NAME, getModel());
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(Model model, final HttpServletRequest request, final HttpServletResponse response) {
		this.sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		final int langId = UserSession.getCurrent().getLanguageId();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		deptList = tbDepartmentService.findByOrgId(org.getOrgid(), Long.valueOf(langId)); //
		model.addAttribute("deptList", deptList);
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		model.addAttribute("locations", loadLocation());
		this.getModel().setDeptList(deptList);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(NOCForBuildPermissionConstant.NBP,
				org.getOrgid());
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());

		if (lookup.getLookUpCode().equals(NOCForBuildPermissionConstant.CHECKLISTAPPLICABLE)) {
			 getCheckListAndCharges(this.getModel(), request, response);
		}
		return new ModelAndView("NOCForBuildingPermissionValidn", MainetConstants.FORM_NAME, getModel());

	}

	/**
	 * @return Location List
	 */
	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}

	@RequestMapping(params = "refreshServiceData", method = RequestMethod.POST)
	public @ResponseBody ModelAndView refreshServiceData(final Model model, HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final List<TbServicesMst> serviceMstList = tbServicesMstService
				.findByDeptId(this.getModel().getNocBuildingPermissionDto().getDeptId(), org.getOrgid());
		this.getModel().setServiceMstList(serviceMstList);
		model.addAttribute("locations", loadLocation());
		return new ModelAndView("NOCForBuildingPermissionValidn", MainetConstants.FORM_NAME, this.getModel());
		// return serviceMstList;
	}

	@RequestMapping(params = "printNOCAck", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		NOCForBuildingPermissionModel model = this.getModel();
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
				Long.valueOf(model.getNocBuildingPermissionDto().getApplicationId()),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setCfcEntity(cfcEntity);
		model.setApplicationId(Long.valueOf(model.getNocBuildingPermissionDto().getApplicationId()));
		String applicantName = String.join(" ", Arrays.asList(model.getCfcEntity().getApmFname(),
				model.getCfcEntity().getApmMname(), model.getCfcEntity().getApmLname()));

		model.setApplicantName(applicantName);
		NOCForBuildingPermissionDTO dto = model.getNocBuildingPermissionDto();
		LookUp title = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(dto.getTitleId()),
				new Organisation(dto.getOrgId()));
		dto.setApplicantEmail(title.getDescLangFirst());
		// model.setServiceName(NOCForBuildPermissionConstant.NOC_FOR_BUILDING_PERMISSION);
		return new ModelAndView("nocforbuildingpermissionAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView searchBirthDraft(@RequestParam(value = "apmApplicationId", required = false) final Long apmApplicationId,
			@RequestParam("fromDate") final Date fromDate, @RequestParam("toDate") final Date toDate,@RequestParam(value = "refNo", required = false) final String refNo,
			final HttpServletRequest request, final Model model) {
		getModel().bind(request);
		this.getModel().getNocBuildingPermissionDto().setApmApplicationId(apmApplicationId);
		this.getModel().getNocBuildingPermissionDto().setFromDate(fromDate);
		this.getModel().getNocBuildingPermissionDto().setToDate(toDate);
		this.getModel().getNocBuildingPermissionDto().setRefNo(refNo);
		
		ModelAndView mv = new ModelAndView("NOCForBuildingPermissionSummaryValidn", MainetConstants.FORM_NAME,
				this.getModel());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<NOCForBuildingPermissionDTO> nocBuildList = nOCForBuildingPermissionService
				.getAppliDetail(apmApplicationId, fromDate, toDate, orgId,refNo);
		List<AttachDocs> attachsList = new ArrayList<>();
		List<AttachDocs> att = new ArrayList<>();
		for (int i = 0; i <= nocBuildList.size() - 1; i++) {
			if (nocBuildList.get(i).getStatus() != null
					&& nocBuildList.get(i).getStatus().equals(MainetConstants.FlagA)) {
				att = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						nocBuildList.get(i).getApmApplicationId().toString()));
				if (!att.isEmpty()) {
					attachsList.addAll(att);
				}
			}
		}
		this.getModel().setAttachDocsList(attachsList);

		request.setAttribute("data", nocBuildList);

		return mv;

	}

	@RequestMapping(params = "edit/View", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model, @RequestParam("mode") String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long bpID, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(mode);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final int langId = UserSession.getCurrent().getLanguageId();
		this.getModel().setNocBuildingPermissionDto(nOCForBuildingPermissionService.getDataById(bpID));
		deptList = tbDepartmentService.findByOrgId(orgId, Long.valueOf(langId));
		serviceMstList = tbServicesMstService.findAllServiceListByOrgId(orgId);
		model.addAttribute("locations", loadLocation());
		this.getModel().getIsDMS();
		this.getModel().setDeptList(deptList);
		this.getModel().setServiceMstList(serviceMstList);
		// fetch uploaded document
		List<CFCAttachment> checklist = new ArrayList<>();
		checklist = iChecklistVerificationService.findAttachmentsForAppId(
				this.getModel().getNocBuildingPermissionDto().getApmApplicationId(), null, orgId);
		this.getModel().setDocumentList(checklist);

		LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS", "MLI",
				UserSession.getCurrent().getOrganisation());
		if (gisFlag != null) {
			getModel().setGisValue(gisFlag.getOtherField());
			String GISURL = ServiceEndpoints.GisItegration.GIS_URI
					+ ServiceEndpoints.GisItegration.BUIDING_PERMISSION_LAYER_NAME;
			if (GISURL != null && gisFlag.getOtherField().equals(MainetConstants.Common_Constant.YES)) {
				getModel().setgISUri(GISURL);
			}
		}

		return new ModelAndView("NOCForBuildingPermissionValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@SuppressWarnings("unchecked")
	private void getCheckListAndCharges(NOCForBuildingPermissionModel nocModel,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		nocModel.setCommonHelpDocs("NOCForBuildingPermission.html");

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(NOCForBuildPermissionConstant.ChecklistModel);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);

			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(NOCForBuildPermissionConstant.NBP);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(NOCForBuildPermissionConstant.ChecklistModel);
			checklistReqDto.setDataModel(checkListModel);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

					if ((checkListList != null) && !checkListList.isEmpty()) {
						nocModel.setCheckList(checkListList);

					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
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

	@RequestMapping(params = "generateNOC", method = RequestMethod.POST)
	public @ResponseBody String generateReport(
			@RequestParam(name = "apmApplicationId", required = false) String apmApplicationId,
			@RequestParam(name = "loiNo", required = false) String loiNo, final HttpServletRequest httpServletRequest) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbLoiMas tbLoiMas = null;
		if (null != apmApplicationId && StringUtils.isNotEmpty(apmApplicationId)) {
			NOCForBuildingPermissionDTO dto = nOCForBuildingPermissionService.findByRefNo(apmApplicationId);
			tbLoiMas = tbLoiMasService.findloiByApplicationIdAndOrgId(dto.getApmApplicationId(), orgId);
		} else {
			tbLoiMas = tbLoiMasService.findLoibyLoiNumber(loiNo, orgId);
		}
		if (tbLoiMas == null) {
			return "NOTFOUND";
		} else if ("Y".equals(tbLoiMas.getLoiPaid()))
			return ServiceEndpoints.NOC_BIRT_REPORT_URL + "BuildingPermissionNOC.rptdesign&OrgId=" + orgId + "&AppId="
					+ tbLoiMas.getLoiApplicationId();
		else
			return "PENDING";
	}

}
