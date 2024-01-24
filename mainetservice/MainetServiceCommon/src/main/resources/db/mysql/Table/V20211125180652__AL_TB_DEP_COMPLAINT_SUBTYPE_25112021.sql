--liquibase formatted sql
--changeset Kanchan:V20211125180652__AL_TB_DEP_COMPLAINT_SUBTYPE_25112021.sql
alter table TB_DEP_COMPLAINT_SUBTYPE modify column EXTERNAL_FLAG varchar(5) NULL DEFAULT NULL;
