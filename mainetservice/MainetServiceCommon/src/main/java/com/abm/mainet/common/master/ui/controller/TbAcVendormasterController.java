package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.VendorMasterUploadDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.ui.validator.VendorMasterValidator;
import com.abm.mainet.common.model.VendormasterResponse;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbAcVendormaster' management.
 */
@Controller
@RequestMapping("/Vendormaster.html")
public class TbAcVendormasterController extends AbstractController {
    private static final String MAIN_ENTITY_NAME = "tbAcVendormaster";
    private static final String JSP_FORM = "tbAcVendormaster/form";
    private static final String JSP_LIST = "tbAcVendormaster/list";
    private static final String JSP_VIEW = "tbAcVendormaster/view";
    private static final String JSP_EXCELUPLOAD = "tbAcVendormaster/ExcelUpload";
    private static final String SAVE_ACTION_CREATE = "Vendormaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "Vendormaster.html?update";
    private static final String JSP_REPORT = "tbAcVendormaster/report";
    private static final String VIEW = "View";
    List<LookUp> venderType;
    private List<TbAcVendormaster> chList = new ArrayList<>();
    private static final String LISTOFTBACFUNCTIONMASTERITEMS = "listOfTbAcFunctionMasterItems";
    // --- Main entity service
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    private TbCustbanksService tbCustbanksService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Autowired
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Autowired
    private IDmsService iDmsService;

    
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;

    @Resource
    private IEmployeeService employeeService;

    @Resource
    private TbBankmasterService banksMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private IAttachDocsService iAttachDocsService;
    
    @Autowired
	private DepartmentJpaRepository deptRepo;

    List<TbAcVendormaster> list = null;

    public TbAcVendormasterController() {
        super(TbAcVendormasterController.class, MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME);
        log("TbAcVendormasterController created.");
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     *
     * @param model
     * @param tbAcVendormaster
     * @throws Exception
     */
    private void populateModel(final Model model, final TbAcVendormaster tbAcVendormaster, final FormMode formMode)
            throws Exception {
        // --- Main entity

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        venderType = CommonMasterUtility.getLookUps(PrefixConstants.VNT, defaultOrg);
        if (formMode == FormMode.CREATE) {
            if (venderType != null) {
                for (final LookUp vendorTypeLookUp : venderType) {
                    if ((vendorTypeLookUp.getDefaultVal() != null) && !vendorTypeLookUp.getDefaultVal().isEmpty()) {
                        if (vendorTypeLookUp.getDefaultVal().equals(MainetConstants.MENU.Y)) {
                            if (tbAcVendormaster.getCpdVendortype() == null) {
                                tbAcVendormaster.setCpdVendortype(vendorTypeLookUp.getLookUpId());
                            }
                        }
                    }
                }
            }
        }
        final List<LookUp> vendorStatus = CommonMasterUtility.getLookUps(PrefixConstants.VST, defaultOrg);
        if (formMode == FormMode.CREATE) {
            if (vendorStatus != null) {
                for (final LookUp vendorSubTypeLookUp : vendorStatus) {
                    if ((vendorSubTypeLookUp.getDefaultVal() != null)
                            && !vendorSubTypeLookUp.getDefaultVal().isEmpty()) {
                        if (vendorSubTypeLookUp.getDefaultVal().equals(MainetConstants.MENU.Y)) {
                            if (tbAcVendormaster.getCpdVendorSubType() == null) {
                                tbAcVendormaster.setCpdVendorSubType(vendorSubTypeLookUp.getLookUpId());
                            }
                        }
                    }
                }
            }
        }
        final Map<Long, String> bankMap = new HashMap<>();
        final List<Object[]> blist = banksMasterService.findActiveBankList();
        for (final Object[] obj : blist) {
            bankMap.put((Long) obj[0],
                    obj[1] + MainetConstants.SEPARATOR + obj[2] + MainetConstants.SEPARATOR + obj[3]);
        }

        String sliMode = null;
        sliMode = tbAcVendormasterService.getCpdMode();

        final List<Employee> emplist = employeeService
                .findEmpList(UserSession.getCurrent().getOrganisation().getOrgid());
        if (formMode == FormMode.CREATE) {
            final LookUp vendorStatusAcIn = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
                    UserSession.getCurrent().getLanguageId(), defaultOrg);
            final List<LookUp> vendorStAc = new ArrayList<>();
            vendorStAc.add(vendorStatusAcIn);
            model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS, vendorStAc);
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);

            boolean primaryDefaultFlag = false;
            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                        MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            Organisation defaultorg = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                defaultorg = UserSession.getCurrent().getOrganisation();
            } else {
                defaultorg = UserSession.getCurrent().getOrganisation();
            }

            final int langId = UserSession.getCurrent().getLanguageId();
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId,
                    UserSession.getCurrent().getOrganisation());
            final Long lookUpStatusId = statusLookup.getLookUpId();
            final Long orgid = defaultorg.getOrgid();
            final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultorg);
            final Long cpdVendortype = vendorLookup.getLookUpId();
            Long cpdVendorSubType = null;
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && tbAcVendormaster.getCpdVendortype()==null) {
				final Organisation organisation = UserSession.getCurrent().getOrganisation();
				Long deptid = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = deptRepo.getDeptCode(deptid);
				LookUp lookup =null;
				if (deptCode.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.RNL)) {
					 lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TbAcVendormaster.TENANT, PrefixConstants.VNT,
							organisation);
				} else {
					 lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TbAcVendormaster.CONTRACTOR, PrefixConstants.VNT,
							organisation);
				}
				if(lookup.getLookUpId() != 0L) {
					tbAcVendormaster.setCpdVendortype(lookup.getLookUpId());
					cpdVendorSubType = tbAcVendormaster.getCpdVendortype();
				}
			}else {
				  cpdVendorSubType = tbAcVendormaster.getCpdVendortype();
			}
            if ((cpdVendortype != null) && (cpdVendorSubType != null)) {

                if (sliMode != null && !sliMode.isEmpty()) {
                    if (sliMode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
                        final Map<Long, String> venderPrimaryHead = tbAcPrimaryheadMasterService
                                .getVendorTypeWisePrimaryAcHead(cpdVendortype, cpdVendorSubType, lookUpStatusId, orgid);
                        model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS, venderPrimaryHead);
                    }
                }
            }

        } else if (formMode == FormMode.UPDATE) {
            final List<LookUp> vendorStatusAcIn = CommonMasterUtility.getLookUps(PrefixConstants.VSS, defaultOrg);
            model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS, vendorStatusAcIn);
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        }
        model.addAttribute(MainetConstants.TbAcChequebookleafMas.EMP_LIST, emplist);
        model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_TYPE, venderType);
        model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS_IN, vendorStatus);

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final Long orgid = organisation.getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();

        boolean functionDefaultFlag = false;
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : accountTypeLevel) {
            if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                    if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {

                        if (sliMode != null && !sliMode.isEmpty()) {
                            if (sliMode.equals("L")) {
                                if (isDafaultOrgExist && functionDefaultFlag) {
                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(
                                                    ApplicationSession.getInstance().getSuperUserOrganization()
                                                            .getOrgid(),
                                                    ApplicationSession.getInstance().getSuperUserOrganization(),
                                                    langId));
                                } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid,
                                                    organisation, langId));
                                } else {
                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid,
                                                    organisation, langId));
                                }
                                model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
                                        MainetConstants.MASTER.Y);
                            }
                        }
                    }
                }
            }
        }

        if (sliMode != null && !sliMode.isEmpty()) {
            tbAcVendormaster.setSliMode(sliMode);
        }
        model.addAttribute(MainetConstants.TbAcVendormaster.SLI_MODE, sliMode);

        final List<TbAcVendormaster> list = tbAcVendormasterService
                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        for (final TbAcVendormaster tbAcVendormasters : list) {
            tbAcVendormasters.setVenderCodeAndName(tbAcVendormasters.getVmVendorcode() + MainetConstants.SEPARATOR
                    + tbAcVendormasters.getVmVendorname());
        }

        model.addAttribute("vendorClass", CommonMasterUtility.getListLookup(PrefixConstants.TbAcVendormaster.VEC,
                UserSession.getCurrent().getOrganisation()));

        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME, tbAcVendormaster);
        model.addAttribute(MainetConstants.VENDOR_MASTER.CUST_BANKLIST, bankMap);
        model.addAttribute("SudaEnv", Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA));

    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model,@RequestParam(value = "leaseFlag", required = false) String leaseFlag) throws Exception {
        log("Action 'formForCreate'");
        // --- Populates the model with a new instance
        fileUpload.sessionCleanUpForFileUpload();
        final TbAcVendormaster tbAcVendormaster = new TbAcVendormaster();
        if(null !=leaseFlag) {
        	tbAcVendormaster.setLeaseFlag(leaseFlag);
        	 model.addAttribute(MainetConstants.RnLCommon.LEASE_FLAG,tbAcVendormaster.getLeaseFlag() );
        }
        populateModel(model, tbAcVendormaster, FormMode.CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(@RequestParam("vmVendorid") final Long vmVendorid, final Model model) throws Exception {
        log("Action 'formForUpdate'");

        fileUpload.sessionCleanUpForFileUpload();
        TbAcVendormaster tbAcVendormaster = null;
        String sliMode = null;
        sliMode = tbAcVendormasterService.getCpdMode();
        if (vmVendorid == null) {
            tbAcVendormaster = new TbAcVendormaster();
        } else {
            tbAcVendormaster = tbAcVendormasterService.findById(vmVendorid,
                    UserSession.getCurrent().getOrganisation().getOrgid());

            boolean primaryDefaultFlag = false;
            final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                        MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            Organisation defaultorg = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                defaultorg = UserSession.getCurrent().getOrganisation();
            } else {
                defaultorg = UserSession.getCurrent().getOrganisation();
            }

            final Organisation organisation = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
            final Long lookUpStatusId = statusLookup.getLookUpId();
            UserSession.getCurrent().getOrganisation().getOrgid();
            final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultorg);
            final Long cpdVendortype = vendorLookup.getLookUpId();
            final Long cpdVendorSubType = tbAcVendormaster.getCpdVendortype();
            if ((cpdVendortype != null) && (cpdVendorSubType != null)) {
                if (sliMode != null && !sliMode.isEmpty()) {
                    if (sliMode.equals("L")) {
                        final Map<Long, String> venderPrimaryHead = tbAcPrimaryheadMasterService
                                .getVendorTypeWisePrimaryAcHead(cpdVendortype, cpdVendorSubType, lookUpStatusId,
                                        defaultorg.getOrgid());
                        model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS, venderPrimaryHead);
                    }
                }
            }
            
            if(tbAcVendormaster !=null) {
                List<AttachDocs> attachDocs = iAttachDocsService.findByCode(tbAcVendormaster.getOrgid(), tbAcVendormaster.getVmVendorcode().toString());
                tbAcVendormaster.setAttachDocsList(attachDocs);
                }
             
        }

        if (sliMode != null && !sliMode.isEmpty()) {
            if (sliMode.equals("L")) {
                final Long functionId = tbAcSecondaryheadMasterService
                        .vendorIdWiseGetFunctionIdValue(tbAcVendormaster.getVmVendorid());
                if (functionId != null) {
                    tbAcVendormaster.setFunctionId(functionId);
                }
            }
        }
        populateModel(model, tbAcVendormaster, FormMode.UPDATE);
        boolean isDMS = false;
		if (MainetConstants.Common_Constant.YES
				.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
			isDMS = true;
		}
        model.addAttribute("isDMS", isDMS);
        return JSP_FORM;
    }

    @RequestMapping(params = "getVendorTypeWisePrimaryAcHead", method = RequestMethod.POST)
    public String getVendorTypeWisePrimaryAcHead(final TbAcVendormaster tbAcVendormaster, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log("tbAcVendormaster-'getVendorTypeWisePrimaryAcHead' : 'getVendorTypeWisePrimaryAcHead data'");
        String result = MainetConstants.CommonConstant.BLANK;
        boolean primaryDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Organisation defaultorg = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            defaultorg = UserSession.getCurrent().getOrganisation();
        } else {
            defaultorg = UserSession.getCurrent().getOrganisation();
        }

        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId,
                UserSession.getCurrent().getOrganisation());
        final Long lookUpStatusId = statusLookup.getLookUpId();
        final Long orgId = defaultorg.getOrgid();
        final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultorg);
        final Long cpdVendortype = vendorLookup.getLookUpId();
        final Long cpdVendorSubType = tbAcVendormaster.getCpdVendortype();

        if ((cpdVendortype != null) && (cpdVendorSubType != null)) {
            final Map<Long, String> venderPrimaryHead = tbAcPrimaryheadMasterService
                    .getVendorTypeWisePrimaryAcHead(cpdVendortype, cpdVendorSubType, lookUpStatusId, orgId);
            model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS, venderPrimaryHead);
        }
        populateModel(model, tbAcVendormaster, FormMode.CREATE);
        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME, tbAcVendormaster);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "create")
    // GET or POST
    public ModelAndView create(@Valid final TbAcVendormaster tbAcVendormaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'create'");
        final VendorMasterValidator validator = new VendorMasterValidator();
            	validator.setTbAcVendormasterService(tbAcVendormasterService);
                validator.validate(tbAcVendormaster, bindingResult);
        
        if (!bindingResult.hasErrors()) {

            final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
            Organisation defaultOrg = null;
            if (isDafaultOrgExist) {
                defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
            } else {
                defaultOrg = UserSession.getCurrent().getOrganisation();
            }
            final LookUp secndVendorType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.TbAcVendormaster.VD,
                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getLanguageId(), defaultOrg);
            final Organisation org = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            tbAcVendormaster.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcVendormaster.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcVendormaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            tbAcVendormaster.setLangId((long) UserSession.getCurrent().getLanguageId());
            if(tbAcVendormaster.getVendorClass() !=  null) {
            	tbAcVendormaster.setVendorClassName(CommonMasterUtility
                        .getNonHierarchicalLookUpObjectByPrefix(tbAcVendormaster.getVendorClass(), tbAcVendormaster.getOrgid(),
                                PrefixConstants.TbAcVendormaster.VEC)
                        .getDescLangFirst());
            }                            
            final TbAcVendormaster tbAcVendormasterCreated = tbAcVendormasterService.create(tbAcVendormaster,
                    venderType, secndVendorType.getLookUpId(), defaultOrg, org, langId);
            
            //File Upload  #40590				   		
            List<DocumentDetailsVO> docs =new ArrayList<>();
            docs=fileUpload.setFileUploadMethod(docs);
            if(!docs.isEmpty()) {
            	 FileUploadDTO fileUploadDTO = new FileUploadDTO();                                                       
                 fileUploadDTO.setIdfId(tbAcVendormasterCreated.getVmVendorcode().toString());
                 tbAcVendormaster.setAttach(docs);
                 tbAcVendormasterService.documentUpload(tbAcVendormaster.getAttach(), fileUploadDTO, UserSession.getCurrent().getEmployee().getEmpId(),
                 		UserSession.getCurrent().getOrganisation().getOrgid());        
            }
    		/*if (tbAcVendormaster.getAttach() !=null && !tbAcVendormaster.getAttach().isEmpty()) {
                FileUploadDTO fileUploadDTO = new FileUploadDTO();                                                       
                fileUploadDTO.setIdfId(tbAcVendormasterCreated.getVmVendorid().toString());
                tbAcVendormaster.setAttach(fileUpload.setFileUploadMethod(tbAcVendormaster.getAttach()));
                tbAcVendormasterService.documentUpload(tbAcVendormaster.getAttach(), fileUploadDTO, UserSession.getCurrent().getEmployee().getEmpId(),
                		UserSession.getCurrent().getOrganisation().getOrgid());                   
            } */  
            //vendorMasUploadFile(tbAcVendormasterCreated);
            //File Upload end
            model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME, tbAcVendormasterCreated);
            messageHelper.addMessage(redirectAttributes,
                    new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                    MainetConstants.Req_Status.SUCCESS);

        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcVendormaster, FormMode.CREATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    private void vendorMasUploadFile(TbAcVendormaster tbAcVendormasterCreated) {
    	List<DocumentDetailsVO> docs = new ArrayList<>(0);
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
             && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
         for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
             final List<File> list = new ArrayList<>(entry.getValue());
             for (final File file : list) {
                 try {
                     DocumentDetailsVO d = new DocumentDetailsVO();
                     final Base64 base64 = new Base64();
                     final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                     d.setDocumentByteCode(bytestring);
                     d.setDocumentName(file.getName());
                     docs.add(d);
                 } catch (final IOException e) {
                 }
             }
         }
     }
        
        FileUploadDTO fileUploadDTO = new FileUploadDTO();                                                       
        fileUploadDTO.setIdfId(tbAcVendormasterCreated.getVmVendorid().toString());
        //tbAcVendormaster.setAttach(fileUpload.setFileUploadMethod(tbAcVendormaster.getAttach()));
        tbAcVendormasterService.documentUpload(docs, fileUploadDTO, UserSession.getCurrent().getEmployee().getEmpId(),
        		UserSession.getCurrent().getOrganisation().getOrgid());   
        
		
	}

	@RequestMapping(params = "update")
    // GET or POST
    public ModelAndView update(@Valid final TbAcVendormaster tbAcVendormaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'update'");
        if (!bindingResult.hasErrors()) {
            tbAcVendormaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcVendormaster.setUpdatedDate(new Date());
            tbAcVendormaster.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            tbAcVendormaster.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcVendormaster.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcVendormaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            tbAcVendormaster.setLangId((long) UserSession.getCurrent().getLanguageId());
            if(tbAcVendormaster.getVendorClass()!=null) {
            tbAcVendormaster.setVendorClassName(CommonMasterUtility
                    .getNonHierarchicalLookUpObjectByPrefix(tbAcVendormaster.getVendorClass(), tbAcVendormaster.getOrgid(),
                            PrefixConstants.TbAcVendormaster.VEC)
                    .getDescLangFirst());
            }
            final TbAcVendormaster tbAcVendormasterSaved = tbAcVendormasterService.update(tbAcVendormaster,
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
            
            //File Upload end
            List<DocumentDetailsVO> docs =new ArrayList<>();
            docs=fileUpload.setFileUploadMethod(docs);
            if(!docs.isEmpty()) {
            	 FileUploadDTO fileUploadDTO = new FileUploadDTO();                                                       
                 fileUploadDTO.setIdfId(tbAcVendormasterSaved.getVmVendorcode().toString());
                 tbAcVendormaster.setAttach(docs);
                 tbAcVendormasterService.documentUpload(tbAcVendormaster.getAttach(), fileUploadDTO, UserSession.getCurrent().getEmployee().getEmpId(),
                 		UserSession.getCurrent().getOrganisation().getOrgid());        
            }
            List<Long> removeFileById = null;
			String fileId = tbAcVendormaster.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray) {
					removeFileById.add(Long.valueOf(fields));
				}
			}
			if (removeFileById != null && !removeFileById.isEmpty()) {				
				tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, tbAcVendormaster.getUpdatedBy());
			}

            model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME, tbAcVendormasterSaved);
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                    MainetConstants.Req_Status.SUCCESS);

        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcVendormaster, FormMode.UPDATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String list(final Model model) throws Exception {
        chList.clear();
        log("Action 'list'");
        helpDoc("Vendormaster.html", model);
        final List<TbAcVendormaster> list = tbAcVendormasterService
                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        for (final TbAcVendormaster tbAcVendormaster : list) {
            tbAcVendormaster.setVenderCodeAndName(tbAcVendormaster.getVmVendorcode() + MainetConstants.SEPARATOR
                    + tbAcVendormaster.getVmVendorname());
        }
        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, list);
        final TbAcVendormaster tbAcVendormaster = new TbAcVendormaster();
        populateModel(model, tbAcVendormaster, FormMode.UPDATE);
        model.addAttribute(MAIN_ENTITY_NAME, tbAcVendormaster);

        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody VendormasterResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));

        final VendormasterResponse VendormasterResponse = new VendormasterResponse();
        VendormasterResponse.setRows(chList);
        VendormasterResponse.setTotal(chList.size());
        VendormasterResponse.setRecords(chList.size());
        VendormasterResponse.setPage(page);

        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, chList);

        return VendormasterResponse;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("cpdVendortype") final Long cpdVendortype,
            @RequestParam("cpdVendorSubType") final Long cpdVendorSubType,
            @RequestParam("vendor_vmvendorcode") final String vendor_vmvendorcode,
            @RequestParam("vmCpdStatus") final Long vmCpdStatus) {
        chList.clear();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final LookUp activeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
                UserSession.getCurrent().getLanguageId(), defaultOrg);
        final LookUp inActiveLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.TbAcVendormaster.IN, PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                defaultOrg);
        final LookUp blacklistLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.TbAcVendormaster.BL, PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                defaultOrg);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = tbAcVendormasterService.getVendorData(cpdVendortype, cpdVendorSubType, vendor_vmvendorcode,
                vmCpdStatus, orgid);
        Long cpdStatus = null;
        for (final TbAcVendormaster tbAcVendormaster : chList) {
            cpdStatus = tbAcVendormaster.getVmCpdStatus();
            if (cpdStatus != null) {
                if (activeLookup.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(activeLookup.getLookUpDesc());
                }
                if (inActiveLookup.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(inActiveLookup.getLookUpDesc());
                }
                if (blacklistLookUp.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(blacklistLookUp.getLookUpDesc());
                }
            }
        }
        return chList;
    }

    @RequestMapping(params = "viewMode")
    public String updateForView(@Valid final TbAcVendormaster tbAcVendormasterBeen, final BindingResult bindingResult,
            final Model model, final HttpServletRequest httpServletRequest,
            @RequestParam("MODE1") final String viewmode) throws Exception {
        log("Action 'update'");
        String viewReturned = MainetConstants.CommonConstant.BLANK;

        if (tbAcVendormasterBeen.getVmVendorid() != null) {
            if (viewmode.endsWith(VIEW)) {
                model.addAttribute(MODE, VIEW);

                boolean primaryDefaultFlag = false;
                final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
                final int langId = UserSession.getCurrent().getLanguageId();
                String sliMode = null;
                sliMode = tbAcVendormasterService.getCpdMode();
                final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                        PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId,
                        UserSession.getCurrent().getOrganisation());
                final Long lookUpStatusId = statusLookup.getLookUpId();
                if (isDafaultOrgExist) {
                    primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                            MainetConstants.MASTER.Y);
                } else {
                    primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                            UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
                }
                Organisation defaultorg = null;
                if (isDafaultOrgExist && primaryDefaultFlag) {
                    defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
                } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                    defaultorg = UserSession.getCurrent().getOrganisation();
                } else {
                    defaultorg = UserSession.getCurrent().getOrganisation();
                }

                final TbAcVendormaster tbVendormaster1 = tbAcVendormasterService.findById(
                        (long) tbAcVendormasterBeen.getVmVendorid(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                tbVendormaster1.setVmCpdStatusDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.VSS,
                        defaultorg.getOrgid(), tbVendormaster1.getVmCpdStatus()));
                if (tbVendormaster1.getCpdVendortype() != null) {
                    tbVendormaster1.setCpdVendortypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.VNT,
                            defaultorg.getOrgid(), tbVendormaster1.getCpdVendortype()));
                }
                if (tbVendormaster1.getCpdVendorSubType() != null) {
                    tbVendormaster1.setCpdVendorSubTypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.VST,
                            defaultorg.getOrgid(), tbVendormaster1.getCpdVendorSubType()));
                }
                final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultorg);
                final Long cpdVendortype = vendorLookup.getLookUpId();
                final Long cpdVendorSubType = tbVendormaster1.getCpdVendortype();
                if ((cpdVendortype != null) && (cpdVendorSubType != null)) {
                    if (sliMode != null && !sliMode.isEmpty()) {
                        if (sliMode.equals("L")) {
                            final Map<Long, String> venderPrimaryHead = tbAcPrimaryheadMasterService
                                    .getVendorTypeWisePrimaryAcHead(cpdVendortype,cpdVendorSubType, lookUpStatusId,
                                            defaultorg.getOrgid());
                            model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS, venderPrimaryHead);
           
                        }
                    }
                }
                String acHeadCode = tbAcSecondaryheadMasterService
                        .vendorIdWiseGetAccountHeadCodeValue(tbAcVendormasterBeen.getVmVendorid());
                if (acHeadCode != null && !acHeadCode.isEmpty()) {
                    tbVendormaster1.setAcHeadCode(acHeadCode);
                    model.addAttribute("acHeadCodeExistFlag", MainetConstants.Y_FLAG);
                } else {
                    model.addAttribute("acHeadCodeExistFlag", MainetConstants.N_FLAG);
                }
                /*
                 * final Long functionId = tbAcSecondaryheadMasterService
                 * .vendorIdWiseGetFunctionIdValue(tbAcVendormasterBeen.getVmVendorid()); if (functionId != null) {
                 * tbVendormaster1.setFunctionId(functionId); }
                 */
                /*
                 * log("Action 'update' : update done - redirect"); model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS,
                 * tbAcPrimaryheadMasterService .getPrimaryHeadCodeLastLevels(defaultorg.getOrgid()));
                 * model.addAttribute(MainetConstants.BANK_MASTER.FUNCTION_LIST,
                 * tbAcFunctionMasterService.getFunctionMasterLastLevels(defaultorg.getOrgid())) ;
                 */
                if(tbVendormaster1 !=null) {
                List<AttachDocs> attachDocs = iAttachDocsService.findByCode(tbVendormaster1.getOrgid(), tbVendormaster1.getVmVendorcode().toString());
                tbVendormaster1.setAttachDocsList(attachDocs);
                }
                if (sliMode != null && !sliMode.isEmpty()) {
                    if (sliMode.equals("L")) {
                        final Long functionId = tbAcSecondaryheadMasterService
                                .vendorIdWiseGetFunctionIdValue(tbVendormaster1.getVmVendorid());
                        if (functionId != null) {
                        	tbVendormaster1.setFunctionId(functionId);
                        }
                    }
                }
                populateModel(model, tbVendormaster1, FormMode.UPDATE);
                model.addAttribute(MODE, viewmode);
                boolean isDMS = false;
        		if (MainetConstants.Common_Constant.YES
        				.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
        			isDMS = true;
        		}
                model.addAttribute("isDMS", isDMS);
                viewReturned = JSP_VIEW;
            } else {
                model.addAttribute(MODE, MODE_CREATE);
            }
        } else {
            log("Action 'list'");
            final List<TbAcVendormaster> list = tbAcVendormasterService
                    .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
            model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, list);
        }
        return viewReturned;
    }

    @RequestMapping(params = "validatePANandMobile", method = RequestMethod.POST)
    public @ResponseBody List<String> validateVendor(@RequestParam("panNo") final String panNo,
            @RequestParam("mobileNo") final String mobileNo, @RequestParam("uidNo") final Long uidNo,
            @RequestParam("vat") final String vat, @RequestParam("gst") final String gst,
            @RequestParam("mode") final String mode, @RequestParam("vendorId") final Long vendorId) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        List<TbAcVendormasterEntity> vendorByPanNo = null;
        List<TbAcVendormasterEntity> vendorByMobileNo = null;
        List<TbAcVendormasterEntity> vendorByUidNo = null;
        List<TbAcVendormasterEntity> vendorByVatNo = null;
        List<TbAcVendormasterEntity> vendorByGstNo = null;

        final List<String> errorList = new ArrayList<>();
        if ((panNo != null) && (panNo != MainetConstants.CommonConstant.BLANK) && !panNo.isEmpty()) {
            vendorByPanNo = tbAcVendormasterService.getVendorByPanNo(panNo, orgId);
        }
        if ((mobileNo != null) && (mobileNo != MainetConstants.CommonConstant.BLANK) && !mobileNo.isEmpty()) {
            vendorByMobileNo = tbAcVendormasterService.getVendorByMobileNo(mobileNo, orgId);
        }
        if ((uidNo != null) && !uidNo.equals(MainetConstants.CommonConstant.BLANK)) {
            vendorByUidNo = tbAcVendormasterService.getVendorByUidNo(uidNo, orgId);
        }
        if ((vat != null) && (vat != MainetConstants.CommonConstant.BLANK) && !vat.isEmpty()) {
            vendorByVatNo = tbAcVendormasterService.getVendorByVat(vat, orgId);
        }
        if ((gst != null) && (gst != MainetConstants.CommonConstant.BLANK) && !gst.isEmpty()) {
            vendorByGstNo = tbAcVendormasterService.getVendorByGst(gst, orgId);
        }

        if (mode.equals(MainetConstants.Actions.CREATE)) {
            if ((vendorByPanNo != null) && !vendorByPanNo.isEmpty()) {
                errorList.add(appSession.getMessage("vendor.error.valid.panNo"));
            }
            if ((vendorByMobileNo != null) && !vendorByMobileNo.isEmpty()) {
                errorList.add(appSession.getMessage("vendor.error.valid.mobileNo"));
            }
            if ((vendorByUidNo != null) && !vendorByUidNo.isEmpty()) {
                errorList.add(appSession.getMessage("vendor.error.valid.uidNo"));
            }
            if ((vendorByVatNo != null) && !vendorByVatNo.isEmpty()) {
                errorList.add(appSession.getMessage("vendor.error.valid.vatNo"));
            }
            if ((vendorByGstNo != null) && !vendorByGstNo.isEmpty()) {
                errorList.add(appSession.getMessage("vendor.error.valid.gstNo"));
            }

        } else {
            // PanNo Validation
            if ((vendorByPanNo != null) && !vendorByPanNo.isEmpty()) {
                boolean isPanNoExist = vendorByPanNo.stream().anyMatch(obj -> !vendorId.equals(obj.getVmVendorid()));
                if (isPanNoExist)
                    errorList.add(appSession.getMessage("vendor.error.valid.panNo"));
            }

            // mobileNo Validation
            if ((vendorByMobileNo != null) && !vendorByMobileNo.isEmpty()) {
                boolean isduplicateMobile = vendorByMobileNo.stream().anyMatch(obj -> !vendorId.equals(obj.getVmVendorid()));
                if (isduplicateMobile)
                    errorList.add(appSession.getMessage("vendor.error.valid.mobileNo"));
            }

            // uidNo Validation
            if ((vendorByUidNo != null) && !vendorByUidNo.isEmpty()) {
                boolean isduplicateUid = vendorByUidNo.stream().anyMatch(obj -> !vendorId.equals(obj.getVmVendorid()));
                if (isduplicateUid)
                    errorList.add(appSession.getMessage("vendor.error.valid.uidNo"));
            }

            // vat Validation
            if ((vendorByVatNo != null) && !vendorByVatNo.isEmpty()) {
                boolean isduplicateVat = vendorByVatNo.stream().anyMatch(obj -> !vendorId.equals(obj.getVmVendorid()));
                if (isduplicateVat)
                    errorList.add(appSession.getMessage("vendor.error.valid.vatNo"));
            }

            // gst Validation
            if ((vendorByGstNo != null) && !vendorByGstNo.isEmpty()) {
                boolean isduplicateGst = vendorByGstNo.stream().anyMatch(obj -> !vendorId.equals(obj.getVmVendorid()));
                if (isduplicateGst)
                    errorList.add(appSession.getMessage("vendor.error.valid.gstNo"));
            }
        }
        return errorList;
    }

    @RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
    public String exportImportExcelTemplate(final Model model) throws Exception {
        log("TbAcVendormaster-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final TbAcVendormaster bean = new TbAcVendormaster();
        populateModel(model, bean, FormMode.CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        result = JSP_EXCELUPLOAD;
        return result;
    }

    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountBudgetOpenBalanceMasterExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<VendorMasterUploadDto> data = new WriteExcelData<>(
                    MainetConstants.VENDORMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request, response);

            data.getExpotedExcelSheet(new ArrayList<VendorMasterUploadDto>(), VendorMasterUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(TbAcVendormaster bean, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {

        log("Action 'loadExcelData'");
        
        final ApplicationSession session = ApplicationSession.getInstance();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = session.getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final UserSession userSession = UserSession.getCurrent();
        final Long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final String filePath = getUploadedFinePath();
        ReadExcelData<VendorMasterUploadDto> data = new ReadExcelData<>(filePath, VendorMasterUploadDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
       if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.TbAcVendormaster.TB_VENDORMASTER, MainetConstants.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.empty.excel")));
        } else {
            final List<VendorMasterUploadDto> vendorMasterUploadDtos = data.getParseData();

            // populateModel(model, bean, FormMode.CREATE);
            List<LookUp> venderType = CommonMasterUtility.getLookUps(PrefixConstants.VNT, defaultOrg);
            List<LookUp> vendorClassName = CommonMasterUtility.getLookUps(PrefixConstants.TbAcVendormaster.VEC,
                    userSession.getOrganisation());
            final List<LookUp> vendorSubType = CommonMasterUtility.getLookUps(PrefixConstants.VST, defaultOrg);
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId,
                    UserSession.getCurrent().getOrganisation());
            final Long lookUpStatusId = statusLookup.getLookUpId();
            final Long orgid = defaultOrg.getOrgid();
            final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultOrg);
            final Long cpdVendortype = vendorLookup.getLookUpId();
            // final Long cpdVendorSubType = bean.getCpdVendortype();

            List<AccountHeadPrimaryAccountCodeMasterBean> venderPrimaryHead = tbAcPrimaryheadMasterService
                    .getPrimaryAcHeadCompositeCodeAllList(lookUpStatusId, orgid);
            final Map<Long, String> functionMasterStatusLastLevels = tbAcFunctionMasterService
                    .getFunctionCompositeCode(defaultOrg, langId);

            final Map<Long, String> bankMap = new HashMap<>();
            final List<Object[]> blist = banksMasterService.findActiveBankList();
            for (final Object[] obj : blist) {
                bankMap.put((Long) obj[0], obj[3].toString());
            }
            VendorMasterValidator validator = new VendorMasterValidator();
            List<VendorMasterUploadDto> vendorMasterUploadDtoList = validator.excelValidation(
                    vendorMasterUploadDtos, bindingResult, venderType, vendorSubType, functionMasterStatusLastLevels,
                    venderPrimaryHead, bankMap, lookUpStatusId, cpdVendortype, vendorClassName);

            final LookUp secndVendorType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.TbAcVendormaster.VD,
                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getLanguageId(), defaultOrg);

            String sliMode = null;
            sliMode = tbAcVendormasterService.getCpdMode();

            if (!bindingResult.hasErrors()) {
                validateExcelData(vendorMasterUploadDtos, bindingResult, orgId);
                if (!bindingResult.hasErrors()) {
                    for (VendorMasterUploadDto vendorMasterUploadDto : vendorMasterUploadDtoList) {
                        if (sliMode != null && !sliMode.isEmpty()) {
                            vendorMasterUploadDto.setSliMode(sliMode);
                        }
                        vendorMasterUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        vendorMasterUploadDto.setLangId(Long.valueOf(langId));
                        vendorMasterUploadDto.setUserId(userId);
                        vendorMasterUploadDto.setLmoddate(new Date());
                        vendorMasterUploadDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                        tbAcVendormasterService.saveVendorMasterExcelData(
                                vendorMasterUploadDto, defaultOrg, langId, venderType, secndVendorType.getLookUpId());
                    }

                    model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                            session.getMessage("accounts.success.excel"));
                }
            }
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
            populateModel(model, bean, FormMode.CREATE);
            model.addAttribute(MAIN_ENTITY_NAME, bean);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        }
        return JSP_EXCELUPLOAD;
    }

    private void validateExcelData(final List<VendorMasterUploadDto> vendorMasterUploadDtos, BindingResult bindingResult,
            final Long orgId) {

        final ApplicationSession session = ApplicationSession.getInstance();
        List<TbAcVendormaster> venMasterDtos = tbAcVendormasterService.getVendorMasterData(orgId);
        int rowNo = 0;

        for (VendorMasterUploadDto vendorMasterUploadDto : vendorMasterUploadDtos) {
            rowNo++;

            for (TbAcVendormaster venMasterDto : venMasterDtos) {
                if (StringUtils.isNotBlank(venMasterDto.getVmPanNumber())
                        && StringUtils.isNotBlank(vendorMasterUploadDto.getPanNum())) {
                    if (venMasterDto.getVmPanNumber().equalsIgnoreCase(vendorMasterUploadDto.getPanNum())) {
                        bindingResult
                                .addError(new ObjectError("panNo", rowNo + session.getMessage("vendormaster.excel.duppanNo")));
                    }
                }
                if (vendorMasterUploadDto.getMobileNum() != null &&
                		StringUtils.isNotBlank(venMasterDto.getMobileNo())
                        && StringUtils.isNotBlank(vendorMasterUploadDto.getMobileNum().toString())) {
                    if (venMasterDto.getMobileNo().trim().equalsIgnoreCase(vendorMasterUploadDto.getMobileNum().toString())) {
                        bindingResult.addError(new ObjectError("mobileNo",
                                rowNo + session.getMessage("vendormaster.excel.dupmobile")));
                    }
                }
                if (venMasterDto.getVmUidNo() != null && vendorMasterUploadDto.getUidNum() != null &&
                		StringUtils.isNotBlank(venMasterDto.getVmUidNo().toString())
                        && StringUtils.isNoneBlank(vendorMasterUploadDto.getUidNum().toString())) {
                    if (venMasterDto.getVmUidNo().equals(vendorMasterUploadDto.getUidNum())) {
                        bindingResult.addError(new ObjectError("uidNo",
                        		rowNo + session.getMessage("vendormaster.excel.dupuid")));
                    }
                }
                if (StringUtils.isNotBlank(venMasterDto.getTinNumber())
                        && StringUtils.isNotBlank(vendorMasterUploadDto.getVatNum())) {
                    if (venMasterDto.getTinNumber() == vendorMasterUploadDto.getVatNum().trim()) {
                        bindingResult.addError(new ObjectError("vatNo",
                                rowNo + session.getMessage("vendor.error.valid.vatNo")));
                    }
                }
                if (StringUtils.isNotBlank(venMasterDto.getVmGstNo())
                        && StringUtils.isNotBlank(vendorMasterUploadDto.getGstNum())) {
                    if (venMasterDto.getVmGstNo().equalsIgnoreCase(vendorMasterUploadDto.getGstNum())) {
                        bindingResult.addError(new ObjectError("gstNo",
                                rowNo + session.getMessage("vendormaster.excel.dupgstin")));
                    }
                }
            }
        }
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

    @RequestMapping(method = RequestMethod.POST, params = "getReportData")
    public String getVendorReportData(final HttpServletRequest request, final Model model) {
        chList.clear();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final LookUp activeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
                UserSession.getCurrent().getLanguageId(), defaultOrg);
        final LookUp inActiveLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.TbAcVendormaster.IN, PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                defaultOrg);
        final LookUp blacklistLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.TbAcVendormaster.BL, PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                defaultOrg);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = tbAcVendormasterService.findAll(orgid);
        Long cpdStatus = null;
        for (final TbAcVendormaster tbAcVendormaster : chList) {
            cpdStatus = tbAcVendormaster.getVmCpdStatus();
            if (cpdStatus != null) {
                if (activeLookup.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(activeLookup.getLookUpDesc());
                }
                if (inActiveLookup.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(inActiveLookup.getLookUpDesc());
                }
                if (blacklistLookUp.getLookUpId() == cpdStatus) {
                    tbAcVendormaster.setVmCpdStatusDesc(blacklistLookUp.getLookUpDesc());
                }
            }
        }
        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, chList);
        return JSP_REPORT;
    }
    
    @RequestMapping(params = "DownloadFile", method = RequestMethod.POST)
    public String download(@RequestParam("docId") final String docId, @RequestParam("docName") final String docName,
            final HttpServletResponse httpServletResponse,final Model model) throws Exception {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS"
                + MainetConstants.FILE_PATH_SEPARATOR;
        final byte[] byteArray = iDmsService.getDocumentById(docId);
        if (byteArray == null) {
            return "404error.jsp";
        }
        Utility.createDirectory(Filepaths.getfilepath() + outputPath);
        outputPath = outputPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);
        outputPath = outputPath + docName;
        final Path path = Paths.get(Filepaths.getfilepath() + outputPath);
        java.nio.file.Files.write(path, byteArray);
        model.addAttribute("filePath", outputPath);
        return "viewHelpMaster";
    }


}
