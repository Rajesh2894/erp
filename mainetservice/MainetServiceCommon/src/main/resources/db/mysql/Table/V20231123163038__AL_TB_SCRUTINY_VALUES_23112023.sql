--liquibase formatted sql
--changeset PramodPatil:V20231123163038__AL_TB_SCRUTINY_VALUES_23112023.sql
alter table TB_SCRUTINY_VALUES add column REMARK varchar(500) null default null;
