--liquibase formatted sql
--changeset nilima:V20181124141116__al_tb_vendormaster_20112018.sql
ALTER TABLE tb_vendormaster
ADD COLUMN VM_CLASS BIGINT(12) NULL COMMENT 'Vendor Class' AFTER VM_GST_NO;
