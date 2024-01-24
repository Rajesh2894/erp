
package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;

/**
 * @author Vivek.Kumar
 *
 */

public interface VoucherTemplateRepository
        extends CrudRepository<VoucherTemplateMasterEntity, Long>, VoucherTemplateRepositoryCustom {

    @Query("SELECT vt FROM VoucherTemplateMasterEntity vt JOIN FETCH vt.templateDetailEntities WHERE vt.templateId=:templateId ")
    VoucherTemplateMasterEntity queryTemplateById(@Param("templateId") long templateId);

    @Query("SELECT vt.templateFor FROM VoucherTemplateMasterEntity vt WHERE vt.voucherType=:voucherType AND vt.department=:deptId AND vt.orgid=:orgId AND vt.status=:status")
    List<Long> queryVoucherSubTypesForVoucher(@Param("voucherType") Long voucherType,
            @Param("deptId") Long department,
            @Param("orgId") Long orgId,
            @Param("status") Long status);

    @Query("SELECT vt FROM VoucherTemplateMasterEntity vt JOIN FETCH vt.templateDetailEntities WHERE vt.templateFor=:templateFor AND vt.orgid=:orgId AND vt.status=:status")
    VoucherTemplateMasterEntity findAcHeadCodeInTemplateForCashMode(@Param("templateFor") Long templateFor,
            @Param("orgId") Long orgId,
            @Param("status") Long status);

    @Query("SELECT b.acHeadCode "
            + " FROM VoucherTemplateDetailEntity v, AccountHeadSecondaryAccountCodeMasterEntity b  "
            + " WHERE v.sacHeadId=b.sacHeadId AND v.orgid=b.orgid AND v.sacHeadId=:sacHeadId "
            + " AND v.templateId.templateId=:templateId AND v.accountType=:accountType AND v.orgid = :orgid ORDER BY b.acHeadCode ASC")
    String callAcHeadCodeDesc(@Param("orgid") long orgId, @Param("templateId") long templateId,
            @Param("accountType") Long accountType, @Param("sacHeadId") Long sacHeadId);

    @Query("select td from VoucherTemplateDetailEntity td where td.templateId.templateId=:templateId and td.orgid=:orgId")
    List<VoucherTemplateDetailEntity> queryDefinedTemplateDet(@Param("templateId") long templateId, @Param("orgId") Long orgId);

    @Query("select td.cpdIdDrcr from VoucherTemplateDetailEntity td where td.templateId.templateId=:templateId and td.accountType=:cpdIdAcHeadType and td.orgid=:orgId and td.templateId.status=:statusId and td.sacHeadId IS NULL")
    Long getDrCrIdByPassingSacHeadId(@Param("templateId") long templateId, @Param("cpdIdAcHeadType") Long cpdIdAcHeadType,
            @Param("orgId") Long orgId, @Param("statusId") Long statusId);

    @Query("select td.cpdIdDrcr,td.sacHeadId from VoucherTemplateDetailEntity td where td.templateId.templateId=:templateId and td.cpdIdPayMode=:payModeId and td.orgid=:orgId and td.templateId.status=:statusId and td.sacHeadId IS NOT NULL")
    List<Object[]> getDrCrIdByPassingModeId(@Param("templateId") long templateId, @Param("payModeId") Long payModeId,
            @Param("orgId") Long orgId, @Param("statusId") Long statusId);

    @Query("select count(*) from VoucherTemplateDetailEntity td where td.templateId.templateId=:templateId and td.accountType=:cpdIdAcHeadTypes and td.cpdIdDrcr=:drCrValue and td.orgid=:orgId")
    Long getAccountHeadTypeAndDrCrExists(@Param("templateId") long templateId, @Param("cpdIdAcHeadTypes") Long cpdIdAcHeadTypes,
            @Param("drCrValue") Long drCrValue, @Param("orgId") Long orgId);

    @Query(value = "select CPD_ID_ACCOUNT_TYPE,sac_head_id from tb_ac_vouchertemplate_det where TEMPLATE_ID in (\r\n" +
            "select TEMPLATE_ID from tb_ac_vouchertemplate_mas where COALESCE(fa_yearid,0)=:yearId and cpd_id_template_for=:demandTypeId and dp_deptid=:departmentId\r\n"
            +
            "and orgid=:orgId and CPD_ID_STATUS_FLG=:status) and CPD_ID_DRCR is not null and sac_head_id is not null", nativeQuery = true)
    List<Object[]> getDrCrIdByPassingDemandTypeId(@Param("demandTypeId") Long demandTypeId, @Param("yearId") Long yearId,
            @Param("departmentId") Long departmentId, @Param("status") Long status,
            @Param("orgId") Long orgId);

    @Query("select vouNo from AccountVoucherEntryEntity account where account.vouTypeCpdId=:LookUpId and account.vouReferenceNo=:vouReferenceNo and account.vouPostingDate=:vouPostingDate and account.org=:orgId")
    String getVoucherNo(@Param("LookUpId") Long LookUpId, @Param("vouReferenceNo") String vouReferenceNo,
            @Param("vouPostingDate") Date vouPostingDate, @Param("orgId") Long orgId);

}
