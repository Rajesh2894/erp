--liquibase formatted sql
--changeset Kanchan:V20230110195201__AL_tb_sfac_fpo_administrative_det_10012023.sql
alter table tb_sfac_fpo_administrative_det
add column NAME_OF_BOARD varchar(100) null default null,
add column CIN_NO varchar(50) null default null;