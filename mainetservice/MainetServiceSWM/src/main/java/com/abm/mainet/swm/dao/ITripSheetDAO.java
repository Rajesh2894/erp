package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.TripSheet;

/**
 * @author Ajay.Kumar
 *
 */
public interface ITripSheetDAO {

    /**
     * search TripSheet
     * @param veId
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    List<TripSheet> searchTripSheet(Long veId, Date fromDate, Date toDate, Long orgId);

}
