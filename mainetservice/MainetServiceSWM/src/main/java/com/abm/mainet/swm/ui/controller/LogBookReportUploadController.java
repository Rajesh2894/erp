package com.abm.mainet.swm.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.domain.AllFineLogBookDetails;
import com.abm.mainet.swm.domain.AnimalLogDetails;
import com.abm.mainet.swm.domain.CdWasteLogBookDetails;
import com.abm.mainet.swm.domain.ConstrutionNDemolition;
import com.abm.mainet.swm.domain.GardenBWGLogBook;
import com.abm.mainet.swm.domain.ItcBasedDoorToDoorCollection;
import com.abm.mainet.swm.domain.ItcBasedMonitoringforSweeping;
import com.abm.mainet.swm.domain.LogBookDesludgingDetail;
import com.abm.mainet.swm.domain.LogBookSweepingDetails;
import com.abm.mainet.swm.domain.LogBookUserChargesDetail;
import com.abm.mainet.swm.domain.Slrm1Details;
import com.abm.mainet.swm.domain.Slrm2Details;
import com.abm.mainet.swm.domain.VehicleLogBook;
import com.abm.mainet.swm.domain.VehicleLogMain;
import com.abm.mainet.swm.domain.VehicleLogMain2;
import com.abm.mainet.swm.dto.WriteExcelData;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.abm.mainet.swm.ui.model.LogBookReportUploadModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/LogBookReportUploadByExcel.html")
public class LogBookReportUploadController extends AbstractFormController<LogBookReportUploadModel> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ILogBookReportService iLogBookReportService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("LogBookReportUploadByExcel.html");
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        return index();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "exportLogBookExcel", method = { RequestMethod.GET })
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request,
            @RequestParam("logBookCode") String logBookCode) {
        List<ItcBasedDoorToDoorCollection> dtdcdtos = new ArrayList<>();
        List<ItcBasedMonitoringforSweeping> sweepingdtos = new ArrayList<>();
        List<LogBookSweepingDetails> sweepingLogBook = new ArrayList<>();
        List<ConstrutionNDemolition> cdwasteDto = new ArrayList<>();
        List<GardenBWGLogBook> gardendtos = new ArrayList<>();
        List<AnimalLogDetails> animaldtos = new ArrayList<>();
        List<VehicleLogMain> vehicleLogMain = new ArrayList<>();
        List<VehicleLogBook> vehicleLogBook = new ArrayList<>();
        List<CdWasteLogBookDetails> cdWasteLogBook = new ArrayList<>();
        List<Slrm1Details> slrm1Details = new ArrayList<>();
        List<Slrm2Details> slrm2Details = new ArrayList<>();
        List<VehicleLogMain2> vehicleLogMain2 = new ArrayList<>();
        List<AllFineLogBookDetails> allFine = new ArrayList<>();
        List<LogBookDesludgingDetail> desludging = new ArrayList<>();
        List<LogBookUserChargesDetail> usercharges = new ArrayList<>();
        try {
            if (logBookCode.equalsIgnoreCase("DTDC")) {
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringForDoorToDoorCollection.xlsx", request,
                        response);
                data.getExpotedExcelSheet(dtdcdtos, ItcBasedDoorToDoorCollection.class);
            }
            if (logBookCode.equalsIgnoreCase("MFS")) {
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringForSweeping.xlsx", request, response);
                data.getExpotedExcelSheet(sweepingdtos, ItcBasedMonitoringforSweeping.class);
            }
            if (logBookCode.equalsIgnoreCase("ICND")) {
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringCDWaste.xlsx", request, response);
                data.getExpotedExcelSheet(cdwasteDto, ConstrutionNDemolition.class);
            }
            if (logBookCode.equalsIgnoreCase("SWEP")) {
                WriteExcelData data = new WriteExcelData("SweepingTemplate.xlsx", request, response);
                data.getExpotedExcelSheet(sweepingLogBook, LogBookSweepingDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("GBWG")) {
                WriteExcelData data = new WriteExcelData("GardenBWGCompostLogBook.xlsx", request, response);
                data.getExpotedExcelSheet(gardendtos, GardenBWGLogBook.class);
            }
            if (logBookCode.equalsIgnoreCase("AML")) {
                WriteExcelData data = new WriteExcelData("AnimalManagementLog.xlsx", request, response);
                data.getExpotedExcelSheet(animaldtos, AnimalLogDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("VLM")) {
                WriteExcelData data = new WriteExcelData("VehicleLogbookMainpage.xlsx", request, response);
                data.getExpotedExcelSheet(vehicleLogMain, VehicleLogMain.class);
            }
            if (logBookCode.equalsIgnoreCase("VDP")) {
                WriteExcelData data = new WriteExcelData("VehicleDeploymentPlan.xlsx", request, response);
                data.getExpotedExcelSheet(vehicleLogBook, VehicleLogBook.class);
            }
            if (logBookCode.equalsIgnoreCase("CNDI")) {
                WriteExcelData data = new WriteExcelData("CnDWasteLogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(cdWasteLogBook, CdWasteLogBookDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("SLRM")) {
                WriteExcelData data = new WriteExcelData("SLRM1LogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(slrm1Details, Slrm1Details.class);
            }
            if (logBookCode.equalsIgnoreCase("SLRM2")) {
                WriteExcelData data = new WriteExcelData("SLRM2LogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(slrm2Details, Slrm2Details.class);
            }
            if (logBookCode.equalsIgnoreCase("VLMS")) {
                WriteExcelData data = new WriteExcelData("VehicleLogbookMainpage2.xlsx", request, response);
                data.getExpotedExcelSheet(vehicleLogMain2, VehicleLogMain2.class);
            }
            if (logBookCode.equalsIgnoreCase("FINE")) {
                WriteExcelData data = new WriteExcelData("AllFine.xlsx", request, response);
                data.getExpotedExcelSheet(allFine, AllFineLogBookDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("DESL")) {
                WriteExcelData data = new WriteExcelData("Desludging.xlsx", request, response);
                data.getExpotedExcelSheet(desludging, LogBookDesludgingDetail.class);
            }
            if (logBookCode.equalsIgnoreCase("UCC")) {
                WriteExcelData data = new WriteExcelData("UserChargeCollection.xlsx", request, response);
                data.getExpotedExcelSheet(usercharges, LogBookUserChargesDetail.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final LogBookReportUploadModel model = this.getModel();
        String fileName = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                fileName = file.getName();
                break;
            }
        }
        if (fileName == null) {
            model.addValidationError("Please Check Uploaded document");
        }
        if (!model.hasValidationErrors()) {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            }
        } else {
            return defaultMyResult();
        }
        return defaultMyResult();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(params = "getData", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String getData(@RequestParam("scheme") final String scheme,
            final HttpServletRequest request) {
        Class className = null;
        className = getModel().getClassName(scheme);
        if (className != null) {
            getModel().setList(iLogBookReportService.getData(className));

            if (getModel().getList() != null && getModel().getList().size() > 0) {
                return "Failure";
            }
            return "success";
        }
        return "null";
    }

    @RequestMapping(params = "getExcelDocument", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView chekker(@RequestParam("scheme") final String scheme, final HttpServletRequest request) {
        ModelAndView modelAndView = null;
        getModel().setAttachDocs(iLogBookReportService.getExcelDocument(scheme,
                com.abm.mainet.common.utility.UserSession.getCurrent().getOrganisation().getOrgid()));
        getModel().setDocName(getModel().getAttachDocs().getAttFname());
        getModel().setAttDate(getModel().getAttachDocs().getAttDate());
        modelAndView = new ModelAndView("UploadedSchemeDocument", MainetConstants.FORM_NAME, getModel());
        return modelAndView;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = MainetConstants.WorksManagement.EXPORT_EXEL, method = { RequestMethod.GET })
    public void exportExcelData(final HttpServletResponse response, @RequestParam("logBookCode") String logBookCode,
            final HttpServletRequest request) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().bind(request);

        try {
            if (logBookCode.equalsIgnoreCase("DTDC")) {
                List<T> dtdList = iLogBookReportService.getRecord(ItcBasedDoorToDoorCollection.class, orgId);
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringForDoorToDoorCollection.xlsx", request,
                        response);
                data.getExpotedExcelSheet(dtdList, ItcBasedDoorToDoorCollection.class);
            }
            if (logBookCode.equalsIgnoreCase("MFS")) {
                List<T> dtdList = iLogBookReportService.getRecord(ItcBasedMonitoringforSweeping.class, orgId);
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringForSweeping.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, ItcBasedMonitoringforSweeping.class);
            }
            if (logBookCode.equalsIgnoreCase("ICND")) {
                List<T> dtdList = iLogBookReportService.getRecord(ConstrutionNDemolition.class, orgId);
                WriteExcelData data = new WriteExcelData("ICTBasedMonitoringCDWaste.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, ConstrutionNDemolition.class);
            }
            if (logBookCode.equalsIgnoreCase("SWEP")) {
                List<T> dtdList = iLogBookReportService.getRecord(LogBookSweepingDetails.class, orgId);
                WriteExcelData data = new WriteExcelData("SweepingTemplate.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, LogBookSweepingDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("GBWG")) {
                List<T> dtdList = iLogBookReportService.getRecord(GardenBWGLogBook.class, orgId);
                WriteExcelData data = new WriteExcelData("GardenBWGCompostLogBook.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, GardenBWGLogBook.class);
            }
            if (logBookCode.equalsIgnoreCase("AML")) {
                List<T> dtdList = iLogBookReportService.getRecord(AnimalLogDetails.class, orgId);
                WriteExcelData data = new WriteExcelData("AnimalManagementLog.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, AnimalLogDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("VLM")) {
                List<T> dtdList = iLogBookReportService.getRecord(VehicleLogMain.class, orgId);
                WriteExcelData data = new WriteExcelData("VehicleLogbookMainpage.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, VehicleLogMain.class);
            }
            if (logBookCode.equalsIgnoreCase("VDP")) {
                List<T> dtdList = iLogBookReportService.getRecord(VehicleLogBook.class, orgId);
                WriteExcelData data = new WriteExcelData("VehicleDeploymentPlan.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, VehicleLogBook.class);
            }
            if (logBookCode.equalsIgnoreCase("CNDI")) {
                List<T> dtdList = iLogBookReportService.getRecord(CdWasteLogBookDetails.class, orgId);
                WriteExcelData data = new WriteExcelData("CnDWasteLogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, CdWasteLogBookDetails.class);
            }
            if (logBookCode.equalsIgnoreCase("SLRM")) {
                List<T> dtdList = iLogBookReportService.getRecord(Slrm1Details.class, orgId);
                WriteExcelData data = new WriteExcelData("SLRM1LogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, Slrm1Details.class);
            }
            if (logBookCode.equalsIgnoreCase("SLRM2")) {
                List<T> dtdList = iLogBookReportService.getRecord(Slrm2Details.class, orgId);
                WriteExcelData data = new WriteExcelData("SLRM2LogBookDetails.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, Slrm2Details.class);
            }
            if (logBookCode.equalsIgnoreCase("VLMS")) {
                List<T> dtdList = iLogBookReportService.getRecord(VehicleLogMain2.class, orgId);
                WriteExcelData data = new WriteExcelData("VehicleLogbookMainpage2.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, VehicleLogMain2.class);
            }
            if (logBookCode.equalsIgnoreCase("FINE")) {
                List<T> dtdList = iLogBookReportService.getRecord(AllFineLogBookDetails.class, orgId);
                WriteExcelData data = new WriteExcelData("AllFine.xlsx", request, response);
                data.getExpotedExcelSheet(dtdList, AllFineLogBookDetails.class);
            }	
			if (logBookCode.equalsIgnoreCase("DESL")) {
			    List<T> dtdList = iLogBookReportService.getRecord(LogBookDesludgingDetail.class, orgId);
			    WriteExcelData data = new WriteExcelData("Desludging.xlsx", request, response);
			    data.getExpotedExcelSheet(dtdList, LogBookDesludgingDetail.class);
			}			
			if (logBookCode.equalsIgnoreCase("UCC")) {
			    List<T> dtdList = iLogBookReportService.getRecord(LogBookUserChargesDetail.class, orgId);
			    WriteExcelData data = new WriteExcelData("UserChargeCollection.xlsx", request, response);
			    data.getExpotedExcelSheet(dtdList, LogBookUserChargesDetail.class);
			}
			
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
