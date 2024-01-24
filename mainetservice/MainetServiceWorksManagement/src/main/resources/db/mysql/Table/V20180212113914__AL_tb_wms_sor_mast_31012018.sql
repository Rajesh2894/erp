--liquibase formatted sql
--changeset priya:V20180212113914__AL_tb_wms_sor_mast_31012018.sql
ALTER TABLE tb_wms_sor_mast
ADD COLUMN SOR_CPD_ID VARCHAR(45) NULL COMMENT 'SOR name from prefix' AFTER `LG_IP_MAC_UPD`;

