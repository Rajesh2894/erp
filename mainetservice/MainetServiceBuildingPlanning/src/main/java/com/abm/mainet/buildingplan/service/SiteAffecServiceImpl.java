package com.abm.mainet.buildingplan.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.buildingplan.dao.DeveloperRegRepository;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseGrantedEntity;
import com.abm.mainet.buildingplan.domain.TbLicPerfSiteAffecEntity;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.buildingplan.dto.LicenseGrantedDTO;
import com.abm.mainet.buildingplan.dto.SiteAffectedDTO;
import com.abm.mainet.buildingplan.repository.LicenseGrantedRepository;
import com.abm.mainet.buildingplan.repository.SiteAffectedRepository;
import com.abm.mainet.common.constant.MainetConstants;

@Service
public class SiteAffecServiceImpl implements ISiteAffecService {

	@Autowired
	SiteAffectedRepository siteAffectedRepository;

	@Autowired
	LicenseGrantedRepository licenseGrantedRepository;
	
	@Resource
	DeveloperRegRepository developerRegRepository;

	@Override
	@Transactional
	public void saveSiteList(List<TbLicPerfSiteAffecEntity> listSiteAffDto) {
		siteAffectedRepository.save(listSiteAffDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SiteAffectedDTO> getSiteDetailsByApplicationId(Long applicationId, String flag) {
		List<TbLicPerfSiteAffecEntity> listSiteAff = new ArrayList<TbLicPerfSiteAffecEntity>();
		List<SiteAffectedDTO> listSiteAffDto = new ArrayList<SiteAffectedDTO>();
		listSiteAff = siteAffectedRepository.getSiteDetailsByApplicationId(applicationId, flag);
		for (TbLicPerfSiteAffecEntity siteAffectDto : listSiteAff) {
			SiteAffectedDTO siteAffDto = new SiteAffectedDTO();
			BeanUtils.copyProperties(siteAffectDto, siteAffDto);
			listSiteAffDto.add(siteAffDto);
		}
		return listSiteAffDto;
	}

	@Override
	@Transactional
	public void saveLicenseData(List<LicenseGrantedEntity> listLicenseDto) {
		licenseGrantedRepository.save(listLicenseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LicenseGrantedDTO> getLicenseDetailsByApplicationId(Long applicationId) {
		List<LicenseGrantedEntity> listLicenseEntity = new ArrayList<LicenseGrantedEntity>();
		List<LicenseGrantedDTO> listLicenseDto = new ArrayList<LicenseGrantedDTO>();
		listLicenseEntity = licenseGrantedRepository.getLicenseDetailsByApplicationId(applicationId);
		for (LicenseGrantedEntity licenseEntity : listLicenseEntity) {
			LicenseGrantedDTO licenseDto = new LicenseGrantedDTO();
			BeanUtils.copyProperties(licenseEntity, licenseDto);
			listLicenseDto.add(licenseDto);
		}
		return listLicenseDto;
	}
	
	
	  @Override
	    @Transactional(readOnly = true)
	    @WebMethod(exclude = true)
	    public Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> getLandDetailsByAppId(final Long applicationId, final Long orgId) {
	        final List<LicenseApplicationLandAcquisitionDetEntity> list = developerRegRepository.getLandListByAppId(applicationId, orgId);
	        final List<LicenseApplicationLandAcquisitionDetailDTO> landList = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
	        Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> mapData = new HashMap<>(0);
	        if(!CollectionUtils.isEmpty(list)) {
	        for (LicenseApplicationLandAcquisitionDetEntity landEntity : list) {
	        	LicenseApplicationLandAcquisitionDetailDTO landDto = new LicenseApplicationLandAcquisitionDetailDTO();
				BeanUtils.copyProperties(landEntity, landDto);
				 LicenseApplicationMasterDTO licenseApplicationMaster=new LicenseApplicationMasterDTO();
				BeanUtils.copyProperties(landEntity.getLicenseApplicationMaster(), licenseApplicationMaster);
				landDto.setLicenseApplicationMaster(licenseApplicationMaster);
				if(landDto.getKanal()!=null) {
					landDto.setbArea(landDto.getKanal().toString()+MainetConstants.HYPHEN+landDto.getMarla().toString()+MainetConstants.HYPHEN+landDto.getSarsai().toString());
					landDto.setType("C");
					if(StringUtils.isAllBlank(landDto.getcBigha())) {
						landDto.setcBigha(landDto.getKanal().toString());
					} if(StringUtils.isAllBlank(landDto.getcBiswa())) {
						landDto.setcBiswa(landDto.getMarla().toString());
					} if(StringUtils.isAllBlank(landDto.getcBiswansi())) {
						landDto.setcBiswansi(landDto.getSarsai().toString());
					}
				}else {
					landDto.setbArea(landDto.getBigha()+MainetConstants.HYPHEN+landDto.getBiswa()+MainetConstants.HYPHEN+landDto.getBiswansi());
					landDto.setType("N");
					if(StringUtils.isAllBlank(landDto.getcBigha())) {
						landDto.setcBigha(landDto.getBigha().toString());
					} if(StringUtils.isAllBlank(landDto.getcBiswa())) {
						landDto.setcBiswa(landDto.getBiswa().toString());
					} if(StringUtils.isAllBlank(landDto.getcBiswansi())) {
						landDto.setcBiswansi(landDto.getBiswansi().toString());
					}
				}
				landList.add(landDto);
			}
	        
	        landList.forEach(landDto -> {
	        	List<LicenseApplicationLandAcquisitionDetailDTO> helperLandList;
	        	if(mapData.containsKey(landDto.getLandOwnerName())) {
		        	helperLandList = mapData.get(landDto.getLandOwnerName());
	        	} else {
		        	helperLandList = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
	        	}	        	
	        	helperLandList.add(landDto);
	        	mapData.put(landDto.getLandOwnerName(), helperLandList);
	        	
	        });
	       }
	        
	        return mapData;
	    }
	  
	@Transactional(readOnly = true)
	@Override
	public List<SiteAffectedDTO> getApplicationNotingDetail(final Long applicationId, Long orgId, Long level) {
		List<TbLicPerfSiteAffecEntity> listSiteAff = siteAffectedRepository.getApplicationNotingDetail(applicationId,level);
		List<SiteAffectedDTO> listSiteAffDto = new ArrayList<SiteAffectedDTO>();
		for (TbLicPerfSiteAffecEntity siteAffectDto : listSiteAff) {
			SiteAffectedDTO siteAffDto = new SiteAffectedDTO();
			BeanUtils.copyProperties(siteAffectDto, siteAffDto);
			listSiteAffDto.add(siteAffDto);
		}
		return listSiteAffDto;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<LicenseGrantedDTO> getApplicationNotingDetailLicense(final Long applicationId, Long orgId, Long level) {
		List<LicenseGrantedEntity> listSiteAff = licenseGrantedRepository.getApplicationNotingDetailLicense(applicationId,level);
		List<LicenseGrantedDTO> listSiteAffDto = new ArrayList<LicenseGrantedDTO>();
		for (LicenseGrantedEntity siteAffectDto : listSiteAff) {
			LicenseGrantedDTO siteAffDto = new LicenseGrantedDTO();
			BeanUtils.copyProperties(siteAffectDto, siteAffDto);
			listSiteAffDto.add(siteAffDto);
		}
		return listSiteAffDto;
	}
	

}
