/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetInformationRev;
import com.abm.mainet.asset.domain.InformationHistory;
import com.abm.mainet.asset.mapper.BasicInformationMapper;
import com.abm.mainet.asset.repository.AssetInformationRepo;
import com.abm.mainet.asset.repository.AssetInformationRevRepo;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInventoryInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPostingInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;


/**
 * @author satish.rathore
 *
 */
@Service
public class InformationServiceImpl implements IInformationService {

    @Autowired
    private AssetInformationRepo assetRepo;

    @Autowired
    private AssetInformationRevRepo assetRevRepo;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ApplicationSession applicationSession;

    private static final Logger LOGGER = Logger.getLogger(InformationServiceImpl.class);

    @Override
    @Transactional
    public Long saveInfo(final AssetInformationDTO dto) {
        final InformationHistory entityHistory = new InformationHistory();
        AssetInformation assetInfo = BasicInformationMapper.mapToInfoEntity(dto);
        try {
            assetInfo = assetRepo.save(assetInfo);
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            try {
            	entityHistory.setAssetCode(assetInfo.getAstCode());
                auditService.createHistory(assetInfo, entityHistory);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry while saving asset Information ", ex);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Information ", exception);
            throw new FrameworkException("Exception occur while saving asset Information ", exception);
        }
        return assetInfo.getAssetId();
    }

    @Override
    @Transactional
    public void updateInformation(final Long assetId, final AssetInformationDTO dto) {
        final InformationHistory entityHistory = new InformationHistory();
        if (dto.getAstCode() != null) {
            /* Task #5318 */
            LookUp lookUpObj = applicationSession.getNonHierarchicalLookUp(dto.getOrgId(), dto.getAssetClass2());
            String[] code = dto.getAstCode().split("/");
            String astCode = dto.getAstCode().replace(code[1], lookUpObj.getLookUpCode());
            // String astCode = lookUpObj.getLookUpCode() + "/" + code[1];
            dto.setAstCode(astCode);
        }
        AssetInformation entity = BasicInformationMapper.mapToInfoEntity(dto);
        try {
            assetRepo.updateByAssetId(assetId, entity);
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
            try {
                auditService.createHistory(entity, entityHistory);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry while updating asset Information ", ex);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while updating asset Information ", exception);
            throw new FrameworkException("Exception occur while updating asset Information ", exception);
        }
    }

    @Override
    @Transactional
    public Long saveInformationRev(Long assetId, AssetInformationDTO dto) {
        // final InformationHistory entityHistory = new InformationHistory();
        AssetInformationRev assetInfo = BasicInformationMapper.mapToInfoEntityRev(dto);
        try {
            assetInfo = assetRevRepo.save(assetInfo);
            // entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            // auditService.createHistory(assetInfo, entityHistory);
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Information ", exception);
            throw new FrameworkException("Exception occur while saving asset Information ", exception);
        }
        return assetInfo.getAssetRevId();
    }

    /*
     * @Override
     * @Transactional public Long saveClassificationRev(Long assetId, AssetClassificationDTO dto, AuditDetailsDTO auditDTO) {
     * return saveRev(dto); }
     */

    @Override
    public void updateInventory(final Long assetId, final Long orgId, final AssetInventoryInformationDTO dto) {

    }

    @Override
    public void updateSpecification(final Long assetId, final Long orgId, final AssetSpecificationDTO dto) {
    }

    @Override
    public void updatePosting(Long assetId, Long orgId, AssetPostingInformationDTO dto) {
    }

    @Override
    @Transactional(readOnly = true)
    public AssetInformationDTO getInfoById(final Long id) {
        AssetInformationDTO dto = null;
        try {
            final AssetInformation entity = assetRepo.findOne(id);
            if (entity != null) {
                dto = BasicInformationMapper.mapToInfoDTO(entity);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching asset Information ", exception);
            throw new FrameworkException("Exception occur while fetching asset Information ", exception);
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetInformationDTO getInfoByCode(final Long orgId, final String assetCode) {
        AssetInformationDTO dto = null;
        try {
            final AssetInformation entity = assetRepo.getAssetByCode(orgId, assetCode);
            if (entity != null) {
                dto = BasicInformationMapper.mapToInfoDTO(entity);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching asset Information ", exception);
            throw new FrameworkException("Exception occur while fetching asset Information ", exception);
        }
        return dto;
    }

    @Override
    @Transactional
    public AssetInformationDTO getInfoRevById(Long id) {
        AssetInformationDTO dto = null;
        try {
            final AssetInformationRev entity = assetRevRepo.getAssetRevId(id);
            if (entity != null) {
                dto = BasicInformationMapper.mapToInfoDTORev(entity);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching asset Information ", exception);
            throw new FrameworkException("Exception occur while fetching asset Information ", exception);
        }
        return dto;
    }

    @Override
    @Transactional
    public boolean isDuplicateName(final Long orgId, final String assetName) {
        boolean nameStatus = false;
        final List<AssetInformation> entities = assetRepo.checkDuplicateName(orgId, assetName.toLowerCase());
        if (entities != null && !entities.isEmpty()) {
            nameStatus = true;
        }
        return nameStatus;

    }

    @Override
    @Transactional
    public boolean isDuplicateSerialNo(final Long orgId, final String serialNo, final Long assetId) {
        boolean serialStatus = false;
        final List<AssetInformation> entities = assetRepo.checkDuplicateSerialNo(orgId, serialNo.toLowerCase(), assetId);
        if (entities != null && !entities.isEmpty()) {
            serialStatus = true;
        }
        return serialStatus;

    }

    @Override
    @Transactional
    public AssetInformationDTO getAssetId(Long orgId, Long assetId) {
        AssetInformationDTO dto = BasicInformationMapper.mapToInfoDTO(assetRepo.getAssetId(orgId, assetId));
        return dto;
    }

    @Override
    @Transactional
    public boolean updateAppStatusFlag(Long assetId, Long orgId, String appovalStatus, String astAppNo) {
        return assetRepo.updateAppStatusFlag(assetId, orgId, appovalStatus, astAppNo);
    }
    
    

    @Override
    @Transactional
    public boolean updateAstCode(Long assetId, Long orgId, String astCode) {
        return assetRepo.updateAstCode(assetId, orgId, astCode);
    }
    
    
    @Transactional
    public void updateAssetCode(Long assetId, Long orgId, String astCode) {
    	  assetRepo.updateAssetCode(astCode, assetId, orgId);
    }
    
    

    @Override
    @Transactional
    public boolean updateURLParam(Long assetId, Long orgId, String urlParam) {
        return assetRepo.updateURLParam(assetId, orgId, urlParam);
    }

    @Override
    public Set<String> getReferenceAsset(Long orgId) {
        Set<String> set = null;
        if (orgId != null) {
            set = assetRepo.getReferenceAsset(orgId);
        }

        return set;

    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetInformationDTO> getAssetByAssetClass(final Long orgId, final Long assetClass, final Long assetStatus,
            String appovalStatus) {
        try {
            // D#37144
            final List<AssetInformation> entityList = assetRepo.getAssetByAssetClass(orgId, assetClass, assetStatus,
                    appovalStatus);
            final List<AssetInformationDTO> dtoList = new ArrayList<>();
            if (entityList != null && !entityList.isEmpty()) {
                for (AssetInformation entiity : entityList) {
                    AssetInformationDTO dto = BasicInformationMapper.mapToInfoDTO(entiity);
                    dtoList.add(dto);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException("Unable to get asset by asset class", exp);
        }
    }

    @Override
    @Transactional
    public void updateEmployee(Long id, Long empId) {
        assetRepo.updateEmployee(id, empId);
    }
   
   
    @Transactional
    public void updateLocationId(Long id, Long location) {
        assetRepo.updateLocationId(id, location);
    }
    
    
    @Transactional
    public void updateDepartment(Long id, String astAvlstatus) {
        assetRepo.updateDepartment(id, astAvlstatus);
    }
    
   

    @Override
    @Transactional
    @Deprecated
    public void updateLocation(Long id, Long locationId) {
        assetRepo.updateLocation(id, locationId);
    }

    @Override
    public boolean isDuplicateRfIdNo(Long orgId, String rfiId) {
        boolean rfIdStatus = false;
        final List<AssetInformation> entities = assetRepo.isDuplicateRfIdNo(orgId, rfiId.toLowerCase());
        if (entities != null && !entities.isEmpty()) {
            rfIdStatus = true;
        }
        return rfIdStatus;
    }

    @Override
    @Transactional
    public boolean updateStatusFlag(Long assetId, Long orgId, String appovalStatus, Long statusId, String astAppNo) {
        if (assetId != null && orgId != null && statusId != null) {
            return assetRepo.updateStatusFlag(assetId, orgId, appovalStatus, statusId, astAppNo);
        }
        LOGGER.error("unable to update asset status please check asset id ");
        return false;

    }

    @Override
    public List<AssetInformationDTO> fetchAssetInfoByStatus(Long orgId, Long assetStatus) {
        List<AssetInformationDTO> assetInformationDTOs = new ArrayList<>();
        List<AssetInformation> infoList = assetRepo.findAllInformationListByOrgId(orgId);
        infoList.forEach(info -> {
            AssetInformationDTO infoDTO = new AssetInformationDTO();
            BeanUtils.copyProperties(info, infoDTO);
            assetInformationDTOs.add(infoDTO);
        });
        return assetInformationDTOs;
    }

    @Override
    public List<AssetInformationDTO> fetchAssetInfoByIds(Long orgId, Long assetStatusId, Long assetClassId) {
        List<AssetInformationDTO> assetInformationDTOs = new ArrayList<>();
        List<AssetInformation> infoList = assetRepo.fetchAssetInfoByIds(orgId, assetStatusId, assetClassId);
        infoList.forEach(info -> {
            AssetInformationDTO infoDTO = new AssetInformationDTO();
            BeanUtils.copyProperties(info, infoDTO);
            assetInformationDTOs.add(infoDTO);
        });
        return assetInformationDTOs;
    }

    @Override
    @Transactional
    public void updateUrlParamNullById(Long astId, Long orgId) {
        assetRepo.updateUrlParamNullById(astId, orgId);
    }
    
    
   
	@Override
	@Transactional
	public void updateGroupRefId(List<Long> assetIds,String groupRefId,String appovalStatus,  Long orgId) {
		assetRepo.updateGroupRefId( assetIds,groupRefId, appovalStatus, orgId);
		
	}

	@Override
    public List<AssetInformationDTO> findAllInformationListByGroupRefId(String groupRefId,Long orgId) {
        List<AssetInformationDTO> assetInformationDTOs = new ArrayList<>();
        List<AssetInformation> infoList = assetRepo.findAllInformationListByGroupRefId(groupRefId,orgId);
        infoList.forEach(info -> {
            AssetInformationDTO infoDTO = new AssetInformationDTO();
            if("P".equals(info.getAppovalStatus())) {
            BeanUtils.copyProperties(info, infoDTO);
            assetInformationDTOs.add(infoDTO);
            }
        });
        return assetInformationDTOs;
    }
	
	@Override
    public Long getAssetCount(Long orgId,Long assetClass2,Long assetStatus) {
        Long count = assetRepo.getAssetCount(orgId,assetClass2,assetStatus);
        return count;
    }
	
	@Override
    public Long getAllAssetCount(Long orgId,Long assetClass2,Long assetStatus) {
        Long count = assetRepo.getAllAssetCount(orgId,assetClass2,assetStatus);
        return count;
    }
	@Override
    public List<String> getAssetCode(Long orgId,Long assetClass2) {
         List<String> astCode = assetRepo.getAstCode(orgId,assetClass2);
        return astCode;
    }
	
	@Override
    public List<String> getSerialNo(Long orgId,String assetCode) {
         List<AssetInformation> list = assetRepo.getAstSerialNo(orgId,assetCode);
         List<String> astSerialNo =new ArrayList<>();
         for (AssetInformation assetInformation : list) {
			if(null !=assetInformation.getSerialNo() && !assetInformation.getSerialNo().isEmpty()) {
				astSerialNo.add(assetInformation.getSerialNo());
			}else {
				astSerialNo.add(assetInformation.getAssetModelIdentifier());
				break;
			}
		}
         
        return astSerialNo;
    }
	
	@Transactional
    public void upDateEmpDept(Long orgId,String serialNo,String astCode,Long employeeId,String deptCode,Date updatedDate,Long location) {
		AssetInformationDTO dto = getInfoByCodeAndSerialNo(orgId,astCode,serialNo);
		
		 if(dto!=null) {
		         assetRepo.updateEmpDep(employeeId,updatedDate,deptCode,location,astCode,serialNo,orgId);
		         assetRepo.updateEmpDephi(employeeId, location, astCode, serialNo, orgId);
		       
		 }
		 else {
			 assetRepo.updateAsssetData(employeeId,updatedDate,deptCode,location,astCode,serialNo,orgId);
			 assetRepo.updateAsssetDatahis(employeeId, location, astCode, serialNo, orgId);
			
		 }
        
    }
	
	 @Override
	    @Transactional(readOnly = true)
	    public AssetInformationDTO getInfoByCodeAndSerialNo(final Long orgId, final String assetCode,final String serialNo) {
	        AssetInformationDTO dto = null;
	        try {
	            final AssetInformation entity = assetRepo.getAssetByCodeAndSerialNo(orgId, assetCode,serialNo);
	         
	            if (entity != null) {
	                dto = BasicInformationMapper.mapToInfoDTO(entity);
	            }
	        } catch (Exception exception) {
	            LOGGER.error("Exception occur while fetching asset Information ", exception);
	            throw new FrameworkException("Exception occur while fetching asset Information ", exception);
	        }
	        return dto;
	    }

	@Override
	public void updateAsssetDatahis(Long orgId,Long location, String serialNo, String astCode, Long employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEmpDephis(Long orgId,Long location, String serialNo, String astCode, Long employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAssetHistory(Long assetId, Long orgId, Long employeeId) {
		// TODO Auto-generated method stub
		assetRepo.updateAssseHistory(employeeId, assetId, orgId);
		
	}
	
	@Transactional
	public void updateAssetIdbySerialNo(Long assetRequisitionId, Long orgId, String assetId) {
		// TODO Auto-generated method stub
		assetRepo.updateAssetIdbySerialNo(assetRequisitionId, orgId, assetId);
		
	}
	
	@Override
	@Transactional
	public AssetInformation getAssetCodeIdentifier(Long orgId, String astCode, String serialNo) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetCodeIdentifier(orgId, astCode, serialNo);
	}
	
	
	@Override
	@Transactional
	 public String findGroupRefIdByAssetAppNo(String astAppNo) {
	        return assetRepo.findGroupRefIdByAssetAppNo(astAppNo);
	    }

	
	
	
  
	 
	 
	
	 
	 
}
