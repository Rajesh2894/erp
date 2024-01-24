--liquibase formatted sql
--changeset Anil:V20190704161833__CR_tb_lgl_judgement_mast_hist_07042019.sql
drop table if exists tb_lgl_judgement_mast_hist;
--liquibase formatted sql
--changeset Anil:V20190704161833__CR_tb_lgl_judgement_mast_hist_070420191.sql
CREATE TABLE tb_lgl_judgement_mast_hist (
  JUD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  JUD_ID bigint(12) NOT NULL,
  CSE_ID bigint(12) NOT NULL COMMENT 'Case ID',
  CRT_ID bigint(12) NOT NULL COMMENT 'Court Detail',
  H_STATUS char(1) NOT NULL COMMENT 'Status I->Insert,update',
  JUD_CASE_DATE date NOT NULL COMMENT 'Case Date',
  JUD_DATE date NOT NULL COMMENT 'Judgement Date',
  JUD_SUMMARY_DETAIL varchar(1000) NOT NULL COMMENT 'Judgement Summary Detail',
  JUD_BENCH_NAME varchar(500) NOT NULL COMMENT 'Judgement Bench Name',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

