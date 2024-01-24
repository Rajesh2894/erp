package com.abm.mainet.common.master.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class ReceiptFormValidator extends BaseEntityValidator<TbServiceReceiptMasBean>{

	@Override
	protected void performValidations(TbServiceReceiptMasBean dto,
			EntityValidationContext<TbServiceReceiptMasBean> entityValidationContext) {
		
		final ApplicationSession session = ApplicationSession.getInstance();
		if (dto.getApmApplicationId() == null && dto.getDpDeptId() == null && StringUtils.isEmpty(dto.getRmLoiNo()) && dto.getRmRcptno() == null  && dto.getRmReceivedfrom().isEmpty() &&  dto.getRmDate() == null && dto.getRmAmount().isEmpty()  && dto.getRefId() == null && dto.getRmReceiptNo().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("receipt.search_data"));
		}
	}

}
