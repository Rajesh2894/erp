package com.abm.mainet.sfac.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.sfac.domain.FundReleaseRequestDetailEntity;
import com.abm.mainet.sfac.domain.FundReleaseRequestMasterEntity;
import com.abm.mainet.sfac.dto.FundReleaseRequestDetailDto;
import com.abm.mainet.sfac.dto.FundReleaseRequestMasterDto;
import com.abm.mainet.sfac.repository.FundReleaseRequestDetailRepository;
import com.abm.mainet.sfac.repository.FundReleaseRequestMasterRepository;

@Service
public class FundReleaseRequestServiceImpl implements FundReleaseRequestService{
	
	private static final Logger logger = Logger.getLogger(FundReleaseRequestServiceImpl.class);

	@Autowired FundReleaseRequestMasterRepository fundReleaseRequestMasterRepository;


	@Autowired FundReleaseRequestDetailRepository fundReleaseRequestDetailRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AttachDocsRepository attachDocsRepository;

	@Autowired
	private ApplicationService applicationService;

	@Override
	public FundReleaseRequestMasterDto getDetailById(Long frrId) {
		// TODO Auto-generated method stub
		FundReleaseRequestMasterDto fundReleaseRequestMasterDto = new FundReleaseRequestMasterDto();

		FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity =  fundReleaseRequestMasterRepository.findOne(frrId);

		

		BeanUtils.copyProperties(fundReleaseRequestMasterEntity, fundReleaseRequestMasterDto);
		List<FundReleaseRequestDetailDto> fundReleaseRequestDetailDtos = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();
		
		
		for(FundReleaseRequestDetailEntity fundReleaseRequestDetailEntity : fundReleaseRequestDetailRepository.findByFundReleaseRequestMasterEntity(fundReleaseRequestMasterEntity)){

			FundReleaseRequestDetailDto fundReleaseRequestDetailDto = new FundReleaseRequestDetailDto();
			BeanUtils.copyProperties(fundReleaseRequestDetailEntity, fundReleaseRequestDetailDto);
			
			List<String> identifer = new ArrayList<>();
			identifer.add("FRR_DOC_ID" + MainetConstants.WINDOWS_SLASH + fundReleaseRequestDetailEntity.getFrrdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(fundReleaseRequestDetailEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			fundReleaseRequestDetailDto.setAttachDocsList(attachDocs);

			fundReleaseRequestDetailDtos.add(fundReleaseRequestDetailDto);
			count++;
		}

	

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		fundReleaseRequestMasterDto.setFundReleaseRequestDetailDtos(fundReleaseRequestDetailDtos);
		

		return fundReleaseRequestMasterDto;
	}

	@Override
	public List<FundReleaseRequestMasterDto> getFundReleaseReqDetails(Long iaId, String applicationRef, Long fy) {
		// TODO Auto-generated method stub
		List<FundReleaseRequestMasterDto> fundReleaseRequestMasterDtos = new  ArrayList<>();
		List<FundReleaseRequestMasterEntity> fundReleaseRequestMasterEntities = new  ArrayList<>();
		if(applicationRef!=null && !applicationRef.isEmpty() && fy !=null && fy != 0 )
			fundReleaseRequestMasterEntities = fundReleaseRequestMasterRepository.findByIaIdAndFinancialYearAndFileReferenceNumber(iaId,fy,applicationRef);
		else if(fy!=null && fy!=0)
			fundReleaseRequestMasterEntities = fundReleaseRequestMasterRepository.findByIaIdAndFinancialYear(iaId,fy);
		else if(applicationRef!=null && !applicationRef.isEmpty())
			fundReleaseRequestMasterEntities = fundReleaseRequestMasterRepository.findByIaIdAndFileReferenceNumber(iaId,applicationRef);

		for(FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity : fundReleaseRequestMasterEntities) {
			FundReleaseRequestMasterDto fundReleaseRequestMasterDto = new FundReleaseRequestMasterDto();
			BeanUtils.copyProperties(fundReleaseRequestMasterEntity, fundReleaseRequestMasterDto);
			fundReleaseRequestMasterDtos.add(fundReleaseRequestMasterDto);
		}
		
		return fundReleaseRequestMasterDtos;
	}
	
	private void saveDocuments(List<DocumentDetailsVO> attach, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attach,
				requestDTO);
	}
	
	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
	}

	@Override
	public FundReleaseRequestMasterDto saveAndUpdateApplication(FundReleaseRequestMasterDto mastDto, List<Long> removedIds) {
		try {
			
			if (removedIds != null && !removedIds.isEmpty()) {
				// update with DEACTIVE status on removeIds
				fundReleaseRequestDetailRepository.deActiveBPInfo(removedIds,
						UserSession.getCurrent().getEmployee().getEmpId());

			}

			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.Sfac.FUND_REL_BPM_SHORTCODE,UserSession.getCurrent().getOrganisation().getOrgid());


			RequestDTO requestDto = setApplicantRequestDto(mastDto, sm);
			Long applicationId = applicationService.createApplication(requestDto);
			if (applicationId != null)
				mastDto.setApplicationNumber(applicationId);


			FundReleaseRequestMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = fundReleaseRequestMasterRepository.save(masEntity);
			mastDto.setFrrId(masEntity.getFrrId());

			
			int count = 0;

			for (FundReleaseRequestDetailEntity fundReleaseRequestDetailEntity : masEntity.getFundReleaseRequestDetailEntities()) {
				List<DocumentDetailsVO> attach = new ArrayList<>();
				attach.add(mastDto.getFundReleaseRequestDetailDtos().get(count).getAttachments().get(count));
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(fundReleaseRequestDetailEntity.getOrgId());
				requestDTO.setDepartmentName(MainetConstants.ENV_SFAC);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("FRR_DOC_ID" + MainetConstants.WINDOWS_SLASH + fundReleaseRequestDetailEntity.getFrrdId());
				requestDTO.setUserId(fundReleaseRequestDetailEntity.getCreatedBy());
				saveDocuments(attach, requestDTO);
				count++;
				// history table update

			}

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
				
				if (requestDto != null && requestDto.getMobileNo() != null) {
					mastDto.getApplicantDetailDto().setMobileNo(requestDto.getMobileNo());
				}

				if (sm.getSmFeesSchedule().longValue() == 0) {
					applicationData.setIsLoiApplicable(false);
				} else {
					applicationData.setIsLoiApplicable(true);
				}
				
				for(FundReleaseRequestDetailDto fundReleaseRequestDetailDto :  mastDto.getFundReleaseRequestDetailDtos())
					updateLegacyDocuments(fundReleaseRequestDetailDto.getAttachDocsList(), UserSession.getCurrent().getEmployee().getEmpId());

				commonService.initiateWorkflowfreeService(applicationData, mastDto.getApplicantDetailDto());

			}




		} catch (Exception e) {
			logger.error("error occured while saving Fund Release Req master  details" + e);
			throw new FrameworkException("error occured while saving Fund Release Req master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private RequestDTO setApplicantRequestDto(FundReleaseRequestMasterDto mastDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(mastDto.getCreatedBy());

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

	private FundReleaseRequestMasterEntity mapDtoToEntity(FundReleaseRequestMasterDto mastDto) {
		FundReleaseRequestMasterEntity masEntity = new FundReleaseRequestMasterEntity();
		List<FundReleaseRequestDetailEntity> detailsList = new ArrayList<>();


		BeanUtils.copyProperties(mastDto, masEntity);

		mastDto.getFundReleaseRequestDetailDtos().forEach(dto -> {
			FundReleaseRequestDetailEntity entity = new FundReleaseRequestDetailEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setFundReleaseRequestMasterEntity(masEntity);
			detailsList.add(entity);
		});


		if (CollectionUtils.isNotEmpty(mastDto.getFundReleaseRequestDetailDtos()))
			masEntity.setFundReleaseRequestDetailEntities(detailsList);
		

		return masEntity;
	}

	@Override
	public FundReleaseRequestMasterDto fetchFundReleasedReqDetailbyAppId(Long appNumber) {
		// TODO Auto-generated method stub
		FundReleaseRequestMasterDto fundReleaseRequestMasterDto = new FundReleaseRequestMasterDto();

		FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity =  fundReleaseRequestMasterRepository.findByApplicationNumber(appNumber);

		

		BeanUtils.copyProperties(fundReleaseRequestMasterEntity, fundReleaseRequestMasterDto);
		List<FundReleaseRequestDetailDto> fundReleaseRequestDetailDtos = new ArrayList<>();
		Long count = 0L;
		List<AttachDocs> finalAttachList = new ArrayList<>();
		
		
		for(FundReleaseRequestDetailEntity fundReleaseRequestDetailEntity : fundReleaseRequestDetailRepository.findByFundReleaseRequestMasterEntity(fundReleaseRequestMasterEntity)){

			FundReleaseRequestDetailDto fundReleaseRequestDetailDto = new FundReleaseRequestDetailDto();
			BeanUtils.copyProperties(fundReleaseRequestDetailEntity, fundReleaseRequestDetailDto);
			
			List<String> identifer = new ArrayList<>();
			identifer.add("FRR_DOC_ID" + MainetConstants.WINDOWS_SLASH + fundReleaseRequestDetailEntity.getFrrdId());

			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(fundReleaseRequestDetailEntity.getOrgId(), identifer);
			if (!attachDocs.isEmpty()) {
				for(AttachDocs attachDocs2 : attachDocs) {
					finalAttachList.add(attachDocs2);
				}

			}
			fundReleaseRequestDetailDto.setAttachDocsList(attachDocs);

			fundReleaseRequestDetailDtos.add(fundReleaseRequestDetailDto);
			count++;
		}

	

		FileUploadUtility.getCurrent().setFileMap(
				getUploadedFileList(finalAttachList, FileNetApplicationClient.getInstance()));
		fundReleaseRequestMasterDto.setFundReleaseRequestDetailDtos(fundReleaseRequestDetailDtos);
		

		return fundReleaseRequestMasterDto;
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
	public void updateApprovalStatusAndRemark(FundReleaseRequestMasterDto oldMasDto, String lastDecision,
			String status) {

		FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity = fundReleaseRequestMasterRepository.findOne(oldMasDto.getFrrId());

		if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_REJECTED)) {
			fundReleaseRequestMasterEntity.setStatus(lastDecision);


		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)) {
			fundReleaseRequestMasterEntity.setStatus(status);

		} else if (lastDecision.equalsIgnoreCase(MainetConstants.TASK_STATUS_APPROVED)
				&& status.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED)) {
			fundReleaseRequestMasterEntity.setStatus(lastDecision);

		}

		fundReleaseRequestMasterEntity.setAuthRemark(oldMasDto.getAuthRemark());

		fundReleaseRequestMasterRepository.save(fundReleaseRequestMasterEntity);


	}

}
