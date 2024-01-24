package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TradeLicenseItemDetailDTOS implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String itemCategory1; 
    private String itemCategory2;
    private String itemCategory3;  
    private String itemCategory4;
    private String itemCategory5;
  
    private BigDecimal trdQuantity;  
    private BigDecimal trdUnit;    

    
    public String getItemCategory1() {
        return itemCategory1;
    }

    public void setItemCategory1(String itemCategory1) {
        this.itemCategory1 = itemCategory1;
    }

    public String getItemCategory2() {
        return itemCategory2;
    }

    public void setItemCategory2(String itemCategory2) {
        this.itemCategory2 = itemCategory2;
    }

    public String getItemCategory3() {
        return itemCategory3;
    }

    public void setItemCategory3(String itemCategory3) {
        this.itemCategory3 = itemCategory3;
    }

    public String getItemCategory4() {
        return itemCategory4;
    }

    public void setItemCategory4(String itemCategory4) {
        this.itemCategory4 = itemCategory4;
    }

    public String getItemCategory5() {
        return itemCategory5;
    }

    public void setItemCategory5(String itemCategory5) {
        this.itemCategory5 = itemCategory5;
    }

    public BigDecimal getTrdQuantity() {
        return trdQuantity;
    }

    public BigDecimal getTrdUnit() {
        return trdUnit;
    }

    public void setTrdQuantity(BigDecimal trdQuantity) {
        this.trdQuantity = trdQuantity;
    }

    public void setTrdUnit(BigDecimal bigDecimal) {
        this.trdUnit = bigDecimal;
    }

}
