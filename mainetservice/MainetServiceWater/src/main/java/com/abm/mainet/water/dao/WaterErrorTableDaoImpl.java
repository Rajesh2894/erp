package com.abm.mainet.water.dao;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.TbWtBillGenError;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class WaterErrorTableDaoImpl extends AbstractDAO<Long> implements WaterErrorTableDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterErrorTableDAO#saveErrorRecords(com.abm.mainet.water.domain.TbWtBillGenError)
     */
    @Override
    public void saveErrorRecords(final TbWtBillGenError error) {
        entityManager.persist(error);
    }

}
