--liquibase formatted sql
--changeset nilima:V20171116120856__vw_voucher_detail_14112017.sql
create or replace view vw_voucher_detail as
select vd.VOUDET_ID AS VOUDET_ID,
ac.VOU_ID AS VOU_ID,ac.VOU_NO AS VOU_NO,
ac.VOU_DATE AS VOU_DATE,
ac.ORGID AS ORGID,
ac.PAYER_PAYEE AS PAYER_PAYEE,
ac.NARRATION AS Particulars,
ac.VOU_TYPE_CPD_ID AS VOU_TYPE_CPD_ID,
vd.BUDGETCODE_ID AS BUDGETCODE_ID,
b.AC_HEAD_CODE AS AC_HEAD_CODE,
b.ba_accountid AS BA_ACCOUNTID,
vd.SAC_HEAD_ID AS SAC_HEAD_ID,
(case when vd.DRCR_CPD_ID = (select cd.CPD_ID from tb_comparam_det cd , tb_comparam_mas cm where cm.CPM_ID = cd.CPM_ID and cd.CPD_VALUE = 'CR' and cm.CPM_PREFIX = 'DCR') then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_CR,
(case when vd.DRCR_CPD_ID = (select cd.CPD_ID from tb_comparam_det cd , tb_comparam_mas cm where cm.CPM_ID = cd.CPM_ID and cd.CPD_VALUE = 'DR' and cm.CPM_PREFIX = 'DCR') then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_DR,
ac.VOU_POSTING_DATE AS VOU_POSTING_DATE,
vd.VOUDET_AMT AS Voucher_Amount,
(select tb_comparam_det.CPD_VALUE from tb_comparam_det where (tb_comparam_det.CPD_ID = vd.DRCR_CPD_ID)) AS DRCR,
ac.VOU_SUBTYPE_CPD_ID AS VOU_SUBTYPE_CPD_ID
from tb_ac_voucher ac , tb_ac_voucher_det vd , tb_comparam_det cd , tb_ac_secondaryhead_master b
where ac.ORGID = vd.ORGID and ac.VOU_ID = vd.VOU_ID
 and ac.VOU_TYPE_CPD_ID = cd.CPD_ID and cd.CPD_STATUS = 'A'
 and vd.ORGID = b.ORGID and vd.SAC_HEAD_ID = b.SAC_HEAD_ID
 and ac.AUTHO_FLG = 'Y';