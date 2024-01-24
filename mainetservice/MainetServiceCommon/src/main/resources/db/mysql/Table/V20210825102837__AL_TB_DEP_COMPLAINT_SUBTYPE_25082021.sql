--liquibase formatted sql
--changeset Kanchan:V20210825102837__AL_TB_DEP_COMPLAINT_SUBTYPE_25082021.sql
alter table TB_DEP_COMPLAINT_SUBTYPE add column  EXTERNAL_FLAG varchar(5) not null default 'N'; 
