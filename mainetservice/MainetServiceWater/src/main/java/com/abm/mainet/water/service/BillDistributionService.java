package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillMas;

public interface BillDistributionService {

    boolean updateBillDueDate(List<TbBillMas> billMas, Organisation organisation, Long distributionType);
}
