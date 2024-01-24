package com.abm.mainet.water.dao;

import java.util.List;

import com.abm.mainet.water.domain.TbWtBillScheduleEntity;

public interface WaterBillScheduleDAO {

	List<TbWtBillScheduleEntity> searchBillingData(String string);

	int validateBillingData(String string);

}
