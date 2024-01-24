package com.abm.mainet.cfc.loi.ui.model;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class LoiDeletionModel extends AbstractFormModel {

    /**
     *
     */
    private static final long serialVersionUID = 7344621910340278907L;

    @Resource
    private TbLoiMasService tbLoiMasService;

    private String address;

    private LoiPaymentSearchDTO searchData = null;

    private String loiDelRemark;

    @Override
    public boolean saveForm() {

        final ApplicationSession appSession = ApplicationSession.getInstance();
        if ((getSearchData() == null) || (getSearchData().getLoiMasData() == null)) {
            addValidationError(appSession.getMessage("loiDet.error.msg.searchAppNo"));
            return false;
        }
        if ((getLoiDelRemark() == null) || getLoiDelRemark().isEmpty()) {
            addValidationError(appSession.getMessage("loiDet.error.msg.delRemark"));
            return false;
        }
        tbLoiMasService.deleteLoiData(getSearchData(), UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee().getEmpId(), getLoiDelRemark());
        setSuccessMessage(appSession.getMessage("loiDel.msg.loiForAppNo") + getSearchData().getApplicationId()
                + appSession.getMessage("loiDel.msg.delSuccess"));
        return true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public LoiPaymentSearchDTO getSearchData() {
        return searchData;
    }

    public void setSearchData(final LoiPaymentSearchDTO searchData) {
        this.searchData = searchData;
    }

    public String getLoiDelRemark() {
        return loiDelRemark;
    }

    public void setLoiDelRemark(final String loiDelRemark) {
        this.loiDelRemark = loiDelRemark;
    }

}
