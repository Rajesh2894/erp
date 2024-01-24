package com.abm.mainet.swm.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart1DetailDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.VendorContractMappingDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.IVendorContractMappingService;
import com.abm.mainet.swm.ui.model.ContractMappingModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/ContractMapping.html")
public class ContractMappingController extends AbstractFormController<ContractMappingModel> {

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private IVendorContractMappingService vendorContractMappingService;

	@Autowired
	private TbAcVendormasterService vendorService;

	@Autowired
	private IContractAgreementService contractAgreementService;

	@Autowired
	private IBeatMasterService routeMasterService;

	/**
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
				MainetConstants.SolidWasteManagement.SHORT_CODE);
		final List<ContractMappingDTO> list = vendorContractMappingService.findContractDeptWise(orgId, tbDepartment,
				MainetConstants.CommonConstants.BLANK);
		this.getModel().setContractMappingDTOList(list);
		setVenderDetails();
		this.getModel().setCommonHelpDocs("ContractMapping.html");
		return index();
	}

	// Populates the list of vendors
	/**
	 * setVenderDetails
	 */
	private void setVenderDetails() {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final Integer languageId = UserSession.getCurrent().getLanguageId();
		List<TbAcVendormaster> vendorList = new ArrayList<TbAcVendormaster>();
		final LookUp lookUpVendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		if (vendorStatus != null) {
			vendorList = vendorService.getActiveVendors(org.getOrgid(), vendorStatus);
		}
		if (CollectionUtils.isNotEmpty(vendorList)) {
			Map<Long, String> vendorMaps = vendorList.stream()
					.collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
			if (CollectionUtils.isNotEmpty(this.getModel().getContractMappingDTOList())) {
				this.getModel().getContractMappingDTOList().forEach(master -> {
					if (vendorMaps != null && StringUtils.isNotEmpty(master.getVendorName())) {
						master.setVendorName(vendorMaps.get(Long.valueOf(master.getVendorName())));
					}
				});
			}

		}
	}

	/**
	 * add Contract Mapping
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "AddContractMapping", method = RequestMethod.POST)
	public ModelAndView addContractMapping(final HttpServletRequest request) {
		this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.ADD);
		sessionCleanup(request);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
				MainetConstants.SolidWasteManagement.SHORT_CODE);
		setUnmappedContract(orgId, tbDepartment);
		this.getModel().setRouteList(routeMasterService.getAllRouteNo(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("ContractMappingForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * set Unmapped Contract
	 * 
	 * @param orgId
	 * @param tbDepartment
	 */
	private void setUnmappedContract(final Long orgId, final TbDepartment tbDepartment) {
		final List<ContractMappingDTO> list = vendorContractMappingService.findContractDeptWise(orgId, tbDepartment,
				MainetConstants.CommonConstants.E);
		this.getModel().setContractMappingDTOList(list);
	}

	/**
	 * View Contract Mapping
	 * 
	 * @param request
	 * @param contId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "ViewContractMapping", method = RequestMethod.POST)
	public ModelAndView ViewContractMapping(final HttpServletRequest request, @RequestParam Long contId) {
		sessionCleanup(request);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
				MainetConstants.SolidWasteManagement.SHORT_CODE);
		this.getModel().setContractMappingDTO(
				vendorContractMappingService.findContractsByContractId(orgId, contId, tbDepartment).get(0));
		this.getModel().setVendorContractMappingList(vendorContractMappingService.getById(contId, orgId));
		String vendorid = this.getModel().getContractMappingDTO().getVendorName();
		if(StringUtils.isNotEmpty(vendorid)) {
		final String vendorDescription = vendorService.getVendorNameById(Long.valueOf(vendorid), orgId);
		this.getModel().getContractMappingDTO().setVendorName(vendorDescription);
		}
		this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.VIEW);
		this.getModel()
				.setRouteList(routeMasterService.getAllRouteNo(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("ContractMappingForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(params = "contractMappingForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		return new ModelAndView("ContractMappingForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * search Contract Mapping
	 * 
	 * @param request
	 * @param contid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "searchContractMapping", method = RequestMethod.POST)
	public ModelAndView searchContractMapping(final HttpServletRequest request, @RequestParam Long contid) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.ADD);
		this.getModel().getVendorContractMappingDTO().setContId(contid);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
				MainetConstants.SolidWasteManagement.SHORT_CODE);
		this.getModel().setContractMappingDTO(
				vendorContractMappingService.findContractsByContractId(orgId, contid, tbDepartment).get(0));
		this.getModel().setContractMappingDTOList(
				vendorContractMappingService.findContractsByContractId(orgId, contid, tbDepartment));
		String vendorid = this.getModel().getContractMappingDTO().getVendorName();
		if (vendorid != null) {
			final String vendorDescription = vendorService.getVendorNameById(Long.valueOf(vendorid), orgId);
			this.getModel().getContractMappingDTO().setVendorName(vendorDescription);
		}
		this.getModel()
				.setRouteList(routeMasterService.getAllRouteNo(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("ContractMappingForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * delete Contract Mapping
	 * 
	 * @param request
	 * @param contractNo
	 * @param contDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "filter", method = RequestMethod.POST)
	public ModelAndView deleteContractMapping(final HttpServletRequest request, @RequestParam String contractNo,
			@RequestParam Date contDate) {
		sessionCleanup(request);
		this.getModel().getContractMappingDTO().setContractNo(contractNo);
		this.getModel().getContractMappingDTO().setContDate(Utility.dateToString(contDate));
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
				MainetConstants.SolidWasteManagement.SHORT_CODE);
		this.getModel().setContractMappingDTOList(
				vendorContractMappingService.findContract(orgId, contractNo, contDate, tbDepartment));
		setVenderDetails();
		return new ModelAndView("ContractMappingSummary", MainetConstants.FORM_NAME, this.getModel());

	}

	/**
	 * print Contract Mapping
	 * 
	 * @param request
	 * @param contid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "print", method = RequestMethod.POST)
	public ModelAndView printContractMapping(final HttpServletRequest request, @RequestParam Long contid) {
		sessionCleanup(request);
		VendorContractMappingDTO service = null;
		List<VendorContractMappingDTO> serviceList = new ArrayList<>();
		List<VendorContractMappingDTO> vendorServiceList = vendorContractMappingService.getById(contid,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (VendorContractMappingDTO vendorContractList : vendorServiceList) {
			service = new VendorContractMappingDTO();
			if (vendorContractList.getMapTaskId() != null) {
				service.setMapTaskId(vendorContractList.getMapTaskId().toString());
				service.setServices(
						CommonMasterUtility.getCPDDescription(Long.valueOf(vendorContractList.getMapTaskId()), ""));
			}
			serviceList.add(service);
		}
		this.getModel().setVendorContractMappingList(serviceList);
		ContractMastDTO contractMastDTO = new ContractMastDTO();
		contractMastDTO = contractAgreementService.findById(contid,
				UserSession.getCurrent().getOrganisation().getOrgid());
		// vendor Details
		Long vendorId = 0L;
		for (ContractPart2DetailDTO vendorlist1 : contractMastDTO.getContractPart2List()) {
			vendorId = vendorlist1.getVmVendorid();
			this.getModel().getVendorContractMappingDTO().setVendorNam(vendorlist1.getVenderName());
			break;
		}
		final List<TbAcVendormasterEntity> vendorList2 = vendorService
				.getVendorCodeByVendorId(UserSession.getCurrent().getOrganisation().getOrgid(), vendorId);
		for (TbAcVendormasterEntity list2 : vendorList2) {
			this.getModel().getVendorContractMappingDTO().setVendorAddress(list2.getVmVendoradd());
		}
		// Represented Details
		Long desigId = 0L;
		for (ContractPart1DetailDTO replist1 : contractMastDTO.getContractPart1List()) {
			this.getModel().getVendorContractMappingDTO().setRepresentedBy(replist1.getContp1Name());
			desigId = replist1.getDsgid();
			break;
		}
		String desigName = vendorContractMappingService.findDesignationById(desigId);
		this.getModel().getVendorContractMappingDTO().setDesignation(desigName);
		for (ContractDetailDTO contractDetList : contractMastDTO.getContractDetailList()) {
			this.getModel().getVendorContractMappingDTO()
					.setContractToDate(Utility.dateToString(contractDetList.getContToDate()));
			this.getModel().getVendorContractMappingDTO()
					.setContractFromDate(Utility.dateToString(contractDetList.getContFromDate()));
			if (contractDetList.getContAmount() != null) {
				this.getModel().getVendorContractMappingDTO().setContractAmount(
						new BigDecimal(contractDetList.getContAmount().toString()).setScale(2, RoundingMode.HALF_EVEN));
			} else {
				this.getModel().getVendorContractMappingDTO()
						.setContractAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
			}
			break;
		}
		// Witness and Representative
		VendorContractMappingDTO contractDTO = null;
		List<VendorContractMappingDTO> contractList = new ArrayList<VendorContractMappingDTO>();
		for (ContractPart1DetailDTO witnesList1 : contractMastDTO.getContractPart1List()) {
			contractDTO = new VendorContractMappingDTO();
			String W = "W";
			if (witnesList1.getContp1Type().equals(W)) {
				contractDTO.setWitnessName(witnesList1.getContp1Name());
				contractList.add(contractDTO);
			}
		}
		this.getModel().setUlbWitnessMappingList(contractList);
		// Vendor and Representative
		VendorContractMappingDTO vendorDTO = null;
		List<VendorContractMappingDTO> vendorList = new ArrayList<VendorContractMappingDTO>();
		for (ContractPart2DetailDTO vendorList1 : contractMastDTO.getContractPart2List()) {
			vendorDTO = new VendorContractMappingDTO();
			String W = "W";
			if (vendorList1.getContp2Type().equals(W)) {
				vendorDTO.setVendorWitness(vendorList1.getContp2Name());
				vendorList.add(vendorDTO);
			}
		}
		this.getModel().setVendorWitnessMappingList(vendorList);
		this.getModel().getVendorContractMappingDTO().setTendorNo(contractMastDTO.getContTndNo());
		this.getModel().getVendorContractMappingDTO().setResolutionNo(contractMastDTO.getContRsoNo());
		this.getModel().getVendorContractMappingDTO().setResolutionDate(contractMastDTO.getResoDate());
		return new ModelAndView("ContractMappingPrint", MainetConstants.FORM_NAME, this.getModel());

	}

}
