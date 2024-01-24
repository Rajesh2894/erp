select x.*,y.* 
from 
(select
b.COD_DWZID1,
b.COD_DWZID2,
b.COD_DWZID3,
b.COD_DWZID4,
b.COD_DWZID5,
a.orgid,a.O_NLS_ORGNAME,b.cs_name,cs_meteredccn,
(select cpd_desc from tb_comparam_det where cpd_id=cs_meteredccn) Meter_Non_Meter,
TRM_GROUP1,
(select cod_desc from tb_comparent_det where cod_id=TRM_GROUP1) Tarrif_Type,
cs_ccnsize,
(select cpd_desc from tb_comparam_det where cpd_id=cs_ccnsize) Connection_Ssize,
CS_IDN,cs_ccn 
from tb_organisation a 
join 
(select 
CS_IDN,cs_ccn,orgid,cs_name,cs_meteredccn,
TRM_GROUP1,
cs_ccnsize,COD_DWZID1,
COD_DWZID2,COD_DWZID3,
COD_DWZID4,COD_DWZID5 
from tb_csmr_info) b
on a.orgid=b.orgid
where 
a.orgid=87 and 
COALESCE(b.COD_DWZID1,0)=(case when COALESCE(0,0)=0 then COALESCE(b.COD_DWZID1,0) else COALESCE(0,0) end) and
COALESCE(b.COD_DWZID2,0)=(case when COALESCE(0,0)=0 then COALESCE(b.COD_DWZID2,0) else COALESCE(0,0) end) AND
COALESCE(b.COD_DWZID3,0)=(case when COALESCE(0,0)=0 then COALESCE(b.COD_DWZID2,0) else COALESCE(0,0) end) AND
COALESCE(b.COD_DWZID4,0)=(case when COALESCE(0,0)=0 then COALESCE(b.COD_DWZID2,0) else COALESCE(0,0) end) AND
COALESCE(b.COD_DWZID5,0)=(case when COALESCE(0,0)=0 then COALESCE(b.COD_DWZID2,0) else COALESCE(0,0) end)) x ,
(select 
CS_IDN,
case when TAX='1' then 'CURRENT'
when TAX='2' then 'ARREAR' 
when TAX='3' then "ADJ" End TAX,
sum(TaxAmt) TaxAmt,
TAX_DISPLAY_SEQ 
from 
(select CS_IDN,
'1' TAX,
sum(BD_CUR_BAL_TAXAMT) TaxAmt,
b.TAX_DISPLAY_SEQ 
from 
TB_WT_BILL_DET a,
TB_WT_BILL_MAS c,
(select tax_id,tax_desc,TAX_DISPLAY_SEQ 
from tb_tax_mas a,tb_comparam_det b,
tb_department c where cpd_id=TAX_APPLICABLE and b.cpd_value in ('BILL') and c.DP_DEPTCODE = 'WT' 
and a.DP_DEPTID = c.DP_DEPTID) b
where
 a.TAX_ID = b.TAX_ID and 
 a.BM_IDNO = c.BM_IDNO and 
 c.BM_BILLDT <= '2020-02-17'
group by tax_desc,c.CS_IDN,TAX_DISPLAY_SEQ 
union 
select CS_IDN,
'2',
sum(BD_PRV_BAL_ARRAMT),
TAX_DISPLAY_SEQ 
from TB_WT_BILL_DET a,
(select 
tax_id,
tax_desc,
TAX_DISPLAY_SEQ 
from tb_tax_mas a,
tb_comparam_det b,
tb_department c where 
cpd_id=TAX_APPLICABLE and
b.cpd_value in ('BILL') and 
c.DP_DEPTCODE = 'WT' and 
a.DP_DEPTID = c.DP_DEPTID) b,
TB_WT_BILL_MAS c
where 
a.TAX_ID = b.TAX_ID and
a.BM_IDNO = c.BM_IDNO and 
c.BM_BILLDT <= '2020-03-31'
group by tax_desc,c.CS_IDN,TAX_DISPLAY_SEQ
union 
select adj_ref_no,
'3' TAX,
sum(adj_adjusted_amount),
TAX_DISPLAY_SEQ
from
tb_adjustment_mas a,
tb_adjustment_det b,
(select tax_id,tax_desc,TAX_DISPLAY_SEQ from tb_tax_mas a,tb_comparam_det b,tb_department c
where 
cpd_id=TAX_APPLICABLE and
b.cpd_value in ('BILL') and 
c.DP_DEPTCODE = 'WT' and 
a.DP_DEPTID = c.DP_DEPTID) c 
where dp_deptid in (select dp_deptid from tb_department where dp_deptcode='WT') 
and a.adj_id=b.adj_id 
and adj_type='P' and 
adj_date <= '2020-03-31' and 
b.tax_id=c.tax_id 
group by tax_desc,adj_ref_no,TAX_DISPLAY_SEQ) x
group by CS_IDN,TAX,TAX_DISPLAY_SEQ) y 
where x.CS_IDN=y.CS_IDN and 
TRM_GROUP1 like case 0 when 0 then '%' else 0 end and 
cs_meteredccn like case 0 when 0 then '%' else 0 end and 
cs_ccnsize like case 0 when 0 then '%' else 0 end and 
cs_ccn like case 0 when 0 then '%' else 0 end