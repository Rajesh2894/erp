/**
 * 
 */
package com.abm.mainet.socialsecurity.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.abm.mainet.socialsecurity.domain.SchemeApplicantFamilyDetEntity;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppFamilyDetailsDto;


/**
 * @author satish.rathore
 *
 */
public class ApplicationFormDetailsMapper {

	public static SocialSecurityApplicationForm dtoToEntity(ApplicationFormDto dto) {

		SocialSecurityApplicationForm entity = new SocialSecurityApplicationForm();

		entity.setAccountNumber(dto.getAccountNumber());
		entity.setAgeason(dto.getAgeason());
		entity.setAnnualIncome(dto.getAnnualIncome());
		entity.setAnnualIncomeoffam(dto.getAnnualIncomeoffam());
		entity.setApplicantAdress(dto.getApplicantAdress());
		entity.setApplicationDob(dto.getApplicantDob());
		entity.setApplicationId(dto.getApplicationId());
		entity.setBankNameId(dto.getBankNameId());
		entity.setBplfamily(dto.getBplfamily());
		entity.setBplid(dto.getIsBplApplicable());
		entity.setBplinspectyr(dto.getBplinspectyr());

		entity.setCategoryId(dto.getCategoryId());
		entity.setClasss(dto.getClasss());
		entity.setContactNumber(dto.getContactNumber());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setCreatedDate(dto.getCreatedDate());
		entity.setDetailsoffamIncomeSource(dto.getDetailsoffamIncomeSource());
		entity.setEducationId(dto.getEducationId());
		entity.setGenderId(dto.getGenderId());

		entity.setLgIpMac(dto.getLgIpMac());
		entity.setLgIpMacUpd(dto.getLgIpMacUpd());
		entity.setMaritalStatusId(dto.getMaritalStatusId());
		entity.setMobNum(dto.getMobNum());
		entity.setNameofApplicant(dto.getNameofApplicant());
		entity.setNameofFather(dto.getNameofFather());
		entity.setNameofMother(dto.getNameofMother());
		entity.setOrgId(dto.getOrgId());
		entity.setPercenrofDis(dto.getPercenrofDis());
		entity.setPinCode(dto.getPinCode());
		entity.setSelectSchemeName(dto.getSelectSchemeName());
		entity.setTypeofDisId(dto.getTypeofDisId());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedDate(dto.getUpdatedDate());
		entity.setSubSchemeName(dto.getSubSchemeName());
		if (dto.getMasterAppId() != null) {
			entity.setApmApplicationId(dto.getMasterAppId().toString());
		}
//D#138191
		entity.setSwdward1(dto.getSwdward1());
		entity.setSwdward2(dto.getSwdward2());
		entity.setSwdward3(dto.getSwdward3());
		entity.setSwdward4(dto.getSwdward4());
		entity.setSwdward5(dto.getSwdward5());
		entity.setDisbilityCertNo(dto.getDisbilityCertNo());;
		entity.setEmail(dto.getEmail());

		entity.setLastPaymentDate(dto.getLastPaymentDate());
		entity.setReferenceno(dto.getReferenceNo());
		entity.setBeneficiarynumber(dto.getBeneficiaryno());
		entity.setLastDateofLifeCerti(dto.getLastDateofLifeCerti());
		entity.setValidtoDate(dto.getValidtoDate());
		entity.setPensionCancelReason(dto.getPensionCancelReason());
		entity.setPensionCancelDate(dto.getPensionCancelDate());
		entity.setAadharCard(dto.getAadharCard());
		if(dto.getStatus()!=null)
		entity.setStatus(dto.getStatus());
		// D#138191
		List<SchemeApplicantFamilyDetEntity> famList = new ArrayList<SchemeApplicantFamilyDetEntity>();
		if (CollectionUtils.isNotEmpty(dto.getOwnerFamilydetailDTO())) {
			for (SchemeAppFamilyDetailsDto famDetail : dto.getOwnerFamilydetailDTO()) {
				SchemeApplicantFamilyDetEntity famEntity = new SchemeApplicantFamilyDetEntity();
				BeanUtils.copyProperties(famDetail, famEntity);
				famList.add(famEntity);
			}
			entity.setOwnerFamilyDetails(famList);
		}
		return entity;

	}

	public static ApplicationFormDto entityToDto(SocialSecurityApplicationForm entity) {

		ApplicationFormDto dto = new ApplicationFormDto();
		if (entity.getAccountNumber() != null) {
			dto.setAccountNumber(entity.getAccountNumber());
		}
		dto.setAgeason(entity.getAgeason());
		dto.setAnnualIncome(entity.getAnnualIncome());
		dto.setAnnualIncomeoffam(entity.getAnnualIncomeoffam());
		dto.setApplicantAdress(entity.getApplicantAdress());
		dto.setApplicantDob(entity.getApplicationDob());
		dto.setApplicationId(entity.getApplicationId());
		dto.setBankNameId(entity.getBankNameId());
		dto.setBplfamily(entity.getBplfamily());
		dto.setIsBplApplicable(entity.getBplid());
		dto.setBplinspectyr(entity.getBplinspectyr());

		dto.setCategoryId(entity.getCategoryId());
		dto.setClasss(entity.getClasss());
		dto.setContactNumber(entity.getContactNumber());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setDetailsoffamIncomeSource(entity.getDetailsoffamIncomeSource());
		dto.setEducationId(entity.getEducationId());
		dto.setGenderId(entity.getGenderId());
		dto.setLgIpMac(entity.getLgIpMac());
		dto.setLgIpMacUpd(entity.getLgIpMacUpd());
		dto.setMaritalStatusId(entity.getMaritalStatusId());
		dto.setMobNum(entity.getMobNum());
		dto.setNameofApplicant(entity.getNameofApplicant());
		dto.setNameofFather(entity.getNameofFather());
		dto.setNameofMother(entity.getNameofMother());
		dto.setOrgId(entity.getOrgId());
		dto.setPercenrofDis(entity.getPercenrofDis());
		dto.setPinCode(entity.getPinCode());
		dto.setSelectSchemeName(entity.getSelectSchemeName());
		dto.setTypeofDisId(entity.getTypeofDisId());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setUpdatedDate(entity.getUpdatedDate());
		if (entity.getApmApplicationId() != null) {
			dto.setMasterAppId(Long.valueOf(entity.getApmApplicationId()));
		}
		dto.setLastPaymentDate(entity.getLastPaymentDate());
		dto.setReferenceNo(entity.getReferenceno());
		dto.setDataLegacyFlag(entity.getDatalegacyflag());
		dto.setBeneficiaryno(entity.getBeneficiarynumber());
		dto.setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
		dto.setValidtoDate(entity.getValidtoDate());
		dto.setPensionCancelReason(entity.getPensionCancelReason());
		dto.setPensionCancelDate(entity.getPensionCancelDate());
		dto.setAadharCard(entity.getAadharCard());
		dto.setGridId(entity.getBranchname());
		dto.setSwdward1(entity.getSwdward1());
		if(entity.getSubSchemeName()!=null)
		dto.setSubSchemeName(entity.getSubSchemeName());
		if(entity.getStatus()!=null)
		dto.setStatus(entity.getStatus());
		if(entity.getEmail()!=null)
		dto.setEmail(entity.getEmail());
		return dto;

	}
//D#13819
	public static List<SchemeApplicantFamilyDetEntity> familyDetailsDtotoEntity(ApplicationFormDto dto,
			SocialSecurityApplicationForm schemeEntity) {

		List<SchemeApplicantFamilyDetEntity> entityList = new ArrayList<SchemeApplicantFamilyDetEntity>();
		if (CollectionUtils.isNotEmpty(dto.getOwnerFamilydetailDTO())) {
			for (SchemeAppFamilyDetailsDto dtos : dto.getOwnerFamilydetailDTO()) {
				SchemeApplicantFamilyDetEntity entity = new SchemeApplicantFamilyDetEntity();
				entity.setAge(dtos.getAge());
				entity.setContactNo(dtos.getContactNo());
				entity.setDob(dtos.getDob());
				entity.setEducation(dtos.getEducation());
				entity.setFamMemName(dtos.getFamMemName());
				entity.setGender(dtos.getGender());
				entity.setLgIpMac(dto.getLgIpMac());
				entity.setCreatedBy(dto.getCreatedBy());
				entity.setCreatedDate(dto.getCreatedDate());
				entity.setLgIpMacUpd(dto.getLgIpMacUpd());
				entity.setRelation(dtos.getRelation());
				entity.setOccupation(dtos.getOccupation());
				entity.setApplicationId(schemeEntity);
				entity.setOrgId(dto.getOrgId());
				if(dtos.getFamMemId()!= null)
				entity.setFamMemId(dtos.getFamMemId());

				entityList.add(entity);
			}
		}
		return entityList;
	}

}
