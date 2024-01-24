package com.abm.mainet.common.formbuilder.ui.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.formbuilder.dto.FormBuilder;
import com.abm.mainet.common.formbuilder.dto.FormBuildersDto;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author nirmal.mahanta
 *
 */
public class FormBuilderValidator implements Validator {

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return FormBuilder.class.equals(aClass);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    private ApplicationSession appsession = ApplicationSession.getInstance();

    @Override
    public void validate(final Object object, final Errors errors) {
        final FormBuildersDto formBuildersDto = (FormBuildersDto) object;
        final List<FormBuilder> formBuildersList = formBuildersDto.getScrutinyLabelsList();
        ValidationUtils.rejectIfEmpty(errors, "scrutinyLabels.smShortDesc",
                "scrutiny.level.empty.err",
                appsession.getMessage(MainetConstants.CommonMasterUiValidator.SERVICE_NAME_NOT_EMPTY));

        int levelCounter = 0;
        int roleCounter = 0;
        int slLabelCounter = 0;
        int slLabelMarCounter = 0;
        int slDatatypeCounter = 0;
        int slFormModeCounter = 0;
        int rowCount = 1;
        for (final FormBuilder formBuilder : formBuildersList) {
            if ((levelCounter == 0) && (formBuilder.getLevels() == null)) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_LAVELS,
                        "scrutiny.level.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.SCRUTINY_NOT_EMPTY) + rowCount);
                levelCounter++;
            }
            if ((roleCounter == 0) && (formBuilder.getGmId() == null)) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_GMID,
                        "scrutiny.role.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.SELECT_ROLE) + rowCount);
                roleCounter++;
            }
            if ((slFormModeCounter == 0) && StringUtils.isEmpty(formBuilder.getSlFormMode())) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLFORM_MODE,
                        "scrutiny.mode.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.MODE_NOT_EMPTY) + rowCount);
                slFormModeCounter++;
            }
            if ((slDatatypeCounter == 0) && StringUtils.isEmpty(formBuilder.getSlDatatype())) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_SLDATATYPE,
                        "scrutiny.dadatype.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.SELECT_DATATYPE) + rowCount);
                slDatatypeCounter++;
            }
            if ((slLabelCounter == 0) && StringUtils.isEmpty(formBuilder.getSlLabel())) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLABEL,
                        "scrutiny.label.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.LABEL_NOT_EMPTY) + rowCount);
                slLabelCounter++;
            }
            if ((slLabelMarCounter == 0) && StringUtils.isEmpty(formBuilder.getSlLabelMar())) {
                ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLABELMAR,
                        "scrutiny.reg.label.empty.err",
                        appsession.getMessage(MainetConstants.CommonMasterUiValidator.REGIONAL_LABEL_NOT_EMPTY) + rowCount);
                slLabelMarCounter++;
            }
            rowCount++;
        }
    }
}
