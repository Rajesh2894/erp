package com.abm.mainet.property.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.GridPaginationUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Repository
public class AssessmentMastDaoImpl extends AbstractDAO<Long> implements IAssessmentMastDao {

    private static Logger log = Logger.getLogger(AssessmentMastDaoImpl.class);

    @Override
    public AssesmentMastEntity fetchAssDetailAssNoOrOldpropno(long orgId, String assNo, String assOldpropno,
            String status,String logicalPropNo) {
    	 Organisation organisation = new Organisation();
      	organisation.setOrgid(orgId);
    	 LookUp billDeletionInactive = null;
         try {
         	billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
         }catch (Exception e) {
        	 log.error("No prefix found for ENV - BDI");
 		}
        AssesmentMastEntity mst = null;
        StringBuilder queryString = new StringBuilder(
                "from AssesmentMastEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from AssesmentMastEntity am  WHERE  am.orgId=:orgId and ( am.assAutStatus='Y' or am.assAutStatus='AC') ");
		if ((billDeletionInactive == null)
				|| org.apache.commons.lang.StringUtils.isBlank(billDeletionInactive.getOtherField())
				|| org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
						MainetConstants.FlagN)) {
        	queryString.append(" and am.assActive=:assActive and ( am.assStatus='N' or am.assStatus='SN')"); 
        }
		else {
			if(!Utility.isEnvPrefixAvailable(organisation, "ASCL") && !Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
				queryString.append(" and am.flag in ('D','U') ");
			}
		}
        if (assNo != null && !assNo.isEmpty()) {
            queryString.append(" and am.assNo=:assNo ");
        }
        if (logicalPropNo != null && !logicalPropNo.isEmpty()) {
            queryString.append(" and am.logicalPropNo=:logicalPropNo ");
        }
		if (assOldpropno != null && !assOldpropno.isEmpty()) {
			queryString.append(" and am.assOldpropno=:assOldpropno ");
		}
		 
        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        if ((billDeletionInactive == null)
				|| org.apache.commons.lang.StringUtils.isBlank(billDeletionInactive.getOtherField())
				|| org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
						MainetConstants.FlagN)) {
        	query.setParameter("assActive", status);
        }
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }
        if (logicalPropNo != null && !logicalPropNo.isEmpty()) {
        	query.setParameter("logicalPropNo",logicalPropNo);
        }
		if (assOldpropno != null && !assOldpropno.isEmpty()) {
			query.setParameter("assOldpropno", assOldpropno);
		}
         
        try {
            mst = (AssesmentMastEntity) query.getSingleResult();
        } catch (Exception e) {
            log.info("No Assessemnt master found for prop No:" + assNo + " OR old Prop No:" + assOldpropno +" "+e);
        }
        return mst;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> fetchAssDetailBySearchCriteriaForBill(long orgId, NoticeGenSearchDto notGenDto,
            List<String> propNos, Long finId) {
        List<AssesmentMastEntity> mst = null;
        StringBuilder queryString = new StringBuilder(
                "select a from AssesmentMastEntity a left join a.assesmentDetailEntityList d,FinancialYear b " +
                        "where a.assNo in(:propNo) " +
                        "and a.faYearId=b.faYear " +
                        "and (DATE_FORMAT(b.faFromDate,'%Y'),a.assNo) in ( " +
                        "select max(DATE_FORMAT(b.faFromDate,'%Y')),a.assNo from AssesmentMastEntity a left join a.assesmentDetailEntityList d,FinancialYear b "
                        +
                        " where a.faYearId = b.faYear " +
                        "and a.assNo in (:propNo ) " +
                        "group by a.assNo) " +
                        "and b.faYear not in (:finId) and a.assAutStatus='Y' and a.assActive='A' ");
        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            queryString.append(" and a.assWard1=:assWard1 ");
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            queryString.append(" and a.assWard2=:assWard2 ");
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            queryString.append(" and a.assWard3=:assWard3 ");
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            queryString.append(" and a.assWard4=:assWard4 ");
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            queryString.append(" and a.assWard5=:assWard5 ");
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            queryString.append(" and a.locId=:locId ");
        }
        // if (notGenDto.getAssOwnerType() != null) {
        // queryString.append(" and a.assOwnerType=:assOwnerType ");
        // }
        // if (notGenDto.getAssPropType1() != null) {
        // queryString.append(" and a.assPropType1=:assPropType1 ");
        // }
        // if (notGenDto.getAssPropType2() != null) {
        // queryString.append(" and a.assPropType2=:assPropType2 ");
        // }
        // if (notGenDto.getAssPropType3() != null) {
        // queryString.append(" and a.assPropType3=:assPropType3 ");
        // }
        // if (notGenDto.getAssPropType4() != null) {
        // queryString.append(" and a.assPropType4=:assPropType4 ");
        // }
        // if (notGenDto.getAssPropType5() != null) {
        // queryString.append(" and a.assPropType5=:assPropType5 ");
        // }
        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
        }
        /*
         * if (notGenDto.getAssdUsagetype2() != null) { //
         * queryString.append(" and a.assesmentDetailEntityList.assdUsagetype2=:assdUsagetype2 ");
         * queryString.append(" and d.assdUsagetype2=:assdUsagetype2 "); } if (notGenDto.getAssdUsagetype3() != null) {
         * queryString.append(" and d.assdUsagetype3=:assdUsagetype3 "); } if (notGenDto.getAssdUsagetype4() != null) {
         * queryString.append(" and d.assdUsagetype4=:assdUsagetype4 "); } if (notGenDto.getAssdUsagetype5() != null) {
         * queryString.append(" and d.assdUsagetype5=:assdUsagetype5 "); }
         */

        final Query query = createQuery(queryString.toString());
        query.setParameter("propNo", propNos);
        query.setParameter("finId", finId);

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            query.setParameter("assWard1", notGenDto.getAssWard1());
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            query.setParameter("assWard2", notGenDto.getAssWard2());
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            query.setParameter("assWard3", notGenDto.getAssWard3());
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            query.setParameter("assWard4", notGenDto.getAssWard4());
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            query.setParameter("assWard5", notGenDto.getAssWard5());
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            query.setParameter("locId", notGenDto.getLocId());
        }
        /*
         * if (notGenDto.getAssOwnerType() != null) { query.setParameter("assOwnerType", notGenDto.getAssOwnerType()); } if
         * (notGenDto.getAssPropType1() != null) { query.setParameter("assPropType1", notGenDto.getAssPropType1()); } if
         * (notGenDto.getAssPropType2() != null) { query.setParameter("assPropType2", notGenDto.getAssPropType2()); } if
         * (notGenDto.getAssPropType3() != null) { query.setParameter("assPropType3", notGenDto.getAssPropType3()); } if
         * (notGenDto.getAssPropType4() != null) { query.setParameter("assPropType4", notGenDto.getAssPropType4()); } if
         * (notGenDto.getAssPropType5() != null) { query.setParameter("assPropType5", notGenDto.getAssPropType5()); }
         */
        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
        }

        mst = query.getResultList();
        return mst;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> fetchAssDetailBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto) {
        List<AssesmentMastEntity> mst = null;
        StringBuilder queryString = new StringBuilder(
                "select a from AssesmentMastEntity a left join a.assesmentDetailEntityList d " +
                        "where (a.assNo,a.proAssId) in ( " +
                        "select  b.assNo,MAX(b.proAssId) from AssesmentMastEntity b where  " +
                        " b.orgId=:orgId and b.assAutStatus='AC'  and b.assStatus='SA' and b.assActive='A' group by b.assNo)");
        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            queryString.append(" and a.assWard1=:assWard1 ");
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            queryString.append(" and a.assWard2=:assWard2 ");
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            queryString.append(" and a.assWard3=:assWard3 ");
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            queryString.append(" and a.assWard4=:assWard4 ");
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            queryString.append(" and a.assWard5=:assWard5 ");
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            queryString.append(" and a.locId=:locId ");
        }
        // if (notGenDto.getAssOwnerType() != null) {
        // queryString.append(" and a.assOwnerType=:assOwnerType ");
        // }
        // if (notGenDto.getAssPropType1() != null) {
        // queryString.append(" and a.assPropType1=:assPropType1 ");
        // }
        // if (notGenDto.getAssPropType2() != null) {
        // queryString.append(" and a.assPropType2=:assPropType2 ");
        // }
        // if (notGenDto.getAssPropType3() != null) {
        // queryString.append(" and a.assPropType3=:assPropType3 ");
        // }
        // if (notGenDto.getAssPropType4() != null) {
        // queryString.append(" and a.assPropType4=:assPropType4 ");
        // }
        // if (notGenDto.getAssPropType5() != null) {
        // queryString.append(" and a.assPropType5=:assPropType5 ");
        // }
        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
        }
        /*
         * if (notGenDto.getAssdUsagetype2() != null) { //
         * queryString.append(" and a.assesmentDetailEntityList.assdUsagetype2=:assdUsagetype2 ");
         * queryString.append(" and d.assdUsagetype2=:assdUsagetype2 "); } if (notGenDto.getAssdUsagetype3() != null) {
         * queryString.append(" and d.assdUsagetype3=:assdUsagetype3 "); } if (notGenDto.getAssdUsagetype4() != null) {
         * queryString.append(" and d.assdUsagetype4=:assdUsagetype4 "); } if (notGenDto.getAssdUsagetype5() != null) {
         * queryString.append(" and d.assdUsagetype5=:assdUsagetype5 "); }
         */

        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            query.setParameter("assWard1", notGenDto.getAssWard1());
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            query.setParameter("assWard2", notGenDto.getAssWard2());
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            query.setParameter("assWard3", notGenDto.getAssWard3());
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            query.setParameter("assWard4", notGenDto.getAssWard4());
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            query.setParameter("assWard5", notGenDto.getAssWard5());
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            query.setParameter("locId", notGenDto.getLocId());
        }
        /*
         * if (notGenDto.getAssOwnerType() != null) { query.setParameter("assOwnerType", notGenDto.getAssOwnerType()); } if
         * (notGenDto.getAssPropType1() != null) { query.setParameter("assPropType1", notGenDto.getAssPropType1()); } if
         * (notGenDto.getAssPropType2() != null) { query.setParameter("assPropType2", notGenDto.getAssPropType2()); } if
         * (notGenDto.getAssPropType3() != null) { query.setParameter("assPropType3", notGenDto.getAssPropType3()); } if
         * (notGenDto.getAssPropType4() != null) { query.setParameter("assPropType4", notGenDto.getAssPropType4()); } if
         * (notGenDto.getAssPropType5() != null) { query.setParameter("assPropType5", notGenDto.getAssPropType5()); }
         */
        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
        }

        mst = query.getResultList();
        return mst;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> fetchAllSpecialNoticePropBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto) {
        List<AssesmentMastEntity> mst = null;
        StringBuilder queryString = new StringBuilder(
                "SELECT a FROM  AssesmentMastEntity a,FinancialYear f,AssessNoticeMasterEntity n  WHERE f.faYear=a.faYearId and a.assNo=n.mnAssNo "
                        +
                        "  AND (DATE_FORMAT(f.faFromDate,'%Y'),a.assNo) in( " +
                        "  select max(DATE_FORMAT(f.faFromDate,'%Y')),a.assNo from AssesmentMastEntity a left join a.assesmentDetailEntityList d,FinancialYear f "
                        +
                        "where f.faYear=a.faYearId and a.assAutStatus='AC'  and a.assStatus='SN' and a.orgId=:orgId and a.assActive='A' ");
        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            queryString.append(" and a.assWard1=:assWard1 ");
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            queryString.append(" and a.assWard2=:assWard2 ");
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            queryString.append(" and a.assWard3=:assWard3 ");
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            queryString.append(" and a.assWard4=:assWard4 ");
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            queryString.append(" and a.assWard5=:assWard5 ");
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            queryString.append(" and a.locId=:locId ");
        }

        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
        }
        queryString.append(" group by a.assNo)");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            query.setParameter("assWard1", notGenDto.getAssWard1());
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            query.setParameter("assWard2", notGenDto.getAssWard2());
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            query.setParameter("assWard3", notGenDto.getAssWard3());
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            query.setParameter("assWard4", notGenDto.getAssWard4());
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            query.setParameter("assWard5", notGenDto.getAssWard5());
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            query.setParameter("locId", notGenDto.getLocId());
        }

        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
        }

        mst = query.getResultList();
        return mst;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> fetchAllObjNotPropBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto) {
        StringBuilder queryString = new StringBuilder(
                "SELECT a FROM  AssesmentMastEntity a,FinancialYear f,NoticeMasterEntity n  WHERE f.faYear=a.faYearId and a.assNo=n.refNo "
                        +
                        "  AND (DATE_FORMAT(f.faFromDate,'%Y'),a.assNo) in( " +
                        "  select max(DATE_FORMAT(f.faFromDate,'%Y')),a.assNo from AssesmentMastEntity a left join a.assesmentDetailEntityList d,FinancialYear f "
                        +
                        "where f.faYear=a.faYearId and a.assAutStatus='AC'  and a.assStatus='SN' and a.orgId=:orgId and a.assActive='A' ");
        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            queryString.append(" and a.assWard1=:assWard1 ");
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            queryString.append(" and a.assWard2=:assWard2 ");
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            queryString.append(" and a.assWard3=:assWard3 ");
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            queryString.append(" and a.assWard4=:assWard4 ");
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            queryString.append(" and a.assWard5=:assWard5 ");
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            queryString.append(" and a.locId=:locId ");
        }

        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
        }
        queryString.append(" group by a.assNo)");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            query.setParameter("assWard1", notGenDto.getAssWard1());
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            query.setParameter("assWard2", notGenDto.getAssWard2());
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            query.setParameter("assWard3", notGenDto.getAssWard3());
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            query.setParameter("assWard4", notGenDto.getAssWard4());
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            query.setParameter("assWard5", notGenDto.getAssWard5());
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            query.setParameter("locId", notGenDto.getLocId());
        }

        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getAllPropBillGeneByPropNo(Long finId, Long orgId) {
        final Query query = createNativeQuery(
                "select MN_BM_IDNO,MN_ASS_ID,MN_PROP_NO,MN_BM_YEAR  from TB_AS_BILL_MAS m where (MN_PROP_NO,MN_BM_IDNO) in (" +
                        "select a.MN_PROP_NO,max(a.MN_BM_IDNO) as MN_BM_IDNO " +
                        "from  " +
                        "(select pro_bm_idno as MN_BM_IDNO,pro_prop_no as MN_PROP_NO from TB_AS_PRO_BILL_MAS where orgid=:orgId "
                        +
                        "union " +
                        "select MN_BM_IDNO,MN_PROP_NO from TB_AS_BILL_MAS where orgid=:orgId ) a " +
                        "group by a.MN_PROP_NO) and MN_BM_YEAR not in (:finId)");
        query.setParameter("finId", finId);
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    @Override
    public int getCountWhetherMaxBmIdExistInMainBill(String propNo, Long orgId) {
        final Query query = createNativeQuery(
                "select count(*) from TB_AS_BILL_MAS m where (MN_PROP_NO,MN_BM_IDNO) in (" +
                        " select a.MN_PROP_NO,max(a.MN_BM_IDNO) as MN_BM_IDNO " +
                        " from  " +
                        " (select pro_bm_idno as MN_BM_IDNO,pro_prop_no as MN_PROP_NO from TB_AS_PRO_BILL_MAS where pro_prop_no=:propNo "
                        +
                        " union select MN_BM_IDNO,MN_PROP_NO from TB_AS_BILL_MAS where MN_PROP_NO=:propNo ) a " +
                        " group by a.MN_PROP_NO) and orgid=:orgId and MN_PROP_NO=:propNo");
        query.setParameter("propNo", propNo);
        query.setParameter("orgId", orgId);
        BigInteger result = (BigInteger) query.getSingleResult();
        return result.intValue();
    }

    @Override
    public List<Object[]> getAllPropBillForPrint(NoticeGenSearchDto specNotSearchDto, long orgid, List<String> propNoList) {
        StringBuilder queryString = new StringBuilder(" select m.propNo,max(m.bmIdno) from MainBillMasEntity m " +
                " where m.propNo in (:porpNo) " +
                "group by  m.propNo");
        final Query query = createQuery(queryString.toString());
        query.setParameter("porpNo", propNoList);
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return result;
    }

    @Override
    public List<Object[]> getAllPropBillForPrintByOldPropNo(NoticeGenSearchDto specNotSearchDto, long orgid,
            List<String> oldNoList) {
        StringBuilder queryString = new StringBuilder(" select m.propNo,max(m.bmIdno) from MainBillMasEntity m " +
                " where  m.propNo in ( " +
                "  select a.assNo from AssesmentMastEntity a where a.assOldpropno in(:oldPropNo))  " +
                "group by  m.propNo");
        final Query query = createQuery(queryString.toString());
        query.setParameter("oldPropNo", oldNoList);
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> fetchBillPrintDetailBySearchCriteria(NoticeGenSearchDto notGenDto, long orgid) {
        StringBuilder queryString = new StringBuilder(" select m.propNo,max(m.bmIdno) from MainBillMasEntity m " +
                " where  m.propNo in ( " +
                "  select a.assNo from AssesmentMastEntity a left join a.assesmentDetailEntityList d where a.orgId=:orgId ");

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            queryString.append(" and a.assWard1=:assWard1 ");
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            queryString.append(" and a.assWard2=:assWard2 ");
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            queryString.append(" and a.assWard3=:assWard3 ");
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            queryString.append(" and a.assWard4=:assWard4 ");
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            queryString.append(" and a.assWard5=:assWard5 ");
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            queryString.append(" and a.locId=:locId ");
        }

        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
        }
		queryString.append("  and (a.isGroup is null or a.isGroup <> 'Y') ");
        
        queryString.append(" ) group by  m.propNo");

        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgid);

        if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
            query.setParameter("assWard1", notGenDto.getAssWard1());
        }
        if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
            query.setParameter("assWard2", notGenDto.getAssWard2());
        }
        if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
            query.setParameter("assWard3", notGenDto.getAssWard3());
        }
        if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
            query.setParameter("assWard4", notGenDto.getAssWard4());
        }
        if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
            query.setParameter("assWard5", notGenDto.getAssWard5());
        }
        if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
            query.setParameter("locId", notGenDto.getLocId());
        }
        if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
            query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
        }

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> getAllPropertyAfterDueDate(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto notGenDto) {
        StringBuilder queryString = new StringBuilder(
                "select a from AssesmentMastEntity a left join a.assesmentDetailEntityList d " +
                        "where (a.assNo,a.proAssId) in " +
                        "(select b.assNo,max(b.proAssId) from AssesmentMastEntity b " +
                        "group by b.assNo) " +
                        "and a.assNo in " +
                        "(select distinct m.propNo from MainBillMasEntity m " +
                        "where m.bmDuedate < CURRENT_DATE ) and a.orgId=:orgId and a.assActive='A' "); // Only active properties should come so flag 'A' is added

        if (propNoList != null && !propNoList.isEmpty()) {
            queryString.append(" and a.assNo in (:assNo) ");
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            queryString.append(" and a.assOldpropno in (:assOldpropno) ");
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                queryString.append(" and a.assWard1=:assWard1 ");
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                queryString.append(" and a.assWard2=:assWard2 ");
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                queryString.append(" and a.assWard3=:assWard3 ");
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                queryString.append(" and a.assWard4=:assWard4 ");
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                queryString.append(" and a.assWard5=:assWard5 ");
            }
            
            
            
            if (notGenDto.getParshadAssWard1() != null && notGenDto.getParshadAssWard1() > 0) {
                queryString.append(" and a.assParshadWard1=:assParshadWard1 ");
            }
            if (notGenDto.getParshadAssWard2() != null && notGenDto.getParshadAssWard2() > 0) {
                queryString.append(" and a.assParshadWard2=:assParshadWard2 ");
            }
            if (notGenDto.getParshadAssWard3() != null && notGenDto.getParshadAssWard3() > 0) {
                queryString.append(" and a.assParshadWard3=:assParshadWard3 ");
            }
            if (notGenDto.getParshadAssWard4() != null && notGenDto.getParshadAssWard4() > 0) {
                queryString.append(" and a.assParshadWard4=:assParshadWard4 ");
            }
            if (notGenDto.getParshadAssWard5() != null && notGenDto.getParshadAssWard5() > 0) {
                queryString.append(" and a.assParshadWard5=:assParshadWard5 ");
            }
            
            
            
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                queryString.append(" and a.locId=:locId ");
            }

            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
            }
        }

        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgid);

        if (propNoList != null && !propNoList.isEmpty()) {
            query.setParameter("assNo", propNoList);
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            query.setParameter("assOldpropno", oldNoList);
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                query.setParameter("assWard1", notGenDto.getAssWard1());
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                query.setParameter("assWard2", notGenDto.getAssWard2());
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                query.setParameter("assWard3", notGenDto.getAssWard3());
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                query.setParameter("assWard4", notGenDto.getAssWard4());
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                query.setParameter("assWard5", notGenDto.getAssWard5());
            }
            
            
            
            
            if (notGenDto.getParshadAssWard1() != null && notGenDto.getParshadAssWard1() > 0) {
                query.setParameter("assParshadWard1", notGenDto.getParshadAssWard1());
            }
            if (notGenDto.getParshadAssWard2() != null && notGenDto.getParshadAssWard2() > 0) {
                query.setParameter("assParshadWard2", notGenDto.getParshadAssWard2());
            }
            if (notGenDto.getParshadAssWard3() != null && notGenDto.getParshadAssWard3() > 0) {
                query.setParameter("assParshadWard3", notGenDto.getParshadAssWard3());
            }
            if (notGenDto.getParshadAssWard4() != null && notGenDto.getParshadAssWard4() > 0) {
                query.setParameter("assParshadWard4", notGenDto.getParshadAssWard4());
            }
            if (notGenDto.getParshadAssWard5() != null && notGenDto.getParshadAssWard5() > 0) {
                query.setParameter("assParshadWard5", notGenDto.getParshadAssWard5());
            }
            
            
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                query.setParameter("locId", notGenDto.getLocId());
            }
            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
            }
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> getNoticesAfterDueDate(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto notGenDto, Long noticeType) {
        StringBuilder queryString = new StringBuilder(
                "select a from AssesmentMastEntity a left join a.assesmentDetailEntityList d where (a.assNo,a.proAssId) in ( " +
                        " select b.assNo,max(b.proAssId) from AssesmentMastEntity b where b.assNo in ( " +
                        " select n.mnAssNo from AssessNoticeMasterEntity n WHERE n.cpdNottyp=:cpdNottyp " +
                        " AND n.mnDuedt < CURRENT_DATE ) " +
                        " group by b.assNo) and a.orgId=:orgId and a.assActive='A' "); // Only active properties should come so flag 'A' is added

        if (propNoList != null && !propNoList.isEmpty()) {
            queryString.append(" and a.assNo in (:assNo) ");
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            queryString.append(" and a.assOldpropno in (:assOldpropno) ");
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                queryString.append(" and a.assWard1=:assWard1 ");
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                queryString.append(" and a.assWard2=:assWard2 ");
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                queryString.append(" and a.assWard3=:assWard3 ");
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                queryString.append(" and a.assWard4=:assWard4 ");
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                queryString.append(" and a.assWard5=:assWard5 ");
            }
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                queryString.append(" and a.locId=:locId ");
            }

            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
            }
        }

        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", orgid);
        query.setParameter("cpdNottyp", noticeType);

        if (propNoList != null && !propNoList.isEmpty()) {
            query.setParameter("assNo", propNoList);
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            query.setParameter("assOldpropno", oldNoList);
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                query.setParameter("assWard1", notGenDto.getAssWard1());
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                query.setParameter("assWard2", notGenDto.getAssWard2());
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                query.setParameter("assWard3", notGenDto.getAssWard3());
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                query.setParameter("assWard4", notGenDto.getAssWard4());
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                query.setParameter("assWard5", notGenDto.getAssWard5());
            }
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                query.setParameter("locId", notGenDto.getLocId());
            }
            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
            }
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> fetchPropertyDemandNoticePrint(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto notGenDto) {
        StringBuilder queryString = new StringBuilder(
                "select a from AssesmentMastEntity a left join a.assesmentDetailEntityList d where (a.assNo,a.proAssId) in ( " +
                        " select b.assNo,max(b.proAssId) from AssesmentMastEntity b where b.assNo in ( " +
                        " select n.mnAssNo from AssessNoticeMasterEntity n WHERE n.cpdNottyp=:cpdNottyp " +
                        "  ) " +
                        " group by b.assNo) and a.orgId=:orgId ");

        if (propNoList != null && !propNoList.isEmpty()) {
            queryString.append(" and a.assNo in (:assNo) ");
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            queryString.append(" and a.assOldpropno in (:assOldpropno) ");
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                queryString.append(" and a.assWard1=:assWard1 ");
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                queryString.append(" and a.assWard2=:assWard2 ");
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                queryString.append(" and a.assWard3=:assWard3 ");
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                queryString.append(" and a.assWard4=:assWard4 ");
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                queryString.append(" and a.assWard5=:assWard5 ");
            }
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                queryString.append(" and a.locId=:locId ");
            }

            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                queryString.append(" and d.assdUsagetype1=:assdUsagetype1  ");
            }
        }

        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", orgid);
        query.setParameter("cpdNottyp", notGenDto.getNoticeType());

        if (propNoList != null && !propNoList.isEmpty()) {
            query.setParameter("assNo", propNoList);
        }
        if (oldNoList != null && !oldNoList.isEmpty()) {
            query.setParameter("assOldpropno", oldNoList);
        } else {
            if (notGenDto.getAssWard1() != null && notGenDto.getAssWard1() > 0) {
                query.setParameter("assWard1", notGenDto.getAssWard1());
            }
            if (notGenDto.getAssWard2() != null && notGenDto.getAssWard2() > 0) {
                query.setParameter("assWard2", notGenDto.getAssWard2());
            }
            if (notGenDto.getAssWard3() != null && notGenDto.getAssWard3() > 0) {
                query.setParameter("assWard3", notGenDto.getAssWard3());
            }
            if (notGenDto.getAssWard4() != null && notGenDto.getAssWard4() > 0) {
                query.setParameter("assWard4", notGenDto.getAssWard4());
            }
            if (notGenDto.getAssWard5() != null && notGenDto.getAssWard5() > 0) {
                query.setParameter("assWard5", notGenDto.getAssWard5());
            }
            if (notGenDto.getLocId() != null && notGenDto.getLocId() > 0) {
                query.setParameter("locId", notGenDto.getLocId());
            }
            if (notGenDto.getAssdUsagetype1() != null && notGenDto.getAssdUsagetype1() > 0) {
                query.setParameter("assdUsagetype1", notGenDto.getAssdUsagetype1());
            }
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AssesmentMastEntity> searchPropetyForView(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder(
                "from AssesmentMastEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from AssesmentMastEntity am join am.assesmentOwnerDetailEntityList o "
                        + "  WHERE am.orgId=:orgId ");
        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.PropertySearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        if (serviceId != null) {
            queryString.append(" and am.smServiceId=:serviceId ");
        }
        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            queryString.append(" and am.assNo=:assNo ");
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }
        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            queryString.append(" and am.locId=:locId ");
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            queryString.append(" and am.assWard1=:assWard1 ");
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            queryString.append(" and am.assWard2=:assWard2 ");
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            queryString.append(" and am.assWard3=:assWard3 ");
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            queryString.append(" and am.assWard4=:assWard4 ");
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            queryString.append(" and am.assWard5=:assWard5 ");
        }
        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            queryString.append(" and o.assoOwnerName=:assoOwnerName ");
        }
        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            queryString.append(" and o.assoMobileno=:assoMobileno ");
        }
        if (gridSearchDTO != null && pagingDTO != null) {
            if ("proertyNo".equals(gridSearchDTO.getSearchField()) || "oldPid".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = "am.";
            } else if ("ownerName".equals(gridSearchDTO.getSearchField()) || "mobileno".equals(gridSearchDTO.getSearchField())) {
                referenceNumber = "o.";
            }
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }
        queryString.append(" group by am.assNo )");
        if (gridSearchDTO != null && pagingDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.PropertySearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "p.");
        }
        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", searchDto.getOrgId());

        if (serviceId != null) {
            query.setParameter("serviceId", serviceId);
        }
        if (!StringUtils.isEmpty(searchDto.getProertyNo())) {
            query.setParameter("assNo", searchDto.getProertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getOldPid())) {
            query.setParameter("assOldpropno", searchDto.getOldPid().trim());
        }
        if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
            query.setParameter("locId", searchDto.getLocId());
        }
        if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
            query.setParameter("assWard1", searchDto.getAssWard1());
        }
        if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
            query.setParameter("assWard2", searchDto.getAssWard2());
        }
        if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
            query.setParameter("assWard3", searchDto.getAssWard3());
        }
        if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
            query.setParameter("assWard4", searchDto.getAssWard4());
        }
        if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
            query.setParameter("assWard5", searchDto.getAssWard5());
        }
        if (!StringUtils.isEmpty(searchDto.getOwnerName())) {
            query.setParameter("assoOwnerName", searchDto.getOwnerName().trim());
        }
        if (!StringUtils.isEmpty(searchDto.getMobileno())) {
            query.setParameter("assoMobileno", searchDto.getMobileno().trim());
        }
        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

    @Override
    public boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status,
            Long finYearId, Long serviceId) {
    	Organisation organisation = new Organisation();
    	organisation.setOrgid(orgId);
    	LookUp billDeletionInactive = null;
        try {
        	billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",organisation);
        }catch (Exception e) {
       	 log.error("No prefix found for ENV - BDI");
		}
        StringBuilder queryString = new StringBuilder(
                "from AssesmentMastEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from AssesmentMastEntity am  WHERE  am.orgId=:orgId and"
                        + "   am.faYearId=:faYearId and am.smServiceId !=:serviceId ");
        if (billDeletionInactive != null
				&& org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
				&& org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
						MainetConstants.FlagY)
				&& org.apache.commons.lang.StringUtils.isNotBlank(status)
				) {
        	queryString.append(" and am.assActive=:assActive "); 
        }
        
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
		if (billDeletionInactive != null
				&& org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
				&& org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
						MainetConstants.FlagY)
				&& org.apache.commons.lang.StringUtils.isNotBlank(status)
				) {
			query.setParameter("assActive", status);
		}
        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            log.info("No  provisional Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status,
            Long finYearId) {
        StringBuilder queryString = new StringBuilder(
                "from AssesmentMastEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from AssesmentMastEntity am  WHERE  am.orgId=:orgId and"
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

    @Override
    public AssesmentMastEntity fetchAssessmentDetailsAssNoOrOldpropno(long orgId, String assNo, String assOldpropno,
            String status) {
        AssesmentMastEntity mst = null;
        StringBuilder queryString = new StringBuilder(
                " from AssesmentMastEntity am  WHERE  am.orgId=:orgId and am.assActive=:assActive ");
        if (assNo != null && !assNo.isEmpty()) {
            queryString.append(" and am.assNo=:assNo ");
        }

        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }

        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("assActive", status);
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }

        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            query.setParameter("assOldpropno", assOldpropno);
        }

        try {
            mst = (AssesmentMastEntity) query.getSingleResult();
            // Hibernate.initialize(mst.getAssesmentDetailEntityList());
        } catch (Exception e) {
            log.info("No Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
        }
        return mst;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.property.dao.IAssessmentMastDao#fetchDataEntryByAssNoOrOldPropNo(long, java.lang.String,
     * java.lang.String)
     */
    @Override
    public AssesmentMastEntity fetchDataEntryByAssNoOrOldPropNo(long orgId, String assNo, String assOldpropno) {
        AssesmentMastEntity mastEntity = null;
        StringBuilder queryString = new StringBuilder(
                "from AssesmentMastEntity p where p.proAssId in ("
                        + "select max(am.proAssId) from AssesmentMastEntity am inner join am.assesmentOwnerDetailEntityList o WHERE  am.orgId=:orgId and am.assActive='A' ");
        if (assNo != null && !assNo.isEmpty()) {

            queryString.append(" and am.assNo=:assNo ");
        }

        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }

        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }
        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            query.setParameter("assOldpropno", assOldpropno);
        }
        try {
            mastEntity = (AssesmentMastEntity) query.getSingleResult();
        } catch (Exception e) {
            log.info("No Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
        }
        return mastEntity;
    }
    
    @Override
    public AssesmentMastEntity fetchAssessmentDetailsAssNoOrOldpropnoAndFlatNo(long orgId, String assNo, String assOldpropno,
            String status,String logicalPropNo) {
        AssesmentMastEntity mst = null;
        StringBuilder queryString = new StringBuilder(
                " from AssesmentMastEntity am  WHERE  am.orgId=:orgId and am.assActive=:assActive ");
        if (assNo != null && !assNo.isEmpty()) {
            queryString.append(" and am.assNo=:assNo ");
        }

        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            queryString.append(" and am.assOldpropno=:assOldpropno ");
        }
        
        if (logicalPropNo != null && !logicalPropNo.isEmpty()) {
            queryString.append(" and am.logicalPropNo=:logicalPropNo ");
        }

        queryString.append(" )");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("assActive", status);
        if (assNo != null && !assNo.isEmpty()) {
            query.setParameter("assNo", assNo);
        }

        if (assOldpropno != null && !assOldpropno.isEmpty()) {
            query.setParameter("assOldpropno", assOldpropno);
        }
        if (logicalPropNo != null && !logicalPropNo.isEmpty()) {
            query.setParameter("logicalPropNo", logicalPropNo);
        }

        try {
            mst = (AssesmentMastEntity) query.getSingleResult();
            // Hibernate.initialize(mst.getAssesmentDetailEntityList());
        } catch (Exception e) {
            log.info("No Assessemnt master found for prop No:" + assNo + "OR old Prop No:" + assOldpropno);
        }
        return mst;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllMaxBmYearByPropNo(List<String> propNoList, Long orgId) {
		final Query query = createNativeQuery(
				"select LOGICAL_PROP_NO,max(MN_BM_YEAR) from TB_AS_BILL_MAS where orgid=:orgId and LOGICAL_PROP_NO in (:propNoList) group by LOGICAL_PROP_NO");
		 query.setParameter("orgId", orgId);
	        query.setParameter("propNoList", propNoList);
	        return query.getResultList();
	}

	@Override
	public List<Object[]> getAllBillsForBillDistribution(List<String> propNoList, Long orgId) {
		final Query query = createNativeQuery("select mn_prop_no,MN_NO,MN_BILLDT, MN_BM_IDNO,BM_SRV_DT,MN_FROMDT from TB_AS_BILL_MAS where BM_SRV_DT is null and (mn_prop_no,MN_BM_IDNO) in (select mn_prop_no,max(MN_BM_IDNO) from TB_AS_BILL_MAS where orgid=:orgId and  MN_PROP_NO in (:propNoList) group by MN_PROP_NO)");
		 query.setParameter("orgId", orgId);
	        query.setParameter("propNoList", propNoList);
	        return query.getResultList();
	}

	@Override
	public List<Object[]> getAllBillsForBillDistributionForupdation(List<String> propNoList, Long orgId) {
		final Query query = createNativeQuery("select mn_prop_no,MN_NO,MN_BILLDT, MN_BM_IDNO,BM_SRV_DT,MN_FROMDT from TB_AS_BILL_MAS where BM_SRV_DT is not null and (mn_prop_no,MN_BM_IDNO) in (select mn_prop_no,max(MN_BM_IDNO) from TB_AS_BILL_MAS where orgid=:orgId and  MN_PROP_NO in (:propNoList) group by MN_PROP_NO)");
		 query.setParameter("orgId", orgId);
	        query.setParameter("propNoList", propNoList);
	        return query.getResultList();
	}
	
	 @SuppressWarnings("unchecked")
	    @Override
	    public List<Object[]> getAllPropBillGeneByPropNoList(Long finId, Long orgId,List<String> propNoList) {
	        final Query query = createNativeQuery(
	                "select MN_BM_IDNO,MN_ASS_ID,MN_PROP_NO,MN_BM_YEAR  from TB_AS_BILL_MAS m where (MN_PROP_NO,MN_BM_IDNO) in (" +
	                        "select a.MN_PROP_NO,max(a.MN_BM_IDNO) as MN_BM_IDNO " +
	                        "from  " +
	                        "(select MN_BM_IDNO,MN_PROP_NO from TB_AS_BILL_MAS where orgid=:orgId ) a " +
	                        "group by a.MN_PROP_NO) and MN_BM_YEAR not in (:finId) and mn_prop_no in (:propNoList)");
	        query.setParameter("finId", finId);
	        query.setParameter("orgId", orgId);
	        query.setParameter("propNoList", propNoList);
	        return query.getResultList();
	    }
	    
	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetchAssessmentByGroupPropNo(Long orgId, String parentPropNo, String parentPropName,
			String status) {
		List<String> propNoList = null;
		StringBuilder queryString = new StringBuilder(
				"select distinct am.assNo from AssesmentMastEntity am  WHERE  am.orgId=:orgId and am.assActive=:assActive and am.isGroup='Y' ");
		if (!StringUtils.isEmpty(parentPropNo)) {
			queryString.append(" and am.parentPropNo=:parentPropNo ");
		}
		if (!StringUtils.isEmpty(parentPropName) && !parentPropName.equals("0")) {
			queryString.append(" and am.parentPropName=:parentPropName ");
		}

		final Query query = createQuery(queryString.toString());
		query.setParameter("orgId", orgId);
		query.setParameter("assActive", status);
		if (!StringUtils.isEmpty(parentPropNo)) {
			query.setParameter("parentPropNo", parentPropNo);
		}
		if (!StringUtils.isEmpty(parentPropName) && !parentPropName.equals("0")) {
			query.setParameter("parentPropName", parentPropName);
		}
		try {
			propNoList = query.getResultList();
		} catch (Exception e) {
			log.info("No Assessemnt master found ");
		}
		return propNoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchPropNoWhoseBillNotPresent(Long orgId, NoticeGenSearchDto searchDto) {
		List<Object[]> propNoList = null;
		StringBuilder queryString = new StringBuilder(
				"SELECT distinct am.MN_ASS_no,am.MN_ASS_oldpropno FROM tb_as_assesment_mast am WHERE  am.orgid=:orgId and am.MN_ASS_active='A' ");

		if (searchDto != null) {
			if (!StringUtils.isEmpty(searchDto.getPropertyNo())) {
				queryString.append(" and am.MN_ASS_NO=:assNo ");
	        }
	        if (!StringUtils.isEmpty(searchDto.getOldPropertyNo())) {
	        	queryString.append(" and am.MN_ASS_oldpropno=:assOldpropno ");
	        }
	        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
	        	queryString.append(" and am.MN_FLAT_NO=:flatNo ");
	        }
			if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
				queryString.append(" and am.MN_loc_id=:locId ");
			}
			if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
				queryString.append(" and am.MN_ASS_ward1=:assWard1 ");
			}
			if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
				queryString.append(" and am.MN_ASS_ward2=:assWard2 ");
			}
			if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
				queryString.append(" and am.MN_ASS_ward3=:assWard3 ");
			}
			if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
				queryString.append(" and am.MN_ASS_ward4=:assWard4 ");
			}
			if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
				queryString.append(" and am.MN_ASS_ward5=:assWard5 ");
			}

			StringBuilder subquery = new StringBuilder(
					" and am.MN_ass_id in (select pd.MN_ass_id from tb_as_assesment_detail pd where ");
			boolean subAppli = false;
			if (searchDto.getAssdUsagetype1() != null && searchDto.getAssdUsagetype1() > 0) {
				subquery.append(" pd.MN_assd_usagetype1=:MN_assd_usagetype1 ");
				subAppli = true;
			}
			if (searchDto.getAssdUsagetype2() != null && searchDto.getAssdUsagetype2() > 0) {
				subquery.append("  and pd.MN_assd_usagetype2=:MN_assd_usagetype2 ");
			}
			if (searchDto.getAssdUsagetype3() != null && searchDto.getAssdUsagetype3() > 0) {
				subquery.append(" and  pd.MN_assd_usagetype3=:MN_assd_usagetype3 ");
			}
			if (searchDto.getAssdUsagetype4() != null && searchDto.getAssdUsagetype4() > 0) {
				subquery.append(" and  pd.MN_assd_usagetype4=:MN_assd_usagetype4 ");
			}
			if (searchDto.getAssdUsagetype5() != null && searchDto.getAssdUsagetype5() > 0) {
				subquery.append(" and  pd.MN_assd_usagetype5=:MN_assd_usagetype5 ");
			}
			if (subAppli) {
				subquery.append(" and pd.orgId=:orgId ");
				queryString.append(subquery.toString() + " ) ");
			}
		}
		queryString
				.append(" and am.MN_ASS_no  not in ( select b.MN_PROP_NO from tb_as_bill_mas b where b.orgid=:orgId) ");
		final Query query = createNativeQuery(queryString.toString());
		query.setParameter("orgId", orgId);
		if (searchDto != null) {
			if (!StringUtils.isEmpty(searchDto.getPropertyNo())) {
	            query.setParameter("assNo", searchDto.getPropertyNo().trim());
	        }
	        if (!StringUtils.isEmpty(searchDto.getOldPropertyNo())) {
	            query.setParameter("assOldpropno", searchDto.getOldPropertyNo().trim());
	        }
	        if (!StringUtils.isEmpty(searchDto.getFlatNo())) {
	            query.setParameter("flatNo", searchDto.getFlatNo().trim());
	        }
			if (searchDto.getLocId() != null && searchDto.getLocId() > 0) {
				query.setParameter("locId", searchDto.getLocId());
			}
			if (searchDto.getAssWard1() != null && searchDto.getAssWard1() > 0) {
				query.setParameter("assWard1", searchDto.getAssWard1());
			}
			if (searchDto.getAssWard2() != null && searchDto.getAssWard2() > 0) {
				query.setParameter("assWard2", searchDto.getAssWard2());
			}
			if (searchDto.getAssWard3() != null && searchDto.getAssWard3() > 0) {
				query.setParameter("assWard3", searchDto.getAssWard3());
			}
			if (searchDto.getAssWard4() != null && searchDto.getAssWard4() > 0) {
				query.setParameter("assWard4", searchDto.getAssWard4());
			}
			if (searchDto.getAssWard5() != null && searchDto.getAssWard5() > 0) {
				query.setParameter("assWard5", searchDto.getAssWard5());
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
		}
		try {
			propNoList = query.getResultList();
		} catch (Exception e) {
			logger.info("No Assessemnt master found ");
		}
		return propNoList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetParentBillNoOfBillGeneratedProps(Long finId, Long orgId, List<String> propNoList) {
		final Query query = createNativeQuery(
				"select PARENT_MN_NO  from TB_AS_BILL_MAS m where (MN_PROP_NO,MN_BM_IDNO) in ("
						+ "select a.MN_PROP_NO,max(a.MN_BM_IDNO) as MN_BM_IDNO " + "from  "
						+ "(select pro_bm_idno as MN_BM_IDNO,pro_prop_no as MN_PROP_NO from TB_AS_PRO_BILL_MAS where orgid=:orgId "
						+ "union " + "select MN_BM_IDNO,MN_PROP_NO from TB_AS_BILL_MAS where orgid=:orgId ) a "
						+ "group by a.MN_PROP_NO) and MN_BM_YEAR  in (:finId) and mn_prop_no in (:propNoList) GROUP BY PARENT_MN_NO");
		query.setParameter("finId", finId);
		query.setParameter("orgId", orgId);
		query.setParameter("propNoList", propNoList);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getConsolidatedBillsForGrpProperty(Long finId, Long orgId, String parentpropNo) {
		final Query query = createNativeQuery(
				" Select x.parent_PROP_NO,parent_MN_NO,count(distinct MN_ASS_no) count_using,max(MN_BILLDT) MN_BILLDT,max(MN_FROMDT) MN_FROMDT, "
						+ " max(MN_TODT) MN_TODT,max(MN_DUEDATE) MN_DUEDATE,sum(BD_CUR_BAL_TAXAMT) current,sum(BD_PRV_BAL_ARRAMT) arrear,TAX_DISPLAY_SEQ, tax_desc, "
						+ " SUM(Assd_alv) TotalARV,SUM(Assd_rv) totalrv,TX.tax_desc_id From TB_AS_ASSESMENT_MAST x LEFT JOIN tb_as_bill_mas y on x.MN_ASS_no=y.mn_prop_no "
						+ " INNER JOIN tb_as_bill_det z on z.BM_IDNO = y.MN_BM_IDNO INNER JOIN tb_tax_mas TX ON z.TAX_ID = TX.TAX_ID "
						+ " where y.orgid=:orgId AND y.parent_MN_NO is not null AND x.orgid=:orgId AND x.is_group = 'Y' AND x.parent_PROP_NO=:parentpropNo AND y.MN_BM_YEAR=:finId"
						+ " group by parent_MN_NO,parent_PROP_NO,TAX_DISPLAY_SEQ,tax_desc,tax_desc_id");
		query.setParameter("finId", finId);
		query.setParameter("orgId", orgId);
		query.setParameter("parentpropNo", parentpropNo);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllMaxBmYearByPropNoForWhole(List<String> propNoList, Long orgId) {
		final Query query = createNativeQuery(
				"select MN_PROP_NO,max(MN_BM_YEAR) from TB_AS_BILL_MAS where orgid=:orgId and MN_PROP_NO in (:propNoList) group by MN_PROP_NO");
		 query.setParameter("orgId", orgId);
	        query.setParameter("propNoList", propNoList);
	        return query.getResultList();
	}

}
