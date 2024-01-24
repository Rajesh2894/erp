package com.abm.mainet.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IDesignationDAO;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

@Service
public class DesignationService implements IDesignationService {

    @Autowired
    private IDesignationDAO designationDAO;

    private static final long serialVersionUID = 5332528951130596683L;

    @Override
    @Transactional
    public boolean saveDesignation(Designation designation) {
        designation.setIsdeleted("0");
        designationDAO.saveOrUpdate(designation);
        return true;
    }

    @Override
    @Transactional
    public List<Designation> getAllListDesignationByDeptIdAndDesgId(long deptId, long desgId) {
        return designationDAO.getDesignationbyDeptIdAndDesgId(deptId, desgId);
    }

    @Override
    @Transactional
    public boolean saveDesignationLocation(final Organisation organisation, final Employee employee,
            final Designation designation) {
        new Date();
        new SimpleDateFormat(MainetConstants.COMMOM_TIME_FORMAT);

        designation.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        designationDAO.saveOrUpdate(designation);
        return true;
    }

    @Override
    @Transactional
    public List<Designation> getAllListDesignationByDeptId(final long deptId) {
        return designationDAO.getDesignation(deptId);
    }

    @Transactional
    public String getDesignationName(final long desgId) {
        return designationDAO.getDesignationName(desgId);
    }

    @Override
    @Transactional
    public Designation getDesignationByName(String dsgName) {
        return designationDAO.getDesgByName(dsgName);
    }

    public Designation createDesignation(Designation designation) {
        designation.setIsdeleted("0");
        designationDAO.saveOrUpdate(designation);
        return designation;
    }

    @Override
    @Transactional
    public Designation create(Designation designation) {
        return designationDAO.create(designation);
    }

    @Override
    @Transactional(readOnly = true)
    public Designation getDesignation(final long desgId) {
        return designationDAO.getDesgName(desgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Designation> getAllDesignationByDesgName(final String desgName) {

        return designationDAO.getAllDesignationByDesgName(desgName);
    }

    @Override
    @Transactional
    public List<LookUp> getDesignationByOrg(final Organisation organisation) {
        return designationDAO.getDesignationByOrg(organisation);
    }
    
    @Override
    public Designation findByShortname(final String shortName) {
        final Designation designationEntity = designationDAO.findByShortName(shortName);
        return designationEntity;
    }

}
