--liquibase formatted sql
--changeset Kanchan:V20230414195818__AL_TB_SWD_SCHEME_APPLICATION_14042023.sql
alter table TB_SWD_SCHEME_APPLICATION add column STATUS char(1) null default null;