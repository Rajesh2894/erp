package com.abm.mainet.sfac.ui.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.AuditedBalanceSheetInfoDTO;
import com.abm.mainet.sfac.dto.BusinessPlanInfoDTO;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.FPOProfileMasterDto;
import com.abm.mainet.sfac.dto.LicenseInformationDTO;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.FPOProfileMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FPOProfileModel;

@Controller
@RequestMapping("FPOProfileManagementForm.html")
public class FPOProfileManagementController extends AbstractFormController<FPOProfileModel>{

	private static final Logger logger = Logger.getLogger(FPOProfileManagementController.class);

	@Autowired
	private FPOMasterService fPOMasterService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired 
	private FPOProfileMasterService fpoProfileMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {

		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		List<FPOMasterDto> fpoMasterDtos = new ArrayList<>();
		
		
		 FPOMasterDto fpoMasterDto =  fpoProfileMasterService.getFPODetails(UserSession.getCurrent().getEmployee().getMasId());
		 fpoMasterDtos.add(fpoMasterDto);
		 this.getModel().setFpoMasterDto(fpoMasterDto);
		 this.getModel().setFpoMasterDtoList(fpoMasterDtos);
		return new ModelAndView("FPOProfileManagementSummary", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long fpoId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		populateModel();
		FPOProfileMasterDto dto = fpoProfileMasterService.getDetailById(fpoId);


		dto.setFpoId(fpoId);
		this.getModel().setDto(dto);
		return new ModelAndView("FPOProfileManagement", MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);

		this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoDetails(Long fpoId, String  fpoRegNo, Long iaId ,final HttpServletRequest httpServletRequest) {
		List<FPOMasterDto> DtoList = fPOMasterService.getfpoByIdAndRegNo(fpoId,fpoRegNo,iaId,UserSession.getCurrent().getEmployee().getMasId(),UserSession.getCurrent()
				.getOrganisation().getOrgShortNm(),UserSession.getCurrent().getEmployee().getEmploginname());
		this.getModel().setFpoMasterDtoList(DtoList);
		this.getModel().getDto().setFpoId(fpoId);
		this.getModel().getDto().setFpoRegNo(fpoRegNo);
		this.getModel().getDto().setIaId(iaId);
		//this.getModel().setFpoMasterDtoList(DtoList);


		return new ModelAndView("FPOProfileSummaryFormValidn", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveFPOProfileMasterPage")
	public @ResponseBody Long showAstInfoPage(final HttpServletRequest request
			) {
		logger.debug("Save Master data");
		bindModel(request);
		FPOProfileModel model = this.getModel();

		return  model.updateFPOProfileFinInfo(model.getDto());


	}


	@RequestMapping(method = RequestMethod.POST, params = "saveLicenseInfoForm" )
	public @ResponseBody Long saveLicenseInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileLicenseInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveBPInfoForm" )
	public @ResponseBody Long saveBPInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileBPInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveABSInfoForm" )
	public @ResponseBody Long saveABSInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileABSInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveDPRInfoForm" )
	public @ResponseBody Long saveDPRInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileDPRInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveCreditInfoForm" )
	public @ResponseBody Long saveCreditInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileCreditInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveEquityInfoForm" )
	public @ResponseBody Long saveEquityInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileEquityInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveProfileFarmerSummaryForm" )
	public @ResponseBody Long saveProfileFarmerSummaryForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileFarmerSummaryInfo(model.getDto());


	}


	@RequestMapping(method = RequestMethod.POST, params = "saveCreditGrandForm" )
	public @ResponseBody Long saveCreditGrandForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileCreditGrandInfo(model.getDto());


	}


	@RequestMapping(method = RequestMethod.POST, params = "saveCustomInfoForm" )
	public @ResponseBody Long saveCustomInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileCustomInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "savePNSInfoForm" )
	public @ResponseBody Long savePNSInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfilePNSInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveSubsidiesInfoForm" )
	public @ResponseBody Long saveSubsidiesInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileSubsidiesInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "savePreharveshInfoForm" )
	public @ResponseBody Long savePreharveshInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfilePreharveshInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "savePostharvestInfoForm" )
	public @ResponseBody Long savePostharvestInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfilePostHarvestInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveTransportVehicleInfoForm" )
	public @ResponseBody Long saveTransportVehicleInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileTransportInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveMLInfoForm" )
	public @ResponseBody Long saveMLInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileMLInfo(model.getDto(), model.getFpoMasterDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "saveStorageInfoForm" )
	public @ResponseBody Long saveStorageInfoForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileStorageInfo(model.getDto());


	}



	@RequestMapping(method = RequestMethod.POST, params = "saveProfileManagementForm" )
	public @ResponseBody Long saveProfileManagementForm(final HttpServletRequest request) {
		bindModel(request);
		FPOProfileModel model = this.getModel();


		return  model.updateFPOProfileManagementInfo(model.getDto());


	}

	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<LicenseInformationDTO> list = new ArrayList<>();

		Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();
		Long countLi = 0l;
		for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {

			newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
			countLi++;
			list.add(licenseInformationDTO);

		}
		Long countBP = 100L;
		for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
			newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
			countBP++;

		}

		Long countABS = 200L;
		for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
			newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
			countABS++;

		}


		int count = 0;
		for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {

			if (licenseInformationDTO.getLicenseType() != null) {
				try {
					BeanUtils.copyProperties(licenseInformationDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}

		}
		this.getModel().getDto().setLicenseInformationDetEntities(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<LicenseInformationDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			LicenseInformationDTO tesDto = list.get(i);
			LicenseInformationDTO newData = new LicenseInformationDTO();
			newData = tesDto;
			newData.setLicenseType(this.getModel().getDto().getLicenseInformationDetEntities().get(i).getLicenseType());
			newData.setLicenseDesc(this.getModel().getDto().getLicenseInformationDetEntities().get(i).getLicenseDesc());
			newData.setLicIssueDate(this.getModel().getDto().getLicenseInformationDetEntities().get(i).getLicIssueDate());
			newData.setLicExpDate(this.getModel().getDto().getLicenseInformationDetEntities().get(i).getLicExpDate());
			newData.setLicIssueAuth(this.getModel().getDto().getLicenseInformationDetEntities().get(i).getLicIssueAuth());

			listData.add(newData);
		}


		Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());
		countABS = 200L;
		countLi = 0L;
		countBP = 100L;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
				fileMap1.put(countLi, entry.getValue());
				countLi++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
				fileMap1.put(countBP, entry.getValue());
				countBP++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
				fileMap1.put(countABS, entry.getValue());
				countABS++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new LicenseInformationDTO());

		this.getModel().getDto().setLicenseInformationDetEntities(listData);
		return new ModelAndView("FPOProfileLicenseInfo", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(method = RequestMethod.POST, params = "fileCountUploadBP")
	public ModelAndView fileCountUploadBP(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<BusinessPlanInfoDTO> list = new ArrayList<>();

		Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();
		Long countLi = 0l;
		for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {

			newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
			countLi++;

		}
		Long countBP = 100L;
		for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
			newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
			countBP++;
			list.add(businessPlanInfoDTO);
		}

		Long countABS = 200L;
		for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
			newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
			countABS++;

		}


		int count = 0;
		for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {

			if (businessPlanInfoDTO.getDocumentDescription() != null) {
				try {
					BeanUtils.copyProperties(businessPlanInfoDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}

		}
		this.getModel().getDto().setBusinessPlanInfoDTOs(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<BusinessPlanInfoDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			BusinessPlanInfoDTO tesDto = list.get(i);
			BusinessPlanInfoDTO newData = new BusinessPlanInfoDTO();
			newData = tesDto;
			newData.setDocumentDescription(this.getModel().getDto().getBusinessPlanInfoDTOs().get(i).getDocumentDescription());

			listData.add(newData);
		}


		Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());
		countABS = 200L;
		countLi = 0L;
		countBP = 100L;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
				fileMap1.put(countLi, entry.getValue());
				countLi++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
				fileMap1.put(countBP, entry.getValue());
				countBP++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
				fileMap1.put(countABS, entry.getValue());
				countABS++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new BusinessPlanInfoDTO());

		this.getModel().getDto().setBusinessPlanInfoDTOs(listData);
		return new ModelAndView("FPOProfileBPInfo", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "fileCountUploadABS")
	public ModelAndView fileCountUploadABS(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<AuditedBalanceSheetInfoDTO> list = new ArrayList<>();
		Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();
		Long countLi = 0l;
		for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {

			newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
			countLi++;

		}
		Long countBP = 100L;
		for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
			newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
			countBP++;
		}

		Long countABS = 200L;
		for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
			newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
			countABS++;
			list.add(auditedBalanceSheetInfoDTO);
		}

		int count = 0;
		for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {

			if (auditedBalanceSheetInfoDTO.getFinancialYear() != null) {
				try {
					BeanUtils.copyProperties(auditedBalanceSheetInfoDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}

		}
		this.getModel().getDto().setAuditedBalanceSheetInfoDetailEntities(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<AuditedBalanceSheetInfoDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			AuditedBalanceSheetInfoDTO tesDto = list.get(i);
			AuditedBalanceSheetInfoDTO newData = new AuditedBalanceSheetInfoDTO();
			newData = tesDto;
			newData.setFinancialYear(this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities().get(i).getFinancialYear());



			listData.add(newData);
		}

		Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());

		countABS = 200L;
		countLi = 0L;
		countBP = 100L;

		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
				fileMap1.put(countLi, entry.getValue());
				countLi++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
				fileMap1.put(countBP, entry.getValue());
				countBP++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
				fileMap1.put(countABS, entry.getValue());
				countABS++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new AuditedBalanceSheetInfoDTO());

		this.getModel().getDto().setAuditedBalanceSheetInfoDetailEntities(listData);
		return new ModelAndView("FPOProfileABSInfo", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletionLi")
	public ModelAndView doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getLicenseInformationDetEntities().get(id).getLicId()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getLicenseInformationDetEntities().get(id).getLicId());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getLicenseInformationDetEntities().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getLicenseInformationDetEntities();
				

					//this.getModel().getDto().getLicenseInformationDetEntities().get(id).getAttachmentsLi().remove(0);
					/*
					 * iJudgementMasterService.updateJudgementData(
					 * this.getModel().getJudgementMasterDtoList().get(id), attachments, attachDocs,
					 * this.getModel().getJudgementMasterDtoList().get(id).getJudId());
					 */

				}

			}


			Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();


			Long countLi = 0L;
			for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {
				newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
				countLi++;
			}
			
			Long countBP = 100L;
			for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
				newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
				countBP++;
			}

			Long countABS = 200L;
			for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
				newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
				countABS++;

			}
			Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());

			countABS = 200L;
			 countLi = 0L;
			countBP = 100L;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list1 = new ArrayList<>(entry.getValue());
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
					fileMap1.put(countLi, entry.getValue());
					countLi++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
					fileMap1.put(countBP, entry.getValue());
					countBP++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
					fileMap1.put(countABS, entry.getValue());
					countABS++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);

		}

		

		return new ModelAndView("FPOProfileLicenseInfo", MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletionBP")
	public ModelAndView doEntryDeletionBP(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getBusinessPlanInfoDTOs().get(id).getBpID()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getBusinessPlanInfoDTOs().get(id).getBpID());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getBusinessPlanInfoDTOs().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getBusinessPlanInfoDTOs();
				

					//this.getModel().getDto().getLicenseInformationDetEntities().get(id).getAttachmentsLi().remove(0);
					/*
					 * iJudgementMasterService.updateJudgementData(
					 * this.getModel().getJudgementMasterDtoList().get(id), attachments, attachDocs,
					 * this.getModel().getJudgementMasterDtoList().get(id).getJudId());
					 */

				}

			}


			Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();


			Long countLi = 0L;
			for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {
				newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
				countLi++;
			}
			
			Long countBP = 100L;
			for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
				newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
				countBP++;
			}

			Long countABS = 200L;
			for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
				newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
				countABS++;

			}
			Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());

			countABS = 200L;
			 countLi = 0L;
			 countBP = 100L;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list1 = new ArrayList<>(entry.getValue());
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
					fileMap1.put(countLi, entry.getValue());
					countLi++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
					fileMap1.put(countBP, entry.getValue());
					countBP++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
					fileMap1.put(countABS, entry.getValue());
					countABS++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);

		}

		

		return new ModelAndView("FPOProfileBPInfo", MainetConstants.FORM_NAME, this.getModel());

	}

	
	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletionABS")
	public ModelAndView doEntryDeletionABS(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities().get(id).getAuditedBalanceSheetId()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities().get(id).getAuditedBalanceSheetId());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities();
				

					//this.getModel().getDto().getLicenseInformationDetEntities().get(id).getAttachmentsLi().remove(0);
					/*
					 * iJudgementMasterService.updateJudgementData(
					 * this.getModel().getJudgementMasterDtoList().get(id), attachments, attachDocs,
					 * this.getModel().getJudgementMasterDtoList().get(id).getJudId());
					 */

				}

			}


			Map<Long, List<AttachDocs>> newMap = new LinkedHashMap<>();

			Long countLi = 0L;
			for (LicenseInformationDTO licenseInformationDTO : this.getModel().getDto().getLicenseInformationDetEntities()) {
				newMap.put(countLi, licenseInformationDTO.getAttachDocsListLi());
				countLi++;

			}

			Long countBP = 100L;
			for (BusinessPlanInfoDTO businessPlanInfoDTO : this.getModel().getDto().getBusinessPlanInfoDTOs()) {
				newMap.put(countBP, businessPlanInfoDTO.getAttachDocsListBP());
				countBP++;
			}

			
			
			
			Long countABS = 200L;
			for (AuditedBalanceSheetInfoDTO auditedBalanceSheetInfoDTO : this.getModel().getDto().getAuditedBalanceSheetInfoDetailEntities()) {
				newMap.put(countABS, auditedBalanceSheetInfoDTO.getAttachDocsListABS());
				countABS++;

			}
			Map<Long, Set<File>> fileMap1 = fpoProfileMasterService.getUploadedFileList(newMap,FileNetApplicationClient.getInstance());

			countABS = 200L;
			countLi = 0L;
			countBP = 100L;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list1 = new ArrayList<>(entry.getValue());
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("lidfg")) {
					fileMap1.put(countLi, entry.getValue());
					countLi++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("bpdfg")) {
					fileMap1.put(countBP, entry.getValue());
					countBP++;
				}
				if (!list1.isEmpty()  && list1.get(0).getPath().contains("absdfg")) {
					fileMap1.put(countABS, entry.getValue());
					countABS++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);

		}

		

		return new ModelAndView("FPOProfileABSInfo", MainetConstants.FORM_NAME, this.getModel());

	}


}
