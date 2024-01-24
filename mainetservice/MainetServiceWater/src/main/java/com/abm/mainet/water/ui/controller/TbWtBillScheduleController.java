/*
 * Created on 2 May 2016 ( Time 19:16:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.service.TbComparentDetService;
import com.abm.mainet.common.service.TbComparentMasService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.BillingMonthDTO;
import com.abm.mainet.water.dto.BillingScheduleDto;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.abm.mainet.water.ui.model.BillScheduleResponse;
//--- Common classes
//--- Entities
//--- Services
//--- List Items

/**
 * Spring MVC controller for 'TbWtBillSchedule' management.
 */
@Controller
@RequestMapping("/BillSchedule.html")
public class TbWtBillScheduleController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TbWtBillScheduleController.class);

	// --- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "tbWtBillSchedule";
	private static final String MAIN_LIST_NAME = MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST;

	// --- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM = "tbWtBillSchedule/form";
	private static final String JSP_LIST = "tbWtBillSchedule/list";

	// --- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE = "BillSchedule.html?create";
	private static final String SAVE_ACTION_UPDATE = "BillSchedule.html?update";

	// --- Main entity service
	@Resource
	private TbWtBillScheduleService tbWtBillScheduleService; // Injected by Spring
	// --- Other service(s)
	@Resource
	private TbComparentDetService tbComparentDetService; // Injected by Spring
	@Resource
	private TbComparamDetService tbComparamDetService; // Injected by Spring
	@Resource
	private TbComparamMasService tbComparamMasService;
	@Resource
	private TbComparentMasService tbComparentMasService;
	@Resource
	private TbFinancialyearService financialyearService;

	/*
	 * private List<ViewPrefixDetails> monPrefixList = new ArrayList<>();; private
	 * List<ViewPrefixDetails> wnmPrefixList = new ArrayList<>(); ;
	 */
	private List<LookUp> wwzPrefixList = new ArrayList<>();
	private List<LookUp> ccgPrefixList = new ArrayList<>();
	private List<LookUp> wwzTempPrefixList = new ArrayList<>();
	private List<LookUp> monPrefixList = new ArrayList<>();
	private List<LookUp> wnmPrefixList = new ArrayList<>();
	private List<LookUp> bscPrefixList = new ArrayList<>();

	private List<BillingMonthDTO> billingMonthTempList = new LinkedList<>();
	private final Map<Long, String> finYearData = new LinkedHashMap<>();
	private TbWtBillSchedule tbWtBillScheduleTemp = new TbWtBillSchedule();
	private List<TbWtBillSchedule> gridList = new ArrayList<>();

	// --------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public TbWtBillScheduleController() {
		super(TbWtBillScheduleController.class, MAIN_ENTITY_NAME);
		log("TbWtBillScheduleController created.");
	}

	/**
	 * Populates the Spring MVC model with the given entity and eventually other
	 * useful data
	 * 
	 * @param model
	 * @param tbWtBillSchedule
	 */
	private void populateModel(final Model model, final TbWtBillSchedule tbWtBillSchedule, final FormMode formMode) {
		// --- Main entity

		model.addAttribute(MAIN_ENTITY_NAME, tbWtBillSchedule);
		if (formMode == FormMode.CREATE) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
		} else if (formMode == FormMode.UPDATE) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
		}
	}

	/**
	 * Shows a list with all the occurrences of TbWtBillSchedule found in the
	 * database
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(final Model model) {
		log("Action 'list'");

		billingMonthTempList = new LinkedList<>();
		wwzPrefixList = new ArrayList<>();
		gridList = new ArrayList<>();

		wnmPrefixList = CommonMasterUtility.getLookUps(MainetConstants.NewWaterServiceConstants.WMN,
				UserSession.getCurrent().getOrganisation());

		bscPrefixList = CommonMasterUtility.getLookUps(PrefixConstants.NewWaterServiceConstants.Water_prefix.BSC,
				UserSession.getCurrent().getOrganisation());

		monPrefixList = CommonMasterUtility.getLookUps(PrefixConstants.WATERMODULEPREFIX.MONTH,
				UserSession.getCurrent().getOrganisation());

		ccgPrefixList = CommonMasterUtility.getNextLevelData(PrefixConstants.WATERMODULEPREFIX.CCG, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());

		wwzTempPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.NewWaterServiceConstants.WWZ,
				UserSession.getCurrent().getOrganisation());

		List<LookUp> lookupList = CommonMasterUtility.getNextLevelData(PrefixConstants.NewWaterServiceConstants.WWZ, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		wwzPrefixList.addAll(lookupList);

		BillingMonthDTO billingMonthData = new BillingMonthDTO();
		long tempMonthId = 1;
		for (final LookUp prefixDet : monPrefixList) {
			if ((Long.valueOf(prefixDet.getLookUpCode()).intValue() >= 4)
					&& (Long.valueOf(prefixDet.getLookUpCode()).intValue() <= 12)) {
				billingMonthData = new BillingMonthDTO();
				billingMonthData.setMonthId(Long.valueOf(prefixDet.getLookUpCode()));
				billingMonthData.setMonthName(prefixDet.getLookUpDesc());
				billingMonthData.setTempMonthId(tempMonthId);
				tempMonthId++;
				billingMonthTempList.add(billingMonthData);
			}
		}

		for (final LookUp prefixDet : monPrefixList) {
			if (Long.valueOf(prefixDet.getLookUpCode()).intValue() < 4) {
				billingMonthData = new BillingMonthDTO();
				billingMonthData.setMonthId(Long.valueOf(prefixDet.getLookUpCode()));
				billingMonthData.setMonthName(prefixDet.getLookUpDesc());
				billingMonthData.setTempMonthId(tempMonthId);
				tempMonthId++;
				billingMonthTempList.add(billingMonthData);
			}
		}

		final List<FinancialYear> finYearList = financialyearService
				.findAllFinYearByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		String financialYear = null;
		for (final FinancialYear finYearTemp : finYearList) {
			try {
				financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				finYearData.put(finYearTemp.getFaYear(), financialYear);
			} catch (final Exception e) {
				LOGGER.error("error in finYear list", e);
			}
		}
		model.addAttribute("wwzTempPrefixList", wwzTempPrefixList);
		model.addAttribute("wnmPrefixList", wnmPrefixList);
		model.addAttribute("bscPrefixList", bscPrefixList);
		model.addAttribute("monPrefixList", monPrefixList);
		model.addAttribute("finYearData", finYearData);

		model.addAttribute("wwzPrefixList", wwzPrefixList);
		model.addAttribute("ccgPrefixList", ccgPrefixList);
		model.addAttribute("billingMonthTempList", billingMonthTempList);
		model.addAttribute("codIdTrf", "codIdTrf");
		model.addAttribute("codIdWwz", "codIdWwz");
		model.addAttribute("codUsageName", "codUsageName");
		model.addAttribute("getChildPrefixData", "BillSchedule.html?getChildPrefixData");

		return JSP_LIST;

	}

	@RequestMapping(params = "searchBillingData")
	public @ResponseBody void searchBillingData(final Model model, @RequestParam("cnsMn") final String cnsMn,
			@RequestParam("cnsYearid") final Long cnsYearid, @RequestParam("dependsOnType") final Long dependsOnType,
			@RequestParam("billFreq") final Long billFreq, @RequestParam("codIdWwz1") final Long codIdWwz1,
			@RequestParam("codIdWwz2") final Long codIdWwz2, @RequestParam("codIdWwz3") final Long codIdWwz3,
			@RequestParam("codIdWwz4") final Long codIdWwz4, @RequestParam("codIdWwz5") final Long codIdWwz5,
			@RequestParam("cnsCcgid1") final Long cnsCcgid1, @RequestParam("fromMonth") final Long fromMonth) {

		final BillingScheduleDto billingScheduleDto = new BillingScheduleDto();

		billingScheduleDto.setCnsMn(cnsMn);
		billingScheduleDto.setBillFreq(billFreq);
		billingScheduleDto.setDependsOnType(dependsOnType);
		billingScheduleDto.setCodIdWwz1(codIdWwz1);
		billingScheduleDto.setCodIdWwz2(codIdWwz2);
		billingScheduleDto.setCodIdWwz3(codIdWwz3);
		billingScheduleDto.setCodIdWwz4(codIdWwz4);
		billingScheduleDto.setCodIdWwz5(codIdWwz5);
		billingScheduleDto.setFromMonth(fromMonth);
		billingScheduleDto.setCnsYearid(cnsYearid);
		billingScheduleDto.setCnsCcgid1(cnsCcgid1);

		gridList = tbWtBillScheduleService.searchBillingData(billingScheduleDto,
				UserSession.getCurrent().getOrganisation().getOrgid());

		for (final TbWtBillSchedule billSchedule : gridList) {
			for (final LookUp prefixDet : monPrefixList) {
				if ((billSchedule.getCnsFromDate() != null)
						&& (billSchedule.getCnsFromDate().intValue() == Integer.parseInt(prefixDet.getLookUpCode()))) {
					billSchedule.setFromMonth(prefixDet.getLookUpDesc());
				}
				if ((billSchedule.getCnsToDate() != null)
						&& (billSchedule.getCnsToDate().intValue() == Integer.parseInt(prefixDet.getLookUpCode()))) {
					billSchedule.setToMonth(prefixDet.getLookUpDesc());
				}
			}
			for (final LookUp prefixDet : wnmPrefixList) {
				if (!StringUtils.isEmpty(billSchedule.getCnsMn())
						&& prefixDet.getLookUpCode().equals(billSchedule.getCnsMn())) {
					billSchedule.setMeterType(prefixDet.getLookUpDesc());
				}
			}
			for (final LookUp billingMonth : bscPrefixList) {
				if ((billSchedule.getCnsCpdid() != null)
						&& (billSchedule.getCnsCpdid().longValue() == billingMonth.getLookUpId())) {
					billSchedule.setBillingFreq(billingMonth.getLookUpDesc());
				}
			}
			final Set<Long> keys = finYearData.keySet();
			for (final Long key : keys) {
				if ((billSchedule.getCnsYearid() != null)
						&& (billSchedule.getCnsYearid().intValue() == key.intValue())) {
					billSchedule.setFinYr(finYearData.get(billSchedule.getCnsYearid()));
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "getMasGridData")
	public @ResponseBody BillScheduleResponse getMasGridData(final HttpServletRequest request, final Model model) {
		log("Action 'Get grid Data'");
		final BillScheduleResponse response = new BillScheduleResponse();
		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		final int listSize = gridList.size();

		response.setRows(gridList);
		response.setTotal(listSize);
		response.setRecords(listSize);
		response.setPage(page);

		model.addAttribute(MAIN_LIST_NAME, gridList);

		return response;
	}

	@RequestMapping(params = "bifurcateBillMonth", method = RequestMethod.POST)
	public @ResponseBody List<BillingMonthDTO> bifurcateBillMonth(@RequestParam("freqId") final Long freqId,
			final Model model) {

		final List<BillingMonthDTO> billingMonthList = new LinkedList<>();
		final int tempFreq = freqId.intValue();
		if (billingMonthTempList.size() > 0) {
			for (int iCounter = 0; iCounter < 12; iCounter += tempFreq) {
				billingMonthList.add(billingMonthTempList.get(iCounter));
			}
		}
		model.addAttribute("billingMonthList", billingMonthList);

		return billingMonthList;
	}

	@RequestMapping(params = "getChildPrefixData", method = RequestMethod.POST)
	public @ResponseBody List<LookUp> getChildPrefixData(@RequestParam("cpdId") final long cpdId) {

		final List<LookUp> prefixDataTempList = new ArrayList<>();
		prefixDataTempList.addAll(ApplicationSession.getInstance().getChildLookUpsFromParentId(cpdId));
		return prefixDataTempList;
	}

	/**
	 * Shows a form page in order to create a new TbWtBillSchedule
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping(params = "form")
	public String formForCreate(final Model model) {
		log("Action 'formForCreate'");
		final TbWtBillSchedule tbWtBillSchedule = new TbWtBillSchedule();

		model.addAttribute("wwzPrefixList", wwzPrefixList);
		model.addAttribute("wnmPrefixList", wnmPrefixList);
		model.addAttribute("bscPrefixList", bscPrefixList);
		model.addAttribute("ccgPrefixList", ccgPrefixList);
		model.addAttribute("monPrefixList", monPrefixList);
		model.addAttribute("billingMonthTempList", billingMonthTempList);
		model.addAttribute("finYearData", finYearData);
		model.addAttribute("codIdTrf", "codIdTrf");
		model.addAttribute("codIdWwz", "codIdWwz");
		model.addAttribute("codUsageName", "codUsageName");
		model.addAttribute("getChildPrefixData", "BillSchedule.html?getChildPrefixData");
		populateModel(model, tbWtBillSchedule, FormMode.CREATE);
		model.addAttribute("wwzTempPrefixList", wwzTempPrefixList);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing TbWtBillSchedule
	 * 
	 * @param model
	 *            Spring MVC model
	 * @param cnsId
	 *            primary key element
	 * @param orgid
	 *            primary key element
	 * @return
	 */
	@RequestMapping(params = "formForUpdate")
	public String formForUpdate(final Model model, @RequestParam("cnsId") final Long cnsId,
			@RequestParam("mode") final String mode) {
		log("Action 'formForUpdate'");
		// --- Search the entity by its primary key and stores it in the model
		final TbWtBillSchedule tbWtBillSchedule = tbWtBillScheduleService.findById(cnsId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		tbWtBillSchedule.setCnsToDateTemp(tbWtBillSchedule.getCnsToDate());
		tbWtBillScheduleTemp = tbWtBillSchedule;
		ccgPrefixList = CommonMasterUtility.getNextLevelData(PrefixConstants.WATERMODULEPREFIX.CCG, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		wwzTempPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.NewWaterServiceConstants.WWZ,
				UserSession.getCurrent().getOrganisation());

		for (final LookUp bscData : bscPrefixList) {
			if (Integer.valueOf(bscData.getLookUpCode()).intValue() == tbWtBillSchedule.getCnsCpdid().intValue()) {
				tbWtBillSchedule.setCnsCpdIdTemp(bscData.getLookUpId());
			}
		}

		final List<LookUp> wwzPrefixList = new ArrayList<>();
		List<LookUp> lookupList = CommonMasterUtility.getNextLevelData(PrefixConstants.NewWaterServiceConstants.WWZ, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		wwzPrefixList.addAll(lookupList);

		final List<BillingMonthDTO> billingMonthList = new LinkedList<>();
		int tempFreq = 0;

		for (final LookUp bscPrefixData : bscPrefixList) {
			if (bscPrefixData.getLookUpId() == tbWtBillSchedule.getCnsCpdid().longValue()) {
				tempFreq = Integer.parseInt(bscPrefixData.getLookUpCode());
				break;
			}
		}

		for (int iCounter = 0; iCounter < 12; iCounter += tempFreq) {
			billingMonthList.add(billingMonthTempList.get(iCounter));
		}

		model.addAttribute("codIdWwz", "codIdWwz");
		model.addAttribute("codUsageName", "codUsageName");
		model.addAttribute("getChildPrefixData", "BillSchedule.html?getChildPrefixData");

		model.addAttribute("billingMonthList", billingMonthList);

		populateModel(model, tbWtBillSchedule, FormMode.UPDATE);
		model.addAttribute("wnmPrefixList", wnmPrefixList);
		model.addAttribute("bscPrefixList", bscPrefixList);
		model.addAttribute("monPrefixList", monPrefixList);
		model.addAttribute("ccgPrefixList", ccgPrefixList);
		model.addAttribute("finYearData", finYearData);
		model.addAttribute("billingMonthTempList", billingMonthTempList);
		model.addAttribute("wwzPrefixList", wwzPrefixList);
		model.addAttribute("wwzTempPrefixList", wwzTempPrefixList);
		List<BillingScheduleDto> scheduleList = new ArrayList<>();
		scheduleList = tbWtBillScheduleService.createSchedule(tbWtBillSchedule.getCnsCpdid(),
				tbWtBillSchedule.getCnsYearid(), UserSession.getCurrent().getOrganisation());
		model.addAttribute("scheduleList", scheduleList);
		model.addAttribute("formMode", mode);
		return JSP_FORM;
	}

	@RequestMapping(params = "validateBillingData", method = RequestMethod.POST)
	public @ResponseBody int validateBillingData(final TbWtBillSchedule tbWtBillSchedule) {

		int countResult = 0;

		final BillingScheduleDto billingScheduleDto = new BillingScheduleDto();
		billingScheduleDto.setCnsYearid(tbWtBillSchedule.getCnsYearid());
		billingScheduleDto.setCnsMn(tbWtBillSchedule.getCnsMn());
		final List<TbWtBillSchedule> data = tbWtBillScheduleService.searchBillingData(billingScheduleDto,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if ((data != null) && !data.isEmpty()
				&& !data.get(0).getDependsOnType().equals(tbWtBillSchedule.getDependsOnType())) {

			countResult++;
		} else {
			billingScheduleDto.setDependsOnType(Long.parseLong(tbWtBillSchedule.getDependsOnType()));
			
			if((data != null) && !data.isEmpty()) {
			    if(tbWtBillSchedule.getCodIdWwz1() !=null && data.get(0).getCodIdWwz1() != -1) {
				    billingScheduleDto.setCodIdWwz1(tbWtBillSchedule.getCodIdWwz1());
				}else {
				    billingScheduleDto.setCodIdWwz1(-1L);
				}
			    if(tbWtBillSchedule.getCodIdWwz2() != null && (data.get(0).getCodIdWwz2()) != -1) {
				    billingScheduleDto.setCodIdWwz2(tbWtBillSchedule.getCodIdWwz2());
				}else {
				    billingScheduleDto.setCodIdWwz2(-1L);
				}
			    if(tbWtBillSchedule.getCodIdWwz3() !=null && data.get(0).getCodIdWwz3() != -1) {
				    billingScheduleDto.setCodIdWwz3(tbWtBillSchedule.getCodIdWwz3());
				}else {
				    billingScheduleDto.setCodIdWwz3(-1L);
				}
			    if(tbWtBillSchedule.getCodIdWwz4() !=null && data.get(0).getCodIdWwz4() != -1) {
				    billingScheduleDto.setCodIdWwz4(tbWtBillSchedule.getCodIdWwz4());
				}else {
				    billingScheduleDto.setCodIdWwz4(-1L);
				}
			    if(tbWtBillSchedule.getCodIdWwz5() !=null && data.get(0).getCodIdWwz5() != -1) {
				    billingScheduleDto.setCodIdWwz5(tbWtBillSchedule.getCodIdWwz5());
				}else {
				    billingScheduleDto.setCodIdWwz5(-1L);
				}
			}else {
			    if(tbWtBillSchedule.getCodIdWwz1() !=null) {
				billingScheduleDto.setCodIdWwz1(tbWtBillSchedule.getCodIdWwz1());
			    }
			    if(tbWtBillSchedule.getCodIdWwz2() !=null) {
				billingScheduleDto.setCodIdWwz2(tbWtBillSchedule.getCodIdWwz2());
			    }
			    
			    if(tbWtBillSchedule.getCodIdWwz3() !=null) {
				billingScheduleDto.setCodIdWwz3(tbWtBillSchedule.getCodIdWwz3());
			    }
			    if(tbWtBillSchedule.getCodIdWwz4() !=null) {
				billingScheduleDto.setCodIdWwz4(tbWtBillSchedule.getCodIdWwz4());
			    }
			    if(tbWtBillSchedule.getCodIdWwz5() !=null) {
				billingScheduleDto.setCodIdWwz5(tbWtBillSchedule.getCodIdWwz5());
			    }
			}
			
			
			
			countResult = tbWtBillScheduleService.validateBillingData(billingScheduleDto,
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (countResult > 0) {
				return countResult;
			}

			if (MainetConstants.CommonConstants.ONE.equals(tbWtBillSchedule.getDependsOnType())) {
				billingScheduleDto.setCodIdWwz1(tbWtBillSchedule.getCodIdWwz1());
				billingScheduleDto.setCodIdWwz2(tbWtBillSchedule.getCodIdWwz2());
				billingScheduleDto.setCodIdWwz3(tbWtBillSchedule.getCodIdWwz3());
				billingScheduleDto.setCodIdWwz4(tbWtBillSchedule.getCodIdWwz4());
				billingScheduleDto.setCodIdWwz5(tbWtBillSchedule.getCodIdWwz5());
			} else {
				billingScheduleDto.setCnsCcgid1(tbWtBillSchedule.getCnsCcgid1());
			}

			countResult = tbWtBillScheduleService.validateBillingData(billingScheduleDto,
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		return countResult;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by
	 * 'http redirect'<br>
	 * 
	 * @param tbWtBillSchedule
	 *            entity to be created
	 * @param bindingResult
	 *            Spring MVC binding result
	 * @param model
	 *            Spring MVC model
	 * @param redirectAttributes
	 *            Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Actions.CREATE) // GET or POST
	public ModelAndView create(@Valid final TbWtBillSchedule tbWtBillSchedule, final BindingResult bindingResult,
			final Model model, final HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {

				final TbComparamMas tbComparamMas = tbComparamMasService
						.findComparamDetDataByCpmId(PrefixConstants.NewWaterServiceConstants.WWZ);
				final List<TbComparentMas> comparentMasList = tbComparentMasService.findComparentMasDataById(
						tbComparamMas.getCpmId(), UserSession.getCurrent().getOrganisation().getOrgid());

				tbWtBillSchedule.setPrefixLevel(comparentMasList.size());
				tbWtBillSchedule.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				tbWtBillSchedule.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				tbWtBillSchedule.setLmoddate(new Date());
				tbWtBillSchedule.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));

				for (final LookUp bscPrefixData : bscPrefixList) {
					if (bscPrefixData.getLookUpId() == tbWtBillSchedule.getCnsCpdid().longValue()) {
						tbWtBillSchedule.setTempBillFreq(Integer.parseInt(bscPrefixData.getLookUpCode()));
						break;
					}
				}

				final TbWtBillSchedule tbWtBillScheduleCreated = tbWtBillScheduleService.create(tbWtBillSchedule,
						billingMonthTempList);
				model.addAttribute(MAIN_ENTITY_NAME, tbWtBillScheduleCreated);

				// ---
				return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
						MainetConstants.COMMON_STATUS.SUCCESS);
			} else {
				populateModel(model, tbWtBillSchedule, FormMode.CREATE);
				return new ModelAndView(JSP_FORM);
			}
		} catch (final Exception e) {
			log("Action 'create' : Exception - " + e.getMessage());
			messageHelper.addException(model, "tbWtBillSchedule.error.create", e);
			populateModel(model, tbWtBillSchedule, FormMode.CREATE);
			return new ModelAndView(JSP_FORM);
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by
	 * 'http redirect'<br>
	 * 
	 * @param tbWtBillSchedule
	 *            entity to be updated
	 * @param bindingResult
	 *            Spring MVC binding result
	 * @param model
	 *            Spring MVC model
	 * @param redirectAttributes
	 *            Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Actions.UPDATE) // GET or POST
	public ModelAndView update(@Valid final TbWtBillSchedule tbWtBillSchedule, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				tbWtBillSchedule.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				tbWtBillSchedule.setUpdatedDate(new Date());

				for (final LookUp bscPrefixData : bscPrefixList) {
					if ((tbWtBillSchedule.getCnsCpdid() != null)
							&& (bscPrefixData.getLookUpId() == tbWtBillSchedule.getCnsCpdid().longValue())) {
						tbWtBillSchedule.setTempBillFreq(Integer.parseInt(bscPrefixData.getLookUpCode()));
						break;
					}
				}
				final TbWtBillSchedule tbWtBillScheduleSaved = tbWtBillScheduleService.update(tbWtBillSchedule,
						billingMonthTempList, tbWtBillScheduleTemp);
				model.addAttribute(MAIN_ENTITY_NAME, tbWtBillScheduleSaved);
				// --- Set the result message
				messageHelper.addMessage(redirectAttributes,
						new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
				log("Action 'update' : update done - redirect");
				return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
						MainetConstants.COMMON_STATUS.SUCCESS);
			} else {
				log("Action 'update' : binding errors");
				populateModel(model, tbWtBillSchedule, FormMode.UPDATE);
				return new ModelAndView(JSP_FORM);
			}
		} catch (final Exception e) {
			messageHelper.addException(model, "tbWtBillSchedule.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage());
			populateModel(model, tbWtBillSchedule, FormMode.UPDATE);
			return new ModelAndView(JSP_FORM);
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by
	 * 'http redirect'<br>
	 * 
	 * @param redirectAttributes
	 * @param cnsId
	 *            primary key element
	 * @param orgid
	 *            primary key element
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Actions.DELETE) // GET or POST
	public String delete(@RequestParam("cnsId") final Long cnsId) {
		log("Action 'delete'");
		try {
			tbWtBillScheduleService.delete(cnsId, UserSession.getCurrent().getOrganisation().getOrgid());
		} catch (final Exception e) {
			LOGGER.error("error in delete ", e);
		}

		return redirectToList();
	}

	@RequestMapping(params = MainetConstants.Property.BillSchedule.CREATE_SCHEDULE, method = RequestMethod.POST)
	public ModelAndView createBillSchedule(@Valid final TbWtBillSchedule tbWtBillSchedule,
			final BindingResult bindingResult, final Model model, final HttpServletRequest httpServletRequest) {
		/*
		 * this.bindModel(httpServletRequest);
		 * this.getModel().getBillScheduleDto().setAsBillFrequency(schFreq);
		 * this.getModel().setBillSchDtoList(billScheduleService.createSchedule(schFreq,
		 * this.getModel().getBillSchDtoList(),UserSession.getCurrent().getOrganisation(
		 * )));
		 */
		// final TbWtBillSchedule tbWtBillSchedule = new TbWtBillSchedule();

		model.addAttribute("wwzPrefixList", wwzPrefixList);
		model.addAttribute("wnmPrefixList", wnmPrefixList);
		model.addAttribute("bscPrefixList", bscPrefixList);
		model.addAttribute("ccgPrefixList", ccgPrefixList);
		model.addAttribute("monPrefixList", monPrefixList);
		model.addAttribute("billingMonthTempList", billingMonthTempList);
		model.addAttribute("finYearData", finYearData);
		model.addAttribute("codIdTrf", "codIdTrf");
		model.addAttribute("codIdWwz", "codIdWwz");
		model.addAttribute("codUsageName", "codUsageName");
		model.addAttribute("getChildPrefixData", "BillSchedule.html?getChildPrefixData");
		populateModel(model, tbWtBillSchedule, FormMode.CREATE);
		model.addAttribute("wwzTempPrefixList", wwzTempPrefixList);
		List<BillingScheduleDto> scheduleList = new ArrayList<>();
		scheduleList = tbWtBillScheduleService.createSchedule(tbWtBillSchedule.getCnsCpdid(), null,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("scheduleList", scheduleList);
		return new ModelAndView(JSP_FORM);
	}

}
