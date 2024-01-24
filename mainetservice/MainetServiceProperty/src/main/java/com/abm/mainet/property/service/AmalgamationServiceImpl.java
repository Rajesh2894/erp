package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class AmalgamationServiceImpl implements AmalgamationService {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private PropertyMainBillRepository mainBillRepository;

    @Override
    @Transactional
    public void saveAmulgamatedProperty(
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, List<Long> finYearList, Long deptId,
            List<ProvisionalAssesmentMstDto> childList) {
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(provisionalAssesmentMstDto.getOrgId());
        setRequestApplicantDetails(reqDto, provisionalAssesmentMstDto, organisation.getOrgid(), deptId, MainetConstants.ENGLISH,
                provisionalAssesmentMstDto.getCreatedBy(),
                provisionalAssesmentMstDto.getSmServiceId());
        final Long applicationNo = applicationService.createApplication(reqDto);
        provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
        iProvisionalAssesmentMstService.saveProvisionalAssessment(provisionalAssesmentMstDto,
                organisation.getOrgid(), provisionalAssesmentMstDto.getCreatedBy(), finYearList,
                applicationNo);
        iProvisionalAssesmentMstService.saveProvisionalAssessmentListForAmalgamation(childList, organisation.getOrgid(),
                provisionalAssesmentMstDto.getCreatedBy(), provisionalAssesmentMstDto.getLgIpMac(),
                provisionalAssesmentMstDto.getAssNo(),provisionalAssesmentMstDto.getSmServiceId());
        /*
         * childList.forEach(assMst -> { List<ProvisionalAssesmentMstEntity> childAssMas = iProvisionalAssesmentMstService
         * .getPropDetailFromProvAssByPropNo(organisation.getOrgid(), assMst.getAssNo(), MainetConstants.STATUS.ACTIVE);
         * updateInactiveAmalgamatedProp(childAssMas, provisionalAssesmentMstDto); });
         */
        if ((provisionalAssesmentMstDto.getDocs() != null) && !provisionalAssesmentMstDto.getDocs().isEmpty()) {
            reqDto.setApplicationId(applicationNo);
            fileUploadService.doFileUpload(provisionalAssesmentMstDto.getDocs(), reqDto);
        }
        sendSmsAndMail(provisionalAssesmentMstDto, organisation, Utility.getDefaultLanguageId(organisation),
                provisionalAssesmentMstDto.getCreatedBy());
    }

    private void updateInactiveAmalgamatedProp(
            List<ProvisionalAssesmentMstEntity> childAssMas, ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        childAssMas.forEach(mst -> {
            mst.setAssActive(MainetConstants.STATUS.INACTIVE);
            mst.setUpdatedBy(provisionalAssesmentMstDto.getCreatedBy());
            mst.setUpdatedDate(provisionalAssesmentMstDto.getCreatedDate());
            mst.setLgIpMacUpd(provisionalAssesmentMstDto.getLgIpMac());
            mst.setParentProp(provisionalAssesmentMstDto.getAssNo());
        });
        provisionalAssesmentMstRepository.save(childAssMas);
    }

    private void sendSmsAndMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId,
            Long userId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(provAsseMstDto.getAssEmail());
        dto.setUserId(userId);
        dto.setMobnumber(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
        ServiceMaster service = serviceMasterRepository.getServiceMaster(provAsseMstDto.getSmServiceId(),
                provAsseMstDto.getOrgId());
        if (langId == MainetConstants.MARATHI) {
            dto.setServName(service.getSmServiceNameMar());
        } else {
            dto.setServName(service.getSmServiceName());
        }
        dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        dto.setPropertyNo(provAsseMstDto.getAssNo());
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.URLBasedOnShortCode.valueOf(service.getSmShortdesc()).getUrl(),
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, langId);
    }

    private void setRequestApplicantDetails(final RequestDTO reqDto, ProvisionalAssesmentMstDto asseMstDto, Long orgId,
            Long deptId, int langId, Long empId, Long serviceId) {
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        reqDto.setfName(ownDtlDto.getAssoOwnerName());
        reqDto.setMobileNo(ownDtlDto.getAssoMobileno());
        reqDto.setEmail(asseMstDto.getAssEmail());
        reqDto.setPincodeNo(asseMstDto.getAssPincode());
        reqDto.setDeptId(deptId);
        if (reqDto.getGender() != null) {
            reqDto.setGender(ownDtlDto.getGenderId().toString());
        }
        reqDto.setPayStatus(MainetConstants.PAYMENT.FREE);
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(empId);
    }

    @Override
    public List<TbBillMas> fetchNotPaidBillsByPropNo(List<String> assNo, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<TbBillMas>();
        List<MainBillMasEntity> masEntityList = mainBillRepository.fetchNotPaidBillsByPropNo(assNo, orgId);
        for (MainBillMasEntity masEntity : masEntityList) {
            TbBillMas billMas = new TbBillMas();
            BeanUtils.copyProperties(masEntity, billMas);
            billMasList.add(billMas);
        }
        return billMasList;

    }

}
