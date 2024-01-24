--liquibase formatted sql
--changeset Anil:V20190624163839__CR_tb_lgl_casejudgement_detail_24062019.sql
drop table if exists tb_lgl_casejudgement_detail;
--liquibase formatted sql
--changeset Anil:V20190624163839__CR_tb_lgl_casejudgement_detail_240620191.sql
CREATE TABLE tb_lgl_casejudgement_detail (
  cjd_id bigint(12) NOT NULL COMMENT 'Primary Key',
  cse_id bigint(12) NOT NULL COMMENT 'Case Id',
  cjd_date date DEFAULT NULL COMMENT 'Judgement Detail',
  cjd_type bigint(12) DEFAULT NULL COMMENT 'Judgement Type',
  cjd_details varchar(1000) DEFAULT NULL COMMENT 'judegement Details',
  cjd_attendee varchar(50) DEFAULT NULL COMMENT 'judegement Implementation attendy',
  cjd_actiontaken varchar(50) DEFAULT NULL COMMENT 'judegement Implementation action',
  NAME_OF_IMPLEMENTER varchar(50) DEFAULT NULL,
  PHONE_NO bigint(12) DEFAULT NULL,
  EMAIL_ID varchar(30) DEFAULT NULL,
  IMPLEMENTATION_START_DATE date DEFAULT NULL,
  IMPLEMENTATION_END_DATE date DEFAULT NULL,
  DESIG_OF_IMPLEMENTER varchar(50) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  IMPLE_STATUS varchar(100) DEFAULT NULL,
  PRIMARY KEY (cjd_id),
  KEY FK_CJDCSEID_idx (cse_id),
  CONSTRAINT FK_CJDCSEID FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

