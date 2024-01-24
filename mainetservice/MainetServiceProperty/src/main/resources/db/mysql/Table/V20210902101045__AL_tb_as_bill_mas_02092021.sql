--liquibase formatted sql
--changeset Kanchan:V20210902101045__AL_tb_as_bill_mas_02092021.sql
ALTER TABLE `tb_as_bill_mas`  ADD COLUMN `PARENT_MN_NO` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210902101045__AL_tb_as_bill_mas_020920211.sql
ALTER TABLE `tb_as_bill_mas_hist`  ADD COLUMN `PARENT_MN_NO` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210902101045__AL_tb_as_bill_mas_020920212.sql
ALTER TABLE `tb_as_pro_bill_mas_hist`  ADD COLUMN `PARENT_MN_NO` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210902101045__AL_tb_as_bill_mas_020920213.sql
ALTER TABLE `TB_AS_PRO_BILL_MAS`  ADD COLUMN `PARENT_MN_NO` VARCHAR(50) NULL default NULL;

