package com.abm.mainet.bill.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.bill.repository.AdjustmentBillDetailMappingRepository;
import com.abm.mainet.bill.repository.AdjustmentEntryRepository;
import com.abm.mainet.cfc.challan.domain.AdjustmentBillDetailMappingEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentDetailEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.cfc.challan.dto.AdjustmentDetailDTO;
import com.abm.mainet.cfc.challan.dto.AdjustmentMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.CustomerInfoDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Service
public class AdjustmentEntryServiceImpl implements AdjustmentEntryService {

    @Resource
    private AdjustmentEntryRepository adjustmentEntryRepository;

    @Resource
    private AdjustmentBillDetailMappingRepository adjustmentBillDetailMappingRepository;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private TbTaxMasService tbTaxMasService;

    private final String constantNO = MainetConstants.Common_Constant.NO;
    private final String constantYES = MainetConstants.Common_Constant.YES;
    private final String positive = MainetConstants.MENU.P;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdjustmentEntryServiceImpl.class);

    @Override
    @Transactional
    public List<TbBillMas> fetchModuleWiseBills(final Long deptId, final String ccnNoOrPropNo, final Long orgId) {
        List<TbBillMas> result = null;
        try {
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(deptId);
            serviceClassName = messageSource.getMessage(
                    ApplicationSession.getInstance().getMessage("bill.lbl.Bills") + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.fetchCurrentBill(ccnNoOrPropNo, orgId);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching bill for Adjustment entry for Department: " + deptId, e);
        }
        return result;
    }

    @Override
    public List<TbTaxMas> fetchAllModulewiseTaxes(final Long deptId, final Organisation orgId) {
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, orgId);
        return tbTaxMasService.findAllTaxesForBillGeneration(orgId.getOrgid(), deptId, chargeApplicableAt.getLookUpId(), null);
    }

    @Override
    @Transactional
    public boolean saveAdjustmentEntryData(final List<TbBillMas> billlist, final AdjustmentMasterDTO adjustmentDto) {
        final AdjustmentMasterEntity entity = new AdjustmentMasterEntity();
        BeanUtils.copyProperties(adjustmentDto, entity);
		if (billlist.get(0).getCsIdn() != null) {
			entity.setAdjRefNo(billlist.get(0).getCsIdn().toString());
		} else if (StringUtils.isNotBlank(billlist.get(0).getPropNo())) {
			entity.setAdjRefNo(billlist.get(0).getPropNo());
		}
        entity.setAdjDate(new Date());
        entity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        entity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        entity.setCreatedDate(new Date());
        entity.setLgIpMac(Utility.getMacAddress());
        AdjustmentDetailEntity detailsEntity = null;
        final List<AdjustmentBillDetailMappingEntity> mappingList = new ArrayList<>(0);
        for (final TbBillMas bill : billlist) {
            Double amount = 0d;
            List<TbBillDet> previous = null;
            for (final AdjustmentDetailDTO detail : adjustmentDto.getAdjDetailDto()) {
                if (detail.getAdjAmount() > 0d) {
                    detailsEntity = new AdjustmentDetailEntity();
                    BeanUtils.copyProperties(detail, detailsEntity);
                    detailsEntity.setAdjId(entity);
                    if ((bill.getTbWtBillDet() != null) && !bill.getTbWtBillDet().isEmpty()) {
                        AdjustmentBillDetailMappingEntity mapping = null;
                        for (final TbBillDet det : bill.getTbWtBillDet()) {
                            if (det.getTaxId().equals(detail.getTaxId())) {
                                if (constantNO.equals(adjustmentDto.getAdjType())) {
                                    final double adjAmount = det.getBdCurBalTaxamt() - detail.getAdjAmount();
                                    if (adjAmount >= 0d) {
                                        det.setBdCurBalTaxamt(adjAmount);
                                        detailsEntity.setAdjAdjustedAmount(detail.getAdjAmount());
                                        detailsEntity.setAdjBalanceAmount(0d);
                                        detailsEntity.setAdjAdjustedFlag(constantYES);
                                        detail.setAdjAmount(0);
                                    } else {
                                        detailsEntity.setAdjAdjustedAmount(det.getBdCurBalTaxamt());
                                        det.setBdCurBalTaxamt(0d);
                                        detailsEntity.setAdjBalanceAmount(Math.abs(adjAmount));
                                        detailsEntity.setAdjAdjustedFlag(constantNO);
                                        detail.setAdjAmount(Math.abs(adjAmount));
                                    }
                                    
                                    billlist.forEach(billMas ->{
                                    	billMas.setRevisedBillDate(new Date());
                                    	billMas.setRevisedBillType("NA");
                                    	billMas.getTbWtBillDet().forEach(detailDto ->{
                                    		detailDto.setRevisedBillDate(new Date());
                                    		detailDto.setRevisedBillType("NA");
                                    	});
                                    });
                                } else {
                                    if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
                                		(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(adjustmentDto.getDpDeptId())))) {
                                    	//do not adjust in current bill for positive adjustment for water department SKDCL
                                        detailsEntity.setAdjAdjustedFlag(constantNO);
                                        detailsEntity.setAdjBalanceAmount(detail.getAdjAmount());
                                        detailsEntity.setAdjAdjustedAmount(0d);

									} /*
										 * else
										 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
										 * MainetConstants.ENV_ASCL) &&
										 * (MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(
										 * adjustmentDto.getDpDeptId())))) {
										 * 
										 * detailsEntity.setAdjAdjustedFlag(constantNO);
										 * detailsEntity.setAdjBalanceAmount(detail.getAdjAmount());
										 * detailsEntity.setAdjAdjustedAmount(0d); }
										 */else {
                                    	det.setBdCurBalTaxamt(detail.getAdjAmount() + det.getBdCurBalTaxamt());
	                                     	det.setBdCurTaxamt(detail.getAdjAmount() + det.getBdCurTaxamt());
                                        detailsEntity.setAdjAdjustedFlag(constantYES);
                                        detailsEntity.setAdjBalanceAmount(0d);
                                        detailsEntity.setAdjAdjustedAmount(detail.getAdjAmount());
                                        billlist.forEach(billMas -> {
                                        	billMas.setBmPaidFlag(MainetConstants.FlagN);
                                        	if(!(billMas.getBmYear().equals(bill.getBmYear()))) {
                                        		billMas.getTbWtBillDet().forEach(detailDto ->{
                                        			if (detailDto.getTaxId().equals(detail.getTaxId())) {
                                        				detailDto.setBdPrvArramt(detailDto.getBdPrvArramt() + detail.getAdjAmount());
                                            		}
                                        		});
                                        	}
                                        });

                                    }
                                    detail.setAdjAmount(0);
                                    billlist.forEach(billMas ->{
                                    	billMas.setRevisedBillDate(new Date());
                                    	billMas.setRevisedBillType("PA");
                                    	billMas.getTbWtBillDet().forEach(detailDto ->{
                                    		detailDto.setRevisedBillDate(new Date());
                                    		detailDto.setRevisedBillType("PA");
                                    	});
                                    });
                                }

                                mapping = new AdjustmentBillDetailMappingEntity();
                                mapping.setAdjAmount(detailsEntity.getAdjAdjustedAmount());
                                mapping.setAdjbmId(det.getBdBilldetid());
                                mapping.setOrgId(entity.getOrgId());
                                mapping.setDpDeptId(entity.getDpDeptId());
                                mapping.setLgIpMac(entity.getLgIpMac());
                                mapping.setCreatedBy(entity.getCreatedBy());
                                mapping.setCreatedDate(new Date());
                                mapping.setTaxId(detail.getTaxId());
                                mappingList.add(mapping);
                                detailsEntity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                                detailsEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                                detailsEntity.setCreatedDate(new Date());
                                detailsEntity.setLgIpMac(Utility.getMacAddress());
                                detailsEntity.setBdBilldetid(det.getBdBilldetid());
                                detailsEntity.setBmIdNo(det.getBmIdno());
                                amount += detailsEntity.getAdjAdjustedAmount();
                                entity.getAdjDetail().add(detailsEntity);

                            }
                        }
                    } else {
                        detailsEntity.setAdjAdjustedFlag(constantNO);
                        detailsEntity.setAdjBalanceAmount(detailsEntity.getAdjAmount());
                        detailsEntity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                        detailsEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        detailsEntity.setCreatedDate(new Date());
                        detailsEntity.setLgIpMac(Utility.getMacAddress());
                        amount += detailsEntity.getAdjAdjustedAmount();
                        entity.getAdjDetail().add(detailsEntity);
                        
                    }
                }
            }
            final List<TbBillDet> current = bill.getTbWtBillDet();
            if ((current != null) && !current.isEmpty()) {
                for (final TbBillDet curdet : current) {
                    if (previous != null) {
                        for (final TbBillDet arrdet : previous) {
                            if (curdet.getTaxId().longValue() == arrdet.getTaxId().longValue()) {
                                curdet.setBdPrvBalArramt(arrdet.getBdPrvBalArramt() + arrdet.getBdCurBalTaxamt());
                                curdet.setBdPrvArramt(arrdet.getBdPrvArramt() + arrdet.getBdCurTaxamt());
                            }
                        }
                    }
                }
                previous = current;
            }
            if (amount > 0d) {
                if (constantNO.equals(adjustmentDto.getAdjType())) {
                    final double adjAmt = bill.getBmTotalBalAmount() - amount;
                    if (adjAmt >= 0d) {
                        bill.setBmTotalBalAmount(adjAmt);
                    } else {
                        bill.setBmTotalBalAmount(0d);
                    }
                } else {
                    bill.setBmTotalBalAmount(bill.getBmTotalBalAmount() + amount);
                }
            }
        }

        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
    		(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(adjustmentDto.getDpDeptId()))) &&
        		positive.equals(adjustmentDto.getAdjType())) {
        	// do nothing
		} /*
			 * else
			 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
			 * MainetConstants.ENV_ASCL) &&
			 * (MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(
			 * adjustmentDto.getDpDeptId()))) &&
			 * positive.equals(adjustmentDto.getAdjType())) { // do nothing
			 * 
			 * }
			 */else {
        	
        	 if (billlist.get(0).getBmIdno() > 0d) {
                 updateAdjustedBillModuleWise(billlist, adjustmentDto.getDpDeptId());
             }
        }
       

        adjustmentEntryRepository.save(entity);
        if (!mappingList.isEmpty()) {
            for (final AdjustmentBillDetailMappingEntity mapping : mappingList) {
                for (final AdjustmentDetailEntity adjDetail : entity.getAdjDetail()) {
                    if (mapping.getTaxId().equals(adjDetail.getTaxId()) && mapping.getAdjId() == null) {
						List<AdjustmentBillDetailMappingEntity> ajdjList = mappingList.stream().filter(map -> (map.getAdjId() !=null && map.getAdjId().equals(adjDetail.getAdjDetId())))
								.collect(Collectors.toList());
                    	if(CollectionUtils.isEmpty(ajdjList)) {
                    		 mapping.setAdjId(adjDetail.getAdjDetId());
                    	}
                    }
                }
            }
            adjustmentBillDetailMappingRepository.save(mappingList);
        }
        return true;
    }

    private List<TbBillMas> updateAdjustedBillModuleWise(final List<TbBillMas> billlist, final Long dpDeptId) {
        List<TbBillMas> result = null;
        try {
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(dpDeptId);
            serviceClassName = messageSource.getMessage(
                    "Bill." + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.updateAdjustedCurrentBill(billlist);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in updating bill for Adjustment entry for Department: " + dpDeptId, e);
        }
        return result;

    }

    @Override
    @Transactional
    public List<AdjustmentMasterDTO> fetchHistory(final String refNo, final Long dpDeptId) {
        final List<AdjustmentMasterEntity> adj = adjustmentEntryRepository.fetchHistory(refNo, dpDeptId);
        final List<AdjustmentMasterDTO> result = new ArrayList<>(0);
        List<AdjustmentDetailDTO> detailresult = null;
        AdjustmentMasterDTO dto = null;
        AdjustmentDetailDTO detalDto = null;
        if ((adj != null) && !adj.isEmpty()) {
            for (final AdjustmentMasterEntity entity : adj) {
                dto = new AdjustmentMasterDTO();
                detailresult = new ArrayList<>(0);
                BeanUtils.copyProperties(entity, dto);
                for (final AdjustmentDetailEntity detail : entity.getAdjDetail()) {
                    detalDto = new AdjustmentDetailDTO();
                    BeanUtils.copyProperties(detail, detalDto);
                    detalDto.setTaxDesc(tbTaxMasService.findTaxDescByTaxIdAndOrgId(detalDto.getTaxId(), detalDto.getOrgId()));
                    detailresult.add(detalDto);
                }
                dto.setAdjDetailDto(detailresult);
                if (positive.equals(dto.getAdjType())) {
                    dto.setAdjType(MainetConstants.AdjustmentType.POSITIVE);
                } else {
                    dto.setAdjType(MainetConstants.AdjustmentType.NEGATIVE);
                }
                result.add(dto);
            }
        }
        return result;
    }

    private List<AdjustmentMasterEntity> fetchModuleWiseAdjustmentByUniqueIds(final Long deptId, final List<String> uniqueIds,
            final long orgid) {
        final List<AdjustmentMasterEntity> adj = adjustmentEntryRepository.fetchModuleWiseAdjustmentByUniqueIds(deptId, uniqueIds,
                orgid);
        return adj;
    }

    @Override
    @Transactional
    public void updateAdjustedAdjustmentData(final List<AdjustmentMasterEntity> adjustmentEntity) {
        adjustmentEntryRepository.save(adjustmentEntity);
    }

    @Override
    public Map<Long, List<AdjustmentMasterEntity>> fetchAllAdjustmentEntry(final List<String> uniqueId, final long orgid,
            final Long deptId) {
        final List<AdjustmentMasterEntity> entity = fetchModuleWiseAdjustmentByUniqueIds(deptId, uniqueId,
                orgid);
        final Map<Long, List<AdjustmentMasterEntity>> adjustment = new HashMap<>(0);
        List<AdjustmentMasterEntity> beans = null;
        if ((entity != null) && !entity.isEmpty()) {
            for (final AdjustmentMasterEntity adjEntity : entity) {
                beans = adjustment.get(Long.valueOf(adjEntity.getAdjRefNo()));
                if (beans == null) {
                    beans = new ArrayList<>(0);
                }
                beans.add(adjEntity);
                adjustment.put(Long.valueOf(adjEntity.getAdjRefNo()), beans);
            }
        }
        return adjustment;
    }
    
    @Override
    @Transactional
    public String getPropNoByOldPropNo(final String oldPropNo, final Long orgId,Long deptId) {
        String result = null;
        try {
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final String deptCode = departmentService.getDeptCode(deptId);
            serviceClassName = messageSource.getMessage(
                    ApplicationSession.getInstance().getMessage("bill.lbl.Bills") + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.getPropNoByOldPropNo(oldPropNo, orgId);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching property nu,ber by old prop no " + deptId, e);
        }
        return result;
    }
    
    @Override
    @Transactional
    public Long getBillMethodIdByPropNo(final String oldPropNo, final Long orgId, final String deptCode) {
        Long result = null;
        try {
        	Class<?> clazz = null;
            Object dynamicServiceInstance = null;
            String serviceClassName = "com.abm.mainet.property.service.PrimaryPropertyServiceImpl";
            
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            final Method method = ReflectionUtils.findMethod(clazz,
                    "getBillMethodIdByPropNo",
                    new Class[] { String.class, Long.class });
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);
            result = (Long) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                    new Object[] { oldPropNo, orgId });
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching billing method by property number " + deptCode, e);
        }
        return result;
    }
    
    @Override
    @Transactional
    public List<String>  getFlatNoIdByPropNo(final String oldPropNo, final Long orgId, final String deptCode) {
    	List<String>  result = null;
        try {
        	 Class<?> clazz = null;
             Object dynamicServiceInstance = null;
             String serviceClassName = "com.abm.mainet.property.service.PrimaryPropertyServiceImpl";
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            final Method method = ReflectionUtils.findMethod(clazz,
                    "getFlatNoIdByPropNo",
                    new Class[] { String.class, Long.class });
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);
            
            result = (List<String>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                    new Object[] { oldPropNo, orgId });
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching flat no by property number " + deptCode, e);
        }
        return result;
    }
    
    @Override
    @Transactional
    public List<TbBillMas> fetchAllBillByPropNoAndFlatNo(final Long deptId, final String ccnNoOrPropNo, final String flatNo, final Long orgId) {
        List<TbBillMas> result = null;
        try {
        	 Class<?> clazz = null;
             Object dynamicServiceInstance = null;
        	String serviceClassName = "com.abm.mainet.property.service.PropertyMainBillServiceImpl";
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            final Method method = ReflectionUtils.findMethod(clazz,
                    "fetchAllBillByPropNoAndFlatNo",
                    new Class[] { String.class, String.class, long.class });
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);
            
            result = (List<TbBillMas>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                    new Object[] { ccnNoOrPropNo , flatNo, orgId });
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching bill for Adjustment entry for Department: " + deptId, e);
        }
        return result;
    }

	@Override
	public CustomerInfoDTO getCsmrInfoDetails(String ccnNoOrPropNo, Long orgId) {
		CustomerInfoDTO result = null;
	        try {
	        	Class<?> clazz = null;
	            Object dynamicServiceInstance = null;
	        	String serviceClassName = "com.abm.mainet.water.service.NewWaterConnectionServiceImpl";
	            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
	                    .getClassLoader());
	            final Method method = ReflectionUtils.findMethod(clazz,
						ApplicationSession.getInstance().getMessage("common.water.csmrinfo"),
						new Class[] {String.class, Long.class });
	            
	            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
	                    .autowire(clazz, 4, false);
	            
	            result = (CustomerInfoDTO) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
	                    new Object[] { ccnNoOrPropNo, orgId });
	        } catch (LinkageError | Exception e) {
	        	LOGGER.error("Exception in fetching csmr info for conNo: " + ccnNoOrPropNo + " " + e.getMessage());
	        }
	        return result;
	}

	@Override
	public boolean saveAdjustmentEntryDataForLastBill(List<TbBillMas> billlist, AdjustmentMasterDTO adjustmentDto) {


        final AdjustmentMasterEntity entity = new AdjustmentMasterEntity();
        BeanUtils.copyProperties(adjustmentDto, entity);
		if (billlist.get(0).getCsIdn() != null) {
			entity.setAdjRefNo(billlist.get(0).getCsIdn().toString());
		} else if (StringUtils.isNotBlank(billlist.get(0).getPropNo())) {
			entity.setAdjRefNo(billlist.get(0).getPropNo());
		}
        entity.setAdjDate(new Date());
        entity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        entity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        entity.setCreatedDate(new Date());
        entity.setLgIpMac(Utility.getMacAddress());
        AdjustmentDetailEntity detailsEntity = null;
        final List<AdjustmentBillDetailMappingEntity> mappingList = new ArrayList<>(0);
        List<TbBillMas> lastBilllist = new ArrayList<TbBillMas>();
        lastBilllist.add(billlist.get(billlist.size() - 1));
        for (final TbBillMas bill : lastBilllist) {
            Double amount = 0d;
            List<TbBillDet> previous = null;
            for (final AdjustmentDetailDTO detail : adjustmentDto.getAdjDetailDto()) {
                if (detail.getAdjAmount() > 0d) {
                    detailsEntity = new AdjustmentDetailEntity();
                    BeanUtils.copyProperties(detail, detailsEntity);
                    detailsEntity.setAdjId(entity);
                    if ((bill.getTbWtBillDet() != null) && !bill.getTbWtBillDet().isEmpty()) {
                        AdjustmentBillDetailMappingEntity mapping = null;
                        for (final TbBillDet det : bill.getTbWtBillDet()) {
                            if (det.getTaxId().equals(detail.getTaxId())) {
                                if (constantNO.equals(adjustmentDto.getAdjType())) {
                                    final double adjAmount = det.getBdCurBalTaxamt() - detail.getAdjAmount();
                                    if (adjAmount >= 0d) {
                                        det.setBdCurBalTaxamt(adjAmount);
                                        detailsEntity.setAdjAdjustedAmount(detail.getAdjAmount());
                                        detailsEntity.setAdjBalanceAmount(0d);
                                        detailsEntity.setAdjAdjustedFlag(constantYES);
                                        detail.setAdjAmount(0);
                                    } else {
                                        detailsEntity.setAdjAdjustedAmount(det.getBdCurBalTaxamt());
                                        det.setBdCurBalTaxamt(0d);
                                        detailsEntity.setAdjBalanceAmount(Math.abs(adjAmount));
                                        detailsEntity.setAdjAdjustedFlag(constantNO);
                                        detail.setAdjAmount(Math.abs(adjAmount));
                                    }
                                } else {
                                	bill.setBmPaidFlag(MainetConstants.FlagN);
                                    if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
                                		(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(adjustmentDto.getDpDeptId())))) {
                                    	//do not adjust in current bill for positive adjustment for water department SKDCL
                                        detailsEntity.setAdjAdjustedFlag(constantNO);
                                        detailsEntity.setAdjBalanceAmount(detail.getAdjAmount());
                                        detailsEntity.setAdjAdjustedAmount(0d);

									} else {
                                    	det.setBdCurBalTaxamt(detail.getAdjAmount() + det.getBdCurBalTaxamt());
	                                    det.setBdCurTaxamt(detail.getAdjAmount() + det.getBdCurTaxamt());
                                        detailsEntity.setAdjAdjustedFlag(constantYES);
                                        detailsEntity.setAdjBalanceAmount(0d);
                                        detailsEntity.setAdjAdjustedAmount(detail.getAdjAmount());
                                    }
                                    detail.setAdjAmount(0);
                                    billlist.forEach(billMas ->{
                                    	billMas.setRevisedBillDate(new Date());
                                    	billMas.setRevisedBillType("PA");
                                    	billMas.getTbWtBillDet().forEach(detailDto ->{
                                    		detailDto.setRevisedBillDate(new Date());
                                    		detailDto.setRevisedBillType("PA");
                                    	});
                                    });
                                }

                                mapping = new AdjustmentBillDetailMappingEntity();
                                mapping.setAdjAmount(detailsEntity.getAdjAdjustedAmount());
                                mapping.setAdjbmId(det.getBdBilldetid());
                                mapping.setOrgId(entity.getOrgId());
                                mapping.setDpDeptId(entity.getDpDeptId());
                                mapping.setLgIpMac(entity.getLgIpMac());
                                mapping.setCreatedBy(entity.getCreatedBy());
                                mapping.setCreatedDate(new Date());
                                mapping.setTaxId(detail.getTaxId());
                                mappingList.add(mapping);
                                detailsEntity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                                detailsEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                                detailsEntity.setCreatedDate(new Date());
                                detailsEntity.setLgIpMac(Utility.getMacAddress());
                                detailsEntity.setBdBilldetid(det.getBdBilldetid());
                                detailsEntity.setBmIdNo(det.getBmIdno());
                                amount += detailsEntity.getAdjAdjustedAmount();
                                entity.getAdjDetail().add(detailsEntity);

                            }
                        }
                    } else {
                        detailsEntity.setAdjAdjustedFlag(constantNO);
                        detailsEntity.setAdjBalanceAmount(detailsEntity.getAdjAmount());
                        detailsEntity.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                        detailsEntity.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        detailsEntity.setCreatedDate(new Date());
                        detailsEntity.setLgIpMac(Utility.getMacAddress());
                        amount += detailsEntity.getAdjAdjustedAmount();
                        entity.getAdjDetail().add(detailsEntity);
                        
                    }
                }
            }
            final List<TbBillDet> current = bill.getTbWtBillDet();
            if ((current != null) && !current.isEmpty()) {
                for (final TbBillDet curdet : current) {
                    if (previous != null) {
                        for (final TbBillDet arrdet : previous) {
                            if (curdet.getTaxId().longValue() == arrdet.getTaxId().longValue()) {
                                curdet.setBdPrvBalArramt(arrdet.getBdPrvBalArramt() + arrdet.getBdCurBalTaxamt());
                                curdet.setBdPrvArramt(arrdet.getBdPrvArramt() + arrdet.getBdCurTaxamt());
                            }
                        }
                    }
                }
                previous = current;
            }
            if (amount > 0d) {
                if (constantNO.equals(adjustmentDto.getAdjType())) {
                    final double adjAmt = bill.getBmTotalBalAmount() - amount;
                    if (adjAmt >= 0d) {
                        bill.setBmTotalBalAmount(adjAmt);
                    } else {
                        bill.setBmTotalBalAmount(0d);
                    }
                } else {
                    bill.setBmTotalBalAmount(bill.getBmTotalBalAmount() + amount);
                }
            }
        }

        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
    		(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(adjustmentDto.getDpDeptId()))) &&
        		positive.equals(adjustmentDto.getAdjType())) {
        	// do nothing
		} /*
			 * else
			 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
			 * MainetConstants.ENV_ASCL) &&
			 * (MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(
			 * adjustmentDto.getDpDeptId()))) &&
			 * positive.equals(adjustmentDto.getAdjType())) { // do nothing
			 * 
			 * }
			 */else {
        	
        	 if (billlist.get(0).getBmIdno() > 0d) {
                 updateAdjustedBillModuleWise(billlist, adjustmentDto.getDpDeptId());
             }
        }
       

        adjustmentEntryRepository.save(entity);
        if (!mappingList.isEmpty()) {
            for (final AdjustmentBillDetailMappingEntity mapping : mappingList) {
                for (final AdjustmentDetailEntity adjDetail : entity.getAdjDetail()) {
                    if (mapping.getTaxId().equals(adjDetail.getTaxId()) && mapping.getAdjId() == null) {
						List<AdjustmentBillDetailMappingEntity> ajdjList = mappingList.stream().filter(map -> (map.getAdjId() !=null && map.getAdjId().equals(adjDetail.getAdjDetId())))
								.collect(Collectors.toList());
                    	if(CollectionUtils.isEmpty(ajdjList)) {
                    		 mapping.setAdjId(adjDetail.getAdjDetId());
                    	}
                    }
                }
            }
            adjustmentBillDetailMappingRepository.save(mappingList);
        }
        return true;
    
    
	}

	@Override
	public PropertyDetailDto getPropertyDetails(String propertyNo, Long orgId) {
		// TODO Auto-generated method stub
		 PropertyDetailDto detailDTO = null;
        PropertyInputDto propInputDTO = new PropertyInputDto();
        propInputDTO.setPropertyNo(propertyNo);
        propInputDTO.setOrgId(orgId);
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                ServiceEndpoints.PROP_BY_PROP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

            detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
        }
        return detailDTO;
	}
    
}
