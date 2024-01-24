--liquibase formatted sql
--changeset Kanchan:V20230220173423__AL_TB_SFAC_MEETING_DET_20022023.sql
alter table TB_SFAC_MEETING_DET add column DESIGNATION Varchar(200) Null default null;