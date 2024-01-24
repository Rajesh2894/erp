package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.property.domain.PropertyDetEntity;
import com.abm.mainet.property.domain.PropertyFactorEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.domain.PropertyRoomDetailEntity;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.PropertyMastRepository;

@Service
public class PrimaryPropertyServiceImpl implements PrimaryPropertyService {

    /**
     * 
     */
    private static final long serialVersionUID = 4479579162185786594L;

    @Autowired
    private PropertyMastRepository propertyMastRepository;

    @Override
    @Transactional
    public void savePropertyMaster(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId) {
        final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
        PropertyMastEntity entity = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(provisionalAssesmentMstDto.getFlatNo())) {
        	entity = getPropertyDetailsByPropNoNFlatNo(provisionalAssesmentMstDto.getAssNo(),provisionalAssesmentMstDto.getFlatNo(),orgId);
		} else {
			entity = getPropertyDetailsByPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
		}
        if (entity == null) {
            entity = new PropertyMastEntity();
            entity.setOrgId(orgId);
            entity.setCreatedBy(empId);
            entity.setLgIpMac(ipAddress);
            entity.setCreatedDate(new Date());
            entity.setAssActive(MainetConstants.STATUS.ACTIVE);
            BeanUtils.copyProperties(provisionalAssesmentMstDto, entity);
            entity.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);

        } else {
            deleteDetailInDESEdit(provisionalAssesmentMstDto, entity);
            BeanUtils.copyProperties(provisionalAssesmentMstDto, entity);
            entity.setUpdatedBy(empId);
            entity.setUpdatedDate(new Date());
            entity.setLgIpMacUpd(ipAddress);
        }
        // Assessment Detail
        final List<PropertyDetEntity> provAsseDetList = new ArrayList<>();
        for (ProvisionalAssesmentDetailDto provDet : provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList()) {
            boolean isexist = false;
            if (entity.getProDetList() != null && !entity.getProDetList().isEmpty()) {
                isexist = entity.getProDetList().stream()
                        .filter(priDet -> priDet.getAssdUnitNo().equals(provDet.getAssdUnitNo()))
                        .findFirst()
                        .isPresent();
            }
            if (!isexist) {
                PropertyDetEntity provAsseDet = new PropertyDetEntity();
                BeanUtils.copyProperties(provDet, provAsseDet);
                provAsseDet.setTbAsPrimaryMast(entity);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                // Factor
                final List<PropertyFactorEntity> provAsseFactList = new ArrayList<>();
                for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
                    PropertyFactorEntity provAssFact = new PropertyFactorEntity();
                    BeanUtils.copyProperties(provfact, provAssFact);
                    provAssFact.setTbAsPrimaryDet(provAsseDet);
                    provAssFact.setTbAsPrimaryMast(entity);
                    // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setOrgId(orgId);
                    provAsseFactList.add(provAssFact);
                }
                provAsseDet.setPropfactorDtlList(provAsseFactList);
                
                //97207 By Arun
                final List<PropertyRoomDetailEntity> propertyRoomDetailEntityList = new ArrayList<>();
                for ( PropertyRoomDetailsDto roomDetailDto: provDet.getRoomDetailsDtoList()) {
                	PropertyRoomDetailEntity propertyRoomDetailEntity=new PropertyRoomDetailEntity();
                	BeanUtils.copyProperties(roomDetailDto, propertyRoomDetailEntity);
                	propertyRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                	propertyRoomDetailEntity.setCreatedBy(empId);
                	propertyRoomDetailEntity.setCreatedDate(new Date());
                    propertyRoomDetailEntity.setOrgId(orgId);
                    propertyRoomDetailEntity.setPropDetEntity(provAsseDet);
                	propertyRoomDetailEntityList.add(propertyRoomDetailEntity);
                }
                provAsseDet.setRoomDetailEntityList(propertyRoomDetailEntityList);
                ///end               
                
                provAsseDetList.add(provAsseDet);
            } else {
                setExistingFloorDetails(orgId, empId, ipAddress, entity, provAsseDetList, provDet);
            }
        }
        entity.setProDetList(provAsseDetList);
        propertyMastRepository.save(entity);
    }

	@Override
	@Transactional
	public void savePropertyMasterForIndividualBill(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
			Long empId) {

		PropertyMastEntity entity = null;
		boolean flag=false;
		entity = getPropertyDetailsByPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
		if(entity==null) {
			flag=true;
		}
		for (ProvisionalAssesmentDetailDto provDet : provisionalAssesmentMstDto
				.getProvisionalAssesmentDetailDtoList()) {
			final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();

			if (flag) {
				entity = new PropertyMastEntity();
				BeanUtils.copyProperties(provisionalAssesmentMstDto, entity);
				entity.setOrgId(orgId);
				entity.setCreatedBy(empId);
				entity.setLgIpMac(ipAddress);
				entity.setCreatedDate(new Date());
				entity.setAssActive(MainetConstants.STATUS.ACTIVE);								
				entity.setFlatNo(provDet.getFlatNo());
				entity.setLogicalPropNo(provisionalAssesmentMstDto.getAssNo() + MainetConstants.operator.UNDER_SCORE
						+ provDet.getFlatNo());
				entity.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
			} else {
				deleteDetailInDESEdit(provisionalAssesmentMstDto, entity);
				BeanUtils.copyProperties(provisionalAssesmentMstDto, entity);
				entity.setUpdatedBy(empId);
				entity.setUpdatedDate(new Date());
				entity.setLgIpMacUpd(ipAddress);
			}
			final List<PropertyDetEntity> provAsseDetList = new ArrayList<>();
			boolean isexist = false;
			if (entity.getProDetList() != null && !entity.getProDetList().isEmpty()) {
				isexist = entity.getProDetList().stream()
						.filter(priDet -> priDet.getAssdUnitNo().equals(provDet.getAssdUnitNo())).findFirst()
						.isPresent();
			}
			if (!isexist) {
				PropertyDetEntity provAsseDet = new PropertyDetEntity();
				BeanUtils.copyProperties(provDet, provAsseDet);
				provAsseDet.setTbAsPrimaryMast(entity);
				provAsseDet.setOrgId(orgId);
				provAsseDet.setCreatedBy(empId);
				provAsseDet.setLgIpMac(ipAddress);
				provAsseDet.setCreatedDate(new Date());
				provAsseDet.setAssdAssesmentDate(new Date());
				provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
				// Factor
				final List<PropertyFactorEntity> provAsseFactList = new ArrayList<>();
				for (ProvisionalAssesmentFactorDtlDto provfact : provDet.getProvisionalAssesmentFactorDtlDtoList()) {
					PropertyFactorEntity provAssFact = new PropertyFactorEntity();
					BeanUtils.copyProperties(provfact, provAssFact);
					provAssFact.setTbAsPrimaryDet(provAsseDet);
					provAssFact.setTbAsPrimaryMast(entity);
					// provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
					provAssFact.setCreatedBy(empId);
					provAssFact.setCreatedDate(new Date());
					provAssFact.setLgIpMac(ipAddress);
					provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
					provAssFact.setOrgId(orgId);
					provAsseFactList.add(provAssFact);
				}
				provAsseDet.setPropfactorDtlList(provAsseFactList);

				// 97207 By Arun
				final List<PropertyRoomDetailEntity> propertyRoomDetailEntityList = new ArrayList<>();
				for (PropertyRoomDetailsDto roomDetailDto : provDet.getRoomDetailsDtoList()) {
					PropertyRoomDetailEntity propertyRoomDetailEntity = new PropertyRoomDetailEntity();
					BeanUtils.copyProperties(roomDetailDto, propertyRoomDetailEntity);
					propertyRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
					propertyRoomDetailEntity.setCreatedBy(empId);
					propertyRoomDetailEntity.setCreatedDate(new Date());
					propertyRoomDetailEntity.setOrgId(orgId);
					propertyRoomDetailEntity.setPropDetEntity(provAsseDet);
					propertyRoomDetailEntityList.add(propertyRoomDetailEntity);
				}
				provAsseDet.setRoomDetailEntityList(propertyRoomDetailEntityList);
				/// end

				provAsseDetList.add(provAsseDet);
			} else {
				setExistingFloorDetails(orgId, empId, ipAddress, entity, provAsseDetList, provDet);
			}
			entity.setProDetList(provAsseDetList);
			propertyMastRepository.save(entity);
		}
	}
	
    private void deleteDetailInDESEdit(ProvisionalAssesmentMstDto provAssDto, PropertyMastEntity entity) {
        if (entity != null) {
            for (PropertyDetEntity provDet : entity.getProDetList()) {
                boolean isexist = false;
                if (entity.getProDetList() != null && !entity.getProDetList().isEmpty()) {
                    isexist = provAssDto.getProvisionalAssesmentDetailDtoList().stream()
                            .filter(priDet -> priDet.getAssdUnitNo().equals(provDet.getAssdUnitNo()))
                            .findFirst()
                            .isPresent();
                }
                if (!isexist) {
                    if (provDet.getPmAssdId() > 0) {
                        provDet.setAssdActive(MainetConstants.STATUS.INACTIVE);
                        provDet.getPropfactorDtlList().forEach(fact -> {
                            fact.setAssfActive(MainetConstants.STATUS.INACTIVE);
                        });
                        
                        //97207 by Arun
                        provDet.getRoomDetailEntityList().forEach(room ->{
                        	room.setIsActive(MainetConstants.STATUS.INACTIVE);
                        });
                        //
                    }
                } else {
                    provAssDto.getProvisionalAssesmentDetailDtoList().stream()
                            .filter(priDet -> priDet.getAssdUnitNo().equals(provDet.getAssdUnitNo()))
                            .forEach(pridet -> {
                                for (PropertyFactorEntity fact : provDet.getPropfactorDtlList()) {
                                    boolean isFactorexist = pridet.getProvisionalAssesmentFactorDtlDtoList().stream()
                                            .filter(priFact -> priFact.getAssfFactorValueId().equals(fact.getAssfFactorValueId()))
                                            .findFirst()
                                            .isPresent();
                                    if (!isFactorexist && fact.getPmAssfId() > 0) {
                                        fact.setAssfActive(MainetConstants.STATUS.INACTIVE);
                                    }
                                }
                                
                                //97207 By Arun
                                for ( PropertyRoomDetailEntity room : provDet.getRoomDetailEntityList()) {
                                    boolean isRoomExists = pridet.getRoomDetailsDtoList().stream()
                                            .filter(rooms -> rooms.getRoomNo().equals(room.getRoomNo()))
                                            .findFirst()
                                            .isPresent();
                                    if (!isRoomExists && room.getPrRoomId() > 0) {
                                    	room.setIsActive(MainetConstants.STATUS.INACTIVE);
                                    }
                                }
                                //
                                
                            });
                }
            }
        }
    }

    private void setExistingFloorDetails(Long orgId, Long empId, final String ipAddress, PropertyMastEntity entity,
            final List<PropertyDetEntity> provAsseDetList, ProvisionalAssesmentDetailDto provDet) {
        entity.getProDetList().stream().filter(priDet -> priDet.getAssdUnitNo().equals(provDet.getAssdUnitNo()))
                .forEach(pridet -> {
                    BeanUtils.copyProperties(provDet, pridet);
                    pridet.setUpdatedBy(empId);
                    pridet.setUpdatedDate(new Date());
                    pridet.setLgIpMacUpd(ipAddress);
                    provAsseDetList.add(pridet);
                    boolean isFactExist = false;
                    
					// 97207 By Arun
					boolean isRoomExists = false;
					final List<PropertyRoomDetailEntity> propertyRoomDetailEntityList = new ArrayList<>();
					if (provDet.getRoomDetailsDtoList() != null && !provDet.getRoomDetailsDtoList().isEmpty()) {

						for (PropertyRoomDetailsDto propertyRoomDetailsDto : provDet.getRoomDetailsDtoList()) {

							isRoomExists = pridet.getRoomDetailEntityList().stream()
									.filter(room -> room.getRoomNo().equals(propertyRoomDetailsDto.getRoomNo()))
									.findFirst().isPresent();

							if (!isRoomExists) {
								PropertyRoomDetailEntity propertyRoomDetailEntity = new PropertyRoomDetailEntity();
								BeanUtils.copyProperties(propertyRoomDetailsDto, propertyRoomDetailEntity);
								propertyRoomDetailEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
								propertyRoomDetailEntity.setCreatedBy(empId);
								propertyRoomDetailEntity.setCreatedDate(new Date());
								propertyRoomDetailEntity.setOrgId(orgId);
								propertyRoomDetailEntityList.add(propertyRoomDetailEntity);
							} else {
								pridet.getRoomDetailEntityList().stream()
										.filter(rooms -> rooms.getRoomNo().equals(propertyRoomDetailsDto.getRoomNo()))
										.forEach(room -> {
											BeanUtils.copyProperties(propertyRoomDetailsDto, room);
											if (room.getCreatedBy() == null) {
												room.setCreatedBy(empId);
											}
											if (room.getCreatedDate() == null) {
												room.setCreatedDate(new Date());
											}
											room.setIsActive(MainetConstants.STATUS.ACTIVE);
											room.setOrgId(orgId);
											propertyRoomDetailEntityList.add(room);
										});
							}							
						}
						pridet.setRoomDetailEntityList(propertyRoomDetailEntityList);
					}
					//
                    
                    final List<PropertyFactorEntity> provAsseFactList = new ArrayList<>();
                    if (provDet.getProvisionalAssesmentFactorDtlDtoList() != null
                            && !provDet.getProvisionalAssesmentFactorDtlDtoList().isEmpty()) {

                        for (ProvisionalAssesmentFactorDtlDto provFact : provDet
                                .getProvisionalAssesmentFactorDtlDtoList()) {

                            isFactExist = pridet.getPropfactorDtlList().stream()
                                    .filter(priFact -> priFact.getAssfFactorValueId().equals(provFact.getAssfFactorValueId()))
                                    .findFirst()
                                    .isPresent();

                            if (!isFactExist) {
                                PropertyFactorEntity provAssFact = new PropertyFactorEntity();
                                BeanUtils.copyProperties(provFact, provAssFact);
                                provAssFact.setTbAsPrimaryDet(pridet);
                                provAssFact.setTbAsPrimaryMast(entity);
                                // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                                provAssFact.setCreatedBy(empId);
                                provAssFact.setCreatedDate(new Date());
                                provAssFact.setLgIpMac(ipAddress);
                                provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                                provAssFact.setOrgId(orgId);
                                provAsseFactList.add(provAssFact);
                            } else {
                                pridet.getPropfactorDtlList().stream()
                                        .filter(priFact -> priFact.getAssfFactorValueId().equals(provFact.getAssfFactorValueId()))
                                        .forEach(priFact -> {
                                            BeanUtils.copyProperties(provFact, priFact);
                                            priFact.setUpdatedBy(empId);
                                            priFact.setUpdatedDate(new Date());
                                            priFact.setLgIpMacUpd(ipAddress);
                                            if (priFact.getCreatedBy() == null) {
                                                priFact.setCreatedBy(empId);
                                            }
                                            if (priFact.getCreatedDate() == null) {
                                                priFact.setCreatedDate(new Date());
                                            }
                                            if (priFact.getLgIpMac() == null || priFact.getLgIpMac().isEmpty()) {
                                                priFact.setLgIpMac(ipAddress);
                                            }
                                            priFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                                            priFact.setOrgId(orgId);
                                            provAsseFactList.add(priFact);

                                        });
                            }
                        }
                        pridet.setPropfactorDtlList(provAsseFactList);
                    }
                });
    }

    @Override
    @Transactional
    public void updatePrimayPropertyInactive(long orgId, String assNo, Long empId) {
        PropertyMastEntity entity = propertyMastRepository.getPropertyDetailsByPropNo(assNo, orgId);
        if (entity != null) {
            entity.setAssActive(MainetConstants.STATUS.INACTIVE);
            entity.setUpdatedBy(empId);
            entity.setUpdatedDate(new Date());
            propertyMastRepository.save(entity);
        }
    }

    @Override
    @Transactional
    public void updatePrimayProperty(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, long orgId, Long empId) {
        PropertyMastEntity entity = propertyMastRepository.getPropertyDetailsByPropNo(provisionalAssesmentMstDto.getAssNo(),
                orgId);
        BeanUtils.copyProperties(provisionalAssesmentMstDto, entity);
        final List<PropertyDetEntity> provAsseDetList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
            PropertyDetEntity provAsseDet = new PropertyDetEntity();
            BeanUtils.copyProperties(provDet, provAsseDet);
            provAsseDet.setTbAsPrimaryMast(entity);
            provAsseDet.setOrgId(orgId);
            provAsseDet.setCreatedBy(empId);
            provAsseDet.setLgIpMac(provisionalAssesmentMstDto.getLgIpMac());
            provAsseDet.setCreatedDate(new Date());
            provAsseDet.setAssdAssesmentDate(new Date());
            provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
            // Factor
            final List<PropertyFactorEntity> provAsseFactList = new ArrayList<>();
            provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                PropertyFactorEntity provAssFact = new PropertyFactorEntity();
                BeanUtils.copyProperties(provfact, provAssFact);
                provAssFact.setTbAsPrimaryDet(provAsseDet);
                provAssFact.setTbAsPrimaryMast(entity);
                // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                provAssFact.setCreatedBy(empId);
                provAssFact.setCreatedDate(new Date());
                provAssFact.setLgIpMac(provisionalAssesmentMstDto.getLgIpMac());
                provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                provAssFact.setOrgId(orgId);
                provAsseFactList.add(provAssFact);
            });
            provAsseDet.setPropfactorDtlList(provAsseFactList);
            provAsseDetList.add(provAsseDet);
        });
        entity.getProDetList().addAll(provAsseDetList);
        propertyMastRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PropertyMastEntity getPropertyDetailsByPropNo(String propertyNo, Long orgId) {
        return propertyMastRepository.getPropertyDetailsByPropNo(propertyNo, orgId);

    }

	@Override
	public Long getBillMethodIdByPropNo(String propNo, Long orgId) {
		 return propertyMastRepository.getBillMethodIdByPropNo(propNo, orgId);
	}

	@Override
	public List<String> getFlatNoIdByPropNo(String propNo, Long orgId) {
		 return propertyMastRepository.getFlatNoIdByPropNo(propNo, orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public PropertyMastEntity getPropertyDetailsByPropNoNFlatNo(String propertyNo, String flatNo, Long orgId) {
		return propertyMastRepository.getPropertyDetailsByPropNoNFlatNo(propertyNo, flatNo, orgId);

	}
	
	@Override
	@Transactional
	public void updatePropertyMstStatus(String propNo, String status, Long orgId) {
		propertyMastRepository.updatePropertyMasterStatus(propNo, status, orgId);
	}
	@Override
	@Transactional(readOnly = true)
	public String getPropertyDetailsByPropNoFlatNoAndOrgId(String propertyNo, String flatNo, Long orgId) {
		List<PropertyDetEntity> propDet= propertyMastRepository.getPropertyDetailsByPropNoFlatNoAndOrgId(propertyNo, flatNo, orgId);
		if(CollectionUtils.isNotEmpty(propDet))
			return propDet.get(0).getOccupierName();
		else
			return null;
		

	}
	
}
