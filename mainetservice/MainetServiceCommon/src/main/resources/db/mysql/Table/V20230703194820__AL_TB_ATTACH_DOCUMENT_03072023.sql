--liquibase formatted sql
--changeset Kanchan:V20230703194820__AL_TB_ATTACH_DOCUMENT_03072023.sql
alter table TB_ATTACH_DOCUMENT modify column DMS_DOC_NAME varchar(500) null default null;