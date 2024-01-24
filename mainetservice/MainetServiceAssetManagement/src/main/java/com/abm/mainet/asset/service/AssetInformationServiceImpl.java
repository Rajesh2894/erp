package com.abm.mainet.asset.service;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.repository.AssetPurchaseInformationRepo;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInventoryInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPostingInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;


import io.swagger.annotations.Api;

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.asset.service.IAssetInformationService")
@Api(value = "/assetinfo")
@Path("/assetinfo")
@Service
public class AssetInformationServiceImpl implements IAssetInformationService {

    @Autowired
    private IInformationService infoService;
    @Autowired
    private IClassificationInfoService classInfoService;
    @Autowired
    private IDepreciationService depreService;
    @Autowired
    private IInsuranceService insurService;
    @Autowired
    private ILeasingCompanyService leasingService;
    @Autowired
    private IPurchaseInfoService purchaseService;
    @Autowired
    private IRealEstateInfoService realEstateService;
    @Autowired
    private IServiceInfoService serviceInfoService;
    @Autowired
    IFileUploadService assetFileUpload;
    @Autowired
    private ILinearAssetService linearService;
    @Resource
    IDocumentUploadService documentUploadService;
    @Resource
    private TbDepartmentService iTbDepartmentService;
    @Autowired
    private IAssetFunctionalLocationMasterService assetFuncLocMasterService;
    @Autowired
    private IOrganisationService iOrganisationService;
    @Autowired
    private IAssetValuationService valuationService;

    @Resource
    private ISearchService searchService;
    
    @Autowired
    private AssetPurchaseInformationRepo assetpurrepo;

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/getAssetDetails")
    @Transactional(readOnly = true)
    @Override
    public AssetDetailsDTO getDetailsByOrgIdAndAssetCode(@RequestBody AssetDetailsDTO dto) {
        AssetDetailsDTO detailDTO = new AssetDetailsDTO();
        try {
            final AssetInformationDTO infoDTO = infoService.getInfoByCode(dto.getOrgId(),
                    dto.getAssetInformationDTO().getAstCode());
            if (infoDTO != null) {
                final AssetClassificationDTO clsDTO = getClassification(infoDTO.getAssetId());
                final AssetPurchaseInformationDTO purchDTO = getPurchaseInfo(infoDTO.getAssetId());
                final AssetLeasingCompanyDTO leaseDTO = getLeaseInfo(infoDTO.getAssetId());
                // final AssetInsuranceDetailsDTO insurDTO = getInsuranceInfo(infoDTO.getAssetId());
                final List<AssetInsuranceDetailsDTO> insurDTOList = getInsuranceInfoList(infoDTO.getAssetId());
                final List<AssetServiceInformationDTO> servDTO = getServiceInfo(infoDTO.getAssetId());
                final AssetDepreciationChartDTO depreDTO = getDepreciationInfo(infoDTO.getAssetId());
                final AssetLinearDTO lineDTO = getLinear(infoDTO.getAssetId());
                detailDTO.setAssetInformationDTO(infoDTO);
                detailDTO.setAssetClassificationDTO(clsDTO);
                detailDTO.setAssetPurchaseInformationDTO(purchDTO);
                detailDTO.setAstLeaseDTO(leaseDTO);
                // detailDTO.setAstInsuDTO(insurDTO);
                detailDTO.setAstInsuDTOList(insurDTOList);
                detailDTO.setAstSerList(servDTO);
                detailDTO.setAstDepreChartDTO(depreDTO);
                detailDTO.setAstLinearDTO(lineDTO);
            } else {
                // NO Record Found

            }

        } catch (Exception ex) {
            throw new FrameworkException("Exception while getting details of Asset ", ex);
        }
        return detailDTO;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/getDetailsByAssetId/id{id}")
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetDetailsDTO getDetailsByAssetId(@PathParam(value = "id") Long id) {
        final AssetDetailsDTO detailDTO = new AssetDetailsDTO();
        try {
            final AssetInformationDTO infoDTO = getInfo(id);
            final AssetClassificationDTO clsDTO = getClassification(id);
            final AssetPurchaseInformationDTO purchDTO = getPurchaseInfo(id);
            final AssetRealEstateInformationDTO realEstateDTO = getRealEstateInfo(id);
            final AssetLeasingCompanyDTO leaseDTO = getLeaseInfo(id);
            // final AssetInsuranceDetailsDTO insurDTO = getInsuranceInfo(id);
            final List<AssetInsuranceDetailsDTO> insurDTOList = getInsuranceInfoList(id);
            final List<AssetServiceInformationDTO> servDTO = getServiceInfo(id);
            final AssetDepreciationChartDTO depreDTO = getDepreciationInfo(id);
            final AssetLinearDTO lineDTO = getLinear(id);
            detailDTO.setAssetInformationDTO(infoDTO);
            detailDTO.setAssetClassificationDTO(clsDTO);
            detailDTO.setAssetPurchaseInformationDTO(purchDTO);
            detailDTO.setAssetRealEstateInfoDTO(realEstateDTO);
            detailDTO.setAstLeaseDTO(leaseDTO);
            // detailDTO.setAstInsuDTO(insurDTO);
            detailDTO.setAstInsuDTOList(insurDTOList);
            detailDTO.setAstSerList(servDTO);
            detailDTO.setAstDepreChartDTO(depreDTO);
            detailDTO.setAstLinearDTO(lineDTO);
        } catch (Exception ex) {
            throw new FrameworkException("Exception while getting details of Asset ", ex);
        }
        return detailDTO;
    }

    /*
     * @Override public AssetSummaryDTO getSummary(Long id) { // TODO Auto-generated method stub return null; }
     */

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetDetailsDTO getDetailsByIdAndUrlparamRev(Long idRev, String urlParam) {
        final AssetDetailsDTO detailDTO = new AssetDetailsDTO();
        try {

            if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_CLASS_Page)) {
                final AssetClassificationDTO clsDTO = getClassificationRev(idRev);
                detailDTO.setAssetClassificationDTO(clsDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INFO_Page)) {
                final AssetInformationDTO infoDTO = getInfoRev(idRev);
                detailDTO.setAssetInformationDTO(infoDTO);
                detailDTO.getAssetInformationDTO().setUrlParam(urlParam);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_PRUCH_Page)) {
                final AssetPurchaseInformationDTO purchaseDTO = getPurchaseInfoRev(idRev);
                detailDTO.setAssetPurchaseInformationDTO(purchaseDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_REAL_ESTATE_Page)) {
                final AssetRealEstateInformationDTO realEstateDTO = getRealEstateInfoRev(idRev);
                detailDTO.setAssetRealEstateInfoDTO(realEstateDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_LEASE_Page)) {
                final AssetLeasingCompanyDTO purchaseDTO = getLeaseInfoRev(idRev);
                detailDTO.setAstLeaseDTO(purchaseDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_SERVICE_Page)) {
                final List<AssetServiceInformationDTO> serviceInfoDTO = getServiceInfoRev(idRev);
                detailDTO.setAstSerList(serviceInfoDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_DEPRE_CHART_Page)) {
                final AssetDepreciationChartDTO chartDTO = getDepreciationInfoRev(idRev);
                detailDTO.setAstDepreChartDTO(chartDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_LINEAR_Page)) {
                final AssetLinearDTO linearDTO = getLinearRev(idRev);
                detailDTO.setAstLinearDTO(linearDTO);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INSU_Page)) {
                final List<AssetInsuranceDetailsDTO> insuranceDTOList = getInsuranceInfoListByGroupIdRev(idRev);
                detailDTO.setAstInsuDTOList(insuranceDTOList);
                // detailDTO.setAstInsuDTO(insuranceDTOList);
            } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INSU_Page_DATATABLE)) {
                final List<AssetInsuranceDetailsDTO> insuranceDTOList = getInsuranceInfoListByGroupIdRev(idRev);
                detailDTO.setAstInsuDTOList(insuranceDTOList);
                // detailDTO.setAstInsuDTO(insuranceDTOList);
            }

            /*
             * final AssetInformationDTO infoDTO = getInfo(id); final AssetClassificationDTO clsDTO = getClassification(id); final
             * AssetPurchaseInformationDTO purchDTO = getPurchaseInfo(id); final AssetLeasingCompanyDTO leaseDTO =
             * getLeaseInfo(id); final AssetInsuranceDetailsDTO insurDTO = getInsuranceInfo(id); final AssetServiceInformationDTO
             * servDTO = getServiceInfo(id); final AssetDepreciationChartDTO depreDTO = getDepreciationInfo(id); final
             * AssetLinearDTO lineDTO = getLinear(id); detailDTO.setAssetInformationDTO(infoDTO);
             * detailDTO.setAssetClassificationDTO(clsDTO); detailDTO.setAssetPurchaseInformationDTO(purchDTO);
             * detailDTO.setAstLeaseDTO(leaseDTO); detailDTO.setAstInsuDTO(insurDTO); detailDTO.setAstSerInfoDTO(servDTO);
             * detailDTO.setAstDepreChartDTO(depreDTO); detailDTO.setAstLinearDTO(lineDTO);
             */
        } catch (Exception ex) {
            throw new FrameworkException("Exception while getting details of Asset ", ex);
        }
        return detailDTO;
    }

    @WebMethod(exclude = true)
    @Override

    public AssetDetailsDTO getITAssetDetailsByIdRev(String idRev) {
        // This split will contain the indexes of INFO,PURC,SERV
        String[] ids = idRev.split("-");

        final AssetDetailsDTO detailDTO = new AssetDetailsDTO();
        try {
            final AssetInformationDTO infoDTO = getInfoRev(Long.parseLong(ids[0]));
            detailDTO.setAssetInformationDTO(infoDTO);

            final AssetPurchaseInformationDTO purchaseDTO = getPurchaseInfoRev(Long.parseLong(ids[1]));
            detailDTO.setAssetPurchaseInformationDTO(purchaseDTO);
            // Service information can be null or not null so we need to check for avoiding exception
            if (ids.length > 2) { // in future if there is any fourth page then need to change the condition accordingly
                final List<AssetServiceInformationDTO> serviceInfoDTO = getServiceInfoRev(Long.parseLong(ids[2]));
                detailDTO.setAstSerList(serviceInfoDTO);
            }

        } catch (Exception ex) {
            throw new FrameworkException("Exception while getting details of Asset ", ex);
        }
        return detailDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetInsuranceDetailsDTO> getInsuranceInfoList(Long id) {
        final List<AssetInsuranceDetailsDTO> dtoList = insurService.getInsuranceListByAssetId(id);
        return dtoList;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public List<AssetInsuranceDetailsDTO> getInsuranceInfoListByGroupIdRev(Long id) {
        final List<AssetInsuranceDetailsDTO> dtoList = insurService.getInsuranceListRevByGroupId(id);
        return dtoList;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetInventoryInformationDTO getInventoryInfo(Long id) {
        AssetInventoryInformationDTO dto = null;
        try {
            dto = infoService.getInfoById(id).getAssetInventoryInfoDTO();
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while fetching Asset Inventory Information details ", exp);
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetPostingInformationDTO getPostingInfo(Long id) {
        AssetPostingInformationDTO dto = null;
        try {
            dto = infoService.getInfoById(id).getAssetPostingInfoDTO();
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while fetching Asset Posting Information details ", exp);
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetSpecificationDTO getSpecification(Long id) {
        AssetSpecificationDTO dto = null;
        try {
            dto = infoService.getInfoById(id).getAssetSpecificationDTO();
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while fetching Asset Specification Information details ",
                    exp);
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetClassificationDTO getClassification(Long id) {
        AssetClassificationDTO dto = null;
        try {
            dto = classInfoService.getclassByAssetId(id);
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while fetching Asset Classification details ", exp);
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetClassificationDTO getClassificationRev(Long assetClassIdRev) {
        AssetClassificationDTO dto = null;
        try {
            dto = classInfoService.getclassRevById(assetClassIdRev);
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while fetching Asset Classification details ", exp);
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetPurchaseInformationDTO getPurchaseInfo(Long id) {
        final AssetPurchaseInformationDTO dto = purchaseService.getPurchaseByAssetId(id);
        final AssetInformationDTO infoDTO = getInfo(id);
        final AssetValuationDetailsDTO valuationDTO = findLatestAssetId(infoDTO.getOrgId(), id);
        if (valuationDTO != null) {
            dto.setLatestBookValue(valuationDTO.getCurrentBookValue());
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetRealEstateInformationDTO getRealEstateInfo(Long id) {
        final AssetRealEstateInformationDTO dto = realEstateService.getRealEstateInfoByAssetId(id);

        final AssetInformationDTO infoDTO = getInfo(id);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetRealEstateInformationDTO getRealEstateInfoRev(Long id) {
        AssetRealEstateInformationDTO realEstateDTO = realEstateService.getRealEstateInfoRevByAssetId(id);
        return realEstateDTO;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetPurchaseInformationDTO getPurchaseInfoRev(Long idRev) {
        final AssetPurchaseInformationDTO dto = purchaseService.getPurchaseRevByAssetId(idRev);
        final AssetInformationDTO infoDTO = getInfo(dto.getAssetId());
        final AssetValuationDetailsDTO valuationDTO = findLatestAssetId(infoDTO.getOrgId(), dto.getAssetId());
        if (valuationDTO != null) {
            dto.setLatestBookValue(valuationDTO.getCurrentBookValue());
        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public List<AssetServiceInformationDTO> getServiceInfo(Long id) {
        final List<AssetServiceInformationDTO> listDto = serviceInfoService.getServiceByAssetId(id);
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetServiceInformationDTO> getServiceInfoRev(Long id) {
        final List<AssetServiceInformationDTO> dtoList = serviceInfoService.getServiceRevByAssetId(id);
        return dtoList;
    }

    /*
     * @WebMethod(exclude = true)
     * @Override
     * @Transactional public AssetRealEstateInformationDTO getRealEstateInfo(Long id) { final AssetRealEstateInformationDTO dto =
     * serviceInfoService.getServiceByAssetId(id) .getAssetRealEstateInfoDTO(); AssetRealEstateInformationDTO dto = new
     * AssetRealEstateInformationDTO(); return dto; }
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetInsuranceDetailsDTO getInsuranceInfo(Long id) {
        final AssetInsuranceDetailsDTO dto = insurService.getInsuranceByAssetId(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetInsuranceDetailsDTO getInsuranceInfoRev(Long id) {
        final AssetInsuranceDetailsDTO dto = insurService.getInsuranceRevByAssetId(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetLeasingCompanyDTO getLeaseInfo(Long id) {
        final AssetLeasingCompanyDTO dto = leasingService.getLeasingByAssetId(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetLeasingCompanyDTO getLeaseInfoRev(Long id) {
        final AssetLeasingCompanyDTO dto = leasingService.getLeasingRevByAssetId(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    public void getDocuments(Long id) {
        // TODO Auto-generated method stub

    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetDepreciationChartDTO getDepreciationInfo(Long id) {
        final AssetDepreciationChartDTO dto = depreService.findDeprtChartByAssetId(id);
        final AssetInformationDTO infoDTO = getInfo(id);
        final AssetValuationDetailsDTO valuationDTO = findLatestAssetIdWithChangeType(infoDTO.getOrgId(), id,
                MainetConstants.AssetManagement.ValuationType.DPR.getValue());
        if (valuationDTO != null) {
            dto.setLatestAccumDepreAmount(valuationDTO.getAccumDeprValue());
            dto.setLatestAccumulDeprDate(valuationDTO.getCurrentBookDate());

        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetDepreciationChartDTO getDepreciationInfoRev(Long id) {
        final AssetDepreciationChartDTO dto = depreService.getChartRevByAssetId(id);
        final AssetInformationDTO infoDTO = getInfo(dto.getAssetId());
        final AssetValuationDetailsDTO valuationDTO = findLatestAssetIdWithChangeType(infoDTO.getOrgId(),
                dto.getAssetId(), MainetConstants.AssetManagement.ValuationType.DPR.getValue());
        if (valuationDTO != null) {
            dto.setLatestAccumDepreAmount(valuationDTO.getAccumDeprValue());
            dto.setLatestAccumulDeprDate(valuationDTO.getCurrentBookDate());

        }
        return dto;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetInformationDTO getInfo(Long id) {
        final AssetInformationDTO dto = infoService.getInfoById(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public AssetInformationDTO getInfoRev(Long id) {
        final AssetInformationDTO dto = infoService.getInfoRevById(id);
        return dto;
    }

    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public AssetLinearDTO getLinear(Long id) {
        final AssetLinearDTO dto = linearService.getLinearByAssetId(id);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetLinearDTO getLinearRev(Long id) {
        final AssetLinearDTO dto = linearService.getLinearRevByAssetId(id);
        return dto;
    }

    @Override
    @GET
    @Path("/id/{assetId}")
    @Transactional(readOnly = true)
    public SummaryDTO getSummaryByAssetId(@PathParam("assetId") final Long assetId) {
        AssetDetailsDTO detailsdto = getDetailsByAssetId(assetId);
        SummaryDTO sumDTO = new SummaryDTO();
        final Organisation organisation = iOrganisationService
                .getOrganisationById(detailsdto.getAssetInformationDTO().getOrgId());
        sumDTO.setAssetClass1(detailsdto.getAssetInformationDTO().getAssetClass1());
        sumDTO.setAssetClass1Desc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(detailsdto.getAssetInformationDTO().getAssetClass1()), organisation)
                .getDescLangFirst());
        sumDTO.setAssetClass2(detailsdto.getAssetInformationDTO().getAssetClass2());
        sumDTO.setAssetClass2Desc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(detailsdto.getAssetInformationDTO().getAssetClass2()), organisation)
                .getDescLangFirst());
        sumDTO.setAssetName(detailsdto.getAssetInformationDTO().getAssetName());
        sumDTO.setAstId(detailsdto.getAssetInformationDTO().getAssetId());
        sumDTO.setDetails(detailsdto.getAssetInformationDTO().getDetails());
        sumDTO.setSerialNo(detailsdto.getAssetInformationDTO().getSerialNo());
        sumDTO.setAstCode(detailsdto.getAssetInformationDTO().getAstCode());
        final AssetClassificationDTO classDTO = detailsdto.getAssetClassificationDTO();
        if (classDTO != null) {
            LocationMasEntity locEntity = ApplicationContextProvider.getApplicationContext()
                    .getBean(ILocationMasService.class)
                    .findbyLocationId(detailsdto.getAssetClassificationDTO().getLocation());
            sumDTO.setLocation(locEntity != null ? locEntity.getLocNameEng() : null);

            if (classDTO.getFunctionalLocationCode() != null) {
                sumDTO.setLocation(assetFuncLocMasterService
                        .findByFuncLocId(detailsdto.getAssetClassificationDTO().getFunctionalLocationCode(),
                                detailsdto.getAssetInformationDTO().getOrgId())
                        .getFuncLocationCode());
                sumDTO.setLocationId(detailsdto.getAssetClassificationDTO().getFunctionalLocationCode());
            }
            if (StringUtils.isNotBlank(classDTO.getCostCenter())) {
                sumDTO.setCostCenter(detailsdto.getAssetClassificationDTO().getCostCenter());
            }
            sumDTO.setDeptName(iTbDepartmentService.findById(detailsdto.getAssetClassificationDTO().getDepartment())
                    .getDpDeptdesc());
        }
        return sumDTO;
    }

    @Override
    @POST
    @Path("/getAssetInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true)
    public SummaryDTO getAssetInfo(@RequestBody AssetDetailsDTO dto) {
        // AssetDetailsDTO detailsdto = getDetailsByAssetId(assetId);
        AssetInformationDTO infoDTO = infoService.getInfoByCode(dto.getOrgId(), dto.getAssetInformationDTO().getAstCode());
        SummaryDTO sumDTO = new SummaryDTO();
        if (infoDTO != null) {
            AssetDetailsDTO detailsdto = getDetailsByAssetId(infoDTO.getAssetId());

            final Organisation organisation = iOrganisationService
                    .getOrganisationById(detailsdto.getAssetInformationDTO().getOrgId());
            sumDTO.setAssetClass1(detailsdto.getAssetInformationDTO().getAssetClass1());
            sumDTO.setAssetClass1Desc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(detailsdto.getAssetInformationDTO().getAssetClass1()),
                            organisation)
                    .getDescLangFirst());
            sumDTO.setAssetClass2(detailsdto.getAssetInformationDTO().getAssetClass2());
            sumDTO.setAssetClass2Desc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(detailsdto.getAssetInformationDTO().getAssetClass2()),
                            organisation)
                    .getDescLangFirst());
            sumDTO.setAssetName(detailsdto.getAssetInformationDTO().getAssetName());
            sumDTO.setAstId(detailsdto.getAssetInformationDTO().getAssetId());
            sumDTO.setDetails(detailsdto.getAssetInformationDTO().getDetails());
            sumDTO.setSerialNo(detailsdto.getAssetInformationDTO().getSerialNo());
            sumDTO.setAstCode(detailsdto.getAssetInformationDTO().getAstCode());
            final AssetClassificationDTO classDTO = detailsdto.getAssetClassificationDTO();
            if (classDTO != null) {
                LocationMasEntity locEntity = ApplicationContextProvider.getApplicationContext()
                        .getBean(ILocationMasService.class)
                        .findbyLocationId(detailsdto.getAssetClassificationDTO().getLocation());
                sumDTO.setLocation(locEntity != null ? locEntity.getLocNameEng() : null);

                if (classDTO.getFunctionalLocationCode() != null) {
                    sumDTO.setLocation(assetFuncLocMasterService
                            .findByFuncLocId(detailsdto.getAssetClassificationDTO().getFunctionalLocationCode(),
                                    detailsdto.getAssetInformationDTO().getOrgId())
                            .getFuncLocationCode());
                    sumDTO.setLocationId(detailsdto.getAssetClassificationDTO().getFunctionalLocationCode());
                }
                if (StringUtils.isNotBlank(classDTO.getCostCenter())) {
                    sumDTO.setCostCenter(detailsdto.getAssetClassificationDTO().getCostCenter());
                }
                // D#36724
                Department dept = iTbDepartmentService.findDepartmentById(detailsdto.getAssetClassificationDTO().getDepartment());
                if (dto.getLangId() != null) {
                    if (dto.getLangId() == 1) {
                        sumDTO.setDeptName(dept.getDpDeptdesc());
                    } else {
                        sumDTO.setDeptName(dept.getDpNameMar());
                    }
                } else {
                    sumDTO.setDeptName(dept.getDpDeptdesc());
                }
            }
        } else {
            // error masg invalid asset no
        }

        return sumDTO;
    }

    @GET
    @Path("/barcode/{barcode}")
    @WebMethod
    @Override
    public SummaryDTO getSummaryByBarcode(@PathParam("barcode") final String barcode) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @WebMethod(exclude = true)
     * @Override
     * @Transactional(readOnly=true) public List<LookUp> getCostCenterbyOrgId(final Long orgId, final Integer langId) { return
     * classInfoService.getCostCenterbyOrgId(orgId, langId); }
     */

    /**
     * Used to get list of asset by matching asset class in an org
     * 
     * @param assetClass
     * @param assetStatus
     * @param orgId
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public List<AssetInformationDTO> getAssetByAssetClass(final Long orgId, final Long assetClass,
            final Long assetStatus, String appovalStatus) {
        return infoService.getAssetByAssetClass(orgId, assetClass, assetStatus, appovalStatus);
    }

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param orgId
     * @param assetId
     * @return DepreciationAssetDTO
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetValuationDetailsDTO findLatestAssetId(Long orgId, Long assetId) {
        return valuationService.findLatestAssetId(orgId, assetId);
    }

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId for change type "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public AssetValuationDetailsDTO findLatestAssetIdWithChangeType(final Long orgId, final Long assetId,
            final String changeType) {
        return valuationService.findLatestAssetIdWithChangeType(orgId, assetId, changeType);
    }

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId for change type other than passed change Type
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssetValuationDetailsDTO> checkTransaction(final Long orgId, final Long assetId,
            final String changeType) {
        return valuationService.checkTransaction(orgId, assetId, changeType);
    }
    
    
    
    
	@Override
	@Transactional(readOnly = true)
	public AssetPurchaseInformationDTO assetpurhcaseView(Long assetId) {
		AssetPurchaseInformation entity = assetpurrepo.findPurchaseByAssetId(assetId);
		AssetPurchaseInformationDTO dto=new AssetPurchaseInformationDTO();
		 BeanUtils.copyProperties(entity, dto);
		
		 return dto;
	}
	

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getDetailsByEmpId/{id}/{orgId}/{langId}")
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public List<SummaryDTO> getDetailsByEmpId(@PathParam("id") Long id, @PathParam("orgId") Long orgId,
            @PathParam("langId") Long langId) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setEmployeeId(id);
        searchDTO.setOrgId(orgId);
        searchDTO.setLangId(langId.intValue());
        List<SummaryDTO> summaryDTOList = new ArrayList<SummaryDTO>();
        summaryDTOList = searchService.search(searchDTO);
        return summaryDTOList;
    }

}
