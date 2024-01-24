
package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.BudgetHeadDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;

/**
 * @author Vivek.Kumar
 * @since 13 - JAN 2017
 */
public interface VoucherTemplateService {

    /**
     * used to populate Model which are needed in form
     * @param orgId : pass orgId
     * @return VoucherTemplateDTO which holds required data
     *
     */
    VoucherTemplateDTO populateModel(long orgId);

    /**
     * used to find Budget Code
     * @param orgId
     * @param mode
     * @return
     */
    List<BudgetHeadDTO> findBudgetCodeByMode(long payMode, long orgId);

    boolean createVoucherTemplate(VoucherTemplateDTO voucherTemplate, long orgId);

    List<VoucherTemplateDTO> searchTemplate(VoucherTemplateDTO voucherTemplate, long orgId);

    VoucherTemplateDTO viewTemplate(long templateId);

    VoucherTemplateDTO editTemplate(long templateId);

    boolean isTemplateExist(VoucherTemplateDTO templateType, long orgId);

    List<VoucherTemplateDTO> searchByAllTemplateData(long orgId);

    Long getSacHeadIdForVoucherTemplate(Long voucherSubTypeId, Long modeId, Long deptId, Long status, Long orgid);

    String getVoucherNoBy(Long LookUpId, String vouReferenceNo, Date vouPostingDate, Long orgId);
}
