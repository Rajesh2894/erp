package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.BidCompareDto;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.CommercialBIDDetailDto;
import com.abm.mainet.workManagement.dto.TechnicalBIDDetailDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.repository.BIDDetailRepository;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.ui.model.BIDMasterModel;

@Controller
@RequestMapping(value= {"/BIDMaster.html"})
public class BIDMasterController extends AbstractFormController<BIDMasterModel> {

	@Autowired
	private TbAcVendormasterService vendorMasterService;

	@Autowired
	private TenderInitiationService tenderInitiationService;
	
	

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		List<TenderMasterDto> tenderList = new ArrayList<TenderMasterDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tenderList = tenderInitiationService.findAllTenderList(orgId);
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
				tenderInitiationService.getAllActiveProjectsForTndServices(orgId));
		List<String> tndNo = new ArrayList<String>();
		tenderList.forEach(dto -> {
			if (dto.getTenderNo() != null) {
				tndNo.add(dto.getTenderNo());

			}
		});
		model.addAttribute("tnderNoList", tndNo);

		return index();
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_BID_COMPARE_FORM, method = RequestMethod.POST)
	public ModelAndView openBidCompareForm(final HttpServletRequest httpServletRequest, @RequestParam Long tndId) {
		bindModel(httpServletRequest);
		List<TbAcVendormaster> acVendormaster = new ArrayList<TbAcVendormaster>();
		List<TbAcVendormaster> acVendormasters = vendorMasterService
				.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> vendorList = new HashMap<Long, String>();

		List<BidMasterDto> bidMasterDtos = tenderInitiationService
				.getBidMAsterByTndId(UserSession.getCurrent().getOrganisation().getOrgid(), tndId);
		if (!bidMasterDtos.isEmpty()) {
			bidMasterDtos.forEach(dto -> {
				acVendormasters.forEach(vendoMasterDto -> {
					if (!vendorList.containsKey(vendoMasterDto.getVmVendorid())) {
						if (dto.getVendorId() == vendoMasterDto.getVmVendorid()) {
							vendorList.put(vendoMasterDto.getVmVendorid(), vendoMasterDto.getVmVendorname());
						}
					}
				});
			});
		}
		this.getModel().setVendorMapList(vendorList);
		this.getModel().setTndId(tndId);
		return new ModelAndView("BIDMaster/compare", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_BID_SEARCH_FORM, method = RequestMethod.POST)
	public ModelAndView bidCompareSearchPage(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		List<TenderMasterDto> tenderList = new ArrayList<TenderMasterDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tenderList = tenderInitiationService.findAllTenderList(orgId);

		List<String> tndNo = new ArrayList<String>();
		tenderList.forEach(dto -> {
			if (dto.getTenderNo() != null) {
				tndNo.add(dto.getTenderNo());

			}
		});
		model.addAttribute("tnderNoList", tndNo);

		return new ModelAndView("BIDMaster/search", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SEARCH_TENDER, method = RequestMethod.POST)
	public ModelAndView searchTender(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam String tenderNo, @RequestParam Date tenderDate) {

		sessionCleanup(httpServletRequest);
		List<TenderMasterDto> tenderSearchList = new ArrayList<TenderMasterDto>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tenderSearchList = tenderInitiationService.findAllTenderList(orgId);

		List<String> tndNo = new ArrayList<String>();
		tenderSearchList.forEach(dto -> {
			if (dto.getTenderNo() != null) {
				tndNo.add(dto.getTenderNo());

			}
		});
		model.addAttribute("tnderNoList", tndNo);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TenderMasterDto> tenderList = tenderInitiationService.searchTenderByTendernoAndDate(orgid, tenderNo,
				tenderDate);
		model.addAttribute(MainetConstants.WorksManagement.TENDER_LIST, tenderList);

		return new ModelAndView("BIDMaster/search", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SEARCH_BID_DETAILS, method = RequestMethod.POST)
	public ModelAndView searchBIdDetByVendorIdandBidId(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam(name = "bidId", required = false) String bidId,
			@RequestParam(name = "vendorIds", required = false) String vendorIds) {
		String idList = vendorIds;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Long> vendorIdList = null;
		if (idList != null && !idList.isEmpty()) {
			vendorIdList = new ArrayList<>();
			String fileArray[] = idList.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				vendorIdList.add(Long.valueOf(fields));
			}
		}
		List<BidMasterDto> bidMasterDtos = new ArrayList<BidMasterDto>();
		if (vendorIdList != null && !vendorIdList.isEmpty()) {
			vendorIdList.forEach(vendorId -> {
				BidMasterDto bidMasterDto = tenderInitiationService.searchBIdDetByVendorIdandBidId(orgId, bidId,
						vendorId, this.getModel().getTndId());
				bidMasterDtos.add(bidMasterDto);
			});
		}
		if (vendorIdList == null && bidId != null) {
			BidMasterDto bidMasterDto = tenderInitiationService.searchBIdDetByVendorIdandBidId(orgId, bidId,
					null, this.getModel().getTndId());
			bidMasterDtos.add(bidMasterDto);
		}

		List<TbAcVendormaster> acVendormasters = vendorMasterService
				.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		bidMasterDtos.forEach(dto -> {
			acVendormasters.forEach(vendorDto -> {
				if (dto.getVendorId() == vendorDto.getVmVendorid()) {
					dto.setVendorName(vendorDto.getVmVendorname());
				}
			});
		});
		// List<Map<String,Long>> paramDescMap=new ArrayList<Map<String,Long>>();
		List<BidCompareDto> bidCompareDtos = new ArrayList<BidCompareDto>();
		List<String> paramName = new ArrayList<String>();
		Long count = 0l;
		Map<Long, Map<String, Long>> paramMap = new HashMap<Long, Map<String, Long>>();
		for (BidMasterDto dto : bidMasterDtos) {

			BidCompareDto bidCompareDto = new BidCompareDto();
			bidCompareDto.setVendorName(dto.getVendorName());
			for (CommercialBIDDetailDto commDto : dto.getCommercialBIDDetailDtos()) {
				Map<String, Long> map = new HashMap<String, Long>();
				if (!paramName.contains(commDto.getParamDescId())) {
					paramName.add(commDto.getParamDescId());
				}
				map.put(commDto.getParamDescId(), commDto.getFinalMark());
				paramMap.put(count, map);
				count++;
			}

			bidCompareDto.setParamMap(paramMap);
			bidCompareDto.setParamName(paramName);
			bidCompareDtos.add(bidCompareDto);
		}

		model.addAttribute("bidCompaareDtos", bidCompareDtos);
		// model.addAttribute("mapCreated", bidCompareDtos.get);

		model.addAttribute("paramNames", paramName);

		List<BidCompareDto> bidCompareDtosforTech = new ArrayList<BidCompareDto>();
		List<String> paramNameforTech = new ArrayList<String>();
		Long countforTech = 0l;
		Map<Long, Map<String, Long>> paramMapforTech = new HashMap<Long, Map<String, Long>>();
		for (BidMasterDto dto : bidMasterDtos) {

			BidCompareDto bidCompareDto = new BidCompareDto();
			bidCompareDto.setVendorName(dto.getVendorName());
			for (TechnicalBIDDetailDto commDto : dto.getTechnicalBIDDetailDtos()) {
				Map<String, Long> map = new HashMap<String, Long>();
				if (!paramNameforTech.contains(commDto.getParamDescId())) {
					paramNameforTech.add(commDto.getParamDescId());
				}
				map.put(commDto.getParamDescId(), commDto.getFinalMark());
				paramMapforTech.put(countforTech, map);
				countforTech++;
			}

			bidCompareDto.setParamMap(paramMapforTech);
			bidCompareDto.setParamName(paramNameforTech);
			bidCompareDtosforTech.add(bidCompareDto);
		}

		model.addAttribute("bidCompareDtosforTech", bidCompareDtosforTech);
		// model.addAttribute("mapCreated", bidCompareDtos.get);

		model.addAttribute("paramNameforTech", paramNameforTech);
		model.addAttribute("flag", "C");

		return new ModelAndView("BIDMaster/compare", MainetConstants.FORM_NAME, this.getModel());
	}
    
	@RequestMapping(params = "BIDEntry", method = { RequestMethod.POST })
	public ModelAndView BIDEntry(final Model model, HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,tenderInitiationService.getAllActiveTenderProjects(orgId));
		this.getModel().setTnderNoList(tenderInitiationService.findAllApprovedTender(orgId));
		
		ModelAndView mv = null;
		String viewName = "BIDEntryandBidFinalization";
		mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
		List<BidMasterDto> bidMAsterList=new ArrayList<BidMasterDto>();
		bidMAsterList=tenderInitiationService.getBidMAsterByOrgId(orgId);
		this.getModel().setBidDtoList(bidMAsterList);

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "ADD")
	public ModelAndView AddBIDEntry(final Model model, final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
				tenderInitiationService.getAllActiveTenderProjects(orgId));
		ModelAndView mv = new ModelAndView("BIDEntryForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "Technical")
	public ModelAndView bidTechnicalEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		this.getModel().setBidMasterDto(tenderInitiationService
				.getBidMAsterByBIDId(UserSession.getCurrent().getOrganisation().getOrgid(), bidId));
		this.getModel().setBidId(bidId);
		ModelAndView mv = new ModelAndView("TechnicalDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "Financial")
	public ModelAndView bidFinancialEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = new ModelAndView("FinancialDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		this.getModel().setBidId(bidId);
		this.getModel().setBidMasterDto(tenderInitiationService
				.getBidMAsterByBIDId(UserSession.getCurrent().getOrganisation().getOrgid(), bidId));
		TenderMasterDto tenderDto=tenderInitiationService.getTenderDetailsByTenderNo(this.getModel().getBidMasterDto().getTndNo(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWorkEstimateAmt(tenderDto.getWorkDto().get(0).getWorkEstimateAmt());
		return mv;

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "gettender")
	public ModelAndView gettender(final Model model, @RequestParam("projId") final Long projId,
			final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
				tenderInitiationService.getAllActiveTenderProjects(orgId));
		this.getModel().setTnderNoList(tenderInitiationService.findAllApprovedTender(orgId));
		this.getModel().setProjectid(projId);
		return new ModelAndView("BIDEntryForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getTenderData")
	public ModelAndView getTenderData(final Model model, @RequestParam("projId") final Long projId,
			final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,
				tenderInitiationService.getAllActiveTenderProjects(orgId));
		this.getModel().setTnderNoList(tenderInitiationService.findAllApprovedTender(orgId));
		this.getModel().getBidMasterDto().setProjectid(projId);
		return new ModelAndView("BIDEntryandBidFinalizationValid", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "saveData")
	public boolean saveData(final HttpServletRequest request) {
		getModel().bind(request);
		this.getModel().saveForm();
		return true;

	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView searchData(final HttpServletRequest request, @RequestParam Long projId,
			@RequestParam String tndNo) {
		this.getModel().bind(request);
		List<BidMasterDto> bidMasterDtos = tenderInitiationService
				.getBidMAster(UserSession.getCurrent().getOrganisation().getOrgid(), projId, tndNo);
		this.getModel().setBidDtoList(bidMasterDtos);
		request.setAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,tenderInitiationService.getAllActiveTenderProjects(UserSession.getCurrent().getOrganisation().getOrgid()));
		if(projId != null)
			this.getModel().getBidMasterDto().setProjectid(projId);
		if(tndNo != null)
			this.getModel().getBidMasterDto().setTndNo(tndNo);
		
		return new ModelAndView("BIDEntryandBidFinalizationValid", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "saveTechData")
	public boolean saveTechData(final HttpServletRequest request) {
		getModel().bind(request);
		this.getModel().saveTechData();
		return true;
	}
	 
	@RequestMapping(params = "bidEntryReset", method = { RequestMethod.POST })
	public ModelAndView bidEntryRest(final Model model, HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute(MainetConstants.WorksManagement.TENDER_PROJECT,tenderInitiationService.getAllActiveTenderProjects(orgId));
		this.getModel().setTnderNoList(tenderInitiationService.findAllTenderList(orgId));
		ModelAndView mv = null;
		String viewName = "BIDEntryandBidFinalizationValid";
		mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
		List<BidMasterDto> bidMAsterList=new ArrayList<BidMasterDto>();
		bidMAsterList=tenderInitiationService.getBidMAsterByOrgId(orgId);
		this.getModel().setBidDtoList(bidMAsterList);

		return mv;
	}
	
	@RequestMapping(params = "CheckName", method = RequestMethod.POST)
	public @ResponseBody Boolean CheckName(@RequestParam("bidNo") final String bidNo, final HttpServletRequest httpServletRequest){
	    Long id = tenderInitiationService.BidCount(bidNo, UserSession.getCurrent().getOrganisation().getOrgid());
	    if (id == 0) {
	        return true;
	    }
	    else {
	        return false;
	    }
	}
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "viewTechnical")
	public ModelAndView viewBidTechnicalEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setBidMasterDto(tenderInitiationService
				.getBidMAsterByBIDId(UserSession.getCurrent().getOrganisation().getOrgid(), bidId));
		this.getModel().setBidId(bidId);
		ModelAndView mv = new ModelAndView("TechnicalDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "editTechnical")
	public ModelAndView editBidTechnicalEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		this.getModel().setBidMasterDto(tenderInitiationService
				.getBidMAsterByBIDId(UserSession.getCurrent().getOrganisation().getOrgid(), bidId));
		this.getModel().setBidId(bidId);
		ModelAndView mv = new ModelAndView("TechnicalDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "viewFinancial")
	public ModelAndView viewBidFinancialEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = new ModelAndView("FinancialDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		this.getModel().setBidId(bidId);
		this.getModel().setBidMasterDto(tenderInitiationService
				.viewandeditFinancial(UserSession.getCurrent().getOrganisation().getOrgid(), bidId,this.getModel().getSaveMode()));
		TenderMasterDto tenderDto=tenderInitiationService.getTenderDetailsByTenderNo(this.getModel().getBidMasterDto().getTndNo(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWorkEstimateAmt(tenderDto.getWorkDto().get(0).getWorkEstimateAmt());
		return mv;

	}
	
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "editFinancial")
	public ModelAndView editBidFinancialEntry(final Model model, final HttpServletRequest httpServletRequest,
			@RequestParam("bidId") final Long bidId) {
		getModel().bind(httpServletRequest);
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPELIST, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.VTY, UserSession.getCurrent().getOrganisation()));
		model.addAttribute(MainetConstants.WorksManagement.VALUE_TYPEAMOUNT, CommonMasterUtility
				.getLookUps(MainetConstants.WorksManagement.TPA, UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = new ModelAndView("FinancialDetailsForm", MainetConstants.FORM_NAME, this.getModel());
		this.getModel().setBidId(bidId);
		this.getModel().setBidMasterDto(tenderInitiationService
				.viewandeditFinancial(UserSession.getCurrent().getOrganisation().getOrgid(), bidId,this.getModel().getSaveMode()));
		TenderMasterDto tenderDto=tenderInitiationService.getTenderDetailsByTenderNo(this.getModel().getBidMasterDto().getTndNo(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWorkEstimateAmt(tenderDto.getWorkDto().get(0).getWorkEstimateAmt());
		return mv;

	}
}
