select a.*,b.* from
(select
distinct
a.MN_ASS_no,
a.MN_ASS_OLDPROPNO,
c.MN_asso_owner_name,
a.orgid,
b.O_NLS_ORGNAME,
b.O_NLS_ORGNAME_MAR,
MN_ASS_ward1,
(select cod_desc from tb_comparent_det where cod_id=MN_ASS_ward1) as MN_ASS_ward1_Eng,
(select COD_DESC_MAR from tb_comparent_det where cod_id=MN_ASS_ward1) as MN_ASS_ward1_Mar,
MN_ASS_ward2,
(select cod_desc from tb_comparent_det where cod_id=MN_ASS_ward2) as MN_ASS_ward2_Eng,
(select COD_DESC_MAR from tb_comparent_det where cod_id=MN_ASS_ward2) as MN_ASS_ward2_Mar,
MN_ASS_ward3,
(select cod_desc from tb_comparent_det where cod_id=MN_ASS_ward3) as MN_ASS_ward3_Eng,
(select COD_DESC_MAR from tb_comparent_det where cod_id=MN_ASS_ward3) as MN_ASS_ward3_Mar,
MN_ASS_ward4,
(select cod_desc from tb_comparent_det where cod_id=MN_ASS_ward4) as MN_ASS_ward4_Eng,
(select COD_DESC_MAR from tb_comparent_det where cod_id=MN_ASS_ward4) as MN_ASS_ward4_Mar,
MN_ASS_ward5,
(select cod_desc from tb_comparent_det where cod_id=MN_ASS_ward5) as MN_ASS_ward5_Eng,
(select COD_DESC_MAR from tb_comparent_det where cod_id=MN_ASS_ward5) as MN_ASS_ward5_Mar
FROM
(select MN_ASS_id,MN_ASS_no,MN_ASS_OLDPROPNO,orgid,MN_ASS_ward1,MN_ASS_ward2,MN_ASS_ward3,MN_ASS_ward4,MN_ASS_ward5
from TB_AS_ASSESMENT_MAST a where a. orgid=87
union
select PRO_ASS_ID,PRO_ASS_NO,PRO_ASS_OLDPROPNO,orgid,PRO_ASS_WARD1,PRO_ASS_WARD2,PRO_ASS_WARD3,PRO_ASS_WARD4,PRO_ASS_WARD5
from TB_AS_PRO_ASSESMENT_MAST b where b. orgid=87) A ,
tb_organisation b ,
(select a.* from tb_as_assesment_owner_dtl a where a. orgid=87 and MN_asso_otype='P'
union
select b.* from tb_as_pro_assesment_owner_dtl b where b. orgid=87 and pro_asso_otype='P') c
where a.orgid=b.orgid and a.MN_ass_id=c.MN_ass_id AND
a.orgid=(case when COALESCE(87,0)=0 then COALESCE(a.orgid,0) else COALESCE(87,0) end) and
COALESCE(MN_ASS_ward1,0)=(case when COALESCE(0,0)=0 then COALESCE(MN_ASS_ward1,0) else COALESCE(0,0) end) and
COALESCE(MN_ASS_ward2,0)=(case when COALESCE(0,0)=0 then COALESCE(MN_ASS_ward2,0) else COALESCE(0,0) end) and
COALESCE(MN_ASS_ward3,0)=(case when COALESCE(0,0)=0 then COALESCE(MN_ASS_ward3,0) else COALESCE(0,0) end) and
COALESCE(MN_ASS_ward4,0)=(case when COALESCE(0,0)=0 then COALESCE(MN_ASS_ward4,0) else COALESCE(0,0) end) and
COALESCE(MN_ASS_ward5,0)=(case when COALESCE(0,0)=0 then COALESCE(MN_ASS_ward5,0) else COALESCE(0,0) end) AND
COALESCE(a.MN_ASS_no,0)=(case when COALESCE('X','X')='X' then COALESCE(a.MN_ASS_no,0) else COALESCE('X','X') end)) a
join (select
y.MN_PROP_NO,
y.mn_no as bill_no,
CONCAT(y.mn_no,'-',date_format(y.MN_BILLDT,"%d-%m-%Y")) MN_BILLDT,
case when y.TAX='1' then 'CURRENT'
when y.TAX='2' then 'ARREAR'
when y.TAX='3' then 'ADJ' End TAX,
TAX_DISPLAY_SEQ,tax_desc,
sum(y.bd_prv_arramt) bd_prv_arramt
from (
select x.MN_PROP_NO,x.mn_no,x.MN_BILLDT,'1' TAX,x.TAX_DISPLAY_SEQ,tax_desc,(sum(bd_prv_arramt) -SUM(bd_prv_balarramt)) bd_prv_arramt
from (select MN_PROP_NO,mn_no,MN_BILLDT,TAX_DISPLAY_SEQ,tax_desc,sum(BD_CUR_TAXAMT) bd_prv_arramt,SUM(BD_CUR_BAL_TAXAMT) bd_prv_balarramt
from (select BD_BILLDETID,BM_IDNO,TAX_ID,BD_CUR_TAXAMT,BD_CUR_BAL_TAXAMT from TB_AS_BILL_DET where  orgid=87
union
select pro_bd_billdetid,bm_idno,TAX_ID,pro_bd_cur_taxamt,pro_bd_cur_bal_taxamt from TB_AS_PRO_BILL_DET where  orgid=87) a,
(select tax_id,tax_desc,TAX_DISPLAY_SEQ from tb_tax_mas a, tb_comparam_det b, tb_department c
where cpd_id=TAX_APPLICABLE and b.cpd_value in ('BILL') and c.DP_DEPTCODE = 'AS' and a.DP_DEPTID = c.DP_DEPTID) b,
(select MN_BM_IDNO,MN_PROP_NO,orgid,MN_FROMDT,MN_NO,MN_BILLDT from TB_AS_BILL_MAS where  orgid=87
union
select pro_bm_idno,pro_prop_no,orgid,pro_bm_fromdt,pro_bm_no,pro_bm_billdt from TB_AS_PRO_BILL_MAS where  orgid=87) c
where a.TAX_ID = b.TAX_ID and a.BM_IDNO = c.MN_BM_IDNO and 
date_format(c.MN_FROMDT,"%d-%m-%Y")=(select  date_format(FA_FROMDATE,"%d-%m-%Y") from tb_financialyear where '2020-02-13' between FA_FROMDATE and FA_TODATE)
group by c.MN_PROP_NO,mn_no,MN_BILLDT,TAX_DISPLAY_SEQ,tax_desc ) x
group by x.MN_PROP_NO,x.mn_no,x.MN_BILLDT,TAX_DISPLAY_SEQ,x.tax_desc
union
select x.MN_PROP_NO,x.mn_no,x.MN_BILLDT,'2' TAX,TAX_DISPLAY_SEQ,tax_desc,(SUM(BD_PRV_ARRAMT)-sum(bd_prv_balarramt)) bd_prv_arramt
from
(select MN_PROP_NO,mn_no,MN_BILLDT,TAX_DISPLAY_SEQ,tax_desc,SUM(BD_PRV_ARRAMT) bd_prv_arramt,sum(bd_prv_bal_arramt) bd_prv_balarramt from
(select BD_BILLDETID,BM_IDNO,TAX_ID,BD_PRV_ARRAMT,bd_prv_bal_arramt from TB_AS_BILL_DET  where  orgid=87
union
select pro_bd_billdetid,bm_idno,TAX_ID,pro_bd_prv_arramt,pro_bd_prv_bal_arramt from TB_AS_PRO_BILL_DET where  orgid=87) a,
(select tax_id,tax_desc,TAX_DISPLAY_SEQ from tb_tax_mas a,tb_comparam_det b,tb_department c
where cpd_id=TAX_APPLICABLE and b.cpd_value in ('BILL') and c.DP_DEPTCODE = 'AS' and a.DP_DEPTID = c.DP_DEPTID) b,
(select MN_BM_IDNO,MN_PROP_NO,orgid,MN_FROMDT,MN_NO,MN_BILLDT from TB_AS_BILL_MAS where  orgid=87
union
select pro_bm_idno,pro_prop_no,orgid,pro_bm_fromdt,pro_bm_no,pro_bm_billdt from TB_AS_PRO_BILL_MAS where  orgid=87) c
where a.TAX_ID = b.TAX_ID and a.BM_IDNO = c.MN_BM_IDNO and 
date_format(c.MN_FROMDT,"%d-%m-%Y")=
(select date_format(FA_FROMDATE,"%d-%m-%Y") from tb_financialyear where '2020-02-13' between FA_FROMDATE and FA_TODATE)
group by c.MN_PROP_NO,mn_no,MN_BILLDT,TAX_DISPLAY_SEQ,tax_desc) x
group by x.MN_PROP_NO,x.mn_no,x.MN_BILLDT,tax_desc,TAX_DISPLAY_SEQ )Y
group by MN_PROP_NO,TAX,mn_no,MN_BILLDT,TAX_DISPLAY_SEQ,tax_desc ) b
on a.MN_ASS_no=b.MN_PROP_NO
where b.MN_PROP_NO is not null  
order by MN_ASS_no,TAX_DISPLAY_SEQ
