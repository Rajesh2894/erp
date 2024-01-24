package com.abm.mainet.swm.ui.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.ui.model.CDWasteManagementModel;

@Controller
@RequestMapping("CDWasteManagementReport.html")
public class CDWasteManagementController extends AbstractFormController<CDWasteManagementModel> {

    @Autowired
    private ILogBookReportService iLogBookReportService;

    @Autowired
    private IMRFMasterService imRFMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("CDWasteManagementReport.html");
        getMrfCenterName(httpServletRequest);
        return index();
    }

    private void loadMrfCenterName(final HttpServletRequest httpServletRequest) {
        this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    private void getMrfCenterName(final HttpServletRequest httpServletRequest) {
        loadMrfCenterName(httpServletRequest);
        Map<Long, String> mrfCenterNameMap = this.getModel().getmRFMasterDtoList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        this.getModel().getmRFMasterDtoList().forEach(master -> {
            master.setMrfPlantName(mrfCenterNameMap.get(master.getMrfId()));
        });
        this.getModel().getmRFMasterDtoList();
    }

    @ResponseBody
    @RequestMapping(params = "cdWasteManagement", method = RequestMethod.POST)
    public ModelAndView cdWasteCenterInput(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo, @RequestParam Long mrfId, @RequestParam("monthName") String monthName) {
        sessionCleanup(request);
        CDWasteManagementModel cdWasteManagementModel = this.getModel();
        Long LookUpId = UserSession.getCurrent().getOrganisation().getOrgCpdIdDis();
        Organisation organisation = UserSession.getCurrent().getOrganisation();
        LookUp lookUpDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(LookUpId, organisation);
        String distictName = lookUpDesc.getDescLangFirst();
        cdWasteManagementModel.getVehicleMasterDTO().setDistrictName(distictName);
        WasteCollectorDTO cdWasteCollectionInput = iLogBookReportService
                .getCAndDWasteCenterInputByMrfIdAndMonthNo(UserSession.getCurrent().getOrganisation().getOrgid(), mrfId, monthNo);
        cdWasteManagementModel.setWasteCollectorDTO(cdWasteCollectionInput);
        cdWasteManagementModel.getWasteCollectorDTO().setDistrictName(distictName);
        cdWasteManagementModel.getWasteCollectorDTO().setMonthName(monthName);
        return new ModelAndView("cdWasteManagementReportData", MainetConstants.FORM_NAME, cdWasteManagementModel);
    }
}
