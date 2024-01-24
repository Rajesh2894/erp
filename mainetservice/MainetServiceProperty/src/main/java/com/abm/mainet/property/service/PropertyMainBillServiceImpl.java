package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.MainBillDetEntity;
import com.abm.mainet.property.domain.MainBillDetHistEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.MainBillMasHistEntity;
import com.abm.mainet.property.domain.ProvisionalBillDetEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.repository.PropertyMainBillDetRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;

@Service
public class PropertyMainBillServiceImpl implements PropertyMainBillService {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyMainBillServiceImpl.class);

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Resource
    private AuditService auditService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;
    
    @Autowired
    private IOrganisationService orgService;
    
    @Autowired
    private TbTaxMasService tbTaxMasService;
    
	@Autowired
	private PropertyMainBillDetRepository propertyMainBillDetRepository;
	
	@Autowired
    private ReceiptRepository receiptRepository;
	
	@Autowired
    private IFinancialYearService iFinancialYearService;

    @Transactional
    @Override
    public List<MainBillMasEntity> saveAndUpdateMainBill(List<TbBillMas> billMasList, Long orgId, Long empId, String authFlag,
            String macAddress) {
        // Provisional bill mas
        List<MainBillMasEntity> mainBillEntList = new ArrayList<>();
        if (billMasList != null && !billMasList.isEmpty()) {
            billMasList.forEach(billMasDto -> {
                final MainBillMasEntity billMasEntity = new MainBillMasEntity();
                BeanUtils.copyProperties(billMasDto, billMasEntity);
                billMasEntity.setOrgid(orgId);
                if (billMasEntity.getBmIdno() == 0L) {
                	billMasEntity.setUserId(empId);
                    billMasEntity.setLgIpMac(macAddress);
                    billMasEntity.setLmoddate(new Date());
                }else {
                	billMasEntity.setUpdatedBy(empId);
                    billMasEntity.setUpdatedDate(new Date());
                    billMasEntity.setLgIpMacUpd(macAddress);
                }
                // Provisional bill detail
                final List<MainBillDetEntity> billDetEntityList = new ArrayList<>();
                billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection
                                                                                              // sequence
                billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                    MainBillDetEntity billDetEntity = new MainBillDetEntity();
                    BeanUtils.copyProperties(billDetDto, billDetEntity);
                    billDetEntity.setBmIdNo(billMasEntity);
                    billDetEntity.setOrgid(orgId);
                    if (billDetEntity.getBdBilldetid() == 0L) {
                    	billDetEntity.setUserId(empId);
                        billDetEntity.setLgIpMac(macAddress);
                        billDetEntity.setLmoddate(new Date());
                    }else {
                    	billDetEntity.setUpdatedBy(empId);
                        billDetEntity.setLgIpMacUpd(macAddress);
                        billDetEntity.setUpdatedDate(new Date());
                    }
                    billDetEntityList.add(billDetEntity);
                });
                billMasEntity.setBillDetEntityList(billDetEntityList);
                mainBillEntList.add(billMasEntity);
            });
        }
        propertyMainBillRepository.save(mainBillEntList);
        return mainBillEntList;
    }

    @Override
    @Transactional
    public List<MainBillMasEntity> saveAndUpdateMainBillWithKeyGen(List<TbBillMas> billMasList, Long orgId, Long empId,
            String propNo,
            Map<Long, Long> assIdMap, String ipAddress) {
        // Provisional bill mas
        List<MainBillMasEntity> billMasEntityList = new ArrayList<>();
        Organisation orgn = orgService.getOrganisationById(orgId);
        billMasList.forEach(billMasDto -> {
            final MainBillMasEntity billMasEntity = new MainBillMasEntity();
            BeanUtils.copyProperties(billMasDto, billMasEntity);
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
            //To update Bill generation date 
            if (Utility.isEnvPrefixAvailable(orgn, PrefixConstants.ENV.BGD)) {
				billMasEntity.setBmBilldt(new Date());
			}
            //
            
            // Provisional bill detail
            final List<MainBillDetEntity> billDetEntityList = new ArrayList<>();

            billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection sequence
            billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                MainBillDetEntity billDetEntity = new MainBillDetEntity();
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
            billMasEntity.setBillDetEntityList(billDetEntityList);
            billMasEntityList.add(billMasEntity);
        });
        propertyMainBillRepository.save(billMasEntityList);
        return billMasEntityList;
    }

    @Transactional
    @Override
    public void SaveAndUpdateMainBillOnlyForDES(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
            String macAddress) {
        // Provisional bill mas
        List<MainBillMasEntity> mainBillEntList = new ArrayList<>();

        if (billMasList != null && !billMasList.isEmpty()) {
            billMasList.forEach(billMasDto -> {
                final MainBillMasEntity billMasEntity = new MainBillMasEntity();
                BeanUtils.copyProperties(billMasDto, billMasEntity);
                if (billMasDto.getBmIdno() == 0) {
                    final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.BILL_TABLE.PropertyTable,
                            MainetConstants.BILL_TABLE.PropertyColumn, orgId,
                            MainetConstants.RECEIPT_MASTER.Reset_Type, null);

                    final long bmIdNo = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_MAS,
                            MainetConstants.Property.primColumn.PRO_BM_IDNO,
                            null,
                            null);
                    billMasEntity.setBmNo(bmNumber.toString());
                    billMasDto.setBmNo(bmNumber.toString());
                    billMasEntity.setPropNo(propNo);
                    billMasEntity.setBmIdno(bmIdNo);
                    billMasEntity.setUserId(empId);
                    billMasEntity.setOrgid(orgId);
                    billMasEntity.setLgIpMac(macAddress);
                    billMasEntity.setLmoddate(new Date());
                } else {
                    billMasEntity.setUpdatedBy(empId);
                    billMasEntity.setUpdatedDate(new Date());
                    billMasEntity.setLgIpMacUpd(macAddress);
                }

                // Provisional bill detail
                final List<MainBillDetEntity> billDetEntityList = new ArrayList<>();
                billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection
                                                                                              // sequence
                billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                    MainBillDetEntity billDetEntity = new MainBillDetEntity();
                    BeanUtils.copyProperties(billDetDto, billDetEntity);

                    if (billDetDto.getBdBilldetid() == 0) {
                        final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                                MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                                null);
                        billDetEntity.setBdBilldetid(bdBilldetid);
                        billDetEntity.setBmIdNo(billMasEntity);
                        billDetEntity.setOrgid(orgId);
                        billDetEntity.setUserId(empId);
                        billDetEntity.setLgIpMac(macAddress);
                        billDetEntity.setLmoddate(new Date());
                    } else {
                        billDetEntity.setBmIdNo(billMasEntity);
                        billDetEntity.setUpdatedBy(empId);
                        billDetEntity.setUpdatedDate(new Date());
                        billDetEntity.setLgIpMacUpd(macAddress);
                    }
                    billDetEntityList.add(billDetEntity);
                });
                billMasEntity.setBillDetEntityList(billDetEntityList);
                mainBillEntList.add(billMasEntity);
            });
        }
        propertyMainBillRepository.save(mainBillEntList);
    }

     
    @Override
    @Transactional
    public void saveAndUpdateMainBillAfterObjection(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo, String macAddress) {

    	LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " saveAndUpdateMainBillAfterObjection() method");
    	
        // Main bill mas
        List<MainBillMasEntity> mainBillEntList = new ArrayList<>();

        if (billMasList != null && !billMasList.isEmpty()) {
            billMasList.forEach(billMasDto -> {
            	
            	propertyMainBillRepository.delete(billMasDto.getBmIdno());
                final MainBillMasEntity billMasEntity = new MainBillMasEntity();
                BeanUtils.copyProperties(billMasDto, billMasEntity);
                if (billMasDto.getBmIdno() == 0) {
                    final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.BILL_TABLE.PropertyTable,
                            MainetConstants.BILL_TABLE.PropertyColumn, orgId,
                            MainetConstants.RECEIPT_MASTER.Reset_Type, null);

                    final long bmIdNo = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_MAS,
                            MainetConstants.Property.primColumn.PRO_BM_IDNO,
                            null,
                            null);
                    billMasEntity.setBmNo(bmNumber.toString());
                    billMasDto.setBmNo(bmNumber.toString());
                    billMasEntity.setPropNo(propNo);
                    billMasEntity.setBmIdno(bmIdNo);
                    billMasEntity.setUserId(empId);
                    billMasEntity.setOrgid(orgId);
                    billMasEntity.setLgIpMac(macAddress);
                    billMasEntity.setLmoddate(new Date());
                } else {
                    billMasEntity.setUpdatedBy(empId);
                    billMasEntity.setUpdatedDate(new Date());
                    billMasEntity.setLgIpMacUpd(macAddress);
                }

                // Main bill detail
                final List<MainBillDetEntity> billDetEntityList = new ArrayList<>();
                billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection sequence
                billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                    MainBillDetEntity billDetEntity = new MainBillDetEntity();
                    BeanUtils.copyProperties(billDetDto, billDetEntity);

                    if (billDetDto.getBdBilldetid() == 0) {
                        final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                                MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                                null);
                        billDetEntity.setBdBilldetid(bdBilldetid);
                        billDetEntity.setBmIdNo(billMasEntity);
                        billDetEntity.setOrgid(orgId);
                        billDetEntity.setUserId(empId);
                        billDetEntity.setLgIpMac(macAddress);
                        billDetEntity.setLmoddate(new Date());
                    } else {
                        billDetEntity.setBmIdNo(billMasEntity);
                        billDetEntity.setUpdatedBy(empId);
                        billDetEntity.setUpdatedDate(new Date());
                        billDetEntity.setLgIpMacUpd(macAddress);
                    }
                    billDetEntityList.add(billDetEntity);
                });
                billMasEntity.setBillDetEntityList(billDetEntityList);
                mainBillEntList.add(billMasEntity);
            });
        }
        propertyMainBillRepository.save(mainBillEntList);
        LOGGER.info("End -> " + this.getClass().getSimpleName() + " saveAndUpdateMainBillAfterObjection() method");
    	
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchNotPaidBillForAssessment(String assNo, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>(0);

        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchNotPaidBillForAssessment(assNo, orgId, "N");
        if (billMasListData != null && !billMasListData.isEmpty()) {
            billMasListData.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }

    private TbBillMas getBillMasDtoByBillMasEnt(MainBillMasEntity billMas) {
        TbBillMas billMasdto = new TbBillMas();
        BeanUtils.copyProperties(billMas, billMasdto);
        List<TbBillDet> billDetList = new ArrayList<>();
        billMas.getBillDetEntityList().forEach(billDetEntity -> {
            TbBillDet billDet = new TbBillDet();
            billDet.setBmIdno(billMas.getBmIdno());
            BeanUtils.copyProperties(billDetEntity, billDet);
            billDet.setBdCsmp(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()));
            billDetList.add(billDet);
        });
        billMasdto.setTbWtBillDet(billDetList);
        return billMasdto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchAllBillByPropNo(String assNo, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>(0);

        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchAllBillByPropNo(assNo, orgId);
        if (billMasListData != null && !billMasListData.isEmpty()) {
            billMasListData.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchBillByPropNo(String assNo, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>(0);

        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchBillByPropNo(assNo, orgId);
        if (billMasListData != null && !billMasListData.isEmpty()) {
            billMasListData.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }

    @Override
    @Transactional
    public List<MainBillMasEntity> saveAndUpdateMainBillFromProvisionalBill(List<ProvisionalBillMasEntity> provBillList, long orgId, Long empId,
            String auth, String ipAddress) {
    	List<MainBillMasEntity> billMasEntityList = new ArrayList<>(0);
        provBillList.forEach(billMasDto -> {
            final MainBillMasEntity billMasEntity = new MainBillMasEntity();
            BeanUtils.copyProperties(billMasDto, billMasEntity);
            billMasEntity.setOrgid(orgId);
            // Provisional bill detail
            final List<MainBillDetEntity> billDetEntityList = new ArrayList<>();
            billMasDto.getProvisionalBillDetEntityList().sort(Comparator.comparing(ProvisionalBillDetEntity::getCollSeq));
            billMasDto.getProvisionalBillDetEntityList().forEach(billDetDto -> {
                MainBillDetEntity billDetEntity = new MainBillDetEntity();
                BeanUtils.copyProperties(billDetDto, billDetEntity);
                billDetEntity.setBmIdNo(billMasEntity);
                billDetEntity.setOrgid(orgId);
                billDetEntityList.add(billDetEntity);
            });
            billMasEntity.setBillDetEntityList(billDetEntityList);
            propertyMainBillRepository.save(billMasEntity);
            billMasEntityList.add(billMasEntity);
        });

        return billMasEntityList;
    }

    /*
     * @Override public int getCountOfBillByPropNoAndCurFinId(String propNo, long orgId, Long finYearId) { return
     * propertyMainBillRepository.getCountOfBillByPropNoAndCurFinId(propNo, finYearId, orgId); }
     */

    @Override
    @Transactional
    public int getCountOfBillByOldPropNoOrPropNoAndFinId(String propNo, long orgId, Long finYearId) {
        return propertyMainBillRepository.getCountOfBillByOldPropNoOrPropNoAndFinId(propNo, finYearId, orgId);

    }

    @Override
    @Transactional
    public void copyDataFromMainBillDetailToHistory(List<TbBillMas> oldBillMasList, String hStatus,Long userId,String lgipAddress) {
        oldBillMasList.forEach(billMas -> { // Copy complete data of Bill from provisional to History
            MainBillMasHistEntity billMstHisEnt = new MainBillMasHistEntity();
            try {
            	billMas.setUpdatedBy(userId);
            	billMas.setUpdatedDate(new Date());
            	billMas.setLgIpMacUpd(lgipAddress);
                auditService.createHistory(billMas, billMstHisEnt);
                billMstHisEnt.sethStatus(hStatus);
                Thread.sleep(2000);
            } catch (Exception ex) {
                throw new FrameworkException("could ot make entry for", ex);
            }
            billMas.getTbWtBillDet().forEach(billDet -> {
                MainBillDetHistEntity billDetHisEnt = new MainBillDetHistEntity();
                try {
                	billDetHisEnt.setBmIdnoHistId(billMstHisEnt.getBmIdnoHistId());
                	billDetHisEnt.setApmApplicationId(billMas.getApmApplicationId());
                    auditService.createHistory(billDet, billDetHisEnt);
                    
                    billDetHisEnt.sethStatus(hStatus);
                } catch (Exception ex) {
                    throw new FrameworkException("could ot make entry for", ex);
                }
            });
        });
    }

    @Override
    @Transactional
    public void deleteMainBillWithEntById(List<MainBillDetEntity> mainBillDetEntity) {
        for (MainBillDetEntity mainBillEnt : mainBillDetEntity) {
            propertyMainBillRepository.delete(mainBillEnt.getBmIdNo());
        }
    }

    @Override
    @Transactional
    public void deleteMainBillWithDtoById(List<TbBillMas> tbBillMasList) {
        for (TbBillMas mainBillDto : tbBillMasList) {
            propertyMainBillRepository.delete(mainBillDto.getBmIdno());
        }

    }

    @Override
    @Transactional
    public TbBillMas fetchBillFromBmIdNo(Long bmIdno, Long orgId) {
        return getBillMasDtoByBillMasEnt(propertyMainBillRepository.fetchBillFromBmIdNo(bmIdno));

    }

    @Override
    @Transactional
    public List<TbBillMas> fetchBillFromBmIdNos(List<Long> bmIdnos) {
        List<TbBillMas> billMasList = new ArrayList<>(0);

        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchBillsFromBmIdNo(bmIdnos);
        if (billMasListData != null && !billMasListData.isEmpty()) {
            billMasListData.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }

    @Override
    @Transactional
    public int getCountOfBillWithoutDESByPropNo(String propNo, Long orgId) {
        return propertyMainBillRepository.getCountOfBillWithoutDESByPropNo(propNo, orgId);

    }

    @Override
    @Transactional
    public int getPaidBillCountByPropNoList(List<String> propNoList, Long orgId, Long bmYear) {
        return propertyMainBillRepository.getPaidBillCountByPropNoList(propNoList, orgId, bmYear);

    }

    @Override
    @Transactional
    public List<TbBillMas> fetchBillByBillNoAndPropertyNo(String propNo, String billNo, Long orgId) {
        List<TbBillMas> billMasList = null;
        List<MainBillMasEntity> billMasEntityList = propertyMainBillRepository.fetchBillByBillNoAndPropertyNo(propNo, billNo,
                orgId);
        if (billMasEntityList != null && !billMasEntityList.isEmpty()) {
            billMasList = new ArrayList<>(0);
            for (MainBillMasEntity billMas : billMasEntityList) {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            }
        }
        return billMasList;
    }

    /*
     * At time of assessment approval can edit the bill again
     */
    @Override
    @Transactional
    public List<TbBillMas> fetchAllBillByPropNoAndAssIds(String assNo, List<Long> assIds, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>(0);
        List<MainBillMasEntity> billMasEntityList = propertyMainBillRepository.fetchAllBillByPropNoAndAssIds(assNo, assIds,
                orgId);
        if (billMasEntityList != null && !billMasEntityList.isEmpty()) {
            billMasEntityList.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }

    // @Override
    // @Transactional(readOnly = true)
    // public List<TbBillMas> fetchMainBillIfItExistMaxBillByPropNo(String assNo, long orgId) {
    // List<TbBillMas> billMasList = new ArrayList<>(0);
    //
    // List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchMainBillIfItExistMaxBillByPropNo(assNo, orgId);
    // if (billMasListData != null && !billMasListData.isEmpty()) {
    // billMasListData.forEach(billMas -> {
    // TbBillMas billMasdto = new TbBillMas();
    // BeanUtils.copyProperties(billMas, billMasdto);
    // List<TbBillDet> billDetList = new ArrayList<>();
    // billMas.getBillDetEntityList().forEach(billDetEntity -> {
    // TbBillDet billDet = new TbBillDet();
    // billDet.setBmIdno(billMas.getBmIdno());
    // BeanUtils.copyProperties(billDetEntity, billDet);
    // billDetList.add(billDet);
    // });
    // billMasdto.setTbWtBillDet(billDetList);
    // billMasList.add(billMasdto);
    // });
    // }
    // return billMasList;
    // }

    @Override
    @Transactional
    public int getPaidBillCountByPropNo(String propNo, Long orgId, Long bmYear) {
        return propertyMainBillRepository.getPaidBillCountByPropNo(propNo, orgId, bmYear);

    }

	@Override
	@Transactional
	public List<TbBillMas> fetchNotPaidBillForAssessmentByFlatNo(String assNo, long orgId, String flatNo) {
		  List<TbBillMas> billMasList = new ArrayList<>(0);

	        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchNotPaidBillForAssessmentByFlatNo(assNo, orgId, "N",flatNo);
	        if (billMasListData != null && !billMasListData.isEmpty()) {
	            billMasListData.forEach(billMas -> {
	                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
	                billMasList.add(billMasdto);
	            });
	        }
	        return billMasList;
	}

	@Override
	@Transactional
	public List<TbBillMas> fetchAllBillByPropNoAndFlatNo(String assNo, String flatNo, long orgId) {
		List<TbBillMas> billMasList = new ArrayList<>(0);
		List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchAllBillByPropNoAndFlatNo(assNo,
				flatNo, orgId);
		if (billMasListData != null && !billMasListData.isEmpty()) {
			billMasListData.forEach(billMas -> {
				TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
				billMasList.add(billMasdto);
			});
		}
		return billMasList;
	}

	@Override
	@Transactional
	public void updateServeDateAndDueDate(Date serveDate, Date dueDate, Long bmIdNo) {
		propertyMainBillRepository.updateBillServeDateAndDueDate(serveDate, dueDate, bmIdNo);
		
	}
	
	 @Override
	    @Transactional(readOnly = true)
	    public List<TbBillMas> fetchAllBillByPropNoAndFinId(String assNo, long orgId, Long finId) {
	        List<TbBillMas> billMasList = new ArrayList<>(0);

	        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchAllBillByPropNoAndFinId(assNo, orgId, finId);
	        if (billMasListData != null && !billMasListData.isEmpty()) {
	            billMasListData.forEach(billMas -> {
	                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
	                billMasList.add(billMasdto);
	            });
	        }
	        return billMasList;
	    }
	 
	 
	  @Override
	    @Transactional(readOnly = true)
	    public List<TbBillMas> fetchNotPaidBillForAssessmentByParentPropNo(String parentPropNo, long orgId) {
	        List<TbBillMas> billMasList = new ArrayList<>(0);

	        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchNotPaidBillForAssessmentByParentPropNo(parentPropNo, orgId, "N");
	        if (billMasListData != null && !billMasListData.isEmpty()) {
	            billMasListData.forEach(billMas -> {
	                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
	                billMasList.add(billMasdto);
	            });
	        }
	        return billMasList;
	    }
	  
	  @Override
	    @Transactional(readOnly = true)
	    public List<TbBillMas> fetchAllBillByPropNoAndCurrentFinId(String assNo, long orgId, Long finId) {
	        List<TbBillMas> billMasList = new ArrayList<>(0);

	        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchAllBillByPropNoAndCurrentFinId(assNo, orgId, finId);
	        if (billMasListData != null && !billMasListData.isEmpty()) {
	            billMasListData.forEach(billMas -> {
	                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
	                billMasList.add(billMasdto);
	            });
	        }
	        return billMasList;
	    }
	  
	@Override
	@Transactional(readOnly = true)
	public List<TbBillMas> getBillsForBillMethodChange(ProperySearchDto properySearchDto) {
		Organisation org = new Organisation();
		org.setOrgid(properySearchDto.getOrgId());
		List<TbBillMas> billMas = new ArrayList<>(0);
		Map<Long, String> tax = new HashMap<>(0);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
		final LookUp chargeApplicableAtBillRec = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA, org);
		List<TbTaxMas> taxes = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
				properySearchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
		List<TbTaxMas> taxesBillRece = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
				properySearchDto.getDeptId(), chargeApplicableAtBillRec.getLookUpId(), null);
		List<TbTaxMas> notActiveTaxes = tbTaxMasService.findAllNotActiveTaxesForBillGeneration(
				properySearchDto.getOrgId(), properySearchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
		if (taxes != null && !taxes.isEmpty()) {
			taxes.forEach(t -> {
				tax.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		if ((notActiveTaxes != null && !notActiveTaxes.isEmpty())) {
			notActiveTaxes.forEach(t -> {
				tax.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		if (taxesBillRece != null && !taxesBillRece.isEmpty()) {
			taxesBillRece.forEach(t -> {
				tax.put(t.getTaxId(), t.getTaxDesc());
			});
		}
		List<MainBillMasEntity> bills = null;
		if (StringUtils.isNotBlank(properySearchDto.getFlatNo())) {
			bills = propertyMainBillRepository.fetchNotPaidBillForAssessmentByFlatNo(properySearchDto.getProertyNo(),
					properySearchDto.getOrgId(), "N", properySearchDto.getFlatNo());
		} else {
			bills = propertyMainBillRepository.fetchNotPaidBillForAssessment(properySearchDto.getProertyNo(),
					properySearchDto.getOrgId(), "N");
		}
		if (bills != null && !bills.isEmpty()) {
			bills.forEach(bill -> {
				TbBillMas b = new TbBillMas();
				List<TbBillDet> tbBillDet = new ArrayList<>(0);
				BeanUtils.copyProperties(bill, b);
				b.setBmTotalAmount(0);
				b.setBmRemarks(Utility.dateToString(b.getBmDuedate()));
				if (b.getBmBilldt() != null) {
					b.setBmBilldtString(Utility.dateToString(b.getBmBilldt()));
				}
				if (b.getBillDistrDate() != null) {
					b.setBillDistrDateString(Utility.dateToString(b.getBillDistrDate()));
				}
				b.setBmCcnOwner(new SimpleDateFormat("MMM").format(b.getBmFromdt()) + " "
						+ Utility.getYearByDate(b.getBmFromdt()) + "-"
						+ new SimpleDateFormat("MMM").format(b.getBmTodt()) + " "
						+ Utility.getYearByDate(b.getBmTodt()));
				double totalBalanceAmount = 0;
				double totalAmtToTransfer = 0;
				for (MainBillDetEntity billdet : bill.getBillDetEntityList()) {
					TbBillDet det = new TbBillDet();
					BeanUtils.copyProperties(billdet, det);
					det.setTaxDesc(tax.get(det.getTaxId()));
					b.setBmTotalAmount(b.getBmTotalAmount() + billdet.getBdCurTaxamt());
					det.setBdBalAmtToTransfer(
							billdet.getBdCurBalTaxamt() != null ? BigDecimal.valueOf(billdet.getBdCurBalTaxamt())
									: null);
					tbBillDet.add(det);
					totalBalanceAmount += billdet.getBdCurBalTaxamt();
					totalAmtToTransfer += billdet.getBdCurBalTaxamt();
				}
				b.setTotalBalanceAmount(totalBalanceAmount);
				b.setTotalAmtToTransfer(totalAmtToTransfer);
				b.setTbWtBillDet(tbBillDet);
				billMas.add(b);
			});
		}
		return billMas;
	}
	
	@Override
	@Transactional
	public void deleteMainBillDetById(List<Long> bmIdNos) {
		for (Long bmIdNo : bmIdNos) {
			propertyMainBillDetRepository.delete(bmIdNo);
		}
	}
	
	@Override
	@Transactional
	public int getPaidBillCountByPropNoAndFlatNo(String propNo, Long orgId, Long bmYear, String flatNo) {
		return propertyMainBillRepository.getPaidBillCountByPropNoAndFlatNo(propNo, orgId, bmYear, flatNo);
	}
	
	@Override
	@Transactional
	public int getBillExistByPropNoAndYearId(String propNo, Long orgId,	Long bmYear) {
		return propertyMainBillRepository.getBillExistByPropNoAndYearId(propNo, orgId, bmYear);
	}
	
	@Override
	@Transactional
	public int getBillExistByPropNoFlatNoAndYearId(String propNo, Long orgId, Long bmYear, String flatNo) {
		return propertyMainBillRepository.getBillExistByPropNoFlatNoAndYearId(propNo, orgId, bmYear, flatNo);
	}

	@Override
	public Long getMaxFinYearIdByPropNo(String propNo, Long orgId) {
        return propertyMainBillRepository.getMaxFinYearIdByPropNo(propNo, orgId);
    }
	
	   @Override
	    @Transactional(readOnly = true)
	    public List<TbBillMas> fetchBillSForViewProperty(String assNo) {
	        List<TbBillMas> billMasList = new ArrayList<>(0);

	        List<MainBillMasEntity> billMasListData = propertyMainBillRepository.fetchBillSForViewProperty(assNo);
	        if (billMasListData != null && !billMasListData.isEmpty()) {
	            billMasListData.forEach(billMas -> {
	                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
	                billMasList.add(billMasdto);
	            });
	        }
	        return billMasList;
	    }
	   

	@Override
	@Transactional
	public void deleteBillDetList(List<Long> billDetList) {

		propertyMainBillRepository.deleteBillDet(billDetList);
	}
	
	@Override
    public String ValidateBillData(List<TbBillMas> billMasList) {
		TbBillMas firstBill = billMasList.get(0);
    	TbBillMas lastBill = billMasList.get(billMasList.size() -1);
    	StringBuilder builder = new StringBuilder();
    	Organisation org = new Organisation();
    	org.setOrgid(firstBill.getOrgid());
    	Long currYear = iFinancialYearService.getFinanceYearId(new Date());
    	
    	billMasList.forEach(billMas ->{
    		if(billMas.getBmIdno() == firstBill.getBmIdno()) {
    			billMas.getTbWtBillDet().forEach(billDet ->{
    				if(!Utility.isEnvPrefixAvailable(org, "PSCL")) {
    					if(billDet.getBdPrvBalArramt() > 0) {
            				builder.append("First bill has arrear amount for Bill det -> "+ billDet.getBdBilldetid());
            			}
    				}
        		});
    		}
    		billMas.getTbWtBillDet().forEach(billDet ->{
    			if(billDet.getBdCurBalTaxamt() > billDet.getBdCurTaxamt() || billDet.getBdPrvBalArramt() > billDet.getBdPrvArramt()){
    				builder.append("Balance is greater than demand for Bill det -> "+ billDet.getBdBilldetid());
    			}
    		});
    	});
    	
    	if(!(lastBill.getBmBilldt().compareTo(lastBill.getBmFromdt()) >= 0
                && lastBill.getBmBilldt().compareTo(lastBill.getBmTodt()) <= 0) && currYear.equals(lastBill.getBmYear())) {
    		builder.append("Bill date does not exist in financial year"+ lastBill.getBmIdno());
    	}
                
    	for (int i = 1; i < billMasList.size(); i++) {
					TbBillMas currBillMas = billMasList.get(i);
					TbBillMas immedPrvBill = billMasList.get(i-1);
					currBillMas.getTbWtBillDet().forEach(currentDet ->{
						List<TbBillDet> lastDet = immedPrvBill.getTbWtBillDet().stream().filter(det -> det.getTaxId().equals(currentDet.getTaxId())).collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(lastDet)) {
							double prvArrearAmnt = lastDet.get(0).getBdCurBalTaxamt() + lastDet.get(0).getBdPrvBalArramt();
							if(Math.round(prvArrearAmnt) != Math.round(currentDet.getBdPrvBalArramt())) {
								builder.append(" Arrear Balance not carry forwarded properly for Bill det -> "+ currentDet.getBdBilldetid());
							}
						}
					});
		}
    	return builder.toString();
    }
}
