--liquibase formatted sql
--changeset nilima:V20180921201230__al_tb_sw_vehicle_tgdet_21092018.sql
ALTER TABLE tb_sw_vehicle_tgdet
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK tb_sw_beat_mast' ;
