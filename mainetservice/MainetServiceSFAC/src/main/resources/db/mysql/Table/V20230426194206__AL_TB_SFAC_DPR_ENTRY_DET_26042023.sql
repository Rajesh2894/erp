--liquibase formatted sql
--changeset Kanchan:V20230426194206__AL_TB_SFAC_DPR_ENTRY_DET_26042023.sql
alter table TB_SFAC_DPR_ENTRY_DET  modify column DPR_SEC varchar(200) null default null;