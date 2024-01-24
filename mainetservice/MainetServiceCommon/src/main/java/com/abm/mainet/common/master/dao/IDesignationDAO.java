package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.Designation;

public interface IDesignationDAO {

    /**
     * To save or update {@link Designation}
     * @param designation
     * @return true if success else false.
     */
    public abstract void saveOrUpdate(Designation designation);

    public abstract String getDesignationName(long desgId);

    public abstract List<Designation> getAllDesignationByDesgName(String desgName);

}