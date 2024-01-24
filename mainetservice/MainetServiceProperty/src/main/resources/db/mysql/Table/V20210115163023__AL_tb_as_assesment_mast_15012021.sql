--liquibase formatted sql
--changeset Kanchan:V20210115163023__AL_tb_as_assesment_mast_15012021.sql
alter table tb_as_assesment_mast  add Column DES_UPD_FLAG  char(1) null;





