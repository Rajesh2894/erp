--liquibase formatted sql
--changeset Kanchan:V20210707154456__AL_tb_attach_document_07072021.sql
alter table tb_attach_document  add column DOC_DESCRIPTION   varchar(50)   Null;
