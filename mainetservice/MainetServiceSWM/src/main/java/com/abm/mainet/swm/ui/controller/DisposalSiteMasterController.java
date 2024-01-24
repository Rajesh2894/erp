package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.DesposalDetailDTO;
import com.abm.mainet.swm.dto.DisposalMasterDTO;
import com.abm.mainet.swm.service.IDisposalMasterService;
import com.abm.mainet.swm.ui.model.DisposalSiteMasterModel;

@Controller
@RequestMapping("/DisposalSiteMaster.html")
public class DisposalSiteMasterController extends AbstractFormController<DisposalSiteMasterModel> {
    @Autowired
    private IDisposalMasterService disposalMasterService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        fileUpload.sessionCleanUpForFileUpload();
        searchDefault(httpServletRequest);
        this.getModel().setCommonHelpDocs("DisposalSiteMaster.html");
        /*
         * this.getModel().setLocList(iLocationMasService
         * .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
         */

        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocAddress));

        this.getModel().getDisposalMasterList().forEach(disp -> {
            disp.setDeAddress(locationMap.get(disp.getDeLocId()));
        });

        return defaultResult();
    }

    /**
     * @param httpServletRequest
     */
    private void searchDefault(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setDisposalMasterList(disposalMasterService.serchDisposalSite(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    @ResponseBody
    @RequestMapping(params = "Add", method = RequestMethod.POST)
    public ModelAndView adddisposalSiteMaster(final HttpServletRequest request) {
        setSiteItemDetails();
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setEmployeList(
                employeeService.findAllEmployeeByDept(UserSession.getCurrent().getOrganisation().getOrgid(), deptId));
        return new ModelAndView("add/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editdisposalSiteMaster(final HttpServletRequest request, @RequestParam Long siteNumber) {
        sessionCleanup(request);
        this.getModel().setDisposalMasterDTO(disposalMasterService.getDisposalSiteBySiteNumber(siteNumber));
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.SolidWasteManagement.DISPOSAL_SITE + siteNumber.toString());
        this.getModel().setAttachDocsList(attachDocs);
        /*
         * this.getModel().setLocList(iLocationMasService
         * .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
         */
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        setSiteItemDetails();
        return new ModelAndView("editDisposalSite/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    private void setSiteItemDetails() {
        List<DesposalDetailDTO> tbSwDesposalDets = new ArrayList<>();
        this.getModel().getAlphaNumericSortedLevelData("WTY", 1).forEach(lookup -> {
            DesposalDetailDTO dto = new DesposalDetailDTO();
            dto.setDeWestTypeDesc(lookup.getLookUpDesc());
            dto.setDeWestType(lookup.getLookUpId());
            tbSwDesposalDets.add(dto);

        });

        this.getModel().getDisposalMasterDTO().getTbSwDesposalDets().forEach(det -> {
            tbSwDesposalDets.forEach(list1 -> {
                if (det.getDeWestType().equals(list1.getDeWestType())) {
                    det.setDeWestTypeDesc(list1.getDeWestTypeDesc());
                    BeanUtils.copyProperties(det, list1);
                }
            });
        });
        this.getModel().getDisposalMasterDTO().setTbSwDesposalDets(tbSwDesposalDets);
    }

    /**
     * view disposal Site Master
     * @param request
     * @param siteNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewdisposalSiteMaster(final HttpServletRequest request, @RequestParam Long siteNumber) {
        this.getModel().setDisposalMasterDTO(disposalMasterService.getDisposalSiteBySiteNumber(siteNumber));
        setSiteItemDetails();
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.SolidWasteManagement.DISPOSAL_SITE + siteNumber.toString());
        this.getModel().setAttachDocsList(attachDocs);
        /*
         * this.getModel().setLocList(iLocationMasService
         * .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
         */
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("viewDisposalSite/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * search disposal Site Master
     * @param request
     * @param siteNumber
     * @param siteName
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView searchdisposalSiteMaster(final HttpServletRequest request,
            @RequestParam(required = false) Long siteNumber, @RequestParam(required = false) String siteName) {
        sessionCleanup(request);
        this.getModel().getDisposalMasterDTO().setDeId(siteNumber);
        this.getModel().getDisposalMasterDTO().setDeName(siteName);
        this.getModel().setDisposalMasterList(disposalMasterService.serchDisposalSite(siteNumber, siteName,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocAddress));

        this.getModel().getDisposalMasterList().forEach(disp -> {
            disp.setDeAddress(locationMap.get(disp.getDeLocId()));
        });
        return new ModelAndView("DisposalSiteMasterForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * delete disposalSite Master
     * @param request
     * @param siteNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deletedisposalSiteMaster(final HttpServletRequest request, @RequestParam Long siteNumber) {
        sessionCleanup(request);
        Employee emp = UserSession.getCurrent().getEmployee();
        disposalMasterService.deleteDisposalSite(siteNumber, emp.getEmpId(), emp.getEmppiservername());
        searchDefault(request);
        return new ModelAndView("DisposalSiteMasterForm", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * @return
     */
    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public Object[] getMapData() {
        final Object data[] = new Object[] { null, null, null, null };
        List<String[]> position = null;
        TbLocationMas locList;

        List<DisposalMasterDTO> displmstr = disposalMasterService.serchDisposalSite(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        disposalMasterService.downloadDisposalSiteImages(displmstr);

        if (displmstr != null && !displmstr.isEmpty()) {
            position = new ArrayList<>();

            for (DisposalMasterDTO mstr : displmstr) {

                try {
                    if (mstr.getDeLocId() != null) {
                        locList = iLocationMasService.findById(mstr.getDeLocId());

                        if (locList.getLatitude() != null && locList.getLongitude() != null) {
                            final String[] mapData = new String[] {
                                    CommonMasterUtility.getCPDDescription(mstr.getDeCategory(),
                                            PrefixConstants.D2KFUNCTION.ENGLISH_DESC),
                                    locList.getLatitude(), locList.getLongitude(), mstr.getDeArea().toString(),
                                    mstr.getDeCapacity().toString(),
                                    CommonMasterUtility.getCPDDescription(mstr.getDeCapacityUnit(),
                                            PrefixConstants.D2KFUNCTION.CPD_VALUE),
                                    mstr.getDeName(), mstr.getSiteImage() };

                            position.add(mapData);
                        }
                    }
                } catch (Exception e) {
                    logger.error("error while encoding", e);
                }
            }
        }

        data[0] = position;

        return data;
    }

}
