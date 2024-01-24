--liquibase formatted sql
--changeset PramodPatil: V20230725181331__AL_TB_CONTRACT_MAST_25072023.sql 
alter table TB_CONTRACT_MAST add column REASON varchar(1000) null default null;
