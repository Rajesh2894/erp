package com.abm.mainet.property.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.EditNameAddressModel;

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

@Controller
@RequestMapping("/EditNameAddress.html")
public class EditNameAddressController extends AbstractFormController<EditNameAddressModel>{

	private static final Logger LOGGER = Logger.getLogger(EditNameAddressController.class);
	
	@Autowired
    private SelfAssessmentService assessmentService;
	
	@Autowired
    private MutationService mutationService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().bind(request);
        getModel().setCommonHelpDocs("EditNameAddress.html");
        return new ModelAndView("EditNameAddress", MainetConstants.FORM_NAME, this.getModel());
    }
	
	@RequestMapping(params = "getEditNameAddressDetails", method = RequestMethod.POST)
    public ModelAndView getEditNameAddressDetails(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " getEditNameAddressDetails method");
		this.sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().bind(request);
        //EditNameAddressModel model = this.getModel();
        ProvisionalAssesmentMstDto mstDto = this.getModel().getProvisionalAssesmentMstDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        mstDto.setOrgId(orgId);
        if (StringUtils.isEmpty(mstDto.getAssNo())) {
            getModel().addValidationError(getApplicationSession().getMessage("update.enter.propNo.oldPropNo"));
            ModelAndView mv = new ModelAndView("EditNameAddressValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        ProvisionalAssesmentMstDto assesmentMstDto = null;
        mstDto.setBillingMethod(this.getModel().getBillingMethod());
        
        LOGGER.info("before getPropertyEdidNameAddress method call");
        assesmentMstDto = assessmentService.getPropertyEdidNameAddress(mstDto);
        LOGGER.info("after getPropertyEdidNameAddress method call");
        
		if (assesmentMstDto != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.STATUS.INACTIVE, assesmentMstDto.getAssActive())) {
			getModel().addValidationError(getApplicationSession().getMessage("property.inactiveProperty"));
			ModelAndView mv = new ModelAndView("EditNameAddressValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
		}	
		if (assesmentMstDto != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.STATUS.CANCEL, assesmentMstDto.getAssActive())) {
			getModel().addValidationError(getApplicationSession().getMessage("property.cancelledProperty"));
			ModelAndView mv = new ModelAndView("EditNameAddressValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}

        if (assesmentMstDto == null) {
            getModel().addValidationError(getApplicationSession().getMessage("property.enter.valid.propNo"));
            ModelAndView mv = new ModelAndView("EditNameAddressValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        if(!assesmentMstDto.getDocs().isEmpty()) {
        	this.getModel().setCheckList(assesmentMstDto.getDocs());
        }
        this.getModel().setDeptId(assesmentMstDto.getDeptId());
        this.getModel().setServiceId(assesmentMstDto.getSmServiceId());
        this.getModel().setFinYearList(Arrays.asList(assesmentMstDto.getFaYearId()));
        
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipTypeValue(lookup.getDescLangFirst());
        assesmentMstDto.setProAssOwnerTypeName(lookup.getDescLangFirst());
        assesmentMstDto.setRemarks(MainetConstants.BLANK);
        assesmentMstDto.setEditNameAddressFlag(getModel().getEditType());
        this.getModel().setProvisionalAssesmentMstDto(assesmentMstDto);
        this.getModel().setFlatNo(mstDto.getFlatNo());
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " getEditNameAddressDetails method");
        return new ModelAndView("EditNameAddressFormValidn", MainetConstants.FORM_NAME, this.getModel());
    }
	
	@Override
	@RequestMapping(params = "PrintReport")
	public ModelAndView printChallanReceipt(HttpServletRequest request) {
        Date date = new Date();
    	this.getModel().setDate(Utility.dateToString(date, MainetConstants.DATE_FORMAT));
    	String currentYear = null;
		try {
			currentYear = Utility.getFinancialYearFromDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	getModel().setFinYear(currentYear);
    	SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
    	this.getModel().setTime(localDateFormat.format(new Date()));
    	this.getModel().setOrgNameMar(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
    	this.getModel().setDeptName(this.getModel().getProvisionalAssesmentMstDto().getDeptName());
    	this.getModel().setServiceName(this.getModel().getProvisionalAssesmentMstDto().getServiceName());
    	return new ModelAndView("editNameAddressAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
    }
	
	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		this.getModel().bind(request);
		List<String> flatNoList = null;
		String billingMethod = mutationService.getPropertyBillingMethod(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
		
		this.getModel().setBillingMethod(billingMethod);
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FLAGI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = mutationService.getPropertyFlatList(propNo,
					String.valueOf(UserSession.getCurrent().getOrganisation().getOrgid()));
		}
		if(CollectionUtils.isNotEmpty(flatNoList)) {
			this.getModel().setFlatNoList(flatNoList);
		}
		return flatNoList;
	}
}
