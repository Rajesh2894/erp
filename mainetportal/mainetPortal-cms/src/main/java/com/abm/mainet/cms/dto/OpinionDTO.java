package com.abm.mainet.cms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OpinionDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	long id;
	String opinionEng;
	String oninionReg;
	String imgPath;
	String docPath;
	List<OptionDTO> options = new ArrayList<OptionDTO>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOpinionEng() {
		return opinionEng;
	}
	public void setOpinionEng(String opinionEng) {
		this.opinionEng = opinionEng;
	}
	public String getOninionReg() {
		return oninionReg;
	}
	public void setOninionReg(String oninionReg) {
		this.oninionReg = oninionReg;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	
}



