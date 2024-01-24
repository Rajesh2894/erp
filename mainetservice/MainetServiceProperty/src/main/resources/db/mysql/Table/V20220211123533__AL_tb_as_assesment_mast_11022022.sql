--liquibase formatted sql
--changeset Kanchan:V20220211123533__AL_tb_as_assesment_mast_11022022.sql
alter table tb_as_assesment_mast add column 
MN_ASS__PARSH_WARD1 bigint(12), 
add MN_ASS__PARSH_WARD2 bigint(12), 
add MN_ASS__PARSH_WARD3 bigint(12), 
add MN_ASS__PARSH_WARD4 bigint(12), 
add MN_ASS__PARSH_WARD5 bigint(12) DEFAULT NULL;
