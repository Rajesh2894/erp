--liquibase formatted sql
--changeset Anil:V20190701153657__AL_tb_rl_estate_booking_01072019.sql
ALTER TABLE tb_rl_estate_booking 
ADD COLUMN EBK_RESAON_OFFREEZ VARCHAR(300) NULL AFTER EBK_NOINVITIES;
