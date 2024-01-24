package com.abm.mainet.tradeLicense.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseModel;

@Controller
@RequestMapping("/CancellationLicense.html")
public class CancellationLicenseController extends AbstractFormController<CancellationLicenseModel> {

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@Autowired
	private ServiceMasterService serviceMasterService;
	@Autowired
	private TbCfcApplicationMstService cfcApplicationMstService;

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
	public ModelAndView viewApproval(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
			@RequestParam("workflowId") Long workflowId) throws ParseException {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final CancellationLicenseModel model = this.getModel();
		WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setTaskId(taskId);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("CTL", wfmass.getOrganisation().getOrgid());
		getModel().setServiceMaster(sm);
		List<TbLoiMas> tbLoiMas = new ArrayList<>();
		try {
			tbLoiMas = tbLoiMasService.getloiByApplicationId(applicationId, sm.getSmServiceId(),
					wfmass.getOrganisation().getOrgid());
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (CollectionUtils.isNotEmpty(tbLoiMas)) {
			model.setTbLoiMas(tbLoiMas);
			model.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
					tbLoiMas.get(0).getLoiNo());
			model.setRmRcptno(dto.get(0).getRmRcptno());
			model.setRmAmount(dto.get(0).getRmAmount());
			model.setPayMode(dto.get(0).getCpdDesc());
		}
		model.setTradeDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		//D#134032
		String ownerName = tradeLicenseApplicationService.getOwnersName(model.getTradeDetailDTO(),
				MainetConstants.FlagA);
		model.setOwnerName(ownerName);
		model.setViewMode(MainetConstants.FlagV);
		return new ModelAndView("CancelTradeLicenseApproval", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "cancelLicenseNumber", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		CancellationLicenseModel model = this.getModel();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
				UserSession.getCurrent().getOrganisation());

		model.getTradeDetailDTO().setTrdStatus(lookUp.getLookUpId());
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_LICENSE_PRINT)
	public ModelAndView getTradeCertificate(final HttpServletRequest request) {
		this.getModel().bind(request);

		final CancellationLicenseModel model = this.getModel();

		TbCfcApplicationMst entity = cfcApplicationMstService.findById(model.getTradeDetailDTO().getApmApplicationId());
		model.setCfcEntity(entity);

		return new ModelAndView("CancellationLicenseReport", MainetConstants.FORM_NAME, model);

	}

}
