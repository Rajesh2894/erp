package com.abm.mainet.smsemail.dao;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.TemplateLookUp;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;

public interface ISMSAndEmailDAO {

    public abstract boolean saveMessageTemplate(SMSAndEmailInterface entity);

    public TemplateLookUp getTemplateFromDBForTran(String deptCode, String menuURL, String type, Organisation organisation,
            int langId);

    public boolean checkExistanceForEdit(SMSAndEmailInterface entity);

    public TemplateLookUp getTemplate(String deptCode, String menuURL, String type, Long orgId, int langId);

    public List<SMSAndEmailInterface> searchTemplates(SMSAndEmailInterface interfaces);

    public SMSAndEmailInterface getTemplateById(Long orgId, Long templateId);

    void deleteTemplate(Long seId);
}