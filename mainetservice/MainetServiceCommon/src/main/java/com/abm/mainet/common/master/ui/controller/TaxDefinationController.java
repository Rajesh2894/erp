package com.abm.mainet.common.master.ui.controller;

import java.util.ArrayList;
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
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.ITaxDefinationService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.master.ui.model.TaxDefinationModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Controller
@RequestMapping("/TaxDefination.html")
public class TaxDefinationController extends AbstractFormController<TaxDefinationModel> {

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ITaxDefinationService definationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("TaxDefination.html");
        this.getModel().setTaxList(getTaxList());
        this.getModel().setTaxDefinationList(
                definationService.getTaxDefinationList(UserSession.getCurrent().getOrganisation().getOrgid()));
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.OPEN_TAXDEF_FORM)
    public ModelAndView openTaxDefinationForm(
            @RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
            final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setSaveMode(mode);
        this.getModel().setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.CommonMasterUi.VTY,
                UserSession.getCurrent().getOrganisation()));
        return new ModelAndView(MainetConstants.TAXDEF_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * Used to check For Taxes Definition is defined or not
     * 
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.CHECK_TAXES, method = RequestMethod.POST)
    public String checkProjectDefinedOrNot(final HttpServletRequest httpServletRequest) {
        String defaultVal = MainetConstants.FlagN;
        List<Long> taxIds = new ArrayList<>();
        List<LookUp> taxesList = new ArrayList<>();
        this.getModel().getTaxDefinationList().clear();
        List<TaxDefinationDto> dtos = definationService
                .getTaxDefinationList(UserSession.getCurrent().getOrganisation().getOrgid());

        for (TaxDefinationDto dto : dtos) {
            taxIds.add(dto.getTaxId());
        }
        for (LookUp lookUp : getTaxList()) {
            if (!taxIds.contains(lookUp.getLookUpId())) {
                defaultVal = MainetConstants.FlagY;
                taxesList.add(lookUp);
            }
        }
        this.getModel().setTaxList(taxesList);
        return defaultVal;

    }

    public List<LookUp> getTaxList() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<LookUp> allTaxList = CommonMasterUtility.getNextLevelData(MainetConstants.CommonMasterUi.TAC, 1,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long tdsId = null;
        for (LookUp lookUp : allTaxList) {
            if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
                tdsId = lookUp.getLookUpId();
            }
        }
        List<LookUp> taxList = new ArrayList<>();
        LookUp lookUpObj = null;
        List<TbTaxMas> tdsList = tbTaxMasService.findAllByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        for (TbTaxMas tbTaxMas : tdsList) {

            if (tdsId != null && tbTaxMas.getTaxCategory1().longValue() == tdsId.longValue()) {
                lookUpObj = new LookUp();
                lookUpObj.setLookUpId(tbTaxMas.getTaxId());
                lookUpObj.setLookUpCode(tbTaxMas.getTaxCode());
                lookUpObj.setDescLangFirst(tbTaxMas.getTaxDesc());
                LookUp obj = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(tbTaxMas.getTaxDescId(), orgId,
                        MainetConstants.PG_REQUEST_PROPERTY.TXN);
                // #120829
               if(obj != null) { 
                if (obj.getOtherField() != null && !obj.getOtherField().isEmpty()) {
                    lookUpObj.setOtherField(obj.getOtherField());
                } else {
                    lookUpObj.setOtherField(MainetConstants.BLANK);
                }
               }               
                taxList.add(lookUpObj);
               
            }
        }
        return taxList;
    }

    /**
     * Used to get defined Taxes in tax Definition
     * 
     * @param request
     * @param taxDefId
     * @param formMode
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.UPDATE_TAXDEF, method = RequestMethod.POST)
    public ModelAndView updateTaxDefinitionForm(final HttpServletRequest request,
            @RequestParam(MainetConstants.TAXDEF_ID) Long taxDefId,
            @RequestParam(MainetConstants.FORM_MODE) String formMode) {
        TaxDefinationModel model = this.getModel();
        model.getTaxDefinationList().clear();
        model.setSaveMode(formMode);
        model.setParenetTaxId(taxDefId);
        List<TaxDefinationDto> dto = definationService.findByAllGridSearchData(taxDefId, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!dto.isEmpty())
            this.getModel().setTaxDefinationList(dto);
        this.getModel().setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.CommonMasterUi.VTY,
                UserSession.getCurrent().getOrganisation()));
        return new ModelAndView(MainetConstants.TAXDEF_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * To Get the grid Data for Summary Page
     * 
     * @param request
     * @param taxId
     * @param panCard
     * @return
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.SEARCH_TAXDEF, method = RequestMethod.POST)
    public List<TaxDefinationDto> searchTaxDefinationForm(final HttpServletRequest request,
            @RequestParam(MainetConstants.TAX_ID) Long taxId, @RequestParam(MainetConstants.PAN_CARD) String panCard) {
        List<TaxDefinationDto> dtos = definationService.findByAllGridSearchData(taxId, panCard,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return dtos;
    }

}
