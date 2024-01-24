package com.abm.mainet.common.dashboard.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dashboard.domain.AdvocateMastRegEntity;
import com.abm.mainet.common.dashboard.domain.CitizenDashboardGraphEntity;
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
import com.abm.mainet.common.domain.FinancialYear;

@Repository
public class CitizenDashboardGraphDAO {

	private static final Logger log = LoggerFactory.getLogger(CitizenDashboardGraphDAO.class);

	@PersistenceContext
	protected EntityManager entityManager;

	public List<CitizenDashboardGraphEntity> getCitizenDashboardGraphEntityList() {
		List<CitizenDashboardGraphEntity> citizenDashboardGraphEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select\r\n"
				+ "sum(coalesce(y.totalRecieved,0)) as recieved_query,\r\n"
				+ "sum(coalesce(y.Pending,0)) as Pending_query,\r\n" + "sum(coalesce(y.closed,0)) as closed_query,\r\n"
				+ "sum(coalesce(y.expired,0)) as expired_query,\r\n" + "y.FY_Year as FY_Year\r\n" + "from\r\n"
				+ "(select\r\n" + "count(tb1.QUESTION_REG_ID) totalRecieved,\r\n"
				+ "(case when tb1.STATUS in ('OPEN','REOPEN')THEN COUNT(1) END) Pending,\r\n"
				+ "-- (case when tb1.STATUS ='OPEN'|| tb1.STATUS ='REOPEN' THEN COUNT(1) END) Pending,\r\n"
				+ "(case when tb1.STATUS='CONCLUDED' THEN COUNT(1) END) closed,\r\n"
				+ "(case when tb1.DEADLINE_DATE <= curdate() THEN COUNT(1) END) expired,\r\n"
				+ "(select concat(date_format(FA_FROMDATE,'%Y'),'-',date_format(FA_TODATE,'%Y')) from tb_financialyear\r\n"
				+ "where tb1.QUESTION_DATE between FA_FROMDATE and FA_TODATE) FY_Year\r\n" + "from\r\n"
				+ "tb_lqp_query_registration tb1\r\n"
				+ "-- where COALESCE(tb1.ORGID,0)=(case when COALESCE(13,0)=0 then COALESCE(tb1.ORGID,0) else COALESCE(13,0) end)\r\n"
				+ "group by tb1.STATUS,tb1.QUESTION_REG_ID,tb1.DEADLINE_DATE,tb1.QUESTION_DATE,FY_Year) y\r\n"
				+ "group by y.totalRecieved,FY_Year )\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");

		citizenDashboardGraphEntityList = (List<CitizenDashboardGraphEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CitizenDashboardGraphEntity.class).getResultList();

		return citizenDashboardGraphEntityList;
	}

	public List<DropdownEntity> getDropdownSevenDaysEntityList() {
		List<DropdownEntity> dropdownSevenDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select date_format(x.DATE_OF_REQUEST,'%d-%m-%Y') as Years,\r\n" + " sum(Closed) as Closed,\r\n"
				+ " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n" + " sum(Rejected) as Rejected,\r\n"
				+ " count(1) as counts\r\n" + " from\r\n" + " (select \r\n" + " C.DATE_OF_REQUEST,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c\r\n" + " where c.apm_application_id=b.apm_application_id) X\r\n"
				+ " where x.DATE_OF_REQUEST between DATE_SUB(CURDATE(), INTERVAL 7 DAY) and CURDATE()\r\n"
				+ " group by date_format(x.DATE_OF_REQUEST,'%d-%m-%Y')\r\n"
				+ " order by date_format(x.DATE_OF_REQUEST,'%d-%m-%Y')  desc)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownSevenDaysEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownSevenDaysEntityList;
	}

	public List<DropdownEntity> getDropdownSixMonthsEntity() {
		List<DropdownEntity> dropdownSevenDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select date_format(x.DATE_OF_REQUEST,'%b-%Y') as Years,\r\n" + " sum(Closed) as Closed,\r\n"
				+ " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n" + " sum(Rejected) as Rejected,\r\n"
				+ " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n" + " C.DATE_OF_REQUEST,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c\r\n" + " where c.apm_application_id=b.apm_application_id) X\r\n"
				+ " group by date_format(x.DATE_OF_REQUEST,'%b-%Y'),date_format(x.DATE_OF_REQUEST,'%Y%m')  \r\n"
				+ " order by date_format(x.DATE_OF_REQUEST,'%Y%m')   DESC LIMIT 6)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownSevenDaysEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownSevenDaysEntityList;
	}

	public List<DropdownEntity> getDropdownFinancialYrEntity() {
		List<DropdownEntity> dropdownFinancialYrEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(year(fa_fromdate),'-',year(fa_todate)) Year,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c,\r\n" + " tb_financialyear a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownFinancialYrEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownFinancialYrEntityList;
	}

	public List<DropdownEntity> getDropdownHalfYearlyEntity() {
		List<DropdownEntity> dropdownHalfYearlyEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) Year,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c,\r\n" + " tb_financialhalfy a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year DESC LIMIT 2)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownHalfYearlyEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownHalfYearlyEntityList;
	}

	public List<DropdownEntity> getDropdownQuarterlyEntity() {
		List<DropdownEntity> dropdownQuarterlyEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) Year,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c,\r\n" + " tb_financialquarty a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year DESC LIMIT 4)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownQuarterlyEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownQuarterlyEntityList;
	}

	public List<TotalCollectionSWMEntity> getTotalCollectionSWMEntityList() {
		List<TotalCollectionSWMEntity> totalCollectionSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.O_NLS_ORGNAME,sum(a.rm_amount) as SUM_AMOUNT\r\n" + " from \r\n" + " tb_receipt_mas a,\r\n"
				+ " tb_organisation c,\r\n" + " (select Distinct RM_RCPTID from tb_receipt_det b,\r\n"
				+ " tb_tax_mas d,\r\n" + " tb_comparent_det e,\r\n" + " tb_comparent_mas f,\r\n"
				+ " tb_comparam_mas g\r\n" + " where \r\n" + " b.TAX_ID=d.TAX_ID and\r\n"
				+ " d.TAX_CATEGORY1=e.COD_ID and\r\n" + " g.cpm_id=f.CPM_ID and\r\n" + " f.COM_ID=e.COM_ID and\r\n"
				+ " g.CPM_PREFIX='TAC' and\r\n" + " e.COD_VALUE='SC') x\r\n" + " where \r\n"
				+ " a.orgid=c.orgid and\r\n" + " x.RM_RCPTID=a.RM_RCPTID and\r\n"
				+ " c.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORGID=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ " group by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR\r\n"
				+ " order by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		totalCollectionSWMEntityList = (List<TotalCollectionSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), TotalCollectionSWMEntity.class).getResultList();
		return totalCollectionSWMEntityList;
	}

	public List<TotalServiceCntSWMEntity> getTotalServiceCntSWMEntityList() {
		List<TotalServiceCntSWMEntity> totalServiceCntSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select c.O_NLS_ORGNAME,\r\n"
				+ " c.O_NLS_ORGNAME_MAR,\r\n" + " count(a.APM_APPLICATION_ID) ServiceCount from \r\n"
				+ " tb_cfc_application_mst a,\r\n" + " tb_services_mst b,\r\n" + " tb_organisation c\r\n"
				+ " where a.SM_SERVICE_ID=b.SM_SERVICE_ID and\r\n" + " a.ORGID=b.ORGID and\r\n"
				+ " a.ORGID=c.orgid and\r\n" + " \r\n"
				+ " c.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORGID=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ " group by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR\r\n"
				+ " order by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		totalServiceCntSWMEntityList = (List<TotalServiceCntSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), TotalServiceCntSWMEntity.class).getResultList();
		return totalServiceCntSWMEntityList;
	}

	public List<TotalTransCntSWMEntity> getTotalTransCntSWMEntityList() {
		List<TotalTransCntSWMEntity> totalTransCntSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select c.O_NLS_ORGNAME,\r\n"
				+ " c.O_NLS_ORGNAME_MAR,\r\n" + " count(a.APM_APPLICATION_ID) as COUNT_RM_RCPTNO from \r\n"
				+ " tb_cfc_application_mst a,\r\n" + " tb_services_mst b,\r\n" + " tb_organisation c\r\n"
				+ " where a.SM_SERVICE_ID=b.SM_SERVICE_ID and\r\n" + " a.ORGID=b.ORGID and\r\n"
				+ " a.ORGID=c.orgid and\r\n" + " \r\n"
				+ " c.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORGID=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ " group by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR\r\n"
				+ " order by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		totalTransCntSWMEntityList = (List<TotalTransCntSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), TotalTransCntSWMEntity.class).getResultList();
		return totalTransCntSWMEntityList;
	}

	public List<TotalCollectionAmtSWMEntity> getTotalCollectionAmtSWMEntityList() {
		List<TotalCollectionAmtSWMEntity> totalCollectionAmtSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.O_NLS_ORGNAME,sum(a.rm_amount) as SUM_RM_AMT\r\n" + " from \r\n" + " tb_receipt_mas a,\r\n"
				+ " tb_organisation c,\r\n" + " (select Distinct RM_RCPTID from tb_receipt_det b,\r\n"
				+ " tb_tax_mas d,\r\n" + " tb_comparent_det e,\r\n" + " tb_comparent_mas f,\r\n"
				+ " tb_comparam_mas g\r\n" + " where \r\n" + " b.TAX_ID=d.TAX_ID and\r\n"
				+ " d.TAX_CATEGORY1=e.COD_ID and\r\n" + " g.cpm_id=f.CPM_ID and\r\n" + " f.COM_ID=e.COM_ID and\r\n"
				+ " g.CPM_PREFIX='TAC' and\r\n" + " e.COD_VALUE!='SC') x\r\n" + " where \r\n"
				+ " a.orgid=c.orgid and\r\n" + " x.RM_RCPTID=a.RM_RCPTID and\r\n"
				+ " c.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(c.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) and\r\n"
				+ " c.ORGID=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ " group by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR\r\n"
				+ " order by c.O_NLS_ORGNAME,c.O_NLS_ORGNAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		totalCollectionAmtSWMEntityList = (List<TotalCollectionAmtSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), TotalCollectionAmtSWMEntity.class).getResultList();
		return totalCollectionAmtSWMEntityList;
	}

	public List<VehicleTypeDryWetEntity> getVehicleTypeDryWetEntityList() {
		List<VehicleTypeDryWetEntity> vehicleTypeDryWetEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=Z.ORG_CPD_ID_STATE) State,\r\n"
				+ " z.O_NLS_ORGNAME OrganisationName,z.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " z.ORG_LATITUDE,\r\n" + " z.ORG_LONGITUDE,\r\n" + " y.CPD_DESC VechicleType,\r\n"
				+ " y.CPD_DESC_MAR HVechicleType,\r\n" + " sum(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(HAZ) HAZ\r\n" + " from (SELECT f.VE_VETYPE,\r\n" + " a.ORGID, \r\n" + " SUM(case  \r\n"
				+ " when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY, \r\n"
				+ " SUM(case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,\r\n"
				+ " SUM(case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ\r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e,\r\n"
				+ " tb_sw_vehicle_mast f\r\n" + " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID\r\n"
				+ " AND a.VE_ID=f.ve_id\r\n" + " group by f.VE_VETYPE,a.ORGID) x,\r\n" + " tb_comparam_det y,\r\n"
				+ " tb_organisation z\r\n" + " where x.VE_VETYPE=y.cpd_id \r\n" + " and x.orgid=z.orgid\r\n"
				+ " group by Z.ORG_CPD_ID_STATE,z.O_NLS_ORGNAME,z.O_NLS_ORGNAME_MAR,z.ORG_CPD_ID_DIS,z.ORG_CPD_ID_DIV,z.ORG_LATITUDE,z.ORG_LONGITUDE,x.VE_VETYPE,y.CPD_DESC,y.CPD_DESC_MAR)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		vehicleTypeDryWetEntityList = (List<VehicleTypeDryWetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), VehicleTypeDryWetEntity.class).getResultList();
		return vehicleTypeDryWetEntityList;
	}

	public List<VehicleTypeDryWetEntity> getVehicleTypeDryWetFinancialDurationWiseEntityList(int noOfDays,
			String toDate) {
		List<VehicleTypeDryWetEntity> vehicleTypeDryWetEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=Z.ORG_CPD_ID_STATE) State,\r\n"
				+ " z.O_NLS_ORGNAME OrganisationName,z.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " z.ORG_LATITUDE,\r\n" + " z.ORG_LONGITUDE,\r\n" + " y.CPD_DESC VechicleType,\r\n"
				+ " y.CPD_DESC_MAR HVechicleType,\r\n" + " sum(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(HAZ) HAZ\r\n" + " from (SELECT f.VE_VETYPE,\r\n" + " a.ORGID, \r\n" + " SUM(case  \r\n"
				+ " when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY, \r\n"
				+ " SUM(case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,\r\n"
				+ " SUM(case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ\r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e,\r\n"
				+ " tb_sw_vehicle_mast f\r\n" + " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID\r\n"
				+ " AND a.VE_ID=f.ve_id\r\n" + "AND x.CREATED_DATE BETWEEN DATE_SUB('" + toDate + "', INTERVAL "
				+ noOfDays + " day) and '" + toDate + "'" + " group by f.VE_VETYPE,a.ORGID) x,\r\n"
				+ " tb_comparam_det y,\r\n" + " tb_organisation z\r\n" + " where x.VE_VETYPE=y.cpd_id \r\n"
				+ " and x.orgid=z.orgid\r\n"
				+ " group by Z.ORG_CPD_ID_STATE,z.O_NLS_ORGNAME,z.O_NLS_ORGNAME_MAR,z.ORG_CPD_ID_DIS,z.ORG_CPD_ID_DIV,z.ORG_LATITUDE,z.ORG_LONGITUDE,x.VE_VETYPE,y.CPD_DESC,y.CPD_DESC_MAR)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		vehicleTypeDryWetEntityList = (List<VehicleTypeDryWetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), VehicleTypeDryWetEntity.class).getResultList();
		return vehicleTypeDryWetEntityList;
	}

	public List<VehicleAndWasteTypeByDaysEntity> getVehicleAndWasteByDaysEntityList(String vehicleType,
			String wasteType, int noOfDays, int orgId, String toDate) {
		List<VehicleAndWasteTypeByDaysEntity> vehicleTypeDryWetEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select (select cpd_desc from tb_comparam_det where cpd_id=Z.ORG_CPD_ID_STATE) State,\r\n"
				+ " z.O_NLS_ORGNAME OrganisationName,z.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=z.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " z.ORG_LATITUDE,\r\n" + " z.ORG_LONGITUDE,\r\n" + " y.CPD_DESC VechicleType,\r\n"
				+ " y.CPD_DESC_MAR HVechicleType,\r\n" + " x.TRIP_VOLUME, x.CREATED_DATE\r\n"
				+ " from (SELECT f.VE_VETYPE,\r\n" + " a.ORGID, x.TRIP_VOLUME, x.CREATED_DATE\r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e,\r\n"
				+ " tb_sw_vehicle_mast f\r\n" + " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID\r\n"
				+ " AND a.VE_ID=f.ve_id\r\n");
		if (wasteType != null)
			queryBuilder.append(" AND e.COD_VALUE = '" + wasteType + "'\r\n");
		queryBuilder.append(" -- AND a.ORGID = " + orgId + "\r\n" + " AND x.CREATED_DATE between DATE_SUB('" + toDate
				+ "', INTERVAL " + noOfDays + " day) and '" + toDate + "') x,\r\n" + " tb_comparam_det y,\r\n"
				+ " tb_organisation z\r\n" + " where x.VE_VETYPE=y.cpd_id \r\n" + " and x.orgid=z.orgid\r\n");
		if (vehicleType != null)
			queryBuilder.append(" AND y.CPD_DESC = '" + vehicleType + "'");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		vehicleTypeDryWetEntityList = (List<VehicleAndWasteTypeByDaysEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), VehicleAndWasteTypeByDaysEntity.class).getResultList();
		return vehicleTypeDryWetEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphDryWasteEntityList() {
		List<DryWasteHazSWMEntity> graphDryWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC ,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='DW'\r\n"
				+ " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.O_NLS_ORGNAME_MAR,  y.ORG_CPD_ID_DIS,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphDryWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphDryWasteEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphDryWasteFinancialDurationWiseEntityList(int noOfDays, String toDate) {
		List<DryWasteHazSWMEntity> graphDryWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC ,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='DW'\r\n"
				+ " and a.CREATED_DATE BETWEEN DATE_SUB('" + toDate + "', INTERVAL " + noOfDays + " day) and '" + toDate
				+ "'" + " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.O_NLS_ORGNAME_MAR,  y.ORG_CPD_ID_DIS,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphDryWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphDryWasteEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphWetWasteEntityList() {
		List<DryWasteHazSWMEntity> graphWetWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='WW'\r\n"
				+ " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.ORG_CPD_ID_DIS,y.O_NLS_ORGNAME_MAR,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR  )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphWetWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphWetWasteEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphWetWasteFinancialDurationWiseEntityList(int noOfDays, String toDate) {
		List<DryWasteHazSWMEntity> graphWetWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='WW'\r\n"
				+ " and a.CREATED_DATE BETWEEN DATE_SUB('" + toDate + "', INTERVAL " + noOfDays + " day) and '" + toDate
				+ "'" + " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.ORG_CPD_ID_DIS,y.O_NLS_ORGNAME_MAR,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR  )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphWetWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphWetWasteEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphHazWasteEntityList() {
		List<DryWasteHazSWMEntity> graphHazWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='HZ'\r\n"
				+ " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.O_NLS_ORGNAME_MAR,y.ORG_CPD_ID_DIS,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR  )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphHazWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphHazWasteEntityList;
	}

	public List<DryWasteHazSWMEntity> getGraphHazWasteFinancialDurationWiseEntityList(int noOfDays, String toDate) {
		List<DryWasteHazSWMEntity> graphHazWasteEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName,y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC,\r\n" + " COD_DESC_MAR,\r\n"
				+ " sum(Dry) DryWaste\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " sum(TRIP_VOLUME) Dry\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id and\r\n" + " b.cod_value='HZ'\r\n"
				+ " and a.CREATED_DATE BETWEEN DATE_SUB('" + toDate + "', INTERVAL " + noOfDays + " day) and '" + toDate
				+ "'" + " group by a.orgid,a.COD_WAST2) x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id \r\n"
				+ " group by y.ORG_CPD_ID_STATE,y.O_NLS_ORGNAME,y.O_NLS_ORGNAME_MAR,y.ORG_CPD_ID_DIS,y.ORG_CPD_ID_DIV,y.ORG_LATITUDE,y.ORG_LONGITUDE,COD_DESC,COD_DESC_MAR  )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		graphHazWasteEntityList = (List<DryWasteHazSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DryWasteHazSWMEntity.class).getResultList();
		return graphHazWasteEntityList;
	}

	public List<DrillDownWasteTypeWiseEntity> getDrillDownDryWasteTypeWiseEntityList(String wasteType, String codDesc,
			int noOfDays, String toDate) {
		List<DrillDownWasteTypeWiseEntity> drillDownWasteTypeWiseEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_STATE) State,\r\n"
				+ " y.O_NLS_ORGNAME OrganisationName, y.O_NLS_ORGNAME_MAR HCity,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) DistrinctName,\r\n"
				+ " (select cpd_desc from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) DivisonName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIS) HDistrinctName,\r\n"
				+ " (select cpd_desc_mar from tb_comparam_det where cpd_id=y.ORG_CPD_ID_DIV) HDivisonName,\r\n"
				+ " y.ORG_LATITUDE,\r\n" + " y.ORG_LONGITUDE,\r\n" + " COD_DESC ,\r\n" + " COD_DESC_MAR,\r\n"
				+ " x.TRIP_VOLUME, x.CREATED_DATE\r\n" + " from\r\n" + " (select a.ORGID,\r\n" + " a.COD_WAST2,\r\n"
				+ " TRIP_VOLUME, a.CREATED_DATE\r\n" + " from tb_sw_wasteseg_det a,\r\n" + " tb_comparent_det b\r\n"
				+ " where a.COD_WAST1=cod_id");
		if (wasteType != null)
			queryBuilder.append(" and\r\n b.cod_value='" + wasteType + "'\r\n");
		queryBuilder.append(" AND a.CREATED_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
				+ " day) and '" + toDate + "') x,\r\n" + " tb_organisation y,\r\n" + " tb_comparent_det z\r\n"
				+ " where x.ORGID=y.orgid and\r\n" + " x.COD_WAST2=cod_id ");
		if (codDesc != null)
			queryBuilder.append(" and\r\n COD_DESC = '" + codDesc + "'\r\n");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		drillDownWasteTypeWiseEntityList = (List<DrillDownWasteTypeWiseEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DrillDownWasteTypeWiseEntity.class).getResultList();
		return drillDownWasteTypeWiseEntityList;
	}

	public List<FinancialYearSWMEntity> getFinancialYrSWMEntityList() {
		List<FinancialYearSWMEntity> financialYrSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " concat(year(fa_fromdate),'-',year(fa_todate)) year,\r\n" + " sum(DRY) DRY,\r\n"
				+ " SUM(WATE) WATE,\r\n" + " SUM(HAZ) HAZ\r\n" + " from\r\n" + " (SELECT a.TRIP_DATE AS TRIP_DATE,\r\n"
				+ " (case  when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY,\r\n"
				+ " (case  when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,      \r\n"
				+ " (case   when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ      \r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e\r\n"
				+ " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID) X,\r\n" + " tb_financialyear y\r\n"
				+ " where TRIP_DATE between y.FA_FROMDATE and y.FA_TODATE AND\r\n"
				+ " CURDATE() > FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE\r\n"
				+ " group by concat(year(fa_fromdate),'-',year(fa_todate)) )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		financialYrSWMEntityList = (List<FinancialYearSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYearSWMEntity.class).getResultList();
		return financialYrSWMEntityList;
	}

	public List<FinancialYearSWMEntity> getHalfYrSWMEntityList() {
		List<FinancialYearSWMEntity> halfYrSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) year,\r\n"
				+ " sum(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n" + " SUM(HAZ) HAZ\r\n" + " from\r\n"
				+ " (SELECT a.TRIP_DATE AS TRIP_DATE,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,      \r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ      \r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e\r\n"
				+ " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID) X,\r\n"
				+ " tb_financialhalfy y\r\n" + " where TRIP_DATE between y.FA_FROMDATE and y.FA_TODATE AND\r\n"
				+ " CURDATE() > FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 2 YEAR) < FA_FROMDATE\r\n"
				+ " group by concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		halfYrSWMEntityList = (List<FinancialYearSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYearSWMEntity.class).getResultList();
		return halfYrSWMEntityList;
	}

	public List<FinancialYearSWMEntity> getLastFourQuarterSWMEntityList() {
		List<FinancialYearSWMEntity> lastFourQuarterSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) year,\r\n"
				+ " sum(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n" + " SUM(HAZ) HAZ\r\n" + " from\r\n"
				+ " (SELECT a.TRIP_DATE AS TRIP_DATE,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,      \r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ      \r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e\r\n"
				+ " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID) X,\r\n"
				+ " tb_financialquarty y\r\n" + " where TRIP_DATE between y.FA_FROMDATE and y.FA_TODATE AND\r\n"
				+ " CURDATE() > FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 4 YEAR) < FA_FROMDATE\r\n"
				+ " group by concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) )\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		lastFourQuarterSWMEntityList = (List<FinancialYearSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYearSWMEntity.class).getResultList();
		return lastFourQuarterSWMEntityList;
	}

	public List<FinancialYearSWMEntity> getLastSevenDaysSWMEntityList() {
		List<FinancialYearSWMEntity> lastSevenDaysSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " date_format(x.TRIP_DATE,'%d-%m-%Y') year,\r\n" + " sum(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(HAZ) HAZ\r\n" + " from\r\n" + " (SELECT a.TRIP_DATE AS TRIP_DATE,\r\n"
				+ " (case when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) AS DRY,\r\n"
				+ " (case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) AS WATE,\r\n"
				+ " (case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) AS HAZ\r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e\r\n"
				+ " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID) X\r\n"
				+ " where x.TRIP_DATE between DATE_SUB(CURDATE(), INTERVAL 7 DAY) and CURDATE()            \r\n"
				+ " group by date_format(x.TRIP_DATE,'%d-%m-%Y') )\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		lastSevenDaysSWMEntityList = (List<FinancialYearSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYearSWMEntity.class).getResultList();
		return lastSevenDaysSWMEntityList;
	}

	public List<MonthlySWMEntity> getMonthlySWMEntityList() {
		List<MonthlySWMEntity> monthlySWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select \r\n"
				+ " date_format(x.TRIP_DATE,'%b-%Y') as year,\r\n"
				+ " date_format(x.TRIP_DATE,'%Y%m') as DATE_STRING, \r\n" + " sum(DRY) as DRY,\r\n"
				+ " SUM(WATE) as WATE,\r\n" + " SUM(HAZ) as HAZ\r\n" + " from\r\n"
				+ " (SELECT a.TRIP_DATE AS TRIP_DATE,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY,\r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,      \r\n" + " (case  \r\n"
				+ " when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) HAZ\r\n" + " FROM\r\n"
				+ " tb_sw_tripsheet a,\r\n" + " tb_sw_tripsheet_gdet x,\r\n" + " tb_comparent_det e\r\n"
				+ " WHERE x.WAST_TYPE = e.COD_ID\r\n" + " AND x.TRIP_ID = a.TRIP_ID) X\r\n"
				+ " group by date_format(x.TRIP_DATE,'%b-%Y'),date_format(x.TRIP_DATE,'%Y%m')  \r\n"
				+ " order by date_format(x.TRIP_DATE,'%Y%m')   DESC LIMIT 6)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		monthlySWMEntityList = (List<MonthlySWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), MonthlySWMEntity.class).getResultList();
		return monthlySWMEntityList;
	}

	public List<FirstTableSWMEntity> getFirstTableSWMEntityList() {
		List<FirstTableSWMEntity> firstTableSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select\r\n"
				+ " x.ORG_CPD_ID_DIV,\r\n" + " Division,\r\n" + " DivisionReg,\r\n" + " SUM(DRY) as DRY,\r\n"
				+ " SUM(WATE) as WATE,\r\n" + " SUM(Haz) as Haz\r\n" + " from\r\n" + " (select  \r\n" + " y.orgid,\r\n"
				+ " Y.O_NLS_ORGNAME,\r\n" + " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.ORG_CPD_ID_STATE,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateReg,\r\n"
				+ " y.ORG_CPD_ID_DIV,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) Division,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) DivisionReg,\r\n"
				+ " y.ORG_CPD_ID_DIS,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictReg,\r\n"
				+ " Y.ORG_LATITUDE,\r\n" + " Y.ORG_LONGITUDE,\r\n" + " SUM(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(Haz) Haz\r\n" + " from\r\n" + " (SELECT a.ORGID,\r\n" + "       SUM(case  \r\n"
				+ "          when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY, \r\n"
				+ "        SUM(case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,\r\n"
				+ "        SUM(case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) Haz\r\n" + "        FROM\r\n"
				+ "            tb_sw_tripsheet a,\r\n" + "            tb_sw_tripsheet_gdet x,\r\n"
				+ "            tb_comparent_det e\r\n" + "        WHERE x.WAST_TYPE = e.COD_ID\r\n"
				+ "            AND x.TRIP_ID = a.TRIP_ID\r\n" + " group by a.ORGID) x,\r\n" + " tb_organisation y\r\n"
				+ " where x.orgid=y.ORGID and\r\n"
				+ " y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ " group by \r\n" + " y.ORG_CPD_ID_DIV,\r\n" + " y.ORG_CPD_ID_DIS,\r\n" + " Y.O_NLS_ORGNAME,\r\n"
				+ " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.orgid,\r\n" + " Y.ORG_LATITUDE,\r\n" + " Y.ORG_LONGITUDE) x\r\n"
				+ " group by x.ORG_CPD_ID_DIV)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		firstTableSWMEntityList = (List<FirstTableSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FirstTableSWMEntity.class).getResultList();
		return firstTableSWMEntityList;
	}

	public List<FirstTableYearwiseSWMEntity> getFirstTableYearlySWMEntityList(int orgId) {
		List<FirstTableYearwiseSWMEntity> firstTableSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select x.SW_YEAR as YEAR,\r\n" + " x.ORG_CPD_ID_DIV,\r\n" + " Division,\r\n" + " DivisionReg,\r\n"
				+ " SUM(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n" + " SUM(Haz) Haz\r\n" + " from\r\n" + " (select  \r\n"
				+ " y.orgid,\r\n" + " Y.O_NLS_ORGNAME,\r\n" + " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.ORG_CPD_ID_STATE,\r\n"
				+ " x.SW_YEAR,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateReg,\r\n"
				+ " y.ORG_CPD_ID_DIV,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) Division,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) DivisionReg,\r\n"
				+ " y.ORG_CPD_ID_DIS,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictReg,\r\n"
				+ " Y.ORG_LATITUDE,\r\n" + " Y.ORG_LONGITUDE,\r\n" + " SUM(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(Haz) Haz\r\n" + " from\r\n" + " (SELECT year(x.CREATED_DATE) as SW_YEAR,\r\n"
				+ "       SUM(case  \r\n" + "          when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY, \r\n"
				+ "        SUM(case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,\r\n"
				+ "        SUM(case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) Haz\r\n" + "        FROM\r\n"
				+ "            tb_sw_tripsheet a,\r\n" + "            tb_sw_tripsheet_gdet x,\r\n"
				+ "            tb_comparent_det e\r\n" + "        WHERE x.WAST_TYPE = e.COD_ID\r\n"
				+ "            AND x.TRIP_ID = a.TRIP_ID \r\n "
				+ "-- AND a.ORGID = " + orgId + "\r\n" 
				+ " group by SW_YEAR) x,\r\n"
				+ " tb_organisation y\r\n" + " where \r\n "
				+ "-- y.ORGID = " + orgId + " and\r\n"
				+ " y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ " group by \r\n" + " x.SW_YEAR,\r\n" + " y.ORG_CPD_ID_DIV,\r\n" + " y.ORG_CPD_ID_DIS,\r\n"
				+ " Y.O_NLS_ORGNAME,\r\n" + " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.orgid,\r\n" + " Y.ORG_LATITUDE,\r\n"
				+ " Y.ORG_LONGITUDE) x\r\n"
				+ " group by SW_YEAR, x.ORG_CPD_ID_DIV ) as t, (SELECT @row_number\\:=0) AS rn");
		firstTableSWMEntityList = (List<FirstTableYearwiseSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FirstTableYearwiseSWMEntity.class).getResultList();
		return firstTableSWMEntityList;
	}

	public List<FirstTableYearwiseSWMEntity> getFirstTableCurrYearSWMEntityList(int orgId) {
		List<FirstTableYearwiseSWMEntity> firstTableSWMEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select x.SW_YEAR as YEAR,\r\n" + " x.ORG_CPD_ID_DIV,\r\n" + " Division,\r\n" + " DivisionReg,\r\n"
				+ " SUM(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n" + " SUM(Haz) Haz\r\n" + " from\r\n" + " (select  \r\n"
				+ " y.orgid,\r\n" + " Y.O_NLS_ORGNAME,\r\n" + " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.ORG_CPD_ID_STATE,\r\n"
				+ " x.SW_YEAR,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_STATE) StateReg,\r\n"
				+ " y.ORG_CPD_ID_DIV,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) Division,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIV) DivisionReg,\r\n"
				+ " y.ORG_CPD_ID_DIS,\r\n"
				+ " (select CPD_DESC from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictEng,\r\n"
				+ " (select CPD_DESC_MAR from tb_comparam_det where CPD_ID=y.ORG_CPD_ID_DIS) DistrictReg,\r\n"
				+ " Y.ORG_LATITUDE,\r\n" + " Y.ORG_LONGITUDE,\r\n" + " SUM(DRY) DRY,\r\n" + " SUM(WATE) WATE,\r\n"
				+ " SUM(Haz) Haz\r\n" + " from\r\n" + " (SELECT year(x.CREATED_DATE) as SW_YEAR,\r\n"
				+ "       SUM(case  \r\n" + "          when e.COD_VALUE = 'DW' then x.TRIP_VOLUME else 0 end) DRY, \r\n"
				+ "        SUM(case when e.COD_VALUE = 'WW' then x.TRIP_VOLUME else 0 end) WATE,\r\n"
				+ "        SUM(case when e.COD_VALUE = 'HZ' then x.TRIP_VOLUME else 0 end) Haz\r\n" + "        FROM\r\n"
				+ "            tb_sw_tripsheet a,\r\n" + "            tb_sw_tripsheet_gdet x,\r\n"
				+ "            tb_comparent_det e\r\n" + "        WHERE x.WAST_TYPE = e.COD_ID\r\n"
				+ "            AND x.TRIP_ID = a.TRIP_ID \r\n"
				+ "-- AND a.ORGID = " + orgId + "\r\n"
				+ " group by SW_YEAR) x,\r\n"
				+ " tb_organisation y\r\n" + " where \r\n "
				+ "-- y.ORGID = " + orgId + " and\r\n"
				+ " y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ " group by \r\n" + " x.SW_YEAR,\r\n" + " y.ORG_CPD_ID_DIV,\r\n" + " y.ORG_CPD_ID_DIS,\r\n"
				+ " Y.O_NLS_ORGNAME,\r\n" + " Y.O_NLS_ORGNAME_MAR,\r\n" + " y.orgid,\r\n" + " Y.ORG_LATITUDE,\r\n"
				+ " Y.ORG_LONGITUDE) x\r\n" + " where year(SW_YEAR) = year(now())\r\n"
				+ " group by SW_YEAR, x.ORG_CPD_ID_DIV ) as t, (SELECT @row_number\\:=0) AS rn");
		firstTableSWMEntityList = (List<FirstTableYearwiseSWMEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FirstTableYearwiseSWMEntity.class).getResultList();
		return firstTableSWMEntityList;
	}

	public List<DeptComplaintsEntity> getDeptComplaintsEntityList() {
		List<DeptComplaintsEntity> deptComplaintsEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select z.DP_DEPTDESC,\r\n"
				+ "        z.DP_NAME_MAR,\r\n" + "        sum(Closed) as Closed,\r\n"
				+ "        sum(Pending) as Pending,\r\n" + "        sum(Rejected) as Rejected,\r\n"
				+ "        sum(Hold) as Hold,\r\n" + "        count(1) as Received\r\n" + "        from\r\n"
				+ "        (select C.DEPT_COMP_ID,\r\n" + "        c.ORGID,\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_care_request c\r\n" + "        where c.apm_application_id=b.apm_application_id\r\n"
				+ "        ) x,\r\n" + "        tb_dept_complaint_type y,\r\n" + "        tb_department z,\r\n"
				+ "        tb_organisation u\r\n" + "        where y.DEPT_ID=X.DEPT_COMP_ID and\r\n"
				+ "        y.DEPT_ID=z.DP_DEPTID and\r\n" + "        x.orgid=u.orgid and\r\n"
				+ "        u.orgid=(case when COALESCE(@x,0)=0 then COALESCE(u.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "        group by z.DP_DEPTDESC,z.DP_NAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsEntityList = (List<DeptComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptComplaintsEntity.class).getResultList();
		return deptComplaintsEntityList;
	}

	public List<ModeComplaintsEntity> getModeComplaintsEntityList() {
		List<ModeComplaintsEntity> modeComplaintsEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select x.REFERENCE_MODE, \r\n" + "			z.cpd_desc,\r\n" + "			z.cpd_desc_mar,\r\n"
				+ "			sum(Closed) as Closed,\r\n" + "			sum(Pending) as Pending,\r\n"
				+ "			sum(Rejected) as Rejected,\r\n" + "			sum(Hold) as Hold,\r\n"
				+ "			count(1) as Received\r\n" + "			from\r\n" + "			(select c.ORGID,\r\n"
				+ "			 c.REFERENCE_MODE,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold\r\n" + "			from tb_workflow_request b,\r\n"
				+ "			tb_care_request c\r\n" + "			where c.apm_application_id=b.apm_application_id) x,\r\n"
				+ "			tb_organisation y,\r\n" + "			tb_comparam_det Z \r\n"
				+ "			where x.orgid=y.ORGID and\r\n" + "			x.REFERENCE_MODE=z.cpd_id and\r\n"
				+ "			y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ "			group by x.REFERENCE_MODE)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsEntityList = (List<ModeComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ModeComplaintsEntity.class).getResultList();
		return modeComplaintsEntityList;
	}

	public List<DeptComplaintsEntity> getDeptComplaintsByDaysEntityList(int noOfDays) {
		List<DeptComplaintsEntity> deptComplaintsByDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select z.DP_DEPTDESC,\r\n"
				+ "        z.DP_NAME_MAR,\r\n" + "        sum(Closed) Closed,\r\n" + "        sum(Pending) Pending,\r\n"
				+ "        sum(Rejected) Rejected,\r\n" + "        sum(Hold) Hold,\r\n"
				+ "        count(1) Received\r\n" + "\r\n" + "        from\r\n" + "        (select C.DEPT_COMP_ID,\r\n"
				+ "        c.ORGID,\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_care_request c\r\n" + "        where c.apm_application_id=b.apm_application_id\r\n"
				+ "		and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n"
				+ "        ) x,\r\n" + "  tb_department z,\r\n"
				+ "        tb_organisation u\r\n" + "        where x.DEPT_COMP_ID=z.DP_DEPTID and\r\n"
				+ "        x.orgid=u.orgid and\r\n"
				+ "        u.orgid=(case when COALESCE(@x,0)=0 then COALESCE(u.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "        group by z.DP_DEPTDESC,z.DP_NAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsByDaysEntityList = (List<DeptComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptComplaintsEntity.class).getResultList();
		return deptComplaintsByDaysEntityList;
	}

	public List<ModeComplaintsEntity> getModeComplaintsByDaysEntityList(int noOfDays) {
		List<ModeComplaintsEntity> modeComplaintsByDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select x.REFERENCE_MODE, \r\n" + "			z.cpd_desc,\r\n" + "			z.cpd_desc_mar,\r\n"
				+ "			sum(Closed) Closed,\r\n" + "			sum(Pending) Pending,\r\n"
				+ "			sum(Rejected) Rejected,\r\n" + "			sum(Hold) Hold,\r\n"
				+ "			count(1) Received\r\n" + "			from\r\n" + "			(select c.ORGID,\r\n"
				+ "			 c.REFERENCE_MODE,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold\r\n" + "			from tb_workflow_request b,\r\n"
				+ "			tb_care_request c\r\n" + "			where c.apm_application_id=b.apm_application_id\r\n"
				+ "			and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays
				+ " day) and now()) x,\r\n" + "			tb_organisation y,\r\n" + "			tb_comparam_det Z \r\n"
				+ "			where x.orgid=y.ORGID and\r\n" + "			x.REFERENCE_MODE=z.cpd_id and\r\n"
				+ "			y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ "			group by x.REFERENCE_MODE)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsByDaysEntityList = (List<ModeComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ModeComplaintsEntity.class).getResultList();
		return modeComplaintsByDaysEntityList;
	}

	public List<SLAAnalysisEntity> getSLAAnalysisEntityList() {
		List<SLAAnalysisEntity> slaAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select sum(BeyondRejectSLA) as BeyondRejectSLA,\r\n"
				+ " sum(WithinRejectSLA) as WithinRejectSLA,\r\n" + " sum(BeyondCloseSLA) as BeyondCloseSLA,\r\n"
				+ " sum(WithinCloseSLA) as WithinCloseSLA,\r\n" + " sum(BeyondPendingSLA) as BeyondPendingSLA,\r\n"
				+ " sum(WithinPendingSLA) as WithinPendingSLA,\r\n" + " sum(BeyondSLA) as BeyondSLA,\r\n"
				+ " sum(WithinSLA) as WithinSLA\r\n" + " from\r\n" + " (select c.ORGID,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondRejectSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinRejectSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondCloseSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinCloseSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondPendingSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinPendingSLA,\r\n" + " (CASE WHEN \r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondSLA,\r\n" + " (CASE WHEN \r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinSLA\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_care_request c,\r\n" + " tb_workflow_mas a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n" + " a.WF_ID =b.WORFLOW_TYPE_ID) x,\r\n"
				+ " tb_organisation y,\r\n" + " tb_comparam_det Z \r\n" + " where x.orgid=y.ORGID and\r\n"
				+ " y.ORG_CPD_ID_DIV=z.cpd_id and\r\n"
				+ " y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end))\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		slaAnalysisEntityList = (List<SLAAnalysisEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), SLAAnalysisEntity.class).getResultList();
		return slaAnalysisEntityList;
	}

	public List<OrganizationEntity> getOrgEntityList() {
		List<OrganizationEntity> organizationEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"select ORGID,O_NLS_ORGNAME,O_NLS_ORGNAME_MAR from tb_organisation a where a.ORG_STATUS='A' order by O_NLS_ORGNAME");
		organizationEntityList = (List<OrganizationEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), OrganizationEntity.class).getResultList();
		return organizationEntityList;
	}

	public List<DurationEntity> getYearlyDurationEntityList() {
		List<DurationEntity> durationEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select concat(date_format(fa_fromdate,'%Y'),'-',date_format(fa_todate,'%Y')) as Duration\r\n"
				+ "from tb_financialyear c\r\n"
				+ "where c.FA_TODATE AND CURDATE() > c.FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < c.FA_FROMDATE\r\n"
				+ "order by Duration asc)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		durationEntityList = (List<DurationEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DurationEntity.class).getResultList();
		return durationEntityList;
	}

	public List<DurationEntity> getHalfYearlyDurationEntityList() {
		List<DurationEntity> durationEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) as Duration\r\n"
				+ "from tb_financialhalfy c\r\n"
				+ "where c.FA_TODATE AND CURDATE() > c.FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR) < c.FA_FROMDATE\r\n"
				+ "order by Duration asc)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		durationEntityList = (List<DurationEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DurationEntity.class).getResultList();
		return durationEntityList;
	}

	public List<DurationEntity> getQuarterlyDurationEntityList() {
		List<DurationEntity> durationEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) as Duration\r\n"
				+ "from tb_financialquarty c\r\n"
				+ "where c.FA_TODATE AND CURDATE() > c.FA_FROMDATE  AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR) < c.FA_FROMDATE)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		durationEntityList = (List<DurationEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DurationEntity.class).getResultList();
		return durationEntityList;
	}

	public DashboardAllCountsEntity getDashboardAllCounts() {
		DashboardAllCountsEntity dashboardAllCountsEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select count(*) from tb_rti_application) as rtiCount, (select count(*) from tb_care_request) as grievancesCount,\r\n"
				+ "(select count(*) from TB_SW_CONSTDEMO_GARBAGECOLL) as solidWasteCount, (select count(*) from tb_department where status = 'A') as deptCount,\r\n"
				+ "(select count(*) from tb_services_mst where SM_SERV_ACTIVE = 39) as serviceCount, ");
		queryBuilder.append("(select (crCnt + rtiCnt + lglCnt + swmCnt + lqpQCnt) from (\r\n" + "select \r\n"
				+ "(select count(*) from tb_care_request) as crCnt, \r\n"
				+ "(select count(*) from tb_rti_application) as rtiCnt,\r\n"
				+ "(select count(*) from tb_lgl_case_mas) as lglCnt,\r\n"
				+ "(select count(*) from TB_SW_CONSTDEMO_GARBAGECOLL) as swmCnt,\r\n"
				+ "(select count(*) from tb_lqp_query_registration) as lqpQCnt\r\n"
				+ "from dual) as cnts) as applReceivedCount,\r\n");
		queryBuilder.append("(select (crCnt + rtiCnt + lglCnt + swmCnt + lqpQCnt) from (\r\n" + "select \r\n"
				+ "(select sum(Closed) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END)  Closed\r\n" + "from tb_workflow_request b,\r\n"
				+ "tb_care_request c\r\n" + "where c.apm_application_id=b.apm_application_id) x) as crCnt, \r\n"
				+ "(select sum(Closed) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END)  Closed\r\n" + "from tb_workflow_request b,\r\n"
				+ "tb_rti_application c\r\n" + "where c.apm_application_id=b.apm_application_id) x) as rtiCnt,\r\n"
				+ "(select count(*) as CNT from tb_lgl_case_mas l,tb_lgl_hearing h,tb_comparam_det cd \r\n"
				+ "where h.cse_id=l.cse_id \r\n"
				+ "and  cd.CPD_ID = h.hr_status and cd.CPD_DESC='Concluded') as lglCnt,\r\n"
				+ "(select count(*) from TB_SW_CONSTDEMO_GARBAGECOLL sw,tb_workflow_request wf where \r\n"
				+ "sw.APM_APPLICATION_ID=wf.APM_APPLICATION_ID and wf.status='CLOSED') as swmCnt,\r\n"
				+ "(select count(*) from tb_lqp_query_registration where STATUS='CONCLUDED') as lqpQCnt\r\n"
				+ "from dual) as cnts) as applDisposedCount,\r\n");
		queryBuilder.append("(select count(*) from tb_lgl_case_mas) as legalCount,\r\n"
				+ "(select count(*) from tb_lqp_query_registration) as legisQueryCount, "
				+ "(select count(*) from tb_cmt_council_proposal_mast) as councilMgmtCount,\r\n"
				+ "(select 0) as hrmsCount, (select 0) as storeMgmtCount,\r\n"
				+ "(select 0) as propertyCount, (select 0) as waterCount,\r\n"
				+ "(select 0) as financialAccCount, (select 0) as advertiseTaxCount,\r\n"
				+ "(select 0) as landEstateCount, (select 0) as birthDeathCount,\r\n"
				+ "(select 0) as workMgmtSysCount\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		dashboardAllCountsEntity = (DashboardAllCountsEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), DashboardAllCountsEntity.class).getSingleResult();
		return dashboardAllCountsEntity;
	}

	public HRMSStoreCountEntity getDashboardHRMSCount() {
		HRMSStoreCountEntity hrmsCount = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + ""
				+ ") as t, (SELECT @row_number\\:=0) AS rn");

		try {
			hrmsCount = (HRMSStoreCountEntity) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSStoreCountEntity.class).getSingleResult();
			return hrmsCount;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public HRMSStoreCountEntity getDashboardStoreCount() {
		HRMSStoreCountEntity storeCount = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + ""
				+ ") as t, (SELECT @row_number\\:=0) AS rn");

		try {
			storeCount = (HRMSStoreCountEntity) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSStoreCountEntity.class).getSingleResult();
			return storeCount;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<DeptByDaysAndDescEntity> getDeptComplaintsByDaysAndDescEntityList(int noOfDays, String desc) {
		List<DeptByDaysAndDescEntity> deptComplaintsByDaysAndDescEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.APM_APPLICATION_ID, c.REFERENCE_MODE, (select EMPNAME from employee where EMPID = c.CREATED_BY) as CREATED_BY, c.COMPLAINT_NO, c.COMPLAINT_DESC, c.DATE_OF_REQUEST, z.DP_DEPTDESC as cpd_desc,\r\n"
				+ "			z.DP_NAME_MAR as cpd_desc_mar, \r\n" + "(CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_care_request c,\r\n" + "        tb_dept_complaint_type y,\r\n"
				+ "        tb_department z,\r\n" + "        tb_organisation u\r\n" + "        where \r\n"
				+ "		c.apm_application_id=b.apm_application_id and\r\n"
				+ "		b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now() and\r\n"
				+ "        z.DP_DEPTID=c.DEPT_COMP_ID and\r\n"
				+ "        c.orgid=u.orgid ");
		if (desc != null)
			queryBuilder.append(" and z.DP_DEPTDESC = '" + desc + "' \r\n");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsByDaysAndDescEntityList = (List<DeptByDaysAndDescEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptByDaysAndDescEntity.class).getResultList();
		return deptComplaintsByDaysAndDescEntityList;
	}

	public List<DeptByDaysAndDescEntity> getModeComplaintsByDaysAndDescEntityList(int noOfDays, String desc) {
		List<DeptByDaysAndDescEntity> modeComplaintsByDaysAndDescEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.APM_APPLICATION_ID, c.REFERENCE_MODE, (select EMPNAME from employee where EMPID = c.CREATED_BY) as CREATED_BY, c.COMPLAINT_NO, c.COMPLAINT_DESC, c.DATE_OF_REQUEST, z.cpd_desc,\r\n"
				+ "			z.cpd_desc_mar, \r\n" + "(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold \r\n" + "from tb_workflow_request b,\r\n"
				+ "			tb_care_request c,\r\n" + "			tb_organisation y,\r\n"
				+ "			tb_comparam_det Z \r\n" + "			where c.orgid=y.ORGID and\r\n"
				+ "			c.REFERENCE_MODE=z.cpd_id and\r\n"
				+ "            c.apm_application_id=b.apm_application_id\r\n"
				+ "			and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays
				+ " day) and now() ");
		if (desc != null)
			queryBuilder.append("and z.cpd_desc = '" + desc + "' \r\n");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsByDaysAndDescEntityList = (List<DeptByDaysAndDescEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptByDaysAndDescEntity.class).getResultList();
		return modeComplaintsByDaysAndDescEntityList;
	}

	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphEntityList() {
		List<YearWiseGrievanceGraphEntity> yrWiseTrendAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select       years as FY_Year,\r\n" + "            sum(Closed) as closed_query,\r\n"
				+ "            sum(Pending) as Pending_query,\r\n" + "            sum(Rejected) as expired_query,\r\n"
				+ "            sum(Hold) as Hold,\r\n" + "            count(1) as recieved_query from (\r\n"
				+ "select year(b.DATE_OF_REQUEST) as years,\r\n" + "(CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('HOLD') then 1 else 0 END)  Hold \r\n"
				+ "             from tb_workflow_request b,\r\n" + "             tb_care_request c\r\n"
				+ "             where c.apm_application_id=b.apm_application_id) x \r\n"
				+ "             group by x.years ) as t, (SELECT @row_number\\:=0) AS rn");
		yrWiseTrendAnalysisEntityList = (List<YearWiseGrievanceGraphEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), YearWiseGrievanceGraphEntity.class).getResultList();
		return yrWiseTrendAnalysisEntityList;
	}

	/*
	 * RTI Module APIs
	 */

	public List<SLAAnalysisEntity> getSLAAnalysisEntityRTIList() {
		List<SLAAnalysisEntity> slaAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select sum(BeyondRejectSLA) as BeyondRejectSLA,\r\n"
				+ " sum(WithinRejectSLA) as WithinRejectSLA,\r\n" + " sum(BeyondCloseSLA) as BeyondCloseSLA,\r\n"
				+ " sum(WithinCloseSLA) as WithinCloseSLA,\r\n" + " sum(BeyondPendingSLA) as BeyondPendingSLA,\r\n"
				+ " sum(WithinPendingSLA) as WithinPendingSLA,\r\n" + " sum(BeyondSLA) as BeyondSLA,\r\n"
				+ " sum(WithinSLA) as WithinSLA\r\n" + " from\r\n" + " (select c.ORGID,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondRejectSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinRejectSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondCloseSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinCloseSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondPendingSLA,\r\n" + " (CASE WHEN \r\n"
				+ " ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) and\r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinPendingSLA,\r\n" + " (CASE WHEN \r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) >= \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID =a.WF_ID)/ 1000) <> 0) \r\n"
				+ " THEN\r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE WF_ID = a.WF_ID) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID=a.SM_SERVICE_ID) \r\n"
				+ " END)) then 1 else 0 END)  BeyondSLA,\r\n" + " (CASE WHEN \r\n"
				+ " (TIMESTAMPDIFF(SECOND,b.DATE_OF_REQUEST,NOW()) < \r\n"
				+ " (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n"
				+ " WHERE (WF_ID = a.WF_ID)) / 1000) <> 0) \r\n" + " THEN \r\n"
				+ " ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID =a.WF_ID)) / 1000) \r\n"
				+ " ELSE \r\n"
				+ " (SELECT (SM_SERDUR/ 1000) FROM tb_services_mst WHERE SM_SERVICE_ID =a.SM_SERVICE_ID) \r\n"
				+ " END)) \r\n" + " then 1 else 0 END)  WithinSLA\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c,\r\n" + " tb_workflow_mas a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n" + " a.WF_ID =b.WORFLOW_TYPE_ID) x,\r\n"
				+ " tb_organisation y,\r\n" + " tb_comparam_det Z \r\n" + " where x.orgid=y.ORGID and\r\n"
				+ " y.ORG_CPD_ID_DIV=z.cpd_id and\r\n"
				+ " y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end))\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		slaAnalysisEntityList = (List<SLAAnalysisEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), SLAAnalysisEntity.class).getResultList();
		return slaAnalysisEntityList;
	}

	public List<DropdownEntity> getDropdownSevenDaysRTIEntityList() {
		List<DropdownEntity> dropdownSevenDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select date_format(x.APM_APPLICATION_DATE,'%d-%m-%Y') as Years,\r\n" + " sum(Closed) as Closed,\r\n"
				+ " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n" + " sum(Rejected) as Rejected,\r\n"
				+ " count(1) as counts\r\n" + " from\r\n" + " (select \r\n" + " C.APM_APPLICATION_DATE,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c\r\n" + " where c.apm_application_id=b.apm_application_id) X\r\n"
				+ " where x.APM_APPLICATION_DATE between DATE_SUB(CURDATE(), INTERVAL 7 DAY) and CURDATE()\r\n"
				+ " group by date_format(x.APM_APPLICATION_DATE,'%d-%m-%Y')\r\n"
				+ " order by date_format(x.APM_APPLICATION_DATE,'%d-%m-%Y')  desc)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownSevenDaysEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownSevenDaysEntityList;
	}

	public List<DropdownEntity> getDropdownSixMonthsRTIEntity() {
		List<DropdownEntity> dropdownSevenDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select date_format(x.APM_APPLICATION_DATE,'%b-%Y') as Years,\r\n" + " sum(Closed) as Closed,\r\n"
				+ " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n" + " sum(Rejected) as Rejected,\r\n"
				+ " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n" + " C.APM_APPLICATION_DATE,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c\r\n" + " where c.apm_application_id=b.apm_application_id) X\r\n"
				+ " group by date_format(x.APM_APPLICATION_DATE,'%b-%Y'),date_format(x.APM_APPLICATION_DATE,'%Y%m')  \r\n"
				+ " order by date_format(x.APM_APPLICATION_DATE,'%Y%m')   DESC LIMIT 6)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownSevenDaysEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownSevenDaysEntityList;
	}

	public List<DropdownEntity> getDropdownFinancialYrRTIEntity() {
		List<DropdownEntity> dropdownFinancialYrEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(year(fa_fromdate),'-',year(fa_todate)) Year,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c,\r\n" + " tb_financialyear a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownFinancialYrEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownFinancialYrEntityList;
	}

	public List<DropdownEntity> getDropdownHalfYearlyRTIEntity() {
		List<DropdownEntity> dropdownHalfYearlyEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) Year,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c,\r\n" + " tb_financialhalfy a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year DESC LIMIT 2)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownHalfYearlyEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownHalfYearlyEntityList;
	}

	public List<DropdownEntity> getDropdownQuarterlyRTIEntity() {
		List<DropdownEntity> dropdownQuarterlyEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select year as Years,\r\n"
				+ " sum(Closed) as Closed,\r\n" + " sum(Pending) as Pending,\r\n" + " sum(Hold) as Hold,\r\n"
				+ " sum(Rejected) as Rejected,\r\n" + " count(1) as Counts\r\n" + " from\r\n" + " (select \r\n"
				+ " concat(date_format(fa_fromdate,'%b%Y'),'-',date_format(fa_todate,'%b%Y')) Year,\r\n"
				+ " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + " (CASE WHEN \r\n"
				+ " (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ " WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ " IN ('HOLD') then 1 else 0 END)  Hold\r\n" + " from tb_workflow_request b,\r\n"
				+ " tb_rti_application c,\r\n" + " tb_financialquarty a\r\n"
				+ " where c.apm_application_id=b.apm_application_id and\r\n"
				+ " b.DATE_OF_REQUEST between fa_fromdate and fa_todate)b\r\n" + " group by year DESC LIMIT 4)\r\n"
				+ " as t, (SELECT @row_number\\:=0) AS rn");
		dropdownQuarterlyEntityList = (List<DropdownEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DropdownEntity.class).getResultList();
		return dropdownQuarterlyEntityList;
	}

	public List<DeptComplaintsEntity> getDeptComplaintsRTIEntityList() {
		List<DeptComplaintsEntity> deptComplaintsEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select z.DP_DEPTDESC,\r\n"
				+ "        z.DP_NAME_MAR,\r\n" + "        sum(Closed) as Closed,\r\n"
				+ "        sum(Pending) as Pending,\r\n" + "        sum(Rejected) as Rejected,\r\n"
				+ "        sum(Hold) as Hold,\r\n" + "        count(1) as Received\r\n" + "        from\r\n"
				+ "        (select C.RTI_DEPTID,\r\n" + "        c.ORGID,\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_rti_application c\r\n" + "        where c.apm_application_id=b.apm_application_id\r\n"
				+ "        ) x,\r\n" + "        tb_dept_complaint_type y,\r\n" + "        tb_department z,\r\n"
				+ "        tb_organisation u\r\n" + "        where y.DEPT_ID=X.RTI_DEPTID and\r\n"
				+ "        y.DEPT_ID=z.DP_DEPTID and\r\n" + "        x.orgid=u.orgid and\r\n"
				+ "        u.orgid=(case when COALESCE(@x,0)=0 then COALESCE(u.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "        group by z.DP_DEPTDESC,z.DP_NAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsEntityList = (List<DeptComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptComplaintsEntity.class).getResultList();
		return deptComplaintsEntityList;
	}

	public List<ModeComplaintsEntity> getModeComplaintsRTIEntityList() {
		List<ModeComplaintsEntity> modeComplaintsEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select x.APPL_REFERENCE_MODE as REFERENCE_MODE, \r\n" + "			z.cpd_desc,\r\n"
				+ "			z.cpd_desc_mar,\r\n" + "			sum(Closed) as Closed,\r\n"
				+ "			sum(Pending) as Pending,\r\n" + "			sum(Rejected) as Rejected,\r\n"
				+ "			sum(Hold) as Hold,\r\n" + "			count(1) as Received\r\n" + "			from\r\n"
				+ "			(select c.ORGID,\r\n" + "			 c.APPL_REFERENCE_MODE,\r\n"
				+ "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold\r\n" + "			from tb_workflow_request b,\r\n"
				+ "			tb_rti_application c\r\n"
				+ "			where c.apm_application_id=b.apm_application_id) x,\r\n"
				+ "			tb_organisation y,\r\n" + "			tb_comparam_det Z \r\n"
				+ "			where x.orgid=y.ORGID and\r\n" + "			x.APPL_REFERENCE_MODE=z.cpd_id and\r\n"
				+ "			y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ "			group by x.APPL_REFERENCE_MODE)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsEntityList = (List<ModeComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ModeComplaintsEntity.class).getResultList();
		return modeComplaintsEntityList;
	}

	public List<DeptComplaintsEntity> getDeptComplaintsByDaysRTIEntityList(int noOfDays) {
		List<DeptComplaintsEntity> deptComplaintsByDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select z.DP_DEPTDESC,\r\n"
				+ "        z.DP_NAME_MAR,\r\n" + "        sum(Closed) Closed,\r\n" + "        sum(Pending) Pending,\r\n"
				+ "        sum(Rejected) Rejected,\r\n" + "        sum(Hold) Hold,\r\n"
				+ "        count(1) Received\r\n" + "\r\n" + "        from\r\n" + "        (select C.RTI_DEPTID,\r\n"
				+ "        c.ORGID,\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_rti_application c\r\n" + "        where c.apm_application_id=b.apm_application_id\r\n"
				+ "		and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n"
				+ "        ) x,\r\n" + "        tb_dept_complaint_type y,\r\n" + "        tb_department z,\r\n"
				+ "        tb_organisation u\r\n" + "        where y.DEPT_ID=X.RTI_DEPTID and\r\n"
				+ "        y.DEPT_ID=z.DP_DEPTID and\r\n" + "        x.orgid=u.orgid and\r\n"
				+ "        u.orgid=(case when COALESCE(@x,0)=0 then COALESCE(u.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "        group by z.DP_DEPTDESC,z.DP_NAME_MAR)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsByDaysEntityList = (List<DeptComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptComplaintsEntity.class).getResultList();
		return deptComplaintsByDaysEntityList;
	}

	public List<ModeComplaintsEntity> getModeComplaintsByDaysRTIEntityList(int noOfDays) {
		List<ModeComplaintsEntity> modeComplaintsByDaysEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select x.APPL_REFERENCE_MODE as REFERENCE_MODE, \r\n" + "	z.cpd_desc,\r\n"
				+ "			z.cpd_desc_mar,\r\n" + "	sum(Closed) Closed,\r\n"
				+ "			sum(Pending) Pending,\r\n" + "		sum(Rejected) Rejected,\r\n"
				+ "			sum(Hold) Hold,\r\n" + "		count(1) Received\r\n" + "			from\r\n"
				+ "			(select c.ORGID,\r\n" + "	c.APPL_REFERENCE_MODE,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'CLOSED')  AND (`b`.`LAST_DECISION` <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(`b`.`STATUS`,'EXPIRED','CLOSED') = 'PENDING') AND (`b`.`LAST_DECISION` <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold\r\n" + "			from tb_workflow_request b,\r\n"
				+ "			tb_rti_application c\r\n" + "			where c.apm_application_id=b.apm_application_id\r\n"
				+ "			and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays
				+ " day) and now()) x,\r\n" + "			tb_organisation y,\r\n" + "			tb_comparam_det Z \r\n"
				+ "			where x.orgid=y.ORGID and\r\n" + "			x.APPL_REFERENCE_MODE=z.cpd_id and\r\n"
				+ "			y.orgid=(case when COALESCE(@x,0)=0 then COALESCE(y.orgid,0) else COALESCE(@x,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ "			y.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(y.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ "			group by x.APPL_REFERENCE_MODE)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsByDaysEntityList = (List<ModeComplaintsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ModeComplaintsEntity.class).getResultList();
		return modeComplaintsByDaysEntityList;
	}

	public List<DeptByDaysAndDescEntity> getDeptComplaintsByDaysAndDescRTIEntityList(int noOfDays, String desc) {
		List<DeptByDaysAndDescEntity> deptComplaintsByDaysAndDescEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.APM_APPLICATION_ID, c.APPL_REFERENCE_MODE as REFERENCE_MODE, (select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY, c.RTI_NO as COMPLAINT_NO, c.RTI_SUBJECT as COMPLAINT_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST, z.DP_DEPTDESC as cpd_desc,\r\n"
				+ "			z.DP_NAME_MAR as cpd_desc_mar, \r\n" + "(CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('CLOSED') then 1 else 0 END) Closed,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('PENDING') then 1 else 0 END) Pending,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('REJECTED') then 1 else 0 END) Rejected,\r\n" + "\r\n" + "        (CASE WHEN\r\n"
				+ "        (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "        WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "        IN ('HOLD') then 1 else 0 END) Hold\r\n" + "        from tb_workflow_request b,\r\n"
				+ "        tb_rti_application c,\r\n" + "        tb_dept_complaint_type y,\r\n"
				+ "        tb_department z,\r\n" + "        tb_organisation u\r\n" + "        where \r\n"
				+ "		c.apm_application_id=b.apm_application_id and\r\n"
				+ "		b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now() and\r\n"
				+ "        y.DEPT_ID=c.RTI_DEPTID and\r\n" + "        y.DEPT_ID=z.DP_DEPTID and\r\n"
				+ "        c.orgid=u.orgid");
		if (desc != null)
			queryBuilder.append(" and z.DP_DEPTDESC = '" + desc + "' \r\n");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		deptComplaintsByDaysAndDescEntityList = (List<DeptByDaysAndDescEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptByDaysAndDescEntity.class).getResultList();
		return deptComplaintsByDaysAndDescEntityList;
	}

	public List<DeptByDaysAndDescEntity> getModeComplaintsByDaysAndDescRTIEntityList(int noOfDays, String desc) {
		List<DeptByDaysAndDescEntity> modeComplaintsByDaysAndDescEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select c.APM_APPLICATION_ID, c.APPL_REFERENCE_MODE as REFERENCE_MODE, \r\n"
				+ "(select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY, \r\n"
				+ "c.RTI_NO as COMPLAINT_NO, c.RTI_SUBJECT as COMPLAINT_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST, z.cpd_desc,\r\n"
				+ "			z.cpd_desc_mar, \r\n" + "(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "			(CASE WHEN \r\n"
				+ "			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) \r\n"
				+ "			IN ('HOLD') then 1 else 0 END)  Hold \r\n" + "from tb_workflow_request b,\r\n"
				+ "			tb_rti_application c,\r\n" + "			tb_organisation y,\r\n"
				+ "			tb_comparam_det Z \r\n" + "			where c.orgid=y.ORGID and\r\n"
				+ "			c.APPL_REFERENCE_MODE=z.cpd_id and\r\n"
				+ "            c.apm_application_id=b.apm_application_id\r\n"
				+ "			and b.date_of_request between DATE_SUB(CURDATE(), INTERVAL " + noOfDays
				+ " day) and now() ");
		if (desc != null)
			queryBuilder.append("and z.cpd_desc = '" + desc + "' \r\n");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		modeComplaintsByDaysAndDescEntityList = (List<DeptByDaysAndDescEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptByDaysAndDescEntity.class).getResultList();
		return modeComplaintsByDaysAndDescEntityList;
	}

	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphRTIEntityList() {
		List<YearWiseGrievanceGraphEntity> yrWiseTrendAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select       years as FY_Year,\r\n" + "    sum(Closed) as closed_query,\r\n"
				+ "            sum(Pending) as Pending_query,\r\n" + "            sum(Rejected) as expired_query,\r\n"
				+ "            sum(Hold) as Hold,\r\n" + "            count(1) as recieved_query from (\r\n"
				+ "select year(b.DATE_OF_REQUEST) as years,\r\n" + "(CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('CLOSED') then 1 else 0 END)  Closed,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('PENDING') then 1 else 0 END)  Pending,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('REJECTED') then 1 else 0 END)  Rejected,\r\n" + "            (CASE WHEN\r\n"
				+ "             (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
				+ "                   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n"
				+ "            IN ('HOLD') then 1 else 0 END)  Hold \r\n"
				+ "             from tb_workflow_request b,\r\n" + "  tb_rti_application c\r\n"
				+ "             where c.apm_application_id=b.apm_application_id) x \r\n"
				+ "             group by x.years ) as t, (SELECT @row_number\\:=0) AS rn");
		yrWiseTrendAnalysisEntityList = (List<YearWiseGrievanceGraphEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), YearWiseGrievanceGraphEntity.class).getResultList();
		return yrWiseTrendAnalysisEntityList;
	}

	public RTIApplicationCountEntity getRTIFirstAppealCount() {
		RTIApplicationCountEntity rtiApplicationCountEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (SELECT count(*) as COUNT FROM tb_objection_mast tom,tb_rti_application tra where tom.OBJ_STATUS ='"
						+ MainetConstants.TASK_STATUS_PENDING
						+ "' and tom.APM_APPLICATION_ID=tra.APM_APPLICATION_ID) as t, (SELECT @row_number\\:=0) AS rn");
		rtiApplicationCountEntity = (RTIApplicationCountEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationCountEntity.class).getSingleResult();
		return rtiApplicationCountEntity;
	}

	public RTIApplicationCountEntity getRTISecondAppealCount() {
		RTIApplicationCountEntity rtiApplicationCountEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (SELECT count(*) as COUNT FROM tb_objection_mast tom,tb_rti_application tra where tom.OBJ_STATUS ='"
						+ MainetConstants.TASK_STATUS_COMPLETED
						+ "' and tom.APM_APPLICATION_ID=tra.APM_APPLICATION_ID) as t, (SELECT @row_number\\:=0) AS rn");
		rtiApplicationCountEntity = (RTIApplicationCountEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationCountEntity.class).getSingleResult();
		return rtiApplicationCountEntity;
	}

	public RTIApplicationCountEntity getRTIOpenFirstAndSecondAppealCount() {
		RTIApplicationCountEntity rtiApplicationCountEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (SELECT count(*) as COUNT FROM tb_objection_mast tom,tb_rti_application tra where tom.OBJ_STATUS in ('"
						+ MainetConstants.TASK_STATUS_COMPLETED + "','" + MainetConstants.TASK_STATUS_PENDING
						+ "') and tom.APM_APPLICATION_ID=tra.APM_APPLICATION_ID) as t, (SELECT @row_number\\:=0) AS rn");
		rtiApplicationCountEntity = (RTIApplicationCountEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationCountEntity.class).getSingleResult();
		return rtiApplicationCountEntity;
	}

	public RTIApplicationCountEntity getRTIClosedFirstAndSecondAppealCount() {
		RTIApplicationCountEntity rtiApplicationCountEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (SELECT count(*) as COUNT FROM tb_objection_mast tom,tb_rti_application tra where tom.OBJ_STATUS in ('"
						+ MainetConstants.TASK_STATUS_COMPLETED + "','"
						+ MainetConstants.RTISERVICE.TASK_STATUS_SCNDAPPL_COMP
						+ "') and  tom.APM_APPLICATION_ID=tra.APM_APPLICATION_ID) as t, (SELECT @row_number\\:=0) AS rn");
		rtiApplicationCountEntity = (RTIApplicationCountEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationCountEntity.class).getSingleResult();
		return rtiApplicationCountEntity;
	}

	public RTIApplicationCountEntity getTotalReceivedRTIApplications() {
		RTIApplicationCountEntity rtiApplicationCountEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (SELECT count(*) as COUNT FROM tb_rti_application) as t, (SELECT @row_number\\:=0) AS rn");
		rtiApplicationCountEntity = (RTIApplicationCountEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationCountEntity.class).getSingleResult();
		return rtiApplicationCountEntity;
	}

	/*
	 * Legal Module APIs
	 */

	public List<LegalCountsEntity> getLegalReceivedYrWiseEntityList() {
		List<LegalCountsEntity> legalCountEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, year(created_date) as YR from tb_lgl_case_mas\r\n"
				+ "group by year(created_date) ) as t, (SELECT @row_number\\:=0) AS rn");
		legalCountEntityList = (List<LegalCountsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LegalCountsEntity.class).getResultList();
		return legalCountEntityList;
	}

	public List<LegalCountsEntity> getLegalClosedYrWisetEntityList() {
		List<LegalCountsEntity> legalCountEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, year(l.created_date) as YR from tb_lgl_case_mas l,tb_lgl_hearing h,tb_comparam_det cd \r\n"
				+ "where h.cse_id=l.cse_id \r\n" + "and  cd.CPD_ID = h.hr_status and cd.CPD_DESC='Concluded'\r\n"
				+ "group by year(l.created_date) ) as t, (SELECT @row_number\\:=0) AS rn");
		entityManager.clear();
		legalCountEntityList = (List<LegalCountsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LegalCountsEntity.class).getResultList();
		return legalCountEntityList;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseReceivedLegalCount() {
		List<DeptAndCourtWiseLegalCntEntity> deptAndCourtWiseLegalCntEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, d.DP_DEPTCODE as NAME from tb_lgl_case_mas l, tb_department d\r\n"
				+ "where  l.cse_deptid= d.DP_DEPTID\r\n"
				+ "and l.created_date between DATE_SUB(CURDATE(), INTERVAL 365 day) and now()\r\n"
				+ "group by d.DP_DEPTCODE ) as t, (SELECT @row_number\\:=0) AS rn");
		deptAndCourtWiseLegalCntEntityList = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptAndCourtWiseLegalCntEntityList;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getCourtWiseReceivedLegalCount() {
		List<DeptAndCourtWiseLegalCntEntity> deptAndCourtWiseLegalCntEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, ct.CRT_NAME as NAME from tb_lgl_case_mas cs,tb_lgl_court_mast ct \r\n"
				+ "where cs.crt_id = ct.crt_id\r\n"
				+ "and cs.created_date between DATE_SUB(CURDATE(), INTERVAL 365 day) and now()\r\n"
				+ "group by ct.CRT_NAME ) as t, (SELECT @row_number\\:=0) AS rn");
		deptAndCourtWiseLegalCntEntityList = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptAndCourtWiseLegalCntEntityList;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseReceivedLegalCount(int noOfDays) {
		List<DeptAndCourtWiseLegalCntEntity> deptAndCourtWiseLegalCntEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, d.DP_DEPTCODE as NAME from tb_lgl_case_mas l, tb_department d\r\n"
				+ "where  l.cse_deptid= d.DP_DEPTID\r\n" + "and l.created_date between DATE_SUB(CURDATE(), INTERVAL "
				+ noOfDays + " day) and now()\r\n" + "group by d.DP_DEPTCODE ) as t, (SELECT @row_number\\:=0) AS rn");
		deptAndCourtWiseLegalCntEntityList = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptAndCourtWiseLegalCntEntityList;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getCourtWiseReceivedLegalCount(int noOfDays) {
		List<DeptAndCourtWiseLegalCntEntity> deptAndCourtWiseLegalCntEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select count(*) as CNT, ct.CRT_NAME as NAME from tb_lgl_case_mas cs,tb_lgl_court_mast ct \r\n"
				+ "where cs.crt_id = ct.crt_id\r\n" + "and cs.created_date between DATE_SUB(CURDATE(), INTERVAL "
				+ noOfDays + " day) and now()\r\n" + "group by ct.CRT_NAME ) as t, (SELECT @row_number\\:=0) AS rn");
		deptAndCourtWiseLegalCntEntityList = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptAndCourtWiseLegalCntEntityList;
	}

	public List<DeptAndCourtWiseAllLegalDataEntity> getDeptWiseAllLegalData(String deptName, int noOfDays) {
		List<DeptAndCourtWiseAllLegalDataEntity> deptWiseAllLegalDataEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select COD_DESC from tb_comparent_det where COD_ID=l.cse_cat_id) as cse_category,\r\n"
				+ "l.cse_name as cse_name,l.cse_suit_no as cse_suit_no,l.cse_date as cse_date,\r\n"
				+ "l.cse_matdet_1 as cse_matdet_1,\r\n" + "d.DP_DEPTDESC as cse_dept,\r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.CPD_ID=l.cse_case_status_id) as cse_status,\r\n"
				+ "(select pd.csed_name from tb_lgl_case_pddetails pd where pd.cse_id= l.cse_id and pd.csed_flag = 'P') as case_filed_by,\r\n"
				+ "(select pd.csed_name from tb_lgl_case_pddetails pd where pd.cse_id= l.cse_id and pd.csed_flag = 'D') as case_filed_on,\r\n"
				+ "ct.CRT_NAME as crt_name\r\n" + "from tb_lgl_case_mas l, tb_department d, tb_lgl_court_mast ct \r\n"
				+ "where l.cse_deptid= d.DP_DEPTID AND\r\n" + "l.crt_id = ct.crt_id\r\n"
				+ "AND l.created_date between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTCODE = '" + deptName + "'");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseAllLegalDataEntityList = (List<DeptAndCourtWiseAllLegalDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseAllLegalDataEntity.class).getResultList();
		return deptWiseAllLegalDataEntityList;
	}

	public List<DeptAndCourtWiseAllLegalDataEntity> getCourtWiseAllLegalData(String courtName, int noOfDays) {
		List<DeptAndCourtWiseAllLegalDataEntity> deptCourtAllLegalDataEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select COD_DESC from tb_comparent_det where COD_ID=l.cse_cat_id) as cse_category,\r\n"
				+ "l.cse_name as cse_name,l.cse_suit_no as cse_suit_no,l.cse_date as cse_date,\r\n"
				+ "l.cse_matdet_1 as cse_matdet_1,\r\n" + "d.DP_DEPTDESC as cse_dept,\r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.CPD_ID=l.cse_case_status_id) as cse_status,\r\n"
				+ "(select pd.csed_name from tb_lgl_case_pddetails pd where pd.cse_id= l.cse_id and pd.csed_flag = 'P') as case_filed_by,\r\n"
				+ "(select pd.csed_name from tb_lgl_case_pddetails pd where pd.cse_id= l.cse_id and pd.csed_flag = 'D') as case_filed_on,\r\n"
				+ "ct.CRT_NAME as crt_name\r\n" + "from tb_lgl_case_mas l, tb_department d, tb_lgl_court_mast ct \r\n"
				+ "where l.cse_deptid= d.DP_DEPTID AND\r\n" + "l.crt_id = ct.crt_id\r\n"
				+ "AND l.created_date between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		if (courtName != null)
			queryBuilder.append("and ct.CRT_NAME = '" + courtName + "'");
		queryBuilder.append(" ) as t, (SELECT @row_number\\:=0) AS rn");
		deptCourtAllLegalDataEntityList = (List<DeptAndCourtWiseAllLegalDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseAllLegalDataEntity.class).getResultList();
		return deptCourtAllLegalDataEntityList;
	}

	/*
	 * Legislative Query Management module APIs
	 */

	public List<YearwiseTrendAnalysisEntity> getYrWiseTrendAnalysisEntityList() {
		List<YearwiseTrendAnalysisEntity> yrWiseTrendAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select\r\n"
				+ "sum(coalesce(y.totalRecieved,0)) as recieved_query,\r\n"
				+ "sum(coalesce(y.Pending,0)) as Pending_query,\r\n" + "sum(coalesce(y.closed,0)) as closed_query,\r\n"
				+ "sum(coalesce(y.expired,0)) as expired_query,\r\n" + "y.FY_Year\r\n" + "from\r\n" + "(select\r\n"
				+ "count(tb1.QUESTION_REG_ID) totalRecieved,\r\n"
				+ "(case when tb1.STATUS in ('OPEN','REOPEN')THEN COUNT(1) END) Pending,\r\n"
				+ "-- (case when tb1.STATUS ='OPEN'|| tb1.STATUS ='REOPEN' THEN COUNT(1) END) Pending,\r\n"
				+ "(case when tb1.STATUS='CONCLUDED' THEN COUNT(1) END) closed,\r\n"
				+ "(case when tb1.DEADLINE_DATE <= curdate() THEN COUNT(1) END) expired,\r\n"
				+ "(select concat(date_format(FA_FROMDATE,'%Y'),'-',date_format(FA_TODATE,'%Y')) from tb_financialyear\r\n"
				+ "where tb1.QUESTION_DATE between FA_FROMDATE and FA_TODATE) FY_Year\r\n" + "from\r\n"
				+ "tb_lqp_query_registration tb1\r\n"
				+ "-- where COALESCE(tb1.ORGID,0)=(case when COALESCE(13,0)=0 then COALESCE(tb1.ORGID,0) else COALESCE(13,0) end)\r\n"
				+ "group by tb1.STATUS,tb1.QUESTION_REG_ID,tb1.DEADLINE_DATE,tb1.QUESTION_DATE,FY_Year) y\r\n"
				+ "group by y.totalRecieved,FY_Year)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		yrWiseTrendAnalysisEntityList = (List<YearwiseTrendAnalysisEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), YearwiseTrendAnalysisEntity.class).getResultList();
		return yrWiseTrendAnalysisEntityList;
	}

	public List<DeptWiseTrendAnalysisEntity> getDeptWiseTrendAnalysisEntityList() {
		List<DeptWiseTrendAnalysisEntity> deptWiseTrendAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "Select\r\n"
				+ "count(QUESTION_REG_ID) as totalRecieved,\r\n"
				+ "(select d.DP_DEPTDESC from tb_department d where d.DP_DEPTID=tb1.DEPT_ID) as DepartmentName\r\n"
				+ "from\r\n" + "tb_lqp_query_registration tb1\r\n"
				+ "-- where COALESCE(tb1.ORGID,0)=(case when COALESCE(13,0)=0 then COALESCE(tb1.ORGID,0) else COALESCE(13,0) end)\r\n"
				+ "group by DepartmentName)\r\n" + " as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseTrendAnalysisEntityList = (List<DeptWiseTrendAnalysisEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptWiseTrendAnalysisEntity.class).getResultList();
		return deptWiseTrendAnalysisEntityList;
	}

	public List<YearwiseTrendAnalysisEntity> getCurrYrTrendAnalysisEntityList() {
		List<YearwiseTrendAnalysisEntity> yrWiseTrendAnalysisEntityList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "select\r\n"
				+ "sum(coalesce(y.totalRecieved,0)) recieved_query,\r\n"
				+ "sum(coalesce(y.Pending,0)) Pending_query,\r\n" + "sum(coalesce(y.closed,0)) closed_query,\r\n"
				+ "sum(coalesce(y.expired,0)) expired_query,\r\n" + "y.FY_Year\r\n" + "from\r\n" + "(select\r\n"
				+ "count(tb1.QUESTION_REG_ID) totalRecieved,\r\n"
				+ "(case when tb1.STATUS in ('OPEN','REOPEN')THEN COUNT(1) END) Pending,\r\n"
				+ "-- (case when tb1.STATUS ='OPEN'|| tb1.STATUS ='REOPEN' THEN COUNT(1) END) Pending,\r\n"
				+ "(case when tb1.STATUS='CONCLUDED' THEN COUNT(1) END) closed,\r\n"
				+ "(case when tb1.DEADLINE_DATE <= curdate() THEN COUNT(1) END) expired,\r\n"
				+ "(select concat(date_format(FA_FROMDATE,'%Y'),'-',date_format(FA_TODATE,'%Y')) from tb_financialyear\r\n"
				+ "where FA_YEARID = (select max(FA_YEARID) from tb_financialyear) and\r\n"
				+ "tb1.QUESTION_DATE between FA_FROMDATE and FA_TODATE) FY_Year\r\n" + "from\r\n"
				+ "tb_lqp_query_registration tb1\r\n"
				+ "-- where COALESCE(tb1.ORGID,0)=(case when COALESCE(13,0)=0 then COALESCE(tb1.ORGID,0) else COALESCE(13,0) end)\r\n"
				+ "group by tb1.STATUS,tb1.QUESTION_REG_ID,tb1.DEADLINE_DATE,tb1.QUESTION_DATE,FY_Year) y\r\n"
				+ "group by y.totalRecieved,FY_Year ) as t, (SELECT @row_number\\:=0) AS rn");
		yrWiseTrendAnalysisEntityList = (List<YearwiseTrendAnalysisEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), YearwiseTrendAnalysisEntity.class).getResultList();
		return yrWiseTrendAnalysisEntityList;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseLQPQuestionCount(String deptName, int noOfDays,
			String toDate) {
		List<DeptAndCourtWiseLegalCntEntity> deptWiseLQPQuestionCounts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select d.DP_DEPTDESC as NAME, count(*) as CNT from tb_lqp_query_registration lq, tb_department d\r\n"
				+ "where d.DP_DEPTID=lq.DEPT_ID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		queryBuilder.append("and QUESTION_DATE BETWEEN \r\n" + "DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
				+ " day) and '" + toDate + "'\r\n");
		if (deptName == null)
			queryBuilder.append("group by d.DP_DEPTDESC");
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseLQPQuestionCounts = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptWiseLQPQuestionCounts;
	}

	public List<DeptWiseQuestionDataLQPEntity> getDeptWiseLQPQuestionData(String deptName, int noOfDays,
			String toDate) {
		List<DeptWiseQuestionDataLQPEntity> deptWiseLQPQuestionData = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select lq.question_id, lq.QUESTION_DATE, lq.INWARD_NO, \r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.CPD_ID = lq.QUESTION_TYPE) as QUESTION_TYPE, \r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.cpd_id = lq.RAISED_BY_ASSEMBLY) as RAISED_BY_ASSEMBLY, \r\n"
				+ "d.DP_DEPTDESC, lq.QUESTION_SUBJECT, \r\n"
				+ "lq.MLA_NAME, lq.QUESTION, lq.MEETING_DATE, lq.DEADLINE_DATE, lq.STATUS, \r\n"
				+ "(select e.EMPNAME from employee e where e.EMPID = lq.CREATED_BY) as Question_CREATED_BY , \r\n"
				+ "(select e.EMPNAME from employee e where e.EMPID = lq.EMP_ID) as Questionee, \r\n"
				+ "lq.REOPEN_DATE\r\n" + "from tb_lqp_query_registration lq, tb_department d\r\n"
				+ "where d.DP_DEPTID=lq.DEPT_ID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		queryBuilder.append("and QUESTION_DATE BETWEEN \r\n" + "DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
				+ " day) and '" + toDate + "'\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseLQPQuestionData = (List<DeptWiseQuestionDataLQPEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptWiseQuestionDataLQPEntity.class).getResultList();
		return deptWiseLQPQuestionData;
	}

	public List<DeptAndCourtWiseLegalCntEntity> getDeptWiseLQPAnswerCount(String deptName, int noOfDays,
			String toDate) {
		List<DeptAndCourtWiseLegalCntEntity> deptWiseLQPAnswerCounts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select d.DP_DEPTDESC as NAME, count(*) as CNT from tb_lqp_query_answer lqa, tb_lqp_query_registration lq, tb_department d\r\n"
				+ "where lqa.QUESTION_REG_ID = lq.QUESTION_REG_ID\r\n" + "and d.DP_DEPTID=lq.DEPT_ID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		queryBuilder.append("and lqa.ANSWER_DATE BETWEEN \r\n" + "DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
				+ " day) and '" + toDate + "'\r\n");
		if (deptName == null)
			queryBuilder.append("group by d.DP_DEPTDESC");
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseLQPAnswerCounts = (List<DeptAndCourtWiseLegalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptAndCourtWiseLegalCntEntity.class).getResultList();
		return deptWiseLQPAnswerCounts;
	}

	public List<DeptWiseAnswerDataLQPEntity> getDeptWiseLQPAnswerData(String deptName, int noOfDays, String toDate) {
		List<DeptWiseAnswerDataLQPEntity> deptWiseLQPAnswerData = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select lq.question_id, lq.QUESTION_DATE, lq.INWARD_NO, \r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.CPD_ID = lq.QUESTION_TYPE) as QUESTION_TYPE, \r\n"
				+ "(select tcd.CPD_DESC from tb_comparam_det tcd where tcd.cpd_id = lq.RAISED_BY_ASSEMBLY) as RAISED_BY_ASSEMBLY, \r\n"
				+ "d.DP_DEPTDESC, lq.QUESTION_SUBJECT, \r\n"
				+ "lq.MLA_NAME, lq.QUESTION, lq.MEETING_DATE, lq.DEADLINE_DATE, lq.STATUS, \r\n"
				+ "(select e.EMPNAME from employee e where e.EMPID = lq.CREATED_BY) as Question_CREATED_BY , \r\n"
				+ "(select e.EMPNAME from employee e where e.EMPID = lq.EMP_ID) as Questionee, \r\n"
				+ "lq.REOPEN_DATE,\r\n" + "lqa.ANSWER_DATE, lqa.ANSWER, lqa.REMARK as ANSWER_REMARK,\r\n"
				+ "(select e.EMPNAME from employee e where e.EMPID = lqa.CREATED_BY) as Question_Answered_BY\r\n"
				+ "from tb_lqp_query_answer lqa, tb_lqp_query_registration lq, tb_department d\r\n"
				+ "where lqa.QUESTION_REG_ID = lq.QUESTION_REG_ID\r\n" + "and d.DP_DEPTID=lq.DEPT_ID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		queryBuilder.append("and lqa.ANSWER_DATE BETWEEN \r\n" + "DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
				+ " day) and '" + toDate + "'\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		deptWiseLQPAnswerData = (List<DeptWiseAnswerDataLQPEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptWiseAnswerDataLQPEntity.class).getResultList();
		return deptWiseLQPAnswerData;
	}

	/*
	 * Council Module APIs
	 */

	public List<CouncilProposalCntEntity> getCouncilProposalCount() {
		List<CouncilProposalCntEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select count(*) from tb_cmt_council_proposal_mast) as Received,\r\n"
				+ "(select count(*) from tb_cmt_council_proposal_mast where PROPOSAL_STATUS = 'R') as Rejected,\r\n"
				+ "(select count(*) from tb_cmt_council_proposal_mast where PROPOSAL_STATUS = 'A' ) as Approved,\r\n"
				+ "(select count(*) from tb_cmt_council_proposal_mast where PROPOSAL_STATUS in( 'P','D')) as Pending \r\n"
				+ "from dual ) as t, (SELECT @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilProposalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilProposalCntEntity.class).getResultList();
		return councilProposalList;
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilProposalCount(String deptName, int noOfDays,
			String toDate) {
		List<CouncilDeptYearWiseCntEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select  x.DP_DEPTDESC as DEPT_OR_YEAR,\r\n" + "sum(Pending) as Pending,\r\n"
				+ "sum(Rejected) as Rejected,\r\n" + "sum(Approved) as Approved,\r\n"
				+ "count(1) as Received from (\r\n" + "select d.DP_DEPTDESC, p.PROPOSAL_DATE,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (p.PROPOSAL_STATUS in ('P', 'D')) THEN 'Pending' END)\r\n"
				+ "IN ('Pending') then 1 else 0 END)  Pending,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (p.PROPOSAL_STATUS = 'R') THEN 'Rejected' END)\r\n"
				+ "IN ('Rejected') then 1 else 0 END)  Rejected,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (p.PROPOSAL_STATUS = 'A') THEN 'Approved' END)\r\n"
				+ "IN ('Approved') then 1 else 0 END)  Approved\r\n" + "from tb_cmt_council_proposal_mast p,\r\n"
				+ "tb_department d\r\n" + "where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '"
					+ toDate + "'\r\n");
		queryBuilder.append(") x group by x.DP_DEPTDESC ) as t, (SELECT @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilProposalList;
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilProposalCount(String deptName, int noOfDays,
			String toDate) {
		List<CouncilDeptYearWiseCntEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select year(x.PROPOSAL_DATE) as DEPT_OR_YEAR,\r\n" + "sum(Pending) as Pending,\r\n"
				+ "sum(Rejected) as Rejected,\r\n" + "sum(Approved) as Approved,\r\n"
				+ "count(1) as Received from (\r\n" + "select d.DP_DEPTDESC, p.PROPOSAL_DATE,\r\n" + "(CASE WHEN\r\n"
				+ "	(CASE WHEN (p.PROPOSAL_STATUS in ('P', 'D')) THEN 'Pending' END)\r\n"
				+ "	IN ('Pending') then 1 else 0 END) Pending,\r\n" + "	(CASE WHEN\r\n"
				+ "	(CASE WHEN (p.PROPOSAL_STATUS = 'R') THEN 'Rejected' END)\r\n"
				+ "	IN ('Rejected') then 1 else 0 END) Rejected,\r\n" + "	(CASE WHEN\r\n"
				+ "	(CASE WHEN (p.PROPOSAL_STATUS = 'A') THEN 'Approved' END)\r\n"
				+ "	IN ('Approved') then 1 else 0 END) Approved \r\n" + "from tb_cmt_council_proposal_mast p,\r\n"
				+ "tb_department d\r\n" + "where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '"
					+ toDate + "'\r\n");
		queryBuilder.append(") x group by year(x.PROPOSAL_DATE) ) as t, (SELECT @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilProposalList;
	}

	public List<CouncilDeptWiseProposalDataEntity> getDeptWiseProposalGridData(String deptName, int noOfDays,
			String toDate, int orgId) {
		List<CouncilDeptWiseProposalDataEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select  p.PROPOSAL_NUMBER, d.DP_DEPTDESC, p.PROPOSAL_DATE, p.PROPOSAL_DETAILS,\r\n"
				+ "p.PROPOSAL_AMOUNT, \r\n" + "( CASE \r\n" + "	WHEN (p.PROPOSAL_STATUS = 'A') THEN 'Approved'\r\n"
				+ "	WHEN (p.PROPOSAL_STATUS = 'R') THEN 'Rejected'\r\n"
				+ "	WHEN (p.PROPOSAL_STATUS in ('P', 'D')) THEN 'Pending' END\r\n" + "  ) as PROPOSAL_STATUS\r\n"
				+ ", p.purpose_remark, e.EMPNAME as CREATED_BY\r\n" + " from tb_cmt_council_proposal_mast p,\r\n"
				+ " tb_department d,\r\n" + " employee e\r\n" + " where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n"
				+ " and e.EMPID = p.CREATED_BY\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append("and p.PROPOSAL_DATE between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '"
					+ toDate + "'\r\n");
		queryBuilder.append("-- and p.ORGID = " + orgId + " \r\n "
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilDeptWiseProposalDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptWiseProposalDataEntity.class).getResultList();
		return councilProposalList;
	}

	public List<CouncilProposalCntEntity> getCouncilAgendaCount() {
		List<CouncilProposalCntEntity> councilAgendaList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select t.Received, t.Approved, (t.Received - t.Approved) as Pending, 0 as Rejected from (\r\n"
				+ "select\r\n" + "(select count(*) from tb_cmt_council_agenda_mast) as Received,\r\n"
				+ "(select count(*) from tb_cmt_council_agenda_mast a, TB_CMT_COUNCIL_MEETING_MAST m\r\n"
				+ "where a.AGENDA_ID = m.AGENDA_ID) as Approved ) t\r\n" + ") as ta, (SELECT @row_number\\:=0) AS rn");
		councilAgendaList = (List<CouncilProposalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilProposalCntEntity.class).getResultList();
		return councilAgendaList;
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilAgendaCount() {
		List<CouncilDeptYearWiseCntEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select t.DEPT_OR_YEAR, t.Received, t.Rejected, t.Approved,\r\n"
				+ "(t.Received - t.Approved) as Pending from (\r\n" + "select year(x.AGENDA_DATE) as DEPT_OR_YEAR,\r\n"
				+ "sum(0) as Rejected,\r\n" + "sum(Approved) as Approved,\r\n" + "count(1) as Received from (\r\n"
				+ "select a.AGENDA_DATE,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (a.AGENDA_ID <> m.AGENDA_ID) THEN 'PENDING' END)\r\n"
				+ "IN ('PENDING') then 1 else 0 END)  PENDING,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (a.AGENDA_ID = m.AGENDA_ID) THEN 'Approved' END)\r\n"
				+ "IN ('Approved') then 1 else 0 END)  Approved \r\n" + "from tb_cmt_council_agenda_mast a,\r\n"
				+ "TB_CMT_COUNCIL_MEETING_MAST m\r\n" + "where a.AGENDA_ID = m.AGENDA_ID ) x \r\n"
				+ "group by year(x.AGENDA_DATE) ) as t ) as ta, (SELECT @row_number\\:=0) AS rn");
		// queryBuilder.append(") x group by year(x.PROPOSAL_DATE) ) as t, (SELECT
		// @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilProposalList;
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilAgendaCount(String deptName, int noOfDays,
			String toDate) {
		List<CouncilDeptYearWiseCntEntity> councilAgendaList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select t.DEPT_OR_YEAR, t.Received, t.Rejected, t.Approved,\r\n"
				+ "(t.Received - t.Approved) as Pending from (\r\n" + "select x.DP_DEPTDESC as DEPT_OR_YEAR,\r\n"
				+ "sum(0) as Rejected,\r\n" + "sum(Approved) as Approved,\r\n" + "count(1) as Received from (\r\n"
				+ "select d.DP_DEPTDESC,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (a.AGENDA_ID <> m.AGENDA_ID) THEN 'PENDING' END)\r\n"
				+ "IN ('PENDING') then 1 else 0 END)  PENDING,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (a.AGENDA_ID = m.AGENDA_ID) THEN 'Approved' END)\r\n"
				+ "IN ('Approved') then 1 else 0 END)  Approved \r\n" + "from tb_cmt_council_agenda_mast a,\r\n"
				+ "TB_CMT_COUNCIL_MEETING_MAST m,\r\n" + "tb_cmt_council_proposal_mast p,\r\n" + "tb_department d\r\n"
				+ "where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n" + "and a.AGENDA_ID = m.AGENDA_ID\r\n"
				+ "and p.AGENDA_ID = a.AGENDA_ID\r\n" + "and p.ISMOMPROPOSAL = 'N'\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and a.AGENDA_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append(
					"and a.AGENDA_DATE between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '" + toDate + "'\r\n");
		queryBuilder.append(") x group by x.DP_DEPTDESC ) as t ) as ta, (SELECT @row_number\\:=0) AS rn");
		councilAgendaList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilAgendaList;
	}

	public List<CouncilDeptWiseAgendaDataEntity> getDeptWiseAgendaGridData(String deptName, int noOfDays, String toDate,
			int orgId) {
		List<CouncilDeptWiseAgendaDataEntity> councilAgendaList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select  p.PROPOSAL_NUMBER, d.DP_DEPTDESC, p.PROPOSAL_DATE, a.AGENDA_DATE, com.CPD_DESC as COMMITTEE_TYPE, p.PROPOSAL_DETAILS,\r\n"
				+ "p.PROPOSAL_AMOUNT, \r\n" + "( CASE \r\n" + "	WHEN (a.AGENDA_ID = m.AGENDA_ID) THEN 'Approved'\r\n"
				+ "	WHEN (a.AGENDA_ID <> m.AGENDA_ID) THEN 'Pending' END\r\n" + "  ) as AGENDA_STATUS\r\n"
				+ ", p.purpose_remark, e.EMPNAME as CREATED_BY\r\n" + "from tb_cmt_council_agenda_mast a,\r\n"
				+ "TB_CMT_COUNCIL_MEETING_MAST m,\r\n" + "tb_cmt_council_proposal_mast p,\r\n"
				+ "tb_department d, employee e,\r\n" + "tb_comparam_det com\r\n"
				+ "where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n" + "and e.EMPID = p.CREATED_BY\r\n"
				+ "and a.AGENDA_ID = m.AGENDA_ID\r\n" + "and p.AGENDA_ID = a.AGENDA_ID\r\n"
				+ "and a.COMMITTEE_TYPE_ID = com.CPD_ID\r\n" + "and p.ISMOMPROPOSAL = 'N'\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and a.AGENDA_DATE between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append(
					"and a.AGENDA_DATE between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '" + toDate + "'\r\n");
		queryBuilder.append("-- and p.ORGID = " + orgId + " \r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		councilAgendaList = (List<CouncilDeptWiseAgendaDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptWiseAgendaDataEntity.class).getResultList();
		return councilAgendaList;
	}

	public List<CouncilProposalCntEntity> getCouncilMeetingCount() {
		List<CouncilProposalCntEntity> councilAgendaList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select RECEIVED, APPROVED, (RECEIVED - APPROVED) as Pending, 0 as Rejected from (\r\n" + "select\r\n"
				+ "(select count(*) from TB_CMT_COUNCIL_MEETING_MAST) as RECEIVED,\r\n"
				+ "(select count(*) from tb_cmt_council_resolution) as APPROVED\r\n" + ") as t\r\n"
				+ ") as ta, (SELECT @row_number\\:=0) AS rn");
		councilAgendaList = (List<CouncilProposalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilProposalCntEntity.class).getResultList();
		return councilAgendaList;
	}

	public List<CouncilDeptYearWiseCntEntity> getYearWiseCouncilMeetingCount() {
		List<CouncilDeptYearWiseCntEntity> councilMeetingCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select t.DEPT_OR_YEAR, t.Received, t.Rejected, t.Approved,\r\n"
				+ "(t.Received - t.Approved) as Pending from (\r\n"
				+ "select year(x.MEETING_DATE_TIME) as DEPT_OR_YEAR,\r\n" + "sum(0) as Rejected,\r\n"
				+ "sum(Approved) as Approved,\r\n" + "count(1) as Received from (\r\n"
				+ "select r.reso_id, m.MEETING_ID, m.MEETING_DATE_TIME,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (m.MEETING_ID <> r.MEETING_ID) THEN 'Pending' END)\r\n"
				+ "IN ('Pending') then 1 else 0 END) as Pending,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (m.MEETING_ID = r.MEETING_ID) THEN 'Approved' END)\r\n"
				+ "IN ('Approved') then 1 else 0 END) as Approved\r\n" + "from TB_CMT_COUNCIL_MEETING_MAST m\r\n"
				+ "left outer join\r\n" + "tb_cmt_council_resolution r\r\n" + "on m.MEETING_ID = r.MEETING_ID) as x\r\n"
				+ "group by year(x.MEETING_DATE_TIME) ) as t\r\n" + ") as ta, (SELECT @row_number\\:=0) AS rn");

		councilMeetingCntList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilMeetingCntList;
	}

	public List<CouncilDeptYearWiseCntEntity> getDeptAndDurationWiseCouncilMeetingCount(String deptName, int noOfDays,
			String toDate) {
		List<CouncilDeptYearWiseCntEntity> councilMeetingList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, ta.* from (\r\n"
				+ "select t.DEPT_OR_YEAR, t.Received, t.Rejected, t.Approved,\r\n"
				+ "(t.Received - t.Approved) as Pending from (\r\n" + "select x.DP_DEPTDESC as DEPT_OR_YEAR,\r\n"
				+ "sum(0) as Rejected,\r\n" + "sum(Approved) as Approved,\r\n" + "count(1) as Received from (\r\n"
				+ "select r.reso_id, m.MEETING_ID, m.MEETING_DATE_TIME, d.DP_DEPTDESC,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (m.MEETING_ID <> r.MEETING_ID) THEN 'Pending' END)\r\n"
				+ "IN ('Pending') then 1 else 0 END) as Pending,\r\n" + "(CASE WHEN\r\n"
				+ "(CASE WHEN (m.MEETING_ID = r.MEETING_ID) THEN 'Approved' END)\r\n"
				+ "IN ('Approved') then 1 else 0 END) as Approved\r\n" + "from TB_CMT_COUNCIL_MEETING_MAST m,\r\n"
				+ "tb_cmt_council_resolution r,\r\n" + "tb_cmt_council_agenda_mast a,\r\n"
				+ "tb_cmt_council_proposal_mast p,\r\n" + "tb_department d\r\n"
				+ "where m.MEETING_ID = r.MEETING_ID\r\n" + "and m.AGENDA_ID = a.AGENDA_ID\r\n"
				+ "and a.AGENDA_ID = p.AGENDA_ID\r\n" + "and p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n"
				+ "and p.ISMOMPROPOSAL = 'N'\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and m.MEETING_DATE_TIME between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append("and m.MEETING_DATE_TIME between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '"
					+ toDate + "'\r\n");
		queryBuilder.append(") x group by x.DP_DEPTDESC ) as t ) as ta, (SELECT @row_number\\:=0) AS rn");
		councilMeetingList = (List<CouncilDeptYearWiseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptYearWiseCntEntity.class).getResultList();
		return councilMeetingList;
	}

	public List<CouncilDeptWiseMeetingDataEntity> getDeptWiseMeetingGridData(String deptName, int noOfDays,
			String toDate, int orgId) {
		List<CouncilDeptWiseMeetingDataEntity> councilMeetingList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select  r.reso_id as RESOLUTION_ID, m.MEETING_ID, m.MEETING_NUMBER, m.MEETING_DATE_TIME, com.CPD_DESC as MEETING_TYPE, m.MEETING_PLACE,\r\n"
				+ "m.MEETING_INVITATION_MSG, d.DP_DEPTDESC, p.PROPOSAL_NUMBER,\r\n"
				+ "p.PROPOSAL_DATE, a.AGENDA_DATE, p.PROPOSAL_DETAILS, p.PROPOSAL_AMOUNT,\r\n" + "( CASE \r\n"
				+ "	WHEN (m.MEETING_ID = r.MEETING_ID) THEN 'Approved'\r\n"
				+ "	WHEN (m.MEETING_ID <> r.MEETING_ID) THEN 'Pending' END\r\n" + "  ) as MEETING_STATUS\r\n"
				+ ", p.purpose_remark, e.EMPNAME as CREATED_BY\r\n" + "from TB_CMT_COUNCIL_MEETING_MAST m,\r\n"
				+ "tb_cmt_council_resolution r,\r\n" + "tb_cmt_council_agenda_mast a,\r\n"
				+ "tb_cmt_council_proposal_mast p,\r\n" + "tb_department d, employee e,\r\n" + "tb_comparam_det com\r\n"
				+ "where m.MEETING_ID = r.MEETING_ID\r\n" + "and m.AGENDA_ID = a.AGENDA_ID\r\n"
				+ "and a.AGENDA_ID = p.AGENDA_ID\r\n" + "and p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n"
				+ "and m.MEETING_TYPE_ID = com.CPD_ID\r\n" + "and e.EMPID = m.CREATED_BY\r\n"
				+ "and p.ISMOMPROPOSAL = 'N'\r\n");
		if (deptName != null)
			queryBuilder.append("and d.DP_DEPTDESC = '" + deptName + "'\r\n");
		if (noOfDays != 0)
			queryBuilder.append("and m.MEETING_DATE_TIME between DATE_SUB('" + toDate + "', INTERVAL " + noOfDays
					+ " day) and '" + toDate + "'\r\n");
		else
			queryBuilder.append("and m.MEETING_DATE_TIME between DATE_SUB('" + toDate + "', INTERVAL 365 day) and '"
					+ toDate + "'\r\n");
		queryBuilder.append("-- and m.ORGID = " + orgId + " \r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		councilMeetingList = (List<CouncilDeptWiseMeetingDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptWiseMeetingDataEntity.class).getResultList();
		return councilMeetingList;
	}

	public FinancialYear getFinancialYear() {
		List<FinancialYear> financialYears = null;
		StringBuilder queryBuilder = new StringBuilder();
		FinancialYear financialYear = new FinancialYear();
		queryBuilder.append(
				"select * from tb_financialyear where FA_YEARID = (select max(FA_YEARID) from tb_financialyear)");
		financialYears = (List<FinancialYear>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYear.class).getResultList();
		if (financialYears != null && !financialYears.isEmpty()) {
			financialYear = financialYears.get(0);
		}
		return financialYear;
	}
	

	/*
	 * SKDCL common APIs
	 */

	public SKDCLDashboardAllCountsEntity getSKDCLDashboardAllCounts() {
		SKDCLDashboardAllCountsEntity dashboardAllCountsEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select count(*) from tb_department where status = 'A') as deptCount,\r\n"
				+ "(select count(*) from tb_services_mst where SM_SERV_ACTIVE = 39) as serviceCount,\r\n");
		queryBuilder.append("(select (crCnt + propCnt + wtCnt + bndCnt + mrmCnt + licCnt) from (\r\n" + "select\r\n"
				+ "(select count(*) from tb_care_request) as crCnt, \r\n"
				+ "(select count(*) from tb_as_assesment_mast where MN_ASS_aut_status = 'Y') as propCnt, \r\n"
				+ "(select count(*) as cnt from tb_csmr_info where PC_FLG = 'Y') as wtCnt,\r\n"
				+ "(select count(*) from tb_bd_cfc_interface) as bndCnt,\r\n"
				+ "(select count(*) from tb_mrm_marriage) as mrmCnt,\r\n"
				+ "(select count(*) FROM tb_ml_trade_mast) as licCnt\r\n"
				+ "from dual) as cnts) as applReceivedCount,\r\n");
		queryBuilder.append("(select (crCnt + propCnt + mrmCnt + bndCnt + licCnt) from (\r\n" + "select \r\n"
				+ "(select COALESCE(sum(Closed), 0) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END)  Closed\r\n" + "from tb_workflow_request b,\r\n"
				+ "tb_care_request c\r\n" + "where c.apm_application_id=b.apm_application_id) x) as crCnt,\r\n"
				+ "(select COALESCE(sum(Closed), 0) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END)  Closed\r\n" + "from tb_workflow_request b,\r\n"
				+ "tb_as_assesment_mast a\r\n" + "where a.apm_application_id=b.apm_application_id) x) as propCnt,\r\n"
				+ "(select COALESCE(sum(Closed), 0) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END) Closed\r\n" + "from tb_mrm_marriage m,\r\n"
				+ "tb_workflow_request b\r\n" + "where m.APPLICATION_ID = b.APM_APPLICATION_ID) x) as mrmCnt,\r\n"
				+ "(select COALESCE(sum(Closed), 0) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END) Closed\r\n" + "from tb_bd_cfc_interface c,\r\n"
				+ "tb_workflow_request b\r\n" + "where c.apm_application_id=b.apm_application_id) x) as bndCnt,\r\n"
				+ "(select COALESCE(sum(Closed), 0) from (\r\n" + "select (CASE WHEN\r\n"
				+ "(CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED' END)\r\n"
				+ "IN ('CLOSED') then 1 else 0 END) Closed\r\n" + "from tb_ml_trade_mast c,\r\n"
				+ "tb_workflow_request b\r\n" + "where c.apm_application_id=b.apm_application_id) x) as licCnt\r\n"
				+ "from dual) as cnts) as applDisposedCount,\r\n");
		queryBuilder.append("(select count(*) from tb_care_request) as grievancesCount,\r\n"
				+ "(select count(*) from tb_as_assesment_mast where MN_ASS_aut_status = 'Y') as propertyCount,\r\n"
				+ "(select count(*) as cnt from tb_csmr_info where PC_FLG = 'Y') as waterCount, (select count(*) from tb_bd_cfc_interface) as birthDeathCount,\r\n"
				+ "(select count(*) from tb_mrm_marriage) as marriageRegistCount, (select count(*) FROM tb_ml_trade_mast) as tradeLicenCount,\r\n"
				+ "(select 0) as financialAccountCount\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		dashboardAllCountsEntity = (SKDCLDashboardAllCountsEntity) entityManager
				.createNativeQuery(queryBuilder.toString(), SKDCLDashboardAllCountsEntity.class).getSingleResult();
		return dashboardAllCountsEntity;
	}

	/*
	 * HRMS Module APIs
	 */

	public List<HRMSCategoryWiseEmpCntEntity> getStatusWiseEmployeeCount() {
		List<HRMSCategoryWiseEmpCntEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select Old_Emp_Status as CATEGORY, count(Old_Emp_Status) as CNT from tblemployeemaster where Pay_Processing_Status='Active' and Transfer_Status='Yes' group by Old_Emp_Status\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSCategoryWiseEmpCntEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSCategoryWiseEmpCntEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSCategoryWiseEmpCntEntity> getCategoryWiseEmployeeCount() {
		List<HRMSCategoryWiseEmpCntEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select Employee_Category as CATEGORY, count(Employee_Category) as CNT from tblemployeemaster where employee_category is not null group by Employee_Category\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSCategoryWiseEmpCntEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSCategoryWiseEmpCntEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSCategoryWiseEmpCntEntity> getDepartmentWiseEmployeeCount() {
		List<HRMSCategoryWiseEmpCntEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select Department as CATEGORY, count(Employee_id) as CNT from tblemployeemaster where Pay_Processing_Status='Active' and Transfer_Status='Yes' group by Department\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSCategoryWiseEmpCntEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSCategoryWiseEmpCntEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSGenderRatioEntity> getGenderWiseEmployeeCount() {
		List<HRMSGenderRatioEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select gender, count(gender) as CNT,(cast(count(gender) as decimal) / (select count(gender) from tblemployeemaster)) * 100 as Percent from tblemployeemaster  \r\n"
				+ "where gender is not null group by gender\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSGenderRatioEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSGenderRatioEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSSalaryBreakdownEntity> getSalaryRangeWiseEmployeeCount() {
		List<HRMSSalaryBreakdownEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "SELECT CASE\r\n"
				+ "         WHEN Gross_pay <= 10000 THEN '0-10000'\r\n"
				+ "         WHEN Gross_pay <= 20000 THEN '10001-20000'\r\n"
				+ "         WHEN Gross_pay <= 30000 THEN '20001-30000'\r\n"
				+ "         WHEN Gross_pay <= 40000 THEN '30001-40000'\r\n"
				+ "         WHEN Gross_pay <= 50000 THEN '40001-50000'\r\n"
				+ "         WHEN Gross_pay <= 60000 THEN '50001-60000'\r\n"
				+ "         WHEN Gross_pay <= 70000 THEN '60001-70000'\r\n"
				+ "         WHEN Gross_pay <= 80000 THEN '70001-80000'\r\n"
				+ "         WHEN Gross_pay <= 90000 THEN '80001-90000'\r\n"
				+ "         WHEN Gross_pay <= 100000 THEN '90001-100000'\r\n" + "         ELSE '>100000'\r\n"
				+ "       END AS Gross_pay_range,\r\n" + "       COUNT(*) AS no_of_employees,department\r\n"
				+ "FROM tblmonthlyemployeepay \r\n" + "where paymonth_name='May' \r\n"
				+ "and WF_Status = 'Approved'\r\n" + "GROUP BY department, CASE\r\n"
				+ "         WHEN Gross_pay <= 10000 THEN '0-10000'\r\n"
				+ "         WHEN Gross_pay <= 20000 THEN '10001-20000'\r\n"
				+ "         WHEN Gross_pay <= 30000 THEN '20001-30000'\r\n"
				+ "         WHEN Gross_pay <= 40000 THEN '30001-40000'\r\n"
				+ "         WHEN Gross_pay <= 50000 THEN '40001-50000'\r\n"
				+ "         WHEN Gross_pay <= 60000 THEN '50001-60000'\r\n"
				+ "         WHEN Gross_pay <= 70000 THEN '60001-70000'\r\n"
				+ "         WHEN Gross_pay <= 80000 THEN '70001-80000'\r\n"
				+ "         WHEN Gross_pay <= 90000 THEN '80001-90000'\r\n"
				+ "         WHEN Gross_pay <= 100000 THEN '90001-100000'\r\n" + "         ELSE '>100000'\r\n"
				+ "         END order by Gross_pay_range\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSSalaryBreakdownEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSSalaryBreakdownEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSEmployeeGridDataEntity> getSalaryRangeDeptWiseEmployeeData(String dept, String salaryRange) {
		List<HRMSEmployeeGridDataEntity> empData = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder = new StringBuilder("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select em.Employee_ID, em.Employee_Name, em.Department, em.Age, em.Date_of_Birth, em.Mobile_No, em.Email_ID, em.Date_of_Joining, em.Employee_Status\r\n"
				+ "from tblemployeemaster em, tblmonthlyemployeepay ep\r\n"
				+ "where em.Employee_ID = ep.Employee_ID\r\n" + "and ep.paymonth_name='May'\r\n"
				+ "and ep.WF_Status = 'Approved'\r\n");
		if (salaryRange != null) {
			String[] salRange = salaryRange.split("-");
			if (salRange.length != 0) {
				queryBuilder.append(
						"and ep.Gross_Pay >= '" + salRange[0] + "' and ep.Gross_Pay <= '" + salRange[1] + "'\r\n");
			}
		}
		if (dept != null) {
			queryBuilder.append("and ep.Department = '" + dept + "'\r\n");
		}
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			empData = (List<HRMSEmployeeGridDataEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSEmployeeGridDataEntity.class).getResultList();
			return empData;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSCategoryWiseEmpCntEntity> getJoiningYearWiseEmployeeCount() {
		List<HRMSCategoryWiseEmpCntEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select extract(year from e.DATE_OF_JOINING) as CATEGORY, count(e.EmployeeMaster_Id) as CNT from tblemployeemaster e\r\n"
				+ "where extract(year from e.DATE_OF_JOINING) is not null\r\n" + "group by CATEGORY\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<HRMSCategoryWiseEmpCntEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSCategoryWiseEmpCntEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSEmployeeGridDataEntity> getEmployeeData(String criteria, String criteriaType) {
		List<HRMSEmployeeGridDataEntity> empData = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select Employee_ID, Employee_Name, Department, Age, Date_of_Birth, Mobile_No, Email_ID, Date_of_Joining, Employee_Status from tblemployeemaster\r\n");
		if (criteriaType.equals("DEPARTMENT")) {
			queryBuilder.append("where Pay_Processing_Status='Active' and Transfer_Status='Yes'\r\n");
			if (criteria != null) {
				queryBuilder.append("and Department = '" + criteria + "'\r\n");
			}
		}
		if (criteriaType.equals("CATEGORY") && criteria != null) {
			queryBuilder.append("where Employee_Category = '" + criteria + "'\r\n");
		}
		if (criteriaType.equals("STATUS")) {
			queryBuilder.append("where Pay_Processing_Status='Active' and Transfer_Status='Yes'\r\n");
			if (criteria != null) {
				queryBuilder.append("and Old_Emp_Status = '" + criteria + "'\r\n");
			}
		}
		if (criteriaType.equals("GENDER") && criteria != null) {
			queryBuilder.append("where gender = '" + criteria + "'\r\n");
		}
		if (criteriaType.equals("JOINING_YEAR") && criteria != null) {
			queryBuilder.append("where year(Date_of_Joining) = '" + criteria + "'");
		}

		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			empData = (List<HRMSEmployeeGridDataEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSEmployeeGridDataEntity.class).getResultList();
			return empData;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<HRMSRecruitmentPostsEntity> getVacantPostInfo() {
		List<HRMSRecruitmentPostsEntity> info = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select Designation_Name as Name_Of_Post,cadre_name as Cadre_Name, total_no_of_quotas as Sanctioned,\r\n"
				+ "(select count(employee_id) from tblemployeemaster a where a.Designation=b.designation_ref and a.ulb_code=b.ulb_code\r\n"
				+ " and payhistory_fromdate_max<='2017-12-18') as Filled,(total_no_of_quotas-(select count(employee_id) from tblemployeemaster a where a.Designation=b.designation_ref and a.ulb_code=b.ulb_code\r\n"
				+ " and payhistory_fromdate_max<='2017-12-18')) as Vacant,\r\n"
				+ " (select  sum(total_no_of_vacancy)  from tbljobvacancydetails c where c.designation=b.designation_ref and c.cadre=b.cadre_ref and Jvstatus='Processed' and c.ulb_code=b.ulb_code and c.indent_date<='2019-12-18') as Recruitment_Total_Posts,\r\n"
				+ " (select count(jobapplicationform_id) from tbljobapplicationform d where d.Designation_value=b.designation_name and d.cader_value=b.cadre_name and d.ulb_code=b.ulb_code and d.offer_accepted_yes_no='Not Accepted' and d.application_date<='2019-12-18'\r\n"
				+ "  ) as Filled2,(((select  sum(total_no_of_vacancy)  from tbljobvacancydetails c where c.designation=b.designation_ref and c.cadre=b.cadre_ref and Jvstatus='Processed' and c.ulb_code=b.ulb_code and c.indent_date<='2019-12-18'))-\r\n"
				+ "  ((select count(jobapplicationform_id) from tbljobapplicationform d where d.Designation_value=b.designation_name and d.cader_value=b.cadre_name and d.ulb_code=b.ulb_code and d.offer_accepted_yes_no='Not Accepted' and d.application_date<='2019-12-18'\r\n"
				+ "  ) )) as Vacant2\r\n" + "from tblsanctionedpost b where Active_Status='Active'\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			info = (List<HRMSRecruitmentPostsEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), HRMSRecruitmentPostsEntity.class).getResultList();
			return info;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	/*
	 * Store Module APIs
	 */

	public List<StoreItemCountEntity> getItemGroupWiseInventoryValue() {
		List<StoreItemCountEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select b.item_group_name as Name, sum(a.av_quantity) as CNT from tblbinlocation a join tblitemmaster b where a.item_description=b.Item_Description and a.ULB_Code = b.ULB_Code group by b.item_group_name\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<StoreItemCountEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), StoreItemCountEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<StoreItemWiseInventoryCntEntity> getItemWiseInventoryValue() {
		List<StoreItemWiseInventoryCntEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select b.item_group_name, a.item_description, sum(a.av_quantity) as CNT from tblbinlocation a join tblitemmaster b where a.item_description=b.Item_Description \r\n"
				+ "and a.ULB_Code = b.ULB_Code\r\n" + "group by b.item_group_name,a.item_description\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<StoreItemWiseInventoryCntEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), StoreItemWiseInventoryCntEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<StoreItemCountEntity> getItemGroupWiseItemCount() {
		List<StoreItemCountEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select item_group_name as Name, count(item_description) as CNT from tblitemmaster where Item_Status ='Active' and Item_Group_Name is not null group by item_group_name\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<StoreItemCountEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), StoreItemCountEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<StoreItemCountEntity> getTopNMovingItemCounts(int limit) {
		List<StoreItemCountEntity> counts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select item_description as Name, count(item_description) as CNT from tblbinlocation where item_description is not null group by item_description order by CNT desc limit "
				+ limit + "\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			counts = (List<StoreItemCountEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), StoreItemCountEntity.class).getResultList();
			return counts;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}

	public List<StoreItemDataEntity> getItemDataForGroup(String itemGroup) {
		List<StoreItemDataEntity> itemData = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select b.Item_Code, b.item_description, b.Item_Status, b.Item_Group_Name, b.Item_belongs_to, b.Item_Valuation_Method, b.Purpose,\r\n"
				+ "a.av_quantity as Available_Quantity\r\n"
				+ "from tblbinlocation a join tblitemmaster b where a.item_description=b.Item_Description \r\n"
				+ "and a.ULB_Code = b.ULB_Code\r\n");
		if (itemGroup != null) {
			queryBuilder.append("and b.Item_Group_Name = '" + itemGroup + "'\r\n");
		}
		queryBuilder.append("order by b.item_group_name" + ") as t, (SELECT @row_number\\:=0) AS rn");
		try {
			itemData = (List<StoreItemDataEntity>) entityManager
					.createNativeQuery(queryBuilder.toString(), StoreItemDataEntity.class).getResultList();
			return itemData;
		} catch (Exception e) {
			log.error("Exception occured while getting dashboard data :: ", e);
		}
		return null;
	}
 
	// #120768
	@SuppressWarnings("unchecked")
	public FinancialYear getCurrentFinancialYear() {
		List<FinancialYear> financialYears = null;
		StringBuilder queryBuilder = new StringBuilder();
		FinancialYear financialYear = new FinancialYear();
		queryBuilder.append(
				"select * from tb_financialyear where FA_YEARID = (select max(FA_YEARID) from tb_financialyear where now() between FA_FROMDATE and FA_TODATE)");
		financialYears = (List<FinancialYear>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialYear.class).getResultList();
		if (financialYears != null && !financialYears.isEmpty()) {
			financialYear = financialYears.get(0);
		}
		return financialYear;
	}

	@SuppressWarnings("unchecked")
	public List<CouncilDeptWiseProposalCntEntity> getOrgIdWiseCouncilProposalCount(Long orgId, String fromDate,
			String toDate) {
		List<CouncilDeptWiseProposalCntEntity> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.DEPT_NAME, t.PROPOSAL_COUNT as PROPOSAL_COUNT from (\r\n"
						+ "select x.DP_DEPTDESC as DEPT_NAME," + "count(1) as PROPOSAL_COUNT from ("
						+ "select d.DP_DEPTDESC, p.PROPOSAL_DATE from tb_cmt_council_proposal_mast p, tb_department d "
						+ "where p.PROPOSAL_DEP_ID = d.DP_DEPTID\r\n");
		if (orgId != null)
			queryBuilder.append("and p.orgId ='" + orgId + "'\r\n");
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate))
			queryBuilder.append("and p.PROPOSAL_DATE between '" + fromDate + "' and '" + toDate + "'\r\n");
		queryBuilder.append(") x group by x.DP_DEPTDESC ) as t, (SELECT @row_number\\:=0) AS rn");
		councilProposalList = (List<CouncilDeptWiseProposalCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilDeptWiseProposalCntEntity.class).getResultList();
		return councilProposalList;
	}
	@SuppressWarnings("unchecked")
	public List<RTIApplicationByApplicantType> getRTIApplicationByApplicantType() {
		List<RTIApplicationByApplicantType> rtiAppltypeList=null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + 
				"				 select        (Select cpd_desc from tb_comparam_det where cpd_id=RTI_APPLICANT_TYPYE) as applicantType , sum(Closed) as closed_query,\r\n" + 
				"				             sum(Pending) as Pending_query,              sum(Rejected) as expired_query,\r\n" + 
				"				             sum(Hold) as Hold,              count(1) as recieved_query from (\r\n" + 
				"				 select RTI_APPLICANT_TYPYE, (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('CLOSED') then 1 else 0 END)  Closed,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('PENDING') then 1 else 0 END)  Pending,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('REJECTED') then 1 else 0 END)  Rejected,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('HOLD') then 1 else 0 END)  Hold \r\n" + 
				"				              from tb_workflow_request b,    tb_rti_application c\r\n" + 
				"				              where c.apm_application_id=b.apm_application_id) x \r\n" + 
				"				              group by x.RTI_APPLICANT_TYPYE) as t, (SELECT @row_number\\:=0) AS rn\r\n" + 
				"                              \r\n" + 
				"                        \r\n" + 
				"                              ");
		rtiAppltypeList = (List<RTIApplicationByApplicantType>) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationByApplicantType.class).getResultList();
		return rtiAppltypeList;
		
	}
	@SuppressWarnings("unchecked")
	public List<RTIApplicationyBplFlagEntity> getRTIApplicationDetailByBplFactorWise() {
		List<RTIApplicationyBplFlagEntity> rtiBplltypeList=null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + 
				"				 select      CASE WHEN RTI_BPLFLAG='N' then 'NO'\r\n" + 
				"                 When RTI_BPLFLAG='Y' then 'YES' END as BPL_Flag , sum(Closed) as closed_query,\r\n" + 
				"				             sum(Pending) as Pending_query,              sum(Rejected) as expired_query,\r\n" + 
				"				             sum(Hold) as Hold,              count(1) as recieved_query from (\r\n" + 
				"				 select  RTI_BPLFLAG, (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('CLOSED') then 1 else 0 END)  Closed,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('PENDING') then 1 else 0 END)  Pending,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('REJECTED') then 1 else 0 END)  Rejected,              (CASE WHEN\r\n" + 
				"				              (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"				                    WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				"				             IN ('HOLD') then 1 else 0 END)  Hold \r\n" + 
				"				              from tb_workflow_request b,    tb_rti_application c\r\n" + 
				"				              where c.apm_application_id=b.apm_application_id) x \r\n" + 
				"				              group by x.RTI_BPLFLAG ) as t, (SELECT @row_number\\:=0) AS rn\r\n" + 
				"                              \r\n" + 
				"                        \r\n" + 
				"                              ");
		rtiBplltypeList = (List<RTIApplicationyBplFlagEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIApplicationyBplFlagEntity.class).getResultList();
		return rtiBplltypeList;
	}

	@SuppressWarnings("unchecked")
	public List<CouncilMeetingCountYrwise> getYearWiseCouncilMeetingCountByOrgId(Long orgId) {
		List<CouncilMeetingCountYrwise> councilMeetingList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, YEAR(MEETING_DATE_TIME) Years,Count(MEETING_ID) TOTOL_COUNT\r\n" + 
				"From TB_CMT_COUNCIL_MEETING_MAST,(SELECT @row_number\\:=0) AS rn\r\n" + 
				"Where orgId='" + orgId + "' Group By Years\r\n");
		councilMeetingList = (List<CouncilMeetingCountYrwise>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilMeetingCountYrwise.class).getResultList();
		return councilMeetingList;
	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CouncilMeetingCountYrwise> getYearWiseAgendaCountByOrgId(Long orgId) {
		List<CouncilMeetingCountYrwise> councilAgendaList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, YEAR(AGENDA_DATE) Years,Count(AGENDA_ID) Totol_Count \r\n" + 
				"From TB_CMT_COUNCIL_AGENDA_MAST,(SELECT @row_number\\:=0) AS rn\r\n" + 
				"Where orgId='" + orgId + "' Group By Years");
		councilAgendaList = (List<CouncilMeetingCountYrwise>) entityManager
				.createNativeQuery(queryBuilder.toString(), CouncilMeetingCountYrwise.class).getResultList();
		return councilAgendaList;
	
	}

	@SuppressWarnings("unchecked")
	public List<FinancialAndNonFinacialProposalCount> getFinancialAndNonFiancialProposalCountByOrgId(Long orgId) {
		List<FinancialAndNonFinacialProposalCount> councilProposalList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, (CASE WHEN PROPOSAL_TYPE='F' Then 'Financial' ELSE 'Non_Financial' END) ProposalType,\r\n" + 
				"Count(PROPOSAL_ID) ProposalCount\r\n" + 
				"From TB_CMT_COUNCIL_PROPOSAL_MAST,(SELECT @row_number\\:=0) AS rn\r\n" + 
				"Where orgId='" + orgId + "' Group By ProposalType");
		councilProposalList = (List<FinancialAndNonFinacialProposalCount>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialAndNonFinacialProposalCount.class).getResultList();
		return councilProposalList;
	}

	@SuppressWarnings("unchecked")
	public List<RtiApplicationDtoByCondEntity> getRtiDrillDownDataByBplType(String bplType) {
		List<RtiApplicationDtoByCondEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (select c.APM_APPLICATION_ID,c.RTI_NO, CASE WHEN RTI_BPLFLAG='N' then 'NO'\r\n" + 
				"						                  When RTI_BPLFLAG='Y' then 'YES' END as BPL_Flag , (select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY,\r\n" + 
				"						 (Select CPD_DESC from tb_comparam_det where CPD_ID=c.INWARD_TYPE) as INWORD_TYPE, c.RTI_SUBJECT as RTI_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST, \r\n" + 
				"						 				 			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n" + 
				"						 				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"						 				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"						 				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) as Status \r\n" + 
				"						 								from tb_workflow_request b,tb_rti_application c,tb_organisation y  where c.orgid=y.ORGID and \r\n" + 
				"						 				             c.apm_application_id=b.apm_application_id ) as t, (SELECT @row_number\\:=0) AS rn\r\n" + 
				"                                                     where  t.BPL_Flag like "+"'"+bplType+"'");
		rtiEntList = (List<RtiApplicationDtoByCondEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RtiApplicationDtoByCondEntity.class).getResultList();
		return rtiEntList;
	}

	public List<RtiApplicationDtoByCondEntity> getRtiDrillDownDataByApplicantType(String applicantType) {
		List<RtiApplicationDtoByCondEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @row_number\\:=@row_number+1 AS num, t.* from (select c.APM_APPLICATION_ID,c.RTI_NO, CASE WHEN RTI_BPLFLAG='N' then 'NO'\r\n"
						+ "                 When RTI_BPLFLAG='Y' then 'YES' END as BPL_Flag , (select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY,\r\n"
						+ "(Select CPD_DESC from tb_comparam_det where CPD_ID=c.INWARD_TYPE) as INWORD_TYPE, c.RTI_DESC as RTI_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST, \r\n"
						+ "				 			 (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION = 'REJECTED'))  THEN 'REJECTED'\r\n"
						+ "				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED')  AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n"
						+ "				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n"
						+ "				 				   WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END) as Status \r\n"
						+ "								from tb_workflow_request b,tb_rti_application c,tb_organisation y  where c.orgid=y.ORGID and \r\n"
						+ "				             c.apm_application_id=b.apm_application_id and (Select CPD_DESC from tb_comparam_det where CPD_ID=c.RTI_APPLICANT_TYPYE) like '%"
						+ applicantType + "%') as t, (SELECT @row_number\\:=0) AS rn;");
		rtiEntList = (List<RtiApplicationDtoByCondEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RtiApplicationDtoByCondEntity.class).getResultList();
		return rtiEntList;
	}


	public List<RtiApplicationDtoByCondEntity> getRSAPendingClosedDrillDownData() {
		List<RtiApplicationDtoByCondEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* \r\n"
				+ "from (SELECT  c.APM_APPLICATION_ID,c.RTI_NO, CASE WHEN RTI_BPLFLAG='N' then 'NO'\r\n"
				+ "                 When RTI_BPLFLAG='Y' then 'YES' END as BPL_Flag , \r\n"
				+ "                 (select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY,\r\n"
				+ "(Select CPD_DESC from tb_comparam_det where CPD_ID=c.INWARD_TYPE) as INWORD_TYPE, \r\n"
				+ "c.RTI_DESC as RTI_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST,  RTI_SCND_APEAL_STATUS as status \r\n"
				+ "				 			 FROM tb_rti_application c\r\n"
				+ " where RTI_SCND_APEAL_STATUS in ('Completed','Pending')  ) as t, (SELECT @row_number\\:=0) AS rn");
		rtiEntList = (List<RtiApplicationDtoByCondEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RtiApplicationDtoByCondEntity.class).getResultList();
		return rtiEntList;
	}

	public List<RtiApplicationDtoByCondEntity> getRFAOpenClosedDrillDownData() {
		List<RtiApplicationDtoByCondEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* \r\n"
				+ "from (SELECT  c.APM_APPLICATION_ID,c.RTI_NO, CASE WHEN RTI_BPLFLAG='N' then 'NO'\r\n"
				+ "                 When RTI_BPLFLAG='Y' then 'YES' END as BPL_Flag , \r\n"
				+ "                 (select EMPNAME from employee where EMPID = c.UPDATED_BY) as CREATED_BY,\r\n"
				+ "(Select CPD_DESC from tb_comparam_det where CPD_ID=c.INWARD_TYPE) as INWORD_TYPE, \r\n"
				+ "c.RTI_DESC as RTI_DESC, c.APM_APPLICATION_DATE as DATE_OF_REQUEST,  tom.OBJ_STATUS as Status\r\n"
				+ "				 			 FROM tb_objection_mast tom,tb_rti_application c\r\n"
				+ " where tom.OBJ_STATUS in ('Completed','Pending') \r\n"
				+ " and tom.APM_APPLICATION_ID=c.APM_APPLICATION_ID ) as t, (SELECT @row_number\\:=0) AS rn;");
		rtiEntList = (List<RtiApplicationDtoByCondEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RtiApplicationDtoByCondEntity.class).getResultList();
		return rtiEntList;
	}

	

	public List<RTIDasBoardAppealStatusEntity> getIndvidualRtiRfaOpenClosedData() {
		List<RTIDasBoardAppealStatusEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, \r\n"
				+ "Sum(Coalesce(Pending,0)) Pending,\r\n" + "Sum(Coalesce(Closed,0)) Closed\r\n"
				+ "from \r\n" + "(SELECT Case when tom.OBJ_STATUS='Pending' then count(1) end as Pending,\r\n"
				+ "Case when tom.OBJ_STATUS='Completed' then count(1) end as Closed\r\n"
				+ "FROM tb_objection_mast tom,tb_rti_application tra where \r\n"
				+ "tom.OBJ_STATUS in ('Pending','Completed') and\r\n"
				+ " tom.APM_APPLICATION_ID=tra.APM_APPLICATION_ID group by tom.OBJ_STATUS) as t, (SELECT @row_number\\:=0) AS rn \r\n"
				+ " ");
		rtiEntList = (List<RTIDasBoardAppealStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIDasBoardAppealStatusEntity.class).getResultList();
		return rtiEntList;
	}

	public List<RTIDasBoardAppealStatusEntity> getIndvidualRtiRSAOpenClosedData() {
		List<RTIDasBoardAppealStatusEntity> rtiEntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, \r\n" + 
				"SUM(Coalesce(Pending,0)) Pending,\r\n" + 
				"SUM(Coalesce(Completed,0)) Closed\r\n" + 
				"				 from (SELECT RTI_SCND_APEAL_STATUS as status1, \r\n" + 
				"                 Case when RTI_SCND_APEAL_STATUS='Pending' then count(1) end as Pending,\r\n" + 
				"                 Case when RTI_SCND_APEAL_STATUS='Completed' then count(1) end as Completed\r\n" + 
				"                  \r\n" + 
				"                 FROM tb_rti_application tra\r\n" + 
				"				  where RTI_SCND_APEAL_STATUS in ('Completed','Pending') group by RTI_SCND_APEAL_STATUS) as t, (SELECT @row_number\\:=0) AS rn");
		rtiEntList = (List<RTIDasBoardAppealStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), RTIDasBoardAppealStatusEntity.class).getResultList();
		return rtiEntList;
	}

	
	public List<AdvocateMastRegEntity> getAdvocateMstRegCount() {
		List<AdvocateMastRegEntity> advocateMastRegEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT  p.* from (\r\n" + 
				"Select sum(t.PENDING) PENDING , sum(t.REJECTED) REJECTED , sum(t.CLOSED) CLOSED,\r\n" + 
				"sum(Total) NoOfRequests \r\n" + 
				"from (select count(1) Total,\r\n" + 
				"Case when Status='PENDING' then count(1) end PENDING,\r\n" + 
				"Case when Status='REJECTED' then count(1) end REJECTED,\r\n" + 
				"Case when Status='CLOSED' then count(1) end CLOSED\r\n" + 
				"from (select c.apm_application_id,\r\n" + 
				"( (CASE WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n" + 
				"WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (b.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				" WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"WHEN ((REPLACE(b.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (b.LAST_DECISION <> 'HOLD')) THEN 'PENDING' END)\r\n" + 
				") Status from tb_workflow_request b, tb_lgl_advocate_mas c\r\n" + 
				"where c.apm_application_id=b.apm_application_id and c.apm_application_id is not null and c.orgid=b.ORGID) x group by Status\r\n" + 
				") as t)p ");
		advocateMastRegEntity = (List<AdvocateMastRegEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AdvocateMastRegEntity.class).getResultList();
		return advocateMastRegEntity;
	}

}
