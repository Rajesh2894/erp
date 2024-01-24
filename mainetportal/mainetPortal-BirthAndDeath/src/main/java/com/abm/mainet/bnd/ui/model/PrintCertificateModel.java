package com.abm.mainet.bnd.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component("printCertificateModel")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PrintCertificateModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1796895240130367999L;

    private TbDeathregDTO tbDeathregDTO= new TbDeathregDTO();
	
	private BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();
	
	private List<TbDeathregDTO> tbDeathregDTOList;
	
	private TbBdCertCopyDTO tbBdCertCopyDTO;
	
	private List<BirthRegistrationDTO> tbBirthregDTOList;
	
	private List<HospitalMasterDTO> hospitalMasterDTOList;
	
	//private List<CemeteryMasterDTO> cemeteryMasterDTOList;
	
	private String childOrDecasedName;
	private String fatherName;
	private String year;
	private String address;
	private String remark;
	private String remarkReg;
	private String serviceCode;
	
	
	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}
	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}
	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}
	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}
	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}
	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}
	public List<BirthRegistrationDTO> getTbBirthregDTOList() {
		return tbBirthregDTOList;
	}
	public void setTbBirthregDTOList(List<BirthRegistrationDTO> tbBirthregDTOList) {
		this.tbBirthregDTOList = tbBirthregDTOList;
	}
	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}
	public void setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		this.hospitalMasterDTOList = hospitalMasterDTOList;
	}
	public String getChildOrDecasedName() {
		return childOrDecasedName;
	}
	public void setChildOrDecasedName(String childOrDecasedName) {
		this.childOrDecasedName = childOrDecasedName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkReg() {
		return remarkReg;
	}
	public void setRemarkReg(String remarkReg) {
		this.remarkReg = remarkReg;
	}
	public TbBdCertCopyDTO getTbBdCertCopyDTO() {
		return tbBdCertCopyDTO;
	}
	public void setTbBdCertCopyDTO(TbBdCertCopyDTO tbBdCertCopyDTO) {
		this.tbBdCertCopyDTO = tbBdCertCopyDTO;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	
	
	
	
}
