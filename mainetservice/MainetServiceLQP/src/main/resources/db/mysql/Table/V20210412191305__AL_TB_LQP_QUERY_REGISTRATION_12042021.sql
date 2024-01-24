--liquibase formatted sql
--changeset Kanchan:V20210412191305__AL_TB_LQP_QUERY_REGISTRATION_12042021.sql
alter table  TB_LQP_QUERY_REGISTRATION  modify column QUESTION varchar(500) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210412191305__AL_TB_LQP_QUERY_REGISTRATION_120420211.sql
alter table TB_LQP_QUERY_REGISTRATION_HIST modify column QUESTION varchar(500) NOT NULL;

