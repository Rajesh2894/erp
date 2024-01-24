package com.abm.mainet.water.ui.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.domain.TbPlumberExperience;
import com.abm.mainet.water.domain.TbPlumberQualification;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.service.PlumberLicenseService;

/**
 * @author Arun.Chavda
 *
 */

@Component
@Scope("session")
public class PlumberLicenseScrutinyModel extends AbstractFormModel {

    private static final long serialVersionUID = -6509117877250473347L;
    @Autowired
    private ICFCApplicationMasterService icfcApplicationMasterService;
    @Resource
    private PlumberLicenseService plumberLicenseService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    private List<CFCAttachment> documentList = null;

    private CFCApplicationAddressEntity cfcAddressEntity;
    private TbCfcApplicationMstEntity cfcEntity;
    private boolean scrutinyApplicable;
    private ApplicantDetailDTO applicantDetailDto;
    private List<PlumberQualificationDTO> plumberQualificationDTOList = new ArrayList<>();
    private List<PlumberExperienceDTO> plumberExperienceDTOList = new ArrayList<>();
    private PlumberMaster plumberMaster;
    private Long applicationId;
    private Long serviceId;
    private Long plumberId;
    private String fileDownLoadPath;
    private String interviewRemark;
    private String interviewDateTime;
    private Date interviewDateTimeD;
    private String plumQualDeletedRow;
    private String plumExpDeletedRow;
    private String totalExp;

    /**
     * This method is used for view Application Details on Scrutiny level
     */
    @Override
    public void populateApplicationData(final long applicationId) {
        if (null != plumQualDeletedRow) {
            plumQualDeletedRow.isEmpty();
        }
        if (null != plumExpDeletedRow) {
            plumExpDeletedRow.isEmpty();
        }
        setApplicationId(applicationId);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        applicantDetailDto = new ApplicantDetailDTO();
        cfcEntity = icfcApplicationMasterService.getCFCApplicationByApplicationId(applicationId, orgId);
        if (null != cfcEntity) {
            setServiceId(cfcEntity.getTbServicesMst().getSmServiceId());
            applicantDetailDto.setGender(Utility.getGenderId(cfcEntity.getApmSex()));
        }
        PlumberRenewalRegisterMaster plumberLicenceRenewalDetailsByAppId = plumberLicenseService.
        		getPlumberLicenceRenewalDetailsByAppId(applicationId, orgId);
        if(plumberLicenceRenewalDetailsByAppId!=null) {
        	PlumberMaster plumberDetailsByPlumId = plumberLicenseService.getPlumberDetailsByPlumId(
        			plumberLicenceRenewalDetailsByAppId.getPlum_id(), orgId);
        	setPlumberMaster(plumberDetailsByPlumId);
            if (null != plumberMaster) {
                plumberId = plumberMaster.getPlumId();
                setInterviewDateTimeD(plumberMaster.getPlumInterviewDate());
                setInterviewRemark(plumberMaster.getPlumInterviewRemark());
                final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                        + "SHOW_DOCS";
                setFileDownLoadPath(Utility.downloadedFileUrl(
                        plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR + plumberMaster.getPlumImage(),
                        outputPath, getFileNetClient()));
                setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
            }

            ApplicantDetailDTO dto = getApplicantDetailDto();
            if (dto == null) {
                dto = new ApplicantDetailDTO();
            }
            applicantDetailDto = initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity),
                    cfcService.getApplicantsDetails(applicationId));

            final List<PlumberQualificationDTO> plumberQualificationDTOs = plumberLicenseService
                    .getPlumberQualificationDetails(plumberId, orgId);
            setPlumberQualificationDTOList(plumberQualificationDTOs);
            final List<PlumberExperienceDTO> plumberExperienceDTOs = plumberLicenseService.getPlumberExperienceDetails(plumberId,
                    orgId);
            setPlumberExperienceDTOList(plumberExperienceDTOs);
            BigInteger sumYear = BigInteger.ZERO;
            BigInteger sumMonth = BigInteger.ZERO;
            BigInteger totalMonth = BigInteger.ZERO;
            if ((plumberExperienceDTOs != null) && !plumberExperienceDTOs.isEmpty()) {
                for (final PlumberExperienceDTO plumberExperienceDTO : plumberExperienceDTOs) {
                    final Long year = plumberExperienceDTO.getPlumExpYear();
                    final Long month = plumberExperienceDTO.getPlumExpMonth();
                    if (year != null) {
                        sumYear = sumYear.add(BigInteger.valueOf(year));
                    }
                    if (month != null) {
                        sumMonth = sumMonth.add(BigInteger.valueOf(month));
                    }
                }
            }
            final BigInteger yearToMonth = sumYear.multiply(BigInteger.valueOf(12));
            totalMonth = yearToMonth.add(sumMonth);
            final BigInteger divide = totalMonth.divide(BigInteger.valueOf(12));
            final BigInteger remainder = totalMonth.mod(BigInteger.valueOf(12));
            final String exp = divide.toString() + MainetConstants.operator.DOT + remainder.toString();
            setTotalExp(exp);
            final ServiceMaster service = cfcEntity.getTbServicesMst();
            if (MainetConstants.MENU.Y.equals(service.getSmScrutinyApplicableFlag())) {
                setScrutinyApplicable(true);
            }
        }else {
        	setPlumberMaster(plumberLicenseService.getPlumberDetailsByAppId(applicationId,orgId));
                if (null != plumberMaster) {
                    plumberId = plumberMaster.getPlumId();
                    setInterviewDateTimeD(plumberMaster.getPlumInterviewDate());
                    setInterviewRemark(plumberMaster.getPlumInterviewRemark());
                    final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                            + "SHOW_DOCS";
                    setFileDownLoadPath(Utility.downloadedFileUrl(
                            plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR + plumberMaster.getPlumImage(),
                            outputPath, getFileNetClient()));
                    setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
                }

                ApplicantDetailDTO dto = getApplicantDetailDto();
                if (dto == null) {
                    dto = new ApplicantDetailDTO();
                }
                applicantDetailDto = initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity),
                        cfcService.getApplicantsDetails(applicationId));

                final List<PlumberQualificationDTO> plumberQualificationDTOs = plumberLicenseService
                        .getPlumberQualificationDetails(plumberId, orgId);
                setPlumberQualificationDTOList(plumberQualificationDTOs);
                final List<PlumberExperienceDTO> plumberExperienceDTOs = plumberLicenseService.getPlumberExperienceDetails(plumberId,
                        orgId);
                setPlumberExperienceDTOList(plumberExperienceDTOs);
                BigInteger sumYear = BigInteger.ZERO;
                BigInteger sumMonth = BigInteger.ZERO;
                BigInteger totalMonth = BigInteger.ZERO;
                if ((plumberExperienceDTOs != null) && !plumberExperienceDTOs.isEmpty()) {
                    for (final PlumberExperienceDTO plumberExperienceDTO : plumberExperienceDTOs) {
                        final Long year = plumberExperienceDTO.getPlumExpYear();
                        final Long month = plumberExperienceDTO.getPlumExpMonth();
                        if (year != null) {
                            sumYear = sumYear.add(BigInteger.valueOf(year));
                        }
                        if (month != null) {
                            sumMonth = sumMonth.add(BigInteger.valueOf(month));
                        }
                    }
                }
                final BigInteger yearToMonth = sumYear.multiply(BigInteger.valueOf(12));
                totalMonth = yearToMonth.add(sumMonth);
                final BigInteger divide = totalMonth.divide(BigInteger.valueOf(12));
                final BigInteger remainder = totalMonth.mod(BigInteger.valueOf(12));
                final String exp = divide.toString() + MainetConstants.operator.DOT + remainder.toString();
                setTotalExp(exp);
                final ServiceMaster service = cfcEntity.getTbServicesMst();
                if (MainetConstants.MENU.Y.equals(service.getSmScrutinyApplicableFlag())) {
                    setScrutinyApplicable(true);
                }
            
        }
        
    }

    /**
     * This method is used for updated Application details on Scrutiny level
     */
    @Override
    public boolean saveForm() {
        final boolean result = validateFormData(getInterviewRemark(), getInterviewDateTime());
        if (result) {
            return false;
        }
        final PlumberMaster master = getPlumberMaster();
        final List<PlumberQualificationDTO> qualificationDTOs = getPlumberQualificationDTOList();
        final List<PlumberExperienceDTO> experienceDTOs = getPlumberExperienceDTOList();
        master.setPlumInterviewRemark(getInterviewRemark());
        master.setPlumInterviewDate(Utility.stringToDate(getInterviewDateTime(), "MM/dd/yyyy HH:mm"));
        TbPlumberQualification plumberQualification = null;
        final List<TbPlumberQualification> plumberQualificationsList = new ArrayList<>();
        final String[] qualRowId = getPlumQualDeletedRow().split(MainetConstants.operator.COMMA);
        final String[] expRowId = getPlumExpDeletedRow().split(MainetConstants.operator.COMMA);
        plumberLicenseService.updatedQualAndExpIsDeletedFlag(qualRowId, expRowId);

        if ((qualRowId != null) && (qualRowId.length > 1)) {
            for (final String element : qualRowId) {
                for (final PlumberQualificationDTO plumberQualifications : qualificationDTOs) {
                    if (Long.parseLong(element) == plumberQualifications.getPlumQualId()) {
                        qualificationDTOs.remove(plumberQualifications);
                        break;
                    }
                }
            }
        }
        for (final PlumberQualificationDTO qualification : qualificationDTOs) {

            plumberQualification = new TbPlumberQualification();
            plumberQualification.setPlumQualId(qualification.getPlumQualId());
            plumberQualification.setPlumId(master.getPlumId());
            plumberQualification.setPlumQualification(qualification.getPlumQualification());
            plumberQualification.setPlumInstituteName(qualification.getPlumInstituteName());
            plumberQualification.setPlumInstituteAddress(qualification.getPlumInstituteAddress());
            plumberQualification.setPlumPassYear(qualification.getPlumPassYear());
            plumberQualification.setPlumPassMonth(qualification.getPlumPassMonth());
            plumberQualification.setPlumPercentGrade(qualification.getPlumPercentGrade());
            plumberQualification.setUserId(plumberMaster.getUserId());
            plumberQualification.setLangId((int) (long) plumberMaster.getLangId());
            plumberQualification.setOrgId(plumberMaster.getOrgid());
            plumberQualification.setLgIpMac(plumberMaster.getLgIpMac());
            plumberQualification.setLmodDate(new Date());
            plumberQualification.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
            plumberQualificationsList.add(plumberQualification);
        }
        if ((expRowId != null) && (expRowId.length > 1)) {
            for (final String element : expRowId) {
                for (final PlumberExperienceDTO plumberExperience : experienceDTOs) {
                    if (Long.parseLong(element) == plumberExperience.getPlumExpId()) {
                        experienceDTOs.remove(plumberExperience);
                        break;
                    }
                }
            }
        }

        TbPlumberExperience plumberExperience = null;
        final List<TbPlumberExperience> plumberExperiencesList = new ArrayList<>();
        for (final PlumberExperienceDTO experienceDTO : experienceDTOs) {
            plumberExperience = new TbPlumberExperience();
            plumberExperience.setPlumExpId(experienceDTO.getPlumExpId());
            plumberExperience.setPlumId(master.getPlumId());
            plumberExperience.setPlumCompanyName(experienceDTO.getPlumCompanyName());
            plumberExperience.setPlumCompanyAddress(experienceDTO.getPlumCompanyAddress());
            final Date frmDate = Utility.stringToDate(experienceDTO.getExpFromDate());
            plumberExperience.setPlumFromDate(frmDate);
            final Date toDate = Utility.stringToDate(experienceDTO.getExpToDate());
            plumberExperience.setPlumToDate(toDate);
            final String exp = Double.toString(experienceDTO.getExperience());
            final String[] string = exp.split("\\.");
            final String year = string[0];
            final String month = string[1];
            plumberExperience.setPlumExpYear(Long.parseLong(year));
            plumberExperience.setPlumExpMonth(Long.parseLong(month));
            plumberExperience.setPlumCPDFirmType(experienceDTO.getFirmType());
            plumberExperience.setUserId(plumberMaster.getUserId());
            plumberExperience.setLangId((int) (long) plumberMaster.getLangId());
            plumberExperience.setOrgId(plumberMaster.getOrgid());
            plumberExperience.setLgIpMac(plumberMaster.getLgIpMac());
            plumberExperience.setLmodDate(new Date());
            plumberExperience.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
            plumberExperiencesList.add(plumberExperience);
        }

        plumberLicenseService.updatedPlumberLicenseDetailsOnScrutiny(master, plumberQualificationsList, plumberExperiencesList);
        return true;
    }

    private boolean validateFormData(final String remark, final String interviewDateTime) {
        boolean status = false;
        if ((remark == null) || remark.isEmpty()) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.interviewRemark"));
            status = true;
        }
        if ((interviewDateTime == null) || MainetConstants.BLANK.equals(interviewDateTime)) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.interviewDateAndTime"));
            status = true;
        }
        return status;
    }

    private ApplicantDetailDTO initializeApplicantAddressDetail(final ApplicantDetailDTO dto,
            final CFCApplicationAddressEntity addressEntity) {
        dto.setMobileNo(addressEntity.getApaMobilno());
        dto.setEmailId(addressEntity.getApaEmail());
        dto.setFloorNo(addressEntity.getApaFloor());
        dto.setBuildingName(addressEntity.getApaBldgnm());
        dto.setRoadName(addressEntity.getApaRoadnm());
        dto.setAreaName(addressEntity.getApaAreanm());
        dto.setVillageTownSub(addressEntity.getApaCityName());
        dto.setBlockName(addressEntity.getApaBlockName());
        if (addressEntity.getApaPincode() != null) {
            dto.setPinCode(Long.toString(addressEntity.getApaPincode()));
        }
        dto.setDwzid1(addressEntity.getApaZoneNo());
        dto.setDwzid2(addressEntity.getApaWardNo());
        return dto;
    }

    private ApplicantDetailDTO initializeApplicationDetail(final ApplicantDetailDTO dto, final TbCfcApplicationMstEntity entity) {
        dto.setApplicantTitle(entity.getApmTitle());
        dto.setApplicantFirstName(entity.getApmFname());
        dto.setApplicantMiddleName(entity.getApmMname());
        dto.setApplicantLastName(entity.getApmLname());
        if (entity.getApmBplNo() != null) {
            dto.setBplNo(entity.getApmBplNo());
            dto.setIsBPL(MainetConstants.PlumberLicense.YES);
        } else {
            dto.setIsBPL(MainetConstants.PlumberLicense.NO);
        }
        if (entity.getApmUID() != null) {
            dto.setAadharNo(entity.getApmUID().toString());
        }
        return dto;
    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the serviceId
     */
    @Override
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the plumberId
     */
    public Long getPlumberId() {
        return plumberId;
    }

    /**
     * @param plumberId the plumberId to set
     */
    public void setPlumberId(final Long plumberId) {
        this.plumberId = plumberId;
    }

    public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
    }

    public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
    }

    public boolean isScrutinyApplicable() {
        return scrutinyApplicable;
    }

    public void setScrutinyApplicable(final boolean scrutinyApplicable) {
        this.scrutinyApplicable = scrutinyApplicable;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    /**
     * @return the cfcEntity
     */
    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    /**
     * @param cfcEntity the cfcEntity to set
     */
    public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public PlumberMaster getPlumberMaster() {
        return plumberMaster;
    }

    public void setPlumberMaster(final PlumberMaster plumberMaster) {
        this.plumberMaster = plumberMaster;
    }

    public List<PlumberQualificationDTO> getPlumberQualificationDTOList() {
        return plumberQualificationDTOList;
    }

    public void setPlumberQualificationDTOList(
            final List<PlumberQualificationDTO> plumberQualificationDTOList) {
        this.plumberQualificationDTOList = plumberQualificationDTOList;
    }

    public List<PlumberExperienceDTO> getPlumberExperienceDTOList() {
        return plumberExperienceDTOList;
    }

    public void setPlumberExperienceDTOList(final List<PlumberExperienceDTO> plumberExperienceDTOList) {
        this.plumberExperienceDTOList = plumberExperienceDTOList;
    }

    public String getFileDownLoadPath() {
        return fileDownLoadPath;
    }

    public void setFileDownLoadPath(final String fileDownLoadPath) {
        this.fileDownLoadPath = fileDownLoadPath;
    }

    public String getInterviewRemark() {
        return interviewRemark;
    }

    public void setInterviewRemark(final String interviewRemark) {
        this.interviewRemark = interviewRemark;
    }

    public String getPlumQualDeletedRow() {
        return plumQualDeletedRow;
    }

    public void setPlumQualDeletedRow(final String plumQualDeletedRow) {
        this.plumQualDeletedRow = plumQualDeletedRow;
    }

    public String getPlumExpDeletedRow() {
        return plumExpDeletedRow;
    }

    public void setPlumExpDeletedRow(final String plumExpDeletedRow) {
        this.plumExpDeletedRow = plumExpDeletedRow;
    }

    /**
     * @return the interviewDateTime
     */
    public String getInterviewDateTime() {
        return interviewDateTime;
    }

    /**
     * @param interviewDateTime the interviewDateTime to set
     */
    public void setInterviewDateTime(final String interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(final String totalExp) {
        this.totalExp = totalExp;
    }

    public Date getInterviewDateTimeD() {
        return interviewDateTimeD;
    }

    public void setInterviewDateTimeD(final Date interviewDateTimeD) {
        this.interviewDateTimeD = interviewDateTimeD;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }
}
