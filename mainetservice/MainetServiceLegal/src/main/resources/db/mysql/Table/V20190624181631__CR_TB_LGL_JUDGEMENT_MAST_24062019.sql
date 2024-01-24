--liquibase formatted sql
--changeset Anil:V20190624181631__CR_TB_LGL_JUDGEMENT_MAST_24062019.sql
CREATE TABLE TB_LGL_JUDGEMENT_MAST (
  JUD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  CSE_ID bigint(12) NOT NULL COMMENT 'foregin key tb_lgl_case_mas',
  CRT_ID bigint(12) NOT NULL COMMENT 'foregin key tb_lgl_court_mast',
  JUD_CASE_DATE date NOT NULL COMMENT 'Case Date',
  JUD_DATE date NOT NULL COMMENT 'Judgement Date',
  JUD_SUMMARY_DETAIL Varchar(1000) NOT NULL COMMENT 'Judgement Summary Detail',
  JUD_BENCH_NAME Varchar(500) NOT NULL COMMENT 'Judgement Bench Name',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUD_ID),
  KEY FK_CSEID_idx (CSE_ID),
  KEY FK_CRTID_idx (CRT_ID),
  CONSTRAINT FK_CSEID FOREIGN KEY (CSE_ID) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_CRTID FOREIGN KEY (CRT_ID) REFERENCES tb_lgl_court_mast (CRT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
