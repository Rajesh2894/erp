package com.abm.mainet.securitymanagement.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.securitymanagement.dao.IShiftMasterDao;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.ShiftMasterDTO;
import com.abm.mainet.securitymanagement.repository.ShiftMasterRepository;

@Service
public class ShiftMasterServiceImpl implements IShiftMasterService {

	@Autowired
	private ShiftMasterRepository repo;

	@Autowired
	private IShiftMasterDao shiftMasterDao;

	@Override
	@Transactional
	public ShiftMasterDTO saveOrUpdate(ShiftMasterDTO dto) {
		ShiftMaster shiftMaster = new ShiftMaster();
		List<ShiftMaster> shiftList = repo.findAll(dto.getOrgid());

		Long count = 0l;
		dto.setCount(count);
		if (dto.getShiftMasId() != null) {
			for (int i = 0; i < shiftList.size(); i++) {
				if (dto.getShiftMasId() != shiftList.get(i).getShiftMasId()) {
					if (((shiftList.get(i).getShiftId().equals(dto.getShiftId()))
							&& (shiftList.get(i).getStatus().equals("A")))
							|| ((shiftList.get(i).getIsCrossDayShift().equals("Y"))
									&& (dto.getIsCrossDayShift().equals("Y")))
							|| ((shiftList.get(i).getIsGeneralShift().equals("Y"))
									&& (dto.getIsGeneralShift().equals("Y")))) {

						if (shiftList.get(i).getIsCrossDayShift().equals("Y")
								&& (dto.getIsCrossDayShift().equals("Y"))) {
							dto.setCrossDayFlag("Y");
						} else if (shiftList.get(i).getIsGeneralShift().equals("Y")
								&& (dto.getIsGeneralShift().equals("Y"))) {
							dto.setGeneralDayFlag("Y");
						}
						count++;
						dto.setCount(count);
					}
				}
			}
		} else {
			for (int i = 0; i < shiftList.size(); i++) {
				if (((shiftList.get(i).getShiftId().equals(dto.getShiftId()))
						&& (shiftList.get(i).getStatus().equals("A")))
						|| ((shiftList.get(i).getIsCrossDayShift().equals("Y"))
								&& (dto.getIsCrossDayShift().equals("Y")))
						|| ((shiftList.get(i).getIsGeneralShift().equals("Y"))
								&& (dto.getIsGeneralShift().equals("Y")))) {

					if (shiftList.get(i).getIsCrossDayShift().equals("Y") && (dto.getIsCrossDayShift().equals("Y"))) {
						dto.setCrossDayFlag("Y");
					} else if (shiftList.get(i).getIsGeneralShift().equals("Y")
							&& (dto.getIsGeneralShift().equals("Y"))) {
						dto.setGeneralDayFlag("Y");
					}
					count++;
					dto.setCount(count);
				}
			}
		}
		if (count == 0) {
			BeanUtils.copyProperties(dto, shiftMaster);
			repo.save(shiftMaster);
		}
		return dto;
	}

	@Override
	public List<ShiftMasterDTO> searchShift(Long shiftId, Long orgid) {
		List<ShiftMaster> listShift = shiftMasterDao.searchShift(shiftId, orgid);
		List<ShiftMasterDTO> listShiftDto = new ArrayList<ShiftMasterDTO>();

		listShift.forEach(data -> {
			ShiftMasterDTO shiftDto = new ShiftMasterDTO();
			BeanUtils.copyProperties(data, shiftDto);
			if (shiftDto.getShiftId() != null) {		
				shiftDto.setShiftIdDesc(
						CommonMasterUtility.getCPDDescription(data.getShiftId(), MainetConstants.BLANK));
				if(StringUtils.isNotEmpty(shiftDto.getIsCrossDayShift()) && shiftDto.getIsCrossDayShift().equals("Y")) {
					shiftDto.setIsCrossDayShift("Yes");
				}
				else if(StringUtils.isNotEmpty(shiftDto.getIsCrossDayShift()) ){
					shiftDto.setIsCrossDayShift("No");
				}
				
				if(StringUtils.isNotEmpty(shiftDto.getIsGeneralShift()) && shiftDto.getIsGeneralShift().equals("Y")) {
					shiftDto.setIsGeneralShift("Yes");
				}
				else if(StringUtils.isNotEmpty(shiftDto.getIsGeneralShift()) ) {
					shiftDto.setIsGeneralShift("No");
				}
				
				if(StringUtils.isNotEmpty(shiftDto.getFromTime())) {
					shiftDto.setFromTime(shiftDto.getFromTime().substring(0,5));
				}
				
				if(StringUtils.isNotEmpty(shiftDto.getToTime())) {
					shiftDto.setToTime(shiftDto.getToTime().substring(0,5));
				}
				
			}
			listShiftDto.add(shiftDto);
		});
		return listShiftDto;
	}

	@Override
	public ShiftMasterDTO findById(Long shiftMasId) {
		ShiftMasterDTO dto = new ShiftMasterDTO();
		ShiftMaster master = repo.findOne(shiftMasId);
		BeanUtils.copyProperties(master, dto);
		if(StringUtils.isNotEmpty(dto.getFromTime())) {
			dto.setFromTime(dto.getFromTime().substring(0,5));
		}
		
		if(StringUtils.isNotEmpty(dto.getToTime())) {
			dto.setToTime(dto.getToTime().substring(0,5));
		}
		return dto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<LookUp> getAvtiveShift(final Long orgid) {
		List<LookUp> lookupShift = new ArrayList<>();
		List<Long> activeShiftList = repo.getActiveShiftInfo(orgid);

		activeShiftList.forEach(activeShift -> {
			if(activeShift!=null) {
			LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(activeShift, "SHT", orgid);
			lookupShift.add(lookUp);
	}
		});

		return lookupShift;
	}
	
}
