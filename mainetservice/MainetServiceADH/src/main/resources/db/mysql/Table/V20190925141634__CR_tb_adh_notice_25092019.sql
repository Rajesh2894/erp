--liquibase formatted sql
--changeset Anil:V20190925141634__CR_tb_adh_notice_25092019.sql
drop table if exists tb_adh_notice;
--liquibase formatted sql
--changeset Anil:V20190925141634__CR_tb_adh_notice_250920191.sql
CREATE TABLE tb_adh_notice(
  notice_id bigint(12) NOT NULL COMMENT 'Primary key',
  notice_no varchar(20) NOT NULL COMMENT 'notice number',
  lic_id varchar(40) NOT NULL COMMENT 'lic id',
  remarks varchar(200) NOT NULL COMMENT 'remarks',
  notice_type varchar(40) NOT NULL COMMENT 'notice type',
  orgid bigint(12) NOT NULL COMMENT 'Organization id',
  created_date bigint(12) NOT NULL COMMENT 'last modification date',
  created_by datetime NOT NULL COMMENT 'User Identity',
  lg_ip_mac varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
  PRIMARY KEY(notice_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
