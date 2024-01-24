package com.abm.mainet.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bill.service.BillMasterCommonServiceImpl;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CitizenDashboardView;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Repository
public class CitizenDashBoardDAO extends AbstractDAO<Department> implements ICitizenDashBoardDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CitizenDashBoardDAO.class);
	
	 @Autowired
	    private IOrganisationDAO organisationDAO;
	
    @Override
    public List<CitizenDashboardView> getAllApplicationsOfCitizen(CitizenDashBoardReqDTO request) {
    	
    	Organisation org = organisationDAO.getOrganisationById(request.getOrgId(),MainetConstants.STATUS.ACTIVE);
    	boolean moalFlag=false;
        final StringBuilder queryString = new StringBuilder();
        queryString.append("select c "
                + "from CitizenDashboardView c where "
                + " ((c.ServiceType ='NW' and c.empId =:empId) "
                + " or ((c.ServiceType ='W' or c.ServiceType ='D') "
                + " and (c.apmMobileNo = :mobile)))  ");
        
        LookUp multiOrgDataList  = null;
    	try {
    		LOGGER.error("Fetching MOAL prefix");
    		multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL,  MainetConstants.ENV, org);
    		LOGGER.error("MOAL prefix is available");
    	}catch (Exception e) {
    		LOGGER.error("No Prefix found for ENV(MOAL)");
		}
    	
    	if(!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField()) && StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
    		queryString.append("and c.orgId=:orgId ");
    		moalFlag=true;
    		LOGGER.error("Appending org id in query: "+request.getOrgId());
    	}
    	
    	queryString.append("order by c.apmApplicationDate DESC");
    	LOGGER.error("Final Query: "+queryString.toString());
        final Query query = createQuery(queryString.toString());
        query.setParameter("empId", request.getEmpId());
        if(moalFlag==true) {
        LOGGER.error("Setting orgId parameter in  Query setParameter field: "+queryString.toString());
        query.setParameter("orgId", request.getOrgId());
        }
        query.setParameter("mobile", request.getMobileNo());
        @SuppressWarnings("unchecked")
        final List<CitizenDashboardView> allApplications = query.getResultList();
        LOGGER.error("Result Size: "+allApplications.size());
        return allApplications;
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllFaliuredOrCancelledOnlineList(String mobNo, Long orgId) {
		List<Object[]> entityList = new ArrayList<>();

		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		boolean moalFlag = false;
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append(
					"select c.smServiceName, c.smServiceNameMar , c.smShortdesc , d.dpDeptdesc,d.dpNameMar, b.tranCmId ,b.referenceId, b.referenceDate, b.recvStatus, b.orgId "
							+ " from PaymentTransactionMas b, ServiceMaster c, Department d "
							+ " where b.sendPhone=:mobNo  and b.smServiceId=c.smServiceId and c.tbDepartment.dpDeptid=d.dpDeptid and (b.referenceId,b.referenceDate) in (select x.referenceId,max(x.referenceDate) "
							+ " from PaymentTransactionMas x group by referenceId) and b.recvStatus<>'success'");
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
				queryString.append(" and b.orgId=:orgId ");
				moalFlag = true;
				LOGGER.error("Appending org id in query: " + orgId);
			}
			//Defect #125484
			queryString.append(" order by  b.referenceDate DESC");
			LOGGER.error("Final Query: " + queryString.toString());
			final Query query = createQuery(queryString.toString());
			query.setParameter("mobNo", mobNo);
			if (moalFlag == true) {
				LOGGER.error("Setting orgId parameter in  Query setParameter field: " + queryString.toString());
				query.setParameter("orgId", orgId);
			}

			entityList = (List<Object[]>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  getAllFaliuredOrCancelledOnlineList() ", exception);

		}
		return entityList;

	}
	
	@Override
	public List<PaymentTransactionMas> getCitizenPayPendingDataByDateAndStatus(Date validDate, Long serviceId,
			String flatNo, Long orgId,String refNo) {
		List<PaymentTransactionMas> entityList = new ArrayList<>();
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append(
					"select b from PaymentTransactionMas b where b.recvStatus='pending' and date(b.referenceDate)=:payDate and b.orgId=:orgId");

			if (serviceId != null) {
				queryString.append(" and b.smServiceId=:smServiceId");
			}

			if (StringUtils.isNotBlank(flatNo)) {
				queryString.append(" and b.flatNo=:flatNo");
			}
			if (StringUtils.isNotBlank(refNo)) {
				queryString.append(" and b.referenceId=:referenceId");
			}

			// Defect #125484

			queryString.append(" order by  b.referenceDate DESC");
			LOGGER.error("Final Query: " + queryString.toString());
			final Query query = createQuery(queryString.toString());

			query.setParameter("payDate", validDate);

			if (serviceId != null) {
				query.setParameter("smServiceId", serviceId);
			}

			if (StringUtils.isNotBlank(flatNo)) {
				query.setParameter("flatNo", flatNo);

			}
			if (StringUtils.isNotBlank(refNo)) {
				query.setParameter("referenceId", refNo);

			}
			query.setParameter("orgId", orgId);
			entityList = (List<PaymentTransactionMas>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  getAllFaliuredOrCancelledOnlineList() ", exception);

		}
		return entityList;

	}

}
