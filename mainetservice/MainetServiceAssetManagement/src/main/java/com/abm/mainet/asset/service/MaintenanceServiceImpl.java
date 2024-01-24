/**
 * 
 */
package com.abm.mainet.asset.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.asset.mapper.DepreciationCalculationMapper;
import com.abm.mainet.asset.repository.AssetInformationRepo;
import com.abm.mainet.asset.ui.dto.AssetAccountPostDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.CalculationDTO;
import com.abm.mainet.asset.ui.dto.DocumentDto;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalListDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostListDTO;
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
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.ReflectionUtils;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

/**
 * @author satish.rathore
 *
 */
/**
 * @author satish.rathore
 *
 */
@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.asset.service.IMaintenanceService")
@Path("/maintenance")
@Api(value = "/maintenance")
@Service
public class MaintenanceServiceImpl implements IMaintenanceService {

    @Autowired
    private ILocationMasService iLocationMasService;
    @Autowired
    private IInformationService infoService;
    @Autowired
    private TbDepartmentService deptService;
    @Autowired
    private IClassificationInfoService classInfoService;
    @Autowired
    private IDepreciationService depreService;
    @Autowired
    private IInsuranceService insurService;
    @Autowired
    private ILeasingCompanyService leasingService;
    @Autowired
    private ILinearAssetService lineartService;
    @Autowired
    private IPurchaseInfoService purchaseService;
    @Autowired
    private IRealEstateInfoService realEstateService;
    @Autowired
    private IServiceInfoService serviceInfoService;
    @Autowired
    IFileUploadService assetFileUpload;
    @Resource
    IDocumentUploadService documentUploadService;
    @Autowired
    private IAssetInformationService astService;
    @Autowired
    private IAssetValuationService valuationService;
    @Autowired
    private TbFinancialyearService financialyearService;
    @Autowired
    private IDepreciationCalculationDetailsService calculateService;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Autowired
    private ServiceMasterService iServiceMasterService;
    @Autowired
    private IAssetWorkflowService assetWorkFlowService;

    @Autowired
    private AssetInformationRepo assetRepo;

    private static final Logger LOGGER = Logger.getLogger(MaintenanceServiceImpl.class);

    @Autowired
    private Validator validator;

    public <T> void validateConstraints(T bean, Class<T> beanClass, BindingResult bindResult) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (violations.size() == 0) {
            return;
        }
        if (bindResult != null) {
            for (ConstraintViolation<T> violation : violations) {
                bindResult.addError(new ObjectError(violation.getRootBeanClass().getName(), violation.getMessage()));
            }
        } else {
            throw new ConstraintViolationException(violations);
        }
    }

    /**
     * Registers a new asset
     * 
     * @param detailsDTO
     * @param orgId
     * @param auditDTO
     */
    @POST
    @Path("/register")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public void registerDetailDto(@RequestBody final AssetDetailsDTO detailsDTO) {
    	if(detailsDTO.getAssetInformationDTO().getDeptCode() != null && detailsDTO.getAssetInformationDTO().getDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT) ) {
    		checkLeaseAndLinear(detailsDTO);
    	}
    	validateConstraints(detailsDTO, AssetDetailsDTO.class, null);
        Date date = new Date();
        // AssetValuationDetailsDTO valuationDTO = new AssetValuationDetailsDTO();
        detailsDTO.getAssetInformationDTO().setOrgId(detailsDTO.getOrgId());
        if (detailsDTO.getAuditDTO() == null) {
            detailsDTO.setAuditDTO(new AuditDetailsDTO());
        }
        detailsDTO.getAssetInformationDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
        detailsDTO.getAssetInformationDTO().setCreationDate(date);
        detailsDTO.getAssetInformationDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
        Long astId = infoService.saveInfo(detailsDTO.getAssetInformationDTO());
        detailsDTO.getAssetInformationDTO().setAssetId(astId);

        try {
        	if(detailsDTO.getAssetInformationDTO().getDeptCode().equals(MainetConstants.AssetManagement.ASSET_MANAGEMENT)) {
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetClassificationDTO(), AssetClassificationDTO.class)) {
                detailsDTO.getAssetClassificationDTO().setAssetId(astId);
                detailsDTO.getAssetClassificationDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAssetClassificationDTO().setCreationDate(date);
                detailsDTO.getAssetClassificationDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                classInfoService.saveClassInfo(detailsDTO.getAssetClassificationDTO());
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstDepreChartDTO(), AssetDepreciationChartDTO.class)) {
                detailsDTO.getAstDepreChartDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAstDepreChartDTO().setCreationDate(date);
                detailsDTO.getAstDepreChartDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                detailsDTO.getAstDepreChartDTO().setAssetId(astId);
                depreService.saveDepreciation(detailsDTO.getAstDepreChartDTO());
            } else {
                AssetDepreciationChartDTO chartDto = new AssetDepreciationChartDTO();
                chartDto.setDeprApplicable(false);
                chartDto.setAssetId(astId);
                detailsDTO.setAstDepreChartDTO(chartDto);
                detailsDTO.getAstDepreChartDTO().setAssetId(astId);
                depreService.saveDepreciation(detailsDTO.getAstDepreChartDTO());
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstInsuDTO(), AssetInsuranceDetailsDTO.class)
                    && StringUtils.isNotEmpty(detailsDTO.getAstInsuDTO().getInsuranceNo())) {
                detailsDTO.getAstInsuDTO().setAssetId(astId);
                detailsDTO.getAstInsuDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAstInsuDTO().setCreationDate(date);
                detailsDTO.getAstInsuDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                insurService.saveInsurance(detailsDTO.getAstInsuDTO());
            }

            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstLeaseDTO(), AssetLeasingCompanyDTO.class)) {
                detailsDTO.getAstLeaseDTO().setAssetId(astId);
                detailsDTO.getAstLeaseDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAstLeaseDTO().setCreationDate(date);
                detailsDTO.getAstLeaseDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                leasingService.saveLeasingInfo(detailsDTO.getAstLeaseDTO());
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstLinearDTO(), AssetLinearDTO.class)) {
                detailsDTO.getAstLinearDTO().setAssetId(astId);
                detailsDTO.getAstLinearDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAstLinearDTO().setCreationDate(date);
                detailsDTO.getAstLinearDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                lineartService.saveLinearInfo(detailsDTO.getAstLinearDTO());
            }
        
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetPurchaseInformationDTO(),
                    AssetPurchaseInformationDTO.class)) {
                detailsDTO.getAssetPurchaseInformationDTO().setAssetId(astId);
                detailsDTO.getAssetPurchaseInformationDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAssetPurchaseInformationDTO().setCreationDate(date);
                detailsDTO.getAssetPurchaseInformationDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                purchaseService.savePurchaseInfo(detailsDTO.getAssetPurchaseInformationDTO());
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetRealEstateInfoDTO(),
                    AssetRealEstateInformationDTO.class)
                    && StringUtils.isNotEmpty(detailsDTO.getAssetRealEstateInfoDTO().getPropertyRegistrationNo())) {
                detailsDTO.getAssetRealEstateInfoDTO().setAssetId(astId);
                detailsDTO.getAssetRealEstateInfoDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                detailsDTO.getAssetRealEstateInfoDTO().setCreationDate(date);
                detailsDTO.getAssetRealEstateInfoDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                realEstateService.saveRealEstate(detailsDTO.getAssetRealEstateInfoDTO());
            }

            if (detailsDTO.getAstSerList() != null && !detailsDTO.getAstSerList().isEmpty()) {
                if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstSerList().get(0),
                        AssetServiceInformationDTO.class)
                        && StringUtils.isNotEmpty(detailsDTO.getAstSerList().get(0).getServiceNo())) {
                    detailsDTO.getAstSerList().get(0).setAssetId(astId);
                    detailsDTO.getAstSerList().get(0).setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                    detailsDTO.getAstSerList().get(0).setCreationDate(date);
                    detailsDTO.getAstSerList().get(0).setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                    serviceInfoService.saveServiceInfo(detailsDTO.getAstSerList().get(0));
                }
            }
            if (detailsDTO.getAttachments() != null && !detailsDTO.getAttachments().isEmpty()) {
             // D#135788 TB_ATTACH_CFC Prepare file upload
                Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(
                        detailsDTO.getAssetInformationDTO().getDeptCode(),
                        MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                        MainetConstants.ITAssetManagement.IT_ASSET_REG_SERVICE_CODE);
             
                ServiceMaster serviceMast = iServiceMasterService
                        .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                                detailsDTO.getAssetInformationDTO().getOrgId());
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setOrgId(detailsDTO.getAssetInformationDTO().getOrgId());
                requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
                requestDTO.setServiceId(serviceMast.getSmServiceId());
                requestDTO.setUserId(detailsDTO.getAuditDTO().getEmpId());

                requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
                FileUploadDTO fileUploadDTO = new FileUploadDTO();
                final String assetId = detailsDTO.getAssetInformationDTO().getDeptCode()
                        + MainetConstants.operator.FORWARD_SLACE + astId.toString();
                //fileUploadDTO.setIdfId(assetId);
                requestDTO.setReferenceId(assetId);//ex:ASTorIAST/PK
                //fileUploadDTO.setDepartmentName(detailsDTO.getAssetInformationDTO().getDeptCode());
                documentUploadService.documentUpload(detailsDTO.getAttachments(), requestDTO, detailsDTO.getOrgId(),
                        detailsDTO.getAuditDTO().getEmpId());
            }
        	}else {//this block is specific for IT Asset
        		if (detailsDTO.getAstSerList() != null && !detailsDTO.getAstSerList().isEmpty()) {
                    if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstSerList().get(0),
                            AssetServiceInformationDTO.class)
                            && detailsDTO.getAstSerList().get(0).getServiceProvider() != null && !detailsDTO.getAstSerList().get(0).getServiceProvider().equals("")) {
                        detailsDTO.getAstSerList().get(0).setAssetId(astId);
                        detailsDTO.getAstSerList().get(0).setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                        detailsDTO.getAstSerList().get(0).setCreationDate(date);
                        detailsDTO.getAstSerList().get(0).setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                        serviceInfoService.saveServiceInfo(detailsDTO.getAstSerList().get(0));
                    }
                }
        		if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetPurchaseInformationDTO(),
                        AssetPurchaseInformationDTO.class)) {
                    detailsDTO.getAssetPurchaseInformationDTO().setAssetId(astId);
                    detailsDTO.getAssetPurchaseInformationDTO().setCreatedBy(detailsDTO.getAuditDTO().getEmpId());
                    detailsDTO.getAssetPurchaseInformationDTO().setCreationDate(date);
                    detailsDTO.getAssetPurchaseInformationDTO().setLgIpMac(detailsDTO.getAuditDTO().getEmpIpMac());
                    purchaseService.savePurchaseInfo(detailsDTO.getAssetPurchaseInformationDTO());
                }
        		 if (detailsDTO.getAttachments() != null && !detailsDTO.getAttachments().isEmpty()) {
                     FileUploadDTO fileUploadDTO = new FileUploadDTO();
                     final String assetId = detailsDTO.getAssetInformationDTO().getDeptCode()
                             + MainetConstants.operator.FORWARD_SLACE + astId.toString();
                     fileUploadDTO.setIdfId(assetId);
                     fileUploadDTO.setDepartmentName(detailsDTO.getAssetInformationDTO().getDeptCode());
                     
                  // D#135788 TB_ATTACH_CFC Prepare file upload
                     Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(
                             detailsDTO.getAssetInformationDTO().getDeptCode(),
                             MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                             MainetConstants.ITAssetManagement.IT_ASSET_REG_SERVICE_CODE);
                  //Test here
                     ServiceMaster serviceMast = iServiceMasterService
                             .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                                     detailsDTO.getAssetInformationDTO().getOrgId());
                     RequestDTO requestDTO = new RequestDTO();
                     requestDTO.setOrgId(detailsDTO.getAssetInformationDTO().getOrgId());
                     requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
                     requestDTO.setServiceId(serviceMast.getSmServiceId());
                     requestDTO.setUserId(detailsDTO.getAuditDTO().getEmpId());

                     requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
                     requestDTO.setReferenceId(assetId);//ex:IAST/PK
                     documentUploadService.documentUpload(detailsDTO.getAttachments(), requestDTO, detailsDTO.getOrgId(),
                             detailsDTO.getAuditDTO().getEmpId());
                 }
        	}
            // addValuationEntry(detailsDTO, date, valuationDTO, astId);

        } catch (IntrospectionException e) {
            throw new FrameworkException("Exception occurs while Asset Registration ", e);
        }
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional
    public void addValuationEntry(final AssetDetailsDTO detailsDTO, Date date, AssetValuationDetailsDTO valuationDTO,
            Long astId) {
        try {
            final AssetPurchaseInformationDTO purchaseDTO = detailsDTO.getAssetPurchaseInformationDTO();
            if (ReflectionUtils.isBeanPopulated(purchaseDTO, AssetPurchaseInformationDTO.class)) {
                try {
                    // Defect #7260 User is unable to retire current year created assets.
                    FinancialYear financiaYear = null;
                    if (purchaseDTO.getInitialBookDate() != null) {
                        financiaYear = financialyearService
                                .getFinanciaYearByDate(purchaseDTO.getInitialBookDate());
                    } else {
                        financiaYear = financialyearService
                                .getFinanciaYearByDate(purchaseDTO.getDateOfAcquisition());
                    }
                    valuationDTO.setInitialBookValue(purchaseDTO.getCostOfAcquisition());
                    valuationDTO.setInitialBookDate(purchaseDTO.getDateOfAcquisition());

                    if (purchaseDTO.getInitialBookDate() != null) {
                        valuationDTO.setCurrentBookDate(purchaseDTO.getInitialBookDate());
                    } else {
                        valuationDTO.setCurrentBookDate(purchaseDTO.getDateOfAcquisition());
                    }
                    if (financiaYear != null) {
                        valuationDTO.setBookFinYear(financiaYear.getFaYear());
                    }
                    valuationDTO.setPreviousBookDate(purchaseDTO.getDateOfAcquisition());
                    if (purchaseDTO.getInitialBookValue() != null) {
                        valuationDTO.setPreviousBookValue(purchaseDTO.getInitialBookValue());
                        valuationDTO.setCurrentBookValue(purchaseDTO.getInitialBookValue());
                    } else {
                        valuationDTO.setPreviousBookValue(purchaseDTO.getCostOfAcquisition());
                        valuationDTO.setCurrentBookValue(purchaseDTO.getCostOfAcquisition());
                    }
                    final AssetDepreciationChartDTO depDTO = detailsDTO.getAstDepreChartDTO();
                    if (ReflectionUtils.isBeanPopulated(depDTO, AssetDepreciationChartDTO.class)) {
                        valuationDTO.setAccumDeprValue(depDTO.getInitialAccumDepreAmount());
                    } else {
                        valuationDTO.setAccumDeprValue(BigDecimal.ZERO);
                    }
                    valuationDTO.setAssetId(astId);
                    valuationDTO.setCreationDate(date);
                    valuationDTO.setChangetype(MainetConstants.AssetManagement.ValuationType.NEW.getValue());
                    valuationDTO.setDeprValue(BigDecimal.ZERO);
                    valuationDTO.setOrgId(detailsDTO.getAssetInformationDTO().getOrgId());
                    valuationService.addEntry(valuationDTO);
                } catch (Exception exp) {
                    throw new FrameworkException("Problem occur during valuation entry ", exp);
                }
            } else {
                throw new FrameworkException(
                        "Acquisition Details are not available for asset id " + astId + " during asset registration");
            }

        } catch (IntrospectionException e) {
            throw new FrameworkException("Exception occurs while valuation entry ", e);
        }
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional
    public void updateDetailDto(final AssetDetailsDTO detailsDTO, final AuditDetailsDTO auditDTO) {

        checkLeaseAndLinear(detailsDTO);
        validateConstraints(detailsDTO, AssetDetailsDTO.class, null);
        Long assetId = detailsDTO.getAssetInformationDTO().getAssetId();
        updateInformation(assetId, detailsDTO.getAssetInformationDTO(), auditDTO);
        try {
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetClassificationDTO(), AssetClassificationDTO.class)) {
                updateClassification(assetId, detailsDTO.getAssetClassificationDTO(), auditDTO);
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAssetPurchaseInformationDTO(),
                    AssetPurchaseInformationDTO.class)) {
                updatePurchaseInformation(assetId, detailsDTO.getAssetPurchaseInformationDTO(), auditDTO);
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstSerInfoDTO(), AssetServiceInformationDTO.class)) {
                updateServiceInformation(assetId, detailsDTO.getAstSerInfoDTO(), auditDTO);
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstInsuDTO(), AssetInsuranceDetailsDTO.class)) {
                updateInsuranceDetails(assetId, detailsDTO.getAstInsuDTO(), auditDTO);
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstLeaseDTO(), AssetLeasingCompanyDTO.class)) {
                updateLeaseInformation(assetId, detailsDTO.getAstLeaseDTO(), auditDTO);
            }
            if (detailsDTO.getAstDepreChartDTO().getDeprApplicable() != null
                    && detailsDTO.getAstDepreChartDTO().getDeprApplicable()) {
                if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstDepreChartDTO(), AssetDepreciationChartDTO.class)) {
                    updateDepreciationChart(assetId, detailsDTO.getAstDepreChartDTO(), auditDTO);
                }
            }
            if (ReflectionUtils.isBeanPopulated(detailsDTO.getAstLinearDTO(), AssetLinearDTO.class)) {
                updateLinearInformation(assetId, detailsDTO.getAstLinearDTO(), auditDTO);
            }
            if (detailsDTO.getAttachments() != null && !detailsDTO.getAttachments().isEmpty()) {
                final DocumentDto docDTO = new DocumentDto();
                docDTO.setDocumentAttached(detailsDTO.getAttachments());
                updateDocumentDetails(assetId, detailsDTO.getAssetInformationDTO().getOrgId(), auditDTO, docDTO,
                        detailsDTO.getAssetInformationDTO().getDeptCode());
            }
        } catch (IntrospectionException e) {
            throw new FrameworkException("Exception occurs while Asset Registration in send back process", e);
        }
    }

    /**
     * Updates just the data passed in AssetInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset information to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/information/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public void updateInformation(@PathParam("assetId") final Long assetId, @RequestBody final AssetInformationDTO dto,
            @BeanParam final AuditDetailsDTO auditDTO) {
        dto.setAssetId(astService.getInfo(assetId).getAssetId());
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        infoService.updateInformation(assetId, dto);
    }

    @Override
    @Transactional
    public Long saveInformationRev(Long assetId, AssetInformationDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return infoService.saveInformationRev(assetId, dto);
    }

    /**
     * Updates just the data passed in AssetClassificationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset classification details to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/classification/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateClassification(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetClassificationDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetClassificationDTO classDTO = astService.getClassification(assetId);
        if (classDTO != null) {
            if (classDTO.getAssetClassificationId() != null && classDTO.getAssetClassificationId() != 0) {
                dto.setAssetId(classDTO.getAssetId());
                dto.setAssetClassificationId(classDTO.getAssetClassificationId());
                dto.setCreatedBy(classDTO.getCreatedBy());
                dto.setCreationDate(classDTO.getCreationDate());
                dto.setLgIpMac(classDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());

        return classInfoService.updateClassInfo(assetId, dto);
    }

    @Override
    @Transactional
    public Long saveClassificationRev(Long assetId, AssetClassificationDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return classInfoService.saveClassificationRev(assetId, dto, auditDTO);
    }

    /**
     * Updates just the data passed in AssetPurchaseInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset purchase details to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/purchase/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updatePurchaseInformation(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetPurchaseInformationDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetPurchaseInformationDTO purchaseDTO = astService.getPurchaseInfo(assetId);
        if (purchaseDTO != null) {
            if (purchaseDTO.getAssetPurchaserId() != null && purchaseDTO.getAssetPurchaserId() != 0) {
                dto.setAssetId(purchaseDTO.getAssetId());
                dto.setAssetPurchaserId(purchaseDTO.getAssetPurchaserId());
                dto.setCreatedBy(purchaseDTO.getCreatedBy());
                dto.setCreationDate(purchaseDTO.getCreationDate());
                dto.setLgIpMac(purchaseDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return purchaseService.updatePurchaseInfoByAssetId(assetId, dto);
    }

    @Override
    public Long savePurchaseInformationRev(Long assetId, AssetPurchaseInformationDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return purchaseService.savePurchaseInfoRev(assetId, dto);
    }

    /**
     * Updates just the data passed in AssetPurchaseInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto realEstate to be updated
     * @param auditDTO audit related details to be updated
     */
    @Override
    public Long updateRealEstateInformation(Long assetId, AssetRealEstateInformationDTO dto, AuditDetailsDTO auditDTO) {
        final AssetRealEstateInformationDTO realEstateDTO = astService.getRealEstateInfo(assetId);
        if (realEstateDTO != null) {
            if (realEstateDTO.getAssetRealEstId() != null && realEstateDTO.getAssetRealEstId() != 0) {
                dto.setAssetId(realEstateDTO.getAssetId());
                dto.setAssetRealEstId(realEstateDTO.getAssetRealEstId());
                dto.setCreatedBy(realEstateDTO.getCreatedBy());
                dto.setCreationDate(realEstateDTO.getCreationDate());
                dto.setLgIpMac(realEstateDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return realEstateService.updateRealEstateByAssetId(assetId, dto);
    }

    @Override
    public Long saveupdateRealEstateInformationRev(Long assetId, AssetRealEstateInformationDTO dto,
            AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return realEstateService.saveRealEstateRev(assetId, dto);
    }

    /**
     * Updates just the data passed in AssetLeasingCompanyDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset lease details to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/lease/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateLeaseInformation(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetLeasingCompanyDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetLeasingCompanyDTO leaseDTO = astService.getLeaseInfo(assetId);
        if (leaseDTO != null) {
            if (leaseDTO.getAssetLeasingId() != null && leaseDTO.getAssetLeasingId() != 0) {
                dto.setAssetId(leaseDTO.getAssetId());
                dto.setAssetLeasingId(leaseDTO.getAssetLeasingId());
                dto.setCreatedBy(leaseDTO.getCreatedBy());
                dto.setCreationDate(leaseDTO.getCreationDate());
                dto.setLgIpMac(leaseDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return leasingService.updateLeasingInfoByAssetId(assetId, dto);
    }

    /**
     * Updates just the data passed in AssetPurchaseInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset Lease to be updated
     * @param auditDTO audit related details to be updated
     */

    @Override
    public Long saveLeaseInformationRev(Long assetId, AssetLeasingCompanyDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return leasingService.saveLeasingInfoRev(assetId, dto);

    }

    /**
     * Updates just the data passed in AssetServiceInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset service information to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/service/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateServiceInformation(@PathParam("assetId") final Long assetId,
            @RequestBody final List<AssetServiceInformationDTO> listDto, @BeanParam final AuditDetailsDTO auditDTO) {
        final List<AssetServiceInformationDTO> listServiceDTO = astService.getServiceInfo(assetId);
        if (listServiceDTO != null && !listServiceDTO.isEmpty()) {
            for (AssetServiceInformationDTO listServiceDTOs : listServiceDTO) {
                if (listServiceDTOs.getAssetServiceId() != null && listServiceDTOs.getAssetServiceId() != 0) {
                    for (AssetServiceInformationDTO listDtos : listDto) {
                        if (listDtos.getAssetServiceId() == listServiceDTOs.getAssetServiceId()) {
                            listDtos.setAssetId(listServiceDTOs.getAssetId());
                            listDtos.setCreatedBy(listServiceDTOs.getCreatedBy());
                            listDtos.setCreationDate(listServiceDTOs.getCreationDate());
                            listDtos.setLgIpMac(listServiceDTOs.getLgIpMac());
                            break;
                        }

                    }
                }
            }
        }
        if (listDto != null && !listDto.isEmpty()) {
            for (AssetServiceInformationDTO listDtos : listDto) {
                if (listDtos.getAssetServiceId() == null) {
                    listDtos.setUpdatedBy(auditDTO.getEmpId());
                    listDtos.setUpdatedDate(new Date());
                    listDtos.setLgIpMacUpd(auditDTO.getEmpIpMac());
                }
            }
        }
        return serviceInfoService.updateServiceInfo(assetId, listDto);
    }

    /**
     * Updates just the data passed in AssetServiceInformationDTO in case of send back
     * 
     * @param assetId asset to be updated
     * @param dto asset service information to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod(exclude = true)
    @POST
    @Path("/insurance/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateServiceInformation(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetServiceInformationDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetServiceInformationDTO serviceDTO = astService.getServiceInfo(assetId).get(0);
        if (serviceDTO != null) {
            if (serviceDTO.getAssetServiceId() != null && serviceDTO.getAssetServiceId() != 0) {
                dto.setAssetId(serviceDTO.getAssetId());
                dto.setAssetServiceId(serviceDTO.getAssetServiceId());
                dto.setCreatedBy(serviceDTO.getCreatedBy());
                dto.setCreationDate(serviceDTO.getCreationDate());
                dto.setLgIpMac(serviceDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return serviceInfoService.updateServiceInfo(assetId, dto);
    }

    @Override
    @Transactional
    public Long saveServiceInformationRev(Long assetIdRev, List<AssetServiceInformationDTO> dtoList,
            AuditDetailsDTO auditDTO, Long orgId) {
        if (dtoList != null && !dtoList.isEmpty()) {
            for (AssetServiceInformationDTO dtoLists : dtoList) {
                dtoLists.setUpdatedBy(auditDTO.getEmpId());
                dtoLists.setUpdatedDate(new Date());
                dtoLists.setLgIpMacUpd(auditDTO.getEmpIpMac());
            }
        }
        return serviceInfoService.saveServiceInfoRev(dtoList, assetIdRev, orgId);
    }

    @Override
    public Long saveRealStateInfoRev(Long assetIdRev, AssetRealEstateInformationDTO dto, AuditDetailsDTO auditDTO,
            Long orgId) {
        if (dto != null) {
            dto.setUpdatedBy(auditDTO.getEmpId());
            dto.setUpdatedDate(new Date());
            dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        }
        return serviceInfoService.saveRealEstateInfoRev(dto, assetIdRev, orgId);
    }

    /**
     * Updates just the data passed in AssetInsuranceDetailsDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset insurance details to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/insurance/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateInsuranceDetails(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetInsuranceDetailsDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetInsuranceDetailsDTO insuranceDTO = astService.getInsuranceInfo(assetId);
        if (insuranceDTO != null) {
            if (insuranceDTO.getAssetInsuranceId() != null && insuranceDTO.getAssetInsuranceId() != 0) {
                dto.setAssetId(insuranceDTO.getAssetId());
                dto.setAssetInsuranceId(insuranceDTO.getAssetInsuranceId());
                dto.setCreatedBy(insuranceDTO.getCreatedBy());
                dto.setCreationDate(insuranceDTO.getCreationDate());
                dto.setLgIpMac(insuranceDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return insurService.updateInsuranceInfo(assetId, dto);
    }

    @Override
    @Transactional
    public Long updateInsuranceDetailsListRev(List<AssetInsuranceDetailsDTO> insDTOList, AuditDetailsDTO auditDTO,
            Long astId) {
        Long squenceNo = null;

        // List<AssetInsuranceDetailsDTO> assetInsuDTOSave = new
        // ArrayList<AssetInsuranceDetailsDTO>();

        squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE,
                "TB_AST_INSURANCE_REV", "REV_GRP_ID", UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.FlagC, null);

        // final List<AssetInsuranceDetailsDTO> insDTOList =
        // astService.getInsuranceInfoListRev(groupId);

        for (int i = 0; i < insDTOList.size(); i++) {

            AssetInsuranceDetailsDTO dto = insDTOList.get(i);
            dto.setRevGrpId(squenceNo);
            dto.setAssetId(astId);
            if (dto.getRevGrpIdentity() != null && dto.getRevGrpIdentity().equalsIgnoreCase("N")) {
                List<AssetInsuranceDetailsDTO> astInsuDTOList = new ArrayList<AssetInsuranceDetailsDTO>();
                astInsuDTOList.add(dto);
                insurService.saveInsuranceRev(astInsuDTOList);
            } else if (dto.getRevGrpIdentity() != null && dto.getRevGrpIdentity().equalsIgnoreCase("O")) {
                dto.setUpdatedBy(auditDTO.getEmpId());
                dto.setUpdatedDate(new Date());
                dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
                insurService.updateInsuranceInfoRev(squenceNo, dto);
            }
        }

        return squenceNo;
    }

    @Override
    public Long saveInsuranceDetails(Long assetId, AssetInsuranceDetailsDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        dto.setAssetId(assetId);
        return insurService.saveInsurance(dto);

    }

    @Override
    public Long saveInsuranceDetailsRev(Long assetIdRev, final List<AssetInsuranceDetailsDTO> dtoList,
            AuditDetailsDTO auditDTO) {
        for (int i = 0; i < dtoList.size(); i++) {
            AssetInsuranceDetailsDTO dto = dtoList.get(i);
            dto.setUpdatedBy(auditDTO.getEmpId());
            dto.setUpdatedDate(new Date());
            dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        }

        return insurService.saveInsuranceRev(dtoList);

    }

    public List<AssetInsuranceDetailsDTO> getAllInsuranceDetailsList(final Long assetId,
            final AssetInsuranceDetailsDTO dto) {
        return insurService.getAllInsuranceDetailsList(assetId, dto);
    }

    /**
     * Updates just the data passed in AssetDepreciationChartDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset depreciation details to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/depreciationChart/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateDepreciationChart(@PathParam("assetId") final Long assetId,
            @RequestBody final AssetDepreciationChartDTO dto, @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetDepreciationChartDTO chartDTO = astService.getDepreciationInfo(assetId);
        if (chartDTO != null) {
            if (chartDTO.getAssetDeprChartId() != null && chartDTO.getAssetDeprChartId() != 0) {
                dto.setAssetId(chartDTO.getAssetId());
                dto.setAssetDeprChartId(chartDTO.getAssetDeprChartId());
                dto.setCreatedBy(chartDTO.getCreatedBy());
                dto.setCreationDate(chartDTO.getCreationDate());
                dto.setLgIpMac(chartDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return depreService.updateDepreciationByAssetId(assetId, dto);
    }

    /*
     * @Override public void retire() { // TODO Auto-generated method stub }
     */

    @Override
    public Long saveDepreciationChartRev(Long assetId, AssetDepreciationChartDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return depreService.saveDepreciationRev(dto);

    }

    /**
     * Updates just the data passed in AssetLinearDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset linear information to be updated
     * @param auditDTO audit related details to be updated
     */
    @WebMethod
    @POST
    @Path("/linear/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long updateLinearInformation(@PathParam("assetId") final Long assetId, @RequestBody final AssetLinearDTO dto,
            @BeanParam final AuditDetailsDTO auditDTO) {
        final AssetLinearDTO lineDTO = astService.getLinear(assetId);
        if (lineDTO != null) {
            if (lineDTO.getAssetLinearId() != null && lineDTO.getAssetLinearId() != 0) {
                dto.setAssetId(lineDTO.getAssetId());
                dto.setAssetLinearId(lineDTO.getAssetLinearId());
                dto.setCreatedBy(lineDTO.getCreatedBy());
                dto.setCreationDate(lineDTO.getCreationDate());
                dto.setLgIpMac(lineDTO.getLgIpMac());
            }
        }
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return lineartService.updateLinearInfoByAssetId(assetId, dto);
    }

    @Override
    public Long saveLinearInformationRev(Long assetId, AssetLinearDTO dto, AuditDetailsDTO auditDTO) {
        dto.setUpdatedBy(auditDTO.getEmpId());
        dto.setUpdatedDate(new Date());
        dto.setLgIpMacUpd(auditDTO.getEmpIpMac());
        return lineartService.saveLinearInfoRev(dto);
    }

    @WebMethod(exclude = true)
    @POST
    @Path("/document/{orgId}/{assetId}")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public void updateDocumentDetails(@PathParam("assetId") final Long assetId, @PathParam("orgId") final Long orgId,
            @BeanParam final AuditDetailsDTO auditDTO, @RequestBody final DocumentDto dto, String moduleDeptCode) {
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        final String assetIds = moduleDeptCode
                + MainetConstants.operator.FORWARD_SLACE + assetId.toString();
        //fileUploadDTO.setIdfId(assetIds);
        
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(
                moduleDeptCode,
                MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_ASSET_REG_SERVICE_CODE);
   
        ServiceMaster serviceMast = iServiceMasterService
                .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),orgId);
        RequestDTO requestDTO = new RequestDTO();
        // requestDTO.setReferenceId(refId);
        requestDTO.setOrgId(orgId);
        requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(auditDTO.getEmpId());

        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        requestDTO.setReferenceId(assetIds);//ex:ASTorIAST/PK
        
        
        documentUploadService.documentUpload(dto.getDocumentAttached(), requestDTO, orgId, auditDTO.getEmpId());
        documentUploadService.documentDelete(auditDTO.getEmpId(), dto.getDocumentId());
    }

    /*
     * @WebMethod(exclude = true)
     * @Override
     * @Transactional(propagation = Propagation.REQUIRES_NEW) public void postDepreciationVoucher(final AssetValuationDetailsDTO
     * valuationDTO, String activeFlag, Organisation org, String narration) { if
     * (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) { VoucherPostDTO voucherDto = prepareVoucherDto(valuationDTO, org,
     * activeFlag, narration); if (voucherDto == null) { throw new
     * FrameworkException("Could not generate voucher posting details"); } VoucherPostListDTO dto = new VoucherPostListDTO();
     * dto.setVoucherdto(new ArrayList<VoucherPostDTO>()); dto.getVoucherdto().add(voucherDto); final ResponseEntity<?>
     * responseValidate = RestClient.callRestTemplateClient(dto, ServiceEndpoints.ACCOUNT_POSTING_VALIDATE); if ((responseValidate
     * != null) && (responseValidate.getStatusCode() == HttpStatus.OK)) {
     * LOGGER.info("Account Voucher Posting validated successfully"); final ResponseEntity<?> response =
     * RestClient.callRestTemplateClient(dto, ServiceEndpoints.ACCOUNT_POSTING); if ((response != null) &&
     * (response.getStatusCode() == HttpStatus.OK)) { LOGGER.info("Account Voucher Posting done successfully"); } else {
     * LOGGER.error("Account Voucher Posting failed due to :" + (response != null ? response.getBody() : MainetConstants.BLANK));
     * throw new FrameworkException("Check account integration for tax master: NO itegration found for " ); } } else {
     * LOGGER.error("Account Voucher Posting Validation Failed due to :" + (responseValidate != null ? responseValidate.getBody()
     * : MainetConstants.BLANK)); throw new FrameworkException("Check input values for account posting data for:"); } } } private
     * VoucherPostDTO prepareVoucherDto(final AssetValuationDetailsDTO valuationDTO, final Organisation org, String activeFlag,
     * String narration) { VoucherPostDTO accountPosting = null; if (valuationDTO == null || org == null || narration == null ||
     * narration.equals("")) { throw new FrameworkException(" Valuation DTO or organisation or narration is null"); } if
     * (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) { accountPosting = new VoucherPostDTO(); Long locationId = null;
     * final TbLocationMas locMas = iLocationMasService.findByLocationName(
     * ApplicationSession.getInstance().getMessage("location.LocNameEng"), org.getOrgid()); if
     * ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) { locationId =
     * locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1(); } if (locationId == null || locationId.longValue() == 0) {
     * throw new FrameworkException("Could not find Head Office value for getting revenue location mapping" ); }
     * VoucherPostDetailDTO voucherDetail; LookUp tdpPrefix; accountPosting.setVoucherDetails(new
     * ArrayList<VoucherPostDetailDTO>(0)); tdpPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
     * MainetConstants.AssetManagement.DEPR_ACCT_TMPLT_PRF, MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, org);
     * if (tdpPrefix != null) { accountPosting.setVoucherSubTypeId(tdpPrefix.getLookUpId()); } else { throw new
     * FrameworkException("ADE common parameter for TDP prefix not defined"); } accountPosting.setFieldId(locationId); Date
     * curDate = new Date(); accountPosting.setVoucherDate(curDate); Department dept =
     * deptService.findDepartmentByCode(MainetConstants.AssetManagement. ASSET_MANAGEMENT); if (dept == null) { throw new
     * FrameworkException("AST for asset management department not found"); } accountPosting.setDepartmentId(dept.getDpDeptid());
     * // accountPosting.setVoucherReferenceNo(String.valueOf(valuationDTO. getValuationDetId())); // Not Mandatory at Account //
     * End accountPosting.setNarration(narration); accountPosting.setPayerOrPayee("System for depreciation");
     * accountPosting.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE); accountPosting.setOrgId(org.getOrgid());
     * accountPosting.setCreatedBy(1L); accountPosting.setBillVouPostingDate(curDate); accountPosting.setCreatedDate(curDate);
     * accountPosting.setLgIpMac(Utility.getMacAddress()); Long payModeId = CommonMasterUtility
     * .getValueFromPrefixLookUp(MainetConstants.AssetManagement.PAY_MODE_TRANSFER, MainetConstants.AssetManagement.PAY_MODE_PRF,
     * org) .getLookUpId(); accountPosting.setPayModeId(payModeId); voucherDetail = new VoucherPostDetailDTO();
     * voucherDetail.setVoucherAmount(valuationDTO.getDeprValue()); voucherDetail.setSacHeadId(valuationDTO.getAccountCode());
     * accountPosting.getVoucherDetails().add(voucherDetail); voucherDetail = new VoucherPostDetailDTO();
     * voucherDetail.setVoucherAmount(valuationDTO.getDeprValue()); voucherDetail.setPayModeId(payModeId);
     * accountPosting.getVoucherDetails().add(voucherDetail); } return accountPosting; }
     */

    @WebMethod(exclude = true)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postDepreciationVoucher(final AssetAccountPostDTO postDTO, String activeFlag, Organisation org,
            String narration, List<VoucherPostDetailDTO> voucherDTO, String moduleDeptCode) {
        if (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) {

            VoucherPostDTO voucherDto = prepareVoucherDto(postDTO, org, activeFlag, narration, voucherDTO, moduleDeptCode);
            if (voucherDto == null) {
                throw new FrameworkException("Could not generate voucher posting details");
            }
            VoucherPostListDTO dto = new VoucherPostListDTO();
            dto.setVoucherdto(new ArrayList<VoucherPostDTO>());
            dto.getVoucherdto().add(voucherDto);
            final ResponseEntity<?> responseValidate = RestClient.callRestTemplateClient(dto,
                    ServiceEndpoints.ACCOUNT_POSTING_VALIDATE);
            if ((responseValidate != null) && (responseValidate.getStatusCode() == HttpStatus.OK)) {
                LOGGER.info("Account Voucher Posting validated successfully");
                final ResponseEntity<?> response = RestClient.callRestTemplateClient(dto,
                        ServiceEndpoints.ACCOUNT_POSTING);
                if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                    LOGGER.info("Account Voucher Posting done successfully");
                } else {
                    LOGGER.error("Account Voucher Posting failed due to :"
                            + (response != null ? response.getBody() : MainetConstants.BLANK));

                    throw new FrameworkException("Check account integration for tax master: NO itegration found for ");
                }
            } else {
                LOGGER.error("Account Voucher Posting Validation Failed due to :"
                        + (responseValidate != null ? responseValidate.getBody() : MainetConstants.BLANK));

                throw new FrameworkException("Check input values for account posting data for:");
            }

        }
    }

    private VoucherPostDTO prepareVoucherDto(final AssetAccountPostDTO postDTO, final Organisation org,
            String activeFlag, String narration, List<VoucherPostDetailDTO> voucherDTO, String moduleDeptCode) {
        VoucherPostDTO accountPosting = null;
        if (postDTO == null || org == null || narration == null || narration.equals("") || voucherDTO == null
                || voucherDTO.isEmpty()) {
            throw new FrameworkException(
                    " AssetAccountPost DTO or organisation or narration or voucherDTO list is null");
        }
        if (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) {
            accountPosting = new VoucherPostDTO();
            Long locationId = null;
            final TbLocationMas locMas = iLocationMasService.findByLocationName(
                    ApplicationSession.getInstance().getMessage("location.LocNameEng"), org.getOrgid());
            if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                locationId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
            }
            if (locationId == null || locationId.longValue() == 0) {
                throw new FrameworkException("Could not find Head Office value for getting revenue location mapping");
            }

            if (postDTO.getVoucherSubTypeId() != null && postDTO.getVoucherSubTypeId().intValue() != 0) {
                accountPosting.setVoucherSubTypeId(postDTO.getVoucherSubTypeId());
            } else {
                throw new FrameworkException(
                        postDTO.getVoucherSubTypeName() + " common parameter for TDP prefix not defined");
            }
            accountPosting.setFieldId(locationId);

            accountPosting.setVoucherDate(postDTO.getVoucherDate());
            Department dept = deptService.findDepartmentByCode(moduleDeptCode);
            if (dept == null) {
                throw new FrameworkException("AST for asset management department not found");
            }
            accountPosting.setDepartmentId(dept.getDpDeptid());
            accountPosting.setNarration(narration);
            accountPosting.setPayerOrPayee(postDTO.getPayerPayee());
            accountPosting.setEntryType(postDTO.getEntryType());
            accountPosting.setOrgId(org.getOrgid());
            accountPosting.setCreatedBy(1L);
            accountPosting.setBillVouPostingDate(postDTO.getBillVouPostingDate());
            accountPosting.setCreatedDate(postDTO.getCreatedDate());
            accountPosting.setLgIpMac(postDTO.getLgIpMac());
            accountPosting.setPayModeId(postDTO.getPayModeId());

            accountPosting.setVoucherDetails(voucherDTO);
        }
        return accountPosting;
    }

    /**
     * Calculate depreciation for given asset for a given month
     * 
     * @param dto
     * @param months
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional
    public AssetValuationDetailsDTO calculateDepreciation(CalculationDTO dto, Integer months) {
        final AssetValuationDetailsDTO valuationDTO = DepreciationCalculationMapper.mapToValuation(dto);
        FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
        calculateService.calculateDepreciation(valuationDTO, months, financiaYear);
        return valuationDTO;
    }

    /**
     * @param detailsDTO this method is for checking the lease and linear if asset group and acquisition method are not linear and
     * lease then we sets null value in linear and lease dto
     */
    @WebMethod(exclude = true)
    @Transactional
    private void checkLeaseAndLinear(final AssetDetailsDTO detailsDTO) {
        String linear = null;
        if (detailsDTO.getAssetInformationDTO().getAssetGroup() != null) {
            linear = CommonMasterUtility.getCPDDescription(
                    Long.valueOf(detailsDTO.getAssetInformationDTO().getAssetGroup()), MainetConstants.MODE_EDIT,
                    detailsDTO.getOrgId());
        }
        final String lease = CommonMasterUtility.getCPDDescription(
                Long.valueOf(detailsDTO.getAssetInformationDTO().getAcquisitionMethod()), MainetConstants.MODE_EDIT,
                detailsDTO.getOrgId());

        if (!MainetConstants.AssetManagement.LEASE.equals(lease) && detailsDTO.getAstLeaseDTO() != null) {
            detailsDTO.setAstLeaseDTO(null);
        }
        if (!MainetConstants.AssetManagement.LINEAR.equals(linear) && detailsDTO.getAstLinearDTO() != null) {
            detailsDTO.setAstLinearDTO(null);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.IMaintenanceService#checkAccountActiveOrNot() if lookupcode is L means account is active
     * if it is S that means account code it is not active you need to put your own account head
     */
    @Override
    @WebMethod(exclude = true)
    public Boolean checkAccountActiveOrNot() {
        boolean accountCodeStatus = false;
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
            String accountCode = defaultVal.getLookUpCode();
            if (accountCode.equals(MainetConstants.FlagL)) {
                accountCodeStatus = true;
            }
        }
        return accountCodeStatus;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void externalVoucherPostingInAccount(Long orgId, VoucherPostExternalDTO dto) {
        VoucherPostExternalListDTO dtos = new VoucherPostExternalListDTO();
        dtos.setVoucherextsysdto(new ArrayList<VoucherPostExternalDTO>());
        dtos.getVoucherextsysdto().add(dto);

        // T#37615 final ResponseEntity<?> response = RestClient.callRestTemplateClient(dtos,
        // ServiceEndpoints.ACCOUNT_EXT_SYS_POSTING);
        StringBuilder inputCheckSum = new StringBuilder();
        inputCheckSum.append(dto.getCreatedBy());
        inputCheckSum.append(MainetConstants.operator.ORR);
        inputCheckSum.append(dto.getUlbCode());
        String outputCheckSum = ApplicationContextProvider.getApplicationContext().getBean(ICommonEncryptionAndDecryption.class)
                .commonCheckSum(inputCheckSum.toString());

        dtos.setCheckSum(outputCheckSum);
        // JSON print in log
        final ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            result = mapper.writeValueAsString(dtos);
            LOGGER.info("external voucher json  :" + result);
        } catch (IOException e) {
            LOGGER.error("problem while converting external voucher dto to jsonString:", e);
        }
        String encryptedString = ApplicationContextProvider.getApplicationContext().getBean(ICommonEncryptionAndDecryption.class)
                .commonEncrypt(dtos);

        final ResponseEntity<?> response = RestClient.callRestTemplateClient(encryptedString,
                ServiceEndpoints.ACCOUNT_EXT_SYS_POSTING);

        if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
            LOGGER.info("Account Voucher Posting done successfully");
        } else {
            LOGGER.error("Account Voucher Posting failed due to :"
                    + (response != null ? response.getBody() : MainetConstants.BLANK));

            throw new FrameworkException("depreciation calculation successfull. Account posting failed");
        }
    }

    // this is for wms module
    @POST
    @Path("/assetregister")
    @Consumes({ "application/xml", "application/json" })
    @Override
    @Transactional
    public Long assetRegisterUtility(@RequestBody AssetDetailsDTO assetDetailsDTO) {
        Long assetId = null;
        if (assetDetailsDTO != null && assetDetailsDTO.getAssetInformationDTO() == null
                && assetDetailsDTO.getAssetClassificationDTO() == null
                && assetDetailsDTO.getAssetPurchaseInformationDTO() == null) {
            throw new FrameworkException(MainetConstants.AssetManagement.THROW_EX);
        }
        validateConstraints(assetDetailsDTO.getAssetInformationDTO(), AssetInformationDTO.class, null);
        validateConstraints(assetDetailsDTO.getAssetClassificationDTO(), AssetClassificationDTO.class, null);
        validateAssetRegister(assetDetailsDTO);
        try {
            assetDetailsDTO.getAssetPurchaseInformationDTO()
                    .setInitialBookValue(assetDetailsDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition());
            registerDetailDto(assetDetailsDTO);
            // D32939 ISRAT
            ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                            assetDetailsDTO.getOrgId());

            Organisation org = new Organisation();
            org.setOrgid(assetDetailsDTO.getOrgId());
            LookUp workflow = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmProcessId(), org);
            // check workflow applicable to service or not
            if (StringUtils.equalsIgnoreCase(workflow.getLookUpCode(), MainetConstants.WorkFlow.Process.MAKER_CHECKER)) {
                // initiate workflow code
                workflowInitiateForAPI(assetDetailsDTO.getOrgId(), assetDetailsDTO.getAssetInformationDTO());
            }
        } catch (Exception ex) {
            throw new FrameworkException(ex);
        }
        assetId = assetDetailsDTO.getAssetInformationDTO().getAssetId();
        return assetId;
    }

    // method to call initiate workflow
    public void workflowInitiateForAPI(Long orgId, AssetInformationDTO astInfoDto) {
        TbDepartment deptObj = deptService.findDeptByCode(orgId, MainetConstants.FlagA,
                MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        ServiceMaster sm = iServiceMasterService
                .getServiceMasterByShortCode(MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE, orgId);
        WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId, deptObj.getDpDeptid(),
                sm.getSmServiceId(), null, null, null, null, null);
        String assetCode = "";
        if (astInfoDto.getAstCode() == null || astInfoDto.getAstCode().isEmpty()) {
            assetCode = MainetConstants.AssetManagement.ASSETCODE;
        }
        String astIdStr = assetCode + "/" + MainetConstants.AssetManagement.NEW + "/" + astInfoDto.getAssetId();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(astInfoDto.getCreatedBy());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(astInfoDto.getCreatedBy());
        taskAction.setReferenceId(astIdStr);
        taskAction.setPaymentMode(MainetConstants.FlagF);
        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(taskAction, workFlowMas.getWfId(),
                "AssetRegistration.html",
                MainetConstants.FlagA,
                MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE);

        // It updates the flag if and only if task is created in workflow
        if (response != null) {
            // pass astIdStr for ASSET_APP_NO
            infoService.updateAppStatusFlag(astInfoDto.getAssetId(), orgId,
                    MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, astIdStr);
        }

    }

    private void validateAssetRegister(AssetDetailsDTO assetDetailsDTO) {
        if (assetDetailsDTO.getAssetPurchaseInformationDTO().getFromWhomAcquired() == null) {
            throw new FrameworkException("From Whom Acquired not found");
        }
        if (assetDetailsDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition() == null) {
            throw new FrameworkException("Cost of Acquisition not found ");
        }
        if (assetDetailsDTO.getAssetPurchaseInformationDTO().getDateOfAcquisition() == null) {
            throw new FrameworkException("Date Of Acquisition not found");
        }
        if (assetDetailsDTO.getAssetInformationDTO().getCreatedBy() == null) {
            throw new FrameworkException("CreatedBy not found in Information");
        }
        if (assetDetailsDTO.getAssetInformationDTO().getCreationDate() == null) {
            throw new FrameworkException("CreationDate not found in Information");
        }
    }

    // #D34059
    @Override
    @Transactional
    public void saveAssetEntryDataInDraftMode(Long astId, String urlShortCode, AssetDetailsDTO detailsDTO) {

        switch (urlShortCode) {
        case MainetConstants.AssetManagement.AST_INFO_URL_CODE:
            astId = infoService.saveInfo(detailsDTO.getAssetInformationDTO());
            detailsDTO.getAssetInformationDTO().setAssetId(astId);
            // update the urlParam status used at when click draft mode button than populate last modified JSP page
            // used like pending page if all done than at the final submit make it null
            // because based on urlParam logic used in AssetRegistrationModal method (inside updateApprovalFlag) so make it null
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_INFO_Page);

            break;
        case MainetConstants.AssetManagement.AST_CLASS_URL_CODE:
            detailsDTO.getAssetClassificationDTO().setAssetId(astId);
            detailsDTO.getAssetClassificationDTO()
                    .setAssetClassificationId(classInfoService.saveClassInfo(detailsDTO.getAssetClassificationDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_CLASS_Page);
            break;

        case MainetConstants.AssetManagement.AST_DEPRE_CHART_URL_CODE:
            detailsDTO.getAstDepreChartDTO().setAssetId(astId);
            detailsDTO.getAstDepreChartDTO().setAssetDeprChartId(depreService.saveDepreciation(detailsDTO.getAstDepreChartDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_DEPRE_CHART_Page);
            break;

        case MainetConstants.AssetManagement.AST_INSU_URL_CODE:
            detailsDTO.getAstInsuDTO().setAssetId(astId);
            detailsDTO.getAstInsuDTO().setAssetInsuranceId(insurService.saveInsurance(detailsDTO.getAstInsuDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_INSU_Page);
            break;

        case MainetConstants.AssetManagement.AST_LEASE_URL_CODE:
            detailsDTO.getAstLeaseDTO().setAssetId(astId);
            detailsDTO.getAstLeaseDTO().setAssetLeasingId(leasingService.saveLeasingInfo(detailsDTO.getAstLeaseDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_LEASE_Page);
            break;
        case MainetConstants.AssetManagement.AST_LINEAR_URL_CODE:
            detailsDTO.getAstLinearDTO().setAssetId(astId);
            detailsDTO.getAstLinearDTO().setAssetLinearId(lineartService.saveLinearInfo(detailsDTO.getAstLinearDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_LINEAR_Page);
            break;

        case MainetConstants.AssetManagement.AST_PURCH_URL_CODE:
            detailsDTO.getAssetPurchaseInformationDTO().setAssetId(astId);
            detailsDTO.getAssetPurchaseInformationDTO()
                    .setAssetPurchaserId(purchaseService.savePurchaseInfo(detailsDTO.getAssetPurchaseInformationDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_PRUCH_Page);
            break;

        case MainetConstants.AssetManagement.AST_REAL_ESTATE_URL_CODE:
            detailsDTO.getAssetRealEstateInfoDTO().setAssetId(astId);
            detailsDTO.getAssetRealEstateInfoDTO()
                    .setAssetRealEstId(realEstateService.saveRealEstate(detailsDTO.getAssetRealEstateInfoDTO()));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_REAL_ESTATE_Page);
            break;

        case MainetConstants.AssetManagement.AST_SERVICE_URL_CODE:
            detailsDTO.getAstSerList().get(0).setAssetId(astId);
            detailsDTO.getAstSerList().get(0)
                    .setAssetServiceId(serviceInfoService.saveServiceInfo(detailsDTO.getAstSerList().get(0)));
            infoService.updateURLParam(astId,
                    detailsDTO.getOrgId(),
                    MainetConstants.AssetManagement.SHOW_AST_SERVICE_Page);
            break;
        }
        // update approval status like DRAFT mode
        // pass null for ASSET_APP_NO
        infoService.updateAppStatusFlag(astId, detailsDTO.getOrgId(),
                MainetConstants.AssetManagement.APPROVAL_STATUS_DRAFT, null);
    }

}