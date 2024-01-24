--liquibase formatted sql
--changeset Kanchan:V20211221094512__AL_TB_SWD_SCHEME_DET_21122021.sql
alter table TB_SWD_SCHEME_DET add column BD_YEARID bigint(12)  NULL DEFAULT NULL;
