package com.abm.mainet.common.service;

import java.util.HashMap;
import java.util.List;

import com.abm.mainet.common.dto.DistrictLocationDto;

public interface IndiaMapService {

	HashMap<Integer, List<DistrictLocationDto>> getVacantBlockData();

	HashMap<Integer, List<DistrictLocationDto>> getAllocatedBlackList();

	HashMap<Integer, List<String>> getIaList(Long distId);

	HashMap<Integer, List<String>> getCbboList(Long distId);

	HashMap<Integer, List<String>> getFpoList(Long distId);

	HashMap<Integer, List<DistrictLocationDto>> getVacantList(Long distId);

	HashMap<Integer, List<DistrictLocationDto>> getAllocatedList(Long distId);

	Integer getTotalBlock(Long distId);

	HashMap<Integer, List<DistrictLocationDto>> getVacantListByBlockId(Long blockId);

	HashMap<Integer, List<DistrictLocationDto>> getAllocatedListByBlockId(Long blockId);

	HashMap<Integer, List<String>> getIaListByBlock(Long blockId);

	HashMap<Integer, List<String>> getCbboListByBlock(Long blockId);

	HashMap<Integer, List<String>> getFpoListByBlock(Long blockId);

	Integer getTotalBlockBlockId(Long blockId);

}
