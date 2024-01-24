package com.abm.mainet.orgnization.chart.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.OrganizationChartEntity;

@Repository
public class OrgnizationChartDaoImpl extends AbstractDAO<OrganizationChartEntity> implements OrgnizationChartDao {

	public OrgnizationChartDaoImpl() {
		// TODO Auto-generated constructor stub OrganizationChartEntity
	}

	@Override
	public List<Object[]> getOrgChartData() {
		final Query query = createQuery(
				"select desigId ,desiName,desigParentId from OrganizationChartEntity order by desigParentId asc");

		@SuppressWarnings("unchecked")
		final List<Object[]> orgDetails = query.getResultList();
		return orgDetails;
	}

}
