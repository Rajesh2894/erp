package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TBCsmrrCmdMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;

/**
 * @author deepika.pimpale
 *
 */
public interface NewWaterRepository {

    /**
     * @param csmrInfo
     */
    void saveCsmrInfo(TbKCsmrInfoMH csmrInfo);

    /**
     * @param plumId
     * @return
     */
    PlumberMaster getVerifiedPlumberId(String plumId);

    /**
     * @param applicationId
     * @param org
     * @return
     */
    TbKCsmrInfoMH getApplicantInformationById(long applicationId, long org);

    /**
     * @param master
     */
    void updateCsmrInfo(TbKCsmrInfoMH master);

    /**
     * @param instId
     * @param orgid
     * @return
     */
    Long getAvgConsumtionById(Long instId, long orgid);

    /**
     * @param rcLength
     * @param org
     * @return
     */
    Double getSlopeValueByLength(Long rcLength, Organisation org);

    /**
     * @param diameter
     * @param organisation
     * @return
     */
    Double getDiameteSlab(Double diameter, Organisation organisation);

    /**
     * @param entityDTO
     * @param meteredtype
     * @param endDate
     */
    List<Object[]> findWaterRecordsForMeterReading(MeterReadingDTO entityDTO, Long meteredtype, Date endDate);

    TbKCsmrInfoMH getWaterConnectionDetailsById(Long consumerIdNo, Long orgId);

    /**
     * @param entityDTO
     * @param meteredtype
     * @return
     */
    List<Object[]> findNewWaterRecordsForMeterReading(MeterReadingDTO entityDTO, Long meteredtype);

    /**
     * @param master
     * @return
     */
    List<TbWtCsmrRoadTypes> getRoadListById(TbKCsmrInfoMH master);

    List<TbWtCsmrRoadTypes> getReconnectionRoadDiggingListByAppId(Long applicationId, Long orgId);

    void saveReconnectionRoadDiggingDetails(List<TbWtCsmrRoadTypes> csmrRoadTypesEntity);

    /**
     * @param entity
     * @param contype
     * @return
     */
    List<TbKCsmrInfoMH> getwaterRecordsForBill(TbCsmrInfoDTO entity, String contype);

    /**
     * @param entity
     * @param conType
     * @param createGridSearchDTO
     * @param createPagingDTO
     * @return
     */
    List<Object[]> getBillPrintingSearchDetail(TbCsmrInfoDTO entity, String conType);

    /**
     * @param ccnNumber
     * @param orgid
     * @return
     */
    TbKCsmrInfoMH getWaterConnectionDetailsConNumber(String ccnNumber, long orgid);

    TbKCsmrInfoMH fetchConnectionDetails(String csCcn, Long orgid, String active);

    List<Object[]> fetchBillsForDistributionEntry(WardZoneBlockDTO entity, String billCcnType, Long distriutionType,
            String connectionNo, long orgId);

    List<Object[]> fetchNoticesForDistributionEntry(WardZoneBlockDTO entity, String billCcnType, String connectionNo,
            long orgId, Long noticeType);

    TbKCsmrInfoMH fetchConnectionDetailsByConnNo(String csCcn, Long orgid);

    TbKCsmrInfoMH fetchConnectionByCsIdn(Long csIdn, Long orgid);

    TbKCsmrInfoMH fetchConnectionFromOldCcnNumber(String csOldccn, long orgid);

    List<TbKCsmrInfoMH> getAllConnectionMasterForBillSchedulerMeter(long orgid);

    List<TbKCsmrInfoMH> getAllConnectionMasterForBillSchedulerNonMeter(long orgid);

    int getTotalSearchCount(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO);

    List<TbKCsmrInfoMH> searchConnectionForView(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO);

    TbKCsmrInfoMH getConnectionDetailsById(Long csIdn);

    List<TbKCsmrInfoMH> getAllConnectionMasterByMoblieNo(String mobNo, Long orgid);

    /**
     * get All Illegal Connection Notice
     * 
     * @param orgId
     * @return
     */
    List<TbKCsmrInfoMH> getAllIllegalConnectionNotice(WaterDataEntrySearchDTO searchDTO);

    /**
     * Fetch Connection By Illegal Notice No
     * 
     * @param noticeNo
     * @param orgid
     * @return TbKCsmrInfoMH
     */
    TbKCsmrInfoMH fetchConnectionByIllegalNoticeNo(String noticeNo, Long orgid);

    Long checkValidConnectionNumber(String ccnNo, Long orgId);

    String checkEntryFlag(Long csIdn, Long orgId);
    
    public Long getconnIdByConnNo(String connNo,Long orgId);
    
    public Long getconnIdByOldConnNo(String oldConnNo,Long orgId);
    
    Long getPlumIdByApplicationId(Long applicationId, Long orgId);
    
    Long getCsIdnByApplicationId(Long applicationId, Long orgId);
    
    List<Long> getCsidnsByPropNo(String PropNo);
    
    void updateMobileNumberOfConMaster(final Long csIdn, final Long orgId, final String mobileNo,final String emailId);

	Long getMeternoByConNo(String csCcn, Long orgid, String active);

	Map<Long, Date> getReceiptDet(Long apmId, Long orgId);

	TbKCsmrInfoMH fetchConnectionDet(String csCcn, String csOldccn, Long orgid, String active);

	Double getSlopeValueByRoadLengthAndOrg(Long rcLength, Organisation org);

	Double getConnectionSizeByDFactor(Long d_Factor, Organisation org);

	CsmrInfoHistEntity getCsmrHistByCsIdAndOrgId(Long csIdn, Long orgId);

	/**
	 * This method is used to initialize lazy association of object distribution
	 * @param applicationId
	 * @param orgId
	 * @return TbKCsmrInfoMH
	 */
	TbKCsmrInfoMH getCsmrInfoByAppIdAndOrgId(long applicationId, long orgId);

	/**
	 * Update csmr History
	 */
	void updateCsmrInfoHistory(TbKCsmrInfoMH tbKCsmrInfoMH);

	TBCsmrrCmdMas getDistributionByCsIdn(long csIdn, long orgId);

	TbKCsmrInfoMH getActiveConnectionByCsCcnAndOrgId(String csCcn, Long orgId, String activeFlag);

	TbKCsmrInfoMH getConnectionByCsCcnAndOrgId(String csCcn, Long orgId);

}

