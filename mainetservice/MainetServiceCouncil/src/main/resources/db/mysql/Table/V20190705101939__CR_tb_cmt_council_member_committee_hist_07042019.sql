--liquibase formatted sql
--changeset Anil:V20190705101939__CR_tb_cmt_council_member_committee_hist_07042019.sql
drop table if exists tb_cmt_council_member_committee_hist;
--liquibase formatted sq l
--changeset Anil:V20190705101939__CR_tb_cmt_council_member_committee_hist_070420191.sql
CREATE TABLE tb_cmt_council_member_committee_hist (
  MEMBER_COMMITTEE_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MEMBER_COMMITTEE_ID bigint(12) NOT NULL,
  COU_ID bigint(12) DEFAULT NULL COMMENT 'Member Id',
  COMMITTEE_TYPE_ID bigint(12) DEFAULT NULL COMMENT 'Committee Type (Prefix)',
  DISSOLVE_DATE datetime DEFAULT NULL COMMENT 'DISSOLVE_DATE',
  FROM_DATE datetime DEFAULT NULL COMMENT 'FROM_DATE',
  TO_DATE datetime DEFAULT NULL COMMENT 'TO_DATE',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  STATUS char(20) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (MEMBER_COMMITTEE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
