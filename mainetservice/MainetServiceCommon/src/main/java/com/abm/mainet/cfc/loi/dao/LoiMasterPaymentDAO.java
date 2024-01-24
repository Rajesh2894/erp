package com.abm.mainet.cfc.loi.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class LoiMasterPaymentDAO extends AbstractDAO<TbLoiMasEntity> implements ILoiMasterPaymentDAO {

	private static final Logger LOGGER = Logger.getLogger(LoiMasterPaymentDAO.class);

	@Autowired
	private IOrganisationDAO organisationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.loi.repository.ILoiMasterPaymentDAO#
	 * findLoiMasBySearchDTO(com.abm.mainetservice.web.loi.bean.
	 * LoiPaymentSearchDTO)
	 */
	@Override
	public TbLoiMasEntity findLoiMasBySearchDTO(final LoiPaymentSearchDTO searchDto, final String status) {
		// #120105-> code updated for dashboard data based on env prefix
		Organisation org = organisationDAO.getOrganisationById(searchDto.getOrgId(), MainetConstants.STATUS.ACTIVE);
		boolean moalFlag = false;
		final StringBuilder queryString = new StringBuilder(
				"select distinct m from TbLoiMasEntity m,ServiceMaster b,CitizenDashboardView c where m.loiStatus='A' and m.loiServiceId = b.smServiceId and b.smServiceName = c.smServiceName ");

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
			queryString.append("and m.compositePrimaryKey.orgid=:orgId ");
			moalFlag = true;
			LOGGER.error("Appending org id in query: " + searchDto.getOrgId());
		}
		if (searchDto.getApplicationId() != null) {
			queryString.append(" and m.loiApplicationId=:loiApplicationId");
		}
		if ((searchDto.getLoiNo() != null) && !searchDto.getLoiNo().isEmpty()) {
			queryString.append(" and m.loiNo=:loiNo");
		}
		if (searchDto.getEmpId() != null) {
			queryString.append(" and ( c.empId=:empId");
		}
		if (searchDto.getMobileNo() != null) {
			queryString.append(" or  c.apmMobileNo=:apmMobileNo)");
		}

		final Query queryData = entityManager.createQuery(queryString.toString());
		if (moalFlag == true) {
			LOGGER.error("Setting orgId parameter in  Query setParameter field: " + queryString.toString());
			queryData.setParameter("orgId", searchDto.getOrgId());
		}
		// queryData.setParameter(1, searchDto.getOrgId());
		// queryData.setParameter("status", status);

		if (searchDto.getApplicationId() != null) {
			queryData.setParameter("loiApplicationId", searchDto.getApplicationId());
		}
		if ((searchDto.getLoiNo() != null) && !searchDto.getLoiNo().isEmpty()) {
			queryData.setParameter("loiNo", searchDto.getLoiNo());
		}

		if (searchDto.getEmpId() != null) {
			queryData.setParameter("empId", searchDto.getEmpId());
		}
		if (searchDto.getMobileNo() != null) {
			queryData.setParameter("apmMobileNo", searchDto.getMobileNo());
		}

		@SuppressWarnings("unchecked")
		final List<TbLoiMasEntity> loiMas = queryData.getResultList();
		if ((loiMas != null) && !loiMas.isEmpty()) {
			return loiMas.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.loi.repository.ILoiMasterPaymentDAO#
	 * findLoiMasBySearchDTO(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void updateLoiMaster(final Long loiId, final Long orgid) {
		final Query query = entityManager.createQuery(
				"update  TbLoiMasEntity l set l.loiPaid='Y' where l.compositePrimaryKey.loiId=?1 and l.compositePrimaryKey.orgid=?2");
		query.setParameter(1, loiId);
		query.setParameter(2, orgid);
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.loi.repository.ILoiMasterPaymentDAO#
	 * updateLoiPaidByLoiNo(java.lang.String, long)
	 */
	@Override
	public void updateLoiPaidByLoiNo(final String loiNo, final long orgid) {
		final Query query = entityManager.createQuery(
				"update  TbLoiMasEntity l set l.loiPaid='Y' where l.loiNo=?1 and l.compositePrimaryKey.orgid=?2");
		query.setParameter(1, loiNo);
		query.setParameter(2, orgid);
		query.executeUpdate();
	}

	@Override
	public void updateLoiMasterStatus(final String loiNo, final long orgId, final Long empId,
			final String deleteRemark) {
		final Query query = entityManager.createQuery("update  TbLoiMasEntity l set l.loiStatus='I',l.loiDelRemark=?5"
				+ ",l.updatedBy=?3,l.updatedDate=?4 where l.loiNo=?1 and l.compositePrimaryKey.orgid=?2 ");
		query.setParameter(1, loiNo);
		query.setParameter(2, orgId);
		query.setParameter(3, empId);
		query.setParameter(4, new Date());
		query.setParameter(5, deleteRemark);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findLoiInformation(Long orgId, String mobileNo, Long empId) {
		List<Object[]> entityList = new ArrayList<>();
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		boolean moalFlag = false;
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append(
					"select a.loiApplicationId, b.smServiceName, b.smServiceNameMar,b.smShortdesc, a.loiNo, a.loiRefId, a.loiAmount, a.loiPaid, a.loiServiceId,a.loiDate, c.orgId "
							+ "from TbLoiMasEntity a, ServiceMaster b, CitizenDashboardView c  where a.loiPaid='N' and a.loiStatus='A' "
							+ "and a.loiServiceId = b.smServiceId and b.smServiceName=c.smServiceName  and  a.loiApplicationId = c.apmApplicationId   "
							+ "and (c.empId =:empId or c.apmMobileNo =:mobile)");
			LookUp multiOrgDataList = null;
			try {
				LOGGER.error("Fetching MOAL prefix");
				multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL,
						MainetConstants.ENV, org);
				LOGGER.error("MOAL prefix is available");
			} catch (Exception e) {
				LOGGER.error("No Prefix found for ENV(MOAL)");
			}

			if (!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField())
					&& StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
				queryString.append("and c.orgId=:orgId ");
				moalFlag = true;
				LOGGER.error("Appending org id in query: " + orgId);
			}

			queryString.append("order by c.apmApplicationDate DESC");
			LOGGER.error("Final Query: " + queryString.toString());
			final Query query = createQuery(queryString.toString());
			query.setParameter("empId", empId);
			if (moalFlag == true) {
				LOGGER.error("Setting orgId parameter in  Query setParameter field: " + queryString.toString());
				query.setParameter("orgId", orgId);
			}
			query.setParameter("mobile", mobileNo);

			entityList = (List<Object[]>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  getFilteredNewTradeLicenceList() ", exception);

		}
		return entityList;

	}

	@Override
	public List<TbLoiDetEntity> findLoiDetailsByLoiMasAndOrgId(Long loiId, Long orgId) {
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		boolean moalFlag = false;
		List<TbLoiDetEntity> entityList = new ArrayList<>();
		final StringBuilder queryString = new StringBuilder();
		//Defect #135030 change request dto orgid to master orgid
		queryString.append(
				"select l from TbLoiDetEntity l where l.loiMasId=:loiId and l.compositePrimaryKey.orgid=:orgId");

		final Query query = createQuery(queryString.toString());

		query.setParameter("loiId", loiId);
		query.setParameter("orgId", orgId);
		entityList = query.getResultList();
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getLoiMasData(Long orgid, Long deptId, Long serviceId, Long appId, String loiNo,String apmFname, String apmLname) {
		List<Object[]> entityList = new ArrayList<>();
		final StringBuilder queryString = new StringBuilder();
		try {
		queryString.append(
				"select l.compositePrimaryKey.loiId,l.loiNo,l.loiDate,l.loiServiceId,l.loiApplicationId,l.loiRefId,l.loiAmount,b.smServiceName,b.smServiceNameMar,c.apmFname,c.apmMname,c.apmLname,c.apmApplicationDate from TbLoiMasEntity l, ServiceMaster b,  TbCfcApplicationMstEntity c where l.compositePrimaryKey.orgid=:orgid  and b.smServiceId=l.loiServiceId  and c.apmApplicationId=l.loiApplicationId");

		if (deptId != null && deptId != 0) {
			queryString.append(" and b.tbDepartment.dpDeptid=:deptId");
		} 

		if (serviceId != null && serviceId != 0) {
			queryString.append(" and b.smServiceId=:serviceId");
		}

		if (appId != null && appId != 0) {
			queryString.append(" and l.loiApplicationId=:appId");
		}
		
		if ((loiNo != null) && !loiNo.isEmpty() && !loiNo.equals("0")) {
			queryString.append(" and l.loiNo=:loiNo");
		}
		if (StringUtils.isNotEmpty(apmFname)) {
			queryString.append(" and c.apmFname like '%" + apmFname + "%'");
		}
	
		if (StringUtils.isNotEmpty(apmLname)) {
			queryString.append(" and c.apmLname like '%" + apmLname + "%'");
		}
		queryString.append(" order by l.loiApplicationId desc");
		
		Query query = this.createQuery(queryString.toString());
		query.setParameter("orgid", orgid);

		if (deptId != null && deptId != 0) {
			query.setParameter("deptId", deptId);
		}
		if (serviceId != null && serviceId != 0) {
			query.setParameter("serviceId", serviceId);
		}
		if (appId != null && appId != 0) {
			query.setParameter("appId", appId);
		}
		if ((loiNo != null) && !loiNo.isEmpty() && !loiNo.equals("0")) {
			query.setParameter("loiNo", loiNo);
		}
	
		entityList = (List<Object[]>) query.getResultList();
		}
		catch (Exception e) {
			LOGGER.error("Exception occur while fetchig LOI Details in  getLoiMasData() ", e);

		}
		return entityList;
	}

     //#139419
	 @Override
	 @Transactional
	 public void updateIssuanceDataInLoiMas(Long applicationId, Long issuedBy, Long orgId) {
        final Query query = createQuery("UPDATE TbLoiMasEntity c SET  c.issuedDate=CURRENT_TIMESTAMP, c.issuedBy=?1  where c.loiApplicationId=?2 and c.compositePrimaryKey.orgid=?3");
        query.setParameter(1, issuedBy);
        query.setParameter(2, applicationId);
        query.setParameter(3, orgId);
        query.executeUpdate();
	}
   
}
