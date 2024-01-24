package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.BillingScheduleDto;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.ui.validator.BillingScheduleValidator;

@Component
@Scope("session")
public class BillingScheduleModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private BillingScheduleDto billScheduleDto;
    private String modeType;
    private Map<Long, String> financialYearMap = new LinkedHashMap<>();

    @Autowired
    private BillingScheduleService billScheduleService;
    
    private List<BillingScheduleDto> billSchDtoList=new LinkedList<>();

    @Override
    public boolean saveForm() {
        ApplicationSession appSession = ApplicationSession.getInstance();
        BillingScheduleDto dto = this.getBillScheduleDto();
        validateBean(dto, BillingScheduleValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        if (this.getModeType().equals(MainetConstants.WORKFLOWTYPE.MODE_CREATE)) {
            List<Long> finYearIdList = new ArrayList<>();
            String fyears = dto.getTbFinancialyears();
            if (!fyears.isEmpty()) {
                String array[] = fyears.split(",");
                for (String fYearid : array) {
                    finYearIdList.add(Long.valueOf(fYearid));
                }
            }
            billScheduleService.saveBillingSchedule(dto,this.getBillSchDtoList(),finYearIdList,UserSession.getCurrent().getOrganisation());
            setSuccessMessage(appSession.getMessage("billschedule.create.success"));
        } else {
            billScheduleService.updateBillingSchedule(dto,this.getBillSchDtoList());
            setSuccessMessage(appSession.getMessage("billschedule.update.success"));
        }
        return true;
    }

    public BillingScheduleDto getBillScheduleDto() {
        return billScheduleDto;
    }

    public void setBillScheduleDto(BillingScheduleDto billScheduleDto) {
        this.billScheduleDto = billScheduleDto;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public void setFinancialYearMap(Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

	public List<BillingScheduleDto> getBillSchDtoList() {
		return billSchDtoList;
	}

	public void setBillSchDtoList(List<BillingScheduleDto> billSchDtoList) {
		this.billSchDtoList = billSchDtoList;
	}

}