--liquibase formatted sql
--changeset Kanchan:V20220304132045__AL_TB_APPREJ_MAS_04032022.sql
Alter table TB_APPREJ_MAS  add column   CATEGORY_ID bigint(12) DEFAULT NULL;
