package com.abm.mainet.water.dto;

import java.io.Serializable;

/**
 * @author nirmal.mahanta
 *
 */
public class BillingMonthDTO implements Serializable {

    private static final long serialVersionUID = 2353328987790834998L;

    private Long monthId;
    private String monthName;
    private String monthNameReg;
    private Long tempMonthId;

    /**
     * @return the monthId
     */
    public Long getMonthId() {
        return monthId;
    }

    /**
     * @param monthId the monthId to set
     */
    public void setMonthId(final Long monthId) {
        this.monthId = monthId;
    }

    /**
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(final String monthName) {
        this.monthName = monthName;
    }

    /**
     * @return the monthNameReg
     */
    public String getMonthNameReg() {
        return monthNameReg;
    }

    /**
     * @param monthNameReg the monthNameReg to set
     */
    public void setMonthNameReg(final String monthNameReg) {
        this.monthNameReg = monthNameReg;
    }

    /**
     * @return the tempMonthId
     */
    public Long getTempMonthId() {
        return tempMonthId;
    }

    /**
     * @param tempMonthId the tempMonthId to set
     */
    public void setTempMonthId(final Long tempMonthId) {
        this.tempMonthId = tempMonthId;
    }

}
