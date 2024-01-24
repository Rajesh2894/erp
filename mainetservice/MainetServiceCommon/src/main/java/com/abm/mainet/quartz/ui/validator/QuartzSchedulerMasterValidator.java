package com.abm.mainet.quartz.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.ui.model.QuartzSchedulerMasterModel;

/**
 *
 * @author Vivek.Kumar
 * @since 14-May-2015
 */
@Component
public class QuartzSchedulerMasterValidator extends BaseEntityValidator<QuartzSchedulerMasterModel> {

    @Override
    protected void performValidations(
            final QuartzSchedulerMasterModel model,
            final EntityValidationContext<QuartzSchedulerMasterModel> entityValidationContext) {

        final QuartzSchedulerMaster entity = model.getEntity();

        if ((model.getDepartmentForQuartz() == null) || model.getDepartmentForQuartz().trim().equals(MainetConstants.BLANK)
                || model.getDepartmentForQuartz().trim().equals(MainetConstants.CommonConstants.ZERO)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.department.validation.error"));
        }

        if ((model.getJobId() == null) || model.getJobId().trim().equals(MainetConstants.BLANK)
                || model.getJobId().trim().equals(MainetConstants.CommonConstants.ZERO)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.jobName.validation.error"));
        }

        if ((entity.getCpdIdBfr() == null) || (entity.getCpdIdBfr() == 0l)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.jobFrequency.validation.error"));
        } else {
            if ((entity.getHiddenJobFrequencyType() != null)
                    && entity.getHiddenJobFrequencyType().equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.DAILY)) {
                if ((entity.getStartAtTime_Daily() == null)
                        || entity.getStartAtTime_Daily().trim().equals(MainetConstants.BLANK)) {
                    entityValidationContext
                            .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.timeAt.validation.error"));
                }
            } else if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType().equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MONTHLY)
                            || entity.getHiddenJobFrequencyType()
                                    .equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.QUARTERLY)
                            || entity.getHiddenJobFrequencyType()
                                    .equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HALF_YEARLY))) {
                if ((entity.getStartOnDate_monthly() == null)
                        || entity.getStartOnDate_monthly().trim().equals(MainetConstants.BLANK)) {
                    entityValidationContext.addOptionConstraint(
                            getApplicationSession().getMessage("quartz.form.field.startOn.validation.error"));
                }
                if ((entity.getStartAtTime() == null) || entity.getStartAtTime().trim().equals(MainetConstants.BLANK)) {
                    entityValidationContext
                            .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.timeAt.validation.error"));
                }
            } else if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType().equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.YEARLY)
                            || entity.getHiddenJobFrequencyType()
                                    .equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.ONE_TIME))) {
                if ((entity.getStartOnDate_yearly() == null)
                        || entity.getStartOnDate_yearly().trim().equals(MainetConstants.BLANK)) {
                    entityValidationContext.addOptionConstraint(
                            getApplicationSession().getMessage("quartz.form.field.startOn.validation.error"));
                }
                if ((entity.getStartAtTime() == null) || entity.getStartAtTime().trim().equals(MainetConstants.BLANK)) {
                    entityValidationContext
                            .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.timeAt.validation.error"));
                }
            }
            else if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType().equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HOURLY))) {
            	 if ((entity.getRepeatHour() == null)
                         || entity.getRepeatHour().trim().equals(MainetConstants.BLANK)) {
                     entityValidationContext.addOptionConstraint(
                             getApplicationSession().getMessage("quartz.form.field.timeAt.validation.hour.check"));
                 }
            	 else {
            		 int validateMin = entity.getRepeatHour().compareTo(MainetConstants.QUARTZ_SCHEDULE.MAXHOURS); 
            		 if(validateMin == 0 || validateMin == 1){
            			 entityValidationContext.addOptionConstraint(
                                 getApplicationSession().getMessage("quartz.form.field.timeAt.valid.hour"));
            		   
                 }
            	 }
            }
            
            else if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType()
                            .equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MINUTES))) {
            	 if ((entity.getRepeatMinTime() == null)
                         || entity.getRepeatMinTime().trim().equals(MainetConstants.BLANK)) {
                     entityValidationContext.addOptionConstraint(
                             getApplicationSession().getMessage("quartz.form.field.timeAt.validation.min.check"));
                 }
            	 else {
            		 int validateMin = entity.getRepeatMinTime().compareTo(MainetConstants.QUARTZ_SCHEDULE.MAXMINUTES); 
            		 if(validateMin == 0 || validateMin == 1){
            			 entityValidationContext.addOptionConstraint(
                                 getApplicationSession().getMessage("quartz.form.field.timeAt.valid.min"));
            		    
                 }
            	 }
            }
          
        }

        if ((entity.getJobProcName() == null) || entity.getJobProcName().trim().equals(MainetConstants.BLANK)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.procOrFuncName.validation.error"));
        }

        if ((entity.getJobClassName() == null) || entity.getJobClassName().trim().equals(MainetConstants.BLANK)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.serviceClass.validation.error"));
        } else if (!entity.getJobClassName().trim().startsWith("com.abm.mainet.")) {
            entityValidationContext.addOptionConstraint(
                    getApplicationSession().getMessage("quartz.form.field.serviceClass.inValid.validation.error"));
        }

        if ((entity.getJobFuncName() == null) || entity.getJobFuncName().trim().equals(MainetConstants.BLANK)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.serviceMethod.validation.error"));
        }

        if ((entity.getStatus() == null) || entity.getStatus().trim().equals(MainetConstants.BLANK)) {
            entityValidationContext
                    .addOptionConstraint(getApplicationSession().getMessage("quartz.form.field.status.validation.error"));
        }

    }

    public QuartzSchedulerMasterValidator() {

    }

}
