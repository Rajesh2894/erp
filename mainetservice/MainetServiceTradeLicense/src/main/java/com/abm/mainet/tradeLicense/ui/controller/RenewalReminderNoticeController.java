package com.abm.mainet.tradeLicense.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.InspectionDetailService;
import com.abm.mainet.tradeLicense.ui.model.RenewalReminderNoticeModel;

@Controller
@RequestMapping("/RenewalReminderNotice.html")
public class RenewalReminderNoticeController  extends AbstractFormController<RenewalReminderNoticeModel> {
	
	private static final Logger LOGGER = Logger.getLogger(RenewalReminderNoticeController.class);
	
	 @Autowired
     private ITradeLicenseApplicationService tradeLicenseApplicationService;
	 
	@Autowired
	private ITradeLicenseApplicationService iTradeLicApplService;
	
	@Autowired
	private InspectionDetailService inspectionDetailService;

	
	   @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	    public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 1,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setTriCodList1(lookUpList);
		} catch (Exception e) {
			LOGGER.info("ITC  Prefix Not found");
		}	
		this.getModel().setDataDisplayFlag(MainetConstants.FlagN);
		return index();
	   }
	   
		@ResponseBody
		@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_SUBCATAGORY_BY_CATID, method = {
				RequestMethod.POST })
		public List<LookUp> searchLicenseSubCatagory(@RequestParam(MainetConstants.Common_Constant.TRI_COD1) Long triCode1,
				HttpServletRequest request) {
			List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
			try {
				List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 2,
						UserSession.getCurrent().getOrganisation());
				lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == triCode1)
						.collect(Collectors.toList());
				return lookUpList1;
			} catch (Exception e) {
				// TODO: handle exception
	            LOGGER.info("ITC Prefix Level-2 data not found inside searchLicenseSubCatagory method");
				return lookUpList1;

			}
		}
		
	
		@RequestMapping(params = MainetConstants.TradeLicense.SEARCH_DETAILS, method = {RequestMethod.POST })
		public ModelAndView searchLicenseDetailsByCatAndDate(@RequestParam("triCod1") Long triCod1 ,@RequestParam("triCod2") Long triCod2,
				@RequestParam("fromDate") Date fromDate , @RequestParam("toDate") Date toDate ,final HttpServletRequest httpServletRequest){
			this.getModel().bind(httpServletRequest);
			List<TradeMasterDetailDTO> mastDto = new ArrayList<>();
			this.getModel().setDataDisplayFlag(MainetConstants.FlagY);
			Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
			mastDto = tradeLicenseApplicationService.getLicenseDetByCatAndDate(triCod1,triCod2,fromDate,toDate,orgId);
			this.getModel().setTrdMasDtoList(mastDto);
			this.getModel().getRenewalDto().setTriCod1(triCod1);
			this.getModel().setSubCatId(triCod2);
			this.getModel().getRenewalDto().setTreLicfromDate(fromDate);
			this.getModel().getRenewalDto().setTreLictoDate(toDate);
			if(CollectionUtils.isEmpty(mastDto)) {		
				ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_REMINDER_NOTICE_VALIDN,
							MainetConstants.FORM_NAME, this.getModel());		
			 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		    this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("no.records.availble"));
		    return mv;
			}
			return new ModelAndView(MainetConstants.TradeLicense.RENEWAL_REMINDER_NOTICE_VALIDN,
					MainetConstants.FORM_NAME, this.getModel());
		}
		
		
	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_RENEWAL_REMINDER_NOTICE, method = {
			RequestMethod.POST })
	 public ModelAndView printRenewalreminderNotice(@RequestParam("trdLicno") String trdLicno , HttpServletRequest request) {
		this.getModel().bind(request);
		RenewalReminderNoticeModel model = this.getModel();
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
		TradeMasterDetailDTO trdMastDet = iTradeLicApplService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		if(trdMastDet != null)
		model.setTrdMastDto(trdMastDet);		
		if (!model.saveNoticeForm()) {
			ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_REMINDE_RNOTICE_PRINT,
					MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(getApplicationSession().getMessage("renewal.save.validation.message"));			
			return mv;
		    } else {
			model.sendSmsEmail(model.getTrdMastDto(), "RenewalReminderNotice.html",PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG);
		}
		NoticeDetailDto dto = inspectionDetailService.getNoticeDetailsByTrdIdAndOrgId(model.getTrdMastDto(),orgId);
	    model.setNoticeDetailDto(dto);
	    //#117325
	    String ulbName,desgination;
	    if (UserSession.getCurrent().getLanguageId() == 1) {
            ulbName = UserSession.getCurrent().getOrganisation().getONlsOrgname();
            desgination = UserSession.getCurrent().getEmployee().getDesignation().getDsgname();
        } else {
            ulbName = UserSession.getCurrent().getOrganisation().getONlsOrgnameMar();
            desgination = UserSession.getCurrent().getEmployee().getDesignation().getDsgnameReg();
        }
	    this.getModel().setONlsOrgname(ulbName);
	    this.getModel().setDesignation(desgination);
	    return new ModelAndView(MainetConstants.TradeLicense.RENEWAL_REMINDE_RNOTICE_PRINT,
				MainetConstants.FORM_NAME, this.getModel());	
	}
}
