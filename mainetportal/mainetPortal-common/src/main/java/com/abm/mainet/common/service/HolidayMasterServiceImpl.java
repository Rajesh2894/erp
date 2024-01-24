package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.HolidayMasterDao;
import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.common.domain.WorkTimeMaster;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.WorkTimeMasterDto;
import com.abm.mainet.common.util.Utility;

@Service
public class HolidayMasterServiceImpl implements HolidayMasterService {

	@Resource
	private HolidayMasterDao holidayMasterDao;

	@Override
	@Transactional
	public WorkTimeMasterDto saveWorkTimeMaster(WorkTimeMasterDto workTimeEntity, String mode, Long orgId,
			Long userId) {

		Date todayDate = new Date();

		if (mode.equals(MainetConstants.EDIT)) {

			WorkTimeMaster master = holidayMasterDao.getworkTimeEntity(orgId);
			if (master != null) {
				master.setUpdatedDate(todayDate);
				master.setUpdatedBy(userId);
				master.setLgIpMacUpd(Utility.getMacAddress());
				master.setWrValidEndDate(todayDate);

				holidayMasterDao.saveWorkTimeMaster(master);
			}
		}
		workTimeEntity.setWrId(null);
		workTimeEntity.setWrEntryDate(todayDate);
		workTimeEntity.setCreatedBy(userId);
		workTimeEntity.setCreatedDate(todayDate);
		workTimeEntity.setLgIpMac(Utility.getMacAddress());
		workTimeEntity.setUpdatedDate(null);
		workTimeEntity.setUpdatedBy(null);
		workTimeEntity.setLgIpMacUpd(null);
		workTimeEntity.setOrgId(orgId);
		workTimeEntity.setWrValidEndDate(null);
		if (workTimeEntity.getWorrkWeekFlag().equals(MainetConstants.FLAGN)) {
			workTimeEntity.setWrEvenWorkWeek(null);
			workTimeEntity.setWrOddWorkWeek(null);
		} else {
			workTimeEntity.setWrWorkWeek(null);
		}
		WorkTimeMaster master = new WorkTimeMaster();
		BeanUtils.copyProperties(workTimeEntity, master);
		master = holidayMasterDao.saveWorkTimeMaster(master);
		BeanUtils.copyProperties(master, workTimeEntity);
		return workTimeEntity;
	}

	@Override
	@Transactional
	public WorkTimeMasterDto getworkTimeEntity(Long orgId) {
		WorkTimeMasterDto dto = new WorkTimeMasterDto();
		WorkTimeMaster master = holidayMasterDao.getworkTimeEntity(orgId);
		if(master!=null)
		BeanUtils.copyProperties(master, dto);
		return dto;
	}

	@Override
	@Transactional
	public boolean saveDeleteHolidayMaster(Long hoId, Long orgId) {
		return holidayMasterDao.saveDeleteHolidayMaster(orgId, hoId);
	}

	@Override
	@Transactional
	public void saveHolidayDetailsList(List<HolidayMasterDto> holidayMasterList, 
			Long orgId, Long userId) {

		HolidayMaster holidayMasterUpdate = null;
		Date todayDate = new Date();

		for (HolidayMasterDto holidayMasters : holidayMasterList) {
			holidayMasterUpdate = new HolidayMaster();
			if (holidayMasters.getCreatedDate() != null)
				holidayMasters.setUpdatedDate(todayDate);
			if (holidayMasters.getCreatedBy() != null)
				holidayMasters.setUpdatedBy(userId);
			holidayMasters.setOrgId(orgId);
			if (holidayMasters.getLgIpMac() != null)
				holidayMasters.setLgIpMacUpd(Utility.getMacAddress());

			holidayMasters.setCreatedBy(userId);
			holidayMasters.setCreatedDate(todayDate);
			holidayMasters.setLgIpMac(Utility.getMacAddress());
			holidayMasters.setHoActive(MainetConstants.FLAGY);
			BeanUtils.copyProperties(holidayMasters, holidayMasterUpdate);
			holidayMasterDao.saveHolidayDetailsList(holidayMasterUpdate);

		}

	}

	@Override
	@Transactional
	public List<HolidayMasterDto> getGridData(Date yearStartDate, Date yearEndDate, Long orgId) {
		List<HolidayMasterDto> list = new ArrayList<>();
		HolidayMasterDto dto = null;
		List<HolidayMaster> holidayMasterEntity = holidayMasterDao.getGridData(yearStartDate, yearEndDate, orgId);
		for (HolidayMaster holidayMaster : holidayMasterEntity) {
			dto = new HolidayMasterDto();
			BeanUtils.copyProperties(holidayMaster, dto);
			list.add(dto);
		}
		return list;

	}

	@Override
	@Transactional
	public List<HolidayMasterDto> getHolidayDetailsList(Date startDate, Date endDate, Long orgId) {
		List<HolidayMasterDto> list = new ArrayList<>();
		HolidayMasterDto dto = null;
		List<HolidayMaster> holidayMasterEntity = holidayMasterDao.getHolidayDetailsList(startDate, endDate, orgId);
		for (HolidayMaster holidayMaster : holidayMasterEntity) {
			dto = new HolidayMasterDto();
			BeanUtils.copyProperties(holidayMaster, dto);
			list.add(dto);
		}
		return list;
	}

}
