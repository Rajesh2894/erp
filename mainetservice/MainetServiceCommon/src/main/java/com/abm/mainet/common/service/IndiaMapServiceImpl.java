package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dto.DistrictLocationDto;
import com.abm.mainet.common.repository.DistrictLocationRepository;

@Service
public class IndiaMapServiceImpl implements IndiaMapService{


	@Autowired DistrictLocationRepository districtLocationRepository;

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getVacantBlockData() {
		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getVacantBlockData();
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);
			count++;
			
			dto.setRank(count);
			dto.setCategory("VACANT");
			dto.setRange("1-30000");
			dto.setColor("#3AB54B");
			districtLocationDtos.add(dto);
		}

		list.put(count, districtLocationDtos);

		return list;

	}

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getAllocatedBlackList() {
		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getAllocatedBlockData();
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);

			count++;
			
			dto.setRank(count);
			dto.setCategory("ALLOCATED");
			dto.setRange("1-30000");
			dto.setColor("#ffa500");
			districtLocationDtos.add(dto);
		}
		list.put(count, districtLocationDtos);

		return list;

	}

	@Override
	public HashMap<Integer, List<String>> getIaList(Long distId) {
		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getIaList(distId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;
	}

	@Override
	public HashMap<Integer, List<String>> getCbboList(Long distId) {

		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getCbboList(distId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;

	}

	@Override
	public HashMap<Integer, List<String>> getFpoList(Long distId) {

		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getFPOList(distId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;


	}

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getVacantList(Long distId) {

		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getVacantList(distId);
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);


			count++;
			dto.setRank(count);
			dto.setCategory("VACANT");
			dto.setRange("1-30000");
			dto.setColor("#3AB54B");
			districtLocationDtos.add(dto);
		}
		list.put(count, districtLocationDtos);

		return list;


	}

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getAllocatedList(Long distId) {

		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getAllocatedList(distId);
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);


			count++;
			dto.setRank(count);
			dto.setCategory("ALLOCATED");
			dto.setRange("1-30000");
			dto.setColor("#ffa500");
			districtLocationDtos.add(dto);
		}
		list.put(count, districtLocationDtos);

		return list;


	}

	@Override
	public Integer getTotalBlock(Long distId) {
		// TODO Auto-generated method stub
		return districtLocationRepository.getTotalBlock(distId);
	}

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getVacantListByBlockId(Long blockId) {

		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getVacantListByBlock(blockId);
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);


			count++;
			dto.setRank(count);
			dto.setCategory("VACANT");
			dto.setRange("1-30000");
			dto.setColor("#3AB54B");
			districtLocationDtos.add(dto);
		}
		list.put(count, districtLocationDtos);

		return list;


	}

	@Override
	public HashMap<Integer, List<DistrictLocationDto>> getAllocatedListByBlockId(Long blockId) {

		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<DistrictLocationDto>> list = new HashMap<>();
		List<Object[]> detailsList = districtLocationRepository.getAllocatedListByBlock(blockId);
		List<DistrictLocationDto> districtLocationDtos = new ArrayList<DistrictLocationDto>();
		for (Object[] obj : detailsList) {
			DistrictLocationDto dto = new DistrictLocationDto();
			if (obj[0] != null)
				dto.setStateId(Long.parseLong(obj[0].toString()));
			if (StringUtils.isNotEmpty((String) obj[1]))
				dto.setState((String) obj[1]);
			if (obj[2] != null)
				dto.setDistId(Long.parseLong(obj[2].toString()));
			if (StringUtils.isNotEmpty((String) obj[3]))
				dto.setDistrict((String) obj[3]);
			if (obj[4] != null)
				dto.setBlockId(Long.parseLong(obj[4].toString()));
			if (StringUtils.isNotEmpty((String) obj[5]))
				dto.setBlock((String) obj[5]);
			if (StringUtils.isNotEmpty((String) obj[6]))
				dto.setLatitude((String) obj[6]);
			if (StringUtils.isNotEmpty((String) obj[7]))
				dto.setLongitude((String) obj[7]);


			count++;
			dto.setRank(count);
			dto.setCategory("ALLOCATED");
			dto.setRange("1-30000");
			dto.setColor("#ffa500");
			districtLocationDtos.add(dto);
		}
		list.put(count, districtLocationDtos);

		return list;


	}

	@Override
	public HashMap<Integer, List<String>> getIaListByBlock(Long blockId) {
		// TODO Auto-generated method stub
		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getIaListByBlock(blockId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;
	}

	@Override
	public HashMap<Integer, List<String>> getCbboListByBlock(Long blockId) {

		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getCbboListByBlock(blockId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;

	}

	@Override
	public HashMap<Integer, List<String>> getFpoListByBlock(Long blockId) {

		Integer count = 0;
		HashMap<Integer, List<String>> list = new HashMap<>();
		List<String> detailsList = districtLocationRepository.getFPOListByBlock(blockId);
		List<String> nameList = new ArrayList<String>();
		for (String obj : detailsList) {

			if (StringUtils.isNotEmpty(obj))
				nameList.add(obj);

			count++;
		}
		list.put(count, nameList);

		return list;


	}

	@Override
	public Integer getTotalBlockBlockId(Long blockId) {
		// TODO Auto-generated method stub
		return districtLocationRepository.getTotalBlockByBlock(blockId);
	}

}
