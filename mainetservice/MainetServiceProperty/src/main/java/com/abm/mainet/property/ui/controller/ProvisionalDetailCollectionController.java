package com.abm.mainet.property.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.ui.model.ProvisionalDetailCollectionModel;

@Controller
@RequestMapping("/ProvisionalCollection.html")
public class ProvisionalDetailCollectionController extends AbstractFormController<ProvisionalDetailCollectionModel>{

	@Autowired
	private DepartmentService idepartmentService;  
	
	@Autowired
	private DataEntrySuiteService idataEntrySuiteService; 
	
	@RequestMapping(method= {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView index(final HttpServletRequest httpServletRequest,final Model models) {
		sessionCleanup(httpServletRequest);
		
		ProvisionalDetailCollectionModel model=this.getModel();
		Long currentOrgId=UserSession.getCurrent().getOrganisation().getOrgid();
		Long deptmntId = idepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
		List<LookUp> taxList = idataEntrySuiteService.getTaxCollectorList(deptmntId, currentOrgId);
		
		
		model.setTaxWiseList(taxList);
		return index();

}
	@RequestMapping(params="GetCollectionReport",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String viewReportSheet(@RequestParam("zoneWise") Long zoneWise, @RequestParam("wardWise") Long wardWise,
			@RequestParam("frmDate") Date frmDate, @RequestParam("toDate") Date toDate,@RequestParam("TaxCollectorWise") Long TaxCollectorWise,
		final HttpServletRequest request) {
				
		Long currentOrgId=UserSession.getCurrent().getOrganisation().getOrgid();
		
		String PFrmDate=Utility.dateToString(frmDate, "yyyy-MM-dd");
		String PtoDate=Utility.dateToString(toDate, "yyyy-MM-dd");
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL+"=ProvisionalDetailCollectionRegister.rptdesign&OrgId="+currentOrgId +
				"&Zone="+zoneWise+"&Ward="+wardWise +"&FromDate="+PFrmDate +"&ToDate="+PtoDate +"&TaxCollector=" +TaxCollectorWise;
				
	}
}
 
