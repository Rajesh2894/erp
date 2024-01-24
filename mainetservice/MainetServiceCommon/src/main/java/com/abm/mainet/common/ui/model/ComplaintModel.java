package com.abm.mainet.common.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ComplaintTypeBean;
import com.abm.mainet.common.dto.DepartmentComplaintBean;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class ComplaintModel extends AbstractFormModel {

    @Autowired
    private IComplaintTypeService iComplaintService;

    private static final long serialVersionUID = 6683953025021720462L;
    DepartmentComplaintBean departmentComplaint;
    String modeType;
    String removeChildIds;
    List<Object[]> deptList;

    public DepartmentComplaintBean getDepartmentComplaint() {
        return departmentComplaint;
    }

    public void setDepartmentComplaint(
            final DepartmentComplaintBean departmentComplaint) {
        this.departmentComplaint = departmentComplaint;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(final String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public List<Object[]> getDeptList() {
        return deptList;
    }

    public void setDeptList(final List<Object[]> deptList) {
        this.deptList = deptList;
    }

    @Override
    public boolean saveForm() {

        final DepartmentComplaintBean departmentComplaintBean = getDepartmentComplaint();
        if (getModeType().equals(MainetConstants.Complaint.MODE_CREATE)) {
            departmentComplaintBean.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            departmentComplaintBean.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            iComplaintService.save(departmentComplaintBean);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("complaint.model.create"));
        } else if (getModeType().equals(MainetConstants.Complaint.MODE_EDIT)) {
			List<ComplaintTypeBean> complaintTypeBeanList = departmentComplaintBean.getComplaintTypesList().stream()
					.filter(c -> (c.getCompId() != null || StringUtils.isNotBlank(c.getComplaintDesc())))
					.collect(Collectors.toList());
        	if(!complaintTypeBeanList.isEmpty()) {
        		departmentComplaintBean.getComplaintTypesList().clear();
        		departmentComplaintBean.setComplaintTypesList(complaintTypeBeanList);
        	}
            departmentComplaintBean.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            final List<Long> removeIds = new ArrayList<>();
            final String ids = getRemoveChildIds();
            if (!ids.isEmpty()) {
                final String array[] = ids.split(MainetConstants.operator.COMMA);
                for (final String string : array) {
                    removeIds.add(Long.valueOf(string));
                }
            }
            iComplaintService.saveEdit(departmentComplaintBean, removeIds);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("complaint.model.edit"));
        }
     
        return true;
    }

}
