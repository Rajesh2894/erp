package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HRMSRecruitmentPostsEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Name_Of_Post")
	private String nameOfPost;

	@Column(name = "Cadre_Name")
	private String cadreName;

	@Column(name = "Sanctioned")
	private Integer sanctioned;

	@Column(name = "Filled")
	private Integer filled;

	@Column(name = "Vacant")
	private Integer vacant;

	@Column(name = "Recruitment_Total_Posts")
	private Integer recruitmentTotal;

	@Column(name = "Filled2")
	private Integer recruitmentFilled;

	@Column(name = "Vacant2")
	private Integer recruitmentVacant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNameOfPost() {
		return nameOfPost;
	}

	public void setNameOfPost(String nameOfPost) {
		this.nameOfPost = nameOfPost;
	}

	public String getCadreName() {
		return cadreName;
	}

	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}

	public Integer getSanctioned() {
		return sanctioned;
	}

	public void setSanctioned(Integer sanctioned) {
		this.sanctioned = sanctioned;
	}

	public Integer getFilled() {
		return filled;
	}

	public void setFilled(Integer filled) {
		this.filled = filled;
	}

	public Integer getVacant() {
		return vacant;
	}

	public void setVacant(Integer vacant) {
		this.vacant = vacant;
	}

	public Integer getRecruitmentTotal() {
		return recruitmentTotal;
	}

	public void setRecruitmentTotal(Integer recruitmentTotal) {
		this.recruitmentTotal = recruitmentTotal;
	}

	public Integer getRecruitmentFilled() {
		return recruitmentFilled;
	}

	public void setRecruitmentFilled(Integer recruitmentFilled) {
		this.recruitmentFilled = recruitmentFilled;
	}

	public Integer getRecruitmentVacant() {
		return recruitmentVacant;
	}

	public void setRecruitmentVacant(Integer recruitmentVacant) {
		this.recruitmentVacant = recruitmentVacant;
	}

	@Override
	public String toString() {
		return "HRMSRecruitmentPostsEntity [id=" + id + ", nameOfPost=" + nameOfPost + ", cadreName=" + cadreName
				+ ", sanctioned=" + sanctioned + ", filled=" + filled + ", vacant=" + vacant + ", recruitmentTotal="
				+ recruitmentTotal + ", recruitmentFilled=" + recruitmentFilled + ", recruitmentVacant="
				+ recruitmentVacant + "]";
	}

}
