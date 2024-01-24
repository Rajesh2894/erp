package com.abm.mainet.bnd.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dao.BirthRegDao;
import com.abm.mainet.bnd.dao.InclusionOfChildDao;
import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.BirthRegistrationCorrection;
import com.abm.mainet.bnd.domain.BirthRegistrationCorrectionHistory;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.BirthRegistrationHistoryEntity;
import com.abm.mainet.bnd.domain.ParentDetail;
import com.abm.mainet.bnd.domain.ParentDetailHistory;
import com.abm.mainet.bnd.dto.BirthRegistrationCorrDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.ParentDetailDTO;
import com.abm.mainet.bnd.repository.BirthDeathCfcInterfaceRepository;
import com.abm.mainet.bnd.repository.BirthRegCorrectionHistoryRepository;
import com.abm.mainet.bnd.repository.BirthRegCorrectionRepository;
import com.abm.mainet.bnd.repository.BirthRegHistRepository;
import com.abm.mainet.bnd.repository.BirthRegRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.ParentDetHistRepository;
import com.abm.mainet.bnd.ui.model.InclusionOfChildNameModel;
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
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author vishwanath.s
 *
 */
@Service
@WebService(endpointInterface="com.abm.mainet.bnd.service.InclusionOfChildNameService")
@Produces(value= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE})
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "/inclusionOfChildNameService")
@Path(value="/inclusionOfChildNameService")
public class InclusionOfChildNameServiceImpl implements InclusionOfChildNameService {

	@Resource
	private InclusionOfChildDao inclusionOfChildDao;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private IFileUploadService fileUploadService;
	
	@Resource
	private BirthRegHistRepository birthRegHistRepository;
	
	@Resource
	private ParentDetHistRepository parentDetHistRepository;
	
	@Resource
	private CfcInterfaceJpaRepository tbBdCfcInterfaceJpaRepository;

	@Autowired
	private BirthRegCorrectionRepository birthRegCorrectionRepository;

	@Autowired
	private BirthRegCorrectionHistoryRepository birthRegCorrectionRepositoy;
	
	@Autowired
	private BirthRegRepository birthRegRepository;
	 
	@Autowired
	private BirthRegDao birthRegDao;
	
	@Autowired
	private IChallanService challanService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Resource
    private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IBirthRegService iBirthRegService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;

	@Autowired
	private BirthDeathCfcInterfaceRepository birthDeathCfcInterfaceRepository;
	
	@Autowired
	private IOrganisationDAO iOrganisationDAO;
	
	@Override
	@Transactional(readOnly = true)
	@Path(value="/SearchBirth")
	@ApiOperation(value = "get application detail", response = BirthRegistrationDTO.class)
	@GET
	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(@QueryParam("certNo")String certNo, @QueryParam("regNo")String regNo, @QueryParam("year") String year,@RequestParam("brDob") Date brDob,
			@QueryParam("applicnId")String applicnId, @QueryParam("orgId")Long orgId) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.BR, orgId);
		Long smServiceId = serviceMas.getSmServiceId();
		List<BirthRegistrationEntity> DetailEntity = inclusionOfChildDao.getBirthRegisteredApplicantList(certNo,
				regNo, year, brDob,applicnId,smServiceId, orgId);
        List<BirthRegistrationDTO> listDTO=new ArrayList<BirthRegistrationDTO>();
		
		if (DetailEntity != null) {
			DetailEntity.forEach(entity->{
				BirthRegistrationDTO dto = new BirthRegistrationDTO();
				 ParentDetailDTO pDto = new ParentDetailDTO(); 
				  if(entity!=null) {
				  BeanUtils.copyProperties(entity, dto);
				  BeanUtils.copyProperties(entity.getParentDetail(), pDto);
				  }
				  pDto.setCpdId3(entity.getParentDetail().getCpdId3());
				dto.setParentDetailDTO(pDto);
				
				/*
				 * LookUp lokkup =
				 * CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(
				 * entity.getBrSex()), orgId, GENDER); dto.setBrSex(lokkup.getLookUpDesc());
				 */
				listDTO.add(dto);
				
				   });
		
				}
			
		return listDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BirthRegistrationDTO getBirthByID(Long brId) {
		BirthRegistrationEntity birthRegCorrection = birthRegRepository.findOne(brId);
		BirthRegistrationDTO dto = new BirthRegistrationDTO();
		ParentDetailDTO pDto = new ParentDetailDTO();
		BeanUtils.copyProperties(birthRegCorrection, dto);
		
        Long sum = 0L;
		
		sum = dto.getNoOfCopies() != null && birthRegCorrection.getBrManualCertNo() != null
				? dto.getNoOfCopies() + birthRegCorrection.getBrManualCertNo()
				: dto.getNoOfCopies() != null ? sum + dto.getNoOfCopies()
						: birthRegCorrection.getBrManualCertNo() != null ? sum + birthRegCorrection.getBrManualCertNo()
								: sum;

		dto.setAlreayIssuedCopy(sum);
		BeanUtils.copyProperties(birthRegCorrection.getParentDetail(), pDto);
		if (birthRegCorrection.getParentDetail().getCpdId3() != null) {
			pDto.setCpdId3(birthRegCorrection.getParentDetail().getCpdId3());
		}
		dto.setBirthWfStatus(birthRegCorrection.getBirthWFStatus());
		dto.setNoOfCopies(null);
		dto.setParentDetailDTO(pDto);

		return dto;
	}
	
	//save inclusion of child name
	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	//@GET
	//@POST
	//@Path("/saveInclusionOfChild")
	public BirthRegistrationDTO saveInclusionOfChild(BirthRegistrationDTO requestDTO,InclusionOfChildNameModel model) {
		final RequestDTO commonRequest = requestDTO.getRequestDTO();
		BirthRegistrationCorrectionHistory birthRegCorreHistory=new BirthRegistrationCorrectionHistory();
		BirthRegistrationCorrection birthRegCorrection = new BirthRegistrationCorrection();
		BirthDeathCFCInterface birthDeathCFCInterface = new BirthDeathCFCInterface();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.INC, requestDTO.getOrgId());
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(serviceMas.getSmServiceId());
		commonRequest.setApmMode("F");
		commonRequest.setUserId(requestDTO.getUserId());
		if(serviceMas.getSmFeesSchedule()==0)
		{
		  commonRequest.setPayStatus("F");
		}else {
			commonRequest.setPayStatus("Y");
		}
		
		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		// Generate the Application Number #115500 By Bhagyashri
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
		birthRegCorrection.setPdMothername(requestDTO.getParentDetailDTO().getPdMothernameMar());
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
		if (requestDTO.getParentDetailDTO().getPdRegUnitId() !=null)
		birthRegCorrection.setPdRegUnitId(requestDTO.getParentDetailDTO().getPdRegUnitId());
		birthRegCorrectionRepository.save(birthRegCorrection);
		birthRegRepository.updateNoOfIssuedCopy(requestDTO.getBrId(), requestDTO.getOrgId(), birthRegCorrection.getBirthWFStatus());
		
		// Birth Interface Entry
		BeanUtils.copyProperties(requestDTO, birthDeathCFCInterface);
		birthDeathCFCInterface.setApmApplicationId(applicationId);
		birthDeathCFCInterface.setBdRequestId(birthRegCorrection.getBrId());
		birthDeathCFCInterface.setOrgId(requestDTO.getOrgId());
		birthDeathCFCInterface.setUserId(birthRegCorrection.getUserId());
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
		birthRegCorrectionRepositoy.save(birthRegCorreHistory);
		//child inclusion history start
		//when BPM is not applicable
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),requestDTO.getOrgId());
		if(processName==null) {
			BirthRegistrationCorrDTO tbBirthregcorrDTO = new BirthRegistrationCorrDTO();
			BeanUtils.copyProperties(birthRegCorrection, tbBirthregcorrDTO);
			requestDTO.setApplicationId(String.valueOf(applicationId));
			tbBirthregcorrDTO.setApmApplicationId(applicationId);
			iBirthRegService.updateBirthApproveStatus(tbBirthregcorrDTO, MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	tbBirthregcorrDTO.setBirthWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
	    	iBirthRegService.updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Status.CLOSED, birthRegCorrection.getOrgId());
	    	//certificate generation/update
	    	iBirthRegService.updateBirthRegCorrApprove(tbBirthregcorrDTO,MainetConstants.WorkFlow.Decision.APPROVED,MainetConstants.WorkFlow.Status.CLOSED);
	    	issuenceOfBirthCertificateService.updatNoOfcopyStatus(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getOrgId(), tbBirthregcorrDTO.getBrId(), requestDTO.getNoOfCopies());
	    	// save data to birth registration entity after final approval 
	    	BeanUtils.copyProperties(tbBirthregcorrDTO, requestDTO);
	    	requestDTO.setBrChildName(birthRegCorrection.getBrChildname());
	    	requestDTO.setBrChildNameMar(birthRegCorrection.getBrChildnameMar());
	    	requestDTO.getParentDetailDTO().setPdFathername(tbBirthregcorrDTO.getPdFathername());
	    	saveInclusionOfChildOnApproval(requestDTO);
	    	
		}
		requestDTO.setApmApplicationId(applicationId);
		if (serviceMas.getSmFeesSchedule() == 0 || requestDTO.getAmount()==0.0) {
			requestDTO.setServiceId(serviceMas.getSmServiceId());
			initializeWorkFlowForFreeService(requestDTO);
		} else {
		setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model);
		}
		requestDTO.setLangId(Integer.valueOf(String.valueOf(commonRequest.getLangId())));
		Organisation organisation = iOrganisationDAO.getOrganisationById(requestDTO.getOrgId(),MainetConstants.STATUS.ACTIVE);
		smsAndEmail(requestDTO, organisation);
		return requestDTO;	
	} 

	@Override
	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto) {
		long noOfDays = Utility.getDaysBetweenDates(birthRegDto.getBrDob(), new Date());
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
	public BirthRegistrationDTO saveInclusionOfChildOnApproval(BirthRegistrationDTO requestDTO) {
		
		BirthRegistrationEntity birthReg = new BirthRegistrationEntity();
		ParentDetail parentDetail = new ParentDetail();
		BirthRegistrationHistoryEntity birthRegHistEntity=new BirthRegistrationHistoryEntity();
		ParentDetailHistory parentDetailHistEntity=new ParentDetailHistory();
		List<BirthDeathCFCInterface> birthDeathList = birthDeathCfcInterfaceRepository.findData(requestDTO.getApmApplicationId());
		//Parent Detail Entry
		
		List<BirthRegistrationEntity> tbBirthregentity = birthRegDao.getBirthRegApplnData(requestDTO.getBrId(), requestDTO.getOrgId());
		parentDetail.setBrId(tbBirthregentity.get(0)); 
		parentDetail.setPdId(tbBirthregentity.get(0).getParentDetail().getPdId());
		BeanUtils.copyProperties(requestDTO.getParentDetailDTO(), parentDetail);
		parentDetail.setOrgId(requestDTO.getOrgId());
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
		birthReg.setBrSex(String.valueOf(requestDTO.getBrSex()));}
		
		birthReg.setBrRegDate(new Date());
		birthReg.setBrStatus("A");
		birthReg.setBirthWFStatus(BndConstants.APPROVED);
		birthReg.setBrCorrectionFlg("Y");
		birthReg.setBrCorrnDate(new Date());
		//birthRegRepository.save(birthReg);
		
		birthReg.setBrChildName(requestDTO.getBrChildName());
		birthReg.setBrChildNameMar(requestDTO.getBrChildNameMar());
		inclusionOfChildDao.updateBirthRegEntityOnApproval(birthReg.getBrId(), birthReg.getBrChildName(), birthReg.getBrChildNameMar(), 
					birthReg.getBirthWFStatus(), birthReg.getBrStatus(), birthReg.getBrCorrectionFlg(), birthReg.getBrCorrnDate(), birthReg.getOrgId());
		
		//birth Registration History start
		BeanUtils.copyProperties(birthReg, birthRegHistEntity);
		birthRegHistEntity.setBrSex(tbBirthregentity.get(0).getBrSex());
		birthRegHistEntity.setAction(BndConstants.BR);
		birthRegHistRepository.save(birthRegHistEntity);
		//birth Registration history end 

		//Parent Detail History start
		BeanUtils.copyProperties(parentDetail, parentDetailHistEntity);
		parentDetailHistEntity.setAction(BndConstants.BR);
		parentDetailHistEntity.setPdBrId(parentDetail.getBrId().getBrId()); 
		parentDetHistRepository.save(parentDetailHistEntity);
		//Parent Detail history end 
 
		requestDTO.setAlreayIssuedCopy(tbBirthregentity.get(0).getNoOfCopies());
		if(!birthDeathList.isEmpty())
		requestDTO.setNoOfCopies(birthDeathList.get(0).getCopies());
		return requestDTO;
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline,InclusionOfChildNameModel birthRegModel) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(BndConstants.INC,
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
		 if(null != birthRegModel.getRequestDTO() && null != birthRegModel.getRequestDTO().getWardNo() &&  birthRegModel.getRequestDTO().getWardNo()!=0L ) {
		 wardName = CommonMasterUtility.getNonHierarchicalLookUpObject(birthRegModel.getRequestDTO().getWardNo()).getLookUpDesc();
		 }
		 String pinCode = "";
		 if(birthRegModel.getRequestDTO().getPincodeNo()!=null) {
			 pinCode = String.valueOf(birthRegModel.getRequestDTO().getPincodeNo());
		 }
		 String applicantAddress="";
		 if(StringUtils.isNotEmpty(wardName)) {
			 applicantAddress= String.join(" ",
				Arrays.asList(birthRegModel.getRequestDTO().getBldgName(),
						birthRegModel.getRequestDTO().getBlockName(), birthRegModel.getRequestDTO().getRoadName(),wardName,
						birthRegModel.getRequestDTO().getCityName(),pinCode));
		 }else {
			 applicantAddress= String.join(" ",
						Arrays.asList(birthRegModel.getRequestDTO().getBldgName(),
								birthRegModel.getRequestDTO().getBlockName(), birthRegModel.getRequestDTO().getRoadName(),
								birthRegModel.getRequestDTO().getCityName(),pinCode));
		 }
			 
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
			birthRegModel.setReceiptDTO(printDto);
			birthRegModel.setSuccessMessage(birthRegModel.getAppSession().getMessage("adh.receipt"));
		}
		
	}

	private void initializeWorkFlowForFreeService(BirthRegistrationDTO requestDto) {
		boolean checkList = false;
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
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void smsAndEmail(BirthRegistrationDTO dto,Organisation organisation)
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
	public void smsAndEmailApproval(BirthRegistrationDTO dto, String decision) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		smdto.setUserId(dto.getUserId());
		smdto.setAppNo(String.valueOf(dto.getApmApplicationId()));
		smdto.setMobnumber(dto.getRequestDTO().getMobileNo());
		Organisation organisation =UserSession.getCurrent().getOrganisation();
		smdto.setCc(decision);
		String fullName = String.join(" ", Arrays.asList(dto.getRequestDTO().getfName(),
				dto.getRequestDTO().getmName(), dto.getRequestDTO().getlName()));
		smdto.setAppName(fullName);
		smdto.setRegNo(dto.getBrCertNo());
		smdto.setEmail(dto.getRequestDTO().getEmail());
		String alertType = MainetConstants.BLANK;
		if(decision.equals(BndConstants.APPROVED)) {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
		}else {
			alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		}
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				BndConstants.BND, BndConstants.INCLUSION_OF_CHILD_APPR_URL, alertType, smdto, organisation, dto.getLangId());
		
	}
	
}
