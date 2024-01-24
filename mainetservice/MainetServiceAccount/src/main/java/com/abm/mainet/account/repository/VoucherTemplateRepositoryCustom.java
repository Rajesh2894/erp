
package com.abm.mainet.account.repository;

import java.util.List;

import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;

/**
 * @author Vivek.Kumar
 * @since 01 Feb 2017
 *
 */
public interface VoucherTemplateRepositoryCustom {

    List<VoucherTemplateMasterEntity> searchTemplateData(Long templateType, Long financialYear, Long voucherType,
            Long templateFor, Long orgId);

    List<VoucherTemplateMasterEntity> searchTemplate(Long templateType, Long financialYear, Long voucherType,
            Long department, Long templateFor, Long status, Long orgId);

    long templateExistOrNot(Long templateType, Long financialYear, Long voucherType,
            Long department, Long templateFor, Long orgId);

    List<VoucherTemplateMasterEntity> searchByAllTemplateData(Long orgId);

    /**
     *
     * @param templateFor
     * @param deptId
     * @param orgId
     * @param yesrId
     * @return
     */
    VoucherTemplateMasterEntity queryDefinedTemplate(Long templateFor, Long deptId, Long orgId, Long status,
            Long financialYearId);

    // Object generateSeqNo(Object[] ipValues, long orgId);

    VoucherTemplateMasterEntity getSacHeadIdForVoucherTemplate(Long voucherSubTypeId, Long deptId, Long status,
            Long orgid);

}
