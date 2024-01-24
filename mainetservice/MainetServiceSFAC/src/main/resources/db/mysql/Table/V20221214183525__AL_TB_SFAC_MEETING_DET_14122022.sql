--liquibase formatted sql
--changeset Kanchan:V20221214183525__AL_TB_SFAC_MEETING_DET_14122022.sql
Alter table TB_SFAC_MEETING_DET add column STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20221214183525__AL_TB_SFAC_MEETING_DET_141220221.sql
Alter table TB_SFAC_MEETING_MOM add column STATUS char(1) Null default null;