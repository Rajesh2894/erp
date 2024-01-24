--liquibase formatted sql
--changeset Kanchan:V20210715151311__AL_tb_ac_secondaryhead_master_15072021.sql
ALTER TABLE  tb_ac_secondaryhead_master  ADD COLUMN `STATUS_REASON` VARCHAR(200) NULL AFTER `SAC_HEAD_OLD`;
--liquibase formatted sql
--changeset Kanchan:V20210715151311__AL_tb_ac_secondaryhead_master_150720211.sql
ALTER TABLE  tb_ac_secondaryhead_mas_hist  ADD COLUMN `STATUS_REASON` VARCHAR(200) NULL AFTER `SAC_HEAD_OLD`;
