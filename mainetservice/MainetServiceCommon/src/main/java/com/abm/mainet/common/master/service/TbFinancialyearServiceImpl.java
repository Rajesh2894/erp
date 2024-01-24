/*
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 * Created on 2 Jan 2016 ( Time 11:57:13 )
 */
package com.abm.mainet.common.master.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.FinancialYearHistory;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbFincialyearorgMapEntity;
import com.abm.mainet.common.domain.TbFincialyearorgMapEntityHistory;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbFincialyearorgMap;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.mapper.TbFinancialyearServiceMapper;
import com.abm.mainet.common.master.mapper.TbFincialyearorgMapServiceMapper;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.repository.TbFincialyearorgMapJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;

/** Implementation of TbFinancialyearService */
@Component
public class TbFinancialyearServiceImpl implements TbFinancialyearService {

    @Autowired
    private AuditService auditService;

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;

    @Resource
    private TbFinancialyearServiceMapper tbFinancialyearServiceMapper;

    @Resource
    private TbFincialyearorgMapJpaRepository tbFincialyearorgMapJpaRepository;

    @Resource
    private TbFincialyearorgMapServiceMapper tbFincialyearorgMapServiceMapper;

    @Resource
    private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

    @Resource
    private TbFinancialyearOrgMapService tbFinancialyearOrgMapService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Override
    @Transactional
    public TbFinancialyear findYearById(final Long faYearid, final long orgId) {
        TbFinancialyear finYear = null;
        final List<TbFincialyearorgMap> orgMapList = new ArrayList<>(0);
        FinancialYear tbFinancialyearEntity = null;
        final TbFincialyearorgMapEntity orgFincialYear = tbFinancialyearOrgMapService.findByOrgId(faYearid, orgId);

        if (orgFincialYear == null) {
            tbFinancialyearEntity = tbFinancialyearJpaRepository.findOne(faYearid);
        } else {
            tbFinancialyearEntity = orgFincialYear.getTbFinancialyear();
        }
        finYear = tbFinancialyearServiceMapper.mapTbFinancialyearEntityToTbFinancialyear(tbFinancialyearEntity);
        for (final TbFincialyearorgMap orgmap : finYear.getFinancialyearOrgMap()) {
            if (orgFincialYear != null && orgmap.getOrgid().equals(orgFincialYear.getOrgid())) {
                orgMapList.add(orgmap);
            }
        }
        finYear.setFinancialyearOrgMap(orgMapList);
        return finYear;
    }

    @Override
    @Transactional
    public void create(final TbFinancialyear tbFinancialyear, final Long sessionOrgId, final Employee sessionEmp)
            throws Exception {

        final FinancialYear tbFinancialyearEntity = new FinancialYear();
        tbFinancialyearServiceMapper.mapTbFinancialyearToTbFinancialyearEntity(tbFinancialyear, tbFinancialyearEntity);
        tbFinancialyearJpaRepository.save(tbFinancialyearEntity);

        FinancialYearHistory tbFinancialyearHistEntity = new FinancialYearHistory();
        tbFinancialyearHistEntity.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(tbFinancialyearEntity, tbFinancialyearHistEntity);

        final String prefixOpn = ApplicationSession.getInstance().getMessage("prefix.open");

        TbFincialyearorgMap finOrgBean = null;
        final List<TbOrganisation> orgList = tbOrganisationService.findAll(); // Active organizations

        if (orgList.size() > 0) {
            for (final TbOrganisation org : orgList) {

                finOrgBean = new TbFincialyearorgMap();
                final Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(tbFinancialyearEntity.getFaFromDate());
                finOrgBean.setFaFromYear(Long.valueOf(calendar1.get(Calendar.YEAR)));

                final Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(tbFinancialyearEntity.getFaToDate());
                finOrgBean.setFaToYear(Long.valueOf(calendar2.get(Calendar.YEAR)));

                final Organisation orgEntity = tbOrganisationService.findDefaultOrganisation();
                final List<LookUp> yearLookup = CommonMasterUtility.getLookUps(MainetConstants.FIN_YEAR_YOC, orgEntity);
                for (final LookUp lookUp : yearLookup) {
                    if (lookUp.getLookUpCode().equals(prefixOpn)) {
                        finOrgBean.setYaTypeCpdId(lookUp.getLookUpId());
                        break;
                    }
                }

                finOrgBean.setFaYearid(tbFinancialyearEntity.getFaYear());
                finOrgBean.setOrgid(org.getOrgid());
                finOrgBean.setCreatedBy(sessionEmp.getEmpId());
                finOrgBean.setCreatedDate(new Date());
                tbFinancialyearOrgMapService.create(finOrgBean);

            }
        }
    }

    @Override
    @Transactional

    public void update(final TbFinancialyear tbFinancialyear, final Long mapId, final Employee sessionEmp) {

        TbFincialyearorgMapEntity orgMapEntity = new TbFincialyearorgMapEntity();
        TbFincialyearorgMap fincialyearorgMap = new TbFincialyearorgMap();
        Long hclLookupId = 0L;
        Long opnLookupId = 0L;
        final String prefixOpn = ApplicationSession.getInstance().getMessage("prefix.open");
        final String prefixHcl = ApplicationSession.getInstance().getMessage("prefix.hardclose");

        fincialyearorgMap = tbFincialyearorgMapServiceMapper
                .mapTbFincialyearorgMapEntityToTbFincialyearorgMap(orgMapEntity);

        fincialyearorgMap.setMapId(tbFinancialyear.getFinancialyearOrgMap().get(0).getMapId());
        fincialyearorgMap.setFaYearid(tbFinancialyear.getFaYear());
        fincialyearorgMap.setOrgid(tbFinancialyear.getOrgId());
        fincialyearorgMap.setIpMacUpd(tbFinancialyear.getLgIpMacUpd());
        final Organisation org = tbOrganisationService.findDefaultOrganisation();
        final List<LookUp> yearLookup = CommonMasterUtility.getLookUps(MainetConstants.FIN_YEAR_YOC, org);
        for (final LookUp lookUp : yearLookup) {
            if (lookUp.getLookUpCode().equals(prefixHcl)) {
                hclLookupId = lookUp.getLookUpId();
            }
            if (lookUp.getLookUpCode().equals(prefixOpn)) {
                opnLookupId = lookUp.getLookUpId();
            }
        }

          // ||tbFinancialyear.getFinancialyearOrgMap().get(0).getFaMonthStatus().equals(opnLookupId)
     	 // commented from if condition against id #119992
        if (tbFinancialyear.getFinancialyearOrgMap().get(0).getYaTypeCpdId().equals(hclLookupId)) {
            fincialyearorgMap.setFaFromMonth(null);
            fincialyearorgMap.setFaToMonth(null);
            fincialyearorgMap.setFaMonthStatus(null);
        } else {
            fincialyearorgMap.setFaFromMonth(tbFinancialyear.getFinancialyearOrgMap().get(0).getFaFromMonth());
            fincialyearorgMap.setFaToMonth(tbFinancialyear.getFinancialyearOrgMap().get(0).getFaToMonth());
            fincialyearorgMap.setFaMonthStatus(tbFinancialyear.getFinancialyearOrgMap().get(0).getFaMonthStatus());
        }

        final String fromDate = tbFinancialyear.getFromDate().substring(tbFinancialyear.getFromDate().length() - 4,
                tbFinancialyear.getFromDate().length());
        final String toDate = tbFinancialyear.getToDate().substring(tbFinancialyear.getToDate().length() - 4,
                tbFinancialyear.getToDate().length());

        fincialyearorgMap.setFaFromYear(Long.valueOf(fromDate));
        fincialyearorgMap.setFaToYear(Long.valueOf(toDate));
        fincialyearorgMap.setYaTypeCpdId(tbFinancialyear.getFinancialyearOrgMap().get(0).getYaTypeCpdId());

        fincialyearorgMap = tbFinancialyearOrgMapService.update(fincialyearorgMap);
        TbFincialyearorgMapEntityHistory tbFincialyearorgMapHistEntity = new TbFincialyearorgMapEntityHistory();
        tbFincialyearorgMapHistEntity.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        tbFincialyearorgMapHistEntity.setFaYearID(tbFinancialyear.getFaYear()); //
        tbFincialyearorgMapHistEntity.setOrgid(fincialyearorgMap.getOrgid());
        auditService.createHistory(fincialyearorgMap, tbFincialyearorgMapHistEntity);

    }

    public TbFinancialyearJpaRepository getTbFinancialyearJpaRepository() {
        return tbFinancialyearJpaRepository;
    }

    public void setTbFinancialyearJpaRepository(final TbFinancialyearJpaRepository tbFinancialyearJpaRepository) {
        this.tbFinancialyearJpaRepository = tbFinancialyearJpaRepository;
    }

    public TbFinancialyearServiceMapper getTbFinancialyearServiceMapper() {
        return tbFinancialyearServiceMapper;
    }

    public void setTbFinancialyearServiceMapper(final TbFinancialyearServiceMapper tbFinancialyearServiceMapper) {
        this.tbFinancialyearServiceMapper = tbFinancialyearServiceMapper;
    }

    @Override
    @Transactional
    public List<TbFinancialyear> findAllFinancialYearByOrgId(final Organisation organisation) {
        List<FinancialYear> entities = null;
        entities = findAllFinYearByOrgId(Long.valueOf(organisation.getOrgid()));

        final Organisation defaultOrg = tbOrganisationService.findDefaultOrganisation();
        final String prefixMon = ApplicationSession.getInstance().getMessage("prefix.month");
        final List<TbFinancialyear> beans = new ArrayList<>();
        TbFincialyearorgMapEntity tbFincialyearorgMapEntity = null;
        final int langId = UserSession.getCurrent().getLanguageId();
        TbFinancialyear tbFinancialyear = null;
        String yearStatus = null;
        String yearStatusCode = null;

        for (final FinancialYear tbFinancialyearEntity : entities) {
            yearStatus = null;
            tbFincialyearorgMapEntity = tbFincialyearorgMapJpaRepository.findOrgFincialYear(organisation.getOrgid(),
                    tbFinancialyearEntity.getFaYear());
            tbFinancialyear = new TbFinancialyear();
            tbFinancialyear = tbFinancialyearServiceMapper
                    .mapTbFinancialyearEntityToTbFinancialyear(tbFinancialyearEntity);
            if (tbFincialyearorgMapEntity != null) {
                if ((tbFincialyearorgMapEntity.getFaFromMonth() != null)
                        && (tbFincialyearorgMapEntity.getFaToMonth() != null)) {
                    final String fromMonth = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            getStringValForMonth(tbFincialyearorgMapEntity.getFaFromMonth()), prefixMon, langId,
                            organisation).getLookUpDesc();
                    final String toMonth = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            getStringValForMonth(tbFincialyearorgMapEntity.getFaToMonth()), prefixMon, langId,
                            organisation).getLookUpDesc();

                    tbFinancialyear.setFaYearFromTo(fromMonth + MainetConstants.HYPHEN + toMonth);
                }
                if (tbFincialyearorgMapEntity.getFaMonthStatus() != null) {
                    final String monthStatus = CommonMasterUtility
                            .getNonHierarchicalLookUpObject(tbFincialyearorgMapEntity.getFaMonthStatus(), organisation)
                            .getLookUpDesc();
                    tbFinancialyear.setFaMonthStatus(monthStatus);
                }

                if (tbFincialyearorgMapEntity.getYaTypeCpdId() != null) {
                    yearStatus = CommonMasterUtility.findLookUpDesc(MainetConstants.FIN_YEAR_YOC, defaultOrg.getOrgid(),
                            tbFincialyearorgMapEntity.getYaTypeCpdId());
                    tbFinancialyear.setYearStatusDesc(yearStatus);

                    yearStatusCode = CommonMasterUtility.findLookUpCode(MainetConstants.FIN_YEAR_YOC, defaultOrg.getOrgid(),
                            tbFincialyearorgMapEntity.getYaTypeCpdId());
                    tbFinancialyear.setYearStatusCode(yearStatusCode);
                } else {

                    tbFinancialyear.setYearStatusDesc(yearStatus);
                    tbFinancialyear.setYearStatusCode(yearStatusCode);
                }
            }
            beans.add(tbFinancialyear);
        }
        // Defect #33083->to get finyearId by ascending order
        Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaFromDate,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(beans, comparing);
        return beans;
    }

    @Override
    @Transactional
    public List<FinancialYear> findAllFinYearByOrgId(final long orgid) {
        final List<FinancialYear> entities = tbFincialyearorgMapJpaRepository
                .findAllFinYearByOrgId(Long.valueOf(orgid));
        return entities;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.financialyear.business.service.
     * TbFinancialyearService#isFaYearExists(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public FinancialYear isFaYearExists(final String fromDate) {
        FinancialYear finYear = tbFinancialyearJpaRepository
                .finanacialYearAlreadyExistCount(UtilityService.convertStringDateToDateFormat(fromDate));
        return finYear;
    }

    @Override
    @Transactional
    public Map<Long, String> getAllFinincialYearByStatusWise(Long orgId) {

        Organisation org = new Organisation();
        org.setOrgid(orgId);
        Long opnLookupId = 0L;
        final String prefixOpn = ApplicationSession.getInstance().getMessage("prefix.open");
        final List<LookUp> yearLookup = CommonMasterUtility.getLookUps(MainetConstants.FIN_YEAR_YOC, org);
        for (final LookUp lookUp : yearLookup) {
            if (lookUp.getLookUpCode().equals(prefixOpn)) {
                opnLookupId = lookUp.getLookUpId();
            }
        }
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final Map<Long, String> finYearMap = new LinkedHashMap<>();
        String frmdate;
        String todate;
        String date;
        List<FinancialYear> finYearsList = tbFinancialyearJpaRepository.findAllFinYearInBudget();
        for (FinancialYear financialYear : finYearsList) {
            List<TbFincialyearorgMapEntity> fnYearlist = financialYear.getFinancialyearOrgMap();
            for (TbFincialyearorgMapEntity tbFincialyearorgMapEntity : fnYearlist) {
                if ((opnLookupId.equals(tbFincialyearorgMapEntity.getYaTypeCpdId()))
                        && (orgId.equals(tbFincialyearorgMapEntity.getOrgid()))) {
                    frmdate = formatter.format(financialYear.getFaFromDate());
                    todate = formatter.format(financialYear.getFaToDate());
                    if (!frmdate.isEmpty() && !todate.isEmpty()) {
                        date = MainetConstants.WHITE_SPACE + frmdate + MainetConstants.HYPHEN + todate
                                + MainetConstants.WHITE_SPACE;
                        finYearMap.put(financialYear.getFaYear(), date);
                    }
                }
            }
        }
        return finYearMap;
    }

    @Override
    @Transactional
    public Map<Long, String> getAllFinincialYear() {
        final List<Object[]> finYears = tbFinancialyearJpaRepository.getAllFinincialYear();
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final Map<Long, String> finYearMap = new LinkedHashMap<>();
        String frmdate;
        String todate;
        String date;
        if ((finYears != null) && !finYears.isEmpty()) {
            for (final Object[] obj : finYears) {
                frmdate = formatter.format(obj[1]);
                todate = formatter.format(obj[2]);
                if (!frmdate.isEmpty() && !todate.isEmpty()) {
                    date = MainetConstants.WHITE_SPACE + frmdate + MainetConstants.HYPHEN + todate
                            + MainetConstants.WHITE_SPACE;
                    finYearMap.put((Long) obj[0], date);
                }
            }
        }
        return finYearMap;
    }

    @Override
    @Transactional
    public String findFinancialYearDesc(final Long fyId) {
        return concateFinancialYearDesc(tbFinancialyearJpaRepository.getFinanceYearFrmDate(fyId));
    }

    private String concateFinancialYearDesc(final List<Object[]> list) {
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        String frmdate;
        String todate;
        String date = StringUtils.EMPTY;
        for (final Object[] objArray : list) {
            frmdate = formatter.format(objArray[0]);
            todate = formatter.format(objArray[1]);
            if (!frmdate.isEmpty() && !todate.isEmpty()) {
                date = frmdate + MainetConstants.HYPHEN + todate;
                break;
            }
        }

        return date;
    }

    @Override
    @Transactional
    public List<TbFinancialyear> findAll() {
        List<FinancialYear> entities = null;
        entities = tbFinancialyearJpaRepository.findAllFinYear();
        final List<TbFinancialyear> beans = new ArrayList<>();
        for (final FinancialYear tbFinancialyearEntity : entities) {
            beans.add(tbFinancialyearServiceMapper.mapTbFinancialyearEntityToTbFinancialyear(tbFinancialyearEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public TbFinancialyear findFinancialYear(final Date trnsDate) {
        final FinancialYear financialYear = tbFinancialyearJpaRepository.getFinanciaYearId(trnsDate);
        return tbFinancialyearServiceMapper.mapTbFinancialyearEntityToTbFinancialyear(financialYear);
    }

    @Override
    @Transactional
    public List<Long> getCurrentAndPreviousYears() {
        List<Long> faYearIdList = null;
        faYearIdList = tbFincialyearorgMapJpaRepository.getCurrentandPreviousFinancialYear();
        return faYearIdList;
    }

    @Override
    @Transactional
    public Long checkForPreviousYear(final int fromYear, final int toYear) {
        final Long finYearCount = tbFinancialyearJpaRepository.getPreviousFinancialYear(fromYear, toYear);
        return finYearCount;
    }

    private String getStringValForMonth(final Long month) {
        String value = MainetConstants.BLANK;
        final int length = (int) Math.log10(month) + 1;
        if (length == 1) {
            value = MainetConstants.CommonConstants.ZERO + String.valueOf(month);
        } else {
            value = String.valueOf(month);
        }
        return value;
    }

    @Override
    public Date getValidFInancialYearDate() {
        return tbFinancialyearJpaRepository.getValidFInancialYearDate();
    }

    /**
     * @param SLI Prefix Date wise
     * @return Financial Years
     */
    @Override
    @Transactional
    public Map<Long, String> getAllSLIPrefixDateFinincialYear(final Date sliDate) {
        final List<Object[]> finYears = tbFinancialyearJpaRepository.getAllSLIPrefixDateFinincialYear(sliDate);
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final Map<Long, String> finYearMap = new LinkedHashMap<>();
        String frmdate;
        String todate;
        String date;
        if ((finYears != null) && !finYears.isEmpty()) {
            for (final Object[] obj : finYears) {
                frmdate = formatter.format(obj[1]);
                todate = formatter.format(obj[2]);
                if (!frmdate.isEmpty() && !todate.isEmpty()) {
                    date = MainetConstants.WHITE_SPACE + frmdate + MainetConstants.HYPHEN + todate
                            + MainetConstants.WHITE_SPACE;
                    finYearMap.put((Long) obj[0], date);
                }
            }
        }
        return finYearMap;
    }

    @Override
    public int getFinancialYearTotalCount() {
        int count = 0;
        count = tbFinancialyearJpaRepository.getFinancialYearTotalCount();
        return count;
    }

    @Override
    @Transactional
    public Date getMinFinancialYear() {
        return tbFinancialyearJpaRepository.getMinFinancialYear();
    }

    @Override
    @Transactional
    public void create(FinancialYear entity, Long orgId) {

        if (entity.getFaYear() == 0) {
            entity = tbFinancialyearJpaRepository.save(entity);
        }

        final String prefixOpn = ApplicationSession.getInstance().getMessage("prefix.open");

        TbFincialyearorgMap finOrgBean = new TbFincialyearorgMap();
        final Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(entity.getFaFromDate());
        finOrgBean.setFaFromYear(Long.valueOf(calendar1.get(Calendar.YEAR)));

        final Calendar calendar2 = new GregorianCalendar();
        calendar2.setTime(entity.getFaToDate());
        finOrgBean.setFaToYear(Long.valueOf(calendar2.get(Calendar.YEAR)));

        final Organisation org = tbOrganisationService.findDefaultOrganisation();

        final List<LookUp> yearLookup = CommonMasterUtility.getLookUps(MainetConstants.FIN_YEAR_YOC, org);
        for (final LookUp lookUp : yearLookup) {
            if (lookUp.getLookUpCode().equals(prefixOpn)) {
                finOrgBean.setYaTypeCpdId(lookUp.getLookUpId());
                break;
            }
        }

        finOrgBean.setFaYearid(entity.getFaYear());
        finOrgBean.setOrgid(orgId);
        finOrgBean.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        finOrgBean.setCreatedDate(new Date());
        tbFinancialyearOrgMapService.create(finOrgBean);

    }

    @Override
    public Long checkHardClosedFinYearDateExists(Date date, Long orgid) {
        Long fiYearStatus = tbFinancialyearJpaRepository.getFinancialYearTotalCount(date, orgid);
        return fiYearStatus;
    }

    @Override
    public Long checkSoftClosedFinYearDateExists(Date date, Long orgid) {
        Long fiYearStatus = tbFinancialyearJpaRepository.getFinancialYearMonthTotalCount(date, orgid);
        return fiYearStatus;
    }

    @Override
    @Transactional
    public List<TbFinancialyear> getFinanciaYearsFromDate(final Date fromDate) {
        List<FinancialYear> entities = null;
        entities = tbFinancialyearJpaRepository.getFinanciaYearsFromDate(fromDate);
        final List<TbFinancialyear> beans = new ArrayList<>();
        for (final FinancialYear tbFinancialyearEntity : entities) {
            beans.add(tbFinancialyearServiceMapper.mapTbFinancialyearEntityToTbFinancialyear(tbFinancialyearEntity));
        }
        return beans;
    }

    @Override
    public List<FinYearDTO> getFinancialYear() {
        final List<Object[]> finYears = tbFinancialyearJpaRepository.getAllFinincialYear();
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        String frmdate;
        String todate;
        String date = StringUtils.EMPTY;
        List<FinYearDTO> list = new ArrayList<FinYearDTO>();

        if (finYears != null && !finYears.isEmpty()) {
            FinYearDTO fYearDto = null;
            for (final Object[] obj : finYears) {
                fYearDto = new FinYearDTO();
                frmdate = formatter.format(obj[1]);
                todate = formatter.format(obj[2]);
                if (!frmdate.isEmpty() && !todate.isEmpty()) {
                    date = MainetConstants.WHITE_SPACE + frmdate + MainetConstants.HYPHEN + todate
                            + MainetConstants.WHITE_SPACE;

                    fYearDto.setId((Long) obj[0]);
                    fYearDto.setText(date);
                    list.add(fYearDto);
                }
            }
        }
        return list;
    }

    @Override
    public FinancialYear getFinanciaYearByDate(Date date) {
        return tbFinancialyearJpaRepository.getFinanciaYearId(date);
    }

    @Override
    public List<FinancialYear> findAllFinancialYearById(List<Long> finYearIdList) {
        return tbFinancialyearJpaRepository.findAllFinancialYearById(finYearIdList);
    }

    @Override
    @Transactional
    public Map<Long, String> getAllFinincialYearStatusOpen(Long orgId) {

        final Long finYarStatusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("OPN", "YOC", orgId);
        final List<Object[]> finYears = tbFinancialyearJpaRepository.getAllFinincialYearStatusOpen(finYarStatusId,
                orgId);
        DateFormat formatter;
        formatter = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final Map<Long, String> finYearMap = new LinkedHashMap<>();
        String frmdate;
        String todate;
        String date;
        if ((finYears != null) && !finYears.isEmpty()) {
            for (final Object[] obj : finYears) {
                frmdate = formatter.format(obj[1]);
                todate = formatter.format(obj[2]);
                if (!frmdate.isEmpty() && !todate.isEmpty()) {
                    date = MainetConstants.WHITE_SPACE + frmdate + MainetConstants.HYPHEN + todate
                            + MainetConstants.WHITE_SPACE;
                    finYearMap.put((Long) obj[0], date);
                }
            }
        }
        return finYearMap;
    }

    @Override
    public List<Object[]> getFromDateAndToDateByFinancialYearId(Long financialYearId) {
        // TODO Auto-generated method stub
        return tbFinancialyearJpaRepository.getFinanceYearFrmDate(financialYearId);
    }

    @Override
    public int getCountOfFinYearBetwDates(Date fromDate, Date toDate, Long orgid) {
        return tbFinancialyearJpaRepository.getCountOfFinYearBetwDates(fromDate, toDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getFinanciaYearIdByFromDate(Date finYearFromDate) {
        return tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(finYearFromDate);
    }
    
    @Override
    @Transactional
    public List<FinancialYear> findAllFinYear() {
        final List<FinancialYear> entities = tbFincialyearorgMapJpaRepository.findAllFinYear();
        return entities;
    }

}
