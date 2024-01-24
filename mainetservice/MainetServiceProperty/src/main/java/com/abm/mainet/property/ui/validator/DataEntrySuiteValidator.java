package com.abm.mainet.property.ui.validator;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;
import com.google.common.util.concurrent.AtomicDouble;

@Component
public class DataEntrySuiteValidator extends BaseEntityValidator<NewPropertyRegistrationModel> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Override
    protected void performValidations(NewPropertyRegistrationModel entity,
            EntityValidationContext<NewPropertyRegistrationModel> entityValidationContext) {
        List<TbBillMas> billMasList = entity.getBillMasList();
        if ((billMasList != null) && !billMasList.isEmpty()) {
            Long curFinId = iFinancialYearService.getFinanceYearId(new Date());
            Long curSelFinId = entity.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
            for (final TbBillMas billMas : billMasList) {
                AtomicDouble totAmt = new AtomicDouble(0);
                totAmt.getAndSet(0);
                billMas.getTbWtBillDet().forEach(det -> {
                    if (det.getBdCsmp() != null) {
                        totAmt.addAndGet(det.getBdCsmp().doubleValue());
                    }
                });
                if ((!curFinId.equals(billMas.getBmYear()) || curFinId.equals(curSelFinId)) && totAmt.doubleValue() == 0) {
                    FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billMas.getBmYear(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    String year = Utility.getFinancialYear(finYear.getFaFromDate(), finYear.getFaToDate());
                    entityValidationContext.addOptionConstraint(session.getMessage("dataEntry.year.valdation") + " " + year);
                    break;
                }
            }
        }

    }
}
