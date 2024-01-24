package com.abm.mainet.rnl.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.RnLCommon;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.ui.model.ContractAgreementRenewalModel;

@Controller
@RequestMapping(MainetConstants.RnLCommon.CONTRACT_RENEWAL_URL)
public class ContractAgreementRenewalController extends AbstractFormController<ContractAgreementRenewalModel> {
    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private IContractAgreementService contractAgreementService;

    @Autowired
    private IEstateContractMappingService iEstateContractMappingService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private IEstateService iEstateService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(request);
        this.getModel().setCommonHelpDocs(RnLCommon.CONTRACT_RENEWAL_URL);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // fetch list based on cont_close_flag : N only
        List<ContractAgreementSummaryDTO> contractSummaryList = ApplicationContextProvider.getApplicationContext()
                .getBean(IContractAgreementService.class)
                .getContractAgreementSummaryData(orgId, null, null, null, null, "N", "Y");
        // filter based on
        // this.getModel().setContractNoList(contractNoList);
        this.getModel().setDepartmentList(iTbDepartmentService.findMappedDepartments(orgId));

        // re-filter based on contractId is present in contract mapping or not
        List<ContractAgreementSummaryDTO> contractRenewalList = new ArrayList<>();
        for (ContractAgreementSummaryDTO summaryDTO : contractSummaryList) {
            EstateContMappingDTO contratctMappingDto = iEstateContractMappingService.findByContractId(summaryDTO.getContId());
            if (!contratctMappingDto.getContractPropListDTO().isEmpty()) {
                contractRenewalList.add(summaryDTO);
            }
        }

        try {
            this.getModel().setSummaryDTOList(contractRenewalList);
        } catch (Exception ex) {

            throw new FrameworkException("Exception while Contract Summary Data : " + ex.getMessage());
        }
        // fetch vendor list from vendor master
        final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        List<TbAcVendormaster> vendorList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbAcVendormasterService.class)
                .getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
        model.addAttribute("vendorList", vendorList);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "filterData", method = RequestMethod.POST)
    public List<ContractAgreementSummaryDTO> getGridFilterData(
            @RequestParam(value = "contractNo") final String contractNo,
            @RequestParam(value = "contractDate") final String contractDate,
            @RequestParam(value = "vendorId") final Long vendorId,
            @RequestParam(value = "departmentId") final Long departmentId) {
        List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO = new ArrayList<>();
        try {
            contractAgreementSummaryDTO = contractAgreementService.getContractAgreementSummaryData(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    contractNo, contractDate, departmentId, vendorId, "N", "Y"/* renewal */);// fetch list based on
                                                                                             // cont_close_flag : N only
        } catch (Exception e) {
            throw new FrameworkException("Problem in getGridData", e);
        }
        List<ContractAgreementSummaryDTO> contractRenewalList = new ArrayList<>();
        for (ContractAgreementSummaryDTO summaryDTO : contractAgreementSummaryDTO) {
            EstateContMappingDTO contratctMappingDto = iEstateContractMappingService.findByContractId(summaryDTO.getContId());
            if (!contratctMappingDto.getContractPropListDTO().isEmpty()) {
                contractRenewalList.add(summaryDTO);
            }
        }
        return contractRenewalList;
    }

    @RequestMapping(params = "form", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView formForCreate(final HttpServletRequest request,
            @RequestParam(value = "contId", required = false) final Long contId,
            @RequestParam(value = "type", required = false) final String modeType,
            @RequestParam(value = "showForm", required = false) final String showForm) {
        bindModel(request);
        final ContractAgreementRenewalModel contractAgreementRenewalModel = getModel();
        populateModel(contId,
                contractAgreementRenewalModel, modeType, showForm, request);
        // D#82826 check here mode is Edit than don't pass installment record
        if (modeType.equals(MainetConstants.MODE_EDIT)) {
            contractAgreementRenewalModel.getContractMastDTO().getContractInstalmentDetailList().clear();
        }

        return new ModelAndView("ContractAgreementRenewalForm",
                MainetConstants.FORM_NAME, contractAgreementRenewalModel);
    }

    private void populateModel(final Long contId, final ContractAgreementRenewalModel contractAgreementModel,
            final String modeType, final String showForm, HttpServletRequest request) {
        if (contId == null) {
            contractAgreementModel.setContractMastDTO(new ContractMastDTO());
            contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
        } else {
            contractAgreementModel.setContractMastDTO(
                    contractAgreementService.findById(contId, UserSession.getCurrent().getOrganisation().getOrgid()));
            contractAgreementModel.getContractMastDTO()
                    .setContractType(CommonMasterUtility.findLookUpCode(PrefixConstants.CONTRACT_TYPE,
                            UserSession.getCurrent().getOrganisation().getOrgid(),
                            contractAgreementModel.getContractMastDTO().getContType()));
            if (contractAgreementModel.getContractMastDTO().getContractDetailList().get(0).getContAmount() != null)
                contractAgreementModel
                        .getContractMastDTO().getContractDetailList().get(0)
                        .setContractAmt(BigDecimal.valueOf(contractAgreementModel.getContractMastDTO()
                                .getContractDetailList().get(0).getContAmount()).setScale(2, BigDecimal.ROUND_UP));
            final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE
                            + contractAgreementModel.getContractMastDTO().getContId());
            this.getModel().setAttachDocsList(attachDocs);
            if (MainetConstants.RnLCommon.MODE_VIEW.equals(modeType)) {
                contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
                // contractAgreementModel.setShowForm(showForm);

            }
            if (MainetConstants.RnLCommon.MODE_EDIT.equals(modeType)) {
                contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_EDIT);
                // contractAgreementModel.setShowForm(showForm);
            }
            // D#138342 set property no and property name
            EstateContMappingDTO estateContMappingDTO = iEstateContractMappingService.findByContractId(contId);
            if (estateContMappingDTO != null && !estateContMappingDTO.getContractPropListDTO().isEmpty()) {
                contractAgreementModel.getContractMastDTO()
                        .setPropertyNo(estateContMappingDTO.getContractPropListDTO().get(0).getPropertyNo());
                contractAgreementModel.getContractMastDTO()
                        .setPropName(estateContMappingDTO.getContractPropListDTO().get(0).getPropName());
                contractAgreementModel.getContractMastDTO()
                        .setPropId(estateContMappingDTO.getContractPropListDTO().get(0).getPropId());
            }

        }

    }

    @RequestMapping(params = "saveForm", method = RequestMethod.POST)
    @Transactional
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        if (getModel().saveForm()) {
            /*
             * if (getModel().getModeType().equals(MainetConstants.CommonConstants.C)) { return
             * jsonResult(JsonViewObject.successResult(
             * ApplicationSession.getInstance().getMessage(MainetConstants.ContractAgreement.CONTRACT_CREATE) +
             * MainetConstants.WHITE_SPACE + getModel().getContractMastDTO().getContNo())); } else if
             * (getModel().getModeType().equals(MainetConstants.CommonConstants.E)) { return
             * jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance()
             * .getMessage(MainetConstants.ContractAgreement.CONTRACT_UPDATE) + MainetConstants.WHITE_SPACE +
             * getModel().getContractMastDTO().getContNo() + MainetConstants.WHITE_SPACE +
             * ApplicationSession.getInstance().getMessage(MainetConstants.ContractAgreement.CONTRACT_MSG))); }
             */

            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("contract.renewal.saved.success")
                            + MainetConstants.WHITE_SPACE + getModel().getContractMastDTO().getContNo()));
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "viewPropertyDet", method = RequestMethod.POST)
    public ModelAndView viewPropertyDet(@RequestParam(value = "propId", required = false) Long propId,
            @RequestParam(value = "taskId", required = false) Long taskId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        // fileUpload.sessionCleanUpForFileUpload();
        ContractAgreementRenewalModel contractAgreementModel = this.getModel();
        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        contractAgreementModel.setUsageType(CommonMasterUtility.getLevelData("USA", 1, organisation));
        final List<Object[]> list = iEstateService
                .findEstateRecordsForProperty(UserSession.getCurrent().getOrganisation().getOrgid());
        contractAgreementModel.setEstateMasters(list);

        if ((list != null) && !list.isEmpty()) {
            for (final Object[] obj : list) {
                contractAgreementModel.getCodeMap().put(Long.valueOf(String.valueOf(obj[0])), String.valueOf(obj[1]));
            }
        }
        final EstatePropMaster estatePropMaster = iEstatePropertyService
                .findEstatePropWithDetailsById(contractAgreementModel.getContractMastDTO().getPropId());
        estatePropMaster.setEstatecode(getModel().getCodeMap().get(estatePropMaster.getEstateId()));

        contractAgreementModel.setEstatePropMaster(estatePropMaster);
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                contractAgreementModel.getEstatePropMaster().getCode());
        contractAgreementModel.setDocumentList(attachDocs);
        contractAgreementModel.setSaveMode("VIEW_PROP");
        contractAgreementModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
        contractAgreementModel.getEstatePropMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_VIEW);
        return new ModelAndView(MainetConstants.EstateProMaster.ESTATE_PROP_MAS_FORM, MainetConstants.FORM_NAME,
                contractAgreementModel);
    }
}
