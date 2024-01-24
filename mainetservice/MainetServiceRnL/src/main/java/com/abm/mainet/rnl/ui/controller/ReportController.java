package com.abm.mainet.rnl.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.dto.ReportDTO;
import com.abm.mainet.rnl.repository.EstateContractMappingRepository;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.service.IReportService;
import com.abm.mainet.rnl.ui.model.ReportModel;

@Controller
@RequestMapping(value = { "/EstateRevenueReport.html", "EstateOutstandingReport.html", "EstateDemandRegister.html" })
public class ReportController extends AbstractFormController<ReportModel> {

	@Autowired
	IReportService reportService;

	@Autowired
	private IEstateService iEstateService;

	@Autowired
	private IEstatePropertyService iEstatePropertyService;
	
	@Autowired
	EstateContractMappingRepository estateContractMappingRepository;

	@RequestMapping()
	public String index(final HttpServletRequest request, final ModelMap model) {
		getModel().bind(request);
		String INDEX_PAGE = null;
		if (request.getRequestURI().contains("Revenue")) {
			model.addAttribute("headerName", "Revenue Reports");
			model.addAttribute("resetPage", "EstateRevenueReport.html");
			INDEX_PAGE = "revenueReport";
		} else if (request.getRequestURI().contains("Outstanding")) {
			model.addAttribute("testPage", "Outstanding Reports");
			model.addAttribute("resetPage", "EstateOutstandingReport.html");
			INDEX_PAGE = "outstandingReport";
		} else if (request.getRequestURI().contains("DemandRegister")) {
			model.addAttribute("testPage", "Demand Register Reports");
			model.addAttribute("resetPage", "EstateDemandRegister.html");
			Map<Long, String> financialYearMap = ApplicationContextProvider.getApplicationContext()
					.getBean(TbFinancialyearService.class).getAllFinincialYear();
			final List<Object[]> estateMasters = iEstateService
					.findEstateRecordsForProperty(UserSession.getCurrent().getOrganisation().getOrgid());
			model.addAttribute("financialYearMap", financialYearMap);
			model.addAttribute("estateMasters", estateMasters);
			INDEX_PAGE = "demandRegister";
		}
		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
		return INDEX_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST, params = "summaryReport")
	public ModelAndView findReport(@RequestParam("occupancyType") final String occupancyType,
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
			final HttpServletRequest request) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long serviceId = null;
		// PREFIX - ROC - RENT(RE), LEASE(LE)
		// service code : ESR - RENT , CBP - LEASE
		if (occupancyType.equalsIgnoreCase("RE")) {
			// RENT
			serviceId = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.RNL_ESTATE_BOOKING, orgId).getSmServiceId();
		} else {
			// LEASE
			serviceId = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.EstateContract.CBP, orgId).getSmServiceId();
		}
		// fetch REVENUE summary report
		this.getModel().setReportDTO(null);
		this.getModel().setSummaryReportDetails(new ArrayList<ReportDTO>());
		reportService.fetchRevenueReport(this.getModel(), occupancyType, fromDate, toDate, serviceId, orgId);
		return new ModelAndView("revenueReportSummary", MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "outstandingSummaryReport")
	public ModelAndView outstandingSummaryReport(@RequestParam("date") final String date,
			final HttpServletRequest request) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// fetch outstanding report list
		this.getModel().setReportDTO(null);
		this.getModel().setSummaryReportDetails(new ArrayList<ReportDTO>());
		reportService.fetchOutstandingReport(this.getModel(), date, orgId);
		this.getModel().getReportDTO().setAsOnDate(date);
		return new ModelAndView("outstandingReportSummary", MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "demandRegisterReport")
	public ModelAndView demandRegisterReport(@RequestParam("financialYear") final String financialYear,
			final HttpServletRequest request) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setReportDTO(null);

		this.getModel().setSummaryReportDetails(new ArrayList<ReportDTO>());

		// make data for demand register
		reportService.fetchDemandRegisterReport(this.getModel(), financialYear, orgId);
		this.getModel().getReportDTO().setFinancialYear(financialYear);
		return new ModelAndView("demandRegisterSummary", MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "demandRegisterReportUsingProp")
	public ModelAndView demandRegisterReportUsingProp(@RequestParam("estateId") final Long estateId,
			@RequestParam("propId") Long  propId,@RequestParam("financialYear") final String financialYear, final HttpServletRequest request) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setReportDTO(null);
		this.getModel().setSummaryReportDetails(new ArrayList<ReportDTO>());
		if(propId == 0) {
			List<EstatePropGrid> list = getPropertyList(estateId);
			if(list != null) {
				reportService.fetchDemandRegisterReportAllProp(this.getModel(), estateId, list,financialYear, orgId);
			}
		}else {
			reportService.fetchDemandRegisterReportUsingProp(this.getModel(), estateId, propId,financialYear, orgId);
		}
		return new ModelAndView("demandRegisterSummary", MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "propList", method = RequestMethod.POST)
	public List<EstatePropGrid> getPropertyList(@RequestParam("esId") final Long esId) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final List<EstatePropGrid> list = iEstatePropertyService
				.findGridFilterRecords(UserSession.getCurrent().getOrganisation().getOrgid(), esId);
		List<EstatePropGrid> finalList = new ArrayList<>();
		List<ContractMastEntity> contracts =  new ArrayList<>();
		for(EstatePropGrid prop :list) {
			List<ContractMastEntity> contract = estateContractMappingRepository.findAllMappedProperty(orgId, prop.getPropId());
			if(contract != null && contract.size() != 0) {
				contracts.addAll(contract);
				finalList.add(prop);			}
		}
		if(finalList !=null){
			return finalList;
		}
		return null;
		
	}

}
