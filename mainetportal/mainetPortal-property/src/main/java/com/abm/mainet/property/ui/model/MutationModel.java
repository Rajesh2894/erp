package com.abm.mainet.property.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.MutationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.MutationService;

@Component
@Scope("session")
public class MutationModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String ownershipPrefix;

    private String ownershipTypeValue;

    private String ownershipPrefixNew;

    private String saveButFlag;

    private String appliChargeFlag;

    private String mutationLevelFlag; // M-mutation A-Authorization

    private Long deptId;

    private Long orgId;

    private Long serviceId;

    private String serviceName;

    private Integer ownerDetailTableCount;

    private boolean isLastAuthorizer;

    private MutationDto mutationdto = new MutationDto();

    private List<LookUp> location = new ArrayList<>(0);

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private List<BillDisplayDto> charges = new ArrayList<>();

    private PropertyTransferMasterDto propTransferDto = null;

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;// Main DTO to Bind Data

    private List<CFCAttachment> documentList = new ArrayList<>();

    @Autowired
    private IChallanService iChallanService;
    
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
    
    private String assType; // Assessment Type: MUT-Mutation 
    
    private List<String> flatNoList;
    
    private String billingMethod;
    
    private String errorMsg;
    
    private String flatOrParentLevelChange;
    
    @Autowired
    private MutationService mutationService;
    
    @Override
    public boolean saveForm() {
        setCustomViewName("MutationForm");
        ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
        List<DocumentDetailsVO> docs = getCheckList();
        if(CollectionUtils.isNotEmpty(docs)) {
        	docs = setFileUploadMethod(docs);
        	propTransferDto.setDocs(docs);        	
        }
        propTransferDto.setDeptId(getDeptId());
        propTransferDto.setLangId(UserSession.getCurrent().getLanguageId());
        propTransferDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        propTransferDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        propTransferDto.setFlatOrParentLevelChange(flatOrParentLevelChange);
        if ((propTransferDto.getDocs() != null) && !propTransferDto.getDocs().isEmpty()) {
            for (final DocumentDetailsVO doc : propTransferDto.getDocs()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
                    if (doc.getDocumentByteCode() == null) {                        
                        addValidationError(getAppSession().getMessage("property.mandtory.docs"));
                        break;
                    }
                }
            }
        }
 
        //validateBean(assMstDto, SelfAssessmentValidator.class);
        final CommonChallanDTO offline = getOfflineDTO();
       if (appliChargeFlag.equals(MainetConstants.Common_Constant.YES) && !propTransferDto.getCharges().isEmpty()) {
            validateBean(offline, CommonOfflineMasterValidator.class);
      }
        if (hasValidationErrors()) {
            return false;
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.ASCL) || 
        		Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {
        	propTransferDto.setApplicantDetailDto(getApplicantDetailDto());
        }
        propTransferDto=  mutationService.saveMutation(propTransferDto);
       if (appliChargeFlag.equals(MainetConstants.Common_Constant.YES) && !propTransferDto.getCharges().isEmpty()) {
          setChallanDToandSaveChallanData(offline, charges, propTransferDto, provisionalAssesmentMstDto);
       } else {
            propTransferDto.setLocationId(assMstDto.getLocId());
            mutationService.callWorkFlowForFreeService(propTransferDto);
            setSuccessMessage(getAppSession().getMessage("property.mutation.save") + ":" + propTransferDto.getApmApplicationId());
       }
        return true;
    }

   private PropertyTransferMasterDto setChallanDToandSaveChallanData(final CommonChallanDTO offline, final List<BillDisplayDto> charges,
            PropertyTransferMasterDto asseMstDto, ProvisionalAssesmentMstDto prevAssessDetail) {
        final UserSession session = UserSession.getCurrent();
        PropertyTransferOwnerDto ownDtlDto = asseMstDto.getPropTransferOwnerList().get(0);
        offline.setAmountToPay(Double.toString(asseMstDto.getBillTotalAmt()));
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        asseMstDto.getCharges().forEach(charge -> {
            offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue());
        });
        offline.setEmailId(prevAssessDetail.getAssEmail());
        if(Utility.isEnvPrefixAvailable(session.getOrganisation(), MainetConstants.APP_NAME.ASCL)) {
        	StringBuilder applicant = new StringBuilder();
        	applicant.append(asseMstDto.getApplicantDetailDto().getApplicantFirstName()+MainetConstants.WHITE_SPACE);
        	if(StringUtils.isNotBlank(asseMstDto.getApplicantDetailDto().getApplicantMiddleName())) {
        		applicant.append(asseMstDto.getApplicantDetailDto().getApplicantMiddleName()+MainetConstants.WHITE_SPACE);
        	}
        	applicant.append(asseMstDto.getApplicantDetailDto().getApplicantLastName());
        	offline.setApplicantName(applicant.toString());
        }else {
        	offline.setApplicantName(ownDtlDto.getOwnerName());
        }
        offline.setApplNo(asseMstDto.getApmApplicationId());
        offline.setApplicantAddress(prevAssessDetail.getAssAddress());
        offline.setUniquePrimaryId(asseMstDto.getProAssNo());
        offline.setMobileNumber(ownDtlDto.getMobileno());
        offline.setServiceId(getServiceId());
        offline.setDeptId(getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        
		// task 108966 changes by Arun
		offline.setPropNoConnNoEstateNoV(prevAssessDetail.getAssNo());
		offline.setReferenceNo(prevAssessDetail.getAssOldpropno());
		offline.setPlotNo(prevAssessDetail.getTppPlotNo());
		StringBuilder jointOwnerName = new StringBuilder();
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(asseMstDto.getOwnerType(),
				UserSession.getCurrent().getOrganisation());
		if (lookUp.getLookUpCode().equals(MainetConstants.Property.JO)) {
			asseMstDto.getPropTransferOwnerList().forEach(data -> {
				if (StringUtils.isEmpty(jointOwnerName.toString())) {
					jointOwnerName.append(data.getOwnerName() + MainetConstants.WHITE_SPACE + data.getGuardianName());
				} else {
					jointOwnerName.append(MainetConstants.operator.COMA + data.getOwnerName()
							+ MainetConstants.WHITE_SPACE + data.getGuardianName());
				}
			});
			offline.setApplicantFullName(jointOwnerName.toString());
		} else {
			offline.setApplicantFullName(
					ownDtlDto.getOwnerName() + MainetConstants.WHITE_SPACE + ownDtlDto.getGuardianName());
		}
		offline.setTransferType(CommonMasterUtility.getNonHierarchicalLookUpObject(asseMstDto.getTransferType(),
				UserSession.getCurrent().getOrganisation()).getLookUpDesc());
		//end
        
        if (getCheckList() != null && !getCheckList().isEmpty()) {
            offline.setDocumentUploaded(true);
        }
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {             
                CommonChallanDTO dto=iChallanService.generateChallanNumber(offline);
            	offline.setChallanValidDate(dto.getChallanValidDate());
                offline.setChallanNo(dto.getChallanNo());
            setSuccessMessage(getAppSession().getMessage("property.mutation.save") + asseMstDto.getApmApplicationId());
        } 
        setOfflineDTO(offline);
        return asseMstDto;
    }

   private List<DocumentDetailsVO> setFileUploadMethod(
           final List<DocumentDetailsVO> docs) {
       final Map<Long, String> listOfString = new HashMap<>();
       final Map<Long, String> fileName = new HashMap<>();

       if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
           Base64 base64 = null;
           List<File> list = null;
           for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
               list = new ArrayList<>(entry.getValue());
               for (final File file : list) {
                   try {
                       base64 = new Base64();
                       final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                       fileName.put(entry.getKey(), file.getName());
                       listOfString.put(entry.getKey(), bytestring);
                   } catch (final IOException e) {
                	   throw new FrameworkException("Exception has been occurred in file byte to string conversions", e);
                   }
               }
           }
       }
       if (!docs.isEmpty() && !listOfString.isEmpty()) {
           for (final DocumentDetailsVO d : docs) {
               final long count = d.getDocumentSerialNo() - 1;
               if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                   d.setDocumentByteCode(listOfString.get(count));
                   d.setDocumentName(fileName.get(count));
               }
           }
       }
       return docs;
   }
    public void setDropDownValues(ProvisionalAssesmentMstDto assMst, Organisation org) {
        assMst.setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation())
                .getDescLangFirst());
        if(assMst.getPropLvlRoadType() != null) {
        	assMst.setProAssdRoadfactorDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assMst.getPropLvlRoadType(), UserSession.getCurrent().getOrganisation())
                    .getDescLangFirst());        	
        }

        assMst.setAssWardDesc1(
                CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard1())
                        .getDescLangFirst());
        

        if (assMst.getAssWard2() != null) {
            assMst.setAssWardDesc2(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard2())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard3() != null) {
            assMst.setAssWardDesc3(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard3())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard4() != null) {
            assMst.setAssWardDesc4(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard4())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard5() != null) {
            assMst.setAssWardDesc5(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard5())
                            .getDescLangFirst());
        }
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org).getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
            	if(dto.getGenderId() != null) {
            		dto.setProAssGenderId(
            				CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(),
            						UserSession.getCurrent().getOrganisation()).getDescLangFirst());
            	}
            	if(dto.getRelationId() != null) {
            		dto.setProAssRelationId(
            				CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(),
            						UserSession.getCurrent().getOrganisation()).getDescLangFirst());
            	}
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }
        if(provisionalAssesmentMstDto.getLocId()!=null) {
            this.getLocation().forEach(loca -> {
                if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                    this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
                }
            });
            }
        
        this.getDistrict().forEach(dis -> {
            if (Long.toString(dis.getLookUpId()).equals(assMst.getAssDistrict())) {
                assMst.setAssDistrictDesc(dis.getDescLangFirst());
            }
        });

        this.getTehsil().forEach(teh -> {
            if (teh.getLookUpCode().equals(assMst.getAssTahasil())) {
                assMst.setAssTahasilDesc(teh.getDescLangFirst());
            }
        });

        this.getVillage().forEach(vil -> {
            if (vil.getLookUpCode().equals(assMst.getTppVillageMauja())) {
                assMst.setTppVillageMaujaDesc(vil.getDescLangFirst());
            }
        });

        for (LookUp moh : this.getMohalla()) {
            if (moh.getLookUpCode().equals(assMst.getMohalla())) {
                assMst.setMohallaDesc(moh.getDescLangFirst());
                break;
            }
        }

        for (LookUp sheet : this.getBlockStreet()) {
            if (sheet.getLookUpCode().equals(assMst.getAssStreetNo())) {
                assMst.setAssStreetNoDesc(sheet.getDescLangFirst());
                break;
            }
        }               
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
        final ProvisionalAssesmentMstDto reqDTO = this.getProvisionalAssesmentMstDto();
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = reqDTO.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        final PortalService portalServiceMaster = ApplicationContextProvider.getApplicationContext().getBean(IPortalServiceMasterService.class).getService(getServiceId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(propTransferDto.getApmApplicationId()));
        payURequestDTO.setApplicantName(ownDtlDto.getAssoOwnerName());
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf2(String.valueOf(propTransferDto.getApmApplicationId()));
        payURequestDTO.setMobNo(ownDtlDto.getAssoMobileno());
        payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
        payURequestDTO.setDueAmt(new BigDecimal(propTransferDto.getBillTotalAmt()));
        payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
        payURequestDTO.setApplicationId(String.valueOf(propTransferDto.getApmApplicationId()));
    }
    public MutationDto getMutationdto() {
        return mutationdto;
    }

    public void setMutationdto(MutationDto mutationdto) {
        this.mutationdto = mutationdto;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<BillDisplayDto> getCharges() {
        return charges;
    }

    public void setCharges(List<BillDisplayDto> charges) {
        this.charges = charges;
    }

    public String getSaveButFlag() {
        return saveButFlag;
    }

    public void setSaveButFlag(String saveButFlag) {
        this.saveButFlag = saveButFlag;
    }

    public String getAppliChargeFlag() {
        return appliChargeFlag;
    }

    public void setAppliChargeFlag(String appliChargeFlag) {
        this.appliChargeFlag = appliChargeFlag;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getOwnerDetailTableCount() {
        return ownerDetailTableCount;
    }

    public void setOwnerDetailTableCount(Integer ownerDetailTableCount) {
        this.ownerDetailTableCount = ownerDetailTableCount;
    }

    public PropertyTransferMasterDto getPropTransferDto() {
        return propTransferDto;
    }

    public void setPropTransferDto(PropertyTransferMasterDto propTransferDto) {
        this.propTransferDto = propTransferDto;
    }

    public boolean isLastAuthorizer() {
        return isLastAuthorizer;
    }

    public void setLastAuthorizer(boolean isLastAuthorizer) {
        this.isLastAuthorizer = isLastAuthorizer;
    }

    public String getMutationLevelFlag() {
        return mutationLevelFlag;
    }

    public void setMutationLevelFlag(String mutationLevelFlag) {
        this.mutationLevelFlag = mutationLevelFlag;
    }

    public String getOwnershipPrefixNew() {
        return ownershipPrefixNew;
    }

    public void setOwnershipPrefixNew(String ownershipPrefixNew) {
        this.ownershipPrefixNew = ownershipPrefixNew;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

        public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
                return provisionalAssesmentMstDto;
        }

        public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
                this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
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

        public String getEnteredPlotNo() {
                return enteredPlotNo;
        }

        public void setEnteredPlotNo(String enteredPlotNo) {
                this.enteredPlotNo = enteredPlotNo;
        }

        public String getServiceShortCode() {
                return serviceShortCode;
        }

        public void setServiceShortCode(String serviceShortCode) {
                this.serviceShortCode = serviceShortCode;
        }

        public String getAssType() {
                return assType;
        }

        public void setAssType(String assType) {
                this.assType = assType;
        }

		public List<String> getFlatNoList() {
			return flatNoList;
		}

		public void setFlatNoList(List<String> flatNoList) {
			this.flatNoList = flatNoList;
		}

		public String getBillingMethod() {
			return billingMethod;
		}

		public void setBillingMethod(String billingMethod) {
			this.billingMethod = billingMethod;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public String getFlatOrParentLevelChange() {
			return flatOrParentLevelChange;
		}

		public void setFlatOrParentLevelChange(String flatOrParentLevelChange) {
			this.flatOrParentLevelChange = flatOrParentLevelChange;
		}

}