package com.abm.mainet.common.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class CFCApplicationAddressDAO extends AbstractDAO<CFCApplicationAddressEntity> implements ICFCApplicationAddressDAO {

	private static final Logger LOGGER = Logger.getLogger(CFCApplicationAddressDAO.class);
	
	 @Autowired
	 private IOrganisationDAO organisationDAO;
    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.dao.ICFCApplicationAddressDAO#getApplicationAddressByAppId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public CFCApplicationAddressEntity getApplicationAddressByAppId(
            final Long loiApplicationId, final Long orgId) {
        CFCApplicationAddressEntity master = new CFCApplicationAddressEntity();
        Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
        boolean moalFlag = false;
        final StringBuilder query = new StringBuilder(
                "select s from CFCApplicationAddressEntity s where  s.apmApplicationId = ?2 ");
        LookUp multiOrgDataList  = null;
     	try {
     		LOGGER.error("Fetching MOAL prefix");
     		multiOrgDataList = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.MOAL,  MainetConstants.ENV, org);
     		LOGGER.error("MOAL prefix is available");
     	}catch (Exception e) {
     		LOGGER.error("No Prefix found for ENV(MOAL)");
 		}
     	
     	if(!(multiOrgDataList != null && StringUtils.isNotBlank(multiOrgDataList.getOtherField()) && StringUtils.equals(multiOrgDataList.getOtherField(), MainetConstants.FlagY))) {
     		query.append(" and s.orgId.orgid=:orgId");
     		moalFlag=true;
     		LOGGER.error("Appending org id in query: "+ orgId);
     	}
     	final Query queryData = entityManager.createQuery(query.toString());
        if (moalFlag == true) {
			LOGGER.error("Setting orgId parameter in  Query setParameter field: " + query.toString());
			queryData.setParameter("orgId", orgId);
		}        
        queryData.setParameter(2, loiApplicationId);
        try {
            master = (CFCApplicationAddressEntity) queryData.getSingleResult();
        } catch (final NoResultException nre) {
            // Ignore this because as per logic this is ok!
        }
        return master;
    }

}
