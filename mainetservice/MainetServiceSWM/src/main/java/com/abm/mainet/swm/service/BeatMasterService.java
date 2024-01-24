package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.swm.dao.IBeatMasterDAO;
import com.abm.mainet.swm.domain.BeatDetail;
import com.abm.mainet.swm.domain.BeatMaster;
import com.abm.mainet.swm.domain.BeatMasterHistory;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.mapper.RouteMasterMapper;
import com.abm.mainet.swm.repository.BeatMasterRepository;

/**
 * The Class RouteMasterServiceImpl.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 07-May-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IBeatMasterService")
@Path(value = "/routemasterservice")
public class BeatMasterService implements IBeatMasterService {

    /** The route master repository. */
    @Autowired
    private BeatMasterRepository beatMasterRepository;

    @Autowired
    private IBeatMasterDAO routeMasterDAO;

    /** The route master mapper. */
    @Autowired
    private RouteMasterMapper routeMasterMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /**
     * The SeqGenFunctionUtility service
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The IOrganisationService service
     */
    @Autowired
    private IOrganisationService organisationService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.RouteMasterService#getRouteByRouteId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public BeatMasterDTO getRouteByRouteId(Long routeId) {
        return routeMasterMapper.mapRouteMasterToRouteMasterDTO(beatMasterRepository.findOne(routeId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.RouteMasterService#saveRoute(com.abm.mainet.swm.dto.RouteMasterDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public BeatMasterDTO saveRoute(BeatMasterDTO route) {

        Organisation org = organisationService.getOrganisationById(route.getOrgid());

        final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SolidWasteManagement.SHORT_CODE,
                "TB_SW_BEAT_MAST", "BEAT_ID", route.getOrgid(),
                MainetConstants.FlagC, route.getOrgid());

        String beatName = "BT" + MainetConstants.HYPHEN + org.getOrgShortNm() + MainetConstants.HYPHEN
                + String.format("%03d", sequenceNo);

        BeatMaster master = routeMasterMapper.mapRouteMasterDTOToRouteMaster(route);
        for (BeatDetail det : master.getTbSwBeatDetail()) {
            det.setTbSwBeatMaster(master);  
	}
        master.setBeatNo(beatName);
        master = beatMasterRepository.save(master);
        
        BeatMasterHistory masterHistory = new BeatMasterHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);
        return routeMasterMapper.mapRouteMasterToRouteMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.RouteMasterService#updateRoute(com.abm.mainet.swm.dto.RouteMasterDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public BeatMasterDTO updateRoute(BeatMasterDTO routeIdDetails, List<Long> removeAreaIds) {
        BeatMaster master = routeMasterMapper.mapRouteMasterDTOToRouteMaster(routeIdDetails);
        for (BeatDetail det : master.getTbSwBeatDetail()) {
            det.setTbSwBeatMaster(master);  
	}
        master = beatMasterRepository.save(master);
        
        BeatMasterHistory masterHistory = new BeatMasterHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        if(CollectionUtils.isNotEmpty(removeAreaIds)) {
            beatMasterRepository.deleteAllRecords(removeAreaIds);
	}
        return routeMasterMapper.mapRouteMasterToRouteMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.RouteMasterService#deleteRoute(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void deleteRoute(Long routeId, Long empId, String ipMacAdd) {
        BeatMaster master = beatMasterRepository.findOne(routeId);
        master.setBeatActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        beatMasterRepository.save(master);
        BeatMasterHistory masterHistory = new BeatMasterHistory();
        masterHistory.sethStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.RouteMasterService#serchRouteByRouteTypeAndRouteNo(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/search")
    public List<BeatMasterDTO> serchRouteByRouteTypeAndRouteNo(@QueryParam(value = "routeName") String routeName,
            @QueryParam(value = "routeNo") String routeNo, @QueryParam(value = "orgId") Long orgId) {
        return routeMasterMapper.mapRouteMasterListToRouteMasterDTOList(
                routeMasterDAO.serchRouteByRouteTypeAndRouteNo(null, routeName, MainetConstants.FlagY, routeNo, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBeatMasterService#serchRouteByOrgid(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @POST
    @Path(value = "/search/{orgId}")
    public List<BeatMasterDTO> serchRouteByOrgid(@PathParam(value = "orgId") Long orgId) {
        return routeMasterMapper.mapRouteMasterListToRouteMasterDTOList(
                routeMasterDAO.serchRouteByRouteTypeAndRouteNo(null, null, MainetConstants.FlagY, null, orgId));
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<BeatMasterDTO> serchRoute(String routeName, String routeNo, Long orgId) {
        return routeMasterMapper.mapRouteMasterListToRouteMasterDTOList(
                routeMasterDAO.serchRouteByRouteTypeAndRouteNo(null, routeName, null, routeNo, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBeatMasterService#deleteRouteDetails(java.lang.String, java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void deleteRouteDetails(String status, Long empId, String ipMacAdd, Long id) {
        // TODO Auto-generated method stub
        beatMasterRepository.deleteRouteDetails(status, empId, ipMacAdd, id);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBeatMasterService#validateRoute(com.abm.mainet.swm.dto.BeatMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public boolean validateRoute(BeatMasterDTO beatMasterDTO) {

        Assert.notNull(beatMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(beatMasterDTO.getBeatName(), "Beat Name Can Not Be Empty");
        Assert.notNull(beatMasterDTO.getBeatNo(), "Beat Number Can Not Be Empty");

        List<BeatMaster> routeList = routeMasterDAO.serchRouteByRouteTypeAndRouteNo(beatMasterDTO.getBeatId(),
                beatMasterDTO.getBeatName(), null, beatMasterDTO.getBeatNo(),
                beatMasterDTO.getOrgid());

        return routeList.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBeatMasterService#getAllRouteNo(long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<BeatMasterDTO> getAllRouteNo(long orgid) {
        List<BeatMasterDTO> masList = new ArrayList<>();
        List<BeatMaster> entityList = beatMasterRepository.findAllRouteNoByOrgId(orgid);
        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                BeatMasterDTO masDto = new BeatMasterDTO();
                BeanUtils.copyProperties(entity, masDto);
                masList.add(masDto);
            });
        }
        return masList;

    }

}
