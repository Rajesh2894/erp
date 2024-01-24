package com.abm.mainet.additionalservices.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.CFCCollectionMasterDto;
import com.abm.mainet.additionalservices.dto.CFCCounterScheduleDto;
import com.abm.mainet.additionalservices.dto.CFCSchedularSummaryDto;
import com.abm.mainet.additionalservices.repository.CFCSchedulingForTrxRepo;
import com.abm.mainet.additionalservices.service.CFCSchedulingTrxService;
import com.abm.mainet.additionalservices.ui.model.CFCSchedulingForTrxModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/CFCSchedulingTrx.html")
public class CFCSchedulingTrxController extends AbstractFormController<CFCSchedulingForTrxModel> {

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	private IEmployeeService employeeScheduleService;

	@Autowired
	private CFCSchedulingForTrxRepo cfcSchedulingForTrxRepo;

	@Autowired
	private CFCSchedulingTrxService cfcSchedulingService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		List<CFCSchedularSummaryDto> cfcSchedularSummaryDtos = cfcSchedulingService.searchCollectionInfo(null,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setCfcSchedularSummaryDtos(cfcSchedularSummaryDtos);
		loadData(httpServletRequest);
		return index();
	}

	private void loadData(final HttpServletRequest httpServletRequest) {
		List<Employee> empList = employeeScheduleService
				.getActiveEmployeeList(UserSession.getCurrent().getOrganisation().getOrgid());
		empList.forEach(emp -> {
			StringBuilder empName = new StringBuilder();
			if (emp.getEmpname() != null)
				empName.append(emp.getEmpname());
			if (emp.getEmpmname() != null)
				empName.append(" " + emp.getEmpmname());
			if (emp.getEmplname() != null)
				empName.append(" " + emp.getEmplname());
			emp.setEmpname(empName.toString());
		});
		this.getModel().setEmpList(empList);

		this.getModel().setCollectionNos(
				cfcSchedulingForTrxRepo.getCollectionNoByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));

		loadWardZone(httpServletRequest);
	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest) {

		return new ModelAndView("CFCSchedulingTrx/create", MainetConstants.FORM_NAME, this.getModel());

	}

	private void loadWardZone(HttpServletRequest httpServletRequest) {
		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();
		wardList = locationMasterService.findlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		this.getModel().setWardList(wardMapList);

	}

	@RequestMapping(params = "addScheduleDetail", method = RequestMethod.POST)
	public ModelAndView saveBIDDetails(HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.FlagC);
		return new ModelAndView("CFCSchedulingTrx/create", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView sequenceMasterSearch(final HttpServletRequest request, @RequestParam String collectionNo,
			@RequestParam String counterNo, @RequestParam Long userId, @RequestParam String status) {

		sessionCleanup(request);
		loadData(request);
		List<CFCSchedularSummaryDto> cfcSchedularSummaryDtos = cfcSchedulingService.searchCollectionInfo(collectionNo,
				counterNo, userId, status, UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setCfcSchedularSummaryDtos(cfcSchedularSummaryDtos);

		return new ModelAndView("CFCSchedulingTrx/summary", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "searchCounterNo", method = RequestMethod.POST)
	public @ResponseBody List<String> searchPrefixDataById(@RequestParam("collectionNo") final String collectionNo) {

		List<String> counterNoList = new ArrayList<String>();
		counterNoList = cfcSchedulingService.getCounterNos(collectionNo);

		return counterNoList;
	}

	@ResponseBody
	@RequestMapping(params = "formForEdit", method = RequestMethod.POST)
	public ModelAndView formForEdit(final HttpServletRequest httpServletRequest,
			@RequestParam("counterScheduleId") final Long counterScheduleId) {
		this.getModel().setSaveMode(MainetConstants.FlagE);
		// CFCCollectionMasterDto cfcCollectionMasterDto =
		// cfcSchedulingService.getCFCCollectionInfoById(collectionId);
		// this.getModel().setCfcCollectionMasterDto(cfcCollectionMasterDto);

		CFCCounterScheduleDto cfcCounterScheduleDto = cfcSchedulingService
				.searchCounterScheduleBuId(UserSession.getCurrent().getOrganisation().getOrgid(), counterScheduleId);

		this.getModel().setCfcCounterScheduleDto(cfcCounterScheduleDto);

		return new ModelAndView("CFCSchedulingTrx/edit", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "updateCounterDetail", method = RequestMethod.POST)
	public ModelAndView updateCounterDetail(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		CFCCounterScheduleDto cfcCounterScheduleDto = this.getModel().getCfcCounterScheduleDto();
		if(this.getModel().getCfcCollectionMasterDto() != null){
		if(this.getModel().getCfcCollectionMasterDto().getDeviceId() !=null)
			cfcCounterScheduleDto.setDeviceId(this.getModel().getCfcCollectionMasterDto().getDeviceId());
		}
		if(this.getModel().getStatus().isEmpty())
			this.getModel().setStatus(MainetConstants.FlagA);
		cfcCounterScheduleDto.setCsStatus(this.getModel().getStatus());
		cfcCounterScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		cfcCounterScheduleDto.setUpdatedDate(new Date());
		cfcCounterScheduleDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		cfcSchedulingService.updateCounterScheduleDetail(cfcCounterScheduleDto);
		return new ModelAndView("CFCSchedulingTrx/edit", MainetConstants.FORM_NAME, this.getModel());

	}

}
