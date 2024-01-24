--liquibase formatted sql
--changeset Anil:V20191019112024__AL_tb_adh_agencymaster_hist_19102019.sql
ALTER TABLE tb_adh_agencymaster_hist CHANGE COLUMN GST_NO GST_NO VARCHAR(15) NULL DEFAULT NULL COMMENT 'GST No';
