package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SelfAssessmentFinancialDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1174862329386132139L;

    private Long finYearId;

    private List<Long> finYearList;

    private List<Object> acqDateDetail;

    private Map<Long, String> yearMap = null;

    private String finYear;

    public Long getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(Long finYearId) {
        this.finYearId = finYearId;
    }

    public List<Long> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<Long> finYearList) {
        this.finYearList = finYearList;
    }

    public List<Object> getAcqDateDetail() {
        return acqDateDetail;
    }

    public void setAcqDateDetail(List<Object> acqDateDetail) {
        this.acqDateDetail = acqDateDetail;
    }

    public Map<Long, String> getYearMap() {
        return yearMap;
    }

    public void setYearMap(Map<Long, String> yearMap) {
        this.yearMap = yearMap;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

}
