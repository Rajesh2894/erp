package com.abm.mainet.socialsecurity.mapper;

import java.util.Date;

import com.abm.mainet.socialsecurity.domain.BeneficiaryPaymentDetailEntity;
import com.abm.mainet.socialsecurity.ui.dto.BeneficiaryPaymentOrderDto;

public class BeneficiaryPaymentDetMapper {

    public static BeneficiaryPaymentDetailEntity dtoToEntity(BeneficiaryPaymentOrderDto dto) {

        BeneficiaryPaymentDetailEntity entity = new BeneficiaryPaymentDetailEntity();
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setAmount(dto.getAmount());
        entity.setApplicationNumber(dto.getApplicationNumber());
        entity.setBankId(dto.getBankId());
        entity.setBeneficiaryName(dto.getBeneficiaryName());
        entity.setBeneficiaryNumber(dto.getBeneficiaryNumber());
        entity.setCreatedBy(dto.getEmpId());
        entity.setCreatedDate(new Date());
        entity.setIfscCode(dto.getIfscCode());
        entity.setLgIpMac(dto.getIpAddress());
        entity.setOrgId(dto.getOrgId());
        entity.setPaymentScheduleId(dto.getPaymentScheduleId());
        entity.setRemark(dto.getRemark());
        entity.setRtgsTransId(dto.getRtgsTransId());
        entity.setSchemeId(dto.getSchemeId());
        entity.setWorkOrderNumber(dto.getWorkOrderNumber());
        entity.setWorkOrdrerDate(dto.getWorkOrdrerDate());
        entity.setRtgsStatus(dto.getRtgsStatus());
        entity.setRtgsBillno(dto.getBillNumber());

        return entity;

    }

    public static BeneficiaryPaymentOrderDto entityToDto(BeneficiaryPaymentDetailEntity k) {

        BeneficiaryPaymentOrderDto dto = new BeneficiaryPaymentOrderDto();
        dto.setAccountNumber(k.getAccountNumber());
        dto.setAmount(k.getAmount());
        dto.setApplicationNumber(k.getApplicationNumber());
        dto.setBankId(k.getBankId());
        dto.setBeneficiaryName(k.getBeneficiaryName());
        dto.setBeneficiaryNumber(k.getBeneficiaryNumber());
        dto.setEmpId(k.getCreatedBy());
        dto.setCreatedDate(k.getCreatedDate());
        dto.setIfscCode(k.getIfscCode());
        dto.setIpAddress(k.getLgIpMac());
        dto.setOrgId(k.getOrgId());
        dto.setPaymentScheduleId(k.getPaymentScheduleId());
        dto.setRemark(k.getRemark());
        dto.setRtgsTransId(k.getRtgsTransId());
        dto.setSchemeId(k.getSchemeId());
        dto.setWorkOrderNumber(k.getWorkOrderNumber());
        dto.setWorkOrdrerDate(k.getWorkOrdrerDate());
        dto.setRtgsStatus(k.getRtgsStatus());
        dto.setBillNumber(k.getRtgsBillno());
        return dto;
    }
}