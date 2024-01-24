--liquibase formatted sql
--changeset Kanchan:V20210208200258__AL_TB_DEP_COMPLAINT_SUBTYPE_08022021.sql
alter table TB_DEP_COMPLAINT_SUBTYPE add column DOCUMENT_REQ varchar(5) NULL;
