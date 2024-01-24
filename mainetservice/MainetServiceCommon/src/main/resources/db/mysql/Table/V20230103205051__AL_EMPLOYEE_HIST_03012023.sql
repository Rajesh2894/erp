--liquibase formatted sql
--changeset Kanchan:V20230103205051__AL_EMPLOYEE_HIST_03012023.sql
Alter table EMPLOYEE_HIST add column MAS_ID bigint(20) NULL DEFAULT NULL;