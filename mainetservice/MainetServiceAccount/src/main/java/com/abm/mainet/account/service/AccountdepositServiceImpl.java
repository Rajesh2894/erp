
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountDepositDao;
import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.AccountDepositUploadDto;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.TbAcVendormasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountdepositServiceImpl implements AccountDepositService {

    @Resource
    private AccountDepositRepository accountDepositJparepository;

    @Resource
    private TbAcVendormasterJpaRepository vendormasterjparepository;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private AccountDepositDao accountDepositDao;
    
    @Resource
	private DepartmentService departmentService;

    private String generateDepositNumber(final Long OrgId) {
    	//Need to configure here
    	String depositNumberAsString=null;
		SequenceConfigMasterDTO configMasterDTO = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
        configMasterDTO = seqGenFunctionUtility.loadSequenceData(OrgId, deptId,
        		MainetConstants.TB_AC_DEPOSITS,  MainetConstants.AccountDeposit.DEP_NO);
        if (configMasterDTO.getSeqConfigId() == null) {
        	final Long depositNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                    MainetConstants.TB_AC_DEPOSITS, MainetConstants.AccountDeposit.DEP_NO,
                    OrgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, null);
        	depositNumberAsString=depositNumber.toString();
        }else {
       	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
       	/* AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(billEntryBean.getFieldId());
       	 if(fieldMaster!=null) {
       		 commonDto.setCustomField(fieldMaster.getFieldDesc());
       	 }*/
        	depositNumberAsString=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
       }
        return depositNumberAsString.toString();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public AccountDepositBean create(final AccountDepositBean bean) throws ParseException {

        final AccountDepositEntity deposit = new AccountDepositEntity();

        if ((bean.getDepId() != null) && (bean.getDepId() > 0L)) {
            bean.setDepId(bean.getDepId());
        }

        BeanUtils.copyProperties(bean, deposit);
        deposit.setDepReceiptdt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).parse(bean.getDepEntryDate()));
        deposit.setDefectLiabilityDate(Utility.stringToDate(bean.getDefectLiablityDate()));
        deposit.setSacHeadId(bean.getSacHeadId());
        final Long sacHeadId = accountDepositJparepository.getSacHeadIdByBudgetCodeId(bean.getSacHeadId(), bean.getOrgid());
        if (sacHeadId != null) {
            bean.setSacHeadId(sacHeadId);
        }

        final Department dep = new Department();
        dep.setDpDeptid(bean.getDpDeptid());
        deposit.setTbDepartment(dep);

        final TbComparamDetEntity status = new TbComparamDetEntity();
        status.setCpdId(bean.getCpdStatus());
        deposit.setTbComparamDet3(status);
        
        Organisation org = new Organisation();
        org.setOrgid(bean.getOrgid());
        LookUp cpdStatusLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(bean.getCpdStatus(), org);

        if(StringUtils.equals("RD", cpdStatusLookUp.getLookUpCode())) {
        	deposit.setDep_del_flag(MainetConstants.FlagY);
        }
        final TbComparamDetEntity type = new TbComparamDetEntity();
        type.setCpdId(bean.getCpdDepositType());
        deposit.setTbComparamDet(type);
        final TbAcVendormasterEntity vendor = new TbAcVendormasterEntity();
        vendor.setVmVendorid(bean.getVmVendorid());
        deposit.setTbVendormaster(vendor);

        if (deposit.getDepNo() == null || deposit.getDepNo().isEmpty()) {
            final String depositNumber = generateDepositNumber(bean.getOrgid());
            if ((depositNumber != null) && !depositNumber.isEmpty()) {
                deposit.setDepNo(depositNumber);
            }
        } else {
            deposit.setDepNo(deposit.getDepNo());
        }
        // easy to find that record was inserted receipt form or any one other area.
        if (bean.getAdv_del_flag() != null && !bean.getAdv_del_flag().isEmpty()) {
            deposit.setDepEntryTypeFlag(bean.getAdv_del_flag());
        }
        deposit.setDepReferenceNo(bean.getDepReceiptno());

        accountDepositJparepository.save(deposit);
        bean.setDepNo(deposit.getDepNo());

        return bean;

    }

    @Override
    @Transactional(readOnly = true)
    public AccountDepositEntity fidbyId(final Long depid) {

        return accountDepositJparepository.findOne(depid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDepositBean> findAll(final Long orgId) {

        final List<AccountDepositBean> list = new ArrayList<>();

        final List<AccountDepositEntity> listEntity = accountDepositJparepository.findAll(orgId);

        AccountDepositBean bean = null;
        if ((listEntity != null) && !listEntity.isEmpty()) {
            for (final AccountDepositEntity entity : listEntity) {

                bean = new AccountDepositBean();
                bean.setDepId(entity.getDepId());
                bean.setDepNo(entity.getDepNo());
                bean.setDepEntryDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDepReceiptdt()));
                bean.setTempDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDepReceiptdt()));
                bean.setSacHeadId(entity.getSacHeadId());
                bean.setDepReceiptno(entity.getDepReferenceNo());
                bean.setCpdDepositTypeDup(CommonMasterUtility.findLookUpDesc(AccountPrefix.DTY.toString(), orgId,
                        entity.getTbComparamDet().getCpdId()));
                bean.setVendorName(entity.getTbVendormaster().getVmVendorname());
                bean.setDepositAmount(CommonMasterUtility.getAmountInIndianCurrency(entity.getDepAmount()));
                bean.setDpDeptid(entity.getTbDepartment().getDpDeptid());
                bean.setStatusCodeFlag(CommonMasterUtility.findLookUpCode(AccountPrefix.RDC.toString(), orgId,
                        entity.getTbComparamDet3().getCpdId()));
                bean.setStatusCodeValue(CommonMasterUtility.findLookUpDesc(AccountPrefix.RDC.toString(), orgId,
                        entity.getTbComparamDet3().getCpdId()));
                if (entity.getDepEntryTypeFlag() != null && !entity.getDepEntryTypeFlag().isEmpty()) {
                    bean.setAdv_del_flag(entity.getDepEntryTypeFlag());
                }
                list.add(bean);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDepositBean> findByAllGridSearchData(final String depNo, final Long vmVendorid, final Long cpdDepositType,
            final Long sacHeadId, final Date date, final String depAmount, final Long orgId,Long deptId) {

        final List<AccountDepositBean> list = new ArrayList<>();

        final List<AccountDepositEntity> listEntity = accountDepositDao.findByAllGridSearchData(depNo, vmVendorid, cpdDepositType,
                sacHeadId, date, depAmount, orgId,deptId);

        AccountDepositBean bean = null;
        if ((listEntity != null) && !listEntity.isEmpty()) {
            for (final AccountDepositEntity entity : listEntity) {

                bean = new AccountDepositBean();
                bean.setDepId(entity.getDepId());
                bean.setDepNo(entity.getDepNo());
                if (entity.getDepReceiptdt() != null) {
                    bean.setDepEntryDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDepReceiptdt()));
                    bean.setTempDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDepReceiptdt()));
                }

                bean.setSacHeadId(entity.getSacHeadId());
                bean.setDepReceiptno(entity.getDepReferenceNo());
                bean.setCpdDepositTypeDup(CommonMasterUtility.findLookUpDesc(AccountPrefix.DTY.toString(), orgId,
                        entity.getTbComparamDet().getCpdId()));
                bean.setVendorName(entity.getTbVendormaster().getVmVendorname());
                bean.setDepositAmount(CommonMasterUtility.getAmountInIndianCurrency(entity.getDepAmount()));
                if (entity.getDepRefundBal() != null) {
                    bean.setBalanceAmount(CommonMasterUtility.getAmountInIndianCurrency(entity.getDepRefundBal()));
                } else {
                    bean.setBalanceAmount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                }
                bean.setStatusCodeFlag(CommonMasterUtility.findLookUpCode(AccountPrefix.RDC.toString(), orgId,
                        entity.getTbComparamDet3().getCpdId()));
                bean.setStatusCodeValue(CommonMasterUtility.findLookUpDesc(AccountPrefix.RDC.toString(), orgId,
                        entity.getTbComparamDet3().getCpdId()));
                if (entity.getDepEntryTypeFlag() != null && !entity.getDepEntryTypeFlag().isEmpty()) {
                    bean.setAdv_del_flag(entity.getDepEntryTypeFlag());
                }
                list.add(bean);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getEmdAmount(final Long trEmdAmt, final Long orgId) {
        final BigDecimal emdAmount = accountDepositJparepository.getEmdAmount(trEmdAmt, orgId);

        return emdAmount;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveAccountDepositExcelData(AccountDepositUploadDto accountDepositUploadDto, Long orgId, int langId,
            Long statusId) throws ParseException {
        AccountDepositEntity depositEntity = new AccountDepositEntity();
        AccountDepositBean depositBean = new AccountDepositBean();
        depositBean.setOrgid(accountDepositUploadDto.getOrgid());
        depositBean.setLgIpMac(accountDepositUploadDto.getLgIpMac());
        depositBean.setCreatedBy(accountDepositUploadDto.getUserId());
        depositBean.setCreatedDate(accountDepositUploadDto.getLmoddate());
        depositBean.setDepReceiptdt(new Date(accountDepositUploadDto.getDepositDate()));

        final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        Date dateFromParsing = null;
        String sampleDate = accountDepositUploadDto.getDepositDate();
        try {
            dateFromParsing = dateFormater.parse(sampleDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String depositDate = Utility.dateToString(dateFromParsing);
        depositBean.setDepEntryDate(depositDate);
        depositBean.setCpdDepositType(Long.valueOf(accountDepositUploadDto.getTypeOfDeposit()));
        //D#161797
        //depositBean.setDepId(Long.valueOf(accountDepositUploadDto.getDepartment()));
        depositBean.setDpDeptid(Long.valueOf(accountDepositUploadDto.getDepartment()));
        depositBean.setDepReceiptno((accountDepositUploadDto.getRefNo()));
        depositBean.setDepAmount(accountDepositUploadDto.getDepositAmount());
        depositBean.setDepRefundBal(accountDepositUploadDto.getBalAmount());
        depositBean.setVmVendorid(accountDepositUploadDto.getVendorId());
        depositBean.setDepReceivedfrom(accountDepositUploadDto.getDepositerName());
        depositBean.setDepNarration(accountDepositUploadDto.getNarration());
        depositBean.setSacHeadId(Long.valueOf(accountDepositUploadDto.getDepositHead()));
        depositBean.setCpdStatus(statusId);
        create(depositBean);
    }

}
