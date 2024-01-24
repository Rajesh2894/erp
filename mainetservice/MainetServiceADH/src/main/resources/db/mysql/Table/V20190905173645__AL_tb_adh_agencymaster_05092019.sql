--liquibase formatted sql
--changeset Anil:V20190905173645__AL_tb_adh_agencymaster_05092019.sql
ALTER TABLE tb_adh_agencymaster CHANGE COLUMN AGN_LIC_NO AGN_LIC_NO VARCHAR(40) NULL COMMENT 'New License No';

