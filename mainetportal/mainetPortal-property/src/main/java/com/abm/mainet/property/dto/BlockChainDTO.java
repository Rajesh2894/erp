package com.abm.mainet.property.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockChainDTO {
    
    private String purpsTrans;    
    private Date actTransDate;
    private BigDecimal marketValue;
    private BigDecimal salesDeedValue;
    private BigDecimal stampDutyCharges;
    private BigDecimal regiCharges;
    private BigDecimal otherCharges;
    private BigDecimal regiOfficeDet;
    private String paymentReceived;

    private String channel;
    private String chaincode;
    private String method;
    private String chaincodeVer;
    private String propNo;
    
    private ArrayList<String> args = new ArrayList<String>();
    
    private List<OwnerDTO> ownerDetails= new ArrayList<>();
    
    private List<WitnessDTO> witnessDetails=  new ArrayList<>();

    public String getPurpsTrans() {
        return purpsTrans;
    }

    public void setPurpsTrans(String purpsTrans) {
        this.purpsTrans = purpsTrans;
    }

    public Date getActTransDate() {
        return actTransDate;
    }

    public void setActTransDate(Date actTransDate) {
        this.actTransDate = actTransDate;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getSalesDeedValue() {
        return salesDeedValue;
    }

    public void setSalesDeedValue(BigDecimal salesDeedValue) {
        this.salesDeedValue = salesDeedValue;
    }

    public BigDecimal getStampDutyCharges() {
        return stampDutyCharges;
    }

    public void setStampDutyCharges(BigDecimal stampDutyCharges) {
        this.stampDutyCharges = stampDutyCharges;
    }

    public BigDecimal getRegiCharges() {
        return regiCharges;
    }

    public void setRegiCharges(BigDecimal regiCharges) {
        this.regiCharges = regiCharges;
    }

    public BigDecimal getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(BigDecimal otherCharges) {
        this.otherCharges = otherCharges;
    }

    public BigDecimal getRegiOfficeDet() {
        return regiOfficeDet;
    }

    public void setRegiOfficeDet(BigDecimal regiOfficeDet) {
        this.regiOfficeDet = regiOfficeDet;
    }

    public String getPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(String paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public List<OwnerDTO> getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(List<OwnerDTO> ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public List<WitnessDTO> getWitnessDetails() {
        return witnessDetails;
    }

    public void setWitnessDetails(List<WitnessDTO> witnessDetails) {
        this.witnessDetails = witnessDetails;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChaincode() {
        return chaincode;
    }

    public void setChaincode(String chaincode) {
        this.chaincode = chaincode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChaincodeVer() {
        return chaincodeVer;
    }

    public void setChaincodeVer(String chaincodeVer) {
        this.chaincodeVer = chaincodeVer;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }
    
    

}
