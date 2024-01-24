package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TBCsmrrCmdMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterServiceMapper;
import com.abm.mainet.water.ui.validator.NewWaterConnectionFormValidator;

@Component
@Scope("session")
public class NewWaterConnectionFormModel extends AbstractFormModel {

    private static final long serialVersionUID = -7087904014932198341L;

    private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

    private TBCsmrrCmdMas csmrrCmd = new TBCsmrrCmdMas();

    private TbCfcApplicationMst cfcMasterDTO = new TbCfcApplicationMst();

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

    private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

    private String scrutinyFlag;

    private String serviceName;
    private String paymentMode;
    private String serviceDuration;
    private String propOutStanding;
    private String wtConnDueDt;

    @Autowired
    private NewWaterConnectionService waterService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private WaterServiceMapper waterMapper;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

    @Resource
    private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

    @Resource
    private WaterCommonService waterCommonService;

    private boolean scrutinyApplicable = false;

    private String plumberNo;

    private List<CFCAttachment> documentList = new ArrayList<>();

    private Long serviceId;

    private Long orgId;

    private Long deptId;

    private Long langId;

    private String noOfFamilyorUser;

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private Double charges = 0.0d;

    private String free = "O";

    private Map<Long, Double> chargesMap = new HashMap<>();

    private boolean isDocumentSubmitted;

    private NewWaterConnectionReqDTO reqDTO = new NewWaterConnectionReqDTO();

    private NewWaterConnectionResponseDTO responseDTO = new NewWaterConnectionResponseDTO();

    private List<ChargeDetailDTO> chargesInfo;

    private int rowCount;

    private boolean authView;

    private String saveMode = MainetConstants.NewWaterServiceConstants.NO;

    private String isBillingSame = MainetConstants.NewWaterServiceConstants.YES;

    private String isConsumerSame = MainetConstants.NewWaterServiceConstants.NO;

    private List<DocumentDetailsVO> checkListForPreview = new ArrayList<>();
    public List<PlumberMaster> plumberList = new ArrayList<>();
    public List<PlumberMaster> ulbPlumberList = new ArrayList<>();

    @Resource
    private IChallanService iChallanService;
    @Autowired
    private NewWaterConnectionService newWaterConnectionService;

    private ServiceMaster serviceMaster = new ServiceMaster();

    private String propNoOptionalFlag;
    private Long applicationId;
    private String applicantName;
    
    private boolean propDuesCheck;
    
    private WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO = new WaterApplicationAcknowledgementDTO();
    
    private String isWithoutProp;
    
    private ProvisionalCertificateDTO provisionCertificateDTO;
    
    private String sudaEnv;
    
    private String showScrutinyButton;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Override
    public void populateApplicationData(final long applicationId) {
        setApmApplicationId(applicationId);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        setOrgId(orgId);

        final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
        final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
        csmrInfo = waterCommonService.getApplicantInformationById(applicationId, orgId);
        NewWaterConnectionReqDTO reqDto = getReqDTO();
        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        reqDto.setPropertyNo(csmrInfo.getPropertyNo());
        TbCsmrInfoDTO propInfoDTO = getPropertyDetailsByPropertyNumber(reqDto);
        if (propInfoDTO != null) {
            csmrInfo.setTotalOutsatandingAmt(propInfoDTO.getTotalOutsatandingAmt());
        }
        // D#123484
        if (csmrInfo.getCsGender() != null && csmrInfo.getCsGender() > 0) {
            csmrInfo.setGenderDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(csmrInfo.getCsGender(), UserSession.getCurrent().getOrganisation())
                    .getDescLangFirst());
        }
        for (final AdditionalOwnerInfoDTO owner : csmrInfo.getOwnerList()) {
            if ((owner.getIsDeleted() != null)
                    && !owner.getIsDeleted().equals(PrefixConstants.NewWaterServiceConstants.YES)
                    && (owner.getCaoNewTitle() != null)) {
                if (owner.getOwnerFirstName() == null) {
                    owner.setOwnerFirstName(owner.getCaoNewFName());
                }
                if (owner.getOwnerMiddleName() == null) {
                    owner.setOwnerMiddleName(owner.getCaoNewMName());
                }
                if (owner.getOwnerLastName() == null) {
                    owner.setOwnerLastName(owner.getCaoNewLName());
                }
                if ((owner.getOwnerTitle() == null) && (owner.getCaoNewTitle() != null)) {
                    owner.setOwnerTitle(owner.getCaoNewTitle().toString());
                }
                if (owner.getGender() == null) {
                    owner.setGender(owner.getCaoNewGender());
                }
                if (owner.getCao_id() == null) {
                    owner.setCaoUID(owner.getCaoNewUID());
                }
                tempOwnerList.add(owner);
            }
        }
        for (final TbKLinkCcnDTO link : csmrInfo.getLinkDetails()) {
            if ((link.getIsDeleted() != null)
                    && !link.getIsDeleted().equals(PrefixConstants.NewWaterServiceConstants.YES)) {
                tempLinkList.add(link);
                getReqDTO().setExistingConsumerNumber("Y");
            }

        }
        csmrInfo.setOwnerList(tempOwnerList);
        csmrInfo.setLinkDetails(tempLinkList);
        if (tempLinkList.isEmpty())
            getReqDTO().setExistingConsumerNumber("N");
        documentList = iChecklistVerificationService.getDocumentUploaded(applicationId, orgId);
        setPlumberData(csmrInfo);
        cfcAddressEntity = cfcService.getApplicantsDetails(applicationId);
        cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
        final ServiceMaster service = cfcEntity.getTbServicesMst();
        if ((service.getSmScrutinyApplicableFlag() != null)
                && service.getSmScrutinyApplicableFlag().equals(PrefixConstants.NewWaterServiceConstants.YES)) {
            setScrutinyApplicable(true);
        }

        ApplicantDetailDTO applicant = getApplicantDetailDto();
        applicant.setApplicantFirstName(cfcEntity.getApmFname());
        applicant.setApplicantMiddleName(cfcEntity.getApmMname());
        applicant.setApplicantLastName(cfcEntity.getApmLname());
        applicant.setApplicantTitle(cfcEntity.getApmTitle());
        applicant.setMobileNo(cfcAddressEntity.getApaMobilno());
        applicant.setEmailId(cfcAddressEntity.getApaEmail());
        // D#123484
        if (cfcEntity.getApmSex() != null) {
            LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(cfcEntity.getApmSex(), MainetConstants.GENDER,
                    UserSession.getCurrent().getOrganisation());
            if (lookUp != null && lookUp.getDescLangFirst() != null) {
                applicant.setGender(lookUp.getDescLangFirst());
            }
        }
        List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (final LookUp lookUp : tarrifCategoryLookUps) {
			if ((csmrInfo.getTrmGroup1() != null) && csmrInfo.getTrmGroup1() != 0l) {
				if (lookUp.getLookUpId() == csmrInfo.getTrmGroup1()) {
					getReqDTO().setTarrifCategory(lookUp.getDescLangFirst());
					break;
				}			
			}
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			 tarrifCategoryLookUps = CommonMasterUtility.getNextLevelData("TRF", 2,
						UserSession.getCurrent().getOrganisation().getOrgid());
				for (final LookUp lookUp : tarrifCategoryLookUps) {
					if ((csmrInfo.getTrmGroup2() != null) && csmrInfo.getTrmGroup2() != 0l) {
						if (lookUp.getLookUpId() == csmrInfo.getTrmGroup2()) {
							getReqDTO().setSubCategory(lookUp.getDescLangFirst());
							break;
						}
					}
				}
			}
		
		final List<LookUp> connsectionSizeLookUps = CommonMasterUtility.getLookUps("CSZ",
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : connsectionSizeLookUps) {
			if ((csmrInfo.getCsCcnsize() != null) && csmrInfo.getCsCcnsize() != 0l) {
				if (lookUp.getLookUpId() == csmrInfo.getCsCcnsize()) {
					getReqDTO().setConnSize(lookUp.getLookUpDesc());
					break;
				}
			}
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
		final List<LookUp> lookUpDis = CommonMasterUtility.getLookUps("DIS", UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUpDis) {
			if (csmrInfo.getCsDistrict() != 0l) {
				if (lookUp.getLookUpId() == csmrInfo.getCsDistrict()) {
					csmrInfo.setCsDistrictDesc(lookUp.getLookUpDesc());
					break;
				}
			}

		}
		final List<LookUp> categoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (final LookUp lookUp : categoryLookUps) {
			if ((csmrInfo.getCodDwzid1() != null) && csmrInfo.getCodDwzid1() != 0l) {
				if (lookUp.getLookUpId() == csmrInfo.getCsCcncategory1()) {
					getReqDTO().setCategory(lookUp.getDescLangFirst());
					if("T".equals(lookUp.getLookUpCode()) )
						csmrInfo.setTypeOfApplication(lookUp.getLookUpCode());
					break;
				}
			}
		  
		 }
		final List<LookUp> subCategoryLookUps = CommonMasterUtility.getNextLevelData("CCG", 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		for (final LookUp lookUp : subCategoryLookUps) {
			if ((csmrInfo.getCodDwzid1() != null) && csmrInfo.getCodDwzid1() != 0l) {
				if (lookUp.getLookUpId() == csmrInfo.getCsCcncategory2()) {
					getReqDTO().setSubCategory(lookUp.getDescLangFirst());
					break;
				}
			}

		}
		}
		
        applicant.setFlatBuildingNo(cfcAddressEntity.getApaFlatBuildingNo());
        applicant.setBuildingName(cfcAddressEntity.getApaBldgnm());
        applicant.setAreaName(cfcAddressEntity.getApaAreanm());
        applicant.setRoadName(cfcAddressEntity.getApaRoadnm());
        applicant.setVillageTownSub(cfcAddressEntity.getApaCityName());
        applicant.setPinCode(cfcAddressEntity.getApaPincode().toString());

        if (cfcEntity.getApmUID() != null)
            applicant.setAadharNo(cfcEntity.getApmUID().toString());
        applicant.setBplNo(cfcEntity.getApmBplNo());
        if (applicant.getBplNo() != null && !applicant.getBplNo().isEmpty()) {
            applicant.setIsBPL("Y");
        } else {
            applicant.setIsBPL("N");
        }
        applicant.setDwzid1(csmrInfo.getCodDwzid1());
        applicant.setDwzid2(csmrInfo.getCodDwzid2());
        applicant.setDwzid3(csmrInfo.getCodDwzid3());
        applicant.setDwzid4(csmrInfo.getCodDwzid4());
        applicant.setDwzid5(csmrInfo.getCodDwzid5());

        setScrutinyFlag(MainetConstants.FlagY);
        if(StringUtils.isNotBlank(csmrInfo.getNocAppl())) {
        	setShowScrutinyButton(MainetConstants.FlagY);
        }else {
        	setShowScrutinyButton(MainetConstants.FlagN);
        }
        setAuthView(true);
    }

    @Override
    public String getConnectioNo(final long applicationid, final long serviceid) {

        final String connectionString = waterService.setConnectionNoDetails(applicationid,
                UserSession.getCurrent().getOrganisation().getOrgid(), serviceid,
                UserSession.getCurrent().getEmployee().getEmpId());
        return connectionString;
    }

    /**
     * @param csmrInfo2
     */
    private void setPropertyData(final TbCsmrInfoDTO csmrInfo) {
        if (!StringUtils.isEmpty(csmrInfo.getPropertyNo())) {
            getReqDTO().setPropertyNo(csmrInfo.getPropertyNo());
            Map<String, String> propData = waterCommonService.validateAndFetchProperty(csmrInfo.getPropertyNo(),
                    getOrgId());
            if (propData != null && !propData.isEmpty()) {
                getReqDTO().setPropertyOutStanding(
                        propData.get(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_AMOUNT));
            }
            getReqDTO().setExistingPropertyNo("ExistingProperty");
        } else {
            getReqDTO().setExistingPropertyNo(null);
        }
    }

    /**
     * @param csmrInfo2
     */
    private void setPlumberData(final TbCsmrInfoDTO csmrInfo) {
        if (csmrInfo.getPlumId() != null) {
            final PlumberMaster plumber = waterService.getPlumberByPlumberNo(String.valueOf(csmrInfo.getPlumId()));
            if (plumber != null) {
                setPlumberNo(plumber.getPlumLicNo());
                getReqDTO().setIsULBRegisterd("N");
                getReqDTO().setPlumberName(plumber.getPlumLicNo());
            }
            else
            	getReqDTO().setPlumberName(MainetConstants.NewWaterServiceConstants.ANY_PLUMBER);
        } else {
            getReqDTO().setIsULBRegisterd("Y");
            
        }
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {

        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            return "csmrInfo.trmGroup";

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            if (isAuthView()) {
                return "csmrInfo.codDwzid";
            } else {
                return "applicantDetailDto.dwzid";
            }
        case PrefixConstants.WATERMODULEPREFIX.CSZ:
            return "csmrInfo.csCcnsize";

        case PrefixConstants.WATERMODULEPREFIX.CCG:
            return "csmrInfo.csCcncategory";

        }
        return null;
    }

    @Override
    public boolean saveForm() {
        final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
        csmrInfo.setPropertyNo(getReqDTO().getPropertyNo());
        final TbCsmrInfoDTO savedMaster = waterCommonService
                .getApplicantInformationById(getCsmrInfo().getApplicationNo(), getCsmrInfo().getOrgId());
        final List<AdditionalOwnerInfoDTO> existingOwnerList = savedMaster.getOwnerList();
        final List<TbKLinkCcnDTO> existingLinkDetails = savedMaster.getLinkDetails();
        final List<AdditionalOwnerInfoDTO> updatedOwnerList = getCsmrInfo().getOwnerList();
        final List<TbKLinkCcnDTO> updatedLinkDetails = getCsmrInfo().getLinkDetails();
        final List<TbKLinkCcnDTO> tempList = new ArrayList<>();
        updateAuditFields(csmrInfo);
        if (existingOwnerList != null && !existingOwnerList.isEmpty()) {
            for (final AdditionalOwnerInfoDTO owner : existingOwnerList) {
                owner.setIsDeleted(PrefixConstants.NewWaterServiceConstants.YES);
                owner.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                owner.setUpdatedDate(new Date());
            }
        }
        if (getReqDTO().getExistingConsumerNumber() != null) {
            if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
                for (final TbKLinkCcnDTO link : existingLinkDetails) {

                    if (!link.getLcOldccn().isEmpty()) {
                        boolean flag = false;
                        if (updatedLinkDetails != null && !updatedLinkDetails.isEmpty()) {
                            for (final TbKLinkCcnDTO link1 : updatedLinkDetails) {
                                if (link1.getLcOldccn().equals(link.getLcOldccn())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.YES);
                                link.setUpdatedBy(UserSession.getCurrent().getEmployee());
                                link.setUpdatedDate(new Date());
                                tempList.add(link);
                            }
                        }

                    }
                }

            }
            if (updatedLinkDetails != null && !updatedLinkDetails.isEmpty()) {
                for (final TbKLinkCcnDTO link : updatedLinkDetails) {
                    if (!link.getLcOldccn().isEmpty()) {
                        boolean flag = false;
                        if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
                            for (final TbKLinkCcnDTO link1 : existingLinkDetails) {
                                if (link1.getLcOldccn().equals(link.getLcOldccn())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                if (link.getIsDeleted() != null)
                                    link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
                                link.setUpdatedBy(UserSession.getCurrent().getEmployee());
                                link.setUpdatedDate(new Date());
                                tempList.add(link);
                            }
                        } else {
                            link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
                            link.setUpdatedBy(UserSession.getCurrent().getEmployee());
                            link.setUpdatedDate(new Date());
                            tempList.add(link);
                        }

                    }

                }
            }
        } else {
            if (existingLinkDetails != null && !existingLinkDetails.isEmpty()) {
                for (final TbKLinkCcnDTO link : existingLinkDetails) {
                    link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.YES);
                    link.setUpdatedBy(UserSession.getCurrent().getEmployee());
                    link.setUpdatedDate(new Date());
                }
            }
        }
        updatedOwnerList.addAll(existingOwnerList);
        tempList.addAll(existingLinkDetails);
        getCsmrInfo().setLinkDetails(tempList);
        getCsmrInfo().setOwnerList(updatedOwnerList);
        setApplicantDetails();
        final TbKCsmrInfoMH master = waterMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
        if (scrutinyApplicable) {
            waterCommonService.updateCsmrInfo(master, null);
        } else {
            final String connectionNo = waterService.generateWaterConnectionNumber(master.getApplicationNo(),
                    getServiceId(), getOrgId(), master);
            master.setCsCcn(connectionNo);
            waterCommonService.updateCsmrInfo(master, null);
        }
        tbCfcApplicationAddressJpaRepository.save(getCfcAddressEntity());
        tbCfcApplicationMstJpaRepository.save(getCfcEntity());

        final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
        final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
        for (final AdditionalOwnerInfoDTO owner : csmrInfo.getOwnerList()) {
            if ((owner.getIsDeleted() != null)
                    && !owner.getIsDeleted().equals(PrefixConstants.NewWaterServiceConstants.YES)
                    && (owner.getCaoNewTitle() != null)) {
                if (owner.getOwnerFirstName() == null) {
                    owner.setOwnerFirstName(owner.getCaoNewFName());
                }
                if (owner.getOwnerMiddleName() == null) {
                    owner.setOwnerMiddleName(owner.getCaoNewMName());
                }
                if (owner.getOwnerLastName() == null) {
                    owner.setOwnerLastName(owner.getCaoNewLName());
                }
                if ((owner.getOwnerTitle() == null) && (owner.getCaoNewTitle() != null)) {
                    owner.setOwnerTitle(owner.getCaoNewTitle().toString());
                }
                if (owner.getGender() == null) {
                    owner.setGender(owner.getCaoNewGender());
                }
                if (owner.getCao_id() == null) {
                    owner.setCaoUID(owner.getCaoNewUID());
                }
                tempOwnerList.add(owner);
            }
        }
        for (final TbKLinkCcnDTO link : csmrInfo.getLinkDetails()) {
            if ((link.getIsDeleted() != null)
                    && !link.getIsDeleted().equals(PrefixConstants.NewWaterServiceConstants.YES)) {
                tempLinkList.add(link);
            }

        }

        getCsmrInfo().setLinkDetails(tempList);
        getCsmrInfo().setOwnerList(tempOwnerList);

        return false;
    }

    /**
     * 
     */
    private void setApplicantDetails() {
        final TbCsmrInfoDTO master = getCsmrInfo();
        final TbCfcApplicationMstEntity cfcApplicationMst = getCfcEntity();
        final CFCApplicationAddressEntity cfcAddress = getCfcAddressEntity();
        ApplicantDetailDTO applicant = getApplicantDetailDto();
        cfcApplicationMst.setApmFname(applicant.getApplicantFirstName());
        cfcApplicationMst.setApmMname(applicant.getApplicantMiddleName());
        cfcApplicationMst.setApmLname(applicant.getApplicantLastName());
        cfcApplicationMst.setApmTitle(applicant.getApplicantTitle());
        cfcAddress.setApaMobilno(applicant.getMobileNo());
        cfcAddress.setApaEmail(applicant.getEmailId());
        cfcAddress.setApaFlatBuildingNo(applicant.getFlatBuildingNo());
        cfcAddress.setApaBldgnm(applicant.getBuildingName());
        cfcAddress.setApaAreanm(applicant.getAreaName());
        cfcAddress.setApaRoadnm(applicant.getRoadName());
        if (applicant.getAadharNo() != null && !applicant.getAadharNo().isEmpty()) {
            cfcApplicationMst.setApmUID(Long.valueOf(applicant.getAadharNo()));
            master.setCsUid(Long.valueOf(applicant.getAadharNo()));
        }
        cfcApplicationMst.setApmBplNo(applicant.getBplNo());
        cfcAddress.setApaZoneNo(applicant.getDwzid1());
        cfcAddress.setApaWardNo(applicant.getDwzid2());

        master.setCodDwzid1(applicant.getDwzid1());
        master.setCodDwzid2(applicant.getDwzid2());
        master.setCodDwzid3(applicant.getDwzid3());
        master.setCodDwzid4(applicant.getDwzid4());
        master.setCodDwzid5(applicant.getDwzid5());

        master.setBplFlag(applicant.getIsBPL());
        master.setBplNo(applicant.getBplNo());

    }

    /**
     * @param csmrInfo2
     */
    private void updateAuditFields(final TbCsmrInfoDTO master) {

        if (master.getOwnerList() != null && !master.getOwnerList().isEmpty()) {
            for (final AdditionalOwnerInfoDTO owner : master.getOwnerList()) {
                owner.setOrgid(getOrgId());
                owner.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                owner.setLangId((long) UserSession.getCurrent().getLanguageId());
                owner.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                owner.setLmoddate(new Date());
                owner.setCsIdn(master);
                owner.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);

            }
        }
        if (master.getLinkDetails() != null && !master.getLinkDetails().isEmpty()) {
            for (final TbKLinkCcnDTO link : master.getLinkDetails()) {
                link.setLcId(0);
                link.setOrgIds(getOrgId());
                link.setUserIds(UserSession.getCurrent().getEmployee().getEmpId());
                link.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                link.setCsIdn(master);
                link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);

            }
        }

    }

    public boolean save() {

        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        getReqDTO().setPayMode(offline.getOnlineOfflineCheck());
        if (((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO))
                || (getCharges() > 0d)) {
            offline.setApplNo(responseDTO.getApplicationNo());
            offline.setAmountToPay(Double.toString(getCharges()));
            offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            offline.setLangId(UserSession.getCurrent().getLanguageId());
            offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
            if ((getCheckList() != null) && (getCheckList().size() > 0)) {
                offline.setDocumentUploaded(true);
            } else {
                offline.setDocumentUploaded(false);
            }
            offline.setPlotNo(getReqDTO().getApplicantDTO().getHouseNumber());
            offline.setReferenceNo(getCsmrInfo().getPtin());
            offline.setApplicantAddress(getCsmrInfo().getCsAdd());
            offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
            offline.setFaYearId(UserSession.getCurrent().getFinYearId());
            offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
            offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
            offline.setServiceId(getServiceId());
            offline.setApplicantName(getApplicantDetailDto().getApplicantFirstName());
            offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
        	StringBuilder applicantName = new StringBuilder();
        	applicantName.append(getApplicantDetailDto().getApplicantFirstName());
        	if (StringUtils.isNotBlank(getApplicantDetailDto().getApplicantMiddleName())) {
        		applicantName.append(MainetConstants.WHITE_SPACE);
        		applicantName.append(getApplicantDetailDto().getApplicantMiddleName());
        	}
        	if (StringUtils.isNotBlank(getApplicantDetailDto().getApplicantLastName())) {
        		applicantName.append(MainetConstants.WHITE_SPACE);
        		applicantName.append(getApplicantDetailDto().getApplicantLastName());
        	}
        	offline.setApplicantFullName(applicantName.toString());
            offline.setEmailId(getApplicantDetailDto().getEmailId());
            for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
                offline.getFeeIds().put(entry.getKey(), entry.getValue());
            }
            offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
            offline.setDeptId(getDeptId());
            offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                    offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
            if (offline.getPayModeIn() != null && offline.getPayModeIn() != 0) {
                setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getPayModeIn(),
                        UserSession.getCurrent().getOrganisation()).getLookUpDesc());
            } else {
                setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
                        UserSession.getCurrent().getOrganisation()).getLookUpDesc());
            }
            if ((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
                final ChallanMaster challanNumber = iChallanService.InvokeGenerateChallan(offline);
                offline.setChallanNo(challanNumber.getChallanNo());
                offline.setChallanValidDate(challanNumber.getChallanValiDate());
                setOfflineDTO(offline);
            } else if ((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
                final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
                setReceiptDTO(printDto);
                setSuccessMessage(getAppSession().getMessage("water.receipt"));
            }
        }
        return true;
    }

    public boolean validateInputs() {
        // validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
        validateBean(this, NewWaterConnectionFormValidator.class);
        if (getSaveMode().equals(MainetConstants.NewWaterServiceConstants.YES)) {
            if (MainetConstants.NewWaterServiceConstants.NO.equals(getFree())) {
                validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
            }
        }
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    /**
     * @return the csmrrCmd
     */
    public TBCsmrrCmdMas getCsmrrCmd() {
        return csmrrCmd;
    }

    /**
     * @param csmrrCmd the csmrrCmd to set
     */
    public void setCsmrrCmd(final TBCsmrrCmdMas csmrrCmd) {
        this.csmrrCmd = csmrrCmd;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public TbCsmrInfoDTO getCsmrInfo() {
        return csmrInfo;
    }

    public void setCsmrInfo(final TbCsmrInfoDTO csmrInfo) {
        this.csmrInfo = csmrInfo;
    }

    public TbCfcApplicationMst getCfcMasterDTO() {
        return cfcMasterDTO;
    }

    public void setCfcMasterDTO(final TbCfcApplicationMst cfcMasterDTO) {
        this.cfcMasterDTO = cfcMasterDTO;
    }

    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
    }

    public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
    }

    public ICFCApplicationMasterService getCfcService() {
        return cfcService;
    }

    public void setCfcService(final ICFCApplicationMasterService cfcService) {
        this.cfcService = cfcService;
    }

    public boolean isScrutinyApplicable() {
        return scrutinyApplicable;
    }

    public void setScrutinyApplicable(final boolean scrutinyApplicable) {
        this.scrutinyApplicable = scrutinyApplicable;
    }

    public String getPlumberNo() {
        return plumberNo;
    }

    public void setPlumberNo(final String plumberNo) {
        this.plumberNo = plumberNo;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(final List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public String getNoOfFamilyorUser() {
        return noOfFamilyorUser;
    }

    public void setNoOfFamilyorUser(final String noOfFamilyorUser) {
        this.noOfFamilyorUser = noOfFamilyorUser;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(final List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(final Double charges) {
        this.charges = charges;
    }

    /**
     * @return the free
     */
    public String getFree() {
        return free;
    }

    /**
     * @param free the free to set
     */
    public void setFree(final String free) {
        this.free = free;
    }

    public Map<Long, Double> getChargesMap() {
        return chargesMap;
    }

    public void setChargesMap(final Map<Long, Double> chargesMap) {
        this.chargesMap = chargesMap;
    }

    public boolean isDocumentSubmitted() {
        return isDocumentSubmitted;
    }

    public void setDocumentSubmitted(final boolean isDocumentSubmitted) {
        this.isDocumentSubmitted = isDocumentSubmitted;
    }

    public NewWaterConnectionReqDTO getReqDTO() {
        return reqDTO;
    }

    public void setReqDTO(final NewWaterConnectionReqDTO reqDTO) {
        this.reqDTO = reqDTO;
    }

    @Override
    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    @Override
    public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public NewWaterConnectionResponseDTO getResponseDTO() {
        return responseDTO;
    }

    public void setResponseDTO(final NewWaterConnectionResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(final ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    /**
     * @return the rowCount
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    @Override
    public void setRowCount(final int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return the authView
     */
    public boolean isAuthView() {
        return authView;
    }

    /**
     * @param authView the authView to set
     */
    public void setAuthView(final boolean authView) {
        this.authView = authView;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(final String saveMode) {
        this.saveMode = saveMode;
    }

    public String getIsBillingSame() {
        return isBillingSame;
    }

    public void setIsBillingSame(final String isBillingSame) {
        this.isBillingSame = isBillingSame;
    }

    public String getIsConsumerSame() {
        return isConsumerSame;
    }

    public void setIsConsumerSame(String isConsumerSame) {
        this.isConsumerSame = isConsumerSame;
    }

    public List<DocumentDetailsVO> getCheckListForPreview() {
        return checkListForPreview;
    }

    public void setCheckListForPreview(List<DocumentDetailsVO> checkListForPreview) {
        this.checkListForPreview = checkListForPreview;
    }

    public String getScrutinyFlag() {
        return scrutinyFlag;
    }

    public void setScrutinyFlag(String scrutinyFlag) {
        this.scrutinyFlag = scrutinyFlag;
    }

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
    	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
    		 return newWaterConnectionService.getPropertyDetailsByPropertyNumberNFlatNo(requestDTO);
    	}else {
    		 return newWaterConnectionService.getPropertyDetailsByPropertyNumber(requestDTO);
    	}       
    }

    public List<PlumberMaster> getPlumberList() {
        return plumberList;
    }

    public void setPlumberList(List<PlumberMaster> plumberList) {
        this.plumberList = plumberList;
    }

    public List<PlumberMaster> getUlbPlumberList() {
        return ulbPlumberList;
    }

    public void setUlbPlumberList(List<PlumberMaster> ulbPlumberList) {
        this.ulbPlumberList = ulbPlumberList;
    }

    public String getPropOutStanding() {
        return propOutStanding;
    }

    public void setPropOutStanding(String propOutStanding) {
        this.propOutStanding = propOutStanding;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPropNoOptionalFlag() {
        return propNoOptionalFlag;
    }

    public void setPropNoOptionalFlag(String propNoOptionalFlag) {
        this.propNoOptionalFlag = propNoOptionalFlag;
    }

	public String getWtConnDueDt() {
		return wtConnDueDt;
	}

	public void setWtConnDueDt(String wtConnDueDt) {
		this.wtConnDueDt = wtConnDueDt;
	}

	public boolean isPropDuesCheck() {
		return propDuesCheck;
	}

	public void setPropDuesCheck(boolean propDuesCheck) {
		this.propDuesCheck = propDuesCheck;
	}

	public WaterApplicationAcknowledgementDTO getWaterApplicationAcknowledgementDTO() {
		return waterApplicationAcknowledgementDTO;
	}

	public void setWaterApplicationAcknowledgementDTO(
			WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO) {
		this.waterApplicationAcknowledgementDTO = waterApplicationAcknowledgementDTO;
	}

	public String getIsWithoutProp() {
		return isWithoutProp;
	}

	public void setIsWithoutProp(String isWithoutProp) {
		this.isWithoutProp = isWithoutProp;
	}

	public ProvisionalCertificateDTO getProvisionCertificateDTO() {
		return provisionCertificateDTO;
	}

	public void setProvisionCertificateDTO(ProvisionalCertificateDTO provisionCertificateDTO) {
		this.provisionCertificateDTO = provisionCertificateDTO;
	}

	public String getSudaEnv() {
		return sudaEnv;
	}

	public void setSudaEnv(String sudaEnv) {
		this.sudaEnv = sudaEnv;
	}

	public String getShowScrutinyButton() {
		return showScrutinyButton;
	}

	public void setShowScrutinyButton(String showScrutinyButton) {
		this.showScrutinyButton = showScrutinyButton;
	}
	

}
