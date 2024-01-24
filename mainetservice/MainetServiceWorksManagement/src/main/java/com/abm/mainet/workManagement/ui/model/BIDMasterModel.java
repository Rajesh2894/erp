package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.CommercialBIDDetailDto;
import com.abm.mainet.workManagement.dto.TechnicalBIDDetailDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BIDMasterModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5272033963081634062L;
	private List<TbAcVendormaster> vendorList;
	private Map<Long, String> vendorMapList = new HashMap<Long, String>();
	private Long tndId;
	private Long bidId;
    private Long projectid;
	private String tndNo;
	private BigDecimal workEstimateAmt;
	private List<TenderMasterDto> tnderNoList = new ArrayList<>();
	private List<BidMasterDto> bidDtoList = new ArrayList<>();
	private BidMasterDto bidMasterDto = new BidMasterDto();
	private String saveMode;
	private List<BidMasterDto> bidMasterDtoList = new ArrayList<>();
	
	@Autowired
	private TenderInitiationService tenderInitiationService;
	
	
	public List<BidMasterDto> getBidDtoList() {
		return bidDtoList;
	}

	public void setBidDtoList(List<BidMasterDto> bidDtoList) {
		this.bidDtoList = bidDtoList;
	}

	public Long getProjectid() {
		return projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public String getTndNo() {
		return tndNo;
	}

	public void setTndNo(String tndNo) {
		this.tndNo = tndNo;
	}

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {
		this.tndId = tndId;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public Map<Long, String> getVendorMapList() {
		return vendorMapList;
	}

	public void setVendorMapList(Map<Long, String> vendorMapList) {
		this.vendorMapList = vendorMapList;
	}

	public List<TenderMasterDto> getTnderNoList() {
		return tnderNoList;
	}

	public void setTnderNoList(List<TenderMasterDto> tnderNoList) {
		this.tnderNoList = tnderNoList;
	}

	public BidMasterDto getBidMasterDto() {
		return bidMasterDto;
	}

	public void setBidMasterDto(BidMasterDto bidMasterDto) {
		this.bidMasterDto = bidMasterDto;
	}
	
	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public BigDecimal getWorkEstimateAmt() {
		return workEstimateAmt;
	}

	public void setWorkEstimateAmt(BigDecimal workEstimateAmt) {
		this.workEstimateAmt = workEstimateAmt;
	}

	@Override
    public boolean saveForm() {
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        List<BidMasterDto> bidDtoList = getBidDtoList();
        for (BidMasterDto bidMasterDto : bidDtoList) {
            if (bidMasterDto.getBidId() == null) {
            	bidMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            	bidMasterDto.setCreatedBy(empId);
            	bidMasterDto.setCreationDate(new Date());
            	bidMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            	bidMasterDto.setProjectid(projectid);
            	bidMasterDto.setTndNo(tndNo);
            }
        }
        bidDtoList = tenderInitiationService.createBid(bidDtoList);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("Saved Data Successfully"));
        return true;

    }

	public boolean saveTechData() {
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		BidMasterDto bidDto = getBidMasterDto();
		for (TechnicalBIDDetailDto techDto : bidDto.getTechnicalBIDDetailDtos()) {
			if (techDto.getTechBidId() == null) {
				techDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				techDto.setCreatedBy(empId);
				techDto.setCreationDate(new Date());
				techDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				techDto.setBidMasterDto(bidDto);
			} else {
				techDto.setUpdatedBy(empId);
				techDto.setUpdatedDate(new Date());
				techDto.setBidMasterDto(bidDto);
			}
		}
		for (CommercialBIDDetailDto commDto : bidDto.getCommercialBIDDetailDtos()) {
			if (commDto.getCommBidId() == null) {
				commDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				commDto.setCreatedBy(empId);
				commDto.setCreationDate(new Date());
				commDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				commDto.setBidMasterDto(bidDto);
			} else {
				commDto.setUpdatedBy(empId);
				commDto.setUpdatedDate(new Date());
				commDto.setBidMasterDto(bidDto);
			}
		}
		bidDto.getItemRateBidDetailDtos().forEach(itemRateBidDetail -> {
			if (itemRateBidDetail.getItemRateBidId() == null) {
				itemRateBidDetail.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				itemRateBidDetail.setCreatedBy(empId);
				itemRateBidDetail.setCreationDate(new Date());
				itemRateBidDetail.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				// itemRateBidDetail.setBidMasterDto(bidDto);
			} else {
				itemRateBidDetail.setUpdatedBy(empId);
				itemRateBidDetail.setUpdatedDate(new Date());
				//itemRateBidDetail.setBidMasterDto(bidDto);
			}
		});
		boolean status = tenderInitiationService.saveData(bidDto);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("Saved Data Successfully"));
		return true;

	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<BidMasterDto> getBidMasterDtoList() {
		return bidMasterDtoList;
	}

	public void setBidMasterDtoList(List<BidMasterDto> bidMasterDtoList) {
		this.bidMasterDtoList = bidMasterDtoList;
	}

}
