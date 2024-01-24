package com.abm.mainet.property.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.service.PropertyNoticeService;
import com.abm.mainet.property.ui.model.PropertyDemandNoticeGenerationModel;

@Controller
@RequestMapping("PropertyDemandNoticePrinting.html")
public class PropertyDemandNoticePrintingController extends AbstractFormController<PropertyDemandNoticeGenerationModel> {

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);

        PropertyDemandNoticeGenerationModel model = this.getModel();
        model.setCommonHelpDocs("PropertyDemandNoticePrinting.html");

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
        List<String> notices = new ArrayList<>();
        notices.add("DN");
        notices.add("WN");
        List<LookUp> demandType = model.getLevelData("NTY");
        demandType.stream()
                .filter(notice -> notices.contains(notice.getLookUpCode()))
                .forEach(type -> {
                    model.getDemandType().add(type);
                });

        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "SearchPropNo")
    public ModelAndView search(HttpServletRequest request) {
        getModel().bind(request);
        PropertyDemandNoticeGenerationModel model = this.getModel();
        model.setNotGenSearchDtoList(null);
        if (model.getSpecialNotGenSearchDto().getNoticeType() == null || model.getSpecialNotGenSearchDto().getNoticeType() <= 0) {
            model.addValidationError("Please select notice type.");
        } else {
            if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
                if ((model.getSpecialNotGenSearchDto().getPropertyNo() == null
                        || model.getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
                        && (model.getSpecialNotGenSearchDto().getOldPropertyNo() == null
                                || model.getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
                    model.addValidationError("Please enter valid property number or Old property number.");
                } else {
                    fetchPropertyForDemandNotice(model);
                }
            } else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL")) {
                if ((model.getSpecialNotGenSearchDto().getLocId() == null || model.getSpecialNotGenSearchDto().getLocId() <= 0)
                        && (model.getSpecialNotGenSearchDto().getAssWard1() == null
                                || model.getSpecialNotGenSearchDto().getAssWard1() <= 0)
                        && (model.getSpecialNotGenSearchDto().getAssdUsagetype1() == null
                                || model.getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)) {
                    model.addValidationError("Please select any mandatory search criteria.");
                } else {
                    fetchPropertyForDemandNotice(model);
                }
            }
        }
        return defaultResult();
    }

    private void fetchPropertyForDemandNotice(PropertyDemandNoticeGenerationModel model) {
        List<NoticeGenSearchDto> notGenShowList;
        notGenShowList = propertyNoticeService.fetchPropertyDemandNoticePrint(
                model.getSpecialNotGenSearchDto(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.setNotGenSearchDtoList(notGenShowList);
        if (notGenShowList == null || notGenShowList.isEmpty()) {
            model.addValidationError("No record found");
        }
    }

    @RequestMapping(params = "PropertyNoticePrinting", method = { RequestMethod.POST })
    public ModelAndView printReport(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse)
            throws IOException {
        bindModel(httpServletRequest);
        final PropertyDemandNoticeGenerationModel model = getModel();
        return printCheckListRejectReport(model, httpServletRequest, httpServletResponse);
    }

    private ModelAndView printCheckListRejectReport(final PropertyDemandNoticeGenerationModel model,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        /*************************** Declaring the DTO list and single DTO object ***************************/
        final List<PrintBillMaster> dtolist = propertyNoticeService.getNoticePrintingData(
                model.getNotGenSearchDtoList(), UserSession.getCurrent().getOrganisation(),
                model.getSpecialNotGenSearchDto().getNoticeType());
        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);
        String jrxmlName = "";
        LookUp typelookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getSpecialNotGenSearchDto().getNoticeType(),
                UserSession.getCurrent().getOrganisation());
        if (typelookUp.getLookUpCode().equals("DN")) {
            jrxmlName = "PropertyTaxDemandNoticeEnglish.jrxml";
        } else if (typelookUp.getLookUpCode().equals("WN")) {
            jrxmlName = "PropertyTaxWarrantNoticeEnglish.jrxml";
        }
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
       String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
                jrxmlFileLocation, map, dtolist, UserSession.getCurrent().getEmployee().getEmpId());
        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
        	if(fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
        		fileName=fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);  
        	}
        	else if(fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
        		fileName=fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
        	}   
            getModel().setFilePath(fileName);
        }
        getModel().setRedirectURL("PropertyDemandNoticePrinting.html");
        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
    }

}
