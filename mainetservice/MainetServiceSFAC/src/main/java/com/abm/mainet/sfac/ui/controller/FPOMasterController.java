package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.StateInformationDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.FarmerMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.service.StateInformationMasterService;
import com.abm.mainet.sfac.ui.model.FPOMasterModel;

@Controller
@RequestMapping("FPOMasterForm.html")
public class FPOMasterController extends AbstractFormController<FPOMasterModel> {

	private static final Logger logger = Logger.getLogger(FPOMasterController.class);

	@Autowired
	private BankMasterService bankMasterService;

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	private StateInformationMasterService stateInfoMastService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private FPOMasterService fPOMasterService;

	@Autowired
	private FarmerMasterService farmerMasterService;

	@Autowired
	private IOrganisationService orgService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		// this.getModel().setMasterDtoList(fPOMasterService.findFPOByIds(UserSession.getCurrent().getEmployee().getMasId()));

		this.getModel()
				.setFpoMasterDtoList(fPOMasterService.findFpoByMasId(UserSession.getCurrent().getEmployee().getMasId(),
						UserSession.getCurrent().getOrganisation().getOrgShortNm(),
						UserSession.getCurrent().getEmployee().getEmploginname()));

		List<CBBOMasterDto> masterDtoList = cbboMasterService
				.findAllIaAssociatedWithCbbo(UserSession.getCurrent().getEmployee().getEmploginname());
		this.getModel().setIaNameList(masterDtoList);
		if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.IA))
			this.getModel().getDto().setIaName(UserSession.getCurrent().getEmployee().getEmpname());
		return new ModelAndView("FPOMasterSummaryForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "bankCode", method = RequestMethod.POST)
	public @ResponseBody List<BankMasterEntity> getBankCode(@RequestParam("cbBankCode") final String bankName) {
		final List<BankMasterEntity> details = bankMasterService.getBankListByName(bankName);
		this.getModel().setBanks(details);
		return details;
	}

	@RequestMapping(params = "getBranch", method = RequestMethod.POST)
	public @ResponseBody String getBranch(@RequestParam("cbBankCode") final Long bankId) {
		final String details = bankMasterService.getBankBranchName(bankId);

		return details;
	}

	@RequestMapping(params = "getAgeOfFPO", method = RequestMethod.POST)
	public @ResponseBody Long getFPOAgeByRegDate(@RequestParam("dateIncorporation") final Date dateIncorporation) {
		Long days = (long) Utility.getDaysBetDates(dateIncorporation, new Date());
		return days;
	}

	@ResponseBody
	@RequestMapping(params = "getStateInfoByDistId", method = RequestMethod.POST)
	public StateInformationDto getStateInfoByDistId(HttpServletRequest request, @RequestParam("sdb2") Long sdb2) {
		StateInformationDto dto = new StateInformationDto();
		try {
			logger.info("getStateInfoByDistId Started");
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
			dto = stateInfoMastService.getStateInfoByDistId(sdb2, org.getOrgid());
		} catch (Exception e) {
			logger.error("Error occured while fetching state inforamtion in getStateInfoByDistId " + e);
		}
		logger.info("getStateInfoByDistId Started");
		return dto;
	}

	@ResponseBody
	@RequestMapping(params = "checkSpecialCateExist", method = RequestMethod.POST)
	public Boolean checkSpecialCateExist(HttpServletRequest request, @RequestParam("sdb3") Long sdb3) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		LookUp lookup = CommonMasterUtility.getHieLookupByLookupCode("SPC", "ALC", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		boolean result = fPOMasterService.checkSpecialCateExist(sdb3, lookup.getLookUpId());
		return result;
	}

	@ResponseBody
	@RequestMapping(params = "getTypeOFPromotinAgency", method = RequestMethod.POST)
	public Long getTypeOFPromotinAgency(HttpServletRequest request, @RequestParam("cbboId") Long cbboId) {
		Long typeOfPromotionId = cbboMasterService.fetchPromotionAgnByCbboId(cbboId);
		this.getModel().getDto().setTypeofPromAgen(typeOfPromotionId);
		return typeOfPromotionId;
	}

	@ResponseBody
	@RequestMapping(params = "getIaALlocationYear", method = RequestMethod.POST)
	public Long getIaALlocationYear(HttpServletRequest request, @RequestParam("iaId") Long iaId) {
		Long iaAlcYear = iaMasterService.getIaALlocationYear(iaId);
		this.getModel().getDto().setIaAlcYear(iaAlcYear);
		return iaAlcYear;
	}

	private void populateModel() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		this.getModel().setBankName(bankMasterService.getBankListGroupBy());
		List<CBBOMasterDto> masterDtoList = cbboMasterService
				.findAllIaAssociatedWithCbbo(UserSession.getCurrent().getEmployee().getEmploginname());
		this.getModel().setIaNameList(masterDtoList);
		this.getModel().setDesignlist(
				designationService.findDesgByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));
		LookUp femaleLookup = CommonMasterUtility.getValueFromPrefixLookUp("F", "GEN", org);
		LookUp maleLookUp = CommonMasterUtility.getValueFromPrefixLookUp("M", "GEN", org);
		boolean result = farmerMasterService.checkWomenCentric(femaleLookup.getLookUpId(), maleLookUp.getLookUpId(),
				UserSession.getCurrent().getEmployee().getEmpId());
		final List<LookUp> allcCatgList = CommonMasterUtility.getLevelData("ALC", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setAllocationCatgList(allcCatgList);
		this.getModel().setWomenCentric(result);
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		/*
		 * String cbboName = UserSession.getCurrent().getEmployee().getEmpname() +
		 * MainetConstants.WHITE_SPACE +
		 * UserSession.getCurrent().getEmployee().getEmplname();
		 */
		this.getModel().getDto().setCbboName(UserSession.getCurrent().getEmployee().getEmpname());
		List<LookUp> looks = CommonMasterUtility.getNextLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().getDto().setStateList(looks);
		this.getModel().setShowUdyogDet(MainetConstants.FlagN);
		this.getModel().getDto().setDistrictList(new ArrayList<>());
		this.getModel().getDto().setBlockList(new ArrayList<>());
		return new ModelAndView("FPOMasterForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long fpoId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		populateModel();
		List<LookUp> alcCateSubLIst = new ArrayList<LookUp>();
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		FPOMasterDto dto = fPOMasterService.getDetailById(fpoId);
		this.getModel().setDto(dto);
		if(dto.getUdyogAadharDate() !=null  && StringUtils.isNotEmpty(dto.getUdyogAadharNo())){
			this.getModel().setShowUdyogDet(MainetConstants.FlagY);
		}else
			this.getModel().setShowUdyogDet(MainetConstants.FlagN);
		if (dto.getAllocationCategory() != null) {
			List<LookUp> alcList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			alcCateSubLIst = alcList.stream()
					.filter(lookUp -> lookUp.getLookUpParentId() == dto.getAllocationCategory())
					.collect(Collectors.toList());
			this.getModel().setAllocationSubCatgList(alcCateSubLIst);
		}
		LookUp femaleLookup = CommonMasterUtility.getValueFromPrefixLookUp("F", "GEN", org);
		LookUp maleLookUp = CommonMasterUtility.getValueFromPrefixLookUp("M", "GEN", org);
		boolean result = farmerMasterService.checkWomenCentric(femaleLookup.getLookUpId(), maleLookUp.getLookUpId(),
				dto.getCreatedBy());
		this.getModel().setWomenCentric(result);
		return new ModelAndView("FPOMasterForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoDetails(Long fpoId, String fpoRegNo, Long iaId,
			final HttpServletRequest httpServletRequest) {
		Organisation org = UserSession.getCurrent().getOrganisation();
		List<FPOMasterDto> DtoList = fPOMasterService.getfpoByIdAndRegNo(fpoId, fpoRegNo, iaId,
				UserSession.getCurrent().getEmployee().getMasId(), org.getOrgShortNm(),
				UserSession.getCurrent().getEmployee().getEmploginname());
		this.getModel().setFpoMasterDtoList(DtoList);
		this.getModel().getDto().setFpoId(fpoId);
		this.getModel().getDto().setFpoRegNo(fpoRegNo);
		this.getModel().getDto().setIaId(iaId);
		return new ModelAndView("FPOMasterSummaryFormValidn", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "getAlcSubCatList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getAlcSubCatList(@RequestParam("allocationCategory") Long allocationCategory,
			HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == allocationCategory)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			logger.error("ALC Prefix not found");
			return lookUpList1;
		}
	}

	@RequestMapping(params = "getBlockDetByIa", method = { RequestMethod.POST })
	public ModelAndView getBlockDetByIa(@RequestParam("iaId") Long iaId, HttpServletRequest request) {
		List<LookUp> look = new ArrayList<>();
		List<BlockAllocationDetailDto> details = fPOMasterService.findBlockDetailsByMasIdAndYr(iaId,
				UserSession.getCurrent().getEmployee().getMasId());
		List<LookUp> looks = CommonMasterUtility.getNextLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		details.forEach(d -> {
			look.add(looks.stream().filter(l -> l.getLookUpId() == d.getSdb1()).findAny().get());
		});
		this.getModel().getDto().setStateList(look.stream().distinct().collect(Collectors.toList()));
		this.getModel().getDto().setBlockDto(details);
		this.getModel().getDto().setIaId(iaId);
		return new ModelAndView("FPOMasterForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("sdb1") Long sdb1, HttpServletRequest request) {
		List<LookUp> look = new ArrayList<LookUp>();
		List<BlockAllocationDetailDto> blockDetDto = new CopyOnWriteArrayList<>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb1)
					.collect(Collectors.toList());
			blockDetDto  =  this.getModel().getDto().getBlockDto();
			/*blockDetDto.forEach(d -> {
				look.add(lookUpList1.stream().filter(l -> l.getLookUpId() == d.getSdb2()).findAny().get());
			});*/
			for (BlockAllocationDetailDto d : blockDetDto) {
				for (LookUp l : lookUpList1) {
					if (l.getLookUpId() == d.getSdb2())
						look.add(l);
				}
			}
		} catch (Exception e) {
			logger.error("SDB Prefix not found"+e);
			e.printStackTrace();
		}
		return look.stream().distinct().collect(Collectors.toList());
	}

	@RequestMapping(params = "getBlockList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getBlockListByDistrictId(@RequestParam("sdb2") Long sdb2, HttpServletRequest request) {
		List<LookUp> look = new CopyOnWriteArrayList<>();
		List<BlockAllocationDetailDto> blockDetDto = new CopyOnWriteArrayList<>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 3,
					UserSession.getCurrent().getOrganisation());
			final List<LookUp> lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == sdb2)
					.collect(Collectors.toList());
			blockDetDto  =  this.getModel().getDto().getBlockDto();
			//blockDetDto  = blockDetDto.stream().filter(d-> d.getSdb2() == sdb2).collect(Collectors.toList());
			for (BlockAllocationDetailDto d : blockDetDto) {
				for (LookUp l : lookUpList1) {
					if (l.getLookUpId() == d.getSdb3())
						look.add(l);
				}
			}
			
	/*		blockDetDto.forEach(d -> {
				look.add(lookUpList1.stream().filter(l -> l.getLookUpId() == d.getSdb3()).findAny().get());
			});*/

		} catch (Exception e) {
			logger.error("SDB Prefix not found"+e);
			e.printStackTrace();
		}
		return look.stream().distinct().collect(Collectors.toList());
	}

	@ResponseBody
	@RequestMapping(params = "checkFpoNameExist", method = RequestMethod.POST)
	public Boolean checkFpoNameExist(HttpServletRequest request, @RequestParam("fpoName") String fpoName) {
		boolean result = fPOMasterService.checkFpoNameExist(fpoName);
		return result;
	}

	@ResponseBody
	@RequestMapping(params = "checkComRegNoExist", method = RequestMethod.POST)
	public Boolean checkComRegNoExist(HttpServletRequest request, @RequestParam("companyRegNo") String companyRegNo) {
		String regNo = companyRegNo.toUpperCase();
		boolean result = fPOMasterService.checkComRegNoExist(regNo);
		return result;
	}
}
