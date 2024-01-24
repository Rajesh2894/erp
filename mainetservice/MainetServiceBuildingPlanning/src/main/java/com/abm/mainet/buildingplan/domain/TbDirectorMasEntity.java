package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_DIRECTORS_MSTR")
public class TbDirectorMasEntity implements Serializable {

	private static final long serialVersionUID = 1105130536880012421L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID", nullable = false)
    private Long directorId;
	
	@ManyToOne
	@JoinColumn(name = "TCP_DEV_REG_NO")
	private TbDeveloperRegistrationEntity developerRegMas;
	
    @Column(name = "NAME", length = 45, nullable = true)
    private String directorName;

    @Column(name = "DIN_NO", length = 15, nullable = true)
    private String dinNumber;

    @Column(name = "CONTACT_NO", length = 10, nullable = true)
    private Long directorContactNumber;
  
    @Column(name = "CREATED_BY", length = 45, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "MODIFIED_BY", length = 45, nullable = true)
    private Long updatedBy;

    @Column(name = "MODIFIED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

	public String[] getPkValues() {
        return new String[] { "DRN", "TB_BPMS_DIRECTORS_MSTR", "ID" };
    }


	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getDinNumber() {
		return dinNumber;
	}

	public void setDinNumber(String dinNumber) {
		this.dinNumber = dinNumber;
	}

	public Long getDirectorContactNumber() {
		return directorContactNumber;
	}

	public void setDirectorContactNumber(Long directorContactNumber) {
		this.directorContactNumber = directorContactNumber;
	}

	public TbDeveloperRegistrationEntity getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(TbDeveloperRegistrationEntity developerRegMas) {
		this.developerRegMas = developerRegMas;
	}

	public Long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Long getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public String getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}