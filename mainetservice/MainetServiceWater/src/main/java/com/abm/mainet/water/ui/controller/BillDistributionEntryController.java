package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.BillDistributionEntryModel;

@Controller
@RequestMapping("BillDistributionForm.html")
public class BillDistributionEntryController extends AbstractFormController<BillDistributionEntryModel> {

    @Autowired
    private NewWaterConnectionService newWaterConnectionService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        final BillDistributionEntryModel model = getModel();
        this.getModel().setCommonHelpDocs("BillDistributionForm.html");
        model.setBillCcnType(MainetConstants.MENU.M);
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachBillDistributionData")
    public ModelAndView serachBillDistributionData(final HttpServletRequest request) {
        bindModel(request);
        final BillDistributionEntryModel model = getModel();
        final List<TbBillMas> billMas = newWaterConnectionService.fetchBillsForDistributionEntry(model.getEntity(),
                model.getBillCcnType(),
                model.getDistriutionType(), model.getConnectionNo(), UserSession.getCurrent().getOrganisation().getOrgid());
        model.setBillMas(billMas);
        return defaultResult();
    }

}
