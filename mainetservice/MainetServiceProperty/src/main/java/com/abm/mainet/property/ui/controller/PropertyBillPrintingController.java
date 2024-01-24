package com.abm.mainet.property.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.service.PropertyBillGenerationService;
import com.abm.mainet.property.ui.model.PropertyBillGenerationModel;

@Controller
@RequestMapping("PropertyBillPrinting.html")
public class PropertyBillPrintingController extends AbstractFormController<PropertyBillGenerationModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyBillPrintingController.class);

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);

        PropertyBillGenerationModel model = this.getModel();
        model.setCommonHelpDocs("PropertyBillGeneration.html");
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(request);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, org);
			this.getModel().setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "SearchPropNo")
    public ModelAndView search(HttpServletRequest request) {
        getModel().bind(request);
        PropertyBillGenerationModel model = this.getModel();
        model.setNotGenSearchDtoList(null);
        Organisation org = UserSession.getCurrent().getOrganisation();
        List<NoticeGenSearchDto> notGenShowList = null;
        model.setSuccessMessage(MainetConstants.N_FLAG);
        if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
            if ((model.getSpecialNotGenSearchDto().getPropertyNo() == null
                    || model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
                    && (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
                            || model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
                model.addValidationError("Please enter valid property number or Old property number.");
            } else {            	
                notGenShowList = propertyBillGenerationService.getAllPropForBillPrint(
                        model.getSpecialNotGenSearchDto(),
                        org.getOrgid());
				if (!model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("GP")) {
					notGenShowList = notGenShowList.stream()
							.filter(notGen -> !MainetConstants.FlagY.equals(notGen.getIsGroup()))
							.collect(Collectors.toList());
				}
				if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
					notGenShowList = notGenShowList.stream()
							.filter(notGen -> MainetConstants.FlagY.equals(notGen.getSplNotDueDatePass()))
							.collect(Collectors.toList());
				}
                model.setNotGenSearchDtoList(notGenShowList);
                if (notGenShowList == null || notGenShowList.isEmpty()) {
                    model.addValidationError("Please enter valid Property/ Old property number");
                }
            }
        } else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL")) {
            if ((model.getSpecialNotGenSearchDto().getLocId() == null || model.getSpecialNotGenSearchDto().getLocId() <= 0)
                    && (model.getSpecialNotGenSearchDto().getAssWard1() == null
                            || model.getSpecialNotGenSearchDto().getAssWard1() <= 0)
                    && (model.getSpecialNotGenSearchDto().getAssdUsagetype1() == null
                            || model.getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)) {
                model.addValidationError("Please select any mandatory search criteria.");
            } else {
                notGenShowList = propertyBillGenerationService.fetchBillPrintDetailBySearchCriteria(
                        model.getSpecialNotGenSearchDto(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
				if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
					notGenShowList = notGenShowList.stream()
							.filter(notGen -> MainetConstants.FlagY.equals(notGen.getSplNotDueDatePass()))
							.collect(Collectors.toList());
				}
                model.setNotGenSearchDtoList(notGenShowList);
                if (notGenShowList == null || notGenShowList.isEmpty()) {
                    model.addValidationError("No record found");
                }
            }
        }
        if(!model.hasValidationErrors()) {
        	model.setSuccessMessage(MainetConstants.Y_FLAG);
        }
        return defaultResult();
    }

    @RequestMapping(params = "PropertyBillPrinting", method = { RequestMethod.POST })
    public ModelAndView printReport(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("checkedValue") final String checkedValue)
            throws IOException {
        bindModel(httpServletRequest);
        final PropertyBillGenerationModel model = getModel();
        return printCheckListRejectReport(model, httpServletRequest, httpServletResponse, checkedValue);
    }

    private ModelAndView printCheckListRejectReport(final PropertyBillGenerationModel model,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, String checkedValue) {
        /*************************** Declaring the DTO list and single DTO object ***************************/

        String[] ids = checkedValue.split(",");

        List<String> idsList = new LinkedList<String>(Arrays.asList(ids));

        if (idsList.get(0).equals("selectall")) {

            idsList.remove("selectall");
        }

        List<NoticeGenSearchDto> NotList = new ArrayList<>();

        for (String s : idsList) {
            model.getNotGenSearchDtoList().get(Integer.valueOf(s)).setGenNotCheck("Y");

            NotList.add(model.getNotGenSearchDtoList().get(Integer.valueOf(s)));

        }

        final List<PrintBillMaster> dtolist = propertyBillGenerationService.getBillPrintingData(
                NotList, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);
        String jrxmlName = "PropertyTaxBillPrintingEnglish.jrxml";
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
        final String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
                jrxmlFileLocation, map, dtolist, UserSession.getCurrent().getEmployee().getEmpId());
        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            getModel().setFilePath(fileName);
        }
        /*
         * final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);
         */
        getModel().setRedirectURL("PropertyBillPrinting.html");
        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
    }
    
	@RequestMapping(params = "printBill", method = RequestMethod.POST)
	public @ResponseBody String printBill(@RequestParam("parentPropNo") String parentPropNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long finId = iFinancialYearService.getFinanceYearId(new Date());
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=Parent_GroupedPropertyBillReport.rptdesign&Orgid=" + orgId
				+ "&PropertyNo=" + parentPropNo+"&FinancialYear="+finId;
	}
	
	@RequestMapping(params = "printDetailBill", method = RequestMethod.POST)
	public @ResponseBody String printDetailBill(@RequestParam("parentPropNo") String parentPropNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=DetailGroupPropertyBillPrinting.rptdesign&Orgid=" + orgId
				+ "&ParentPropNo=" + parentPropNo;
	}

}
