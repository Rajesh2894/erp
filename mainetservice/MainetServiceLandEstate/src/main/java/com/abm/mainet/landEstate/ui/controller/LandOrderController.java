package com.abm.mainet.landEstate.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.scrutiny.ui.controller.ApplicationAuthorizationController;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.TbWorkOrder;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.service.TbWorkOrderService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.landEstate.dao.ILandAcquisitionDao;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;
import com.abm.mainet.landEstate.service.ILandAcquisitionService;
import com.abm.mainet.landEstate.ui.model.LandAcquisitionModel;

@Controller
@RequestMapping(value = { "/LandOrder.html" })
public class LandOrderController extends AbstractFormController<LandAcquisitionModel> {

    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "tbWorkOrder";
    private static final String MAIN_PRIFEX_WPC = "WOR";
    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_LIST = "landOrder/list";

    String REDIRECT_LAND_ORDER_HTML = "redirect:/LandOrder.html?generatLandOrder";

    private static final String ERROR_MESSAGE = "Error Occurred while request processing for Application Authorization for Application No.=";
    private static final String SERVICE_URL_NOT_CONFIGURED = "Service action Url is not configured in Service Master against serviceId=";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthorizationController.class);

    @Resource
    private CommonService commonService;

    @Autowired
    private IOrganisationService iOrganisationService;

    // --- Main entity service
    @Autowired
    private TbWorkOrderService tbWorkOrderService;

    @Resource
    private TbServicesMstService tbServicesMstService;
    // --- Main entity service
    @Resource
    private TbApprejMasService tbApprejMasService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private ICFCApplicationMasterService icfcApplicationMasterService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private ILandAcquisitionService acquisitionService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private DepartmentService departmentService;

    /**
     * Shows a list with all the occurrences of TbWorkOrder found in the database
     * 
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "showDetails", method = { RequestMethod.POST, RequestMethod.GET })
    public String workorder(@RequestParam("appNo") final long applicationId,
            @RequestParam("actualTaskId") final Long actualTaskId, final HttpServletRequest request,
            final Model model) {
        final TbWorkOrder tbWorkOrder = new TbWorkOrder();
        String actionURL = null;
        String approvalDate = "NA", landOwnername = "NA";
        BigDecimal valuationAmt = null;
        try {
            final List<String> paramList = commonService.findServiceActionUrl(applicationId,
                    UserSession.getCurrent().getOrganisation().getOrgid());

            /*
             * UserSession.getCurrent().getScrutinyCommonParamMap().put( MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID,
             * applicationId + MainetConstants.BLANK);
             * UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.CFC_URL,
             * paramList.get(MainetConstants.INDEX.ZERO)); UserSession.getCurrent().getScrutinyCommonParamMap()
             * .put(MainetConstants.SCRUTINY_COMMON_PARAM.SM_SERVICE_ID, paramList.get(MainetConstants.INDEX.ONE));
             * UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID,
             * actualTaskId.toString()); if ((paramList.get(MainetConstants.INDEX.ZERO) == null) ||
             * paramList.get(MainetConstants.INDEX.ZERO).toString().isEmpty()) { throw new
             * FrameworkException(SERVICE_URL_NOT_CONFIGURED + paramList.get(MainetConstants.INDEX.ONE)); } else { final String
             * serviceId = UserSession.getCurrent().getScrutinyCommonParamMap()
             * .get(MainetConstants.SCRUTINY_COMMON_PARAM.SM_SERVICE_ID); final String taskId =
             * UserSession.getCurrent().getScrutinyCommonParamMap() .get(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID);
             * request.getSession().setAttribute(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO, applicationId);
             * request.getSession().setAttribute(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID, serviceId);
             * request.getSession().setAttribute(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID, taskId); return new
             * String(REDIRECT_LAND_ORDER_HTML); // return new String("redirect:/generatLandOrder"); }
             */

            // make data for LOI order from TB_EST_AQUISN AND TB_LOI_MAS

            LandAcquisitionDto acquisitionDto = acquisitionService.getLandAcqProposalByAppId(applicationId);
            this.getModel().setAcquisitionDto(acquisitionDto);
            landOwnername = acquisitionDto.getPayTo();
            valuationAmt = acquisitionDto.getAcqCost();
            TbLoiMasEntity loiMasEntity = ApplicationContextProvider.getApplicationContext().getBean(ILandAcquisitionDao.class)
                    .getLoiMasDataByApplicationId(applicationId);
            this.getModel().setServiceId(loiMasEntity.getLoiServiceId());// serviceId set used when print
            this.getModel().setApmApplicationId(applicationId);
            this.getModel().setTaskId(actualTaskId);
            approvalDate = Utility.dateToString(loiMasEntity.getLoiDate());

        } catch (final Exception ex) {
            LOGGER.error(ERROR_MESSAGE + applicationId, ex);
        }
        model.addAttribute("approvalDate", approvalDate);
        model.addAttribute("landOwnername", landOwnername);
        model.addAttribute("valuationAmt", valuationAmt);
        model.addAttribute(MAIN_ENTITY_NAME, tbWorkOrder);

        return JSP_LIST;

    }

    @RequestMapping(params = "generatLandOrder", method = { RequestMethod.POST, RequestMethod.GET })
    public String generatLandOrder(final HttpServletRequest httpServletRequest, final Model model) {
        Long taskId = null;
        final long applicationId = Long.parseLong(httpServletRequest.getSession()
                .getAttribute(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO).toString());
        final long serviceId = Long.parseLong(httpServletRequest.getSession().getAttribute("serviceId").toString());

        final String serviceName = tbServicesMstService.getServiceNameByServiceId(serviceId);

        if (!httpServletRequest.getSession().getAttribute("taskId").equals("null")) {
            taskId = Long.parseLong(httpServletRequest.getSession().getAttribute("taskId").toString());
        }

        final TbWorkOrder tbWorkOrder = new TbWorkOrder();
        long workorderid = 0;
        final long deparmentid = tbServicesMstService.findDepartmentIdByserviceid(serviceId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        TbDepartment department = tbDepartmentService.findById(deparmentid);
        String dept = MainetConstants.BLANK;
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dept = department.getDpDeptdesc();
        } else {
            dept = department.getDpNameMar();
        }

        final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(
                Long.valueOf(applicationId + MainetConstants.BLANK),
                UserSession.getCurrent().getOrganisation().getOrgid());
        String mobileNo = address.getApaMobilno() != null ? address.getApaMobilno() : MainetConstants.BLANK;
        final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
        final String ApplicantFullName = tbCfcApplicationMstEntity.getApmFname() + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmMname() != null ? tbCfcApplicationMstEntity.getApmMname()
                        : MainetConstants.BLANK)
                + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmLname() != null ? tbCfcApplicationMstEntity.getApmLname()
                        : MainetConstants.BLANK);
        final Date ApplicarionDate = tbCfcApplicationMstEntity.getApmApplicationDate();
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpCode().equalsIgnoreCase(MAIN_PRIFEX_WPC)) {
                workorderid = Lookup1.getLookUpId();
            }
        }
        List<TbApprejMas> apprejMasList = new ArrayList<>();
        apprejMasList = tbApprejMasService.findByRemarkType(serviceId, workorderid);
        tbWorkOrder.setWoServiceId(serviceId);
        tbWorkOrder.setWoDeptId(deparmentid);
        tbWorkOrder.setWoApplicationDateS(ApplicarionDate + MainetConstants.BLANK);
        tbWorkOrder.setWoApplicationId(applicationId);
        tbWorkOrder.setTaskId(taskId);
        model.addAttribute("applicationId", applicationId);
        model.addAttribute("ApplicantFullName", ApplicantFullName);
        model.addAttribute("ApplicarionDate", ApplicarionDate);
        model.addAttribute("apprejMasList", apprejMasList);
        model.addAttribute(MAIN_ENTITY_NAME, tbWorkOrder);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("deptName", dept);
        model.addAttribute("mobileNo", mobileNo);
        httpServletRequest.getSession().removeAttribute("applicationId");
        httpServletRequest.getSession().removeAttribute("conncetionNo");
        httpServletRequest.getSession().removeAttribute("serviceId");
        httpServletRequest.getSession().removeAttribute("taskId");
        return JSP_LIST;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param tbWorkOrder entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST) // GET or POST
    public ModelAndView create(@Valid final TbWorkOrder tbWorkOrder, final Model model,
            final HttpServletRequest httpServletRequest) {
        try {

            /*
             * final String woApplicationDate = tbWorkOrder.getWoApplicationDateS(); final SimpleDateFormat formatter = new
             * SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); final Date date = formatter.parse(woApplicationDate);
             * tbWorkOrder.setWoApplicationDate(date);
             */
            // final TbWorkOrder tbWorkOrderCreated = tbWorkOrderService.create(tbWorkOrder);
            // update ACQ_STATUS like Acquired (A)
            /*
             * Long apmApplicationId = tbWorkOrder.getWoApplicationId(); Long serviceId = tbWorkOrder.getWoServiceId();
             */

            acquisitionService.updateLandProposalAcqStatusById(this.getModel().getApmApplicationId(),
                    UserSession.getCurrent().getEmployee(), this.getModel().getServiceId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getTaskId());

            // model.addAttribute(MAIN_ENTITY_NAME, tbWorkOrderCreated);
            // model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL, "AdminHome.html");
            // return MainetConstants.CommonConstants.SUCCESS_PAGE;

        } catch (final Exception e) {
            // return new String("redirect:/AdminHome.html?");
        }
        return jsonResult(JsonViewObject.successResult("Land order successfully"));
    }

    @RequestMapping(params = "printLandReport")
    public String printLandReport(final HttpServletRequest request, final Model model) {
        try {
            Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            String yearId = UserSession.getCurrent().getFinYearId();
            String approvalDate = "NA", landOwnername = "NA";
            BigDecimal valuationAmt = null;
            LandAcquisitionDto acquisitionDto = this.getModel().getAcquisitionDto();
            landOwnername = acquisitionDto.getPayTo();
            valuationAmt = acquisitionDto.getAcqCost();
            TbLoiMasEntity loiMasEntity = ApplicationContextProvider.getApplicationContext().getBean(ILandAcquisitionDao.class)
                    .getLoiMasDataByApplicationId(this.getModel().getAcquisitionDto().getApmApplicationId());
            approvalDate = Utility.dateToString(loiMasEntity.getLoiDate());
            Organisation org = iOrganisationService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
            final ChallanReceiptPrintDTO receiptDTO = new ChallanReceiptPrintDTO();
            receiptDTO.setOrgName(org.getONlsOrgname());
            receiptDTO.setOrgNameMar(org.getONlsOrgnameMar());
            final String date = Utility.dateToString(new Date());
            receiptDTO.setReceiptDate(date);
            final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            final String time = localDateFormat.format(new Date());
            receiptDTO.setReceiptTime(time);
            if (yearId != null && !yearId.isEmpty()) {
                FinancialYear finYear = iFinancialYear.getFinincialYearsById(Long.valueOf(yearId), orgId);
                if (finYear != null) {
                    final SimpleDateFormat sdf1 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
                    final String startYear = sdf1.format(finYear.getFaFromDate());
                    final SimpleDateFormat sdf2 = new SimpleDateFormat(MainetConstants.YEAR_FORMAT1);
                    final String endYaer = sdf2.format(finYear.getFaToDate());
                    receiptDTO.setFinYear(startYear + MainetConstants.HYPHEN + endYaer);
                }
            }
            receiptDTO.setPaymentText(ApplicationSession.getInstance().getMessage("receipt.label.offline"));
            final long deparmentid = tbServicesMstService.findDepartmentIdByserviceid(this.getModel().getServiceId(), orgId);

            TbDepartment department = tbDepartmentService.findById(deparmentid);
            String dept = MainetConstants.BLANK;
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                dept = department.getDpDeptdesc();
            } else {
                dept = department.getDpNameMar();
            }
            receiptDTO.setDeptName(dept);
            receiptDTO.setReceivedFrom(acquisitionDto.getPayTo());
            receiptDTO.setApplicationNumber(acquisitionDto.getApmApplicationId());
            receiptDTO.setReferenceNo(loiMasEntity.getLoiNo());
            receiptDTO.setReceiptDate(approvalDate);
            receiptDTO.setDate(approvalDate);
            receiptDTO.setAmount(Double.valueOf(acquisitionDto.getAcqCost().toString()));
            receiptDTO.setAmountInWords(Utility.convertBigNumberToWord(acquisitionDto.getAcqCost()));
            receiptDTO.setReceiverName(acquisitionDto.getPayTo());
            model.addAttribute("approvalDate", approvalDate);
            model.addAttribute("landOwnername", landOwnername);
            model.addAttribute("valuationAmt", valuationAmt);
            model.addAttribute("receiptDTO", receiptDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "landOrderPrinting";
    }
    
}
