/**
 *
 */
package com.abm.mainet.cfc.scrutiny.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.service.TbScrutinyLabelsService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class ScrutinyDAOImpl extends AbstractDAO<Long> implements ScrutinyDAO {

	private static Logger LOG = Logger.getLogger(ScrutinyDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#getDesignationName(
	 * java.lang.Long)
	 */
	@Override
	public GroupMaster getDesignationName(final Long gmId) {

		LOG.info("Start the getDesignationName");

		final StringBuilder hql = new StringBuilder("SELECT gm FROM GroupMaster gm  where gm.gmId = :gmId");
		final Query query = createQuery(hql.toString());
		query.setParameter("gmId", gmId);

		try {
			final GroupMaster entity = (GroupMaster) query.getSingleResult();
			return entity;
		} catch (final Exception exception) {
			LOG.error("Exception occur in getDesignationName() ", exception);
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#
	 * getAllScrutinyDocDesgWise(long, long)
	 */
	@Override
	public List<CFCAttachment> getAllScrutinyDocDesgWise(final long applId, final long orgId) {

		LOG.info("Start the getAllScrutinyDocDesgWise");

		final StringBuilder hql = new StringBuilder(
				"SELECT gm FROM CFCAttachment gm  where gm.applicationId = :apmApplicationId and gm.orgid =:orgId and gm.smScrutinyId is not null ");
		final Query query = createQuery(hql.toString());
		query.setParameter("apmApplicationId", applId);
		query.setParameter("orgId", orgId);

		try {
			@SuppressWarnings("unchecked")
			final List<CFCAttachment> entity = query.getResultList();
			return entity;
		} catch (final Exception exception) {
			LOG.error("Exception occur in getAllScrutinyDocDesgWise() ", exception);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#
	 * getfindAllscrutinyLabelValueList(long, long, long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getfindAllscrutinyLabelValueList(final Long orgId, final Long serviceId,
			final Long smScrutinyId, final Long applicationId, Long triCod1,Long currentLevel) {
		LOG.info("Start the getScrutinyUserByWardZoneBlock");
		// US#113590
		String temp = "";
		String shortCode =null;
		if (serviceId != null) {
			 shortCode = ApplicationContextProvider.getApplicationContext().getBean(TbScrutinyLabelsService.class)
					.getServiceCode(serviceId, orgId);
			 Organisation org = new Organisation();
			 org.setOrgid(orgId);
			if ((Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))
					&& (shortCode != null && shortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE))) {
				temp = " and l.TRI_COD1=:tricod1 ";
			}
		}

		List<Object> list = null;
		final StringBuilder nativeQuery = new StringBuilder(
				"SELECT SM_SERVICE_ID, SL_LABEL, SL_AUTHORISING, SL_DATATYPE, SL_DISPLAY_FLAG,SL_FORM_MODE, SL_FORM_NAME,SL_LABEL_ID, SL_LABEL_MAR, SL_PRE_VALIDATION, sl_validation_text,SL_TABLE_COLUMN, SL_WHERE_CLAUSE,GM_ID,LEVELS,  SV_VALUE, CFC_APPLICATION_ID,REMARK,SL_QUERY "
						+ "FROM ( SELECT l.sm_service_id,l.sl_Label,l.sl_authorising,l.sl_datatype,l.sl_display_flag,l.sl_Form_Mode,l.sl_Form_Name,l.sl_Label_Id,l.sl_Label_Mar ,l.sl_Pre_Validation,l.sl_validation_text,l.sl_Table_Column,l.sl_Where_Clause,l.gm_Id,l.levels,v.sv_Value,v.cfc_application_Id,if(v.cfc_application_id=:applicationId,v.remark,null)as remark,l.SL_QUERY"
						+ " FROM TB_SCRUTINY_LABELS l, TB_SCRUTINY_VALUES v " + " WHERE v.sl_label_id = l.sl_Label_Id"
						+ " and l.orgid = v.orgid and l.sm_service_id =:smServiceId and if(:levels is null,''='', l.levels=:levels) and  l.SL_ACTIVE_STATUS='A'"
						+ " and v.cfc_application_id=:applicationId and l.orgid =:orgId  " + temp + " union "
						+ " select l.sm_service_id,l.sl_Label,l.sl_authorising,l.sl_datatype,l.sl_display_flag,l.sl_Form_Mode,l.sl_Form_Name,l.sl_Label_Id,l.sl_Label_Mar ,l.sl_Pre_Validation,l.sl_validation_text,l.sl_Table_Column,l.sl_Where_Clause,l.gm_Id,l.levels,null,null,if(v.cfc_application_id =:applicationId,v.remark,null) as remark,l.SL_QUERY"
						+ " from TB_SCRUTINY_LABELS l"
						+ " LEFT JOIN TB_SCRUTINY_VALUES v ON l.SL_LABEL_ID=v.SL_LABEL_ID AND l.ORGID = v.ORGID"
						+ "  where l.SL_ACTIVE_STATUS='A' " + "  and l.sm_service_id =:smServiceId" + "  and if(:levels is null,''='', l.levels=:levels)" + temp
						+ " and l.sl_label_id not in ( select l.sl_label_id from TB_SCRUTINY_LABELS l, TB_SCRUTINY_VALUES v where l.sl_label_id = v.sl_label_id and l.orgid = v.orgid "
						+ " and l.sm_service_id =:smServiceId  and v.cfc_application_id =:applicationId and l.orgid =:orgId"
						+ temp + ")) sc ORDER BY SL_LABEL_ID");

		final Query query = createNativeQuery(nativeQuery.toString());

		if ((null != serviceId) && (serviceId > 0)) {
			query.setParameter("smServiceId", serviceId);
		}
		if ((null != orgId) && (orgId > 0)) {
			query.setParameter("orgId", orgId);
		}
		//if ((null != currentLevel) && (currentLevel > 0)) {
			query.setParameter("levels", currentLevel);
		//}
		if ((null != applicationId) && (applicationId > 0)) {
			query.setParameter("applicationId", applicationId);
		}
		// US#113590
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		if ((Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))
				&& (shortCode != null && shortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE))) {
			query.setParameter("tricod1", triCod1);
		}

		try

		{

			list = query.getResultList();

		} catch (final Exception exception) {
			LOG.error("Exception occur in getScrutinyUserByWardZoneBlock() ", exception);
			return list;
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllscrutinyLabelValueListTCP(final Long orgId, final Long serviceId,
			final Long smScrutinyId, final Long applicationId, Long triCod1,Long currentLevel,Long gmId) {
		LOG.info("Start the getScrutinyUserByWardZoneBlock");
		// US#113590
		String temp = "";
		String shortCode =null;
		if (serviceId != null) {
			 shortCode = ApplicationContextProvider.getApplicationContext().getBean(TbScrutinyLabelsService.class)
					.getServiceCode(serviceId, orgId);
			 Organisation org = new Organisation();
			 org.setOrgid(orgId);
			if ((Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))
					&& (shortCode != null && shortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE))) {
				temp = " and l.TRI_COD1=:tricod1 ";
			}
		}

		List<Object> list = null;
		final StringBuilder nativeQuery = new StringBuilder(
				"SELECT SM_SERVICE_ID, SL_LABEL, SL_AUTHORISING, SL_DATATYPE, SL_DISPLAY_FLAG,SL_FORM_MODE, SL_FORM_NAME,SL_LABEL_ID, SL_LABEL_MAR, SL_PRE_VALIDATION, sl_validation_text,SL_TABLE_COLUMN, SL_WHERE_CLAUSE,GM_ID,LEVELS,  SV_VALUE, CFC_APPLICATION_ID,REMARK,SL_QUERY "
						+ "FROM ( SELECT l.sm_service_id,l.sl_Label,l.sl_authorising,l.sl_datatype,l.sl_display_flag,l.sl_Form_Mode,l.sl_Form_Name,l.sl_Label_Id,l.sl_Label_Mar ,l.sl_Pre_Validation,l.sl_validation_text,l.sl_Table_Column,l.sl_Where_Clause,l.gm_Id,l.levels,v.sv_Value,v.cfc_application_Id,if(v.cfc_application_id=:applicationId,v.remark,null)as remark,l.SL_QUERY"
						+ " FROM TB_SCRUTINY_LABELS l, TB_SCRUTINY_VALUES v " + " WHERE v.sl_label_id = l.sl_Label_Id"
						+ " and l.orgid = v.orgid and l.sm_service_id =:smServiceId and if(:levels is null,''='', l.levels=:levels) and  l.SL_ACTIVE_STATUS='A'"
						+ " and v.cfc_application_id=:applicationId and l.orgid =:orgId and GM_ID=:gmId " + temp + " union "
						+ " select l.sm_service_id,l.sl_Label,l.sl_authorising,l.sl_datatype,l.sl_display_flag,l.sl_Form_Mode,l.sl_Form_Name,l.sl_Label_Id,l.sl_Label_Mar ,l.sl_Pre_Validation,l.sl_validation_text,l.sl_Table_Column,l.sl_Where_Clause,l.gm_Id,l.levels,null,null,if(v.cfc_application_id =:applicationId,v.remark,null) as remark,l.SL_QUERY"
						+ " from TB_SCRUTINY_LABELS l"
						+ " LEFT JOIN TB_SCRUTINY_VALUES v ON l.SL_LABEL_ID=v.SL_LABEL_ID AND l.ORGID = v.ORGID"
						+ "  where l.SL_ACTIVE_STATUS='A' " + "  and l.sm_service_id =:smServiceId" + "  and if(:levels is null,''='', l.levels=:levels)" + temp
						+ " and l.sl_label_id not in ( select l.sl_label_id from TB_SCRUTINY_LABELS l, TB_SCRUTINY_VALUES v where l.sl_label_id = v.sl_label_id and l.orgid = v.orgid "
						+ " and l.sm_service_id =:smServiceId  and v.cfc_application_id =:applicationId and l.orgid =:orgId"
						+ temp + ") and GM_ID=:gmId) sc ORDER BY SL_LABEL_ID");

		final Query query = createNativeQuery(nativeQuery.toString());

		if ((null != serviceId) && (serviceId > 0)) {
			query.setParameter("smServiceId", serviceId);
		}
		if ((null != orgId) && (orgId > 0)) {
			query.setParameter("orgId", orgId);
		}
		//if ((null != currentLevel) && (currentLevel > 0)) {
			query.setParameter("levels", currentLevel);
		//}
		if ((null != applicationId) && (applicationId > 0)) {
			query.setParameter("applicationId", applicationId);
		}
		if ((null != gmId) && (gmId > 0)) {
			query.setParameter("gmId", gmId);
		}
		// US#113590
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		if ((Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))
				&& (shortCode != null && shortCode.equals(MainetConstants.TradeLicense.MARKET_LICENSE))) {
			query.setParameter("tricod1", triCod1);
		}

		try

		{

			list = query.getResultList();

		} catch (final Exception exception) {
			LOG.error("Exception occur in getScrutinyUserByWardZoneBlock() ", exception);
			return list;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getNotingData(final Long applicationId) {
		LOG.info("Start the getScrutinyUserByWardZoneBlock");
		// US#113590
		List<Object> list = null;
		final StringBuilder nativeQuery = new StringBuilder(
				"select if(ac.SL_LABEL_ID is not null,concat('Draw of',aa.SL_LABEL),aa.SL_LABEL) as SL_LABEL_IDs,aa.LEVELS,ab.* ,aa.SL_DATATYPE,aa.SL_QUERY from \r\n"
						+ "(\r\n"
						+ "select a.REMARK,a.SV_VALUE,ifnull(a.UPDATED_DATE,a.LMODDATE) as UPDATED_DATE,a.SL_LABEL_ID,a.CFC_APPLICATION_ID,b.DSGID,c.DSGNAME,b.GM_ID,d.GR_DESC_ENG ,a.ORGID,concat(b.EMPNAME,' ',b.EMPMNAME,' ',b.EMPLNAME)  as employeename,d.GR_DESC_ENG as role,a.TASK_ID \r\n"
						+ "from tb_scrutiny_values a\r\n" + "left join employee b on a.USER_ID=b.EMPID\r\n"
						+ "left join designation c on b.DSGID=c.DSGID\r\n"
						+ "left join tb_group_mast d on b.GM_ID=d.GM_ID\r\n"
						+ "group by b.DSGID,b.GM_ID,a.CFC_APPLICATION_ID,a.SL_LABEL_ID,a.ORGID,a.REMARK,a.SV_VALUE,a.LEVELS) ab \r\n"
						+ "join tb_scrutiny_labels aa on aa.SL_LABEL_ID=ab.SL_LABEL_ID and aa.ORGID=ab.ORGID\r\n"
						+ "left join tb_lic_perf_draw ac on ac.SL_LABEL_ID=ab.SL_LABEL_ID and ac.CFC_APPLICATION_ID=ab.CFC_APPLICATION_ID and ac.GM_ID=ab.GM_ID\r\n"
						+ "where ab.CFC_APPLICATION_ID=:applicationId \r\n"
						+ "order by ab.UPDATED_DATE,aa.LEVELS,aa.SL_LABEL_ID");

		final Query query = createNativeQuery(nativeQuery.toString());
		if ((null != applicationId) && (applicationId > 0)) {
			query.setParameter("applicationId", applicationId);
		}
		try {
			list = query.getResultList();

		} catch (final Exception exception) {
			LOG.error("Exception occur in getScrutinyUserByWardZoneBlock() ", exception);
			return list;
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#getValueByLabelQuery(
	 * java.lang.String)
	 */
	@Override
	public String getValueByLabelQuery(final String query) {

		String value = "";
		try {
			final Query nativeQuery = createNativeQuery(query);
			Object result = nativeQuery.getSingleResult();
			if (result != null) {
				value = result.toString();
			}

		} catch (final Exception exception) {
			LOG.error("Exception occur in getValueByLabelQuery() ", exception);
			return value;

		}
		return value;
	}
	
	@Override
	public String getdataByLabelQuery(final String query ,final long APM_APPLICATION_ID) {

		String value = "";
		try {
			final Query queryValue = createNativeQuery(query);

			queryValue.setParameter("APM_APPLICATION_ID",APM_APPLICATION_ID);
			Object result = queryValue.getSingleResult();
			if (result != null) {
				value = result.toString();
			}

		} catch (final Exception exception) {
			LOG.error("Exception occur in getValueByLabelQuery() ", exception);
			return value;

		}
		return value;
	}

}
