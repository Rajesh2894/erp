package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dao.IssuenceOfDeathCertificateDao;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.DeathCertEntity;
import com.abm.mainet.bnd.domain.TbBdCertCopy;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.repository.BirthDeathCertificateCopyRepository;
import com.abm.mainet.bnd.repository.BirthDeathCfcInterfaceRepository;
import com.abm.mainet.bnd.repository.CertCopyRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.DeathCertificateRepository;
import com.abm.mainet.bnd.repository.DeathRegistrationRepository;
import com.abm.mainet.bnd.ui.model.DeathRegistrationCertificateModel;
import com.abm.mainet.bnd.ui.model.NacForDeathRegModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

/**
 * 
 * @author vishwanath.s
 *
 */
@Service
@WebService(endpointInterface="com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService")
@Produces(value= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@Api(value = "/issuanceDeathCertService")
@Consumes(value= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE})
@Path(value="/issuanceDeathCertService")
public class IssuenceOfDeathCertificateServiceImpl implements IssuenceOfDeathCertificateService {

	@Autowired
	private IssuenceOfDeathCertificateDao issueDeathsertiDao;

	@Resource
	private ApplicationService applicationService;
	
	@Autowired
	private ReceiptRepository receiptRepository;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private CfcInterfaceJpaRepository tbBdCfcInterfaceJpaRepository;
	
	@Resource
	private BirthDeathCertificateCopyRepository birthDeathCertificateCopyRepository;
	
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Autowired
	private DeathRegistrationRepository deathRegistrationRepository;
	 
	@Autowired
	private IChallanService challanService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Resource
    private TbDepartmentJpaRepository tbDepartmentJpaRepository;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
		
	@Resource
	private DeathCertificateRepository deathCertificateRepositorys;
	
	@Resource
	private IFileUploadService fileUploadService;
	
	@Autowired
	private CertCopyRepository certCopyRepository;
	
	@Autowired
	private IOrganisationDAO organisationDAO;
	
	@Autowired
	private IDeathRegistrationService iDeathRegistrationService;
	
	@Autowired
	private BirthDeathCfcInterfaceRepository  birthDeathCfcInterfaceRepository;
	
	@Resource
    private IReceiptEntryService receiptEntryService;
	
	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = Exception.class)
	@Path(value="/searchDeath")
	@GET
	public TbDeathregDTO getDeathRegisteredAppliDetail(@QueryParam("certNo")String certNo, @QueryParam("regNo")String regNo, @QueryParam("regDate")String regDate, @QueryParam("applicnId")String applicnId,
			@QueryParam("orgId")Long orgId){
		TbDeathregDTO appliRegDetailDto = null;
		
		TbDeathreg applicationDetail1 = issueDeathsertiDao.getDeathRegisteredApplicantList(certNo, regNo, regDate,
				applicnId, orgId,null);
		if(applicationDetail1!=null) {
		if(applicationDetail1.getDrStatus().equals("Y") ){
			LookUp lokkup = null;
			if (applicationDetail1 != null) {
			if (applicationDetail1.getDrSex() != null) {
				lokkup = CommonMasterUtility
						.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(applicationDetail1.getDrSex()), orgId, GENDER);
			    }
				appliRegDetailDto = new TbDeathregDTO();
				BeanUtils.copyProperties(applicationDetail1, appliRegDetailDto);
				appliRegDetailDto.setDrSex(lokkup.getDescLangFirst());
				//RM-38982
				appliRegDetailDto.setDrSexMar(lokkup.getDescLangSecond());
				if(applicationDetail1.getCertNoCopies()!=null){
					appliRegDetailDto.setAlreayIssuedCopy(Long.valueOf(applicationDetail1.getCertNoCopies()));
				}else {
					appliRegDetailDto.setAlreayIssuedCopy(0L);
				}
			}
				appliRegDetailDto.setNumberOfCopies(null);
				
				if (applicationDetail1.getWardid() != null && applicationDetail1.getWardid() != 0) {
					appliRegDetailDto.setWardDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(applicationDetail1.getWardid()),
							orgId, "BWD").getDescLangFirst());
					
					appliRegDetailDto.setWardDescReg(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(applicationDetail1.getWardid()),
							orgId, "BWD").getDescLangSecond());
				}
			
		 }
		}
		
		return appliRegDetailDto;
	}
	
	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public TbDeathregDTO getDeathissuRegisteredAppliDetail(@QueryParam("certNo")String certNo, @QueryParam("regNo")String regNo, @QueryParam("regDate")String regDate, @QueryParam("applicnId")String applicnId,
			@QueryParam("orgId")Long orgId){
		TbDeathregDTO appliRegDetailDto = null;
		
		TbDeathreg applicationDetail1 = issueDeathsertiDao.getDeathRegisteredApplicantList(certNo, regNo, regDate,
				applicnId, orgId,null);
		if(applicationDetail1!=null) {
		if(applicationDetail1.getDrStatus().equals("Y") ){
			Long noOfCopies = issueDeathsertiDao.getNoOfCopies(applicnId, orgId);
			LookUp lokkup = null;
			if (applicationDetail1 != null) {
			if (applicationDetail1.getDrSex() != null) {
				lokkup = CommonMasterUtility
						.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(applicationDetail1.getDrSex()), orgId, GENDER);
			    }
				appliRegDetailDto = new TbDeathregDTO();
				BeanUtils.copyProperties(applicationDetail1, appliRegDetailDto);
				appliRegDetailDto.setDrSex(lokkup.getLookUpDesc());
				Long sum = 0L;
				
				sum = applicationDetail1.getCertNoCopies() != null && applicationDetail1.getDrManualCertno() != null ? 
						applicationDetail1.getCertNoCopies() + applicationDetail1.getDrManualCertno() : applicationDetail1.getCertNoCopies() != null ? 
							  sum + applicationDetail1.getCertNoCopies() : applicationDetail1.getDrManualCertno() != null ? sum + applicationDetail1.getDrManualCertno() : sum;
				
							  appliRegDetailDto.setAlreayIssuedCopy(sum);
			}
			if (appliRegDetailDto !=null) {
				appliRegDetailDto.setNumberOfCopies(noOfCopies);
			 }
			else {
				appliRegDetailDto.setNumberOfCopies(0L);
			}
			
		 }
		}
		
		return appliRegDetailDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public Map<String,Object> saveIssuanceDeathCertificateDetail(@RequestBody TbDeathregDTO birthRegDto,DeathRegistrationCertificateModel deathModel) {
		final RequestDTO commonRequest = birthRegDto.getRequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		birthRegDto.setOrgId(orgId);
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		// Get the serviceId from service.
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("IDC", birthRegDto.getOrgId());
		if (serviceMas != null) {
			commonRequest.setOrgId(birthRegDto.getOrgId());
			commonRequest.setServiceId(serviceMas.getSmServiceId());
			commonRequest.setApmMode("F");
			commonRequest.setUserId(birthRegDto.getUserId());
			
			commonRequest.setLangId(Long.valueOf(birthRegDto.getLangId()));

		}
		
		 //update the certificate no copies 
		String DeathWFStatus = "OPEN";
		 deathRegistrationRepository.updateNoOfIssuedCopy(birthRegDto.getDrId(), birthRegDto.getOrgId(),DeathWFStatus);
		 
		// Generate the Application Number #111859 By Arun
		commonRequest.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,PrefixConstants.STATUS_ACTIVE_PREFIX));
		commonRequest.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		commonRequest.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		String monthStr = localDate.getMonthValue() < 10 ? "0"+localDate.getMonthValue() : String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0"+localDate.getDayOfMonth() : String.valueOf(localDate.getDayOfMonth());
		commonRequest.setCustomField(String.valueOf(monthStr+""+dayStr));
		///
		
		final Long applicationId = applicationService.createApplication(commonRequest);
		birthRegDto.setApplicationNo(String.valueOf(applicationId));
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		BeanUtils.copyProperties(birthRegDto, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegDto.getDrId());
		birthDeathCFCInterface.setLmodDate(new Date());
		birthDeathCFCInterface.setUpdatedBy(birthRegDto.getUpdatedBy());
		birthDeathCFCInterface.setUserId(birthRegDto.getUpdatedBy());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		birthDeathCFCInterface.setUpdatedDate(new Date());
		birthDeathCFCInterface.setLgIpMac(birthRegDto.getLgIpMac());
		birthDeathCFCInterface.setLgIpMacUpd(birthRegDto.getLgIpMacUpd());
		if(birthRegDto.getNumberOfCopies()!=null) {
		birthDeathCFCInterface.setCopies(Long.valueOf(birthRegDto.getNumberOfCopies()));
		}else
		{
			birthDeathCFCInterface.setCopies(0L);
		}
		//birthReg.getParentDetail().setPdBrId(birthReg.getBrId());
		birthDeathCFCInterface.setOrgId(birthRegDto.getOrgId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);	
		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("ApplicationId",applicationId);
		//when BPM is not applicable
		  String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),orgId);
			if(processName==null) {
				birthRegDto.setApplicationId(applicationId);
		   updateDeathApproveStatus(birthRegDto,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	       updateBirthWorkFlowStatus(birthRegDto.getDrId(),MainetConstants.WorkFlow.Decision.APPROVED, orgId);
	       iDeathRegistrationService.updatNoOfcopyStatus(birthRegDto.getDrId(), orgId, birthRegDto.getDrId(), birthRegDto.getNumberOfCopies());
			}
	       if(serviceMas.getSmFeesSchedule()==0 || birthRegDto.getAmount()==0.0)
			{
			  initializeWorkFlowForFreeService(birthRegDto);
			}else {
		  setAndSaveChallanDtoOffLine(deathModel.getOfflineDTO(), deathModel);
			}
	       Organisation organisation = organisationDAO.getOrganisationById(birthRegDto.getOrgId(),MainetConstants.STATUS.ACTIVE);
	       if(birthRegDto.getLangId()==1) {
	    	   birthRegDto.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
			}
			else {
				birthRegDto.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
			}
	       smsAndEmail(birthRegDto, organisation);
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
	public String updateDeathApproveStatus(TbDeathregDTO tbDeathregDTO, String status , String lastDecision) {
		String finalcertificateNo=null;
      List<BirthDeathCFCInterface> birthDeathCFCInterface = birthDeathCfcInterfaceRepository.findData(tbDeathregDTO.getApplicationId());
		
		if ((birthDeathCFCInterface.get(0).getCopies() != 0)&& (birthDeathCFCInterface.get(0).getCopies() != null)) {
				 //Generate the Certificate Number start
		Long alreadyIssuCopy = tbDeathregDTO.getAlreayIssuedCopy();
		if (alreadyIssuCopy == null) {
			alreadyIssuCopy = 0L;
		}
		List<TbDeathreg> tbDeathreg = deathRegistrationRepository.findData(tbDeathregDTO.getDrId());
		String certnum = null;
		if ((!tbDeathreg.isEmpty())) {
			certnum = tbDeathreg.get(0).getDrCertNo();
		}
		TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();

		if ((certnum == null) || (certnum.equals("0"))) {
			SequenceConfigMasterDTO configMasterDTO = null;
			Long deptId = departmentService.getDepartmentIdByDeptCode(BndConstants.BND,
					PrefixConstants.STATUS_ACTIVE_PREFIX);
			configMasterDTO = seqGenFunctionUtility.loadSequenceData(tbDeathregDTO.getOrgId(), deptId,
					BndConstants.TB_DEATHREG, BndConstants.DR_CERT_NO);
			if (configMasterDTO.getSeqConfigId() == null) {
				Long certificateNo = seqGenFunctionUtility.generateSequenceNo(BndConstants.BND,
						BndConstants.TB_BD_CERT_COPY, BndConstants.CERT_NO, tbDeathregDTO.getOrgId(), "Y", null);
				String financialYear = UserSession.getCurrent().getCurrentDate();
				finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
						+ new DecimalFormat("000000").format(certificateNo);
			} else {
				CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
				finalcertificateNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
			}
				
				 //Generate the Certificate Number end
				tbBdCertCopy.setOrgid(tbDeathregDTO.getOrgId());
			    tbBdCertCopy.setDrId(tbDeathregDTO.getDrId());
			    tbBdCertCopy.setCopyNo(tbDeathregDTO.getNumberOfCopies());
			    tbBdCertCopy.setUpdatedBy(tbDeathregDTO.getUserId());
			    tbBdCertCopy.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
			    tbBdCertCopy.setUserId(tbDeathregDTO.getUserId());
			    tbBdCertCopy.setLgIpMac(tbDeathregDTO.getLgIpMac());
			    tbBdCertCopy.setLgIpMacUpd(tbDeathregDTO.getLgIpMacUpd());
			    tbBdCertCopy.setLmoddate(new Date());
			    tbBdCertCopy.setCertNo(finalcertificateNo);
			    tbBdCertCopy.setAPMApplicationId(Long.valueOf(tbDeathregDTO.getApplicationId()));
			    tbBdCertCopy.setStatus("N");
				 birthDeathCertificateCopyRepository.save(tbBdCertCopy);
				 deathRegistrationRepository.updateCertNo(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), finalcertificateNo);
				 return finalcertificateNo;
			}else {
				if((certnum!=null) || !certnum.equals("0")){
					finalcertificateNo = certnum;
					tbBdCertCopy.setOrgid(tbDeathregDTO.getOrgId());
				    tbBdCertCopy.setDrId(tbDeathregDTO.getDrId());
				    tbBdCertCopy.setCopyNo(tbDeathregDTO.getNumberOfCopies());
				    tbBdCertCopy.setUpdatedBy(tbDeathregDTO.getUserId());
				    tbBdCertCopy.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
				    tbBdCertCopy.setUserId(tbDeathregDTO.getUserId());
				    tbBdCertCopy.setLgIpMac(tbDeathregDTO.getLgIpMac());
				    tbBdCertCopy.setLgIpMacUpd(tbDeathregDTO.getLgIpMacUpd());
				    tbBdCertCopy.setLmoddate(new Date());
				    tbBdCertCopy.setCertNo(finalcertificateNo);
				    tbBdCertCopy.setAPMApplicationId(Long.valueOf(tbDeathregDTO.getApplicationId()));
				    tbBdCertCopy.setStatus("N");
					 birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					 deathRegistrationRepository.updateCertNo(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), finalcertificateNo);
					 return finalcertificateNo;
				}
			}
		}
		return null;
	}

	 @Override
	 @Transactional(rollbackFor=Exception.class)
	 @WebMethod(exclude = true)
	 public void updateBirthWorkFlowStatus(Long drId, String closed, Long orgId) {
		 deathRegistrationRepository.updateWorkFlowStatusForissuance(drId, orgId, closed);
	}

	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(rollbackFor=Exception.class)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,
			DeathRegistrationCertificateModel deathModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.IDC, deathModel.getTbDeathregDTO().getOrgId());
		offline.setApplNo(Long.valueOf(deathModel.getTbDeathregDTO().getApplicationNo()));
		offline.setAmountToPay(deathModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ", Arrays.asList(deathModel.getRequestDTO().getfName(),
				deathModel.getRequestDTO().getmName(), deathModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		 String wardName = "";
		 if(deathModel.getRequestDTO().getWardNo()!=0L && deathModel.getRequestDTO().getWardNo()!=null) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(deathModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode = "";
		 if(deathModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(deathModel.getRequestDTO().getPincodeNo());
		 }
		 String applicantAddress = String.join(" ",
				Arrays.asList(deathModel.getRequestDTO().getBldgName(),
						deathModel.getRequestDTO().getBlockName(), deathModel.getRequestDTO().getRoadName(),wardName,
						deathModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(deathModel.getRequestDTO().getMobileNo());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		if(deathModel.getChargesInfo()!=null) { 
			for (ChargeDetailDTO dto : deathModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
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

			deathModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			printDto.setSubject(printDto.getSubject()+" - "+deathModel.getTbDeathregDTO().getDrRegno());
			deathModel.setReceiptDTO(printDto);
			deathModel.setSuccessMessage(deathModel.getAppSession().getMessage("adh.receipt"));
		}
		
	}

	@Override
	public Map<String, Object> saveIssuanceDeathCertificateDetail(TbDeathregDTO tbDeathregDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TbServiceReceiptMasBean getReceiptNo(Long applicationNo, Long orgId) {
		 TbServiceReceiptMasBean dto=new TbServiceReceiptMasBean();
		// Long app=Long.parseLong(applicationNo);
		 TbServiceReceiptMasEntity entity= receiptRepository.getReceiptDetailsByAppId(applicationNo,orgId);
		 if(entity!=null) {
		 BeanUtils.copyProperties(entity, dto);
		 if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			 dto.setRmReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(entity.getFieldId(), entity.getSmServiceId(), entity.getSmServiceId(), entity.getRmDate(), entity.getOrgId()));
		   }
		 }
		
		return dto;
	}
    
	private void initializeWorkFlowForFreeService(TbDeathregDTO requestDto) {
		boolean checkList = false;
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.IDC,
				requestDto.getOrgId());
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

		applicationMetaData.setApplicationId(Long.valueOf(requestDto.getApplicationNo()));
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	@POST
	@Path("/saveIssuanceDeathCertificateFromPortal")
	public TbDeathregDTO saveIssuanceDeathCertificateFromPortal(@RequestBody TbDeathregDTO birthRegDto) {
		final RequestDTO commonRequest = birthRegDto.getRequestDTO();
		Long orgId = birthRegDto.getOrgId();
		birthRegDto.setOrgId(orgId);
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		// Get the serviceId from service.
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("IDC", birthRegDto.getOrgId());
		if (serviceMas != null) {
			commonRequest.setOrgId(birthRegDto.getOrgId());
			commonRequest.setServiceId(serviceMas.getSmServiceId());
			commonRequest.setApmMode("F");
			//commonRequest.setUserId(birthRegDto.getUserId());
			commonRequest.setLangId(Long.valueOf(birthRegDto.getLangId()));
			birthRegDto.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
			//commonRequest.setMobileNo(birthRegDto.getMobileNo());

		}
		
		 //update the certificate no copies 
		String DeathWFStatus = "OPEN";
		 deathRegistrationRepository.updateNoOfIssuedCopy(birthRegDto.getDrId(), birthRegDto.getOrgId(),DeathWFStatus);
		// Generate the Application Number #111859 By Arun
		commonRequest.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
				PrefixConstants.STATUS_ACTIVE_PREFIX));
		commonRequest.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
		commonRequest.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String monthStr = localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue()
				: String.valueOf(localDate.getMonthValue());
		String dayStr = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth()
				: String.valueOf(localDate.getDayOfMonth());
		commonRequest.setCustomField(String.valueOf(monthStr + "" + dayStr));
		///
		final Long applicationId = applicationService.createApplication(commonRequest);
		birthRegDto.setApplicationNo(String.valueOf(applicationId));
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		birthRegDto.setApmApplicationId(applicationId);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		BeanUtils.copyProperties(birthRegDto, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegDto.getDrId());
		birthDeathCFCInterface.setLmodDate(new Date());
		birthDeathCFCInterface.setUpdatedBy(birthRegDto.getUpdatedBy());
		birthDeathCFCInterface.setUserId(birthRegDto.getUpdatedBy());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		birthDeathCFCInterface.setUpdatedDate(new Date());
		birthDeathCFCInterface.setLgIpMac(birthRegDto.getLgIpMac());
		birthDeathCFCInterface.setLgIpMacUpd(birthRegDto.getLgIpMacUpd());
		if(birthRegDto.getNumberOfCopies()!=null) {
		birthDeathCFCInterface.setCopies(Long.valueOf(birthRegDto.getNumberOfCopies()));
		}else
		{
			birthDeathCFCInterface.setCopies(0L);
		}
		//birthReg.getParentDetail().setPdBrId(birthReg.getBrId());
		birthDeathCFCInterface.setOrgId(birthRegDto.getOrgId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);	
		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("ApplicationId",applicationId);
		//when BPM is not applicable
		  String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),orgId);
			if(processName==null) {
				birthRegDto.setApplicationId(applicationId);
		   updateDeathApproveStatus(birthRegDto,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	       updateBirthWorkFlowStatus(birthRegDto.getDrId(),MainetConstants.WorkFlow.Decision.APPROVED, orgId);
			}
			birthRegDto.setServiceId(serviceMas.getSmServiceId());
	       if(serviceMas.getSmFeesSchedule()==0 || birthRegDto.getAmount()==0.0)
			{
			  initializeWorkFlowForFreeService(birthRegDto);
			}else {
		 // setAndSaveChallanDtoOffLine(deathModel.getOfflineDTO(), deathModel);
			}
	       
	       Organisation organisation = organisationDAO.getOrganisationById(birthRegDto.getOrgId(),MainetConstants.STATUS.ACTIVE);
	       birthRegDto.setUserId(birthRegDto.getUpdatedBy());
	       if(birthRegDto.getLangId()==1) {
	    	   birthRegDto.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
			}
			else {
				birthRegDto.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
			}
	       smsAndEmail(birthRegDto, organisation);
		return birthRegDto;
	}

	@Override
	@POST
	@Path("/saveNacDeathCertificate")
	@WebMethod(exclude = true)
	@Transactional
	public DeathCertificateDTO saveDeathRegDetails(DeathCertificateDTO deathCertificateDTO,
			NacForDeathRegModel tbDeathregModel) {
		final RequestDTO requestDTO = tbDeathregModel.getRequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.NDR,
				deathCertificateDTO.getOrgId());
		DeathCertEntity deathCertificateEntity = new DeathCertEntity();

		// RequestDTO entry
		requestDTO.setOrgId(deathCertificateDTO.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode(BndConstants.FLAGF);
		if (serviceMas.getSmFeesSchedule() == 0) {
			requestDTO.setPayStatus(BndConstants.PAYSTSTUSFREE);
		} else {
			requestDTO.setPayStatus(BndConstants.PAYSTATUSCHARGE);
		}
		requestDTO.setUserId(deathCertificateDTO.getUserId());
		requestDTO.setfName(requestDTO.getfName());
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
			throw new FrameworkException("Application Not Generated");
		}
		requestDTO.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId);
		if ((applicationId != null) && (applicationId != 0)) {
			deathCertificateDTO.setApplnId(applicationId);
			deathCertificateDTO.setApplicationNo(applicationId);
		}
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),deathCertificateDTO.getOrgId());
		if (processName == null) {
			deathCertificateDTO.setDrStatus(BndConstants.BRSTATUSAPPROVED);
			deathCertificateDTO.setWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
		}
		BeanUtils.copyProperties(deathCertificateDTO, deathCertificateEntity);
		deathCertificateEntity.setApplicationNo(deathCertificateDTO.getApplicationNo());
		deathCertificateEntity.setDrStatus(BndConstants.STATUSOPEN);
		deathCertificateEntity.setSmServiceId(serviceMas.getSmServiceId());
		DeathCertEntity savedeath = deathCertificateRepositorys.save(deathCertificateEntity);

		if ((deathCertificateDTO.getDocumentList() != null) && !deathCertificateDTO.getDocumentList().isEmpty()) {
			fileUploadService.doFileUpload(deathCertificateDTO.getDocumentList(), requestDTO);
		}

		deathCertificateDTO.setApplicantMobilno(requestDTO.getMobileNo());
		deathCertificateDTO.setApplicantEmail(requestDTO.getEmail());
		deathCertificateDTO.setApplicantFname(requestDTO.getfName());
		deathCertificateDTO.setApplicantAddress(requestDTO.getCityName());
		if (processName == null) {
			deathCertificateDTO.setDrRId(savedeath.getDrRId());
			String certificateno = generateCertificate(deathCertificateDTO, MainetConstants.WorkFlow.Decision.APPROVED, MainetConstants.WorkFlow.Status.CLOSED);
		}
		if (serviceMas.getSmFeesSchedule() == 0) {
			// initializeWorkFlowForFreeService
			deathCertificateDTO.setSmServiceId(serviceMas.getSmServiceId());
			initializeWorkFlowForFreeService(deathCertificateDTO);
		} else {
			// workflow for paid service with charges and challan
			setAndSaveChallanDtoOffLine(tbDeathregModel.getOfflineDTO(), tbDeathregModel);
		}
		// smsAnd EmailService
		 Organisation organisation = UserSession.getCurrent().getOrganisation();
		 smsAndEmailNac(deathCertificateDTO, organisation);
		return deathCertificateDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	private void initializeWorkFlowForFreeService(DeathCertificateDTO requestDto) {
		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
			checkList = true;
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getApplicantFname());
		applicantDto.setServiceId(requestDto.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(BndConstants.BIRTH_DEATH));
		applicantDto.setMobileNo(requestDto.getApplicantMobilno());
		applicantDto.setUserId(requestDto.getUserId());

		applicationMetaData.setApplicationId(requestDto.getApplnId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Transactional
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, NacForDeathRegModel tbDeathregModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.NDR,
				tbDeathregModel.getDeathCertificateDTO().getOrgId());
		offline.setApplNo(tbDeathregModel.getDeathCertificateDTO().getApplicationNo());
		offline.setAmountToPay(String.valueOf(tbDeathregModel.getDeathCertificateDTO().getAmount()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ", Arrays.asList(tbDeathregModel.getRequestDTO().getfName(),
				tbDeathregModel.getRequestDTO().getmName(), tbDeathregModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		offline.setMobileNumber(tbDeathregModel.getDeathCertificateDTO().getApplicantMobilno());
		offline.setEmailId(tbDeathregModel.getDeathCertificateDTO().getApplicantEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		String wardName = "";
		 if(tbDeathregModel.getRequestDTO().getWardNo()!=0L && tbDeathregModel.getRequestDTO().getWardNo()!=null) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(tbDeathregModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode = "";
		 if(tbDeathregModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(tbDeathregModel.getRequestDTO().getPincodeNo());
		 }
		String applicantAddress = String.join(" ",
				Arrays.asList(tbDeathregModel.getRequestDTO().getBldgName(),
						tbDeathregModel.getRequestDTO().getBlockName(), tbDeathregModel.getRequestDTO().getRoadName(),wardName,
						tbDeathregModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		if (tbDeathregModel.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : tbDeathregModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(tbDeathregModel.getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			tbDeathregModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			tbDeathregModel.setReceiptDTO(printDto);
			tbDeathregModel.setSuccessMessage(tbDeathregModel.getAppSession().getMessage("adh.receipt"));
		}
	}


	@Override
	public String generateCertificate(DeathCertificateDTO deathCertificateDTO, String lastDecision, String status) {
   		Long drId = deathCertificateDTO.getDrRId();
		//DeathCertEntity deathCert = deathCertificateRepositorys.findOne(aplId);

		if ((deathCertificateDTO.getDemandedCopies() != 0) && (deathCertificateDTO.getDemandedCopies() != null)) {

			List<TbBdCertCopy> certCopy = certCopyRepository.findDeathCertData(drId);
			String finYear = UserSession.getCurrent().getCurrentDate();

			String certnum = null;
			String certnumcopy = null;
			if ((!certCopy.isEmpty())) {
				certnum = certCopy.get(0).getCertNo();
				certnumcopy = certnum.substring(3, 7);
			}

			if ((certnum == null) || (!certnumcopy.equals(finYear.substring(6, 10)))) {
				String finalcertificateNo = null;
				Long certificateNo = seqGenFunctionUtility.generateSequenceNo("BND", "TB_BD_CERT_COPY", "CERT_NO",
						deathCertificateDTO.getOrgId(), "Y", null);
				TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
				String financialYear = UserSession.getCurrent().getCurrentDate();
				finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
						+ new DecimalFormat("000000").format(certificateNo);
				tbBdCertCopy.setOrgid(deathCertificateDTO.getOrgId());
				tbBdCertCopy.setNacDrId(drId);
				tbBdCertCopy.setCopyNo(deathCertificateDTO.getDemandedCopies());
				tbBdCertCopy.setUpdatedBy(deathCertificateDTO.getUserId());
				tbBdCertCopy.setUpdatedDate(deathCertificateDTO.getUpdatedDate());
				tbBdCertCopy.setUserId(deathCertificateDTO.getUserId());
				tbBdCertCopy.setLgIpMac(deathCertificateDTO.getLgIpMac());
				tbBdCertCopy.setLgIpMacUpd(deathCertificateDTO.getLgIpMacUpd());
				tbBdCertCopy.setCertNo(finalcertificateNo);
				tbBdCertCopy.setLmoddate(new Date());
				tbBdCertCopy.setStatus("N");
				tbBdCertCopy.setAPMApplicationId(deathCertificateDTO.getApplicationNo());
				birthDeathCertificateCopyRepository.save(tbBdCertCopy);
				// saveCertcopy
				return finalcertificateNo;
			} else {
				if ((certnum != null) || (certnumcopy.equals(finYear.substring(6, 10)))) {
					String finalcertificateNo = null;
					TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
					finalcertificateNo = certnum;
					tbBdCertCopy.setOrgid(deathCertificateDTO.getOrgId());
					tbBdCertCopy.setNacDrId(drId);
					tbBdCertCopy.setCopyNo(deathCertificateDTO.getDemandedCopies());
					tbBdCertCopy.setUpdatedBy(deathCertificateDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(deathCertificateDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(deathCertificateDTO.getUserId());
					tbBdCertCopy.setLgIpMac(deathCertificateDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(deathCertificateDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(deathCertificateDTO.getApplicationNo());
					// saveCertcopy
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					return finalcertificateNo;
				}
			}
		}
		return null;
	}

	@Override
	public DeathCertificateDTO getNacDeathCertdetail(Long applnId, Long orgId) {
		Organisation organisation = organisationDAO.getOrganisationById(orgId,MainetConstants.STATUS.ACTIVE);
		DeathCertificateDTO deathDto = new DeathCertificateDTO();
		DeathCertEntity deathData = deathCertificateRepositorys.findNacDeathData(applnId, orgId);
		if (deathData != null) {
			BeanUtils.copyProperties(deathData, deathDto);
			String dateToString = Utility.dateToString(deathData.getDrDod());
			deathDto.setMcOthercond(dateToString.substring(6));
			deathDto.setApplicantAddress(organisation.getOrgAddress());
			String state = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdIdState(), organisation).getLookUpDesc();
			String district = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdIdDis(), organisation).getLookUpDesc();
			deathDto.setDrInformantAddr(district);
			deathDto.setDrMarInformantAddr(state);
		}
		return deathDto;
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(TbDeathregDTO dto,Organisation organisation)
	{
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(dto.getApplicationNo());
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
				BndConstants.BIRTH_DEATH, BndConstants.ISSUANCE_DEATH_URL_FOR_WORLFLOW_INIT, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmailNac(DeathCertificateDTO dto,Organisation organisation)
	{
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApplicationNo()));
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.NAC_DEATH_REG, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}

	@Override
	public void smsAndEmailApproval(TbDeathregDTO dto, String decision) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApplicationId()));
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smdto.setCc(decision);
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setRegNo(dto.getDrCertNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		String alertType = MainetConstants.BLANK;
		if(decision.equals(BndConstants.APPROVED)) {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
		}else {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		}
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BND, BndConstants.ISSUE_DEATH_APPR_URL, alertType, smdto, organisation, dto.getLangId());
	}
	
	@Override
	@Transactional
	@Path(value = "/getDeathIssuenceApplIdPortal/{applicationId}/{orgId}")
	@POST
	public TbDeathregDTO getDeathIssuenceApplId(@PathParam("applicationId") Long applicationId,
			@PathParam("orgId") Long orgId) {
		TbDeathregDTO dto = new TbDeathregDTO();
		dto = getDeathissuRegisteredAppliDetail(null, null, null, applicationId.toString(), orgId);
		return dto;

	}
}
