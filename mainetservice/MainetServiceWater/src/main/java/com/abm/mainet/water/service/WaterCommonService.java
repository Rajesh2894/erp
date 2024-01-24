package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

public interface WaterCommonService {

    public TbKCsmrInfoMH getConnectionDetail(final long orgId, final String connectionNumber);

    /**
     * Gets the connection detail.
     *
     * @param orgId the org id
     * @param connectionNumber the connection number
     * @return the connection detail
     */
    CustomerInfoDTO getConnectionDetailDto(long orgId, String connectionNumber);

    /**
     * @param applicationId
     * @param l
     */
    TbCsmrInfoDTO getApplicantInformationById(long applicationId, long orgId);

    public void updateCsmrInfo(final TbKCsmrInfoMH master, final ScrutinyLableValueDTO lableValueDTO);

    /**
     * @param applicationId
     * @param orgId
     * @param serviceShortCode
     * @return
     */
    TbCsmrInfoDTO getApplicantInformationByAppId(long applicationId, long orgId, String serviceShortCode);

    TbCsmrInfoDTO fetchConnectionDetailsByConnNo(String csCcn, Long orgid, String active);

    public TbKCsmrInfoMH fetchConnectionDetailsByConnNo(String csCcn, Long orgid);

    public TbKCsmrInfoMH getWaterConnectionDetailsById(final Long csIdn,
            final Long orgId);

    public PlumberMaster getPlumberDetailsById(final Long pluberId);

    /**
     * @param plumbId
     * @return
     */
    String getPlumberLicenceNumber(Long plumbId);

    Map<String, String> validateAndFetchProperty(final String propertyNo, final Long orgId);

    public boolean fetchConnectionFromOldCcnNumber(String csOldccn, long orgid);

	List<PlumberMaster> listofplumber(Long orgid);

	String getPlumberLicenceName(Long plumbId);

	Map<Long, Date> getReceiptDet(Long apmId, Long orgId);

	public TbCsmrInfoDTO fetchConnectionDetailsByConnNoOrOldConnNo(String csCcn, String csOldccn, Long orgid,
			String active);

	TbCsmrInfoDTO getCsmrInfoByAppIdAndOrgId(long applicationId, long orgId);

	boolean updateConnectionAppNoByCsIdn(Long csIdn, Long applicationId);

	List<Object[]> fetchCountMeterTypeCcn(Long orgId, String dateN);

	List<Object[]> fetchCountUsageTypeCcn(Long orgId, String date1);

	List<Object[]> totalPendingApplications(Long orgId, String date1);

	List<Object[]> fetchCountCcnType(Long orgId, String dateN);
}
