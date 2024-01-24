--liquibase formatted sql
--changeset Kanchan:V20230313104611__AL_TB_SFAC_EMPLOYEE_TEMP_13032023.sql
Alter table TB_SFAC_EMPLOYEE_TEMP add column APPLICATION_ID bigint(20) Null default null;