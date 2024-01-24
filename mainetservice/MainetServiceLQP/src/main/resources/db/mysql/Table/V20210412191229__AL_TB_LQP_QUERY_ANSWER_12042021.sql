--liquibase formatted sql
--changeset Kanchan:V20210412191229__AL_TB_LQP_QUERY_ANSWER_12042021.sql
alter table TB_LQP_QUERY_ANSWER  modify column ANSWER varchar(3000) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210412191229__AL_TB_LQP_QUERY_ANSWER_120420211.sql
alter table TB_LQP_QUERY_ANSWER_HIST  modify column ANSWER varchar(3000) NOT NULL;
