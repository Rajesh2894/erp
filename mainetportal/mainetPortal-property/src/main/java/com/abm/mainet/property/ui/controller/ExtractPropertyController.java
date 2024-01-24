
package com.abm.mainet.property.ui.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.abm.mainet.property.ui.model.PropertyNoDuesCertificateModel;

/**
 * @author cherupelli.srikanth
 * @since 25 January 2021
 */

@Controller
@RequestMapping("/ExtractProperty.html")
public class ExtractPropertyController extends AbstractFormController<PropertyNoDuesCertificateModel> {

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;
	
	@Autowired
	ICommonBRMSService iCommonBRMSService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		setCommonFeilds();
		getModel().getNoDuesCertificateDto().setServiceUrl("ExtractProperty.html");		
		getModel().setCommonHelpDocs("ExtractProperty.html");
		return defaultResult();
	}

	private void setCommonFeilds() {
		PropertyNoDuesCertificateModel model = this.getModel();
		getModel().setNoDuesCertificateDto(new NoDuesCertificateDto());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setOrgId(orgId);
		NoDuesCertificateDto noDueDto = getModel().getNoDuesCertificateDto();
		noDueDto.setOrgId(orgId);
		noDueDto.setSmShortCode("EXT");
		noDueDto = propertyNoDuesCertificate.getPropertyServiceData(noDueDto);
		model.setServiceId(noDueDto.getSmServiceId());
		model.setServiceShrtCode(noDueDto.getSmShortCode());
		model.setDeptId(noDueDto.getDeptId());
		model.setServiceName(noDueDto.getServiceName());
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)){
			model.getNoDuesCertificateDto().setNoOfCopies(1l);
		}
		
		getModel().setAllowToGenerate(MainetConstants.FlagY);
		if (StringUtils
				.equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(noDueDto.getSmChklstVerify(),
						UserSession.getCurrent().getOrganisation()).getLookUpCode(), MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (noDueDto.getSmFeesSchedule().equals(1l)) {
			model.setAppliChargeFlag(noDueDto.getAppliChargeFlag());
			model.setFree(false);
		} else {
			model.setAppliChargeFlag(MainetConstants.FlagN);
			model.setFree(true);
		}
		if ((noDueDto.getSmScrutinyApplicableFlag().equals(MainetConstants.FlagN)
				&& noDueDto.getSmProcessLookUpCode().equals(MainetConstants.NewWaterServiceConstants.NA))
				|| (noDueDto.getSmProcessLookUpCode().equals(MainetConstants.NewWaterServiceConstants.NA))) {
			model.setScrutinyAppliFlag(MainetConstants.FlagN);
		} else {
			model.setScrutinyAppliFlag(MainetConstants.FlagY);
		}

		if (noDueDto.getFinancialYearMap() != null) {
			
			Map<Long, String> sorted = noDueDto.getFinancialYearMap().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

			this.getModel().setFinancialYearMap(sorted);
		}
			

		LookUp outstandingCheckPrefix = CommonMasterUtility.getValueFromPrefixLookUp("EXT", "OSC",UserSession.getCurrent().getOrganisation());
		if (outstandingCheckPrefix != null)
			if (MainetConstants.FlagY.equals(outstandingCheckPrefix.getOtherField())) {
				model.setNeedToCheckOutstanding(true);
			}
	}

	@RequestMapping(params = MainetConstants.Property.GET_PROPERTY_DETAILS, method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo) {
		this.getModel().bind(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = getModel().getNoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setPropNo(propNo);
		noDuesDto.setOldpropno(oldPropNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getPropertyDetailsByPropertyNumber(noDuesDto);
		if (dto != null && dto.getSuccessFlag() != null && dto.getSuccessFlag().equals(MainetConstants.FlagY)) {
			getModel().setNoDuesCertificateDto(dto);
			if (dto.getTotalOutsatandingAmt() <= 0) {
				getModel().setAllowToGenerate(MainetConstants.FlagY);
				return defaultMyResult();
			} else {
				this.getModel().addValidationError("property.noDues.pendingDuesValid" + noDuesDto.getPropNo());
			}
		} else {
			this.getModel().addValidationError("property.noDues.propertySearchValid");
		}

		ModelAndView mv = new ModelAndView("ExtractPropertyValidn", MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	/**
	 * used to fetch checklist and charges if enable
	 * 
	 * @param httpServletRequest
	 * 
	 */
	@RequestMapping(params = MainetConstants.Property.GET_CHECKLIST_AND_CHANGES, method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = getModel().getNoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setSmServiceId(model.getServiceId());
		noDuesDto.setDeptId(model.getDeptId());
		if(StringUtils.isNotBlank(noDuesDto.getPropertyDetails().get(0).getFlatNo())) {
			model.setFlatNumber(noDuesDto.getPropertyDetails().get(0).getFlatNo());
		}

		if (!model.checkChequeClearanceStatus()
				|| (model.isNeedToCheckOutstanding() && !model.checkOutstandingStatus())) {
			return defaultMyResult();
		}
		if (model.getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			List<DocumentDetailsVO> docs = propertyNoDuesCertificate.fetchCheckList(noDuesDto);
			if (CollectionUtils.isNotEmpty(docs)) {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
			} else {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
				this.getModel().addValidationError("No Checklist found. Please configure rule sheet");
				ModelAndView mv = new ModelAndView("ExtractPropertyValidn", MainetConstants.FORM_NAME,
						this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
			model.setCheckList(docs);
		}
		if (model.getAppliChargeFlag().equals(MainetConstants.FlagY)) {
			NoDuesCertificateDto dto = propertyNoDuesCertificate.fetchChargesForNoDues(noDuesDto);
			model.setNoDuesCertificateDto(dto);
			if (CollectionUtils.isNotEmpty(dto.getCharges())) {
				this.getModel().setAppliChargeFlag(MainetConstants.FlagN);
				this.getModel().getOfflineDTO().setAmountToPay(String.valueOf(dto.getTotalPaybleAmt()));
				this.getModel().getOfflineDTO().setAmountToShow(dto.getTotalPaybleAmt());
				this.getModel().getOfflineDTO().getFeeIds().put(dto.getCharges().get(0).getTaxId(), dto.getTotalPaybleAmt());
			} else {
				this.getModel().setAppliChargeFlag(MainetConstants.FlagY);
				this.getModel().addValidationError("No charges found. Please configure rule sheet");
				ModelAndView mv = new ModelAndView("ExtractPropertyValidn", MainetConstants.FORM_NAME,
						this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}
		return defaultMyResult();
	}

	/**
	 * used to generate certificate
	 */
	@RequestMapping(params = MainetConstants.Property.GENERATE_NO_DUES_CERTI, method = RequestMethod.POST)
	public ModelAndView getNoDuesCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();

		if ((!model.isWorkflowDefined() || model.isLastApproval()) && (null != model.getNoDuesCertificateDto()
				&& !model.getNoDuesCertificateDto().getPropertyDetails().isEmpty())) {
			NoDuesPropertyDetailsDto dto = model.getNoDuesCertificateDto().getPropertyDetails().get(0);
			if(StringUtils.isEmpty(dto.getFlatNo()) || StringUtils.equals(MainetConstants.Property.WHOLE, model.getBillMethod())) {
				dto.setFlatNo("X");
			}
			getModel().setFilePath(ServiceEndpoints.PROPERTY_URL.PROPERTY_BIRT_REPORT_URL
					+ "=RP_ExtractionOfAssessment.rptdesign&RP_ORGID=" + dto.getOrgId()
					+ "&RP_Fyn_Year=" + dto.getFinacialYearId()
					+ "&RP_PropNo=" + dto.getPropNo()
					+ "&RP_FlatNo=" + dto.getFlatNo());
			getModel().setRedirectURL("CitizenHome.html");
			return new ModelAndView("generatePDFNextTab", MainetConstants.FORM_NAME, model);
		} else {
			return new ModelAndView("redirect:/CitizenHome.html");
		}

	}

	@RequestMapping(params = "getPropertyDetailsByFlatNo", method = RequestMethod.POST)
	public @ResponseBody NoDuesCertificateDto getPropertyDetailsByFlatNo(@RequestParam("propNo") String propNo,
			@RequestParam("flatNo") String flatNo, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		PropertyNoDuesCertificateModel model = this.getModel();
		NoDuesCertificateDto noDuesDto = new NoDuesCertificateDto();
		noDuesDto.setOrgId(model.getOrgId());
		noDuesDto.setPropNo(propNo);
		noDuesDto.setFlatNo(flatNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getPropertyDetailsByPropertyNumberNFlatNo(noDuesDto);
		return dto;

	}

	@RequestMapping(params = "deletePropertyNo", method = RequestMethod.POST)
	public @ResponseBody void deletedEWC(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "deletedRow") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		NoDuesPropertyDetailsDto detDto = this.getModel().getNoDuesCertificateDto().getPropertyDetails()
				.get(deletedRowCount);
		if (detDto != null) {
			detDto.setIsDeleted(MainetConstants.FlagY);
		}
	}

	@RequestMapping(params = "backToForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView backToNodues(HttpServletRequest request) {
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(request);
		return defaultResult();
	}

	@RequestMapping(params = "generateCertificate", method = RequestMethod.POST)
	public @ResponseBody String getCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		if (MainetConstants.FlagN.equals(model.getScrutinyAppliFlag())) {
			NoDuesPropertyDetailsDto dto = model.getNoDuesCertificateDto().getPropertyDetails().get(0);
			if(StringUtils.isEmpty(dto.getFlatNo()) || StringUtils.equals(MainetConstants.Property.WHOLE, model.getBillMethod())) {
				dto.setFlatNo("X");
			}
			return ServiceEndpoints.PROPERTY_URL.PROPERTY_BIRT_REPORT_URL
					+ "=RP_ExtractionOfAssessment.rptdesign&RP_ORGID=" + dto.getOrgId()
					+ "&RP_Fyn_Year=" + dto.getFinacialYearId()
					+ "&RP_PropNo=" + dto.getPropNo()
					+ "&RP_FlatNo=" + dto.getFlatNo();
		} else {
			return "CitizenHome.html";
		}
	}

	@RequestMapping(params = "getAcknowledement", method = RequestMethod.POST)
	public ModelAndView getTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		PropertyNoDuesCertificateModel model = this.getModel();
		Date date = new Date();
		model.setDate(Utility.dateToString(date, MainetConstants.DATEFORMAT_DDMMYYYY));
		String currentYear = Utility.getFinancialYearFromDate(date);
		getModel().setFinYear(currentYear);
		return new ModelAndView("propertyAcknowledgement", MainetConstants.FORM_NAME, model);

	}
	
	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public Map<String, List<String>> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo,
			HttpServletRequest request) {
		this.getModel().bind(request);
		Map<String, List<String>> resultMap = new HashMap<>();
		PropertyNoDuesCertificateModel model = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		String billingMethod =  propertyNoDuesCertificate.getPropertyBillingMethod(propNo, org.getOrgid());		
		List<String> flatNoList = propertyNoDuesCertificate.getPropertyFlatList(propNo, org.getOrgid());
		
		if (StringUtils.isEmpty(billingMethod)) {
			resultMap.put("NULL", flatNoList);
		} else {
			model.setBillMethod(billingMethod);
			resultMap.put(billingMethod, flatNoList);
		}

		return resultMap;
	}
	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showDetails(@RequestParam("appId") final String appNo, @RequestParam("serviceCode") final String serviceCode,
			@RequestParam("appStatus") final String appStatus, final HttpServletRequest httpServletRequest)
			throws Exception {
        this.sessionCleanup(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        setCommonFeilds();
		model.setOrgId(orgId);
		getModel().getNoDuesCertificateDto().setOrgId(orgId);
		getModel().getNoDuesCertificateDto().setApplicationNo(appNo);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getNoDuesDetails(getModel().getNoDuesCertificateDto());
		model.setNoDuesCertificateDto(dto);
		model.setApplicantDetailDto(dto.getApplicantDto());
		model.setAllowToGenerate(MainetConstants.FLAGY);
		model.setAuthFlag(MainetConstants.FlagY);
		//model.setApmApplicationId(dto.getApmApplicationId());
		model.setPayableFlag(MainetConstants.FlagN);
		model.setCheckList(dto.getDocs());
		
		iCommonBRMSService.getChecklistDocument(dto.getApmApplicationId().toString(), UserSession.getCurrent().getOrganisation().getOrgid(), "N");




		
		return customDefaultMyResult("viewPropertyService");
    }

}
