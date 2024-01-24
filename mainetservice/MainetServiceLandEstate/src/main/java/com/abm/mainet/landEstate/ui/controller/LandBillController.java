package com.abm.mainet.landEstate.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.landEstate.ui.model.LandAcquisitionModel;

@Controller
@RequestMapping(MainetConstants.LandEstate.LAND_BILL_URL)
public class LandBillController extends AbstractFormController<LandAcquisitionModel>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LandBillController.class);
	private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";
	
	 @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request)throws Exception{
		
		  return defaultResult();
	}
	 
	 
	 @RequestMapping(params = "landRentNoticeGeneration", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView landRentNoticeGeneration(final HttpServletRequest request) {
	        getModel().bind(request);
	        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
	                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
	        this.getModel().setLocationList(locationList);
	       
	        return new ModelAndView("landRentNoticeGeneration", MainetConstants.FORM_NAME, this.getModel());
	    }
	 
	 @RequestMapping(params = "billPayment", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView billPayment(final HttpServletRequest request) {
	        getModel().bind(request);
	       List<BankMasterEntity> bankList=ApplicationContextProvider.getApplicationContext().getBean(BankMasterService.class).getBankList();
	       this.getModel().setBankList(bankList);
	        return new ModelAndView("billPayment", MainetConstants.FORM_NAME, this.getModel());
	    }
	 
	 @RequestMapping(params = "searchReport", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView searchReport(final HttpServletRequest request) {
	        getModel().bind(request);
	        List<TbFinancialyear> financialYearList = ApplicationContextProvider.getApplicationContext()
	                .getBean(TbFinancialyearService.class)
	                .findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
	        this.getModel().getFinancialYearList().clear();
	        if(financialYearList!=null && !financialYearList.isEmpty()) {
	        	financialYearList.forEach(finYearTemp ->{
	        		try
	        		{
	        			finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
	        			this.getModel().getFinancialYearList().add(finYearTemp);
	        		}catch (Exception ex) {
	                    throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
	                }
	        		
	        	});
	        }
	       
	       
	        return new ModelAndView("searchReport", MainetConstants.FORM_NAME, this.getModel());
	    }
	 
	 @RequestMapping(params = "landRevenueReport", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView landRevenueReport(final HttpServletRequest request) {
	        getModel().bind(request);
	        
	       
	        return new ModelAndView("landRevenueReport", MainetConstants.FORM_NAME, this.getModel());
	    }
	 
	 @RequestMapping(params = "landOutstandingRegister", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView landOutstandingRegister(final HttpServletRequest request) {
	        getModel().bind(request);
	        
	       
	        return new ModelAndView("landOutstandingRegister", MainetConstants.FORM_NAME, this.getModel());
	    }
	 
	 @RequestMapping(params = "landDefaulterRegister", method = { RequestMethod.POST, RequestMethod.GET })
	    public ModelAndView landDefaulterRegister(final HttpServletRequest request) {
	        getModel().bind(request);
	        
	       
	        return new ModelAndView("landDefaulterRegister", MainetConstants.FORM_NAME, this.getModel());
	    }
	 

}
