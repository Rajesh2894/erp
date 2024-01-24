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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dao.IMRFMasterDAO;
import com.abm.mainet.swm.domain.MRFMaster;
import com.abm.mainet.swm.domain.MRFMasterHistory;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.mapper.MRFMasterMapper;
import com.abm.mainet.swm.repository.MRFMasterRepository;

/**
 * @author Ajay.Kumar
 *
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IMRFMasterService")
@Path(value = "/mrfmasterservice")
public class MRFMasterService implements IMRFMasterService {

    /**
     * The MRFMaster Mapper
     */
    @Autowired
    private MRFMasterMapper mRFMasterMapper;

    /**
     * The MRF Master Repository
     */
    @Autowired
    private MRFMasterRepository mRFMasterRepository;

    /**
     * The Audit Service
     */
    @Autowired
    private AuditService auditService;

    /**
     * The IMRFMaster DAO
     */
    @Autowired
    private IMRFMasterDAO mRFMasterDAO;

    /**
     * The IAttachDocs Service
     */
    @Autowired
    private IAttachDocsService attachDocsService;

    /**
     * The Seq Gen Function Utility
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The IOrganisation Service
     */
    @Autowired
    private IOrganisationService organisationService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#save(com.abm.mainet.swm.dto.MRFMasterDto)
     */
    @Override
    @Transactional
    public MRFMasterDto save(MRFMasterDto mRFMasterDtoDetails) {
        // TODO Auto-generated method stub

        Organisation org = organisationService.getOrganisationById(mRFMasterDtoDetails.getOrgId());

        final Long sequenceNo = seqGenFunctionUtility.generateSequenceNo(
                MainetConstants.SolidWasteManagement.SHORT_CODE, "TB_SW_MRF_MAST", "MRF_ID",
                mRFMasterDtoDetails.getOrgId(), MainetConstants.FlagC, mRFMasterDtoDetails.getOrgId());

        String mrfID = "CG" + org.getOrgShortNm() + String.format("%03d", sequenceNo);

        MRFMaster mRFMaster = mapped(mRFMasterDtoDetails);
        mRFMaster.setMrfPlantId(mrfID);
        mRFMaster = mRFMasterRepository.save(mRFMaster);
        saveMRFMasterHistory(mRFMaster, MainetConstants.Transaction.Mode.ADD);
        return mRFMasterMapper.MRFMasterToMRFMasterDto(mRFMaster);
    }

    /**
     * save MRFMaster History
     * @param mRFMaster
     * @param status
     */
    private void saveMRFMasterHistory(MRFMaster mRFMaster, String status) {
        MRFMasterHistory mRFMasterHistory = new MRFMasterHistory();
        mRFMasterHistory.sethStatus(status);
        auditService.createHistory(mRFMaster, mRFMasterHistory);
    }

    /**
     * mapped
     * @param mRFMasterDtoDetails
     * @return
     */
    private MRFMaster mapped(MRFMasterDto mRFMasterDtoDetails) {
        MRFMaster master = mRFMasterMapper.mapMRFMasterDtoToMRFMaster(mRFMasterDtoDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#serchMRFMasterByPlantIdAndPlantname(java.lang.String, java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @POST
    @Path(value = "/search")
    public List<MRFMasterDto> serchMRFMasterByPlantIdAndPlantname(@QueryParam("mrfPlantId") String plantId,
            @QueryParam("mrfPlantName") String plantname, @QueryParam("orgId") Long orgId) {
        return mRFMasterMapper.mapMRFMasterListToMRFMasterDtoList(
                mRFMasterDAO.serchIMRFCenterByPlantIdAndPlantName(plantId, plantname, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#getPlantNameByPlantId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public MRFMasterDto getPlantNameByPlantId(@PathParam("id") Long plantId) {
        // TODO Auto-generated method stub
        return mRFMasterMapper.MRFMasterToMRFMasterDto(mRFMasterRepository.findOne(plantId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#update(com.abm.mainet.swm.dto.MRFMasterDto)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public MRFMasterDto update(MRFMasterDto mRFMasterDtoDetails) {
        MRFMaster mRFMaster = mapped(mRFMasterDtoDetails);
        mRFMasterRepository.save(mRFMaster);
        saveMRFMasterHistory(mRFMaster, MainetConstants.Transaction.Mode.UPDATE);
        return mRFMasterMapper.MRFMasterToMRFMasterDto(mRFMaster);
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void deleteMRFMasterDto(Long plantId, Long empId, String ipMacAdd) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#findDayMonthWiseMrfCenter(java.lang.Long, java.lang.Long, java.util.Date,
     * java.util.Date)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public MRFMasterDto findDayMonthWiseMrfCenter(Long orgId, Long mrfId, Date fromDate, Date toDate) {

        List<Object[]> mrfcenterDetails = mRFMasterRepository.findVehicleSchedulingDetails(orgId, mrfId, fromDate,
                toDate);
        MRFMasterDto mRFMasterDTO = null;
        MRFMasterDto MRFMasterDet = null;
        BigDecimal SumOfvolume = new BigDecimal(0.000);
        BigDecimal SumOfwet = new BigDecimal(0.000);
        BigDecimal SumOfdry = new BigDecimal(0.000);
        BigDecimal SumOfhazardous = new BigDecimal(0.000);
        List<MRFMasterDto> mRFMasterDtoList = new ArrayList<>();
        if (mrfcenterDetails != null && !mrfcenterDetails.isEmpty()) {
            mRFMasterDTO = new MRFMasterDto();
            for (Object[] disdayDet : mrfcenterDetails) {
                BigDecimal total = new BigDecimal(0.000);
                MRFMasterDet = new MRFMasterDto();
                if (disdayDet[0] != null) {
                    MRFMasterDet.setTripDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) disdayDet[0]));
                }
                if (disdayDet[2] != null) {
                    MRFMasterDet.setMrfPlantName(disdayDet[2].toString());
                }
                if (disdayDet[3] != null) {
                    MRFMasterDet.setMrfPlantCap(
                            new BigDecimal(disdayDet[3].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (disdayDet[4] != null) {
                    SumOfdry = SumOfdry.add(new BigDecimal(disdayDet[4].toString()));
                    MRFMasterDet.setDry(new BigDecimal(disdayDet[4].toString()).setScale(3, RoundingMode.HALF_EVEN));
                }
                if (disdayDet[5] != null) {
                    MRFMasterDet.setWate(new BigDecimal(disdayDet[5].toString()).setScale(3, RoundingMode.HALF_EVEN));
                    SumOfwet = SumOfwet.add(new BigDecimal(disdayDet[5].toString()));
                }
                if (disdayDet[6] != null) {
                    MRFMasterDet
                            .setHazardous(new BigDecimal(disdayDet[6].toString()).setScale(3, RoundingMode.HALF_EVEN));
                    SumOfhazardous = SumOfhazardous.add(new BigDecimal(disdayDet[6].toString()));
                }
                total = total.add(new BigDecimal(disdayDet[4].toString())
                        .add(new BigDecimal(disdayDet[5].toString()).add(new BigDecimal(disdayDet[6].toString()))));
                MRFMasterDet.setSumOfDryWate(new BigDecimal(total.toString()).setScale(3, RoundingMode.HALF_EVEN));
                SumOfvolume = SumOfvolume.add(new BigDecimal(total.toString()).setScale(3, RoundingMode.HALF_EVEN));
                mRFMasterDtoList.add(MRFMasterDet);
            }
            mRFMasterDTO.setTotaldry(new BigDecimal(SumOfdry.toString()).setScale(3, RoundingMode.HALF_EVEN));
            mRFMasterDTO.setTotalwate(new BigDecimal(SumOfwet.toString()).setScale(3, RoundingMode.HALF_EVEN));
            mRFMasterDTO
                    .setTotalhazardous(new BigDecimal(SumOfhazardous.toString()).setScale(3, RoundingMode.HALF_EVEN));
            mRFMasterDTO.setTotalVolume(new BigDecimal(SumOfvolume.toString()).setScale(3, RoundingMode.HALF_EVEN));
            mRFMasterDTO.setmRFMasterList(mRFMasterDtoList);
        }
        return mRFMasterDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#serchMrfCenter(java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<MRFMasterDto> serchMrfCenter(String plantId, String plantname, Long orgId) {
        return mRFMasterMapper.mapMRFMasterListToMRFMasterDtoList(
                mRFMasterDAO.serchIMRFCenterByPlantIdAndPlantName(plantId, plantname, orgId));
    }
    
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<MRFMasterDto> serchMrfCenter(Long orgId, String propertyNo) {
    	List<MRFMasterDto> mrfMasterList = mRFMasterMapper.mapMRFMasterListToMRFMasterDtoList(
                mRFMasterDAO.serchIMRFCenterByPlantIdAndPlantName(orgId, propertyNo));
    	
    	return mrfMasterList;
    }
    
    
    @Override
    @SuppressWarnings("unused")
    public boolean getPropertyDetailsByPropertyNumber(String propertyNo) {
      boolean propertyExists = false;   
		PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(propertyNo);
        propInputDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);           
            	propertyExists = true;
        }       
        return propertyExists;
    }
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#downloadMrfSiteImages(java.util.List)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<MRFMasterDto> downloadMrfSiteImages(List<MRFMasterDto> mRFMastercenter) {
        if (!mRFMastercenter.isEmpty()) {
            Map<String, String> imageMap = new HashMap<>();
            attachDocsService.findAllDocLikeReferenceId(mRFMastercenter.get(0).getOrgId(),
                    "SWM_MRF_" + MainetConstants.operator.PERCENTILE).forEach(doc -> {
                        final String imagepath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + File.separator
                                + mRFMastercenter.get(0).getOrgId().toString() + File.separator + "SWM_MRF_";
                        imageMap.put(doc.getIdfId(),
                                Utility.downloadedFileUrl(
                                        doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname(),
                                        imagepath, FileNetApplicationClient.getInstance()));
                    });

            mRFMastercenter.forEach(site -> {
                site.setSiteImage(imageMap.get("SWM_MRF_" + site.getMrfId()));
            });
        }
        return mRFMastercenter;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IMRFMasterService#validateMRFMaster(com.abm.mainet.swm.dto.MRFMasterDto)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public boolean validateMRFMaster(MRFMasterDto mRFMasterDto) {
        // TODO Auto-generated method stub
        return false;
    }

}
