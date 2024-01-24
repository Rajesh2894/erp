package com.abm.mainet.agency.authentication.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;

/**
 *
 * @author Rajdeep.Sinha
 *
 */

@Component
@Scope("session")
public class AgencyAuthorizationModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 2680527126120584662L;
    @Autowired
    private IEmployeeService iEmployeeService;
    private List<EmployeeDTO> agency = new ArrayList<>(0);
    private final List<LookUp> lookUps = new ArrayList<>();
    private String statusIS;
    private String agencyName;
    private int sortValue;
    private EmployeeDTO entity = new EmployeeDTO();

    /**
     * @return the entity
     */
    public EmployeeDTO getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(final EmployeeDTO entity) {
        this.entity = entity;
    }

    public String getStatusIS() {
        return statusIS;
    }

    public void setStatusIS(final String statusIS) {
        this.statusIS = statusIS;
    }

    public IEmployeeService getiEmployeeService() {
        return iEmployeeService;
    }

    public void setiEmployeeService(final IEmployeeService iEmployeeService) {
        this.iEmployeeService = iEmployeeService;
    }

    public int getSortValue() {
        return sortValue;
    }

    public void setSortValue(final int sortValue) {
        this.sortValue = sortValue;
    }

    public List<EmployeeDTO> getAgency() {
        return agency;
    }

    public void setAgency(final List<EmployeeDTO> agency) {
        this.agency = agency;
    }
    @Override
    protected void initializeModel() {
        super.initializeModel();

        final List<LookUp> list = this.getLevelData(PrefixConstants.NEC.PARENT);

        final ListIterator<LookUp> iterator = list.listIterator();

        while (iterator.hasNext()) {
            final LookUp lookUp = iterator.next();

            if (!lookUp.getLookUpCode().equals(PrefixConstants.NEC.CITIZEN)) {
                lookUps.add(lookUp);
            }
        }
    }

    /**
     * @return the lookUps
     */
    public List<LookUp> getLookUps() {
        return lookUps;
    }

    /**
     * @return the agencyName
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * @param agencyName the agencyName to set
     */
    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * @return the agencyHospital
     */

    /**
     * @param agencyHospital the agencyHospital to set
     */

}
