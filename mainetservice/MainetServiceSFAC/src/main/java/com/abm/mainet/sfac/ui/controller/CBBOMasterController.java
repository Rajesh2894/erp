/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.StateAreaZoneCategoryDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.CBBOMasterModel;

/**
 * @author pooja.maske
 *
 */

@Controller
@RequestMapping(MainetConstants.Sfac.CBBO_MASTER_FORM_HTML)
public class CBBOMasterController extends AbstractFormController<CBBOMasterModel> {

	private static final Logger logger = Logger.getLogger(CBBOMasterController.class);

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private CBBOMasterService cbboMasterService;

	@Autowired
	private FPOMasterService fpoMasterService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private TbFinancialyearService financialyearService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		String name = "";
		if (StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmpname())
				&& StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmplname()))
			name = UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
					+ UserSession.getCurrent().getEmployee().getEmplname();
		else
			name = UserSession.getCurrent().getEmployee().getEmpname();
		if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.CBBO)) {
			this.getModel().getMasterDto().setCbboName(name);
			if (null != UserSession.getCurrent().getEmployee().getMasId())
			this.getModel().setCbId(UserSession.getCurrent().getEmployee().getMasId());
		}
		else if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.IA)) {
			this.getModel().getMasterDto().setIAName(name);
			if (null != UserSession.getCurrent().getEmployee().getMasId())
				this.getModel().setiId(UserSession.getCurrent().getEmployee().getMasId());
		}
		return new ModelAndView(MainetConstants.Sfac.CBBO_MASTER_SUMMARY_FORM, MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {
		try {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.IA)) {
				if (UserSession.getCurrent().getEmployee().getMasId() != null)
					this.getModel().setCbboMasterList(cbboMasterService.findCbboById(UserSession.getCurrent().getEmployee().getMasId()));
			} else {
				this.getModel().setCbboMasterList(cbboMasterService.findAllCBBO());
			}
			this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));
			if (UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.NPMA)) {
				this.getModel().setIaMasterDtoList(iaMasterService.findAllIA());
			}else {
			List<CBBOMasterDto> masterDtoList = cbboMasterService
					.findAllIaAssociatedWithCbbo(UserSession.getCurrent().getEmployee().getEmploginname());
			this.getModel().setIaNameList(masterDtoList);
			}
		} catch (Exception e) {
			logger.error("Error Ocurred while fething details of financial year or Ia master details by orgid" + e);
		}

	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		String iaName = null  ;
		if(	UserSession.getCurrent().getEmployee().getEmpname() != null &&  UserSession.getCurrent().getEmployee().getEmplname() != null ) {
			iaName=	UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
				+ UserSession.getCurrent().getEmployee().getEmplname();
		}else if (UserSession.getCurrent().getEmployee().getEmpname() != null) {
			iaName=UserSession.getCurrent().getEmployee().getEmpname();
		}
		this.getModel().getMasterDto().setIAName(iaName);
		TbFinancialyear financialYear = null ;
		try {
			if (UserSession.getCurrent().getEmployee().getMasId() != null) {
			Long iayrId = iaMasterService.getIaALlocationYear(UserSession.getCurrent().getEmployee().getMasId());
			if (iayrId != null)
				financialYear = financialyearService.findYearById(iayrId,UserSession.getCurrent().getOrganisation().getOrgid());
			if (financialYear != null)
			this.getModel().setIaAllYr(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
			}
		}catch (Exception e) {
			logger.error("Error occured while fetching ia allocation year"+e);
		}
			
		
		this.getModel().setViewMode(MainetConstants.FlagA);
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		this.getModel().setDesignlist(designationService.findAll());
		return new ModelAndView(MainetConstants.Sfac.CBBO_MASTER_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@ResponseBody
	@RequestMapping(params = "getCbboLDeatails")
	public ModelAndView getCbboListByNameAndId(HttpServletRequest request, Long cbboId, Date alcYearToCBBO,Long iaId) {
		List<CBBOMasterDto> dtoList = new ArrayList<>();
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		if ((UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.CBBO)))
			cbboId = UserSession.getCurrent().getEmployee().getMasId();
		dtoList = cbboMasterService.getCBBODetailsByIds(cbboId, alcYearToCBBO, org.getOrgid(),iaId);
		this.getModel().setCbboMastDtoList(dtoList);
		if (!(UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.CBBO)))
		this.getModel().getMasterDto().setCbboName(String.valueOf(cbboId));
		this.getModel().getMasterDto().setAlcYearToCBBO(alcYearToCBBO);
		if (!(UserSession.getCurrent().getOrganisation().getOrgShortNm().equals(MainetConstants.Sfac.IA)))
		this.getModel().getMasterDto().setIaId(iaId);
		// return new ModelAndView(MainetConstants.Sfac.CBBO_MAST_SUMMARY_FORM_VALIDN,
		// MainetConstants.FORM_NAME, getModel());
		return new ModelAndView(MainetConstants.Sfac.CBBO_MAST_SUMMARY_FORM_VALIDN, MainetConstants.FORM_NAME, getModel());
	}

	@ResponseBody
	@RequestMapping(params = "fetchAllAreaAndZoneByStateCode")
	public StateAreaZoneCategoryDto fetchAllAreaAndZoneByStateCode(HttpServletRequest request, Long sdb1) {
		StateAreaZoneCategoryDto dto = new StateAreaZoneCategoryDto();
		try {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(sdb1, org.getOrgid());
			String stateCode = fpoMasterService.getPrefixOtherValue(lookUp.getLookUpId(), org.getOrgid());
			dto = cbboMasterService.fetchAreaAndZoneByStateCode(stateCode);
			this.getModel().setStateAreaZoneCatgDto(dto);
		} catch (Exception e) {
			logger.error("Error Occured while fetching area and zone details" + e);
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping(params = "checkIsAispirationalDist")
	public boolean checkIsAispirationalDist(HttpServletRequest request, Long sdb2) {
		boolean result = cbboMasterService.checkIsAispirationalDist(sdb2);
		if (result == true) {
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(params = "checkIsTribalDist")
	public boolean checkIsTribalDist(HttpServletRequest request, Long sdb2) {
		boolean result = cbboMasterService.checkIsTribalDist(sdb2);
		if (result == true) {
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(params = "getOdopByDist")
	public String getOdopByDist(HttpServletRequest request, Long sdb2) {
		String odopName = "";
		odopName = cbboMasterService.getOdopByDist(sdb2);
		return odopName;
	}

	@RequestMapping(params = MainetConstants.Sfac.EDIT_AND_VIEW_FORM, method = RequestMethod.POST)
	public ModelAndView editAndViewForm(@RequestParam("cbboId") final Long cbboId,
			@RequestParam(name = MainetConstants.FORM_MODE, required = false) String formMode,
			final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		logger.info("editAndViewForm started");
		CBBOMasterModel model = this.getModel();
		if (formMode.equals(MainetConstants.FlagE))
			model.setViewMode(MainetConstants.FlagE);
		else
			model.setViewMode(MainetConstants.FlagV);
		model.setMasterDto(cbboMasterService.findBycbboId(cbboId));
		this.getModel().setDesignlist(designationService.findAll());
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		/*List<IAMasterDto> masterDtoList = iaMasterService.getIAListByOrgId(org.getOrgid());
		this.getModel().setIaMasterDtoList(masterDtoList);*/
		this.getModel().setStateList(stateList);
		TbFinancialyear financialYear = null ;
		try {
			if (UserSession.getCurrent().getEmployee().getMasId() !=null) {
		
			if (this.getModel().getMasterDto() != null && this.getModel().getMasterDto().getCbboAppoitmentYr() !=null)
				financialYear = financialyearService.findYearById(this.getModel().getMasterDto().getCbboAppoitmentYr(),UserSession.getCurrent().getOrganisation().getOrgid());
			if (financialYear != null)
			this.getModel().setIaAllYr(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
			this.getModel().setCbboAppyr(Utility.getFinancialYearFromDate(financialYear.getFaFromDate()));
			}
		}catch (Exception e) {
			logger.error("Error occured while fetching ia allocation year"+e);
		}
		logger.info("editAndViewForm started");
		return new ModelAndView(MainetConstants.Sfac.CBBO_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "getAppoitmentYear", method = RequestMethod.POST)
	public List<Object> getAppoitmentYear(HttpServletRequest request, @RequestParam("alcYearToCBBO") Date alcYearToCBBO) {
		getModel().bind(request);
		List<Object> financialYear = new ArrayList<>();
		try {
			if (alcYearToCBBO != null) {
				FinancialYear financialYearId = financialyearService.getFinanciaYearByDate(alcYearToCBBO);
				if (financialYearId != null) {
					financialYear.add(0, financialYearId.getFaYear());
					if (financialYearId.getFaFromDate() != null) {
						String cbboYear = Utility.getFinancialYearFromDate(financialYearId.getFaFromDate());
						financialYear.add(1, cbboYear);
					}
				}
			}
		} catch (Exception exception) {
			throw new FrameworkException("Exception occured from get financial year" + exception);
		}
		return financialYear;
	}

	@ResponseBody
	@RequestMapping(params = "getDetailsByPanNo", method = RequestMethod.POST)
	public ModelAndView getDetailsByPanNo(HttpServletRequest request, @RequestParam("panNo") String panNo) {
		getModel().bind(request);
		CBBOMasterModel model = this.getModel();
		String panNoUpper = panNo.toUpperCase();
		model.setMasterDto(cbboMasterService.getDetailsByPanNo(panNoUpper));
		if (this.getModel().getMasterDto().getCreatedBy() == null) {
			this.getModel().getMasterDto().setPanNo(panNoUpper);
		}
		this.getModel().setDesignlist(designationService.findAll());
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1, UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		String iaName = null  ;
		if(	UserSession.getCurrent().getEmployee().getEmpname() != null &&  UserSession.getCurrent().getEmployee().getEmplname() != null ) {
			iaName=	UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
				+ UserSession.getCurrent().getEmployee().getEmplname();
		}else if (UserSession.getCurrent().getEmployee().getEmpname() != null) {
			iaName=UserSession.getCurrent().getEmployee().getEmpname();
		}
		this.getModel().getMasterDto().setIAName(iaName);
		try {
		String cbboYear = Utility.getFinancialYearFromDate(this.getModel().getMasterDto().getAlcYearToCBBO());
		this.getModel().setCbboAppyr(cbboYear);
		}catch (Exception e) {
			logger.error("Error Occured while fetching cbbo year getDetailsByPanNo"+e);
		}
		return new ModelAndView(MainetConstants.Sfac.CBBO_MASTER_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "checkPanNoExist")
	public boolean checkPanNoExist(HttpServletRequest request, String panNo) {
		String PanNo = panNo.toUpperCase();
		if (this.getModel().getViewMode().equals(MainetConstants.FlagA)) {
		boolean result = cbboMasterService.checkPanNoExist(PanNo,UserSession.getCurrent().getEmployee().getMasId(),UserSession.getCurrent().getEmployee().getEmpId());
		if (result == true) {
			return true;
		 }
		}
		return false;
	}
}
