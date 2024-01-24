--liquibase formatted sql
--changeset Kanchan:V20201005155033__AL_TB_LGL_CASE_MAS_05102020.sql
alter table TB_LGL_CASE_MAS add column CSE_NO varchar(20) NOT NULL;


