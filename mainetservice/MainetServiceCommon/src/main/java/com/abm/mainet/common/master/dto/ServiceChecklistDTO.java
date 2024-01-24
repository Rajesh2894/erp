/**
 *
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;

/**
 * @author nirmal.mahanta
 *
 */
public class ServiceChecklistDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5192119353192687716L;

    private Long smServiceId;
    private String smServiceName;
    private String smServiceNameMar;
    private String docGroup;
    private String docMandatory;

    private String docName;
    private String docType;
    private Long docSize;
    private Long dgId;

    private Long clmSrNo;
    private Long docSrNo;
    private String ccmValueset;
    
    private String docNameReg;    
    private String docTypeReg;
    
    private String docPrefixRequired;
    
    private String prefixName;

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the smServiceName
     */
    public String getSmServiceName() {
        return smServiceName;
    }

    /**
     * @param smServiceName the smServiceName to set
     */
    public void setSmServiceName(final String smServiceName) {
        this.smServiceName = smServiceName;
    }

    /**
     * @return the smServiceNameMar
     */
    public String getSmServiceNameMar() {
        return smServiceNameMar;
    }

    /**
     * @param smServiceNameMar the smServiceNameMar to set
     */
    public void setSmServiceNameMar(final String smServiceNameMar) {
        this.smServiceNameMar = smServiceNameMar;
    }

    /**
     * @return the docGroup
     */
    public String getDocGroup() {
        return docGroup;
    }

    /**
     * @param docGroup the docGroup to set
     */
    public void setDocGroup(final String docGroup) {
        this.docGroup = docGroup;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(final String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(final String docType) {
        this.docType = docType;
    }

    public Long getDocSize() {
        return docSize;
    }

    public void setDocSize(final Long docSize) {
        this.docSize = docSize;
    }

    public Long getClmSrNo() {
        return clmSrNo;
    }

    public void setClmSrNo(final Long clmSrNo) {
        this.clmSrNo = clmSrNo;
    }

    public String getCcmValueset() {
        return ccmValueset;
    }

    public void setCcmValueset(final String ccmValueset) {
        this.ccmValueset = ccmValueset;
    }

    public Long getDgId() {
        return dgId;
    }

    public void setDgId(final Long dgId) {
        this.dgId = dgId;
    }

    public Long getDocSrNo() {
        return docSrNo;
    }

    public void setDocSrNo(final Long docSrNo) {
        this.docSrNo = docSrNo;
    }

    public String getDocMandatory() {
        return docMandatory;
    }

    public void setDocMandatory(final String docMandatory) {
        this.docMandatory = docMandatory;
    }

	public String getDocNameReg() {
		return docNameReg;
	}

	public void setDocNameReg(String docNameReg) {
		this.docNameReg = docNameReg;
	}

	public String getDocTypeReg() {
		return docTypeReg;
	}

	public void setDocTypeReg(String docTypeReg) {
		this.docTypeReg = docTypeReg;
	}

	public String getDocPrefixRequired() {
		return docPrefixRequired;
	}

	public void setDocPrefixRequired(String docPrefixRequired) {
		this.docPrefixRequired = docPrefixRequired;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	
	
}
