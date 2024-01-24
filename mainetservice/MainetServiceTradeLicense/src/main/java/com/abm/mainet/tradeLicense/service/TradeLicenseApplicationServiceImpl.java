package com.abm.mainet.tradeLicense.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.repository.DishonurChargeEntryRepository;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dao.ITradeLicenseApplicationDao;
import com.abm.mainet.tradeLicense.datamodel.MLNewTradeLicense;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.RenewalHistroyDetails;
import com.abm.mainet.tradeLicense.dto.TradeDataEntyDto;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeItemDetailsHistoryRepository;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.repository.TradeMasterHistoryDetailsRepository;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
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
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Service(value = "TradeLicenseService")
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService")
@Api(value = "/tradeLicenseApplicationService")
@Path("/tradeLicenseApplicationService")
public class TradeLicenseApplicationServiceImpl implements ITradeLicenseApplicationService {

	private static final Logger LOGGER = Logger.getLogger(TradeLicenseApplicationServiceImpl.class);
	private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
	private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
	private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
	private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
	private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
	public static final float PADDING = 10f;
	public static final float TEXT_SIZE_10 = 11f;
	public static final float TEXT_SIZE_9 = 9f;
	public static final float TEXT_SIZE_8 = 8f;

	@Resource
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TradeLicenseApplicationRepository tradeLicenseApplicationRepository;

	@Autowired
	private ITradeLicenseApplicationDao iTradeLicenseApplicationDao;

	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private IRenewalLicenseApplicationService renewalLicenseApplicationService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	IOrganisationService iOrganisationService;

	@Autowired
	AuditService auditService;
	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;
	@Autowired
	private TradeItemDetailsHistoryRepository tradeDetHistRepository;

	@Autowired
	private TradeMasterHistoryDetailsRepository tradeDetailsHistRepo;

	@Autowired
	private TbCfcApplicationMstJpaRepository tbCfcRepo;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private DishonurChargeEntryRepository tbDisHnrRepo;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@Autowired
	TbLoiDetService tbLoiDetService;

	@Autowired
	private TbApprejMasService tbApprejMasService;
	
	@Autowired
	private EmployeeJpaRepository employeeRepository;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;


	@Value("${upload.physicalPath}")
	private String filenetPath;

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.SAVE_UPDATE_TRADE_LICENSE_APPLICATION, notes = MainetConstants.TradeLicense.SAVE_UPDATE_TRADE_LICENSE_APPLICATION, response = TradeMasterDetailDTO.class)
	@Path("/tradeLicenseApplication")
	@Transactional
	public TradeMasterDetailDTO saveAndUpdateApplication(@RequestBody TradeMasterDetailDTO tradeMasterDto) {

		try {
			Long appicationId = null;
			LOGGER.info("saveAndUpdateApplication started");
			Organisation org = iOrganisationService.getOrganisationById(tradeMasterDto.getOrgid());
			tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				if (!itemDto.isSelectedItems()) {
					itemDto.setTriRate(null);
					itemDto.setTriStatus(MainetConstants.FlagY);
				} else {
					itemDto.setTriStatus(MainetConstants.FlagA);
				}
			});

			tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				ownDto.setTroPr(MainetConstants.FlagA);
			});

			TbMlTradeMast masEntity = mapDtoToEntity(tradeMasterDto);

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
							tradeMasterDto.getOrgid());
			// #144362
			if (Utility.isEnvPrefixAvailable(iOrganisationService.getOrganisationById(tradeMasterDto.getOrgid()),
					MainetConstants.ENV_SKDCL)) {
				if (tradeMasterDto.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					tradeMasterDto.setDepartmentName(sm.getTbDepartment().getDpDeptdesc());
				else
					tradeMasterDto.setDepartmentName(sm.getTbDepartment().getDpNameMar());
				tradeMasterDto.setServiceDuration(sm.getSmServiceDuration());
			}
			RequestDTO requestDto = setApplicantRequestDto(tradeMasterDto, sm);
			if (masEntity.getApmApplicationId() == null) {

				/* Defect #99393 */
				StringBuilder ownName = new StringBuilder();
				String fName = null;
				tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(dto -> {
					if (StringUtils.isNotEmpty(dto.getTroName())) {
						ownName.append(dto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
				});
				if (ownName != null) {
					fName = ownName.deleteCharAt(ownName.length() - 1).toString();
					if (fName != null) {
						requestDto.setfName(fName);
					}
				}
				appicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
						.createApplication(requestDto);
				LOGGER.info("application number for new trade licence : " + appicationId);
				masEntity.setApmApplicationId(appicationId);
				tradeMasterDto.setApmApplicationId(appicationId);

				// 125706 to save application id in owner and item detail table
				masEntity.getItemDetails().forEach(itemEntity -> {
					itemEntity.setApmApplicationId(tradeMasterDto.getApmApplicationId());
				});
				masEntity.getOwnerDetails().forEach(ownerEntity -> {
					ownerEntity.setApmApplicationId(tradeMasterDto.getApmApplicationId());
				});
			}
			// Defect #133748
			if (tradeMasterDto.isEditValue()) {
				masEntity.getItemDetails().forEach(itemEntity -> {
					itemEntity.setApmApplicationId(tradeMasterDto.getApmApplicationId());
				});
			}
			masEntity = tradeLicenseApplicationRepository.save(masEntity);

			saveHistoryData(masEntity);

			boolean checklist = false;
			if ((tradeMasterDto.getDocumentList() != null) && !tradeMasterDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(appicationId);
				checklist = fileUploadService.doFileUpload(tradeMasterDto.getDocumentList(), requestDto);
				checklist = true;
			}

			int i = 0;
			if ((tradeMasterDto.getAttachments() != null) && !tradeMasterDto.getAttachments().isEmpty()) {
				List<DocumentDetailsVO> getImgList = null;
				for (final TbMlOwnerDetail d : masEntity.getOwnerDetails()) {
					getImgList = new ArrayList<>();

					requestDto.setReferenceId(d.getTroId().toString());
					requestDto.setApplicationId(d.getTroId());
					List<DocumentDetailsVO> getList = tradeMasterDto.getAttachments();
					for (int j = i; j < getList.size(); j++) {
						DocumentDetailsVO img = getList.get(i);
						getImgList.add(img);
						break;

					}

					i++;
					fileUploadService.doFileUpload(getImgList, requestDto);

				}

			}

			LOGGER.info("saveAndUpdateApplication End");

			if (tradeMasterDto.isFree()) {
				iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(tradeMasterDto.getApmApplicationId(),
						MainetConstants.PAY_STATUS.PAID, tradeMasterDto.getOrgid());
				ApplicantDetailDTO applicantDetailDTO = tradeMasterDto.getApplicantDetailDto();
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(tradeMasterDto.getApmApplicationId());
				applicationData.setOrgId(tradeMasterDto.getOrgid());
				applicationData.setIsCheckListApplicable(checklist);
				tradeMasterDto.getApplicantDetailDto().setUserId(tradeMasterDto.getUserId());
				tradeMasterDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				tradeMasterDto.getApplicantDetailDto()
						.setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));

				if (tradeMasterDto.getTrdWard1() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid1(tradeMasterDto.getTrdWard1());
				}
				if (tradeMasterDto.getTrdWard2() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid2(tradeMasterDto.getTrdWard2());
				}
				if (tradeMasterDto.getTrdWard3() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid3(tradeMasterDto.getTrdWard3());
				}
				if (tradeMasterDto.getTrdWard4() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid4(tradeMasterDto.getTrdWard4());
				}
				if (tradeMasterDto.getTrdWard5() != null) {
					tradeMasterDto.getApplicantDetailDto().setDwzid5(tradeMasterDto.getTrdWard5());
				}
				if (requestDto != null && requestDto.getMobileNo() != null) {
					tradeMasterDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				Organisation organisation = iOrganisationService.getOrganisationById(tradeMasterDto.getOrgid());
					LookUp lookup1 = null;
					try {
						lookup1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WWC", "LCC", 1,
								organisation);
					} catch (Exception e) {
						LOGGER.error("No prefix found for for workflow based on category ", e);
					}
					WorkflowMas mas = null;
					if (lookup1 != null && lookup1.getOtherField() != null && lookup1.getOtherField().equals("Y")
							&& (tradeMasterDto.isFree())) {
						tradeMasterDto.setOrgid(org.getOrgid());
						String processName = serviceMasterService.getProcessName(sm.getSmServiceId(),org.getOrgid());
				        if (processName != null) {
				            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
				            boolean autoescalate = false;
				            mas = workflowTyepResolverService.resolveServiceWorkflowType(tradeMasterDto.getOrgid(),
									Long.valueOf(sm.getTbDepartment().getDpDeptid()), sm.getSmServiceId(), null, null,
									tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), tradeMasterDto.getTrdWard1(), tradeMasterDto.getTrdWard2(),
									tradeMasterDto.getTrdWard3(), tradeMasterDto.getTrdWard4(), tradeMasterDto.getTrdWard5());
				            if (mas != null) {
				                String code = CommonMasterUtility
				                        .getNonHierarchicalLookUpObject(mas.getWorkflowMode(), mas.getOrganisation())
				                        .getLookUpCode();
				                if (code.equals("AE")) {
				                    autoescalate = true;
				                }
				                applicationData.setWorkflowId(mas.getWfId());
				            }
				            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
				            applicationData.setIsAutoEscalate(autoescalate);
				            applicationData.setIsFreeService(true);
				            if (MainetConstants.Y_FLAG.equalsIgnoreCase(sm.getSmScrutinyApplicableFlag())) {
				                applicationData.setIsScrutinyApplicable(true);
				            } else {
				                applicationData.setIsScrutinyApplicable(false);
				            }
				            TaskAssignment requesterTaskAssignment = setRequesterTask(applicationData,
				            		applicantDetailDTO);
				            workflowdto.setRequesterTaskAssignment(requesterTaskAssignment);
				            applicationData.setPaymentMode(MainetConstants.PAYMENT.FREE);
				            workflowdto.setApplicationMetadata(applicationData);
				            workflowdto
				                    .setProcessName(processName);
				            if(applicationData.getApplicationId() !=null && applicationData.getReferenceId() ==null) {
				            	
				            	 workflowAction.setApplicationId(applicationData.getApplicationId());
				                 workflowAction.setReferenceId(applicationData.getApplicationId().toString());
				            }else {
								workflowAction.setApplicationId(applicationData.getApplicationId());
								workflowAction.setReferenceId(applicationData.getReferenceId());
				            	
				            }
				            
				            workflowAction.setDateOfAction(new Date());
				            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
				            workflowAction.setOrgId(applicationData.getOrgId());
				            workflowAction.setEmpId(tradeMasterDto.getApplicantDetailDto().getUserId());
				            workflowAction.setCreatedBy(tradeMasterDto.getApplicantDetailDto().getUserId());
				            workflowAction.setCreatedDate(new Date());
				            workflowdto.setWorkflowTaskAction(workflowAction);
				            try {
				                workflowExecutionService.initiateWorkflow(workflowdto);
				            } catch (Exception e) {
				                throw new FrameworkException("Exception while creating workflow for free services", e);
				            }
				        }	
					}else if (tradeMasterDto.isFree()) {
						commonService.initiateWorkflowfreeService(applicationData, tradeMasterDto.getApplicantDetailDto());
					 }
				}
			} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Trade License Application ", exception);
			throw new FrameworkException("Exception occur while saving Trade License Application ", exception);
		}
					 
		sendSmsEmail(tradeMasterDto);
		return tradeMasterDto;
	}
			
	private TaskAssignment setRequesterTask(final ApplicationMetadata applicationData,
            final ApplicantDetailDTO applicantDetailsDto) {
        TaskAssignment assignment = new TaskAssignment();
        Set<String> actorIds = new HashSet<>();
        assignment
                .setActorId(applicantDetailsDto.getUserId() + MainetConstants.operator.COMMA + applicantDetailsDto.getMobileNo());
        actorIds.add(Long.toString(applicantDetailsDto.getUserId()));
        actorIds.add(applicantDetailsDto.getMobileNo());
        assignment.setActorIds(actorIds);
        assignment.setOrgId(applicationData.getOrgId());
        return assignment;
    }

	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);
		// 125706 to set history status in history table
		TbMlTradeMastHist.setHistoryStatus(MainetConstants.FlagA);

		List<TbMlOwnerDetailHist> tbMlOwnerDetailHistList = new ArrayList<>();
		List<TbMlItemDetailHist> tbMlItemDetailHistList = new ArrayList<>();
		masEntity.getOwnerDetails().forEach(ownerEntity -> {

			TbMlOwnerDetailHist tbMlOwnerDetailHistEntity = new TbMlOwnerDetailHist();

			BeanUtils.copyProperties(ownerEntity, tbMlOwnerDetailHistEntity);
			tbMlOwnerDetailHistEntity.setHistoryStatus(MainetConstants.FlagA);
			tbMlOwnerDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlOwnerDetailHistList.add(tbMlOwnerDetailHistEntity);

		});
		masEntity.getItemDetails().forEach(itemEntity -> {

			TbMlItemDetailHist TbMlItemDetailHistEntity = new TbMlItemDetailHist();

			BeanUtils.copyProperties(itemEntity, TbMlItemDetailHistEntity);
			TbMlItemDetailHistEntity.setHistoryStatus(MainetConstants.FlagA);
			TbMlItemDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		auditService.createHistoryForObject(TbMlTradeMastHist);
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.FETCH_TRADE_LICENSE_WITH_DETAILS, notes = MainetConstants.TradeLicense.FETCH_TRADE_LICENSE_WITH_DETAILS, response = TradeMasterDetailDTO.class)
	@Path("/getTradeLicenseApplication/{applicationId}")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenseWithAllDetailsByApplicationId(
			@PathParam(value = "applicationId") Long applicationId) {
		TradeMasterDetailDTO masterDto = new TradeMasterDetailDTO();
		try {
			TbMlTradeMast entity = tradeLicenseApplicationRepository
					.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
			LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
			if (entity != null)
				masterDto = mapEntityToDto(entity);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return masterDto;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenseById(Long id) {
		TradeMasterDetailDTO masterDto = null;
		try {
			TbMlTradeMast entity = tradeLicenseApplicationRepository.findOne(id);
			LOGGER.info("Trade Licence Data fetched for application id " + entity.getApmApplicationId());
			masterDto = mapEntityToDto(entity);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return masterDto;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional
	public TradeMasterDetailDTO saveTradeApplicationDataSuite(TradeMasterDetailDTO tradeMasterDto) {

		try {
			LOGGER.info("saveTradeApplicationDataSuite started");
			TbMlTradeMast masEntity = mapDtoToEntity(tradeMasterDto);
			if (masEntity.getTrdLicno() == null || masEntity.getTrdLicno().isEmpty()) {
				String licenseNo = getNewGeneratedLicenseNo(tradeMasterDto, masEntity.getOrgid());
				masEntity.setTrdLicno(licenseNo);
				tradeMasterDto.setTrdLicno(licenseNo);
			}
			tradeLicenseApplicationRepository.save(masEntity);
			// Defect #131239
			saveHistoryData(masEntity);
			LOGGER.info("saveTradeApplicationDataSuite End");
			tradeMasterDto.setTrdId(masEntity.getTrdId());
		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Trade License Application ", exception);
			throw new FrameworkException("Exception occur while saving Trade License Application ", exception);
		}
		return tradeMasterDto;
	}

	/**
	 * used to map DTO Object to Entity
	 * 
	 * @param tradeMasterDto
	 * @return
	 */
	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();
		List<TbMlOwnerDetail> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});
		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setItemDetails(itemdDetailsList);

		return masEntity;
	}

	/**
	 * used to map Entity To DTO
	 * 
	 * @param masEntity
	 * @return
	 */
	private TradeMasterDetailDTO mapEntityToDto(TbMlTradeMast masEntity) {

		TradeMasterDetailDTO masDto = new TradeMasterDetailDTO();
		List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
		List<TradeLicenseOwnerDetailDTO> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(masEntity, masDto);
		masEntity.getItemDetails().forEach(itemdDetails -> {
			TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
			BeanUtils.copyProperties(itemdDetails, itemDto);
			if (itemDto.getTriRate() != null) {
				itemDto.setSelectedItems(true);
			}
			itemDto.setMasterTradeId(masDto);
			itemdDetailsList.add(itemDto);
		});
		masEntity.getOwnerDetails().forEach(ownerDetails -> {
			TradeLicenseOwnerDetailDTO ownerDto = new TradeLicenseOwnerDetailDTO();
			BeanUtils.copyProperties(ownerDetails, ownerDto);
			ownerDto.setMasterTradeId(masDto);
			ownerDetailsList.add(ownerDto);
		});

		masDto.setTradeLicenseOwnerdetailDTO(ownerDetailsList);
		masDto.setTradeLicenseItemDetailDTO(itemdDetailsList);

		return masDto;

	}

	private RequestDTO setApplicantRequestDto(TradeMasterDetailDTO tradeMasterDto, ServiceMaster sm) {
		TradeLicenseOwnerDetailDTO ownerDetails = tradeMasterDto.getTradeLicenseOwnerdetailDTO().get(0);
		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(ownerDetails.getCreatedBy());

		requestDto.setOrgId(tradeMasterDto.getOrgid());
		requestDto.setLangId((long) tradeMasterDto.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(ownerDetails.getTroName());
		requestDto.setEmail(ownerDetails.getTroEmailid());
		requestDto.setMobileNo(ownerDetails.getTroMobileno());
		requestDto.setAreaName(ownerDetails.getTroAddress());
		// requestDto.setApmSource(tradeMasterDto.getSource());

		if (tradeMasterDto.getTotalApplicationFee() != null) {
			requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue());
		}
		return requestDto;

	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {

		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());
		Date todayDate = new Date();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE, masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class)
				.fetchAllApplicableServiceCharge(sm.getSmServiceId(), organisation.getOrgid(),
						chargeApplicableAt.getLookUpId())
				.stream().sorted(Comparator.comparingLong(TbTaxMasEntity::getTaxDisplaySeq))
				.collect(Collectors.toList());
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			LocalDate applicationDate = LocalDate.now();
			if (!(lookUp.getLookUpCode().equals("LFP"))) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TKF") || taxLookUp.getLookUpCode().equals("LF")) {
				        iterator.remove();
				    }
				}
			} else {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TKF")
				            && !(applicationDate.getMonthValue() >= 4 && applicationDate.getMonthValue() <= 6)) {
				        iterator.remove();
				    }
				    if (taxLookUp.getLookUpCode().equals("LF")
				            && (applicationDate.getMonthValue() >= 4 && applicationDate.getMonthValue() <= 6)) {
				        iterator.remove();
				    }
				}
			}
		}
		
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			Iterator<TbTaxMasEntity> iterator = taxesMaster.iterator();
			LookUp lookUp = CommonMasterUtility
					.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), masDto.getOrgid());

			//LocalDate applicationDate = LocalDate.now();l
			if (lookUp.getLookUpCode().equals("STR")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("TLF") || taxLookUp.getLookUpCode().equals("TRC")) {
				        iterator.remove();
				    }
				}
			} else if (lookUp.getLookUpCode().equals("TL")) {
				while (iterator.hasNext()) {
				    TbTaxMasEntity taxdto = iterator.next();
				    LookUp taxLookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxdto.getTaxDescId(),
				            masDto.getOrgid(), "TXN");
				    if (taxLookUp.getLookUpCode().equals("SLF") || taxLookUp.getLookUpCode().equals("SRC")) {
				        iterator.remove();
				    }
				}
			}
		}

		List<MLNewTradeLicense> masterList = new ArrayList<>();
		List<MLNewTradeLicense> list = new ArrayList<>();

		List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();
		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
		List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
		boolean result = false;
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SUDA)
				&& StringUtils.isNotEmpty(masDto.getCheckApptimeCharge())
				&& masDto.getCheckApptimeCharge().equals(MainetConstants.FlagY)) {
			result = true;
		}
		if (result || masDto.getTrdId() != null) {

			try {
				lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
						masDto.getOrgid());
				lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2,
						masDto.getOrgid());
				lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3,
						masDto.getOrgid());
				lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4,
						masDto.getOrgid());
				lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5,
						masDto.getOrgid());
			} catch (Exception e) {
				LOGGER.info("prefix level not found");
			}

			for (TbTaxMasEntity taxdto : taxesMaster) {

				if (taxdto.getParentCode() == null) {
					

					for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
						MLNewTradeLicense license = new MLNewTradeLicense();
						license.setOrgId(masDto.getOrgid());
						license.setServiceCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE);
						Department dept = tbDepartmentService.findDepartmentByCode("ML");
						TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(), dept.getDpDeptid(),
								taxdto.getTaxCode());
						String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
								MainetConstants.FlagE, masDto.getOrgid());
						license.setTaxType(taxType);
						license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																	// code(category
						license.setTaxCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

						license.setTaxSubCategory(CommonMasterUtility
								.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());

						license.setRateStartDate(todayDate.getTime());
						license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
						license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
						LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
								organisation);
						license.setLicenseType(licenseType.getDescLangFirst());
						// For Setting LicenseTpe NA in case of SKDCL ENV.Defect #102173
						// start
						try {

							List<LookUp> lookupList = CommonMasterUtility.getListLookup(
									MainetConstants.TradeLicense.TRD_ENV, UserSession.getCurrent().getOrganisation());
							if (lookupList != null && !lookupList.isEmpty()) {
								String env = lookupList.get(0).getLookUpCode();
								if (env != null && !env.isEmpty()) {
									if (env.equals(MainetConstants.ENV_SKDCL)) {

										license.setLicenseType(MainetConstants.TradeLicense.NOT_APPLICABLE);
									}
								}
							}
						} catch (Exception e) {

						}
						// end
						license.setArea(dto.getTrdUnit());

						if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
							List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
									.collect(Collectors.toList());
							if (level1 != null && !level1.isEmpty()) {
								license.setItemCategory1(level1.get(0).getDescLangFirst());
								dto.setItemCategory1(level1.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
							List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
									.collect(Collectors.toList());
							if (level2 != null && !level2.isEmpty()) {
								license.setItemCategory2(level2.get(0).getDescLangFirst());
								dto.setItemCategory2(level2.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
							List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
									.collect(Collectors.toList());
							if (level3 != null && !level3.isEmpty()) {
								license.setItemCategory3(level3.get(0).getDescLangFirst());
								dto.setItemCategory3(level3.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
							List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
									.collect(Collectors.toList());
							if (level4 != null && !level4.isEmpty()) {
								license.setItemCategory4(level4.get(0).getDescLangFirst());
								dto.setItemCategory4(level4.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
							List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
									clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
									.collect(Collectors.toList());
							if (level5 != null && !level5.isEmpty()) {
								license.setItemCategory5(level5.get(0).getDescLangFirst());
								dto.setItemCategory5(level5.get(0).getDescLangFirst());
							}
						} else {
							dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
						}
						masterList.add(license);

					}

				}

			}

		}
		List<MLNewTradeLicense> master = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(masterList)) {

			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
			WSResponseDTO responseDTO = new WSResponseDTO();
			WSRequestDTO wsRequestDTO = new WSRequestDTO();

			wsRequestDTO.setDataModel(masterList);

			try {
				LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
				responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
				if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					master = setTradeLicenceChargesDTO(responseDTO);
				} else {
					throw new FrameworkException(responseDTO.getErrorMessage());
				}
			} catch (Exception ex) {
				throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
			}
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");

			setChargeToItemsDtoList(master, taxesMaster, masDto);
			// D#129779 for saving charge amount in TB_ML_ITEMDETAILS Table
			if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
				setChargeDetailsInItemDetailsTable(lookupListLevel2, master, taxesMaster, masDto);
			}
		}

		for (TbTaxMasEntity taxdto : taxesMaster) {

			if (taxdto.getParentCode() != null) {

				for (TradeLicenseItemDetailDTO dto : tradeLicenseItemDetailDTO) {
					MLNewTradeLicense license = new MLNewTradeLicense();
					license.setOrgId(masDto.getOrgid());
					license.setServiceCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE);
					Department dept = tbDepartmentService.findDepartmentByCode("ML");
					TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(), dept.getDpDeptid(),
							taxdto.getTaxCode());
					String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
							MainetConstants.FlagE, masDto.getOrgid());
					license.setTaxType(taxType);
					license.setTaxCode(taxdto.getTaxCode()); // for getting scrutiny level tax
																// code(category
					// and
					TbTaxMas ParentTax = tbTaxMasService.findTaxByTaxIdAndOrgId(taxdto.getParentCode(),
							masDto.getOrgid());

					license.setParentTaxCode(ParentTax.getTaxCode()); // subcategory)
					license.setTaxCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory1(), organisation).getDescLangFirst());

					license.setTaxSubCategory(CommonMasterUtility
							.getHierarchicalLookUp(taxdto.getTaxCategory2(), organisation).getDescLangFirst());

					master.forEach(model -> {

						if (dto.getItemCategory1().equals(model.getItemCategory1())
								&& dto.getItemCategory2().equals(model.getItemCategory2())
								&& dto.getItemCategory3().equals(model.getItemCategory3())
								&& dto.getItemCategory4().equals(model.getItemCategory4())
								&& dto.getItemCategory5().equals(model.getItemCategory5())
								&& model.getTaxCode().equals(ParentTax.getTaxCode())) {

							if (model.getTaxType().equals(CommonMasterUtility
									.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {

								license.setParentTaxValue(model.getSlabRate1());

							}
							if (model.getTaxType().equals(CommonMasterUtility
									.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
								license.setParentTaxValue(model.getFlatRate());
							}

						}
					});

					license.setRateStartDate(todayDate.getTime());
					license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
					license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
					LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
							organisation);
					license.setLicenseType(licenseType.getDescLangFirst());

					// category code

					if (dto.getTriCod1() != null && dto.getTriCod1() != 0) {
						List<LookUp> level1 = lookupListLevel1.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod1().longValue())
								.collect(Collectors.toList());
						if (level1 != null && !level1.isEmpty()) {
							license.setItemCategory1(level1.get(0).getDescLangFirst());
							dto.setItemCategory1(level1.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod2() != null && dto.getTriCod2() != 0) {
						List<LookUp> level2 = lookupListLevel2.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod2().longValue())
								.collect(Collectors.toList());
						if (level2 != null && !level2.isEmpty()) {
							license.setItemCategory2(level2.get(0).getDescLangFirst());
							dto.setItemCategory2(level2.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod3() != null && dto.getTriCod3() != 0) {
						List<LookUp> level3 = lookupListLevel3.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod3().longValue())
								.collect(Collectors.toList());
						if (level3 != null && !level3.isEmpty()) {
							license.setItemCategory3(level3.get(0).getDescLangFirst());
							dto.setItemCategory3(level3.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod4() != null && dto.getTriCod4() != 0) {
						List<LookUp> level4 = lookupListLevel4.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod4().longValue())
								.collect(Collectors.toList());
						if (level4 != null && !level4.isEmpty()) {
							license.setItemCategory4(level4.get(0).getDescLangFirst());
							dto.setItemCategory4(level4.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}
					if (dto.getTriCod5() != null && dto.getTriCod5() != 0) {
						List<LookUp> level5 = lookupListLevel5.parallelStream().filter(
								clList -> clList != null && clList.getLookUpId() == dto.getTriCod5().longValue())
								.collect(Collectors.toList());
						if (level5 != null && !level5.isEmpty()) {
							license.setItemCategory5(level5.get(0).getDescLangFirst());
							dto.setItemCategory5(level5.get(0).getDescLangFirst());
						}
					} else {
						dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE);
					}

					list.add(license);

				}
			}
		}

		if (CollectionUtils.isNotEmpty(list)) {
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
			WSResponseDTO responseDto = new WSResponseDTO();
			WSRequestDTO wsRequestDto = new WSRequestDTO();
			List<MLNewTradeLicense> masterResponseList = new ArrayList<>();

			wsRequestDto.setDataModel(list);

			try {
				LOGGER.info("brms ML request DTO  is :" + wsRequestDto.toString());
				responseDto = RestClient.callBRMS(wsRequestDto, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
				if (responseDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
					masterResponseList = setTradeLicenceChargesDTO(responseDto);
				} else {
					throw new FrameworkException(responseDto.getErrorMessage());
				}
			} catch (Exception ex) {
				throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
			}
			LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");
			setChargeToDtoList(masterResponseList, taxesMaster, masDto);
			// D#129779 for saving charge amount in TB_ML_ITEMDETAILS Table
			if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
				setChargeDetailsInItemDetailsTable(lookupListLevel2, master, taxesMaster, masDto);
			}

		}

		return masDto;
	}

	// D#129779 for saving charge amount in TB_ML_ITEMDETAILS Table
	private void setChargeDetailsInItemDetailsTable(List<LookUp> lookupListLevel2, List<MLNewTradeLicense> master,
			List<TbTaxMasEntity> taxesMaster, TradeMasterDetailDTO masDto) {

		masDto.getTradeLicenseItemDetailDTO().stream().forEach(itemDto -> {
			List<LookUp> level2 = lookupListLevel2.stream()
					.filter(clList -> clList != null && clList.getLookUpId() == itemDto.getTriCod2())
					.collect(Collectors.toList());
			String itc2Desc = level2.get(0).getLookUpDesc();
			master.stream().forEach(mlMaster -> {
				if (mlMaster.getItemCategory2() != null && mlMaster.getItemCategory2().equals(itc2Desc)) {
					taxesMaster.parallelStream().forEach(t -> {
						if (t.getTaxCode().equals(mlMaster.getTaxCode())
								&& mlMaster.getTaxCategory().equals(MainetConstants.TradeLicense.DEPOSITS)) {
							itemDto.setDepositAmt(new BigDecimal(Math.round(mlMaster.getSlabRate1())));
						} else if (t.getTaxCode().equals(mlMaster.getTaxCode())) {
							itemDto.setLicenseFee(new BigDecimal(Math.round(mlMaster.getSlabRate1())));
						}
					});
				}
			});

		});
		TbMlTradeMast trdEntity = mapDtoToEntity(masDto);
		tradeLicenseApplicationRepository.save(trdEntity);
	}

	private MLNewTradeLicense settingTaxCategories(MLNewTradeLicense mlNewTradeLicense, TbTaxMasEntity enity,
			Organisation organisation) {

		List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.ONE, organisation);
		for (LookUp lookUp : taxCategories) {
			if (enity.getTaxCategory1() != null && lookUp.getLookUpId() == enity.getTaxCategory1()) {
				mlNewTradeLicense.setTaxCategory(lookUp.getDescLangFirst());
				break;
			}
		}
		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
				MainetConstants.NUMBERS.TWO, organisation);
		for (LookUp lookUp : taxSubCategories) {
			if (enity.getTaxCategory2() != null && lookUp.getLookUpId() == enity.getTaxCategory2()) {
				mlNewTradeLicense.setTaxSubCategory(lookUp.getDescLangFirst());
				break;
			}

		}
		return mlNewTradeLicense;

	}

	private TradeMasterDetailDTO setChargeToItemsDtoList(List<MLNewTradeLicense> master,
			List<TbTaxMasEntity> taxesMaster, TradeMasterDetailDTO masDto) {
		Organisation organisation = iOrganisationService.getOrganisationById(masDto.getOrgid());
		// D#129853 for round off the charge value

		Boolean flag = Utility.isRoundedOffApplicable(organisation);
		// first time set this value as null due to loi generation can done multiple
		// time till save
		masDto.setTotalApplicationFee(null);
		boolean result = false;
		if (StringUtils.isNotEmpty(masDto.getCheckApptimeCharge())
				&& masDto.getCheckApptimeCharge().equals(MainetConstants.FlagY)
				&& (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SUDA)
						|| Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)))
			result = true;

		if (result || masDto.getApmApplicationId() != null)
			masDto.getTradeLicenseItemDetailDTO().forEach(entity -> {
				master.forEach(model -> {

					double currVal = 0.0d;

					if (entity.getItemCategory1().equals(model.getItemCategory1())
							&& entity.getItemCategory2().equals(model.getItemCategory2())
							&& entity.getItemCategory3().equals(model.getItemCategory3())
							&& entity.getItemCategory4().equals(model.getItemCategory4())) {

						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {
							if (flag) {
								entity.setTriRate(BigDecimal.valueOf(Math.round(model.getSlabRate1())));

								currVal = Math.round(model.getSlabRate1());
							} else {
								entity.setTriRate(BigDecimal.valueOf(model.getSlabRate1()));

								currVal = model.getSlabRate1();
							}

						}
						if (model.getTaxType().equals(CommonMasterUtility
								.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
							if (flag) {
								entity.setTriRate(BigDecimal.valueOf(Math.round(model.getFlatRate())));
								currVal = Math.round(model.getFlatRate());
							} else {
								entity.setTriRate(BigDecimal.valueOf(model.getFlatRate()));
								currVal = model.getFlatRate();
							}
						}
						double totalAmount = 0.0d;
						for (TbTaxMasEntity taxdto : taxesMaster) {
							if (model.getTaxCode().equals(taxdto.getTaxCode())) {
								totalAmount = masDto.getFeeIds().containsKey(taxdto.getTaxId())
										? masDto.getFeeIds().get(taxdto.getTaxId())
										: 0.0d;
								totalAmount = currVal + totalAmount;
								masDto.getFeeIds().put(taxdto.getTaxId(), totalAmount);

							}

						}
						// #140263
						for (final Entry<Long, Double> entry : masDto.getFeeIds().entrySet()) {
							if (entry.getValue() != null)
								masDto.setLoiFee(new BigDecimal(entry.getValue()));
						}
						masDto.setTotalApplicationFee(masDto.getTotalApplicationFee() != null
								? masDto.getTotalApplicationFee().add(new BigDecimal(totalAmount))
								: new BigDecimal(totalAmount));
					}

				});
			});

		return masDto;
	}

	private TradeMasterDetailDTO setChargeToDtoList(List<MLNewTradeLicense> master, List<TbTaxMasEntity> taxesMaster,
			TradeMasterDetailDTO masDto) {

		Organisation organisation = iOrganisationService.getOrganisationById(masDto.getOrgid());
// D#129853 for round off the charge value		
		Boolean flag = Utility.isRoundedOffApplicable(organisation);

		if (masDto.getApmApplicationId() != null)

			master.forEach(model -> {
				double currVal = 0.0d;

				if (model.getTaxType().equals(
						CommonMasterUtility.getValueFromPrefixLookUp("S", "FSD", organisation).getDescLangFirst())) {
					if (flag) {
						currVal = Math.round(model.getSlabRate1());
					} else {
						currVal = model.getSlabRate1();
					}
				}
				if (model.getTaxType().equals(
						CommonMasterUtility.getValueFromPrefixLookUp("F", "FSD", organisation).getDescLangFirst())) {
					if (flag) {
						currVal = Math.round(model.getSlabRate1());
					} else {
						currVal = model.getSlabRate1();
					}
				}

				for (TbTaxMasEntity taxdto : taxesMaster) {
					if (model.getTaxCode().equals(taxdto.getTaxCode())) {

						double totalAmount = masDto.getFeeIds().containsKey(taxdto.getTaxId())
								? masDto.getFeeIds().get(taxdto.getTaxId())
								: 0.0d;
						totalAmount = currVal + totalAmount;

						masDto.getFeeIds().put(taxdto.getTaxId(), totalAmount);

					}

				}

			});

		return masDto;
	}

	private TradeMasterDetailDTO setApplicationChargeToDtoList(List<MLNewTradeLicense> master,
			TradeMasterDetailDTO masDto) {
		master.forEach(model -> {

			if (model.getTaxSubCategory().equalsIgnoreCase("Application Charge")) {

				masDto.setApplicationCharge(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()).toString());
				masDto.setTotalApplicationFee(
						BigDecimal.valueOf(master.stream().mapToDouble(c -> c.getFlatRate()).sum()));
			}

		});

		return masDto;
	}

	private List<MLNewTradeLicense> setTradeLicenceChargesDTO(WSResponseDTO responseDTO) {
		LOGGER.info("setTradeLicenceChargesDTO execution start..");
		final List<?> charges = RestClient.castResponse(responseDTO, MLNewTradeLicense.class);
		List<MLNewTradeLicense> finalRateMaster = new ArrayList<>();
		for (Object rate : charges) {
			MLNewTradeLicense masterRate = (MLNewTradeLicense) rate;
			finalRateMaster.add(masterRate);
		}
		LOGGER.info("setTradeLicenceChargesDTO execution end..");
		return finalRateMaster;
	}

	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public List<TradeDataEntyDto> getFilteredNewTradeLicenceList(Long licenseType, String oldLicenseNo,
			String newLicenseNo, Long ward1, Long ward2, Long ward3, Long ward4, Long ward5, Long orgId, String busName,
			String ownerName) {

		List<TradeDataEntyDto> detailDTOs = new ArrayList<>();
		// As per Rajesh sir's suggestion create new TradeDataEntyDto for Defect #100482
		List<Object[]> list = iTradeLicenseApplicationDao.getFilteredNewTradeLicenceList(licenseType, oldLicenseNo,
				newLicenseNo, ward1, ward2, ward3, ward4, ward5, orgId, busName, ownerName);

		for (Object[] obj : list) {
			TradeDataEntyDto dto = new TradeDataEntyDto();
			dto.setLicenseType(obj[0].toString());
			if (obj[1] != null)
				dto.setTrdOldlicno(obj[1].toString());
			if (obj[2] != null)
				dto.setTrdLicno(obj[2].toString());
			if (obj[3] != null)
				dto.setLicenseFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(obj[3]));
			if (obj[4] != null)
				dto.setLicenseToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(obj[4]));
			if (obj[5] != null)
				dto.setTrdBusnm(obj[5].toString());
			if (obj[6] != null)
				dto.setTrdBusadd(obj[6].toString());
			if (obj[7] != null)
				dto.setTrdId(obj[7].toString());

			LOGGER.info("Inside  getFilteredNewTradeLicenceList()  ----------->" + dto);
			detailDTOs.add(dto);
		}
		/*
		 * list.parallelStream().forEach(ob -> { TradeMasterDetailDTO detailDTO = new
		 * TradeMasterDetailDTO(); BeanUtils.copyProperties(ob, detailDTO);
		 * detailDTO.setLicenseType(CommonMasterUtility.findLookUpDesc("LIT", orgId,
		 * ob.getTrdLictype())); if (detailDTO.getLicenseType() == null) {
		 * detailDTO.setLicenseType(""); } if (ob.getTrdLicfromDate() != null)
		 * detailDTO.setLicenseFromDate( new
		 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(ob.getTrdLicfromDate()))
		 * ; if (ob.getTrdLictoDate() != null) detailDTO.setLicenseToDate( new
		 * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(ob.getTrdLictoDate()));
		 * detailDTOs.add(detailDTO); });
		 */

		return detailDTOs;
	}

	private String getNewGeneratedLicenseNo(TradeMasterDetailDTO tradeMasterDto, Long orgId) {
		String licenseNo = null;
		// D34938
		SequenceConfigMasterDTO configMasterDTO = null;

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
				MainetConstants.TradeLicense.TB_ML_TRADE_MAST, MainetConstants.TradeLicense.TRD_LICNO);

		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		// added code for SKDCL ENV to create License No according to Group of Trade
		// Catagory User Story #107219
		try {

			String env = checkEnviorement();
			if (env != null && !env.isEmpty()) {
				if (env.equals(MainetConstants.ENV_SKDCL)) {
					// generate sequence number.
					LookUp licType = null;
					// Defect #137903
					boolean istempLic = false;
					if (tradeMasterDto.getTrdLictype() != null) {
						licType = CommonMasterUtility.getNonHierarchicalLookUpObject(tradeMasterDto.getTrdLictype());
						if (licType != null && licType.getLookUpCode() != null
								&& licType.getLookUpCode().equals(MainetConstants.FlagT)) {
							istempLic = true;
						}
					}
					LookUp lookUp = null;
					LookUp lookUp1 = null;
					String LicenseNo = null;
					String licNo = null;
					// Defect #137611
					String seqVar = "";
					try {
						if (tradeMasterDto.getTrdWard1() != null) {
							lookUp = CommonMasterUtility.getHierarchicalLookUp(tradeMasterDto.getTrdWard1(), orgId);
							if (lookUp != null && StringUtils.isNotEmpty(lookUp.getLookUpCode())) {
								seqVar = lookUp.getLookUpCode();
							}
						}
						if (tradeMasterDto.getTradeLicenseItemDetailDTO() != null
								&& tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1() != null
								&& !istempLic) {
							lookUp1 = CommonMasterUtility.getHierarchicalLookUp(
									tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);
							if (lookUp1 != null && StringUtils.isNotEmpty(lookUp1.getOtherField())) {
								seqVar = seqVar + lookUp1.getOtherField();
							}
						} else {
							seqVar = MainetConstants.FlagR + seqVar;
						}
						Long sequence = 0L;
						if (StringUtils.isNotEmpty(seqVar)) {
							sequence = ApplicationContextProvider.getApplicationContext()
									.getBean(SeqGenFunctionUtility.class).getNumericSeqNo(seqVar,
											MainetConstants.TradeLicense.TB_ML_TRADE_MAST,
											MainetConstants.TradeLicense.TRD_LICNO, orgId, "CNT", deptId.toString(), 1l,
											99999999l);
							licNo = String.format(MainetConstants.CommonMasterUi.PADDING_FOUR,
									Integer.parseInt(sequence.toString()));
						}
						if (lookUp != null) {
							LicenseNo = lookUp.getLookUpCode();
						}
						if (lookUp1 != null && !istempLic) {
							LicenseNo = LicenseNo + lookUp1.getOtherField();
						} else {
							LicenseNo = LicenseNo + MainetConstants.FlagR;
						}
						if (LicenseNo != null) {
							return LicenseNo + licNo.toString();
						}

					} catch (Exception e) {
						// TODO: handle exception

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured at the time of license no generating for SKDCL ENV");
		}
		// end

		if (configMasterDTO.getSeqConfigId() == null) {

			LOGGER.info("financial Year " + financiaYear);
			if (financiaYear != null) {
				// get financial year from date & end date and generate financial year as like:
				// 2018-19 format for new license code
				String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(),
						financiaYear.getFaToDate());
				LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(
						tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);

				// generate sequence number.
				final Long sequence = ApplicationContextProvider.getApplicationContext()
						.getBean(SeqGenFunctionUtility.class)
						.generateSequenceNo(MainetConstants.TradeLicense.MARKET_LICENSE,
								MainetConstants.TradeLicense.TB_ML_TRADE_MAST, MainetConstants.TradeLicense.TRD_LICNO,
								orgId, MainetConstants.FlagC, financiaYear.getFaYear());
				// generate new license code.
				licenseNo = lookup.getLookUpCode() + MainetConstants.WINDOWS_SLASH + finacialYear
						+ MainetConstants.WINDOWS_SLASH
						+ String.format(MainetConstants.WorksManagement.THREE_PERCENTILE, sequence);
				LOGGER.info("license No for new trade License" + licenseNo);
			}
			return licenseNo;
		} else {
			CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
			commonSequenceConfigDto.setFinancYear(financiaYear);

			for (TradeLicenseItemDetailDTO detailDTO : tradeMasterDto.getTradeLicenseItemDetailDTO()) {
				if (detailDTO.getTriCod1() != null) {
					commonSequenceConfigDto.setTradeCategory(detailDTO.getTriCod1());
					break;
				}
			}

			return seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
		}

	}

	@Override
	public TradeMasterDetailDTO getPropertyDetailsByPropertyNumber(TradeMasterDetailDTO tradeMasterDTO) {
		TradeMasterDetailDTO infoDTO = new TradeMasterDetailDTO();
		PropertyDetailDto detailDTO = null;
		PropertyInputDto propInputDTO = new PropertyInputDto();
		propInputDTO.setPropertyNo(tradeMasterDTO.getPmPropNo());
		propInputDTO.setOrgId(tradeMasterDTO.getOrgid());
		// #124288
		if (StringUtils.isNotEmpty(tradeMasterDTO.getTrdFlatNo()))
			propInputDTO.setFlatNo(tradeMasterDTO.getTrdFlatNo());
		Organisation organisation = new Organisation();
		organisation.setOrgid(tradeMasterDTO.getOrgid());
		try {
		final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
				ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
		if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

			detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
			LOGGER.info("PropertyDetailDto formed is " + detailDTO);
			if (detailDTO != null) {
				if (tradeMasterDTO.getLangId() != null
						&& tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID
						&& StringUtils.isNotEmpty(detailDTO.getJointOwnerNameReg()))
					infoDTO.setPrimaryOwnerName(detailDTO.getJointOwnerNameReg());
				else
					infoDTO.setPrimaryOwnerName(detailDTO.getJointOwnerName());
				if (tradeMasterDTO.getLangId() != null
						&& tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID
						&& StringUtils.isNotEmpty(detailDTO.getUsageTypeReg()))
					infoDTO.setUsage(detailDTO.getUsageTypeReg());
				else
					infoDTO.setUsage(detailDTO.getUasge());
				infoDTO.setTotalOutsatandingAmt(detailDTO.getTotalOutsatandingAmt());
				if (tradeMasterDTO.getLangId() != null
						&& tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID
						&& StringUtils.isNotEmpty(detailDTO.getPropAddressReg()))
					infoDTO.setPropertyAddress(detailDTO.getPropAddressReg());
				else
					infoDTO.setPropertyAddress(detailDTO.getAddress());
				infoDTO.setPmPropNo(detailDTO.getPropNo());
				// Us#97147
				if (tradeMasterDTO.getLangId() != null
						&& tradeMasterDTO.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID
						&& StringUtils.isNotEmpty(detailDTO.getPropertyTypeReg()))
					infoDTO.setPropertyType(detailDTO.getPropertyTypeReg());
				else
					infoDTO.setPropertyType(detailDTO.getPropertyType());
				infoDTO.setSurveyNumber(detailDTO.getTppSurveyNumber());
				infoDTO.setVillageName(detailDTO.getAreaName());
				infoDTO.setPartNo(detailDTO.getTppKhataNo());
				infoDTO.setPlotNo(detailDTO.getTppPlotNo());
				infoDTO.setRoadType(detailDTO.getProAssdRoadfactorDesc());
				infoDTO.setPropLvlRoadType(detailDTO.getRoadTypeId());
				infoDTO.setLandType(detailDTO.getLandTypeId());
				infoDTO.setAssPlotArea(detailDTO.getAssPlotArea());
				infoDTO.setAssessmentCheckFlag(detailDTO.getAssessmentCheckFlag());
				infoDTO.setTrdLicno(tradeMasterDTO.getTrdLicno());
				infoDTO.setpTaxCollEmpId(detailDTO.getpTaxCollEmpId());
				infoDTO.setpTaxCollEmpName(detailDTO.getpTaxCollEmpName());
				infoDTO.setpTaxCollEmpMobNo(detailDTO.getpTaxCollEmpMobNo());
				infoDTO.setpTaxCollEmpEmailId(detailDTO.getpTaxCollEmpEmailId());
				infoDTO.setTrdFlatNo(tradeMasterDTO.getTrdFlatNo());
                //D#142339
				infoDTO.setTrdEWard1(detailDTO.getWd1());
				infoDTO.setTrdEWard2(detailDTO.getWd2());
				infoDTO.setTrdEWard3(detailDTO.getWd3());
				infoDTO.setTrdEWard4(detailDTO.getWd4());
				infoDTO.setTrdEWard5(detailDTO.getWd5());
				
			}
		
		} else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
			LOGGER.info("No Data found for given property No " + tradeMasterDTO.getPmPropNo());
		} else {
			LOGGER.error("Problem while getting property by property Number " + responseEntity);
		}
		}catch(Exception e) {
			LOGGER.error("Problem while getting " + e);
		}
		
		// #140250 to get water due amount by property no
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SUDA)) {
			if (StringUtils.isNotEmpty(tradeMasterDTO.getPmPropNo())) {
				final ResponseEntity<?> response = RestClient.callRestTemplateClient(tradeMasterDTO.getPmPropNo(),
						ServiceEndpoints.WATER_DUE_AMOUNT_BY_PROP_NO + MainetConstants.WINDOWS_SLASH
								+ tradeMasterDTO.getPmPropNo());
				Double due;
				if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
					due = Double.valueOf(response.getBody().toString());
					if (due > 0)
						infoDTO.setTotalWaterOutsatandingAmt(due);
				} else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
					LOGGER.info("No Water outstanding found for given property No " + tradeMasterDTO.getPmPropNo());
				} else {
					LOGGER.error("Problem while getting Water outstanding by property Number " + response);
				}
			}
		}
		return infoDTO;
	}

	// #124288 to get flat list
	@Override
	@POST
	@Path("/getPropertyFlatNo/{propNo}/orgId/{orgId}")
	public List<String> getPropertyFlatNo(@PathParam("propNo") String propNo, @PathParam("orgId") Long orgId) {

		TradeMasterDetailDTO infoDTO = new TradeMasterDetailDTO();
		// call to GET_BILLING_MEHOD_BY_PROP_NO to identify its a billingMethod(I/W)
		Map<String, String> requestParam = new HashMap<>();
		ResponseEntity<?> responseEntity;
		List<String> flatNoList = new ArrayList<>();

		if (StringUtils.isNotBlank(propNo)) {
			requestParam.put("propNo", propNo.replaceAll("\\s", ""));
		} else {
			LOGGER.info("reference no can't be empty");
		}
		requestParam.put(MainetConstants.Common_Constant.ORGID, orgId.toString());
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		URI uri = dd.expand(ServiceEndpoints.GET_BILLING_MEHOD_BY_PROP_NO, requestParam);
		String billingMethod = "";
		/*
		 * try { responseEntity = RestClient.callRestTemplateClient(null,
		 * uri.toString()); HttpStatus statusCode = responseEntity.getStatusCode(); if
		 * (statusCode == HttpStatus.OK) { billingMethod = (String) new
		 * JSONObject(responseEntity).get("billingMethod"); } } catch (Exception ex) {
		 * log.info("calling for property dues" + ex); }
		 */

		// next call for flat no list

		uri = dd.expand(ServiceEndpoints.GET_FLAT_LIST_BY_PROP_NO, requestParam);
		responseEntity = RestClient.callRestTemplateClient(requestParam, uri.toString());
		flatNoList = (List<String>) responseEntity.getBody();
		if (flatNoList != null && !flatNoList.isEmpty()) {
			infoDTO.setFlatNoList(flatNoList);
			return flatNoList;
		} else {
			infoDTO.setFlatNoList(flatNoList);
		}
		return flatNoList;
	}

	@Override
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenseByOldLiscenseNo(String oldLicenseNo, Long orgId) {
		TbMlTradeMast entity = null;
		TradeMasterDetailDTO masDto = null;
		entity = tradeLicenseApplicationRepository.getTradeLicenseByOldLiscenseNo(oldLicenseNo, orgId);
		if (entity != null) {
			masDto = mapEntityToDto(entity);
		}
		return masDto;
	}

	@WebMethod(exclude = true)
	@Override
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {

		TradeMasterDetailDTO entity = getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		WardZoneBlockDTO wardZoneDTO = null;

		if (entity != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (entity.getTrdWard1() != null) {
				wardZoneDTO.setAreaDivision1(entity.getTrdWard1());
			}
			if (entity.getTrdWard2() != null) {
				wardZoneDTO.setAreaDivision2(entity.getTrdWard2());
			}
			if (entity.getTrdWard3() != null) {
				wardZoneDTO.setAreaDivision3(entity.getTrdWard3());
			}
			if (entity.getTrdWard4() != null) {
				wardZoneDTO.setAreaDivision4(entity.getTrdWard4());
			}
			if (entity.getTrdWard5() != null) {
				wardZoneDTO.setAreaDivision5(entity.getTrdWard5());
			}
		}

		return wardZoneDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		TradeMasterDetailDTO entity = getTradeLicenseWithAllDetailsByApplicationId(applicationId);

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);

		TradeMasterDetailDTO tradeLicenceCharges = getTradeLicenceChargesFromBrmsRule(entity);

		return tradeLicenceCharges.getFeeIds();
	}

	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public List<TradeMasterDetailDTO> getActiveApplicationIdByOrgId(Long orgid) {

		List<TradeMasterDetailDTO> dtoList = new ArrayList<TradeMasterDetailDTO>();
		TradeMasterDetailDTO dto = new TradeMasterDetailDTO();

		long trdStatus = CommonMasterUtility
				.getValueFromPrefixLookUp("I", "LIS", iOrganisationService.getOrganisationById(orgid)).getLookUpId();

		long trdStatus1 = CommonMasterUtility
				.getValueFromPrefixLookUp("N", "LIS", iOrganisationService.getOrganisationById(orgid)).getLookUpId();
		// code added for D#125111
		Long serviceId = serviceMasterService.getServiceIdByShortName(orgid,
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
		List<TbMlTradeMast> masEntity = tradeLicenseApplicationRepository.getActiveApplicationIdByOrgId(orgid,
				trdStatus, trdStatus1, serviceId);

		for (TbMlTradeMast tbTradeMaster : masEntity) {

			dto = mapEntityToDto1(tbTradeMaster);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> getpaymentMode(Long orgId, String loiNo) {
		List<TradeMasterDetailDTO> tradeMasterDetailDtoList = new ArrayList<>();
		List<Object[]> licenseApproval = iTradeLicenseApplicationDao.getpaymentMode(orgId, loiNo);
		TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
		for (Object[] license : licenseApproval) {

			tradeMasterDetailDTO.setRmRcptno((Long) license[0]);
			tradeMasterDetailDTO.setRmDate((Date) license[1]);
			tradeMasterDetailDTO.setRmAmount((BigDecimal) license[2]);
			tradeMasterDetailDTO.setCpdDesc((String) license[3]);
			tradeMasterDetailDTO.setCpdDescMar((String) license[4]);
			tradeMasterDetailDTO.setRmLoiNo((String) license[5]);
			tradeMasterDetailDTO.setApmApplicationId((Long) license[6]);
			tradeMasterDetailDTO.setCpdValue((String) license[7]);
			tradeMasterDetailDTO.setCheckStatus((Long) license[8]);
			tradeMasterDetailDtoList.add(tradeMasterDetailDTO);
		}

		return tradeMasterDetailDtoList;
	}

	@Override
	@Transactional
	public void updateTradeLicenseStatusFlag(TradeMasterDetailDTO tradeMasterDto, Long orgId, Long flag, Date toDate,
			String lgIpMacUpd) {

		String licenseNo = getNewGeneratedLicenseNo(tradeMasterDto, orgId);
		if (licenseNo != null) {
			tradeLicenseApplicationRepository.updateTradeLicenseFlag(tradeMasterDto.getApmApplicationId(), orgId, flag,
					toDate, lgIpMacUpd, licenseNo);
		}

		else {
			throw new FrameworkException("Exception occur while generating License Number");
		}
	}

	@Override
	@Transactional
	public void updateTradeLicenseStatus(TradeMasterDetailDTO tradeMasterDto, Long orgId, Long flag,
			String lgIpMacUpd) {

		if (tradeMasterDto.getTrdLicno() != null && !tradeMasterDto.getTrdLicno().isEmpty()) {
			tradeLicenseApplicationRepository.updateTradeLicenseFlag(tradeMasterDto.getApmApplicationId(), orgId, flag,
					lgIpMacUpd);
		}

		else {
			throw new FrameworkException("Exception occur while generating License Number");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.LICENSE_DETAILS_BY_LICENSE_NO, notes = MainetConstants.TradeLicense.LICENSE_DETAILS_BY_LICENSE_NO, response = TradeMasterDetailDTO.class)
	@Path("/getLicenseDetailsFromLicenseNo/{trdLicno}/{orgId}")
	public TradeMasterDetailDTO getLicenseDetailsByLicenseNo(@PathParam(value = "trdLicno") String trdLicno,
			@PathParam(value = "orgId") Long orgId) {
		// #134456
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		if (org != null && Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			trdLicno = URLDecoder.decode(trdLicno);
		}
		TradeMasterDetailDTO masDto = null;
		TbMlTradeMast entity = null;
		try {
			// User Story #141190
			if (orgId != null && orgId != 0L) {
				entity = tradeLicenseApplicationRepository.getLicenseDetailsByLicenseNo(trdLicno, orgId);
			} else {
				entity = tradeLicenseApplicationRepository.getLicenseDetailsOnlyByLicenseNo(trdLicno);
			}
			if (entity != null) {
				LOGGER.info("Trade Licence Data fetched for license no " + entity.getTrdLicno());
				masDto = mapEntityToDto1(entity);

				List<TradeLicenseOwnerDetailDTO> activeOwnerDetailDTOList = Optional
						.ofNullable(masDto.getTradeLicenseOwnerdetailDTO()).map(Collection::stream)
						.orElseGet(Stream::empty).filter(Objects::nonNull)
						.filter(k -> Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagA))
								|| Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagD)))
						.collect(Collectors.toList());
				masDto.setTradeLicenseOwnerdetailDTO(activeOwnerDetailDTOList);

			} else {
				LOGGER.info("No Trade Licence Data found for license no " + trdLicno);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching License Detail Application ", exception);
			throw new FrameworkException("Exception occur while fetching License Detail Application ", exception);
		}

		return masDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "License details from license no and mobile number", notes = MainetConstants.TradeLicense.LICENSE_DETAILS_BY_LICENSE_NO, response = TradeMasterDetailDTO.class)
	@Path("/getLicenseDetailbyLicAndMobile/{trdLicno}/{mobileNo}/{orgId}")
	public List<TradeMasterDetailDTO> getLicenseDetailbyLicAndMobile(@PathParam(value = "trdLicno") String trdLicno,@PathParam(value = "mobileNo") String mobileNo,
			@PathParam(value = "orgId") Long orgId) {
		List<TradeMasterDetailDTO> masDto = new ArrayList<TradeMasterDetailDTO>();
		List<TbMlTradeMast> entity = new ArrayList<TbMlTradeMast>();
		try {
			entity = iTradeLicenseApplicationDao.getLicenseDetailbyLicAndMobile(trdLicno,mobileNo, orgId);
			if (!entity.isEmpty()) {
				entity.forEach(items ->{
					TradeMasterDetailDTO indentItem = new TradeMasterDetailDTO();
					 BeanUtils.copyProperties(items, indentItem); 
					 masDto.add(indentItem);
				 });

			} else {
				LOGGER.info("No Trade Licence Data found for license no " + trdLicno);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching License Detail Application ", exception);
			throw new FrameworkException("Exception occur while fetching License Detail Application ", exception);
		}

		return masDto;
	}

	@Override
	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@Path("/getLicenseDetailsFromLicenseNo")
	public TradeMasterDetailDTO getLicenseDetailsByLicenseNoMobileApp(@QueryParam(value = "trdLicno") String trdLicno,
			@QueryParam(value = "orgId") Long orgId) {
		// #134456
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		if (org != null && Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			trdLicno = URLDecoder.decode(trdLicno);
		}
		TradeMasterDetailDTO masDto = null;
		TbMlTradeMast entity = null;
		try {
			// User Story #141190
			if (orgId != null && orgId != 0L) {
				entity = tradeLicenseApplicationRepository.getLicenseDetailsByLicenseNo(trdLicno, orgId);
			} else {
				entity = tradeLicenseApplicationRepository.getLicenseDetailsOnlyByLicenseNo(trdLicno);
			}
			if (entity != null) {
				LOGGER.info("Trade Licence Data fetched for license no " + entity.getTrdLicno());
				masDto = mapEntityToDto1(entity);

				List<TradeLicenseOwnerDetailDTO> activeOwnerDetailDTOList = Optional
						.ofNullable(masDto.getTradeLicenseOwnerdetailDTO()).map(Collection::stream)
						.orElseGet(Stream::empty).filter(Objects::nonNull)
						.filter(k -> Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagA))
								|| Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagD)))
						.collect(Collectors.toList());
				masDto.setTradeLicenseOwnerdetailDTO(activeOwnerDetailDTOList);

			} else {
				LOGGER.info("No Trade Licence Data found for license no " + trdLicno);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching License Detail Application ", exception);
			throw new FrameworkException("Exception occur while fetching License Detail Application ", exception);
		}

		return masDto;
	}

	private TradeMasterDetailDTO mapEntityToDto1(TbMlTradeMast masEntity) {

		TradeMasterDetailDTO masDto = new TradeMasterDetailDTO();
		List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
		List<TradeLicenseOwnerDetailDTO> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(masEntity, masDto);
		masEntity.getItemDetails().forEach(itemdDetails -> {
			TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
			BeanUtils.copyProperties(itemdDetails, itemDto);
			/*
			 * if (itemDto.getTriRate() != null) { itemDto.setSelectedItems(true); }
			 */
			itemDto.setMasterTradeId(masDto);
			itemdDetailsList.add(itemDto);
		});
		masEntity.getOwnerDetails().forEach(ownerDetails -> {
			TradeLicenseOwnerDetailDTO ownerDto = new TradeLicenseOwnerDetailDTO();
			BeanUtils.copyProperties(ownerDetails, ownerDto);
			ownerDto.setMasterTradeId(masDto);
			ownerDetailsList.add(ownerDto);
		});

		masDto.setTradeLicenseOwnerdetailDTO(ownerDetailsList);
		masDto.setTradeLicenseItemDetailDTO(itemdDetailsList);

		return masDto;

	}

	@Override
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeDetailsByTrdId(Long trdId, Long orgId) {
		TradeMasterDetailDTO masterDto = null;
		try {
			TbMlTradeMast entity = tradeLicenseApplicationRepository.getTradeDetailsByTrdId(trdId, orgId);
			LOGGER.info("Trade Licence Data fetched for Trade id " + trdId);
			masterDto = mapEntityToDto(entity);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade Details by Trade id ", exception);
		}
		return masterDto;
	}

	@Override
	public boolean validateDataEntrySuite(Long trdId, Long orgId) {
		int countLic = renewalLicenseApplicationService.validateRenewalLicenseCount(trdId, orgId);
		if (countLic > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeLicenseItemDetailDTO> getItemDetailsByTriStatusAndTrdId(Long trdId, Long orgId) {
		List<TradeLicenseItemDetailDTO> dtto = new ArrayList<>();

		List<TbMlItemDetail> item = tradeLicenseApplicationRepository.getItemDetailsByTriStatus(trdId, orgId);

		item.forEach(detil -> {
			TradeLicenseItemDetailDTO dto = new TradeLicenseItemDetailDTO();
			BeanUtils.copyProperties(detil, dto);
			dtto.add(dto);
		});

		return dtto;

	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, notes = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS_RULE, response = TradeMasterDetailDTO.class)
	@Path("/getApplicationChargesFromBrms")
	@Transactional(readOnly = true)
	public TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto)
			throws FrameworkException {
		// app
		Organisation organisation = new Organisation();
		organisation.setOrgid(masDto.getOrgid());

		List<MLNewTradeLicense> masterList = new ArrayList<>();

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);

		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE, masDto.getOrgid());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						organisation.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility
				.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2, masDto.getOrgid()).getLookUpId();

		for (TbTaxMasEntity taxes : taxesMaster) {

			if ((taxes.getTaxCategory2() == appChargetaxId)) {

				MLNewTradeLicense license = new MLNewTradeLicense();
				license.setOrgId(masDto.getOrgid());
				license.setServiceCode(MainetConstants.TradeLicense.SERVICE_CODE);

				TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(masDto.getOrgid(),
						sm.getTbDepartment().getDpDeptid(), taxes.getTaxCode());
				String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
						MainetConstants.FlagE, masDto.getOrgid());

				license.setTaxType(taxType);
				license.setTaxCode(taxes.getTaxCode());
				settingTaxCategories(license, taxes, organisation);
				// license.setRateStartDate(todayDate.getTime());
				license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
				license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
				LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
						organisation);
				license.setLicenseType(licenseType.getDescLangFirst());
				masterList.add(license);
			}

		}

		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start..");
		WSResponseDTO responseDTO = new WSResponseDTO();
		WSRequestDTO wsRequestDTO = new WSRequestDTO();
		List<MLNewTradeLicense> master = new ArrayList<>();

		wsRequestDTO.setDataModel(masterList);

		try {
			LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
			responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE);
			if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				master = setTradeLicenceChargesDTO(responseDTO);
			} else {
				throw new FrameworkException(responseDTO.getErrorMessage());
			}
		} catch (Exception ex) {
			throw new FrameworkException("unable to process request for Trade Licence Fee", ex);
		}
		LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End.");

		setApplicationChargeToDtoList(master, masDto);
		// for Defect #98649
		setFeeIds(master, taxesMaster, masDto);
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SUDA)) {
			try {
				masDto.setCheckApptimeCharge(MainetConstants.FlagY);
				TradeMasterDetailDTO dto = getTradeLicenceChargesFromBrmsRule(masDto);
				if (dto != null) {
					if (dto.getLoiFee() != null)
						masDto.setLoiFee(dto.getLoiFee());
					masDto.setTotalApplicationFee(new BigDecimal(masDto.getApplicationCharge()));
				}
			} catch (Exception e) {
				LOGGER.info(
						"Error occured while fetching Loi Charges in method getTradeLicenceAppChargesFromBrmsRule()");
			}
		}
		return masDto;
	}

//This mehod is added for Setting FeeIds for portal side Defect #98649
	private void setFeeIds(List<MLNewTradeLicense> master, List<TbTaxMasEntity> taxesMaster,
			TradeMasterDetailDTO masDto) {
		Map<Long, Double> map = new HashMap();
		master.forEach(mast -> {
			taxesMaster.forEach(taxMast -> {

				map.put(taxMast.getTaxId(), mast.getFlatRate());

			});
		});
		masDto.setFeeIds(map);

	}

	public Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId,
			TradeMasterDetailDTO tradeMasterDto) {

		Long licenseMaxTenureDays = 0l;
		Date currentDate = new Date();
		if (licToDate != null && Utility.compareDate(new Date(), licToDate)) {
			currentDate = licToDate;
		}
		List<LicenseValidityMasterDto> licValMasterDtoList = new ArrayList<>();
		List<LicenseValidityMasterDto> licValidityMster = new ArrayList<LicenseValidityMasterDto>();
		// added for system should have provision to define Item Category and
		// Sub-category in the license validity master User Story #113614
		String skdclEnv = checkEnviorement();
		if (skdclEnv != null && skdclEnv.equals(MainetConstants.ENV_SKDCL)
				&& !tradeMasterDto.getTradeLicenseItemDetailDTO().isEmpty()) {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					tradeMasterDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), MainetConstants.ZERO_LONG);
		} else {
			licValidityMster = licenseValidityMasterService.searchLicenseValidityData(orgId, deptId, serviceId,
					MainetConstants.ZERO_LONG, MainetConstants.ZERO_LONG);
		}
		if (CollectionUtils.isNotEmpty(licValidityMster)) {

			licValMasterDtoList = licValidityMster.stream()
					.filter(k -> (k.getLicType() == tradeMasterDto.getTrdLictype().longValue()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(licValMasterDtoList) || licValMasterDtoList.size() <= 0) {

				return null;
			}

			LicenseValidityMasterDto licValMasterDto = licValMasterDtoList.get(0);

			Organisation organisationById = ApplicationContextProvider.getApplicationContext()
					.getBean(IOrganisationService.class).getOrganisationById(orgId);
			LookUp dependsOnLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(licValMasterDto.getLicDependsOn(), organisationById);

			LookUp unitLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(licValMasterDto.getUnit(),
					organisationById);

			Long tenure = Long.valueOf(licValMasterDto.getLicTenure());
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagD)) {
				licenseMaxTenureDays = tenure - 1;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagH)) {
				licenseMaxTenureDays = 1l;
			}
			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagM)) {
				int currentYear = Integer.valueOf(Year.now().toString());
				Month monthObject = Month.from(LocalDate.now());
				int month = monthObject.getValue();
				licenseMaxTenureDays = Long.valueOf(YearMonth.of(currentYear, month).lengthOfMonth());
				if (tenure > 1) {
					for (int i = 2; i <= tenure; i++) {
						licenseMaxTenureDays = licenseMaxTenureDays
								+ Long.valueOf(YearMonth.of(currentYear, ++month).lengthOfMonth());
						if (month == 12) {
							month = 0;
							currentYear++;
						}
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(unitLookUp.getLookUpCode(), MainetConstants.FlagY)) {
				if (StringUtils.equalsIgnoreCase(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagF)) {
					int month = 0;
					int currentYear = Integer.valueOf(Year.now().toString());
					TbFinancialyear financialYear;
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int monthValue = currLocalDate.getMonthValue();
					int currentMonthValue = currLocalDate.getMonthValue();

					if (monthValue > 3 && monthValue <= 12) {

						for (int i = monthValue; i <= 15; i++) {

							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;

								monthValue++;

							} else {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}

							}
						}

					} else {
						for (int i = monthValue; i <= 3; i++) {
							if (i == currentMonthValue) {
								LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
								Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
								Long valueOf = Long.valueOf(Utility.getDaysBetweenDates(currentDate, date));

								licenseMaxTenureDays = valueOf;
								monthValue++;
								// Long currMonthDays = Long.valueOf(YearMonth.of(currentYear,
								// monthValue).lengthOfMonth());
							} else {
								licenseMaxTenureDays = licenseMaxTenureDays
										+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
								monthValue++;
							}

						}
					}
					if (tenure > 1) {
						for (int i = 2; i <= tenure; i++) {
							monthValue = 4;
							currentYear++;
							month = 0;
							for (int j = monthValue; j <= 15; j++) {
								if (monthValue <= 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(currentYear, monthValue).lengthOfMonth());
									monthValue++;
								} else if (monthValue > 12) {
									licenseMaxTenureDays = licenseMaxTenureDays
											+ Long.valueOf(YearMonth.of(++currentYear, ++month).lengthOfMonth());
									monthValue++;
									--currentYear;
								}
							}
						}
					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagC)) {
					int currentYear = Integer.valueOf(Year.now().toString());
					LocalDate currLocalDate = LocalDate.now();
					LocalDate with = currLocalDate.with(lastDayOfYear());

					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate,
							Date.from(with.atStartOfDay(ZoneId.systemDefault()).toInstant())));
					if (tenure > 1) {

						for (int i = 2; i <= tenure; i++) {
							Year year = Year.of(++currentYear);
							licenseMaxTenureDays = licenseMaxTenureDays + Long.valueOf(year.length());
						}

					}
				}
				if (StringUtils.equals(dependsOnLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					LocalDate currLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					// Defect #36796
					int leapYear = 0;

					if (currLocalDate.getMonthValue() == 2 && currLocalDate.getDayOfMonth() == 29) {
						leapYear = 1;
					}
					Instant instant1 = LocalDate
							.of(currLocalDate.getYear() + Integer.valueOf(tenure.toString()),
									currLocalDate.getMonthValue(), currLocalDate.getDayOfMonth() - leapYear)
							.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Date from1 = Date.from(instant1);
					licenseMaxTenureDays = Long.valueOf(Utility.getDaysBetweenDates(currentDate, from1)) - 1;

				}
			}
		}

		Date renwalToDate = Utility.getAddedDateBy2(new Date(), licenseMaxTenureDays.intValue());
		System.out.println(renwalToDate);

		return licenseMaxTenureDays;

	}

	@Transactional(readOnly = true)
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/getServiceMasterByServiceCode/orgId/{orgId}/serviceShortName/{serviceShortName}")
	public Map<String, String> getServiceMasterByServiceCode(@PathParam("orgId") Long orgId,
			@PathParam("serviceShortName") String serviceShortName) {

		Map<String, String> map = new LinkedHashMap<String, String>();
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceByShortName(serviceShortName, orgId);
		if (serviceMaster != null) {

			LookUp checkListApplLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(serviceMaster.getSmChklstVerify(), ApplicationContextProvider
							.getApplicationContext().getBean(IOrganisationService.class).getOrganisationById(orgId));

			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				map.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				map.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				map.put("applicationchargeApplFlag", MainetConstants.FlagY);
			} else {
				map.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}
			if (null != serviceMaster.getSmServiceDuration())
			map.put("smServiceDuration", String.valueOf(serviceMaster.getSmServiceDuration()));
		}

		return map;
	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.CHECK_WORKFLOW_EXIST_OR_NOT, notes = MainetConstants.TradeLicense.CHECK_WORKFLOW_EXIST_OR_NOT, response = Boolean.class)
	@Path("/checkWorkFlowExistOrNot")
	@Transactional(readOnly = true)
	@ResponseBody
	public Boolean checkWoflowDefinedOrNot(TradeMasterDetailDTO mastDto) {
		WorkflowMas mas = null;
		Boolean flag = true;
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE, mastDto.getOrgid());
		
		Organisation organisation = iOrganisationService.getOrganisationById(mastDto.getOrgid());
		LookUp lookup1 = null;
		try {
			lookup1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WWC", "LCC", 1,
					organisation);
		} catch (Exception e) {
			LOGGER.error("No prefix found for for workflow based on category ", e);
		}
		try {
			if(lookup1 != null && lookup1.getOtherField() != null && lookup1.getOtherField().equals("Y")) {
				mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(
					mastDto.getOrgid(),
					deptId, sm.getSmServiceId(),null, null,
					mastDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1() ,
					mastDto.getTrdWard1(), mastDto.getTrdWard2(), mastDto.getTrdWard3(),
					mastDto.getTrdWard4(), mastDto.getTrdWard5());
				flag = false;
			}
			else {
				
				mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(mastDto.getOrgid(), deptId,
						sm.getSmServiceId(), mastDto.getTrdWard1(), mastDto.getTrdWard2(), mastDto.getTrdWard3(),
						mastDto.getTrdWard4(), mastDto.getTrdWard5());
				flag = false;
			  }
			 
		} catch (Exception e) {

		}
		if (mas == null || mas.equals(MainetConstants.BLANK)) {
			return flag;
		}
		return flag;
	}

	@Override
	public List<TradeLicenseItemDetailDTO> getTradeLicenseHistDetBuTrdId(Long triId) {
		// TODO Auto-generated method stub
		List<TradeLicenseItemDetailDTO> itemDto = new ArrayList<TradeLicenseItemDetailDTO>();
		List<Object[]> detHistEnt = tradeDetHistRepository.getItemDetailsHistByTrdId(triId);
		for (Object[] s1 : detHistEnt) {
			TradeLicenseItemDetailDTO dto = new TradeLicenseItemDetailDTO();
			dto.setTriId(Long.valueOf(s1[0].toString()));
			dto.setTriCod1(Long.valueOf(s1[1].toString()));
			dto.setTriCod2(Long.valueOf(s1[2].toString()));
			dto.setTriCod3(Long.valueOf(s1[3].toString()));
			dto.setTriCod4(Long.valueOf(s1[4].toString()));
			dto.setTriStatus(s1[5].toString());
			dto.setTrdUnit(new BigDecimal(s1[6].toString()));
			itemDto.add(dto);
		}
		return itemDto;
	}

	@Override
	public String getApprovedBuisnessName(String licenseNo, long orgId, Long lookUpId) {

		List<TbMlTradeMastHist> trdHistList = tradeDetailsHistRepo.getTradeDetailsHistByLicNo(licenseNo);
		List<TbMlTradeMastHist> trdList = new ArrayList<TbMlTradeMastHist>();
		trdHistList.forEach(trd -> {
			if (trd.getTrdStatus().equals(lookUpId) || trd.getTrdStatus() == lookUpId) {
				trdList.add(trd);

			}
		});

		if (trdList != null && !trdList.isEmpty() && trdList.size() != 0)
			if (trdList.size() > 1) {
				return trdList.get(trdList.size() - 1).getTrdBusnm();
			} else {
				return trdList.get(0).getTrdBusnm();
			}
		return null;
	}

	// Added this method for saving history data in TB_ML_TRADE_MST_HIST Table
	@Override
	public void saveTradeLicensePrintingData(TbMlTradeMast masEntity) {
		// TODO Auto-generated method stub
		tradeLicenseApplicationRepository.save(masEntity);
		saveHistoryData(masEntity);
	}

	// Defect #112673
	@Override
	public Map<String, Long> getApplicationNumberByRefNo(String licenseNo, Long serviceId, Long orgId, Long empId) {
		Map<String, Long> outputMap = new HashMap<>();
		outputMap.put(MainetConstants.Objection.APPLICTION_NO,
				tradeLicenseApplicationRepository.getApplicationNumberByRefNo(licenseNo, orgId));
		outputMap.put(MainetConstants.Objection.PERIOD, null);
		return outputMap;
	}

	@Override
	public String checkEnviorement() {
		try {

			List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.TradeLicense.TRD_ENV,
					UserSession.getCurrent().getOrganisation());
			if (lookupList != null && !lookupList.isEmpty()) {
				String env = lookupList.get(0).getLookUpCode();
				if (env != null && !env.isEmpty()) {
					return env;
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	// Defect #112673
	@Override
	public boolean checkLicensePresent(String referenceNo, Long orgId) {
		Boolean licensePresent = tradeLicenseApplicationRepository.checkRefNoValidOrNot(referenceNo, orgId);
		if (licensePresent) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public Map<String, Long> getItemDetailsByApplicationId(Long applicationId) {
		LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
		Map<String, Long> map = new HashMap<String, Long>();
		try {

			TbMlTradeMast entity = tradeLicenseApplicationRepository
					.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
			List<TradeLicenseItemDetailDTO> itemDto = getItemDetailsByTriStatusAndTrdId(entity.getTrdId(),
					entity.getOrgid());
			if (itemDto != null && !itemDto.isEmpty()) {
				map.put(MainetConstants.TRI_COD1, itemDto.get(0).getTriCod1());
				map.put(MainetConstants.TRI_COD2, itemDto.get(0).getTriCod2());
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return map;
	}

	@Override
	public List<TradeMasterDetailDTO> getLicenseDetByCatAndDate(Long triCod1, Long triCod2, Date fromDate, Date toDate,
			Long orgId) {
		List<TradeMasterDetailDTO> dtoList = new ArrayList<>();
		List<Object[]> list = tradeLicenseApplicationRepository.getLicenseDetByCatAndDate(triCod1, triCod2, fromDate,
				toDate, orgId);
		for (Object[] obj : list) {
			TradeMasterDetailDTO dto = new TradeMasterDetailDTO();
			dto.setTrdLicno(obj[0].toString());
			if (obj[1] != null)
				dto.setLicenseType(obj[1].toString());
			if (obj[2] != null)
				dto.setLicenceTypeReg(obj[2].toString());
			if (obj[3] != null)
				dto.setTrdOwnerNm(obj[3].toString());
			if (obj[4] != null)
				dto.setLiceCategory(obj[4].toString());
			if (obj[5] != null)
				dto.setLicCatgoryReg(obj[5].toString());
			if (obj[6] != null)
				dto.setLiceSubCategory(obj[6].toString());
			if (obj[7] != null)
				dto.setLicSubCatReg(obj[7].toString());
			if (obj[8] != null)
				dto.setLicenseFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(obj[8]));
			if (obj[9] != null)
				dto.setLicenseToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(obj[9]));
			if (obj[10] != null)
				dto.setRenewalPendingDays(Long.valueOf(obj[10].toString()));
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Transactional(readOnly = true)
	@Consumes("application/json")
	@POST
	@ApiOperation(value = MainetConstants.TradeLicense.CHECK_APP_REJECT_ORNOT, notes = MainetConstants.TradeLicense.CHECK_APP_REJECT_ORNOT, response = Boolean.class)
	@Path("/checkAppRejectOrNot/{applicationId}/{orgId}")
	@Override
	public Boolean checkRejectedInTaskflow(@PathParam(value = "applicationId") Long applicationId,
			@PathParam(value = "orgId") Long orgId) {
		Long serviceId = serviceMasterService.getServiceIdByShortName(orgId,
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
		int count = tbCfcRepo.checkApplicationIsRejectedOrNot(applicationId, serviceId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public CommonChallanDTO getLicenseDetailsByAppIdAndOrgId(Long applicationId, Long orgId) {
		CommonChallanDTO challanDto = new CommonChallanDTO();
		TradeMasterDetailDTO masDto = new TradeMasterDetailDTO();
		try {
			List<String> ownerNameList = new ArrayList<String>();
			List<Object[]> entity = tradeLicenseApplicationRepository.getApplicantName(applicationId, orgId);
			if (entity != null) {
				LOGGER.info("Trade Licence Data fetched for ");
				for (Object[] str : entity) {
					if (str[0] != null)
						ownerNameList.add(str[0].toString());
				}
				StringBuilder ownName = new StringBuilder();
				String ownerName = "";
				for (String ownDto : ownerNameList) {
					if (StringUtils.isNotBlank(ownDto))
						ownName.append(ownDto + " " + MainetConstants.operator.COMMA);
				}
				if (ownName.length() > 0) {
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				}
				if (StringUtils.isNotBlank(ownerName)) {
					challanDto.setApplicantName(ownerName);
				}
			} else {
				LOGGER.info("No Trade Licence Data found for applicationId no " + applicationId);
			}
		} catch (Exception e) {
			throw new FrameworkException(e.getMessage());
		}
		return challanDto;
	}

	// 105334 - to get license end date by ApplicationId
	@Transactional
	@Override
	public String getLicenseDetailsByApplId(Long applicationId) {
		LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
		String licenseEndDate = null;
		try {
			TbMlTradeMast entity = tradeLicenseApplicationRepository
					.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
			if (entity != null && entity.getTrdLictoDate() != null)
				licenseEndDate = Utility.dateToString(entity.getTrdLictoDate());
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return licenseEndDate;
	}

	@Override
	public String getOwnersName(TradeMasterDetailDTO tradeMasterDto, String flagy) {
		List<TradeLicenseOwnerDetailDTO> ownerNameDto = tradeMasterDto.getTradeLicenseOwnerdetailDTO().stream()
				.filter(ownDto -> ownDto != null
						&& (ownDto.getTroPr().equals(flagy) || ownDto.getTroPr().equals(MainetConstants.FlagD)))
				.collect(Collectors.toList());
		String ownerName = "";
		if (!CollectionUtils.isEmpty(ownerNameDto)) {
			StringBuilder ownName = new StringBuilder();

			for (TradeLicenseOwnerDetailDTO ownDto : ownerNameDto) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (StringUtils.isNotBlank(ownName.toString())) {
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
		}
		return ownerName;
	}

	// 123943
	@Override
	public CommonChallanDTO getLicenseDetails(Long applicationId, Long orgId) {
		CommonChallanDTO challanDto = new CommonChallanDTO();
		TradeMasterDetailDTO masDto = new TradeMasterDetailDTO();
		TbMlTradeMast entity = tradeLicenseApplicationRepository.getLicenseDetailsByAppIdAndOrgId(applicationId, orgId);
		if (entity != null) {
			BeanUtils.copyProperties(entity, masDto);
			challanDto.setLicNo(entity.getTrdLicno());
			challanDto.setFromedate(entity.getTrdLicfromDate());
			challanDto.setToDate(entity.getTrdLictoDate());
		}
		return challanDto;

	}

	@Consumes("application/json")
	@Transactional(readOnly = true)
	@POST
	@Path("/getOwnerList/{trdLicno}/{orgId}")
	@Override
	public List<TradeLicenseOwnerDetailDTO> getOwnerListByLicNoAndOrgId(@PathParam(value = "trdLicno") String LicNo,
			@PathParam(value = "orgId") Long orgId) {
		TradeMasterDetailDTO masterDto = null;
		List<TradeLicenseOwnerDetailDTO> ownerList = new ArrayList<TradeLicenseOwnerDetailDTO>();
		TbMlTradeMast tradeMast = tradeLicenseApplicationRepository.getLicenseDetailsByLicenseNo(LicNo, orgId);
		if (tradeMast != null) {
			masterDto = getTradeDetailsByTrdId(tradeMast.getTrdId(), orgId);
			if (masterDto != null) {
				ownerList.addAll(masterDto.getTradeLicenseOwnerdetailDTO().stream()
						.filter(td -> td != null && (td.getTroPr().equals(MainetConstants.FlagA)
								|| td.getTroPr().equals(MainetConstants.FlagY)
								|| td.getTroPr().equals(MainetConstants.FlagD)))
						.collect(Collectors.toList()));
			}

		}
		return ownerList;

	}

	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "To get FeesId", notes = "To get FeesId", response = CommonChallanDTO.class)
	@Path("/getFeesId")
	@Transactional
	public CommonChallanDTO getFeesId(TradeMasterDetailDTO tradeMaster) {
		CommonChallanDTO offline = new CommonChallanDTO();
		Organisation org = iOrganisationService.getOrganisationById(tradeMaster.getOrgid());
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, org);

		final List<TbTaxMasEntity> taxesMaster = tbTaxMasService.fetchAllApplicableServiceCharge(
				tradeMaster.getServiceId(), org.getOrgid(), chargeApplicableAt.getLookUpId());

		long appChargetaxId = CommonMasterUtility
				.getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid()).getLookUpId();

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			if (tbTaxMas.getTaxCategory2() == appChargetaxId) {
				offline.getFeeIds().put(tbTaxMas.getTaxId(), Double.valueOf(tradeMaster.getApplicationCharge()));
			}
		}

		return offline;

	}

	@Override
	public void sendSmsEmail(TradeMasterDetailDTO masDto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = iOrganisationService.getOrganisationById(masDto.getOrgid());
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE, org.getOrgid());
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "TradeApplicationForm.html";
		org.setOrgid(masDto.getOrgid());
		int langId = masDto.getLangId().intValue();
		smsDto.setUserId(masDto.getUserId());
        //D#142339
		if(masDto.getPmPropNo()!=null) {
			sendSmsEmailToWardZoneOfficer(masDto,org);
		}
		ismsAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
	}
    //D#142339
	private void sendSmsEmailToWardZoneOfficer(TradeMasterDetailDTO masDto,Organisation org) {
		TradeMasterDetailDTO dto=getPropertyDetailsByPropertyNumber(masDto);
		List<Object> empIdList=new ArrayList<Object>();
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE, org.getOrgid());
		smsDto.setServName(sm.getSmServiceName());
		String url = "TradeApplicationFormSms.html";
		org.setOrgid(masDto.getOrgid());
		int langId = masDto.getLangId().intValue();
		smsDto.setLangId(langId);
		try {
			if(dto!=null) {
				empIdList=iTradeLicenseApplicationDao.getEmployeeIdList(org.getOrgid(), dto.getTrdEWard1(), dto.getTrdEWard2(), dto.getTrdEWard3(), dto.getTrdEWard4(), dto.getTrdEWard5());
			if(CollectionUtils.isNotEmpty(empIdList)) {
				for(Object empId:empIdList) {
					Employee emp=employeeRepository.findEmployeeByIdAndOrgId(Long.valueOf(empId.toString()), org.getOrgid());
					smsDto.setMobnumber(emp.getEmpmobno());
					smsDto.setEmail(emp.getEmpemail());
					smsDto.setOrgId(org.getOrgid());
					smsDto.setUserId(Long.valueOf(empId.toString()));
					ismsAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
							PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsDto, org, langId);
					LOGGER.info("Sms and Email send to Wardzone officer Successfully ");
				}
			}else {
				LOGGER.info("No employee found ");
			
			}
		}}
		catch (Exception e) {
			LOGGER.error("Exception occure at the time of fetching wardzone mapping employee"+e);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> getpaymentModeByRcptId(Long orgId, long loiNo) {

		List<TradeMasterDetailDTO> tradeMasterDetailDtoList = new ArrayList<>();
		List<Object[]> licenseApproval = iTradeLicenseApplicationDao.getpaymentModeByRcptId(orgId, loiNo);
		TradeMasterDetailDTO tradeMasterDetailDTO = null;
		for (Object[] license : licenseApproval) {

			tradeMasterDetailDTO = new TradeMasterDetailDTO();
			tradeMasterDetailDTO.setRmRcptno((Long) license[0]);
			tradeMasterDetailDTO.setRmDate((Date) license[1]);
			tradeMasterDetailDTO.setRmAmount((BigDecimal) license[2]);
			tradeMasterDetailDTO.setCpdDesc((String) license[3]);
			tradeMasterDetailDTO.setCpdDescMar((String) license[4]);
			tradeMasterDetailDTO.setRmLoiNo((String) license[5]);
			tradeMasterDetailDTO.setApmApplicationId((Long) license[6]);
			tradeMasterDetailDTO.setCpdValue((String) license[7]);
			tradeMasterDetailDTO.setCheckStatus((Long) license[8]);
			tradeMasterDetailDtoList.add(tradeMasterDetailDTO);
		}

		return tradeMasterDetailDtoList;
	}

	// Forv checking is Account module live or not
	@Override
	public Boolean checkAccountActiveOrNot() {
		boolean accountCodeStatus = false;
		try {
			LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
			if (defaultVal != null) {
				String accountCode = defaultVal.getLookUpCode();
				if (accountCode.equals(MainetConstants.FlagL)) {
					accountCodeStatus = true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("SLI Prefi not found");
			return false;
		}
		return accountCodeStatus;
	}

	@Override
	@Transactional
	public Boolean updateStatusAfterDishonurEntry(Long appId, Long orgId) {
		Organisation org = iOrganisationService.getOrganisationById(orgId);
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", org);
		TbMlTradeMast entity = tradeLicenseApplicationRepository.getLicenseDetailsByAppIdAndOrgId(appId, orgId);
		entity.setTrdStatus(lookUp.getLookUpId());
		tradeLicenseApplicationRepository.save(entity);
		return true;
	}

	@Override
	public void setApplTimeDishonurChargeAmt(TradeMasterDetailDTO trdDto, String servShortCode) {
		DishonourChargeEntity disHnrEnt = null;
		Organisation organisation = iOrganisationService.getOrganisationById(trdDto.getOrgid());
		List<Long> appList = tradeDetailsHistRepo.getTradeRenewalAppHist(trdDto.getTrdLicno(), servShortCode);
		if (CollectionUtils.isNotEmpty(appList)) {
			disHnrEnt = tbDisHnrRepo.getDishonourChargeData(appList.get(0));
		}
		if (disHnrEnt != null && (disHnrEnt.getIsDishnrChgPaid() != null
				&& disHnrEnt.getIsDishnrChgPaid().equals(MainetConstants.FlagN))) {
			trdDto.getFeeIds().put(disHnrEnt.getTaxId(), disHnrEnt.getDisHnAm().doubleValue());
			List<TbTaxMasEntity> taxesMaster = null;
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.TradeLicense.APL, PrefixConstants.NewWaterServiceConstants.CAA, organisation);
			LookUp lookUp1 = CommonMasterUtility.getHieLookupByLookupCode("CDC", "TAC", 2, trdDto.getOrgid());
			Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
			try {
				// fetching dishonur tax details by deptId ,orgid and subcategoryId
				taxesMaster = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(trdDto.getOrgid(), deptId,
						chargeApplicableAt.getLookUpId(), lookUp1.getLookUpId());
			} catch (Exception e) {
				// TODO: handle exception
			}

			trdDto.getChargesInfo().put(taxesMaster.get(0).getTaxDesc(), disHnrEnt.getDisHnAm().doubleValue());
			trdDto.setTotalApplicationFee(trdDto.getTotalApplicationFee().add(disHnrEnt.getDisHnAm()));
			trdDto.setApplicationCharge(trdDto.getTotalApplicationFee().toString());
		}
	}

	@Override
	public boolean generateCertificate(String docPath, Long applicationNo, String certificateNo, Long orgId,
			TradeMasterDetailDTO tradeMasterDetailDTO) {
		LOGGER.info("generateCertificate method started ------------------------>");
		Organisation org = UserSession.getCurrent().getOrganisation();
		tradeMasterDetailDTO.setLicenseFromDate(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
		tradeMasterDetailDTO.setLicenseToDate(Utility.dateToString(tradeMasterDetailDTO.getTrdLictoDate()));
		if (tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriRate() != null)
			tradeMasterDetailDTO
					.setCpdValue(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriRate().toString());

		LookUp licCat = CommonMasterUtility
				.getHierarchicalLookUp(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);
		LookUp licSubCat = CommonMasterUtility
				.getHierarchicalLookUp(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod2(), orgId);
		tradeMasterDetailDTO.setLiceCategory(licCat.getLookUpDesc());
		tradeMasterDetailDTO.setLiceSubCategory(licSubCat.getLookUpDesc());

		if (tradeMasterDetailDTO.getTrdWard1() != null) {
			tradeMasterDetailDTO.setWard1Level(CommonMasterUtility
					.getHierarchicalLookUp(tradeMasterDetailDTO.getTrdWard1(), org).getDescLangFirst());
		}
		if (tradeMasterDetailDTO.getTrdWard2() != null) {
			tradeMasterDetailDTO.setWard2Level(CommonMasterUtility
					.getHierarchicalLookUp(tradeMasterDetailDTO.getTrdWard2(), org).getDescLangFirst());
		}
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			List<TbApprejMas> apprejMasList = new ArrayList<>();
			List<RenewalHistroyDetails> renHistDetails  = new ArrayList<>();
			Long serviceId = iCFCApplicationMasterDAO.getServiceIdByApplicationId(applicationNo, orgId);
			ServiceMaster sm;
			if (serviceId != null) {
				sm = serviceMasterService.getServiceMaster(serviceId, orgId);
				if (sm != null)
					tradeMasterDetailDTO.setServiceName(sm.getSmServiceNameMar());
				List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(applicationNo, sm.getSmServiceId(),
						tradeMasterDetailDTO.getOrgid());
				List<TbLoiDetEntity> loiDetails = new ArrayList<TbLoiDetEntity>();

				// For Resovling AIOB Exception
				if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
					loiDetails = tbLoiDetService.findLoiDetailsByLoiIdAndOrgId(tbLoiMas.get(0).getLoiId(), orgId);
				}

				if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
					tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
					List<TradeMasterDetailDTO> dto = getpaymentMode(orgId, tbLoiMas.get(0).getLoiNo());

					if (dto != null && !dto.isEmpty()) {
						tradeMasterDetailDTO.setRmRcptno(dto.get(0).getRmRcptno());
						tradeMasterDetailDTO.setRmAmount(dto.get(0).getRmAmount());
						if (dto.get(0).getSecurityDepositeAmt() != null)
							tradeMasterDetailDTO.setSecurityDepositeAmt(dto.get(0).getSecurityDepositeAmt());
						if (dto.get(0).getLicenseFee() != null)
							tradeMasterDetailDTO.setLicenseFee(dto.get(0).getLicenseFee());
					}
				} else {
					List<TradeMasterDetailDTO> dto = getpaymentModeByAppId(orgId, applicationNo);
					if (dto != null && !dto.isEmpty()) {
						tradeMasterDetailDTO.setRmRcptno(dto.get(0).getRmRcptno());
						tradeMasterDetailDTO.setRmAmount(dto.get(0).getRmAmount());
						tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(dto.get(0).getRmDate()));
						if (dto.get(0).getSecurityDepositeAmt() != null)
							tradeMasterDetailDTO.setSecurityDepositeAmt(dto.get(0).getSecurityDepositeAmt());
					}
				}

				Long artId = 0l;
				final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
						UserSession.getCurrent().getOrganisation());
				for (final LookUp Lookup1 : lookUpList) {

					if (Lookup1.getLookUpCode().equals(PrefixConstants.WATERMODULEPREFIX.APP)) {
						artId = Lookup1.getLookUpId();
					}
				}

				apprejMasList = tbApprejMasService.findByRemarkType(serviceId, artId);
			
				if(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(sm.getSmShortdesc()))
					renHistDetails = renewalLicenseApplicationService.getRenewalHistoryDetails(tradeMasterDetailDTO.getTrdLicno(), orgId);
			}
			return generatePdfCertificateSKDCL(docPath, tradeMasterDetailDTO, orgId, apprejMasList,renHistDetails);
		} else
			return generatePdfCertificate(docPath, tradeMasterDetailDTO, orgId);	
	}

	public boolean generatePdfCertificateSKDCL(String docPath, TradeMasterDetailDTO tradeMasterDetailDTO, Long orgId,
			List<TbApprejMas> apprejMasList,List<RenewalHistroyDetails> renHistDetails) {

		LOGGER.info("generateCertificate PFD method started ------------------------>");
		final String MANGAL = ApplicationSession.getInstance().getMessage("mangalFont.path");
		final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");

		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);

			Table table = new Table(new float[] { 166, 166, 168 }) // in points
					.setWidth(500); // 100 pt

			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(4);
			b1.setColor(ColorConstants.ORANGE);
			//table.setBorder(b1);
			
			
		

			Cell r1Cell1 = new Cell();
			ImageData leftImgData = ImageDataFactory
					.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(95f);
			logoImg.setWidth(70f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell1.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell();
			ImageData orgNameData = ImageDataFactory.create(
					Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.orgName"),
							MANGAL, 15f, false));
			Image orgNameImg = new Image(orgNameData);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.LEFT));
			ImageData headingData = ImageDataFactory.create(
					Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.deptName"),
							MANGAL, 15f, false));
			Image headingImg = new Image(headingData);
			r1Cell2.add(headingImg.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			r1Cell3.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell3.setPadding(PADDING));

			Cell keyValPara1Cell1 = new Cell();
			keyValPara1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell1.setBorder(Border.NO_BORDER);
			ImageData para1tableCell1Data = ImageDataFactory.create(
					Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.licno")
							+ tradeMasterDetailDTO.getTrdLicno(), ARIALUNICODE, TEXT_SIZE_10, false));
			Image para1tableCell1Img = new Image(para1tableCell1Data);
			para1tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para1tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara1Cell1.add(para1tableCell1Img);
			table.addCell(keyValPara1Cell1);

			Cell keyValPara1Cell2 = new Cell();
			keyValPara1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell2.setBorder(Border.NO_BORDER);
			ImageData para1tableCell2Data = ImageDataFactory.create(
					Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.ward")
							+ tradeMasterDetailDTO.getWard2Level(), ARIALUNICODE, TEXT_SIZE_10, false));
			Image para1tableCell2Img = new Image(para1tableCell2Data);
			para1tableCell2Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para1tableCell2Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara1Cell2.add(para1tableCell2Img);
			table.addCell(keyValPara1Cell2);

			Cell keyValPara1Cell3 = new Cell();
			keyValPara1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell3.setBorder(Border.NO_BORDER);
			ImageData para1tableCell3Data = ImageDataFactory.create(Utility.textToImage(
					(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.date")
							+ new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date())),
					ARIALUNICODE, TEXT_SIZE_10, false));
			Image para1tableCell3Img = new Image(para1tableCell3Data);
			para1tableCell3Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para1tableCell3Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara1Cell3.add(para1tableCell3Img);
			table.addCell(keyValPara1Cell3);

	

			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
			
			Cell keyValPara2Cell1 = new Cell(1, 3);
			keyValPara2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara2Cell1.setBorder(Border.NO_BORDER);
			ImageData para2tableCell1Data = ImageDataFactory.create(
					Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.note1")),
							ARIALUNICODE, TEXT_SIZE_9, false));
			Image para2tableCell1Img = new Image(para2tableCell1Data);
			para2tableCell1Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para2tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para2tableCell1Img.setMarginLeft(0.5f);
			keyValPara2Cell1.add(para2tableCell1Img);
			table.addCell(keyValPara2Cell1);

			Cell keyValPara2Cell2 = new Cell(1, 3);
			keyValPara2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara2Cell2.setBorder(Border.NO_BORDER);
			ImageData para2tableCell2Data = ImageDataFactory.create(
					Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.note2")),
							ARIALUNICODE, TEXT_SIZE_9, false));
			Image para2tableCell2Img = new Image(para2tableCell2Data);
			para2tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para2tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValPara2Cell2.add(para2tableCell2Img);
			table.addCell(keyValPara2Cell2);

		

			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

			Cell r4Cell1 = new Cell(1, 3);
			ImageData serviceName = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.note3"), MANGAL, 11f, false));
			Image serviceNameImg = new Image(serviceName);
			r4Cell1.add(serviceNameImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			r4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r4Cell1.setBorder(Border.NO_BORDER);
			r4Cell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r4Cell1.setTextAlignment(TextAlignment.CENTER);
			table.addCell(r4Cell1);
			
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			

			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow1Cell1Data = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.ownerName"), MANGAL,
					TEXT_SIZE_10, false));
			Image keyValRow1Cell1Img = new Image(keyValRow1Cell1Data);
			keyValRow1Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow1Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow1Cell1Img.setMarginLeft(5f);
			keyValRow1Cell1.add(keyValRow1Cell1Img);
			table.addCell(keyValRow1Cell1);
			
			
			

			String trdOwnerName = tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName() + " "
					+ tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMname();
			Cell keyValRow2Cell1 = new Cell(1, 2);
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell1Data = ImageDataFactory
					.create(Utility.textToImage(trdOwnerName, ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow2Cell1Img = new Image(keyValRow2Cell1Data);
			keyValRow2Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell1Img.setMarginLeft(5f);
			keyValRow2Cell1.add(keyValRow2Cell1Img);
			table.addCell(keyValRow2Cell1);
			
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			

			Cell keyValRow2Cell2 = new Cell();
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell2Data = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.busNameAndAdd"), ARIALUNICODE,
					TEXT_SIZE_10, false));
			Image keyValRow2Cell2Img = new Image(keyValRow2Cell2Data);
			keyValRow2Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell2Img.setMarginLeft(5f);
			keyValRow2Cell2.add(keyValRow2Cell2Img);
			table.addCell(keyValRow2Cell2);

			String busNameAndAdd = tradeMasterDetailDTO.getTrdBusnm() + " " + tradeMasterDetailDTO.getTrdBusadd();
			Cell keyValRow2Cell3 = new Cell(1, 2);
			keyValRow2Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell3.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell3Data = ImageDataFactory
					.create(Utility.textToImage(busNameAndAdd, ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow2Cell3Img = new Image(keyValRow2Cell3Data);
			keyValRow2Cell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell3Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell3Img.setMarginLeft(5f);
			keyValRow2Cell3.add(keyValRow2Cell3Img);
			table.addCell(keyValRow2Cell3);

			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
		
			
				
		
			
			
			Table table2 = new Table(new float[] { 100, 100, 100, 100, 100 }) // in points
					.setWidth(500); // 100 pt

			table2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b2 = new DoubleBorder(2);
			b2.setColor(ColorConstants.BLACK);
			table2.setBorder(b2);
			
			
			Cell rcell = new Cell(1,5);
			rcell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData CellData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.businessDetails"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image CellDataImg = new Image(CellData);
			CellDataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			CellDataImg.setTextAlignment(TextAlignment.LEFT);
			rcell.add(CellDataImg);
			table2.addCell(rcell);
			
			
			Cell keyValCell1 = new Cell();
			keyValCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.srNo"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg = new Image(Cell1Data);
			Cell1DataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg.setTextAlignment(TextAlignment.LEFT);
			keyValCell1.add(Cell1DataImg);
			table2.addCell(keyValCell1);
			
			
			Cell keyValCell2 = new Cell();
			keyValCell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell2Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.TypesofSpecificItems"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell2DataImg = new Image(Cell2Data);
			Cell2DataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell2DataImg.setTextAlignment(TextAlignment.LEFT);
			keyValCell2.add(Cell2DataImg);
			table2.addCell(keyValCell2);
			
			
			Cell keyValCell3 = new Cell();
			keyValCell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.dimensions"), ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell3DataImg = new Image(Cell3Data);
			Cell3DataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell3DataImg.setTextAlignment(TextAlignment.LEFT);
			keyValCell3.add(Cell3DataImg);
			table2.addCell(keyValCell3);
			
			
			Cell keyValCell4 = new Cell();
			keyValCell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell4Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.feeRs"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell4DataImg = new Image(Cell4Data);
			Cell4DataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell4DataImg.setTextAlignment(TextAlignment.LEFT);
			keyValCell4.add(Cell4DataImg);
			table2.addCell(keyValCell4);
			
			
			Cell keyValCell5 = new Cell();
			keyValCell5.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell5Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.securitydepositRs"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell5DataImg = new Image(Cell5Data);
			Cell5DataImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell5DataImg.setTextAlignment(TextAlignment.LEFT);
			keyValCell5.add(Cell5DataImg);
			table2.addCell(keyValCell5);
			
		
			
			int i=0;
			for (TradeLicenseItemDetailDTO dto:tradeMasterDetailDTO.getTradeLicenseItemDetailDTO()) {
				i++;
			Cell keyVal1Cell1 = new Cell();
			keyVal1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data1 = ImageDataFactory.create(Utility.textToImage(String.valueOf(i),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg1 = new Image(Cell1Data1);
			Cell1DataImg1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg1.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell1.add(Cell1DataImg1);
			table2.addCell(keyVal1Cell1);
			
			LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(dto.getTriCod2(), tradeMasterDetailDTO.getOrgid());
			Cell keyVal1Cell2 = new Cell();
			keyVal1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data2 = ImageDataFactory.create(Utility.textToImage(lookUp.getDescLangSecond(),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg2 = new Image(Cell1Data2);
			Cell1DataImg2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg2.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell2.add(Cell1DataImg2);
			table2.addCell(keyVal1Cell2);
			
			Cell keyVal1Cell3 = new Cell();
			keyVal1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data3 = ImageDataFactory.create(Utility.textToImage(String.valueOf(dto.getTrdUnit()),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg3 = new Image(Cell1Data3);
			Cell1DataImg3.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg3.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell3.add(Cell1DataImg3);
			table2.addCell(keyVal1Cell3);
			
			
			Cell keyVal1Cell4 = new Cell();
			keyVal1Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data4 = ImageDataFactory.create(Utility.textToImage(String.valueOf(dto.getLicenseFee() == null ? MainetConstants.BLANK
                    : dto.getLicenseFee() + MainetConstants.WHITE_SPACE),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg4 = new Image(Cell1Data4);
			Cell1DataImg4.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg4.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell4.add(Cell1DataImg4);
			table2.addCell(keyVal1Cell4);
			
			Cell keyVal1Cell5 = new Cell();
			keyVal1Cell5.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data5 = ImageDataFactory.create(Utility.textToImage(String.valueOf(dto.getDepositAmt() == null ? MainetConstants.BLANK
                    : dto.getDepositAmt() + MainetConstants.WHITE_SPACE),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg5 = new Image(Cell1Data5);
			Cell1DataImg5.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg5.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell5.add(Cell1DataImg5);
			table2.addCell(keyVal1Cell5);
			
			}
			
			Cell keyValCell6 = new Cell(1,3);
			keyValCell5.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell6Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.total"),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell6DataImg = new Image(Cell6Data);
			Cell6DataImg.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			Cell6DataImg.setTextAlignment(TextAlignment.RIGHT);
			keyValCell6.add(Cell6DataImg);
			table2.addCell(keyValCell6);
			
			
			Cell keyVal1Cell6 = new Cell(1,2);
			keyVal1Cell6.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData Cell1Data6 = ImageDataFactory.create(Utility.textToImage(String.valueOf(tradeMasterDetailDTO.getRmAmount() == null ? MainetConstants.BLANK
                    :tradeMasterDetailDTO.getRmAmount() + MainetConstants.WHITE_SPACE),ARIALUNICODE, TEXT_SIZE_9, false));
			Image Cell1DataImg6 = new Image(Cell1Data6);
			Cell1DataImg6.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Cell1DataImg6.setTextAlignment(TextAlignment.LEFT);
			keyVal1Cell6.add(Cell1DataImg6);
			table2.addCell(keyVal1Cell6);
			
		
			
	
			Table table3 = new Table(new float[] { 166, 166, 168 }) // in points
					.setWidth(500); // 100 pt

			table3.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b3 = new DoubleBorder(2);
			b3.setColor(ColorConstants.ORANGE);			
			
	
			Cell keyValRow5Cell1 = new Cell(1, 3);
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow5Cell1Data = ImageDataFactory
					.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.secutiydepAmt")
									+ (tradeMasterDetailDTO.getSecurityDepositeAmt() == null ? MainetConstants.BLANK
						                    : tradeMasterDetailDTO.getSecurityDepositeAmt() + MainetConstants.WHITE_SPACE)),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow5Cell1Img = new Image(keyValRow5Cell1Data);
			keyValRow5Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell1.add(keyValRow5Cell1Img);
			table3.addCell(keyValRow5Cell1);

	

			Cell keyValPara5Cell2 = new Cell();
			keyValPara5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5Cell2.setBorder(Border.NO_BORDER);
			ImageData para5tableCell2Data = ImageDataFactory.create(Utility
					.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.totalLicenseFee")
							+ (tradeMasterDetailDTO.getRmAmount() == null ? MainetConstants.BLANK
				                    :tradeMasterDetailDTO.getRmAmount() + MainetConstants.WHITE_SPACE)), ARIALUNICODE, TEXT_SIZE_10, false));
			Image para5tableCell2Img = new Image(para5tableCell2Data);
			para5tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para5tableCell2Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara5Cell2.add(para5tableCell2Img);
			table3.addCell(keyValPara5Cell2);

			Cell keyValPara6Cell1 = new Cell();
			keyValPara6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara6Cell1.setBorder(Border.NO_BORDER);
			ImageData para6tableCell1Data = ImageDataFactory.create(Utility
					.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.receiptNo")
							+  (tradeMasterDetailDTO.getRmRcptno() == null ? MainetConstants.BLANK
				                    :tradeMasterDetailDTO.getRmRcptno() + MainetConstants.WHITE_SPACE)), ARIALUNICODE, TEXT_SIZE_10, false));
			Image para6tableCell2Img = new Image(para6tableCell1Data);
			para6tableCell2Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para6tableCell2Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara6Cell1.add(para6tableCell2Img);
			table3.addCell(keyValPara6Cell1);

			Cell keyValPara7Cell1 = new Cell();
			keyValPara7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara7Cell1.setBorder(Border.NO_BORDER);
			ImageData para7tableCell1Data = ImageDataFactory
					.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.date")
											+ (tradeMasterDetailDTO.getLicenseDateDesc()  == null ? MainetConstants.BLANK
								                    :tradeMasterDetailDTO.getLicenseDateDesc()  + MainetConstants.WHITE_SPACE)),ARIALUNICODE, TEXT_SIZE_10, false));
			Image para7tableCell1Img = new Image(para7tableCell1Data);
			para7tableCell1Img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para7tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara7Cell1.add(para7tableCell1Img);
			table3.addCell(keyValPara7Cell1);

			Cell keyValPara7Cell2 = new Cell();
			keyValPara7Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara7Cell2.setBorder(Border.NO_BORDER);
			ImageData para7tableCell2Data = ImageDataFactory.create(Utility.textToImage(
					(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.date")
							+ new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(new Date())),
					ARIALUNICODE, TEXT_SIZE_10, false));
			Image para7tableCell2Img = new Image(para7tableCell2Data);
			para7tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para7tableCell2Img.setTextAlignment(TextAlignment.CENTER);
			keyValPara7Cell2.add(para7tableCell2Img);
			table3.addCell(keyValPara7Cell2);

			Cell keyValPara7Cell3 = new Cell();
			keyValPara7Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara7Cell3.setBorder(Border.NO_BORDER);
			ImageData para7tableCell3Data = ImageDataFactory.create(Utility.textToImage(((ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.fromdate"))
											+ " " + tradeMasterDetailDTO.getLicenseFromDate() + " "	+ (ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.to")) + " " + tradeMasterDetailDTO.getLicenseToDate() + " "+ (ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.todate"))),ARIALUNICODE, TEXT_SIZE_10, false));
			Image para7tableCell3Img = new Image(para7tableCell3Data);
			para7tableCell3Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para7tableCell3Img.setTextAlignment(TextAlignment.CENTER);
			para7tableCell3Img.setFontSize(TEXT_SIZE_9);
			keyValPara7Cell3.add(para7tableCell3Img);
			table3.addCell(keyValPara7Cell3);

			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table3.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

			Cell r2Cell3 = new Cell();
			r2Cell3.setBorder(Border.NO_BORDER);
			table3.addCell(r2Cell3.setPadding(PADDING));

			Cell r2Cell2 = new Cell(1, 2);
			r2Cell2.setBorder(Border.NO_BORDER);
			ImageData note1 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.licensingAuthority"), MANGAL,
					9f, false));
			Image orgnoteImg = new Image(note1);
			orgnoteImg.setMarginRight(20f);
			r2Cell2.add(orgnoteImg.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData note2 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.and"), MANGAL, 9f, false));
			Image orgnote2Img = new Image(note2);
			orgnote2Img.setMarginRight(20f);
			r2Cell2.add(orgnote2Img.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData note3 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.licnInspector"), MANGAL, 9f,
					false));
			Image orgnote3Img = new Image(note3);
			orgnote3Img.setMarginRight(20f);
			r2Cell2.add(orgnote3Img.setHorizontalAlignment(HorizontalAlignment.RIGHT));

			ImageData note4 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.orgName"), MANGAL, 9f, false));
			Image orgnote4Img = new Image(note4);
			orgnote4Img.setMarginRight(20f);
			r2Cell2.add(orgnote4Img.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			table3.addCell(r2Cell2);

			
		
			
			Table table4 = new Table(new float[] { 63, 62, 62, 63, 63, 62, 62, 63 }) // in points
					.setWidth(500); // 100 pt

			table4.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b5 = new DoubleBorder(2);
			b5.setColor(ColorConstants.BLACK);
			table4.setBorder(b5);

		//	if (CollectionUtils.isNotEmpty(renHistDetails)) {
			Cell r5Cell1 = new Cell(1, 8);
			ImageData r5CellData1 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.renewalDet"), MANGAL, 11f, false));
			Image r5CellImg1 = new Image(r5CellData1);
			r5Cell1.add(r5CellImg1.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell1.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell1);
			
			Cell r5Cell2 = new Cell();
			ImageData r5CellData2 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.srNo"), MANGAL, 9f, false));
			Image r5CellImg2 = new Image(r5CellData2);
			r5Cell2.add(r5CellImg2.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell2.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell2);
			
			
			Cell r5Cell3 = new Cell();
			ImageData r5CellData3 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.renewalNo"), MANGAL, 9f, false));
			Image r5CellImg3 = new Image(r5CellData3);
			r5Cell3.add(r5CellImg3.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell3.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell3.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell3);
			
			
			Cell r5Cell4 = new Cell();
			ImageData r5CellData4 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.date"), MANGAL, 9f, false));
			Image r5CellImg4 = new Image(r5CellData4);
			r5Cell4.add(r5CellImg4.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell4.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell4.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell4);
			
			Cell r5Cell5 = new Cell();
			ImageData r5CellData5 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.licenseFee"), MANGAL, 9f, false));
			Image r5CellImg5 = new Image(r5CellData5);
			r5Cell5.add(r5CellImg5.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell5.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell5.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell5.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell5);
			
			
			Cell r5Cell6 = new Cell();
			ImageData r5CellData6 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.late.fee"), MANGAL, 9f, false));
			Image r5CellImg6 = new Image(r5CellData6);
			r5Cell6.add(r5CellImg6.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell6.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell6.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell6.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell6);
			
			
			Cell r5Cell7 = new Cell();
			ImageData r5CellData7 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.total"), MANGAL, 9f, false));
			Image r5CellImg7 = new Image(r5CellData7);
			r5Cell7.add(r5CellImg7.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell7.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell7.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell7.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell7);
			
			Cell r5Cell8 = new Cell();
			ImageData r5CellData8 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.validity"), MANGAL, 9f, false));
			Image r5CellImg8 = new Image(r5CellData8);
			r5Cell8.add(r5CellImg8.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell8.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell8.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell8.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell8);
			
			Cell r5Cell9 = new Cell();
			ImageData r5CellData9 = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("licence.renewal.officer.sign"), MANGAL, 9f, false));
			Image r5CellImg9 = new Image(r5CellData9);
			r5Cell9.add(r5CellImg9.setHorizontalAlignment(HorizontalAlignment.LEFT));
			r5Cell9.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r5Cell9.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r5Cell9.setTextAlignment(TextAlignment.CENTER);
			table4.addCell(r5Cell9);
			
			//}
            int j = 0;
			if (CollectionUtils.isNotEmpty(renHistDetails)) {
				for (RenewalHistroyDetails renewalHistroyDet : renHistDetails) {
				j++;
				Cell keyValRowCell1 = new Cell();
				keyValRowCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode())) {
				ImageData keyValRowCell1Data = ImageDataFactory.create(Utility.textToImage((String.valueOf(j)), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell1Img = new Image(keyValRowCell1Data);
				keyValRowCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell1Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell1.add(keyValRowCell1Img);
				table4.addCell(keyValRowCell1);
				}
				
				
				
				Cell keyValRowCell2 = new Cell();
				keyValRowCell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode()) && renewalHistroyDet.getRcptNo() != null) {
				ImageData keyValRowCell2Data = ImageDataFactory.create(Utility.textToImage((String.valueOf(renewalHistroyDet.getRcptNo())), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell2Img = new Image(keyValRowCell2Data);
				keyValRowCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell2.add(keyValRowCell2Img);
				table4.addCell(keyValRowCell2);
				}
				
				
				Cell keyValRowCell3 = new Cell();
				keyValRowCell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode())  && renewalHistroyDet.getUpdatedDate() != null) {
				ImageData keyValRowCell3Data = ImageDataFactory.create(Utility.textToImage((new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(renewalHistroyDet.getUpdatedDate())), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell3Img = new Image(keyValRowCell3Data);
				keyValRowCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell3Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell3.add(keyValRowCell3Img);
				table4.addCell(keyValRowCell3);
				}
				
				
				Cell keyValRowCell4 = new Cell();
				keyValRowCell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode()) && renewalHistroyDet.getChargeDesc() != null) {
				ImageData keyValRowCell4Data = ImageDataFactory.create(Utility.textToImage(String.valueOf((renewalHistroyDet.getChargeDesc().get("License Fee"))), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell4Img = new Image(keyValRowCell4Data);
				keyValRowCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell4.add(keyValRowCell4Img);
				table4.addCell(keyValRowCell4);
				}
				
				
				
				Cell keyValRowCell5 = new Cell();
				keyValRowCell5.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode())  && renewalHistroyDet.getChargeDesc() != null) {
				ImageData keyValRowCell5Data = ImageDataFactory.create(Utility.textToImage(String.valueOf((renewalHistroyDet.getChargeDesc().get("Late Fee"))), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell5Img = new Image(keyValRowCell5Data);
				keyValRowCell5Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell5Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell5.add(keyValRowCell5Img);
				table4.addCell(keyValRowCell5);
				}
				
				
				
				Cell keyValRowCell6 = new Cell();
				keyValRowCell6.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode())  && renewalHistroyDet.getTotalAmt() != null) {
				ImageData keyValRowCell6Data = ImageDataFactory.create(Utility.textToImage((String.valueOf((renewalHistroyDet.getTotalAmt()))), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell6Img = new Image(keyValRowCell6Data);
				keyValRowCell6Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell6Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell6.add(keyValRowCell6Img);
				table4.addCell(keyValRowCell6);
				}
				
				
				
				Cell keyValRowCell7 = new Cell();
				keyValRowCell7.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode()) && renewalHistroyDet.getRenewalFromDateDesc() != null && renewalHistroyDet.getRenewalTodDateDesc() != null) {
				ImageData keyValRowCell7Data = ImageDataFactory.create(Utility.textToImage(((renewalHistroyDet.getRenewalFromDateDesc()) + " "	+ (ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.to")) + " " + (renewalHistroyDet.getRenewalTodDateDesc())), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell7Img = new Image(keyValRowCell7Data);
				keyValRowCell7Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell7Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell7.add(keyValRowCell7Img);
				table4.addCell(keyValRowCell7);
				}
				
				
				Cell keyValRowCell8 = new Cell();
				keyValRowCell8.setVerticalAlignment(VerticalAlignment.MIDDLE);
				if (MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE.equals(renewalHistroyDet.getServiceShrtCode())) {
				ImageData keyValRowCell8Data = ImageDataFactory.create(Utility.textToImage("", ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRowCell8Img = new Image(keyValRowCell8Data);
				keyValRowCell8Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				keyValRowCell8Img.setTextAlignment(TextAlignment.LEFT);
				keyValRowCell8.add(keyValRowCell8Img);
				table4.addCell(keyValRowCell8);
				}
				
				
				}
			}
			
		
			Table table5 = new Table(new float[] { 166, 166, 168 }) // in points
					.setWidth(500); // 100 pt

			table5.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b4 = new DoubleBorder(2);
			b4.setColor(ColorConstants.ORANGE);
			
			Cell keyValRow17Cell1 = new Cell(1, 3);
			keyValRow17Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow17Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow17Cell1Data = ImageDataFactory.create(
					Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.skdcl.certificate.terms"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow17Cell1Img = new Image(keyValRow17Cell1Data);
			keyValRow17Cell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow17Cell1Img.setTextAlignment(TextAlignment.CENTER);
			keyValRow17Cell1.add(keyValRow17Cell1Img);
			table5.addCell(keyValRow17Cell1);
			
			table5.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table5.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			
             int k= 0;
			if (CollectionUtils.isNotEmpty(apprejMasList) && apprejMasList.get(0).getArtRemarks() != null) {
				for (TbApprejMas tbApprejMas : apprejMasList) {
				k++;
		        Cell keyValRow9Celli = new Cell(1,3);
		        keyValRow9Celli.setVerticalAlignment(VerticalAlignment.MIDDLE);
		        keyValRow9Celli.setBorder(Border.NO_BORDER);
				ImageData keyValRow9CelliData = ImageDataFactory.create(
						Utility.textToImage((k+"."+ tbApprejMas.getArtRemarks()), ARIALUNICODE, TEXT_SIZE_9, false));
				Image keyValRow9CelliImg = new Image(keyValRow9CelliData);
		
				
					float imageWidthr3Cell1 = keyValRow9CelliImg.getImageWidth();

					float cellWidth = 480;

					if (imageWidthr3Cell1 > cellWidth) {
						String addressRow1Cell2 = tbApprejMas.getArtRemarks();
						int sizeRow1Cell2 = Math.round(addressRow1Cell2.length() / 1.6f);
						String address1Row1Cell2 = addressRow1Cell2.substring(0, sizeRow1Cell2);
						address1Row1Cell2 = StringUtils.substringBeforeLast(address1Row1Cell2, " ");
						String address2Row1Cell2 = StringUtils.substringAfterLast(addressRow1Cell2, address1Row1Cell2);

						ImageData address1ImgDataRow1Cell2 = ImageDataFactory
								.create(Utility.textToImage(k+"."+address1Row1Cell2, ARIALUNICODE, TEXT_SIZE_9, false));
						Image address1ImgRow1Cell2 = new Image(address1ImgDataRow1Cell2);

						ImageData address2ImgDataRow1Cell2 = ImageDataFactory
								.create(Utility.textToImage(address2Row1Cell2, ARIALUNICODE, TEXT_SIZE_9, false));
						Image address2ImgRow1Cell2 = new Image(address2ImgDataRow1Cell2);

						address1ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
						address1ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
						keyValRow9Celli.add(address1ImgRow1Cell2);
						keyValRow9Celli.add(new Paragraph(""));
						address2ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
						address2ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
						keyValRow9Celli.add(address2ImgRow1Cell2);

					} else {
						keyValRow9CelliImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
						keyValRow9CelliImg.setTextAlignment(TextAlignment.LEFT);
						keyValRow9Celli.add(keyValRow9CelliImg);
					}

					table5.addCell(keyValRow9Celli);

				}
				
			}
		
			
			document.add(table);
			document.add(new Paragraph("\n"));
			document.add(table2);
			document.add(new Paragraph("\n\n"));
			document.add(table3);
			document.add(new Paragraph("\n"));
			document.add(table4);
			document.add(new Paragraph("\n\n"));
			document.add(table5);
			document.close();
			writer.close();
			LOGGER.info("generateCertificate method ended ------------------------>");
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
			return false;
		}

	}

	public boolean generatePdfCertificate(String docPath, TradeMasterDetailDTO tradeMasterDetailDTO, Long orgId) {

		LOGGER.info("generateCertificate PFD method started ------------------------>");
		final String MANGAL = ApplicationSession.getInstance().getMessage("mangalFont.path");
		final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");

		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);

			Table table = new Table(new float[] { 166, 166, 168 }) // in points
					.setWidth(500); // 100 pt

			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(4);
			b1.setColor(ColorConstants.ORANGE);
			table.setBorder(b1);

			Cell r1Cell1 = new Cell();
			ImageData leftImgData = ImageDataFactory
					.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(95f);
			logoImg.setWidth(70f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell1.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell();
			ImageData orgNameData = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("property.orgName.reg"), MANGAL, 15f, false));
			Image orgNameImg = new Image(orgNameData);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.LEFT));
			Paragraph orgNameEng = new Paragraph(
					ApplicationSession.getInstance().getMessage("property.orgName.english"));
			orgNameEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng.setTextAlignment(TextAlignment.LEFT);
			orgNameEng.setFontSize(13f);
			// orgNameEng.setMarginLeft(40f);
			r1Cell2.add(orgNameEng);
			ImageData headingData = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.certificate1"), MANGAL, 13f, false));
			Image headingImg = new Image(headingData);
			r1Cell2.add(headingImg.setHorizontalAlignment(HorizontalAlignment.LEFT));

			Paragraph subHeadingData = new Paragraph(
					ApplicationSession.getInstance().getMessage("property.orgName.english"));
			subHeadingData.setHorizontalAlignment(HorizontalAlignment.CENTER);
			subHeadingData.setVerticalAlignment(VerticalAlignment.MIDDLE);
			subHeadingData.setTextAlignment(TextAlignment.LEFT);
			subHeadingData.setFontSize(13f);
			r1Cell2.add(subHeadingData);
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			ImageData rightImgData = ImageDataFactory
					.create(filenetPath + ApplicationSession.getInstance().getMessage("rightlogo"));
			Image logoImgRight = new Image(rightImgData);
			logoImgRight.setWidth(70f);
			logoImgRight.setHeight(71f);
			r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell3.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell3.setPadding(PADDING));

			Cell keyValPara1Cell1 = new Cell(1, 3);
			keyValPara1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell1.setBorder(Border.NO_BORDER);
			ImageData para1tableCell1Data = ImageDataFactory.create(Utility.textToImage(
					ApplicationSession.getInstance().getMessage("trade.certificate3"), MANGAL, TEXT_SIZE_10, false));
			Image para1tableCell1Img = new Image(para1tableCell1Data);
			para1tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			// para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para1tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara1Cell1.add(para1tableCell1Img);
			table.addCell(keyValPara1Cell1);

			Cell keyValPara2Cell1 = new Cell(1, 3);
			keyValPara2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara2Cell1.setBorder(Border.NO_BORDER);
			Paragraph para2 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate4"));
			para2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para2.setTextAlignment(TextAlignment.CENTER);
			para2.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara2Cell1.add(para2);
			table.addCell(keyValPara2Cell1);

			Cell keyValPara3Cell1 = new Cell(1, 3);
			keyValPara3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara3Cell1.setBorder(Border.NO_BORDER);
			ImageData para3tableCell1Data = ImageDataFactory
					.create(Utility
							.textToImage(
									(ApplicationSession.getInstance().getMessage("trade.certificate5")
											+ tradeMasterDetailDTO.getLiceCategory()),
									ARIALUNICODE, TEXT_SIZE_9, false));
			Image para3tableCell1Img = new Image(para3tableCell1Data);
			para3tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para3tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			para3tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara3Cell1.add(para3tableCell1Img);
			table.addCell(keyValPara3Cell1);

			Cell keyValPara4Cell1 = new Cell(1, 3);
			keyValPara4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara4Cell1.setBorder(Border.NO_BORDER);
			Paragraph para4 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate6")
					+ tradeMasterDetailDTO.getLiceCategory());
			para4.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para4.setTextAlignment(TextAlignment.CENTER);
			para4.setMarginLeft(8f);
			para4.setFirstLineIndent(20f);
			para4.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara4Cell1.add(para4);
			table.addCell(keyValPara4Cell1);

			Cell keyValPara5Cell1 = new Cell(1, 3);
			keyValPara5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5Cell1.setBorder(Border.NO_BORDER);
			ImageData para5tableCell1Data = ImageDataFactory
					.create(Utility
							.textToImage(
									(ApplicationSession.getInstance().getMessage("trade.certificate7")
											+ tradeMasterDetailDTO.getLiceSubCategory()),
									ARIALUNICODE, TEXT_SIZE_9, false));
			Image para5tableCell1Img = new Image(para5tableCell1Data);
			para5tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para5tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			para5tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara5Cell1.add(para5tableCell1Img);
			table.addCell(keyValPara5Cell1);

			Cell keyValPara6Cell1 = new Cell(1, 3);
			keyValPara6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara6Cell1.setBorder(Border.NO_BORDER);
			Paragraph para6 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate8")
					+ tradeMasterDetailDTO.getLiceSubCategory());
			para6.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para6.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para6.setTextAlignment(TextAlignment.CENTER);
			para6.setMarginLeft(8f);
			para6.setFirstLineIndent(20f);
			para6.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara6Cell1.add(para6);
			table.addCell(keyValPara6Cell1);

			Cell keyValPara7Cell1 = new Cell(1, 3);
			keyValPara7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara7Cell1.setBorder(Border.NO_BORDER);
			ImageData para7tableCell1Data = ImageDataFactory.create(Utility.textToImage(
					(ApplicationSession.getInstance().getMessage("trade.certificate9")
							+ tradeMasterDetailDTO.getLicenseFromDate() + " "
							+ ApplicationSession.getInstance().getMessage("trade.certificate9a")
							+ tradeMasterDetailDTO.getLicenseToDate() + " "
							+ ApplicationSession.getInstance().getMessage("trade.certificate9b")),
					MANGAL, TEXT_SIZE_9, false));
			Image para7tableCell1Img = new Image(para7tableCell1Data);
			para7tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para7tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			para7tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara7Cell1.add(para7tableCell1Img);
			table.addCell(keyValPara7Cell1);

			Cell keyValPara8Cell1 = new Cell(1, 3);
			keyValPara8Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara8Cell1.setBorder(Border.NO_BORDER);
			Paragraph para8 = new Paragraph((ApplicationSession.getInstance().getMessage("trade.certificate10")) + " "
					+ tradeMasterDetailDTO.getLicenseFromDate() + " To " + tradeMasterDetailDTO.getLicenseToDate());
			para8.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para8.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para8.setTextAlignment(TextAlignment.CENTER);
			para8.setMarginLeft(8f);
			para8.setFirstLineIndent(20f);
			para8.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara8Cell1.add(para8);
			table.addCell(keyValPara8Cell1);

			Cell keyValPara10Cell1 = new Cell(1, 3);
			keyValPara10Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara10Cell1.setBorder(Border.NO_BORDER);
			ImageData para10tableCell1Data = ImageDataFactory
					.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.certificate11")
							+ tradeMasterDetailDTO.getTrdLicno()), ARIALUNICODE, TEXT_SIZE_9, false));
			Image para10tableCell1Img = new Image(para10tableCell1Data);
			para10tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para10tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			para10tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara10Cell1.add(para10tableCell1Img);
			table.addCell(keyValPara10Cell1);

			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow1Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate12"),
							ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow1Cell2Data = ImageDataFactory.create(
					Utility.textToImage(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName(),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow1Cell2Img = new Image(keyValRow1Cell2Data);
			keyValRow1Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow1Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow1Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow1Cell2.add(keyValRow1Cell2Img);
			table.addCell(keyValRow1Cell2);

			Cell keyValRow2Cell1 = new Cell();
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate13"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow2Cell1Img = new Image(keyValRow2Cell1Data);
			keyValRow2Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow2Cell1.add(keyValRow2Cell1Img);
			Paragraph fatherName = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate13a"));
			fatherName.setHorizontalAlignment(HorizontalAlignment.LEFT);
			// fatherName.setVerticalAlignment(VerticalAlignment.MIDDLE);
			fatherName.setTextAlignment(TextAlignment.LEFT);
			fatherName.setFontSize(9f);
			fatherName.setMarginLeft(5f);
			// orgNameEng.setMarginLeft(35f);
			keyValRow2Cell1.add(fatherName);

			table.addCell(keyValRow2Cell1);

			Cell keyValRow2Cell2 = new Cell(1, 2);
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData keyValRow2Cell2Data = ImageDataFactory.create(
					Utility.textToImage(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMname(),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow2Cell2Img = new Image(keyValRow2Cell2Data);
			keyValRow2Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow2Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow2Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow2Cell2.add(keyValRow2Cell2Img);
			table.addCell(keyValRow2Cell2);

			Cell keyValRow3Cell1 = new Cell();
			keyValRow3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow3Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate14"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow3Cell1Img = new Image(keyValRow3Cell1Data);
			keyValRow3Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow3Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow3Cell1.add(keyValRow3Cell1Img);
			table.addCell(keyValRow3Cell1);

			Cell keyValRow3Cell2 = new Cell(1, 2);
			keyValRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell2.setBorder(Border.NO_BORDER);
			keyValRow3Cell2
					.add(new Paragraph(tradeMasterDetailDTO.getApmApplicationId().toString()).setFontSize(TEXT_SIZE_9));
			table.addCell(keyValRow3Cell2);

			Cell keyValRow4Cell1 = new Cell();
			keyValRow4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow4Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate15"),
							ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow4Cell2Data = ImageDataFactory
					.create(Utility.textToImage(tradeMasterDetailDTO.getTrdBusnm(), ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow4Cell2Img = new Image(keyValRow4Cell2Data);
			keyValRow4Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow4Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow4Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow4Cell2.add(keyValRow4Cell2Img);
			table.addCell(keyValRow4Cell2);

			Cell keyValRow5Cell1 = new Cell();
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow5Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate16"),
							ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow5Cell2Data = ImageDataFactory.create(
					Utility.textToImage(tradeMasterDetailDTO.getTrdBusadd(), ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow5Cell2Img = new Image(keyValRow5Cell2Data);
			keyValRow5Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow5Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow5Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow5Cell2.add(keyValRow5Cell2Img);
			table.addCell(keyValRow5Cell2);

			Cell keyValRow6Cell1 = new Cell();
			keyValRow6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow6Cell1Data = ImageDataFactory
					.create(Utility.textToImage((ApplicationSession.getInstance().getMessage("trade.certificate17")),
							ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow6Cell2Data = ImageDataFactory.create(
					Utility.textToImage(tradeMasterDetailDTO.getWard1Level(), ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow6Cell2Img = new Image(keyValRow6Cell2Data);
			keyValRow6Cell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow6Cell2Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow6Cell2Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow6Cell2.add(keyValRow6Cell2Img);
			table.addCell(keyValRow6Cell2);

			Cell keyValRow7Cell1 = new Cell();
			keyValRow7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow7Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate18"),
							ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow7Cell2Data = ImageDataFactory.create(
					Utility.textToImage(tradeMasterDetailDTO.getWard2Level(), ARIALUNICODE, TEXT_SIZE_10, false));
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
			ImageData keyValRow8Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate19"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow8Cell1Img = new Image(keyValRow8Cell1Data);
			keyValRow8Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow8Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow8Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow8Cell1.add(keyValRow8Cell1Img);
			table.addCell(keyValRow8Cell1);

			Cell keyValRow8Cell2 = new Cell(1, 2);
			keyValRow8Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow8Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell2.setBorder(Border.NO_BORDER);
			if (tradeMasterDetailDTO.getCpdValue() != null)
				keyValRow8Cell2.add(new Paragraph(tradeMasterDetailDTO.getCpdValue()).setFontSize(TEXT_SIZE_9));
			table.addCell(keyValRow8Cell2);

			Cell keyValRow9Cell1 = new Cell();
			keyValRow9Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow9Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate20"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow9Cell1Img = new Image(keyValRow9Cell1Data);
			keyValRow9Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow9Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow9Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow9Cell1.add(keyValRow9Cell1Img);
			table.addCell(keyValRow9Cell1);

			Cell keyValRow9Cell2 = new Cell(1, 2);
			keyValRow9Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow9Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell2.setBorder(Border.NO_BORDER);
			keyValRow9Cell2.add(new Paragraph(tradeMasterDetailDTO.getLicenseFromDate()).setFontSize(TEXT_SIZE_9));
			table.addCell(keyValRow9Cell2);

			Cell keyValRow10Cell1 = new Cell();
			keyValRow10Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow10Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate21"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow10Cell1Img = new Image(keyValRow10Cell1Data);
			keyValRow10Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow10Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow10Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow10Cell1.add(keyValRow10Cell1Img);
			table.addCell(keyValRow10Cell1);

			Cell keyValRow10Cell2 = new Cell(1, 2);
			keyValRow10Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow10Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell2.setBorder(Border.NO_BORDER);
			keyValRow10Cell2.add(new Paragraph("Aligarh").setFontSize(TEXT_SIZE_9));
			table.addCell(keyValRow10Cell2);

			Cell keyValRow11Cell1 = new Cell();
			keyValRow11Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell1.setBorder(Border.NO_BORDER);
			ImageData keyValRow11Cell1Data = ImageDataFactory
					.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("trade.certificate22"),
							ARIALUNICODE, TEXT_SIZE_10, false));
			Image keyValRow11Cell1Img = new Image(keyValRow11Cell1Data);
			keyValRow11Cell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow11Cell1Img.setTextAlignment(TextAlignment.LEFT);
			keyValRow11Cell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow11Cell1.add(keyValRow11Cell1Img);
			table.addCell(keyValRow11Cell1);

			Cell keyValRow11Cell2 = new Cell(1, 2);
			keyValRow11Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			keyValRow11Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell2.setBorder(Border.NO_BORDER);
			keyValRow11Cell2
					.add(new Paragraph(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno())
							.setFontSize(TEXT_SIZE_9));
			table.addCell(keyValRow11Cell2);

			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 3).setBorder(Border.NO_BORDER));

			Cell keyValPara11Cell1 = new Cell(1, 2);
			keyValPara11Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara11Cell1.setBorder(Border.NO_BORDER);
			Paragraph para11 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate23"));
			para11.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para11.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para11.setTextAlignment(TextAlignment.RIGHT);
			para11.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara11Cell1.add(para11);
			table.addCell(keyValPara11Cell1);

			Cell keyValPara11aCell1 = new Cell();
			keyValPara11aCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara11aCell1.setBorder(Border.NO_BORDER);
			Paragraph para11a = new Paragraph("");
			keyValPara11aCell1.add(para11a);
			table.addCell(keyValPara11aCell1);

			Cell keyValPara12bCell1 = new Cell();
			keyValPara12bCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara12bCell1.setBorder(Border.NO_BORDER);
			Paragraph para12b = new Paragraph("");
			keyValPara12bCell1.add(para12b);
			table.addCell(keyValPara12bCell1);

			Cell keyValPara12Cell1 = new Cell();
			keyValPara12Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara12Cell1.setBorder(Border.NO_BORDER);
			Paragraph para12 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate24"));
			para12.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para12.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para12.setTextAlignment(TextAlignment.LEFT);
			para12.setFontSize(TEXT_SIZE_10);
			// para12.setMargin(50f);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara12Cell1.add(para12);
			table.addCell(keyValPara12Cell1);

			Cell keyValPara12aCell1 = new Cell();
			keyValPara12aCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara12aCell1.setBorder(Border.NO_BORDER);
			Paragraph para12a = new Paragraph("");
			keyValPara12aCell1.add(para12a);
			table.addCell(keyValPara12aCell1);

			Cell keyValPara13bCell1 = new Cell();
			keyValPara13bCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara13bCell1.setBorder(Border.NO_BORDER);
			Paragraph para13b = new Paragraph("");
			keyValPara13bCell1.add(para13b);
			table.addCell(keyValPara13bCell1);

			Cell keyValPara13Cell1 = new Cell();
			keyValPara13Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara13Cell1.setBorder(Border.NO_BORDER);
			Paragraph para13 = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate25"));
			para13.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para13.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para13.setTextAlignment(TextAlignment.LEFT);
			para13.setFontSize(TEXT_SIZE_10);
			keyValPara13Cell1.add(para13);
			table.addCell(keyValPara13Cell1);

			Cell keyValPara13aCell1 = new Cell();
			keyValPara13aCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara13aCell1.setBorder(Border.NO_BORDER);
			Paragraph para13a = new Paragraph("");
			keyValPara13aCell1.add(para13a);
			table.addCell(keyValPara13aCell1);

			document.add(table);

			Paragraph paralast = new Paragraph(ApplicationSession.getInstance().getMessage("trade.certificate26"));
			paralast.setHorizontalAlignment(HorizontalAlignment.CENTER);
			paralast.setVerticalAlignment(VerticalAlignment.MIDDLE);
			paralast.setTextAlignment(TextAlignment.CENTER);
			paralast.setFontSize(TEXT_SIZE_10);
			document.add(paralast);
			document.close();
			writer.close();
			LOGGER.info("generateCertificate method ended ------------------------>");
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
			return false;
		}

	}

	// #140263
	@Override
	@Consumes("application/json")
	@POST
	@ApiOperation(value = "getLicenseDetailsBySourceAndOrgId", notes = "getLicenseDetailsBySourceAndOrgId", response = TradeMasterDetailDTO.class)
	@Path("/getLicenseDetails")
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> getLicenseDetailsBySourceAndOrgId(TradeMasterDetailDTO dto) {
		List<TradeMasterDetailDTO> mstDtoList = new ArrayList<>();
		TradeMasterDetailDTO mstDto = new TradeMasterDetailDTO();
		try {
			List<TbMlTradeMast> entityList = tradeLicenseApplicationRepository
					.getLicenseDetailsBySourceAndOrgId(dto.getOrgId(), dto.getMobileNo());
			if (CollectionUtils.isNotEmpty(entityList))
				for (TbMlTradeMast master : entityList) {
					mstDto = mapEntityToDto1(master);
					mstDtoList.add(mstDto);
				}
		} catch (Exception e) {
			LOGGER.info("Exception Occur while fetcing license details in getLicenseDetailsBySourceAndOrgId" + e);
		}
		return mstDtoList;
	}

	@Override
	public Double getParentTaxValue(TradeMasterDetailDTO masDto) {
		double parentTaxValue = 0;
		TradeMasterDetailDTO trdDto = getTradeLicenceChargesFromBrmsRule(masDto);
		Map.Entry<Long, Double> maxPrice = null;
		for (Map.Entry<Long, Double> currentEntry : trdDto.getFeeIds().entrySet()) {
			if (maxPrice == null || currentEntry.getValue().compareTo(maxPrice.getValue()) > 0) {
				maxPrice = currentEntry;
			}
		}
		if (maxPrice != null && maxPrice.getValue() != null)
			parentTaxValue = maxPrice.getValue();
		masDto.setFeeIds(new HashMap<Long, Double>());
		return parentTaxValue;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> fetchAllLicenseDemand(Long orgId, String licNo, Long long1, Long long2,
			Long long3, Long long4, Long long5) {
		Long licType = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("P", "LIT", orgId);
		List<TbMlTradeMast> licList = iTradeLicenseApplicationDao.fetchLicenseDetails(orgId, licType, licNo, new Date(),
				long1, long2, long3, long4, long5);
		List<TradeMasterDetailDTO> tbMltrdist = new ArrayList<TradeMasterDetailDTO>();
		if (CollectionUtils.isNotEmpty(licList)) {
			TradeMasterDetailDTO dto = new TradeMasterDetailDTO();
			for (TbMlTradeMast master : licList) {
				dto = mapEntityToDto1(master);
				tbMltrdist.add(dto);
			}
		}
		return tbMltrdist;
	}

	@Override
	public CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO2, Long empId, Long orgId,
			String serviceName, String amount) throws BeansException, IOException, InterruptedException {
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(serviceName, orgId);
		offlineDTO2.setOrgId(orgId);
		offlineDTO2.setAmountToPay(amount);
		offlineDTO2.setDeptId(sm.getTbDepartment().getDpDeptid());
		offlineDTO2.setUserId(empId);
		final CommonChallanDTO master = ApplicationContextProvider.getApplicationContext()
				.getBean(IChallanService.class).callPushToPayApiForPayment(offlineDTO2);

		return master;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeMasterDetailDTO> getpaymentModeByAppId(Long orgId, Long applicationId) {

		List<TradeMasterDetailDTO> tradeMasterDetailDtoList = new ArrayList<>();
		List<Object[]> licenseApproval = iTradeLicenseApplicationDao.getpaymentModeByAppId(orgId, applicationId);
		TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
		for (Object[] license : licenseApproval) {

			tradeMasterDetailDTO.setRmRcptno((Long) license[0]);
			tradeMasterDetailDTO.setRmDate((Date) license[1]);
			tradeMasterDetailDTO.setRmAmount((BigDecimal) license[2]);
			tradeMasterDetailDTO.setCpdDesc((String) license[3]);
			tradeMasterDetailDTO.setCpdDescMar((String) license[4]);
			if (license[5] != null)
				tradeMasterDetailDTO.setRmLoiNo((String) license[5]);
			tradeMasterDetailDTO.setApmApplicationId((Long) license[6]);
			tradeMasterDetailDTO.setCpdValue((String) license[7]);
			tradeMasterDetailDTO.setCheckStatus((Long) license[8]);
			tradeMasterDetailDtoList.add(tradeMasterDetailDTO);
		}

		return tradeMasterDetailDtoList;
	}
	@Override
	@Transactional
	public boolean updateStatusFlagByRefId(String refId,Long orgId,Long updateBy) {
		long trdStatus = CommonMasterUtility
				.getValueFromPrefixLookUp("I", "LIS", iOrganisationService.getOrganisationById(orgId)).getLookUpId();
		    tradeLicenseApplicationRepository.updateStatusFlagByRefId(Long.valueOf(refId), orgId, trdStatus, new  Date(), updateBy);
		return true;
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, String> getApplicationDetail(final Long applicationId) {

		LOGGER.info("Trade Licence Data fetched for application id " + applicationId);
		Map<String, String> map = new HashMap<String, String>();
		try {
			TbMlTradeMast entity = tradeLicenseApplicationRepository
					.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
			List<TradeLicenseItemDetailDTO> itemDto = getItemDetailsByTriStatusAndTrdId(entity.getTrdId(),
					entity.getOrgid());
			if (itemDto != null && !itemDto.isEmpty()) {
				if(entity.getTrdType()!=null)
				map.put("TRD_TYPE", entity.getTrdType().toString());
				if(entity.getTrdBusnm()!=null)
				map.put("TRD_BUSNM", entity.getTrdBusnm());
				if(entity.getTrdBusadd()!=null)
				map.put("TRD_BUSADD", entity.getTrdBusadd());
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Trade License Application ", exception);
			throw new FrameworkException("Exception occur while fetching Trade License Application ", exception);
		}
		return map;
	}
}
