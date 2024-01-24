/**
 *
 */
package com.abm.mainet.cfc.scrutiny.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.utility.LookUp;

/**
 * @author Rajendra.Bhujbal
 *
 */
public class ScrutinyLabelDTO extends CommonDataDTO implements Serializable {

    private static final long serialVersionUID = -7808151406309641455L;

    private String applicationId;

    private Long smServiceId;

    private String serviceName;

    private String applicantName;

    private String actionUrl = "";

    private long coloumnCount = 1L;
    
    private String serviceNameReg;

    private List<ViewCFCScrutinyLabelValue> viewCFCScrutinyLabelValues = new ArrayList<>(
            0);

    private Map<Long, List<ViewCFCScrutinyLabelValue>> desgWiseScrutinyLabelMap = new HashMap<>(
            0);
    
    private Map<Long, List<ViewCFCScrutinyLabelValue>> desgWiseFieldLabelMap = new HashMap<>(
            0);

    private Map<Long, List<LookUp>> dsgWiseScrutinyDocMap = new HashMap<>(
            0);

    private Map<Long, String> desgNameMap = new HashMap<>(0);

    private String roleId = "";

    private Map<Long, String> actionUrlMap = new LinkedHashMap<>(0);

    private String decisionFlag;

    private List<String> dislist = new ArrayList<>();
    
    private  String mobNo;
    
    private String email;
    
    private String licsenseDate;
    
    private String reamrkValidFlag;
    
    private String refNo;
    
    private String serviceShortCode;
    
    private String remark;
    
    private List<String> decisionList = new ArrayList<>();
    
    private Map<Long, List<ViewCFCScrutinyLabelValue>> noteSheetMap = new HashMap<>(
            0);
    
    private List<NotingDTO> notingList = new ArrayList<>();
    
    private Long taskId;

    /**
     * @return the actionUrl
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * @param actionUrl the actionUrl to set
     */
    public void setActionUrl(final String actionUrl) {
        this.actionUrl = actionUrl;
    }

    /**
     * @return the actionUrlMap
     */
    public Map<Long, String> getActionUrlMap() {
        return actionUrlMap;
    }

    /**
     * @param actionUrlMap the actionUrlMap to set
     */
    public void setActionUrlMap(final Map<Long, String> actionUrlMap) {
        this.actionUrlMap = actionUrlMap;
    }

    /**
     * @return the decisionFlag
     */
    public String getDecisionFlag() {
        return decisionFlag;
    }

    /**
     * @param decisionFlag the decisionFlag to set
     */
    public void setDecisionFlag(final String decisionFlag) {
        this.decisionFlag = decisionFlag;
    }

    public Map<Long, String> getDesgNameMap() {
        return desgNameMap;
    }

    public void setDesgNameMap(final Map<Long, String> desgNameMap) {
        this.desgNameMap = desgNameMap;
    }

    public Map<Long, List<ViewCFCScrutinyLabelValue>> getDesgWiseScrutinyLabelMap() {
        return desgWiseScrutinyLabelMap;
    }

    public void setDesgWiseScrutinyLabelMap(
            final Map<Long, List<ViewCFCScrutinyLabelValue>> desgWiseScrutinyLabelMap) {
        this.desgWiseScrutinyLabelMap = desgWiseScrutinyLabelMap;
    }

    public List<ViewCFCScrutinyLabelValue> getViewCFCScrutinyLabelValues() {
        return viewCFCScrutinyLabelValues;
    }

    public void setViewCFCScrutinyLabelValues(
            final List<ViewCFCScrutinyLabelValue> viewCFCScrutinyLabelValues) {
        this.viewCFCScrutinyLabelValues = viewCFCScrutinyLabelValues;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Map<Long, List<LookUp>> getDsgWiseScrutinyDocMap() {
        return dsgWiseScrutinyDocMap;
    }

    public void setDsgWiseScrutinyDocMap(
            final Map<Long, List<LookUp>> dsgWiseScrutinyDocMap) {
        this.dsgWiseScrutinyDocMap = dsgWiseScrutinyDocMap;
    }

    public long getColoumnCount() {
        return coloumnCount;
    }

    public void setColoumnCount(final long coloumnCount) {
        this.coloumnCount = coloumnCount;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(final String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the dislist
     */
    public List<String> getDislist() {
        return dislist;
    }

    /**
     * @param dislist the dislist to set
     */
    public void setDislist(final List<String> dislist) {
        this.dislist = dislist;
    }

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLicsenseDate() {
		return licsenseDate;
	}

	public void setLicsenseDate(String licsenseDate) {
		this.licsenseDate = licsenseDate;
	}

	public String getReamrkValidFlag() {
		return reamrkValidFlag;
	}

	public void setReamrkValidFlag(String reamrkValidFlag) {
		this.reamrkValidFlag = reamrkValidFlag;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	public String getServiceNameReg() {
		return serviceNameReg;
	}

	public void setServiceNameReg(String serviceNameReg) {
		this.serviceNameReg = serviceNameReg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Map<Long, List<ViewCFCScrutinyLabelValue>> getDesgWiseFieldLabelMap() {
		return desgWiseFieldLabelMap;
	}

	public void setDesgWiseFieldLabelMap(Map<Long, List<ViewCFCScrutinyLabelValue>> desgWiseFieldLabelMap) {
		this.desgWiseFieldLabelMap = desgWiseFieldLabelMap;
	}

	public List<String> getDecisionList() {
		return decisionList;
	}

	public void setDecisionList(List<String> decisionList) {
		this.decisionList = decisionList;
	}

	public Map<Long, List<ViewCFCScrutinyLabelValue>> getNoteSheetMap() {
		return noteSheetMap;
	}

	public void setNoteSheetMap(Map<Long, List<ViewCFCScrutinyLabelValue>> noteSheetMap) {
		this.noteSheetMap = noteSheetMap;
	}

	public List<NotingDTO> getNotingList() {
		return notingList;
	}

	public void setNotingList(List<NotingDTO> notingList) {
		this.notingList = notingList;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
