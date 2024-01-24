--liquibase formatted sql
--changeset nilima:V20181004171836__AL_tb_challan_master_04102018.sql
ALTER TABLE tb_challan_master 
CHANGE COLUMN BILL_NO REFERENCE_NO VARCHAR(32) NULL DEFAULT NULL COMMENT 'Connection ID/Property Id for bill payment' ;