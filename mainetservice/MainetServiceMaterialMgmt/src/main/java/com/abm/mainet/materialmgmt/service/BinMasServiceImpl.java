package com.abm.mainet.materialmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.materialmgmt.domain.BinDefMasEntity;
import com.abm.mainet.materialmgmt.domain.BinLocDefMapping;
import com.abm.mainet.materialmgmt.domain.BinLocMasEntity;
import com.abm.mainet.materialmgmt.dto.BinDefMasDto;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.repository.BinDefMasRepository;
import com.abm.mainet.materialmgmt.repository.BinLocMasRepository;


@Service
public class BinMasServiceImpl implements IBinMasService {
	
	
	@Autowired
	private BinDefMasRepository binDefMasRepository;
	
	@Autowired
	private BinLocMasRepository binLocMasRepository;
	

	@Override
	@Transactional
	public void save(BinDefMasDto binDefMasDto) {
		BinDefMasEntity entity =new BinDefMasEntity();
		BeanUtils.copyProperties(binDefMasDto, entity);
		binDefMasRepository.save(entity);
	}

	@Override
	@Transactional
	public void saveBinLocation(BinLocMasDto binLocMasDto) {
        BinLocMasEntity locMasEntity = new BinLocMasEntity();
        String [] defs =binLocMasDto.getDefinitions().split(",");
        List<BinLocDefMapping> mappingList =new ArrayList<>();
       // List<Long> removeMappingIds =new ArrayList<>();
        //List<String> addDefIds =new ArrayList<>();
        BinLocDefMapping mapping;
        BinDefMasEntity defMas;
        
        for(String def : defs) {
             	defMas = new BinDefMasEntity();
             	mapping =new BinLocDefMapping();
             	defMas.setBinDefId(Long.valueOf(def));
     			mapping.setBinDefMasEntity(defMas);
     			mappingList.add(mapping);
     		}
       
        BeanUtils.copyProperties(binLocMasDto, locMasEntity);
        locMasEntity.setMappingList(mappingList);
		binLocMasRepository.save(locMasEntity);
	}

	@Override
	@Transactional(readOnly=true)
	public List<BinDefMasDto> findAllBinDefintion(Long orgId) {
		List<BinDefMasEntity> entities = binDefMasRepository.findByOrgId(orgId);
		BinDefMasDto dto =null;
		List<BinDefMasDto> list = new ArrayList<BinDefMasDto>();
		for (BinDefMasEntity binDefMasEntity : entities) {
			dto =new BinDefMasDto();	
			BeanUtils.copyProperties(binDefMasEntity, dto);
			list.add(dto);
		}
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public BinDefMasDto findByBinDefID(Long bindefId) {
		BinDefMasEntity entity = binDefMasRepository.findOne(bindefId);
		BinDefMasDto dto =new BinDefMasDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<BinLocMasDto> findAllBinLocation(Long orgId) {
       List<BinLocMasEntity> entities =binLocMasRepository.findByOrgId(orgId);
       BinLocMasDto dto =null;
		List<BinLocMasDto> list = new ArrayList<BinLocMasDto>();
		for (BinLocMasEntity binDefMasEntity : entities) {
			dto =new BinLocMasDto();	
			BeanUtils.copyProperties(binDefMasEntity, dto);
			list.add(dto);
		}
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public BinLocMasDto findByBinLocID(Long binlocId) {
		BinLocMasEntity entity =binLocMasRepository.findOne(binlocId);
		Hibernate.initialize(entity.getMappingList());
        BinLocMasDto dto=new BinLocMasDto();
        List<BinLocDefMapping> mapping =entity.getMappingList();
        StringJoiner joiner = new StringJoiner(",");
        Map<Long,Long> map=new HashMap<>();
        for (BinLocDefMapping binLocDefMapping : mapping) {
        	joiner.add(binLocDefMapping.getBinDefMasEntity().getBinDefId().toString());
        	map.put(binLocDefMapping.getMapId(), binLocDefMapping.getBinDefMasEntity().getBinDefId());
		}
        dto.setDefinitions(joiner.toString());
        dto.setLocMapping(map);
        BeanUtils.copyProperties(entity, dto);
        return dto;
	}

	@Override
	@Transactional
	public void deleteEmptyLocId() {
		binDefMasRepository.deleteByDefMappingId();
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> getbinLocIdAndNameListByOrgId(Long orgId) {
		return binLocMasRepository.getbinLocIdAndNameListByOrgId(orgId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Object[]> getbinLocIdAndNameListByStore(Long storeId, Long orgId) {
		return binLocMasRepository.getbinLocIdAndNameListByStore(storeId, orgId);
	}	
	

}
