package com.abm.mainet.swm.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class FineChargesCollectionReportModel extends AbstractFormModel {

    private static final long serialVersionUID = -5066569338978011662L;

    List<TbOrganisation> listOfUlb;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public List<TbOrganisation> getListOfUlb() {
        return listOfUlb;
    }

    public void setListOfUlb(List<TbOrganisation> listOfUlb) {
        this.listOfUlb = listOfUlb;
    }

}
