/**
 *
 */
package com.abm.mainet.common.formbuilder.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.GroupMaster;

/**
 *
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class FormBuilderDataDAO extends AbstractDAO<Long> implements IFormBuilderDataDAO {

    private static Logger LOG = Logger.getLogger(FormBuilderDataDAO.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#getDesignationName(java.lang.Long)
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
     * @see com.abm.mainetservice.web.cfc.scrutiny.dao.ScrutinyDAO#getfindAllscrutinyLabelValueList(long, long, long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getfindAllFormLabelValueList(final Long orgId, final String  smShortDesc, final Long smScrutinyId,
            final Long applicationId) {
        LOG.info("Start the getScrutinyUserByWardZoneBlock");
        List<Object> list = null;
        final StringBuilder nativeQuery = new StringBuilder(
                "SELECT SM_SHORTDESC, FORM_LABEL, FORM_AUTHORISING, FORM_DATATYPE, FORM_DISPLAY_FLAG,FORM_FORM_MODE, FORM_FORM_NAME,FORM_LABEL_ID, FORM_LABEL_MAR, FORM_PRE_VALIDATION, FORM_VALIDATION_TEXT,FORM_TABLE_COLUMN, FORM_WHERE_CLAUSE,GM_ID,FORM_LEVELS,  FORM_VALUE, FORM_VID, FORM_ID,FORM_POSITION "
                        + "FROM ( SELECT L.SM_SHORTDESC,L.FORM_LABEL,L.FORM_AUTHORISING,L.FORM_DATATYPE,L.FORM_DISPLAY_FLAG,L.FORM_FORM_MODE,L.FORM_FORM_NAME,L.FORM_LABEL_ID,L.FORM_LABEL_MAR ,L.FORM_PRE_VALIDATION,L.FORM_VALIDATION_TEXT,L.FORM_TABLE_COLUMN,L.FORM_WHERE_CLAUSE,L.GM_ID,L.FORM_LEVELS,V.FORM_VALUE,V.FORM_VID,V.FORM_ID,L.FORM_POSITION "
                        + " FROM TB_FORMBUILDER_LABELS L, TB_FORMBUILDER_VALUES V "
                        + " WHERE V.FORM_LABEL_ID = L.FORM_LABEL_ID"
                        + " AND L.SM_SHORTDESC =:SM_SHORTDESC AND L.FORM_ACTIVE_STATUS='A' "
                        + " AND V.ORGID =:ORGID ");
        if (null != applicationId) {
            nativeQuery.append(" AND V.FORM_VID =:APPLICATIONID  ");
        }

        nativeQuery.append(" UNION "
                + " SELECT L.SM_SHORTDESC,L.FORM_LABEL,L.FORM_AUTHORISING,L.FORM_DATATYPE,L.FORM_DISPLAY_FLAG,L.FORM_FORM_MODE,L.FORM_FORM_NAME,L.FORM_LABEL_ID,L.FORM_LABEL_MAR ,L.FORM_PRE_VALIDATION,L.FORM_VALIDATION_TEXT,L.FORM_TABLE_COLUMN,L.FORM_WHERE_CLAUSE,L.GM_ID,L.FORM_LEVELS,NULL,NULL,NULL,FORM_POSITION "
                + " FROM TB_FORMBUILDER_LABELS L"
                + " LEFT JOIN TB_FORMBUILDER_VALUES V ON L.FORM_LABEL_ID=V.FORM_LABEL_ID "
                + " WHERE L.FORM_ACTIVE_STATUS='A' "
                + " AND L.SM_SHORTDESC =:SM_SHORTDESC "
                + " AND L.FORM_LABEL_ID NOT IN ( SELECT L.FORM_LABEL_ID FROM TB_FORMBUILDER_LABELS L, TB_FORMBUILDER_VALUES V WHERE L.FORM_LABEL_ID = V.FORM_LABEL_ID "
                + " AND L.SM_SHORTDESC =:SM_SHORTDESC  ");

        if (null != applicationId) {
            nativeQuery.append(" AND V.FORM_VID =:APPLICATIONID  ");
        }

        nativeQuery.append(" AND V.ORGID =:ORGID) ) SC ORDER BY FORM_POSITION,FORM_LABEL_ID ");

        final Query query = createNativeQuery(nativeQuery.toString());

        if (StringUtils.isNotEmpty(smShortDesc)) {
            query.setParameter("SM_SHORTDESC", smShortDesc);
        }
        if ((null != orgId) && (orgId > 0)) {
            query.setParameter("ORGID", orgId);
        }
        if ((null != applicationId)) {
            query.setParameter("APPLICATIONID", applicationId);
        }
        try {

            list = query.getResultList();

        } catch (final Exception exception) {
            LOG.error("Exception occur in getfindAllscrutinyLabelValueList() ", exception);
            return list;
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> searchFormValue(Long orgId, String serviceCode) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT DISTINCT(v.saApplicationId),v.orgId.orgid,v.userId.empId,v.lmodDate FROM FormBuilderValueEntity v WHERE v.orgId.orgid =:orgId ");

        if (StringUtils.isNotEmpty(serviceCode)) {
            searchQuery.append(
                    " AND v.slLabelId IN ( SELECT b.slLabelId FROM FormBuilderEntity b where b.smShortDesc = :serviceCode) ");
        }

        Query query = this.createQuery(searchQuery.toString());
        query.setParameter("orgId", orgId);
        if (StringUtils.isNotEmpty(serviceCode)) {
            query.setParameter("serviceCode", serviceCode);
        }

        return query.getResultList();
    }

}
