package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;
import com.abm.mainet.property.service.PropertyNoticeService;
import com.abm.mainet.property.ui.model.SpecialNoticeGenerationModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/SpecialNoticePrinting.html")
public class SpecialNoticePrintingController extends AbstractFormController<SpecialNoticeGenerationModel> {

    private static Logger log = Logger.getLogger(SpecialNoticePrintingController.class);

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        SpecialNoticeGenerationModel model = this.getModel();
        model.bind(request);
        model.setCommonHelpDocs("SpecialNoticePrinting.html");
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
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
        return defaultResult();
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SpecialNoticeGenerationModel model = this.getModel();
        if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
            List<NoticeGenSearchDto> notGenShowList = propertyNoticeService.getAllSpecialNoticeProperty(
                    model.getSpecialNotGenSearchDto(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            model.setNotGenSearchDtoList(notGenShowList);
        } else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL"
                + "")) {
            List<NoticeGenSearchDto> notGenShowList = propertyNoticeService.fetchAllSpecialNoticePropBySearchCriteria(
                    model.getSpecialNotGenSearchDto(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            model.setNotGenSearchDtoList(notGenShowList);
        }
        return defaultResult();

    }

    @RequestMapping(params = "printSpecialNotice", method = RequestMethod.POST)
    public ModelAndView printSpecialNotice(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam("checkedValue") final String checkedValue) {

        bindModel(httpServletRequest);

        SpecialNoticeGenerationModel model = this.getModel();

        String[] ids = checkedValue.split(",");

        List<String> idsList = new LinkedList<String>(Arrays.asList(ids));

        if (idsList.get(0).equals("selectall")) {

            idsList.remove("selectall");
        }

        for (String s : idsList) {
            model.getNotGenSearchDtoList().get(Integer.valueOf(s)).setGenNotCheck("Y");
            ;

        }

        final List<SpecialNoticeReportDto> specNotDtoList = propertyNoticeService
                .setDtoForSpecialNotPrinting(model.getNotGenSearchDtoList(), UserSession.getCurrent().getOrganisation());

        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);    
        map.put("imgPath", imgpath);
        
        String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
        String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
                jrxmlFileLocation, map, specNotDtoList, UserSession.getCurrent().getEmployee().getEmpId());

        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
                fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
            } else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
                fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
            }
            getModel().setFilePath(fileName);
        }
        getModel().setRedirectURL("AdminHome.html");
        // T#101661 here send email and attachment to user
        String filePath = Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR + fileName;
        final File file = new File(filePath);
        List<File> filesForAttach = new ArrayList<File>();
        filesForAttach.add(file);
        String url = "SpecialNoticePrinting.html";
        if (!model.getNotGenSearchDtoList().isEmpty() && !specNotDtoList.isEmpty()
                && StringUtils.isNotBlank(model.getNotGenSearchDtoList().get(0).getEmailId())) {
            final SMSAndEmailDTO dto = new SMSAndEmailDTO();

            dto.setEmail(model.getNotGenSearchDtoList().get(0).getEmailId());
            dto.setMobnumber(model.getNotGenSearchDtoList().get(0).getMobileNo());
            dto.setPropertyNo(specNotDtoList.get(0).getPropNoV());
            dto.setOwnerName(specNotDtoList.get(0).getName());
            dto.setDecision(specNotDtoList.get(0).getLetterHeading());
            dto.setNoticeDate(specNotDtoList.get(0).getNoticeDateV());
            dto.setOrganizationName(specNotDtoList.get(0).getOrgName());
            dto.setNoticeNo(specNotDtoList.get(0).getNoticeNoV());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setServName("NPG");
            dto.setFilesForAttach(filesForAttach);
            ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, url,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, dto, UserSession.getCurrent().getOrganisation(),
                    UserSession.getCurrent().getLanguageId());

        } else {
            String value = model.getNotGenSearchDtoList().isEmpty() ? "Empty application Id"
                    : model.getNotGenSearchDtoList().get(0).getApplicationId() + "";
            log.info("Email Id is Empty for applicationId " + value);
        }

        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "resetFormData", method = RequestMethod.POST)
    public ModelAndView resetFormDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        SpecialNoticeGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(httpServletRequest);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        model.setDeptId(deptId);
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
        return defaultMyResult();
    }

}