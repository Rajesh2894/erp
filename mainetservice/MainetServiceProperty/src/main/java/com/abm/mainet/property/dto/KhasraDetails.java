package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "KhasraDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhasraDetails {

    @XmlElement
    private String VillageCode;
    @XmlElement
    private String Khasra_No;
    @XmlElement
    private String Area;
    @XmlElement
    private String NakshaURL;
    @XmlElement
    private String MortageFlag;
    @XmlElement
    private String BankName;
    @XmlElement
    private String OwnerType;
    @XmlElement
    private String SignedB1PdfURL;
    @XmlElement
    private String SignedP2PdfURL;
    @XmlElement
    private String PendingDeeds;
    @XmlElement
    private String RemainingAreaForRegistration;
    @XmlElement
    private String IrrigatedArea;
    @XmlElement
    private String UnirrigatedArea;
    @XmlElement
    private String IrrigatedSource;
    @XmlElement
    private String Kafiyat;
    @XmlElement
    private String ErrorMessage;
    /*
     * @XmlElement private List<OwnerDetails> ownerdetails = new ArrayList<>(0);
     */

    @XmlElement
    private List<OwnerDetailsList> ownerdetails = new ArrayList<>(0);

    /*
     * @XmlElement private List<Revcase> Revcase = new ArrayList<>(0);
     */

    @XmlElement
    private List<RevCaseDet> RevCaseDet = new ArrayList<>(0);

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
    }

    public String getKhasra_No() {
        return Khasra_No;
    }

    public void setKhasra_No(String khasra_No) {
        Khasra_No = khasra_No;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getNakshaURL() {
        return NakshaURL;
    }

    public void setNakshaURL(String nakshaURL) {
        NakshaURL = nakshaURL;
    }

    public String getMortageFlag() {
        return MortageFlag;
    }

    public void setMortageFlag(String mortageFlag) {
        MortageFlag = mortageFlag;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getOwnerType() {
        return OwnerType;
    }

    public void setOwnerType(String ownerType) {
        OwnerType = ownerType;
    }

    public String getSignedB1PdfURL() {
        return SignedB1PdfURL;
    }

    public void setSignedB1PdfURL(String signedB1PdfURL) {
        SignedB1PdfURL = signedB1PdfURL;
    }

    public String getSignedP2PdfURL() {
        return SignedP2PdfURL;
    }

    public void setSignedP2PdfURL(String signedP2PdfURL) {
        SignedP2PdfURL = signedP2PdfURL;
    }

    public String getPendingDeeds() {
        return PendingDeeds;
    }

    public void setPendingDeeds(String pendingDeeds) {
        PendingDeeds = pendingDeeds;
    }

    public String getRemainingAreaForRegistration() {
        return RemainingAreaForRegistration;
    }

    public void setRemainingAreaForRegistration(String remainingAreaForRegistration) {
        RemainingAreaForRegistration = remainingAreaForRegistration;
    }

    public String getIrrigatedArea() {
        return IrrigatedArea;
    }

    public void setIrrigatedArea(String irrigatedArea) {
        IrrigatedArea = irrigatedArea;
    }

    public String getUnirrigatedArea() {
        return UnirrigatedArea;
    }

    public void setUnirrigatedArea(String unirrigatedArea) {
        UnirrigatedArea = unirrigatedArea;
    }

    public String getIrrigatedSource() {
        return IrrigatedSource;
    }

    public void setIrrigatedSource(String irrigatedSource) {
        IrrigatedSource = irrigatedSource;
    }

    public String getKafiyat() {
        return Kafiyat;
    }

    public void setKafiyat(String kafiyat) {
        Kafiyat = kafiyat;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    /*
     * public List<OwnerDetails> getOwnerdetails() { return ownerdetails; } public void setOwnerdetails(List<OwnerDetails>
     * ownerdetails) { this.ownerdetails = ownerdetails; }
     */

    /*
     * public List<Revcase> getRevcase() { return Revcase; }
     */

    public List<OwnerDetailsList> getOwnerdetails() {
        return ownerdetails;
    }

    public void setOwnerdetails(List<OwnerDetailsList> ownerdetails) {
        this.ownerdetails = ownerdetails;
    }

    /*
     * public void setRevcase(List<Revcase> revcase) { Revcase = revcase; }
     */

}
