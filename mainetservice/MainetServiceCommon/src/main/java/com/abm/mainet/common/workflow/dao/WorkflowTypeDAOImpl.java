package com.abm.mainet.common.workflow.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.util.Date;
import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;


@Repository
public class WorkflowTypeDAOImpl extends AbstractDAO<WorkflowMas> implements IWorkflowTypeDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTypeDAOImpl.class);
    
    @Autowired
	 private IOrganisationDAO organisationDAO;
    @Override
    public List<WorkflowMas> getDefinedActiveWorkFlows(Long orgId, Long deptId, Long serviceId, Long compId,Long schId,Long extId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select wt from WorkflowMas wt where wt.status = 'Y' and wt.organisation.orgid =" + orgId
                + " and wt.department.dpDeptid = " + deptId
                + " and wt.service.smServiceId = " + serviceId);
        if (compId != null &&  compId != 0) {
            sb.append(" and wt.complaint.compId = " + compId);
        }
		//D89638
        if(schId!=null && schId!=0) {
        	sb.append(" and wt.wmSchCodeId2 = " + schId);
        }
        if(extId!=null && extId!=0){
        	sb.append(" and wt.extIdentifier = " + extId);
        }
        sb.append(" order by wt.fromAmount asc ");
        
        Query query = this.createQuery(sb.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        return list;
    }

    @Override
    public List<WorkflowMas> getAllWorkFlows(Long orgId, Long deptId, Long serviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select wt from WorkflowMas wt where wt.organisation.orgid = " + orgId);

        if (deptId != 0 && deptId != null) {
            sb.append(" and wt.department.dpDeptid = " + deptId);
        }
        if (serviceId != 0 && serviceId != null) {
            sb.append(" and wt.service.smServiceId = " + serviceId);
        }
        sb.append(" order by wt.wfId desc ");
        Query query = this.createQuery(sb.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        return list;
    }

    @Override
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(Long orgId, Long compId,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.complaint != NULL"
                + " and wt.complaint.compId = " + compId
                + " and wt.type = 'N'"
                + " and wt.status = 'Y' ");
        if (codIdOperLevel1 != null)
            builder.append(" and wt.codIdOperLevel1 = " + codIdOperLevel1);
        if (codIdOperLevel2 != null)
            builder.append(" and wt.codIdOperLevel2 = " + codIdOperLevel2);
        if (codIdOperLevel3 != null)
            builder.append(" and wt.codIdOperLevel3 = " + codIdOperLevel3);
        if (codIdOperLevel4 != null)
            builder.append(" and wt.codIdOperLevel4 = " + codIdOperLevel4);
        if (codIdOperLevel5 != null)
            builder.append(" and wt.codIdOperLevel5 = " + codIdOperLevel5);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if(list.isEmpty()) {
     	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }

    
	@Override
    public WorkflowMas getServiceWorkFlowForWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.department.dpDeptid = " + deptId
                + " and wt.complaint is NULL"
                + " and wt.service.smServiceId = " + serviceId
                + " and wt.type = 'N'"
                + " and wt.status = 'Y' ");
        if (amount != null)
            builder.append(" and wt.fromAmount <= " + amount + " and wt.toAmount >= " + amount);
        if (codIdOperLevel1 != null && codIdOperLevel1 > 0d)
            builder.append(" and wt.codIdOperLevel1 = " + codIdOperLevel1);
        if (codIdOperLevel2 != null && codIdOperLevel2 > 0d)
            builder.append(" and wt.codIdOperLevel2 = " + codIdOperLevel2);
        if (codIdOperLevel3 != null && codIdOperLevel3 > 0d)
            builder.append(" and wt.codIdOperLevel3 = " + codIdOperLevel3);
        if (codIdOperLevel4 != null && codIdOperLevel4 > 0d)
            builder.append(" and wt.codIdOperLevel4 = " + codIdOperLevel4);
        if (codIdOperLevel5 != null && codIdOperLevel5 > 0d)
            builder.append(" and wt.codIdOperLevel5 = " + codIdOperLevel5);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
       if(list.isEmpty()) {
    	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
       }	     
        return list.isEmpty() == true ? null : list.get(0);
    }

    @Override
    public WorkflowMas getServiceWorkFlowForAllWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.department.dpDeptid = " + deptId
                + " and wt.service.smServiceId = " + serviceId
                + " and wt.complaint is NULL"
                + " and wt.type = 'A'"
                + " and wt.status = 'Y'");

        if (sourceOfFund != null && sourceOfFund != 0)
            builder.append(" and wt.wmSchCodeId1=" + sourceOfFund);

        
        
        if (amount != null)
            builder.append(" and wt.fromAmount <= " + amount + " and wt.toAmount >= " + amount);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if(list.isEmpty()) {
     	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }

    
    
    // NEW METHOD ADDEDD FOR getwmschcodeid2
    @Override
    public WorkflowMas getServiceWorkFlowForAllWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long getwmschcodeid2) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.department.dpDeptid = " + deptId
                + " and wt.service.smServiceId = " + serviceId
                + " and wt.wmSchCodeId2= " + getwmschcodeid2
                + " and wt.complaint is NULL"
                + " and wt.type = 'A'"
                + " and wt.status = 'Y'");

        if (sourceOfFund != null && sourceOfFund != 0)
            builder.append(" and wt.wmSchCodeId1=" + sourceOfFund);

        if (getwmschcodeid2 != null && getwmschcodeid2 != 0)
            builder.append(" and wt.wmSchCodeId2=" + getwmschcodeid2);

        
        if (amount != null)
            builder.append(" and wt.fromAmount <= " + amount + " and wt.toAmount >= " + amount);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if(list.isEmpty()) {
     	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }
    
	public Long getTaskIdByAppIdAndOrgId(Long appId, Long orgId) {
		BigInteger taskId = null;
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		boolean moalFlag = false;
		final StringBuilder queryString = new StringBuilder();
		queryString.append(
				"SELECT wt.TASK_ID from tb_workflow_task wt where wt.SMFACTION='LoiPayment.html' AND wt.TASK_STATUS <> 'EXITED' AND wt.APM_APPLICATION_ID=:appId ");
		LookUp multiOrgDataList = null;
		try {
			LOGGER.error("Fetching MOAL prefix");
			multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL, MainetConstants.ENV,
					org);
			LOGGER.error("MOAL prefix is available");
		} catch (Exception e) {
			LOGGER.error("No Prefix found for ENV(MOAL)");
		}

		if (!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField())
				&& StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
			queryString.append("and wt.ORGID=:orgId ");
			moalFlag = true;
			LOGGER.error("Appending org id in query: " + orgId);
		}
		LOGGER.error("Final Query: " + queryString.toString());
		final Query query = createNativeQuery(queryString.toString());
		query.setParameter("appId", appId);
		if (moalFlag == true) {
			LOGGER.error("Setting orgId parameter in  Query setParameter field: " + queryString.toString());
			query.setParameter("orgId", orgId);
		}
		try {
			taskId = (BigInteger) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Loi payment task exited " + queryString.toString());
		}
		if (taskId != null) {
			return taskId.longValue();
		} else {
			return null;
		}

	}

    @Override
    public List<Object[]> fetchClosedTaskList(TaskSearchRequest taskSearchRequest) {
        StringBuilder builder = new StringBuilder();
        builder.append(" Select A.* from (SELECT  distinct\r\n" + 
        		" cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,\r\n" + 
        		"  'Certificate' Type FROM tb_workflow_request e,\r\n" + 
        		"  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  tb_services_mst srv\r\n" + 
        		"  WHERE srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID\r\n" + 
        		"  AND srv.SM_PRINT_RESPONS = (SELECT CPD_ID FROM tb_comparam_det where CPM_ID = (SELECT CPM_ID FROM TB_COMPARAM_MAS WHERE CPM_PREFIX = 'PRN') AND CPD_VALUE = 'C')\r\n" + 
        		"  AND e.apm_application_id = cfc.APM_APPLICATION_ID\r\n" + 
        		"  AND cfc.ISSUED_BY IS NULL"+
        		"  AND e.Status= " +'"'+taskSearchRequest.getStatus()+'"' + 
        		"  AND cfc.orgId = "+taskSearchRequest.getOrgId()+ 
        		"  UNION\r\n" + 
        		"  SELECT  distinct\r\n" + 
        		"  cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,\r\n" + 
        		"  'LOI' Type  FROM \r\n" + 
        		"  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  TB_SERVICES_MST srv,\r\n" + 
        		"  TB_LOI_MAS lm\r\n" + 
        		"  WHERE  srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID \r\n" + 
        		"  AND lm.apm_application_id = cfc.APM_APPLICATION_ID\r\n" + 
        		"  AND lm.ISSUED_BY IS NULL  \r\n" + 
        		"  AND cfc.orgId = "+ taskSearchRequest.getOrgId() +
        		"  UNION\r\n" + 
        		"  SELECT  distinct\r\n" + 
        		"  cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,\r\n" + 
        		"  'WorkOrder' Type  FROM\r\n" + 
        		"  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  TB_SERVICES_MST srv,\r\n" + 
        		"  TB_WORK_ORDER wd\r\n" + 
        		"  WHERE  srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID \r\n" + 
        		"  AND wd.WO_APPLICATION_ID = cfc.APM_APPLICATION_ID\r\n" + 
        		"  AND wd.ISSUED_BY IS NULL  \r\n" + 
        		"  AND cfc.orgId = "+ taskSearchRequest.getOrgId() +")A order by APM_APPLICATION_DATE desc");

        Query query = this.createNativeQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<Object[]> objList = query.getResultList();
        return objList;
    }
    
    
    @Override
    public List<Object[]> fetchIssuedTaskList(TaskSearchRequest taskSearchRequest) {
        StringBuilder builder = new StringBuilder();
        builder.append(" Select A.* from (SELECT  distinct\r\n" + 
        		" cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,cfc.ISSUED_DATE,\r\n" + 
        		"  'Certificate' Type FROM \r\n" + 
        		"  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  tb_services_mst srv\r\n" + 
        		"  WHERE srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID\r\n" + 
        		"  AND srv.SM_PRINT_RESPONS = (SELECT CPD_ID FROM tb_comparam_det where CPM_ID = (SELECT CPM_ID FROM TB_COMPARAM_MAS WHERE CPM_PREFIX = 'PRN') AND CPD_VALUE = 'C')\r\n" + 
        		"  AND cfc.ISSUED_BY  IS NOT NULL   "
        		+ "AND cfc.orgId = "+ taskSearchRequest.getOrgId() +
        		"  UNION\r\n" + 
        		"  SELECT  distinct\r\n" + 
        		"  cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,lm.ISSUED_DATE,\r\n" + 
        		"  'LOI' Type FROM  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  TB_SERVICES_MST srv,\r\n" + 
        		"  TB_LOI_MAS lm\r\n" + 
        		"  WHERE  srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID \r\n" + 
        		"  AND lm.apm_application_id = cfc.APM_APPLICATION_ID\r\n" + 
        		"  AND lm.ISSUED_BY  IS NOT NULL  \r\n" + 
        		"  AND "+ taskSearchRequest.getOrgId() + 
        		"  UNION\r\n" + 
        		"  SELECT  distinct\r\n" + 
        		"  cfc.APM_APPLICATION_ID AS APM_APPLICATION_ID,  cfc.ORGID AS ORGID, srv.CDM_DEPT_ID AS DP_DEPTID,\r\n" + 
        		" (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_DEPTDESC,\r\n" + 
        		" (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = srv.CDM_DEPT_ID) AS DP_NAME_MAR,\r\n" + 
        		"  srv.SM_SERVICE_ID AS SM_SERVICE_ID,srv.SM_SERVICE_NAME, srv.SM_SERVICE_NAME_MAR, cfc.APM_FNAME, cfc.APM_MNAME,cfc.APM_LNAME, cfc.APM_APPLICATION_DATE,wd.ISSUED_DATE,\r\n" + 
        		"  'WorkOrder' Type FROM\r\n" + 
        		"  TB_CFC_APPLICATION_MST cfc,\r\n" + 
        		"  TB_SERVICES_MST srv,\r\n" + 
        		"  TB_WORK_ORDER wd WHERE  srv.SM_SERVICE_ID = cfc.SM_SERVICE_ID \r\n" + 
        		"  AND wd.WO_APPLICATION_ID = cfc.APM_APPLICATION_ID\r\n" + 
        		"  AND wd.ISSUED_BY IS NOT NULL  \r\n" + 
        		"  AND "+ taskSearchRequest.getOrgId() + ")A order by ISSUED_DATE desc");

        Query query = this.createNativeQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<Object[]> objList = query.getResultList();
        return objList;
    }
    

   
    //D#127111
    @Override
    public List<Object[]> findComplaintTaskForEmployee(TaskSearchRequest taskSearchRequest) {
        StringBuilder builder = new StringBuilder();
        builder.append("  SELECT\r\n" + 
                "        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" + 
                "        a.ORGID AS ORGID,\r\n" + 
                "        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
                "        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
                "        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
                "        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
                "        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
                "        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
                "        c.EVENT_ID AS EVENT_ID,\r\n" + 
                "        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
        
        "        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
                "        a.TASK_ID AS TASK_ID,\r\n" + 
                "        a.TASK_NAME AS TASK_NAME,\r\n" + 
                "        c.SMFACTION AS SMFACTION,\r\n" + 
                "        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
                "        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" + 
                "        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
                "        e.LAST_DECISION AS DECISION,\r\n" + 
                "        a.EMPID AS EMPID,\r\n" + 
                "        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
                "        e.WORFLOW_TYPE_ID AS WORFLOW_TYPE_ID,\r\n" + 
                "        c.TASK_SLA_DURATION AS TASK_SLA_DURATION,\r\n" +
                "            c.WFTASK_ASSIGDATE as WFTASK_ASSIGDATE,\r\n" +
                "            c.WFTASK_ASSIGDATE as WFTASK_COMPDATE,\r\n" +
                "			 re.APA_MOBILNO as Mobile_No,\r\n"+
                "            re.COMPLAINTDESC as Complaint_Desc,\r\n"+
                "(SELECT case when group_concat(distinct DMS_DOC_ID) is not null then group_concat(ATT_PATH,':',ATT_FNAME,':',DMS_DOC_ID)\r\n" +
				"			else group_concat(ATT_FNAME) end\r\n"+
				"			FROM tb_attach_cfc\r\n"+
				"			WHERE application_id=cr.APM_APPLICATION_ID\r\n"+
				"			GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path\r\n"+
                "     FROM tb_workflow_request e,\r\n" + 
                "         tb_workflow_task c,\r\n" + 
                "         tb_workflow_action a,\r\n" + 
                "		  tb_care_request cr, \r\n"+
                "         complaintRegister re"+
                "    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
                "            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID \r\n"
                + "                     AND a.empId =:empId  and c.task_Status=:status \r\n"
                + "                     AND a.orgId =:orgId  and cr.REFERENCE_Mode =:referenceMode AND cr.APM_APPLICATION_ID = c.APM_APPLICATION_ID "+
                "                       AND re.APM_APPLICATION_ID= c.APM_APPLICATION_ID"+
                "           order by c.WFTASK_ASSIGDATE desc \r\n ");

        
        final Query query = createNativeQuery(builder.toString());
        query.setParameter("empId", taskSearchRequest.getEmpId());
        query.setParameter("status", taskSearchRequest.getStatus());
        query.setParameter("orgId", taskSearchRequest.getOrgId());
        //#161047
        query.setParameter("referenceMode", taskSearchRequest.getReferenceMode());
        
        LOGGER.error("ReferenceMode : " + taskSearchRequest.getReferenceMode());
        
        @SuppressWarnings("unchecked")
        List<Object[]> objList = query.getResultList();
        return objList;
    }
    
    @Override
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndExtForWardZone(Long orgId, Long extIdentifier,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, Long serviceId) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.extIdentifier != NULL"
                + " and wt.extIdentifier = " + extIdentifier
                + " and wt.type = 'N'"
                + " and wt.status = 'Y' ");
        if (codIdOperLevel1 != null)
            builder.append(" and wt.codIdOperLevel1 = " + codIdOperLevel1);
        if (codIdOperLevel2 != null)
            builder.append(" and wt.codIdOperLevel2 = " + codIdOperLevel2);
        if (codIdOperLevel3 != null)
            builder.append(" and wt.codIdOperLevel3 = " + codIdOperLevel3);
        if (codIdOperLevel4 != null)
            builder.append(" and wt.codIdOperLevel4 = " + codIdOperLevel4);
        if (codIdOperLevel5 != null)
            builder.append(" and wt.codIdOperLevel5 = " + codIdOperLevel5);
        if(serviceId !=null)
        	builder.append(  "and wt.service.smServiceId = " + serviceId);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if(list.isEmpty()) {
     	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }
    
    
    @Override
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndWmsForWardZone(Long orgId, Long getwmschcodeid2,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, Long serviceId,BigDecimal amount) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.wmSchCodeId2= " + getwmschcodeid2
                + " and wt.type = 'N'"
                + " and wt.status = 'Y' ");
        if (codIdOperLevel1 != null)
            builder.append(" and wt.codIdOperLevel1 = " + codIdOperLevel1);
        if (codIdOperLevel2 != null)
            builder.append(" and wt.codIdOperLevel2 = " + codIdOperLevel2);
        if (codIdOperLevel3 != null)
            builder.append(" and wt.codIdOperLevel3 = " + codIdOperLevel3);
        if (codIdOperLevel4 != null)
            builder.append(" and wt.codIdOperLevel4 = " + codIdOperLevel4);
        if (codIdOperLevel5 != null)
            builder.append(" and wt.codIdOperLevel5 = " + codIdOperLevel5);
        if(serviceId !=null)
        	builder.append(  "and wt.service.smServiceId = " + serviceId);
        if (amount != null)
            builder.append(" and wt.fromAmount <= " + amount + " and wt.toAmount >= " + amount);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if(list.isEmpty()) {
     	   LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }
    
    @Override
	@Transactional
	public void insertIntoTbWorkflowAction(UserTaskDTO userTaskDto, WorkflowTaskAction actionDto, Long taskId, Long wfTaskId){
    	
		StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO tb_workflow_action (WORKFLOW_ACT_ID, WORKFLOW_REQ_ID, WFTASK_ID, APM_APPLICATION_ID, REFERENCE_ID, DATE_OF_ACTION, TASK_ID, TASK_NAME, TASK_SLA_DURATION, COMMENTS, DECISION, EMPID, WORKFLOW_ATT, ORGID, CREATED_BY, CREATED_DATE) (SELECT FN_JAVA_SQ_GENERATION('COM','TB_WORKFLOW_ACTION','WORKFLOW_ACT_ID',NULL,NULL),");
        builder.append(":workflowReqId,:wfTaskID, :appId, :refId, :dateOfAction,:taskId, :taskName ,:taskSlaDuration , :comments, :decision, :empId, :workflowAttId, :orgId, :createdBy, :createdDate)");
        final Query query = createNativeQuery(builder.toString());
        LOGGER.error("Query is: " +query.toString());
        query.setParameter("workflowReqId", userTaskDto.getWorkflowReqId());
        query.setParameter("wfTaskID", wfTaskId);
        query.setParameter("appId", userTaskDto.getApplicationId());
        query.setParameter("refId", userTaskDto.getReferenceId());
        query.setParameter("dateOfAction", actionDto.getDateOfAction());
        query.setParameter("taskId", taskId);
        query.setParameter("taskName", userTaskDto.getTaskName());
        query.setParameter("taskSlaDuration", userTaskDto.getTaskSlaDurationInMS());
        query.setParameter("comments", actionDto.getComments());
        query.setParameter("decision", actionDto.getDecision());
        query.setParameter("empId", userTaskDto.getActorId());
        query.setParameter("workflowAttId", actionDto.getAttachementId());
        query.setParameter("orgId", userTaskDto.getOrgId());
        query.setParameter("createdBy", userTaskDto.getCreatedBy());
        query.setParameter("createdDate", actionDto.getModifiedDate());
        query.executeUpdate();
        

        LOGGER.error("Executed Successfully");
	}
    
    @Override
    @Transactional
    public String getWftaskid() {
        StringBuilder sb1 = new StringBuilder();
        sb1.append("SELECT FN_JAVA_SQ_GENERATION('COM','TB_WORKFLOW_TASK','WFTASK_ID',NULL,NULL)");
        Query query1 = this.createNativeQuery(sb1.toString());
        Object wftaskid = query1.getSingleResult();
        LOGGER.error("Updated WfTaskId " + wftaskid.toString());
		return wftaskid.toString();
    }
    
    @Override
	@Transactional
	public void insertIntoTbWorkflowTask(UserTaskDTO userTaskDto, WorkflowTaskAction actionDto, Long taskId, Long wfTaskId){
    	
    	StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO tb_workflow_task (WFTASK_ID, WORKFLOW_REQ_ID, TASK_ID, TASK_NAME, TASK_STATUS, APM_APPLICATION_ID, REFERENCE_ID, WF_ID, DP_DEPTID, DP_DEPTDESC, DP_NAME_MAR, SM_SERVICE_ID, SM_SERVICE_NAME, SM_SERVICE_NAME_MAR, EVENT_ID, SMFACTION, WFTASK_ACTORID, WFTASK_ESCALLEVEL, TASK_SLA_DURATION, WFTASK_CCHEKLEVEL, WFTASK_CCHEKGROUP, WFTASK_ASSIGDATE, WFTASK_COMPDATE, ORGID, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE) VALUES");
        builder.append("(:wfTaskID, :workflowReqId, :taskId,:taskName, :taskStatus, :appId, :refId, :wfId, :deptId, :deptDesc, :deptNameReg, :serviceId, :serviceName, :serviceNameReg, :eventId, :smfAction, :actorId, :escalationLevel, :slaDuration, :checkerLevel, :checkerGroup, :assignDate, :compDate, :orgId, :createdBy, :createdDate, :updatedBy, :updatedDate)");
        final Query query = createNativeQuery(builder.toString());
        LOGGER.error("Query is: " +query.toString());
        query.setParameter("wfTaskID", wfTaskId);
        query.setParameter("workflowReqId", userTaskDto.getWorkflowReqId());
        query.setParameter("taskId", taskId);
        query.setParameter("taskName", userTaskDto.getTaskName());
        query.setParameter("taskStatus", userTaskDto.getTaskStatus());
        query.setParameter("appId", userTaskDto.getApplicationId());
        query.setParameter("refId", userTaskDto.getReferenceId());
        query.setParameter("wfId", userTaskDto.getWorkflowId());
        query.setParameter("deptId", userTaskDto.getDeptId());
        query.setParameter("deptDesc", userTaskDto.getDeptName());
        query.setParameter("deptNameReg", userTaskDto.getDeptNameReg());
        query.setParameter("serviceId", userTaskDto.getServiceId());
        query.setParameter("serviceName", userTaskDto.getServiceName());
        query.setParameter("serviceNameReg", userTaskDto.getServiceNameReg());
        query.setParameter("eventId", userTaskDto.getServiceEventId());
        /*query.setParameter("smfName", userTaskDto.getServiceEventName());
        query.setParameter("smfNameReg", userTaskDto.getServiceEventNameReg());*/
        query.setParameter("smfAction", userTaskDto.getServiceEventUrl());
        query.setParameter("actorId", userTaskDto.getActorId());
        /*query.setParameter("roleId", userTaskDto.getActorRole());*/
        query.setParameter("escalationLevel", userTaskDto.getCurrentEscalationLevel());
        query.setParameter("slaDuration", userTaskDto.getTaskSlaDurationInMS());
        query.setParameter("checkerLevel", userTaskDto.getCurentCheckerLevel());
        query.setParameter("checkerGroup", userTaskDto.getCurrentCheckerGroup());
        query.setParameter("assignDate", actionDto.getCreatedDate());
        query.setParameter("compDate", userTaskDto.getDateOfCompletion());
        query.setParameter("orgId", userTaskDto.getOrgId());
        query.setParameter("createdBy", userTaskDto.getCreatedBy());
        query.setParameter("createdDate", actionDto.getModifiedDate());
        query.setParameter("updatedBy", userTaskDto.getModifiedBy());
        query.setParameter("updatedDate", userTaskDto.getModifiedDate());
        int executeUpdate = query.executeUpdate();
        
        LOGGER.error("Task: " +executeUpdate);
        LOGGER.error("Executed Successfully");
	}
    
    @Override
	@Transactional
	public void updateTbWorkflowTask(UserTaskDTO userTaskDTO){
    	Date date = new Date();
		StringBuilder builder = new StringBuilder();
        builder.append("UPDATE tb_workflow_task SET");
        builder.append(" TASK_STATUS = '" + userTaskDTO.getTaskStatus()+"'");
        builder.append(" , WFTASK_COMPDATE = :date");
        builder.append(" , UPDATED_BY = '" + userTaskDTO.getModifiedBy()+"'");
        builder.append(" , UPDATED_DATE =:date");
        builder.append(" WHERE WFTASK_ID =" + userTaskDTO.getWorkflowTaskId());
        final Query query = createNativeQuery(builder.toString());
        query.setParameter("date", date);
        LOGGER.error("Update task query: " +builder.toString());
        query.executeUpdate();

	}
    
    @Override
	@Transactional
	public void updateTbWorkflowAction(UserTaskDTO userTaskDTO, WorkflowTaskAction actionDto){
		StringBuilder builder = new StringBuilder();
        builder.append("UPDATE tb_workflow_action SET");
        builder.append(" DECISION = '" + actionDto.getDecision()+"'");
        builder.append(" , COMMENTS = '" + actionDto.getComments()+"'");
        builder.append(" WHERE WFTASK_ID =" + userTaskDTO.getWorkflowTaskId());
        builder.append(" AND EMPID =" + userTaskDTO.getModifiedBy());
        final Query query = createNativeQuery(builder.toString());
        query.executeUpdate();

	}
    
    @Override
	@Transactional
	public void updateTbWorkflowTaskForHistory(UserTaskDTO userTaskDTO, Long taskId){
    	Date date = new Date();
		StringBuilder builder = new StringBuilder();
        builder.append("UPDATE tb_workflow_task SET");
        builder.append(" TASK_ID = :taskId");
        builder.append(" , TASK_STATUS = :taskStatus");
        builder.append(" , WFTASK_ASSIGDATE = :date");
        builder.append(" , WFTASK_COMPDATE = :date");
        builder.append(" , CREATED_DATE = :date");
        builder.append(" , UPDATED_BY = '" + userTaskDTO.getActorId()+"'");
        builder.append(" , UPDATED_DATE =:date");
        builder.append(" WHERE WFTASK_ID =" + userTaskDTO.getWorkflowTaskId());
        final Query query = createNativeQuery(builder.toString());
        query.setParameter("taskId", taskId);
        query.setParameter("taskStatus", "COMPLETED");
        query.setParameter("date", date);
        LOGGER.error("Update task query: " +builder.toString());
        query.executeUpdate();

	}
    
    @Override
	@Transactional
	public void updateTbWorkflowActionForHistory(UserTaskDTO userTaskDTO, WorkflowTaskAction actionDto, Long taskId){
    	Date date = new Date();
		StringBuilder builder = new StringBuilder();
        builder.append("UPDATE tb_workflow_action SET");
        builder.append(" DATE_OF_ACTION = :date");
        builder.append(" , TASK_ID = :taskId");
        builder.append(" , DECISION = :decision");
        builder.append(" , COMMENTS = '" + actionDto.getComments()+"'");
        builder.append(" , CREATED_DATE = :date");
        builder.append(" WHERE WFTASK_ID =" + userTaskDTO.getWorkflowTaskId());
        builder.append(" AND EMPID =" + actionDto.getEmpId());
        final Query query = createNativeQuery(builder.toString());
        query.setParameter("date", date);
        query.setParameter("taskId", taskId);
        query.setParameter("decision", "FORWARD_FOR_CLARIFICATION");
        query.executeUpdate();

	}
    
	@Override
	public WorkflowMas getWorkFlowByDivWardZoneAndConnSizeSpecification(Long orgId, Long deptId, Long serviceId,
			BigDecimal connectionSize, Long ward) {
		
		 StringBuilder builder = new StringBuilder();
	        builder.append("SELECT wt FROM WorkflowMas wt"
	                + " where wt.organisation.orgid = " + orgId
	                + " and wt.department.dpDeptid = " + deptId
	                + " and wt.service.smServiceId = " + serviceId
	                + " and wt.complaint is NULL"
	                + " and wt.status = 'Y'");

	        if (connectionSize != null) {
	            builder.append(" and wt.fromAmount <= " + connectionSize + " and wt.toAmount >= " + connectionSize);
	        }
	      
		
		  if (ward != null) { 
			  builder.append(" and wt.codIdOperLevel1 = " + ward); 
		  }
		 	        Query query = this.createQuery(builder.toString());
	        
	      List<WorkflowMas> list = query.getResultList();
		  if(list.isEmpty()) {
			  LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
			  throw new FrameworkException("Workflow not found for the specified combination");
		  }	     
		  
		  return list.isEmpty() == true ? null : list.get(0);
			
	}

}
