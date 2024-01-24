package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.IViewWaterDetailsService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.WaterDuesCheckModel;

@Controller
@RequestMapping("/WaterDuesCheck.html")
public class WaterDuesCheckController extends AbstractFormController<WaterDuesCheckModel>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WaterDuesCheckController.class);
	
	@Autowired
	IViewWaterDetailsService viewWaterDetailsService;
	
	@Autowired
	NewWaterConnectionService newWaterConnectionService;
	
	@RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setShowForm(MainetConstants.FlagN);
        return defaultResult();
    }
	
	@ResponseBody
	@RequestMapping(params = "searchWaterDetails", method = RequestMethod.POST)
	public List<WaterDataEntrySearchDTO> searchWaterDetails(HttpServletRequest request) {
		WaterDuesCheckModel model = this.getModel();
		model.bind(request);
		model.getEntrySearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<WaterDataEntrySearchDTO> searchConnectionDetails = newWaterConnectionService.searchConnectionDetails(model.getEntrySearchDto(), null, null, null);
		return searchConnectionDetails;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, params = "getWaterDetail")
    public ModelAndView searchWaterBillDetails(final HttpServletRequest httpServletRequest, @RequestParam String connNo) {
        bindModel(httpServletRequest);
        final WaterDuesCheckModel model = getModel();
        WaterDataEntrySearchDTO cc=model.getEntrySearchDto();
        if(cc.getCsCcn() !=null || !"".equals(connNo)) {
        	if("".equals(cc.getCsCcn())) {
        		cc.setCsCcn(connNo);
        	}
        	
        	
        	 model.setCcnNumber(cc.getCsCcn());
             model.getBillPaymentData();
        	
        }
       
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)){
			model.setSkdclEnv(MainetConstants.FlagY);
		}
        this.getModel().setShowForm(MainetConstants.FlagY);
        return new ModelAndView("WaterDuesCheckValidn", MainetConstants.FORM_NAME,model);
        
    }
	
	@ResponseBody
	@RequestMapping(params = "backToSearchWaterDetails", method = RequestMethod.POST)
	public ModelAndView backToSearchWaterDetails(HttpServletRequest request,@RequestParam String connNo) {
	bindModel(request);
	WaterDuesCheckModel model = this.getModel();
	model.getEntrySearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	List<WaterDataEntrySearchDTO> searchConnectionDetails = newWaterConnectionService.searchConnectionDetails(model.getEntrySearchDto(), null, null, null);
	this.getModel().setEntrySearchDtoList(searchConnectionDetails);
	this.getModel().setShowForm(MainetConstants.FlagN);
	return new ModelAndView("WaterDuesCheckValidn", MainetConstants.FORM_NAME,model);
	}
	
	

}
