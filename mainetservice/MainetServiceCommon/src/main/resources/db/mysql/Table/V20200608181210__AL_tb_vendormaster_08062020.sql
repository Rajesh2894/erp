--liquibase formatted sql
--changeset Anil:V20200608181210__AL_tb_vendormaster_08062020.sql
ALTER TABLE tb_vendormaster ADD COLUMN PFMS_VendorID VARCHAR(20) NULL;
--liquibase formatted sql
--changeset Anil:V20200608181210__AL_tb_vendormaster_080620201.sql
ALTER TABLE tb_vendormaster_hist ADD COLUMN PFMS_VendorID VARCHAR(20) NULL;
