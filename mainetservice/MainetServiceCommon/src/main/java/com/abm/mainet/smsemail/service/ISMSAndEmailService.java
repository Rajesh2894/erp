package com.abm.mainet.smsemail.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterfaceDTO;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Transactional
public interface ISMSAndEmailService {

	public boolean saveMessageTemplate(SMSAndEmailInterface entity, String modeType);

	public boolean checkExistanceForEdit(SMSAndEmailInterface entity);

	public boolean sendEmailSMS(String deptCode, String menuURL, String type, SMSAndEmailDTO dto,
			Organisation organisation, int langId);

	ResponseEntity<?> validate(WSRequestDTO requestDTO);

	// public boolean sendEmailSMS(SMSAndEmailDTO dto);

	public List<SMSAndEmailInterfaceDTO> searchTemplateFromDB(SMSAndEmailInterface entity);

	public SMSAndEmailInterface getTemplate(Long orgId, Long templateId);

	void deleteTemplate(Long seId);

	public List<SmsEmailTransactionDTO> getEmailSMS(final SMSAndEmailDTO dto);

	// boolean sendTestEmailSMS(SMSAndEmailDTO dto);

}
