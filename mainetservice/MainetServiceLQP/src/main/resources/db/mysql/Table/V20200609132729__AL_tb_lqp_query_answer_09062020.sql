--liquibase formatted sql
--changeset Anil:V20200609132729__AL_tb_lqp_query_answer_09062020.sql
ALTER TABLE tb_lqp_query_answer CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL COMMENT 'id of the user who update' ;
--liquibase formatted sql
--changeset Anil:V20200609132729__AL_tb_lqp_query_answer_090620201.sql
ALTER TABLE tb_lqp_query_answer_hist CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL COMMENT 'id of the user who update' ;
