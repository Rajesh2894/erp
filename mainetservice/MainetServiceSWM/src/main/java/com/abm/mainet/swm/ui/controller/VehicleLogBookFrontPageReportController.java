package com.abm.mainet.swm.ui.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.ITripSheetService;
import com.abm.mainet.swm.ui.model.VehicleLogBookFrontPageModel;

@Controller
@RequestMapping("/VehiclelogbookfrontpageReport.html")
public class VehicleLogBookFrontPageReportController extends AbstractFormController<VehicleLogBookFrontPageModel> {

    @Autowired
    private IMRFMasterService mRFMasterService;

    @Autowired
    private ITripSheetService vehicleLogBookService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("VehiclelogbookfrontpageReport.html");
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "getVehicleLogReport", method = RequestMethod.POST)
    public ModelAndView vehiclelogbookfrontpage(final HttpServletRequest request, @RequestParam("date") Date date,
            @RequestParam("mrfId") Long mrfId) {
        sessionCleanup(request);
        String redirectType = null;
        List<Object[]> vehicleNO = vehicleLogBookService.getVehicleDetsOfAssociateMRFCenter(mrfId, date,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!vehicleNO.isEmpty())
            this.getModel().setVehicleNo(vehicleNO);

        List<Object[]> beatCount = vehicleLogBookService.getVehicleCountandBeatCountOfAssMRFCenter(mrfId, date,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!beatCount.isEmpty())
            this.getModel().setBeatCount(beatCount);

        List<Object[]> wardCount = vehicleLogBookService.getwardCount(mrfId, date,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!wardCount.isEmpty())
            this.getModel().setWardCount(
                    vehicleLogBookService.getwardCount(mrfId, date, UserSession.getCurrent().getOrganisation().getOrgid()));

        List<Object[]> mrfDetails = vehicleLogBookService.getMrfwiseDetails(mrfId, date,
                UserSession.getCurrent().getOrganisation().getOrgid());
        BigInteger ResidentialCount = new BigInteger("0");
        BigInteger commercialCount = new BigInteger("0");
        BigInteger commercialShopsCount = new BigInteger("0");
        String SwardNo = "";
        String EwardNo = "";
        redirectType = "VehicleLogBookReport";
        if (vehicleNO.isEmpty() || beatCount.isEmpty() || mrfDetails.isEmpty() || wardCount.isEmpty()) {
            redirectType = "VehicleLogBookFrontPageList";
            this.getModel().getVehicleLogBookDTO().setFlagStatus("N");
            this.getModel().setMrfMasterList(
                    mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        } else {
            for (int i = 0; i < mrfDetails.size(); i++) {
                if (mrfDetails.get(i)[3] != null)
                    ResidentialCount = ResidentialCount.add((BigInteger) mrfDetails.get(i)[3]);
                if (mrfDetails.get(i)[1] != null)
                    commercialCount = commercialCount.add((BigInteger) mrfDetails.get(i)[1]);
                if (mrfDetails.get(i)[2] != null)
                    commercialShopsCount = commercialShopsCount.add((BigInteger) mrfDetails.get(i)[2]);
                if (mrfDetails.get(i)[4] != null)
                    SwardNo = mrfDetails.get(i)[4] + MainetConstants.operator.COMA + SwardNo;
                if (mrfDetails.get(i)[5] != null)
                    EwardNo = mrfDetails.get(i)[5] + MainetConstants.operator.COMA + EwardNo;
            }

            this.getModel().getVehicleLogBookDTO().setCommercialCount(commercialCount.longValue());
            this.getModel().getVehicleLogBookDTO().setResidentialCount(ResidentialCount.longValue());
            this.getModel().getVehicleLogBookDTO().setCommercialShopsCount(commercialShopsCount.longValue());
            this.getModel().getVehicleLogBookDTO().setWardNo(SwardNo.trim() + MainetConstants.operator.COMA + EwardNo.trim());
            this.getModel().getVehicleLogBookDTO().setDate(date);
            this.getModel().getVehicleLogBookDTO().setFlagStatus("Y");
            this.getModel().setMRFMasterDto(mRFMasterService.getPlantNameByPlantId(mrfId));
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME,
                this.getModel());
    }

}
