--liquibase formatted sql
--changeset Kanchan:V20221216185059__AL_employee_16122022.sql
Alter table employee add column MAS_ID bigint(20) Null default null;