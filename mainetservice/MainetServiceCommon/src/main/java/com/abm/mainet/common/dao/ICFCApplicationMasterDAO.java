package com.abm.mainet.common.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

public interface ICFCApplicationMasterDAO {

    public abstract TbCfcApplicationMstEntity getCFCApplicationMasterByApplicationId(long applicationId, long orgId);

    public long getServiceIdByApplicationId(long applicationId, long orgId);

    public CFCApplicationAddressEntity getApplicantsDetailsDao(long apmApplicationId);

    public TbCfcApplicationMstEntity getCFCApplicationByApplicationId(long applicationId, long orgId);

    boolean updateApprovedBy(TbCfcApplicationMstEntity entity);

    public abstract boolean updateCFCApplicationMasterPaymentStatus(long applicationId, String paid, long orgid);

    public abstract TbCfcApplicationMstEntity getApplicationMaster(long applicationId, Organisation orgnisation);

    public void updateCFCApplicationStatus(String status, long applicationId);

    public abstract TbCfcApplicationMstEntity getCFCApplicationByRefNoOrAppNo(String refNumber, Long applicationId, Long orgid);

    public void updateIssuanceDataInCFCApplication(Long applicationId, Long issuedBy, Long orgId);

	TbCfcApplicationMstEntity getCFCApplicationByOnlyApplicationId(long applicationId);

	List<TbCfcApplicationMstEntity> getApplicantList(Long orgid);

	Long getTransactionsList(Long orgid, Long deptId, Date daten);

	Long getCountClosedApplications(Long orgid, Long deptId, String dateSet);

	Long getTodaysApprovedApplications(Long orgId, Long deptId, String dateSet);
	
	List<Object[]> getPropTodaysMovedAppl(Long orgId, Long deptId, String dateSet);

	
	

}