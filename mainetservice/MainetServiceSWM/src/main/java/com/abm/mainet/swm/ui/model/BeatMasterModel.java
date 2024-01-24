package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.BeatDetailDto;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.ui.validator.BeatMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class BeatMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = 3393907505674171218L;

    @Autowired
    private IBeatMasterService beatMasterService;

    private BeatMasterDTO beatMasterDTO = new BeatMasterDTO();
    
    private List<BeatDetailDto> beatDetailListDto = new ArrayList<>();
    
    private List<BeatMasterDTO> beatMasterList = new ArrayList<>();

    private List<TbLocationMas> locList = new ArrayList<>();

    private List<MRFMasterDto> mrfMasterList = new ArrayList<>();

    private List<TbLocationMas> location = new ArrayList<>();

    private String excelFileName;

    private String successFlag;

    private String saveMode;
    
    private String removeAreaDetail;
    

    public List<BeatDetailDto> getBeatDetailListDto() {
        return beatDetailListDto;
    }

    public void setBeatDetailListDto(List<BeatDetailDto> beatDetailListDto) {
        this.beatDetailListDto = beatDetailListDto;
    }

    public List<BeatMasterDTO> getBeatMasterList() {
        return beatMasterList;
    }

    public void setBeatMasterList(List<BeatMasterDTO> beatMasterList) {
        this.beatMasterList = beatMasterList;
    }


    @Override
    public boolean saveForm() {
	boolean status = true;
	validateBean(beatMasterDTO, BeatMasterValidator.class);
	beatMasterDTO.setCollCount(10L);
	beatMasterDTO.setDryAssQty(10L);
	beatMasterDTO.setWetAssQty(10L);
	beatMasterDTO.setHazAssQty(10L);
	if (hasValidationErrors()) {
	    return false;
	} else {
	    if (beatMasterDTO.getBeatId() == null) {
		beatMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		beatMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		beatMasterDTO.setCreatedDate(new Date());
		beatMasterDTO.setLgIpMac(Utility.getMacAddress());
		beatMasterDTO.setBeatActive(MainetConstants.FlagY);

		for (BeatDetailDto beatDetailDto : beatMasterDTO.getTbSwBeatDetail()) {
		    if (beatDetailDto.getBeatDetId() == null) {
			beatDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			beatDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			beatDetailDto.setCreatedDate(new Date());
			beatDetailDto.setLpIpMac(Utility.getMacAddress());

			if (beatDetailDto.getBeatHouseHold() == null && beatDetailDto.getBeatShop() != null) {
			    beatDetailDto.setBeatHouseHold(0l);
			}
			if (beatDetailDto.getBeatHouseHold() != null && beatDetailDto.getBeatShop() == null) {
			    beatDetailDto.setBeatShop(0l);
			}

		    }

		}
		beatMasterService.saveRoute(beatMasterDTO);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("RouteMasterDTO.save.add"));
	    } else {
		beatMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		beatMasterDTO.setUpdatedDate(new Date());
		beatMasterDTO.setLgIpMacUpd(Utility.getMacAddress());

		for (BeatDetailDto beatDetailDto : beatMasterDTO.getTbSwBeatDetail()) {
		    beatDetailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		    beatDetailDto.setUpdatedDate(new Date());
		    beatDetailDto.setLpIpMac(Utility.getMacAddress());

		    if (beatDetailDto.getBeatHouseHold() == null && beatDetailDto.getBeatShop() != null) {
			beatDetailDto.setBeatHouseHold(0l);
		    }
		    if (beatDetailDto.getBeatHouseHold() != null && beatDetailDto.getBeatShop() == null) {
			beatDetailDto.setBeatShop(0l);
		    }
		}
		
		List<Long> removeWasteIds = new ArrayList<>();
		    String ids = getRemoveAreaDetail();
		    if(ids != null && !ids.isEmpty()) {
			String areaId[] = ids.split(MainetConstants.operator.COMMA);
			for (String id : areaId) {
			    removeWasteIds.add(Long.valueOf(id));
			}
		    }
		beatMasterService.updateRoute(beatMasterDTO,removeWasteIds);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("RouteMasterDTO.save.edit"));
		
	    }

	}
	return status;
    }

    public BeatMasterDTO getBeatMasterDTO() {
        return beatMasterDTO;
    }

    public void setBeatMasterDTO(BeatMasterDTO beatMasterDTO) {
        this.beatMasterDTO = beatMasterDTO;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public List<MRFMasterDto> getMrfMasterList() {
        return mrfMasterList;
    }

    public void setMrfMasterList(List<MRFMasterDto> mrfMasterList) {
        this.mrfMasterList = mrfMasterList;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public List<TbLocationMas> getLocation() {
        return location;
    }

    public void setLocation(List<TbLocationMas> location) {
        this.location = location;
    }

    public String getRemoveAreaDetail() {
        return removeAreaDetail;
    }

    public void setRemoveAreaDetail(String removeAreaDetail) {
        this.removeAreaDetail = removeAreaDetail;
    }

    
}
