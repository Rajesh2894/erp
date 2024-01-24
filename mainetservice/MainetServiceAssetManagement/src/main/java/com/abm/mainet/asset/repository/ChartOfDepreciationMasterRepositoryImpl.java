/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.ChartOfDepreciationMasterEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author sarojkumar.yadav
 *
 * Repository Implementation Class for Chart of Depreciation Master
 */
@Repository
public class ChartOfDepreciationMasterRepositoryImpl extends AbstractDAO<Long>
        implements ChartOfDepreciationMasterRepositoryCustom {

    /**
     * search chart Of Depreciation Master Records by name or/and by accountCode or/and by assetClass or/and by frequency with org
     * id.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List<ChartOfDepreciationMasterEntity> for search criteria
     */
    @Override
    @Transactional
    public List<ChartOfDepreciationMasterEntity> findByAllSearchData(String accountCode, Long assetClass, Long frequency,
            String name, Long orgId, String deptCode) {

        final TypedQuery<ChartOfDepreciationMasterEntity> query = entityManager.createQuery(
                buildSelectDataQuery(accountCode, assetClass, frequency, name, deptCode), ChartOfDepreciationMasterEntity.class);

        if (accountCode != null) {
            query.setParameter("accountCode", accountCode);
        }
        if (assetClass != null) {
            query.setParameter("assetClass", assetClass);
        }
        if (frequency != null) {
            query.setParameter("frequency", frequency);
        }
        if (name != null && !name.isEmpty()) {
            name = "%" + name.toLowerCase() + "%";
            query.setParameter("name", name);
        }
        if (StringUtils.isNotEmpty(deptCode)) {
            query.setParameter("deptCode", deptCode);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        List<ChartOfDepreciationMasterEntity> result = query.getResultList();
        return result;
    }

    /**
     * Method to create Dynamic Select Query depend upon parameters provided in the method
     * 
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return String of Dynamic query
     */
    private String buildSelectDataQuery(String accountCode, Long assetClass, Long frequency, String name, String deptCode) {
        final StringBuilder builder = new StringBuilder();
        builder.append("select cdm FROM ChartOfDepreciationMasterEntity cdm where cdm.orgId=:orgId");
        if ((name != null) && !name.isEmpty()) {
            builder.append(" AND cdm.name LIKE :name");
        }
        if ((accountCode != null)) {
            builder.append(" AND cdm.accountCode=:accountCode");
        }
        if (assetClass != null) {
            builder.append(" AND cdm.assetClass=:assetClass");
        }
        if (frequency != null) {
            builder.append(" AND cdm.frequency=:frequency");
        }
        if (StringUtils.isNotEmpty(deptCode)) {
            builder.append(" and cdm.deptCode =:deptCode ");
        }

        return builder.toString();
    }

    /**
     * Method to create Dynamic Update Query depend upon parameters provided in the method
     * 
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return String of Dynamic query
     */
    private String buildUpdateDataQuery(String accountCode, Long assetClass, Long frequency, String name, Long groupId,
            String remark, BigDecimal rate, Long depreciationKey, Long updatedBy, Date updatedDate, String lgIpMacUpd) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update ChartOfDepreciationMasterEntity cdm set cdm.orgId=:orgId");

        if ((name != null) && !name.isEmpty()) {
            builder.append(",cdm.name=:name");
        }
        if ((accountCode != null)) {
            builder.append(",cdm.accountCode=:accountCode");
        }
        if (assetClass != null) {
            builder.append(",cdm.assetClass=:assetClass");
        }
        if (frequency != null) {
            builder.append(",cdm.frequency=:frequency");
        }

        builder.append(",cdm.remark=:remark");

        if (rate != null) {
            builder.append(",cdm.rate=:rate");
        }

        if (depreciationKey != null) {
            builder.append(",cdm.depreciationKey=:depreciationKey");
        }
        if (updatedBy != null && updatedBy != 0) {
            builder.append(",cdm.updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",cdm.updatedDate=:updatedDate");
        }
        if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
            builder.append(",cdm.lgIpMacUpd=:lgIpMacUpd");
        }
        if (groupId != null && groupId != 0) {
            builder.append(" where cdm.groupId=:groupId");
        }
        return builder.toString();
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
    public boolean updateByGroupId(final ChartOfDepreciationMasterEntity cdmEntity) {
        boolean status = false;
        final Query query = createQuery(buildUpdateDataQuery(cdmEntity.getAccountCode(),
                cdmEntity.getAssetClass(), cdmEntity.getFrequency(), cdmEntity.getName(), cdmEntity.getGroupId(),
                cdmEntity.getRemark(), cdmEntity.getRate(), cdmEntity.getDepreciationKey(), cdmEntity.getUpdatedBy(),
                cdmEntity.getUpdatedDate(), cdmEntity.getLgIpMacUpd()));

        if (cdmEntity.getAccountCode() != null) {
            query.setParameter("accountCode", cdmEntity.getAccountCode());
        }
        if (cdmEntity.getAssetClass() != null) {
            query.setParameter("assetClass", cdmEntity.getAssetClass());
        }
        if (cdmEntity.getFrequency() != null) {
            query.setParameter("frequency", cdmEntity.getFrequency());
        }
        if (cdmEntity.getName() != null && !cdmEntity.getName().isEmpty()) {
            query.setParameter("name", cdmEntity.getName());
        }

        query.setParameter("remark", cdmEntity.getRemark());

        if (cdmEntity.getRate() != null) {
            query.setParameter("rate", cdmEntity.getRate());
        }
        if (cdmEntity.getDepreciationKey() != null) {
            query.setParameter("depreciationKey", cdmEntity.getDepreciationKey());
        }
        if (cdmEntity.getUpdatedBy() != null && cdmEntity.getUpdatedBy() != 0) {
            query.setParameter("updatedBy", cdmEntity.getUpdatedBy());
        }
        if (cdmEntity.getUpdatedDate() != null) {
            query.setParameter("updatedDate", cdmEntity.getUpdatedDate());
        }
        if (cdmEntity.getLgIpMacUpd() != null && !cdmEntity.getLgIpMacUpd().isEmpty()) {
            query.setParameter("lgIpMacUpd", cdmEntity.getLgIpMacUpd());
        }
        if (cdmEntity.getGroupId() != null && cdmEntity.getGroupId() != 0) {
            query.setParameter("groupId", cdmEntity.getGroupId());
        }
        query.setParameter(MainetConstants.Common_Constant.ORGID, cdmEntity.getOrgId());
        int result = query.executeUpdate();
        if (result > 0) {
            status = true;
        }
        return status;
    }

    @Override
    public Long getAssetClassByGroupIdAndAssetClass(Long groupId, Long assetClass2) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<ChartOfDepreciationMasterEntity> chtMas = criteriaQuery.from(ChartOfDepreciationMasterEntity.class);
        criteriaQuery.select(criteriaBuilder.count(chtMas));
        criteriaQuery.where(criteriaBuilder.equal(chtMas.get("groupId"), groupId),
                criteriaBuilder.equal(chtMas.get("assetClass"), assetClass2));
        Query query = entityManager.createQuery(criteriaQuery);
        Long result = (Long) query.getSingleResult();
        return result;

    }

}
