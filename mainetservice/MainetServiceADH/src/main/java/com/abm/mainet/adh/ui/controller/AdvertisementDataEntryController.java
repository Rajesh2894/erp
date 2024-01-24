package com.abm.mainet.adh.ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IAdvertisementDataEntryService;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.AdvertisementDataEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author Anwarul.Hassan
 * @since 26-Sep-2019
 */
@Controller
@RequestMapping(MainetConstants.AdvertisingAndHoarding.ADVERTISEMENT_DATA_ENTRY_URL)
public class AdvertisementDataEntryController extends AbstractFormController<AdvertisementDataEntryModel> {
	@Autowired
	private ILocationMasService locationMasService;
	@Autowired
	private IAdvertiserMasterService advertiserMasterService;
	@Autowired
	private IAdvertisementDataEntryService advertisementDataEntryService;
	
	@Autowired
	ILicenseValidityMasterService licenseValidityMasterService;
	
	@Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;


	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		List<NewAdvertisementApplicationDto> applicationDtoList = ApplicationContextProvider.getApplicationContext()
				.getBean(IAdvertisementDataEntryService.class)
				.getAdvertisementDetailsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		if (!applicationDtoList.isEmpty()) {
			applicationDtoList.forEach(applicationDto -> {
				NewAdvertisementApplicationDto dtoList = new NewAdvertisementApplicationDto();
				BeanUtils.copyProperties(applicationDto, dtoList);
				LookUp licTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dtoList.getLicenseType(),
						UserSession.getCurrent().getOrganisation());
				dtoList.setLicenseTypeDesc(licTypeLookUp.getLookUpDesc());
				String locationNameById = locationMasService.getLocationNameById(dtoList.getLocId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				dtoList.setLocIdDesc(locationNameById);
				this.getModel().getAdvertisementDtoList().add(dtoList);
			});
		}
		this.getModel().setAdvertiserMasterDtoList(advertiserMasterService
				.getAllAdvertiserMasterByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbLocationMas> locationList = locationMasService.getlocationByDeptId(
				serviceMaster.getTbDepartment().getDpDeptid(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
		this.getModel().setLocationList(locationList);

		this.getModel()
				.setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
						.calculateLicMaxTenureDays(serviceMaster.getTbDepartment().getDpDeptid(),
								serviceMaster.getSmServiceId(), null,
								UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.ZERO_LONG));

		return index();
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADD_DATA_ENTRY, method = RequestMethod.POST)
	public ModelAndView addAdvertisementEntry(HttpServletRequest request) {
		this.getModel().setSaveMode(MainetConstants.FlagA);
		bindModel(request);
		this.getModel().setAdvertiserMasterDtoList(advertiserMasterService.getAgencyDetailsByOrgIdAndStatus(
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA));
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_DATA_ENTRY, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_DATA_ENTRY, method = RequestMethod.POST)
	public List<NewAdvertisementApplicationDto> searchAdvtDataEntry(
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.AGENCY_ID, required = false) Long agencyId,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.LIC_TYPE, required = false) Long licenseType,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.ADH_STATUS, required = false) String adhStatus,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.LOCID, required = false) Long locId,
			@RequestParam(value = "licenseFromDate", required = false) String licenseFromDate,
			@RequestParam(value = "licenseToDate", required = false) String licenseToDate,
			HttpServletRequest request) {
		bindModel(request);
		Date licenFromDate=UtilityService.convertStringDateToDateFormat(licenseFromDate);
		Date licenToDate=UtilityService.convertStringDateToDateFormat(licenseToDate);
		List<NewAdvertisementApplicationDto> advertisementDtoList = advertisementDataEntryService.searchAdvtDataEntry(
				UserSession.getCurrent().getOrganisation().getOrgid(), agencyId, licenseType, adhStatus, locId,licenFromDate,licenToDate);
		this.getModel().setAdvertiserMasterDtoList(advertiserMasterService
				.getAllAdvertiserMasterByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		if (CollectionUtils.isNotEmpty(advertisementDtoList)) {
			for (NewAdvertisementApplicationDto dtoList : advertisementDtoList) {
				dtoList.setAgencyName(advertiserMasterService.getAgencyNameByAgnIdAndOrgId(dtoList.getAgencyId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
				LookUp licTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dtoList.getLicenseType(),
						UserSession.getCurrent().getOrganisation());
				dtoList.setLicenseTypeDesc(licTypeLookUp.getLookUpDesc());
				String locationNameById = locationMasService.getLocationNameById(dtoList.getLocId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				dtoList.setLocIdDesc(locationNameById);
				/*dtoList.setLicenseFromDate(UtilityService.convertStringToDDMMMYYYYDate(UtilityService.convertDateToDDMMYYYY(dtoList.getLicenseFromDate())));
				dtoList.setLicenseToDate(UtilityService.convertStringToDDMMMYYYYDate(UtilityService.convertDateToDDMMYYYY(dtoList.getLicenseToDate())));*/
				this.getModel().getAdvertisementDtoList().add(dtoList);
			}
		}
		return advertisementDtoList;
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
	public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {
		List<AdvertiserMasterDto> advertiserMasterList = advertiserMasterService
				.getAgencyDetailsByAgencyCategoryAndOrgId(advertiserCategoryId,
						UserSession.getCurrent().getOrganisation().getOrgid());
		return advertiserMasterList;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LOCATION_MAPPING, method = RequestMethod.POST)
	public String getLocationMapping(
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.LOCATION_ID, required = false) Long locationId) {
		String response = MainetConstants.BLANK;
		LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(locationId,
				this.getModel().getDeptId());
		if (wzMapping != null) {
			response = "Y";
		} else {
			response = "N";
		}
		return response;
	}

	@RequestMapping(params = MainetConstants.EDIT, method = RequestMethod.POST)
	public ModelAndView editOrViewAdvDataEntry(@RequestParam(MainetConstants.AdvertisingAndHoarding.ADH_ID) Long adhId,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode) {
		this.getModel().setSaveMode(saveMode);
		NewAdvertisementApplicationDto applicationDto = advertisementDataEntryService
				.getAdvertisementDetailsByOrgIdAndAdhId(adhId, UserSession.getCurrent().getOrganisation().getOrgid());
		if (applicationDto != null) {
			this.getModel().setAdvertisementDto(applicationDto);
		}
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String LicenseNo = applicationDto.getLicenseNo();
		if(LicenseNo != null) {
		NewAdvertisementReqDto advertisementReqDto = newAdvApplicationService.getAdvertisementApplicationByLicenseNo(LicenseNo,
                orgId);
		this.getModel().setAdvertisementReqDto(advertisementReqDto);
		}
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_DATA_ENTRY, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/* Defect #79106 */
	@RequestMapping(method = RequestMethod.POST, params = { "doDeletion" })
	public void doItemDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		AdvertisementDataEntryModel model = this.getModel();

		List<NewAdvertisementApplicationDetDto> item = this.getModel().getAdvertisementDto().getNewAdvertDetDtos();
		if(id<item.size()) {
			item.remove(id);
		}
		this.getModel().getAdvertisementDto().setNewAdvertDetDtos(item);

	}
	
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody Long getLicMaxTenureDays(@RequestParam("licType") Long licType,@RequestParam( value="licenseFromDate" ,required = false) String licenseFromDate,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Date date=null;
		if(licenseFromDate != null)
		date=UtilityService.convertStringDateToDateFormat(licenseFromDate);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());

		Long calculateLicMaxTenureDays = 0l;
		if(licenseFromDate != null) {
		calculateLicMaxTenureDays = ApplicationContextProvider.getApplicationContext()
				.getBean(IAdvertisementDataEntryService.class).calculateLicMaxTenureDays(
						serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmServiceId(), date,
						UserSession.getCurrent().getOrganisation().getOrgid(), licType);
		}else{
		calculateLicMaxTenureDays=ApplicationContextProvider.getApplicationContext()
		.getBean(IAdvertisementDataEntryService.class).calculateLicMaxTenureDays(
				serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmServiceId(), null,
				UserSession.getCurrent().getOrganisation().getOrgid(), licType);
		}
		this.getModel().setLicMaxTenureDays(calculateLicMaxTenureDays);
		return calculateLicMaxTenureDays;
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService.searchLicenseValidityData(
				UserSession.getCurrent().getOrganisation().getOrgid(), serviceMaster.getTbDepartment().getDpDeptid(),
				serviceMaster.getSmServiceId(), MainetConstants.ZERO_LONG, licType);
		LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				licValMasterDtoList.get(0).getLicDependsOn(), UserSession.getCurrent().getOrganisation());
		String YearType = dependsOnLookUp.getLookUpCode();
		return YearType;
	}
}
