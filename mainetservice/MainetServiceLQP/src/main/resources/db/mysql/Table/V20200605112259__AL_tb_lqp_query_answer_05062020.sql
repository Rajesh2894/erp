--liquibase formatted sql
--changeset Anil:V20200605112259__AL_tb_lqp_query_answer_05062020.sql
ALTER TABLE tb_lqp_query_answer 
CHANGE COLUMN ANSWER ANSWER VARCHAR(500) NOT NULL COMMENT 'record answer of question' ,
CHANGE COLUMN REMARK REMARK VARCHAR(100) NULL DEFAULT NULL COMMENT 'additional future field for remark' ;
--liquibase formatted sql
--changeset Anil:V20200605112259__AL_tb_lqp_query_answer_050620201.sql
ALTER TABLE tb_lqp_query_answer_hist 
CHANGE COLUMN ANSWER ANSWER VARCHAR(500) NOT NULL COMMENT 'record answer of question' ,
CHANGE COLUMN REMARK REMARK VARCHAR(100) NULL DEFAULT NULL COMMENT 'additional future field for remark' ;
