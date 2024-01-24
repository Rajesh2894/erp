--liquibase formatted sql
--changeset Anil:V20200609133018__AL_tb_lqp_query_registration_09062020.sql
ALTER TABLE tb_lqp_query_registration ADD COLUMN QUESTION_ID_WF VARCHAR(20) NOT NULL;
--liquibase formatted sql
--changeset Anil:V20200609133018__AL_tb_lqp_query_registration_090620201.sql
ALTER TABLE tb_lqp_query_registration_hist ADD COLUMN QUESTION_ID_WF VARCHAR(20) NOT NULL;
