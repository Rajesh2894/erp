package com.abm.mainet.swm.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.PopulationMasterDTO;
import com.abm.mainet.swm.dto.WriteExcelData;
import com.abm.mainet.swm.service.IPopulationMasterService;
import com.abm.mainet.swm.ui.model.PopulationMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/PopulationMaster.html")
public class PopulationMasterController extends AbstractFormController<PopulationMasterModel> {
    private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";
    /**
     * The IPopulationMaster Service
     */
    @Autowired
    private IPopulationMasterService populationMasterService;

    /**
     * The IFileUpload Service
     */
    @Autowired
    private IFileUploadService fileUpload;

    /**
     * The TbFinancialyear Service
     */
    @Resource
    TbFinancialyearService tbFinancialyearService;

    /**
     * The IPopulationMaster Service
     */
    @Resource
    IPopulationMasterService ipopulationMasterService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        // loadDefaultData(httpServletRequest);
        loadSummaryData(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("PopulationMaster.html");
        return index();
    }

    private void loadSummaryData(HttpServletRequest httpServletRequest) {
        PopulationMasterModel model = this.getModel();
        PopulationMasterDTO popDto = null;
        List<PopulationMasterDTO> popaddList = new ArrayList<>();
        List<PopulationMasterDTO> poplist = populationMasterService.findPopulation(null, null, null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long popYear = 0L;
        if (poplist != null && !poplist.isEmpty()) {
            for (PopulationMasterDTO popDetList : poplist) {
                popDto = new PopulationMasterDTO();
                if (!popYear.equals(popDetList.getPopYear())) {
                    BigDecimal totalPopulation = new BigDecimal(0.00);
                    popDto.setPopYear(popDetList.getPopYear());
                    popDto.setPopId(popDetList.getPopId());
                    popYear = popDto.getPopYear();
                    for (PopulationMasterDTO popDetList1 : poplist) {
                        if (popYear.equals(popDetList1.getPopYear())) {
                            totalPopulation = totalPopulation.add(new BigDecimal(popDetList1.getPopEst()));
                        }
                    }
                    popDto.setTotalPopulation(totalPopulation.toString());
                    popaddList.add(popDto);
                }
            }
            Set<PopulationMasterDTO> setOfPopulation = new HashSet<PopulationMasterDTO>(popaddList);
            model.setPopulationSet(setOfPopulation);
        }
    }

    /**
     * @param httpServletRequest
     */
    private void loadDefaultData(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        PopulationMasterModel model = this.getModel();
        Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = tbFinancialyearService.findAllFinancialYearByOrgId(org);
        if (finYearList != null && !finYearList.isEmpty()) {
            for (final TbFinancialyear finYearTemp : finYearList) {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    model.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);

                }
            }
        }
        this.getModel().setPopulationslist(populationMasterService.findPopulation(null, null, null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * addPopulationForm
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddPopulationMaster", method = RequestMethod.POST)
    public ModelAndView addPopulationForm(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        // PopulationMasterModel model = this.getModel();
        fileUpload.sessionCleanUpForFileUpload();
        // this.getModel().setPopulationMasterDTO(new PopulationMasterDTO());
        // List<PopulationMasterDTO> listOfPopYr = ipopulationMasterService.financialyear();
        // model.setPopulationslist(listOfPopYr);
        return new ModelAndView("addPopulationMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * Excel Upload Form
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "excelUploadMaster", method = RequestMethod.POST)
    public ModelAndView ExcelUploadForm(final HttpServletRequest request) {
        fileUpload.sessionCleanUpForFileUpload();
        return new ModelAndView("excelUploadMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * edit Population Form
     * @param request
     * @param popId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editPopulationMaster", method = RequestMethod.POST)
    public ModelAndView editPopulationForm(final HttpServletRequest request, @RequestParam Long popId) {
        sessionCleanup(request);
        this.getModel().getPopulationMasterDTO().setPopYear(popId);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        this.getModel().setPopulationslist(populationMasterService.findPopulation(popId, null, null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addPopulationMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * View Population Form
     * @param request
     * @param popId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewPopulationMaster", method = RequestMethod.POST)
    public ModelAndView ViewPopulationForm(final HttpServletRequest request, @RequestParam Long popId) {
        sessionCleanup(request);
        this.getModel().getPopulationMasterDTO().setPopYear(popId);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        this.getModel().setPopulationslist(populationMasterService.findPopulation(popId, null, null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addPopulationMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * delete Population Form
     * @param request
     * @param popId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deletePopulationMaster", method = RequestMethod.POST)
    public ModelAndView deletePopulationForm(final HttpServletRequest request, @RequestParam Long popId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        populationMasterService.deletePopulationMaster(popId, emp.getEmpId(), emp.getEmppiservername());
        loadDefaultData(request);
        return new ModelAndView("PopulationMasterForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * create Population Form
     * @param request
     * @param yearCpdId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchPopulationMaster", method = RequestMethod.POST)
    public ModelAndView createPopulationForm(final HttpServletRequest request,
            @RequestParam(required = false) Long yearCpdId) {
        sessionCleanup(request);
        this.getModel().getPopulationMasterDTO().setPopYear(yearCpdId);
        PopulationMasterModel model = this.getModel();
        PopulationMasterDTO popDto = null;
        List<PopulationMasterDTO> popaddList = new ArrayList<>();
        List<PopulationMasterDTO> poplist = populationMasterService.findPopulation(yearCpdId, null, null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long popYear = 0L;
        if (poplist != null && !poplist.isEmpty()) {
            for (PopulationMasterDTO popDetList : poplist) {
                popDto = new PopulationMasterDTO();
                if (!popYear.equals(popDetList.getPopYear())) {
                    BigDecimal totalPopulation = new BigDecimal(0.00);
                    popDto.setPopYear(popDetList.getPopYear());
                    popDto.setPopId(popDetList.getPopId());
                    popYear = popDto.getPopYear();
                    for (PopulationMasterDTO popDetList1 : poplist) {
                        if (popYear.equals(popDetList1.getPopYear())) {
                            totalPopulation = totalPopulation.add(new BigDecimal(popDetList1.getPopEst()));
                        }
                    }
                    popDto.setTotalPopulation(totalPopulation.toString());
                    popaddList.add(popDto);
                }
            }
            Set<PopulationMasterDTO> setOfPopulation = new HashSet<PopulationMasterDTO>(popaddList);
            model.setPopulationSet(setOfPopulation);
        }
        return new ModelAndView("PopulationMasterForm", MainetConstants.FORM_NAME, model);
    }

    /**
     * export Rate Excel Data
     * @param response
     * @param request
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "exportRateExcelData", method = { RequestMethod.GET })
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        List<PopulationMasterDTO> dtos = new ArrayList<>();
        try {
            WriteExcelData data = new WriteExcelData("PopulationMaster.xlsx",
                    request, response);
            data.getExpotedExcelSheet(dtos, PopulationMasterDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load Validate And Load Excel Data
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(final HttpServletRequest request) {
        PopulationMasterModel model = this.getModel();
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
            ReadExcelData data = new ReadExcelData<>(filePath, PopulationMasterDTO.class);
            data.parseExcelList();
            boolean status = false;
            int i = 1;
            List<String> errlist = data.getErrorList();
            if (errlist.isEmpty()) {
                List<PopulationMasterDTO> dtoList = data.getParseData();
                List<PopulationMasterDTO> detList = new ArrayList<>();

                for (PopulationMasterDTO populationMasterDTO : dtoList) {
                    Long parentId2, parentId3, parentId4, parentId5;

                    if (populationMasterDTO.getCodDwzid1Str() != null) {
                        List<LookUp> lookupcode = CommonMasterUtility.getSecondLevelData("SWZ", 1);
                        for (LookUp lookUp : lookupcode) {
                            if (populationMasterDTO.getCodDwzid1Str().trim().equalsIgnoreCase(lookUp.getLookUpDesc())) {
                                populationMasterDTO.setCodDwzid1(lookUp.getLookUpId());
                                parentId2 = lookUp.getLookUpId();
                                if (populationMasterDTO.getCodDwzid2Str() != null) {
                                    List<LookUp> lookupcode1 = ApplicationSession.getInstance()
                                            .getChildLookUpsFromParentId(parentId2);
                                    for (LookUp lookUp1 : lookupcode1) {
                                        if (populationMasterDTO.getCodDwzid2Str().trim()
                                                .equalsIgnoreCase(lookUp1.getLookUpDesc())) {
                                            populationMasterDTO.setCodDwzid2(lookUp1.getLookUpId());
                                            parentId3 = lookUp1.getLookUpId();

                                            if (populationMasterDTO.getCodDwzid3Str() != null) {
                                                List<LookUp> lookupcode2 = ApplicationSession.getInstance()
                                                        .getChildLookUpsFromParentId(parentId3);
                                                for (LookUp lookUp2 : lookupcode2) {
                                                    if (populationMasterDTO.getCodDwzid3Str().trim()
                                                            .equalsIgnoreCase(lookUp2.getLookUpDesc())) {
                                                        populationMasterDTO.setCodDwzid3(lookUp2.getLookUpId());
                                                        parentId4 = lookUp2.getLookUpId();

                                                        if (populationMasterDTO.getCodDwzid4Str() != null) {
                                                            List<LookUp> lookupcode3 = ApplicationSession.getInstance()
                                                                    .getChildLookUpsFromParentId(parentId4);
                                                            for (LookUp lookUp3 : lookupcode3) {
                                                                if (populationMasterDTO.getCodDwzid4Str().trim()
                                                                        .equalsIgnoreCase(lookUp3.getLookUpDesc())) {
                                                                    populationMasterDTO.setCodDwzid4(lookUp3.getLookUpId());
                                                                    parentId5 = lookUp3.getLookUpId();

                                                                    if (populationMasterDTO.getCodDwzid5Str() != null) {
                                                                        List<LookUp> lookupcode4 = ApplicationSession
                                                                                .getInstance()
                                                                                .getChildLookUpsFromParentId(parentId5);
                                                                        for (LookUp lookUp4 : lookupcode4) {
                                                                            if (populationMasterDTO.getCodDwzid5Str().trim()
                                                                                    .equalsIgnoreCase(lookUp4.getLookUpDesc())) {
                                                                                populationMasterDTO
                                                                                        .setCodDwzid5(lookUp4.getLookUpId());
                                                                            }
                                                                        }
                                                                        if (populationMasterDTO.getCodDwzid5() == null) {
                                                                            this.getModel().addValidationError("City Null" + i + "/ Enter Proper City Name");
                                                                        }

                                                                    } /*else {
                                                                        this.getModel().addValidationError("City Null" + i + "/ Enter Proper City Name");
                                                                    }*/
                                                                }
                                                            }
                                                            if (populationMasterDTO.getCodDwzid4() == null) {
                                                                this.getModel().addValidationError("Block Null" + i + "/ Enter Proper Block Name");
                                                            } 

                                                        } /*else {
                                                            this.getModel().addValidationError("Block Null" + i + "/ Enter Proper Block Name");
                                                        }*/
                                                    }
                                                }
                                                if (populationMasterDTO.getCodDwzid3() == null) {
                                                    this.getModel().addValidationError("Colony Null" + i + "/ Enter Proper Colony Name");
                                                } 

                                            }/*else {
                                                this.getModel().addValidationError("Colony Null" + i + "/ Enter Proper Colony Name");
                                            }*/
                                        }
                                    }
                                    if (populationMasterDTO.getCodDwzid2() == null) {
                                        this.getModel().addValidationError("Ward Null" + i + "/ Enter Proper Ward Name");
                                    }

                                } else {
                                    this.getModel().addValidationError("Ward Null" + i + "/ Enter Proper Ward Name");
                                }

                            }
                        }
                        if (populationMasterDTO.getCodDwzid1() == null) {
                            this.getModel().addValidationError("Zone Null" + i + "/ Enter Proper Zone Name");
                        }
                    } else {
                        this.getModel().addValidationError("Zone Null" + i + "/ Enter Proper Zone Name");
                    }

                    if (populationMasterDTO.getPopYear() != null) {
                        populationMasterDTO
                                .setPopYear(
                                        CommonMasterUtility.getIdFromPrefixLookUpDesc(populationMasterDTO.getPopYear().toString(),
                                                "CYR",
                                                (int) UserSession.getCurrent().getOrganisation().getOrgid()));

                    }
                    if (populationMasterDTO.getPopYear() == null || populationMasterDTO.getPopYear() == -1) {
                        this.getModel().addValidationError("Census Year " + i + " can not be null / Enter Proper Census Year");
                    }

                    if (populationMasterDTO.getPopActive() != null) {
                        populationMasterDTO.setPopActive(populationMasterDTO.getPopActive().toUpperCase());
                    }else {
                        populationMasterDTO.setPopActive(MainetConstants.Y_FLAG);
                    }
                    populationMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    populationMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    populationMasterDTO.setCreatedDate(new Date());
                    populationMasterDTO.setLgIpMac(Utility.getMacAddress());
                    detList.add(populationMasterDTO);
                    i++;
                }
                if (!this.getModel().hasValidationErrors()) {
                    populationMasterService.savePopulationMaster(detList);
                }
            }else {
                this.getModel().addValidationError("Check the Content of Excel Sheet");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fileUpload.sessionCleanUpForFileUpload();
        // return new ModelAndView("excelUploadMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        // return new ModelAndView("PopulationMasterForm", MainetConstants.FORM_NAME, model);
        return customResult("excelUploadMaster/Form");
    }

}
