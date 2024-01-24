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
import com.abm.mainet.sfac.dto.FundReleaseRequestDetailDto;
import com.abm.mainet.sfac.dto.FundReleaseRequestMasterDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.FundReleaseRequestService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FundReleaseRequestModel;

@Controller
@RequestMapping(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM_HTML)
public class FundReleaseRequestController extends AbstractFormController<FundReleaseRequestModel>{
	
	@Autowired
	private IOrganisationService orgService;
	
	@Autowired
	private IAMasterService iaMasterService;
	
	@Autowired FundReleaseRequestService fundReleaseRequestService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateModel();

		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}
	
	private void populateModel() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));
		
		IAMasterDto iaMasterDto = iaMasterService.findByIaId(UserSession.getCurrent().getEmployee().getMasId());
		this.getModel().getDto().setIaId(iaMasterDto.getIAId());
		this.getModel().getDto().setIaName(iaMasterDto.getIAName());
		
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long frrId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		populateModel();
		FundReleaseRequestMasterDto dto = fundReleaseRequestService.getDetailById(frrId);

		this.getModel().setDto(dto);
		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();

		this.getModel().setViewMode(MainetConstants.FlagA);

		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(Long iaId, String applicationRef, Long fy ,final HttpServletRequest httpServletRequest) {

		populateModel();
		List<FundReleaseRequestMasterDto> fundReleaseRequestMasterDtos = fundReleaseRequestService.getFundReleaseReqDetails(iaId,applicationRef,fy);
		this.getModel().setFundReleaseRequestMasterDtos(fundReleaseRequestMasterDtos);

		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletion")
	public ModelAndView doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(id).getFrrdId()!=null) {
			enclosureRemoveById.add(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(id).getFrrdId());
			
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		
		this.getModel().getDto().getFundReleaseRequestDetailDtos().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getDto().getFundReleaseRequestDetailDtos();
			

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

		

		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM, MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<FundReleaseRequestDetailDto> list = new ArrayList<>();
		
		for (FundReleaseRequestDetailDto fundReleaseRequestDetailDto : this.getModel().getDto().getFundReleaseRequestDetailDtos()) {

			list.add(fundReleaseRequestDetailDto);

		}
		
		int count = 0;
		for (FundReleaseRequestDetailDto fundReleaseRequestDetailDto : this.getModel().getDto().getFundReleaseRequestDetailDtos()) {

			if (fundReleaseRequestDetailDto.getPurposeFor() != null) {
				try {
					BeanUtils.copyProperties(fundReleaseRequestDetailDto, list.get(count));
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
		this.getModel().getDto().setFundReleaseRequestDetailDtos(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<FundReleaseRequestDetailDto> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			FundReleaseRequestDetailDto tesDto = list.get(i);
			FundReleaseRequestDetailDto newData = new FundReleaseRequestDetailDto();
			newData = tesDto;
			newData.setPurposeFor(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getPurposeFor());
			newData.setAllocatedNoOfFPO(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getAllocatedNoOfFPO());
			newData.setAllocatedBudget(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getAllocatedBudget());
			newData.setTotalFundRelTillDate(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getTotalFundRelTillDate());
			newData.setUtilizedAmount(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getUtilizedAmount());
			newData.setNewDemand(this.getModel().getDto().getFundReleaseRequestDetailDtos().get(i).getNewDemand());

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

		listData.add(new FundReleaseRequestDetailDto());

		this.getModel().getDto().setFundReleaseRequestDetailDtos(listData);
		return new ModelAndView(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM, MainetConstants.FORM_NAME, getModel());
	}

}
