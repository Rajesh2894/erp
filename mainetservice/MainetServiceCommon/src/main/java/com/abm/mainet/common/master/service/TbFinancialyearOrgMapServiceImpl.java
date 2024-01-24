/**
 *
 */
package com.abm.mainet.common.master.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbFincialyearorgMapEntity;
import com.abm.mainet.common.domain.TbFincialyearorgMapEntityHistory;
import com.abm.mainet.common.master.dto.TbFincialyearorgMap;
import com.abm.mainet.common.master.mapper.TbFincialyearorgMapServiceMapper;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.repository.TbFincialyearorgMapJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Harsha.Ramachandran
 *
 */
@Service
public class TbFinancialyearOrgMapServiceImpl implements TbFinancialyearOrgMapService {

    @Autowired
    private AuditService auditService;

    @Resource
    private TbFincialyearorgMapJpaRepository tbFincialyearorgMapJpaRepository;

    @Resource
    private TbFincialyearorgMapServiceMapper tbFincialyearorgMapServiceMapper;

    @Resource
    private IFinancialYearProvisionService financialYearProvisionService;

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;

    @Override
    @Transactional
    public TbFincialyearorgMap create(final TbFincialyearorgMap bean) {

        final Employee sessionEmp = UserSession.getCurrent().getEmployee();
        final TbFincialyearorgMapEntity entity = new TbFincialyearorgMapEntity();
        TbFincialyearorgMapEntity entitySaved = new TbFincialyearorgMapEntity();
        bean.setCreatedBy(sessionEmp.getEmpId());
        bean.setCreatedDate(new Date());
        tbFincialyearorgMapServiceMapper.mapTbFincialyearorgMapToTbFincialyearorgMapEntity(bean, entity);
        entitySaved = tbFincialyearorgMapJpaRepository.save(entity);

        TbFincialyearorgMapEntityHistory tbFincialyearorgMapHistEntity = new TbFincialyearorgMapEntityHistory();
        tbFincialyearorgMapHistEntity.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        tbFincialyearorgMapHistEntity.setFaYearID(bean.getFaYearid());
        auditService.createHistory(entitySaved, tbFincialyearorgMapHistEntity);
        TbFincialyearorgMap savedMappingDto = tbFincialyearorgMapServiceMapper
                .mapTbFincialyearorgMapEntityToTbFincialyearorgMap(entitySaved);

        if (savedMappingDto.getFaFromDate() == null || savedMappingDto.getFaToDate() == null) {
            FinancialYear finYear = tbFinancialyearJpaRepository.findOne(savedMappingDto.getFaYearid());
            savedMappingDto.setFaFromDate(finYear.getFaFromDate());
            savedMappingDto.setFaToDate(finYear.getFaToDate());
        }

        if (savedMappingDto.getYaTypeCpdId() != null) {
            Organisation orgDto = new Organisation();
            orgDto.setOrgid(savedMappingDto.getOrgid());
            savedMappingDto.setFaYearstatusDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(savedMappingDto.getYaTypeCpdId(), orgDto)
                            .getDescLangFirst());
        }

        /**
         * this service is used to push created or updated Financial Year Organization mapping data from MAINet to Other
         * Applications like GRP.if GRP posting flag is 'Y'. if yes than only Location data push from MAINet to GRP.flag is
         * configured in serviceConfiguration.properties file.
         */
        financialYearProvisionService.createFinYearMapping(savedMappingDto);

        return savedMappingDto;
    }

    @Override
    @Transactional
    public TbFincialyearorgMapEntity findByOrgId(final Long faYearId, final Long orgId) {
        final TbFincialyearorgMapEntity tbFincialyearorgMap = tbFincialyearorgMapJpaRepository.findOrgFincialYear(orgId,
                faYearId);
        return tbFincialyearorgMap;
    }

    @Override
    @Transactional
    public TbFincialyearorgMap update(final TbFincialyearorgMap bean) {

        TbFincialyearorgMapEntity entity = tbFincialyearorgMapJpaRepository.findOne(bean.getMapId());
        Long orgId = entity.getOrgid();
        entity.setUpdatedDate(new Date());
        entity.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        entity.setYaTypeCpdId(bean.getYaTypeCpdId());
        entity.setFaFromMonth(bean.getFaFromMonth());
        entity.setFaToMonth(bean.getFaToMonth());
        entity.setFaMonthStatus(bean.getFaMonthStatus());
        entity.setOrgid(orgId);
        TbFincialyearorgMapEntity entitySaved = tbFincialyearorgMapJpaRepository.save(entity);
        TbFincialyearorgMap updatedMappingDto = tbFincialyearorgMapServiceMapper
                .mapTbFincialyearorgMapEntityToTbFincialyearorgMap(entitySaved);

        /**
         * this service is used to push created or updated Financial Year Organization mapping data from MAINet to Other
         * Applications like GRP.if GRP posting flag is 'Y'. if yes than only Location data push from MAINet to GRP.flag is
         * configured in serviceConfiguration.properties file.
         */
        if (updatedMappingDto.getYaTypeCpdId() != null) {
            Organisation orgDto = new Organisation();
            orgDto.setOrgid(updatedMappingDto.getOrgid());
            updatedMappingDto.setFaYearstatusDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(updatedMappingDto.getYaTypeCpdId(), orgDto)
                            .getDescLangFirst());
        }
        financialYearProvisionService.updateFinYearMapping(updatedMappingDto);
        return updatedMappingDto;

    }

    @Override
    @Transactional
    public TbFincialyearorgMapEntity findById(final Long mapId) {
        final TbFincialyearorgMapEntity entity = tbFincialyearorgMapJpaRepository.findOne(mapId);
        return entity;
    }
}
