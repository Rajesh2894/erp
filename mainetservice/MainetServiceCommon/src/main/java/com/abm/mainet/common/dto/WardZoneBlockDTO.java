package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author Lalit.Prusti
 *
 */
public class WardZoneBlockDTO implements Serializable {
    private static final long serialVersionUID = -8583873170376400033L;

    private long areaDivision1;
    private long areaDivision2;
    private long areaDivision3;
    private long areaDivision4;
    private long areaDivision5;
    private boolean isAvailable;
    private long empId;
    private long tariffCategory;

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final long empId) {
        this.empId = empId;
    }

    /**
     * @return the isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable the isAvailable to set
     */
    public void setAvailable(final boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * @return the areaDivision1
     */
    public long getAreaDivision1() {
        return areaDivision1;
    }

    /**
     * @param areaDivision1 the areaDivision1 to set
     */
    public void setAreaDivision1(final long areaDivision1) {
        this.areaDivision1 = areaDivision1;
    }

    /**
     * @return the areaDivision2
     */
    public long getAreaDivision2() {
        return areaDivision2;
    }

    /**
     * @param areaDivision2 the areaDivision2 to set
     */
    public void setAreaDivision2(final long areaDivision2) {
        this.areaDivision2 = areaDivision2;
    }

    /**
     * @return the areaDivision3
     */
    public long getAreaDivision3() {
        return areaDivision3;
    }

    /**
     * @param areaDivision3 the areaDivision3 to set
     */
    public void setAreaDivision3(final long areaDivision3) {
        this.areaDivision3 = areaDivision3;
    }

    /**
     * @return the areaDivision4
     */
    public long getAreaDivision4() {
        return areaDivision4;
    }

    /**
     * @param areaDivision4 the areaDivision4 to set
     */
    public void setAreaDivision4(final long areaDivision4) {
        this.areaDivision4 = areaDivision4;
    }

    /**
     * @return the areaDivision5
     */
    public long getAreaDivision5() {
        return areaDivision5;
    }

    /**
     * @param areaDivision5 the areaDivision5 to set
     */
    public void setAreaDivision5(final long areaDivision5) {
        this.areaDivision5 = areaDivision5;
    }

    public long getTariffCategory() {
        return tariffCategory;
    }

    public void setTariffCategory(final long tariffCategory) {
        this.tariffCategory = tariffCategory;
    }

}
