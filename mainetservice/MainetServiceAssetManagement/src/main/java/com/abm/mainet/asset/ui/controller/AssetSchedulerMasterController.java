/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.asset.service.IDepreciationCalculationDetailsService;
import com.abm.mainet.asset.ui.dto.AstSchedulerMasterDTO;
import com.abm.mainet.asset.ui.model.AssetSchedulerModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(MainetConstants.AssetManagement.ASSET_SCHEDULER_MAS_URL)
public class AssetSchedulerMasterController extends AbstractFormController<AssetSchedulerModel> {

    /**
     * 
     */

    @Autowired
    private IDepreciationCalculationDetailsService depcalservice;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        List<LookUp> lookupList = CommonMasterUtility.getListLookup("ACL", UserSession.getCurrent().getOrganisation());
        model.addAttribute("assetClassAST", lookupList);
        return index();
    }

    @RequestMapping(params = "calculateDeprecation", method = { RequestMethod.POST })
    public ModelAndView calculateDeprecation(@RequestParam("assetCode") final String assetCode,@RequestParam("assetDateField") final Date assetDateField,
            final HttpServletRequest request) {
        bindModel(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        AuditDetailsDTO auditDTO = new AuditDetailsDTO();
        auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
        AstSchedulerMasterDTO dto = depcalservice.getAssetWithDepreciation(orgId, assetCode, auditDTO,assetDateField);
        AssetSchedulerModel model = this.getModel();
        model.setAstschDto(dto);
        return new ModelAndView("AssetScheduler", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "calculateDepByAssetClass", method = { RequestMethod.POST })
    public ModelAndView calculateDepByAssetClass(@RequestParam("assetClass") final Long assetClass,@RequestParam("assetDateField") final Date assetDateField,
            final HttpServletRequest request) {
        bindModel(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        AuditDetailsDTO auditDTO = new AuditDetailsDTO();
        auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
        AstSchedulerMasterDTO dto = depcalservice.getAssetDepreciationByClass(orgId, assetClass, auditDTO,assetDateField);
        AssetSchedulerModel model = this.getModel();
        model.setAstschDto(dto);
        return new ModelAndView("AssetScheduler", MainetConstants.FORM_NAME, this.getModel());

    }
}