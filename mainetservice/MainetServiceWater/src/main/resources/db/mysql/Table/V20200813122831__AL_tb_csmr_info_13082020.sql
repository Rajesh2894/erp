--liquibase formatted sql
--changeset Anil:V20200813122831__AL_tb_csmr_info_13082020.sql
ALTER TABLE tb_csmr_info ADD COLUMN PM_PROP_USG_TYP VARCHAR(20) NULL;
--liquibase formatted sql
--changeset Anil:V20200813122831__AL_tb_csmr_info_130820201.sql
ALTER TABLE tb_csmr_info_hist ADD COLUMN PM_PROP_USG_TYP VARCHAR(20) NULL;
