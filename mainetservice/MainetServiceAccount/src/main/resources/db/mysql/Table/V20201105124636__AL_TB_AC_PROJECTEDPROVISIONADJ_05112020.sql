--liquibase formatted sql
--changeset Kanchan:V20201105124636__AL_TB_AC_PROJECTEDPROVISIONADJ_05112020.sql
alter table TB_AC_PROJECTEDPROVISIONADJ add FIELD_ID BIGINT(12) NOT NULL;
