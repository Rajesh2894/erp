/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetLeasingCompany;
import com.abm.mainet.asset.domain.AssetLeasingCompanyRev;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class LeasingCompanyMapper {

    public static AssetLeasingCompany mapToEntity(AssetLeasingCompanyDTO leasingCompDto) {
        AssetLeasingCompany leasingEntity = new AssetLeasingCompany();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(leasingCompDto.getAssetId());
        if (leasingCompDto.getAssetLeasingId() != null) {
            leasingEntity.setAssetLeasingID(leasingCompDto.getAssetLeasingId());
        }
        leasingEntity.setContractAgreementNo(leasingCompDto.getContractAgreementNo());
        leasingEntity.setAgreementDate(leasingCompDto.getAgreementDate());
        leasingEntity.setNoticeDate(leasingCompDto.getNoticeDate());
        leasingEntity.setLeaseStartDate(leasingCompDto.getLeaseStartDate());
        leasingEntity.setLeaseEndDate(leasingCompDto.getLeaseEndDate());
        leasingEntity.setLeaseType(leasingCompDto.getLeaseType());
        leasingEntity.setPurchasePrice(leasingCompDto.getPurchasePrice());
        leasingEntity.setNoOfInstallment(leasingCompDto.getNoOfInstallment());
        leasingEntity.setPaymentFrequency(leasingCompDto.getPaymentFrequency());
        leasingEntity.setAdvancedPayment(leasingCompDto.getAdvancedPayment());
        leasingEntity.setCreatedBy(leasingCompDto.getCreatedBy());
        leasingEntity.setCreatedDate(leasingCompDto.getCreationDate());
        leasingEntity.setUpdatedBy(leasingCompDto.getUpdatedBy());
        leasingEntity.setUpdatedDate(leasingCompDto.getUpdatedDate());
        leasingEntity.setLgIpMac(leasingCompDto.getLgIpMac());
        leasingEntity.setLgIpMacUpd(leasingCompDto.getLgIpMacUpd());
        leasingEntity.setAssetId(astInfo);
        return leasingEntity;

    }

    public static AssetLeasingCompanyRev mapToEntityRev(AssetLeasingCompanyDTO leasingCompDto) {
        AssetLeasingCompanyRev leasingEntity = new AssetLeasingCompanyRev();
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(leasingCompDto.getAssetId());
        if (leasingCompDto.getAssetLeasingId() != null) {
            leasingEntity.setAssetLeasingID(leasingCompDto.getAssetLeasingId());
        }
        leasingEntity.setContractAgreementNo(leasingCompDto.getContractAgreementNo());
        leasingEntity.setAgreementDate(leasingCompDto.getAgreementDate());
        leasingEntity.setNoticeDate(leasingCompDto.getNoticeDate());
        leasingEntity.setLeaseStartDate(leasingCompDto.getLeaseStartDate());
        leasingEntity.setLeaseEndDate(leasingCompDto.getLeaseEndDate());
        leasingEntity.setLeaseType(leasingCompDto.getLeaseType());
        leasingEntity.setPurchasePrice(leasingCompDto.getPurchasePrice());
        leasingEntity.setNoOfInstallment(leasingCompDto.getNoOfInstallment());
        leasingEntity.setPaymentFrequency(leasingCompDto.getPaymentFrequency());
        leasingEntity.setAdvancedPayment(leasingCompDto.getAdvancedPayment());
        leasingEntity.setCreatedBy(leasingCompDto.getCreatedBy());
        leasingEntity.setCreatedDate(leasingCompDto.getCreationDate());
        leasingEntity.setUpdatedBy(leasingCompDto.getUpdatedBy());
        leasingEntity.setUpdatedDate(leasingCompDto.getUpdatedDate());
        leasingEntity.setLgIpMac(leasingCompDto.getLgIpMac());
        leasingEntity.setLgIpMacUpd(leasingCompDto.getLgIpMacUpd());
        leasingEntity.setAssetId(astInfo);
        return leasingEntity;

    }
    
    public static AssetLeasingCompanyDTO mapToDTO(AssetLeasingCompany leasingEntity) {
        AssetLeasingCompanyDTO leasingCompDto = new AssetLeasingCompanyDTO();
        AssetInformation astInfo = leasingEntity.getAssetId();
        leasingCompDto.setContractAgreementNo(leasingEntity.getContractAgreementNo());
        leasingCompDto.setAgreementDate(leasingEntity.getAgreementDate());
        leasingCompDto.setNoticeDate(leasingEntity.getNoticeDate());
        leasingCompDto.setLeaseStartDate(leasingEntity.getLeaseStartDate());
        leasingCompDto.setLeaseEndDate(leasingEntity.getLeaseEndDate());
        leasingCompDto.setLeaseType(leasingEntity.getLeaseType());
        leasingCompDto.setPurchasePrice(leasingEntity.getPurchasePrice());
        leasingCompDto.setNoOfInstallment(leasingEntity.getNoOfInstallment());
        leasingCompDto.setPaymentFrequency(leasingEntity.getPaymentFrequency());
        leasingCompDto.setAdvancedPayment(leasingEntity.getAdvancedPayment());
        leasingCompDto.setCreatedBy(leasingEntity.getCreatedBy());
        leasingCompDto.setCreationDate(leasingEntity.getCreatedDate());
        leasingCompDto.setUpdatedBy(leasingEntity.getUpdatedBy());
        leasingCompDto.setUpdatedDate(leasingEntity.getUpdatedDate());
        leasingCompDto.setLgIpMac(leasingEntity.getLgIpMac());
        leasingCompDto.setLgIpMacUpd(leasingEntity.getLgIpMacUpd());
        leasingCompDto.setAssetId(astInfo.getAssetId());
        leasingCompDto.setAssetLeasingId(leasingEntity.getAssetLeasingID());
        return leasingCompDto;

    }
    
    public static AssetLeasingCompanyDTO mapToDTORev(AssetLeasingCompanyRev leasingEntity) {
        AssetLeasingCompanyDTO leasingCompDto = new AssetLeasingCompanyDTO();
        AssetInformation astInfo = leasingEntity.getAssetId();
        leasingCompDto.setContractAgreementNo(leasingEntity.getContractAgreementNo());
        leasingCompDto.setAgreementDate(leasingEntity.getAgreementDate());
        leasingCompDto.setNoticeDate(leasingEntity.getNoticeDate());
        leasingCompDto.setLeaseStartDate(leasingEntity.getLeaseStartDate());
        leasingCompDto.setLeaseEndDate(leasingEntity.getLeaseEndDate());
        leasingCompDto.setLeaseType(leasingEntity.getLeaseType());
        leasingCompDto.setPurchasePrice(leasingEntity.getPurchasePrice());
        leasingCompDto.setNoOfInstallment(leasingEntity.getNoOfInstallment());
        leasingCompDto.setPaymentFrequency(leasingEntity.getPaymentFrequency());
        leasingCompDto.setAdvancedPayment(leasingEntity.getAdvancedPayment());
        leasingCompDto.setCreatedBy(leasingEntity.getCreatedBy());
        leasingCompDto.setCreationDate(leasingEntity.getCreatedDate());
        leasingCompDto.setUpdatedBy(leasingEntity.getUpdatedBy());
        leasingCompDto.setUpdatedDate(leasingEntity.getUpdatedDate());
        leasingCompDto.setLgIpMac(leasingEntity.getLgIpMac());
        leasingCompDto.setLgIpMacUpd(leasingEntity.getLgIpMacUpd());
        leasingCompDto.setAssetId(astInfo.getAssetId());
        leasingCompDto.setAssetLeasingId(leasingEntity.getAssetLeasingID());
        return leasingCompDto;

    }

    public static AssetLeasingCompanyDTO resetLease(AssetLeasingCompanyDTO leaseDTO) {
    	leaseDTO.setContractAgreementNo(null);
    	leaseDTO.setAgreementDate(null);
    	leaseDTO.setNoticeDate(null);
    	leaseDTO.setLeaseStartDate(null);
    	leaseDTO.setLeaseEndDate(null);
    	leaseDTO.setLeaseType(null);
    	leaseDTO.setPurchasePrice(null);
    	leaseDTO.setNoOfInstallment(null);
    	leaseDTO.setPaymentFrequency(null);
    	leaseDTO.setAdvancedPayment(null);
    	leaseDTO.setCreatedBy(null);
    	leaseDTO.setCreationDate(null);
    	leaseDTO.setUpdatedBy(null);
    	leaseDTO.setUpdatedDate(null);
    	leaseDTO.setLgIpMac(null);
    	leaseDTO.setLgIpMacUpd(null);
		return leaseDTO;
	}
}
