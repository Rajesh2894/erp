--liquibase formatted sql
--changeset nilima:V20181030144517__AL_tb_sw_beat_mast_hist_25102018.sql
ALTER TABLE tb_sw_beat_mast_hist 
ADD COLUMN H_STATUS CHAR(1) NULL COMMENT 'History Status' AFTER BEAT_ACTIVE;

