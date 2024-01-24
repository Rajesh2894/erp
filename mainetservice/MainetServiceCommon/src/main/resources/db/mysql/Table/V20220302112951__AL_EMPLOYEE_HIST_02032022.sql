--liquibase formatted sql
--changeset Kanchan:V20220302112951__AL_EMPLOYEE_HIST_02032022.sql
alter table EMPLOYEE_HIST add column
PREV_EMPNAME varchar(1000),
add PREV_EMPMNAME varchar(200),
add PREV_EMPLNAME varchar(200) NULL DEFAULT NULL;

