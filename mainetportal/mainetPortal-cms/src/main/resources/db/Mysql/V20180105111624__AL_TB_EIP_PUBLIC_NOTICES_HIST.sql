--liquibase formatted sql

--changeset jinea:V20180105111624__AL_TB_EIP_PUBLIC_NOTICES_HIST.sql
drop table if exists TB_EIP_PUBLIC_NOTICES_HIST;

--changeset jinea:V20180105111624__AL_TB_EIP_PUBLIC_NOTICES_HIST1.sql
CREATE TABLE TB_EIP_PUBLIC_NOTICES_HIST (
  PN_ID_H bigint(12) DEFAULT NULL,
  PN_ID bigint(12) DEFAULT NULL COMMENT 'Public notice id',
  DEPT_ID bigint(12) DEFAULT NULL COMMENT 'Department Id',
  NOTICE_SUB_EN varchar(250) DEFAULT NULL COMMENT 'Notice subject in english',
  NOTICE_SUB_REG varchar(500) DEFAULT NULL COMMENT 'Notice subject in regional',
  DETAIL_EN varchar(500) DEFAULT NULL COMMENT 'Detail in English',
  DETAIL_REG varchar(1000) DEFAULT NULL COMMENT 'Detail In Regional Language',
  ISSUE_DATE datetime DEFAULT NULL COMMENT 'Issue Date',
  VALIDITY_DATE datetime DEFAULT NULL COMMENT 'Validity Date',
  PUBLISH_FLAG varchar(1) DEFAULT NULL,
  PROFILE_IMG_PATH varchar(1000) DEFAULT NULL COMMENT 'Profile Image path',
  ISDELETED varchar(1) DEFAULT NULL COMMENT ' Flag to identify whether the record is deleted or not. 1 for deleted (inactive) and 0 for not deleted (active) record.',
  ORGID int(11) DEFAULT NULL COMMENT 'Organisation Id',
  USER_ID int(11) DEFAULT NULL COMMENT 'User Id',
  LANG_ID int(11) DEFAULT NULL COMMENT 'Language Id',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  CHEKER_FLAG varchar(1) DEFAULT NULL COMMENT 'Authorisation flag (Y->Authorised,N -> not Authorised)',
  DMS_DOC_ID varchar(100) DEFAULT NULL COMMENT 'DMS Document Id',
  DMS_FOLDER_PATH varchar(100) DEFAULT NULL COMMENT 'DMS Folder Path',
  DMS_DOC_NAME varchar(100) DEFAULT NULL COMMENT 'DMS Document Name',
  DMS_DOC_VERSION varchar(10) DEFAULT NULL COMMENT 'DMS Document Version',
  H_STATUS varchar(1) DEFAULT NULL COMMENT 'Status of the record'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;