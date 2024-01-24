/**
 * 
 */
package com.abm.mainet.socialsecurity.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeDetails;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySubSchemeDetails;
import com.abm.mainet.socialsecurity.ui.dto.PensionEligibilityCriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.PensionSchemeMasterDto;
import com.abm.mainet.socialsecurity.ui.dto.PensionSourceOfFundDto;
import com.abm.mainet.socialsecurity.ui.dto.SubSchemeDetailsDto;

/**
 * @author satish.rathore
 *
 */
public class PensionDetailsMapper {

    public static SocialSecuritySchemeMaster DtoToEntity(PensionSchemeMasterDto dto) {

        SocialSecuritySchemeMaster sSmasterEntity = new SocialSecuritySchemeMaster();
        sSmasterEntity.setCreatedBy(dto.getCreatedBy());
        sSmasterEntity.setCreatedDate(dto.getCreatedDate());
        if (dto.getIsSchmeActive() == null || dto.getIsSchmeActive() == " ") {
            sSmasterEntity.setIsSchmeActive("Y");
        } else {
            sSmasterEntity.setIsSchmeActive(dto.getIsSchmeActive());
        }
      //Defect #138181 handling NPE
        if (dto.getFamilyDetReq() != null && dto.getFamilyDetReq().equals("true")) {
            sSmasterEntity.setFamilyDetReq("Y");
        } else {
        	sSmasterEntity.setFamilyDetReq("N");
        }
        //#140118
        if (dto.getLifeCertificateReq() != null && dto.getLifeCertificateReq().equals("true")) {
            sSmasterEntity.setLifeCertificateReq("Y");
        } else {
        	sSmasterEntity.setLifeCertificateReq("N");
        }
        sSmasterEntity.setLgIpMac(dto.getLgIpMac());
        sSmasterEntity.setObjOfScheme(dto.getObjOfschme());
        sSmasterEntity.setLgIpMacUpd(dto.getLgIpMacUpd());
        sSmasterEntity.setOrgId(dto.getOrgId());
        sSmasterEntity.setSchemeNameId(dto.getServiceId());
        sSmasterEntity.setUpdatedBy(dto.getUpdatedBy());
        sSmasterEntity.setUpdatedDate(dto.getUpdatedDate());
        sSmasterEntity.setSchemeMstId(dto.getSchmeMsId());
        //Defect #138181
        sSmasterEntity.setResolutionDate(dto.getResolutionDate());
        sSmasterEntity.setResolutionNo(dto.getResolutionNo());

        List<SocialSecuritySchemeDetails> socialDetailEntityList = new ArrayList<>();
        socialDetailEntityList = dto.getPensionSourceFundList().stream().map(k -> {
            SocialSecuritySchemeDetails socialDetailEntity = new SocialSecuritySchemeDetails();
            socialDetailEntity.setCreatedBy(dto.getCreatedBy());
            socialDetailEntity.setCreatedDate(dto.getCreatedDate());
            socialDetailEntity.setLgIpMac(dto.getLgIpMac());
            socialDetailEntity.setLgIpMacUpd(dto.getLgIpMacUpd());
            socialDetailEntity.setOrgId(dto.getOrgId());
            socialDetailEntity.setSharingAmt(k.getSharingAmt());
            socialDetailEntity.setSponserBy(k.getSponcerId().toString());
            socialDetailEntity.setBdYearId(k.getBdYearId());

            if (dto.getIsSchmeActive() == null || dto.getIsSchmeActive() == " ") {
                socialDetailEntity.setIsschemeDetActive("Y");
            } else {
                socialDetailEntity.setIsschemeDetActive(dto.getIsSchmeActive());
            }
            socialDetailEntity.setSchemeDtlId(k.getPenFundId());
            socialDetailEntity.setSocialSecuritySchemeMaster(sSmasterEntity);
            return socialDetailEntity;
        }).collect(Collectors.toList());
        sSmasterEntity.setSocialSecuritySchemeDetList(socialDetailEntityList);

        List<SocialSecuritySchemeEligibilty> eligibiltyList = new ArrayList<>();
        for (List<PensionEligibilityCriteriaDto> list : dto.getSaveDataList()) {
            for (PensionEligibilityCriteriaDto list2 : list) {
                SocialSecuritySchemeEligibilty scoEligibiltyEntity = new SocialSecuritySchemeEligibilty();
                scoEligibiltyEntity.setAmount(list2.getAmt());
                scoEligibiltyEntity.setCreatedBy(dto.getCreatedBy());
                scoEligibiltyEntity.setCreatedDate(dto.getCreatedDate());
                scoEligibiltyEntity.setCriteriaId(list2.getCriteriaId());
                scoEligibiltyEntity.setFactorApplicableId(list2.getFactorApplicableId());
                scoEligibiltyEntity.setGroupID(list2.getBatchId());
                scoEligibiltyEntity.setLgIpMac(dto.getLgIpMac());
                scoEligibiltyEntity.setLgIpMacUpd(dto.getLgIpMacUpd());
                scoEligibiltyEntity.setOrgId(dto.getOrgId());
                scoEligibiltyEntity.setPaySchedule(list2.getPaySchedule());
                scoEligibiltyEntity.setRangeFrom(list2.getRangeFrom());
                scoEligibiltyEntity.setRangeTo(list2.getRangeTo());
                scoEligibiltyEntity.setSosecuritySchmMasEligibility(sSmasterEntity);
                scoEligibiltyEntity.setUpdatedBy(dto.getUpdatedBy());
                scoEligibiltyEntity.setUpdatedDate(dto.getUpdatedDate());
                eligibiltyList.add(scoEligibiltyEntity);
            }
        }
        sSmasterEntity.setSocialSecuritySchemeEligibilty(eligibiltyList);
        
		if (!dto.getSubSchemeDetailsDtoList().isEmpty()) {
			List<SocialSecuritySubSchemeDetails> SocialSecuritySubSchemeDetList = new ArrayList<>();
			SocialSecuritySubSchemeDetList = dto.getSubSchemeDetailsDtoList().stream().map(k -> {
				SocialSecuritySubSchemeDetails socialSubDetailEntity = new SocialSecuritySubSchemeDetails();
				socialSubDetailEntity.setCreatedBy(dto.getCreatedBy());
				socialSubDetailEntity.setCreatedDate(dto.getCreatedDate());
				socialSubDetailEntity.setLgIpMac(dto.getLgIpMac());
				socialSubDetailEntity.setLgIpMacUpd(dto.getLgIpMacUpd());
				socialSubDetailEntity.setOrgId(dto.getOrgId());
				socialSubDetailEntity.setNoBeneficiary(k.getNoBeneficiary());
				socialSubDetailEntity.setAmount(k.getAmount());
				socialSubDetailEntity.setTotalAmount(k.getTotalAmount());
				socialSubDetailEntity.setSubSchemeName(k.getSubSchemeName());

				if (dto.getIsSchmeActive() == null || dto.getIsSchmeActive() == " ") {
					socialSubDetailEntity.setIsschemeDetActive("Y");
				} else {
					socialSubDetailEntity.setIsschemeDetActive(dto.getIsSchmeActive());
				}
				socialSubDetailEntity.setSocialSecuritySchemeMaster(sSmasterEntity);
				return socialSubDetailEntity;
			}).collect(Collectors.toList());
			sSmasterEntity.setSocialSecuritySubSchemeDetails(SocialSecuritySubSchemeDetList);
		}
       
        return sSmasterEntity;

    }

    public static PensionSchemeMasterDto entityToDto(SocialSecuritySchemeMaster entity) {

        PensionSchemeMasterDto dto = new PensionSchemeMasterDto();
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setIsSchmeActive(entity.getIsSchmeActive());
        dto.setLgIpMac(entity.getLgIpMac());
        dto.setObjOfschme(entity.getObjOfScheme());
        dto.setLgIpMacUpd(entity.getLgIpMacUpd());
        dto.setOrgId(entity.getOrgId());
        dto.setServiceId(entity.getSchemeNameId());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setSchmeMsId(entity.getSchemeMstId());
       //Defect #138181
        dto.setResolutionDate(entity.getResolutionDate());
        dto.setResolutionNo(entity.getResolutionNo());
        dto.setFamilyDetReq(entity.getFamilyDetReq());
        dto.setLifeCertificateReq(entity.getLifeCertificateReq());

        List<PensionSourceOfFundDto> pensionSourceFundList = new ArrayList<>();
        pensionSourceFundList = entity.getSocialSecuritySchemeDetList().stream().map(k -> {
            PensionSourceOfFundDto funddto = new PensionSourceOfFundDto();
            funddto.setSharingAmt(k.getSharingAmt());
            funddto.setSponcerId(Long.valueOf(k.getSponserBy()));
            funddto.setPenFundId(k.getSchemeDtlId());
            funddto.setBdYearId(k.getBdYearId());
            return funddto;
        }).collect(Collectors.toList());
        dto.setPensionSourceFundList(pensionSourceFundList);
        List<PensionEligibilityCriteriaDto> eligibiltyL = new ArrayList<>();
        for (SocialSecuritySchemeEligibilty list : entity.getSocialSecuritySchemeEligibilty()) {
            PensionEligibilityCriteriaDto scoEligibiltydto = new PensionEligibilityCriteriaDto();
            scoEligibiltydto.setAmt(list.getAmount());
            scoEligibiltydto.setCriteriaId(list.getCriteriaId());
            scoEligibiltydto.setFactorApplicableId(list.getFactorApplicableId());
            scoEligibiltydto.setBatchId(list.getGroupID());
            scoEligibiltydto.setPaySchedule(list.getPaySchedule());
            scoEligibiltydto.setRangeFrom(list.getRangeFrom());
            scoEligibiltydto.setRangeTo(list.getRangeTo());
            scoEligibiltydto.setCheckBox(true);
            scoEligibiltydto.setSchmeEligibiltyId(list.getSchmeEligibiltyId());
            eligibiltyL.add(scoEligibiltydto);
        }
        dto.setPensioneligibilityList(eligibiltyL);
		if (!entity.getSocialSecuritySubSchemeDetails().isEmpty()) {
			List<SubSchemeDetailsDto> subSchemeDetailsDto = new ArrayList<>();
			for (SocialSecuritySubSchemeDetails list : entity.getSocialSecuritySubSchemeDetails()) {
				SubSchemeDetailsDto scoEligibiltydto = new SubSchemeDetailsDto();
				scoEligibiltydto.setNoBeneficiary(list.getNoBeneficiary());
				scoEligibiltydto.setAmount(list.getAmount());
				scoEligibiltydto.setTotalAmount(list.getTotalAmount());
				scoEligibiltydto.setSubSchemeName(list.getSubSchemeName());
				subSchemeDetailsDto.add(scoEligibiltydto);
			}
			dto.setSubSchemeDetailsDtoList(subSchemeDetailsDto);
		}
        return dto;
    }

}
