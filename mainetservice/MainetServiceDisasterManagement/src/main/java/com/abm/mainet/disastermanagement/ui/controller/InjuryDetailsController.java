package com.abm.mainet.disastermanagement.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.constant.DisasterConstant;
import com.abm.mainet.disastermanagement.dto.CallScrutinyDTO;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IComplainRegisterService;
import com.abm.mainet.disastermanagement.ui.model.InjuryDetailsModel;

@Controller
@RequestMapping(value = "/InjuryDetails.html")
public class InjuryDetailsController extends AbstractFormController<InjuryDetailsModel> {

	@Autowired
	private IComplainRegisterService icomplainRegister;
    
	@Autowired
	private IEmployeeService employeeService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("InjuryDetails.html");
		httpServletRequest.setAttribute("locations", loadLocation());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<ComplainRegisterDTO> complaints = icomplainRegister.findInjuryDetails(null,
				null, null, null, orgId, null);
		model.addAttribute("complaints", complaints);
		return new ModelAndView("InjuryDetails", MainetConstants.FORM_NAME, getModel());
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}

	
	
	@RequestMapping(params = "editDisInjury", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewInjury(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long complainId,String complainNo, final HttpServletRequest httpServletRequest) {
		this.getModel().setComplainId(complainId);
		this.getModel().setCall(icomplainRegister.getDepartmentNameAndEmpName(complainId));
		this.getModel().setComplainRegisterDTO(icomplainRegister.getComplainById(complainId));
		this.getModel().getComplainRegisterDTO().setComplaintStatus(DisasterConstant.OPEN);
		this.getModel().setSaveMode(mode);
		
		// validation for pending status start
		List<CallScrutinyDTO> list = this.getModel().getCall();
		if(list!=null && !(list.isEmpty())) {
			int count = 0;
			for(CallScrutinyDTO dtoList : list) {
				if(dtoList.getComplainStatus().equalsIgnoreCase(DisasterConstant.PENDING) && count==0) {
					count++;
				}
				 List<EmployeeBean>  empMapBean= employeeService.getAllEmployee( UserSession.getCurrent().getOrganisation().getOrgid());
				String s[] = dtoList.getCallAttendEmployee().split(",");
				StringBuilder combineEmp = new StringBuilder();
				empMapBean.forEach(e -> { 
					for(int i=0; i < s.length; i++) {
					if(e.getEmpId().toString().equals(s[i])) {
						combineEmp.append(e.getFullName()+"("+e.getDesignName()+")");
						combineEmp.append("<br>");
				}}});
				dtoList.setCallAttendEmployee(combineEmp.toString());
			}
			if(count!=0) {
				this.getModel().getComplainRegisterDTO().setStatusVariable(DisasterConstant.PENDING);
			} else {
				this.getModel().getComplainRegisterDTO().setStatusVariable(DisasterConstant.PROCEED);
			}
		}
		// validation for pending status end
		
		return new ModelAndView("InjuryDetailsadd", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(params = "searchInjSummary", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<ComplainRegisterDTO> findComplainClose(
			@RequestParam("complaintType1") Long complaintType1,
			@RequestParam("complaintType2") Long complaintType2,
			@RequestParam("location") Long location,
			@RequestParam("srutinyStatus") String srutinyStatus,
			@RequestParam("complainNo") String complainNo,final HttpServletRequest request, final Model model) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(""==srutinyStatus || srutinyStatus.isEmpty())
			srutinyStatus=null;
		if(""==complainNo || complainNo.isEmpty())
			complainNo=null;
		List<ComplainRegisterDTO> injuryDetailsList = icomplainRegister.findInjuryDetails(complaintType1,
				complaintType2, location, complainNo, orgId, srutinyStatus);
		return injuryDetailsList;
	}

	
		
	
}
	
	
	
	


