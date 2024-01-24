package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.quartz.domain.ViewAccountVoucherEntryDetailsEntity;
import com.abm.mainet.quartz.domain.ViewAccountVoucherEntryEntity;
import com.abm.mainet.quartz.domain.ViewSalaryBillMasterEntity;
import com.abm.mainet.quartz.domain.ViewServiceReceiptMasEntity;

public interface FindVoucherDataRepository extends PagingAndSortingRepository<ViewAccountVoucherEntryEntity, Long> {

    @Query("select e from ViewAccountVoucherEntryEntity e where e.org=:orgid order by 1 asc")
    List<ViewAccountVoucherEntryEntity> getVoucherEntryData(@Param("orgid") Long orgId);

    @Query("select e from ViewServiceReceiptMasEntity e  where e.orgId=:orgid order by 1 asc")
    List<ViewServiceReceiptMasEntity> getPropertyTexReceiptPosting(@Param("orgid") Long orgId);

    @Query("select e from ViewAccountVoucherEntryDetailsEntity e where e.master.vouId=:vouId order by 1 asc")
    List<ViewAccountVoucherEntryDetailsEntity> getVoucherDetEntryData(@Param("vouId") Long vouId);

    @Query("select v from AccountVoucherEntryEntity v where v.vouReferenceNo=:vouId and v.org=:orgId and v.fi04Lo1='Y'")
    List<AccountVoucherEntryEntity> checkVoucherEntryDataExists(@Param("vouId") String vouId, @Param("orgId") Long orgId);

    @Query("select fm.fieldId from AccountFieldMasterEntity fm where fm.orgid=:orgId and fm.fieldCompcode='1-1'")
    Long getFiledId(@Param("orgId") Long orgId);

    @Query("select rm from TbServiceReceiptMasEntity rm where rm.refId=:rmRcptid and rm.orgId=:orgId and rm.rmLo1='Y'")
    List<TbServiceReceiptMasEntity> checkReceiptEntryDataExists(@Param("rmRcptid") Long rmRcptid, @Param("orgId") Long orgId);

    @Query("select sum(rd.rfFeeamount) from ViewSrcptFeesDetEntity rd where rd.rmRcptid.rmRcptid=:rmRcptid and rd.billType=:billType")
    BigDecimal getSumOfRecDetAmount(@Param("rmRcptid") long rmRcptid, @Param("billType") String billType);

    @Query("select e from ViewSalaryBillMasterEntity e where e.orgId=:orgid order by 1 asc")
    List<ViewSalaryBillMasterEntity> getSalaryRecordsForUpload(@Param("orgid") Long orgid);

    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.billIntRefId=:bmId and bm.orgId=:orgId")
    List<AccountBillEntryMasterEnitity> checkSalBillEntryDataExists(@Param("bmId") Long bmId, @Param("orgId") Long orgId);

    @Query("select count(*) from BankAccountMasterEntity account where account.baAccountId =:baAccountId and account.orgId =:orgId")
    Long checkBankIdExistsOrNot(@Param("baAccountId") Long baAccountId, @Param("orgId") Long orgId);

}
