/**
 * 
 */
package com.abm.mainet.account.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.IStopPaymentDao;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.dto.StopPaymemtReqDto;
import com.abm.mainet.account.dto.TbAcChequebookleafDetDto;
import com.abm.mainet.account.repository.TbAcChequebookleafDetJpaRepository;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 12-Dec-2019
 */
@Service
public class StopPaymentServiceImpl implements IStopPaymentService {

    @Autowired
    private IStopPaymentDao stopPaymentDao;
    @Autowired
    private TbAcChequebookleafDetJpaRepository chequebookleafDetJpaRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.IStopPaymentService#getChequeBookIdByChequeNo(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional
    public TbAcChequebookleafDetDto getById(Long chequebookDetid) {
        TbAcChequebookleafDetDto detDto = null;
        try {
            TbAcChequebookleafDetEntity detEntity = chequebookleafDetJpaRepository.findById(chequebookDetid);
            if (detEntity != null) {
                detDto = new TbAcChequebookleafDetDto();
                BeanUtils.copyProperties(detEntity, detDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while fetching the StopPaymentEntry data", exception);
        }
        return detDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.IStopPaymentService#saveStopPaymentEntry(com.abm.mainet.account.dto.PaymentEntryDto)
     */
    @Override
    @Transactional
    public void updateStopPaymentEntry(Long chequeNo, Long cpdIdstatus, String stopPayRemark, Date stoppayDate, Long updatedBy,
            Date updatedDate,
            String lgIpMacUpd,
            Long orgId) {
        try {
            chequebookleafDetJpaRepository.updateStopPaymentEntry(chequeNo, cpdIdstatus,
                    stopPayRemark, stoppayDate, updatedBy, updatedDate, lgIpMacUpd, orgId);
        } catch (Exception exception) {
            throw new FrameworkException("Error occured while updating the StopPaymentEntry data", exception);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.IStopPaymentService#searchPaymentDetails(java.lang.String, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional
    public StopPaymemtReqDto searchPaymentDetails(String paymentNo, Long instrumentNumber, Date paymentDate, Long orgId) {
        StopPaymemtReqDto paymemtReqDto = new StopPaymemtReqDto();
        AccountPaymentMasterEntity masterEntity = stopPaymentDao.searchPaymentDetails(paymentNo, instrumentNumber, paymentDate,
                orgId);
        if (masterEntity != null) {
            paymemtReqDto = mapEntityToDto(masterEntity);
            paymemtReqDto.getPaymentEntryDto().setPaymentEntryDate(Utility.dateToString(masterEntity.getPaymentDate()));
            BeanUtils.copyProperties(masterEntity, paymemtReqDto);
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            LookUp billTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(masterEntity.getPaymentModeId().getCpdId(), org);
            paymemtReqDto.setBiilPaymentFlag(billTypeLookUp.getLookUpCode());
            if(StringUtils.equals(billTypeLookUp.getLookUpCode(), "Q")) {
            	 Long chequeBookSatatusId = chequebookleafDetJpaRepository.getCheckSatatus(masterEntity.getInstrumentNumber(), orgId);
                 paymemtReqDto.setChecqueStatusCode(CommonMasterUtility.getNonHierarchicalLookUpObject(chequeBookSatatusId, org).getLookUpCode());
            }
           
        }
        return paymemtReqDto;
    }

    private StopPaymemtReqDto mapEntityToDto(AccountPaymentMasterEntity accountPaymentMasterEntity) {
        StopPaymemtReqDto stopPaymemtReqDto = new StopPaymemtReqDto();
        if (accountPaymentMasterEntity.getPaymentId() > 0) {
            BeanUtils.copyProperties(accountPaymentMasterEntity, stopPaymemtReqDto.getPaymentEntryDto());
        }
        if (accountPaymentMasterEntity.getBaBankAccountId() != null) {
            BeanUtils.copyProperties(accountPaymentMasterEntity.getBaBankAccountId(),
                    stopPaymemtReqDto.getBankAccountMasterDto());
        }
        if (accountPaymentMasterEntity.getVmVendorId() != null) {
            BeanUtils.copyProperties(accountPaymentMasterEntity.getVmVendorId(), stopPaymemtReqDto.getTbAcVendormaster());
        }
        return stopPaymemtReqDto;
    }
}
