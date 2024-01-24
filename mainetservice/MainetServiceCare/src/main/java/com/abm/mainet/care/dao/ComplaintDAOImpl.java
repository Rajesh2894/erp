package com.abm.mainet.care.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.care.domain.ComplaintRegister;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.report.ComplaintDTO;
import com.abm.mainet.care.dto.report.ComplaintGradeSummary;
import com.abm.mainet.care.dto.report.SummaryField;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;

@Repository
public class ComplaintDAOImpl extends AbstractDAO<Long> implements IComplaintDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ComplaintDAOImpl.class);
	
	@Autowired
	private ApplicationSession applicationSession;

	/**
	 * 
	 */
	@Override
	public Set<ComplaintDTO> getComplaints(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId,String referenceMode,String empId) {

		Set<ComplaintDTO> comlaints = new LinkedHashSet<>();
		StringBuilder builder = new StringBuilder();
		builder.append(
				"SELECT cr.complaintId,cr.applicationId,cr.deptNameEng,cr.deptNameReg,cr.compNameEng,cr.compNameReg,cr.dateOfRequest,cr.lastDateOfAction,cr.status,cr.lastDecision,cr.applicationSlaDurationInMS,cr.apmName,cr.apaEmail,cr.apaMobilno,cr.careWardNo,cr.careWardNoEng,cr.careWardNoReg,cr.careWardNo1,cr.careWardNoEng1,cr.careWardNoReg1,cr.careWardNo2,cr.careWardNoEng2,cr.careWardNoReg2,cr.careWardNo3,cr.careWardNoEng3,cr.careWardNoReg3,cr.careWardNo4,cr.careWardNoEng4,cr.careWardNoReg4,cr.locId,cr.locNameEng,cr.locNameReg,cr.pincode,cr.numberOfDay,cr.stateNameEng,cr.districtNameEng,cr.complaintDesc,cr.modeType,cr.modeTypeReg,cr.empId,cr.modeTypeCode,cr.apaAreaName FROM ComplaintRegister cr where ");

		if (orgId != null)
			builder.append(" cr.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			builder.append("and cr.deptId=:deptId ");
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and cr.compId=:compalintTypeId ");
		
		if (referenceMode != null && !referenceMode.equals("-1"))
			builder.append("and cr.modeType=:referenceMode ");
		if (fromDate != null && toDate != null)
			builder.append("and cr.dateOfRequest between :fromDate and :toDate ");
		if (status != null && !status.equals("-1")) {
			if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						"and cr.lastDecision in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
			else
				builder.append("and cr.lastDecision=:status ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and cr.careWardNo=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and cr.careWardNo1=:codIdOperLevel2 ");

		/*if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and cr.codIdOperLevel3=:codIdOperLevel3 ");
		
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and cr.codIdOperLevel4=:codIdOperLevel4 ");
		
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and cr.codIdOperLevel5=:codIdOperLevel5");*/
		
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and cr.careWardNo2=:codIdOperLevel3 ");
		
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and cr.careWardNo3=:codIdOperLevel4 ");
		
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and cr.careWardNo4=:codIdOperLevel5 ");
		// for sorting according to department
		//builder.append("order by cr.deptId desc");
		/*D#127277*/
		builder.append("order by cr.dateOfRequest desc");
		
		Query query = this.createQuery(builder.toString());

		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		try {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(referenceMode), "RFM",orgId );
			if (lookUp != null)
				query.setParameter("referenceMode",lookUp.getLookUpDesc() );
            
            }catch(Exception e) {
            	
            }
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (status != null && !status.equals("-1")) {
			if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", status);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);
		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		objList.forEach(obj -> {
		    
			ComplaintDTO complaint = new ComplaintDTO();
			long slaDuration = 0l;
			long duration = 0l;
			long slaDurationDiff = 0l;
			String slaStatus = "";
			
			complaint.setComplaintId((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
			complaint.setApplicationId((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
			if (langId == MainetConstants.ENGLISH) {
				complaint.setDepartmentName((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
			} else {
				complaint.setDepartmentName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[5] != null) ? obj[5].toString() : MainetConstants.BLANK);
			}
			complaint.setDepartmentRegName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
			Date dateOfRequest = (obj[6] != null) ? (Date) obj[6] : null;
			Date lastDateOfAction = (obj[7] != null) ? (Date) obj[7] : null;
			complaint.setDateOfRequest(
					(dateOfRequest != null) ? Utility.dateToString(dateOfRequest) : MainetConstants.BLANK);
			complaint.setLastDateOfAction(
					(lastDateOfAction != null) ? Utility.dateToString(lastDateOfAction) : MainetConstants.BLANK);

			String lastDecision = (obj[9] != null) ? obj[9].toString() : MainetConstants.BLANK;
			complaint.setStatus(MainetConstants.WorkFlow.StatusForDecision.getStatusForDecision(lastDecision));

			/*
			 * complaint.setCodIdOperLevel1((obj[9] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[9].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel2((obj[10] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[10].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel3((obj[11] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[11].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel4((obj[12] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[12].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel5((obj[13] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[13].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 */

			// Determine SLA duration
			complaint.setSlaDuration(MainetConstants.BLANK);
			slaDuration = (obj[10] != null) ? Long.parseLong(obj[10].toString()) : 0l;
			complaint.setSlaDuration(String.valueOf(Utility.getDurationBreakdown(slaDuration)));

			// Determine SLA slab, and complaint duration
			if (dateOfRequest != null) {
				if (lastDateOfAction != null) {
					if (complaint.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
							|| complaint.getStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
						duration = Math.abs(lastDateOfAction.getTime() - dateOfRequest.getTime());
					else
						duration = Math.abs(new Date().getTime() - dateOfRequest.getTime());
					complaint.setDuration(String.valueOf(Utility.getDurationBreakdown(duration)));
				}
				long diff = Math.abs(new Date().getTime() - dateOfRequest.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);
				complaint.setSlab(Long.toString(diffDays));
			}

			// Determine SLA Status
			slaStatus = (duration <= slaDuration)
					? applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA)
					: applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA);
			complaint.setSlaStatus(slaStatus);

			// Determine SLA duration and taken duration difference
			slaDurationDiff = duration - slaDuration;
			complaint.setSlaDurationDiff(String.valueOf(Utility.getDurationBreakdown(slaDurationDiff)));

			complaint.setApmName((obj[11] != null) ? obj[11].toString() : MainetConstants.BLANK);
			complaint.setApaEmail((obj[12] != null) ? obj[12].toString() : MainetConstants.BLANK);
			complaint.setApaMobilno((obj[13] != null) ? obj[13].toString() : MainetConstants.BLANK);
			/**/ complaint.setCareWardNoEng((obj[15] != null) ? obj[15].toString() : MainetConstants.BLANK);
			complaint.setCareWardNoEng1((obj[18] != null) ? obj[18].toString() : MainetConstants.BLANK);
			complaint.setCareWardNoReg1((obj[19] != null) ? obj[18].toString() : MainetConstants.BLANK);
			/*Setting ward & zone name*/
			if(langId == MainetConstants.ENGLISH) {
				complaint.setCodIdOperLevel1((obj[15] != null) ?  obj[15].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel2((obj[18] != null) ?  obj[18].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel3((obj[21] != null) ?  obj[21].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel4((obj[24] != null) ?  obj[24].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel5((obj[27] != null) ?  obj[27].toString() : MainetConstants.CommonConstants.NA);
			} else {
				complaint.setCodIdOperLevel1((obj[16] != null) ?  obj[16].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel2((obj[19] != null) ?  obj[19].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel3((obj[22] != null) ?  obj[22].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel4((obj[25] != null) ?  obj[25].toString() : MainetConstants.CommonConstants.NA);
				complaint.setCodIdOperLevel5((obj[28] != null) ?  obj[28].toString() : MainetConstants.CommonConstants.NA);
			}
			complaint.setLocNameEng((obj[30] != null) ? obj[30].toString() : MainetConstants.BLANK);
			complaint.setPincode((obj[32] != null) ? obj[32].toString() : MainetConstants.BLANK);
			complaint.setNumberOfDay((obj[33] != null) ? (long) obj[33] : 0L);
			complaint.setStateNameEng((obj[34] != null) ? obj[34].toString() : MainetConstants.BLANK);
			complaint.setDistrictNameEng((obj[35] != null) ? obj[35].toString() : MainetConstants.BLANK);
			complaint.setComplaintDesc((obj[36] != null) ? obj[36].toString() : MainetConstants.BLANK);
			if(langId == MainetConstants.ENGLISH) 
				complaint.setReferenceMode((obj[37] != null) ? obj[37].toString() : MainetConstants.BLANK);
			else
				complaint.setReferenceMode((obj[38] != null) ? obj[38].toString() : MainetConstants.BLANK);
			if(StringUtils.isNotBlank(empId)) {
                            if(StringUtils.isNumeric(empId) && obj[39].toString().equals(empId)) {
                                //compare empId
                                comlaints.add(complaint);
                            }else if(obj[40] != null && obj[40].toString().equals(empId)){
                                //compare String empId with cpd_value like mobile<->MA and portal<->WB
                                comlaints.add(complaint);
                            }
                        }else {
                            comlaints.add(complaint);
                        }
			complaint.setApaAreaName((obj[41] != null) ? obj[41].toString() : MainetConstants.BLANK);
			
		});
		return comlaints;
	}
	
	@Override
	public List<ComplaintDTO> getComplaintsSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId,String referenceMode,String empId) {

		List<ComplaintDTO> comlaints = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder.append(
				"SELECT cr.deptNameEng,cr.deptNameReg,cr.compNameEng,cr.compNameReg, count(cr.complaintId) FROM ComplaintRegister cr where ");

		if (orgId != null)
			builder.append(" cr.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			builder.append("and cr.deptId=:deptId ");
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and cr.compId=:compalintTypeId ");
		
		if (referenceMode != null && !referenceMode.equals("-1"))
			builder.append("and cr.modeType=:referenceMode ");
		if (fromDate != null && toDate != null)
			builder.append("and cr.dateOfRequest between :fromDate and :toDate ");
		if (status != null && !status.equals("-1")) {
			if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						"and cr.lastDecision in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
			else
				builder.append("and cr.lastDecision=:status ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and cr.careWardNo=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and cr.careWardNo1=:codIdOperLevel2 ");

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and cr.codIdOperLevel3=:codIdOperLevel3 ");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and cr.codIdOperLevel4=:codIdOperLevel4 ");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and cr.codIdOperLevel5=:codIdOperLevel5");
	
		
		builder.append("GROUP BY cr.deptNameEng,cr.deptNameReg,cr.compNameEng,cr.compNameReg ");
		//builder.append("order by cr.dateOfRequest desc");
		
		Query query = this.createQuery(builder.toString());

		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		try {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(referenceMode), "RFM",orgId );
			if (lookUp != null)
				query.setParameter("referenceMode",lookUp.getLookUpDesc() );
            
            }catch(Exception e) {
            	
            }
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (status != null && !status.equals("-1")) {
			if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", status);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);
	
		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		
		objList.forEach(obj -> {
		    
			ComplaintDTO complaint = new ComplaintDTO();
			if (langId == MainetConstants.ENGLISH) {
				complaint.setDepartmentName((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
			} else {
				complaint.setDepartmentName((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
			}
			complaint.setDepartmentRegName((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
					
			complaint.setTotalnoOfComplaints((obj[4] != null) ? Long.parseLong(obj[4].toString()) : 0l);
			comlaints.add(complaint);		
		});
		return comlaints;
	}
	


	
	@Override
	public List<ComplaintDTO> getComplaintsSummaryDeptStatWise(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId,String referenceMode,String empId) {

		List<ComplaintDTO> comlaints = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder.append("select\r\n" + 
				"department_desc,\r\n" + 
				"sum(coalesce(b.PENDING,0)) PENDING,\r\n" + 
				"sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + 
				"sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + 
				"(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))) AS TotalRecieved\r\n" + 
				"from\r\n" + 
				"(SELECT\r\n" + 
				"(select DP_DEPTDESC from tb_department where DP_DEPTID=B.DEPT_COMP_ID) department_desc,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n" + 
				"FROM\r\n" + 
				"TB_CARE_REQUEST B ,\r\n" + 
				"TB_WORKFLOW_REQUEST a ,\r\n" + 
				"tb_cfc_application_mst c\r\n" + 
				"WHERE\r\n" + 
				"B.APM_APPLICATION_ID = a.APM_APPLICATION_ID  and\r\n" + 
				"B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n");  
				/*"AND B.CARE_WARD_NO is not null ");*/

		if (orgId != null)
			builder.append(" and B.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			builder.append("and B.DEPT_COMP_ID=:deptId ");
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and B.COMP_SUBTYPE_ID=:compalintTypeId ");
		
		if (referenceMode != null && !referenceMode.equals("-1"))
			builder.append("and B.modeType=:referenceMode ");
		if (fromDate != null && toDate != null)
			builder.append("and (B.DATE_OF_REQUEST between :fromDate and :toDate) ");
		if (status != null && !status.equals("-1")) {
			if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						"and a.LAST_DECISION in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
			else
				builder.append("and a.LAST_DECISION=:status ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and B.CARE_WARD_NO=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and B.CARE_WARD_NO1=:codIdOperLevel2 ");

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and B.CARE_WARD_NO2=:codIdOperLevel3 ");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and B.CARE_WARD_NO3=:codIdOperLevel4 ");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and B.CARE_WARD_NO4=:codIdOperLevel5");

		builder.append("GROUP BY A.STATUS,a.LAST_DECISION,department_desc )B\r\n " + "GROUP BY department_desc ");
		 Query query = this.createNativeQuery(builder.toString());
	    
		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		try {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(referenceMode), "RFM",orgId );
			if (lookUp != null)
				query.setParameter("referenceMode",lookUp.getLookUpDesc() );
            
            }catch(Exception e) {
            	
            }
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		
		if (status != null && !status.equals("-1")) {
			if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", status);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);
	
		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		
		objList.forEach(obj -> {
		    
			ComplaintDTO complaint = new ComplaintDTO();

				complaint.setDepartmentName((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);				
				complaint.setPendingComplaints((obj[1] != null) ? Long.parseLong(obj[1].toString()) : 0l);
				complaint.setClosedComplaints((obj[2] != null) ? Long.parseLong(obj[2].toString()) : 0l);			
			    complaint.setRejectedComplaints((obj[3] != null) ? Long.parseLong(obj[3].toString()) : 0l);
				complaint.setTotalnoOfComplaints((obj[4] != null) ? Long.parseLong(obj[4].toString()) : 0l);
			    comlaints.add(complaint);		
		});
		return comlaints;
	}

	@Override
	public Set<ComplaintDTO> getComplaints(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			List<String> status, List<String> worflowStatus, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId) {

		Set<ComplaintDTO> comlaints = new LinkedHashSet<>();
		StringBuilder builder = new StringBuilder();
		builder.append(
				"SELECT cr.complaintId,cr.applicationId,cr.deptNameEng,cr.deptNameReg,cr.compNameEng,cr.compNameReg,cr.dateOfRequest,cr.lastDateOfAction,cr.status,cr.lastDecision,cr.applicationSlaDurationInMS,cr.apmName,cr.apaEmail,cr.apaMobilno,cr.careWardNo,cr.careWardNoEng,cr.careWardNoReg,cr.careWardNo1,cr.careWardNoEng1,cr.careWardNoReg1,cr.locId,cr.locNameEng,cr.locNameReg,cr.pincode,cr.numberOfDay,cr.stateNameEng,cr.districtNameEng,cr.complaintDesc FROM ComplaintRegister cr where ");

		if (orgId != null)
			builder.append(" cr.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			// change for Defect#93755
			builder.append("and cr.deptId=:deptId ");
		/*
		 * builder.append("and cr.departmentComplaint.department.dpDeptid=:deptId ");
		 */
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and cr.complaintType.compId=:compalintTypeId ");
		if (fromDate != null && toDate != null)
			builder.append("and cr.dateOfRequest between :fromDate and :toDate ");
		if (status != null && !status.isEmpty()) {
			builder.append("and cr.lastDecision in (:status) ");
		}
		if (worflowStatus != null && !worflowStatus.isEmpty()) {
			builder.append("and cr.status in (:worflowStatus) ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and cr.careWardNo=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and cr.careWardNo1=:codIdOperLevel2 ");
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and cr.codIdOperLevel3=:codIdOperLevel3 ");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and cr.codIdOperLevel4=:codIdOperLevel4 ");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and cr.codIdOperLevel5=:codIdOperLevel5");

		Query query = this.createQuery(builder.toString());

		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (status != null && !status.isEmpty()) {
			query.setParameter("status", status);
		}
		if (worflowStatus != null && !worflowStatus.isEmpty()) {
			query.setParameter("worflowStatus", worflowStatus);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();

		objList.forEach(obj -> {
			ComplaintDTO complaint = new ComplaintDTO();
			long slaDuration = 0l;
			long duration = 0l;
			long slaDurationDiff = 0l;
			String slaStatus = "";

			complaint.setApplicationId((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
			if (langId == MainetConstants.ENGLISH) {
				complaint.setDepartmentName((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
			} else {
				complaint.setDepartmentName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
				complaint.setComlaintSubType((obj[5] != null) ? obj[5].toString() : MainetConstants.BLANK);
			}
			complaint.setDepartmentRegName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
			Date dateOfRequest = (obj[6] != null) ? (Date) obj[6] : null;
			Date lastDateOfAction = (obj[7] != null) ? (Date) obj[7] : null;
			complaint.setDateOfRequest(
					(dateOfRequest != null) ? Utility.dateToString(dateOfRequest) : MainetConstants.BLANK);
			complaint.setLastDateOfAction(
					(lastDateOfAction != null) ? Utility.dateToString(lastDateOfAction) : MainetConstants.BLANK);

			String lastDecision = (obj[9] != null) ? obj[9].toString() : MainetConstants.BLANK;
			complaint.setStatus(MainetConstants.WorkFlow.StatusForDecision.getStatusForDecision(lastDecision));

			/*
			 * complaint.setCodIdOperLevel1((obj[9] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[9].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel2((obj[10] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[10].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel3((obj[11] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[11].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel4((obj[12] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[12].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 * complaint.setCodIdOperLevel5((obj[13] != null) ?
			 * CommonMasterUtility.getHierarchicalLookUp(Long.parseLong(obj[13].toString()),
			 * orgId).getLookUpDesc() : MainetConstants.BLANK);
			 */

			// Determine SLA duration
			complaint.setSlaDuration(MainetConstants.BLANK);
			slaDuration = (obj[10] != null) ? Long.parseLong(obj[10].toString()) : 0l;
			complaint.setSlaDuration(String.valueOf(Utility.getDurationBreakdown(slaDuration)));

			// Determine SLA slab, and complaint duration
			if (dateOfRequest != null) {
				if (lastDateOfAction != null) {
					if (complaint.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
							|| complaint.getStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
						duration = Math.abs(lastDateOfAction.getTime() - dateOfRequest.getTime());
					else
						duration = Math.abs(new Date().getTime() - dateOfRequest.getTime());
					complaint.setDuration(String.valueOf(Utility.getDurationBreakdown(duration)));
				}
				long diff = Math.abs(new Date().getTime() - dateOfRequest.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);
				complaint.setSlab(Long.toString(diffDays));
			}

			// Determine SLA Status
			slaStatus = (duration <= slaDuration)
					? applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA)
					: applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA);
			complaint.setSlaStatus(slaStatus);

			// Determine SLA duration and taken duration difference
			slaDurationDiff = duration - slaDuration;
			complaint.setSlaDurationDiff(String.valueOf(Utility.getDurationBreakdown(slaDurationDiff)));

			complaint.setApmName((obj[11] != null) ? obj[11].toString() : MainetConstants.BLANK);
			complaint.setApaEmail((obj[12] != null) ? obj[12].toString() : MainetConstants.BLANK);
			complaint.setApaMobilno((obj[13] != null) ? obj[13].toString() : MainetConstants.BLANK);
			/**/ 
			if(langId == MainetConstants.ENGLISH) {
				complaint.setCareWardNoEng((obj[15] != null) ? obj[15].toString() : MainetConstants.BLANK);
				complaint.setCareWardNoEng1((obj[18] != null) ? obj[18].toString() : MainetConstants.BLANK);
			}else {
				complaint.setCareWardNoEng((obj[16] != null) ? obj[16].toString() : MainetConstants.BLANK);
				complaint.setCareWardNoEng1((obj[19] != null) ? obj[19].toString() : MainetConstants.BLANK);
				
			}
			comlaints.add(complaint);
		});
		return comlaints;
	}

	/**
	 * 
	 */
	@Override
	public Set<ComplaintDTO> getComplaintFeedbacks(Long orgId, Date fromDate, Date toDate, Integer feedbackRating,
			long langId) {
		Set<ComplaintDTO> comlaints = new LinkedHashSet<>();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT " + "cr.complaintId," +  "cr.applicationId," + "apm.apmFname," + "apm.apmMname," + "apm.apmLname,"
				+ "apa.apaEmail," + "apa.apaMobilno," + "cf.ratings," + "cf.ratingsContent," + "cf.ratingsStarCount,"
				+ "cf.feedbackDate " + "FROM CareFeedback cf , " + "TbCfcApplicationMstEntity apm, "
				+ "CFCApplicationAddressEntity apa, " + "CareRequest cr "
				+ "where cf.tokenNumber = apm.apmApplicationId " + "and cr.applicationId = cf.tokenNumber "
				+ "and cf.tokenNumber = apa.apmApplicationId ");
		if (orgId != null)
			builder.append("and apm.tbOrganisation.orgid=:orgId ");
		if (fromDate != null && toDate != null)
			builder.append("and cf.feedbackDate between :fromDate and :toDate ");
		if (feedbackRating != null && feedbackRating > 0)
			builder.append("and cf.ratingsStarCount=:feedbackRating ");

		Query query = this.createQuery(builder.toString());
		if (orgId != null)
			query.setParameter("orgId", orgId);
		/*D#140299- to filter records till toDate*/
		if ((fromDate != null && toDate != null) && (fromDate.equals(toDate))) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}else if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			//D#129857- to filter records for current date
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (feedbackRating != null && feedbackRating > 0)
			query.setParameter("feedbackRating", feedbackRating);

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		objList.forEach(obj -> {
			ComplaintDTO complaint = new ComplaintDTO();
			complaint.setComplaintId((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
			complaint.setApplicationId((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
			complaint.setApaEmail((obj[5] != null) ? obj[5].toString() : MainetConstants.BLANK);
			complaint.setApaMobilno((obj[6] != null) ? obj[6].toString() : MainetConstants.BLANK);
			complaint.setFeedbackRatings((obj[7] != null) ? obj[7].toString() : MainetConstants.BLANK);
			complaint.setFeedback((obj[8] != null) ? obj[8].toString() : MainetConstants.BLANK);
			complaint.setFeedback((obj[9] != null) ? obj[9].toString() : MainetConstants.BLANK);
			complaint.setFeedbackDate((obj[10] != null) ? Utility.dateToString((Date) obj[10]) : MainetConstants.BLANK);
			comlaints.add(complaint);
		});
		
		return comlaints;
	}
	
	
	@Override
	public Set<ComplaintDTO> getComplaintFeedbacksSummary(Long orgId, Date fromDate, Date toDate, Integer feedbackRating,
			long langId) {
		Set<ComplaintDTO> comlaints = new LinkedHashSet<>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT cf.ratings, count(cf.ratingsStarCount)  FROM  CareFeedback cf , CareRequest cr  where  cr.applicationId = cf.tokenNumber ");
		if (orgId != null)
			builder.append("and cr.orgId=:orgId ");
		if (fromDate != null && toDate != null)
			builder.append("and cf.feedbackDate between :fromDate and :toDate ");
		if (feedbackRating != null && feedbackRating > 0)
			builder.append("and cf.ratingsStarCount=:feedbackRating ");
		
		builder.append("GROUP BY cf.ratings ");
		Query query = this.createQuery(builder.toString());
		if (orgId != null)
			query.setParameter("orgId", orgId);
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (feedbackRating != null && feedbackRating > 0)
			query.setParameter("feedbackRating", feedbackRating);

		@SuppressWarnings("unchecked")
 		List<Object[]> objList = query.getResultList();
 		AtomicInteger counter = new AtomicInteger(0);
 		objList.forEach(obj -> {
			ComplaintDTO complaint = new ComplaintDTO();
			complaint.setFeedbackRatings((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
			complaint.setFeedback((obj[1] != null) ? obj[1].toString(): MainetConstants.BLANK);
			comlaints.add(complaint);
			int a =counter.getAndIncrement();
			//Don't use application id after this
			complaint.setApplicationId(String.valueOf(a));
		
 		});
		
		return comlaints;
	}

	@Override
	public Map<SummaryField, Long> getComlaintSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate,
			Date toDate, String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId) {

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT " + "cr.deptNameEng," + "cr.compNameEng," + "cr.deptNameReg," + "cr.compNameReg,"
				+ "sum(1)" + "FROM ComplaintRegister cr ,  CareRequest creq " + "where cr.applicationId = creq.applicationId ");
		if (orgId != null)
			builder.append(" and cr.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			builder.append("and cr.deptId=:deptId ");
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and cr.compId=:compalintTypeId ");
		if (fromDate != null && toDate != null)
			builder.append("and cr.dateOfRequest between :fromDate and :toDate ");
		if (status != null && !status.equals("-1")) {
			if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						"and cr.lastDecision in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE') ");
			else
				builder.append("and cr.lastDecision=:status ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and creq.ward1=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and creq.ward2=:codIdOperLevel2 ");
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and creq.ward3=:codIdOperLevel3 ");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and creq.ward4=:codIdOperLevel4 ");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and creq.ward5=:codIdOperLevel5");
		
			
		//imp append when ward zone filter
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
		builder.append("and cr.complaintId = creq.complaintId ");
			
		
		builder.append(
				" GROUP BY cr.deptNameEng,cr.deptNameReg, cr.compNameEng, cr.compNameReg ORDER BY cr.deptNameEng asc");

		Query query = this.createQuery(builder.toString());
		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (status != null && !status.equals("-1")) {
			if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", status);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);
		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);

		Map<SummaryField, Long> summaryFields = new LinkedHashMap<>();

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		objList.forEach(obj -> {
			SummaryField sf = new SummaryField();
			if (langId == MainetConstants.ENGLISH) {
				sf.setDepartment((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
				sf.setComplaintType((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
			} else {
				sf.setDepartment((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
				sf.setComplaintType((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
			}
			summaryFields.put(sf, (obj[4] != null) ? Long.parseLong(obj[4].toString()) : 0l);
		});
		return summaryFields;
	}

	/**
	 * Selected From date and To date are just date("DD/MM/YYYY"), these are not
	 * TIMESTAMP or date with time("DD/MM/YYYY HH:MM:SS"). following code read the
	 * dates and set it to, start of midnight for 'From date' ("DD/MM/YYYY
	 * 00:00:00") and 'To date' up to midnight ("DD/MM/YYYY 23:59:59")
	 * 
	 */
	private void setDateRange(Date fromDate, Date toDate, Query query) {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		/*D#127270*/
		//c.add(Calendar.DATE, 1);
		c.add(Calendar.SECOND, -1);
		toDate = c.getTime();
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
	}

	@Override
	public List<Object[]> searchComplaints(ComplaintSearchDTO filter) {

		StringBuilder builder = new StringBuilder();
		builder.append("select " + "c.complaintReqId," + "c.complaintId," + "c.applicationId," + "c.deptId,"
				+ "c.deptNameEng," + "c.deptNameReg," + "c.compId," + "c.compNameEng," + "c.compNameReg," + "c.locId,"
				+ "c.locNameEng," + "c.locNameReg," + "c.pincode," + "c.dateOfRequest," + "c.status,"
				+ "c.lastDecision," + "c.complaintDesc," + "c.orgId," + "c.createdDate," + "c.createdBy,"
				+ "c.modifiedDate," + "c.modifiedBy," + "c.apmName " +   "from ComplaintRegister c " + " WHERE ");

		StringBuilder andQur = new StringBuilder();

		if (filter.getOrgId() != null)
			andQur.append("c.orgId=:orgId");
		if (filter.getDateOfRequest() != null) {
			if (andQur.length() > 0)
				andQur.append(" AND c.dateOfRequest =:date");
			else
				andQur.append(" c.dateOfRequest =:date");
		}

		/*
		 * if (filter.getEmpId() != null && filter.getEmplType() != null) { if
		 * (andQur.length() > 0)
		 * andQur.append(" AND c.empId =:empId AND c.empType =:empType "); else
		 * andQur.append(" c.empId =:empId OR c.empType =:empType "); }
		 */

		if (filter.getEmpId() != null) {
			if (andQur.length() > 0) {
				andQur.append("AND  c.empId =:empId");
			} else {
				andQur.append("  c.empId =:empId");
			}

		}
		StringBuilder orQur = new StringBuilder();
		if (filter.getApplicationId() != null)
			orQur.append(" c.applicationId =:applicationId");
		if (filter.getMobileNumber() != null) {
			if (orQur.length() > 0)
				orQur.append(" or c.apaMobilno=:apaMobilno");
			else
				orQur.append(" c.apaMobilno=:apaMobilno");
		}
		if (filter.getPincode() != null) {
			if (orQur.length() > 0)
				orQur.append(" or c.location.pincode=:pincode");
			else
				orQur.append(" c.location.pincode=:pincode");
		}
		if (filter.getComplaintId() != null) {
			if (orQur.length() > 0)
				orQur.append(" or c.complaintId=:complaintId");
			else
				orQur.append(" c.complaintId=:complaintId");
		}
		if (orQur.length() > 0 && andQur.length() > 0) {
			orQur.insert(0, " AND (").append(")");
			andQur.append(orQur);
		} else {
			andQur.append(orQur);
		}
		if (andQur.length() > 0) {
			builder.append(andQur);
		}

		builder.append(" order by c.applicationId desc");
		Query query = this.createQuery(builder.toString());

		if (filter.getOrgId() != null)
			query.setParameter("orgId", filter.getOrgId());

		if (filter.getDateOfRequest() != null)
			query.setParameter("date", filter.getDateOfRequest());

		if (filter.getApplicationId() != null)
			query.setParameter("applicationId", filter.getApplicationId());

		if (filter.getMobileNumber() != null)
			query.setParameter("apaMobilno", filter.getMobileNumber());

		if (filter.getPincode() != null)
			query.setParameter("pincode", filter.getPincode());

		if (filter.getComplaintId() != null)
			query.setParameter("complaintId", filter.getComplaintId());

		/*
		 * if (filter.getEmpId() != null && filter.getEmplType() != null) {
		 * query.setParameter("empId", filter.getEmpId()); query.setParameter("empType",
		 * filter.getEmplType()); }
		 */
		if (filter.getEmpId() != null) {
			query.setParameter("empId", filter.getEmpId());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		return objList;
	}

	@Override
	public Set<ComplaintGradeSummary> getComplaintSummaryDepartmentWise(Long orgId, Long deptId, Long compalintTypeId,
			Date fromDate, Date toDate, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId) {

		Set<ComplaintGradeSummary> complaintGradeSummarys = new LinkedHashSet<>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		Metamodel m = entityManager.getMetamodel();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		EntityType<ComplaintRegister> complaint_ = m.entity(ComplaintRegister.class);
		Root<ComplaintRegister> complaint = cq.from(complaint_);
		ArrayList<Predicate> whereClause = new ArrayList<Predicate>();

		Expression<String> deptNameEng = complaint.get("deptNameEng").as(String.class);
		Expression<String> deptNameReg = complaint.get("deptNameReg").as(String.class);

		if (orgId != null && orgId > 0) {
			whereClause.add(cb.equal(complaint.get("orgId"), orgId));
		}
		if (deptId != null && deptId > 0) {
			whereClause.add(cb.equal(complaint.get("deptId"), deptId));
		}
		if (fromDate != null && toDate != null) {
			whereClause.add(cb.between(complaint.get("dateOfRequest"), fromDate, toDate));
		}

		Predicate pendingBeyondSla = cb.and(cb.equal(complaint.get("slaStatus"), MainetConstants.FlagB), complaint
				.get("status").in(MainetConstants.WorkFlow.Status.PENDING, MainetConstants.WorkFlow.Decision.HOLD));
		Predicate pendingWithinSla = cb.and(cb.equal(complaint.get("slaStatus"), MainetConstants.FlagW), complaint
				.get("status").in(MainetConstants.WorkFlow.Status.PENDING, MainetConstants.WorkFlow.Decision.HOLD));
		Predicate closedBeyondSla = cb.and(cb.equal(complaint.get("slaStatus"), MainetConstants.FlagB), complaint
				.get("status").in(MainetConstants.WorkFlow.Status.CLOSED, MainetConstants.WorkFlow.Decision.REJECTED));
		Predicate closedWithinSla = cb.and(cb.equal(complaint.get("slaStatus"), MainetConstants.FlagW), complaint
				.get("status").in(MainetConstants.WorkFlow.Status.CLOSED, MainetConstants.WorkFlow.Decision.REJECTED));

		Expression<Integer> pendingBeyondSlaCount = cb.selectCase().when(pendingBeyondSla, 1).otherwise(0)
				.as(Integer.class);
		Expression<Integer> pendingWithinSlaCount = cb.selectCase().when(pendingWithinSla, 1).otherwise(0)
				.as(Integer.class);
		Expression<Integer> closedBeyondSlaCount = cb.selectCase().when(closedBeyondSla, 1).otherwise(0)
				.as(Integer.class);
		Expression<Integer> closedWithinSlaCount = cb.selectCase().when(closedWithinSla, 1).otherwise(0)
				.as(Integer.class);

		cq.groupBy(deptNameEng, deptNameReg);
		cq.where(whereClause.toArray(new Predicate[0]));
		CriteriaQuery<Object[]> select = cq.multiselect(deptNameEng, deptNameReg, cb.count(deptNameEng),
				cb.sum(pendingBeyondSlaCount), cb.sum(pendingWithinSlaCount), cb.sum(closedBeyondSlaCount),
				cb.sum(closedWithinSlaCount));

		TypedQuery<Object[]> query = entityManager.createQuery(select);
		List<Object[]> resultList = query.getResultList();

		resultList.stream().forEach(obj -> {
			ComplaintGradeSummary complaintGradeSummary = new ComplaintGradeSummary();
			if (langId == MainetConstants.ENGLISH) {
				complaintGradeSummary.setDepartment(obj[0].toString());
			} else {
				complaintGradeSummary.setDepartment(obj[1].toString());
			}
			complaintGradeSummary.setReceived(Long.parseLong(obj[2].toString()));
			complaintGradeSummary.setPendingBeyondSla(Long.parseLong(obj[3].toString()));
			complaintGradeSummary.setPendingWithinSla(Long.parseLong(obj[4].toString()));
			complaintGradeSummary.setClosedBeyondSla(Long.parseLong(obj[5].toString()));
			complaintGradeSummary.setClosedWithinSla(Long.parseLong(obj[6].toString()));
			calculateRedressal(complaintGradeSummary);
			complaintGradeSummarys.add(complaintGradeSummary);
		});
		return complaintGradeSummarys;
	}

	private void calculateRedressal(ComplaintGradeSummary cgs) {
		long total = cgs.getPendingWithinSla() + cgs.getPendingBeyondSla() + cgs.getClosedWithinSla()
				+ cgs.getClosedBeyondSla();
		double redressalWithinSla = Math.round((double) cgs.getClosedWithinSla() / (double) total * 100);
		double redressalBeyondSla = Math.round((double) cgs.getClosedBeyondSla() / (double) total * 100);
		double redressal = Math
				.round(((double) (cgs.getClosedWithinSla() + cgs.getClosedBeyondSla())) / (double) total * 100);

		cgs.setRedressalWithinSla(redressalWithinSla);
		cgs.setRedressalBeyondSla(redressalBeyondSla);
		cgs.setRedressal(redressal);
		cgs.setGrade(getGrade(redressal));
	}

	private String getGrade(double redressal) {
		String grade = null;
		if (75 >= redressal || redressal <= 100)
			grade = MainetConstants.FlagA;
		else if (50 >= redressal || redressal <= 74)
			grade = MainetConstants.FlagB;
		else if (25 >= redressal || redressal <= 49)
			grade = MainetConstants.FlagC;
		else if (0 >= redressal || redressal <= 24)
			grade = MainetConstants.FlagD;
		return grade;
	}

	@Override
	public List<Object[]> searchComplaintsDetail(ComplaintSearchDTO filter) {
		StringBuilder builder = new StringBuilder();
		builder.append("select " + "c.complaintReqId," + "c.complaintId," + "c.applicationId," + "c.deptId,"
				+ "c.deptNameEng," + "c.deptNameReg," + "c.compId," + "c.compNameEng," + "c.compNameReg," + "c.locId,"
				+ "c.locNameEng," + "c.locNameReg," + "c.pincode," + "c.dateOfRequest," + "c.status,"
				+ "c.lastDecision," + "c.complaintDesc," + "c.orgId," + "c.createdDate," + "c.createdBy,"
				+ "c.modifiedDate," + "c.modifiedBy,"+ "c.apmName " +   "from ComplaintRegister c " + " WHERE ");

		StringBuilder andQur = new StringBuilder();

		if (filter.getOrgId() != null)
			andQur.append("c.orgId=:orgId");
		
		if ((filter.getFromDate() != null &&  !filter.getFromDate().equals(MainetConstants.BLANK)) ||
			(filter.getToDate() != null && filter.getToDate().equals(MainetConstants.BLANK))) {
				andQur.append(" AND Date(c.dateOfRequest) between :fromDate  and :toDate");			
		}		
		
		if (filter.getDepartmentComplaint() != null  && filter.getDepartmentComplaint() > 0) {
			if (andQur.length() > 0)
				andQur.append(" AND c.deptId=:departmentComplaint");
			else
				andQur.append(" c.deptId=:departmentComplaint");
		}
		if (filter.getComplaintTypeId() != null && filter.getComplaintTypeId() > 0) {
			if (andQur.length() > 0)
				andQur.append(" AND c.compId=:complaintTypeId");
			else
				andQur.append(" c.compId=:complaintTypeId");
		}
		
		StringBuilder orQur = new StringBuilder();
		
		if (StringUtils.isNotBlank(filter.getMobileNumber())) {
			if (orQur.length() > 0)
				orQur.append(" or c.apaMobilno=:apaMobilno");
			else
				orQur.append(" c.apaMobilno=:apaMobilno");
		}
	
		if (StringUtils.isNotBlank(filter.getComplaintId())) {
			if (orQur.length() > 0)
				orQur.append(" or c.complaintId=:complaintId");
			else
				orQur.append(" c.complaintId=:complaintId");
		}
		if (StringUtils.isNotBlank(filter.getStatus())) {                                                                                                                                                                                                            
			if (orQur.length() > 0)
				orQur.append(" or c.status=:status");
			else
				orQur.append(" c.status=:status");
		}
		if (orQur.length() > 0 && andQur.length() > 0) {
			orQur.insert(0, " AND (").append(")");
			andQur.append(orQur);
		} else {
			andQur.append(orQur);
		}
		if (andQur.length() > 0) {
			builder.append(andQur);
		}

		builder.append(" order by c.applicationId desc");
		Query query = this.createQuery(builder.toString());

		if (filter.getOrgId() != null)
			query.setParameter("orgId", filter.getOrgId());

		if (filter.getDateOfRequest() != null)
			query.setParameter("date", filter.getDateOfRequest());

		if (filter.getApplicationId() != null)
			query.setParameter("applicationId", filter.getApplicationId());

		if (StringUtils.isNotBlank(filter.getMobileNumber()))
			query.setParameter("apaMobilno", filter.getMobileNumber());

	
		if (StringUtils.isNotBlank(filter.getComplaintId()))
			query.setParameter("complaintId", filter.getComplaintId());

		if(filter.getFromDate() != null && !filter.getFromDate().equals(MainetConstants.BLANK)) {
			query.setParameter("fromDate", filter.getFromDate());
			
		}if(filter.getToDate() != null && !filter.getToDate().equals(MainetConstants.BLANK)) {
			query.setParameter("toDate", filter.getToDate());
		}
		if(filter.getDepartmentComplaint() != null && filter.getDepartmentComplaint() > 0) {
			query.setParameter("departmentComplaint", filter.getDepartmentComplaint());
		}
		if(filter.getComplaintTypeId() != null && filter.getComplaintTypeId() > 0) {
			query.setParameter("complaintTypeId", filter.getComplaintTypeId());
		}
		if (StringUtils.isNotBlank(filter.getStatus())) {
			query.setParameter("status", filter.getStatus());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		return objList;	
	}
	
	@Override
	public List<Object[]> searchComplaintsDetailForCareOperatorRole(ComplaintSearchDTO filter) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT \r\n" + 
				"                    a.APM_APPLICATION_ID AS APM_APPLICATION_ID, \r\n" + 
				"                    a.ORGID AS ORGID, \r\n" + 
				"                    c.DP_DEPTID AS DP_DEPTID, \r\n" + 
				"                    (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC, \r\n" + 
				"                    (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR, \r\n" + 
				"                    c.SM_SERVICE_ID AS SM_SERVICE_ID, \r\n" + 
				"                    (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME, \r\n" + 
				"                    (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR, \r\n" + 
				"                    c.EVENT_ID AS EVENT_ID, \r\n" + 
				"                    (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName, \r\n" + 
				"                    (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg, \r\n" + 
				"                    a.TASK_ID AS TASK_ID, \r\n" + 
				"                    a.TASK_NAME AS TASK_NAME, \r\n" + 
				"                    c.SMFACTION AS SMFACTION, \r\n" + 
				"                    e.DATE_OF_REQUEST AS DATE_OF_REQUEST, \r\n" + 
				"                    c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID, \r\n" + 
				"                    c.TASK_STATUS AS TASK_STATUS, \r\n" + 
				"                    e.LAST_DECISION AS DECISION, \r\n" + 
				"                    a.EMPID AS EMPID, \r\n" + 
				"                    a.REFERENCE_ID AS REFERENCE_ID,\r\n" + 
				"                   e.WORFLOW_TYPE_ID AS WORFLOW_TYPE_ID, \r\n" + 
				"                    c.TASK_SLA_DURATION AS TASK_SLA_DURATION,\r\n" + 
				"    (select count(1) from tb_workflow_task x where REFERENCE_ID =e.REFERENCE_ID and task_name not in ('Start','Hidden_Task Requester')) lvl,\r\n" + 
				"    (select group_concat(empname,' ') from employee a where FIND_IN_SET (a.empid,c.wftask_actorid)) actor_name,\r\n" + 
				"    COMMENTS\r\n" + 
				"                 FROM tb_workflow_request e, \r\n" + 
				"                     tb_workflow_task c, \r\n" + 
				"                     tb_workflow_action a ,\r\n" + 
				"                             (  select a.REFERENCE_ID,max(c.wftask_id) wftask_id,max(workflow_act_id) workflow_act_id\r\n" + 
				"                 FROM tb_workflow_request e, \r\n" + 
				"                     tb_workflow_task c, \r\n" + 
				"                     tb_workflow_action a \r\n" + 
				"                WHERE a.WFTASK_ID = c.WFTASK_ID \r\n" + 
				"                        AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID and e.orgid=6\r\n" + 
				"                    group by a.REFERENCE_ID limit 100) d\r\n" + 
				"                WHERE a.WFTASK_ID = c.WFTASK_ID \r\n" + 
				"                       AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID\r\n" + 
				"                       and e.REFERENCE_ID=d.REFERENCE_ID\r\n" + 
				"                        and c.wftask_id=d.wftask_id\r\n" + 
				"                       and a.workflow_act_id=d.workflow_act_id\r\n");

		StringBuilder andQur = new StringBuilder();

		if (filter.getOrgId() != null)
			andQur.append(" AND c.ORGID=:orgId ");
		
		if ((filter.getFromDate() != null &&  !filter.getFromDate().equals(MainetConstants.BLANK)) ||
			(filter.getToDate() != null && filter.getToDate().equals(MainetConstants.BLANK))) {
				andQur.append(" AND Date(e.DATE_OF_REQUEST) between :fromDate  and :toDate ");			
		}		
		
		
		if (StringUtils.isNotBlank(filter.getMobileNumber())) {
				andQur.append(" AND e.APM_APPLICATION_ID in (select APM_APPLICATION_ID  FROM tb_cfc_application_address WHERE APA_MOBILNO=:apaMobilno) ");
		}
	
		if (StringUtils.isNotBlank(filter.getComplaintId())) {
				andQur.append(" OR e.REFERENCE_ID=:complaintId or e.APM_APPLICATION_ID=:complaintId");
		}
		if (StringUtils.isNotBlank(filter.getStatus())) {                                                                                                                                                                                                            
				andQur.append(" AND c.TASK_STATUS=:status ");
		}

		builder.append(andQur);
		builder.append(" order by e.DATE_OF_REQUEST desc");
		Query query = this.createNativeQuery(builder.toString());

		if (filter.getOrgId() != null)
			query.setParameter("orgId", filter.getOrgId());

		if (filter.getDateOfRequest() != null)
			query.setParameter("date", filter.getDateOfRequest());

		if (filter.getApplicationId() != null)
			query.setParameter("applicationId", filter.getApplicationId());

		if (StringUtils.isNotBlank(filter.getMobileNumber()))
			query.setParameter("apaMobilno", filter.getMobileNumber());

	
		if (StringUtils.isNotBlank(filter.getComplaintId()))
			query.setParameter("complaintId", filter.getComplaintId());

		if(filter.getFromDate() != null && !filter.getFromDate().equals(MainetConstants.BLANK)) {
			query.setParameter("fromDate", filter.getFromDate());
			
		}if(filter.getToDate() != null && !filter.getToDate().equals(MainetConstants.BLANK)) {
			query.setParameter("toDate", filter.getToDate());
		}
		if (StringUtils.isNotBlank(filter.getStatus())) {
			query.setParameter("status", filter.getStatus());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		return objList;	
	}

	@Override
	public List<ComplaintDTO> getComplaintsSummarySlaWise(Long orgId, Long department, Long complaintType,
			Date fromDate, Date toDate, List<String> statuses, String closed, Long codIdOperLevel1,
			Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5, long langId) {


		List<ComplaintDTO> comlaints = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder.append("select\r\n" + 
				"department_descrption,\r\n" + 
				"complaintSubType,\r\n" + 
				"sum(coalesce(BeyoundCLOSED,0)) BeyoundCLOSED,\r\n" + 
				"sum(coalesce(WithinCLOSED,0)) WithinCLOSED,\r\n" + 
				"sum(coalesce(BeyoundPending,0)) BeyoundPending,\r\n" + 
				"sum(coalesce(WithinPending,0)) WithinPending,\r\n" + 
				"(sum(coalesce(BeyoundCLOSED,0))+sum(coalesce(WithinCLOSED,0))+sum(coalesce(BeyoundPending,0))+sum(coalesce(WithinPending,0))) AS TotalRecieved\r\n" + 
				"from\r\n" + 
				"(select\r\n" + 
				"department_descrption,\r\n" + 
				"complaintSubType,\r\n" + 
				"CASE WHEN STATUS='CLOSED' THEN COUNT(1) END AS CLOSED,\r\n" + 
				"CASE WHEN STATUS='CLOSED' and SLA='B' THEN COUNT(1) END AS BeyoundCLOSED,\r\n" + 
				"CASE WHEN STATUS='CLOSED' and SLA='W' THEN COUNT(1) END AS WithinCLOSED,\r\n" + 
				"CASE WHEN STATUS='PENDING' and SLA='B' THEN COUNT(1) END AS BeyoundPending,\r\n" + 
				"CASE WHEN STATUS='PENDING' and SLA='W' THEN COUNT(1) END AS WithinPending\r\n" + 
				"from\r\n" + 
				"(select\r\n" + 
				"(select dept.DP_DEPTDESC from tb_department dept where dept.DP_DEPTID=B.dept_comp_id) as department_descrption,\r\n" + 
				"(select com.comp_type_desc from tb_dep_complaint_subtype com where com.COMP_ID=B.COMP_SUBTYPE_ID) complaintSubType,\r\n" + 
				"a.LAST_DECISION,\r\n" + 
				"(CASE WHEN\r\n" + 
				"((a.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,NOW()) >=\r\n" + 
				"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0) THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n" + 
				"WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
				"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END))) THEN 'B'\r\n" + 
				"WHEN ((a.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,NOW()) <\r\n" + 
				"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)\r\n" + 
				"THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
				"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))\r\n" + 
				"THEN 'W'\r\n" + 
				"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND (TIMESTAMPDIFF(SECOND,\r\n" + 
				"a.DATE_OF_REQUEST,a.LAST_DATE_OF_ACTION) >\r\n" + 
				"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)\r\n" + 
				"THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
				"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END))) THEN 'B' WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND\r\n" + 
				"(TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,a.LAST_DATE_OF_ACTION) <= (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.wf_id)) / 1000) <> 0) THEN\r\n" + 
				"((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
				"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))\r\n" + 
				"THEN 'W' END) AS SLA,\r\n" + 
				"(CASE WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (a.LAST_DECISION = 'REJECTED')) THEN 'REJECTED'\r\n" + 
				"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (a.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
				"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (a.LAST_DECISION = 'HOLD')) THEN 'HOLD'\r\n" + 
				"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (a.LAST_DECISION <> 'HOLD'))\r\n" + 
				"THEN 'PENDING' END) AS STATUS\r\n" + 
				"from\r\n" + 
				"tb_care_request b,\r\n" + 
				"tb_organisation c ,\r\n" + 
				"TB_WORKFLOW_REQUEST a,\r\n" + 
				"tb_workflow_mas tb5\r\n" + 
				"where\r\n" + 
				"b.orgid = c.orgid and\r\n" + 
				"b.apm_application_id = a.APM_APPLICATION_ID and\r\n" + 
				"tb5.WF_ID = a.WORFLOW_TYPE_ID and\r\n" + 
				"b.SM_SERVICE_ID is NULL and \r\n" + 
				"B.CARE_WARD_NO IS NOT NULL  \r\n" );

		if (orgId != null)
			builder.append(" and b.orgId=:orgId ");
		if (department != null && department > 0)
			builder.append(" and b.DEPT_COMP_ID=:department ");
		if (complaintType != null && complaintType > 0)
			builder.append(" and b.COMP_SUBTYPE_ID=:complaintType");
			
		if (fromDate != null && toDate != null)
			builder.append(" and (b.DATE_OF_REQUEST between :fromDate and :toDate) ");
		/*if (statuses != null && !statuses.equals("-1")) {
			if (statuses.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						" and a.LAST_DECISION in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
			else
				builder.append(" and a.LAST_DECISION in (:status)");
		}*/
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append(" and b.CARE_WARD_NO=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("  and b.CARE_WARD_NO1=:codIdOperLevel2");

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append(" and b.CARE_WARD_NO2=:codIdOperLevel3");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append(" and b.CARE_WARD_NO3=:codIdOperLevel4");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append(" and b.CARE_WARD_NO4=:codIdOperLevel5 ");
            builder.append(" ) tb1");
		builder.append(" group by status,sla,department_descrption,LAST_DECISION,complaintSubType ) tb2 " + 
				" group by department_descrption,complaintSubType");
		 Query query = this.createNativeQuery(builder.toString());
	    
		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (department != null && department > 0)
			query.setParameter("department", department);
		if (complaintType != null && complaintType > 0)
			query.setParameter("compalintTypeId", complaintType);
	
		/*D#140299- to filter records till toDate*/ 
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		/*if (statuses != null && !statuses.equals("-1")) {
			if (!statuses.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", statuses);
		}*/
	
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);
		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		
		objList.forEach(obj -> {
		    
			ComplaintDTO complaint = new ComplaintDTO();

				complaint.setDepartmentName((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);				
				complaint.setComlaintSubType((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
			    
			    complaint.setTotalResolvedBeyoundSla((obj[2] != null) ? Long.parseLong(obj[2].toString()) : 0l);
				complaint.setTotalResolvedWithinSla((obj[3] != null) ? Long.parseLong(obj[3].toString()) : 0l);			
			    complaint.setTotalPendingbeyoundSla((obj[4] != null) ? Long.parseLong(obj[4].toString()) : 0l);
			    complaint.setTotalPendingWithinSla((obj[5] != null) ? Long.parseLong(obj[5].toString()) : 0l);
			    complaint.setTotalnoOfComplaints((obj[6] != null) ? Long.parseLong(obj[6].toString()) : 0l);
			    comlaints.add(complaint);		
		});
		return comlaints;
	
	}

	@Override
	public List<ComplaintDTO> getUserWiseComplaintSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId,String referenceMode,String empId) {

		List<ComplaintDTO> comlaints = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder.append(
				"SELECT cr.deptNameEng,cr.compNameEng,cr.modeType,cr.careWardNoEng,count(cr.complaintId)  FROM ComplaintRegister cr where ");

		if (orgId != null)
			builder.append(" cr.orgId=:orgId ");
		if (deptId != null && deptId > 0)
			builder.append("and cr.deptId=:deptId ");
		if (compalintTypeId != null && compalintTypeId > 0)
			builder.append("and cr.compId=:compalintTypeId ");
		
		if (referenceMode != null && !referenceMode.equals("-1"))
			builder.append("and cr.modeType=:referenceMode ");
		if (fromDate != null && toDate != null)
			builder.append("and cr.dateOfRequest between :fromDate and :toDate ");
		if (status != null && !status.equals("-1")) {
			if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				builder.append(
						"and cr.lastDecision in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
			else
				builder.append("and cr.lastDecision=:status ");
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			builder.append("and cr.careWardNo=:codIdOperLevel1 ");
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			builder.append("and cr.careWardNo1=:codIdOperLevel2 ");

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			builder.append("and cr.codIdOperLevel3=:codIdOperLevel3 ");
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			builder.append("and cr.codIdOperLevel4=:codIdOperLevel4 ");
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			builder.append("and cr.codIdOperLevel5=:codIdOperLevel5");
		
		builder.append("GROUP BY cr.deptNameEng,cr.compNameEng,cr.modeType,cr.careWardNoEng");
		
		Query query = this.createQuery(builder.toString());

		if (orgId != null)
			query.setParameter("orgId", orgId);
		if (deptId != null && deptId > 0)
			query.setParameter("deptId", deptId);
		if (compalintTypeId != null && compalintTypeId > 0)
			query.setParameter("compalintTypeId", compalintTypeId);
		try {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(referenceMode), "RFM",orgId );
			if (lookUp != null)
				query.setParameter("referenceMode",lookUp.getLookUpDesc() );
            
            }catch(Exception e) {
            	
            }
		/*D#140299- to filter records till toDate*/
		if (fromDate != null && toDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			/*D#129857- to filter records for current date*/
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
		}
		if (status != null && !status.equals("-1")) {
			if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
				query.setParameter("status", status);
		}
		if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
			query.setParameter("codIdOperLevel1", codIdOperLevel1);
		if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
			query.setParameter("codIdOperLevel2", codIdOperLevel2);

		if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
			query.setParameter("codIdOperLevel3", codIdOperLevel3);
		if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
			query.setParameter("codIdOperLevel4", codIdOperLevel4);
		if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
			query.setParameter("codIdOperLevel5", codIdOperLevel5);
		@SuppressWarnings("unchecked")
		List<Object[]> objList = query.getResultList();
		objList.forEach(obj -> {
	        //#129885
			ComplaintDTO complaint = new ComplaintDTO();		
			complaint.setDepartmentName((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
			complaint.setComlaintSubType((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
			complaint.setReferenceMode((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
			complaint.setCareWardNoEng((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
			complaint.setTotalnoOfComplaints((obj[4] != null) ? Long.parseLong(obj[4].toString()) : 0l);
			comlaints.add(complaint);
		});
		return comlaints;
	}

	@Override
	public Set<ComplaintDTO> getComplaintsWithPendingWith(Long orgId, Long deptId, Long compalintTypeId, Date fromDate,
			Date toDate, String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId, String referenceMode, String empId) {
		
		Set<ComplaintDTO> comlaints = new LinkedHashSet<>();
		
			
			StringBuilder hql = new StringBuilder("SELECT cr.COMPLAINT_NO,cr.APM_APPLICATION_ID,cr.DEPARTMENT_ENG,cr.DEPARTMENT_REG,cr.COMPLAINT_SUB_TYPE,cr.COMPLAINT_SUB_TYPE_REG,cr.DATEOFREQUEST,cr.LASTDAYACTION,cr.STATUS,cr.LAST_DECISION,cr.APPLICATION_SLA_DURATION,cr.APPLICANT_NAME,cr.APA_EMAIL,cr.APA_MOBILNO,cr.care_ward_no,cr.CARE_WARD_NO_ENG,cr.CARE_WARD_NO_REG,cr.care_ward_no1,cr.CARE_WARD_NO1_ENG,cr.CARE_WARD_NO1_REG,cr.care_ward_no2,cr.CARE_WARD_NO2_ENG,cr.CARE_WARD_NO2_REG,cr.care_ward_no3,cr.CARE_WARD_NO3_ENG,cr.CARE_WARD_NO3_REG,cr.care_ward_no4,cr.CARE_WARD_NO4_ENG,cr.CARE_WARD_NO4_REG,cr.LOC_ID,cr.LOCATIONNAME_ENG,cr.LOCATIONNAME_REG,cr.PINCODE,cr.NOOFDAY,cr.STATE_ENG,cr.DISTRICT_ENG,cr.COMPLAINTDESC,cr.CARE_MODE,cr.CARE_MODE_REG,cr.EMPID,cr.CARE_MODE_CODE,cr.APA_AREANM,  CASE WHEN STATUS='PENDING' THEN (SELECT group_CONCAT(EMPNAME,' ',EMPLNAME)   FROM  EMPLOYEE A WHERE FIND_IN_SET (empid,task.WFTASK_ACTORID)) ELSE NULL END PENDING_WITH,CASE WHEN STATUS='PENDING' THEN CR.LASTDAYACTION ELSE NULL END PENDING_FROM_DATE,CASE WHEN STATUS='CLOSED' THEN (SELECT CONCAT(EMPNAME,' ',EMPLNAME)   FROM  EMPLOYEE A WHERE empid=task.Updated_BY) ELSE NULL END CLOSED_BY,CASE WHEN STATUS='CLOSED' THEN CR.LASTDAYACTION ELSE NULL END CLOSED_DATE FROM ComplaintRegister cr,(Select APM_APPLICATION_ID, a.WFTASK_ACTORID,a.Updated_BY,CREATED_BY,Created_date,Updated_date from  tb_workflow_task a where (a.APM_APPLICATION_ID,a.WFTASK_ID)in (select APM_APPLICATION_ID,max(WFTASK_ID) from tb_workflow_task where TASK_NAME!='Hidden_Task Requester'  group by  APM_APPLICATION_ID))task where task.APM_APPLICATION_ID=cr.APM_APPLICATION_ID");
			
			if(orgId!=null)
				hql.append(" and cr.orgId=:orgId ");
			if (deptId != null && deptId > 0)
				hql.append("and cr.DEPARTMENT_ID=:deptId ");
			if (compalintTypeId != null && compalintTypeId > 0)
				hql.append("and cr.DEPT_COMP_ID=:compalintTypeId ");
			
			if (referenceMode != null && !referenceMode.equals("-1"))
				hql.append("and cr.CARE_MODE=:referenceMode ");
			if (fromDate != null && toDate != null)
				hql.append("and cr.DATEOFREQUEST between :fromDate and :toDate ");
			if (status != null && !status.equals("-1")) {
				if (status.equals(MainetConstants.WorkFlow.Decision.PENDING))
					hql.append(
							"and cr.LAST_DECISION in ('SUBMITTED','REOPENED','FORWARD_TO_DEPARTMENT','FORWARD_TO_EMPLOYEE','FOLLOWUP','SEND_FOR_FEEDBACK') ");
				else
					hql.append("and cr.LAST_DECISION=:status ");
			}
			if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
				hql.append("and cr.care_ward_no=:codIdOperLevel1 ");
			if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
				hql.append("and cr.care_ward_no1=:codIdOperLevel2 ");
			
			if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
				hql.append("and cr.care_ward_no2=:codIdOperLevel3 ");
			
			if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
				hql.append("and cr.care_ward_no3=:codIdOperLevel4 ");
			
			if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
				hql.append("and cr.care_ward_no4=:codIdOperLevel5 ");
			// for sorting according to department
			//builder.append("order by cr.deptId desc");
			/*D#127277*/
			hql.append("order by cr.dateOfRequest desc");

			
			LOGGER.info("QUERY  "+hql.toString());

			final Query query = this.createNativeQuery(hql.toString());
		
			if (orgId != null)
				query.setParameter("orgId", orgId);
			if (deptId != null && deptId > 0)
				query.setParameter("deptId", deptId);
			if (compalintTypeId != null && compalintTypeId > 0)
				query.setParameter("compalintTypeId", compalintTypeId);
			try {
				LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(Long.valueOf(referenceMode), "RFM",orgId );
				if (lookUp != null)
					query.setParameter("referenceMode",lookUp.getLookUpDesc() );
	            
	            }catch(Exception e) {
	            	
	            }
			/*D#140299- to filter records till toDate*/
			if (fromDate != null && toDate != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(toDate);
				/*D#129857- to filter records for current date*/
				c.add(Calendar.DATE, 1);
				toDate = c.getTime();
				query.setParameter("fromDate", fromDate);
				query.setParameter("toDate", toDate);
			}
			if (status != null && !status.equals("-1")) {
				if (!status.equals(MainetConstants.WorkFlow.Decision.PENDING))
					query.setParameter("status", status);
			}
			if (codIdOperLevel1 != null && codIdOperLevel1 > 0)
				query.setParameter("codIdOperLevel1", codIdOperLevel1);
			if (codIdOperLevel2 != null && codIdOperLevel2 > 0)
				query.setParameter("codIdOperLevel2", codIdOperLevel2);

			if (codIdOperLevel3 != null && codIdOperLevel3 > 0)
				query.setParameter("codIdOperLevel3", codIdOperLevel3);
			if (codIdOperLevel4 != null && codIdOperLevel4 > 0)
				query.setParameter("codIdOperLevel4", codIdOperLevel4);
			if (codIdOperLevel5 != null && codIdOperLevel5 > 0)
				query.setParameter("codIdOperLevel5", codIdOperLevel5);
			
			@SuppressWarnings("unchecked")
			List<Object[]> objList = query.getResultList();
			
			objList.forEach(obj -> {
			    
				ComplaintDTO complaint = new ComplaintDTO();
				long slaDuration = 0l;
				long duration = 0l;
				long slaDurationDiff = 0l;
				String slaStatus = "";
				
				complaint.setComplaintId((obj[0] != null) ? obj[0].toString() : MainetConstants.BLANK);
				complaint.setApplicationId((obj[1] != null) ? obj[1].toString() : MainetConstants.BLANK);
				if (langId == MainetConstants.ENGLISH) {
					complaint.setDepartmentName((obj[2] != null) ? obj[2].toString() : MainetConstants.BLANK);
					complaint.setComlaintSubType((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
				} else {
					complaint.setDepartmentName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
					complaint.setComlaintSubType((obj[5] != null) ? obj[5].toString() : MainetConstants.BLANK);
				}
				complaint.setDepartmentRegName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
				Date dateOfRequest = (obj[6] != null) ? (Date) obj[6] : null;
				Date lastDateOfAction = (obj[7] != null) ? (Date) obj[7] : null;
				complaint.setDateOfRequest(
						(dateOfRequest != null) ? Utility.dateToString(dateOfRequest) : MainetConstants.BLANK);
				complaint.setLastDateOfAction(
						(lastDateOfAction != null) ? Utility.dateToString(lastDateOfAction) : MainetConstants.BLANK);

				String lastDecision = (obj[9] != null) ? obj[9].toString() : MainetConstants.BLANK;
				complaint.setStatus(MainetConstants.WorkFlow.StatusForDecision.getStatusForDecision(lastDecision));

				// Determine SLA duration
				complaint.setSlaDuration(MainetConstants.BLANK);
				slaDuration = (obj[10] != null) ? Long.parseLong(obj[10].toString()) : 0l;
				complaint.setSlaDuration(String.valueOf(Utility.getDurationBreakdown(slaDuration)));

				// Determine SLA slab, and complaint duration
				if (dateOfRequest != null) {
					if (lastDateOfAction != null) {
						if (complaint.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
								|| complaint.getStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED))
							duration = Math.abs(lastDateOfAction.getTime() - dateOfRequest.getTime());
						else
							duration = Math.abs(new Date().getTime() - dateOfRequest.getTime());
						complaint.setDuration(String.valueOf(Utility.getDurationBreakdown(duration)));
					}
					long diff = Math.abs(new Date().getTime() - dateOfRequest.getTime());
					long diffDays = diff / (24 * 60 * 60 * 1000);
					complaint.setSlab(Long.toString(diffDays));
				}

				// Determine SLA Status
				slaStatus = (duration <= slaDuration)
						? applicationSession.getMessage(MainetConstants.ServiceCareCommon.WITHIN_SLA)
						: applicationSession.getMessage(MainetConstants.ServiceCareCommon.BEYOND_SLA);
				complaint.setSlaStatus(slaStatus);

				// Determine SLA duration and taken duration difference
				slaDurationDiff = duration - slaDuration;
				complaint.setSlaDurationDiff(String.valueOf(Utility.getDurationBreakdown(slaDurationDiff)));

				complaint.setApmName((obj[11] != null) ? obj[11].toString() : MainetConstants.BLANK);
				complaint.setApaEmail((obj[12] != null) ? obj[12].toString() : MainetConstants.BLANK);
				complaint.setApaMobilno((obj[13] != null) ? obj[13].toString() : MainetConstants.BLANK);
				/**/ complaint.setCareWardNoEng((obj[15] != null) ? obj[15].toString() : MainetConstants.BLANK);
				complaint.setCareWardNoEng1((obj[18] != null) ? obj[18].toString() : MainetConstants.BLANK);
				complaint.setCareWardNoReg1((obj[19] != null) ? obj[18].toString() : MainetConstants.BLANK);
				/*Setting ward & zone name*/
				if(langId == MainetConstants.ENGLISH) {
					complaint.setCodIdOperLevel1((obj[15] != null) ?  obj[15].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel2((obj[18] != null) ?  obj[18].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel3((obj[21] != null) ?  obj[21].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel4((obj[24] != null) ?  obj[24].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel5((obj[27] != null) ?  obj[27].toString() : MainetConstants.CommonConstants.NA);
				} else {
					complaint.setCodIdOperLevel1((obj[16] != null) ?  obj[16].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel2((obj[19] != null) ?  obj[19].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel3((obj[22] != null) ?  obj[22].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel4((obj[25] != null) ?  obj[25].toString() : MainetConstants.CommonConstants.NA);
					complaint.setCodIdOperLevel5((obj[28] != null) ?  obj[28].toString() : MainetConstants.CommonConstants.NA);
				}
				complaint.setLocNameEng((obj[30] != null) ? obj[30].toString() : MainetConstants.BLANK);
				complaint.setPincode((obj[32] != null) ? obj[32].toString() : MainetConstants.BLANK);
				
				if(obj[33] != null){
				String num = obj[33].toString();
				Long longNum= Long.parseLong(num);
				
				complaint.setNumberOfDay((longNum != null) ? longNum : 0L);
				}
				else{
					complaint.setNumberOfDay(0L);
				}
				
				complaint.setStateNameEng((obj[34] != null) ? obj[34].toString() : MainetConstants.BLANK);
				complaint.setDistrictNameEng((obj[35] != null) ? obj[35].toString() : MainetConstants.BLANK);
				complaint.setComplaintDesc((obj[36] != null) ? obj[36].toString() : MainetConstants.BLANK);
				if(langId == MainetConstants.ENGLISH) 
					complaint.setReferenceMode((obj[37] != null) ? obj[37].toString() : MainetConstants.BLANK);
				else
					complaint.setReferenceMode((obj[38] != null) ? obj[38].toString() : MainetConstants.BLANK);
				if(StringUtils.isNotBlank(empId)) {
	                            if(StringUtils.isNumeric(empId) && obj[39].toString().equals(empId)) {
	                                //compare empId
	                                comlaints.add(complaint);
	                            }else if(obj[40] != null && obj[40].toString().equals(empId)){
	                                //compare String empId with cpd_value like mobile<->MA and portal<->WB
	                                comlaints.add(complaint);
	                            }
	                        }else {
	                            comlaints.add(complaint);
	                        }
				complaint.setApaAreaName((obj[41] != null) ? obj[41].toString() : MainetConstants.BLANK);
				complaint.setPendingWith((obj[42] != null) ? obj[42].toString() : MainetConstants.BLANK);
				//#155098
				if(obj[43]!=null)
				{
					Date pendingDate= (Date)obj[43];
					complaint.setPendingFromDate(Utility.dateToString(pendingDate,MainetConstants.DATE_HOUR_FORMAT));
				}	
				else{
					complaint.setPendingFromDate(MainetConstants.BLANK);
					
				}
				
				complaint.setClosedBy((obj[44] != null) ? obj[44].toString() : MainetConstants.BLANK);
				
				
				if(obj[45]!=null){
					Date closedDate= (Date)obj[45];
					complaint.setClosedDate(Utility.dateToString(closedDate,MainetConstants.DATE_HOUR_FORMAT));
				}else{
					complaint.setClosedDate(MainetConstants.BLANK);
				}
			});
			return comlaints;
	}

}
