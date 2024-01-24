package com.abm.mainet.water.constant;

public interface QueryConstants {

    interface WATER_MODULE_QUERY {
        interface METER_READING {
            String METER_READING_BY_METERID = "select b from TbMeterMasEntity b where b.mmMtnid=:meterMasId and b.orgid=:orgid";
            String METER_READING_BY_CONNECTIONID = "select b from TbMeterMasEntity b where b.tbCsmrInfo.csIdn in (:conIds) and b.orgid=:orgid";
            String METERDATA_BYMRID = "select m from TbMrdataEntity m where m.mrdId:mrdId and m.orgid=:orgid ";
            String METERDATA_BYCSIDN = "select m from TbMrdataEntity m where m.tbCsmrInfo.csIdn=:csIdn and m.orgid=:orgid order by m.mrdId desc ";
            String MAX_METERDATA_BYCSIDN = "select m from TbMrdataEntity m where m.tbCsmrInfo.csIdn in(:csIdn) "
                    + " and m.orgid=:orgid and m.mrdId in (select max(d.mrdId) from TbMrdataEntity d where"
                    + " d.tbCsmrInfo.csIdn=m.tbCsmrInfo.csIdn and d.orgid =m.orgid)";

            String METER_READING_DATAMAX = "select s.csIdn,m.mrdId, s.csCcn,e.mmMtrno ,"
                    + " s.csAdd,s.csFlatno,s.csBldplt,s.csLanear,s.csRdcross,s.csTitle,s.csName,"
                    + " s.csMname,s.csLname,m.tbComparamDet.cpdId,m.tbComparamDet2.cpdId,m.mrdMtrread ,e.mmMtnid,"
                    + " m.mrdCpdIdWtp,s.csMeteredccn,s.pcDate,s.csCcncategory1,s.codDwzid1 ,s.codDwzid2,s.codDwzid3,s.codDwzid4,s.codDwzid5, "
                    + " e.maxMeterRead,e.mmInitialReading,e.mmInstallDate,m.mrdMrdate,m.billGen "
                    + " from TbKCsmrInfoMH s ,TbMrdataEntity m,TbMeterMasEntity e where s.orgId=:orgId and s.csMeteredccn=:csMeteredccn "
                    + " and s.csIdn=m.tbCsmrInfo.csIdn and s.orgId=m.orgid "
                    + " and s.csIdn=e.tbCsmrInfo.csIdn and s.orgId=e.orgid "
                    + " and s.csCcn is not null and e.mmStatus = 'A' and s.csIsBillingActive='A' "
                    + " and m.mrdMrdate in (select max(d.mrdMrdate) from TbMrdataEntity d where d.tbCsmrInfo.csIdn = s.csIdn and d.orgid = s.orgId ";

            String NEW_METER_READING_DATAMAX = "select s.csIdn, s.csCcn,e.mmMtrno ,"
                    + " s.csAdd,s.csFlatno,s.csBldplt,s.csLanear,s.csRdcross,s.csTitle,s.csName,"
                    + " s.csMname,s.csLname,e.mmMtnid,s.csMeteredccn,s.pcDate,s.csCcncategory1,s.codDwzid1 ,s.codDwzid2,s.codDwzid3,s.codDwzid4,s.codDwzid5,"
                    + " e.maxMeterRead,e.mmInitialReading,e.mmInstallDate "
                    + " from TbKCsmrInfoMH s ,TbMeterMasEntity e where s.orgId=:orgId and s.csMeteredccn=:csMeteredccn "
                    + " and s.csIdn=e.tbCsmrInfo.csIdn and s.orgId=e.orgid "
                    + " and s.csIdn not in (select d.tbCsmrInfo.csIdn from TbMrdataEntity d where d.orgid = s.orgId) "
                    + " and s.csCcn is not null and e.mmStatus = 'A' and s.csIsBillingActive='A' ";

            String UNBILLED_METER_READINGS = "select m from TbMrdataEntity m where m.tbCsmrInfo.csIdn in(:csIdn) and m.orgid=:orgid "
            		+ "and m.billGen='N' order by m.mrdId desc ";

        }

        interface BILL_MAS_QUERY {
            String BILL_MAS_PRINT = "select b.bmIdno,w.csCcn,w.csCcncategory1,w.csCcnsize,w.trmGroup1,w.csFlatno,w.csName,w.csMname,w.csLname, "
                    + " w.csAdd,w.csCcityName,w.csBldplt,w.csLanear,w.csRdcross,b.bmBilldt,b.bmFromdt,b.bmTodt,b.bmDuedate,b.bmTotalOutstanding,"
                    + " w.csBadd,w.csBcityName,w.csBlanear,w.csBrdcross,w.csIdn,w.propertyNo,b.bmNo,b.bmRemarks,w.typeOfApplication,b.bmYear,w.bplFlag,w.depositAmount,w.depositDate,w.csCpinCode,w.houseNumber"
                    + " from TbKCsmrInfoMH w ,TbWtBillMasEntity b  where "
                    + " w.csIdn=b.csIdn and w.orgId=b.orgid  and b.orgid=:orgId and b.bmIdno in (:billIds) ";
            String BILL_PRINTING = "select b.bmIdno,b.csIdn,w.csCcn,b.bmNo,w.csMeteredccn,w.csName,w.csMname,w.csLname,b.bmBilldt  from TbKCsmrInfoMH w ,TbWtBillMasEntity b where "
                    + " b.bmIdno in (select (i.bmIdno) from TbWtBillMasEntity i where i.csIdn=w.csIdn and i.orgid=w.orgId) "
                    + " and w.csIdn=b.csIdn and w.orgId=b.orgid  and w.orgId=:orgId and w.conActive is null and b.bmPaidFlag='N'";

            String NOTICE_DISTRIBUTION = "select b.nbNoticeid,b.csIdn,w.csCcn,b.nbNoticeno,w.csMeteredccn,w.csName,w.csMname,w.csLname,b.nbNoticedt  from TbKCsmrInfoMH w ,DemandNotice b where "
                    + " and w.csIdn=b.csIdn and w.orgId=b.orgid  and w.orgId=:orgId  and b.cpdNottype=:cpdNottype  ";
            
            String BILL_PRINT_SKDCL = "select b.bmIdno,w.csCcn,w.csCcncategory1,w.csCcnsize,w.trmGroup1,w.csFlatno,w.csName,"
            		+ "w.csMname,w.csLname, w.csAdd,w.csCcityName,w.csBldplt,w.csLanear,w.csRdcross,b.bmBilldt,b.bmFromdt,b.bmTodt,"
            		+ "b.bmDuedate,b.bmTotalOutstanding, w.csBadd,w.csBcityName,w.csBlanear,w.csBrdcross,w.csIdn,w.propertyNo,"
            		+ "b.bmNo,b.bmRemarks,w.csContactno,b.bmYear,w.bplFlag,w.depositAmount,w.depositDate,w.csCpinCode, "
            		+ "w.codDwzid1, w.codDwzid3, w.noOfFamilies, w.csOldccn, w.trmGroup2, w.opincode, w.applicationNo, b.genFlag, "
            		+ "b.bmTotalBalAmount, b.bmTotalArrears, w.csIdn, b.bmTotalAmount, w.codDwzid2 from TbKCsmrInfoMH w ,TbWtBillMasEntity b  where "
            		+ "w.csIdn=b.csIdn and w.orgId=b.orgid and b.orgid=:orgId and b.bmIdno in (:billIds) ";
            
            String DUPLICATE_BILL_PRINT_SKDCL = "select b.bmIdno,w.csCcn,w.csCcncategory1,w.csCcnsize,w.trmGroup1,w.csFlatno,w.csName,"
            		+ "w.csMname,w.csLname, w.csAdd,w.csCcityName,w.csBldplt,w.csLanear,w.csRdcross,b.bmBilldt,b.bmFromdt,b.bmTodt,"
            		+ "b.bmDuedate,b.bmTotalOutstanding, w.csBadd,w.csBcityName,w.csBlanear,w.csBrdcross,w.csIdn,w.propertyNo,"
            		+ "b.bmNo,b.bmRemarks,w.csContactno,b.bmYear,w.bplFlag,w.depositAmount,w.depositDate,w.csCpinCode, "
            		+ "w.codDwzid1, w.codDwzid3, w.noOfFamilies, w.csOldccn, w.trmGroup2, w.opincode, w.applicationNo, b.bmMeteredccn, "
            		+ "b.bmTotalBalAmount, b.bmTotalArrears, w.csIdn, b.bmTotalAmount, w.codDwzid2 from TbKCsmrInfoMH w, TbWtBIllMasHist b where "
            		+ "w.csIdn=b.csIdn and w.orgId=b.orgid and b.orgid=:orgid and b.bmNo=:bmNo ";
            
        }

        interface BILL_DET_QUERY {
            String BILLDET_COLLSEQ_LIST = "select d from TbWtBillDetEntity d where d.bmIdNo.bmIdno in (:billIds) and d.orgid=:orgId order by d.collSeq asc ";
        }

        interface WATER_TABLE_CSMRINFO {
            String BILLING_RECORD = "select w from TbKCsmrInfoMH w where w.orgId=:orgId and w.csIsBillingActive='A' ";

            String BILLING_METER_QUERY = "select w from TbKCsmrInfoMH w where w.orgId=:orgId and w.csIsBillingActive='A' and w.csIdn "
                    + " in (select m.tbCsmrInfo.csIdn from TbMrdataEntity m  where COALESCE(m.billGen,'N')='N' "
                    + " and m.orgid=:orgId )";

            String BILLING_NONMETER_QUERY = "select w from TbKCsmrInfoMH w where w.orgId=:orgId and w.csIsBillingActive='A' and "
                    + " (( w.csIdn in (select m.csIdn from TbWtBillMasEntity m where m.orgid=w.orgId and m.csIdn=w.csIdn "
                    + " and m.bmIdno in (select max(b.bmIdno) from TbWtBillMasEntity b where b.orgid=m.orgid and b.csIdn=m.csIdn  )"
                    + " and m.bmTodt < :currDate)) or w.csIdn not in( select z.csIdn from TbWtBillMasEntity z "
                    + " where z.orgid=w.orgId and z.csIdn=w.csIdn )) ";
        }

    }

    interface DemandNoticeQuery {

        interface Parameter {

            String CONNECTION_ID = "connectionId";

            String COD_DWZID1 = "codDwzid1";
            String COD_DWZID2 = "codDwzid2";
            String COD_DWZID3 = "codDwzid3";
            String COD_DWZID4 = "codDwzid4";
            String COD_DWZID5 = "codDwzid5";

            String TRM_GROUP1 = "trmGroup1";
            String TRM_GROUP2 = "trmGroup2";
            String TRM_GROUP3 = "trmGroup3";
            String TRM_GROUP4 = "trmGroup4";
            String TRM_GROUP5 = "trmGroup5";

            String ORG_ID = "orgId";
            String STATUS_FLAG = "status";
            String NOTICE_TYPE = "cpdNottype";
            String FINAL_NOTICE_TYPE = "noticeType";
            String METER_TYPE = "meterType";
            String CONNECTION_SIZE = "connSize";
            String CON_FROM = "conFrom";
            String CON_TO = "conTo";
            String AMT_FROM = "amountFrom";
            String AMT_TO ="amountTo";
			String CS_IDN = "csIdn";
        }

        String WWZ5 = " AND c.codDwzid5 = :codDwzid5 ";
        String WWZ4 = " AND c.codDwzid4 = :codDwzid4 ";
        String WWZ3 = " AND c.codDwzid3 = :codDwzid3 ";
        String WWZ2 = " AND c.codDwzid2 = :codDwzid2 ";
        String WWZ1 = " AND c.codDwzid1 = :codDwzid1 ";

        String TRM_GROUP1 = " AND c.trmGroup1 = :trmGroup1 ";
        String TRM_GROUP2 = " AND c.trmGroup2 = :trmGroup2 ";
        String TRM_GROUP3 = " AND c.trmGroup3 = :trmGroup3 ";
        String TRM_GROUP4 = " AND c.trmGroup4 = :trmGroup4 ";
        String TRM_GROUP5 = " AND c.trmGroup5 = :trmGroup5 ";

        String METER_TYPE = " AND c.csMeteredccn = :meterType ";
        String CONNECTION_SIZE = " AND c.csCcnsize = :connSize ";
        String CONNECTION_BETWEEN = " AND c.csCcn BETWEEN :conFrom AND :conTo ";

        String NOTICE_TYPE = " AND d.cpdNottype = :cpdNottype ";

        String SELECT_DEMAND_NOTICE = "SELECT d FROM DemandNotice d where d.orgid = :orgId AND d.isDeleted = 'N'";

        String SELECT_TAX_CODE = "SELECT d FROM DemandNotice d where d.orgid = :orgId AND d.csIdn in (:connectionId) AND d.isDeleted = 'N'";

        String DEMAND_PRINT = " SELECT d.nbNoticeid, c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalArrears, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno , b.bmFromdt, b.bmTodt ,d.cpdNottype,d.nbNoticeno,d.nbNoticedt "
                + " FROM TbKCsmrInfoMH c , TbWtBillMasEntity b, DemandNotice d "
                + " WHERE c.orgId = :orgId AND c.orgId = b.orgid "
                + " AND c.csIdn = b.csIdn  "
                + " AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z WHERE z.orgid = c.orgId AND z.csIdn = c.csIdn   AND z.bmPaidFlag = 'N') "
                + " AND c.csIdn = d.csIdn AND  d.orgid = c.orgId AND d.isDeleted = 'N'"
                + " AND b.bmDuedate IS NOT NULL ";

        String DEMAND_SEARCH = " SELECT c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalArrears, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno "
                + " FROM TbKCsmrInfoMH c , TbWtBillMasEntity b "
                + " WHERE c.orgId = :orgId AND c.orgId = b.orgid "
                + " AND c.csIdn = b.csIdn  "
                + " AND c.csIdn NOT IN ( SELECT n.csIdn FROM DemandNotice n WHERE n.orgid = c.orgId AND n.isDeleted = 'N' ) "
                + " AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z where z.orgid = c.orgId and z.csIdn = c.csIdn  AND z.bmPaidFlag = 'N' AND z.bmTotalArrears > 0 ) "
                + " AND b.bmDuedate IS NOT NULL and b.bmDuedate < :currDate ";
        
        String DEMAND_SEARCH_ASCL = " SELECT c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalArrears, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno "
                + " FROM TbKCsmrInfoMH c , TbWtBillMasEntity b "
                + " WHERE c.orgId = :orgId AND c.orgId = b.orgid "
                + " AND c.csIdn = b.csIdn  "
                + " AND c.csIdn NOT IN ( SELECT n.csIdn FROM DemandNotice n WHERE n.orgid = c.orgId AND n.isDeleted = 'N' ) "
                + " AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z where z.orgid = c.orgId and z.csIdn = c.csIdn  AND z.bmPaidFlag = 'N' AND z.bmTotalArrears > 0 ) "
                + " AND b.bmDuedate IS NOT NULL";
        
        String DEMAND_SEARCH_OUTSTANDING_RANGE = " SELECT c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalOutstanding, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno "
                + " FROM TbKCsmrInfoMH c , TbWtBillMasEntity b "
                + " WHERE c.orgId = :orgId AND c.orgId = b.orgid "
                + " AND c.csIdn = b.csIdn  "
                + " AND c.csIdn NOT IN ( SELECT n.csIdn FROM DemandNotice n WHERE n.orgid = c.orgId AND n.isDeleted = 'N' ) "
                + " AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z where z.orgid = c.orgId and z.csIdn = c.csIdn  AND z.bmPaidFlag = 'N' AND z.bmTotalArrears > 0 "
                + " AND z.bmTotalOutstanding between :amountFrom and :amountTo ) AND b.bmDuedate IS NOT NULL";

        
        String FINAL_DEMAND_SEARCH = " SELECT c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalArrears, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno "
                + " FROM TbKCsmrInfoMH c , TbWtBillMasEntity b "
                + " WHERE c.orgId = :orgId AND c.orgId = b.orgid "
                + " AND c.csIdn = b.csIdn  "
                + " AND c.csIdn IN ( SELECT n.csIdn FROM DemandNotice n WHERE n.orgid = c.orgId AND n.isDeleted = 'N' AND n.cpdNottype = :noticeType and n.nbNotduedt < :currDate ) "
                + " AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z where z.orgid = c.orgId and z.csIdn = c.csIdn  AND z.bmPaidFlag = 'N' AND z.bmTotalArrears > 0 ) "
                + " AND b.bmDuedate IS NOT NULL ";

        String UPDATE_DEMAND_NOTICE = "UPDATE DemandNotice d SET d.isDeleted = :status WHERE d.orgid = :orgId AND d.csIdn=:connectionId AND d.isDeleted = 'N'";
   
        String DEMAND_NOTICE_PER_CONN_ID ="SELECT c.csIdn, c.csCcn, b.bmIdno, b.bmDuedate, b.bmTotalOutstanding, c.csName, c.csMname, c.csLname, "
                + " c.csAdd, c.csFlatno, c.csBldplt, c.csLanear, c.csRdcross, c.csContactno FROM TbKCsmrInfoMH c , TbWtBillMasEntity b WHERE c.orgId = :orgId "
        		+ " AND c.csIdn = :csIdn  AND c.csIdn = b.csIdn AND c.orgId = b.orgid  AND c.csIdn NOT IN ( SELECT n.csIdn FROM DemandNotice"
        		+ " n WHERE n.orgid = c.orgId AND  n.isDeleted = 'N' ) AND b.bmIdno = ( SELECT MAX(z.bmIdno) FROM TbWtBillMasEntity z where"
        		+ " z.orgid = c.orgId and  z.csIdn = c.csIdn  AND z.bmPaidFlag = 'N' AND  z.bmTotalArrears > 0 AND z.bmDuedate IS NOT NULL )";
        		
    }

    interface ChangeOfUsageQuery {
        String QUERY_CONNECTION_BY_CONNECTION_NO = "SELECT am FROM TbKCsmrInfoMH am  WHERE am.orgId = :orgId AND  am.csCcn = :csCcn ";
        String QUERY_CONNECTION_BY_CONNECTION_ID = "SELECT am FROM TbKCsmrInfoMH am  WHERE am.orgId = :orgId AND  am.csIdn = :csIdn ";
        String QUERY_CHANGE_OF_USES = "SELECT cu FROM ChangeOfUsage cu  WHERE cu.orgId = :orgId AND  cu.apmApplicationId = :applicationId ";
        String UPDATE_CHANGE_OF_USES = "UPDATE TbKCsmrInfoMH cs  SET cs.trmGroup1 = :trmGroup1, cs.trmGroup2 = :trmGroup2, cs.trmGroup3 = :trmGroup3, cs.trmGroup4 = :trmGroup4, cs.trmGroup5 = :trmGroup5 WHERE cs.orgId = :orgId AND  cs.csIdn = :connectionId ";
		String QUERY_CONNECTION_BY_ORGID_AND_GRANT_FLAG = "SELECT cu FROM ChangeOfUsage cu  WHERE cu.orgId = :orgId AND  cu.chanGrantFlag = :chanGrantFlag ";
        String UPDATE_CHANGE_OF_USAGE= "UPDATE ChangeOfUsage c SET c.chanGrantFlag = :chanGrantFlag WHERE c.apmApplicationId = :apmApplicationId AND c.orgId = :orgId";

    }

    interface Exceptional_Gap_Tool {
        String Fetch_Add_Data = "select a "
                + "from TbKCsmrInfoMH a , "
                + "TbWtBillScheduleEntity b "
                + "where a.orgId=:orgId and "
                + "a.orgId=b.orgid "
                + "and a.csIsBillingActive='A' "
                + "and b.cnsYearid=:finyearId and "
                + "b.cnsMn=:meterType and "
                + "((COALESCE(a.codDwzid1,0)=(case when COALESCE(:codDwzid1,0)=-1 then COALESCE(a.codDwzid1,0) else COALESCE(:codDwzid1,0) end)) and "
                + "(COALESCE(a.codDwzid2,0)=(case when COALESCE(:codDwzid2,0)=-1 then COALESCE(a.codDwzid2,0) else COALESCE(:codDwzid2,0) end)) and "
                + "(COALESCE(a.codDwzid3,0)=(case when COALESCE(:codDwzid3,0)=-1 then COALESCE(a.codDwzid3,0) else COALESCE(:codDwzid3,0) end)) and "
                + "(COALESCE(a.codDwzid4,0)=(case when COALESCE(:codDwzid4,0)=-1 then COALESCE(a.codDwzid4,0) else COALESCE(:codDwzid4,0) end)) and "
                + "(COALESCE(a.codDwzid5,0)=(case when COALESCE(:codDwzid5,0)=-1 then COALESCE(a.codDwzid5,0) else COALESCE(:codDwzid5,0) end)) "
                + "or "
                + "(COALESCE(a.csCcncategory1,0)=(case when COALESCE(b.tbComparentDet5.codId,0)=-1 then COALESCE(a.csCcncategory1,0) else COALESCE(b.tbComparentDet5.codId,0) end)) and "
                + "(COALESCE(a.csCcncategory2,0)=(case when COALESCE(b.tbComparentDet.codId,0)=-1 then COALESCE(a.csCcncategory2,0) else COALESCE(b.tbComparentDet.codId,0) end)) and "
                + "(COALESCE(a.csCcncategory3,0)=(case when COALESCE(b.tbComparentDet2.codId,0)=-1 then COALESCE(a.csCcncategory3,0) else COALESCE(b.tbComparentDet2.codId,0) end)) and "
                + "(COALESCE(a.csCcncategory4,0)=(case when COALESCE(b.tbComparentDet3.codId,0)=-1 then COALESCE(a.csCcncategory4,0) else COALESCE(b.tbComparentDet3.codId,0) end)) and "
                + "(COALESCE(a.csCcncategory5,0)=(case when COALESCE(b.tbComparentDet4.codId,0)=-1 then COALESCE(a.csCcncategory5,0) else COALESCE(b.tbComparentDet4.codId,0) end))) ";

    }

    String ChangeOwnerQuery = "SELECT m.csIdn FROM ChangeOfOwnerMas m WHERE m.apmApplicationId=?1";

}
