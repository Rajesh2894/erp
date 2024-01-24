package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.WaterBillPaymentModel;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/WaterBillPayment.html")
public class WaterBillPaymentController extends AbstractFormController<WaterBillPaymentModel> {
	
	 @Autowired
	 private NewWaterConnectionService newWaterConnectionService;


    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillPayment")
    public ModelAndView searchWaterBillRecords(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final WaterBillPaymentModel model = getModel();
        WaterDataEntrySearchDTO cc=model.getSearchDTO();
        this.getModel().setShowMode(MainetConstants.FlagN);
        this.getModel().setRebateMessage(MainetConstants.FlagN);
        if(cc.getCsCcn()!=null)
        {
        model.setCcnNumber(cc.getCsCcn());
        model.setMode(MainetConstants.FlagY);
        }
        model.getBillPaymentData();
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)){
			model.setSkdclEnv(MainetConstants.FlagY);
		}
        if(model.getMode()!=null && model.getMode().equals(MainetConstants.FlagY))
        {
        	return new ModelAndView("WaterBillPaymentValidn", MainetConstants.FORM_NAME, model);
        }
        return index();
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
        WaterDataEntrySearchDTO dto = model.getSearchDTO();
        dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		
        if (!model.hasValidationErrors()) {
			result = newWaterConnectionService.searchConnectionDetails(model.getSearchDTO(),null,null,null);
        }
        
		return result;
        
    }
    
    @RequestMapping(params = "backToMainSearch", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);      
        getModel().bind(httpServletRequest);
        WaterBillPaymentModel model = this.getModel();
        return new ModelAndView("WaterBillPaymentDetailSearchBack", MainetConstants.FORM_NAME, model);
    }
    
}
