--liquibase formatted sql
--changeset nilima:V20190621111015_AL_tb_ac_projectedrevenue_hist1.sql
alter table tb_ac_projectedrevenue_hist change column `FI04_N1` `FIELD_ID` BIGINT(12) DEFAULT NULL;
--liquibase formatted sql
--changeset nilima:V20190621111015_AL_tb_ac_projectedrevenue_hist2.sql
alter table tb_ac_projected_expendi_hist change column `FI04_N1` `FIELD_ID` BIGINT(12) DEFAULT NULL;