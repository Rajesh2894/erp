--liquibase formatted sql
--changeset Anil:V20200604191407__AL_tb_wms_workdefination_04062020.sql
ALTER TABLE tb_wms_workdefination ADD COLUMN WRK_SUBTPEID BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200604191407__AL_tb_wms_workdefination_040620201.sql
ALTER TABLE tb_wms_workdefination_hist ADD COLUMN WRK_SUBTPEID BIGINT(12) NULL;
