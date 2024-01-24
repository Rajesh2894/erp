package com.abm.mainet.sfac.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.BankMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.domain.EquityGrantDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantFuctionalCommitteeDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;
import com.abm.mainet.sfac.domain.EquityGrantShareHoldingDetailEntity;
import com.abm.mainet.sfac.domain.FPOAdministrativeDetailEntity;
import com.abm.mainet.sfac.domain.FPOBankDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.dto.EquityGrantDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantFunctionalCommitteeDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantShareHoldingDetailDto;
import com.abm.mainet.sfac.dto.FPOAdministrativeDto;
import com.abm.mainet.sfac.dto.FPOBankDetailDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.repository.EquityGrantDetailRepository;
import com.abm.mainet.sfac.repository.EquityGrantFunctionalCommitteeDetailRepostory;
import com.abm.mainet.sfac.repository.EquityGrantMasterRepository;
import com.abm.mainet.sfac.repository.EquityGrantShareholdingDetailRepository;
import com.abm.mainet.sfac.repository.FPOAdministrativeDetailRpository;
import com.abm.mainet.sfac.repository.FPOBankDetailRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.ui.model.EquityGrantApprovalModel;

@Service
public class EquityGrantRequestMasterServiceImpl implements EquityGrantRequestMasterService {

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired FPOBankDetailRepository fpoBankDetailRepository;

	@Autowired FPOAdministrativeDetailRpository fpoAdministrativeDetailRpository;

	@Autowired EquityGrantMasterRepository equityGrantMasterRepository;

	@Autowired EquityGrantDetailRepository equityGrantDetailRepository;
	
	@Autowired EquityGrantShareholdingDetailRepository equityGrantShareholdingDetailRepository;
	
	@Autowired EquityGrantFunctionalCommitteeDetailRepostory equityGrantFunctionalCommitteeDetailRepostory;
	
	@Autowired
	private IOrganisationService orgService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Resource
	private BankMasterRepository bankMasterRepository;

	private static final Logger logger = Logger.getLogger(EquityGrantRequestMasterServiceImpl.class);

	@Override
	public FPOMasterDto getFPODetails(Long masId) {
		// TODO Auto-generated method stub
		FPOMasterDto fpoMasterDto = new FPOMasterDto();
		FPOMasterEntity fpoMasterEntity =   fpoMasterRepository.findOne(masId);
		BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
		List<FPOBankDetailEntity> bankDetailEntities = fpoBankDetailRepository.getBankDetails(fpoMasterEntity);
		List<FPOAdministrativeDto> fpoAdministrativeDtos = new ArrayList<FPOAdministrativeDto>();
		List<FPOBankDetailDto> fpoBankDetailDtos = new ArrayList<FPOBankDetailDto>();
		for(FPOBankDetailEntity fpoBankDetailEntity : bankDetailEntities) {
			FPOBankDetailDto fpoBankDetailDto = new FPOBankDetailDto();
			BeanUtils.copyProperties(fpoBankDetailEntity, fpoBankDetailDto);

			fpoBankDetailDto.getBankMasterList().add(bankMasterRepository.findOne(fpoBankDetailEntity.getBankName()));
			fpoBankDetailDto.setBankName(bankMasterRepository.findOne(fpoBankDetailEntity.getBankName()).getBank());
			fpoBankDetailDtos.add(fpoBankDetailDto);

		}
		List<FPOAdministrativeDetailEntity> fpoAdministrativeDetailEntities = fpoAdministrativeDetailRpository.getAdminDetails(fpoMasterEntity);
		for(FPOAdministrativeDetailEntity fpoAdministrativeDetailEntity : fpoAdministrativeDetailEntities) {
			FPOAdministrativeDto fpoAdministrativeDto = new FPOAdministrativeDto();
			BeanUtils.copyProperties(fpoAdministrativeDetailEntity, fpoAdministrativeDto);
			fpoAdministrativeDtos.add(fpoAdministrativeDto);
		}
		fpoMasterDto.setFpoAdministrativeDto(fpoAdministrativeDtos);
		fpoMasterDto.setFpoBankDetailDto(fpoBankDetailDtos);
		return fpoMasterDto;
	}
	@Transactional
	@Override
	public EquityGrantMasterDto saveAndUpdateApplication(EquityGrantMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("EGR",
							orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());

			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			 if (applicationId != null)
				mastDto.setAppNumber(applicationId);

			if ((mastDto.getDocumentList() != null) && !mastDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(applicationId);
				fileUploadService.doFileUpload(mastDto.getDocumentList(), requestDto);
			}
			
			EquityGrantMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = equityGrantMasterRepository.save(masEntity);
			mastDto.setEgId(masEntity.getEgId());

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(mastDto.getAppNumber());
				applicationData.setOrgId(orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				mastDto.getApplicantDetailDto().setUserId(mastDto.getCreatedBy());
				mastDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				mastDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				mastDto.getApplicantDetailDto().setExtIdentifier(mastDto.getFpoMasterDto().getCbboId());
				if (requestDto != null && requestDto.getMobileNo() != null) {
					mastDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				commonService.initiateWorkflowfreeService(applicationData, mastDto.getApplicantDetailDto());
			}



		} catch (Exception e) {
			logger.error("error occured while saving  Equity Grant Master  details" + e);
			throw new FrameworkException("error occured while saving Equity Grant Master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private RequestDTO setApplicantRequestDto(EquityGrantMasterDto masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());
		
		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		requestDto.setEmail(masDto.getEmailId());
		requestDto.setMobileNo(masDto.getMobileNo());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;
	}
	private EquityGrantMasterEntity mapDtoToEntity(EquityGrantMasterDto mastDto) {
	
		EquityGrantMasterEntity masEntity = new EquityGrantMasterEntity();
		List<EquityGrantDetailEntity> detailsList = new ArrayList<>();
		List<EquityGrantFuctionalCommitteeDetailEntity> equityGrantFuctionalCommitteeDetailEntities = new ArrayList<>();
		List<EquityGrantShareHoldingDetailEntity> equityGrantShareHoldingDetailEntities = new ArrayList<>();


		BeanUtils.copyProperties(mastDto, masEntity);
		masEntity.setFpoName(mastDto.getFpoMasterDto().getFpoId());
		masEntity.setState(mastDto.getFpoMasterDto().getSdb1());
		masEntity.setCorrespondenceAdd(mastDto.getFpoMasterDto().getFpoOffAddr());
		masEntity.setRegistrationNo(mastDto.getFpoMasterDto().getFpoRegNo());
		masEntity.setRegistrationDate(mastDto.getFpoMasterDto().getDateIncorporation());
		masEntity.setPaidUpCapital(new BigDecimal(mastDto.getFpoMasterDto().getPaidupCapital()));
		mastDto.getEquityGrantDetailDto().forEach(dto -> {
			EquityGrantDetailEntity entity = new EquityGrantDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masEntity);
			entity.setIsBOM(MainetConstants.FlagN);
			detailsList.add(entity);
		});
		mastDto.getEquityGrantDetailDtoBOM().forEach(dto -> {
			EquityGrantDetailEntity entity = new EquityGrantDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masEntity);
			entity.setIsBOM(MainetConstants.FlagY);
			detailsList.add(entity);
		});
		mastDto.getEquityGrantFunctionalCommitteeDetailDtos().forEach(dto -> {
			EquityGrantFuctionalCommitteeDetailEntity entity = new EquityGrantFuctionalCommitteeDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masEntity);
			equityGrantFuctionalCommitteeDetailEntities.add(entity);
		});
		mastDto.getEquityGrantShareHoldingDetailDtos().forEach(dto -> {
			EquityGrantShareHoldingDetailEntity entity = new EquityGrantShareHoldingDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masEntity);
			equityGrantShareHoldingDetailEntities.add(entity);
		});



		if (CollectionUtils.isNotEmpty(mastDto.getEquityGrantDetailDto()))
			masEntity.setEquityGrantDetailEntities(detailsList);
		if (CollectionUtils.isNotEmpty(mastDto.getEquityGrantFunctionalCommitteeDetailDtos()))
			masEntity.setEquityGrantFuctionalCommitteeDetailEntities(equityGrantFuctionalCommitteeDetailEntities);
		if (CollectionUtils.isNotEmpty(mastDto.getEquityGrantShareHoldingDetailDtos()) && mastDto.getEquityGrantShareHoldingDetailDtos().get(0).getNoOfShareHolder()!=null)
			masEntity.setEquityGrantShareHoldingDetailEntities(equityGrantShareHoldingDetailEntities);


		return masEntity;
	}


	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}

	public void updateLegacyDocuments(AttachDocs attachDocs, Long empId) {
		if (attachDocs!=null && attachDocs.getAttId()!=null && attachDocs.getAttId()!=0 ) {

			attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, attachDocs.getAttId());

		}
	}

	@Override
	public List<EquityGrantMasterDto> getAppliacationDetails(Long fpoId, String status) {
		// TODO Auto-generated method stub
		List<EquityGrantMasterDto> equityGrantMasterDtos = new ArrayList<>();
		List<EquityGrantMasterEntity> equityGrantMasterEntities = new ArrayList<>();
		if((fpoId!=null && fpoId != 0)  && (status!=null && !status.isEmpty())) {


			equityGrantMasterEntities = equityGrantMasterRepository.findByFpoNameAndAppStatus(fpoId, status);
		}else {
			equityGrantMasterEntities = equityGrantMasterRepository.findByFpoName(fpoId);
		}

		FPOMasterEntity fpoMasterEntity = fpoMasterRepository.findOne(fpoId);

		for (EquityGrantMasterEntity entity : equityGrantMasterEntities) {
			EquityGrantMasterDto dto = new EquityGrantMasterDto();

			BeanUtils.copyProperties(entity, dto);
			FPOMasterDto fpoMasterDto = new FPOMasterDto();
			BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
			dto.setFpoMasterDto(fpoMasterDto);

			equityGrantMasterDtos.add(dto);
		}

		return equityGrantMasterDtos;
	}

	@Override
	public EquityGrantMasterDto getDetailById(Long egId) {
		// TODO Auto-generated method stub
		EquityGrantMasterDto equityGrantMasterDto = new EquityGrantMasterDto();

		EquityGrantMasterEntity equityGrantMasterEntity =  equityGrantMasterRepository.findOne(egId);

		FPOMasterDto fpoMasterDto = getFPODetails(equityGrantMasterEntity.getFpoName());



		BeanUtils.copyProperties(equityGrantMasterEntity, equityGrantMasterDto);
		equityGrantMasterDto.setFpoMasterDto(fpoMasterDto);

		List<EquityGrantDetailDto> equityGrantDetailDtos = new ArrayList<>();
		List<EquityGrantDetailDto> equityGrantDetailDtosBOM = new ArrayList<>();
		for(EquityGrantDetailEntity equityGrantDetailEntity : equityGrantDetailRepository.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantDetailDto equityGrantDetailDto = new EquityGrantDetailDto();
			if(equityGrantDetailEntity.getIsBOM().equalsIgnoreCase("N")) {
			BeanUtils.copyProperties(equityGrantDetailEntity, equityGrantDetailDto);
			equityGrantDetailDtos.add(equityGrantDetailDto);
			}else {
				
				BeanUtils.copyProperties(equityGrantDetailEntity, equityGrantDetailDto);
				equityGrantDetailDtosBOM.add(equityGrantDetailDto);
			}
		}
		
		List<EquityGrantFunctionalCommitteeDetailDto> equityGrantFunctionalCommitteeDetailDtos = new ArrayList<>();
		for(EquityGrantFuctionalCommitteeDetailEntity equityGrantFuctionalCommitteeDetailEntity : equityGrantFunctionalCommitteeDetailRepostory.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantFunctionalCommitteeDetailDto equityGrantFunctionalCommitteeDetailDto = new EquityGrantFunctionalCommitteeDetailDto();
		
				
				BeanUtils.copyProperties(equityGrantFuctionalCommitteeDetailEntity, equityGrantFunctionalCommitteeDetailDto);
				equityGrantFunctionalCommitteeDetailDtos.add(equityGrantFunctionalCommitteeDetailDto);
			
		}
		
		List<EquityGrantShareHoldingDetailDto> equityGrantShareHoldingDetailDtos = new ArrayList<>();
		for(EquityGrantShareHoldingDetailEntity equityGrantShareHoldingDetailEntity : equityGrantShareholdingDetailRepository.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantShareHoldingDetailDto equityGrantShareHoldingDetailDto = new EquityGrantShareHoldingDetailDto();
		
				
				BeanUtils.copyProperties(equityGrantShareHoldingDetailEntity, equityGrantShareHoldingDetailDto);
				equityGrantShareHoldingDetailDtos.add(equityGrantShareHoldingDetailDto);
			
		}



		
		/*FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));*/

		equityGrantMasterDto.setEquityGrantDetailDto(equityGrantDetailDtos);
		equityGrantMasterDto.setEquityGrantDetailDtoBOM(equityGrantDetailDtosBOM);
		equityGrantMasterDto.setEquityGrantFunctionalCommitteeDetailDtos(equityGrantFunctionalCommitteeDetailDtos);
		equityGrantMasterDto.setEquityGrantShareHoldingDetailDtos(equityGrantShareHoldingDetailDtos);

		return equityGrantMasterDto;
	}
	private Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;

		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
		for (AttachDocs doc : attachDocs) {
			fileList = new HashSet<>();
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
			String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);

			String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingPath);

			directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
			FileOutputStream fos = null;
			File file = null;
			try {
				final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

				file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

				fos = new FileOutputStream(file);

				fos.write(image);

				fos.close();

			} catch (final Exception e) {
				throw new FrameworkException("Exception in getting getUploadedFileList", e);
			} finally {
				try {

					if (fos != null) {
						fos.close();
					}

				} catch (final IOException e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				}
			}
			fileList.add(file);
			fileMap.put(x, fileList);
			x++;
		}


		return fileMap;
	}

	@Transactional
	@Override
	public EquityGrantMasterDto updateApplication(EquityGrantMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");

			EquityGrantMasterEntity masEntity = mapDtoToEntityDetails(mastDto);
			// equityGrantDetailRepository.save(masEntity.getEquityGrantDetailEntities());
			equityGrantMasterRepository.save(masEntity);


			/*if (removedIds != null && !removedIds.isEmpty()) {
                // update with DEACTIVE status on removeIds
				 fpoManagementCostDocDetailRepository.deActiveBPInfo(removedIds,
                        UserSession.getCurrent().getEmployee().getEmpId());
            }*/

			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(masEntity.getOrgId());
			requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setUserId(masEntity.getCreatedBy());

			





		} catch (Exception e) {
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}
	private EquityGrantMasterEntity mapDtoToEntityDetails(EquityGrantMasterDto mastDto) {
		// TODO Auto-generated method stub
		EquityGrantMasterEntity masterEntity = equityGrantMasterRepository.findOne(mastDto.getEgId());

		List<EquityGrantDetailEntity> equityGrantDetailEntities = new ArrayList<>();
		BeanUtils.copyProperties(mastDto, masterEntity);
		mastDto.getEquityGrantDetailDto().forEach(dto -> {

			EquityGrantDetailEntity entity = new EquityGrantDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMasterEntity(masterEntity);
			equityGrantDetailEntities.add(entity);

		});

		masterEntity.setEquityGrantDetailEntities(equityGrantDetailEntities);


		return masterEntity;
	}
	@Override
	public void updateApprovalStatusAndRemark(EquityGrantMasterDto oldMasDto,String lastDecision, String status) {
	
			EquityGrantMasterEntity equityGrantMasterEntity = equityGrantMasterRepository.findOne(oldMasDto.getEgId());
			
			if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
				equityGrantMasterEntity.setAppStatus(lastDecision);
				

			} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
					&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
				equityGrantMasterEntity.setAppStatus(status);

			} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
					&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED))  {
				equityGrantMasterEntity.setAppStatus(lastDecision);

			}
			
			
			
		
			equityGrantMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());
			
			equityGrantMasterRepository.save(equityGrantMasterEntity);
			
			/*equityGrantMasterRepository.updateApprovalStatusAndRemark(oldMasDto.getEgId(), 
					oldMasDto.getAppStatus());*/
			/*allocationOfBlocksHistRepo.updateApprovalStatusAndRemHist(oldMasDto.getBlockId(), oldMasDto.getApplicationId(),
					oldMasDto.getStatus(), oldMasDto.getAuthRemark());*/
			
		
	}
	@Override
	public EquityGrantMasterDto fetchEquityDetailsbyAppId(Long valueOf) {
		// TODO Auto-generated method stub
		EquityGrantMasterDto equityGrantMasterDto = new EquityGrantMasterDto();
		EquityGrantMasterEntity equityGrantMasterEntity =  equityGrantMasterRepository.findByAppNumber(valueOf);
		
		FPOMasterDto fpoMasterDto = getFPODetails(equityGrantMasterEntity.getFpoName());
		BeanUtils.copyProperties(equityGrantMasterEntity, equityGrantMasterDto);
		equityGrantMasterDto.setFpoMasterDto(fpoMasterDto);
		
		List<EquityGrantDetailDto> equityGrantDetailDtos = new ArrayList<>();
		List<EquityGrantDetailDto> equityGrantDetailDtosBOM = new ArrayList<>();
		for(EquityGrantDetailEntity equityGrantDetailEntity : equityGrantDetailRepository.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantDetailDto equityGrantDetailDto = new EquityGrantDetailDto();
			if(equityGrantDetailEntity.getIsBOM().equalsIgnoreCase("N")) {
			BeanUtils.copyProperties(equityGrantDetailEntity, equityGrantDetailDto);
			equityGrantDetailDtos.add(equityGrantDetailDto);
			}else {
				
				BeanUtils.copyProperties(equityGrantDetailEntity, equityGrantDetailDto);
				equityGrantDetailDtosBOM.add(equityGrantDetailDto);
			}
		}
		
		List<EquityGrantFunctionalCommitteeDetailDto> equityGrantFunctionalCommitteeDetailDtos = new ArrayList<>();
		for(EquityGrantFuctionalCommitteeDetailEntity equityGrantFuctionalCommitteeDetailEntity : equityGrantFunctionalCommitteeDetailRepostory.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantFunctionalCommitteeDetailDto equityGrantFunctionalCommitteeDetailDto = new EquityGrantFunctionalCommitteeDetailDto();
		
				
				BeanUtils.copyProperties(equityGrantFuctionalCommitteeDetailEntity, equityGrantFunctionalCommitteeDetailDto);
				equityGrantFunctionalCommitteeDetailDtos.add(equityGrantFunctionalCommitteeDetailDto);
			
		}
		
		List<EquityGrantShareHoldingDetailDto> equityGrantShareHoldingDetailDtos = new ArrayList<>();
		for(EquityGrantShareHoldingDetailEntity equityGrantShareHoldingDetailEntity : equityGrantShareholdingDetailRepository.findByMasterEntity(equityGrantMasterEntity)){
			EquityGrantShareHoldingDetailDto equityGrantShareHoldingDetailDto = new EquityGrantShareHoldingDetailDto();
		
				
				BeanUtils.copyProperties(equityGrantShareHoldingDetailEntity, equityGrantShareHoldingDetailDto);
				equityGrantShareHoldingDetailDtos.add(equityGrantShareHoldingDetailDto);
			
		}



		
		/*FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));*/

		equityGrantMasterDto.setEquityGrantDetailDto(equityGrantDetailDtos);
		equityGrantMasterDto.setEquityGrantDetailDtoBOM(equityGrantDetailDtosBOM);
		equityGrantMasterDto.setEquityGrantFunctionalCommitteeDetailDtos(equityGrantFunctionalCommitteeDetailDtos);
		equityGrantMasterDto.setEquityGrantShareHoldingDetailDtos(equityGrantShareHoldingDetailDtos);

		return equityGrantMasterDto;
	}

}
