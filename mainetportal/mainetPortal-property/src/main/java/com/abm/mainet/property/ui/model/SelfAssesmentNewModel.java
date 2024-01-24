package com.abm.mainet.property.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.DemandLevelRebateDTO;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.validator.PropertyAssessmentValidator;

@Component
@Scope("session")
public class SelfAssesmentNewModel extends AbstractFormModel {

    private static final long serialVersionUID = -1250602655307212504L;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();// Main DTO to Bind Data

    private List<TbBillMas> billMasList = new LinkedList<>();// Bill Generation List

    private List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = new LinkedList<>();// Factor DTO to Bind Factor separately

    private List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();

    private Map<String, Map<String, List<ChargeDetailDTO>>> chargesMap = new HashMap<>();

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();

    private Map<String, List<ProvisionalAssesmentFactorDtlDto>> displayfactorMap = new HashMap<>();

    private List<LookUp> location = new ArrayList<>(0);

    private List<String> unitNoList = new ArrayList<>(0);

    private List<Long> finYearList = new ArrayList<>(0);

    private List<CFCAttachment> documentList = new ArrayList<>();

    private List<TbBillMas> billMasArrears = new LinkedList<>();// Bill Generation List

    private SelfAssessmentDeaultValueDTO defaultData = new SelfAssessmentDeaultValueDTO();

    private String otp;
    private String mobNumber;
    private String userOtp;

    private Long currFinYearId;
    private Long serviceId;
    private Long orgId;
    private Long deptId;
    private Long factorId;
    private String factorPrefix;
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private List<LookUp> schedule = new ArrayList<>(0);
    private String proAssPropAddCheck;
    private String assMethod;
    private Date leastFinYear;// least Date of Fin year in System
    private String assType;// Assessment type N-New ,C-Change,NC- No Change
    private Integer unitDetailTableCount;

    private Integer ownerDetailTableCount;
    private Integer unitStatusCount;
    private String selfAss;
    private Long maxUnit;
    private boolean mobdisabled;
    private boolean otpdisabled;
    private String intgrtionWithBP;

    private String billFilePath = null;

    List<DemandLevelRebateDTO> demandLevelRebateList = null;

    private Map<Long, String> financialYearMap = new LinkedHashMap<>();

    private CommonChallanDTO offlineDTO = new CommonChallanDTO();

    private List<DocumentDetailsVO> docList = new ArrayList<>();

    private String isEdit;

    private ProperySearchDto searchDto = new ProperySearchDto();

    private List<ProperySearchDto> searchDtoResult = new ArrayList<>();

    private List<LookUp> collectionDetails = new ArrayList<>(0);

    private Map<Long, List<DocumentDetailsVO>> appDocument = new LinkedHashMap<>();

    private List<TbBillMas> authComBillList = null;// Bill List to compare Data in Authorization

    private List<TbBillMas> billListbyProperty = new LinkedList<>();

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

    private String propTaxCalculate;

    private String OrganizationName;

    private ChallanReceiptPrintDTO receiptDTO = null;

    private String draftModeFlag;

    private String apmMode;
    
    private String factorNotApplicable;
    private String ConstructFlag;
    private String countOfRow;
    private String copyOfRow;
    private String unitDetailsCheck;
    private String checklistApplicable;
    private String serviceShortCode;
    private Map<Long, String> roomTypeMap = new LinkedHashMap<>();
    private String roomTypeJson;
    private String billingMethod;
    private String showForm;
    private String ownerName;
    private String occupierName;
    private String mobileNo;
    private String emailId;
    private String displayCaptcha;
    private String userCaptcha;
    private List<String> flatNoList;
    private String searchFlag;
    

    public LookUp getFactorLookup(String factorCode) {
        return CommonMasterUtility.getValueFromPrefixLookUp(factorCode, MainetConstants.Property.propPref.FCT);
    }

    public LookUp getOwnershipTypeLookup(String ownershipType) {
        return CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT);
    }

    // to get text description of the selected value of a dropdownList
    public void setDropDownValues() {
        if (provisionalAssesmentMstDto.getAssOwnerType() != null) {
            this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType()).getDescLangFirst());
            String ownerTypeCode = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType()).getLookUpCode();

            if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
                for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
                        .getProvisionalAssesmentOwnerDtlDtoList()) {
                    dto.setProAssGenderId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId()).getDescLangFirst());
                    dto.setProAssRelationId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId()).getDescLangFirst());
                }
            } else {
                ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
                ownerDto.setGenderId(null);
                ownerDto.setRelationId(null);
                ownerDto.setAssoAddharno(null);
            }
        }
        LookUp lookUpVal = null;
        final List<LookUp> lookupList = getLevelData(MainetConstants.Property.propPref.ASS);
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp lookUp : lookupList) {
                if ("Y".equals(lookUp.getDefaultVal())) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            this.setAssMethod(lookUpVal.getLookUpCode());
        }
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(MainetConstants.Property.MANUAL)) {
         * this.getSchedule().forEach(schedule -> { if (schedule.getLookUpId() == provisionalAssesmentMstDto.getAssLpYear()) {
         * this.getProvisionalAssesmentMstDto().setProAssLpYearDesc(schedule.getLookUpCode()); } }); }
         */

        if (provisionalAssesmentMstDto.getAssLandType() != null) {
            this.getProvisionalAssesmentMstDto().setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType()).getDescLangFirst());

        }

        if(provisionalAssesmentMstDto.getAssWard1() != null) {
        	this.getProvisionalAssesmentMstDto().setAssWardDesc1(
        			CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1()).getDescLangFirst());        	
        }

        if (provisionalAssesmentMstDto.getAssWard2() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc2(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2()).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard3() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc3(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3()).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard4() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc4(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4()).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard5() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc5(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5()).getDescLangFirst());
        }
        if (provisionalAssesmentMstDto.getLocId() != null) {
            this.getLocation().forEach(loca -> {
                if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                    this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
                }
            });
        }
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

        if(provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
        	this.getProvisionalAssesmentMstDto().setProAssdRoadfactorDesc(CommonMasterUtility
        			.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType()).getDescLangFirst());        	
        }
        /*
         * this.getProvisionalAssesmentMstDto()
         * .setProAssPropType(CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1())
         * .getDescLangFirst());
         */
        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
                    detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
					detaildto.setProAssdAssesmentDate(formatter.format(detaildto.getAssdAssesmentDate()));
                    if(detaildto.getAssdUsagetype1() != null) {
                    	detaildto.setProAssdUsagetypeDesc(
                    			CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1()).getDescLangFirst());                    	
                    }

                    /*
                     * detaildto.setProAssdUnitType(
                     * CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdUnitTypeId()) .getDescLangFirst());
                     */
                    if(detaildto.getAssdFloorNo() != null) {
                    	detaildto.setProFloorNo(
                    			CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo()).getDescLangFirst());                    	
                    }
                    if(detaildto.getAssdConstruType() != null) {
                    	detaildto.setProAssdConstruTypeDesc(
                    			CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType())
                    			.getDescLangFirst());                    	
                    }
                    /*
                     * detaildto.setProAssdRoadfactorDesc(
                     * CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdRoadFactor()) .getDescLangFirst());
                     */
                    if(detaildto.getAssdOccupancyType() != null) {
                    	detaildto.setProAssdOccupancyTypeDesc(
                    			CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType())
                    			.getDescLangFirst());                    	
                    }
                    if (detaildto.getAssdNatureOfproperty1() != null) {

                        detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1()).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty2() != null) {
                        detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2()).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty3() != null) {
                        detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3()).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty4() != null) {
                        detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4()).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty5() != null) {
                        detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5()).getDescLangFirst());
                    }
                }
                for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
                    if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                        provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorValueId())
                                .getDescLangFirst());
                        provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(
                                CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId())
                                        .getDescLangFirst());
                    }
                }
            }
        }
        setAddressDetails();
        // setBillPayment(); Not required for suda
        formatDate();
    }

    // Tax calculator: to get text description of the selected value of a dropdownList: required org id
    public void setDropDownValues(Organisation org) {
    	if(provisionalAssesmentMstDto.getAssOwnerType() != null) {
    		this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
    				.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());    		
    	}

        LookUp lookUpVal = null;
        final List<LookUp> lookupList = getLevelData(MainetConstants.Property.propPref.ASS);
        if ((lookupList != null) && !lookupList.isEmpty()) {
            for (final LookUp lookUp : lookupList) {
                if ("Y".equals(lookUp.getDefaultVal())) {
                    lookUpVal = lookUp;
                    break;
                }
            }
            this.setAssMethod(lookUpVal.getLookUpCode());
        }
        if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                            .getDescLangFirst());
        }


        if(getProvisionalAssesmentMstDto().getAssOwnerType() != null) {
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentOwnerDtlDtoList()) {
            	if(dto.getGenderId() != null && dto.getGenderId() > 0) {
            		dto.setProAssGenderId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
            	}
            	if(dto.getRelationId() != null && dto.getRelationId() > 0) {
            		 dto.setProAssRelationId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org)
                             .getDescLangFirst());
            	}
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }
        }
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
         * this.getSchedule().forEach(schedule -> { if (schedule.getLookUpId() == provisionalAssesmentMstDto.getAssLpYear()) {
         * this.getProvisionalAssesmentMstDto().setProAssLpYearDesc(schedule. getLookUpCode()); } }); }
         */
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

        if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                            .getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssLandType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setAssLandTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType(), org)
                            .getDescLangFirst());

        }

        if(provisionalAssesmentMstDto.getAssWard1() != null) {
        	this.getProvisionalAssesmentMstDto().setAssWardDesc1(CommonMasterUtility
        			.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());        	
        }

        if (provisionalAssesmentMstDto.getAssWard2() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc2(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard3() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc3(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard4() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc4(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getDescLangFirst());
        }

        if (provisionalAssesmentMstDto.getAssWard5() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc5(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getDescLangFirst());
        }
        
		if (provisionalAssesmentMstDto.getSurveyType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setSurveyTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getSurveyType(), org)
							.getDescLangFirst());
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
				
        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                    // SimpleDateFormat formatter = new
                    // SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    // detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    // Defect_40635 Construction date need to remove depend on ULB Currently it is
                    // mandatory
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

                    if (detaildto.getAssdYearConstruction() != null) {
                        detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    } else {
                        detaildto.setAssdYearConstruction(new Date());
                        detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    }
                    if(detaildto.getAssdUsagetype1() != null) {
                    	detaildto.setProAssdUsagetypeDesc(
                    			CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(),
                    					UserSession.getCurrent().getOrganisation()).getDescLangFirst());                    	
                    }

                    if (detaildto.getAssdUsagetype2() != null) {
                        detaildto.setProAssdUsagetypeDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype3() != null) {
                        detaildto.setProAssdUsagetypeDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype4() != null) {
                        detaildto.setProAssdUsagetypeDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype5() != null) {
                        detaildto.setProAssdUsagetypeDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
                    }

                    if(detaildto.getAssdNatureOfproperty1() != null) {
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
                    }if(detaildto.getAssdFloorNo() != null) {
                        detaildto.setProFloorNo(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                    }
                    if(detaildto.getAssdConstruType() != null) {
                    	detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                    			.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());                    	
                    }
                    if(detaildto.getAssdOccupancyType() != null) {
                    	detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                    			.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());                    	
                    }
                }
                for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
                    if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                        provisionalAssesmentFactorDtlDto
                                .setProAssfFactorValueDesc(CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(
                                                provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                        .getDescLangFirst());
                        provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                .getDescLangFirst());
                    }
                }
            }
        }
        // D#140426
        if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
	        List<String> usageTypeList = new ArrayList<>();
	        Set<String> unique= new HashSet<>();
	        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach( det ->{
	        	if(det.getProAssdUsagetypeDesc() != null) {
	        		usageTypeList.add(det.getProAssdUsagetypeDesc());
	            	unique.add(det.getProAssdUsagetypeDesc());
	        	}
	        });
        }//end of D#140426
        setAddressDetails();
        formatDate();
    }

    private void setAddressDetails() {
        if (provisionalAssesmentMstDto.getProAsscheck() != null) {

            if (getProvisionalAssesmentMstDto().getProAsscheck().equalsIgnoreCase(MainetConstants.Property.Y)) {
                getProvisionalAssesmentMstDto().setAssCorrAddress(getProvisionalAssesmentMstDto().getAssAddress());
                getProvisionalAssesmentMstDto().setAssCorrPincode(getProvisionalAssesmentMstDto().getAssPincode());
                getProvisionalAssesmentMstDto().setAssCorrEmail(getProvisionalAssesmentMstDto().getAssEmail());
            }
        }
    }

    private void setBillPayment() {
        if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(MainetConstants.Property.NOT_APP)) {
            getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants.Property.NOT_APP_DESC);
            // blank model set data
            getProvisionalAssesmentMstDto().setAssLpReceiptNo(null);
            getProvisionalAssesmentMstDto().setAssLpReceiptAmt(null);
            getProvisionalAssesmentMstDto().setAssLpReceiptDate(null);
            getProvisionalAssesmentMstDto().setAssLpYear(null);
            getProvisionalAssesmentMstDto().setBillAmount(null);
            getProvisionalAssesmentMstDto().setOutstandingAmount(null);
            getProvisionalAssesmentMstDto().setProAssLpReceiptDateFormat(null);
            getProvisionalAssesmentMstDto().setProAssLpYearDesc(null);

        } else if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(MainetConstants.Property.MANUAL)) {
            getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants.Property.MANUAL_DESC);
        }
    }

    private void formatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(MainetConstants.Property.MANUAL)) {
         * this.getProvisionalAssesmentMstDto()
         * .setProAssLpReceiptDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssLpReceiptDate())); }
         */
        if (provisionalAssesmentMstDto.getAssAcqDate() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssAcqDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssAcqDate()));
        }
        
        if(getProvisionalAssesmentMstDto().getReviseAssessmentDate() != null) {
			this.getProvisionalAssesmentMstDto().setReviseAssessmentDateFormat(
					formatter.format(getProvisionalAssesmentMstDto().getReviseAssessmentDate()));
		}
        if(getProvisionalAssesmentMstDto().getLastAssessmentDate() != null) {
        	this.getProvisionalAssesmentMstDto().setLastAssessmentDateFormat(formatter.format(getProvisionalAssesmentMstDto().getLastAssessmentDate()));
        }
        if (getProvisionalAssesmentMstDto().getConstructPermissionDate()!=null) {
  		  	this.getProvisionalAssesmentMstDto().setConstructPermissionDateFormat(formatter.format(getProvisionalAssesmentMstDto().getConstructPermissionDate()));
        }
        if (getProvisionalAssesmentMstDto().getAssdYearConstruction()!=null) {
            this.getProvisionalAssesmentMstDto().setAssdYearConstructionDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssdYearConstruction()));
        }
        /*
         * for (ProvisionalAssesmentDetailDto dto : this.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
         * dto.setProAssdConstructionDate(formatter.format(dto.getProAssdYearConstruction())); }
         */
    }

    @Override
    public boolean saveForm() {
        setCustomViewName("SelfAssessmentView");
        CommonChallanDTO offline = getOfflineDTO();
        ProvisionalAssesmentMstDto assMstDto = getProvisionalAssesmentMstDto();
        List<DocumentDetailsVO> docs = getCheckList();
        docs = setFileUploadMethod(docs);
        provisionalAssesmentMstDto.setDocs(docs);
        setOfflineDtoBeforeSave(offline, assMstDto);
        
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {
        	if (provisionalAssesmentMstDto.getBillTotalAmt() > 0 && getDraftModeFlag() == null) { 
			validateBean(offline, CommonOfflineMasterValidator.class);
        	}
        }
		 
        if (getDraftModeFlag() == null) {
            validateBean(assMstDto, PropertyAssessmentValidator.class);
        }
        if (getDraftModeFlag()==null && hasValidationErrors()) {
            return false;
        }
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);

        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        	provisionalAssesmentMstDto.setFlag(MainetConstants.ServiceCode.PORTAL);//To identify punched from Portal
        	setMultipleYearAssessment(provisionalAssesmentMstDto);
        }
        provisionalAssesmentMstDto.setSmServiceId(getServiceId());
        provisionalAssesmentMstDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        if (getDraftModeFlag() != null) {
            provisionalAssesmentMstDto.setApmDraftMode(getDraftModeFlag());
        }
        SelfAssessmentSaveDTO saveDto = new SelfAssessmentSaveDTO();
        saveDto.setProvisionalMas(provisionalAssesmentMstDto);
        saveDto.setBillMasList(billMasList);
        saveDto.setDeptId(this.getDeptId());
        saveDto.setFinYearList(finYearList);
        saveDto.setLanguageId(UserSession.getCurrent().getLanguageId());
        saveDto.setDemandLevelRebateList(getDemandLevelRebateList());
        offlineDTO.setApplNo(provisionalAssesmentMstDto.getApmApplicationId());
		
		if (provisionalAssesmentMstDto.getAssWard1() != null) {
			offlineDTO.getDwzDTO().setAreaDivision1(provisionalAssesmentMstDto.getAssWard1());
		}
		if (provisionalAssesmentMstDto.getAssWard2() != null) {
			offlineDTO.getDwzDTO().setAreaDivision2(provisionalAssesmentMstDto.getAssWard2());
		}
		if (provisionalAssesmentMstDto.getAssWard3() != null) {
			offlineDTO.getDwzDTO().setAreaDivision3(provisionalAssesmentMstDto.getAssWard3());
		}
		if (provisionalAssesmentMstDto.getAssWard4() != null) {
			offlineDTO.getDwzDTO().setAreaDivision4(provisionalAssesmentMstDto.getAssWard4());
		}
		if (provisionalAssesmentMstDto.getAssWard5() != null) {
			offlineDTO.getDwzDTO().setAreaDivision5(provisionalAssesmentMstDto.getAssWard5());
		}

        saveDto.setOffline(offlineDTO);
        saveDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        if (provisionalAssesmentMstDto.getAssStatus()!=null && provisionalAssesmentMstDto.getAssStatus().equals("SD") ) {
            // save and update draft application after edit
            saveDto = selfAssessmentService.saveAndUpdateDraftApplicationAfterEdit(saveDto);
            this.setProvisionalAssesmentMstDto(saveDto.getProvisionalMas());
        } else {
            // first time direct save or save as draft
            saveDto = selfAssessmentService.saveSelfAssessment(saveDto);
            this.setProvisionalAssesmentMstDto(saveDto.getProvisionalMas());
        }
        setOfflineDtoAfterSave(offline, details, billDetails, provisionalAssesmentMstDto);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
        	if (provisionalAssesmentMstDto.getBillMethod() != null
    				&& MainetConstants.FLAGI.equals(CommonMasterUtility.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getBillMethod(), 
    						UserSession.getCurrent().getOrganisation()).getLookUpCode())) {
        		Map<String, Long> flatWiseAppIdmap = new LinkedHashMap<>();
        		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(detail ->{
        			flatWiseAppIdmap.put(detail.getFlatNo(), detail.getApmApplicationId());
        		});
        		setSuccessMessage(getAppSession().getMessage("prop.self.submit") + flatWiseAppIdmap);
        	} else {
        		setSuccessMessage(getAppSession().getMessage("prop.self.submit") + provisionalAssesmentMstDto.getApmApplicationId());        		
        	}
        }
        return true;
    }
    
    public boolean savePropertyEditForm() {
    	boolean flag = false;
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SOV")) {
    		//stop validation
    	}else {
    		if(StringUtils.isBlank(otp)) {
        		addValidationError("Please generate OTP");
        	}else if(StringUtils.isBlank(userOtp)) {
        		addValidationError("Please enter OTP");
        	}else if(!StringUtils.equals(otp, userOtp)) {
        		addValidationError("Please enter valid OTP");
        	}
    	}
    	
    	if(StringUtils.isBlank(userCaptcha)) {
    		addValidationError("Please enter captcha");
    	}else if(!StringUtils.equals(displayCaptcha, userCaptcha)) {
    		addValidationError("Please enter valid captcha");
    	}
    	
    	if(StringUtils.isBlank(provisionalAssesmentMstDto.getIsUnderGroundDrainConAvail())) {
    		addValidationError(getAppSession().getMessage("property.Is.Underground.Drainage.Connection.Available.validation"));
    	}else if(StringUtils.equalsIgnoreCase("Yes", provisionalAssesmentMstDto.getIsUnderGroundDrainConAvail()) && provisionalAssesmentMstDto.getConnectionDate() == null){
    		addValidationError(getAppSession().getMessage("proeprty.Connection.Date.validation"));
    	}
    	
    	if (CollectionUtils.isNotEmpty(getCheckList())) {
    		List<DocumentDetailsVO> docs = getCheckList();
            docs = setFileUploadMethod(docs);
            provisionalAssesmentMstDto.setDocs(docs);
            for (final DocumentDetailsVO doc : getCheckList()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
                    if (doc.getDocumentByteCode() == null) {                        
                        addValidationError(getAppSession().getMessage("property.mandtory.docs"));
                        break;
                    }
                }
            }
        }
    	if(hasValidationErrors()) {
    		return false;
    	}
    	provisionalAssesmentMstDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
    	provisionalAssesmentMstDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).setAssoMobileno(mobileNo);
    	provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).seteMail(emailId);
    	ProvisionalAssesmentMstDto savePropertyElectricRelatedData = selfAssessmentService.savePropertyElectricRelatedData(provisionalAssesmentMstDto);
    	setProvisionalAssesmentMstDto(savePropertyElectricRelatedData);
    	flag = true;
    	return flag;
    }

    private void setOfflineDtoBeforeSave(CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDto) {
        final UserSession session = UserSession.getCurrent();
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        offline.setAmountToPay(Double.toString(asseMstDto.getBillPartialAmt()));
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        offline.setEmailId(asseMstDto.getAssEmail());
        offline.setApplicantName(ownDtlDto.getAssoOwnerName());
        offline.setApplicantAddress(asseMstDto.getAssAddress());
        offline.setMobileNumber(ownDtlDto.getAssoMobileno());
        offline.setServiceId(getServiceId());
        offline.setDeptId(getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            offline.setDocumentUploaded(true);
        }
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        WardZoneBlockDTO dwzDto = new WardZoneBlockDTO();
        if (asseMstDto.getAssWard1() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard1());
        }
        if (asseMstDto.getAssWard2() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard2());
        }
        if (asseMstDto.getAssWard3() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard3());
        }
        if (asseMstDto.getAssWard4() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard4());
        }
        if (asseMstDto.getAssWard5() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard5());
        }
        offline.setDwzDTO(dwzDto);
    }

    private void setOfflineDtoAfterSave(CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, ProvisionalAssesmentMstDto asseMstDto) {
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setApplNo(asseMstDto.getApmApplicationId());
        offline.setUniquePrimaryId(asseMstDto.getAssNo());

        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)
                && asseMstDto.getApmDraftMode() == null) {
            offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED);
            offline = iChallanService
                    .generateChallanNumber(offline);
        }
        setSuccessMessage(getAppSession().getMessage("prop.save.self") + asseMstDto.getApmApplicationId());        	
        setOfflineDTO(offline);
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
        final ProvisionalAssesmentMstDto reqDTO = this.getProvisionalAssesmentMstDto();
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = reqDTO.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(reqDTO.getApmApplicationId()));
        payURequestDTO.setApplicantName(ownDtlDto.getAssoOwnerName());
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf2(String.valueOf(reqDTO.getApmApplicationId()));
        payURequestDTO.setMobNo(ownDtlDto.getAssoMobileno());
        payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
        payURequestDTO.setDueAmt(new BigDecimal(reqDTO.getBillPartialAmt()));
        payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
        payURequestDTO.setApplicationId(reqDTO.getApmApplicationId().toString());
    }

    public void setSelectionValues() {

        for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()) {
            dto.setProAssGenderId(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId()).getDescLangFirst());
            dto.setProAssRelationId(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId()).getDescLangFirst());
        }
        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
            }
        });

        this.getProvisionalAssesmentMstDto()
                .setProAssPropType(CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1())
                        .getDescLangFirst());

        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {

            for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                    detaildto.setProFaYearIdDesc(entry.getValue());
                }
            }
            detaildto.setProAssdUsagetypeDesc(
                    CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1()).getDescLangFirst());
            /*
             * detaildto.setProAssdUnitType(
             * CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdUnitTypeId()).getDescLangFirst());
             */
            detaildto.setProFloorNo(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo()).getDescLangFirst());
            detaildto.setProAssdConstruTypeDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType()).getDescLangFirst());
            /*
             * detaildto.setProAssdRoadfactorDesc(
             * CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdRoadFactor()).getDescLangFirst());
             */
            detaildto.setProAssdOccupancyTypeDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType()).getDescLangFirst());

        }
        for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
            if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorValueId())
                        .getDescLangFirst());
                provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId())
                                .getDescLangFirst());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        for (ProvisionalAssesmentDetailDto dto : this.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
            dto.setProAssdConstructionDate(formatter.format(dto.getAssdYearConstruction()));

            dto.setProAssEffectiveDateDesc(formatter.format(dto.getAssEffectiveDate()));
        }
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

    public void setRoomTypeList(Organisation org) {
		List<LookUp> roomTypeList = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.Prefix.RTP, org);
		for(LookUp roomType : roomTypeList) {
				getRoomTypeMap().put(roomType.getLookUpId(), roomType.getOtherField());
		}
	}
    
    public void setMultipleYearAssessment(ProvisionalAssesmentMstDto dto) {
    	Organisation org = UserSession.getCurrent().getOrganisation();
    	Long natureOfproperty = null;
    	List<ProvisionalAssesmentDetailDto> detailDtoList = new ArrayList<ProvisionalAssesmentDetailDto>();
    	for(Long finYearId : getFinYearList()) {
    		// set property type(natureOfproperty) in every floor/flats
			if(CollectionUtils.isNotEmpty(dto.getProvisionalAssesmentDetailDtoList())) {
				ProvisionalAssesmentDetailDto proAssDetailDto = dto.getProvisionalAssesmentDetailDtoList().get(0);
				if(proAssDetailDto.getAssdNatureOfproperty1() != null) {
					natureOfproperty = proAssDetailDto.getAssdNatureOfproperty1();
					// set property type(natureOfproperty) in LandType (LDT prefix)
					List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.Property.propPref.LDT,	org);
					String propTypeDesc = CommonMasterUtility.getHierarchicalLookUp(natureOfproperty, org).getDescLangFirst();
					if(propTypeDesc != null) {
						lookupList.forEach(lookUp ->{
							if(propTypeDesc.equals(lookUp.getDescLangFirst())) {
								dto.setAssLandType(lookUp.getLookUpId());
							}
						});						
					}
				}
			}
			// for new registration
			Iterator<ProvisionalAssesmentDetailDto> iterator = dto.getProvisionalAssesmentDetailDtoList().iterator();
			while(iterator.hasNext()) {
				ProvisionalAssesmentDetailDto detailDto = iterator.next();
				ProvisionalAssesmentDetailDto assesmentDetailDto = new ProvisionalAssesmentDetailDto();
				detailDto.setFaYearId(finYearId);
				detailDto.setAssdYearConstruction(dto.getAssAcqDate());							
				detailDto.setAssdNatureOfproperty1(natureOfproperty);
				BeanUtils.copyProperties(detailDto, assesmentDetailDto);
				detailDtoList.add(assesmentDetailDto);
			}
    	}
    	if(natureOfproperty != null) {
			dto.setAssPropType1(natureOfproperty);				
		}
		dto.setProvisionalAssesmentDetailDtoList(detailDtoList);
    }
    
    public void showOnlySelectedFinYearAssessment(ProvisionalAssesmentMstDto dto) {
		Long finYearId = dto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
		Iterator<ProvisionalAssesmentDetailDto> iterator = dto.getProvisionalAssesmentDetailDtoList().iterator();
		while(iterator.hasNext()) {
			ProvisionalAssesmentDetailDto detailDto = iterator.next();
			if(!finYearId.equals(detailDto.getFaYearId())) {
				iterator.remove();
			}
		}
	}
    
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public List<LookUp> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LookUp> schedule) {
        this.schedule = schedule;
    }

    public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
        return provisionalAssesmentMstDto;
    }

    public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
    }

    public Map<String, Map<String, List<ChargeDetailDTO>>> getChargesMap() {
        return chargesMap;
    }

    public void setChargesMap(Map<String, Map<String, List<ChargeDetailDTO>>> chargesMap) {
        this.chargesMap = chargesMap;
    }

    public LookUp getdesc(String id, String perfix) {
        return CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(id));
    }

    public String getFactorPrefix() {
        return factorPrefix;
    }

    public void setFactorPrefix(String factorPrefix) {
        this.factorPrefix = factorPrefix;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public void setFinancialYearMap(Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public Long getCurrFinYearId() {
        return currFinYearId;
    }

    public void setCurrFinYearId(Long currFinYearId) {
        this.currFinYearId = currFinYearId;
    }

    public List<Long> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<Long> finYearList) {
        this.finYearList = finYearList;
    }

    public String getProAssPropAddCheck() {
        return proAssPropAddCheck;
    }

    public void setProAssPropAddCheck(String proAssPropAddCheck) {
        this.proAssPropAddCheck = proAssPropAddCheck;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getProvAsseFactDtlDto() {
        return provAsseFactDtlDto;
    }

    public void setProvAsseFactDtlDto(List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto) {
        this.provAsseFactDtlDto = provAsseFactDtlDto;
    }

    public Long getFactorId() {
        return factorId;
    }

    public void setFactorId(Long factorId) {
        this.factorId = factorId;
    }

    public CommonChallanDTO getOfflineDTO() {
        return offlineDTO;
    }

    public void setOfflineDTO(CommonChallanDTO offlineDTO) {
        this.offlineDTO = offlineDTO;
    }

    public String getAssMethod() {
        return assMethod;
    }

    public void setAssMethod(String assMethod) {
        this.assMethod = assMethod;
    }

    public Date getLeastFinYear() {
        return leastFinYear;
    }

    public void setLeastFinYear(Date leastFinYear) {
        this.leastFinYear = leastFinYear;
    }

    public Map<String, List<ProvisionalAssesmentFactorDtlDto>> getDisplayfactorMap() {
        return displayfactorMap;
    }

    public void setDisplayfactorMap(Map<String, List<ProvisionalAssesmentFactorDtlDto>> displayfactorMap) {
        this.displayfactorMap = displayfactorMap;
    }

    public Map<String, List<BillDisplayDto>> getDisplayMap() {
        return displayMap;
    }

    public void setDisplayMap(Map<String, List<BillDisplayDto>> displayMap) {
        this.displayMap = displayMap;
    }

    public List<String> getUnitNoList() {
        return unitNoList;
    }

    public void setUnitNoList(List<String> unitNoList) {
        this.unitNoList = unitNoList;
    }

    public String getAssType() {
        return assType;
    }

    public void setAssType(String assType) {
        this.assType = assType;
    }

    public List<ProvisionalAssesmentMstDto> getProvAssMstDtoList() {
        return provAssMstDtoList;
    }

    public void setProvAssMstDtoList(List<ProvisionalAssesmentMstDto> provAssMstDtoList) {
        this.provAssMstDtoList = provAssMstDtoList;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public Integer getOwnerDetailTableCount() {
        return ownerDetailTableCount;
    }

    public void setOwnerDetailTableCount(Integer ownerDetailTableCount) {
        this.ownerDetailTableCount = ownerDetailTableCount;
    }

    public Integer getUnitStatusCount() {
        return unitStatusCount;
    }

    public void setUnitStatusCount(Integer unitStatusCount) {
        this.unitStatusCount = unitStatusCount;
    }

    public Integer getUnitDetailTableCount() {
        return unitDetailTableCount;
    }

    public void setUnitDetailTableCount(Integer unitDetailTableCount) {
        this.unitDetailTableCount = unitDetailTableCount;
    }

    public String getSelfAss() {
        return selfAss;
    }

    public void setSelfAss(String selfAss) {
        this.selfAss = selfAss;
    }

    public Long getMaxUnit() {
        return maxUnit;
    }

    public void setMaxUnit(Long maxUnit) {
        this.maxUnit = maxUnit;
    }

    public List<TbBillMas> getBillMasArrears() {
        return billMasArrears;
    }

    public void setBillMasArrears(List<TbBillMas> billMasArrears) {
        this.billMasArrears = billMasArrears;
    }

    public SelfAssessmentDeaultValueDTO getDefaultData() {
        return defaultData;
    }

    public void setDefaultData(SelfAssessmentDeaultValueDTO defaultData) {
        this.defaultData = defaultData;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(String mobNumber) {
        this.mobNumber = mobNumber;
    }

    public boolean isMobdisabled() {
        return mobdisabled;
    }

    public void setMobdisabled(boolean mobdisabled) {
        this.mobdisabled = mobdisabled;
    }

    public boolean isOtpdisabled() {
        return otpdisabled;
    }

    public void setOtpdisabled(boolean otpdisabled) {
        this.otpdisabled = otpdisabled;
    }

    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }

    public List<DemandLevelRebateDTO> getDemandLevelRebateList() {
        return demandLevelRebateList;
    }

    public void setDemandLevelRebateList(List<DemandLevelRebateDTO> demandLevelRebateList) {
        this.demandLevelRebateList = demandLevelRebateList;
    }

    public String getIntgrtionWithBP() {
        return intgrtionWithBP;
    }

    public void setIntgrtionWithBP(String intgrtionWithBP) {
        this.intgrtionWithBP = intgrtionWithBP;
    }

    public List<DocumentDetailsVO> getDocList() {
        return docList;
    }

    public void setDocList(List<DocumentDetailsVO> docList) {
        this.docList = docList;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public ProperySearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(ProperySearchDto searchDto) {
        this.searchDto = searchDto;
    }

    public List<ProperySearchDto> getSearchDtoResult() {
        return searchDtoResult;
    }

    public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
        this.searchDtoResult = searchDtoResult;
    }

    public List<LookUp> getCollectionDetails() {
        return collectionDetails;
    }

    public void setCollectionDetails(List<LookUp> collectionDetails) {
        this.collectionDetails = collectionDetails;
    }

    public Map<Long, List<DocumentDetailsVO>> getAppDocument() {
        return appDocument;
    }

    public void setAppDocument(Map<Long, List<DocumentDetailsVO>> appDocument) {
        this.appDocument = appDocument;
    }

    public List<TbBillMas> getAuthComBillList() {
        return authComBillList;
    }

    public void setAuthComBillList(List<TbBillMas> authComBillList) {
        this.authComBillList = authComBillList;
    }

    public String getBillFilePath() {
        return billFilePath;
    }

    public void setBillFilePath(String billFilePath) {
        this.billFilePath = billFilePath;
    }

    public List<TbBillMas> getBillListbyProperty() {
        return billListbyProperty;
    }

    public void setBillListbyProperty(List<TbBillMas> billListbyProperty) {
        this.billListbyProperty = billListbyProperty;
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

    public String getPropTaxCalculate() {
        return propTaxCalculate;
    }

    public void setPropTaxCalculate(String propTaxCalculate) {
        this.propTaxCalculate = propTaxCalculate;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public ChallanReceiptPrintDTO getReceiptDTO() {
        return receiptDTO;
    }

    public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
        this.receiptDTO = receiptDTO;
    }

    public String getDraftModeFlag() {
        return draftModeFlag;
    }

    public void setDraftModeFlag(String draftModeFlag) {
        this.draftModeFlag = draftModeFlag;
    }

    public String getApmMode() {
        return apmMode;
    }

    public void setApmMode(String apmMode) {
        this.apmMode = apmMode;
    }

	public String getFactorNotApplicable() {
		return factorNotApplicable;
	}

	public void setFactorNotApplicable(String factorNotApplicable) {
		this.factorNotApplicable = factorNotApplicable;
	}

	public String getConstructFlag() {
		return ConstructFlag;
	}

	public void setConstructFlag(String constructFlag) {
		ConstructFlag = constructFlag;
	}

	public String getCountOfRow() {
		return countOfRow;
	}

	public void setCountOfRow(String countOfRow) {
		this.countOfRow = countOfRow;
	}

	public String getCopyOfRow() {
		return copyOfRow;
	}

	public void setCopyOfRow(String copyOfRow) {
		this.copyOfRow = copyOfRow;
	}

	public String getUnitDetailsCheck() {
		return unitDetailsCheck;
	}

	public void setUnitDetailsCheck(String unitDetailsCheck) {
		this.unitDetailsCheck = unitDetailsCheck;
	}

	public String getChecklistApplicable() {
		return checklistApplicable;
	}

	public void setChecklistApplicable(String checklistApplicable) {
		this.checklistApplicable = checklistApplicable;
	}

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	public Map<Long, String> getRoomTypeMap() {
		return roomTypeMap;
	}

	public void setRoomTypeMap(Map<Long, String> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}

	public String getRoomTypeJson() {
		return roomTypeJson;
	}

	public void setRoomTypeJson(String roomTypeJson) {
		this.roomTypeJson = roomTypeJson;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(String showForm) {
		this.showForm = showForm;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDisplayCaptcha() {
		return displayCaptcha;
	}

	public void setDisplayCaptcha(String displayCaptcha) {
		this.displayCaptcha = displayCaptcha;
	}

	public String getUserCaptcha() {
		return userCaptcha;
	}

	public void setUserCaptcha(String userCaptcha) {
		this.userCaptcha = userCaptcha;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	
}
