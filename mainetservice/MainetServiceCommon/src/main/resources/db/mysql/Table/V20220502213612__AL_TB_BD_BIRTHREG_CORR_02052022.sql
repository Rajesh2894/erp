--liquibase formatted sql
--changeset Kanchan:V20220502213612__AL_TB_BD_BIRTHREG_CORR_02052022.sql
Alter table TB_BD_BIRTHREG_CORR  add column PD_REG_UNIT_ID bigint(20) NUll default null;
