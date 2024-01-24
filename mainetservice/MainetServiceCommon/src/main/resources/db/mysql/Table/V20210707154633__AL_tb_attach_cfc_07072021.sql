--liquibase formatted sql
--changeset Kanchan:V20210707154633__AL_tb_attach_cfc_07072021.sql
alter table tb_attach_cfc add column DOC_DESCRIPTION   varchar(50)   Null;


