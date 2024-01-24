package com.abm.mainet.bpm.utility;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.dto.WorkflowTaskAction;
import com.abm.mainet.bpm.domain.WorkflowAction;
import com.abm.mainet.bpm.domain.WorkflowUserTask;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Decision;

/**
 * Type Conversion Utility
 * 
 * @author sanket.joshi
 *
 */
public class ConversionUtility {

	 private static final Logger LOGGER = Logger.getLogger(ConversionUtility.class);
	 
    /**
     * 
     * @param adto
     * @return
     */
    public static WorkflowAction toAction(WorkflowTaskAction adto) {
        if (adto == null)
            return null;
        WorkflowAction action = new WorkflowAction();
        action.setId(adto.getId());
        action.setApplicationId(adto.getApplicationId());
        action.setReferenceId(adto.getReferenceId());
        action.setDecision(adto.getDecision());
        action.setComments(adto.getComments());
        action.setOrgId(adto.getOrgId());
        action.setEmpId(adto.getEmpId());
        action.setEmpType(adto.getEmpType());
        action.setTaskId(adto.getTaskId());
        action.setTaskName(adto.getTaskName());
        action.setDateOfAction(adto.getDateOfAction());
        action.setCreatedDate(adto.getCreatedDate());
        action.setCreatedBy(adto.getCreatedBy());
        action.setModifiedDate(adto.getModifiedDate());
        action.setModifiedBy(adto.getModifiedBy());
        action.setTaskSlaDurationInMS((adto.getTaskSlaDurationInMS() != null) ? adto.getTaskSlaDurationInMS() : 0l);
        return action;
    }

    public static WorkflowAction getNewActionForTask(WorkflowUserTask task) {
        if (task == null)
            return null;
        WorkflowAction action = new WorkflowAction();
        action.setApplicationId(task.getApplicationId());
        action.setReferenceId(task.getReferenceId());
        action.setDecision(Decision.ESCALATED.getValue());
        action.setOrgId(task.getOrgId());
        action.setEmpId(-1l);
        action.setEmpType(-1l);
        action.setTaskId(task.getTaskId());
        action.setTaskName(task.getTaskName());
        action.setDateOfAction(new Date());
        action.setCreatedDate(new Date());
        action.setCreatedBy(-1l);
        action.setTaskSlaDurationInMS(0l);
        action.setComments(Decision.ESCALATED.getValue());
        return action;
    }

    /**
     * 
     * @param taskDto
     * @return
     */
    public static WorkflowUserTask toTask(WorkflowTask taskDto) {
        WorkflowUserTask task = new WorkflowUserTask();
        BeanUtils.copyProperties(taskDto, task);
        return task;
    }

    /**
     * @author harshit.kumar To convert current date into string.
     * @return string value of current date object.
     */
    public static String dateToString(final Date date, final String dateFormat) {
        String strDate = MainetConstants.Common.BLANK;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(dateFormat);
            if (date != null) {
                strDate = formatter.format(date);
            }
        } catch (final Exception e) {
        }
        return strDate;
    }

    /**
     * @author harshit.kumar To convert string to date.
     * @return string value of current date object.
     */
    public static Date stringToDate(final String dateString, final String dateFormat) {
        Date date = null;
        DateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(dateFormat);
            if (dateString != null) {
                date = formatter.parse(dateString);
            }
        } catch (final Exception e) {
            return null;
        }
        return date;
    }

    /**
     * This method will convert WorkflowProcessParameter instance to HashMap, where instance field name will be key and field will
     * be value.
     * 
     * This method make use of {@code java.beans.Introspector} to retrieve bean information, and java reflection API to read field
     * values. This method will filter resulting Map by removing generated BeanInfo with propertyDescriptor name as a 'class' and
     * null values.
     * 
     * 
     * @param parameter WorkflowProcessParameter
     * @return Map<String, Object>
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * 
     * @see java.beans.Introspector
     * @see java.lang.reflect.Method
     */
    public static Map<String, Object> workflowProcessParameterToMap(WorkflowProcessParameter parameter)
            throws FrameworkException {
        Map<String, Object> parameterMap = new HashMap<>();
        try {
			BeanInfo info = Introspector.getBeanInfo(parameter.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			    Method reader = pd.getReadMethod();
			    if (reader != null)
			        parameterMap.put(pd.getName(), reader.invoke(parameter));
			}
			parameterMap.remove("class");
	        parameterMap.values().removeIf(Objects::isNull);
	        return parameterMap;
		} catch (Exception e) {
			LOGGER.error("Error workflowProcessParameterToMap", e);
			throw new FrameworkException(e);
		}
    }

}
