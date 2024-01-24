--liquibase formatted sql
--changeset Kanchan:V20201005155125__AL_TB_LGL_CASE_MAS_HIST_05102020.sql
alter table TB_LGL_CASE_MAS_HIST add column CSE_NO varchar(20) NOT NULL;
