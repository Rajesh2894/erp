package com.abm.mainet.water.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.BillDistributionService;

@Component
@Scope("session")
public class BillDistributionEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = 4711741266642561048L;

    @Autowired
    private BillDistributionService billDistributionService;

    private List<TbBillMas> billMas = null;

    private WardZoneBlockDTO entity = null;

    private String connectionNo;

    private Long distriutionType;

    private String billCcnType;

    private boolean validatePrefix;

    @Override
    protected void initializeModel() {

        List<LookUp> billingMethod = null;
        billingMethod = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.DDM,
                UserSession.getCurrent().getOrganisation());
        LookUp lookUpVal = null;
        if ((billingMethod != null) && !billingMethod.isEmpty()) {
            for (final LookUp lookUp : billingMethod) {
                if (lookUp.getDefaultVal().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
                    lookUpVal = lookUp;
                    break;
                }
            }
        }
        if ((lookUpVal != null)
                && !MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE
                        .equals(lookUpVal.getLookUpCode())) {
            setValidatePrefix(true);
        }
    }

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        if (PrefixConstants.NewWaterServiceConstants.WWZ.equals(parentCode)) {
            return "entity.areaDivision";
        } else {
            return null;
        }
    }

    @Override
    public boolean saveForm() {
        boolean record = false;
        for (final TbBillMas bill : getBillMas()) {
            if (MainetConstants.MENU.Y.equals(bill.getBmEntryFlag())) {
                record = true;
                if (bill.getDistDate() == null) {
                    addValidationError(getAppSession().getMessage("distributio.Date"));
                }
            }
        }
        if (!record) {
            addValidationError(getAppSession().getMessage("distributio.selectRecord"));
        }
        if (hasValidationErrors()) {
            return false;
        }
        billDistributionService.updateBillDueDate(getBillMas(), UserSession.getCurrent().getOrganisation(), getDistriutionType());
        setSuccessMessage(getAppSession().getMessage("distributio.save.success"));
        return true;
    }

    public List<TbBillMas> getBillMas() {
        return billMas;
    }

    public void setBillMas(final List<TbBillMas> billMas) {
        this.billMas = billMas;
    }

    public WardZoneBlockDTO getEntity() {
        return entity;
    }

    public void setEntity(final WardZoneBlockDTO entity) {
        this.entity = entity;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public Long getDistriutionType() {
        return distriutionType;
    }

    public void setDistriutionType(final Long distriutionType) {
        this.distriutionType = distriutionType;
    }

    public String getBillCcnType() {
        return billCcnType;
    }

    public void setBillCcnType(final String billCcnType) {
        this.billCcnType = billCcnType;
    }

    public boolean isValidatePrefix() {
        return validatePrefix;
    }

    public void setValidatePrefix(final boolean validatePrefix) {
        this.validatePrefix = validatePrefix;
    }

}
