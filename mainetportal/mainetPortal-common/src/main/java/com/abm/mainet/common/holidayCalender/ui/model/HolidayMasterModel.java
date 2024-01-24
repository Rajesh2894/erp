package com.abm.mainet.common.holidayCalender.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.WorkTimeMasterDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.Utility;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Component
@Scope("session")
public class HolidayMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = -1297081206560447448L;

	private WorkTimeMasterDto workTimeMasterEntity; // used To Save Work Time Details

	private HolidayMasterDto holidayMasterEntity; // used To Save Holiday Details

	private Map<String, String> holidayDescriptionList = new LinkedHashMap<String, String>();

	private List<LookUp> selectionType;

	private List<HolidayMasterDto> holidayMasterList = new ArrayList<>();

	private List<HolidayMasterDto> holidayMastersGrid = new ArrayList<>();

	private List<HolidayMasterDto> editHolidayMasterData = new ArrayList<>();

	private Date hoYearStartDate;

	private Date hoYearEndDate;

	private String mode;

	private String typeFlag;

	private String startDate;
	
	private String endDate;
	
	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public WorkTimeMasterDto getWorkTimeMasterEntity() {
		return workTimeMasterEntity;
	}

	public void setWorkTimeMasterEntity(WorkTimeMasterDto workTimeMasterEntity) {
		this.workTimeMasterEntity = workTimeMasterEntity;
	}

	public HolidayMasterDto getHolidayMasterEntity() {
		return holidayMasterEntity;
	}

	public void setHolidayMasterEntity(HolidayMasterDto holidayMasterEntity) {
		this.holidayMasterEntity = holidayMasterEntity;
	}

	public Map<String, String> getHolidayDescriptionList() {
		return holidayDescriptionList;
	}

	public void setHolidayDescriptionList(Map<String, String> holidayDescriptionList) {
		this.holidayDescriptionList = holidayDescriptionList;
	}

	public List<LookUp> getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(List<LookUp> selectionType) {
		this.selectionType = selectionType;
	}

	public List<HolidayMasterDto> getHolidayMasterList() {
		return holidayMasterList;
	}

	public void setHolidayMasterList(List<HolidayMasterDto> holidayMasterList) {
		this.holidayMasterList = holidayMasterList;
	}

	public List<HolidayMasterDto> getHolidayMastersGrid() {
		return holidayMastersGrid;
	}

	public void setHolidayMastersGrid(List<HolidayMasterDto> holidayMastersGrid) {
		this.holidayMastersGrid = holidayMastersGrid;
	}

	public List<HolidayMasterDto> getEditHolidayMasterData() {
		return editHolidayMasterData;
	}

	public void setEditHolidayMasterData(List<HolidayMasterDto> editHolidayMasterData) {
		this.editHolidayMasterData = editHolidayMasterData;
	}

	public Date stringToTimeConvet(String time) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		Date timeValue = null;
		if (time != null)
			timeValue = new Date(formatter.parse(time).getTime());
		return timeValue;

	}

	public Date getHoYearStartDate() {
		return hoYearStartDate;
	}

	public void setHoYearStartDate(Date hoYearStartDate) {
		this.hoYearStartDate = hoYearStartDate;
	}

	public Date getHoYearEndDate() {
		return hoYearEndDate;
	}

	public void setHoYearEndDate(Date hoYearEndDate) {
		this.hoYearEndDate = hoYearEndDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<HolidayMasterDto> prepareholidayDetailsEntity() {

		Date endDate = Utility.getFullYearByDate(getHoYearStartDate());
		HolidayMasterDto holidayMaster = null;

		for (Map.Entry<String, String> entry : getHolidayDescriptionList().entrySet()) {

			holidayMaster = new HolidayMasterDto();
			holidayMaster.setHoDate(Utility.stringToDate(entry.getKey(), "yyyy-MM-dd"));
			holidayMaster.setHoDescription(entry.getValue());
			holidayMaster.setHoYearStartDate(getHoYearStartDate());
			holidayMaster.setHoYearEndDate(endDate);
			holidayMasterList.add(holidayMaster);
		}
		
		for (HolidayMasterDto holidayMasterDto : holidayMasterList) {
			for (HolidayMasterDto holidatDto : editHolidayMasterData) {
				if (holidatDto.getHoDate().compareTo(holidayMasterDto.getHoDate())==0) {
					String holidayDescription=holidayMasterDto.getHoDescription();
					BeanUtils.copyProperties(holidatDto, holidayMasterDto);
					holidayMasterDto.setHoDescription(holidayDescription);
				}
			}
		}
		
		return holidayMasterList;

	}

	public void prepareHolidayDescriptionList(List<HolidayMasterDto> holidayMasters) {

		for (HolidayMasterDto holidayMaster : holidayMasters) {
			DateFormat formatter = new SimpleDateFormat("yyy-M-d");
			formatter.format(holidayMaster.getHoDate());
			holidayDescriptionList.put(formatter.format(holidayMaster.getHoDate()), holidayMaster.getHoDescription());

		}

	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
