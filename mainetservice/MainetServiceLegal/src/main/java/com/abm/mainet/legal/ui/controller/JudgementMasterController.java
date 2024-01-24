package com.abm.mainet.legal.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.legal.domain.JudgeDetailMaster;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgementMasterDto;
import com.abm.mainet.legal.dto.LegalRestResponseDto;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.service.IJudgementMasterService;
import com.abm.mainet.legal.ui.model.JudgementMasterModel;

@Controller
@RequestMapping("/JudgementMaster.html")
public class JudgementMasterController extends AbstractFormController<JudgementMasterModel> {

	@Autowired
	IJudgementMasterService iJudgementMasterService;

	@Autowired
	ICaseEntryService iCaseEntryService;

	@Autowired
	private ICourtMasterService courtMasterService;

	@Autowired
	private TbDepartmentService iTbDepartmentService;

	@Autowired
	private ICaseEntryService caseEntryService;

	@Autowired
	private IJudgementMasterService judgementMasterService;

	@Autowired
	TbOrganisationService tbOrganisationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("JudgementMaster.html");
		JudgementMasterModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = tbOrganisationService.findDefaultOrganisation();		
		// List<CaseEntryDTO> caseEntryList = iCaseEntryService.getAllCaseEntry(orgId);
		// get CONCLUDED DATA FROM HSC PREFIX HSC
		final List<LookUp> lookUps = CommonMasterUtility.lookUpListByPrefix("HSC", orgId);
		Long hrStatusId = -1L;
		for (final LookUp lookUp : lookUps) {
			if ("CL".equalsIgnoreCase(lookUp.getLookUpCode())) {
				hrStatusId = lookUp.getLookUpId();
			}
		}

		if (lookUps.isEmpty()) {
			throw new FrameworkException("HSC PREFIX NOT FOUND IN LEGAL");
		}
		if (orgId.equals(org.getOrgid())) {
			List<CaseEntryDTO> caseEntryList = iCaseEntryService.getAllCaseEntryBasedOnHearingStatus(hrStatusId);
			model.setDepartmentsList(iTbDepartmentService.findMappedDepartments(orgId));
			model.setCaseEntryDtoList(caseEntryList);
			model.getCaseEntryDTO().setParentOrgid(org.getOrgid());
			return defaultResult();
		} else {
			List<CaseEntryDTO> caseEntryList = iCaseEntryService.getAllCaseEntryBasedOnHearing(hrStatusId, orgId);
			model.setDepartmentsList(iTbDepartmentService.findMappedDepartments(orgId));
			model.setCaseEntryDtoList(caseEntryList);
		    model.getCaseEntryDTO().setParentOrgid(org.getOrgid());
			return defaultResult();
		}

	}

	@ResponseBody
	@RequestMapping(params = "searchJudgementDetails", method = RequestMethod.POST)
	public LegalRestResponseDto getCouncilMeetingList(@RequestParam("cseSuitNo") final String cseSuitNo,
			@RequestParam("cseDeptid") final Long cseDeptid, @RequestParam("cseDate") final Date cseDate,
			final HttpServletRequest request) {
		getModel().bind(request);
		LegalRestResponseDto response = new LegalRestResponseDto();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 Organisation org = tbOrganisationService.findDefaultOrganisation();
		// get CONCLUDED DATA FROM HSC PREFIX HSC
		final List<LookUp> lookUps = CommonMasterUtility.lookUpListByPrefix("HSC", orgId);
		Long hrStatusId = -1L;
		for (final LookUp lookUp : lookUps) {
			if ("CL".equalsIgnoreCase(lookUp.getLookUpCode())) {
				hrStatusId = lookUp.getLookUpId();
			}
		}
		if (lookUps.isEmpty()) {
			throw new FrameworkException("HSC PREFIX NOT FOUND IN LEGAL");
		}
		 if (orgId.equals(org.getOrgid())) {
		List<CaseEntryDTO> caseEntryDtoList = caseEntryService.searchCaseEntry(cseSuitNo, cseDeptid, null, null, null,
				cseDate, null, null, org.getOrgid(), hrStatusId,MainetConstants.FlagH);
		response.setCaseEntryDtoList(caseEntryDtoList);
		 }else {
			 List<CaseEntryDTO> caseEntryDtoList = caseEntryService.searchCaseEntry(cseSuitNo, cseDeptid, null, null, null,
						cseDate, null, null, orgId, hrStatusId,null);
				response.setCaseEntryDtoList(caseEntryDtoList);
		 }
		return response;
	}

	@RequestMapping(params = "editJudgementDetails", method = RequestMethod.POST)
	public ModelAndView editJudgementDetails(@RequestParam("cseId") final Long cseId,
			@RequestParam("crtId") final Long crtId) {
		this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = tbOrganisationService.findDefaultOrganisation();
		if (orgId.equals(org.getOrgid())) {
			this.getModel().setOrgFlag(MainetConstants.FlagY);

		} else {
			this.getModel().setOrgFlag(MainetConstants.FlagN);
		}
		populateData(cseId, crtId, orgId);
		return new ModelAndView("judgementMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "viewJudgementDetails", method = RequestMethod.POST)
	public ModelAndView viewJudgementDetails(@RequestParam("cseId") final Long cseId,
			@RequestParam("crtId") final Long crtId) {
		this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setOrgFlag(MainetConstants.FlagN);
		populateData(cseId, crtId, orgId);
		return new ModelAndView("judgementMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	// method for fetch needed data and display on UI
	public void populateData(Long cseId, Long crtId, Long orgId) {
		Organisation org = tbOrganisationService.findDefaultOrganisation();
		Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		// make data for UI
		// get data like department, court details,case submission date,bench name
		List<JudgementMasterDto> judgementMasterDtoList = new ArrayList<>();
		// 1st find data from TB_LGL_JUDGEMENT_MAST table by cseId and crtId
		judgementMasterDtoList = judgementMasterService.fetchJudgementDataByIds(cseId, crtId, orgId);
		CaseEntryDTO caseEntryDto = caseEntryService.getCaseEntryById(cseId);
		CourtMasterDTO courtMasterDto = courtMasterService.getCourtMasterById(crtId);
		if (judgementMasterDtoList.isEmpty()) {
			// if empty than set data from TB_LGL_CASE_MAS table and TB_LGL_COURT_MAST for
			// court details
			JudgementMasterDto judgementDto = new JudgementMasterDto();
			BeanUtils.copyProperties(caseEntryDto, judgementDto);
			judgementDto.setCseDeptName(
					iTbDepartmentService.findDepartmentById(caseEntryDto.getCseDeptid()).getDpDeptdesc());
			judgementDto.setCseCourtDesc(courtMasterDto.getCrtName());
			judgementDto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(caseEntryDto.getCseDate()));
			judgementDto.setCseDate(caseEntryDto.getCseDate());
			// benchName ask to madam

			String benchName = "";
			// get data from TB_LGL_JUDGE_DET
			List<JudgeDetailMaster> judgeDetailsList = ApplicationContextProvider.getApplicationContext()
					.getBean(IJudgeMasterService.class).fetchJudgeDetailsByCrtId(crtId,cseId,orgIds);
			for (JudgeDetailMaster judgeData : judgeDetailsList) {
				if (!StringUtils.isEmpty(judgeData.getJudge().getJudgeBenchName())) {
					benchName += judgeData.getJudge().getJudgeBenchName() + ",";
				}
			}
			if (benchName != "") {
				judgementDto.setCseBenchName(benchName.substring(0, benchName.lastIndexOf(",")));
			}
			judgementMasterDtoList.add(judgementDto);
		} else {
			judgementMasterDtoList.forEach(masterDto -> {
				JudgementMasterDto judgementDto = masterDto;
				// doing this because object field set from caseEntry which is not correct so
				// set field from judgementMasterDto
				// set createdBy,createdDate,lgIpMac
				caseEntryDto.setCreatedBy(masterDto.getCreatedBy());
				caseEntryDto.setCreatedDate(masterDto.getCreatedDate());
				caseEntryDto.setLgIpMac(masterDto.getLgIpMac());
				BeanUtils.copyProperties(caseEntryDto, judgementDto);
				
				if(UserSession.getCurrent().getLanguageId()==1) {
				judgementDto.setCseDeptName(
						iTbDepartmentService.findDepartmentById(caseEntryDto.getCseDeptid()).getDpDeptdesc());
				judgementDto.setCseCourtDesc(courtMasterDto.getCrtName());
				}
				else {
					
					judgementDto.setCseDeptName(
							iTbDepartmentService.findDepartmentById(caseEntryDto.getCseDeptid()).getDpNameMar());
					judgementDto.setCseCourtDesc(courtMasterDto.getCrtNameReg());
				}
				
				judgementDto.setCseDateDesc(UtilityService.convertDateToDDMMYYYY(caseEntryDto.getCseDate()));
				judgementDto.setCseId(cseId);

			});
		}

		// make code for fetch document data from tb_attach_document table
		List<String> identifer = new ArrayList<>();
		if (orgId.equals(org.getOrgid())) {
			for (JudgementMasterDto jmd : judgementMasterDtoList) {
				identifer.add("JDM" + MainetConstants.WINDOWS_SLASH + jmd.getJudId());
				final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
						.getBean(IAttachDocsService.class).findByIdfInQuery(jmd.getOrgId(), identifer);
				this.getModel().setAttachDocsList(attachDocs);
			}
		} else {
			for (JudgementMasterDto jmd : judgementMasterDtoList) {
				identifer.add("JDM" + MainetConstants.WINDOWS_SLASH + jmd.getJudId());
			}
			// get attached document
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
					.getBean(IAttachDocsService.class)
					.findByIdfInQuery(UserSession.getCurrent().getOrganisation().getOrgid(), identifer);
			if (!attachDocs.isEmpty()) {
				FileUploadUtility.getCurrent().setFileMap(
						this.getModel().getUploadedFileList(attachDocs, FileNetApplicationClient.getInstance()));
			}
			this.getModel().setAttachDocsList(attachDocs);

		}
		this.getModel().setCaseDate(UtilityService.convertDateToDDMMYYYY(caseEntryDto.getCseDate()));
		this.getModel().setJudgementMasterDtoList(judgementMasterDtoList);
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		List<JudgementMasterDto> list = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getLength(); i++) {
			attachments.add(new DocumentDetailsVO());
			list.add(new JudgementMasterDto());
		}
		int count = 0;
		for (JudgementMasterDto dto : this.getModel().getJudgementMasterDtoList()) {

			if (dto.getCseDeptName() != null) {
				BeanUtils.copyProperties(dto, list.get(count));
				count++;
			}

		}
		this.getModel().setAttachments(attachments);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<JudgementMasterDto> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			JudgementMasterDto newData = list.get(i);
			newData.setCseId(this.getModel().getJudgementMasterDtoList().get(0).getCseId());
			newData.setCrtId(this.getModel().getJudgementMasterDtoList().get(0).getCrtId());
			newData.setCseDate(this.getModel().getJudgementMasterDtoList().get(0).getCseDate());
			newData.setCseDeptName(this.getModel().getJudgementMasterDtoList().get(0).getCseDeptName());
			newData.setCseCourtDesc(this.getModel().getJudgementMasterDtoList().get(0).getCseCourtDesc());
			newData.setCseDateDesc(this.getModel().getJudgementMasterDtoList().get(0).getCseDateDesc());
			newData.setCseBenchName(this.getModel().getJudgementMasterDtoList().get(0).getCseBenchName());
			listData.add(newData);
		}

		this.getModel().setJudgementMasterDtoList(listData);
		Long count1 = 0l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);
		return new ModelAndView("judgementMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
	public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);

		this.getModel().getJudgementMasterDtoList().remove(id);
		this.getModel().getAttachments().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			List<Long> enclosureRemoveById = new ArrayList<>();
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getJudgementMasterDtoList();
					enclosureRemoveById.add(entry.getKey());

					//this.getModel().getAttachments().remove(id);
					/*
					 * iJudgementMasterService.updateJudgementData(
					 * this.getModel().getJudgementMasterDtoList().get(id), attachments, attachDocs,
					 * this.getModel().getJudgementMasterDtoList().get(id).getJudId());
					 */

				}

			}
			Long count1 = 0l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				if (!list.isEmpty()) {
					fileMap1.put(count1, entry.getValue());
					count1++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);

		}

	}

}
