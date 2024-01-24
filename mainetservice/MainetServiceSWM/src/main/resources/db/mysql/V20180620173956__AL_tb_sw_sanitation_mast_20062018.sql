--liquibase formatted sql
--changeset nilima:V20180620173956__AL_tb_sw_sanitation_mast_20062018.sql
ALTER TABLE tb_sw_sanitation_mast
ADD COLUMN SAN_ADDRESS VARCHAR(200) NOT NULL AFTER `LONGITUDE`;

