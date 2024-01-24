--liquibase formatted sql
--changeset Kanchan:V20220217112113__AL_tb_as_pro_assesment_mast_17022022.sql
alter table  tb_as_pro_assesment_mast add column
PRO_ASS__PARSH_WARD1 bigint(12), 
add PRO_ASS__PARSH_WARD2 bigint(12),
add PRO_ASS__PARSH_WARD3 bigint(12),
add PRO_ASS__PARSH_WARD4 bigint(12), 
add PRO_ASS__PARSH_WARD5 bigint(12)  DEFAULT NULL;
