--liquibase formatted sql
--changeset Kanchan:V20220428122147__AL_TB_LGL_CASE_MAS_28042022.sql
alter table TB_LGL_CASE_MAS add column CSE_PHYSICAL_NO varchar(50) DEFAULT NULL;
