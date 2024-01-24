package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.BookReceiptDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component

@Scope(value = "session")
public class BookReceiptModel extends AbstractFormModel {

    private static final long serialVersionUID = 8789330432360806481L;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private TbFinancialyearService tbFinancialyearService;

    private BookReceiptDTO bookReceiptDTO = new BookReceiptDTO();

    private Boolean loadAllRecords;

    private List<BookReceiptDTO> receiptBookDatalist = new ArrayList<>();

    private List<LookUp> employeeList = new ArrayList<>(0);

    private List<LookUp> fiancialYearList = new ArrayList<>(0);

    @Override
    protected void initializeModel() {
        loadAllRecords = true;
        iEmployeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()).forEach(employee -> {
            LookUp lookUp = new LookUp();
            lookUp.setLookUpId(employee.getEmpId().longValue());
            lookUp.setLookUpType(employee.getEmpname().concat(MainetConstants.WHITE_SPACE).concat(employee.getEmplname()));
            employeeList.add(lookUp);
        });

        tbFinancialyearService.findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation()).forEach(financialYear -> {
            LookUp lookUp = new LookUp();
            lookUp.setLookUpId(financialYear.getFaYear());
            lookUp.setLookUpType(String.valueOf(Utility.getYearByDate(financialYear.getFaFromDate()))
                    .concat(MainetConstants.HYPHEN).concat(String.valueOf(Utility.getYearByDate(financialYear.getFaToDate()))));
            fiancialYearList.add(lookUp);

        });
    }

    public List<BookReceiptDTO> getReceiptBookDatalist() {
        return receiptBookDatalist;
    }

    public void setReceiptBookDatalist(List<BookReceiptDTO> receiptBookDatalist) {
        this.receiptBookDatalist = receiptBookDatalist;
    }

    public Boolean getLoadAllRecords() {
        return loadAllRecords;
    }

    public void setLoadAllRecords(Boolean loadAllRecords) {
        this.loadAllRecords = loadAllRecords;
    }

    public BookReceiptDTO getBookReceiptDTO() {
        return bookReceiptDTO;
    }

    public void setBookReceiptDTO(BookReceiptDTO bookReceiptDTO) {
        this.bookReceiptDTO = bookReceiptDTO;
    }

    public List<LookUp> getFiancialYearList() {
        return fiancialYearList;
    }

    public void setFiancialYearList(List<LookUp> fiancialYearList) {
        this.fiancialYearList = fiancialYearList;
    }

    public List<LookUp> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<LookUp> employeeList) {
        this.employeeList = employeeList;
    }

}
