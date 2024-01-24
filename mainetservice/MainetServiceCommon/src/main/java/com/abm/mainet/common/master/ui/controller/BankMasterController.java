package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.dto.BankMasterUploadDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.ui.validator.BankMasterValidator;
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
import com.ctc.wstx.util.StringUtil;

/**
 * @author prasad.kancharla
 *
 */
@Controller
@RequestMapping("/GeneralBankMaster.html")
public class BankMasterController extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbBankMaster";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM = "tbBankMaster/form";
	private static final String JSP_VIEW = "tbBankMaster/view";
	private static final String JSP_LIST = "tbBankMaster/list";
	private static final String JSP_EXCELUPLOAD = "tbBankMaster/ExcelUpload";
	private String modeView = MainetConstants.BLANK;

	@Resource
	private BankMasterService bankMasterService;

	private List<BankMasterDTO> chList = null; // new ArrayList<BankMasterDTO>();

	public BankMasterController() {
		super(BankMasterController.class, MAIN_ENTITY_NAME);
		log("BankMasterController created.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(params = "getGridData", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
			final Model model) {
		log("BankMasterController-'gridData' : 'Get grid Data'");
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
			@RequestParam("bank") final String bank, @RequestParam("branch") final String branch,
			@RequestParam("ifsc") final String ifsc, @RequestParam("micr") final String micr,
			@RequestParam("city") final String city) {
		log("BankMasterController-'getjqGridsearch' : 'get jqGrid search data'");
		chList = new ArrayList<>();
		chList.clear();
		chList = bankMasterService.findByAllGridSearchData(bank, branch, ifsc, micr, city);
		return chList;
	}

	@RequestMapping()
	public String getList(final Model model) throws Exception {
		log("BankMasterController-'list' :'list'");
		helpDoc("GeneralBankMaster.html", model);
		String result = MainetConstants.BLANK;
		chList = new ArrayList<>();
		final List<LookUp> listBank = new ArrayList<>(0);
		LookUp lookupBank = null;
		final Organisation org = UserSession.getCurrent().getOrganisation();

		final List<LookUp> listBranch = new ArrayList<>(0);
		LookUp lookupBranch = null;

		final List<LookUp> listIfsc = new ArrayList<>(0);
		LookUp lookupIfsc = null;

		final List<LookUp> listMicr = new ArrayList<>(0);
		LookUp lookupMicr = null;

		final List<LookUp> listCity = new ArrayList<>(0);
		LookUp lookupCity = null;

		chList.clear();
		final List<BankMasterDTO> bankList = bankMasterService.findByAllGridData();
		for (final BankMasterDTO bankMaster : bankList) {
			// sort bank
			lookupBank = new LookUp();
			if (bankMaster.getBank() != null) {
				lookupBank.setDescLangFirst(bankMaster.getBank());
				lookupBank.setDescLangSecond(bankMaster.getBank());
				lookupBank.setLookUpDesc(bankMaster.getBank());
				listBank.add(lookupBank);
			}

			// sort branch
			lookupBranch = new LookUp();
			if (bankMaster.getBranch() != null) {
				lookupBranch.setDescLangFirst(bankMaster.getBranch());
				lookupBranch.setDescLangSecond(bankMaster.getBranch());
				lookupBranch.setLookUpDesc(bankMaster.getBranch());
				listBranch.add(lookupBranch);
			}

			// sort ifsc
			lookupIfsc = new LookUp();
			if (bankMaster.getIfsc() != null) {
				lookupIfsc.setDescLangFirst(bankMaster.getIfsc());
				lookupIfsc.setDescLangSecond(bankMaster.getIfsc());
				lookupIfsc.setLookUpDesc(bankMaster.getIfsc());
				listIfsc.add(lookupIfsc);
			}

			// sort micr
			lookupMicr = new LookUp();
			if (bankMaster.getMicr() != null) {
				lookupMicr.setDescLangFirst(bankMaster.getMicr());
				lookupMicr.setDescLangSecond(bankMaster.getMicr());
				lookupMicr.setLookUpDesc(bankMaster.getMicr());
				listMicr.add(lookupMicr);
			}

			// sort city
			lookupCity = new LookUp();
			if (bankMaster.getCity() != null) {
				lookupCity.setDescLangFirst(bankMaster.getCity());
				lookupCity.setDescLangSecond(bankMaster.getCity());
				lookupCity.setLookUpDesc(bankMaster.getCity());
				listCity.add(lookupCity);
			}
		}
		Collections.sort(listBank);
		final List<LookUp> newBankList = new ArrayList<>(new LinkedHashSet<>(listBank));
		model.addAttribute("listBank", newBankList);

		Collections.sort(listBranch);
		final List<LookUp> newBranchList = new ArrayList<>(new LinkedHashSet<>(listBranch));
		model.addAttribute("listBranch", newBranchList);

		Collections.sort(listIfsc);
		final List<LookUp> newIfscList = new ArrayList<>(new LinkedHashSet<>(listIfsc));
		model.addAttribute("listIfsc", newIfscList);

		Collections.sort(listMicr);
		final List<LookUp> newMicrList = new ArrayList<>(new LinkedHashSet<>(listMicr));
		model.addAttribute("listMicr", newMicrList);

		Collections.sort(listCity);
		final List<LookUp> newCityList = new ArrayList<>(new LinkedHashSet<>(listCity));
		model.addAttribute("listCity", newCityList);

		if ((org.getDefaultStatus() != null) && (org.getDefaultStatus() != MainetConstants.BLANK)) {
			model.addAttribute("isDefault", MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute("isDefault", MainetConstants.Common_Constant.NO);
		}

		final BankMasterDTO bean = new BankMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);

		result = JSP_LIST;
		return result;
	}

	@RequestMapping(params = "form")
	public String formForCreate(final Model model) throws Exception {
		log("BankMasterController-'formForCreate' : 'formForCreate'");
		String result = MainetConstants.BLANK;
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final List<LookUp> acnPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.ACN, org);
		model.addAttribute("acnPrefixList", acnPrefixList);
		final BankMasterDTO bean = new BankMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
        //#140119
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SFAC)) {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.NO);
		}
		result = JSP_FORM;
		return result;
	}

	private void populateModel(final Model model, final BankMasterDTO bean, final FormMode formMode) throws Exception {
		log("BankMasterController-'populateModel' : populate model");
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		if (formMode == FormMode.CREATE) {
			model.addAttribute("MODE_DATA", "create");
			model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
		} else if (formMode == FormMode.UPDATE) {
			model.addAttribute("MODE_DATA", "update");
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.NO);
		}
	}

	@RequestMapping(params = "getDuplicateIFSCCodeExit", method = RequestMethod.POST)
	public @ResponseBody boolean getDuplicateIFSCCodeExit(final BankMasterDTO dto, final HttpServletRequest request,
			final Model model, final BindingResult bindingResult) {
		boolean isValidationError = false;
		final String ifsc = dto.getIfsc();
		if (StringUtils.isNotEmpty(ifsc)) {
		final BankMasterEntity bankMstEntity = bankMasterService.isCombinationIfscCodeExists(ifsc);
			if (bankMstEntity != null) {
				if (!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)
						&& (StringUtils.isNotBlank(dto.getBank()))
								&& StringUtils.equalsIgnoreCase(dto.getBank(), MainetConstants.BankAccountMaster.GRAMIN_BANK))) {
					if (dto.getBankId() != null) {
						if (!(bankMstEntity.getBankId().equals(dto.getBankId()))) {
							bindingResult.addError(
									new org.springframework.validation.FieldError("tbBankMaster", MainetConstants.BLANK,
											null, false, new String[] { MainetConstants.CommonConstants.ERROR }, null,
											ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
							isValidationError = true;
						}

					} else {
						bindingResult.addError(
								new org.springframework.validation.FieldError("tbBankMaster", MainetConstants.BLANK,
										null, false, new String[] { MainetConstants.CommonConstants.ERROR }, null,
										ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
						isValidationError = true;
					}
				}
			}	
		}
		return isValidationError;
	}

	@RequestMapping(params = "getDuplicateBranchNameExit", method = RequestMethod.POST)
	public @ResponseBody boolean getDuplicateBranchNameExit(final BankMasterDTO dto, final HttpServletRequest request,
			final Model model, final BindingResult bindingResult) {
		boolean isValidationError = false;
		final String bank = dto.getBank();
		final String branch = dto.getBranch();
		final BankMasterEntity bankMstEntity = bankMasterService.isCombinationBranchNameExists(bank, branch);
		if (bankMstEntity != null) {
			bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster", MainetConstants.BLANK,
					null, false, new String[] { MainetConstants.CommonConstants.ERROR }, null,
					ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
			isValidationError = true;
		}
		return isValidationError;
	}

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String create(final BankMasterDTO tbBankMaster, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
		log("BankMasterDTO-'create/update' : 'create/update'");
		String result = MainetConstants.BLANK;

		// Validations on Branch name, IFSC code and MICR code
		final BankMasterEntity bankMstBankBranch = bankMasterService
				.isCombinationBranchNameExists(tbBankMaster.getBank(), tbBankMaster.getBranch());
		if (bankMstBankBranch != null) { // bankMstBankBranch name checking null updated by ajay kumar 22/07/2019
			if (tbBankMaster.getBankId() != null) {
				if (!tbBankMaster.getBankId().equals(bankMstBankBranch.getBankId())) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.branch")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			} else {
				if (bankMstBankBranch != null) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.branch")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			}
			final BankMasterEntity bankMstIfsc = bankMasterService.isCombinationIfscCodeExists(tbBankMaster.getIfsc());
			if (tbBankMaster.getBankId() != null) {
				if (!tbBankMaster.getBankId().equals(bankMstBankBranch.getBankId())) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.ifsc")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			} else {
				if (bankMstIfsc != null) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.ifsc")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			}
			final BankMasterEntity bankMstMicr = bankMasterService.isDuplicateMICR(tbBankMaster.getMicr());
			if (tbBankMaster.getBankId() != null) {
				if (!tbBankMaster.getBankId().equals(bankMstBankBranch.getBankId())) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.micr")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			} else {
				if (bankMstMicr != null) {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage("bank.error.duplicate.micr")
									+ tbBankMaster.getBank().toUpperCase()));
				}
			}
		}
		if (!bindingResult.hasErrors()) {
			tbBankMaster.setHasError(MainetConstants.MENU.FALSE);
			final UserSession userSession = UserSession.getCurrent();
			tbBankMaster.setLangId(Long.valueOf(userSession.getLanguageId()));
			tbBankMaster.setCreatedBy(userSession.getEmployee().getEmpId());
			tbBankMaster.setLmodDate(new Date());

			if (tbBankMaster.getBankId() == null) {
				tbBankMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				tbBankMaster.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}

			// Trim the trailing spaces
			if (tbBankMaster.getBank() != null)
				tbBankMaster.setBank(tbBankMaster.getBank().trim().toUpperCase());
			tbBankMaster.setBranch(tbBankMaster.getBranch().trim().toUpperCase());
			tbBankMaster.setCity(tbBankMaster.getCity().trim().toUpperCase());
			tbBankMaster.setDistrict(tbBankMaster.getDistrict().trim().toUpperCase());
			tbBankMaster.setState(tbBankMaster.getState().trim().toUpperCase());
			tbBankMaster.setAddress(tbBankMaster.getAddress().toUpperCase());
			tbBankMaster.setIfsc(tbBankMaster.getIfsc().toUpperCase());
			if (tbBankMaster.getBankId() == null) {
				tbBankMaster.setBankStatus(MainetConstants.STATUS.ACTIVE);
			}

			populateModel(model, tbBankMaster, FormMode.CREATE);
			BankMasterDTO tbBankMasterCreated = bankMasterService.saveBankMasterFormData(tbBankMaster);
			if (tbBankMasterCreated == null) {
				tbBankMasterCreated = new BankMasterDTO();
			}
			model.addAttribute(MAIN_ENTITY_NAME, tbBankMasterCreated);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, "Save ok"));
			if (tbBankMaster.getBankId() == null) {
				model.addAttribute("keyTest", "Record saved successfully");
			}
			if (tbBankMaster.getBankId() != null) {
				model.addAttribute("keyTest", "Record updated successfully");
			}
			result = JSP_FORM;
		} else {
			tbBankMaster.setHasError(MainetConstants.MENU.TRUE);
			model.addAttribute("modeView", getModeView());
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND, bindingResult);
			populateModel(model, tbBankMaster, FormMode.CREATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(BankMasterDTO tbBankMaster, @RequestParam("bankId") final Long bankId,
			@RequestParam("MODE_DATA") final String viewmode, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
		log("tbBankMaster-'gridData' : 'update'");
		String result = MainetConstants.BLANK;

		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.CommonConstants.EDIT)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.CommonConstants.EDIT);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, false);
			}
			setModeView(viewmode);

			final Organisation org = UserSession.getCurrent().getOrganisation();
			final List<LookUp> acnPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.ACN, org);
			model.addAttribute("acnPrefixList", acnPrefixList);

			tbBankMaster.setBankId(bankId);
			tbBankMaster = bankMasterService.getDetailsUsingBankId(tbBankMaster);
			model.addAttribute(MAIN_ENTITY_NAME, tbBankMaster);
			populateModel(model, tbBankMaster, FormMode.UPDATE);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, "Save ok"));
			log("BankMaster 'update' : update done - redirect");
			model.addAttribute("MODE_DATA", viewmode);
			model.addAttribute("modeView", getModeView());
			log("BankMaster 'update' : update done - redirect");
			result = JSP_FORM;
		} else {
			log("BankMaster 'update' : binding errors");
			populateModel(model, tbBankMaster, FormMode.UPDATE);
			result = JSP_FORM;
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.YES);
		} else {
			model.addAttribute("envFlag", MainetConstants.Common_Constant.NO);
		}
		return result;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(BankMasterDTO tbBankMaster, @RequestParam("bankId") final Long bankId,
			@RequestParam("MODE_DATA") final String viewmode, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
		log("tbBankMaster-'gridData' : 'view'");
		String result = MainetConstants.BLANK;
		final Organisation org = UserSession.getCurrent().getOrganisation();
		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.CommonConstants.VIEW)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.CommonConstants.VIEW);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, false);
			}
			final List<LookUp> acnPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.ACN, org);
			model.addAttribute("acnPrefixList", acnPrefixList);
			setModeView(viewmode);
			tbBankMaster.setBankId(bankId);
			tbBankMaster = bankMasterService.getDetailsUsingBankId(tbBankMaster);
			model.addAttribute(MAIN_ENTITY_NAME, tbBankMaster);
			populateModel(model, tbBankMaster, FormMode.VIEW);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, "Save ok"));
			log("BankMaster 'view' : view done - redirect");
			model.addAttribute("MODE_DATA", viewmode);
			model.addAttribute("modeView", getModeView());
			log("BankMaster 'view' : view done - redirect");
			result = JSP_VIEW;
		} else {
			log("BankMaster 'view' : binding errors");
			populateModel(model, tbBankMaster, FormMode.VIEW);
			result = JSP_VIEW;
		}
		return result;
	}

	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}

	@RequestMapping(params = "getDuplicateMICR", method = RequestMethod.POST)
	public @ResponseBody boolean getDuplicateMICR(final BankMasterDTO dto, final HttpServletRequest request,
			final Model model, final BindingResult bindingResult) {
		boolean isValidationError = false;
		final String MICR = dto.getMicr();
		if  (StringUtils.isNotEmpty(MICR)) {                                                                                    
		final BankMasterEntity bankMstEntity = bankMasterService.isDuplicateMICR(MICR);
			if (bankMstEntity != null) {
				if (dto.getBankId() != null) {
					if (!(bankMstEntity.getBankId().equals(dto.getBankId()))) {
						bindingResult.addError(
								new org.springframework.validation.FieldError("tbBankMaster", MainetConstants.BLANK,
										null, false, new String[] { MainetConstants.CommonConstants.ERROR }, null,
										ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
						isValidationError = true;
					}
				} else {
					bindingResult.addError(new org.springframework.validation.FieldError("tbBankMaster",
							MainetConstants.BLANK, null, false, new String[] { MainetConstants.CommonConstants.ERROR },
							null, ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
					isValidationError = true;
				}

			}
		}
		return isValidationError;
	}

	@RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
	public String exportImportExcelTemplate(final Model model) throws Exception {
		log("BankMasterDTO-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final BankMasterDTO bean = new BankMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
		result = JSP_EXCELUPLOAD;
		return result;
	}

	@RequestMapping(params = "ExcelTemplateData")
	public void exportAccountDepositExcelData(final HttpServletResponse response, final HttpServletRequest request) {

		try {
			WriteExcelData<BankMasterUploadDTO> data = new WriteExcelData<>(
					MainetConstants.BANKMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request, response);

			data.getExpotedExcelSheet(new ArrayList<BankMasterUploadDTO>(), BankMasterUploadDTO.class);
		} catch (Exception ex) {
			throw new FrameworkException(ex.getMessage());
		}
	}

	@RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
	public String loadValidateAndLoadExcelData(BankMasterDTO bean, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {

		log("Action 'loadExcelData'");

		final ApplicationSession session = ApplicationSession.getInstance();
		// final boolean isDafaultOrgExist =
		// tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		// Organisation defaultOrg = null;
		// if (isDafaultOrgExist) {
		// defaultOrg = session.getSuperUserOrganization();
		// } else {
		// defaultOrg = UserSession.getCurrent().getOrganisation();
		// }
		final UserSession userSession = UserSession.getCurrent();
		final Long orgId = userSession.getOrganisation().getOrgid();
		final int langId = userSession.getLanguageId();
		final Long userId = userSession.getEmployee().getEmpId();
		final String filePath = getUploadedFinePath();
		int rowNo = 0;
		ReadExcelData<BankMasterUploadDTO> data = new ReadExcelData<>(filePath, BankMasterUploadDTO.class);
		data.parseExcelList();
		List<String> errlist = data.getErrorList();
		if (!errlist.isEmpty()) {
			bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.BANKMASTERUPLOADDTO,
					MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
					session.getMessage("accounts.empty.excel")));
		} else {
			final List<BankMasterUploadDTO> bankMasterUploadDtos = data.getParseData();

			BankMasterValidator validator = new BankMasterValidator();
			List<BankMasterUploadDTO> bankMasterUploadDtosUploadList = validator.excelValidation(bankMasterUploadDtos,
					bindingResult);
			// Validations on Branch name, IFSC code and MICR code
			for (BankMasterUploadDTO bankMasterUploadDto : bankMasterUploadDtosUploadList) {
				rowNo++;
				final BankMasterEntity bankMstIfsc = bankMasterService
						.isCombinationIfscCodeExists(bankMasterUploadDto.getIfscCode());
				if (bankMstIfsc != null) {
					bindingResult.addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO, MainetConstants.BLANK,
							null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("bank.error.duplicate.ifsc") + bankMasterUploadDto.getIfscCode()
									+ " for row number " + rowNo));
				}
				final BankMasterEntity bankMstBankBranch = bankMasterService.isCombinationBranchNameExists(
						bankMasterUploadDto.getBankName(), bankMasterUploadDto.getBankBranch());
				if (bankMstBankBranch != null) {
					bindingResult.addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO, MainetConstants.BLANK,
							null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("bank.error.duplicate.branch") + bankMasterUploadDto.getBankBranch()
									+ " for row number " + rowNo));
				}
                if (bankMasterUploadDto.getMicrCode() != null) {
				final BankMasterEntity bankMstMicr = bankMasterService
						.isDuplicateMICR(bankMasterUploadDto.getMicrCode().toString());
				if (bankMstMicr != null) {
					bindingResult.addError(new FieldError(MainetConstants.BANKMASTERUPLOADDTO, MainetConstants.BLANK,
							null, false, new String[] { MainetConstants.ERRORS }, null,
							session.getMessage("bank.error.duplicate.micr") + bankMasterUploadDto.getMicrCode()
									+ " for row number " + rowNo));
				}
                }
			}
			if (!bindingResult.hasErrors()) {
				for (BankMasterUploadDTO bankMasterUploadDto : bankMasterUploadDtosUploadList) {

					bankMasterUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
					bankMasterUploadDto.setLangId(Long.valueOf(langId));
					bankMasterUploadDto.setUserId(userId);
					bankMasterUploadDto.setLmoddate(new Date());
					bankMasterUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
					bankMasterService.saveBankMasterExcelData(bankMasterUploadDto, orgId, langId);
				}

				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						session.getMessage("accounts.success.excel"));
			}
		}
		model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));

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

	//#134943
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "getBankList")
	public List<BankMasterEntity> getBankList(final HttpServletRequest httpServletRequest) {
		List<BankMasterEntity> entity = bankMasterService.getBankList();
		return entity;
	}
}