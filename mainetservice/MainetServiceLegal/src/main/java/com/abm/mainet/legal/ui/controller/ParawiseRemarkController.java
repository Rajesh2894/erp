package com.abm.mainet.legal.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.ParawiseRemarkDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IParawiseRemarkService;
import com.abm.mainet.legal.ui.model.ParawiseRemarkModel;

@Controller
@RequestMapping("/ParawiseRemark.html")
public class ParawiseRemarkController extends AbstractFormController<ParawiseRemarkModel> {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ICourtMasterService courtMasterService;

	@Autowired
	private ICaseEntryService caseEntryService;

	@Autowired
	private IParawiseRemarkService parawiseRemarkService;

	@Autowired
	private ILocationMasService locationMasService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private IAdvocateMasterService advocateMasterService;
	
	@Autowired
	TbOrganisationService tbOrganisationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		 Long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
		 this.getModel().setParentOrgid(parentOrgid);
		this.getModel().setCommonHelpDocs("ParawiseRemark.html");
		Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
				PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();

		this.getModel().setCaseEntryDTOList(caseEntryService.searchCaseEntry(null, null, null, null, null, null, null,
				closedCaseStatus, UserSession.getCurrent().getOrganisation().getOrgid(), null,null));

		ModelAndView mv = defaultResult();
		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = { "searchParawiseRemark" }, method = RequestMethod.POST)
	public ModelAndView searchCaseEntry(final HttpServletRequest request,
			@RequestParam(required = false) String cseSuitNo, @RequestParam(required = false) Long cseDeptid,
			@RequestParam(required = false) Long cseCatId1, @RequestParam(required = false) Long cseCatId2,
			@RequestParam(required = false) Long cseTypId, @RequestParam(required = false) Date cseDate,
			@RequestParam(required = false) Long crtId) {

		sessionCleanup(request);
		if (crtId != null) {
			this.getModel().getCaseEntryDTO().setCrtId(crtId);
		}
		if (cseDeptid != null) {
			this.getModel().getCaseEntryDTO().setCseDeptid(cseDeptid);
		}
		if (StringUtils.isNotEmpty(cseSuitNo)) {
			this.getModel().getCaseEntryDTO().setCseSuitNo(cseSuitNo);
		}
		if (cseCatId1 != null) {
			this.getModel().getCaseEntryDTO().setCseCatId1(cseCatId1);
		}
		if (cseCatId2 != null) {
			this.getModel().getCaseEntryDTO().setCseCatId2(cseCatId2.longValue());
		}
		if (cseDate != null) {
			this.getModel().getCaseEntryDTO().setCseDate(cseDate);
		}
		if (cseTypId != null) {
			this.getModel().getCaseEntryDTO().setCseTypId(cseTypId);
		}

		ModelAndView mv = new ModelAndView("searchParawiseRemark", MainetConstants.FORM_NAME, this.getModel());

		this.getModel()
				.setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.Legal.CASE_ENTRY + MainetConstants.DOUBLE_BACK_SLACE + crtId));
		Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
				PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();
		this.getModel().setCaseEntryDTOList(
				caseEntryService.searchCaseEntry(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate, crtId,
						closedCaseStatus, UserSession.getCurrent().getOrganisation().getOrgid(), null,null));

		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());
		mv.addObject("advocates", getAdvocateMasterList());
		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrViewParawiseRemark(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
				.findDefaultOrganisation();
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode(mode);
		ParawiseRemarkModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		if (orgId.longValue() == org.getOrgid()) {
			model.setParawiseRemarkDTOList(parawiseRemarkService.getAllParawiseRemark(id));
			for (ParawiseRemarkDTO dto : this.getModel().getParawiseRemarkDTOList()) {
				if(dto.getOrgid() == org.getOrgid()) {
				dto.setAttachDocsList1(attachDocsService.findByCode(orgId,
						dto.getCaseId() + MainetConstants.WINDOWS_SLASH + null));
				}else {
					dto.setAttachDocsList1(attachDocsService.findByCode(orgId,
							dto.getCaseId() + MainetConstants.WINDOWS_SLASH + dto.getParId()));
				}
			}
		} else {
			List<ParawiseRemarkDTO> parawiseRemarkDTO = parawiseRemarkService
					.getAllParawiseRemark(id);
			parawiseRemarkDTO.forEach(dto ->{
				dto.setAttachDocsList(
						attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
								MainetConstants.Legal.CASE_ENTRY + MainetConstants.WINDOWS_SLASH + id));
				if(StringUtils.isNotEmpty(dto.getParUadRemark())) {
					if(dto.getOrgid() == orgId) {
					dto.setAttachDocsList1(attachDocsService.findByCode(org.getOrgid(),
							dto.getCaseId() + MainetConstants.WINDOWS_SLASH + dto.getParId()));
					}
					else if(dto.getOrgid() == org.getOrgid()) {
						dto.setAttachDocsList1(attachDocsService.findByCode(org.getOrgid(),
								dto.getCaseId() + MainetConstants.WINDOWS_SLASH + null));
					}
					else {
						dto.setAttachDocsList1(attachDocsService.findByCode(org.getOrgid(),
								dto.getCaseId() + MainetConstants.WINDOWS_SLASH + null));
					}
					this.getModel().getParawiseRemarkDTOListView().add(dto);
				}else {
					this.getModel().getParawiseRemarkDTOList().add(dto);
				}
			});
		}
		
		model.setCaseEntryDTO(caseEntryService.getCaseEntryById(id));
		// Defect #33500 uploaded document should be visible on uad and ulb both
		model.setCaseAttachDocsList(attachDocsService.findByCode(model.getCaseEntryDTO().getOrgid().longValue(),
				MainetConstants.Legal.CASE_ENTRY + MainetConstants.WINDOWS_SLASH + id));
		ModelAndView mv = new ModelAndView("ParawiseRemarkForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());
		mv.addObject("advocates", getAdvocateMasterList());
		return mv;
	}

	private List<Department> loadDepartmentList() {
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); // Active
																												// = "A"
		return departments;
	}

	private List<CourtMasterDTO> getCourtMasterList() {

		List<CourtMasterDTO> courtMasterDTOList = courtMasterService
				.getAllCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> courtMap = courtMasterDTOList.stream()
				.collect(Collectors.toMap(CourtMasterDTO::getId, CourtMasterDTO::getCrtName));
		if (CollectionUtils.isNotEmpty(this.getModel().getCaseEntryDTOList())) {
			this.getModel().getCaseEntryDTOList().forEach(master -> {
				master.setCrtName(courtMap.get(master.getCrtId()));
			});
		}
		return courtMasterDTOList;
	}

	private List<AdvocateMasterDTO> getAdvocateMasterList() {
		List<AdvocateMasterDTO> advocateMasterDTOList = advocateMasterService
				.getAllAdvocateMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> advocateMap = advocateMasterDTOList.stream()
				.collect(Collectors.toMap(AdvocateMasterDTO::getAdvId, AdvocateMasterDTO::getAdvFirstNm));
		if (CollectionUtils.isNotEmpty(this.getModel().getCaseEntryDTOList())) {
			this.getModel().getCaseEntryDTOList().forEach(master -> {
				master.setCrtName(advocateMap.get(master.getAdvId()));
			});
		}
		return advocateMasterDTOList;
	}

	private List<LocationDTO> getLocationList() {
		List<LocationMasEntity> locationMasEntityList = locationMasService
				.getlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<LocationDTO> locationDTOList = new ArrayList<>();
		for (LocationMasEntity locationMasEntity : locationMasEntityList) {
			LocationDTO locationDTO = new LocationDTO();

			locationDTO.setLocId(locationMasEntity.getLocId());
			locationDTO.setLocName(locationMasEntity.getLocNameEng());
			locationDTO.setLocNameEng(locationMasEntity.getLocNameEng());
			locationDTO.setLocNameReg(locationMasEntity.getLocNameReg());
			locationDTO.setLandmark(locationMasEntity.getLandmark());
			locationDTO.setPincode(locationMasEntity.getPincode());

			locationDTOList.add(locationDTO);
		}
		return locationDTOList;
	}
	
}
