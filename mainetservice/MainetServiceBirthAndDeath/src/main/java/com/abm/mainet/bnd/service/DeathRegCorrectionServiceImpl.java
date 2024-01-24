package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dao.IDeathRegCorrDao;
import com.abm.mainet.bnd.dao.IssuenceOfDeathCertificateDao;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.DeceasedMasterCorrection;
import com.abm.mainet.bnd.domain.MedicalMaster;
import com.abm.mainet.bnd.domain.MedicalMasterCorrection;
import com.abm.mainet.bnd.domain.TbBdDeathregCorr;
import com.abm.mainet.bnd.domain.TbBdDeathregCorrHistory;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.domain.TbDeathregTemp;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterCorrDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterCorrectionDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.DeathRegCorrHistoryRepository;
import com.abm.mainet.bnd.repository.DeathRegCorrectionRepository;
import com.abm.mainet.bnd.repository.DeathRegistrationRepository;
import com.abm.mainet.bnd.repository.MedicalMasterRepository;
import com.abm.mainet.bnd.ui.model.DeathRegCorrectionModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.bnd.service.IdeathregCorrectionService")
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Api(value = "/deathRegCorrectionService")
@Path(value = "/deathRegCorrectionService")
public class DeathRegCorrectionServiceImpl implements IdeathregCorrectionService {

	@Autowired
	private IDeathRegCorrDao iDeathRegCorrDao;

	@Autowired
	private DeathRegCorrectionRepository deathRegCorrectionRepository;

	@Autowired
	private DeathRegCorrHistoryRepository deathRegCorrHistoryRepository;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private DeathRegistrationRepository deathRegistrationRepository;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	private CfcInterfaceJpaRepository cfcInterfaceJpaRepository;

	@Resource
	private ApplicationService applicationService;
	
	@Resource
	private IOrganisationDAO iOrganisationDAO;

	@Autowired
	private MedicalMasterRepository medicalMasterRepository;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;
	
	@Autowired
	private ICemeteryMasterService iCemeteryMasterService;
	
	@Autowired
	private IChallanService challanService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	 @Autowired
	 private IDeathRegistrationService iDeathRegistrationService;
	 
	@Autowired
	private IssuenceOfDeathCertificateDao issueDeathsertiDao;

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getDeathRegisteredAppliDetail(@QueryParam("drCertNo") String drCertNo,
			@QueryParam("applnId") Long applnId, @QueryParam("year") String year,
			@QueryParam("applicationId") String drRegno, @QueryParam("drDod") Date drDod,
			@QueryParam("drDeceasedname") String drDeceasedname, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DR", orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		//String DeathWFStatus ="APPROVED";
		

		
			List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredAppliDetail(drCertNo, applnId, year, drRegno,
				drDod, drDeceasedname, orgId, smServiceId,null);
			
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();

			if (tbDeathreg != null) {
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
					LookUp lokkup = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
					dto.setDrSex(lokkup.getDescLangFirst());
					dto.setDrSexMar(lokkup.getDescLangSecond());
//					String pattern = "dd-MM-yyyy";//changes done by vishu
//					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//					String date = simpleDateFormat.format(dto.getDrRegdate());
//					dto.setDrRegdate(Utility.stringToDate(date));
					listDTO.add(dto);

				});
			}
			return listDTO;
			
		}

	@Override
	@POST
	@Path(value = "/getDeathRegistrationDetails")
	@Transactional(readOnly = true)
	public List<TbDeathregDTO> getDeathRegDataForPortal(TbDeathregDTO tbDeathregDTO) {
		List<TbDeathregDTO> deathRegDetailsList = getDeathDataForCorr(tbDeathregDTO);
		
		
		return deathRegDetailsList;
	}
	

	@Transactional
	@Override
	public List<TbDeathregDTO> getDeathRegDataByStatus(@QueryParam("drCertNo") String drCertNo,
			@QueryParam("applnId") Long applnId, @QueryParam("year") String year,
			@QueryParam("drRegno") String drRegno, @QueryParam("drDod") Date drDod,
			@QueryParam("drDeceasedname") String drDeceasedname, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DR", orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		
		List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredAppliDetail(drCertNo, applnId, year, drRegno,
				drDod, drDeceasedname, orgId, smServiceId,null);
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();
			
			if (tbDeathreg != null) {
			
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
					if(entity.getMedicalMaster()!=null)
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
				       if(entity.getDeceasedMaster()!=null)
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
					LookUp lokkup = null;
					if(entity.getDrSex()!=null) {
					 lokkup = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
					 dto.setDrSex(lokkup.getDescLangFirst());
						dto.setDrSexMar(lokkup.getDescLangSecond());
					}
					
					listDTO.add(dto);

				});
			}
			return listDTO;
		
	}
	

	@Override
	@Transactional
	@POST
	@Path("/getDeathRegDetailById/{drId}")
	public TbDeathregDTO getDeathRegDetailById(@PathParam(value = "drId") Long drID) {
		TbDeathregDTO tbDeathregDTO = getDeathById(drID);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC,tbDeathregDTO.getOrgId());
		//LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		Organisation org = iOrganisationDAO.getOrganisationById(tbDeathregDTO.getOrgId(), MainetConstants.STATUS.ACTIVE);
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), org);
		if(lookup.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
		{
			tbDeathregDTO.setCheckStatus("A");
		}else {
			tbDeathregDTO.setCheckStatus("NA");
		}
		
		if(serviceMas.getSmFeesSchedule()!=0) {
			tbDeathregDTO.setChargeStatus("CC");
		}
		return tbDeathregDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathregDTO getDeathById(Long drID) {
		TbDeathreg tbDeathreg = deathRegistrationRepository.findOne(drID);
		TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
		BeanUtils.copyProperties(tbDeathreg, tbDeathregDTO);
		tbDeathregDTO.getMedicalMasterDto().setMcOthercond(tbDeathreg.getMedicalMaster().getMcOthercond());
		tbDeathregDTO.getMedicalMasterDto().setMcMdSuprName(tbDeathreg.getMedicalMaster().getMcMdSuprName());

		Long sum = 0L;
		
		sum = tbDeathreg.getCertNoCopies() != null && tbDeathreg.getDrManualCertno() != null ? 
			  tbDeathreg.getCertNoCopies() + tbDeathreg.getDrManualCertno() : tbDeathreg.getCertNoCopies() != null ? 
					  sum + tbDeathreg.getCertNoCopies() : tbDeathreg.getDrManualCertno() != null ? sum + tbDeathreg.getDrManualCertno() : sum;
		
		tbDeathregDTO.setAlreayIssuedCopy(sum);
		
		tbDeathregDTO.setNumberOfCopies(null);
		return tbDeathregDTO;
	}
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public long CalculateNoOfDays(TbDeathregDTO tbDeathregDTO) {

		long noOfDays = Utility.getDaysBetweenDates(tbDeathregDTO.getDrDod(), tbDeathregDTO.getDrRegdate());
		if (noOfDays <= 21) {
			return 21;
		} else if (noOfDays <= 30 & noOfDays > 21) {
			return 30;
		} else if (noOfDays <= 365 & noOfDays > 30) {
			return 365;

		} else {
			return 366;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public Map<String,Object> saveDeathCorrData(TbDeathregDTO tbDeathregDTO,DeathRegCorrectionModel deathCorrModel) {
		final RequestDTO requestDTO = tbDeathregDTO.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DRC, tbDeathregDTO.getOrgId());

		TbBdDeathregCorr tbBdDeathregCorr = new TbBdDeathregCorr();
		TbBdDeathregCorrHistory tbBdDeathregCorrHistory = new TbBdDeathregCorrHistory();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();

		// death correction entry
		BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorr);

		if (tbDeathregDTO.getDrRegdate() != null) {
			tbBdDeathregCorr.setDrRegdate(tbDeathregDTO.getDrRegdate());
		} else {
			tbBdDeathregCorr.setDrRegdate(new Date());
		}
		tbBdDeathregCorr.setLmoddate(new Date());
		tbBdDeathregCorr.setDrId(tbDeathregDTO.getDrId());
		tbBdDeathregCorr.setDrSex(tbDeathregDTO.getDrSex());
		tbBdDeathregCorr.setDrDeathaddr(tbDeathregDTO.getDrDeathaddr());
		tbBdDeathregCorr.setOrgId(tbDeathregDTO.getOrgId());
		tbBdDeathregCorr.setDrStatus("O");
		tbBdDeathregCorr.setDeathWFStatus("OPEN");
		tbBdDeathregCorr.setMcMdSuprName(tbDeathregDTO.getMedicalMasterDto().getMcMdSuprName());
		tbBdDeathregCorr.setMcOthercond(tbDeathregDTO.getMedicalMasterDto().getMcOthercond());
		if (tbDeathregDTO.getCorrCategory() != null && !tbDeathregDTO.getCorrCategory().isEmpty()) {
			tbBdDeathregCorr.setCorrCategory(String.join(",", tbDeathregDTO.getCorrCategory()));
		}
		if(null != tbDeathregDTO.getCpdRegUnit())
			tbBdDeathregCorr.setPdRegUnitId(tbDeathregDTO.getCpdRegUnit());
		deathRegCorrectionRepository.save(tbBdDeathregCorr);
		

		// death registration Correction history
		BeanUtils.copyProperties(tbBdDeathregCorr, tbBdDeathregCorrHistory);
		tbBdDeathregCorrHistory.setAction(BndConstants.DRC);
		tbBdDeathregCorrHistory.setDrStatus("O");
		deathRegCorrHistoryRepository.save(tbBdDeathregCorrHistory);

		// RequestDTO entry
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode("F");
		//requestDTO.setReferenceId(String.valueOf(tbBdDeathregCorr.getDrCorrId()));
		requestDTO.setUserId(tbDeathregDTO.getUserId());
		requestDTO.setDeptId(tbDeathregDTO.getDeptId());
		if(serviceMas.getSmFeesSchedule()==0)
		{
			requestDTO.setPayStatus("F");
		}else {
			requestDTO.setPayStatus("Y");
		}
		
		requestDTO.setLangId(Long.valueOf(tbDeathregDTO.getLangId()));
		
		// Generate the Application Number #111859 By Arun
		requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,PrefixConstants.STATUS_ACTIVE_PREFIX));
		requestDTO.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		requestDTO.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		String monthStr = localDate.getMonthValue() < 10 ? "0"+localDate.getMonthValue() : String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0"+localDate.getDayOfMonth() : String.valueOf(localDate.getDayOfMonth());
		requestDTO.setCustomField(String.valueOf(monthStr+""+dayStr));
		///
		final Long applicationId = applicationService.createApplication(requestDTO);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}

		requestDTO.setApplicationId(applicationId);
		requestDTO.setReferenceId(String.valueOf(applicationId));
		tbDeathregDTO.setApplicationId(applicationId);
		if ((applicationId != null) && (applicationId != 0)) {
			tbDeathregDTO.setApmApplicationId(applicationId);
		}

		//death interface entry
		BeanUtils.copyProperties(tbDeathregDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		tbBdDeathregCorr.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(tbBdDeathregCorr.getDrId());
		birthDeathCFCInterface.setOrgId(tbBdDeathregCorr.getOrgId());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		 if (tbDeathregDTO.getNumberOfCopies() != null) {
				birthDeathCFCInterface.setCopies(tbDeathregDTO.getNumberOfCopies());
			} else {
				birthDeathCFCInterface.setCopies(0l);
			}
		
		//birthDeathCFCInterface.setCopies(tbDeathregDTO.getNumberOfCopies());
		cfcInterfaceJpaRepository.save(birthDeathCFCInterface);

		if ((tbDeathregDTO.getDocumentList() != null) && !tbDeathregDTO.getDocumentList().isEmpty()) {
			boolean status = fileUploadService.doFileUpload(tbDeathregDTO.getDocumentList(), requestDTO);
		}

		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("ApplicationId",applicationId);
		   TbBdDeathregCorrDTO tbBdDeathregCorrDTO = new TbBdDeathregCorrDTO();
		   
		   
		  //when BPM is not applicable
		  String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),tbDeathregDTO.getOrgId());
			if(processName==null) {
				tbDeathregDTO.setApplicationId(applicationId);
			BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorrDTO);
			tbBdDeathregCorrDTO.setApmApplicationId(applicationId);
	        updateDeathApproveStatus(tbBdDeathregCorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBdDeathregCorrDTO.setDeathWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    	updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(),MainetConstants.WorkFlow.Decision.APPROVED, tbDeathregDTO.getOrgId());
	    	//certificate generation/update
	    	iDeathRegistrationService.updateDeathRegCorrApprove(tbBdDeathregCorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	
	    	// save data to death registration entity after final approval 
	    	tbDeathregDTO.setDrId(tbDeathregDTO.getDrId());
	    	tbDeathregDTO.setApmApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
	    	iDeathRegistrationService.saveDeathRegDetOnApproval(tbDeathregDTO);
	    	iDeathRegistrationService.updatNoOfcopyStatus(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), tbDeathregDTO.getDrId(), tbDeathregDTO.getNumberOfCopies());
			}else {
				deathRegistrationRepository.updateNoOfIssuedCopy(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), BndConstants.OPEN);
			}
		  if(serviceMas.getSmFeesSchedule()==0 || tbDeathregDTO.getAmount()==0.0)
			{
			  tbDeathregDTO.setServiceId(serviceMas.getSmServiceId());
		  initializeWorkFlowForFreeService(tbDeathregDTO);
			}else {
		  setAndSaveChallanDtoOffLine(deathCorrModel.getOfflineDTO(), deathCorrModel);
			}
		  Organisation organisation = iOrganisationDAO.getOrganisationById(tbDeathregDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		  if(tbDeathregDTO.getLangId()==1) {
			  tbDeathregDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
			}
			else {
				tbDeathregDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
			}
	       smsAndEmail(tbDeathregDTO, organisation);
		  
		  return map;

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url, 
			String workFlowFlag) {
		try {

			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();

			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			ApplicationMetadata applicationMetadata = new ApplicationMetadata();
			applicationMetadata.setApplicationId(workflowActionDto.getApplicationId());
			//applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowMas.getWfId());
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);
			//applicationMetadata.setIsScrutinyApplicable(true);
			//applicationMetadata.setIsFreeService(true);

			ApplicationSession appSession = ApplicationSession.getInstance();

			TaskAssignment assignment = new TaskAssignment();

			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setServiceEventId(-1L);
			String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
			assignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

			assignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));
			assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
			assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
			assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
			assignment.setServiceId(workFlowMas.getService().getSmServiceId());
			assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
			assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
			assignment.setUrl(url);

			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).initiateWorkflow(workflowProcessParameter);

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
		}
		return null;

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getDeathRegCorrApplnData(@QueryParam("applnId") Long applnId, @QueryParam("orgId") Long orgId) {

		List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredCorrAppliDetail(applnId, orgId);

		List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();
		if (tbDeathreg != null) {
			tbDeathreg.forEach(entity -> {
				TbDeathregDTO dto = new TbDeathregDTO();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}

		return listDTO;
	}

	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbBdDeathregCorrDTO> getDeathRegisteredAppliDetailFromApplnId(@QueryParam("applnId") Long applnId, @QueryParam("orgId") Long orgId) {
		List<TbBdDeathregCorr> tbDeathregcorr = iDeathRegCorrDao.getDeathRegisteredAppliDetailFromApplnId(applnId, orgId);
		List<TbBdDeathregCorrDTO> listDTO = new ArrayList<TbBdDeathregCorrDTO>();
		if (tbDeathregcorr != null) {
			tbDeathregcorr.forEach(entity -> {
				TbBdDeathregCorrDTO dto = new TbBdDeathregCorrDTO();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}
		return listDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getDeathRegApplnData(Long drId, Long orgId) {
		List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredCorrAppliDetail(drId, orgId);

		List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();
		if (tbDeathreg != null) {
			tbDeathreg.forEach(entity -> {
				TbDeathregDTO dto = new TbDeathregDTO();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				if(lokkup.getLookUpDesc() != null) {
					dto.setDrSex(lokkup.getLookUpDesc());
				}
				dto.getMedicalMasterDto().setMcOthercond(entity.getMedicalMaster().getMcOthercond());
				dto.getMedicalMasterDto().setMcDeathManner(entity.getMedicalMaster().getMcDeathManner());
				dto.getMedicalMasterDto().setMcMdattndName(entity.getMedicalMaster().getMcMdattndName());
				dto.getMedicalMasterDto().setMcMdSuprName(entity.getMedicalMaster().getMcMdSuprName());
				dto.getMedicalMasterDto().setMcInteronset(entity.getMedicalMaster().getMcInteronset());
				dto.getMedicalMasterDto().setMcVerifnDate(entity.getMedicalMaster().getMcVerifnDate());
				listDTO.add(dto);
			});
		}

		return listDTO;
	}

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {
        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
    }

    
	@Override
	@Transactional(rollbackFor=Exception.class)
	@WebMethod(exclude = true)
	public void updateDeathApproveStatus(TbBdDeathregCorrDTO tbBdDeathregCorrDTO, String status, String lastDecision){
		
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		TbDeathreg TbDeathregEntity=new TbDeathreg();
		cfcApplEntiry.setApmApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(tbBdDeathregCorrDTO.getApmApplicationId(), tbBdDeathregCorrDTO.getOrgId());
	    BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
	    final Organisation organisation = new Organisation();
        organisation.setOrgid(tbBdDeathregCorrDTO.getOrgId());
        cfcApplEntiry.setTbOrganisation(organisation);
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
	    cfcApplEntiry.setUpdatedBy(tbBdDeathregCorrDTO.getUserId());
		TbDeathregEntity.setDrId(tbBdDeathregCorrDTO.getDrId());
		TbDeathregEntity.setUpdatedBy(tbBdDeathregCorrDTO.getUserId());
	    TbDeathregEntity.setUpdatedDate(new Date());
	    TbDeathregEntity.setLmoddate(new Date());
		if(lastDecision.equals("REJECTED")){
		    cfcApplEntiry.setApmAppRejFlag("R");
		    cfcApplEntiry.setAppAppRejBy(tbBdDeathregCorrDTO.getSmServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag("C");
		    cfcApplEntiry.setApmApplicationDate(new Date());
		    TbDeathregEntity.setDrStatus("C");  
		}
		else if(status.equals("APPROVED") && lastDecision.equals("PENDING")){
			cfcApplEntiry.setApmApplSuccessFlag("P");
			cfcApplEntiry.setApmApprovedBy(tbBdDeathregCorrDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			TbDeathregEntity.setDrStatus("I");	
		}
		else if(status.equals("APPROVED") && lastDecision.equals("CLOSED")){
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setApmApprovedBy(tbBdDeathregCorrDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(tbBdDeathregCorrDTO.getDrRegdate());
			cfcApplEntiry.setApmApplicationDate(new Date());
			TbDeathregEntity.setDrStatus("C");
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updateDeathWorkFlowStatus(Long drId, String taskNamePrevious, Long orgId) {
		deathRegCorrectionRepository.updateWorkFlowStatus(drId, orgId, taskNamePrevious);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<MedicalMasterDTO> getDeathRegApplnDataFromMedicalCorr(Long drId, Long orgId) {
		List<MedicalMaster> tbDeathregcorr = iDeathRegCorrDao.getDeathRegApplnDataFromMedicalCorr(drId, orgId);
		List<MedicalMasterDTO> listDTO = new ArrayList<MedicalMasterDTO>();
		if (tbDeathregcorr != null) {
			tbDeathregcorr.forEach(entity -> {
				MedicalMasterDTO dto = new MedicalMasterDTO();
				BeanUtils.copyProperties(entity, dto);
				//LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				//dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}
		return listDTO;
	}
    /*
	@Override
	@Transactional
	public List<CemeteryMasterDTO> getDeathRegApplnDataFromCemetryCorr(Long drId, Long orgId) {
		List<CemeteryMaster> tbDeathregcorr = iDeathRegCorrDao.getDeathRegApplnDataFromCemetryCorr(drId, orgId);
		List<CemeteryMasterDTO> listDTO = new ArrayList<CemeteryMasterDTO>();
		if (tbDeathregcorr != null) {
			tbDeathregcorr.forEach(entity -> {
				CemeteryMasterDTO dto = new CemeteryMasterDTO();
				BeanUtils.copyProperties(entity, dto);
				//LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				//dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}
		return listDTO;
	}
    */

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<MedicalMasterCorrectionDTO> getDeathRegApplnDataFromMedicalMasCorr(Long drCorrId, Long orgId) {
		List<MedicalMasterCorrection> tbDeathregcorr = iDeathRegCorrDao.getDeathRegApplnDataFromMedicalMasCorr(drCorrId, orgId);
		List<MedicalMasterCorrectionDTO> listDTO = new ArrayList<MedicalMasterCorrectionDTO>();
		if (tbDeathregcorr != null) {
			tbDeathregcorr.forEach(entity -> {
				MedicalMasterCorrectionDTO dto = new MedicalMasterCorrectionDTO();
				BeanUtils.copyProperties(entity, dto);
				//LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				//dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}
		return listDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<DeceasedMasterCorrDTO> getDeathRegApplnDataFromDecaseCorr(Long drCorrId, Long orgId) {
		List<DeceasedMasterCorrection> tbDeathregcorr = iDeathRegCorrDao.getDeathRegApplnDataFromDecaseCorr(drCorrId, orgId);
		List<DeceasedMasterCorrDTO> listDTO = new ArrayList<DeceasedMasterCorrDTO>();
		if (tbDeathregcorr != null) {
			tbDeathregcorr.forEach(entity -> {
				DeceasedMasterCorrDTO dto = new DeceasedMasterCorrDTO();
				BeanUtils.copyProperties(entity, dto);
				//LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
				//dto.setDrSex(lokkup.getLookUpDesc());
				listDTO.add(dto);
			});
		}
		return listDTO;
	}

	@Transactional
	@GET
	@Path(value = "/searchDeathCorrectionData")
	public List<TbDeathregDTO> searchDeathCorrectionData(@QueryParam("drCertNo") String drCertNo,
			@QueryParam("applnId") Long applnId, @QueryParam("year") String year,
			@QueryParam("drRegno") String drRegno, @QueryParam("drDod") Date drDod,
			@QueryParam("drDeceasedname") String drDeceasedname, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DR", orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		final String DeathWFStatus ="APPROVED";
		List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredAppliDetail(drCertNo, applnId, year, drRegno,
				drDod, drDeceasedname, orgId, smServiceId,null);
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();
			if (tbDeathreg != null) {
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					dto.setAgePeriodUnit((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdAgeperiodId(), orgId, "APG")).getLookUpCode());
					dto.setDeathplaceType((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getCpdDeathplaceType()), orgId, "DPT")).getLookUpCode());
					HospitalMasterDTO hospitalMasterDTO = iHospitalMasterService.getHospitalById(entity.getHiId());
				    dto.setHospital(hospitalMasterDTO.getHiName());
				    if(entity.getCeId()!=null && entity.getCeId()!=0) {
				    CemeteryMasterDTO cemeteryMasterDTO=iCemeteryMasterService.getCemeteryById(entity.getCeId());
				    dto.setCemetery(cemeteryMasterDTO.getCeName());
				    }
					dto.setReligion((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdReligionId(), orgId, "RLG")).getLookUpCode());
					dto.setEducation((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdEducationId(), orgId, "EDU")).getLookUpCode());
					dto.setMaritalStatus((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdMaritalStatId(), orgId, "MAR")).getLookUpCode());
					dto.setOccupation((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdOccupationId(), orgId, "OCU")).getLookUpCode());
					dto.setRegistrationUnit((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdRegUnit(), orgId, "REU")).getLookUpCode());
					dto.setAttentiontype((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdAttntypeId(), orgId, "ATD")).getLookUpCode());
					dto.setDeathcause((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getCpdDeathcauseId(), orgId, "DCA")).getLookUpCode());
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					//dto.setDeathManner((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(medicalDto.getMcDeathManner()), orgId, "BDM")).getLookUpCode());
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
						dto.setDeathManner((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(medicalDto.getMcDeathManner()), orgId, "BDM")).getLookUpCode());
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
					LookUp lokkup = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
					dto.setDrSex(lokkup.getDescLangFirst());
					dto.setDrSexMar(lokkup.getDescLangSecond());
					listDTO.add(dto);

				});
			}
			return listDTO;
		
	}




	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathRegCorrectionModel deathCorrModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DRC, deathCorrModel.getTbDeathregDTO().getOrgId());
		offline.setApplNo(deathCorrModel.getTbDeathregDTO().getApmApplicationId());
		offline.setAmountToPay(deathCorrModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ", Arrays.asList(deathCorrModel.getRequestDTO().getfName(),
				deathCorrModel.getRequestDTO().getmName(), deathCorrModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		String wardName = "";
		 if(deathCorrModel.getRequestDTO().getWardNo()!=0L && deathCorrModel.getRequestDTO().getWardNo()!=null) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(deathCorrModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode ="";
		 if(deathCorrModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(deathCorrModel.getRequestDTO().getPincodeNo());
		 }
		String applicantAddress = String.join(" ",
				Arrays.asList(deathCorrModel.getRequestDTO().getBldgName(),
						deathCorrModel.getRequestDTO().getBlockName(), deathCorrModel.getRequestDTO().getRoadName(),wardName,
						deathCorrModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		
		offline.setMobileNumber(deathCorrModel.getRequestDTO().getMobileNo());
		offline.setEmailId(deathCorrModel.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		if(deathCorrModel.getChargesInfo()!=null) { 
			for (ChargeDetailDTO dto : deathCorrModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(deathCorrModel.getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			deathCorrModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			printDto.setSubject(printDto.getSubject()+" - "+deathCorrModel.getTbDeathregDTO().getDrRegno());
			deathCorrModel.setReceiptDTO(printDto);
			deathCorrModel.setSuccessMessage(deathCorrModel.getAppSession().getMessage("adh.receipt"));
		}
		
	}

	private void initializeWorkFlowForFreeService(TbDeathregDTO requestDto) {
		boolean checkList = false;
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DRC,
				requestDto.getOrgId());
		boolean loiChargeApplflag = false;
        if (StringUtils.equalsIgnoreCase(serviceMas.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
            loiChargeApplflag = true;
        }
		if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
			checkList = true;
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();

		applicantDto.setApplicantFirstName(requestDto.getDrInformantName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.BIRTH_DEATH));
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(requestDto.getUserId());

		applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setIsLoiApplicable(loiChargeApplflag);
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@POST
	@Path("/saveDeathCorrectionFromPortal")
	public TbDeathregDTO saveDeathCorrectionDataForPortal(TbDeathregDTO tbDeathregDTO) {
		final RequestDTO requestDTO = tbDeathregDTO.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DRC, tbDeathregDTO.getOrgId());

		TbBdDeathregCorr tbBdDeathregCorr = new TbBdDeathregCorr();
		TbBdDeathregCorrHistory tbBdDeathregCorrHistory = new TbBdDeathregCorrHistory();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		tbDeathregDTO.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		// death correction entry
		BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorr);

		if (tbDeathregDTO.getDrRegdate() != null) {
			tbBdDeathregCorr.setDrRegdate(tbDeathregDTO.getDrRegdate());
		} else {
			tbBdDeathregCorr.setDrRegdate(new Date());
		}
		tbBdDeathregCorr.setLmoddate(new Date());
		tbBdDeathregCorr.setDrId(tbDeathregDTO.getDrId());
		tbBdDeathregCorr.setDrSex(tbDeathregDTO.getDrSex());
		tbBdDeathregCorr.setDrDeathaddr(tbDeathregDTO.getDrDeathaddr());
		tbBdDeathregCorr.setOrgId(tbDeathregDTO.getOrgId());
		tbBdDeathregCorr.setDrStatus("O");
		tbBdDeathregCorr.setDeathWFStatus("OPEN");
		if(tbDeathregDTO.getMedicalMasterDto()!=null) {
		tbBdDeathregCorr.setMcMdSuprName(tbDeathregDTO.getMedicalMasterDto().getMcMdSuprName());
		tbBdDeathregCorr.setMcOthercond(tbDeathregDTO.getMedicalMasterDto().getMcOthercond());
		}
		if (tbDeathregDTO.getCorrCategory() != null && !tbDeathregDTO.getCorrCategory().isEmpty()) {
			tbBdDeathregCorr.setCorrCategory(String.join(",", tbDeathregDTO.getCorrCategory()));
		}
		tbBdDeathregCorr.setUserId(tbDeathregDTO.getRequestDTO().getUserId());
		deathRegCorrectionRepository.save(tbBdDeathregCorr);
		

		// death registration Correction history
		BeanUtils.copyProperties(tbBdDeathregCorr, tbBdDeathregCorrHistory);
		tbBdDeathregCorrHistory.setAction(BndConstants.DRC);
		tbBdDeathregCorrHistory.setDrStatus("O");
		deathRegCorrHistoryRepository.save(tbBdDeathregCorrHistory);

		// RequestDTO entry
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode("F");
		//requestDTO.setReferenceId(String.valueOf(tbBdDeathregCorr.getDrCorrId()));
		//requestDTO.setUserId(tbDeathregDTO.getUserId());
		requestDTO.setDeptId(tbDeathregDTO.getDeptId());
		//requestDTO.setMobileNo(tbDeathregDTO.getMobileNo());
		if(serviceMas.getSmFeesSchedule()==0)
		{
			requestDTO.setPayStatus("F");
		}else {
			requestDTO.setPayStatus("Y");
		}
		requestDTO.setLangId(Long.valueOf(tbDeathregDTO.getLangId()));
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		
		// Generate the Application Number #111859 By Arun
		requestDTO.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
				PrefixConstants.STATUS_ACTIVE_PREFIX));
		requestDTO.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		requestDTO.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String monthStr = localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue()
				: String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth()
				: String.valueOf(localDate.getDayOfMonth());
		requestDTO.setCustomField(String.valueOf(monthStr + "" + dayStr));
		///
		final Long applicationId = applicationService.createApplication(requestDTO);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}

		requestDTO.setApplicationId(applicationId);
		requestDTO.setReferenceId(String.valueOf(applicationId));
		tbDeathregDTO.setApplicationId(applicationId);
		if ((applicationId != null) && (applicationId != 0)) {
			tbDeathregDTO.setApmApplicationId(applicationId);
		}

		//death interface entry
		BeanUtils.copyProperties(tbDeathregDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		tbBdDeathregCorr.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(tbBdDeathregCorr.getDrId());
		birthDeathCFCInterface.setOrgId(tbBdDeathregCorr.getOrgId());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		 if (tbDeathregDTO.getNumberOfCopies() != null) {
				birthDeathCFCInterface.setCopies(tbDeathregDTO.getNumberOfCopies());
			} else {
				birthDeathCFCInterface.setCopies(0l);
			}
		 birthDeathCFCInterface.setUserId(tbDeathregDTO.getRequestDTO().getUserId());
		//birthDeathCFCInterface.setCopies(tbDeathregDTO.getNumberOfCopies());
		cfcInterfaceJpaRepository.save(birthDeathCFCInterface);

		if ((tbDeathregDTO.getDocumentList() != null) && !tbDeathregDTO.getDocumentList().isEmpty()) {
			boolean status = fileUploadService.doFileUpload(tbDeathregDTO.getDocumentList(), requestDTO);
		}

		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("ApplicationId",applicationId);
		   TbBdDeathregCorrDTO tbBdDeathregCorrDTO = new TbBdDeathregCorrDTO();
		   tbBdDeathregCorrDTO.setOrgId(tbDeathregDTO.getOrgId());
		  //when BPM is not applicable
		  String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),tbDeathregDTO.getOrgId());
			if(processName==null) {
				tbDeathregDTO.setApplicationId(applicationId);
			BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorrDTO);
			tbBdDeathregCorrDTO.setApmApplicationId(applicationId);
	        updateDeathApproveStatus(tbBdDeathregCorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBdDeathregCorrDTO.setDeathWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    	updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(),MainetConstants.WorkFlow.Decision.APPROVED, tbDeathregDTO.getOrgId());
	    	//certificate generation/update
	    	iDeathRegistrationService.updateDeathRegCorrApprove(tbBdDeathregCorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	
	    	// save data to death registration entity after final approval 
	    	tbDeathregDTO.setDrId(tbDeathregDTO.getDrId());
	    	tbDeathregDTO.setApmApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
	    	iDeathRegistrationService.saveDeathRegDetOnApproval(tbDeathregDTO);
			}else {
				deathRegistrationRepository.updateNoOfIssuedCopy(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), BndConstants.OPEN);
			}
			tbDeathregDTO.setServiceId(serviceMas.getSmServiceId());
		  if(serviceMas.getSmFeesSchedule()==0 || tbDeathregDTO.getAmount()==0.0)
			{
		   initializeWorkFlowForFreeService(tbDeathregDTO);
		   Organisation organisation = iOrganisationDAO.getOrganisationById(tbDeathregDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		   tbDeathregDTO.setUserId(tbDeathregDTO.getRequestDTO().getUserId());
	       smsAndEmail(tbDeathregDTO, organisation);
			}
		  
		  
		  return tbDeathregDTO;

	}

	@Override
	@Transactional
	public List<TbDeathregDTO> getDeathDataForCorr(TbDeathregDTO tbDeathregDTO) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DR", tbDeathregDTO.getOrgId());
		tbDeathregDTO.setServiceId(serviceMas.getSmServiceId());
		List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathDataForCorr(tbDeathregDTO);
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();
			
			if (tbDeathreg != null) {
			
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
					if(entity.getMedicalMaster()!=null)
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
				       if(entity.getDeceasedMaster()!=null)
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
					LookUp lokkup = null;
					if(entity.getDrSex()!=null) {
					 lokkup = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), tbDeathregDTO.getOrgId(), GENDER);
					if (lokkup != null) {
						dto.setDrSex(lokkup.getDescLangFirst());
						dto.setDrSexMar(lokkup.getDescLangSecond());
					}
					}
					
					listDTO.add(dto);

				});
			}
			return listDTO;
	}
	

	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(TbDeathregDTO dto,Organisation organisation)
	{//sms email for death correction
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApplicationId()));
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(dto.getAmount()==null) {
			dto.setAmount(0.0d);
		}
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		smdto.setServName(dto.getRequestDTO().getServiceShortCode());
		
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.DEATH_REG_CORR_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}
	
	@Override
	@Transactional
	@POST
	@Path("/getDeathByApplIdPortal/{applicationId}/{orgId}")
	public TbDeathregDTO getDeathByApplId(@PathParam("applicationId") Long applicationId,
			@PathParam("orgId") Long orgId) {
		TbDeathregDTO dto = new TbDeathregDTO();
		List<TbBdDeathregCorrDTO> tbDeathRegCorrDtoList = getDeathRegisteredAppliDetailFromApplnId(applicationId,
				orgId);
		if (tbDeathRegCorrDtoList != null) {
			BeanUtils.copyProperties(tbDeathRegCorrDtoList.get(0), dto);
			dto.getMedicalMasterDto().setMcOthercond(tbDeathRegCorrDtoList.get(0).getMcOthercond());
			dto.getMedicalMasterDto().setMcMdSuprName(tbDeathRegCorrDtoList.get(0).getMcMdSuprName());
			long lookupId = CommonMasterUtility.lookUpIdByLookUpDescAndPrefix(tbDeathRegCorrDtoList.get(0).getDrSex(),
					GENDER, orgId);
			dto.setDrSex(String.valueOf(lookupId));
			Long noOfCopies = issueDeathsertiDao.getNoOfCopies(applicationId.toString(), orgId);
			if (noOfCopies != null) {
				dto.setNumberOfCopies(noOfCopies);
			} else {
				dto.setNumberOfCopies(0L);
			}
		}
		return dto;

	}
	
	@Override
	@Transactional
	public void updateNoOfIssuedCopy(Long drId,Long orgId,String DeathWFStatus) {
		deathRegistrationRepository.updateNoOfIssuedCopy(drId,orgId,DeathWFStatus);
	}
	
	@Override
    @Transactional
    public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId) {
        boolean updateFlag = false;
        try {
            updateWorkflowTaskAction(taskAction, smServiceId);
            updateFlag = true;
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return updateFlag;
    }
	
	@Transactional
	@Override
    public WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {
        WorkflowTaskActionResponse workflowResponse = null;
        try {
            String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getProcessName(serviceId, taskAction.getOrgId());
            if (StringUtils.isNotBlank(processName)) {
                WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
                workflowDto.setProcessName(processName);
                workflowDto.setWorkflowTaskAction(taskAction);
                workflowResponse = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return workflowResponse;
    }

	@Override
	@Transactional
	public String executeFinalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId,TbDeathregDTO tbDeathregDTO,TbBdDeathregCorrDTO tbBdDeathregCorrDTO) {
		String certificateno=null;
		tbBdDeathregCorrDTO.setApmApplicationId(taskAction.getApplicationId());
		tbBdDeathregCorrDTO.setSmServiceId(smServiceId);
        tbBdDeathregCorrDTO.setOrgId(tbDeathregDTO.getOrgId());
        tbDeathregDTO.setApmApplicationId(taskAction.getApplicationId());
		updateDeathApproveStatus(tbBdDeathregCorrDTO,taskAction.getDecision(),MainetConstants.WorkFlow.Status.CLOSED);
    	tbBdDeathregCorrDTO.setDeathWfStatus(taskAction.getDecision());  
    	updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(),taskAction.getDecision(), tbDeathregDTO.getOrgId());
    	//certificate generation/update
    	BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorrDTO);
    	certificateno=iDeathRegistrationService.updateDeathRegCorrApprove(tbBdDeathregCorrDTO,taskAction.getDecision(),MainetConstants.WorkFlow.Status.CLOSED);
    	
    	// save data to death registration entity after final approval 
    	tbDeathregDTO.setDrId(tbDeathregDTO.getDrId());
    	tbDeathregDTO.setApmApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
    	TbDeathregDTO saveDeathDet = iDeathRegistrationService.saveDeathRegDetOnApproval(tbDeathregDTO);
    	iDeathRegistrationService.updatNoOfcopyStatus(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), tbDeathregDTO.getDrId(), saveDeathDet.getNumberOfCopies());
    	tbDeathregDTO.setDrCertNo(certificateno);
		updateWorkflowTaskAction(taskAction, smServiceId);
		iDeathRegistrationService.smsAndEmailApproval(tbDeathregDTO,taskAction.getDecision());
		return certificateno;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeathRemark(Long drId, String deathRegremark,Long orgId) {
		deathRegistrationRepository.updateDeathRemark(drId,orgId,deathRegremark);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeathCorrectionRemark(Long drCorrId, String corrAuthRemark,Long orgId) {
		deathRegCorrectionRepository.updateDeathCorrectionRemark(drCorrId, orgId, corrAuthRemark);
	}
	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getDeathRegAppliDetailForDataEntry(@QueryParam("drCertNo") String drCertNo,
			@QueryParam("applnId") Long applnId, @QueryParam("year") String year,
			@QueryParam("applicationId") String drRegno, @QueryParam("drDod") Date drDod,
			@QueryParam("drDeceasedname") String drDeceasedname, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DED", orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		
		
			List<TbDeathreg> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredAppliDetail(drCertNo, applnId, year, drRegno,
				drDod, drDeceasedname, orgId, smServiceId,null);
			
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();

			if (tbDeathreg != null) {
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
					LookUp lokkup = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()), orgId, GENDER);
					dto.setDrSex(lokkup.getDescLangFirst());
					dto.setDrSexMar(lokkup.getDescLangSecond());
					listDTO.add(dto);

				});
			}
			return listDTO;
			
		}
	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getDeathRegAppliDetailForDataEntryTemp(@QueryParam("drCertNo") String drCertNo,
			@QueryParam("applnId") Long applnId, @QueryParam("year") String year,
			@QueryParam("applicationId") String drRegno, @QueryParam("drDod") Date drDod,
			@QueryParam("drDeceasedname") String drDeceasedname, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("DED", orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		
		
			List<TbDeathregTemp> tbDeathreg = iDeathRegCorrDao.getDeathRegisteredAppliDetailTemp(drCertNo, applnId, year, drRegno,
				drDod, drDeceasedname, orgId, smServiceId,null);
			
			List<TbDeathregDTO> listDTO = new ArrayList<TbDeathregDTO>();

			if (tbDeathreg != null) {
				tbDeathreg.forEach(entity -> {
					TbDeathregDTO dto = new TbDeathregDTO();
					MedicalMasterDTO medicalDto= new MedicalMasterDTO();
					DeceasedMasterDTO deceasedDto = new DeceasedMasterDTO();
					if(entity!=null) {
					BeanUtils.copyProperties(entity, dto);
				        BeanUtils.copyProperties(entity.getMedicalMaster(), medicalDto);
					BeanUtils.copyProperties(entity.getDeceasedMaster(), deceasedDto);
					}
					dto.setMedicalMasterDto(medicalDto);
					dto.setDeceasedMasterDTO(deceasedDto);
				/*
				 * LookUp lokkup = CommonMasterUtility
				 * .getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()),
				 * orgId, GENDER); dto.setDrSex(lokkup.getDescLangFirst());
				 * dto.setDrSexMar(lokkup.getDescLangSecond());
				 */
					listDTO.add(dto);

				});
			}
			return listDTO;
			
		}

}
