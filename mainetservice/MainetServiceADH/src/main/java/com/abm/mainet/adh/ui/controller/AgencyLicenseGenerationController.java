
package com.abm.mainet.adh.ui.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 04 November 2019
 */
@Controller
@RequestMapping("/AgencyLicenseGeneration.html")
public class AgencyLicenseGenerationController extends AbstractFormController<AgencyRegistrationModel> {

    @Autowired
    IAdvertiserMasterService advertiserMasterService;

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
    public ModelAndView executeAgencyRegistration(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
            @RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
            final HttpServletRequest request) {

        getModel().bind(request);
        // D#40966
        advertiserMasterService.processTheAgencyLicenseGenerationTask(UserSession.getCurrent().getOrganisation(),
                applicationId, actualTaskId, this.getModel());
        // D#126605
        List<TbLoiMas> loiMasList = ApplicationContextProvider.getApplicationContext().getBean(TbLoiMasService.class)
                .getloiByApplicationId(applicationId, Long.valueOf(taskId),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        if (CollectionUtils.isNotEmpty(loiMasList)) {
            TbLoiMas loiMas = loiMasList.get(loiMasList.size() - 1);
            if (loiMas != null && loiMas.getLoiRemark() != null) {
                String loiRemark = loiMas.getLoiRemark();
                List<String> remarkList = Stream.of(loiRemark.split(",")).map(rem -> new String(rem))
                        .collect(Collectors.toList());
                this.getModel().setLoiRemarkList(remarkList);
            }
        }// END
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.PRINT_AGENCY_LIC_LETTER,
                MainetConstants.FORM_NAME, this.getModel());

    }

}
