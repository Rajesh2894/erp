package com.abm.mainet.tradeLicense.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.InspectionDetailService;
import com.abm.mainet.tradeLicense.ui.model.InspectionDetailFormModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.INSPECTION_DETAIL_FORM)
public class InspectionDetailFormController extends AbstractFormController<InspectionDetailFormModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private InspectionDetailService inspectionDetailService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		bindModel(request);
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LookUp trasitStatuslookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", organisation);

		this.getModel()
				.setTradeMasterDetailDTOList(tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId)
						.stream().filter(k -> k.getTrdStatus() == trasitStatuslookUp.getLookUpId())
						.collect(Collectors.toList()));
		return new ModelAndView(MainetConstants.TradeLicense.INSPECTION_DETAIL_ENTRY_FORM, MainetConstants.FORM_NAME,
				getModel());

	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_INSPECTION, method = RequestMethod.POST)
	public Map<String, Object> saveAndRedirectToShowCauseNotice(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().saveForm();
	
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put("messageText", this.getModel().getSuccessMessage());
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());

		object.put(MainetConstants.TradeLicense.LIC_NO, this.getModel().getInspectionDetailDto().getLicNo());
		object.put(MainetConstants.TradeLicense.INSP_NO, this.getModel().getInspectionDetailDto().getInspNo());
	

		return object;

	}

	@RequestMapping(params = "generateShowCauseNotice", method = RequestMethod.POST)
	public ModelAndView generateShowCauseNotice(@RequestParam(value = "licNo", required = false) String licNo,
			@RequestParam(value = "inspNo", required = false) String inspNo, final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getNoticeDetailDto().setLicNo(licNo);
		this.getModel().getNoticeDetailDto().setInspNo(inspNo);
		//D#129843 for setting owner details
		List<TradeLicenseOwnerDetailDTO> ownerDto = tradeLicenseApplicationService
				.getOwnerListByLicNoAndOrgId(this.getModel().getInspectionDetailDto().getLicNo(), orgId);
		if (!CollectionUtils.isEmpty(ownerDto)) {
			this.getModel().getInspectionDetailDto().setMobNo(ownerDto.get(0).getTroMobileno());
			this.getModel().getInspectionDetailDto().setApplicantName(ownerDto.get(0).getTroName());
			this.getModel().getInspectionDetailDto().setEmailId(ownerDto.get(0).getTroEmailid());
		}
		return new ModelAndView(MainetConstants.TradeLicense.SHOWCAUSE_NOTICE_FORM, MainetConstants.FORM_NAME,
				getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_NOTICE_DETAILS, method = RequestMethod.POST)
	public Map<String, Object> saveNoticeDetailsAndPrintLetter(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().saveNoticeDetails();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put("messageText", this.getModel().getSuccessMessage());
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(MainetConstants.TradeLicense.LIC_NO, this.getModel().getInspectionDetailDto().getLicNo());
		object.put(MainetConstants.TradeLicense.INSP_NO, this.getModel().getInspectionDetailDto().getInspNo());
		object.put(MainetConstants.TradeLicense.NOTICE_NO, this.getModel().getNoticeDetailDto().getNoticeNo());
		return object;
	}

	@RequestMapping(params = "printShowCauseNotice")
	public ModelAndView printShowCauseNotice(@RequestParam(value = "licNo", required = false) String licNo,
			@RequestParam(value = "inspNo", required = false) Long inspNo,
			@RequestParam(value = "noticeNo", required = false) Long noticeNo, final HttpServletRequest request) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<NoticeDetailDto> dtoList = inspectionDetailService.getNoticeDataById(licNo, noticeNo, orgId);
		this.getModel().setNoticeDetailDtoList(dtoList);
		NoticeDetailDto dto = inspectionDetailService.getDetailsToPrintLetter(licNo, inspNo, noticeNo, orgId);
		this.getModel().setNoticeDetailDto(dto);
		request.setAttribute("logo", UserSession.getCurrent().getOrgLogoPath());
		request.setAttribute("sign", UserSession.getCurrent().getEmployee().getScansignature());
		return new ModelAndView(MainetConstants.TradeLicense.SHOW_CAUSE_NOTICE, MainetConstants.FORM_NAME, getModel());
	}

}
