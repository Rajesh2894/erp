package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CreditGuaranteeCGFMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.FPOAdministrativeDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.CreditGuaranteeRequestMasterService;
import com.abm.mainet.sfac.ui.model.CreditGrantEntryModel;
import com.abm.mainet.sfac.ui.model.EquityGrantRequestModel;

@RequestMapping(MainetConstants.Sfac.CGF_REQUEST_HTML)
@Controller
public class CreditGrantEntryRequestController extends  AbstractFormController<CreditGrantEntryModel> {
	
	@Autowired
	private IOrganisationService orgService;

	@Autowired CreditGuaranteeRequestMasterService creditGuaranteeRequestMasterService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IAttachDocsService attachDocsService;
	
	@RequestMapping(method = { RequestMethod.POST,RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.CGF_REQUEST_SUMMARY_FORM,MainetConstants.FORM_NAME,getModel());

	}

	private void populateModel() {
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
	
		FPOMasterDto fpoMasterDto = creditGuaranteeRequestMasterService.getFPODetails(UserSession.getCurrent().getEmployee().getMasId());
		CreditGuaranteeCGFMasterDto creditGuaranteeCGFMasterDto = new CreditGuaranteeCGFMasterDto();
	
		creditGuaranteeCGFMasterDto.setFpoMasterDto(fpoMasterDto);
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().setDto(creditGuaranteeCGFMasterDto);
	}
	
	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().setChecklistFlag(MainetConstants.FlagN);

		return new ModelAndView(MainetConstants.Sfac.CGF_REQUEST, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoManagementCostDetails(Long fpoId, String status ,final HttpServletRequest httpServletRequest) {
		List<CreditGuaranteeCGFMasterDto> DtoList = creditGuaranteeRequestMasterService.getAppliacationDetails(fpoId, status);
		this.getModel().setCreditGuaranteeCGFMasterDtos(DtoList);

		return new ModelAndView(MainetConstants.Sfac.CGF_REQUEST_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "getCheckList")
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		CreditGuaranteeCGFMasterDto masDto = this.getModel().getDto();
		CreditGrantEntryModel model = this.getModel();
		// Defect #120380

		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgId(orgid);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				"CGF",
				orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
		model.setServiceMaster(sm);


		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
				UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setChecklistFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setChecklistFlag(MainetConstants.FlagN);
		}

		if (model.getChecklistFlag().equals("Y")) {

			model.getCheckListFromBrms();
		}




		return new ModelAndView(MainetConstants.Sfac.CGF_REQUEST, MainetConstants.FORM_NAME, getModel());

	}
	
	@RequestMapping(params = "saveCreditGuaranteeForm", method = RequestMethod.POST)
	public ModelAndView saveEquityGrantRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		CreditGrantEntryModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		model.getDto().setDocumentList(docs);
		fileUpload.validateUpload(model.getBindingResult());



		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);


	}

}
