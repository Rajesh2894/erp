--liquibase formatted sql
--changeset Kanchan:V20210121113053__AL_tb_as_pro_assesment_mast_21012021.sql
alter table tb_as_pro_assesment_mast add column DES_UPD_FLAG  char(1) null;
