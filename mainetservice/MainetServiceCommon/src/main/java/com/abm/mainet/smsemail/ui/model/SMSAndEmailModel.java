package com.abm.mainet.smsemail.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.EventMasterService;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SmsAndMailRespose;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Component
@Scope("session")
public class SMSAndEmailModel extends AbstractEntryFormModel<SMSAndEmailInterface> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Resource
    private EventMasterService eventMasterService;

    private Map<Long, String> departmentList = new HashMap<>();

    private List<Object[]> eventsList = new ArrayList<>();
    private List<String> attributeList = new ArrayList<>();
    private List<LookUp> messageLookUp = new ArrayList<>();
    private static final String EMAIL = "E";
    private static final String SMS = "S";
    private static final String BOTH = "B";
    private static final String ADD = "A";
    private static final String EDIT = "E";

    private String deptid;
    private String serid;

    private Long deptID;

    private String modeType;

    private Long selectedServiceId;

    private String selectedMode;

    private String editModeType;

    public String getEditModeType() {
        return editModeType;
    }

    public void setEditModeType(final String editModeType) {
        this.editModeType = editModeType;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    private Long serviceID;

    public Map<Long, String> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(final Map<Long, String> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    protected void initializeModel() {
        setEntity(new SMSAndEmailInterface());
        final List<Department> deptList = ApplicationSession.getInstance().getDepartmentList();
        for (final Department dept : deptList) {
            if(UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH)
            getDepartmentList().put(dept.getDpDeptid(), (dept.getDpDeptdesc() == null ? "" : dept.getDpDeptdesc()) + "-->"
                    + (dept.getDpDeptcode() == null ? "" : dept.getDpDeptcode()));
            else
        	 getDepartmentList().put(dept.getDpDeptid(), (dept.getDpNameMar() == null ? "" : dept.getDpNameMar()) + "-->"
                         + (dept.getDpDeptcode() == null ? "" : dept.getDpDeptcode()));
        }
        List<Object[]> events = null;
        events = eventMasterService.findEventList(MainetConstants.MENU.EndWithHTML, MainetConstants.MENU._0);
        setEventsList(events);
        final List<LookUp> messageType = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.SMT,
                UserSession.getCurrent().getOrganisation());
        setMessageLookUp(messageType);
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody SmsAndMailRespose getGridData(final HttpServletRequest request, final Model model) {
        final SmsAndMailRespose response = new SmsAndMailRespose();
        Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));

        return response;

    }

    @Override
    public boolean saveOrUpdateForm() {
        ApplicationSession appSession = ApplicationSession.getInstance();
        boolean result = false;
        final SMSAndEmailInterface entity = getEntity();
        if ((getSelectedMode() != null) && getSelectedMode().equalsIgnoreCase(ADD)) {
            entity.setLmodDate(new Date());
            entity.setLgIpMac(this.getClientIpAddress());
            entity.setOrgId(getUserSession().getOrganisation());
            entity.setUserId(getUserSession().getEmployee());

            entity.getSmsAndmailTemplate().setLmodDate(new Date());
            entity.getSmsAndmailTemplate().setLgIpMac(this.getClientIpAddress());
            entity.getSmsAndmailTemplate().setOrgId(getUserSession().getOrganisation());
            entity.getSmsAndmailTemplate().setUserId(getUserSession().getEmployee());
            result = ismsAndEmailService.saveMessageTemplate(entity, selectedMode);
            entity.getSmsAndmailTemplate().setUpdatedDate(new Date());

            setSuccessMessage(appSession.getMessage("sms.email.record.save"));
            return result;

        }
        if ((getSelectedMode() != null) && getSelectedMode().equalsIgnoreCase(EDIT)) {

            entity.setUpdatedDate(new Date());
            entity.setUpdatedBy(getUserSession().getEmployee());
            entity.setLgIpMacUpd(Utility.getMacAddress());

            entity.getSmsAndmailTemplate().setLgIpMacUpd(Utility.getMacAddress());
            entity.getSmsAndmailTemplate().setUpdatedBy(getUserSession().getEmployee());
            entity.getSmsAndmailTemplate().setUpdatedDate(new Date());

            result = ismsAndEmailService.saveMessageTemplate(entity, selectedMode);
            setSuccessMessage(appSession.getMessage("sms.email.record.update"));
            return result;
        }
        return result;
    }

    public boolean checkExistance() {

        final boolean result = ismsAndEmailService.checkExistanceForEdit(getEntity());
        if (result) {
            addValidationError("Template already defined.");
        }
        return result;
    }

    public String getAlertType() {
        if ((getEntity().getAlertType() != null) && !getEntity().getAlertType().isEmpty()) {
            if (getEntity().getAlertType().equalsIgnoreCase(EMAIL)) {
                return EMAIL;
            }
            if (getEntity().getAlertType().equalsIgnoreCase(SMS)) {
                return SMS;
            }
            if (getEntity().getAlertType().equalsIgnoreCase(BOTH)) {
                return BOTH;
            }
        }
        return "";
    }

    public String getCurrentMode() {
        if ((getSelectedMode() != null) && !getSelectedMode().isEmpty()) {
            if (getSelectedMode().equalsIgnoreCase(ADD)) {
                return ADD;
            }
            if (getSelectedMode().equalsIgnoreCase(EDIT)) {
                return EDIT;
            }

        }
        return "";
    }

    public Long getServiceID() {
        return serviceID;
    }

    public void setServiceID(final Long serviceID) {
        this.serviceID = serviceID;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(final String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public Long getSelectedServiceId() {
        return selectedServiceId;
    }

    public void setSelectedServiceId(final Long selectedServiceId) {
        this.selectedServiceId = selectedServiceId;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(final String deptid) {
        this.deptid = deptid;
    }

    public String getSerid() {
        return serid;
    }

    public void setSerid(final String serid) {
        this.serid = serid;
    }

    public Long getDeptID() {
        return deptID;
    }

    public void setDeptID(final Long deptID) {
        this.deptID = deptID;
    }

    public List<Object[]> getEventsList() {
        return eventsList;
    }

    public void setEventsList(final List<Object[]> eventsList) {
        this.eventsList = eventsList;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(final List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public List<LookUp> getMessageLookUp() {
        return messageLookUp;
    }

    public void setMessageLookUp(final List<LookUp> messageLookUp) {
        this.messageLookUp = messageLookUp;
    }

}
