package com.abm.mainet.asset.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetAnnualPlan;
import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.domain.AssetRequisition;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class AssetFunctionalLocationMasterDAOImpl extends AbstractDAO<Long> implements AssetFunctionalLocationMasterDAO {

    /**
     * returns All AssetFunctionalLocation object List by default use to display parent dropdw n values
     * 
     * @return AssetFunctionalLocation List of objects
     */
    @Override
    public List<AssetFunctionalLocation> retriveList(final Long orgId) {

        String hql = "SELECT flc from AssetFunctionalLocation flc where flc.orgId=" + orgId;

        // final Query query = createQuery(hql.toString());
        TypedQuery<AssetFunctionalLocation> query = this.entityManager.createQuery(hql.toString(), AssetFunctionalLocation.class);

        List<AssetFunctionalLocation> assetFuncLocList = query.getResultList();

        return assetFuncLocList;
    }

    /**
     * save AssetFunctionalLocation object present inside the List to database
     * 
     * @return AssetFunctionalLocation List of objects
     */
    @Override
    public boolean save(List<AssetFunctionalLocation> funcLocCodeList) {
        for (int i = 0; i < funcLocCodeList.size(); i++) {
            this.entityManager.persist(funcLocCodeList.get(i));
        }

        return true;
    }

    /**
     * update AssetFunctionalLocation object present inside the List to database
     * 
     * @return true if successfully saved in database.
     */
    @Override
    public boolean update(AssetFunctionalLocation funcLocCode) {
        final String description = funcLocCode.getDescription();
        final String startPoint = funcLocCode.getStartPoint();
        final String endPoint = funcLocCode.getEndPoint();
        final Long unit = funcLocCode.getUnit();
        final Long orgId = funcLocCode.getOrgId();
        final Long updatedBy = funcLocCode.getUpdatedBy();
        final Date updatedDate = funcLocCode.getUpdatedDate();
        final String lgIpMacUpd = funcLocCode.getLgIpMacUpd();
        String funcLocationCode = funcLocCode.getFuncLocationCode();
        // Long funcLocParentId = funcLocCode.getFuncLocParentId();
        Long funcLocParentId = null;

        if (funcLocCode.getFlcObject() != null)
            funcLocParentId = funcLocCode.getFlcObject().getFuncLocationId();

        boolean status = false;
        final Query query = createQuery(buildUpdateDataQuery(description, startPoint, endPoint,
                unit, funcLocParentId, orgId, updatedBy, updatedDate,
                lgIpMacUpd, funcLocationCode));

        if (description != null && !description.isEmpty()) {
            query.setParameter("description", description);
        }
        if (startPoint != null && !startPoint.isEmpty()) {
            query.setParameter("startPoint", startPoint);
        }
        if (endPoint != null && !endPoint.isEmpty()) {
            query.setParameter("endPoint", endPoint);
        }
        if (unit != null && unit != 0) {
            query.setParameter("unit", unit);
        }
        /*
         * if (funcLocParentId != null ) { query.setParameter("funcLocParentId", funcLocParentId); }
         */

        query.setParameter("funcLocParentId", funcLocParentId);

        if (updatedBy != null && updatedBy != 0) {
            query.setParameter("updatedBy", updatedBy);
        }
        if (updatedDate != null) {
            query.setParameter("updatedDate", updatedDate);
        }
        if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
            query.setParameter("lgIpMacUpd", lgIpMacUpd);
        }

        if (funcLocationCode != null && !funcLocationCode.isEmpty()) {
            query.setParameter("funcLocationCode", funcLocationCode);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        int result = query.executeUpdate();

        if (result > 0) {
            status = true;
        }
        return status;
    }

    /**
     * Method to create Dynamic Update Query depend upon parameters provided in the method
     * 
     * @return String of Dynamic query
     */
    private String buildUpdateDataQuery(String description, String startPoint, String endPoint, Long unit, Long funcLocParentId,
            Long orgId, Long updatedBy, Date updatedDate, String lgIpMacUpd, String funcLocationCode) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update AssetFunctionalLocation flc set flc.orgId=:orgId");

        if ((description != null) && !description.isEmpty()) {
            builder.append(",flc.description=:description");
        }
        if ((startPoint != null) && !startPoint.isEmpty()) {
            builder.append(",flc.startPoint=:startPoint");
        }
        if (endPoint != null && !endPoint.isEmpty()) {
            builder.append(",flc.endPoint=:endPoint");
        }
        if (unit != null && unit != 0) {
            builder.append(",flc.unit=:unit");
        }
        /*
         * if (funcLocParentId != null) { builder.append(",flc.flcObject.funcLocationId=:funcLocParentId"); }
         */

        builder.append(",flc.flcObject.funcLocationId=:funcLocParentId");

        if (updatedBy != null && updatedBy != 0) {
            builder.append(",flc.updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",flc.updatedDate=:updatedDate");
        }
        if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
            builder.append(",flc.lgIpMacUpd=:lgIpMacUpd");
        }

        if (funcLocationCode != null && !funcLocationCode.isEmpty()) {
            builder.append(" where flc.funcLocationCode=:funcLocationCode");
        }
        return builder.toString();
    }

    /**
     * used validate the function location code based onblur function
     * 
     * @param funcLocCode
     * @return true when duplicate
     */
    @Override
    public boolean isDuplicate(String funcLocCode, Long orgId) {

        String hql = "SELECT COUNT(flc) from AssetFunctionalLocation flc WHERE flc.funcLocationCode=:funcLocCode and flc.orgId=:orgId";

        final Query query = createQuery(hql.toString());
        query.setParameter("funcLocCode", funcLocCode);
        query.setParameter("orgId", orgId);
        Long count = (Long) query.getSingleResult();

        if (count == 1)
            return true;
        else
            return false;
    }

    /**
     * used to filter search results on basis of below given parameters
     * 
     * @param funcLocCode
     * @param description
     * @param orgId
     */
    @Override
    public List<AssetFunctionalLocation> search(String funcLocCode, String description, Long orgId)//
    {
        StringBuilder hql = new StringBuilder("SELECT flc from AssetFunctionalLocation flc where flc.orgId=:orgId ");

        if (funcLocCode != null && !funcLocCode.isEmpty())
            hql.append("and flc.funcLocationCode Like :funcLocCode ");
        if (description != null && !description.isEmpty())
            hql.append("and flc.description Like :description ");

        TypedQuery<AssetFunctionalLocation> query = this.entityManager.createQuery(hql.toString(), AssetFunctionalLocation.class);

        if (orgId != null)
            query.setParameter("orgId", orgId);
        if (funcLocCode != null && !funcLocCode.isEmpty())
            query.setParameter("funcLocCode", "%" + funcLocCode + "%");
        if (description != null && !description.isEmpty())
            query.setParameter("description", "%" + description + "%");

        List<AssetFunctionalLocation> assetFuncLocList = query.getResultList();

        return assetFuncLocList;
    }

    /**
     * returns AssetFunctionalLocation object based on
     * 
     * @param funcLocId
     * @param orgId
     * 
     * @return AssetFunctionalLocation
     */
    @Override
    public AssetFunctionalLocation findByFuncLocId(Long funcLocId, Long orgId) {

        AssetFunctionalLocation assetFuncLoc = null;

        String hql = "SELECT flc from AssetFunctionalLocation flc WHERE flc.funcLocationId=:funcLocationId and flc.orgId=:orgId";

        final Query query = createQuery(hql.toString());

        if (orgId != null)
            query.setParameter("orgId", orgId);
        if (funcLocId != null)
            query.setParameter("funcLocationId", funcLocId);

        List<AssetFunctionalLocation> assetFuncLocList = query.getResultList();

        if (assetFuncLocList.size() == 1) {
            assetFuncLoc = assetFuncLocList.get(0);
        }

        return assetFuncLoc;
    }

    // D#89523
    @SuppressWarnings("unchecked")
    @Override
    public List<AssetRequisition> searchAssetRequisitionData(Long astCategoryId, Long locId, Long deptId, Long orgId,
            String moduleDeptCode) {
        List<AssetRequisition> requisitions = new ArrayList<AssetRequisition>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT ar FROM AssetRequisition ar  where ar.orgId = :orgId ");

            if (Optional.ofNullable(astCategoryId).orElse(0L) != 0) {
                jpaQuery.append(" and ar.astCategory = :astCategoryId ");
            }
            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                jpaQuery.append(" and ar.astLoc = :locId ");
            }

            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                jpaQuery.append(" and ar.astDept = :deptId ");
            }

            if (StringUtils.isNotEmpty(moduleDeptCode)) {
                jpaQuery.append(" and ar.deptCode =:deptCode ");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgId", orgId);

            if (Optional.ofNullable(astCategoryId).orElse(0L) != 0) {
                hqlQuery.setParameter("astCategoryId", astCategoryId);
            }

            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                hqlQuery.setParameter("locId", locId);
            }

            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                hqlQuery.setParameter("deptId", deptId);
            }

            if (StringUtils.isNotEmpty(moduleDeptCode)) {
                hqlQuery.setParameter("deptCode", moduleDeptCode);
            }

            requisitions = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Record", exception);
        }

        return requisitions;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssetAnnualPlan> searchAssetAnnualPlanData(Long finYearId, Long deptId, Long locId,
            Long orgId, String moduleDeptCode) {
        List<AssetAnnualPlan> annualPlans = new ArrayList<AssetAnnualPlan>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT ap FROM AssetAnnualPlan ap JOIN FETCH ap.locationMas JOIN FETCH ap.department where ap.orgId = :orgId ");

            if (Optional.ofNullable(finYearId).orElse(0L) != 0) {
                jpaQuery.append(" and ap.financialYear = :finYearId ");
            }
            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                jpaQuery.append(" and ap.department.dpDeptid = :deptId ");
            }

            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                jpaQuery.append(" and ap.locationMas.locId = :locId ");
            }

            if (StringUtils.isNotEmpty(moduleDeptCode)) {
                jpaQuery.append(" and ap.deptCode =:deptCode ");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgId", orgId);

            if (Optional.ofNullable(finYearId).orElse(0L) != 0) {
                hqlQuery.setParameter("finYearId", finYearId);
            }

            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                hqlQuery.setParameter("deptId", deptId);
            }

            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                hqlQuery.setParameter("locId", locId);
            }

            if (StringUtils.isNotEmpty(moduleDeptCode)) {
                hqlQuery.setParameter("deptCode", moduleDeptCode);
            }

            annualPlans = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Annual Plan Record", exception);
        }
        return annualPlans;
    }

}
