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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
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
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostDetailEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostDocDetailEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterEntity;
import com.abm.mainet.sfac.dto.FPOManagementCostDetailDTO;
import com.abm.mainet.sfac.dto.FPOManagementCostDocDetailDTO;
import com.abm.mainet.sfac.dto.FPOManagementCostMasterDTO;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.FPOManagementCostDetailRepository;
import com.abm.mainet.sfac.repository.FPOManagementCostDocDetailRepository;
import com.abm.mainet.sfac.repository.FPOManagementCostMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.IAMasterRepository;

@Service
public class FPOManagementCostMasterServiceImpl implements FPOManagementCostMasterService{

	@Autowired FPOManagementCostMasterRepository fpoManagementCostMasterRepository;

	@Autowired FPOManagementCostDetailRepository fpoManagementCostDetailRepository;

	@Autowired FPOManagementCostDocDetailRepository fpoManagementCostDocDetailRepository;

	@Autowired FPOMasterRepository fpoMasterRepository;

	@Autowired CBBOMasterRepository cbboMasterRepository;

	@Autowired IAMasterRepository iaMasterRepository;

	@Autowired IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private AttachDocsRepository attachDocsRepository;

	private static final Logger logger = Logger.getLogger(FPOManagementCostMasterServiceImpl.class);

	@Override
	public List<FPOManagementCostMasterDTO> getAppliacationDetails(Long fpoId, Long cbboid, Long iaId, Long fyId) {
		// TODO Auto-generated method stub
		List<FPOManagementCostMasterEntity> fpoManagementCostMasterEntities = new ArrayList<FPOManagementCostMasterEntity>();
		List<FPOManagementCostMasterDTO> fpoManagementCostMasterDTOs = new ArrayList<>();
		FPOMasterEntity fpoMasterEntity = new FPOMasterEntity();
		if((fpoId!=null && fpoId != 0) && (cbboid!=null && cbboid!= 0) && (iaId!=null && iaId != 0) && (fyId!=null && fyId !=0)) {
			fpoMasterEntity = fpoMasterRepository.findOne(fpoId);
			CBBOMasterEntity cbboMasterEntity = cbboMasterRepository.findOne(cbboid);
			IAMasterEntity iaMasterEntity =  iaMasterRepository.findOne(iaId);

			fpoManagementCostMasterEntities = fpoManagementCostMasterRepository.findByFpoMasterEntityAndCbboMasterEntityAndIaMasterEntityAndFinancialYear(fpoMasterEntity, cbboMasterEntity, iaMasterEntity, fyId);
		}
		else if((fpoId!=null && fpoId != 0) && (cbboid!=null && cbboid!= 0) && (iaId!=null && iaId != 0) ) {
			fpoMasterEntity = fpoMasterRepository.findOne(fpoId);
			CBBOMasterEntity cbboMasterEntity = cbboMasterRepository.findOne(cbboid);
			IAMasterEntity iaMasterEntity =  iaMasterRepository.findOne(iaId);
			fpoManagementCostMasterEntities = fpoManagementCostMasterRepository.findByFpoMasterEntityAndCbboMasterEntityAndIaMasterEntity(fpoMasterEntity, cbboMasterEntity, iaMasterEntity);

		}
		else if((fpoId!=null && fpoId != 0) && (cbboid!=null && cbboid!= 0)  && (fyId!=null && fyId !=0)) {
			fpoMasterEntity = fpoMasterRepository.findOne(fpoId);
			CBBOMasterEntity cbboMasterEntity = cbboMasterRepository.findOne(cbboid);
			fpoManagementCostMasterEntities = fpoManagementCostMasterRepository.findByFpoMasterEntityAndCbboMasterEntityAndFinancialYear(fpoMasterEntity, cbboMasterEntity, fyId);

		}
		else if((fpoId!=null && fpoId != 0) && (cbboid!=null && cbboid!= 0) ) {
			fpoMasterEntity = fpoMasterRepository.findOne(fpoId);
			CBBOMasterEntity cbboMasterEntity = cbboMasterRepository.findOne(cbboid);
			fpoManagementCostMasterEntities = fpoManagementCostMasterRepository.findByFpoMasterEntityAndCbboMasterEntity(fpoMasterEntity, cbboMasterEntity);

		}

		for (FPOManagementCostMasterEntity entity : fpoManagementCostMasterEntities) {
			FPOManagementCostMasterDTO dto = new FPOManagementCostMasterDTO();
			BigDecimal totalCost = new BigDecimal(0L) ;
			List<FPOManagementCostDetailEntity> fpoManagementCostDetailEntities =  fpoManagementCostDetailRepository.findByFpoManagementCostMasterEntity(entity);
			for(FPOManagementCostDetailEntity fpoManagementCostDetailEntity : fpoManagementCostDetailEntities) {
				
			totalCost =	totalCost.add(fpoManagementCostDetailEntity.getManagementCostIncurred());
			}
			FPOMasterDto fpoMasterDto = new FPOMasterDto();
			BeanUtils.copyProperties(entity, dto);
			BeanUtils.copyProperties(fpoMasterEntity, fpoMasterDto);
			dto.setFoFpoMasterDto(fpoMasterDto);
			dto.setTotalCostApproved(totalCost);
			fpoManagementCostMasterDTOs.add(dto);
		}

		return fpoManagementCostMasterDTOs;
	}

	@Transactional
	@Override
	public FPOManagementCostMasterDTO saveAndUpdateApplication(FPOManagementCostMasterDTO mastDto, List<Long> removedIds) {
		try {
			logger.info("saveAndUpdateApplication started");

			if (removedIds != null && !removedIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				fpoManagementCostDocDetailRepository.deActiveBPInfo(removedIds,
						UserSession.getCurrent().getEmployee().getEmpId());

			}

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("FMC",UserSession.getCurrent().getOrganisation().getOrgid());


			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);


			FPOManagementCostMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = fpoManagementCostMasterRepository.save(masEntity);
			mastDto.setFmcId(masEntity.getFmcId());
			int count = 0;
			for (FPOManagementCostDocDetailEntity fpoManagementCostDocDetailEntity : masEntity.getFpoManagementCostDocDetailEntities()) {
				List<DocumentDetailsVO> attach = new ArrayList<>();



				attach.add(mastDto.getFpoManagementCostDocDetailDTOs().get(count).getAttachments().get(count));

				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(fpoManagementCostDocDetailEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("FMC_DOC_LD" + MainetConstants.WINDOWS_SLASH + fpoManagementCostDocDetailEntity.getDocId());
				requestDTO.setUserId(fpoManagementCostDocDetailEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
				count++;
				// history table update

			}
			/*	for(FPOManagementCostDocDetailDTO managementCostDocDetailDTO :  mastDto.getFpoManagementCostDocDetailDTOs())
				updateLegacyDocuments(managementCostDocDetailDTO.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());*/

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(mastDto.getApplicationNumber());
				applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				mastDto.getApplicantDetailDto().setUserId(mastDto.getCreatedBy());
				mastDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				mastDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
				mastDto.getApplicantDetailDto().setExtIdentifier(mastDto.getFoFpoMasterDto().getCbboId());
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

			for(FPOManagementCostDocDetailDTO managementCostDocDetailDTO :  mastDto.getFpoManagementCostDocDetailDTOs())
				updateLegacyDocuments(managementCostDocDetailDTO.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());


		} catch (Exception e) {
			logger.error("error occured while saving fpo management cost master  details" + e);
			throw new FrameworkException("error occured while saving fpo management cost details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	

	
	private RequestDTO setApplicantRequestDto(FPOManagementCostMasterDTO masDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(masDto.getCreatedBy());

		requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		
		// setting applicant info
		requestDto.setfName(UserSession.getCurrent().getEmployee().getEmpname());
		if(UserSession.getCurrent().getEmployee().getEmpemail()!=null)
			requestDto.setEmail(UserSession.getCurrent().getEmployee().getEmpmobno());
		if(UserSession.getCurrent().getEmployee().getEmpmobno()!=null)
			requestDto.setMobileNo(UserSession.getCurrent().getEmployee().getEmpemail());
		if (sm.getSmFeesSchedule() == 0) {
			requestDto.setPayStatus("F");
		} else {
			requestDto.setPayStatus("Y");
		}
		return requestDto;
	}

	private FPOManagementCostMasterEntity mapDtoToEntity(FPOManagementCostMasterDTO mastDto) {
		FPOManagementCostMasterEntity masEntity = new FPOManagementCostMasterEntity();
		List<FPOManagementCostDetailEntity> detailsList = new ArrayList<>();
		List<FPOManagementCostDocDetailEntity> docDetailtList = new ArrayList<>();


		BeanUtils.copyProperties(mastDto, masEntity);
		masEntity.setCbboMasterEntity(cbboMasterRepository.findOne(mastDto.getFoFpoMasterDto().getCbboId()));
		masEntity.setFpoMasterEntity(fpoMasterRepository.findOne(mastDto.getFoFpoMasterDto().getFpoId()));
		masEntity.setIaMasterEntity(iaMasterRepository.findOne(mastDto.getFoFpoMasterDto().getIaId()));

		mastDto.getFpoManagementCostDetailDTOs().forEach(dto -> {
			FPOManagementCostDetailEntity entity = new FPOManagementCostDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoManagementCostMasterEntity(masEntity);
			detailsList.add(entity);
		});
		mastDto.getFpoManagementCostDocDetailDTOs().forEach(bankDto -> {
			FPOManagementCostDocDetailEntity entity = new FPOManagementCostDocDetailEntity();
			BeanUtils.copyProperties(bankDto, entity);

			entity.setFpoManagementCostMasterEntity(masEntity);

			docDetailtList.add(entity);
		});


		if (CollectionUtils.isNotEmpty(mastDto.getFpoManagementCostDetailDTOs()) && mastDto.getFpoManagementCostDetailDTOs().get(0).getParticulars()!=null)
			masEntity.setFpoManagementCostDetailEntities(detailsList);
		if (CollectionUtils.isNotEmpty(mastDto.getFpoManagementCostDocDetailDTOs()) && StringUtils.isNotEmpty(mastDto.getFpoManagementCostDocDetailDTOs().get(0).getDocumentDescription()))	
			masEntity.setFpoManagementCostDocDetailEntities(docDetailtList);

		return masEntity;
	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}

	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
	}

	public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
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

	@Override
	public FPOManagementCostMasterDTO getDetailById(Long fmcId) {
		// TODO Auto-generated method stub
		FPOManagementCostMasterDTO fpoManagementCostMasterDTO = new FPOManagementCostMasterDTO();

		FPOManagementCostMasterEntity fpoManagementCostMasterEntity =  fpoManagementCostMasterRepository.findOne(fmcId);

		FPOMasterDto fpoMasterDto = new FPOMasterDto();

		BeanUtils.copyProperties(fpoManagementCostMasterEntity.getFpoMasterEntity(), fpoMasterDto);

		BeanUtils.copyProperties(fpoManagementCostMasterEntity, fpoManagementCostMasterDTO);
		List<FPOManagementCostDetailDTO> fpoManagementCostDetailDTOs = new ArrayList<>();
		for(FPOManagementCostDetailEntity fpoManagementCostDetailEntity : fpoManagementCostDetailRepository.findByFpoManagementCostMasterEntity(fpoManagementCostMasterEntity)){

			FPOManagementCostDetailDTO fpoManagementCostDetailDTO = new FPOManagementCostDetailDTO();
			BeanUtils.copyProperties(fpoManagementCostDetailEntity, fpoManagementCostDetailDTO);
			fpoManagementCostDetailDTOs.add(fpoManagementCostDetailDTO);
		}

		List<FPOManagementCostDocDetailDTO> fpoManagementCostDocDetailDTOs = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();
		for(FPOManagementCostDocDetailEntity fpoManagementCostDocDetailEntity : fpoManagementCostDocDetailRepository.findByFpoManagementCostMasterEntityAndStatus(fpoManagementCostMasterEntity,MainetConstants.FlagA)){

			FPOManagementCostDocDetailDTO fpoManagementCostDetailDTO = new FPOManagementCostDocDetailDTO();
			BeanUtils.copyProperties(fpoManagementCostDocDetailEntity, fpoManagementCostDetailDTO);




			List<String> identifer = new ArrayList<>();
			identifer.add("FMC_DOC_LD" + MainetConstants.WINDOWS_SLASH + fpoManagementCostDocDetailEntity.getDocId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(fpoManagementCostDocDetailEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			fpoManagementCostDetailDTO.setAttachDocsList(attachDocs);

			fpoManagementCostDocDetailDTOs.add(fpoManagementCostDetailDTO);
			count++;


		}

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		fpoManagementCostMasterDTO.setFoFpoMasterDto(fpoMasterDto);
		fpoManagementCostMasterDTO.setFpoManagementCostDetailDTOs(fpoManagementCostDetailDTOs);
		fpoManagementCostMasterDTO.setFpoManagementCostDocDetailDTOs(fpoManagementCostDocDetailDTOs);

		return fpoManagementCostMasterDTO;
	}

	@Override
	public FPOMasterDto getFPODetails(Long masId) {
		// TODO Auto-generated method stub
		FPOMasterDto fpoMasterDto = new FPOMasterDto();
		BeanUtils.copyProperties(fpoMasterRepository.findOne(masId), fpoMasterDto);

		return fpoMasterDto;
	}

	@Transactional
	@Override
	public FPOManagementCostMasterDTO updateApplication(FPOManagementCostMasterDTO mastDto, List<Long> removedIds) {
		try {
			logger.info("saveAndUpdateApplication started");
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode("EGR",
							orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());

			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);



			fpoManagementCostDetailRepository.save(mapDtoToEntityDetails(mastDto));

			List<FPOManagementCostDocDetailEntity> fpoManagementCostDocDetailEntities =  fpoManagementCostDocDetailRepository.save(mapDtoToEntityDocDetails(mastDto));

			if (removedIds != null && !removedIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				fpoManagementCostDocDetailRepository.deActiveBPInfo(removedIds,
						UserSession.getCurrent().getEmployee().getEmpId());
			}

			int count = 0;
			for (FPOManagementCostDocDetailEntity fpoManagementCostDocDetailEntity : fpoManagementCostDocDetailEntities) {
				List<DocumentDetailsVO> attach = new ArrayList<>();



				attach.add(mastDto.getFpoManagementCostDocDetailDTOs().get(count).getAttachments().get(count));

				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(fpoManagementCostDocDetailEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("FMC_DOC_LD" + MainetConstants.WINDOWS_SLASH + fpoManagementCostDocDetailEntity.getDocId());
				requestDTO.setUserId(fpoManagementCostDocDetailEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
				count++;
				// history table update

			}
			for(FPOManagementCostDocDetailDTO managementCostDocDetailDTO :  mastDto.getFpoManagementCostDocDetailDTOs())
				updateLegacyDocuments(managementCostDocDetailDTO.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

			if (sm.getSmFeesSchedule() == 0) {
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(mastDto.getApplicationNumber());
				applicationData.setOrgId(orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
				LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify());
				if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA))
					applicationData.setIsCheckListApplicable(true);
				else
					applicationData.setIsCheckListApplicable(false);
				mastDto.getApplicantDetailDto().setUserId(mastDto.getCreatedBy());
				mastDto.getApplicantDetailDto().setServiceId(sm.getSmServiceId());
				mastDto.getApplicantDetailDto().setDepartmentId(Long.valueOf(sm.getTbDepartment().getDpDeptid()));
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
			logger.error("error occured while saving fpo master  details" + e);
			throw new FrameworkException("error occured while saving fpo master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private List<FPOManagementCostDetailEntity> mapDtoToEntityDetails(FPOManagementCostMasterDTO mastDto) {
		// TODO Auto-generated method stub
		FPOManagementCostMasterEntity masterEntity = fpoManagementCostMasterRepository.findOne(mastDto.getFmcId());
		masterEntity.setFinancialYear(mastDto.getFinancialYear());
		List<FPOManagementCostDetailEntity> fpoManagementCostDetailEntities = new ArrayList<>();

		mastDto.getFpoManagementCostDetailDTOs().forEach(dto -> {

			FPOManagementCostDetailEntity entity = new FPOManagementCostDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoManagementCostMasterEntity(masterEntity);
			fpoManagementCostDetailEntities.add(entity);

		});


		return fpoManagementCostDetailEntities;
	}

	private List<FPOManagementCostDocDetailEntity> mapDtoToEntityDocDetails(FPOManagementCostMasterDTO mastDto) {
		// TODO Auto-generated method stub
		FPOManagementCostMasterEntity masterEntity = fpoManagementCostMasterRepository.findOne(mastDto.getFmcId());
		masterEntity.setFinancialYear(mastDto.getFinancialYear());
		List<FPOManagementCostDocDetailEntity> fpoManagementCostDocDetailEntities = new ArrayList<>();

		mastDto.getFpoManagementCostDocDetailDTOs().forEach(dto -> {

			FPOManagementCostDocDetailEntity entity = new FPOManagementCostDocDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFpoManagementCostMasterEntity(masterEntity);
			fpoManagementCostDocDetailEntities.add(entity);

		});


		return fpoManagementCostDocDetailEntities;
	}

	@Override
	public FPOManagementCostMasterDTO fetchFPOManagementCostbyAppId(Long appNumber) {
		// TODO Auto-generated method stub
		FPOManagementCostMasterDTO fpoManagementCostMasterDTO = new FPOManagementCostMasterDTO();

		FPOManagementCostMasterEntity fpoManagementCostMasterEntity =  fpoManagementCostMasterRepository.findByApplicationNumber(appNumber);

		FPOMasterDto fpoMasterDto = new FPOMasterDto();

		BeanUtils.copyProperties(fpoManagementCostMasterEntity.getFpoMasterEntity(), fpoMasterDto);

		BeanUtils.copyProperties(fpoManagementCostMasterEntity, fpoManagementCostMasterDTO);
		List<FPOManagementCostDetailDTO> fpoManagementCostDetailDTOs = new ArrayList<>();
		for(FPOManagementCostDetailEntity fpoManagementCostDetailEntity : fpoManagementCostDetailRepository.findByFpoManagementCostMasterEntity(fpoManagementCostMasterEntity)){

			FPOManagementCostDetailDTO fpoManagementCostDetailDTO = new FPOManagementCostDetailDTO();
			BeanUtils.copyProperties(fpoManagementCostDetailEntity, fpoManagementCostDetailDTO);
			fpoManagementCostDetailDTOs.add(fpoManagementCostDetailDTO);
		}

		List<FPOManagementCostDocDetailDTO> fpoManagementCostDocDetailDTOs = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();
		for(FPOManagementCostDocDetailEntity fpoManagementCostDocDetailEntity : fpoManagementCostDocDetailRepository.findByFpoManagementCostMasterEntityAndStatus(fpoManagementCostMasterEntity,MainetConstants.FlagA)){

			FPOManagementCostDocDetailDTO fpoManagementCostDetailDTO = new FPOManagementCostDocDetailDTO();
			BeanUtils.copyProperties(fpoManagementCostDocDetailEntity, fpoManagementCostDetailDTO);




			List<String> identifer = new ArrayList<>();
			identifer.add("FMC_DOC_LD" + MainetConstants.WINDOWS_SLASH + fpoManagementCostDocDetailEntity.getDocId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(fpoManagementCostDocDetailEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			fpoManagementCostDetailDTO.setAttachDocsList(attachDocs);

			fpoManagementCostDocDetailDTOs.add(fpoManagementCostDetailDTO);
			count++;


		}

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		fpoManagementCostMasterDTO.setFoFpoMasterDto(fpoMasterDto);
		fpoManagementCostMasterDTO.setFpoManagementCostDetailDTOs(fpoManagementCostDetailDTOs);
		fpoManagementCostMasterDTO.setFpoManagementCostDocDetailDTOs(fpoManagementCostDocDetailDTOs);

		return fpoManagementCostMasterDTO;
	}

	@Override
	public void updateApprovalStatusAndRemark(FPOManagementCostMasterDTO oldMasDto, String lastDecision,
			String status) {

		FPOManagementCostMasterEntity fpoManagementCostMasterEntity = fpoManagementCostMasterRepository.findOne(oldMasDto.getFmcId());

		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			fpoManagementCostMasterEntity.setStatus(lastDecision);


		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			fpoManagementCostMasterEntity.setStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED)) {
			fpoManagementCostMasterEntity.setStatus(lastDecision);

		}




		fpoManagementCostMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());

		fpoManagementCostMasterRepository.save(fpoManagementCostMasterEntity);

		/*equityGrantMasterRepository.updateApprovalStatusAndRemark(oldMasDto.getEgId(), 
				oldMasDto.getAppStatus());*/
		/*allocationOfBlocksHistRepo.updateApprovalStatusAndRemHist(oldMasDto.getBlockId(), oldMasDto.getApplicationId(),
				oldMasDto.getStatus(), oldMasDto.getAuthRemark());*/


	}

}
