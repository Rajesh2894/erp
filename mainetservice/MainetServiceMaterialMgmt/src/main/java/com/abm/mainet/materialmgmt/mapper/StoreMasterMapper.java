package com.abm.mainet.materialmgmt.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.domain.StoreGroupMapping;
import com.abm.mainet.materialmgmt.domain.StoreMaster;
import com.abm.mainet.materialmgmt.dto.StoreGroupMappingDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;

@Component
public class StoreMasterMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public StoreMasterMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@SuppressWarnings("deprecation")
	public StoreMaster mapStoreMasterDTOToStoreMaster(StoreMasterDTO storeMasterDTO) {
		if (storeMasterDTO == null) {
			return null;
		}
		StoreMaster master = map(storeMasterDTO, StoreMaster.class);
		StoreGroupMapping storeGroupMappingDetails = null;
		if (storeMasterDTO.getStoreGrMappingDtoList() != null) {
			for (final StoreGroupMappingDto storeGroupMappingDto : storeMasterDTO.getStoreGrMappingDtoList()) {
				if(storeGroupMappingDto.getItemGroupId() != null) {
					storeGroupMappingDetails = new StoreGroupMapping();
					storeGroupMappingDetails.setGroupMapId(storeGroupMappingDto.getGroupMapId());
					storeGroupMappingDetails.setItemGroupId(storeGroupMappingDto.getItemGroupId());
					storeGroupMappingDetails.setStatus(storeGroupMappingDto.getStatus());
					storeGroupMappingDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					storeGroupMappingDetails.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					storeGroupMappingDetails.setLmoDate(new Date());
					storeGroupMappingDetails.setLgIpMac(Utility.getMacAddress());
					storeGroupMappingDetails.setLangId(UserSession.getCurrent().getOrganisation().getOrgid());
					master.addStoreMasterMapping(storeGroupMappingDetails);
				}
				
			}
		}
		return master;
	}
	
	public StoreMasterDTO mapStoreMasterToStoreMasterDTO(StoreMaster storeMaster) {
		if (storeMaster == null) {
			return null;
		}
		final StoreMasterDTO storeMasterDTO = map(storeMaster, StoreMasterDTO.class);
		List<StoreGroupMappingDto> storeMasterDTODtoList = new ArrayList<>();
		StoreGroupMappingDto storeMasterDetails = null;
		if (storeMaster.getGrMappingList() != null && storeMaster.getGrMappingList().size() > 0) {
			for (final StoreGroupMapping storeGroupMapping : storeMaster.getGrMappingList()) {
				storeMasterDetails = new StoreGroupMappingDto();
				storeMasterDetails.setGroupMapId(storeGroupMapping.getGroupMapId());
				storeMasterDetails.setItemGroupId(storeGroupMapping.getItemGroupId());
				storeMasterDetails.setStatus(storeGroupMapping.getStatus());
				storeMasterDetails.setOrgId(storeGroupMapping.getOrgId());
				storeMasterDetails.setUserId(storeGroupMapping.getUserId());
				storeMasterDetails.setLmoDate(storeGroupMapping.getLmoDate());
				storeMasterDetails.setLgIpMac(storeGroupMapping.getLgIpMac());
				storeMasterDetails.setLangId(storeGroupMapping.getLangId());
				
				storeMasterDTODtoList.add(storeMasterDetails);
			}
			storeMasterDTO.setStoreGrMappingDtoList(storeMasterDTODtoList);
		}
		return storeMasterDTO;
	}

	public List<StoreMasterDTO> mapStoreMasterListToStoreMasterDTOList(List<StoreMaster> storeMasterList) {
		if (storeMasterList == null) {
			return null;
		}
		return map(storeMasterList, StoreMasterDTO.class);
	}

	public List<StoreMaster> mapStoreMasterDTOListToStoreMasterList(List<StoreMasterDTO> storeMasterDTOList) {
		if (storeMasterDTOList == null) {
			return null;
		}
		return map(storeMasterDTOList, StoreMaster.class);
	}

}
