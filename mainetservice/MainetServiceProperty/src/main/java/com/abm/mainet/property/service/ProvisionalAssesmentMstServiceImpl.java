package com.abm.mainet.property.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.BarcodeGenerator;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IProvisionalAssessmentMstDao;
import com.abm.mainet.property.domain.ProAssDetailHisEntity;
import com.abm.mainet.property.domain.ProAssFactlHisEntity;
import com.abm.mainet.property.domain.ProAssMstHisEntity;
import com.abm.mainet.property.domain.ProAssOwnerHisEntity;
import com.abm.mainet.property.domain.ProAssRoomHisEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentFactorDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.ProvisionalAssesmentDetRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentFactRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentOwnerRepository;
import com.abm.mainet.property.repository.ProvisionalAssessOwnerRepository;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class ProvisionalAssesmentMstServiceImpl implements IProvisionalAssesmentMstService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionalAssesmentMstServiceImpl.class);

    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;

    @Autowired
    private ProvisionalAssessOwnerRepository provisionalAssessOwnerRepository;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private PropertyService propertyService;

    @Resource
    private AuditService auditService;

    @Autowired
    private IProvisionalAssessmentMstDao iProvisionalAssessmentMstDao;

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private ProvisionalAssesmentDetRepository provisionalAssesmentDetRepository;

    @Autowired
    private ProvisionalAssesmentFactRepository provisionalAssesmentFactRepository;

    @Autowired
    private ProvisionalAssesmentOwnerRepository provisionalAssesmentOwnerRepository;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private IProvisionalBillService provisionalBillService;

    @Autowired
    private IReceiptEntryService receiptEntryService;

    @Autowired
    private SelfAssessmentService selfAssessmentService;
    
    @Autowired
    private IOrganisationService organisationService;

    /**
     * First New Assessment of Property Saving Property Details
     */

    @Transactional
    @Override
    public Map<Long, Long> saveProvisionalAssessment(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId, List<Long> finYearList, Long applicationNo) {
        Map<Long, Long> assIdMap = new HashMap<>();    
        Organisation org = organisationService.getOrganisationById(orgId);
        boolean skdclCheck = Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SKDCL);
        final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
        Long currFinYaer = iFinancialYear.getFinanceYearId(new Date());
        String propNoExist = provisionalAssesmentMstDto.getAssNo();
        if (StringUtils.isEmpty(propNoExist)) {
            propNoExist = propertyService.getPropertyNo(orgId, provisionalAssesmentMstDto);
            getBarcode(provisionalAssesmentMstDto, propNoExist);
        }
        List<ProvisionalAssesmentMstEntity> proEntList = new ArrayList<>();
        final String propNo = propNoExist;
        finYearList.forEach(finYear -> {
            final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
            provisionalAssesmentMstDto.setAssNo(propNo);
            provisionalAssesmentMstDto.setOrgId(orgId);
            provisionalAssesmentMstDto.setCreatedBy(empId);
            provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
            provisionalAssesmentMstDto.setLgIpMac(ipAddress);
            if(provisionalAssesmentMstDto.getUpdatedDate() != null) {
            	provisionalAssesmentMstDto.setUpdatedDate(new Date());
            }else {
            	provisionalAssesmentMstDto.setCreatedDate(new Date());
            }
            provisionalAssesmentMstDto.setFaYearId(finYear);//
            provisionalAssesmentMstDto.setAssActive(MainetConstants.STATUS.ACTIVE);
            if (provisionalAssesmentMstDto.getApmDraftMode() != null
                    && provisionalAssesmentMstDto.getApmDraftMode().equals("D")) {
                provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.SAVE_AS_DRAFT);
            } else {
                provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.ASS_FILED);
            }
            provisionalAssesmentMstDto.setAssAutStatus(MainetConstants.Property.AuthStatus.NON_AUTH);
            BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);
                        
            // Assessment Detail
            final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
            	
                if (provDet.getFaYearId().toString().equals(finYear.toString())) {
                    ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
                    BeanUtils.copyProperties(provDet, provAsseDet);
					if (skdclCheck && StringUtils.isEmpty(provDet.getLegal())) {
						provAsseDet.setLegal(MainetConstants.FlagN);
					}
                    provAsseDet.setTbAsAssesmentMast(provAssetMst);
                    provAsseDet.setOrgId(orgId);
                    provAsseDet.setCreatedBy(empId);
                    provAsseDet.setLgIpMac(ipAddress);
                    provAsseDet.setCreatedDate(new Date());
                    provAsseDet.setAssdAssesmentDate(new Date());
                    provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                    // #150274
                    if (skdclCheck) {
                    	provAsseDet.setConstructPermissionDate(provDet.getConstructPermissionDate());
                    }
                    // Factor
                    final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
                    provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                        ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                        BeanUtils.copyProperties(provfact, provAssFact);
                        provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                        provAssFact.setTbAsAssesmentMast(provAssetMst);
                        // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                        provAssFact.setCreatedBy(empId);
                        provAssFact.setCreatedDate(new Date());
                        provAssFact.setLgIpMac(ipAddress);
                        provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                        provAssFact.setOrgId(orgId);
                        provAsseFactList.add(provAssFact);
                    });
                    provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);
                    // 97207 By arun
                    final List<ProvisionalAssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
                    provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
                        ProvisionalAssessmentRoomDetailEntity roomEntity = new ProvisionalAssessmentRoomDetailEntity();
                        BeanUtils.copyProperties(roomDetails, roomEntity);
                        roomEntity.setProAssDetEntity(provAsseDet);
                        roomEntity.setOrgId(orgId);
                        roomEntity.setCreatedBy(empId);
                        roomEntity.setCreatedDate(new Date());
                        roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                        proAssessmentRoomDetailEntity.add(roomEntity);
                    });
                    provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);
                    ///
                    provAsseDetList.add(provAsseDet);
                }
            });
            provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

            // Assessment owner
            if (currFinYaer.toString().equals(finYear.toString())) { // for mutilple year assessment owner
                                                                     // details are saved for
                // current year assessment
                final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
                provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
                    ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                    if (StringUtils.isEmpty(provOwndto.getAssoGuardianName())) {
                        provAsseOwnerEnt.setAssoGuardianName(MainetConstants.BLANK);
                    }
                    provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
                    provAsseOwnerEnt.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
                    provAsseOwnerEnt.setOrgId(orgId);
                    provAsseOwnerEnt.setCreatedBy(empId);
                    provAsseOwnerEnt.setLgIpMac(ipAddress);
                    provAsseOwnerEnt.setCreatedDate(new Date());
                    provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                    provAsseOwnerEnt.setApmApplicationId(applicationNo);
                    provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
                    provAsseOwnerEnt.setAssNo(provisionalAssesmentMstDto.getAssNo());
                    provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                    provAsseOwnEntList.add(provAsseOwnerEnt);

                });
                provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
            }
            proEntList.add(provAssetMst);
        });
        List<ProvisionalAssesmentMstEntity> proEntityList =provisionalAssesmentMstRepository.save(proEntList);
        provHistAtAdd(proEntityList);
        proEntList.forEach(proEnt -> {
            assIdMap.put(proEnt.getFaYearId(), proEnt.getProAssId());
        });
        return assIdMap;
    }
    
	private void provHistAtAdd(List<ProvisionalAssesmentMstEntity> provEntityList) {
		if(null!=provEntityList) {
		provEntityList.forEach(provEntity -> {
			ProAssMstHisEntity proAssMstHisEntity = new ProAssMstHisEntity();
			proAssMstHisEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);

			try {
				auditService.createHistory(provEntity, proAssMstHisEntity);
			} catch (Exception ex) {
				throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + provEntity, ex);
			}
			if (null != provEntity.getProvisionalAssesmentDetailList()) {
			provEntity.getProvisionalAssesmentDetailList().forEach(det -> {
					ProAssDetailHisEntity proAssDetHisEntity = new ProAssDetailHisEntity();
					proAssDetHisEntity.setAssId(provEntity.getProAssId());
					proAssDetHisEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);

					try {
						auditService.createHistory(det, proAssDetHisEntity);
					} catch (Exception ex) {
						throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + det, ex);
					}
					det.getProvisionalAssesmentFactorDtlList().forEach(fact -> {
						ProAssFactlHisEntity proAssFactlHisEntity = new ProAssFactlHisEntity();
						proAssFactlHisEntity.setProAssdId(det.getProAssdId());
						proAssFactlHisEntity.setProAssId(provEntity.getProAssId());
						proAssFactlHisEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);

						try {
							auditService.createHistory(fact, proAssFactlHisEntity);
						} catch (Exception ex) {
							throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + fact, ex);
						}
					});
					if (null != det.getRoomDetailEntityList()) {
						det.getRoomDetailEntityList().forEach(room -> {
							ProAssRoomHisEntity proAssRoomHisEntity = new ProAssRoomHisEntity();
							proAssRoomHisEntity.setProAssdId(det.getProAssdId());
							proAssRoomHisEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);

							try {
								auditService.createHistory(room, proAssRoomHisEntity);
							} catch (Exception ex) {
								throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + room,
										ex);
							}
						});
					}
			});
		}
			if (null != provEntity.getProvisionalAssesmentOwnerDtlList()) {
				provEntity.getProvisionalAssesmentOwnerDtlList().forEach(owner -> {
					ProAssOwnerHisEntity proAssOwnHisEntity = new ProAssOwnerHisEntity();
					proAssOwnHisEntity.setProAssId(provEntity.getProAssId());
					proAssOwnHisEntity.sethStatus(MainetConstants.Transaction.Mode.ADD);

					try {
						auditService.createHistory(owner, proAssOwnHisEntity);
					} catch (Exception ex) {
						LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + owner, ex);
					}
				});
			}
		});
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<Long, Long> saveProvisionalAssessmentWithFlatNo(ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
			Long orgId, Long empId, List<Long> finYearList, Map<String, Long> flatWiseAppIdmap) {

		Map<Long, Long> assIdMap = new HashMap<>();
		final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
		Long currFinYear = iFinancialYear.getFinanceYearId(new Date());
		String propNoExist = provisionalAssesmentMstDto.getAssNo();
		if (StringUtils.isEmpty(propNoExist)) {
			propNoExist = propertyService.getPropertyNo(orgId, provisionalAssesmentMstDto);
			getBarcode(provisionalAssesmentMstDto, propNoExist);
		}
		List<ProvisionalAssesmentMstEntity> proEntList = new ArrayList<>();
		final String propNo = propNoExist;
		provisionalAssesmentMstDto.setAssNo(propNo);
		provisionalAssesmentMstDto.setOrgId(orgId);
		provisionalAssesmentMstDto.setCreatedBy(empId);
		provisionalAssesmentMstDto.setLgIpMac(ipAddress);
		provisionalAssesmentMstDto.setCreatedDate(new Date());		
		provisionalAssesmentMstDto.setAssActive(MainetConstants.STATUS.ACTIVE);
		if (provisionalAssesmentMstDto.getApmDraftMode() != null
				&& provisionalAssesmentMstDto.getApmDraftMode().equals(MainetConstants.FlagD)) {
			provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.SAVE_AS_DRAFT);
		} else {
			provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.ASS_FILED);
		}
		provisionalAssesmentMstDto.setAssAutStatus(MainetConstants.Property.AuthStatus.NON_AUTH);
		
		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
			
			// prepare master
			final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();	
			
			Long applicationNo = flatWiseAppIdmap.get(provDet.getFlatNo());
			provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
			provisionalAssesmentMstDto.setFlatNo(provDet.getFlatNo());
			provisionalAssesmentMstDto
					.setLogicalPropNo(propNo + MainetConstants.operator.UNDER_SCORE + provDet.getFlatNo());
			provisionalAssesmentMstDto.setFaYearId(provDet.getFaYearId());// changed
			BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);

			// prepare detail			
			List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
			ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
			BeanUtils.copyProperties(provDet, provAsseDet);
			provDet.setApmApplicationId(applicationNo);
			provAsseDet.setTbAsAssesmentMast(provAssetMst);
			provAsseDet.setOrgId(orgId);
			provAsseDet.setCreatedBy(empId);
			provAsseDet.setLgIpMac(ipAddress);
			provAsseDet.setCreatedDate(new Date());
			provAsseDet.setAssdAssesmentDate(new Date());
			provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
			// #150274
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
            	provAsseDet.setConstructPermissionDate(provDet.getConstructPermissionDate());
            }

			final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();// Factor start
			provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
				ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
				BeanUtils.copyProperties(provfact, provAssFact);
				provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
				provAssFact.setTbAsAssesmentMast(provAssetMst);
				// provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
				provAssFact.setCreatedBy(empId);
				provAssFact.setCreatedDate(new Date());
				provAssFact.setLgIpMac(ipAddress);
				provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
				provAssFact.setOrgId(orgId);
				provAsseFactList.add(provAssFact);
			});
			provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);// Factor end
			
			// Room start
			final List<ProvisionalAssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
			provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
				ProvisionalAssessmentRoomDetailEntity roomEntity = new ProvisionalAssessmentRoomDetailEntity();
				BeanUtils.copyProperties(roomDetails, roomEntity);
				roomEntity.setProAssDetEntity(provAsseDet);
				roomEntity.setOrgId(orgId);
				roomEntity.setCreatedBy(empId);
				roomEntity.setCreatedDate(new Date());
				roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
				proAssessmentRoomDetailEntity.add(roomEntity);
			});
			provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);// Room end
			provAsseDetList.add(provAsseDet);
			provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

			// Owner start
			if (currFinYear.toString().equals(provDet.getFaYearId().toString())) {
				final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
				provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
					ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
					BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
					if (StringUtils.isEmpty(provOwndto.getAssoGuardianName())) {
						provAsseOwnerEnt.setAssoGuardianName(MainetConstants.BLANK);
					}
					provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
					provAsseOwnerEnt.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
					provAsseOwnerEnt.setOrgId(orgId);
					provAsseOwnerEnt.setCreatedBy(empId);
					provAsseOwnerEnt.setLgIpMac(ipAddress);
					provAsseOwnerEnt.setCreatedDate(new Date());
					provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
					provAsseOwnerEnt.setApmApplicationId(applicationNo);
					provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
					provAsseOwnerEnt.setAssNo(provisionalAssesmentMstDto.getAssNo());
					provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
					provAsseOwnEntList.add(provAsseOwnerEnt);
				});
				provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);// owner end
			}
			provisionalAssesmentMstRepository.save(provAssetMst);
			proEntList.add(provAssetMst);
		});
		provHistAtAdd(proEntList);
		proEntList.forEach(proEnt -> {
			assIdMap.put(proEnt.getFaYearId(), proEnt.getProAssId());
		});
		return assIdMap;
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

    @Transactional
    @Override
    public ProvisionalAssesmentMstEntity saveProvisionalAssessmentForDataEntry(
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            Long orgId, Long empId, Long finYear, Long applicationNo) {
        final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
        String propNoExist = provisionalAssesmentMstDto.getAssNo();
        if (StringUtils.isEmpty(propNoExist)) {
            propNoExist = propertyService.getPropertyNo(orgId, provisionalAssesmentMstDto);
            getBarcode(provisionalAssesmentMstDto, propNoExist);
        }
        final String propNo = propNoExist;

        final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
        provisionalAssesmentMstDto.setAssNo(propNo);
        provisionalAssesmentMstDto.setOrgId(orgId);
        provisionalAssesmentMstDto.setCreatedBy(empId);
        provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
        provisionalAssesmentMstDto.setLgIpMac(ipAddress);
        provisionalAssesmentMstDto.setCreatedDate(new Date());
        provisionalAssesmentMstDto.setFaYearId(finYear);//
        provisionalAssesmentMstDto.setAssActive(MainetConstants.STATUS.ACTIVE);
        provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.ASS_FILED);
        provisionalAssesmentMstDto.setAssAutStatus(MainetConstants.Property.AuthStatus.NON_AUTH);
        BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);

        // Assessment Detail
        final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
            if (provDet.getFaYearId().toString().equals(finYear.toString())) {
                ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
                BeanUtils.copyProperties(provDet, provAsseDet);
                provAsseDet.setTbAsAssesmentMast(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                // Factor
                final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
                provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                    ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                    BeanUtils.copyProperties(provfact, provAssFact);
                    provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                    provAssFact.setTbAsAssesmentMast(provAssetMst);
                    // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setOrgId(orgId);
                    provAsseFactList.add(provAssFact);
                });
                provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);
                provAsseDetList.add(provAsseDet);
            }
        });
        provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

        // Assessment owner
        // for mutilple year assessment owner details are saved for
        // current year assessment
        final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
            ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
            provAsseOwnerEnt.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
            provAsseOwnerEnt.setOrgId(orgId);
            provAsseOwnerEnt.setCreatedBy(empId);
            provAsseOwnerEnt.setLgIpMac(ipAddress);
            provAsseOwnerEnt.setCreatedDate(new Date());
            provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
            provAsseOwnerEnt.setApmApplicationId(applicationNo);
            provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
            provAsseOwnerEnt.setAssNo(provisionalAssesmentMstDto.getAssNo());
            provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
            provAsseOwnEntList.add(provAsseOwnerEnt);

        });
        provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
        List<ProvisionalAssesmentMstEntity> proEntityList = new ArrayList<>();
        ProvisionalAssesmentMstEntity proEntity= provisionalAssesmentMstRepository.save(provAssetMst);
        proEntityList.add(proEntity);
        provHistAtAdd(proEntityList);
        return provAssetMst;
    }

    @Transactional
    @Override
    public Map<Long, Long> updateProvisionalAssessment(ProvisionalAssesmentMstDto provAssMstDto,
            List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId,
            Long empId, String authFlag, List<Long> finYearList, List<ProvisionalAssesmentMstEntity> provAssEntList) {
        final String ipAddress = provAssMstDto.getLgIpMac();
        Map<Long, Long> assIdMap = new HashMap<>();

        for (ProvisionalAssesmentMstDto provAssDto : provAssMstDtoList) {
            ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
            BeanUtils.copyProperties(provAssMstDto, provAssetMst);
            provAssetMst.setProAssId(provAssDto.getProAssId());
            provAssetMst.setFaYearId(provAssDto.getFaYearId());
            provAssetMst.setUpdatedBy(empId);
            provAssetMst.setLgIpMacUpd(ipAddress);
            provAssetMst.setUpdatedDate(new Date());
            provAssetMst.setAssAutStatus(authFlag);
            if (provAssetMst.getAssStatus().equals(MainetConstants.Property.AssStatus.SAVE_AS_DRAFT)) {
                provAssetMst.setAssStatus(MainetConstants.Property.AssStatus.ASS_FILED);
            }
            final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            for (ProvisionalAssesmentDetailDto provDet : provAssMstDto.getProvisionalAssesmentDetailDtoList()) {
                if (provDet.getFaYearId().toString().equals(provAssDto.getFaYearId().toString())) {
                    ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
                    BeanUtils.copyProperties(provDet, provAsseDet);
                    if (provDet.getProAssdId() == 0) {
                        provAsseDet.setCreatedBy(empId);
                        provAsseDet.setLgIpMac(ipAddress);
                        provAsseDet.setCreatedDate(new Date());
                        provAsseDet.setTbAsAssesmentMast(provAssetMst);
                        provAsseDet.setOrgId(orgId);
                        provAsseDet.setAssdAssesmentDate(new Date());
                        provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                    } else {
                        provAsseDet.setUpdatedBy(empId);
                        provAsseDet.setLgIpMacUpd(ipAddress);
                        provAsseDet.setUpdatedDate(new Date());
                    }
                    provAsseDet.setTbAsAssesmentMast(provAssetMst);
                    final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
                    for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                        ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                        BeanUtils.copyProperties(provfact, provAssFact);
                        if (provfact.getProAssfId() == 0) {
                            provAssFact.setCreatedBy(empId);
                            provAssFact.setCreatedDate(new Date());
                            provAssFact.setLgIpMac(ipAddress);
                            provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                            provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                            provAssFact.setTbAsAssesmentMast(provAssetMst);
                            // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                            provAssFact.setOrgId(orgId);
                        } else {
                            provAssFact.setUpdatedBy(empId);
                            provAssFact.setUpdatedDate(new Date());
                            provAssFact.setLgIpMacUpd(ipAddress);
                            provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                            provAssFact.setTbAsAssesmentMast(provAssetMst);
                        }
                        provAsseFactList.add(provAssFact);
                    }
                    provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);
                    //Room details
                    final List<ProvisionalAssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
                    provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
                        ProvisionalAssessmentRoomDetailEntity roomEntity = new ProvisionalAssessmentRoomDetailEntity();
                        BeanUtils.copyProperties(roomDetails, roomEntity);
                        if(roomDetails.getProRoomId() == 0) {
                        	roomEntity.setOrgId(orgId);
                            roomEntity.setCreatedBy(empId);
                            roomEntity.setCreatedDate(new Date());
                            roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                        }
                        roomEntity.setProAssDetEntity(provAsseDet);
                        
                        proAssessmentRoomDetailEntity.add(roomEntity);
                    });
                    provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);
                    //end
                    provAsseDetList.add(provAsseDet);
                }
            }
            provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);
            if (provAssMstDto.getProAssId() == provAssDto.getProAssId()) {  // to update or add owner into latest assment only
                final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
                for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                    ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
                    BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                    provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
                    if (provOwndto.getProAssoId() == 0) {
                        provAsseOwnerEnt.setCreatedBy(empId);
                        provAsseOwnerEnt.setLgIpMac(ipAddress);
                        provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                        provAsseOwnerEnt.setCreatedDate(new Date());
                        provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                        provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
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
                provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
            }
            List<ProvisionalAssesmentMstEntity> proEntityList = new ArrayList<>();
            ProvisionalAssesmentMstEntity provAssEntTemp = provisionalAssesmentMstRepository.save(provAssetMst);
            proEntityList.add(provAssEntTemp);
            provHistAtAdd(proEntityList);
            if (provAssEntList != null) {
                provAssEntList.add(provAssEntTemp);
            }
            assIdMap.put(provAssetMst.getFaYearId(), provAssetMst.getProAssId());
        }

        return assIdMap;
    }

    @Transactional
    @Override
    public void updateProvisionalAssessmentForSingleAssessment(ProvisionalAssesmentMstDto provAssDto, Long orgId,
            Long empId, String ipAddress) {

        ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
        BeanUtils.copyProperties(provAssDto, provAssetMst);
        Long finYear = provAssDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
        provAssetMst.setUpdatedBy(empId);
        provAssetMst.setLgIpMacUpd(ipAddress);
        provAssetMst.setUpdatedDate(new Date());
        provAssetMst.setFaYearId(finYear);
        final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
        for (ProvisionalAssesmentDetailDto provDet : provAssDto.getProvisionalAssesmentDetailDtoList()) {
            ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
            BeanUtils.copyProperties(provDet, provAsseDet);
            if (provDet.getProAssdId() == 0) {
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setTbAsAssesmentMast(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
            } else {
                provAsseDet.setUpdatedBy(empId);
                provAsseDet.setLgIpMacUpd(ipAddress);
                provAsseDet.setUpdatedDate(new Date());
            }
            provAsseDet.setTbAsAssesmentMast(provAssetMst);
            final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
            for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                BeanUtils.copyProperties(provfact, provAssFact);
                provAssFact.setOrgId(orgId);
                if (provfact.getProAssfId() == 0) {
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                    provAssFact.setTbAsAssesmentMast(provAssetMst);
                    // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                } else {
                    provAssFact.setUpdatedBy(empId);
                    provAssFact.setUpdatedDate(new Date());
                    provAssFact.setLgIpMacUpd(ipAddress);
                    provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                    provAssFact.setTbAsAssesmentMast(provAssetMst);
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

                }
                provAsseFactList.add(provAssFact);
            }
            provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);
            provAsseDetList.add(provAsseDet);
        }
        provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);
        final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssDto.getProvisionalAssesmentOwnerDtlDtoList()) {
            ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
            if (provOwndto.getProAssoId() == 0) {
                provAsseOwnerEnt.setCreatedBy(empId);
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setAssNo(provAssDto.getAssNo());
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
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
        provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
        List<ProvisionalAssesmentMstEntity> proEntityList = new ArrayList<>();
        ProvisionalAssesmentMstEntity proEntity= provisionalAssesmentMstRepository.save(provAssetMst);
        proEntityList.add(proEntity);
        provHistAtAdd(proEntityList);
    }

    @Transactional
    @Override
    public ProvisionalAssesmentMstDto getProvAssDtoByAppId(Long applicationId, Long orgId) {
        List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
                .getProvisionalAssessmentMstByApplicationId(orgId, applicationId);
        return getProvAssesmentMstDtoByEntity(provAssMstEntList);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstEntity> getProvisionalAssesmentMstListByAppId(Long applicationId, Long orgId) {
        return provisionalAssesmentMstRepository
                .getProvisionalAssessmentMstByApplicationId(orgId, applicationId);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstDto> getAssesmentMstDtoListByAppId(Long applicationId, Long orgId) {
        List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
                .getProvisionalAssessmentMstByApplicationId(orgId, applicationId);
        return mapListFromEntitytoDto(provAssMstEntList);
    }

    private List<ProvisionalAssesmentMstDto> mapListFromEntitytoDto(List<ProvisionalAssesmentMstEntity> provAssMstEntList) {
        List<ProvisionalAssesmentMstDto> assMstDtoList = new ArrayList<>();
        if (provAssMstEntList != null && !provAssMstEntList.isEmpty()) {
            provAssMstEntList.forEach(provAssMast -> {
                ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
                BeanUtils.copyProperties(provAssMast, assMstDto);
                final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
                provAssMast.getProvisionalAssesmentDetailList().forEach(provDetEnt -> {
                    ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                    final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                    BeanUtils.copyProperties(provDetEnt, provDetDto);
                    if (provDetEnt.getFirstAssesmentDate() != null) {
                        provDetDto.setFirstAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                                .format(provDetEnt.getFirstAssesmentDate()));
                    }
                    provDetEnt.getProvisionalAssesmentFactorDtlList().forEach(proAssFactEnt -> {
                        ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                        BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                        provAssFactDtoList.add(proAssFactDto);
                    });
                    provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);

                    // 97207 By arun
                    final List<PropertyRoomDetailsDto> propertyRoomDetailsDtoList = new ArrayList<>();
                    provDetEnt.getRoomDetailEntityList().forEach(room -> {
                        PropertyRoomDetailsDto propertyRoomDetailsDto = new PropertyRoomDetailsDto();
                        BeanUtils.copyProperties(room, propertyRoomDetailsDto);
                        propertyRoomDetailsDtoList.add(propertyRoomDetailsDto);
                    });
                    provDetDto.setRoomDetailsDtoList(propertyRoomDetailsDtoList);
                    //
                    provAssDetDtoList.add(provDetDto);
                });
                assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
                if (!provAssMast.getProvisionalAssesmentOwnerDtlList().isEmpty()
                        && provAssMast.getProvisionalAssesmentOwnerDtlList() != null) {
                    final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
                    provAssMast.getProvisionalAssesmentOwnerDtlList().forEach(provOwnEnt -> {
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

    @Transactional
    @Override
    public ProvisionalAssesmentMstDto getProvAssesmentMstDtoByEntity(List<ProvisionalAssesmentMstEntity> provAssEntList) {
        ProvisionalAssesmentMstEntity proAssMstEnt = provAssEntList.get(provAssEntList.size() - 1);
        ProvisionalAssesmentMstDto provAssMstDto = new ProvisionalAssesmentMstDto();
        BeanUtils.copyProperties(proAssMstEnt, provAssMstDto);
        // Detail and Factor
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();

        provAssEntList.forEach(provAssMast -> {
            provAssMstDto.addFinYearList(provAssMast.getFaYearId());
            provAssMast.getProvisionalAssesmentDetailList().forEach(provDetEnt -> {
                ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                BeanUtils.copyProperties(provDetEnt, provDetDto);
                provDetEnt.getProvisionalAssesmentFactorDtlList().forEach(proAssFactEnt -> {
                    ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                    provAssFactDtoList.add(proAssFactDto);
                });
                provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                provAssDetDtoList.add(provDetDto);
            });
        });

        proAssMstEnt.getProvisionalAssesmentOwnerDtlList().forEach(provOwnEnt -> {
            ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
            BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
            provAssOwnDtoList.add(provAssOwnDto);
        });
        provAssMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        provAssMstDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        return provAssMstDto;
    }

    Long getProAssIdByOldPropertyNo(Long orgId, String oldPropertyNo) {
        return provisionalAssesmentMstRepository.getProAssIdByOldPropertyNo(orgId, oldPropertyNo);
    }

    @Override
    public List<ProvisionalAssesmentMstEntity> getProAssMasterByApplicationId(Long orgId, Long applicationId) {
        return provisionalAssesmentMstRepository.getProvisionalAssessmentMstByApplicationId(orgId, applicationId);
    }

    @Override
    public List<ProvisionalAssesmentMstDto> getPropDetailFromProvAssByPropNoOrOldPropNo(Long orgId, String propertyNo,
            String oldPropertyNo) {
        List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
                .getPropDetailFromProvAssByPropNoOrOldPropNo(orgId, propertyNo);
        return mapListFromEntitytoDto(provAssMstEntList);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNo(Long orgId, String propertyNo,
            String activeSatus) {
        return provisionalAssesmentMstRepository.getPropDetailFromProvAssByPropNo(orgId, propertyNo, activeSatus);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNo(Long orgId, String propertyNo,
            String activeSatus) {
        List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
                .getPropDetailFromProvAssByPropNo(orgId, propertyNo, activeSatus);
        return mapListFromEntitytoDto(provAssMstEntList);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstDto> getPropDetailByPropNoOnly(String propertyNo) {

        List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
                .getPropDetailByPropNoOnly(propertyNo);
        return mapListFromEntitytoDto(provAssMstEntList);
    }

    @Override
    public ProvisionalAssesmentMstDto copyProvDtoDataToOtherDto(ProvisionalAssesmentMstDto provAssMstDto) {
        ProvisionalAssesmentMstDto newDto = new ProvisionalAssesmentMstDto();
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
        BeanUtils.copyProperties(provAssMstDto, newDto);
        provAssMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDetEnt -> {
            ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
            final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
            BeanUtils.copyProperties(provDetEnt, provDetDto);
            provDetEnt.getProvisionalAssesmentFactorDtlDtoList().forEach(proAssFactEnt -> {
                ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                provAssFactDtoList.add(proAssFactDto);
            });
            provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
            provAssDetDtoList.add(provDetDto);
        });
        provAssMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwnEnt -> {
            ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
            BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
            provAssOwnDtoList.add(provAssOwnDto);
        });
        newDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        newDto.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        return newDto;
    }

    @Override
    @Transactional
    public void copyDataFromProvisionalPropDetailToHistory(List<ProvisionalAssesmentMstDto> provAssDtoList,Long empId,ProvisionalAssesmentMstDto provAsseMstDto) {
		provAssDtoList.forEach(provAssDto -> { // Copy complete data of Property Detail from provisional to History
			ProAssMstHisEntity proAssMstHisEntity = new ProAssMstHisEntity();
			proAssMstHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
			provAsseMstDto.setProAssId(provAssDto.getProAssId());
			provAsseMstDto.setUpdatedBy(empId);
			provAsseMstDto.setUpdatedDate(new Date());
			provAsseMstDto.setLgIpMacUpd(provAsseMstDto.getLgIpMac());
			try {
				auditService.createHistory(provAsseMstDto, proAssMstHisEntity);
			} catch (Exception ex) {
				throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + provAsseMstDto, ex);
			}
			provAsseMstDto.getProvisionalAssesmentDetailDtoList().forEach(det -> {
				if (det.getFaYearId().toString().equals(provAssDto.getFaYearId().toString())) {
					ProAssDetailHisEntity proAssDetHisEntity = new ProAssDetailHisEntity();
					proAssDetHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
					proAssDetHisEntity.setAssId(provAssDto.getProAssId());
					det.setUpdatedBy(empId);
					det.setUpdatedDate(new Date());
					det.setLgIpMacUpd(provAsseMstDto.getLgIpMac());
					try {
						auditService.createHistory(det, proAssDetHisEntity);
					} catch (Exception ex) {
						throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + det, ex);
					}
					det.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
						ProAssFactlHisEntity proAssFactlHisEntity = new ProAssFactlHisEntity();
						proAssFactlHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
						proAssFactlHisEntity.setProAssdId(det.getProAssdId());
						proAssFactlHisEntity.setProAssId(provAssDto.getProAssId());
						fact.setUpdatedBy(empId);
						fact.setUpdatedDate(new Date());
						fact.setLgIpMacUpd(provAsseMstDto.getLgIpMac());
						try {
							auditService.createHistory(fact, proAssFactlHisEntity);
						} catch (Exception ex) {
							throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + fact, ex);
						}
					});

					// 97207 By Arun
					det.getRoomDetailsDtoList().forEach(room -> {
						ProAssRoomHisEntity proAssRoomHisEntity = new ProAssRoomHisEntity();
						proAssRoomHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
						proAssRoomHisEntity.setProAssdId(det.getProAssdId());
						try {
							auditService.createHistory(room, proAssRoomHisEntity);
						} catch (Exception ex) {
							throw new FrameworkException(MainetConstants.Property.validation.HISTORY_MASG + room, ex);
						}
					});
				}
			});
			if (provAsseMstDto.getProAssId() == provAssDto.getProAssId()) {
				provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(owner -> {
					ProAssOwnerHisEntity proAssOwnHisEntity = new ProAssOwnerHisEntity();
					proAssOwnHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
					proAssOwnHisEntity.setProAssId(provAssDto.getProAssId());
					owner.setUpdatedBy(empId);
					owner.setUpdatedDate(new Date());
					owner.setLgIpMacUpd(provAsseMstDto.getLgIpMac());
					try {
						auditService.createHistory(owner, proAssOwnHisEntity);
					} catch (Exception ex) {
						LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + owner, ex);
					}
				});
			}
		});
        

    }

    @Override
    @Transactional(readOnly = true)
    public boolean CheckForAssesmentFieldForCurrYear(long orgId, String assNo, String assOldpropno, String status,
            Long finYearId, Long serviceId) {
        return iProvisionalAssessmentMstDao.CheckForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status, finYearId,
                serviceId);
    }

    @Override
    public void saveAndUpdateProvsionalOwner(ProvisionalAssesmentMstDto provAssMstDto, Long orgId, Long empId) {
        final String ipAddress = provAssMstDto.getLgIpMac();
        final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
            ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            if (provOwndto.getProAssoId() == 0) {
                provAsseOwnerEnt.setCreatedBy(empId);
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setAssNo(provAssMstDto.getAssNo());
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setAssoStartDate(provAssMstDto.getAssAcqDate());
                provAsseOwnerEnt.setOrgId(orgId);
                provAsseOwnerEnt.setApmApplicationId(provAssMstDto.getApmApplicationId());
                provAsseOwnerEnt.setSmServiceId(provAssMstDto.getSmServiceId());
                provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                                                                             // provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setUpdatedBy(empId);
                provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                provAsseOwnerEnt.setUpdatedDate(new Date());
                provAsseOwnEntList.add(provAsseOwnerEnt);
            }
            provisionalAssessOwnerRepository.save(provAsseOwnEntList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByParentPropNo(long orgId, String assNo) {
        return provisionalAssesmentMstRepository.getPropDetailFromProvAssByParentPropNo(orgId, assNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAmalgamatedChildPropNo(String assNo, long orgId) {
        List<String> propNo = new ArrayList<>();
        List<ProvisionalAssesmentMstEntity> propList = provisionalAssesmentMstRepository
                .getPropDetailFromProvAssByParentPropNo(orgId, assNo);
        if (propList != null && !propList.isEmpty()) {
            propList.forEach(prop -> {
                propNo.add(prop.getAssNo());
            });
        }
        return propNo;
    }

    @Override
    @Transactional
    public void saveProvisionalAssessmentFromDtoForAmlg(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, long orgId,
            Long empId,
            String authWithChng) {
        final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
        if (authWithChng != null)
            provisionalAssesmentMstDto.setAssAutStatus(authWithChng);
        BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);
        provAssetMst.setUpdatedBy(empId);
        provAssetMst.setUpdatedDate(new Date());
        final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
            ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
            if (provOwndto.getProAssoId() <= 0d) {
                provOwndto.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
                provOwndto.setOrgId(orgId);
                provOwndto.setCreatedBy(empId);
                provOwndto.setLgIpMac(provisionalAssesmentMstDto.getLgIpMac());
                provOwndto.setCreatedDate(new Date());
                provOwndto.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                provOwndto.setApmApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
                provOwndto.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
                provOwndto.setAssNo(provisionalAssesmentMstDto.getAssNo());
                provOwndto.setAssoActive(MainetConstants.STATUS.ACTIVE);
            }
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
            provOwndto.setProAssoId(provAsseOwnerEnt.getProAssoId());
            provAsseOwnEntList.add(provAsseOwnerEnt);
        });
        provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
        List<ProvisionalAssesmentMstEntity> proEntityList = new ArrayList<>();
        ProvisionalAssesmentMstEntity proEntity= provisionalAssesmentMstRepository.save(provAssetMst);
        proEntityList.add(proEntity);
        provHistAtAdd(proEntityList);
    }

    @Transactional
    @Override
    public void saveProvisionalAssessmentListForAmalgamation(
            List<ProvisionalAssesmentMstDto> provAssesmentMstDtoList,
            Long orgId, Long empId, String ipAddress, String parentPropNo,Long serviceId) {
        List<ProvisionalAssesmentMstEntity> proAssList = new ArrayList<>();
        provAssesmentMstDtoList.forEach(proAssMastDto -> {
            final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
            proAssMastDto.setUpdatedBy(empId);
            proAssMastDto.setUpdatedDate(new Date());
            proAssMastDto.setLgIpMacUpd(ipAddress);
            proAssMastDto.setParentProp(parentPropNo);
            proAssMastDto.setAssActive(MainetConstants.STATUS.INACTIVE);
            BeanUtils.copyProperties(proAssMastDto, provAssetMst);

            // Assessment Detail
            final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            proAssMastDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {

                ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
                BeanUtils.copyProperties(provDet, provAsseDet);
                provAsseDet.setTbAsAssesmentMast(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.INACTIVE);
                // Factor
                final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
                provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                    ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                    BeanUtils.copyProperties(provfact, provAssFact);
                    provAssFact.setUpdatedBy(empId);
                    provAssFact.setUpdatedDate(new Date());
                    provAssFact.setLgIpMacUpd(ipAddress);
                    provAssFact.setTbAsAssesmentMast(provAssetMst);
                    provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                    provAssFact.setAssfActive(MainetConstants.STATUS.INACTIVE);
                    provAsseFactList.add(provAssFact);
                });
                provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);
                
                final List<ProvisionalAssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
                provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
                    ProvisionalAssessmentRoomDetailEntity roomEntity = new ProvisionalAssessmentRoomDetailEntity();
                    BeanUtils.copyProperties(roomDetails, roomEntity);
                    roomEntity.setProAssDetEntity(provAsseDet);
                    roomEntity.setOrgId(orgId);
                    roomEntity.setCreatedBy(empId);
                    roomEntity.setCreatedDate(new Date());
                    roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                    proAssessmentRoomDetailEntity.add(roomEntity);
                });
                provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);
                provAsseDetList.add(provAsseDet);

            });
            provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

            // Assessment owner
            // for mutilple year assessment owner details are saved for
            // current year assessment
            final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
            proAssMastDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
                ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
                BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                provAsseOwnerEnt.setUpdatedBy(empId);
                provAsseOwnerEnt.setUpdatedDate(new Date());
                provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
                provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.INACTIVE);
                provAsseOwnerEnt.setSmServiceId(serviceId);
                provAsseOwnEntList.add(provAsseOwnerEnt);
            });

            provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
            proAssList.add(provAssetMst);
        });
        List<ProvisionalAssesmentMstEntity> proEntityList =provisionalAssesmentMstRepository.save(proAssList);
        provHistAtAdd(proEntityList);

    }

    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> getViewData(String propNo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto fetchPropertyByPropNo(String propNo, Long orgId) {
        ProvisionalAssesmentMstDto assMstDto = null;
        ProvisionalAssesmentMstEntity provAssMast = provisionalAssesmentMstRepository.fetchPropertyByPropNo(
                propNo);
        if (provAssMast != null) {
            assMstDto = new ProvisionalAssesmentMstDto();
            BeanUtils.copyProperties(provAssMast, assMstDto);
            final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
            provAssMast.getProvisionalAssesmentDetailList().forEach(provDetEnt -> {
                ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                BeanUtils.copyProperties(provDetEnt, provDetDto);
                provDetEnt.getProvisionalAssesmentFactorDtlList().forEach(proAssFactEnt -> {
                    ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                    provAssFactDtoList.add(proAssFactDto);
                });
                provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                provAssDetDtoList.add(provDetDto);
            });
            assMstDto.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
            if (!provAssMast.getProvisionalAssesmentOwnerDtlList().isEmpty()
                    && provAssMast.getProvisionalAssesmentOwnerDtlList() != null) {
                final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
                provAssMast.getProvisionalAssesmentOwnerDtlList().forEach(provOwnEnt -> {
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
    public List<Long> fetchApplicationAgainstProperty(long orgId, String proertyNo) {
        return provisionalAssesmentMstRepository.fetchApplicationAgainstProperty(orgId, proertyNo);
    }

    @Transactional
    @Override
    public void deleteProvisionalAssessWithEntById(List<ProvisionalAssesmentMstEntity> provAssEntList) {
        for (ProvisionalAssesmentMstEntity masEntity : provAssEntList) {
            List<Long> proAssdIdList = provisionalAssesmentDetRepository.fetchProAssDetEntity(masEntity);
            for (Long proAssdId : proAssdIdList) {
                provisionalAssesmentDetRepository.delete(proAssdId);
            }
            provisionalAssesmentMstRepository.delete(masEntity.getProAssId());
        }
    }

    @Transactional
    @Override
    public void deleteProvisionalAssessWithDtoById(List<ProvisionalAssesmentMstDto> provAssDtoList) {
        for (ProvisionalAssesmentMstDto proAssDto : provAssDtoList) {
            provisionalAssesmentMstRepository.delete(proAssDto.getProAssId());
        }

    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(Long orgId, String propertryNo) {
        return provisionalAssesmentMstRepository.getPrimaryOwnerDetailByPropertyNo(orgId, propertryNo);
    }

    @Transactional
    @Override
    public List<ProvisionalAssesmentMstEntity> getListOfPropertyByListOfPropNos(Long orgId, List<String> propNoList) {
        return provisionalAssesmentMstRepository.getListOfPropertyByListOfPropNos(orgId, propNoList);
    }

    @Override
    @Transactional(readOnly = true)
    public int validateProperty(String proertyNo, Long orgId, Long serviceId) {
        return iProvisionalAssessmentMstDao.validateProperty(proertyNo, orgId, serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigInteger[] validateBill(String proertyNo, Long orgId, Long bmIdno) {
        return iProvisionalAssessmentMstDao.validateBill(proertyNo, orgId, bmIdno);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteDetailInDESEdit(ProvisionalAssesmentMstDto provAssDto, Long orgId,
            Long empId, String ipAddress) {
        ProvisionalAssesmentMstDto provAssOrginalMstDto = null;

        List<ProvisionalAssesmentMstDto> provAssDtoList = getDataEntryPropDetailFromProvAssByPropNo(orgId, provAssDto.getAssNo(),
                "A");
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
                        provisionalAssesmentDetRepository.delete(provDet.getProAssdId());
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
                                        provisionalAssesmentFactRepository.delete(provfact.getProAssfId());
                                    }
                                }
                            });
                }

                for (ProvisionalAssesmentOwnerDtlDto provOwndto : provAssOrginalMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                    boolean ownerExit = provAssDto.getProvisionalAssesmentOwnerDtlDtoList().stream()
                            .filter(s -> s.getProAssoId() == provOwndto.getProAssoId())
                            .findFirst()
                            .isPresent();

                    if (!ownerExit) {
                        provisionalAssesmentOwnerRepository.delete(provOwndto.getProAssoId());
                    }

                }

            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean CheckForAssesmentFiledForCurrYear(long orgId, String assNo, String assOldpropno, String status,
            Long finYearId) {
        return iProvisionalAssessmentMstDao.CheckForAssesmentFiledInCurrentYearOrNot(orgId, assNo, assOldpropno, status,
                finYearId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> saveNoOfDaysEditForm(List<ProvisionalAssesmentMstDto> oldProvAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, List<TbBillMas> oldBillMasList,
            List<TbBillMas> newBillMasList, Employee emp, List<Long> finYearList, BillDisplayDto advanceAmt,
            BillDisplayDto earlyPayRebate, List<BillReceiptPostingDTO> rebateRecDto, double advanceAmount,
            PropertyPenltyDto penaltydto, String ipAddress, Long userId, double editableLastAmountPaid,
            double previousSurcharge) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(provisionalAssesmentMstDto.getOrgId());
        try {
            final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                    .getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.STATUS.ACTIVE);

            List<ProvisionalAssesmentMstDto> oldPropDetailByPropNoOnly = getPropDetailByPropNoOnly(
                    provisionalAssesmentMstDto.getAssNo());
            copyDataFromProvisionalPropDetailToHistory(oldPropDetailByPropNoOnly,emp.getEmpId(),provisionalAssesmentMstDto);
            provisionalBillService.copyDataFromProvisionalBillDetailToHistory(oldBillMasList,MainetConstants.FlagW);
            propertyAuthorizationService.setUniqueIdentiFromOldBillToNewBill(newBillMasList, oldBillMasList);
            List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
            Map<Long, Long> updateProvisionalAssessment = updateProvisionalAssessment(provisionalAssesmentMstDto,
                    oldProvAssMstDtoList, provisionalAssesmentMstDto.getOrgId(), emp.getEmpId(), MainetConstants.FlagN,
                    finYearList, provAssEntList);

            saveOrUpdatStatus(oldPropDetailByPropNoOnly, provisionalAssesmentMstDto, finYearList, emp, organisation);
            LookUp billDeletionInactive = null;
            try {
                billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
            } catch (Exception e) {

            }
            if (billDeletionInactive == null
                    || org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                    || org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
                            MainetConstants.FlagN)) {
                receiptEntryService.inActiveAllRebetReceiptByAppNo(provisionalAssesmentMstDto.getOrgId(),
                        provisionalAssesmentMstDto.getApmApplicationId(), deptId,
                        "Inactive previous rebate paid amount at assesment editable period", emp.getEmpId());
            }

            if (advanceAmount > 0) {
                TbServiceReceiptMasEntity latestReceiptDetailByAddRefNo = receiptEntryService
                        .getLatestReceiptDetailByAddRefNo(organisation.getOrgid(),
                                provisionalAssesmentMstDto.getAssNo());
                saveAdvancePayment(organisation.getOrgid(), advanceAmount, provisionalAssesmentMstDto.getAssNo(),
                        emp.getEmpId(), latestReceiptDetailByAddRefNo.getRmRcptid());
            }

            if (provisionalAssesmentMstDto.getBillTotalAmt() <= 0) {
                if ((oldBillMasList.get(oldBillMasList.size() - 1).getBmTotalOutstanding() <= 0
                        && earlyPayRebate != null && earlyPayRebate.getTotalTaxAmt() != null)
                        || (newBillMasList.get(newBillMasList.size() - 1).getBmTotalOutstanding() > 0
                                && earlyPayRebate != null && earlyPayRebate.getTotalTaxAmt() != null)) {
                    final Map<Long, Double> details = new HashMap<>(0);
                    final Map<Long, Long> billDetails = new HashMap<>(0);
                    List<BillReceiptPostingDTO> earlyPayRebateList = billMasterCommonService.updateBillData(
                            newBillMasList, earlyPayRebate.getTotalTaxAmt().doubleValue(), details, billDetails,
                            organisation, null, null);
                    earlyPayRebateList.forEach(rebate -> {// as there is always one early Payment rebate
                        rebate.setTaxId(earlyPayRebate.getTaxId());
                        rebate.setRmNarration(earlyPayRebate.getTaxDesc());
                    });

                }
            } else if (provisionalAssesmentMstDto.getBillTotalAmt() > 0) {
                final Map<Long, Double> details = new HashMap<>(0);
                final Map<Long, Long> billDetails = new HashMap<>(0);
                if (previousSurcharge > 0) {
                    editableLastAmountPaid = editableLastAmountPaid - previousSurcharge;
                }

                LookUp knockOffTaxwise = null;
                try {
                    knockOffTaxwise = CommonMasterUtility.getValueFromPrefixLookUp("KTW", "ENV", organisation);
                } catch (Exception e) {

                }
                if (knockOffTaxwise != null && org.apache.commons.lang.StringUtils.isNotBlank(knockOffTaxwise.getOtherField())
                        && org.apache.commons.lang.StringUtils.equals(knockOffTaxwise.getOtherField(), MainetConstants.FlagY)) {
                    TbBillMas currentBillMas = newBillMasList.get(newBillMasList.size() - 1);
                    List<TbServiceReceiptMasEntity> currentYearBillPaidList = ApplicationContextProvider
                            .getApplicationContext().getBean(ReceiptRepository.class)
                            .getbillPaidBetweenCurFinYear(provisionalAssesmentMstDto.getAssNo(),
                                    currentBillMas.getBmFromdt(), currentBillMas.getBmTodt(), organisation.getOrgid());
                    double excessAmt = propertyService.knockOffPreviousPaidBillTaxWise(currentYearBillPaidList, newBillMasList,
                            organisation,
                            editableLastAmountPaid);
                    if (excessAmt > 0) {
                        billMasterCommonService.updateBillData(newBillMasList, excessAmt, details, billDetails,
                                organisation, null, null);
                    }
                } else {
                    billMasterCommonService.updateBillData(newBillMasList, editableLastAmountPaid, details, billDetails,
                            organisation, null, null);
                }

            }

            List<BillReceiptPostingDTO> billRecePstingDto = null;
            double ajustedAmt = 0;
            if ((provisionalAssesmentMstDto.getBillTotalAmt() > 0 && advanceAmt != null
                    && advanceAmt.getTotalTaxAmt() != null)) {

                ApplicationContextProvider.getApplicationContext().getBean(AsExecssAmtService.class)
                        .inactiveAllAdvPayEnrtyByPropNo(provisionalAssesmentMstDto.getAssNo(),
                                provisionalAssesmentMstDto.getOrgId());

            } else if (newBillMasList.get(newBillMasList.size() - 1).getBmTotalOutstanding() > 0 && advanceAmt != null
                    && advanceAmt.getTotalTaxAmt() != null) {
                billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(newBillMasList, advanceAmt, organisation, null,
                        provisionalAssesmentMstDto.getAssNo());

                if (newBillMasList.get(newBillMasList.size() - 1).getExcessAmount() > 0) {
                    ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue()
                            - newBillMasList.get(newBillMasList.size() - 1).getExcessAmount();
                } else {
                    ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue();
                }
            }
            List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
            if (earlyPayRebate != null && earlyPayRebate.getCurrentYearTaxAmt() != null) {
                newBillMasList.get(newBillMasList.size() - 1)
                        .setBmToatlRebate(earlyPayRebate.getCurrentYearTaxAmt().doubleValue());
            }
            List<Long> billIds = provisionalBillService.saveAndUpdateProvisionalBill(newBillMasList,
                    provisionalAssesmentMstDto.getOrgId(),
                    emp.getEmpId(), provisionalAssesmentMstDto.getAssNo(), updateProvisionalAssessment, provBillList,
                    provisionalAssesmentMstDto.getLgIpMac());
            selfAssessmentService.saveDemandLevelRebate(provisionalAssesmentMstDto, emp.getEmpId(), deptId,
                    newBillMasList, rebateRecDto, organisation, provBillList);
            if (ajustedAmt > 0) {
                selfAssessmentService.saveAdvanceAmt(provisionalAssesmentMstDto, emp.getEmpId(), deptId, advanceAmt,
                        organisation, newBillMasList, provisionalAssesmentMstDto.getLgIpMac(), provBillList,
                        billRecePstingDto, ajustedAmt,null,0);
            }
            if (penaltydto != null) {
                ApplicationContextProvider.getApplicationContext().getBean(PropertyPenltyService.class)
                        .updatePropertyPenalty(penaltydto, ipAddress, userId);
            }

            return billIds;

        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While editing the self assessment data", exception);
        }

    }

    public boolean saveAdvancePayment(final Long orgId, final Double amount, final String propNo, final Long userId,
            final Long receiptId) {
        ApplicationContextProvider.getApplicationContext().getBean(AsExecssAmtService.class).addEntryInExcessAmt(orgId,
                propNo, amount, receiptId, userId);
        return true;
    }

    private void saveOrUpdatStatus(List<ProvisionalAssesmentMstDto> oldPropDetailByPropNoOnly,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, List<Long> finYearList, Employee emp,
            Organisation organisation) {
        List<Long> finYesrListToAddNewEntries = new ArrayList<Long>();

        LookUp billDeletionInactive = null;
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
        } catch (Exception e) {

        }
        if (billDeletionInactive == null
                || org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                        && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
                                MainetConstants.FlagN)) {
            finYearList.forEach(finYesr -> {
                Optional<ProvisionalAssesmentMstDto> propMasExists = oldPropDetailByPropNoOnly.stream()
                        .filter(detail -> detail.getFaYearId().equals(finYesr)).findFirst();
                if (!propMasExists.isPresent()) {
                    finYesrListToAddNewEntries.add(finYesr);
                }
            });
        }
        if (CollectionUtils.isNotEmpty(finYesrListToAddNewEntries)) {
            provisionalAssesmentMstDto.setProAssId(0L);
            saveProvisionalAssessment(provisionalAssesmentMstDto, organisation.getOrgid(), emp.getEmpId(),
                    finYesrListToAddNewEntries, provisionalAssesmentMstDto.getApmApplicationId());
        }
        List<Long> primaKeyOfPropMasToInactive = new ArrayList<Long>();
        oldPropDetailByPropNoOnly.forEach(oldPropMas -> {
            Optional<Long> findFirst = finYearList.stream().filter(detail -> detail.equals(oldPropMas.getFaYearId()))
                    .findFirst();
            if (!findFirst.isPresent()) {
                primaKeyOfPropMasToInactive.add(oldPropMas.getProAssId());
            }
        });
        if (CollectionUtils.isNotEmpty(primaKeyOfPropMasToInactive)) {
            provisionalAssesmentMstRepository.updatePropMasStatus(primaKeyOfPropMasToInactive, MainetConstants.FlagI);
        }

        List<Long> unitDetailInactiveList = new ArrayList<Long>();
        oldPropDetailByPropNoOnly.get(oldPropDetailByPropNoOnly.size() - 1).getProvisionalAssesmentDetailDtoList()
                .forEach(unitDetail -> {
                    Optional<ProvisionalAssesmentDetailDto> findFirst = provisionalAssesmentMstDto
                            .getProvisionalAssesmentDetailDtoList().stream()
                            .filter(unitDetail1 -> Long.valueOf(unitDetail1.getProAssdId())
                                    .equals(Long.valueOf(unitDetail.getProAssdId())))
                            .findFirst();
                    if (!findFirst.isPresent()) {
                        unitDetailInactiveList.add(unitDetail.getProAssdId());
                    }

                });

        if (CollectionUtils.isNotEmpty(unitDetailInactiveList)) {
            provisionalAssesmentMstRepository.updatePropUnitDetailStatus(unitDetailInactiveList, MainetConstants.FlagI);
        }
    }

    @Override
    public void updatePropMasStatus(List<Long> propPrimKeyList, String status) {
        provisionalAssesmentMstRepository.updatePropMasStatus(propPrimKeyList, status);

    }

    @Override
	@Transactional(readOnly = true)
	public ProvisionalAssesmentMstDto fetchProvisionalDetailsByPropNo(String propNo, Long orgId) {
		ProvisionalAssesmentMstDto assMstDto = null;
		List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();
		List<ProvisionalAssesmentMstEntity> provAssMstEntList = new ArrayList<>();
		ProvisionalAssesmentMstEntity provAssMast = provisionalAssesmentMstRepository.fetchPropertyByPropNo(propNo);
		if (provAssMast != null) {
			provAssMstEntList.add(provAssMast);
			provAssMstDtoList = mapListFromEntitytoDto(provAssMstEntList);
			return provAssMstDtoList.get(0);
		}
		return assMstDto;
	}

	@Override
	@Transactional(readOnly = true)
	public ProvisionalAssesmentMstDto fetchProvisionalDetailsByOldPropNo(String oldPropNo, Long orgId) {
		ProvisionalAssesmentMstDto assMstDto = null;
		List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();
		List<ProvisionalAssesmentMstEntity> provAssMstEntList = new ArrayList<>();
		ProvisionalAssesmentMstEntity provAssMast = provisionalAssesmentMstRepository
				.fetchPropertyByOldPropNo(oldPropNo);
		if (provAssMast != null) {
			provAssMstEntList.add(provAssMast);
			provAssMstDtoList = mapListFromEntitytoDto(provAssMstEntList);
			return provAssMstDtoList.get(0);
		}
		return assMstDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public int validatePropertyWithFlat(String propNo, String flatNo, long orgid, Long serviceId) {
		 return iProvisionalAssessmentMstDao.validatePropertyWithFlat(propNo,flatNo, orgid, serviceId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(long orgId, String propertyNo,
			String flatNo, String activeSatus) {
		 List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
	                .getPropDetailFromProvAssByPropNoAndFlatNo(orgId, propertyNo,flatNo, activeSatus);
	        return mapListFromEntitytoDto(provAssMstEntList);
	}

	@Override
	@Transactional
	public List<Long> fetchApplicationAgainstPropertyWithFlatNo(long orgId, String proertyNo, String flatNo) {
		 return provisionalAssesmentMstRepository.fetchApplicationAgainstPropertyWithFlatNo(orgId, proertyNo,flatNo);
	}
	
	@Transactional
	@Override
	public List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNoAndOldPropNo(Long orgId,
			String propertyNo, String activeSatus, String oldPropNo) {

		List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
				.getPropDetailFromProvAssByPropNo(orgId, propertyNo, activeSatus);
		if ((provAssMstEntList == null || provAssMstEntList.isEmpty())
				&& (oldPropNo != null && !oldPropNo.equals(MainetConstants.BLANK))) {
			provAssMstEntList = provisionalAssesmentMstRepository.getProAssIdByOldPropertyNoActive(orgId, oldPropNo);
		}
		if (CollectionUtils.isNotEmpty(provAssMstEntList)) {
			LOGGER.info("Assesment master found succesfully -> getPropDetailFromMainAssByPropNoOrOldPropNo"
					+ provAssMstEntList.size());
		} else {
			LOGGER.info("Assesment master not found failed -> getPropDetailFromMainAssByPropNoOrOldPropNo");
		}

		return mapListFromEntitytoDto(provAssMstEntList);
	}
	   
	@Override
	@Transactional(readOnly = true)
	public List<ProvisionalAssesmentMstDto> getPropDetailFromProvAssByOldPropNoAndFlatNo(long orgId, String oldPropNo,
			String flatNo, String activeSatus) {
		List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
				.getPropDetailFromProvAssByPropNoAndFlatNo(orgId, oldPropNo, flatNo, activeSatus);
		return mapListFromEntitytoDto(provAssMstEntList);
	}

	@Override
	public List<ProvisionalAssesmentMstDto> getPropDetailByOldPropNoAndOrgId(String assOldpropno, Long orgId) {
		List<ProvisionalAssesmentMstEntity> provAssMstEntList = provisionalAssesmentMstRepository
				.getPropDetailByOldPropNoAndOrgId(assOldpropno, orgId);
		return mapListFromEntitytoDto(provAssMstEntList);
	}
	
	@Override
	@Transactional
	public void updateBillMethodChangeFlag(String proertyNo, String billMethodChngFlag, Long orgId) {
		provisionalAssesmentMstRepository.updateBillMethodChangeFlag(proertyNo, billMethodChngFlag, orgId);
	}
	
	@Override
	public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
		return provisionalAssesmentMstRepository.getPropNoByOldPropNo(oldPropNo, orgId);
	}
}
