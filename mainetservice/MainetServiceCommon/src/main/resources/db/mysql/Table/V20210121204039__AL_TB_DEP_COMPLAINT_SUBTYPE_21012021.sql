--liquibase formatted sql
--changeset Kanchan:V20210121204039__AL_TB_DEP_COMPLAINT_SUBTYPE_21012021.sql
alter table TB_DEP_COMPLAINT_SUBTYPE add column RESIDENT_ID varchar(20),add AMT_DUES varchar(5)null;
