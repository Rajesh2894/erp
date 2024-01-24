package com.abm.mainet.bill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.dao.ChequeDishonorDao;
import com.abm.mainet.bill.repository.ChequeDishonorRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.repository.TbBankmasterJpaRepository;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author Rahul.Yadav
 *
 */
@Service
public class ChequeDishonorServiceImpl implements ChequeDishonorService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ChequeDishonorDao ChequeDishonorDao;

    @Autowired
    private BillMasterCommonService billMasterService;
    
    @Autowired
    private ChequeDishonorRepository chequeDishonorRepository;

    @Autowired
    private TbBankmasterJpaRepository tbBankmasterJpaRepository;
    
    @Autowired
    private TbTaxMasService tbTaxMasService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.ChequeDishonorService#fetchChequePaymentData(java.lang.Long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbServiceReceiptMasBean> fetchChequePaymentData(final List<Long> chequeId,
            final long orgid, final Long deptId, final Date fromDate, final Date toDate, final Long receiptNo,
            final Long chequeNo, final Long bankId) {
    	Organisation org=new Organisation();
    	org.setOrgid(orgid);
        List<TbServiceReceiptMasEntity> recpMasList = null;
        if ((bankId == null) || (bankId <= 0d)) {
        	recpMasList = ChequeDishonorDao.fetchChequePaymentData(chequeId, orgid, deptId, fromDate, toDate, receiptNo, chequeNo,
                    bankId);
        } else {
        	recpMasList = ChequeDishonorDao.fetchChequePaymentDataWithAccount(chequeId, orgid, deptId, fromDate, toDate, receiptNo,
                    chequeNo, bankId);
        }
        final List<TbServiceReceiptMasBean> receiptMas = new ArrayList<>(0);
        
		if (recpMasList != null) {
			for (final TbServiceReceiptMasEntity feeReceipt : recpMasList) {
				final List<TbSrcptModesDetBean> receiptModeList = new ArrayList<>();
				final TbServiceReceiptMasBean masDto = new TbServiceReceiptMasBean();
				feeReceipt.getReceiptModeDetail().forEach(modeDet -> {
					LookUp feeModeLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(modeDet.getCpdFeemode(),
							UserSession.getCurrent().getOrganisation());
					// while comparing variable in equal methods, always perform equals method on
					// constant to avoid null pointer exception -- code review by ritesh patil
					if (((PrefixConstants.PaymentMode.CHEQUE.equals(feeModeLookup.getLookUpCode())
							|| PrefixConstants.PaymentMode.PAYORDER.equals(feeModeLookup.getLookUpCode())
							|| PrefixConstants.PaymentMode.BANK.equals(feeModeLookup.getLookUpCode())
							|| PrefixConstants.PaymentMode.DEMAND_DRAFT.equals(feeModeLookup.getLookUpCode()))							
							&& ((null == chequeNo && null == modeDet.getRdSrChkDis()) || 
									(modeDet.getRdChequeddno()!=null && modeDet.getRdChequeddno() .equals(chequeNo))))) {
						// with one cheque no multiple entry in mode with billNo and amount as they
						// knock off
						Optional<TbSrcptModesDetBean> receiptMode = receiptModeList.stream()
								.filter(mode -> (mode.getRdChequeddno().equals(modeDet.getRdChequeddno())
										&& mode.getCbBankid().equals(modeDet.getCbBankid())))
								.findFirst();
							
						if (receiptMode.isPresent()) { // for already exist cheque
							Double amt = receiptMode.get().getBillNoAndAmountMap().get(modeDet.getBillIdNo());
							if (amt != null) {
								amt += modeDet.getRdAmount().doubleValue();
								receiptMode.get().getBillNoAndAmountMap().put(modeDet.getBillIdNo(), amt);
							} else {
								receiptMode.get().getBillNoAndAmountMap().put(modeDet.getBillIdNo(),
										modeDet.getRdAmount().doubleValue());
							}
							receiptMode.get().getModeKeyList().add(modeDet.getRdModesid());
							Double totalAmt = modeDet.getRdAmount().doubleValue()
									+ receiptMode.get().getRdAmount().doubleValue();
							receiptMode.get().setRdAmount(new BigDecimal(totalAmt));
							receiptMode.get().setReceiptId(feeReceipt.getRmRcptid());

						} else {// for new cheque no
							TbSrcptModesDetBean mode = new TbSrcptModesDetBean();
							BeanUtils.copyProperties(modeDet, mode);
							Map<Long, Double> billNoAndAmountMap = new HashMap<>();
							Map<Long, Double> penaltyWiseBillNoAndAmountMap = new HashMap<>();
							List<Long> modeKeyList = new ArrayList<>();
							billNoAndAmountMap.put(mode.getBillIdNo(), mode.getRdAmount().doubleValue());
							modeKeyList.add(modeDet.getRdModesid());
							mode.setBillNoAndAmountMap(billNoAndAmountMap);
							mode.setPenaltyWiseBillNoAndAmountMap(penaltyWiseBillNoAndAmountMap);
							mode.setModeKeyList(modeKeyList);
							mode.setOrgid(orgid);
							// mode.setDeptId(deptId);
							mode.setRdDrawnon(feeModeLookup.getLookUpDesc());
							mode.setReceiptId(feeReceipt.getRmRcptid());
							receiptModeList.add(mode);
						}
					}
				});
				List<TbSrcptFeesDetBean> recDetList = new ArrayList<>();
				feeReceipt.getReceiptFeeDetail().forEach(det -> {
					TbSrcptFeesDetBean dto = new TbSrcptFeesDetBean();
					BeanUtils.copyProperties(det, dto);
					recDetList.add(dto);
				});
				
				BeanUtils.copyProperties(feeReceipt, masDto);
				masDto.setReceiptModeList(receiptModeList);
				masDto.setReceiptFeeDetail(recDetList);
				receiptMas.add(masDto);
				
			}
			
			receiptMas.forEach(tr -> {
				tr.getReceiptModeList().forEach(modedet -> {
					getDishonorCharges(deptId, org, modedet); // fetching charges of dishonor from BRMS
				});
			});
		}
        return receiptMas;
    }
    
    @Override
    public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org,final  TbSrcptModesDetBean chequeModeDto) {
    	TbSrcptModesDetBean result = null;
        try {
            final ApplicationSession appSession = ApplicationSession.getInstance();
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(deptId);
            serviceClassName = messageSource.getMessage(
                    appSession.getMessage("bill.lbl.Bills") + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.getDishonorCharges(deptId,  org,  chequeModeDto);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in cheque dishonor revert bill for department:" + deptId,
                    e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.ChequeDishonorService#fetchReceiptFeeDetails(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public TbSrcptModesDetEntity fetchReceiptFeeDetails(final Long rdModesid,
            final Long orgid) {
        return ChequeDishonorDao.fetchReceiptFeeDetails(rdModesid, orgid);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.service.ChequeDishonorService#updateFeeDet(com.abm.mainetservice.core.entity.
     * TbSrcptModesDetEntity)
     */
    @Override
    @Transactional
    public void updateFeeDet(final TbSrcptModesDetEntity feeDet) {
        ChequeDishonorDao.updateFeeDet(feeDet);
    }

    @Override
    @Transactional
    public void updateLoiNotPaid(final String rmLoiNo, final Long orgid) {
        ChequeDishonorDao.updateLoiNotPaid(rmLoiNo, orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getULBDepositBanks(final long orgid, final Long statusId) {
        final List<Object[]> result = tbBankmasterJpaRepository.getActiveBankAccountList(orgid, statusId);
        final Map<Long, String> banks = new HashMap<>(0);
        if ((result != null) && !result.isEmpty()) {
            for (final Object[] array : result) {
                if ((array != null) && (array.length > 0)) {
                    banks.put(Long.valueOf(array[0].toString()), array[3].toString() + " : " +
                            array[2].toString() + " : " + array[1].toString());
                }
            }
        }
        return banks;
    }

    @Override
    public boolean revertBills(final TbServiceReceiptMasBean feedetailDto, Long userId, String ipAdress) {
        boolean result = false;
        try {
            final ApplicationSession appSession = ApplicationSession.getInstance();
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(feedetailDto.getDpDeptId());
            serviceClassName = messageSource.getMessage(
                    appSession.getMessage("bill.lbl.Bills") + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.revertBills(feedetailDto, userId, ipAdress);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in cheque dishonor revert bill for department:" + feedetailDto.getDpDeptId(),
                    e);
        }
        return result;
    }

	@Override
	@Transactional
	public boolean revertBills(TbSrcptModesDetBean modeDto, Long userId, String ipAdress , Long deptId) {
		  boolean result = false;
	        try {
	            final ApplicationSession appSession = ApplicationSession.getInstance();
	            BillGenerationService dynamicServiceInstance = null;
	            String serviceClassName = null;
	            final String deptCode = departmentService.getDeptCode(deptId);
	            serviceClassName = messageSource.getMessage(
	                    appSession.getMessage("bill.lbl.Bills") + deptCode, new Object[] {},
	                    StringUtils.EMPTY, Locale.ENGLISH);
	            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
	                    BillGenerationService.class);
	            result = dynamicServiceInstance.revertBills(modeDto, userId, ipAdress);
	        } catch (LinkageError | Exception e) {
	            throw new FrameworkException("Exception in cheque dishonor revert bill for department:" + deptId,
	                    e);
	        }
	        return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<TbSrcptModesDetEntity> fetchReceiptFeeDetails(List<Long> rdModesIds, Long orgId) {
		return ChequeDishonorDao.fetchReceiptFeeDetails(rdModesIds, orgId);
	}

	@Override
	@Transactional
	public void saveReceiptModeDetails(List<TbSrcptModesDetEntity> modeDetails) {
		chequeDishonorRepository.save(modeDetails);		
	}

	@Override
	@Transactional
	public List<Object[]> fetchPaymentMode(Long orgId, String date1) {
		return ChequeDishonorDao.fetchPaymentMode(orgId, date1);
	}
	
	
}
