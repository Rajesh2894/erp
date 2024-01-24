/**
 * 
 */
package com.abm.mainet.adh.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.adh.domain.NewAdvertisementApplication;

/**
 * @author anwarul.hassan
 * @since 28-Nov-2019
 */
public interface IAdvertisementDataEntryDao {
    List<NewAdvertisementApplication> searchDataEntry(Long orgId, Long agencyId, Long licenseType, String adhStatus, Long locId,java.sql.Date licenFromDate,java.sql.Date licenToDate);
}
