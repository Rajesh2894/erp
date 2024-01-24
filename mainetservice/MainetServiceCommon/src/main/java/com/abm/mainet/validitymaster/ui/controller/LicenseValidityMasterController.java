package com.abm.mainet.validitymaster.ui.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.abm.mainet.validitymaster.ui.model.LicenseValidityMasterModel;
import com.aspose.slides.Collections.ArrayList;

/**
 * @author cherupelli.srikanth
 * @since 16 september 2019
 */
@Controller
@RequestMapping("/LicenseValidityMaster.html")
public class LicenseValidityMasterController extends AbstractFormController<LicenseValidityMasterModel> {

	private static final Logger LOGGER = Logger.getLogger(LicenseValidityMasterController.class);
	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	TbServicesMstService serviceMaster;

	@Autowired
	ILicenseValidityMasterService licenseValidityMasterService;

	/**
	 * This method is used to load summary page of License validity form
	 * 
	 * @param request
	 * @return summary page of License validity form
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		List<TbDepartment> deparmentList = tbDepartmentService
				.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(deparmentList)) {
			this.getModel().setDeparatmentList(deparmentList);
		}
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService
				.searchLicenseValidityData(UserSession.getCurrent().getOrganisation().getOrgid(), 0l, 0l,0l,MainetConstants.ZERO_LONG);

		for (LicenseValidityMasterDto masterDto : licValMasterDtoList) {

			LicenseValidityMasterDto licMasterDto = new LicenseValidityMasterDto();
			BeanUtils.copyProperties(masterDto, licMasterDto);

			LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getLicDependsOn(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setLicDependsOnDesc(dependsOnLookUp.getLookUpDesc());
			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getUnit(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setUnitDesc(unitLookUp.getLookUpDesc());
			LookUp licTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getLicType(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setLicTypeDesc(licTypeLookUp.getLookUpDesc());
			licMasterDto.setDeptName(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.fetchDepartmentDescById(masterDto.getDeptId()));
			licMasterDto.setServiceName(serviceMaster.findServiceNameById(masterDto.getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid()));

			this.getModel().getMasterDtoList().add(licMasterDto);
		}

		return index();
	}

	/**
	 * This method is used to return the list of services based on the department id
	 * 
	 * @param deptId
	 * @param request
	 * @return list of services
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_SERVICES_BY_DEPTID, method = {
			RequestMethod.POST })
	@ResponseBody
	public List<TbServicesMst> searchServiceByDeptId(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			HttpServletRequest request) {

		List<TbServicesMst> serviceList = serviceMaster.findByDeptId(deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setServiceList(serviceList);
		return this.getModel().getServiceList();

	}

	/**
	 * This method is used to load license validity entry from
	 * 
	 * @param request
	 * @return loads license validity entry from
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADD_LICENSE_VALIDITY_ENTRY, method = {
			RequestMethod.POST })
	public ModelAndView addLicenseValidityEntry(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().bind(request);
		List<TbDepartment> deparmentList = tbDepartmentService
				.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(deparmentList)) {
			this.getModel().setDeparatmentList(deparmentList);
		}
		this.getModel().setSaveMode(MainetConstants.FlagA);
		try {
		/*	List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.TradeLicense.TRD_ENV,
					UserSession.getCurrent().getOrganisation());*/
			Organisation org = UserSession.getCurrent().getOrganisation();
			//#142368 category required for both skdcl and tscl project
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
				this.getModel().setEnvSpec(MainetConstants.FlagY);
			}
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 1,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setTriCodList1(lookUpList);
		} catch (Exception e) {
			LOGGER.info("TEV  Prefix Not found");
		}

		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_LICENSE_VALIDITY_ENTRY,
				MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_LICENSE_VALIDITY_DATA, method = {
			RequestMethod.POST })
	public List<LicenseValidityMasterDto> searchLicenseValidityMasterData(
			@RequestParam(value = MainetConstants.Common_Constant.DEPTID, required = false) Long deptId,
			@RequestParam(value = MainetConstants.Common_Constant.SERVICEID, required = false) Long serviceId,
			HttpServletRequest request) {
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService
				.searchLicenseValidityData(UserSession.getCurrent().getOrganisation().getOrgid(), deptId, serviceId,MainetConstants.ZERO_LONG,MainetConstants.ZERO_LONG);

		List<LicenseValidityMasterDto> masterDtoList = new ArrayList();
		for (LicenseValidityMasterDto masterDto : licValMasterDtoList) {

			LicenseValidityMasterDto licMasterDto = new LicenseValidityMasterDto();
			BeanUtils.copyProperties(masterDto, licMasterDto);
			LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getLicDependsOn(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setLicDependsOnDesc(dependsOnLookUp.getLookUpDesc());
			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getUnit(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setUnitDesc(unitLookUp.getLookUpDesc());
			LookUp licTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterDto.getLicType(),
					UserSession.getCurrent().getOrganisation());
			licMasterDto.setLicTypeDesc(licTypeLookUp.getLookUpDesc());
			licMasterDto.setDeptName(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.fetchDepartmentDescById(masterDto.getDeptId()));
			licMasterDto.setServiceName(serviceMaster.findServiceNameById(masterDto.getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid()));
			masterDtoList.add(licMasterDto);

		}
		this.getModel().setMasterDtoList(masterDtoList);
		return this.getModel().getMasterDtoList();

	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.EDIT_OR_VIEW_LICENSE_VALIDITY, method = {
			RequestMethod.POST })
	public ModelAndView editOrViewLicenseValidityMaster(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.LIC_ID) Long licId,
			@RequestParam(MainetConstants.AdvertisingAndHoarding.SAVE_MODE) String saveMode,
			HttpServletRequest request) {
		LicenseValidityMasterDto licValidityMasterDto = licenseValidityMasterService
				.searchLicenseValidityByLicIdAndOrgId(licId, UserSession.getCurrent().getOrganisation().getOrgid());
		if (licValidityMasterDto != null) {
			this.getModel().setMasterDto(licValidityMasterDto);
			List<TbServicesMst> serviceList = serviceMaster.findByDeptId(licValidityMasterDto.getDeptId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setServiceList(serviceList);
		}
		//Code added for SKDCL ENV to showing License Category and sub-category in in License ValidityMaster Form in SKDCL User Story #107219
		try {
			/*List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.TradeLicense.TRD_ENV,
					UserSession.getCurrent().getOrganisation());*/
			Organisation org = UserSession.getCurrent().getOrganisation();
			//#142368 category required for both skdcl and tscl project
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
				this.getModel().setEnvSpec(MainetConstants.FlagY);
			}
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
				this.getModel().setDeptShortName(ApplicationContextProvider.getApplicationContext()
						.getBean(DepartmentService.class).getDeptCode(this.getModel().getMasterDto().getDeptId()));
				List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 1,
						UserSession.getCurrent().getOrganisation());

				List<LookUp> lookUpListSub = CommonMasterUtility.getLevelData("ITC", 2,
						UserSession.getCurrent().getOrganisation());
				this.getModel()
						.setTriCodList2(lookUpListSub.stream()
								.filter(lookUp -> lookUp.getLookUpParentId() == licValidityMasterDto.getTriCod1())
								.collect(Collectors.toList()));
				this.getModel().setTriCodList1(lookUpList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("TEV Prefix Not Found--->inside method  editOrViewLicenseValidityMaster()");
		}
		//end

		this.getModel().setSaveMode(saveMode);

		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_LICENSE_VALIDITY_ENTRY,
				MainetConstants.FORM_NAME, this.getModel());

	}

	/**
	 * This method is used to return the list of services based on the department id
	 * 
	 * @param deptId
	 * @param request
	 * @return list of services
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_LIC_SUB_CATAGORY_BY_CATAGORY_ID, method = {
			RequestMethod.POST })
	@ResponseBody
	public List<LookUp> searchLicenseSubCatagory(@RequestParam(MainetConstants.Common_Constant.TRI_CODE1) Long triCode1,
			HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == triCode1)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			// TODO: handle exception
            LOGGER.info("ITC Prefix Level -2 data not found inside searchLicenseSubCatagory method");
			return lookUpList1;

		}
	}

	@RequestMapping(params = "getDepartmentShortName", method = { RequestMethod.POST })
	@ResponseBody
	public String getDepartmentShortName(@RequestParam("deptId") Long deptId, HttpServletRequest request) {
		String deptShortCode = null;
		if (deptId != null) {
			deptShortCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(deptId);
			this.getModel().setDeptShortName(deptShortCode);
			return deptShortCode;
		}
		return deptShortCode;
	}
}
