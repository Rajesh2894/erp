/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.service.IInformationService;

import com.abm.mainet.asset.ui.dto.TransferDTO;

import com.abm.mainet.asset.ui.model.AssetTransferModel;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.dms.dao.ICFCAttachmentDAO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;

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
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_TRANSFER_PATH, "ITAssetTransfer.html" })
public class AssetTransferController extends AbstractFormController<AssetTransferModel> {

    @Autowired
    private IAssetInformationService astService;
    @Resource
    private IFileUploadService fileUpload;
    @Autowired
    private TbDepartmentService iTbDepartmentService;
    @Resource
    private ILocationMasService iLocationMasService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private SecondaryheadMasterService shmService;

    @Autowired
    IInformationService iInformationService;

    @Autowired
    private ICFCAttachmentDAO cfcAttachmentDAO;
    
    @Autowired
    private TbDepartmentService tbDepartmentService;
    
    
	

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("AssetTransfer.html");
        this.getModel().setTransDTO(new TransferDTO());
        this.getModel().setCompletedFlag("N");

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String smShortDesc = request.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, request.getRequestURI(), "ITAssetTransfer.html"));

        Long assetStatusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
                PrefixConstants.ASSET_PREFIX.ASEET_STATUS, orgId);
        // DOUBT ASK TO SAMADHAN SIR FOR IT ASSET
        Long assetClassId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.ASSET_PREFIX.ASSET_CLASSIFICATION_MOV,
                UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE)
                        ? PrefixConstants.ASSET_PREFIX.ASSET_CLASSIFICATION
                        : "ISC",
                orgId);
        List<AssetInformationDTO> astInfoList = iInformationService
                .fetchAssetInfoByIds(UserSession.getCurrent().getOrganisation().getOrgid(), assetStatusId, assetClassId);

        this.getModel().setLookUpList(loadAssetCodes(orgId, ""));
        this.getModel().getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        this.getModel().getAudit().setEmpIpMac(Utility.getClientIpAddress(request));
        // employee list make alphabetical order or full name
        // D#38594
        List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgId);
        employeeList.forEach(employee -> {
        	employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname() +" " +employee.getDesignName());
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        this.getModel().setEmpList(employeeList);
        this.getModel().setDepartmentsList(iTbDepartmentService.findAll());
        this.getModel().setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        this.getModel().setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        this.getModel().getTransDTO().setOrgId(orgId);
        this.getModel().setClickFromNode(true);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "getAssetData", method = RequestMethod.POST)
    public TransferDTO getAssetData(@RequestParam("assetId") final Long assetId,
            final HttpServletRequest request) {
        getModel().bind(request);
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(assetId);
        final AssetTransferModel transferModel = this.getModel();
        final TransferDTO transDTO = transferModel.getTransDTO();
        int langId = UserSession.getCurrent().getLanguageId();
        Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        transDTO.setAssetCode(detDTO.getAssetInformationDTO().getAssetId());
        transDTO.setAssetSrNo(detDTO.getAssetInformationDTO().getAstCode());
        transDTO.setAssetDesc(detDTO.getAssetInformationDTO().getDetails());
        transDTO.setAssetClass2(detDTO.getAssetInformationDTO().getAssetClass2());
        transDTO.setCurrentLocation(detDTO.getAssetInformationDTO().getLocation());
       
       
		if (detDTO.getAssetInformationDTO().getLocation() != null) {
			String loc = iLocationMasService.getLocationNameById(detDTO.getAssetInformationDTO().getLocation(), orgId);
			transDTO.setCurrentLocationDesc(loc);
		}
		if (detDTO.getAssetInformationDTO().getAstAvlstatus() != null) {
			Department dept = tbDepartmentService
					.findDepartmentByCode(detDTO.getAssetInformationDTO().getAstAvlstatus());
			transDTO.setDepartment(dept.getDpDeptid());
		}
        
        
      
        
        
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getLocation() != null) {
            TbLocationMas locmas = iLocationMasService.findById(detDTO.getAssetClassificationDTO().getLocation());
            if (langId != 2) {
                transDTO.setCurrentLocationDesc(locmas.getLocNameEng());
            } else {
                transDTO.setCurrentLocationDesc(locmas.getLocNameReg());
            }
        }
        if (detDTO.getAssetClassificationDTO() != null
                && StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
            transDTO.setCurrentCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian() != null) {
            transDTO.setCustodian(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            transDTO.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
        }
        if (detDTO.getAssetInformationDTO().getEmployeeId() != null) {
            transDTO.setCurrentEmployee(detDTO.getAssetInformationDTO().getEmployeeId());
        } else {
            transDTO.setCurrentEmployee(detDTO.getAssetInformationDTO().getEmployeeId());
        }
        // set current cost center
        if (StringUtils.isNotEmpty(transDTO.getCurrentCostCenter())) {
            LookUp accoundSecHead = transferModel.getAccountHead().stream()
                    .filter(account -> account.getLookUpDesc().equals(transDTO.getCurrentCostCenter())).findFirst().orElse(null);

            transDTO.setCurrentCostCenter(accoundSecHead != null ? String.valueOf(accoundSecHead.getLookUpId()) : null);
        }
        transDTO.setOrgId(orgId);
        List<EmployeeBean> employeeList = employeeService.getEmployeeData(transDTO.getDepartment(),null,null,orgId,null);
        employeeList.forEach(employee -> {
       employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname() +" " +employee.getDesignName());
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        transDTO.setEmpList(employeeList);
        transDTO.setAssetSrNo(detDTO.getAssetInformationDTO().getAssetId().toString());
        return transDTO;
    }

    /**
     * Below code is used from asset entry summary screen transfer BT
     * 
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView transferForm(final Model model,
            @RequestParam(value = MainetConstants.AssetManagement.AST_ID, required = false) final Long assetId,
            final HttpServletRequest request) {
        int langId = UserSession.getCurrent().getLanguageId();
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCompletedFlag("N");
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(assetId);
        final AssetTransferModel transferModel = this.getModel();
        final TransferDTO transDTO = transferModel.getTransDTO();
        Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        transferModel.setLookUpList(loadAssetCodes(orgId, ""));
        transferModel.setDepartmentsList(iTbDepartmentService.findAll());
        transferModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
        // transferModel.setEmpList(employeeService.getAllEmployee(orgId));
        // employee list make alphabetical order or full name
        // D#38594
        List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgId);
        employeeList.forEach(employee -> {
        	employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname() +" " +employee.getDesignName());
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        transferModel.setEmpList(employeeList);
        transferModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        transferModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(request));
        transDTO.setAssetCode(detDTO.getAssetInformationDTO().getAssetId());
        transDTO.setAssetSrNo(detDTO.getAssetInformationDTO().getAstCode());
        transDTO.setAssetDesc(detDTO.getAssetInformationDTO().getDetails());
        transferModel
                .setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getLocation() != null) {
            TbLocationMas locmas = iLocationMasService.findById(detDTO.getAssetClassificationDTO().getLocation());
            if (langId != 2) {
                transDTO.setCurrentLocationDesc(locmas.getLocNameEng());
            } else {
                transDTO.setCurrentLocationDesc(locmas.getLocNameReg());
            }
        }
        if (detDTO.getAssetClassificationDTO() != null
                && StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
            transDTO.setCurrentCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian() != null) {
            transDTO.setCustodian(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            transDTO.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId() != null) {
            transDTO.setCurrentEmployee(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId());
        }

        transDTO.setOrgId(orgId);
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        this.getModel().setClickFromNode(false);
        return new ModelAndView("AssetTransferForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showTransferApproval(@RequestParam("appNo") final String serialNoModified,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
       
        int langId = UserSession.getCurrent().getLanguageId();
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        AssetTransferModel transferModel = this.getModel();
        this.getModel().setCompletedFlag("N");
        transferModel.setTaskId(actualTaskId);
        transferModel.getWorkflowActionDto().setReferenceId(serialNoModified);
        transferModel.getWorkflowActionDto().setTaskId(actualTaskId);
        // load new information as recorded in the transfer request
       
        transferModel.loadTransferDetails();
        model.addAttribute("mode", "APR");
        final TransferDTO transDTO = transferModel.getTransDTO();
        // populate the existing data from asset records
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(transDTO.getAssetCode());
        
        Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        transDTO.setAssetClass2(detDTO.getAssetInformationDTO().getAssetClass2());
        transferModel.setDepartmentsList(iTbDepartmentService.findAll());
        transferModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
		if (detDTO.getAssetInformationDTO().getLocation() != null) {
			String loc = iLocationMasService.getLocationNameById(detDTO.getAssetInformationDTO().getLocation(), orgId);
			transDTO.setCurrentLocationDesc(loc);
		}

		if (detDTO.getAssetInformationDTO().getAstAvlstatus() != null) {
			Department dept = tbDepartmentService
					.findDepartmentByCode(detDTO.getAssetInformationDTO().getAstAvlstatus());
			transDTO.setDepartment(dept.getDpDeptid());
		}
        // employee list make alphabetical order or full name
        // D#38594
        List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgId);
        employeeList.forEach(employee -> {
            employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname() +" " +employee.getDesignName()); 
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        // transferModel.setEmpList(employeeService.getAllEmployee(orgId));
        transferModel.setEmpList(employeeList);
        transferModel.setLookUpList(loadAssetCodes(orgId, transferModel.getTransDTO().getDeptCode()));
        transferModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        transferModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(httpServletRequest));
        transDTO.setAssetCode(detDTO.getAssetInformationDTO().getAssetId());
        // transDTO.setAssetSrNo(detDTO.getAssetInformationDTO().getAstCode());
        transDTO.setAssetSrNo(String.valueOf(detDTO.getAssetInformationDTO().getAssetId()));
        transDTO.setAssetDesc(detDTO.getAssetInformationDTO().getDetails());
        // changes
       // transDTO.setAssetSrNo(detDTO.getAssetInformationDTO().getAstCode());
        if (transDTO.getTransferType() != null && transDTO.getTransferType().equals("trans-emp")) {
            transDTO.setEmpDesignation(empDesignation(transDTO.getTransferEmployee(), httpServletRequest));
        }
        transferModel
                .setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getLocation() != null) {
            TbLocationMas locmas = iLocationMasService.findById(detDTO.getAssetClassificationDTO().getLocation());
            if (langId != 2) {
                transDTO.setCurrentLocationDesc(locmas.getLocNameEng());
            } else {
                transDTO.setCurrentLocationDesc(locmas.getLocNameReg());
            }
        }
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getCostCenter() != null) {
            transDTO.setCurrentCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
            // set current cost center
            if (StringUtils.isNotEmpty(transDTO.getCurrentCostCenter())) {
                LookUp accoundSecHead = transferModel.getAccountHead().stream()
                        .filter(account -> account.getLookUpDesc().equals(transDTO.getCurrentCostCenter())).findFirst()
                        .orElse(null);
                transDTO.setCurrentCostCenter(accoundSecHead != null ? String.valueOf(accoundSecHead.getLookUpId()) : null);
            }
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian() != null) {
            transDTO.setCustodian(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            transDTO.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId() != null) {
            transDTO.setCurrentEmployee(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId());
        }
        transDTO.setOrgId(orgId);
        transDTO.setDeptCode(detDTO.getAssetInformationDTO().getDeptCode());
        // get attached document

        /*
         * final List<AttachDocs> attachDocs1 =
         * ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class) .findByCode(
         * UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.AssetManagement.ASSET_MANAGEMENT +
         * MainetConstants.WINDOWS_SLASH + transDTO.getTransferId()); if (attachDocs1 != null) {
         * this.getModel().setAttachDocsList(attachDocs1); }
         */

        // D#135788

        List<CFCAttachment> attachment = cfcAttachmentDAO.getDocumentUploadedByApplicationId(transDTO.getTransferId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        List<AttachDocs> attachDocs = new ArrayList<AttachDocs>();
        attachment.forEach(attach -> {
            AttachDocs attDoc = new AttachDocs();
            attDoc.setAttId(attach.getAttId());
            attDoc.setAttFname(attach.getAttFname());
            attDoc.setAttPath(attach.getAttPath());
            attachDocs.add(attDoc);
        });
        this.getModel().setAttachDocsList(attachDocs);
        UserSession.getCurrent().setModuleDeptCode(transferModel.getTransDTO().getDeptCode());

        return new ModelAndView("AssetTransferApproval", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "appTrfAction", method = RequestMethod.POST)
    public ModelAndView transferAppAction(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        AssetTransferModel asstModel = this.getModel();

        String decision = asstModel.getWorkflowActionDto().getDecision();
       
        boolean updFlag = asstModel.apprTransferAction(asstModel.getTransDTO().getDeptCode());
        
		/*
		 * if(decision.equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
		 * 
		 * }
		 */
        
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
            	
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
        }
       

        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }

    @RequestMapping(params = "empDesignation", method = { RequestMethod.POST })
    public @ResponseBody String empDesignation(@RequestParam("employeeId") final Long employeeId,
            final HttpServletRequest request) {
        bindModel(request);
        String empdesignation = null;
        final AssetTransferModel asstModel = this.getModel();
        if (asstModel.getEmpList() != null && !asstModel.getEmpList().isEmpty()) {
            empdesignation = asstModel.getEmpList().stream()
                    .filter(l -> l.getEmpId() != null && l.getEmpId().equals(employeeId))
                    .collect(Collectors.toList()).get(0).getDesignName();

        }
        return empdesignation;
    }

    public List<LookUp> loadAssetCodes(Long orgId, String moduleDeptCode) {
        List<LookUp> assetCodes = new ArrayList<>();
        Long assetStatusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
                PrefixConstants.ASSET_PREFIX.ASEET_STATUS, orgId);
        // DOUBT ASK TO SAMADHAN SIR FOR IT ASSET
        if (StringUtils.isBlank(moduleDeptCode)) {
            moduleDeptCode = UserSession.getCurrent().getModuleDeptCode();
        }
        Long assetClassId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.ASSET_PREFIX.ASSET_CLASSIFICATION_MOV,
                moduleDeptCode.equals(MainetConstants.AssetManagement.ASSETCODE)
                        ? PrefixConstants.ASSET_PREFIX.ASSET_CLASSIFICATION
                        : "ISC",
                orgId);
        List<AssetInformationDTO> astInfoList = iInformationService
                .fetchAssetInfoByIds(UserSession.getCurrent().getOrganisation().getOrgid(), assetStatusId, assetClassId);
        final String finalModuleDeptCode = moduleDeptCode;
        if (moduleDeptCode.equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE) &&
        	    Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {

            astInfoList.stream().filter(astInfo -> astInfo.getAstAvlstatus() != null && astInfo.getAstCode() != null && StringUtils.equalsIgnoreCase(finalModuleDeptCode, astInfo.getDeptCode())).forEach(astInfo -> {
                
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(astInfo.getAssetId());
                if (StringUtils.isNotEmpty(astInfo.getSerialNo())) {
                    lookUp.setLookUpType((astInfo.getAstCode() + " - " + astInfo.getSerialNo()));
                } else if (StringUtils.isNotEmpty(astInfo.getAssetModelIdentifier())) {
                    lookUp.setLookUpType((astInfo.getAstCode() + " - " + astInfo.getAssetModelIdentifier()));
                } else {
                    lookUp.setLookUpType(astInfo.getAstCode());
                }
                assetCodes.add(lookUp);
            });
        	
        } else {
            astInfoList.stream().filter(astInfo -> astInfo.getAstCode() != null && StringUtils.equalsIgnoreCase(finalModuleDeptCode, astInfo.getDeptCode())).forEach(astInfo -> {
               
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(astInfo.getAssetId());
                if (StringUtils.isNotEmpty(astInfo.getSerialNo())) {
                    lookUp.setLookUpType((astInfo.getAstCode() + " - " + astInfo.getSerialNo()));
                } else if (StringUtils.isNotEmpty(astInfo.getAssetModelIdentifier())) {
                    lookUp.setLookUpType((astInfo.getAstCode() + " - " + astInfo.getAssetModelIdentifier()));
                } else {
                    lookUp.setLookUpType(astInfo.getAstCode());
                }
                assetCodes.add(lookUp);
            });
        }

        return assetCodes;
    }
    
    
    @Override 
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long taskId, final HttpServletRequest request )throws Exception {
        this.sessionCleanup(request);
        int langId = UserSession.getCurrent().getLanguageId();
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(request);
        AssetTransferModel transferModel = this.getModel();
        this.getModel().setCompletedFlag("Y");
        transferModel.setTaskId(taskId);
        transferModel.getWorkflowActionDto().setReferenceId(applicationId);
        transferModel.getWorkflowActionDto().setTaskId(taskId);
        // load new information as recorded in the transfer request
        transferModel.loadTransferDetails();
       // model.addAttribute("mode", "APR");
        final TransferDTO transDTO = transferModel.getTransDTO();
        // populate the existing data from asset records
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(transDTO.getAssetCode());
        Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        transDTO.setAssetClass2(detDTO.getAssetInformationDTO().getAssetClass2());
        transferModel.setDepartmentsList(iTbDepartmentService.findAll());
        transferModel.setLocList(iLocationMasService.fillAllActiveLocationByOrgId(orgId));
		if (detDTO.getAssetInformationDTO().getLocation() != null) {
			String loc = iLocationMasService.getLocationNameById(detDTO.getAssetInformationDTO().getLocation(), orgId);
			transDTO.setCurrentLocationDesc(loc);
		}

		if (detDTO.getAssetInformationDTO().getAstAvlstatus() != null) {
			Department dept = tbDepartmentService
					.findDepartmentByCode(detDTO.getAssetInformationDTO().getAstAvlstatus());
			transDTO.setDepartment(dept.getDpDeptid());
		}
        // employee list make alphabetical order or full name
        // D#38594
        List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgId);
        employeeList.forEach(employee -> {
            employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname() +" " +employee.getDesignName()); 
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        // transferModel.setEmpList(employeeService.getAllEmployee(orgId));
        transferModel.setEmpList(employeeList);
        transferModel.setLookUpList(loadAssetCodes(orgId, transferModel.getTransDTO().getDeptCode()));
        transferModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        transferModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(request));
        transDTO.setAssetCode(detDTO.getAssetInformationDTO().getAssetId());
        
        transDTO.setAssetSrNo(String.valueOf(detDTO.getAssetInformationDTO().getAssetId()));
        transDTO.setAssetDesc(detDTO.getAssetInformationDTO().getDetails());
        
        if (transDTO.getTransferType() != null && transDTO.getTransferType().equals("trans-emp")) {
            transDTO.setEmpDesignation(empDesignation(transDTO.getTransferEmployee(), request));
        }
        transferModel
                .setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getLocation() != null) {
            TbLocationMas locmas = iLocationMasService.findById(detDTO.getAssetClassificationDTO().getLocation());
            if (langId != 2) {
                transDTO.setCurrentLocationDesc(locmas.getLocNameEng());
            } else {
                transDTO.setCurrentLocationDesc(locmas.getLocNameReg());
            }
        }
        if (detDTO.getAssetClassificationDTO() != null && detDTO.getAssetClassificationDTO().getCostCenter() != null) {
            transDTO.setCurrentCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
            // set current cost center
            if (StringUtils.isNotEmpty(transDTO.getCurrentCostCenter())) {
                LookUp accoundSecHead = transferModel.getAccountHead().stream()
                        .filter(account -> account.getLookUpDesc().equals(transDTO.getCurrentCostCenter())).findFirst()
                        .orElse(null);
                transDTO.setCurrentCostCenter(accoundSecHead != null ? String.valueOf(accoundSecHead.getLookUpId()) : null);
            }
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian() != null) {
            transDTO.setCustodian(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getCustodian());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            transDTO.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
        }
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO() != null
                && detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId() != null) {
            transDTO.setCurrentEmployee(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getEmployeeId());
        }
        transDTO.setOrgId(orgId);
        transDTO.setDeptCode(detDTO.getAssetInformationDTO().getDeptCode());
       
        List<CFCAttachment> attachment = cfcAttachmentDAO.getDocumentUploadedByApplicationId(transDTO.getTransferId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        List<AttachDocs> attachDocs = new ArrayList<AttachDocs>();
        attachment.forEach(attach -> {
            AttachDocs attDoc = new AttachDocs();
            attDoc.setAttId(attach.getAttId());
            attDoc.setAttFname(attach.getAttFname());
            attDoc.setAttPath(attach.getAttPath());
            attachDocs.add(attDoc);
        });
        
        this.getModel().setAttachDocsList(attachDocs);
        UserSession.getCurrent().setModuleDeptCode(transferModel.getTransDTO().getDeptCode());

        return new ModelAndView("AssetTransferApproval", MainetConstants.FORM_NAME, this.getModel());
    }

    
    
}