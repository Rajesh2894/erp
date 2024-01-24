package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.CustomerMasterDTO;
import com.abm.mainet.common.master.service.ICustomerMasterService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.ui.model.CustomerMasterModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/CustomerMaster.html")
public class CustomerMasterController extends AbstractFormController<CustomerMasterModel> {

    private static final String JSP_FORM = "CustomerMasterForm";
    private static final String JSP_SEARCH_LIST = "CustomerMasterSearch";
    private static final String JSP_EXCELUPLOAD = "CustomerMasterUpload";
    private static final String SEARCH_CUSTOMER = "searchCustomer";
    private static final String OPEN_CUSTOMER_FORM = "openCustomerForm";
    private static final String EDIT_CUSTOMER_FORM = "EditCustomerMaster";
    private static final String VIEW_CUSTOMER_FORM = "ViewCustomerMaster";
    private static final String UPLOAD_CUSTOMER_FORM = "uploadCustomerForm";
    private static final String EXCEL_TEMPLATE = "ExcelTemplateData";
    private static final String LOAD_EXCEL = "loadExcelData";

    // --- Main entity service

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ICustomerMasterService customerMasterService;


    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        this.getModel().setCommonHelpDocs("CustomerMaster.html");
        this.getModel().setCustMasterDtos(customerMasterService.searchCustomer(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = OPEN_CUSTOMER_FORM)
    public ModelAndView addCustomerMaster(final HttpServletRequest request) {

        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        return new ModelAndView(JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = EDIT_CUSTOMER_FORM)
    public ModelAndView editCustomerMaster(@RequestParam Long id, final HttpServletRequest request) {

        this.getModel().setCustMasterDTO(customerMasterService.getById(id));
        this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
        return new ModelAndView(JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = VIEW_CUSTOMER_FORM)
    public ModelAndView viewCustomerMaster(@RequestParam Long id, final HttpServletRequest request) {

        this.getModel().setCustMasterDTO(customerMasterService.getById(id));
        this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
        return new ModelAndView(JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = UPLOAD_CUSTOMER_FORM)
    public ModelAndView importExportCustomerMaster(final HttpServletRequest request) {
        fileUpload.sessionCleanUpForFileUpload();
        return new ModelAndView(JSP_EXCELUPLOAD, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = SEARCH_CUSTOMER)
    public ModelAndView searchCustomerMaster(@RequestParam(required = false) String custName,
            @RequestParam(required = false) String custAddress, final HttpServletRequest request) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setCustMasterDtos(customerMasterService.searchCustomer(custName, custAddress, orgId));
        return new ModelAndView(JSP_SEARCH_LIST, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = EXCEL_TEMPLATE, method = { RequestMethod.GET })
    public void exportCustomerMasterExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        try {
            WriteExcelData<CustomerMasterDTO> data = new WriteExcelData<>("CustomerMaster.xlsx", request, response);

            data.getExpotedExcelSheet(new ArrayList<CustomerMasterDTO>(), CustomerMasterDTO.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = LOAD_EXCEL, method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {
        this.bindModel(request);
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
        final String filePath = getUploadedFinePath();

        int i = 1;
        try {
            ReadExcelData<CustomerMasterDTO> data = new ReadExcelData<>(filePath, CustomerMasterDTO.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();
            if (!errlist.isEmpty()) {
                this.getModel().addValidationError(session.getMessage("accounts.empty.excel"));
            } else {
                final List<CustomerMasterDTO> customerMasterDtoList = data.getParseData();
                List<CustomerMasterDTO> custList = new ArrayList<>();
                final List<LookUp> customerType = CommonMasterUtility.getLookUps(MainetConstants.Property.propPref.OWT, organisation);

                for (CustomerMasterDTO custMasterDTO : customerMasterDtoList) {
                    if (custMasterDTO.getCustTypeStr() != null) {
                        for (LookUp lookUp : customerType) {
                            if (custMasterDTO.getCustTypeStr().trim().equalsIgnoreCase(lookUp.getLookUpDesc())) {
                                custMasterDTO.setCustType(lookUp.getLookUpId());
                            }
                        }
                        if (custMasterDTO.getCustType() == null) {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custType") + i);
                        }
                    } else {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.empty.custType") + i);
                    }

                    if (custMasterDTO.getCustName() != null) {
                        if (customerMasterService.getCustomerByName(custMasterDTO.getCustName().trim(), orgId).isEmpty()) {
                            custMasterDTO.setCustName(custMasterDTO.getCustName().trim());
                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custName") + i);
                        }

                    } else {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.empty.custName") + i);
                    }

                    if (custMasterDTO.getCustAddress() != null) {
                        custMasterDTO.setCustAddress(custMasterDTO.getCustAddress().trim());
                    } else {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.empty.custAddress") + i);
                    }

                    if (custMasterDTO.getCustTINNoStr() != null) {
                        if (customerMasterService.getCustomerByTINNo(custMasterDTO.getCustTINNoStr().trim(), orgId).isEmpty()) {
                            Pattern pattern = Pattern.compile("\\d{10}");
                            if (pattern.matcher(custMasterDTO.getCustTINNoStr().trim()).matches()) {
                                custMasterDTO.setCustTINNo(custMasterDTO.getCustTINNoStr().trim());
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custTinNo") + i);
                            }

                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custTinNo") + i);
                        }

                    } else {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.empty.custTinNo") + i);
                    }

                    if (custMasterDTO.getCustEmailId() != null) {
                        if (customerMasterService.getCustomerByEmail(custMasterDTO.getCustEmailId().trim(), orgId).isEmpty()) {

                            Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

                            if (pattern.matcher(custMasterDTO.getCustEmailId().trim()).matches()) {
                                custMasterDTO.setCustEmailId(custMasterDTO.getCustEmailId().trim());
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custEmail") + i);
                            }

                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custEmail") + i);
                        }

                    }

                    if (custMasterDTO.getCustGSTNo() != null) {
                        if (customerMasterService.getCustomerByGstNo(custMasterDTO.getCustGSTNo(), orgId).isEmpty()) {

                            Pattern pattern = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}\\d[Z]{1}[A-Z\\d]{1}");
                            if (pattern.matcher(custMasterDTO.getCustGSTNo().trim()).matches()) {
                                custMasterDTO.setCustGSTNo(custMasterDTO.getCustGSTNo().trim());
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custGstNo") + i);
                            }

                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custGstNo") + i);
                        }
                    }

                    if (custMasterDTO.getCustMobNoStr() != null) {
                        if (customerMasterService.getcustomerByMobileNo(custMasterDTO.getCustMobNoStr().trim(), orgId).isEmpty()) {
                            Pattern mobilePattern = Pattern.compile("\\d{10}");
                            boolean isValidmobile = mobilePattern.matcher(custMasterDTO.getCustMobNoStr().trim()).matches();
                            if (isValidmobile) {
                                custMasterDTO.setCustMobNo(custMasterDTO.getCustMobNoStr().trim());
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custMobNo") + i);
                            }
                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custMobNo") + i);
                        }

                    }

                    if (custMasterDTO.getCustPANNo() != null) {
                        if (customerMasterService.getcustomerByPanNo(custMasterDTO.getCustPANNo().trim(), orgId).isEmpty()) {
                            Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                            if (pattern.matcher(custMasterDTO.getCustPANNo().trim()).matches()) {
                                custMasterDTO.setCustPANNo(custMasterDTO.getCustPANNo().trim());
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custPanNo") + i);
                            }
                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custPanNo") + i);
                        }

                    }

                    if (custMasterDTO.getCustUIDNoStr() != null) {
                        if (customerMasterService.getcustomerByUidNo(Long.valueOf(custMasterDTO.getCustUIDNoStr()), orgId).isEmpty()) {
                            Pattern aadharPattern = Pattern.compile("\\d{12}");
                            boolean isValidAadhar = aadharPattern.matcher(custMasterDTO.getCustUIDNoStr().toString()).matches();
                            if (isValidAadhar) {
                                custMasterDTO.setCustUIDNo(Long.valueOf(custMasterDTO.getCustUIDNoStr()));
                            } else {
                                this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.invalid.custUidNo") + i);
                            }
                        } else {
                            this.getModel().addValidationError(session.getMessage("accounts.customerMaster.validation.excel.duplicate.custUidNo") + i);
                        }

                    }

                    LookUp cpdstatus = CommonMasterUtility.getDefaultValue(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
                    custMasterDTO.setCustStatus(cpdstatus.getLookUpId());
                    custMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    custMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    custMasterDTO.setCreatedDate(new Date());
                    custMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    custList.add(custMasterDTO);
                    i++;
                }
                if (!this.getModel().hasValidationErrors()) {
                    if (custList.size() > 1) {
                        duplicationCheckInList(custList);
                    }
                }
                if (!this.getModel().hasValidationErrors()) {
                    customerMasterService.saveCustomerList(custList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileUpload.sessionCleanUpForFileUpload();

        return customResult(JSP_EXCELUPLOAD);
    }

    private void duplicationCheckInList(List<CustomerMasterDTO> custList) {
        final ApplicationSession session = ApplicationSession.getInstance();
        int secondRow = 1;
        for (int k = 0; k < custList.size(); k++) {
            for (int j = secondRow; j < custList.size(); j++) {
                if (custList.get(k).getCustMobNo() != null && custList.get(j).getCustMobNo() != null)
                    if (custList.get(k).getCustMobNo().equals(custList.get(j).getCustMobNo())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.mobNo") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustPANNo() != null && custList.get(j).getCustPANNo() != null)
                    if (custList.get(k).getCustPANNo().equals(custList.get(j).getCustPANNo())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.panNo") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustGSTNo() != null && custList.get(j).getCustGSTNo() != null)
                    if (custList.get(k).getCustGSTNo().equals(custList.get(j).getCustGSTNo())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.GstNo") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustTINNo() != null && custList.get(j).getCustTINNo() != null)
                    if (custList.get(k).getCustTINNo().equals(custList.get(j).getCustTINNo())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.TinNo") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustUIDNo() != null && custList.get(j).getCustUIDNo() != null)
                    if (custList.get(k).getCustUIDNo().equals(custList.get(j).getCustUIDNo())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.UidNo") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustName() != null && custList.get(j).getCustName() != null)
                    if (custList.get(k).getCustName().equals(custList.get(j).getCustName())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.custName") + (k + 1) + " & " + (j + 1));
                    }
                if (custList.get(k).getCustEmailId() != null && custList.get(j).getCustEmailId() != null)
                    if (custList.get(k).getCustEmailId().equals(custList.get(j).getCustEmailId())) {
                        this.getModel().addValidationError(session.getMessage("accounts.customerMaster.excel.duplicate.custEmail") + (k + 1) + " & " + (j + 1));
                    }
            }
            secondRow++;
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

}
