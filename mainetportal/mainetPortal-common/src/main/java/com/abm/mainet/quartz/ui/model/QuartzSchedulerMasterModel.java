package com.abm.mainet.quartz.ui.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.Common;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.DepartmentLookUp;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.service.IQuartzSchedulerMasterService;
import com.abm.mainet.quartz.ui.validator.QuartzSchedulerMasterValidator;

/**
 *
 * @author Vivek.Kumar
 * @since 04-May-2015
 */
@Component
@Scope(value = "session")
public class QuartzSchedulerMasterModel extends AbstractEntryFormModel<QuartzSchedulerMaster> {

    private static final long serialVersionUID = 4485445834511086201L;

    @Autowired
    private IQuartzSchedulerMasterService iQuartzSchedulerMasterService;

    private List<QuartzSchedulerMaster> jobList;

    /* QuartzSchedulerMaster entity = new QuartzSchedulerMaster(); */

    private List<DepartmentLookUp> departmentList = new ArrayList<>();

    private String departmentForQuartz;

    private List<LookUp> availableJobsForDept;

    private String jobId;

    private List<LookUp> jobRunFrequencyLookUp = new ArrayList<>();

    private List<QuartzSchedulerMaster> finalList = new ArrayList<>();

    private boolean isEditMode;

    private String isSuccess;

    public void initializeMasterForm() throws RuntimeException {

        initializeDepartmentLookUp();

        initializeJobFrequency();

    }

    public void initializeDepartmentLookUp() throws RuntimeException {

        final List<DepartmentLookUp> listDept = this.getDepartmentLookUp();

        for (final DepartmentLookUp dept : listDept) {
            final long Id = dept.getLookUpId();
            if (Id != MainetConstants.Common_Constant.ZERO_LONG) {
                departmentList.add(dept);
            }
        }

        setDepartmentList(departmentList);

    }

    public void initializeJobFrequency() throws RuntimeException {

        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(Common.BFR, UserSession.getCurrent().getOrganisation());

        setJobRunFrequencyLookUp(iQuartzSchedulerMasterService.arrangeJobFrequencyInOrder(lookUps));

    }

    @Override
    public boolean saveForm() {

        boolean success = false;

        validateBean(this, QuartzSchedulerMasterValidator.class);

        final StringBuilder builder = new StringBuilder();

        if (!isEditMode()) {
            if (iQuartzSchedulerMasterService.validateProcedureOrFunction(this)) {
                builder.append(ApplicationSession.getInstance()
                        .getMessage("quartz.form.field.procOrFuncName.exist.validation.error"))
                        .append(MainetConstants.WHITE_SPACE)
                        .append(getEntity().getJobProcName());
                addValidationError(builder.toString());
            }
        }
        if (!hasValidationErrors()) {
            try {
                success = iQuartzSchedulerMasterService.processAndSaveQuartzMaster(this);
                if (success == true) {
                    setIsSuccess(MainetConstants.SUCCESS);
                } else {
                    setIsSuccess(MainetConstants.FAIL);
                }
            } catch (RuntimeException | ParseException | ClassNotFoundException | SchedulerException
                    | LinkageError ex) {
                logger.error(MainetConstants.EXCEPTION_OCCURED, ex);
                setIsSuccess(MainetConstants.FAIL);
            }
        }
        return success;
    }

    @Override
    public void editForm(final long rowId) {

        final QuartzSchedulerMaster master = iQuartzSchedulerMasterService.findQuartzMasterById(rowId,
                UserSession.getCurrent().getOrganisation());

        setEntity(iQuartzSchedulerMasterService.initializeQuartzMasterDataForEditMode(this, master));

    }

    @Override
    public boolean saveOrUpdateForm() {

        return saveForm();
    }

    public List<QuartzSchedulerMaster> getJobList() {
        return jobList;
    }

    public void setJobList(final List<QuartzSchedulerMaster> jobList) {
        this.jobList = jobList;
    }

    public List<DepartmentLookUp> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(final List<DepartmentLookUp> departmentList) {
        this.departmentList = departmentList;
    }

    public String getDepartmentForQuartz() {
        return departmentForQuartz;
    }

    public void setDepartmentForQuartz(final String departmentForQuartz) {
        this.departmentForQuartz = departmentForQuartz;
    }

    public List<LookUp> getAvailableJobsForDept() {
        return availableJobsForDept;
    }

    public void setAvailableJobsForDept(final List<LookUp> availableJobsForDept) {
        this.availableJobsForDept = availableJobsForDept;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(final String jobId) {
        this.jobId = jobId;
    }

    public List<LookUp> getJobRunFrequencyLookUp() {
        return jobRunFrequencyLookUp;
    }

    public void setJobRunFrequencyLookUp(final List<LookUp> jobRunFrequencyLookUp) {
        this.jobRunFrequencyLookUp = jobRunFrequencyLookUp;
    }

    public List<QuartzSchedulerMaster> getFinalList() {
        return finalList;
    }

    public void setFinalList(final List<QuartzSchedulerMaster> finalList) {
        this.finalList = finalList;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(final boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(final String isSuccess) {
        this.isSuccess = isSuccess;
    }

}
