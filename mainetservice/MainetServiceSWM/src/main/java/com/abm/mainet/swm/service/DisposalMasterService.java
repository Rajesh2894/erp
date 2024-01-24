package com.abm.mainet.swm.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dao.IDisposalMasterDAO;
import com.abm.mainet.swm.domain.DisposalDetailHistory;
import com.abm.mainet.swm.domain.DisposalMaster;
import com.abm.mainet.swm.domain.DisposalMasterHistory;
import com.abm.mainet.swm.dto.DisposalMasterDTO;
import com.abm.mainet.swm.mapper.DisposalMasterMapper;
import com.abm.mainet.swm.repository.DisposalMasterRepository;

/**
 * The Class DisposalMasterServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IDisposalMasterService")
@Path(value = "/disposalmasterservice")
public class DisposalMasterService implements IDisposalMasterService {

    /** The disposal master repository. */
    @Autowired
    private DisposalMasterRepository disposalMasterRepository;

    @Autowired
    private IDisposalMasterDAO disposalMasterDAO;

    /** The disposal master mapper. */
    @Autowired
    private DisposalMasterMapper disposalMasterMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /**
     * The IAttachDocs Service
     * 
     */
    @Autowired
    private IAttachDocsService attachDocsService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.DisposalMasterService#serchDisposalSiteBySiteNumberAndSiteName(java.lang.Long,
     * java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/search")
    public List<DisposalMasterDTO> serchDisposalSiteBySiteNumberAndSiteName(@QueryParam("siteNumber") Long siteNumber,
            @QueryParam("siteName") String siteName,
            @QueryParam("orgId") Long orgId) {
        return disposalMasterMapper.mapDisposalMasterListToDisposalMasterDTOList(
                disposalMasterDAO.serchDisposalSiteBySiteNumberAndSiteName(siteNumber, MainetConstants.FlagY, siteName, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDisposalMasterService#serchDisposalSite(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<DisposalMasterDTO> serchDisposalSite(Long siteNumber, String siteName, Long orgId) {
        return disposalMasterMapper.mapDisposalMasterListToDisposalMasterDTOList(
                disposalMasterDAO.serchDisposalSiteBySiteNumberAndSiteName(siteNumber, null, siteName, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.DisposalMasterService#getDisposalSiteBySiteNumber(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public DisposalMasterDTO getDisposalSiteBySiteNumber(@PathParam("id") Long siteNumber) {
        return disposalMasterMapper.mapDisposalMasterToDisposalMasterDTO(disposalMasterRepository.findOne(siteNumber));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.DisposalMasterService#saveDisposalSite(com.abm.mainet.swm.dto.DisposalMasterDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public DisposalMasterDTO saveDisposalSite(@RequestBody DisposalMasterDTO siteDetails) {
        DisposalMaster master = disposalMasterMapper.mapDisposalMasterDTOToDisposalMaster(siteDetails);

        master = disposalMasterRepository.save(master);

        DisposalMasterHistory masterHistory = new DisposalMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);

        auditService.createHistory(master, masterHistory);
        master.getTbSwDesposalDets().forEach(detail -> {
            DisposalDetailHistory detailHistory = new DisposalDetailHistory();
            detailHistory.setDeId(detail.getTbSwDesposalMast().getDeId());
            detailHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
            auditService.createHistory(detail, detailHistory);
        });
        return disposalMasterMapper.mapDisposalMasterToDisposalMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.DisposalMasterService#updateDisposalSite(com.abm.mainet.swm.dto.DisposalMasterDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public DisposalMasterDTO updateDisposalSite(DisposalMasterDTO siteDetails) {
        DisposalMaster master = disposalMasterMapper.mapDisposalMasterDTOToDisposalMaster(siteDetails);
        master = disposalMasterRepository.save(master);

        DisposalMasterHistory masterHistory = new DisposalMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);
        master.getTbSwDesposalDets().forEach(detail -> {
            DisposalDetailHistory detailHistory = new DisposalDetailHistory();
            detailHistory.setDeId(detail.getTbSwDesposalMast().getDeId());
            detailHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
            auditService.createHistory(detail, detailHistory);
        });
        return disposalMasterMapper.mapDisposalMasterToDisposalMasterDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.DisposalMasterService#deleteDisposalSite(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void deleteDisposalSite(Long siteNumber, Long empId, String ipMacAdd) {
        DisposalMaster master = disposalMasterRepository.findOne(siteNumber);
        master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        disposalMasterRepository.save(master);

        DisposalMasterHistory masterHistory = new DisposalMasterHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDisposalMasterService#findDayMonthWiseDumping(java.lang.Long, java.lang.Long,
     * java.util.Date, java.util.Date)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public DisposalMasterDTO findDayMonthWiseDumping(Long orgId, Long deName, Date fromDate, Date toDate) {
        // TODO Auto-generated method stub
        List<Object[]> disposaldDetails = disposalMasterRepository.findVehicleSchedulingDetails(orgId, deName, fromDate, toDate);
        DisposalMasterDTO disposalMasterDTO = new DisposalMasterDTO();
        DisposalMasterDTO disposalMasterDet = null;
        BigDecimal SumOfvolume = new BigDecimal(0.000);

        List<DisposalMasterDTO> disposalMasterList = new ArrayList<>();
        if (disposaldDetails != null && !disposaldDetails.isEmpty()) {
            for (Object[] disdayDet : disposaldDetails) {
                BigDecimal total = new BigDecimal(0.000);
                disposalMasterDet = new DisposalMasterDTO();
                disposalMasterDet.setTripDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) disdayDet[0]));
                disposalMasterDet.setDeName(disdayDet[2].toString());
                disposalMasterDet.setDeCapacity(new BigDecimal(disdayDet[3].toString()).setScale(3, RoundingMode.HALF_EVEN));
                disposalMasterDet.setDeCapacityUnit(Long.valueOf(disdayDet[4].toString()));
                disposalMasterDet.setDry(new BigDecimal(disdayDet[5].toString()).setScale(3, RoundingMode.HALF_EVEN));
                disposalMasterDet.setWate(new BigDecimal(disdayDet[6].toString()).setScale(3, RoundingMode.HALF_EVEN));
                total = total
                        .add(new BigDecimal(disdayDet[5].toString()).add(new BigDecimal(disdayDet[6].toString())));
                disposalMasterDet.setSumOfDryWate(new BigDecimal(total.toString()).setScale(3, RoundingMode.HALF_EVEN));
                SumOfvolume = SumOfvolume
                        .add(new BigDecimal(total.toString()).setScale(3, RoundingMode.HALF_EVEN));
                disposalMasterList.add(disposalMasterDet);
            }
            disposalMasterDTO.setTotalVolume(new BigDecimal(SumOfvolume.toString()).setScale(3, RoundingMode.HALF_EVEN));
            if (deName != 0)
                disposalMasterDTO.setDeNameReg(disposalMasterDet.getDeName());
            disposalMasterDTO.setDisposalMasterList(disposalMasterList);
        }
        return disposalMasterDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDisposalMasterService#downloadDisposalSiteImages(java.util.List)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<DisposalMasterDTO> downloadDisposalSiteImages(List<DisposalMasterDTO> disposalsites) {
        if (!disposalsites.isEmpty()) {
            Map<String, String> imageMap = new HashMap<>();
            attachDocsService
                    .findAllDocLikeReferenceId(disposalsites.get(0).getOrgid(),
                            MainetConstants.SolidWasteManagement.DISPOSAL_SITE + MainetConstants.operator.PERCENTILE)
                    .forEach(doc -> {
                        final String imagepath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + File.separator
                                + disposalsites.get(0).getOrgid().toString() + File.separator
                                + MainetConstants.SolidWasteManagement.DISPOSAL_SITE;
                        imageMap.put(doc.getIdfId(), Utility.downloadedFileUrl(
                                doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname(), imagepath,
                                FileNetApplicationClient.getInstance()));
                    });

            disposalsites.forEach(site -> {
                site.setSiteImage(imageMap.get(MainetConstants.SolidWasteManagement.DISPOSAL_SITE + site.getDeId()));
            });
        }
        return disposalsites;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IDisposalMasterService#validateDisposalMaster(com.abm.mainet.swm.dto.DisposalMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateDisposalMaster(DisposalMasterDTO disposalMasterDTO) {

        Assert.notNull(disposalMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
        Assert.notNull(disposalMasterDTO.getDeName(), "Disposal Site Name Can Not Be Empty");

        List<DisposalMaster> disposalMasterList = disposalMasterDAO.serchDisposalSiteBySiteName(disposalMasterDTO.getDeId(), null,
                disposalMasterDTO.getDeName().trim(), disposalMasterDTO.getOrgid());
        return disposalMasterList.isEmpty();
    }

}
