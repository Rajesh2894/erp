package com.abm.mainet.smsemail.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterfaceDTO;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Transactional(readOnly = true)
public interface ISMSAndEmailService extends Serializable {

    public boolean saveMessageTemplate(SMSAndEmailInterface entity, String modeType);

    public boolean checkExistanceForEdit(SMSAndEmailInterface entity);

    public Boolean sendEmailSMS(String deptCode, String menuURL, String type, SMSAndEmailDTO dto, Organisation organisation,
            int langId);

    public List<SMSAndEmailInterfaceDTO> searchTemplateFromDB(SMSAndEmailInterface entity);

    public SMSAndEmailInterface getTemplate(Long orgId, Long templateId);

    void deleteTemplate(Long seId);

    public SMSAndEmailInterface getTemplateFromDB(SMSAndEmailInterface entity);

    public void sendEmailAndSMS(SMSAndEmailDTO dto, long orgid);

    public List<Object[]> findEventList(String endwithhtml, String string);

    public void sendHTMLNewsLetterEmail(EMail sendData, Organisation orgId);

    public void sendEmailSMS(TemplateLookUp lookup, SMSAndEmailDTO dto, Organisation organisation);

}
