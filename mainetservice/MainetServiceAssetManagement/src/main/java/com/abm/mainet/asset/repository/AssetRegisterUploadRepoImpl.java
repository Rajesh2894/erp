/**
 * 
 */
package com.abm.mainet.asset.repository;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetRegisterUploadError;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetRegisterUploadRepoImpl extends AbstractDAO<Long> implements AssetRegisterUploadRepoCustom {

  

    @Override
    @Transactional
    public void deleteErrorLog(Long orgId, String astType) {
        StringBuilder hql = new StringBuilder(
                "DELETE FROM AssetRegisterUploadError d WHERE d.astType= :AstType and d.orgId= :orgId");
        final Query query = createQuery(hql.toString());
        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        query.setParameter("AstType", astType);
        query.executeUpdate();

    }

    @Override
    public void saveErrorDetails(AssetRegisterUploadError entity) {
        entityManager.merge(entity);
    }

}
