package com.abm.mainet.common.integration.payment.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.payment.dto.PgBankParameterDetailDto;
import com.abm.mainet.common.integration.payment.dto.PgBankParameterMasDto;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.service.PgBankParameterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 24 sep 2020
 */
@Component
@Scope("session")
public class PgBankParameterDetailModel extends AbstractFormModel {

    private static final long serialVersionUID = -2829885799686175396L;

    @Autowired
    PgBankParameterService pgBankParameterService;

    private PgBankParameterMasDto pgBankMasterDto = new PgBankParameterMasDto();

    private PgBankParameterDetailDto pgBankDetailDto = new PgBankParameterDetailDto();

    private List<PgBankParameterDetailDto> pgBankDetailDtoList = new ArrayList<PgBankParameterDetailDto>();

    @Override
    public boolean saveForm() {
        boolean succesFlag = false;
        if (pgBankMasterDto.getPgId() == 0) {
            pgBankMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            pgBankMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            pgBankMasterDto.setLgIpMac(getClientIpAddress());
            pgBankMasterDto.setLmodDate(new Date());
            pgBankMasterDto.setPgStatus(MainetConstants.FlagA);
            pgBankMasterDto.setLangId(UserSession.getCurrent().getLanguageId());

        } else {
            pgBankMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee());
            pgBankMasterDto.setUpdatedDate(new Date());
            pgBankMasterDto.setLgIpMacUpd(getClientIpAddress());
        }
        PGBankDetail pgBankMas = new PGBankDetail();
        BeanUtils.copyProperties(pgBankMasterDto, pgBankMas);
        List<PGBankParameter> pgBankDetailsList = new ArrayList<PGBankParameter>();
        pgBankDetailDtoList.forEach(bankDetailDto -> {
            PGBankParameter bankDetailEntity = new PGBankParameter();
            if (bankDetailDto.getPgPramDetId() == null || bankDetailDto.getPgPramDetId().equals(0)) {
                bankDetailDto.setOrgId(UserSession.getCurrent().getOrganisation());
                bankDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                bankDetailDto.setLmodDate(new Date());
                bankDetailDto.setLgIpMac(getClientIpAddress());
                bankDetailDto.setParStatus(MainetConstants.FlagA);
                bankDetailDto.setLangId(UserSession.getCurrent().getLanguageId());
                BeanUtils.copyProperties(bankDetailDto, bankDetailEntity);
                // bankDetailEntity.setPgBankDetail(pgBankMas);
                pgBankDetailsList.add(bankDetailEntity);
            }
        });

        // pgBankMas.setPgBankParameterList(pgBankDetailsList);
        pgBankParameterService.savePgBankParameterDetails(pgBankMas);
        setSuccessMessage(ApplicationSession.getInstance().getMessage("Pg param details saved successfully"));
        succesFlag = true;
        return succesFlag;

    }

    public PgBankParameterMasDto getPgBankMasterDto() {
        return pgBankMasterDto;
    }

    public void setPgBankMasterDto(PgBankParameterMasDto pgBankMasterDto) {
        this.pgBankMasterDto = pgBankMasterDto;
    }

    public PgBankParameterDetailDto getPgBankDetailDto() {
        return pgBankDetailDto;
    }

    public void setPgBankDetailDto(PgBankParameterDetailDto pgBankDetailDto) {
        this.pgBankDetailDto = pgBankDetailDto;
    }

    public List<PgBankParameterDetailDto> getPgBankDetailDtoList() {
        return pgBankDetailDtoList;
    }

    public void setPgBankDetailDtoList(List<PgBankParameterDetailDto> pgBankDetailDtoList) {
        this.pgBankDetailDtoList = pgBankDetailDtoList;
    }

}
