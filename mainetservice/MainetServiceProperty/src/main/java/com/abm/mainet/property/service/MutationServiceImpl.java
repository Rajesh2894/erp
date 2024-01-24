package com.abm.mainet.property.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WorkFlow.BpmpDelayPeriods;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CommonAppResponseDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.repository.TbOrganisationJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.PropertyTransferMasterEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.MutationDetailDto;
import com.abm.mainet.property.dto.PropertyRequestDTO;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.PublicNoticeDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.MainAssessmentDetailRepository;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;
import com.abm.mainet.property.repository.PropertyTransferRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.google.common.util.concurrent.AtomicDouble;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.MutationService")
@Api(value = "/mutation")
@Path("/mutation")
public class MutationServiceImpl implements MutationService {

    private static final long serialVersionUID = 9183008043033804610L;
    private static final Logger LOGGER = Logger.getLogger(MutationServiceImpl.class);
    public static final float PADDING=10f;
	public static final float TEXT_SIZE_10=11f;
	public static final float TEXT_SIZE_9=9f;
	public static final float TEXT_SIZE_8=8f;
    @Autowired
    private BRMSCommonService brmsCommonService;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private ApplicationService applicationService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private PropertyTransferService propertyTransferService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private MutationIntimationService mutationIntimationService;

    @Autowired
    private IObjectionDetailsService iObjectionDetailsService;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;

    @Autowired
    private TbLoiMasService TbLoiMasService;

    @Autowired
    private MainAssessmentOwnerRepository ownerRepository;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private MainAssessmentDetailRepository assessmentDetailRepository;

    @Autowired
    private PropertyTransferRepository propertyTransferRepository;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private AmalgamationService amalgamationService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private TbOrganisationJpaRepository organisationJpaRepository;

    @Autowired
    private IFinancialYearService financialYearService;

    @Autowired
    private IOrganisationService organisationService;
    @Autowired
    private IProvisionalBillService provisionalBillService;
    
    @Value("${upload.physicalPath}")
	private String filenetPath;
    
    @Autowired
    private CFCAttechmentRepository cfcAttechmentRepository;
    
    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;
    
    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;
    
    @Autowired
    private ISMSAndEmailService ismsAndEmailService;
    
    @Autowired
	private PropertyTransferRepository PropertyTransferRepository;
    
    @Autowired
    private ReceiptRepository receiptRepository;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;
    
    @Override
    @POST
    @Path("/fetchCheckListForMutation")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DocumentDetailsVO> fetchCheckList(PropertyTransferMasterDto dto) {
        final WSRequestDTO wsdto = new WSRequestDTO();
        List<DocumentDetailsVO> docs = null;
        wsdto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
        WSResponseDTO response = brmsCommonService.initializeModel(wsdto);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
            CheckListModel checkListModel = (CheckListModel) checklist.get(0);
            docs = populateCheckListModel(dto, checkListModel, wsdto);
        }
        return docs;
    }

    private List<DocumentDetailsVO> populateCheckListModel(PropertyTransferMasterDto dto, CheckListModel checklistModel,
            final WSRequestDTO wsdto) {
        Map<Long, DocumentDetailsVO> docMap = new HashMap<>(0);
        List<DocumentDetailsVO> checklist = new ArrayList<>(0);
        checklistModel.setOrgId(dto.getOrgId());
        Organisation org = new Organisation();
        org.setOrgid(dto.getOrgId());
        checklistModel
                .setServiceCode(serviceMasterRepository.getServiceShortCode(dto.getSmServiceId(), dto.getOrgId()));
        if (null != dto.getOwnerType()) {
            checklistModel.setApplicantType(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getOwnerType(), org).getDescLangFirst());
        }
        if (dto.getAssLandType() != null && dto.getAssLandType() > 0) {
            String lookUpCode = CommonMasterUtility.findLookUpCode(MainetConstants.Property.propPref.LDT,
                    dto.getOrgId(), dto.getAssLandType());
            if (lookUpCode.equals(MainetConstants.Property.LandType.ENT)) {
                checklistModel.setFactor2(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getAssLandType(), org)
                        .getDescLangFirst());
            } else {
                checklistModel.setFactor2(MainetConstants.CommonConstants.NA);
            }
        }
        checklistModel.setNoOfDays(org.getOrgid());
        checklistModel.setFactor4(
                CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getTransferType(), org).getDescLangFirst());
        checklistModel.setIsBPL(MainetConstants.CommonConstants.NA);
        wsdto.setDataModel(checklistModel);
        WSResponseDTO response = brmsCommonService.getChecklist(wsdto);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            @SuppressWarnings("unchecked")
            List<DocumentDetailsVO> checklistData = (List<DocumentDetailsVO>) response.getResponseObj();
            // Defect #91001
            long count = 1;
            for (DocumentDetailsVO doc : checklistData) {
                doc.setDocumentSerialNo(count);
                count++;
                docMap.put(doc.getDocumentId(), doc);
            }
        }
        if (!docMap.isEmpty()) {
            docMap.forEach((key, value) -> {
                checklist.add(value);
            });
            checklist.sort(Comparator.comparing(DocumentDetailsVO::getDocumentSerialNo));// Sorting List by collection
                                                                                         // sequence
        }
        return checklist;
    }

    @Override
    @POST
    @Path("/fetchDetailForMutataion/{propNo}/{oldPropNo}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProvisionalAssesmentMstDto fetchDetailForMutataion(@PathParam(value = "propNo") String propNo,
            @PathParam(value = "oldPropNo") String oldPropNo, @PathParam(value = "orgId") Long orgId) {
        return fetchMutationDetails(propNo, oldPropNo, null, orgId);
    }

    // D#126417 This method is added for mobile, issue is coming when we pass old property no like 04/24 then url is mismatch
    @Override
    @POST
    @Path("/fetchDetailForMutationForMobile")
    @Produces(MediaType.APPLICATION_JSON)
    public ProvisionalAssesmentMstDto fetchDetailForMutationForMobile(PropertyInputDto inputDto) {
        return fetchMutationDetails(inputDto.getPropertyNo(), inputDto.getOldPropertyNo(), inputDto.getFlatNo(), inputDto.getOrgId());
    }

    @Override
    public ProvisionalAssesmentMstDto fetchMutationDetails(String propNo, String oldPropNo, String flatNo, long orgId) {
        ProvisionalAssesmentMstDto assMst = null;
        List<TbBillMas> paidFlagList = null;
        List<ProvisionalAssesmentMstDto> assMstList = null;
        // Take provisional assessment detail
        if (StringUtils.isBlank(propNo)) {
            if (StringUtils.isNotBlank(flatNo)) {
                assMstList = iProvisionalAssesmentMstService.getPropDetailFromProvAssByOldPropNoAndFlatNo(orgId,
                        oldPropNo, flatNo, MainetConstants.FlagA);
                if (CollectionUtils.isNotEmpty(assMstList)) {
                    assMst = assMstList.get(0);
                }
            } else {
                assMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByOldPropNo(oldPropNo, orgId);
            }
        } else {
            if (StringUtils.isNotBlank(flatNo)) {
                assMstList = iProvisionalAssesmentMstService.getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(orgId,
                        propNo, flatNo, MainetConstants.FlagA);
                if (CollectionUtils.isNotEmpty(assMstList)) {
                    assMst = assMstList.get(0);
                }
            } else {
                assMst = iProvisionalAssesmentMstService.fetchProvisionalDetailsByPropNo(propNo, orgId);
            }
        }
        // Take assessment detail
        // assMst = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, propNo);
        if (assMst == null) {
            if (org.apache.commons.lang3.StringUtils.isBlank(propNo) || propNo.equalsIgnoreCase("Null")) {
                if (StringUtils.isNotBlank(flatNo)) {
                    assMstList = assesmentMastService.fetchAssessmentMasterByOldPropNoNFlatNo(orgId, oldPropNo, flatNo);
                    if (CollectionUtils.isNotEmpty(assMstList)) {
                        assMst = assMstList.get(0);
                    }
                } else {
                    assMst = assesmentMastService.fetchAssessmentMasterByOldPropNo(orgId, oldPropNo);
                }
            } else {
				if (StringUtils.isNotBlank(flatNo)) {
					assMstList = assesmentMastService.getPropDetailFromAssByPropNoFlatNo(orgId, propNo, flatNo);
					if (CollectionUtils.isNotEmpty(assMstList)) {
						assMst = assMstList.get(0);
					}
				} else {
					assMst = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, propNo);
				}
            }
        }

        if (assMst != null) {
            int countForMainBill = ApplicationContextProvider.getApplicationContext().getBean(IAssessmentMastDao.class)
                    .getCountWhetherMaxBmIdExistInMainBill(assMst.getAssNo(), orgId);
            List<TbBillMas> billMasData = null;
            if (countForMainBill > 0) {
                // Form Main Bill table
                billMasData = ApplicationContextProvider.getApplicationContext().getBean(PropertyMainBillService.class)
                        .fetchNotPaidBillForAssessment(assMst.getAssNo(), orgId);
            } else {
                // From Provisional Bill Table
                billMasData = ApplicationContextProvider.getApplicationContext().getBean(IProvisionalBillService.class)
                        .fetchNotPaidBillForProAssessment(assMst.getAssNo(), orgId);
            }

            if (CollectionUtils.isNotEmpty(billMasData)) {
                assMst.setOutstandingAmount(billMasData.get(billMasData.size() - 1).getBmTotalOutstanding());
            } else {
                assMst.setOutstandingAmount(0d);
            }
            // D#117830
            PropertyTransferMasterDto transferMasDto = null;
            if (StringUtils.isNotBlank(flatNo)) {
            	transferMasDto = getMutationByPropNonFlatNo(assMst.getAssNo(), flatNo, orgId);
            }else {
            	transferMasDto = getMutationByPropNo(assMst.getAssNo(), orgId);
            }
            if (transferMasDto.getApmApplicationId() != null) {
                String status = getWorkflowRequestByAppId(transferMasDto.getApmApplicationId(),
                        orgId);
                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                    assMst.setStatus(MainetConstants.TASK_STATUS_PENDING);
                }
            }
            // In case of new property Registration service no need to check bills
            // ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.NPR, orgId);
            // if (service.getSmServiceId().equals(assMst.getSmServiceId())) {
            // count++;
            // } else {
            // // check for no dues pending for current year assessment
            //
            // Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            // count = propertyMainBillService.getCountOfBillByOldPropNoOrPropNoAndFinId(assMst.getAssNo(), orgId,
            // finYearId);
            // }
            // if (count >= 0) {
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            // Defect #128911 Mutation 3.Land detail not showing proper (showing some code value in place of name)
            if (assMst.getAssLandType() != null) {
                assMst.setAssLandTypeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssLandType(), org)
                        .getDescLangFirst());
            }
            LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org);
            String ownerTypeCode = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org)
                    .getLookUpCode();
            if (MainetConstants.Property.SO.equals(ownerTypeCode)
                    || MainetConstants.Property.JO.equals(ownerTypeCode)) {
                for (ProvisionalAssesmentOwnerDtlDto dto : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
                    if (dto.getGenderId() != null) {
                        dto.setProAssGenderId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org)
                                .getDescLangFirst());
                    } else {
                        dto.setProAssGenderId(MainetConstants.CommonConstants.NA);
                    }
                    if (dto.getRelationId() != null) {
                        dto.setProAssRelationId(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(dto.getRelationId(), org).getDescLangFirst());
                    } else {
                        dto.setProAssRelationId(MainetConstants.CommonConstants.NA);
                    }
                }
            }
            assMst.setProAssOwnerTypeName(ownerType.getDescLangFirst());
        }
        // }
        // For sending dues pending validation message to Mobile for Task #92509
        if (assMst != null) {
        	Long finYearId = iFinancialYear.getFinanceYearId(new Date());
			Organisation org = organisationService.getOrganisationById(orgId);
            List<String> propNoList = Arrays.asList(assMst.getAssNo());
			if (StringUtils.isNotBlank(flatNo)) {
				paidFlagList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(assMst.getAssNo(), flatNo, orgId);
			} else {
				paidFlagList = amalgamationService.fetchNotPaidBillsByPropNo(propNoList, orgId);
			}
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && CollectionUtils.isEmpty(paidFlagList)) {
				assMst.setDueStatus(MainetConstants.Property.DEMAND_GEN_PENDING);
			}
			else if (CollectionUtils.isNotEmpty(paidFlagList)) {
                TbBillMas billMas = paidFlagList.get(paidFlagList.size() - 1);
                boolean paidFlag = false;
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                	AtomicDouble pendingAmount = new AtomicDouble(0);
                    billMas.getTbWtBillDet().forEach(billDet ->{
                    	pendingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvArramt());
                    });
                    BillDisplayDto advanceAmnt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(propNo, orgId, null);
                    if(advanceAmnt != null && advanceAmnt.getCurrentYearTaxAmt() != null && advanceAmnt.getCurrentYearTaxAmt().doubleValue() >= pendingAmount.doubleValue()) {
                    	paidFlag = true;
                    }
                }
				if (!finYearId.equals(billMas.getBmYear())) {
					assMst.setDueStatus(MainetConstants.Property.DEMAND_GEN_PENDING);
				} else if (StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.N_FLAG) && !paidFlag) {
					assMst.setDueStatus(MainetConstants.TASK_STATUS_PENDING);
				} else if(StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.Y_FLAG)) {
					assMst.setOutstandingAmount(0d);
				}
            } else {
                // D#117830
            	if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
            		 List<String> proPaidFlagList = provisionalBillService.fetchProvisionalNotPaidBillByPropNo(propNo, orgId);
                     if (CollectionUtils.isNotEmpty(proPaidFlagList) && assMst != null) {
                         String paidFlag = proPaidFlagList.get(proPaidFlagList.size() - 1);
                         if (StringUtils.equals(MainetConstants.N_FLAG, paidFlag)) {
                             assMst.setDueStatus(MainetConstants.TASK_STATUS_PENDING);
                         }
                     }
            	}
            }
            // Assessment done for current year or not           
            if (finYearId.equals(assMst.getFaYearId())) {
                assMst.setAssesmentDoneForCurrentYear(true);
            } else {
                assMst.setAssesmentDoneForCurrentYear(false);
            }
            // D#126417 All owner is not showing in mutation mobile receipt
            Organisation organisation = new Organisation();
            organisation.setOrgid(assMst.getOrgId());
            StringBuilder ownerFullName = new StringBuilder();
            int ownerSize = 1;
            List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMst.getProvisionalAssesmentOwnerDtlDtoList();
            for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
                if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

                    if (StringUtils.isEmpty(ownerFullName.toString())) {
                        ownerFullName.append(owner.getAssoOwnerName());
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                            LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    organisation);
                            ownerFullName.append(reltaionLookUp.getDescLangFirst());
                        } else {
                            ownerFullName.append("Contact person - ");
                        }
                        if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            ownerFullName.append(owner.getAssoGuardianName());
                        }
                    } else {
                        ownerFullName.append(owner.getAssoOwnerName());
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                            LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    organisation);
                            ownerFullName.append(reltaionLookUp.getDescLangFirst());
                        } else {
                            ownerFullName.append("Contact person - ");
                        }
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        ownerFullName.append(owner.getAssoGuardianName());
                    }
                    if (ownerSize < ownerList.size()) {
                        ownerFullName.append("," + " ");
                    }
                    ownerSize = ownerSize + 1;
                } else {
                    ownerFullName.append(owner.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    } else {
                        ownerFullName.append("Contact person - ");
                    }
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    ownerFullName.append(owner.getAssoGuardianName());
                    if (ownerSize < ownerList.size()) {
                        ownerFullName.append("," + " ");
                    }
                }
            }
            
            assMst.setOwnerFullName(ownerFullName.toString());
            // End of D#126417
        }

        return assMst;
    }

    @Override
    @Transactional
    @POST
    @Path("/fetchChargesForMuatationForMobile")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyTransferMasterDto fetchChargesForMuatationForMobile(PropertyTransferMasterDto transferDto) {
        ProvisionalAssesmentMstDto prvDto = null;

        prvDto = assesmentMastService.fetchAssessmentMasterByPropNo(transferDto.getOrgId(), transferDto.getProAssNo());

        Long serviceId = transferDto.getSmServiceId();
        Long orgId = transferDto.getOrgId();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        if (service.getSmFeesSchedule().equals(1l)) {
            transferDto.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
            transferDto.setAppliChargeFlag(MainetConstants.N_FLAG);
        }
        if (transferDto.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
            List<BillDisplayDto> charges = propertyBRMSService.fetchApplicationOrScurtinyCharges(org,
                    transferDto.getDeptId(), MainetConstants.Property.MUT,
                    MainetConstants.ChargeApplicableAt.APPLICATION, transferDto, prvDto);
            transferDto.setCharges(charges);
            transferDto.setBillTotalAmt(getTotalPayableAmount(charges));
        }
        return transferDto;
    }

    /*
     * Defect no this 26817 regarding pass dto here by Sharvan
     */
    @Override
    @POST
    @Path("/fetchChargesForMuatation")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyTransferMasterDto fetchChargesForMuatation(PropertyTransferMasterDto transferDto,
            ProvisionalAssesmentMstDto prvDto) {
        Long serviceId = transferDto.getSmServiceId();
        Long orgId = transferDto.getOrgId();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        /*
         * if (service.getSmFeesSchedule().equals(1l)) { transferDto.setAppliChargeFlag(service.getSmAppliChargeFlag()); } else {
         * transferDto.setAppliChargeFlag(MainetConstants.N_FLAG); }
         */
        transferDto.setAssesmentMstDto(prvDto);
        if (StringUtils.isNotBlank(transferDto.getAppliChargeFlag())
                && transferDto.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
            List<BillDisplayDto> charges = propertyBRMSService.fetchApplicationOrScurtinyCharges(org,
                    transferDto.getDeptId(), MainetConstants.Property.MUT,
                    MainetConstants.ChargeApplicableAt.APPLICATION, transferDto, prvDto);
            transferDto.setCharges(charges);
            transferDto.setBillTotalAmt(getTotalPayableAmount(charges));
        }

        else if (StringUtils.isNotBlank(transferDto.getScrutinyChargeFlag())
                && transferDto.getScrutinyChargeFlag().equals(MainetConstants.Y_FLAG)) {
            List<BillDisplayDto> charges = propertyBRMSService.fetchApplicationOrScurtinyCharges(org,
                    transferDto.getDeptId(), MainetConstants.Property.MUT, MainetConstants.ChargeApplicableAt.SCRUTINY,
                    transferDto, prvDto);
            transferDto.setCharges(charges);
            transferDto.setBillTotalAmt(getTotalPayableAmount(charges));
        }

        return transferDto;
    }

    private double getTotalPayableAmount(List<BillDisplayDto> charges) {
        double totAmt = 0;
        for (BillDisplayDto charge : charges) {
            totAmt = totAmt + charge.getTotalTaxAmt().doubleValue();
        }
        return totAmt;
    }

    @Override
    @POST
    @Path("/callWorkFlowForFreeService")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyTransferMasterDto callWorkFlowForFreeService(PropertyTransferMasterDto transferDto) {
        ApplicationMetadata applicationData = new ApplicationMetadata();
        Organisation org = new Organisation();
        org.setOrgid(transferDto.getOrgId());
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicationData.setApplicationId(transferDto.getApmApplicationId());
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)
                && StringUtils.isNotEmpty(transferDto.getReferenceNo())) {
            applicationData.setReferenceId(transferDto.getReferenceNo());
        }
        // Task#92835
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT, org.getOrgid());
        String checkListRequiredFlag = service.getSmCheckListReq();
        LookUp checkListVerificationyFlag = null;
        try {
            checkListVerificationyFlag = CommonMasterUtility.getValueFromPrefixLookUp("CVF", "ENV", org);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if ((checkListVerificationyFlag != null) && (checkListRequiredFlag != null)) {
            if (StringUtils.equals(checkListVerificationyFlag.getOtherField(), MainetConstants.FlagY)) {
                if (transferDto.getDocs() != null && !transferDto.getDocs().isEmpty()
                        && checkListRequiredFlag.equals(MainetConstants.Y_FLAG)) {
                    applicationData.setIsCheckListApplicable(true);
                } else if (transferDto.getDocs() != null && !transferDto.getDocs().isEmpty()
                        && checkListRequiredFlag.equals(MainetConstants.N_FLAG)) {
                    applicationData.setIsCheckListApplicable(false);
                }
            }
        } else {
            applicationData.setIsCheckListApplicable(
                    (transferDto.getDocs() != null && !transferDto.getDocs().isEmpty()) ? true : false);
        }
        // Task#92835 END
        applicationData.setOrgId(transferDto.getOrgId());
        applicantDetailDto.setServiceId(transferDto.getSmServiceId());
        applicantDetailDto.setDepartmentId(transferDto.getDeptId());
        applicantDetailDto.setUserId(transferDto.getEmpId());
        applicantDetailDto.setOrgId(transferDto.getOrgId());
        applicantDetailDto.setMobileNo(transferDto.getPropTransferOwnerList().get(0).getMobileno());
        // LocOperationWZMappingDto operWZDto =
        // iLocationMasService.findOperLocationAndDeptId(transferDto.getLocationId(),
        // transferDto.getDeptId());
        ProvisionalAssesmentMstDto assesmentMstDto = transferDto.getAssesmentMstDto();
        if (assesmentMstDto != null) {
            if (assesmentMstDto.getAssWard1() != null) {
                applicantDetailDto.setDwzid1(assesmentMstDto.getAssWard1());
            }
            if (assesmentMstDto.getAssWard2() != null) {
                applicantDetailDto.setDwzid2(assesmentMstDto.getAssWard2());
            }
            if (assesmentMstDto.getAssWard3() != null) {
                applicantDetailDto.setDwzid3(assesmentMstDto.getAssWard3());
            }
            if (assesmentMstDto.getAssWard4() != null) {
                applicantDetailDto.setDwzid4(assesmentMstDto.getAssWard4());
            }
            if (assesmentMstDto.getAssWard5() != null) {
                applicantDetailDto.setDwzid5(assesmentMstDto.getAssWard5());
            }
        }
        commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
        return transferDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @POST
    @Path("/saveMutation")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyTransferMasterDto saveMutation(PropertyTransferMasterDto propTranMstDto) {
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(propTranMstDto.getOrgId());
        setRequestApplicantDetails(reqDto, propTranMstDto);

        final Long applicationNo = applicationService.createApplication(reqDto);

        propTranMstDto.setApmApplicationId(applicationNo);
        propertyTransferService.saveAndUpadPropTransferMast(propTranMstDto);

        if (propTranMstDto.getMutIntiFlag() != null && propTranMstDto.getMutIntiFlag().equals(MainetConstants.Y_FLAG)) {
            mutationIntimationService.updateMutationRegistrationByMutId(applicationNo, propTranMstDto.getMutId(),
                    propTranMstDto.getOrgId());
        }
        if ((propTranMstDto.getDocs() != null) && !propTranMstDto.getDocs().isEmpty()) {
            reqDto.setApplicationId(applicationNo);
            fileUploadService.doFileUpload(propTranMstDto.getDocs(), reqDto);
        }
        // code for workflow call for free service Defect#85146
        if (propTranMstDto.getAppliChargeFlag() != null
                && !propTranMstDto.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
            callWorkFlowForFreeService(propTranMstDto);
        }
        return propTranMstDto;
    }

    private void setRequestApplicantDetails(final RequestDTO reqDto, PropertyTransferMasterDto propTranMstDto) {
        Organisation org = organisationService.getOrganisationById(propTranMstDto.getOrgId());
        PropertyTransferOwnerDto ownDtlDto = propTranMstDto.getPropTransferOwnerList().get(0);
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
        	ApplicantDetailDTO applicantDetailDto = propTranMstDto.getApplicantDetailDto();
        	reqDto.setTitleId(applicantDetailDto.getApplicantTitle());
        	reqDto.setfName(applicantDetailDto.getApplicantFirstName());
        	reqDto.setmName(applicantDetailDto.getApplicantMiddleName());
        	reqDto.setlName(applicantDetailDto.getApplicantLastName());
        	reqDto.setBldgName(applicantDetailDto.getBuildingName());
        	reqDto.setBlockName(applicantDetailDto.getBlockName());
        	reqDto.setAreaName(applicantDetailDto.getAreaName());
        	reqDto.setZoneNo(applicantDetailDto.getDwzid1());
        	reqDto.setReferenceId(propTranMstDto.getProAssNo());
        	if(applicantDetailDto.getDwzid2() != null) {
        		reqDto.setWardNo(applicantDetailDto.getDwzid2());
        	}
        	if(applicantDetailDto.getDwzid3() != null) {
        		reqDto.setBlockNo(String.valueOf(applicantDetailDto.getDwzid3()));
        	}
        	reqDto.setRoadName(applicantDetailDto.getRoadName());
        	reqDto.setCityName(applicantDetailDto.getAreaName());
        	if(StringUtils.isNotEmpty(applicantDetailDto.getPinCode())) {
        		reqDto.setPincodeNo(Long.valueOf(applicantDetailDto.getPinCode()));
        	}
        	reqDto.setMobileNo(applicantDetailDto.getMobileNo());
        	reqDto.setEmail(applicantDetailDto.getEmailId());
        }else {
        	reqDto.setfName(ownDtlDto.getOwnerName());
            reqDto.setMobileNo(ownDtlDto.getMobileno());
            reqDto.setEmail(ownDtlDto.geteMail());
            if (reqDto.getGender() != null) {
                reqDto.setGender(ownDtlDto.getGenderId().toString());
            }
        }
        reqDto.setDeptId(propTranMstDto.getDeptId());
        reqDto.setPayStatus(MainetConstants.PAYMENT.FREE);
        
        reqDto.setOrgId(propTranMstDto.getOrgId());
        reqDto.setServiceId(propTranMstDto.getSmServiceId());
        reqDto.setLangId(Long.valueOf(propTranMstDto.getLangId()));
        reqDto.setUserId(propTranMstDto.getEmpId());
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)
                && StringUtils.isNotEmpty(propTranMstDto.getReferenceNo())) {
            reqDto.setReferenceId(propTranMstDto.getReferenceNo());
        }
    }

    @Override
    @WebMethod(exclude = true)
    public void saveUploadedFile(PropertyTransferMasterDto proAssDto, Long orgId, Employee emp, Long deptId, int langId,
            Long serviceId) {
        RequestDTO reqDto = new RequestDTO();
        reqDto.setApplicationId(proAssDto.getApmApplicationId());
        reqDto.setDeptId(deptId);
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(emp.getEmpId());
        fileUploadService.doFileUpload(proAssDto.getDocs(), reqDto);
    }
    
    
    @Override
    @WebMethod
    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateWorkflowAction")
    public CommonAppResponseDTO updateWorkflowAction(PropertyRequestDTO requestDTO) {
    	
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " updateWorkflowAction() method");
    	CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
    	WorkflowTaskAction workFlowActionDto = new WorkflowTaskAction();
		 Employee emp = new Employee();
		 emp.setEmpId(requestDTO.getEmpId());
		 emp.setLgIpMac(requestDTO.getLgIpMac());
		 emp.setEmppiservername(requestDTO.getLgIpMac());
		 Long deptId = requestDTO.getDeptId();
		 Long orgId = requestDTO.getOrgId();
		 int langId = requestDTO.getLangId();
		 Long serviceId = requestDTO.getServiceId();
		 Long taskId = requestDTO.getTaskId();
		 workFlowActionDto.setDecision(requestDTO.getDecision());
		 workFlowActionDto.setApplicationId(requestDTO.getApplicationId());
		 workFlowActionDto.setTaskId(taskId);
		 workFlowActionDto.setComments(requestDTO.getComments());
		 Organisation org = organisationService.getOrganisationById(orgId);
		 try {
			 
			 PropertyTransferMasterDto transferMasterDto = propertyTransferService.getPropTransferMstByAppId(orgId, requestDTO.getApplicationId());
			 
			 boolean isBeforeLastAuthorizer = isBeforeLastAuthoriser(taskId);
			 boolean isLastAuthorizer = iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId);
			 if(CollectionUtils.isNotEmpty(requestDTO.getAttachments())) {
				 transferMasterDto.setDocs(requestDTO.getAttachments());
				 saveUploadedFile(transferMasterDto, orgId, emp, deptId, langId, serviceId);
			 }
			 transferMasterDto.setDeptId(deptId);
		     saveAuthorizationWithoutEdit(workFlowActionDto, orgId, emp, transferMasterDto, isLastAuthorizer, deptId,
		    		isBeforeLastAuthorizer, requestDTO.getLgIpMac());
		    	
		    sendEmailSMS(requestDTO, langId, org, transferMasterDto);
		 }catch(Exception e) {
			 LOGGER.error("Excepton occured while updateWorkflowAction : " + e.getMessage());
             responseDTO.setErrorMsg("Excepton occured while updateWorkflowAction : " + e.getMessage());
             responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
             return responseDTO;
        }
        responseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
        responseDTO.setHttpstatus(HttpStatus.OK);
		
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " updateWorkflowAction() method");
		return responseDTO;
    	
    }

	private void sendEmailSMS(PropertyRequestDTO requestDTO, int langId,
			Organisation org, PropertyTransferMasterDto transferMasterDto) {
		SMSAndEmailDTO dto = new SMSAndEmailDTO();
		List<PropertyTransferOwnerDto> ownerDtoList = transferMasterDto.getPropTransferOwnerList();
		StringBuilder owner = new StringBuilder();
		for (PropertyTransferOwnerDto owners : ownerDtoList) {
			if (StringUtils.isBlank(owner.toString())) {
				owner.append(owners.geteMail());
			} else if (StringUtils.isNotBlank(owners.geteMail())) {
				owner.append(MainetConstants.operator.COMMA + owners.geteMail());
			}
		}
		dto.setEmail(owner.toString());			
		dto.setMobnumber(ownerDtoList.get(0).getMobileno());			
		dto.setUserId(requestDTO.getEmpId());
		dto.setAppNo(requestDTO.getApplicationId().toString());
		dto.setMsg(requestDTO.getDecision());		
		ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
				"MutationAuthorization.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, langId);
	}

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String saveAuthorizationWithoutEdit(WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp,
            PropertyTransferMasterDto propTranMstDto, boolean isLastAuthorizer, Long deptId,
            boolean isBeforeLastAuthorizer, String clientIpAddress) {
        NoticeMasterDto noticeMasDto = null;
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        String noticePeriod = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC, org)
                .getOtherField();
        if (!StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.REJECTED) &&
                !StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.SEND_BACK) &&
                !StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT) &&
                !StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.HOLD)) {
            if (isBeforeLastAuthorizer && (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
                    MainetConstants.Property.MUT))) {
                addInactiveEntryInObjection(propTranMstDto, deptId, emp, noticePeriod);
                noticeMasDto = propertyNoticeService.saveNoticeApplicableForObjection(propTranMstDto.getProAssNo(),
                        propTranMstDto.getApmApplicationId(), propTranMstDto.getSmServiceId(), orgId,
                        UserSession.getCurrent().getEmployee(), "SP", MainetConstants.Property.propPref.SNC,
                        propTranMstDto.getDeptId(), null);
                workFlowActionDto.setIsFinalApproval(false);
            } else if (isLastAuthorizer && (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
                    MainetConstants.Property.MUT))) {
                AssesmentMastEntity assesmentMastEntity = null;
                List<AssesmentOwnerDtlEntity> ownerDtlEntityList = new ArrayList<>();
                List<AssesmentMastEntity> assesmentMastEntityList = assesmentMstRepository.getAssessmentMstByPropNo(orgId,
                        propTranMstDto.getProAssNo());
				if (CollectionUtils.isNotEmpty(assesmentMastEntityList)) {
					assesmentMastEntity = assesmentMastEntityList.get(assesmentMastEntityList.size() - 1);
				}
                List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity = ownerRepository
                        .fetchOwnerDetailListByProAssId(assesmentMastEntity, propTranMstDto.getOrgId());

                assesmentMstRepository.updateAssesmentDetails(orgId, propTranMstDto.getProAssNo(),
                        propTranMstDto.getOwnerType(), emp.getEmpId(), clientIpAddress);
                List<PropertyTransferOwnerDto> propTransferOwnerList = propTranMstDto.getPropTransferOwnerList();

                for (PropertyTransferOwnerDto entity : propTransferOwnerList) {
                    AssesmentOwnerDtlEntity ownerDtl = new AssesmentOwnerDtlEntity();
                    if (assesmentMastEntity != null) {
                        ownerDtl.setMnAssId(assesmentMastEntity);
                    }
                    // Defect #94099
                    if (ownerDtl.getProAssoId() == 0) {
                        final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                                MainetConstants.Property.primColumn.PRO_ASSO_ID, null, null);
                        ownerDtl.setProAssoId(proAssoId);
                    }

                    ownerDtl.setAssoOType(entity.getOtype());
                    ownerDtl.setAssoOwnerName(entity.getOwnerName());
                    ownerDtl.setAssoOwnerNameReg(entity.getAssoOwnerNameReg());
                    ownerDtl.setGenderId(entity.getGenderId());
                    ownerDtl.setRelationId(entity.getRelationId());
                    ownerDtl.setAssoGuardianName(entity.getGuardianName());
                    ownerDtl.setAssoMobileno(entity.getMobileno());
                    ownerDtl.seteMail(entity.geteMail());
                    ownerDtl.setAssoAddharno(entity.getAddharno());
                    ownerDtl.setAssoPanno(entity.getPanno());
                    ownerDtl.setAssNo(entity.getAssNo());
                    ownerDtl.setAssoActive(MainetConstants.STATUS.ACTIVE);
                    ownerDtl.setPropertyShare(entity.getPropertyShare());
                    ownerDtl.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                    ownerDtl.setSmServiceId(entity.getSmServiceId());
                    ownerDtl.setCreatedBy(emp.getEmpId());
                    ownerDtl.setLgIpMac(clientIpAddress);
                    ownerDtl.setCreatedDate(new Date());
                    ownerDtl.setOrgId(orgId);
                    ownerDtl.setApmApplicationId(entity.getApmApplicationId());
                    ownerDtlEntityList.add(ownerDtl);
                }
                ownerRepository.save(ownerDtlEntityList);

                ownerRepository.delete(oldOwnerDtlEntity);

                // To maintain history of old owners
                AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
                mastHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
                assesmentMastEntity.setUpdatedDate(new Date());
                assesmentMastEntity.setUpdatedBy(emp.getEmpId());
                assesmentMastEntity.setLgIpMacUpd(clientIpAddress);
                assesmentMastEntity.setApmApplicationId(workFlowActionDto.getApplicationId());
                auditService.createHistory(assesmentMastEntity, mastHistEntity);

                for (AssesmentOwnerDtlEntity oldOwnerEntity : oldOwnerDtlEntity) {
                    AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
                    ownerDtlHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
                    ownerDtlHistEntity.setProAssoId(oldOwnerEntity.getProAssoId());
                    oldOwnerEntity.setUpdatedDate(new Date());
                    oldOwnerEntity.setUpdatedBy(emp.getEmpId());
                    oldOwnerEntity.setApmApplicationId(workFlowActionDto.getApplicationId());
                    oldOwnerEntity.setLgIpMacUpd(clientIpAddress);
                    auditService.createHistory(oldOwnerEntity, ownerDtlHistEntity);
                }

                workFlowActionDto.setIsFinalApproval(true);
            }
        }
		if (isLastAuthorizer) {
			String clmAprStatus = "";
			if (StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.APPROVED)) {
				clmAprStatus = MainetConstants.FlagY;
			} else if (StringUtils.equals(workFlowActionDto.getDecision(),
					MainetConstants.WorkFlow.Decision.REJECTED)) {
				clmAprStatus = MainetConstants.FlagN;
			}
			ServiceMaster service = serviceMaster.getServiceMaster(propTranMstDto.getSmServiceId(), orgId);
			if (StringUtils.equals(service.getSmCheckListReq(), MainetConstants.FlagN)) {
				cfcAttechmentRepository.updateClmAprStatus(clmAprStatus, orgId, propTranMstDto.getApmApplicationId());
			}
		}

        initiateWorkFlow(workFlowActionDto, orgId, emp, propTranMstDto.getApmApplicationId(),
                propTranMstDto.getSmServiceId(), isLastAuthorizer, noticePeriod);
        if (noticeMasDto != null && noticeMasDto.getNotNo() != null) {
            return noticeMasDto.getNotNo();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String saveAuthorizationWithEdit(WorkflowTaskAction workFlowActionDto,
            PropertyTransferMasterDto propTranMstDto, Long orgId, Employee emp, Long deptId) {
        propTranMstDto.setOrgId(orgId);
        propTranMstDto.setEmpId(emp.getEmpId());
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        propertyTransferService.saveAndUpadPropTransferMast(propTranMstDto);
        String noticePeriod = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC, org)
                .getOtherField();
        addInactiveEntryInObjection(propTranMstDto, deptId, emp, noticePeriod);
        NoticeMasterDto noticeMasDto = propertyNoticeService.saveNoticeApplicableForObjection(propTranMstDto.getProAssNo(),
                propTranMstDto.getApmApplicationId(), propTranMstDto.getSmServiceId(), orgId,
                UserSession.getCurrent().getEmployee(), "SP", MainetConstants.Property.propPref.SNC,
                propTranMstDto.getDeptId(), null);
        initiateWorkFlow(workFlowActionDto, orgId, emp, propTranMstDto.getApmApplicationId(),
                propTranMstDto.getSmServiceId(), true, noticePeriod);
        return noticeMasDto.getNotNo();
        // byDefault true because last authorizer only can do Edit
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String saveMutationApproval(WorkflowTaskAction workFlowActionDto, PropertyTransferMasterDto propTranMstDto,
            Organisation org, Employee emp, Long deptId, Map<Long, Double> loiCharges) {
        final ProvisionalAssesmentMstDto provAssDto = assesmentMastService
                .fetchAssessmentMasterByPropNoWithKey(org.getOrgid(), propTranMstDto.getProAssNo());
        provAssDto.setAssOwnerType(propTranMstDto.getOwnerType());
        propTranMstDto.getPropTransferOwnerList().forEach(tarnOwner -> {
            ProvisionalAssesmentOwnerDtlDto owner = new ProvisionalAssesmentOwnerDtlDto();
            owner.setAssoOwnerName(tarnOwner.getOwnerName());
            owner.setGenderId(tarnOwner.getGenderId());
            owner.setRelationId(tarnOwner.getRelationId());
            owner.setAssoGuardianName(tarnOwner.getGuardianName());
            owner.setAssoMobileno(tarnOwner.getMobileno());
            owner.seteMail(tarnOwner.geteMail());
            owner.setAssoAddharno(tarnOwner.getAddharno());
            owner.setPropertyShare(tarnOwner.getPropertyShare());
            owner.setSmServiceId(tarnOwner.getSmServiceId());
            owner.setApmApplicationId(tarnOwner.getApmApplicationId());
            provAssDto.getProvisionalAssesmentOwnerDtlDtoList().add(owner);
        });
        assesmentMastService.saveAndUpdateAssessmentMastInMutation(provAssDto, org.getOrgid(), emp.getEmpId(),
                emp.getEmppiservername());
        primaryPropertyService.savePropertyMaster(provAssDto, org.getOrgid(), emp.getEmpId());

        if (loiCharges != null) {
            List<LoiDetail> loiDetList = new ArrayList<>();
            LoiDetail loidet = new LoiDetail();
            TbLoiMas loiMasDto = new TbLoiMas();
            loiMasDto.setLoiApplicationId(propTranMstDto.getApmApplicationId());
            loiMasDto.setLoiServiceId(propTranMstDto.getSmServiceId());
            // loiMasDto.setLoiDateStr(loiDateStr);
            String loiNumber = TbLoiMasService.saveLOIMaster(loiCharges, null, org, emp, loiMasDto);
            loidet.setLoiNumber(loiNumber);
            loidet.setLoiPaymentApplicable(true);
            loidet.setIsComplianceApplicable(false);
            loidet.setIsApprovalLetterGenerationApplicable(false);
            loiDetList.add(loidet);
            workFlowActionDto.setLoiDetails(loiDetList);
        }
        List<Long> attachementId = iChecklistVerificationService
                .fetchAttachmentIdByAppid(propTranMstDto.getApmApplicationId(), org.getOrgid());
        workFlowActionDto.setAttachementId(attachementId);

        iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, org.getOrgid(), propTranMstDto.getSmServiceId());

        return null;
    }

    private void initiateWorkFlow(WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long applicationId,
            Long serviceId, boolean isLastAuthorizer, String noticePeriod) {
    	
        List<Long> attachementId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId, orgId);
        workFlowActionDto.setAttachementId(attachementId);
        if (isLastAuthorizer) {
        	workFlowActionDto.setIsObjectionAppealApplicable(false);
        	workFlowActionDto.setSignalExpiryDelay(noticePeriod);
            workFlowActionDto.setSignalExpiryDelayUnit(BpmpDelayPeriods.DAY);
        }
        iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId, serviceId);
    }

    /*
     * get LOI charges during LOI generation through task
     */
    @Override
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId) {

        Map<Long, Double> chargeMap = null;
        ServiceMaster service = serviceMasterService.getServiceMaster(serviceId, orgId);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<BillDisplayDto> billDtoList = propertyBRMSService.fetchApplicationOrScurtinyCharges(org,
                service.getTbDepartment().getDpDeptid(), service.getSmShortdesc(),
                MainetConstants.ChargeApplicableAt.SCRUTINY, null, null);
        if (!billDtoList.isEmpty()) {
            chargeMap = new HashMap<>();
            for (BillDisplayDto dto : billDtoList) {
                chargeMap.put(dto.getTaxId(), dto.getTotalTaxAmt().doubleValue());
            }
        }
        return chargeMap;
    }

    /*
     * get LOI charges during LOI generation through task
     */
    @Override
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiChargesAtApproval(PropertyTransferMasterDto transDto, final ServiceMaster service,
            final Organisation org, ProvisionalAssesmentMstDto assMstDto) {
        Map<Long, Double> chargeMap = null;
        List<BillDisplayDto> billDtoList = propertyBRMSService.fetchApplicationOrScurtinyCharges(org,
                service.getTbDepartment().getDpDeptid(), service.getSmShortdesc(),
                MainetConstants.ChargeApplicableAt.SCRUTINY, transDto, assMstDto);
        if (!billDtoList.isEmpty()) {
            chargeMap = new HashMap<>();
            for (BillDisplayDto dto : billDtoList) {
                chargeMap.put(dto.getTaxId(), dto.getTotalTaxAmt().doubleValue());
            }
            transDto.setCharges(billDtoList);
        }
        return chargeMap;
    }

    private void addInactiveEntryInObjection(PropertyTransferMasterDto propTranMstDto, Long deptId, Employee emp,
            String noticePeriod) {
        ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();
        objectionDetailsDto.setApmApplicationId(propTranMstDto.getApmApplicationId());
        objectionDetailsDto.setObjectionReferenceNumber(propTranMstDto.getProAssNo());
        objectionDetailsDto.setObjectionDeptId(deptId);
        objectionDetailsDto.setServiceId(propTranMstDto.getSmServiceId());
        objectionDetailsDto.setObjectionStatus(MainetConstants.STATUS.INACTIVE);
        objectionDetailsDto.setObjectionDetails(MainetConstants.Objection.OBJ_DETAIL_INACTIVE);
        objectionDetailsDto.setOrgId(propTranMstDto.getOrgId());
        objectionDetailsDto.setObjTime(TimeUnit.MILLISECONDS.convert(Long.valueOf(noticePeriod), TimeUnit.DAYS));

        iObjectionDetailsService.saveAndUpateObjectionMaster(objectionDetailsDto, emp.getEmpId(),
                emp.getEmppiservername());
    }

    @Override
    @Transactional
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long org) {

        // get unique data from tb_as_transfer_mst using application id and orgid and
        // serviceId
        // property no
        String propNo = propertyTransferService.getPropertyNoByAppId(org, applicationId, serviceId);
        ProvisionalAssesmentMstDto assMst = assesmentMastService.fetchAssessmentMasterByPropNo(org, propNo);

        // final List<ProvisionalAssesmentMstEntity> assMstList =
        // iProvisionalAssesmentMstService
        // .getProAssMasterByApplicationId(org, applicationId);
        WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();
        if (assMst != null) {
            // ProvisionalAssesmentMstEntity assMst = assMstList.get(0);
            if (assMst.getAssWard1() != null) {
                wardZoneDTO.setAreaDivision1(assMst.getAssWard1());
            }
            if (assMst.getAssWard2() != null) {
                wardZoneDTO.setAreaDivision2(assMst.getAssWard2());
            }
            if (assMst.getAssWard3() != null) {
                wardZoneDTO.setAreaDivision3(assMst.getAssWard3());
            }
            if (assMst.getAssWard4() != null) {
                wardZoneDTO.setAreaDivision4(assMst.getAssWard4());
            }
            if (assMst.getAssWard4() != null) {
                wardZoneDTO.setAreaDivision5(assMst.getAssWard4());
            }
        }
        return wardZoneDTO;
    }

    @Override
    @Transactional
    @POST
    @Path("/fetchChargesForMuatationForPortal")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyTransferMasterDto fetchChargesForMuatationForPortal(
            @RequestBody PropertyTransferMasterDto transferDto) {
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT,
                transferDto.getOrgId());
        if (service.getSmFeesSchedule().equals(1l)) {
            transferDto.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
            transferDto.setAppliChargeFlag(MainetConstants.N_FLAG);
        }
        fetchChargesForMuatation(transferDto, transferDto.getAssesmentMstDto());
        return transferDto;
    }

    @Override
    @Transactional
    public void updateCertificateNo(String certificateNo, Long empId, Long apmApplicationId, Long orgid) {
        propertyTransferRepository.updateCertificateNo(certificateNo, empId, apmApplicationId, orgid);
    }

    @Override
    @Transactional
    @POST
    @Path("/MutationPrint")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.APPLICATION_JSON)
    public List<PropertyTransferMasterDto> getMutationData(String propno, String oldPropNo, Long applicationId,
            Long orgId) {
        List<PropertyTransferMasterDto> propTranMstDto = new ArrayList<>();
        List<PropertyTransferMasterDto> propTranMstDto1 = new ArrayList<>();
        ProvisionalAssesmentMstDto assMst = new ProvisionalAssesmentMstDto();
        String propNo = null;
        if (!oldPropNo.isEmpty() && propno.isEmpty()) {
            assMst = assesmentMastService.fetchAssessmentMasterByOldPropNo(orgId, oldPropNo);
        }
        if (assMst != null && assMst.getAssNo() != null) {
            propNo = assMst.getAssNo();
        } else {
            propNo = propno;
        }
        List<PropertyTransferMasterEntity> list = propertyTransferRepository.getPropTransferMst(orgId, applicationId,
                propNo);
        if (!list.isEmpty()) {
            list.forEach(entity -> {
                PropertyTransferMasterDto dto = new PropertyTransferMasterDto();
                BeanUtils.copyProperties(entity, dto);
                propTranMstDto.add(dto);
            });
            Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                    .getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
            
            List<TbServiceReceiptMasEntity> receiptDetailsByAppIdList = receiptRepository.getReceiptDetailsByAppIdList(list.get(0).getApmApplicationId(), orgId);
            
            
            if (CollectionUtils.isNotEmpty(receiptDetailsByAppIdList)) {
                for (int i = receiptDetailsByAppIdList.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < propTranMstDto.size(); j++) {
                       if (receiptDetailsByAppIdList.get(i).getApmApplicationId() != null && (receiptDetailsByAppIdList.get(i).getApmApplicationId()
                                .equals(propTranMstDto.get(j).getApmApplicationId())) && StringUtils.isBlank(receiptDetailsByAppIdList.get(i).getRmLoiNo())) {
                            propTranMstDto.get(j).setReceiptNo(receiptDetailsByAppIdList.get(i).getRmRcptno());
                            propTranMstDto.get(j).setReceiptId(receiptDetailsByAppIdList.get(i).getRmRcptid());
                            propTranMstDto1.add(propTranMstDto.get(j));
                        }
                    }
                }
            }
            
            if (CollectionUtils.isNotEmpty(receiptDetailsByAppIdList)) {
            	receiptDetailsByAppIdList.forEach(receipt ->{
            		if(StringUtils.isNotBlank(receipt.getRmLoiNo())) {
            			if(CollectionUtils.isNotEmpty(propTranMstDto1)) {
            				propTranMstDto1.forEach(propTrans ->{
            					propTrans.setLoiReceiptId(receipt.getRmRcptid());
            				});
            			}
            		}
            	});
            }
        }
        
        return propTranMstDto1;
    }

    @Override
    @GET
    @Path("/MutationCheck/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mutationCheck(@PathParam(value = "propertyId") String propertyId) {
        MutationDetailDto detailDto = new MutationDetailDto();
        AssesmentMastEntity mastEntity = assesmentMstRepository.fetchPropertyByPropNo(propertyId);
        Format f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String asOnDate = f.format(new Date());
        if (mastEntity != null) {
            List<AssesmentDetailEntity> detailEntitiyList = assessmentDetailRepository.fetchAssdIdByAssId(mastEntity);
            List<AssesmentOwnerDtlEntity> ownerEntityList = ownerRepository.fetchOwnerDetailListByPropNo(propertyId,
                    mastEntity.getOrgId());
            List<Double> totalARV = new ArrayList<Double>();
            for (AssesmentDetailEntity dto : detailEntitiyList) {
                if (dto.getAssdRv() != null) {
                    totalARV.add(dto.getAssdRv());
                }
            }
            detailDto.setValid(MainetConstants.YES);
            detailDto.setAsOnDateTime(asOnDate);
            detailDto.setPropertyID(propertyId);
            if (mastEntity.getAssWard1() != null) {
                detailDto.setZoneName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard1(), mastEntity.getOrgId())
                                .getDescLangFirst());
            }
            if (mastEntity.getAssWard2() != null) {
                detailDto.setWardName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard2(), mastEntity.getOrgId())
                                .getDescLangFirst());
            }
            if (mastEntity.getAssWard3() != null) {
                detailDto.setMohallaName(
                        CommonMasterUtility.getHierarchicalLookUp(mastEntity.getAssWard3(), mastEntity.getOrgId())
                                .getDescLangFirst());
            } else {
                detailDto.setMohallaName(MainetConstants.CommonConstants.NA);
            }
            for (AssesmentOwnerDtlEntity ownerEntity : ownerEntityList) {
                if (ownerEntity.getAssoOType() != null
                        && ownerEntity.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                    detailDto.setOwnerName(ownerEntity.getAssoOwnerName());
                    detailDto.setFatherName(ownerEntity.getAssoGuardianName());
                    if (ownerEntity.getAssoMobileno().isEmpty() || ownerEntity.getAssoMobileno() == null) {
                        detailDto.setMobile(MainetConstants.ZERO);
                    } else {
                        detailDto.setMobile(ownerEntity.getAssoMobileno());
                    }
                }
            }
            String houseNo = "";
            if (mastEntity.getTppPlotNo() != null) {
                houseNo = mastEntity.getTppPlotNo();
            }
            detailDto.setAddress(houseNo + " " + mastEntity.getAssAddress());
            Double areaOf = mastEntity.getAssPlotArea();
            long area = areaOf.longValue();
            detailDto.setAreaOfLand(String.valueOf(area));
            List<TbBillMas> paidFlagList = propertyMainBillService.fetchAllBillByPropNo(propertyId, mastEntity.getOrgId());
            Long yearId = financialYearService.getFinanceYearId(new Date());
            if (CollectionUtils.isNotEmpty(paidFlagList)) {
                TbBillMas billMas = paidFlagList.get(paidFlagList.size() - 1);
                if (StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.FlagY) && (yearId.equals(billMas.getBmYear()))) {
                    detailDto.setFullHouseTaxPaid(MainetConstants.YES);
                } else {
                    detailDto.setFullHouseTaxPaid(MainetConstants.NO);
                }
            } else {
                detailDto.setFullHouseTaxPaid(MainetConstants.NO);
            }
            Long ulbCode = organisationJpaRepository.findOrgShortNameByOrgId(mastEntity.getOrgId());
            detailDto.setUlbCode(ulbCode.toString());
            if (CollectionUtils.isNotEmpty(totalARV)) {
                Double arv = totalARV.stream().mapToDouble(Double::doubleValue).sum();
                long av = arv.longValue();
                detailDto.setArv(String.valueOf(av));
            } else {
                detailDto.setArv(MainetConstants.ZERO);
            }
            detailDto.setZoneID(mastEntity.getAssWard1().toString());
        } else {
            detailDto.setValid(MainetConstants.NO);
            detailDto.setAsOnDateTime(asOnDate);
            detailDto.setPropertyID(propertyId);
        }
        JSONObject jsonObject = new JSONObject(detailDto);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }

    @Override
    public PropertyTransferMasterDto getMutationByPropNo(String propno, Long orgId) {
        PropertyTransferMasterDto dto = new PropertyTransferMasterDto();
        PropertyTransferMasterEntity transferMasterEntity = propertyTransferRepository
                .fetchPropTransferMstByPropNo(propno, orgId);
        if (transferMasterEntity != null) {
            BeanUtils.copyProperties(transferMasterEntity, dto);
        }
        return dto;
    }

    @Override
    public String getWorkflowRequestByAppId(Long apmApplicationId, long orgId) {
        return propertyTransferRepository.getWorkflowRequestByAppId(apmApplicationId, orgId);
    }

    /* To Save mutation data after LOI payment - Used in SKDCL */
    @Override
    @Transactional
    @POST
    @Path("/saveMutationAfterloiPayment")
    public Boolean saveMutationAfterloiPayment(CommonChallanDTO dto) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveMutationAfterloiPayment() method");
        try {
            PropertyTransferMasterDto tranferDto = propertyTransferService.getPropTransferMstByAppId(dto.getOrgId(),
                    dto.getApplNo());
            AssesmentMastEntity assesmentMastEntity = null;
            List<AssesmentOwnerDtlEntity> ownerDtlEntityList = new ArrayList<>();
            List<AssesmentMastEntity> assesmentMastEntityList = new ArrayList<>();
            List<AssesmentMastEntity> assesmentMastFlatWiseEntityList = new ArrayList<>();
			Set<String> set = new HashSet<>();
			assesmentMastEntityList = assesmentMstRepository.getAssessmentMstByPropNo(tranferDto.getOrgId(),
					tranferDto.getProAssNo());
			if (tranferDto != null && StringUtils.isNotBlank(tranferDto.getFlatNo())) {
				assesmentMastFlatWiseEntityList = assesmentMstRepository.getPropDetailFromAssByPropNoFlatNo(
						tranferDto.getOrgId(), tranferDto.getProAssNo(), tranferDto.getFlatNo());

				// Filter out distinct flat no's
				assesmentMastEntityList.stream().filter(flat -> set.add(flat.getFlatNo())).collect(Collectors.toList());
			}
			
			if (CollectionUtils.isNotEmpty(assesmentMastFlatWiseEntityList)) {
				assesmentMastEntity = assesmentMastFlatWiseEntityList.get(assesmentMastFlatWiseEntityList.size() - 1);
			} else {
				assesmentMastEntity = assesmentMastEntityList.get(assesmentMastEntityList.size() - 1);
			}

            List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity = ownerRepository
                    .fetchOwnerDetailListByProAssId(assesmentMastEntity, tranferDto.getOrgId());

            StringBuilder newOwnerName = new StringBuilder();
            StringBuilder newOwnerNameReg = new StringBuilder();
            for (PropertyTransferOwnerDto entity : tranferDto.getPropTransferOwnerList()) {
                AssesmentOwnerDtlEntity ownerDtl = new AssesmentOwnerDtlEntity();
                if (assesmentMastEntity != null) {
                    ownerDtl.setMnAssId(assesmentMastEntity);
                }
                if (ownerDtl.getProAssoId() == 0) {
                    final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
                            MainetConstants.Property.primColumn.PRO_ASS_ID, null, null);
                    ownerDtl.setProAssoId(proAssoId);
                }
                ownerDtl.setAssoOType(entity.getOtype());
                ownerDtl.setAssoOwnerName(entity.getOwnerName());
                ownerDtl.setAssoOwnerNameReg(entity.getAssoOwnerNameReg());
                ownerDtl.setGenderId(entity.getGenderId());
                ownerDtl.setRelationId(entity.getRelationId());
                ownerDtl.setAssoGuardianName(entity.getGuardianName());
                ownerDtl.setAssoMobileno(entity.getMobileno());
                ownerDtl.seteMail(entity.geteMail());
                ownerDtl.setAssoAddharno(entity.getAddharno());
                ownerDtl.setAssoPanno(entity.getPanno());
                ownerDtl.setAssNo(entity.getAssNo());
                ownerDtl.setAssoActive(MainetConstants.STATUS.ACTIVE);
                ownerDtl.setPropertyShare(entity.getPropertyShare());
                ownerDtl.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                ownerDtl.setSmServiceId(entity.getSmServiceId());
                ownerDtl.setCreatedBy(dto.getUserId());
                ownerDtl.setLgIpMac(dto.getLgIpMac());
                ownerDtl.setCreatedDate(new Date());
                ownerDtl.setOrgId(tranferDto.getOrgId());
                ownerDtlEntityList.add(ownerDtl);
                if (StringUtils.isBlank(newOwnerName.toString())) {
                    newOwnerName.append(entity.getOwnerName() + MainetConstants.WHITE_SPACE);
                } else {
                    newOwnerName.append(MainetConstants.operator.AMPERSENT + MainetConstants.WHITE_SPACE + entity.getOwnerName());
                }
                if (StringUtils.isBlank(newOwnerNameReg.toString())) {
                    newOwnerNameReg.append(entity.getAssoOwnerNameReg() + MainetConstants.WHITE_SPACE);
                } else {
                    newOwnerNameReg.append(
                            MainetConstants.operator.AMPERSENT + MainetConstants.WHITE_SPACE + entity.getAssoOwnerNameReg()
                                    + MainetConstants.WHITE_SPACE);
                }
            }

            // To maintain history of old owners
            AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
            mastHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
            assesmentMastEntity.setCreatedBy(dto.getUserId());
            assesmentMastEntity.setCreatedDate(new Date());
            assesmentMastEntity.setLgIpMac(dto.getLgIpMac());
            assesmentMastEntity.setApmApplicationId(tranferDto.getApmApplicationId());
            auditService.createHistory(assesmentMastEntity, mastHistEntity);

            for (AssesmentDetailEntity detailEntity : assesmentMastEntity.getAssesmentDetailEntityList()) {
                AssesmentDetailHistEntity detailHisEntity = new AssesmentDetailHistEntity();
                detailHisEntity.setMnAssId(assesmentMastEntity.getProAssId());
                detailHisEntity.setProAssdId(detailEntity.getProAssdId());
                detailHisEntity.setApmApplicationId(tranferDto.getApmApplicationId());
                detailEntity.setCreatedDate(new Date());
                detailEntity.setCreatedBy(dto.getUserId());
                detailEntity.setLgIpMac(dto.getLgIpMac());
                auditService.createHistory(detailEntity, detailHisEntity);
            }
            for (AssesmentOwnerDtlEntity oldOwnerEntity : oldOwnerDtlEntity) {
                AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
                ownerDtlHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
                ownerDtlHistEntity.setProAssoId(oldOwnerEntity.getProAssoId());
                oldOwnerEntity.setCreatedDate(new Date());
                oldOwnerEntity.setCreatedBy(dto.getUserId());
                oldOwnerEntity.setApmApplicationId(tranferDto.getApmApplicationId());
                oldOwnerEntity.setLgIpMac(dto.getLgIpMac());
                auditService.createHistory(oldOwnerEntity, ownerDtlHistEntity);
            }
            // History ends

            // To update owner type against property no
            if (StringUtils.isNotBlank(tranferDto.getFlatNo()) && set.size() <= 1) {
                assesmentMstRepository.updateAssesmentDetailsWithFlat(tranferDto.getOrgId(), tranferDto.getProAssNo(),
                        tranferDto.getOwnerType(), tranferDto.getEmpId(), tranferDto.getLgIpMac(),
                        tranferDto.getFlatNo());
            } else if (StringUtils.isBlank(tranferDto.getFlatNo())){
                assesmentMstRepository.updateAssesmentDetails(tranferDto.getOrgId(), tranferDto.getProAssNo(),
                        tranferDto.getOwnerType(), tranferDto.getEmpId(), tranferDto.getLgIpMac());
            }

            // Update assessment detail table for owner name
            List<Long> detailIds = assesmentMastEntity.getAssesmentDetailEntityList().stream()
                    .map(detail -> detail.getProAssdId()).collect(Collectors.toList());
            assesmentMstRepository.updateAssesmentDetailtableOwners(tranferDto.getOrgId(), newOwnerName.toString(),
                    newOwnerNameReg.toString(), tranferDto.getPropTransferOwnerList().get(0).getMobileno(),
                    tranferDto.getPropTransferOwnerList().get(0).geteMail(), dto.getUserId(), detailIds,
                    dto.getLgIpMac());

            Organisation org = organisationService.getOrganisationById(tranferDto.getOrgId());
            String billMethod = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assesmentMastEntity.getBillMethod(), org).getLookUpCode();

            // In case of individual property owner at property level will get updated only
            // if there is single flat present against that property no
            if (billMethod != null && ((billMethod.equals(MainetConstants.FlagI) && set.size() <= 1)
                    || !billMethod.equals(MainetConstants.FlagI))) {
                ownerRepository.save(ownerDtlEntityList);
                ownerRepository.delete(oldOwnerDtlEntity);
            }

            // Update authorisation status in Transfer Master table
            propertyTransferRepository.updatePropTransferMstStatus(tranferDto.getOrgId(), dto.getApplNo(),
                    tranferDto.getProAssNo(), MainetConstants.FlagA, dto.getUserId());
        } catch (Exception e) {
            LOGGER.error("Excepton occured while saving mutation data : " + e.getMessage());
            return false;
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " saveMutationAfterloiPayment() method");
        return true;
    }
    
    @Override
    @Transactional
    public PropertyTransferMasterDto getMutationByPropNonFlatNo(String propno, String flatNo, long orgId) {
        PropertyTransferMasterDto dto = new PropertyTransferMasterDto();
        PropertyTransferMasterEntity transferMasterEntity = propertyTransferRepository
                .fetchPropTransferMstByPropNoNFlatNo(propno, flatNo, orgId);
        if (transferMasterEntity != null) {
            BeanUtils.copyProperties(transferMasterEntity, dto);
        }
        return dto;
    }

    @Override
    public List<PropertyTransferMasterDto> getMutationDetails(String propno, String oldPropNo, Long applicationNo,
            String flatNo, Long orgId) {
        List<PropertyTransferMasterDto> propTranMstDto = new ArrayList<>();
        ProvisionalAssesmentMstDto assMst = new ProvisionalAssesmentMstDto();
        List<PropertyTransferMasterEntity> list = new ArrayList<>();
        String propNo = null;
        if (StringUtils.isNotBlank(oldPropNo) && propno.isEmpty()) {
            assMst = assesmentMastService.fetchAssessmentMasterByOldPropNo(orgId, oldPropNo);
        }
        if (assMst != null && assMst.getAssNo() != null) {
            propNo = assMst.getAssNo();
        } else {
            propNo = propno;
        }

        ProvisionalAssesmentMstDto assMstNew = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, propNo);
        Organisation org = organisationService.getOrganisationById(orgId);
        String billMethod = null;
        if (assMstNew.getBillMethod() != null) {
            billMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(assMstNew.getBillMethod(), org)
                    .getLookUpCode();
        }
        if (billMethod != null && billMethod.equals(MainetConstants.FlagI)) {
            list = propertyTransferRepository.getPropTransferMstWithFlat(orgId, applicationNo, propNo, flatNo);
        } else {
            list = propertyTransferRepository.getPropTransferMaster(orgId, applicationNo, propNo);
        }

        list.forEach(entity -> {
            PropertyTransferMasterDto dto = new PropertyTransferMasterDto();
            BeanUtils.copyProperties(entity, dto);
            List<PropertyTransferOwnerDto> ownerDtoList = new ArrayList<>();
            entity.getPropTransferOwnerList().forEach(owner -> {
                PropertyTransferOwnerDto ownerDto = new PropertyTransferOwnerDto();
                BeanUtils.copyProperties(owner, ownerDto);
                ownerDtoList.add(ownerDto);
            });
            dto.setPropTransferOwnerList(ownerDtoList);
            propTranMstDto.add(dto);
        });
        return propTranMstDto;
    }
    
	@Override
	@Transactional
	public void updateMutationDetails(ProvisionalAssesmentMstDto provAssDto, PropertyTransferMasterDto tranferDto,
			WorkflowTaskAction workFlowActionDto, Employee emp, Long serviceId, String ownerCode, String multipleFlats,
			Organisation org) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " updateMutationDetails() method");
		AssesmentMastEntity assesmentMastEntity = null;
		List<AssesmentMastEntity> assesmentMastEntityList = new ArrayList<>();
		if (StringUtils.isNotBlank(provAssDto.getFlatNo())) {
			assesmentMastEntityList = assesmentMstRepository.getPropDetailFromAssByPropNoFlatNo(org.getOrgid(),
					provAssDto.getAssNo(), provAssDto.getFlatNo());
		} else {
			assesmentMastEntityList = assesmentMstRepository.getAssessmentMstByPropNo(org.getOrgid(),
					provAssDto.getAssNo());
		}
		assesmentMastEntity = assesmentMastEntityList.get(assesmentMastEntityList.size() - 1);
		
		List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity = ownerRepository
				.fetchOwnerDetailListByProAssId(assesmentMastEntity, org.getOrgid());
		StringBuilder newOwnerName = new StringBuilder();
		StringBuilder newOwnerNameReg = new StringBuilder();
		List<AssesmentOwnerDtlEntity> ownerDtlEntityList = new ArrayList<>();
		for (PropertyTransferOwnerDto entity : tranferDto.getPropTransferOwnerList()) {
			AssesmentOwnerDtlEntity ownerDtl = new AssesmentOwnerDtlEntity();
			if (assesmentMastEntity != null) {
				ownerDtl.setMnAssId(assesmentMastEntity);
			}
			if (ownerDtl.getProAssoId() == 0) {
				final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
						MainetConstants.Property.PROP_DEPT_SHORT_CODE,
						MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
						MainetConstants.Property.primColumn.PRO_ASS_ID, null, null);
				ownerDtl.setProAssoId(proAssoId);
			}
			ownerDtl.setAssoOType(entity.getOtype());
			ownerDtl.setAssoOwnerName(entity.getOwnerName());
			ownerDtl.setAssoOwnerNameReg(entity.getAssoOwnerNameReg());
			ownerDtl.setGenderId(entity.getGenderId());
			ownerDtl.setRelationId(entity.getRelationId());
			ownerDtl.setAssoGuardianName(entity.getGuardianName());
			ownerDtl.setAssoMobileno(entity.getMobileno());
			ownerDtl.seteMail(entity.geteMail());
			ownerDtl.setAssoAddharno(entity.getAddharno());
			ownerDtl.setAssoPanno(entity.getPanno());
			ownerDtl.setAssNo(entity.getAssNo());
			ownerDtl.setAssoActive(MainetConstants.STATUS.ACTIVE);
			ownerDtl.setPropertyShare(entity.getPropertyShare());
			ownerDtl.setAssoType(MainetConstants.Property.OWNER);// Owner Type
			ownerDtl.setSmServiceId(serviceId);
			ownerDtl.setCreatedBy(emp.getUserId());
			ownerDtl.setLgIpMac(emp.getLgIpMac());
			ownerDtl.setCreatedDate(new Date());
			ownerDtl.setOrgId(provAssDto.getOrgId());
			ownerDtlEntityList.add(ownerDtl);
			if (StringUtils.isBlank(newOwnerName.toString())) {
				newOwnerName.append(entity.getOwnerName() + MainetConstants.WHITE_SPACE);
			} else {
				newOwnerName.append(
						MainetConstants.operator.AMPERSENT + MainetConstants.WHITE_SPACE + entity.getOwnerName());
			}
			if (StringUtils.isBlank(newOwnerNameReg.toString())) {
				newOwnerNameReg.append(entity.getAssoOwnerNameReg() + MainetConstants.WHITE_SPACE);
			} else {
				newOwnerNameReg.append(MainetConstants.operator.AMPERSENT + MainetConstants.WHITE_SPACE
						+ entity.getAssoOwnerNameReg() + MainetConstants.WHITE_SPACE);
			}
		}

		// To maintain history
		maintainHistory(assesmentMastEntity, workFlowActionDto, oldOwnerDtlEntity, emp);

		LookUp ownerLookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerCode,
				MainetConstants.Property.propPref.OWT, org);

		// Owner type will not be updated in case of multiple flats
		if (StringUtils.isNotBlank(provAssDto.getFlatNo()) && (!StringUtils.equals(multipleFlats, MainetConstants.FlagY))) {
			assesmentMstRepository.updateAssesmentDetailsWithFlat(org.getOrgid(), provAssDto.getAssNo(),
					ownerLookup.getLookUpId(), emp.getEmpId(), emp.getEmppiservername(), provAssDto.getFlatNo());
		} else if (StringUtils.isBlank(provAssDto.getFlatNo())) {
			assesmentMstRepository.updateAssesmentDetails(org.getOrgid(), provAssDto.getAssNo(),
					ownerLookup.getLookUpId(), emp.getEmpId(), emp.getEmppiservername());
		}

		// Update assessment detail table for owner name
		List<Long> detailIds = assesmentMastEntity.getAssesmentDetailEntityList().stream()
				.map(detail -> detail.getProAssdId()).collect(Collectors.toList());
		assesmentMstRepository.updateAssesmentDetailtableOwners(assesmentMastEntity.getOrgId(), newOwnerName.toString(),
				newOwnerNameReg.toString(), tranferDto.getPropTransferOwnerList().get(0).getMobileno(),
				tranferDto.getPropTransferOwnerList().get(0).geteMail(), emp.getEmpId(), detailIds,
				emp.getEmppiservername());

			String billMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(assesmentMastEntity.getBillMethod(), org)
					.getLookUpCode();
			// In case of individual property owner at property level will get updated only
			// if there is single flat present against that property no
			if ((MainetConstants.FlagI.equals(billMethod) && (!StringUtils.equals(multipleFlats, MainetConstants.FlagY)))
					|| !MainetConstants.FlagI.equals(billMethod)) {
				ownerRepository.save(ownerDtlEntityList);
				ownerRepository.delete(oldOwnerDtlEntity);
			}		
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " updateMutationDetails() method");
	}
	
	private void maintainHistory(AssesmentMastEntity assesmentMastEntity, WorkflowTaskAction workFlowActionDto,
			List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity, Employee emp) {
		AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
		mastHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
		assesmentMastEntity.setCreatedBy(emp.getUserId());
		assesmentMastEntity.setCreatedDate(new Date());
		assesmentMastEntity.setLgIpMac(emp.getLgIpMac());
		assesmentMastEntity.setApmApplicationId(workFlowActionDto.getApplicationId());
		auditService.createHistory(assesmentMastEntity, mastHistEntity);

		for (AssesmentDetailEntity detailEntity : assesmentMastEntity.getAssesmentDetailEntityList()) {
			AssesmentDetailHistEntity detailHisEntity = new AssesmentDetailHistEntity();
			detailHisEntity.setMnAssId(assesmentMastEntity.getProAssId());
			detailHisEntity.setProAssdId(detailEntity.getProAssdId());
			detailHisEntity.setApmApplicationId(workFlowActionDto.getApplicationId());
			detailEntity.setCreatedDate(new Date());
			detailEntity.setCreatedBy(emp.getUserId());
			detailEntity.setLgIpMac(emp.getLgIpMac());
			auditService.createHistory(detailEntity, detailHisEntity);
		}
		for (AssesmentOwnerDtlEntity oldOwnerEntity : oldOwnerDtlEntity) {
			AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
			ownerDtlHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
			ownerDtlHistEntity.setProAssoId(oldOwnerEntity.getProAssoId());
			oldOwnerEntity.setCreatedDate(new Date());
			oldOwnerEntity.setCreatedBy(emp.getUserId());
			oldOwnerEntity.setApmApplicationId(workFlowActionDto.getApplicationId());
			oldOwnerEntity.setLgIpMac(emp.getLgIpMac());
			auditService.createHistory(oldOwnerEntity, ownerDtlHistEntity);
		}
	}
	
	@Override
	public boolean generateCertificate(String docPath, Long applicationNo, String certificateNo, Long orgId) {
		PropertyTransferMasterDto tranferDto = propertyTransferService.getPropTransferMstByAppId(orgId, applicationNo);
		ProvisionalAssesmentMstDto proAssMast = assesmentMastService.fetchAssessmentMasterByPropNoWithKey(orgId,
				tranferDto.getProAssNo());
		PublicNoticeDto publicNoticeDto = new PublicNoticeDto();
		Organisation org = UserSession.getCurrent().getOrganisation();
		publicNoticeDto.setPropNo(proAssMast.getAssNo());
        publicNoticeDto.setHouseNo(proAssMast.getTppPlotNo());
        publicNoticeDto.setOldProNo(proAssMast.getAssOldpropno());
        publicNoticeDto.setApplicateName(tranferDto.getPropTransferOwnerList().get(0).getOwnerName());
		LookUp mutationType = CommonMasterUtility.getNonHierarchicalLookUpObject(
				tranferDto.getTransferType(), UserSession.getCurrent().getOrganisation());
        if (mutationType != null) {
        	tranferDto.setTransferTypeDesc(mutationType.getLookUpDesc());
        }
        publicNoticeDto.setTransferType(tranferDto.getTransferTypeDesc());
		SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String appDate = sm.format(tranferDto.getCreatedDate());
		 publicNoticeDto.setAppDate(appDate);
		 publicNoticeDto.setCertificateNo(certificateNo);
		 
		final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, org);
		if (proAssMast.getAssWard1() != null) {
			publicNoticeDto.setWard1L(lookupList.get(0).getLookUpDesc());												
			publicNoticeDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard1(), org).getDescLangFirst());
		}
		if (proAssMast.getAssWard2() != null) {
            publicNoticeDto.setWard2L(lookupList.get(1).getLookUpDesc());
            publicNoticeDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard2(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard3() != null) {
            publicNoticeDto.setWard3L(lookupList.get(2).getLookUpDesc());
            publicNoticeDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard3(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard4() != null) {
            publicNoticeDto.setWard4L(lookupList.get(3).getLookUpDesc());
            publicNoticeDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard4(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard5() != null) {
            publicNoticeDto.setWard5L(lookupList.get(4).getLookUpDesc());
            publicNoticeDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard5(), org)
                    .getDescLangFirst());
        }
        List<AssesmentOwnerDtlHistEntity> ownerHistEntityList = assesmentMstRepository
                .fetchAssOwnerHistory(orgId, proAssMast.getProAssId());
		StringBuilder oldOwnerName = new StringBuilder();
		if (CollectionUtils.isNotEmpty(ownerHistEntityList)) {
			for (int j = 0; j < ownerHistEntityList.size(); j++) {
				if (StringUtils.isNotBlank(ownerHistEntityList.get(j).getAssoGuardianName())) {
					oldOwnerName.append(ownerHistEntityList.get(j).getAssoOwnerName()+ MainetConstants.WHITE_SPACE
							+ ownerHistEntityList.get(j).getAssoGuardianName()+ MainetConstants.operator.AMPERSENT);
				} else {
					oldOwnerName.append(ownerHistEntityList.get(j).getAssoOwnerName()+ MainetConstants.WHITE_SPACE + MainetConstants.operator.AMPERSENT);
				}
				break;
			}
			publicNoticeDto.setOldOwnerFullName(oldOwnerName.deleteCharAt((oldOwnerName.length() - 1)).toString());
			
		}
		
		List<String> ownerNames = new ArrayList<>();
		StringBuilder newOwnerFullName = new StringBuilder();
		for (int i = 0; i < tranferDto.getPropTransferOwnerList().size(); i++) {
			String ownerName = tranferDto.getPropTransferOwnerList().get(i).getOwnerName();
			String guardianName = tranferDto.getPropTransferOwnerList().get(i).getGuardianName();
			ownerNames.add(ownerName);
			if (StringUtils.isNotBlank(guardianName)) {
				newOwnerFullName.append(MainetConstants.WHITE_SPACE + ownerName + MainetConstants.WHITE_SPACE+ guardianName + MainetConstants.operator.AMPERSENT);
			} else {
				newOwnerFullName.append(MainetConstants.WHITE_SPACE + ownerName + MainetConstants.operator.AMPERSENT);
			}
		}
		publicNoticeDto.setNewOwnerFullName(newOwnerFullName.deleteCharAt((newOwnerFullName.length() - 1)).toString());
		publicNoticeDto.setApplicationNo(applicationNo.toString());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) 
			return generatePdfCertificateForSKDCL(docPath,publicNoticeDto,orgId);
		else
		return generatePdfCertificate(docPath,publicNoticeDto,orgId);
	}
	
	
	   public boolean generatePdfCertificateForSKDCL(String docPath, PublicNoticeDto publicNoticeDto, Long orgId) {
		    final String MANGAL =  ApplicationSession.getInstance().getMessage("mangalFont.path");
		    final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");
		
		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);
			
			// Adding table
			/*
			 * Table table = new Table(3); table.setWidth(new UnitValue(UnitValue.PERCENT,
			 * 100)); Border b1 = new DoubleBorder(4); table.setBorder(b1);
			 */
	    	
	    	Table table = new Table(new float[] {166,166,168}) // in points
			        .setWidth(500); //100 pt
			 
			//table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(2);
			table.setBorder(b1);
			SolidLine line = new SolidLine(1f);
			line.setColor(ColorConstants.BLACK);
			LineSeparator ls = new LineSeparator(line);
			ls.setBold();
			ls.setWidth(new UnitValue(UnitValue.PERCENT, 98));
			ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
	    	
			Cell r1Cell1 = new Cell();
			ImageData leftImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(85f);
			logoImg.setWidth(60f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
	    	r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
	    	r1Cell1.setBorder(Border.NO_BORDER);
	    	table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell();
		     // PdfFont myHindiFont1 = PdfFontFactory.createRegisteredFont(ApplicationSession.getInstance().getMessage("arialUniCode.path"), PdfEncodings.IDENTITY_H, true);
		      
			ImageData orgNameData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.orgName"),MANGAL,15f,false));
			Image orgNameImg = new Image(orgNameData);
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.orgName.reg")).setFont(font1));
			//r1Cell2.setFont(myHindiFont1);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);
			
			Cell r1Cell3 = new Cell();
			r1Cell3.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell3.setPadding(PADDING));
			
		/*	ImageData orgNameEng = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.orgName.english"), ARIALUNICODE, 13f, false));
			Image orgNameEngImg = new Image(orgNameEng);
			r1Cell2.add(orgNameEngImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.orgName.english")).setFont(font1));
			
			 * Paragraph orgNameEng = new
			 * Paragraph(ApplicationSession.getInstance().getMessage(
			 * "property.orgName.english"));
			 * orgNameEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
			 * orgNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * orgNameEng.setTextAlignment(TextAlignment.CENTER);
			 * orgNameEng.setFontSize(13f); //orgNameEng.setMarginLeft(40f);
			 * r1Cell2.add(orgNameEng);
			 
			
			ImageData headingData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.nameParivartan"),MANGAL, 13f, false));
			Image headingImg = new Image(headingData);
			r1Cell2.add(headingImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.mutation.nameParivartan")).setFont(font1));
			
			ImageData subHeadingData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.certificate"), MANGAL, 13f, false));
			Image subHeadingImg = new Image(subHeadingData);
			r1Cell2.add(subHeadingImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.mutation.certificate")).setFont(font1));
			
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			ImageData rightImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("rightlogo"));
			Image logoImgRight = new Image(rightImgData);
			logoImgRight.setWidth(70f);
			logoImgRight.setHeight(71f);
			r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	    	r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
	    	r1Cell3.setBorder(Border.NO_BORDER);
	    	table.addCell(r1Cell3.setPadding(PADDING));*/

			
			
			Cell r1Cell3c = new Cell(1,3);
			r1Cell3c.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell3c.setBorder(Border.NO_BORDER);
			r1Cell3c.add(ls);
			table.addCell(r1Cell3c.setPadding(PADDING));
			
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			
			Cell r2Cell1 = new Cell();
			r2Cell1.setBorder(Border.NO_BORDER);
			table.addCell(r2Cell1.setPadding(PADDING));

			Cell r2Cell2 = new Cell(1, 2);
			r2Cell2.setBorder(Border.NO_BORDER);
			ImageData note1 = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.note1")) + publicNoticeDto.getWard1V().trim() + "/" + publicNoticeDto.getWard2V().trim() +" "+ (ApplicationSession.getInstance().getMessage("prop.certificate.note2")) ,ARIALUNICODE, TEXT_SIZE_10, false));
			Image orgnoteImg = new Image(note1);
			orgnoteImg.setMarginRight(20f);
			orgnoteImg.setMarginLeft(10f);
			r2Cell2.add(orgnoteImg.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData note2 = ImageDataFactory.create(Utility.textToImage(((ApplicationSession.getInstance().getMessage("prop.certificate.ward")) + " '"+publicNoticeDto.getWard1V().trim() + "' "+(ApplicationSession.getInstance().getMessage("prop.certificate.not3"))),ARIALUNICODE, TEXT_SIZE_10, false));
			Image orgnote2Img = new Image(note2);
			orgnote2Img.setMarginRight(50f);
			orgnote2Img.setMarginLeft(10f);
			r2Cell2.add(orgnote2Img.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData note3 = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.date") + new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date())),MANGAL, TEXT_SIZE_10, false));
			Image orgnote3Img = new Image(note3);
			orgnote3Img.setMarginRight(50f);
			orgnote3Img.setMarginLeft(10f);
			r2Cell2.add(orgnote3Img.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			table.addCell(r2Cell2);
			
			
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			
			
			
			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow1CellData = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.ref")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow1CellImg = new Image(keyValRow1CellData);
			keyValRow1CellImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow1CellImg.setTextAlignment(TextAlignment.LEFT);
			keyValRow1CellImg.setMarginLeft(30f);
			keyValRow1Cell1.add(keyValRow1CellImg);
			table.addCell(keyValRow1Cell1);
			
			
			
			Cell  keyValRow1Cell2 = new Cell(1,2);
			keyValRow1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow1Cell1Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.tokenNo")+"    "+publicNoticeDto.getApplicationNo().trim()) ,ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow1Cell1Img = new Image(keyValRow1Cell1Data);
			keyValRow1Cell2.add(keyValRow1Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT));

			ImageData keyValRow1Cell2Data = ImageDataFactory.create(Utility.textToImage(((ApplicationSession.getInstance().getMessage("prop.certificate.entryNo")) +publicNoticeDto.getAppDate().trim()),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow1Cell2Img = new Image(keyValRow1Cell2Data);
			keyValRow1Cell2.add(keyValRow1Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT));

			ImageData keyValRow1Cell3Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.note5")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow1Cell3Img = new Image(keyValRow1Cell3Data);
			keyValRow1Cell2.add(keyValRow1Cell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT));
			table.addCell(keyValRow1Cell2);
		
			
           table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
           table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
			Cell keyValRow1Cell3 = new Cell(1, 3);
			keyValRow1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell3.setBorder(Border.NO_BORDER);
			ImageData para2tableCell3Data = ImageDataFactory.create(
					Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.order")),
							MANGAL,12f,false));
			Image para2tableCell3Img = new Image(para2tableCell3Data);
			keyValRow1Cell3.add(para2tableCell3Img.setHorizontalAlignment(HorizontalAlignment.CENTER));
			para2tableCell3Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para2tableCell3Img.setTextAlignment(TextAlignment.CENTER);
			para2tableCell3Img.setMarginLeft(0.5f);
			table.addCell(keyValRow1Cell3);
			
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

		
			
			Cell keyValRow2Cell1 = new Cell(1,3);
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell1Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para1.1")+"  "+ publicNoticeDto.getPropNo().trim()+ " , " + publicNoticeDto.getOldOwnerFullName().trim() +"  "+ ApplicationSession.getInstance().getMessage("prop.certificate.para1.2")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow2Cell1Img = new Image(keyValRow2Cell1Data);
			keyValRow2Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell1Img.setMarginLeft(5f);
			keyValRow2Cell1.add(keyValRow2Cell1Img);
			table.addCell(keyValRow2Cell1);
			
			Cell keyValRow2Cell2 = new Cell(1,3);
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell2Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para1.3")+" "+ publicNoticeDto.getNewOwnerFullName().trim()+" "+ApplicationSession.getInstance().getMessage("prop.certificate.para1.4") ),ARIALUNICODE, 10f, false));
			Image keyValRow2Cell2Img = new Image(keyValRow2Cell2Data);
			keyValRow2Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell2.add(keyValRow2Cell2Img);
			table.addCell(keyValRow2Cell2);

	
			Cell keyValRow3Cell1 = new Cell(1,3);
			keyValRow3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow3Cell1Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para1.5") +" "+publicNoticeDto.getNewOwnerFullName().trim()+" "+ApplicationSession.getInstance().getMessage("prop.certificate.para1.6")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow3Cell1Img = new Image(keyValRow3Cell1Data);
			keyValRow3Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell1Img.setTextAlignment(TextAlignment.RIGHT);
			keyValRow3Cell1.add(keyValRow3Cell1Img);
			table.addCell(keyValRow3Cell1);
			
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
			Cell keyValRow3Cell2 = new Cell(1, 3);
			keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow3Cell2Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.para2"),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow3Cell2Img = new Image(keyValRow3Cell2Data);
			keyValRow3Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow3Cell2Img.setMarginLeft(5f);
			keyValRow3Cell2.add(keyValRow3Cell2Img);
			table.addCell(keyValRow3Cell2);

			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
	
			Cell keyValRow4Cell1 = new Cell(1,3);
			keyValRow4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow4Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.para3.1"),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow4Cell1Img = new Image(keyValRow4Cell1Data);
			keyValRow4Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow4Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow4Cell1Img.setMarginLeft(5f);
			keyValRow4Cell1.add(keyValRow4Cell1Img);
			table.addCell(keyValRow4Cell1);
			
			Cell keyValRow4Cell2 = new Cell(1, 3);
			keyValRow4Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow4Cell2Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para3.2")+" "+publicNoticeDto.getNewOwnerFullName().trim()+" "+ApplicationSession.getInstance().getMessage("prop.certificate.para3.3")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow4Cell2Img = new Image(keyValRow4Cell2Data);
			keyValRow4Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow4Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow4Cell2.add(keyValRow4Cell2Img);
			table.addCell(keyValRow4Cell2);
			
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

			Cell keyValRow5Cell1 = new Cell(1,3);
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			String empname = UserSession.getCurrent().getEmployee().getEmpname() == null ? MainetConstants.BLANK
                    : UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE +" "+ UserSession.getCurrent().getEmployee().getEmpmname() == null ? MainetConstants.BLANK
                            : UserSession.getCurrent().getEmployee().getEmpmname() + MainetConstants.WHITE_SPACE +" "+UserSession.getCurrent().getEmployee().getEmplname()== null ? MainetConstants.BLANK
                                    : UserSession.getCurrent().getEmployee().getEmplname() + MainetConstants.WHITE_SPACE;
			ImageData keyValRow5Cell1Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para3.4")+" "+ empname +" "+ApplicationSession.getInstance().getMessage("prop.certificate.para3.5")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow5Cell1Img = new Image(keyValRow5Cell1Data);
			keyValRow5Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell1Img.setMarginLeft(5f);
			keyValRow5Cell1.add(keyValRow5Cell1Img);
			table.addCell(keyValRow5Cell1);
			
			Cell keyValRow5Cell2 = new Cell(1, 3);
			keyValRow5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow5Cell2Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para3.6")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow5Cell2Img = new Image(keyValRow5Cell2Data);
			keyValRow5Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell2.add(keyValRow5Cell2Img);
			table.addCell(keyValRow5Cell2);
		
			Cell keyValRow6Cell1 = new Cell(1,3);
			keyValRow6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow6Cell1Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.para3.7") +" "+publicNoticeDto.getPropNo().trim() +" "+ApplicationSession.getInstance().getMessage("prop.certificate.para3.8")+" "+publicNoticeDto.getOldOwnerFullName().trim()+" "+ApplicationSession.getInstance().getMessage("prop.certificate.para3.9")+" "+publicNoticeDto.getNewOwnerFullName().trim()+" "+ApplicationSession.getInstance().getMessage("prop.certificate.para3.10")),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow6Cell1Img = new Image(keyValRow6Cell1Data);
			keyValRow6Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow6Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow6Cell1.add(keyValRow6Cell1Img);
			table.addCell(keyValRow6Cell1);
			
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
			Cell keyValRow6Cell2 = new Cell(1, 3);
			keyValRow6Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow6Cell2Data = ImageDataFactory.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("prop.certificate.Date")+ new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date())),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow6Cell2Img = new Image(keyValRow6Cell2Data);
			keyValRow6Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow6Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow6Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow6Cell2.add(keyValRow6Cell2Img);
			table.addCell(keyValRow6Cell2);

			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 
			 
			Cell keyValRow7Cell1 = new Cell(1,3);
			keyValRow7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow7Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.copy"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow7Cell1Img = new Image(keyValRow7Cell1Data);
			keyValRow7Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow7Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow7Cell1Img.setMarginLeft(5f);
			keyValRow7Cell1.add(keyValRow7Cell1Img);
			table.addCell(keyValRow7Cell1);
			
			 table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			 
			 
			Cell keyValRow7Cell2 = new Cell();
			keyValRow7Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow7Cell2Data = ImageDataFactory.create(Utility.textToImage("1) "+publicNoticeDto.getOldOwnerFullName().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow7Cell2Img = new Image(keyValRow7Cell2Data);
			keyValRow7Cell2Img.setMarginLeft(10f);
			keyValRow7Cell2.add(keyValRow7Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT));
			
			ImageData keyValRow7Cell3Data = ImageDataFactory.create(Utility.textToImage("1) "+publicNoticeDto.getNewOwnerFullName().trim() ,ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow7Cell3Img = new Image(keyValRow7Cell3Data);
			keyValRow7Cell3Img.setMarginLeft(10f);
			keyValRow7Cell2.add(keyValRow7Cell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT));

			ImageData keyValRow7Cell4Data = ImageDataFactory.create(Utility.textToImage(("3) "+(ApplicationSession.getInstance().getMessage("prop.certificate.wardAreaOfficer"))+" '"+ publicNoticeDto.getWard1V().trim() +" '"),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow7Cell4Img = new Image(keyValRow7Cell4Data);
			keyValRow7Cell4Img.setMarginLeft(10f);
			keyValRow7Cell2.add(keyValRow7Cell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT));
			table.addCell(keyValRow7Cell2);
			
			
			
	/*		
			Cell keyValRow8Cell1 = new Cell();
			keyValRow8Cell1.setBorder(Border.NO_BORDER);
			table.addCell(keyValRow8Cell1.setPadding(PADDING));*/
			
		
			
			Cell keyValRow8Cell2= new Cell(1, 2);
			keyValRow8Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow8Cell2Data1 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.taxAssessorAndCollector"), MANGAL,10f, false));
			Image keyValRow8Cell2Img1= new Image(keyValRow8Cell2Data1);
			keyValRow8Cell2Img1.setMarginRight(30f);
			keyValRow8Cell2.add(keyValRow8Cell2Img1.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData keyValRow8Cell2Data2 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.organisationName"), MANGAL, 10f, false));
			Image keyValRow8Cell2Img2 = new Image(keyValRow8Cell2Data2);
			keyValRow8Cell2Img2.setMarginRight(10f);
			keyValRow8Cell2.add(keyValRow8Cell2Img2.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData keyValRow8Cell2Data3 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.location"), MANGAL, 10f,false));
			Image keyValRow8Cell2Img3= new Image(keyValRow8Cell2Data3);
			keyValRow8Cell2Img3.setMarginRight(60f);
			keyValRow8Cell2.add(keyValRow8Cell2Img3.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			table.addCell(keyValRow8Cell2);

			
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));


		


			/*Cell r12Cell1 = new Cell(1, 3);
			r12Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r12Cell1.setBorder(Border.NO_BORDER);
			ImageData content12Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("prop.certificate.lastNote"), ARIALUNICODE,TEXT_SIZE_10,false));
			Image content12Img = new Image(content12Data);
			content12Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			content12Img.setTextAlignment(TextAlignment.LEFT);
			r12Cell1.add(content12Img);
			table.addCell(r12Cell1.setPadding(PADDING));*/
			
			document.add(table);
			document.close();
			writer.close();
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
			return false;
		}
	}
	
	   public boolean generatePdfCertificate(String docPath, PublicNoticeDto publicNoticeDto, Long orgId) {
		    final String MANGAL =  ApplicationSession.getInstance().getMessage("mangalFont.path");
		    final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");
		
		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);
			
			// Adding table
			/*
			 * Table table = new Table(3); table.setWidth(new UnitValue(UnitValue.PERCENT,
			 * 100)); Border b1 = new DoubleBorder(4); table.setBorder(b1);
			 */
	    	
	    	Table table = new Table(new float[] {166,166,168}) // in points
			        .setWidth(500); //100 pt
			 
			//table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(4);
			table.setBorder(b1);
	    	
			Cell r1Cell1 = new Cell();
			ImageData leftImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(95f);
			logoImg.setWidth(70f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
	    	r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
	    	r1Cell1.setBorder(Border.NO_BORDER);
	    	table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell();
		     // PdfFont myHindiFont1 = PdfFontFactory.createRegisteredFont(ApplicationSession.getInstance().getMessage("arialUniCode.path"), PdfEncodings.IDENTITY_H, true);
		      
			ImageData orgNameData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.orgName.reg"),MANGAL,15f,false));
			Image orgNameImg = new Image(orgNameData);
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.orgName.reg")).setFont(font1));
			//r1Cell2.setFont(myHindiFont1);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			
			ImageData orgNameEng = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.orgName.english"), ARIALUNICODE, 13f, false));
			Image orgNameEngImg = new Image(orgNameEng);
			r1Cell2.add(orgNameEngImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.orgName.english")).setFont(font1));
			/*
			 * Paragraph orgNameEng = new
			 * Paragraph(ApplicationSession.getInstance().getMessage(
			 * "property.orgName.english"));
			 * orgNameEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
			 * orgNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * orgNameEng.setTextAlignment(TextAlignment.CENTER);
			 * orgNameEng.setFontSize(13f); //orgNameEng.setMarginLeft(40f);
			 * r1Cell2.add(orgNameEng);
			 */
			
			ImageData headingData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.nameParivartan"),MANGAL, 13f, false));
			Image headingImg = new Image(headingData);
			r1Cell2.add(headingImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.mutation.nameParivartan")).setFont(font1));
			
			ImageData subHeadingData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.certificate"), MANGAL, 13f, false));
			Image subHeadingImg = new Image(subHeadingData);
			r1Cell2.add(subHeadingImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			
			//r1Cell2.add(new Paragraph(ApplicationSession.getInstance().getMessage("property.mutation.certificate")).setFont(font1));
			
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			ImageData rightImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("rightlogo"));
			Image logoImgRight = new Image(rightImgData);
			logoImgRight.setWidth(70f);
			logoImgRight.setHeight(71f);
			r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	    	r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
	    	r1Cell3.setBorder(Border.NO_BORDER);
	    	table.addCell(r1Cell3.setPadding(PADDING));

			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER)); 
	    	table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 
			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow1Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate1"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow1Cell1Img = new Image(keyValRow1Cell1Data);
			keyValRow1Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow1Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow1Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow1Cell1.add(keyValRow1Cell1Img);
			table.addCell(keyValRow1Cell1);
			
			Cell keyValRow1Cell2 = new Cell(1, 2);
			keyValRow1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow1Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getApplicationNo().trim(),ARIALUNICODE, 12f, false));
			Image keyValRow1Cell2Img = new Image(keyValRow1Cell2Data);
			keyValRow1Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow1Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow1Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow1Cell2.add(keyValRow1Cell2Img);
			table.addCell(keyValRow1Cell2);

			/*
			 * Cell keyValRow1Cell2 = new Cell(1, 2);
			 * keyValRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow1Cell2.setBorder(Border.NO_BORDER); keyValRow1Cell2.add(new
			 * Paragraph(": "+publicNoticeDto.getCertificateNo().trim()).setFontSize(
			 * TEXT_SIZE_8)); table.addCell(keyValRow1Cell2);
			 */
			
			Cell keyValRow2Cell1 = new Cell();
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate2"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow2Cell1Img = new Image(keyValRow2Cell1Data);
			keyValRow2Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow2Cell1.add(keyValRow2Cell1Img);
			table.addCell(keyValRow2Cell1);
			
			Cell keyValRow2Cell2 = new Cell(1, 2);
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getApplicationNo().trim(),ARIALUNICODE, 12f, false));
			Image keyValRow2Cell2Img = new Image(keyValRow2Cell2Data);
			keyValRow2Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow2Cell2.add(keyValRow2Cell2Img);
			table.addCell(keyValRow2Cell2);

			/*
			 * Cell keyValRow2Cell2 = new Cell(1, 2);
			 * keyValRow2Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow2Cell2.setBorder(Border.NO_BORDER); keyValRow2Cell2.add(new
			 * Paragraph(": "+publicNoticeDto.getApplicationNo().trim()).setFontSize(
			 * TEXT_SIZE_8)); table.addCell(keyValRow2Cell2);
			 */
			
			Cell keyValRow3Cell1 = new Cell();
			keyValRow3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow3Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate3"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow3Cell1Img = new Image(keyValRow3Cell1Data);
			keyValRow3Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow3Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow3Cell1.add(keyValRow3Cell1Img);
			table.addCell(keyValRow3Cell1);
			
			Cell keyValRow3Cell2 = new Cell(1, 2);
			keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow3Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getPropNo().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow3Cell2Img = new Image(keyValRow3Cell2Data);
			keyValRow3Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow3Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow3Cell2.add(keyValRow3Cell2Img);
			table.addCell(keyValRow3Cell2);

			/*
			 * Cell keyValRow3Cell2 = new Cell(1, 2);
			 * keyValRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow3Cell2.setBorder(Border.NO_BORDER); keyValRow3Cell2.add(new
			 * Paragraph(": "+publicNoticeDto.getPropNo().trim()).setFontSize(TEXT_SIZE_8));
			 * table.addCell(keyValRow3Cell2);
			 */

			Cell keyValRow4Cell1 = new Cell();
			keyValRow4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow4Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate4"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow4Cell1Img = new Image(keyValRow4Cell1Data);
			keyValRow4Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow4Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow4Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow4Cell1.add(keyValRow4Cell1Img);
			table.addCell(keyValRow4Cell1);
			
			Cell keyValRow4Cell2 = new Cell(1, 2);
			keyValRow4Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow4Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getOldProNo().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow4Cell2Img = new Image(keyValRow4Cell2Data);
			keyValRow4Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow4Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow4Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow4Cell2.add(keyValRow4Cell2Img);
			table.addCell(keyValRow4Cell2);

			/*
			 * Cell keyValRow4Cell2 = new Cell(1, 2);
			 * keyValRow4Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow4Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow4Cell2.setBorder(Border.NO_BORDER); keyValRow4Cell2.add(new
			 * Paragraph(": "+publicNoticeDto.getOldProNo().trim()).setFontSize(TEXT_SIZE_8)
			 * ); table.addCell(keyValRow4Cell2);
			 */
			
			Cell keyValRow5Cell1 = new Cell();
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow5Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate5"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow5Cell1Img = new Image(keyValRow5Cell1Data);
			keyValRow5Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow5Cell1.add(keyValRow5Cell1Img);
			table.addCell(keyValRow5Cell1);
			
			Cell keyValRow5Cell2 = new Cell(1, 2);
			keyValRow5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow5Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getWard1V().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow5Cell2Img = new Image(keyValRow5Cell2Data);
			keyValRow5Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow5Cell2.add(keyValRow5Cell2Img);
			table.addCell(keyValRow5Cell2);

			/*
			 * Cell keyValRow5Cell2 = new Cell(1, 2);
			 * keyValRow5Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow5Cell2.setBorder(Border.NO_BORDER); keyValRow5Cell2.add(new
			 * Paragraph(": "+publicNoticeDto.getWard1V().trim()).setFontSize(TEXT_SIZE_8));
			 * table.addCell(keyValRow5Cell2);
			 */
		
			Cell keyValRow6Cell1 = new Cell();
			keyValRow6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow6Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate6"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow6Cell1Img = new Image(keyValRow6Cell1Data);
			keyValRow6Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow6Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow6Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow6Cell1.add(keyValRow6Cell1Img);
			table.addCell(keyValRow6Cell1);
			
			
			Cell keyValRow6Cell2 = new Cell(1, 2);
			keyValRow6Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow6Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getWard2V().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow6Cell2Img = new Image(keyValRow6Cell2Data);
			keyValRow6Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow6Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow6Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow6Cell2.add(keyValRow6Cell2Img);
			table.addCell(keyValRow6Cell2);

			/*
			 * Cell keyValRow6Cell2 = new Cell(1, 2);
			 * keyValRow6Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * keyValRow6Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * keyValRow6Cell2.setBorder(Border.NO_BORDER); keyValRow6Cell2.add(new
			 * Paragraph("  : "+publicNoticeDto.getWard2V().trim()).setFontSize(TEXT_SIZE_8)
			 * ); table.addCell(keyValRow6Cell2);
			 */
			
			Cell keyValRow7Cell1 = new Cell();
			keyValRow7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow7Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate7"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow7Cell1Img = new Image(keyValRow7Cell1Data);
			keyValRow7Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow7Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow7Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow7Cell1.add(keyValRow7Cell1Img);
			table.addCell(keyValRow7Cell1);

			Cell keyValRow7Cell2 = new Cell(1, 2);
			keyValRow7Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow7Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getHouseNo().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow7Cell2Img = new Image(keyValRow7Cell2Data);
			keyValRow7Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow7Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow7Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow7Cell2.add(keyValRow7Cell2Img);
			table.addCell(keyValRow7Cell2);
			
			Cell keyValRow8Cell1 = new Cell();
			keyValRow8Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow8Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate8"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow8Cell1Img = new Image(keyValRow8Cell1Data);
			keyValRow8Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow8Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow8Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow8Cell1.add(keyValRow8Cell1Img);
			table.addCell(keyValRow8Cell1);

			Cell keyValRow8Cell2 = new Cell(1, 2);
			keyValRow8Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell2.setBorder(Border.NO_BORDER);
			if (publicNoticeDto.getWard3V() != null) {
			ImageData keyValRow8Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getWard3V().trim(),ARIALUNICODE, 12f, false));
			Image keyValRow8Cell2Img = new Image(keyValRow8Cell2Data);
			keyValRow8Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow8Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow8Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow8Cell2.add(keyValRow8Cell2Img);
			}
			table.addCell(keyValRow8Cell2);
			
			Cell keyValRow9Cell1 = new Cell();
			keyValRow9Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow9Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate9"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow9Cell1Img = new Image(keyValRow9Cell1Data);
			keyValRow9Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow9Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow9Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow9Cell1.add(keyValRow9Cell1Img);
			table.addCell(keyValRow9Cell1);

			Cell keyValRow9Cell2 = new Cell(1, 2);
			keyValRow9Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow9Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getTransferType().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow9Cell2Img = new Image(keyValRow9Cell2Data);
			keyValRow9Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow9Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow9Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow9Cell2.add(keyValRow9Cell2Img);
			table.addCell(keyValRow9Cell2);
			
			Cell keyValRow10Cell1 = new Cell();
			keyValRow10Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow10Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate10"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow10Cell1Img = new Image(keyValRow10Cell1Data);
			keyValRow10Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow10Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow10Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow10Cell1.add(keyValRow10Cell1Img);
			table.addCell(keyValRow10Cell1);

			Cell keyValRow10Cell2 = new Cell(1, 2);
			keyValRow10Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow10Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getOldOwnerFullName().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow10Cell2Img = new Image(keyValRow10Cell2Data);
			keyValRow10Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow10Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow10Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow10Cell2.add(keyValRow10Cell2Img);
			table.addCell(keyValRow10Cell2);
			
			Cell keyValRow11Cell1 = new Cell();
			keyValRow11Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow11Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate11"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow11Cell1Img = new Image(keyValRow11Cell1Data);
			keyValRow11Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow11Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow11Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow11Cell1.add(keyValRow11Cell1Img);
			table.addCell(keyValRow11Cell1);

			Cell keyValRow11Cell2 = new Cell(1, 2);
			keyValRow11Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow11Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getNewOwnerFullName().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow11Cell2Img = new Image(keyValRow11Cell2Data);
			keyValRow11Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow11Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow11Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow11Cell2.add(keyValRow11Cell2Img);
			table.addCell(keyValRow11Cell2);
			
			Cell keyValRow12Cell1 = new Cell();
			keyValRow12Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow12Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mut.certificate12"),MANGAL, TEXT_SIZE_10, false));
			Image keyValRow12Cell1Img = new Image(keyValRow12Cell1Data);
			keyValRow12Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow12Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow12Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow12Cell1.add(keyValRow12Cell1Img);
			table.addCell(keyValRow12Cell1);

			Cell keyValRow12Cell2 = new Cell(1, 2);
			keyValRow12Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow12Cell2Data = ImageDataFactory.create(Utility.textToImage(": "+publicNoticeDto.getAppDate().trim(),ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow12Cell2Img = new Image(keyValRow12Cell2Data);
			keyValRow12Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow12Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow12Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow12Cell2.add(keyValRow12Cell2Img);
			table.addCell(keyValRow12Cell2);
			  
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			  table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));

			Cell r9Cell1 = new Cell(1, 3);
			r9Cell1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r9Cell1.setBorder(Border.NO_BORDER);
			ImageData content9Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.certificate14"), MANGAL,TEXT_SIZE_10,false));
			Image content9Img = new Image(content9Data);
			content9Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			content9Img.setTextAlignment(TextAlignment.RIGHT);
			r9Cell1.add(content9Img);
			table.addCell(r9Cell1.setPaddingRight(PADDING));

			Cell r10Cell1 = new Cell(1, 3);
			r10Cell1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r10Cell1.setBorder(Border.NO_BORDER);
			ImageData content10Data = ImageDataFactory.create(Utility.textToImage(publicNoticeDto.getWard1V(), ARIALUNICODE,TEXT_SIZE_10,false));
			Image content10Img = new Image(content10Data);
			content10Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			content10Img.setTextAlignment(TextAlignment.RIGHT);
			r10Cell1.add(content10Img);
			table.addCell(r10Cell1.setPaddingRight(PADDING));

			Cell r11Cell1 = new Cell(1, 3);
			r11Cell1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r11Cell1.setBorder(Border.NO_BORDER);
			ImageData content11Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.certificate16"), MANGAL,TEXT_SIZE_10,false));
			Image content11Img = new Image(content11Data);
			content11Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			content11Img.setTextAlignment(TextAlignment.RIGHT);
			r11Cell1.add(content11Img);
			table.addCell(r11Cell1.setPaddingRight(PADDING));
			
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));
			 table.addCell(new Cell(1,3).setBorder(Border.NO_BORDER));

			Cell r12Cell1 = new Cell(1, 3);
			r12Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r12Cell1.setBorder(Border.NO_BORDER);
			ImageData content12Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("property.mutation.note"), MANGAL,TEXT_SIZE_10,false));
			Image content12Img = new Image(content12Data);
			content12Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			content12Img.setTextAlignment(TextAlignment.LEFT);
			r12Cell1.add(content12Img);
			table.addCell(r12Cell1.setPadding(PADDING));
			
			document.add(table);
			document.close();
			writer.close();
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
			return false;
		}
	}

	   private boolean isBeforeLastAuthoriser(Long taskId) throws Exception{
	    	 UserTaskDTO task = null;
	    	 boolean beforeLastAuthoriser = false;
	         if (taskId > 0) {
	             Long curentCheckerLevel = 1l;
	             Long currentCheckerGroup = 1l;
	                 task = iWorkflowTaskService.findByTaskId(taskId);
	             if (task != null) {
	                 if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {

	                     curentCheckerLevel = task.getCurentCheckerLevel();
	                     currentCheckerGroup = task.getCurrentCheckerGroup();

	                     TaskAssignmentRequest taskAssignmentRequest = new TaskAssignmentRequest();
	                     taskAssignmentRequest.setWorkflowTypeId(task.getWorkflowId());
	                     taskAssignmentRequest.setServiceEventName(MainetConstants.WorkFlow.EventNames.CHECKER);
	                     LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> checkerLevelGroups = ApplicationContextProvider
	                             .getApplicationContext().getBean(ITaskAssignmentService.class)
	                             .getEventLevelGroupsByWorkflowTypeAndEventName(taskAssignmentRequest);
	                     if (currentCheckerGroup == checkerLevelGroups.size()) {
	                         LinkedHashMap<String, TaskAssignment> lastCheckerLevelGroup = checkerLevelGroups.get(
	                                 MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + currentCheckerGroup);
	                         if (lastCheckerLevelGroup.size() - curentCheckerLevel == 1) {
	                        	 beforeLastAuthoriser = true;
	                         }
	                     }
	                 }
	             }
	         }
			return beforeLastAuthoriser;
	    }
	   
	   @Override
	    @Transactional
	    @WebMethod(exclude = true)
	    public void saveFinalAuthorization(Long orgId, Employee emp, PropertyTransferMasterDto propTranMstDto,
				String clientIpAddress, Long applicationNo) {
	        NoticeMasterDto noticeMasDto = null;
	        Organisation org = new Organisation();
	        org.setOrgid(orgId);
	        String noticePeriod = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC, org)
	                .getOtherField();
	                AssesmentMastEntity assesmentMastEntity = null;
	                List<AssesmentOwnerDtlEntity> ownerDtlEntityList = new ArrayList<>();
	                List<AssesmentMastEntity> assesmentMastEntityList = assesmentMstRepository.getAssessmentMstByPropNo(orgId,
	                        propTranMstDto.getProAssNo());
					if (CollectionUtils.isNotEmpty(assesmentMastEntityList)) {
						assesmentMastEntity = assesmentMastEntityList.get(assesmentMastEntityList.size() - 1);
					}
	                List<AssesmentOwnerDtlEntity> oldOwnerDtlEntity = ownerRepository
	                        .fetchOwnerDetailListByProAssId(assesmentMastEntity, propTranMstDto.getOrgId());

	                assesmentMstRepository.updateAssesmentDetails(orgId, propTranMstDto.getProAssNo(),
	                        propTranMstDto.getOwnerType(), emp.getEmpId(), clientIpAddress);
	                List<PropertyTransferOwnerDto> propTransferOwnerList = propTranMstDto.getPropTransferOwnerList();

	                for (PropertyTransferOwnerDto entity : propTransferOwnerList) {
	                    AssesmentOwnerDtlEntity ownerDtl = new AssesmentOwnerDtlEntity();
	                    if (assesmentMastEntity != null) {
	                        ownerDtl.setMnAssId(assesmentMastEntity);
	                    }
	                    // Defect #94099
	                    if (ownerDtl.getProAssoId() == 0) {
	                        final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
	                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
	                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
	                                MainetConstants.Property.primColumn.PRO_ASSO_ID, null, null);
	                        ownerDtl.setProAssoId(proAssoId);
	                    }

	                    ownerDtl.setAssoOType(entity.getOtype());
	                    ownerDtl.setAssoOwnerName(entity.getOwnerName());
	                    ownerDtl.setAssoOwnerNameReg(entity.getAssoOwnerNameReg());
	                    ownerDtl.setGenderId(entity.getGenderId());
	                    ownerDtl.setRelationId(entity.getRelationId());
	                    ownerDtl.setAssoGuardianName(entity.getGuardianName());
	                    ownerDtl.setAssoMobileno(entity.getMobileno());
	                    ownerDtl.seteMail(entity.geteMail());
	                    ownerDtl.setAssoAddharno(entity.getAddharno());
	                    ownerDtl.setAssoPanno(entity.getPanno());
	                    ownerDtl.setAssNo(entity.getAssNo());
	                    ownerDtl.setAssoActive(MainetConstants.STATUS.ACTIVE);
	                    ownerDtl.setPropertyShare(entity.getPropertyShare());
	                    ownerDtl.setAssoType(MainetConstants.Property.OWNER);// Owner Type
	                    ownerDtl.setSmServiceId(entity.getSmServiceId());
	                    ownerDtl.setCreatedBy(emp.getEmpId());
	                    ownerDtl.setLgIpMac(clientIpAddress);
	                    ownerDtl.setCreatedDate(new Date());
	                    ownerDtl.setOrgId(orgId);
	                    ownerDtl.setApmApplicationId(entity.getApmApplicationId());
	                    ownerDtlEntityList.add(ownerDtl);
	                }
	                ownerRepository.save(ownerDtlEntityList);

	                ownerRepository.delete(oldOwnerDtlEntity);

	                // To maintain history of old owners
	                AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
	                mastHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
	                assesmentMastEntity.setCreatedBy(emp.getEmpId());
	                assesmentMastEntity.setCreatedDate(assesmentMastEntity.getCreatedDate());
	                assesmentMastEntity.setLgIpMacUpd(clientIpAddress);
	                assesmentMastEntity.setApmApplicationId(applicationNo);
	                auditService.createHistory(assesmentMastEntity, mastHistEntity);

	                for (AssesmentOwnerDtlEntity oldOwnerEntity : oldOwnerDtlEntity) {
	                    AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
	                    ownerDtlHistEntity.setMnAssId(assesmentMastEntity.getProAssId());
	                    ownerDtlHistEntity.setProAssoId(oldOwnerEntity.getProAssoId());
	                    oldOwnerEntity.setCreatedDate(oldOwnerEntity.getCreatedDate());
	                    oldOwnerEntity.setCreatedBy(emp.getEmpId());
	                    oldOwnerEntity.setApmApplicationId(applicationNo);
	                    oldOwnerEntity.setLgIpMacUpd(clientIpAddress);
	                    auditService.createHistory(oldOwnerEntity, ownerDtlHistEntity);
	                }

	                PropertyTransferRepository.updateAuthorizeBy(applicationNo, orgId, emp.getEmpId());
	    }

	@Override
	@Transactional
	public void updateMutationApproval(Long orgId, Long empId, Long applicationNo) {
		PropertyTransferRepository.updateAuthorizeBy(applicationNo, orgId, empId);
		
	}
}
