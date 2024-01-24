package com.abm.mainet.common.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.TbRejectionMst;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.service.TbRejectionMstService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbRejectionMst' management.
 */
@Controller
@RequestMapping(value = { "/RejectionMst.html", "/Turtipatra.html" })
public class TbRejectionMstController extends AbstractController {

    private static final String TURTIPATRA_HTML = "Turtipatra.html";
    private static final String TURTIDOCUMENT = "Turtidocument";
    private static final String TURTIPATRA = "Turtipatra";
    private static final String TURTIPATRAN_NO = "TurtipatranNO";
    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "tbRejectionMst";
    private static final String MAIN_LIST_NAME = "list";

    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "tbRejectionMst/list";
    private static final String JSP_LIST = "tbRejectionMst/form";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "/tbRejectionMst/create";
    private static final String SAVE_ACTION_UPDATE = "/tbRejectionMst/update";

    // --- Main entity service
    @Resource
    private TbRejectionMstService tbRejectionMstService; // Injected by Spring
    // --- Other service(s)
    @Autowired
    ICFCApplicationMasterService cfcService;

    @Autowired
    TbApprejMasService tbApprejMasServiceImpl;
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private TbApprejMasService tbApprejMasService;

    @Resource
    private ServiceMasterService serviceMasterService; 
    

    List<CFCAttachment> documentList = new ArrayList<>();

    public TbRejectionMstController() {
        super(TbRejectionMstController.class, MAIN_ENTITY_NAME);
        log("TbRejectionMstController created.");
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbRejectionMst
     */
    private void populateModel(final Model model, final TbRejectionMst tbRejectionMst, final FormMode formMode) {
        // --- Main entity
        model.addAttribute(MAIN_ENTITY_NAME, tbRejectionMst);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            // --- Other data useful in this screen in "create" mode (all fields)
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            // --- Other data useful in this screen in "update" mode (only non-pk fields)
        }
    }

    /**
     * Shows a list with all the occurrences of TbRejectionMst found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model, final HttpServletRequest httpServletRequest) {
        final TbRejectionMst tbRejectionMst = new TbRejectionMst();
        List<TbApprejMas> list = new ArrayList<>();
        log("Action 'list'");
        final long applicationId = Long
                .parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.APPL_ID).toString());
        final long labelId = Long.parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.LABEL_ID).toString());
        final long serviceId = Long
                .parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID).toString());
        final long level = Long
                .parseLong(httpServletRequest.getSession().getAttribute(MainetConstants.CommonConstants.LEVEL).toString());
        final String labelVal = httpServletRequest.getSession().getAttribute(MainetConstants.LABEL_VAL).toString();
        tbRejectionMst.setRejApplicationId(applicationId);
        tbRejectionMst.setRejServiceId(serviceId);
        tbRejectionMst.setLabelId(labelId);
        tbRejectionMst.setLevel(level);
        tbRejectionMst.setLableValue(labelVal);
        final String ServiceName = serviceMasterService.getServiceNameByServiceId(serviceId);
        tbRejectionMst.setRejServicename(ServiceName);
        final String url = (String) httpServletRequest.getRequestURL().subSequence(36, 51);

        if (url.equalsIgnoreCase(TURTIPATRA_HTML)) {
            model.addAttribute(TURTIDOCUMENT, TURTIDOCUMENT);
            final long trutiId = CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.TRU, PrefixConstants.WATERMODULEPREFIX.REM,
                            UserSession.getCurrent().getOrganisation())
                    .getLookUpId();
            list = tbApprejMasService.findByRemarkType(serviceId, trutiId);
            tbRejectionMst.setRejType(trutiId);
        } else {
            final long rejId = CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.REJ, PrefixConstants.WATERMODULEPREFIX.REM,
                            UserSession.getCurrent().getOrganisation())
                    .getLookUpId();
            list = tbApprejMasService.findByRemarkType(serviceId, rejId);
            tbRejectionMst.setRejType(rejId);
        }
        documentList = iChecklistVerificationService.getDocumentUploaded(applicationId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.REJ_APPLICATIONID, applicationId);
        model.addAttribute(MainetConstants.LABLE_ID, labelId);
        model.addAttribute(MainetConstants.SERVICE_ID, serviceId);
        model.addAttribute(MainetConstants.CommonConstants.LEVEL, level);
        final List<TbRejectionMst> tbRejectionMstforupdat = tbRejectionMstService.findByApplicationID(tbRejectionMst);
        if (tbRejectionMstforupdat.isEmpty()) {
            model.addAttribute(MainetConstants.FIRST_ATTEMPT, MainetConstants.FIRST_ATTEMPT);
        } else {
            model.addAttribute(MainetConstants.SECOND_ATTEMPT, MainetConstants.SECOND_ATTEMPT);
        }
        httpServletRequest.getSession().removeAttribute(MainetConstants.APPL_ID);
        httpServletRequest.getSession().removeAttribute(MainetConstants.CommonConstants.LEVEL);
        httpServletRequest.getSession().removeAttribute(MainetConstants.LABEL_VAL);
        httpServletRequest.getSession().removeAttribute(MainetConstants.LABEL_ID);
        httpServletRequest.getSession().removeAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID);
        model.addAttribute(MainetConstants.DOCUMENT_LIST, documentList);
        model.addAttribute(MAIN_LIST_NAME, list);
        model.addAttribute(MAIN_ENTITY_NAME, tbRejectionMst);
        return JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbRejectionMst
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping("/form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        // --- Populates the model with a new instance
        final TbRejectionMst tbRejectionMst = new TbRejectionMst();
        populateModel(model, tbRejectionMst, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbRejectionMst entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST) // GET or POST
    public String create(@Valid final TbRejectionMst tbRejectionMst, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        try {
            if (!bindingResult.hasErrors()) {
                final Organisation org = UserSession.getCurrent().getOrganisation();
                model.addAttribute(MainetConstants.LABLE_ID, tbRejectionMst.getLabelId());
                final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
                        Long.valueOf(tbRejectionMst.getRejServiceId() + MainetConstants.BLANK),
                        org.getOrgid());
                final List<TbRejectionMst> tbRejectionMstforupdat = tbRejectionMstService.findByApplicationID(tbRejectionMst);
                List<TbRejectionMst> tbRejectionMstCreated = null;
                if (tbRejectionMstforupdat.isEmpty())// firstattempt Secondattempt check
                {
                    String RejDesc = null;
                    // firstattempt
                    final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM, org);
                    for (final LookUp Lookup1 : lookUpList) {
                        final long RejType = Long.valueOf(tbRejectionMst.getRejType() + MainetConstants.BLANK);
                        if (Lookup1.getLookUpId() == RejType) {
                            RejDesc = Lookup1.getLookUpCode();
                        }
                    }
                    if ((tbRejectionMst.getRejType() != null) && RejDesc.equals(PrefixConstants.TRU)) {
                        model.addAttribute(TURTIPATRA, TURTIPATRA);
                        tbRejectionMst.setRejLetterNo(ApplicationSession.getInstance().getMessage("scutiny.letter.trutiPatra")
                                + MainetConstants.operator.FORWARD_SLACE
                                + serviceMaster.getSmShortdesc()
                                + MainetConstants.operator.FORWARD_SLACE
                                + UserSession.getCurrent().getOrganisation().getOrgid()
                                + MainetConstants.operator.FORWARD_SLACE
                                + Utility.getCurrentYear()
                                + MainetConstants.operator.FORWARD_SLACE
                                + seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                                        MainetConstants.TB_REJECTION_MST, MainetConstants.REJ_LETTER_NO,
                                        org.getOrgid(), "F", null));
                        model.addAttribute(TURTIPATRAN_NO, tbRejectionMst.getRejLetterNo());
                    } else {
                        model.addAttribute(MainetConstants.REJCTION, MainetConstants.REJCTION);
                        tbRejectionMst.setRejLetterNo(ApplicationSession.getInstance().getMessage("scutiny.letter.rejection")
                                + MainetConstants.operator.FORWARD_SLACE
                                + serviceMaster.getSmShortdesc()
                                + MainetConstants.operator.FORWARD_SLACE
                                + UserSession.getCurrent().getOrganisation().getOrgid()
                                + MainetConstants.operator.FORWARD_SLACE
                                + Utility.getCurrentYear()
                                + MainetConstants.operator.FORWARD_SLACE
                                + seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                                        MainetConstants.TB_REJECTION_MST, MainetConstants.REJ_LETTER_NO,
                                        org.getOrgid(), "F", null));
                        model.addAttribute(MainetConstants.REJCTION_NO, tbRejectionMst.getRejLetterNo());
                    }
                   
                    tbRejectionMstCreated = tbRejectionMstService.create(tbRejectionMst);
                }

                else {
                    // Secondattempt
                    tbRejectionMstCreated = tbRejectionMstforupdat;
                    if ((tbRejectionMst.getRejType() != null) && tbRejectionMst.getRejType().equals(Long.valueOf(5829))) {
                        model.addAttribute(TURTIPATRA, TURTIPATRA);
                        model.addAttribute(TURTIPATRAN_NO, tbRejectionMstCreated.get(0).getRejLetterNo());
                    } else {
                        model.addAttribute(MainetConstants.REJCTION, MainetConstants.REJCTION);
                        model.addAttribute(MainetConstants.REJCTION_NO, tbRejectionMstCreated.get(0).getRejLetterNo());
                    }
                }
                List<TbApprejMas> tbApprejMas = new ArrayList<>();
                model.addAttribute(MainetConstants.DOCUMENT_LIST, documentList);
                Department dept = serviceMaster.getTbDepartment(); 

                if (!tbRejectionMstCreated.isEmpty()) {
                    model.addAttribute(MainetConstants.REJ_APPLICATION_ID, tbRejectionMstCreated.get(0).getRejApplicationId());
                    final SimpleDateFormat df = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    final String rejdate = df.format(tbRejectionMstCreated.get(0).getRejLetterDate());
                    model.addAttribute(MainetConstants.REJ_DATE, rejdate);
                    final TbCfcApplicationMstEntity applicantDetails = cfcService.getCFCApplicationByApplicationId(
                            Long.valueOf(tbRejectionMstCreated.get(0).getRejApplicationId() + MainetConstants.BLANK),
                            UserSession.getCurrent().getOrganisation().getOrgid());
    		        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && MainetConstants.DeptCode.WATER.equals(dept.getDpDeptcode())) {
    			        String connectionNumber = tbRejectionMstService.setConnectionNumber(dept, tbRejectionMstCreated.get(0).getRejApplicationId(),
    			        		org, serviceMaster);
    			        model.addAttribute(MainetConstants.CONNCETION_NO, connectionNumber !=null ? connectionNumber : StringUtils.EMPTY);
    		        }
                    model.addAttribute(redirectAttributes);
                    model.addAttribute(MainetConstants.NAME, applicantDetails.getApmFname());
                    model.addAttribute(MainetConstants.APM_APPLICATION_DATE,
                            Utility.dateToString(applicantDetails.getApmApplicationDate(),
                                    MainetConstants.DATE_HOUR_FORMAT));
                    final String ServiceName = serviceMasterService
                            .getServiceNameByServiceId(
                                    Long.valueOf(tbRejectionMstCreated.get(0).getRejServiceId() + MainetConstants.BLANK));
                    model.addAttribute(MainetConstants.SERVICE_NAME, ServiceName);
                    model.addAttribute(MainetConstants.SERVICE_ID, tbRejectionMstCreated.get(0).getRejServiceId());
                    final List<Long> ArtId = new ArrayList<>(0);
                    for (final TbRejectionMst tbRejectionMst2 : tbRejectionMstCreated) {
                        ArtId.add(tbRejectionMst2.getRejRefId());
                    }
                    tbApprejMas = tbApprejMasServiceImpl.findByArtId(ArtId,
                            UserSession.getCurrent().getOrganisation().getOrgid());
                }

                model.addAttribute(MainetConstants.REMARK_LIST, tbApprejMas);
                model.addAttribute(MAIN_ENTITY_NAME, tbRejectionMstCreated);
		        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && MainetConstants.DeptCode.WATER.equals(dept.getDpDeptcode())) {
		        	model.addAttribute(MainetConstants.ComparamMasterConstants.WATER_TAX, dept.getDpDeptdesc());
			        model.addAttribute(MainetConstants.DeptCode.WATER, dept.getDpDeptcode());
		        }

                return JSP_FORM;
            } else {
                populateModel(model, tbRejectionMst, FormMode.CREATE);
                final List<TbApprejMas> list = tbApprejMasService.findAll();
                model.addAttribute(MAIN_LIST_NAME, list);
                return JSP_LIST;
            }
        } catch (final Exception e) {
            log("Action 'create' : Exception - " + e.getMessage());
            messageHelper.addException(model, "tbRejectionMst.error.create", e);
            final List<TbApprejMas> list = tbApprejMasService.findAll();
            model.addAttribute(MAIN_LIST_NAME, list);
            return JSP_LIST;
        }
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbRejectionMst entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update") // GET or POST
    public String update(@Valid final TbRejectionMst tbRejectionMst, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        try {
            if (!bindingResult.hasErrors()) {
                // --- Perform database operations
                final TbRejectionMst tbRejectionMstSaved = tbRejectionMstService.update(tbRejectionMst);
                model.addAttribute(MAIN_ENTITY_NAME, tbRejectionMstSaved);
                log("Action 'update' : update done - redirect");
                return redirectToForm(httpServletRequest, tbRejectionMst.getRejId(), tbRejectionMst.getOrgid());
            } else {
                log("Action 'update' : binding errors");
                populateModel(model, tbRejectionMst, FormMode.UPDATE);
                return JSP_FORM;
            }
        } catch (final Exception e) {
            messageHelper.addException(model, "tbRejectionMst.error.update", e);
            log("Action 'update' : Exception - " + e.getMessage());
            populateModel(model, tbRejectionMst, FormMode.UPDATE);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "ScrutinyTurtipatraRejectionLetter", method = RequestMethod.POST)
    public String scrutinyInspectionLetter(@RequestParam("applId") final long applId,
            @RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
            @RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
            @RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
            final HttpServletRequest httpServletRequest,
            final Model model) {
        httpServletRequest.getSession().setAttribute(MainetConstants.APPL_ID, applId);
        httpServletRequest.getSession().setAttribute(MainetConstants.LABEL_ID, labelId);
        httpServletRequest.getSession().setAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, serviceId);
        httpServletRequest.getSession().setAttribute(MainetConstants.CommonConstants.LEVEL, level);
        httpServletRequest.getSession().setAttribute(MainetConstants.LABEL_VAL, labelVal);
        final String url = (String) httpServletRequest.getRequestURL().subSequence(36, 51);
        if (url.equalsIgnoreCase(TURTIPATRA_HTML)) {
            return new String("redirect:/Turtipatra.html");
        }
        return new String("redirect:/RejectionMst.html");

    }

}
