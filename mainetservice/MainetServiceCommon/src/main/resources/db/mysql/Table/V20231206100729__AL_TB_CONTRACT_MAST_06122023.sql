--liquibase formatted sql
--changeset PramodPatil:V20231206100729__AL_TB_CONTRACT_MAST_06122023.sql
alter table TB_CONTRACT_MAST add column REASON varchar(1000) null default null;
