--liquibase formatted sql
--changeset nilima:V20170424155132__del_duplic_tb_comparam_mas_SET_PREFIX.sql
delete FROM TB_COMPARAM_DET R WHERE R.CPM_ID in (58);
delete from tb_comparam_mas p where p.cpm_id in (58);
commit;

--liquibase formatted sql
--changeset nilima:V20170424155133__del_duplic_tb_comparam_mas_SET_PREFIX.sql
delete FROM TB_COMPARAM_DET R WHERE R.CPD_ID in (228);
commit;