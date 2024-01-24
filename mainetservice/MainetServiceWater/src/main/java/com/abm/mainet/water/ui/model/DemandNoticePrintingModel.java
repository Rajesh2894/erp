package com.abm.mainet.water.ui.model;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;
import com.abm.mainet.water.service.DemandNoticeGenarationService;

@Component
@Scope("session")
public class DemandNoticePrintingModel extends
        AbstractFormModel {

    private static final long serialVersionUID = -3307269739839482467L;

    @Resource
    private DemandNoticeGenarationService demandNoticeGenarationService;

    @Autowired
    private TbDepartmentService departmentService;

    private DemandNoticeRequestDTO dto;
    private String orgName;
    private String deptName;
    private String demandDays;
    private String finalDemandDays;
    private Long demandType;
    private Long finalDemandType;
    private List<DemandNoticeResponseDTO> response;

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            return "dto.trf";

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "dto.wwz";

        case PrefixConstants.WATERMODULEPREFIX.CSZ:
            return "dto.csz";

        case PrefixConstants.WATERMODULEPREFIX.WDN:
            return "dto.wdn";

        case PrefixConstants.NewWaterServiceConstants.WMN:
            return "dto.wmn";

        default:
            return null;

        }
    }

    public DemandNoticeRequestDTO getDto() {
        return dto;
    }

    public String getOrgName() {
        return orgName;
    }

    @Override
    protected void initializeModel() {

        initializeLookupFields(PrefixConstants.NewWaterServiceConstants.TRF);
        initializeLookupFields(PrefixConstants.NewWaterServiceConstants.WWZ);
        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.CSZ);
        initializeLookupFields(PrefixConstants.WATERMODULEPREFIX.WDN);
        initializeLookupFields(PrefixConstants.NewWaterServiceConstants.WMN);
    }

    @Override
    public boolean saveForm() {
        int selectCount = 0;
        for (final DemandNoticeResponseDTO demandNotice : response) {
            if (demandNotice.isSelected()) {
                selectCount++;

            }
        }

        if (selectCount == 0) {
            addValidationError(getAppSession().getMessage(
                    "demand.select"));
            return false;
        }
        setSuccessMessage(getAppSession().getMessage(
                "demand.print"));
        return true;
    }

    /**
     * @return
     */
    public boolean search() {
        response = null;
        dto.setOrgid(UserSession.getCurrent()
                .getOrganisation().getOrgid());
        if (dto.getWdn() <= 0) {
            addValidationError(getAppSession().getMessage("notice.type"));
            return false;
        } else {
            response = demandNoticeGenarationService
                    .searchAllDemand(dto);
            if ((response == null) || response.isEmpty()) {
                addValidationError(getAppSession().getMessage(
                        "wwater.Norecord"));
                return false;
            }
            return true;
        }
    }

    public void setDto(final DemandNoticeRequestDTO dto) {
        this.dto = dto;
    }

    public List<DemandNoticeResponseDTO> getResponse() {
        return response;
    }

    public void setResponse(
            final List<DemandNoticeResponseDTO> response) {
        this.response = response;
    }

    public void setCommonData() {

        final int languageId = UserSession.getCurrent()
                .getLanguageId();
        switch (languageId) {
        case 1:
            orgName = UserSession.getCurrent()
                    .getOrganisation().getONlsOrgname();
            break;
        case 2:
            orgName = UserSession.getCurrent()
                    .getOrganisation().getONlsOrgnameMar();
            break;
        default:
            orgName = UserSession.getCurrent()
                    .getOrganisation().getONlsOrgname();
            break;
        }
        final long langId = languageId;
        final LookUp demand = demandNoticeGenarationService
                .getDemandType(MainetConstants.DemandNotice.DEMAND_NOTICE, UserSession.getCurrent()
                        .getOrganisation());
        final LookUp finaldemand = demandNoticeGenarationService
                .getDemandType(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, UserSession.getCurrent()
                        .getOrganisation());
        deptName = departmentService
                .getDepartmentNameByDeptCode(MainetConstants.WATER_DEPT, langId);
        demandDays = demand.getOtherField();
        finalDemandDays = finaldemand.getOtherField();
        demandType = demand.getLookUpId();
        finalDemandType = finaldemand.getLookUpId();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }

    public String getDemandDays() {
        return demandDays;
    }

    public void setDemandDays(final String demandDays) {
        this.demandDays = demandDays;
    }

    public String getFinalDemandDays() {
        return finalDemandDays;
    }

    public void setFinalDemandDays(final String finalDemandDays) {
        this.finalDemandDays = finalDemandDays;
    }

    public Long getDemandType() {
        return demandType;
    }

    public void setDemandType(final Long demandType) {
        this.demandType = demandType;
    }

    public Long getFinalDemandType() {
        return finalDemandType;
    }

    public void setFinalDemandType(final Long finalDemandType) {
        this.finalDemandType = finalDemandType;
    }

}
