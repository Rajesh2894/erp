package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class ActRuleMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long actNo;
    private String actName;
    private Date actYear;
    private Date activeFromDate;
    private Date activeToDate;
    private String actTitle;
    private String actDesc;
    private String actRef;
    private Long addNo;
    private String addTitle;
    private Date addYear;
    private Date addDate;
    private String addDetails;

    public Long getActNo() {
        return actNo;
    }

    public void setActNo(Long actNo) {
        this.actNo = actNo;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Date getActYear() {
        return actYear;
    }

    public void setActYear(Date actYear) {
        this.actYear = actYear;
    }

    public Date getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(Date activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public Date getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(Date activeToDate) {
        this.activeToDate = activeToDate;
    }

    public String getActTitle() {
        return actTitle;
    }

    public void setActTitle(String actTitle) {
        this.actTitle = actTitle;
    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public String getActRef() {
        return actRef;
    }

    public void setActRef(String actRef) {
        this.actRef = actRef;
    }

    public Long getAddNo() {
        return addNo;
    }

    public void setAddNo(Long addNo) {
        this.addNo = addNo;
    }

    public String getAddTitle() {
        return addTitle;
    }

    public void setAddTitle(String addTitle) {
        this.addTitle = addTitle;
    }

    public Date getAddYear() {
        return addYear;
    }

    public void setAddYear(Date addYear) {
        this.addYear = addYear;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getAddDetails() {
        return addDetails;
    }

    public void setAddDetails(String addDetails) {
        this.addDetails = addDetails;
    }

}
