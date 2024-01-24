--liquibase formatted sql
--changeset Anil:V20190705101928__CR_tb_cmt_council_meeting_mom_hist_07042019.sql
drop table if exists tb_cmt_council_meeting_mom_hist;
--liquibase formatted sql
--changeset Anil:V20190705101928__CR_tb_cmt_council_meeting_mom_hist_070420191.sql
CREATE TABLE tb_cmt_council_meeting_mom_hist (
  MEETING_MOM_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MEETING_MOM_ID bigint(12) DEFAULT NULL COMMENT 'Primary key',
  MEETING_ID bigint(12) DEFAULT NULL COMMENT 'MEETING_ID',
  PROPOSAL_ID bigint(12) DEFAULT NULL COMMENT 'PROPOSAL_ID',
  MOM_RESOLUTION_COMMENTS varchar(250) DEFAULT NULL COMMENT 'RESOLUTION_REMARKS',
  MOM_STATUS varchar(20) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MEETING_MOM_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
