package com.abm.mainet.bnd.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.PrintBNDCertificateService;
import com.abm.mainet.bnd.ui.model.PrintCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/PrintCertificate.html")
public class PrintCertificateController extends AbstractFormController<PrintCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(PrintCertificateController.class);

	@Autowired
	private PrintBNDCertificateService printBNDCertificateService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("PrintCertificate.html");
		return new ModelAndView("PrintCertificate", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "printbirthAndDeathCert", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<TbBdCertCopyDTO> SearchDeathCorrectionData(@RequestParam("applnId") final Long applnId,
			final HttpServletRequest request, final Model model) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TbBdCertCopyDTO> list = printBNDCertificateService.getPrintCertificateDetails(applnId, orgId);
		if (!list.isEmpty()) {
			getModel().setTbBdCertCopyDTO(list.get(0));
			this.getModel().setServiceCode(getModel().getTbBdCertCopyDTO().getServiceCode());
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "getCertificateData", method = RequestMethod.POST, produces = "Application/JSON")
	public ModelAndView getCertificateData(@RequestParam("ApplnId") final Long ApplnId,
			@RequestParam("certiNo") final String certiNo, @RequestParam("status") final String status,
			@RequestParam("bdId") final Long bdId, HttpServletRequest request, final Model model) {
		PrintCertificateModel certificateModel = this.getModel();
		String serviceCode = this.getModel().getServiceCode();

		model.addAttribute("status", status);
		model.addAttribute("bdId", bdId);
		if (serviceCode.equals(BndConstants.IBC) || serviceCode.equals(BndConstants.BRC)
				|| serviceCode.equals(BndConstants.INC)) {
			BirthRegistrationDTO birthRgDetail = printBNDCertificateService.getBirthRegisteredAppliDetail(certiNo, null,
					null, ApplnId.toString(), UserSession.getCurrent().getOrganisation().getOrgid());
			certificateModel.setBirthRegDto(birthRgDetail);
			if (birthRgDetail.getAuthRemark() != null
					&& birthRgDetail.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
				certificateModel.setRemark(birthRgDetail.getAuthRemark());
			else
				certificateModel.setRemarkReg(birthRgDetail.getAuthRemark());
			birthRgDetail.setBrCertNo(String.valueOf(certiNo));
			model.addAttribute("brOrDrID", birthRgDetail.getBrId());
			if (serviceCode.equals(BndConstants.IBC)) {
				model.addAttribute("serviceCode", BndConstants.IBC);
			} else {
				model.addAttribute("serviceCode", BndConstants.BRC);
			}
			return new ModelAndView("PrintCertificateValidnforBirth", MainetConstants.FORM_NAME, getModel());
		} else if (serviceCode.equals(BndConstants.IDC)) {
			TbDeathregDTO certificateDetailList = printBNDCertificateService.getDeathRegisteredAppliDetail(null, null,
					null, ApplnId.toString(), UserSession.getCurrent().getOrganisation().getOrgid());
			certificateDetailList.setDrCertNo(String.valueOf(certiNo));
			certificateModel.setTbDeathregDTO(certificateDetailList);
			if (certificateDetailList.getAuthRemark() != null
					&& certificateDetailList.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
				certificateModel.setRemark(certificateDetailList.getAuthRemark());
			else
				certificateModel.setRemarkReg(certificateDetailList.getAuthRemark());
			model.addAttribute("brOrDrID", certificateDetailList.getDrId());
			model.addAttribute("serviceCode", BndConstants.IDC);
			return new ModelAndView("PrintCertificateValidnforDeath", MainetConstants.FORM_NAME, getModel());
		} else {
			TbDeathregDTO certificateDetailList = printBNDCertificateService.getDeathRegisteredAppliDetail(null, null,
					null, ApplnId.toString(), UserSession.getCurrent().getOrganisation().getOrgid());
			certificateDetailList.setDrCertNo(String.valueOf(certiNo));
			if (null != certificateDetailList.getCpdRegUnit()) {
				LookUp lookup = CommonMasterUtility
						.getNonHierarchicalLookUpObject(certificateDetailList.getCpdRegUnit());
				certificateDetailList.setPdRegUnitCode(lookup.getLookUpCode());
			}
			certificateModel.setTbDeathregDTO(certificateDetailList);
			if (certificateDetailList.getAuthRemark() != null
					&& certificateDetailList.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
				certificateModel.setRemark(certificateDetailList.getAuthRemark());
			else
				certificateModel.setRemarkReg(certificateDetailList.getAuthRemark());
			model.addAttribute("brOrDrID", certificateDetailList.getDrId());
			model.addAttribute("serviceCode", BndConstants.DRC);
			return new ModelAndView("PrintCertificateValidnforDeath", MainetConstants.FORM_NAME, getModel());

		}
	}

	@RequestMapping(params = "updatePrintStatusAfterPrint", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody Boolean updatePrintStatusAfterPrint(@RequestParam("certificateNo") final String certificateNo,
			@RequestParam("brOrdrID") final Long brOrdrID, @RequestParam("serviceCode") final String serviceCode,
			@RequestParam("bdId") final Long bdId, final HttpServletRequest request, final Model model) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		printBNDCertificateService.updatPrintStatus(brOrdrID, orgId, bdId);
		return true;
	}

}
