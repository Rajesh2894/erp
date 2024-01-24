--liquibase formatted sql
--changeset Kanchan:V20220324180425__AL_employee_hist_24032022.sql
alter table employee_hist add column PREV_TTL_ID bigint(20) DEFAULT NULL;
