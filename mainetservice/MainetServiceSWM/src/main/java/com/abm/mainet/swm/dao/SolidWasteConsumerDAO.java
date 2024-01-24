package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;

/**
 * The Class UserRegistrationDAOImpl.
 *
 * @author Lalit.Prusti
 * 
 *         Created Date : 20-Jun-2018
 */
@Repository
public class SolidWasteConsumerDAO extends AbstractDAO<SolidWasteConsumerMaster> implements ISolidWasteConsumerDAO {

    private static final Logger LOGGER = Logger.getLogger(SolidWasteConsumerDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.abm.mainet.swm.dao.UserRegistrationDAO#search(java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    public SolidWasteConsumerMaster search(Long registrationId, String custNumber, String propetyNo, Long mobileNo,
	    Long orgId) {
    	SolidWasteConsumerMaster entity = null;
	Query query = this.createQuery(buildQuery(registrationId, custNumber, propetyNo, mobileNo));
	query.setParameter("orgid", orgId);

	if (registrationId != null) {
	    query.setParameter("registrationId", registrationId);
	}
	if (mobileNo != null) {
	    query.setParameter("mobileNo", mobileNo);
	}
	if (StringUtils.isNotBlank(propetyNo)) {
	    query.setParameter("propetyNo", propetyNo);
	}

	if (StringUtils.isNotBlank(custNumber)) {
	    query.setParameter("custNumber", custNumber);
	}
	
try {
	entity= (SolidWasteConsumerMaster) query.getSingleResult();
}catch (Exception e) {
}
return entity;
	
    }

    /**
     * Builds the query.
     *
     * @param registrationId
     *            the registration id
     * @param custNumber
     *            the cust number
     * @param propetyNo
     *            the propety no
     * @param mobileNo
     *            the mobile no
     * @return the string
     */
    private String buildQuery(Long registrationId, String custNumber, String propetyNo, Long mobileNo) {
	StringBuilder searchQuery = new StringBuilder(
		" SELECT ur FROM SolidWasteConsumerMaster ur  WHERE ur.orgid = :orgid ");

	if (registrationId != null) {
	    searchQuery.append(" AND ur.registrationId = :registrationId ");
	}
	if (mobileNo != null) {
	    searchQuery.append(" AND ur.swMobile = :mobileNo ");
	}


	if (StringUtils.isNotBlank(propetyNo)) {

	    searchQuery.append(" AND ( ur.swOldPropNo = :propetyNo Or ur.swNewPropNo = :propetyNo )");
	}

	if (StringUtils.isNotBlank(custNumber)) {
	    searchQuery.append(" AND ( ur.swNewCustId = :custNumber OR ur.swOldCustId = :custNumber )");
	}

	searchQuery.append(" ORDER BY ur.registrationId DESC ");

	return searchQuery.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SolidWasteConsumerMaster> searchCitizenData(Long zone, Long ward, Long block, Long route, Long house,
	    Long mobileNo, String name, Long orgId) {

	List<SolidWasteConsumerMaster> entity = null;

	try {

	    StringBuilder hql = new StringBuilder(
		    "SELECT ur FROM SolidWasteConsumerMaster ur  where ur.orgid = :orgid");

	    if (zone != null && zone != 0) {
		hql.append(" and ur.swCod1= :zone");
	    }
	    if (ward != null && ward != 0) {
		hql.append(" and ur.swCod2= :ward");
	    }
	    if (block != null && block != 0) {
		hql.append(" and ur.swCod3= :block");
	    }
	    if (route != null && route != 0) {
		hql.append(" and ur.swCod4= :route");
	    }
	    if (house != null && house != 0) {
		hql.append(" and ur.swCod5= :house");
	    }
	    if (StringUtils.isNotBlank(name)) {

		hql.append(" and ur.swName= :name");
	    }
	    if (mobileNo != null) {
		hql.append(" and ur.swMobile= :mobileNo");
	    }

	    hql.append(" ORDER BY ur.registrationId DESC ");
	    final Query query = this.createQuery(hql.toString());
	    

	    query.setParameter("orgid", orgId);

	    if (zone != null && zone != 0) {
		query.setParameter("zone", zone);
	    }
	    if (ward != null && ward != 0) {
		query.setParameter("ward", ward);
	    }
	    if (block != null && block != 0) {
		query.setParameter("block", block);
	    }
	    if (route != null && route != 0) {
		query.setParameter("route", route);
	    }
	    if (house != null && house != 0) {
		query.setParameter("house", house);
	    }
	    if (StringUtils.isNotBlank(name)) {

		query.setParameter("name", name);
	    }
	    if (mobileNo != null) {
		query.setParameter("mobileNo", mobileNo);
	    }
	    try {
	    	 entity = (List<SolidWasteConsumerMaster>) query.getResultList();
	    }catch (Exception e) {
			// TODO: handle exception
		}
	   

	} catch (final Exception exception) {
		throw new FrameworkException("Exception occur fetching citizen data ", exception);

	}
	return entity;

    }

}