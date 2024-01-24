--liquibase formatted sql
--changeset Kanchan:V20211215180102__AL_TB_LGL_LEGAL_OPINION_15122021.sql
alter table TB_LGL_LEGAL_OPINION modify column LOC_ID bigint(12) DEFAULT NULL;
