--liquibase formatted sql
--changeset Kanchan:V20210429174923__AL_TB_CSMR_INFO_29042021.sql
alter table  TB_CSMR_INFO add column cs_no_of_flts  int(5),add cs_no_of_fmls int(5),add cs_no_of_mmbrs int(5) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210429174923__AL_TB_CSMR_INFO_290420211.sql
alter table  TB_CSMR_INFO_HIST add column cs_no_of_flts  int(5),add cs_no_of_fmls int(5),add cs_no_of_mmbrs int(5) NULL;
