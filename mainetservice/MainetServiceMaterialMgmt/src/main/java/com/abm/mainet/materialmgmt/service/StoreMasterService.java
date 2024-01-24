package com.abm.mainet.materialmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dao.StoreMasterDao;
import com.abm.mainet.materialmgmt.domain.StoreMaster;
import com.abm.mainet.materialmgmt.domain.StoreMasterHistory;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.mapper.StoreMasterMapper;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

/**
 * @author Ajay Kumar
 *
 */
@Service
public class StoreMasterService implements IStoreMasterService{
	
	
	@Autowired
	private StoreMasterRepository storeMasterRepository;
	
	@Autowired
	private StoreMasterMapper storeMasterMapper;

    @Autowired
    private AuditService auditService;
    
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
    
    @Autowired
    private StoreMasterDao storeMasterDao;
   
    private static final String SEQUENCE_NO = "S";
	
	/* (non-Javadoc)
	 * @see com.abm.mainet.materialmgmt.service.IStoreMasterService#saveStore(com.abm.mainet.materialmgmt.dto.StoreMasterDTO)
	 */
	@Override
    @Transactional
	public StoreMasterDTO saveStore(StoreMasterDTO storeMasterDTO) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String ulb = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		if (storeMasterDTO.getStoreId() == null) {
			long storeCodeNumber = seqGenFunctionUtility.generateSequenceNo(MainetMMConstants.MmItemMaster.STR,
					MainetMMConstants.MmItemMaster.MM_STOREMASTER, MainetMMConstants.MmItemMaster.MM_STORECODE, orgId,
					MainetConstants.MODE_CREATE, null);
			final StringBuilder storeCodeNo = new StringBuilder();
			storeMasterDTO.setStoreCode(storeCodeNo.append(MainetMMConstants.MmItemMaster.STR).append(ulb)
					.append(String.format("%03d", storeCodeNumber)).toString());
		}
		StoreMaster storemaster = storeMasterMapper.mapStoreMasterDTOToStoreMaster(storeMasterDTO);
		storemaster = storeMasterRepository.save(storemaster);
		StoreMasterHistory masterHistory = new StoreMasterHistory();
		masterHistory.setStatus('A');
		auditService.createHistory(storemaster, masterHistory);
		return storeMasterMapper.mapStoreMasterToStoreMasterDTO(storemaster);
	}
	
	
	
	@Override
    @Transactional
	public void saveStoreExcelData(List<StoreMasterDTO> storeMasterDTOlist) {
		storeMasterDTOlist.forEach(storeMasterDTO -> {
			long storeCodeNumber;
		   	  final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		   	  String ulb=UserSession.getCurrent().getOrganisation().getOrgShortNm();
		   	  storeCodeNumber = seqGenFunctionUtility.generateSequenceNo(
		       		  MainetMMConstants.MmItemMaster.STR,
		       		  MainetMMConstants.MmItemMaster.MM_STOREMASTER,
		       		  MainetMMConstants.MmItemMaster.MM_STORECODE, orgId,
		                 MainetConstants.MODE_CREATE, null);
		         final StringBuilder storeCodeNo = new StringBuilder();
		         storeCodeNo.append(SEQUENCE_NO).append(storeCodeNumber).toString();
		        storeMasterDTO.setStoreCode(MainetMMConstants.MmItemMaster.STR+ulb+storeCodeNumber);
				StoreMaster storemaster=storeMasterMapper.mapStoreMasterDTOToStoreMaster(storeMasterDTO);
				storemaster = storeMasterRepository.save(storemaster);
		        StoreMasterHistory masterHistory = new StoreMasterHistory();
		        masterHistory.setStatus('A');
		       // auditService.createHistory(storemaster, masterHistory);
	        });	
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.materialmgmt.service.IStoreMasterService#getStoreMasterByStoreId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public StoreMasterDTO getStoreMasterByStoreId(Long storeId) {
		return storeMasterMapper.mapStoreMasterToStoreMasterDTO(storeMasterRepository.findAllStoreByStoreid(storeId));
	}

	
	/* (non-Javadoc)
	 * @see com.abm.mainet.materialmgmt.service.IStoreMasterService#serchStoreMasterDataByOrgid(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<StoreMasterDTO> serchStoreMasterDataByOrgid(Long orgId) {
		List<StoreMaster> list=storeMasterRepository.getAllStoreDataByOrgId(orgId);
		return  storeMasterMapper.mapStoreMasterListToStoreMasterDTOList(list);
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.materialmgmt.service.IStoreMasterService#serchStoreByRouteTypeAndRouteNo(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<StoreMasterDTO> serchStoreByLocAndStoreName(Long locationId, String storeName, Long orgId) {
		List<StoreMaster> list=storeMasterDao.searhStoreMasterData(locationId, storeName, orgId);
		//List<StoreMaster> list=storeMasterRepository.findByLocIdAndStoreName(locationId,storeName,orgId);
		return storeMasterMapper.mapStoreMasterListToStoreMasterDTOList(list);
	}

	@Override
	@Transactional(readOnly = true)
	public String getStorenameByStoreId(Long storeId) {
		return storeMasterRepository.findStorenameByStoreid(storeId);
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getStoreIdAndNameList(Long orgId) {
		return storeMasterRepository.getStoreIdAndNameList(orgId);
	}


	@Override
	@Transactional(readOnly = true)
	public Object[] getStoreDetailsByStore(Long storeId, Long orgId) {
		return storeMasterRepository.getStoreDetailsByStore(storeId, orgId);
	}

	@Override
	@Transactional
	public List<Object[]> getActiveStoreObjectListForAdd(Long orgId) {
		return storeMasterRepository.getActiveStoreObjectListForAdd(orgId, MainetConstants.CommonConstants.CHAR_Y);
	}	
	
	@Override
	@Transactional
	public Object[] getStoreLocationAddress(Long storeId, Long orgId) {
		return storeMasterRepository.getStoreLocationAddress(storeId, orgId);
	}
}
