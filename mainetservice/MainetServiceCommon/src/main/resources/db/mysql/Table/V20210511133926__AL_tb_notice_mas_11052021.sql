--liquibase formatted sql
--changeset Kanchan:V20210511133926__AL_tb_notice_mas_11052021.sql
ALTER TABLE  `tb_notice_mas`
ADD COLUMN `FLAT_NO` VARCHAR(48) NULL default NULL;
