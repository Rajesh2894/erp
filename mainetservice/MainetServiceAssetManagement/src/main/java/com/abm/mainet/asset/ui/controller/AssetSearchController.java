package com.abm.mainet.asset.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.asset.ui.model.AssetSearchModel;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping(value = { "/AssetSearch.html", "ITAssetSearch.html" })
public class AssetSearchController extends AbstractFormController<AssetSearchModel> {

    @Autowired
    private IAssetInformationService service;

    @Autowired
    private ILocationMasService iLocationMasService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        searchDTO.setLangId(UserSession.getCurrent().getLanguageId());
        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String smShortDesc = httpServletRequest.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, httpServletRequest.getRequestURI(), "ITAssetSearch.html"));
        // get AST(asset status) default value
        // D#38246
        Long defaultAssetStatusId = CommonMasterUtility
                .getDefaultValueByOrg(PrefixConstants.ASSET_PREFIX.ASEET_STATUS, UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        searchDTO.setAssetStatusId(defaultAssetStatusId);
        searchDTO.setModuleDeptCode(UserSession.getCurrent().getModuleDeptCode());
        List<SummaryDTO> summaryDTOList = this.getModel().search(searchDTO);
        this.getModel().setBarcodeList(summaryDTOList);
        this.getModel().setEmpList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
                .getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        model.addAttribute("summaryDTOList", summaryDTOList);
        this.getModel().setLocList(
                iLocationMasService.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
       this.getModel().setOrgname(UserSession.getCurrent().getOrganisation().getOrgShortNm());
        return new ModelAndView("AssetSearch", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "filterAsset", method = { RequestMethod.POST })
    public @ResponseBody List<SummaryDTO> filterAsset(
            @RequestParam("deptId") Long deptId, @RequestParam("assetClass1") Long assetClass1,
            @RequestParam("assetClass2") Long assetClass2, @RequestParam("astSerialNo") String astSerialNo,
            /* @RequestParam ("astModelId") String astModelId,@RequestParam ("subId") String subId, */
            @RequestParam("assetStatusId") Long assetStatusId, /* @RequestParam ("astInventId") Long astInventId, */
            @RequestParam("locationId") Long locationId, @RequestParam("acquisitionMethodId") Long acquisitionMethodId,
            @RequestParam("employeeId") Long employeeId, @RequestParam("astAppNo") String astAppNo,
            @RequestParam("roadName") String roadName, @RequestParam("pincode") Long pincode) {
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setDeptId(deptId);
        searchDTO.setAssetClass1(assetClass1);
        searchDTO.setAssetClass2(assetClass2);
        searchDTO.setAstSerialNo(astSerialNo);
        /*
         * searchDTO.setAstModelId(astModelId); searchDTO.setSubId(subId);
         */
        // searchDTO.setCostCenter(costCenter);
        searchDTO.setAssetStatusId(assetStatusId);
        /* searchDTO.setAstInventId(astInventId); */
        searchDTO.setAcquisitionMethodId(acquisitionMethodId);
        searchDTO.setEmployeeId(employeeId);
        searchDTO.setLocationId(locationId);
        searchDTO.setAstAppNo(astAppNo);
        searchDTO.setRoadName(roadName);
        searchDTO.setPincode(pincode);
        searchDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        searchDTO.setLangId(UserSession.getCurrent().getLanguageId());
        searchDTO.setModuleDeptCode(UserSession.getCurrent().getModuleDeptCode());
        List<SummaryDTO> summaryDTOList = this.getModel().filterAsset(searchDTO);
        this.getModel().setBarcodeList(summaryDTOList);
        return summaryDTOList;
    }

    @RequestMapping(params = "viewBarcode", method = { RequestMethod.POST })
    public ModelAndView viewBarcode(final Model model, final HttpServletRequest request) {
        ModelAndView mv = null;
        bindModel(request);
        AssetSearchModel assetSearchModel = this.getModel();
        BindingResult bindingResult = assetSearchModel.getBindingResult();
        List<SummaryDTO> imageList = new ArrayList<>();
        try {
            imageList = assetSearchModel.barcodeGenerate();
        } catch (FrameworkException ex) {
            if (ex.getErrCode() != null && ex.getErrCode().equals(MainetConstants.AssetManagement.ASSET_LIMIT_ERROR_CODE)) {
                bindingResult.addError(
                        new ObjectError(MainetConstants.BLANK, ApplicationSession.getInstance().getMessage("ast.limit.check")));
            } else {
                throw ex;
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            index(model, request);
            mv = new ModelAndView("AssetSearch", MainetConstants.FORM_NAME, this.getModel());
        } else {
            assetSearchModel.setBarcodeList(imageList);
            mv = new ModelAndView("viewBarcodeList", MainetConstants.FORM_NAME, this.getModel());
        }
        return mv;
    }

    @RequestMapping(params = "barcodeImg", method = { RequestMethod.GET })
    public void getBarcodeImg(@QueryParam("assetId") Long assetId, HttpServletResponse response) throws Exception {
        AssetSearchModel assetSearchModel = this.getModel();
        List<SummaryDTO> assetList = assetSearchModel.barcodeGenerate();
        if (assetId == null) {
            return;
        }
        for (SummaryDTO dto : assetList) {
            if (dto.getAstId().longValue() == assetId.longValue() && dto.getBufferImage() != null) {
                ServletOutputStream os = response.getOutputStream();
                ImageIO.write(dto.getBufferImage(), "png", os);
                os.close();
            }
        }

    }

    @RequestMapping(params = "searchAsset", method = RequestMethod.POST)
    public ModelAndView astSearchForm(final HttpServletRequest request,
            final Model model) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        String smShortDesc = request.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, request.getRequestURI(), "ITAssetSearch.html"));
        searchDTO.setModuleDeptCode(UserSession.getCurrent().getModuleDeptCode());
        searchDTO.setLangId(UserSession.getCurrent().getLanguageId());
        List<SummaryDTO> summaryDTOList = this.getModel().search(searchDTO);
        model.addAttribute("summaryDTOList", summaryDTOList);
        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        return new ModelAndView("AssetSearchForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getAssetSummary", method = { RequestMethod.POST })
    public @ResponseBody List<SummaryDTO> getAsset(@RequestParam("assetIds") String assetIds) {
        final List<SummaryDTO> list = new ArrayList<>();
        List<Long> assetIdList = null;
        String idList = assetIds;
        if (idList != null && !idList.isEmpty()) {
            assetIdList = new ArrayList<>();
            String fileArray[] = idList.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                assetIdList.add(Long.valueOf(fields));
            }
        }
        assetIdList.forEach(id -> list.add(service.getSummaryByAssetId(id)));
        return list;
    }
}