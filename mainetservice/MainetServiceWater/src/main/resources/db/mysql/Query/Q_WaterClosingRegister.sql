select
tb1.COD_DWZID1 zone,
(select cod_desc from tb_comparent_det where cod_id=tb1.COD_DWZID1) z_desc,
tb1.COD_DWZID2 ward,
(select cod_desc from tb_comparent_det where cod_id=tb1.COD_DWZID2) w_desc,
tb1.TRM_GROUP1,
tb1.orgid,
tb1.cs_ccn,
tb2.APM_APPLICATION_ID,
(select cod_desc from tb_comparent_det where cod_id=tb1.TRM_GROUP1) Tarrif_Type,
(select cpd_desc from tb_comparam_det where cpd_id=tb1.cs_ccnsize) Connection_size,
tb1.cs_name,
tb1.CS_ADD Address,
tb2.disc_type,
date_format(tb2.DISC_EXECDATE,"%d-%m-%Y") as DiscExeDAte,
(select cpd_desc from tb_comparam_det where cpd_id=tb2.disc_type) disc_type_desc,
date_format(tb2.disc_appdate,"%d-%m-%Y") as DiscappDAte,
tb2.DISC_REASON
from
tb_csmr_info tb1,
tb_wt_disconnections tb2
where
tb1.cs_idn=tb2.CS_IDN and
(select cpd_value from tb_comparam_det where cpd_id=tb1.CS_CCNSTATUS)='C' and
COALESCE(tb1.orgid ,0)=(case when COALESCE(87,0)=0 then COALESCE(tb1.orgid ,0) else COALESCE(87,0) end) and
COALESCE(tb1.COD_DWZID1,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COD_DWZID1,0) else COALESCE(0,0) end)
and COALESCE(tb1.COD_DWZID2,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COD_DWZID2,0) else COALESCE(0,0) end)
and COALESCE(tb1.COD_DWZID3,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COD_DWZID3,0) else COALESCE(0,0) end)
and COALESCE(tb1.COD_DWZID4,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COD_DWZID4,0) else COALESCE(0,0) end)
and COALESCE(tb1.COD_DWZID5,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COD_DWZID5,0) else COALESCE(0,0) end)
and COALESCE(tb2.disc_type,0)=(case when COALESCE(0,0)=0 then COALESCE(tb2.disc_type,0) else COALESCE(0,0) end)
and COALESCE(tb1.TRM_GROUP1,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.TRM_GROUP1,0) else COALESCE(0,0) end)
and tb2.DISC_EXECDATE between '2020-02-01' and '2020-12-31'
and tb2.disc_type is not null and tb2.DISC_EXECDATE is not null
order by tb1.cs_ccn