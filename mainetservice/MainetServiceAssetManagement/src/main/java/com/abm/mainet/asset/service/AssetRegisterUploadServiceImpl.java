/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetRegisterUploadError;
import com.abm.mainet.asset.mapper.AssetExcelUploadMapper;
import com.abm.mainet.asset.repository.AssetRegisterUploadRepo;
import com.abm.mainet.asset.ui.dto.AssetRegisterUploadDto;
import com.abm.mainet.asset.ui.dto.AssetUploadErrorDetailDto;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.rathore
 *
 */
@Service
public class AssetRegisterUploadServiceImpl implements IAssetRegisterUploadService {

    @Autowired
    private AssetRegisterUploadRepo assetRegisterUploadRepo;
    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
    @Autowired
    private TbFinancialyearService financialyearService;

    private static final Logger LOGGER = Logger.getLogger(IAssetRegisterUploadService.class);

    @Override
    @Transactional
    public void saveAndDeleteErrorDetails(List<AssetUploadErrorDetailDto> errorDetails, Long orgId, String AssetType) {

        assetRegisterUploadRepo.deleteErrorLog(orgId, AssetType);
        if (errorDetails != null && !errorDetails.isEmpty()) {
            AssetRegisterUploadError entity = new AssetRegisterUploadError();
            for (AssetUploadErrorDetailDto astErrorDetails : errorDetails) {
                BeanUtils.copyProperties(astErrorDetails, entity);
                entity.setAstType(AssetType);
                assetRegisterUploadRepo.saveErrorDetails(entity);
            }

        }
    }

    @Override
    @Transactional
    public boolean saveUploadData(Stream<AssetRegisterUploadDto> dtos, Long orgId, String ipMacAddress, Long empId,
            String ulbName, String moduleDeptCode) {
        boolean saveStatus = true;
        if (dtos != null) {
            try {
                dtos.parallel().forEach(dto -> {
                    // for (AssetRegisterUploadDto dto : dtos) {
                    AssetDetailsDTO astDetDto = new AssetDetailsDTO();
                    astDetDto.setOrgId(orgId);
                    astDetDto.setAuditDTO(new AuditDetailsDTO());
                    astDetDto.getAuditDTO().setEmpId(empId);
                    astDetDto.getAuditDTO().setEmpIpMac(ipMacAddress);
                    dto.setAssetAppStatus("A");
                    astDetDto.setAssetPurchaseInformationDTO(AssetExcelUploadMapper.mapUploadToPurchese(dto));
                    astDetDto.setAstDepreChartDTO(AssetExcelUploadMapper.mapUploadToDepre(dto));
                    astDetDto.setAssetClassificationDTO(AssetExcelUploadMapper.mapUploadToClass(dto));
                    dto.setGeneratedAstCode(
                            assetCodeForExcel(orgId, dto.getAstClassCode(), ulbName, astDetDto.getAssetPurchaseInformationDTO(),
                                    moduleDeptCode));
                    /* Defect #5432 Asset code not generated automatically when data uploaded through excel */
                    astDetDto.setAssetInformationDTO(AssetExcelUploadMapper.mapUploadToInfo(dto));
                    astDetDto.getAssetInformationDTO().setDeptCode(moduleDeptCode);
                    maintenanceService.registerDetailDto(astDetDto);
                    Date date = new Date();
                    AssetValuationDetailsDTO valuationDTO = new AssetValuationDetailsDTO();
                    maintenanceService.addValuationEntry(astDetDto, date, valuationDTO,
                            astDetDto.getAssetInformationDTO().getAssetId());
                    // }
                });
            } catch (FrameworkException ex) {
                LOGGER.error("exception occur while uploading excel file ", ex);
                saveStatus = false;

            }
        }
        return saveStatus;

    }
    @Override
    @Transactional
    public boolean saveUploadITAsssetData(Stream<ITAssetRegisterUploadDto> dtos, Long orgId, String ipMacAddress, Long empId,
            String ulbName, String moduleDeptCode) {
        boolean saveStatus = true;
        if (dtos != null) {
            try {
                dtos.parallel().forEach(dto -> {
                    // for (AssetRegisterUploadDto dto : dtos) {
                    AssetDetailsDTO astDetDto = new AssetDetailsDTO();
                    astDetDto.setOrgId(orgId);
                    astDetDto.setAuditDTO(new AuditDetailsDTO());
                    astDetDto.getAuditDTO().setEmpId(empId);
                    astDetDto.getAuditDTO().setEmpIpMac(ipMacAddress);
                    dto.setAssetAppStatus("A");
                    astDetDto.setAssetPurchaseInformationDTO(AssetExcelUploadMapper.mapITAssetUploadToPurchese(dto));
                    //astDetDto.setAstDepreChartDTO(AssetExcelUploadMapper.mapUploadToDepre(dto));
                    //astDetDto.setAssetClassificationDTO(AssetExcelUploadMapper.mapUploadToClass(dto));
                    //astDetDto.setAstSerInfoDTO(AssetExcelUploadMapper.mapITAssetUploadToServiceInfo(dto));
                    if(astDetDto.getAstSerList() != null && !astDetDto.getAstSerList().isEmpty()) {
                    	 astDetDto.getAstSerList().clear();
                    }                   
                    List<AssetServiceInformationDTO> l = new ArrayList<AssetServiceInformationDTO>();
                    l.add(AssetExcelUploadMapper.mapITAssetUploadToServiceInfo(dto));
                    astDetDto.setAstSerList(l);
                    dto.setGeneratedAstCode(
                            assetCodeForExcel(orgId, dto.getAstClassCode(), ulbName, astDetDto.getAssetPurchaseInformationDTO(),
                                    moduleDeptCode));
                    /* Defect #5432 Asset code not generated automatically when data uploaded through excel */
                    astDetDto.setAssetInformationDTO(AssetExcelUploadMapper.mapITAssetUploadToInfo(dto));
                    astDetDto.getAssetInformationDTO().setDeptCode(moduleDeptCode);
                    astDetDto.getAssetInformationDTO().setUrlParam(moduleDeptCode);
                    astDetDto.getAssetInformationDTO().setAssetName("DF");//IN Database Asset_Name cannot be null
                    astDetDto.getAssetInformationDTO().setDetails("DF");
                    astDetDto.getAssetInformationDTO().setPurpose("DF");
                    maintenanceService.registerDetailDto(astDetDto);
                    Date date = new Date();
                    AssetValuationDetailsDTO valuationDTO = new AssetValuationDetailsDTO();
                    maintenanceService.addValuationEntry(astDetDto, date, valuationDTO,
                            astDetDto.getAssetInformationDTO().getAssetId());
                    // }
                });
            } catch (FrameworkException ex) {
                LOGGER.error("exception occur while uploading excel file ", ex);
                saveStatus = false;

            }
        }
        return saveStatus;

    }

    /*
     * modify under Task #5318 (non-Javadoc)
     * @see com.abm.mainet.asset.service.IAssetRegisterUploadService#assetCodeForExcel(java.lang.Long, java.lang.String) this
     * generates same type of codes as work flow generates
     */
    @Override
    @Transactional
    public String assetCodeForExcel(Long orgId, String astClasscode, String ulbName,
            AssetPurchaseInformationDTO assetPurchaseInformationDTO, String moduleDeptCode) {
        // #T92467
        Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDepartmentIdByDeptCode(moduleDeptCode,
                        MainetConstants.STATUS.ACTIVE);

        final Long squenceNo = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .getNumericSeqNo(deptId.toString(),
                        "TB_AST_INFO_MST", "ASSET_CODE", orgId, "CNT"/* continues */, deptId.toString(), 1L, 999999L);
        /*
         * Long squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE, "TB_AST_INFO_MST",
         * "ASSET_CODE", orgId, MainetConstants.FlagC, null);
         */

        FinancialYear financialYear = financialyearService
                .getFinanciaYearByDate(assetPurchaseInformationDTO.getDateOfAcquisition());
        String faYear = Utility.getFinancialYear(financialYear.getFaFromDate(), financialYear.getFaToDate());
        String assetCode = ulbName + "/" + astClasscode + "/" + faYear + "/" + squenceNo;
        return assetCode;
    }
}
