--liquibase formatted sql
--changeset Kanchan:V20211011101224__AL_tb_document_group_11102021.sql
alter table tb_document_group add column DOC_PREFIX_REQ varchar(3) NULL DEFAULT 'N';
--liquibase formatted sql
--changeset Kanchan:V20211011101224__AL_tb_document_group_111020211.sql
alter table tb_document_group add column PREFIX_NAME varchar(5) NULL DEFAULT NULL;
