--liquibase formatted sql
--changeset Kanchan:V20230329190034__AL_TB_SFAC_MEETING_DET_29032023.sql
Alter table TB_SFAC_MEETING_DET add column ORGANIZATION varchar(200) Null default null;