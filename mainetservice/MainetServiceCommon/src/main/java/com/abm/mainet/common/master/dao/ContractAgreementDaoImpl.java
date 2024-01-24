package com.abm.mainet.common.master.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;

@Repository
public class ContractAgreementDaoImpl extends AbstractDAO<Long> implements IContractAgreementDao {

	private static Logger logger = Logger.getLogger(ContractAgreementDaoImpl.class);

	@Override
	public List<Object[]> getContractAgreementSummary(final Long orgId, final String contractNo,
			final Date contractDate, final Long deptId, final Long venderId, final String viewClosedCon,
			final String contRenewal) {

		String queryString = "	select  c.contId,c.contNo,dept.dpDeptdesc,c.contDate,d.contFromDate,d.contToDate, p2.contp2Name,ven.vmVendorname,c.contTndNo,c.loaNo,d.contAmount from "
				+ " ContractMastEntity c ,ContractDetailEntity d,ContractPart1DetailEntity p1 ,ContractPart2DetailEntity p2, Department dept,TbAcVendormasterEntity ven "
				+ " where c.contId=d.contId and c.contId=p1.contId and c.contId=p2.contId and dept.dpDeptid=c.contDept "
				+ " and ven.vmVendorid=p2.vmVendorid and p1.contp1Type='U' and p2.contp2Type='V' and c.contActive!='N' and d.contdActive='Y' "
				+ " and p2.contp2Primary='Y' and p1.contp1Active='Y' and p2.contvActive='Y'  and c.orgId=:orgId";

		if (contractNo != null && !contractNo.isEmpty()) {
			queryString += " and c.contNo =:contractNo";
		}

		if (contractDate != null) {
			queryString += " and c.contDate =:contractDate";
		}

		if (deptId != null) {
			queryString += " and c.contDept =:deptId";
		}

		if (venderId != null) {
			queryString += " and p2.vmVendorid =:venderId";
		}
		if (!StringUtils.isEmpty(viewClosedCon)) {
			queryString += " and c.contCloseFlag =:contCloseFlag";
		}
		if (!StringUtils.isEmpty(contRenewal)) {
			queryString += " and c.contRenewal =:contRenewal";
		}

		final Query query = createQuery(queryString);
		query.setParameter("orgId", orgId);
		if (contractNo != null && !contractNo.isEmpty()) {
			query.setParameter("contractNo", contractNo);
		}
		if (contractDate != null) {
			query.setParameter("contractDate", contractDate);
		}
		if (deptId != null) {
			query.setParameter("deptId", deptId);

		}
		if (venderId != null) {
			query.setParameter("venderId", venderId);
		}
		if (!StringUtils.isEmpty(viewClosedCon)) {
			query.setParameter("contCloseFlag", viewClosedCon);
		}
		// D#90462
		if (!StringUtils.isEmpty(contRenewal)) {
			query.setParameter("contRenewal", contRenewal);
		}
		final List<Object[]> list = query.getResultList();

		return list;
	}

	@Override
	public List<Object[]> findPrintContractAgreementByContId(final Long orgId, final Long contId) {
		final String queryString = "select o.ONlsOrgname,o.oNlsOrgnameMar,o.orgShortNm,cm.contId,cm.contTndNo,cm.contTndDate,cm.contRsoNo,cm.contRsoDate,"
				+ "cd.contFromDate, cd.contToDate,cd.contAmount, ep.empname ,de.dsgname from ContractMastEntity cm ,ContractDetailEntity cd,ContractPart1DetailEntity p1 ,"
				+ "ContractPart2DetailEntity p2 ,Organisation o , Employee ep , Designation de "
				+ "where o.orgid= ?1 and  cm.contId = ?2 and o.orgid= p1.orgId and o.orgid = p2.orgId and p1.contId.contId= p2.contId.contId"
				+ " and cm.contId=p1.contId.contId  and cm.contId = cd.contId.contId and de.dsgid = p1.dsgid and ep.empId=p1.empid "
				+ "and cd.contdActive='Y' and p1.contp1Type='U' and p1.contp1Active='Y' "
				+ "and p2.contp2Type='V' and p2.contvActive='Y' and p2.contp2Primary='Y' ";

		final Query query = createQuery(queryString);
		query.setParameter(1, orgId);
		query.setParameter(2, contId);
		@SuppressWarnings("unchecked")
		final List<Object[]> list = query.getResultList();

		return list;
	}

	@Override
	public List<Object[]> findByContractNo(final Long orgId, final String contNo) {

		final String queryString = "select c.contId,c.contNo,dept.dpDeptdesc,c.contDate,d.contFromDate,d.contToDate, p1.contp1Name,ven.vmVendorname,ven.vmVendoradd,ven.emailId,ven.mobileNo,d.contAmount , p2.contp2Name,dept.dpDeptcode from "
				+ " ContractMastEntity c ,ContractDetailEntity d,ContractPart1DetailEntity p1 ,ContractPart2DetailEntity p2, Department dept,TbAcVendormasterEntity ven "
				+ " where c.contId=d.contId and c.contId=p1.contId and c.contId=p2.contId and dept.dpDeptid=c.contDept "
				+ " and ven.vmVendorid=p2.vmVendorid and p1.contp1Type='U' and p2.contp2Type='V' and c.contActive!='N' "
				+ " and p2.contp2Primary='Y' and p1.contp1Active='Y' and p2.contvActive='Y' and c.orgId= ?1 and c.contNo = ?2";

		final Query query = createQuery(queryString);
		query.setParameter(1, orgId);
		query.setParameter(2, contNo);
		@SuppressWarnings("unchecked")
		final List<Object[]> list = query.getResultList();

		return list;
	}


	@Override
	public List<Object[]> getBillDetailbyContAndMobile(String contNo, String mobileNo, Long orgId) {
		List<Object[]> entityList = null;
		try {

			StringBuilder hql = new StringBuilder(
					"select c.contId,c.contNo,dept.dpDeptdesc,c.contDate,d.contFromDate,d.contToDate, p1.contp1Name,ven.vmVendorname,ven.vmVendoradd,ven.emailId,ven.mobileNo,d.contAmount , p2.contp2Name,dept.dpDeptcode from "
			        +" ContractMastEntity c ,ContractDetailEntity d,ContractPart1DetailEntity p1 ,ContractPart2DetailEntity p2, Department dept,TbAcVendormasterEntity ven "
			        +" where c.contId=d.contId and c.contId=p1.contId and c.contId=p2.contId and dept.dpDeptid=c.contDept "
			        + " and ven.vmVendorid=p2.vmVendorid and p1.contp1Type='U' and p2.contp2Type='V' and c.contActive='Y' "
			        + " and p2.contp2Primary='Y' and p1.contp1Active='Y' and p2.contvActive='Y' and c.orgId= :orgId ");
			
			
			if (StringUtils.isNotBlank(contNo)) {
				hql.append("  and c.contNo = :contNo");
			}
			
			if (StringUtils.isNotEmpty(mobileNo)) {
				hql.append(" and ven.mobileNo = :mobileNo ");
			}
			
			final Query query = createQuery(hql.toString());
			
			if(orgId!=null)
			query.setParameter("orgId", orgId);
			if (StringUtils.isNotBlank(contNo)) {
				query.setParameter("contNo", contNo);
			} 
			if (StringUtils.isNotEmpty(mobileNo)) {
				query.setParameter("mobileNo", mobileNo );
			}
			
			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception ex) {
			return null;
		}
		return entityList;
	}


	@Override
	public ContractDetailEntity saveUpdateContractDetailEntity(ContractDetailEntity contractDetailEntity) {
		return entityManager.merge(contractDetailEntity);
	}

	@Override
	public void updateContractDetailActiveFlag(Long contdId, Long empId) {
		final StringBuilder hql = new StringBuilder(
				"UPDATE ContractDetailEntity a set a.contdActive='N', a.updatedBy=:empId, a.updatedDate =:CURRENT_DATE WHERE a.contdId=:contdId");
		final Query query = createQuery(hql.toString());
		query.setParameter("contdId", contdId);
		query.setParameter("empId", empId);
		query.setParameter("CURRENT_DATE", new Date());
		query.executeUpdate();
	}

	@Override
	public List<Object[]> getContractFilterData(Long orgId, String contractNo, Date contractDate,
			String viewClosedCon) {
		String queryString = "select c.contId,c.contTndNo,c.loaNo,c.contDate,d.contFromDate,d.contToDate from "
				+ " ContractMastEntity c ,ContractDetailEntity d " +

				" where  d.contId.contId = c.contId and  c.contActive='Y' and d.contdActive='Y' and c.contNo is null and  c.orgId=:orgId";

		if (contractDate != null) {
			queryString += " and c.contDate =:contractDate";
		}

		final Query query = createQuery(queryString);
		query.setParameter("orgId", orgId);

		if (contractDate != null) {
			query.setParameter("contractDate", contractDate);
		}

		final List<Object[]> list = query.getResultList();

		return list;
	}
	
	@Override
	public List<Object[]> getRLContractAgreementSummaryData(final Long orgId, final String contractNo,
			final Date contractDate, final Long deptId, final String viewClosedCon,
			final String contRenewal,final Long estateId, final Long propId) {
		
		
		StringBuilder queryString = new StringBuilder(
				 "select CM.CONT_ID , CM.CONT_NO, DEPT.DP_DEPTDESC, CM.CONT_DATE , CD.CONT_FROM_DATE , CD.CONT_TO_DATE , CP2D.CONTP2_NAME , VEN.VM_VENDORNAME ,\r\n" + 
				 " CM.CONT_TND_NO , CM.LOA_NO, CD.CONT_AMOUNT, EM.ES_ID,EM.ES_NAME_ENG,EM.ES_NAME_REG ,\r\n" + 
				 " PM.PROP_ID, PM.PM_PROPNO, PM.PROP_NAME\r\n" + 
				 "from TB_CONTRACT_MAST CM \r\n" + 
				 "cross join TB_CONTRACT_DETAIL CD \r\n" + 
				 "cross join TB_CONTRACT_PART1_DETAIL CP1D \r\n" + 
				 "cross join TB_CONTRACT_PART2_DETAIL CP2D \r\n" + 
				 "cross join TB_DEPARTMENT DEPT \r\n" + 
				 "cross join TB_VENDORMASTER VEN  \r\n" + 
				 "left join TB_RL_EST_CONTRACT_MAPPING ECM on CM.CONT_ID = ECM.cont_id\r\n" + 
				 "left join TB_RL_ESTATE_MAS EM on ECM.ES_ID=EM.ES_ID left join TB_RL_PROPERTY_MAS PM on ECM.PROP_ID=PM.PROP_ID \r\n" + 
				 "where CM.CONT_ID=CD.CONT_ID and CM.CONT_ID=CP1D.CONT_ID and CM.CONT_ID=CP2D.CONT_ID and DEPT.DP_DEPTID=CM.CONT_DEPT and VEN.VM_VENDORID=CP2D.VM_VENDORID\r\n" + 
				 "and CP1D.CONTP1_TYPE='U' and CP2D.CONTP2_TYPE='V' and CM.CONT_ACTIVE='Y' and CD.CONTD_ACTIVE='Y' and CP2D.CONTP2_PRIMARY='Y' and CP1D.CONTP1_ACTIVE='Y' and CP2D.CONTV_ACTIVE='Y' and CM.ORGID=:orgId");
		
		if (contractNo != null && !contractNo.isEmpty()) {
			queryString.append(" and CM.CONT_NO =:contractNo ");	
		}

		if (contractDate != null) {
			queryString.append(" and CM.CONT_DATE =:contractDate ");
		}

		if (deptId != null) {
			queryString.append(" and CM.CONT_DEPT =:deptId ");
		}

		if (!StringUtils.isEmpty(viewClosedCon)) {
			queryString.append(" and CM.CONT_CLOSE_FLAG =:contCloseFlag ");
		}
		
		if (!StringUtils.isEmpty(contRenewal)) {
			queryString.append(" and CM.CONT_RENEWAL =:contRenewal ");
		}
		
		if (estateId != null) {
			queryString.append(" and EM.ES_ID =:estateId ");
		}
		
		if (propId != null) {
			queryString.append(" and PM.PROP_ID =:propId ");
		}

		final Query query = createNativeQuery(queryString.toString());
		query.setParameter("orgId", orgId);
		if (contractNo != null && !contractNo.isEmpty()) {
			query.setParameter("contractNo", contractNo);
		}
		if (contractDate != null) {
			query.setParameter("contractDate", contractDate);
		}
		if (deptId != null) {
			query.setParameter("deptId", deptId);

		}
		if (!StringUtils.isEmpty(viewClosedCon)) {
			query.setParameter("contCloseFlag", viewClosedCon);
		}
		// D#90462
		if (!StringUtils.isEmpty(contRenewal)) {
			query.setParameter("contRenewal", contRenewal);
		}
		if (estateId != null) {
			query.setParameter("estateId", estateId);

		}
		if (propId != null) {
			query.setParameter("propId", propId);

		}
		final List<Object[]> list = query.getResultList();

		return list;
	}

	
}
