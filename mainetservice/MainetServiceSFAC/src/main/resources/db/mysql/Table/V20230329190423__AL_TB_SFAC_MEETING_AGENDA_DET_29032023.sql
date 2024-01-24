--liquibase formatted sql
--changeset Kanchan:V20230329190423__AL_TB_SFAC_MEETING_AGENDA_DET_29032023.sql
Alter table TB_SFAC_MEETING_AGENDA_DET add column STATUS char(1) Null default null;