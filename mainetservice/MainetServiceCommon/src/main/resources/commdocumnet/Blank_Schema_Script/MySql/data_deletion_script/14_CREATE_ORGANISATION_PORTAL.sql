update _sequences set next=14
where name='SQ_ORGID_1';
commit;

update _sequences set next=35 where name='SQ_EMPID_9';
commit;

insert tb_organisation 
(ORGID, 
O_NLS_ORGNAME,
USER_ID,
LMODDATE, 
TDS_ACCOUNTNO, 
TDS_CIRCLE, 
TDS_PAN_GIR_NO,  
ORG_TAX_DED_NAME,  
ORG_TAX_DED_ADDR, 
ORG_SHORT_NM, 
O_LOGO, 
ORG_ADDRESS, 
O_NLS_ORGNAME_MAR, 
ORG_ADDRESS_MAR, 
UPDATED_BY, 
UPDATED_DATE, 
TRAN_START_DATE, 
ESDT_DATE, 
ORG_STATUS, 
ORG_CPD_ID,
DEFAULT_STATUS, 
ORG_CPD_ID_DIV, 
ORG_CPD_ID_OST, 
ORG_CPD_ID_DIS, 
ORG_EMAIL_ID, 
VAT_CIRCLE, 
VAT_DED_NAME, 
ORG_CPD_ID_STATE, 
ORG_LATITUDE, 
ORG_LONGITUDE, 
ORG_GST_NO, 
ULB_ORG_ID )
(select 
ORGID, 
O_NLS_ORGNAME,
CREATED_BY,
CREATED_DATE, 
TDS_ACCOUNTNO, 
TDS_CIRCLE, 
TDS_PAN_GIR_NO,  
ORG_TAX_DED_NAME,  
ORG_TAX_DED_ADDR, 
ORG_SHORT_NM, 
O_LOGO, 
ORG_ADDRESS, 
O_NLS_ORGNAME_MAR, 
ORG_ADDRESS_MAR, 
UPDATED_BY, 
UPDATED_DATE, 
TRAN_START_DATE, 
ESDT_DATE, 
ORG_STATUS, 
ORG_CPD_ID,
DEFAULT_STATUS, 
ORG_CPD_ID_DIV, 
ORG_CPD_ID_OST, 
ORG_CPD_ID_DIS, 
ORG_EMAIL_ID, 
VAT_CIRCLE, 
VAT_DED_NAME, 
ORG_CPD_ID_STATE, 
ORG_LATITUDE, 
ORG_LONGITUDE, 
ORG_GST_NO, 
ULB_ORG_ID 
from suda_service1.tb_organisation where orgid>13);
commit;

update _sequences set next=149
where name='SQ_ORGID_1';
commit;

delete from employee_hist;
commit;

delete from tb_group_mast_hist;
commit;

delete from tb_portal_service_master_hist;
commit;

update _sequences set next=36 where name='SQ_GM_ID_5';
commit;


update tb_comparam_det set cpd_default='Y'
where cpm_id in
(select CPM_ID from tb_comparam_mas where CPM_PREFIX='SLI')
and cpd_value='S';

update tb_comparam_det set cpd_default='N'
where cpm_id in
(select CPM_ID from tb_comparam_mas where CPM_PREFIX='SLI')
and cpd_value='L';
commit;

