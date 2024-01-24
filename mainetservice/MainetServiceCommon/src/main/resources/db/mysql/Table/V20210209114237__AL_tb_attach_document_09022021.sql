--liquibase formatted sql
--changeset Kanchan:V20210209114237__AL_tb_attach_document_09022021.sql
ALTER TABLE tb_attach_document
ADD COLUMN DOC_DESC VARCHAR(510) NULL AFTER DMS_DOC_VERSION;
