package com.abm.mainet.bnd.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abm.mainet.bnd.domain.CemeteryMaster;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.SortCemeteryByEnglish;
import com.abm.mainet.bnd.dto.SortCemeteryByMarathi;
import com.abm.mainet.bnd.repository.CemeteryMasterRepository;
import com.abm.mainet.common.utility.UserSession;

@Service
public class CemeteryMasterService implements ICemeteryMasterService {

	@Autowired
	private CemeteryMasterRepository cemeteryMasterRepository;

	@Override
	public List<CemeteryMasterDTO> getAllCemetery(Long orgId) {
		
		List<CemeteryMaster> listOfCemetryDtl = cemeteryMasterRepository.findByOrgid(orgId);
		List<CemeteryMasterDTO> dtoList=new ArrayList<CemeteryMasterDTO>();	
		
		if(!listOfCemetryDtl.isEmpty()) {
			if(String.valueOf(UserSession.getCurrent().getLanguageId()).equals("1")) {
				Collections.sort(listOfCemetryDtl, new SortCemeteryByEnglish());
			}else {
				Collections.sort(listOfCemetryDtl, new SortCemeteryByMarathi());
			}
			
		listOfCemetryDtl.forEach(entity->{
			if((entity.getCeNameMar()!=null)) {
			CemeteryMasterDTO dto = new CemeteryMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
			}
		});
		}
		return dtoList;
		
	}
	
	@Override
	public void saveCemetry(CemeteryMasterDTO cemeteryMasterDTO) {
		CemeteryMaster cemeteryMaster = new CemeteryMaster();
		BeanUtils.copyProperties(cemeteryMasterDTO, cemeteryMaster);
		cemeteryMasterRepository.save(cemeteryMaster);

	}
	
	@Override
	public List<CemeteryMasterDTO> findByCemeteryNameAndCemeteryType(String ceName, Long cpdTypeId, Long orgId) {
		List<CemeteryMasterDTO>  listDTO=new ArrayList<CemeteryMasterDTO>();
		List<CemeteryMaster> list = null;
		if(StringUtils.isNotEmpty(ceName) && (null != cpdTypeId && cpdTypeId > 0L)) {
			list =	cemeteryMasterRepository.findByOrgidAndCeNameLikeAndCpdTypeId(orgId, ceName, cpdTypeId);
		}else if(StringUtils.isEmpty(ceName)) {
			list =	cemeteryMasterRepository.findByOrgidAndCpdTypeId(orgId, cpdTypeId);
		}else if(null == cpdTypeId || cpdTypeId == 0L){
			list =	cemeteryMasterRepository.findByOrgidAndCeNameLike(orgId, ceName);
		}else {
			list =	cemeteryMasterRepository.findByOrgid(orgId);
		}
		list.forEach(obj->{
			CemeteryMasterDTO dto=new CemeteryMasterDTO();
			BeanUtils.copyProperties(obj, dto);
			listDTO.add(dto);
		});
	
		return listDTO;
	}


	@Override
	public CemeteryMasterDTO getCemeteryById(Long ceId) {
		CemeteryMaster cmeteryMaster = cemeteryMasterRepository.findOne(ceId);
		CemeteryMasterDTO cemeteryMasterDTO = new CemeteryMasterDTO();
		BeanUtils.copyProperties(cmeteryMaster, cemeteryMasterDTO);
		return cemeteryMasterDTO;
	}


	@Override
	public List<CemeteryMasterDTO> getAllCemeteryList(Long orgId) {
		
		List<CemeteryMaster> listOfCemetryDtl = cemeteryMasterRepository.findByOrgid(orgId);
		List<CemeteryMasterDTO> dtoList=new ArrayList<CemeteryMasterDTO>();	
		
		if(!listOfCemetryDtl.isEmpty()) {
		listOfCemetryDtl.forEach(entity->{
			if((entity.getCeNameMar()!=null)) {
			CemeteryMasterDTO dto = new CemeteryMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
			}
		});
		}
		return dtoList;
		
	}

}
