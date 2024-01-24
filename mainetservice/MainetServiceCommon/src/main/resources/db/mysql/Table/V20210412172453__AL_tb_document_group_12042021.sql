--liquibase formatted sql
--changeset Kanchan:V20210412172453__AL_tb_document_group_12042021.sql
alter table tb_document_group add column doc_name_reg varchar(100),add doc_type_reg varchar(20) NULL;
