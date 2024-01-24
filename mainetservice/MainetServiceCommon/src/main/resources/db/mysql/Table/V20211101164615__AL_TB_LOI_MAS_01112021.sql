--liquibase formatted sql
--changeset Kanchan:V20211101164615__AL_TB_LOI_MAS_01112021.sql
alter table TB_LOI_MAS add column PROP_DUE_AMOUNT decimal(20,2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20211101164615__AL_TB_LOI_MAS_011120211.sql
alter table TB_LOI_MAS_HIST add column PROP_DUE_AMOUNT decimal(20,2) null default null;
