--liquibase formatted sql
--changeset Kanchan:V20210401093731__AL_tb_as_pro_factor_dtl_hist_01042021.sql
alter table tb_as_pro_factor_dtl_hist add column PRO_assf_factor_date datetime DEFAULT NULL,  add column PRO_assf_factor_remark varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210401093731__AL_tb_as_pro_factor_dtl_hist_010420211.sql
alter table tb_as_pro_owner_dtl_hist add column  PRO_asso_owner_name_reg varchar(50) DEFAULT NULL;
