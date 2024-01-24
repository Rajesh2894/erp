package com.abm.mainet.securitymanagement.ui.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.TransferSchedulingOfStaffDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.service.IShiftMasterService;
import com.abm.mainet.securitymanagement.service.ITransferSchedulingOfStaffService;
import com.abm.mainet.securitymanagement.ui.model.TransferSchedulingOfStaffModel;

@Controller
@RequestMapping(value = "/TransferAndDutyScheduling.html")
public class TransferSchedulingOfStaffController extends AbstractFormController<TransferSchedulingOfStaffModel> {

	@Resource
	private TbAcVendormasterService tbVendormasterService;

	@Autowired
	ITransferSchedulingOfStaffService transferSchedulingOfStaffService;
	
	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;
	
	@Autowired
	private IShiftMasterService iShiftMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {
		this.sessionCleanup(httpServletRequest);	
		this.getModel().setLocation(loadLocation());
		this.getModel().setVendorList(loadVendor());
		this.getModel().setLookup(iShiftMasterService.getAvtiveShift(UserSession.getCurrent().getOrganisation().getOrgid()));
		
		return index();
	}

	@RequestMapping(params = "searchStaffDetails", method = RequestMethod.POST)
	public ModelAndView findStaff(@RequestParam("empTypeId") final Long empTypeId,
			@RequestParam("vendorId") final Long vendorId, @RequestParam("cpdShiftId") final Long cpdShiftId,
			@RequestParam("locId") final Long locId, final HttpServletRequest request, final Model model) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String str=null;
		if(empTypeId!=null) {
			LookUp lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(empTypeId, UserSession.getCurrent().getOrganisation());
			str=lookUp.getLookUpCode();
		}
		List<TransferSchedulingOfStaffDTO> staffDetails = transferSchedulingOfStaffService.findStaffDetails(empTypeId,str,
				vendorId, cpdShiftId, locId, orgId);	
		this.getModel().setLocation(loadLocation());
		this.getModel().setVendorList(loadVendor());
		this.getModel().setLookup(iShiftMasterService.getAvtiveShift(UserSession.getCurrent().getOrganisation().getOrgid()));
		TransferSchedulingOfStaffDTO staffDTO = new TransferSchedulingOfStaffDTO();
		staffDTO.setEmpTypeId(empTypeId);
		staffDTO.setVendorId(vendorId);
		staffDTO.setCpdShiftId(cpdShiftId);
		staffDTO.setLocId(locId);
		this.getModel().setMode("Y");
		this.getModel().setTransferSchedulingOfStaffDTO(staffDTO);
		this.getModel().setTransferSchedulingOfStaffDTOList(staffDetails);
		return new ModelAndView("TransferSchedulingOfStaffValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	private List<TbAcVendormaster> loadVendor() {
		final List<TbAcVendormaster> list1 = contractualStaffMasterService
				.findAgencyBasedOnStaffMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		return list1;
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
	@RequestMapping(params = "checkDateWithAppointDate", method = RequestMethod.POST)
	public TransferSchedulingOfStaffDTO checkDateWithAppointDate(@RequestParam(value="contStaffIdNo") String contStaffIdNo,
			@RequestParam(value="contStaffSchFrom") Date contStaffSchFrom, final HttpServletRequest request) {
		TransferSchedulingOfStaffDTO staffData = transferSchedulingOfStaffService
				.checkDateWithAppointDate(contStaffIdNo, UserSession.getCurrent().getOrganisation().getOrgid());
		return staffData;
	}
	
	@RequestMapping(params = "checkIfStaffExists", method = RequestMethod.POST)
	public ModelAndView create(final TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("TransferSchedulingOfStaffValidn", MainetConstants.FORM_NAME, this.getModel());
	}
}
