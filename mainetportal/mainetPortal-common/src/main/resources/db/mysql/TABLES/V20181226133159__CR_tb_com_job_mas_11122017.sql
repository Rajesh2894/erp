--liquibase formatted sql
--changeset priya:V20181226133159__CR_tb_com_job_mas_11122017.sql
drop table if exists tb_com_job_mas;
--liquibase formatted sql
--changeset priya:V20181226133159__CR_tb_com_job_mas_111220171.sql
CREATE TABLE tb_com_job_mas (
  CJ_ID bigint(12) NOT NULL DEFAULT '0',
  DP_DEPTID bigint(12) DEFAULT NULL,
  CJ_NO varchar(100) DEFAULT NULL COMMENT 'job number',
  CJ_DESC varchar(500) DEFAULT NULL COMMENT 'Procedure and function to be excuted.',
  CJ_NXDT datetime DEFAULT NULL COMMENT 'Next date',
  CJ_INTERVAL varchar(500) DEFAULT NULL COMMENT 'interval',
  STATUS varchar(1) DEFAULT NULL COMMENT 'status',
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Modification By',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Modification Date',
  CPD_ID_BJO bigint(12) DEFAULT NULL,
  CPD_ID_BFR bigint(12) DEFAULT NULL,
  CJ_REPEAT varchar(1) DEFAULT NULL,
  CJ_CLASSNAME varchar(1000) DEFAULT NULL,
  CJ_FUNNAME varchar(100) DEFAULT NULL,
  CJ_DATE datetime DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (CJ_ID),
  KEY FK_COM_DEPT_ID (DP_DEPTID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;