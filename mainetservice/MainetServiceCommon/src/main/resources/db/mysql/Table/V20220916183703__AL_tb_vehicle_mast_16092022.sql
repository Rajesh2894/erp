--liquibase formatted sql
--changeset Kanchan:V20220916183703__AL_tb_vehicle_mast_16092022.sql
Alter table tb_vehicle_mast  add column PUR_REF_NO varchar(20) null default null AFTER VE_ACTIVE;