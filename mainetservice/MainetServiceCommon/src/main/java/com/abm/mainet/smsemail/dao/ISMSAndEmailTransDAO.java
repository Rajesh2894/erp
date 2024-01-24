package com.abm.mainet.smsemail.dao;

import java.util.List;

import com.abm.mainet.smsemail.domain.SmsEmailTransaction;

public interface ISMSAndEmailTransDAO {

    public abstract boolean saveMessageTemplate(SmsEmailTransaction entity);
    
    public List<SmsEmailTransaction> getSmsEmailTransaction(final long orgId, final Long empId);
    
  //  public void updateRefId4(final Long smsId);


}