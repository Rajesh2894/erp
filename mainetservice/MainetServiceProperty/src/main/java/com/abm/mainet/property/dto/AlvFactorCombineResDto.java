package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.util.concurrent.AtomicDouble;

public class AlvFactorCombineResDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1715663448537712152L;
    private AtomicDouble arv = new AtomicDouble(0);
    private AtomicDouble rv = new AtomicDouble(0);
    private AtomicDouble cv = new AtomicDouble(0);
    private AtomicDouble taxableArea = new AtomicDouble(0);
    private AtomicDouble multiplicationFactorTax = new AtomicDouble(1);
    private Map<String, String> factor = new HashMap<>(0);
    private AtomicDouble residentialRv = new AtomicDouble(0);

    public AtomicDouble getArv() {
        return arv;
    }

    public void setArv(AtomicDouble arv) {
        this.arv = arv;
    }

    public AtomicDouble getRv() {
        return rv;
    }

    public void setRv(AtomicDouble rv) {
        this.rv = rv;
    }

    public AtomicDouble getCv() {
        return cv;
    }

    public void setCv(AtomicDouble cv) {
        this.cv = cv;
    }

    public AtomicDouble getTaxableArea() {
        return taxableArea;
    }

    public void setTaxableArea(AtomicDouble taxableArea) {
        this.taxableArea = taxableArea;
    }

    public Map<String, String> getFactor() {
        return factor;
    }

    public void setFactor(Map<String, String> factor) {
        this.factor = factor;
    }

    public AtomicDouble getMultiplicationFactorTax() {
        return multiplicationFactorTax;
    }

    public void setMultiplicationFactorTax(AtomicDouble multiplicationFactorTax) {
        this.multiplicationFactorTax = multiplicationFactorTax;
    }

	public AtomicDouble getResidentialRv() {
		return residentialRv;
	}

	public void setResidentialRv(AtomicDouble residentialRv) {
		this.residentialRv = residentialRv;
	}

    
}
