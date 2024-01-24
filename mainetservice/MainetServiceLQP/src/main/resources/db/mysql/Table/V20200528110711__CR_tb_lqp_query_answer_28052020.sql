--liquibase formatted sql
--changeset Anil:V20200528110711__CR_tb_lqp_query_answer_28052020.sql
drop table if exists tb_lqp_query_answer;
--liquibase formatted sql
--changeset Anil:V20200528110711__CR_tb_lqp_query_answer_280520201.sql
CREATE TABLE tb_lqp_query_answer(
ANSWER_REG_ID bigint(15) NOT NULL  COMMENT 'primary id of the table',
QUESTION_REG_ID bigint(15) NOT NULL  COMMENT 'F.K of tb_lqp_query_registration',
ANSWER_DATE datetime NOT NULL  COMMENT 'date of answer given',
ANSWER varchar(200) NOT NULL  COMMENT 'record answer of question',
REMARK varchar(50) DEFAULT NULL  COMMENT 'additional future field for remark',
ORGID bigint(12) NOT NULL  COMMENT 'id of orgnisation',
CREATED_BY bigint(12) NOT NULL  COMMENT 'id of created by user',
CREATED_DATE datetime NOT NULL  COMMENT 'date and time for create question in DB',
UPDATED_BY bigint(12) NOT NULL  COMMENT 'id of the user who update',
UPDATED_DATE datetime DEFAULT NULL  COMMENT 'date and time for update question in DB',
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
PRIMARY KEY(ANSWER_REG_ID),
KEY QUESTION_REG_ID_IDX (QUESTION_REG_ID),
CONSTRAINT FK_QUESTION_REG_ID FOREIGN KEY (QUESTION_REG_ID) REFERENCES tb_lqp_query_registration (QUESTION_REG_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
--liquibase formatted sql
--changeset Anil:V20200528110711__CR_tb_lqp_query_answer_280520202.sql
drop table if exists tb_lqp_query_answer_hist;
--liquibase formatted sql
--changeset Anil:V20200528110711__CR_tb_lqp_query_answer_280520203.sql
CREATE TABLE tb_lqp_query_answer_hist(
ANSWER_REG_ID_H bigint(15) NOT NULL  COMMENT 'primary id of the table',
ANSWER_REG_ID bigint(15) NOT NULL  COMMENT 'primary id of the answer table',
QUESTION_REG_ID bigint(15) NOT NULL  COMMENT 'F.K of tb_lqp_query_registration',
ANSWER_DATE datetime NOT NULL  COMMENT 'date of the answer given',
ANSWER varchar(200) NOT NULL  COMMENT 'record answer of question',
REMARK varchar(50) DEFAULT NULL  COMMENT 'additional future field for remark',
H_STATUS char(1) NOT NULL  COMMENT 'status for history',
ORGID bigint(12) NOT NULL  COMMENT 'id of orgnisation',
CREATED_BY bigint(12) NOT NULL  COMMENT 'id of created by user',
CREATED_DATE datetime NOT NULL  COMMENT 'date and time for create question in DB',
UPDATED_BY bigint(12) DEFAULT NULL  COMMENT 'id of the user who update',
UPDATED_DATE datetime DEFAULT NULL  COMMENT 'date and time for update question in DB',
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
PRIMARY KEY(ANSWER_REG_ID_H)
);

