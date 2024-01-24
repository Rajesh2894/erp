package com.abm.mainet.common.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationElectrolWZMapping;
import com.abm.mainet.common.domain.LocationElectrolWZMappingHistory;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationMasEntityHistory;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.LocationOperationWZMappingHistory;
import com.abm.mainet.common.domain.LocationRevenueWZMapping;
import com.abm.mainet.common.domain.LocationRevenueWZMappingHistory;
import com.abm.mainet.common.domain.LocationYearDetEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.WardZoneDescDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.repository.TbAcFieldMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.dao.IOperationWZMappingDAO;
import com.abm.mainet.common.master.dto.LocElectrolWZMappingDto;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.LocRevenueWZMappingDto;
import com.abm.mainet.common.master.dto.LocationMasterUploadDto;
import com.abm.mainet.common.master.dto.LocationYearDetDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.mapper.LocationMasServiceMapper;
import com.abm.mainet.common.master.repository.ElectrolWZMappingJpaRepository;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.OperationWZMappingJpaRepository;
import com.abm.mainet.common.master.repository.RevenueWZMappingJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;

import io.swagger.annotations.Api;

@Service
@Repository
@WebService(endpointInterface = "com.abm.mainet.common.service.ILocationMasService")
@Api("/locationservice")
@Path("/locationservice")
public class LocationMasServiceImpl implements ILocationMasService {

    private static final Logger LOGGER = Logger.getLogger(LocationMasServiceImpl.class);

    @Resource
    private LocationMasJpaRepository locationMasJpaRepository;

    @Autowired
    private AuditService auditService;

    @Resource
    ElectrolWZMappingJpaRepository electrolWZMappingJpaRepository;

    @Resource
    RevenueWZMappingJpaRepository revenueWZMappingJpaRepository;

    @Resource
    OperationWZMappingJpaRepository operationWZMappingJpaRepository;

    @Resource
    private LocationMasServiceMapper tbLocationMasServiceMapper;

    @Autowired
    private IOperationWZMappingDAO iOperationWZMappingDAO;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @Autowired
    private TbDepartmentService departmentService;

    @Autowired
    private ILocationMasProvisionService locationMasProvisionService;
    @Autowired
    private TbAcFieldMasterJpaRepository tbAcFieldMasterJpaRepository;

    @Autowired
    private AccountFieldMasterService accountFieldMasterService;

    @Autowired
    private TbServicesMstService servicesMstService;

    private static Logger logger = Logger.getLogger(LocationMasServiceImpl.class);

    @Override
    @Transactional
    public TbLocationMas findById(final Long locId) {

        /**
         *
         * Did not use asssociation mapping becoz it has given LazyInitialization exception
         *
         * Used Fetch stereatergy but Hibernate not able to Fetch multiple bags simoultanosuly, We have 4 bags as association
         *
         */
        logger.info("findById(Long locId) execution starts locid=" + locId);
        final LocationMasEntity tbLocationMasEntity = locationMasJpaRepository.findOne(locId);
        final List<LocationElectrolWZMapping> electrolWZMappings = electrolWZMappingJpaRepository.findByLocationId(locId);
        final List<LocationRevenueWZMapping> revenueWZMappings = revenueWZMappingJpaRepository.findByLocationId(locId);
        final List<LocationOperationWZMapping> operationWZMappings = operationWZMappingJpaRepository.findByLocationList(locId);
        final List<LocationYearDetDto> locationYearDto = new ArrayList<>();
        tbLocationMasEntity.setLocationElectrolWZMapping(electrolWZMappings);
        tbLocationMasEntity.setLocationRevenueWZMapping(revenueWZMappings);
        tbLocationMasEntity.setLocationOperationWZMapping(operationWZMappings);
        logger.info("findById(Long locId) execution ends ");
        TbLocationMas locMaster = tbLocationMasServiceMapper.mapTbLocationMasEntityToTbLocationMas(tbLocationMasEntity);
        if (locMaster.getLocOperationWZMappingDto() != null && !locMaster.getLocOperationWZMappingDto().isEmpty()) {
            locMaster.getLocOperationWZMappingDto().forEach(wardZone -> {
				// Defect #135274 for setting regional language in setDpDeptDescReg and
				// setCodIdOperLevel2DescReg
				wardZone.setDpDeptDesc(departmentService.findById(wardZone.getDpDeptId()).getDpDeptdesc());
				wardZone.setDpDeptDescReg(departmentService.findById(wardZone.getDpDeptId()).getDpNameMar());

				if (wardZone.getCodIdOperLevel1() != null) {
					wardZone.setCodIdOperLevel1Desc(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel1(), locMaster.getOrgId())
							.getDescLangFirst());
					wardZone.setCodIdOperLevel1DescReg(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel1(), locMaster.getOrgId())
							.getDescLangSecond());
				}
				if (wardZone.getCodIdOperLevel2() != null) {
					wardZone.setCodIdOperLevel2Desc(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel2(), locMaster.getOrgId())
							.getDescLangFirst());
					wardZone.setCodIdOperLevel2DescReg(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel2(), locMaster.getOrgId())
							.getDescLangSecond());
				}
				if (wardZone.getCodIdOperLevel3() != null) {
					wardZone.setCodIdOperLevel3Desc(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel3(), locMaster.getOrgId())
							.getDescLangFirst());
					wardZone.setCodIdOperLevel3DescReg(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel3(), locMaster.getOrgId())
							.getDescLangSecond());
				}
				if (wardZone.getCodIdOperLevel4() != null) {
					wardZone.setCodIdOperLevel4Desc(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel4(), locMaster.getOrgId())
							.getDescLangFirst());
					wardZone.setCodIdOperLevel4DescReg(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel4(), locMaster.getOrgId())
							.getDescLangSecond());
				}
				if (wardZone.getCodIdOperLevel5() != null) {
					wardZone.setCodIdOperLevel5Desc(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel5(), locMaster.getOrgId())
							.getDescLangFirst());
					wardZone.setCodIdOperLevel5DescReg(CommonMasterUtility
							.getHierarchicalLookUp(wardZone.getCodIdOperLevel5(), locMaster.getOrgId())
							.getDescLangSecond());
				}

            });
            
            if (locMaster.getLocCategory() != null) {
                String locCategoryDesc = CommonMasterUtility.findLookUpDesc(MainetConstants.fieldName.LCT,
                        locMaster.getOrgId(), locMaster.getLocCategory());
                locMaster.setLocCategoryDesc(locCategoryDesc);
            }

        }
        
        if (tbLocationMasEntity.getLocYearDetEntity() != null && !tbLocationMasEntity.getLocYearDetEntity().isEmpty()) {
        	tbLocationMasEntity.getLocYearDetEntity().forEach(yearBudget -> {
        		final LocationYearDetDto yearDto = new LocationYearDetDto();
        		BeanUtils.copyProperties(yearBudget, yearDto);
        		locationYearDto.add(yearDto);
            });
        	locMaster.setYearDtos(locationYearDto);
        }
        return locMaster;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void delete(final String flag, final Long userId, final Long locId) {
        logger.info("delete(String flag,Long userId,Long locId) execution starts locid=" + locId + ",userId= " + userId
                + ",flag= " + flag);
        locationMasJpaRepository.deleteRecord(flag, userId, locId);
        TbLocationMas locatioDto = findById(locId);
        locationMasProvisionService.updateLocation(locatioDto);
        logger.info("delete(String flag,Long userId,Long locId)) execution ends ");
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<TbLocationMas> fillAllLocationMasterDetails(final Organisation org) {
        logger.info("fillAllLocationMasterDetails(Organisation org) execution starts");
        final List<TbLocationMas> locationMasDtoList = new ArrayList<>();
        List<Object[]> datalist = new ArrayList<>();
        TbLocationMas locationMasDto = null;
        datalist = locationMasJpaRepository.findAllLocationMasterListWithoutCriateria(org.getOrgid());
        for (final Object[] listObj : datalist) {
            locationMasDto = new TbLocationMas();
            if (listObj[0] != null) {
                locationMasDto.setLocId(listObj[0] == null ? null : (Long) listObj[0]);
                locationMasDto.setLocNameEng(listObj[1] == null ? null : listObj[1].toString());
                locationMasDto.setLocNameReg(listObj[2] == null ? null : listObj[2].toString());
                locationMasDto.setLocArea(listObj[3] == null ? null : listObj[3].toString());
                locationMasDto.setLocAreaReg(listObj[4] == null ? null : listObj[4].toString());
                locationMasDto.setOrgId(listObj[5] == null ? null : new Long(listObj[5].toString()));
                locationMasDto.setUserId(listObj[6] == null ? null : (Long) listObj[6]);
                locationMasDto.setLangId(listObj[7] == null ? null : (Integer) listObj[7]);
                locationMasDto.setLandmark(listObj[8] == null ? null : listObj[8].toString());
                locationMasDto.setPincode(listObj[9] == null ? null : (Long) listObj[9]);
                locationMasDto.setLocActive(listObj[10] == null ? null : (String) listObj[10]);
            }
            locationMasDtoList.add(locationMasDto);
        }
        logger.info("fillAllLocationMasterDetails(Organisation org) execution ends ");
        return locationMasDtoList;
    }

    @Override
    @Transactional
    public List<TbLocationMas> getlocationByDeptId(final Long deptId, final Long orgId) {

        logger.info("getlocationByDeptId(Long deptId, Long orgId) execution starts deptId= " + deptId + ",orgId= " + orgId);
        final List<LocationMasEntity> entities = locationMasJpaRepository.findAllLocationByDeptid(deptId, orgId);
        final List<TbLocationMas> beans = new ArrayList<>();
        TbLocationMas tbLocationMas = null;
        for (final LocationMasEntity tbLocationMasEntity : entities) {
            tbLocationMas = new TbLocationMas();
            BeanUtils.copyProperties(tbLocationMasEntity, tbLocationMas);
            beans.add(tbLocationMas);
        }
        logger.info("getlocationByDeptId(Long deptId, Long orgId) execution ends ");
        return beans;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Object[]> getLocationNameByOrgId(final Long orgId) {
        logger.info("getLocationNameByOrgId execution starts orgId= " + orgId);
        return locationMasJpaRepository.getLocationNameByOrgId(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#findByLocationId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public LocOperationWZMappingDto findOperLocationAndDeptId(final Long locId, final Long deptId) {
        logger.info("findOperLocationAndDeptId(Long locId, Long deptId) execution starts locId= " + locId + ",deptId= " + deptId);
        final LocationOperationWZMapping operationWZEntity = operationWZMappingJpaRepository.findByLocationId(locId, deptId);
        logger.info("findOperLocationAndDeptId(Long locId, Long deptId) execution ends");
        return tbLocationMasServiceMapper.mapLocationOperationWZMappingToLocOperationWZMappingDto(operationWZEntity);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#findByLocationList(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean findByLocationList(final Long locId) {
        logger.info("findByLocationList(Long locId) execution starts locId= " + locId);
        final List<LocationOperationWZMapping> locationOperationWZMappings = operationWZMappingJpaRepository
                .findByLocationList(locId);
        logger.info("findByLocationList(Long locId) execution ends");
        if (!locationOperationWZMappings.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean createLocationMas(
            final TbLocationMas bean, final Organisation organisation, FileNetApplicationClient fileNetApplicationClient) {
        logger.info("createLocationMas(TbLocationMas bean, Organisation organisation) execution starts");
        final LocationMasEntity tbLocationMasEntity = new LocationMasEntity();
        final List<LocElectrolWZMappingDto> electoralWZDto = bean.getLocElectrolWZMappingDto();
        final List<LocRevenueWZMappingDto> revenueWZDto = bean.getLocRevenueWZMappingDto();
        final List<LocOperationWZMappingDto> operationWZDto = bean.getLocOperationWZMappingDto();
        final List<LocationYearDetDto> locationYearDto = bean.getYearDtos();

        if (electoralWZDto.isEmpty()) {

        } else {

            if (electoralWZDto.get(0).isElectoralChkBox()) {
                electoralWZDto.get(0).setOrgId(organisation.getOrgid());
                electoralWZDto.get(0).setLangId(bean.getLangId());
                electoralWZDto.get(0).setUserId(bean.getUserId());
                electoralWZDto.get(0).setLmodDate(new Date());
                electoralWZDto.get(0).setLgIpMac(bean.getLgIpMac());
            } else {
                bean.setLocElectrolWZMappingDto(null);
            }

        }

        if (revenueWZDto.isEmpty()) {

        } else {

            if (revenueWZDto.get(0).isRevenueChkBox()) {
                revenueWZDto.get(0).setOrgId(organisation.getOrgid());
                revenueWZDto.get(0).setLangId(bean.getLangId());
                revenueWZDto.get(0).setUserId(bean.getUserId());
                revenueWZDto.get(0).setLmodDate(new Date());
                revenueWZDto.get(0).setLgIpMac(bean.getLgIpMac());

            } else {
                bean.setLocRevenueWZMappingDto(null);
            }

        }

        if (operationWZDto.isEmpty()) {

        } else {

            if (operationWZDto.get(0).isOpertionalChkBox()) {
                operationWZDto.get(0).setOrgId(organisation.getOrgid());
                operationWZDto.get(0).setLangId(bean.getLangId());
                operationWZDto.get(0).setUserId(bean.getUserId());
                operationWZDto.get(0).setLmodDate(new Date());
                operationWZDto.get(0).setLgIpMac(bean.getLgIpMac());
            } else {
                bean.setLocOperationWZMappingDto(null);
            }

        }

        tbLocationMasServiceMapper.mapTbLocationMasToTbLocationMasEntity(bean, tbLocationMasEntity);
        
        if (!locationYearDto.isEmpty()) {
        List<LocationYearDetEntity> locYearEntityList = new ArrayList<>();
        locationYearDto.forEach(defYear -> {
				if (defYear.getFaYearId() != null
						|| (defYear.getFinanceCodeDesc() != null
								&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
						|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
					LocationYearDetEntity locYearEntity = new LocationYearDetEntity();
					BeanUtils.copyProperties(defYear, locYearEntity);
					if (defYear.getPbId() != null) {
						locYearEntity.setUpdatedBy(bean.getUserId());
						locYearEntity.setUpdatedDate(new Date());
						locYearEntity.setLgIpMacUpd(bean.getLgIpMac());
					} else {
						locYearEntity.setCreatedBy(bean.getUserId());
						locYearEntity.setCreatedDate(new Date());
						locYearEntity.setLgIpMac(bean.getLgIpMac());
						locYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
						locYearEntity.setOrgId(bean.getOrgId());
					}
					locYearEntity.setLocId(tbLocationMasEntity);
					locYearEntityList.add(locYearEntity);
				}
		});
        tbLocationMasEntity.setLocYearDetEntity(locYearEntityList);
        }
        tbLocationMasEntity.setLocSource("O");
        tbLocationMasEntity.setLocActive(MainetConstants.Common_Constant.YES);
        tbLocationMasEntity.setLmoddate(new Date());
        tbLocationMasEntity.setOrganisation(organisation);
        LocationMasEntity locationMasEntity = locationMasJpaRepository.save(tbLocationMasEntity);

        /**
         * this service is used to create Location to GRP environment if GRP posting flag is 'Y'. if yes than only Location data
         * push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
         */
        createProvisionService(locationMasEntity);

        addAndUpdateLocationPhoto(fileNetApplicationClient, tbLocationMasEntity);
        LocationMasEntityHistory locationentityhist = new LocationMasEntityHistory();
        locationentityhist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(tbLocationMasEntity, locationentityhist);

        if (electoralWZDto.get(0).isElectoralChkBox()) {
            List<LocationElectrolWZMapping> locationElectrolWZMapping = tbLocationMasServiceMapper
                    .mapLocElectrolWZMappingDtoToLocationElectrolWZMapping(electoralWZDto);
            for (LocationElectrolWZMapping flow : locationElectrolWZMapping) {
                LocationElectrolWZMappingHistory locationElectrolmaphist = new LocationElectrolWZMappingHistory();
                locationElectrolmaphist.setLocId(tbLocationMasEntity.getLocId());
                locationElectrolmaphist.setCreatedBy(flow.getUserId());
                locationElectrolmaphist.setCreatedDate(flow.getLmodDate());
                locationElectrolmaphist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                flow.setLocewzmpId(locationMasEntity.getLocationElectrolWZMapping().get(0).getLocewzmpId());
                auditService.createHistory(flow, locationElectrolmaphist);
            }
        }

        if (operationWZDto.get(0).isOpertionalChkBox()) {
            List<LocationOperationWZMapping> locationOperationWZMapping = tbLocationMasServiceMapper
                    .mapLocOperationWZMappingDtoToLocationOperationWZMapping(operationWZDto);
            for (LocationOperationWZMapping flow : locationOperationWZMapping) {
                LocationOperationWZMappingHistory locationopermaphist = new LocationOperationWZMappingHistory();
                locationopermaphist.setLocId(tbLocationMasEntity.getLocId());
                locationopermaphist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                locationopermaphist.setCreatedBy(flow.getUserId());
                locationopermaphist.setCreatedDate(flow.getLmodDate());
                flow.setLocowzmpId(locationMasEntity.getLocationOperationWZMapping().get(0).getLocowzmpId());
                auditService.createHistory(flow, locationopermaphist);
            }
        }

        if (revenueWZDto.get(0).isRevenueChkBox()) {
            List<LocationRevenueWZMapping> locationRevenueWZMapping = tbLocationMasServiceMapper
                    .mapLocRevenueWZMappingDtoToLocationRevenueWZMapping(revenueWZDto);
            for (LocationRevenueWZMapping flow : locationRevenueWZMapping) {
                LocationRevenueWZMappingHistory locationrevenuemaphist = new LocationRevenueWZMappingHistory();
                locationrevenuemaphist.setLocId(tbLocationMasEntity.getLocId());
                locationrevenuemaphist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                locationrevenuemaphist.setUserId(flow.getUserId());
                ;
                locationrevenuemaphist.setLmodDate(flow.getLmodDate());
                flow.setLocrwzmpId(locationMasEntity.getLocationRevenueWZMapping().get(0).getLocrwzmpId());
                auditService.createHistory(flow, locationrevenuemaphist);
            }
        }
        logger.info("createLocationMas(TbLocationMas bean, Organisation organisation) execution ends");
        return true;
    }

    private void createProvisionService(LocationMasEntity locationMasEntity) {
        TbLocationMas savedLocationMasDto = new TbLocationMas();
        BeanUtils.copyProperties(locationMasEntity, savedLocationMasDto);
        savedLocationMasDto.setOrgId(locationMasEntity.getOrganisation().getOrgid());
        if (locationMasEntity.getLocCategory() != null) {
            savedLocationMasDto.setLocCategoryDesc(
                    CommonMasterUtility
                            .getNonHierarchicalLookUpObject(locationMasEntity.getLocCategory(),
                                    locationMasEntity.getOrganisation())
                            .getDescLangFirst());
        }
        locationMasProvisionService.createLocation(savedLocationMasDto);
    }

    private String getDirectory(long orgId) {
        return orgId + MainetConstants.FILE_PATH_SEPARATOR + "LOCATION MASTER" + MainetConstants.FILE_PATH_SEPARATOR
                + new Date().getTime();

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean createLocationMasEdit(
            final TbLocationMas bean, final Organisation organisation, FileNetApplicationClient fileNetApplicationClient) {
        logger.info("createLocationMasEdit(TbLocationMas bean, Organisation organisation) execution starts");
        final LocationMasEntity tbLocationMasEntity = new LocationMasEntity();
        final List<LocElectrolWZMappingDto> electoralWZDto = bean.getLocElectrolWZMappingDto();
        final List<LocRevenueWZMappingDto> revenueWZDto = bean.getLocRevenueWZMappingDto();
        final List<LocOperationWZMappingDto> operationWZDto = bean.getLocOperationWZMappingDto();
        final List<LocationYearDetDto> locationYearDto = bean.getYearDtos();

        if (electoralWZDto.isEmpty()) {

        } else {

            if (electoralWZDto.get(0).isElectoralChkBox()) {
                if (electoralWZDto.get(0).getLocewzmpId() == null) {
                    electoralWZDto.get(0).setOrgId(organisation.getOrgid());
                    electoralWZDto.get(0).setLangId(bean.getLangId());
                    electoralWZDto.get(0).setUserId(bean.getUpdatedBy());
                    electoralWZDto.get(0).setLmodDate(new Date());
                    electoralWZDto.get(0).setLgIpMac(bean.getLgIpMac());
                } else {
                    electoralWZDto.get(0).setUpdatedBy(bean.getUpdatedBy());
                    electoralWZDto.get(0).setUpdatedDate(new Date());
                }

            } else {
                bean.setLocElectrolWZMappingDto(null);
            }

        }

        if (revenueWZDto.isEmpty()) {

        } else {

            if (revenueWZDto.get(0).isRevenueChkBox()) {
                if (revenueWZDto.get(0).getLocrwzmpId() == null) {
                    revenueWZDto.get(0).setOrgId(organisation.getOrgid());
                    revenueWZDto.get(0).setLangId(bean.getLangId());
                    revenueWZDto.get(0).setUserId(bean.getUpdatedBy());
                    revenueWZDto.get(0).setLmodDate(new Date());
                    revenueWZDto.get(0).setLgIpMac(bean.getLgIpMac());
                } else {
                    revenueWZDto.get(0).setUpdatedBy(bean.getUpdatedBy());
                    revenueWZDto.get(0).setUpdatedDate(new Date());
                }

            } else {
                bean.setLocRevenueWZMappingDto(null);
            }

        }

        if (operationWZDto.isEmpty()) {

        } else {

            if (operationWZDto.get(0).isOpertionalChkBox()) {
                if (operationWZDto.get(0).getLocowzmpId() == null) {
                    operationWZDto.get(0).setOrgId(organisation.getOrgid());
                    operationWZDto.get(0).setLangId(bean.getLangId());
                    operationWZDto.get(0).setUserId(bean.getUpdatedBy());
                    operationWZDto.get(0).setLmodDate(new Date());
                    operationWZDto.get(0).setLgIpMac(bean.getLgIpMac());
                } else {
                    operationWZDto.get(0).setUpdatedBy(bean.getUpdatedBy());
                    operationWZDto.get(0).setUpdatedDate(new Date());
                }
            } else {
                bean.setLocOperationWZMappingDto(null);
            }

        }

        tbLocationMasServiceMapper.mapTbLocationMasToTbLocationMasEntity(bean, tbLocationMasEntity);
        
        if (!locationYearDto.isEmpty()) {
            List<LocationYearDetEntity> locYearEntityList = new ArrayList<>();
            locationYearDto.forEach(defYear -> {
    				if (defYear.getFaYearId() != null
    						|| (defYear.getFinanceCodeDesc() != null
    								&& !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
    						|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
    					LocationYearDetEntity locYearEntity = new LocationYearDetEntity();
    					BeanUtils.copyProperties(defYear, locYearEntity);
    					if (defYear.getPbId() != null) {
    						locYearEntity.setUpdatedBy(bean.getUserId());
    						locYearEntity.setUpdatedDate(new Date());
    						locYearEntity.setLgIpMacUpd(bean.getLgIpMac());
    					} else {
    						locYearEntity.setCreatedBy(bean.getUserId());
    						locYearEntity.setCreatedDate(new Date());
    						locYearEntity.setLgIpMac(bean.getLgIpMac());
    						locYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
    						locYearEntity.setOrgId(bean.getOrgId());
    					}
    					locYearEntity.setLocId(tbLocationMasEntity);
    					locYearEntityList.add(locYearEntity);
    				}
    		});
            tbLocationMasEntity.setLocYearDetEntity(locYearEntityList);
            }
        LocationMasEntity locationMasEntity = locationMasJpaRepository.save(tbLocationMasEntity);

        /**
         * this service is used to update Location to GRP environment if GRP posting flag is 'Y'. if yes than only Location data
         * push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
         */
        TbLocationMas savedLocationMasDto = new TbLocationMas();
        BeanUtils.copyProperties(locationMasEntity, savedLocationMasDto);
        savedLocationMasDto.setOrgId(locationMasEntity.getOrganisation().getOrgid());
        if (locationMasEntity.getLocCategory() != null) {
            savedLocationMasDto.setLocCategoryDesc(
                    CommonMasterUtility
                            .getNonHierarchicalLookUpObject(locationMasEntity.getLocCategory(),
                                    locationMasEntity.getOrganisation())
                            .getDescLangFirst());
        }

        locationMasProvisionService.updateLocation(savedLocationMasDto);

        addAndUpdateLocationPhoto(fileNetApplicationClient, tbLocationMasEntity);

        LocationMasEntityHistory locationentityhist = new LocationMasEntityHistory();
        locationentityhist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(tbLocationMasEntity, locationentityhist);

        if (electoralWZDto.get(0).isElectoralChkBox()) {
            List<LocationElectrolWZMapping> locationElectrolWZMapping = tbLocationMasServiceMapper
                    .mapLocElectrolWZMappingDtoToLocationElectrolWZMapping(electoralWZDto);
            for (LocationElectrolWZMapping flow : locationElectrolWZMapping) {
                LocationElectrolWZMappingHistory locationElectrolmaphist = new LocationElectrolWZMappingHistory();
                locationElectrolmaphist.setLocId(bean.getLocId());
                locationElectrolmaphist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                flow.setUpdatedDate(bean.getUpdatedDate());
                flow.setUpdatedBy(bean.getUpdatedBy());
                flow.setLocewzmpId(locationMasEntity.getLocationElectrolWZMapping().get(0).getLocewzmpId());
                auditService.createHistory(flow, locationElectrolmaphist);
            }
        }

        if (operationWZDto.get(0).isOpertionalChkBox()) {
            List<LocationOperationWZMapping> locationOperationWZMapping = tbLocationMasServiceMapper
                    .mapLocOperationWZMappingDtoToLocationOperationWZMapping(operationWZDto);
            for (LocationOperationWZMapping flow : locationOperationWZMapping) {
                LocationOperationWZMappingHistory locationopermaphist = new LocationOperationWZMappingHistory();
                locationopermaphist.setLocId(bean.getLocId());
                locationopermaphist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                locationopermaphist.setCreatedBy(flow.getUserId());
                locationopermaphist.setCreatedDate(flow.getLmodDate());
                flow.setUpdatedDate(bean.getUpdatedDate());
                flow.setUpdatedBy(bean.getUserId());
                flow.setLocowzmpId(locationMasEntity.getLocationOperationWZMapping().get(0).getLocowzmpId());
                auditService.createHistory(flow, locationopermaphist);
            }
        }

        if (revenueWZDto.get(0).isRevenueChkBox()) {
            List<LocationRevenueWZMapping> locationRevenueWZMapping = tbLocationMasServiceMapper
                    .mapLocRevenueWZMappingDtoToLocationRevenueWZMapping(revenueWZDto);
            for (LocationRevenueWZMapping flow : locationRevenueWZMapping) {
                LocationRevenueWZMappingHistory locationrevenuemaphist = new LocationRevenueWZMappingHistory();
                locationrevenuemaphist.setLocId(bean.getLocId());
                locationrevenuemaphist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                flow.setLocrwzmpId(locationMasEntity.getLocationRevenueWZMapping().get(0).getLocrwzmpId());
                flow.setUpdatedDate(bean.getUpdatedDate());
                flow.setUpdatedBy(bean.getUserId());
                auditService.createHistory(flow, locationrevenuemaphist);
            }
        }
        logger.info("createLocationMasEdit(TbLocationMas bean, Organisation organisation) execution ends");
        return true;
    }

    private void addAndUpdateLocationPhoto(
            FileNetApplicationClient fileNetApplicationClient, final LocationMasEntity tbLocationMasEntity) {
        List<File> list = null;
        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
                && !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
            if (tbLocationMasEntity.getLocId() != null && tbLocationMasEntity.getLocId() > 0d) {
                iAttachDocsService.updateMasterDocumentStatus("LOC" + tbLocationMasEntity.getLocId(), MainetConstants.FlagD);
            }
            List<AttachDocs> attachment = new ArrayList<>();
            AttachDocs doc = null;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                list = new ArrayList<>(entry.getValue());
                final Iterator<File> setFilesItr = entry.getValue().iterator();
                String tempDirPath = null;
                while (setFilesItr.hasNext()) {
                    doc = new AttachDocs();
                    final File file = setFilesItr.next();
                    tempDirPath = getDirectory(tbLocationMasEntity.getOrganisation().getOrgid());
                    doc.setAttDate(new Date());
                    doc.setAttFname(file.getName());
                    doc.setAttPath(tempDirPath);
                    doc.setIdfId("LOC" + tbLocationMasEntity.getLocId());
                    doc.setOrgid(tbLocationMasEntity.getOrganisation().getOrgid());
                    doc.setUserId(tbLocationMasEntity.getUserId());
                    doc.setLmodate(new Date());
                    doc.setLgIpMac(tbLocationMasEntity.getLgIpMac());
                    doc.setStatus(MainetConstants.FlagA);
                    attachment.add(doc);
                    try {
                        fileNetApplicationClient.uploadFileList(list, tempDirPath);
                        iAttachDocsService.saveMasterDocuments(attachment);
                    } catch (final Exception e) {
                        throw new FrameworkException(e);
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#getlocationByPinCode(java.lang.
     * Integer)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<TbLocationMas> getlocationByPinCode(final Long pinCode) {
        List<TbLocationMas> masList = new ArrayList<>();
        List<LocationMasEntity> entityList = locationMasJpaRepository.getlocationByPinCode(pinCode);
        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                TbLocationMas masDto = new TbLocationMas();
                BeanUtils.copyProperties(entity, masDto);
                masList.add(masDto);
            });
        }
        return masList;

    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#findByWardZoneAndBlock(java.lang.
     * Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<LocationMasEntity> findByOrgAndDepartmentAndWardZone(final Long orgId, final Long deptId,
            final Long codIdOperLevel1,
            final Long codIdOperLevel2, final Long codIdOperLevel3, final Long codIdOperLevel4, final Long codIdOperLevel5) {
        logger.info("findByWardZoneAndBlock(Long wardID,Long zoneID, Long blockId) execution starts");
        final List<LocationMasEntity> locationsListByWardZoneBlock = iOperationWZMappingDAO
                .findLocationOperationWZMapping(orgId, deptId, codIdOperLevel1, codIdOperLevel2, codIdOperLevel3,
                        codIdOperLevel4, codIdOperLevel5);
        logger.info("findByWardZoneAndBlock(Long wardID,Long zoneID, Long blockId) execution ends");
        return locationsListByWardZoneBlock;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#findByWardZoneAndBlock(java.lang.
     * Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public LocationOperationWZMapping findbyLocationAndDepartment(final Long locationId, final Long deptId) {
        LocationOperationWZMapping locationOperationWZMapping = null;
        try {
            locationOperationWZMapping = locationMasJpaRepository.findbyLocationAndDepartment(
                    locationId,
                    deptId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return locationOperationWZMapping;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.masters.locationmaster.business.service.ILocationMasService#findByDepartmentWardZoneAndBlock(java
     * .lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<LocationOperationWZMapping> findByDepartmentWardZoneAndBlock(
            final Long deptId, final Long wardID, final Long zoneID, final Long blockId) {
        logger.info("findByDepartmentWardZoneAndBlock(Long deptId,Long wardID,Long zoneID, Long blockId) execution starts");
        final List<LocationOperationWZMapping> locationsListByDepartmentWardZoneBlock = locationMasJpaRepository
                .findByDepartmentWardZoneAndBlock(deptId, wardID, zoneID, blockId);
        logger.info("findByDepartmentWardZoneAndBlock(Long deptId,Long wardID,Long zoneID, Long blockId) execution ends");
        return locationsListByDepartmentWardZoneBlock;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Long getRevenueLocationId(final long areaDivision1, final Long orgId) {
        return revenueWZMappingJpaRepository.getRevenueLocationId(areaDivision1, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Long fetchEmployeeLocation(final Long empId, final Long orgId) {
        return locationMasJpaRepository.fetchEmployeeLocation(empId, orgId);
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public LocationMasEntity createDefaultLocationMas(final LocationMasEntity entity,
            final Organisation organisation) {
        entity.setOrganisation(organisation);
        final LocationMasEntity locationMasSaved = locationMasJpaRepository.save(entity);
        createProvisionService(locationMasSaved);
        return locationMasSaved;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<LocationMasEntity> getlocationByOrgId(final Long orgId) {
        final List<LocationMasEntity> locList = locationMasJpaRepository.getLocationByOrgId(orgId);
        return locList;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Object[]> getdefaultLocWithOtherLocByOrgId(final String empName, final Long orgId) {
        final List<Object[]> locList = locationMasJpaRepository.getdefaultLocWithOtherLocByOrgId(empName, orgId);
        return locList;
    }

    @Override
    @WebMethod(exclude = true)
    public LocationMasEntity findbyLocationId(final Long locId) {
        final LocationMasEntity tbLocationMasEntity = locationMasJpaRepository.findbyLocationId(locId);
        return tbLocationMasEntity;
    }

    @Override
    @WebMethod(exclude = true)
    public List<Long> findLocations(final Long orgId, final Long deptId, final Long fLevel, final Long sLevel, final Long tLevel,
            final Long foLevel,
            final Long fiLevel) {
        // TODO Auto-generated method stub
        final List<Long> locationIds = iOperationWZMappingDAO.findLocations(orgId, deptId, fLevel, sLevel, tLevel, foLevel,
                fiLevel);
        return locationIds;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<WardZoneDTO> findlocationByOrgId(final Long orgId) {
        final List<LocationMasEntity> locList = locationMasJpaRepository.getLocationByOrgId(orgId);

        final List<WardZoneDTO> WardZoneDTOList = new ArrayList<>();
        WardZoneDTO wardZoneDTO = null;
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        for (final LocationMasEntity entity : locList) {
            wardZoneDTO = new WardZoneDTO();
            wardZoneDTO.setLocationId(entity.getLocId());
            wardZoneDTO.setLocationName(entity.getLocNameEng());
            final List<LocationElectrolWZMapping> electrolWZMappings = electrolWZMappingJpaRepository
                    .findByLocationId(entity.getLocId());
            if ((electrolWZMappings != null) && !electrolWZMappings.isEmpty()) {
                final LocationElectrolWZMapping mapping = electrolWZMappings.get(0);
                wardZoneDTO.setWordId(mapping.getCodIdElecLevel1());
                wardZoneDTO.setZoneId(mapping.getCodIdElecLevel2());
                wardZoneDTO.setBlockId(mapping.getCodIdElecLevel3());
                if (mapping.getCodIdElecLevel1() != null) {
                    wardZoneDTO.setWardName(CommonMasterUtility.getHierarchicalLookUp(mapping.getCodIdElecLevel1(), organisation)
                            .getDescLangFirst());
                }
                if (mapping.getCodIdElecLevel2() != null) {
                    wardZoneDTO.setZoneName(CommonMasterUtility.getHierarchicalLookUp(mapping.getCodIdElecLevel2(), organisation)
                            .getDescLangFirst());
                }
                if (mapping.getCodIdElecLevel3() != null) {
                    wardZoneDTO.setBlockName(CommonMasterUtility.getHierarchicalLookUp(mapping.getCodIdElecLevel3(), organisation)
                            .getDescLangFirst());
                }
            }
            WardZoneDTOList.add(wardZoneDTO);
        }
        return WardZoneDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public int validateLocationNameAndArea(final String locName, final String locArea, Long orgId) {
        int count = locationMasJpaRepository.findLocationNameAndAre(locName, locArea, orgId);
        return count;
    }

    @Override
    @WebMethod(exclude = true)
    public Long getcodIdRevLevel1ByLocId(final Long locId, final Long orgId) {
        return revenueWZMappingJpaRepository.getcodIdRevLevel1ByLocId(locId, orgId);
    }

    @Override
    @WebMethod(exclude = true)
    public List<LocationMasEntity> findAllLocationWithOperationWZMapping(final Long orgId, final Long deptId) {
        return locationMasJpaRepository.findAllLocationWithOperationWZMapping(orgId, deptId);
    }

    @Override
    @WebMethod(exclude = true)
    public String getLocationNameById(final Long locId, final Long orgId) {
        return locationMasJpaRepository.getLocationNameById(locId, orgId);
    }

    @Override
    @WebMethod(exclude = true)
    public List<LocationMasEntity> findWZMappedLocationByOrgIdAndDeptId(final Long orgId, final Long deptId) {
        return operationWZMappingJpaRepository.findWZMappedLocationByOrgIdAndDeptId(orgId, deptId);
    }

    @Override
    @WebMethod(exclude = true)
    public AttachDocs fetchLocationImage(Long orgid, Long locId) {
        AttachDocs document = null;
        List<AttachDocs> docs = iAttachDocsService.findByCode(orgid, "LOC" + locId);
        if (docs != null && !docs.isEmpty()) {
            document = docs.get(0);
        }
        return document;
    }

    /**
     * Add Service for fetch All active location master DTO list based on organization Id.
     * @param orgId
     * @return List<TbLocationMas>
     */
    @Override
    public List<TbLocationMas> fillAllActiveLocationByOrgId(final Long orgId) {
        List<TbLocationMas> masList = new ArrayList<>();
        List<LocationMasEntity> entityList = locationMasJpaRepository.fillAllActiveLocationByOrgId(orgId);
        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                TbLocationMas masDto = new TbLocationMas();
                BeanUtils.copyProperties(entity, masDto);
                masList.add(masDto);
            });
        }
        return masList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public TbLocationMas findByLocationName(final String locName, final Long orgId) {

        final LocationMasEntity tbLocationMasEntity = locationMasJpaRepository.findByLocationName(locName, orgId);
        final List<LocationElectrolWZMapping> electrolWZMappings = electrolWZMappingJpaRepository
                .findByLocationId(tbLocationMasEntity.getLocId());
        final List<LocationRevenueWZMapping> revenueWZMappings = revenueWZMappingJpaRepository
                .findByLocationId(tbLocationMasEntity.getLocId());
        final List<LocationOperationWZMapping> operationWZMappings = operationWZMappingJpaRepository
                .findByLocationList(tbLocationMasEntity.getLocId());
        tbLocationMasEntity.setLocationElectrolWZMapping(electrolWZMappings);
        tbLocationMasEntity.setLocationRevenueWZMapping(revenueWZMappings);
        tbLocationMasEntity.setLocationOperationWZMapping(operationWZMappings);
        return tbLocationMasServiceMapper.mapTbLocationMasEntityToTbLocationMas(tbLocationMasEntity);
    }

    @Override
    public List<TbLocationMas> findlAllLocationByLocationCategoryAndOrgId(Long locCategory, Long orgId) {
        List<TbLocationMas> masList = new ArrayList<>();
        List<LocationMasEntity> entityList = locationMasJpaRepository.findlAllLocationByLocationCategoryAndOrgId(locCategory,
                orgId);
        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                TbLocationMas masDto = new TbLocationMas();
                BeanUtils.copyProperties(entity, masDto);
                masList.add(masDto);
            });
        }
        return masList;
    }

    @Override
    public List<WardZoneDescDTO> fetchOperationalWard(Long orgId, Long deptId, Long locId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<WardZoneDescDTO> wardZoneList = new ArrayList<>(0);
        LocOperationWZMappingDto operWZDto = findOperLocationAndDeptId(locId, deptId);
        final String prefixName = departmentService.findDepartmentPrefixName(deptId, orgId);
        if (operWZDto != null) {
            final List<LookUp> lookupList = ApplicationSession.getInstance()
                    .getHierarchicalLookUp(org, prefixName).get(prefixName);
            if (operWZDto.getCodIdOperLevel1() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(operWZDto.getCodIdOperLevel1(), org)
                                .getDescLangFirst(),
                        lookupList.get(0).getDescLangFirst()));
            }
            if (operWZDto.getCodIdOperLevel2() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(operWZDto.getCodIdOperLevel2(), org)
                                .getDescLangFirst(),
                        lookupList.get(1).getDescLangFirst()));
            }
            if (operWZDto.getCodIdOperLevel3() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(operWZDto.getCodIdOperLevel3(), org)
                                .getDescLangFirst(),
                        lookupList.get(2).getDescLangFirst()));
            }
            if (operWZDto.getCodIdOperLevel4() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(operWZDto.getCodIdOperLevel4(), org)
                                .getDescLangFirst(),
                        lookupList.get(3).getDescLangFirst()));
            }
            if (operWZDto.getCodIdOperLevel5() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(operWZDto.getCodIdOperLevel5(), org)
                                .getDescLangFirst(),
                        lookupList.get(4).getDescLangFirst()));
            }
        }

        return wardZoneList;
    }

    private WardZoneDescDTO setWardZoneDto(String desc, String label) {
        WardZoneDescDTO dto = new WardZoneDescDTO();
        dto.setWardZoneLabel(label);
        dto.setWardZoneDesc(desc);
        return dto;
    }

    @Override
    public List<WardZoneDescDTO> fetchRevenueWard(Long orgId, Long deptId, Long locId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<WardZoneDescDTO> wardZoneList = new ArrayList<>(0);
        final List<LocationRevenueWZMapping> revenueWZMappings = revenueWZMappingJpaRepository.findByLocationId(locId);
        if (revenueWZMappings != null && !revenueWZMappings.isEmpty()) {
            LocationRevenueWZMapping revenueward = revenueWZMappings.get(0);
            final AccountFieldMasterEntity field = tbAcFieldMasterJpaRepository.findOne(revenueward.getCodIdRevLevel1());
            if (revenueward.getCodIdRevLevel1() != null) {
                wardZoneList.add(setWardZoneDto(
                        field.getFieldCompcode() + MainetConstants.SEPARATOR + field.getFieldDesc(),
                        ApplicationSession.getInstance().getMessage("location.fieldMapping")));
            }
        }
        return wardZoneList;
    }

    @Override
    public List<WardZoneDescDTO> fetchElectoralWard(Long orgId, Long deptId, Long locId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<WardZoneDescDTO> wardZoneList = new ArrayList<>(0);
        final List<LocationElectrolWZMapping> electrolWZMappings = electrolWZMappingJpaRepository.findByLocationId(locId);
        if (electrolWZMappings != null && !electrolWZMappings.isEmpty()) {
            LocationElectrolWZMapping electrolward = electrolWZMappings.get(0);
            final List<LookUp> lookupList = ApplicationSession.getInstance()
                    .getHierarchicalLookUp(org, "EWZ").get("EWZ");
            if (electrolward.getCodIdElecLevel1() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(electrolward.getCodIdElecLevel1(), org)
                                .getDescLangFirst(),
                        lookupList.get(0).getDescLangFirst()));
            }
            if (electrolward.getCodIdElecLevel2() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(electrolward.getCodIdElecLevel2(), org)
                                .getDescLangFirst(),
                        lookupList.get(1).getDescLangFirst()));
            }
            if (electrolward.getCodIdElecLevel3() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(electrolward.getCodIdElecLevel3(), org)
                                .getDescLangFirst(),
                        lookupList.get(2).getDescLangFirst()));
            }
            if (electrolward.getCodIdElecLevel4() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(electrolward.getCodIdElecLevel4(), org)
                                .getDescLangFirst(),
                        lookupList.get(3).getDescLangFirst()));
            }
            if (electrolward.getCodIdElecLevel5() != null) {
                wardZoneList.add(setWardZoneDto(
                        CommonMasterUtility.getHierarchicalLookUp(electrolward.getCodIdElecLevel5(), org)
                                .getDescLangFirst(),
                        lookupList.get(4).getDescLangFirst()));
            }
        }
        return wardZoneList;
    }

    @Override
    public List<TbLocationMas> getAllLocationByOrgId(Long orgId) {
        List<TbLocationMas> masList = new ArrayList<>();
        List<LocationMasEntity> entityList = locationMasJpaRepository.getAllLocationByOrgId(orgId);
        Set<LocationMasEntity> set = new HashSet<>(entityList);
        entityList.clear();
        entityList.addAll(set);
        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                TbLocationMas masDto = new TbLocationMas();
                BeanUtils.copyProperties(entity, masDto);
                masList.add(masDto);
            });
        }
        return masList;

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<TbLocationMas> findAllLocationsForOrg(final Organisation org) {
        logger.info("findAllLocationsForOrg(Organisation org) execution starts");
        final List<TbLocationMas> locationMasDtoList = new ArrayList<>();
        List<LocationMasEntity> datalist = new ArrayList<>();
        datalist = locationMasJpaRepository.findAllLocations(org.getOrgid());
        datalist.forEach(locationMasEntity -> {
            if (locationMasEntity != null) {
                TbLocationMas tbLocationMas = tbLocationMasServiceMapper.mapTbLocationMasEntityToTbLocationMas(locationMasEntity);
                locationMasDtoList.add(tbLocationMas);
            }
        });
        logger.info("findAllLocationsForOrg(Organisation org) execution ends ");
        return locationMasDtoList;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void saveLocationMasterExcelData(List<LocationMasterUploadDto> locationMasterUploadDtoList,
            TbLocationMas tbLocationMas, Organisation org) {
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        LocElectrolWZMappingDto electrolWZMappingDto = new LocElectrolWZMappingDto();
        LocRevenueWZMappingDto revenueWZMappingDto = new LocRevenueWZMappingDto();
        LocOperationWZMappingDto operationWZMappingDto = new LocOperationWZMappingDto();
        tbLocationMas.getLocElectrolWZMappingDto().add(electrolWZMappingDto);
        tbLocationMas.getLocRevenueWZMappingDto().add(revenueWZMappingDto);
        tbLocationMas.getLocOperationWZMappingDto().add(operationWZMappingDto);
        // Delete currently uploaded XL file, else it will be considered as location image document.
        FileUploadUtility.getCurrent().deleteFile("0_file_0");

        locationMasterUploadDtoList.forEach(locationMasterUploadDto -> {
            locationMasterUploadDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            locationMasterUploadDto.setLangId(Integer.valueOf(langId));
            locationMasterUploadDto.setUserId(userId);
            locationMasterUploadDto.setLmoddate(new Date());
            locationMasterUploadDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

            if (tbLocationMas.getLocElectrolWZMappingDto() != null) {
                tbLocationMas.getLocElectrolWZMappingDto().set(0, electrolWZMappingDto);
            } else {
                List<LocElectrolWZMappingDto> locElectrolWZMappingDto = new ArrayList<>();
                locElectrolWZMappingDto.add(electrolWZMappingDto);
                tbLocationMas.setLocElectrolWZMappingDto(locElectrolWZMappingDto);
            }
            if (tbLocationMas.getLocRevenueWZMappingDto() != null) {
                tbLocationMas.getLocRevenueWZMappingDto().set(0, revenueWZMappingDto);
            } else {
                List<LocRevenueWZMappingDto> locRevenueWZMappingDto = new ArrayList<>();
                locRevenueWZMappingDto.add(revenueWZMappingDto);
                tbLocationMas.setLocRevenueWZMappingDto(locRevenueWZMappingDto);
            }
            if (tbLocationMas.getLocOperationWZMappingDto() != null) {
                tbLocationMas.getLocOperationWZMappingDto().set(0, operationWZMappingDto);
            } else {
                List<LocOperationWZMappingDto> locOperationWZMappingDto = new ArrayList<>();
                locOperationWZMappingDto.add(operationWZMappingDto);
                tbLocationMas.setLocOperationWZMappingDto(locOperationWZMappingDto);
            }

            BeanUtils.copyProperties(locationMasterUploadDto, tbLocationMas);
            tbLocationMas.setDeptLoc(locationMasterUploadDto.getDeptLoc().charAt(0));
            tbLocationMas.setLocCategory(Long.parseLong(locationMasterUploadDto.getLocCategory()));

            tbLocationMas.getLocRevenueWZMappingDto().get(0).setRevenueChkBox(locationMasterUploadDto.isRevenueChkBox());
            tbLocationMas.getLocRevenueWZMappingDto().get(0)
                    .setCodIdRevLevel1(locationMasterUploadDto.getCodIdRevLevel1() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdRevLevel1())
                            : null);

            tbLocationMas.getLocElectrolWZMappingDto().get(0).setElectoralChkBox(locationMasterUploadDto.isElectoralChkBox());
            tbLocationMas.getLocElectrolWZMappingDto().get(0)
                    .setCodIdElecLevel1(locationMasterUploadDto.getCodIdElecLevel1() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdElecLevel1())
                            : null);
            tbLocationMas.getLocElectrolWZMappingDto().get(0)
                    .setCodIdElecLevel2(locationMasterUploadDto.getCodIdElecLevel2() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdElecLevel2())
                            : null);
            tbLocationMas.getLocElectrolWZMappingDto().get(0)
                    .setCodIdElecLevel3(locationMasterUploadDto.getCodIdElecLevel3() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdElecLevel3())
                            : null);
            tbLocationMas.getLocElectrolWZMappingDto().get(0)
                    .setCodIdElecLevel4(locationMasterUploadDto.getCodIdElecLevel4() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdElecLevel4())
                            : null);
            tbLocationMas.getLocElectrolWZMappingDto().get(0)
                    .setCodIdElecLevel5(locationMasterUploadDto.getCodIdElecLevel5() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdElecLevel5())
                            : null);

            tbLocationMas.getLocOperationWZMappingDto().get(0).setOpertionalChkBox(locationMasterUploadDto.isOpertionalChkBox());
            tbLocationMas.getLocOperationWZMappingDto().get(0)
                    .setCodIdOperLevel1(locationMasterUploadDto.getCodIdOperLevel1() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdOperLevel1())
                            : null);
            tbLocationMas.getLocOperationWZMappingDto().get(0)
                    .setCodIdOperLevel2(locationMasterUploadDto.getCodIdOperLevel2() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdOperLevel2())
                            : null);
            tbLocationMas.getLocOperationWZMappingDto().get(0)
                    .setCodIdOperLevel3(locationMasterUploadDto.getCodIdOperLevel3() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdOperLevel3())
                            : null);
            tbLocationMas.getLocOperationWZMappingDto().get(0)
                    .setCodIdOperLevel4(locationMasterUploadDto.getCodIdOperLevel4() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdOperLevel4())
                            : null);
            tbLocationMas.getLocOperationWZMappingDto().get(0)
                    .setCodIdOperLevel5(locationMasterUploadDto.getCodIdOperLevel5() != null
                            ? Long.parseLong(locationMasterUploadDto.getCodIdOperLevel5())
                            : null);
            tbLocationMas.getLocOperationWZMappingDto().get(0).setDpDeptId(locationMasterUploadDto.getDpDeptId());
            tbLocationMas.getLocOperationWZMappingDto().get(0).setDpDeptDesc(locationMasterUploadDto.getDpDeptDesc());

            createLocationMas(tbLocationMas, org, FileNetApplicationClient.getInstance());
        });

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<LocationMasterUploadDto> exportAllLocationsForOrg(Organisation org) {
        List<LocationMasterUploadDto> locationMasterUploadDtos = new ArrayList<>();
        List<TbLocationMas> locationMasDtoList = findAllLocationsForOrg(org);
        if (locationMasDtoList != null && !locationMasDtoList.isEmpty()) {

            List<LookUp> locCategory = CommonMasterUtility.getLookUps(MainetConstants.fieldName.LCT, org);
            Map<Integer, List<LookUp>> elecLookupsMap = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                try {
                    List<LookUp> lookUps = CommonMasterUtility.getLevelData(PrefixConstants.prefixName.ElectrolWZ, i, org);
                    elecLookupsMap.put(i, lookUps);
                } catch (Exception e) {

                }
            }
            Map<Long, String> revenueMap = accountFieldMasterService.getFieldMasterLastLevelsForLocation(org.getOrgid());

            locationMasDtoList.forEach(locationMasDto -> {
                LocationMasterUploadDto locationMasterUploadDto = new LocationMasterUploadDto();
                BeanUtils.copyProperties(locationMasDto, locationMasterUploadDto);
                locationMasterUploadDto
                        .setDeptLoc(locationMasDto.getDeptLoc() != null ? locationMasDto.getDeptLoc().toString() : null);
                for (LookUp list : locCategory) {
                    if (Objects.equals(locationMasDto.getLocCategory(), list.getLookUpId())) {
                        locationMasterUploadDto.setLocCategory(list.getDescLangFirst());
                        break;
                    }
                }

                List<LocElectrolWZMappingDto> electrolWZMappingDtos = locationMasDto.getLocElectrolWZMappingDto();
                List<LocRevenueWZMappingDto> locRevenueWZMappingDtos = locationMasDto.getLocRevenueWZMappingDto();
                List<LocOperationWZMappingDto> locOperationWZMappingDtos = locationMasDto.getLocOperationWZMappingDto();
                if (electrolWZMappingDtos != null && !electrolWZMappingDtos.isEmpty()) {
                    locationMasterUploadDto.setIsAreaMappingElectoral("Y");

                    int size = elecLookupsMap.size();
                    if (size > 0) {
                        List<LookUp> elecLevel1List = elecLookupsMap.get(1);
                        List<LookUp> elecLevel2List = elecLookupsMap.get(2);
                        List<LookUp> elecLevel3List = elecLookupsMap.get(3);
                        List<LookUp> elecLevel4List = elecLookupsMap.get(4);
                        List<LookUp> elecLevel5List = elecLookupsMap.get(5);

                        if (elecLevel1List != null) {
                            for (LookUp list : elecLevel1List) {
                                if (Objects.equals(electrolWZMappingDtos.get(0).getCodIdElecLevel1(), list.getLookUpId())) {
                                    locationMasterUploadDto.setCodIdElecLevel1(list.getDescLangFirst());
                                    break;
                                }
                            }
                        }
                        if (elecLevel2List != null) {
                            for (LookUp list : elecLevel2List) {
                                if (Objects.equals(electrolWZMappingDtos.get(0).getCodIdElecLevel2(), list.getLookUpId())) {
                                    locationMasterUploadDto.setCodIdElecLevel2(list.getDescLangFirst());
                                    break;
                                }
                            }
                        }
                        if (elecLevel3List != null) {
                            for (LookUp list : elecLevel3List) {
                                if (Objects.equals(electrolWZMappingDtos.get(0).getCodIdElecLevel3(), list.getLookUpId())) {
                                    locationMasterUploadDto.setCodIdElecLevel3(list.getDescLangFirst());
                                    break;
                                }
                            }
                        }
                        if (elecLevel4List != null) {
                            for (LookUp list : elecLevel4List) {
                                if (Objects.equals(electrolWZMappingDtos.get(0).getCodIdElecLevel4(), list.getLookUpId())) {
                                    locationMasterUploadDto.setCodIdElecLevel4(list.getDescLangFirst());
                                    break;
                                }
                            }
                        }
                        if (elecLevel5List != null) {
                            for (LookUp list : elecLevel5List) {
                                if (Objects.equals(electrolWZMappingDtos.get(0).getCodIdElecLevel5(), list.getLookUpId())) {
                                    locationMasterUploadDto.setCodIdElecLevel5(list.getDescLangFirst());
                                    break;
                                }
                            }
                        }
                    }

                }
                if (locRevenueWZMappingDtos != null && !locRevenueWZMappingDtos.isEmpty()) {
                    locationMasterUploadDto.setIsAreaMappingRevenue("Y");
                    for (Map.Entry<Long, String> revMapEntry : revenueMap.entrySet()) {
                        if (Objects.equals(locRevenueWZMappingDtos.get(0).getCodIdRevLevel1(), revMapEntry.getKey())) {
                            locationMasterUploadDto.setCodIdRevLevel1(revMapEntry.getValue());
                            break;
                        }
                    }
                }
                if (locOperationWZMappingDtos != null && !locOperationWZMappingDtos.isEmpty()) {
                    // copy dpdeptid, fetch dpdeptdesc and set dpdeptdesc
                    locationMasterUploadDto.setIsAreaMappingOperational("Y");
                    if (locOperationWZMappingDtos.get(0).getDpDeptId() != null) {
                        locationMasterUploadDto.setDpDeptId(locOperationWZMappingDtos.get(0).getDpDeptId());
                        String deptDesc = departmentService.findDepartmentDescByDeptId(locationMasterUploadDto.getDpDeptId());
                        locationMasterUploadDto.setDpDeptDesc(deptDesc);

                        String prefixName = null;
                        prefixName = departmentService.findDepartmentPrefixName(locationMasterUploadDto.getDpDeptId(),
                                org.getOrgid());

                        Map<Integer, List<LookUp>> operLookupsMap = new HashMap<>();
                        for (int i = 1; i <= 5; i++) {
                            try {
                                List<LookUp> lookUps = CommonMasterUtility.getLevelData(prefixName, i, org);
                                operLookupsMap.put(i, lookUps);
                            } catch (Exception e) {

                            }
                        }
                        int size = operLookupsMap.size();
                        if (size > 0) {
                            List<LookUp> operLevel1List = operLookupsMap.get(1);
                            List<LookUp> operLevel2List = operLookupsMap.get(2);
                            List<LookUp> operLevel3List = operLookupsMap.get(3);
                            List<LookUp> operLevel4List = operLookupsMap.get(4);
                            List<LookUp> operLevel5List = operLookupsMap.get(5);

                            if (operLevel1List != null) {
                                for (LookUp list : operLevel1List) {
                                    if (Objects.equals(locOperationWZMappingDtos.get(0).getCodIdOperLevel1(),
                                            list.getLookUpId())) {
                                        locationMasterUploadDto.setCodIdOperLevel1(list.getDescLangFirst());
                                        break;
                                    }
                                }
                            }
                            if (operLevel2List != null) {
                                for (LookUp list : operLevel2List) {
                                    if (Objects.equals(locOperationWZMappingDtos.get(0).getCodIdOperLevel2(),
                                            list.getLookUpId())) {
                                        locationMasterUploadDto.setCodIdOperLevel2(list.getDescLangFirst());
                                        break;
                                    }
                                }
                            }
                            if (operLevel3List != null) {
                                for (LookUp list : operLevel3List) {
                                    if (Objects.equals(locOperationWZMappingDtos.get(0).getCodIdOperLevel3(),
                                            list.getLookUpId())) {
                                        locationMasterUploadDto.setCodIdOperLevel3(list.getDescLangFirst());
                                        break;
                                    }
                                }
                            }
                            if (operLevel4List != null) {
                                for (LookUp list : operLevel4List) {
                                    if (Objects.equals(locOperationWZMappingDtos.get(0).getCodIdOperLevel4(),
                                            list.getLookUpId())) {
                                        locationMasterUploadDto.setCodIdOperLevel4(list.getDescLangFirst());
                                        break;
                                    }
                                }
                            }
                            if (operLevel5List != null) {
                                for (LookUp list : operLevel5List) {
                                    if (Objects.equals(locOperationWZMappingDtos.get(0).getCodIdOperLevel5(),
                                            list.getLookUpId())) {
                                        locationMasterUploadDto.setCodIdOperLevel5(list.getDescLangFirst());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                locationMasterUploadDtos.add(locationMasterUploadDto);
            });
        }

        return locationMasterUploadDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, String> getWardAndZoneByOrgIdLocationDept(Long orgId, Long loc) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + "getWardAndZoneByOrgIdLocationDept method orgId & loc "
                + orgId + "  " + loc);
        ServiceMaster master = servicesMstService
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
        LocationOperationWZMapping locationOperationWZMapping = null;
        String zone = null;
        String ward = null;
        Map<String, String> wardZone = new HashMap<String, String>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        try {
            locationOperationWZMapping = locationMasJpaRepository.findbyLocationAndDepartment(
                    loc,
                    master.getTbDepartment().getDpDeptid());
            if (locationOperationWZMapping != null && locationOperationWZMapping.getCodIdOperLevel1() != null)
                zone = CommonMasterUtility
                        .getHierarchicalLookUp(locationOperationWZMapping.getCodIdOperLevel1(),
                                organisation)
                        .getDescLangFirst();
            if (locationOperationWZMapping != null && locationOperationWZMapping.getCodIdOperLevel2() != null)
                ward = CommonMasterUtility
                        .getHierarchicalLookUp(locationOperationWZMapping.getCodIdOperLevel2(),
                                organisation)
                        .getDescLangFirst();
            wardZone.put("zone", zone);
            wardZone.put("ward", ward);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + "getWardAndZoneByOrgIdLocationDept method");
        return wardZone;
    }
    
    @Override
    @WebMethod(exclude = true)
    public Long getFieldIdWithWard(final Long deptId,final Long wardID, final Long orgId) {
        return locationMasJpaRepository.findFieldIdWithWardAndDeptId(deptId,wardID, orgId);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Long> getlocationListByFieldIdAndOrgId(final long areaDivision1, final Long orgId) {
    	return revenueWZMappingJpaRepository.getlocationListByFieldIdAndOrgId(areaDivision1,orgId);
    }
    
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<Long> getOperLocationIdBasedOnLocIdDeptIdOrgId(final long locId, final Long deptId, final Long orgId) {
    	return operationWZMappingJpaRepository.getOperLocationId(locId,deptId,orgId);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @WebMethod(exclude = true)
    @Transactional
    public  Map<Long, String>getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO) {
    	Map<Long, String> dto = new HashMap<>();
        ResponseEntity<?> response = null;
        Object responseObj = null;

        try {
            response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.GET_BUDGET);
            if (response != null ) {
            	responseObj = response.getBody();
                dto = (Map<Long, String>)responseObj;
    		}
        } catch (Exception ex) {
            LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
            throw new FrameworkException(ex);
        }
        return dto;
    }

	@Override
	@Transactional
	public void inactiveRemovedYearDetails(TbLocationMas tbLocationMas, List<Long> removeYearIdList) {
		if (removeYearIdList != null && !removeYearIdList.isEmpty()) {
			locationMasJpaRepository.iactiveYearsByIds(removeYearIdList, tbLocationMas.getUpdatedBy());
 		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<LocationOperationWZMapping> findByLocationWZList(Long locId) {
		logger.info("findByLocationList(Long locId) execution starts locId= " + locId);
        final List<LocationOperationWZMapping> locationOperationWZMappings = operationWZMappingJpaRepository
                .findByLocationList(locId);
        logger.info("findByLocationList(Long locId) execution ends");
        return locationOperationWZMappings;
	}

	@Override
	public Long findFieldIdByLocationId(Long locId, Long orgId) {
		return locationMasJpaRepository.findFieldIdByLocationId(locId, orgId);
	}

	@Override
    @Transactional
    @WebMethod(exclude = true)
    public List<Object[]> getLocationNameAndLocCodeByOrgId(Long orgId) {
        logger.info("getLocationNameAndLocCodeByOrgId execution starts orgId= " + orgId);
        return locationMasJpaRepository.getLocationNameAndLocCodeByOrgId(orgId);
    }
}
