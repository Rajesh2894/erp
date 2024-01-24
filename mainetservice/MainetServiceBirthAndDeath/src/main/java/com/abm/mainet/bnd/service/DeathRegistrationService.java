package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dao.IDeathRegCorrDao;
import com.abm.mainet.bnd.dao.IDeathRegDraftDao;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.DeceasedMaster;
import com.abm.mainet.bnd.domain.DeceasedMasterHistory;
import com.abm.mainet.bnd.domain.DeceasedMasterTemp;
import com.abm.mainet.bnd.domain.MedicalMaster;
import com.abm.mainet.bnd.domain.MedicalMasterHistory;
import com.abm.mainet.bnd.domain.MedicalMasterTemp;
import com.abm.mainet.bnd.domain.TbBdCertCopy;
import com.abm.mainet.bnd.domain.TbBdDeathregCorr;
import com.abm.mainet.bnd.domain.TbDeathRegHistory;
import com.abm.mainet.bnd.domain.TbDeathRegdraft;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.domain.TbDeathregTemp;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathRegdraftDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.repository.BirthDeathCertificateCopyRepository;
import com.abm.mainet.bnd.repository.BirthDeathCfcInterfaceRepository;
import com.abm.mainet.bnd.repository.CertCopyRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.DeathRegDraftRepository;
import com.abm.mainet.bnd.repository.DeathRegHistoryrepository;
import com.abm.mainet.bnd.repository.DeathRegistrationRepository;
import com.abm.mainet.bnd.repository.DeathRegistrationTempRepository;
import com.abm.mainet.bnd.ui.model.DeathRegistrationModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
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
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
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
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.bnd.service.IDeathRegistrationService")
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
@Api(value = "/deathRegistrationService")
@Path(value = "/deathRegistrationService")
public class DeathRegistrationService implements IDeathRegistrationService {
	private static final Logger logger = Logger.getLogger(DeathRegistrationService.class);

	@Autowired
	private DeathRegistrationRepository deathRegistrationRepository;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private CFCApplicationAddressRepository cFCApplicationAddressRepository;

	@Resource
	private CfcInterfaceJpaRepository cfcInterfaceJpaRepository;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private DeathRegHistoryrepository deathRegHistoryrepository;
	
	@Autowired
	private GroupMasterService groupMasterService;
	
	@Autowired
	private IDeathRegDraftDao iDeathRegDraftDao;
	
	@Autowired
	private DeathRegDraftRepository deathRegDraftRepository;
	
	@Autowired
	private BirthDeathCfcInterfaceRepository  birthDeathCfcInterfaceRepository;
	
	@Autowired
	private IDeathRegCorrDao DeathRegCorrDaoImpl;
	
	@Autowired
	private IDeathRegCorrDao iDeathRegCorrDao;
	
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
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private DeathRegistrationTempRepository deathRegistrationTempRepository;
	
	@Autowired
	private IRtsService irtsService;
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathregDTO saveDeathRegDraft(TbDeathregDTO tbDeathregDTO) {
		final RequestDTO requestDTO = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DR, tbDeathregDTO.getOrgId());

		TbDeathreg tbDeathreg = new TbDeathreg();
		MedicalMaster medicalMaster = new MedicalMaster();
		DeceasedMaster deceasedMaster = new DeceasedMaster();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		TbDeathRegdraft tbDeathRegdraft = new TbDeathRegdraft();
		
		
		// medical master entry
		BeanUtils.copyProperties(tbDeathregDTO.getMedicalMasterDto(), medicalMaster);
		tbDeathreg.setMedicalMaster(medicalMaster);
		
		// deceased master entry
		BeanUtils.copyProperties(tbDeathregDTO.getDeceasedMasterDTO(), deceasedMaster);
		tbDeathreg.setDeceasedMaster(deceasedMaster);

		////death registration draft Entry
		BeanUtils.copyProperties(tbDeathregDTO, tbDeathreg);
		tbDeathreg.setOrgId(tbDeathregDTO.getOrgId());
		tbDeathreg.setHiId(tbDeathregDTO.getHiId());
		tbDeathreg.setDrRegdate(tbDeathregDTO.getDrRegdate());
		tbDeathreg.setUserId(tbDeathregDTO.getUserId());
		tbDeathreg.setRegAplDate(new Date());
		tbDeathreg.setCpdAgeperiodId(tbDeathregDTO.getCpdAgeperiodId());
		
		BeanUtils.copyProperties(tbDeathreg, tbDeathRegdraft);			
		tbDeathRegdraft.setOrgId(tbDeathregDTO.getOrgId());	
		//medical entry into draft
		tbDeathRegdraft.setMcDeathcause(medicalMaster.getMcDeathcause());
		tbDeathRegdraft.setMcDeathManner(medicalMaster.getMcDeathManner());
		tbDeathRegdraft.setMcDelivery(medicalMaster.getMcDelivery());
		tbDeathRegdraft.setMcInteronset(medicalMaster.getMcInteronset());
		tbDeathRegdraft.setMcMdattndName(medicalMaster.getMcMdattndName());
		tbDeathRegdraft.setMcMdSuprName(medicalMaster.getMcMdSuprName());
		tbDeathRegdraft.setMcOthercond(medicalMaster.getMcOthercond());
		tbDeathRegdraft.setMcPregnAssoc(medicalMaster.getMcPregnAssoc());
		tbDeathRegdraft.setMcVerifnDate(medicalMaster.getMcVerifnDate());
	
		//deceasedMaster entry into draft
		tbDeathRegdraft.setDecAlcoholic(deceasedMaster.getDecAlcoholic());
		tbDeathRegdraft.setDecChewarac(deceasedMaster.getDecChewarac());
		tbDeathRegdraft.setDecChewtb(deceasedMaster.getDecChewtb());
		tbDeathRegdraft.setDecSmoker(deceasedMaster.getDecSmoker());
		tbDeathRegdraft.setDrSex(tbDeathreg.getDrSex());
		tbDeathRegdraft.setSmServiceId(serviceMas.getSmServiceId());
		tbDeathRegdraft.setMcDeathcause(medicalMaster.getMcDeathcause());
		tbDeathRegdraft.setDrStatus("D");
		tbDeathRegdraft.setDrDraftId(tbDeathregDTO.getDrDraftId());
		tbDeathRegdraft.setDrMarMotherName(tbDeathregDTO.getDrMarMotherName());
		tbDeathRegdraft.setCeName(tbDeathregDTO.getCeName());
		tbDeathRegdraft.setCeNameMar(tbDeathregDTO.getCeNameMar());
		tbDeathRegdraft.setCeAddr(tbDeathregDTO.getCeAddr());
		tbDeathRegdraft.setMedCert(medicalMaster.getMedCert());
		tbDeathRegdraft.setCeAddrMar(tbDeathregDTO.getCeAddrMar());
		tbDeathRegdraft.setCpdAgeperiodId(tbDeathregDTO.getCpdAgeperiodId());
		deathRegDraftRepository.save(tbDeathRegdraft);
		
		// RequestDTO entry
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		requestDTO.setLangId(Long.valueOf(tbDeathregDTO.getLangId()));
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode("F");
		if(tbDeathregDTO.getDrInformantName()!=null) {
		requestDTO.setfName(tbDeathregDTO.getDrInformantName());
		}else{
		requestDTO.setfName("NA");
		}
		requestDTO.setfName(tbDeathregDTO.getDrInformantName());
		if (serviceMas.getSmFeesSchedule() == 0) {
			requestDTO.setPayStatus("F");
		} else {
			requestDTO.setPayStatus("Y");
		}
		requestDTO.setUserId(tbDeathregDTO.getUserId());
		requestDTO.setDeptId(tbDeathregDTO.getDeptId());
		
		if(tbDeathregDTO.getApmApplicationId()!=null) {
			Long aplId = tbDeathregDTO.getApmApplicationId();
			requestDTO.setReferenceId(String.valueOf(aplId));
			requestDTO.setApplicationId(aplId);
			List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);
			if (birth != null) {
				birth.forEach(entity -> {
					BeanUtils.copyProperties(entity, birthDeathCFCInterface);
					birthDeathCFCInterface.setBdRequestId(tbDeathRegdraft.getDrDraftId());
					birthDeathCFCInterface.setLmodDate(new Date());
					cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
				});
			  }
		}
		else if((tbDeathregDTO.getApmApplicationId()==null)||(tbDeathregDTO.getApmApplicationId().equals("0"))){
		final Long applicationId = applicationService.createApplication(requestDTO);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		requestDTO.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId);
		tbDeathregDTO.setApplicationId(applicationId);
		if ((applicationId != null) && (applicationId != 0)) {
			tbDeathregDTO.setApmApplicationId(applicationId);
		}
		
		// death interface entry
		BeanUtils.copyProperties(tbDeathregDTO, birthDeathCFCInterface);
		//birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(tbDeathRegdraft.getDrDraftId());
		birthDeathCFCInterface.setLmodDate(new Date());
		birthDeathCFCInterface.setOrgId(tbDeathreg.getOrgId());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		}
	if ((tbDeathregDTO.getDocumentList() != null) && !tbDeathregDTO.getDocumentList().isEmpty()) {
			boolean status = fileUploadService.doFileUpload(tbDeathregDTO.getDocumentList(), requestDTO);
		}
		
		return tbDeathregDTO;
	}

	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public Map<String,Object> saveDeathRegDet(TbDeathregDTO tbDeathregDTO,DeathRegistrationModel tbDeathregModel) {

		final RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy=UserSession.getCurrent().getEmployee().getEmpId();
		try {
  			if( tbDeathregModel.getOfflineDTO().getPayModeIn()!=null) {
  			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(tbDeathregModel.getOfflineDTO().getPayModeIn());
  			if (lookup != null && lookup.getLookUpCode().equals("POS")) {
  				CommonChallanDTO dto=irtsService.createPushToPayApiRequest(tbDeathregModel.getOfflineDTO(), createdBy, orgId,"IBC" , tbDeathregModel.getOfflineDTO().getAmountToPay());
  				if(dto!=null && dto.getPushToPayErrMsg()!=null) {
  					tbDeathregModel.addValidationError(dto.getPushToPayErrMsg());
  					return null;
  				}
  			}}
  		} catch (Exception e) {
  			// TODO: handle exception
  		}
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DR, tbDeathregDTO.getOrgId());
	
		TbDeathreg tbDeathreg = new TbDeathreg();
		MedicalMaster medicalMaster = new MedicalMaster();
		DeceasedMaster deceasedMaster = new DeceasedMaster();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		MedicalMasterHistory medicalMasterHistory = new MedicalMasterHistory();
		DeceasedMasterHistory deceasedMasterHistory=new DeceasedMasterHistory();
		TbDeathRegHistory tbDeathRegHistory= new TbDeathRegHistory();
		TbCfcApplicationMstEntity tbCfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		
		// medical master entry
		BeanUtils.copyProperties(tbDeathregDTO.getMedicalMasterDto(), medicalMaster);
		medicalMaster.setOrgId(tbDeathregDTO.getOrgId());
		medicalMaster.setDrId(tbDeathreg);
		medicalMaster.setLmoddate(new Date());
		medicalMaster.setUserId(tbDeathregDTO.getUserId());
		medicalMaster.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		medicalMaster.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
		if(tbDeathregDTO.getCpdDeathcauseId()!=null)
		medicalMaster.setMcDeathcause(tbDeathregDTO.getCpdDeathcauseId().toString());
		if(medicalMaster.getMedCert() == null) {
			medicalMaster.setMedCert("N");	
		}
		if(medicalMaster.getMcPregnAssoc() == null) {
			medicalMaster.setMcPregnAssoc("N");	
		}
		tbDeathreg.setMedicalMaster(medicalMaster);
		
		// deceased master entry
		BeanUtils.copyProperties(tbDeathregDTO.getDeceasedMasterDTO(), deceasedMaster);
		deceasedMaster.setOrgId(tbDeathregDTO.getOrgId());
		deceasedMaster.setDrId(tbDeathreg);
		deceasedMaster.setUserId(tbDeathregDTO.getUserId());
		deceasedMaster.setLmoddate(new Date());
		
		if(deceasedMaster.getDecAlcoholic() == null) {
			deceasedMaster.setDecAlcoholic("N");	
		}
		if(deceasedMaster.getDecChewarac() == null) {
			deceasedMaster.setDecChewarac("N");	
		}
		if(deceasedMaster.getDecChewtb() == null) {
			deceasedMaster.setDecChewtb("N");	
		}
		if(deceasedMaster.getDecSmoker() == null) {
			deceasedMaster.setDecSmoker("N");	
		}
		
		deceasedMaster.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		deceasedMaster.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
		tbDeathreg.setDeceasedMaster(deceasedMaster);
		

		// Death Registration Entry
		BeanUtils.copyProperties(tbDeathregDTO, tbDeathreg);
		tbDeathreg.setOrgId(tbDeathregDTO.getOrgId());
		tbDeathreg.setHiId(tbDeathregDTO.getHiId());
		tbDeathreg.setDrRegdate(tbDeathregDTO.getDrRegdate());
		tbDeathreg.setUserId(tbDeathregDTO.getUserId());
		tbDeathreg.setRegAplDate(new Date());
		tbDeathreg.setCpdAgeperiodId(tbDeathregDTO.getCpdAgeperiodId());
		tbDeathreg.setDrStatus("O");
		tbDeathreg.setDrCertNo("0");
        tbDeathreg.setDrFlag("C");
		tbDeathreg.setDrRelPreg("N");
		tbDeathreg.setDrCorrectionFlg("N");
		tbDeathreg.setDeathWFStatus("OPEN");
		tbDeathreg.setDrUdeathReg("N");
		tbDeathreg.setBcrFlag("N");
		tbDeathreg.setCertNoCopies(0L);
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),tbDeathreg.getOrgId());
		if (processName != null) {
			tbDeathreg.setDrStatus(BndConstants.STATUS);
			tbDeathreg.setDeathWFStatus(BndConstants.OPEN);
		}else {
			tbDeathreg.setDrStatus(BndConstants.DEATH_STATUS_APPROVED);
			tbDeathreg.setDeathWFStatus(BndConstants.APPROVED);
		}
		deathRegistrationRepository.save(tbDeathreg);

		// medical master History entry
		BeanUtils.copyProperties(medicalMaster, medicalMasterHistory);
		medicalMasterHistory.setLmodDate(new Date());
		medicalMasterHistory.setAction(BndConstants.DR);
		tbDeathRegHistory.setMedicalMasterHistory(medicalMasterHistory);
		
		// deceased master History entry
		BeanUtils.copyProperties(deceasedMaster, deceasedMasterHistory);
		deceasedMasterHistory.setLmodDate(new Date());
		deceasedMasterHistory.setAction(BndConstants.DR);
		tbDeathRegHistory.setDeceasedMasterHistory(deceasedMasterHistory);
		
		// Death Registration History Entry
		BeanUtils.copyProperties(tbDeathreg, tbDeathRegHistory);
		tbDeathRegHistory.setLmoddate(new Date());
		tbDeathRegHistory.setAction(BndConstants.DR);
		tbDeathRegHistory.setDrStatus("O");
		deathRegHistoryrepository.save(tbDeathRegHistory);

		// RequestDTO entry
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode("F");
		requestDTO.setLangId(Long.valueOf(tbDeathregDTO.getLangId()));
		  if(serviceMas.getSmFeesSchedule()==0)
			{
			  requestDTO.setPayStatus("F");
			}else {
				requestDTO.setPayStatus("Y");
			}
		requestDTO.setUserId(tbDeathregDTO.getUserId());
		requestDTO.setDeptId(tbDeathregDTO.getDeptId());
		requestDTO.setfName(tbDeathregDTO.getDrInformantName());

        if(tbDeathregDTO.getApmApplicationId()!=null) {
    		Long aplId = tbDeathregDTO.getApmApplicationId();
    		requestDTO.setReferenceId(String.valueOf(aplId));
    		requestDTO.setApplicationId(aplId);
    		
    		List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);
    		
    		if (birth != null) {
    			birth.forEach(entity -> {
    				//BirthDeathCFCInterfaceDTO dto = new BirthDeathCFCInterfaceDTO();
    				BeanUtils.copyProperties(entity, birthDeathCFCInterface);
    				birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
    				birthDeathCFCInterface.setLmodDate(new Date());
    				birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
    				cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
    			});
    		}
		}
		
		else if((tbDeathregDTO.getApmApplicationId()==null)||(tbDeathregDTO.getApmApplicationId().equals("0"))) {
		final Long applicationId = applicationService.createApplication(requestDTO);
		if (null == applicationId) {
			throw new RuntimeException("Application Not Generated");
		}
		requestDTO.setReferenceId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId);
		tbDeathregDTO.setApplicationId(applicationId);
		tbDeathregDTO.setDrId(tbDeathreg.getDrId());
		if ((applicationId != null) && (applicationId != 0)) {
			tbDeathregDTO.setApmApplicationId(applicationId);
		}
		// death interface entry
		BeanUtils.copyProperties(tbDeathregDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
		birthDeathCFCInterface.setLmodDate(new Date());
		tbDeathreg.getMedicalMaster().setDrId(tbDeathreg);
		tbDeathreg.getDeceasedMaster().setDrId(tbDeathreg);
		tbDeathRegHistory.getMedicalMasterHistory().setDrHiId(tbDeathRegHistory.getDrHiId());
		tbDeathRegHistory.getDeceasedMasterHistory().setDrHiId(tbDeathRegHistory.getDrHiId());
		birthDeathCFCInterface.setOrgId(tbDeathreg.getOrgId());
		birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
		cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
         }
        
        //RM-38294
		if ((tbDeathregDTO.getApmApplicationId() != null) && (tbDeathregDTO.getApmApplicationId() != 0)) {
			Long lang = Long.valueOf(tbDeathregDTO.getLangId());
			Organisation org = UserSession.getCurrent().getOrganisation();
			tbCfcApplicationMstEntity.setApmFname(tbDeathregDTO.getDrInformantName());
			tbCfcApplicationMstEntity.setApmApplicationId(tbDeathregDTO.getApmApplicationId());
			tbCfcApplicationMstEntity.setApmSex(tbDeathregDTO.getDrSex());
			tbCfcApplicationMstEntity.setApmApplicationDate(tbDeathregDTO.getDrRegdate());
			tbCfcApplicationMstEntity.setUserId(tbDeathregDTO.getUserId());
			tbCfcApplicationMstEntity.setLangId(lang);
			tbCfcApplicationMstEntity.setLmoddate(new Date());
			tbCfcApplicationMstEntity.setApmChklstVrfyFlag("P");
			tbCfcApplicationMstEntity.setApmPayStatFlag(requestDTO.getPayStatus());
			tbCfcApplicationMstEntity.setTbOrganisation(org);
			tbCfcApplicationMstEntity.setTbServicesMst(serviceMas);
			tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		}
		//RM-38294
        
		if ((tbDeathregDTO.getDocumentList() != null) && !tbDeathregDTO.getDocumentList().isEmpty()) {
			boolean status = fileUploadService.doFileUpload(tbDeathregDTO.getDocumentList(), requestDTO);
		}
		
		  Map<String,Object> map=new HashMap<String,Object>();
		  map.put("ApplicationId",tbDeathregDTO.getApmApplicationId());
		  if(serviceMas.getSmFeesSchedule()==0 || tbDeathregDTO.getAmount()==0.0)
			{
		  initializeWorkFlowForFreeService(tbDeathregDTO,serviceMas);
			}else {
		     setAndSaveChallanDtoOffLine(tbDeathregModel.getOfflineDTO(), tbDeathregModel);
			}
		return map;

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

	public DeathRegistrationRepository getDeathRegistrationRepository() {
		return deathRegistrationRepository;
	}

	public void setDeathRegistrationRepository(DeathRegistrationRepository deathRegistrationRepository) {
		this.deathRegistrationRepository = deathRegistrationRepository;
	}

	@Override
	@WebMethod(exclude = true)
	public List<TbDeathregDTO> getAllDeathReg(Long orgId) {
		return null;
	}

	@Override
	@WebMethod(exclude = true)
	public TbDeathregDTO getDeathRegApplnDetails(String certno, Long regNo, String year) {
		return null;
	}

	@Override
	@WebMethod(exclude = true)
	public TbDeathregDTO getDeathRegById(Long drId) {
		// TODO Auto-generated method stubDeathRegistration.html
		return null;
	}

	@Override
	@WebMethod(exclude = true)
	public void deleteDeathRegById(Long drId) {
		// TODO Auto-generated method stub
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
	public String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@WebMethod(exclude = true)
	public void updateDeathApproveStatus(TbDeathregDTO tbDeathregDTO, String status, String lastDecision){
		logger.info("Start updateDeathApproveStatus");
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		ServiceMaster service=new ServiceMaster();
		TbDeathreg TbDeathregEntity=new TbDeathreg();
		TbDeathRegHistory deathRegHistory=new TbDeathRegHistory();
		cfcApplEntiry.setApmApplicationId(tbDeathregDTO.getApplicationId());
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(tbDeathregDTO.getApplicationId(), tbDeathregDTO.getOrgId());
	    BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
	    cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setApmApplicationDate(tbDeathregDTO.getDrRegdate());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
		service.setSmServiceId(tbDeathregDTO.getServiceId());
	    cfcApplEntiry.setUpdatedBy(tbDeathregDTO.getUserId());
	    cfcApplEntiry.setTbServicesMst(service);
		TbDeathregEntity.setDrId(tbDeathregDTO.getDrId());
		TbDeathregEntity.setUpdatedBy(tbDeathregDTO.getUserId());
	    TbDeathregEntity.setUpdatedDate(new Date());
	    TbDeathregEntity.setLmoddate(new Date());
	    
	    LookUp lokkup = null;
		if (tbDeathregDTO.getDrSex() != null) {
			lokkup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(tbDeathregDTO.getDrSex(), GENDER, 1); 
		}
		
		if(lastDecision.equals(BndConstants.REJECTED)){
			logger.info("rejected status updateDeathApproveStatus");
		    cfcApplEntiry.setApmAppRejFlag("R");
		    cfcApplEntiry.setAppAppRejBy(tbDeathregDTO.getServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag("C");
		    deathRegHistory.setDrStatus("C");  
		    deathRegHistory.setDeathWFStatus(BndConstants.REJECTED);
		    
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.PENDING)){
			logger.info("pending status updateDeathApproveStatus");
			cfcApplEntiry.setApmApplSuccessFlag("P");
			cfcApplEntiry.setApmApprovedBy(tbDeathregDTO.getServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			deathRegHistory.setDrStatus("I");	
			deathRegHistory.setDeathWFStatus(BndConstants.PENDING);
			
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.CLOSED)){
			logger.info("closed status updateDeathApproveStatus");
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setApmApprovedBy(tbDeathregDTO.getServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(tbDeathregDTO.getRegAplDate());
			deathRegHistory.setDrStatus("C");
			deathRegHistory.setDeathWFStatus(BndConstants.APPROVED);
		}
		logger.info("Middle updateDeathApproveStatus(after all condition checked)");
	 	BeanUtils.copyProperties(tbDeathregDTO, deathRegHistory);
		//BeanUtils.copyProperties(tbDeathregDTO, TbDeathregEntity);
	 	deathRegHistory.setDrRegdate(tbDeathregDTO.getDrRegdate());
		BeanUtils.copyProperties(tbDeathregDTO.getMedicalMasterDto(), deathRegHistory.getMedicalMasterHistory());
		BeanUtils.copyProperties(tbDeathregDTO.getDeceasedMasterDTO(), deathRegHistory.getDeceasedMasterHistory());
		 deathRegHistory.setDrSex(String.valueOf(lokkup.getLookUpId()));
		 deathRegHistory.getMedicalMasterHistory().setLmodDate(new Date());
		 deathRegHistory.getDeceasedMasterHistory().setLmodDate(new Date());
		 TbDeathregEntity.getDeceasedMaster().setLmoddate(new Date());
		 TbDeathregEntity.getMedicalMaster().setLmoddate(new Date());
		 tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
		 deathRegHistoryrepository.save(deathRegHistory);
		//deathRegistrationRepository.save(TbDeathregEntity);
		 logger.info("End updateDeathApproveStatus");
 }

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updateDeathWorkFlowStatus(Long drId, String taskNamePrevious, Long orgId,String drStatus) {
		logger.info("start updateDeathWorkFlowStatus");
		deathRegistrationRepository.updateWorkFlowStatus(drId, orgId, taskNamePrevious,drStatus);
		logger.info("End updateDeathWorkFlowStatus");
	}

	    @Override
	    @Transactional
	    @WebMethod(exclude = true)
	    public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction) {
	    	logger.info("start updateWorkFlowDeathService");
	        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
	        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
	        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
	        try {
	        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
	        } catch (Exception exception) {
	        	logger.error("Exception  Occured when Update Workflow methods",exception);
	        	throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
	       
	        }
	        logger.info("End updateWorkFlowDeathService");
	        return null;
	    }

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Boolean checkEmployeeRole(UserSession ses){		
	 @SuppressWarnings("deprecation")
	 LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(BndConstants.ROLE_CODE,BndConstants.DESIGN_PRIFIX,1);
	 GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),ses.getOrganisation().getOrgid());
	 boolean checkFinalAproval=false;
	    if(lookup.getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
	    	checkFinalAproval=true;
	    }
	  return checkFinalAproval;
 }

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathRegdraftDto> getDeathRegisteredAppliDetail(@QueryParam("applnId")Long applnId, @QueryParam("drDod")Date drDod, @QueryParam("orgId")Long orgId) {
      		List<TbDeathRegdraft> tbDeathreg = iDeathRegDraftDao.getDeathRegisteredAppliDetail(applnId,
				drDod, orgId);
		List<TbDeathRegdraftDto> listDTO = new ArrayList<TbDeathRegdraftDto>();
		if ((tbDeathreg != null) && !tbDeathreg.isEmpty()) {
			tbDeathreg.forEach(entity -> {
				TbDeathRegdraftDto dto = new TbDeathRegdraftDto();
				BeanUtils.copyProperties(entity, dto);
				LookUp lokkup = null;
				if (entity.getDrSex() != null && !entity.getDrSex().equals("0")) {
					lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()),
							orgId, GENDER);
					dto.setDrSex(lokkup.getLookUpDesc());	
				}
				//BeanUtils.copyProperties(entity, dto);
				listDTO.add(dto);
			});
		}

		return listDTO;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public List<TbDeathRegdraftDto> getAllDeathRegdraft(Long orgId) {
		List<TbDeathRegdraft> listTbDeathRegdraft=iDeathRegDraftDao.getDraftDetail(orgId) ;
		List<TbDeathRegdraftDto> listTbDeathRegdraftDto = new ArrayList<TbDeathRegdraftDto>();
		if (listTbDeathRegdraft != null) {
		listTbDeathRegdraft.forEach(entity ->{
			TbDeathRegdraftDto dto= new TbDeathRegdraftDto();
			BeanUtils.copyProperties(entity, dto);
			LookUp lokkup = null;
			if (!entity.getDrSex().equals("0")) {
				lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(entity.getDrSex()),
						orgId, GENDER);
				dto.setDrSex(lokkup.getLookUpDesc());
			}
			
			
			listTbDeathRegdraftDto.add(dto);
		});
		}
		return listTbDeathRegdraftDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathRegdraftDto getDeathById(Long drDraftId) {
		TbDeathRegdraft tbDeathRegdraft = deathRegDraftRepository.findOne(drDraftId);
		TbDeathRegdraftDto tbDeathRegdraftDto = new TbDeathRegdraftDto();
		BeanUtils.copyProperties(tbDeathRegdraft, tbDeathRegdraftDto);
		tbDeathRegdraftDto.setDrDraftId(tbDeathRegdraft.getDrDraftId());
    	return tbDeathRegdraftDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathregDTO getTbDeathregDTOFromDraftDTO(TbDeathRegdraftDto tbDeathRegdraftDto) {
		TbDeathregDTO tbDeathRegDto = new TbDeathregDTO();
		MedicalMasterDTO medicalMasterDTO = new MedicalMasterDTO();
		DeceasedMasterDTO deceasedMasterDTO= new DeceasedMasterDTO();
		
		//medical entry from draft reg
		medicalMasterDTO.setMcDeathManner(tbDeathRegdraftDto.getMcDeathManner());
		medicalMasterDTO.setMcDelivery(tbDeathRegdraftDto.getMcDelivery());
		medicalMasterDTO.setMcInteronset(tbDeathRegdraftDto.getMcInteronset());
		medicalMasterDTO.setMcMdattndName(tbDeathRegdraftDto.getMcMdattndName());
		medicalMasterDTO.setMcMdSuprName(tbDeathRegdraftDto.getMcMdSuprName());
		medicalMasterDTO.setMcOthercond(tbDeathRegdraftDto.getMcOthercond());
		medicalMasterDTO.setMcPregnAssoc(tbDeathRegdraftDto.getMcPregnAssoc());
		medicalMasterDTO.setMcVerifnDate(tbDeathRegdraftDto.getMcVerifnDate());
		medicalMasterDTO.setMedCert(tbDeathRegdraftDto.getMedCert());
		tbDeathRegDto.setMedicalMasterDto(medicalMasterDTO);
		
		//deceased entry from draft reg
		deceasedMasterDTO.setDecAlcoholic(tbDeathRegdraftDto.getDecAlcoholic());
		deceasedMasterDTO.setDecChewarac(tbDeathRegdraftDto.getDecChewarac());
		deceasedMasterDTO.setDecChewtb(tbDeathRegdraftDto.getDecChewtb());
		deceasedMasterDTO.setDecSmoker(tbDeathRegdraftDto.getDecSmoker());
		tbDeathRegDto.setDeceasedMasterDTO(deceasedMasterDTO);
		
		BeanUtils.copyProperties(tbDeathRegdraftDto, tbDeathRegDto);
		tbDeathRegDto.setCpdAgeperiodId(tbDeathRegdraftDto.getCpdAgeperiodId());
		return tbDeathRegDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathregDTO saveDeathRegDetOnApproval(TbDeathregDTO tbDeathregDTO) {
		
		TbDeathreg tbDeathreg = new TbDeathreg();
		TbDeathRegHistory tbDeathRegHistory = new TbDeathRegHistory();

		// Death Registration Entry
		BeanUtils.copyProperties(tbDeathregDTO, tbDeathreg);
		
		List<TbBdDeathregCorr> tbDeathregcorr = iDeathRegCorrDao.getDeathRegisteredAppliDetailFromApplnId(tbDeathregDTO.getApmApplicationId(), tbDeathregDTO.getOrgId());
		List<BirthDeathCFCInterface> birthDeathlist = birthDeathCfcInterfaceRepository.findData(tbDeathregDTO.getApmApplicationId());
		List<TbDeathreg> tbDeathreglist = deathRegistrationRepository.findData(tbDeathregcorr.get(0).getDrId());
		if(tbDeathregcorr.get(0).getDrSex()!=null && !(tbDeathregcorr.get(0).getDrSex().isEmpty())) {
			tbDeathreg.setDrSex(tbDeathregcorr.get(0).getDrSex());
		}
		if(tbDeathregcorr.get(0).getDrStatus()!=null && !(tbDeathregcorr.get(0).getDrStatus().isEmpty())) {
			tbDeathreg.setDrStatus(tbDeathregcorr.get(0).getDrStatus());
		}
		if(tbDeathregcorr.get(0).getDrDcaddrAtdeath()!=null && !(tbDeathregcorr.get(0).getDrDcaddrAtdeath().isEmpty())) {
			tbDeathreg.setDrDcaddrAtdeath(tbDeathregcorr.get(0).getDrDcaddrAtdeath());
		}		
		if(tbDeathregcorr.get(0).getDrDcaddrAtdeathMar()!=null && !(tbDeathregcorr.get(0).getDrDcaddrAtdeathMar().isEmpty())) {
			tbDeathreg.setDrDcaddrAtdeathMar(tbDeathregcorr.get(0).getDrDcaddrAtdeathMar());
		}	
		if(tbDeathregcorr.get(0).getDrDeceasedaddr()!=null && !(tbDeathregcorr.get(0).getDrDeceasedaddr().isEmpty())) {
			tbDeathreg.setDrDeceasedaddr(tbDeathregcorr.get(0).getDrDeceasedaddr());
		}		
		if(tbDeathregcorr.get(0).getDrMarDeceasedaddr()!=null && !(tbDeathregcorr.get(0).getDrMarDeceasedaddr().isEmpty())) {
			tbDeathreg.setDrMarDeceasedaddr(tbDeathregcorr.get(0).getDrMarDeceasedaddr());
		}		
		if(tbDeathregcorr.get(0).getDrDeceasedname()!=null && !(tbDeathregcorr.get(0).getDrDeceasedname().isEmpty())) {
			tbDeathreg.setDrDeceasedname(tbDeathregcorr.get(0).getDrDeceasedname());
		}		
		if(tbDeathregcorr.get(0).getDrMarDeceasedname()!=null && !(tbDeathregcorr.get(0).getDrMarDeceasedname().isEmpty())) {
			tbDeathreg.setDrMarDeceasedname(tbDeathregcorr.get(0).getDrMarDeceasedname());
		}
		if(tbDeathregcorr.get(0).getDrDeathplace()!=null && !(tbDeathregcorr.get(0).getDrDeathplace().isEmpty())) {
			tbDeathreg.setDrDeathplace(tbDeathregcorr.get(0).getDrDeathplace());
		}
		if(tbDeathregcorr.get(0).getDrMarDeathplace()!=null && !(tbDeathregcorr.get(0).getDrMarDeathplace().isEmpty())) {
			tbDeathreg.setDrMarDeathplace(tbDeathregcorr.get(0).getDrMarDeathplace());
		}
		if(tbDeathregcorr.get(0).getDrMotherName()!=null && !(tbDeathregcorr.get(0).getDrMotherName().isEmpty())) {
			tbDeathreg.setDrMotherName(tbDeathregcorr.get(0).getDrMotherName());
		}
		if(tbDeathregcorr.get(0).getDrMarMotherName()!=null && !(tbDeathregcorr.get(0).getDrMarMotherName().isEmpty())) {
			tbDeathreg.setDrMarMotherName(tbDeathregcorr.get(0).getDrMarMotherName());
		}
		if(tbDeathregcorr.get(0).getDrMarRelativeName()!=null && !(tbDeathregcorr.get(0).getDrMarRelativeName().isEmpty())) {
			tbDeathreg.setDrMarRelativeName(tbDeathregcorr.get(0).getDrMarRelativeName());
		}
		if(tbDeathregcorr.get(0).getDrRelativeName()!=null && !(tbDeathregcorr.get(0).getDrRelativeName().isEmpty())) {
			tbDeathreg.setDrRelativeName(tbDeathregcorr.get(0).getDrRelativeName());
		}
		if(tbDeathregcorr.get(0).getDrDeathaddr()!=null && !(tbDeathregcorr.get(0).getDrDeathaddr().isEmpty())) {
			tbDeathreg.setDrDeathaddr(tbDeathregcorr.get(0).getDrDeathaddr());
		}
		if(tbDeathregcorr.get(0).getDrMarDeathaddr()!=null && !(tbDeathregcorr.get(0).getDrMarDeathaddr().isEmpty())) {
			tbDeathreg.setDrMarDeathaddr(tbDeathregcorr.get(0).getDrMarDeathaddr());
		}
		
		if(tbDeathregcorr.get(0).getDrInformantName()!=null && !(tbDeathregcorr.get(0).getDrInformantName().isEmpty())) {
			tbDeathreg.setDrInformantName(tbDeathregcorr.get(0).getDrInformantName());
		}
		if(tbDeathregcorr.get(0).getDrInformantAddr()!=null && !(tbDeathregcorr.get(0).getDrInformantAddr().isEmpty())) {
			tbDeathreg.setDrInformantAddr(tbDeathregcorr.get(0).getDrInformantAddr());
		}
		
		if(tbDeathregcorr.get(0).getDrInformantName()!=null && !(tbDeathregcorr.get(0).getDrInformantName().isEmpty())) {
			tbDeathreg.setDrInformantName(tbDeathregcorr.get(0).getDrInformantName());
		}
		if(tbDeathregcorr.get(0).getDrInformantAddr()!=null && !(tbDeathregcorr.get(0).getDrInformantAddr().isEmpty())) {
			tbDeathreg.setDrInformantAddr(tbDeathregcorr.get(0).getDrInformantAddr());
		}
		if(tbDeathregcorr.get(0).getDrRemarks()!=null && !(tbDeathregcorr.get(0).getDrRemarks().isEmpty())) {
			tbDeathreg.setDrRemarks(tbDeathregcorr.get(0).getDrRemarks());
		}

		if (tbDeathregcorr.get(0).getCpdMaritalStatId() != null) {
			tbDeathreg.setCpdMaritalStatId(tbDeathregcorr.get(0).getCpdMaritalStatId());
		}
		else {
			tbDeathreg.setCpdMaritalStatId(tbDeathreglist.get(0).getCpdMaritalStatId());
		}
		if (tbDeathregcorr.get(0).getCpdReligionId() != null) {
			tbDeathreg.setCpdReligionId(tbDeathregcorr.get(0).getCpdReligionId());
		}
		if (tbDeathregcorr.get(0).getCpdOccupationId() != null) {
			tbDeathreg.setCpdOccupationId(tbDeathregcorr.get(0).getCpdOccupationId());
		}
		if (tbDeathregcorr.get(0).getCpdDeathcauseId() != null) {
			tbDeathreg.setCpdDeathcauseId(tbDeathregcorr.get(0).getCpdDeathcauseId());
		}
		if (tbDeathregcorr.get(0).getCpdAgeperiodId() != null) {
			tbDeathreg.setCpdAgeperiodId(tbDeathregcorr.get(0).getCpdAgeperiodId());
		}
		else {
			tbDeathreg.setCpdAgeperiodId(tbDeathregDTO.getCpdAgeperiodId());
		}
		if (tbDeathregcorr.get(0).getDrDeceasedage() != null) {
			tbDeathreg.setDrDeceasedage(tbDeathregcorr.get(0).getDrDeceasedage());
		}
		if (tbDeathregcorr.get(0).getWardid() != null) {
			tbDeathreg.setWardid(tbDeathregcorr.get(0).getWardid());
		}
		if (tbDeathregcorr.get(0).getPdRegUnitId() != null)
		tbDeathreg.setCpdRegUnit(tbDeathregcorr.get(0).getPdRegUnitId());
		tbDeathreg.setDrDod(tbDeathregcorr.get(0).getDrDod());
		tbDeathreg.setOrgId(tbDeathregDTO.getOrgId());
		tbDeathreg.setHiId(tbDeathregDTO.getHiId());
		//tbDeathreg.setDrRegdate(tbDeathregDTO.getDrDod());
		tbDeathreg.setUserId(tbDeathregDTO.getUserId());
		tbDeathreg.setRegAplDate(new Date());
		tbDeathreg.setDrStatus("Y");
		tbDeathreg.setDeathWFStatus(BndConstants.APPROVED);
		tbDeathreg.setDrCorrectionFlg("Y");
		tbDeathreg.setDrCorrnDate(new Date());
	
		// medical master entry
		List<TbDeathreg> tbDeathregRecord = DeathRegCorrDaoImpl.getDeathRegisteredCorrAppliDetail(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId());
		tbDeathreg.setMedicalMaster(null);
		
		// deceased master entry
		tbDeathreg.setDeceasedMaster(null);
		
		if(tbDeathregRecord.get(0).getCertNoCopies()!=null) {
		tbDeathreg.setCertNoCopies(tbDeathregRecord.get(0).getCertNoCopies());
		}
		else {
			tbDeathreg.setCertNoCopies(0L);
		}
		deathRegistrationRepository.save(tbDeathreg);

		
		tbDeathRegHistory.setMedicalMasterHistory(null);//defect id 96370
		
		tbDeathRegHistory.setDeceasedMasterHistory(null);//defect id 96370
		
		// Death Registration History Entry
		BeanUtils.copyProperties(tbDeathreg, tbDeathRegHistory);
		tbDeathRegHistory.setLmoddate(new Date());
		tbDeathRegHistory.setAction(BndConstants.DR);
		tbDeathRegHistory.setDrStatus("A");
		if(tbDeathreg.getDrSex()!=null && !(tbDeathreg.getDrSex().isEmpty())) {
			tbDeathRegHistory.setDrSex(tbDeathreg.getDrSex());
		}
		deathRegHistoryrepository.save(tbDeathRegHistory);
		if(!birthDeathlist.isEmpty())
		tbDeathregDTO.setNumberOfCopies(birthDeathlist.get(0).getCopies());
		if(!tbDeathreglist.isEmpty())
		tbDeathregDTO.setAlreayIssuedCopy(tbDeathreglist.get(0).getCertNoCopies());
		return tbDeathregDTO;
		
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updatNoOfcopyStatus(Long brId, Long orgId, Long bdId,Long noOfCopies) {
		/*String status ="P";
		TbBdCertCopy tbBdCertCopy=certCopyRepository.findOne(bdId);
		if(tbBdCertCopy.getStatus().equals("N")) {
		deathRegistrationRepository.updatNoOfcopyStatus(brId, orgId, noOfCopies);
		certCopyRepository.updateStatus(bdId, orgId, status);
		}*/
		deathRegistrationRepository.updatNoOfcopyStatus(brId, orgId, noOfCopies);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String updateDeathRegCorrApprove(TbBdDeathregCorrDTO tbBdDeathregCorrDTO, String lastDecision,
			String status) {
		Long aplId = tbBdDeathregCorrDTO.getApmApplicationId();
		Long drId =tbBdDeathregCorrDTO.getDrId();
		
		List<BirthDeathCFCInterface> birthDeathCFCInterface = birthDeathCfcInterfaceRepository.findData(aplId);
		

		if ((birthDeathCFCInterface.get(0).getCopies() != 0)&& (birthDeathCFCInterface.get(0).getCopies() != null)) {
			
			List<TbDeathreg> tbDeathreg = deathRegistrationRepository.findData(drId);
			
			String certnum=null;
			if((!tbDeathreg.isEmpty())) {
			 certnum = tbDeathreg.get(0).getDrCertNo();
			}
			
			if ((certnum == null)||(certnum.equals("0"))) {
				String finalcertificateNo = null;
				Long alreadyIssuCopy = tbBdDeathregCorrDTO.getAlreayIssuedCopy();
				if (alreadyIssuCopy == null) {
					alreadyIssuCopy = 0L;
				}	
				TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
				SequenceConfigMasterDTO configMasterDTO = null;
		        Long deptId = departmentService.getDepartmentIdByDeptCode(BndConstants.BND, PrefixConstants.STATUS_ACTIVE_PREFIX);
		        configMasterDTO = seqGenFunctionUtility.loadSequenceData(tbBdDeathregCorrDTO.getOrgId(), deptId,
		        		BndConstants.TB_DEATHREG, BndConstants.DR_CERT_NO);
		        if (configMasterDTO.getSeqConfigId() == null) {
					Long certificateNo = seqGenFunctionUtility.generateSequenceNo(BndConstants.BND, BndConstants.TB_BD_CERT_COPY, BndConstants.CERT_NO,
							tbBdDeathregCorrDTO.getOrgId(), "Y", null);
					String financialYear = UserSession.getCurrent().getCurrentDate();
					finalcertificateNo = "HQ/" + financialYear.substring(6, 10) + "/"
							+ new DecimalFormat("000000").format(certificateNo);
		        }else {
		        	CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
		        	finalcertificateNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
		        }
				
					tbBdCertCopy.setOrgid(tbBdDeathregCorrDTO.getOrgId());
					tbBdCertCopy.setDrId(drId);
					tbBdCertCopy.setCopyNo(birthDeathCFCInterface.get(0).getCopies());
					tbBdCertCopy.setUpdatedBy(tbBdDeathregCorrDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(tbBdDeathregCorrDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(tbBdDeathregCorrDTO.getUserId());
					tbBdCertCopy.setLgIpMac(tbBdDeathregCorrDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(tbBdDeathregCorrDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
					deathRegistrationRepository.updateCertNo(drId, tbBdDeathregCorrDTO.getOrgId(), finalcertificateNo);
				return finalcertificateNo;
			}
				else {
				if((certnum!=null) || !certnum.equals("0")){
				String finalcertificateNo = null;
				Long alreadyIssuCopy = null;
				if(!tbDeathreg.isEmpty()) {
					alreadyIssuCopy=tbDeathreg.get(0).getCertNoCopies();
				}else {
					alreadyIssuCopy=0L;
				}
				
					TbBdCertCopy tbBdCertCopy = new TbBdCertCopy();
					finalcertificateNo = certnum;
					tbBdCertCopy.setOrgid(tbBdDeathregCorrDTO.getOrgId());
					tbBdCertCopy.setDrId(drId);
					tbBdCertCopy.setCopyNo(birthDeathCFCInterface.get(0).getCopies());
					tbBdCertCopy.setUpdatedBy(tbBdDeathregCorrDTO.getUserId());
					tbBdCertCopy.setUpdatedDate(tbBdDeathregCorrDTO.getUpdatedDate());
					tbBdCertCopy.setUserId(tbBdDeathregCorrDTO.getUserId());
					tbBdCertCopy.setLgIpMac(tbBdDeathregCorrDTO.getLgIpMac());
					tbBdCertCopy.setLgIpMacUpd(tbBdDeathregCorrDTO.getLgIpMacUpd());
					tbBdCertCopy.setCertNo(finalcertificateNo);
					tbBdCertCopy.setLmoddate(new Date());
					tbBdCertCopy.setStatus("N");
					tbBdCertCopy.setAPMApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
					// saveCertcopy
					birthDeathCertificateCopyRepository.save(tbBdCertCopy);
				return finalcertificateNo;
			 }
				}
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathRegistrationModel tbDeathregModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DR, tbDeathregModel.getTbDeathregDTO().getOrgId());
		offline.setApplNo(tbDeathregModel.getTbDeathregDTO().getApmApplicationId());
		offline.setAmountToPay(tbDeathregModel.getChargesAmount());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		offline.setApplicantName(tbDeathregModel.getTbDeathregDTO().getDrDeceasedname());
		offline.setMobileNumber("NA");
		offline.setEmailId("NA");
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(tbDeathregModel.getTbDeathregDTO().getDrInformantAddr());
		offline.setPosPayApplicable(true);
		if(tbDeathregModel.getChargesInfo()!=null) { 
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
		offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());

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
			printDto.setSubject(printDto.getSubject()+" - "+tbDeathregModel.getTbDeathregDTO().getDrRegno());
			tbDeathregModel.setReceiptDTO(printDto);
		
			tbDeathregModel.setSuccessMessage(tbDeathregModel.getAppSession().getMessage("adh.receipt"));
		}		
	}

	@Override
	public boolean checkregnoByregno(String drRegno, Long orgId, Long drDraftId) {
		boolean result;
		Long count;
		if (drDraftId != null) {
			count = deathRegDraftRepository.checkduplregnobyRegnoanddraftId(drRegno, orgId, drDraftId);
			if (count > 0) {
				result = false;
			} else {
				count = deathRegistrationRepository.checkDuplicateRegno(drRegno, orgId);
			}

		} else {
			count = deathRegDraftRepository.checkDuplicateRegno(drRegno, orgId);

			if (count == 0) {
				count = deathRegistrationRepository.checkDuplicateRegno(drRegno, orgId);
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
	public void initializeWorkFlowForFreeService(TbDeathregDTO requestDto,ServiceMaster serviceMas) {
		boolean checkList = false;
		/*ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DR,
				requestDto.getOrgId());*/
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
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional
	public void smsAndEmailApproval(TbDeathregDTO dto, String decision) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smdto.setCc(decision);
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setRegNo(dto.getDrCertNo());
		String alertType = MainetConstants.BLANK;
		if(decision.equals(BndConstants.APPROVED)) {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
		}else if(decision.equals(BndConstants.REJECTED)){
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		}else {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG;
		}
		smdto.setEmail(dto.getRequestDTO().getEmail());
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BND, BndConstants.DEATH_CORR_APPR_URL,alertType, smdto, organisation, dto.getLangId());
	}

	/* xls code */
	/*
	@Override
	@Transactional
	public void saveDeathExcelData(TbDeathregUploadDTO deathMasterUploadDto, Long orgId, int langId) {
		
		TbDeathregTempExcelDataUpload deathMasterTempEntity = new TbDeathregTempExcelDataUpload();
        
		//get value from cpd code
        LookUp lokkupGen = CommonMasterUtility.getValueFromPrefixLookUp(deathMasterUploadDto.getDrSex(), "GEN");
        
        //get value from cpd desc
        Long lokUUp = CommonMasterUtility.getIdFromPrefixLookUpDesc(deathMasterUploadDto.getCpdAgeperiodId(), "APG", langId);
   
        //Date Convertion
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String xlsDate = deathMasterUploadDto.getDrDod();
        
        Date date = null;
		try {
			date = formatter.parse(xlsDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		deathMasterTempEntity.setDrDeceasedname(deathMasterUploadDto.getDrDeceasedname());
		deathMasterTempEntity.setDrMarDeceasedname(deathMasterUploadDto.getDrMarDeceasedname());
		deathMasterTempEntity.setDrDeceasedaddr(deathMasterUploadDto.getDrDeceasedaddr());
		deathMasterTempEntity.setDrMarDeceasedaddr(deathMasterUploadDto.getDrMarDeceasedaddr());
        
		deathMasterTempEntity.setDrInformantName(deathMasterUploadDto.getDrInformantName());
		deathMasterTempEntity.setDrMarInformantName(deathMasterUploadDto.getDrMarInformantName());
        deathMasterTempEntity.setDrInformantAddr(deathMasterUploadDto.getDrInformantAddr());
        deathMasterTempEntity.setDrMarInformantAddr(deathMasterUploadDto.getDrMarInformantAddr());
        
        deathMasterTempEntity.setCpdAttntypeId(1L);
        deathMasterTempEntity.setCpdDeathcauseId(1L);
        deathMasterTempEntity.setCpdReligionId(1L);
        deathMasterTempEntity.setDrRegdate(new Date());
        deathMasterTempEntity.setRegAplDate(new Date());
        deathMasterTempEntity.setDeathWFStatus("OPEN");
        deathMasterTempEntity.setOrgId(orgId);
        deathMasterTempEntity.setUserId(UserSession.getCurrent().getEmployee().getUserId());
        deathMasterTempEntity.setLangId(Integer.valueOf(deathMasterUploadDto.getLangId().toString()));
        deathMasterTempEntity.setLmoddate(deathMasterUploadDto.getLmoddate());
        deathMasterTempEntity.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        
        deathMasterTempEntity.setDrSex(String.valueOf(lokkupGen.getLookUpId()));
        deathMasterTempEntity.setDrDeceasedage(deathMasterUploadDto.getDrDeceasedage());
        deathMasterTempEntity.setDrDod(date);
        deathMasterTempEntity.setCpdAgeperiodId(lokUUp);
        
        tbDeathregTempExcelDataUploadRepository.save(deathMasterTempEntity);
		
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<Long, Double> getLoiCharges(Long orgId, int noOfCopies, Long applId,ServiceMaster serviceMas) {
		List<BirthDeathCFCInterface> birthDeathCFCInterface = birthDeathCfcInterfaceRepository.findData(applId);
		BndRateMaster ratemaster = new BndRateMaster();
		final Map<Long, Double> chargeMap = new HashMap<>();
		WSResponseDTO certificateCharges = null;
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgId);
			rateMasterModel.setServiceCode(serviceMas.getSmShortdesc());
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			rateMasterModel.setOrganisationType(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
							UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
					.getDescLangFirst());
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
			}
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
				if (!responsefortax.isFree()) {
					final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate.setServiceCode(serviceMas.getSmShortdesc());
						masterrate.setDeptCode(BndConstants.BIRTH_DEATH);
						masterrate.setIssuedCopy(0L);
						masterrate.setNoOfCopies(birthDeathCFCInterface.get(0).getCopies().intValue());
						//masterrate.setNoOfCopies(2);
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName(BndConstants.BND_RATE_MASTER);
					certificateCharges = issuenceOfBirthCertificateService.getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();

						BigDecimal totalAmount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							ChargeDetailDTO chargeDTO = new ChargeDetailDTO();
							TbTaxMas taxMaster = tbTaxMasService.getTaxMasterByTaxCode(orgId,
									serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
							chargeDTO.setChargeCode(taxMaster.getTaxId());
							chargeDTO.setChargeAmount(
									Double.valueOf(String.valueOf(charges.get(BndConstants.BNDCHARGES))));
							totalAmount = totalAmount.add(new BigDecimal(chargeDTO.getChargeAmount()));
							chargesInfo.add(chargeDTO);
							chargeMap.put(taxMaster.getTaxId(), chargeDTO.getChargeAmount());
						}
					}
				}

			}
		}
		return chargeMap;

	}

	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public TbDeathregDTO saveDataEntryDeathRegDet(TbDeathregDTO tbDeathregDTO) {

		final RequestDTO requestDTO = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.DED,
				tbDeathregDTO.getOrgId());

		TbDeathreg tbDeathreg1 = deathRegistrationRepository.findOne(tbDeathregDTO.getDrId());
		TbDeathregTemp tbDeathreg =new TbDeathregTemp();
		BeanUtils.copyProperties(tbDeathreg1, tbDeathreg);
		MedicalMasterTemp medicalMaster = new MedicalMasterTemp();
		DeceasedMasterTemp deceasedMaster = new DeceasedMasterTemp();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		MedicalMasterHistory medicalMasterHistory = new MedicalMasterHistory();
		DeceasedMasterHistory deceasedMasterHistory = new DeceasedMasterHistory();
		TbDeathRegHistory tbDeathRegHistory = new TbDeathRegHistory();
		TbCfcApplicationMstEntity tbCfcApplicationMstEntity = new TbCfcApplicationMstEntity();

		// medical master entry
		BeanUtils.copyProperties(tbDeathregDTO.getMedicalMasterDto(), medicalMaster);
		medicalMaster.setOrgId(tbDeathregDTO.getOrgId());
		medicalMaster.setDrId(tbDeathreg);
		medicalMaster.setLmoddate(new Date());
		medicalMaster.setUserId(tbDeathregDTO.getUserId());
		medicalMaster.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		medicalMaster.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
		medicalMaster.setMcDeathcause(tbDeathregDTO.getCpdDeathcauseId().toString());
		if (medicalMaster.getMedCert() == null) {
			medicalMaster.setMedCert("N");
		}
		if (medicalMaster.getMcPregnAssoc() == null) {
			medicalMaster.setMcPregnAssoc("N");
		}
		medicalMaster.setMcId(tbDeathreg1.getMedicalMaster().getMcId());
		tbDeathreg.setMedicalMaster(medicalMaster);

		// deceased master entry
		BeanUtils.copyProperties(tbDeathregDTO.getDeceasedMasterDTO(), deceasedMaster);
		deceasedMaster.setOrgId(tbDeathregDTO.getOrgId());
		deceasedMaster.setDrId(tbDeathreg);
		deceasedMaster.setUserId(tbDeathregDTO.getUserId());
		deceasedMaster.setLmoddate(new Date());

		if (deceasedMaster.getDecAlcoholic() == null) {
			deceasedMaster.setDecAlcoholic("N");
		}
		if (deceasedMaster.getDecChewarac() == null) {
			deceasedMaster.setDecChewarac("N");
		}
		if (deceasedMaster.getDecChewtb() == null) {
			deceasedMaster.setDecChewtb("N");
		}
		if (deceasedMaster.getDecSmoker() == null) {
			deceasedMaster.setDecSmoker("N");
		}

		deceasedMaster.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		deceasedMaster.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
		deceasedMaster.setDecId(tbDeathreg1.getDeceasedMaster().getDecId());
		tbDeathreg.setDeceasedMaster(deceasedMaster);

		// Death Registration Entry
		BeanUtils.copyProperties(tbDeathregDTO, tbDeathreg);
		tbDeathreg.setOrgId(tbDeathregDTO.getOrgId());
		tbDeathreg.setHiId(tbDeathregDTO.getHiId());
		tbDeathreg.setDrRegdate(tbDeathregDTO.getDrRegdate());
		tbDeathreg.setUserId(tbDeathregDTO.getUserId());
		tbDeathreg.setRegAplDate(new Date());
		tbDeathreg.setCpdAgeperiodId(tbDeathregDTO.getCpdAgeperiodId());
		tbDeathreg.setDrStatus("O");
		tbDeathreg.setDrCertNo("0");
		tbDeathreg.setDrFlag("C");
		tbDeathreg.setDrRelPreg("N");
		tbDeathreg.setDrCorrectionFlg("N");
		tbDeathreg.setDeathWFStatus("OPEN");
		tbDeathreg.setDrUdeathReg("N");
		tbDeathreg.setBcrFlag("N");
		tbDeathreg.setCertNoCopies(0L);
		
		String processName = null;
		if (serviceMas != null && serviceMas.getSmProcessId() != null) {
			Organisation org = new Organisation();
			org.setOrgid(tbDeathreg.getOrgId());
			final LookUp processLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(serviceMas.getSmProcessId(), org);
			if (processLookUp != null && !MainetConstants.CommonConstants.NA.equals(processLookUp.getLookUpCode())) {
				processName = processLookUp.getLookUpCode().toLowerCase();
			}
		}
		
		if (processName != null) {
			tbDeathreg.setDrStatus(BndConstants.STATUS);
			tbDeathreg.setDeathWFStatus(BndConstants.OPEN);
		}else {
			tbDeathreg.setDrStatus(BndConstants.DEATH_STATUS_APPROVED);
			tbDeathreg.setDeathWFStatus(BndConstants.APPROVED);
		}
		tbDeathreg.setDrStatus(BndConstants.STATUS);
		tbDeathreg.setDeathWFStatus(BndConstants.OPEN);
		deathRegistrationTempRepository.save(tbDeathreg);
		deathRegistrationRepository.updateNoOfIssuedCopy(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), BndConstants.OPEN);

		// medical master History entry
		BeanUtils.copyProperties(medicalMaster, medicalMasterHistory);
		medicalMasterHistory.setLmodDate(new Date());
		medicalMasterHistory.setAction(BndConstants.DED);
		tbDeathRegHistory.setMedicalMasterHistory(medicalMasterHistory);

		// deceased master History entry
		BeanUtils.copyProperties(deceasedMaster, deceasedMasterHistory);
		deceasedMasterHistory.setLmodDate(new Date());
		deceasedMasterHistory.setAction(BndConstants.DED);
		tbDeathRegHistory.setDeceasedMasterHistory(deceasedMasterHistory);

		// Death Registration History Entry
		BeanUtils.copyProperties(tbDeathreg, tbDeathRegHistory);
		tbDeathRegHistory.setLmoddate(new Date());
		tbDeathRegHistory.setAction(BndConstants.DED);
		tbDeathRegHistory.setDrStatus("O");
		deathRegHistoryrepository.save(tbDeathRegHistory);

		// RequestDTO entry
		requestDTO.setOrgId(tbDeathregDTO.getOrgId());
		requestDTO.setServiceId(serviceMas.getSmServiceId());
		requestDTO.setApmMode("F");
		requestDTO.setLangId(Long.valueOf(tbDeathregDTO.getLangId()));
		if (serviceMas.getSmFeesSchedule() == 0) {
			requestDTO.setPayStatus("F");
		} else {
			requestDTO.setPayStatus("Y");
		}
		requestDTO.setUserId(tbDeathregDTO.getUserId());
		requestDTO.setDeptId(tbDeathregDTO.getDeptId());
		requestDTO.setfName(tbDeathregDTO.getDrInformantName());

		if (tbDeathregDTO.getApmApplicationId() != null) {
			Long aplId = tbDeathregDTO.getApmApplicationId();
			requestDTO.setReferenceId(String.valueOf(aplId));
			requestDTO.setApplicationId(aplId);

			List<BirthDeathCFCInterface> birth = birthDeathCfcInterfaceRepository.findData(aplId);

			if (birth != null) {
				birth.forEach(entity -> {
					BeanUtils.copyProperties(entity, birthDeathCFCInterface);
					birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
					birthDeathCFCInterface.setLmodDate(new Date());
					birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
					cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
				});
			}
		}

		else if ((tbDeathregDTO.getApmApplicationId() == null) || (tbDeathregDTO.getApmApplicationId().equals("0"))) {
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
			final Long applicationId = applicationService.createApplication(requestDTO);
			if (null == applicationId) {
				throw new RuntimeException("Application Not Generated");
			}
			requestDTO.setReferenceId(String.valueOf(applicationId));
			requestDTO.setApplicationId(applicationId);
			tbDeathregDTO.setApplicationId(applicationId);
			tbDeathregDTO.setDrId(tbDeathreg.getDrId());
			if ((applicationId != null) && (applicationId != 0)) {
				tbDeathregDTO.setApmApplicationId(applicationId);
			}
			// death interface entry
			BeanUtils.copyProperties(tbDeathregDTO, birthDeathCFCInterface);
			birthDeathCFCInterface.setApmApplicationId(applicationId);
			birthDeathCFCInterface.setBdRequestId(tbDeathreg.getDrId());
			birthDeathCFCInterface.setLmodDate(new Date());
			tbDeathreg.getMedicalMaster().setDrId(tbDeathreg);
			tbDeathreg.getDeceasedMaster().setDrId(tbDeathreg);
			tbDeathRegHistory.getMedicalMasterHistory().setDrHiId(tbDeathRegHistory.getDrHiId());
			tbDeathRegHistory.getDeceasedMasterHistory().setDrHiId(tbDeathRegHistory.getDrHiId());
			birthDeathCFCInterface.setOrgId(tbDeathreg.getOrgId());
			birthDeathCFCInterface.setSmServiceId(serviceMas.getSmServiceId());
			cfcInterfaceJpaRepository.save(birthDeathCFCInterface);
		}
		if ((tbDeathregDTO.getApmApplicationId() != null) && (tbDeathregDTO.getApmApplicationId() != 0)) {
			Long lang = Long.valueOf(tbDeathregDTO.getLangId());
			Organisation org = UserSession.getCurrent().getOrganisation();
			tbCfcApplicationMstEntity.setApmFname(tbDeathregDTO.getDrInformantName());
			tbCfcApplicationMstEntity.setApmApplicationId(tbDeathregDTO.getApmApplicationId());
			tbCfcApplicationMstEntity.setApmSex(tbDeathregDTO.getDrSex());
			tbCfcApplicationMstEntity.setApmApplicationDate(tbDeathregDTO.getDrRegdate());
			tbCfcApplicationMstEntity.setUserId(tbDeathregDTO.getUserId());
			tbCfcApplicationMstEntity.setLangId(lang);
			tbCfcApplicationMstEntity.setLmoddate(new Date());
			tbCfcApplicationMstEntity.setApmChklstVrfyFlag("P");
			tbCfcApplicationMstEntity.setApmPayStatFlag(requestDTO.getPayStatus());
			tbCfcApplicationMstEntity.setTbOrganisation(org);
			tbCfcApplicationMstEntity.setTbServicesMst(serviceMas);
			tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		}
		
		tbDeathregDTO.setServiceId(serviceMas.getSmServiceId());
		if (serviceMas.getSmFeesSchedule() == 0 || tbDeathregDTO.getAmount() == 0.0) {
			initializeWorkFlowForFreeService(tbDeathregDTO,serviceMas);
		}
		return tbDeathregDTO;

	}
	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TbDeathregDTO saveDeathRegDetOnApprovalTemp(TbDeathregDTO tbDeathregDTO) {
	
		TbDeathreg tbDeathreg = new TbDeathreg();
		MedicalMaster medicalMaster = new MedicalMaster();
		DeceasedMaster deceasedMaster = new DeceasedMaster();
		
		MedicalMasterHistory medicalMasterHistory = new MedicalMasterHistory();
		DeceasedMasterHistory deceasedMasterHistory=new DeceasedMasterHistory();
		TbDeathRegHistory tbDeathRegHistory= new TbDeathRegHistory();
		
		
		// medical master entry
		BeanUtils.copyProperties(tbDeathregDTO.getMedicalMasterDto(), medicalMaster);
		medicalMaster.setDrId(tbDeathreg);
		medicalMaster.setLmoddate(new Date());
		medicalMaster.setUserId(tbDeathregDTO.getUserId());
		medicalMaster.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		medicalMaster.setUpdatedDate(tbDeathregDTO.getUpdatedDate());
		tbDeathreg.setMedicalMaster(medicalMaster);
		BeanUtils.copyProperties(tbDeathregDTO.getDeceasedMasterDTO(), deceasedMaster);
		deceasedMaster.setDrId(tbDeathreg);
		deceasedMaster.setUserId(tbDeathregDTO.getUserId());
		deceasedMaster.setLmoddate(new Date());
		
		if(deceasedMaster.getDecAlcoholic() == null) {
			deceasedMaster.setDecAlcoholic("N");	
		}
		if(deceasedMaster.getDecChewarac() == null) {
			deceasedMaster.setDecChewarac("N");	
		}
		if(deceasedMaster.getDecChewtb() == null) {
			deceasedMaster.setDecChewtb("N");	
		}
		if(deceasedMaster.getDecSmoker() == null) {
			deceasedMaster.setDecSmoker("N");	
		}
		
		tbDeathreg.setDeceasedMaster(deceasedMaster);
		BeanUtils.copyProperties(tbDeathregDTO, tbDeathreg);
		tbDeathreg.setDeathWFStatus(BndConstants.APPROVED);
		tbDeathreg.setDrStatus("Y");
		deathRegistrationRepository.save(tbDeathreg);
		
		return tbDeathregDTO;
	}

}