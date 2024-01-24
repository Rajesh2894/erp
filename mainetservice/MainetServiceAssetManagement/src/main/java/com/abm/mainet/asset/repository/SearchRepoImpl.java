package com.abm.mainet.asset.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class SearchRepoImpl extends AbstractDAO<Long> implements SearchRepoCustom {

    @Override
    @Transactional
    public List<Object[]> search(final SearchDTO searchDTO) {

        Long deptId = searchDTO.getDeptId();
        Long assetClass1 = searchDTO.getAssetClass1();
        Long assetClass2 = searchDTO.getAssetClass2();
        String serialNo = searchDTO.getAstSerialNo();
        /*
         * String assetModelIdentifier = searchDTO.getAstModelId(); String assetChildIdentifier = searchDTO.getSubId();
         */
        // Long costCenter = searchDTO.getCostCenter();
        Long assetStatusId = searchDTO.getAssetStatusId();
        String details = searchDTO.getDetails();
        Long locationId = searchDTO.getLocationId();
        Long orgId = searchDTO.getOrgId();
        /* Long inventoryNo = searchDTO.getAstInventId(); */
        Long acquisitionMethodId = searchDTO.getAcquisitionMethodId();
        Long employeeId = searchDTO.getEmployeeId();
        String astAppNo = searchDTO.getAstAppNo();
        String roadName = searchDTO.getRoadName();
        Long pincode = searchDTO.getPincode();
        final Query query = entityManager.createNativeQuery(buildSelectDataQueryNew(deptId, assetClass1, assetClass2,
                serialNo, /* assetModelIdentifier, assetChildIdentifier, */
                /* costCenter */ assetStatusId, details, locationId, orgId/*
                                                                           * , inventoryNo
                                                                           */, acquisitionMethodId, employeeId, astAppNo,
                roadName, pincode, searchDTO.getModuleDeptCode()));

        if (deptId != null && deptId != 0) {
            query.setParameter("deptId", deptId);
        }

        if (assetClass1 != null && assetClass1 != 0) {
            query.setParameter("assetClass1", assetClass1);
        }

        if (assetClass2 != null && assetClass2 != 0) {
            query.setParameter("assetClass2", assetClass2);
        }

        if (serialNo != null && !serialNo.isEmpty()) {
            serialNo = "%" + serialNo + "%";
            query.setParameter("serialNo", serialNo);
        }

        /*
         * if (assetModelIdentifier != null && !assetModelIdentifier.isEmpty()) { query.setParameter("assetModelIdentifier", "%" +
         * assetModelIdentifier + "%"); } if (assetChildIdentifier != null && !assetChildIdentifier.isEmpty()) {
         * query.setParameter("assetChildIdentifier", "%" + assetChildIdentifier + "%"); }
         */

        /*
         * if (costCenter != null && costCenter != 0) { query.setParameter("costCenter", costCenter); }
         */

        if (assetStatusId != null && assetStatusId != 0) {
            query.setParameter("assetStatusId", assetStatusId);
        }

        if (acquisitionMethodId != null && acquisitionMethodId != 0) {
            query.setParameter("acquisitionMethodId", acquisitionMethodId);
        }

        if (employeeId != null && employeeId != 0) {
            query.setParameter("employeeId", employeeId);
        }

        if (details != null && !details.isEmpty()) {
            query.setParameter("details", details);
        }

        if (locationId != null && locationId != 0) {
            query.setParameter("locationId", locationId);
        }

        if (astAppNo != null && !astAppNo.isEmpty()) {
            astAppNo = "%" + astAppNo + "%";
            query.setParameter("astAppNo", astAppNo);
        }

        if (roadName != null && !roadName.isEmpty()) {
            roadName = "%" + roadName + "%";
            query.setParameter("roadName", roadName);
        }

        if (pincode != null && pincode != 0) {
            // pincode = "%" + pincode + "%";
            query.setParameter("pincode", "%" + pincode + "%");
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

        if (StringUtils.isNotEmpty(searchDTO.getModuleDeptCode())) {
            query.setParameter("deptCode", searchDTO.getModuleDeptCode());
        }

        /*
         * if (inventoryNo != null && inventoryNo != 0) { query.setParameter("inventoryNo", inventoryNo); }
         */

        /*
         * query.setFirstResult(0); query.setMaxResults(100);
         */

        List<Object[]> objArry = query.getResultList();

        return objArry;
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
    private String buildSelectDataQueryNew(Long deptId, Long assetClass1, Long assetClass2,
            String serialNo, /* String assetModelIdentifier, String assetChildIdentifier, */
            Long assetStatusId, String details, Long locationId,
            Long orgId/* , Long inventoryNo */, Long acquisitionMethodId, Long employeeId, String astAppNo, String roadName,
            Long pincode, String deptCode) {
        final StringBuilder builder = new StringBuilder();

        /*
         * builder.append("select {ai.*}," + " {ac.*} " +
         * " from tb_ast_info_mst ai left outer join tb_ast_classfctn ac on ai.ASSET_ID=ac.ASSET_ID ");
         */

        builder.append(
                "select fn.ASSET_ID, fn.ASSET_NAME, fn.SERIAL_NO, fn.ASSET_STATUS, fn.ASSET_CLASSIFICATION, fn.ASSET_CLASS, fn.DETAILS, fn.BARCODE_NO, fn.ASSET_APP_STATUS,"
                        + " fn.ASSET_CLASSFCTN_ID, fn.ASSET_ID AS ASTID, fn.LOC_ID, fn.COST_CENTRE, fn.DEPARTMENT, fn.ORGID, fn.ASSET_CODE,fn.ASSET_APP_NO,fn.DEPT_CODE,fn.ASSET_MODEL_IDENTIFIER"
                        + " from (select ai.ASSET_ID,ai.ASSET_NAME,ai.SERIAL_NO,ai.ASSET_STATUS,ai.ACQUISITION_METHOD,ai.EMPLOYEE_ID,ai.ASSET_CLASSIFICATION,ai.ASSET_CLASS,ai.DETAILS,"
                        + " ai.BARCODE_NO,ai.ASSET_APP_STATUS,ai.ASSET_CODE ,ai.ASSET_APP_NO,ai.PINCODE,ai.ROAD_NAME,"
                        + " ac.ASSET_CLASSFCTN_ID, ac.ASSET_ID AS ASTID,"
                        + " ac.LOC_ID,ac.COST_CENTRE,ac.DEPARTMENT, ai.ORGID ,ai.DEPT_CODE,ai.ASSET_MODEL_IDENTIFIER"
                        + " from tb_ast_info_mst ai left outer join tb_ast_classfctn ac on ai.ASSET_ID=ac.ASSET_ID) fn ");

        final List<String> whereClause = new ArrayList<>();

        if (deptId != null && deptId != 0) {
            whereClause.add(" fn.DEPARTMENT =:deptId ");
        }

        /*
         * if (costCenter != null && costCenter != 0) { whereClause.add(" fn.COST_CENTRE =:costCenter "); }
         */

        if (assetStatusId != null && assetStatusId != 0) {
            whereClause.add(" fn.ASSET_STATUS =:assetStatusId ");
        }

        if (acquisitionMethodId != null && acquisitionMethodId != 0) {
            whereClause.add(" fn.ACQUISITION_METHOD =:acquisitionMethodId ");
        }
        if (employeeId != null && employeeId != 0) {
            whereClause.add(" fn.EMPLOYEE_ID =:employeeId ");
        }

        if (locationId != null && locationId != 0) {
            whereClause.add(" fn.LOC_ID =:locationId ");
        }

        if (assetClass1 != null && assetClass1 != 0) {
            whereClause.add(" fn.ASSET_CLASSIFICATION =:assetClass1 ");
        }

        if (assetClass2 != null && assetClass2 != 0) {
            whereClause.add(" fn.ASSET_CLASS =:assetClass2 ");
        }

        if (serialNo != null && !serialNo.isEmpty()) {
            whereClause.add(" fn.ASSET_CODE Like :serialNo ");
        }

        /*
         * if (assetModelIdentifier != null && !assetModelIdentifier.isEmpty()) {
         * builder.append(" And inf.assetModelIdentifier Like :assetModelIdentifier"); } if (assetChildIdentifier != null &&
         * !assetChildIdentifier.isEmpty()) { builder.append(" And inf.assetChildIdentifier Like :assetChildIdentifier"); }
         */

        if (astAppNo != null && !astAppNo.isEmpty()) {
            whereClause.add(" fn.ASSET_APP_NO Like :astAppNo ");
        }

        if (roadName != null && !roadName.isEmpty()) {
            whereClause.add(" fn.ROAD_NAME Like :roadName ");
        }

        if (pincode != null && pincode != 0) {
            whereClause.add(" fn.PINCODE Like :pincode ");
        }

        if (details != null && !details.isEmpty()) {
            whereClause.add(" fn.DETAILS Like :details ");
        }

        if (orgId != null && orgId != 0) {
            whereClause.add(" fn.ORGID =:orgId ");
        }
        if (StringUtils.isNotEmpty(deptCode)) {
            whereClause.add(" fn.DEPT_CODE =:deptCode ");
        }

        StringBuilder tempBuilder = new StringBuilder();
        for (String clause : whereClause) {
            if (tempBuilder.length() == 0) {
                tempBuilder.append(" WHERE ");
            } else {
                tempBuilder.append(" AND ");
            }
            tempBuilder.append(clause);
        }
        builder.append(tempBuilder.toString());
        builder.append(" order by fn.ASSET_ID desc");

        /*
         * if (inventoryNo != null && inventoryNo != 0) { builder.append(" AND inf.inventoryNo =:inventoryNo"); }
         */

        return builder.toString();
    }

}
