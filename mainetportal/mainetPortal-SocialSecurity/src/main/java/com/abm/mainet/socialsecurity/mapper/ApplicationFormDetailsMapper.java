/**
 * 
 */
package com.abm.mainet.socialsecurity.mapper;

import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;

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
       // entity.setApplicationDob(dto.getApplicationDob());
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
        if(dto.getMasterAppId()!=null)
        {
        entity.setApmApplicationId(dto.getMasterAppId().toString());
        }
        entity.setLastPaymentDate(dto.getLastPaymentDate());
        entity.setReferenceno(dto.getReferenceNo());
        entity.setBeneficiarynumber(dto.getBeneficiaryno());
        entity.setLastDateofLifeCerti(dto.getLastDateofLifeCerti());
        entity.setValidtoDate(dto.getValidtoDate());
        return entity;

    }

    public static ApplicationFormDto entityToDto(SocialSecurityApplicationForm entity) {

        ApplicationFormDto dto = new ApplicationFormDto();

        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAgeason(entity.getAgeason());
        dto.setAnnualIncome(entity.getAnnualIncome());
        dto.setAnnualIncomeoffam(entity.getAnnualIncomeoffam());
        dto.setApplicantAdress(entity.getApplicantAdress());
        //dto.setApplicantDob(entity.getApplicationDob());
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
        dto.setMasterAppId(Long.valueOf(entity.getApmApplicationId()));
        
        dto.setLastPaymentDate(entity.getLastPaymentDate());
        dto.setReferenceNo(entity.getReferenceno());
        dto.setDataLegacyFlag(entity.getDatalegacyflag());
        dto.setBeneficiaryno(entity.getBeneficiarynumber());
        dto.setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
        dto.setValidtoDate(entity.getValidtoDate());
        
        return dto;

    }

}
