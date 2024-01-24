--liquibase formatted sql
--changeset priya:V20180201121847__CR_tb_com_help_docs_31012018.sql
CREATE TABLE tb_com_help_docs (
  HELP_DOC_ID bigint(12) NOT NULL DEFAULT '0' COMMENT 'Primary key (Document id)',
  MODULE_NAME varchar(200) NOT NULL COMMENT 'Name of the module for which the help doc is being uploaded',
  FILE_NAME_ENG varchar(100) NOT NULL COMMENT 'Name of the file being uploaded',
  FILE_PATH_ENG varchar(2000) DEFAULT NULL COMMENT 'Path of the file being uploaded',
  FILE_TYPE_ENG varchar(1) NOT NULL COMMENT 'Type of the file being uploaded',
  FILE_NAME_REG varchar(100) DEFAULT NULL COMMENT 'Name of the file being uploaded for regional',
  FILE_PATH_REG varchar(2000) DEFAULT NULL COMMENT 'Path of the file being uploaded for regional',
  FILE_TYPE_REG varchar(1) DEFAULT NULL COMMENT 'Type of the file being uploaded for regional',
  DEPTNAME varchar(50) DEFAULT NULL COMMENT 'dept name',
  ATTACH_BY bigint(12) DEFAULT NULL,
  ATTACH_ON datetime DEFAULT NULL COMMENT 'Date on which the file has been uploaded',
  ISDELETED varchar(1) NOT NULL COMMENT 'Record Deletion flag - value N non-deleted record and Y- deleted record',
  ORGID int(11) NOT NULL COMMENT 'Organization Id.',
  USER_ID int(11) NOT NULL COMMENT 'User Id',
  LANG_ID int(11) NOT NULL COMMENT 'Language Id',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'Modified By',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Modification Date',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine''''s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine''''s Login Name | IP Address | Physical Address',
  PRIMARY KEY (HELP_DOC_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
