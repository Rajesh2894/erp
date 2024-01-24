package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dao.BirthRegDao;
import com.abm.mainet.bnd.dao.IBirthRegDraftDao;
import com.abm.mainet.bnd.dao.IBirthRegDraftTempDao;
import com.abm.mainet.bnd.dao.IssuenceOfBirthCertificateDao;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.BirthRegdraftEntity;
import com.abm.mainet.bnd.domain.BirthRegistrationCorrection;
import com.abm.mainet.bnd.domain.BirthRegistrationCorrectionHistory;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.BirthRegistrationEntityTemp;
import com.abm.mainet.bnd.domain.BirthRegistrationHistoryEntity;
import com.abm.mainet.bnd.domain.HospitalMaster;
import com.abm.mainet.bnd.domain.ParentDetail;
import com.abm.mainet.bnd.domain.ParentDetailHistory;
import com.abm.mainet.bnd.domain.ParentDetailTemp;
import com.abm.mainet.bnd.domain.TbBdCertCopy;
import com.abm.mainet.bnd.dto.BirthReceiptDTO;
import com.abm.mainet.bnd.dto.BirthRegDraftDto;
import com.abm.mainet.bnd.dto.BirthRegistrationCorrDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.ParentDetailDTO;
import com.abm.mainet.bnd.repository.BirthCertificateRepository;
import com.abm.mainet.bnd.repository.BirthDeathCertificateCopyRepository;
import com.abm.mainet.bnd.repository.BirthDeathCfcInterfaceRepository;
import com.abm.mainet.bnd.repository.BirthRegCorrectionHistoryRepository;
import com.abm.mainet.bnd.repository.BirthRegCorrectionRepository;
import com.abm.mainet.bnd.repository.BirthRegDraftRepository;
import com.abm.mainet.bnd.repository.BirthRegHistRepository;
import com.abm.mainet.bnd.repository.BirthRegRepository;
import com.abm.mainet.bnd.repository.BirthRegTempRepository;
import com.abm.mainet.bnd.repository.CertCopyRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.HospitalMasterRepository;
import com.abm.mainet.bnd.repository.ParentDetHistRepository;
import com.abm.mainet.bnd.ui.model.BirthCorrectionModel;
import com.abm.mainet.bnd.ui.model.BirthRegistrationModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
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
import io.swagger.annotations.ApiOperation;

@Service
@WebService(endpointInterface = "com.abm.mainet.bnd.service.IBirthRegService")
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Api(value = "/birthRegService")
@Path(value = "/birthRegService")
public class BirthRegService implements IBirthRegService {

	@Autowired
	private BirthRegDao birthRegDao;

	@Autowired
	private BirthRegRepository birthRegRepository;
	
	@Autowired
	private BirthRegHistRepository birthRegHistRepository;
	
	@Autowired
	private BirthRegCorrectionHistoryRepository birthRegCorreHistRepository;
	
	@Autowired
	private BirthCertificateRepository birthCertificateRepository;
	

	
	@Autowired
	private ParentDetHistRepository parentDetHistRepository;

	@Autowired
	private BirthRegCorrectionRepository birthRegCorrectionRepository;

	@Resource
	private TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	private CfcInterfaceJpaRepository tbBdCfcInterfaceJpaRepository;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Autowired
	private GroupMasterService groupMasterService;

    @Autowired
	private BirthRegDraftRepository birthRegDraftRepository;

	@Autowired
	private IBirthRegDraftDao iBirthRegDraftDao;

	@Autowired
	private BirthDeathCfcInterfaceRepository birthDeathCfcInterfaceRepository;

	@Autowired
	private CertCopyRepository certCopyRepository;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private BirthDeathCertificateCopyRepository birthDeathCertificateCopyRepository;
	
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
	private IOrganisationDAO iOrganisationDAO;
	
	@Autowired
	private InclusionOfChildNameService inclusionOfChildNameService;
	
	@Autowired
	IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private HospitalMasterRepository hospitalMasterRepository;
	
	@Resource
	private IssuenceOfBirthCertificateDao issuenceOfBirthCertificateDao;
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private BirthRegTempRepository birthRegTempRepository;
	
	@Autowired
	private IBirthRegDraftTempDao birthRegTempDao;
	
	// save birth registration
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional(rollbackFor=Exception.class)
	@WebMethod(exclude = true)
	public BirthRegistrationDTO saveBirthRegDet(@RequestBody final BirthRegistrationDTO requestDTO,BirthRegistrationModel model) {

		final RequestDTO commonRequest = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR, requestDTO.getOrgId());
		BirthRegistrationEntity birthReg = new BirthRegistrationEntity();
		ParentDetail parentDetail = new ParentDetail();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		BirthRegistrationHistoryEntity birthRegHistEntity=new BirthRegistrationHistoryEntity();
		ParentDetailHistory parentDetailHistEntity=new ParentDetailHistory();
		TbCfcApplicationMstEntity tbCfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		
		//Parent Detail Entry
		
		BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetail);
		parentDetail.setOrgId(requestDTO.getOrgId());
		parentDetail.setUserId(requestDTO.getUserId());
		parentDetail.setLangId(requestDTO.getLangId());
		parentDetail.setLmodDate(requestDTO.getLmodDate());
		parentDetail.setUpdatedBy(requestDTO.getUpdatedBy());
		parentDetail.setUpdatedDate(requestDTO.getUpdatedDate());
		parentDetail.setLgIpMac(requestDTO.getLgIpMac());
		parentDetail.setLgIpMacUpd(requestDTO.getLgIpMacUpd());
		if(requestDTO.getParentDetailDTO().getCpdId1()!=null) {
		LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(requestDTO.getParentDetailDTO().getCpdId1(), parentDetail.getOrgId());
		if(lookUp.getLookUpCode()!=null && lookUp.getLookUpCode().equals("IND")) {
		  parentDetail.setAddressflag("I"); 
		  }
		}
		  else{ 
			  parentDetail.setAddressflag("O"); 
			  }
		//parentDetail.setPdBrId(birthReg.getBrId());
		parentDetail.setBrId(birthReg);  //vi added
		birthReg.setParentDetail(parentDetail);

		//Birth Registration Entry
		BeanUtils.copyProperties(requestDTO, birthReg);
		birthReg.setOrgId(requestDTO.getOrgId());
		birthReg.setHiId(requestDTO.getHiId());
		birthReg.setBrBirthWt(requestDTO.getBrBirthWt());
		birthReg.setBrRegDate(requestDTO.getBrRegDate());
		birthReg.setBrAdopFlg("N");
		birthReg.setBrCorrectionFlg("N");
		birthReg.setBrStillBirthFlg("N");
		birthReg.setBrOrphanReg("N");
		birthReg.setBrFlag("C");
		birthReg.setBrManualCertNo(0l);
		birthReg.setBcrFlag("N");
		birthReg.setHrReg("N");
		birthReg.setNoOfCopies(0l);
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),birthReg.getOrgId());
		
		if (processName != null) {
			birthReg.setBrStatus(BndConstants.STATUS);
			birthReg.setBirthWFStatus(BndConstants.OPEN);
		}else {
			birthReg.setBrStatus(BndConstants.BIRTH_STATUS_APPROVED);
			birthReg.setBirthWFStatus(BndConstants.APPROVED);
		}
		birthReg.setBrCertNo("0");
		//Long birthPlace=Long.parseLong(birthReg.getBrBirthPlace());
		LookUp lookup =null;
		if(birthReg.getBrBirthPlaceType()!=null)
		lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.parseLong(birthReg.getBrBirthPlaceType()), birthReg.getOrgId(), "DPT");
		
		
		  if(lookup!=null && lookup.getLookUpCode()!=null && lookup.getLookUpCode().equals("I"))
				  { 
			  birthReg.setBrHospital("Y");
		  } 
		  else 
		  { 
			  birthReg.setBrHospital("N");
			  }
		 
		birthRegRepository.save(birthReg);
		
		//birth Registration History start
		BeanUtils.copyProperties(birthReg, birthRegHistEntity);
		birthRegHistEntity.setAction(BndConstants.BR);
		birthRegHistRepository.save(birthRegHistEntity);
		//birth Registration history end 

		//Parent Detail History start
		BeanUtils.copyProperties(parentDetail, parentDetailHistEntity);
		parentDetailHistEntity.setAction(BndConstants.BR);
		parentDetailHistEntity.setPdBrId(parentDetail.getBrId().getBrId()); //vi commented
		parentDetHistRepository.save(parentDetailHistEntity);
		//Parent Detail history end 
		
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApmMode("F");
		commonRequest.setfName(requestDTO.getParentDetailDTO().getPdFathername());
		commonRequest.setGender(requestDTO.getBrSex());
		
		
		 commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		  if(serviceMas.getSmFeesSchedule()==0)
			{
			  commonRequest.setPayStatus("F");
			}else {
				commonRequest.setPayStatus("Y");
			}
		  
		commonRequest.setUserId(requestDTO.getUserId());
		//commonRequest.setReferenceId(String.valueOf(requestDTO.getApmApplicationId()));
		if (requestDTO.getApmApplicationId() != null) {
			Long aplId = requestDTO.getApmApplicationId();
			commonRequest.setReferenceId(String.valueOf(aplId));
			commonRequest.setApplicationId(aplId);
			List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);
			if (birth != null) {
				birth.forEach(entity -> {
					BeanUtils.copyProperties(entity, birthDeathCFCInterface);
					birthDeathCFCInterface.setBdRequestId(birthReg.getBrId());
					birthDeathCFCInterface.setUserId(birthReg.getUserId());
					birthDeathCFCInterface.setLmodDate(new Date());
					tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
				});
			}
		} else if ((requestDTO.getApmApplicationId() == null) || (requestDTO.getApmApplicationId().equals("0"))) {
		final Long applicationId = applicationService.createApplication(commonRequest);
		
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		commonRequest.setReferenceId(String.valueOf(applicationId));
		commonRequest.setApplicationId(applicationId);
		requestDTO.setApplicationId(applicationId.toString());
		if ((applicationId != null) && (applicationId != 0)) {
			requestDTO.setApmApplicationId(applicationId);
		}
   
		// Birth Interface Entry
			BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
			// birthDeathCFCInterface.setApmApplicationId(applicationId);
			birthDeathCFCInterface.setBdRequestId(birthReg.getBrId());
			birthReg.getParentDetail().setBrId(birthReg); // setPdBrId(birthReg.getBrId());//vi commetted
			birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
			birthDeathCFCInterface.setUserId(birthReg.getUserId());
			birthDeathCFCInterface.setCopies(0l);
			birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
			tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		}
		
		//RM-38294
		if ((requestDTO.getApmApplicationId() != null) && (requestDTO.getApmApplicationId() != 0)) {
			Long lang = Long.valueOf(requestDTO.getLangId());
			Organisation org = UserSession.getCurrent().getOrganisation();
			tbCfcApplicationMstEntity.setApmFname(requestDTO.getParentDetailDTO().getPdFathername());
			tbCfcApplicationMstEntity.setApmApplicationId(requestDTO.getApmApplicationId());
			tbCfcApplicationMstEntity.setApmSex(requestDTO.getBrSex());
			tbCfcApplicationMstEntity.setApmApplicationDate(requestDTO.getBrRegDate());
			tbCfcApplicationMstEntity.setUserId(requestDTO.getUserId());
			tbCfcApplicationMstEntity.setLangId(lang);
			tbCfcApplicationMstEntity.setLmoddate(requestDTO.getLmodDate());
			tbCfcApplicationMstEntity.setApmChklstVrfyFlag("P");
			tbCfcApplicationMstEntity.setApmPayStatFlag(commonRequest.getPayStatus());
			tbCfcApplicationMstEntity.setTbOrganisation(org);
			tbCfcApplicationMstEntity.setTbServicesMst(serviceMas);
			tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		}
		//RM-38294
		
		if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getUploadDocument(), commonRequest);
		}
		
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount()==0.0) {
			requestDTO.setServiceId(serviceMas.getSmServiceId());
			initializeWorkFlowForFreeService(requestDTO,serviceMas);
		} else {
			setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		
		return requestDTO;

	}

	// get birth application details
	@Override
	@Transactional
	@GET
	@Path(value = "/getBirthRegApplnDtl")
	public BirthRegistrationDTO getBirthRegApplnDetails(@QueryParam("certno") String certno,
			@QueryParam("regNo") Long regNo, @QueryParam("year") String year,
			@QueryParam("brApplicationId") String brApplicationId, @QueryParam("orgId") Long orgId) {

		BirthRegistrationEntity birthRegData = birthRegDao.getBirthRegApplnDetails(certno, regNo, year, brApplicationId,
				orgId);
		BirthRegistrationDTO birthRegDto = null;
		ParentDetailDTO parentDtlDto = null;
		if (birthRegData != null) {
			birthRegDto = new BirthRegistrationDTO();
			parentDtlDto = new ParentDetailDTO();
			BeanUtils.copyProperties(birthRegData, birthRegDto);
			BeanUtils.copyProperties(birthRegData.getParentDetail(), parentDtlDto);
			birthRegDto.setParentDetailDTO(parentDtlDto);
		}
		return birthRegDto;
	}

	// save birth correction
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	@GET
	@POST
	@Path("/saveBirthRegCorrection")
	public BirthRegistrationDTO saveBirthCorrectionDet(BirthRegistrationDTO requestDTO,BirthCorrectionModel model) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthRegistrationCorrection birthRegCorrection = new BirthRegistrationCorrection();
		//BirthRegistrationHistoryEntity birthCorreHistoryEntity=new BirthRegistrationHistoryEntity();
		BirthRegistrationCorrectionHistory  birthCorreHistoryEntity=new BirthRegistrationCorrectionHistory();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		ParentDetailHistory parentDetailHistory=new ParentDetailHistory();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("BRC", requestDTO.getOrgId());
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setApmMode("F");
		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		if(serviceMas.getSmFeesSchedule()==0)
		{
		  commonRequest.setPayStatus("F");
		}else {
			commonRequest.setPayStatus("Y");
		}
		commonRequest.setUserId(requestDTO.getUserId());
		
		
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
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId.toString());
		
		if ((applicationId != null) && (applicationId != 0)) {
			requestDTO.setApmApplicationId(applicationId);
		}
		if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getUploadDocument(), commonRequest);
		}
		BeanUtils.copyProperties(requestDTO, birthRegCorrection);
		if (requestDTO.getBrRegDate() != null) {
			birthRegCorrection.setBrRegdate(requestDTO.getBrRegDate());
		} else {
			birthRegCorrection.setBrRegdate(new Date());
		}
		birthRegCorrection.setBrId(requestDTO.getBrId());
		birthRegCorrection.setBrSex(requestDTO.getBrSex());
		birthRegCorrection.setBrBirthaddr(requestDTO.getBrBirthAddr());
		birthRegCorrection.setBrBirthaddrMar(requestDTO.getBrBirthAddrMar());
		birthRegCorrection.setBrBirthplace(requestDTO.getBrBirthPlace());
		birthRegCorrection.setBrBirthplaceMar(requestDTO.getBrBirthPlaceMar());
		birthRegCorrection.setOrgId(requestDTO.getOrgId());
		birthRegCorrection.setSmServiceId(serviceMas.getSmServiceId());
		birthRegCorrection.setPdMothername(requestDTO.getParentDetailDTO().getPdMothername());
		birthRegCorrection.setPdMothernameMar(requestDTO.getParentDetailDTO().getPdMothernameMar());
		birthRegCorrection.setPdFathername(requestDTO.getParentDetailDTO().getPdFathername());
		birthRegCorrection.setPdFathernameMar(requestDTO.getParentDetailDTO().getPdFathernameMar());
		birthRegCorrection.setBrCorrnDate(new Date());
		birthRegCorrection.setBrChildname(requestDTO.getBrChildName());
		birthRegCorrection.setBrChildnameMar(requestDTO.getBrChildNameMar());
		birthRegCorrection.setBrBirthplace(requestDTO.getBrBirthPlace());
		birthRegCorrection.setBrBirthplaceMar(requestDTO.getBrBirthPlaceMar());
		birthRegCorrection.setPdAddress(requestDTO.getParentDetailDTO().getPdAddress());
		birthRegCorrection.setPdAddressMar(requestDTO.getParentDetailDTO().getPdAddressMar());
		birthRegCorrection.setPdParaddress(requestDTO.getParentDetailDTO().getPdParaddress());
		birthRegCorrection.setPdParaddressMar(requestDTO.getParentDetailDTO().getPdParaddressMar());
		birthRegCorrection.setBrStatus(requestDTO.getBrStatus());
		birthRegCorrection.setBirthWFStatus(BndConstants.OPEN);
		birthRegCorrection.setBrInformantName(requestDTO.getBrInformantName());
		birthRegCorrection.setBrInformantAddr(requestDTO.getBrInformantAddr());
		birthRegCorrection.setCpdFEducnId(requestDTO.getParentDetailDTO().getCpdFEducnId());
		birthRegCorrection.setCpdFOccuId(requestDTO.getParentDetailDTO().getCpdFOccuId());
		birthRegCorrection.setCpdMEducnId(requestDTO.getParentDetailDTO().getCpdMEducnId());
		birthRegCorrection.setCpdMOccuId(requestDTO.getParentDetailDTO().getCpdMOccuId());
		birthRegCorrection.setCpdReligionId(requestDTO.getParentDetailDTO().getCpdReligionId());
		birthRegCorrection.setPdAgeAtBirth(requestDTO.getParentDetailDTO().getPdAgeAtBirth());
		birthRegCorrection.setPdLiveChildn(requestDTO.getParentDetailDTO().getPdLiveChildn());
		birthRegCorrection.setPdRegUnitId(requestDTO.getParentDetailDTO().getPdRegUnitId());
		if (requestDTO.getCorrCategory() != null && !requestDTO.getCorrCategory().isEmpty()) {
			birthRegCorrection.setCorrCategory(String.join(",", requestDTO.getCorrCategory()));
		}

		birthRegCorrectionRepository.save(birthRegCorrection);
		birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(),birthRegCorrection.getBirthWFStatus());

		// Birth Interface Entry
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegCorrection.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
        birthDeathCFCInterface.setUserId(birthRegCorrection.getUserId());
        if (requestDTO.getNoOfCopies() != null) {
			birthDeathCFCInterface.setCopies(requestDTO.getNoOfCopies());
		} else {
			birthDeathCFCInterface.setCopies(0l);
		}
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		
	   //histort for correction start
	    BeanUtils.copyProperties(birthRegCorrection, birthCorreHistoryEntity);
	    birthCorreHistoryEntity.setAction(BndConstants.BRC);
	    birthRegCorreHistRepository.save(birthCorreHistoryEntity);
	    BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetailHistory);
	    parentDetailHistory.setAction(BndConstants.BRC);
	    parentDetailHistory.setOrgId(requestDTO.getOrgId());
		parentDetHistRepository.save(parentDetailHistory);
	   //histort for correction start
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			BirthRegistrationCorrDTO tbBirthregcorrDTO = new BirthRegistrationCorrDTO();
			BeanUtils.copyProperties(birthRegCorrection, tbBirthregcorrDTO);
			requestDTO.setApplicationId(String.valueOf(applicationId));
			tbBirthregcorrDTO.setApmApplicationId(applicationId);
			updateBirthApproveStatus(tbBirthregcorrDTO, MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBirthregcorrDTO.setBirthWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    	updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Status.CLOSED, birthRegCorrection.getOrgId());
	    	//certificate generation/update
	    	updateBirthRegCorrApprove(tbBirthregcorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	// save data to birth registration entity after final approval 
	    	requestDTO.setApmApplicationId(tbBirthregcorrDTO.getApmApplicationId());
	    	saveBirthRegDetOnApproval(requestDTO);
	    	issuenceOfBirthCertificateService.updatNoOfcopyStatus(requestDTO.getBrId(), requestDTO.getOrgId(), requestDTO.getBrId(), requestDTO.getNoOfCopies());
			
		}
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount()==0.0) {
			requestDTO.setServiceId(serviceMas.getSmServiceId());
			initializeWorkFlowForFreeService(requestDTO,serviceMas);
		} else {
		setAndSavebirthcorrChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		Organisation organisation = iOrganisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		if(requestDTO.getLangId()==1) {
			requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceName());
		}
		else {
			requestDTO.getRequestDTO().setServiceShortCode(serviceMas.getSmServiceNameMar());
		}
		smsAndEmail(requestDTO, organisation);
		return requestDTO;
	}

	@Override
	@Transactional(readOnly = true)
	@Path(value = "/SearchBirth")
	@ApiOperation(value = "get application detail", response = BirthRegistrationDTO.class)
	@GET
	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(@QueryParam("certNo") String certNo,
			@QueryParam("brRegNo") String brRegNo, @QueryParam("year") String year,@QueryParam("brDob") Date brDob,@QueryParam("brChildName") String brChildName,
			@QueryParam("applicnId") String applicnId, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR, orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		List<BirthRegistrationEntity> DetailEntity = birthRegDao.getBirthRegisteredApplicantList(certNo, brRegNo, year,brDob,brChildName,smServiceId,
				applicnId, orgId);
		List<BirthRegistrationDTO> listDTO=new ArrayList<BirthRegistrationDTO>();
		
		if (DetailEntity != null) {
			DetailEntity.forEach(entity->{
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				 ParentDetailDTO pDto = new ParentDetailDTO(); 
				  if(entity!=null) {
				  BeanUtils.copyProperties(entity, dto);
				  BeanUtils.copyProperties(entity.getParentDetail(), pDto);
				  }
                                dto.setBirthWfStatus(entity.getBirthWFStatus());
				  pDto.setCpdId3(entity.getParentDetail().getCpdId3());
				dto.setParentDetailDTO(pDto);
				listDTO.add(dto);
				
				   });
		
				}
			
		return listDTO;
	}
	
	
	@Override
	@Transactional
	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto) {
		long noOfDays = Utility.getDaysBetweenDates(birthRegDto.getBrDob(), birthRegDto.getBrRegDate());
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
	@Transactional(rollbackFor=Exception.class)
	public void updateBirthApproveStatusBR(BirthRegistrationDTO birthRegDTO, String status, String lastDecision){
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		BirthRegistrationEntity TbbirthregEntity=new BirthRegistrationEntity();
		ServiceMaster service=new ServiceMaster();
		BirthRegistrationHistoryEntity birthRegistrationHistoryEntity =new BirthRegistrationHistoryEntity();
		cfcApplEntiry.setApmApplicationId(Long.valueOf(birthRegDTO.getApplicationId()));
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(cfcApplEntiry.getApmApplicationId(), birthRegDTO.getOrgId());
	    BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
	    cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setApmApplicationDate(birthRegDTO.getBrRegDate());
		service.setSmServiceId(birthRegDTO.getServiceId());
		cfcApplEntiry.setTbServicesMst(service);
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
	    cfcApplEntiry.setUpdatedBy(birthRegDTO.getUserId());
		cfcApplEntiry.setUserId(birthRegDTO.getUserId());
		cfcApplEntiry.setApmSex(birthRegDTO.getBrSex());
		cfcApplEntiry.setApmFname(birthRegDTO.getParentDetailDTO().getPdFathername());
	    TbbirthregEntity.setBrId(birthRegDTO.getBrId());
	    TbbirthregEntity.setUpdatedBy(birthRegDTO.getUserId());
	    TbbirthregEntity.setUpdatedDate(new Date());
	    TbbirthregEntity.setLmodDate(new Date());
	    TbbirthregEntity.setOrgId(birthRegDTO.getOrgId());
	    birthRegistrationHistoryEntity.setOrgId(birthRegDTO.getOrgId());
	    birthRegistrationHistoryEntity.setLmodDate(new Date());
	    birthRegistrationHistoryEntity.setUpdatedBy(birthRegDTO.getUserId());
	    birthRegistrationHistoryEntity.setUpdatedDate(new Date());
	    BeanUtils.copyProperties(birthRegDTO, birthRegistrationHistoryEntity);
	    birthRegistrationHistoryEntity.setBrId(birthRegDTO.getBrId());
	    birthRegistrationHistoryEntity.setHiId(null);
		
		/*
		 * LookUp lokkup = null; if (birthRegDTO.getBrSex() != null) { lokkup =
		 * CommonMasterUtility.getLookUpFromPrefixLookUpDesc(birthRegDTO.getBrSex(),
		 * GENDER, 1); }
		 */

	    if(birthRegDTO.getBrSex() != null ) { 
		birthRegistrationHistoryEntity.setBrSex(String.valueOf(birthRegDTO.getBrSex()));
	    }
		if(lastDecision.equals(BndConstants.REJECTED)){
		    cfcApplEntiry.setApmAppRejFlag("R");
		    cfcApplEntiry.setAppAppRejBy(birthRegDTO.getServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag("C");
		    TbbirthregEntity.setBrStatus("C");  
		    TbbirthregEntity.setBirthWFStatus(BndConstants.REJECTED);
		    birthRegistrationHistoryEntity.setBrStatus("C");
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.PENDING)){
			 cfcApplEntiry.setApmApplSuccessFlag("P");
			 cfcApplEntiry.setApmApprovedBy(birthRegDTO.getServiceId());
			// cfcApplEntiry.setRejectionDt(new Date());
			 cfcApplEntiry.setApmApplClosedFlag("O");
			 TbbirthregEntity.setBrStatus("I");	
			 TbbirthregEntity.setBirthWFStatus(BndConstants.PENDING);
			 birthRegistrationHistoryEntity.setBrStatus("I");
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.CLOSED)){
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setApmApprovedBy(birthRegDTO.getServiceId());
			//cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(birthRegDTO.getBrRegDate());
			TbbirthregEntity.setBrStatus("C");
			TbbirthregEntity.setBirthWFStatus(BndConstants.APPROVED);
			birthRegistrationHistoryEntity.setBrStatus("C");
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
		//birthRegRepository.save(TbbirthregEntity);
		birthRegHistRepository.save(birthRegistrationHistoryEntity);	
 }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBirthWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId,
			String brStatus) {
		birthRegRepository.updateWorkFlowStatus(brId, orgId, taskNamePrevious,brStatus);
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
	public Boolean checkEmployeeRole(UserSession ses) {	
	 @SuppressWarnings("deprecation")
	 LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("BND_APPROVER","DLO",1);
	 GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),ses.getOrganisation().getOrgid());
	  boolean checkFinalAproval=false;
	    if(lookup.getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
	    	checkFinalAproval=true;
	    }
	  return checkFinalAproval;
	}
	
	
	@Override
	@Transactional
	public List<BirthRegistrationCorrDTO> getBirthRegisteredAppliDetailFromApplnId(@QueryParam("applnId") Long applnId, @QueryParam("orgId") Long orgId) {
		List<BirthRegistrationCorrection> tbBirthregcorr = birthRegDao.getBirthRegisteredAppliDetailFromApplnId(applnId, orgId);
		List<BirthRegistrationCorrDTO> listDTO = new ArrayList<BirthRegistrationCorrDTO>();
		if (tbBirthregcorr != null) {
			tbBirthregcorr.forEach(entity -> {
				BirthRegistrationCorrDTO dto = new BirthRegistrationCorrDTO();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getBrSex()), orgId, GENDER);
				
				if(lokkup!=null)
				dto.setBrSex(lokkup.getLookUpDesc());
				dto.setBrBirthAddr(entity.getBrBirthaddr());
				dto.setBrBirthAddrMar(entity.getBrBirthaddrMar());
				dto.setBrBirthPlace(entity.getBrBirthplace());
				dto.setBrBirthPlaceMar(entity.getBrBirthplaceMar());
				dto.setBrBirthPlace(entity.getBrBirthplace());
				dto.setBrBirthPlaceType(entity.getBrBirthplaceType());
				dto.setBrChildName(entity.getBrChildname());
				dto.setBrChildNameMar(entity.getBrChildnameMar());
				dto.setHiId(entity.getHiId());
				dto.setBrRemarks(entity.getBrRemarks());
				dto.setBrRegNo(entity.getBrRegno());
				dto.getParentDetailDTO().setPdAddress(entity.getPdAddress());
				dto.getParentDetailDTO().setPdAddressMar(entity.getPdAddressMar());
				dto.setCpdFEducnId(entity.getCpdFEducnId());
				dto.setCpdFOccuId(entity.getCpdFOccuId());
				dto.setCpdMEducnId(entity.getCpdMEducnId());
				dto.setCpdMOccuId(entity.getCpdMOccuId());
				dto.setCpdReligionId(entity.getCpdReligionId());
				dto.setPdAgeAtBirth(entity.getPdAgeAtBirth());
				dto.setPdLiveChildn(entity.getPdLiveChildn());
				dto.setBrInformantName(entity.getBrInformantName());
				dto.setBrInformantAddr(entity.getBrInformantAddr());
				dto.setBirthRegremark(entity.getCorrAuthRemark());
				
				//dto.getParentDetailDTO().setPdRegUnitId(entity.);
				//dto.getParentDetailDTO().setCpdReligionId(entity.get);
				
				listDTO.add(dto);
			});
		}
		return listDTO;
	}

	@Override
	public List<BirthRegistrationDTO> getBirthRegApplnData(Long brId, Long orgId) {
		List<BirthRegistrationEntity> tbBirthregentity = birthRegDao.getBirthRegApplnData(brId, orgId);
		List<BirthRegistrationDTO> listDTO = new ArrayList<BirthRegistrationDTO>();
		if (tbBirthregentity != null) {
			tbBirthregentity.forEach(entity -> {
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getBrSex()), orgId, GENDER);
				dto.setBrSex(lokkup.getLookUpDesc());
				dto.getParentDetailDTO().setPdMothername(entity.getParentDetail().getPdMothername());
				dto.getParentDetailDTO().setPdMothernameMar(entity.getParentDetail().getPdMothernameMar());
				dto.getParentDetailDTO().setPdFathername(entity.getParentDetail().getPdFathername());
				dto.getParentDetailDTO().setPdFathernameMar(entity.getParentDetail().getPdFathernameMar());
				dto.getParentDetailDTO().setPdParaddress(entity.getParentDetail().getPdParaddress());
				dto.getParentDetailDTO().setPdParaddressMar(entity.getParentDetail().getPdParaddressMar());
				dto.getParentDetailDTO().setCpdReligionId(entity.getParentDetail().getCpdReligionId());
				dto.getParentDetailDTO().setPdRegUnitId(entity.getParentDetail().getPdRegUnitId());
				dto.getParentDetailDTO().setPdAddress(entity.getParentDetail().getPdAddress());
				dto.getParentDetailDTO().setPdAddressMar(entity.getParentDetail().getPdAddressMar());
				dto.getParentDetailDTO().setCpdFEducnId(entity.getParentDetail().getCpdFEducnId());
				dto.getParentDetailDTO().setCpdFOccuId(entity.getParentDetail().getCpdFOccuId());
				dto.getParentDetailDTO().setCpdMEducnId(entity.getParentDetail().getCpdMEducnId());
				dto.getParentDetailDTO().setCpdMOccuId(entity.getParentDetail().getCpdMOccuId());
				dto.getParentDetailDTO().setPdAgeAtBirth(entity.getParentDetail().getPdAgeAtBirth());
				dto.getParentDetailDTO().setPdLiveChildn(entity.getParentDetail().getPdLiveChildn());
				dto.setCpdRegUnit(String.valueOf(entity.getParentDetail().getPdRegUnitId()));
				listDTO.add(dto);
			});
		}
		return listDTO;
	}

	@Override
	public List<ParentDetailDTO> getParentDtlApplnData(Long brId, Long orgId) {
		List<ParentDetail> tbParentDtlentity = birthRegDao.getParentDtlApplnData(brId, orgId);
		List<ParentDetailDTO> listDTO = new ArrayList<ParentDetailDTO>();
		if (tbParentDtlentity != null) {
			tbParentDtlentity.forEach(entity -> {
				ParentDetailDTO dto = new ParentDetailDTO();
				BeanUtils.copyProperties(entity, dto);
				//LookUp lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getBrSex()), orgId, GENDER);
				//dto.setBrSex(lokkup.getLookUpDesc());
				dto.setCpdFEducnId(entity.getCpdFEducnId());
				dto.setCpdFOccuId(entity.getCpdFOccuId());
				dto.setCpdMEducnId(entity.getCpdMEducnId());
				dto.setCpdMOccuId(entity.getCpdMOccuId());
				dto.setCpdReligionId(entity.getCpdReligionId());
				dto.setCpdId1(entity.getCpdId1());
				dto.setCpdId2(entity.getCpdId2());
				dto.setCpdId3(entity.getCpdId3());
				dto.setCpdId4(entity.getCpdId4());
				dto.setMotheraddress(entity.getMotheraddress());
				//dto.setPdAddressMar(pdAddressMar);
				dto.setPdAgeAtBirth(entity.getPdAgeAtBirth());
				dto.setPdAgeAtMarry(entity.getPdAgeAtMarry());
				dto.setPdAddress(entity.getPdAddress());
				dto.setPdAddressMar(entity.getPdAddressMar());
				dto.setPdLiveChildn(entity.getPdLiveChildn());
				dto.setPdFathername(entity.getPdFathername());
				dto.setPdFathernameMar(entity.getPdFathernameMar());
				dto.setPdMothername(entity.getPdMothername());
				dto.setPdMothernameMar(entity.getPdMothernameMar());
				
				listDTO.add(dto);
			});
		}
		return listDTO;
	}


	@Override
    @Transactional
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
	public void updateBirthApproveStatus(BirthRegistrationCorrDTO tbBirthregcorrDTO, String status, String lastDecision) {
		
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		BirthRegistrationEntity TbBirthregEntity=new BirthRegistrationEntity();
		cfcApplEntiry.setApmApplicationId(tbBirthregcorrDTO.getApmApplicationId());
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(cfcApplEntiry.getApmApplicationId(), tbBirthregcorrDTO.getOrgId());
	    BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
	   // cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
	    cfcApplEntiry.setUpdatedBy(tbBirthregcorrDTO.getUserId());
	    final Organisation organisation = new Organisation();
        organisation.setOrgid(tbBirthregcorrDTO.getOrgId());
        cfcApplEntiry.setTbOrganisation(organisation);
	    TbBirthregEntity.setBrId(tbBirthregcorrDTO.getBrId());
	    TbBirthregEntity.setUpdatedBy(tbBirthregcorrDTO.getUserId());
	    TbBirthregEntity.setUpdatedDate(new Date());
	    //TbDeathregEntity.setLmoddate(new Date());
		if(lastDecision.equals("REJECTED")){
		    cfcApplEntiry.setApmAppRejFlag("R");
		    cfcApplEntiry.setAppAppRejBy(tbBirthregcorrDTO.getServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag("C");
		    cfcApplEntiry.setApmApplicationDate(new Date());
		    TbBirthregEntity.setBirthWFStatus(BndConstants.REJECTED);
		    TbBirthregEntity.setBrStatus("C");  
		}
		else if(status.equals("APPROVED") && lastDecision.equals("PENDING")){
			cfcApplEntiry.setApmApplSuccessFlag("P");
			cfcApplEntiry.setApmApprovedBy(tbBirthregcorrDTO.getServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			TbBirthregEntity.setBirthWFStatus(BndConstants.PENDING);
			TbBirthregEntity.setBrStatus("I");	
		}
		else if(status.equals("APPROVED") && lastDecision.equals("CLOSED")){
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setApmApprovedBy(tbBirthregcorrDTO.getServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(tbBirthregcorrDTO.getBrRegDate());
			cfcApplEntiry.setApmApplicationDate(new Date());
			TbBirthregEntity.setBirthWFStatus(BndConstants.APPROVED);
			TbBirthregEntity.setBrStatus("C");
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}


	@Override
	@Transactional
	public void updateBirthWorkFlowStatus(Long brId, String taskNamePrevious, Long orgId){
		birthRegCorrectionRepository.updateWorkFlowStatus(brId, orgId, taskNamePrevious);
	}
	

	
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Path(value = "/saveBirthDraftRegistration")
	public BirthRegistrationDTO saveBirthRegDraft(BirthRegistrationDTO birthRegDto) {

		final RequestDTO requestDTO = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR, birthRegDto.getOrgId());

		BirthRegistrationEntity birthReg = new BirthRegistrationEntity();
		ParentDetail parentDetail = new ParentDetail();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		BirthRegdraftEntity birthRegdraftEntity = new BirthRegdraftEntity();

		// Parent Detail Entry
		parentDetail.setOrgId(birthRegDto.getOrgId());
		BeanUtils.copyProperties(birthRegDto.getParentDetailDTO(), parentDetail);
		parentDetail.setBrId(birthReg);
		//parentDetail.setUserId(birthRegDto.getUserId());
		birthReg.setParentDetail(parentDetail);
		

		// Birth Registration Entry
		BeanUtils.copyProperties(birthRegDto, birthReg);
		birthReg.setOrgId(birthRegDto.getOrgId());
		birthReg.setHiId(birthRegDto.getHiId());
//		birthReg.setBrBirthWt(birthRegDto.getBrBirthWt().longValue());
		birthReg.setBrRegDate(birthRegDto.getBrRegDate());
		birthReg.setBrStatus("D");

		// Birth Reg Draft entry
		BeanUtils.copyProperties(birthRegDto, birthRegdraftEntity);
		birthRegdraftEntity.setBrChildname(birthRegDto.getBrChildName());
		birthRegdraftEntity.setBrChildnameMar(birthRegDto.getBrChildNameMar());
		birthRegdraftEntity.setBrBirthwt(birthRegDto.getBrBirthWt());
		birthRegdraftEntity.setBrBirthplace(birthRegDto.getBrBirthPlace());
		birthRegdraftEntity.setBrBirthplaceMar(birthRegDto.getBrBirthPlaceMar());
		birthRegdraftEntity.setBrBirthplaceType(birthRegDto.getBrBirthPlaceType());
		birthRegdraftEntity.setBrBirthaddr(birthRegDto.getBrBirthAddr());
		birthRegdraftEntity.setBrBirthaddrMar(birthRegDto.getBrBirthAddrMar());
		birthRegdraftEntity.setCpdDelmethId(birthRegDto.getCpdDelMethId());
		birthRegdraftEntity.setBrBirthMark(birthRegDto.getBrBirthMark());
		birthRegdraftEntity.setBrPregDuratn(birthRegDto.getBrPregDuratn());
		birthRegdraftEntity.setCpdDelmethId(birthRegDto.getCpdDelMethId());
		birthRegdraftEntity.setOrgId(birthRegDto.getOrgId());
		birthRegdraftEntity.setBrRegdate(birthRegDto.getBrRegDate());
		birthRegdraftEntity.setLmoddate(new Date());
		birthRegdraftEntity.setLangId(birthRegDto.getLangId());
		// parent entry into draft
		birthRegdraftEntity.setPdAddress(parentDetail.getPdAddress());
		birthRegdraftEntity.setPdAddressMar(parentDetail.getPdAddressMar());
		birthRegdraftEntity.setPdAgeAtBirth(parentDetail.getPdAgeAtBirth());
		birthRegdraftEntity.setCpdFEducnId(parentDetail.getCpdFEducnId());
		birthRegdraftEntity.setCpdFOccuId(parentDetail.getCpdFOccuId());
		birthRegdraftEntity.setCpdMEducnId(parentDetail.getCpdMEducnId());
		birthRegdraftEntity.setCpdMOccuId(parentDetail.getCpdMOccuId());
		birthRegdraftEntity.setPdAgeAtMarry(parentDetail.getPdAgeAtMarry());
		birthRegdraftEntity.setPdFathername(parentDetail.getPdFathername());
		birthRegdraftEntity.setPdFathernameMar(parentDetail.getPdFathernameMar());
		birthRegdraftEntity.setPdLiveChildn(parentDetail.getPdLiveChildn());
		birthRegdraftEntity.setPdMotherAdd(parentDetail.getMotheraddress());
		birthRegdraftEntity.setPdMothername(parentDetail.getPdMothername());
		birthRegdraftEntity.setPdMothernameMar(parentDetail.getPdMothernameMar());
		birthRegdraftEntity.setMotheraddressMar(parentDetail.getMotheraddressMar());
		birthRegdraftEntity.setPdParaddress(parentDetail.getPdParaddress());
		birthRegdraftEntity.setPdParaddressMar(parentDetail.getPdParaddressMar());
		birthRegdraftEntity.setPdRegUnitId(parentDetail.getPdRegUnitId());
		birthRegdraftEntity.setCpdNationltyId(parentDetail.getCpdId1());
		birthRegdraftEntity.setCpdStateId(parentDetail.getCpdId2());
		birthRegdraftEntity.setCpdDistrictId(parentDetail.getCpdId3());
		birthRegdraftEntity.setCpdTalukaId(parentDetail.getCpdId4());
		birthRegdraftEntity.setCpdResId(parentDetail.getCpdId5());
		birthRegdraftEntity.setCpdReligionId(parentDetail.getCpdReligionId());
		// save into Birth Reg Draft
		birthRegDraftRepository.save(birthRegdraftEntity);
		
		requestDTO.setOrgId(birthRegDto.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setDeptId(birthRegDto.getDeptId());
		requestDTO.setfName(birthRegDto.getParentDetailDTO().getPdFathername());
		requestDTO.setApmMode("F");
		requestDTO.setLangId(Long.valueOf(birthRegDto.getLangId()));
		if (serviceMas.getSmFeesSchedule() == 0) {
			requestDTO.setPayStatus("F");
		} else {
			requestDTO.setPayStatus("Y");
		}
		//requestDTO.setReferenceId(String.valueOf(birthReg.getBrId()));

		if (birthRegDto.getApmApplicationId()!= null) {
			Long aplId = birthRegDto.getApmApplicationId();
			requestDTO.setReferenceId(String.valueOf(aplId));
			requestDTO.setApplicationId(aplId);
			List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);
			if (birth != null) {
				birth.forEach(entity -> {
					BeanUtils.copyProperties(entity, birthDeathCFCInterface);
					birthDeathCFCInterface.setApmApplicationId(birthRegDto.getApmApplicationId());
					birthDeathCFCInterface.setBdRequestId(birthRegdraftEntity.getBrDraftId());
					birthDeathCFCInterface.setLmodDate(new Date());
					tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
				});
			}
		} else if ((birthRegDto.getApmApplicationId() == null) || (birthRegDto.getApmApplicationId().equals("0"))) {
			requestDTO.setUserId(birthRegDto.getUserId());
			final Long applicationId = applicationService.createApplication(requestDTO);
			requestDTO.setReferenceId(String.valueOf(applicationId));
			if (null == applicationId) {
				throw new RuntimeException("Application Not Generated");
			}

			requestDTO.setApplicationId(applicationId);
			birthRegDto.setApplicationId(applicationId.toString());
			birthRegDto.setBrId(birthReg.getBrId());
			if ((applicationId != null) && (applicationId != 0)) {
				birthRegDto.setApmApplicationId(applicationId);
			}

			// Birth Interface Entry
			BeanUtils.copyProperties(birthRegDto, birthDeathCFCInterface);
			// birthDeathCFCInterface.setApmApplicationId(applicationId);
			birthDeathCFCInterface.setBdRequestId(birthRegdraftEntity.getBrDraftId());
			birthDeathCFCInterface.setOrgId(birthRegDto.getOrgId());
			birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
			tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		}
		if ((birthRegDto.getUploadDocument() != null) && !birthRegDto.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(birthRegDto.getUploadDocument(), requestDTO);
		}

		return birthRegDto;

	}

	@Override
	public List<BirthRegDraftDto> getAllBirthRegdraft(Long orgId) {
		List<BirthRegdraftEntity> listBirthRegdraft = iBirthRegDraftDao.getDraftDetail(orgId);
		List<BirthRegDraftDto> listbirthRegDraftDto = new ArrayList<BirthRegDraftDto>();
		if (listBirthRegdraft != null) {
			listBirthRegdraft.forEach(entity -> {
				BirthRegDraftDto dto = new BirthRegDraftDto();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = null;
				if (!entity.getBrSex().equals("0")) {
					lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getBrSex()),
							orgId, GENDER);
					dto.setBrSex(lokkup.getLookUpDesc());
				}

				listbirthRegDraftDto.add(dto);
			});
		}
		return listbirthRegDraftDto;
	}

	@Override
	public List<BirthRegDraftDto> getBirthRegDraftAppliDetail(Long applnId, Date brDob, Long orgId) {

		List<BirthRegdraftEntity> birthregList = iBirthRegDraftDao.getBirthRegDraftAppliDetail(applnId, brDob, orgId);
		List<BirthRegDraftDto> listDTO = new ArrayList<BirthRegDraftDto>();
		if (birthregList != null) {
			birthregList.forEach(entity -> {
				BirthRegDraftDto dto = new BirthRegDraftDto();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = null;
				if (!entity.getBrSex().equals("0")) {
					lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getBrSex()),
							orgId, GENDER);
					dto.setBrSex(lokkup.getLookUpDesc());
				}
				

				listDTO.add(dto);
			});
		}

		return listDTO;
	}

	@Override
	@Transactional
	public BirthRegDraftDto getBirthById(Long brDraftId) {
		BirthRegdraftEntity birthRegdraftEntity = birthRegDraftRepository.findOne(brDraftId);
		BirthRegDraftDto birthRegDraftDto = new BirthRegDraftDto();
		BeanUtils.copyProperties(birthRegdraftEntity, birthRegDraftDto);
		birthRegDraftDto.setBrDraftId(birthRegdraftEntity.getBrDraftId());
		return birthRegDraftDto;
	}

	@Override
	@Transactional
	public BirthRegistrationDTO getBirthRegDTOFromDraftDTO(BirthRegDraftDto birthRegDraftDto) {
		BirthRegistrationDTO birthRegistrationDTO = new BirthRegistrationDTO();
		ParentDetailDTO parentDetailDTO = new ParentDetailDTO();
		// set parent deatils from draft
		parentDetailDTO.setCpdFEducnId(birthRegDraftDto.getCpdFEducnId());
		parentDetailDTO.setCpdFOccuId(birthRegDraftDto.getCpdFOccuId());
		parentDetailDTO.setCpdId1(birthRegDraftDto.getCpdNationltyId());
		parentDetailDTO.setCpdId2(birthRegDraftDto.getCpdStateId());
		parentDetailDTO.setCpdId3(birthRegDraftDto.getCpdDistrictId());
		parentDetailDTO.setCpdId4(birthRegDraftDto.getCpdTalukaId());
		parentDetailDTO.setCpdId5(birthRegDraftDto.getCpdResId());
		parentDetailDTO.setCpdMEducnId(birthRegDraftDto.getCpdMEducnId());
		parentDetailDTO.setCpdMOccuId(birthRegDraftDto.getCpdMOccuId());
		parentDetailDTO.setMotheraddress(birthRegDraftDto.getPdMotherAdd());
		parentDetailDTO.setOtherReligion(birthRegDraftDto.getOtherReligion());
		parentDetailDTO.setPdAddress(birthRegDraftDto.getPdAddress());
		parentDetailDTO.setPdAddressMar(birthRegDraftDto.getPdAddressMar());
		parentDetailDTO.setPdAgeAtBirth(birthRegDraftDto.getPdAgeAtBirth());
		parentDetailDTO.setPdAgeAtMarry(birthRegDraftDto.getPdAgeAtMarry());
		parentDetailDTO.setPdFathername(birthRegDraftDto.getPdFathername());
		parentDetailDTO.setPdFathernameMar(birthRegDraftDto.getPdFathernameMar());
		parentDetailDTO.setPdLiveChildn(birthRegDraftDto.getPdLiveChildn());
		parentDetailDTO.setPdMothername(birthRegDraftDto.getPdMothername());
		parentDetailDTO.setPdMothernameMar(birthRegDraftDto.getPdMothernameMar());
		parentDetailDTO.setPdRegUnitId(birthRegDraftDto.getPdRegUnitId());
		parentDetailDTO.setPdParaddress(birthRegDraftDto.getPdParaddress());
		parentDetailDTO.setPdParaddressMar(birthRegDraftDto.getPdParaddressMar());
		parentDetailDTO.setMotheraddress(birthRegDraftDto.getPdMotherAdd());
		parentDetailDTO.setMotheraddressMar(birthRegDraftDto.getMotheraddressMar());
		parentDetailDTO.setCpdReligionId(birthRegDraftDto.getCpdReligionId());
		BeanUtils.copyProperties(birthRegDraftDto, birthRegistrationDTO);
		birthRegistrationDTO.setBrChildName(birthRegDraftDto.getBrChildname());
		birthRegistrationDTO.setBrChildNameMar(birthRegDraftDto.getBrChildnameMar());
		birthRegistrationDTO.setBrBirthPlaceType(birthRegDraftDto.getBrBirthplaceType());
		birthRegistrationDTO.setBrBirthPlace(birthRegDraftDto.getBrBirthplace());
		birthRegistrationDTO.setBrBirthPlaceMar(birthRegDraftDto.getBrBirthplaceMar());
		birthRegistrationDTO.setBrBirthAddr(birthRegDraftDto.getBrBirthaddr());
		birthRegistrationDTO.setBrBirthAddrMar(birthRegDraftDto.getBrBirthaddrMar());
		birthRegistrationDTO.setParentDetailDTO(parentDetailDTO);
		birthRegistrationDTO.setBrBirthWt(birthRegDraftDto.getBrBirthwt());
		birthRegistrationDTO.setBrPregDuratn(birthRegDraftDto.getBrPregDuratn());
		birthRegistrationDTO.setBrRegNo(birthRegDraftDto.getBrRegNo());
		birthRegistrationDTO.getParentDetailDTO().setCpdId3(birthRegDraftDto.getCpdDistrictId());
		birthRegistrationDTO.setBrRegDate(birthRegDraftDto.getBrRegdate());
		//birthRegistrationDTO.setBrBirthMark(birthRegDraftDto.getBrBirthMark());
                birthRegistrationDTO.setCpdDelMethId(birthRegDraftDto.getCpdDelmethId());
		return birthRegistrationDTO;
	}

	@Override
	@Transactional
	public BirthRegistrationDTO getBirthByID(Long brId) {
		BirthRegistrationEntity birthRegCorrection = birthRegRepository.findOne(brId);
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		ParentDetailDTO pDto = new ParentDetailDTO();
		BeanUtils.copyProperties(birthRegCorrection, dto);
		dto.setBirthWfStatus(birthRegCorrection.getBirthWFStatus());
        Long sum = 0L;
		
		sum = dto.getNoOfCopies() != null && birthRegCorrection.getBrManualCertNo() != null
				? dto.getNoOfCopies() + birthRegCorrection.getBrManualCertNo()
				: dto.getNoOfCopies() != null ? sum + dto.getNoOfCopies()
						: birthRegCorrection.getBrManualCertNo() != null ? sum + birthRegCorrection.getBrManualCertNo()
								: sum;

		dto.setAlreayIssuedCopy(sum);
		dto.setNoOfCopies(null);
		BeanUtils.copyProperties(birthRegCorrection.getParentDetail(), pDto);
		if (birthRegCorrection.getParentDetail().getCpdId3() != null) {
			pDto.setCpdId3(birthRegCorrection.getParentDetail().getCpdId3());
		}
		
		dto.setParentDetailDTO(pDto);

		return dto;
	}
        
        
    	@Override
    	@Transactional
    	public BirthRegistrationDTO saveBirthRegDetOnApproval(BirthRegistrationDTO requestDTO) {
    		
    		BirthRegistrationEntity birthReg = new BirthRegistrationEntity();
    		ParentDetail parentDetail = new ParentDetail();
    		BirthRegistrationHistoryEntity birthRegHistEntity = new BirthRegistrationHistoryEntity();
    		ParentDetailHistory parentDetailHistEntity = new ParentDetailHistory();
    		List<BirthDeathCFCInterface> birthDeathList = birthDeathCfcInterfaceRepository.findData(requestDTO.getApmApplicationId());
    		//Parent Detail Entry
    		parentDetail.setOrgId(requestDTO.getOrgId());
    		List<BirthRegistrationEntity> tbBirthregentity = birthRegDao.getBirthRegApplnData(requestDTO.getBrId(), requestDTO.getOrgId());
    		parentDetail.setBrId(tbBirthregentity.get(0)); 
    		parentDetail.setPdId(tbBirthregentity.get(0).getParentDetail().getPdId());
    		
    		List<BirthRegistrationCorrection> tbBirthregCorrentity = birthRegDao.getBirthRegisteredAppliDetailFromApplnId(requestDTO.getApmApplicationId(), requestDTO.getOrgId());
    		
    		if(tbBirthregCorrentity.get(0).getPdMothername()!=null && !(tbBirthregCorrentity.get(0).getPdMothername().isEmpty())) {
    			parentDetail.setPdMothername(tbBirthregCorrentity.get(0).getPdMothername());
    		}
    		if(tbBirthregCorrentity.get(0).getPdMothernameMar()!=null && !(tbBirthregCorrentity.get(0).getPdMothernameMar().isEmpty())) {
    			parentDetail.setPdMothernameMar(tbBirthregCorrentity.get(0).getPdMothernameMar());
    		}
    		if(tbBirthregCorrentity.get(0).getPdFathername()!=null && !(tbBirthregCorrentity.get(0).getPdFathername().isEmpty())) {
    			parentDetail.setPdFathername(tbBirthregCorrentity.get(0).getPdFathername());
    		}
    		if(tbBirthregCorrentity.get(0).getPdFathernameMar()!=null && !(tbBirthregCorrentity.get(0).getPdFathernameMar().isEmpty())) {
    			parentDetail.setPdFathernameMar(tbBirthregCorrentity.get(0).getPdFathernameMar());
    		}
    		if(tbBirthregCorrentity.get(0).getPdAddress()!=null && !(tbBirthregCorrentity.get(0).getPdAddress().isEmpty())) {
    			parentDetail.setPdAddress(tbBirthregCorrentity.get(0).getPdAddress());
    		}
    		if(tbBirthregCorrentity.get(0).getPdAddressMar()!=null && !(tbBirthregCorrentity.get(0).getPdAddressMar().isEmpty())) {
    			parentDetail.setPdAddressMar(tbBirthregCorrentity.get(0).getPdAddressMar());
    		}
    		if(tbBirthregCorrentity.get(0).getPdParaddress()!=null && !(tbBirthregCorrentity.get(0).getPdParaddress().isEmpty())) {
    			parentDetail.setPdParaddress(tbBirthregCorrentity.get(0).getPdParaddress());
    		}
    		if(tbBirthregCorrentity.get(0).getPdParaddressMar()!=null && !(tbBirthregCorrentity.get(0).getPdParaddressMar().isEmpty())) {
    			parentDetail.setPdParaddressMar(tbBirthregCorrentity.get(0).getPdParaddressMar());
    		}
    		if(tbBirthregentity.get(0).getParentDetail().getMotheraddress()!=null && !(tbBirthregentity.get(0).getParentDetail().getMotheraddress().isEmpty())) {
    			parentDetail.setMotheraddress(tbBirthregentity.get(0).getParentDetail().getMotheraddress());
    		}
    		if(tbBirthregentity.get(0).getParentDetail().getMotheraddressMar()!=null && !(tbBirthregentity.get(0).getParentDetail().getMotheraddressMar().isEmpty())) {
    			parentDetail.setMotheraddressMar(tbBirthregentity.get(0).getParentDetail().getMotheraddressMar());
    		}
    		if(tbBirthregCorrentity.get(0).getCpdFEducnId()!=null) {
    			parentDetail.setCpdFEducnId(tbBirthregCorrentity.get(0).getCpdFEducnId());
    		}
    		else {
    			parentDetail.setCpdFEducnId(tbBirthregentity.get(0).getParentDetail().getCpdFEducnId());
    		}
    		if(tbBirthregCorrentity.get(0).getCpdFOccuId()!=null) {
    			parentDetail.setCpdFOccuId(tbBirthregCorrentity.get(0).getCpdFOccuId());
    		}
    		else {
    			parentDetail.setCpdFOccuId(tbBirthregentity.get(0).getParentDetail().getCpdFOccuId());
    		}
    		if(tbBirthregCorrentity.get(0).getCpdMEducnId()!=null) {
    			parentDetail.setCpdMEducnId(tbBirthregCorrentity.get(0).getCpdMEducnId());
    		}
    		else {
    			parentDetail.setCpdMEducnId(tbBirthregentity.get(0).getParentDetail().getCpdMEducnId());
    		}
    		if(tbBirthregCorrentity.get(0).getCpdMOccuId()!=null) {
    			parentDetail.setCpdMOccuId(tbBirthregCorrentity.get(0).getCpdMOccuId());
    		}
    		else {
    			parentDetail.setCpdMOccuId(tbBirthregentity.get(0).getParentDetail().getCpdMOccuId());
    		}
    		
    		if(tbBirthregCorrentity.get(0).getCpdReligionId()!=null) {
    			parentDetail.setCpdReligionId(tbBirthregCorrentity.get(0).getCpdReligionId());
    		}
    		else {
    			parentDetail.setCpdReligionId(tbBirthregentity.get(0).getParentDetail().getCpdReligionId());
    		}
    		
    		
    		if(tbBirthregCorrentity.get(0).getPdLiveChildn()!=null) {
    			parentDetail.setPdLiveChildn(tbBirthregCorrentity.get(0).getPdLiveChildn());
    		}
    		else {
    			parentDetail.setPdLiveChildn(tbBirthregentity.get(0).getParentDetail().getPdLiveChildn());
    		}
    		
    		if(tbBirthregCorrentity.get(0).getPdAgeAtBirth()!=null) {
    			parentDetail.setPdAgeAtBirth(tbBirthregCorrentity.get(0).getPdAgeAtBirth());
    		}
    		else {
    			parentDetail.setPdAgeAtBirth(tbBirthregentity.get(0).getParentDetail().getPdAgeAtBirth());
    		}
    		
    		parentDetail.setCpdId1(tbBirthregentity.get(0).getParentDetail().getCpdId1());
    		parentDetail.setCpdId2(tbBirthregentity.get(0).getParentDetail().getCpdId2());
    		parentDetail.setCpdId3(tbBirthregentity.get(0).getParentDetail().getCpdId3());
    		parentDetail.setCpdId4(tbBirthregentity.get(0).getParentDetail().getCpdId4());
    		//code update for signature on certificate based in registration unit
    		if (null != tbBirthregCorrentity.get(0).getPdRegUnitId())
    		parentDetail.setPdRegUnitId(tbBirthregCorrentity.get(0).getPdRegUnitId());
    		else
    		parentDetail.setPdRegUnitId(tbBirthregentity.get(0).getParentDetail().getPdRegUnitId());
    		parentDetail.setPdAgeAtMarry(tbBirthregentity.get(0).getParentDetail().getPdAgeAtMarry());
    		parentDetail.setPdGmothername(tbBirthregentity.get(0).getParentDetail().getPdGmothername());
    		parentDetail.setPdGfathername(tbBirthregentity.get(0).getParentDetail().getPdGfathername());
    		parentDetail.setPdStatus(tbBirthregentity.get(0).getParentDetail().getPdStatus());
    		birthReg.setParentDetail(parentDetail);

    		//Birth Registration Entry
    		BeanUtils.copyProperties(requestDTO, birthReg);
    		birthReg.setOrgId(requestDTO.getOrgId());
    		birthReg.setHiId(requestDTO.getHiId());
    		
		/*
		 * LookUp lookup = null; if(requestDTO.getBrSex() != null ||
		 * !(requestDTO.getBrSex().isEmpty())) { lookup =
		 * CommonMasterUtility.getLookUpFromPrefixLookUpDesc(requestDTO.getBrSex(),
		 * "GEN", 1); }
		 */
    		if(requestDTO.getBrSex() != null) { 
    		birthReg.setBrSex(String.valueOf(requestDTO.getBrSex()));
    		}

    		birthReg.setBrBirthWt(tbBirthregentity.get(0).getBrBirthWt());
    		birthReg.setBrInformantName(tbBirthregentity.get(0).getBrInformantName());
    		birthReg.setBrInformantNameMar(tbBirthregentity.get(0).getBrInformantNameMar());
        	birthReg.setBrInformantAddr(tbBirthregentity.get(0).getBrInformantAddr());
        	birthReg.setBrInformantAddrMar(tbBirthregentity.get(0).getBrInformantAddrMar());
        	birthReg.setBrPregDuratn(tbBirthregentity.get(0).getBrPregDuratn());
        	birthReg.setBrRemarks(tbBirthregentity.get(0).getBrRemarks());
        	birthReg.setBrBirthMark(tbBirthregentity.get(0).getBrBirthMark()); 
        	birthReg.setBrBirthMark(tbBirthregentity.get(0).getBrBirthMark());
        	birthReg.setBrDob(tbBirthregCorrentity.get(0).getBrDob());

		if (tbBirthregCorrentity.get(0).getBrSex() != null && !(tbBirthregCorrentity.get(0).getBrSex().isEmpty())) {
			birthReg.setBrSex(tbBirthregCorrentity.get(0).getBrSex());
		}
		if (tbBirthregCorrentity.get(0).getBrChildname() != null
				&& !(tbBirthregCorrentity.get(0).getBrChildname().isEmpty())) {
			birthReg.setBrChildName(tbBirthregCorrentity.get(0).getBrChildname());
		}
		if (tbBirthregCorrentity.get(0).getBrChildnameMar() != null
				&& !(tbBirthregCorrentity.get(0).getBrChildnameMar().isEmpty())) {
			birthReg.setBrChildNameMar(tbBirthregCorrentity.get(0).getBrChildnameMar());
		}
		if (tbBirthregCorrentity.get(0).getBrBirthaddr() != null
				&& !(tbBirthregCorrentity.get(0).getBrBirthaddr().isEmpty())) {
			birthReg.setBrBirthAddr(tbBirthregCorrentity.get(0).getBrBirthaddr());
		}
		if (tbBirthregCorrentity.get(0).getBrBirthaddrMar() != null
				&& !(tbBirthregCorrentity.get(0).getBrBirthaddrMar().isEmpty())) {
			birthReg.setBrBirthAddrMar(tbBirthregCorrentity.get(0).getBrBirthaddrMar());
		}
		if (tbBirthregCorrentity.get(0).getBrBirthplace() != null
				&& !(tbBirthregCorrentity.get(0).getBrBirthplace().isEmpty())) {
			birthReg.setBrBirthPlace(tbBirthregCorrentity.get(0).getBrBirthplace());
		}
		if (tbBirthregCorrentity.get(0).getBrBirthplaceMar() != null
				&& !(tbBirthregCorrentity.get(0).getBrBirthplaceMar().isEmpty())) {
			birthReg.setBrBirthPlaceMar(tbBirthregCorrentity.get(0).getBrBirthplaceMar());
		}
		if (tbBirthregCorrentity.get(0).getBrBirthplaceType() != null
				&& !(tbBirthregCorrentity.get(0).getBrBirthplaceType().isEmpty())) {
			birthReg.setBrBirthPlaceType(tbBirthregCorrentity.get(0).getBrBirthplaceType());
		}
		if (tbBirthregCorrentity.get(0).getBrRemarks() != null
				&& !(tbBirthregCorrentity.get(0).getBrRemarks().isEmpty())) {
			birthReg.setBrRemarks(tbBirthregCorrentity.get(0).getBrRemarks());
		}
		if (tbBirthregCorrentity.get(0).getBrStatus() != null
				&& !(tbBirthregCorrentity.get(0).getBrStatus().isEmpty())) {
			birthReg.setBrStatus(tbBirthregCorrentity.get(0).getBrStatus());
		}
		if (tbBirthregCorrentity.get(0).getCpdAttntypeId() != null) {
			birthReg.setCpdAttntypeId(tbBirthregCorrentity.get(0).getCpdAttntypeId());
		}
		if (tbBirthregCorrentity.get(0).getCpdDelmethId() != null) {
			birthReg.setCpdAttntypeId(tbBirthregCorrentity.get(0).getCpdDelmethId());
		}
		if (tbBirthregCorrentity.get(0).getBrInformantName() != null
				&& !(tbBirthregCorrentity.get(0).getBrInformantName().isEmpty())) {
			birthReg.setBrInformantName(tbBirthregCorrentity.get(0).getBrInformantName());
		} else {
			birthReg.setBrInformantName(tbBirthregentity.get(0).getBrInformantName());
		}
		if (tbBirthregCorrentity.get(0).getBrInformantAddr() != null
				&& !(tbBirthregCorrentity.get(0).getBrInformantAddr().isEmpty())) {
			birthReg.setBrInformantAddr(tbBirthregCorrentity.get(0).getBrInformantAddr());
		} else {
			birthReg.setBrInformantAddr(tbBirthregentity.get(0).getBrInformantAddr());
		}
		if (tbBirthregCorrentity.get(0).getWardid() != null) {
			birthReg.setWardid(tbBirthregCorrentity.get(0).getWardid());
		}
		else {
			birthReg.setWardid(tbBirthregentity.get(0).getWardid());
		}

    		//birthReg.setBrRegDate(new Date());
    		birthReg.setBrStatus("A");
    		birthReg.setBirthWFStatus(BndConstants.APPROVED);
    		birthReg.setBrCorrectionFlg("Y");
    		birthReg.setBrCorrnDate(new Date());
    		birthReg.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        	
    		birthRegRepository.save(birthReg);
    		
    		//birth Registration History start
    		BeanUtils.copyProperties(birthReg, birthRegHistEntity);
    		birthRegHistEntity.setAction(BndConstants.BR);
    		birthRegHistRepository.save(birthRegHistEntity);
    		//birth Registration history end 

    		//Parent Detail History start
    		BeanUtils.copyProperties(parentDetail, parentDetailHistEntity);
    		parentDetailHistEntity.setAction(BndConstants.BR);
    		parentDetailHistEntity.setPdBrId(parentDetail.getBrId().getBrId()); 
    		parentDetHistRepository.save(parentDetailHistEntity);
    		//Parent Detail history end 
    		requestDTO.setAlreayIssuedCopy(birthReg.getNoOfCopies());
    		if(!birthDeathList.isEmpty())
    		requestDTO.setNoOfCopies(birthDeathList.get(0).getCopies());
    		return requestDTO;
    	}
    	
        
    	public ApplicationService getApplicationService() {
    		return applicationService;
    	}

    	public void setApplicationService(ApplicationService applicationService) {
    		this.applicationService = applicationService;
    	}
    	
    	public IFileUploadService getFileUploadService() {
    		return fileUploadService;
    	}

    	public void setFileUploadService(IFileUploadService fileUploadService) {
    		this.fileUploadService = fileUploadService;
    	}

	@Override
	@Transactional
	public String updateBirthRegCorrApprove(BirthRegistrationCorrDTO tbBirthregcorrDTO, String lastDecision,
			String status) {
		Long aplId = tbBirthregcorrDTO.getApmApplicationId();
		Long brId =tbBirthregcorrDTO.getBrId();
		
		List<BirthDeathCFCInterface> birthDeathCFCInterface = birthDeathCfcInterfaceRepository.findData(aplId);
		

		if ((birthDeathCFCInterface.get(0).getCopies() != 0)&& (birthDeathCFCInterface.get(0).getCopies() != null)) {
			List<BirthRegistrationEntity> tbBirthregentity = birthRegDao.getBirthRegApplnData(brId, tbBirthregcorrDTO.getOrgId());
			String certnum=null;
			if((!tbBirthregentity.isEmpty())) {
			 certnum = tbBirthregentity.get(0).getBrCertNo();
			}
			if (certnum == null || certnum.equals("0")) {
				TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
				String finalcertificateNo = null;
				Long alreadyIssuCopy = tbBirthregcorrDTO.getAlreayIssuedCopy();
				if (alreadyIssuCopy == null) {
					alreadyIssuCopy = 0L;
				}
				TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
				SequenceConfigMasterDTO configMasterDTO = null;
		        Long deptId = departmentService.getDepartmentIdByDeptCode(BndConstants.BND, PrefixConstants.STATUS_ACTIVE_PREFIX);
		        configMasterDTO = seqGenFunctionUtility.loadSequenceData(tbBirthregcorrDTO.getOrgId(), deptId,
		        		BndConstants.TB_BIRTHREG, BndConstants.BR_CERT_NO);
		        if (configMasterDTO.getSeqConfigId() == null) {
		        	Long certificateNo = seqGenFunctionUtility.generateSequenceNo(BndConstants.BND, BndConstants.TB_BD_CERT_COPY, BndConstants.CERT_NO,
							tbBirthregcorrDTO.getOrgId(), "Y", null);
						
						String financialYear = UserSession.getCurrent().getCurrentDate();
						finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
								+ new DecimalFormat("000000").format(certificateNo);
		        }else {
		        	CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
		        	finalcertificateNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
		        }
				
					tbBdCertCopy.setOrgid(tbBirthregcorrDTO.getOrgId());
					tbBdCertCopy.setBrId(tbBirthregcorrDTO.getBrId());
					tbBdCertCopy.setCopyNo(birthDeathCFCInterface.get(0).getCopies());
					tbBdCertCopy.setUpdatedBy(tbBirthregcorrDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(tbBirthregcorrDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(tbBirthregcorrDTO.getUserId());
					tbBdCertCopy.setLgIpMac(tbBirthregcorrDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(tbBirthregcorrDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(tbBirthregcorrDTO.getApmApplicationId());
					
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					birthRegRepository.updateBrCertNo(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getOrgId(), finalcertificateNo);
				return finalcertificateNo;
			}
				else {
				if(certnum!=null || !certnum.equals("0")) {
				TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
				String finalcertificateNo = null;
				Long alreadyIssuCopy = null;
				List<BirthRegistrationEntity> birthEntity = birthRegRepository.findData(brId);
				if(!birthEntity.isEmpty()) {
					alreadyIssuCopy=birthEntity.get(0).getNoOfCopies();
				}else {
					alreadyIssuCopy=0L;
				}
				
					TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
					finalcertificateNo = certnum;
					tbBdCertCopy.setOrgid(tbBirthregcorrDTO.getOrgId());
					tbBdCertCopy.setBrId(tbBirthregcorrDTO.getBrId());
					tbBdCertCopy.setCopyNo(birthDeathCFCInterface.get(0).getCopies());
					tbBdCertCopy.setUpdatedBy(tbBirthregcorrDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(tbBirthregcorrDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(tbBirthregcorrDTO.getUserId());
					tbBdCertCopy.setLgIpMac(tbBirthregcorrDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(tbBirthregcorrDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(tbBirthregcorrDTO.getApmApplicationId());

					// saveCertcopy
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					birthRegRepository.updateBrCertNo(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getOrgId(), finalcertificateNo);
				return finalcertificateNo;
			 }
				}
		}
		return null;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, BirthRegistrationModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR,
				birthRegModel.getBirthRegDto().getOrgId());
		offline.setApplNo(birthRegModel.getBirthRegDto().getApmApplicationId());
		offline.setAmountToPay(birthRegModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		if(birthRegModel.getBirthRegDto().getParentDetailDTO().getPdFathername()!=null){
			offline.setApplicantName(birthRegModel.getBirthRegDto().getParentDetailDTO().getPdFathername());
		}else {
		offline.setApplicantName(birthRegModel.getBirthRegDto().getBrInformantName());
		}
		offline.setApplicantAddress(birthRegModel.getBirthRegDto().getParentDetailDTO().getMotheraddress());
		offline.setMobileNumber("NA");
		offline.setEmailId("NA");
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		if (birthRegModel.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : birthRegModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(birthRegModel.getCheckList())) {
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

			birthRegModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			printDto.setSubject(printDto.getSubject()+" - "+birthRegModel.getBirthRegDto().getBrRegNo());
			birthRegModel.setReceiptDTO(printDto);
			birthRegModel.setSuccessMessage(birthRegModel.getAppSession().getMessage("adh.receipt"));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void setAndSavebirthcorrChallanDtoOffLine(CommonChallanDTO offline, BirthCorrectionModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BRC,
				birthRegModel.getBirthRegDto().getOrgId());
		offline.setApplNo(birthRegModel.getBirthRegDto().getApmApplicationId());
		offline.setAmountToPay(birthRegModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		String fullName = String.join(" ", Arrays.asList(birthRegModel.getRequestDTO().getfName(),
				birthRegModel.getRequestDTO().getmName(), birthRegModel.getRequestDTO().getlName()));
		offline.setApplicantName(fullName);
		String wardName = "";
		 if(birthRegModel.getRequestDTO().getWardNo()!=0L && birthRegModel.getRequestDTO().getWardNo()!=null) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode = "";
		 if(birthRegModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(birthRegModel.getRequestDTO().getPincodeNo());
		 }
		String applicantAddress = String.join(" ",
				Arrays.asList(birthRegModel.getRequestDTO().getBldgName(),
						birthRegModel.getRequestDTO().getBlockName(), birthRegModel.getRequestDTO().getRoadName(),wardName,
						birthRegModel.getRequestDTO().getCityName(),pinCode));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(birthRegModel.getRequestDTO().getMobileNo());
		offline.setEmailId(birthRegModel.getRequestDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		if (birthRegModel.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : birthRegModel.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		if (CollectionUtils.isNotEmpty(birthRegModel.getCheckList())) {
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

			birthRegModel.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			printDto.setSubject(printDto.getSubject()+" - "+birthRegModel.getBirthRegDto().getBrRegNo());
			birthRegModel.setReceiptDTO(printDto);
			birthRegModel.setSuccessMessage(birthRegModel.getAppSession().getMessage("adh.receipt"));
		}
		
	}

	@Override
	public void saveBirthRegDet(BirthRegistrationDTO birthRegDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeWorkFlowForFreeService(BirthRegistrationDTO requestDto,ServiceMaster serviceMas) {
		boolean checkList = false;
		/*ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BRC,
				requestDto.getOrgId());*/
		boolean loiChargeApplflag = false;
        if (StringUtils.equalsIgnoreCase(serviceMas.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
            loiChargeApplflag = true;
        }
		if (CollectionUtils.isNotEmpty(requestDto.getUploadDocument())) {
			checkList = true;
		}
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getParentDetailDTO().getPdFathername());
		applicantDto.setServiceId(requestDto.getServiceId());
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
public boolean checkregnoByregno(String brRegno, Long orgId, Long brDraftId) {
	boolean result;
	Long count;
	if (brDraftId != null) {
		count = birthRegDraftRepository.checkduplregnobyRegnoanddraftId(brRegno, orgId, brDraftId);
		if (count > 0) {
			result = false;
		} else {
			count = birthRegRepository.checkRegno1(brRegno, orgId);
		}

	} else {
		count = birthRegDraftRepository.checkDuplicateRegno(brRegno, orgId);

		if (count == 0) {
			count = birthRegRepository.checkRegno1(brRegno, orgId);
		}
	}
	if (count.toString().equals("1")) {
		result = false;
	} else {
		result = true;
	}

	return result;
}
	
	
	@Override
	@Transactional(readOnly = true)
	@Path(value = "/SearchBirthData")
	@POST
	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetails(BirthRegistrationDTO birthCertificateDto) {
		
		List<BirthRegistrationDTO> registrationDetail = getBirthRegiDetailForCorr(birthCertificateDto);
			
		return registrationDetail;
	}
	
	@Override
	@Transactional
	@Path(value = "/SearchBirthDataById/{brId}")
	@POST
	public BirthRegistrationDTO getBirthByIDs(@PathParam("brId") final Long brId) {
		
		BirthRegistrationDTO dto =getBirthByID(brId);
		 ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC, dto.getOrgId());
		 Organisation org= iOrganisationDAO.getOrganisationById(dto.getOrgId(), "A");
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), org);
			if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
			{
				dto.setStatusCheck("A");
			}
			else {
				dto.setStatusCheck("NA");
			}
			 if(serviceMas.getSmFeesSchedule()!=0) {	 
				 dto.setChargesStatus("CC");
				}

		return dto;
	}
	
	
	// save birth correction
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	@POST
	@Path("/saveBirthRegCorrections")
	public BirthRegistrationDTO saveBirthCorrectionDets(BirthRegistrationDTO requestDTO) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthRegistrationCorrection birthRegCorrection = new BirthRegistrationCorrection();
		//BirthRegistrationHistoryEntity birthCorreHistoryEntity=new BirthRegistrationHistoryEntity();
		BirthRegistrationCorrectionHistory  birthCorreHistoryEntity=new BirthRegistrationCorrectionHistory();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		
		ParentDetailHistory parentDetailHistory=new ParentDetailHistory();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("BRC", requestDTO.getOrgId());
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setApmMode("F");
		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		//commonRequest.setMobileNo(requestDTO.getRequestDTO().getMobileNo());
		if(serviceMas.getSmFeesSchedule()==0)
		{
		  commonRequest.setPayStatus("F");
		}else {
			commonRequest.setPayStatus("Y");
		}
		//commonRequest.setUserId(requestDTO.getUserId());
		requestDTO.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
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
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId.toString());
		
		if ((applicationId != null) && (applicationId != 0)) {
			requestDTO.setApmApplicationId(applicationId);
		}
		if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getUploadDocument(), commonRequest);
		}
		BeanUtils.copyProperties(requestDTO, birthRegCorrection);
		if (requestDTO.getBrRegDate() != null) {
			birthRegCorrection.setBrRegdate(requestDTO.getBrRegDate());
		} else {
			birthRegCorrection.setBrRegdate(new Date());
		}
		birthRegCorrection.setBrId(requestDTO.getBrId());
		birthRegCorrection.setBrSex(requestDTO.getBrSex());
		birthRegCorrection.setBrBirthaddr(requestDTO.getBrBirthAddr());
		birthRegCorrection.setBrBirthaddrMar(requestDTO.getBrBirthAddrMar());
		birthRegCorrection.setBrBirthplace(requestDTO.getBrBirthPlace());
		birthRegCorrection.setBrBirthplaceMar(requestDTO.getBrBirthPlaceMar());
		birthRegCorrection.setOrgId(requestDTO.getOrgId());
		birthRegCorrection.setBrRegno(requestDTO.getBrRegNo());
		birthRegCorrection.setSmServiceId(serviceMas.getSmServiceId());
		birthRegCorrection.setPdMothername(requestDTO.getParentDetailDTO().getPdMothername());
		birthRegCorrection.setPdMothernameMar(requestDTO.getParentDetailDTO().getPdMothernameMar());
		birthRegCorrection.setPdFathername(requestDTO.getParentDetailDTO().getPdFathername());
		birthRegCorrection.setPdFathernameMar(requestDTO.getParentDetailDTO().getPdFathernameMar());
		birthRegCorrection.setBrCorrnDate(new Date());
		birthRegCorrection.setBrChildname(requestDTO.getBrChildName());
		birthRegCorrection.setBrChildnameMar(requestDTO.getBrChildNameMar());
		birthRegCorrection.setBrBirthplace(requestDTO.getBrBirthPlace());
		birthRegCorrection.setBrBirthplaceMar(requestDTO.getBrBirthPlaceMar());
		birthRegCorrection.setPdAddress(requestDTO.getParentDetailDTO().getPdAddress());
		birthRegCorrection.setPdAddressMar(requestDTO.getParentDetailDTO().getPdAddressMar());
		birthRegCorrection.setPdParaddress(requestDTO.getParentDetailDTO().getPdParaddress());
		birthRegCorrection.setPdParaddressMar(requestDTO.getParentDetailDTO().getPdParaddressMar());
		birthRegCorrection.setBrStatus(requestDTO.getBrStatus());
		birthRegCorrection.setBirthWFStatus(BndConstants.OPEN);
		birthRegCorrection.setBrInformantName(requestDTO.getBrInformantName());
		birthRegCorrection.setBrInformantAddr(requestDTO.getBrInformantAddr());
		birthRegCorrection.setCpdFEducnId(requestDTO.getParentDetailDTO().getCpdFEducnId());
		birthRegCorrection.setCpdFOccuId(requestDTO.getParentDetailDTO().getCpdFOccuId());
		birthRegCorrection.setCpdMEducnId(requestDTO.getParentDetailDTO().getCpdMEducnId());
		birthRegCorrection.setCpdMOccuId(requestDTO.getParentDetailDTO().getCpdMOccuId());
		birthRegCorrection.setCpdReligionId(requestDTO.getParentDetailDTO().getCpdReligionId());
		birthRegCorrection.setPdAgeAtBirth(requestDTO.getParentDetailDTO().getPdAgeAtBirth());
		birthRegCorrection.setPdLiveChildn(requestDTO.getParentDetailDTO().getPdLiveChildn());
		if (requestDTO.getCorrCategory() != null && !requestDTO.getCorrCategory().isEmpty()) {
			birthRegCorrection.setCorrCategory(String.join(",", requestDTO.getCorrCategory()));
		}
		birthRegCorrection.setUserId(requestDTO.getRequestDTO().getUserId());
		birthRegCorrectionRepository.save(birthRegCorrection);
		birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(), birthRegCorrection.getBirthWFStatus());

		// Birth Interface Entry
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegCorrection.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
        birthDeathCFCInterface.setUserId(requestDTO.getRequestDTO().getUserId());
        if (requestDTO.getNoOfCopies() != null) {
			birthDeathCFCInterface.setCopies(requestDTO.getNoOfCopies());
		} else {
			birthDeathCFCInterface.setCopies(0l);
		}
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		
	   //histort for correction start
	    BeanUtils.copyProperties(birthRegCorrection, birthCorreHistoryEntity);
	    birthCorreHistoryEntity.setAction(BndConstants.BRC);
	    birthRegCorreHistRepository.save(birthCorreHistoryEntity);
	    BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetailHistory);
	    parentDetailHistory.setAction(BndConstants.BRC);
	    parentDetailHistory.setOrgId(requestDTO.getOrgId());
		parentDetHistRepository.save(parentDetailHistory);
	   //histort for correction start
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			BirthRegistrationCorrDTO tbBirthregcorrDTO = new BirthRegistrationCorrDTO();
			BeanUtils.copyProperties(birthRegCorrection, tbBirthregcorrDTO);
			requestDTO.setApplicationId(String.valueOf(applicationId));
			tbBirthregcorrDTO.setApmApplicationId(applicationId);
			updateBirthApproveStatus(tbBirthregcorrDTO, MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBirthregcorrDTO.setBirthWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    	updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Status.CLOSED, birthRegCorrection.getOrgId());
	    	//certificate generation/update
	    	updateBirthRegCorrApprove(tbBirthregcorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	// save data to birth registration entity after final approval 
	    	requestDTO.setApmApplicationId(tbBirthregcorrDTO.getApmApplicationId());
	    	saveBirthRegDetOnApproval(requestDTO);
			
		}
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount()==0.0) {
			initializeWorkFlowForFreeService(requestDTO,serviceMas);
			Organisation organisation = iOrganisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
			requestDTO.setUserId(requestDTO.getRequestDTO().getUserId());
			smsAndEmail(requestDTO, organisation);
		} else {
		//setAndSavebirthcorrChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		return requestDTO;
	}
	
	@Override
	@Transactional
	@Path(value = "/getBirthByIDPortal/{brId}")
	@POST
	public BirthRegistrationDTO getBirthByIDPortal(@PathParam("brId") final Long brId) {
		BirthRegistrationDTO dto = getBirthByID(brId);
		Organisation org= iOrganisationDAO.getOrganisationById(dto.getOrgId(), "A");
		 ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.INC, dto.getOrgId());
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),org);
			if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
			{
				dto.setStatusCheck("A");
			}
			else {
				dto.setStatusCheck("NA");
			}
			 if(serviceMas.getSmFeesSchedule()!=0) {
				 dto.setChargesStatus("CC");
				}

		return dto;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	@POST
	@Path("/saveInclusionOfChildPortal")
	public BirthRegistrationDTO saveInclusionOfChildPortal(BirthRegistrationDTO requestDTO) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthRegistrationCorrectionHistory birthRegCorreHistory=new BirthRegistrationCorrectionHistory();
		BirthRegistrationCorrection birthRegCorrection = new BirthRegistrationCorrection();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.INC, requestDTO.getOrgId());
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setApmMode("F");
		//commonRequest.setUserId(requestDTO.getUserId());
		//commonRequest.setMobileNo(requestDTO.getMobileNo());
		if(serviceMas.getSmFeesSchedule()==0)
		{
		  commonRequest.setPayStatus("F");
		}else {
			commonRequest.setPayStatus("Y");
		}
		requestDTO.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		// Generate the Application Number 
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
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApplicationId(applicationId);
		commonRequest.setReferenceId(applicationId.toString());
		requestDTO.setApplicationId(applicationId.toString());
		if ((applicationId != null) && (applicationId != 0)) {
			requestDTO.setApmApplicationId(applicationId);
		}
		if ((requestDTO.getUploadDocument() != null) && !requestDTO.getUploadDocument().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getUploadDocument(), commonRequest);
		}
		
		birthRegCorrection.setBrId(requestDTO.getBrId());
		birthRegCorrection.setBrChildname(requestDTO.getBrChildName());
		birthRegCorrection.setBrChildnameMar(requestDTO.getBrChildNameMar());
		birthRegCorrection.setBrSex(requestDTO.getBrSex());
		birthRegCorrection.setBrBirthaddr(requestDTO.getBrBirthAddr());
		birthRegCorrection.setBrBirthaddrMar(requestDTO.getBrBirthAddrMar());
		birthRegCorrection.setBrBirthplace(requestDTO.getBrBirthPlace());
		birthRegCorrection.setBrBirthplaceMar(requestDTO.getBrBirthPlaceMar());
		birthRegCorrection.setPdFathername(requestDTO.getParentDetailDTO().getPdFathername());
		birthRegCorrection.setPdFathernameMar(requestDTO.getParentDetailDTO().getPdFathernameMar());
		birthRegCorrection.setPdMothername(requestDTO.getParentDetailDTO().getPdMothername());
		birthRegCorrection.setPdMothernameMar(requestDTO.getParentDetailDTO().getPdMothernameMar());
		birthRegCorrection.setOrgId(requestDTO.getOrgId());
		birthRegCorrection.setSmServiceId(serviceMas.getSmServiceId());
		birthRegCorrection.setApmApplicationId(applicationId);
		birthRegCorrection.setBrRegdate(requestDTO.getBrRegDate());
		birthRegCorrection.setLmodDate(new Date());
		birthRegCorrection.setUserId(1L);
		birthRegCorrection.setBrCertNo(String.valueOf(requestDTO.getNoOfCopies()));
		birthRegCorrection.setBrStatus(requestDTO.getBrStatus());
		birthRegCorrection.setBirthWFStatus(BndConstants.OPEN);
		birthRegCorrection.setBrDob(requestDTO.getBrDob());
		birthRegCorrection.setBrRegno(requestDTO.getBrRegNo());
		birthRegCorrection.setPdParaddress(requestDTO.getParentDetailDTO().getPdParaddress());
		birthRegCorrectionRepository.save(birthRegCorrection);
		birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(), birthRegCorrection.getBirthWFStatus());
		
		// Birth Interface Entry
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegCorrection.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
		birthDeathCFCInterface.setUserId(requestDTO.getRequestDTO().getUserId());
	    birthDeathCFCInterface.setCopies(requestDTO.getNoOfCopies());
	    birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		//vi end
		//child inclusion history start
		 //BeanUtils.copyProperties(birthRegCorrection, birthRegCorreHistory);
		birthRegCorreHistory.setBrChildname(birthRegCorrection.getBrChildname());
		birthRegCorreHistory.setBrChildnameMar(birthRegCorrection.getBrChildnameMar());
		birthRegCorreHistory.setBrId(birthRegCorrection.getBrId());
		birthRegCorreHistory.setBrCertNo(birthRegCorrection.getBrCertNo());
		birthRegCorreHistory.setApmApplicationId(applicationId);
		birthRegCorreHistory.setAction(BndConstants.INC);
		birthRegCorreHistory.setBrRegdate(requestDTO.getBrRegDate());
		birthRegCorreHistory.setBrSex(birthRegCorrection.getBrSex());
		birthRegCorreHistory.setUserId(1l);
		birthRegCorreHistory.setOrgId(birthRegCorrection.getOrgId());
		birthRegCorreHistory.setSmServiceId(serviceMas.getSmServiceId());
		birthRegCorreHistory.setLmodDate(new Date());
		birthRegCorreHistRepository.save(birthRegCorreHistory);
		//child inclusion history start
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			BirthRegistrationCorrDTO tbBirthregcorrDTO = new BirthRegistrationCorrDTO();
			BeanUtils.copyProperties(birthRegCorrection, tbBirthregcorrDTO);
			requestDTO.setApplicationId(String.valueOf(applicationId));
			tbBirthregcorrDTO.setApmApplicationId(applicationId);
			updateBirthApproveStatus(tbBirthregcorrDTO, MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBirthregcorrDTO.setBirthWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Status.CLOSED, birthRegCorrection.getOrgId());
	    	//certificate generation/update
	    	updateBirthRegCorrApprove(tbBirthregcorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	// save data to birth registration entity after final approval 
	    	BeanUtils.copyProperties(tbBirthregcorrDTO, requestDTO);
	    	requestDTO.setBrChildName(birthRegCorrection.getBrChildname());
	    	requestDTO.setBrChildNameMar(birthRegCorrection.getBrChildnameMar());
	    	inclusionOfChildNameService.saveInclusionOfChildOnApproval(requestDTO);
		}
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.getParentDetailDTO().setPdFathername(birthRegCorrection.getPdFathername());
		requestDTO.getParentDetailDTO().setPdParaddress(birthRegCorrection.getPdParaddress());
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount()==0.0) {
			initializeWorkFlowForFreeService(requestDTO,serviceMas);
		} else {
		//setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		Organisation organisation = iOrganisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		requestDTO.setUserId(requestDTO.getRequestDTO().getUserId());
		smsAndEmailIncOfChild(requestDTO, organisation);
		return requestDTO;	
	} 
	

	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId and ServiceCode", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/service-information/{orgId}/{serviceShortCode}")
	public LinkedHashMap<String, Object> serviceInformation(@PathParam("orgId") Long orgId,
			@PathParam("serviceShortCode") String serviceShortCode) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceByShortName(serviceShortCode, Long.valueOf(orgId));
		LinkedHashMap<String, Object> serviceDataMap = new LinkedHashMap<String, Object>();
	//	LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify());

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("lookup", "A");
			} 
			else {
				serviceDataMap.put("lookup", MainetConstants.FlagN);
			}
			if (serviceMasterData.getSmFeesSchedule()!=0) {
				serviceDataMap.put("ChargesStatus", "CA");

			} 
			else {
				serviceDataMap.put("ChargesStatus", MainetConstants.FlagN);
			}
			serviceDataMap.put("serviceNameEng", serviceMasterData.getSmServiceName());
			serviceDataMap.put("serviceNameReg", serviceMasterData.getSmServiceNameMar());
			serviceDataMap.put("deptNameEng", serviceMasterData.getTbDepartment().getDpDeptdesc());
			serviceDataMap.put("deptNameReg", serviceMasterData.getTbDepartment().getDpNameMar());
			serviceDataMap.put("smServiceDuration", String.valueOf(serviceMasterData.getSmServiceDuration()));
		}

		return serviceDataMap;
	}

	@Override
	@Transactional
	public List<BirthRegistrationDTO> getBirthRegiDetailForCorr(BirthRegistrationDTO birthRegDto) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR, birthRegDto.getOrgId());
		Long smServiceId = serviceMas.getSmServiceId();
		birthRegDto.setServiceId(smServiceId);
		List<BirthRegistrationEntity> DetailEntity = birthRegDao.getBirthRegCorrDetList(birthRegDto);
		List<BirthRegistrationDTO> listDTO=new ArrayList<BirthRegistrationDTO>();
		
		if (DetailEntity != null) {
			DetailEntity.forEach(entity->{
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				 ParentDetailDTO pDto = new ParentDetailDTO(); 
				  if(entity!=null) {
				  BeanUtils.copyProperties(entity, dto);
				  BeanUtils.copyProperties(entity.getParentDetail(), pDto);
				  }
                                dto.setBirthWfStatus(entity.getBirthWFStatus());
				  pDto.setCpdId3(entity.getParentDetail().getCpdId3());
				dto.setParentDetailDTO(pDto);
				listDTO.add(dto);
				
				   });
		
				}
			
		return listDTO;
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(BirthRegistrationDTO dto,Organisation organisation)
	{//birth Correction sms email
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		smdto.setServName(dto.getRequestDTO().getServiceShortCode());
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(dto.getAmount()==null) {
			dto.setAmount(0.0d);
		}
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.BIRTH_REG_CORR_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}

	@Override
	@Transactional
	public void smsAndEmailApproval(BirthRegistrationDTO birthRegDto, String decision) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(birthRegDto.getUserId());
		smdto.setAppNo(birthRegDto.getApplicationId());
		smdto.setMobnumber(birthRegDto.getRequestDTO().getMobileNo());
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smdto.setCc(decision);
		String fullName = String.join(" ", Arrays.asList(birthRegDto.getRequestDTO().getfName(),
				birthRegDto.getRequestDTO().getmName(), birthRegDto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setEmail(birthRegDto.getRequestDTO().getEmail());
		smdto.setRegNo(birthRegDto.getBrCertNo());
		String alertType = MainetConstants.BLANK;
		if(decision.equals(BndConstants.APPROVED)) {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
		}else if(decision.equals(BndConstants.REJECTED)){
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		}else {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG;
		}
		
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BND, BndConstants.BIRTH_CORR_APPR_URL, alertType, smdto, organisation, birthRegDto.getLangId());
	}

	@Override
	@Transactional
	public RequestDTO getApplicantDetailsByApplNoAndOrgId(Long applnNO, Long orgId) {
		RequestDTO dto = new RequestDTO();
		List<Object[]> listObject = birthRegDao.getApplicantDetailsByApplNoAndOrgId(applnNO,orgId);
		for(Object[] obj : listObject) {
			 dto.setMobileNo(String.valueOf(obj[0]));
			 dto.setEmail(String.valueOf(obj[1]));
			 dto.setfName(String.valueOf(obj[2]));
			 dto.setmName(String.valueOf(obj[3]));
			 dto.setlName(String.valueOf(obj[4]));
			// Date d1=new Date(String.valueOf(obj[5]));
			 //Date date = Utility.stringToDate(String.valueOf(obj[5]));
			 dto.setApplicationDate(new Date());

		 }
		return dto;
	}
	
	@Transactional
	public List<BirthReceiptDTO> getBirethReceiptData(BirthReceiptDTO receiptData) {
	
		List<BirthReceiptDTO> listDTO=new ArrayList<BirthReceiptDTO>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format=null;
		String rmdate=null;
		String deathdate=null;
		if(receiptData.getBirthDate()!=null) {
		Date fromDate = receiptData.getBirthDate();
		 format = formatter.format(fromDate);
		}
		if(receiptData.getRmDate()!=null) {
			Date fromDate1 = receiptData.getRmDate();
			rmdate = formatter.format(fromDate1);
			}
		if(receiptData.getDeathDate()!=null) {
			Date fromDate2 = receiptData.getDeathDate();
			deathdate = formatter.format(fromDate2);
			}
	
		List<Object[]> listObject = birthCertificateRepository.getBirthReceiptData(format,rmdate,receiptData.getBrChildName(),receiptData.getRmRcptno(),deathdate,receiptData.getDeathName());
		
		for(Object[] obj : listObject) {
					BirthReceiptDTO dto = new BirthReceiptDTO();
					dto.setRmRcptno(Long.valueOf(obj[0].toString()));
					dto.setRmDate((Date) obj[1]);
					dto.setRmReceivedfrom(String.valueOf(obj[2]));
					dto.setRmAmount(Double.valueOf(obj[3].toString()));
					listDTO.add(dto);
					}
			
		return listDTO;
	}

	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmailIncOfChild(BirthRegistrationDTO dto,Organisation organisation)
	{
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		if(dto.getAmount()==null) {
			dto.setAmount(0.0d);
		}
		smdto.setAmount(dto.getAmount());
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BIRTH_DEATH, BndConstants.INCLUSION_CHILD_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, dto.getLangId());

	}
	
	@Override
	@Transactional
	@POST
	@ApiOperation(value = "fetch Hospitals", notes = "fetch Hospitals")
	@Path(value = "/fetchHospitals/{hiId}")
	public HospitalMasterDTO getHospitalById(@PathParam("hiId") Long hiId) {
		HospitalMaster hospital = hospitalMasterRepository.findOne(hiId);
		HospitalMasterDTO hospitalMasterDTO = new HospitalMasterDTO();
		BeanUtils.copyProperties(hospital, hospitalMasterDTO);

		return hospitalMasterDTO;
	}
	
	@Override
	@Transactional
	@Path(value = "/getBirthByApplIdPortal/{applicationId}/{orgId}")
	@POST
	public BirthRegistrationDTO getBirthByApplId(@PathParam("applicationId") Long applicationId,
			@PathParam("orgId") Long orgId) {
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		List<BirthRegistrationCorrDTO> tbBirthRegCorrDtoList = getBirthRegisteredAppliDetailFromApplnId(applicationId,
				orgId);
		if (tbBirthRegCorrDtoList != null) {
			BeanUtils.copyProperties(tbBirthRegCorrDtoList.get(0), dto);
			dto.getParentDetailDTO().setPdFathername(tbBirthRegCorrDtoList.get(0).getPdFathername());
			dto.getParentDetailDTO().setPdFathernameMar(tbBirthRegCorrDtoList.get(0).getPdFathernameMar());
			dto.getParentDetailDTO().setPdMothername(tbBirthRegCorrDtoList.get(0).getPdMothername());
			dto.getParentDetailDTO().setPdMothernameMar(tbBirthRegCorrDtoList.get(0).getPdMothernameMar());
			dto.getParentDetailDTO().setCpdFEducnId(tbBirthRegCorrDtoList.get(0).getCpdFEducnId());
			dto.getParentDetailDTO().setCpdFOccuId(tbBirthRegCorrDtoList.get(0).getCpdFOccuId());
			dto.getParentDetailDTO().setCpdMEducnId(tbBirthRegCorrDtoList.get(0).getCpdMEducnId());
			dto.getParentDetailDTO().setCpdMOccuId(tbBirthRegCorrDtoList.get(0).getCpdMOccuId());
			dto.getParentDetailDTO().setPdAgeAtBirth(tbBirthRegCorrDtoList.get(0).getPdAgeAtBirth());
			dto.getParentDetailDTO().setPdLiveChildn(tbBirthRegCorrDtoList.get(0).getPdLiveChildn());
			dto.getParentDetailDTO().setPdParaddress(tbBirthRegCorrDtoList.get(0).getPdParaddress());
			dto.getParentDetailDTO().setPdParaddressMar(tbBirthRegCorrDtoList.get(0).getPdParaddressMar());
			dto.getParentDetailDTO().setCpdReligionId(tbBirthRegCorrDtoList.get(0).getCpdReligionId());
			long lookupId = CommonMasterUtility.lookUpIdByLookUpDescAndPrefix(tbBirthRegCorrDtoList.get(0).getBrSex(),
					GENDER, orgId);
			dto.setBrSex(String.valueOf(lookupId));
			Long noOfCopies = issuenceOfBirthCertificateDao.getNoOfRequestCopies(applicationId.toString(), orgId);
			if (noOfCopies != null) {
				dto.setNoOfCopies(noOfCopies);
			} else {
				dto.setNoOfCopies(0L);
			}
		}
		return dto;
	}

	@Override
	@Transactional
	public String executeFinalWorkflowAction(WorkflowTaskAction taskAction, Long serviceId,
			BirthRegistrationDTO birthRegDto, BirthRegistrationCorrDTO tbBirthregcorrDTO) {
		String certificateno = null;
		tbBirthregcorrDTO.setApmApplicationId(taskAction.getApplicationId());
		tbBirthregcorrDTO.setServiceId(serviceId);
		birthRegDto.setApmApplicationId(taskAction.getApplicationId());
		updateBirthApproveStatus(tbBirthregcorrDTO, taskAction.getDecision(), MainetConstants.WorkFlow.Status.CLOSED);
		tbBirthregcorrDTO.setBirthWfStatus(taskAction.getDecision());
		updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(), MainetConstants.WorkFlow.Status.CLOSED,
				taskAction.getOrgId());
		// certificate generation/update
		certificateno = updateBirthRegCorrApprove(tbBirthregcorrDTO, taskAction.getDecision(),
				MainetConstants.WorkFlow.Status.CLOSED);
		// save data to birth registration entity after final approval
		birthRegDto.setApmApplicationId(tbBirthregcorrDTO.getApmApplicationId());
		BirthRegistrationDTO saveOnApproval = saveBirthRegDetOnApproval(birthRegDto);
		issuenceOfBirthCertificateService.updatNoOfcopyStatus(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getOrgId(),
				tbBirthregcorrDTO.getBrId(), saveOnApproval.getNoOfCopies());
		birthRegDto.setBrCertNo(certificateno);
		birthRegDto.setApplicationId(String.valueOf(taskAction.getApplicationId()));
		smsAndEmailApproval(birthRegDto, taskAction.getDecision());
		ideathregCorrectionService.updateWorkflowTaskAction(taskAction, serviceId);
		return certificateno;
	}
	
	@Override
	@Transactional
	@POST	
	@Path(value ="/getTaxMasterByTaxCodeFromPortal/{orgId}/{taxCode}")
	public LinkedHashMap<String, Object> getTaxMasterByTaxCodeFromPortal(@PathParam("orgId") Long orgId,@PathParam("taxCode") String taxCode) {
		LinkedHashMap<String, Object> serviceDataMap = new LinkedHashMap<String, Object>();
		Long deptId = departmentService.getDepartmentIdByDeptCode(BndConstants.BND,PrefixConstants.STATUS_ACTIVE_PREFIX);
		final TbTaxMas entity = tbTaxMasService.getTaxMasterByTaxCode(orgId, deptId, taxCode);
		if(entity!=null) {
		serviceDataMap.put("taxName", entity.getTaxDesc());
		serviceDataMap.put("taxId", entity.getTaxId());
		}
		return serviceDataMap;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBirthRemark(Long brId, String birthRegremark,Long orgId) {
		birthRegRepository.updateBirthRemark(brId,orgId,birthRegremark);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBirthCorrectionRemark(Long brCorrId, String corrAuthRemark,Long orgId) {
		birthRegCorrectionRepository.updateBirthCorrectionRemark(brCorrId, orgId, corrAuthRemark);
	}
	
	
	// save data entry for birth registration
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public BirthRegistrationDTO saveDataEntryBirthRegDet(@RequestBody final BirthRegistrationDTO requestDTO) {

		final RequestDTO commonRequest = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DEB,
				requestDTO.getOrgId());
		BirthRegistrationEntity birthReg1 = birthRegRepository.findOne(requestDTO.getBrId());
		BirthRegistrationEntityTemp birthReg = new BirthRegistrationEntityTemp();
		BeanUtils.copyProperties(birthReg1, birthReg);
		ParentDetailTemp parentDetail = new ParentDetailTemp();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		BirthRegistrationHistoryEntity birthRegHistEntity = new BirthRegistrationHistoryEntity();
		ParentDetailHistory parentDetailHistEntity = new ParentDetailHistory();
		TbCfcApplicationMstEntity tbCfcApplicationMstEntity = new TbCfcApplicationMstEntity();

		// Parent Detail Entry
		parentDetail.setOrgId(requestDTO.getOrgId());
		BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetail);
		//parentDetail.setPdId(birthReg.getParentDetail().getpdi);
		parentDetail.setUserId(requestDTO.getUserId());
		parentDetail.setLangId(requestDTO.getLangId());
		parentDetail.setLmodDate(requestDTO.getLmodDate());
		parentDetail.setUpdatedBy(requestDTO.getUpdatedBy());
		parentDetail.setUpdatedDate(requestDTO.getUpdatedDate());
		parentDetail.setLgIpMac(requestDTO.getLgIpMac());
		parentDetail.setLgIpMacUpd(requestDTO.getLgIpMacUpd());
		if (requestDTO.getParentDetailDTO().getCpdId1() != null) {
			LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(requestDTO.getParentDetailDTO().getCpdId1(),
					parentDetail.getOrgId());
			if (lookUp.getLookUpCode().equals("IND")) {
				parentDetail.setAddressflag("I");
			}
		} else {
			parentDetail.setAddressflag("O");
		}
		parentDetail.setBrId(birthReg);
		birthReg.setParentDetail(parentDetail);
		parentDetail.setPdId(birthReg.getParentDetail().getPdId());
		// Birth Registration Entry
		BeanUtils.copyProperties(requestDTO, birthReg);
		birthReg.getParentDetail().setPdId(parentDetail.getPdId());
		birthReg.setOrgId(requestDTO.getOrgId());
		birthReg.setHiId(requestDTO.getHiId());
		birthReg.setBrBirthWt(requestDTO.getBrBirthWt());
		birthReg.setBrRegDate(requestDTO.getBrRegDate());
		birthReg.setBrAdopFlg("N");
		birthReg.setBrCorrectionFlg("N");
		birthReg.setBrStillBirthFlg("N");
		birthReg.setBrOrphanReg("N");
		birthReg.setBrFlag("C");
		birthReg.setBrManualCertNo(0l);
		birthReg.setBcrFlag("N");
		birthReg.setHrReg("N");
		birthReg.setNoOfCopies(0l);
		String processName = null;
		if (serviceMas != null && serviceMas.getSmProcessId() != null) {
			Organisation org = new Organisation();
			org.setOrgid(birthReg.getOrgId());
			final LookUp processLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(serviceMas.getSmProcessId(), org);
			if (processLookUp != null && !MainetConstants.CommonConstants.NA.equals(processLookUp.getLookUpCode())) {
				processName = processLookUp.getLookUpCode().toLowerCase();
			}
		}
		if (processName != null) {
			birthReg.setBrStatus(BndConstants.STATUS);
			birthReg.setBirthWFStatus(BndConstants.OPEN);
		}else {
			birthReg.setBrStatus(BndConstants.BIRTH_STATUS_APPROVED);
			birthReg.setBirthWFStatus(BndConstants.APPROVED);
		}
		birthReg.setBrCertNo("0");
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
				Long.parseLong(birthReg.getBrBirthPlaceType()), birthReg.getOrgId(), "DPT");

		if (lookup.getLookUpCode().equals("I")) {
			birthReg.setBrHospital("Y");
		} else {
			birthReg.setBrHospital("N");
		}

		birthRegTempRepository.save(birthReg);
		
		birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(),birthReg.getBirthWFStatus());

		// birth Registration History start
		BeanUtils.copyProperties(birthReg, birthRegHistEntity);
		birthRegHistEntity.setAction(BndConstants.DEB);
		birthRegHistRepository.save(birthRegHistEntity);
		// birth Registration history end

		// Parent Detail History start
		BeanUtils.copyProperties(parentDetail, parentDetailHistEntity);
		parentDetailHistEntity.setAction(BndConstants.DEB);
		parentDetailHistEntity.setPdBrId(parentDetail.getBrId().getBrId()); 
		parentDetHistRepository.save(parentDetailHistEntity);
		// Parent Detail history end

		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApmMode("F");
		commonRequest.setfName(requestDTO.getParentDetailDTO().getPdFathername());
		commonRequest.setGender(requestDTO.getBrSex());

		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		if (serviceMas.getSmFeesSchedule() == 0) {
			commonRequest.setPayStatus("F");
		} else {
			commonRequest.setPayStatus("Y");
		}

		commonRequest.setUserId(requestDTO.getUserId());
		if (requestDTO.getApmApplicationId() != null) {
			Long aplId = requestDTO.getApmApplicationId();
			commonRequest.setReferenceId(String.valueOf(aplId));
			commonRequest.setApplicationId(aplId);
			List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);
			if (birth != null) {
				birth.forEach(entity -> {
					BeanUtils.copyProperties(entity, birthDeathCFCInterface);
					birthDeathCFCInterface.setBdRequestId(birthReg.getBrId());
					birthDeathCFCInterface.setUserId(birthReg.getUserId());
					birthDeathCFCInterface.setLmodDate(new Date());
					tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
				});
			}
		} else if ((requestDTO.getApmApplicationId() == null) || (requestDTO.getApmApplicationId().equals("0"))) {
			commonRequest.setDeptId(departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,PrefixConstants.STATUS_ACTIVE_PREFIX));
			commonRequest.setTableName(MainetConstants.CommonMasterUi.TB_CFC_APP_MST);
			commonRequest.setColumnName(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
			LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
			String monthStr = localDate.getMonthValue() < 10 ? "0"+localDate.getMonthValue() : String.valueOf(localDate.getMonthValue());
			String dayStr = localDate.getDayOfMonth() < 10 ? "0"+localDate.getDayOfMonth() : String.valueOf(localDate.getDayOfMonth());
			commonRequest.setCustomField(String.valueOf(monthStr+""+dayStr));

			final Long applicationId = applicationService.createApplication(commonRequest);

			if (null == applicationId) {
				throw new RuntimeException("Application Not Generated");
			}
			commonRequest.setReferenceId(String.valueOf(applicationId));
			commonRequest.setApplicationId(applicationId);
			requestDTO.setApplicationId(applicationId.toString());
			if ((applicationId != null) && (applicationId != 0)) {
				requestDTO.setApmApplicationId(applicationId);
			}
			// Birth Interface Entry
			BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
			birthDeathCFCInterface.setBdRequestId(birthReg.getBrId());
			birthReg.getParentDetail().setBrId(birthReg);
			birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
			birthDeathCFCInterface.setUserId(birthReg.getUserId());
			birthDeathCFCInterface.setCopies(0l);
			birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
			tbBdCfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		}
		if ((requestDTO.getApmApplicationId() != null) && (requestDTO.getApmApplicationId() != 0)) {
			Long lang = Long.valueOf(requestDTO.getLangId());
			Organisation org = UserSession.getCurrent().getOrganisation();
			tbCfcApplicationMstEntity.setApmFname(requestDTO.getParentDetailDTO().getPdFathername());
			tbCfcApplicationMstEntity.setApmApplicationId(requestDTO.getApmApplicationId());
			tbCfcApplicationMstEntity.setApmSex(requestDTO.getBrSex());
			tbCfcApplicationMstEntity.setApmApplicationDate(requestDTO.getBrRegDate());
			tbCfcApplicationMstEntity.setUserId(requestDTO.getUserId());
			tbCfcApplicationMstEntity.setLangId(lang);
			tbCfcApplicationMstEntity.setLmoddate(requestDTO.getLmodDate());
			tbCfcApplicationMstEntity.setApmChklstVrfyFlag("P");
			tbCfcApplicationMstEntity.setApmPayStatFlag(commonRequest.getPayStatus());
			tbCfcApplicationMstEntity.setTbOrganisation(org);
			tbCfcApplicationMstEntity.setTbServicesMst(serviceMas);
			tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		}
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount() == 0.0) {
			requestDTO.setServiceId(serviceMas.getSmServiceId());
			initializeWorkFlowForFreeService(requestDTO, serviceMas);
		}
		return requestDTO;

	}
	
	@Override
	@Transactional(readOnly = true)
	@Path(value = "/getBirthRegDetailFordataEntry")
	@ApiOperation(value = "get application detail", response = BirthRegistrationDTO.class)
	@GET
	public List<BirthRegistrationDTO> getBirthRegDetailFordataEntry(@QueryParam("certNo") String certNo,
			@QueryParam("brRegNo") String brRegNo, @QueryParam("year") String year,@QueryParam("brDob") Date brDob,@QueryParam("brChildName") String brChildName,
			@QueryParam("applicnId") String applicnId, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DEB, orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		List<BirthRegistrationEntity> DetailEntity = birthRegDao.getBirthRegisteredApplicantList(certNo, brRegNo, year,brDob,brChildName,smServiceId,
				applicnId, orgId);
		List<BirthRegistrationDTO> listDTO=new ArrayList<BirthRegistrationDTO>();
		
		if (DetailEntity != null) {
			DetailEntity.forEach(entity->{
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				 ParentDetailDTO pDto = new ParentDetailDTO(); 
				  if(entity!=null) {
				  BeanUtils.copyProperties(entity, dto);
				  BeanUtils.copyProperties(entity.getParentDetail(), pDto);
				  }
                                dto.setBirthWfStatus(entity.getBirthWFStatus());
				  pDto.setCpdId3(entity.getParentDetail().getCpdId3());
				dto.setParentDetailDTO(pDto);
				listDTO.add(dto);
				
				   });
		
				}
			
		return listDTO;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<BirthRegistrationDTO> getBirthRegDetailFordataEntryTemp(@QueryParam("certNo") String certNo,
			@QueryParam("brRegNo") String brRegNo, @QueryParam("year") String year,@QueryParam("brDob") Date brDob,@QueryParam("brChildName") String brChildName,
			@QueryParam("applicnId") String applicnId, @QueryParam("orgId") Long orgId) {
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DEB, orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		List<BirthRegistrationEntityTemp> DetailEntity = birthRegTempDao.getBirthRegisteredApplicantListTemp(certNo, brRegNo, year,brDob,brChildName,smServiceId,
				applicnId, orgId);
		List<BirthRegistrationDTO> listDTO=new ArrayList<BirthRegistrationDTO>();
		
		if (DetailEntity != null) {
			DetailEntity.forEach(entity->{
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				 ParentDetailDTO pDto = new ParentDetailDTO(); 
				  if(entity!=null) {
				  BeanUtils.copyProperties(entity, dto);
				  BeanUtils.copyProperties(entity.getParentDetail(), pDto);
				  }
                                dto.setBirthWfStatus(entity.getBirthWFStatus());
				  pDto.setCpdId3(entity.getParentDetail().getCpdId3());
				dto.setParentDetailDTO(pDto);
				listDTO.add(dto);
				
				   });
		
				}
			
		return listDTO;
	}
	
	
	@Override
	@Transactional
	public BirthRegistrationDTO saveBirthRegTempDetOnApproval(BirthRegistrationDTO requestDTO) {
		BirthRegistrationEntity birthReg = new BirthRegistrationEntity();
		ParentDetail parentDetail = new ParentDetail();
		
		//Birth Registration Entry
		BeanUtils.copyProperties(requestDTO, birthReg);
		birthReg.setBirthWFStatus(BndConstants.APPROVED);
		
		BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetail);
		parentDetail.setBrId(birthReg);
		birthReg.setParentDetail(parentDetail);
		birthRegRepository.save(birthReg);
		
		return requestDTO;
	}
	
	@Override
	@Transactional
	public String getBirthAppn(@PathParam("brId") final Long brId,@PathParam("brId") final Long orgId) {
		String status=null;
		List<BirthDeathCFCInterface> bdAppl=tbBdCfcInterfaceJpaRepository.findAppBirthorDeath(brId,orgId);
		Long application=null;
		if(!bdAppl.isEmpty()) {
		application=bdAppl.get(0).getApmApplicationId();
		 status = tbBdCfcInterfaceJpaRepository.getWorkflowRequestByAppId(application, orgId);
		}
		return status;
	}
}
