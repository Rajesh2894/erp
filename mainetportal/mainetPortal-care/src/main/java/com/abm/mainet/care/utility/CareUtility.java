package com.abm.mainet.care.utility;

import com.abm.mainet.care.dto.ActorDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;

/**
 *
 * @author sanket.joshi
 *
 */
public class CareUtility {

    /**
     * 
     * @param employee Login user
     * @return
     */
    public static ActorDTO toActor(final Employee employee) {

        final ActorDTO actotr = new ActorDTO();
        actotr.setAutEmail(employee.getAutEmail());
        if (employee.getDesignation() != null) {
            actotr.setDesignation(employee.getDesignation().getDsgdescription());
        } else {
            actotr.setDesignation(MainetConstants.BLANK);
        }
        actotr.setEmpAddress(employee.getEmpAddress());
        actotr.setEmpAddress1(employee.getEmpAddress1());
        actotr.setEmpdob(employee.getEmpdob());
        actotr.setEmpemail(employee.getEmpemail());
        actotr.setEmpGender(employee.getEmpGender());
        actotr.setEmpId(employee.getEmpId());
        actotr.setEmpisecuritykey(employee.getEmpisecuritykey());
        actotr.setEmpLoginName(employee.getEmploginname());
        actotr.setEmplType(employee.getEmplType());
        actotr.setEmpmobno(employee.getEmpmobno());
        actotr.setEmpnetwork(employee.getEmpnetwork());
        actotr.setEmpnew(employee.getEmpnew());
        actotr.setEmpoutward(employee.getEmpoutward());
        actotr.setEmpPassword(employee.getEmppassword());
        actotr.setEmppayrollnumber(employee.getEmppayrollnumber());
        actotr.setEmppiservername(employee.getEmppiservername());
        actotr.setEmprecord(employee.getEmprecord());
        actotr.setEmpregistry(employee.getEmpregistry());
        actotr.setEmpuid(employee.getEmpuid());
        actotr.setEmpuwmsowner(employee.getEmpuwmsowner());
        actotr.setFname(employee.getEmpname());
        actotr.setMname(employee.getEmpMName());
        actotr.setLname(employee.getEmpLName());
        return actotr;
    }

}
