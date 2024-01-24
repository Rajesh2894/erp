update TB_COMPARAM_MAS SET CPM_REPLICATE_FLAG='N' ,LOAD_AT_STARTUP='Y' where CPM_PREFIX='OTY';
commit;

delete from tb_comparam_det where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OTY')
and  orgid<>1;
commit;

update TB_COMPARAM_MAS SET CPM_REPLICATE_FLAG='N' ,LOAD_AT_STARTUP='Y' where CPM_PREFIX='STT';
commit;

delete from tb_comparam_det where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='STT') and orgid<>1;
commit;

