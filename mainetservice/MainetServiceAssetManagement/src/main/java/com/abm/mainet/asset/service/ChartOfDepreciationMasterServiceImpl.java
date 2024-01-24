/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntity;
import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntityHistory;
import com.abm.mainet.asset.mapper.DepreciationMasterMapper;
import com.abm.mainet.asset.repository.ChartOfDepreciationMasterRepository;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;

/**
 * @author sarojkumar.yadav
 * 
 * Service Implementation Class for Chart of Depreciation Master
 */
@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.asset.service.IChartOfDepreciationMasterService")
@Path("/asset/depremaster")
@Api(value = "/asset/depremaster")
@Service
public class ChartOfDepreciationMasterServiceImpl implements IChartOfDepreciationMasterService {

    @Autowired
    private AuditService auditService;

    @Resource
    private ChartOfDepreciationMasterRepository cdmRepository;

    @Resource
    private SecondaryheadMasterService shmService;

    /**
     * Add record in Chart Of Depreciation Master Entity
     * 
     * @param ChartOfDepreciationMasterDTO
     * @param organisation
     * @return boolean true if record inserted successfully else return false
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public boolean addEntry(final ChartOfDepreciationMasterDTO cdmDTO) {
        final ChartOfDepreciationMasterEntity cdmEntity = DepreciationMasterMapper.mapToEntity(cdmDTO);
        // BeanUtils.copyProperties(cdmDTO, cdmEntity);
        cdmEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        cdmEntity.setCreatedDate(new Date());
        cdmEntity.setLgIpMac(Utility.getMacAddress());
        cdmRepository.save(cdmEntity);
        final ChartOfDepreciationMasterEntityHistory entityHistory = new ChartOfDepreciationMasterEntityHistory();
        entityHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(cdmEntity, entityHistory);
        return true;
    }

    /**
     * find Chart Of Depreciation Master details by orgId
     * 
     * @param orgId
     * @param organisation
     * @return list of ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<ChartOfDepreciationMasterDTO> findByOrgId(final Long orgId) {
        final List<ChartOfDepreciationMasterEntity> cdmEntityList = cdmRepository
                .findAllDepreciationMasterListByOrgId(orgId);
        final List<ChartOfDepreciationMasterDTO> dtoList = new ArrayList<>();
        if (cdmEntityList != null && !cdmEntityList.isEmpty()) {
            cdmEntityList.forEach(entity -> {
                ChartOfDepreciationMasterDTO cdmDTO = DepreciationMasterMapper.mapToDTO(entity);
                // BeanUtils.copyProperties(entity, cdmDTO);
                dtoList.add(cdmDTO);
            });
        }
        return dtoList;
    }

    /**
     * search chart Of Depreciation Master Records by name or/and by accountCode or/and by assetClass or/and by frequency with org
     * id.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List of ChartOfDepreciationMasterDTO for search criteria
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<ChartOfDepreciationMasterDTO> findBySearchData(final String accountCode, final Long assetClass,
            final Long frequency, final String name, final Long orgId, String moduleDeptCode) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<ChartOfDepreciationMasterEntity> entities = cdmRepository.findByAllSearchData(accountCode,
                assetClass, frequency, name, orgId, moduleDeptCode);
        final List<ChartOfDepreciationMasterDTO> beans = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            entities.forEach(entity -> {
                ChartOfDepreciationMasterDTO cdmDTO = DepreciationMasterMapper.mapToDTO(entity);
                LookUp astcls = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getAssetClass(), org);
                LookUp freq = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getFrequency(), org);
                cdmDTO.setAssetClassDesc(astcls.getDescLangFirst());
                cdmDTO.setFrequencyDesc(freq.getDescLangFirst());
                cdmDTO.setAccountCodeDesc(entity.getAccountCode());
                // BeanUtils.copyProperties(entity, cdmDTO);
                beans.add(cdmDTO);
            });
        }
        return beans;
    }

    /**
     * find CDM Master details by groupId(primary key)
     * 
     * @param groupId
     * @param organisation
     * @return ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public ChartOfDepreciationMasterDTO findByGroupId(final Long groupId) {
        ChartOfDepreciationMasterDTO mastDTO = null;
        final ChartOfDepreciationMasterEntity entity = cdmRepository.findOne(groupId);
        if (entity != null) {
            mastDTO = DepreciationMasterMapper.mapToDTO(entity);
            // BeanUtils.copyProperties(entity, mastDTO);
        }
        return mastDTO;
    }

    /**
     * update chart Of Depreciation Master fields by name or/and by accountCode or/and by assetClass or/and by frequency with
     * primary Key groupId.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return true if record updated successfully else return false
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public boolean updateByGroupId(final ChartOfDepreciationMasterDTO cdmDTO) {
        boolean status = false;
        ChartOfDepreciationMasterEntity cdmEntity = cdmRepository.findOne(cdmDTO.getGroupId());
        final ChartOfDepreciationMasterEntityHistory entityHistory = new ChartOfDepreciationMasterEntityHistory();
        cdmDTO.setCreatedDate(cdmEntity.getCreatedDate());
        cdmDTO.setCreatedBy(cdmEntity.getCreatedBy());
        cdmDTO.setLgIpMac(cdmEntity.getLgIpMac());
        // BeanUtils.copyProperties(cdmDTO, cdmEntity);
        cdmEntity = DepreciationMasterMapper.mapToEntity(cdmDTO);
        cdmEntity.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        cdmEntity.setUpdatedDate(new Date());
        cdmEntity.setLgIpMacUpd(Utility.getMacAddress());
        status = cdmRepository.updateByGroupId(cdmEntity);
        entityHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(cdmEntity, entityHistory);
        return status;
    }

    /**
     * check for duplicate name in Chart of Depreciation Master table
     * 
     * @param orgId
     * @param name
     * @return true if duplicate entry found else return false
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public boolean checkDuplicateName(final Long orgId, final String name) {
        boolean nameStatus = false;
        final List<ChartOfDepreciationMasterEntity> cdmEntities = cdmRepository.checkDuplicateName(orgId,
                name.toLowerCase());
        if (cdmEntities != null && !cdmEntities.isEmpty()) {
            nameStatus = true;
        }
        return nameStatus;
    }

    /**
     * find Chart Of Depreciation Master details by orgId and Asset Class
     * 
     * @param orgId
     * @param assetClass
     * @return list of ChartOfDepreciationMasterEntity with All details records if found else return null.
     */
    @Override
    @GET
    @WebMethod
    @Path("/assetClass/{orgId}/{assetClass}")
    @Consumes({ "application/xml", "application/json" })
    @Transactional(readOnly = true)
    public List<LookUp> findAllByOrgIdAstCls(final @PathParam("orgId") Long orgId,
            final @PathParam("assetClass") Long assetClass) {
        final List<Object[]> list = cdmRepository.findAllByOrgIdAstCls(orgId, assetClass);
        if ((list != null) && !list.isEmpty()) {
            return prepareAstClsLookUps(list);
        }
        return null;
    }

    /**
     * Get child level data from a prefix
     * 
     * @param orgId
     * @param prefix
     * @return list of LookUp with All details records if found else return null.
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<LookUp> getChildByPrefix(Long orgId, String cpmPrefix) {
        final List<Object[]> list = cdmRepository.getChildByPrefix(orgId, cpmPrefix);
        if ((list != null) && !list.isEmpty()) {
            return prepareChildLookUps(list);
        }
        return null;
    }

    /**
     * Prepare List of Child LookUps from given Object
     * 
     * @param List<Object>
     * @return list of lookups
     */
    @WebMethod(exclude = true)
    private List<LookUp> prepareChildLookUps(final List<Object[]> list) {
        final List<LookUp> lookUps = new ArrayList<>();
        for (final Object[] array : list) {
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId((Long) array[0]);
            lookUp.setDescLangFirst((String) array[1]);
            lookUp.setDescLangSecond((String) array[2]);
            lookUps.add(lookUp);
        }
        return lookUps;
    }

    /**
     * Prepare List of Child LookUps from given Object
     * 
     * @param List<Object>
     * @return list of lookups
     */
    @WebMethod(exclude = true)
    private List<LookUp> prepareAstClsLookUps(final List<Object[]> list) {
        final List<LookUp> lookUps = new ArrayList<>();
        for (final Object[] array : list) {
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId((Long) array[0]);
            lookUp.setDescLangFirst((String) array[1]);
            lookUps.add(lookUp);
        }
        return lookUps;
    }

    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public List<ChartOfDepreciationMasterDTO> getAssetClassByfrequency(final Long orgId, final Long frequency,
            final Long method) {
        final List<ChartOfDepreciationMasterEntity> cdmEntityList = cdmRepository.getAssetClassByfrequency(orgId,
                frequency, method);
        final List<ChartOfDepreciationMasterDTO> dtoList = new ArrayList<>();
        if (cdmEntityList != null && !cdmEntityList.isEmpty()) {
            cdmEntityList.forEach(entity -> {
                ChartOfDepreciationMasterDTO cdmDTO = DepreciationMasterMapper.mapToDTO(entity);
                dtoList.add(cdmDTO);
            });
        }
        return dtoList;
    }

    /**
     * check for duplicate asset class in Chart of Depreciation Master table
     * 
     * @param orgId
     * @param Asset Class
     * @return true if duplicate entry found else return false
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public boolean checkDuplicateAssetClass(final Long orgId, final Long assetClass) {
        boolean classStatus = false;
        final List<ChartOfDepreciationMasterEntity> cdmEntities = cdmRepository.getAllByAssetClass(orgId, assetClass);
        if (cdmEntities != null && !cdmEntities.isEmpty()) {
            classStatus = true;
        }
        return classStatus;
    }

    /**
     * find CDM Master details by asset class in Chart of Depreciation Master table
     * 
     * @param asset class
     * @param organisation
     * @return ChartOfDepreciationMasterDTO with All details records if found else return null.
     */
    @WebMethod(exclude = true)
    @Override
    @Transactional(readOnly = true)
    public ChartOfDepreciationMasterDTO getAllByAssetClass(final Long orgId, final Long assetClass) {
        ChartOfDepreciationMasterDTO mastDTO = null;
        try {
            final List<ChartOfDepreciationMasterEntity> entityList = cdmRepository.getAllByAssetClass(orgId, assetClass);
            if (entityList != null && !entityList.isEmpty()) {
                for (ChartOfDepreciationMasterEntity entity : entityList) {
                    mastDTO = DepreciationMasterMapper.mapToDTO(entity);
                }
                return mastDTO;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException("Exception occurs while Asset Registration ", exp);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAssetClassByGroupIdAndAssetClass(Long groupId, Long AssetClass2) {
        Long count = null;
        if (groupId != null && AssetClass2 != null) {
            count = cdmRepository.getAssetClassByGroupIdAndAssetClass(groupId, AssetClass2);
        }
        return count;
    }
}