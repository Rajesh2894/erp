--liquibase formatted sql
--changeset PramodPatil:V20230727192117__AL_TB_CONTRACT_MAST_HIST_27072023.sql
alter table TB_CONTRACT_MAST_HIST add column REMARK varchar(1000) null default null;