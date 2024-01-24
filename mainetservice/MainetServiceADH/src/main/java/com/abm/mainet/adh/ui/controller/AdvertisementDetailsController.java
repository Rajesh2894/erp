/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AdvertisementDetailsModel;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 *
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/AdvertisementDetails.html")
public class AdvertisementDetailsController extends AbstractFormController<AdvertisementDetailsModel> {

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;
    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        List<AdvertiserMasterDto> advertiserMasterDtoList = advertiserMasterService.searchAdvertiserMasterData(
                UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null);
        if (CollectionUtils.isNotEmpty(advertiserMasterDtoList)) {
            this.getModel().setAdvertiserMasterDtoList(advertiserMasterDtoList);
        }
        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
        this.getModel().setLocationList(locationList);
        this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
        return index();
    }
}
