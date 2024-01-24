--liquibase formatted sql
--changeset Kanchan:V20211005123005__AL_TB_MRM_MARRIAGE_05102021.sql
alter table TB_MRM_MARRIAGE add column ACTION_STATUS varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20211005123005__AL_TB_MRM_MARRIAGE_051020211.sql
alter table TB_MRM_MARRIAGE_HIST add column ACTION_STATUS varchar(20) NULL;
