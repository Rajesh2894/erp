package com.abm.mainet.quartz.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Vikrant.Thakur
 *
 */
public class JobDetailsDefination implements Serializable {

    private static final long serialVersionUID = -2663877655734367552L;

    private int id;

    private Object beanName;

    private boolean active;

    private List<Object> paramList = new ArrayList<>();

    private List<QuartzSchedulerMaster> jobsParam = new ArrayList<>();	// used to hold Jobs details from TB_COM_JOB_MAS

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Object getBeanName() {
        return beanName;
    }

    public void setBeanName(final Object beanName) {
        this.beanName = beanName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public void setParamList(final List<Object> paramList) {
        this.paramList = paramList;
    }

    public List<QuartzSchedulerMaster> getJobsParam() {
        return jobsParam;
    }

    public void setJobsParam(final List<QuartzSchedulerMaster> jobsParam) {
        this.jobsParam = jobsParam;
    }

}
