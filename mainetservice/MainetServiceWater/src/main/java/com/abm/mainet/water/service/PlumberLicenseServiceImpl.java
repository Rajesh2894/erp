package com.abm.mainet.water.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.abm.mainet.water.dao.PlumberLicenseRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.domain.TbPlumberExperience;
import com.abm.mainet.water.domain.TbPlumberQualification;
import com.abm.mainet.water.dto.PlumLicenseRenewalSchDTO;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.repository.PlumberMasterRepository;
import com.abm.mainet.water.repository.TbWtPlumberRenewalRepostitory;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Arun.Chavda
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.PlumberLicenseService")
@Api(value = "/plumberlicenseservice")
@Path("/plumberlicenseservice")
public class PlumberLicenseServiceImpl implements PlumberLicenseService {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private IFileUploadService fileUploadService;
    @Resource
    PlumberLicenseRepository plumberLicenseRepository;
    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;
    @Resource
    private MessageSource messageSource;
    @Resource
    CommonService commonService;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private ServiceMasterService iServiceMasterService;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Resource
    TbWtPlumberRenewalRepostitory tbWtPlumberRenewalRepostitory;

    @Autowired
    private TbLoiMasService iTbLoiMasService;

    @Autowired
    private ICFCApplicationMasterService icfcApplicationMasterService;

    @Autowired
    private TbFinancialyearService iFinancialYearService;
    
    @Autowired
	ILicenseValidityMasterService licenseValidityMasterService;
    
    @Autowired
	private IOrganisationService organisationService;
    
    @Autowired
    private PlumberMasterRepository plumbermasterrepository;


    private static Logger log = Logger.getLogger(PlumberLicenseServiceImpl.class);

    @Override
    @Transactional
    @POST
    @Path("/saveplumberlicense")
    public PlumberLicenseResponseDTO savePlumberLicenseDetails(@RequestBody final PlumberLicenseRequestDTO requestDTO) {
        final RequestDTO applicantDetailDTO = new RequestDTO();
        final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
        applicantDetailDTO.setTitleId(requestDTO.getApplicant().getApplicantTitle());
        applicantDetailDTO.setfName(requestDTO.getApplicant().getApplicantFirstName());
        applicantDetailDTO.setmName(requestDTO.getApplicant().getApplicantMiddleName());
        applicantDetailDTO.setlName(requestDTO.getApplicant().getApplicantLastName());
        applicantDetailDTO.setMobileNo(requestDTO.getApplicant().getMobileNo());
        applicantDetailDTO.setEmail(requestDTO.getApplicant().getEmailId());
        applicantDetailDTO.setAreaName(requestDTO.getApplicant().getAreaName());
        if(StringUtils.isNotBlank(requestDTO.getApplicant().getPinCode())) {
        	applicantDetailDTO.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
        }
        
        applicantDetailDTO.setServiceId(requestDTO.getServiceId());
        applicantDetailDTO.setDeptId(requestDTO.getDeptId());
        applicantDetailDTO.setUserId(requestDTO.getUserId());
        applicantDetailDTO.setOrgId(requestDTO.getOrgId());
        applicantDetailDTO.setLangId((long) requestDTO.getApplicant().getLangId());
        if (requestDTO.getApplicant().getIsBPL()!=null && requestDTO.getApplicant().getIsBPL().equals(MainetConstants.FlagY)) {
            applicantDetailDTO.setBplNo(requestDTO.getApplicant().getBplNo());
        }

        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgId());

        /*
         * final LookUp lookUp = CommonMasterUtility .getNonHierarchicalLookUpObject(Long.valueOf(requestDTO.getApplicant().
         * getGender()), organisation);
         */

        // applicantDetailDTO.setGender(lookUp.getLookUpCode());
        applicantDetailDTO.setCityName(requestDTO.getApplicant().getVillageTownSub());
        applicantDetailDTO.setRoadName(requestDTO.getApplicant().getRoadName());

        applicantDetailDTO.setBlockName(requestDTO.getApplicant().getBlockName());
        if ((null != requestDTO.getApplicant().getAadharNo())
                && !MainetConstants.BLANK.equals(requestDTO.getApplicant().getAadharNo())) {
            final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
            final Long aadhaarNo = Long.parseLong(aadhar);
            applicantDetailDTO.setUid(aadhaarNo);
        }
        if (null != requestDTO.getApplicant().getDwzid1()) {
            applicantDetailDTO.setZoneNo(requestDTO.getApplicant().getDwzid1());
        }
        if (null != requestDTO.getApplicant().getDwzid2()) {
            applicantDetailDTO.setWardNo(requestDTO.getApplicant().getDwzid2());
        }
        if (null != requestDTO.getApplicant().getDwzid3()) {
            applicantDetailDTO.setBlockNo(String.valueOf(requestDTO.getApplicant().getDwzid3()));
        }
        if (requestDTO.getAmount() == 0d) {
            applicantDetailDTO.setPayStatus("F");
        }
        try {
            final Long applicationId = applicationService.createApplication(applicantDetailDTO);
            final PlumberMaster entity = new PlumberMaster();
            entity.setPlumFname(requestDTO.getPlumberFName());
            entity.setPlumMname(requestDTO.getPlumberMName());
            entity.setPlumLname(requestDTO.getPlumberLName());
            entity.setPlumContactNo(requestDTO.getApplicant().getMobileNo());
            entity.setPlumAddress(requestDTO.getApplicant().getAreaName());
            entity.setLangId(requestDTO.getLangId());
            entity.setPlumAppDate(requestDTO.getPlumAppDate());
            entity.setLgIpMac(requestDTO.getLgIpMac());
            entity.setLmoddate(new Date());
            entity.setOrgid(requestDTO.getOrgId());
            entity.setUserId(requestDTO.getUserId());
            entity.setApmApplicationId(applicationId);
            final StringBuilder builder = new StringBuilder();
            builder.append(requestDTO.getOrgId()).append(MainetConstants.FILE_PATH_SEPARATOR).append("Plumber_Photo")
                    .append(MainetConstants.FILE_PATH_SEPARATOR).append(Utility.getTimestamp());
            final String dirPath = builder.toString();
            entity.setPlumImagePath(dirPath);
            entity.setPlumImage(requestDTO.getPlumberImage());
            final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
            documentDetailsVO.setDocumentByteCode(requestDTO.getImageByteCode());
            final String fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            fileUploadService.convertAndSaveFile(documentDetailsVO, fileNetPath, dirPath, requestDTO.getPlumberImage());
            final PlumberMaster plumberMaster = plumberLicenseRepository.savePlumberLicenseDetails(entity);
            Long plumberId = null;
            if (null != plumberMaster) {
                plumberId = plumberMaster.getPlumId();
            }
            TbPlumberQualification plumberQualification = null;
            final List<TbPlumberQualification> plumberQualificationsList = new ArrayList<>();
            for (final PlumberQualificationDTO qualification : requestDTO.getPlumberQualificationDTOList()) {
                plumberQualification = new TbPlumberQualification();
                plumberQualification.setPlumQualification(qualification.getPlumQualification());
                plumberQualification.setPlumInstituteName(qualification.getPlumInstituteName());
                plumberQualification.setPlumInstituteAddress(qualification.getPlumInstituteAddress());
                plumberQualification.setPlumPassYear(qualification.getPlumPassYear());
                plumberQualification.setPlumPassMonth((qualification.getPlumPassMonth()));
                plumberQualification.setPlumPercentGrade(qualification.getPlumPercentGrade());
                plumberQualification.setUserId(requestDTO.getUserId());
                if (requestDTO.getLangId() != null)
                    plumberQualification.setLangId((int) (long) requestDTO.getLangId());
                plumberQualification.setOrgId(requestDTO.getOrgId());
                plumberQualification.setLgIpMac(requestDTO.getLgIpMac());
                plumberQualification.setLmodDate(requestDTO.getPlumAppDate());
                plumberQualification.setPlumId(plumberId);
                plumberQualification.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
                plumberQualificationsList.add(plumberQualification);
            }
            plumberLicenseRepository.savePlumberLicenseAcademicDetails(plumberQualificationsList);

            TbPlumberExperience plumberExperience = null;
            final List<TbPlumberExperience> plumberExperiencesList = new ArrayList<>();
            if (requestDTO.getPlumberExperienceDTOList() != null
                    && !requestDTO.getPlumberExperienceDTOList().isEmpty()) {
                for (final PlumberExperienceDTO experienceDTO : requestDTO.getPlumberExperienceDTOList()) {
                    plumberExperience = new TbPlumberExperience();
                    plumberExperience.setPlumCompanyName(experienceDTO.getPlumCompanyName());
                    plumberExperience.setPlumCompanyAddress(experienceDTO.getPlumCompanyAddress());
                    if (experienceDTO.getExpFromDate() != null) {
                        final Date frmDate = Utility.stringToDate(experienceDTO.getExpFromDate());
                        plumberExperience.setPlumFromDate(frmDate);
                    }
                    if (experienceDTO.getExpToDate() != null) {
                        final Date toDate = Utility.stringToDate(experienceDTO.getExpToDate());
                        plumberExperience.setPlumToDate(toDate);
                    }
                    if (experienceDTO.getExperience() != null) {
                        final String exp = Double.toString(experienceDTO.getExperience());
                        final String[] string = exp.split("\\.");
                        final String year = string[0];
                        final String month = string[1];
                        plumberExperience.setPlumExpYear(Long.parseLong(year));
                        plumberExperience.setPlumExpMonth(Long.parseLong(month));
                    }
                    plumberExperience.setPlumCPDFirmType(experienceDTO.getFirmType());
                    plumberExperience.setUserId(requestDTO.getUserId());
                    if (requestDTO.getLangId() != null)
                        plumberExperience.setLangId((int) (long) requestDTO.getLangId());
                    plumberExperience.setOrgId(requestDTO.getOrgId());
                    plumberExperience.setLgIpMac(requestDTO.getLgIpMac());
                    plumberExperience.setLmodDate(requestDTO.getPlumAppDate());
                    plumberExperience.setPlumId(plumberId);
                    plumberExperience.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
                    plumberExperiencesList.add(plumberExperience);
                }
            }
            plumberLicenseRepository.savePlumberLicenseExperienceDetails(plumberExperiencesList);

            applicantDetailDTO.setApplicationId(applicationId);

            responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                fileUploadService.doFileUpload(requestDTO.getDocumentList(), applicantDetailDTO);
            }
            responseDTO.setApplicationId(applicationId);
            requestDTO.setApplicationId(applicationId);
        } catch (Exception e) {
            log.error("In saving new savePlumberLicenseDetails()", e);
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            throw new FrameworkException("Error in savePlumberLicenseDetails() ", e);
        }
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public PlumberMaster getPlumberDetailsByAppId(final long applicationId, final Long orgId) {
        return plumberLicenseRepository.getPlumberDetailsByAppId(applicationId, orgId);
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<PlumberQualificationDTO> getPlumberQualificationDetails(final Long plumberId, final Long orgId) {
        final List<TbPlumberQualification> list = plumberLicenseRepository.getPlumberQualificationDetails(plumberId,
                orgId);
        final List<PlumberQualificationDTO> qualificationDTOs = new ArrayList<>();
        PlumberQualificationDTO qualificationDTO = null;
        if ((null != list) && !list.isEmpty()) {
            for (final TbPlumberQualification qualificationEntity : list) {
                qualificationDTO = new PlumberQualificationDTO();
                qualificationDTO.setPlumQualification(qualificationEntity.getPlumQualification());
                qualificationDTO.setPlumInstituteName(qualificationEntity.getPlumInstituteName());
                qualificationDTO.setPlumInstituteAddress(qualificationEntity.getPlumInstituteAddress());
                // qualificationDTO.setPlumPassYear(qualificationEntity.getPlumPassYear());
                qualificationDTO.setPlumPassMonth(qualificationEntity.getPlumPassMonth());
                qualificationDTO.setPlumPercentGrade(qualificationEntity.getPlumPercentGrade());
                qualificationDTO.setPlumQualId(qualificationEntity.getPlumQualId());
                qualificationDTOs.add(qualificationDTO);
            }
        }
        return qualificationDTOs;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<PlumberExperienceDTO> getPlumberExperienceDetails(final Long plumberId, final Long orgId) {
        final List<TbPlumberExperience> list = plumberLicenseRepository.getPlumberExperienceDetails(plumberId, orgId);
        final List<PlumberExperienceDTO> experienceDTOs = new ArrayList<>();
        PlumberExperienceDTO experienceDTO = null;
        if ((null != list) && !list.isEmpty()) {
            String string = null;
            String frmDate = null;
            String toDate = null;
            for (final TbPlumberExperience experienceEntity : list) {
                experienceDTO = new PlumberExperienceDTO();
                experienceDTO.setPlumCompanyName(experienceEntity.getPlumCompanyName());
                experienceDTO.setPlumCompanyAddress(experienceEntity.getPlumCompanyAddress());
                experienceDTO.setPlumExpYear(experienceEntity.getPlumExpYear());
                experienceDTO.setPlumExpMonth(experienceEntity.getPlumExpMonth());
                if (experienceEntity.getPlumExpYear() != null && experienceEntity.getPlumExpMonth() != null) {
                    string = experienceEntity.getPlumExpYear() + MainetConstants.operator.DOT
                            + experienceEntity.getPlumExpMonth();
                    experienceDTO.setExperience(Double.parseDouble(string));
                }
                if (experienceEntity.getPlumFromDate() != null) {
                    frmDate = Utility.dateToString(experienceEntity.getPlumFromDate());
                    experienceDTO.setExpFromDate(frmDate);
                }
                if (experienceEntity.getPlumToDate() != null) {
                    toDate = Utility.dateToString(experienceEntity.getPlumToDate());
                    experienceDTO.setExpToDate(toDate);
                }
                experienceDTO.setFirmType(experienceEntity.getPlumCPDFirmType());
                experienceDTO.setPlumExpId(experienceEntity.getPlumExpId());
                experienceDTOs.add(experienceDTO);
            }
        }
        return experienceDTOs;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long orgId) {
        final CFCApplicationAddressEntity master = iCFCApplicationAddressService
                .getApplicationAddressByAppId(applicationId, orgId);
        WardZoneBlockDTO wardZoneDTO = null;
        if (master != null) {
            wardZoneDTO = new WardZoneBlockDTO();
            if (master.getApaZoneNo() != null) {
                wardZoneDTO.setAreaDivision1(master.getApaZoneNo());
            }
            if (master.getApaWardNo() != null) {
                wardZoneDTO.setAreaDivision2(master.getApaWardNo());
            }
            if ((null != master.getApaBlockno()) && !master.getApaBlockno().isEmpty()) {
                final Long blockNo = Long.parseLong(master.getApaBlockno());
                wardZoneDTO.setAreaDivision3(blockNo);
            }
        }
        return wardZoneDTO;
    }

    /**
     * This method used for get Scrutiny level LOI charges
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId) {

        List<WaterRateMaster> requiredCharges = new ArrayList<>();
        final WSRequestDTO requestDTO = new WSRequestDTO();
        final List<WaterRateMaster> chargeModelList = new ArrayList<>();
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        TbCfcApplicationMstEntity cfcEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applicationId, orgId);

        requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId,
                MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE);
        for (final WaterRateMaster actualRate : requiredCharges) {
            if (cfcEntity.getApmBplNo() != null)
                actualRate.setIsBPL(MainetConstants.FlagY);
            else
                actualRate.setIsBPL(MainetConstants.FlagN);
            actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
            actualRate.setOrgId(orgId);
            actualRate.setServiceCode(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE);
            actualRate.setRateStartDate(new Date().getTime());
            chargeModelList.add(actualRate);
        }
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
        final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
        WaterRateMaster loiCharges = null;
        Double baseRate = 0d;
        double amount;
        final Map<Long, Double> chargeMap = new HashMap<>();
        for (final Object rate : waterRateList) {

            loiCharges = (WaterRateMaster) rate;
            LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                    PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
            baseRate = WaterCommonUtility.getAndSetBaseRate(0.0, loiCharges, null, taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), amount);
        }
        return chargeMap;

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void updatedPlumberLicenseDetailsOnScrutiny(final PlumberMaster plumberMaster,
            final List<TbPlumberQualification> tbPlumberQualifications,
            final List<TbPlumberExperience> tbPlumberExperiences) {
        plumberLicenseRepository.savePlumberInterviewDetails(plumberMaster);
        plumberLicenseRepository.savePlumberLicenseAcademicDetails(tbPlumberQualifications);
        plumberLicenseRepository.savePlumberLicenseExperienceDetails(tbPlumberExperiences);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void updatedQualAndExpIsDeletedFlag(final String[] qualRowId, final String[] expRowId) {
        if ((qualRowId != null) && (qualRowId.length > 1)) {
            for (final String string : qualRowId) {
                final long qualId = Long.valueOf(string);
                plumberLicenseRepository.updatedQualificationIsDeletedFlag(qualId);
            }
        }
        if ((expRowId != null) && (expRowId.length > 1)) {
            for (final String string : expRowId) {
                final long expId = Long.valueOf(string);
                plumberLicenseRepository.updatedExpIsDeletedFlag(expId);
            }
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public PlumLicenseRenewalSchDTO updatedUserTaskAndPlumberLicenseExecutionDetails(final PlumberMaster master,
            final WorkflowProcessParameter taskDefDto) {
        final PlumLicenseRenewalSchDTO renewalSchDTO = new PlumLicenseRenewalSchDTO();
        final Long licNo = seqGenFunctionUtility.generateSequenceNo(
                MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.MODULE,
                MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.TABLE,
                MainetConstants.PlumberLicense.PLUM_MASTER_TABLE.COLUMN, master.getOrgid(),
                MainetConstants.RECEIPT_MASTER.Reset_Type, null);
        
        final Date sysDate = UtilityService.getSQLDate(new Date());
        final String string = sysDate.toString();
        final String[] strings = string.split(MainetConstants.operator.MINUS);
        final String yearPart = strings[0];
        
        final Long orgId = master.getOrgid();
        String licNumber = "";
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	 final String  twoPaddingLicNo = String.format("%03d", Integer.parseInt(licNo.toString()));
        	 final String orgShortName = organisationService.getOrganisationById(orgId).getOrgShortNm();
             final String plumNo = "PLNO";
             licNumber =  orgShortName.concat(MainetConstants.SLASH).concat(MainetConstants.DEPT_SHORT_NAME.WATER)
             		.concat(MainetConstants.SLASH).concat(plumNo)
             		.concat(MainetConstants.SLASH).concat(twoPaddingLicNo);
        } else {
        	final String paddingLicNo = String.format("%04d", Integer.parseInt(licNo.toString()));
        	licNumber = orgId.toString().concat(yearPart).concat(paddingLicNo);
        }
        
        renewalSchDTO.setLicenseNo(String.valueOf(licNumber));
        Long dependOnId = null;
        final Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        Organisation organisation = new Organisation();
        organisation.setOrgid(master.getOrgid());
        String toDateDependOn  = null;
        
    	//service master for department
    	ServiceMaster serviceMaster = iServiceMasterService.getServiceMaster(master.getServiceId(), master.getOrgid());
    	List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService
				.searchLicenseValidityData(master.getOrgid(), serviceMaster.getTbDepartment().getDpDeptid(), master.getServiceId(),MainetConstants.ZERO_LONG,MainetConstants.ZERO_LONG);
    	LicenseValidityMasterDto licMasData = licValMasterDtoList.get(0);
    	toDateDependOn = CommonMasterUtility.getNonHierarchicalLookUpObject(licMasData.getLicDependsOn()).getLookUpCode();
    	String units =  CommonMasterUtility.getNonHierarchicalLookUpObject(licMasData.getUnit()).getLookUpCode();
    	int valToAddInDate = Integer.valueOf(licMasData.getLicTenure());
    	 if (MainetConstants.PlumberLicense.TODATE_CALENDER_YEAR_WISE.equals(toDateDependOn)) {
             cal.setTime(date);
             final int year = cal.get(Calendar.YEAR);
             cal.set(Calendar.YEAR, year);
             cal.set(Calendar.MONTH, 11);
             cal.set(Calendar.DAY_OF_MONTH, 31);
             final Date endDate = cal.getTime();
             renewalSchDTO.setLicenseToDate(Utility.dateToString(endDate));
             renewalSchDTO.setLicenseFromDate(Utility.dateToString(date));
         } else if (MainetConstants.PlumberLicense.TODATE_FINANCIAL_YEAR_WISE.equals(toDateDependOn)) {
             cal.setTime(date);
             final int year = cal.get(Calendar.YEAR);
             cal.set(Calendar.YEAR, year);
             cal.set(Calendar.MONTH, 2);
             cal.set(Calendar.DAY_OF_MONTH, 31);
             final Date endDate = cal.getTime();
             renewalSchDTO.setLicenseToDate(Utility.dateToString(endDate));
             renewalSchDTO.setLicenseFromDate(Utility.dateToString(date));
         } else if (MainetConstants.PlumberLicense.TODATE_ON_LICENSE_ASDATE_WISE.equals(toDateDependOn)) {
             final Date today = cal.getTime();
             if(MainetConstants.PlumberLicense.YEARLY.equals(units)) {
            	 cal.add(Calendar.YEAR, valToAddInDate);
             }else if(MainetConstants.PlumberLicense.MONTHLY.equals(units)) {
            	 //months to year
            	 cal.add(Calendar.MONTH, valToAddInDate);
             }
             final Date nextYear = cal.getTime();
             renewalSchDTO.setLicenseToDate(Utility.dateToString(nextYear));
             renewalSchDTO.setLicenseFromDate(Utility.dateToString(today));
         }
    	 
        
//        	final List<TbPlumRenewalScheduler> renewalSchedulers = plumberLicenseRepository
//                    .getPlumRenewalSchedulerDetails(master.getServiceId(), master.getOrgid());
//            for (final TbPlumRenewalScheduler tbPlumRenewalScheduler : renewalSchedulers) {
//                if (tbPlumRenewalScheduler.getTodate() == null) {
//                    dependOnId = tbPlumRenewalScheduler.getDependon();
//                    break;
//                }
//            }
//
//            toDateDependOn = CommonMasterUtility.getNonHierarchicalLookUpObject(dependOnId).getLookUpCode();
//            if (MainetConstants.PlumberLicense.TODATE_CALENDER_YEAR_WISE.equals(toDateDependOn)) {
//                cal.setTime(date);
//                final int year = cal.get(Calendar.YEAR);
//                cal.set(Calendar.YEAR, year);
//                cal.set(Calendar.MONTH, 11);
//                cal.set(Calendar.DAY_OF_MONTH, 31);
//                final Date endDate = cal.getTime();
//                renewalSchDTO.setLicenseToDate(Utility.dateToString(endDate));
//                renewalSchDTO.setLicenseFromDate(Utility.dateToString(date));
//            } else if (MainetConstants.PlumberLicense.TODATE_FINANCIAL_YEAR_WISE.equals(toDateDependOn)) {
//                cal.setTime(date);
//                final int year = cal.get(Calendar.YEAR);
//                cal.set(Calendar.YEAR, year);
//                cal.set(Calendar.MONTH, 2);
//                cal.set(Calendar.DAY_OF_MONTH, 31);
//                final Date endDate = cal.getTime();
//                renewalSchDTO.setLicenseToDate(Utility.dateToString(endDate));
//                renewalSchDTO.setLicenseFromDate(Utility.dateToString(date));
//            } else if (MainetConstants.PlumberLicense.TODATE_ON_LICENSE_DATE_WISE.equals(toDateDependOn)) {
//                final Date today = cal.getTime();
//                cal.add(Calendar.YEAR, 1); // to get previous year add -1
//                cal.add(Calendar.DAY_OF_MONTH, -1);
//                final Date nextYear = cal.getTime();
//                renewalSchDTO.setLicenseToDate(Utility.dateToString(nextYear));
//                renewalSchDTO.setLicenseFromDate(Utility.dateToString(today));
//            }
        
        
        
        master.setPlumLicNo(String.valueOf(licNo));
        master.setPlumLicFromDate(Utility.stringToDate(renewalSchDTO.getLicenseFromDate()));
        master.setPlumLicToDate(Utility.stringToDate(renewalSchDTO.getLicenseToDate()));
        plumberLicenseRepository.updatedPlumberLicenseExecutionDetailsByDept(master);
        // iTaskManagerService.updateUserTask(taskDefDto);
        return renewalSchDTO;
    }

    @Override
    @WebMethod(exclude = true)
    public void initiateWorkFlowForFreeService(PlumberLicenseRequestDTO requestDTO) {
        if (requestDTO.getAmount() == 0d) {
        	boolean checklist = false;
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                checklist = true;
            }
            ApplicationMetadata applicationData = new ApplicationMetadata();
            final ServiceMaster serviceMaster = iServiceMasterService.getServiceMaster(requestDTO.getServiceId(),
                    requestDTO.getOrgId());
            if(serviceMaster!=null && (serviceMaster.getTbDepartment()!=null && serviceMaster.getTbDepartment().getDpDeptid()!=null)) {
            	requestDTO.setDeptId(serviceMaster.getTbDepartment().getDpDeptid());	
            }
            if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
                applicationData.setIsLoiApplicable(false);               
            } else {
                applicationData.setIsLoiApplicable(true);
            }
            applicationData.setApplicationId(requestDTO.getApplicationId());
            applicationData.setIsCheckListApplicable(checklist);
            applicationData.setOrgId(requestDTO.getOrgId());
            requestDTO.getApplicant().setServiceId(requestDTO.getServiceId());
            requestDTO.getApplicant().setDepartmentId(requestDTO.getDeptId());
            commonService.initiateWorkflowfreeService(applicationData, requestDTO.getApplicant());  
        }
    }

    @Override
    @Transactional
    public boolean executeWfAction(WorkflowTaskAction wfAction, String eventName, Long serviceId) {
        boolean retVal = false;
        WorkflowTaskActionResponse response = null;

        if (eventName.equals("WPL") || eventName.equals("PLR") || eventName.equals("DPL")) {
            if (wfAction.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
                    && wfAction.getIsFinalApproval()) {
                plumberLicenseRepository.updateAuthStatus("A", wfAction.getEmpId(), new Date(),
                        wfAction.getApplicationId(), wfAction.getOrgId());
            }
        } else if (wfAction.getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            plumberLicenseRepository.updateAuthStatus("R", wfAction.getEmpId(), new Date(), wfAction.getApplicationId(),
                    wfAction.getOrgId());
        }
        try {
            response = executeWorkflowAction(wfAction, serviceId);
            retVal = true;
        } catch (Exception ex) {
            log.info("Exception occurred during workflow action execution ", ex);
            retVal = false;
            throw new FrameworkException("Exception occurred during workflow action execution ", ex);
        }

        return retVal;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private WorkflowTaskActionResponse executeWorkflowAction(final WorkflowTaskAction wfAction, Long serviceId) {
        WorkflowTaskActionResponse response = null;
        String processName = iServiceMasterService.getProcessName(serviceId, wfAction.getOrgId());
        if (processName != null) {

            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();

            workflowdto.setProcessName(processName);
            workflowdto.setWorkflowTaskAction(wfAction);
            try {
                response = workflowExecutionService.updateWorkflow(workflowdto);
            } catch (final Exception e) {
                throw new FrameworkException("Exception in work order generation for jbpm workflow : " + e.getMessage(),
                        e);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @POST
    @Path("/getPlumberDetailsByLicenseNumber/orgId/{orgId}/licenseNumber/{licenseNumber}")
    public PlumberLicenseRequestDTO getPlumberDetailsByLicenseNumber(@PathParam("orgId") Long orgId,
            @PathParam("licenseNumber") String licenseNumber) {
        PlumberLicenseRequestDTO plumDto = null;
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        try {
            PlumberMaster plumMas = plumberLicenseRepository.getLicenseDetail(licenseNumber, orgId);
            if (plumMas != null) {
                plumDto = new PlumberLicenseRequestDTO();
                plumDto.setPlumberId(plumMas.getPlumId());
                plumDto.setApplicationId(plumMas.getApmApplicationId());
                plumDto.setPlumberLicenceNo(plumMas.getPlumLicNo());

                final List<PlumberQualificationDTO> qualificationList = getPlumberQualificationDetails(
                        plumMas.getPlumId(), orgId);

                if (qualificationList != null && !qualificationList.isEmpty()) {
                    plumDto.setPlumberQualificationDTOList(qualificationList);
                }
                final List<PlumberExperienceDTO> experienceList = getPlumberExperienceDetails(plumMas.getPlumId(),
                        orgId);
                if (experienceList != null) {
                    plumDto.setPlumberExperienceDTOList(experienceList);
                }
                plumDto.setPlumberFName(plumMas.getPlumFname());
                if (plumMas.getPlumMname() != null && !plumMas.getPlumMname().isEmpty()) {
                    plumDto.setPlumberMName(plumMas.getPlumMname());
                }

                plumDto.setPlumberLName(plumMas.getPlumLname());
                plumDto.setPlumberId(plumMas.getPlumId());
                plumDto.setPlumContactNo(plumMas.getPlumContactNo());
                plumDto.setPlumAddress(plumMas.getPlumAddress());
                plumDto.setPlumLicFromDate(plumMas.getPlumLicFromDate());
                plumDto.setPlumLicToDate(plumMas.getPlumLicToDate());

                plumDto.setApplicant(getPlumberDetails(plumMas.getPlumId(), orgId));
                if(plumMas.getApmApplicationId() != null) {
                	plumDto.setApplicant(getApplicantDetails(plumMas.getApmApplicationId(), orgId));
                }
            }
        } catch (Exception e) {
            log.error("Exception ocours to getPlumberDetailsByLicenseNumber() " + e);
            throw new FrameworkException("Exception occours in getPlumberDetailsByLicenseNumber() method" + e);
        }
        return plumDto;
    }

    @Override
    @POST
    @Path("/saveplumberlicenseRenewalData")
    public PlumberLicenseResponseDTO savePlumberRenewalData(PlumberLicenseRequestDTO requestDTO) {
        Organisation organisation = new Organisation();
        final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
        try {
            PlumberLicenseRequestDTO savePlumRenewData = savePlumberLicenseRenewDataAndAttachFile(requestDTO);
            organisation.setOrgid(requestDTO.getOrgId());

            responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
            responseDTO.setApplicationId(savePlumRenewData.getApplicationId());

            if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(responseDTO.getStatus())) {
                initiateWorkFlowForFreeService(savePlumRenewData);
            }
        } catch (Exception e) {
            log.error("Exception ocours to savePlumberRenewalData() " + e);
            throw new FrameworkException("Exception occours in savePlumberRenewalData() method" + e);
        }

        return responseDTO;
    }

    @Override
    @Transactional
    public PlumberLicenseRequestDTO savePlumberLicenseRenewDataAndAttachFile(PlumberLicenseRequestDTO requestDTO) {
        final RequestDTO applicantDetailDTO = new RequestDTO();
        applicantDetailDTO.setTitleId(requestDTO.getApplicant().getApplicantTitle());
        applicantDetailDTO.setfName(requestDTO.getApplicant().getApplicantFirstName());
        applicantDetailDTO.setlName(requestDTO.getApplicant().getApplicantLastName());
        applicantDetailDTO.setMobileNo(requestDTO.getApplicant().getMobileNo());
        applicantDetailDTO.setEmail(requestDTO.getApplicant().getEmailId());
        applicantDetailDTO.setAreaName(requestDTO.getApplicant().getAreaName());
        if(requestDTO.getApplicant().getPinCode()!=null)
        applicantDetailDTO.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
        applicantDetailDTO.setServiceId(requestDTO.getServiceId());
        applicantDetailDTO.setDeptId(requestDTO.getDeptId());
        applicantDetailDTO.setUserId(requestDTO.getUserId());
        applicantDetailDTO.setOrgId(requestDTO.getOrgId());
        applicantDetailDTO.setLangId((long) requestDTO.getApplicant().getLangId());
        if (requestDTO.getApplicant().getIsBPL()!=null && requestDTO.getApplicant().getIsBPL().equals(MainetConstants.FlagY)) {
            applicantDetailDTO.setBplNo(requestDTO.getApplicant().getBplNo());
        }

        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgId());

        applicantDetailDTO.setCityName(requestDTO.getApplicant().getVillageTownSub());
        applicantDetailDTO.setBlockName(requestDTO.getApplicant().getBlockName());
        if ((null != requestDTO.getApplicant().getAadharNo())
                && !MainetConstants.BLANK.equals(requestDTO.getApplicant().getAadharNo())) {
            final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
            final Long aadhaarNo = Long.parseLong(aadhar);
            applicantDetailDTO.setUid(aadhaarNo);
        }
        if (null != requestDTO.getApplicant().getDwzid1()) {
            applicantDetailDTO.setZoneNo(requestDTO.getApplicant().getDwzid1());
        }
        if (null != requestDTO.getApplicant().getDwzid2()) {
            applicantDetailDTO.setWardNo(requestDTO.getApplicant().getDwzid2());
        }
        if (null != requestDTO.getApplicant().getDwzid3()) {
            applicantDetailDTO.setBlockNo(String.valueOf(requestDTO.getApplicant().getDwzid3()));
        }
        if (requestDTO.getAmount() == 0d) {
            applicantDetailDTO.setPayStatus("F");
        }
        try {
            final Long applicationId = applicationService.createApplication(applicantDetailDTO);
            requestDTO.setApplicationId(applicationId);
            final PlumberRenewalRegisterMaster entity = new PlumberRenewalRegisterMaster();
            entity.setPlum_id(requestDTO.getPlumberId());
            entity.setApm_application_id(applicationId);
            entity.setRn_date(new Date());
            entity.setOrgid(requestDTO.getOrgId());
            entity.setLangId(requestDTO.getLangId());
            entity.setUserId(requestDTO.getUserId());
            entity.setLmoddate(new Date());
            entity.setUpdatedBy(requestDTO.getUserId());
            entity.setUpdatedDate(new Date());
            tbWtPlumberRenewalRepostitory.save(entity);
            applicantDetailDTO.setApplicationId(applicationId);

            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                fileUploadService.doFileUpload(requestDTO.getDocumentList(), applicantDetailDTO);
            }

        } catch (Exception e) {
            log.error("Exception ocours to savePlumberLicenseRenewDataAndAttachFile() " + e);
            throw new FrameworkException("Exception occours in savePlumberLicenseRenewDataAndAttachFile() method" + e);
        }
        WaterCommonUtility.sendSMSandEMail(requestDTO.getApplicant(), requestDTO.getApplicationId(), null, "PLR",
                organisation);
        return requestDTO;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean loiCalculation(Long applicationId, Long serviceId, Long orgId, Long userUId, String serviceName,
            Date LicTodate) {
        boolean status;

        Map<Long, Double> loiCharges = getLoiCharges(applicationId, serviceId, orgId, serviceName, LicTodate);
        // T#90050
        if (MapUtils.isNotEmpty(loiCharges)) {
            List<TbLoiDet> loiDetail = new ArrayList<TbLoiDet>();
            WorkflowTaskAction wfActionDto = new WorkflowTaskAction();
            TbLoiMas loiMasDto = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class)
                    .saveLOIAppData(
                            loiCharges, serviceId, loiDetail, true/* approvalLetterGenerationApplicable */,
                            wfActionDto);
            if (loiMasDto != null) {
                // setLoiId(loiMasDto.getLoiNo());
                return true;
            }
        }
        return false;

    }

    /**
     * This method used for get Scrutiny level LOI charges
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId,
            String serviceName, Date LicTodate) {

        final WSRequestDTO requestDTO = new WSRequestDTO();

        final List<WaterRateMaster> chargeModelList = new ArrayList<>();
        List<WaterRateMaster> requiredCharges = new ArrayList<>();
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId, serviceName);
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC, 2,
                org);
        String bpl = MainetConstants.BLANK;
        TbCfcApplicationMstEntity cfcEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applicationId, orgId);
        if (cfcEntity.getApmBplNo() != null && !cfcEntity.getApmBplNo().isEmpty()) {
            bpl = MainetConstants.FlagY;
        } else {
            bpl = MainetConstants.FlagN;
        }
        String subCategoryDesc = MainetConstants.BLANK;
        for (final WaterRateMaster actualRate : requiredCharges) {

            for (final LookUp lookup : subCategryLookup) {
                if (lookup.getLookUpCode().equals("LF")) {
                    subCategoryDesc = lookup.getDescLangFirst();
                }
            }

            actualRate.setTaxSubCategory(subCategoryDesc);
            actualRate.setIsBPL(bpl);
            actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
            // actualRate.setBillingStartDate(new Date().getTime());
            // actualRate.setBillingEndDate(new Date().getTime());
            actualRate.setOrgId(orgId);
            actualRate.setServiceCode(serviceName);
            chargeModelList.add(actualRate);

        }
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
        final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
        WaterRateMaster loiCharges = null;
        Double baseRate = 0d;
        double amount;
        final Map<Long, Double> chargeMap = new HashMap<>();
        for (final Object rate : waterRateList) {
            loiCharges = (WaterRateMaster) rate;
            LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                    PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
            baseRate = WaterCommonUtility.getAndSetBaseRate(0.0, loiCharges, null, taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), amount);

        }
        return chargeMap;

    }

    @Transactional
    @Override
    public PlumberLicenseRequestDTO getValidDates(Long applicationId) {
        PlumberLicenseRequestDTO plumberDto = new PlumberLicenseRequestDTO();
        try {
            Object[] reciptMas = plumberLicenseRepository.plumberRenewValidDates(applicationId);
            if (reciptMas != null) {

                if (reciptMas[0] != null) {
                    plumberDto.setPlumLicFromDate((Date) reciptMas[0]);
                }
                if (reciptMas[1] != null) {
                    plumberDto.setPlumLicToDate((Date) reciptMas[1]);
                }
                if (reciptMas[2] != null) {
                    plumberDto.setPlumberId(Long.valueOf(reciptMas[2].toString()));
                }
                if (plumberDto.getPlumberId() != null) {
                    Object[] renewMas = plumberLicenseRepository.getmasterDates(plumberDto.getPlumberId());
                    if (renewMas != null) {
                        if (renewMas[0] != null && plumberDto.getPlumLicFromDate() == null) {
                            plumberDto.setPlumLicFromDate((Date) renewMas[0]);
                        }
                        if (renewMas[1] != null && plumberDto.getPlumLicToDate() == null) {
                            plumberDto.setPlumLicToDate((Date) renewMas[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception ocours to getValidDates() " + e);
            throw new FrameworkException("Exception occours in getValidDates() method" + e);
        }

        return plumberDto;
    }

    @Transactional
    @WebMethod(exclude = true)
    public Map<Long, Double> getRenewalLoiCharges(final Long applicationId, final Long serviceId, final Long orgId,
            String serviceName, Date LicTodate) {

        final WSRequestDTO requestDTO = new WSRequestDTO();
        final List<WaterRateMaster> chargeModelList = new ArrayList<>();
        List<WaterRateMaster> requiredCharges = new ArrayList<>();
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        requiredCharges = WaterCommonUtility.getChargesForWaterRateMaster(requestDTO, orgId, "PLR");
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC, 2,
                org);
        String subCategoryDesc = MainetConstants.BLANK;
        for (final WaterRateMaster actualRate : requiredCharges) {
            final Date date = new Date();

            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            final int year = cal.get(Calendar.YEAR);

            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, 00);
            cal.set(Calendar.DAY_OF_MONTH, 01);
            final Date startDate = cal.getTime();

            final Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date);
            cal1.set(Calendar.YEAR, year);
            cal1.set(Calendar.MONTH, 00);
            cal1.set(Calendar.DAY_OF_MONTH, 22);
            final Date endDate = cal1.getTime();

            final Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            cal2.set(Calendar.YEAR, year + 1);
            cal2.set(Calendar.MONTH, 00);
            cal2.set(Calendar.DAY_OF_MONTH, 31);
            final Date nextYrDate = cal2.getTime();

            final Calendar cal3 = Calendar.getInstance();
            cal3.setTime(LicTodate);
            final int licExpiryYear = cal3.get(Calendar.YEAR);
            int diffYrforSecurityDeposit = year - licExpiryYear;

            /*
             * String taxNameCode = CommonMasterUtility.getNonHierarchicalLookUpObject(actualRate.getTaxId(), org)
             * .getLookUpCode(); if (date.after(startDate) && date.before(endDate) && year == licExpiryYear) { if
             * (taxNameCode.equals("APC")) { for (final LookUp lookup : subCategryLookup) { if
             * (lookup.getLookUpCode().equals(actualRate.getTaxSubCategory())) { subCategoryDesc = lookup.getDescLangFirst(); } }
             * actualRate.setTaxSubCategory(subCategoryDesc); actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER); //
             * actualRate.setBillingStartDate(new Date().getTime()); // actualRate.setBillingEndDate(new Date().getTime());
             * actualRate.setOrgId(orgId); actualRate.setServiceCode(serviceName); chargeModelList.add(actualRate); } } else if
             * (date.after(endDate) && diffYrforSecurityDeposit <= 3) { if (taxNameCode.equals("APC") ||
             * taxNameCode.equals("SCD")) { for (final LookUp lookup : subCategryLookup) { if
             * (lookup.getLookUpCode().equals(actualRate.getTaxSubCategory())) { subCategoryDesc = lookup.getDescLangFirst(); } }
             * // actualRate.setDiffYr(diffYrforSecurityDeposit); actualRate.setTaxSubCategory(subCategoryDesc);
             * actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER); // actualRate.setBillingStartDate(new
             * Date().getTime()); // actualRate.setBillingEndDate(new Date().getTime()); actualRate.setOrgId(orgId);
             * actualRate.setServiceCode(serviceName); chargeModelList.add(actualRate); } } else if (date.after(endDate) &&
             * diffYrforSecurityDeposit > 3) { // actualRate.setDiffYr(diffYrforSecurityDeposit);
             * actualRate.setTaxSubCategory(subCategoryDesc); actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER); //
             * actualRate.setBillingStartDate(new Date().getTime()); // actualRate.setBillingEndDate(new Date().getTime());
             * actualRate.setOrgId(orgId); actualRate.setServiceCode(serviceName); chargeModelList.add(actualRate); }else {
             */
            String bpl = MainetConstants.BLANK;
            TbCfcApplicationMstEntity cfcEntity = icfcApplicationMasterService
                    .getCFCApplicationByApplicationId(applicationId, orgId);
            if (cfcEntity.getApmBplNo() != null && !cfcEntity.getApmBplNo().isEmpty()) {
                bpl = MainetConstants.FlagY;
            } else {
                bpl = MainetConstants.FlagN;
            }
            subCategoryDesc = MainetConstants.BLANK;

            for (final LookUp lookup : subCategryLookup) {
                if (lookup.getLookUpCode().equals("LF")) {
                    subCategoryDesc = lookup.getDescLangFirst();
                }
            }

            actualRate.setTaxSubCategory(subCategoryDesc);
            actualRate.setIsBPL(bpl);
            actualRate.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
            // actualRate.setBillingStartDate(new Date().getTime());
            // actualRate.setBillingEndDate(new Date().getTime());
            actualRate.setOrgId(orgId);
            actualRate.setServiceCode(serviceName);
            chargeModelList.add(actualRate);

            // }

        }
        requestDTO.setDataModel(chargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
        final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
        WaterRateMaster loiCharges = null;

        final Map<Long, Double> chargeMap = new HashMap<>();
        // List<Object> chargesList = null;
        Double baseRate = 0d;
        double amount;
        for (final Object rate : waterRateList) {
            loiCharges = (WaterRateMaster) rate;
            LookUp taxLookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(loiCharges.getTaxType(),
                    PrefixConstants.LookUp.FLAT_SLAB_DEPEND, 1, org);
            baseRate = WaterCommonUtility.getAndSetBaseRate(0.0, loiCharges, null, taxLookup.getLookUpCode());
            amount = chargeMap.containsKey(loiCharges.getTaxId()) ? chargeMap.get(loiCharges.getTaxId()) : 0;
            amount += baseRate;
            chargeMap.put(loiCharges.getTaxId(), amount);

        }

        /*
         * for (final Object rate : waterRateList) { chargesList = new ArrayList<>(); loiCharges = (WaterRateMaster) rate; //
         * chargesList.add(loiCharges.getDiffYr()); chargesList.add(loiCharges.getFlatRate()); //
         * chargesList.add(loiCharges.getTaxDescId()); chargeMap.put(loiCharges.getTaxId(), chargesList); }
         */
        return chargeMap;

    }

    @Override
    @Transactional
    public boolean updateValiDates(PlumberLicenseRequestDTO plumDto) {
        return plumberLicenseRepository.updateValiDates(plumDto);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getReceiptDetails(PlumberLicenseRequestDTO plumDto) {
        List<Object[]> reciptMas = null;
        try {
            reciptMas = plumberLicenseRepository.getReceiptDetails(plumDto);
            if (reciptMas != null) {
                Object[] receiptDet = reciptMas.get(0);
                if (receiptDet[0] != null) {
                    plumDto.setAmount(Double.valueOf(receiptDet[0].toString()));
                }
                if (receiptDet[1] != null) {
                    plumDto.setPlumberFullName(receiptDet[1].toString());
                }
                if (receiptDet[2] != null) {
                    plumDto.setApplicationId(Long.valueOf(receiptDet[2].toString()));
                }
                if (receiptDet[3] != null) {
                    plumDto.setServiceId(Long.valueOf(receiptDet[3].toString()));
                }
            }
        } catch (Exception e) {
            log.error("Exception ocours to getReceiptDetails() " + e);
            throw new FrameworkException("Exception occours in getReceiptDetails() method" + e);
        }

        return reciptMas;
    }

    @Override
    @Transactional
    public boolean updateLicNumber(PlumberLicenseRequestDTO plumDto) {
        return plumberLicenseRepository.updateLicNumber(plumDto);
    }

    @Transactional(readOnly = true)
    @Override
    public String getplumLicenseNo(Long applicationId) {
        return plumberLicenseRepository.getplumLicenseNo(applicationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getPlumMasId(Long applicationId) {
        return plumberLicenseRepository.getLicenseDetail(applicationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getApplicationNumByPlumId(Long plumId) {
        return plumberLicenseRepository.getApplicationNumByPlumId(plumId);
    }

    @Override
    @Transactional
    public boolean updatPlumberRenewalDates(PlumberLicenseRequestDTO plumDto) {
        return plumberLicenseRepository.updatPlumberRenewalDates(plumDto);

    }

    public ApplicantDetailDTO getApplicantDetails(Long applicationId, Long orgId) {
        TbCfcApplicationMstEntity cfcEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applicationId, orgId);
        ApplicantDetailDTO dto = new ApplicantDetailDTO();
        dto.setApplicantTitle(cfcEntity.getApmTitle());
        dto.setApplicantFirstName(cfcEntity.getApmFname());
        dto.setApplicantMiddleName(cfcEntity.getApmMname());
        dto.setApplicantLastName(cfcEntity.getApmLname());
        if (cfcEntity.getApmBplNo() != null) {
            dto.setBplNo(cfcEntity.getApmBplNo());
            dto.setIsBPL(MainetConstants.PlumberLicense.YES);
        } else {
            dto.setIsBPL(MainetConstants.PlumberLicense.NO);
        }
        if (cfcEntity.getApmUID() != null) {
            dto.setAadharNo(cfcEntity.getApmUID().toString());
        }
        CFCApplicationAddressEntity addressEntity = icfcApplicationMasterService.getApplicantsDetails(applicationId);
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

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "getPlumberApplicationDashBoardDetails", notes = "getPlumberApplicationDashBoardDetails", response = Object.class)
    @Path("/getPlumberApplicationDashBoardDetails/applicationId/{applicationId}/orgId/{orgId}")
    public PlumberLicenseRequestDTO getPlumberApplicationDashBoardDetails(
            @PathParam("applicationId") Long applicationId, @PathParam("orgId") Long orgId) {

        PlumberLicenseRequestDTO requestDto = new PlumberLicenseRequestDTO();
        try {
        	PlumberMaster plumberMasterDto = null;
        	 PlumberRenewalRegisterMaster plumberLicenceRenewalDetailsByAppId = plumberLicenseRepository.
             		getPlumberLicenceRenewalDetailsByAppId(applicationId, orgId);
             if(plumberLicenceRenewalDetailsByAppId!=null) {
            	  plumberMasterDto = plumberLicenseRepository.getPlumberDetailsByPlumId(
              			plumberLicenceRenewalDetailsByAppId.getPlum_id(), orgId);
             }else {
            	 plumberMasterDto = getPlumberDetailsByAppId(applicationId, orgId);
             }

	            if (plumberMasterDto != null) {
	                final List<PlumberQualificationDTO> qualificationList = getPlumberQualificationDetails(
	                        plumberMasterDto.getPlumId(), orgId);
	
	                if (qualificationList != null && !qualificationList.isEmpty()) {
	                    requestDto.setPlumberQualificationDTOList(qualificationList);
	                }
	                final List<PlumberExperienceDTO> experienceList = getPlumberExperienceDetails(
	                        plumberMasterDto.getPlumId(), orgId);
	                if (experienceList != null && !experienceList.isEmpty()) {
	                    requestDto.setPlumberExperienceDTOList(experienceList);
	                }
	                requestDto.setPlumberFName(plumberMasterDto.getPlumFname());
	                if (plumberMasterDto.getPlumMname() != null && !plumberMasterDto.getPlumMname().isEmpty()) {
	                    requestDto.setPlumberMName(plumberMasterDto.getPlumMname());
	                }
	
	                requestDto.setPlumberLName(plumberMasterDto.getPlumLname());
	                requestDto.setPlumberId(plumberMasterDto.getPlumId());
	                requestDto.setPlumContactNo(plumberMasterDto.getPlumContactNo());
	                requestDto.setPlumAddress(plumberMasterDto.getPlumAddress());
	
	                requestDto.setApplicant(getApplicantDetails(applicationId, orgId));
	            } else {
	                return null;
	            }
             
        } catch (Exception exception) {
            log.error("Error while fetching plumber details", exception);
            throw new FrameworkException("Error while fetching plumber details ", exception);
        }

        return requestDto;
    }

    @Override
    public String getRenewalValidateMsg(Long plumberId) {
        String msg = "";
        List<PlumberRenewalRegisterMaster> renewalRegister = plumberLicenseRepository.getPlumberRenewals(plumberId);
        if (!CollectionUtils.isEmpty(renewalRegister)) {
            PlumberRenewalRegisterMaster result = renewalRegister.get(0);
            if (result != null) {
                FinancialYear financialYear = iFinancialYearService.getFinanciaYearByDate(new Date());
                if (result.getRn_todate() != null) {
                    if (!Utility.compareDate(result.getRn_todate(), financialYear.getFaToDate())
                            || result.getRn_todate().compareTo(financialYear.getFaToDate()) == 0) {
                        msg = "Plumber Renewal Licence Expiry Date:" + " " + result.getRn_todate() + " "
                                + "You are not eligible to apply this service";
                    }
                } else {
                    msg = ApplicationSession.getInstance().getMessage("water.ren.plum.app") + " "
                            + result.getApm_application_id() + " " + ApplicationSession.getInstance().getMessage("water.ren.plum.under.process");
                }
            }
        }
        return msg;
    }

    @Override
    @POST
    @Path("/saveduplicateplumberlicense")
    public PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO requestDTO) {
        Organisation organisation = new Organisation();
        PlumberLicenseRequestDTO saveDuplicateData = saveDuplicatePlumberLicenseAndAttachFile(requestDTO);
        organisation.setOrgid(requestDTO.getOrgId());
        final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
        responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
        responseDTO.setApplicationId(saveDuplicateData.getApplicationId());
        if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(responseDTO.getStatus())) {
            initiateWorkFlowForFreeService(saveDuplicateData);
        }

        /*
         * ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); String fileName =
         * MainetConstants.PlumberJasperFiles.PLUM_APP.getColDescription(); // String jrxmlName = //
         * MainetConstants.PlumberJasperFiles.PLUM_SUB_APP_REPORT.getColDescription(); String menuURL =
         * MainetConstants.URLBasedOnShortCode.DPL.getUrl(); String type = PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED; final
         * String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR; Map oParms =
         * new HashMap(); oParms.put("plumber_id", saveDuplicateData.getPlumberId().toString()); oParms.put("SUBREPORT_DIR",
         * jrxmlFileLocation); oParms.put("apm_application_id", saveDuplicateData.getApplicationId().toString());
         * generateJasperReportPDF(saveDuplicateData, outputStream, oParms, fileName, menuURL, type);
         */
        /*
         * plumberLicenseService.sendSMSandEMail(requestDTO, saveDuplicateData.getApplicationId(), null,
         * MainetConstants.WaterServiceShortCode.DUPLICATE_PLUMBER_LICENSE, organisation);
         */ return responseDTO;
    }

    public PlumberLicenseRequestDTO saveDuplicatePlumberLicenseAndAttachFile(PlumberLicenseRequestDTO requestDTO) {

        final RequestDTO applicantDetailDTO = new RequestDTO();
        final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
        applicantDetailDTO.setTitleId(requestDTO.getApplicant().getApplicantTitle());
        applicantDetailDTO.setfName(requestDTO.getApplicant().getApplicantFirstName());
        applicantDetailDTO.setlName(requestDTO.getApplicant().getApplicantLastName());
        applicantDetailDTO.setMobileNo(requestDTO.getApplicant().getMobileNo());
        applicantDetailDTO.setEmail(requestDTO.getApplicant().getEmailId());
        applicantDetailDTO.setAreaName(requestDTO.getApplicant().getAreaName());
        applicantDetailDTO.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
        applicantDetailDTO.setServiceId(requestDTO.getServiceId());
        applicantDetailDTO.setDeptId(requestDTO.getDeptId());
        applicantDetailDTO.setUserId(requestDTO.getUserId());
        applicantDetailDTO.setOrgId(requestDTO.getOrgId());
        applicantDetailDTO.setLangId((long) requestDTO.getApplicant().getLangId());
        applicantDetailDTO.setReferenceId(requestDTO.getPlumberLicenceNo());
        if (requestDTO.getApplicant().getIsBPL().equals(MainetConstants.FlagY)) {
            applicantDetailDTO.setBplNo(requestDTO.getApplicant().getBplNo());
        }

        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgId());

        /*
         * final LookUp lookUp = CommonMasterUtility .getNonHierarchicalLookUpObject(Long.valueOf(requestDTO.getApplicant().
         * getGender()), organisation);
         */

        // applicantDetailDTO.setGender(lookUp.getLookUpCode());
        applicantDetailDTO.setCityName(requestDTO.getApplicant().getVillageTownSub());
        applicantDetailDTO.setBlockName(requestDTO.getApplicant().getBlockName());
        if ((null != requestDTO.getApplicant().getAadharNo())
                && !MainetConstants.BLANK.equals(requestDTO.getApplicant().getAadharNo())) {
            final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
            final Long aadhaarNo = Long.parseLong(aadhar);
            applicantDetailDTO.setUid(aadhaarNo);
        }
        if (null != requestDTO.getApplicant().getDwzid1()) {
            applicantDetailDTO.setZoneNo(requestDTO.getApplicant().getDwzid1());
        }
        if (null != requestDTO.getApplicant().getDwzid2()) {
            applicantDetailDTO.setWardNo(requestDTO.getApplicant().getDwzid2());
        }
        if (null != requestDTO.getApplicant().getDwzid3()) {
            applicantDetailDTO.setBlockNo(String.valueOf(requestDTO.getApplicant().getDwzid3()));
        }
        if (requestDTO.getAmount() == 0d) {
            applicantDetailDTO.setPayStatus("F");
        }
        try {
            final Long applicationId = applicationService.createApplication(applicantDetailDTO);
            applicantDetailDTO.setApplicationId(applicationId);

            responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                fileUploadService.doFileUpload(requestDTO.getDocumentList(), applicantDetailDTO);
            }
            responseDTO.setApplicationId(applicationId);
            requestDTO.setApplicationId(applicationId);
        } catch (Exception e) {
            log.warn("In saving new saveDuplicatePlumberLicenseAndAttachFile()", e);
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            throw new FrameworkException("Error while saveDuplicatePlumberLicenseAndAttachFile() ", e);
        }

        return requestDTO;
    }

    @Override
    public String generateJasperReportPDF(PlumberLicenseRequestDTO plumDto, ByteArrayOutputStream outputStream,
            Map oParms, String fileName, String menuURL, String type) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(plumDto.getOrgId());
        FileOutputStream fos = null;
        File someFile = null;
        String pdfNameGenarated = null;
        byte[] bytes = plumberLicenseRepository.generateJasperReportPDF(plumDto, outputStream, oParms, fileName);
        try {
            if (bytes.length > 1) {
                String genFilName = fileName.substring(0, fileName.length() - 6);
                pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
                        + MainetConstants.FILE_PATH_SEPARATOR + genFilName + "_" + +plumDto.getApplicationId() + "_"
                        + Utility.getTimestamp() + MainetConstants.PDF_EXTENSION;
                someFile = new File(pdfNameGenarated);

                fos = new FileOutputStream(someFile);
                fos.write(bytes);
                fos.flush();
                fos.close();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return someFile.getPath();
    }

	@Override
	public PlumberRenewalRegisterMaster getPlumberLicenceRenewalDetailsByAppId(Long applicationId, Long orgId) {
		try {
	        return plumberLicenseRepository.getPlumberLicenceRenewalDetailsByAppId(applicationId, orgId);
		}catch(Exception ex) {
            throw new FrameworkException("Plumber renewal licence not found for application id " + applicationId + " " + ex.getMessage());
		}
	}

	@Override
	public PlumberMaster getPlumberDetailsByPlumId(Long plumId, Long orgId) {
		try {
	        return plumberLicenseRepository.getPlumberDetailsByPlumId(plumId, orgId);
		}catch(Exception ex) {
            throw new FrameworkException("Plumber renewal licence not found for plumber id " + plumId + " " + ex.getMessage());
		}
	}
	
	public ApplicantDetailDTO getPlumberDetails(Long plumId, Long orgId) {
		 PlumberMaster plumberDetailsByPlumId = plumberLicenseRepository.getPlumberDetailsByPlumId(plumId, orgId);
		ApplicantDetailDTO dto = new ApplicantDetailDTO();
		//        CommonMasterUtility.findLookUpDesc("TTL", orgId, plumberDetailsByPlumId.getPlumCpdTitle());
		//        dto.setApplicantTitle(plumberDetailsByPlumId.getPlumCpdTitle()!=null ? );
		dto.setApplicantFirstName(plumberDetailsByPlumId.getPlumFname());
		dto.setApplicantMiddleName(plumberDetailsByPlumId.getPlumMname());
		 dto.setApplicantLastName(plumberDetailsByPlumId.getPlumLname());
		dto.setMobileNo(plumberDetailsByPlumId.getPlumContactNo());
		 dto.setAreaName(plumberDetailsByPlumId.getPlumAddress());
		 return dto;
	}
	
	 @Override
	    @Transactional(readOnly = true)
	    @WebMethod(exclude = true)
	    public Long getPlumberId(final long applicationId, final Long orgId) {
	        return plumbermasterrepository.getPlumberId(applicationId, orgId);
	    }

}
