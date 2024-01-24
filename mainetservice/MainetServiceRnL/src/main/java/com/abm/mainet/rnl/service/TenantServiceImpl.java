package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.rnl.domain.TenantEntity;
import com.abm.mainet.rnl.domain.TenantOwnerEntity;
import com.abm.mainet.rnl.dto.TenantMaster;
import com.abm.mainet.rnl.dto.TenantMasterGrid;
import com.abm.mainet.rnl.dto.TenantOwnerMaster;
import com.abm.mainet.rnl.repository.TenantRepository;

/**
 * @author ritesh.patil
 *
 */
@Service("jpaTenantService")
@Repository
@Transactional(readOnly = true)
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IAttachDocsDao iAttachDocsDao;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.ITenantService#findAllTenantRecords(java.lang.Long)
     */

    @Override
    public List<TenantMasterGrid> findAllOrgTenantRecords(
            final Long orgId) {
        final List<Object[]> objList = tenantRepository.findAllTenantRecords(orgId, MainetConstants.RnLCommon.Y);
        List<TenantMasterGrid> tenantMasterGrids = null;
        TenantMasterGrid tenantMasterGrid = null;
        if ((objList != null) && !objList.isEmpty()) {
            tenantMasterGrids = new ArrayList<>();
            for (final Object[] obj : objList) {
                tenantMasterGrid = new TenantMasterGrid();
                tenantMasterGrid.setId(Long.valueOf(obj[0].toString()));
                tenantMasterGrid.setCode(obj[1].toString());
                tenantMasterGrid.setType(Integer.valueOf(obj[2].toString()));
                tenantMasterGrid.setfName(obj[3].toString());
                tenantMasterGrid.setlName(obj[4].toString());
                tenantMasterGrid.setMobileNo(obj[5].toString());
                tenantMasterGrids.add(tenantMasterGrid);
            }
        }
        return tenantMasterGrids;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#findById(java.lang.Long)
     */

    @Override
    public TenantMaster findById(final Long tntId) {
        final TenantEntity tenantEntity = tenantRepository.findOne(tntId);
        final TenantMaster tenantMaster = new TenantMaster();
        if (tenantEntity != null) {
            tenantEntity.getTenantOwnerList().size();
            TenantOwnerMaster tenantOwnerMaster;
            BeanUtils.copyProperties(tenantEntity, tenantMaster);
            for (final TenantOwnerEntity tenantOwnerEntity : tenantEntity.getTenantOwnerList()) {
                tenantOwnerMaster = new TenantOwnerMaster();
                BeanUtils.copyProperties(tenantOwnerEntity, tenantOwnerMaster);
                tenantMaster.getTenantOwnerMasters().add(tenantOwnerMaster);
            }
        }
        return tenantMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#save()
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveTenantMaster(final TenantMaster tenantMaster, final List<AttachDocs> list) {
        final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RnLCommon.RentLease,
                MainetConstants.EstateMaster.TB_RL_ESTATE_MAS, MainetConstants.EstateMaster.ES_CODE, tenantMaster.getOrgId(),
                MainetConstants.RnLCommon.Flag_C, null);
        TenantEntity entity = new TenantEntity();
        BeanUtils.copyProperties(tenantMaster, entity, MainetConstants.TenantMaster.ExludeCopyArrayTenant);
        entity.setCode(MainetConstants.TenantMaster.TenantShortType + getNumberBasedOnOrg(entity.getOrgId()) + entity.getOrgId()
                + getNumberBasedOnFunctionValue(javaFq) + javaFq);
        entity.setIsActive(MainetConstants.RnLCommon.Y);
        entity.setCreatedDate(new Date());
        entity.setLgIpMac(tenantMaster.getLgIpMac());
        entity.setTntFlag(MainetConstants.TenantMaster.FLAG_S);
        final List<TenantOwnerMaster> ownerMasters = tenantMaster.getTenantOwnerMasters();
        final List<TenantOwnerEntity> ownerListEntity = new ArrayList<>();
        TenantOwnerEntity ownerEntity;
        for (final TenantOwnerMaster tenantOwnerMaster : ownerMasters) {
            ownerEntity = new TenantOwnerEntity();
            BeanUtils.copyProperties(tenantOwnerMaster, ownerEntity, MainetConstants.TenantMaster.ExludeCopyArrayTenantOwner);
            ownerEntity.setCreatedDate(new Date());
            ownerEntity.setCreatedBy(entity.getCreatedBy());
            ownerEntity.setIsActive(MainetConstants.RnLCommon.Y);
            ownerEntity.setLgIpMac(entity.getLgIpMac());
            ownerEntity.setLangId(entity.getLangId());
            ownerEntity.setOrgId(entity.getOrgId());
            ownerListEntity.add(ownerEntity);
            ownerEntity.setTenantEntity(entity);
        }
        entity.setTenantOwnerList(ownerListEntity);
        entity = tenantRepository.save(entity);
        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        return entity.getCode();
    }

    private String getNumberBasedOnFunctionValue(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Tripe_Zero;
        case 2:
            return MainetConstants.RnLCommon.Double_Zero;
        case 3:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

    private String getNumberBasedOnOrg(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Double_Zero;
        case 2:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#deleteRecord(java.lang.Character, java.lang.Long)
     */

    @Override
    @Transactional
    public boolean deleteRecord(final Long id, final Long empId) {
        tenantRepository.deleteRecord(MainetConstants.RnLCommon.N, empId, id);
        tenantRepository.deleteRecordOwner(MainetConstants.RnLCommon.N, empId, id);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rentlease.service.IEstateService#saveEdit(com.abm.mainetservice.rentlease.bean.EstateMaster,
     * java.util.List)
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEdit(final TenantMaster tenantMaster,
            final List<AttachDocs> list, final List<Long> ids, final List<Long> removeChildIds) {
        TenantEntity entity = new TenantEntity();
        BeanUtils.copyProperties(tenantMaster, entity);
        final List<TenantOwnerMaster> ownerMasters = tenantMaster.getTenantOwnereditDetails();
        final List<TenantOwnerEntity> ownerListEntity = new ArrayList<>();
        TenantOwnerEntity ownerEntity;
        for (final TenantOwnerMaster tenantOwnerMaster : ownerMasters) {
            if (!removeChildIds.contains(tenantOwnerMaster.getTntOwnerId())) {
                ownerEntity = new TenantOwnerEntity();
                BeanUtils.copyProperties(tenantOwnerMaster, ownerEntity);
                ownerEntity.setTenantEntity(entity);
                if (null != tenantOwnerMaster.getTntOwnerId()) {
                    ownerEntity.setUpdatedDate(new Date());
                    ownerEntity.setUpdatedBy(tenantMaster.getUpdatedBy());
                    ownerEntity.setLgIpMacUp(tenantMaster.getLgIpMacUp());
                } else {
                    ownerEntity.setCreatedDate(new Date());
                    ownerEntity.setCreatedBy(tenantMaster.getUpdatedBy());
                    ownerEntity.setLgIpMac(tenantMaster.getLgIpMacUp());
                    ownerEntity.setLangId(entity.getLangId());
                    ownerEntity.setOrgId(entity.getOrgId());
                    ownerEntity.setIsActive(MainetConstants.RnLCommon.Y);
                }
                ownerListEntity.add(ownerEntity);

            }
        }
        entity.setTenantOwnerList(ownerListEntity);
        entity.setUpdatedDate(new Date());
        entity.setUpdatedBy(tenantMaster.getUpdatedBy());
        entity = tenantRepository.save(entity);
        if (!removeChildIds.isEmpty()) {
            tenantRepository.deleteRecordDetails(MainetConstants.RnLCommon.N, tenantMaster.getUpdatedBy(), removeChildIds);
        }
        if ((ids != null) && !ids.isEmpty()) {
            iAttachDocsDao.updateRecord(ids, tenantMaster.getUpdatedBy(), MainetConstants.RnLCommon.Flag_D);
        }
        if (!list.isEmpty()) {
            for (final AttachDocs attachDocs : list) {
                attachDocs.setIdfId(entity.getCode());
            }
            attachDocsService.saveMasterDocuments(list);
        }
        return true;
    }

}
