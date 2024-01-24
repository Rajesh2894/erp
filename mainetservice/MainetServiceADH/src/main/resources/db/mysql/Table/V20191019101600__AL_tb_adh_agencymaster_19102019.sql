--liquibase formatted sql
--changeset Anil:V20191019101600__AL_tb_adh_agencymaster_19102019.sql
ALTER TABLE tb_adh_agencymaster CHANGE COLUMN GST_NO GST_NO VARCHAR(15) NULL DEFAULT NULL COMMENT 'GST No';
