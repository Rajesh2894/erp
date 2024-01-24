
package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailHistEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterHistEntity;
import com.abm.mainet.account.dto.BudgetHeadDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * @author Vivek.Kumar
 * @since 13 JAN 2017
 */
@Service
public class VoucherTemplateServiceImpl implements VoucherTemplateService {

    @Resource
    private TbFinancialyearService financialyearService;
    @Resource
    private DepartmentService iDepartmentService;
    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;
    @Resource
    private BudgetHeadProvider budgetHeadProvider;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;
    @Resource
    private AuditService auditService;
    private static final Logger LOGGER = Logger.getLogger(VoucherTemplateServiceImpl.class);

    @Override
    @Transactional
    public VoucherTemplateDTO populateModel(final long orgId) {

        final VoucherTemplateDTO dto = new VoucherTemplateDTO();
        dto.setTemplateLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.ContraVoucherEntry.MTP, orgId));
        dto.setFinancialYearMap(financialyearService.getAllFinincialYear());
        dto.setVouchertTypeLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.ContraVoucherEntry.VOT, orgId));
        dto.setDepartmentLookUps(iDepartmentService.getDepartmentLookUpsByAsc(MainetConstants.MENU.A));
        dto.setTemplateForLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.REV_TYPE_CPD_VALUE, orgId));
        dto.setStatusLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.STATUS_PREFIX, orgId));
        dto.setAccountTypeLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.COA, orgId));
        dto.setModeLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, orgId));
        dto.setDebitCreditLookUps(CommonMasterUtility.lookUpListByPrefix(PrefixConstants.DCR, orgId));

        return dto;
    }

    @Override
    @Transactional
    public List<BudgetHeadDTO> findBudgetCodeByMode(final long payMode, final long orgId) {

        return budgetHeadProvider.findBudgetHeadsByMode(payMode, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.VoucherTemplateService#createVoucherTemplate(com.abm.mainetservice.account.bean.
     * VoucherTemplateDTO, long)
     */
    @SuppressWarnings("unused")
    @Override
    @Transactional
    public boolean createVoucherTemplate(final VoucherTemplateDTO voucherTemplate, final long orgId) {
        boolean isTemplateCreated = true;
        final VoucherTemplateMasterEntity entity = mapToEntity(voucherTemplate, orgId);
        try {
            voucherTemplateRepository.save(entity);
            VoucherTemplateMasterHistEntity voucherTemplateMasterHistEntity = new VoucherTemplateMasterHistEntity();
            if ((Long) entity.getTemplateId() == null) {
                voucherTemplateMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                auditService.createHistory(entity, voucherTemplateMasterHistEntity);
                List<Object> voucherTemplateDetailHistoryList = new ArrayList<>();
                entity.getTemplateDetailEntities().forEach(masDet -> {
                    VoucherTemplateDetailHistEntity voucherTemplateDetailHistEntity = new VoucherTemplateDetailHistEntity();
                    BeanUtils.copyProperties(masDet, voucherTemplateDetailHistEntity);
                    voucherTemplateDetailHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                    voucherTemplateDetailHistoryList.add(voucherTemplateDetailHistEntity);
                });
                auditService.createHistoryForListObj(voucherTemplateDetailHistoryList);
            } else {
                voucherTemplateMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                auditService.createHistory(entity, voucherTemplateMasterHistEntity);
                List<Object> voucherTemplateDetailHistoryList = new ArrayList<>();
                entity.getTemplateDetailEntities().forEach(masDet -> {
                    VoucherTemplateDetailHistEntity voucherTemplateDetailHistEntity = new VoucherTemplateDetailHistEntity();
                    BeanUtils.copyProperties(masDet, voucherTemplateDetailHistEntity);
                    voucherTemplateDetailHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    voucherTemplateDetailHistoryList.add(voucherTemplateDetailHistEntity);
                });
                auditService.createHistoryForListObj(voucherTemplateDetailHistoryList);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + entity, ex);
        }
        isTemplateCreated = true;
        return isTemplateCreated;
    }

    private VoucherTemplateMasterEntity mapToEntity(final VoucherTemplateDTO voucherTemplate, final long orgId) {

        final VoucherTemplateMasterEntity masterEntity = new VoucherTemplateMasterEntity();
        BeanUtils.copyProperties(voucherTemplate, masterEntity);
        // setting some common fields
        masterEntity.setCreatedDate(new Date());
        masterEntity.setLgIpMac(voucherTemplate.getIpMacAddress());
        masterEntity.setOrgid(orgId);
        if ((masterEntity.getFinancialYear() != null)
                && (masterEntity.getFinancialYear() == 0l)) {
            masterEntity.setFinancialYear(null);
        }
        // prepare child records
        final List<VoucherTemplateDetailEntity> mappingList = new ArrayList<>();
        if ((voucherTemplate.getMappingDetails() == null)
                || voucherTemplate.getMappingDetails().isEmpty()) {
            throw new IllegalArgumentException("Mapping Details List cannot be null or empty.");
        }
        VoucherTemplateDetailEntity childEntity = null;
        for (final VoucherTemplateDTO mapping : voucherTemplate.getMappingDetails()) {
            childEntity = new VoucherTemplateDetailEntity();
            BeanUtils.copyProperties(mapping, childEntity);
            childEntity.setTemplateId(masterEntity);
            childEntity.setCpdIdDrcr(mapping.getDebitCredit());
            childEntity.setCpdIdPayMode(mapping.getMode());
            if (mapping.getSacHeadId() != null && mapping.getSacHeadId() != 0L) {
                childEntity.setSacHeadId(mapping.getSacHeadId());
            } else {
                childEntity.setSacHeadId(null);
            }
            if (mapping.getStatus() != null) {
                childEntity.setCpdStatusFlg(mapping.getStatus());
            } else {
                childEntity.setCpdStatusFlg(voucherTemplate.getStatus());
            }
            childEntity.setOrgid(masterEntity.getOrgid());
            childEntity.setCreatedBy(voucherTemplate.getCreatedBy());
            childEntity.setCreatedDate(new Date());
            childEntity.setLangId(voucherTemplate.getLangId());
            childEntity.setLgIpMac(voucherTemplate.getIpMacAddress());

            if ((Long) voucherTemplate.getTemplateId() != null && (Long) voucherTemplate.getTemplateId() > 0) {
                childEntity.setUpdatedBy(voucherTemplate.getUpdatedBy());
                childEntity.setUpdatedDate(voucherTemplate.getUpdatedDate());
                childEntity.setLgIpMacUpd(voucherTemplate.getLgIpMacUpd());
            }
            mappingList.add(childEntity);

        }
        masterEntity.setTemplateDetailEntities(mappingList);

        return masterEntity;

    }

    @Override
    @Transactional
    public boolean isTemplateExist(final VoucherTemplateDTO voucherTemplate, final long orgId) {

        final long count = voucherTemplateRepository.templateExistOrNot(voucherTemplate.getTemplateType(),
                voucherTemplate.getFinancialYear(),
                voucherTemplate.getVoucherType(), voucherTemplate.getDepartment(),
                voucherTemplate.getTemplateFor(), orgId);
        boolean isTemplateExist = false;
        if (count > 0) {
            isTemplateExist = true;
        }
        return isTemplateExist;
    }

    @Override
    @Transactional
    public List<VoucherTemplateDTO> searchTemplate(final VoucherTemplateDTO dto, final long orgId) {

        final List<VoucherTemplateMasterEntity> templateList = voucherTemplateRepository.searchTemplate(dto.getTemplateType(),
                dto.getFinancialYear(),
                dto.getVoucherType(), dto.getDepartment(), dto.getTemplateFor(), dto.getStatus(), orgId);

        return mapToDTO(templateList);
    }

    private List<VoucherTemplateDTO> mapToDTO(final List<VoucherTemplateMasterEntity> templateList) {

        List<VoucherTemplateDTO> dtoList = Collections.emptyList();
        VoucherTemplateDTO templateDTO = null;
        if ((templateList != null)
                && !templateList.isEmpty()) {
            dtoList = new ArrayList<>();
            for (final VoucherTemplateMasterEntity entity : templateList) {
                templateDTO = new VoucherTemplateDTO();
                BeanUtils.copyProperties(entity, templateDTO);
                templateDTO.setGridId(entity.getTemplateId());
                if ((entity.getTemplateType() == null) || (entity.getTemplateType() == 0l)) {
                    throw new IllegalArgumentException(
                            "Junk data found! templateType(TB_AC_VOUCHERTEMPLATE_MAS.CPD_ID_MAPPING_TYPE) In Primary Key Id is :"
                                    + entity.getTemplateId() + " found null, can not be null");
                }
                templateDTO.setTemplateTypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.ContraVoucherEntry.MTP,
                        entity.getOrgid(), entity.getTemplateType()));
                templateDTO.setVoucherTypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.ContraVoucherEntry.VOT,
                        entity.getOrgid(), entity.getVoucherType()));
                templateDTO.setTemplateForDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.REV_TYPE_CPD_VALUE,
                        entity.getOrgid(), entity.getTemplateFor()));
                templateDTO.setStatusDesc(
                        CommonMasterUtility.findLookUpDesc(PrefixConstants.STATUS_PREFIX, entity.getOrgid(), entity.getStatus()));
                if(entity.getDepartment()!=null)
                	templateDTO.setDepartmentDesc(departmentRepository.fetchDepartmentDescById(entity.getDepartment()));
                if(entity.getFinancialYear()!=null)
                	templateDTO.setFinancialYearDesc(financialyearService.findFinancialYearDesc(entity.getFinancialYear()));

                dtoList.add(templateDTO);
            }
        }

        return dtoList;
    }

    @Override
    @Transactional
    public VoucherTemplateDTO viewTemplate(final long templateId) {

        final VoucherTemplateMasterEntity masterEntity = voucherTemplateRepository.queryTemplateById(templateId);
        final VoucherTemplateDTO masterDTO = new VoucherTemplateDTO();
        masterDTO.setTemplateTypeDesc(
                CommonMasterUtility.findLookUpDesc(PrefixConstants.ContraVoucherEntry.MTP, masterEntity.getOrgid(),
                        masterEntity.getTemplateType()));
        if (masterEntity.getFinancialYear() != null) {
            masterDTO.setFinancialYearDesc(financialyearService.findFinancialYearDesc(masterEntity.getFinancialYear()));
        }
        masterDTO.setVoucherTypeDesc(
                CommonMasterUtility.findLookUpDesc(PrefixConstants.ContraVoucherEntry.VOT, masterEntity.getOrgid(),
                        masterEntity.getVoucherType()));
        masterDTO.setDepartmentDesc(departmentRepository.fetchDepartmentDescById(masterEntity.getDepartment()));
        masterDTO.setTemplateForDesc(
                CommonMasterUtility.findLookUpDesc(PrefixConstants.REV_TYPE_CPD_VALUE, masterEntity.getOrgid(),
                        masterEntity.getTemplateFor()));
        masterDTO.setStatusDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.STATUS_PREFIX, masterEntity.getOrgid(),
                masterEntity.getStatus()));
        // adding mapping details
        final List<VoucherTemplateDTO> detailList = new ArrayList<>();
        VoucherTemplateDTO detailDTO = null;
        for (final VoucherTemplateDetailEntity detailEntity : masterEntity.getTemplateDetailEntities()) {
            detailDTO = new VoucherTemplateDTO();
            BeanUtils.copyProperties(detailEntity, detailDTO);
            if (detailEntity.getAccountType() != null) {
                detailDTO.setAccountTypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.COA, masterEntity.getOrgid(),
                        detailEntity.getAccountType()));
            }
            if (detailEntity.getCpdIdDrcr() != null) {
                detailDTO.setDrCrDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.DCR, masterEntity.getOrgid(),
                        detailEntity.getCpdIdDrcr()));
            }
            if (detailEntity.getCpdIdPayMode() != null) {
                detailDTO.setModeDesc(
                        CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, masterEntity.getOrgid(),
                                detailEntity.getCpdIdPayMode()));
            }
            if (detailEntity.getSacHeadId() != null) {
                detailDTO.setSacHeadId(detailEntity.getSacHeadId());
            }
            // need to do ur activity
            final Long orgId = masterEntity.getOrgid();
            final Long accountType = detailEntity.getAccountType();
            Long sacHeadId = null;
            if (detailEntity.getSacHeadId() != null) {
                sacHeadId = detailEntity.getSacHeadId();
                final String acHeadCode = voucherTemplateRepository.callAcHeadCodeDesc(orgId, templateId, accountType,
                        sacHeadId);
                if ((acHeadCode != null) && !acHeadCode.isEmpty()) {
                    detailDTO.setAcHeadDesc(acHeadCode);
                }
            }
            detailList.add(detailDTO);
        }

        masterDTO.setMappingDetails(detailList);

        return masterDTO;
    }

    @Override
    @Transactional
    public VoucherTemplateDTO editTemplate(final long templateId) {

        final VoucherTemplateMasterEntity masterEntity = voucherTemplateRepository.queryTemplateById(templateId);
        // setting master data
        final VoucherTemplateDTO masterDTO = new VoucherTemplateDTO();
        BeanUtils.copyProperties(masterEntity, masterDTO);
        final List<VoucherTemplateDTO> detailList = new ArrayList<>();
        VoucherTemplateDTO detailDTO = null;
        for (final VoucherTemplateDetailEntity detailEntity : masterEntity.getTemplateDetailEntities()) {
            detailDTO = new VoucherTemplateDTO();
            BeanUtils.copyProperties(detailEntity, detailDTO);
            detailDTO.setTemplateIdDet(detailEntity.getTemplateIdDet());
            detailDTO.setStatus(detailEntity.getCpdStatusFlg());
            detailDTO.setDebitCredit(detailEntity.getCpdIdDrcr());
            detailDTO.setMode(detailEntity.getCpdIdPayMode());
            if (detailEntity.getSacHeadId() != null && detailEntity.getSacHeadId() != 0L) {
                detailDTO.setSacHeadId(detailEntity.getSacHeadId());
            }
            detailList.add(detailDTO);
        }
        masterDTO.setMappingDetails(detailList);
        return masterDTO;
    }

    @Override
    @Transactional
    public List<VoucherTemplateDTO> searchByAllTemplateData(final long orgId) {
        final List<VoucherTemplateMasterEntity> templateList = voucherTemplateRepository.searchByAllTemplateData(orgId);

        return mapToDTO(templateList);
    }

    @Override
    @Transactional
    public Long getSacHeadIdForVoucherTemplate(Long voucherSubTypeId, Long modeId, Long deptId, Long status, Long orgid) {
        // TODO Auto-generated method stub
        VoucherTemplateMasterEntity entity = voucherTemplateRepository.getSacHeadIdForVoucherTemplate(voucherSubTypeId,
                deptId, status,
                orgid);
        Long sacHeadId = null;
        List<VoucherTemplateDetailEntity> list = entity.getTemplateDetailEntities();
        for (VoucherTemplateDetailEntity listDto : list) {
            if (listDto.getCpdIdPayMode().equals(modeId)) {
                sacHeadId = listDto.getSacHeadId();
                break;
            }
        }
        return sacHeadId;
    }

    @Override
    @Transactional
    public String getVoucherNoBy(Long LookUpId, String vouReferenceNo, Date vouPostingDate, Long orgId) {
        // TODO Auto-generated method stub
        String voucherNumber = voucherTemplateRepository.getVoucherNo(LookUpId, vouReferenceNo, vouPostingDate, orgId);
        return voucherNumber;
    }

}
