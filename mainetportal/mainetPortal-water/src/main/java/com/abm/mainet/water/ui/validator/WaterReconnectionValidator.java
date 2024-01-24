package com.abm.mainet.water.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;

@Component
public class WaterReconnectionValidator extends BaseEntityValidator<WaterReconnectionRequestDTO> {

	@Override
	protected void performValidations(final WaterReconnectionRequestDTO entity,
			final EntityValidationContext<WaterReconnectionRequestDTO> entityValidationContext) {

		if ((entity.getApplicantTitle() != null) && (entity.getApplicantTitle() == 0l)) {
			entityValidationContext.addOptionConstraint("water.title");
		}

		if ((entity.getFirstName() != null) && MainetConstants.BLANK.equals(entity.getFirstName())) {
			entityValidationContext.addOptionConstraint("water.firstName");
		}

		if ((entity.getLastName() != null) && MainetConstants.BLANK.equals(entity.getLastName())) {
			entityValidationContext.addOptionConstraint("water.lastName");
		}

		if ((entity.getMobileNo() != null) && MainetConstants.BLANK.equals(entity.getMobileNo())) {
			entityValidationContext.addOptionConstraint("water.mobile");
		}

		if ((entity.getEmailId() != null) && !entity.getEmailId().equals(MainetConstants.BLANK)) {
			if (!Pattern.compile(MainetConstants.EMAIL_PATTERN).matcher(entity.getEmailId()).matches()) {
				entityValidationContext.addOptionConstraint("water.emailID");
			}
		}

		if ((entity.getAreaName() != null) && MainetConstants.BLANK.equals(entity.getAreaName())) {
			entityValidationContext.addOptionConstraint("water.area");
		}

		if ((entity.getPincode() != null) && MainetConstants.BLANK.equals(entity.getPincode())) {
			entityValidationContext.addOptionConstraint("water.pincode");
		}

		if ((entity.getConnectionNo() != null) && entity.getConnectionNo().equals(MainetConstants.BLANK)) {
			entityValidationContext.addOptionConstraint("water.billPayment.search");
		}

		if ((entity.getPlumber() != null) && entity.getPlumber().equals(MainetConstants.BLANK)) {
			entityValidationContext.addOptionConstraint("water.plumberDetails");
		}

		if ((entity.getPlumberId() == null)) {
			entityValidationContext.addOptionConstraint("Please Select Plumber Name");
		}

		if ((entity.getDocumentList() != null) && !entity.getDocumentList().isEmpty()) {
			for (final DocumentDetailsVO doc : entity.getDocumentList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
					if (doc.getDocumentByteCode() == null) {
						entityValidationContext.addOptionConstraint("upload.doc");
						break;
					}
				}
			}
		}

	}

}
