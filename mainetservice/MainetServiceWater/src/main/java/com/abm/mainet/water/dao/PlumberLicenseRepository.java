package com.abm.mainet.water.dao;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.domain.TbPlumRenewalScheduler;
import com.abm.mainet.water.domain.TbPlumberExperience;
import com.abm.mainet.water.domain.TbPlumberQualification;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;

/**
 * @author Arun.Chavda
 *
 */
public interface PlumberLicenseRepository {

	PlumberMaster savePlumberLicenseDetails(PlumberMaster master);

	void savePlumberLicenseExperienceDetails(List<TbPlumberExperience> experience);

	void savePlumberLicenseAcademicDetails(List<TbPlumberQualification> qualification);

	PlumberMaster getPlumberDetailsByAppId(long applicationId, Long orgId);

	List<TbPlumberQualification> getPlumberQualificationDetails(Long plumberId, Long orgId);

	List<TbPlumberExperience> getPlumberExperienceDetails(Long plumberId, Long orgId);

	boolean updatedQualificationIsDeletedFlag(Long qaulId);

	boolean updatedExpIsDeletedFlag(Long expId);

	void savePlumberInterviewDetails(PlumberMaster master);

	void updatedPlumberLicenseExecutionDetailsByDept(PlumberMaster master);

	List<TbPlumRenewalScheduler> getPlumRenewalSchedulerDetails(Long serviceId, Long orgId);

	void updateAuthStatus(String authStatus, Long authBy, Date authDate, Long applicationId, Long orgId);

	PlumberMaster getLicenseDetail(String licNo, Long orgId);

	Object[] getRenewDetail(Long plumId);

	Object[] getmasterDates(Long plumId);

	Object[] plumberRenewValidDates(Long applicationId);

	boolean updateValiDates(PlumberLicenseRequestDTO plumDto);

	List<Object[]> getReceiptDetails(PlumberLicenseRequestDTO plumDto);

	boolean updateLicNumber(PlumberLicenseRequestDTO plumDto);

	String getplumLicenseNo(Long applicationId);

	Long getLicenseDetail(Long applicationId);

	Long getApplicationNumByPlumId(Long plumId);

	boolean updatPlumberRenewalDates(PlumberLicenseRequestDTO plumDto);

	/**
	 * To Get Plumber Renewals By Plumber Id
	 * 
	 * @param plumberid
	 * @return List<PlumberRenewalRegisterMaster>
	 */
	List<PlumberRenewalRegisterMaster> getPlumberRenewals(Long plumberid);

	/**
	 * To generate Jasper Report PDF
	 * 
	 * @param plumDto
	 * @param outputStream
	 * @param oParms
	 * @param fileName
	 * @return
	 */
	byte[] generateJasperReportPDF(PlumberLicenseRequestDTO plumDto, ByteArrayOutputStream outputStream, Map oParms,
			String fileName);

	public PlumberRenewalRegisterMaster getPlumberLicenceRenewalDetailsByAppId(Long applicationId, Long orgId);

	public PlumberMaster getPlumberDetailsByPlumId(Long plumId, Long orgId);

}
