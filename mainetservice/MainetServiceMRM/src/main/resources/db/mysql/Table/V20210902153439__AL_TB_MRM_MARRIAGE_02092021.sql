--liquibase formatted sql
--changeset Kanchan:V20210902153439__AL_TB_MRM_MARRIAGE_02092021.sql
alter table TB_MRM_MARRIAGE add column APPLICANT_TYPE varchar(10) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210902153439__AL_TB_MRM_MARRIAGE_020920211.sql
alter table TB_MRM_MARRIAGE_HIST   add column APPLICANT_TYPE varchar(10) NULL;
