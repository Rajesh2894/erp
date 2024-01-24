--liquibase formatted sql
--changeset nilima:V20190627191015_AL_tb_ac_projectedrevenue1.sql
alter table tb_ac_projectedrevenue change column `FI04_N1` `FIELD_ID` BIGINT(12) DEFAULT NULL;
--liquibase formatted sql
--changeset nilima:V20190627191015_AL_tb_ac_projectedrevenue2.sql
alter table tb_ac_projected_expenditure change column `FI04_N1` `FIELD_ID` BIGINT(12) DEFAULT NULL;