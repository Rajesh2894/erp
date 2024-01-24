
package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryDetailsBean;
import com.abm.mainet.account.dto.AccountVoucherCommPostingDetailDto;
import com.abm.mainet.account.dto.AccountVoucherCommPostingMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;

/**
 * @author dharmendra.chouhan
 *
 */
@Component
@Transactional
public class AccountVoucherCommPostingMasterServiceImpl implements AccountVoucherCommPostingMasterService {

    @Resource
    private AccountJournalVoucherService accountJournalVoucherService;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private IFinancialYearService iFinancialYearService;

    @Resource
    private DepartmentService departmentService;

    @Override
    public AccountVoucherCommPostingMasterDto deasEntryCommPosting(
            final AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto) {

        final AccountJournalVoucherEntryBean accountJournalVoucherEntryBean = new AccountJournalVoucherEntryBean();
        AccountJournalVoucherEntryDetailsBean accountJournalVoucherEntryDetailsBean = new AccountJournalVoucherEntryDetailsBean();
        final Date curDate = new Date();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(accountVoucherCommPostingMasterDto.getOrgid());

        final LookUp n_cpdid = CommonMasterUtility.getValueFromPrefixLookUp(
                accountVoucherCommPostingMasterDto.getVou_type_cpd_id_value(), PrefixConstants.ContraVoucherEntry.VOT,
                organisation);
        accountJournalVoucherEntryBean.setVouTypeCpdId(n_cpdid.getLookUpId());
        final LookUp n_cpdidsubtype = CommonMasterUtility.getValueFromPrefixLookUp(
                accountVoucherCommPostingMasterDto.getVou_subtype_cpd_id_value(), PrefixConstants.REV_SUB_CPD_VALUE,
                organisation);

        if (departmentService.getDepartmentIdByDeptCode(accountVoucherCommPostingMasterDto.getDp_deptid_code(),
                MainetConstants.MENU.A) != null) {
            accountJournalVoucherEntryBean.setDpDeptid(departmentService
                    .getDepartmentIdByDeptCode(accountVoucherCommPostingMasterDto.getDp_deptid_code(), MainetConstants.MENU.A));
        }

        if ((MainetConstants.MENU.Y).equals(accountVoucherCommPostingMasterDto.getAutho_flg())) {
            final String voucherNumber = findAuthVoucherNumber(accountVoucherCommPostingMasterDto.getVou_type_cpd_id_value(),
                    accountVoucherCommPostingMasterDto.getVou_date(), accountVoucherCommPostingMasterDto.getOrgid());
            accountJournalVoucherEntryBean.setVouNo(voucherNumber);
            accountJournalVoucherEntryBean.setAuthoId(accountVoucherCommPostingMasterDto.getCreated_by());
            accountJournalVoucherEntryBean.setAuthoDate(curDate);
        }

        accountJournalVoucherEntryBean.setVouSubtypeCpdId(n_cpdidsubtype.getLookUpId());
        accountJournalVoucherEntryBean.setVouDate(accountVoucherCommPostingMasterDto.getVou_date());
        accountJournalVoucherEntryBean.setVouPostingDate(accountVoucherCommPostingMasterDto.getVou_posting_date());
        accountJournalVoucherEntryBean.setOrg(accountVoucherCommPostingMasterDto.getOrgid());
        accountJournalVoucherEntryBean.setCreatedBy(accountVoucherCommPostingMasterDto.getCreated_by());
        accountJournalVoucherEntryBean.setLgIpMac(accountVoucherCommPostingMasterDto.getLg_ip_mac());
        accountJournalVoucherEntryBean.setLangId(accountVoucherCommPostingMasterDto.getLang_id().intValue());
        accountJournalVoucherEntryBean.setLmodDate(curDate);
        accountJournalVoucherEntryBean.setNarration(accountVoucherCommPostingMasterDto.getNarration());
        accountJournalVoucherEntryBean.setVouReferenceNo(accountVoucherCommPostingMasterDto.getVou_reference_no());
        accountJournalVoucherEntryBean.setVouReferenceNoDate(accountVoucherCommPostingMasterDto.getVou_reference_no_date());
        accountJournalVoucherEntryBean.setAuthoFlg(accountVoucherCommPostingMasterDto.getAutho_flg());
        accountJournalVoucherEntryBean.setEntryType(Long.valueOf(accountVoucherCommPostingMasterDto.getEntry_type()));
        if (accountVoucherCommPostingMasterDto.getFieldId() != null) {
            accountJournalVoucherEntryBean.setFieldId(accountVoucherCommPostingMasterDto.getFieldId());
        }

        final List<AccountJournalVoucherEntryDetailsBean> VoucherdetailsList = new ArrayList<>(0);
        if (accountVoucherCommPostingMasterDto.getAccountVoucherCommPostingDetailDto() != null) {

            for (final AccountVoucherCommPostingDetailDto drcrGroupHeadList : accountVoucherCommPostingMasterDto
                    .getAccountVoucherCommPostingDetailDto()) {
                accountJournalVoucherEntryDetailsBean = new AccountJournalVoucherEntryDetailsBean();
                final LookUp debitCreditCpdId = CommonMasterUtility.getValueFromPrefixLookUp(drcrGroupHeadList.getDrcrValue(),
                        PrefixConstants.DCR);
                accountJournalVoucherEntryDetailsBean.setDrcrCpdId(debitCreditCpdId.getLookUpId());
                accountJournalVoucherEntryDetailsBean.setMaster(accountJournalVoucherEntryBean);

                if (drcrGroupHeadList.getFundId() != null) {
                    accountJournalVoucherEntryDetailsBean.setFundId(drcrGroupHeadList.getFundId());
                }

                if (drcrGroupHeadList.getFunctionId() != null) {
                    accountJournalVoucherEntryDetailsBean.setFunctionId(drcrGroupHeadList.getFunctionId());
                }

                if (drcrGroupHeadList.getSacHeadId() != null) {
                    accountJournalVoucherEntryDetailsBean.setSacHeadId(drcrGroupHeadList.getSacHeadId());
                }

                if (drcrGroupHeadList.getVoudetAmt() != null) {
                    accountJournalVoucherEntryDetailsBean.setVoudetAmt(drcrGroupHeadList.getVoudetAmt());
                }

                accountJournalVoucherEntryDetailsBean.setOrgId(accountVoucherCommPostingMasterDto.getOrgid());
                accountJournalVoucherEntryDetailsBean.setCreatedBy(accountVoucherCommPostingMasterDto.getCreated_by());
                accountJournalVoucherEntryDetailsBean.setLgIpMac(accountVoucherCommPostingMasterDto.getLg_ip_mac());
                accountJournalVoucherEntryDetailsBean.setLangId(accountVoucherCommPostingMasterDto.getLang_id().intValue());
                accountJournalVoucherEntryDetailsBean.setLmodDate(curDate);
                VoucherdetailsList.add(accountJournalVoucherEntryDetailsBean);
                accountJournalVoucherEntryBean.setDetails(VoucherdetailsList);
            }

        }

        accountJournalVoucherService.saveAccountJournalVoucherEntry(accountJournalVoucherEntryBean);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public String findAuthVoucherNumber(final String voucherTypeRvPVACvJv, final Date voucherDate, final Long OrgId) {
        Long voucherType = null;
        long faYear = 0;
        final Object[] finData = iFinancialYearService.getFinacialYearByDate(voucherDate);
        if ((finData != null) && (finData.length > 0)) {
            faYear = (long) finData[0];
        }

        if ((MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 1 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.PV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 2 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.CV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 3 + faYear;
        } else if ((MainetConstants.AccountJournalVoucherEntry.JV).equals(voucherTypeRvPVACvJv)) {
            voucherType = 4 + faYear;
        }

        final Long vouNoyear = seqGenFunctionUtility.generateSequenceNo(
                MainetConstants.RECEIPT_MASTER.Module,
                AccountConstants.VOUCHERTABLE.getValue(),
                AccountConstants.VOUCHERCOLUNVOU_NO.getValue(),
                OrgId,
                AccountConstants.POSTRESET_TYPE.getValue(),
                voucherType);
        final String voucherNumber = voucherTypeRvPVACvJv.substring(0, 2)
                + MainetConstants.RECEIPT_MASTER.VOUCHER_NO.substring(vouNoyear.toString().length()) + vouNoyear.toString();
        return voucherNumber;
    }
}
