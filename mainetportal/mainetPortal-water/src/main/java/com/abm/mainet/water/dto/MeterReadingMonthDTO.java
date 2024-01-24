
package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rahul.Yadav
 *
 */
public class MeterReadingMonthDTO implements Serializable {

    private static final long serialVersionUID = 1188357691428925693L;

    private int from;
    private int to;
    private String monthDesc;
    private String valueCheck;
    private String cssProperty;
    private Date billFromdate;
    private Date billTodate;
    public int getFrom() {
        return from;
    }

    public void setFrom(final int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(final int to) {
        this.to = to;
    }

    public String getMonthDesc() {
        return monthDesc;
    }

    public void setMonthDesc(final String monthDesc) {
        this.monthDesc = monthDesc;
    }

    public String getValueCheck() {
        return valueCheck;
    }

    public void setValueCheck(final String valueCheck) {
        this.valueCheck = valueCheck;
    }

	public String getCssProperty() {
		return cssProperty;
	}

	public void setCssProperty(String cssProperty) {
		this.cssProperty = cssProperty;
	}

	public Date getBillFromdate() {
		return billFromdate;
	}

	public void setBillFromdate(Date billFromdate) {
		this.billFromdate = billFromdate;
	}

	public Date getBillTodate() {
		return billTodate;
	}

	public void setBillTodate(Date billTodate) {
		this.billTodate = billTodate;
	}
    
    
}
