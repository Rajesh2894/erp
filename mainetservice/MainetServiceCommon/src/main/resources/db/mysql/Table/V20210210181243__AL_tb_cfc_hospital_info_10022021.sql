--liquibase formatted sql
--changeset Kanchan:V20210210181243__AL_tb_cfc_hospital_info_10022021.sql
alter  table tb_cfc_hospital_info add column
CREATED_BY	bigint(10) NOT NULL,
add CREATED_DATE datetime NOT NULL,
add LG_IP_MAC varchar(100) NOT NULL,
add LG_IP_MAC_UPD varchar(100) NULL,
add UPDATED_BY	bigint(12) NULL,
add UPDATED_DATE datetime NULL;
--liquibase formatted sql
--changeset Kanchan:V20210210181243__AL_tb_cfc_hospital_info_100220211.sql
alter  table tb_cfc_hospital_info_det add column
CREATED_BY	bigint(10) NOT NULL,
add CREATED_DATE datetime NOT NULL,
add LG_IP_MAC varchar(100) NOT NULL,
add LG_IP_MAC_UPD varchar(100) NULL,
add UPDATED_BY	bigint(12) NULL,
add UPDATED_DATE datetime NULL;
