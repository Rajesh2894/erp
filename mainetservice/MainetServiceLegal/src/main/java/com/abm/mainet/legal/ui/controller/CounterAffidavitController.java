package com.abm.mainet.legal.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.CounterAffidavitDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICounterAffidavit;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.model.CounterAffidavitModel;

@Controller
@RequestMapping("/CounterAffidavit.html")
public class CounterAffidavitController extends AbstractFormController<CounterAffidavitModel> {

	@Autowired
	private ICaseEntryService caseEntryService;
	@Autowired
	private ICourtMasterService courtMasterService;

	@Autowired
	private ILocationMasService locationMasService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IAttachDocsService attachDocsService;
	@Autowired
	ICounterAffidavit iCounterAffidavit;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagP,
				PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();

		this.getModel().setCaseEntryDTOList(caseEntryService.getCaseEntryByStatus(closedCaseStatus,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		return defaultResult();
	}

	@RequestMapping(params = MainetConstants.CommonConstants.EDIT, method = RequestMethod.POST)
	public ModelAndView editOrViewCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {

		fileUpload.sessionCleanUpForFileUpload();
		bindModel(httpServletRequest);
		this.getModel().setSaveMode(mode);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		CaseEntryDTO dto = caseEntryService.getCaseEntryById(id);
		this.getModel().setCaseEntryDTO(dto);

		CounterAffidavitDTO counterAffidavitDTO = iCounterAffidavit.getCounterAffidavitBysceId(id, orgId);

		if (counterAffidavitDTO != null)
			this.getModel().setCounterAffidavitDTO(counterAffidavitDTO);
		this.getModel()
				.setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.Legal.COUNTER_AFFIDAVIT + MainetConstants.DOUBLE_BACK_SLACE + id));
		 this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                 MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));
		 List<CaseEntryDetailDTO> caseEntryDetailDTOs = this.getModel().getCaseEntryDTO().getTbLglCasePddetails();
		 List<CaseEntryDetailDTO> caseEntryDetailDTOsP = new ArrayList<CaseEntryDetailDTO>();
		 List<CaseEntryDetailDTO> caseEntryDetailDTOsD = new ArrayList<CaseEntryDetailDTO>();
		 for (CaseEntryDetailDTO caseEntryDetailDTO : caseEntryDetailDTOs) {
	            if (caseEntryDetailDTO.getCsedFlag().equalsIgnoreCase("P")) {
	                caseEntryDetailDTOsP.add(caseEntryDetailDTO);
	            } else if (caseEntryDetailDTO.getCsedFlag().equalsIgnoreCase("D")) {
	                caseEntryDetailDTO.setCsedParty(caseEntryDetailDTO.getCsedPartyType());
	                caseEntryDetailDTOsD.add(caseEntryDetailDTO);
	            }
	        }
		 this.getModel().setPlenfiffEntryDetailDTOList(caseEntryDetailDTOsP);
		 this.getModel().setDefenderEntryDetailDTOList(caseEntryDetailDTOsD);
		ModelAndView mv = new ModelAndView("CounterAffidavitForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());

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
				.getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		return courtMasterDTOList;
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
