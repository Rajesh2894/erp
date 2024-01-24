/**
 *
 */
package com.abm.mainet.common.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IDesignationService extends Serializable {

    public boolean saveDesignationLocation(Organisation organisation, Employee employee, Designation designation);

	public boolean saveDesignation(Designation designation);

    public List<Designation> getAllListDesignationByDeptIdAndDesgId(long deptId, long desgId);
	
	public List<Designation> getAllListDesignationByDeptId(long deptId);
	
	public Designation getDesignation(long desgId);
	
	public List<Designation> getAllDesignationByDesgName(String desgName);
	
	List<LookUp> getDesignationByOrg(Organisation organisation);
	
	public Designation getDesignationByName(String dsgName);
	
	public Designation create(Designation designation);

	Designation findByShortname(String shortName);
}
