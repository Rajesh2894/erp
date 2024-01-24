--liquibase formatted sql
--changeset nilima:V20190328171134__AL_tb_cfc_application_mst_27032019.sql
ALTER TABLE tb_cfc_application_mst
CHANGE COLUMN REF_NO REF_NO VARCHAR(100);

