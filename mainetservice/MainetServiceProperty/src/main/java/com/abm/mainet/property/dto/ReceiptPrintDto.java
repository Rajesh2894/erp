package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReceiptPrintDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3869816985364936335L;
    private String receiptNoL;
    private String receiptNoV;
    private String receiptDateL;
    private String receiptDateV;
    private String financialYearL;
    private String financialYearV;
    private String departmentL;
    private String departmentV;
    private String propertyNoL;
    private String propertyNoV;
    private String counterRefL;
    private String counterRefV;
    private String modeL;
    private String modeV;;
    private String receivedFromL;
    private String receivedFromV;
    private String subjectL;
    private String subjectV;
    private String addressL;
    private String addressV;
    private String zoneL;
    private String zoneV;
    private String ward1L;
    private String ward1V;
    private String ward2L;
    private String ward2V;
    private String ward3L;
    private String ward3V;
    private String ward4L;
    private String ward4V;
    private String ward5L;
    private String ward5V;
    private String blockL;
    private String blockV;
    private String routeL;
    private String routeV;
    private String appNoL;
    private String appNoV;
    private String loiNoL;
    private String loiNoV;
    private String appDateL;
    private String appDateV;
    private String paymentModeL;
    private String paymentModeV;
    private String amountL;
    private String amountV;
    private String chequeNoL;
    private String chequeNoV;
    private String chequeDateL;
    private String chequeDateV;
    private String bankNameL;
    private String bankNameV;
    private String detailsL;
    private String paymentAmtL;
    private String totalAmtL;
    private String totalPayAmtV;;
    private String totalReceivedAmtV;
    private String amtInWordsL;
    private String amtInWordsV;
    private String receiverSignatureL;
    private String receiverSignatureV;
    private String noteL;
    private String noteV;;
    private String ulbNameL;
    private String receipt;
    private String actL;
    private String gstNoL;
    private String receivedAmtL;
    private String finalNoteL;
    private String logo;

    private List<ReceiptPrintDetailDto> detailList = new ArrayList<>();

    public String getReceiptNoL() {
        return receiptNoL;
    }

    public void setReceiptNoL(String receiptNoL) {
        this.receiptNoL = receiptNoL;
    }

    public String getReceiptNoV() {
        return receiptNoV;
    }

    public void setReceiptNoV(String receiptNoV) {
        this.receiptNoV = receiptNoV;
    }

    public String getReceiptDateL() {
        return receiptDateL;
    }

    public void setReceiptDateL(String receiptDateL) {
        this.receiptDateL = receiptDateL;
    }

    public String getReceiptDateV() {
        return receiptDateV;
    }

    public void setReceiptDateV(String receiptDateV) {
        this.receiptDateV = receiptDateV;
    }

    public String getFinancialYearL() {
        return financialYearL;
    }

    public void setFinancialYearL(String financialYearL) {
        this.financialYearL = financialYearL;
    }

    public String getFinancialYearV() {
        return financialYearV;
    }

    public void setFinancialYearV(String financialYearV) {
        this.financialYearV = financialYearV;
    }

    public String getDepartmentL() {
        return departmentL;
    }

    public void setDepartmentL(String departmentL) {
        this.departmentL = departmentL;
    }

    public String getDepartmentV() {
        return departmentV;
    }

    public void setDepartmentV(String departmentV) {
        this.departmentV = departmentV;
    }

    public String getPropertyNoL() {
        return propertyNoL;
    }

    public void setPropertyNoL(String propertyNoL) {
        this.propertyNoL = propertyNoL;
    }

    public String getPropertyNoV() {
        return propertyNoV;
    }

    public void setPropertyNoV(String propertyNoV) {
        this.propertyNoV = propertyNoV;
    }

    public String getCounterRefL() {
        return counterRefL;
    }

    public void setCounterRefL(String counterRefL) {
        this.counterRefL = counterRefL;
    }

    public String getCounterRefV() {
        return counterRefV;
    }

    public void setCounterRefV(String counterRefV) {
        this.counterRefV = counterRefV;
    }

    public String getModeL() {
        return modeL;
    }

    public void setModeL(String modeL) {
        this.modeL = modeL;
    }

    public String getModeV() {
        return modeV;
    }

    public void setModeV(String modeV) {
        this.modeV = modeV;
    }

    public String getReceivedFromL() {
        return receivedFromL;
    }

    public void setReceivedFromL(String receivedFromL) {
        this.receivedFromL = receivedFromL;
    }

    public String getReceivedFromV() {
        return receivedFromV;
    }

    public void setReceivedFromV(String receivedFromV) {
        this.receivedFromV = receivedFromV;
    }

    public String getSubjectL() {
        return subjectL;
    }

    public void setSubjectL(String subjectL) {
        this.subjectL = subjectL;
    }

    public String getSubjectV() {
        return subjectV;
    }

    public void setSubjectV(String subjectV) {
        this.subjectV = subjectV;
    }

    public String getAddressL() {
        return addressL;
    }

    public void setAddressL(String addressL) {
        this.addressL = addressL;
    }

    public String getAddressV() {
        return addressV;
    }

    public void setAddressV(String addressV) {
        this.addressV = addressV;
    }

    public String getZoneL() {
        return zoneL;
    }

    public void setZoneL(String zoneL) {
        this.zoneL = zoneL;
    }

    public String getZoneV() {
        return zoneV;
    }

    public void setZoneV(String zoneV) {
        this.zoneV = zoneV;
    }

    public String getWard1L() {
        return ward1L;
    }

    public void setWard1L(String ward1l) {
        ward1L = ward1l;
    }

    public String getWard1V() {
        return ward1V;
    }

    public void setWard1V(String ward1v) {
        ward1V = ward1v;
    }

    public String getWard2L() {
        return ward2L;
    }

    public void setWard2L(String ward2l) {
        ward2L = ward2l;
    }

    public String getWard2V() {
        return ward2V;
    }

    public void setWard2V(String ward2v) {
        ward2V = ward2v;
    }

    public String getWard3L() {
        return ward3L;
    }

    public void setWard3L(String ward3l) {
        ward3L = ward3l;
    }

    public String getWard3V() {
        return ward3V;
    }

    public void setWard3V(String ward3v) {
        ward3V = ward3v;
    }

    public String getWard4L() {
        return ward4L;
    }

    public void setWard4L(String ward4l) {
        ward4L = ward4l;
    }

    public String getWard4V() {
        return ward4V;
    }

    public void setWard4V(String ward4v) {
        ward4V = ward4v;
    }

    public String getWard5L() {
        return ward5L;
    }

    public void setWard5L(String ward5l) {
        ward5L = ward5l;
    }

    public String getWard5V() {
        return ward5V;
    }

    public void setWard5V(String ward5v) {
        ward5V = ward5v;
    }

    public String getBlockL() {
        return blockL;
    }

    public void setBlockL(String blockL) {
        this.blockL = blockL;
    }

    public String getBlockV() {
        return blockV;
    }

    public void setBlockV(String blockV) {
        this.blockV = blockV;
    }

    public String getRouteL() {
        return routeL;
    }

    public void setRouteL(String routeL) {
        this.routeL = routeL;
    }

    public String getRouteV() {
        return routeV;
    }

    public void setRouteV(String routeV) {
        this.routeV = routeV;
    }

    public String getAppNoL() {
        return appNoL;
    }

    public void setAppNoL(String appNoL) {
        this.appNoL = appNoL;
    }

    public String getAppNoV() {
        return appNoV;
    }

    public void setAppNoV(String appNoV) {
        this.appNoV = appNoV;
    }

    public String getLoiNoL() {
        return loiNoL;
    }

    public void setLoiNoL(String loiNoL) {
        this.loiNoL = loiNoL;
    }

    public String getLoiNoV() {
        return loiNoV;
    }

    public void setLoiNoV(String loiNoV) {
        this.loiNoV = loiNoV;
    }

    public String getAppDateL() {
        return appDateL;
    }

    public void setAppDateL(String appDateL) {
        this.appDateL = appDateL;
    }

    public String getAppDateV() {
        return appDateV;
    }

    public void setAppDateV(String appDateV) {
        this.appDateV = appDateV;
    }

    public String getPaymentModeL() {
        return paymentModeL;
    }

    public void setPaymentModeL(String paymentModeL) {
        this.paymentModeL = paymentModeL;
    }

    public String getPaymentModeV() {
        return paymentModeV;
    }

    public void setPaymentModeV(String paymentModeV) {
        this.paymentModeV = paymentModeV;
    }

    public String getAmountL() {
        return amountL;
    }

    public void setAmountL(String amountL) {
        this.amountL = amountL;
    }

    public String getAmountV() {
        return amountV;
    }

    public void setAmountV(String amountV) {
        this.amountV = amountV;
    }

    public String getChequeNoL() {
        return chequeNoL;
    }

    public void setChequeNoL(String chequeNoL) {
        this.chequeNoL = chequeNoL;
    }

    public String getChequeNoV() {
        return chequeNoV;
    }

    public void setChequeNoV(String chequeNoV) {
        this.chequeNoV = chequeNoV;
    }

    public String getChequeDateL() {
        return chequeDateL;
    }

    public void setChequeDateL(String chequeDateL) {
        this.chequeDateL = chequeDateL;
    }

    public String getChequeDateV() {
        return chequeDateV;
    }

    public void setChequeDateV(String chequeDateV) {
        this.chequeDateV = chequeDateV;
    }

    public String getBankNameL() {
        return bankNameL;
    }

    public void setBankNameL(String bankNameL) {
        this.bankNameL = bankNameL;
    }

    public String getBankNameV() {
        return bankNameV;
    }

    public void setBankNameV(String bankNameV) {
        this.bankNameV = bankNameV;
    }

    public String getDetailsL() {
        return detailsL;
    }

    public void setDetailsL(String detailsL) {
        this.detailsL = detailsL;
    }

    public String getPaymentAmtL() {
        return paymentAmtL;
    }

    public void setPaymentAmtL(String paymentAmtL) {
        this.paymentAmtL = paymentAmtL;
    }

    public String getTotalAmtL() {
        return totalAmtL;
    }

    public void setTotalAmtL(String totalAmtL) {
        this.totalAmtL = totalAmtL;
    }

    public String getTotalPayAmtV() {
        return totalPayAmtV;
    }

    public void setTotalPayAmtV(String totalPayAmtV) {
        this.totalPayAmtV = totalPayAmtV;
    }

    public String getTotalReceivedAmtV() {
        return totalReceivedAmtV;
    }

    public void setTotalReceivedAmtV(String totalReceivedAmtV) {
        this.totalReceivedAmtV = totalReceivedAmtV;
    }

    public String getAmtInWordsL() {
        return amtInWordsL;
    }

    public void setAmtInWordsL(String amtInWordsL) {
        this.amtInWordsL = amtInWordsL;
    }

    public String getAmtInWordsV() {
        return amtInWordsV;
    }

    public void setAmtInWordsV(String amtInWordsV) {
        this.amtInWordsV = amtInWordsV;
    }

    public String getReceiverSignatureL() {
        return receiverSignatureL;
    }

    public void setReceiverSignatureL(String receiverSignatureL) {
        this.receiverSignatureL = receiverSignatureL;
    }

    public String getReceiverSignatureV() {
        return receiverSignatureV;
    }

    public void setReceiverSignatureV(String receiverSignatureV) {
        this.receiverSignatureV = receiverSignatureV;
    }

    public String getNoteL() {
        return noteL;
    }

    public void setNoteL(String noteL) {
        this.noteL = noteL;
    }

    public String getNoteV() {
        return noteV;
    }

    public void setNoteV(String noteV) {
        this.noteV = noteV;
    }

    public String getUlbNameL() {
        return ulbNameL;
    }

    public void setUlbNameL(String ulbNameL) {
        this.ulbNameL = ulbNameL;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getActL() {
        return actL;
    }

    public void setActL(String actL) {
        this.actL = actL;
    }

    public String getGstNoL() {
        return gstNoL;
    }

    public void setGstNoL(String gstNoL) {
        this.gstNoL = gstNoL;
    }

    public String getReceivedAmtL() {
        return receivedAmtL;
    }

    public void setReceivedAmtL(String receivedAmtL) {
        this.receivedAmtL = receivedAmtL;
    }

    public String getFinalNoteL() {
        return finalNoteL;
    }

    public void setFinalNoteL(String finalNoteL) {
        this.finalNoteL = finalNoteL;
    }

    public List<ReceiptPrintDetailDto> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ReceiptPrintDetailDto> detailList) {
        this.detailList = detailList;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
