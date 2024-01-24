/*
package com.abm.mainet.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbResource;
import com.abm.mainet.common.dto.TbResourceDetDto;
import com.abm.mainet.common.dto.TbResourceMasDto;
import com.abm.mainet.common.repository.TbResourceRepository;
import com.abm.mainet.common.utility.Resource;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private TbResourceRepository tbResourceRepository;

	@Override
	public Map<String, Map<String, Resource>> getAllResourceDtls() {
		List<TbResource> resourceList = tbResourceRepository.getAllResources();
		Map<String, Map<String, Resource>> resourceMap = new ConcurrentHashMap<>();
		if (!resourceList.isEmpty())
			resourceList.stream().forEach(res -> {
				String pageId = (String) res.getPageId();
				final Map<String, Resource> fieldsMap = (resourceMap.get(pageId) == null) ? new HashMap<>()
						: resourceMap.get(pageId);
				resourceMap.put(pageId, fieldsMap);

				res.getFieldDetails().forEach(dtls -> {
					String fieldId = (String) dtls.getFieldId();
					String isMandatory = (String) dtls.getIsMandatory();
					String isVisible = (String) dtls.getIsVisible();
					Resource resource = fieldsMap.get(fieldId);
					if (resource == null) {
						resource = new Resource();
						fieldsMap.put(fieldId, resource);
					}
					resource.setFieldId(fieldId);
					resource.setPageId(pageId);
					resource.setMandatory((isMandatory.equals(MainetConstants.FlagY)) ? true : false);
					resource.setVisible((isVisible.equals(MainetConstants.FlagY)) ? true : false);

				});

			});

		return resourceMap;
	}

	@Override public List<TbResourceMasDto> getAllTbResourceMas() { // TODO
  Auto-generated method stub return null; }

	@Override public List<TbResourceDetDto> getAllTbResourceDetDto(Long resId) {
	// TODO Auto-generated method stub return null; }

	@Override public TbResource getTbResourceById(Long resId) { // TODO
  Auto-generated method stub return null; }

	@Override public TbResourceMasDto getTbResourceMasDto(Long resId) { // TODO
  Auto-generated method stub return null; }

	@Override
	public boolean saveTbResourceMas(TbResourceMasDto resourceMasDto) {
		// TODO Auto-generated method stub return false; }

}*/

package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbResource;
import com.abm.mainet.common.domain.TbResourceDtls;
import com.abm.mainet.common.dto.TbResourceDetDto;
import com.abm.mainet.common.dto.TbResourceMasDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.repository.TbResourceRepository;
import com.abm.mainet.common.utility.Resource;

@Service
public class ResourceServiceImpl implements IResourceService {
	private static final Logger LOGGER = Logger.getLogger(ResourceServiceImpl.class);

	@Autowired
	private TbResourceRepository tbResourceRepository;

	@Override
	public Map<String, Map<String, Resource>> getAllResourceDtls() {
		List<TbResource> resourceList = tbResourceRepository.getAllResources();
		Map<String, Map<String, Resource>> resourceMap = new ConcurrentHashMap<>();
		if (!resourceList.isEmpty())
			resourceList.stream().forEach(res -> {
				String pageId = (String) res.getPageId();
				final Map<String, Resource> fieldsMap = (resourceMap.get(pageId) == null) ? new HashMap<>()
						: resourceMap.get(pageId);
				resourceMap.put(pageId, fieldsMap);

				res.getFieldDetails().forEach(dtls -> {
					String fieldId = (String) dtls.getFieldId();
					String isMandatory =  dtls.getIsMandatory();
					String isVisible = dtls.getIsVisible();
					Resource resource = fieldsMap.get(fieldId);
					if (resource == null) {
						resource = new Resource();
						fieldsMap.put(fieldId, resource);
					}
					resource.setFieldId(fieldId);
					resource.setPageId(pageId);
					resource.setMandatory((isMandatory.equals(MainetConstants.FlagY)) ? true : false);
					resource.setVisible((isVisible.equals(MainetConstants.FlagY)) ? true : false);

				});

			});

		return resourceMap;
	}

	@Override
	public Map<String, Map<String, Resource>> getSingleResourceDtls(String pageId) {
		List<TbResource> resourceList = tbResourceRepository.getSingleResourceDtls(pageId);
		Map<String, Map<String, Resource>> resourceMap = new ConcurrentHashMap<>();
		if (!resourceList.isEmpty())
			resourceList.stream().forEach(res -> {
				if (res != null) {
					final Map<String, Resource> fieldsMap = (resourceMap.get(pageId) == null) ? new HashMap<>()
							: resourceMap.get(pageId);
					resourceMap.put(pageId, fieldsMap);

					res.getFieldDetails().forEach(dtls -> {
						if (dtls.getFieldId() != null) {
							String fieldId = (String) dtls.getFieldId();
							String isMandatory = dtls.getIsMandatory();
							String isVisible =  dtls.getIsVisible();
							Resource resource = fieldsMap.get(fieldId);
							if (resource == null) {
								resource = new Resource();
								fieldsMap.put(fieldId, resource);
							}
							resource.setFieldId(fieldId);
							resource.setPageId(pageId);
							resource.setMandatory((isMandatory.equals(MainetConstants.FlagY)) ? true : false);
							resource.setVisible((isVisible.equals(MainetConstants.FlagY)) ? true : false);
						}
					});
				}
			});

		return resourceMap;
	}

	@Override
	public List<TbResourceMasDto> getAllTbResourceMas() {
		List<TbResourceMasDto> masDtos = new ArrayList<TbResourceMasDto>();

		List<TbResource> resourceList = tbResourceRepository.getAllResources();
		List<TbResourceDetDto> detDtos = new ArrayList<TbResourceDetDto>();
		if (resourceList != null) {
			resourceList.forEach(entity -> {
				TbResourceMasDto masterDto = new TbResourceMasDto();
				BeanUtils.copyProperties(entity, masterDto);

				for (TbResourceDtls dtls : entity.getFieldDetails()) {
					TbResourceDetDto resourceDetDto = new TbResourceDetDto();
					BeanUtils.copyProperties(dtls, resourceDetDto);
					detDtos.add(resourceDetDto);

				}
				masterDto.setFieldDetails(detDtos);
				masDtos.add(masterDto);

			});
		}
		return masDtos;
	}

	@Override
	public List<TbResourceDetDto> getAllTbResourceDetDto(Long resId) {

		TbResource tbResource = tbResourceRepository.getResourceMasById(resId);
		List<TbResourceDetDto> detDtos = new ArrayList<TbResourceDetDto>();
		if (tbResource != null) {
			for (TbResourceDtls dtls : tbResource.getFieldDetails()) {
				TbResourceDetDto resourceDetDto = new TbResourceDetDto();
				BeanUtils.copyProperties(dtls, resourceDetDto);
				detDtos.add(resourceDetDto);
			}
		}

		return detDtos;
	}

	@Override
	public TbResource getTbResourceById(Long resId) {

		return null;
	}

	@Override
	public boolean saveTbResourceMas(TbResourceMasDto resourceMasDto) {
		TbResource tbResource = mapDtoToEntity(resourceMasDto);
		try {
			tbResource = tbResourceRepository.save(tbResource);
			return true;

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the squence configuration ", e);
			throw new FrameworkException("Exception occur while saving the squence configuration", e);
		}
	}

	private TbResource mapDtoToEntity(TbResourceMasDto entryDto) {
		TbResource entryEntity = new TbResource();
		List<TbResourceDtls> detEntities = new ArrayList<>();
		BeanUtils.copyProperties(entryDto, entryEntity);
		if (CollectionUtils.isNotEmpty(entryDto.getFieldDetails())) {
			entryDto.getFieldDetails().forEach(det -> {
				TbResourceDtls detDto = new TbResourceDtls();
				BeanUtils.copyProperties(det, detDto);
				detDto.setTbResource(entryEntity);
				detEntities.add(detDto);
			});
		}
		entryEntity.setFieldDetails(detEntities);
		return entryEntity;
	}

	@Override
	public TbResourceMasDto getTbResourceMasDto(Long resId) {
		TbResource tbResource = tbResourceRepository.getResourceMasById(resId);
		TbResourceMasDto resourceMasDto = new TbResourceMasDto();
		BeanUtils.copyProperties(tbResource, resourceMasDto);
		List<TbResourceDetDto> detDtos = new ArrayList<TbResourceDetDto>();
		if (tbResource != null) {
			for (TbResourceDtls dtls : tbResource.getFieldDetails()) {
				TbResourceDetDto resourceDetDto = new TbResourceDetDto();
				BeanUtils.copyProperties(dtls, resourceDetDto);
				detDtos.add(resourceDetDto);
			}
		}
		resourceMasDto.setFieldDetails(detDtos);
		return resourceMasDto;
	}

}
