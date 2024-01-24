package com.abm.mainet.common.master.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbDepositEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbDepositDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.repository.TbDepositReceiptRepository;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;

/**
 * @author jeetedra.pal
 *
 */

@Service
public class DepositReceiptServiceImpl implements IDepositReceiptService {

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private TbDepositReceiptRepository depositReceiptRepository;

    @Autowired
    private IReceiptEntryService receiptEntryService;

    @Override
    @Transactional

    public void saveAndUpdateDepositReceiptEntry(TbDepositDto tbDepositDto) {
        TbDepositEntity depositEntity = new TbDepositEntity();
        TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();
        try {
            BeanUtils.copyProperties(tbDepositDto, depositEntity);
            BeanUtils.copyProperties(tbDepositDto.getReceiptMasBean(), receiptMasEntity);
            depositEntity.setRmRcptid(receiptMasEntity);
            depositEntity.setDepNo(getDepositNumber(tbDepositDto));
            depositReceiptRepository.save(depositEntity);

        } catch (Exception e) {
            throw new FrameworkException(
                    "Exception acured while  calling method saveAndUpdateDepositReceiptEntry " + e);
        }
    }

    public Long getDepositNumber(TbDepositDto tbDepositDto) {
        return seqGenFunctionUtility.generateSequenceNo("COM", "TB_DEPOSIT", "DEP_NO", tbDepositDto.getOrgId(),
                MainetConstants.FlagF, tbDepositDto.getDpDeptId());
    }

    @Override
    @Transactional
    public TbServiceReceiptMasBean saveReceiptEntry(CommonChallanDTO requestDto) {
        TbServiceReceiptMasEntity receiptMasEntity = receiptEntryService.insertInReceiptMaster(requestDto, null);
        TbServiceReceiptMasBean receiptMasBean = new TbServiceReceiptMasBean();
        receiptMasBean.setRmRcptno(receiptMasEntity.getRmRcptno());
        receiptMasBean.setFieldId(receiptMasEntity.getFieldId());
        receiptMasBean.setRmDate(receiptMasEntity.getRmDate());
        receiptMasBean.setGstNo(requestDto.getGstNo());
        if(receiptMasEntity.getSmServiceId()!=null)
        receiptMasBean.setSmServiceId(receiptMasEntity.getSmServiceId());
        receiptMasBean.setOrgId(receiptMasEntity.getOrgId());
        String recCatrogoryTypeCode = CommonMasterUtility.findLookUpCode(
                MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, receiptMasEntity.getOrgId(),
                receiptMasEntity.getRmReceiptcategoryId());
        if (receiptMasEntity != null && recCatrogoryTypeCode.equals(MainetConstants.FlagP)) {
            TbDepositDto tbDepositDto = new TbDepositDto();
            BeanUtils.copyProperties(receiptMasEntity, receiptMasBean);
            tbDepositDto.setOrgId(receiptMasEntity.getOrgId());
            tbDepositDto.setDepDate(receiptMasEntity.getRmDate());
            tbDepositDto
                    .setDepType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(receiptMasEntity.getReceiptTypeFlag(),
                            MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, receiptMasEntity.getOrgId()));
            tbDepositDto.setDpDeptId(receiptMasEntity.getDpDeptId());
            tbDepositDto.setDepAmount(receiptMasEntity.getRmAmount());
            tbDepositDto.setCreatedBy(receiptMasEntity.getCreatedBy());
            tbDepositDto.setCreatedDate(new Date());
            tbDepositDto.setLgIpMac(receiptMasEntity.getLgIpMac());
            tbDepositDto.setDepRefId(receiptMasEntity.getApmApplicationId());
            tbDepositDto.setDepReceiptNo(receiptMasEntity.getRmRcptno());
            tbDepositDto.setReceiptMasBean(receiptMasBean);
            tbDepositDto.setDepEntryFlag(MainetConstants.FlagS);
            tbDepositDto.setDepStatus("DO");
            tbDepositDto.setDepRefId(receiptMasEntity.getApmApplicationId());
            tbDepositDto.setDepNarration(receiptMasEntity.getRmNarration());
            tbDepositDto.setVendorId(receiptMasEntity.getVmVendorId());
            tbDepositDto.setDepRefundBal(tbDepositDto.getDepAmount());
            if (receiptMasEntity.getReceiptFeeDetail() != null) {
                tbDepositDto.setSacHeadId(receiptMasEntity.getReceiptFeeDetail().get(0).getSacHeadId());
            }
            saveAndUpdateDepositReceiptEntry(tbDepositDto);
        }
        return receiptMasBean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbDepositDto> getDepositRefundList(Long depNo, Long cpdDepositType, Date depositDate, Long vmVendorid,
            BigDecimal depositAmount, Long sacHeadId, Long orgId) {

        final List<TbDepositDto> list = new ArrayList<>();
        TbDepositDto dto = null;
        final List<TbDepositEntity> listEntity = depositReceiptRepository.getDepositRefundList(depNo, vmVendorid,
                cpdDepositType, sacHeadId, depositDate, depositAmount, orgId);
        for (TbDepositEntity tbDepositEntity : listEntity) {
            dto = new TbDepositDto();
            BeanUtils.copyProperties(tbDepositEntity, dto);
            list.add(dto);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public TbDepositDto getDepositRefundById(Long depositId) {
        TbDepositDto dto = new TbDepositDto();
        TbDepositEntity listEntity = depositReceiptRepository.findOne(depositId);
        if (listEntity != null)
            BeanUtils.copyProperties(listEntity, dto);
        return dto;
    }

    @Override
    @Transactional

    public void saveDepositReceiptEntry(TbDepositDto tbDepositDto) {
        TbDepositEntity depositEntity = new TbDepositEntity();
        try {
            BeanUtils.copyProperties(tbDepositDto, depositEntity);
            depositEntity.setDepNo(getDepositNumber(tbDepositDto));
            depositReceiptRepository.save(depositEntity);

        } catch (Exception e) {
            throw new FrameworkException(
                    "Exception acured while  calling method saveDepositReceiptEntry " + e);
        }
        
    }
}
