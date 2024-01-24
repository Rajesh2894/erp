/**
 * 
 */
package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;


/**
 * @author Saiprasad.Vengurekar
 *
 */
public interface ProjectProgressDao{

	List<Object[]> getProjectProgressWithWorkId(Long orgId, Date fromDate,Date toDate );
	
	List<Object[]> getProjectProgress(Long orgId, Date fromDate,Date toDate );
}
