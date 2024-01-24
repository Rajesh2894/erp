package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.domain.MeasurementBookLbh;
import com.abm.mainet.workManagement.domain.MeasurementBookLbhHistory;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetails;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookLbhDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.repository.MbOverHeadRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookDetailsRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookLbhRepository;
import com.abm.mainet.workManagement.repository.MeasurementBookRepository;
import com.abm.mainet.workManagement.repository.WorksMeasurementSheetRepository;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class MeasurementBookLbhServiceImpl implements MeasurementBookLbhService {

    private static final Logger LOGGER = Logger.getLogger(MeasurementBookLbhServiceImpl.class);

    @Autowired
    private MeasurementBookLbhRepository mbLbhRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private MeasurementBookService mbService;

    @Autowired
    private MeasurementBookDetailsRepository mbDetailsRepository;
    
    @Autowired
    private MbOverHeadRepository mbOverHeadRepository;

    @Autowired
    private MeasurementBookRepository mbRepository;

    @Autowired
    private WorksMeasurementSheetService measurementSheetService;

    @Autowired
    private WorkEstimateService workEstimateService;
    
    @Autowired
    private WorksMeasurementSheetRepository worksMeasurementSheetRepository;
    
    

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementBookLbhDto> getAllLbhDetailsByMeasurementId(Long MeasurementId) {
        MeasurementBookLbhDto mbDetails = null;
        List<MeasurementBookLbhDto> mbDetailsList = new ArrayList<>();
        LOGGER.info("getAllLbhDetailsByMeasurementId method started");
        try {
            List<MeasurementBookLbh> bookMasterLbhList = mbLbhRepository.getAllLbhDetailsByMeasurementId(MeasurementId);
            for (MeasurementBookLbh lbhObj : bookMasterLbhList) {
                mbDetails = new MeasurementBookLbhDto();
                BeanUtils.copyProperties(lbhObj, mbDetails);
                mbDetailsList.add(mbDetails);
            }
        } catch (Exception e) {
            throw new FrameworkException("Exception acured while  calling method getAllLbhDetailsByMeasurementId " + e);
        }
        LOGGER.info("getAllLbhDetailsByMeasurementId method End");
        return mbDetailsList;
    }

    @Override
    @Transactional(readOnly = true)
    public MeasurementBookLbhDto getMbLbhByLbhId(Long lbhId) {
        MeasurementBookLbhDto bookLbhDto = new MeasurementBookLbhDto();
        LOGGER.info("getMbLbhByLbhId method started");
        try {
            MeasurementBookLbh mbEntity = mbLbhRepository.getLbhDetailsByLbhId(lbhId);
            BeanUtils.copyProperties(mbEntity, bookLbhDto);
        } catch (Exception e) {
            throw new FrameworkException("Exception acured while  calling method getMbLbhByLbhId " + e);
        }
        LOGGER.info("getMbLbhByLbhId method End");
        return bookLbhDto;
    }

    @Override
    @Transactional
    public void saveUpdateMbLbhList(List<MeasurementBookLbhDto> lbhDtosList, List<Long> deletedLbhId, Long parentEstimatedId) {
        MeasurementBookLbh mbLbhEntity = null;
        WorkEstimateMeasureDetails estimateMeasureDetails = null;
        MeasurementBookLbhHistory mbLbhHistory = null;
        BigDecimal commulativeQuantity = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        LOGGER.info("saveUpdateMbLbhList method started");
        try {
            for (MeasurementBookLbhDto mbLbhDto : lbhDtosList) {
                mbLbhEntity = new MeasurementBookLbh();
                mbLbhHistory = new MeasurementBookLbhHistory();
                estimateMeasureDetails = new WorkEstimateMeasureDetails();
                BeanUtils.copyProperties(mbLbhDto, mbLbhEntity);
                if (mbLbhDto.getEstimateMeasureDetId() != null) {
                    estimateMeasureDetails.setMeMentId(mbLbhDto.getEstimateMeasureDetId());
                    mbLbhEntity.setDetails(estimateMeasureDetails);
                }
                mbLbhEntity = mbLbhRepository.save(mbLbhEntity);
                if (mbLbhDto.getEstimateMeasureDetId() != null) {
                    Long utiQuantity = null;
                    WorkEstimateMeasureDetailsDto dto = measurementSheetService
                            .findByMeasureDetailsId(mbLbhEntity.getDetails().getMeMentId());
                    if (dto.getMeNoUtl() != null) {
                        utiQuantity = dto.getMeNoUtl() + mbLbhEntity.getMbNosAct();
                    } else {
                        utiQuantity = mbLbhEntity.getMbNosAct();
                    }

                    measurementSheetService.updateUtilizationNoByMeId(utiQuantity,
                            mbLbhEntity.getDetails().getMeMentId());
                }
                auditService.createHistory(mbLbhEntity, mbLbhHistory);
            }
            if (deletedLbhId != null && !deletedLbhId.isEmpty()) {
                mbLbhRepository.deleteMbLbhByMbDetailsId(deletedLbhId);
            }
            if (lbhDtosList != null && !lbhDtosList.isEmpty()) {
                BigDecimal amt = mbDetailsRepository.getAllRateByMbId(lbhDtosList.get(0).getMbdId());
                BigDecimal quantity = mbLbhRepository.getAllLbhTotal(lbhDtosList.get(0).getMbdId());
                if (quantity != null) {
                    commulativeQuantity = commulativeQuantity.add(quantity);
                    mbService.updateLbhQuantityByMbId(quantity, lbhDtosList.get(0).getMbdId());
                    BigDecimal totalQuantity = quantity.multiply(lbhDtosList.get(0).getSorRate());
                    if (amt != null) {
                        BigDecimal totalAmount = amt.add(totalQuantity);
                        mbDetailsRepository.updateAmount(lbhDtosList.get(0).getMbdId(), totalAmount);
                    } else {
                        mbDetailsRepository.updateAmount(lbhDtosList.get(0).getMbdId(), totalQuantity);
                    }

                    MeasurementBookDetailsDto detailsDto = mbService
                            .getMBDetailsByDetailsId(lbhDtosList.get(0).getMbdId());
                    BigDecimal actualAmt = mbDetailsRepository.getAllRateAmount(detailsDto.getMbId());
                    BigDecimal mbovdAmt=mbOverHeadRepository.getOverheadAmount(detailsDto.getMbId());
                    if(mbovdAmt!=null) {
                    	actualAmt=actualAmt.add(mbovdAmt);
                    }
                    mbRepository.updateTotalMbAmountByMbId(actualAmt, detailsDto.getMbId());
                }

            } else {
                for (Long lbhId : deletedLbhId) {
                    MeasurementBookLbhDto lbhDto = getMbLbhByLbhId(lbhId);
                    commulativeQuantity = commulativeQuantity.subtract(lbhDto.getMbTotal());
                    mbService.updateLbhQuantityByMbId(null, lbhDto.getMbdId());

                    BigDecimal amt = mbDetailsRepository.getAllRateByMbId(lbhDto.getMbdId());
                    mbDetailsRepository.updateAmount(lbhDto.getMbdId(), amt);
                    MeasurementBookDetailsDto detailsDto = mbService.getMBDetailsByDetailsId(lbhDto.getMbdId());
                    BigDecimal actualAmt = mbDetailsRepository.getAllRateAmount(detailsDto.getMbId());
                    mbRepository.updateTotalMbAmountByMbId(actualAmt, detailsDto.getMbId());
                    break;
                }

            }

            if (lbhDtosList != null && !lbhDtosList.isEmpty()) {
                workEstimateService.findById(lbhDtosList.get(0).getOrgId(), parentEstimatedId);
                workEstimateService.updateWorkEsimateLbhUtlQunatity(parentEstimatedId, commulativeQuantity);
            }

        } catch (Exception e) {
            throw new FrameworkException("Exception acured while  calling method saveUpdateMbLbhList " + e);
        }
        LOGGER.info("saveUpdateMbLbhList method End");
    }

    @Override
    @Transactional
    public void saveUpdateMbLbh(MeasurementBookLbhDto lbhDto) {
        MeasurementBookLbh mbLbhEntity = new MeasurementBookLbh();
        MeasurementBookLbhHistory mbLbhHistory = new MeasurementBookLbhHistory();
        LOGGER.info("saveUpdateMbLbh method started");
        try {
            BeanUtils.copyProperties(lbhDto, mbLbhEntity);
            mbLbhEntity = mbLbhRepository.save(mbLbhEntity);
            auditService.createHistory(mbLbhEntity, mbLbhHistory);
        } catch (Exception e) {
            throw new FrameworkException("Exception acured while  calling method saveUpdateMbLbh " + e);
        }
        LOGGER.info("saveUpdateMbLbh method End");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementBookLbhDto> getLbhDetailsByMeasurementId(List<Long> measurementId, Long mbDetId,
            String mode) {
        List<MeasurementBookLbhDto> lbhDtoList = new ArrayList<>();
        MeasurementBookLbhDto mbLbhDto;
        if (!mode.equals(MainetConstants.WorksManagement.APPROVAL)) {
            for (Long id : measurementId) {
            	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            	
            	  List<MeasurementBookLbh> measurementBookLbhList = mbLbhRepository.getLbhDetailsByMeasurement(id, mbDetId);
            	  for(MeasurementBookLbh measurementBookLbh:measurementBookLbhList) {
                	  if (measurementBookLbh != null) {
                        mbLbhDto = new MeasurementBookLbhDto();
                        BeanUtils.copyProperties(measurementBookLbh, mbLbhDto);
                        mbLbhDto.setEstimateMeasureDetId(measurementBookLbh.getDetails().getMeMentId());
                        lbhDtoList.add(mbLbhDto);
                    } else {
                        mbLbhDto = new MeasurementBookLbhDto();
                        mbLbhDto.setMbdId(mbDetId);
                        lbhDtoList.add(mbLbhDto);
                    }
                }}
            	else {
            		MeasurementBookLbh measurementBookLbh = mbLbhRepository.getLbhDetailsByMeasurementId(id, mbDetId);
            		 if (measurementBookLbh != null) {
                         mbLbhDto = new MeasurementBookLbhDto();
                         BeanUtils.copyProperties(measurementBookLbh, mbLbhDto);
                         mbLbhDto.setEstimateMeasureDetId(measurementBookLbh.getDetails().getMeMentId());
                         lbhDtoList.add(mbLbhDto);
                     } else {
                         mbLbhDto = new MeasurementBookLbhDto();
                         mbLbhDto.setMbdId(mbDetId);
                         lbhDtoList.add(mbLbhDto);
                     }
                 }
            	}
            	  
            	
            	 
        } else {
        	  for (Long id : measurementId) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)){
        		List<MeasurementBookLbh> measurementBookLbhList = mbLbhRepository.getLbhDetailsByMeasurement(id, mbDetId);
            	for(MeasurementBookLbh measurementBookLbh:measurementBookLbhList) {
                if (measurementBookLbh != null) {
                    mbLbhDto = new MeasurementBookLbhDto();
                    BeanUtils.copyProperties(measurementBookLbh, mbLbhDto);
                    mbLbhDto.setEstimateMeasureDetId(measurementBookLbh.getDetails().getMeMentId());
                    lbhDtoList.add(mbLbhDto);
                }
            	}
        		
        	}
        	else {
        		MeasurementBookLbh measurementBookLbh = mbLbhRepository.getLbhDetailsByMeasurementId(id, mbDetId);
        		if (measurementBookLbh != null) {
                    mbLbhDto = new MeasurementBookLbhDto();
                    BeanUtils.copyProperties(measurementBookLbh, mbLbhDto);
                    mbLbhDto.setEstimateMeasureDetId(measurementBookLbh.getDetails().getMeMentId());
                    lbhDtoList.add(mbLbhDto);
                }
        	}
          
            	
            }
        }
        return lbhDtoList;
    }

    @Override
    @Transactional
    public void deleteMbLbhByMbDetailsId(List<Long> mbdId) {
        mbLbhRepository.deleteMbLbhByMbDetailsId(mbdId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementBookLbhDto> getAuditDetailsByMeasurementId(Long mbDetId) {
        MeasurementBookLbhDto mbDetails = null;
        List<MeasurementBookLbhDto> mbDetailsList = new ArrayList<>();
        try {
            List<MeasurementBookLbhHistory> bookMasterLbhList = mbLbhRepository.getAuditDetailsByMeasurementId(mbDetId);
            for (MeasurementBookLbhHistory lbhObj : bookMasterLbhList) {
                mbDetails = new MeasurementBookLbhDto();
                BeanUtils.copyProperties(lbhObj, mbDetails);
                mbDetailsList.add(mbDetails);
            }
        } catch (Exception e) {
            throw new FrameworkException("Exception acured while  calling method getAuditDetailsByMeasurementId " + e);
        }
        return mbDetailsList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementBookLbhDto> getLbhDetailsByMbDetailId(Long orgId, Long mbDetId) {
        List<MeasurementBookLbhDto> lbhDtoList = new ArrayList<>();
        MeasurementBookLbhDto mbLbhDto;

       List< MeasurementBookLbh> measurementBookLbh = mbLbhRepository.getLbhDetailsByMbDetailId(orgId, mbDetId);
        if (measurementBookLbh != null) {
        	 for (MeasurementBookLbh dro : measurementBookLbh) {
            mbLbhDto = new MeasurementBookLbhDto();
            BeanUtils.copyProperties(dro, mbLbhDto);
            lbhDtoList.add(mbLbhDto);
        	 }
        } else {
            mbLbhDto = new MeasurementBookLbhDto();
            mbLbhDto.setMbdId(mbDetId);
            lbhDtoList.add(mbLbhDto);
        }
        return lbhDtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMeasureDetailsDto> getWorkEstimateByWorkEId(Long WorkEId) {
        List<WorkEstimateMeasureDetails> listEntity = worksMeasurementSheetRepository.getWorkEstimateByWorkEId(WorkEId);
        List<WorkEstimateMeasureDetailsDto> dtoList = new ArrayList<>();
        if (!listEntity.isEmpty()) {
            listEntity.forEach(entity -> {
                WorkEstimateMeasureDetailsDto obj = new WorkEstimateMeasureDetailsDto();
                BeanUtils.copyProperties(entity, obj);
                dtoList.add(obj);
            });
        }
        return dtoList;
    } 

}
