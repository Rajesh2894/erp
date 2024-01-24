/**
 * 
 */
package com.abm.mainet.adh.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbDepartment;

/**
 * @author cherupelli.srikanth
 *
 */
@Repository
public class AdvertisementContractMappingDaoImpl extends AbstractDAO<ContractMastEntity>
	implements IAdvertisementContractMappingDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ContractMastEntity> searchContractMappingData(Long orgId, String contNo, Date contDate,
	    TbDepartment contDept) {
	List<ContractMastEntity> entity = null;
	try {

	    StringBuilder hql = new StringBuilder(
		    "SELECT c from ContractMastEntity c where c.orgId =:orgId and c.contDept =:contDept");
	    if (StringUtils.isNotBlank(contNo) && contDate != null) {
		hql.append(" and (c.contNo =:contNo and c.contDate =:contDate )");
	    } else if (StringUtils.isNotBlank(contNo)) {
		hql.append(" and (c.contNo =:contNo)");
	    } else if (contDate != null) {
		hql.append(" and (c.contDate =:contDate)");
	    }

	    hql.append(
		    " and exists (select m.contractMastEntity.contId from ADHContractMappingEntity m  where m.contractMastEntity.contId = c.contId)");
	    final Query query = this.createQuery(hql.toString());

	    query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
	    query.setParameter("contDept", contDept.getDpDeptid());

	    if (StringUtils.isNotBlank(contNo)) {
		query.setParameter("contNo", contNo);
	    }
	    if (contDate != null) {
		query.setParameter("contDate", contDate);
	    }

	    entity = (List<ContractMastEntity>) query.getResultList();

	} catch (Exception exception) {

	    throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
	}
	return entity;
    }

}
