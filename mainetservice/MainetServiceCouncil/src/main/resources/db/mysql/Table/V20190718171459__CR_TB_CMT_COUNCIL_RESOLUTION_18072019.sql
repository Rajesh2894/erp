--liquibase formatted sql
--changeset Anil:V20190718171459__CR_TB_CMT_COUNCIL_RESOLUTION_18072019.sql
drop table if exists tb_cmt_sumoto_resolution;
--liquibase formatted sql
--changeset Anil:V20190718171459__CR_TB_CMT_COUNCIL_RESOLUTION_180720191.sql
CREATE TABLE TB_CMT_COUNCIL_RESOLUTION(
  RESO_ID bigint(12) NOT NULL COMMENT 'Primary key',
  RESOLUTION_NO varchar(15) NOT NULL COMMENT 'RESOLUTION_NO',
  MEETING_ID bigint(12) NOT NULL COMMENT 'MEETING_ID',
  PROPOSAL_ID bigint(12) NOT NULL COMMENT 'PROPOSAL_ID',
  RESOLUTION_COMMENTS varchar(500) NOT NULL COMMENT 'RESOLUTION COMMENTS',
  STATUS varchar(30) NOT NULL COMMENT 'status set from prefix',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RESO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
