
package com.abm.mainet.account.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.rest.ui.controller.AccountVoucherPostingRestController;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * @author Vivek.Kumar
 * @since 01 Feb 2017
 */
@Repository
public class VoucherTemplateRepositoryImpl extends AbstractDAO<VoucherTemplateMasterEntity>
        implements VoucherTemplateRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;

    /*
     * @Autowired private JdbcStoredProcedure jdbcStoredProcedure;
     */

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherPostingRestController.class);

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<VoucherTemplateMasterEntity> searchTemplate(final Long templateType, final Long financialYear,
            final Long voucherType,
            final Long department, final Long templateFor, final Long status, final Long orgId) {
        LOGGER.info("querying voucher template for these params[ templateType=" + templateType + ",financialYear=" + financialYear
                + ""
                + ",voucherType=" + voucherType + ",department=" + department + ",templateFor=" + templateFor + ",status="
                + status + ",orgId=" + orgId + "]");
        final Query query = entityManager
                .createQuery(buildDynamicQuery(templateType, financialYear, voucherType, department, templateFor, status, orgId));
        if ((templateType != null) && (templateType != 0l)) {
            query.setParameter("templateType", templateType);
        }
        if ((financialYear != null) && (financialYear != 0l)) {
            query.setParameter("financialYear", financialYear);
        }
        if ((voucherType != null) && (voucherType != 0l)) {
            query.setParameter("voucherType", voucherType);
        }
        if ((department != null) && (department != 0l)) {
            query.setParameter("department", department);
        }
        if ((templateFor != null) && (templateFor != 0l)) {
            query.setParameter("templateFor", templateFor);
        }
        if ((status != null) && (status != 0l)) {
            query.setParameter("status", status);
        }
        query.setParameter("orgId", orgId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<VoucherTemplateMasterEntity> searchTemplateData(final Long templateType, final Long financialYear,
            final Long voucherType,
            final Long templateFor, final Long orgId) {
        LOGGER.info("querying voucher template for these params[ templateType=" + templateType + ",financialYear=" + financialYear
                + ""
                + ",voucherType=" + voucherType + ",templateFor=" + templateFor + ",orgId=" + orgId
                + "]");
        final Query query = entityManager
                .createQuery(buildDynamicDataQuery(templateType, financialYear, voucherType, templateFor, orgId));
        if ((templateType != null) && (templateType != 0l)) {
            query.setParameter("templateType", templateType);
        }
        if ((financialYear != null) && (financialYear != 0l)) {
            query.setParameter("financialYear", financialYear);
        }
        if ((voucherType != null) && (voucherType != 0l)) {
            query.setParameter("voucherType", voucherType);
        }
        if ((templateFor != null) && (templateFor != 0l)) {
            query.setParameter("templateFor", templateFor);
        }
        query.setParameter("orgId", orgId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public VoucherTemplateMasterEntity queryDefinedTemplate(final Long templateFor, final Long deptId, final Long orgId,
            final Long status, Long yearId) {
        StringBuilder s = new StringBuilder(buildQuery());
        if (yearId != null) {
            s.append(" and vt.financialYear=:yearId");
        } else {
            s.append(" and vt.financialYear IS NULL");
        }
        final Query query = entityManager.createQuery(s.toString());
        query.setParameter("templateFor", templateFor);
        query.setParameter("deptId", deptId);
        query.setParameter("orgId", orgId);
        query.setParameter("status", status);
        if (yearId != null) {
            query.setParameter("yearId", yearId);
        }
        VoucherTemplateMasterEntity template = null;
        try {
            template = (VoucherTemplateMasterEntity) query.getSingleResult();
        } catch (final NonUniqueResultException nue) {// handle exception here just because Global Handler defined
            LOGGER.error("duplicate Template exist in TB_AC_VOUCHERTEMPLATE_MAS against voucherSubTypeId=" + templateFor, nue);
        } catch (final NoResultException ex) {
            LOGGER.error("No record found from TB_AC_VOUCHERTEMPLATE_MAS against voucherSubTypeId=" + templateFor, ex);
        }

        return template;
    }

    private String buildQuery() {
        return "FROM VoucherTemplateMasterEntity vt WHERE vt.templateFor=:templateFor AND vt.department=:deptId AND vt.orgid=:orgId AND vt.status=:status";
    }

    private String buildDynamicQuery(final Long templateType, final Long financialYear, final Long voucherType,
            final Long department, final Long templateFor, final Long status, final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(" FROM VoucherTemplateMasterEntity vt WHERE ");

        if ((templateType != null) && (templateType != 0l)) {
            builder.append(" vt.templateType=:templateType AND");
        }
        if ((financialYear != null) && (financialYear != 0l)) {
            builder.append(" vt.financialYear=:financialYear AND");
        }
        if ((voucherType != null) && (voucherType != 0l)) {
            builder.append(" vt.voucherType=:voucherType AND");
        }
        if ((department != null) && (department != 0l)) {
            builder.append(" vt.department=:department AND");
        }
        if ((templateFor != null) && (templateFor != 0l)) {
            builder.append(" vt.templateFor=:templateFor AND");
        }
        if ((status != null) && (status != 0l)) {
            builder.append(" vt.status=:status AND");
        }
        builder.append(" vt.orgid=:orgId");
        builder.append(" order by 1 desc");
        return builder.toString();
    }

    private String buildDynamicDataQuery(final Long templateType, final Long financialYear, final Long voucherType,
            final Long templateFor, final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(" FROM VoucherTemplateMasterEntity vt WHERE ");

        if ((templateType != null) && (templateType != 0l)) {
            builder.append(" vt.templateType=:templateType AND");
        }
        if ((financialYear != null) && (financialYear != 0l)) {
            builder.append(" vt.financialYear=:financialYear AND");
        }
        if ((voucherType != null) && (voucherType != 0l)) {
            builder.append(" vt.voucherType=:voucherType AND");
        }
        if ((templateFor != null) && (templateFor != 0l)) {
            builder.append(" vt.templateFor=:templateFor AND");
        }
        builder.append(" vt.orgid=:orgId");

        return builder.toString();
    }

    @Override
    @Transactional
    public long templateExistOrNot(final Long templateType, final Long financialYear, final Long voucherType,
            final Long department,
            final Long templateFor, final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(
                " SELECT COUNT(vt.templateId) FROM VoucherTemplateMasterEntity vt WHERE vt.templateType=:templateType AND ");
        if ((financialYear != null) && (financialYear != 0l)) {
            builder.append("vt.financialYear=:financialYear AND ");
        }
        builder.append(
                "vt.voucherType=:voucherType AND vt.department=:department AND vt.templateFor=:templateFor AND vt.orgid=:orgId AND vt.status=:status");
        final Query query = entityManager.createQuery(builder.toString());
        query.setParameter("templateType", templateType);
        query.setParameter("voucherType", voucherType);
        query.setParameter("department", department);
        query.setParameter("templateFor", templateFor);
        query.setParameter("orgId", orgId);
        query.setParameter("status", CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("A", AccountPrefix.ACN.toString(), orgId));

        if ((financialYear != null) && (financialYear != 0l)) {
            query.setParameter("financialYear", financialYear);
        }
        Object result = null;
        try {
            result = query.getSingleResult();
        } catch (final Exception e) {
            LOGGER.error("No record found from table for provided input Params[templateType=" + templateType + "financialYear="
                    + financialYear + "voucherType=" + voucherType + "department=" + department + "templateFor=" + templateFor
                    + "orgId=" + orgId + "]", e);
        }
        return (long) (result == null ? 0l : result);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<VoucherTemplateMasterEntity> searchByAllTemplateData(final Long orgId) {
        LOGGER.info("querying voucher template for these params[orgId=" + orgId + "]");
        final Query query = entityManager.createQuery(buildDynamicAllQueryData(orgId));
        query.setParameter("orgId", orgId);

        return query.getResultList();
    }

    private String buildDynamicAllQueryData(final Long orgId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" FROM VoucherTemplateMasterEntity vt WHERE ");
        builder.append(" vt.orgid=:orgId");
        builder.append(" order by 1 desc");
        return builder.toString();
    }

    @Override
    @Transactional
    public VoucherTemplateMasterEntity getSacHeadIdForVoucherTemplate(final Long voucherSubTypeId,
            final Long deptId,
            final Long status, final Long orgid) {

        final Query query = entityManager
                .createQuery(buildDynamicSacHeadIdForVoucherTemplate(voucherSubTypeId, deptId, status, orgid));
        query.setParameter("voucherSubTypeId", voucherSubTypeId);
        query.setParameter("deptId", deptId);
        query.setParameter("status", status);
        query.setParameter("orgid", orgid);
        return (VoucherTemplateMasterEntity) query.getSingleResult();
    }

    private String buildDynamicSacHeadIdForVoucherTemplate(final Long voucherSubTypeId, final Long deptId,
            final Long status, final Long orgid) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" FROM VoucherTemplateMasterEntity vt WHERE ");
        builder.append(" vt.templateFor=:voucherSubTypeId AND");
        builder.append(" vt.department=:deptId AND");
        builder.append(" vt.status=:status AND");
        builder.append(" vt.orgid=:orgid");
        builder.append(" order by 1 desc");
        return builder.toString();
    }

}
