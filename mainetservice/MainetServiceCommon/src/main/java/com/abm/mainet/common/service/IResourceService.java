package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.TbResource;
import com.abm.mainet.common.dto.TbResourceDetDto;
import com.abm.mainet.common.dto.TbResourceMasDto;
import com.abm.mainet.common.utility.Resource;

/**
 * @author sadik.shaikh
 *
 */
public interface IResourceService {
	public Map<String, Map<String,Resource>> getAllResourceDtls();
	
	public List<TbResourceMasDto> getAllTbResourceMas();
	
	public List<TbResourceDetDto> getAllTbResourceDetDto(Long resId);
	
	public TbResource getTbResourceById(Long resId);
	
	public TbResourceMasDto getTbResourceMasDto(Long resId);
	
	public boolean saveTbResourceMas(TbResourceMasDto resourceMasDto);

	Map<String, Map<String, Resource>> getSingleResourceDtls(String pageId);
}
