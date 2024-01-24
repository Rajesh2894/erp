package com.abm.mainet.swm.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IWastageSegregationDAO;
import com.abm.mainet.swm.domain.WastageSegregation;
import com.abm.mainet.swm.domain.WastageSegregationHistory;
import com.abm.mainet.swm.dto.WastageSegregationDTO;
import com.abm.mainet.swm.mapper.WastageSegregationMapper;
import com.abm.mainet.swm.repository.WastageSegregationRepository;

/**
 * The Class WastageSegregationServiceImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IWastageSegregationService")
@Path(value = "/wastagesegregationservice")
public class WastageSegregationService implements IWastageSegregationService {

    private static Logger log = Logger.getLogger(WastageSegregationService.class);

    /** The wastage segregation repository. */
    @Autowired
    private WastageSegregationRepository wastageSegregationRepository;

    /** The wastage segregation DAO. */
    @Autowired
    private IWastageSegregationDAO wastageSegregationDAO;

    /** The wastage segregation mapper. */
    @Autowired
    private WastageSegregationMapper wastageSegregationMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.WastageSegregationService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void delete(Long wastageSegregationId, Long empId, String ipMacAdd) {
        WastageSegregation master = wastageSegregationRepository.findOne(wastageSegregationId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        wastageSegregationRepository.save(master);
        saveWastageSegregationHistory(master, MainetConstants.Transaction.Mode.DELETE);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.WastageSegregationService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public WastageSegregationDTO getById(@PathParam("id") Long wastageSegregationId) {
        return wastageSegregationMapper
                .mapWastageSegregationToWastageSegregationDTO(
                        wastageSegregationRepository.findOne(wastageSegregationId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.WastageSegregationService#save(com.abm.mainet.swm.dto.WastageSegregationDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public WastageSegregationDTO save(@RequestBody WastageSegregationDTO wastageSegregationDetails) {
        WastageSegregation master = mapped(wastageSegregationDetails);
        master = wastageSegregationRepository.save(master);
        saveWastageSegregationHistory(master, MainetConstants.Transaction.Mode.ADD);
        return wastageSegregationMapper.mapWastageSegregationToWastageSegregationDTO(master);
    }

    /**
     * Mapped.
     *
     * @param wastageSegregationDetails the wastage segregation details
     * @return the wastage segregation
     */
    private WastageSegregation mapped(WastageSegregationDTO wastageSegregationDetails) {
        WastageSegregation master = wastageSegregationMapper
                .mapWastageSegregationDTOToWastageSegregation(wastageSegregationDetails);
        return master;
    }

    /**
     * Save wastage segregation history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveWastageSegregationHistory(WastageSegregation master, String status) {
        try {
            WastageSegregationHistory masterHistory = new WastageSegregationHistory();
            masterHistory.setHStatus(status);
            auditService.createHistory(master, masterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + master, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.WastageSegregationService#update(com.abm.mainet.swm.dto.WastageSegregationDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public WastageSegregationDTO update(WastageSegregationDTO wastageSegregationDetails) {
        WastageSegregation master = mapped(wastageSegregationDetails);
        master = wastageSegregationRepository.save(master);
        saveWastageSegregationHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return wastageSegregationMapper.mapWastageSegregationToWastageSegregationDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.WastageSegregationService#search(java.lang.Long, java.util.Date, java.util.Date,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<WastageSegregationDTO> search(Long deId, Date fromDate, Date toDate, Long orgId) {
        return wastageSegregationMapper.mapWastageSegregationListToWastageSegregationDTOList(
                wastageSegregationDAO.searchWastageSegregation(deId, fromDate, toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWastageSegregationService#findWastageSegregation(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public WastageSegregationDTO findWastageSegregation(Long OrgId, Long deId, Long codWast1, Long codWast2, Long codWast3,
            Date fromDate, Date toDate) {
        List<Object[]> WstSegregationDetails = wastageSegregationRepository.findWastageSegregationReportBy(OrgId, deId,
                codWast1, codWast2, codWast3, fromDate, toDate);
        BigDecimal sumofVolume = new BigDecimal(0.00);
        WastageSegregationDTO wastageSegregationDTO = null;
        WastageSegregationDTO wstsegregationDto = null;
        List<WastageSegregationDTO> wastageSegregationList = new ArrayList<>();
        if (WstSegregationDetails != null && !WstSegregationDetails.isEmpty()) {
            wastageSegregationDTO = new WastageSegregationDTO();
            for (Object[] WstSegregationDTO : WstSegregationDetails) {
                wstsegregationDto = new WastageSegregationDTO();
                wstsegregationDto.setdName(WstSegregationDTO[0].toString());
                wstsegregationDto.setFromDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) WstSegregationDTO[1]));
                wstsegregationDto.setWaste(WstSegregationDTO[2].toString());
                wstsegregationDto.setSubTypeWaste(WstSegregationDTO[3].toString());
                wstsegregationDto.setSubTypeWaste2(WstSegregationDTO[4].toString());
                wstsegregationDto.setVolume(new BigDecimal(WstSegregationDTO[7].toString()).setScale(3, RoundingMode.HALF_EVEN));
                sumofVolume = sumofVolume
                        .add(new BigDecimal(wstsegregationDto.getVolume().toString()).setScale(3, RoundingMode.HALF_EVEN));
                wastageSegregationList.add(wstsegregationDto);
            }
            wastageSegregationDTO.setTotalVolume(new BigDecimal(sumofVolume.toString()).setScale(3, RoundingMode.HALF_EVEN));
            wastageSegregationDTO.setWastageSegregationList(wastageSegregationList);
        }

        return wastageSegregationDTO;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public WastageSegregationDTO findSlrmWiseSegregation(Long OrgId, Long mrfId, Long monthNo) {
        List<Object[]> slrmSegregationDetails = wastageSegregationRepository.findSlrmWastageSegregationReportBy(OrgId, mrfId,
                monthNo);
        WastageSegregationDTO slrmDetailsDto = null;
        WastageSegregationDTO slrmsegregationDto = null;
        List<WastageSegregationDTO> slrmSegregationList = new ArrayList<>();
        if (slrmSegregationDetails != null && !slrmSegregationDetails.isEmpty()) {
            slrmDetailsDto = new WastageSegregationDTO();
            for (Object[] slrmdet : slrmSegregationDetails) {
                slrmsegregationDto = new WastageSegregationDTO();
                slrmsegregationDto.setMrfCenterName(slrmdet[1].toString());
                slrmsegregationDto.setDate(Long.valueOf(slrmdet[2].toString()));
                slrmsegregationDto.setMonth(slrmdet[3].toString());
                slrmsegregationDto.setYear(slrmdet[4].toString());
                slrmsegregationDto.setWaste(slrmdet[5].toString());
                slrmsegregationDto.setTotalDry(slrmdet[6].toString());
                slrmsegregationDto.setTotalWet(slrmdet[7].toString());
                slrmsegregationDto.setTotalHazardius(slrmdet[8].toString());
                slrmSegregationList.add(slrmsegregationDto);
            }
            slrmDetailsDto.setWastageSegregationList(slrmSegregationList);
        }
        return slrmDetailsDto;
    }
}
