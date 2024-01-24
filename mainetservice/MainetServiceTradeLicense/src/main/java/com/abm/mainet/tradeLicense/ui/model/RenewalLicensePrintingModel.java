package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@Component
@Scope("session")
public class RenewalLicensePrintingModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
    private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
    private String issuanceDateDesc;
    private String viewMode;
    private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
    private String fromDateDesc;
    private String toDateDesc;
    private String finYear;
    private List<CFCAttachment> documentList = new ArrayList<>();
    private String imagePath;

    public TradeMasterDetailDTO getTradeDetailDTO() {
        return tradeDetailDTO;
    }

    public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
        this.tradeDetailDTO = tradeDetailDTO;
    }

    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public List<CFCAttachment> getFetchDocumentList() {
        return fetchDocumentList;
    }

    public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
        this.fetchDocumentList = fetchDocumentList;
    }

    public String getIssuanceDateDesc() {
        return issuanceDateDesc;
    }

    public void setIssuanceDateDesc(String issuanceDateDesc) {
        this.issuanceDateDesc = issuanceDateDesc;
    }

    public String getFromDateDesc() {
        return fromDateDesc;
    }

    public String getToDateDesc() {
        return toDateDesc;
    }

    public void setFromDateDesc(String fromDateDesc) {
        this.fromDateDesc = fromDateDesc;
    }

    public void setToDateDesc(String toDateDesc) {
        this.toDateDesc = toDateDesc;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
