--liquibase formatted sql
--changeset Kanchan:V20230222115337__AL_TB_VENDORMASTER_22022023.sql
alter table TB_VENDORMASTER add column MSME_NO varchar(50) Null default null;