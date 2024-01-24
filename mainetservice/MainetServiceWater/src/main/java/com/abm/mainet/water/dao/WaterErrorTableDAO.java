package com.abm.mainet.water.dao;

import com.abm.mainet.water.domain.TbWtBillGenError;

/**
 * @author Rahul.Yadav
 *
 */
public interface WaterErrorTableDAO {

    /**
     * @param error
     */
    void saveErrorRecords(TbWtBillGenError error);

}
