/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.swm.dao.ISolidWasteConsumerDAO;
import com.abm.mainet.swm.domain.HomeComposeDetails;
import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;
import com.abm.mainet.swm.domain.SolidWasteConsumerMasterHistory;
import com.abm.mainet.swm.dto.HomeComposeDetailsDto;
import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;
import com.abm.mainet.swm.mapper.SolidWasteConsumerMapper;
import com.abm.mainet.swm.repository.SolidWasteConsumerRepository;

/**
 * @author cherupelli.srikanth
 *
 */

@Service
public class HomeCompostingServiceImpl implements IHomeCompostingService {

    @Autowired
    SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    IOrganisationService organisationService;

    @Autowired
    SolidWasteConsumerMapper solidWasteConsumerMapper;

    @Autowired
    SolidWasteConsumerRepository solidWasteConsumerRepository;

    @Autowired
    ISolidWasteConsumerDAO solidWasteConsumerDAO;
    
    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    public SolidWasteConsumerMasterDTO saveHomeComposting(SolidWasteConsumerMasterDTO masterDto) {
	SolidWasteConsumerMasterHistory masterHistory = new SolidWasteConsumerMasterHistory();
	final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SolidWasteManagement.SHORT_CODE,
		"TB_SW_REGISTRATION", "SW_NEW_CUST_ID", masterDto.getOrgid(), MainetConstants.FlagC,
		masterDto.getOrgid());
	Organisation org = organisationService.getOrganisationById(masterDto.getOrgid());
	String customerId = org.getOrgShortNm() + String.format("%06d", sequence);
	masterDto.setSwNewCustId(customerId);
	SolidWasteConsumerMaster master = solidWasteConsumerMapper.mapUserRegistrationDTOToUserRegistration(masterDto);

	List<HomeComposeDetails> tbHomeComposeDetails = new ArrayList<>();
	masterDto.getHomeComposeDetailList().forEach(det -> {
	    HomeComposeDetails detObj = new HomeComposeDetails();
	    BeanUtils.copyProperties(det, detObj);
	    tbHomeComposeDetails.add(detObj);
	});

	master.setTbHomeComposeDetails(tbHomeComposeDetails);

	for (HomeComposeDetails det : master.getTbHomeComposeDetails()) {
	    det.setTbSolidWasteConsumerMaster(master);
	}
	masterHistory.sethStatus(MainetConstants.Transaction.Mode.ADD);
	solidWasteConsumerRepository.save(master);
	auditService.createHistory(master, masterHistory);
	return solidWasteConsumerMapper.mapUserRegistrationToUserRegistrationDTO(master);
    }

    @Override
    @Transactional
    public SolidWasteConsumerMasterDTO updateHomeComposting(SolidWasteConsumerMasterDTO masterDto , List<Long> removeWasteIds) {
	SolidWasteConsumerMasterHistory masterHistory = new SolidWasteConsumerMasterHistory();
	SolidWasteConsumerMaster master = solidWasteConsumerMapper.mapUserRegistrationDTOToUserRegistration(masterDto);

	
	List<HomeComposeDetails> tbHomeComposeDetails = new ArrayList<>();
	masterDto.getHomeComposeDetailList().forEach(det -> {
	    HomeComposeDetails detObj = new HomeComposeDetails();
	    BeanUtils.copyProperties(det, detObj);
	    tbHomeComposeDetails.add(detObj);
	});

	master.setTbHomeComposeDetails(tbHomeComposeDetails);

	for (HomeComposeDetails det : master.getTbHomeComposeDetails()) {
	    det.setTbSolidWasteConsumerMaster(master);
	}
	masterHistory.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
	solidWasteConsumerRepository.save(master);
	auditService.createHistory(master, masterHistory);
	if(CollectionUtils.isNotEmpty(removeWasteIds)) {
	    solidWasteConsumerRepository.deleteAllRecords(removeWasteIds);
	}
	return solidWasteConsumerMapper.mapUserRegistrationToUserRegistrationDTO(master);
    }

    @Override
    public List<SolidWasteConsumerMasterDTO> searchHomeCompost(Long zone, Long ward, Long block, Long route, Long house,
	    Long mobileNo, String name, Long orgId) {
	return (solidWasteConsumerMapper.mapUserRegistrationListToUserRegistrationDTOList(
		solidWasteConsumerDAO.searchCitizenData(zone,ward,block,route,house, mobileNo, name, orgId)));
    }

    @Override
    @GET
    @Path(value = "/get/{id}")
    @Transactional(readOnly = true)
    public SolidWasteConsumerMasterDTO getCitizenByRegistrationId(@PathParam("id") Long registrationId) {
	SolidWasteConsumerMaster master = solidWasteConsumerRepository.findOne(registrationId);

	List<HomeComposeDetailsDto> tbHomeComposeDetailList = new ArrayList<>();
	master.getTbHomeComposeDetails().forEach(det -> {
	    HomeComposeDetailsDto detObj = new HomeComposeDetailsDto();
	    BeanUtils.copyProperties(det, detObj);
	    tbHomeComposeDetailList.add(detObj);
	});
	SolidWasteConsumerMasterDTO mapUserRegistrationToUserRegistrationDTO = solidWasteConsumerMapper
		.mapUserRegistrationToUserRegistrationDTO(master);
	mapUserRegistrationToUserRegistrationDTO.setHomeComposeDetailList(tbHomeComposeDetailList);
	return mapUserRegistrationToUserRegistrationDTO;

    }

}
