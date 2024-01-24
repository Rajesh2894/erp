create or replace view vw_pg_bank_master_portal as
select a.bm_bankid, b.cb_bankname bm_bankname,
a.pb_merchantid,c.pb_bankurl,a.orgid
from tb_pg_bank  a,
tb_custbanks b,
tb_pg_bank_detail c
where a.bm_bankid= b.cb_bankcode
and a.orgid        = b.orgid
and a.orgid        = c.orgid
and a.bm_bankid=c.bm_bankid
and a.bm_active = 'A'