--liquibase formatted sql
--changeset Kanchan:V20230105194122__AL_TB_ADT_POSTADT_MAS_05012023.sql
alter table TB_ADT_POSTADT_MAS add column auditAppendix mediumtext NULL default NULL;
