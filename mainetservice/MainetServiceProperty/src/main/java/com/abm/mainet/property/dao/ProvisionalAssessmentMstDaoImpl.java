package com.abm.mainet.property.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.utility.GridPaginationUtility;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Repository
public class ProvisionalAssessmentMstDaoImpl extends AbstractDAO<Long> implements IProvisionalAssessmentMstDao {
    private static Logger log = Logger.getLogger(ProvisionalAssessmentMstDaoImpl.class);

    @Override
    public boolean CheckForAssesmentFieldForCurrYear(long orgId, String assNo, String assOldpropno, String status,
            Long finYearId, Long serviceId) {
        StringBuilder queryString = new StringBuilder(
                "from ProvisionalAssesmentMstEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from ProvisionalAssesmentMstEntity am  WHERE  am.orgId=:orgId and"
                        + "   am.faYearId=:faYearId and am.smServiceId !=:serviceId ");
        if (assNo != null && !assNo.isEmpty()) {
            queryString.append(" and am.assNo=:assNo ");
        }
        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }
        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("faYearId", finYearId);
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }
        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            query.setParameter("assOldpropno", assOldpropno);
        }
        if (serviceId != null) {
            query.setParameter("serviceId", serviceId);
        }
        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            log.info("No  provisional Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchPropetyForView(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        String coloumnName = null;
        String referenceNumber = null;
        // D#90244
        StringBuilder queryString = new StringBuilder(
                "select distinct PM_PROP_NO,PM_PROP_OLDPROPNO,MN_ASSO_OWNER_NAME OWNER_NAME,MN_ASSO_MOBILENO AS MOBILE_NO,PM_PLOT_NO_CS,a.orgid,pm_ward1,pm_ward2,PM_LOC_ID,PM_PROP_LVL_ROAD_TYPE,"
                        +
                        "OUTSTANDING,c.RM_AMOUNT,c.RM_DATE,pm_ward3,pm_ward4,pm_ward5,MN_asso_guardian_name,PM_PLOT_NO"
                        +
                        " from tb_as_prop_mas a left join((select * from tb_as_assesment_detail )d,"
                        +
                        " (select * from TB_AS_ASSESMENT_OWNER_DTL b where MN_asso_otype='P')b)on a.PM_PROP_NO=b.MN_ASS_no and a.orgid=b.orgid and d.mn_ass_id = b.mn_ass_id and d.orgid = b.orgid "
                        +
                        " LEFT JOIN (SELECT X.MN_BM_IDNO, x.MN_PROP_NO, MN_TOTAL_OUTSTANDING AS OUTSTANDING"
                        +
                        " FROM (SELECT MN_BM_IDNO,MN_PROP_NO,(COALESCE(MN_TOTAL_OUTSTANDING, 0) + COALESCE(MN_TOTAL_PENALTY, 0)) AS MN_TOTAL_OUTSTANDING FROM tb_as_bill_mas WHERE orgid = :orgid"
                        +
                        " UNION SELECT pro_bm_idno, pro_prop_no,(COALESCE(pro_bm_total_outstanding, 0) + COALESCE(PRO_BM_TOTAL_PENALTY, 0)) FROM tb_as_pro_bill_mas WHERE  orgid = :orgid) X,"
                        +
                        "(SELECT MN_PROP_NO, MAX(MN_BM_IDNO) MN_BM_IDNO FROM  (SELECT MN_BM_IDNO, MN_PROP_NO FROM   tb_as_bill_mas WHERE  orgid = :orgid"
                        +
                        " UNION SELECT pro_bm_idno, pro_prop_no FROM   tb_as_pro_bill_mas WHERE  orgid = :orgid) Y"
                        +
                        " GROUP BY MN_PROP_NO) Y WHERE  X.MN_BM_IDNO = Y.MN_BM_IDNO) N ON a.PM_PROP_NO = N.MN_PROP_NO"
                        +
                        " left join (select a.ADDITIONAL_REF_NO,a.RM_AMOUNT,a.RM_DATE from tb_receipt_mas a,(select ADDITIONAL_REF_NO,max(RM_RCPTID) RM_RCPTID from tb_receipt_mas where dp_deptid=:deptId and orgid=:orgid"
                        +
                        " group by ADDITIONAL_REF_NO) b where a.ADDITIONAL_REF_NO=b.ADDITIONAL_REF_NO and a.RM_RCPTID=b.RM_RCPTID and a.orgid=:orgid and a.RECEIPT_DEL_FLAG is null) c ON a.PM_PROP_NO = c.ADDITIONAL_REF_NO"
                        +
                        " where a.orgid=:orgid");

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            queryString.append(" and PM_PROP_NO=:assNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            queryString.append(" and PM_PROP_OLDPROPNO=:assOldpropno ");
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            queryString.append(
                    " and PM_LOC_ID=(case when COALESCE(:PM_LOC_ID,0)=0 then COALESCE(PM_LOC_ID,0) else COALESCE(:PM_LOC_ID,0) end) ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            queryString.append(
                    " and pm_ward1=(case when COALESCE(:pm_ward1,0)=0 then COALESCE(pm_ward1,0) else COALESCE(:pm_ward1,0) end) ");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            queryString.append(
                    " and pm_ward2=(case when COALESCE(:pm_ward2,0)=0 then COALESCE(pm_ward2,0) else COALESCE(:pm_ward2,0) end)");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            queryString.append(
                    " and pm_ward3=(case when COALESCE(:pm_ward3,0)=0 then COALESCE(pm_ward3,0) else COALESCE(:pm_ward3,0) end)");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            queryString.append(
                    " and pm_ward4=(case when COALESCE(:pm_ward4,0)=0 then COALESCE(pm_ward4,0) else COALESCE(:pm_ward4,0) end)");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            queryString.append(
                    " and pm_ward5=(case when COALESCE(:pm_ward5,0)=0 then COALESCE(pm_ward5,0) else COALESCE(:pm_ward5,0) end)");
        }

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            queryString.append(
                    " and PM_PROP_LVL_ROAD_TYPE=(case when COALESCE(:PM_PROP_LVL_ROAD_TYPE,0)=0 then COALESCE(PM_PROP_LVL_ROAD_TYPE,0) else COALESCE(:PM_PROP_LVL_ROAD_TYPE,0) end) ");
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            queryString.append(
                    " and MN_assd_usagetype1=(case when COALESCE(:MN_assd_usagetype1,0)=0 then COALESCE(MN_assd_usagetype1,0) else COALESCE(:MN_assd_usagetype1,0) end) ");
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            queryString.append(
                    " and MN_assd_usagetype2=(case when COALESCE(:MN_assd_usagetype2,0)=0 then COALESCE(MN_assd_usagetype2,0) else COALESCE(:MN_assd_usagetype2,0) end)");
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            queryString.append(
                    " and MN_assd_usagetype3=(case when COALESCE(:MN_assd_usagetype3,0)=0 then COALESCE(MN_assd_usagetype3,0) else COALESCE(:MN_assd_usagetype3,0) end)");
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            queryString.append(
                    " and  MN_assd_usagetype4=(case when COALESCE(:MN_assd_usagetype4,0)=0 then COALESCE(MN_assd_usagetype4,0) else COALESCE(:MN_assd_usagetype4,0) end)");
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            queryString.append(
                    " and  MN_assd_usagetype5=(case when COALESCE(: MN_assd_usagetype5,0)=0 then COALESCE( MN_assd_usagetype5,0) else COALESCE(: MN_assd_usagetype5,0) end)");
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            queryString.append(
                    " and MN_assd_constru_type=(case when COALESCE(:MN_assd_constru_type,0)=0 then COALESCE(MN_assd_constru_type,0) else COALESCE(:MN_assd_constru_type,0) end) ");
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            queryString.append(" and PM_PLOT_NO_CS=:tppPlotNoCs ");
        }
        if (!StringUtils.isEmpty(searchDto.getHouseNo())) {
            queryString.append(" and UPPER(PM_PLOT_NO) =:houseNo ");
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            queryString.append(" and UPPER(MN_ASSO_OWNER_NAME) LIKE :assoOwnerName");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            queryString.append(" and MN_ASSO_MOBILENO=:assoMobileno ");
        }
        if (!StringUtils.isEmpty(searchDto.getFromAmout()) && !StringUtils.isEmpty(searchDto.getToAmount())) {
            queryString.append(" and OUTSTANDING BETWEEN :fromAmout and :toAmount ");
        }

        if (gridSearchDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField())
                    || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }

        if (gridSearchDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), " ");
        }

        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> " + queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgid", searchDto.getOrgId());
        query.setParameter("deptId", searchDto.getDeptId());

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }
        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("PM_LOC_ID", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("pm_ward1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("pm_ward2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("pm_ward3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("pm_ward4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("pm_ward5", searchDto.getAssWard5());
        }

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            query.setParameter("PM_PROP_LVL_ROAD_TYPE", searchDto.getPropLvlRoadType());
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            query.setParameter("MN_assd_usagetype1", searchDto.getAssdUsagetype1());
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            query.setParameter("MN_assd_usagetype2", searchDto.getAssdUsagetype2());
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            query.setParameter("MN_assd_usagetype3", searchDto.getAssdUsagetype3());
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            query.setParameter("MN_assd_usagetype4", searchDto.getAssdUsagetype4());
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            query.setParameter("MN_assd_usagetype5", searchDto.getAssdUsagetype5());
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            query.setParameter("MN_assd_constru_type", searchDto.getAssdConstruType());
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", "%" + searchDto.getOwnerName().trim() + "%");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }
        
        if (!StringUtils.isEmpty(searchDto.getHouseNo())) {
            query.setParameter("houseNo", searchDto.getHouseNo().trim().toUpperCase());
        }
        
        if (!StringUtils.isEmpty(searchDto.getFromAmout())) {
            query.setParameter("fromAmout", searchDto.getFromAmout().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getToAmount())) {
            query.setParameter("toAmount", searchDto.getToAmount().trim());
        }
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

    @Override
    public int getTotalSearchCount(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId) {
        String coloumnName = null;
        String referenceNumber = null;

        StringBuilder queryString = new StringBuilder(
                "select distinct(PM_PROP_NO)"
                        +
                        " from tb_as_prop_mas a left join ((select * from tb_as_assesment_detail )d,"
                        +
                        " (select * from TB_AS_ASSESMENT_OWNER_DTL b where MN_asso_otype='P')b)on a.PM_PROP_NO=b.MN_ASS_no and a.orgid=b.orgid and d.mn_ass_id = b.mn_ass_id and d.orgid = b.orgid"
                        +
                        " LEFT JOIN (SELECT X.MN_BM_IDNO, x.MN_PROP_NO, MN_TOTAL_OUTSTANDING AS OUTSTANDING"
                        +
                        " FROM (SELECT MN_BM_IDNO,MN_PROP_NO,(COALESCE(MN_TOTAL_OUTSTANDING, 0) + COALESCE(MN_TOTAL_PENALTY, 0)) AS MN_TOTAL_OUTSTANDING FROM tb_as_bill_mas WHERE orgid = :orgid"
                        +
                        " UNION SELECT pro_bm_idno, pro_prop_no,(COALESCE(pro_bm_total_outstanding, 0) + COALESCE(PRO_BM_TOTAL_PENALTY, 0)) FROM tb_as_pro_bill_mas WHERE  orgid = :orgid) X,"
                        +
                        "(SELECT MN_PROP_NO, MAX(MN_BM_IDNO) MN_BM_IDNO FROM  (SELECT MN_BM_IDNO, MN_PROP_NO FROM   tb_as_bill_mas WHERE  orgid = :orgid"
                        +
                        " UNION SELECT pro_bm_idno, pro_prop_no FROM   tb_as_pro_bill_mas WHERE  orgid = :orgid) Y"
                        +
                        " GROUP BY MN_PROP_NO) Y WHERE  X.MN_BM_IDNO = Y.MN_BM_IDNO) N ON a.PM_PROP_NO = N.MN_PROP_NO"
                        +
                        " left join (select a.ADDITIONAL_REF_NO,a.RM_AMOUNT,a.RM_DATE from tb_receipt_mas a,(select ADDITIONAL_REF_NO,max(RM_RCPTID) RM_RCPTID from tb_receipt_mas where dp_deptid=:deptId and orgid=:orgid"
                        +
                        " group by ADDITIONAL_REF_NO) b where a.ADDITIONAL_REF_NO=b.ADDITIONAL_REF_NO and a.RM_RCPTID=b.RM_RCPTID and a.orgid=:orgid) c ON a.PM_PROP_NO = c.ADDITIONAL_REF_NO"
                        +
                        " where a.orgid=:orgid");

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            queryString.append(" and PM_PROP_NO=:assNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            queryString.append(" and PM_PROP_OLDPROPNO=:assOldpropno ");
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            queryString.append(
                    " and PM_LOC_ID=(case when COALESCE(:PM_LOC_ID,0)=0 then COALESCE(PM_LOC_ID,0) else COALESCE(:PM_LOC_ID,0) end) ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            queryString.append(
                    " and pm_ward1=(case when COALESCE(:pm_ward1,0)=0 then COALESCE(pm_ward1,0) else COALESCE(:pm_ward1,0) end)");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            queryString.append(
                    "and pm_ward2=(case when COALESCE(:pm_ward2,0)=0 then COALESCE(pm_ward2,0) else COALESCE(:pm_ward2,0) end) ");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            // queryString.append(" and WARD3=:assWard3 ");
            queryString.append(
                    "and pm_ward3=(case when COALESCE(:pm_ward3,0)=0 then COALESCE(pm_ward3,0) else COALESCE(:pm_ward3,0) end) ");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            // queryString.append(" and WARD4=:assWard4 ");
            queryString.append(
                    "and pm_ward4=(case when COALESCE(:pm_ward4,0)=0 then COALESCE(pm_ward4,0) else COALESCE(:pm_ward4,0) end) ");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            // queryString.append(" and WARD5=:assWard5 ");
            queryString.append(
                    "and pm_ward5=(case when COALESCE(:pm_ward5,0)=0 then COALESCE(pm_ward5,0) else COALESCE(:pm_ward5,0) end) ");
        }
        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            queryString.append(
                    " and PM_PROP_LVL_ROAD_TYPE=(case when COALESCE(:PM_PROP_LVL_ROAD_TYPE,0)=0 then COALESCE(PM_PROP_LVL_ROAD_TYPE,0) else COALESCE(:PM_PROP_LVL_ROAD_TYPE,0) end) ");
        }

        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            queryString.append(
                    " and MN_assd_usagetype1=(case when COALESCE(:MN_assd_usagetype1,0)=0 then COALESCE(MN_assd_usagetype1,0) else COALESCE(:MN_assd_usagetype1,0) end)");
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            queryString.append(
                    "and MN_assd_usagetype2=(case when COALESCE(:MN_assd_usagetype2,0)=0 then COALESCE(MN_assd_usagetype2,0) else COALESCE(:MN_assd_usagetype2,0) end) ");
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            queryString.append(
                    "and MN_assd_usagetype3=(case when COALESCE(:MN_assd_usagetype3,0)=0 then COALESCE(MN_assd_usagetype3,0) else COALESCE(:MN_assd_usagetype3,0) end) ");
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            queryString.append(
                    "and MN_assd_usagetype4=(case when COALESCE(:MN_assd_usagetype4,0)=0 then COALESCE(MN_assd_usagetype4,0) else COALESCE(:MN_assd_usagetype4,0) end) ");
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            queryString.append(
                    "and MN_assd_usagetype5=(case when COALESCE(:MN_assd_usagetype5,0)=0 then COALESCE(pMN_assd_usagetype5,0) else COALESCE(:MN_assd_usagetype5,0) end) ");
        }

        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            queryString.append(
                    " and MN_assd_constru_type=(case when COALESCE(:MN_assd_constru_type,0)=0 then COALESCE(MN_assd_constru_type,0) else COALESCE(:MN_assd_constru_type,0) end)");
        }
        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            queryString.append(" and UPPER(MN_ASSO_OWNER_NAME) LIKE :assoOwnerName");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            queryString.append(" and MN_ASSO_MOBILENO=:assoMobileno ");
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            queryString.append(" and PM_PLOT_NO_CS=:tppPlotNoCs ");
        }

        if (!StringUtils.isEmpty(searchDto.getFromAmout()) && !StringUtils.isEmpty(searchDto.getToAmount())) {
            queryString.append(" and OUTSTANDING BETWEEN :fromAmout and :toAmount ");
        }

        if (gridSearchDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField())
                    || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }

        if (gridSearchDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "");
        }
        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> " + queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgid", searchDto.getOrgId());
        query.setParameter("deptId", searchDto.getDeptId());

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }
        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("PM_LOC_ID", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("pm_ward1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("pm_ward2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("pm_ward3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("pm_ward4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("pm_ward5", searchDto.getAssWard5());
        }
        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            query.setParameter("PM_PROP_LVL_ROAD_TYPE", searchDto.getPropLvlRoadType());
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            query.setParameter("MN_assd_constru_type", searchDto.getAssdConstruType());
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            query.setParameter("MN_assd_usagetype1", searchDto.getAssdUsagetype1());
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            query.setParameter("MN_assd_usagetype2", searchDto.getAssdUsagetype2());
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            query.setParameter("MN_assd_usagetype3", searchDto.getAssdUsagetype3());
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            query.setParameter("MN_assd_usagetype4", searchDto.getAssdUsagetype4());
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            query.setParameter("MN_assd_usagetype5", searchDto.getAssdUsagetype5());
        }
        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", "%" + searchDto.getOwnerName().trim() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getFromAmout())) {
            query.setParameter("fromAmout", searchDto.getFromAmout().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getToAmount())) {
            query.setParameter("toAmount", searchDto.getToAmount().trim());
        }

        return query.getResultList().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int validateProperty(String propNo, Long orgId, Long serviceId) {

        StringBuilder queryString = new StringBuilder("select "
                + "(select count(provisiona0_.PRO_ASS_ID) as col_0_0_ from TB_AS_PRO_ASSESMENT_MAST provisiona0_ where provisiona0_.PRO_ASS_NO=:propNo and provisiona0_.SM_SERVICE_ID<>:serviceId and provisiona0_.ORGID=:orgId) + "
                + "(select count(assesmentm0_.MN_ASS_id) as col_0_0_ from tb_as_assesment_mast assesmentm0_ where assesmentm0_.MN_ASS_no=:propNo and assesmentm0_.SM_SERVICE_ID<>:serviceId and assesmentm0_.orgid=:orgId) +"
                + "(select count(tbservicer0_.RM_RCPTID) as col_0_0_ from TB_RECEIPT_MAS tbservicer0_ where tbservicer0_.ADDITIONAL_REF_NO=:propNo and tbservicer0_.RECEIPT_DEL_FLAG is null and tbservicer0_.ORGID=:orgId and SM_SERVICE_ID=:serviceId) +"
                + "(select count(mainbillma0_.MN_BM_IDNO) as col_0_0_ from tb_as_bill_mas mainbillma0_ where mainbillma0_.MN_PROP_NO=:propNo and mainbillma0_.orgid=:orgId and (mainbillma0_.MN_GEN_DES is null)) +"
                + "(select count(provisiona0_.pro_bm_idno) as col_0_0_ from TB_AS_PRO_BILL_MAS provisiona0_ where provisiona0_.pro_prop_no=:propNo and provisiona0_.orgid=:orgId and (provisiona0_.PRO_GEN_DES is null)) +"
                + "(select count(c.challan_id)as c from tb_challan_master c where c.REFERENCE_NO=:propNo and c.ORGID=:orgId) e");

        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgId", orgId);
        query.setParameter("propNo", propNo);
        query.setParameter("serviceId", serviceId);

        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public BigInteger[] validateBill(String propNo, Long orgId, Long bmIdno) {

        BigInteger count[] = new BigInteger[2];

        StringBuilder queryStringforReceipt = new StringBuilder(
                "select count(1) from tb_receipt_mas x where rm_rcptid in (select rm_rcptid from tb_receipt_det where bm_idno=:bmIdno) and (select DP_DEPTCODE from tb_department where DP_DEPTID=x.DP_DEPTID)='AS'");

        final Query queryReceipt = createNativeQuery(queryStringforReceipt.toString());

        queryReceipt.setParameter("bmIdno", bmIdno);
        // query.setParameter("serviceId", serviceId);

        count[0] = (BigInteger) queryReceipt.getSingleResult();

        StringBuilder queryStringforProvisinal = new StringBuilder(
                "select count(provisiona0_.PRO_ASS_ID) as col_0_0_ from TB_AS_PRO_ASSESMENT_MAST provisiona0_ where provisiona0_.PRO_ASS_NO=:propNo and provisiona0_.ORGID=:orgId");

        final Query queryProv = createNativeQuery(queryStringforProvisinal.toString());

        queryProv.setParameter("orgId", orgId);
        queryProv.setParameter("propNo", propNo);

        // query.setParameter("serviceId", serviceId);

        count[1] = (BigInteger) queryProv.getSingleResult();
        return count;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> fetchDistrictTehsilVillageId(ProperySearchDto searchDto) {
        StringBuilder q = new StringBuilder(
                "select distinct t.tryDisCode,t.tryTehsilCode,t.tryVillCode from TbAsTryEntity t  where ");
        q.append("t.tryVsrNo=:vsrNo");
        final Query query = createQuery(q.toString());
        query.setParameter("vsrNo", searchDto.getVsrNo());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> searchPropetyDetailsForApi(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        StringBuilder queryString = new StringBuilder(
                "SELECT PM_PROP_NO, PM_PROP_OLDPROPNO, MN_ASSO_OWNER_NAME OWNER_NAME, MN_ASSO_MOBILENO AS MOBILE_NO, PM_PLOT_NO_CS,"
                        +
                        "PM_VILLAGE_MAUJA, PRO_ASS_VSRNO, pm_ward1, pm_ward2, PM_LOC_ID,OUTSTANDING, c.RM_AMOUNT, c.RM_DATE FROM tb_as_prop_mas a"
                        +
                        " JOIN TB_AS_ASSESMENT_OWNER_DTL b ON a.PM_PROP_NO = b.MN_ASS_no LEFT JOIN (SELECT X.MN_BM_IDNO, x.MN_PROP_NO, MN_TOTAL_OUTSTANDING AS OUTSTANDING"
                        +
                        " FROM (SELECT MN_BM_IDNO, MN_PROP_NO,(COALESCE(MN_TOTAL_OUTSTANDING, 0) + COALESCE(MN_TOTAL_PENALTY, 0)) AS MN_TOTAL_OUTSTANDING FROM tb_as_bill_mas"
                        +
                        " UNION SELECT pro_bm_idno, pro_prop_no,(COALESCE(pro_bm_total_outstanding, 0) + COALESCE(PRO_BM_TOTAL_PENALTY, 0)) FROM   tb_as_pro_bill_mas) X,"
                        +
                        "(SELECT MN_PROP_NO, MAX(MN_BM_IDNO) MN_BM_IDNO FROM (SELECT MN_BM_IDNO, MN_PROP_NO FROM tb_as_bill_mas UNION SELECT pro_bm_idno, pro_prop_no FROM tb_as_pro_bill_mas) Y"
                        +
                        " GROUP BY MN_PROP_NO) Y WHERE  X.MN_BM_IDNO = Y.MN_BM_IDNO) N ON a.PM_PROP_NO = N.MN_PROP_NO LEFT JOIN (SELECT a.ADDITIONAL_REF_NO, a.RM_AMOUNT, a.RM_DATE"
                        +
                        " FROM tb_receipt_mas a,(SELECT ADDITIONAL_REF_NO, max(RM_RCPTID) RM_RCPTID FROM tb_receipt_mas GROUP BY ADDITIONAL_REF_NO) b"
                        +
                        " WHERE a.ADDITIONAL_REF_NO = b.ADDITIONAL_REF_NO AND a.RM_RCPTID = b.RM_RCPTID) c ON a.PM_PROP_NO = c.ADDITIONAL_REF_NO WHERE  MN_asso_otype = 'P'");

        if (!StringUtils.isEmpty(searchDto.getKhasraNo()) && StringUtils.isEmpty(searchDto.getVsrNo())) {
            queryString.append(" and PM_PLOT_NO_CS=:tppPlotNoCs ");
        }
        if (!StringUtils.isEmpty(searchDto.getVsrNo()) && StringUtils.isEmpty(searchDto.getKhasraNo())) {
            queryString.append(" and PRO_ASS_VSRNO=:assVsrNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getKhasraNo()) && !StringUtils.isEmpty(searchDto.getVsrNo())) {
            queryString.append(" and PM_PLOT_NO_CS=:tppPlotNoCs and  PRO_ASS_VSRNO=:assVsrNo");
        }
        final Query query = createNativeQuery(queryString.toString());

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getVsrNo())) {
            query.setParameter("assVsrNo", searchDto.getVsrNo().trim());
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getPropertiesToGenerateBill(PropertyReportRequestDto propertyDto, Long orgId) {
        StringBuilder queryString = new StringBuilder(
                "select k.MN_ASS_no from (select max(a.fa_yearid) fa_yearid ,a.MN_ASS_no  from " +
                        "(select max(x.MN_ASS_id) MN_ASS_id, x.fa_yearid,x.MN_ASS_no from TB_AS_ASSESMENT_MAST x " +
                        "where x.orgid=:P_ORGID ");
        if (propertyDto.getMnassward1() != null && propertyDto.getMnassward1() > 0) {
            queryString.append(
                    " and COALESCE(MN_ASS_ward1,0)=(case when COALESCE(:MN_ASS_ward1,0)=0 then COALESCE(:MN_ASS_ward1,0) else COALESCE(:MN_ASS_ward1,0) end) ");
        }
        if (propertyDto.getMnassward2() != null && propertyDto.getMnassward2() > 0) {
            queryString.append(
                    " and COALESCE(MN_ASS_ward2,0)=(case when COALESCE(:MN_ASS_ward2,0)=0 then COALESCE(MN_ASS_ward2,0) else COALESCE(:MN_ASS_ward2,0) end) ");
        }
        if (propertyDto.getMnassward3() != null && propertyDto.getMnassward3() > 0) {
            queryString.append(
                    " and COALESCE(MN_ASS_ward3,0)=(case when COALESCE(:MN_ASS_ward3,0)=0 then COALESCE(MN_ASS_ward3,0) else COALESCE(:MN_ASS_ward3,0) end) ");
        }
        if (propertyDto.getMnassward4() != null && propertyDto.getMnassward4() > 0) {
            queryString.append(
                    " and COALESCE(MN_ASS_ward4,0)=(case when COALESCE(:MN_ASS_ward4,0)=0 then COALESCE(MN_ASS_ward4,0) else COALESCE(:MN_ASS_ward4,0) end) ");
        }
        if (propertyDto.getMnassward5() != null && propertyDto.getMnassward5() > 0) {
            queryString.append(
                    " and COALESCE(MN_ASS_ward5,0)=(case when COALESCE(:MN_ASS_ward5,0)=0 then COALESCE(MN_ASS_ward5,0) else COALESCE(:MN_ASS_ward5,0) end) ");
        }

        queryString.append(
                " group by x.MN_ASS_no,x.fa_yearid union select max(y.PRO_ASS_ID) MN_ASS_id, y.fa_yearid,y.PRO_ASS_NO from TB_AS_PRO_ASSESMENT_MAST y where y.orgid=:P_ORGID ");

        if (propertyDto.getMnassward1() != null && propertyDto.getMnassward1() > 0) {
            queryString.append(
                    " and COALESCE(PRO_ASS_ward1,0)=(case when COALESCE(:PRO_ASS_ward1,0)=0 then COALESCE(PRO_ASS_ward1,0) else COALESCE(:PRO_ASS_ward1,0) end) ");
        }
        if (propertyDto.getMnassward2() != null && propertyDto.getMnassward2() > 0) {
            queryString.append(
                    " and COALESCE(PRO_ASS_ward2,0)=(case when COALESCE(:PRO_ASS_ward2,0)=0 then COALESCE(PRO_ASS_ward2,0) else COALESCE(PRO_ASS_ward2,0) end) ");
        }
        if (propertyDto.getMnassward3() != null && propertyDto.getMnassward3() > 0) {
            queryString.append(
                    " and COALESCE(PRO_ASS_ward3,0)=(case when COALESCE(:PRO_ASS_ward3,0)=0 then COALESCE(PRO_ASS_ward3,0) else COALESCE(:PRO_ASS_ward3,0) end) ");
        }
        if (propertyDto.getMnassward4() != null && propertyDto.getMnassward4() > 0) {
            queryString.append(
                    " and COALESCE(PRO_ASS_ward4,0)=(case when COALESCE(:PRO_ASS_ward4,0)=0 then COALESCE(PRO_ASS_ward4,0) else COALESCE(:PRO_ASS_ward4,0) end) ");
        }
        if (propertyDto.getMnassward5() != null && propertyDto.getMnassward5() > 0) {
            queryString.append(
                    " and COALESCE(PRO_ASS_ward5,0)=(case when COALESCE(:PRO_ASS_ward5,0)=0 then COALESCE(PRO_ASS_ward5,0) else COALESCE(:PRO_ASS_ward5,0) end) ");
        }
        queryString.append(
                " group by y.PRO_ASS_NO,y.fa_yearid) a group by a.MN_ASS_no) k, tb_financialyear j where k.fa_yearid < (select fa_yearid from tb_financialyear");

        if (propertyDto.getMnFromdt() != null) {
            queryString.append(" where :AS_ON_DATE");
        }
        queryString.append(" between FA_FROMDATE and FA_TODATE) and k.FA_YEARID = j.FA_YEARId");

        final Query query = createNativeQuery(queryString.toString());
        query.setParameter("P_ORGID", orgId);

        if (propertyDto.getMnassward1() != null && propertyDto.getMnassward1() > 0) {
            query.setParameter("MN_ASS_ward1", propertyDto.getMnassward1());
        }
        if (propertyDto.getMnassward2() != null && propertyDto.getMnassward2() > 0) {
            query.setParameter("MN_ASS_ward2", propertyDto.getMnassward2());
        }
        if (propertyDto.getMnassward3() != null && propertyDto.getMnassward3() > 0) {
            query.setParameter("MN_ASS_ward3", propertyDto.getMnassward3());
        }
        if (propertyDto.getMnassward4() != null && propertyDto.getMnassward4() > 0) {
            query.setParameter("MN_ASS_ward4", propertyDto.getMnassward4());
        }
        if (propertyDto.getMnassward5() != null && propertyDto.getMnassward5() > 0) {
            query.setParameter("MN_ASS_ward5", propertyDto.getMnassward5());
        }
        if (propertyDto.getMnFromdt() != null) {
            query.setParameter("AS_ON_DATE", propertyDto.getMnFromdt());
        }
        if (propertyDto.getMnassward1() != null && propertyDto.getMnassward1() > 0) {
            query.setParameter("PRO_ASS_ward1", propertyDto.getMnassward1());
        }
        if (propertyDto.getMnassward2() != null && propertyDto.getMnassward2() > 0) {
            query.setParameter("PRO_ASS_ward2", propertyDto.getMnassward2());
        }
        if (propertyDto.getMnassward3() != null && propertyDto.getMnassward3() > 0) {
            query.setParameter("PRO_ASS_ward3", propertyDto.getMnassward3());
        }
        if (propertyDto.getMnassward4() != null && propertyDto.getMnassward4() > 0) {
            query.setParameter("PRO_ASS_ward4", propertyDto.getMnassward4());
        }
        if (propertyDto.getMnassward5() != null && propertyDto.getMnassward5() > 0) {
            query.setParameter("PRO_ASS_ward5", propertyDto.getMnassward5());
        }
        return query.getResultList();
    }

    @Override
    public boolean CheckForAssesmentFiledInCurrentYearOrNot(long orgId, String assNo, String assOldpropno, String status,
            Long finYearId) {
        StringBuilder queryString = new StringBuilder(
                "from ProvisionalAssesmentMstEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from ProvisionalAssesmentMstEntity am  WHERE  am.orgId=:orgId and"
                        + "   am.faYearId=:faYearId ");
        if (assNo != null && !assNo.isEmpty()) {
            queryString.append(" and am.assNo=:assNo ");
        }
        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }
        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("faYearId", finYearId);
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }
        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            query.setParameter("assOldpropno", assOldpropno);
        }
        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            log.info("No  provisional Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchPropetyForViewForAll(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        String coloumnName = null;
        String referenceNumber = null;

        StringBuilder queryString = new StringBuilder(
                "SELECT  b.MN_ASS_NO,b.MN_ASS_OLDPROPNO,MN_ASSO_OWNER_NAME,MN_asso_mobileno,b.MN_FLAT_NO,b.CPD_BILLMETH,MN_asso_guardian_name,d.MN_ASS_EMAIL,b.MN_ASS_oldpropno,b.MN_plot_no,b.MN_ASS_ward1,b.MN_ASS_ward2,b.MN_ASS_ward3,b.MN_ASS_ward4,b.MN_ASS_ward5,LOGICAL_PROP_NO,b.MN_ASS_ADDRESS,ad.MN_assd_occupier_name,b.MN_SPL_NOT_DUE_DATE "
                        + " FROM tb_as_assesment_owner_dtl d,tb_as_assesment_mast b , tb_as_assesment_detail ad, ");

        StringBuilder nestedQueryString1 = new StringBuilder(
                " (select mn_ass_no,MN_FLAT_NO,max(mn_ass_id) mn_ass_id from tb_as_assesment_mast where orgid=:orgid  ");

        StringBuilder nestedQueryString2 = new StringBuilder(
                " where b.orgid=:orgid AND d.mn_asso_otype = 'P'  AND e.MN_ASS_ID=b.MN_ASS_ID  AND b.MN_ASS_ID=d.MN_ASS_ID and b.MN_ASS_ID =ad.MN_ASS_ID");

        // Added for - bill should generate for active properties (A will come in case of SKDCL bill generation)
        if (searchDto.getStatusFlag() != null && searchDto.getStatusFlag().equals(MainetConstants.FlagA)) {
            nestedQueryString2.append(" AND b.MN_ASS_active = 'A' ");
        }

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            nestedQueryString1.append(" and MN_ASS_NO=:assNo ");
            nestedQueryString2.append(" and b.MN_ASS_NO=:assNo ");
        }
        
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            nestedQueryString1.append(" and MN_ASS_oldpropno=:assOldpropno ");
            nestedQueryString2.append(" and b.MN_ASS_oldpropno=:assOldpropno ");
        }

        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            nestedQueryString1.append(" and MN_FLAT_NO=:flatNo ");
            nestedQueryString2.append(" and b.MN_FLAT_NO=:flatNo ");
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            nestedQueryString1.append(
                    " and MN_loc_id=:PM_LOC_ID ");
            nestedQueryString2.append(
                    " and b.MN_loc_id=:PM_LOC_ID ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS_ward1=:pm_ward1");
            nestedQueryString2.append(
                    " and b.MN_ASS_ward1=:pm_ward1");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS_ward2=:pm_ward2");
            nestedQueryString2.append(
                    " and b.MN_ASS_ward2=:pm_ward2");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS_ward3=:pm_ward3");
            nestedQueryString2.append(
                    " and b.MN_ASS_ward3=:pm_ward3");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS_ward4=:pm_ward4");
            nestedQueryString2.append(
                    " and b.MN_ASS_ward4=:pm_ward4");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS_ward5=:pm_ward5");
            nestedQueryString2.append(
                    " and b.MN_ASS_ward5=:pm_ward5");
        }
        if (searchDto.getAssParshadWard1() != null && searchDto.getAssParshadWard1() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS__PARSH_WARD1=:MN_ASS__PARSH_WARD1");
            nestedQueryString2.append(
                    " and b.MN_ASS__PARSH_WARD1=:MN_ASS__PARSH_WARD1");
        }
        if (searchDto.getAssParshadWard2() != null && searchDto.getAssParshadWard2() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS__PARSH_WARD2=:MN_ASS__PARSH_WARD2");
            nestedQueryString2.append(
                    " and b.MN_ASS__PARSH_WARD2=:MN_ASS__PARSH_WARD2");
        }
        if (searchDto.getAssParshadWard3() != null && searchDto.getAssParshadWard3() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS__PARSH_WARD3=:MN_ASS__PARSH_WARD3");
            nestedQueryString2.append(
                    " and b.MN_ASS__PARSH_WARD3=:MN_ASS__PARSH_WARD3");
        }
        if (searchDto.getAssParshadWard4() != null && searchDto.getAssParshadWard4() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS__PARSH_WARD4=:MN_ASS__PARSH_WARD4");
            nestedQueryString2.append(
                    " and b.MN_ASS__PARSH_WARD4=:MN_ASS__PARSH_WARD4");
        }
        if (searchDto.getAssParshadWard5() != null && searchDto.getAssParshadWard5() > 0) {
            nestedQueryString1.append(
                    " and MN_ASS__PARSH_WARD5=:MN_ASS__PARSH_WARD5");
            nestedQueryString2.append(
                    " and b.MN_ASS__PARSH_WARD5=:MN_ASS__PARSH_WARD5");
        }
        
        if (org.apache.commons.lang.StringUtils.isNotBlank(searchDto.getHouseNo())) {
            nestedQueryString1.append(" and MN_plot_no=:tppPlotNo ");
            nestedQueryString2.append(" and b.MN_plot_no=:tppPlotNo ");
        }
        
        if (org.apache.commons.lang.StringUtils.isNotBlank(searchDto.getNewHouseNo())) {
            nestedQueryString1.append(" and new_house_no=:newHouseNo ");
            nestedQueryString2.append(" and b.new_house_no=:newHouseNo ");
        }        

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            nestedQueryString1.append(
                    " and MN_PROP_LVL_ROAD_TYPE=:PM_PROP_LVL_ROAD_TYPE");
            nestedQueryString2.append(
                    " and b.MN_PROP_LVL_ROAD_TYPE=:PM_PROP_LVL_ROAD_TYPE");
        }
        
		if (searchDto.getParentGrp1() != null && searchDto.getParentGrp1() != 0) {
			nestedQueryString2.append(" and b.parent_prop_no=:parentPropNo");
		}
		if (searchDto.getParentGrp2() != null && searchDto.getParentGrp2() != 0) {
			nestedQueryString2.append(" and b.group_prop_no=:groupPropNo");
		}
		if (org.apache.commons.lang3.StringUtils.equals(searchDto.getSpecNotSearchType(), "IV")) {
			nestedQueryString2.append(" and ( b.is_group is null or b.is_group <> 'Y')  ");
		}

        StringBuilder subquery = new StringBuilder(
                " and b.MN_ass_id in (select pd.MN_ass_id from tb_as_assesment_detail pd where ");
        boolean subAppli = false;
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            subquery.append(
                    " pd.MN_assd_usagetype1=:MN_assd_usagetype1 ");
            subAppli = true;
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            subquery.append(
                    "  and pd.MN_assd_usagetype2=:MN_assd_usagetype2 ");
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype3=:MN_assd_usagetype3 ");
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype4=:MN_assd_usagetype4 ");
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype5=:MN_assd_usagetype5 ");
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            if (subAppli) {
                subquery.append(" and  pd.MN_assd_constru_type=:MN_assd_constru_type ");
            } else {
                nestedQueryString2.append(
                        " and b.MN_ass_id in (select pd.MN_ass_id from tb_as_assesment_detail pd where pd.MN_assd_constru_type=:MN_assd_constru_type) ");
            }
        }
        if(!StringUtils.isEmpty(searchDto.getOccupierName())) {
            subquery.append(" pd.MN_assd_occupier_name =:occupierName ");
            subAppli = true;
        }
        subquery.append(" ) ");
        if (subAppli) {
            nestedQueryString2.append(subquery.toString());
        }
        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            nestedQueryString1.append(" and MN_plot_no_cs=:tppPlotNoCs ");
            nestedQueryString2.append(" and b.MN_plot_no_cs=:tppPlotNoCs ");
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            nestedQueryString2.append(" and UPPER(MN_ASSO_OWNER_NAME) LIKE :assoOwnerName");
        }
        if (!StringUtils.isEmpty(searchDto.getAddress())) {
            nestedQueryString1.append(" and UPPER(MN_ASS_ADDRESS) LIKE :assAddress");
        }
        
        if (!StringUtils.isEmpty(searchDto.getGuardianName())) {
            nestedQueryString2.append(" and UPPER(MN_ASSO_GUARDIAN_NAME) LIKE :assoGuardianName");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            nestedQueryString2.append(" and MN_ASSO_MOBILENO=:assoMobileno ");
        }

        nestedQueryString1.append(" group by mn_ass_no,MN_FLAT_NO ) e ");
        queryString.append(nestedQueryString1.toString());
        queryString.append(nestedQueryString2.toString());

        if (gridSearchDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField())
                    || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }

        /*
         * if (gridSearchDTO != null) { GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
         * MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), " "); }
         */

        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> " + queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgid", searchDto.getOrgId());

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            query.setParameter("flatNo", searchDto.getFlatNo().trim());
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(searchDto.getHouseNo())) {
            query.setParameter("tppPlotNo", searchDto.getHouseNo());
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(searchDto.getNewHouseNo())) {
            query.setParameter("newHouseNo", searchDto.getNewHouseNo());
        }
        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("PM_LOC_ID", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("pm_ward1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("pm_ward2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("pm_ward3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("pm_ward4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("pm_ward5", searchDto.getAssWard5());
        }

        
        if (searchDto.getAssParshadWard1() != null && searchDto.getAssParshadWard1() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD1", searchDto.getAssParshadWard1());
        }
        if (searchDto.getAssParshadWard2() != null && searchDto.getAssParshadWard2() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD2", searchDto.getAssParshadWard2());
        }
        if (searchDto.getAssParshadWard3() != null && searchDto.getAssParshadWard3() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD3", searchDto.getAssParshadWard3());
        }
        if (searchDto.getAssParshadWard4() != null && searchDto.getAssParshadWard4() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD4", searchDto.getAssParshadWard4());
        }
        if (searchDto.getAssParshadWard5() != null && searchDto.getAssParshadWard5() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD5", searchDto.getAssParshadWard5());
        }
        
        
        
        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            query.setParameter("PM_PROP_LVL_ROAD_TYPE", searchDto.getPropLvlRoadType());
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            query.setParameter("MN_assd_usagetype1", searchDto.getAssdUsagetype1());
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            query.setParameter("MN_assd_usagetype2", searchDto.getAssdUsagetype2());
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            query.setParameter("MN_assd_usagetype3", searchDto.getAssdUsagetype3());
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            query.setParameter("MN_assd_usagetype4", searchDto.getAssdUsagetype4());
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            query.setParameter("MN_assd_usagetype5", searchDto.getAssdUsagetype5());
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            query.setParameter("MN_assd_constru_type", searchDto.getAssdConstruType());
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", "%" + searchDto.getOwnerName().trim() + "%");
        }

        if (!StringUtils.isEmpty(searchDto.getAddress())) {
            query.setParameter("assAddress", "%" + searchDto.getAddress().trim() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getOccupierName())) {
            query.setParameter("occupierName", searchDto.getOccupierName().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getGuardianName())) {
            query.setParameter("assoGuardianName", "%" + searchDto.getGuardianName().trim() + "%");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getFromAmout())) {
            query.setParameter("fromAmout", searchDto.getFromAmout().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getToAmount())) {
            query.setParameter("toAmount", searchDto.getToAmount().trim());
        }
		if (searchDto.getParentGrp1() != null && searchDto.getParentGrp1() != 0) {
			query.setParameter("parentPropNo", searchDto.getParentGrp1());
		}
		if (searchDto.getParentGrp2() != null && searchDto.getParentGrp2() != 0) {
			query.setParameter("groupPropNo", searchDto.getParentGrp2());
		}
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

    @Override
    public int getTotalSearchCountForAll(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder(
                " select sum(a) from (SELECT   count(distinct concat(b.MN_ASS_NO,coalesce(MN_FLAT_NO,0))) a FROM tb_as_assesment_owner_dtl d ,tb_as_assesment_mast b  "
                        + "  where b.orgid=:orgid and d.mn_asso_otype = 'P' and b.MN_ASS_ID=d.MN_ASS_ID ");

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            queryString.append(" and b.MN_ASS_NO=:assNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getHouseNo())) {
            queryString.append(" and b.MN_plot_no=:tppPlotNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getNewHouseNo())) {
            queryString.append(" and b.new_house_no=:newHouseNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            queryString.append(" and b.MN_ASS_oldpropno=:assOldpropno ");
        }

        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            queryString.append(" and b.MN_FLAT_NO=:flatNo ");
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            queryString.append(
                    " and b.MN_loc_id=:PM_LOC_ID ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            queryString.append(
                    " and b.MN_ASS_ward1=:pm_ward1");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            queryString.append(
                    " and b.MN_ASS_ward2=:pm_ward2");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            queryString.append(
                    " and b.MN_ASS_ward3=:pm_ward3");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            queryString.append(
                    " and b.MN_ASS_ward4=:pm_ward4");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            queryString.append(
                    " and b.MN_ASS_ward5=:pm_ward5");
        }
        if (searchDto.getAssParshadWard1() != null && searchDto.getAssParshadWard1() > 0) {
        	queryString.append(
                    " and MN_ASS__PARSH_WARD1=:MN_ASS__PARSH_WARD1");
        	queryString.append(
                    " and b.MN_ASS__PARSH_WARD1=:MN_ASS__PARSH_WARD1");
        }
        if (searchDto.getAssParshadWard2() != null && searchDto.getAssParshadWard2() > 0) {
        	queryString.append(
                    " and MN_ASS__PARSH_WARD2=:MN_ASS__PARSH_WARD2");
        	queryString.append(
                    " and b.MN_ASS__PARSH_WARD2=:MN_ASS__PARSH_WARD2");
        }
        if (searchDto.getAssParshadWard3() != null && searchDto.getAssParshadWard3() > 0) {
        	queryString.append(
                    " and MN_ASS__PARSH_WARD3=:MN_ASS__PARSH_WARD3");
        	queryString.append(
                    " and b.MN_ASS__PARSH_WARD3=:MN_ASS__PARSH_WARD3");
        }
        if (searchDto.getAssParshadWard4() != null && searchDto.getAssParshadWard4() > 0) {
        	queryString.append(
                    " and MN_ASS__PARSH_WARD4=:MN_ASS__PARSH_WARD4");
        	queryString.append(
                    " and b.MN_ASS__PARSH_WARD4=:MN_ASS__PARSH_WARD4");
        }
        if (searchDto.getAssParshadWard5() != null && searchDto.getAssParshadWard5() > 0) {
        	queryString.append(
                    " and MN_ASS__PARSH_WARD5=:MN_ASS__PARSH_WARD5");
        	queryString.append(
                    " and b.MN_ASS__PARSH_WARD5=:MN_ASS__PARSH_WARD5");
        }

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            queryString.append(
                    " and b.MN_PROP_LVL_ROAD_TYPE=:PM_PROP_LVL_ROAD_TYPE");
        }
        
		if (searchDto.getParentGrp1() != null && searchDto.getParentGrp1() != 0) {
			queryString.append(" and b.parent_prop_no=:parentPropNo");
		}
		if (searchDto.getParentGrp2() != null && searchDto.getParentGrp2() != 0) {
			queryString.append(" and b.group_prop_no=:groupPropNo");
		}
		if (org.apache.commons.lang3.StringUtils.equals(searchDto.getSpecNotSearchType(), "IV")) {
			queryString.append(" and ( b.is_group is null or b.is_group <> 'Y')  ");
		}
        StringBuilder subquery = new StringBuilder(
                " and b.MN_ass_id in (select pd.MN_ass_id from tb_as_assesment_detail pd where ");
        boolean subAppli = false;
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            subquery.append(
                    " pd.MN_assd_usagetype1=:MN_assd_usagetype1 ");
            subAppli = true;
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            subquery.append(
                    "  and pd.MN_assd_usagetype2=:MN_assd_usagetype2 ");
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype3=:MN_assd_usagetype3 ");
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype4=:MN_assd_usagetype4 ");
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            subquery.append(
                    " and  pd.MN_assd_usagetype5=:MN_assd_usagetype5 ");
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            if (subAppli) {
                subquery.append(" and  pd.MN_assd_constru_type=:MN_assd_constru_type ");
            } else {
                queryString.append(
                        " and b.MN_ass_id in (select pd.MN_ass_id from tb_as_assesment_detail pd where pd.MN_assd_constru_type=:MN_assd_constru_type) ");
            }
        }
        if (!StringUtils.isEmpty(searchDto.getOccupierName())) {
            subquery.append(" pd.MN_assd_occupier_name =:occupierName");
            subAppli = true;
        }
        subquery.append(" ) ");
        if (subAppli) {
            queryString.append(subquery.toString());
        }
        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            queryString.append(" and b.MN_plot_no_cs=:tppPlotNoCs ");
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            queryString.append(" and UPPER(MN_ASSO_OWNER_NAME) LIKE :assoOwnerName");
        }
        
        if (!StringUtils.isEmpty(searchDto.getAddress())) {
            queryString.append(" and UPPER(MN_ASS_ADDRESS) LIKE :assAddress");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            queryString.append(" and MN_ASSO_MOBILENO=:assoMobileno ");
        }

        /*
         * if (!StringUtils.isEmpty(searchDto.getFromAmout()) && !StringUtils.isEmpty(searchDto.getToAmount())) {
         * queryString.append(" and OUTSTANDING BETWEEN :fromAmout and :toAmount "); }
         */
        queryString.append(" group by  concat(b.MN_ASS_NO,coalesce(MN_FLAT_NO,0)) ) z ");

        if (gridSearchDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField())
                    || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }

        /*
         * if (gridSearchDTO != null) { GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
         * MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), ""); }
         */
        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> " + queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgid", searchDto.getOrgId());
        // query.setParameter("deptId", searchDto.getDeptId());

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getHouseNo())) {
            query.setParameter("tppPlotNo", searchDto.getHouseNo());
        }
        if (!StringUtils.isEmpty(searchDto.getNewHouseNo())) {
            query.setParameter("newHouseNo", searchDto.getNewHouseNo());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            query.setParameter("flatNo", searchDto.getFlatNo().trim());
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("PM_LOC_ID", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("pm_ward1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("pm_ward2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("pm_ward3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("pm_ward4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("pm_ward5", searchDto.getAssWard5());
        }
        
        if (searchDto.getAssParshadWard1() != null && searchDto.getAssParshadWard1() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD1", searchDto.getAssParshadWard1());
        }
        if (searchDto.getAssParshadWard2() != null && searchDto.getAssParshadWard2() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD2", searchDto.getAssParshadWard2());
        }
        if (searchDto.getAssParshadWard3() != null && searchDto.getAssParshadWard3() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD3", searchDto.getAssParshadWard3());
        }
        if (searchDto.getAssParshadWard4() != null && searchDto.getAssParshadWard4() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD4", searchDto.getAssParshadWard4());
        }
        if (searchDto.getAssParshadWard5() != null && searchDto.getAssParshadWard5() > 0) {
            query.setParameter("MN_ASS__PARSH_WARD5", searchDto.getAssParshadWard5());
        }
        
        
        
        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            query.setParameter("PM_PROP_LVL_ROAD_TYPE", searchDto.getPropLvlRoadType());
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            query.setParameter("MN_assd_constru_type", searchDto.getAssdConstruType());
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            query.setParameter("MN_assd_usagetype1", searchDto.getAssdUsagetype1());
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            query.setParameter("MN_assd_usagetype2", searchDto.getAssdUsagetype2());
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            query.setParameter("MN_assd_usagetype3", searchDto.getAssdUsagetype3());
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            query.setParameter("MN_assd_usagetype4", searchDto.getAssdUsagetype4());
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            query.setParameter("MN_assd_usagetype5", searchDto.getAssdUsagetype5());
        }
        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", "%" + searchDto.getOwnerName().trim() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getAddress())) {
            query.setParameter("assAddress", "%" + searchDto.getAddress().trim() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getOccupierName())) {
            query.setParameter("occupierName", searchDto.getOccupierName().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getFromAmout())) {
            query.setParameter("fromAmout", searchDto.getFromAmout().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getToAmount())) {
            query.setParameter("toAmount", searchDto.getToAmount().trim());
        }
        if (searchDto.getParentGrp1() != null && searchDto.getParentGrp1() != 0) {
			query.setParameter("parentPropNo", searchDto.getParentGrp1());
		}
		if (searchDto.getParentGrp2() != null && searchDto.getParentGrp2() != 0) {
			query.setParameter("groupPropNo", searchDto.getParentGrp2());
		}
        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();
    }

    @Override
    public int validatePropertyWithFlat(String propNo, String flatNo, long orgid, Long serviceId) {

        StringBuilder queryString = new StringBuilder("select "
                + "(select count(provisiona0_.PRO_ASS_ID) as col_0_0_ from TB_AS_PRO_ASSESMENT_MAST provisiona0_ where provisiona0_.PRO_ASS_NO=:propNo and provisiona0_.PRO_FLAT_NO=:flatNo and provisiona0_.SM_SERVICE_ID<>:serviceId and provisiona0_.ORGID=:orgId) + "
                + " (select count(assesmentm0_.MN_ASS_id) as col_0_0_ from tb_as_assesment_mast assesmentm0_ where assesmentm0_.MN_ASS_no=:propNo and assesmentm0_.MN_FLAT_NO=:flatNo and assesmentm0_.SM_SERVICE_ID<>:serviceId and assesmentm0_.orgid=:orgId) +"
                + " (select count(mainbillma0_.MN_BM_IDNO) as col_0_0_ from tb_as_bill_mas mainbillma0_ where mainbillma0_.MN_PROP_NO=:propNo and mainbillma0_.PD_FLATNO=:flatNo and mainbillma0_.orgid=:orgId and (mainbillma0_.MN_GEN_DES is null)) +"
                + " (select count(provisiona0_.pro_bm_idno) as col_0_0_ from TB_AS_PRO_BILL_MAS provisiona0_ where provisiona0_.pro_prop_no=:propNo and provisiona0_.PD_FLATNO=:flatNo and provisiona0_.orgid=:orgId and (provisiona0_.PRO_GEN_DES is null)) ");

        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgId", orgid);
        query.setParameter("propNo", propNo);
        query.setParameter("flatNo", flatNo);
        query.setParameter("serviceId", serviceId);

        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchPropetyForViewForBillGeneration(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, List<String> propNos) {
        String coloumnName = null;
        String referenceNumber = null;

        StringBuilder queryString = new StringBuilder("select PM_PROP_NO,PM_PROP_OLDPROPNO from tb_as_prop_mas a"
                +
                " where a.orgid=:orgid and a.PM_ACTIVE='A' ");  // Active flag added for bill should generate for active
                                                                // properties

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            queryString.append(" and PM_PROP_NO=:assNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            queryString.append(" and PM_PROP_OLDPROPNO=:assOldpropno ");
        }

        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            queryString.append(" and PD_FLATNO=:flatNo ");
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            queryString.append(
                    " and PM_LOC_ID=:PM_LOC_ID ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            queryString.append(
                    " and pm_ward1=:pm_ward1");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            queryString.append(
                    " and pm_ward2=:pm_ward2");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            queryString.append(
                    " and pm_ward3=:pm_ward3");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            queryString.append(
                    " and pm_ward4=:pm_ward4");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            queryString.append(
                    " and pm_ward5=:pm_ward5");
        }

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            queryString.append(
                    " and PM_PROP_LVL_ROAD_TYPE=:PM_PROP_LVL_ROAD_TYPE");
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            queryString.append(
                    " and MN_assd_usagetype1=:MN_assd_usagetype1");
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            queryString.append(
                    " and MN_assd_usagetype2=:MN_assd_usagetype2");
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            queryString.append(
                    " and MN_assd_usagetype3=:MN_assd_usagetype3");
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            queryString.append(
                    " and  MN_assd_usagetype4=:MN_assd_usagetype4");
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            queryString.append(
                    " and  MN_assd_usagetype5=: MN_assd_usagetype5");
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            /*
             * queryString.append( " and MN_assd_constru_type=:MN_assd_constru_type");
             */

            queryString.append(
                    " and PM_PROPID in (select pd.PM_PROPID from tb_as_prop_det pd where pd.PD_CONSTRU_TYPE=:MN_assd_constru_type) ");
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            queryString.append(" and PM_PLOT_NO_CS=:tppPlotNoCs ");
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            queryString.append(" and UPPER(MN_ASSO_OWNER_NAME) LIKE :assoOwnerName");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            queryString.append(" and MN_ASSO_MOBILENO=:assoMobileno ");
        }
        if (!StringUtils.isEmpty(searchDto.getFromAmout()) && !StringUtils.isEmpty(searchDto.getToAmount())) {
            queryString.append(" and OUTSTANDING BETWEEN :fromAmout and :toAmount ");
        }

        queryString.append("  and (is_group is null or is_group <> 'Y')  ");
        
        if (gridSearchDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField())
                    || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = " ";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }

        if (gridSearchDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), " ");
        }

        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> " + queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgid", searchDto.getOrgId());
        // query.setParameter("deptId", searchDto.getDeptId());

        /*
         * if (!StringUtils.isEmpty(searchDto.getGroupPropNo())) { query.setParameter("groupPropNo",
         * searchDto.getGroupPropNo().trim()); }
         */

        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
            query.setParameter("flatNo", searchDto.getFlatNo().trim());
        }

        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("PM_LOC_ID", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("pm_ward1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("pm_ward2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("pm_ward3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("pm_ward4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("pm_ward5", searchDto.getAssWard5());
        }

        if (searchDto.getPropLvlRoadType() != null && searchDto.getPropLvlRoadType() > 0) {
            query.setParameter("PM_PROP_LVL_ROAD_TYPE", searchDto.getPropLvlRoadType());
        }
        if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
            query.setParameter("MN_assd_usagetype1", searchDto.getAssdUsagetype1());
        }
        if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
            query.setParameter("MN_assd_usagetype2", searchDto.getAssdUsagetype2());
        }
        if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
            query.setParameter("MN_assd_usagetype3", searchDto.getAssdUsagetype3());
        }
        if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
            query.setParameter("MN_assd_usagetype4", searchDto.getAssdUsagetype4());
        }
        if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
            query.setParameter("MN_assd_usagetype5", searchDto.getAssdUsagetype5());
        }
        if (searchDto.getAssdConstruType() != null && searchDto.getAssdConstruType() > 0) {
            query.setParameter("MN_assd_constru_type", searchDto.getAssdConstruType());
        }

        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", "%" + searchDto.getOwnerName().trim() + "%");
        }

        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getKhasraNo())) {
            query.setParameter("tppPlotNoCs", searchDto.getKhasraNo().trim());
        }

        if (!StringUtils.isEmpty(searchDto.getFromAmout())) {
            query.setParameter("fromAmout", searchDto.getFromAmout().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getToAmount())) {
            query.setParameter("toAmount", searchDto.getToAmount().trim());
        }
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

}
