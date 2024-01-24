--liquibase formatted sql
--changeset Kanchan:V20221220111858__AL_MM_INDENT_20122022.sql
alter table MM_INDENT modify column expecteddate datetime null default null;