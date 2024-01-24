--liquibase formatted sql
--changeset Anil:V20190709150853__CR_tb_cmt_council_meeting_member_hist_09072019.sql
drop table if exists tb_cmt_council_meeting_member_hist;
--liquibase formatted sql
--changeset Anil:V20190709150853__CR_tb_cmt_council_meeting_member_hist_090720191.sql
CREATE TABLE tb_cmt_council_meeting_member_hist (
  MEETING_MEMBER_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MEETING_MEMBER_ID bigint(12) NOT NULL COMMENT 'MEETING__MEMBER_ID',
  MEETING_ID bigint(12) NOT NULL COMMENT 'MEETING_ID',
  COU_ID bigint(12) NOT NULL COMMENT 'COU_ID',
  ATTENDANCE_STATUS char(1) NOT NULL DEFAULT '0' COMMENT '0->Absent,1->Present',
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MEETING_MEMBER_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
