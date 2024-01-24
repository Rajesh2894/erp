/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetRealEstateRev;
import com.abm.mainet.asset.domain.AssetServiceInformation;
import com.abm.mainet.asset.domain.AssetServiceInformationRev;
import com.abm.mainet.asset.domain.ServiceInformationHistory;
import com.abm.mainet.asset.mapper.ServiceInfoMapper;
import com.abm.mainet.asset.repository.AssetRealEstateRevRepo;
import com.abm.mainet.asset.repository.AssetServiceInformationRepo;
import com.abm.mainet.asset.repository.AssetServiceInformationRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;

/**
 * @author satish.rathore
 *
 */
@Service
public class ServiceInfoServiceImpl implements IServiceInfoService {

    private static final Logger LOGGER = Logger.getLogger(ServiceInfoServiceImpl.class);
    @Autowired
    private AssetServiceInformationRepo astServiceInfoRepo;

    @Autowired
    private AssetServiceInformationRevRepo astServiceInfoRevRepo;

    @Autowired
    private AssetRealEstateRevRepo assetRealEstateRepo;

    @Autowired
    private AuditService auditService;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Override
    public void addServiceInfo(AssetServiceInformationDTO dto) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional
    public Long updateServiceInfo(final Long id, final List<AssetServiceInformationDTO> listDto) {
        Long serviceInfoId = null;
        if (listDto != null && !listDto.isEmpty()) {
            for (AssetServiceInformationDTO dto : listDto) {
                try {
                    if (dto.getAssetServiceId() != null) {
                        final ServiceInformationHistory entityHistory = new ServiceInformationHistory();
                        AssetServiceInformation entity = ServiceInfoMapper.mapToServiceEntity(dto);
                        astServiceInfoRepo.updateByAssetId(dto.getAssetServiceId(), entity);
                        entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
                        try {
                            auditService.createHistory(entity, entityHistory);
                        } catch (Exception ex) {
                            LOGGER.error("Could not make audit entry while updating service information  ", ex);
                        }
                    } else {
                        dto.setAssetId(id);
                        serviceInfoId = saveServiceInfo(dto);
                    }
                } catch (Exception exception) {
                    LOGGER.error("Exception occur while updating service information  ", exception);
                    throw new FrameworkException("Exception occurs while updating Asset Service Information Details ",
                            exception);
                }
            }
        }
        return serviceInfoId;
    }

    @Override
    @Transactional
    public Long saveServiceInfo(final AssetServiceInformationDTO dto) {
        Long serviceInfoId = null;
        final ServiceInformationHistory entityHistory = new ServiceInformationHistory();
        AssetServiceInformation serviceInfo = ServiceInfoMapper.mapToServiceEntity(dto);
        try {
            serviceInfo = astServiceInfoRepo.save(serviceInfo);
            serviceInfoId = serviceInfo.getAssetServiceId();
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            try {
                auditService.createHistory(serviceInfo, entityHistory);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry while saving service information  ", ex);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving service information  ", exception);
            throw new FrameworkException("Exception occur while saving service information  ", exception);
        }

        return serviceInfoId;
    }

    @Override
    @Transactional
    public Long saveServiceInfoRev(final List<AssetServiceInformationDTO> dtoList, Long astId, Long orgId) {
        Long squenceNo = null;
        squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE,
                "TB_AST_SERVICE_REALESTD_REV", "REV_GRP_ID", orgId,
                MainetConstants.FlagC, null);
        for (AssetServiceInformationDTO serviceDTOLists : dtoList) {
            if (serviceDTOLists.getAssetServiceId() == null && serviceDTOLists.getEditFlag() != null
                    && serviceDTOLists.getEditFlag().equals("E")) {
                // identify records as new
                serviceDTOLists.setRevGrpIdentity("N");
                serviceDTOLists.setRevGroupId(squenceNo);
                serviceDTOLists.setAssetId(astId);
            } else if (serviceDTOLists.getAssetServiceId() != null && serviceDTOLists.getEditFlag() != null
                    && serviceDTOLists.getEditFlag().equals("E")) {
                // identify records as old
                serviceDTOLists.setRevGrpIdentity("O");
                serviceDTOLists.setRevGroupId(squenceNo);
            }
        }
        // save only new or edited data, filter rest
        List<AssetServiceInformationDTO> dtoLists = dtoList.stream()
                .filter(dto -> dto.getRevGroupId() != null
                        && (dto.getRevGrpIdentity().equals("O") || dto.getRevGrpIdentity().equals("N")))
                .collect(Collectors.toList());
        List<AssetServiceInformationRev> entityList = new ArrayList<>();
        for (AssetServiceInformationDTO dto : dtoLists) {
            entityList.add(ServiceInfoMapper.mapToServiceEntityRev(dto));
        }
        try {
            if (entityList.isEmpty()) {
                return null;
            }
            astServiceInfoRevRepo.save(entityList);

        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving service information  ", exception);
            throw new FrameworkException("Exception occur while saving service information  ", exception);
        }

        return squenceNo;
    }

    @Override
    @Transactional
    public Long saveRealEstateInfoRev(AssetRealEstateInformationDTO dto, Long astId, Long orgId) {

        AssetRealEstateRev assetRealEstateRev = ServiceInfoMapper.mapToRealEstateEntityRev(dto);

        assetRealEstateRev = assetRealEstateRepo.save(assetRealEstateRev);
        return assetRealEstateRev.getAssetRealStdRevId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetServiceInformationDTO> getServiceByAssetId(final Long assetId) {
        List<AssetServiceInformationDTO> dtoList = new ArrayList<>();
        try {
            final List<AssetServiceInformation> entityList = astServiceInfoRepo.findServiceByAssetId(assetId);
            if (entityList != null && !entityList.isEmpty()) {

                for (AssetServiceInformation entityLists : entityList) {
                    AssetServiceInformationDTO dto = null;
                    dto = ServiceInfoMapper.mapToServiceDTO(entityLists);
                    dtoList.add(dto);
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching service information  ", exception);
            throw new FrameworkException("Exception occur while fetching service information ", exception);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetServiceInformationDTO> getServiceRevByAssetId(Long assetId) {
        List<AssetServiceInformationDTO> dtoList = new ArrayList<>();

        try {
            final List<AssetServiceInformationRev> entityList = astServiceInfoRevRepo.findServiceByAssetId(assetId);
            if (entityList != null && !entityList.isEmpty()) {
                for (AssetServiceInformationRev entityLists : entityList) {
                    dtoList.add(ServiceInfoMapper.mapToServiceDTORev(entityLists));
                }
            }

        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching service information  ", exception);
            throw new FrameworkException("Exception occur while fetching service information ",
                    exception);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public Long updateServiceInfo(Long id, AssetServiceInformationDTO dto) {
        Long serviceInfoId = null;
        try {
            final ServiceInformationHistory entityHistory = new ServiceInformationHistory();
            if (dto.getAssetServiceId() != null) {
                final AssetServiceInformation entity = ServiceInfoMapper.mapToServiceEntity(dto);
                astServiceInfoRepo.updateByAssetId(id, entity);
                entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
                try {
                    auditService.createHistory(entity, entityHistory);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry while updating Asset Service Information Details ", ex);
                }
            } else {
                dto.setAssetId(id);
                serviceInfoId = saveServiceInfo(dto);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occurs while updating Asset Service Information Details ", exception);
            throw new FrameworkException("Exception occurs while updating Asset Service Information Details  ", exception);
        }

        return serviceInfoId;
    }

}
