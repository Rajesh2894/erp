/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AdvertiserCancellationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 25 september 2019
 */
@Controller
@RequestMapping("/AdvertiserCancellation.html")
public class AdvertiserCancellationController extends AbstractFormController<AdvertiserCancellationModel> {

    @Autowired
    IAdvertiserMasterService advertiserMasterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setAgencyLicNoAndNameList(advertiserMasterService
                .getAgenLicNoListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA));

        return index();
    }

    @ResponseBody
    @RequestMapping(params = "searchAdvertiserNameByLicNo", method = { RequestMethod.POST })
    public String searchAdvertiserNameByLicNo(@RequestParam(value = "agencyLicNo") String agencyLicNo,
            HttpServletRequest request) {
        String agencyName = null;
        AgencyRegistrationResponseDto agencyRegDto = advertiserMasterService.getAgencyDetailByLicnoAndOrgId(agencyLicNo,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (agencyRegDto.getMasterDto() != null) {
            if (StringUtils.equals(agencyRegDto.getMasterDto().getAgencyStatus(), MainetConstants.FlagA)) {
                agencyName = agencyRegDto.getMasterDto().getAgencyName();
                this.getModel().setAdvertiserDto(agencyRegDto.getMasterDto());
            } else {
                agencyName = MainetConstants.FlagN;
            }

        }
        return agencyName;

    }

    @ResponseBody
    @RequestMapping(params = "saveAdvertiserCancellation", method = RequestMethod.POST)
    public Map<String, Object> saveAdvertiserCancellation(HttpServletRequest request) {
        getModel().bind(request);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        if (!this.getModel().saveForm()) {
            object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        }

        return object;

    }
}
