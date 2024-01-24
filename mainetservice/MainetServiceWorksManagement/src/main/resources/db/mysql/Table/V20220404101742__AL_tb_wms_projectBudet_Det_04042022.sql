--liquibase formatted sql
--changeset Kanchan:V20220404101742__AL_tb_wms_projectBudet_Det_04042022.sql
alter table tb_wms_projectBudet_Det add column REMARK varchar(200) NULL DEFAULT NULL;
