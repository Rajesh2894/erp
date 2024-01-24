package com.abm.mainet.property.ui.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.service.IViewPropertyDetailsService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.PropertyBillPaymentModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping("/PropertyBillPayment.html")

public class PropertyBillPaymentController extends AbstractFormController<PropertyBillPaymentModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyBillPaymentController.class);
	
    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IViewPropertyDetailsService iViewPropertyDetailsService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        PropertyBillPaymentModel model = this.getModel();
        if(UserSession.getCurrent().getEmployee().getEmploginname().equals(MainetConstants.NOUSER)) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL")){
        		return new ModelAndView("PropertyBillPaymentSearchPageHome", MainetConstants.FORM_NAME, model);
        	}else {
        		return new ModelAndView("PropertyBillPaymentSearchHome", MainetConstants.FORM_NAME, model);
        	}
        }
        return new ModelAndView("PropertyBillPaymentSearch", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "getBillPaymentDetail", method = RequestMethod.POST)
    public ModelAndView getBillPaymentDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
        dto.setBillMethod(model.getBillingMethod());

        dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        dto.setOrgId(org.getOrgid());
        BillPaymentDetailDto billPayDto = selfAssessmentService.getPropertyPaymentDetails(dto,
                UserSession.getCurrent().getOrganisation());
        model.setBillPayDto(billPayDto);
        String lookUpCode = CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();
        ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (billPayDto == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.YES)) {
            return mv;
        }
        if(billPayDto != null && billPayDto.getAssmtDto() != null && StringUtils.equals(MainetConstants.FlagC, billPayDto.getAssmtDto().getAssActive())) {
        	getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.cancelledProperty"));
            return mv;
        }
        Boolean propertyActiveOrNot = selfAssessmentService.checkWhetherPropertyIsActive(billPayDto.getPropNo(),
                billPayDto.getOldpropno(), UserSession.getCurrent().getOrganisation().getOrgid());
        if (!propertyActiveOrNot) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.inactiveProperty"));
            return mv;
        }
		 
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL") && StringUtils.isNotBlank(billPayDto.getCurrentBillGenFlag()) && StringUtils.equals(billPayDto.getCurrentBillGenFlag(), MainetConstants.FlagN)) {
        	getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.currentbill.validation"));
			return mv;
        }
        model.getBillPayDto().setPartialAdvancePayStatus(lookUpCode);
        if(billPayDto.getAssmtDto()!=null && CollectionUtils.isNotEmpty(billPayDto.getAssmtDto().getProvisionalAssesmentDetailDtoList())
        		&& StringUtils.equals(MainetConstants.Property.INDIVIDUAL, getModel().getBillingMethod()))
        model.setOccupierName(billPayDto.getAssmtDto().getProvisionalAssesmentDetailDtoList().get(0).getOccupierName());
        return new ModelAndView("PropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "searchBillPay", method = RequestMethod.POST)
    public ModelAndView getBillPaymentDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "consumerNo") String consumerNo) {
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
        dto.setAssNo(consumerNo);
        BillPaymentDetailDto billPayDto = selfAssessmentService.getPropertyPaymentDetails(dto,
                UserSession.getCurrent().getOrganisation());
        model.setBillPayDto(billPayDto);
        String lookUpCode = CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (billPayDto != null) {
            model.getBillPayDto().setPartialAdvancePayStatus(lookUpCode);
        }
        ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (billPayDto == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.YES)) {
            return mv;
        }
        return new ModelAndView("PropertyBillPaymentHome", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params ="payment",method = RequestMethod.GET)
	public ModelAndView getPropertyDetails(final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam("propNo") final String propertyNumber) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " getPropertyDetails() method");
		sessionCleanup(request);
		getModel().bind(request);
		PropertyBillPaymentModel model = this.getModel();
		PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
		Organisation organisation = iOrganisationService
				.getActiveOrgByUlbShortCode(MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ_ULB);
		dto.setAssNo(propertyNumber);
		dto.setOrgId(organisation.getOrgid());
		BillPaymentDetailDto billPayDto = selfAssessmentService.getPropertyPaymentDetails(dto, organisation);
		BigDecimal amount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
		if (billPayDto != null && !billPayDto.getBillDisList().isEmpty()) {
			for (BillDisplayDto bdDto : billPayDto.getBillDisList()) {
				if (bdDto.getTaxCategoryId() != null) {
					final String taxCode = CommonMasterUtility
							.getHierarchicalLookUp(bdDto.getTaxCategoryId(), organisation).getLookUpCode();
					if (StringUtils.isNotBlank(taxCode)
							&& taxCode.equals(MainetConstants.Property.REBATE_fOR_PROPERTY)) {
						amount = amount.add(bdDto.getCurrentYearTaxAmt());
					}
				}
			}
		}
		model.setTotalRebate(Double.valueOf(amount.toString()));
		model.setBillPayDto(billPayDto);
		String lookUpCode = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.APP, organisation)
				.getLookUpCode();
		ModelAndView mv = new ModelAndView("PropertyBillPaymentSearch", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (billPayDto == null || billPayDto.getPropNo() == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.YES)) {
            return mv;
        }
        Boolean propertyActiveOrNot = selfAssessmentService.checkWhetherPropertyIsActive(billPayDto.getPropNo(),
                billPayDto.getOldpropno(), organisation.getOrgid());
        if (!propertyActiveOrNot) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.inactiveProperty"));
            return mv;
        }
        if (billPayDto.getErrorMsg() != null) {
			getModel().addValidationError(billPayDto.getErrorMsg());
			return mv;
		}
		model.getBillPayDto().setPartialAdvancePayStatus(lookUpCode);
		model.getPropBillPaymentDto().setAssOldpropno(billPayDto.getOldpropno());
		model.getPropBillPaymentDto().setAssNo(billPayDto.getPropNo());
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " getPropertyDetails() method");
		return new ModelAndView(MainetConstants.Property.BILL_PAYMENT_FOR_PROPERTY, MainetConstants.FORM_NAME, model);

	}

    @RequestMapping(params = "getBillPaymentDetailAscl", method = RequestMethod.POST)
    public ModelAndView getBillPayDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
        dto.setOrgId(org.getOrgid());
        Long childorgid = Utility.getOrgId();
        if (childorgid != null) {
            dto.setOrgId(childorgid);
        }
        dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        BillPaymentDetailDto billPayDto = selfAssessmentService.getPropertyPaymentDetails(dto,
                UserSession.getCurrent().getOrganisation());
        Organisation organisation = iOrganisationService.getOrganisationById(dto.getOrgId());
        BigDecimal amount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
        if (billPayDto != null && !billPayDto.getBillDisList().isEmpty()) {
            for (BillDisplayDto bdDto : billPayDto.getBillDisList()) {
                if (bdDto.getTaxCategoryId() != null) {
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(bdDto.getTaxCategoryId(), organisation)
                            .getLookUpCode();
                    if (StringUtils.isNotBlank(taxCode) && taxCode.equals(MainetConstants.Property.REBATE_fOR_PROPERTY)) {
                        amount = amount.add(bdDto.getCurrentYearTaxAmt());
                    }
                }
            }
        }

        model.setTotalRebate(Double.valueOf(amount.toString()));
        model.setBillPayDto(billPayDto);

        String lookUpCode = CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();

        ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (billPayDto == null || billPayDto.getPropNo() == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.YES)) {
            return mv;
        }
        Boolean propertyActiveOrNot = selfAssessmentService.checkWhetherPropertyIsActive(billPayDto.getPropNo(),
                billPayDto.getOldpropno(), Utility.getOrgId());
        if (!propertyActiveOrNot) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.inactiveProperty"));
            return mv;
        }
        if (billPayDto.getErrorMsg() != null) {
			getModel().addValidationError(billPayDto.getErrorMsg());
			return mv;
		}
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL") && StringUtils.isNotBlank(billPayDto.getCurrentBillGenFlag()) && StringUtils.equals(billPayDto.getCurrentBillGenFlag(), MainetConstants.FlagN)) {
        	getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.currentbill.validation"));
			return mv;
        }
        model.getBillPayDto().setPartialAdvancePayStatus(lookUpCode);
        model.getPropBillPaymentDto().setAssOldpropno(billPayDto.getOldpropno());
        model.getPropBillPaymentDto().setAssNo(billPayDto.getPropNo());
        return new ModelAndView("PropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "searchBillPaymentAscl", method = RequestMethod.POST)
    public ModelAndView getBillPayDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "consumerNo") String consumerNo) {
        sessionCleanup(httpServletRequest);
        return new ModelAndView("PropertyBillPaymentSearches", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "propertyDetailSearch", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView propertyDeatilSearch(final HttpServletRequest request) {
        getModel().bind(request);
        this.sessionCleanup(request);
        PropertyBillPaymentModel model = this.getModel();
        return new ModelAndView("PropertyBillPaymentDetailSearch", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public @ResponseBody List<ProperySearchDto> search(HttpServletRequest request) {
        PropertyBillPaymentModel model = this.getModel();
        model.bind(request);
        this.sessionCleanup(request);
        List<ProperySearchDto> result = null;
        ProperySearchDto dto = model.getSearchDto();
        model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Long childorgid = Utility.getOrgId();
        if (childorgid != null) {
            dto.setOrgId(childorgid);
        }

        if ((dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("property.viewPropDetails.formSearchValid"));
        }
        if ((dto.getOwnerName() != null && !dto.getOwnerName().isEmpty()) && dto.getOwnerName().length() < 3) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("property.viewPropDetails.owner.name.valid"));
        }
        if (!model.hasValidationErrors()) {
            result = iViewPropertyDetailsService.searchPropertyDetails(model.getSearchDto());
        }
        return result;
    }

    @RequestMapping(params = "getBillPaymentDetailFromView", method = RequestMethod.POST)
    public ModelAndView getBillPaymentDetailFromView(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("propNo") String PropNo) {
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
        dto.setAssNo(PropNo);
        dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        BillPaymentDetailDto billPayDto = selfAssessmentService.getPropertyPaymentDetails(dto,
                UserSession.getCurrent().getOrganisation());
        model.setBillPayDto(billPayDto);
        String lookUpCode = CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (billPayDto == null) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
            ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.YES)) {
            ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        model.getBillPayDto().setPartialAdvancePayStatus(lookUpCode);
        return new ModelAndView("PropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "propertyBackButton", method = RequestMethod.POST)
    public ModelAndView propertyBackButton(final HttpServletRequest request) {
        this.sessionCleanup(request);
        return new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
    }
    @ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
    	List<String> flatList=selfAssessmentService.fetchFlstList(propNo,UserSession.getCurrent().getOrganisation().getOrgid());
    	return flatList;
    }
    
    @RequestMapping(params = "propertyReceiptPrint", method = RequestMethod.POST)
    public ModelAndView propertyReceiptPrint(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        PropertyBillPaymentModel model = this.getModel();
        model.setReceiptDownloadValue(MainetConstants.FlagY);
        return new ModelAndView("PropertyBillReceiptHome", MainetConstants.FORM_NAME, model);

    }
    @RequestMapping(params = "generatePropertyReceipt", method = RequestMethod.POST)
	public @ResponseBody String generatePropertyReceipt(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
    		getModel().bind(httpServletRequest);
    		PropertyBillPaymentModel model = this.getModel();
			if(StringUtils.isEmpty(model.getPropBillPaymentDto().getFlatNo()) || StringUtils.equals(MainetConstants.FlagW, model.getBillingMethod())) {
				model.getPropBillPaymentDto().setFlatNo("X");
				}
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ReceiptReprintingReport_PropertyNoFlatNo.rptdesign&ULBName="
				+ UserSession.getCurrent().getOrganisation().getOrgid()+"&PropertyNo="+ model.getPropBillPaymentDto().getAssNo()
				+"&FlatNo=" +model.getPropBillPaymentDto().getFlatNo()+ "&ReceiptDate=" +model.getPropReceiptDate();
	}
    
    @RequestMapping(method = RequestMethod.POST, params = "redirectToPropertyPayment")
    public ModelAndView redirectToWaterPayment(final HttpServletRequest httpServletRequest)throws JsonParseException, JsonMappingException, IOException {
    	sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        return new ModelAndView("PropertyBillPaymentSearch", MainetConstants.FORM_NAME, this.getModel());
    }
    
    
}