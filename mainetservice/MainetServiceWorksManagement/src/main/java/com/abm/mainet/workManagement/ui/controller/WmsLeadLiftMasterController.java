package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.workManagement.dto.ReadExcelData;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;
import com.abm.mainet.workManagement.dto.WriteExcelData;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WmsLeadLiftMasterService;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.ui.model.WmsLeadLiftMasterModel;

@Controller
@RequestMapping("/WmsLeadLiftMaster.html")
public class WmsLeadLiftMasterController extends AbstractFormController<WmsLeadLiftMasterModel> {

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Autowired
    private WmsLeadLiftMasterService wmsLeadLiftMasterService;

    @Autowired
    private WmsMaterialMasterService materialMasterService;

    @Autowired
    IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("WmsLeadLiftMaster.html");
        this.getModel().setWmsLeadLiftMasterDtos(
                wmsLeadLiftMasterService.searchLeadLiftDetails(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setScheduleOfRateMstDtoList(scheduleOfRateService
                .getAllActiveScheduleRateMstList(UserSession.getCurrent().getOrganisation().getOrgid()));

        return defaultResult();
    }

    @RequestMapping(params = MainetConstants.GET_SOR_DATE, method = RequestMethod.POST)
    public @ResponseBody String getSorDates(
            @RequestParam(name = MainetConstants.ScheduleOfRate.SOR_ID, required = false) Long sorId) {
        WmsLeadLiftMasterModel masModel = this.getModel();
        ScheduleOfRateMstDto scheduleOfRateMstDto = scheduleOfRateService.findSORMasterWithDetailsBySorId(sorId);
        masModel.getWmsLeadLiftMasterDto().setSorToDate(scheduleOfRateMstDto.getSorToDate());
        masModel.getWmsLeadLiftMasterDto().setSorFromDate(scheduleOfRateMstDto.getSorFromDate());
        return "" + scheduleOfRateMstDto.getSorFromDate() + MainetConstants.operator.COMMA + MainetConstants.BLANK
                + scheduleOfRateMstDto.getSorToDate();

    }

    @RequestMapping(params = MainetConstants.GRID_DATA, produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<WmsLeadLiftMasterDto> leadLiftMasterGridData(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {

        return getModel().paginate(httpServletRequest, page, rows, this.getModel().getWmsLeadLiftMasterDtos());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.ADD_LEAD_LIFT)
    public ModelAndView leadLiftMasterCreate(
            @RequestParam(name = MainetConstants.LELI_FLAG, required = false) String leLiFlag,
            @RequestParam(name = MainetConstants.ScheduleOfRate.SOR_ID, required = false) Long sorId,
            final HttpServletRequest request) {

        String mode = MainetConstants.MODE_CREATE;
        this.getModel().prepareDto(sorId, mode, leLiFlag);
        this.getModel().setLookUpList(CommonMasterUtility.getListLookup(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        return new ModelAndView(MainetConstants.ADD_LEAD_LIFT, MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.SEARCH_DATA, method = RequestMethod.POST)
    public String searchData(@RequestParam(name = MainetConstants.LELI_FLAG, required = false) String leLiFlag,
            @RequestParam(name = MainetConstants.ScheduleOfRate.SOR_ID, required = false) Long sorId,
            final HttpServletRequest request) {
        this.getModel().getWmsLeadLiftMasterDtos().clear();
        String flagGridData = MainetConstants.MENU.N;
        List<WmsLeadLiftMasterDto> masDtos = wmsLeadLiftMasterService.toCheckLeadLiftEntry(sorId, leLiFlag,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (masDtos != null && !masDtos.isEmpty()) {
            this.getModel().setWmsLeadLiftMasterDtos(masDtos);
            flagGridData = MainetConstants.MENU.Y;
        }

        return flagGridData;

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.EDIT_LEAD_LIFT_DATA, method = RequestMethod.POST)
    public ModelAndView editLeadLiftMasterData(@RequestParam(MainetConstants.ScheduleOfRate.SOR_ID) final Long sorId,
            @RequestParam(MainetConstants.LELI_FLAG) final String leLiFlag,
            @RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) final String mode) {

        String flag;
        this.getModel().setFormMode(mode);
        if (!mode.equals(MainetConstants.WorksManagement.UPLOAD)) {
            if (leLiFlag.equals(MainetConstants.LEAD)) {
                flag = MainetConstants.FlagL;
            } else {
                flag = MainetConstants.FlagF;
            }
            this.getModel().prepareDto(sorId, mode, flag);
            List<WmsLeadLiftMasterDto> masDtos = wmsLeadLiftMasterService.editLeadLiftData(sorId, flag,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (masDtos.get(0).getLeLiSlabFlg().equals(MainetConstants.Y_FLAG)) {
                this.getModel().setActiveChkBox(true);
                this.getModel().getWmsLeadLiftMasterDto().setLeLiSlabFlg(MainetConstants.Y_FLAG);
            } else {
                this.getModel().setActiveChkBox(false);
                this.getModel().getWmsLeadLiftMasterDto().setLeLiSlabFlg(MainetConstants.N_FLAG);
            }
            this.getModel().setLookUpList(CommonMasterUtility.getListLookup(MainetConstants.ScheduleOfRate.WUT,
                    UserSession.getCurrent().getOrganisation()));
            if (!masDtos.isEmpty()) {
                this.getModel().setWmsleadLiftTableDtos(masDtos);
            }

        } else {
            this.getModel().prepareDto(sorId, mode, leLiFlag);
            this.getModel().setErrDetails(materialMasterService.getErrorLog(
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.WorksManagement.LE));
        }
        return new ModelAndView(MainetConstants.ADD_LEAD_LIFT, MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.DELETE_LEAD_LIFT_DATA, method = RequestMethod.POST)
    public boolean deleteProjectMasterData(@RequestParam(MainetConstants.ScheduleOfRate.SOR_ID) final Long sorId,
            @RequestParam(MainetConstants.LELI_FLAG) final String leLiFlag) {
        String flag;
        if (leLiFlag.equals(MainetConstants.LEAD)) {
            flag = MainetConstants.FlagL;
        } else {
            flag = MainetConstants.FlagF;
        }
        wmsLeadLiftMasterService.inactiveLeadLiftMaster(sorId, flag, UserSession.getCurrent().getEmployee().getEmpId());

        for (WmsLeadLiftMasterDto wmsLeadLiftMasterDto : this.getModel().getWmsLeadLiftMasterDtos()) {
            if (wmsLeadLiftMasterDto.getSorId().longValue() == sorId.longValue()) {
                this.getModel().getWmsLeadLiftMasterDtos().remove(wmsLeadLiftMasterDto);
                break;
            }
        }
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public ModelAndView validateLoadExcelData(final HttpServletRequest request) {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        WmsLeadLiftMasterModel model = this.getModel();
        model.bind(request);
        String leLiFlag = model.getWmsLeadLiftMasterDto().getLeLiFlag();
        Long sorId = model.getWmsLeadLiftMasterDto().getSorId();
        String filePath = null;
        int rowCount = 1;
        List<String> errorList = new ArrayList<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                filePath = file.toString();
                break;
            }
        }

        try {

            List<WmsErrorDetails> errorDetails = new ArrayList<>();
            ReadExcelData data = new ReadExcelData<>(filePath, WmsLeadLiftMasterDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();

            if (!errlist.isEmpty()) {
                for (String string : errlist) {
                    WmsErrorDetails errDetails = new WmsErrorDetails();
                    errDetails.setErrFlag(MainetConstants.WorksManagement.LE);
                    errDetails.setErrLabel1(model.getWmsLeadLiftMasterDto().getSorName());
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.DATA_TYPE);
                    errDetails.setErrData(string);
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                }
            }

            List<WmsLeadLiftMasterDto> dtos = data.getParseData();
            for (WmsLeadLiftMasterDto wmsDto : dtos) {

                WmsErrorDetails errDetails = new WmsErrorDetails();
                int countError = 0;
                if (model.isActiveChkBox()) {
                    wmsDto.setLeLiSlabFlg(MainetConstants.Y_FLAG);
                } else {
                    wmsDto.setLeLiSlabFlg(MainetConstants.N_FLAG);
                }

                wmsDto.setLeLiUnit(CommonMasterUtility.getIdFromPrefixLookUpDesc(wmsDto.getUnitName(),
                        MainetConstants.WorksManagement.WUT,
                        (int) UserSession.getCurrent().getOrganisation().getOrgid()));
                if (wmsDto.getLeLiUnit() != -1)
                    wmsDto.setUnitName(
                            CommonMasterUtility.getCPDDescription(wmsDto.getLeLiUnit().longValue(), MainetConstants.MENU.E));
                if (wmsDto.getLeLiUnit() == -1 || wmsDto.getLeLiUnit() == null)
                    countError++;
                if (wmsDto.getLeLiTo() == null)
                    countError++;
                if (model.isActiveChkBox()) {
                    if (wmsDto.getLeLiFrom() == null)
                        countError++;
                }
                if (wmsDto.getLeLiRate() == null || wmsDto.getLeLiRate().toString().isEmpty())
                    countError++;
                if (wmsDto.getLeLiRate().signum() == -1) {
                    countError++;
                }
                if (countError > 0) {
                    errDetails.setErrFlag(MainetConstants.WorksManagement.LE);
                    errDetails.setErrLabel1(model.getWmsLeadLiftMasterDto().getSorName());
                    errDetails.setErrLabel2(wmsDto.getLeLiFlag());
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK
                            + MainetConstants.WorksManagement.FOR_ENTRY + rowCount);
                    errDetails.setErrData(wmsDto.getLeLiFrom() + MainetConstants.WorksManagement.OR + wmsDto.getLeLiTo()
                            + MainetConstants.WorksManagement.OR
                            + wmsDto.getLeLiRate() + MainetConstants.WorksManagement.OR + wmsDto.getUnitName());

                    if (wmsDto.getLeLiRate().signum() == -1) {
                        errDetails.setErrDescription(MainetConstants.WorksManagement.RATE_NEGATIVE_CHECK);
                    }
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                    continue;
                }
                String status = wmsLeadLiftMasterService.checkDuplicateExcelData(wmsDto, orgId, leLiFlag, sorId);
                if (!status.equals(MainetConstants.IsDeleted.DELETE)) {
                    errDetails.setErrFlag(MainetConstants.WorksManagement.LE);
                    errDetails.setErrLabel1(model.getWmsLeadLiftMasterDto().getSorName());
                    errDetails.setErrLabel2(wmsDto.getLeLiFlag());
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.DUPLICATE_DATA
                            + MainetConstants.WorksManagement.FOR_ENTRY + rowCount);
                    errDetails.setErrData(wmsDto.getLeLiFrom() + MainetConstants.WorksManagement.OR + wmsDto.getLeLiTo()
                            + MainetConstants.WorksManagement.OR
                            + wmsDto.getLeLiRate() + MainetConstants.WorksManagement.OR + wmsDto.getUnitName());
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                }
                rowCount++;
            }

            this.getModel().setLookUpList(CommonMasterUtility.getListLookup(MainetConstants.ScheduleOfRate.WUT,
                    UserSession.getCurrent().getOrganisation()));
            model.setWmsleadLiftTableDtos(dtos);
            if (errorDetails.isEmpty() && !model.saveForm()) {
                model.getBindingResult();
                model.getWmsleadLiftTableDtos().clear();
                List<WmsLeadLiftMasterDto> errDtos = model.getErrorDtos();
                for (WmsLeadLiftMasterDto dto : errDtos) {
                    WmsErrorDetails errDetails = new WmsErrorDetails();
                    errDetails.setErrFlag(MainetConstants.WorksManagement.LE);
                    errDetails.setErrLabel1(model.getWmsLeadLiftMasterDto().getSorName());
                    errDetails.setErrLabel2(dto.getLeLiFlag());
                    errDetails.setFileName(model.getExcelFileName());
                    errDetails.setErrDescription(dto.getErrMessage());
                    errDetails.setErrData(dto.getLeLiFrom() + MainetConstants.WorksManagement.OR + dto.getLeLiTo()
                            + MainetConstants.WorksManagement.OR + dto.getLeLiRate()
                            + MainetConstants.WorksManagement.OR + dto.getUnitName());
                    errDetails.setOrgId(orgId);
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                }
            }
            if (!errorDetails.isEmpty()) {
                errorList.addAll(errlist);
                model.addValidationError(
                        MainetConstants.ERROR_UPLOAD_EXL + model.getExcelFileName() + MainetConstants.CHECK_ERROR_LOG);
                materialMasterService.saveAndDeleteErrorDetails(errorDetails, orgId,
                        MainetConstants.WorksManagement.LE);
                model.setErrDetails(errorDetails);

            } else {
                model.setErrDetails(new ArrayList<WmsErrorDetails>());
                materialMasterService.saveAndDeleteErrorDetails(new ArrayList<WmsErrorDetails>(), orgId,
                        MainetConstants.WorksManagement.LE);
            }
        } catch (

        Exception ex) {
            throw new FrameworkException(MainetConstants.WorksManagement.LELI_IMPORT_EXCEPTION + ex.getMessage());
        }
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setFormMode(MainetConstants.WorksManagement.UPLOAD);
        return defaultMyResult();

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = MainetConstants.WorksManagement.EXPORT_EXEL, method = { RequestMethod.GET })
    public void exportExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        WmsLeadLiftMasterModel model = this.getModel();
        model.bind(request);
        Long sorId = model.getWmsLeadLiftMasterDto().getSorId();
        String leLiFlag = model.getWmsLeadLiftMasterDto().getLeLiFlag();
        model.setWmsLeadLiftMasterDtos(wmsLeadLiftMasterService.editLeadLiftData(sorId, leLiFlag, orgId));
        for (WmsLeadLiftMasterDto listDo : this.getModel().getWmsLeadLiftMasterDtos()) {
            listDo.setUnitName(CommonMasterUtility.getCPDDescription(listDo.getLeLiUnit(), MainetConstants.MENU.E));
        }
        try {
            WriteExcelData data = new WriteExcelData(
                    model.getWmsLeadLiftMasterDto().getSorName() + MainetConstants.operator.UNDER_SCORE
                            + model.getWmsLeadLiftMasterDto().getLeLiFlag() + MainetConstants.operator.UNDER_SCORE
                            + MainetConstants.WorksManagement.LELI_ENTRY + MainetConstants.XLSX_EXT,
                    request, response);
            data.getExpotedExcelSheet(this.getModel().getWmsLeadLiftMasterDtos(), WmsLeadLiftMasterDto.class);

        } catch (Exception ex) {
            throw new FrameworkException(MainetConstants.WorksManagement.LELI_EXPORT_EXCEPTION + ex.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LELIREPORT, method = RequestMethod.POST)
    public ModelAndView leadLiftReportData(final HttpServletRequest request) {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        WmsLeadLiftMasterModel model = this.getModel();
        model.bind(request);
        Long sorId = model.getWmsLeadLiftMasterDto().getSorId();
        String leLiFlag = model.getWmsLeadLiftMasterDto().getLeLiFlag();
        model.setFromDate((UtilityService.convertDateToDDMMYYYY(model.getWmsLeadLiftMasterDto().getSorFromDate())));
        if (model.getWmsLeadLiftMasterDto().getSorToDate() != null)
            model.setToDate((UtilityService.convertDateToDDMMYYYY(model.getWmsLeadLiftMasterDto().getSorToDate())));
        List<WmsLeadLiftMasterDto> masDtos = wmsLeadLiftMasterService.editLeadLiftData(sorId, leLiFlag, orgId);
        if (masDtos != null && !masDtos.isEmpty()) {
            for (WmsLeadLiftMasterDto wmsLeadLiftMasterDto : masDtos) {
                wmsLeadLiftMasterDto.setUnitName(CommonMasterUtility
                        .getCPDDescription(wmsLeadLiftMasterDto.getLeLiUnit().longValue(), MainetConstants.MENU.E));
            }
        }
        this.getModel().setWmsleadLiftTableDtos(masDtos);
        if (leLiFlag.equals(MainetConstants.FlagL)) {
            model.setRateType(MainetConstants.LEAD);
        } else {
            model.setRateType(MainetConstants.LIFT);
        }
        return new ModelAndView(MainetConstants.WorksManagement.LELIREPORT, MainetConstants.FORM_NAME, this.getModel());
    }

}
