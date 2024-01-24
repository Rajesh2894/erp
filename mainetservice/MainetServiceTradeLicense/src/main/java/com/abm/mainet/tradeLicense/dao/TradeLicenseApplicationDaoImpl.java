package com.abm.mainet.tradeLicense.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public class TradeLicenseApplicationDaoImpl extends AbstractDAO<Long> implements ITradeLicenseApplicationDao {

	private static final Logger LOGGER = Logger.getLogger(TradeLicenseApplicationDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFilteredNewTradeLicenceList(Long licenseType, String oldLicenseNo, String newLicenseNo,
			Long ward1, Long ward2, Long ward3, Long ward4, Long ward5, Long orgId, String busName, String ownerName) {

		List<Object[]> entityList = new ArrayList<>();
		try {
			// changing query As per Rajesh sir's suggestion for Defect #100482
			// query changes for US#112674 after discussion with Samadhan Sir
			StringBuilder hql = new StringBuilder(
					"SELECT tc.cpdDesc,tm.trdOldlicno, tm.trdLicno,tm.trdLicfromDate,tm.trdLictoDate,tm.trdBusnm,tm.trdBusadd,tm.trdId FROM TbMlTradeMast tm,TbComparamDetEntity tc where tm.trdLictype=tc.cpdId and  tm.orgid = :orgid");

			if (oldLicenseNo != null && !oldLicenseNo.isEmpty()) {
				hql.append(" and tm.trdOldlicno = :trdOldlicno ");
			}
			if (newLicenseNo != null && !newLicenseNo.isEmpty()) {
				hql.append(" and tm.trdLicno = :trdLicno ");
			}
			if (licenseType != null && licenseType != 0) {
				hql.append(" and tm.trdLictype = :trdLictype ");
			}
			if (ward1 != null && ward1 != 0) {
				hql.append(" and tm.trdWard1 = :trdWard1 ");
			}
			if (ward2 != null && ward2 != 0) {
				hql.append(" and tm.trdWard2 = :trdWard2 ");
			}
			if (ward3 != null && ward3 != 0) {
				hql.append(" and tm.trdWard3 = :trdWard3 ");
			}
			if (ward4 != null && ward4 != 0) {
				hql.append(" and tm.trdWard4 = :trdWard4 ");
			}
			if (ward5 != null && ward5 != 0) {
				hql.append(" and tm.trdWard5 = :trdWard5 ");
			}
			if (StringUtils.isNotEmpty(busName)) {
				hql.append(" and tm.trdBusnm like :trdBusnm ");
			}

			if (StringUtils.isNotEmpty(ownerName)) {
				hql.append(" and (select count(*) from TbMlOwnerDetail tw where tw.masterTradeId.trdId=tm.trdId and tw.troName like :troName)>0");
			}

			final Query query = createQuery(hql.toString());
			query.setParameter("orgid", orgId);

			if (oldLicenseNo != null && !oldLicenseNo.isEmpty()) {
				query.setParameter("trdOldlicno", oldLicenseNo);
			}
			if (newLicenseNo != null && !newLicenseNo.isEmpty()) {
				query.setParameter("trdLicno", newLicenseNo);
			}
			if (licenseType != null && licenseType != 0) {
				query.setParameter("trdLictype", licenseType);
			}
			if (ward1 != null && ward1 != 0) {
				query.setParameter("trdWard1", ward1);
			}
			if (ward2 != null && ward2 != 0) {
				query.setParameter("trdWard2", ward2);
			}
			if (ward3 != null && ward3 != 0) {
				query.setParameter("trdWard3", ward3);
			}
			if (ward4 != null && ward4 != 0) {
				query.setParameter("trdWard4", ward4);
			}
			if (ward5 != null && ward5 != 0) {
				query.setParameter("trdWard5", ward5);
			}
			if (StringUtils.isNotEmpty(busName)) {
				query.setParameter("trdBusnm", "%" + busName + "%");
			}

			if (StringUtils.isNotEmpty(ownerName)) {
				query.setParameter("troName", "%" + ownerName + "%");
			}

			entityList = (List<Object[]>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  getFilteredNewTradeLicenceList() ", exception);

		}
		return entityList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getpaymentMode(Long orgId, String loiNo) {

		List<Object[]> entityList = null;
		try {
			// Defect#130694 for validate if cheque is dishonoured
			StringBuilder hql = new StringBuilder(

					"select a.rmRcptno,a.rmDate,a.rmAmount,c.cpdDesc,c.cpdDescMar,a.rmLoiNo,a.apmApplicationId ,c.cpdValue,b.checkStatus "
							+ "from TbServiceReceiptMasEntity a,TbSrcptModesDetEntity b,TbComparamDetEntity c,TbComparamMasEntity d "
							+ "where a.rmRcptid=b.rmRcptid and " + "c.tbComparamMas=d.cpmId and b.cpdFeemode=c.cpdId "
							+ "and d.cpmPrefix ='PAY' " + "and a.orgId=:orgId and a.rmLoiNo=:loiNo "
							+ " and a.rmRcptid=(select max(cast(rcpt.rmRcptid as long)) from TbServiceReceiptMasEntity rcpt where rcpt.orgId=:orgId and rcpt.rmLoiNo=:loiNo )");

			final Query query = createQuery(hql.toString());
			query.setParameter("orgId", orgId);
			query.setParameter("loiNo", loiNo);

			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException("Exception while fetching Approval Trade License : " + ex);
		}
		return entityList;

	}

	@Override
	public List<Object[]> getpaymentModeByRcptId(Long orgId, long rmRcptid) {

		List<Object[]> entityList = null;
		try {

			StringBuilder hql = new StringBuilder(

					"select a.rmRcptno,a.rmDate,a.rmAmount,c.cpdDesc,c.cpdDescMar,a.rmLoiNo,a.apmApplicationId ,c.cpdValue,b.checkStatus "
							+ "from TbServiceReceiptMasEntity a,TbSrcptModesDetEntity b,TbComparamDetEntity c,TbComparamMasEntity d "
							+ "where a.rmRcptid=b.rmRcptid and " + "c.tbComparamMas=d.cpmId and b.cpdFeemode=c.cpdId "
							+ "and d.cpmPrefix ='PAY' " + "and a.orgId=:orgId and a.rmRcptid=:rmRcptid ");

			final Query query = createQuery(hql.toString());
			query.setParameter("orgId", orgId);
			query.setParameter("rmRcptid", rmRcptid);

			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException("Exception while fetching Approval Trade License : " + ex);
		}
		return entityList;
	}

	@Override
	public List<TbMlTradeMast> fetchLicenseDetails(Long orgId, Long licType, String licNo, Date date, Long trdWard1,
			Long trdWard2, Long trdWard3, Long trdWard4, Long trdWard5) {
		List<TbMlTradeMast> entityList = null;
		try {

			StringBuilder hql = new StringBuilder(

					"select lic from TbMlTradeMast lic where orgid=:orgid ");

			if (licType != null) {
				hql.append(" and lic.trdLictype=:trdLictype");
			}
			if (trdWard1 != null && trdWard1!=0)
				hql.append(" and lic.trdWard1=:trdWard1");
			if (trdWard2 != null && trdWard2!=0)
				hql.append(" and lic.trdWard2=:trdWard2");
			if (trdWard3 != null && trdWard3!=0)
				hql.append(" and lic.trdWard3=:trdWard3");
			if (trdWard4 != null && trdWard4!=0)
				hql.append(" and lic.trdWard4=:trdWard4");
			if (trdWard5 != null && trdWard5!=0)
				hql.append(" and lic.trdWard5=:trdWard5");

			if (StringUtils.isNotBlank(licNo)) {
				hql.append("  and lic.trdLicno=:trdLicno");
			} else {
				hql.append("  and lic.trdLictoDate<=:date");
			}

			final Query query = createQuery(hql.toString());

			query.setParameter("orgid", orgId);
			if (trdWard1 != null && trdWard1!=0)
				query.setParameter("trdWard1", trdWard1);
			if (trdWard2 != null && trdWard2!=0)
				query.setParameter("trdWard2", trdWard2);

			if (trdWard3 != null && trdWard3!=0)
				query.setParameter("trdWard3", trdWard3);

			if (trdWard4 != null && trdWard4!=0)
				query.setParameter("trdWard4", trdWard4);

			if (trdWard5 != null && trdWard5!=0)
				query.setParameter("trdWard5", trdWard5);

			if (licType != null) {
				query.setParameter("trdLictype", licType);
			}

			if (StringUtils.isNotBlank(licNo)) {
				query.setParameter("trdLicno", licNo);
			} else {
				query.setParameter("date", date);
			}
			entityList = (List<TbMlTradeMast>) query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("error occured at the time of fetching license details");
			return null;
		}
		return entityList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getpaymentModeByAppId(Long orgId, Long applicationId) {

		List<Object[]> entityList = null;
		try {
			// Defect#130694 for validate if cheque is dishonoured
			StringBuilder hql = new StringBuilder(

					"select a.rmRcptno,a.rmDate,a.rmAmount,c.cpdDesc,c.cpdDescMar,a.rmLoiNo,a.apmApplicationId ,c.cpdValue,b.checkStatus "
							+ "from TbServiceReceiptMasEntity a,TbSrcptModesDetEntity b,TbComparamDetEntity c,TbComparamMasEntity d "
							+ "where a.rmRcptid=b.rmRcptid and " + "c.tbComparamMas=d.cpmId and b.cpdFeemode=c.cpdId "
							+ "and d.cpmPrefix ='PAY' " + "and a.orgId=:orgId and a.apmApplicationId=:applicationId " + "and a.rmLoiNo is null"
							+ " and a.rmRcptid=(select max(cast(rcpt.rmRcptid as long)) from TbServiceReceiptMasEntity rcpt where rcpt.orgId=:orgId and rcpt.apmApplicationId=:applicationId )");

			final Query query = createQuery(hql.toString());
			query.setParameter("orgId", orgId);
			query.setParameter("applicationId", applicationId);

			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException("Exception while fetching Approval Trade License : " + ex);
		}
		return entityList;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getEmployeeIdList(Long orgId,  Long ward1, Long ward2, Long ward3, Long ward4, Long ward5) {

		List<Object> entityList = new ArrayList<Object>();
		try {
			// Defect#130694 for validate if cheque is dishonoured
			StringBuilder hql = new StringBuilder("select  ew.emp_id from tb_emp_loc_map_det ew where");
			
			if(orgId!=null)
				hql.append("  ew.orgid=:orgid");
			if(ward1!=null)
				hql.append(" and ew.ward1=:ward1");
			if(ward2!=null)
				hql.append(" and ew.ward2=:ward2");
			if(ward3!=null)
				hql.append(" and ew.ward3=:ward3");
			if(ward4!=null)
				hql.append(" and ew.ward4=:ward4");
			if(ward5!=null)
				hql.append(" and ew.ward5=:ward5");
			
			LOGGER.info("QUERY  "+hql.toString());

			final Query query = this.createNativeQuery(hql.toString());
			if(orgId!=null)
				query.setParameter("orgid", orgId);
			if(ward1!=null)
				query.setParameter("ward1", ward1);
			if(ward2!=null)
				query.setParameter("ward2", ward2);
			if(ward3!=null)
				query.setParameter("ward3", ward3);
			if(ward4!=null)
				query.setParameter("ward4", ward4);
			if(ward5!=null)
				query.setParameter("ward5", ward5);

			entityList = (List<Object>) query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetchinfg emplyee id"+ex);
			return entityList;
		}
		return entityList;

	}

	@Override
	public List<TbMlTradeMast> getLicenseDetailbyLicAndMobile(String trdLicno, String mobileNo, Long orgId) {
		List<TbMlTradeMast> entityList = null;
		try {

			StringBuilder hql = new StringBuilder(

					"select lic FROM TbMlTradeMast lic where lic.orgid =:orgId");
			
			
			if (StringUtils.isNotBlank(trdLicno)) {
				hql.append("  and lic.trdLicno=:trdLicno");
			}
			
			if (StringUtils.isNotEmpty(mobileNo)) {
				hql.append(" and (select count(*) from TbMlOwnerDetail tw where tw.masterTradeId.trdId=lic.trdId and tw.troMobileno=:troMobileno)>0");
			}
			
			final Query query = createQuery(hql.toString());
			
			if(orgId!=null)
			query.setParameter("orgId", orgId);
			if (StringUtils.isNotBlank(trdLicno)) {
				query.setParameter("trdLicno", trdLicno);
			} 
			if (StringUtils.isNotEmpty(mobileNo)) {
				query.setParameter("troMobileno", mobileNo );
			}
			
			entityList = (List<TbMlTradeMast>) query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("error occured at the time of fetching license details");
			return null;
		}
		return entityList;
	}

}
