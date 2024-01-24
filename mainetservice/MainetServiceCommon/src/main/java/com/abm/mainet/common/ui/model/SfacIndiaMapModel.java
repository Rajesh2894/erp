/**
 * 
 */
package com.abm.mainet.common.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.DistrictLocationDto;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author pooja.maske
 *
 */
@Scope("session")
@Component
public class SfacIndiaMapModel extends AbstractFormModel {

	/**
	 * *********
	 */
	private static final long serialVersionUID = 4684134370126582275L;

	List<LookUp> stateList = new ArrayList<>();

	DistrictLocationDto dto = new DistrictLocationDto();

	List<LookUp> districtList = new ArrayList<>();
	
	List<LookUp> blockList = new ArrayList<>();
	
	private String vacantJson;
	
	private String viewMode;
	
	private String alocatedJson;
	
	HashMap<Integer, List<DistrictLocationDto>> vacantBlockMap = new HashMap<>();
	
	HashMap<Integer, List<DistrictLocationDto>> allocatedBlockMap = new HashMap<>();
	
	List<DistrictLocationDto> vacantBlockList = new ArrayList<>();
	
	List<DistrictLocationDto> allocatedBlockList = new ArrayList<>();
	
	HashMap<Integer, List<String>> iaList = new HashMap<>();
	HashMap<Integer, List<String>> cbboList = new HashMap<>();
	HashMap<Integer, List<String>> fpoList = new HashMap<>();
	
	Integer totalBlock;
	
	Integer totalBlockDist;
	
	Integer vacantCount;
	
	Integer allocatedCount;
	
	
	
	


	public Integer getVacantCount() {
		return vacantCount;
	}

	public void setVacantCount(Integer vacantCount) {
		this.vacantCount = vacantCount;
	}

	public Integer getAllocatedCount() {
		return allocatedCount;
	}

	public void setAllocatedCount(Integer allocatedCount) {
		this.allocatedCount = allocatedCount;
	}

	public String getVacantJson() {
		return vacantJson;
	}

	public void setVacantJson(String vacantJson) {
		this.vacantJson = vacantJson;
	}

	public String getAlocatedJson() {
		return alocatedJson;
	}

	public void setAlocatedJson(String alocatedJson) {
		this.alocatedJson = alocatedJson;
	}

	public Integer getTotalBlockDist() {
		return totalBlockDist;
	}

	public void setTotalBlockDist(Integer totalBlockDist) {
		this.totalBlockDist = totalBlockDist;
	}

	public Integer getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(Integer totalBlock) {
		this.totalBlock = totalBlock;
	}

	public HashMap<Integer, List<DistrictLocationDto>> getVacantBlockMap() {
		return vacantBlockMap;
	}

	public void setVacantBlockMap(HashMap<Integer, List<DistrictLocationDto>> vacantBlockMap) {
		this.vacantBlockMap = vacantBlockMap;
	}

	public HashMap<Integer, List<DistrictLocationDto>> getAllocatedBlockMap() {
		return allocatedBlockMap;
	}

	public void setAllocatedBlockMap(HashMap<Integer, List<DistrictLocationDto>> allocatedBlockMap) {
		this.allocatedBlockMap = allocatedBlockMap;
	}

	public List<DistrictLocationDto> getVacantBlockList() {
		return vacantBlockList;
	}

	public void setVacantBlockList(List<DistrictLocationDto> vacantBlockList) {
		this.vacantBlockList = vacantBlockList;
	}

	public List<DistrictLocationDto> getAllocatedBlockList() {
		return allocatedBlockList;
	}

	public void setAllocatedBlockList(List<DistrictLocationDto> allocatedBlockList) {
		this.allocatedBlockList = allocatedBlockList;
	}

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the dto
	 */
	public DistrictLocationDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(DistrictLocationDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	public HashMap<Integer, List<String>> getIaList() {
		return iaList;
	}

	public void setIaList(HashMap<Integer, List<String>> iaList) {
		this.iaList = iaList;
	}

	public HashMap<Integer, List<String>> getCbboList() {
		return cbboList;
	}

	public void setCbboList(HashMap<Integer, List<String>> cbboList) {
		this.cbboList = cbboList;
	}

	public HashMap<Integer, List<String>> getFpoList() {
		return fpoList;
	}

	public void setFpoList(HashMap<Integer, List<String>> fpoList) {
		this.fpoList = fpoList;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<LookUp> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	
	
}
