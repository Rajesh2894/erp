--liquibase formatted sql
--changeset Kanchan:V20210622150354__AL_tb_as_transfer_mst_22062021.sql
ALTER TABLE `tb_as_transfer_mst`  ADD COLUMN `FLAT_NO` VARCHAR(100) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210622150354__AL_tb_as_transfer_mst_220620211.sql
ALTER TABLE `tb_as_transfer_mst`  ADD COLUMN `AUTHO_STATUS` VARCHAR(10) NULL default NULL;


