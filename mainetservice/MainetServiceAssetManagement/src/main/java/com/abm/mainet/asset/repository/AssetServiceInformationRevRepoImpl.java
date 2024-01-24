/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetServiceInformation;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetServiceInformationRevRepoImpl implements AssetServiceInformationRevRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

  /*  @Override
    public void updateByAssetId(final Long assetServiceId, final AssetServiceInformation entity) {
        final Query query = entityManager.createQuery(buildUpdateDataQuery(assetServiceId, entity.getServiceNo(),
                entity.getServiceProvider(), entity.getServiceStartDate(), entity.getServiceExpiryDate(),
                entity.getServiceAmount(), entity.getWarrenty(), entity.getCostCenter(), entity.getServiceContent(),
                entity.getServiceDescription(), entity.getAssessmentNo(), entity.getPropertyRegistrationNo(),
                entity.getTaxCode(), entity.getRealEstateAmount(), entity.getTaxZoneLocation(),
                entity.getMunicipalityName(), entity.getUpdatedBy(), entity.getUpdatedDate(), entity.getLgIpMacUpd()));

        query.setParameter("serviceNo", entity.getServiceNo());
        query.setParameter("serviceProvider", entity.getServiceProvider());
        query.setParameter("serviceStartDate", entity.getServiceStartDate());
        query.setParameter("serviceExpiryDate", entity.getServiceExpiryDate());
        query.setParameter("serviceAmount", entity.getServiceAmount());
        query.setParameter("warrenty", entity.getWarrenty());
        query.setParameter("costCenter", entity.getCostCenter());
        query.setParameter("serviceContent", entity.getServiceContent());
        query.setParameter("serviceDescription", entity.getServiceDescription());
        query.setParameter("assessmentNo", entity.getAssessmentNo());
        query.setParameter("propertyRegistrationNo", entity.getPropertyRegistrationNo());
        query.setParameter("taxCode", entity.getTaxCode());
        query.setParameter("realEstateAmount", entity.getRealEstateAmount());
        query.setParameter("taxZoneLocation", entity.getTaxZoneLocation());
        query.setParameter("municipalityName", entity.getMunicipalityName());
        if (entity.getUpdatedBy() != null && entity.getUpdatedBy() != 0) {
            query.setParameter("updatedBy", entity.getUpdatedBy());
        }
        if (entity.getUpdatedDate() != null) {
            query.setParameter("updatedDate", entity.getUpdatedDate());
        }
        if (entity.getLgIpMacUpd() != null && !entity.getLgIpMacUpd().isEmpty()) {
            query.setParameter("lgIpMacUpd", entity.getLgIpMacUpd());
        }
        query.setParameter("assetServiceId", assetServiceId);
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Asset Service Information Details could not be updated");

        }
    }

    *//**
     * Method to create Dynamic Update Query depend upon parameters provided in the method
     * 
     * @return String of Dynamic query
     *//*
    private String buildUpdateDataQuery(Long assetServiceId, Long serviceNo, String serviceProvider,
            Date serviceStartDate, Date serviceExpiryDate, BigDecimal serviceAmount, Long warrenty, Long costCenter,
            String serviceContent, String serviceDescription, Long assessmentNo, Long propertyRegistrationNo,
            Long taxCode, BigDecimal realEstateAmount, String taxZoneLocation, String municipalityName, Long updatedBy,
            Date updatedDate, String lgIpMacUpd) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update AssetServiceInformation asi set");
        builder.append(" asi.serviceNo=:serviceNo");
        builder.append(",asi.serviceProvider=:serviceProvider");
        builder.append(",asi.serviceStartDate=:serviceStartDate");
        builder.append(",asi.serviceExpiryDate=:serviceExpiryDate");
        builder.append(",asi.serviceAmount=:serviceAmount");
        builder.append(",asi.warrenty=:warrenty");
        builder.append(",asi.costCenter=:costCenter");
        builder.append(",asi.serviceContent=:serviceContent");
        builder.append(",asi.serviceDescription=:serviceDescription");
        builder.append(",asi.assessmentNo=:assessmentNo");
        builder.append(",asi.propertyRegistrationNo=:propertyRegistrationNo");
        builder.append(",asi.taxCode=:taxCode");
        builder.append(",asi.realEstateAmount=:realEstateAmount");
        builder.append(",asi.taxZoneLocation=:taxZoneLocation");
        builder.append(",asi.municipalityName=:municipalityName");
        if (updatedBy != null && updatedBy != 0) {
            builder.append(",asi.updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",asi.updatedDate=:updatedDate");
        }
        if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
            builder.append(",asi.lgIpMacUpd=:lgIpMacUpd");
        }
        builder.append(" where asi.assetServiceId=:assetServiceId");
        return builder.toString();
    }*/
}
