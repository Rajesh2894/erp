--liquibase formatted sql
--changeset Kanchan:V20220524155517__AL_tb_wms_projectBudet_Det_24052022.sql
alter table tb_wms_projectBudet_Det add column FIELD_ID bigint(12) NULL default null;
