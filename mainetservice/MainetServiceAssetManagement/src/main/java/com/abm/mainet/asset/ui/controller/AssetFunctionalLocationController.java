package com.abm.mainet.asset.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;
import com.abm.mainet.asset.ui.model.AssetFunctionalLocationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(MainetConstants.AssetManagement.ASSET_FUNCTIONAL_LOCATION_URL)
public class AssetFunctionalLocationController extends AbstractFormController<AssetFunctionalLocationModel> {

    /**
     * It gets called when search home page loads
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
        List<AssetFunctionalLocationDTO> funcLocDTOList = this.getModel().filterFuncLocCodeList(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setCommonHelpDocs("AssetFunctionalLocation.html");
        model.addAttribute("funcLocDTOList_Search", funcLocDTOList);
        return index();
    }

    /**
     * Method gets called when the object when clicked on search criteria
     * @param funcLocCode
     * @param parentFuncLocCode
     * @return if record found else return empty dto
     */
    @RequestMapping(params = "validateFuncLocCode", method = { RequestMethod.POST })
    public ModelAndView validateFuncLocCode(@RequestParam("funcLocCode") String funcLocCode,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {

        bindModel(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean dupFlag = this.getModel().isDuplicate_FuncLocCode(funcLocCode, orgId);

        String respMsg = "";

        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("asset.functional.location.vldnn.duplicate.code");
        }

        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }

    /**
     * Method gets called when the object wnen clicked on search criteria
     * 
     * @param funcLocCode
     * @param parentFuncLocCode
     * @return List of records when an ajax call is made
     */
    @RequestMapping(params = MainetConstants.AssetManagement.FILTER_FUNC_Code, method = { RequestMethod.POST })
    public @ResponseBody List<AssetFunctionalLocationDTO> filterSearchData(final HttpServletRequest request,
            @RequestParam(value = "funcLocCode", required = false) final String funcLocCode,
            @RequestParam(value = "description", required = false) final String description) {
        List<AssetFunctionalLocationDTO> funcLocDTOList = this.getModel().filterFuncLocCodeList(funcLocCode, description,
                UserSession.getCurrent().getOrganisation().getOrgid());

        return funcLocDTOList;
    }

    /**
     * used for create Chart Of Depreciation Master details form
     * 
     * @param funcLocCode
     * @param modeType
     * @return
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView cdmForm(
            @RequestParam(value = "funcLocId", required = false) Long funcLocId,
            @RequestParam(value = "type", required = false) String type) {
        AssetFunctionalLocationModel flcModel = this.getModel();
        populateModel(this.getModel(), funcLocId, type);
        return new ModelAndView(MainetConstants.AssetManagement.ASSET_FUNCTIONAL_LOCATION_FORM, MainetConstants.FORM_NAME,
                flcModel);
    }

    /**
     * populate common details
     * 
     * @param flcModel
     * @param funcLocCode
     * @param modeType
     */
    private void populateModel(final AssetFunctionalLocationModel flcModel, final Long funcLocId, final String mode) {

        if (mode.equals(MainetConstants.MODE_CREATE)) {
            flcModel.setModeType(mode);
            flcModel.intializeObject();
        } else {
            if (mode.equals(MainetConstants.MODE_EDIT)) {
                flcModel.setModeType(MainetConstants.MODE_EDIT);

            } else {
                flcModel.setModeType(MainetConstants.MODE_VIEW);
            }

            flcModel.intializeObject();
            flcModel.getFuncLocCode(funcLocId, UserSession.getCurrent().getOrganisation().getOrgid());
        }
    }

}
