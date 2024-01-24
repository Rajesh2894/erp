--liquibase formatted sql
--changeset Kanchan:V20230109095356__AL_TB_LGL_COURT_MAST_09012023.sql
ALTER TABLE TB_LGL_COURT_MAST ADD column CSE_TYP_ID varchar(100) null default null;