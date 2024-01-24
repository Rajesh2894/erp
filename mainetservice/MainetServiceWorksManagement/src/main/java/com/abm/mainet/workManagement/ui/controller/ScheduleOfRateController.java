package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.ReadExcelData;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WriteExcelData;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.ui.model.ScheduleOfRateModel;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
@Controller
@RequestMapping(MainetConstants.ScheduleOfRate.HTML)
public class ScheduleOfRateController extends AbstractFormController<ScheduleOfRateModel> {

    private static final String SOR_EXPORT_EXCEPTION = "Exception while exporting Schedule of rate Data : ";
    private static final String SOR_IMPORT_EXCEPTION = "Exception while importing Schedule of rate Data : ";

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Autowired
    private WmsMaterialMasterService materialMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    /**
     * used for showing default home page for schedule of rate master
     * 
     * @param httpServletRequest
     * @return default view
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("ScheduleOfRate.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<ScheduleOfRateMstDto> mstDtoList = scheduleOfRateService
                .getAllScheduleRateMstList(orgId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final List<LookUp> defultStatusUAD = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation)
                .stream().filter(c -> c.getDefaultVal().equals("Y")).collect(Collectors.toList());

        if (defultStatusUAD.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            this.getModel().setUADstatus(defultStatusUAD.get(0).getLookUpCode());

        }
        if (mstDtoList != null) {
            model.addAttribute(MainetConstants.ScheduleOfRate.MST_DTO_LIST, mstDtoList);
        }
        return defaultResult();
    }

    /**
     * used for create Schedule of rate master details form
     * 
     * @param sorId
     * @param sorDId
     * @param modeType
     * @return
     * @throws Exception
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView sorForm(
            @RequestParam(value = MainetConstants.ScheduleOfRate.SOR_ID, required = false) Long sorId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type) {
        ScheduleOfRateModel sorModel = this.getModel();
        populateModel(sorModel, sorId, type);
        return new ModelAndView(MainetConstants.ScheduleOfRate.SOR_FORM, MainetConstants.FORM_NAME, sorModel);
    }

    /**
     * populate common details
     * 
     * @param sorModel
     * @param sorId
     * @param modeType
     */
    private void populateModel(ScheduleOfRateModel sorModel, Long sorId, String mode) {
        sorModel.setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        sorModel.setSorCategoryList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WKC,
                UserSession.getCurrent().getOrganisation()));

        // check for sub category flag is active or not
        List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
                UserSession.getCurrent().getOrganisation());
        if (lookUpList != null && !lookUpList.isEmpty()) {
            sorModel.setSubCatMode(lookUpList.get(0).getLookUpCode());
        } else {
            sorModel.setSubCatMode(null);
        }

        if (mode.equals(MainetConstants.MODE_CREATE)) {
            sorModel.setMstDto(new ScheduleOfRateMstDto());
            sorModel.setModeType(MainetConstants.MODE_CREATE);
        } else if (mode.equals(MainetConstants.MODE_UPLOAD)) {
            sorModel.setModeType(MainetConstants.MODE_UPLOAD);
        } else {
            final ScheduleOfRateMstDto mastDTO = getScheduleOfRateMaster(sorId);
            sorModel.setMstDto(mastDTO);
            if (mode.equals(MainetConstants.MODE_EDIT)) {
                sorModel.setModeType(MainetConstants.MODE_EDIT);
            } else {
                sorModel.setModeType(MainetConstants.MODE_VIEW);
            }
        }
    }

    // common method for getting schedule of rate master details by its primary key.
    private ScheduleOfRateMstDto getScheduleOfRateMaster(Long sorId) {
        return scheduleOfRateService.findSORMasterWithDetailsBySorId(sorId);
    }

    /**
     * used to search records by filter criteria by sor type or sorName or sor start date or sor end date with organization id
     * 
     * @param request
     * @param sorType
     * @param sorName
     * @param sorFromDate
     * @param sorToDate
     * @return List<ScheduleOfRateMstDto> if record found else return empty dto
     */
    @RequestMapping(params = MainetConstants.ScheduleOfRate.FILTER_SOR_DATA, method = RequestMethod.POST)
    public @ResponseBody List<ScheduleOfRateMstDto> filterMstDataList(final HttpServletRequest request,
            @RequestParam(value = MainetConstants.ScheduleOfRate.SORNAME_ID, required = false) Long sorNameId,
            @RequestParam(value = MainetConstants.ScheduleOfRate.SOR_FROM_DATE, required = false) Date sorFromDate,
            @RequestParam(value = MainetConstants.ScheduleOfRate.SOR_TO_DATE, required = false) Date sorToDate) {
        return scheduleOfRateService.searchSorRecords(UserSession.getCurrent().getOrganisation().getOrgid(), sorNameId,
                sorFromDate, sorToDate);
    }

    /**
     * Used to Inactive SOR Master and Item details
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.ScheduleOfRate.INACTIVE_SOR_MASTER, method = RequestMethod.POST)
    public boolean deActiveSorMaster(@RequestParam(MainetConstants.ScheduleOfRate.SOR_ID) Long sorId) {
        scheduleOfRateService.inactiveSorMaster(sorId, UserSession.getCurrent().getEmployee().getEmpId());
        return true;
    }

    /**
     * Used to Export data of Schedule of rate master based on SOR master sorId.
     * 
     * @param response
     * @param request
     */
    @SuppressWarnings({ MainetConstants.ROWTYPES, MainetConstants.UNCHECKED })
    @RequestMapping(params = MainetConstants.WorksManagement.EXPORT_EXEL, method = { RequestMethod.GET })
    public void exportSORExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        ScheduleOfRateModel model = this.getModel();
        model.bind(request);
        if (model.getMstDto().getSorId() != null)
            model.setMstDto(getScheduleOfRateMaster(model.getMstDto().getSorId()));
        try {
            if (model.getMstDto().getSorName() != null) {
                WriteExcelData data = new WriteExcelData(
                        model.getMstDto().getSorName().replaceAll("\\s", MainetConstants.BLANK)
                                + MainetConstants.operator.UNDER_SCORE + MainetConstants.ScheduleOfRate.SOR_FILENAME
                                + MainetConstants.XLSX_EXT,
                        request, response);

                if (this.getModel().getSubCatMode().equals(MainetConstants.FlagY))
                    data.setAlternetKeyForHeader(MainetConstants.WorksManagement.SOR_DET_CPD);
                data.getExpotedExcelSheet(this.getModel().getMstDto().getDetDto(), ScheduleOfRateDetDto.class);
            } else {
                WriteExcelData data = new WriteExcelData(
                        MainetConstants.ScheduleOfRate.SOR_FILENAME + MainetConstants.XLSX_EXT, request, response);
                if (this.getModel().getSubCatMode().equals(MainetConstants.FlagY))
                    data.setAlternetKeyForHeader(MainetConstants.WorksManagement.SOR_DET_CPD);
                data.getExpotedExcelSheet(this.getModel().getMstDto().getDetDto(), ScheduleOfRateDetDto.class);
            }

        } catch (Exception ex) {
            throw new FrameworkException(SOR_EXPORT_EXCEPTION + ex.getMessage());
        }
    }

    /**
     * Used to print Schedule of rate master report based on SOR master sorId.
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = MainetConstants.ScheduleOfRate.PRINT_SOR_REPORT, method = RequestMethod.POST)
    public ModelAndView leadLiftReportData(final HttpServletRequest request) {
        ScheduleOfRateModel model = this.getModel();
        model.bind(request);
        model.setMstDto(getScheduleOfRateMaster(model.getMstDto().getSorId()));
        List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
                UserSession.getCurrent().getOrganisation());
        if (lookUpList != null && !lookUpList.isEmpty()) {
            model.setSubCatMode(lookUpList.get(0).getLookUpCode());
        } else {
            model.setSubCatMode(null);
        }
        return new ModelAndView(MainetConstants.ScheduleOfRate.SOR_REPORT, MainetConstants.FORM_NAME, model);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {
        ScheduleOfRateModel model = this.getModel();
        this.getModel().bind(request);
        String filePath = getUploadedFinePath();
        List<String> errorList = new ArrayList<>();
        try {
            List<WmsErrorDetails> errorDetails = new ArrayList<>();
            ReadExcelData data = new ReadExcelData<>(filePath, ScheduleOfRateDetDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();

            String sorName = CommonMasterUtility
                    .getNonHierarchicalLookUpObjectByPrefix(model.getMstDto().getSorCpdId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.WorksManagement.SRM)
                    .getDescLangFirst();
            String macAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
            Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            Long empId = UserSession.getCurrent().getEmployee().getEmpId();
            Date newDate = new Date();

            if (!errlist.isEmpty()) {
                for (String string : errlist) {
                    WmsErrorDetails errDetails = new WmsErrorDetails();
                    errDetails.setErrFlag(MainetConstants.WorksManagement.SOR_TYPE);
                    errDetails.setErrLabel1(sorName);
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.DATA_TYPE);
                    errDetails.setErrData(string);
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(empId);
                    errDetails.setCreatedDate(newDate);
                    errDetails.setLgIpMac(macAddress);
                    errorDetails.add(errDetails);
                }
            }

            List<ScheduleOfRateDetDto> dtos = data.getParseData();

            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<LookUp> sordCategory = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WKC, organisation);
            List<LookUp> sorIteamUnit = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.WUT, organisation);

            dtos.parallelStream().parallel().forEach(detDto -> {

                WmsErrorDetails errDetails = new WmsErrorDetails();
                int countError = 0;

                if (StringUtils.isNotEmpty(detDto.getSordCategoryDesc())) {
                    List<LookUp> sorDCategory = sordCategory.parallelStream()
                            .filter(clList -> clList != null
                                    && clList.getDescLangFirst().equalsIgnoreCase(detDto.getSordCategoryDesc()))
                            .collect(Collectors.toList());
                    if (sorDCategory != null && !sorDCategory.isEmpty())
                        detDto.setSordCategory(sorDCategory.get(0).getLookUpId());
                }
                if (StringUtils.isNotEmpty(detDto.getSorIteamUnitDesc())) {
                    List<LookUp> sorUnit = sorIteamUnit.parallelStream()
                            .filter(clList -> clList != null
                                    && clList.getDescLangFirst().equalsIgnoreCase(detDto.getSorIteamUnitDesc()))
                            .collect(Collectors.toList());
                    if (sorUnit != null && !sorUnit.isEmpty())
                        detDto.setSorIteamUnit(sorUnit.get(0).getLookUpId());
                }
                if (StringUtils.isNotEmpty(detDto.getLeadUnitDesc())) {
                    List<LookUp> sorUnit = sorIteamUnit.parallelStream()
                            .filter(clList -> clList != null
                                    && clList.getDescLangFirst().equalsIgnoreCase(detDto.getLeadUnitDesc()))
                            .collect(Collectors.toList());
                    if (sorUnit != null && !sorUnit.isEmpty())
                        detDto.setLeadUnit(sorUnit.get(0).getLookUpId());
                }

                String cateDescError = null;
                if (detDto.getSordCategoryDesc() == null || detDto.getSordCategory() == null
                        || detDto.getSordCategory() == -1) {
                    cateDescError = getApplicationSession().getMessage("sor.invalid.categDesc");
                    countError++;
                } else {
                    cateDescError = detDto.getSordCategoryDesc();
                }
                String itemUnitDescError = null;
                if (detDto.getSorIteamUnitDesc() == null || detDto.getSorIteamUnit() == null
                        || detDto.getSorIteamUnit() == -1) {
                    countError++;
                    itemUnitDescError = getApplicationSession().getMessage("sor.invalid.unitDesc");
                } else {
                    itemUnitDescError = detDto.getSorIteamUnitDesc();
                }
                String leadDescError = null;
                if (detDto.getLeadUnitDesc() != null) {
                    if (detDto.getLeadUnit() == null || detDto.getLeadUnit() == -1) {
                        countError++;
                        leadDescError = getApplicationSession().getMessage("sor.invalid.leadUnitDesc");
                    } else {
                        leadDescError = detDto.getLeadUnitDesc();
                    }

                }
                String leadUnitError = null;
                if (detDto.getLeadUpto() != null) {
                    if (detDto.getLeadUnitDesc() == null || detDto.getLeadUnit() == null
                            || detDto.getLeadUnit() == -1) {
                        countError++;
                        leadUnitError = getApplicationSession().getMessage("sor.empty.leadUnit");
                    } else {
                        leadUnitError = detDto.getLeadUnitDesc();
                    }

                }
                String itemNoError = null;
                if (detDto.getSorDIteamNo() == null || detDto.getSorDIteamNo().isEmpty()) {
                    countError++;
                    itemNoError = getApplicationSession().getMessage("sor.itemNo.empty");
                } else {
                    itemNoError = detDto.getSorDIteamNo();
                }

                String sorDescError = null;
                if (detDto.getSorDDescription() == null || detDto.getSorDDescription().isEmpty()) {
                    countError++;
                    sorDescError = getApplicationSession().getMessage("sor.desc.empty");
                } else if (detDto.getSorDDescription().length() > 4000) {
                    countError++;
                    sorDescError = getApplicationSession().getMessage("sor.desc.length");
                } else {
                    sorDescError = detDto.getSorDDescription();
                }

                // negative base rate validation
                String baseRateError = null;
                if (detDto.getSorBasicRate() == null) {
                    countError++;
                    baseRateError = getApplicationSession().getMessage("sor.baserate.empty");
                } else if (detDto.getSorBasicRate().compareTo(BigDecimal.ZERO) < 0) {
                    countError++;
                    baseRateError = getApplicationSession().getMessage("sor.invalid.basicAmt");
                } else {
                    baseRateError = detDto.getSorBasicRate().toString();
                }

                // negative labor rate validation
                String laborRateError = null;
                if (detDto.getSorLabourRate() != null && (detDto.getSorLabourRate().compareTo(BigDecimal.ZERO) < 0)) {
                    countError++;
                    laborRateError = getApplicationSession().getMessage("sor.invalid.lbrAmt");
                } else if (detDto.getSorLabourRate() != null) {
                    laborRateError = detDto.getSorLabourRate().toString();
                }

                // negative lead value validation
                String leadError = null;
                if (detDto.getLeadUpto() != null && (detDto.getLeadUpto().compareTo(BigDecimal.ZERO) < 0)) {
                    countError++;
                    leadError = getApplicationSession().getMessage("sor.invalid.ledVal");
                } else if (detDto.getLeadUpto() != null) {
                    leadError = detDto.getLeadUpto().toString();
                }

                // negative lift value validation
                String liftError = null;
                if (detDto.getLiftUpto() != null && (detDto.getLiftUpto().compareTo(BigDecimal.ZERO) < 0)) {
                    countError++;
                    liftError = getApplicationSession().getMessage("sor.invalid.liftVal");
                } else if (detDto.getLiftUpto() != null) {
                    liftError = detDto.getLiftUpto().toString();
                }

                if (countError > 0) {
                    errDetails.setErrFlag(MainetConstants.WorksManagement.SOR_TYPE);
                    errDetails.setErrLabel1(sorName);
                    errDetails.setErrLabel2(model.getMstDto().getFromDate());
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                    errDetails.setErrData(cateDescError + MainetConstants.WorksManagement.OR
                            + detDto.getSordSubCategory() + MainetConstants.WorksManagement.OR + itemNoError
                            + MainetConstants.WorksManagement.OR + sorDescError + MainetConstants.WorksManagement.OR
                            + itemUnitDescError + MainetConstants.WorksManagement.OR + baseRateError
                            + MainetConstants.WorksManagement.OR + laborRateError + MainetConstants.WorksManagement.OR
                            + leadError + MainetConstants.WorksManagement.OR + leadDescError
                            + MainetConstants.WorksManagement.OR + leadUnitError + MainetConstants.WorksManagement.OR
                            + liftError);
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(empId);
                    errDetails.setCreatedDate(newDate);
                    errDetails.setLgIpMac(macAddress);
                    errorDetails.add(errDetails);
                }

            });

            if (errorDetails.isEmpty()) {
                Set<ScheduleOfRateDetDto> errorDtos = dtos.stream().filter(dto -> Collections.frequency(dtos, dto) > 1)
                        .collect(Collectors.toSet());
                if (!errorDtos.isEmpty()) {

                    errorDtos.forEach(errorDto -> {
                        WmsErrorDetails dupliCombError = new WmsErrorDetails();
                        dupliCombError.setErrFlag(MainetConstants.WorksManagement.SOR_TYPE);
                        dupliCombError.setErrLabel1(sorName);
                        dupliCombError.setErrLabel2(model.getMstDto().getFromDate());
                        dupliCombError.setFileName(model.getExcelFileName());
                        dupliCombError.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                        dupliCombError.setErrData(
                                MainetConstants.WorksManagement.DUPLICATE_COMBINATION + errorDto.getSordCategoryDesc()
                                        + MainetConstants.SEPARATOR + errorDto.getSorDIteamNo());
                        dupliCombError.setOrgId(orgId);
                        dupliCombError.setCreatedBy(empId);
                        dupliCombError.setCreatedDate(newDate);
                        dupliCombError.setLgIpMac(macAddress);
                        errorDetails.add(dupliCombError);
                    });
                }
            }

            if (!errorDetails.isEmpty()) {
                errorList.addAll(errlist);
                model.addValidationError("Error While Uploading Excel: " + model.getExcelFileName()
                        + ": For More Details Check Error Log.");
                materialMasterService.saveAndDeleteErrorDetails(errorDetails,
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.WorksManagement.SOR_TYPE);
                model.setErrDetails(errorDetails);
                getModel().setSuccessFlag(MainetConstants.MASTER.E);

            } else {
                model.getMstDto().setDetDto(dtos);
                model.saveForm();
                model.setErrDetails(new ArrayList<WmsErrorDetails>());
                materialMasterService.saveAndDeleteErrorDetails(new ArrayList<WmsErrorDetails>(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.WorksManagement.SOR_TYPE);
                getModel().setSuccessFlag(MainetConstants.MASTER.Y);
            }
        } catch (Exception ex) {
            throw new FrameworkException(SOR_IMPORT_EXCEPTION + ex.getMessage());
        }
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setModeType(MainetConstants.WorksManagement.UPLOAD);

        if (model.getErrDetails().isEmpty()) {
            populateModel(model, this.getModel().getSorId(), "V");
            return new ModelAndView(MainetConstants.ScheduleOfRate.SOR_FORM, MainetConstants.FORM_NAME, model);
        }
        return defaultMyResult();
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

    @ResponseBody
    @RequestMapping(params = MainetConstants.ScheduleOfRate.VALIDATE_SOR_DATE, method = RequestMethod.POST)
    public boolean validateSorStartDate(final HttpServletRequest request,
            @RequestParam(MainetConstants.ScheduleOfRate.SOR_CPD_ID) Long sorCpdId,
            @RequestParam(MainetConstants.ScheduleOfRate.SOR_FROM_DATE) Date sorFromDate) {
        return this.getModel().checkDateValidation(sorFromDate, sorCpdId);

    }

    @RequestMapping(params = MainetConstants.WorksManagement.SOR_CHAPTERWISE, method = RequestMethod.POST)
    public ModelAndView sorFormChapterWise(
            @RequestParam(value = MainetConstants.ScheduleOfRate.SOR_ID, required = false) Long sorId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            @RequestParam(value = MainetConstants.WorksManagement.CHAPTERID, required = false) Long chapterId) {
        ScheduleOfRateModel sorModel = this.getModel();
        sorModel.setChapterId(chapterId);
        populateModel(sorModel, sorId, type);
        List<ScheduleOfRateDetDto> detDto = new ArrayList<>();
        for (ScheduleOfRateDetDto scheduleOfRateDetDto : sorModel.getMstDto().getDetDto()) {
            if (scheduleOfRateDetDto.getSordCategory().longValue() == chapterId.longValue()) {
                detDto.add(scheduleOfRateDetDto);
            }
        }
        sorModel.getMstDto().setDetDto(detDto);
        return new ModelAndView(MainetConstants.ScheduleOfRate.SOR_FORM, MainetConstants.FORM_NAME, sorModel);
    }

}
