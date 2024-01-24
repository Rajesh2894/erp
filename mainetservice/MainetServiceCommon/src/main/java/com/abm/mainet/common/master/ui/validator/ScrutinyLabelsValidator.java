package com.abm.mainet.common.master.ui.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelsEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ScrutinyLabelsDto;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbScrutinyLabels;
import com.abm.mainet.common.master.repository.TbScrutinyLabelsJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbScrutinyLabelsService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author nirmal.mahanta
 *
 */
public class ScrutinyLabelsValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(final Class<?> aClass) {
		return TbScrutinyLabels.class.equals(aClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	private ApplicationSession appsession = ApplicationSession.getInstance();

	@Override
	public void validate(final Object object, final Errors errors) {
		final ScrutinyLabelsDto scrutinyLabelsDto = (ScrutinyLabelsDto) object;
		final List<TbScrutinyLabels> scrutinyLabelsList = scrutinyLabelsDto.getScrutinyLabelsList();

		ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINYLABELS_SCRUTNITYID,
				"scrutiny.level.empty.err",
				appsession.getMessage(MainetConstants.CommonMasterUiValidator.SERVICE_NAME_NOT_EMPTY));
		try {
			Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			if (orgid != null && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)) {
				Long triCod1 = scrutinyLabelsDto.getScrutinyLabels().getTriCod1();
				if (scrutinyLabelsDto.getScrutinyLabels().getScrutinyId() != null && (triCod1 != null)) {
					String deptShortCode = ApplicationContextProvider.getApplicationContext()
							.getBean(TbScrutinyLabelsService.class)
							.getServiceCode(scrutinyLabelsDto.getScrutinyLabels().getScrutinyId(), orgid);
					if (deptShortCode != null && deptShortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
						List<TbScrutinyLabelsEntity> entity = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsJpaRepository.class)
								.checkScrutinyLabelExistOrNot(scrutinyLabelsDto.getScrutinyLabels().getScrutinyId(),
										orgid, scrutinyLabelsDto.getScrutinyLabels().getTriCod1());
						String formMode = scrutinyLabelsDto.getScrutinyLabels().getFormMode();
						if ((entity != null && !entity.isEmpty())
								&& (formMode != null && !formMode.equals(MainetConstants.Actions.UPDATE))) {
							ValidationUtils.rejectIfEmpty(errors,
									MainetConstants.CommonMasterUiValidator.SCRUTINY_SLDATATYPE,
									"scrutiny.dadatype.empty.err", appsession.getMessage(
											MainetConstants.CommonMasterUiValidator.SCRUTINY_LABEL_ALREADY_EXIST));
						}
					}
				}
			}
		} catch (Exception e) {

		}
		int levelCounter = 0;
		int roleCounter = 0;
		int slLabelCounter = 0;
		int slLabelMarCounter = 0;
		int slDatatypeCounter = 0;
		int slFormModeCounter = 0;

		for (final TbScrutinyLabels scrutinyLabels : scrutinyLabelsList) {
			if ((levelCounter == 0) && (scrutinyLabels.getLevels() == null)) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_LAVELS,
						"scrutiny.level.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.SCRUTINY_NOT_EMPTY));
				levelCounter++;
			}
			if ((roleCounter == 0) && (scrutinyLabels.getGmId() == null)) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_GMID,
						"scrutiny.role.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.SELECT_ROLE));
				roleCounter++;
			}
			if ((slFormModeCounter == 0) && StringUtils.isEmpty(scrutinyLabels.getSlFormMode())) {
				ValidationUtils.rejectIfEmpty(errors,
						MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLFORM_MODE, "scrutiny.mode.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.MODE_NOT_EMPTY));
				slFormModeCounter++;
			}
			if ((slDatatypeCounter == 0) && StringUtils.isEmpty(scrutinyLabels.getSlDatatype())) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_SLDATATYPE,
						"scrutiny.dadatype.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.SELECT_DATATYPE));
				slDatatypeCounter++;
			}
			if ((slLabelCounter == 0) && StringUtils.isEmpty(scrutinyLabels.getSlLabel())) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLABEL,
						"scrutiny.label.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.LABEL_NOT_EMPTY));
				slLabelCounter++;
			}
			if ((slLabelMarCounter == 0) && StringUtils.isEmpty(scrutinyLabels.getSlLabelMar())) {
				ValidationUtils.rejectIfEmpty(errors, MainetConstants.CommonMasterUiValidator.SCRUTINY_LABELS_SLABELMAR,
						"scrutiny.reg.label.empty.err",
						appsession.getMessage(MainetConstants.CommonMasterUiValidator.REGIONAL_LABEL_NOT_EMPTY));
				slLabelMarCounter++;
			}

		}
	}
}
