package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.VoucherDetailViewEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public  class AccountFinanceReportDAOImpl extends AbstractDAO<Long> implements AccountFinanceReportDAO {
	private static final Logger LOGGER = Logger.getLogger(AccountFinanceReportDAOImpl.class);
	@Override
	public List<Object[]> getPaymentBookReportData(Long orgid, Date fromDateId, Date toDateId, Long drId, Long crId,
			String fieldId) {
		 String fieldQuery=" ";
		 String fieldQuery1=" ";
            if(fieldId!=null && !fieldId.isEmpty()) {
            	fieldQuery1="  AND CM.FIELD_ID =:fieldId\r\n";
            	fieldQuery="  AND VM.FIELD_ID =:fieldId\r\n";
            }
        final StringBuilder queryString = new StringBuilder("SELECT DT.VOU_ID,\r\n" + 
        		"       VM.VOU_POSTING_DATE,\r\n" + 
        		"       VM.VOU_NO,\r\n" + 
        		"       SM.AC_HEAD_CODE,\r\n" + 
        		"       VM.NARRATION,\r\n" + 
        		"       DT.VOUDET_AMT,\r\n" + 
        		"       VM.VOU_TYPE_CPD_ID,\r\n" + 
        		"       DT.DRCR_CPD_ID,\r\n" + 
        		"       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
        		"  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" + 
        		"  WHERE DT.VOU_ID = VM.VOU_ID\r\n" + 
        		"  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" + 
        		"   AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
        		fieldQuery+
        		"  AND DT.DRCR_CPD_ID  =:crId  \r\n" + 
        		"  AND DT.ORGID =:orgid\r\n" + 
        		"  UNION\r\n" + 
        		"  SELECT CT.VOU_ID,\r\n" + 
        		"       CM.VOU_POSTING_DATE,\r\n" + 
        		"       CM.VOU_NO,\r\n" + 
        		"       CSM.AC_HEAD_CODE,\r\n" + 
        		"       CM.NARRATION,\r\n" + 
        		"       CT.VOUDET_AMT,\r\n" + 
        		"       CM.VOU_TYPE_CPD_ID,\r\n" + 
        		"       CT.DRCR_CPD_ID,\r\n" + 
        		"       csm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
        		"  FROM TB_AC_VOUCHER_DET CT, TB_AC_VOUCHER CM, TB_AC_SECONDARYHEAD_MASTER CSM\r\n" + 
        		"  WHERE CT.VOU_ID = CM.VOU_ID\r\n" + 
        		"  AND CSM.SAC_HEAD_ID = CT.SAC_HEAD_ID\r\n" + 
        		"  AND CM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
        		fieldQuery1 +
        		"  AND CT.DRCR_CPD_ID =:drId  \r\n" + 
        		"  AND CT.ORGID =:orgid\r\n" + 
        		"  ORDER BY  VOU_ID");

        final Query query = entityManager.createNativeQuery(queryString.toString());
        query.setParameter("fromDateId", fromDateId);
        query.setParameter("toDateId", toDateId);
        query.setParameter("drId", drId);
        query.setParameter("crId", crId);
        query.setParameter("orgid", orgid);
        
        if(fieldId!=null && !fieldId.isEmpty()) {
        	query.setParameter("fieldId", fieldId);
        }
        

        @SuppressWarnings("unchecked")
        final List<Object[]> listOfEntity = query.getResultList();
        return listOfEntity;
    
	}

	@Override
	public List<Object[]> getReceiptBookReportData(Long orgid, Date fromDateId, Date toDateId, Long drId, Long crId,
			String fieldId) {

		 String fieldQuery=" ";
		 String fieldQuery1=" ";
           if(fieldId!=null && !fieldId.isEmpty()) {
           	fieldQuery="  AND CM.FIELD_ID =:fieldId\r\n";
           	fieldQuery1="  AND VM.FIELD_ID =:fieldId\r\n";
           }
       final StringBuilder queryString = new StringBuilder("SELECT DT.VOU_ID,\r\n" + 
       		"       VM.VOU_POSTING_DATE,\r\n" + 
       		"       VM.VOU_NO,\r\n" + 
       		"       SM.AC_HEAD_CODE,\r\n" + 
       		"       VM.NARRATION,\r\n" + 
       		"       DT.VOUDET_AMT,\r\n" + 
       		"       VM.VOU_TYPE_CPD_ID,\r\n" + 
       		"       DT.DRCR_CPD_ID,\r\n" + 
       		"       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
       		"  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" + 
       		"  WHERE DT.VOU_ID = VM.VOU_ID\r\n" + 
       		"  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" + 
       		"   AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" +
       		fieldQuery1 +
       		"  AND DT.DRCR_CPD_ID  =:crId  \r\n" + 
       		"  AND DT.ORGID =:orgid\r\n" + 
       		"  UNION\r\n" + 
       		"  SELECT CT.VOU_ID,\r\n" + 
       		"       CM.VOU_POSTING_DATE,\r\n" + 
       		"       CM.VOU_NO,\r\n" + 
       		"       CSM.AC_HEAD_CODE,\r\n" + 
       		"       CM.NARRATION,\r\n" + 
       		"       CT.VOUDET_AMT,\r\n" + 
       		"       CM.VOU_TYPE_CPD_ID,\r\n" + 
       		"       CT.DRCR_CPD_ID,\r\n" + 
       		"       csm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
       		"  FROM TB_AC_VOUCHER_DET CT, TB_AC_VOUCHER CM, TB_AC_SECONDARYHEAD_MASTER CSM\r\n" + 
       		"  WHERE CT.VOU_ID = CM.VOU_ID\r\n" + 
       		"  AND CSM.SAC_HEAD_ID = CT.SAC_HEAD_ID\r\n" + 
       		"  AND CM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
       		fieldQuery +
       		"  AND CT.DRCR_CPD_ID =:drId  \r\n" + 
       		"  AND CT.ORGID =:orgid\r\n" + 
       		"  ORDER BY  VOU_ID");

       final Query query = entityManager.createNativeQuery(queryString.toString());
       query.setParameter("fromDateId", fromDateId);
       query.setParameter("toDateId", toDateId);
       query.setParameter("drId", drId);
       query.setParameter("crId", crId);
       query.setParameter("orgid", orgid);
       
       if(fieldId!=null && !fieldId.isEmpty()) {
       	query.setParameter("fieldId", fieldId);
       }
       

       @SuppressWarnings("unchecked")
       final List<Object[]> listOfEntity = query.getResultList();
       return listOfEntity;
   
	}
	@Override
	public List<VoucherDetailViewEntity> queryReportDataFromViewGeneralBankBook(Date entryDate,Long orgId,Long sacHeadId,Date endDate,Long fieldId){


		List<VoucherDetailViewEntity> entityList = null;
		try {
			String sacHeadIds=MainetConstants.BLANK;
			if(sacHeadId>0)
                    sacHeadIds="and v1.sacHeadId !=:sacHeadId AND v2.sacHeadId=:sacHeadId";
			
			StringBuilder hql = new StringBuilder("SELECT v1 FROM VoucherDetailViewEntity v1 , VoucherDetailViewEntity v2 "
		            + " WHERE v1.vouNo = v2.vouNo "+sacHeadIds+" AND v2.voPostingDate between  :entryDate and :endDate AND v1.voPostingDate between  :entryDate and :endDate"
		            + "  and v1.vouTypeCpdId not in (select cpdId from TbComparamDetEntity where cpdValue='JV') "
		            + "and v1.orgId=:orgId ");
			if(fieldId!=null && fieldId!=-1 && fieldId !=0) {
				hql.append("  and v1.fieldId=:fieldId ");
			}
           hql.append(" order by v1.voPostingDate,v1.vouId asc )");
			final Query query = createQuery(hql.toString());
			if(sacHeadId>0)
			query.setParameter("sacHeadId", sacHeadId);
			query.setParameter("entryDate", entryDate);
			query.setParameter("endDate", endDate);
			query.setParameter("orgId", orgId);
			if(fieldId!=null && fieldId!=-1 && fieldId !=0) {
				query.setParameter("fieldId", fieldId);
			}
			entityList = (List<VoucherDetailViewEntity>) query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("exception occure at the time of fetching queryReportDataFromViewGeneralBankBook");	
		}
		return entityList;
	
		
	}
	@Override
	public List<VoucherDetailViewEntity> queryReportDataFromView(Date entryDate, Long orgId,Long sacHeadId,Date outDate,Long fieldId) {

		List<VoucherDetailViewEntity> entityList = null;
		try {

			StringBuilder hql = new StringBuilder("SELECT v1 FROM VoucherDetailViewEntity v1 , VoucherDetailViewEntity v2 "
		            + " WHERE v1.vouNo = v2.vouNo and v1.sacHeadId !=:sacHeadId AND v2.sacHeadId=:sacHeadId AND v2.voPostingDate between :entryDate and :outDate AND v1.voPostingDate between :entryDate and :outDate"
		            + "  and v1.vouTypeCpdId not in (select cpdId from TbComparamDetEntity where cpdValue='JV') "
		            + "and v1.orgId=:orgId ");
			if(fieldId!=null && fieldId!=-1 && fieldId !=0) {
				hql.append("  and v1.fieldId=:fieldId ");
			}
           hql.append(" order by v1.voPostingDate , v1.vouId asc )");
			final Query query = createQuery(hql.toString());
			query.setParameter("sacHeadId", sacHeadId);
			query.setParameter("entryDate", entryDate);
			query.setParameter("outDate", outDate);
			query.setParameter("orgId", orgId);
			if(fieldId!=null && fieldId!=-1 && fieldId !=0) {
				query.setParameter("fieldId", fieldId);
			}
			entityList = (List<VoucherDetailViewEntity>) query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("exception occure at the time of fetching queryReportDataFromView");	
		}
		return entityList;
	
	}

	@Override
	public List<Object[]> findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(Date toDates,
			Date fromDates, Long orgId, Long registerDepTypeId,Long fieldId) {


		 String fieldQuery=" ";
		 String deptQuery=" ";
		
      if(fieldId!=null && fieldId>0) {
      	fieldQuery="       AND r.FIELD_ID=:fieldId \r\n";
      	
      }
      if(registerDepTypeId!=null && registerDepTypeId>0) {
        	deptQuery="       AND r.DP_DEPTID=:registerDepTypeId \r\n";
        	
        }
  final StringBuilder queryString = new StringBuilder("SELECT \r\n" + 
  		"    r.RM_RCPTID,\r\n" + 
  		"    r.RM_RCPTNO,\r\n" + 
  		"    r.rm_date,\r\n" + 
  		"    m.CPD_FEEMODE,\r\n" + 
  		"    r.RM_RECEIVEDFROM,\r\n" + 
  		"    m.RD_CHEQUEDDNO,\r\n" + 
  		"    SUM(d.RF_FEEAMOUNT) AS Receipt_Amount,\r\n" + 
  		"    r.RM_NARRATION,\r\n" + 
  		"   r.DP_DEPTID FROM\r\n" + 
  		"    tb_receipt_mas r,\r\n" + 
  		"    tb_receipt_det d,\r\n" + 
  		"    tb_receipt_mode m,\r\n" + 
  		"    tb_comparam_mas cm,\r\n" + 
  		"    tb_comparam_det cd\r\n" + 
  		"    \r\n" + 
  		" WHERE\r\n" + 
  		"    r.RM_RCPTID = d.RM_RCPTID\r\n" + 
  		"        AND r.RM_RCPTID = m.RM_RCPTID\r\n" + 
  		"        AND d.RM_RCPTID = m.RM_RCPTID\r\n" + 
  		"        AND cm.CPM_ID = cd.CPM_ID\r\n" + 
  		"        AND cd.CPD_ID = m.CPD_FEEMODE      \r\n" + 
  		"        AND r.RECEIPT_DEL_FLAG is null        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')\r\n" + 
  		"        AND r.ORGID=:orgId\r\n" + fieldQuery+deptQuery+
  		"       AND date(rm_date) BETWEEN :fromDate AND :toDate \r\n" + 
  		"GROUP BY r.RM_RCPTID , r.RM_RCPTNO , r.rm_date , m.CPD_FEEMODE , r.RM_RECEIVEDFROM , m.RD_CHEQUEDDNO,r.RM_NARRATION,r.DP_DEPTID\r\n" + 
  		" order by r.rm_date,r.RM_RCPTNO asc");

  final Query query = entityManager.createNativeQuery(queryString.toString());
 
  
  

	query.setParameter("fromDate", fromDates);
	query.setParameter("orgId", orgId);
	query.setParameter("toDate", toDates);
	if(fieldId!=null && fieldId>0)
		query.setParameter("fieldId", fieldId);
	if(registerDepTypeId!=null && registerDepTypeId>0)
		query.setParameter("registerDepTypeId", registerDepTypeId);

  @SuppressWarnings("unchecked")
  final List<Object[]> listOfEntity = query.getResultList();
  return listOfEntity;

	
	}

	@Override
	public List<Object[]> queryBankAccountsSummaryReport(Date fromDates, long orgId, Date todates,
			Long financialYearId, Long fieldId) {


		 String fieldQuery=" ";
		 String fieldQuery1=" ";
		 String fieldQuery2=" ";
		
        if(fieldId!=null && fieldId>0) {
        	fieldQuery="    AND    ba.FIELD_ID=:fieldId \r\n";
        	fieldQuery1="    AND    b.FIELD_ID=:fieldId \r\n";
        	fieldQuery2="    AND    vd.FIELD_ID=:fieldId \r\n";
        	
        }
        
    final StringBuilder queryString = new StringBuilder("SELECT \r\n" + 
    		"    bm.BANK,\r\n" + 
    		"    ba.ba_account_no AS BankAcNo,\r\n" + 
    		"    cd.CPD_DESC AS Act_type,\r\n" + 
    		"    m.OPENBAL_AMT As Opn_Balance,\r\n" + 
    		"    m.VAMT_DR AS Receipts,\r\n" + 
    		"    m.VAMT_CR As Payments   ,  \r\n" + 
    		"    ba.BA_ACCOUNTNAME bankname    \r\n" + 
    		"FROM\r\n" + 
    		"    tb_bank_master bm,\r\n" + 
    		"    tb_comparam_det cd,\r\n" + 
    		"    tb_bank_account ba,\r\n" + 
    		"    (SELECT \r\n" + 
    		"        x.SAC_HEAD_ID,\r\n" + 
    		"            x.VAMT_DR,\r\n" + 
    		"            x.VAMT_CR,\r\n" + 
    		"            x.OPENBAL_AMT,\r\n" + 
    		"            bc.BA_ACCOUNTID\r\n" + 
    		"    FROM\r\n" + 
    		"        (SELECT \r\n" + 
    		"        SAC_HEAD_ID,\r\n" + 
    		"            SUM(VAMT_DR) VAMT_DR,\r\n" + 
    		"            SUM(VAMT_CR) VAMT_CR,\r\n" + 
    		"            SUM(OPENBAL_AMT) OPENBAL_AMT\r\n" + 
    		"    FROM\r\n" + 
    		"        (SELECT \r\n" + 
    		"        COALESCE(vd.VAMT_DR, 0) VAMT_DR,\r\n" + 
    		"            COALESCE(vd.VAMT_CR, 0) VAMT_CR,\r\n" + 
    		"            vd.SAC_HEAD_ID,\r\n" + 
    		"            0 OPENBAL_AMT\r\n" + 
    		"    FROM\r\n" + 
    		"        vw_voucher_detail vd, tb_financialyear f\r\n" + 
    		"    WHERE\r\n" + 
    		"        vd.ORGID =:orgId\r\n" +
    		"            AND vd.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate \r\n" + 
    		"            AND f.FA_YEARID =:finYrId UNION ALL SELECT \r\n" + 
    		"        0 VAMT_DR,\r\n" + 
    		"            0 VAMT_CR,\r\n" + 
    		"            b.SAC_HEAD_ID,\r\n" + 
    		"            COALESCE(b.OPENBAL_AMT, 0) OPENBAL_AMT\r\n" + 
    		"    FROM\r\n" + 
    		"        tb_ac_bugopen_balance b, tb_financialyear f\r\n" + 
    		"    WHERE\r\n" + 
    		"        b.FA_YEARID = f.FA_YEARID\r\n" + 
    		"            AND f.FA_YEARID =:finYrId "
    		+ ") A\r\n" + 
    		"    GROUP BY SAC_HEAD_ID) X, tb_bank_account bc, tb_ac_secondaryhead_master se\r\n" + 
    		"    WHERE\r\n" + 
    		"        x.SAC_HEAD_ID = se.SAC_HEAD_ID\r\n" + 
    		"            AND bc.BA_ACCOUNTID = se.BA_ACCOUNTID) M\r\n" + 
    		"WHERE\r\n" + 
    		"    bm.BANKID = ba.BANKID\r\n" + 
    		"        AND ba.cpd_accounttype = cd.CPd_ID\r\n" + 
    		"        AND ba.BA_ACCOUNTID = M.BA_ACCOUNTID\r\n" + fieldQuery+
    		"        AND ba.orgid =:orgId order by bm.BANK asc");

    final Query query = entityManager.createNativeQuery(queryString.toString());
   
    
    
  
	query.setParameter("fromDate", fromDates);
	query.setParameter("orgId", orgId);
	query.setParameter("toDate", todates);
	query.setParameter("finYrId", financialYearId);
	if(fieldId!=null && fieldId>0)
		query.setParameter("fieldId", fieldId);

    @SuppressWarnings("unchecked")
    final List<Object[]> listOfEntity = query.getResultList();
    return listOfEntity;

	
	}

	@Override
	public List<Object[]> findCollectionSummaryReportByTodateAndFromDateAndOrgId(Date toDates, Date fromDates,
			long orgId, Long fieldId) {



		 String fieldQuery=" ";
		
		
     if(fieldId!=null && fieldId>0) {
     	fieldQuery="       AND r.FIELD_ID=:fieldId \r\n";
     	
     }
     
 final StringBuilder queryString = new StringBuilder("SELECT  DP.DP_DEPTDESC,     d.SAC_HEAD_ID,\r\n" + 
 		"      sm.AC_HEAD_CODE, SUM(CASE WHEN cd.CPD_VALUE='C' THEN  m.RD_AMOUNT END) Cash_Amount,\r\n" + 
 		"SUM(CASE WHEN cd.CPD_VALUE NOT IN ('B','C') THEN  m.RD_AMOUNT END) Cheque_DD_Amount ,\r\n" + 
 		"SUM(CASE WHEN cd.CPD_VALUE='B' THEN  m.RD_AMOUNT END) Bank_Amount\r\n" + 
 		"  FROM tb_receipt_mas r,\r\n" + 
 		"       tb_receipt_det d,\r\n" + 
 		"       tb_receipt_mode m,\r\n" + 
 		"       tb_ac_secondaryhead_master sm,\r\n" + 
 		"       tb_comparam_mas cm,\r\n" + 
 		"       tb_comparam_det cd,\r\n" + 
 		"        TB_DEPARTMENT   DP\r\n" + 
 		"WHERE r.RM_RCPTID=d.RM_RCPTID\r\n" + 
 		"       AND r.ORGID=d.ORGID\r\n" + 
 		"       AND r.RM_RCPTID=m.RM_RCPTID\r\n" + 
 		"       AND r.ORGID=m.ORGID\r\n" + 
 		"       AND d.SAC_HEAD_ID=sm.SAC_HEAD_ID\r\n" + 
 		"       AND d.ORGID =sm.ORGID\r\n" + 
 		"       AND r.ORGID =sm.Orgid\r\n" + 
 		"       AND m.ORGID =sm.ORGID\r\n" + 
 		"       AND cm.CPM_ID =cd.CPM_ID\r\n" + 
 		"        AND R.DP_DEPTID = DP.DP_DEPTID\r\n" + 
 		"        -- AND cd.CPD_VALUE = 'C'\r\n" + 
 		"       AND cm.CPM_PREFIX = 'PAY'\r\n" + 
 		"       AND d.SAC_HEAD_ID IS NOT NULL\r\n" + 
 		"       AND cd.CPD_ID = m.CPD_FEEMODE\r\n" + 
 		"       AND r.RECEIPT_DEL_FLAG IS NULL\r\n" + 
 		"        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')       \r\n" + 
 		"        AND r.ORGID =:orgId\r\n" + 
 		fieldQuery+
 		"           AND r.RM_DATE BETWEEN :fromDate AND :toDate  \r\n" + 
 		"       Group By d.SAC_HEAD_ID,sm.AC_HEAD_CODE,DP.DP_DEPTDESC");

 final Query query = entityManager.createNativeQuery(queryString.toString());

 
 

	query.setParameter("fromDate", fromDates);
	query.setParameter("orgId", orgId);
	query.setParameter("toDate", toDates);
	if(fieldId!=null && fieldId>0)
		query.setParameter("fieldId", fieldId);

 @SuppressWarnings("unchecked")
 final List<Object[]> listOfEntity = query.getResultList();
 return listOfEntity;

	
	
	}

	@Override
	public List<Object[]> queryForTrailBalanceReportData( Date fromDate,  Date toDate,
      Long orgId, Long faYearIds,  Date beforeDate,
			Date afterDate, Long fieldId) {

		String fieldQuery = " ";

		if (fieldId != null && fieldId > 0) {
			fieldQuery = "       AND FIELD_ID=:fieldId \r\n";

		}

		final StringBuilder queryString = new StringBuilder("SELECT   \r\n" + "                Y.OPENBAL_AMT,  \r\n"
				+ "                Y.SAC_HEAD_ID,  \r\n" + "                Y.CPD_ID_DRCR,  \r\n"
				+ "                Y.TranCr,  \r\n" + "                Y.TranDr,  \r\n"
				+ "                B.OPVOUCR,  \r\n" + "                B.OPVOUDR \r\n" + "            FROM  \r\n"
				+ "                 tb_ac_primaryhead_master pr,\r\n"
				+ "                 tb_ac_secondaryhead_master se,\r\n" + "                (SELECT   \r\n"
				+ "                    X.OPENBAL_AMT,  \r\n" + "                        (CASE  \r\n"
				+ "                            WHEN X.OPNSACHEADID IS NULL THEN X.TRNSACHEADID  \r\n"
				+ "                            ELSE X.OPNSACHEADID  \r\n"
				+ "                        END) SAC_HEAD_ID,  \r\n" + "                        X.CPD_ID_DRCR,  \r\n"
				+ "                        SUM(X.VAMT_CR) TranCr,  \r\n"
				+ "                        SUM(X.VAMT_DR) TranDr  \r\n" + "                FROM  \r\n"
				+ "                    (SELECT   \r\n" + "                    VOUDET_ID,A.OPENBAL_AMT,  \r\n"
				+ "                        A.SAC_HEAD_ID OPNSACHEADID,  \r\n"
				+ "                        A.CPD_ID_DRCR,  \r\n" + "                        vd.VAMT_CR,  \r\n"
				+ "                        vd.VAMT_DR,  \r\n"
				+ "                        VD.SAC_HEAD_ID TRNSACHEADID  \r\n" + "                FROM  \r\n"
				+ "                    (SELECT   \r\n"
				+ "                    VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR  \r\n" + "                FROM  \r\n"
				+ "                    vw_voucher_detail  \r\n" + "                WHERE  \r\n"
				+ "                    ORGID ='13'  \r\n"+fieldQuery
				+ "                    and VOU_POSTING_DATE BETWEEN :fromDate and :toDate) vd  \r\n"
				+ "                LEFT JOIN (SELECT   \r\n"
				+ "                    bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID  \r\n"
				+ "                FROM  \r\n"
				+ "                    tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm  \r\n"
				+ "                WHERE  \r\n" + "                    sm.SAC_HEAD_ID = bg.SAC_HEAD_ID  \r\n"
				+ "                        AND sm.ORGID =:orgId and bg.FA_YEARID =:financiaYr) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID UNION  SELECT  \r\n"
				+ "            \r\n" + "                    VOUDET_ID,A.OPENBAL_AMT,  \r\n"
				+ "                        A.SAC_HEAD_ID OPNSACHEADID,  \r\n"
				+ "                        A.CPD_ID_DRCR,  \r\n" + "                        vd.VAMT_CR,  \r\n"
				+ "                        vd.VAMT_DR,  \r\n"
				+ "                        VD.SAC_HEAD_ID TRNSACHEADID  \r\n" + "                FROM  \r\n"
				+ "                    (SELECT   \r\n"
				+ "                    VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR  \r\n" + "                FROM  \r\n"
				+ "                    vw_voucher_detail  \r\n"
				+ "                WHERE ORGID =:orgId  \r\n"+fieldQuery
				+ "                    and VOU_POSTING_DATE BETWEEN :fromDate and :toDate) VD  \r\n"
				+ "                RIGHT JOIN (SELECT   \r\n"
				+ "                    bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID  \r\n"
				+ "                FROM  \r\n"
				+ "                    tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm  \r\n"
				+ "                WHERE  \r\n" + "                    sm.SAC_HEAD_ID = bg.SAC_HEAD_ID  \r\n"
				+ "                        AND sm.ORGID =:orgId and bg.FA_YEARID =:financiaYr) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID) X  \r\n"
				+ "                GROUP BY X.OPNSACHEADID , X.OPENBAL_AMT , X.CPD_ID_DRCR , X.TRNSACHEADID) Y  \r\n"
				+ "                    LEFT JOIN  \r\n" + "                (SELECT   \r\n"
				+ "                    SAC_HEAD_ID, SUM(VAMT_CR) OPVOUCR, SUM(VAMT_DR) OPVOUDR  \r\n"
				+ "                FROM  \r\n" + "                    vw_voucher_detail  \r\n"
				+ "                WHERE ORGID =:orgId \r\n"+fieldQuery
				+ "                    and VOU_POSTING_DATE BETWEEN :afterDate and :beforeDate\r\n"
				+ "                GROUP BY SAC_HEAD_ID) B ON Y.SAC_HEAD_ID = B.SAC_HEAD_ID\r\n"
				+ "                where Y.SAC_HEAD_ID=SE.SAC_HEAD_ID AND SE.PAC_HEAD_ID=PR.PAC_HEAD_ID\r\n"
				+ "                ORDER BY pr.PAC_HEAD_COMPO_CODE");

		final Query query = entityManager.createNativeQuery(queryString.toString());

		query.setParameter("fromDate", fromDate);
		query.setParameter("orgId", orgId);
		query.setParameter("toDate", toDate);
		query.setParameter("financiaYr", faYearIds);
		query.setParameter("afterDate", afterDate);
		query.setParameter("beforeDate",beforeDate );
		if (fieldId != null && fieldId > 0)
			query.setParameter("fieldId", fieldId);

		@SuppressWarnings("unchecked")
		final List<Object[]> listOfEntity = query.getResultList();
		return listOfEntity;

	}

	@Override
	public List<Object[]> querypaymentChequeReport(Date stringToDate, long orgId, Date stringToDate2,
			Long accountHeadId, Long fieldId) {

		 String fieldQuery=" ";
		 String acHeadQuery=" ";
          if(fieldId!=null && fieldId>0) {
          	fieldQuery="  AND PM.FIELDID =:fieldId  \r\n";
          	
          }
          if(accountHeadId!=null && accountHeadId>0) {
        	  acHeadQuery="  AND PM.BA_ACCOUNTID=:accountHeadId  \r\n";
            	
            }
      final StringBuilder queryString = new StringBuilder("SELECT A.PAYMENT_NO,\r\n" + 
      		"       A.PAYMENT_DATE,\r\n" + 
      		"       A.BM_BILLNO,\r\n" + 
      		"       A.VM_VENDORNAME,\r\n" + 
      		"       A.NARRATION,\r\n" + 
      		"       CASE\r\n" + 
      		"         WHEN (A.TRAN_REFNO IS NULL AND A.CHEQUE_NO IS NOT NULL) THEN\r\n" + 
      		"          A.CHEQUE_NO\r\n" + 
      		"         ELSE\r\n" + 
      		"          A.TRAN_REFNO\r\n" + 
      		"       END AS TRAN_REFNO,\r\n" + 
      		"       A.BM_ENTRYDATE,\r\n" + 
      		"       A.PAYMENT_AMT,\r\n" + 
      		"       A.INSTRUMENT_DATE,\r\n" + 
      		"       A.CHEQUE_CLEARANCE_DATE,\r\n" + 
      		"       A.CREATED_BY,\r\n" + 
      		"A.VM_VENDORID \r\n"+
      		"  FROM (SELECT Z.*, X.CHEQUE_NO, ISSUANCE_DATE\r\n" + 
      		"          FROM (SELECT A.PAYMENT_ID,\r\n" + 
      		"                       A.VM_VENDORID,\r\n" + 
      		"                       A.PAYMENT_NO,\r\n" + 
      		"                       A.PAYMENT_DATE,\r\n" + 
      		"                       B.BM_BILLNO,\r\n" + 
      		"                       A.NARRATION,\r\n" + 
      		"                       A.TRAN_REFNO,\r\n" + 
      		"                       B.BM_ENTRYDATE,\r\n" + 
      		"                       B.VM_VENDORNAME,\r\n" + 
      		"                       A.PAYMENT_AMT,\r\n" + 
      		"                       A.INSTRUMENT_DATE,\r\n" + 
      		"                       A.CHEQUE_CLEARANCE_DATE,\r\n" + 
      		"                       A.CREATED_BY\r\n" + 
      		"                  FROM (SELECT PM.VM_VENDORID,\r\n" + 
      		"                               PD.BM_ID,\r\n" + 
      		"                               PM.PAYMENT_ID,\r\n" + 
      		"                               PM.PAYMENT_NO,\r\n" + 
      		"                               PAYMENT_DATE,\r\n" + 
      		"                               PM.NARRATION,\r\n" + 
      		"                               PM.TRAN_REFNO,\r\n" + 
      		"                               SUM(PD.PAYMENT_AMT) AS PAYMENT_AMT,\r\n" + 
      		"                               PM.INSTRUMENT_DATE,\r\n" + 
      		"                               PM.CHEQUE_CLEARANCE_DATE,\r\n" + 
      		"                               PM.CREATED_BY\r\n" + 
      		"                                  FROM TB_AC_PAYMENT_MAS PM,TB_AC_PAYMENT_DET PD\r\n" + 
      		"                                 WHERE PM.PAYMENT_DEL_FLAG IS NULL\r\n" + 
      		"                                   AND PM.ORGID =:orgId AND \r\n" + 
      		"                                   PM.PAYMENT_DATE BETWEEN :fromDate AND \r\n" + 
      		"                                         :toDate \r\n" +acHeadQuery+ fieldQuery+ 
      		"                                   AND PM.PAYMENT_ID = PD.PAYMENT_ID\r\n" + 
      		"                                  GROUP BY PM.VM_VENDORID,\r\n" + 
      		"                                  PM.PAYMENT_ID,\r\n" + 
      		"                                  PM.PAYMENT_NO,\r\n" + 
      		"                                  PD.BM_ID,\r\n" + 
      		"                                  PAYMENT_DATE,\r\n" + 
      		"                                  PM.NARRATION,\r\n" + 
      		"                                  PM.TRAN_REFNO,\r\n" + 
      		"                                  PM.INSTRUMENT_DATE,\r\n" + 
      		"                                  PM.CHEQUE_CLEARANCE_DATE,\r\n" + 
      		"                                  PM.CREATED_BY) A\r\n" + 
      		"                  LEFT JOIN (SELECT BM.BM_ID, BM.BM_BILLNO, BM.BM_ENTRYDATE,BM.VM_VENDORNAME,BM.VM_VENDORID\r\n" + 
      		"                              FROM TB_AC_BILL_MAS        BM,\r\n" + 
      		"                                   TB_AC_BILL_EXP_DETAIL BD\r\n" + 
      		"                             WHERE BM.BM_ID = BD.BM_ID\r\n" + 
      		"                             GROUP BY BM.BM_ID, BM.BM_BILLNO, BM.BM_ENTRYDATE) B\r\n" + 
      		"                    ON A.BM_ID = B.BM_ID AND A.VM_VENDORID=B.VM_VENDORID) Z\r\n" + 
      		"          LEFT JOIN (SELECT CHEQUE_NO, PAYMENT_ID, ISSUANCE_DATE\r\n" + 
      		"                      FROM TB_AC_CHEQUEBOOKLEAF_DET\r\n" + 
      		"                     WHERE CANCELLATION_DATE IS NULL) X\r\n" + 
      		"            ON Z.PAYMENT_ID = X.PAYMENT_ID) A\r\n" + 
      		"ORDER BY A.PAYMENT_DATE ASC");

      final Query query = entityManager.createNativeQuery(queryString.toString());
     
      
      if(fieldId!=null && fieldId>0) {
      	query.setParameter("fieldId", fieldId);
      }
  	query.setParameter("fromDate", stringToDate);
  	query.setParameter("orgId", orgId);
	query.setParameter("toDate", stringToDate2);
	if(accountHeadId!=null && accountHeadId>0) {
    	query.setParameter("accountHeadId", accountHeadId);
    }

      @SuppressWarnings("unchecked")
      final List<Object[]> listOfEntity = query.getResultList();
      return listOfEntity;
  
	}

}
