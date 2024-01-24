package com.abm.mainet.landEstate.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.landEstate.service.ILandAcquisitionService;
import com.abm.mainet.landEstate.ui.model.LOIPayableModel;

@Controller
@RequestMapping("LOIPayable.html")
public class LOIPayableController extends AbstractFormController<LOIPayableModel> {

    @Autowired
    ILandAcquisitionService acquisitionService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView populateViewData(@RequestParam final long appNo, @RequestParam("actualTaskId") final long actualTaskId,
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        if (appNo != 0) {
            final LOIPayableModel model = getModel();
            model.setTaskId(actualTaskId);
            final LoiPaymentSearchDTO searchdata = model.getSearchDto();
            searchdata.setApplicationId(appNo);
            final boolean result = model.getLoiData();
            getModel().setPageUrl(MainetConstants.FlagC);
            if (!result) {
                getModel().addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
            }
        }
        // get data from TB_EST_AQUISN by appId
        getModel().setAcquisitionDto(acquisitionService.getLandAcqProposalByAppId(appNo));
        ModelAndView mv = new ModelAndView("LoiPayable", MainetConstants.CommonConstants.COMMAND, getModel());

        // fetch vendor list from vendor master
        final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbAcVendormasterService.class)
                .getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE + MainetConstants.WINDOWS_SLASH
                                + getModel().getAcquisitionDto().getLnaqId());
        getModel().setAttachDocsList(attachDocs);
        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
        getModel().setLocationList(locationList);
        mv.addObject("vendorList", vendorList);
        return mv;
    }

}
