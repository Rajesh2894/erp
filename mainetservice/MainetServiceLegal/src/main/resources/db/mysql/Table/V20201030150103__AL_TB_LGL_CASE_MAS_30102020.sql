--liquibase formatted sql
--changeset Kanchan:V20201030150103__AL_TB_LGL_CASE_MAS_30102020.sql
alter table TB_LGL_CASE_MAS modify column  CSE_NO varchar(50);
--liquibase formatted sql
--changeset Kanchan:V20201030150103__AL_TB_LGL_CASE_MAS_301020201.sql
alter table TB_LGL_CASE_MAS_HIST modify column  CSE_NO varchar(50);
