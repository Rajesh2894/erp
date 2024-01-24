package com.abm.mainet.rnl.dao;

import java.util.Date;

public interface IDemandNoticeDAO {

    Object[] getDemandNoticeDataByCond(Long contId, Long orgId, Date date, StringBuilder condition);

    Object[] getDemandRegisterDataByCond(Long contId, Long orgId, Date date, StringBuilder condition);

}
