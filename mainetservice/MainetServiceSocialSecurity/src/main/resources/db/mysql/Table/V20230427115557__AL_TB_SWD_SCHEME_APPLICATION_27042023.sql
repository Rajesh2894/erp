--liquibase formatted sql
--changeset Kanchan:V20230427115557__AL_TB_SWD_SCHEME_APPLICATION_27042023.sql
alter table TB_SWD_SCHEME_APPLICATION add column EMAIL varchar(50) null default null;