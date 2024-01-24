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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.ui.model.DayMonthWiseDumpingModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/DayMonthWiseDumping.html")
public class DayMonthWiseDumpingController extends AbstractFormController<DayMonthWiseDumpingModel> {

    @Autowired
    private IMRFMasterService imRFMasterService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("DayMonthWiseDumping.html");
        loadMrfCenterName(httpServletRequest);
        getMrfCenterName(httpServletRequest);
        return index();
    }

    /**
     * @param httpServletRequest
     */
    private void loadMrfCenterName(final HttpServletRequest httpServletRequest) {
        this.getModel().setmRFMasterDtoList(imRFMasterService.serchMRFMasterByPlantIdAndPlantname(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * @param httpServletRequest
     */
    private void getMrfCenterName(final HttpServletRequest httpServletRequest) {
        loadMrfCenterName(httpServletRequest);
        Map<Long, String> mrfCenterNameMap = this.getModel().getmRFMasterDtoList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        this.getModel().getmRFMasterDtoList().forEach(master -> {
            master.setMrfPlantName(mrfCenterNameMap.get(master.getMrfId()));
        });
        this.getModel().getmRFMasterDtoList();
    }

    /**
     * day WiseDumping Summary
     * @param request
     * @param deName
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView dayWiseDumpingSummary(final HttpServletRequest request, @RequestParam("deName") Long deName,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        DayMonthWiseDumpingModel dwDm = this.getModel();
        Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        MRFMasterDto mRFMasterDetails = imRFMasterService.findDayMonthWiseMrfCenter(OrgId, deName, Utility.stringToDate(fromDate),
                Utility.stringToDate(toDate));
        if (mRFMasterDetails == null) {
            dwDm.getmRFMasterDto().setFlagMsg("N");
            loadMrfCenterName(request);
            getMrfCenterName(request);
            redirectType = "DayMonthWiseDumpingList";
        } else {
            dwDm.getmRFMasterDto().setFlagMsg("Y");
            dwDm.setmRFMasterDto(mRFMasterDetails);
            dwDm.setmRFMasterDto(mRFMasterDetails);
            dwDm.getmRFMasterDto().setFromDate(fromDate);
            dwDm.getmRFMasterDto().setToDate(toDate);
            redirectType = "dayWiseDumpingSummary";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, dwDm);
    }

}
