package com.abm.mainet.property.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyCommonInfoDto;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.BifurcationService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.validator.PropertyAssessmentValidator;

@Component
@Scope("session")
public class SelfAssesmentNewModel extends AbstractFormModel {

    private static final long serialVersionUID = -1250602655307212504L;

    private static final Logger LOGGER = Logger.getLogger(SelfAssesmentNewModel.class);

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private IFileUploadService fileUpload;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private BifurcationService bifurcationService;

    @Autowired
    private ViewPropertyDetailsService viewPropertyDetailsService;

    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
    private ServiceMasterService serviceMasterService;
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;
    
    @Autowired
	private IReceiptEntryService receiptEntryService;
    
    @Autowired
    private IFinancialYearService iFinancialYear;
    

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();// Main DTO to Bind
                                                                                                     // Data

    private ProvisionalAssesmentMstDto authCompBeforeDto = null;// DTO to compare Data in Authorization

    private List<ProvisionalAssesmentFactorDtlDto> authCompFactDto = new LinkedList<>();

    private List<TbBillMas> authComBillList = null;// Bill List to compare Data in Authorization

    private List<TbBillMas> billMasList = new LinkedList<>();// Bill Generation List

    private ProperySearchDto searchDto = new ProperySearchDto();

    private List<ProperySearchDto> searchDtoResult = new ArrayList<>();

    private List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = new LinkedList<>();// Factor DTO to Bind Factor
                                                                                           // separately

    private List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();

    private List<ProvisionalAssesmentMstDto> oldProAssMstDtoList = new ArrayList<>();

    private Map<String, Map<String, List<ChargeDetailDTO>>> chargesMap = new HashMap<>();

    private ServiceMaster serviceMaster = new ServiceMaster();

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();

    private Map<String, List<BillDisplayDto>> displayMapAuthComp = new HashMap<>();

    private Map<String, List<ProvisionalAssesmentFactorDtlDto>> displayfactorMap = new HashMap<>();

    private List<LookUp> location = new ArrayList<>(0);

    private List<LookUp> collectionDetails = new ArrayList<>(0);

    private List<String> unitNoList = new ArrayList<>(0);

    private List<Long> finYearList = new ArrayList<>(0);

    private List<CFCAttachment> documentList = new ArrayList<>();

    private List<BillReceiptPostingDTO> reabteRecDtoList = new ArrayList<>();

    private List<PropertyCommonInfoDto> propCommonDtoList = new ArrayList<>();

    private String noOfDaysEditableFlag;

    private String noOfDaysAuthEditFlag;

    BillDisplayDto earlyPayRebate = new BillDisplayDto();

    BillDisplayDto advanceAmt = new BillDisplayDto();

    BillDisplayDto surCharge = null;

    private Date manualReeiptDate;

    private Long currFinYearId;
    private Long serviceId;
    private Long orgId;
    private Long deptId;
    private Long factorId;
    private String factorPrefix;
    private String ownershipPrefixBefore;
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private List<LookUp> schedule = new ArrayList<>(0);
    private String proAssPropAddCheck;
    private String assMethod;
    private Date leastFinYear;// least Date of Fin year in System
    private String assType;// Assessment type N-New ,C-Change,NC- No Change
    private String authEditFlag;// Y -Authorization with edit,N-Authorization without Edit
    private String approvalFlag;// Y -Approval task
    private Integer unitDetailTableCount;

    private Integer ownerDetailTableCount;
    private Integer unitStatusCount;
    private String selfAss;
    private Long maxUnit;
    private boolean lastChecker;
    private String intgrtionWithBP;// Integration with Building Permission
    private String ConstructFlag;

    private Map<Long, String> financialYearMap = new LinkedHashMap<>();

    private CommonChallanDTO offlineDTO = new CommonChallanDTO();

    private Map<Long, List<DocumentDetailsVO>> appDocument = new LinkedHashMap<>();

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

    private String oldLandTypePrefix;

    private String changeLandTypePrefix;

    private double advanceAmount;

    private double lastBillAmountPaid;

    private PropertyPenltyDto propPenaltyDto = new PropertyPenltyDto();

    private double previousSurcharge;

    private double manualRecptArrearPaidAmnt;

    private double totalTaxPayableWithArrears;

    private List<LookUp> factorDtlDto = new ArrayList<>();

    private String factorNotApplicable;

    private List<NoticeMasterDto> noticeDetails = new ArrayList<>();

    private Date assesmentManualDate;

    private String assesmentManualNo;

    private String countOfRow;

    private String unitDetailsCheck;

    private String hearingDecisionFlag;

    private DocumentDetailsVO manualCheckList = new DocumentDetailsVO();

    private List<TbBillMas> previousBillMasList = new ArrayList<>();

    private String showTaxDetails;

    private String billReviseFlag;

    private String ownerName;

    private ProvisionalAssesmentMstDto reviseProvisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();

    private List<BillDisplayDto> revisedArrearList = new ArrayList<BillDisplayDto>();

    // For upload documents on update data entry form
    private List<DocumentDetailsVO> updateDataEntryDocs = new ArrayList<>();

    private List<TbServiceReceiptMasEntity> receiptPaidDetails = new ArrayList<TbServiceReceiptMasEntity>();

    private Map<Long, Double> financialYearBillPaidMap = new LinkedHashMap<>();

    private double lastReceiptAmount;

    Map<CommonChallanDTO, List<BillReceiptPostingDTO>> receiptInsertionList = new HashMap<CommonChallanDTO, List<BillReceiptPostingDTO>>();
    
    Map<CommonChallanDTO, List<BillReceiptPostingDTO>> manualReceiptInsertionList = new HashMap<CommonChallanDTO, List<BillReceiptPostingDTO>>();

    Map<String, Double> finYearWiseBillMap = new LinkedHashMap<>();

    private double totalBuildUpArea; // used in bifurcation
    private List<String> flatNoList;
    private String billingMethod;
    BillDisplayDto demandBAsedAdvanceAmt = new BillDisplayDto();
    
    private boolean authLevel;
    private boolean hideUserAction;
    private Map<Long, String> roomTypeMap = new LinkedHashMap<>();
    private String roomTypeJson;
    private String interWaiveOffAppl;
    private String serviceType = "N";
    private String referenceid;
    private String objectionFlag = "N";
    private Date currentFinStartDate;
    private Date currentFinEndDate;
    private BillPaymentDetailDto billPayDto;
    private double adjustedAdvanceAmnt;
    private String formType;
    private Long currentApprovalLevel;

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {
        case MainetConstants.Property.propPref.USA:
            return "provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype";

        case MainetConstants.Property.propPref.PTP:
            return "provisionalAssesmentMstDto.assPropType";
        default:
            return null;
        }
    }

    public LookUp getFactorLookup(String factorCode) {
        return CommonMasterUtility.getValueFromPrefixLookUp(factorCode, MainetConstants.Property.propPref.FCT,
                UserSession.getCurrent().getOrganisation());
    }

    public LookUp getOwnershipTypeLookup(String ownershipType) {
        return CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
    }

    public void setRoomTypeList(Organisation org) {
		List<LookUp> roomTypeList = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.Prefix.RTP,
				org.getOrgid());
		for(LookUp roomType : roomTypeList) {
				getRoomTypeMap().put(roomType.getLookUpId(), roomType.getOtherField());
		}
	}
    // to get text description of the selected value of a dropdownList
    public void setDropDownValues(Organisation org) {
    	if(provisionalAssesmentMstDto.getAssOwnerType() != null) {
    		this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
    				.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());
    		String ownerTypeCode = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
            if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
                for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
                        .getProvisionalAssesmentOwnerDtlDtoList()) {
                    if (dto.getGenderId() != null && dto.getGenderId() > 0) {
                        dto.setProAssGenderId(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
                    }
                    if (dto.getRelationId() != null && dto.getRelationId() > 0) {
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

        if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                            .getDescLangFirst());
        }

        this.setAssMethod(
                CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS, org).getLookUpCode());

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

        this.getProvisionalAssesmentMstDto().setAssWardDesc1(CommonMasterUtility
                .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());

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
        if(provisionalAssesmentMstDto.getAssPropType1() != null) {
        	 this.getProvisionalAssesmentMstDto().setAssPropType1Desc(CommonMasterUtility
                    .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1(), org).getDescLangFirst());
        }
        if(provisionalAssesmentMstDto.getAssPropType2() != null) {
       	 this.getProvisionalAssesmentMstDto().setAssPropType2Desc(CommonMasterUtility
                   .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType2(), org).getDescLangFirst());
       }
        if(provisionalAssesmentMstDto.getAssPropType3() != null) {
       	 this.getProvisionalAssesmentMstDto().setAssPropType3Desc(CommonMasterUtility
                   .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType3(), org).getDescLangFirst());
       }
        if(provisionalAssesmentMstDto.getAssPropType4() != null) {
       	 this.getProvisionalAssesmentMstDto().setAssPropType4Desc(CommonMasterUtility
                   .getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType4(), org).getDescLangFirst());
       }
        if(provisionalAssesmentMstDto.getAssPropType5() != null) {
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
                    if (detaildto.getFirstAssesmentDate() != null) {
                        detaildto.setFirstAssesmentStringDate(formatter.format(detaildto.getFirstAssesmentDate()));
                    }else {
                        detaildto.setFirstAssesmentDate(new Date());
                        detaildto.setProAssdConstructionDate(formatter.format(detaildto.getFirstAssesmentDate()));
                    }
                    if(detaildto.getLastAssesmentDate()!=null){
                    	detaildto.setLastAssesmentStringDate(formatter.format(detaildto.getLastAssesmentDate()));
                    }else {
                    	detaildto.setLastAssesmentDate(new Date());
                    	detaildto.setLastAssesmentStringDate(formatter.format(detaildto.getLastAssesmentDate()));
                    }
                    detaildto.setProAssdUsagetypeDesc(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(),
                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());

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
                    if (detaildto.getAssdFloorNo() != null) {
                        detaildto.setProFloorNo(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                    }
                    detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());

                    detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());
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

        setAddressDetails();
        formatDate();

    }

    private void setAddressDetails() {
        if (getProvisionalAssesmentMstDto().getProAsscheck().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
            getProvisionalAssesmentMstDto().setAssCorrAddress(getProvisionalAssesmentMstDto().getAssAddress());
            getProvisionalAssesmentMstDto().setAssCorrPincode(getProvisionalAssesmentMstDto().getAssPincode());
            getProvisionalAssesmentMstDto().setAssCorrEmail(getProvisionalAssesmentMstDto().getAssEmail());
        }
    }

    // Commented not required for Sudha

    /*
     * private void setBillPayment() { if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(
     * MainetConstants.Property.NOT_APP)) { getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants.
     * Property.NOT_APP_DESC); // blank model set data getProvisionalAssesmentMstDto().setAssLpReceiptNo(null);
     * getProvisionalAssesmentMstDto().setAssLpReceiptAmt(null); getProvisionalAssesmentMstDto().setAssLpReceiptDate(null);
     * getProvisionalAssesmentMstDto().setAssLpYear(null); getProvisionalAssesmentMstDto().setBillAmount(null);
     * getProvisionalAssesmentMstDto().setOutstandingAmount(null);
     * getProvisionalAssesmentMstDto().setProAssLpReceiptDateFormat(null);
     * getProvisionalAssesmentMstDto().setProAssLpYearDesc(null); } else if
     * (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
     * getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants. Property.MANUAL_DESC); } }
     */

    private void formatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        // Commented not required for Sudha
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
         * this.getProvisionalAssesmentMstDto() .setProAssLpReceiptDateFormat(formatter.format(getProvisionalAssesmentMstDto(
         * ).getAssLpReceiptDate())); }
         */
        this.getProvisionalAssesmentMstDto()
                .setProAssAcqDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssAcqDate()));
        if(null!=getProvisionalAssesmentMstDto().getReviseAssessmentDate()) {
        	this.getProvisionalAssesmentMstDto().setReviseAssessmentDateFormat(formatter.format(getProvisionalAssesmentMstDto().getReviseAssessmentDate()));
        }
        // Commented not required for Sudha
        /*
         * for (ProvisionalAssesmentDetailDto dto : this.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
         * dto.setProAssdConstructionDate(formatter.format(dto. getProAssdYearConstruction())); }
         */
    }

    @Override
    public boolean saveForm() {
        setCustomViewName("SelfAssessmentView");
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        final CommonChallanDTO offline = getOfflineDTO();
        List<DocumentDetailsVO> docs = getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        provisionalAssesmentMstDto.setDocs(docs);
        provisionalAssesmentMstDto.setManualReceiptDate(manualReeiptDate);
        if(StringUtils.equals(getAssType(), MainetConstants.Property.NEW_ASESS) && StringUtils.isNotBlank(provisionalAssesmentMstDto.getAssOldpropno())) {
			boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(provisionalAssesmentMstDto.getAssOldpropno(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if(checkOldPropNoExist) {
				addValidationError("Already PTIN exist with "+ provisionalAssesmentMstDto.getAssOldpropno());
			}
       }

        if(StringUtils.equals(getAssType(), MainetConstants.Property.NEW_ASESS)) {
        	AtomicBoolean dupOwnerAadharNo = new AtomicBoolean(false);
  		  if(provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().size() > 1) {
  			provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(ownerDto ->{
  				List<ProvisionalAssesmentOwnerDtlDto> ownerAadharList = provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList()
  						.stream().filter(owner -> owner.getAssoAddharno() != null && ownerDto.getAssoAddharno() != null && owner.getAssoAddharno().equals(ownerDto.getAssoAddharno()))
  						.collect(Collectors.toList());
  				
  				if(!dupOwnerAadharNo.get() && ownerAadharList.size() > 1) {
  					addValidationError("Same aadhar no for multiple owner is not allowed");
  					dupOwnerAadharNo.getAndSet(true);
  				}
  			  });
  		  }
        }
        
		/*
		 * if(StringUtils.equals(getAssType(), MainetConstants.Property.CHN_IN_ASESS)) {
		 * Long finYearId = iFinancialYear.getFinanceYearId(new Date());
		 * getFinYearList().clear(); getFinYearList().add(finYearId);
		 * List<ProvisionalAssesmentDetailDto> provDetailList =
		 * provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().stream().
		 * filter(det ->
		 * det.getFaYearId().equals(finYearId)).collect(Collectors.toList());
		 * provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().clear();
		 * provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().addAll(
		 * provDetailList); }
		 */
        
        
        if (MainetConstants.Property.Bifurcation.equals(getAssType())) {
            return saveBifurcation();
        } else {
            Map<Long, Double> details = new HashMap<>(0);
            final Map<Long, Long> billDetails = new HashMap<>(0);
            setOfflineDtoBeforeSave(offline, provisionalAssesmentMstDto);
            offline.setManualReeiptDate(manualReeiptDate);
            if (provisionalAssesmentMstDto.getPaymentAppicableCheck() != null
                    && MainetConstants.FlagN.equals(provisionalAssesmentMstDto.getPaymentAppicableCheck())) {
                offline.setOnlineOfflineCheck(null);
            }
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
            	if ((provisionalAssesmentMstDto.getBillTotalAmt() > 0 && manualReeiptDate == null)
                        || (manualReeiptDate != null && provisionalAssesmentMstDto.getBillPartialAmt() > 0)) {
                    validateBean(offline, CommonOfflineMasterValidator.class);
                }
            }
            
            // 97207 By Arun - to skip payment
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.PSC)) {
                provisionalAssesmentMstDto.setPaymentCheck(MainetConstants.FlagY);
            }
            // end
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
            	validateBean(provisionalAssesmentMstDto, PropertyAssessmentValidator.class);
            }
            if (manualReeiptDate != null && provisionalAssesmentMstDto.getBillPartialAmt() <= 0
                    && offline.getOnlineOfflineCheck() != null) {
                addValidationError(getAppSession().getMessage("water.billPayment.amount"));
            }
            List<DocumentDetailsVO> manualDocs = null;
            if (getAssesmentManualDate() != null) {
                if (StringUtils.isEmpty(getAssesmentManualNo())) {
                    addValidationError("Manual Receipt No must not be empty");
                }
            }

            if (hasValidationErrors()) {
                return false;
            }

            if (CollectionUtils.isNotEmpty(docs) && getAssesmentManualDate() != null) {
                List<DocumentDetailsVO> manualCheckList = docs.stream()
                        .filter(doc -> doc.getDoc_DESC_ENGL().equalsIgnoreCase("Manual Receipt")).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(manualCheckList)) {
                    setManualCheckList(manualCheckList.get(0));
                    manualDocs = new ArrayList<DocumentDetailsVO>();
                    manualDocs.add(getManualCheckList());
                    manualDocs = fileUpload.prepareFileUpload(manualDocs);
                    if (getManualCheckList() == null || StringUtils.isBlank(getManualCheckList().getDocumentName())) {
                        addValidationError("Please upload manual receipt");
                    } else {
                        docs.remove(docs.size() - 1);
                    }
                }
            }

            if (hasValidationErrors()) {
                return false;
            }
            
            if(adjustedAdvanceAmnt > 0) {
            	asExecssAmtService.updateExecssAmtByAdjustedAmt(getProvisionalAssesmentMstDto().getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid(), adjustedAdvanceAmnt, UserSession.getCurrent().getEmployee().getEmpId(), getClientIpAddress(), null);
            }
            if(MapUtils.isNotEmpty(getReceiptInsertionList())) {
    			for (Entry<CommonChallanDTO, List<BillReceiptPostingDTO>> printingDto : getReceiptInsertionList()
    					.entrySet()) {			
    				CommonChallanDTO offlineDto = printingDto.getKey();
    				//selfAssessmentService.mappingDummyKeyToActualKey(provBillList, printingDto.getValue());
    				TbServiceReceiptMasEntity rcptMastEntity = receiptEntryService.insertInReceiptMaster(offlineDto,
    						printingDto.getValue());
    				ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class).setReceiptDtoAndSaveDuplicateService(rcptMastEntity, offlineDto);
    			}
    		}
            // service call not participating in transaction
            // save assessment and call workflow
            List<Long> bmIds = selfAssessmentService.saveSelfAssessment(provisionalAssesmentMstDto, offline,
                    finYearList, billMasList, reabteRecDtoList, advanceAmt);
           
            offline.setManualReeiptDate(null);

			if (demandBAsedAdvanceAmt != null && demandBAsedAdvanceAmt.getCurrentYearTaxAmt() != null
					&& demandBAsedAdvanceAmt.getCurrentYearTaxAmt().doubleValue() > 0) {
				ApplicationContextProvider.getApplicationContext().getBean(AsExecssAmtService.class)
						.addEntryInExcessAmt(UserSession.getCurrent().getOrganisation().getOrgid(),
								provisionalAssesmentMstDto.getAssNo(),
								demandBAsedAdvanceAmt.getCurrentYearTaxAmt().doubleValue(), null,
								UserSession.getCurrent().getEmployee().getEmpId());
			}
            // demand account posting is also not participating in transaction
			billMasterCommonService.doVoucherPosting(bmIds, UserSession.getCurrent().getOrganisation(),
					MainetConstants.Property.PROP_DEPT_SHORT_CODE, UserSession.getCurrent().getEmployee().getEmpId(),
					UserSession.getCurrent().getLoggedLocId());

            // call bill knock off and receipt entry/challan entry
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
				if (provisionalAssesmentMstDto.getBillTotalAmt() > 0) {
					setOfflineDtoAfterSave(offline, details, billDetails, provisionalAssesmentMstDto);
					setChallanDToandSaveChallanData(offline);
				}
			}
            // In case of individual billing where multiple application id's are present
            if (provisionalAssesmentMstDto.getBillMethod() != null
                    && MainetConstants.FlagI.equals(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getBillMethod(),
                                    UserSession.getCurrent().getOrganisation())
                            .getLookUpCode())
                    && provisionalAssesmentMstDto.getBillPartialAmt() <= 0) {
                Map<String, Long> flatWiseAppIdmap = new LinkedHashMap<>();
                provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
                    flatWiseAppIdmap.put(detail.getFlatNo(), detail.getApmApplicationId());
                });
                for (Map.Entry<String, Long> map : flatWiseAppIdmap.entrySet()) {
                    iChallanService.updateOnlinePaymentCFCStatus(map.getValue(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                }
            } else if (provisionalAssesmentMstDto.getBillPartialAmt() <= 0) {
                iChallanService.updateOnlinePaymentCFCStatus(provisionalAssesmentMstDto.getApmApplicationId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
            }

            if (getAssesmentManualDate() != null) {
                RequestDTO dto = new RequestDTO();
                dto.setDeptId(offline.getDeptId());
                dto.setServiceId(offline.getServiceId());
                dto.setReferenceId(getReceiptDTO().getReceiptId().toString());
                dto.setOrgId(offline.getOrgId());
                dto.setUserId(offline.getUserId());
                dto.setLangId(Long.valueOf(offline.getLangId()));
                fileUpload.doFileUpload(manualDocs, dto);
            }
            // T#89750
            if (MainetConstants.Common_Constant.YES
                    .equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
                // call JASPER method for bill
                ProperySearchDto propDto = new ProperySearchDto();
                propDto.setBmIdNo(bmIds.get(0));
                propDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
                propDto.setOrg(UserSession.getCurrent().getOrganisation());
                ProperySearchDto properySearchDto = viewPropertyDetailsService.getAndGenearteJasperForBill(propDto);
                String filePath = Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR
                        + properySearchDto.getFilePath();
                if (provisionalAssesmentMstDto.getAssNo() != null) {
                    String idfId = MainetConstants.DMS_LIST + MainetConstants.FILE_PATH_SEPARATOR
                            + provisionalAssesmentMstDto.getAssNo();
                    FileUploadDTO uploadDTO = Utility.dataSetForDMS(filePath, idfId,
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE);
                    setCommonFileAttachment(ApplicationContextProvider.getApplicationContext()
                            .getBean(IFileUploadService.class).setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
                    fileUpload.doMasterFileUpload(getCommonFileAttachment(), uploadDTO);
                }
            }

        }
        selfAssessmentService.sendMail(provisionalAssesmentMstDto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getEmployee().getEmpId());
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "APM")) {
            setSuccessMessage(
                    getAppSession().getMessage("prop.save.selfNew") + provisionalAssesmentMstDto.getAssNo());
        } else {
            setSuccessMessage(
                    getAppSession().getMessage("prop.save.self") + provisionalAssesmentMstDto.getApmApplicationId());
        }
        return true;
    }

    private boolean saveBifurcation() {
        if ((provisionalAssesmentMstDto.getDocs() != null) && !provisionalAssesmentMstDto.getDocs().isEmpty()) {
            for (final DocumentDetailsVO doc : provisionalAssesmentMstDto.getDocs()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.CommonConstants.Y)
                        && doc.getDocumentByteCode() == null) {
                    addValidationError(ApplicationSession.getInstance().getMessage("property.mandtory.docs"));
                    break;
                }
            }
        }
        if (hasValidationErrors()) {
            return false;
        }
        // ----------//
        final CommonChallanDTO offline = getOfflineDTO();
        List<DocumentDetailsVO> docs = getCheckList();
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        docs = fileUpload.prepareFileUpload(docs);
        provisionalAssesmentMstDto.setDocs(docs);
        provisionalAssesmentMstDto.setManualReceiptDate(manualReeiptDate);
        setOfflineDtoBeforeSave(offline, provisionalAssesmentMstDto);
        if ((provisionalAssesmentMstDto.getBillTotalAmt() > 0 && manualReeiptDate == null)
                || (manualReeiptDate != null && provisionalAssesmentMstDto.getBillPartialAmt() > 0)) {
            validateBean(offline, CommonOfflineMasterValidator.class);
        }
        // Payment skip check for SKDCL/ASCL
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.PSC)) {
                provisionalAssesmentMstDto.setPaymentCheck(MainetConstants.FlagY);
            }
        }
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),"PSCL")) {
        	validateBean(provisionalAssesmentMstDto, PropertyAssessmentValidator.class);
        }
        if (manualReeiptDate != null && provisionalAssesmentMstDto.getBillPartialAmt() <= 0
                && offline.getOnlineOfflineCheck() != null) {
            addValidationError(getAppSession().getMessage("water.billPayment.amount"));
        }
        if (hasValidationErrors()) {
            return false;
        }
        // validateBean(provisionalAssesmentMstDto, PropertyAssessmentValidator.class);
        // bifurcationService.saveBifurcationAssessment(provisionalAssesmentMstDto,
        // UserSession.getCurrent().getOrganisation().getOrgid(),
        // UserSession.getCurrent().getEmployee().getEmpId(), this.getDeptId(),
        // UserSession.getCurrent().getLanguageId(), finYearList);
        // List<Long> bmIds = selfAssessmentService.saveSelfAssessment(provisionalAssesmentMstDto, offline,
        // finYearList, billMasList, reabteRecDtoList, advanceAmt);
        List<Long> bmIds = new ArrayList<>(0);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
            bmIds = bifurcationService.saveBifurcationForSkdcl(provisionalAssesmentMstDto, offline, finYearList,
                    billMasList,
                    reabteRecDtoList, advanceAmt);
        } else {
            bmIds = bifurcationService.saveBifurcation(provisionalAssesmentMstDto, offline, finYearList, billMasList,
                    reabteRecDtoList, advanceAmt);
        }

        offline.setManualReeiptDate(null);

        // demand account posting is also not participating in transaction
        billMasterCommonService.doVoucherPosting(bmIds, UserSession.getCurrent().getOrganisation(),
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, UserSession.getCurrent().getEmployee().getEmpId(),
                UserSession.getCurrent().getLoggedLocId());
        if (provisionalAssesmentMstDto.getBillTotalAmt() > 0) {
            setOfflineDtoAfterSave(offline, details, billDetails, provisionalAssesmentMstDto);
            setChallanDToandSaveChallanData(offline);
        }

        selfAssessmentService.sendMail(provisionalAssesmentMstDto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getEmployee().getEmpId());
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
            setSuccessMessage(
                    getAppSession().getMessage("property.bif.save.success") + provisionalAssesmentMstDto.getParentProp());
        } else {
            setSuccessMessage(
                    getAppSession().getMessage("prop.save.self") + provisionalAssesmentMstDto.getApmApplicationId());
        }
        return true;
    }

    private void saveApproval(Employee emp) {
        if (getAuthEditFlag().equals(MainetConstants.FlagN)) {
            propertyAuthorizationService.SaveApprovalData(getProvAssMstDtoList(), this.getProvisionalAssesmentMstDto(),
                    getWorkflowActionDto(), orgId, emp, this.getDeptId(), UserSession.getCurrent().getLanguageId());
        } else if (getAuthEditFlag().equals(MainetConstants.FlagY)) {
            propertyAuthorizationService.SaveApprovalDataWithEdit(this.getProvAssMstDtoList(),
                    this.getProvisionalAssesmentMstDto(), finYearList, this.getBillMasList(), this.getAuthComBillList(),
                    getWorkflowActionDto(), emp, this.getDeptId(), reabteRecDtoList, earlyPayRebate, advanceAmt,
                    surCharge, UserSession.getCurrent().getLanguageId());
        }
    }

    public boolean saveAuthorization() {
        setCustomViewName("SelfAssessmentView");
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
        		isHideUserAction() && isAuthLevel()) {
       	 workFlowActionDto.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
       	workFlowActionDto.setComments("Ok");
            setAuthEditFlag(MainetConstants.FlagY);
       }
        if (!MainetConstants.Property.APPROVAL.equals(getApprovalFlag())) {
            validateBean(workFlowActionDto, CheckerActionValidator.class);
        }
        if (hasValidationErrors()) {
            return false;
        }
        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);
        docs = fileUpload.prepareFileUpload(docs);
        this.getProvisionalAssesmentMstDto().setDocs(docs);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Employee emp = UserSession.getCurrent().getEmployee();
        propertyAuthorizationService.saveUploadedFile(this.getProvisionalAssesmentMstDto(), orgId, emp,
                this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
        if (MainetConstants.Property.Bifurcation.equals(getSelfAss())) {
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
                bifurcationService.SaveAuthorizationForSkdcl(this.getProvAssMstDtoList(),
                        this.getProvisionalAssesmentMstDto(), workFlowActionDto, orgId, emp, this.getDeptId(),
                        UserSession.getCurrent().getLanguageId(), this.getServiceId(), getAuthEditFlag());
            } else {
                bifurcationService.SaveAuthorizationWithEdit(this.getProvAssMstDtoList(),
                        this.getProvisionalAssesmentMstDto(), workFlowActionDto, orgId, emp, this.getDeptId(),
                        UserSession.getCurrent().getLanguageId(), this.getServiceId(), getAuthEditFlag(),this.getAuthComBillList());
            }

        } else if (MainetConstants.Property.APPROVAL.equals(getApprovalFlag())) {
            saveApproval(emp);// approval of bill after objection
        } else {

            // 118973 - To call objection workflow even if generate special notice is not selected on UI
            Organisation org = organisationService.getOrganisationById(orgId);
            if (Utility.isEnvPrefixAvailable(org, "SNM")) {
                if (!MainetConstants.FlagY.equals(getNoOfDaysEditableFlag())
                        && !MainetConstants.FlagY.equals(getNoOfDaysAuthEditFlag())
                        && MainetConstants.FlagA.equals(getAssType()) && (isLastChecker())
                        && !MainetConstants.Property.Bifurcation.equals(getSelfAss())
                        && !MainetConstants.Property.APPROVAL.equals(getApprovalFlag())) {
                    setAuthEditFlag(MainetConstants.FlagY);
                    if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
						// In case of edit assesment it knock off the amount which have been paid at the
						// time of assesment. In SKDCL, it is knocking off demand level rebate again.
                    	this.getProvisionalAssesmentMstDto().setBillPaidAmt(0.0);
                    }
                }
            }

            if (getAuthEditFlag().equals(MainetConstants.FlagN)) {
                propertyAuthorizationService.SaveAuthorizationData(this.getProvAssMstDtoList(),
                        this.getProvisionalAssesmentMstDto(), this.getAuthComBillList(), workFlowActionDto, orgId, emp,
                        this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
            } else if (getAuthEditFlag().equals(MainetConstants.FlagY)) {
                List<Long> bmidsAuth = propertyAuthorizationService.SaveAuthorizationWithEdit(
                        this.getProvAssMstDtoList(), this.getProvisionalAssesmentMstDto(), finYearList,
                        this.getBillMasList(), this.getAuthComBillList(), workFlowActionDto, emp, this.getDeptId(),
                        UserSession.getCurrent().getLanguageId(), reabteRecDtoList, earlyPayRebate, advanceAmt,
                        surCharge);
                if (bmidsAuth != null && !bmidsAuth.isEmpty()) {
                    billMasterCommonService.doVoucherPosting(bmidsAuth, UserSession.getCurrent().getOrganisation(),
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            UserSession.getCurrent().getEmployee().getEmpId(),
                            UserSession.getCurrent().getLoggedLocId());
                }
            }
        }
        // D#104615
        this.getProvisionalAssesmentMstDto().setDecision(workFlowActionDto.getDecision());
        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
                && getAuthEditFlag().equals(MainetConstants.FlagN)
                && isLastChecker()) {
            propertyAuthorizationService.sendMail(this.getProvisionalAssesmentMstDto(),
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getEmployee().getEmpId());
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SMS")
                && (workFlowActionDto.getDecision().equals("REJECTED") ||
                        workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED))
                && getAuthEditFlag().equals(MainetConstants.FlagN)
                && isLastChecker()) {
            this.getProvisionalAssesmentMstDto().setRemarks(workFlowActionDto.getComments());
            propertyAuthorizationService.sendMail(this.getProvisionalAssesmentMstDto(),
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getEmployee().getEmpId());
        }
      
        return true;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline) {
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED);
            final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master.getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    getServiceMaster().getSmServiceName());
            setReceiptDTO(printDto);
            // US#102200 // pushing document to DMS
            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
                    + ApplicationSession.getInstance().getMessage("property.birtName.billPayment")
                    + "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTID=" + printDto.getReceiptId();
            Utility.pushDocumentToDms(URL, printDto.getPropNo_connNo_estateN_V(),
                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, fileUpload);

        }
        setOfflineDTO(offline);
    }

    private void setOfflineDtoBeforeSave(final CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDto) {
        final UserSession session = UserSession.getCurrent();
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation().getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        if (offline.getOflPaymentMode() > 0) {
            final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
            offline.setOfflinePaymentText(modeDesc);
        }
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            offline.setDocumentUploaded(true);
        }
        offline.setServiceId(getServiceId());
        offline.setDeptId(getDeptId());
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        offline.setApplicantName(ownDtlDto.getAssoOwnerName());
        offline.setMobileNumber(ownDtlDto.getAssoMobileno());
        offline.setNewHouseNo(asseMstDto.getNewHouseNo());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        WardZoneBlockDTO dwzDto = new WardZoneBlockDTO();
        if (asseMstDto.getAssWard1() != null) {
            dwzDto.setAreaDivision1(asseMstDto.getAssWard1());
        }
        if (asseMstDto.getAssWard2() != null) {
            dwzDto.setAreaDivision2(asseMstDto.getAssWard2());
        }
        if (asseMstDto.getAssWard3() != null) {
            dwzDto.setAreaDivision3(asseMstDto.getAssWard3());
        }
        if (asseMstDto.getAssWard4() != null) {
            dwzDto.setAreaDivision4(asseMstDto.getAssWard4());
        }
        if (asseMstDto.getAssWard5() != null) {
            dwzDto.setAreaDivision5(asseMstDto.getAssWard5());
        }
        
        
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PWZ")) {
        	 if (asseMstDto.getAssParshadWard1() != null) {
                 dwzDto.setAreaDivision1(asseMstDto.getAssParshadWard1());
             }
        	 if (asseMstDto.getAssParshadWard2() != null) {
                 dwzDto.setAreaDivision2(asseMstDto.getAssParshadWard2());
             }
        	 if (asseMstDto.getAssParshadWard3() != null) {
                 dwzDto.setAreaDivision3(asseMstDto.getAssParshadWard3());
             }
        	 if (asseMstDto.getAssParshadWard4() != null) {
                 dwzDto.setAreaDivision4(asseMstDto.getAssParshadWard4());
             }
        	 if (asseMstDto.getAssParshadWard5() != null) {
                 dwzDto.setAreaDivision5(asseMstDto.getAssParshadWard5());
             }
		}
        offline.setDwzDTO(dwzDto);
        if (CollectionUtils.isNotEmpty(asseMstDto.getProvisionalAssesmentOwnerDtlDtoList())) {
            StringBuilder jointOwnerName = new StringBuilder();
            LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(asseMstDto.getAssOwnerType(),
                    UserSession.getCurrent().getOrganisation());
            if (CollectionUtils.isNotEmpty(asseMstDto.getProvisionalAssesmentOwnerDtlDtoList())) {
                if (lookUp.getLookUpCode().equals(MainetConstants.Property.JO)) {
                    asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(data -> {
                        jointOwnerName.append(data.getAssoOwnerName() == null ? MainetConstants.BLANK
                                : data.getAssoOwnerName() + MainetConstants.WHITE_SPACE);
                        jointOwnerName.append(data.getAssoGuardianName() == null ? MainetConstants.BLANK
                                : data.getAssoGuardianName());
                    });
                    offline.setApplicantFullName(jointOwnerName.toString());
                } else {
                    offline.setApplicantFullName(
                            asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName()
                                    + MainetConstants.WHITE_SPACE
                                    + asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoGuardianName());
                }

                StringBuilder applicantName = new StringBuilder();
                AtomicInteger ownerSize = new AtomicInteger(1);
                asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(ownerDetail -> {
                    applicantName.append(ownerDetail.getAssoOwnerName());
                    applicantName.append(MainetConstants.WHITE_SPACE);
                    if (ownerDetail.getProAssRelationId() != null) {
                        // LookUp relationLookUp =
                        // CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(ownerDetail.getProAssRelationId()),
                        // session.getOrganisation());
                        applicantName.append(ownerDetail.getProAssRelationId());
                    } else {
                        applicantName.append("Contact person - ");
                    }
                    applicantName.append(MainetConstants.WHITE_SPACE);
                    applicantName.append(ownerDetail.getAssoGuardianName());
                    if (ownerSize.intValue() < asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().size()) {
                        applicantName.append("," + " ");
                    }
                    ownerSize.addAndGet(1);

                });
                offline.setApplicantName(applicantName.toString());
                offline.setPdRv(asseMstDto.getProvisionalAssesmentDetailDtoList().get(0).getAssdAlv());
            }
        }

        offline.setPlotNo(asseMstDto.getTppPlotNo());
    }

    private void setOfflineDtoAfterSave(final CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, ProvisionalAssesmentMstDto asseMstDto) {
        offline.setAmountToPay(Double.toString(asseMstDto.getBillPartialAmt()));

        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        if (CollectionUtils.isNotEmpty(asseMstDto.getProvisionalAssesmentOwnerDtlDtoList())) {
            StringBuilder jointOwnerName = new StringBuilder();
            LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(asseMstDto.getAssOwnerType(),
                    UserSession.getCurrent().getOrganisation());
            if (lookUp.getLookUpCode().equals(MainetConstants.Property.JO)) {
                asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(data -> {
                    jointOwnerName.append(data.getAssoOwnerName() == null ? MainetConstants.BLANK
                            : data.getAssoOwnerName() + MainetConstants.WHITE_SPACE);
                    jointOwnerName.append(
                            data.getAssoGuardianName() == null ? MainetConstants.BLANK : data.getAssoGuardianName());
                });
                offline.setApplicantFullName(jointOwnerName.toString());
            } else {
                offline.setApplicantFullName(
                        asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName()
                                + MainetConstants.WHITE_SPACE
                                + asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoGuardianName());
            }
            StringBuilder applicantName = new StringBuilder();
            AtomicInteger ownerSize = new AtomicInteger(1);
            asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(ownerDetail -> {
                applicantName.append(ownerDetail.getAssoOwnerName());
                applicantName.append(MainetConstants.WHITE_SPACE);
                if (ownerDetail.getProAssRelationId() != null) {
                    applicantName.append(ownerDetail.getProAssRelationId());
                } else {
                    applicantName.append("Contact person - ");
                }
                applicantName.append(MainetConstants.WHITE_SPACE);
                applicantName.append(ownerDetail.getAssoGuardianName());
                if (ownerSize.intValue() < asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().size()) {
                    applicantName.append("," + " ");
                }
                ownerSize.addAndGet(1);

            });
            offline.setApplicantName(applicantName.toString());
            offline.setPdRv(asseMstDto.getProvisionalAssesmentDetailDtoList().get(0).getAssdAlv());
        }
        offline.setPlotNo(asseMstDto.getTppPlotNo());
        offline.setUniquePrimaryId(asseMstDto.getAssNo());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setEmailId(asseMstDto.getAssEmail());
        offline.setApplNo(asseMstDto.getApmApplicationId());
        offline.setApplicantAddress(asseMstDto.getAssAddress());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("propertydetails.PropertyNo."));
        offline.setPropNoConnNoEstateNoV(asseMstDto.getAssNo());
        offline.setReferenceNo(asseMstDto.getAssOldpropno());
        offline.setManualReceiptNo(getAssesmentManualNo());
        offline.setManualReeiptDate(getAssesmentManualDate());

        double demandLevelRebate = this.getReabteRecDtoList().stream().map(BillReceiptPostingDTO::getTaxAmount)
                .collect(Collectors.toList()).stream().mapToDouble(i -> i).sum();
        // to print rebate on revenue receipt
        asseMstDto.getProvisionalAssesmentDetailDtoList().forEach(det -> {
            if (offline.getUsageType() == null) {
                offline.setUsageType(det.getProAssdUsagetypeDesc());
            } else if (!offline.getUsageType().contains(det.getProAssdUsagetypeDesc())) {
                offline.setUsageType(offline.getUsageType() + "," + det.getProAssdUsagetypeDesc());
            }
        });// to print all usage in revenue receipt
        offline.setDemandLevelRebate(demandLevelRebate);
        if (offline.getOflPaymentMode() > 0) {
            offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                    offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
        }
    }

    public void setSelectionValues() {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentOwnerDtlDtoList()) {
            dto.setProAssGenderId(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
            dto.setProAssRelationId(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org).getDescLangFirst());
        }
        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getDescLangFirst());
            }
        });
        this.getProvisionalAssesmentMstDto()
                .setProAssdRoadfactorDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
                        .getDescLangFirst());

        this.getProvisionalAssesmentMstDto().setProAssPropType(
                CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssPropType1(),
                        UserSession.getCurrent().getOrganisation()).getDescLangFirst());

        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList()) {

            for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                    detaildto.setProFaYearIdDesc(entry.getValue());
                }
            }
            detaildto.setProAssdUsagetypeDesc(
                    CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getDescLangFirst());

            detaildto.setProFloorNo(CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org)
                    .getDescLangFirst());
            detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());
            detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());

        }
        for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
            if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                        .getDescLangFirst());
                provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                        .getDescLangFirst());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        for (ProvisionalAssesmentDetailDto dto : this.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList()) {
            dto.setProAssdConstructionDate(formatter.format(dto.getAssdYearConstruction()));

            dto.setProAssEffectiveDateDesc(formatter.format(dto.getAssEffectiveDate()));
        }
    }

    public boolean saveNoOfDaysEditForm() {
        LookUp billDeletionInactive = null;
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {

        }
        boolean workflowInitiateFlag = false;
        Long applicationNo = 0L;
        RequestDTO reqDto = new RequestDTO();
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        final CommonChallanDTO offline = getOfflineDTO();
        offline.setApplNo(provisionalAssesmentMstDto.getApmApplicationId());
        if (((billDeletionInactive == null || ((StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagN))
                || (provisionalAssesmentMstDto.getBillPartialAmt() > 0)))
                && provisionalAssesmentMstDto.getBillTotalAmt() > 0
                && provisionalAssesmentMstDto.getManualReceiptDate() == null)) {
            validateBean(offline, CommonOfflineMasterValidator.class);
            validateBean(provisionalAssesmentMstDto, PropertyAssessmentValidator.class);
        }
        if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
            List<DocumentDetailsVO> docs = getCheckList();
            docs = fileUpload.prepareFileUpload(docs);
            provisionalAssesmentMstDto.setDocs(docs);
            setRequestApplicantDetails(reqDto, this.getProvisionalAssesmentMstDto(), orgId, deptId,
                    UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getEmployee().getEmpId(),
                    serviceId);

            if (this.getProvisionalAssesmentMstDto().getApmApplicationId() == null
                    || this.getProvisionalAssesmentMstDto().getApmApplicationId() == 0) {

                applicationNo = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
                        .createApplication(reqDto);
                this.getProvisionalAssesmentMstDto().setApmApplicationId(applicationNo);
            }
            if (StringUtils.isBlank(this.getProvisionalAssesmentMstDto().getAssAutStatus())) {
                workflowInitiateFlag = true;
            }
        }

        if (hasValidationErrors()) {
            return false;
        }

        setOfflineDtoBeforeSave(offline, provisionalAssesmentMstDto);
        List<Long> bmIds = provisionalAssesmentMstService.saveNoOfDaysEditForm(this.getOldProAssMstDtoList(),
                this.getProvisionalAssesmentMstDto(), this.getAuthComBillList(), this.getBillMasList(),
                UserSession.getCurrent().getEmployee(), this.getFinYearList(), advanceAmt, earlyPayRebate,
                reabteRecDtoList, advanceAmount, propPenaltyDto, getClientIpAddress(),
                UserSession.getCurrent().getEmployee().getEmpId(), lastBillAmountPaid, previousSurcharge);

        if (workflowInitiateFlag && billDeletionInactive != null
                && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
            offline.setApplNo(applicationNo);
            if ((this.getProvisionalAssesmentMstDto().getDocs() != null)
                    && !this.getProvisionalAssesmentMstDto().getDocs().isEmpty()) {
                reqDto.setApplicationId(applicationNo);
                fileUpload.doFileUpload(this.getProvisionalAssesmentMstDto().getDocs(), reqDto);
            }
            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
                    .initiateAndUpdateWorkFlowProcess(offline, null);
        }
        billMasterCommonService.doVoucherPosting(bmIds, UserSession.getCurrent().getOrganisation(),
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, UserSession.getCurrent().getEmployee().getEmpId(),
                UserSession.getCurrent().getLoggedLocId());

        if (provisionalAssesmentMstDto.getBillPartialAmt() > 0) {
            setOfflineDtoAfterSave(offline, details, billDetails, provisionalAssesmentMstDto);
            setChallanDToandSaveChallanData(offline);
        } else {
            iChallanService.updateOnlinePaymentCFCStatus(applicationNo,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }

        return true;
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
        if (asseMstDto.getBillTotalAmt() <= 0) {
            reqDto.setPayStatus(MainetConstants.FlagN);
        }
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(empId);
        if (asseMstDto.getApmDraftMode() != null) {
            reqDto.setApmMode(asseMstDto.getApmDraftMode());
        }
    }
    
    public boolean saveAuthorizationForObjection() {
    	boolean flag = false;
    	
		long organisationId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long serviceId = serviceMasterService.getServiceIdByShortName(organisationId, "OAB");
			if (demandBAsedAdvanceAmt != null && demandBAsedAdvanceAmt.getCurrentYearTaxAmt() != null
					&& demandBAsedAdvanceAmt.getCurrentYearTaxAmt().doubleValue() > 0) {
				ApplicationContextProvider.getApplicationContext().getBean(AsExecssAmtService.class)
						.addEntryInExcessAmt(UserSession.getCurrent().getOrganisation().getOrgid(),
								provisionalAssesmentMstDto.getAssNo(),
								demandBAsedAdvanceAmt.getCurrentYearTaxAmt().doubleValue(), null,
								UserSession.getCurrent().getEmployee().getEmpId());
			}
			getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			getWorkflowActionDto().setDecisionFavorFlag(MainetConstants.FlagD);
			getWorkflowActionDto().setReferenceId(getReferenceid());
    	propertyAuthorizationService.callWorkflow(0L, getWorkflowActionDto(), organisationId, UserSession.getCurrent().getEmployee(), serviceId);
    	
    	return true;
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

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
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

    public ServiceMasterRepository getServiceMasterRepository() {
        return serviceMasterRepository;
    }

    public void setServiceMasterRepository(ServiceMasterRepository serviceMasterRepository) {
        this.serviceMasterRepository = serviceMasterRepository;
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

    public LookUp getdesc(String id, String perfix) {
        return CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(id), perfix,
                getUserSession().getOrganisation().getOrgid());
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

    public List<ProvisionalAssesmentMstDto> getOldProAssMstDtoList() {
        return oldProAssMstDtoList;
    }

    public void setOldProAssMstDtoList(List<ProvisionalAssesmentMstDto> oldProAssMstDtoList) {
        this.oldProAssMstDtoList = oldProAssMstDtoList;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public ProvisionalAssesmentMstDto getAuthCompBeforeDto() {
        return authCompBeforeDto;
    }

    public void setAuthCompBeforeDto(ProvisionalAssesmentMstDto authCompBeforeDto) {
        this.authCompBeforeDto = authCompBeforeDto;
    }

    public IFileUploadService getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(IFileUploadService fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getAuthEditFlag() {
        return authEditFlag;
    }

    public void setAuthEditFlag(String authEditFlag) {
        this.authEditFlag = authEditFlag;
    }

    public List<TbBillMas> getAuthComBillList() {
        return authComBillList;
    }

    public void setAuthComBillList(List<TbBillMas> authComBillList) {
        this.authComBillList = authComBillList;
    }

    public Map<String, List<BillDisplayDto>> getDisplayMapAuthComp() {
        return displayMapAuthComp;
    }

    public void setDisplayMapAuthComp(Map<String, List<BillDisplayDto>> displayMapAuthComp) {
        this.displayMapAuthComp = displayMapAuthComp;
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

    public List<ProvisionalAssesmentFactorDtlDto> getAuthCompFactDto() {
        return authCompFactDto;
    }

    public void setAuthCompFactDto(List<ProvisionalAssesmentFactorDtlDto> authCompFactDto) {
        this.authCompFactDto = authCompFactDto;
    }

    public List<BillReceiptPostingDTO> getReabteRecDtoList() {
        return reabteRecDtoList;
    }

    public void setReabteRecDtoList(List<BillReceiptPostingDTO> reabteRecDtoList) {
        this.reabteRecDtoList = reabteRecDtoList;
    }

    public BillDisplayDto getEarlyPayRebate() {
        return earlyPayRebate;
    }

    public void setEarlyPayRebate(BillDisplayDto earlyPayRebate) {
        this.earlyPayRebate = earlyPayRebate;
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

    public boolean isLastChecker() {
        return lastChecker;
    }

    public void setLastChecker(boolean lastChecker) {
        this.lastChecker = lastChecker;
    }

    public Map<Long, List<DocumentDetailsVO>> getAppDocument() {
        return appDocument;
    }

    public void setAppDocument(Map<Long, List<DocumentDetailsVO>> appDocument) {
        this.appDocument = appDocument;
    }

    public String getIntgrtionWithBP() {
        return intgrtionWithBP;
    }

    public void setIntgrtionWithBP(String intgrtionWithBP) {
        this.intgrtionWithBP = intgrtionWithBP;
    }

    public List<PropertyCommonInfoDto> getPropCommonDtoList() {
        return propCommonDtoList;
    }

    public void setPropCommonDtoList(List<PropertyCommonInfoDto> propCommonDtoList) {
        this.propCommonDtoList = propCommonDtoList;
    }

    public String getNoOfDaysEditableFlag() {
        return noOfDaysEditableFlag;
    }

    public void setNoOfDaysEditableFlag(String noOfDaysEditableFlag) {
        this.noOfDaysEditableFlag = noOfDaysEditableFlag;
    }

    public String getNoOfDaysAuthEditFlag() {
        return noOfDaysAuthEditFlag;
    }

    public void setNoOfDaysAuthEditFlag(String noOfDaysAuthEditFlag) {
        this.noOfDaysAuthEditFlag = noOfDaysAuthEditFlag;
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

    public String getOwnershipPrefixBefore() {
        return ownershipPrefixBefore;
    }

    public void setOwnershipPrefixBefore(String ownershipPrefixBefore) {
        this.ownershipPrefixBefore = ownershipPrefixBefore;
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

    public String getChangeLandTypePrefix() {
        return changeLandTypePrefix;
    }

    public void setChangeLandTypePrefix(String changeLandTypePrefix) {
        this.changeLandTypePrefix = changeLandTypePrefix;
    }

    public String getOldLandTypePrefix() {
        return oldLandTypePrefix;
    }

    public void setOldLandTypePrefix(String oldLandTypePrefix) {
        this.oldLandTypePrefix = oldLandTypePrefix;
    }

    public BillDisplayDto getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(BillDisplayDto advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public BillDisplayDto getSurCharge() {
        return surCharge;
    }

    public void setSurCharge(BillDisplayDto surCharge) {
        this.surCharge = surCharge;
    }

    public double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public PropertyPenltyDto getPropPenaltyDto() {
        return propPenaltyDto;
    }

    public void setPropPenaltyDto(PropertyPenltyDto propPenaltyDto) {
        this.propPenaltyDto = propPenaltyDto;
    }

    public double getLastBillAmountPaid() {
        return lastBillAmountPaid;
    }

    public void setLastBillAmountPaid(double lastBillAmountPaid) {
        this.lastBillAmountPaid = lastBillAmountPaid;
    }

    public String getConstructFlag() {
        return ConstructFlag;
    }

    public void setConstructFlag(String constructFlag) {
        ConstructFlag = constructFlag;
    }

    public double getPreviousSurcharge() {
        return previousSurcharge;
    }

    public void setPreviousSurcharge(double previousSurcharge) {
        this.previousSurcharge = previousSurcharge;
    }

    public Date getManualReeiptDate() {
        return manualReeiptDate;
    }

    public void setManualReeiptDate(Date manualReeiptDate) {
        this.manualReeiptDate = manualReeiptDate;
    }

    public double getManualRecptArrearPaidAmnt() {
        return manualRecptArrearPaidAmnt;
    }

    public void setManualRecptArrearPaidAmnt(double manualRecptArrearPaidAmnt) {
        this.manualRecptArrearPaidAmnt = manualRecptArrearPaidAmnt;
    }

    public double getTotalTaxPayableWithArrears() {
        return totalTaxPayableWithArrears;
    }

    public void setTotalTaxPayableWithArrears(double totalTaxPayableWithArrears) {
        this.totalTaxPayableWithArrears = totalTaxPayableWithArrears;
    }

    public List<LookUp> getFactorDtlDto() {
        return factorDtlDto;
    }

    public void setFactorDtlDto(List<LookUp> factorDtlDto) {
        this.factorDtlDto = factorDtlDto;
    }

    public String getFactorNotApplicable() {
        return factorNotApplicable;
    }

    public void setFactorNotApplicable(String factorNotApplicable) {
        this.factorNotApplicable = factorNotApplicable;
    }

    public List<NoticeMasterDto> getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(List<NoticeMasterDto> noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public Date getAssesmentManualDate() {
        return assesmentManualDate;
    }

    public void setAssesmentManualDate(Date assesmentManualDate) {
        this.assesmentManualDate = assesmentManualDate;
    }

    public String getAssesmentManualNo() {
        return assesmentManualNo;
    }

    public void setAssesmentManualNo(String assesmentManualNo) {
        this.assesmentManualNo = assesmentManualNo;
    }

    public DocumentDetailsVO getManualCheckList() {
        return manualCheckList;
    }

    public void setManualCheckList(DocumentDetailsVO manualCheckList) {
        this.manualCheckList = manualCheckList;
    }

    public List<DocumentDetailsVO> getUpdateDataEntryDocs() {
        return updateDataEntryDocs;
    }

    public void setUpdateDataEntryDocs(List<DocumentDetailsVO> updateDataEntryDocs) {
        this.updateDataEntryDocs = updateDataEntryDocs;
    }

    public String getCountOfRow() {
        return countOfRow;
    }

    public void setCountOfRow(String countOfRow) {
        this.countOfRow = countOfRow;
    }

    public String getUnitDetailsCheck() {
        return unitDetailsCheck;
    }

    public void setUnitDetailsCheck(String unitDetailsCheck) {
        this.unitDetailsCheck = unitDetailsCheck;
    }

    public String getHearingDecisionFlag() {
        return hearingDecisionFlag;
    }

    public void setHearingDecisionFlag(String hearingDecisionFlag) {
        this.hearingDecisionFlag = hearingDecisionFlag;
    }

    public List<TbBillMas> getPreviousBillMasList() {
        return previousBillMasList;
    }

    public void setPreviousBillMasList(List<TbBillMas> previousBillMasList) {
        this.previousBillMasList = previousBillMasList;
    }

    public String getShowTaxDetails() {
        return showTaxDetails;
    }

    public void setShowTaxDetails(String showTaxDetails) {
        this.showTaxDetails = showTaxDetails;
    }

    public String getBillReviseFlag() {
        return billReviseFlag;
    }

    public void setBillReviseFlag(String billReviseFlag) {
        this.billReviseFlag = billReviseFlag;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public ProvisionalAssesmentMstDto getReviseProvisionalAssesmentMstDto() {
        return reviseProvisionalAssesmentMstDto;
    }

    public void setReviseProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto reviseProvisionalAssesmentMstDto) {
        this.reviseProvisionalAssesmentMstDto = reviseProvisionalAssesmentMstDto;
    }

    public List<BillDisplayDto> getRevisedArrearList() {
        return revisedArrearList;
    }

    public void setRevisedArrearList(List<BillDisplayDto> revisedArrearList) {
        this.revisedArrearList = revisedArrearList;
    }

    public List<TbServiceReceiptMasEntity> getReceiptPaidDetails() {
        return receiptPaidDetails;
    }

    public void setReceiptPaidDetails(List<TbServiceReceiptMasEntity> receiptPaidDetails) {
        this.receiptPaidDetails = receiptPaidDetails;
    }

    public double getLastReceiptAmount() {
        return lastReceiptAmount;
    }

    public void setLastReceiptAmount(double lastReceiptAmount) {
        this.lastReceiptAmount = lastReceiptAmount;
    }

    public Map<Long, Double> getFinancialYearBillPaidMap() {
        return financialYearBillPaidMap;
    }

    public void setFinancialYearBillPaidMap(Map<Long, Double> financialYearBillPaidMap) {
        this.financialYearBillPaidMap = financialYearBillPaidMap;
    }

    public Map<CommonChallanDTO, List<BillReceiptPostingDTO>> getReceiptInsertionList() {
        return receiptInsertionList;
    }

    public void setReceiptInsertionList(Map<CommonChallanDTO, List<BillReceiptPostingDTO>> receiptInsertionList) {
        this.receiptInsertionList = receiptInsertionList;
    }

    public Map<String, Double> getFinYearWiseBillMap() {
        return finYearWiseBillMap;
    }

    public void setFinYearWiseBillMap(Map<String, Double> finYearWiseBillMap) {
        this.finYearWiseBillMap = finYearWiseBillMap;
    }

    public double getTotalBuildUpArea() {
        return totalBuildUpArea;
    }

    public void setTotalBuildUpArea(double totalBuildUpArea) {
        this.totalBuildUpArea = totalBuildUpArea;
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

	public BillDisplayDto getDemandBAsedAdvanceAmt() {
		return demandBAsedAdvanceAmt;
	}

	public void setDemandBAsedAdvanceAmt(BillDisplayDto demandBAsedAdvanceAmt) {
		this.demandBAsedAdvanceAmt = demandBAsedAdvanceAmt;
	}

	public boolean isAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(boolean authLevel) {
		this.authLevel = authLevel;
	}

	public boolean isHideUserAction() {
		return hideUserAction;
	}

	public void setHideUserAction(boolean hideUserAction) {
		this.hideUserAction = hideUserAction;
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

	public String getInterWaiveOffAppl() {
		return interWaiveOffAppl;
	}

	public void setInterWaiveOffAppl(String interWaiveOffAppl) {
		this.interWaiveOffAppl = interWaiveOffAppl;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}

	public String getObjectionFlag() {
		return objectionFlag;
	}

	public void setObjectionFlag(String objectionFlag) {
		this.objectionFlag = objectionFlag;
	}

	public Date getCurrentFinStartDate() {
		return currentFinStartDate;
	}

	public void setCurrentFinStartDate(Date currentFinStartDate) {
		this.currentFinStartDate = currentFinStartDate;
	}

	public Date getCurrentFinEndDate() {
		return currentFinEndDate;
	}

	public void setCurrentFinEndDate(Date currentFinEndDate) {
		this.currentFinEndDate = currentFinEndDate;
	}

	public Map<CommonChallanDTO, List<BillReceiptPostingDTO>> getManualReceiptInsertionList() {
		return manualReceiptInsertionList;
	}

	public void setManualReceiptInsertionList(
			Map<CommonChallanDTO, List<BillReceiptPostingDTO>> manualReceiptInsertionList) {
		this.manualReceiptInsertionList = manualReceiptInsertionList;
	}

	public BillPaymentDetailDto getBillPayDto() {
		return billPayDto;
	}

	public void setBillPayDto(BillPaymentDetailDto billPayDto) {
		this.billPayDto = billPayDto;
	}

	public double getAdjustedAdvanceAmnt() {
		return adjustedAdvanceAmnt;
	}

	public void setAdjustedAdvanceAmnt(double adjustedAdvanceAmnt) {
		this.adjustedAdvanceAmnt = adjustedAdvanceAmnt;
	}


	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	
	public Long getCurrentApprovalLevel() {
		return currentApprovalLevel;
	}

	public void setCurrentApprovalLevel(Long currentApprovalLevel) {
		this.currentApprovalLevel = currentApprovalLevel;
	}

	public void removeMOSFactorForNoBuildingPermissionAndLandProperty(ProvisionalAssesmentMstDto assMstDto) {
		
		ListIterator<ProvisionalAssesmentDetailDto> detDtoList = assMstDto.getProvisionalAssesmentDetailDtoList().listIterator();
		while (detDtoList.hasNext()) {
			ProvisionalAssesmentDetailDto detailDto = detDtoList.next();
			if (detailDto != null && detailDto.getAssdFloorNo() != null) {
				if (StringUtils.equalsIgnoreCase(MainetConstants.Property.LAND, detailDto.getAssdNatureOfpropertyDesc1())
						|| StringUtils.equals(MainetConstants.FlagN, assMstDto.getBuildingPermission())) {
					ListIterator<ProvisionalAssesmentFactorDtlDto> factList = detailDto
							.getProvisionalAssesmentFactorDtlDtoList().listIterator();
					while (factList.hasNext()) {
						ProvisionalAssesmentFactorDtlDto factDto = factList.next();
						if (factDto != null && factDto.getFactorValueCode() != null && StringUtils.equals(MainetConstants.Property.MOS, factDto.getFactorValueCode())) {
							factList.remove();
						}
					}
					getProvAsseFactDtlDto().forEach(fact ->{
						if (fact != null && fact.getFactorValueCode() != null && StringUtils.equals(MainetConstants.Property.MOS, fact.getFactorValueCode())
								&& fact.getUnitNoFact().equals(String.valueOf(detailDto.getAssdUnitNo()))) {
							getProvAsseFactDtlDto().remove(fact);
						} else if(StringUtils.equals(MainetConstants.FlagN, assMstDto.getBuildingPermission()) && 
								StringUtils.equals(MainetConstants.ALL, fact.getUnitNoFact())){
							getProvAsseFactDtlDto().remove(fact);
						}
					});
				}
			}
		}
		
	}
		
	
}
