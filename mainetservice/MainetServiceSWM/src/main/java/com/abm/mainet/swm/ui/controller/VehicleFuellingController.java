package com.abm.mainet.swm.ui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.PumpFuelDetailsDTO;
import com.abm.mainet.swm.dto.PumpMasterDTO;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IPumpMasterService;
import com.abm.mainet.swm.service.IVehicleFuellingService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.VehicleFuellingModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleFueling.html")
public class VehicleFuellingController extends AbstractFormController<VehicleFuellingModel> {

	/**
	 * The IVehicleFuelling Service
	 */
	@Autowired
	private IVehicleFuellingService vehicleFuellingService;

	/**
	 * IVehicle Master Service
	 */
	@Autowired
	private IVehicleMasterService vehicleMasterService;

	/**
	 * IPump Master Service
	 */
	@Autowired
	private IPumpMasterService pumpMasterService;

	/**
	 * TbAcVendormaster Service
	 */
	@Autowired
	private TbAcVendormasterService vendorService;

	/**
	 * IFileUpload Service
	 */
	@Autowired
	private IFileUploadService fileUpload;

	/**
	 * IAttachDocs Service
	 */
	@Autowired
	private IAttachDocsService attachDocsService;

	private List<VehicleMasterDTO> getVehicleMasterList() {
		List<VehicleMasterDTO> vehicleMasterList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null,
				null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> locationMap = vehicleMasterList.stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
		if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuellingList())) {
			this.getModel().getVehicleFuellingList().forEach(master -> {
				master.setVeNo(locationMap.get(master.getVeId()));
			});
		}
		return vehicleMasterList;
	}

	public List<PumpMasterDTO> getPumpMasterList() {
		List<PumpMasterDTO> pumpMasterList = pumpMasterService.serchPumpMasterByPumpType(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> pumpMap = pumpMasterList.stream()
				.collect(Collectors.toMap(PumpMasterDTO::getPuId, PumpMasterDTO::getPuPumpname));
		if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuellingList())) {
			this.getModel().getVehicleFuellingList().forEach(master -> {
				master.setPuPumpname(pumpMap.get(master.getPuId()));
			});
		}
		return pumpMasterList;
	}

	public List<TbAcVendormaster> getVendorMasterList() {
		final Long vendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
						UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
				.getLookUpId();
		this.getModel().setVendorList(
				vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
		return vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
	}

	/**
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(null, null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setCommonHelpDocs("VehicleFueling.html");
		getVehicleMasterList();
		httpServletRequest.setAttribute("pumps", getPumpMasterList());
		return index();

	}

	/**
	 * Add Vehicle Fueling
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "AddVehicleFueling", method = RequestMethod.POST)
	public ModelAndView addVehicleFueling(final HttpServletRequest request) {
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(request);
		this.getModel().setLookUps(CommonMasterUtility.getLookUps("TYI", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = new ModelAndView("AddVehicleFuelling/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vendors", getVendorMasterList());
		mv.addObject("vehicles", getVehicleMasterList());
		mv.addObject("pumps", getPumpMasterList());
		return mv;
	}

	/**
	 * Edit Vehicle Fueling
	 * 
	 * @param request
	 * @param vefId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "editVehicleFuelling", method = RequestMethod.POST)
	public ModelAndView editVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
		sessionCleanup(request);
		this.getModel().setVehicleFuellingDTO(vehicleFuellingService.getVehicleByVehicleId(vefId));
		PumpMasterDTO pumpMasterDTO = pumpMasterService
				.getPumpByPumpId(this.getModel().getVehicleFuellingDTO().getPuId());
		List<PumpFuelDetailsDTO> pumpFuelDetails = pumpMasterDTO.getTbSwPumpFuldets();
		int i = 0;
		for (PumpFuelDetailsDTO test : pumpFuelDetails) {
			if (this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i).getPfuId() == test.getPfuId()) {
				test.setPuFuName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
				test.setPuFuUnitName(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
				this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i).setPumpFuelName(
						CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
				this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i)
						.setFuelUnit(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
				i++;
			}
		}
		ModelAndView mv = new ModelAndView("editVehicleFuelling/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("pumpFuelDetails", pumpFuelDetails);
		mv.addObject("vendors", getVendorMasterList());
		mv.addObject("vehicles", getVehicleMasterList());
		mv.addObject("pumps", getPumpMasterList());
		final List<AttachDocs> attachDocs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), vefId.toString());
		this.getModel().setAttachDocsList(attachDocs);
		return mv;
	}

	/**
	 * view Vehicle Fueling
	 * 
	 * @param request
	 * @param vefId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "viewVehicleFueling", method = RequestMethod.POST)
	public ModelAndView viewVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
		sessionCleanup(request);
		this.getModel().setVehicleFuellingDTO(vehicleFuellingService.getVehicleByVehicleId(vefId));
		PumpMasterDTO pumpMasterDTO = pumpMasterService
				.getPumpByPumpId(this.getModel().getVehicleFuellingDTO().getPuId());
		List<PumpFuelDetailsDTO> pumpFuelDetails = pumpMasterDTO.getTbSwPumpFuldets();
		int i = 0;
		for (PumpFuelDetailsDTO test : pumpFuelDetails) {
			if (this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i).getPfuId() == test.getPfuId()) {
				test.setPuFuName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
				test.setPuFuUnitName(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
				this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i).setPumpFuelName(
						CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
				this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().get(i)
						.setFuelUnit(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
				i++;
			}
		}
		ModelAndView mv = new ModelAndView("viewVehicleFuelling/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("pumpFuelDetails", pumpFuelDetails);
		mv.addObject("vendors", getVendorMasterList());
		mv.addObject("vehicles", getVehicleMasterList());
		mv.addObject("pumps", getPumpMasterList());
		final List<AttachDocs> attachDocs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), vefId.toString());
		this.getModel().setAttachDocsList(attachDocs);
		return mv;
	}

	/**
	 * delete Vehicle Fueling
	 * 
	 * @param request
	 * @param vefId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "deleteVehicleFueling", method = RequestMethod.POST)
	public ModelAndView deleteVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
		Employee emp = UserSession.getCurrent().getEmployee();
		vehicleFuellingService.deleteVehicle(vefId, emp.getEmpId(), emp.getEmppiservername());
		sessionCleanup(request);
		this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(null, null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = new ModelAndView("searchVehicleFuelling", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vendors", getVendorMasterList());
		mv.addObject("vehicles", getVehicleMasterList());
		mv.addObject("pumps", getPumpMasterList());
		return mv;
	}

	/**
	 * fetch Vehicle Fueling Details
	 * 
	 * @param request
	 * @param pumpId
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "fetchVehicleFuelingDetails", method = RequestMethod.POST)
	public ModelAndView fetchVehicleFuelingDetails(final HttpServletRequest request,
			@RequestParam(required = false) Long pumpId, Long orgId) {
		PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(pumpId);
		List<PumpFuelDetailsDTO> pumpFuelDetails = pumpMasterDTO.getTbSwPumpFuldets();
		pumpFuelDetails.forEach(det -> {
			det.setPuFuName(CommonMasterUtility.getCPDDescription(det.getPuFuid(), MainetConstants.BLANK));
			det.setPuFuUnitName(CommonMasterUtility.getCPDDescription(det.getPuFuunit(), MainetConstants.BLANK));
		});
		ModelAndView mv = new ModelAndView("AddPumpFuelDetails/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("pumpFuelDetails", pumpFuelDetails);
		return mv;
	}

	/**
	 * search Vehicle Fueling
	 * 
	 * @param request
	 * @param vehicleType
	 * @param pumpId
	 * @param fromDate
	 * @param todDate
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "searchVehicleFueling", method = RequestMethod.POST)
	public ModelAndView searchVehicleFueling(final HttpServletRequest request,
			@RequestParam(required = false) Long vehicleType, @RequestParam(required = false) Long pumpId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date todDate, Long orgId) {
		sessionCleanup(request);
		this.getModel().getVehicleFuellingDTO().setVeVetype(vehicleType);
		this.getModel().getVehicleFuellingDTO().setPuId(pumpId);
		this.getModel().getVehicleFuellingDTO().setFromDate(fromDate);
		this.getModel().getVehicleFuellingDTO().setToDate(todDate);
		this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(vehicleType, pumpId,
				fromDate, todDate, UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = new ModelAndView("searchVehicleFuelling", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vendors", getVendorMasterList());
		mv.addObject("vehicles", getVehicleMasterList());
		mv.addObject("pumps", getPumpMasterList());
		return mv;
	}

	/**
	 * serchVehicleNo
	 * 
	 * @param vehicleTypeId
	 * @param httpServletRequest
	 * @return
	 */

	@RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			final HttpServletRequest httpServletRequest) {
		List<VehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId,
				null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				data.put(vdata.getVeId(), vdata.getVeNo());
			});

		}
		return data;
	}

	/**
	 * formForPrint
	 * 
	 * @param model
	 * @param request
	 * @param vefId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "formForPrint", method = RequestMethod.POST)
	public ModelAndView formForPrint(final Model model, final HttpServletRequest request,
			@RequestParam final Long vefId) {
		VehicleFuellingDTO vehicleFuellingDTO = vehicleFuellingService.getVehicleByVehicleId(vefId);
		PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(vehicleFuellingDTO.getPuId());
		vehicleFuellingDTO.setPuPumpname(pumpMasterDTO.getPuPumpname());
		// code added for showing total amount
		vehicleFuellingDTO.getTbSwVehiclefuelDets().forEach(i -> {
			if (i.getVefdCost() != null && i.getVefdQuantity() != null) {
				i.setMulti(i.getVefdCost().multiply(new BigDecimal(i.getVefdQuantity())));
			}

		});

		Map<Long, Long> itemMap = pumpMasterDTO.getTbSwPumpFuldets().stream()
				.collect(Collectors.toMap(PumpFuelDetailsDTO::getPfuId, PumpFuelDetailsDTO::getPuFuid));
		getVendorMasterList();
		Map<Long, String> vendorMap = this.getModel().getVendorList().stream()
				.collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
		vehicleFuellingDTO.setvName(vendorMap.get(pumpMasterDTO.getVendorId()));
		Map<Long, String> locationMap = getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
		vehicleFuellingDTO.getTbSwVehiclefuelDets().forEach(det -> {
			if (det.getVefdUnit() != null && det.getPfuId() != null) {
				det.setVefdUnitDesc(CommonMasterUtility.getCPDDescription(det.getVefdUnit(), MainetConstants.BLANK));
				det.setItemDesc(
						CommonMasterUtility.getCPDDescription(itemMap.get(det.getPfuId()), MainetConstants.BLANK));
			}
		});

		vehicleFuellingDTO.setVeNo(locationMap.get(vehicleFuellingDTO.getVeId()));
		ModelAndView mv = new ModelAndView("VehicleFuellingPrint/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vehicleFuellingDTO", vehicleFuellingDTO);
		mv.addObject("vehicles", getVehicleMasterList());
		return mv;
	}

	/**
	 * last Meter Reading
	 * 
	 * @param vehicleId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = "lastMeterReading")
	public @ResponseBody Long lastMeterReading(Long vehicleId, final HttpServletRequest httpServletRequest) {
		Long lastMeterReading = vehicleFuellingService.getLastMeterReading(vehicleId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		return lastMeterReading != null ? lastMeterReading : 0L;
	}

}
