/**
 * 
 */
package com.abm.mainet.property.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.objection.domain.HearingMasterEntity;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.repository.HearingMasRepository;
import com.abm.mainet.cfc.objection.repository.ObjectionMastRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.DuplicateBillEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.DuplicateBillDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.repository.DuplicateBillRepository;
import com.abm.mainet.common.service.IDuplicateBillService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.BarcodeGenerator;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailHistEntity;
import com.abm.mainet.property.domain.ProAssDetailHisEntity;
import com.abm.mainet.property.domain.ProAssFactlHisEntity;
import com.abm.mainet.property.domain.ProAssMstHisEntity;
import com.abm.mainet.property.domain.ProAssOwnerHisEntity;
import com.abm.mainet.property.domain.ProAssRoomHisEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.MainAssessmentDetailRepository;
import com.abm.mainet.property.repository.MainAssessmentFactorRepository;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;
import com.abm.mainet.property.repository.MainAssessmentRoomRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentDetRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentOwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AssesmentMastServiceImpl implements AssesmentMastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssesmentMastServiceImpl.class);

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private PropertyService propertyService;

    @Resource
    private AuditService auditService;

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private MainAssessmentDetailRepository mainAssessmentDetailRepository;

    @Autowired
    private MainAssessmentOwnerRepository mainAssessmentOwnerRepository;

    @Autowired
    private MainAssessmentFactorRepository mainAssessmentFactorRepository;

    @Autowired
    private ObjectionMastRepository objectionMastRepository;

    @Autowired
    private HearingMasRepository hearingMasRepository;

    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;

    @Autowired
    private ProvisionalAssesmentDetRepository provisionalAssesmentDetRepository;

    @Autowired
    private ProvisionalAssesmentOwnerRepository provisionalAssesmentOwnerRepository;

    @Autowired
    private MainAssessmentRoomRepository mnAssRoomRepository;

    @Autowired
    private IOrganisationService orgService;
    
    @Autowired
    IDuplicateBillService duplicateBillService;

    @Transactional
    @Override
    public void saveAssesmentMastByEntity(List<ProvisionalAssesmentMstEntity> provAssDtoList, Long orgId,
            Long empId, String authFlag, String ipAddress) {
        List<AssesmentMastEntity> assEntList = new ArrayList<>();
        provAssDtoList.forEach(assMastDto -> {
            final AssesmentMastEntity assetMst = new AssesmentMastEntity();
            BeanUtils.copyProperties(assMastDto, assetMst);
            assetMst.setCreatedBy(empId);
            assetMst.setLgIpMac(ipAddress);
            assetMst.setAssAutBy(empId);
            assetMst.setAssAutStatus(authFlag);
            if (authFlag.equals(MainetConstants.Property.AuthStatus.AUTH)) {
                assetMst.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
            }
            assetMst.setAssAutDate(new Date());
            assetMst.setCreatedDate(new Date());
            final List<AssesmentDetailEntity> asseDetList = new ArrayList<>();
            assMastDto.getProvisionalAssesmentDetailList().forEach(assDetDto -> {
                AssesmentDetailEntity asseDetEnt = new AssesmentDetailEntity();
                BeanUtils.copyProperties(assDetDto, asseDetEnt);
                asseDetEnt.setMnAssId(assetMst);
                asseDetEnt.setCreatedBy(empId);
                asseDetEnt.setLgIpMac(ipAddress);
                asseDetEnt.setCreatedDate(new Date());

                final List<AssesmentFactorDetailEntity> asseFactList = new ArrayList<>();
                assDetDto.getProvisionalAssesmentFactorDtlList().forEach(assFctDto -> {
                    AssesmentFactorDetailEntity assFactEnt = new AssesmentFactorDetailEntity();
                    BeanUtils.copyProperties(assFctDto, assFactEnt);
                    assFactEnt.setMnAssdId(asseDetEnt);
                    assFactEnt.setMnAssId(assetMst);
                    assFactEnt.setLgIpMac(ipAddress);
                    assFactEnt.setOrgId(orgId);
                    asseFactList.add(assFactEnt);
                });
                asseDetEnt.setAssesmentFactorDetailEntityList(asseFactList);
                asseDetList.add(asseDetEnt);
            });
            assetMst.setAssesmentDetailEntityList(asseDetList);
            if (assMastDto.getProvisionalAssesmentOwnerDtlList() != null
                    && !assMastDto.getProvisionalAssesmentOwnerDtlList().isEmpty()) {
                final List<AssesmentOwnerDtlEntity> asseOwnEntList = new ArrayList<>();
                assMastDto.getProvisionalAssesmentOwnerDtlList().forEach(provOwndto -> {
                    AssesmentOwnerDtlEntity assOwner = new AssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, assOwner);
                    assOwner.setCreatedBy(empId);
                    assOwner.setLgIpMac(ipAddress);
                    assOwner.setCreatedDate(new Date());
                    assOwner.setMnAssId(assetMst);
                    asseOwnEntList.add(assOwner);
                });
                assetMst.setAssesmentOwnerDetailEntityList(asseOwnEntList);
            }
            assEntList.add(assetMst);
        });
        List<AssesmentMastEntity> assMstHistEntList = assesmentMstRepository.save(assEntList);
        assMstHistEntList.forEach(mastEntity -> {
        	maintainAstMstHistory(mastEntity);
        });
    }

    @Transactional
    @Override
    public void saveAssesmentMastByDto(List<ProvisionalAssesmentMstDto> provAssDtoList, Long orgId,
            Long empId, String authFlag, String ipAddress) {
        List<AssesmentMastEntity> assEntList = new ArrayList<>();
        provAssDtoList.forEach(assMastDto -> {
            final AssesmentMastEntity assetMst = new AssesmentMastEntity();
            BeanUtils.copyProperties(assMastDto, assetMst);
            assetMst.setCreatedBy(empId);
            assetMst.setLgIpMac(ipAddress);
            assetMst.setAssAutBy(empId);
            assetMst.setAssAutStatus(authFlag);
            if (authFlag.equals(MainetConstants.Property.AuthStatus.AUTH)) {
                assetMst.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
            }
            assetMst.setAssAutDate(new Date());
            assetMst.setCreatedDate(new Date());
            final List<AssesmentDetailEntity> asseDetList = new ArrayList<>();
            assMastDto.getProvisionalAssesmentDetailDtoList().forEach(assDetDto -> {
                AssesmentDetailEntity asseDetEnt = new AssesmentDetailEntity();
                BeanUtils.copyProperties(assDetDto, asseDetEnt);
                asseDetEnt.setMnAssId(assetMst);
                asseDetEnt.setCreatedBy(empId);
                asseDetEnt.setLgIpMac(ipAddress);
                asseDetEnt.setCreatedDate(new Date());

                final List<AssesmentFactorDetailEntity> asseFactList = new ArrayList<>();
                assDetDto.getProvisionalAssesmentFactorDtlDtoList().forEach(assFctDto -> {
                    AssesmentFactorDetailEntity assFactEnt = new AssesmentFactorDetailEntity();
                    BeanUtils.copyProperties(assFctDto, assFactEnt);
                    assFactEnt.setMnAssdId(asseDetEnt);
                    assFactEnt.setMnAssId(assetMst);
                    assFactEnt.setLgIpMac(ipAddress);
                    assFactEnt.setOrgId(orgId);
                    asseFactList.add(assFactEnt);
                });
                asseDetEnt.setAssesmentFactorDetailEntityList(asseFactList);

                /// #97207 by Arun
                final List<AssessmentRoomDetailEntity> assessmentRoomDetailEntityList = new ArrayList<>();
                AssessmentRoomDetailEntity assessmentRoomDetailEntity;
                for (PropertyRoomDetailsDto roomDetail : assDetDto.getRoomDetailsDtoList()) {
                    assessmentRoomDetailEntity = new AssessmentRoomDetailEntity();
                    BeanUtils.copyProperties(roomDetail, assessmentRoomDetailEntity);
                    if (roomDetail.getOrgId() == null) {
                        assessmentRoomDetailEntity.setOrgId(orgId);
                        assessmentRoomDetailEntity.setCreatedBy(empId);
                        assessmentRoomDetailEntity.setCreatedDate(new Date());
                    }
                    assessmentRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                    assessmentRoomDetailEntity.setAssPropRoomDet(asseDetEnt);
                    assessmentRoomDetailEntityList.add(assessmentRoomDetailEntity);
                }
                asseDetEnt.setRoomDetailEntityList(assessmentRoomDetailEntityList);
                /// end

                asseDetList.add(asseDetEnt);
            });
            assetMst.setAssesmentDetailEntityList(asseDetList);
            if (!assMastDto.getProvisionalAssesmentOwnerDtlDtoList().isEmpty()) {
                final List<AssesmentOwnerDtlEntity> asseOwnEntList = new ArrayList<>();
                assMastDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
                    AssesmentOwnerDtlEntity assOwner = new AssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, assOwner);
                    assOwner.setCreatedBy(empId);
                    assOwner.setLgIpMac(ipAddress);
                    assOwner.setCreatedDate(new Date());
                    assOwner.setMnAssId(assetMst);
                    asseOwnEntList.add(assOwner);
                });
                assetMst.setAssesmentOwnerDetailEntityList(asseOwnEntList);
            }
            assEntList.add(assetMst);
        });
        List<AssesmentMastEntity> assMstHistEntList = assesmentMstRepository.save(assEntList);
        assMstHistEntList.forEach(mastEntity -> {
        	maintainAstMstHistory(mastEntity);
        });
        
        
    }
    
    @Transactional
	private void maintainAstMstHistory(AssesmentMastEntity mastEntity) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " maintainAstMstHistory() method");
		AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
		mastHistEntity.setMnAssId(mastEntity.getProAssId());
		if(MainetConstants.Property.AssStatus.SPEC_NOTICE.equalsIgnoreCase(mastEntity.getAssStatus())) {
			mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
		}else {
			mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);
		}

		try {
			auditService.createHistory(mastEntity, mastHistEntity);
		} catch (Exception ex) {
			throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + mastEntity, ex);
		}
		mastEntity.getAssesmentDetailEntityList().forEach(detailEntity -> {
			AssesmentDetailHistEntity assMstDetHisEntity = new AssesmentDetailHistEntity();
			assMstDetHisEntity.setMnAssId(mastEntity.getProAssId());
			assMstDetHisEntity.setProAssMastHisId(mastHistEntity.getMnAssHisId());
			assMstDetHisEntity.setProAssdId(detailEntity.getProAssdId());
			if(MainetConstants.Property.AssStatus.SPEC_NOTICE.equalsIgnoreCase(mastEntity.getAssStatus())) {
				mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
			}else {
				mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);
			}
			try {
				auditService.createHistory(detailEntity, assMstDetHisEntity);
			} catch (Exception ex) {
				throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + detailEntity, ex);
			}
			detailEntity.getAssesmentFactorDetailEntityList().forEach(factEntity -> {
				AssesmentFactorDetailHistEntity assMstFactlHisEntity = new AssesmentFactorDetailHistEntity();
				assMstFactlHisEntity.setMnAssdId(detailEntity.getProAssdId());
				if(MainetConstants.Property.AssStatus.SPEC_NOTICE.equalsIgnoreCase(mastEntity.getAssStatus())) {
					mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
				}else {
					mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);
				}
				try {
					auditService.createHistory(factEntity, assMstFactlHisEntity);
				} catch (Exception ex) {
					throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + factEntity, ex);
				}
			});
			if (null != detailEntity.getRoomDetailEntityList()) {
				detailEntity.getRoomDetailEntityList().forEach(roomEntity -> {
					AssessmentRoomDetailHistEntity assMstRoomHisEntity = new AssessmentRoomDetailHistEntity();
					assMstRoomHisEntity.setMnAssdId(detailEntity.getProAssdId());
					if(MainetConstants.Property.AssStatus.SPEC_NOTICE.equalsIgnoreCase(mastEntity.getAssStatus())) {
						mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
					}else {
						mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);
					}

					try {
						auditService.createHistory(roomEntity, assMstRoomHisEntity);
					} catch (Exception ex) {
						throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + roomEntity, ex);
					}
				});
			}
		});
		if (null != mastEntity.getAssesmentOwnerDetailEntityList()) {
			mastEntity.getAssesmentOwnerDetailEntityList().forEach(ownerEntity -> {
				AssesmentOwnerDtlHistEntity assMstOwnHisEntity = new AssesmentOwnerDtlHistEntity();
				assMstOwnHisEntity.setMnAssId(mastEntity.getProAssId());
				assMstOwnHisEntity.setProAssoId(ownerEntity.getProAssoId());
				assMstOwnHisEntity.setProAssMastHisId(mastHistEntity.getMnAssHisId());
				if(MainetConstants.Property.AssStatus.SPEC_NOTICE.equalsIgnoreCase(mastEntity.getAssStatus())) {
					mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
				}else {
					mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);
				}
				try {
					auditService.createHistory(ownerEntity, assMstOwnHisEntity);
				} catch (Exception ex) {
					LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + ownerEntity, ex);
				}
			});
		}

		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " maintainAstMstHistory() method");
	}

    @Transactional
    @Override
    public Map<Long, Long> saveAndUpdateAssessmentMast(ProvisionalAssesmentMstDto provAssMstDto,
            List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId,
            Long empId, String authFlag) {
        final String ipAddress = provAssMstDto.getLgIpMac();
        Map<Long, Long> assIdMap = new HashMap<>();
        for (ProvisionalAssesmentMstDto provAssDto : provAssMstDtoList) {
            AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
            BeanUtils.copyProperties(provAssMstDto, provAssetMst);
            provAssetMst.setProAssId(provAssDto.getProAssId());
            provAssetMst.setFaYearId(provAssDto.getFaYearId());
            provAssetMst.setUpdatedBy(empId);
            provAssetMst.setLgIpMacUpd(ipAddress);
            provAssetMst.setUpdatedDate(new Date());
            provAssetMst.setAssAutStatus(authFlag);
            final List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            for (ProvisionalAssesmentDetailDto provDet : provAssMstDto.getProvisionalAssesmentDetailDtoList()) {
                if (provDet.getFaYearId().toString().equals(provAssDto.getFaYearId().toString())) {
                    AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
                    BeanUtils.copyProperties(provDet, provAsseDet);
                    if (provDet.getProAssdId() == 0) {
                        final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
                                MainetConstants.Property.primColumn.PRO_ASSD_ID,
                                null,
                                null);
                        provAsseDet.setProAssdId(proAssdId);
                        provAsseDet.setCreatedBy(empId);
                        provAsseDet.setLgIpMac(ipAddress);
                        provAsseDet.setCreatedDate(new Date());
                        provAsseDet.setOrgId(orgId);
                        provAsseDet.setAssdAssesmentDate(new Date());
                        provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                    } else {
                        provAsseDet.setUpdatedBy(empId);
                        provAsseDet.setLgIpMacUpd(ipAddress);
                        provAsseDet.setUpdatedDate(new Date());
                    }
                    provAsseDet.setMnAssId(provAssetMst);
                    final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
                    for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                        AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
                        BeanUtils.copyProperties(provfact, provAssFact);
                        if (provfact.getProAssfId() == 0) {
                            final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
                                    MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                    MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
                                    MainetConstants.Property.primColumn.PRO_ASSF_ID,
                                    null,
                                    null);
                            provAssFact.setProAssfId(proAssfId);
                            provAssFact.setCreatedBy(empId);
                            provAssFact.setCreatedDate(new Date());
                            provAssFact.setLgIpMac(ipAddress);
                            provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                            // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                            provAssFact.setOrgId(orgId);
                        } else {
                            provAssFact.setUpdatedBy(empId);
                            provAssFact.setUpdatedDate(new Date());
                            provAssFact.setLgIpMacUpd(ipAddress);
                        }
                        provAssFact.setMnAssdId(provAsseDet);
                        provAssFact.setMnAssId(provAssetMst);
                        provAsseFactList.add(provAssFact);
                    }
                    provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

                    /// #97207 by Arun
                    final List<AssessmentRoomDetailEntity> assessmentRoomDetailEntityList = new ArrayList<>();
                    AssessmentRoomDetailEntity assessmentRoomDetailEntity;
                    for (PropertyRoomDetailsDto roomDetail : provDet.getRoomDetailsDtoList()) {
                        assessmentRoomDetailEntity = new AssessmentRoomDetailEntity();
                        BeanUtils.copyProperties(roomDetail, assessmentRoomDetailEntity);
                        if (roomDetail.getOrgId() == null) {
                            assessmentRoomDetailEntity.setOrgId(orgId);
                            assessmentRoomDetailEntity.setCreatedBy(empId);
                            assessmentRoomDetailEntity.setCreatedDate(new Date());
                        }
                        assessmentRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                        assessmentRoomDetailEntity.setAssPropRoomDet(provAsseDet);
                        assessmentRoomDetailEntityList.add(assessmentRoomDetailEntity);
                    }
                    provAsseDet.setRoomDetailEntityList(assessmentRoomDetailEntityList);
                    /// end

                    provAsseDetList.add(provAsseDet);
                }
            }
            provAssetMst.setAssesmentDetailEntityList(provAsseDetList);
            if (provAssMstDto.getProAssId() == provAssDto.getProAssId()) {  // to update or add owner into latest assment only
                final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
                for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                    AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                    provAsseOwnerEnt.setMnAssId(provAssetMst);
                    if (provOwndto.getProAssoId() == 0) {
                        final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                                MainetConstants.Property.primColumn.PRO_ASSO_ID,
                                null,
                                null);
                        provAsseOwnerEnt.setProAssoId(proAssoId);
                        provAsseOwnerEnt.setCreatedBy(empId);
                        provAsseOwnerEnt.setLgIpMac(ipAddress);
                        provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                        provAsseOwnerEnt.setCreatedDate(new Date());
                        provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                        provAsseOwnerEnt.setAssoStartDate(provAssetMst.getAssAcqDate());
                        provAsseOwnerEnt.setOrgId(orgId);
                        provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                        provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                        provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                        provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                    }
                    provAsseOwnerEnt.setUpdatedBy(empId);
                    provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                    provAsseOwnerEnt.setUpdatedDate(new Date());
                    provAsseOwnEntList.add(provAsseOwnerEnt);
                }
                provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
            }
            AssesmentMastEntity assMstEntity = assesmentMstRepository.save(provAssetMst);
            maintainAstMstHistory(assMstEntity);
            assIdMap.put(provAssetMst.getFaYearId(), provAssetMst.getProAssId());
        }
        return assIdMap;
    }

   
    @Override
    @Transactional
    public void saveAndUpdateAssessmentMastAfterObjection(ProvisionalAssesmentMstDto provAssMstDto,
    		List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId,	Long empId, String ipAddress, String authFlag) {
    	
    	LOGGER.info("Begin -> " +this.getClass().getSimpleName() + " saveAndUpdateAssessmentMastAfterObjection() method");
    	
        AssesmentMastEntity assesmentMastEntity = assesmentMstRepository.fetchAssessmentMasterByPropNo(orgId, provAssMstDto.getAssNo());
        assesmentMstRepository.delete(assesmentMastEntity);
        //List<AssesmentDetailEntity> detailEntityList = mainAssessmentDetailRepository.fetchAssdIdByAssId(assesmentMastEntity);
        for (ProvisionalAssesmentMstDto provAssDto : provAssMstDtoList) {
            AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
            BeanUtils.copyProperties(provAssMstDto, provAssetMst);
            provAssetMst.setProAssId(provAssDto.getProAssId());
            provAssetMst.setFaYearId(provAssDto.getFaYearId());
            provAssetMst.setUpdatedBy(empId);
            provAssetMst.setLgIpMacUpd(ipAddress);
            provAssetMst.setUpdatedDate(new Date());
            provAssetMst.setAssAutStatus(authFlag);
            final List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            for (ProvisionalAssesmentDetailDto provDet : provAssMstDto.getProvisionalAssesmentDetailDtoList()) {
                if (provDet.getFaYearId().toString().equals(provAssDto.getFaYearId().toString())) {
                    AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
                    BeanUtils.copyProperties(provDet, provAsseDet);
                    if (provDet.getProAssdId() == 0) {
                        final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
                                MainetConstants.Property.primColumn.PRO_ASSD_ID,
                                null,
                                null);
                        provAsseDet.setProAssdId(proAssdId);
                        provAsseDet.setCreatedBy(empId);
                        provAsseDet.setLgIpMac(ipAddress);
                        provAsseDet.setCreatedDate(new Date());
                        provAsseDet.setOrgId(orgId);
                        provAsseDet.setAssdAssesmentDate(new Date());
                        provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                    } else {
                        provAsseDet.setUpdatedBy(empId);
                        provAsseDet.setLgIpMacUpd(ipAddress);
                        provAsseDet.setUpdatedDate(new Date());
                    }
                    provAsseDet.setMnAssId(provAssetMst);
                    final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
                    for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                        AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
                        BeanUtils.copyProperties(provfact, provAssFact);
                        if (provfact.getProAssfId() == 0) {
                            final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
                                    MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                    MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
                                    MainetConstants.Property.primColumn.PRO_ASSF_ID,
                                    null,
                                    null);
                            provAssFact.setProAssfId(proAssfId);
                            provAssFact.setCreatedBy(empId);
                            provAssFact.setCreatedDate(new Date());
                            provAssFact.setLgIpMac(ipAddress);
                            provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                            provAssFact.setOrgId(orgId);
                        } else {
                            provAssFact.setUpdatedBy(empId);
                            provAssFact.setUpdatedDate(new Date());
                            provAssFact.setLgIpMacUpd(ipAddress);
                        }
                        provAssFact.setMnAssdId(provAsseDet);
                        provAssFact.setMnAssId(provAssetMst);
                        provAsseFactList.add(provAssFact);
                    }
                    provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

                    final List<AssessmentRoomDetailEntity> assessmentRoomDetailEntityList = new ArrayList<>();
                    AssessmentRoomDetailEntity assessmentRoomDetailEntity;
                    for (PropertyRoomDetailsDto roomDetail : provDet.getRoomDetailsDtoList()) {
                        assessmentRoomDetailEntity = new AssessmentRoomDetailEntity();
                        BeanUtils.copyProperties(roomDetail, assessmentRoomDetailEntity);
                        if (roomDetail.getOrgId() == null) {
                            assessmentRoomDetailEntity.setOrgId(orgId);
                            assessmentRoomDetailEntity.setCreatedBy(empId);
                            assessmentRoomDetailEntity.setCreatedDate(new Date());
                        }
                        assessmentRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                        assessmentRoomDetailEntity.setAssPropRoomDet(provAsseDet);
                        assessmentRoomDetailEntityList.add(assessmentRoomDetailEntity);
                    }
                    provAsseDet.setRoomDetailEntityList(assessmentRoomDetailEntityList);

                    provAsseDetList.add(provAsseDet);
                }
            }
            provAssetMst.setAssesmentDetailEntityList(provAsseDetList);
            if (provAssMstDto.getProAssId() == provAssDto.getProAssId()) {  // to update or add owner into latest assment only
                final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
                for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                    AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                    provAsseOwnerEnt.setMnAssId(provAssetMst);
                    if (provOwndto.getProAssoId() == 0) {
                        final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
                                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                                MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                                MainetConstants.Property.primColumn.PRO_ASSO_ID,
                                null,
                                null);
                        provAsseOwnerEnt.setProAssoId(proAssoId);
                        provAsseOwnerEnt.setCreatedBy(empId);
                        provAsseOwnerEnt.setLgIpMac(ipAddress);
                        provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                        provAsseOwnerEnt.setCreatedDate(new Date());
                        provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                        provAsseOwnerEnt.setAssoStartDate(provAssetMst.getAssAcqDate());
                        provAsseOwnerEnt.setOrgId(orgId);
                        provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                        provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                        provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                        provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                    }
                    provAsseOwnerEnt.setUpdatedBy(empId);
                    provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                    provAsseOwnerEnt.setUpdatedDate(new Date());
                    provAsseOwnEntList.add(provAsseOwnerEnt);
                }
                provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
            }
            AssesmentMastEntity assMstEntity = assesmentMstRepository.save(provAssetMst);
            maintainAstMstHistory(assMstEntity);
            LOGGER.info("End -> " +this.getClass().getSimpleName() + " saveAndUpdateAssessmentMastAfterObjection() method");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto getPropDetailByAssNoOrOldPropNo(long orgId, String assNo, String assOldpropno,
            String status, String logicalPropNo) {
        AssesmentMastEntity assessmentEntity = iAssessmentMastDao.fetchAssDetailAssNoOrOldpropno(orgId, assNo,
                assOldpropno, status, logicalPropNo);
        if (assessmentEntity != null) {
            // assessmentEntity.setFlag(null);
            return mapAssessmentMasterToProvisionalDto(assessmentEntity);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.property.service.AssesmentMastService#getAssessmentDetailsAssNoOrOldpropno(long, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto getAssessmentDetailsAssNoOrOldpropno(long orgId, String assNo, String assOldpropno,
            String status) {
        AssesmentMastEntity assessmentEntity = iAssessmentMastDao.fetchAssessmentDetailsAssNoOrOldpropno(orgId, assNo,
                assOldpropno, status);
        if (assessmentEntity != null) {
            assessmentEntity.setFlag(null);
            return mapAssessmentMasterToProvisionalDto(assessmentEntity);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto getDataEntryByPropNoOrOldPropNo(long orgId, String assNo, String assOldpropno) {
        AssesmentMastEntity assessmentEntity = iAssessmentMastDao.fetchDataEntryByAssNoOrOldPropNo(orgId, assNo,
                assOldpropno);
        if (assessmentEntity != null) {
            assessmentEntity.setFlag(null);
            return mapAssessmentMasterToProvisionalDto(assessmentEntity);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDto(AssesmentMastEntity assessmentMaster) {
        ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
        BeanUtils.copyProperties(assessmentMaster, assMstDto, "proAssId");
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        assessmentMaster.getAssesmentDetailEntityList().forEach(detEnt -> {
            ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
            final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
            BeanUtils.copyProperties(detEnt, provDetDto, "proAssdId");
            provDetDto.setAssdUnitNo(detEnt.getAssdUnitNo());
            provDetDto.setPropNo(assessmentMaster.getAssNo());
            detEnt.getAssesmentFactorDetailEntityList().forEach(assFactEnt -> {
                ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(assFactEnt, proAssFactDto, "proAssfId");
                provAssFactDtoList.add(proAssFactDto);
            });

            List<PropertyRoomDetailsDto> roomDetailsDtoList = new ArrayList<>();
            detEnt.getRoomDetailEntityList().forEach(room -> {
                PropertyRoomDetailsDto propertyRoomDetailsDto = new PropertyRoomDetailsDto();
                BeanUtils.copyProperties(room, propertyRoomDetailsDto, "proRoomId");
                roomDetailsDtoList.add(propertyRoomDetailsDto);
            });
            provDetDto.setRoomDetailsDtoList(roomDetailsDtoList);
            provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
            provAssDetDtoList.add(provDetDto);
        });
        assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        if (!assessmentMaster.getAssesmentOwnerDetailEntityList().isEmpty()
                && assessmentMaster.getAssesmentOwnerDetailEntityList() != null) {
            final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
            assessmentMaster.getAssesmentOwnerDetailEntityList().forEach(ownEnt -> {
                ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                BeanUtils.copyProperties(ownEnt, provAssOwnDto, "proAssoId");
                provAssOwnDtoList.add(provAssOwnDto);
            });
            assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        }
        return assMstDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDtoWithPriKey(AssesmentMastEntity assessmentMaster) {
        ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
        BeanUtils.copyProperties(assessmentMaster, assMstDto);
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        assessmentMaster.getAssesmentDetailEntityList().forEach(detEnt -> {
            ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
            final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
            BeanUtils.copyProperties(detEnt, provDetDto);
            provDetDto.setAssdUnitNo(detEnt.getAssdUnitNo());
            provDetDto.setPropNo(assessmentMaster.getAssNo());
            detEnt.getAssesmentFactorDetailEntityList().forEach(assFactEnt -> {
                ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(assFactEnt, proAssFactDto);
                provAssFactDtoList.add(proAssFactDto);
            });
            provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);

            // 97207 By arun
            final List<PropertyRoomDetailsDto> roomDetailList = new ArrayList<>();
            detEnt.getRoomDetailEntityList().forEach(roomDetail -> {
                PropertyRoomDetailsDto roomDetailDto = new PropertyRoomDetailsDto();
                BeanUtils.copyProperties(roomDetail, roomDetailDto);
                roomDetailList.add(roomDetailDto);
            });
            provDetDto.setRoomDetailsDtoList(roomDetailList);
            //

            provAssDetDtoList.add(provDetDto);
        });
        assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        if (!assessmentMaster.getAssesmentOwnerDetailEntityList().isEmpty()
                && assessmentMaster.getAssesmentOwnerDetailEntityList() != null) {
            final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
            assessmentMaster.getAssesmentOwnerDetailEntityList().forEach(ownEnt -> {
                ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                BeanUtils.copyProperties(ownEnt, provAssOwnDto);
                provAssOwnDtoList.add(provAssOwnDto);
            });
            assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        }
        return assMstDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto fetchAssessmentMasterByPropNo(long orgId, String propNo) {
        AssesmentMastEntity assessmentMaster = assesmentMstRepository.fetchAssessmentMasterByPropNo(orgId, propNo);
        if (assessmentMaster != null) {
            return mapAssessmentMasterToProvisionalDto(assessmentMaster);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto fetchAssessmentMasterByPropNoWithKey(long orgId, String propNo) {
        AssesmentMastEntity assessmentMaster = assesmentMstRepository.fetchAssessmentMasterByPropNo(orgId, propNo);
        if (assessmentMaster != null) {
            return mapAssessmentMasterToProvisionalDtoWithPriKey(assessmentMaster);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto fetchAssessmentMasterByOldPropNo(Long orgId, String OldPropNo) {
        AssesmentMastEntity assessmentMaster = assesmentMstRepository.fetchAssessmentMasterByOldPropNo(orgId, OldPropNo);
        if (assessmentMaster != null) {
            return mapAssessmentMasterToProvisionalDto(assessmentMaster);
        }
        return null;

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByPropNo(long orgId, List<String> propNoList) {
        List<ProvisionalAssesmentMstDto> assList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.fetchAssessmentMstByPropNo(orgId, propNoList);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                assList.add(mapAssessmentMasterToProvisionalDto(mst));
            });
        }
        return assList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailFromAssByPropNo(Long orgId, String propNo) {
        List<ProvisionalAssesmentMstDto> propList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getPropDetailFromAssByPropNo(orgId, propNo);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                propList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return propList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> fetchAssessmentMasterForAmalgamation(long orgId, String parentPropNo,
            List<String> propNoList, String parentOldPropNo, List<String> oldPropNoList) {
        Organisation org = orgService.getOrganisationById(orgId);
        List<AssesmentMastEntity> assessmentMaster = null;
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        List<ProvisionalAssesmentMstDto> propList = new ArrayList<>(0);
//        int count = 0;
//        if (!propNoList.isEmpty()) {
//            count = propertyMainBillService.getPaidBillCountByPropNoList(propNoList, orgId, finYearId);
//        }
//        if (count <= 0) {
            if (parentPropNo != null && !parentPropNo.isEmpty())
                propNoList.add(parentPropNo);
            if (parentOldPropNo != null && !parentOldPropNo.isEmpty())
                oldPropNoList.add(parentOldPropNo);
            if (propNoList != null && !propNoList.isEmpty()) {
                // 122590- No need to put financial year check for demand based system (KDMC) as per told by samadhan sir
                if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.DBA)) {
                    assessmentMaster = assesmentMstRepository.fetchAssessmentMstForServicesByPropNoOnly(orgId, propNoList);
                } else {
                    assessmentMaster = assesmentMstRepository.fetchAssessmentMstForServicesByPropNo(orgId, finYearId, propNoList);
                }
            } else if (oldPropNoList != null && !oldPropNoList.isEmpty()) {
                // 122590- No need to put financial year check for demand based system (KDMC) as per told by samadhan sir
                if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.DBA)) {
                    assessmentMaster = assesmentMstRepository.fetchAssessmentMasterForAmalgamationByOldPropOnly(orgId,
                            oldPropNoList);
                } else {
                    assessmentMaster = assesmentMstRepository.fetchAssessmentMasterForAmalgamationByOldProp(orgId,
                            oldPropNoList, finYearId);
                }
            }
            if (assessmentMaster != null) {
                assessmentMaster.forEach(mst -> {
                    mst.setFlag(null);
                    propList.add(mapAssessmentMasterToProvisionalDto(mst));
                });
            }
//        }
        return propList;
    }

    @Override
    @Transactional
    public List<AssesmentMastEntity> getAssessmentMstByPropNo(long orgId, String assNo) {
        return assesmentMstRepository.getAssessmentMstByPropNo(orgId, assNo);
    }

    @Override
    public Long getApplicationNoByPropNoForObjection(Long orgId, String assNo, Long serviceId) {
        Long applinId = null;
        applinId = assesmentMstRepository.getApplicationNoByLogicalPropNoForObjection(orgId, assNo, serviceId);
        if (applinId != null) {
            return applinId;
        } else {
            return assesmentMstRepository.getApplicationNoByPropNoForObjection(orgId, assNo, serviceId);
        }
    }

    @Override
    public ProvisionalAssesmentMstDto fetchLatestAssessmentByPropNo(Long orgId, String propertyNo) {
        ProvisionalAssesmentMstDto assMstDto = null;
        AssesmentMastEntity assMst = assesmentMstRepository.fetchPropertyByPropNo(propertyNo);
        if (assMst != null) {
            assMstDto = new ProvisionalAssesmentMstDto();
            BeanUtils.copyProperties(assMst, assMstDto);
            final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
            assMst.getAssesmentDetailEntityList().forEach(provDetEnt -> {
                ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                BeanUtils.copyProperties(provDetEnt, provDetDto);
                provDetEnt.getAssesmentFactorDetailEntityList().forEach(proAssFactEnt -> {
                    ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                    provAssFactDtoList.add(proAssFactDto);
                });
                provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                provAssDetDtoList.add(provDetDto);
            });
            assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
            if (!assMst.getAssesmentOwnerDetailEntityList().isEmpty()
                    && assMst.getAssesmentOwnerDetailEntityList() != null) {
                final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
                assMst.getAssesmentOwnerDetailEntityList().forEach(provOwnEnt -> {
                    ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                    BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
                    provAssOwnDtoList.add(provAssOwnDto);
                });
                assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
            }
        }
        return assMstDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailFromMainAssByPropNoOrOldPropNo(Long orgId, String propertyNo,
            String oldPropertyNo) {
        List<AssesmentMastEntity> assMstEntList = null;
        assMstEntList = assesmentMstRepository.getPropDetailFromMainAssByPropNo(orgId,
                propertyNo);
        if ((assMstEntList == null || assMstEntList.isEmpty())
                && (oldPropertyNo != null && !oldPropertyNo.equals(MainetConstants.BLANK))) {
            assMstEntList = assesmentMstRepository.getPropDetailFromMainAssByOldPropNo(orgId,
                    oldPropertyNo);
        }
        return mapListFromEntitytoDto(assMstEntList);
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailFromHouseNo(Long orgId, String houseNo) {
        List<AssesmentMastEntity> assMstEntList = null;
        assMstEntList = assesmentMstRepository.getPropDetailFromHouseNo(orgId,
        		houseNo);
		/*
		 * if ((assMstEntList == null || assMstEntList.isEmpty()) && (oldPropertyNo !=
		 * null && !oldPropertyNo.equals(MainetConstants.BLANK))) { assMstEntList =
		 * assesmentMstRepository.getPropDetailFromMainAssByOldPropNo(orgId,
		 * oldPropertyNo); }
		 */
        return mapListFromEntitytoDto(assMstEntList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailByPropNoOnly(String propertyNo) {
        List<AssesmentMastEntity> assMstEntList = assesmentMstRepository.getPropDetailByPropNoOnly(
                propertyNo);
        return mapListFromEntitytoDto(assMstEntList);
    }

    private List<ProvisionalAssesmentMstDto> mapListFromEntitytoDto(List<AssesmentMastEntity> assMstEntList) {
        List<ProvisionalAssesmentMstDto> assMstDtoList = new ArrayList<>();
        if (assMstEntList != null && !assMstEntList.isEmpty()) {
            assMstEntList.forEach(provAssMast -> {
                ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
                BeanUtils.copyProperties(provAssMast, assMstDto);
                final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
                provAssMast.getAssesmentDetailEntityList().forEach(provDetEnt -> {
                    ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                    final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                    BeanUtils.copyProperties(provDetEnt, provDetDto);
                    provDetEnt.getAssesmentFactorDetailEntityList().forEach(proAssFactEnt -> {
                        ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                        BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                        provAssFactDtoList.add(proAssFactDto);
                    });
                    provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                    provAssDetDtoList.add(provDetDto);
                });
                assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
                if (!provAssMast.getAssesmentOwnerDetailEntityList().isEmpty()
                        && provAssMast.getAssesmentOwnerDetailEntityList() != null) {
                    final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
                    provAssMast.getAssesmentOwnerDetailEntityList().forEach(provOwnEnt -> {
                        ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                        BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
                        provAssOwnDtoList.add(provAssOwnDto);
                    });
                    assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
                }

                assMstDtoList.add(assMstDto);
            });
        }
        return assMstDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> fetchApplicationAgainstProperty(long orgId, String proertyNo) {
        return assesmentMstRepository.fetchApplicationAgainstProperty(orgId, proertyNo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status,
            Long finYearId, Long serviceId) {
        return iAssessmentMastDao.checkForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status, finYearId, serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteDetailInDESEdit(ProvisionalAssesmentMstDto provAssDto, Long orgId,
            Long empId, String ipAddress) {
        ProvisionalAssesmentMstDto provAssOrginalMstDto = null;

        // List<ProvisionalAssesmentMstDto> provAssDtoList = getPropDetailFromAssByPropNo(orgId, provAssDto.getAssNo());
        List<ProvisionalAssesmentMstDto> provAssDtoList = new ArrayList<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(provAssDto.getFlatNo())) {
            provAssDtoList = getPropDetailFromAssByPropNoFlatNo(orgId, provAssDto.getAssNo(), provAssDto.getFlatNo());
        } else {
            provAssDtoList = getPropDetailFromAssByPropNo(orgId, provAssDto.getAssNo());
        }

        if (!provAssDtoList.isEmpty()) {

            provAssOrginalMstDto = propertyAuthorizationService
                    .getAssesmentMstDtoForDisplay(provAssDtoList);
            if (provAssOrginalMstDto != null) {
                for (ProvisionalAssesmentDetailDto provDet : provAssOrginalMstDto.getProvisionalAssesmentDetailDtoList()) {

                    boolean detExit = provAssDto.getProvisionalAssesmentDetailDtoList().stream()
                            .filter(s -> s.getProAssdId() == provDet.getProAssdId())
                            .findFirst()
                            .isPresent();
                    if (!detExit) {
                        mainAssessmentDetailRepository.delete(provDet.getProAssdId());
                    }
                    provAssDto.getProvisionalAssesmentDetailDtoList().stream()
                            .filter(s -> s.getProAssdId() == provDet.getProAssdId())
                            .forEach(detorg -> {
                                for (ProvisionalAssesmentFactorDtlDto provfact : provDet
                                        .getProvisionalAssesmentFactorDtlDtoList()) {
                                    boolean factorExit = detorg.getProvisionalAssesmentFactorDtlDtoList().stream()
                                            .filter(s -> s.getProAssfId() == provfact.getProAssfId())
                                            .findFirst()
                                            .isPresent();
                                    if (!factorExit) {
                                        mainAssessmentFactorRepository.delete(provfact.getProAssfId());
                                    }
                                }

                                // 97207 By Arun
                                for (PropertyRoomDetailsDto provfact : provDet
                                        .getRoomDetailsDtoList()) {
                                    boolean factorExit = detorg.getRoomDetailsDtoList().stream()
                                            .filter(s -> s.getProRoomId() == provfact.getProRoomId())
                                            .findFirst()
                                            .isPresent();
                                    if (!factorExit) {
                                        mnAssRoomRepository.delete(provfact.getProRoomId());
                                    }
                                }
                                //

                            });
                }

                for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssOrginalMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                    boolean ownerExit = provAssDto.getProvisionalAssesmentOwnerDtlDtoList().stream()
                            .filter(s -> s.getProAssoId() == provOwndto.getProAssoId())
                            .findFirst()
                            .isPresent();

                    if (!ownerExit) {
                        mainAssessmentOwnerRepository.delete(provOwndto.getProAssoId());
                    }

                }

            }
        }
    }

    @Override
    @Transactional
    public void saveAndUpdateAssessmentMastInMutation(ProvisionalAssesmentMstDto provAssDto, Long orgId,
            Long empId, String ipAddress) {

        provAssDto.setUpdatedBy(empId);
        provAssDto.setLgIpMacUpd(ipAddress);
        provAssDto.setUpdatedDate(new Date());
        AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
        BeanUtils.copyProperties(provAssDto, provAssetMst);

        final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssDto.getProvisionalAssesmentOwnerDtlDtoList()) {
            AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            if (provOwndto.getProAssoId() == 0) {
                final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                        MainetConstants.Property.primColumn.PRO_ASSO_ID,
                        null,
                        null);
                provAsseOwnerEnt.setProAssoId(proAssoId);
                provAsseOwnerEnt.setCreatedBy(empId);
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setMnAssId(provAssetMst);
                provAsseOwnerEnt.setAssoStartDate(provAssetMst.getAssAcqDate());
                provAsseOwnerEnt.setOrgId(orgId);
                provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setMnAssId(provAssetMst);
            } else {
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.INACTIVE);
                provAsseOwnerEnt.setMnAssId(provAssetMst);
                provAsseOwnerEnt.setUpdatedBy(empId);
                provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                provAsseOwnerEnt.setUpdatedDate(new Date());
            }
            provAsseOwnEntList.add(provAsseOwnerEnt);
        }
        provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
        AssesmentMastEntity assMstEntity = assesmentMstRepository.save(provAssetMst);
        maintainAstMstHistory(assMstEntity);
    }

    @Override
    @Transactional
    public void saveAndUpdateAssessmentMastForOnlyForDES(ProvisionalAssesmentMstDto provAssDto, Long orgId,
            Long empId, String ipAddress) {

        Long finYear = provAssDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
        provAssDto.setFaYearId(finYear);
        if (provAssDto.getProAssId() == 0) {
            final long proAssId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                    MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
                    MainetConstants.Property.primColumn.PRO_ASS_ID,
                    null,
                    null);
            String propNoExist = provAssDto.getAssNo();
            if (StringUtils.isEmpty(propNoExist)) {
                propNoExist = propertyService.getPropertyNo(orgId, provAssDto);
                getBarcode(provAssDto, propNoExist);
            }
            provAssDto.setProAssId(proAssId);
            provAssDto.setAssNo(propNoExist);
            provAssDto.setOrgId(orgId);
            provAssDto.setCreatedBy(empId);
            provAssDto.setLgIpMac(ipAddress);
            provAssDto.setCreatedDate(new Date());
            provAssDto.setAssActive(MainetConstants.STATUS.ACTIVE);
            provAssDto.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
            provAssDto.setAssAutStatus(MainetConstants.Property.AuthStatus.AUTH);
        } else {
            provAssDto.setUpdatedBy(empId);
            provAssDto.setLgIpMacUpd(ipAddress);
            provAssDto.setUpdatedDate(new Date());
        }
        AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
        BeanUtils.copyProperties(provAssDto, provAssetMst);

        final List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
        for (ProvisionalAssesmentDetailDto provDet : provAssDto.getProvisionalAssesmentDetailDtoList()) {
            AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
            BeanUtils.copyProperties(provDet, provAsseDet);
            if (provDet.getProAssdId() == 0) {
                final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
                        MainetConstants.Property.primColumn.PRO_ASSD_ID,
                        null,
                        null);
                provAsseDet.setProAssdId(proAssdId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setMnAssId(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
            } else {
                provAsseDet.setUpdatedBy(empId);
                provAsseDet.setLgIpMacUpd(ipAddress);
                provAsseDet.setUpdatedDate(new Date());
                provAsseDet.setMnAssId(provAssetMst);

            }
            final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
            for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
                BeanUtils.copyProperties(provfact, provAssFact);
                provAssFact.setOrgId(orgId);
                if (provfact.getProAssfId() == 0) {
                    final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
                            MainetConstants.Property.primColumn.PRO_ASSF_ID,
                            null,
                            null);
                    provAssFact.setProAssfId(proAssfId);
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setMnAssdId(provAsseDet);
                    provAssFact.setMnAssId(provAssetMst);
                    // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                } else {
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    if (provAssFact.getCreatedBy() == null) {
                        provAssFact.setCreatedBy(empId);
                    }
                    if (provAssFact.getCreatedDate() == null) {
                        provAssFact.setCreatedDate(new Date());
                    }
                    if (provAssFact.getLgIpMac() == null || provAssFact.getLgIpMac().isEmpty()) {
                        provAssFact.setLgIpMac(ipAddress);
                    }
                    provAssFact.setUpdatedBy(empId);
                    provAssFact.setUpdatedDate(new Date());
                    provAssFact.setLgIpMacUpd(ipAddress);
                    provAssFact.setMnAssdId(provAsseDet);
                    provAssFact.setMnAssId(provAssetMst);
                }
                provAsseFactList.add(provAssFact);
            }
            provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

            /// #97207 by Arun
            final List<AssessmentRoomDetailEntity> assessmentRoomDetailEntityList = new ArrayList<>();
            for (PropertyRoomDetailsDto roomDetail : provDet.getRoomDetailsDtoList()) {
                AssessmentRoomDetailEntity assessmentRoomDetailEntity = new AssessmentRoomDetailEntity();
                BeanUtils.copyProperties(roomDetail, assessmentRoomDetailEntity);
                if (roomDetail.getOrgId() == null) {
                    assessmentRoomDetailEntity.setOrgId(orgId);
                    assessmentRoomDetailEntity.setCreatedBy(empId);
                    assessmentRoomDetailEntity.setCreatedDate(new Date());
                }
                assessmentRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                assessmentRoomDetailEntity.setAssPropRoomDet(provAsseDet);
                assessmentRoomDetailEntityList.add(assessmentRoomDetailEntity);
            }
            provAsseDet.setRoomDetailEntityList(assessmentRoomDetailEntityList);
            /// end

            provAsseDetList.add(provAsseDet);
        }
        provAssetMst.setAssesmentDetailEntityList(provAsseDetList);
        final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssDto.getProvisionalAssesmentOwnerDtlDtoList()) {
            AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            if (provOwndto.getProAssoId() == 0) {
                final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                        MainetConstants.Property.primColumn.PRO_ASSO_ID,
                        null,
                        null);
                provAsseOwnerEnt.setProAssoId(proAssoId);
                provAsseOwnerEnt.setCreatedBy(empId);
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setMnAssId(provAssetMst);
                provAsseOwnerEnt.setAssoStartDate(provAssetMst.getAssAcqDate());
                provAsseOwnerEnt.setOrgId(orgId);
                provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
            }
            provAsseOwnerEnt.setUpdatedBy(empId);
            provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
            provAsseOwnerEnt.setUpdatedDate(new Date());
            provAsseOwnerEnt.setMnAssId(provAssetMst);

            provAsseOwnEntList.add(provAsseOwnerEnt);
        }
        provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
        AssesmentMastEntity assMstEntity = assesmentMstRepository.save(provAssetMst);
        maintainAstMstHistory(assMstEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public void saveAndUpdateAssessmentMastForOnlyForDESIndividualBill(ProvisionalAssesmentMstDto provAssDto,
            Long orgId, Long empId, String ipAddress) {

        Boolean flag = false;
        Long finYear = provAssDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
        provAssDto.setFaYearId(finYear);
        if (provAssDto.getProAssId() == 0) {

            String propNoExist = provAssDto.getAssNo();
            if (StringUtils.isEmpty(propNoExist)) {
                propNoExist = propertyService.getPropertyNo(orgId, provAssDto);
                getBarcode(provAssDto, propNoExist);
            }
            provAssDto.setAssNo(propNoExist);
            provAssDto.setOrgId(orgId);
            provAssDto.setCreatedBy(empId);
            provAssDto.setLgIpMac(ipAddress);
            provAssDto.setCreatedDate(new Date());
            provAssDto.setAssActive(MainetConstants.STATUS.ACTIVE);
            provAssDto.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
            provAssDto.setAssAutStatus(MainetConstants.Property.AuthStatus.AUTH);
        } else {
            flag = true;
            provAssDto.setUpdatedBy(empId);
            provAssDto.setLgIpMacUpd(ipAddress);
            provAssDto.setUpdatedDate(new Date());
        }
        for (ProvisionalAssesmentDetailDto provDet : provAssDto.getProvisionalAssesmentDetailDtoList()) {

            if (!flag) {
                final long proAssId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
                        MainetConstants.Property.primColumn.PRO_ASS_ID,
                        null,
                        null);
                provAssDto.setProAssId(proAssId);
            }
            AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
            BeanUtils.copyProperties(provAssDto, provAssetMst);
            provAssetMst.setFlatNo(provDet.getFlatNo());
            provAssetMst.setLogicalPropNo(
                    provAssDto.getAssNo() + MainetConstants.operator.UNDER_SCORE + provDet.getFlatNo());
            List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
            BeanUtils.copyProperties(provDet, provAsseDet);
            if (provDet.getProAssdId() == 0) {
                final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
                        MainetConstants.Property.primColumn.PRO_ASSD_ID,
                        null,
                        null);
                provAsseDet.setProAssdId(proAssdId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setMnAssId(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
            } else {
                provAsseDet.setUpdatedBy(empId);
                provAsseDet.setLgIpMacUpd(ipAddress);
                provAsseDet.setUpdatedDate(new Date());
                provAsseDet.setMnAssId(provAssetMst);

            }

            final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
            for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
                BeanUtils.copyProperties(provfact, provAssFact);
                provAssFact.setOrgId(orgId);
                if (provfact.getProAssfId() == 0) {
                    final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
                            MainetConstants.Property.primColumn.PRO_ASSF_ID,
                            null,
                            null);
                    provAssFact.setProAssfId(proAssfId);
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setMnAssdId(provAsseDet);
                    provAssFact.setMnAssId(provAssetMst);
                } else {
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    if (provAssFact.getCreatedBy() == null) {
                        provAssFact.setCreatedBy(empId);
                    }
                    if (provAssFact.getCreatedDate() == null) {
                        provAssFact.setCreatedDate(new Date());
                    }
                    if (provAssFact.getLgIpMac() == null || provAssFact.getLgIpMac().isEmpty()) {
                        provAssFact.setLgIpMac(ipAddress);
                    }
                    provAssFact.setUpdatedBy(empId);
                    provAssFact.setUpdatedDate(new Date());
                    provAssFact.setLgIpMacUpd(ipAddress);
                    provAssFact.setMnAssdId(provAsseDet);
                    provAssFact.setMnAssId(provAssetMst);
                }
                provAsseFactList.add(provAssFact);
            }
            provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

            final List<AssessmentRoomDetailEntity> assessmentRoomDetailEntityList = new ArrayList<>();
            for (PropertyRoomDetailsDto roomDetail : provDet.getRoomDetailsDtoList()) {
                AssessmentRoomDetailEntity assessmentRoomDetailEntity = new AssessmentRoomDetailEntity();
                BeanUtils.copyProperties(roomDetail, assessmentRoomDetailEntity);
                if (roomDetail.getOrgId() == null) {
                    assessmentRoomDetailEntity.setOrgId(orgId);
                    assessmentRoomDetailEntity.setCreatedBy(empId);
                    assessmentRoomDetailEntity.setCreatedDate(new Date());
                }
                assessmentRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                assessmentRoomDetailEntity.setAssPropRoomDet(provAsseDet);
                assessmentRoomDetailEntityList.add(assessmentRoomDetailEntity);
            }
            provAsseDet.setRoomDetailEntityList(assessmentRoomDetailEntityList);
            /// end
            provAsseDetList.add(provAsseDet);
            provAssetMst.setAssesmentDetailEntityList(provAsseDetList);

            final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
            for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
                BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                if (provOwndto.getProAssoId() == 0) {
                    final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                            MainetConstants.Property.primColumn.PRO_ASSO_ID,
                            null,
                            null);
                    provAsseOwnerEnt.setProAssoId(proAssoId);
                    provAsseOwnerEnt.setCreatedBy(empId);
                    provAsseOwnerEnt.setLgIpMac(ipAddress);
                    provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                    provAsseOwnerEnt.setCreatedDate(new Date());
                    provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                    provAsseOwnerEnt.setMnAssId(provAssetMst);
                    provAsseOwnerEnt.setAssoStartDate(provAssetMst.getAssAcqDate());
                    provAsseOwnerEnt.setOrgId(orgId);
                    provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                    provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                    provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                    provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                }
                provAsseOwnerEnt.setUpdatedBy(empId);
                provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                provAsseOwnerEnt.setUpdatedDate(new Date());
                provAsseOwnerEnt.setMnAssId(provAssetMst);

                provAsseOwnEntList.add(provAsseOwnerEnt);
            }
            provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
            AssesmentMastEntity assMstEntity = assesmentMstRepository.save(provAssetMst);
            maintainAstMstHistory(assMstEntity);
        }

    }

    @Transactional
    @Override
    public List<AssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(Long orgId, String propertryNo) {
        return assesmentMstRepository.getPrimaryOwnerDetailByPropertyNo(orgId, propertryNo);
    }

    private void getBarcode(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, String propNoExist) {
        if (propNoExist != null) {
            try {
                provisionalAssesmentMstDto.setBarCode(barcodeGenerator.getBarcodeInByteArray(propNoExist));
            } catch (Exception e) {
                throw new FrameworkException("error occurs while generating barcode for PropertyNum", e);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getAssesmentMstDtoListByAppId(Long applicationId, Long orgId) {
        List<ProvisionalAssesmentMstDto> assList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getAssessmentMstListByApplicationId(orgId,
                applicationId);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                assList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return assList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getAssessmentMstListByAssIds(List<Long> assIdList, Long orgId) {
        List<ProvisionalAssesmentMstDto> assList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getAssessmentMstListByAssIds(orgId, assIdList);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                assList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return assList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssesmentMastEntity> getAssesmentMstEntListByAppId(Long applicationId, Long orgId) {
        return assesmentMstRepository.getAssessmentMstListByApplicationId(orgId,
                applicationId);
    }

    /*
     * Task #30569 list of properties on which assessment is not done on current year, input List<Long> assIdList, Long orgId
     * @return ProvisionalAssesmentMstDto
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getAssMstListForProvisionalDemand(Long orgId) {
        List<ProvisionalAssesmentMstDto> assList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getAssMstListForProvisionalDemandByOrgId(orgId);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                assList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return assList;
    }

    @Override
    public boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status,
            Long finYearId) {
        return iAssessmentMastDao.checkForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status, finYearId);
    }

    @Override
    public List<String> checkActiveFlag(String propNo, Long orgId) {
        return assesmentMstRepository.checkActiveFlag(propNo, orgId);
    }

    @Override
    public List<String> checkActiveFlagByOldPropNo(String OldPropNo, Long orgId) {
        return assesmentMstRepository.checkActiveFlagByOldPropNo(OldPropNo, orgId);
    }

    @Override
    public List<String> checkActiveFlagByOldPropNoNFlatNo(String OldPropNo, String flatNo, Long orgId) {
        return assesmentMstRepository.checkActiveFlagByOldPropNoNFlatNo(OldPropNo, flatNo, orgId);
    }

    @Override
    public List<String> checkActiveFlagByPropnonFlatNo(String propNo, String flatNo, Long orgId) {
        return assesmentMstRepository.checkActiveFlagByPropNFlatNO(propNo, flatNo, orgId);
    }

    @Override
    public Long getAssMasterIdbyPropNo(String propNo, Long orgId) {
        return assesmentMstRepository.fetchAssdIdbyPropNo(propNo, orgId);
    }

    @Override
    public List<Long> getAssdIdListbyPropNo(String propNo, Long orgId) {
    	return assesmentMstRepository.fetchAssdIdListbyPropNo(propNo, orgId);
    }
    
    @Override
    public Long getAssDetailIdByAssIdAndUnitNo(Long assId, Long unitNo) {
        AssesmentMastEntity findOne = assesmentMstRepository.findOne(assId);
        return mainAssessmentDetailRepository.fetchAssDetailIdByAssIdAndUnitNo(findOne, unitNo);
    }

    @Override
    public Long getAssOwnerIdByAssId(Long assId) {
        AssesmentMastEntity findOne = assesmentMstRepository.findOne(assId);
        return mainAssessmentOwnerRepository.fetchAssOwnerIdByAssId(findOne);
    }

    @Override
    public Long getAssFactorIdByAssId(Long assId) {
        AssesmentMastEntity findOne = assesmentMstRepository.findOne(assId);
        return mainAssessmentFactorRepository.fetchAssFactorIdByAssId(findOne);
    }

    @Override
    public List<Double> getPlotAreaByPropNo(Long orgId, List<String> propId) {
        return assesmentMstRepository.fetchPlotAreaByPropNo(orgId, propId);
    }

    @Override
    @Transactional
    public boolean updateDataEntry(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId,
            String clientIpAddress) {
        boolean updateFlag = false;
        Organisation org = new Organisation(orgId);
        List<Long> proAssIdList = assesmentMstRepository.fetchAssdIdListbyPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
        // Maintain history
        for (Long proAssId : proAssIdList) {
            AssesmentMastEntity assesmentMastEntity = assesmentMstRepository.findOne(proAssId);
            AssesmentMastHistEntity mastHistory = new AssesmentMastHistEntity();
            mastHistory.setMnAssId(proAssId);
            assesmentMastEntity.setUpdatedBy(empId);
            assesmentMastEntity.setUpdatedDate(new Date());
            assesmentMastEntity.setLgIpMacUpd(clientIpAddress);
            auditService.createHistory(assesmentMastEntity, mastHistory);

            List<AssesmentDetailEntity> detailEntityList = mainAssessmentDetailRepository.fetchAssdIdByAssId(assesmentMastEntity);

            for (AssesmentDetailEntity detailEntity : detailEntityList) {
                AssesmentDetailHistEntity detailHistory = new AssesmentDetailHistEntity();
                detailHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                detailHistory.setMnAssId(proAssId);
                detailEntity.setUpdatedBy(empId);
                detailEntity.setUpdatedDate(new Date());
                detailEntity.setLgIpMacUpd(clientIpAddress);
                auditService.createHistory(detailEntity, detailHistory);
            }
        }
        List<AssesmentOwnerDtlEntity> ownerDtlEntity2 = mainAssessmentOwnerRepository
                .fetchOwnerDetailListByPropNo(provisionalAssesmentMstDto.getAssNo(), provisionalAssesmentMstDto.getOrgId());
        for (AssesmentOwnerDtlEntity oldOwnerEntity : ownerDtlEntity2) {
            AssesmentMastEntity mnAssId = oldOwnerEntity.getMnAssId();
            AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
            ownerDtlHistEntity.setProAssoId(oldOwnerEntity.getProAssoId());
            ownerDtlHistEntity.setMnAssId(mnAssId.getProAssId());
            oldOwnerEntity.setUpdatedBy(empId);
            oldOwnerEntity.setUpdatedDate(new Date());
            oldOwnerEntity.setLgIpMacUpd(clientIpAddress);
            auditService.createHistory(oldOwnerEntity, ownerDtlHistEntity);
        }// End Maintain history
         // update Provision table
        List<Long> provisionalAssIdList = provisionalAssesmentMstRepository
                .fetchProAssdIdListbyPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
        if (CollectionUtils.isNotEmpty(provisionalAssIdList)) {
            for (Long provisionalAssId : provisionalAssIdList) {
                ProvisionalAssesmentMstEntity provisionalAssesmentMstEntity = provisionalAssesmentMstRepository
                        .findOne(provisionalAssId);
                List<ProvisionalAssesmentDetailEntity> proDetailEntityList = provisionalAssesmentDetRepository
                        .fetchProAssDetailsByProAssMst(provisionalAssesmentMstEntity);
                List<ProvisionalAssesmentDetailDto> detailDtoList = provisionalAssesmentMstDto
                        .getProvisionalAssesmentDetailDtoList();
                List<ProvisionalAssesmentOwnerDtlDto> ownerList = provisionalAssesmentMstDto
                        .getProvisionalAssesmentOwnerDtlDtoList();
                for (int i = 0; i < proDetailEntityList.size(); i++) {
                    ProvisionalAssesmentDetailDto detailDto = detailDtoList.get(i);
                    ProvisionalAssesmentOwnerDtlDto owner = ownerList.get(0);
                    Long occupancyType = detailDto.getAssdOccupancyType();
                    LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(occupancyType, org);
                    for (int j = 0; j < proDetailEntityList.size(); j++) {
                        Long proAssdId = proDetailEntityList.get(i).getProAssdId();
                        if ("OWN".equals(lookUp.getLookUpCode())) {
                            provisionalAssesmentDetRepository.updateOccupierName(orgId, proAssdId, owner.getAssoOwnerName(),
                                    empId,
                                    clientIpAddress);
                        }
                    }
                }
                List<ProvisionalAssesmentOwnerDtlEntity> proOwnerDtlEntityList = provisionalAssesmentOwnerRepository
                        .fetchOwnerDetailListByProAssMast(provisionalAssesmentMstEntity, orgId);
                for (int i = 0; i < ownerList.size(); i++) {
                    ProvisionalAssesmentOwnerDtlDto owner = ownerList.get(i);
                    for (int j = 0; j < proOwnerDtlEntityList.size(); j++) {
                        Long proAssoId = proOwnerDtlEntityList.get(i).getProAssoId();
                        provisionalAssesmentOwnerRepository.updateOwnerDetailByProAssoId(orgId, proAssoId,
                                owner.getAssoOwnerName(),
                                owner.getAssoMobileno(), owner.getAssoAddharno(), owner.getAssoGuardianName(), owner.geteMail(),
                                owner.getAssoPanno(), empId, clientIpAddress);
                    }
                }
                provisionalAssesmentMstRepository.updateDataEntryDetails(orgId, provisionalAssesmentMstDto.getAssNo(),
                        empId, clientIpAddress,
                        provisionalAssesmentMstDto.getAssAddress(),
                        provisionalAssesmentMstDto.getLocId(), provisionalAssesmentMstDto.getAssPincode(), MainetConstants.FlagY,
                        provisionalAssesmentMstDto.getAssOldpropno(), provisionalAssesmentMstDto.getTppPlotNo(),provisionalAssesmentMstDto.getNewHouseNo());

                // Maintain provisional history
                ProAssMstHisEntity proAssMstHisEntity = new ProAssMstHisEntity();
                proAssMstHisEntity.setProAssId(provisionalAssId);
                proAssMstHisEntity.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                provisionalAssesmentMstEntity.setUpdatedBy(empId);
                provisionalAssesmentMstEntity.setUpdatedDate(new Date());
                provisionalAssesmentMstEntity.setLgIpMacUpd(clientIpAddress);
                auditService.createHistory(provisionalAssesmentMstEntity, proAssMstHisEntity);

                for (ProvisionalAssesmentDetailEntity proDetailEntity : proDetailEntityList) {
                    ProAssDetailHisEntity detailHisEntity = new ProAssDetailHisEntity();
                    detailHisEntity.setProAssdId(provisionalAssId);
                    detailHisEntity.setAssId(provisionalAssId);
                    detailHisEntity.setAssdOccupierName(proDetailEntity.getOccupierName());
                    detailHisEntity.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                    proDetailEntity.setUpdatedBy(empId);
                    proDetailEntity.setUpdatedDate(new Date());
                    proDetailEntity.setLgIpMacUpd(clientIpAddress);
                    auditService.createHistory(proDetailEntity, detailHisEntity);
                }
                for (ProvisionalAssesmentOwnerDtlEntity proOwnerDtlEntity : proOwnerDtlEntityList) {
                    ProAssOwnerHisEntity ownerHisEntity = new ProAssOwnerHisEntity();
                    ownerHisEntity.setProAssoId(proOwnerDtlEntity.getProAssoId());
                    ownerHisEntity.setProAssId(provisionalAssId);
                    ownerHisEntity.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                    proOwnerDtlEntity.setUpdatedBy(empId);
                    proOwnerDtlEntity.setUpdatedDate(new Date());
                    proOwnerDtlEntity.setLgIpMacUpd(clientIpAddress);
                    auditService.createHistory(proOwnerDtlEntity, ownerHisEntity);
                }
            }// End update Provision table
        }
        // update Main table
        for (Long proAssId : proAssIdList) {
            AssesmentMastEntity assesmentMastEntity = assesmentMstRepository.findOne(proAssId);
            List<AssesmentDetailEntity> detailEntityList = mainAssessmentDetailRepository.fetchAssdIdByAssId(assesmentMastEntity);
            List<ProvisionalAssesmentDetailDto> detailDtoList = provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList();
            List<ProvisionalAssesmentOwnerDtlDto> ownerList = provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList();
			/*
			 * for (int i = 0; i < detailEntityList.size(); i++) {
			 * ProvisionalAssesmentDetailDto detailDto = detailDtoList.get(i);
			 * ProvisionalAssesmentOwnerDtlDto owner = ownerList.get(0); Long occupancyType
			 * = detailDto.getAssdOccupancyType(); LookUp lookUp =
			 * CommonMasterUtility.getNonHierarchicalLookUpObject(occupancyType, org); for
			 * (int j = 0; j < detailEntityList.size(); j++) { Long proAssdId =
			 * detailEntityList.get(i).getProAssdId(); if
			 * ("OWN".equals(lookUp.getLookUpCode())) {
			 * mainAssessmentDetailRepository.updateOccupierName(orgId, proAssdId,
			 * owner.getAssoOwnerName(), empId, clientIpAddress); } } }
			 */
            detailEntityList.forEach(detail ->{
            	ProvisionalAssesmentOwnerDtlDto owner = ownerList.get(0);
            	Long occupancyType = detail.getAssdOccupancyType();
                LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(occupancyType, org);
                if ("OWN".equals(lookUp.getLookUpCode())) {
                    mainAssessmentDetailRepository.updateOccupierName(orgId, detail.getProAssdId(), owner.getAssoOwnerName(), empId,
                            clientIpAddress);
                }
            });
            List<AssesmentOwnerDtlEntity> ownerDtlEntity = mainAssessmentOwnerRepository
                    .fetchOwnerDetailListByProAssId(assesmentMastEntity, orgId);
            for (int i = 0; i < ownerList.size(); i++) {
                ProvisionalAssesmentOwnerDtlDto owner = ownerList.get(i);
                for (int j = 0; j < ownerDtlEntity.size(); j++) {
                    Long proAssoId = ownerDtlEntity.get(i).getProAssoId();
					mainAssessmentOwnerRepository.updateOwnerDetailByProAssoId(orgId, proAssoId,
							owner.getAssoOwnerName(), owner.getAssoMobileno(), owner.getAssoAddharno(),
							owner.getGenderId(), owner.getRelationId(), owner.getAssoGuardianName(), owner.geteMail(),
							owner.getAssoPanno(), empId, clientIpAddress);
                }
            }
            
            Long currentYearId = iFinancialYear.getFinanceYearId(new Date());
            
            List<DuplicateBillDTO> findByRefNoAndYearIdList = duplicateBillService.findByRefNoAndYearId(provisionalAssesmentMstDto.getAssNo(), orgId, currentYearId);
            if(CollectionUtils.isNotEmpty(findByRefNoAndYearIdList)) {
            	findByRefNoAndYearIdList.forEach(findByRefNoAndYearId ->{
            		try {
                		PrintBillMaster dataDTO = null;
    					 dataDTO = new ObjectMapper().readValue(findByRefNoAndYearId.getDupBillData(),
    							 PrintBillMaster.class);
    					 StringBuilder name = new StringBuilder(ownerList.get(0).getAssoOwnerName());
    				        if (ownerList.get(0).getRelationId() != null) {
    				            name.append(" " + CommonMasterUtility.getNonHierarchicalLookUpObject(ownerList.get(0).getRelationId(), org)
    				                    .getDescLangFirst());
    				            name.append(" " + ownerList.get(0).getAssoGuardianName());
    				        }
    					 dataDTO.setNameV(name.toString());
    					 String writeValueAsString = new ObjectMapper().writeValueAsString(dataDTO);
    					 findByRefNoAndYearId.setDupBillData(new ObjectMapper().writeValueAsString(dataDTO));
    					 ApplicationContextProvider.getApplicationContext().getBean(DuplicateBillRepository.class).updateOwnerNameById(findByRefNoAndYearId.getDupBillId(), writeValueAsString);
    					 
						/*
						 * DuplicateBillEntity entity = new DuplicateBillEntity();
						 * BeanUtils.copyProperties(findByRefNoAndYearId, entity);
						 * ApplicationContextProvider.getApplicationContext().getBean(
						 * DuplicateBillRepository.class).save(entity);
						 */
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	});
            	
            }
            int count = assesmentMstRepository.updateDataEntryDetails(orgId, provisionalAssesmentMstDto.getAssNo(),
                    provisionalAssesmentMstDto.getAssOwnerType(), empId, clientIpAddress,
                    provisionalAssesmentMstDto.getAssAddress(),
                    provisionalAssesmentMstDto.getLocId(), provisionalAssesmentMstDto.getAssPincode(), MainetConstants.FlagY,
                    provisionalAssesmentMstDto.getAssOldpropno(), provisionalAssesmentMstDto.getTppPlotNo(),provisionalAssesmentMstDto.getNewHouseNo());
            if (count > 0) {
                updateFlag = true;
            }
        }// End update Main table
        return updateFlag;
    }

    @Override
    public String getReceiptDelFlag(String propNo, Long orgId) {
        return assesmentMstRepository.checkReceiptDelFlag(propNo, orgId);
    }

    @Override
    public void updateActiveFlagOfProperty(Long orgId, String propNo, String assActive) {
        assesmentMstRepository.updateActiveFlagOfProperty(orgId, propNo, assActive);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailByPropNoWithoutActiveCon(String propertyNo) {
        List<AssesmentMastEntity> assMstEntList = assesmentMstRepository.getPropDetailByPropNoWithoutActiveCond(propertyNo);
        return mapListFromEntitytoDto(assMstEntList);
    }

    @Override
    public List<Long> getObjectionIdListByPropNo(Long orgId, String propNo) {
        return objectionMastRepository.getObjectionIdListByRefNo(orgId, propNo);
    }

    @Override
    public HearingInspectionDto getHearingIdListByPropNo(Long orgId, Long objId) {
        HearingInspectionDto dto = null;
        HearingMasterEntity entity = hearingMasRepository.getHearingDetailByObjId(orgId, objId);
        if (entity != null) {
            dto = new HearingInspectionDto();
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    @Override
    public List<String> getUpdateDataEntryFlag(String propNo, Long orgId) {
        return assesmentMstRepository.fetchUpdateDataEntryFlag(propNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailFromMainAssByPropNoOrOldPropNoByFlatNo(Long orgId, String propertyNo,
            String oldPropertyNo, String logicalPropNo) {
        List<AssesmentMastEntity> assMstEntList = null;
        assMstEntList = assesmentMstRepository.getPropDetailFromMainAssByPropNoByFlatNo(orgId,
                propertyNo, logicalPropNo);
        if ((assMstEntList == null || assMstEntList.isEmpty())
                && (oldPropertyNo != null  &&  !oldPropertyNo.equals(MainetConstants.BLANK))) {
            assMstEntList = assesmentMstRepository.getPropDetailFromMainAssByOldPropNo(orgId,
                    oldPropertyNo);
        }
        return mapListFromEntitytoDto(assMstEntList);
    }

    @Override
    @Transactional
    public void updateMobileNoAndEmailInOwnerDti(String mobileNo, String emailId, Long ownerDtlId) {
        assesmentMstRepository.updatemobNoAndEmailIdInOwner(mobileNo, emailId, ownerDtlId);

    }

    @Override
    @Transactional
    public void updateMobileNoAndEmailInDetail(String mobileNo, String emailId, Long detailId) {
        assesmentMstRepository.updatemobNoAndEmailIdInDetail(mobileNo, emailId, detailId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstDto> getPropDetailFromAssByPropNoFlatNo(long orgId, String propertyNo,
            String flatNo) {
        List<ProvisionalAssesmentMstDto> propList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getPropDetailFromAssByPropNoFlatNo(orgId, propertyNo,
                flatNo);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                propList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return propList;
    }

    @Override
    @Transactional
    public ProvisionalAssesmentMstDto fetchLatestAssessmentByPropNoAndFlatNo(long orgId, String proertyNo,
            String flatNo) {
        ProvisionalAssesmentMstDto assMstDto = null;
        AssesmentMastEntity assMst = assesmentMstRepository.fetchPropertyByPropNoAndFlatNo(proertyNo, flatNo, orgId);
        if (assMst != null) {
            assMstDto = new ProvisionalAssesmentMstDto();
            BeanUtils.copyProperties(assMst, assMstDto);
            final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
            assMst.getAssesmentDetailEntityList().forEach(provDetEnt -> {
                ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                BeanUtils.copyProperties(provDetEnt, provDetDto);
                provDetEnt.getAssesmentFactorDetailEntityList().forEach(proAssFactEnt -> {
                    ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                    provAssFactDtoList.add(proAssFactDto);
                });
                provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                provAssDetDtoList.add(provDetDto);
            });
            assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
            if (!assMst.getAssesmentOwnerDetailEntityList().isEmpty()
                    && assMst.getAssesmentOwnerDetailEntityList() != null) {
                final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
                assMst.getAssesmentOwnerDetailEntityList().forEach(provOwnEnt -> {
                    ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                    BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
                    provAssOwnDtoList.add(provAssOwnDto);
                });
                assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
            }
        }
        return assMstDto;
    }

    @Override
    @Transactional
    public List<Long> fetchApplicationAgainstPropertyWithFlatNo(long orgId, String proertyNo, String flatNo) {
        return assesmentMstRepository.fetchApplicationAgainstPropertyWithFlatNo(orgId, proertyNo, flatNo);
    }

    @Transactional
    @Override
    public List<AssesmentOwnerDtlEntity> getOwnerDetailsByPropertyNo(Long orgId, String propertryNo) {
        return assesmentMstRepository.getOwnerDetailsByPropertyNo(orgId, propertryNo);
    }

    @Override
    public List<ProvisionalAssesmentMstDto> getAssMstListForProvisionalDemandbyPropNoList(Long orgId,
            List<String> propNoList) {
        List<ProvisionalAssesmentMstDto> assList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository
                .getAssMstListForProvisionalDemandByOrgIdAndPropNoList(orgId, propNoList);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                assList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return assList;
    }

    @Override
    @Transactional
    public ProvisionalAssesmentOwnerDtlDto fetchPropertyOwnerByMobileNo(String mobileNo) {
        ProvisionalAssesmentOwnerDtlDto ownerDtlDto = new ProvisionalAssesmentOwnerDtlDto();
        try {
            // List<String> propNoList = mainAssessmentOwnerRepository.fetchPropertyOwnerByMobileNo(mobileNo);
            List<String> propNoList = provisionalAssesmentOwnerRepository.fetchPropertyOwnerByMobileNo(mobileNo);
            if (CollectionUtils.isNotEmpty(propNoList)) {
                String propNo = propNoList.get(propNoList.size() - 1);
                // List<AssesmentOwnerDtlEntity> ownerDtlEntityList = mainAssessmentOwnerRepository
                // .fetchOwnerDetailListByPropNo(propNo);
                List<ProvisionalAssesmentOwnerDtlEntity> ownerDtlEntityList = provisionalAssesmentOwnerRepository
                        .fetchOwnerDetailListByPropNo(propNo);
                if (CollectionUtils.isNotEmpty(ownerDtlEntityList)) {
                    for (ProvisionalAssesmentOwnerDtlEntity ownerDtlEntity : ownerDtlEntityList) {
                        if (ownerDtlEntity != null
                                && MainetConstants.Property.PRIMARY_OWN.equals(ownerDtlEntity.getAssoOType())) {
                            // update OTP in owner table
                            final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
                            // int count = mainAssessmentOwnerRepository.updateOTPByProAssoId(ownerDtlEntity.getProAssoId(),
                            // Long.valueOf(otp), new Date());
                            int count = provisionalAssesmentOwnerRepository.updateOTPByProAssoId(ownerDtlEntity.getProAssoId(),
                                    Long.valueOf(otp), new Date());
                            if (count > 0) {
                                ownerDtlEntity.setAssoMobileNoOtp(Long.valueOf(otp));
                            }
                            BeanUtils.copyProperties(ownerDtlEntity, ownerDtlDto);
                        }
                    }
                }
                return ownerDtlDto;
            }
        } catch (Exception e) {
            throw new FrameworkException("error occurs while fetching property owner details by mobile number", e);
        }
        return null;
    }

    @Override
    public ProvisionalAssesmentOwnerDtlDto getOTPServiceForAssessment(String mobileNo) {
        ProvisionalAssesmentOwnerDtlDto ownerDtlDto = new ProvisionalAssesmentOwnerDtlDto();
        try {
            List<String> propNoList = provisionalAssesmentOwnerRepository.fetchPropertyOwnerByMobileNo(mobileNo);
            if (CollectionUtils.isNotEmpty(propNoList)) {
                String propNo = propNoList.get(propNoList.size() - 1);
                List<ProvisionalAssesmentOwnerDtlEntity> ownerDtlEntityList = provisionalAssesmentOwnerRepository
                        .fetchOwnerDetailListByPropNo(propNo);
                if (CollectionUtils.isNotEmpty(ownerDtlEntityList)) {
                    for (ProvisionalAssesmentOwnerDtlEntity ownerDtlEntity : ownerDtlEntityList) {
                        if (ownerDtlEntity != null
                                && MainetConstants.Property.PRIMARY_OWN.equals(ownerDtlEntity.getAssoOType())) {
                            if (ownerDtlEntity.getAssoMobileNoOtp() != null) {
                                ownerDtlDto.setAssoMobileNoOtp(ownerDtlEntity.getAssoMobileNoOtp());
                            }
                            if (ownerDtlEntity.getUpdatedDate() != null) {
                                ownerDtlDto.setUpdatedDate(ownerDtlEntity.getUpdatedDate());
                            }
                            return ownerDtlDto;
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new FrameworkException("error occurs while fetching otp details by mobile number", e);
        }
        return null;
    }

    @Override
    @Transactional
    public List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByOldPropNoNFlatNo(long orgId, String oldPropNo,
            String flatNo) {
        List<ProvisionalAssesmentMstDto> propList = new ArrayList<>(0);
        List<AssesmentMastEntity> assessmentMaster = assesmentMstRepository.getPropDetailFromAssByPropNoFlatNo(orgId,
                oldPropNo, flatNo);
        if (assessmentMaster != null) {
            assessmentMaster.forEach(mst -> {
                propList.add(mapAssessmentMasterToProvisionalDtoWithPriKey(mst));
            });
        }
        return propList;
    }

    @Override
    @Transactional
    public void updateAssessmentMstStatus(String propNo, String status, Long orgId) {
        assesmentMstRepository.updateAssessmentMasterStatus(propNo, status, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto getPropDetailByAssNoOrOldPropNoForBillRevise(long orgId, String assNo,
            String assOldpropno, String status, String logicalPropNo) {
        AssesmentMastEntity assessmentEntity = iAssessmentMastDao.fetchAssDetailAssNoOrOldpropno(orgId, assNo,
                assOldpropno, status, logicalPropNo);
        if (assessmentEntity != null) {
            // assessmentEntity.setFlag(null);
            return mapAssessmentMasterToProvisionalDtoForBillRevise(assessmentEntity);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDtoForBillRevise(
            AssesmentMastEntity assessmentMaster) {
        ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
        BeanUtils.copyProperties(assessmentMaster, assMstDto);
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        assessmentMaster.getAssesmentDetailEntityList().forEach(detEnt -> {
            ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
            final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
            BeanUtils.copyProperties(detEnt, provDetDto);
            provDetDto.setAssdUnitNo(detEnt.getAssdUnitNo());
            provDetDto.setPropNo(assessmentMaster.getAssNo());
            detEnt.getAssesmentFactorDetailEntityList().forEach(assFactEnt -> {
                ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(assFactEnt, proAssFactDto);
                provAssFactDtoList.add(proAssFactDto);
            });

            List<PropertyRoomDetailsDto> roomDetailsDtoList = new ArrayList<>();
            detEnt.getRoomDetailEntityList().forEach(room -> {
                PropertyRoomDetailsDto propertyRoomDetailsDto = new PropertyRoomDetailsDto();
                BeanUtils.copyProperties(room, propertyRoomDetailsDto);
                roomDetailsDtoList.add(propertyRoomDetailsDto);
            });
            provDetDto.setRoomDetailsDtoList(roomDetailsDtoList);
            provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
            provAssDetDtoList.add(provDetDto);
        });
        assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        if (!assessmentMaster.getAssesmentOwnerDetailEntityList().isEmpty()
                && assessmentMaster.getAssesmentOwnerDetailEntityList() != null) {
            final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
            assessmentMaster.getAssesmentOwnerDetailEntityList().forEach(ownEnt -> {
                ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                BeanUtils.copyProperties(ownEnt, provAssOwnDto);
                provAssOwnDtoList.add(provAssOwnDto);
            });
            assMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        }
        return assMstDto;
    }

    @Override
    public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
        return assesmentMstRepository.getPropNoByOldPropNo(oldPropNo, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public AssesmentMastHistEntity fetchAssessmentHistMasterByPropNoOrFlat(Long orgId, String propNo, String flatNo) {
        if (!StringUtils.isEmpty(flatNo)) {
            return assesmentMstRepository.fetchAssessmentHistMasterByPropNoNFlatNo(orgId, propNo, flatNo);
        } else {
            return assesmentMstRepository.fetchAssessmentHistMasterByPropNo(orgId, propNo);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyTransferOwnerDto> fetchAssOwnerHistByPropNoNApplId(Long orgId,
            Long apmApplicationId) {
        List<PropertyTransferOwnerDto> propTranOwnList = new ArrayList<>();
        List<AssesmentOwnerDtlHistEntity> ownerHistEntityList = assesmentMstRepository
                .fetchAssOwnerHistByPropNoNApplId(orgId, apmApplicationId);
        ownerHistEntityList.forEach(owner -> {
            PropertyTransferOwnerDto propertyTransferOwnerDto = new PropertyTransferOwnerDto();
            BeanUtils.copyProperties(owner, propertyTransferOwnerDto);
            propertyTransferOwnerDto.setOtype(owner.getAssoOType());
            propertyTransferOwnerDto.setOwnerName(owner.getAssoOwnerName());
            propertyTransferOwnerDto.setAssoOwnerNameReg(owner.getAssoOwnerNameReg());
            propertyTransferOwnerDto.setGuardianName(owner.getAssoGuardianName());
            propertyTransferOwnerDto.setMobileno(owner.getAssoMobileno());
            propertyTransferOwnerDto.setAddharno(owner.getAssoAddharno());
            propertyTransferOwnerDto.setPanno(owner.getAssoPanno());
            propTranOwnList.add(propertyTransferOwnerDto);
        });
        return propTranOwnList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyTransferOwnerDto> fetchAssDetailHistByApplicationId(Long orgId, String flatNo,
            Long apmApplicationId) {
        List<PropertyTransferOwnerDto> propTranOwnList = new ArrayList<>();
        List<AssesmentDetailHistEntity> assDetailHistEntityList = assesmentMstRepository
                .fetchAssDetailHistByApplicationId(orgId, apmApplicationId);
        // Filter selected flat number from list
        assDetailHistEntityList = assDetailHistEntityList.stream().filter(detail -> detail.getFlatNo().equals(flatNo))
                .collect(Collectors.toList());
        assDetailHistEntityList.forEach(detail -> {
            PropertyTransferOwnerDto propertyTransferOwnerDto = new PropertyTransferOwnerDto();
            propertyTransferOwnerDto.setOwnerName(detail.getOccupierName());
            propertyTransferOwnerDto.setAssoOwnerNameReg(detail.getOccupierNameReg());
            propertyTransferOwnerDto.setMobileno(detail.getOccupierMobNo());
            propertyTransferOwnerDto.seteMail(detail.getOccupierEmail());
            propTranOwnList.add(propertyTransferOwnerDto);
        });
        return propTranOwnList;
    }

    @Override
    @Transactional
    public int updateMobileAndEmailForMobile(String mobileNo, String email, Long ownerDtlId, Date updatedDate) {
        return provisionalAssesmentOwnerRepository.updateMobileAndEmailForMobile(mobileNo, email, ownerDtlId, updatedDate);
    }

    @Override
    @Transactional
    public int updateMobileAndEmailForMobileMainTable(String mobileNo, String email, Long ownerDtlId, Date updatedDate) {
        return mainAssessmentOwnerRepository.updateMobileAndEmailForMobile(mobileNo, email, ownerDtlId, updatedDate);
    }
    
	@Override
	@Transactional
	public List<AssesmentOwnerDtlEntity> fetchOwnerDetailListByProAssId(AssesmentMastEntity assesmentMastEntity,
			Long orgId) {
		return mainAssessmentOwnerRepository.fetchOwnerDetailListByProAssId(assesmentMastEntity, orgId);
	}

	@Override
	public List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByParentPropNo(String parentPropNo) {
        List<AssesmentMastEntity> assMstEntList = assesmentMstRepository.getassesmentMasterListByParentPropNo(parentPropNo);
        return mapListFromEntitytoDto(assMstEntList);
    
	}

	@Override
	public ProvisionalAssesmentMstDto getCurrentYearAssesment(Long finYearId, String propNo, Long applicationId) {
		
		AssesmentMastEntity assessmentEntity = new AssesmentMastEntity();
		AssesmentMastHistEntity assessmentHistEntity = assesmentMstRepository.getCurrentYearAssesmentHistByFinIdAndApplId(finYearId, propNo,applicationId);
		if(assessmentHistEntity != null) {
			List<AssesmentDetailHistEntity> detailHistoryList = assesmentMstRepository.getAssesmentDetailHistByMnassId(assessmentHistEntity.getMnAssHisId());
			List<AssesmentOwnerDtlHistEntity> ownerHistoryList = assesmentMstRepository.getAssesmentOwnerHistByMnassId(assessmentHistEntity.getMnAssHisId());
			
			BeanUtils.copyProperties(assessmentHistEntity, assessmentEntity);
			assessmentEntity.setProAssId(assessmentHistEntity.getMnAssId());
			if(CollectionUtils.isNotEmpty(detailHistoryList)) {
				assessmentEntity.setAssesmentDetailEntityList(new ArrayList<AssesmentDetailEntity>()); 
				detailHistoryList.forEach(detHist ->{
					AssesmentDetailEntity det = new AssesmentDetailEntity();
					det.setAssesmentFactorDetailEntityList(new ArrayList<AssesmentFactorDetailEntity>());
					det.setRoomDetailEntityList(new ArrayList<AssessmentRoomDetailEntity>());
					BeanUtils.copyProperties(detHist, det);
					assessmentEntity.getAssesmentDetailEntityList().add(det);
				});
			}
			if(CollectionUtils.isNotEmpty(ownerHistoryList)) {
				assessmentEntity.setAssesmentOwnerDetailEntityList(new ArrayList<AssesmentOwnerDtlEntity>());
				ownerHistoryList.forEach(ownerHist ->{
					AssesmentOwnerDtlEntity owner = new AssesmentOwnerDtlEntity();
					BeanUtils.copyProperties(ownerHist, owner);
					assessmentEntity.getAssesmentOwnerDetailEntityList().add(owner);
				});
			}
			if (assessmentEntity != null) {
	            return mapAssessmentMasterToProvisionalDtoWithPriKey(assessmentEntity);
	        }
		}
        return null;
    
	}
	 
	 @Override
		public List<ProvisionalAssesmentMstDto> getAllYearAssesment(Long finYearId, String propNo) {
		 List<ProvisionalAssesmentMstDto> assList = new ArrayList<ProvisionalAssesmentMstDto>();
			List<AssesmentMastEntity> allAssesment = assesmentMstRepository.getAllAssesment(propNo);
			
	        if (CollectionUtils.isNotEmpty(allAssesment)) {
	        	allAssesment.forEach(assessmentEntity ->{
	        		ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDto = mapAssessmentMasterToProvisionalDtoWithPriKey(assessmentEntity);
	        		assList.add(mapAssessmentMasterToProvisionalDto);
	        	});
	        	return assList;
	        }
	        return null;
	    
		}
	 
		@Override
		@Transactional
		public List<Object[]> getAssessedProperties(Long orgId, String dateSet) {
			return assesmentMstRepository.getAssessedProperties(orgId, dateSet);
		}
		
		@Override
		@Transactional
		public List<Object[]> getPropertiesRegisteredList(Long orgId) {
			return assesmentMstRepository.getPropertiesRegisteredList(orgId);
		}
		
		@Override
		@Transactional
		public List<Object[]> getTransactionsProp(Long orgId, Long deptId, String dateSet) {
			return assesmentMstRepository.getTransactionsProp(orgId, deptId, dateSet);
		}
		
		@Override
		@Transactional
		public List<Object[]> getPaymentModeWiseColln(Long orgId, Long deptId, String dateSet) {
			return assesmentMstRepository.getPaymentModeWiseColln(orgId, deptId, dateSet);
		}
		 
}