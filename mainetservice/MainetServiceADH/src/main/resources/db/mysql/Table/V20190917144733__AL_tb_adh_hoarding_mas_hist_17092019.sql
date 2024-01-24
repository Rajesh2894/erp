--liquibase formatted sql
--changeset Anil:V20190917144733__AL_tb_adh_hoarding_mas_hist_17092019.sql
ALTER TABLE tb_adh_hoarding_mas_hist
ADD COLUMN HRD_ID_H BIGINT(12) NOT NULL FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY(HRD_ID_H);
