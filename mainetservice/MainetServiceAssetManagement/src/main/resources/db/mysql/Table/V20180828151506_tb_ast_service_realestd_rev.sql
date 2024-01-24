--liquibase formatted sql
--changeset shamik:V20180828151506_tb_ast_service_realestd_rev1.sql
ALTER TABLE tb_ast_service_realestd_rev change COLUMN ASSET_SERVICE_ID ASSET_SERVICE_ID bigint(12) DEFAULT NULL COMMENT ' Primary Key   ';