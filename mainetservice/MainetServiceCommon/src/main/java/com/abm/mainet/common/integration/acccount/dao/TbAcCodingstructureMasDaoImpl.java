package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;

@Repository
public class TbAcCodingstructureMasDaoImpl extends AbstractDAO<TbAcCodingstructureMasEntity>
        implements TbAcCodingstructureMasDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<TbAcCodingstructureMasEntity> findByAllGridSearchData(final Long comCpdId, final Long orgId) {

        String queryString = "select te from TbAcCodingstructureMasEntity te where te.orgid =:orgId";

        if (comCpdId != null) {
            queryString += " and te.comCpdId =:comCpdId";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter("orgId",
                orgId);
        if (comCpdId != null) {
            query.setParameter("comCpdId",
                    comCpdId);
        }
        List<TbAcCodingstructureMasEntity> result = null;
        result = query.getResultList();
        return result;
    }
}
