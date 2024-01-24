package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IDesignationDAO {

    /**
     * To save or update {@link Designation}
     * @param designation
     * @return true if success else false.
     */
    public abstract void saveOrUpdate(Designation designation);

    public abstract List<Designation> getDesignation(long deptId);

    public abstract Designation getDesgName(long desgId);

    public abstract List<Designation> getDesignationbyDeptIdAndDesgId(long deptId, long desgId);

    public abstract String getDesignationName(long desgId);

    public abstract List<Designation> getAllDesignationByDesgName(String desgName);

    public abstract List<LookUp> getDesignationByOrg(Organisation organisation);
    
    public abstract Designation getDesgByName(String dsgName);
    
    public abstract Designation create(Designation designation);

	public abstract Designation findByShortName(String dsgshortname);
}