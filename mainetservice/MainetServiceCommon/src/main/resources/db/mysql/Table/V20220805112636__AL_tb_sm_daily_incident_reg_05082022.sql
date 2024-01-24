--liquibase formatted sql
--changeset Kanchan:V20220805112636__AL_tb_sm_daily_incident_reg_05082022.sql
alter table tb_sm_daily_incident_reg modify column INCIDENT_REMARKS varchar(500) not null;