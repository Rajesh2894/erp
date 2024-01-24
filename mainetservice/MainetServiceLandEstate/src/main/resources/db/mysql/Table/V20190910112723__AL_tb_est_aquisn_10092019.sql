--liquibase formatted sql
--changeset Anil:V20190910112723__AL_tb_est_aquisn_10092019.sql
ALTER TABLE tb_est_aquisn ADD COLUMN VENDOR_ID BIGINT(12) NULL AFTER LN_ADDRESS;
