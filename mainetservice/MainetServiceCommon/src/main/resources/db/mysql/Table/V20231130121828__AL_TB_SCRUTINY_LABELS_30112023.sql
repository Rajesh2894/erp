--liquibase formatted sql
--changeset PramodPatil:V20231130121828__AL_TB_SCRUTINY_LABELS_30112023.sql
alter table TB_SCRUTINY_LABELS add column SL_QUERY varchar(500) null default null;
