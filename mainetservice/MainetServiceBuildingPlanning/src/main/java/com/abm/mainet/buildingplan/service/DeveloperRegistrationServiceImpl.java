package com.abm.mainet.buildingplan.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.abm.mainet.buildingplan.domain.TbAuthUserMasEntity;
import com.abm.mainet.buildingplan.domain.TbDevPerGrntMasEntity;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.buildingplan.domain.TbDirectorMasEntity;
import com.abm.mainet.buildingplan.domain.TbStkhldrMasEntity;
import com.abm.mainet.buildingplan.dto.DevLicenseHDRUDTO;
import com.abm.mainet.buildingplan.dto.DeveloperAuthorizedUserDTO;
import com.abm.mainet.buildingplan.dto.DeveloperDirectorInfoDTO;
import com.abm.mainet.buildingplan.dto.DeveloperRegistrationDTO;
import com.abm.mainet.buildingplan.dto.DeveloperStakeholderDTO;
import com.abm.mainet.buildingplan.repository.DevRegMasRepository;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.repository.AttachDocsRepository;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.buildingplan.service.IDeveloperRegistrationService")
@Api(value = "/developerRegistrationService")
@Path("/developerRegistrationService")
public class DeveloperRegistrationServiceImpl implements IDeveloperRegistrationService {
	private static final Logger LOGGER = Logger.getLogger(DeveloperRegistrationServiceImpl.class);
	
	@Resource
	private IFileUploadService fileUploadService;
	
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Autowired
	private AttachDocsRepository attachDocsRepository;
	
	@Resource
	private EmployeeJpaRepository employeeJpaRepository;
	
	
	@Autowired
	private IAttachDocsService iAttachDocsService;
	
	@Resource
	private DevRegMasRepository devRegMasRepository;
	
	@Resource
	private IEmployeeService employeeService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	
	@Override
    @Transactional
    @POST
    @Path("/saveDeveloperRegForm")
    public DeveloperRegistrationDTO saveDeveloperRegForm(@RequestBody final DeveloperRegistrationDTO developerRegistrationDTO, Employee loggedInUser, String lgIpMacUpd, Long orgId, Long serviceId, String deptCode) {
		DeveloperRegistrationDTO devRegistrationDTO = new DeveloperRegistrationDTO();
		try {
			final TbDeveloperRegistrationEntity developerRegistrationEntity = new TbDeveloperRegistrationEntity();	
			
			if(developerRegistrationDTO.getTcp_dev_appno()==null){
				final Long devRegNumber = seqGenFunctionUtility.generateSequenceNo("TCP",
	                    "tb_bpms_dev_reg", "tcp_dev_appno", orgId, "C", null);
				if(devRegNumber!=null){
					developerRegistrationDTO.setTcp_dev_appno(devRegNumber);
				}
			}
			BeanUtils.copyProperties(developerRegistrationDTO, developerRegistrationEntity);
			
			if(!developerRegistrationDTO.getDeveloperStakeholderDTOList().isEmpty()){
				List<TbStkhldrMasEntity> stkhldrMasEntityList = new ArrayList<>();
				developerRegistrationDTO.getDeveloperStakeholderDTOList().forEach(stakeholderDTOList->{
					TbStkhldrMasEntity stkhldrMasEntity = new TbStkhldrMasEntity();
					stakeholderDTOList.setCreatedBy(loggedInUser.getEmpId());
					stakeholderDTOList.setCreatedDate(new Date());
					BeanUtils.copyProperties(stakeholderDTOList, stkhldrMasEntity);
					stkhldrMasEntity.setDeveloperRegMas(developerRegistrationEntity);
					stkhldrMasEntityList.add(stkhldrMasEntity);
				});
				developerRegistrationEntity.setDeveloperStakeholderDTOList(stkhldrMasEntityList);
			}
			
			if(!developerRegistrationDTO.getDeveloperDirectorInfoDTOList().isEmpty()){
				List<TbDirectorMasEntity> directorMasEntityList = new ArrayList<>();
				developerRegistrationDTO.getDeveloperDirectorInfoDTOList().forEach(developerDirectorInfoDTOList->{
					TbDirectorMasEntity directorMasEntity = new TbDirectorMasEntity();
					developerDirectorInfoDTOList.setCreatedBy(loggedInUser.getEmpId());
					developerDirectorInfoDTOList.setCreatedDate(new Date());
					BeanUtils.copyProperties(developerDirectorInfoDTOList, directorMasEntity);
					directorMasEntity.setDeveloperRegMas(developerRegistrationEntity);
					directorMasEntityList.add(directorMasEntity);
				});
				developerRegistrationEntity.setDeveloperDirectorInfoDTOList(directorMasEntityList);
			}
			
			if(!developerRegistrationDTO.getDeveloperAuthorizedUserDTOList().isEmpty()){
				List<TbAuthUserMasEntity> authUserMasEntityList = new ArrayList<>();
				developerRegistrationDTO.getDeveloperAuthorizedUserDTOList().forEach(developerAuthorizedUserDTOList->{
					TbAuthUserMasEntity authUserMasEntity = new TbAuthUserMasEntity();
					developerAuthorizedUserDTOList.setCreatedBy(loggedInUser.getEmpId());
					developerAuthorizedUserDTOList.setCreatedDate(new Date());
					BeanUtils.copyProperties(developerAuthorizedUserDTOList, authUserMasEntity);
					authUserMasEntity.setDeveloperRegMas(developerRegistrationEntity);
					authUserMasEntityList.add(authUserMasEntity);
				});
				developerRegistrationEntity.setDeveloperAuthorizedUserDTOList(authUserMasEntityList);
			}
		
			if(!developerRegistrationDTO.getDevLicenseHDRUDTOList().isEmpty()){
				List<TbDevPerGrntMasEntity> devPerGrntMasEntityList = new ArrayList<>();
				developerRegistrationDTO.getDevLicenseHDRUDTOList().forEach(devLicenseHDRUDTOList->{
					TbDevPerGrntMasEntity devPerGrntMasEntity = new TbDevPerGrntMasEntity();
					BeanUtils.copyProperties(devLicenseHDRUDTOList, devPerGrntMasEntity);
					devPerGrntMasEntity.setDeveloperRegMas(developerRegistrationEntity);
					devPerGrntMasEntityList.add(devPerGrntMasEntity);
				});
				developerRegistrationEntity.setDevLicenseHDRUDTOList(devPerGrntMasEntityList);
			}		
			
			TbDeveloperRegistrationEntity savedDeveloperRegistrationEntity = devRegMasRepository.save(developerRegistrationEntity);			
			devRegistrationDTO = mapDevRegEntityToDTO(savedDeveloperRegistrationEntity, developerRegistrationDTO);										
			
			employeeService.updateEmployeeName(developerRegistrationDTO.getCompanyName(), loggedInUser.getEmpId());
			
			/** Add Authorized Users To Employee Master*/ 
			List<DeveloperAuthorizedUserDTO> authorizedUserList = developerRegistrationDTO.getDeveloperAuthorizedUserDTOList();
			List<Employee> employeeList = new ArrayList<>();
			Organisation organisation = new Organisation();
			organisation.setOrgid(orgId);
			if(!authorizedUserList.isEmpty()){
				authorizedUserList.forEach(authorizedUser -> {
					if(authorizedUser.getAuthUserName()!=null){
						List<Employee> activeEmployeeByEmpMobileNo = employeeJpaRepository.getActiveEmployeeByEmpMobileNo(authorizedUser.getAuthMobileNo().toString());
						if(activeEmployeeByEmpMobileNo.isEmpty()){
						Employee employeeEntity = new Employee();
						employeeEntity.setEmpname(authorizedUser.getAuthUserName());
						employeeEntity.setEmpmobno(authorizedUser.getAuthMobileNo().toString());
						employeeEntity.setEmpemail(authorizedUser.getAuthEmail());
						employeeEntity.setEmpGender(CommonMasterUtility.getNonHierarchicalLookUpObject(authorizedUser.getAuthGender(),
								organisation).getLookUpCode());
						employeeEntity.setEmpdob(authorizedUser.getAuthDOB());
						employeeEntity.setPanNo(authorizedUser.getAuthPanNumber());
						employeeEntity.setIsDeleted("0");
						employeeEntity.setUserId(loggedInUser.getEmpId());
						employeeEntity.setLmodDate(new Date());
						employeeEntity.setLgIpMac(loggedInUser.getEmppiservername());
						//employeeEntity.setEmployeeNo(loggedInUser.getEmpId().toString());
						employeeEntity.setEmploginname(authorizedUser.getAuthUserName());
						employeeEntity.setReportingManager(loggedInUser.getEmpId());
						employeeEntity.setOrganisation(loggedInUser.getOrganisation());
						employeeEntity.setTbDepartment(loggedInUser.getTbDepartment());
						employeeEntity.setDesignation(loggedInUser.getDesignation());
						employeeEntity.setTbLocationMas(loggedInUser.getTbLocationMas());
						employeeEntity.setGmid(employeeService.getGroupIdByGroupCode(
								loggedInUser.getOrganisation().getOrgid(), MainetConstants.Role.DEVLOPER_AUTH_USER));
						employeeEntity.setAuthStatus(MainetConstants.FlagA);
						employeeEntity.setIsuploaded(MainetConstants.FlagY);
						employeeEntity.setAutMob(MainetConstants.FlagY);
						employeeList.add(employeeEntity);
						
						/** Post Authorized Users To SSORegistration*/
						final String uri = ApplicationSession.getInstance().getMessage("sso.registration.employee.push.url");
						/*authorizedUserList.forEach(authorizedUser -> {*/
							try {
								Map<String, String> dataMap = new LinkedHashMap<>();
								dataMap.put("EmailID", authorizedUser.getAuthEmail());
								dataMap.put("Mobile", authorizedUser.getAuthMobileNo().toString());
								dataMap.put("DeveloperName", authorizedUser.getAuthUserName());
								LOGGER.info("Ready to Post Data for " + authorizedUser.getAuthUserName());
								final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(dataMap, uri);				
								LOGGER.info("Successfully Posted Data and Response " + responseEntity.getBody());
							} catch (Exception e) {
								LOGGER.error("Exception occured while Posting " + authorizedUser.getAuthEmail() + " " + e);
							}
						/*});*/
						}
					}					
				});
			}
			
			employeeJpaRepository.save(employeeList);

			

		} catch (Exception e) {
			LOGGER.error("In saving new saveDeveloperRegForm()", e);
			throw new FrameworkException("Error in saveDeveloperDetails() ", e);
		}		
		return devRegistrationDTO;		
	}
	
	private DeveloperRegistrationDTO mapDevRegEntityToDTO(TbDeveloperRegistrationEntity developerRegistrationEntity, DeveloperRegistrationDTO developerRegistrationDTO) {	
		DeveloperRegistrationDTO devRegDto = new DeveloperRegistrationDTO();
		BeanUtils.copyProperties(developerRegistrationEntity, devRegDto);
		
		if(!developerRegistrationEntity.getDeveloperStakeholderDTOList().isEmpty()){
			List<DeveloperStakeholderDTO> stkhldrMasDtoList = new ArrayList<>();
			developerRegistrationEntity.getDeveloperStakeholderDTOList().forEach(stakeholderEntityList->{
				DeveloperStakeholderDTO stkhldrMasDto = new DeveloperStakeholderDTO();
				BeanUtils.copyProperties(stakeholderEntityList, stkhldrMasDto);
				stkhldrMasDto.setStakeholderDoc(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				stkhldrMasDto.setDeveloperRegMas(devRegDto);
				developerRegistrationDTO.getDeveloperStakeholderDTOList().forEach(stkDoc->{
					if(!stkDoc.getStakeholderDocsList().isEmpty()){
						stkhldrMasDto.setStakeholderDocsList(stkDoc.getStakeholderDocsList());
					}
				});
				stkhldrMasDtoList.add(stkhldrMasDto);
			});
			devRegDto.setDeveloperStakeholderDTOList(stkhldrMasDtoList);
		}
		
		if(!developerRegistrationEntity.getDeveloperDirectorInfoDTOList().isEmpty()){
			List<DeveloperDirectorInfoDTO> directorMasDtoList = new ArrayList<>();
			developerRegistrationEntity.getDeveloperDirectorInfoDTOList().forEach(developerDirectorEntityList->{
				DeveloperDirectorInfoDTO directorMasDto = new DeveloperDirectorInfoDTO();
				BeanUtils.copyProperties(developerDirectorEntityList, directorMasDto);
				directorMasDto.setDirectorDoc(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				directorMasDto.setDeveloperRegMas(devRegDto);
				developerRegistrationDTO.getDeveloperDirectorInfoDTOList().forEach(dirDoc->{
					if(!dirDoc.getDirectorDocsList().isEmpty()){
						directorMasDto.setDirectorDocsList(dirDoc.getDirectorDocsList());
					}
				});
				directorMasDtoList.add(directorMasDto);
			});
			devRegDto.setDeveloperDirectorInfoDTOList(directorMasDtoList);
		}
		
		if(!developerRegistrationEntity.getDeveloperAuthorizedUserDTOList().isEmpty()){
			List<DeveloperAuthorizedUserDTO> authorizedUserDTODtoList = new ArrayList<>();
			developerRegistrationEntity.getDeveloperAuthorizedUserDTOList().forEach(authUserEntityList->{
				DeveloperAuthorizedUserDTO authorizedUserDTO = new DeveloperAuthorizedUserDTO();
				BeanUtils.copyProperties(authUserEntityList, authorizedUserDTO);
				authorizedUserDTO.setAuthDocument(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				authorizedUserDTO.setDeveloperRegMas(devRegDto);
				if(authorizedUserDTO.getAuthUserId()!=null && authorizedUserDTO.getAuthUserId()>0){
					authorizedUserDTO.setPanDetailsFlag(MainetConstants.FlagY);
				}
				developerRegistrationDTO.getDeveloperAuthorizedUserDTOList().forEach(authDtoList->{
					if(!authDtoList.getAuthDigitalPDFList().isEmpty()){
						authorizedUserDTO.setAuthDigitalPDFList(authDtoList.getAuthDigitalPDFList());
					}
					if(!authDtoList.getAuthDocumentList().isEmpty()){
						authorizedUserDTO.setAuthDocumentList(authDtoList.getAuthDocumentList());
					}
				});
				authorizedUserDTODtoList.add(authorizedUserDTO);
			});
			devRegDto.setDeveloperAuthorizedUserDTOList(authorizedUserDTODtoList);
		}
	
		if(!developerRegistrationEntity.getDevLicenseHDRUDTOList().isEmpty()){
			List<DevLicenseHDRUDTO> DevHDRUMasDtoList = new ArrayList<>();
			developerRegistrationEntity.getDevLicenseHDRUDTOList().forEach(getDevHDRUEntityList->{
				DevLicenseHDRUDTO DevHDRUMasDto = new DevLicenseHDRUDTO();
				BeanUtils.copyProperties(getDevHDRUEntityList, DevHDRUMasDto);
				DevHDRUMasDto.setDeveloperRegMas(devRegDto);
				DevHDRUMasDtoList.add(DevHDRUMasDto);
			});
			devRegDto.setDevLicenseHDRUDTOList(DevHDRUMasDtoList);
		}
		if(!developerRegistrationDTO.getAttachDocsList().isEmpty()){
			devRegDto.setAttachDocsList(developerRegistrationDTO.getAttachDocsList());
		}
		
		return devRegDto;
	}

	public void saveDocuments(List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(attachments,
				requestDTO);
	}

	@Transactional
	public void updateLegacyDocuments(List<AttachDocs> attachDocs, Long empId) {
		if (!attachDocs.isEmpty()) {
			for (AttachDocs savedDoc : attachDocs) {
				attachDocsRepository.deleteRecord(MainetConstants.FlagD, empId, savedDoc.getAttId());
			}
		}
	}
	
	@Override
	@Transactional
	public DeveloperRegistrationDTO getDeveloperRegistrationDtoById(Long id, Long orgId) {
	    DeveloperRegistrationDTO developerRegistrationDTO = new DeveloperRegistrationDTO();
	    
	    List<TbDeveloperRegistrationEntity> tbDeveloperRegistrationEntityList = devRegMasRepository.getDeveloperRegistrationByCreatedById(id);
	    
	    if (!tbDeveloperRegistrationEntityList.isEmpty()) {
	        TbDeveloperRegistrationEntity tbDeveloperRegistrationEntity = tbDeveloperRegistrationEntityList.get(0);
	        
	        BeanUtils.copyProperties(tbDeveloperRegistrationEntity, developerRegistrationDTO);
	        Long devId = tbDeveloperRegistrationEntity.getDevId();
	        
	        List<TbStkhldrMasEntity> tbStkhldrMasEntityList = tbDeveloperRegistrationEntity.getDeveloperStakeholderDTOList();
	        List<DeveloperStakeholderDTO> developerStakeholderDTOList = new ArrayList<>();
	        tbStkhldrMasEntityList.forEach(entity -> {
	            DeveloperStakeholderDTO dto = new DeveloperStakeholderDTO();
	            BeanUtils.copyProperties(entity, dto);
	            dto.setStakeholderDocsList(iAttachDocsService.findAllDocLikeReferenceId(orgId, "SKH_ID"+ MainetConstants.WINDOWS_SLASH + entity.getStakeholderId()));
	            developerStakeholderDTOList.add(dto);
	        });

	        List<TbDirectorMasEntity> tbDirectorMasEntityList = tbDeveloperRegistrationEntity.getDeveloperDirectorInfoDTOList();
	        List<DeveloperDirectorInfoDTO> developerDirectorInfoDTOList = new ArrayList<>();
	        tbDirectorMasEntityList.forEach(entity -> {
	            DeveloperDirectorInfoDTO dto = new DeveloperDirectorInfoDTO();
	            BeanUtils.copyProperties(entity, dto);
	            dto.setDirectorDocsList(iAttachDocsService.findAllDocLikeReferenceId(orgId, "DIR_ID"+ MainetConstants.WINDOWS_SLASH + entity.getDirectorId()));
	            developerDirectorInfoDTOList.add(dto);
	        });

	        List<TbAuthUserMasEntity> tbAuthUserMasEntityList = tbDeveloperRegistrationEntity.getDeveloperAuthorizedUserDTOList();
	        List<DeveloperAuthorizedUserDTO> developerAuthorizedUserDTOList = new ArrayList<>();
	        tbAuthUserMasEntityList.forEach(entity -> {
	            DeveloperAuthorizedUserDTO dto = new DeveloperAuthorizedUserDTO();
	            BeanUtils.copyProperties(entity, dto);
	            dto.setAuthDocumentList(iAttachDocsService.findAllDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "BR" + MainetConstants.WINDOWS_SLASH + entity.getAuthUserId()));
	            dto.setAuthDigitalPDFList(iAttachDocsService.findAllDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "DS" + MainetConstants.WINDOWS_SLASH + entity.getAuthUserId()));
	            developerAuthorizedUserDTOList.add(dto);
	        });
	        
	        List<TbDevPerGrntMasEntity> tbDevPerGrntMasEntityList = tbDeveloperRegistrationEntity.getDevLicenseHDRUDTOList();
	        List<DevLicenseHDRUDTO> developerLicenseHDRUDTOList = new ArrayList<>();
	        tbDevPerGrntMasEntityList.forEach(entity -> {
	        	DevLicenseHDRUDTO dto = new DevLicenseHDRUDTO();
	            BeanUtils.copyProperties(entity, dto);
	            developerLicenseHDRUDTOList.add(dto);
	        });

	        developerRegistrationDTO.setDeveloperStakeholderDTOList(developerStakeholderDTOList);
	        developerRegistrationDTO.setDeveloperDirectorInfoDTOList(developerDirectorInfoDTOList);
	        developerRegistrationDTO.setDeveloperAuthorizedUserDTOList(developerAuthorizedUserDTOList);
	        developerRegistrationDTO.setDevLicenseHDRUDTOList(developerLicenseHDRUDTOList);
	        
	        List<AttachDocs> attachList = new ArrayList<>();
	        attachList.addAll(iAttachDocsService.findAllDocLikeReferenceId(orgId, "PL_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno()));
	        attachList.addAll(iAttachDocsService.findAllDocLikeReferenceId(orgId, "DC_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno()));

	        developerRegistrationDTO.setAttachDocsList(attachList);
	        developerRegistrationDTO.setCheckListDocumentSet(iChecklistVerificationService.getDocumentUploadedForAppId(developerRegistrationDTO.getTcp_dev_appno(), orgId));				
	    }
	    
	    return developerRegistrationDTO;
	}
	
	@Override
	public void saveApplicantInfoDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode){
		int count=0;
		int stkCount=500;
		for (DeveloperStakeholderDTO saveStkhldrMasDataList : developerRegistrationDTO.getDeveloperStakeholderDTOList()) {
			List<DocumentDetailsVO> attach = new ArrayList<>();
			for(DocumentDetailsVO stakeholderDocList :developerRegistrationDTO.getDeveloperStakeholderDTOList().get(count).getStakeholderDoc()){
				if(stakeholderDocList.getUploadedDocumentPath().contains("stakeholder"+stkCount)){
		            AttachDocs findDocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "SKH_ID"+ MainetConstants.WINDOWS_SLASH + saveStkhldrMasDataList.getStakeholderId());
		            if(findDocLikeReferenceId==null){
		            	attach.add(stakeholderDocList);
		            }else if(findDocLikeReferenceId!=null && !findDocLikeReferenceId.getAttFname().equals(stakeholderDocList.getDocumentName())){
		            	attachDocsRepository.deleteDocLikeReferenceId(orgId, "SKH_ID"+ MainetConstants.WINDOWS_SLASH + saveStkhldrMasDataList.getStakeholderId(), MainetConstants.FlagA);
		            	attach.add(stakeholderDocList);
		            }
				}						
			}	
			if(attach.size()>0){
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(orgId);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("SKH_ID" + MainetConstants.WINDOWS_SLASH + saveStkhldrMasDataList.getStakeholderId());
				requestDTO.setUserId(empId);	
				requestDTO.setServiceId(serviceId);
				requestDTO.setDepartmentName(deptCode);
				saveDocuments(attach, requestDTO);
			}				
			count ++;
			stkCount ++;
		}
		
		int dCount=0;
		int directorCount=100;
		for (DeveloperDirectorInfoDTO savedDirectorMasDataList : developerRegistrationDTO.getDeveloperDirectorInfoDTOList()) {
			List<DocumentDetailsVO> attach = new ArrayList<>();
			for(DocumentDetailsVO directorDocList :developerRegistrationDTO.getDeveloperDirectorInfoDTOList().get(dCount).getDirectorDoc()){
				if(directorDocList.getUploadedDocumentPath().contains("directorInfo"+directorCount)){
					AttachDocs findDIRDocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "DIR_ID"+ MainetConstants.WINDOWS_SLASH + savedDirectorMasDataList.getDirectorId());
					if(findDIRDocLikeReferenceId==null){
						attach.add(directorDocList);
					}else if(findDIRDocLikeReferenceId!=null && !findDIRDocLikeReferenceId.getAttFname().equals(directorDocList.getDocumentName())){
						attachDocsRepository.deleteDocLikeReferenceId(orgId, "DIR_ID"+ MainetConstants.WINDOWS_SLASH + savedDirectorMasDataList.getDirectorId(), MainetConstants.FlagA);
						attach.add(directorDocList);
					}					
				}						
			}		
			if(attach.size()>0){
				RequestDTO requestDTO = new RequestDTO();
				requestDTO.setOrgId(orgId);
				requestDTO.setStatus(MainetConstants.FlagA);
				requestDTO.setIdfId("DIR_ID" + MainetConstants.WINDOWS_SLASH + savedDirectorMasDataList.getDirectorId());
				requestDTO.setUserId(empId);
				requestDTO.setServiceId(serviceId);
				requestDTO.setDepartmentName(deptCode);
				saveDocuments(attach, requestDTO);
			}
			
			dCount++;
			directorCount ++;
		}
	}
	
	@Override
	public void saveAuthUserDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode){
		int aCount=0;
		int authCount=200;
		int signCount=300;
		for (DeveloperAuthorizedUserDTO savedAuthUserMasDataList : developerRegistrationDTO.getDeveloperAuthorizedUserDTOList()) {
			List<DocumentDetailsVO> attachBoardResolution = new ArrayList<>();
			List<DocumentDetailsVO> attachDigitalSign = new ArrayList<>();
			if(!developerRegistrationDTO.getDeveloperAuthorizedUserDTOList().get(aCount).getAuthDocument().isEmpty()){
			for(DocumentDetailsVO authDocList :developerRegistrationDTO.getDeveloperAuthorizedUserDTOList().get(aCount).getAuthDocument()){
				if(authDocList.getUploadedDocumentPath().contains("boardResolution"+authCount)){
					AttachDocs findAuthDocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "BR" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId());
					if(findAuthDocLikeReferenceId==null){
						attachBoardResolution.add(authDocList);
					}else if(findAuthDocLikeReferenceId!=null && !findAuthDocLikeReferenceId.getAttFname().equals(authDocList.getDocumentName())){
						attachDocsRepository.deleteDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "BR" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId(), MainetConstants.FlagA);
						attachBoardResolution.add(authDocList);
					}
					if(attachBoardResolution.size()>0){
						RequestDTO requestDTO = new RequestDTO();
						requestDTO.setOrgId(orgId);
						requestDTO.setStatus(MainetConstants.FlagA);
						requestDTO.setIdfId("AUTH_ID" + MainetConstants.WINDOWS_SLASH + "BR" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId());
						requestDTO.setUserId(empId);
						requestDTO.setServiceId(serviceId);
						requestDTO.setDepartmentName(deptCode);
						saveDocuments(attachBoardResolution, requestDTO);
					}
				}
				if(authDocList.getUploadedDocumentPath().contains("digitalSign"+signCount)){
					AttachDocs findAuth2DocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "DS" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId());
					if(findAuth2DocLikeReferenceId==null){
						attachDigitalSign.add(authDocList);
					}else if(findAuth2DocLikeReferenceId!=null && !findAuth2DocLikeReferenceId.getAttFname().equals(authDocList.getDocumentName())){
						attachDocsRepository.deleteDocLikeReferenceId(orgId, "AUTH_ID" + MainetConstants.WINDOWS_SLASH + "DS" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId(), MainetConstants.FlagA);
						attachDigitalSign.add(authDocList);
					}
					
					if(attachDigitalSign.size()>0){
						RequestDTO requestDTO = new RequestDTO();
						requestDTO.setOrgId(orgId);
						requestDTO.setStatus(MainetConstants.FlagA);
						requestDTO.setIdfId("AUTH_ID" + MainetConstants.WINDOWS_SLASH + "DS" + MainetConstants.WINDOWS_SLASH + savedAuthUserMasDataList.getAuthUserId());
						requestDTO.setUserId(empId);
						requestDTO.setServiceId(serviceId);
						requestDTO.setDepartmentName(deptCode);
						saveDocuments(attachDigitalSign, requestDTO);
					}
				}
				
			}
		}				
			aCount++;
			authCount++;
			signCount++;
		}
	}
	
	@Override
	public void saveCapacityFormDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode, List<DocumentDetailsVO> checkListDoc){
		developerRegistrationDTO.setAttachments(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		for(DocumentDetailsVO attachDocList :developerRegistrationDTO.getAttachments()){
			List<DocumentDetailsVO> attachList = new ArrayList<>();
			if(attachDocList.getUploadedDocumentPath()!=null){
				if(attachDocList.getUploadedDocumentPath().contains("perLetter401")){
					AttachDocs findPLDocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "PL_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno());
					if(findPLDocLikeReferenceId==null){
						attachList.add(attachDocList);
					}else if(findPLDocLikeReferenceId!=null && !findPLDocLikeReferenceId.getAttFname().equals(attachDocList.getDocumentName())){
						attachDocsRepository.deleteDocLikeReferenceId(orgId, "PL_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno(), MainetConstants.FlagA);
						attachList.add(attachDocList);
					}
					
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setOrgId(orgId);
					requestDTO.setStatus(MainetConstants.FlagA);
					requestDTO.setIdfId("PL_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno());
					requestDTO.setUserId(empId);
					requestDTO.setServiceId(serviceId);
					requestDTO.setDepartmentName(deptCode);
					saveDocuments(attachList, requestDTO);
				}
				if(attachDocList.getUploadedDocumentPath().contains("devCapacity402")){
					AttachDocs findDCDocLikeReferenceId = iAttachDocsService.findDocLikeReferenceId(orgId, "DC_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno());
					if(findDCDocLikeReferenceId==null){
						attachList.add(attachDocList);
					}else if(findDCDocLikeReferenceId!=null && !findDCDocLikeReferenceId.getAttFname().equals(attachDocList.getDocumentName())){
						attachDocsRepository.deleteDocLikeReferenceId(orgId, "DC_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno(), MainetConstants.FlagA);
						attachList.add(attachDocList);
					}
					
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setOrgId(orgId);
					requestDTO.setStatus(MainetConstants.FlagA);
					requestDTO.setIdfId("DC_ID" + MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno());
					requestDTO.setUserId(empId);
					requestDTO.setServiceId(serviceId);
					requestDTO.setDepartmentName(deptCode);
					saveDocuments(attachList, requestDTO);
				}
			}								
		}
		
		if ((checkListDoc != null) && !checkListDoc.isEmpty()) {
			List<DocumentDetailsVO> checkListDocSave = new ArrayList<>();
			
			checkListDoc.forEach(checkListDocument->{				
				CFCAttachment documentUploadedList = iChecklistVerificationService.getDocumentUploadedList(developerRegistrationDTO.getTcp_dev_appno(), checkListDocument.getDocumentId());
				if(documentUploadedList == null){
					checkListDocSave.add(checkListDocument);				
				}else if(documentUploadedList!=null && !documentUploadedList.getAttFname().equals(checkListDocument.getDocumentName())){
					devRegMasRepository.deleteDocument(developerRegistrationDTO.getTcp_dev_appno(), serviceId, orgId, documentUploadedList.getClmId());
					checkListDocSave.add(checkListDocument);
				}else if(documentUploadedList!=null && documentUploadedList.getAttFname().equals(checkListDocument.getDocumentName())){
					devRegMasRepository.deleteDocument(developerRegistrationDTO.getTcp_dev_appno(), serviceId, orgId, documentUploadedList.getClmId());
					checkListDocSave.add(checkListDocument);
				}				
			});
			RequestDTO reqDto = new RequestDTO();
			reqDto.setApplicationId(developerRegistrationDTO.getTcp_dev_appno());
			reqDto.setOrgId(orgId);
			reqDto.setReferenceId("DRN"+ MainetConstants.WINDOWS_SLASH + developerRegistrationDTO.getTcp_dev_appno());
			reqDto.setServiceId(serviceId);
			fileUploadService.doFileUpload(checkListDocSave, reqDto);
		}		
	}
	
	@Override
	public Map<Long, Set<File>> getUploadedFileList(List<CFCAttachment> newMap,
		FileNetApplicationClient fileNetApplicationClient, Map<Long, Set<File>> fileMap) {
		Set<File> fileList = null;
		//Map<Long, Set<File>> fileMap = new HashMap<>();
		Long count = 0l;
		for (CFCAttachment doc : newMap) {
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
				fileList.add(file);
				fileMap.put(count, fileList);
				count++;
			} catch (final Exception e) {
				//throw new FrameworkException("Exception in getting getUploadedFileList", e);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (final IOException e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				}

			}			
		}
		return fileMap;
	}
	
	@Override
	public Map<Long, Set<File>> getDocUploadedFileList(List<AttachDocs> newMap,
		FileNetApplicationClient fileNetApplicationClient, Map<Long, Set<File>> fileMap, Long docCount) {
		Set<File> fileList = null;
		//Map<Long, Set<File>> fileMap = new HashMap<>();
		Long count = docCount;
		for (AttachDocs doc : newMap) {
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
				fileList.add(file);
				fileMap.put(count, fileList);
				count++;
			} catch (final Exception e) {
				//throw new FrameworkException("Exception in getting getUploadedFileList", e);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (final IOException e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				}

			}
			
		}
		return fileMap;
	}
}
