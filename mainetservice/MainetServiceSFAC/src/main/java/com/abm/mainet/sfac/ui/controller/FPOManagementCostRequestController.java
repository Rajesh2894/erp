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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FPOManagementCostDocDetailDTO;
import com.abm.mainet.sfac.dto.FPOManagementCostMasterDTO;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.FPOManagementCostMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FPOManagmentCostRequestModel;

@Controller
@RequestMapping(MainetConstants.Sfac.FPO_MGMT_COST_FORM_HTML)
public class FPOManagementCostRequestController extends AbstractFormController<FPOManagmentCostRequestModel> {
	
	@Autowired
	private IAMasterService iaMasterService;

	
	@Autowired FPOManagementCostMasterService fpoManagementCostService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	


	@Autowired
	private IOrganisationService orgService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_SUMMARY_FORM, MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {
		try {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));
			
			 FPOMasterDto fpoMasterDto =  fpoManagementCostService.getFPODetails(UserSession.getCurrent().getEmployee().getMasId());
			this.getModel().getDto().setFoFpoMasterDto(fpoMasterDto);
			
			/*this.getModel().setCbboMasterList(cbboMasterService.findAllCBBO());
			
			List<FPOManagementCostMasterDTO> fpoManagementCostMasterDTOs = new ArrayList<FPOManagementCostMasterDTO>();
			this.getModel().setMasterDtoList(fPOMasterService.findAllFpo());
			this.getModel().getDto().setFoFpoMasterDto(fPOMasterService.find);
			this.getModel().setFpoManagementCostMasterDTOs(fpoManagementCostMasterDTOs);
			List<CBBOMasterDto> masterDtoList = cbboMasterService.findAllIaAssociatedWithCbbo(UserSession.getCurrent().getEmployee().getEmploginname());
			this.getModel().setIaNameList(masterDtoList);*/
			
		} catch (Exception e) {
			logger.error("Error Ocurred while fething details of financial year or Ia master details by orgid" + e);
		}

	}
	
	

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		
		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long fmcId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		populateModel();
		FPOManagementCostMasterDTO dto = fpoManagementCostService.getDetailById(fmcId);


		dto.setFmcId(fmcId);
		this.getModel().setDto(dto);
		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoManagementCostDetails(Long fpoId, Long  cbboId, Long iaId, Long fyId ,final HttpServletRequest httpServletRequest) {
		List<FPOManagementCostMasterDTO> DtoList = fpoManagementCostService.getAppliacationDetails(fpoId,cbboId,iaId,fyId);
		this.getModel().setFpoManagementCostMasterDTOs(DtoList);
		
		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_SUMMARY_FORM_VALID, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletion")
	public ModelAndView doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getFpoManagementCostDocDetailDTOs().get(id).getDocId()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getFpoManagementCostDocDetailDTOs().get(id).getDocId());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getFpoManagementCostDocDetailDTOs().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getFpoManagementCostDocDetailDTOs();
			

				}

			}



			Long count1 = 0l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list1 = new ArrayList<>(entry.getValue());
				if (!list1.isEmpty()) {
					fileMap1.put(count1, entry.getValue());
					count1++;
				}
			}

		}

		

		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_FORM, MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<FPOManagementCostDocDetailDTO> list = new ArrayList<>();

		for (FPOManagementCostDocDetailDTO fpoManagementCostDocDetailDTO : this.getModel().getDto().getFpoManagementCostDocDetailDTOs()) {

			list.add(fpoManagementCostDocDetailDTO);

		}
		
		int count = 0;
		for (FPOManagementCostDocDetailDTO fpoManagementCostDocDetailDTO : this.getModel().getDto().getFpoManagementCostDocDetailDTOs()) {

			if (fpoManagementCostDocDetailDTO.getDocumentDescription() != null) {
				try {
					BeanUtils.copyProperties(fpoManagementCostDocDetailDTO, list.get(count));
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
		this.getModel().getDto().setFpoManagementCostDocDetailDTOs(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<FPOManagementCostDocDetailDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			FPOManagementCostDocDetailDTO tesDto = list.get(i);
			FPOManagementCostDocDetailDTO newData = new FPOManagementCostDocDetailDTO();
			newData = tesDto;
			newData.setDocumentDescription(this.getModel().getDto().getFpoManagementCostDocDetailDTOs().get(i).getDocumentDescription());

			listData.add(newData);
		}


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

		listData.add(new FPOManagementCostDocDetailDTO());

		this.getModel().getDto().setFpoManagementCostDocDetailDTOs(listData);
		return new ModelAndView(MainetConstants.Sfac.FPO_MGMT_COST_FORM, MainetConstants.FORM_NAME, getModel());
	}

}
