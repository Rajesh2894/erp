package com.abm.mainet.common.master.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.TaxDependsOnFactor;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxAcMappingBean;
import com.abm.mainet.common.master.dto.TbTaxDetMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxAcMappingService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.master.ui.model.TaxMasterResponse;
import com.abm.mainet.common.master.ui.validator.TaxMasterValidator;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * Spring MVC controller for 'TbTaxMas' management.
 */
@Controller
@RequestMapping("/TaxMaster.html")
public class TbTaxMasController extends AbstractController {

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "tbTaxMas/form";
    private static final String JSP_LIST = "tbTaxMas/list";

    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "tbTaxMas";
    private static final String MAIN_LIST_NAME = "list";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "TaxMaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "TaxMaster.html?update";

    private List<String> taxDetIdList;
    private List<TbTaxMas> taxMasGridList;
    private List<TbTaxMas> taxMasList;

    // --- Other service(s)
    @Resource
    private TbDepartmentService tbDepartmentService;
    // --- Main entity service
    @Resource
    private TbTaxMasService tbTaxMasService;

    @Resource
    private TbServicesMstService serviceMasService;

    @Resource
    private AccountFundMasterService fundService;

    @Resource
    private AccountFunctionMasterService functionService;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService primaryHeadService;

    @Resource
    private SecondaryheadMasterService secondaryHeadrService;

    @Resource
    private TbComparamDetService tbComparamDetService;

    @Resource
    private TbTaxAcMappingService tbTaxBudgetcodeService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    // --------------------------------------------------------------------------------------
    /**
     * Default constructor
     */
    public TbTaxMasController() {
        super(TbTaxMasController.class, MAIN_ENTITY_NAME);
    }

    // --------------------------------------------------------------------------------------
    // Request mapping
    // --------------------------------------------------------------------------------------
    /**
     * Shows a list with all the occurrences of TbTaxMas found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model, final HttpServletRequest httpServletRequest) {
        final TbTaxMas tbTaxMas = new TbTaxMas();
        Organisation org = new Organisation();
        helpDoc("TaxMaster.html", model);
        org = UserSession.getCurrent().getOrganisation();
        taxMasGridList = new ArrayList<>();
        final List<TbDepartment> deptList = tbDepartmentService.findMappedDepartments(org.getOrgid());
        final List<LookUp> txnPrefixData = CommonMasterUtility.getListLookup(MainetConstants.PG_REQUEST_PROPERTY.TXN, org);
        model.addAttribute(MainetConstants.CommonMasterUi.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.CommonMasterUi.TXN_PREFIX_DATA, txnPrefixData);
        populateModel(model, tbTaxMas, FormMode.CREATE);
        return JSP_LIST;
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbTaxMas
     */
    private void populateModel(final Model model, final TbTaxMas tbTaxMas, final FormMode formMode) {
        // --- Main entity
        model.addAttribute(MAIN_ENTITY_NAME, tbTaxMas);
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, tbTaxMas);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            // --- Other data useful in this screen in "create" mode (all fields)
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            // --- Other data useful in this screen in "update" mode (only non-pk fields)
        } else if (formMode == FormMode.VIEW) {
            model.addAttribute(MODE, MODE_VIEW);
        }
    }

    // --------------------------------------------------------------------------------------
    // Spring MVC model management
    // --------------------------------------------------------------------------------------

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbTaxMas entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST) // GET or POST
    public ModelAndView create(@Valid final TbTaxMas tbTaxMas, final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest) {
        try {

            final TaxMasterValidator validator = new TaxMasterValidator();
            validator.validate(tbTaxMas, bindingResult);
            if (!bindingResult.hasErrors()) {
                TbTaxMas tbTaxMasCreated = null;
                tbTaxMasCreated = tbTaxMasService.create(tbTaxMas);
                model.addAttribute(MAIN_ENTITY_NAME, tbTaxMasCreated);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.COMMON_STATUS.SUCCESS);
            } else {
                populateModel(model, tbTaxMas, FormMode.CREATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {
            messageHelper.addException(model, "tbTaxMas.error.create", e);
            populateModel(model, tbTaxMas, FormMode.CREATE);
            return new ModelAndView(JSP_FORM);
        }

    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbTaxMas entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update", method = RequestMethod.POST) // GET or POST
    public ModelAndView update(@Valid final TbTaxMas tbTaxMas, final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest) {
        try {
            if (!bindingResult.hasErrors()) {

                int counter = 0;
                final List<String> newList = new ArrayList<>();
                final List<String> deletedList = new ArrayList<>();
                final List<String> dataList = new ArrayList<>();

                for (final String curDependsId : tbTaxMas.getTaxDetIdList()) {
                    for (final String dependsId : taxDetIdList) {
                        if (!curDependsId.equals(dependsId)) {
                            counter++;
                        } else {
                            counter = 0;
                            break;
                        }
                    }
                    // this code should be removed since we will not get empty list because it is mandatory to add depends on
                    // factors for a tax
                    if (taxDetIdList.isEmpty()) {
                        newList.add(curDependsId);
                    }
                    if (counter > 0) {
                        newList.add(curDependsId);
                    }
                }
                counter = 0;
                for (final String curDependsId : taxDetIdList) {
                    for (final String dependsId : tbTaxMas.getTaxDetIdList()) {
                        if (!curDependsId.equals(dependsId)) {
                            counter++;
                        } else {
                            counter = 0;
                            dataList.add(curDependsId);
                            break;
                        }
                    }
                    if (counter > 0) {
                        deletedList.add(curDependsId);
                    }
                }
                final TbTaxMas tbTaxMasSaved = tbTaxMasService.update(tbTaxMas, newList, deletedList, dataList);
                model.addAttribute(MAIN_ENTITY_NAME, tbTaxMasSaved);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                        MainetConstants.COMMON_STATUS.SUCCESS);
            } else {
                populateModel(model, tbTaxMas, FormMode.UPDATE);
                return new ModelAndView(JSP_FORM);
            }
        } catch (final Exception e) {
            messageHelper.addException(model, "tbTaxMas.error.update", e);
            populateModel(model, tbTaxMas, FormMode.UPDATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param redirectAttributes
     * @param taxId primary key element
     * @return
     */
    @RequestMapping(params = "delete") // GET or POST
    public String delete(@RequestParam("taxId") final Long taxId) {
        try {
            tbTaxMasService.delete(taxId);
            // --- Set the result message
        } catch (final Exception e) {
        }
        return redirectToList();
    }

    /**
     * Shows a form page in order to create a new TbTaxMas
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) throws Exception {
        final TbTaxMas tbTaxMas = new TbTaxMas();
        final Organisation org = UserSession.getCurrent().getOrganisation();

        final List<LookUp> fsdPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.FSD, org);
        final List<LookUp> vtyPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.VTY, org);
        final List<LookUp> ponPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.PON, org);
        final List<LookUp> tagPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.TAG, org);
        final List<LookUp> txnPrefixData = CommonMasterUtility.getListLookup(MainetConstants.PG_REQUEST_PROPERTY.TXN, org);
        final List<LookUp> taxApplicable = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.CHARGE_MASTER_CAA, org);
        final List<LookUp> acnPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.ACN, org);
        final List<LookUp> dmcPrefixList = CommonMasterUtility.getListLookup("DMC", org);

        final List<TbDepartment> deptList = tbDepartmentService.findMappedDepartments(org.getOrgid());

        final List<AccountHeadSecondaryAccountCodeMasterEntity> entity = getBudgetCode();
        if (entity != null) {
            model.addAttribute(MainetConstants.CommonMasterUi.BUDGET_LIST, entity);
        }
        model.addAttribute(MainetConstants.CommonMasterUi.FSD_PREFIX_DATA, fsdPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.VTY_PREFIX_DATA, vtyPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.PON_PREFIX_DATA, ponPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.TAG_PREFIX_DATA, tagPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.TAX_APPLICABLE, taxApplicable);
        model.addAttribute(MainetConstants.CommonMasterUi.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.CommonMasterUi.ACN_PREFIX_LIST, acnPrefixList);
        model.addAttribute(MainetConstants.CommonMasterUi.TXN_PREFIX_DATA, txnPrefixData);
        model.addAttribute("dmcPrefixList", dmcPrefixList);
        final int langId = UserSession.getCurrent().getLanguageId();
		final LookUp liveMode = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LIVE_MODE,
				 PrefixConstants.SLI, langId, org);
        model.addAttribute("isAccountItegrationMadi", liveMode.getDefaultVal());
        populateModel(model, tbTaxMas, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbTaxMas
     * @param model Spring MVC model
     * @param taxId primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
    public String formForUpdate(final Model model, @RequestParam("taxId") final Long taxId,
            @RequestParam("mode") final String mode)
            throws Exception {
        // --- Search the entity by its primary key and stores it in the model
        final TbTaxMas tbTaxMas = tbTaxMasService.findById(taxId, UserSession.getCurrent().getOrganisation().getOrgid());

        if (CommonMasterUtility.getNonHierarchicalLookUpObject(tbTaxMas.getTaxApplicable()).getLookUpCode().equals("BILL")
                && tbTaxMas.getTaxDisplaySeq() == null) {
            int nextDispSeq = tbTaxMasService.getNextDisplaySequence(tbTaxMas.getOrgid(), tbTaxMas.getDpDeptId(),
                    tbTaxMas.getTaxApplicable());
            if (nextDispSeq == 1) {
                tbTaxMas.setTaxDisplaySeq(null);
            } else {
                tbTaxMas.setTaxDisplaySeq(Long.valueOf(nextDispSeq));
            }

        }
        if (CommonMasterUtility.getNonHierarchicalLookUpObject(tbTaxMas.getTaxApplicable()).getLookUpCode().equals("BILL")
                && tbTaxMas.getCollSeq() == null) {
            int nextCollSeq = tbTaxMasService.getNextCollectionSequence(tbTaxMas.getOrgid(), tbTaxMas.getDpDeptId(),
                    tbTaxMas.getTaxApplicable());
            if (nextCollSeq == 1) {
                tbTaxMas.setCollSeq(null);
            } else {
                tbTaxMas.setCollSeq(Long.valueOf(nextCollSeq));
            }

        }

        taxDetIdList = tbTaxMas.getTaxDetIdList();

        final Organisation org = UserSession.getCurrent().getOrganisation();
        List<LookUp> factForDept = new ArrayList<>();
        List<LookUp> eventMapNotSelectedList = new ArrayList<>();
        final List<LookUp> dependOnFactList = new ArrayList<>();

        final List<LookUp> fsdPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.FSD, org);
        final List<LookUp> vtyPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.VTY, org);
        final List<LookUp> ponPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.PON, org);
        final List<LookUp> tagPrefixData = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.TAG, org);
        final List<LookUp> txnPrefixData = CommonMasterUtility.getListLookup(MainetConstants.PG_REQUEST_PROPERTY.TXN, org);

        final List<LookUp> taxApplicable = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.CHARGE_MASTER_CAA, org);
        final List<LookUp> acnPrefixList = CommonMasterUtility.getListLookup(PrefixConstants.LookUp.ACN, org);
        final List<LookUp> dmcPrefixList = CommonMasterUtility.getListLookup("DMC", org);
        final List<TbDepartment> deptList = tbDepartmentService.findMappedDepartments(org.getOrgid());

        model.addAttribute(MainetConstants.CommonMasterUi.FSD_PREFIX_DATA, fsdPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.VTY_PREFIX_DATA, vtyPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.PON_PREFIX_DATA, ponPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.TAG_PREFIX_DATA, tagPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.TXN_PREFIX_DATA, txnPrefixData);
        model.addAttribute(MainetConstants.CommonMasterUi.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.CommonMasterUi.TAX_APPLICABLE, taxApplicable);
        model.addAttribute(MainetConstants.CommonMasterUi.ACN_PREFIX_LIST, acnPrefixList);
        model.addAttribute(MainetConstants.CommonMasterUi.DEPT, tbTaxMas.getDpDeptId());
        model.addAttribute("dmcPrefixList", dmcPrefixList);

        if (tbTaxMas.getSmServiceId() != null) {
            model.addAttribute(MainetConstants.CommonMasterUi.SERVICE_LIST, getServiceList(tbTaxMas.getDpDeptId()));
        }
        if (tbTaxMas.getDpDeptId() != null) {
            final List<TbTaxMas> taxCodeList = taxCodeList(tbTaxMas.getDpDeptId());
            model.addAttribute(MainetConstants.CommonMasterUi.TAX_CODE_LIST, taxCodeList);
        }

        final List<AccountHeadSecondaryAccountCodeMasterEntity> entity = getBudgetCode();
        if (entity != null) {
            model.addAttribute(MainetConstants.CommonMasterUi.BUDGET_LIST, entity);
        }

        if (tbTaxMas.getTaxDetMasList() != null) {
            for (final TbTaxDetMas tbTaxDetMas : tbTaxMas.getTaxDetMasList()) {
                if (tbTaxDetMas.getTdDependFact() != null) {
                    final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(tbTaxDetMas.getTdDependFact(), org);
                    dependOnFactList.add(lookup);
                }
            }
        }
        model.addAttribute(MainetConstants.CommonMasterUi.DEPENDS_ON_FACTORS, dependOnFactList);

        if (tbTaxMas.getDeptCode() == null) {
            final TbDepartment department = tbDepartmentService.findById(tbTaxMas.getDpDeptId());
            tbTaxMas.setDeptCode(department.getDpDeptcode());
        }

        factForDept = getDependsOnFactor(tbTaxMas.getDeptCode());
        eventMapNotSelectedList = new ArrayList<>();
        eventMapNotSelectedList.addAll(factForDept);

        eventMapNotSelectedList.removeAll(dependOnFactList);
        model.addAttribute(MainetConstants.CommonMasterUi.EVENT_MAPNOT_SELECTEDlIST, eventMapNotSelectedList);
        model.addAttribute(MainetConstants.CommonMasterUi.FACT_FOR_DEPT, factForDept);

        final List<TbTaxAcMappingBean> taxBudgetcodeList = tbTaxBudgetcodeService.getByTaxIdOrgId(org.getOrgid(), taxId);
        tbTaxMas.setTaxBudgetBean(taxBudgetcodeList);
        model.addAttribute("sacHeadCount", taxBudgetcodeList.size());

        if (mode.equals(MainetConstants.CommonMasterUi.VIEW)) {
            populateModel(model, tbTaxMas, FormMode.VIEW);
        } else {
            populateModel(model, tbTaxMas, FormMode.UPDATE);
        }
        model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, mode);
        return JSP_FORM;

    }

    @RequestMapping(params = "getSubAlphanumericSortInfo", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final List<LookUp> getSubAlphanumericSortInfo(
            @RequestParam final String typeCode, @RequestParam final long typeId,
            @RequestParam final String isAlphaNumeric) {
        final List<LookUp> lookup = ApplicationSession.getInstance()
                .getChildLookUpsFromParentId(typeId);
        if ((lookup != null) && !lookup.isEmpty()) {
            if ((isAlphaNumeric != null) && isAlphaNumeric.equals(MainetConstants.Common_Constant.YES)) {
                Collections.sort(lookup, LookUp.alphanumericComparator);
            } else {
                Collections.sort(lookup);
            }

        }
        return lookup;
    }

    @RequestMapping(params = "getSubInfoByLevel", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody final List<LookUp> getSubLookUpListByLevel(
            @RequestParam final String typeCode, @RequestParam final long typeId,
            @RequestParam final long level) {
        return ApplicationSession.getInstance()
                .getChildLookUpsFromParentIdForLevel(typeId, level);
    }

    @RequestMapping(params = "getMasGridData")
    public @ResponseBody TaxMasterResponse gridData(final HttpServletRequest request, final Model model) {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final TaxMasterResponse response = new TaxMasterResponse();
        final int dataSize = taxMasGridList.size();
        response.setRows(taxMasGridList);
        response.setTotal(dataSize);
        response.setRecords(dataSize);
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, taxMasList);
        return response;
    }

    @RequestMapping(params = "searchTaxMasData")
    public @ResponseBody void searchTaxMasData(final Model model, @RequestParam("taxDescId") final Long taxDescId,
            @RequestParam("dpDeptId") final Long dpDeptId) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        taxMasGridList = tbTaxMasService.findAllByDescId(taxDescId, orgId, dpDeptId);
    }

    @RequestMapping(params = "validateSequence")
    public @ResponseBody int validateSequence(@RequestParam("seqNum") final Long seqNum,
            @RequestParam("dpDeptId") final Long dpDeptId,
            @RequestParam("dispSeq") final String dispSeq, @RequestParam("taxApplicableAt") final Long taxApplicableAt) {
        int counter = 0;
        try {
            counter = tbTaxMasService.validateSequence(seqNum, dpDeptId, UserSession.getCurrent().getOrganisation().getOrgid(),
                    dispSeq, taxApplicableAt);
        } catch (final Exception ex) {
            throw new FrameworkException("exception in validateSequence : " + ex);
        }
        return counter;
    }

    @RequestMapping(params = "getNextSequences")
    public @ResponseBody Object[] getNextSequences(@RequestParam("dpDeptId") final Long dpDeptId,
            @RequestParam("taxApplicableAt") final Long taxApplicableAt) {
        Object[] seqObj = new Object[] { null, null };
        seqObj[0] = tbTaxMasService.getNextDisplaySequence(UserSession.getCurrent().getOrganisation().getOrgid(), dpDeptId,
                taxApplicableAt);
        seqObj[1] = tbTaxMasService.getNextCollectionSequence(UserSession.getCurrent().getOrganisation().getOrgid(),
                dpDeptId, taxApplicableAt);
        return seqObj;
    }

    @RequestMapping(params = "serviceList", method = RequestMethod.POST)
    public @ResponseBody List<TbServicesMst> getServiceList(@RequestParam("dpDeptId") final Long dpDeptId, final Model model) {
        final List<TbServicesMst> serviceList = getServiceList(dpDeptId);
        return serviceList;
    }

    @RequestMapping(params = "taxCodeList", method = RequestMethod.POST)
    public @ResponseBody List<TbTaxMas> getTaxCodeListList(@RequestParam("dpDeptId") final Long dpDeptId, final Model model) {
        final TbTaxMas tbTaxMas = new TbTaxMas();
        final List<TbTaxMas> taxCodeList = taxCodeList(dpDeptId);
        model.addAttribute(MainetConstants.CommonMasterUi.TAX_CODE_LIST, taxCodeList);
        populateModel(model, tbTaxMas, FormMode.CREATE);
        return taxCodeList;
    }

    @RequestMapping(params = "secondaryHeader", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> secondaryHeadrService(@RequestParam("primaryHead") final Long primaryHead,
            final Model model) {
        final TbTaxMas tbTaxMas = new TbTaxMas();
        final Map<Long, String> secondaryHead = secondaryHeadrService.findAllById(primaryHead);
        model.addAttribute(MainetConstants.CommonMasterUi.SECONDARY_HEAD, secondaryHead);
        populateModel(model, tbTaxMas, FormMode.CREATE);
        return secondaryHead;
    }

    private List<TbServicesMst> getServiceList(final Long dpDeptId) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbServicesMst> serviceList = serviceMasService.findALlActiveServiceByDeptId(dpDeptId, orgId);
        return serviceList;
    }

    private List<TbTaxMas> taxCodeList(final Long dpDeptId) {
        final List<TbTaxMas> taxCodeList = tbTaxMasService.findAllByTaxOrgId(null,
                UserSession.getCurrent().getOrganisation().getOrgid(), dpDeptId);
        return taxCodeList;
    }

    @RequestMapping(params = "taxList", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getTaxList(@RequestParam("deptId") final Long dpDeptId, final Model model) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final List<Long> taxDescIdList = tbTaxMasService.findTaxByDeptIdAndOrgId(org.getOrgid(), dpDeptId);
        final List<LookUp> lookUpList = new ArrayList<>();
        if (taxDescIdList.size() != 0) {
            for (final Long descId : taxDescIdList) {
                if (descId != null) {
                    final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(descId, org);
                    lookUpList.add(lookup);
                }
            }
        }
        return lookUpList;
    }

    @RequestMapping(params = "getBudgetCodeList")
    public @ResponseBody List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetCode() {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final String cpdMode = tbTaxMasService.getCpdMode();
        if (cpdMode.equals(MainetConstants.FlagL)) {
            final List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadCodes = secondaryheadMasterService
                    .getSecondaryHeadcodesForTax(org.getOrgid());
            return secondaryHeadCodes;
        } else {
            return null;
        }

    }

    @RequestMapping(params = "validateTax", method = RequestMethod.POST)
    public @ResponseBody boolean validateTax(final Long taxDescId, final Long deptId, final Long taxAppl, final String taxMethod,
            final String taxGroup, final Long taxCategory, final Long taxSubCategory, final String taxCode,
            final Long serviceId) {
        boolean result = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        result = tbTaxMasService.validateTax(orgId, taxDescId, deptId, taxAppl, taxMethod, taxGroup, taxCategory, taxSubCategory,
                taxCode, serviceId);
        return result;
    }

    @RequestMapping(params = "getDependsOnFactor", method = RequestMethod.GET)
    public @ResponseBody List<LookUp> getDependsOnFactor(@RequestParam("deptCode") final String deptCode) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        List<LookUp> lookupList = null;
        final int langId = UserSession.getCurrent().getLanguageId();

        if (deptCode.equals(TaxDependsOnFactor.TP.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.TP.getPrefix(), org);
        } else if (deptCode.equals(TaxDependsOnFactor.WT.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.WT.getPrefix(), org);
        } else if (deptCode.equals(TaxDependsOnFactor.AS.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.AS.getPrefix(), org);
        } else if (deptCode.equals(TaxDependsOnFactor.RL.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.RL.getPrefix(), org);

        } else if (deptCode.equals(TaxDependsOnFactor.ADH.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.ADH.getPrefix(), org);
        }else if (deptCode.equals(TaxDependsOnFactor.ENC.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.ENC.getPrefix(), org);
        }else if (deptCode.equals(TaxDependsOnFactor.RTS.name())) {
            lookupList = CommonMasterUtility.getListLookup(TaxDependsOnFactor.RTS.getPrefix(), org);
        } else {
            final Organisation orgDefault = tbOrganisationService.findDefaultOrganisation();
            final LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.Common_Constant.NO,
                    TaxDependsOnFactor.OTHER.getPrefix(),
                    langId,
                    orgDefault);
            lookupList = new ArrayList<>();
            lookupList.add(lookup);
        }
        return lookupList;

    }

    @RequestMapping(params = "deleteBudgetcode", method = RequestMethod.GET)
    public void deleteBudgetCode(@RequestParam("taxbId") final Long taxbId) {
        tbTaxBudgetcodeService.delete(taxbId);
    }

    @RequestMapping(params = "validateCpdMode", method = RequestMethod.POST)
    public @ResponseBody String validateCpdMode() {
        String isValid = null;
        final String cpdMode = tbTaxMasService.getCpdMode();
        if (cpdMode != null) {
            isValid = cpdMode;
        }
        return isValid;
    }

    @RequestMapping(params = "validateTaxCategory", method = RequestMethod.POST)
    public @ResponseBody String validateTaxCategory() {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        String isValid = null;
        final List<LookUp> lookUp = tbTaxMasService.validateTaxCategory(PrefixConstants.TAC_PREFIX, org);
        if (lookUp != null) {
            isValid = MainetConstants.Y_FLAG;
        }
        return isValid;
    }

}
