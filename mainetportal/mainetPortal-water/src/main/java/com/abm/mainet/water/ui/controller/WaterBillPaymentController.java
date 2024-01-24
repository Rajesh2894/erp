
package com.abm.mainet.water.ui.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillRequestDTO;
import com.abm.mainet.water.dto.WaterBillResponseDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.IWaterBillPaymentService;
import com.abm.mainet.water.ui.model.WaterBillPaymentModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/WaterBillPayment.html")
public class WaterBillPaymentController extends AbstractFormController<WaterBillPaymentModel> {
	
	private static final Logger LOGGER = Logger.getLogger(WaterBillPaymentController.class);
	
	@Autowired
	private IWaterBillPaymentService iWaterBillPaymentService;
	
	@Autowired
    private IOrganisationService iOrganisationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillPayment")
    public ModelAndView searchWaterBillRecords(final HttpServletRequest httpServletRequest)
            throws JsonParseException, JsonMappingException, IOException {
    	sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final WaterBillPaymentModel model = getModel();
        WaterDataEntrySearchDTO cc=model.getSearchDTO();
        if(cc.getCsCcn()!=null) {
	        model.setCcnNumber(cc.getCsCcn());
	        model.setMode("Y");
		}
        model.getBillPaymentData();
        if(model.getMode()!=null && model.getMode().equals("Y"))
        {
        	return new ModelAndView("WaterBillPaymentValidn", MainetConstants.FORM_NAME, model);
        }
       
        return index();
    }
    
    @RequestMapping(params="payment", method = RequestMethod.GET)
	public ModelAndView getWaterDetails(final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam("conNo") final String connectionNumber) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " getWaterDetails() method");
		sessionCleanup(request);
		getModel().bind(request);
		final WaterBillPaymentModel model = getModel();
		model.setCcnNumber(connectionNumber);
		model.setMode(MainetConstants.FlagY);
		Organisation organisation = iOrganisationService.getActiveOrgByUlbShortCode(MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ_ULB);
		model.setOrgId(organisation.getOrgid());
		model.getBillPaymentData();
		ModelAndView mv = new ModelAndView("WaterBillPaymentSearch", MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		if(getModel().hasValidationErrors()) {
			return mv;
		}
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " getWaterDetails() method");
		return new ModelAndView("WaterBillPayment", MainetConstants.FORM_NAME, model);
	}
   
    @RequestMapping(method = RequestMethod.POST, params = "searchBillPay")
    public ModelAndView searchBillPay(final HttpServletRequest httpServletRequest,
            @RequestParam(value = "consumerNo") String consumerNo)
            throws JsonParseException, JsonMappingException, IOException {
    	sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        final WaterBillPaymentModel model = getModel();
        model.setCcnNumber(consumerNo);
        model.getBillPaymentData();
        if(UserSession.getCurrent().getEmployee().getEmploginname().equals(MainetConstants.NOUSER)) {
       	 return new ModelAndView("WaterBillPaymentSearchHome", MainetConstants.FORM_NAME, model);	
       } 
        return new ModelAndView("WaterBillPaymentUlbHome", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "waterDetailSearch", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView propertyDeatilSearch(final HttpServletRequest request)
    {
    	getModel().bind(request);
    	this.sessionCleanup(request);
    	WaterBillPaymentModel model = this.getModel();
    	return new ModelAndView("WaterBillPaymentDetailSearch", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public @ResponseBody List<WaterDataEntrySearchDTO> search(HttpServletRequest request) {
    	
    	WaterBillPaymentModel model = this.getModel();
        model.bind(request);
        List<WaterDataEntrySearchDTO> result = null;  
        WaterDataEntrySearchDTO dto= model.getSearchDTO();
        dto.setOrgId(Utility.getOrgId());
        result = iWaterBillPaymentService.getBillPaymentDetailData(dto);
       
		return result;
        
    }
    
    @RequestMapping(params = "backToMainSearch", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);      
        getModel().bind(httpServletRequest);
        WaterBillPaymentModel model = this.getModel();
        return new ModelAndView("WaterBillPaymentDetailSearchBack", MainetConstants.FORM_NAME, model);
    }
    
    
    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillPaymentFromViewWater")
    public ModelAndView searchWaterBillRecordsFromViewWater(final HttpServletRequest httpServletRequest, @RequestParam("CsCcn") String connNo)
            throws JsonParseException, JsonMappingException, IOException {
    	sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        final WaterBillPaymentModel model = getModel();
        WaterDataEntrySearchDTO cc=model.getSearchDTO();
        if(cc.getCsCcn()!=null)
        {
        model.setCcnNumber(cc.getCsCcn());
        model.setMode("Y");
        }
        if(StringUtils.isNotBlank(connNo)) {
        	model.setCcnNumber(connNo);
        	 model.setMode("Y");
        }
        model.getBillPaymentData();
        if(model.getMode()!=null && model.getMode().equals("Y"))
        {
        	return new ModelAndView("WaterBillPaymentValidn", MainetConstants.FORM_NAME, model);
        }
       
        return index();
    }
    
    @RequestMapping(params = "searchBillPaymentAscl", method = RequestMethod.POST)
    public ModelAndView getBillPayDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "consumerNo") String consumerNo) {
    	 sessionCleanup(httpServletRequest);
        return new ModelAndView("WaterBillPaymentSearch", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "WaterReceiptPrint", method = RequestMethod.POST)
    public ModelAndView WaterReceiptPrint(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        WaterBillPaymentModel model = this.getModel();
        return new ModelAndView("WaterBillReceiptHome", MainetConstants.FORM_NAME, model);

    }
    @RequestMapping(params = "generateWaterReceipt", method = RequestMethod.POST)
	public @ResponseBody String generateWaterReceipt(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
    		getModel().bind(httpServletRequest);
    		WaterBillPaymentModel model = this.getModel();
				return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=ReceiptReprintingReport_ConnectionNoWise.rptdesign&ULBName="
				+ UserSession.getCurrent().getOrganisation().getOrgid()+"&ConnectionNoWise="+ model.getCcnNumber()+"&ReceiptDate="+ model.getWaterReceiptDate();
	}

    @RequestMapping(method = RequestMethod.POST, params = "redirectToWaterPayment")
    public ModelAndView redirectToWaterPayment(final HttpServletRequest httpServletRequest)throws JsonParseException, JsonMappingException, IOException {
    	sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        return new ModelAndView("WaterBillPaymentUlbHome", MainetConstants.FORM_NAME, this.getModel());
    }
    
    
    
}
