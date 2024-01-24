package com.abm.mainet.rnl.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.rnl.dto.DemandNoticeDTO;
import com.abm.mainet.rnl.dto.ReportDTO;

@Component
@Scope("session")
public class DemandNoticeModel extends AbstractFormModel {

    private static final long serialVersionUID = 1557587963861427303L;

    private ReportDTO reportDTO = null;;

    private List<ReportDTO> reportDTOList = null;

    private List<LookUp> contractLookupList = null;

    private List<LookUp> venderlookupList = null;

    private List<DemandNoticeDTO> demandNoticeDTOList = null;

    private String reportLang = "ENG";

    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public void setReportDTO(ReportDTO reportDTO) {
        this.reportDTO = reportDTO;
    }

    public List<ReportDTO> getReportDTOList() {
        return reportDTOList;
    }

    public void setReportDTOList(List<ReportDTO> reportDTOList) {
        this.reportDTOList = reportDTOList;
    }

    public List<LookUp> getContractLookupList() {
        return contractLookupList;
    }

    public void setContractLookupList(List<LookUp> contractLookupList) {
        this.contractLookupList = contractLookupList;
    }

    public List<LookUp> getVenderlookupList() {
        return venderlookupList;
    }

    public void setVenderlookupList(List<LookUp> venderlookupList) {
        this.venderlookupList = venderlookupList;
    }

    public List<DemandNoticeDTO> getDemandNoticeDTOList() {
        return demandNoticeDTOList;
    }

    public void setDemandNoticeDTOList(List<DemandNoticeDTO> demandNoticeDTOList) {
        this.demandNoticeDTOList = demandNoticeDTOList;
    }

    public String getReportLang() {
        return reportLang;
    }

    public void setReportLang(String reportLang) {
        this.reportLang = reportLang;
    }

}
