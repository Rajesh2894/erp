package com.abm.mainet.socialsecurity.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.socialsecurity.domain.ConfigurationMasterEntity;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
@Repository
public class ConfigurationMasterDaoImpl
extends AbstractDAO<ConfigurationMasterEntity>
implements ConfigurationMasterDao {

	@Override
	public List<ConfigurationMasterEntity> getData(Long configurationId, Long schemeMstId, Long orgId) {
			Query jpaQuery = this.createQuery(buildQuery(configurationId,schemeMstId,orgId));
			jpaQuery.setParameter("orgId", orgId);

			if (null != configurationId ) {
				jpaQuery.setParameter("configurationId", configurationId);
			}
			if (null != schemeMstId) {
				jpaQuery.setParameter("schemeMstId", schemeMstId);
			}
			return jpaQuery.getResultList();
		}

		private String buildQuery(Long configurationId, Long schemeMstId, Long orgId ) {
			// TODO Auto-generated method stub
			StringBuilder query = new StringBuilder(
					"SELECT cm FROM  ConfigurationMasterEntity  cm WHERE cm.orgId = :orgId ");
			if (null != configurationId) {
				query.append(" AND cm.configurationId = :configurationId");
			}
			if (null != schemeMstId) {
				query.append(" AND cm.schemeMstId = :schemeMstId");
			}

			return query.toString();
		}
		
		
		@Override
		public List<SocialSecurityApplicationForm> getAppData(Long selectSchemeName,Long subSchemeName,String swdward1,String swdward2,String swdward3,
				String swdward4,String swdward5,String aadharCard,String status, Long orgId) {
				Query jpaQuery = this.createQuery(buildQueryData(selectSchemeName,subSchemeName,swdward1,swdward2,swdward3,swdward4,swdward5,aadharCard,status,orgId));
				jpaQuery.setParameter("orgId", orgId);

				if (null != selectSchemeName && selectSchemeName!=0) {
					jpaQuery.setParameter("selectSchemeName", selectSchemeName);
				}
				if (null != subSchemeName && subSchemeName!=0) {
					jpaQuery.setParameter("subSchemeName", subSchemeName);
				}
				if (null != swdward1 && !swdward1.equals("0")) {
					jpaQuery.setParameter("swdward1", swdward1);
				}
				if (null != swdward2) {
					jpaQuery.setParameter("swdward2", swdward2);
				}
				if (null != swdward3) {
					jpaQuery.setParameter("swdward3", swdward3);
				}
				if (null != swdward4) {
					jpaQuery.setParameter("swdward4", swdward4);
				}
				if (null != swdward5) {
					jpaQuery.setParameter("swdward5", swdward5);
				}
				if (null != aadharCard && !aadharCard.equals("")) {
					jpaQuery.setParameter("aadharCard", aadharCard);
				}
				if (null != status && !status.equals("")) {
					jpaQuery.setParameter("status", status);
				}
				return jpaQuery.getResultList();
			}

			private String buildQueryData(Long selectSchemeName,Long subSchemeName,String swdward1,String swdward2,String swdward3,
					String swdward4,String swdward5,String aadharCard,String status, Long orgId ) {
				// TODO Auto-generated method stub
				StringBuilder query = new StringBuilder(
						"SELECT cm FROM  SocialSecurityApplicationForm  cm WHERE cm.orgId = :orgId");
				if (null != selectSchemeName && selectSchemeName!=0) {
					query.append(" AND cm.selectSchemeName = :selectSchemeName");
				}
				if (null != subSchemeName && subSchemeName!=0) {
					query.append(" AND cm.subSchemeName = :subSchemeName");
				}
				if (null != swdward1 && !swdward1.equals("0")) {
					query.append(" AND cm.swdward1 = :swdward1");
				}
				if (null != swdward2) {
					query.append(" AND cm.swdward2 = :swdward2");
				}
				if (null != swdward3) {
					query.append(" AND cm.swdward3 = :swdward3");
				}
				if (null != swdward4) {
					query.append(" AND cm.swdward4 = :swdward4");
				}
				if (null != swdward5) {
					query.append(" AND cm.swdward5 = :swdward5");
				}
				if (null != aadharCard && !aadharCard.equals("")) {
					query.append(" AND cm.aadharCard = :aadharCard");
				}
				
				if (null != status && !status.equals("")) {
					query.append(" AND cm.status = :status");
				}
				
				query.append(" order by cm.applicationId desc");

				return query.toString();
			}

	}

