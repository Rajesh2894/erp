package com.abm.mainet.legal.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.legal.domain.AdvocateMaster;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;

@Repository
public class AdvocateMasterDAO extends AbstractDAO<AdvocateMaster> implements IAdvocateMasterDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<AdvocateMaster> validateAdvocate(String mobile, String email, String pancard, String aadhar, Long advId,
            Long orgid) {

        Query query = this.createQuery(buildQuery(mobile, email, pancard, aadhar, advId, orgid));
        query.setParameter("orgid", orgid);

        if (StringUtils.isNotEmpty(mobile)) {
            query.setParameter("mobile", mobile);
        }
        if (StringUtils.isNotEmpty(email)) {
            query.setParameter("email", email);
        }
        if (StringUtils.isNotEmpty(pancard)) {
            query.setParameter("pancard", pancard);
        }
        if (StringUtils.isNotEmpty(aadhar)) {
            query.setParameter("aadhar", aadhar);
        }
        if (advId != null) {
            query.setParameter("advId", advId);
        }
        return query.getResultList();
    }

    private String buildQuery(String mobile, String email, String pancard, String aadhar, Long advId, Long orgid) {

        StringBuilder searchQuery = new StringBuilder(" SELECT am FROM AdvocateMaster am WHERE am.orgid = :orgid ");
        // And Condition exclude if for edit
        if (advId != null) {
            searchQuery.append(" AND am.advId != :advId ");
        }
        // Or conditions
        if (StringUtils.isNotEmpty(mobile)) {
            searchQuery.append(" AND ( ");
            searchQuery.append("am.advMobile = :mobile ");
        }
        if (StringUtils.isNotEmpty(email)) {
            searchQuery.append(" OR am.advEmail = :email ");
        }
        if (StringUtils.isNotEmpty(pancard)) {
            searchQuery.append(" OR am.advPanno = :pancard ");
        }
        if (StringUtils.isNotEmpty(aadhar)) {
            searchQuery.append(" OR am.advUid = :aadhar ");
        }
        searchQuery.append(" ) ");
        return searchQuery.toString();

    }

	@Override
	public List<AdvocateMaster> findDetails(Long advId, Long crtId, String barCouncilNo,String advStatus, Long orgid) {
		
		final StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT am FROM AdvocateMaster am WHERE am.orgid=:orgid ");

		if (advId != null && advId != 0 ) {
			queryString.append(" and am.adv_advocateTypeId=:advId");
		}
		
		if (crtId != null && crtId != 0 ) {
			queryString.append(" and am.adv_courtNameId=:crtId");
		}
		
		if (barCouncilNo != null && StringUtils.isNotBlank(barCouncilNo)) {
			queryString.append(" and am.adv_barCouncilNo=:barCouncilNo");
		}
		
		if( advStatus != null && StringUtils.isNotBlank(advStatus)) {
			queryString.append(" and am.advStatus=:advStatus");
		}
		
		Query query = this.createQuery(queryString.toString());
		query.setParameter("orgid", orgid);
		
		if (advId != null && advId != 0) {
			query.setParameter("advId", advId);
			}
		
		if (crtId != null && crtId != 0) {
			query.setParameter("crtId", crtId);
			}
		
		if (barCouncilNo != null &&StringUtils.isNotBlank(barCouncilNo)) {
			query.setParameter("barCouncilNo", barCouncilNo);
		}
		if(advStatus != null && StringUtils.isNotBlank(advStatus)){
			query.setParameter("advStatus", advStatus);
		}
		List<AdvocateMaster> entityList = query.getResultList();
		return entityList;
	
	}


}
