package com.abm.mainet.adh.ui.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.ADHContractMappingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.service.IAdvertisementContractMappingService;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.adh.ui.model.AdvertisementContractMappingModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 04 November 2019
 */
@Controller
@RequestMapping("/AdvertisementContractMapping.html")
public class AdvertisementContractMappingController extends AbstractFormController<AdvertisementContractMappingModel> {

    @Autowired
    IAdvertisementContractMappingService advertisementContractMappingService;

    @Autowired
    IHoardingMasterService hoardingMasterService;

    /**
     * This method is used to load the summary page
     * 
     * @param request
     * @return summary page
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
	sessionCleanup(request);

	TbDepartment getDeptByCode = ApplicationContextProvider.getApplicationContext()
		.getBean(TbDepartmentService.class)
		.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA,
			MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	this.getModel().setTbDepartment(getDeptByCode);

	List<ContractMappingDTO> findContractDeptWise = advertisementContractMappingService.findContractDeptWise(
		UserSession.getCurrent().getOrganisation().getOrgid(), getDeptByCode, MainetConstants.FlagE);
	this.getModel().setContractMappingList(findContractDeptWise);

	this.getModel()
		.setContractNoList(advertisementContractMappingService.getContNoByDeptIdAndOrgId(
			this.getModel().getTbDepartment().getDpDeptid(),
			UserSession.getCurrent().getOrganisation().getOrgid()));
	return index();

    }

    /**
     * This method is used to load the contract mapping form
     * 
     * @param request
     * @return contract mapping form
     */
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADD_ADH_CONT_MAP, method = RequestMethod.POST)
    public ModelAndView addAdvertisementContractMapping(HttpServletRequest request) {
	sessionCleanup(request);
	this.getModel().bind(request);
	this.getModel().setSaveMode(MainetConstants.FlagA);
	TbDepartment getDeptByCode = ApplicationContextProvider.getApplicationContext()
		.getBean(TbDepartmentService.class)
		.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA,
			MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	this.getModel().setHoardingNoList(hoardingMasterService
		.getHoardingNumberAndIdListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
	this.getModel().setContractNoList(advertisementContractMappingService.getContNoByDeptIdAndOrgId(
		getDeptByCode.getDpDeptid(), UserSession.getCurrent().getOrganisation().getOrgid()));
	this.getModel().setSaveMode(MainetConstants.FlagA);

	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADVERTISE_CONTRACT_MAPPING_FORM,
		MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_HOARD_DET_BY_HOARDID, method = RequestMethod.POST)
    public Map<String, Object> getHoarding(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.HOARDING_ID) Long hoardingId,
	    HttpServletRequest request) {
	getModel().bind(request);
	Map<String, Object> object = new LinkedHashMap<String, Object>();

	HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();
	Long contId = advertisementContractMappingService.getContIdByHoardIdAndOrgId(hoardingId,
		UserSession.getCurrent().getOrganisation().getOrgid());
	if (contId == null || contId == 0) {
	    hoardingMasterDto = hoardingMasterService
		    .getByOrgIdAndHoardingId(UserSession.getCurrent().getOrganisation().getOrgid(), hoardingId);
	    hoardingMasterDto.setDisplayIdDesc(
		    CommonMasterUtility.getNonHierarchicalLookUpObject(hoardingMasterDto.getDisplayTypeId(),
			    UserSession.getCurrent().getOrganisation()).getDescLangFirst());

	    object.put("successFlag", MainetConstants.FlagY);

	    object.put("hoardingDto", hoardingMasterDto);

	} else {
	    object.put("successFlag", MainetConstants.FlagN);
	}

	return object;

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_CONTRACT_BY_CONTNO_OR_CON_DATE, method = RequestMethod.POST)
    public List<ContractMappingDTO> getContractByContNoOrContDate(final HttpServletRequest httpServletRequest,
	    @RequestParam(name = MainetConstants.AdvertisingAndHoarding.CONTRACT_NO, required = false) String contractNo,
	    @RequestParam(name = MainetConstants.AdvertisingAndHoarding.CONT_DATE, required = false) Date contDate) {

	this.getModel().bind(httpServletRequest);
	final List<ContractMappingDTO> list = advertisementContractMappingService.searchContractMappingData(
		UserSession.getCurrent().getOrganisation().getOrgid(), contractNo, contDate,
		getModel().getTbDepartment());
	return list;
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.VIEW_CONTRACT_MAPPING, method = {
	    RequestMethod.POST })
    public ModelAndView viewAdhContractMapping(HttpServletRequest httpServletRequest,
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.CONT_ID) Long contId,
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.SAVE_MODE) String saveMode) {

	this.getModel().bind(httpServletRequest);
	this.getModel().setSaveMode(MainetConstants.FlagV);
	ADHContractMappingDto adhContractDto = advertisementContractMappingService.findByContractId(contId,
		UserSession.getCurrent().getOrganisation().getOrgid());
	this.getModel().setContractMappingDto(adhContractDto);
	this.getModel().setContractMappingList(advertisementContractMappingService.getAdhContractsByContractId(
		UserSession.getCurrent().getOrganisation().getOrgid(), contId, this.getModel().getTbDepartment()));

	return new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADVERTISE_CONTRACT_MAPPING_VALIDN,
		MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.CHECK_DUP_CONTRACT, method = { RequestMethod.POST })
    public String checkDuplicateContract(@RequestParam("contId") Long contId, HttpServletRequest request) {
	Long contractId = advertisementContractMappingService.findContractByContIdAndOrgId(contId,
		UserSession.getCurrent().getOrganisation().getOrgid());
	String flag = MainetConstants.FlagN;
	if (contractId == null || contractId == 0) {
	    flag = MainetConstants.FlagY;

	}

	this.getModel().setDuplicateContractFlag(flag);
	return flag;
    }
}
