select x.*,y.*,z.rcpt_no from
(select
b.MN_ASS_WARD1,
b.MN_ASS_WARD2,
b.MN_ASS_WARD3,
b.MN_ASS_WARD4,
b.MN_ASS_WARD5,
a.orgid,a.O_NLS_ORGNAME,ORG_CPD_ID_STATE,
(select cpd_desc from tb_comparam_det where cpd_id=ORG_CPD_ID_STATE) as STATE,
a.ORG_CPD_ID_DIV,
(select cpd_desc from tb_comparam_det where cpd_id=ORG_CPD_ID_DIV) as DIVs,
a.ORG_CPD_ID_DIS,
(select cpd_desc from tb_comparam_det where cpd_id=ORG_CPD_ID_DIS) as DIS,
ORG_LATITUDE,ORG_LONGITUDE,mn_ASS_no pro_prop_no,MN_ASS_OLDPROPNO old_prop_no,b.owner_name from tb_organisation a join
(select mn_ASS_no,
(select group_concat(MN_asso_owner_name) owner_name from tb_as_assesment_owner_dtl where MN_ASS_ID=x.MN_ASS_id) owner_name,
orgid,
MN_ASS_WARD1,
MN_ASS_WARD2,
MN_ASS_WARD3,
MN_ASS_WARD4,
MN_ASS_WARD5,
MN_ASS_OLDPROPNO from tb_as_assesment_mast x union
select pro_ASS_no,(select group_concat(pro_asso_owner_name)  from tb_as_pro_assesment_owner_dtl
where pro_ass_id=y.PRO_ASS_ID),orgid,PRO_ASS_WARD1,PRO_ASS_WARD2,PRO_ASS_WARD3,
PRO_ASS_WARD4,
PRO_ASS_WARD5,PRO_ASS_OLDPROPNO from tb_as_pro_assesment_mast y
) b on a.orgid=b.orgid
where a.orgid=(case when COALESCE(87,0)=0 then COALESCE(a.orgid,0) else COALESCE(87,0) end)  and
COALESCE(b.MN_ASS_WARD1,0)=(case when COALESCE(0,0)=0 then COALESCE(b.MN_ASS_WARD1,0) else COALESCE(0,0) end) and
COALESCE(b.MN_ASS_WARD2,0)=(case when COALESCE(0,0)=0 then COALESCE(b.MN_ASS_WARD2,0) else COALESCE(0,0) end) and
COALESCE(b.MN_ASS_WARD3,0)=(case when COALESCE(0,0)=0 then COALESCE(b.MN_ASS_WARD3,0) else COALESCE(0,0) end) and
COALESCE(b.MN_ASS_WARD4,0)=(case when COALESCE(0,0)=0 then COALESCE(b.MN_ASS_WARD4,0) else COALESCE(0,0) end) and
COALESCE(b.MN_ASS_WARD5,0)=(case when COALESCE(0,0)=0 then COALESCE(b.MN_ASS_WARD5,0) else COALESCE(0,0) end)
) x
join
(select a.pro_prop_no,
b.TAX_DESC,
'ARREAR' TAX,
group_concat('RCPT_NO:','-',b.RM_RCPTNO,'RCPT_DT:','-',date_format(b.RM_DATE,'%d-%m-%Y')) grp_info,
sum(RF_FEEAMOUNT) RF_FEEAMOUNT,
b.CREATED_BY
from
(select pro_bm_idno,pro_prop_no,orgid from tb_as_pro_bill_mas  where orgid=87 and pro_bm_billdt <= '2020-02-13'
union 
select MN_BM_IDNO,MN_PROP_NO,orgid from tb_as_bill_mas where orgid=87 and
MN_BILLDT <= '2020-02-13') a,
(SELECT B.BM_IDNO,c.TAX_DESC,a.RM_DATE,a.RM_RCPTNO,
ADDITIONAL_REF_NO,RF_FEEAMOUNT,a.CREATED_BY 
FROM TB_RECEIPT_MAS A,TB_RECEIPT_DET B,TB_TAX_MAS C
WHERE A.RM_RCPTID = B.RM_RCPTID AND A.DP_DEPTID IN (SELECT DP_DEPTID FROM TB_DEPARTMENT WHERE DP_DEPTCODE='AS')
AND B.TAX_ID=C.TAX_ID) b where a.pro_prop_no=b.ADDITIONAL_REF_NO and a.pro_bm_idno<>b.BM_IDNO and
COALESCE(b.CREATED_BY,0)=(case when COALESCE(85,0)=0 then COALESCE(b.CREATED_BY,0) else COALESCE(85,0) end) 
group by a.pro_prop_no,b.TAX_DESC,b.CREATED_BY 
union
select a.pro_prop_no,
b.TAX_DESC,
'CURRENT' TAX,
sum(RF_FEEAMOUNT) RF_FEEAMOUNT,
group_concat('RCPT_NO:','-',b.RM_RCPTNO,'RCPT_DT:','-',date_format(b.RM_DATE,'%d-%m-%Y')) grp_info,
b.CREATED_BY
from
(select pro_bm_idno,pro_prop_no,orgid from tb_as_pro_bill_mas  where orgid=87 and pro_bm_billdt <= '2020-02-13'
union 
select MN_BM_IDNO,MN_PROP_NO,orgid from tb_as_bill_mas where orgid=87 and MN_BILLDT <= '2020-02-13') a,
(SELECT B.BM_IDNO,c.TAX_DESC,a.RM_DATE,a.RM_RCPTNO,ADDITIONAL_REF_NO,RF_FEEAMOUNT,a.CREATED_BY FROM TB_RECEIPT_MAS A,TB_RECEIPT_DET B,TB_TAX_MAS C
WHERE A.RM_RCPTID = B.RM_RCPTID AND A.DP_DEPTID IN (SELECT DP_DEPTID FROM TB_DEPARTMENT WHERE DP_DEPTCODE='AS')
AND B.TAX_ID=C.TAX_ID) b 
where a.pro_prop_no=b.ADDITIONAL_REF_NO and 
a.pro_bm_idno=b.BM_IDNO and
COALESCE(b.CREATED_BY,0)=(case when COALESCE(85,0)=0 then COALESCE(b.CREATED_BY,0) else COALESCE(85,0) end) 
group by a.pro_prop_no,b.TAX_DESC,b.CREATED_BY) y 
on x.pro_prop_no=y.pro_prop_no
join (select ADDITIONAL_REF_NO,group_concat(RM_RCPTNO) rcpt_no from TB_RECEIPT_MAS where orgid=87 and
RM_DATE <= '2020-02-13' group by ADDITIONAL_REF_NO) z on x.pro_prop_no=z.ADDITIONAL_REF_NO

