/**
 *
 */
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
import javax.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dao.ITripSheetDAO;
import com.abm.mainet.swm.domain.TripSheet;
import com.abm.mainet.swm.domain.TripSheetGarbageDetHistory;
import com.abm.mainet.swm.domain.TripSheetHistory;
import com.abm.mainet.swm.dto.TripSheetDTO;
import com.abm.mainet.swm.mapper.TripSheetMapper;
import com.abm.mainet.swm.repository.TripSheetRepository;

import io.swagger.annotations.Api;

/**
 * The Class TripSheetServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.ITripSheetService")
@Path(value = "/tripsheetservice")
@Api(value = "/tripsheetservice")
public class TripSheetService implements ITripSheetService {

	/** The trip sheet repository. */
	@Autowired
	private TripSheetRepository tripSheetRepository;

	/** The trip sheet DAO. */
	@Autowired
	private ITripSheetDAO tripSheetDAO;

	/** The trip sheet mapper. */
	@Autowired
	private TripSheetMapper tripSheetMapper;

	/** The audit service. */
	@Autowired
	private AuditService auditService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.TripSheetService#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void delete(Long tripSheetId, Long empId, String ipMacAdd) {
		TripSheet master = tripSheetRepository.findOne(tripSheetId);
		// TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
		master.setUpdatedBy(empId);
		master.setUpdatedDate(new Date());
		master.setLgIpMacUpd(ipMacAdd);
		tripSheetRepository.save(master);
		saveTripSheetHistory(master, MainetConstants.Transaction.Mode.DELETE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.TripSheetService#getById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{id}")
	public TripSheetDTO getById(@PathParam("id") Long tripSheet) {
		return tripSheetMapper.mapTripSheetToTripSheetDTO(tripSheetRepository.findOne(tripSheet));
	}

	/**
	 * Mapped.
	 *
	 * @param tripSheetDetails the trip sheet details
	 * @return the trip sheet
	 */
	private TripSheet mapped(TripSheetDTO tripSheetDetails) {
		TripSheet master = tripSheetMapper.mapTripSheetDTOToTripSheet(tripSheetDetails);
		return master;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.TripSheetService#save(com.abm.mainet.swm.dto.
	 * TripSheetDTO)
	 */
	@Override
	@Transactional
	@POST
	@Path(value = "/save")
	public TripSheetDTO save(@RequestBody TripSheetDTO tripSheetDetails) {
		TripSheet master = mapped(tripSheetDetails);
		master = tripSheetRepository.save(master);
		saveTripSheetHistory(master, MainetConstants.Transaction.Mode.ADD);
		return tripSheetMapper.mapTripSheetToTripSheetDTO(master);
	}

	/**
	 * Save vendor history.
	 *
	 * @param master the master
	 * @param status the status
	 */
	private void saveTripSheetHistory(TripSheet master, String status) {
		TripSheetHistory masterHistory = new TripSheetHistory();
		masterHistory.setHStatus(status);
		auditService.createHistory(master, masterHistory);
		// insert in TB_SW_TRIPSHEET_GDET_HIST
		List<Object> hisotyList = new ArrayList<Object>();
		master.getTbSwTripsheetGdets().parallelStream().forEach(tripDetails -> {
			TripSheetGarbageDetHistory detHistory = new TripSheetGarbageDetHistory();
			detHistory.sethStatus(status);
			BeanUtils.copyProperties(tripDetails, detHistory);
			detHistory.setTripId(tripDetails.getTbSwTripsheet().getTripId());
			detHistory.sethStatus(status);
			hisotyList.add(detHistory);
		});
		auditService.createHistoryForListObj(hisotyList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.TripSheetService#update(com.abm.mainet.swm.dto.
	 * TripSheetDTO)
	 */
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public TripSheetDTO update(TripSheetDTO tripSheetDetails) {
		TripSheet master = mapped(tripSheetDetails);
		master = tripSheetRepository.save(master);
		saveTripSheetHistory(master, MainetConstants.Transaction.Mode.UPDATE);
		return tripSheetMapper.mapTripSheetToTripSheetDTO(master);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.TripSheetService#search(java.lang.Long,
	 * com.abm.mainet.swm.service.Date, com.abm.mainet.swm.service.Date)
	 */
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public List<TripSheetDTO> search(Long veId, Date fromDate, Date toDate, Long orgId) {
		return tripSheetMapper
				.mapTripSheetListToTripSheetDTOList(tripSheetDAO.searchTripSheet(veId, fromDate, toDate, orgId));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.ITripSheetService#findTripSheetReport(java.lang.
	 * Long, java.lang.Long, java.util.Date, java.util.Date, java.lang.Long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public TripSheetDTO findTripSheetReport(Long OrgId, Long veId, Date veRentFromdate, Date veRentTodate,
			Long veVetype, String vendorName, String contractNo) {
		List<Object[]> tripSheetList = tripSheetRepository.findTripSheetBy(OrgId, veId, veRentFromdate, veRentTodate,
				veVetype,vendorName,contractNo);
		BigDecimal totalGarbage = new BigDecimal(0.00);
		BigDecimal sumOfDry = new BigDecimal(0.00);
		BigDecimal sumOfWet = new BigDecimal(0.00);
		BigDecimal sumOfHazardous = new BigDecimal(0.00);
		TripSheetDTO tripSheetReportDto = null;
		TripSheetDTO tripSheet = null;
		List<TripSheetDTO> tripSheetDTOListfinal = new ArrayList<>();
		if (tripSheetList != null && !tripSheetList.isEmpty()) {
			tripSheetReportDto = new TripSheetDTO();
			for (Object[] tripSheetDTO : tripSheetList) {
				BigDecimal total = new BigDecimal(0.000);
				tripSheet = new TripSheetDTO();
				tripSheet.setTripData(new SimpleDateFormat("dd/MM/yyyy").format((Date) tripSheetDTO[0]));
				tripSheet.setVeId(Long.valueOf(tripSheetDTO[1].toString()));
				tripSheet.setVeNo(tripSheetDTO[2].toString());
				tripSheet.setVeType(tripSheetDTO[3].toString());
				tripSheet.setNoOfTrips(tripSheetDTO[4].toString());
				tripSheet.setDry(new BigDecimal(tripSheetDTO[5].toString()).setScale(2, RoundingMode.HALF_EVEN));
				tripSheet.setWate(new BigDecimal(tripSheetDTO[6].toString()).setScale(2, RoundingMode.HALF_EVEN));
				tripSheet.setHazardous(new BigDecimal(tripSheetDTO[7].toString()).setScale(2, RoundingMode.HALF_EVEN));
				sumOfDry = sumOfDry.add(new BigDecimal(tripSheetDTO[5].toString()).setScale(2, RoundingMode.HALF_EVEN));
				sumOfWet = sumOfWet.add(new BigDecimal(tripSheetDTO[6].toString()).setScale(2, RoundingMode.HALF_EVEN));
				sumOfHazardous = sumOfHazardous
						.add(new BigDecimal(tripSheetDTO[7].toString()).setScale(2, RoundingMode.HALF_EVEN));
				total = total.add(new BigDecimal(tripSheetDTO[5].toString())).add(
						new BigDecimal(tripSheetDTO[6].toString()).add(new BigDecimal(tripSheetDTO[7].toString())));

				tripSheet.setSumOfGarbage(total);
				totalGarbage = totalGarbage.add(
						new BigDecimal(tripSheet.getSumOfGarbage().toString()).setScale(2, RoundingMode.HALF_EVEN));

				tripSheetDTOListfinal.add(tripSheet);
			}
			tripSheetReportDto.setTotalDry(sumOfDry);
			tripSheetReportDto.setTotalWate(sumOfWet);
			tripSheetReportDto.setTotalHazardous(sumOfHazardous);
			tripSheetReportDto
					.setTotalGarbage(new BigDecimal(totalGarbage.toString()).setScale(2, RoundingMode.HALF_EVEN));
			tripSheetReportDto.setTripSheetDTO(tripSheetDTOListfinal);

		}
		return tripSheetReportDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.ITripSheetService#
	 * getTotalGarbageCollectInDisposalsiteByDate(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@POST
	@Transactional(readOnly = true)
	@Path(value = "/getTotalGarbage")
	public Long getTotalGarbageCollectInDisposalsiteByDate(@QueryParam("deId") Long deId,
			@QueryParam("orgid") Long orgid, @QueryParam("tripDate") String tripDate) {
		Date tDate = Utility.stringToDate(tripDate, MainetConstants.DATE_FORMAT);
		return tripSheetRepository.findByDeIdAndOrgidAndTripDateEquals(deId, orgid, tDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.ITripSheetService#findTripSheetReportDetails(java.
	 * lang.Long, java.lang.Long, java.util.Date, java.util.Date, java.lang.Long)
	 */
	@Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
	public TripSheetDTO findTripSheetReportDetails(Long OrgId, Long veId, Date veRentFromdate, Date veRentTodate,
			Long veVetype, String vendorName, String contractNo) {
        List<Object[]> tripSheeDetails = tripSheetRepository.findTripSheetDetails(OrgId, veId, veRentFromdate, veRentTodate,
                veVetype,vendorName,contractNo);
        BigDecimal totalGarbage = new BigDecimal(0.00);
        BigDecimal sumOfDry = new BigDecimal(0.00);
        BigDecimal sumOfWet = new BigDecimal(0.00);
        BigDecimal sumOfHazardous = new BigDecimal(0.00);
        TripSheetDTO tripDetailsDto = null;
        TripSheetDTO tripSheetdet = null;
        List<TripSheetDTO> tripSheetdetailList = new ArrayList<>();
        if (tripSheeDetails != null && !tripSheeDetails.isEmpty()) {
            tripDetailsDto = new TripSheetDTO();
            for (Object[] tripSheetdetDTO : tripSheeDetails) {
                BigDecimal total = new BigDecimal(0.000);
                tripSheetdet = new TripSheetDTO();
                tripSheetdet.setTripData(new SimpleDateFormat("dd/MM/yyyy").format((Date) tripSheetdetDTO[0]));
                tripSheetdet.setInTime(new SimpleDateFormat("HH:mm a").format((Date) tripSheetdetDTO[1]));
                // Null Check as it is optional
                if (null != tripSheetdetDTO[2]) {
                    tripSheetdet.setOutTime(new SimpleDateFormat("HH:mm a").format((Date) tripSheetdetDTO[2]));
                }

                // Null Check as it is optional
                if (null != tripSheetdetDTO[3]) {
                    tripSheetdet.setTripWeslipno(tripSheetdetDTO[3].toString());
                }

                tripSheetdet.setVeNo(tripSheetdetDTO[5].toString());
                tripSheetdet.setVeType(tripSheetdetDTO[6].toString());
                tripSheetdet.setDeName(tripSheetdetDTO[8].toString());
                tripSheetdet.setDry(new BigDecimal(tripSheetdetDTO[9].toString()).setScale(3, RoundingMode.HALF_EVEN));
                tripSheetdet.setWate(new BigDecimal(tripSheetdetDTO[10].toString()).setScale(3, RoundingMode.HALF_EVEN));
                tripSheetdet.setHazardous(new BigDecimal(tripSheetdetDTO[11].toString()).setScale(3, RoundingMode.HALF_EVEN));

                sumOfDry = sumOfDry.add(new BigDecimal(tripSheetdetDTO[9].toString()).setScale(3, RoundingMode.HALF_EVEN));
                sumOfWet = sumOfWet.add(new BigDecimal(tripSheetdetDTO[10].toString()).setScale(3, RoundingMode.HALF_EVEN));
                sumOfHazardous = sumOfHazardous
                        .add(new BigDecimal(tripSheetdetDTO[11].toString()).setScale(3, RoundingMode.HALF_EVEN));
                total = total
                        .add(new BigDecimal(tripSheetdetDTO[9].toString()))
                        .add(new BigDecimal(tripSheetdetDTO[10].toString()).add(new BigDecimal(tripSheetdetDTO[11].toString())));
                tripSheetdet.setSumOfGarbage((new BigDecimal(total.toString()).setScale(3, RoundingMode.HALF_EVEN)));
                totalGarbage = totalGarbage
                        .add(new BigDecimal(tripSheetdet.getSumOfGarbage().toString()).setScale(3, RoundingMode.HALF_EVEN));
                tripSheetdetailList.add(tripSheetdet);
            }
            tripDetailsDto.setTotalDry(sumOfDry);
            tripDetailsDto.setTotalWate(sumOfWet);
            tripDetailsDto.setTotalHazardous(sumOfHazardous);
            tripDetailsDto.setTotalGarbage(new BigDecimal(totalGarbage.toString()).setScale(3, RoundingMode.HALF_EVEN));
            tripDetailsDto.setTripSheetDTO(tripSheetdetailList);
        }
        return tripDetailsDto;
    }

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getVehicleDetsOfAssociateMRFCenter(Long mrfId, Date date, Long orgId) {

		return tripSheetRepository.getVehicleDetOfMRFCenter(mrfId, date, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getVehicleCountandBeatCountOfAssMRFCenter(Long mrfId, Date date, Long orgId) {

		return tripSheetRepository.getVehicleCountandBeatCountOfAssMRFCenter(mrfId, date, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getwardCount(Long mrfId, Date date, Long orgId) {

		return tripSheetRepository.getwardCount(mrfId, date, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getMrfwiseDetails(Long mrfId, Date date, Long orgId) {

		return tripSheetRepository.getMrfwiseDetails(mrfId, date, orgId);
	}

	

}
