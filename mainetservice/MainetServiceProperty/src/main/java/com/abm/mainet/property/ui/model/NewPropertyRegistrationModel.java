package com.abm.mainet.property.ui.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.NewPropertyRegistrationService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.ui.validator.NewPropertyRegistrationValidator;

@Component
@Scope("session")
public class NewPropertyRegistrationModel extends AbstractFormModel {
    private static final long serialVersionUID = -1250602655307212504L;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private IFileUploadService fileUpload;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private NewPropertyRegistrationService newPropertyRegistrationService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DataEntrySuiteService dataeEtrySuiteService;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;
    
    @Autowired
   	private DepartmentService departmentService;
    
    @Autowired
    private PropertyMainBillService propertyMainBillService;

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();// Main DTO to Bind Data

    private ProvisionalAssesmentMstDto authCompBeforeDto = null;// DTO to compare Data in Authorization

    private List<ProvisionalAssesmentFactorDtlDto> authCompFactDto = new LinkedList<>();

    private TbBillMas tbBillMas = new TbBillMas();

    private List<TbTaxMas> taxesMaster = new ArrayList<>();

    private List<TbBillMas> authComBillList = null;// Bill List to compare Data in Authorization

    private List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto = new LinkedList<>();// Factor DTO to Bind Factor separately

    private List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();

    private ServiceMaster serviceMaster = new ServiceMaster();

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();

    private Map<String, List<ProvisionalAssesmentFactorDtlDto>> displayfactorMap = new HashMap<>();

    private List<LookUp> location = new ArrayList<>(0);

    private List<LookUp> taxCollList = new ArrayList<>(0);

    private List<String> unitNoList = new ArrayList<>(0);

    private List<Long> finYearList = new ArrayList<>(0);

    private List<TbBillMas> billMasList = new LinkedList<>();// Bill Generation List
    
    private List<TbBillMas> tempBillMasList = new LinkedList<>();

    private List<ProperySearchDto> searchDtoResult = new ArrayList<>();

    private List<CFCAttachment> documentList = new ArrayList<>();

    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private String removeCommonFileById;

    private ProperySearchDto searchDto = new ProperySearchDto();

    private Long lastFinYearId;
    private Long serviceId;
    private Long orgId;
    private Long deptId;
    private Long factorId;
    private String serviceShortCode;
    private String factorPrefix;
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private List<LookUp> schedule = new ArrayList<>(0);
    private List<LookUp> scheduleForArrEntry = new ArrayList<>(0);
    private String proAssPropAddCheck;
    private String assMethod;
    private Date leastFinYear;// least Date of Fin year in System
    private String assType;// Assessment type N-New ,C-Change,NC- No Change
    private String authEditFlag;// Y -Authorization with edit,N-Authorization without Edit
    private String editFlag;// Y -page open in edit mode
    private String dataFrom;// P-Provisional,M-Main -for DES edit data may come from P or M
    private Integer unitDetailTableCount;
    private Integer ownerDetailTableCount;
    private Integer unitStatusCount;
    private String selfAss;
    private Long maxUnit;
    private boolean lastChecker;
    private BufferedImage bufferImage;
    private Long schduleId;// for data entry suit
    private String modeType;

    private String intgrtionWithBP;// Integration with Building Permission

    private Map<Long, String> financialYearMap = new LinkedHashMap<>();

    private Map<Long, String> financialYearMapForTax = new LinkedHashMap<>();

    private CommonChallanDTO offlineDTO = new CommonChallanDTO();

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

    private String villageCode;

    private String knownKhaNo;

    private String enteredKhasraNo;

    private String enteredPlotNo;

    private List<LookUp> taxMasterList = new ArrayList<>(0);

    private String gisValue;

    private String gisUri;

    private String noOfDaysEditFlag;

    private String factorNotApplicable;
    
    private Long countOfRow;
    private Long flatNoOfRow;
    private String finYearFlag;
    private String billMethodDesc;
    private String billNo;
    private String isGrpProperty;
    private String isGrpPropertyFlag;
    private Long oldProDetFinYaerId;
    List<TbServiceReceiptMasEntity> receiptDetails = new ArrayList<TbServiceReceiptMasEntity>();
    
    List<FinancialYear> finYearMasterList = new ArrayList<FinancialYear>();
    
    Map<Long, String> finYearData = new LinkedHashMap<>();

    public LookUp getFactorLookup(String factorCode) {
        return CommonMasterUtility.getValueFromPrefixLookUp(factorCode, MainetConstants.Property.propPref.FCT,
                UserSession.getCurrent().getOrganisation());
    }

    public LookUp getOwnershipTypeLookup(String ownershipType) {
        return CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
    }

    // to get text description of the selected value of a dropdownList
    public void setDropDownValues() {
        Organisation org = UserSession.getCurrent().getOrganisation();
        this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getLookUpDesc());
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
        this.setAssMethod(CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS).getLookUpCode());
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()) {
				if (dto.getGenderId() != null) {
					dto.setProAssGenderId(
							CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getLookUpDesc());
				}
				if (dto.getRelationId() != null) {
					dto.setProAssRelationId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org)
							.getLookUpDesc());
				}
				if(dto.getAssoGuardianName()==null) {
					dto.setAssoGuardianName(MainetConstants.BLANK);
				}
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }

        this.getLocation().forEach(loca -> {
            if (loca.getLookUpId() == provisionalAssesmentMstDto.getLocId()) {
                this.getProvisionalAssesmentMstDto().setLocationName(loca.getLookUpDesc());
            }
        });

        this.getDistrict().forEach(dis -> {
            if (Long.toString(dis.getLookUpId()).equals(provisionalAssesmentMstDto.getAssDistrict())) {
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

        this.getProvisionalAssesmentMstDto().setProAssdRoadfactorDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org).getLookUpDesc());

        if (provisionalAssesmentMstDto.getAssLandType() != null) {
            this.getProvisionalAssesmentMstDto().setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType(), org).getLookUpDesc());

        }

        this.getProvisionalAssesmentMstDto().setAssWardDesc1(
                CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getLookUpDesc());

        if (provisionalAssesmentMstDto.getAssWard2() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc2(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getLookUpDesc());
        }

        if (provisionalAssesmentMstDto.getAssWard3() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc3(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getLookUpDesc());
        }

        if (provisionalAssesmentMstDto.getAssWard4() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc4(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getLookUpDesc());
        }

        if (provisionalAssesmentMstDto.getAssWard5() != null) {

            this.getProvisionalAssesmentMstDto().setAssWardDesc5(
                    CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getLookUpDesc());
        }
        if (provisionalAssesmentMstDto.getTaxCollEmp() != null) {
            Employee emp = iEmployeeService.findEmployeeByIdAndOrgId(provisionalAssesmentMstDto.getTaxCollEmp(), orgId);
            if (emp != null) {
                provisionalAssesmentMstDto
                        .setTaxCollEmpDesc(emp.getEmpname() + " " + emp.getEmplname() + "-" + emp.getDesignation().getDsgname());
            }
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
        for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
                	if(CollectionUtils.isNotEmpty(getBillMasList()) && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
                		if (entry.getKey().toString().equals(getBillMasList().get(0).getBmYear().toString())) {
                            detaildto.setProFaYearIdDesc(entry.getValue());
                        }
                	}else {
                		if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                            detaildto.setProFaYearIdDesc(entry.getValue());
                        }
                	}
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    if(detaildto.getAssdAssesmentDate() != null)
                    detaildto.setProAssdAssesmentDate(formatter.format(detaildto.getAssdAssesmentDate()));
                    detaildto.setProAssdUsagetypeDesc(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getLookUpDesc());

                    if (detaildto.getAssdUsagetype2() != null) {
                        detaildto.setProAssdUsagetypeDesc2(
                                CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdUsagetype3() != null) {
                        detaildto.setProAssdUsagetypeDesc3(
                                CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdUsagetype4() != null) {
                        detaildto.setProAssdUsagetypeDesc4(
                                CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdUsagetype5() != null) {
                        detaildto.setProAssdUsagetypeDesc5(
                                CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdNatureOfproperty1() != null) {
                    detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getLookUpDesc());
                    }

                    if (detaildto.getAssdNatureOfproperty2() != null) {
                        detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdNatureOfproperty3() != null) {
                        detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdNatureOfproperty4() != null) {
                        detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getLookUpDesc());
                    }
                    if (detaildto.getAssdNatureOfproperty5() != null) {
                        detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getLookUpDesc());
                    }

                    detaildto.setProFloorNo(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org)
                                    .getLookUpDesc());
                    detaildto.setProAssdConstruTypeDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org)
                                    .getLookUpDesc());
                    detaildto.setProAssdOccupancyTypeDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org)
                                    .getLookUpDesc());
                }
                for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : this.getProvAsseFactDtlDto()) {
                    if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                        provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                .getLookUpDesc());
                        provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(
                                CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                        .getLookUpDesc());
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

    private void formatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        if (getProvisionalAssesmentMstDto().getAssAcqDate() != null) {
            this.getProvisionalAssesmentMstDto()
                    .setProAssAcqDateFormat(formatter.format(getProvisionalAssesmentMstDto().getAssAcqDate()));
        }
    }
    

    @Override
    public boolean saveForm() {
        setCustomViewName("NewPropertyRegistrationView");
        ProvisionalAssesmentMstDto assMstDto = getProvisionalAssesmentMstDto();
        List<DocumentDetailsVO> docs = getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        provisionalAssesmentMstDto.setDocs(docs);
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        validateBean(assMstDto, NewPropertyRegistrationValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        newPropertyRegistrationService.saveNewPropertyRegistration(provisionalAssesmentMstDto,
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee().getEmpId(), this.getDeptId(), UserSession.getCurrent().getLanguageId(),
                finYearList);
        setSuccessMessage(getAppSession().getMessage("prop.save.new") + provisionalAssesmentMstDto.getApmApplicationId());
        newPropertyRegistrationService.callWorkFlow(provisionalAssesmentMstDto, orgId, serviceId, deptId);
        return true;
    }

    public boolean saveDataEntryForm() throws FrameworkException{
        setCustomViewName("DataEntrySuiteView");
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        Employee emp = UserSession.getCurrent().getEmployee();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (getAssType().equals(MainetConstants.FlagA)) {
            validateBean(workFlowActionDto, CheckerActionValidator.class);
        }
        if (hasValidationErrors()) {
            return false;
        }
        provisionalAssesmentMstDto.setFlag(MainetConstants.FlagD);
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        
		if (provisionalAssesmentMstDto.getParentGrp1() != null && provisionalAssesmentMstDto.getParentGrp2() != null) {
			LookUp parentLookUp = CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getParentGrp1(), orgId);
			provisionalAssesmentMstDto.setParentPropNo(provisionalAssesmentMstDto.getParentGrp1().toString());			
			provisionalAssesmentMstDto.setParentPropName(parentLookUp.getLookUpDesc());
			
			LookUp grpLookUp = CommonMasterUtility.getHierarchicalLookUp(provisionalAssesmentMstDto.getParentGrp2(), orgId);
			provisionalAssesmentMstDto.setGroupPropNo(provisionalAssesmentMstDto.getParentGrp2().toString());			
			provisionalAssesmentMstDto.setGroupPropName(grpLookUp.getLookUpDesc());
			provisionalAssesmentMstDto.setIsGroup(MainetConstants.FlagY);
		}
        if (getAssType().equals(MainetConstants.Property.DATA_ENTRY_SUITE)) {
        	propertyMainBillService.copyDataFromMainBillDetailToHistory(billMasList,MainetConstants.FlagE,UserSession.getCurrent().getEmployee().getEmpId(),getClientIpAddress());
            provisionalAssesmentMstDto.setSmServiceId(getServiceId());
            provisionalAssesmentMstDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            Long finYear = provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
            ServiceMaster ser = serviceMasterService.getServiceMaster(provisionalAssesmentMstDto.getSmServiceId(),
                    provisionalAssesmentMstDto.getOrgId());

            dataeEtrySuiteService.saveDataEntryForm(billMasList, provisionalAssesmentMstDto, finYear,
                    UserSession.getCurrent().getEmployee().getEmpId(), ser.getSmProcessId());
            prepareContractDocumentsData(provisionalAssesmentMstDto);

            setSuccessMessage(ApplicationSession.getInstance().getMessage("property.dataEntry.sucess")
                    + provisionalAssesmentMstDto.getAssNo());

            if (ser.getSmProcessId() != null && !CommonMasterUtility
                    .getNonHierarchicalLookUpObject(ser.getSmProcessId(), UserSession.getCurrent().getOrganisation())
                    .getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
                ApplicationMetadata applicationData = new ApplicationMetadata();
                final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
                applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
                applicationData.setReferenceId(provisionalAssesmentMstDto.getAssNo());
                applicationData.setIsCheckListApplicable(
                        (provisionalAssesmentMstDto.getDocs() != null && !provisionalAssesmentMstDto.getDocs().isEmpty()) ? true
                                : false);
                applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                applicantDetailDto.setServiceId(getServiceId());
                applicantDetailDto.setDepartmentId(getDeptId());
                applicantDetailDto.setUserId(provisionalAssesmentMstDto.getCreatedBy());
                applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
                applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
                applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
                applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
                applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
                commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
            }

        } else if (getAssType().equals(MainetConstants.FlagE)) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)
					&& getOldProDetFinYaerId() != null && getOldProDetFinYaerId() > 0) {
				provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(prodet ->{
					prodet.setFaYearId(getOldProDetFinYaerId());
				});
			}
            dataeEtrySuiteService.updateDataEntryForm(billMasList, provisionalAssesmentMstDto, orgId, emp.getEmpId(),
                    UserSession.getCurrent().getEmployee().getEmppiservername(), dataFrom);
            prepareContractDocumentsData(provisionalAssesmentMstDto);
        } else {
            finYearList.add(this.getProvAssMstDtoList().get(0).getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
            if (getAuthEditFlag().equals(MainetConstants.FlagN)) {
                propertyAuthorizationService.SaveAuthorizationData(this.getProvAssMstDtoList(),
                        this.getProvisionalAssesmentMstDto(),
                        this.getAuthComBillList(), workFlowActionDto, UserSession.getCurrent().getOrganisation().getOrgid(),
                        emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());

                if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                    prepareContractDocumentsData(provisionalAssesmentMstDto);
                }

            } else if (getAuthEditFlag().equals(MainetConstants.FlagY)) {
                propertyAuthorizationService.SaveAuthorizationWithEditForDES(this.getProvAssMstDtoList(),
                        this.getProvisionalAssesmentMstDto(),
                        this.getBillMasList(), this.getAuthComBillList(), workFlowActionDto,
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId(),
                        UserSession.getCurrent().getEmployee().getEmppiservername());

                if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                    prepareContractDocumentsData(provisionalAssesmentMstDto);
                }
            }
            propertyAuthorizationService.callWorkflow(this.getProvisionalAssesmentMstDto().getApmApplicationId(),
                    workFlowActionDto,
                    orgId, emp, serviceId);
        }
        return true;
    }

    public boolean saveDataEntryFormInEdit() {
        return true;
    }

    public void prepareContractDocumentsData(ProvisionalAssesmentMstDto provisionalAssesmentMstDto)  throws FrameworkException{
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        requestDTO.setIdfId(provisionalAssesmentMstDto.getAssNo());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getCommonFileAttachment();
        setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
        fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
        List<Long> enclosureRemoveById = null;
        String fileId = getRemoveCommonFileById();
        if (fileId != null && !fileId.isEmpty()) {
            enclosureRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                enclosureRemoveById.add(Long.valueOf(fields));
            }
        }
        if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
            iAttachDocsService.updateRecordByUniqueId(enclosureRemoveById,
                    UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.FlagD);

    }

    public boolean saveAuthorization() {
        setCustomViewName("NewPropertyRegistrationView");
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        validateBean(workFlowActionDto, CheckerActionValidator.class);
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
        if (getAuthEditFlag().equals("N")) {
            propertyAuthorizationService.saveUploadedFile(this.getProvisionalAssesmentMstDto(), orgId,
                    emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
            propertyAuthorizationService.saveNewPropertyRegAuthorization(this.getProvAssMstDtoList(),
                    this.getProvisionalAssesmentMstDto(),
                    workFlowActionDto, orgId,
                    emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
        } else if (getAuthEditFlag().equals("Y")) {
            propertyAuthorizationService.saveUploadedFile(this.getProvisionalAssesmentMstDto(), orgId,
                    emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
            propertyAuthorizationService.saveNewPropertyRegAuthorizationWithEdit(
                    this.getProvAssMstDtoList(),
                    this.getProvisionalAssesmentMstDto(), finYearList,
                    workFlowActionDto, orgId,
                    emp, this.getDeptId(), UserSession.getCurrent().getLanguageId(), this.getServiceId());
        }
        propertyAuthorizationService.callWorkflow(this.getProvisionalAssesmentMstDto().getApmApplicationId(), workFlowActionDto,
                orgId, emp, serviceId);
        return true;
    }

    public boolean checkPayDetail(List<TbBillMas> billMasList) {
    	AtomicBoolean payStatus = new AtomicBoolean(false);
    	final Department dept = departmentService.getDepartment(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
				MainetConstants.STATUS.ACTIVE);
        List<TbServiceReceiptMasEntity> billPaymentDoneList = ApplicationContextProvider
				.getApplicationContext().getBean(ReceiptRepository.class)
				.getCollectionDetails(billMasList.get(0).getPropNo(), dept.getDpDeptid(),
						UserSession.getCurrent().getOrganisation().getOrgid());
        setReceiptDetails(billPaymentDoneList);
        if(CollectionUtils.isNotEmpty(billPaymentDoneList)) {
        	billMasList.forEach(billMas ->{
        		billPaymentDoneList.forEach(payDetail ->{
        			if(StringUtils.equals(payDetail.getReceiptTypeFlag(), MainetConstants.FlagR)) {
        				payDetail.getReceiptFeeDetail().forEach(feeDetail ->{
            				if(feeDetail.getBmIdNo() != null && feeDetail.getBmIdNo().equals(billMas.getBmIdno())) {
            					payStatus.getAndSet(true);
            				}
            			});
        			}
        			
        		});
        	});
        }
        
        return payStatus.get();
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

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
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

    public TbBillMas getTbBillMas() {
        return tbBillMas;
    }

    public void setTbBillMas(TbBillMas tbBillMas) {
        this.tbBillMas = tbBillMas;
    }

    public List<TbTaxMas> getTaxesMaster() {
        return taxesMaster;
    }

    public void setTaxesMaster(List<TbTaxMas> taxesMaster) {
        this.taxesMaster = taxesMaster;
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

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    /**
     * @return the bufferImage
     */
    public BufferedImage getBufferImage() {
        return bufferImage;
    }

    /**
     * @param bufferImage the bufferImage to set
     */
    public void setBufferImage(BufferedImage bufferImage) {
        this.bufferImage = bufferImage;
    }

    public Long getSchduleId() {
        return schduleId;
    }

    public void setSchduleId(Long schduleId) {
        this.schduleId = schduleId;
    }

    public List<LookUp> getScheduleForArrEntry() {
        return scheduleForArrEntry;
    }

    public void setScheduleForArrEntry(List<LookUp> scheduleForArrEntry) {
        this.scheduleForArrEntry = scheduleForArrEntry;
    }

    public Long getLastFinYearId() {
        return lastFinYearId;
    }

    public void setLastFinYearId(Long lastFinYearId) {
        this.lastFinYearId = lastFinYearId;
    }

    public List<TbBillMas> getAuthComBillList() {
        return authComBillList;
    }

    public void setAuthComBillList(List<TbBillMas> authComBillList) {
        this.authComBillList = authComBillList;
    }

    public ProvisionalAssesmentMstDto getAuthCompBeforeDto() {
        return authCompBeforeDto;
    }

    public void setAuthCompBeforeDto(ProvisionalAssesmentMstDto authCompBeforeDto) {
        this.authCompBeforeDto = authCompBeforeDto;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getAuthCompFactDto() {
        return authCompFactDto;
    }

    public void setAuthCompFactDto(List<ProvisionalAssesmentFactorDtlDto> authCompFactDto) {
        this.authCompFactDto = authCompFactDto;
    }

    public String getServiceShortCode() {
        return serviceShortCode;
    }

    public void setServiceShortCode(String serviceShortCode) {
        this.serviceShortCode = serviceShortCode;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public List<LookUp> getTaxCollList() {
        return taxCollList;
    }

    public void setTaxCollList(List<LookUp> taxCollList) {
        this.taxCollList = taxCollList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getRemoveCommonFileById() {
        return removeCommonFileById;
    }

    public void setRemoveCommonFileById(String removeCommonFileById) {
        this.removeCommonFileById = removeCommonFileById;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public ProperySearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(ProperySearchDto searchDto) {
        this.searchDto = searchDto;
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

    public String getLandTypePrefix() {
        return landTypePrefix;
    }

    public void setLandTypePrefix(String landTypePrefix) {
        this.landTypePrefix = landTypePrefix;
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

    public List<LookUp> getBlockStreet() {
        return blockStreet;
    }

    public void setBlockStreet(List<LookUp> blockStreet) {
        this.blockStreet = blockStreet;
    }

    public List<LookUp> getMohalla() {
        return mohalla;
    }

    public void setMohalla(List<LookUp> mohalla) {
        this.mohalla = mohalla;
    }

    public List<ProperySearchDto> getSearchDtoResult() {
        return searchDtoResult;
    }

    public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
        this.searchDtoResult = searchDtoResult;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
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

    public Map<Long, String> getFinancialYearMapForTax() {
        return financialYearMapForTax;
    }

    public void setFinancialYearMapForTax(Map<Long, String> financialYearMapForTax) {
        this.financialYearMapForTax = financialYearMapForTax;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getKnownKhaNo() {
        return knownKhaNo;
    }

    public void setKnownKhaNo(String knownKhaNo) {
        this.knownKhaNo = knownKhaNo;
    }

    public String getEnteredPlotNo() {
        return enteredPlotNo;
    }

    public void setEnteredPlotNo(String enteredPlotNo) {
        this.enteredPlotNo = enteredPlotNo;
    }

    public List<LookUp> getTaxMasterList() {
        return taxMasterList;
    }

    public void setTaxMasterList(List<LookUp> taxMasterList) {
        this.taxMasterList = taxMasterList;
    }

    public String getGisValue() {
        return gisValue;
    }

    public void setGisValue(String gisValue) {
        this.gisValue = gisValue;
    }

    public String getNoOfDaysEditFlag() {
        return noOfDaysEditFlag;
    }

    public void setNoOfDaysEditFlag(String noOfDaysEditFlag) {
        this.noOfDaysEditFlag = noOfDaysEditFlag;
    }

    public String getEnteredKhasraNo() {
        return enteredKhasraNo;
    }

    public void setEnteredKhasraNo(String enteredKhasraNo) {
        this.enteredKhasraNo = enteredKhasraNo;
    }

    public String getGisUri() {
        return gisUri;
    }

    public void setGisUri(String gisUri) {
        this.gisUri = gisUri;
    }

    public String getFactorNotApplicable() {
        return factorNotApplicable;
    }

    public void setFactorNotApplicable(String factorNotApplicable) {
        this.factorNotApplicable = factorNotApplicable;
    }

	public Long getCountOfRow() {
		return countOfRow;
	}

	public void setCountOfRow(Long countOfRow) {
		this.countOfRow = countOfRow;
	}

	public Long getFlatNoOfRow() {
		return flatNoOfRow;
	}

	public void setFlatNoOfRow(Long flatNoOfRow) {
		this.flatNoOfRow = flatNoOfRow;
	}
	
	public String getFinYearFlag() {
		return finYearFlag;
	}

	public void setFinYearFlag(String finYearFlag) {
		this.finYearFlag = finYearFlag;
	}

	public List<TbBillMas> getTempBillMasList() {
		return tempBillMasList;
	}

	public void setTempBillMasList(List<TbBillMas> tempBillMasList) {
		this.tempBillMasList = tempBillMasList;
	}

	public String getBillMethodDesc() {
		return billMethodDesc;
	}

	public void setBillMethodDesc(String billMethodDesc) {
		this.billMethodDesc = billMethodDesc;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public List<TbServiceReceiptMasEntity> getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(List<TbServiceReceiptMasEntity> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	public List<FinancialYear> getFinYearMasterList() {
		return finYearMasterList;
	}

	public void setFinYearMasterList(List<FinancialYear> finYearMasterList) {
		this.finYearMasterList = finYearMasterList;
	}

	public Map<Long, String> getFinYearData() {
		return finYearData;
	}

	public void setFinYearData(Map<Long, String> finYearData) {
		this.finYearData = finYearData;
	}

	public String getIsGrpProperty() {
		return isGrpProperty;
	}

	public void setIsGrpProperty(String isGrpProperty) {
		this.isGrpProperty = isGrpProperty;
	}

	public String getIsGrpPropertyFlag() {
		return isGrpPropertyFlag;
	}

	public void setIsGrpPropertyFlag(String isGrpPropertyFlag) {
		this.isGrpPropertyFlag = isGrpPropertyFlag;
	}

	public Long getOldProDetFinYaerId() {
		return oldProDetFinYaerId;
	}

	public void setOldProDetFinYaerId(Long oldProDetFinYaerId) {
		this.oldProDetFinYaerId = oldProDetFinYaerId;
	}
		
    
}
