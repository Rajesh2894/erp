package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.dto.UserChargeCollectionDTO;
import com.abm.mainet.swm.service.ISolidWasteBillMasterService;
import com.abm.mainet.swm.ui.model.UserChargeCollectionReportModel;

@Controller
@RequestMapping("/UserChargeCollectionReport.html")
public class UserChargeCollectionReportController extends AbstractFormController<UserChargeCollectionReportModel> {

    @Autowired
    ISolidWasteBillMasterService solidWasteBillMasterService;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);     
        return index();
    }
    
   
    @ResponseBody
    @RequestMapping(params = "getUserCollectionChargeLogReport", method = RequestMethod.POST)
    public ModelAndView getUserCollectionChargeLogReport(final HttpServletRequest request, @RequestParam("monthNo") Long monthNo,@RequestParam("monthName") String monthName) {
        sessionCleanup(request);
        String redirectType = null; 
        UserChargeCollectionDTO userChargeCollectionDto = solidWasteBillMasterService.getUserChargeDetails(monthNo);  
        
        if(userChargeCollectionDto==null) {            
            redirectType="UserChargeCollectionList"; 
            this.getModel().getUserCharges().setStatusFlag("N");
        }else {
            userChargeCollectionDto.setMonthNo(monthName);
            this.getModel().setUserCharges(userChargeCollectionDto);
            redirectType ="UserChargeCollection";
            this.getModel().getUserCharges().setStatusFlag("Y");
            
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, this.getModel());
    }

}
