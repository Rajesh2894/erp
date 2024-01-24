update tb_group_mast set GR_CODE='SUPER_ADMIN' where GR_CODE='SUPER ADMIN';
commit;

update tb_comparam_mas set CPM_MODULE_NAME='COM' where CPM_PREFIX in ('AUT','CLR','DTY','VNT','VSS','VST','BAT','FSD ');
COMMIT;