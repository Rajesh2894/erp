package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBudgetOpenBalanceBean;
import com.abm.mainet.account.dto.AccountBudgetOpenBalanceUploadDto;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.AccountBudgetOpenBalanceService;
import com.abm.mainet.account.ui.validator.AccountBudgetOpenBalanceExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetOpenBalanceMasterDto;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountBudgetOpenBalanceMaster.html")
public class AccountBudgetOpenBalanceController extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbAcBugopenBalance";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM = "tbAcBugopenBalance/form";
	private static final String JSP_VIEW = "tbAcBugopenBalance/view";
	private static final String JSP_LIST = "tbAcBugopenBalance/list";
	private static final String JSP_EXCELUPLOAD = "tbAcBugopenBalance/ExcelUpload";
	private static final Logger LOGGER = Logger.getLogger(AccountBudgetOpenBalanceController.class);

	private String modeView = MainetConstants.BLANK;

	@Resource
	private AccountBudgetOpenBalanceService tbAcBugopenBalanceService;
	@Resource
	private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;
	@Resource
	private AccountFieldMasterService tbAcFieldMasterService;
	@Resource
	private AccountFundMasterService tbAcFundMasterService;
	@Resource
	private SecondaryheadMasterService tbAcSecondaryheadMasterService;
	@Resource
	private TbFinancialyearService tbFinancialyearService;
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	@Resource
	private TbOrganisationService tbOrganisationService;

	@Autowired
	private IFileUploadService fileUpload;

	private List<AccountBudgetOpenBalanceBean> chList = null;

	public AccountBudgetOpenBalanceController() {
		super(AccountBudgetOpenBalanceController.class, MAIN_ENTITY_NAME);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(params = "getGridData", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
			final Model model) {
		final JQGridResponse response = new JQGridResponse<>();
		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		response.setRows(chList);
		response.setTotal(chList.size());
		response.setRecords(chList.size());
		response.setPage(page);
		model.addAttribute(MAIN_LIST_NAME, chList);
		return response;
	}

	@RequestMapping(params = "getjqGridsearch")
	public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
			@RequestParam("faYearid") final Long faYearid, @RequestParam("cpdIdDrcr") final String cpdIdDrcr,
			@RequestParam("sacHeadId") final Long sacHeadId, @RequestParam("status") final String status) {

		chList = new ArrayList<>();
		chList.clear();
		final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String statusFlag = MainetConstants.CommonConstant.BLANK;
		if ((status != null) && !status.isEmpty()) {
			if (status.equals(MainetConstants.STATUS.ACTIVE)) {
				statusFlag = MainetConstants.MENU.Y;
			} else if (status.equals(MainetConstants.STATUS.INACTIVE)) {
				statusFlag = MainetConstants.MENU.N;
			}
		}
		chList = tbAcBugopenBalanceService.findByGridDataFinancialId(faYearid, cpdIdDrcr, sacHeadId, statusFlag, orgId);
		if (chList != null) {
			String amount = null;
			BigDecimal bd = null;
			for (final AccountBudgetOpenBalanceBean bean : chList) {
				if ((bean.getOpenbalAmt() != null) && !bean.getOpenbalAmt().isEmpty()) {
					bd = new BigDecimal(bean.getOpenbalAmt());
					amount = CommonMasterUtility.getAmountInIndianCurrency(bd);
					bean.setFormattedCurrency(amount);
				}
				if (bean.getSacHeadId() != null) {
					if ((bean.getAcHeadCode() != null) && !bean.getAcHeadCode().isEmpty()) {
						bean.setAccountHeads(bean.getAcHeadCode());
					}
				}
				if ((bean.getCpdIdDrcr() != null) && !bean.getCpdIdDrcr().isEmpty()) {
					final Long statusDrCr = Long.valueOf(bean.getCpdIdDrcr());
					if (drLookup.getLookUpId() == statusDrCr) {
						bean.setCpdIdDrCrDesc(drLookup.getDescLangFirst());
					} else if (crLookup.getLookUpId() == statusDrCr) {
						bean.setCpdIdDrCrDesc(crLookup.getDescLangFirst());
					}
				}
			}
		}
		return chList;
	}

	@RequestMapping()
	public String getList(final Model model) throws ParseException {
		String result = MainetConstants.BLANK;
		chList = new ArrayList<>();
		chList.clear();
		final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp sliLivePrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Map<Long, String> financeMap = null;
		Date sliDate = null;
		if (sliLivePrefix == null) {
			throw new NullPointerException(MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_MODE);
		} else {
			final String sliLivePrefixDate = sliLivePrefix.getOtherField();
			final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
			if (sliLivePrefixDate != null && !sliLivePrefixDate.isEmpty()) {
				try {
					sliDate = formatter.parse(sliLivePrefixDate);
				} catch (final ParseException e) {
					LOGGER.error("Go-live date not set in (DD/MM/YYYY) format in other value of SLI prefix", e);
					throw new IllegalArgumentException(
							MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_LIVE_DATE + sliLivePrefixDate, e);
				}
				financeMap = tbFinancialyearService.getAllSLIPrefixDateFinincialYear(sliDate);
				model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FINANCE_MAP, financeMap);
			} else {
				LOGGER.error("Go-live date not set in (DD/MM/YYYY) format in other value of SLI prefix");
			}
		}
		Long faYearid = null;
		if (sliDate != null) {
			final TbFinancialyear faYearIdDto = tbFinancialyearService.findFinancialYear(sliDate);
			if (faYearIdDto != null) {
				faYearid = faYearIdDto.getFaYear();
			}
		}
		if (faYearid != null) {
			chList = tbAcBugopenBalanceService.findByFinancialId(faYearid, orgId);
			if (chList != null) {
				String amount = null;
				BigDecimal bd = null;
				for (final AccountBudgetOpenBalanceBean bean : chList) {
					if ((bean.getOpenbalAmt() != null) && !bean.getOpenbalAmt().isEmpty()) {
						bd = new BigDecimal(bean.getOpenbalAmt());
						amount = CommonMasterUtility.getAmountInIndianCurrency(bd);
						bean.setFormattedCurrency(amount);
					}
					if (bean.getSacHeadId() != null) {
						if ((bean.getAcHeadCode() != null) && !bean.getAcHeadCode().isEmpty()) {
							bean.setAccountHeads(bean.getAcHeadCode());
						}
					}
					if ((bean.getCpdIdDrcr() != null) && !bean.getCpdIdDrcr().isEmpty()) {
						final Long statusDrCr = Long.valueOf(bean.getCpdIdDrcr());
						if (drLookup.getLookUpId() == statusDrCr) {
							bean.setCpdIdDrCrDesc(drLookup.getDescLangFirst());
						} else if (crLookup.getLookUpId() == statusDrCr) {
							bean.setCpdIdDrCrDesc(crLookup.getDescLangFirst());
						}
					}
				}
			}
		}
		final AccountBudgetOpenBalanceBean bean = new AccountBudgetOpenBalanceBean();
		final List<LookUp> levelMap1 = CommonMasterUtility.getListLookup(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
				UserSession.getCurrent().getOrganisation());
		final List<LookUp> liabilityAssetLookUp1 = new ArrayList<>();
		for (final LookUp lookUp : levelMap1) {
			if ((lookUp.getLookUpCode().equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE))
					|| (lookUp.getLookUpCode().equals(MainetConstants.MENU.A))) {
				liabilityAssetLookUp1.add(lookUp);
			}
		}
		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LEVELS_MAP, liabilityAssetLookUp1);

		final Map<Long, String> pacHeadMap = new LinkedHashMap<>();
		final List<AccountBudgetOpenBalanceBean> newList = tbAcBugopenBalanceService.findAllAccountHeadsData(orgId);
		if (newList != null) {
			for (final AccountBudgetOpenBalanceBean bean1 : newList) {
				if (bean1.getSacHeadId() != null) {
					if ((bean1.getAcHeadCode() != null) && !bean1.getAcHeadCode().isEmpty()) {
						bean1.setAccountHeads(bean1.getAcHeadCode());
					}
					pacHeadMap.put(bean1.getSacHeadId(), bean1.getAccountHeads());
				}
			}
		}
		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_NEW_LIST, pacHeadMap);

		final List<LookUp> statusLookUpList = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.STATUS_CPD_VALUE, statusLookUpList);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		model.addAttribute(MAIN_LIST_NAME, chList);
		result = JSP_LIST;
		return result;
	}

	@RequestMapping(params = "form")
	public String formForCreate(final Model model) throws Exception {
		String result = MainetConstants.BLANK;
		final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());

		final AccountBudgetOpenBalanceBean bean = new AccountBudgetOpenBalanceBean();
		bean.setDrTypeValue(drLookup.getLookUpId());
		bean.setCrTypeValue(crLookup.getLookUpId());
		populateModel(model, bean, FormMode.CREATE);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping(params = "getPrimaryALLOpeningBalanceData", method = RequestMethod.POST)
	public String getPrimaryALLOpeningBalanceData(final AccountBudgetOpenBalanceBean bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) throws Exception {
		final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LIABILITY_OPN_STATUS,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.ASSET_OPN_BAL_TYPES,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
		final int langId = UserSession.getCurrent().getLanguageId();
		if (bean.getFaYearid() != null) {
			if ((bean.getOpnBalType() != null) && !bean.getOpnBalType().isEmpty()) {
				final String cpdId = bean.getOpnBalType();
				if (cpdId.equals(drLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapLiability = new LinkedHashMap<>();
					List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = null;
					listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllStatusLastLevelsLiability(orgid, superUserOrganization, langId);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapLiability.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapLiability);
				}
				if (cpdId.equals(crLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapAsset = new LinkedHashMap<>();
					List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = null;
					listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllStatusLastLevelsAsset(orgid, superUserOrganization, langId);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapAsset.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapAsset);
				}
			}
		}
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		return JSP_FORM;
	}

	@RequestMapping(params = "getOpnBalDuplicateGridloadData", method = RequestMethod.POST)
	public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final AccountBudgetOpenBalanceBean bean,
			final HttpServletRequest request, final Model model, final BindingResult bindingResult,
			@RequestParam("cnt") final int cnt) {
		boolean isValidationError = false;
		final Long faYearid = bean.getFaYearid();
		Long fundId = null;
		if (bean.getFundId() != null) {
			fundId = bean.getFundId();
		}
		Long fieldId = null;
		if (bean.getFieldId() != null) {
			fieldId = bean.getFieldId();
		}
		final String cpdIdDrcr = bean.getOpnBalType();
		final Long sacHeadId = bean.getBugReappMasterDtoList().get(cnt).getSacHeadId();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (tbAcBugopenBalanceService.isCombinationExists(faYearid, fundId, fieldId, cpdIdDrcr, sacHeadId, orgId)) {
			bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.BUG_OPENBAL_MASTER,
					MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
					ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
			isValidationError = true;
		}
		return isValidationError;
	}

	private void populateModel(final Model model, final AccountBudgetOpenBalanceBean bean, final FormMode formMode)
			throws Exception {
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);

		final LookUp sliLivePrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
		if (sliLivePrefix == null) {
			throw new NullPointerException(MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_MODE);
		} else {
			final String sliLivePrefixDate = sliLivePrefix.getOtherField();
			final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
			Date sliDate = null;
			if (sliLivePrefixDate != null && !sliLivePrefixDate.isEmpty()) {
				try {
					sliDate = formatter.parse(sliLivePrefixDate);
				} catch (final ParseException e) {

					LOGGER.error("Go-live date not set in (DD/MM/YYYY) format in other value of SLI prefix", e);
					throw new IllegalArgumentException(
							MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_LIVE_DATE + sliLivePrefixDate, e);
				}
				final Map<Long, String> financeMap = tbFinancialyearService.getAllSLIPrefixDateFinincialYear(sliDate);
				model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FINANCE_MAP, financeMap);
			}
		}
		final List<LookUp> levelMap = CommonMasterUtility.getListLookup(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
				UserSession.getCurrent().getOrganisation());
		final List<LookUp> liabilityAssetLookUp = new ArrayList<>();
		for (final LookUp lookUp : levelMap) {
			if ((lookUp.getLookUpCode().equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE))
					|| (lookUp.getLookUpCode().equals(MainetConstants.MENU.A))) {
				liabilityAssetLookUp.add(lookUp);
			}
		}

		final List<LookUp> drCrLevelMap = CommonMasterUtility.getListLookup(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.DRCR_LEVELS_MAP, drCrLevelMap);

		model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LEVELS_MAP, liabilityAssetLookUp);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Organisation organisation = UserSession.getCurrent().getOrganisation();
		final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
		final int langId = UserSession.getCurrent().getLanguageId();
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FUND_FIELD_STATUS_PREFIX,
				UserSession.getCurrent().getOrganisation());

		String defaultFundFlag = MainetConstants.CommonConstant.BLANK;
		String defaultFieldFlag = MainetConstants.CommonConstant.BLANK;

		Long defaultOrgId = null;
		if (isDafaultOrgExist) {
			defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else {
			defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService.findAllWithOrgId(defaultOrgId);
		if (fundTypeLevel != null) {
			for (final LookUp lookUp : fundTypeLevel) {
				if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FUND_WISE_STATUS
						.equalsIgnoreCase(lookUp.getLookUpCode())) {
					if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)|| formMode.equals(FormMode.VIEW)) {
						final LookUp cmdFundPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
								PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
								UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
						if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
							for (final TbAcCodingstructureMas master : tbCodingList) {
								if (cmdFundPrefix.getLookUpId() == master.getComCpdId()) {
									defaultFundFlag = master.getDefineOnflag();
									if (defaultFundFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
										model.addAttribute(
												MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
												tbAcFundMasterService.getFundMasterStatusLastLevels(defaultOrgId,
														superUserOrganization, langId));
									} else {
										model.addAttribute(
												MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
												tbAcFundMasterService.getFundMasterStatusLastLevels(defaultOrgId,
														organisation, langId));
									}
								}
							}
						}
					}
					/*
					 * if (formMode.equals(FormMode.VIEW)) { if
					 * (defaultFundFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
					 * model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.
					 * FUND_MASTER_ITEMS,
					 * tbAcFundMasterService.getFundMasterLastLevels(defaultOrgId)); } else {
					 * model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.
					 * FUND_MASTER_ITEMS, tbAcFundMasterService.getFundMasterLastLevels(orgId)); } }
					 */
					model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
				}
				if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FIELD_WISE_STATUS
						.equalsIgnoreCase(lookUp.getLookUpCode())) {
					if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE) || formMode.equals(FormMode.VIEW)) {

						final LookUp cmdFieldPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
								PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
								UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
						if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
							for (final TbAcCodingstructureMas master : tbCodingList) {
								if (cmdFieldPrefix.getLookUpId() == master.getComCpdId()) {
									defaultFieldFlag = master.getDefineOnflag();
									if (defaultFieldFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
										model.addAttribute(
												MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
												tbAcFieldMasterService.getFieldMasterStatusLastLevels(defaultOrgId,
														superUserOrganization, langId));
									} else {
										model.addAttribute(
												MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
												tbAcFieldMasterService.getFieldMasterStatusLastLevels(orgId,
														organisation, langId));
									}
								}
							}
						}
					}
					/*
					 * if (formMode.equals(FormMode.VIEW)) { if
					 * (defaultFieldFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
					 * model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.
					 * FIELD_MASTER_ITEMS,
					 * tbAcFieldMasterService.getFieldMasterLastLevels(defaultOrgId)); } else {
					 * model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.
					 * FIELD_MASTER_ITEMS, tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
					 * } }
					 */
					model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
				}
			}
		}
	}

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String create(final AccountBudgetOpenBalanceBean tbAcBugopenBalance, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
			throws Exception {
		String result = MainetConstants.BLANK;

		if (!bindingResult.hasErrors()) {
			tbAcBugopenBalance.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
			final UserSession userSession = UserSession.getCurrent();
			tbAcBugopenBalance.setOrgid(userSession.getOrganisation().getOrgid());
			tbAcBugopenBalance.setLangId(userSession.getLanguageId());
			tbAcBugopenBalance.setUserId(userSession.getEmployee().getEmpId());
			tbAcBugopenBalance.setLmoddate(new Date());
			tbAcBugopenBalance.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
			if (tbAcBugopenBalance.getOpnId() != null) {
				tbAcBugopenBalance.setUpdatedBy(userSession.getEmployee().getEmpId());
				tbAcBugopenBalance.setUpdatedDate(new Date());
				tbAcBugopenBalance.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
			}
			populateModel(model, tbAcBugopenBalance, FormMode.CREATE);
			AccountBudgetOpenBalanceBean tbAcBugopenBalanceCreated = tbAcBugopenBalanceService
					.saveBudgetOpeningBalanceFormData(tbAcBugopenBalance);
			if (tbAcBugopenBalanceCreated == null) {
				tbAcBugopenBalanceCreated = new AccountBudgetOpenBalanceBean();
			}
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBugopenBalanceCreated);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			if (tbAcBugopenBalance.getOpnId() == null) {
				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
			}
			if (tbAcBugopenBalance.getOpnId() != null) {
				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
			}
			result = JSP_FORM;
		} else {
			tbAcBugopenBalance.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			populateModel(model, tbAcBugopenBalance, FormMode.CREATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(AccountBudgetOpenBalanceBean tbAcBugopenBalance,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_ID) final Long opnId,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws Exception {
		String result = MainetConstants.BLANK;
		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.EDIT)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			tbAcBugopenBalance.setOpnId(opnId);
			final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
			final int langId = UserSession.getCurrent().getLanguageId();
			tbAcBugopenBalance = tbAcBugopenBalanceService.getDetailsUsingOpnId(tbAcBugopenBalance, orgid);
			final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LIABILITY_OPN_STATUS,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
			final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.ASSET_OPN_BAL_TYPES,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
			if ((tbAcBugopenBalance.getOpnBalType() != null) && !tbAcBugopenBalance.getOpnBalType().isEmpty()) {
				final String cpdId = tbAcBugopenBalance.getOpnBalType();
				if (cpdId.equals(drLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapLiability = new LinkedHashMap<>();
					final List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllStatusLastLevelsLiability(orgid, superUserOrganization, langId);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapLiability.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapLiability);
				}
				if (cpdId.equals(crLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapAsset = new LinkedHashMap<>();
					final List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllStatusLastLevelsAsset(orgid, superUserOrganization, langId);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapAsset.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapAsset);
				}
			}
			populateModel(model, tbAcBugopenBalance, FormMode.UPDATE);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBugopenBalance);
			result = JSP_FORM;
		} else {
			log("BudgetopenBalanceMaster 'update' : binding errors");
			populateModel(model, tbAcBugopenBalance, FormMode.UPDATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(AccountBudgetOpenBalanceBean tbAcBugopenBalance,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_ID) final Long opnId,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws Exception {
		String result = MainetConstants.BLANK;
		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.VIEW)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			tbAcBugopenBalance.setOpnId(opnId);
			final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			tbAcBugopenBalance = tbAcBugopenBalanceService.getDetailsUsingOpnId(tbAcBugopenBalance, orgid);
			if (tbAcBugopenBalance.getFaYearid() != null) {
				tbAcBugopenBalance.setFinancialYearDesc(
						tbFinancialyearService.findFinancialYearDesc(tbAcBugopenBalance.getFaYearid()));
			}
			final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LIABILITY_OPN_STATUS,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
			final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.ASSET_OPN_BAL_TYPES,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
			if ((tbAcBugopenBalance.getOpnBalType() != null) && !tbAcBugopenBalance.getOpnBalType().isEmpty()) {
				final String cpdId = tbAcBugopenBalance.getOpnBalType();
				if (cpdId.equals(drLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapLiability = new LinkedHashMap<>();
					final List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllLastLevelsLiability(orgid);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapLiability.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapLiability);
				}
				if (cpdId.equals(crLookup.getLookUpCode())) {
					final Map<Long, String> pacHeadMapAsset = new LinkedHashMap<>();
					final List<AccountBudgetOpenBalanceMasterDto> listOfPrimaryAcHeadCode = tbAcPrimaryheadMasterService
							.getPrimaryHeadCodeAllLastLevelsAsset(orgid);
					for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : listOfPrimaryAcHeadCode) {
						if ((accountBudgetOpenBalanceMasterDto.getSacHeadId() != null)
								&& ((accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster() != null)
										&& !accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster()
												.isEmpty())) {
							pacHeadMapAsset.put(accountBudgetOpenBalanceMasterDto.getSacHeadId(),
									accountBudgetOpenBalanceMasterDto.getSacHeadCodeOpenBalanceMaster());
						}
					}
					model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST,
							pacHeadMapAsset);
				}
			}
			populateModel(model, tbAcBugopenBalance, FormMode.VIEW);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("BudgetopenBalanceMaster 'view' : view done - redirect");
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBugopenBalance);
			result = JSP_VIEW;
		} else {
			log("BudgetopenBalanceMaster 'view' : binding errors");
			populateModel(model, tbAcBugopenBalance, FormMode.VIEW);
			result = JSP_VIEW;
		}
		return result;
	}

	@RequestMapping(params = "getAmountIndianCurrencyFormat", method = RequestMethod.POST)
	public @ResponseBody String getIndianCurrencyFormatterAmount(final AccountBudgetOpenBalanceBean bean,
			final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {

		final String openbalAmt = bean.getBugReappMasterDtoList().get(cont).getOpenbalAmt()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final BigDecimal bd = new BigDecimal(openbalAmt);
		final String amount = CommonMasterUtility.getAmountInIndianCurrency(bd);
		return amount;
	}

	@RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
	public String exportImportExcelTemplate(final Model model) throws Exception {
		log("AccountBudgetOpenBalanceMaster-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final AccountBudgetOpenBalanceBean bean = new AccountBudgetOpenBalanceBean();
		populateModel(model, bean, FormMode.CREATE);
		fileUpload.sessionCleanUpForFileUpload();
		result = JSP_EXCELUPLOAD;
		return result;
	}

	@RequestMapping(params = "ExcelTemplateData")
	public void exportAccountBudgetOpenBalanceMasterExcelData(final HttpServletResponse response,
			final HttpServletRequest request) {

		try {
			WriteExcelData<AccountBudgetOpenBalanceUploadDto> data = new WriteExcelData<>(
					MainetConstants.ACCOUNTBUDGETOPENBALANCEMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request,
					response);

			data.getExpotedExcelSheet(new ArrayList<AccountBudgetOpenBalanceUploadDto>(),
					AccountBudgetOpenBalanceUploadDto.class);
		} catch (Exception ex) {
			throw new FrameworkException(ex.getMessage());
		}
	}

	@RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
	public String loadValidateAndLoadExcelData(AccountBudgetOpenBalanceBean bean, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
			throws Exception {

		log("Action 'loadExcelData'");

		final ApplicationSession session = ApplicationSession.getInstance();
		final UserSession userSession = UserSession.getCurrent();
		final Long orgId = userSession.getOrganisation().getOrgid();
		final int langId = userSession.getLanguageId();
		final Long userId = userSession.getEmployee().getEmpId();
		final String filePath = getUploadedFinePath();
		ReadExcelData<AccountBudgetOpenBalanceUploadDto> data = new ReadExcelData<>(filePath,
				AccountBudgetOpenBalanceUploadDto.class);
		data.parseExcelList();
		List<String> errlist = data.getErrorList();
		if (!errlist.isEmpty()) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.empty.excel")));
		} else {
			final List<AccountBudgetOpenBalanceUploadDto> accountBudgetOpenBalanceUploadDtos = data.getParseData();
			final LookUp sliLivePrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
			Map<Long, String> financeMap = null;
			Date sliDate = null;
			if (sliLivePrefix == null) {
				throw new NullPointerException(MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_MODE);
			} else {
				final String sliLivePrefixDate = sliLivePrefix.getOtherField();
				final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
				try {
					sliDate = formatter.parse(sliLivePrefixDate);
				} catch (final ParseException e) {

					LOGGER.error("Go-live date not set in (DD/MM/YYYY) format in other value of SLI prefix", e);
					throw new IllegalArgumentException(
							MainetConstants.AccountBudgetOpenBalance.SLI_PREFIX_LIVE_DATE + sliLivePrefixDate, e);

				}
				financeMap = tbFinancialyearService.getAllSLIPrefixDateFinincialYear(sliDate);
			}
			final List<LookUp> levelMap = CommonMasterUtility.getListLookup(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
					UserSession.getCurrent().getOrganisation());
			Map<Long, String> secondaryHeadDescMap = tbAcSecondaryheadMasterService.getAcHeadCode(orgId);
			final List<LookUp> drCrLevelMap = CommonMasterUtility.getListLookup(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE,
					UserSession.getCurrent().getOrganisation());
			AccountBudgetOpenBalanceExcelValidator validator = new AccountBudgetOpenBalanceExcelValidator();
			List<AccountBudgetOpenBalanceUploadDto> accountBudgetOpenBalanceUploadDtosUploadList = validator
					.excelValidation(accountBudgetOpenBalanceUploadDtos, bindingResult, financeMap, levelMap,
							secondaryHeadDescMap, drCrLevelMap);
			for (AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto : accountBudgetOpenBalanceUploadDtosUploadList) {
				Long faYearid = Long.valueOf(accountBudgetOpenBalanceUploadDto.getFinancialYear());
				String cpdIdDrcr = accountBudgetOpenBalanceUploadDto.getHeadCategory();
				Long sacHeadId = null;
				if (accountBudgetOpenBalanceUploadDto.getAccountHeads() != null
						&& !accountBudgetOpenBalanceUploadDto.getAccountHeads().isEmpty()) {
					sacHeadId = Long.valueOf(accountBudgetOpenBalanceUploadDto.getAccountHeads());
				}
				Long fundId = null;
				Long fieldId = null;
				if (tbAcBugopenBalanceService.isCombinationExists(faYearid, fundId, fieldId, cpdIdDrcr, sacHeadId,
						orgId)) {
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
							false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("bug.openbal.master.DupExist") + " - "
									+ accountBudgetOpenBalanceUploadDto.getDupAccountHead()));
					break;
				}
			}

			for (AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto1 : accountBudgetOpenBalanceUploadDtosUploadList) {
				if ((accountBudgetOpenBalanceUploadDto1.getAccountHeads() != null
						&& !accountBudgetOpenBalanceUploadDto1.getAccountHeads().isEmpty())
						&& (accountBudgetOpenBalanceUploadDto1.getHeadCategoryId() != null)) {
					if (tbAcBugopenBalanceService.isAcHeadWiseHeadCategoryExists(
							Long.valueOf(accountBudgetOpenBalanceUploadDto1.getAccountHeads()),
							accountBudgetOpenBalanceUploadDto1.getHeadCategoryId(), orgId)) {
						bindingResult.addError(new org.springframework.validation.FieldError(
								MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK,
								null, false, new String[] { MainetConstants.ERRORS }, null,
								session.getMessage("bug.openbal.master.catagoryHeadExists") + " - "
										+ accountBudgetOpenBalanceUploadDto1.getDupAccountHead()));
						break;
					}
				}
			}

			if (!bindingResult.hasErrors()) {
				for (AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto : accountBudgetOpenBalanceUploadDtosUploadList) {

					accountBudgetOpenBalanceUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					accountBudgetOpenBalanceUploadDto.setLangId(Long.valueOf(langId));
					accountBudgetOpenBalanceUploadDto.setUserId(userId);
					accountBudgetOpenBalanceUploadDto.setLmoddate(new Date());
					accountBudgetOpenBalanceUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
					tbAcBugopenBalanceService.saveAccountBudgetopeningBalance(accountBudgetOpenBalanceUploadDto, orgId,
							langId);
				}

				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						session.getMessage("accounts.success.excel"));
			}
		}
		model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
		fileUpload.sessionCleanUpForFileUpload();
		return JSP_EXCELUPLOAD;
	}

	private String getUploadedFinePath() {
		String filePath = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			Set<File> list = entry.getValue();
			for (final File file : list) {
				filePath = file.toString();
				break;
			}
		}
		return filePath;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
	public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
		final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
				browserType);
		return jsonViewObject;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
	public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
			@RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
	public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
			final HttpServletRequest httpServletRequest, @RequestParam final String browserType,
			@RequestParam(name = "uniqueId", required = false) final Long uniqueId) {
		UserSession.getCurrent().setBrowserType(browserType);
		JsonViewObject jsonViewObject = JsonViewObject.successResult();
		jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
		return jsonViewObject;
	}

	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}
}
