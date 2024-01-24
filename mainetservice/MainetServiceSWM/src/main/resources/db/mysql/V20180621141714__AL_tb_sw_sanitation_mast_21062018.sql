--liquibase formatted sql
--changeset nilima:V20180621141714__AL_tb_sw_sanitation_mast_21062018.sql
ALTER TABLE tb_sw_sanitation_mast
CHANGE COLUMN SAN_ADDRESS SAN_ADDRESS VARCHAR(200) NULL ;
