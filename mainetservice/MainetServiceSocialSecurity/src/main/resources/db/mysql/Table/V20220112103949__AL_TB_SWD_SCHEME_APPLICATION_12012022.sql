--liquibase formatted sql
--changeset Kanchan:V20220112103949__AL_TB_SWD_SCHEME_APPLICATION_12012022.sql
alter table TB_SWD_SCHEME_APPLICATION add column SDSCH_SUB_SER_ID bigint(12) DEFAULT NULL;
