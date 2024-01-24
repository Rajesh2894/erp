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
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.DPREntryDetailsDto;
import com.abm.mainet.sfac.dto.DPREntryMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.DPREntryRequestService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.ui.model.DPREntryFormModel;

@Controller
@RequestMapping(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM_HTML)
public class DPREntryRequestController extends AbstractFormController<DPREntryFormModel>{
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired CBBOMasterService cbboMasterService;
	
	@Autowired FPOMasterService fpoMasterService;
	
	@Autowired DPREntryRequestService dprEntryRequestService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}
	
	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		
		
		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(Long fpoId, Long iaId ,final HttpServletRequest httpServletRequest) {

		
		List<DPREntryMasterDto> dprEntryMasterDtos = dprEntryRequestService.getDPRDetails(fpoId, iaId);
		this.getModel().setDprEntryMasterDtos(dprEntryMasterDtos);

		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long dprId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		
		DPREntryMasterDto dto = dprEntryRequestService.getDetailById(dprId);
		dto.setDprId(dprId);
		this.getModel().setDto(dto);
		
		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	private void populateModel() {

		CBBOMasterDto cbboMasterDto =	cbboMasterService.findById(UserSession.getCurrent().getEmployee().getMasId());
		DPREntryMasterDto dprEntryMasterDto = new DPREntryMasterDto();
		List<CBBOMasterDto> masterDtoList = cbboMasterService.findIAList(cbboMasterDto.getCbboUniqueId());
		
		List<FPOMasterDto> fpoMasterDtos = fpoMasterService.findByCbboId(cbboMasterDto.getCbboId());
		dprEntryMasterDto.setCbboId(cbboMasterDto.getCbboId());
		this.getModel().setDto(dprEntryMasterDto);
		this.getModel().setCbboMasterDtos(masterDtoList);
		this.getModel().setCbboMasterDto(cbboMasterDto);
		this.getModel().setFpoMasterDtos(fpoMasterDtos);

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletion")
	public ModelAndView doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getDprEntryDetailsDtos().get(id).getDprdId()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getDprEntryDetailsDtos().get(id).getDprdId());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getDprEntryDetailsDtos().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getDprEntryDetailsDtos();
			

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

		

		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM, MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DPREntryDetailsDto> list = new ArrayList<>();
		
		for (DPREntryDetailsDto dprEntryDetailsDto : this.getModel().getDto().getDprEntryDetailsDtos()) {

			list.add(dprEntryDetailsDto);

		}
		
		int count = 0;
		for (DPREntryDetailsDto dprEntryDetailsDto : this.getModel().getDto().getDprEntryDetailsDtos()) {

			if (dprEntryDetailsDto.getDprSection() != null) {
				try {
					BeanUtils.copyProperties(dprEntryDetailsDto, list.get(count));
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
		this.getModel().getDto().setDprEntryDetailsDtos(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<DPREntryDetailsDto> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			DPREntryDetailsDto tesDto = list.get(i);
			DPREntryDetailsDto newData = new DPREntryDetailsDto();
			newData = tesDto;
			newData.setDprSection(this.getModel().getDto().getDprEntryDetailsDtos().get(i).getDprSection());
			
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

		listData.add(new DPREntryDetailsDto());

		this.getModel().getDto().setDprEntryDetailsDtos(listData);
		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}
	

}
