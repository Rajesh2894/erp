/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.mapper.BasicInformationMapper;
import com.abm.mainet.asset.mapper.ClassificationInfoMapper;
import com.abm.mainet.asset.mapper.DepreciationChartMapper;
import com.abm.mainet.asset.mapper.InsuranceServiceMapper;
import com.abm.mainet.asset.mapper.LeasingCompanyMapper;
import com.abm.mainet.asset.mapper.LinearAssetMapper;
import com.abm.mainet.asset.mapper.PurchaseInfoMapper;
import com.abm.mainet.asset.mapper.RealEstateInfoMapper;
import com.abm.mainet.asset.service.IAssetFunctionalLocationMasterService;
import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.service.IChartOfDepreciationMasterService;
import com.abm.mainet.asset.service.IInformationService;
import com.abm.mainet.asset.service.IMaintenanceService;
import com.abm.mainet.asset.ui.dto.AssetRequisitionDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterBulkDTO;
import com.abm.mainet.asset.ui.dto.ReadExcelData;
import com.abm.mainet.asset.ui.model.AssetRegisterUploadModel;
import com.abm.mainet.asset.ui.model.AssetRegistrationModel;
import com.abm.mainet.asset.ui.model.AssetRequisitionModel;
import com.abm.mainet.asset.ui.validator.AssetAcquisitionValidator;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dms.dao.ICFCAttachmentDAO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_REGISTRATION_URL, "ITAssetRegistration.html" })
public class AssetRegistrationController extends AbstractFormController<AssetRegistrationModel> {

    @Resource
    private TbDepartmentService iTbDepartmentService;

    @Resource
    private TbAcVendormasterService vendorMasterService;

    @Resource
    private IAttachDocsService attachDocsService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IChartOfDepreciationMasterService cdmService;

    @Autowired
    private IAssetInformationService astService;

    @Autowired
    private IAssetFunctionalLocationMasterService assetFuncLocMasterService;

    @Autowired
    private IInformationService infoService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private SecondaryheadMasterService shmService;

    @Autowired
    private ICFCAttachmentDAO cfcAttachmentDAO;

    private static final String ASSET_INFORMATION = "AssetInformation";
    private static final String ASSET_CLASSIFICATION = "AssetClassification";
    private static final String ASSET_PURCHASE = "AssetPurchaserInformation";
    private static final String ASSET_REAL_ESTATE = "AssetRealEstateInformation";
    private static final String ASSET_SERVICE = "AssetServiceInformation";
    private static final String ASSET_INSURANCE = "AssetInsuranceDetail";
    private static final String ASSET_LEASE = "AssetLeasingCompany";
    private static final String ASSET_DEPRECIATION = "AssetDepriciationChart";
    private static final String ASSET_LINEAR = "AssetLinear";
    private static final String ASSET_DOC_DETAIL = "AssetDocumentDetails";
    private static final String ASSET_RESET_OPTION = "resetOption";
    private static final String RESET = "reset";
    private static final String IT_ASSET_MAIN = "ITAssetMain";

    /**
     * used for showing default home page for Asset Search
     * 
     * @param httpServletRequest
     * @return default view
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final AssetRegistrationModel registerModel = this.getModel();
        registerModel.setCommonHelpDocs("AssetSearch.html");
        registerModel.setDepartmentsList(iTbDepartmentService.findAll());
        registerModel.setVendorList(vendorMasterService.getActiveVendors(orgId, vendorStatus));
        return index();
    }

    /**
     * used for create Asset Registration form
     * 
     * @param groupId
     * @param modeType
     * @return
     */

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView astRegForm(
            @RequestParam(value = MainetConstants.AssetManagement.AST_ID, required = false) Long assetId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            @RequestParam(value = "saveMode", required = false) String saveMode, final Model model,
            HttpServletRequest request) {
        // sessionCleanup(request);
        AssetRegistrationModel astModel = this.getModel();
        astModel.setCommonHelpDocs("AssetSearch.html");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        astModel.setEmpList(employeeService.getAllEmployee(orgId));
        astModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        astModel.setSerailNoSet(infoService.getReferenceAsset(orgId));
        this.getModel().setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        /* this will check account is live or not */
        astModel.setAccountIsActiveOrNot(maintenanceService.checkAccountActiveOrNot());
        if (saveMode != null && !saveMode.trim().isEmpty()) {
            astModel.setSaveMode(saveMode);
        }

        populateModel(astModel, assetId, type, model);
        Organisation org = UserSession.getCurrent().getOrganisation();
        LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                "MLI", org);
        String GISURL = ServiceEndpoints.GisItegration.GIS_URI + ServiceEndpoints.GisItegration.COMMON_GIS_LAYER_NAME;
        if (gisFlag != null) {
            astModel.setGisValue(gisFlag.getOtherField());
            astModel.setgISUri(GISURL);// default point
        }
        if (astModel.getAstDetailsDTO().getAssetInformationDTO() != null
                && UserSession.getCurrent().getModuleDeptCode() != MainetConstants.ITAssetManagement.IT_ASSET_CODE) {
            Long assetGroup = astModel.getAstDetailsDTO().getAssetInformationDTO().getAssetGroup();
            if (assetGroup != null) {
                LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        astModel.getAstDetailsDTO().getAssetInformationDTO().getAssetGroup(), orgId, "ASG");

                if (lookup != null) {
                    if ("L".equals(lookup.getLookUpCode())) {// L for Linear type assets
                        astModel.setgISUri(GISURL.replaceAll(ServiceEndpoints.GisItegration.COMMON_GIS_LAYER_NAME,
                                ServiceEndpoints.GisItegration.ASSET_LAYER_NAME));
                    }
                }
            }
        }
		/* List<AttachDocs> attachDocs = null; */
        List<AttachDocs> attachDocs = new ArrayList<AttachDocs>();
        if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
            fileUpload.sessionCleanUpForFileUpload();
            if (astModel.getModeType().equals(MainetConstants.MODE_VIEW)
                    || astModel.getModeType().equals(MainetConstants.MODE_EDIT)
                    || astModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                fileUpload.sessionCleanUpForFileUpload();
                final String astId = UserSession.getCurrent().getModuleDeptCode()
                        + MainetConstants.operator.FORWARD_SLACE
                        + astModel.getAstDetailsDTO().getAssetInformationDTO().getAssetId().toString();
				/*
				 * attachDocs = attachDocsService
				 * .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), astId);
				 */
                List<CFCAttachment> attachment = cfcAttachmentDAO.getDocumentUploadedByRefNo(astId,
						UserSession.getCurrent().getOrganisation().getOrgid());

				attachment.forEach(attach -> {
					AttachDocs attDoc = new AttachDocs();
					attDoc.setAttId(attach.getAttId());
					attDoc.setAttFname(attach.getAttFname());
					attDoc.setAttPath(attach.getAttPath());
					attDoc.setAttDate(attach.getAttDate());
					attDoc.setDmsDocName(attach.getClmDescEngl());
					attachDocs.add(attDoc);
				});
				
				astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
                astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
                List<AssetInformationDTO> infoList = infoService.findAllInformationListByGroupRefId(
                        astModel.getAstDetailsDTO().getAssetInformationDTO().getGroupRefId(), orgId);
                if (!infoList.isEmpty() && infoList != null) {
                    List<Long> assetIds = new LinkedList<Long>();
                    infoList.stream().forEach(i -> {
                        if (i.getAssetId() != null) {
                            assetIds.add(i.getAssetId());
                        }
                    });

                    astModel.getAstDetailsDTO().setAssetIds(assetIds);
                   
                }
            }

            final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final Long vendorStatus = lookUpVendorStatus.getLookUpId();
            astModel.setVendorList(
                    vendorMasterService.getActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
        }
        astModel.setApprovalProcess("N");
        switch (astModel.getModeType()) {
        case "V":
            if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
                return new ModelAndView("AssetView", MainetConstants.FORM_NAME, astModel);
            } else {
                return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);
            }

        case "E":
            if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
                return new ModelAndView("AssetEdit", MainetConstants.FORM_NAME, astModel);
            } else {
                astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
                return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);
            }

        case "D":
            // draft mode view page use with modeType=D
            astModel.setModeType(MainetConstants.AssetManagement.APPROVAL_STATUS_DRAFT);
            return new ModelAndView("AssetView", MainetConstants.FORM_NAME, astModel);
        default:
            if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
                return new ModelAndView(MainetConstants.AssetManagement.AST_REG_FORM, MainetConstants.FORM_NAME, astModel);
            } else {
                return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);

            }

        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstClsPage")
    public ModelAndView showAstClsPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        final AssetRegistrationModel model = this.getModel();

        if (maintenanceService.checkAccountActiveOrNot()) {
            model.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
        }
        // this call for cost center }

        model.setFuncLocDTOList(assetFuncLocMasterService.retriveFunctionLocationDtoListByOrgId(orgId));
        model.setDepartmentsList(iTbDepartmentService.findAll());
        model.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        AssetClassificationDTO classDTO = detailDTO.getAssetClassificationDTO();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAssetClassificationDTO(ClassificationInfoMapper.resetClassification(classDTO));
        } else {
            if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (classDTO != null) {
                    astId = classDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    detailDTO.setAssetClassificationDTO(astService.getClassification(astId));
                }
            }
        }
        return new ModelAndView(ASSET_CLASSIFICATION, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstInfoPage")
    public ModelAndView showAstInfoPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        final AssetInformationDTO infoDTO = detailDTO.getAssetInformationDTO();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setEmpList(employeeService.getAllEmployee(orgId));
        model.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        model.setSerailNoSet(infoService.getReferenceAsset(orgId));

        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAssetInformationDTO(BasicInformationMapper.resetAssetInformation(infoDTO));
        } else {
            if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (infoDTO != null) {
                    astId = infoDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    detailDTO.setAssetInformationDTO(astService.getInfo(astId));
                }
            }
        }
        return new ModelAndView(ASSET_INFORMATION, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstPurchPage")
    public ModelAndView showAstPurchPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final AssetRegistrationModel registerModel = this.getModel();
        final AssetDetailsDTO detailDTO = registerModel.getAstDetailsDTO();
        final AssetPurchaseInformationDTO purchaseDTO = detailDTO.getAssetPurchaseInformationDTO();
        registerModel.setVendorList(vendorMasterService.getActiveVendors(orgid, vendorStatus));
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAssetPurchaseInformationDTO(PurchaseInfoMapper.resetPurchaser(purchaseDTO));
        } else {
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (purchaseDTO != null) {
                    astId = purchaseDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    registerModel.getAstDetailsDTO().setAssetPurchaseInformationDTO(astService.getPurchaseInfo(astId));
                }
            }
        }
        return new ModelAndView(ASSET_PURCHASE, MainetConstants.FORM_NAME, registerModel);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstRealEstate")
    public ModelAndView showAstRealEstate(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {

        TbDepartment deptObj = null;
        List<LookUp> locList = null;
        bindModel(request);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final AssetRegistrationModel registerModel = this.getModel();
        final AssetDetailsDTO detailDTO = registerModel.getAstDetailsDTO();
        final AssetRealEstateInformationDTO realEstateDTO = new AssetRealEstateInformationDTO();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAssetRealEstateInfoDTO(RealEstateInfoMapper.resetRealEstate(realEstateDTO));
        } else {
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (realEstateDTO != null) {
                    astId = realEstateDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    registerModel.getAstDetailsDTO().setAssetRealEstateInfoDTO(astService.getRealEstateInfo(astId));
                }
            }
        }

        // this is for tax zone code if property is integrated
        deptObj = iTbDepartmentService.findDeptByCode(orgid, MainetConstants.FlagA,
                MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        if (deptObj != null) {
            List<LocationMasEntity> location = iLocationMasService.findAllLocationWithOperationWZMapping(orgid,
                    deptObj.getDpDeptid());
            // it will check list is null or not and after that it will convert list of
            // object into list of lookUp
            locList = location.stream().filter(loc -> loc != null).map(loc -> {
                final LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                return lookUp;
            }).collect(Collectors.toList());
        }
        registerModel.setLocation(locList);
        // D#72263 check propertyRegistrationNo value based on this set true/false
        if (registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO() != null && !StringUtils
                .isEmpty(registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO().getPropertyRegistrationNo())) {
            registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO().setIsRealEstate(true);
        }
        return new ModelAndView(ASSET_REAL_ESTATE, MainetConstants.FORM_NAME, registerModel);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstInfoPage")
    public ModelAndView saveAstInfoPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        registerModel.setEmpList(employeeService.getAllEmployee(orgId));
        registerModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        ModelAndView mv = null;
        validateConstraints(astDTO.getAssetInformationDTO(), AssetInformationDTO.class, bindingResult);
        if (astDTO.getAssetInformationDTO().getAcquisitionMethod() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetAcquisitionMethod")));
        }
        if (astDTO.getAssetInformationDTO().getAssetStatus() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.statusType")));
        }
        if (astDTO.getAssetInformationDTO().getAssetClass1() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetClassification")));
        }
        if (astDTO.getAssetInformationDTO().getAssetClass2() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetClass")));
        }
        /*
         * if (astDTO.getAssetInformationDTO().getAssetGroup() == 0) { bindingResult.addError(new
         * ObjectError(MainetConstants.BLANK, ApplicationSession.getInstance().getMessage("asset.vldnn.assetGroup"))); }
         */ if (StringUtils.isNotBlank(astDTO.getAssetInformationDTO().getSerialNo())) {
            boolean statusCheck = registerModel.isDuplicateSerialNo(orgId, astDTO.getAssetInformationDTO().getSerialNo(),
                    astDTO.getAssetInformationDTO().getAssetId());
            if (statusCheck) {
                bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                        ApplicationSession.getInstance().getMessage("assset.maintainanceType.duplicate.serialNo")));
            }
        }
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateInformation(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAssetInformationDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                final int langId = UserSession.getCurrent().getLanguageId();
                registerModel.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
                registerModel.setChartList(
                        cdmService.findAllByOrgIdAstCls(orgId, astDTO.getAssetInformationDTO().getAssetClass2()));
                registerModel.setFuncLocDTOList(assetFuncLocMasterService.retriveFunctionLocationDtoListByOrgId(orgId));
                registerModel.setDepartmentsList(iTbDepartmentService.findAll());
                // save in tb_ast_info_mst table
                astDTO.setOrgId(orgId);
                astDTO.getAssetInformationDTO().setOrgId(orgId);
                astDTO.getAssetInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetInformationDTO().setCreationDate(new Date());
                astDTO.getAssetInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                // save in draft mode
                // when back 2nd time D#73719
                if (this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId() != null) {
                    astDTO.getAssetInformationDTO()
                            .setAssetId(this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId());
                }
                // T#92467 set DEPT_CODE
                astDTO.getAssetInformationDTO().setDeptCode(UserSession.getCurrent().getModuleDeptCode());
                maintenanceService.saveAssetEntryDataInDraftMode(null, MainetConstants.AssetManagement.AST_INFO_URL_CODE, astDTO);
                this.getModel().getAstDetailsDTO().getAssetInformationDTO()
                        .setAstInfoId(astDTO.getAssetInformationDTO().getAssetId());
                mv = new ModelAndView(ASSET_CLASSIFICATION, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_INFORMATION, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstInsuPage")
    public ModelAndView showAstInsuPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption,
            @RequestParam(value = "subModeType", required = false) String subModeType,
            @RequestParam(value = "insuId", required = false) String insuNo) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        AssetInsuranceDetailsDTO insuranceDTO = detailDTO.getAstInsuDTO();
        Boolean isSubTypeAdd = false;
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAstInsuDTO(InsuranceServiceMapper.resetInsurance(insuranceDTO));
        } else if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
            model.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
            /*
             * if (insuranceDTO != null) { astId = insuranceDTO.getAssetId(); } if (astId != null && astId != 0) {
             * detailDTO.setAstInsuDTO(astService.getInsuranceInfo(astId)); }
             */

            if (model.getSubModeType().equalsIgnoreCase("Add")) {
                this.getModel().getAstDetailsDTO().setAstInsuDTO(new AssetInsuranceDetailsDTO());
                isSubTypeAdd = true;
            } else if (model.getSubModeType().equalsIgnoreCase("Edit") || model.getSubModeType().equalsIgnoreCase("View")) {
                insuranceDTO = getAstInsuList(detailDTO.getAstInsuDTOList(), insuNo);
                detailDTO.setAstInsuDTO(insuranceDTO);
            }
        } else if (model.getModeType().equals(MainetConstants.MODE_VIEW)) {
            insuranceDTO = getAstInsuList(detailDTO.getAstInsuDTOList(), insuNo);
            detailDTO.setAstInsuDTO(insuranceDTO);
        } else {
            detailDTO.setAstInsuDTO(detailDTO.getAstInsuDTOList() != null && !detailDTO.getAstInsuDTOList().isEmpty()
                    ? detailDTO.getAstInsuDTOList().get(0)
                    : new AssetInsuranceDetailsDTO());
        }
        // D#72263 check insuranceNo value based on this set true/false
        if (model.getAstDetailsDTO().getAstInsuDTO() != null && !StringUtils
                .isEmpty(model.getAstDetailsDTO().getAstInsuDTO().getInsuranceNo())) {
            model.getAstDetailsDTO().getAstInsuDTO().setIsInsuApplicable(true);
            // D#90946
        } else if (!isSubTypeAdd && insuranceDTO != null
                && !StringUtils.isEmpty(insuranceDTO.getInsuranceNo())) {
            model.getAstDetailsDTO().setAstInsuDTO(insuranceDTO);
            model.getAstDetailsDTO().getAstInsuDTO().setIsInsuApplicable(true);
        }
        return new ModelAndView(ASSET_INSURANCE, MainetConstants.FORM_NAME, model);
    }

    private AssetInsuranceDetailsDTO getAstInsuList(List<AssetInsuranceDetailsDTO> insuDTOList, String insuNo) {
        for (int i = 0; i < insuDTOList.size(); i++) {
            AssetInsuranceDetailsDTO insuDTO = insuDTOList.get(i);

            if (insuDTO.getInsuranceNo().equalsIgnoreCase(insuNo)) {
                return insuDTO;
            }
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstInsuPageDataTable")
    public ModelAndView showAstInsuPageDataTable(final HttpServletRequest request) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long langId = (long) UserSession.getCurrent().getLanguageId();
        AssetRegistrationModel model = this.getModel();// registerModel.setAddInsuStatusFlag("Y");
        model.setAddInsuStatusFlag("N");
        if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
            model.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId.intValue(), MainetConstants.FlagA));
            Long astId = model.getAstDetailsDTO().getAssetInformationDTO().getAssetId();
            if (astId != null && astId != 0) {
                // model.getAstDetailsDTO().setAstInsuDTO(insurService.getInsuranceByAssetId(astId));
                model.getAstDetailsDTO().setAstInsuDTOList(astService.getInsuranceInfoList(astId));
            }
        }
        return new ModelAndView("AssetInsuranceDetailDataTable", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstClsPage")
    public ModelAndView saveAstClsPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        ModelAndView mv = null;
        validateConstraints(astDTO.getAssetClassificationDTO(), AssetClassificationDTO.class, bindingResult);
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateClassification(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAssetClassificationDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                registerModel.setVendorList(vendorMasterService.getActiveVendors(orgid, vendorStatus));
                // save in TB_AST_CLASSFCTN table
                astDTO.getAssetClassificationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetClassificationDTO().setCreationDate(new Date());
                astDTO.getAssetClassificationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_CLASS_URL_CODE, astDTO);
                mv = new ModelAndView(ASSET_PURCHASE, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_CLASSIFICATION, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstLeasePage")
    public ModelAndView showAstLeasePage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        final AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        final AssetLeasingCompanyDTO leaseDTO = detailDTO.getAstLeaseDTO();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAstLeaseDTO(LeasingCompanyMapper.resetLease(leaseDTO));
        } else {
            if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (leaseDTO != null) {
                    astId = leaseDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    detailDTO.setAstLeaseDTO(astService.getLeaseInfo(astId));
                }
            }
        }
        return new ModelAndView(ASSET_LEASE, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstPurchPage")
    public ModelAndView saveAstPurchPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        ModelAndView mv = null;
        validateConstraints(astDTO.getAssetPurchaseInformationDTO(), AssetPurchaseInformationDTO.class, bindingResult);
        registerModel.validateBean(astDTO, AssetAcquisitionValidator.class);
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updatePurchaseInformation(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAssetPurchaseInformationDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_PURCHASER
                astDTO.getAssetPurchaseInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetPurchaseInformationDTO().setCreationDate(new Date());
                astDTO.getAssetPurchaseInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_PURCH_URL_CODE, astDTO);
                // D#72263 check propertyRegistrationNo value based on this set true/false
                if (registerModel.getAstDetailsDTO() != null
                        && registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO() != null && !StringUtils
                                .isEmpty(registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO()
                                        .getPropertyRegistrationNo())) {
                    registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO().setIsRealEstate(true);
                }
                // T#92467
                // mv = new ModelAndView(ASSET_REAL_ESTATE, MainetConstants.FORM_NAME, registerModel);
                mv = new ModelAndView(
                        UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE)
                                ? ASSET_REAL_ESTATE
                                : ASSET_SERVICE,
                        MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_PURCHASE, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstRealEstatePage")
    public ModelAndView saveAstRealEstatePage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

        ModelAndView mv = null;
        validateConstraints(astDTO.getAssetRealEstateInfoDTO(), AssetRealEstateInformationDTO.class, bindingResult);
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {

                    registerModel.updateRealEstateInformation(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAssetRealEstateInfoDTO(), auditDTO, orgid);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_REALSTD
                if (StringUtils.isNotEmpty(astDTO.getAssetRealEstateInfoDTO().getPropertyRegistrationNo())) {
                    astDTO.getAssetRealEstateInfoDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    astDTO.getAssetRealEstateInfoDTO().setCreationDate(new Date());
                    astDTO.getAssetRealEstateInfoDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    // save in draft mode
                    maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                            MainetConstants.AssetManagement.AST_REAL_ESTATE_URL_CODE, astDTO);
                }
                // D#72263 check serviceNo value based on this set true/false
                if (registerModel.getAstDetailsDTO().getAstSerList() != null
                        && !registerModel.getAstDetailsDTO().getAstSerList().isEmpty() && !StringUtils
                                .isEmpty(registerModel.getAstDetailsDTO().getAstSerList().get(0).getServiceNo())) {
                    registerModel.getAstDetailsDTO().setIsServiceAplicable(true);
                }
                mv = new ModelAndView(ASSET_SERVICE, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_REAL_ESTATE, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstSerPage")
    public ModelAndView saveAstSerPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        ModelAndView mv = null;
        // validateConstraints(astDTO.getAstSerList().get(0), AssetServiceInformationDTO.class, bindingResult);
        // validation check manually
        if (StringUtils.isEmpty(astDTO.getAstSerList().get(0).getServiceProvider())) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.service.provider")));
        }

        if (StringUtils.isEmpty(astDTO.getAstSerList().get(0).getServiceNo())) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.service.serialno")));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_SERVICE, MainetConstants.FORM_NAME, registerModel);
        } else {
            mv = new ModelAndView(ASSET_INSURANCE, MainetConstants.FORM_NAME, registerModel);
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateServiceInformation(astDTO.getAssetInformationDTO().getAssetId(), astDTO.getAstSerList(),
                            orgId, auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // D#84269 save in TB_AST_SERVICE_REALESTD
                astDTO.getAstSerList().get(0).setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAstSerList().get(0).setCreationDate(new Date());
                astDTO.getAstSerList().get(0).setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_SERVICE_URL_CODE, astDTO);

            }
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstDepreChartPage")
    public ModelAndView showAstDepreChartPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        AssetClassificationDTO classDTO = detailDTO.getAssetClassificationDTO();
        final AssetDepreciationChartDTO chartDTO = detailDTO.getAstDepreChartDTO();
        model.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
        model.setChartList(cdmService.findAllByOrgIdAstCls(orgId,
                detailDTO.getAssetInformationDTO().getAssetClass2()));
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAstDepreChartDTO(DepreciationChartMapper.resetDepreciationChart(chartDTO));
        } else {
            if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                model.setDeprApplicable(true);
                if (classDTO != null) {
                    astId = classDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    detailDTO.setAstDepreChartDTO(astService.getDepreciationInfo(astId));
                }
            }
        }
        // D#72263 check insuranceNo value based on this set true/false
        if (model.getAstDetailsDTO().getAstDepreChartDTO() != null
                && model.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != null
                && model.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != 0) {
            model.getAstDetailsDTO().getAstDepreChartDTO().setDeprApplicable(true);
        } else {
            model.setDeprApplicable(false);
            // initialize object
            AssetDepreciationChartDTO dto = new AssetDepreciationChartDTO();
            dto.setDeprApplicable(false);
            model.getAstDetailsDTO().setAstDepreChartDTO(dto);
        }
        return new ModelAndView(ASSET_DEPRECIATION, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstInsuPage")
    public ModelAndView saveAstInsuPage(final Model model,
            final HttpServletRequest request,
            @RequestParam(value = "subModeType", required = false) String subModeType) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        final String lease = CommonMasterUtility.getCPDDescription(
                Long.valueOf(astDTO.getAssetInformationDTO().getAcquisitionMethod()), MainetConstants.MODE_EDIT);
        ModelAndView mv = null;
        AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
        auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
        BindingResult bindingResult = registerModel.getBindingResult();
        // if insurance applicable is false then we need to set insurance dto null otherwise it will give validation error
        if (astDTO.getAstInsuDTO().getIsInsuApplicable().equals(false)
                && !registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
            astDTO.setAstInsuDTO(null);
        }
        if (astDTO.getAstInsuDTO() != null) {
            validateConstraints(astDTO.getAstInsuDTO(), AssetInsuranceDetailsDTO.class, bindingResult);
        }

        if (!bindingResult.hasErrors()) {

            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    if (this.getModel().getSubModeType() != null && this.getModel().getSubModeType().equalsIgnoreCase("Add")) {
                        if (astDTO.getAstInsuDTOList() != null && astDTO.getAstInsuDTOList().size() > 0)
                            astDTO.getAstInsuDTOList().add(astDTO.getAstInsuDTO());
                        else {
                            astDTO.setAstInsuDTOList(new ArrayList<AssetInsuranceDetailsDTO>());
                            astDTO.getAstInsuDTOList().add(astDTO.getAstInsuDTO());
                        }

                        registerModel.setAddInsuStatusFlag("Y");
                        return new ModelAndView("AssetInsuranceDetailDataTable", MainetConstants.FORM_NAME, registerModel);
                    } else if (this.getModel().getSubModeType() != null
                            && (this.getModel().getSubModeType().equalsIgnoreCase("Edit")
                                    || this.getModel().getSubModeType().equalsIgnoreCase("View"))) {
                        /// this.getModel().setSubModeType("Edit");

                        return new ModelAndView("AssetInsuranceDetailDataTable", MainetConstants.FORM_NAME, registerModel);

                    }
                    // Used for final save after clicking save & Continue from grid list
                    else
                        registerModel.updateInsuranceDetails(astDTO.getAssetInformationDTO().getAssetId(),
                                astDTO.getAstInsuDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_INSURANCE
                if (astDTO.getAstInsuDTO() != null) {
                    astDTO.getAstInsuDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    astDTO.getAstInsuDTO().setCreationDate(new Date());
                    astDTO.getAstInsuDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                    // save in draft mode
                    maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                            MainetConstants.AssetManagement.AST_INSU_URL_CODE, astDTO);
                }

                if (MainetConstants.AssetManagement.LEASE.equals(lease) || MainetConstants.AssetManagement.LEASE == lease) {
                    mv = new ModelAndView(ASSET_LEASE, MainetConstants.FORM_NAME, registerModel);
                } else {
                    // D#72263
                    if (registerModel.getAstDetailsDTO().getAstDepreChartDTO() != null
                            && registerModel.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != null
                            && registerModel.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != 0) {
                        registerModel.getAstDetailsDTO().getAstDepreChartDTO().setDeprApplicable(true);
                    } else {
                        registerModel.setDeprApplicable(false);
                        // initialize object
                        AssetDepreciationChartDTO dto = new AssetDepreciationChartDTO();
                        dto.setDeprApplicable(false);
                        registerModel.getAstDetailsDTO().setAstDepreChartDTO(dto);
                    }
                    mv = new ModelAndView(ASSET_DEPRECIATION, MainetConstants.FORM_NAME, registerModel);
                }
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_INSURANCE, MainetConstants.FORM_NAME, registerModel);
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstLinePage")
    public ModelAndView showAstLinePage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        final AssetLinearDTO lineDTO = detailDTO.getAstLinearDTO();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            detailDTO.setAstLinearDTO(LinearAssetMapper.resetLinear(lineDTO));
        } else {
            if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
                Long astId = null;
                if (lineDTO != null) {
                    astId = lineDTO.getAssetId();
                }
                if (astId != null && astId != 0) {
                    detailDTO.setAstLinearDTO(astService.getLinear(astId));
                }
            }
        }
        return new ModelAndView(ASSET_LINEAR, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstDocDet")
    public ModelAndView showAstDocDet(final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        fileUpload.sessionCleanUpForFileUpload();
        if (registerModel.getModeType().equals(MainetConstants.MODE_VIEW)
                || registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
            fileUpload.sessionCleanUpForFileUpload();
            final String astId = UserSession.getCurrent().getModuleDeptCode()
                    + MainetConstants.operator.FORWARD_SLACE
                    + registerModel.getAstDetailsDTO().getAssetInformationDTO().getAssetId().toString();

            /*
             * final List<AttachDocs> attachDocs1 = attachDocsService
             * .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), astId);
             * registerModel.getAstDetailsDTO().setAttachDocsList(attachDocs1);
             */

            // D#135788
            List<CFCAttachment> attachment = cfcAttachmentDAO.getDocumentUploadedByRefNo(astId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            List<AttachDocs> attachDocs = new ArrayList<AttachDocs>();
            attachment.forEach(attach -> {
                AttachDocs attDoc = new AttachDocs();
                attDoc.setAttId(attach.getAttId());
                attDoc.setAttFname(attach.getAttFname());
                attDoc.setAttPath(attach.getAttPath());
                attDoc.setAttDate(attach.getAttDate());
                attDoc.setDmsDocName(attach.getClmDescEngl());
                attachDocs.add(attDoc);
            });
            registerModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
        }
        UserSession.getCurrent().setModuleDeptCode(registerModel.getAstDetailsDTO().getAssetInformationDTO().getDeptCode());
        // code adding because at the time of back press than attachment hold old object therefore indexing is wrong
        registerModel.getAstDetailsDTO().getAttachments().clear();
        return new ModelAndView(ASSET_DOC_DETAIL, MainetConstants.FORM_NAME, registerModel);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstLeasePage")
    public ModelAndView saveAstLeasePage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        validateConstraints(astDTO.getAstLeaseDTO(), AssetLeasingCompanyDTO.class, bindingResult);
        ModelAndView mv = null;
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateLeaseInformation(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAstLeaseDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_LEASING
                astDTO.getAstLeaseDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAstLeaseDTO().setCreationDate(new Date());
                astDTO.getAstLeaseDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_LEASE_URL_CODE, astDTO);
                // D#72263
                if (registerModel.getAstDetailsDTO().getAstDepreChartDTO() != null
                        && registerModel.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != null
                        && registerModel.getAstDetailsDTO().getAstDepreChartDTO().getChartOfDepre() != 0) {
                    registerModel.getAstDetailsDTO().getAstDepreChartDTO().setDeprApplicable(true);
                } else {
                    registerModel.setDeprApplicable(false);
                    // initialize object
                    AssetDepreciationChartDTO dto = new AssetDepreciationChartDTO();
                    dto.setDeprApplicable(false);
                    registerModel.getAstDetailsDTO().setAstDepreChartDTO(dto);
                }
                mv = new ModelAndView(ASSET_DEPRECIATION, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_LEASE, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstDepreChartPage")
    public ModelAndView saveAstDepreChartPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        String linear = null;
        if (astDTO.getAssetInformationDTO().getAssetGroup() != null) {
            linear = CommonMasterUtility.getCPDDescription(
                    Long.valueOf(astDTO.getAssetInformationDTO().getAssetGroup()), MainetConstants.MODE_EDIT);
        }
        ModelAndView mv = null;
        validateConstraints(astDTO.getAstDepreChartDTO(), AssetDepreciationChartDTO.class, bindingResult);
        registerModel.validateBean(astDTO, AssetDetailsValidator.class);
        /*
         * // if (astDTO.getAstDepreChartDTO().getDeprApplicable().equals(false)) { if
         * (astDTO.getAstDepreChartDTO().getChartOfDepre() == 0) { bindingResult.addError(new ObjectError(MainetConstants.BLANK,
         * ApplicationSession.getInstance().getMessage("asset.vldnn.chartOfDepre.accumulated.values"))); }
         */
        // }
        if ((astDTO.getAstDepreChartDTO().getInitialAccumDepreAmount() != null
                && astDTO.getAstDepreChartDTO().getInitialAccumulDeprDate() == null)
                || (astDTO.getAstDepreChartDTO().getInitialAccumDepreAmount() == null
                        && astDTO.getAstDepreChartDTO().getInitialAccumulDeprDate() != null)) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldn.chartofdepre.amount")));
        }

        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateDepreciationChart(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAstDepreChartDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_CHART_OF_DEPRETN
                if (astDTO.getAstDepreChartDTO().getDeprApplicable()) {
                    astDTO.getAstDepreChartDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    astDTO.getAstDepreChartDTO().setCreationDate(new Date());
                    astDTO.getAstDepreChartDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    // save in draft mode
                    maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                            MainetConstants.AssetManagement.AST_DEPRE_CHART_URL_CODE, astDTO);
                }

                if (linear != null && (MainetConstants.AssetManagement.LINEAR.equals(linear)
                        || MainetConstants.AssetManagement.LINEAR == linear)) {
                    mv = new ModelAndView(ASSET_LINEAR, MainetConstants.FORM_NAME, registerModel);
                } else {
                    mv = new ModelAndView(ASSET_DOC_DETAIL, MainetConstants.FORM_NAME, registerModel);
                }
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_DEPRECIATION, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstLinePage")
    public ModelAndView saveAstLinePage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        validateConstraints(astDTO.getAstLinearDTO(), AssetLinearDTO.class, bindingResult);
        ModelAndView mv = null;
        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateLinearInformation(astDTO.getAssetInformationDTO().getAssetId(),
                            astDTO.getAstLinearDTO(), auditDTO);
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save in TB_AST_LINEAR
                astDTO.getAstLinearDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAstLinearDTO().setCreationDate(new Date());
                astDTO.getAstLinearDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_LINEAR_URL_CODE, astDTO);
                mv = new ModelAndView(ASSET_DOC_DETAIL, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_LINEAR, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstDocPage")
    public ModelAndView saveAstDocPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        ModelAndView mv = null;
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        // D#79659 validation from server side
        validateConstraints(astDTO.getAssetClassificationDTO(), AssetClassificationDTO.class, bindingResult);
        validateConstraints(astDTO.getAssetPurchaseInformationDTO(), AssetPurchaseInformationDTO.class,
                bindingResult);

        // D#100560 check here for real estate value check or not
        if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE)
                && astDTO.getAssetRealEstateInfoDTO() != null) {
            validateConstraints(astDTO.getAssetRealEstateInfoDTO(), AssetRealEstateInformationDTO.class, bindingResult);
        }
        // D#76821
        if (!(astDTO.getAstSerList().isEmpty()) && astDTO.getAstSerList().get(0).getCheckedService()) {
            validateConstraints(astDTO.getAstSerList().get(0), AssetServiceInformationDTO.class, bindingResult);
        }
        // check insurance
        if (astDTO.getAstInsuDTO() != null) {
            validateConstraints(astDTO.getAstInsuDTO(), AssetInsuranceDetailsDTO.class, bindingResult);
        }
        if (astDTO.getAstLeaseDTO() != null) {
            validateConstraints(astDTO.getAstLeaseDTO(), AssetLeasingCompanyDTO.class, bindingResult);
        }

        if ((astDTO.getAstDepreChartDTO().getInitialAccumDepreAmount() != null
                && astDTO.getAstDepreChartDTO().getInitialAccumulDeprDate() == null)
                || (astDTO.getAstDepreChartDTO().getInitialAccumDepreAmount() == null
                        && astDTO.getAstDepreChartDTO().getInitialAccumulDeprDate() != null)) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldn.chartofdepre.amount")));
        }

        if (astDTO.getAstLinearDTO() != null) {
            validateConstraints(astDTO.getAstLinearDTO(), AssetLinearDTO.class, bindingResult);
        }

        registerModel.validateBean(astDTO, AssetAcquisitionValidator.class);
        registerModel.validateBean(astDTO, AssetDetailsValidator.class);
        registerModel.prepareFileUpload();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        astDTO.setOrgId(orgId);
        // validateConstraints(astDTO, AssetDetailsDTO.class, bindingResult);
        if (astDTO.getAttachments() != null && !astDTO.getAttachments().isEmpty()) {
            String description = "";
            for (final DocumentDetailsVO doc : astDTO.getAttachments()) {
                if (doc.getDoc_DESC_ENGL() != null && doc.getDoc_DESC_ENGL().isEmpty()) {
                    description = null;
                }
                if ((doc.getDocumentByteCode() != null && description == null)
                        || (doc.getDocumentByteCode() == null && description != null)) {
                    bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                            ApplicationSession.getInstance().getMessage("asset.vldnn.documentdetails")));
                }
            }
        }

        if (!bindingResult.hasErrors() || !registerModel.hasValidationErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.updateDocumentDetails(astDTO.getAssetInformationDTO().getAssetId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), auditDTO,
                            registerModel.getDeleteByAtdId(), astDTO.getAttachments());
                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                if (registerModel.saveForm()) {
                    // sessionCleanup(request);
                    /*
                     * mv = jsonResult(JsonViewObject
                     * .successResult(getApplicationSession().getMessage("asset.registration.successMessage")));
                     */
                    // #36987
                    mv = jsonResult(JsonViewObject
                            .successResult(getApplicationSession().getMessage(this.getModel().getSuccessMessage())));
                    sessionCleanup(request);
                } else {
                    mv = jsonResult(JsonViewObject
                            .failureResult(getApplicationSession().getMessage("asset.registration.failureMessage")));
                }
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(ASSET_DOC_DETAIL, MainetConstants.FORM_NAME, registerModel);
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        registerModel.getFileCountUpload().clear();

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            registerModel.getFileCountUpload().add(entry.getKey());
        }

        int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        registerModel.getFileCountUpload().add(fileCount + 1L);

        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= registerModel.getAstDetailsDTO().getAttachments().size(); i++)
            attachments.add(new DocumentDetailsVO());

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
        	if(entry.getKey().intValue()!=100)
            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(registerModel.getAstDetailsDTO()
                    .getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }

        if (attachments.get(registerModel.getAstDetailsDTO().getAttachments().size()).getDoc_DESC_ENGL() == null)
            attachments.get(registerModel.getAstDetailsDTO().getAttachments().size())
                    .setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }

        registerModel.getAstDetailsDTO().setAttachments(attachments);

        return new ModelAndView("assetDocumentDetailsUpload", MainetConstants.FORM_NAME, registerModel);
    }

    @RequestMapping(params = "showEditAssetPage", method = { RequestMethod.POST })
    public ModelAndView showEditAssetPage(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setBindingStatus(null);
        if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
            return new ModelAndView("AssetEdit", MainetConstants.FORM_NAME, this.getModel());
        } else {
            return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, this.getModel());

        }

    }

    private ModelAndView updateDetails(final HttpServletRequest request, final boolean status) {
        if (status) {
            // D#38181
            String message = this.getModel().getSuccessMessage();
            if (message != null) {
                String[] words = message.split("\\.");
                if (words.length > 1) {
                    String textNumber = words[1];
                    String[] finalSplit = textNumber.trim().split("\\s");
                    return jsonResult(JsonViewObject
                            .successResult(getApplicationSession().getMessage("asset.maintainanceType.successMessage")
                                    + " And Reference No " + finalSplit[0]));
                } else {
                    return jsonResult(JsonViewObject
                            .failureResult(getApplicationSession().getMessage("asset.maintainanceType.failureMessage")));
                }
            } else {
                return jsonResult(JsonViewObject
                        .successResult(getApplicationSession().getMessage("asset.maintainanceType.successMessage")));
            }

        } else {
            return jsonResult(JsonViewObject
                    .failureResult(getApplicationSession().getMessage("asset.maintainanceType.failureMessage")));
        }
    }

    /**
     * populate common details
     * 
     * @param astModel
     * @param assetId
     * @param modeType
     */
    private void populateModel(final AssetRegistrationModel astModel, final Long assetId, final String mode,
            final Model model) {
        if (mode.equals(MainetConstants.MODE_CREATE)) {
            astModel.setAstDetailsDTO(new AssetDetailsDTO());
            astModel.setModeType(MainetConstants.MODE_CREATE);
        } else {
            final AssetDetailsDTO detailsDTO = getAssetDetails(assetId);
            detailsDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            astModel.setAstDetailsDTO(detailsDTO);
            if (mode.equals(MainetConstants.MODE_EDIT)) {
                checkTransaction(astModel, assetId);
                astModel.setModeType(MainetConstants.MODE_EDIT);
            } else if (mode.equals(MainetConstants.MODE_VIEW)) {
                if (detailsDTO.getAstDepreChartDTO() != null)
                    if (detailsDTO.getAstDepreChartDTO().getDeprApplicable())
                        astModel.setDeprApplicable(true);
                astModel.setModeType(MainetConstants.MODE_VIEW);
            } else {
                astModel.setModeType(MainetConstants.AssetManagement.APPROVAL_STATUS_DRAFT);
            }
        }
    }

    private void checkTransaction(final AssetRegistrationModel astModel, final Long assetId) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String changeType = MainetConstants.AssetManagement.ValuationType.NEW.getValue();
        final List<AssetValuationDetailsDTO> valuationDTO = astService.checkTransaction(orgId, assetId, changeType);
        if (valuationDTO != null && !valuationDTO.isEmpty()) {
            astModel.setCheckTransaction(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);
        } else {
            astModel.setCheckTransaction(MainetConstants.AssetManagement.CHECK_TRANSACTION_NO);
        }
    }

    /**
     * Method gets called when the object when clicked on search criteria
     * 
     * @param name
     * @return if record found else return empty dto
     */
    @RequestMapping(params = "validateDuplicateName", method = { RequestMethod.POST })
    public ModelAndView validateDuplicateName(
            @RequestParam(value = "assetName", required = false) final String assetName,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean dupFlag = this.getModel().isDuplicateName(orgid, assetName);
        String respMsg = "";
        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("assset.maintainanceType.duplicate.assetName");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }

    /**
     * Method gets called when the object when clicked on search criteria
     * 
     * @param serialNo
     * @return if record found else return empty dto
     */
    @RequestMapping(params = "validateDuplicateSerialNo", method = { RequestMethod.POST })
    public ModelAndView validateDuplicateSerialNo(
            @RequestParam(value = "serialNo", required = true) final String serialNo,
            @RequestParam(value = "assetId", required = false) final Long assetId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean dupFlag = this.getModel().isDuplicateSerialNo(orgid, serialNo, assetId);
        String respMsg = "";
        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("assset.maintainanceType.duplicate.serialNo");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }

    private AssetDetailsDTO getAssetDetails(final Long assetId) {
        return astService.getDetailsByAssetId(assetId);
    }

    // params = "showDetails", method = RequestMethod.POST)
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView astRegFormApproval(@RequestParam("appNo") final String serialNoModified,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam(value = "taskName", required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        AssetRegistrationModel astModel = this.getModel();
        astModel.setTaskId(actualTaskId);
        astModel.getWorkflowActionDto().setReferenceId(serialNoModified);
        astModel.getWorkflowActionDto().setTaskId(actualTaskId);
        astModel.setApprovalProcess("Y");
        int start = serialNoModified.lastIndexOf("/" + MainetConstants.AssetManagement.UPDATE + "/");
        String assetCode = null;
        String revId = null;

        Long assetId = null;

        String groupRefId = null;

        if (start < 0) {
            String assetStrId = serialNoModified
                    .substring(serialNoModified.lastIndexOf("/" + MainetConstants.AssetManagement.NEW + "/") + 5);
            try {
                assetId = Long.parseLong(assetStrId);
            } catch (Exception e) {

                groupRefId = "IAST/BULK/" + assetStrId;
            }

        } else {
        	groupRefId = infoService.findGroupRefIdByAssetAppNo(serialNoModified);
            assetCode = serialNoModified.substring(0, start);
            // revId = Long.parseLong(serialNoModified.substring(start + 5));
            if (serialNoModified.contains("-")) {
                // assetArr = serialNoModified.substring(serialNoModified.lastIndexOf('/') + 1).split(",");

                revId = serialNoModified.substring(serialNoModified.lastIndexOf('/') + 1);
            } else {
                revId = serialNoModified.substring(serialNoModified.lastIndexOf('/') + 1);
            }

        }

        AssetInformationDTO asstInfoDTO = null;
        List<Long> assetIds = new ArrayList<Long>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (groupRefId == null) {
            if (assetId != null) {
                asstInfoDTO = infoService.getAssetId(orgId, assetId);
            } else {
                asstInfoDTO = infoService.getInfoByCode(orgId, assetCode);
            }
        } else {
            List<AssetInformationDTO> infoList = infoService.findAllInformationListByGroupRefId(groupRefId, orgId);
            if (!infoList.isEmpty() && infoList != null) {
                asstInfoDTO = infoList.get(0);

                infoList.stream().forEach(i -> {
                    if (i.getAssetId() != null) {
                        assetIds.add(i.getAssetId());
                    }
                });

                astModel.getAstDetailsDTO().setAssetIds(assetIds);
            }

        }
        astModel.getAstDetailsDTO().setAssetInformationDTO(asstInfoDTO);
        astModel.setDepartmentsList(iTbDepartmentService.findAll());
        astModel.setEmpList(employeeService.getAllEmployee(orgId));
       
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final AssetRegistrationModel registerModel = this.getModel();
      
        registerModel.setVendorList(vendorMasterService.getActiveVendors(orgId, vendorStatus));	
        if (taskName != null && !taskName.isEmpty() && taskName.equals("Initiator")) {
            astModel.setModeType(MainetConstants.MODE_EDIT);
        } else {
            astModel.setModeType(MainetConstants.MODE_VIEW);
        }
		/* List<AttachDocs> attachDocs = null; */
		List<AttachDocs> attachDocs = new ArrayList<AttachDocs>();

        if (asstInfoDTO.getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
            /* fileUpload.sessionCleanUpForFileUpload(); */
            if (astModel.getModeType().equals(MainetConstants.MODE_VIEW)) {
                /* fileUpload.sessionCleanUpForFileUpload(); */
                final String astId = UserSession.getCurrent().getModuleDeptCode()
                        + MainetConstants.operator.FORWARD_SLACE
                        + astModel.getAstDetailsDTO().getAssetInformationDTO().getAssetId().toString();
				/*
				 * attachDocs = attachDocsService
				 * .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), astId);
				 * 
				 * astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
				 */
               
              

            }
        }
        if (asstInfoDTO.getUrlParam() != null) {
            if (revId != null)
                populateModelRev(astModel, revId, MainetConstants.MODE_VIEW, asstInfoDTO, model);
            else

                populateModel(astModel, asstInfoDTO.getAssetId(), MainetConstants.MODE_VIEW, model);

            if (asstInfoDTO.getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
                if (attachDocs != null && !attachDocs.isEmpty()) {
                    astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
                }
                astModel.setApprovalViewFlag("A");
               /* final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                        UserSession.getCurrent().getOrganisation());
                final Long vendorStatus = lookUpVendorStatus.getLookUpId();
                astModel.setVendorList(vendorMasterService.getActiveVendors(orgId, vendorStatus));*/
                if (assetIds != null && !assetIds.isEmpty()) {
                    astModel.getAstDetailsDTO().setAssetIds(assetIds);
                }
                return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);
            } else {
                model.addAttribute("urlParam", asstInfoDTO.getUrlParam());
                return new ModelAndView("AssetEditWorkFlow", MainetConstants.FORM_NAME, astModel);
            }

        } else {
            populateModel(astModel, asstInfoDTO.getAssetId(), "V", model);
            if (attachDocs != null && !attachDocs.isEmpty()) {
                astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
            }
            final String astId = UserSession.getCurrent().getModuleDeptCode() + MainetConstants.operator.FORWARD_SLACE
					+ astModel.getAstDetailsDTO().getAssetInformationDTO().getAssetId().toString();
			List<CFCAttachment> attachment = cfcAttachmentDAO.getDocumentUploadedByRefNo(astId,
					UserSession.getCurrent().getOrganisation().getOrgid());

			attachment.forEach(attach -> {
				AttachDocs attDoc = new AttachDocs();
				attDoc.setAttId(attach.getAttId());
				attDoc.setAttFname(attach.getAttFname());
				attDoc.setAttPath(attach.getAttPath());
				attDoc.setAttDate(attach.getAttDate());
				attDoc.setDmsDocName(attach.getClmDescEngl());
				attachDocs.add(attDoc);
			});
			astModel.getAstDetailsDTO().setAttachDocsList(attachDocs);
            if (taskName != null && !taskName.isEmpty() && taskName.equals("Initiator")) {
                astModel.setApprovalProcess("SEND");
                astModel.setModeType(MainetConstants.MODE_CREATE);
                return new ModelAndView("AssetRegistrationSendBack", MainetConstants.FORM_NAME, astModel);
            } else {
                astModel.setApprovalViewFlag("A");
                /* asstInfoDTO.setCapitalizationStatus("N"); */
                if (asstInfoDTO.getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
                    if (assetIds != null && !assetIds.isEmpty()) {
                        astModel.getAstDetailsDTO().setAssetIds(assetIds);
                    }
                    return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);
                } else {
                    return new ModelAndView("AssetView", MainetConstants.FORM_NAME, astModel);
                }

            }
        }

    }

    /*
     * @RequestMapping(params = "saveDecision", method = RequestMethod.POST) public ModelAndView approvalDecision(final
     * HttpServletRequest httpServletRequest) { JsonViewObject respObj = null; this.bindModel(httpServletRequest); boolean updFlag
     * = false; AssetRegistrationModel asstModel = this.getModel(); String decision =
     * asstModel.getWorkflowActionDto().getDecision(); String status = this.getModel().getCapitalizationStatus(); if
     * (!status.equals("Y")) { updFlag = asstModel.updateApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid()); }
     * else { updFlag = asstModel.updateApprovalFlagCapitalizn((UserSession.getCurrent().getOrganisation().getOrgid())); } updFlag
     * = asstModel.updateApprovalFlagCapitalizn(UserSession.getCurrent().getOrganisation().getOrgid()); if (updFlag) { if
     * (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) respObj = JsonViewObject
     * .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved")); else if
     * (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) respObj = JsonViewObject
     * .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject")); else if
     * (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK)) respObj = JsonViewObject
     * .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.sendBack")); } else { respObj =
     * JsonViewObject .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure")); } return new
     * ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj); }
     */

    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        AssetRegistrationModel asstModel = this.getModel();

        String decision = asstModel.getWorkflowActionDto().getDecision();
        boolean updFlag = asstModel.updateApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid(),
                asstModel.getAstDetailsDTO().getAssetInformationDTO().getDeptCode());

        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                // D#37802
                if (!StringUtils.isEmpty(this.getModel().getSuccessMessage())) {
                    respObj = JsonViewObject
                            .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved")
                            		+(ApplicationSession.getInstance().getMessage("asset.validation.submitBtn")+" "  + this.getModel().getSuccessMessage()));
                    
                    this.getModel().setSuccessMessage(null);
                } else {
                    respObj = JsonViewObject
                            .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
                }

            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.sendBack"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
        }

        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

    }

    /**
     * populate common details
     * 
     * @param astModel
     * @param assetId
     * @param modeType
     */
    private void populateModelRev(final AssetRegistrationModel astModel, final String assetClassIdRev, final String mode,
            AssetInformationDTO astInfoDTO, final Model model) {
        String urlParam = astInfoDTO.getUrlParam();
        AssetDetailsDTO detailsDTO = null;
        if (astModel.getAstDetailsDTO().getAssetInformationDTO().getDeptCode()
                .equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
            detailsDTO = astService.getITAssetDetailsByIdRev(assetClassIdRev);

        } else {
            detailsDTO = astService.getDetailsByIdAndUrlparamRev(Long.parseLong(assetClassIdRev), urlParam);

        }
        if (detailsDTO.getAssetInformationDTO() == null)
            detailsDTO.setAssetInformationDTO(astInfoDTO);
        else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INFO_Page)
                && astInfoDTO.getAppovalStatus() != null) {

            detailsDTO.getAssetInformationDTO().setAppovalStatus(astInfoDTO.getAppovalStatus());
        }
        astModel.setAstDetailsDTO(detailsDTO);

        if (mode.equals(MainetConstants.MODE_EDIT)) {
            astModel.setModeType(MainetConstants.MODE_EDIT);
        } else {
            astModel.setModeType(MainetConstants.MODE_VIEW);
        }

    }

    @RequestMapping(params = "duplicateCheckRfIdNo", method = { RequestMethod.POST })
    public ModelAndView duplicateCheckRfIdNo(@RequestParam(value = "rfiId", required = false) final String rfiId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean dupFlag = this.getModel().isDuplicateRfId(orgId, rfiId);
        String respMsg = "";
        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("assset.maintainanceType.duplicate.rfId");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }

    /**
     * @param request
     * @return this returns the information service in case of add
     */
    @RequestMapping(method = RequestMethod.POST, params = "addServicePage")
    public ModelAndView createData(final HttpServletRequest request,
            @RequestParam(value = "count", required = false) Integer count) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        registerModel.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
        if (registerModel.getAstDetailsDTO().getAssetInformationDTO().getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE) &&
                registerModel.getAstDetailsDTO().getAstSerList() != null &&
                registerModel.getAstDetailsDTO().getAstSerList().size() > 0) {
            registerModel.getAstDetailsDTO().setAstSerList(registerModel.getAstDetailsDTO().getAstSerList().stream()
                    .filter(sl -> sl.getServiceProvider() != null).collect(Collectors.toList()));

        }
        final AssetDetailsDTO detailDTO = registerModel.getAstDetailsDTO();
        if (registerModel.getAstDetailsDTO().getAssetInformationDTO().getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
            if (registerModel.getIndex() != null)
                count = registerModel.getIndex();

        }

        int index = detailDTO.getAstSerList().size();
        if (count.equals(index)) {
            registerModel.setBindingStatus(MainetConstants.FlagC);
        } else {
            registerModel.setBindingStatus("");
        }
        registerModel.setIndex(count);

        return new ModelAndView("AssetServiceInformationAdd", MainetConstants.FORM_NAME, registerModel);

    }

    @RequestMapping(method = RequestMethod.POST, params = "showAstSerPage")
    public ModelAndView showAstSerPage(final HttpServletRequest request,
            @RequestParam(value = ASSET_RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        TbDepartment deptObj = null;
        List<LookUp> locList = null;
        final AssetRegistrationModel model = this.getModel();
        final AssetDetailsDTO detailDTO = model.getAstDetailsDTO();
        final List<AssetServiceInformationDTO> serviceDTOList = detailDTO.getAstSerList();
        if (model.getModeType().equals(MainetConstants.MODE_EDIT)) {
            model.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
            Long astId = null;
            if (serviceDTOList != null && !serviceDTOList.isEmpty()) {
                astId = serviceDTOList.get(0).getAssetId();
            }
            // D#84246
            if (astId != null && astId != 0 && model.getBindingStatus() == null) {
                detailDTO.setAstSerList(astService.getServiceInfo(astId));
            } else if (astId == null) {
                // D#76741
                model.getAstDetailsDTO().setAstSerList(new ArrayList<>());
            } else if (model.getBindingStatus() != "" && model.getBindingStatus() != "Y") {
                // remove last element from the asset service list
                detailDTO.getAstSerList().remove(detailDTO.getAstSerList().size() - 1);
            }
        }
        // this is for tax zone code if property is integrated
        deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA,
                MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        if (deptObj != null) {
            List<LocationMasEntity> location = iLocationMasService.findAllLocationWithOperationWZMapping(orgId,
                    deptObj.getDpDeptid());
            // it will check list is null or not and after that it will convert list of
            // object into list of lookUp
            locList = location.stream().filter(loc -> loc != null).map(loc -> {
                final LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                return lookUp;
            }).collect(Collectors.toList());
        }
        model.setLocation(locList);
        // D#72263 check serviceNo value based on this set true/false
        if (model.getAstDetailsDTO().getAstSerList() != null && !model.getAstDetailsDTO().getAstSerList().isEmpty()
                && !StringUtils
                        .isEmpty(model.getAstDetailsDTO().getAstSerList().get(0).getServiceNo())) {
            model.getAstDetailsDTO().setIsServiceAplicable(true);
        }
        return new ModelAndView(ASSET_SERVICE, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveAstServiceInfo")
    public ModelAndView saveAstServiceInfo(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        BindingResult bindingResult = registerModel.getBindingResult();
        if (astDTO.getAssetInformationDTO().getDeptCode().equals(MainetConstants.AssetManagement.ACCOUNT_CODE)) {
            validateConstraints(astDTO.getAstSerList().get(registerModel.getIndex()), AssetServiceInformationDTO.class,
                    bindingResult);
        }

        if (!bindingResult.hasErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            registerModel.setBindingStatus("Y");
            if (astDTO.getAssetInformationDTO().getDeptCode().equals(MainetConstants.AssetManagement.ACCOUNT_CODE)) {
                return new ModelAndView(ASSET_SERVICE, MainetConstants.FORM_NAME, registerModel);
            } else {

                if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE) &&
                        astDTO.getAstSerList() != null &&
                        astDTO.getAstSerList().size() > 0) {
                    astDTO.setAstSerList(astDTO.getAstSerList().stream().filter(sl -> sl.getServiceProvider() != null)
                            .collect(Collectors.toList()));

                }
                return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, registerModel);
            }
        } else {
            registerModel.setBindingStatus("N");
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            return new ModelAndView("AssetServiceInformationAdd", MainetConstants.FORM_NAME, registerModel);

        }
    }

    @RequestMapping(params = "reloadData", method = { RequestMethod.POST })
    public @ResponseBody List<AssetServiceInformationDTO> reloaddata(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        if (astDTO.getAstSerList() != null && !astDTO.getAstSerList().isEmpty()) {
            List<AssetServiceInformationDTO> list = astDTO.getAstSerList().stream()
                    .filter(l -> l.getServiceNo() != null && StringUtils.isNotBlank(l.getServiceProvider()))
                    .collect(Collectors.toList());
            astDTO.setAstSerList(list);
        }
        return astDTO.getAstSerList();
    }

    @RequestMapping(params = "validateSaveITAstForm", method = { RequestMethod.POST })
    public @ResponseBody List<String> validateSaveITAstForm(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        registerModel.setEmpList(employeeService.getAllEmployee(orgId));
        ModelAndView mv = null;
        // validation of ITAssetForm starts ------------->
        if (astDTO.getAssetInformationDTO().getAssetClass1() == null || astDTO.getAssetInformationDTO().getAssetClass1() == 0L) {
            List<LookUp> astClsList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode()
                            .equals(MainetConstants.AssetManagement.ASSETCODE) ? "ASC" : "ISC",
                    UserSession.getCurrent().getOrganisation());
            List<LookUp> astTypList = CommonMasterUtility.getListLookup(
                    UserSession.getCurrent().getModuleDeptCode()
                            .equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL" : "ICL",
                    UserSession.getCurrent().getOrganisation());
            for (LookUp astClassification : astClsList) {
                List<LookUp> astTypLis = astTypList.stream()
                        .filter(i -> i != null
                                && i.getLookUpId() == astDTO.getAssetInformationDTO().getAssetClass2())
                        .collect(Collectors.toList());
                if (astTypLis != null && !astTypLis.isEmpty()) {
                    astDTO.getAssetInformationDTO().setAssetClass1(astClassification.getLookUpId());
                }
            }
        }
        validateConstraints(astDTO.getAssetInformationDTO(), AssetInformationDTO.class, bindingResult);
        validateConstraints(astDTO.getAssetPurchaseInformationDTO(), AssetPurchaseInformationDTO.class, bindingResult);
        // validateConstraints(astDTO.getAstSerList().get(0), AssetServiceInformationDTO.class, bindingResult);

        if (astDTO.getAssetInformationDTO().getAcquisitionMethod() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetAcquisitionMethod")));
        }
        if (astDTO.getAssetInformationDTO().getAssetStatus() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.statusType")));
        }
        if (astDTO.getAssetInformationDTO().getAssetClass1() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetClassification")));
        }
        if (astDTO.getAssetInformationDTO().getAssetClass2() == 0) {
            bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                    ApplicationSession.getInstance().getMessage("asset.vldnn.assetClass")));
        }
        if (StringUtils.isNotBlank(astDTO.getAssetInformationDTO().getSerialNo())) {
            boolean statusCheck = registerModel.isDuplicateSerialNo(orgId, astDTO.getAssetInformationDTO().getSerialNo(),
                    astDTO.getAssetInformationDTO().getAssetId());
            if (statusCheck) {
                bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                        ApplicationSession.getInstance().getMessage("assset.maintainanceType.duplicate.serialNo")));
            }
        }

        registerModel.validateBean(astDTO, AssetAcquisitionValidator.class);
        registerModel.validateBean(astDTO, AssetDetailsValidator.class);
        registerModel.prepareFileUpload();
        astDTO.setOrgId(orgId);
        // validateConstraints(astDTO, AssetDetailsDTO.class, bindingResult);
        if (astDTO.getAttachments() != null && !astDTO.getAttachments().isEmpty()) {
            String description = "";
            for (final DocumentDetailsVO doc : astDTO.getAttachments()) {
                if (doc.getDoc_DESC_ENGL() != null && doc.getDoc_DESC_ENGL().isEmpty()) {
                    description = null;
                }
                if ((doc.getDocumentByteCode() != null && description == null)
                        || (doc.getDocumentByteCode() == null && description != null)) {
                    bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                            ApplicationSession.getInstance().getMessage("asset.vldnn.documentdetails")));
                }
            }
        }
        Set<String> serialNosFromExcel = new LinkedHashSet<String>();
        if (astDTO.getIsBulkExport()) {
            if (getUploadedFinePath() == null) {
                bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                        ApplicationSession.getInstance().getMessage("asset.information.bulkExcel")));
            } else {

                serialNosFromExcel = collectSerialNosFromExcel(request);
                if (serialNosFromExcel.isEmpty()) {
                    bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                            ApplicationSession.getInstance().getMessage("asset.information.bulkExcel.empty")));
                } else {
                    long quantity = astDTO.getAssetInformationDTO().getQuantity();
                    long excelSize = serialNosFromExcel.size();
                    if (quantity != excelSize) {
                        bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                                ApplicationSession.getInstance().getMessage("asset.information.bulkExcel.size")));
                    }
                }
            }

        }
        List<String> errList = new ArrayList<String>();
        for (ObjectError e : bindingResult.getAllErrors()) {
            errList.add(e.getDefaultMessage());
        }
        return errList;
    }

    // T#101107
    @RequestMapping(method = RequestMethod.POST, params = "saveITAstForm")
    public ModelAndView saveITAstForm(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        registerModel.setEmpList(employeeService.getAllEmployee(orgId));
        ModelAndView mv = null;
        registerModel.prepareFileUpload();
        astDTO.setOrgId(orgId);
        if (!bindingResult.hasErrors() || !registerModel.hasValidationErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;

                try {

                    registerModel.editITASSETPage(astDTO, orgId);
                    registerModel.updateDocumentDetails(astDTO.getAssetInformationDTO().getAssetId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), auditDTO,
                            registerModel.getDeleteByAtdId(), astDTO.getAttachments());

                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save AsssetInformationDetails starts ------------>

                final int langId = UserSession.getCurrent().getLanguageId();
                registerModel.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
                registerModel.setChartList(
                        cdmService.findAllByOrgIdAstCls(orgId, astDTO.getAssetInformationDTO().getAssetClass2()));
                registerModel.setFuncLocDTOList(assetFuncLocMasterService.retriveFunctionLocationDtoListByOrgId(orgId));
                registerModel.setDepartmentsList(iTbDepartmentService.findAll());
                // save in tb_ast_info_mst table
                astDTO.setOrgId(orgId);
                astDTO.getAssetInformationDTO().setOrgId(orgId);
                astDTO.getAssetInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetInformationDTO().setCreationDate(new Date());
                astDTO.getAssetInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                // save in draft mode
                if (this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId() != null) {
                    astDTO.getAssetInformationDTO()
                            .setAssetId(this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId());
                }
                astDTO.getAssetInformationDTO().setDeptCode(UserSession.getCurrent().getModuleDeptCode());
                maintenanceService.saveAssetEntryDataInDraftMode(null, MainetConstants.AssetManagement.AST_INFO_URL_CODE, astDTO);
                this.getModel().getAstDetailsDTO().getAssetInformationDTO()
                        .setAstInfoId(astDTO.getAssetInformationDTO().getAssetId());
                // save AsssetInformationDetails ends

                // save AssetPurchseDetails starts
                astDTO.getAssetPurchaseInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetPurchaseInformationDTO().setCreationDate(new Date());
                astDTO.getAssetPurchaseInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                // save in draft mode
                maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                        MainetConstants.AssetManagement.AST_PURCH_URL_CODE, astDTO);

                if (registerModel.getAstDetailsDTO() != null
                        && registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO() != null && !StringUtils
                                .isEmpty(registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO()
                                        .getPropertyRegistrationNo())) {
                    registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO().setIsRealEstate(true);
                }

                // save AssetPurchseDetails ends

                // save AssetServiceInformation starts
                if (astDTO.getAstSerList() != null && !astDTO.getAstSerList().isEmpty()) {
                    if (astDTO.getAstSerList().get(0).getServiceProvider() != null
                            && !astDTO.getAstSerList().get(0).getServiceProvider().equals("")) {
                        astDTO.getAstSerList().get(0).setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        astDTO.getAstSerList().get(0).setCreationDate(new Date());
                        astDTO.getAstSerList().get(0).setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                        // save in draft mode
                        if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)
                                &&
                                astDTO.getAstSerList() != null &&
                                astDTO.getAstSerList().size() > 0) {
                            astDTO.setAstSerList(astDTO.getAstSerList().stream().filter(sl -> sl.getServiceProvider() != null)
                                    .collect(Collectors.toList()));

                        }
                        maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                                MainetConstants.AssetManagement.AST_SERVICE_URL_CODE, astDTO);
                    }
                }

                // save AssetServiceInformation ends

                // save DocumentDetails & initiate workflow step starts

                if (registerModel.saveForm()) {
                    mv = jsonResult(JsonViewObject
                            .successResult(getApplicationSession().getMessage(this.getModel().getSuccessMessage())));
                    sessionCleanup(request);
                } else {
                    mv = jsonResult(JsonViewObject
                            .failureResult(getApplicationSession().getMessage("asset.registration.failureMessage")));
                }

                // save DocumentDetails & initiate workflow step ends ------------>
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveITAstFormBulk1")
    public ModelAndView saveITAstFormBulk1(final Model model, final HttpServletRequest request) {
        Set<String> serialNosFromExcel = new LinkedHashSet<String>();
        serialNosFromExcel = collectSerialNosFromExcel(request);
        bindModel(request);
        final AssetRegistrationModel registerModel = this.getModel();
        BindingResult bindingResult = registerModel.getBindingResult();
        AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        registerModel.setEmpList(employeeService.getAllEmployee(orgId));
        ModelAndView mv = null;
        registerModel.prepareFileUpload();
        astDTO.setOrgId(orgId);
        if (!bindingResult.hasErrors() || !registerModel.hasValidationErrors()) {
            AuditDetailsDTO auditDTO = registerModel.getAuditDTO();
            auditDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            auditDTO.setEmpIpMac(Utility.getClientIpAddress(request));
            if (registerModel.getModeType().equals(MainetConstants.MODE_EDIT)) {
                boolean status = false;
                try {
                    registerModel.editITASSETPage(astDTO, orgId);
                    // changes for bulk edit starts
                    /*
                     * for(Long assetId : astDTO.getAssetIds()) { astDTO.getAssetInformationDTO().setAssetId(assetId);
                     */
                    // changes for bulk edit ends
                    registerModel.updateDocumentDetails(astDTO.getAssetInformationDTO().getAssetId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), auditDTO,
                            registerModel.getDeleteByAtdId(), astDTO.getAttachments());
                    // changes for bulk edit starts
                    // }
                    // changes for bulk edit starts

                    status = true;
                } catch (FrameworkException ex) {
                    status = false;
                }
                mv = updateDetails(request, status);
            } else {
                // save AsssetInformationDetails starts ------------>

                final int langId = UserSession.getCurrent().getLanguageId();
                registerModel.setAcHeadCode(shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagA));
                registerModel.setChartList(
                        cdmService.findAllByOrgIdAstCls(orgId, astDTO.getAssetInformationDTO().getAssetClass2()));
                registerModel.setFuncLocDTOList(assetFuncLocMasterService.retriveFunctionLocationDtoListByOrgId(orgId));
                registerModel.setDepartmentsList(iTbDepartmentService.findAll());
                // set AssetPurchseDetails table
                astDTO.setOrgId(orgId);
                astDTO.getAssetInformationDTO().setOrgId(orgId);
                astDTO.getAssetInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetInformationDTO().setCreationDate(new Date());
                astDTO.getAssetInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                if (this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId() != null) {
                    astDTO.getAssetInformationDTO()
                            .setAssetId(this.getModel().getAstDetailsDTO().getAssetInformationDTO().getAstInfoId());
                }
                astDTO.getAssetInformationDTO().setDeptCode(UserSession.getCurrent().getModuleDeptCode());

                // set AsssetInformationDetails ends

                // set AssetPurchseDetails starts
                astDTO.getAssetPurchaseInformationDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                astDTO.getAssetPurchaseInformationDTO().setCreationDate(new Date());
                astDTO.getAssetPurchaseInformationDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

                if (registerModel.getAstDetailsDTO() != null
                        && registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO() != null && !StringUtils
                                .isEmpty(registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO()
                                        .getPropertyRegistrationNo())) {
                    registerModel.getAstDetailsDTO().getAssetRealEstateInfoDTO().setIsRealEstate(true);
                }
                // set AssetPurchseDetails ends
                // set AssetServiceInformation starts
                if (astDTO.getAstSerList() != null && !astDTO.getAstSerList().isEmpty()) {
                    if (astDTO.getAstSerList().get(0).getServiceProvider() != null
                            && !astDTO.getAstSerList().get(0).getServiceProvider().equals("")) {
                        astDTO.getAstSerList().get(0).setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        astDTO.getAstSerList().get(0).setCreationDate(new Date());
                        astDTO.getAstSerList().get(0).setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                        if (UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)
                                &&
                                astDTO.getAstSerList() != null &&
                                astDTO.getAstSerList().size() > 0) {
                            astDTO.setAstSerList(astDTO.getAstSerList().stream().filter(sl -> sl.getServiceProvider() != null)
                                    .collect(Collectors.toList()));

                        }
                    }
                }
                // changes for Bulk Add Starts -->
                // need to use the length of excel sheet row num as lenght or need to add text field on form
                String sendBackflag = null;
                List<Long> assetIds = new ArrayList<Long>();
                for (String singleInsert : serialNosFromExcel) {
                    astDTO.getAssetInformationDTO().setSerialNo(singleInsert);// this singleInsert will come from excel sheet
                    maintenanceService.saveAssetEntryDataInDraftMode(null, MainetConstants.AssetManagement.AST_INFO_URL_CODE,
                            astDTO);
                    this.getModel().getAstDetailsDTO().getAssetInformationDTO()
                            .setAstInfoId(astDTO.getAssetInformationDTO().getAssetId());
                    maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                            MainetConstants.AssetManagement.AST_PURCH_URL_CODE, astDTO);
                    maintenanceService.saveAssetEntryDataInDraftMode(astDTO.getAssetInformationDTO().getAssetId(),
                            MainetConstants.AssetManagement.AST_SERVICE_URL_CODE, astDTO);

                    registerModel.prepareFileUpload();
                    astDTO.setOrgId(orgId);
                    astDTO.setAuditDTO(auditDTO);
                    if (this.getModel().getApprovalProcess().equals("SEND")) {
                        sendBackflag = MainetConstants.FlagU;
                        maintenanceService.updateDetailDto(astDTO, auditDTO);
                    } else {
                        sendBackflag = MainetConstants.FlagA;
                        maintenanceService.registerDetailDto(astDTO);
                    }
                    assetIds.add(astDTO.getAssetInformationDTO().getAssetId());
                    astDTO.getAssetInformationDTO().setAssetId(null);// making the value as null because for next iteration data
                                                                     // is updating because of this coloumn
                    astDTO.getAssetPurchaseInformationDTO().setAssetPurchaserId(null);
                    if (astDTO.getAstSerList() != null && !astDTO.getAstSerList().isEmpty()) {
                        astDTO.getAstSerList().get(0).setAssetServiceId(null);
                    }
                }// for loop ends
                if (!assetIds.isEmpty() && assetIds != null) {
                    this.getModel().getAstDetailsDTO().setAssetIds(assetIds);
                    Long startIndex = assetIds.get(0);
                    String referenceId = "";
                    if (assetIds.size() > 1) {
                        Long endIndex = assetIds.get(assetIds.size() - 1);
                        referenceId = startIndex + "-TO-" + endIndex + "&Q-" + astDTO.getAssetInformationDTO().getQuantity();
                    } else {
                        referenceId = startIndex.toString();
                    }
                    astDTO.getAssetInformationDTO().setGroupRefId("IAST/BULK/" + referenceId);// need to add one textfiled with
                                                                                              // value groupRefId on
                                                                                              // AssetInformation JSP
                    this.getModel().initiateWorkFlow(orgId, astDTO.getAssetInformationDTO().getAstCode(), null, null,
                            null, true, sendBackflag, referenceId);

                }
                // changes for Bulk Add ends ---->
                if (this.getModel().getSuccessMessage() != null) {

                    mv = jsonResult(JsonViewObject
                            .successResult(getApplicationSession().getMessage(this.getModel().getSuccessMessage())));
                    sessionCleanup(request);
                } else {
                    mv = jsonResult(JsonViewObject
                            .failureResult(getApplicationSession().getMessage("asset.registration.failureMessage")));
                }
                // save DocumentDetails & initiate workflow step ends ------------>
            }
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            mv = new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, registerModel);
        }
        return mv;
    }

    public Set<String> collectSerialNosFromExcel(final HttpServletRequest request) {
        this.getModel().bind(request);
        final String filePath = getUploadedFinePath();
        try {
            Set<String> serialNosFromExcel = new LinkedHashSet<String>();
            ReadExcelData<ITAssetRegisterBulkDTO> data = new ReadExcelData<>(filePath, ITAssetRegisterBulkDTO.class);
            data.parseExcelList();
            final List<ITAssetRegisterBulkDTO> dtos = data.getParseData();
            dtos.stream().forEach(i -> {
                if (i != null) {
                    serialNosFromExcel.add(i.getSerialNo());
                }
            });
            return serialNosFromExcel;
        } catch (Exception ex) {
            throw new FrameworkException("Asset_reg_bulk_serial_no_eport", ex);
        }

    }

    private String getUploadedFinePath() {
        String filePath = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
            	if(entry.getKey()==100)
                filePath = file.toString();
                break;
            }
        }
        return filePath;
    }

    @RequestMapping(params = "ExcelTemplateDataBulk", method = { RequestMethod.GET })
    public void exportAssetExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        AssetRegisterUploadModel model = new AssetRegisterUploadModel();
        model.bind(request);
        try {
            WriteExcelData<ITAssetRegisterBulkDTO> data = new WriteExcelData<>(
                    "ITASSETBULKExcelExort" + MainetConstants.XLSX_EXT,
                    request, response);
            data.getExpotedExcelSheet(this.getModel().getItAssetRegisterBulkDTO(), ITAssetRegisterBulkDTO.class);

        } catch (Exception ex) {
            throw new FrameworkException("ITASSETBULKExcelExort", ex);
        }
    }
    
    
    @Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest request) throws Exception {
		
		final AssetRegistrationModel registerModel = this.getModel();
		BindingResult bindingResult = registerModel.getBindingResult();
		this.bindModel(request);
		AssetRegistrationModel astModel = this.getModel();
		astModel.setCompletedFlag("Y");
		astModel.setTaskId(taskId);
		astModel.getWorkflowActionDto().setReferenceId(applicationId);
		astModel.getWorkflowActionDto().setTaskId(taskId);
		astModel.setApprovalProcess("Y");
		int start = applicationId.lastIndexOf("/" + MainetConstants.AssetManagement.UPDATE + "/");
		AssetDetailsDTO astDTO = registerModel.getAstDetailsDTO();
		//validateConstraints(astDTO.getAssetInformationDTO(), AssetInformationDTO.class, bindingResult);
		String assetCode = null;
		
		String revId = null;

		Long assetId = null;
		Long assetIdinfo = Long.parseLong(applicationId.substring(applicationId.lastIndexOf('/') + 1));

		String groupRefId = null;
		   AssetPurchaseInformationDTO dto=astService.assetpurhcaseView(assetIdinfo);
		  this.getModel().getAstDetailsDTO().setAssetPurchaseInformationDTO(dto);
		if (start < 0) {
			String assetStrId = applicationId
					.substring(applicationId.lastIndexOf("/" + MainetConstants.AssetManagement.NEW + "/") + 5);
			try {
				assetId = Long.parseLong(assetStrId);
			} catch (Exception e) {

				groupRefId = "IAST/BULK/" + assetStrId;
			}

		} else {
			assetCode = applicationId.substring(0, start);
			
			if (applicationId.contains("-")) {
				
				revId = applicationId.substring(applicationId.lastIndexOf('/') + 1);
			} else {
				revId = applicationId.substring(applicationId.lastIndexOf('/') + 1);
			}

		}

		AssetInformationDTO asstInfoDTO = null;
		List<Long> assetIds = new ArrayList<Long>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (groupRefId == null) {
			if (assetId != null) {
				asstInfoDTO = infoService.getAssetId(orgId, assetId);
			} else {
				asstInfoDTO = infoService.getInfoByCode(orgId, assetCode);
			}
		} else {
			List<AssetInformationDTO> infoList = infoService.findAllInformationListByGroupRefId(groupRefId, orgId);
			if (!infoList.isEmpty() && infoList != null) {
				asstInfoDTO = infoList.get(0);

				infoList.stream().forEach(i -> {
					if (i.getAssetId() != null) {
						assetIds.add(i.getAssetId());
					}
				});

				astModel.getAstDetailsDTO().setAssetIds(assetIds);
			}

		}
		astModel.getAstDetailsDTO().setAssetInformationDTO(asstInfoDTO);
		astModel.setDepartmentsList(iTbDepartmentService.findAll());
		astModel.setEmpList(employeeService.getAllEmployee(orgId));

		final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		

		astModel.setVendorList(vendorMasterService.getActiveVendors(orgId, vendorStatus));
		
		astModel.setApprovalViewFlag("A");
		astModel.setModeType(MainetConstants.MODE_VIEW);
		
		if (asstInfoDTO.getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
			if (assetIds != null && !assetIds.isEmpty()) {
				astModel.getAstDetailsDTO().setAssetIds(assetIds);
			}
			return new ModelAndView(IT_ASSET_MAIN, MainetConstants.FORM_NAME, astModel);
		} else {
			return new ModelAndView("AssetView", MainetConstants.FORM_NAME, astModel);
		}

		

	}
    

    

}
