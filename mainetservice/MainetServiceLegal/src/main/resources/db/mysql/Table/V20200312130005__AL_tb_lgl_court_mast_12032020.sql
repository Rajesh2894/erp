--liquibase formatted sql
--changeset Anil:V20200312130005__AL_tb_lgl_court_mast_12032020.sql
ALTER TABLE tb_lgl_court_mast
ADD COLUMN CRT_STATE BIGINT(12) NULL AFTER LG_IP_MAC_UPD,
ADD COLUMN CRT_CITY VARCHAR(250) NULL AFTER CRT_STATE;
--liquibase formatted sql
--changeset Anil:V20200312130005__AL_tb_lgl_court_mast_120320201.sql
ALTER TABLE tb_lgl_court_mast_hist
ADD COLUMN CRT_STATE BIGINT(12) NULL AFTER LG_IP_MAC_UPD,
ADD COLUMN CRT_CITY VARCHAR(250) NULL AFTER CRT_STATE;
