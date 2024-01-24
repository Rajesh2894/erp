--liquibase formatted sql
--changeset Anil:V20190817123316__CR_tb_lgl_case_counter_aff_17082019.sql
drop table if exists tb_lgl_case_counter_aff;
--liquibase formatted sql
--changeset Anil:V20190817123316__CR_tb_lgl_case_counter_aff_170820191.sql
create table tb_lgl_case_counter_aff(
CAF_ID bigint(12) NOT NULL COMMENT 'Primary Key',
CSE_ID bigint(12) NOT NULL COMMENT 'foregin key tb_lgl_case_mas',
CAF_DATE date NOT NULL COMMENT 'Affidevite Date',
CAF_TYPE Varchar(200) NOT NULL COMMENT 'Counter Affidevite Time',
CAF_COUDATE date NOT NULL COMMENT 'Counter Affidevite Date',
CAF_DEFNAME varchar(250) NOT NULL COMMENT 'Deffender Name',
CAF_RESNAME varchar(250) NOT NULL COMMENT 'Responder Name',
ORGID bigint(12) NOT NULL COMMENT 'organization id',
CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
PRIMARY KEY(CAF_ID),
KEY FK_CAFCSEID_idx(CSE_ID),
CONSTRAINT FK_CAFCSEID FOREIGN KEY(CSE_ID) REFERENCES tb_lgl_case_mas(cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
