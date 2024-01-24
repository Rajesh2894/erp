package com.abm.mainet.care.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.care.dao.IComplaintDAO;
import com.abm.mainet.care.dto.report.ComplaintDTO;
import com.abm.mainet.care.dto.report.ComplaintGradeSummary;
import com.abm.mainet.care.dto.report.ComplaintReportDTO;
import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.care.dto.report.SummaryField;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.repository.ComplaintJpaRepository;
import com.abm.mainet.common.repository.SmsEmailRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.domain.SmsEmailTransaction;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class CareReportService implements ICareReportService {

	@Autowired
	private IComplaintDAO complaintDAO;

	@Resource
	private TbDepartmentJpaRepository tbDepartmentJpaRepository;

	@Resource
	private ComplaintJpaRepository complaintJpaRepository;

	@Autowired
	private ApplicationSession applicationSession;

	@Autowired
	private IOrganisationService organisationService;
	@Autowired
	private SmsEmailRepository smsEmailRepository;

	@Override
	public ComplaintReportDTO getDepartmentWiseComplaintReport(ComplaintReportRequestDTO complaintReportRequest) {
           
		 ComplaintReportDTO crp = null ;
		if (complaintReportRequest.getReportName() == 'D' && CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId())) {
			Set<ComplaintDTO> complaints  = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
				complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
				complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(),	complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
				complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
				complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
			
			/*Adding descion based on LangId*/
			if (CollectionUtils.isNotEmpty(complaints)) {
				complaints.stream().forEach(c -> {
					String decisionCode = c.getStatus();
					String code = MainetConstants.WorkFlow.Process.CARE+"." + decisionCode.toLowerCase();
					if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
						c.setStatus(
								applicationSession.getMessage(code, code, new Locale(MainetConstants.REG_ENG.ENGLISH)));
					else
						c.setStatus(applicationSession.getMessage(code, code,
								new Locale(MainetConstants.REG_ENG.REGIONAL)));
				});
			}
		     crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
			crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
			crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
			crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
			crp.setComplaints(complaints);
			}
		else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'S') {
				List<ComplaintDTO> complaintsList = complaintDAO.getComplaintsSummary(complaintReportRequest.getOrgId(),
						complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
						complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
						complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
						complaintReportRequest.getCodIdOperLevel2(),complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
						complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
						complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
				
		        crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
				crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
				crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
				crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
				crp.setComplaintList(complaintsList);
				
			}
		else if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, complaintReportRequest.getOrgId())){
			
			Set<ComplaintDTO> complaints = complaintDAO.getComplaintsWithPendingWith(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(), 
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(), 
					complaintReportRequest.getStatus(), complaintReportRequest.getCareWardNo1(), 
					complaintReportRequest.getCareWardNo2(), complaintReportRequest.getCodIdOperLevel3(), 
					complaintReportRequest.getCodIdOperLevel4(), 
					complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(), 
					complaintReportRequest.getReferenceMode(), complaintReportRequest.getEmpId());
		
				
				/*Adding descion based on LangId*/
				if (CollectionUtils.isNotEmpty(complaints)) {
					complaints.stream().forEach(c -> {
						String decisionCode = c.getStatus();
						String code = MainetConstants.WorkFlow.Process.CARE+"." + decisionCode.toLowerCase();
						if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
							c.setStatus(
									applicationSession.getMessage(code, code, new Locale(MainetConstants.REG_ENG.ENGLISH)));
						else
							c.setStatus(applicationSession.getMessage(code, code,
									new Locale(MainetConstants.REG_ENG.REGIONAL)));
					});
				}
			   crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
				crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
				crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
				crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
				crp.setComplaints(complaints);
		}
		else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId())){
				Set<ComplaintDTO> complaints  = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
						complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
						complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
						complaintReportRequest.getStatus(), complaintReportRequest.getCareWardNo1(),
						complaintReportRequest.getCareWardNo2(),

						complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
						complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
						complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
					
					/*Adding descion based on LangId*/
					if (CollectionUtils.isNotEmpty(complaints)) {
						complaints.stream().forEach(c -> {
							String decisionCode = c.getStatus();
							String code = MainetConstants.WorkFlow.Process.CARE+"." + decisionCode.toLowerCase();
							if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
								c.setStatus(
										applicationSession.getMessage(code, code, new Locale(MainetConstants.REG_ENG.ENGLISH)));
							else
								c.setStatus(applicationSession.getMessage(code, code,
										new Locale(MainetConstants.REG_ENG.REGIONAL)));
						});
					}
				   crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
					crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
					crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
					crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
					crp.setComplaints(complaints);	
			}		
		return crp;
	}
	
	@Override
	public ComplaintReportDTO getUserWiseComplaintSummaryReport(ComplaintReportRequestDTO complaintReportRequest) {
		List<ComplaintDTO> complaintList  = complaintDAO.getUserWiseComplaintSummary(complaintReportRequest.getOrgId(),
				complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
				complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(),	complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
				complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
				complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
		

		ComplaintReportDTO    crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
		crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
		crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaintList(complaintList);

		return crp;
	
	}

	@Override
	public ComplaintReportDTO getDepartmentAndStatusWiseComplaintReport(
			ComplaintReportRequestDTO complaintReportRequest) {
		Organisation org = organisationService.getOrganisationById(complaintReportRequest.getOrgId());
		ComplaintReportDTO crp = null;
		Set<ComplaintDTO> complaints;
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'D') {
			complaints = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
				complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
				complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(),

				complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
				complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
				complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
            crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
			crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
			crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
			crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
			crp.setComplaints(complaints);
		}		
		else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'S'){
            List<ComplaintDTO> complaintList = complaintDAO.getComplaintsSummaryDeptStatWise(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
					complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
					complaintReportRequest.getCodIdOperLevel2(),

					complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
					complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
					complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
                 crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
    		     crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
    		     crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
    		     crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
    		    crp.setComplaintList(complaintList);
		} else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()))	{
			complaints = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
					complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
					complaintReportRequest.getCodIdOperLevel2(),complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
					complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
					complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
	            crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
				crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
				crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
				crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
				crp.setComplaints(complaints);
	}
		return crp;
	}

	@Override
	public ComplaintReportDTO getSlaWiseComplaintReport(ComplaintReportRequestDTO complaintReportRequest) {
		Set<ComplaintDTO> complaints = null ;
		ComplaintReportDTO crp = null;
		List<String> statuses = Stream
				.of(MainetConstants.WorkFlow.Decision.APPROVED, MainetConstants.WorkFlow.Decision.REJECTED, 
						MainetConstants.WorkFlow.Decision.SUBMITTED, MainetConstants.WorkFlow.Decision.REOPENED).collect(Collectors.toList());
		List<String> worflowStatus= Stream.of(MainetConstants.WorkFlow.Status.CLOSED,MainetConstants.WorkFlow.Status.PENDING).
				collect(Collectors.toList());
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'D') {
		 complaints = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
				complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(), statuses,
				worflowStatus,complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(), complaintReportRequest.getCodIdOperLevel3(),
				complaintReportRequest.getCodIdOperLevel4(), complaintReportRequest.getCodIdOperLevel5(),
				complaintReportRequest.getLangId());
		    crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
			crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
			crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
			crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
			crp.setComplaints(complaints);
			if (complaintReportRequest.getSlaStatus() > 1) {
				String slaStatus = (complaintReportRequest.getSlaStatus() == 2)
						? applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA)
						: (complaintReportRequest.getSlaStatus() == 3)
								? applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA)
								: MainetConstants.BLANK;
				Stream<ComplaintDTO> filter = complaints.parallelStream().filter(c -> c.getSlaStatus().equals(slaStatus));
				complaints = filter.collect(Collectors.toSet());
			}
		}
		else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'S'){
		List <ComplaintDTO> complaintList = complaintDAO.getComplaintsSummarySlaWise(complaintReportRequest.getOrgId(),
				complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(), statuses,
				MainetConstants.WorkFlow.Status.CLOSED, complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(), complaintReportRequest.getCodIdOperLevel3(),
				complaintReportRequest.getCodIdOperLevel4(), complaintReportRequest.getCodIdOperLevel5(),
				complaintReportRequest.getLangId());
		crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
		crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
		crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaintList(complaintList);
		
		} else if (!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId())) {
			complaints  = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(), statuses,
					worflowStatus, complaintReportRequest.getCodIdOperLevel1(),
					complaintReportRequest.getCodIdOperLevel2(), complaintReportRequest.getCodIdOperLevel3(),
					complaintReportRequest.getCodIdOperLevel4(), complaintReportRequest.getCodIdOperLevel5(),
					complaintReportRequest.getLangId());
			crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
			crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
			crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
			crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
			crp.setComplaints(complaints);
			if (complaintReportRequest.getSlaStatus() > 1) {
				String slaStatus = (complaintReportRequest.getSlaStatus() == 2)
						? applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA)
						: (complaintReportRequest.getSlaStatus() == 3)
								? applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA)
								: MainetConstants.BLANK;
				Stream<ComplaintDTO> filter = complaints.parallelStream().filter(c -> c.getSlaStatus().equals(slaStatus));
				complaints = filter.collect(Collectors.toSet());
			}
		}
	
		return crp;
	}

	
	@Override
	public ComplaintReportDTO getComplaintFeedbackReport(ComplaintReportRequestDTO complaintReportRequest) {
		Set<ComplaintDTO> complaints = new HashSet<>();
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'D') {
			complaints = complaintDAO.getComplaintFeedbacks(complaintReportRequest.getOrgId(),
				complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
				complaintReportRequest.getFeedbackRating(), complaintReportRequest.getLangId());
		}else if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId()) && complaintReportRequest.getReportName() == 'S') {
			complaints = complaintDAO.getComplaintFeedbacksSummary(complaintReportRequest.getOrgId(),
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
					complaintReportRequest.getFeedbackRating(), complaintReportRequest.getLangId());
		}else if(!CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT,complaintReportRequest.getOrgId())) {
			complaints = complaintDAO.getComplaintFeedbacks(complaintReportRequest.getOrgId(),
					complaintReportRequest.getFromDate(), complaintReportRequest.getToDate(),
					complaintReportRequest.getFeedbackRating(), complaintReportRequest.getLangId());
		}
		Stream<ComplaintDTO> complaintsStream = complaints.stream()
				.filter(complaint -> Double.valueOf(complaint.getFeedback()) > 0);
		DoubleSummaryStatistics collect = complaintsStream
				.collect(Collectors.summarizingDouble(comp -> Double.valueOf(comp.getFeedback())));
		double totalFeedBack = collect.getSum();
		AtomicDouble totFeedBack = new AtomicDouble(totalFeedBack);
		complaints.forEach(complaint -> {
			double feedPercentage = (Double.valueOf(complaint.getFeedback()) / totFeedBack.doubleValue()) * 100;
			complaint.setTotalfeedback(Math.round(feedPercentage));
		});
		ComplaintReportDTO crp = new ComplaintReportDTO();
		crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
		crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaints(complaints);
		return crp;

	}

	@Override
	public ComplaintReportDTO getComplaintDetailedAgeingReport(ComplaintReportRequestDTO complaintReportRequest) {

		Map<String, Set<ComplaintDTO>> complaintsGroups = new LinkedHashMap<>();

		long f = complaintReportRequest.getFromSlab();
		long t = complaintReportRequest.getToSlab();
		long r = t - f + 1;

		for (int i = 0; i < complaintReportRequest.getSlabLevels(); i++) {

			if (i > 0 && (i + 1) == complaintReportRequest.getSlabLevels())
				t += 365;
			Map<String, Date> period = getFromDateToDateByAgeignSlab(f, t, new Date());
			Set<ComplaintDTO> complaints = complaintDAO.getComplaints(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
					period.get(MainetConstants.FROM_DATE), period.get(MainetConstants.TO_DATE),
					complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
					complaintReportRequest.getCodIdOperLevel2(),

					complaintReportRequest.getCodIdOperLevel3(), complaintReportRequest.getCodIdOperLevel4(),
					complaintReportRequest.getCodIdOperLevel5(), complaintReportRequest.getLangId(),
					complaintReportRequest.getReferenceMode(),complaintReportRequest.getEmpId());
			if (!complaints.isEmpty()) {
				if ((t - f) > r)
					complaintsGroups.put(applicationSession.getMessage(MainetConstants.ServiceCareCommon.LEVEL)
							+ MainetConstants.WHITE_SPACE + (complaintsGroups.size() + 1)
							+ MainetConstants.COLON_SEPARATOR + f + MainetConstants.operator.PLUS, complaints);
				else
					complaintsGroups.put(applicationSession.getMessage(MainetConstants.ServiceCareCommon.LEVEL)
							+ MainetConstants.WHITE_SPACE + (complaintsGroups.size() + 1)
							+ MainetConstants.COLON_SEPARATOR + f + MainetConstants.HYPHEN + t, complaints);
			}
			f = t + 1;
			t = t + r;
		}

		ComplaintReportDTO crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
		crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
		crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaintsGroups(complaintsGroups);
		return crp;
	}

	@Override
	public ComplaintReportDTO getComplaintSummaryAgeingReport(ComplaintReportRequestDTO complaintReportRequest) {

		Map<SummaryField, Set<Map<String, Long>>> complaintSummary = new TreeMap<>();
		long f = complaintReportRequest.getFromSlab();
		long t = complaintReportRequest.getToSlab();
		long r = t - f + 1;

		for (int i = 0; i < complaintReportRequest.getSlabLevels(); i++) {
			if ((i + 1) == complaintReportRequest.getSlabLevels())
				t += MainetConstants.DAS_IN_YEAR;
			String slab = getSlabName(f, t, (i + 1) == complaintReportRequest.getSlabLevels());
			Map<String, Date> period = getFromDateToDateByAgeignSlab(f, t, new Date());
			Map<SummaryField, Long> summaryFields = complaintDAO.getComlaintSummary(complaintReportRequest.getOrgId(),
					complaintReportRequest.getDepartment(), complaintReportRequest.getComplaintType(),
					period.get(MainetConstants.FROM_DATE), period.get(MainetConstants.TO_DATE),
					complaintReportRequest.getStatus(), complaintReportRequest.getCodIdOperLevel1(),
					complaintReportRequest.getCodIdOperLevel2(), complaintReportRequest.getCodIdOperLevel3(),
					complaintReportRequest.getCodIdOperLevel4(), complaintReportRequest.getCodIdOperLevel5(),
					complaintReportRequest.getLangId());
			if (!summaryFields.isEmpty()) {
				summaryFields.forEach((k, v) -> {
					Set<Map<String, Long>> row = null;
					if (complaintSummary.containsKey(k)) {
						row = complaintSummary.get(k);
					} else {
						row = getSlabSummaryEmprtyRow(complaintReportRequest.getFromSlab(),
								complaintReportRequest.getToSlab(), complaintReportRequest.getSlabLevels());
					}
					Stream<Map<String, Long>> filter = row.stream().filter(m -> m.containsKey(slab));
					filter.findFirst().map(column -> {
						column.replace(slab, column.get(slab) + v);
						return null;
					});
					complaintSummary.put(k, row);
				});
			}
			f = t + 1;
			t = t + r;
		}
		ComplaintReportDTO crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaintSummary(complaintSummary);
		return crp;
	}

	@Override
	public ComplaintReportDTO getComplaintSummaryDetailedReport(ComplaintReportRequestDTO complaintReportRequest) {
		return null;
	}

	@Override
	public ComplaintReportDTO getDepartmentWiseGradingReport(ComplaintReportRequestDTO complaintReportRequest) {
		Set<ComplaintGradeSummary> complaintGradeSummary = complaintDAO.getComplaintSummaryDepartmentWise(
				complaintReportRequest.getOrgId(), complaintReportRequest.getDepartment(),
				complaintReportRequest.getComplaintType(), complaintReportRequest.getFromDate(),
				complaintReportRequest.getToDate(), complaintReportRequest.getCodIdOperLevel1(),
				complaintReportRequest.getCodIdOperLevel2(), complaintReportRequest.getCodIdOperLevel3(),
				complaintReportRequest.getCodIdOperLevel4(), complaintReportRequest.getCodIdOperLevel5(),
				complaintReportRequest.getLangId());
		ComplaintReportDTO crp = getComplaintReportWithCommonDisplayFilters(complaintReportRequest);
		crp.setFromDate(Utility.dateToString(complaintReportRequest.getFromDate()));
		crp.setToDate(Utility.dateToString(complaintReportRequest.getToDate()));
		crp.setDateTime(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		crp.setComplaintGradeSummary(complaintGradeSummary);
		return crp;
	}

	/**
	 * 
	 * @param complaintReportRequest
	 * @return
	 */
	private ComplaintReportDTO getComplaintReportWithCommonDisplayFilters(
			ComplaintReportRequestDTO complaintReportRequest) {
		ComplaintReportDTO crp = new ComplaintReportDTO();
		Organisation org = organisationService.getOrganisationById(complaintReportRequest.getOrgId());
		if (org != null) {
			if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
				crp.setOrgName(org.getONlsOrgname());
			else
				crp.setOrgName(org.getONlsOrgnameMar());
		}

		if (complaintReportRequest.getDepartment() != null) {
			if (complaintReportRequest.getDepartment() == -1) {
				crp.setDepartmentName(applicationSession.getMessage(MainetConstants.ServiceCareCommon.ALL));
				crp.setShowDepartment(true);
			} else {
				Department department = tbDepartmentJpaRepository.findOne(complaintReportRequest.getDepartment());
				if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
					crp.setDepartmentName(department.getDpDeptdesc());
				else
					crp.setDepartmentName(department.getDpNameMar());
			}
		}
		if (complaintReportRequest.getComplaintType() != null) {
			if (complaintReportRequest.getComplaintType() == -1) {
				crp.setComplaintType(applicationSession.getMessage(MainetConstants.ServiceCareCommon.ALL));
				crp.setShowComplaintType(true);
			} else {
				ComplaintType complaintType = complaintJpaRepository
						.findComplaintTypeById(complaintReportRequest.getComplaintType());
				if (complaintReportRequest.getLangId() == MainetConstants.ENGLISH)
					crp.setComplaintType(complaintType.getComplaintDesc());
				else
					crp.setComplaintType(complaintType.getComplaintDescreg());
			}
		}
		if (complaintReportRequest.getStatus() != null) {
			if (complaintReportRequest.getStatus().equals("-1")) {
				crp.setStatus(applicationSession.getMessage(MainetConstants.ServiceCareCommon.ALL));
				crp.setShowStatus(true);
			} else
				crp.setStatus(MainetConstants.WorkFlow.StatusForDecision
						.getStatusForDecision(complaintReportRequest.getStatus()));
		}
		if (complaintReportRequest.getSlaStatus() != null) {
			if (complaintReportRequest.getSlaStatus() == 1) {
				crp.setSlaStatus(applicationSession.getMessage(MainetConstants.ServiceCareCommon.ALL));
				crp.setShowSlaStatus(true);
			}
			if (complaintReportRequest.getSlaStatus() == 2) {
				crp.setSlaStatus(applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA));
			}
			if (complaintReportRequest.getSlaStatus() == 3) {
				crp.setSlaStatus(applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA));
			}
		}

		crp.setCodIdOperLevel1(complaintReportRequest.getCodIdOperLevel1());
		crp.setCodIdOperLevel2(complaintReportRequest.getCodIdOperLevel2());

		crp.setCodIdOperLevel3(complaintReportRequest.getCodIdOperLevel3());
		crp.setCodIdOperLevel4(complaintReportRequest.getCodIdOperLevel4());
		crp.setCodIdOperLevel5(complaintReportRequest.getCodIdOperLevel5());

		return crp;

	}

	/**
	 * 
	 * @param fromNumberOfdays
	 * @param toNumberOfDays
	 * @param date
	 * @return
	 */
	private static Map<String, Date> getFromDateToDateByAgeignSlab(long fromNumberOfdays, long toNumberOfDays,
			Date date) {
		Map<String, Date> period = new HashMap<>();
		Date toDate = new Date(date.getTime() - fromNumberOfdays * 24 * 60 * 60 * 1000);
		Date fromDate = new Date(date.getTime() - toNumberOfDays * 24 * 60 * 60 * 1000);
		period.put(MainetConstants.FROM_DATE, fromDate);
		period.put(MainetConstants.TO_DATE, toDate);
		return period;
	}

	/**
	 * 
	 * @param fromNumberOfdays
	 * @param toNumberOfDays
	 * @param columns
	 * @return
	 */
	private static Set<Map<String, Long>> getSlabSummaryEmprtyRow(long fromNumberOfdays, long toNumberOfDays,
			long columns) {
		Set<Map<String, Long>> row = new LinkedHashSet<>();
		long f = fromNumberOfdays;
		long t = toNumberOfDays;
		long r = t - f + 1;
		for (int i = 0; i < columns; i++) {
			if ((i + 1) == columns)
				t += 365;
			String slab = getSlabName(f, t, (i + 1) == columns);
			Map<String, Long> column = new LinkedHashMap<>();
			column.put(slab, 0l);
			row.add(column);
			f = t + 1;
			t = t + r;
		}
		return row;
	}

	/**
	 * 
	 * @param fromNumberOfdays
	 * @param toNumberOfDays
	 * @return
	 */
	private static String getSlabName(long fromNumberOfdays, long toNumberOfDays, boolean isLast) {
		if (isLast)
			return fromNumberOfdays + MainetConstants.operator.PLUS;
		else
			return fromNumberOfdays + MainetConstants.HYPHEN + toNumberOfDays;
	}

	@Override
	public List<SmsEmailTransactionDTO> getSmsEmailHistoryByDeptIAndDate(ComplaintReportRequestDTO compRequestDto) {
		// TODO Auto-generated method stub
		List<SmsEmailTransactionDTO> smsEmailDtoList = new ArrayList<SmsEmailTransactionDTO>();
		List<Object[]> transObjList = new ArrayList<Object[]>();
		if (compRequestDto != null && compRequestDto.getAlertType() != null
				&& compRequestDto.getAlertType().equals(MainetConstants.FlagB)) {
			transObjList = smsEmailRepository.getSmsAndEmailHistoryForBothByService(compRequestDto.getOrgId(),
					compRequestDto.getFromDate(), compRequestDto.getToDate(),"CARE");
		} else {
			transObjList = smsEmailRepository.getSmsAndEmailHistoryByService(compRequestDto.getOrgId(),
					compRequestDto.getFromDate(), compRequestDto.getToDate(), "CARE",compRequestDto.getAlertType());
		}
		
		transObjList.forEach(smsEmail -> {
			SmsEmailTransactionDTO smsDto = new SmsEmailTransactionDTO();
			smsDto.setEmailId(smsEmail[22] != null ?(String)smsEmail[22] : "");
			smsDto.setEmailsubject(smsEmail[20] != null ?(String)smsEmail[20] : "");
			smsDto.setMobileNo(smsEmail[3] != null ?(String)smsEmail[3] : "");
			smsDto.setMsgSub(smsEmail[4] != null ?(String)smsEmail[4] : "");
			smsDto.setRefId3(smsEmail[18] != null ?(String)smsEmail[18] : "");
			smsDto.setSentBy(smsEmail[7] != null ?(String)smsEmail[7] : "");
			smsDto.setSentDt(smsEmail[6] != null ?(java.sql.Date)smsEmail[6] : null);
			smsDto.setStatus(smsEmail[8] != null ?(String)smsEmail[8] : "");
			smsDto.setMsgText(smsEmail[5] != null ?(String)smsEmail[5] : "");

			smsEmailDtoList.add(smsDto);
		});

		return smsEmailDtoList;
	}

}
