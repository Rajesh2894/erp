package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.abm.mainet.cfc.loi.dto.LoiPrintDTO;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
//import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiMediaListDTO;

@Component
@Scope("session")
public class RtiLoiPrintingFormModel extends AbstractFormModel{

private static final long serialVersionUID = 1L;

private RtiApplicationFormDetailsReqDTO rtiInformationApplicantDto = new RtiApplicationFormDetailsReqDTO();
        
private List<RtiApplicationFormDetailsReqDTO>  informationApplicant = new ArrayList<>();

private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

private MediaChargeAmountDTO mediaQuantityType= new MediaChargeAmountDTO();

private ServiceMaster serviceMaster = new ServiceMaster();

private List<RtiMediaListDTO> mediaList = new ArrayList<>();

public RtiApplicationFormDetailsReqDTO getRtiInformationApplicantDto() {
        return rtiInformationApplicantDto;
}
private Department department= new Department();

private List<TbDepartment> departmentList =new ArrayList<>();

private LoiPrintDTO loiPrintDto =new  LoiPrintDTO();

private List<LoiPrintDTO> loiDto = new ArrayList<>();

private List<TbLoiMas> loidata = new ArrayList<>();

private String amountInWords;


private long a3a4Quantity;

private long flopCopy;

private String PioName;

 private long LargeCopy;
 
 private long Inspection;
 
 private Long pageQuantity1;
 
 private Long pageQuantity2;
 
 private Long pageQuantity3; 

 private Long pageQuantity4;
 
 private Long grandTotal;


 
 
public Long getPageQuantity3() {
	return pageQuantity3;
}

public void setPageQuantity3(Long pageQuantity3) {
	this.pageQuantity3 = pageQuantity3;
}
private String dateDescription;

public List<RtiApplicationFormDetailsReqDTO> getInformationApplicant() {
        return informationApplicant;
}

public void setInformationApplicant(List<RtiApplicationFormDetailsReqDTO> informationApplicant) {
        this.informationApplicant = informationApplicant;
}

public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
}

public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
}

public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
}

public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
}

public LoiPrintDTO getLoiPrintDto() {
        return loiPrintDto;
}

public void setLoiPrintDto(LoiPrintDTO loiPrintDto) {
        this.loiPrintDto = loiPrintDto;
}

public List<LoiPrintDTO> getLoiDto() {
        return loiDto;
}

public void setLoiDto(List<LoiPrintDTO> loiDto) {
        this.loiDto = loiDto;
}

public void setRtiInformationApplicantDto(RtiApplicationFormDetailsReqDTO rtiInformationApplicantDto) {
        this.rtiInformationApplicantDto = rtiInformationApplicantDto;
}

public List<TbLoiMas> getLoidata() {
        return loidata;
}

public void setLoidata(List<TbLoiMas> loidata) {
        this.loidata = loidata;
}

public MediaChargeAmountDTO getMediaQuantityType() {
    return mediaQuantityType;
}

public void setMediaQuantityType(MediaChargeAmountDTO mediaQuantityType) {
    this.mediaQuantityType = mediaQuantityType;
}

public ServiceMaster getServiceMaster() {
	return serviceMaster;
}

public void setServiceMaster(ServiceMaster serviceMaster) {
	this.serviceMaster = serviceMaster;
}

public List<RtiMediaListDTO> getMediaList() {
	return mediaList;
}

public void setMediaList(List<RtiMediaListDTO> mediaList) {
	this.mediaList = mediaList;
}



public List<TbDepartment> getDepartmentList() {
	return departmentList;
}

public void setDepartmentList(List<TbDepartment> departmentList) {
	this.departmentList = departmentList;
}

public Department getDepartment() {
	return department;
}

public void setDepartment(Department department) {
	this.department = department;
}

public String getAmountInWords() {
	return amountInWords;
}

public void setAmountInWords(String amountInWords) {
	this.amountInWords = amountInWords;
}

public String getPioName() {
	return PioName;
}

public void setPioName(String pioName) {
	PioName = pioName;
}

public long getA3a4Quantity() {
	return a3a4Quantity;
}
public void setA3a4Quantity(long a3a4Quantity) {
	this.a3a4Quantity = a3a4Quantity;
}

public long getFlopCopy() {
	return flopCopy;
}

public void setFlopCopy(long flopCopy) {
	this.flopCopy = flopCopy;
}

public long getLargeCopy() {
	return LargeCopy;
}

public void setLargeCopy(long largeCopy) {
	LargeCopy = largeCopy;
}

public String getDateDescription() {
	return dateDescription;
}

public void setDateDescription(String dateDescription) {
	this.dateDescription = dateDescription;
}

public Long getPageQuantity1() {
	return pageQuantity1;
}

public void setPageQuantity1(Long pageQuantity1) {
	this.pageQuantity1 = pageQuantity1;
}

public Long getPageQuantity2() {
	return pageQuantity2;
}

public void setPageQuantity2(Long pageQuantity2) {
	this.pageQuantity2 = pageQuantity2;
}

public Long getGrandTotal() {
	return grandTotal;
}

public void setGrandTotal(Long grandTotal) {
	this.grandTotal = grandTotal;
}

public Long getPageQuantity4() {
	return pageQuantity4;
}

public void setPageQuantity4(Long pageQuantity4) {
	this.pageQuantity4 = pageQuantity4;
}

public long getInspection() {
	return Inspection;
}

public void setInspection(long inspection) {
	Inspection = inspection;
}


}
