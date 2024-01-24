package com.abm.mainet.workManagement.dao;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class WorkProjectRegisterReportDaoImpl extends AbstractDAO<Long> implements WorkProjectRegisterReportDao {

	@SuppressWarnings("unchecked")
	public List<Object[]> findProjectRegisterSheetReport(Long schId, Long projId, Long workType, Long orgId) {
		List<Object[]> entityList = null;
		try {
			final Query query = createNativeQuery(getNativeQuery(schId, projId, workType, orgId));
			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException("Exception while fetching findAllApprovedNotInitiatedWork : " + ex);
		}
		return entityList;
	}
	// #38616
	private String getNativeQuery(Long schId, Long projId, Long workType, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT workdefina0_.ORGID AS col_0_0_,\r\n" + "       (SELECT schememast2_.SCH_NAME_ENG\r\n"
				+ "          FROM TB_WMS_SCHEME_MAST schememast2_\r\n"
				+ "         WHERE schememast2_.SCH_ID = tbwmsproje1_.SCH_ID)\r\n" + "          AS col_1_0_,\r\n"
				+ "       (SELECT schememast3_.SCH_NAME_REG\r\n" + "          FROM TB_WMS_SCHEME_MAST schememast3_\r\n"
				+ "         WHERE schememast3_.SCH_ID = tbwmsproje1_.SCH_ID)\r\n" + "          AS col_2_0_,\r\n"
				+ "       tbwmsproje1_.PROJ_NAME_ENG AS col_3_0_,\r\n"
				+ "       tbwmsproje1_.PROJ_NAME_REG AS col_4_0_,\r\n"
				+ "       workdefina0_.WORK_TYPE AS col_5_0_,\r\n" + "       workdefina0_.WORK_NAME AS col_6_0_,\r\n"
				+ "       (SELECT DISTINCT workestima4_.WORKE_ESTIMATE_NO\r\n"
				+ "          FROM TB_WMS_WORKESTIMATE_MAS workestima4_\r\n"
				+ "         WHERE workestima4_.WORK_ID = workdefina0_.WORK_ID)\r\n" + "          AS col_7_0_,\r\n"
				+ "       workdefina0_.WORK_ESTAMT AS col_8_0_,\r\n" + "       (SELECT sum(tenderwork5_.TND_AMOUNT)\r\n"
				+ "          FROM TB_WMS_TENDER_WORK tenderwork5_\r\n"
				+ "         WHERE tenderwork5_.WORK_ID = workdefina0_.WORK_ID)\r\n" + "          AS col_9_0_,\r\n"
				+ "       CASE\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'D'\r\n" + "          THEN\r\n"
				+ "             'Draft'\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'P'\r\n"
				+ "          THEN\r\n" + "             'Pending'\r\n"
				+ "          WHEN workdefina0_.WORK_STATUS = 'A'\r\n" + "          THEN\r\n"
				+ "             'Approved'\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'AA'\r\n"
				+ "          THEN\r\n" + "             'Administrator Approval'\r\n"
				+ "          WHEN workdefina0_.WORK_STATUS = 'TA'\r\n" + "          THEN\r\n"
				+ "             'Technical Approval'\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'I'\r\n"
				+ "          THEN\r\n" + "             'Initiated'\r\n"
				+ "          WHEN workdefina0_.WORK_STATUS = 'R'\r\n" + "          THEN\r\n"
				+ "             'Rejected'\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'TS'\r\n"
				+ "          THEN\r\n" + "             'Technical Sanction Approved'\r\n"
				+ "          WHEN workdefina0_.WORK_STATUS = 'AS'\r\n" + "          THEN\r\n"
				+ "             'Admin Sanction Approved'\r\n" + "          WHEN workdefina0_.WORK_STATUS = 'C'\r\n"
				+ "          THEN\r\n" + "             'Completed'\r\n"
				+ "          WHEN workdefina0_.WORK_STATUS = 'T'\r\n" + "          THEN\r\n"
				+ "             'Tender Generated'\r\n" + "       END\r\n" + "          AS col_10_0_,\r\n"
				+ "       (SELECT max(measuremen6_.BM_DATE)\r\n"
				+ "          FROM TB_WMS_MEASUREMENTBOOK_MAST measuremen6_\r\n"
				+ "               CROSS JOIN TB_WMS_WORKEORDER workorder7_\r\n"
				+ "               CROSS JOIN TB_WMS_TENDER_WORK tenderwork8_\r\n"
				+ "         WHERE     measuremen6_.WORKOR_ID = workorder7_.WORKOR_ID\r\n"
				+ "               AND workorder7_.CONT_ID = tenderwork8_.CONT_ID\r\n"
				+ "               AND tenderwork8_.WORK_ID = workdefina0_.WORK_ID)\r\n" + "          AS col_11_0_,\r\n"
				+ "       (SELECT sum(pm.PAYMENT_AMOUNT)\r\n" + "          FROM tb_wms_rabill wr,\r\n"
				+ "               tb_ac_bill_mas bm,\r\n" + "               tb_ac_payment_det pd,\r\n"
				+ "               tb_ac_payment_mas pm\r\n" + "         WHERE     wr.RA_CODE = bm.bm_invoicenumber\r\n"
				+ "               AND pd.bm_id = bm.bm_id\r\n" + "               AND pd.PAYMENT_ID = pm.PAYMENT_ID\r\n"
				+ "               AND wr.work_id = workdefina0_.work_id)\r\n" + "          AS col_12_0_\r\n"
				+ "  FROM TB_WMS_WORKDEFINATION workdefina0_\r\n"
				+ "       CROSS JOIN TB_WMS_PROJECT_MAST tbwmsproje1_\r\n"
				+ " WHERE     workdefina0_.PROJ_ID = tbwmsproje1_.PROJ_ID\r\n" + "       AND workdefina0_.PROJ_ID =\r\n"
				+ "              CASE\r\n" + "                 WHEN coalesce(" + projId + ", -1) = -1\r\n"
				+ "                 THEN\r\n" + "                    coalesce(workdefina0_.PROJ_ID, 0)\r\n"
				+ "                 ELSE\r\n" + "                    coalesce(" + projId + ", -1)\r\n"
				+ "              END\r\n" + "       AND workdefina0_.WORK_TYPE = " + workType + "\r\n"
				+ "       AND workdefina0_.ORGID = " + orgId);
		if (schId == null || schId == 0) {
			builder.append(" AND (tbwmsproje1_.SCH_ID is null)");
		} else {
			builder.append(" AND tbwmsproje1_.SCH_ID =\r\n" + "              CASE\r\n"
					+ "                 WHEN coalesce(" + schId + ", -1) = -1\r\n" + "                 THEN\r\n"
					+ "                    coalesce(tbwmsproje1_.SCH_ID, 0)\r\n" + "                 ELSE\r\n"
					+ "                    coalesce(" + schId + ", -1)\r\n" + "              END");
		}
		return builder.toString();
	}

}