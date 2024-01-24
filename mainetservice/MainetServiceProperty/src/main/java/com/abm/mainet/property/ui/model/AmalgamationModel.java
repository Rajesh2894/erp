package com.abm.mainet.property.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.property.dto.AmalgamationDto;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AmalgamationService;
import com.abm.mainet.property.service.PropertyAuthorizationService;

@Component
@Scope("session")
public class AmalgamationModel extends AbstractFormModel {
    @Autowired
    private AmalgamationService amalgamationService;

    @Resource
    private IFileUploadService fileUpload;

    @Resource
    private CommonService commonService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    private static final long serialVersionUID = 1L;

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;// Main DTO to Bind Data
    private ProvisionalAssesmentMstDto amalParentPropDto = null;// Main DTO to Bind Data
    private AmalgamationDto amalgamationDto = new AmalgamationDto();
    private List<ProvisionalAssesmentMstDto> ProvisionalAssesmentMstDtoList = new ArrayList<>();
    private List<TbBillMas> billMasList = new LinkedList<>();
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private Integer ownerDetailTableCount;
    private Date leastFinYear;// least Date of Fin year in System
    private String assType;// Assessment type Amalgamation
    private String assMethod;
    private Long deptId;
    private String authEditFlag;
    private List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = new LinkedList<>();// Factor DTO to Bind Factor separately
    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private List<CFCAttachment> documentList = new ArrayList<>();
    private List<Long> finYearList = new ArrayList<>(0);

    private List<LookUp> location = new ArrayList<>(0);

    private Map<Long, String> financialYearMap = new LinkedHashMap<>();

    private List<BillReceiptPostingDTO> reabteRecDtoList = new ArrayList<>();

    private String actionURL;

    private boolean lastChecker;

    private String intgrtionWithBP;

    private Long orgId;

    private String proAssPropAddCheck;

    /* For Land details integration with revenue department */

    private ArrayOfKhasraDetails arrayOfKhasraDetails = new ArrayOfKhasraDetails();

    private ArrayOfPlotDetails arrayOfPlotDetails = new ArrayOfPlotDetails();

    private ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails = new ArrayOfDiversionPlotDetails();

    private List<LookUp> district = new ArrayList<>(0);

    private List<LookUp> tehsil = new ArrayList<>(0);

    private List<LookUp> village = new ArrayList<>(0);

    private List<LookUp> khasraNo = new ArrayList<>(0);

    private List<LookUp> plotNo = new ArrayList<>(0);

    private List<LookUp> mohalla = new ArrayList<>(0);

    private List<LookUp> blockStreet = new ArrayList<>(0);

    private String landTypePrefix;

    private String knownKhaNo;

    private String enteredKhasraNo;

    private String enteredPlotNo;

    private String serviceShortCode;

    public void setDropDownValues() {

        Organisation org = UserSession.getCurrent().getOrganisation();
        this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());

        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
        this.setAssMethod(CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS).getLookUpCode());
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()) {
                if (dto.getGenderId() != null) {
                    dto.setProAssGenderId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
                    dto.setProAssRelationId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org).getDescLangFirst());
                }
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        if (getProvisionalAssesmentMstDto().getAssAcqDate() != null)
            this.getProvisionalAssesmentMstDto()
                    .setProAssAcqDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssAcqDate()));
        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
            }
        });

        this.getDistrict().forEach(dis -> {
            if (Long.valueOf(dis.getLookUpId()).toString().equals(provisionalAssesmentMstDto.getAssDistrict())) {
                this.getProvisionalAssesmentMstDto().setAssDistrictDesc(dis.getDescLangFirst());
            }
        });

        this.getTehsil().forEach(teh -> {
            if (teh.getLookUpCode().equals(provisionalAssesmentMstDto.getAssTahasil())) {
                this.getProvisionalAssesmentMstDto().setAssTahasilDesc(teh.getDescLangFirst());
            }
        });

        this.getVillage().forEach(vil -> {
            if (vil.getLookUpCode().equals(provisionalAssesmentMstDto.getTppVillageMauja())) {
                this.getProvisionalAssesmentMstDto().setTppVillageMaujaDesc(vil.getDescLangFirst());
            }
        });

        for (LookUp moh : this.getMohalla()) {
            if (moh.getLookUpCode().equals(provisionalAssesmentMstDto.getMohalla())) {
                this.getProvisionalAssesmentMstDto().setMohallaDesc(moh.getDescLangFirst());
                break;
            }
        }

        for (LookUp sheet : this.getBlockStreet()) {
            if (sheet.getLookUpCode().equals(provisionalAssesmentMstDto.getAssStreetNo())) {
                this.getProvisionalAssesmentMstDto().setAssStreetNoDesc(sheet.getDescLangFirst());
                break;
            }
        }
        /*
         * this.getProvisionalAssesmentMstDto()
         * .setProAssPropType(CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1(),
         * UserSession.getCurrent().getOrganisation()).getDescLangFirst());
         */
		if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setProAssdRoadfactorDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
							.getDescLangFirst());
		}

        if (provisionalAssesmentMstDto.getAssLandType() != null) {
            this.getProvisionalAssesmentMstDto().setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType(), org).getDescLangFirst());
        }

        this.getProvisionalAssesmentMstDto().setAssWardDesc1(
                CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());

        if (provisionalAssesmentMstDto.getAssWard2() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc2(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard3() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc3(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard4() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc4(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard5() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc5(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getDescLangFirst());
        }
		if (provisionalAssesmentMstDto.getAssPropType1() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType1Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType2() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType2Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType2(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType3() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType3Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType3(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType4() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType4Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType4(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssPropType5() != null) {
			this.getProvisionalAssesmentMstDto().setAssPropType5Desc(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType5(), org).getDescLangFirst());
		}

        for (ProvisionalAssesmentDetailDto detaildto : this.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {

                for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                }

                detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                detaildto.setProAssdUsagetypeDesc(
                        CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getDescLangFirst());

                if (detaildto.getAssdUsagetype2() != null) {
                    detaildto.setProAssdUsagetypeDesc2(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype3() != null) {
                    detaildto.setProAssdUsagetypeDesc3(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype4() != null) {
                    detaildto.setProAssdUsagetypeDesc4(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype5() != null) {
                    detaildto.setProAssdUsagetypeDesc5(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
                }
                if (detaildto.getAssdNatureOfproperty1() != null) {
                detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                        .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getDescLangFirst());
                }
                if (detaildto.getAssdNatureOfproperty2() != null) {
                    detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getDescLangFirst());
                }
                if (detaildto.getAssdNatureOfproperty3() != null) {
                    detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getDescLangFirst());
                }
                if (detaildto.getAssdNatureOfproperty4() != null) {
                    detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getDescLangFirst());
                }
                if (detaildto.getAssdNatureOfproperty5() != null) {
                    detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getDescLangFirst());
                }

                /*
                 * detaildto.setProAssdUnitType( CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdUnitTypeId())
                 * .getDescLangFirst());
                 */
                detaildto.setProFloorNo(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                detaildto.setProAssdConstruTypeDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org)
                                .getDescLangFirst());
                /*
                 * detaildto.setProAssdRoadfactorDesc(
                 * CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdRoadFactor()) .getDescLangFirst());
                 */
                detaildto.setProAssdOccupancyTypeDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org)
                                .getDescLangFirst());
            }
        }
    }

    @Override
    public boolean saveForm() {
        setCustomViewName("AmalgamationView");
        getProvisionalAssesmentMstDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        if ("A".equals(getAssType())) {
            final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
            validateBean(workFlowActionDto, CheckerActionValidator.class);
        }
        List<DocumentDetailsVO> docs = getCheckList();
        docs = fileUpload.prepareFileUpload(docs);

        if ((docs != null) && !docs.isEmpty()) {
            for (final DocumentDetailsVO doc : docs) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.CommonConstants.Y)) {
                    if (doc.getDocumentByteCode() == null) {
                        addValidationError(ApplicationSession.getInstance().getMessage("rnl.estate.mandtory.docs"));
                        break;
                    }
                }
            }
        }

        if (hasValidationErrors()) {
            return false;
        } else {
            long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            getProvisionalAssesmentMstDto().setDocs(docs);
            if ("A".equals(getAssType())) {
                propertyAuthorizationService.saveUploadedFile(getProvisionalAssesmentMstDto(), orgId,
                        UserSession.getCurrent().getEmployee(), this.getDeptId(), UserSession.getCurrent().getLanguageId(),
                        this.getServiceId());
                propertyAuthorizationService.saveAmalgamationAuthorizationWithEdit(
                        getProvisionalAssesmentMstDto(), getAmalParentPropDto(),
                        getWorkflowActionDto(), orgId,
                        UserSession.getCurrent().getEmployee(), this.getDeptId(), UserSession.getCurrent().getLanguageId(),
                        this.getServiceId(),
                        this.getFinYearList(), getAuthEditFlag());
                setSuccessMessage(getAppSession().getMessage("property.Auth.save"));
                propertyAuthorizationService.callWorkflow(this.getProvisionalAssesmentMstDto().getApmApplicationId(),
                        getWorkflowActionDto(), orgId, UserSession.getCurrent().getEmployee(), this.getServiceId());
            } else {

                UserSession session = UserSession.getCurrent();
                getProvisionalAssesmentMstDto().setCreatedBy(session.getEmployee().getEmpId());
                getProvisionalAssesmentMstDto().setOrgId(session.getOrganisation().getOrgid());
                getProvisionalAssesmentMstDto().setLgIpMac(session.getEmployee().getEmppiservername());
                amalgamationService.saveAmulgamatedProperty(getProvisionalAssesmentMstDto(),
                        getFinYearList(),
                        getDeptId(), getProvisionalAssesmentMstDtoList());
                ApplicationMetadata applicationData = new ApplicationMetadata();
                final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
                applicantDetailDto.setOrgId(session.getOrganisation().getOrgid());
                applicationData.setApplicationId(getProvisionalAssesmentMstDto().getApmApplicationId());
                applicationData.setIsCheckListApplicable(
                        (getProvisionalAssesmentMstDto().getDocs() != null
                                && !getProvisionalAssesmentMstDto().getDocs().isEmpty()) ? true
                                        : false);
                applicationData.setOrgId(session.getOrganisation().getOrgid());
                applicantDetailDto.setServiceId(getServiceId());
                applicantDetailDto.setDepartmentId(getDeptId());
                applicantDetailDto.setUserId(getProvisionalAssesmentMstDto().getCreatedBy());
                applicantDetailDto.setOrgId(session.getOrganisation().getOrgid());
                Organisation organisation = new Organisation();
                organisation.setOrgid(getProvisionalAssesmentMstDto().getOrgId());
                if(Utility.isEnvPrefixAvailable(organisation, "PWZ")) {
                 	 if (getProvisionalAssesmentMstDto().getAssParshadWard1() != null) {
                 		applicantDetailDto.setDwzid1(getProvisionalAssesmentMstDto().getAssParshadWard1());
                      }
                 	 if (getProvisionalAssesmentMstDto().getAssParshadWard2() != null) {
                 		applicantDetailDto.setDwzid2(getProvisionalAssesmentMstDto().getAssParshadWard2());
                      }
                 	 if (getProvisionalAssesmentMstDto().getAssParshadWard3() != null) {
                 		applicantDetailDto.setDwzid3(getProvisionalAssesmentMstDto().getAssParshadWard3());
                      }
                 	 if (getProvisionalAssesmentMstDto().getAssParshadWard4() != null) {
                 		applicantDetailDto.setDwzid4(getProvisionalAssesmentMstDto().getAssParshadWard4());
                      }
                 	 if (getProvisionalAssesmentMstDto().getAssParshadWard5() != null) {
                 		applicantDetailDto.setDwzid5(getProvisionalAssesmentMstDto().getAssParshadWard5());
                      }
          		}else{
          			applicantDetailDto.setDwzid1(getProvisionalAssesmentMstDto().getAssWard1());
                    applicantDetailDto.setDwzid2(getProvisionalAssesmentMstDto().getAssWard2());
                    applicantDetailDto.setDwzid3(getProvisionalAssesmentMstDto().getAssWard3());
                    applicantDetailDto.setDwzid4(getProvisionalAssesmentMstDto().getAssWard4());
                    applicantDetailDto.setDwzid5(getProvisionalAssesmentMstDto().getAssWard5());
          		}
                commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
                setSuccessMessage(
                        getAppSession().getMessage("Amalgamation saved successfully your Application no is : ")
                                + getProvisionalAssesmentMstDto().getApmApplicationId());
            }
            return true;
        }
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
        return provisionalAssesmentMstDto;
    }

    public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public Integer getOwnerDetailTableCount() {
        return ownerDetailTableCount;
    }

    public void setOwnerDetailTableCount(Integer ownerDetailTableCount) {
        this.ownerDetailTableCount = ownerDetailTableCount;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public Date getLeastFinYear() {
        return leastFinYear;
    }

    public void setLeastFinYear(Date leastFinYear) {
        this.leastFinYear = leastFinYear;
    }

    public String getAssType() {
        return assType;
    }

    public void setAssType(String assType) {
        this.assType = assType;
    }

    public String getAssMethod() {
        return assMethod;
    }

    public void setAssMethod(String assMethod) {
        this.assMethod = assMethod;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getProvAsseFactDtlDto() {
        return provAsseFactDtlDto;
    }

    public void setProvAsseFactDtlDto(List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto) {
        this.provAsseFactDtlDto = provAsseFactDtlDto;
    }

    public Map<String, List<BillDisplayDto>> getDisplayMap() {
        return displayMap;
    }

    public void setDisplayMap(Map<String, List<BillDisplayDto>> displayMap) {
        this.displayMap = displayMap;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public AmalgamationDto getAmalgamationDto() {
        return amalgamationDto;
    }

    public void setAmalgamationDto(AmalgamationDto amalgamationDto) {
        this.amalgamationDto = amalgamationDto;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public void setFinancialYearMap(Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

    public List<ProvisionalAssesmentMstDto> getProvisionalAssesmentMstDtoList() {
        return ProvisionalAssesmentMstDtoList;
    }

    public void setProvisionalAssesmentMstDtoList(List<ProvisionalAssesmentMstDto> provisionalAssesmentMstDtoList) {
        ProvisionalAssesmentMstDtoList = provisionalAssesmentMstDtoList;
    }

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    public List<Long> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<Long> finYearList) {
        this.finYearList = finYearList;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public String getAuthEditFlag() {
        return authEditFlag;
    }

    public void setAuthEditFlag(String authEditFlag) {
        this.authEditFlag = authEditFlag;
    }

    public ProvisionalAssesmentMstDto getAmalParentPropDto() {
        return amalParentPropDto;
    }

    public void setAmalParentPropDto(ProvisionalAssesmentMstDto amalParentPropDto) {
        this.amalParentPropDto = amalParentPropDto;
    }

    public boolean isLastChecker() {
        return lastChecker;
    }

    public void setLastChecker(boolean lastChecker) {
        this.lastChecker = lastChecker;
    }

    public String getIntgrtionWithBP() {
        return intgrtionWithBP;
    }

    public void setIntgrtionWithBP(String intgrtionWithBP) {
        this.intgrtionWithBP = intgrtionWithBP;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getProAssPropAddCheck() {
        return proAssPropAddCheck;
    }

    public void setProAssPropAddCheck(String proAssPropAddCheck) {
        this.proAssPropAddCheck = proAssPropAddCheck;
    }

    public List<BillReceiptPostingDTO> getReabteRecDtoList() {
        return reabteRecDtoList;
    }

    public void setReabteRecDtoList(List<BillReceiptPostingDTO> reabteRecDtoList) {
        this.reabteRecDtoList = reabteRecDtoList;
    }

    public ArrayOfKhasraDetails getArrayOfKhasraDetails() {
        return arrayOfKhasraDetails;
    }

    public void setArrayOfKhasraDetails(ArrayOfKhasraDetails arrayOfKhasraDetails) {
        this.arrayOfKhasraDetails = arrayOfKhasraDetails;
    }

    public ArrayOfPlotDetails getArrayOfPlotDetails() {
        return arrayOfPlotDetails;
    }

    public void setArrayOfPlotDetails(ArrayOfPlotDetails arrayOfPlotDetails) {
        this.arrayOfPlotDetails = arrayOfPlotDetails;
    }

    public ArrayOfDiversionPlotDetails getArrayOfDiversionPlotDetails() {
        return arrayOfDiversionPlotDetails;
    }

    public void setArrayOfDiversionPlotDetails(ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails) {
        this.arrayOfDiversionPlotDetails = arrayOfDiversionPlotDetails;
    }

    public List<LookUp> getDistrict() {
        return district;
    }

    public void setDistrict(List<LookUp> district) {
        this.district = district;
    }

    public List<LookUp> getTehsil() {
        return tehsil;
    }

    public void setTehsil(List<LookUp> tehsil) {
        this.tehsil = tehsil;
    }

    public List<LookUp> getVillage() {
        return village;
    }

    public void setVillage(List<LookUp> village) {
        this.village = village;
    }

    public List<LookUp> getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(List<LookUp> khasraNo) {
        this.khasraNo = khasraNo;
    }

    public List<LookUp> getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(List<LookUp> plotNo) {
        this.plotNo = plotNo;
    }

    public List<LookUp> getMohalla() {
        return mohalla;
    }

    public void setMohalla(List<LookUp> mohalla) {
        this.mohalla = mohalla;
    }

    public List<LookUp> getBlockStreet() {
        return blockStreet;
    }

    public void setBlockStreet(List<LookUp> blockStreet) {
        this.blockStreet = blockStreet;
    }

    public String getLandTypePrefix() {
        return landTypePrefix;
    }

    public void setLandTypePrefix(String landTypePrefix) {
        this.landTypePrefix = landTypePrefix;
    }

    public String getKnownKhaNo() {
        return knownKhaNo;
    }

    public void setKnownKhaNo(String knownKhaNo) {
        this.knownKhaNo = knownKhaNo;
    }

    public String getEnteredKhasraNo() {
        return enteredKhasraNo;
    }

    public void setEnteredKhasraNo(String enteredKhasraNo) {
        this.enteredKhasraNo = enteredKhasraNo;
    }

    public String getServiceShortCode() {
        return serviceShortCode;
    }

    public void setServiceShortCode(String serviceShortCode) {
        this.serviceShortCode = serviceShortCode;
    }

    public String getEnteredPlotNo() {
        return enteredPlotNo;
    }

    public void setEnteredPlotNo(String enteredPlotNo) {
        this.enteredPlotNo = enteredPlotNo;
    }

}
