--liquibase formatted sql
--changeset Anil:V20190704161758__AL_tb_lgl_judgement_mast_07042019.sql
ALTER TABLE tb_lgl_judgement_mast ADD COLUMN JUDGEMENT_STATUS CHAR(1) NOT NULL COMMENT 'user id who updated the record' AFTER LG_IP_MAC_UPD;

