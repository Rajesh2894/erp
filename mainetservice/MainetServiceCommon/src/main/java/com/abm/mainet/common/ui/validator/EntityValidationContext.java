package com.abm.mainet.common.ui.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

public final class EntityValidationContext<T extends Object> {
    private static final String NO_SUBDATA = "NoSubdata";
    private static final String NOT_SELECTED = "NotSelected";
    private static final String INVALID_DATE = "InvalidDate";
    private static final String NON_ZERO = "NonZero";
    private static final String NOT_EMPTY = "NotEmpty";
    private final T entity;
    private final ArrayList<String> errors;
    private boolean valid;

    private ApplicationSession getAppSession() {
        return ApplicationSession.getInstance();
    }

    public EntityValidationContext(final T entity) {
        this.entity = entity;
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    /**
     * To get all validation errors while performing form validation.
     * @return {@link Collection} object containing list of error messages.
     */
    public Collection<String> getErrors() {
        return this.errors;
    }

    public T getEntity() {
        return entity;
    }

    /**
     * To check whether binding result containing any validation error.
     * @return {@link Boolean} <code>true</code> if contains any validation error otherwise <code>false</code>.
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * To find sub information data size for the given parent id.
     * @param parentId the long literal containing parent id.
     * @return {@link Integer} value which is total number of sub information size.
     */
    private int getSubInformationSize(final long parentId) {
        return getAppSession().getHierarchicalForSubDetails(UserSession.getCurrent().getOrganisation(), parentId);
    }

    /**
     * To get value from defined in property resource file in application class path for given field.
     * @param field the {@link String} literal containing property field.
     * @return {@link String} containing value for the given field property if found otherwise return the same field name as value
     * .
     */
    public String getFieldLabel(final String field) {
        final String key = entity.getClass().getSimpleName() + "." + field;
        return getAppSession().getMessage(key, field);
    }

    /**
     * To get validation message from defined in property resource file in application class path for given field.
     * @param messageTemplate the {@link String} object containing validation message key.
     * @param field the {@link String} object containing placeholder for the message template rule.
     * @return {@link String} object containing actual validation message from the property file if found otherwise return message
     * template as message.
     */
    private String getValidationMessage(final String messageTemplate, final String field) {
        return getValidationMessage(messageTemplate, new Object[] { getFieldLabel(field) });
    }

    /**
     * To get validation message from defined in property resource file in application class path for given optional .
     * @param messageTemplate the {@link String} object containing validation message key.
     * @param args array of arguments that will be filled in for params within the message (params look like "{0}", "{1,date}",
     * "{2,time}" within a message), or <code>null</code> if none.
     * @return {@link String} object containing actual validation message from the property file if found otherwise return message
     * template as message.
     */
    private String getValidationMessage(final String messageTemplate, final Object... object) {
        return getAppSession().getMessage(messageTemplate, object);
    }

    /**
     * To add constraints message in error list of validation for not-empty field.
     * @param field the {@link String} literal containing filed name on which constraints to be apply.
     */
    private void addNotEmptyConstraint(final String field) {
        addConstraint(getValidationMessage(NOT_EMPTY, field));
    }

    /**
     * To check not-zero constraints on field.
     * @param field the {@link String} literal containing filed name on which constraints to be apply.
     */
    private void addNonZeroConstraint(final String field) {
        addConstraint(getValidationMessage(NON_ZERO, field));
    }

    /**
     * To add constraints message in error list of validation for given message template code.
     * @param messageTemplate the {@link String} literal containing message template code.
     */
    private void addConstraint(final String messageTemplate) {
        valid = false;
        errors.add(messageTemplate);
    }

    /**
     * To add option constraints message in error list of validation for given message template code.
     * @param messageTemplate the {@link String} literal containing message template code.
     */
    public void addOptionConstraint(final String messageTemplate) {
        addConstraint(getValidationMessage(messageTemplate, ""));
    }

    public void addOptionConstraint(final String messageTemplate, final String fieldName) {
        addConstraint(getValidationMessage(messageTemplate, fieldName));
    }

    public boolean rejectIfEmpty(final Object value, final String field) {
        if (value == null) {
            addNotEmptyConstraint(field);
        }

        return true;
    }

    /**
     * To check validation on field whether field is empty or not.
     * @param value the {@link String} containing value of the field.
     * @param field the {@link String} containing field on which validation to be apply.
     * @return {@link Boolean} <code>true</code> if value is not empty and containing some alphanumeric value otherwise
     * <code>false</code>.
     */
    public boolean rejectIfEmpty(final String value, final String field) {
        if ((value == null) || !StringUtils.hasText(value)) {
            addNotEmptyConstraint(field);

            return false;
        } else {
            return true;
        }
    }

    /**
     * Overloaded method for to check validation on field whether field contains <strong>0<strong> or less value .
     * @param value the {@link Long} containing value of the field.
     * @param field the {@link String} containing field on which validation to be apply.
     * @return {@link Boolean} <code>true</code> if value is not zero or less otherwise <code>false</code>.
     */
    private boolean rejectIfEmpty(final Long value, final String field) {

        if (value == null) {
            addNotEmptyConstraint(field);

            return false;
        } else if (value <= 0L) {
            addNonZeroConstraint(field);

            return false;
        }

        return true;
    }

    /**
     * Overloaded method for to check validation on field whether field contains no value .
     * @param value the {@link Long} containing value of the field.
     * @param field the {@link String} containing field on which validation to be apply.
     * @return {@link Boolean} <code>true</code> if value is not zero or less otherwise <code>false</code>.
     */
    private boolean rejectIfEmptyAllowZero(final Long value, final String field) {

        if (value == null) {
            addNotEmptyConstraint(field);

            return false;
        }
        return true;
    }

    public boolean rejectIfEmpty(final Long value, final String field, final boolean isSelect) {
        if (!isSelect) {
            return rejectIfEmpty(value, field);
        }

        if ((value == null) || (value <= 0L)) {
            addNotEmptyConstraint(field);

            return false;
        }
        return true;
    }

    private boolean rejectIfEmpty(final Integer value, final String field) {

        if (value == null) {
            addNotEmptyConstraint(field);

            return false;
        } else if (value <= 0L) {
            addNonZeroConstraint(field);

            return false;
        }

        return true;
    }

    public boolean rejectIfEmpty(final Integer value, final String field, final boolean isSelect) {
        if (!isSelect) {
            return rejectIfEmpty(value, field);
        }

        if ((value == null) || (value <= 0L)) {
            addNotEmptyConstraint(field);

            return false;
        }
        return true;
    }

    /**
     * To check validation for invalid date for the given {@link String} date value.
     * <p>
     * For valid date date should contains pattern:
     *
     * <pre>
     * 	<strong>dd/MM/yyyy</strong> or
     * 	<strong>dd/MM/yyyy hh:mm am/pm</strong>
     * </pre>
     *
     * So the value should passed the above defined pattern.
     * </p>
     * @param value the date value to be validate.
     * @param field the {@link String} containing field on which validation to be apply.
     * @return {@link Boolean} <code>true</code> if value has valid date otherwise <code>false</code>.
     */
    public boolean rejectInvalidDate(final String value, final String field) {
        if (!rejectIfEmpty(value, field)) {
            return false;
        }

        final DateValidator dateValidator = ApplicationContextProvider.getApplicationContext().getBean(DateValidator.class);

        if (!dateValidator.isValidDate(value)) {
            addConstraint(getValidationMessage(INVALID_DATE, field));
        }

        return true;
    }

    /**
     * To check validation for invalid date for the given {@link Date} date object.
     * <p>
     * For valid date date should contains pattern:
     *
     * <pre>
     * 	<strong>dd/MM/yyyy</strong> or
     * 	<strong>dd/MM/yyyy hh:mm am/pm</strong>
     * </pre>
     *
     * So the value should passed the above defined pattern.
     * </p>
     * @param value the date value to be validate.
     * @param field the {@link String} containing field on which validation to be apply.
     * @return {@link Boolean} <code>true</code> if value has valid date otherwise <code>false</code>.
     */
    public boolean rejectInvalidDate(final Date value, final String field) {
        if (value == null) {
            addConstraint(getValidationMessage(INVALID_DATE, field));
            return false;
        }

        return true;
    }

    /**
     * To check validation for field containing default section or not for given {@link String} value.
     * @param value the value of the selected field.
     * @param field the {@link String} containing field name.
     * @return {@link Boolean} <code>true</code> if field have not contain default value otherwise <code>false</code>.
     */
    public boolean rejectIfNotSelected(final String value, final String field) {
        return rejectIfNotSelected(value, field, false);
    }

    /**
     * To check validation for field containing default section or not for given {@link String} value.
     * <p>
     * <strong>hierarchical</strong> parameter defines whether field has child information or not. If it set to true then it will
     * get child information and if not found any data then it will be rejected with no child data available for selected field.
     * </p>
     * @param value the value of the selected field.
     * @param field the {@link String} containing field name.
     * @return {@link Boolean} <code>true</code> if field have not contain default value otherwise <code>false</code>.
     */
    private boolean rejectIfNotSelected(final String value, final String field, final boolean hierarchical) {
        if (!StringUtils.hasText(value)) {
            addConstraint(getValidationMessage(NOT_SELECTED, field));
        }

        else if (value.equals(MainetConstants.ZERO)) {
            addConstraint(getValidationMessage(NOT_SELECTED, field));
        } else if (hierarchical && (this.getSubInformationSize(Long.parseLong(value)) == 0)) {
            addConstraint(getValidationMessage(NO_SUBDATA, field));
        }

        return true;
    }

    /**
     * To check validation for field containing default section or not for given {@link Long} value.
     * @param value the value of the selected field.
     * @param field the {@link String} containing field name.
     * @return {@link Boolean} <code>true</code> if field have not contain default value otherwise <code>false</code>.
     */
    public boolean rejectIfNotSelected(final long value, final String field) {
        if (value == 0L) {
            addConstraint(getValidationMessage(NOT_SELECTED, field));
            return false;
        }

        return true;
    }

    /**
     * To check validation for given list is empty or not.
     * @param gridList the {@link List} containing data list.
     * @param field the {@link String} containing key field.
     * @return {@link Boolean} <code>true</code> if list is not empty otherwise <code>false</code>.
     */
    public <TEnitity extends BaseEntity> boolean rejectEmptyList(final List<TEnitity> gridList, final String field) {
        if (gridList != null) {
            if (gridList.size() == MainetConstants.EMPTY_LIST) {
                addConstraint(getValidationMessage(MainetConstants.CommonConstants.EMPTY_LIST, field));
            }
        } else {
            addConstraint(getValidationMessage(MainetConstants.NOT_NULL, field));

        }

        return true;
    }

    /**
     * To check validation for given from and to date values.
     * <p>
     * If the from date is greater than the to date then it will reject.
     * </p>
     * @param fromValue the {@link Date} containing from date value.
     * @param fromField the {@link String} containing from date field name.
     * @param fromValue the {@link Date} containing from date value.
     * @param fromField the {@link String} containing from date field name.
     * @return {@link Boolean} <code>true</code> if from date is smaller than to date otherwise <code>false</code>.
     */
    public boolean rejectCompareDate(final Date fromValue, final String fromField, final Date toValue, final String toField) {

        valid = rejectInvalidDate(fromValue, fromField);
        valid = rejectInvalidDate(toValue, toField);

        if (valid && (fromValue.compareTo(toValue) > 0)) {
            addConstraint(getValidationMessage(MainetConstants.DATE_DIFF, new Object[] {
                    getFieldLabel(toField),
                    getFieldLabel(fromField) }));
        }
        return true;
    }

    /**
     * To check validation for from and to value are in the the valid range value.
     * <p>
     * To test whether form value is smaller then to value or not.
     * </p>
     * @param fromValue the {@link Long} containing from value.
     * @param fromField the {@link String} containing from field name.
     * @param toValue the {@link Long} containing to value.
     * @param toField the {@link String} containing to field name.
     * @return {@link Boolean} <code>true</code> if from and to value are in the given range otherwise <code>false</code>
     */
    public boolean rejectNotInRangeFromZero(final Long fromValue, final String fromField, final Long toValue,
            final String toField) {
        valid = rejectIfEmptyAllowZero(fromValue, fromField);
        valid = rejectIfEmpty(toValue, toField) && valid;

        if (valid) {
            if (fromValue > toValue) {
                addConstraint(getValidationMessage(MainetConstants.FROM_TO_RANGE, new Object[] {
                        getFieldLabel(fromField),
                        getFieldLabel(toField) }));
            }
        }

        return true;

    }

    /**
     * To check validation for from and to value are in the the valid range value.
     * <p>
     * To test whether form value is smaller then to value or not.
     * </p>
     * @param fromValue the {@link Long} containing from value.
     * @param fromField the {@link String} containing from field name.
     * @param toValue the {@link Long} containing to value.
     * @param toField the {@link String} containing to field name.
     * @return {@link Boolean} <code>true</code> if from and to value are in the given range otherwise <code>false</code>
     */
    public boolean rejectNotInRange(final Long fromValue, final String fromField, final Long toValue, final String toField) {
        valid = rejectIfEmpty(fromValue, fromField);
        valid = rejectIfEmpty(toValue, toField) && valid;

        if (valid) {
            if (fromValue > toValue) {
                addConstraint(getValidationMessage(MainetConstants.FROM_TO_RANGE, new Object[] {
                        getFieldLabel(fromField),
                        getFieldLabel(toField) }));
            }
        }

        return true;

    }

    /**
     * To check validation for from and to value are in the the given range value.
     * @param fromValue the {@link Long} containing from value.
     * @param fromField the {@link String} containing from field name.
     * @param toValue the {@link Long} containing to value.
     * @param toField the {@link String} containing to field name.
     * @param rangeValue the {@link Long} containing rage value.
     * @return {@link Boolean} <code>true</code> if from and to value are in the given range otherwise <code>false</code>
     */
    public boolean rejectNotInRange(final Long fromValue, final String fromField, final Long toValue, final String toField,
            final Long rangeValue) {
        rejectIfEmpty(fromValue, fromField);
        rejectIfEmpty(toValue, toField);
        return true;

    }

    /**
     * This method is used for validation fields as per pattern provided by user
     * @param value entered value of entity
     * @param pattern getting pattern which want to validate
     * @param field getting name of field
     * @param label getting label which want to show from property file
     * @return true if valid else false with message
     */

    public boolean rejectPatternMatcher(final String value, final String pattern, final String field, final String label) {
        final PatternValidator patternValidator = new PatternValidator(pattern);

        if (!patternValidator.matchPattern(value)) {
            addConstraint(getValidationMessage(label, field));
        }
        return true;
    }

    /**
     * To check validation for current row date and previous row date are valid or not.
     * @param currentDate the {@link Date} containing current row date.
     * @param currentRow the {@link Long} literal containing current row id.
     * @param prevDate the {@link Date} containing previous row date.
     * @param prevRow the {@link Long} literal containing previous row id.
     * @return {@link Boolean} <code>true</code> if dates are in valid range otherwise <code>false</code>.
     */
    public boolean rejectIfIvalidRowDate(final Date currentDate, final long currentRow, final Date prevDate, final long prevRow) {
        addConstraint(getValidationMessage(MainetConstants.INVALID_ROW_DATE, new Object[] { currentRow, prevRow }));

        return true;

    }

    /**
     * This function is used when we have check that if any date value is present in between parent from and To date.
     * @param pFrValue
     * @param pFrField
     * @param pToValue
     * @param pToField
     * @param cmpDtValue
     * @param cmpDtField
     * @return
     */
    public boolean compareParentFromToRange(final Date pFrValue, final String pFrField, final Date pToValue,
            final String pToField, final Date cmpDtValue,
            final String cmpDtField) {
        valid = rejectIfEmpty(cmpDtValue.getTime(), cmpDtField);

        if (valid) {
            if ((cmpDtValue.compareTo(pFrValue) < 0) || (cmpDtValue.compareTo(pToValue) > 0)) {
                addConstraint(getValidationMessage(MainetConstants.PARENT_FROM_TO_RANGE, new Object[] {
                        getFieldLabel(cmpDtField),
                        getFieldLabel(pFrField),
                        getFieldLabel(pToField) }));
                return false;
            }
        }
        return true;

    }

    public boolean rejectIfNull(final Object value, final String field) {
        if (value != null) {
            return true;
        } else {
            addNotEmptyConstraint(field);

            return false;
        }

    }

    public boolean compareWithCurrentDate(final Date field, final String label) {

        if (field == null) {
            addConstraint(getValidationMessage(INVALID_DATE, field));
        } else {
            if (field.after(new Date())) {
                addConstraint(getValidationMessage(MainetConstants.CURRENT_DATE_RANGE, label));
            }
        }

        return true;
    }

    public boolean rejectIfEmpty(final Double value, final String field) {
        if (value == null) {
            addNotEmptyConstraint(field);

            return false;
        }

        return true;

    }

    public boolean rejectIfInvalid(final String message, final String field) {
        addConstraint(getValidationMessage(message, field));
        return false;

    }

    public void checkExistsRow(final String string, final Long order) {

        addConstraint(getValidationMessage(string, order));
    }
}
