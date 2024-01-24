--liquibase formatted sql
--changeset Anil:V20200710172927__AL_tb_lqp_query_registration_10072020.sql
ALTER TABLE tb_lqp_query_registration CHANGE COLUMN QUESTION_ID_WF QUESTION_ID_WF VARCHAR(50) NOT NULL ;
--liquibase formatted sql
--changeset Anil:V20200710172927__AL_tb_lqp_query_registration_100720201.sql
ALTER TABLE tb_lqp_query_registration_hist CHANGE COLUMN QUESTION_ID_WF QUESTION_ID_WF VARCHAR(50) NOT NULL ;

