--liquibase formatted sql
--changeset Kanchan:V20210317105038__AL_tb_as_assesment_factor_dtl_17032021.sql
alter table tb_as_assesment_factor_dtl  add column
MN_assf_factor_date datetime DEFAULT NULL,
add MN_assf_factor_remark varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210317105038__AL_tb_as_assesment_factor_dtl_170320211.sql
alter table tb_as_assesment_owner_dtl add column
 MN_ASSO_OWNER_NAME_REG varchar(50) DEFAULT NULL;
 
