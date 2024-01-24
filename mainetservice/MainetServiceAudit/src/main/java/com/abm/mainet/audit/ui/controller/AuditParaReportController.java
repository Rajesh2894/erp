package com.abm.mainet.audit.ui.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.audit.ui.model.AuditParaEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AuditParaReport.html")
public class AuditParaReportController extends AbstractFormController<AuditParaEntryModel> {

	
	
	@Autowired
	private TbDepartmentService deptService;
	
	@Autowired
	private IAuditParaEntryService auditParaService;
	
	@Resource
	private TbFinancialyearService tbFinancialyearService;
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		
		
        request.setAttribute("aFinancialYr", loadFinancialYearList());
        request.setAttribute("deptLst", loadDepartment());

		return defaultResult();
	}
	
	@ResponseBody
	@RequestMapping(params = "validateAuditReport", method = RequestMethod.POST)
	public Boolean validateAuditReport(final HttpServletRequest request, @RequestParam Long deptId,
			@RequestParam Long auditParaYear,
			@RequestParam String fromDate, @RequestParam String toDate) {
		
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> list = auditParaService.getAuditReportData(deptId, auditParaYear, fromDate, toDate, orgid);
		return !(list).isEmpty();
	}
	
	
	
	@ResponseBody
	@RequestMapping(params = "searchAuditReport", method = RequestMethod.POST)
	public ModelAndView searchAuditReport(final HttpServletRequest request, @RequestParam Long deptId,
			@RequestParam Long auditParaYear,
			@RequestParam String fromDate, @RequestParam String toDate) {
		sessionCleanup(request);
		ModelAndView mv = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> list = auditParaService.getAuditReportData(deptId, auditParaYear, fromDate, toDate, orgid);
		AuditParaEntryDto dto = null;
		List<AuditParaEntryDto> auditParaEntryDtoList = new ArrayList<>();
		if(list != null) {
			for(Object[] obj : list) {
				dto = new AuditParaEntryDto();
				if(obj[0] != null)
					dto.setAuditParaCode(obj[0].toString());
				if(obj[1] != null)
					dto.setAuditParaSub(obj[1].toString());
				if(obj[2] != null)
					dto.setAuditAppendix(obj[2].toString());
				
				auditParaEntryDtoList.add(dto);
			}
		}
		
		String finyear = tbFinancialyearService.findFinancialYearDesc(auditParaYear);
		
		String pattern = "dd-MM-yyyy";
		String timePattern = "hh:mm a";
		String dateInString =new SimpleDateFormat(pattern).format(new Date());
		String time =new SimpleDateFormat(timePattern).format(new Date());
		
		request.setAttribute("finyear", finyear);
		request.setAttribute("auditParaEntryDtoList", auditParaEntryDtoList);
		request.setAttribute("date", dateInString);
		request.setAttribute("time", time);
		
		mv = new ModelAndView("auditParaReport", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	
	
	// Get Active Department List from Department Master Dto
	public List<TbDepartment> loadDepartment() {
		return deptService.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid());
	}
	
	public List<TbFinancialyear> loadFinancialYearList() {
		Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
         List<TbFinancialyear> faYears = new ArrayList<>();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    faYears.add(finYearTemp);
                } catch (Exception ex) {
                	
                }
            });
        }
		return faYears;
	}
	
	public String getStatusLookupCode(Long value) {
		LookUp lookup = CommonMasterUtility.lookUpByLookUpIdAndPrefix(value, IAuditConstants.AUDIT_PARA_STATUS_PREFIX, UserSession.getCurrent().getOrganisation().getOrgid());
		
		return lookup.getLookUpCode();
	}
	
	

}
