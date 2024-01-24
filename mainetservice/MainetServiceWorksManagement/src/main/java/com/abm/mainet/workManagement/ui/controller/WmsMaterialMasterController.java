package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
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
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.dto.WriteExcelData;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.ui.model.WmsMaterialMasterModel;

@Controller
@RequestMapping("/WmsMaterialMaster.html")
public class WmsMaterialMasterController extends AbstractFormController<WmsMaterialMasterModel> {

    @Resource
    WmsMaterialMasterService materialMasterService;

    @Resource
    ScheduleOfRateService scheduleOfRateService;

    @Autowired
    IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("WmsMaterialMaster.html");
        this.getModel().setScheduleOfRateListDto(materialMasterService
                .findAllActiveMaterialBySorMas(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setActiveScheduleRateList(scheduleOfRateService
                .getAllActiveScheduleRateMstList(UserSession.getCurrent().getOrganisation().getOrgid()));

        return index();
    }

    @RequestMapping(params = MainetConstants.WorksManagement.MATERIAL_MASTER, produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<ScheduleOfRateMstDto> projectMasterGridData(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        return getModel().paginate(httpServletRequest, page, rows, this.getModel().getScheduleOfRateListDto());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_MATERIAL_MASTER)
    public ModelAndView editProjectMasterData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setModeType(MainetConstants.WorksManagement.ADD);
        return new ModelAndView(MainetConstants.WorksManagement.ADD_MATERIAL_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_SORNAMES, method = RequestMethod.POST)
    public @ResponseBody List<Object[]> getSorNames(
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID, required = false) Long sorId) {
        return scheduleOfRateService.findAllSorNamesByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_DATE_BY_SORNAME, method = RequestMethod.POST)
    public @ResponseBody String getDateBySorName(
            @RequestParam(name = MainetConstants.WorksManagement.SOR_NAME, required = false) Long sorName) {
        ScheduleOfRateMstDto mstDto = scheduleOfRateService.findSORMasterWithDetailsBySorId(sorName);
        WmsMaterialMasterModel model = this.getModel();
        model.setFromDate(UtilityService.convertDateToDDMMYYYY(mstDto.getSorFromDate()));
        if (mstDto.getSorToDate() != null)
            model.setToDate(UtilityService.convertDateToDDMMYYYY(mstDto.getSorToDate()));
        return model.getFromDate() + MainetConstants.operator.COMMA + model.getToDate();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_MATERIAL_MASTER_GRID_DATA, method = RequestMethod.POST)
    public String getMaterialMasterList(@RequestParam(name = MainetConstants.WorksManagement.SOR_NAME) Long sorName,
            @RequestParam(name = MainetConstants.WorksManagement.SORT_TYPE, required = false) Long sorType,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_NAMESTR) String sorNameStr) {

        WmsMaterialMasterModel model = this.getModel();
        model.setSorName(sorNameStr);
        model.setSorType(sorType);
        this.getModel().getScheduleOfRateMstDto().setSorId(sorName);
        String flagGridData = MainetConstants.IsDeleted.NOT_DELETE;
        this.getModel().getScheduleOfRateListDto().clear();
        List<WmsMaterialMasterDto> dto = materialMasterService.getMaterialListBySorId(sorName);

        if (!dto.isEmpty()) {
            flagGridData = MainetConstants.IsDeleted.DELETE;
            this.getModel().getScheduleOfRateListDto()
                    .add(scheduleOfRateService.findSORMasterWithDetailsBySorId(sorName));
        }
        return flagGridData;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.EDIT_MATERIAL_MASTER_DATA, method = RequestMethod.POST)
    public ModelAndView editProjectMasterData(@RequestParam(MainetConstants.WorksManagement.SOR_ID) final Long sorId,
            @RequestParam(MainetConstants.WorksManagement.MODE) final String mode) {

        WmsMaterialMasterModel model = this.getModel();
        model.setModeType(mode);
        if (!mode.equals(MainetConstants.WorksManagement.UPLOAD)) {
            model.getScheduleOfRateMstDto().setSorId(sorId);
            model.setMaterialMasterListDto(materialMasterService.getMaterialListBySorId(sorId));

            ScheduleOfRateMstDto mstDto = scheduleOfRateService.findSORMasterWithDetailsBySorId(sorId);

            model.setSorName(mstDto.getSorName());
            model.setFromDate(UtilityService.convertDateToDDMMYYYY(mstDto.getSorFromDate()));
            if (mstDto.getSorToDate() != null)
                model.setToDate(UtilityService.convertDateToDDMMYYYY(mstDto.getSorToDate()));
        } else {
            model.setErrDetails(materialMasterService.getErrorLog(UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.WorksManagement.RATE_TYPE));
        }
        return new ModelAndView(MainetConstants.WorksManagement.ADD_MATERIAL_MASTER, MainetConstants.FORM_NAME, model);

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.DELETE_MATERIAL_MASTER, method = RequestMethod.POST)
    public void deleteProjectMasterData(@RequestParam(MainetConstants.WorksManagement.SOR_ID) final Long sorId) {

        materialMasterService.deleteMaterialById(sorId);
        for (ScheduleOfRateMstDto scheduleOfRateMstDto : this.getModel().getScheduleOfRateListDto()) {
            if (scheduleOfRateMstDto.getSorId().longValue() == sorId.longValue()) {
                this.getModel().getScheduleOfRateListDto().remove(scheduleOfRateMstDto);
                break;
            }
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = MainetConstants.WorksManagement.EXPORT_EXCEL, method = { RequestMethod.GET })
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {

        this.getModel().setMaterialMasterListDto(
                materialMasterService.getMaterialListBySorId(this.getModel().getScheduleOfRateMstDto().getSorId()));

        for (WmsMaterialMasterDto listDo : this.getModel().getMaterialMasterListDto()) {
            listDo.setRateType(
                    CommonMasterUtility.getCPDDescription(listDo.getMaTypeId().longValue(), MainetConstants.MODE_EDIT));
            listDo.setUnitName(
                    CommonMasterUtility.getCPDDescription(listDo.getMaItemUnit().longValue(), MainetConstants.MODE_EDIT));
        }
        try {
            WriteExcelData data = new WriteExcelData(MainetConstants.WorksManagement.GEN_RATETYPE
                    + this.getModel().getSorName() + MainetConstants.XLSX_EXT, request, response);
            data.getExpotedExcelSheet(this.getModel().getMaterialMasterListDto(), WmsMaterialMasterDto.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {

        WmsMaterialMasterModel model = this.getModel();
        this.getModel().bind(request);
        String filePath = null;
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
            ReadExcelData data = new ReadExcelData<>(filePath, WmsMaterialMasterDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();

            if (!errlist.isEmpty()) {
                for (String string : errlist) {
                    WmsErrorDetails errDetails = new WmsErrorDetails();
                    errDetails.setErrFlag(MainetConstants.WorksManagement.RATE_TYPE);
                    errDetails.setErrLabel1(model.getSorName());
                    errDetails.setFileName(model.getExcelFilePath());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.DATA_TYPE);
                    errDetails.setErrData(string);
                    errDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                }
            }

            List<WmsMaterialMasterDto> dtos = data.getParseData();
            for (WmsMaterialMasterDto wmsDto : dtos) {

                WmsErrorDetails errDetails = new WmsErrorDetails();
                int countError = 0;
                wmsDto.setMaTypeId(CommonMasterUtility.getIdFromPrefixLookUpDesc(wmsDto.getRateType(),
                        MainetConstants.WorksManagement.MTY,
                        (int) UserSession.getCurrent().getOrganisation().getOrgid()));
                wmsDto.setMaItemUnit(CommonMasterUtility.getIdFromPrefixLookUpDesc(wmsDto.getUnitName(),
                        MainetConstants.WorksManagement.WUT,
                        (int) UserSession.getCurrent().getOrganisation().getOrgid()));
                if (wmsDto.getMaTypeId() == -1 || wmsDto.getMaTypeId() == null)
                    countError++;
                if (wmsDto.getMaItemUnit() == -1 || wmsDto.getMaItemUnit() == null)
                    countError++;
                if (wmsDto.getMaRate() == null)
                    countError++;
                else if (wmsDto.getMaRate().signum() == -1) {
                    countError++;
                }

                if (wmsDto.getMaDescription() == null || wmsDto.getMaDescription().isEmpty())
                    countError++;
                if (wmsDto.getMaItemNo() == null || wmsDto.getMaItemNo().isEmpty())
                    countError++;
                if (countError > 0) {
                    errDetails.setErrFlag(MainetConstants.WorksManagement.RATE_TYPE);
                    errDetails.setErrLabel1(model.getSorName());
                    errDetails.setErrLabel2(wmsDto.getRateType());
                    errDetails.setFileName(model.getExcelFilePath());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                    errDetails.setErrData(wmsDto.getRateType() + MainetConstants.WorksManagement.OR
                            + wmsDto.getMaDescription() + MainetConstants.WorksManagement.OR + wmsDto.getMaItemNo()
                            + MainetConstants.WorksManagement.OR + wmsDto.getUnitName()
                            + MainetConstants.WorksManagement.OR + wmsDto.getMaRate());

                    if (wmsDto.getMaRate() == null) {
                        errDetails.setErrDescription(MainetConstants.WorksManagement.NULL_CHECK);
                    } else if (wmsDto.getMaRate().signum() == -1) {
                        errDetails.setErrDescription(MainetConstants.WorksManagement.RATE_NEGATIVE_CHECK);
                    }
                    errDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                    continue;
                }
                wmsDto.setSorId(model.getScheduleOfRateMstDto().getSorId());
                String status = materialMasterService.checkDuplicateExcelData(wmsDto,
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (status.equals(MainetConstants.IsDeleted.DELETE)) {
                    errDetails.setErrFlag(MainetConstants.WorksManagement.RATE_TYPE);
                    errDetails.setErrLabel1(model.getSorName());
                    errDetails.setErrLabel2(wmsDto.getRateType());
                    errDetails.setFileName(model.getExcelFilePath());
                    errDetails.setErrDescription(MainetConstants.WorksManagement.DUPLICATE_DATA);
                    errDetails.setErrData(wmsDto.getRateType() + MainetConstants.WorksManagement.OR
                            + wmsDto.getMaDescription() + MainetConstants.WorksManagement.OR + wmsDto.getMaItemNo()
                            + MainetConstants.WorksManagement.OR + wmsDto.getUnitName()
                            + MainetConstants.WorksManagement.OR + wmsDto.getMaRate());
                    errDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                    errDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    errDetails.setCreatedDate(new Date());
                    errDetails.setLgIpMac(Utility.getMacAddress());
                    errorDetails.add(errDetails);
                }
            }
            if (!errorDetails.isEmpty()) {
                errorList.addAll(errlist);
                model.addValidationError("Error While Uploading Excel: " + model.getExcelFilePath()
                        + ": For More Details Check Error Log.");
                materialMasterService.saveAndDeleteErrorDetails(errorDetails,
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.WorksManagement.RATE_TYPE);
                model.setErrDetails(errorDetails);

            } else {
                model.setMaterialMasterListDto(dtos);
                model.saveForm();
                model.setErrDetails(new ArrayList<WmsErrorDetails>());
                materialMasterService.saveAndDeleteErrorDetails(new ArrayList<WmsErrorDetails>(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.WorksManagement.RATE_TYPE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setModeType(MainetConstants.WorksManagement.UPLOAD);
        return defaultMyResult();

    }

}
