package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.ProBillDetHisEntity;
import com.abm.mainet.property.domain.ProBillMstHisEntity;
import com.abm.mainet.property.domain.ProvisionalBillDetEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.domain.TemporaryProvisionalBillDetEntity;
import com.abm.mainet.property.domain.TemporaryProvisionalBillMasEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalBillDetDto;
import com.abm.mainet.property.dto.ProvisionalBillMasDto;
import com.abm.mainet.property.repository.ProvisionalBillDetRepository;
import com.abm.mainet.property.repository.ProvisionalBillRepository;
import com.abm.mainet.property.repository.TemporaryProvisionalBillRepository;

@Service
public class ProvisionalBillServiceImpl implements IProvisionalBillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionalBillServiceImpl.class);

    @Autowired
    private ProvisionalBillRepository provisionalBillRepository;

    @Autowired
    private ProvisionalBillDetRepository provisionalBillDetRepository;

    @Resource
    private AuditService auditService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private TemporaryProvisionalBillRepository temporaryProvisionalBillRepository;

    @Autowired
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;

    @Autowired
    private DepartmentService departmentService;

    @Override
    @Transactional
    public List<Long> saveAndUpdateProvisionalBill(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
            Map<Long, Long> assIdMap, List<ProvisionalBillMasEntity> provBillList, String ipAddress) {
        List<Long> bmIdNos = new ArrayList<>();
        // Provisional bill mas
        List<ProvisionalBillMasEntity> billMasEntityList = new ArrayList<>();

        billMasList.forEach(billMasDto -> {
            final ProvisionalBillMasEntity billMasEntity = new ProvisionalBillMasEntity();
            BeanUtils.copyProperties(billMasDto, billMasEntity);
            billMasEntity.setFlatNo(billMasDto.getFlatNo());
            billMasEntity.setOrgid(orgId);
            billMasEntity.setPropNo(propNo);
            billMasDto.setPropNo(propNo);
            StringBuilder logcalPropNo = new StringBuilder();
            logcalPropNo.append(propNo);
            if(StringUtils.isNotBlank(billMasDto.getFlatNo())) {
            	 logcalPropNo.append(MainetConstants.operator.UNDER_SCORE);
            	 logcalPropNo.append(billMasDto.getFlatNo());
            }
            billMasDto.setLogicalPropNo(logcalPropNo.toString());
            billMasEntity.setLogicalPropNo(logcalPropNo.toString());
            if (billMasEntity.getBmIdno() == 0L) {
                final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
                        MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.BILL_TABLE.PropertyTable,
                        MainetConstants.BILL_TABLE.PropertyColumn, orgId,
                        MainetConstants.RECEIPT_MASTER.Reset_Type, null);
                final long bmIdNo = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_MAS", "proBmIdno", null,
                        null);
                billMasEntity.setBmIdno(bmIdNo);
                billMasDto.setBmIdno(bmIdNo);
                billMasEntity.setBmNo(bmNumber.toString());
                billMasDto.setBmNo(bmNumber.toString());
                if (assIdMap != null) {
                    billMasEntity.setAssId(assIdMap.get(billMasEntity.getBmYear()));
                }
                billMasEntity.setUserId(empId);
                billMasEntity.setLgIpMac(ipAddress);
                billMasEntity.setLmoddate(new Date());
            } else {
                billMasEntity.setUpdatedBy(empId);
                billMasEntity.setLgIpMacUpd(ipAddress);
                billMasEntity.setUpdatedDate(new Date());
            }
            // Provisional bill detail
            final List<ProvisionalBillDetEntity> billDetEntityList = new ArrayList<>();

            billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection sequence
            billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                ProvisionalBillDetEntity billDetEntity = new ProvisionalBillDetEntity();
                BeanUtils.copyProperties(billDetDto, billDetEntity);
                billDetEntity.setBmIdNo(billMasEntity);
                billDetEntity.setOrgid(orgId);
                if (billDetEntity.getBdBilldetid() == 0L) {
                    final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_DET",
                            "proBdBilldetid", null,
                            null);
                    billDetEntity.setBdBilldetid(bdBilldetid);
                    billDetDto.setBdBilldetid(billDetEntity.getBdBilldetid());
                    billDetEntity.setUserId(empId);
                    billDetEntity.setLgIpMac(ipAddress);
                    billDetEntity.setLmoddate(new Date());
                } else {
                    billDetEntity.setUpdatedBy(empId);
                    billDetEntity.setLgIpMacUpd(ipAddress);
                    billDetEntity.setUpdatedDate(new Date());
                }
                billDetEntityList.add(billDetEntity);
            });
            billMasEntity.setProvisionalBillDetEntityList(billDetEntityList);
            billMasEntityList.add(billMasEntity);
        });
        provisionalBillRepository.save(billMasEntityList);
        billMasEntityList.forEach(billMas -> {
            if (provBillList != null) {
                provBillList.add(billMas);
            }
            bmIdNos.add(billMas.getBmIdno());
        });
        return bmIdNos;
    }

    @Transactional
    @Override
    public List<TbBillMas> getProvisionalBillMasByPropertyNo(String propertyNo, Long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>();
        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
                .getProvisionalBillMasByPropertyNo(propertyNo, orgId);
        if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
            provisionalBillMasEntityList.forEach(billMasEntity -> {
                TbBillMas billMas = getBillMasDtoMapedWithBillMasEnt(billMasEntity, orgId);
                billMasList.add(billMas);
            });
        }
        return billMasList;
    }

    private TbBillMas getBillMasDtoMapedWithBillMasEnt(ProvisionalBillMasEntity billMasEntity, Long orgId) {
        TbBillMas billMas = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);

        Map<Long, Long> map = new LinkedHashMap<>();
        if (billMasEntity != null) {
            List<Object[]> list = tbTaxMasJpaRepository.getTaxIdAndTaxSubCategory(billMasEntity.getOrgid(), deptId);
            for (Object[] objects : list) {
                if (objects != null && (objects[0] != null && objects[1] != null)) {
                    map.put((Long.valueOf(objects[0].toString())), (Long.valueOf(objects[1].toString())));
                }
            }
            billMas = new TbBillMas();
            BeanUtils.copyProperties(billMasEntity, billMas);
            List<TbBillDet> billDetList = new ArrayList<>();
            billMasEntity.getProvisionalBillDetEntityList().forEach(billDetEntity -> {
                TbBillDet billDet = new TbBillDet();
                billDet.setBmIdno(billMasEntity.getBmIdno());
                map.forEach((taxId, taxSubCategory) -> {
                    if (billDetEntity.getTaxId() != null && billDetEntity.getTaxId().equals(taxId)) {
                        billDet.setTaxSubCategory(taxSubCategory);
                    }
                });
                BeanUtils.copyProperties(billDetEntity, billDet);
                billDet.setBdCsmp(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()));
                billDetList.add(billDet);
            });
            billMas.setTbWtBillDet(billDetList);
        }
        return billMas;
    }

    @Override
    public List<TbBillMas> getProvisionalBillDtoByProvisionalBillEntity(
            List<ProvisionalBillMasEntity> provisionalBillMasEntityList) {
        List<TbBillMas> billMasDtoList = new ArrayList<>();
        provisionalBillMasEntityList.forEach(provisionalBillMasEntity -> {
            TbBillMas billMasDto = new TbBillMas();
            BeanUtils.copyProperties(provisionalBillMasEntity, billMasDto);
            List<TbBillDet> billDetDtoList = new ArrayList<>();
            provisionalBillMasEntity.getProvisionalBillDetEntityList().forEach(provisionalBillDetEntity -> {
                TbBillDet billDetDto = new TbBillDet();
                BeanUtils.copyProperties(provisionalBillDetEntity, billDetDto);
                billDetDtoList.add(billDetDto);
            });
            billMasDto.setTbWtBillDet(billDetDtoList);
            billMasDtoList.add(billMasDto);
        });
        return billMasDtoList;
    }

    @Override
    public boolean checkBillPresentFromProvBillWithDto(List<ProvisionalBillMasDto> provisionalBillMasDtoList) {
        for (ProvisionalBillMasDto provisionalBillMasDto : provisionalBillMasDtoList) {
            for (ProvisionalBillDetDto ProvisionalBillDetDto : provisionalBillMasDto.getProvisionalBillDetDtoList()) {
                if (ProvisionalBillDetDto.getProBdBillflag().equals(MainetConstants.Y_FLAG)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkBillPresentFromProvBillWithEntity(List<ProvisionalBillMasEntity> provisionalBillMasEntityList) {
        for (ProvisionalBillMasEntity provisionalBillMasEntity : provisionalBillMasEntityList) {
            for (ProvisionalBillDetEntity ProvisionalBillDetEntity : provisionalBillMasEntity.getProvisionalBillDetEntityList()) {
                if (ProvisionalBillDetEntity.getBdBillflag().equals(MainetConstants.Y_FLAG)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalBillMasEntity getLatestProvisionalBillByPropNo(Long propertyId, Long orgId) {
        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository.getLatestBillByPropNo(propertyId,
                orgId);
        return provisionalBillMasEntityList.get(provisionalBillMasEntityList.size() - 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> bmIDno, Long orgid) {
        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
                .fetchListOfBillsByPrimaryKey(bmIDno, orgid);
        return getProvisionalBillDtoByProvisionalBillEntity(provisionalBillMasEntityList);
    }

    @Override
    @Transactional
    public void updateAccountPostingFlag(List<Long> bmIdNo, String flag) {
        provisionalBillRepository.updateAccountPostingFlag(bmIdNo, flag);

    }

    @Override
    public void copyDataFromProvisionalBillDetailToHistory(List<TbBillMas> oldBillMasList,String updateFlag) {
        oldBillMasList.forEach(billMas -> { // Copy complete data of Bill from provisional to History
            ProBillMstHisEntity proBillMstHisEnt = new ProBillMstHisEntity();
            try {
                auditService.createHistory(billMas, proBillMstHisEnt);
                proBillMstHisEnt.sethStatus(updateFlag);
            } catch (Exception ex) {
                throw new FrameworkException("could ot make entry for" + billMas, ex);
            }
            billMas.getTbWtBillDet().forEach(billDet -> {
                ProBillDetHisEntity proBillDetHisEnt = new ProBillDetHisEntity();
                try {
                    auditService.createHistory(billDet, proBillDetHisEnt);
                } catch (Exception ex) {
                    throw new FrameworkException("could ot make entry for" + billDet, ex);
                }
            });
        });
    }

    @Override
    @Transactional
    public void deleteDetailsTaxes(List<Long> deleteTaxes) {
        for (Long detId : deleteTaxes) {
            provisionalBillDetRepository.delete(detId);
        }
    }

    @Override
    @Transactional
    public List<TbBillMas> fetchNotPaidBillForProAssessment(String propNo, Long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>();
        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
                .fetchNotPaidBillForProAssessment(propNo, orgId, "N");
        if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
            provisionalBillMasEntityList.forEach(billMasEntity -> {
                TbBillMas billMas = new TbBillMas();
                BeanUtils.copyProperties(billMasEntity, billMas);
                List<TbBillDet> billDetList = new ArrayList<>();
                billMasEntity.getProvisionalBillDetEntityList().forEach(billDetEntity -> {
                    TbBillDet billDet = new TbBillDet();
                    billDet.setBmIdno(billMasEntity.getBmIdno());
                    BeanUtils.copyProperties(billDetEntity, billDet);
                    billDetList.add(billDet);
                });
                billMas.setTbWtBillDet(billDetList);
                billMasList.add(billMas);
            });
        }
        return billMasList;
    }

    @Override
    @Transactional
    public void deleteProvisionalBillsById(List<TbBillMas> billMasList) {
    	if(CollectionUtils.isNotEmpty(billMasList)) {
    		for (TbBillMas billMas : billMasList)
                provisionalBillRepository.delete(billMas.getBmIdno());
    	}
    }

    @Override
    @Transactional
    public void deleteProvisionalBillsWithEntityById(List<ProvisionalBillMasEntity> provBillEntList) {
        for (ProvisionalBillMasEntity billMasEnt : provBillEntList)
            provisionalBillRepository.delete(billMasEnt.getBmIdno());
    }

    @Override
    @Transactional(readOnly = true)
    public TbBillMas fetchBillFromBmIdNo(Long bmIdno, Long orgId) {
        return getBillMasDtoMapedWithBillMasEnt(provisionalBillRepository.fetchBillFromBmIdNo(bmIdno), orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public int getCountOfBillWithoutDESByPropNo(String propNo, Long orgId) {
        return provisionalBillRepository.getCountOfBillWithoutDESByPropNo(propNo, orgId);

    }

    @Override
    public void saveAndUpdateTemporaryProvisionalBill(List<TbBillMas> billMasList, Long orgId, Long empId,
            String ipAddress) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "saveAndUpdateTemporaryProvisionalBill() method");
        List<Long> bmIdNos = new ArrayList<>();
        // Provisional bill mas
        List<TemporaryProvisionalBillMasEntity> billMasEntityList = new ArrayList<>();
        billMasList.forEach(billMasDto -> {
            final TemporaryProvisionalBillMasEntity billMasEntity = new TemporaryProvisionalBillMasEntity();
            BeanUtils.copyProperties(billMasDto, billMasEntity);
            billMasEntity.setOrgid(orgId);
            billMasEntity.setPropNo(billMasDto.getPropNo());
            // billMasDto.setPropNo(propNo);
            if (billMasEntity.getBmIdno() == 0L) {
                final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
                        MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        "TB_AS_TEMP_PRO_BILL_MAS",
                        "Temp_pro_bm_no", orgId,
                        MainetConstants.RECEIPT_MASTER.Reset_Type, null);
                billMasEntity.setBmNo(bmNumber.toString());
                billMasEntity.setNewBillFlag(MainetConstants.FlagY);
                billMasDto.setBmNo(bmNumber.toString());
                /*
                 * if (assIdMap != null) { billMasEntity.setAssId(assIdMap.get(billMasEntity.getBmYear())); }
                 */
                billMasEntity.setUserId(empId);
                billMasEntity.setLgIpMac(ipAddress);
                billMasEntity.setLmoddate(new Date());

            } else {
                billMasEntity.setBmIdno(0);
                billMasEntity.setUpdatedBy(empId);
                billMasEntity.setLgIpMacUpd(ipAddress);
                billMasEntity.setUpdatedDate(new Date());
            }
            // Provisional bill detail
            final List<TemporaryProvisionalBillDetEntity> billDetEntityList = new ArrayList<>();

            billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection sequence
            billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                TemporaryProvisionalBillDetEntity billDetEntity = new TemporaryProvisionalBillDetEntity();
                BeanUtils.copyProperties(billDetDto, billDetEntity);
                billDetEntity.setBmIdNo(billMasEntity);
                billDetEntity.setOrgid(orgId);
                if (billDetEntity.getBdBilldetid() == 0L) {
                    billDetDto.setBdBilldetid(billDetEntity.getBdBilldetid());
                    billDetEntity.setUserId(empId);
                    billDetEntity.setLgIpMac(ipAddress);
                    billDetEntity.setLmoddate(new Date());
                } else {
                    billDetEntity.setBdBilldetid(0);
                    billDetDto.setBdBilldetid(billDetEntity.getBdBilldetid());
                    billDetEntity.setUserId(empId);
                    billDetEntity.setLgIpMac(ipAddress);
                    billDetEntity.setLmoddate(new Date());
                }
                billDetEntityList.add(billDetEntity);
            });
            billMasEntity.setTemporaryProvisionalBillDetEntity(billDetEntityList);
            billMasEntityList.add(billMasEntity);
        });
        temporaryProvisionalBillRepository.save(billMasEntityList);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "saveAndUpdateTemporaryProvisionalBill() method");
        /*
         * billMasEntityList.forEach(billMas -> { if (billMas.getNewBillFlag() != null &&
         * billMas.getNewBillFlag().equals(MainetConstants.FlagY)) { bmIdNos.add(billMas.getBmIdno()); // } });
         */
        // return bmIdNos;

    }

    @Override
    @Transactional
    public void deleteTemporaryProvisionalBillsWithEntityById(Long empId, Long orgId) {
        temporaryProvisionalBillRepository.deleteRecordFromTempProDet(empId, orgId);
        temporaryProvisionalBillRepository.deleteRecords(empId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> fetchProvisionalNotPaidBillByPropNo(String propNo, Long orgId) {
        return provisionalBillRepository.fetchProvisionalNotPaidBillByPropNo(propNo, orgId);
    }

	@Override
	@Transactional
	public List<TbBillMas> getProvisionalBillMasByPropertyNoAndFlatNo(String propertyNo, String flatNo, long orgId) {
		List<TbBillMas> billMasList = new ArrayList<>();
        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
                .getProvisionalBillMasByPropertyNoFlatNo(propertyNo,flatNo, orgId);
        if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
            provisionalBillMasEntityList.forEach(billMasEntity -> {
                TbBillMas billMas = getBillMasDtoMapedWithBillMasEnt(billMasEntity, orgId);
                billMasList.add(billMas);
            });
        }
        return billMasList;
	}

	@Override
	@Transactional
	public List<TbBillMas> fetchNotPaidBillForProAssessmentWithFlatNo(String propNo, String flatNo, Long orgId) {
		List<TbBillMas> billMasList = new ArrayList<>();
		List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
				.fetchNotPaidBillForProAssessmentWithFlatNo(propNo, orgId, "N", flatNo);
		if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
			provisionalBillMasEntityList.forEach(billMasEntity -> {
				TbBillMas billMas = new TbBillMas();
				BeanUtils.copyProperties(billMasEntity, billMas);
				List<TbBillDet> billDetList = new ArrayList<>();
				billMasEntity.getProvisionalBillDetEntityList().forEach(billDetEntity -> {
					TbBillDet billDet = new TbBillDet();
					billDet.setBmIdno(billMasEntity.getBmIdno());
					BeanUtils.copyProperties(billDetEntity, billDet);
					billDetList.add(billDet);
				});
				billMas.setTbWtBillDet(billDetList);
				billMasList.add(billMas);
			});
		}
		return billMasList;
	}
	
	   @Transactional
	    @Override
	    public List<TbBillMas> getProvisionalBillMasByPropertyNoAndFinId(String propertyNo, Long orgId, Long finId) {
	        List<TbBillMas> billMasList = new ArrayList<>();
	        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
	                .getProvisionalBillMasByPropertyNoAndBillNo(propertyNo, orgId, finId);
	        if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
	            provisionalBillMasEntityList.forEach(billMasEntity -> {
	                TbBillMas billMas = getBillMasDtoMapedWithBillMasEnt(billMasEntity, orgId);
	                billMasList.add(billMas);
	            });
	        }
	        return billMasList;
	    }

	   @Override
	    @Transactional
	    public List<Long> saveAndUpdateProvisionalBillForReviseBill(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
	            Map<Long, Long> assIdMap, List<ProvisionalBillMasEntity> provBillList, String ipAddress) {
	        List<Long> bmIdNos = new ArrayList<>();
	        // Provisional bill mas
	        List<ProvisionalBillMasEntity> billMasEntityList = new ArrayList<>();

	        billMasList.forEach(billMasDto -> {
	            final ProvisionalBillMasEntity billMasEntity = new ProvisionalBillMasEntity();
	            BeanUtils.copyProperties(billMasDto, billMasEntity);
	            billMasEntity.setFlatNo(billMasDto.getFlatNo());
	            billMasEntity.setOrgid(orgId);
	            billMasEntity.setPropNo(propNo);
	            billMasDto.setPropNo(propNo);
	            if (billMasEntity.getBmIdno() == 0L) {
	                final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
	                        MainetConstants.Property.PROP_DEPT_SHORT_CODE,
	                        MainetConstants.BILL_TABLE.PropertyTable,
	                        MainetConstants.BILL_TABLE.PropertyColumn, orgId,
	                        MainetConstants.RECEIPT_MASTER.Reset_Type, null);
	                final long bmIdNo = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_MAS", "proBmIdno", null,
	                        null);
	                billMasEntity.setBmIdno(bmIdNo);
	                billMasDto.setBmIdno(bmIdNo);
	                billMasEntity.setBmNo(bmNumber.toString());
	                billMasDto.setBmNo(bmNumber.toString());
	                if (assIdMap != null) {
	                    billMasEntity.setAssId(assIdMap.get(billMasEntity.getBmYear()));
	                }
	                billMasEntity.setUserId(empId);
	                billMasEntity.setLgIpMac(ipAddress);
	                billMasEntity.setLmoddate(new Date());
	            } else {
	                billMasEntity.setUpdatedBy(empId);
	                billMasEntity.setLgIpMacUpd(ipAddress);
	                billMasEntity.setUpdatedDate(new Date());
	            }
	            // Provisional bill detail
	            final List<ProvisionalBillDetEntity> billDetEntityList = new ArrayList<>();

	            billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection sequence
	            billMasDto.getTbWtBillDet().forEach(billDetDto -> {
	                ProvisionalBillDetEntity billDetEntity = new ProvisionalBillDetEntity();
	                BeanUtils.copyProperties(billDetDto, billDetEntity);
	                billDetEntity.setBmIdNo(billMasEntity);
	                billDetEntity.setOrgid(orgId);
	                if (billDetEntity.getBdBilldetid() == 0L) {
	                    final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_DET",
	                            "proBdBilldetid", null,
	                            null);
	                    billDetEntity.setBdBilldetid(bdBilldetid);
	                    billDetDto.setBdBilldetid(billDetEntity.getBdBilldetid());
	                    billDetEntity.setUserId(empId);
	                    billDetEntity.setLgIpMac(ipAddress);
	                    billDetEntity.setLmoddate(new Date());
	                } else {
	                    billDetEntity.setUpdatedBy(empId);
	                    billDetEntity.setLgIpMacUpd(ipAddress);
	                    billDetEntity.setUpdatedDate(new Date());
	                }
	                billDetEntityList.add(billDetEntity);
	            });
	            billMasEntity.setProvisionalBillDetEntityList(billDetEntityList);
	            billMasEntityList.add(billMasEntity);
	        });
	        billMasEntityList.forEach(billMas -> {
	            if (provBillList != null) {
	                provBillList.add(billMas);
	            }
	            bmIdNos.add(billMas.getBmIdno());
	        });
	        return bmIdNos;
	    }
	   
	   
	   @Override
	    @Transactional
	    public List<TbBillMas> fetchNotPaidBillForProAssessmentByParentPropNo(String parentPropNo, Long orgId) {
	        List<TbBillMas> billMasList = new ArrayList<>();
	        List<ProvisionalBillMasEntity> provisionalBillMasEntityList = provisionalBillRepository
	                .fetchNotPaidBillForProAssessmentByParentPropNo(parentPropNo, orgId, "N");
	        if (provisionalBillMasEntityList != null && !provisionalBillMasEntityList.isEmpty()) {
	            provisionalBillMasEntityList.forEach(billMasEntity -> {
	                TbBillMas billMas = new TbBillMas();
	                BeanUtils.copyProperties(billMasEntity, billMas);
	                List<TbBillDet> billDetList = new ArrayList<>();
	                billMasEntity.getProvisionalBillDetEntityList().forEach(billDetEntity -> {
	                    TbBillDet billDet = new TbBillDet();
	                    billDet.setBmIdno(billMasEntity.getBmIdno());
	                    BeanUtils.copyProperties(billDetEntity, billDet);
	                    billDetList.add(billDet);
	                });
	                billMas.setTbWtBillDet(billDetList);
	                billMasList.add(billMas);
	            });
	        }
	        return billMasList;
	    }
	   
	@Override
	@Transactional
	public void saveAndUpdateProvBillForBillChange(List<TbBillMas> billMasList, Long orgId, Long empId,
			String ipAddress) {		
		List<ProvisionalBillMasEntity> billMasEntityList = new ArrayList<>();
		billMasList.forEach(billMasDto -> {
			final ProvisionalBillMasEntity billMasEntity = new ProvisionalBillMasEntity();
			BeanUtils.copyProperties(billMasDto, billMasEntity);
			billMasEntity.setFlatNo(billMasDto.getFlatNo());
			billMasEntity.setOrgid(orgId);
			billMasEntity.setPropNo(billMasDto.getPropNo());
			if (billMasEntity.getBmIdno() == 0L) {
				final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
						MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.BILL_TABLE.PropertyTable,
						MainetConstants.BILL_TABLE.PropertyColumn, orgId, MainetConstants.RECEIPT_MASTER.Reset_Type,
						null);
				final long bmIdNo = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_MAS",
						"proBmIdno", null, null);
				billMasEntity.setBmIdno(bmIdNo);
				billMasDto.setBmIdno(bmIdNo);
				billMasEntity.setBmNo(bmNumber.toString());
				billMasDto.setBmNo(bmNumber.toString());
				billMasEntity.setUserId(empId);
				billMasEntity.setLgIpMac(ipAddress);
				billMasEntity.setLmoddate(new Date());
			} else {
				billMasEntity.setUpdatedBy(empId);
				billMasEntity.setLgIpMacUpd(ipAddress);
				billMasEntity.setUpdatedDate(new Date());
			}
			// Provisional bill detail
			final List<ProvisionalBillDetEntity> billDetEntityList = new ArrayList<>();
			billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
			billMasDto.getTbWtBillDet().forEach(billDetDto -> {
				ProvisionalBillDetEntity billDetEntity = new ProvisionalBillDetEntity();
				BeanUtils.copyProperties(billDetDto, billDetEntity);
				billDetEntity.setBmIdNo(billMasEntity);
				billDetEntity.setOrgid(orgId);
				if (billDetEntity.getBdBilldetid() == 0L) {
					final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo("AS", "TB_AS_PRO_BILL_DET",
							"proBdBilldetid", null, null);
					billDetEntity.setBdBilldetid(bdBilldetid);
					billDetDto.setBdBilldetid(billDetEntity.getBdBilldetid());
					billDetEntity.setUserId(empId);
					billDetEntity.setLgIpMac(ipAddress);
					billDetEntity.setLmoddate(new Date());
				} else {
					billDetEntity.setUpdatedBy(empId);
					billDetEntity.setLgIpMacUpd(ipAddress);
					billDetEntity.setUpdatedDate(new Date());
				}
				billDetEntityList.add(billDetEntity);
			});
			billMasEntity.setProvisionalBillDetEntityList(billDetEntityList);
			billMasEntityList.add(billMasEntity);
		});
		provisionalBillRepository.save(billMasEntityList);
	}
	
	@Override
	@Transactional
	public List<TbBillMas> getProvisionalBillMasterList(ProvisionalAssesmentMstDto provDto, String flatNo,
			Map<Long, String> taxMap) {
		List<TbBillMas> billMas = new ArrayList<>(0);
		List<ProvisionalBillMasEntity> proBill = provisionalBillRepository
				.fetchBillSForViewProperty(provDto.getAssNo() + MainetConstants.operator.UNDER_SCORE + flatNo);
		if (proBill != null && !proBill.isEmpty()) {
			proBill.forEach(bill -> {
				TbBillMas b = new TbBillMas();
				BeanUtils.copyProperties(bill, b);
				b.setBmTotalAmount(0);
				b.setBmRemarks(Utility.dateToString(b.getBmDuedate()));
				if (b.getBmBilldt() != null) {
					b.setBmBilldtString(Utility.dateToString(b.getBmBilldt()));
				}
				b.setBmCcnOwner(new SimpleDateFormat("MMM").format(b.getBmFromdt()) + " "
						+ Utility.getYearByDate(b.getBmFromdt()) + "-"
						+ new SimpleDateFormat("MMM").format(b.getBmTodt()) + " "
						+ Utility.getYearByDate(b.getBmTodt()));
				List<TbBillDet> tbBillDet = new ArrayList<>(0);
				bill.getProvisionalBillDetEntityList().forEach(billdet -> {
					TbBillDet det = new TbBillDet();
					BeanUtils.copyProperties(billdet, det);
					det.setTaxDesc(taxMap.get(det.getTaxId()));
					b.setBmTotalAmount(b.getBmTotalAmount() + billdet.getBdCurTaxamt());
					det.setBdBalAmtToTransfer(
							billdet.getBdCurBalTaxamt() != null ? BigDecimal.valueOf(billdet.getBdCurBalTaxamt())
									: null);
					tbBillDet.add(det);
				});
				b.setTbWtBillDet(tbBillDet);
				billMas.add(b);
			});
		}
		return billMas;
	}
}
