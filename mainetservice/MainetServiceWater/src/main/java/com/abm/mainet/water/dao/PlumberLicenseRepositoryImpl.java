package com.abm.mainet.water.dao;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.utility.ApplicationDatasourceLoader;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.domain.TbPlumRenewalScheduler;
import com.abm.mainet.water.domain.TbPlumberExperience;
import com.abm.mainet.water.domain.TbPlumberQualification;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author Arun.Chavda
 *
 */
@Repository
public class PlumberLicenseRepositoryImpl extends AbstractDAO<PlumberMaster> implements PlumberLicenseRepository {

	@Override
	public PlumberMaster savePlumberLicenseDetails(final PlumberMaster master) {
		entityManager.persist(master);
		return master;
	}

	@Override
	public void savePlumberLicenseExperienceDetails(final List<TbPlumberExperience> experience) {
		for (final TbPlumberExperience experienceEntity : experience) {
			if (experienceEntity.getPlumExpId() == 0l) {
				entityManager.persist(experienceEntity);
			} else {
				experienceEntity.setUpdatedDate(new Date());
				experienceEntity.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				experienceEntity.setLgIpMacUpd(Utility.getMacAddress());
				entityManager.merge(experienceEntity);
			}
		}
	}

	@Override
	public void savePlumberLicenseAcademicDetails(final List<TbPlumberQualification> qualification) {
		for (final TbPlumberQualification tbPlumberQualification : qualification) {
			if (tbPlumberQualification.getPlumQualId() == 0l) {
				entityManager.persist(tbPlumberQualification);
			} else {
				tbPlumberQualification.setUpdatedDate(new Date());
				tbPlumberQualification.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				tbPlumberQualification.setLgIpMacUpd(Utility.getMacAddress());
				entityManager.merge(tbPlumberQualification);
			}
		}
	}

	@Override
	public PlumberMaster getPlumberDetailsByAppId(final long applicationId, final Long orgId) {
		final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.PLUMBER_LICENSE_DETAIL);
		query.setParameter("applicationId", applicationId);
		query.setParameter("orgId", orgId);
		final PlumberMaster plumberMaster = (PlumberMaster) query.getSingleResult();
		return plumberMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbPlumberQualification> getPlumberQualificationDetails(final Long plumberId, final Long orgId) {
		final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.PLUMBER_QUALIFICATION_DETAIL);
		query.setParameter("plumId", plumberId);
		query.setParameter("orgId", orgId);
		query.setParameter("isDeleted", MainetConstants.PlumberLicense.ISDELETED_N);
		final List<TbPlumberQualification> plumberQualifications = query.getResultList();
		return plumberQualifications;
	}

	@Override
	public List<TbPlumberExperience> getPlumberExperienceDetails(final Long plumberId, final Long orgId) {
		final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.PLUMBER_EXPERIENCE_DETAIL);
		query.setParameter("plumId", plumberId);
		query.setParameter("orgId", orgId);
		query.setParameter("isDeleted", MainetConstants.PlumberLicense.ISDELETED_N);
		@SuppressWarnings("unchecked")
		final List<TbPlumberExperience> plumberQualifications = query.getResultList();
		return plumberQualifications;
	}

	@Override
	public boolean updatedQualificationIsDeletedFlag(final Long qaulId) {
		try {
			final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.UPDATE_PLUM_QUAL_ISDELETED);
			query.setParameter("isDeleted", MainetConstants.PlumberLicense.ISDELETED_Y);
			query.setParameter("plumQualId", qaulId);
			query.executeUpdate();
		} catch (final Exception e) {
			logger.error("Error occured during update Qualification IsDeleted Flag", e);
		}
		return true;
	}

	@Override
	public boolean updatedExpIsDeletedFlag(final Long expId) {
		try {
			final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.UPDATE_PLUM_EXP_ISDELETED);
			query.setParameter("isDeleted", MainetConstants.PlumberLicense.ISDELETED_Y);
			query.setParameter("plumExpId", expId);
			query.executeUpdate();
		} catch (final Exception e) {
			logger.error("Error occured during update Experience IsDeleted Flag ", e);
		}
		return true;
	}

	@Override
	public void savePlumberInterviewDetails(final PlumberMaster master) {
		entityManager.merge(master);
	}

	@Override
	public void updatedPlumberLicenseExecutionDetailsByDept(final PlumberMaster master) {
		entityManager.merge(master);
	}

	@Override
	public List<TbPlumRenewalScheduler> getPlumRenewalSchedulerDetails(final Long serviceId, final Long orgId) {
		final Query query = entityManager.createQuery(QueryConstants.PLUMBER_QUERY.PLUM_RENEWAL_SCHEDULAR_DETAIL);
		query.setParameter("serviceId", serviceId);
		query.setParameter("orgId", orgId);
		@SuppressWarnings("unchecked")
		final List<TbPlumRenewalScheduler> schedulers = query.getResultList();
		return schedulers;
	}

	@Override
	public void updateAuthStatus(String authStatus, Long authBy, Date authDate, Long applicationId, Long orgId) {
		final Query query = entityManager.createQuery(
				"update TbCfcApplicationMstEntity ar SET ar.apmAppRejFlag =:authStatus,ar.apmApprovedBy=:authBy,ar.apmApprovedDate=:authDate where  ar.apmApplicationId=:applicationId");
		query.setParameter("authStatus", authStatus);
		query.setParameter("authBy", authBy);
		query.setParameter("authDate", authDate);
		query.setParameter("applicationId", applicationId);
		query.executeUpdate();

	}

	@Override
	public PlumberMaster getLicenseDetail(String licNo, Long orgId) {

		PlumberMaster data = null;
		final Query query = entityManager
				.createQuery("select pm from PlumberMaster pm where pm.plumLicNo=:licNo and pm.orgid=:orgId order by lmoddate desc ");
		query.setParameter("licNo", licNo);
		query.setParameter("orgId", orgId);
		try {
			data = (PlumberMaster) query.getSingleResult();
		} catch (Exception e) {
			logger.error("No result found for getLicenseDetail() for licNo " + licNo + " and orgId " + orgId + " " +  e.getMessage());
		}
		return data;
	}

	@Override
	public Object[] getRenewDetail(Long plumId) {
		final Query query = entityManager.createNativeQuery(
				"select rn_fromdate,rn_todate from tb_wt_plumrenewal_reg where plum_id=:plumId order by  rn_date desc limit 1");
		query.setParameter("plumId", plumId);
		try {
			return (Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error("No result found for getRenewDetail()" + plumId, e);
		}
		return null;
	}

	@Override
	public Object[] getmasterDates(Long plumId) {
		final Query query = entityManager.createQuery(
				"select pm.plumLicFromDate,pm.plumLicToDate from PlumberMaster pm  where pm.plumId=:plumId");
		query.setParameter("plumId", plumId);
		try {
			return (Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error("No result found for getmasterDates()" + plumId, e);
		}
		return null;
	}

	@Override
	public Object[] plumberRenewValidDates(Long applicationId) {
		final Query query = entityManager.createNativeQuery(
				"select rn_fromdate,rn_todate,plum_id from tb_wt_plumrenewal_reg where apm_application_id=:appId");
		query.setParameter("appId", applicationId);
		try {
			return (Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error("No result found for plumberRenewValidDates()" + applicationId, e);
		}
		return null;
	}

	@Override
	public boolean updateValiDates(PlumberLicenseRequestDTO plumDto) {
		final Query query = entityManager.createQuery(
				"update PlumberRenewalRegisterMaster pm set pm.rn_fromdate=:fromDate,pm.rn_todate=:toDate where pm.apm_application_id=:appId");
		query.setParameter("appId", plumDto.getApplicationId());
		query.setParameter("fromDate", plumDto.getPlumRenewFromDate());
		query.setParameter("toDate", plumDto.getPlumRenewToDate());
		query.executeUpdate();
		if (query.executeUpdate() != 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getReceiptDetails(PlumberLicenseRequestDTO plumDto) {
		final StringBuilder builder = new StringBuilder();
		builder.append(
				"select d.rmAmount,d.rmReceivedfrom,d.apmApplicationId,d.smServiceId FROM TbServiceReceiptMasEntity d where d.rmLoiNo is not null and ");
		if (plumDto.getReceiptNo() != null) {
			builder.append("d.rmRcptno=:recptNo");

		} else if (plumDto.getApplicationId() != null) {
			builder.append("d.apmApplicationId=:appId");
		}
		if (plumDto.getDeptId() != null) {
			builder.append(" and d.dpDeptId=:deptId");
		}
		final Query query = entityManager.createQuery(builder.toString());

		if (plumDto.getReceiptNo() != null) {
			query.setParameter("recptNo", plumDto.getReceiptNo());
		} else if (plumDto.getApplicationId() != null || plumDto.getApplicationId() != 0) {
			query.setParameter("appId", plumDto.getApplicationId());
		}
		if (plumDto.getDeptId() != null) {
			query.setParameter("deptId", plumDto.getDeptId());
		}
		List<Object[]> retList = query.getResultList();

		if (retList.size() > 0) {
			return retList;
		} else

			return null;
	}

	@Override
	public boolean updateLicNumber(PlumberLicenseRequestDTO plumDto) {
		final Query query = entityManager.createQuery(
				"update PlumberMaster pm set pm.plumLicNo=:licNo,pm.plumLicFromDate=:fromDate,pm.plumLicToDate=:toDate where pm.apmApplicationId=:appId");
		query.setParameter("appId", plumDto.getApplicationId());
		query.setParameter("licNo", plumDto.getPlumberLicenceNo());
		query.setParameter("fromDate", plumDto.getPlumLicFromDate());
		query.setParameter("toDate", plumDto.getPlumLicToDate());
		query.executeUpdate();
		if (query.executeUpdate() != 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getplumLicenseNo(Long applicationId) {
		final Query query = entityManager
				.createQuery("select pm.plumLicNo from PlumberMaster pm  where pm.apmApplicationId=:appId");
		query.setParameter("appId", applicationId);
		List<String> retList = query.getResultList();
		try {
			String result = (String) retList.get(0);
			return result.toString();
		} catch (Exception e) {
			logger.error("No result found for bill approval for csidn:" + applicationId, e);
		}
		return null;
	}

	@Override
	public Long getLicenseDetail(Long applicationId) {
		Long data = 0l;
		final Query query = entityManager.createQuery(
				"select pm.plum_id from PlumberRenewalRegisterMaster pm  where pm.apm_application_id=:appId");
		query.setParameter("appId", applicationId);
		try {
			data = (Long) query.getSingleResult();
		} catch (final Exception e) {
			logger.error("Exception in getLicenseDetail()" + applicationId, e);
		}
		return data;

	}

	@Override
	public Long getApplicationNumByPlumId(Long plumId) {
		Long data = 0l;
		final Query query = entityManager
				.createQuery("select pm.apmApplicationId from PlumberMaster pm  where pm.plumId=:plumId");
		query.setParameter("plumId", plumId);
		try {
			data = (Long) query.getSingleResult();
		} catch (final Exception e) {
			logger.error("Exception in getApplicationNumByPlumId()" + plumId, e);
		}
		return data;
	}

	@Override
	public boolean updatPlumberRenewalDates(PlumberLicenseRequestDTO plumDto) {
		final Query query = entityManager.createQuery(
				"update PlumberMaster pm set pm.plumLicFromDate=:fromDate,pm.plumLicToDate=:toDate where pm.plumId=:plumId and pm.orgid=:orgid");
		query.setParameter("plumId", plumDto.getPlumberId());
		query.setParameter("fromDate", plumDto.getPlumRenewFromDate());
		query.setParameter("toDate", plumDto.getPlumRenewToDate());
		query.setParameter("orgid", plumDto.getOrgId());
		query.executeUpdate();
		if (query.executeUpdate() != 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlumberRenewalRegisterMaster> getPlumberRenewals(Long plumberid) {
		final Query query = entityManager.createQuery(
				"select pm from PlumberRenewalRegisterMaster pm  where pm.plum_id=:plumberid order by plum_rn_id desc");
		query.setParameter("plumberid", plumberid);
		final List<PlumberRenewalRegisterMaster> renewalRegister = query.getResultList();
		return renewalRegister;
	}

	@Override
	public byte[] generateJasperReportPDF(PlumberLicenseRequestDTO plumDto, ByteArrayOutputStream outputStream,
			Map oParms, String fileName) {
		// TODO Auto-generated method stub
		JRPdfExporter exporter = new JRPdfExporter();

		try {

			Connection conn = new ApplicationDatasourceLoader().getConnection();
			final JasperReport jasperReport = ReportUtility.getJasperReportLookUp().get(fileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, oParms, conn);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); //
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
			exporter.exportReport();
			// LOGGER.info("END OF Method");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generate Report..." + e);
		} finally {

		}
		return outputStream.toByteArray();
	}

	@Override
	public PlumberRenewalRegisterMaster getPlumberLicenceRenewalDetailsByAppId(Long applicationId, Long orgId) {
		PlumberRenewalRegisterMaster renewalReg = null;
		try {
			final Query query = entityManager
				.createQuery("select r from PlumberRenewalRegisterMaster r  where r.apm_application_id=:applicationId and r.orgid=:orgId");
			query.setParameter("applicationId", applicationId);
			query.setParameter("orgId", orgId);
			renewalReg = (PlumberRenewalRegisterMaster) query.getSingleResult();
		}catch(Exception ex) {
			logger.error("PlumberRenewalRegisterMaster not found for applicationId: " + applicationId + " and orgId: " + orgId + " " + ex.getMessage());
		}
		return renewalReg;
	}

	@Override
	public PlumberMaster getPlumberDetailsByPlumId(Long plumId, Long orgId) {
		PlumberMaster plumber = null;
		try {
			final Query query = entityManager
					.createQuery("select pm from PlumberMaster pm  where pm.plumId=:plumId and pm.orgid=:orgId");
			query.setParameter("plumId", plumId);
			query.setParameter("orgId", orgId);
			plumber = (PlumberMaster) query.getSingleResult();
		}catch(Exception ex) {
			logger.error("PlumberMaster not found for plumId: " + plumId + " and orgId: " + orgId + " " + ex.getMessage());
		}
		return plumber;
	}

}
