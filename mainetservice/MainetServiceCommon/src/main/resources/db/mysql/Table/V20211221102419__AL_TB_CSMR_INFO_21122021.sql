--liquibase formatted sql
--changeset Kanchan:V20211221102419__AL_TB_CSMR_INFO_21122021.sql
alter table TB_CSMR_INFO add column CS_ID bigint(12) NULL DEFAULT NULL;
