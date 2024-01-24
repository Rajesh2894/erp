UPDATE TB_COMPARAM_MAS set CPM_REPLICATE_FLAG='N' where cpm_prefix='YOC';
delete from TB_COMPARAM_DET where cpm_id=66 and orgid<>81;
update TB_FINCIALYEARORG_MAP 
set fa_frommonth=null,
fa_tomonth=null,
fa_fromyear=null,
fa_toyear=null,
fa_monstatus=null,
fa_yearstatus=null;
commit;