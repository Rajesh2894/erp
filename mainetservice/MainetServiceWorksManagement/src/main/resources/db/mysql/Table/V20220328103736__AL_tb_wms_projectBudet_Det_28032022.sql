--liquibase formatted sql
--changeset Kanchan:V20220328103736__AL_tb_wms_projectBudet_Det_28032022.sql
alter table tb_wms_projectBudet_Det add column YE_ACTIVE char(1) Null default Null;
