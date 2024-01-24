package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.TbAcVendormasterEntity;

public interface TbAcVendormasterDao {

    List<TbAcVendormasterEntity> getVendorData(Long cpdVendortype, Long cpdVendorSubType, String vendor_vmvendorcode,
            Long vmCpdStatus, Long orgid);

}
