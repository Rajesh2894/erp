--liquibase formatted sql
--changeset Kanchan:V20201113174949__AL_TB_MRM_MARRIAGE_13112020.sql

alter table TB_MRM_MARRIAGE change APPLICATION_NO  APPLICATION_ID bigint(15) NULL;

--liquibase formatted sql
--changeset Kanchan:V20201113174949__AL_TB_MRM_MARRIAGE_131120201.sql
alter table TB_MRM_MARRIAGE_HIST change APPLICATION_NO  APPLICATION_ID bigint(15) NULL;
