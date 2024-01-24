package com.abm.mainet.common.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dashboard.dao.CitizenDashboardGraphDAO;
import com.abm.mainet.common.dashboard.dao.ComplaintDashBoardDao;
import com.abm.mainet.common.dashboard.domain.AdvocateMastRegEntity;
import com.abm.mainet.common.dashboard.domain.CitizenDashboardGraphEntity;
import com.abm.mainet.common.dashboard.domain.CommonComplaintEntity;
import com.abm.mainet.common.dashboard.domain.ComplaintEntity;
import com.abm.mainet.common.dashboard.domain.CouncilDeptWiseAgendaDataEntity;
import com.abm.mainet.common.dashboard.domain.CouncilDeptWiseMeetingDataEntity;
import com.abm.mainet.common.dashboard.domain.CouncilDeptWiseProposalCntEntity;
import com.abm.mainet.common.dashboard.domain.CouncilDeptWiseProposalDataEntity;
import com.abm.mainet.common.dashboard.domain.CouncilDeptYearWiseCntEntity;
import com.abm.mainet.common.dashboard.domain.CouncilMeetingCountYrwise;
import com.abm.mainet.common.dashboard.domain.CouncilProposalCntEntity;
import com.abm.mainet.common.dashboard.domain.DashboardAllCountsEntity;
import com.abm.mainet.common.dashboard.domain.DeptAndCourtWiseAllLegalDataEntity;
import com.abm.mainet.common.dashboard.domain.DeptAndCourtWiseLegalCntEntity;
import com.abm.mainet.common.dashboard.domain.DeptByDaysAndDescEntity;
import com.abm.mainet.common.dashboard.domain.DeptComplaintsEntity;
import com.abm.mainet.common.dashboard.domain.DeptWiseAnswerDataLQPEntity;
import com.abm.mainet.common.dashboard.domain.DeptWiseQuestionDataLQPEntity;
import com.abm.mainet.common.dashboard.domain.DeptWiseTrendAnalysisEntity;
import com.abm.mainet.common.dashboard.domain.DrillDownWasteTypeWiseEntity;
import com.abm.mainet.common.dashboard.domain.DropdownEntity;
import com.abm.mainet.common.dashboard.domain.DryWasteHazSWMEntity;
import com.abm.mainet.common.dashboard.domain.DurationEntity;
import com.abm.mainet.common.dashboard.domain.FinancialAndNonFinacialProposalCount;
import com.abm.mainet.common.dashboard.domain.FinancialYearSWMEntity;
import com.abm.mainet.common.dashboard.domain.FirstTableSWMEntity;
import com.abm.mainet.common.dashboard.domain.FirstTableYearwiseSWMEntity;
import com.abm.mainet.common.dashboard.domain.HRMSCategoryWiseEmpCntEntity;
import com.abm.mainet.common.dashboard.domain.HRMSEmployeeGridDataEntity;
import com.abm.mainet.common.dashboard.domain.HRMSGenderRatioEntity;
import com.abm.mainet.common.dashboard.domain.HRMSRecruitmentPostsEntity;
import com.abm.mainet.common.dashboard.domain.HRMSSalaryBreakdownEntity;
import com.abm.mainet.common.dashboard.domain.HRMSStoreCountEntity;
import com.abm.mainet.common.dashboard.domain.LegalCountsEntity;
import com.abm.mainet.common.dashboard.domain.ModeComplaintsEntity;
import com.abm.mainet.common.dashboard.domain.MonthlySWMEntity;
import com.abm.mainet.common.dashboard.domain.OrganizationEntity;
import com.abm.mainet.common.dashboard.domain.RTIApplicationByApplicantType;
import com.abm.mainet.common.dashboard.domain.RTIApplicationCountEntity;
import com.abm.mainet.common.dashboard.domain.RTIApplicationyBplFlagEntity;
import com.abm.mainet.common.dashboard.domain.RTIDasBoardAppealStatusEntity;
import com.abm.mainet.common.dashboard.domain.RtiApplicationDtoByCondEntity;
import com.abm.mainet.common.dashboard.domain.SKDCLDashboardAllCountsEntity;
import com.abm.mainet.common.dashboard.domain.SLAAnalysisEntity;
import com.abm.mainet.common.dashboard.domain.StoreItemCountEntity;
import com.abm.mainet.common.dashboard.domain.StoreItemDataEntity;
import com.abm.mainet.common.dashboard.domain.StoreItemWiseInventoryCntEntity;
import com.abm.mainet.common.dashboard.domain.TotalCollectionAmtSWMEntity;
import com.abm.mainet.common.dashboard.domain.TotalCollectionSWMEntity;
import com.abm.mainet.common.dashboard.domain.TotalServiceCntSWMEntity;
import com.abm.mainet.common.dashboard.domain.TotalTransCntSWMEntity;
import com.abm.mainet.common.dashboard.domain.VehicleAndWasteTypeByDaysEntity;
import com.abm.mainet.common.dashboard.domain.VehicleTypeDryWetEntity;
import com.abm.mainet.common.dashboard.domain.YearWiseGrievanceGraphEntity;
import com.abm.mainet.common.dashboard.domain.YearwiseTrendAnalysisEntity;
import com.abm.mainet.common.dashboard.dto.DeptByDaysAndDescDTO;
import com.abm.mainet.common.dashboard.dto.LegalCountsDTO;
import com.abm.mainet.common.domain.FinancialYear;

@Service
@Transactional(readOnly = true)
public class CitizenDashboardGraphServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(CitizenDashboardGraphServiceImpl.class);

	private static final String HOLD = "Hold";
	private static final String PENDING = "Pending";
	private static final String REJECTED = "Rejected";
	private static final String CLOSED = "Closed";

	@Autowired
	CitizenDashboardGraphDAO citizenDashboardGraphDAO;
	@Autowired
	ComplaintDashBoardDao complaintDashBoardDao;

	public List<CitizenDashboardGraphEntity> getCitizenDashboardGraph() {
		return citizenDashboardGraphDAO.getCitizenDashboardGraphEntityList();
	}

	public List<DropdownEntity> getdropdownSevenDaysEntityList() {
		return citizenDashboardGraphDAO.getDropdownSevenDaysEntityList();
	}

	public List<DropdownEntity> getDropdownSixMonthsEntityList() {
		return citizenDashboardGraphDAO.getDropdownSixMonthsEntity();
	}

	public List<DropdownEntity> getDropdownFinancialYrEntityList() {
		return citizenDashboardGraphDAO.getDropdownFinancialYrEntity();
	}

	public List<DropdownEntity> getDropdownHalfYearlyEntityList() {
		return citizenDashboardGraphDAO.getDropdownHalfYearlyEntity();
	}

	public List<DropdownEntity> getDropdownQuarterlyEntityList() {
		return citizenDashboardGraphDAO.getDropdownQuarterlyEntity();
	}

	public List<TotalCollectionSWMEntity> getTotalCollectionSWMEntityList() {
		return citizenDashboardGraphDAO.getTotalCollectionSWMEntityList();
	}

	public List<TotalServiceCntSWMEntity> getTotalServiceCntSWMEntityList() {
		return citizenDashboardGraphDAO.getTotalServiceCntSWMEntityList();
	}

	public List<TotalTransCntSWMEntity> getTotalTransCntSWMEntityList() {
		return citizenDashboardGraphDAO.getTotalTransCntSWMEntityList();
	}

	public List<TotalCollectionAmtSWMEntity> getTotalCollectionAmtSWMEntityList() {
		return citizenDashboardGraphDAO.getTotalCollectionAmtSWMEntityList();
	}

	public List<VehicleTypeDryWetEntity> getVehicleTypeDryWetEntityList() {
		return citizenDashboardGraphDAO.getVehicleTypeDryWetEntityList();
	}

	public List<VehicleAndWasteTypeByDaysEntity> getVehicleAndWasteByDaysEntityList(String vehicleType,
			String wasteType, int noOfDays, int orgId) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		if (vehicleType == null || vehicleType.trim().equalsIgnoreCase(""))
			vehicleType = null;
		if (wasteType == null || wasteType.trim().equalsIgnoreCase(""))
			wasteType = null;
		return citizenDashboardGraphDAO.getVehicleAndWasteByDaysEntityList(vehicleType, wasteType, noOfDays, orgId,
				toDate.toString());
	}

	public List<DryWasteHazSWMEntity> getGraphDryWasteEntityList() {
		return citizenDashboardGraphDAO.getGraphDryWasteEntityList();
	}

	public List<DryWasteHazSWMEntity> getGraphWetWasteEntityList() {
		return citizenDashboardGraphDAO.getGraphWetWasteEntityList();
	}

	public List<DryWasteHazSWMEntity> getGraphHazWasteEntityList() {
		return citizenDashboardGraphDAO.getGraphHazWasteEntityList();
	}

	public List<VehicleTypeDryWetEntity> getVehicleTypeDryWetFinancialDurationWiseEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getVehicleTypeDryWetFinancialDurationWiseEntityList(noOfDays,
				toDate.toString());
	}

	public List<DryWasteHazSWMEntity> getGraphDryWasteFinancialDurationWiseEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getGraphDryWasteFinancialDurationWiseEntityList(noOfDays, toDate.toString());
	}

	public List<DryWasteHazSWMEntity> getGraphWetWasteFinancialDurationWiseEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getGraphWetWasteFinancialDurationWiseEntityList(noOfDays, toDate.toString());
	}

	public List<DryWasteHazSWMEntity> getGraphHazWasteFinancialDurationWiseEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getGraphHazWasteFinancialDurationWiseEntityList(noOfDays, toDate.toString());
	}

	public List<DrillDownWasteTypeWiseEntity> getDrillDownDryWasteTypeWiseEntityList(String wasteType, String codDesc,
			int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		if (codDesc == null || codDesc.trim().equalsIgnoreCase(""))
			codDesc = null;
		if (wasteType == null || wasteType.trim().equalsIgnoreCase(""))
			wasteType = null;
		return citizenDashboardGraphDAO.getDrillDownDryWasteTypeWiseEntityList(wasteType, codDesc, noOfDays,
				toDate.toString());
	}

	public List<FinancialYearSWMEntity> getFinancialYrSWMEntityList() {
		return citizenDashboardGraphDAO.getFinancialYrSWMEntityList();
	}

	public List<FinancialYearSWMEntity> getHalfYrSWMEntityList() {
		return citizenDashboardGraphDAO.getHalfYrSWMEntityList();
	}

	public List<FinancialYearSWMEntity> getLastFourQuarterSWMEntityList() {
		return citizenDashboardGraphDAO.getLastFourQuarterSWMEntityList();
	}

	public List<FinancialYearSWMEntity> getLastSevenDaysSWMEntityList() {
		return citizenDashboardGraphDAO.getLastSevenDaysSWMEntityList();
	}

	public List<MonthlySWMEntity> getMonthlySWMEntityList() {
		return citizenDashboardGraphDAO.getMonthlySWMEntityList();
	}

	public List<FirstTableSWMEntity> getFirstTableSWMEntityList() {
		return citizenDashboardGraphDAO.getFirstTableSWMEntityList();
	}

	public List<FirstTableYearwiseSWMEntity> getFirstTableYearlySWMEntityList(int orgId) {
		return citizenDashboardGraphDAO.getFirstTableYearlySWMEntityList(orgId);
	}

	public List<FirstTableYearwiseSWMEntity> getFirstTableCurrYearSWMEntityList(int orgId) {
		return citizenDashboardGraphDAO.getFirstTableCurrYearSWMEntityList(orgId);
	}

	public List<DeptComplaintsEntity> getDeptComplaintsEntityList() {
		return citizenDashboardGraphDAO.getDeptComplaintsEntityList();
	}

	public List<ModeComplaintsEntity> getModeComplaintsEntityList() {
		return citizenDashboardGraphDAO.getModeComplaintsEntityList();
	}

	public List<DeptComplaintsEntity> getDeptComplaintsByDaysEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getDeptComplaintsByDaysEntityList(noOfDays);
	}

	public int getNoOfDays(int noOfDays) {
		if (noOfDays == 5) {
			noOfDays = 365;
		} else if (noOfDays == 4) {
			noOfDays = 180;
		} else if (noOfDays == 3) {
			noOfDays = 90;
		} else if (noOfDays == 2) {
			noOfDays = 30;
		} else {
			noOfDays = 1;
		}
		return noOfDays;
	}

	public List<ModeComplaintsEntity> getModeComplaintsByDaysEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getModeComplaintsByDaysEntityList(noOfDays);
	}

	public List<SLAAnalysisEntity> getSLAAnalysisEntityList() {
		return citizenDashboardGraphDAO.getSLAAnalysisEntityList();
	}

	public List<OrganizationEntity> getOrgEntityList() {
		return citizenDashboardGraphDAO.getOrgEntityList();
	}

	public List<DurationEntity> getYearlyDurationEntityList() {
		return citizenDashboardGraphDAO.getYearlyDurationEntityList();
	}

	public List<DurationEntity> getHalfYearlyDurationEntityList() {
		return citizenDashboardGraphDAO.getHalfYearlyDurationEntityList();
	}

	public List<DurationEntity> getQuarterlyDurationEntityList() {
		return citizenDashboardGraphDAO.getQuarterlyDurationEntityList();
	}

	public DashboardAllCountsEntity getDashboardAllCounts() {
		return citizenDashboardGraphDAO.getDashboardAllCounts();
	}

	public HRMSStoreCountEntity getDashboardHRMSCount() {
		return citizenDashboardGraphDAO.getDashboardHRMSCount();
	}

	public HRMSStoreCountEntity getDashboardStoreCount() {
		return citizenDashboardGraphDAO.getDashboardStoreCount();
	}

	public List<DeptByDaysAndDescDTO> getDeptComplaintsByDaysAndDescEntityList(int noOfDays, String desc) {
		noOfDays = getNoOfDays(noOfDays);
		if (desc == null || desc.trim().equalsIgnoreCase(""))
			desc = null;
		List<DeptByDaysAndDescDTO> deptByDaysAndDescList = new ArrayList<DeptByDaysAndDescDTO>();
		List<DeptByDaysAndDescEntity> list = citizenDashboardGraphDAO.getDeptComplaintsByDaysAndDescEntityList(noOfDays,
				desc);
		if (list != null) {
			DeptByDaysAndDescDTO dto = null;
			for (DeptByDaysAndDescEntity dept : list) {
				dto = new DeptByDaysAndDescDTO();
				BeanUtils.copyProperties(dept, dto);
				if (dept.getHold() == 1) {
					dto.setStatus(HOLD);
				} else if (dept.getPending() == 1) {
					dto.setStatus(PENDING);
				} else if (dept.getRejected() == 1) {
					dto.setStatus(REJECTED);
				} else if (dept.getClosed() == 1) {
					dto.setStatus(CLOSED);
				}
				deptByDaysAndDescList.add(dto);
			}
		}
		return deptByDaysAndDescList;
	}

	public List<DeptByDaysAndDescDTO> getModeComplaintsByDaysAndDescEntityList(int noOfDays, String desc) {
		noOfDays = getNoOfDays(noOfDays);
		if (desc == null || desc.trim().equalsIgnoreCase(""))
			desc = null;
		List<DeptByDaysAndDescDTO> modeByDaysAndDescList = new ArrayList<DeptByDaysAndDescDTO>();
		List<DeptByDaysAndDescEntity> list = citizenDashboardGraphDAO.getModeComplaintsByDaysAndDescEntityList(noOfDays,
				desc);
		if (list != null) {
			DeptByDaysAndDescDTO dto = null;
			for (DeptByDaysAndDescEntity dept : list) {
				dto = new DeptByDaysAndDescDTO();
				BeanUtils.copyProperties(dept, dto);
				if (dept.getHold() == 1) {
					dto.setStatus(HOLD);
				} else if (dept.getPending() == 1) {
					dto.setStatus(PENDING);
				} else if (dept.getRejected() == 1) {
					dto.setStatus(REJECTED);
				} else if (dept.getClosed() == 1) {
					dto.setStatus(CLOSED);
				}
				modeByDaysAndDescList.add(dto);
			}
		}
		return modeByDaysAndDescList;
	}

	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphEntityList() {
		return citizenDashboardGraphDAO.getYearWiseGraphEntityList();
	}

	/*
	 * RTI Module APIs
	 */

	public List<SLAAnalysisEntity> getSLAAnalysisEntityRTIList() {
		return citizenDashboardGraphDAO.getSLAAnalysisEntityRTIList();
	}

	public List<DropdownEntity> getdropdownSevenDaysRTIEntityList() {
		return citizenDashboardGraphDAO.getDropdownSevenDaysRTIEntityList();
	}

	public List<DropdownEntity> getDropdownSixMonthsRTIEntityList() {
		return citizenDashboardGraphDAO.getDropdownSixMonthsRTIEntity();
	}

	public List<DropdownEntity> getDropdownFinancialYrRTIEntityList() {
		return citizenDashboardGraphDAO.getDropdownFinancialYrRTIEntity();
	}

	public List<DropdownEntity> getDropdownHalfYearlyRTIEntityList() {
		return citizenDashboardGraphDAO.getDropdownHalfYearlyRTIEntity();
	}

	public List<DropdownEntity> getDropdownQuarterlyRTIEntityList() {
		return citizenDashboardGraphDAO.getDropdownQuarterlyRTIEntity();
	}

	public List<DeptComplaintsEntity> getDeptComplaintsRTIEntityList() {
		return citizenDashboardGraphDAO.getDeptComplaintsRTIEntityList();
	}

	public List<ModeComplaintsEntity> getModeComplaintsRTIEntityList() {
		return citizenDashboardGraphDAO.getModeComplaintsRTIEntityList();
	}

	public List<DeptComplaintsEntity> getDeptComplaintsByDaysRTIEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getDeptComplaintsByDaysRTIEntityList(noOfDays);
	}

	public List<ModeComplaintsEntity> getModeComplaintsByDaysRTIEntityList(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getModeComplaintsByDaysRTIEntityList(noOfDays);
	}

	public List<DeptByDaysAndDescDTO> getDeptComplaintsByDaysAndDescRTIEntityList(int noOfDays, String desc) {
		noOfDays = getNoOfDays(noOfDays);
		if (desc == null || desc.trim().equalsIgnoreCase(""))
			desc = null;
		List<DeptByDaysAndDescDTO> deptByDaysAndDescList = new ArrayList<DeptByDaysAndDescDTO>();
		List<DeptByDaysAndDescEntity> list = citizenDashboardGraphDAO
				.getDeptComplaintsByDaysAndDescRTIEntityList(noOfDays, desc);
		if (list != null) {
			DeptByDaysAndDescDTO dto = null;
			for (DeptByDaysAndDescEntity dept : list) {
				dto = new DeptByDaysAndDescDTO();
				BeanUtils.copyProperties(dept, dto);
				if (dept.getHold() == 1) {
					dto.setStatus(HOLD);
				} else if (dept.getPending() == 1) {
					dto.setStatus(PENDING);
				} else if (dept.getRejected() == 1) {
					dto.setStatus(REJECTED);
				} else if (dept.getClosed() == 1) {
					dto.setStatus(CLOSED);
				}
				deptByDaysAndDescList.add(dto);
			}
		}
		return deptByDaysAndDescList;
	}

	public List<DeptByDaysAndDescDTO> getModeComplaintsByDaysAndDescRTIEntityList(int noOfDays, String desc) {
		noOfDays = getNoOfDays(noOfDays);
		if (desc == null || desc.trim().equalsIgnoreCase(""))
			desc = null;
		List<DeptByDaysAndDescDTO> modeByDaysAndDescList = new ArrayList<DeptByDaysAndDescDTO>();
		List<DeptByDaysAndDescEntity> list = citizenDashboardGraphDAO
				.getModeComplaintsByDaysAndDescRTIEntityList(noOfDays, desc);
		if (list != null) {
			DeptByDaysAndDescDTO dto = null;
			for (DeptByDaysAndDescEntity dept : list) {
				dto = new DeptByDaysAndDescDTO();
				BeanUtils.copyProperties(dept, dto);
				if (dept.getHold() == 1) {
					dto.setStatus(HOLD);
				} else if (dept.getPending() == 1) {
					dto.setStatus(PENDING);
				} else if (dept.getRejected() == 1) {
					dto.setStatus(REJECTED);
				} else if (dept.getClosed() == 1) {
					dto.setStatus(CLOSED);
				}
				modeByDaysAndDescList.add(dto);
			}
		}
		return modeByDaysAndDescList;
	}

	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphRTIEntityList() {
		return citizenDashboardGraphDAO.getYearWiseGraphRTIEntityList();
	}

	public RTIApplicationCountEntity getRTIFirstAppealCount() {
		return citizenDashboardGraphDAO.getRTIFirstAppealCount();
	}

	public RTIApplicationCountEntity getRTISecondAppealCount() {
		return citizenDashboardGraphDAO.getRTISecondAppealCount();
	}

	public RTIApplicationCountEntity getRTIOpenFirstAndSecondAppealCount() {
		return citizenDashboardGraphDAO.getRTIOpenFirstAndSecondAppealCount();
	}

	public RTIApplicationCountEntity getRTIClosedFirstAndSecondAppealCount() {
		return citizenDashboardGraphDAO.getRTIClosedFirstAndSecondAppealCount();
	}

	public RTIApplicationCountEntity getTotalReceivedRTIApplications() {
		return citizenDashboardGraphDAO.getTotalReceivedRTIApplications();
	}

	/*
	 * Legal Module APIs
	 */

	public List<LegalCountsDTO> getLegalCountsEntityList() {
		List<LegalCountsDTO> legalCountsDTOs = new ArrayList<LegalCountsDTO>();
		List<LegalCountsEntity> legalReceivedCounts = citizenDashboardGraphDAO.getLegalReceivedYrWiseEntityList();
		List<LegalCountsEntity> legalClosedCounts = citizenDashboardGraphDAO.getLegalClosedYrWisetEntityList();
		if (legalReceivedCounts != null && legalClosedCounts != null) {

			for (LegalCountsEntity legalEntity : legalReceivedCounts) {
				LegalCountsDTO legalDto = new LegalCountsDTO();
				legalDto.setReceived(legalEntity.getCount());
				legalDto.setYear(legalEntity.getYear());

				for (LegalCountsEntity legalClEntity : legalClosedCounts) {
					if (legalEntity.getYear().equals(legalClEntity.getYear())) {
						legalDto.setClosed(legalClEntity.getCount());
						legalDto.setPending(legalEntity.getCount() - legalClEntity.getCount());
					}
				}
				legalCountsDTOs.add(legalDto);
			}
		}
		return legalCountsDTOs;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseReceivedLegalCount() {
		return citizenDashboardGraphDAO.getDeptWiseReceivedLegalCount();
	}

	public List<DeptAndCourtWiseLegalCntEntity> getCourtWiseReceivedLegalCount() {
		return citizenDashboardGraphDAO.getCourtWiseReceivedLegalCount();
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseYearlyReceivedLegalCount(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getDeptWiseReceivedLegalCount(noOfDays);
	}

	public List<DeptAndCourtWiseLegalCntEntity> getCourtWiseYearlyReceivedLegalCount(int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		return citizenDashboardGraphDAO.getCourtWiseReceivedLegalCount(noOfDays);
	}

	public List<DeptAndCourtWiseAllLegalDataEntity> getDeptWiseAllLegalData(String deptName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		return citizenDashboardGraphDAO.getDeptWiseAllLegalData(deptName, noOfDays);
	}

	public List<DeptAndCourtWiseAllLegalDataEntity> getCourtWiseAllLegalData(String courtName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (courtName == null || courtName.trim().equalsIgnoreCase(""))
			courtName = null;
		return citizenDashboardGraphDAO.getCourtWiseAllLegalData(courtName, noOfDays);
	}

	/*
	 * Legislative Query Management module APIs
	 */

	public List<YearwiseTrendAnalysisEntity> getYrWiseTrendAnalysisEntityList() {
		return citizenDashboardGraphDAO.getYrWiseTrendAnalysisEntityList();
	}

	public List<DeptWiseTrendAnalysisEntity> getDeptWiseTrendAnalysisEntityList() {
		return citizenDashboardGraphDAO.getDeptWiseTrendAnalysisEntityList();
	}

	public List<YearwiseTrendAnalysisEntity> getCurrYrTrendAnalysisEntityList() {
		return citizenDashboardGraphDAO.getCurrYrTrendAnalysisEntityList();
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseLQPQuestionCount(String deptName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseLQPQuestionCount(deptName, noOfDays, toDate.toString());
	}

	public List<DeptWiseQuestionDataLQPEntity> getDeptWiseLQPQuestionData(String deptName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseLQPQuestionData(deptName, noOfDays, toDate.toString());
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseLQPAnswerCount(String deptName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseLQPAnswerCount(deptName, noOfDays, toDate.toString());
	}

	public List<DeptWiseAnswerDataLQPEntity> getDeptWiseLQPAnswerData(String deptName, int noOfDays) {
		noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseLQPAnswerData(deptName, noOfDays, toDate.toString());
	}

	/*
	 * Council Module APIs
	 */

	public List<CouncilProposalCntEntity> getCouncilProposalCount() {
		return citizenDashboardGraphDAO.getCouncilProposalCount();
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilProposalCount(String deptName,
			int noOfDays) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptAndDurationWiseCouncilProposalCount(deptName, noOfDays,
				toDate.toString());
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilProposalCount(String deptName, int noOfDays) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getYearWiseCouncilProposalCount(deptName, noOfDays, toDate.toString());
	}

	public List<CouncilDeptWiseProposalDataEntity> getDeptWiseProposalGridData(String deptName, int noOfDays,
			int orgId) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseProposalGridData(deptName, noOfDays, toDate.toString(), orgId);
	}

	public List<CouncilProposalCntEntity> getCouncilAgendaCount() {
		return citizenDashboardGraphDAO.getCouncilAgendaCount();
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilAgendaCount() {
		return citizenDashboardGraphDAO.getYearWiseCouncilAgendaCount();
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilAgendaCount(String deptName, int noOfDays) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptAndDurationWiseCouncilAgendaCount(deptName, noOfDays, toDate.toString());
	}

	public List<CouncilDeptWiseAgendaDataEntity> getDeptWiseAgendaGridData(String deptName, int noOfDays, int orgId) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseAgendaGridData(deptName, noOfDays, toDate.toString(), orgId);
	}

	public List<CouncilProposalCntEntity> getCouncilMeetingCount() {
		return citizenDashboardGraphDAO.getCouncilMeetingCount();
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilMeetingCount() {
		return citizenDashboardGraphDAO.getYearWiseCouncilMeetingCount();
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilMeetingCount(String deptName, int noOfDays) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptAndDurationWiseCouncilMeetingCount(deptName, noOfDays,
				toDate.toString());
	}

	public List<CouncilDeptWiseMeetingDataEntity> getDeptWiseMeetingGridData(String deptName, int noOfDays, int orgId) {
		if (noOfDays != 0)
			noOfDays = getNoOfDays(noOfDays);
		if (deptName == null || deptName.trim().equalsIgnoreCase(""))
			deptName = null;
		FinancialYear financialYear = getFinancialYear();
		Date toDate = financialYear.getFaToDate();
		return citizenDashboardGraphDAO.getDeptWiseMeetingGridData(deptName, noOfDays, toDate.toString(), orgId);
	}

	private FinancialYear getFinancialYear() {
		return citizenDashboardGraphDAO.getFinancialYear();
	}

	
	private FinancialYear getCurrentFinancialYear() {
		return citizenDashboardGraphDAO.getCurrentFinancialYear();
	}
	/*
	 * SKDCL common APIs
	 */

	public SKDCLDashboardAllCountsEntity getSKDCLDashboardAllCounts() {
		return citizenDashboardGraphDAO.getSKDCLDashboardAllCounts();
	}

	/*
	 * HRMS Module APIs
	 */

	public List<HRMSCategoryWiseEmpCntEntity> getStatusWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getStatusWiseEmployeeCount();
	}

	public List<HRMSEmployeeGridDataEntity> getStatusWiseEmployeeData(String status) {
		if (status == null || status.trim().equalsIgnoreCase(""))
			status = null;
		return citizenDashboardGraphDAO.getEmployeeData(status, "STATUS");
	}

	public List<HRMSCategoryWiseEmpCntEntity> getCategoryWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getCategoryWiseEmployeeCount();
	}

	public List<HRMSEmployeeGridDataEntity> getCategoryWiseEmployeeData(String category) {
		if (category == null || category.trim().equalsIgnoreCase(""))
			category = null;
		return citizenDashboardGraphDAO.getEmployeeData(category, "CATEGORY");
	}

	public List<HRMSCategoryWiseEmpCntEntity> getDepartmentWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getDepartmentWiseEmployeeCount();
	}

	public List<HRMSEmployeeGridDataEntity> getDepartmentWiseEmployeeData(String dept) {
		if (dept == null || dept.trim().equalsIgnoreCase(""))
			dept = null;
		return citizenDashboardGraphDAO.getEmployeeData(dept, "DEPARTMENT");
	}

	public List<HRMSGenderRatioEntity> getGenderWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getGenderWiseEmployeeCount();
	}

	public List<HRMSEmployeeGridDataEntity> getGenderWiseEmployeeData(String gender) {
		if (gender == null || gender.trim().equalsIgnoreCase(""))
			gender = null;
		return citizenDashboardGraphDAO.getEmployeeData(gender, "GENDER");
	}

	public List<HRMSSalaryBreakdownEntity> getSalaryRangeWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getSalaryRangeWiseEmployeeCount();
	}

	public List<HRMSEmployeeGridDataEntity> getSalaryRangeDeptWiseEmployeeData(String dept, String salaryRange) {
		if (dept == null || dept.trim().equalsIgnoreCase(""))
			dept = null;
		if (salaryRange == null || salaryRange.trim().equalsIgnoreCase(""))
			salaryRange = null;
		return citizenDashboardGraphDAO.getSalaryRangeDeptWiseEmployeeData(dept, salaryRange);
	}

	public List<HRMSCategoryWiseEmpCntEntity> getJoiningYearWiseEmployeeCount() {
		return citizenDashboardGraphDAO.getJoiningYearWiseEmployeeCount();
	}
	
	public List<HRMSEmployeeGridDataEntity> getJoiningYearWiseEmployeeData(String year) {
		if (year == null || year.trim().equalsIgnoreCase(""))
			year = null;
		return citizenDashboardGraphDAO.getEmployeeData(year, "JOINING_YEAR");
	}

	public List<HRMSRecruitmentPostsEntity> getVacantPostInfo() {
		return citizenDashboardGraphDAO.getVacantPostInfo();
	}

	/*
	 * Store Module APIs
	 */

	public List<StoreItemCountEntity> getItemGroupWiseInventoryValue() {
		return citizenDashboardGraphDAO.getItemGroupWiseInventoryValue();
	}

	public List<StoreItemWiseInventoryCntEntity> getItemWiseInventoryValue() {
		return citizenDashboardGraphDAO.getItemWiseInventoryValue();
	}

	public List<StoreItemCountEntity> getItemGroupWiseItemCount() {
		return citizenDashboardGraphDAO.getItemGroupWiseItemCount();
	}

	public List<StoreItemCountEntity> getTopNMovingItemCounts(int limit) {
		return citizenDashboardGraphDAO.getTopNMovingItemCounts(limit);
	}

	public List<StoreItemDataEntity> getItemDataForGroup(String itemGroup) {
		if (itemGroup == null || itemGroup.trim().equalsIgnoreCase(""))
			itemGroup = null;
		return citizenDashboardGraphDAO.getItemDataForGroup(itemGroup);
	}

	// #120768
	public List<CouncilDeptWiseProposalCntEntity> getDeptWiseCouncilProposalCount(Long orgId) {
		FinancialYear financialYear = getCurrentFinancialYear();
		Date toDate = financialYear.getFaToDate();
		Date fromDate = financialYear.getFaFromDate();
		log.info("deptwise countil proposal count method is called");
		return citizenDashboardGraphDAO.getOrgIdWiseCouncilProposalCount(orgId, fromDate.toString(), toDate.toString());

	}

	public List<RTIApplicationByApplicantType> getRTIApplicationByApplicantType() {
		
		return  citizenDashboardGraphDAO.getRTIApplicationByApplicantType();
	}

	public List<RTIApplicationyBplFlagEntity> getRTIApplicationDetailByBplFactorWise() {
		
		return citizenDashboardGraphDAO.getRTIApplicationDetailByBplFactorWise();
	}

	public List<CouncilMeetingCountYrwise> getYearWiseCouncilMeetingCountByOrgId(Long orgId) {
		log.info("year wise countil meeting count method is called");
		return citizenDashboardGraphDAO.getYearWiseCouncilMeetingCountByOrgId(orgId);
	}

	public List<FinancialAndNonFinacialProposalCount> getFinancialAndNonFiancialProposalCountByOrgId(Long orgId) {
		log.info("year wise countil meeting count method is called");
		return citizenDashboardGraphDAO.getFinancialAndNonFiancialProposalCountByOrgId(orgId);
	}

	
	public List<CouncilMeetingCountYrwise> getYearWiseAgendaCountByOrgId(Long orgId) {
		log.info("year wise countil meeting count method is called");
		return citizenDashboardGraphDAO.getYearWiseAgendaCountByOrgId(orgId);
	}

	public List<RtiApplicationDtoByCondEntity> getRtiDrillDownDataByBplType(String bplType) {
		return citizenDashboardGraphDAO.getRtiDrillDownDataByBplType(bplType);
	}

	public List<RtiApplicationDtoByCondEntity> getRtiDrillDownDataByApplicantType(String applicantType) {
		return citizenDashboardGraphDAO.getRtiDrillDownDataByApplicantType(applicantType);
	}

	public List<RtiApplicationDtoByCondEntity> getRFAOpenClosedDrillDownData() {
		return citizenDashboardGraphDAO.getRFAOpenClosedDrillDownData();
	}

	public List<RtiApplicationDtoByCondEntity> getRSAPendingClosedDrillDownData() {
		return citizenDashboardGraphDAO.getRSAPendingClosedDrillDownData();
	}

	public List<RTIDasBoardAppealStatusEntity> getIndvidualRtiRfaOpenClosedData() {
		return citizenDashboardGraphDAO.getIndvidualRtiRfaOpenClosedData();
	}

	public List<RTIDasBoardAppealStatusEntity> getIndvidualRtiRSAOpenClosedData() {
		return citizenDashboardGraphDAO.getIndvidualRtiRSAOpenClosedData();
	}

	public List<AdvocateMastRegEntity> getAdvocateMstRegCount() {
		return citizenDashboardGraphDAO.getAdvocateMstRegCount();
	}
	
	/* For fetching Complaint drilldown details with Parameter 
	D#152479
	 * */

	public List<ComplaintEntity> getTotComplaintAndStatus() {
		return complaintDashBoardDao.getTotComplaintAndStatus();
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByCategory(String category) {
		return complaintDashBoardDao.getTotComplaintAndStatusByCategory(category);
	}


	public List<CommonComplaintEntity> getTotComplaintAndStatusByWard(String ward) {
		
		return complaintDashBoardDao.getgetTotComplaintAndStatusByWard(ward);
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByZone(String zone) {
		
		return complaintDashBoardDao.getTotComplaintAndStatusByZone(zone);
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByDept(String deptId) {
		return complaintDashBoardDao.getTotComplaintAndStatusByDept(deptId);
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusBySubCategory(String subCategory) {
		return complaintDashBoardDao.getTotComplaintAndStatusBySubCategory(subCategory);
	}
}
