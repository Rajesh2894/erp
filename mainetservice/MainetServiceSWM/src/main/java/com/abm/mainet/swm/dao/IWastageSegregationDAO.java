/*
 * 
 */
package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.WastageSegregation;

/**
 * The Interface WastageSegregationDAO.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
public interface IWastageSegregationDAO {

    /**
     * Search wastage segregation.
     *
     * @param deId the veheicle id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<WastageSegregation> searchWastageSegregation(Long deId, Date fromDate, Date toDate, Long orgId);

}
