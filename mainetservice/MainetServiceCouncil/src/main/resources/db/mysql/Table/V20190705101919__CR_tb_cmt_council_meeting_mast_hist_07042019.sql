--liquibase formatted sql
--changeset Anil:V20190705101919__CR_tb_cmt_council_meeting_mast_hist_07042019.sql
drop table if exists tb_cmt_council_meeting_mast_hist;
--liquibase formatted sql
--changeset Anil:V20190705101919__CR_tb_cmt_council_meeting_mast_hist_070420191.sql
CREATE TABLE tb_cmt_council_meeting_mast_hist (
  MEETING_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MEETING_ID bigint(12) DEFAULT NULL,
  MEETING_NUMBER varchar(15) DEFAULT NULL COMMENT 'MEETING Number (1/12/2017-18 <SerialNo>/<Month>/<FinancialYear>)',
  AGENDA_ID bigint(12) DEFAULT NULL COMMENT 'AGENDA_ID',
  MEETING_TYPE_ID bigint(12) DEFAULT NULL COMMENT 'MEETING_TYPE_ID',
  MEETING_DATE_TIME datetime DEFAULT NULL COMMENT 'MEETING_DATE_TIME',
  MEETING_PLACE varchar(250) DEFAULT NULL,
  MEETING_INVITATION_MSG varchar(250) DEFAULT NULL,
  MEETING_STATUS varchar(250) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MEETING_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
