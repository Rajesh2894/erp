package com.abm.mainet.property.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dao.IMutationIntimationDao;
import com.abm.mainet.property.domain.MutRegdetEntity;
import com.abm.mainet.property.domain.MutationRegistrationEntity;
import com.abm.mainet.property.domain.TbAsTryEntity;
import com.abm.mainet.property.dto.ClaimantDto;
import com.abm.mainet.property.dto.ExecutantsDto;
import com.abm.mainet.property.dto.MutationIntimationDto;
import com.abm.mainet.property.dto.MutationRegistrationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.repository.MutationRegistrationRepository;
import com.abm.mainet.property.repository.TbAsTryRepository;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.MutationIntimationService")
@Api(value = "/mutationIntimationService")
@Path("/mutationIntimationService")
public class MutationIntimationServiceImpl implements MutationIntimationService {

    private static final String DEPT_ID = "Department Code, ";
    private static final String VILLAGE_CODE = "village Code, ";
    private static final String KHASRA_PLOT_NO = "Kharsra/Plot No, ";
    private static final String TOTAL_AREA = "Total Area, ";
    private static final String REG_NO = "Registration No, ";
    private static final String PROP_NO = "Property no, ";
    private static final String ORGID = "orgId, ";
    private static final String CREATED_BY = "createdBy, ";
    private static final String LG_IP_MAC = "lgIpMac, ";
    private static final String LAND_TYPE = "Land Type";
    private static final String MUTATION_TYPE = "Mutation Type,";
    private static final String PROP_TYPE = "Property Type,";
    private static final String DOC_EXCU_DATE = "Document Excutaion Date,, ";
    private static final String DOC_REG_DATE = "Document Excutaion Date,, ";
    private static final String MUT_ORDER_NO = "Mutation Order No, ";
    private static final String MUT_DATE = "Mutation Date, ";
    private static final String ADRESS = "Adress, ";
    private static final String PERSON_NAME = "Person Name, ";
    private static final String MOBILE_NO = "Mobile No, ";
    private static final String FATHER_NAME = "Father Name, ";
    private static final String GENDER = "Gender, ";
    private static final String NOT_NULL = "must not be null or empty";
    private static final String CLAIMANT_LIST = "ClaimantDetailsLis, ";
    private static final String EXECU_LIST = "ExecutantsDetailsList, ";
    private static final String MUT_DOC = "Mutation Document, ";
    private static final String REG_DOC = "Registartion Document , ";

    @Autowired
    private MutationRegistrationRepository mutationRegistrationRepository;

    @Autowired
    private IMutationIntimationDao iMutationIntimationDao;

    @Autowired
    private ITryService iTryService;
    
    @Autowired
    private TbAsTryRepository tbAsTryRepository;
    
    @Autowired
	private PropertyMainBillService propertyMainBillService;
    
    @Autowired
	private IProvisionalBillService iProvisionalBillService;
    
    @Autowired
	private IAssessmentMastDao iAssessmentMastDao;

    @POST
    @Path("/saveMutationIntimation")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseEntity<?> saveMutationIntimation(@RequestBody MutationRegistrationDto mutationRegistration) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        ResponseEntity<?> ruleResponse = null;
        try {
            String errorMsg = validateInput(mutationRegistration);
            if (errorMsg.isEmpty()) {
                saveEntity(mutationRegistration);
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
                ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);

            } else {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(errorMsg);
                ruleResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

        } catch (Exception e) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            ruleResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
        return ruleResponse;
    }

    @Transactional
    private void saveEntity(MutationRegistrationDto mutationRegistration) {
        MutationRegistrationEntity entity = new MutationRegistrationEntity();
        BeanUtils.copyProperties(mutationRegistration, entity);
        List<MutRegdetEntity> regdetEntityList = new ArrayList<>();
        entity.setCreatedDate(new Date());
        Organisation org = new Organisation();
        org.setOrgid(mutationRegistration.getOrgId());
        Long mutTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(mutationRegistration.getMutationType(),
                MainetConstants.Property.propPref.TFT, mutationRegistration.getOrgId());
        Long propTypeId = null;
        Long landId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(mutationRegistration.getLandType(),
                MainetConstants.Property.propPref.LDT, mutationRegistration.getOrgId());
        Long deptId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(mutationRegistration.getLandType(),
                MainetConstants.Property.propPref.MTD, mutationRegistration.getOrgId());
        List<LookUp> propTypeList = CommonMasterUtility.getLevelData(MainetConstants.Property.propPref.PTP, 1, org);
        for (LookUp lookup : propTypeList) {
            if (lookup.getLookUpCode().equals(mutationRegistration.getPropertyType())) {
                propTypeId = lookup.getLookUpId();
                break;
            }
        }
        entity.setDpDeptid(deptId);
        entity.setMutationType(mutTypeId);
        entity.setPropertyType(propTypeId);
        entity.setLandTypeId(landId);
        mutationRegistration.getExecutantsDetailsList().forEach(excutant -> {
            MutRegdetEntity regedEntity = new MutRegdetEntity();
            BeanUtils.copyProperties(excutant, regedEntity);
            regedEntity.setRegtype(MainetConstants.Property.MutationIntimation.EXECUTANTS);
            regedEntity.setCreatedDate(new Date());
            regedEntity.setMutId(entity);
            regedEntity.setOrgId(entity.getOrgId());
            regedEntity.setCreatedBy(entity.getCreatedBy());
            regedEntity.setLgIpMac(entity.getLgIpMac());
            Long genId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(excutant.getGender(),
                    MainetConstants.GENDER, mutationRegistration.getOrgId());
            regedEntity.setGender(genId);
            regdetEntityList.add(regedEntity);
        });
        mutationRegistration.getClaimantDetailsList().forEach(excutant -> {
            MutRegdetEntity regedEntity = new MutRegdetEntity();
            BeanUtils.copyProperties(excutant, regedEntity);
            regedEntity.setRegtype(MainetConstants.Property.MutationIntimation.CLAIMANT);
            regedEntity.setMutId(entity);
            regedEntity.setCreatedDate(new Date());
            regedEntity.setOrgId(entity.getOrgId());
            regedEntity.setCreatedBy(entity.getCreatedBy());
            regedEntity.setLgIpMac(entity.getLgIpMac());
            Long genId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(excutant.getGender(),
                    MainetConstants.GENDER, mutationRegistration.getOrgId());
            regedEntity.setGender(genId);
            regdetEntityList.add(regedEntity);
        });

        entity.setMutRegdetEntityList(regdetEntityList);
        mutationRegistrationRepository.save(entity);
    }

    @Transactional
    @Override
    public List<MutationIntimationDto> getMutationIntimationGridData(MutationIntimationDto mutationIntimationDto,
            PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        List<MutationIntimationDto> dtoList = new ArrayList<>();
        List<MutationRegistrationEntity> mutRegList = iMutationIntimationDao.searchMutationIntimation(mutationIntimationDto,
                pagingDTO, gridSearchDTO);
        if (mutRegList != null && !mutRegList.isEmpty()) {
        	List<Object[]> villageList = tbAsTryRepository.findVillageList();
            mutRegList.forEach(mutReg -> {
                MutationIntimationDto dto = new MutationIntimationDto();
                dto.setRowId(mutReg.getMutId());
                dto.setPropertyno(mutReg.getPropertyno());
                dto.setCurrentOwner(mutReg.getMutRegdetEntityList().get(0).getPersonName());
                for (final Object[] listObj : villageList) {
					if (listObj[2] != null && listObj[2].equals(mutReg.getVillageCode())) {
						dto.setVillage(listObj[1].toString());
					}
				}    
                dto.setKhasraPloatNo(mutReg.getKhasraOrPlotNo());
                dto.setRegNoAndDate(mutReg.getRegistrationNo());
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    @Transactional
    @Override
    public int getcountOfSearchMutationIntimation(MutationIntimationDto mutationIntimationDto,
            PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        return iMutationIntimationDao.getcountOfSearchMutationIntimation(mutationIntimationDto, pagingDTO, gridSearchDTO);

    }

    @Transactional(readOnly = true)
    @Override
    public MutationRegistrationEntity fetchMutationRegistrationById(long mutId, Long orgId) {
        return mutationRegistrationRepository.fetchMutationRegistrationById(mutId, orgId);

    }

    @Transactional
    @Override
    public void updateMutationRegistrationByMutId(Long applicationNo, long mutId, Long orgId) {
        mutationRegistrationRepository.updateMutationRegistrationByMutId(applicationNo, mutId, orgId);
    }

    /*
     * public PropertyTransferMasterDto getPropertyTransferMasterDtoByMutationIntimation(MutationIntimationDto mutDto) {
     * PropertyTransferMasterDto transferDto = new PropertyTransferMasterDto(); return transferDto; }
     */

    @Transactional(readOnly = true)
    @Override
    public MutationIntimationDto getMutationIntimationView(long mutId, Long orgId) {

        MutationRegistrationEntity mutRegEnt = fetchMutationRegistrationById(mutId, orgId);
        MutationIntimationDto dto = new MutationIntimationDto();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        dto.setPropertyno(mutRegEnt.getPropertyno());
        dto.setRowId(mutRegEnt.getMutId());
        LookUp landType = CommonMasterUtility.getNonHierarchicalLookUpObject(mutRegEnt.getLandTypeId(), org);
        dto.setLandType(landType.getLookUpDesc());
        dto.setMutationTypeId(mutRegEnt.getMutationType());
        dto.setMutationType(CommonMasterUtility.getNonHierarchicalLookUpObject(mutRegEnt.getMutationType(), org).getLookUpDesc());
        dto.setPropertyType(CommonMasterUtility
                .getHierarchicalLookUp(mutRegEnt.getPropertyType(), org).getDescLangFirst());
        dto.setMutationDocument(mutRegEnt.getMutationDocument());
        dto.setRegistrationDocument(mutRegEnt.getRegistrationDocument());
        TbAsTryEntity tryEnt = null;
        if (landType.getLookUpCode().equals(MainetConstants.Property.LandType.NZL)
                || landType.getLookUpCode().equals(MainetConstants.Property.LandType.DIV)) {
            tryEnt = iTryService.findTryDataByRecordNoAndLandType(mutRegEnt.getVillageCode(),
                    landType.getLookUpCode());
        } else {
            tryEnt = iTryService.findTryDataByVsrNo(mutRegEnt.getVillageCode());
        }
        if (tryEnt != null) {
            dto.setDistrict(tryEnt.getTryDisName());
            dto.setTehsil(tryEnt.getTryTehsilName());
            dto.setVillage(tryEnt.getTryVillName());
        }
        dto.setKhasraPloatNo(mutRegEnt.getKhasraOrPlotNo());
        dto.setTotalArea(mutRegEnt.getTotalarea().toString());
        dto.setRegistrationNo(mutRegEnt.getRegistrationNo());
        dto.setRegistrationDate(mutRegEnt.getRegistrationDate());
        dto.setMutationOrderNo(mutRegEnt.getMutationOrderNo());
        dto.setDocExecutaionDate(mutRegEnt.getDocExecutionDate());
        dto.setMutationDate(mutRegEnt.getMutationDate());
        dto.setDocExecutaionDate(mutRegEnt.getDocExecutionDate());
        List<ClaimantDto> claimantList = new ArrayList<>();
        List<ExecutantsDto> executantList = new ArrayList<>();

        mutRegEnt.getMutRegdetEntityList().forEach(regEnt -> {
            if (regEnt.getRegtype().equals(MainetConstants.Property.MutationIntimation.CLAIMANT)) {
                ClaimantDto claDto = new ClaimantDto();
                BeanUtils.copyProperties(regEnt, claDto);
                claDto.setGender(CommonMasterUtility.getNonHierarchicalLookUpObject(regEnt.getGender(), org).getLookUpDesc());
                claDto.setGendeId(regEnt.getGender());
                claimantList.add(claDto);

            } else if (regEnt.getRegtype().equals(MainetConstants.Property.MutationIntimation.EXECUTANTS)) {
                ExecutantsDto excDto = new ExecutantsDto();
                BeanUtils.copyProperties(regEnt, excDto);
                excDto.setGender(CommonMasterUtility.getNonHierarchicalLookUpObject(regEnt.getGender(), org).getLookUpDesc());
                excDto.setGenderId(regEnt.getGender());
                executantList.add(excDto);

            }
        });
        dto.setExecutantList(executantList);
        dto.setClaimantList(claimantList);
        return dto;
    }

    @Override
    public PropertyTransferMasterDto getTansferDtoByMutIntimation(MutationIntimationDto dto) {
        List<PropertyTransferOwnerDto> propTransferOwnerList = new ArrayList<>(0);
        dto.getClaimantList().forEach(claimant -> {
            PropertyTransferOwnerDto ownerDto = new PropertyTransferOwnerDto();
            ownerDto.setOwnerName(claimant.getPersonName());
            ownerDto.setGuardianName(claimant.getFatherName());
            ownerDto.setGenderId(claimant.getGendeId());
            ownerDto.setMobileno(claimant.getMobileno());
            propTransferOwnerList.add(ownerDto);
        });
        PropertyTransferMasterDto transferdto = new PropertyTransferMasterDto();
        transferdto.setPropTransferOwnerList(propTransferOwnerList);
        transferdto.setProAssNo(dto.getPropertyno());
        transferdto.setTransferType(dto.getMutationTypeId());
        transferdto.setMutId(dto.getRowId());
        if (dto.getRegistrationDate() != null) {
            transferdto.setActualTransferDate(dto.getRegistrationDate());
        } else {
            transferdto.setActualTransferDate(dto.getMutationDate());
        }
        return transferdto;

    }

    @Override
    public String downloadMutationDocument(MutationIntimationDto dto, String docString) {
        final Base64 base64 = new Base64();
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        String fileName = dto.getPropertyno() + "_" + dto.getMutationOrderNo() + ".pdf";
        FileOutputStream fos = null;
        File file = null;
        try {
            Utility.createDirectory(Filepaths.getfilepath() + outputPath
                    + MainetConstants.FILE_PATH_SEPARATOR);

            file = new File(Filepaths.getfilepath() + outputPath
                    + MainetConstants.FILE_PATH_SEPARATOR + fileName);

            fos = new FileOutputStream(file);

            fos.write(base64.decode(docString));

            fos.close();
        } catch (final FileNotFoundException e1) {
            throw new FrameworkException(e1);
        } catch (final IOException e) {
            throw new FrameworkException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
        outputPath = outputPath + MainetConstants.FILE_PATH_SEPARATOR;

        outputPath = outputPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
                "/");
        dto.setMutationFilePath(outputPath + fileName);
        return outputPath + fileName;
    }

    private String validateInput(MutationRegistrationDto mutationRegistration) {
        final StringBuilder builder = new StringBuilder();
        if (mutationRegistration.getMutationType() != null && mutationRegistration.getOrgId() != null) {
            Long mutTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(mutationRegistration.getMutationType(),
                    MainetConstants.Property.propPref.TFT, mutationRegistration.getOrgId());
            Organisation org = new Organisation();
            org.setOrgid(mutationRegistration.getOrgId());

            LookUp mutationType = CommonMasterUtility.getNonHierarchicalLookUpObject(mutTypeId, org);

            if (mutationType != null && mutationType.getLookUpCode().equals(MainetConstants.Property.SELL)) {

                if (mutationRegistration.getRegistrationNo() == null || mutationRegistration.getRegistrationNo().isEmpty()) {
                    builder.append(REG_NO);
                }
                if (mutationRegistration.getDocExecutionDate() == null) {
                    builder.append(DOC_EXCU_DATE);
                }
                if (mutationRegistration.getRegistrationDate() == null) {
                    builder.append(DOC_REG_DATE);
                }
                if (mutationRegistration.getRegistrationDocument() == null
                        || mutationRegistration.getRegistrationDocument().isEmpty()) {
                    builder.append(REG_DOC);
                }
            }

            if (mutationRegistration.getDeptCode() != null
                    && mutationRegistration.getDeptCode().equals(MainetConstants.Property.MutationIntimation.REV_DEPT)) {
                if (mutationRegistration.getMutationOrderNo() == null || mutationRegistration.getMutationOrderNo().isEmpty()) {
                    builder.append(MUT_ORDER_NO);
                }
                if (mutationRegistration.getRegistrationDocument() == null
                        || mutationRegistration.getRegistrationDocument().isEmpty()) {
                    builder.append(MUT_DOC);
                }
                if (mutationRegistration.getMutationDate() == null) {
                    builder.append(MUT_DATE);
                }
            }

        }
        if (mutationRegistration.getRegistrationNo() == null || mutationRegistration.getRegistrationNo().isEmpty()) {
            builder.append(REG_NO);
        }
        if (mutationRegistration.getDeptCode() == null || mutationRegistration.getDeptCode().isEmpty()) {
            builder.append(DEPT_ID);
        }
        if (mutationRegistration.getVillageCode() == null || mutationRegistration.getVillageCode().isEmpty()) {
            builder.append(VILLAGE_CODE);
        }
        if (mutationRegistration.getKhasraOrPlotNo() == null || mutationRegistration.getKhasraOrPlotNo().isEmpty()) {
            builder.append(KHASRA_PLOT_NO);
        }
        if (mutationRegistration.getTotalarea() == null || mutationRegistration.getTotalarea() <= 0) {
            builder.append(TOTAL_AREA);
        }
        if (mutationRegistration.getLandType() == null || mutationRegistration.getLandType().isEmpty()) {
            builder.append(LAND_TYPE);
        }
        if (mutationRegistration.getOrgId() == null || mutationRegistration.getOrgId() <= 0) {
            builder.append(ORGID);
        }
        if (mutationRegistration.getPropertyType() == null || mutationRegistration.getKhasraOrPlotNo().isEmpty()) {
            builder.append(PROP_TYPE);
        }
        if (mutationRegistration.getPropertyno() == null || mutationRegistration.getPropertyno().isEmpty()) {
            builder.append(PROP_NO);
        }
        if (mutationRegistration.getLgIpMac() == null || mutationRegistration.getLgIpMac().isEmpty()) {
            builder.append(LG_IP_MAC);
        }
        if (mutationRegistration.getCreatedBy() == null || mutationRegistration.getCreatedBy() <= 0) {
            builder.append(CREATED_BY);
        }
        if (mutationRegistration.getRegistrationDate() == null) {
            builder.append(DOC_REG_DATE);
        }
        if (mutationRegistration.getMutationType() == null || mutationRegistration.getMutationType().isEmpty()) {
            builder.append(MUTATION_TYPE);
        }

        if (mutationRegistration.getClaimantDetailsList() == null || mutationRegistration.getClaimantDetailsList().isEmpty()) {
            builder.append(CLAIMANT_LIST);
        }

        if (mutationRegistration.getExecutantsDetailsList() == null
                || mutationRegistration.getExecutantsDetailsList().isEmpty()) {
            builder.append(EXECU_LIST);
        }
        if (mutationRegistration.getClaimantDetailsList() != null) {
            for (ClaimantDto claiDto : mutationRegistration.getClaimantDetailsList()) {
                if (claiDto.getFatherName() == null || claiDto.getFatherName().isEmpty()) {
                    builder.append(FATHER_NAME);
                }
                if (claiDto.getMobileno() == null || claiDto.getMobileno().isEmpty()) {
                    builder.append(MOBILE_NO);
                }
                if (claiDto.getPersonName() == null || claiDto.getPersonName().isEmpty()) {
                    builder.append(PERSON_NAME);
                }
                if (claiDto.getAddress() == null || claiDto.getAddress().isEmpty()) {
                    builder.append(ADRESS);
                }
                if (claiDto.getGender() == null || claiDto.getGender().isEmpty()) {
                    builder.append(GENDER);
                }
            }
        }
        if (mutationRegistration.getExecutantsDetailsList() != null) {
            for (ExecutantsDto execuDto : mutationRegistration.getExecutantsDetailsList()) {
                if (execuDto.getFatherName() == null || execuDto.getFatherName().isEmpty()) {
                    builder.append(FATHER_NAME);
                }
                if (execuDto.getMobileno() == null || execuDto.getMobileno().isEmpty()) {
                    builder.append(MOBILE_NO);
                }
                if (execuDto.getPersonName() == null || execuDto.getPersonName().isEmpty()) {
                    builder.append(PERSON_NAME);
                }
                if (execuDto.getAddress() == null || execuDto.getAddress().isEmpty()) {
                    builder.append(ADRESS);
                }
                if (execuDto.getGender() == null || execuDto.getGender().isEmpty()) {
                    builder.append(GENDER);
                }
            }

        }
        if (!builder.toString().isEmpty()) {
            builder.append(NOT_NULL);
        }

        return builder.toString();
    }

	@Override
	public List<TbBillMas> getPropertyDuesByPropNo(String propertyNo, Long orgId) {
		List<TbBillMas> billMasList = null;
		int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
		if (count > 0) {
			// From Main Bill table
			billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(propertyNo, orgId);
		} else {
			// From Provisional Bill Table
			billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(propertyNo,
					orgId);
		}
		return billMasList;
	}

}
