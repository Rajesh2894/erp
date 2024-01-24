package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.LicenseLetterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AdvertiserMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */

@Controller
@RequestMapping("/AdvertiserMaster.html")
public class AdvertiserMasterController extends AbstractFormController<AdvertiserMasterModel> {

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;
    
    @Resource
	private TbFinancialyearService tbFinancialyearService;

    /**
     * This method is used to load index page of Advertiser Master
     * 
     * @param request
     * @return Advertiser Master Summary Page
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
	sessionCleanup(request);
	List<AdvertiserMasterDto> advertiserMasterDtoList = advertiserMasterService.searchAdvertiserMasterData(
		UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null);

	if (CollectionUtils.isNotEmpty(advertiserMasterDtoList)) {
	    List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();

	    advertiserMasterDtoList.forEach(masterDto -> {
		if (StringUtils.isNotBlank(masterDto.getAgencyLicNo())) {
		    masterDtoList.add(masterDto);
		}
	    });

	    this.getModel().getAdvertiserMasterDto().setApplicationTypeFlag(MainetConstants.FlagM);
	    this.getModel().setAdvertiserMasterDtoList(masterDtoList);
	}
	return index();

    }

    /**
     * This Method is used For add Advertising Master
     * 
     * @param request
     * @return Advertiser Master Entry Form
     */
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADD_ADVERTISER_MASTER, method = RequestMethod.POST)
    public ModelAndView addAdvertiserMaster(HttpServletRequest request) {
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
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        request.setAttribute("aFinancialYr", faYears);
	this.getModel().setSaveMode(MainetConstants.FlagA);
	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_ADVERTISER_MASTER, MainetConstants.FORM_NAME,
		this.getModel());

    }

    /**
     * This Method is used For search Advertiser Master
     * 
     * @param advertiserNumber
     * @param advertiserOldNumber
     * @param advertiserName
     * @param advertiserStatus
     * @param request
     * @return AdvertiserMasterDto list
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_ADVERTISER_MASTER, method = RequestMethod.POST)
    public List<AdvertiserMasterDto> searchAdvertiserMaster(
	    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ADVERTISER_NUMBER, required = false) String advertiserNumber,
	    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ADVERTISER_OLD_NUMBER, required = false) String advertiserOldNumber,
	    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ADVERTISER_NAME, required = false) String advertiserName,
	    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ADVERTISER_STATUS, required = false) String advertiserStatus,
	    HttpServletRequest request) {
	List<AdvertiserMasterDto> masterDtoList = advertiserMasterService.searchAdvertiserMasterData(
		UserSession.getCurrent().getOrganisation().getOrgid(), advertiserNumber, advertiserOldNumber,
		advertiserName, advertiserStatus);
	if (CollectionUtils.isNotEmpty(masterDtoList)) {
	    this.getModel().setAdvertiserMasterDtoList(masterDtoList);
	}
	return masterDtoList;

    }

    /**
     * This method is used to edit or view Advertiser Entry Form
     * 
     * @param agencyId
     * @param saveMode
     * @param request
     * @return returns Advertiser Master Entry Form for edit and view
     */
    @RequestMapping(params = MainetConstants.EDIT, method = RequestMethod.POST)
    public ModelAndView editOrViewAdvertiserMaster(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.AGENCY_ID) Long agencyId,
	    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode,
	    HttpServletRequest request) {
	this.getModel().setSaveMode(saveMode);
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
                //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
            }
        });
    }
    request.setAttribute("aFinancialYr", faYears);
	AdvertiserMasterDto masterDto = advertiserMasterService
		.getAdvertiserMasterByOrgidAndAgencyId(UserSession.getCurrent().getOrganisation().getOrgid(), agencyId);

    this.getModel().setFayear(tbFinancialyearService.getFinanciaYearIdByFromDate(masterDto.getAgencyLicFromDate()));
	if (masterDto != null) {
	    this.getModel().setAdvertiserMasterDto(masterDto);
	}
	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADD_ADVERTISER_MASTER, MainetConstants.FORM_NAME,
		this.getModel());

    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.PRINT_AGENCY_LICENSE_LETTER, method = {
	    RequestMethod.POST })
    public ModelAndView printAgencyLicenseLetter(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.AGENCY_ID) Long agencyId, HttpServletRequest request) {
	List<AdvertiserMasterDto> masterDtoList = advertiserMasterService.getAllLicPeriodDetails(agencyId,
		UserSession.getCurrent().getOrganisation().getOrgid());

	if (CollectionUtils.isNotEmpty(masterDtoList)) {

	    List<LicenseLetterDto> licLetterDtoList = new ArrayList<>();
	    AdvertiserMasterDto advertiserMasterDto = masterDtoList.get(masterDtoList.size() - 1);
	    advertiserMasterDto.setAgencyRegisDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
		    .format(advertiserMasterDto.getAgencyLicIssueDate()));

	    this.getModel().setAdvertiserMasterDto(advertiserMasterDto);

	    long count = 1;
	    for (int i = 0; i < masterDtoList.size(); i++) {
		AdvertiserMasterDto secondLicPeriod = masterDtoList.get(i);

		LicenseLetterDto licDto = new LicenseLetterDto();
		licDto.setsNo(count);
		licDto.setLicFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
			.format(secondLicPeriod.getAgencyLicFromDate()));
		licDto.setLicToDate(
			new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(secondLicPeriod.getAgencyLicToDate()));
		if (StringUtils.equals(secondLicPeriod.getAgencyStatus(), MainetConstants.FlagA)
			&& StringUtils.isNotBlank(secondLicPeriod.getAgencyLicNo())) {
		    count = count + 1;
		    licLetterDtoList.add(licDto);
		}

	    }

	    this.getModel().setLicenseDto(licLetterDtoList);
	}

	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.PRINT_AGENCY_LIC_LETTER,
		MainetConstants.FORM_NAME, this.getModel());
    }
}
