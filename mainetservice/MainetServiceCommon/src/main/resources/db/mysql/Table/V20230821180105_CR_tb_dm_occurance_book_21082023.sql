--liquibase formatted sql
--changeset PramodPatil:V20230821180105_CR_tb_dm_occurance_book_21082023.sql
CREATE TABLE tb_dm_occurance_book (
  occ_id bigint(12) NOT NULL,
  date datetime NOT NULL,
  cmplnt_no varchar(20) DEFAULT NULL,
  time time DEFAULT NULL,
  incident_desc varchar(500) DEFAULT NULL,
  operator_remarks varchar(300) DEFAULT NULL,
  orgid bigint(12) DEFAULT NULL,
  created_by bigint(12) DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  updated_by bigint(12) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  lg_ip_mac varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (occ_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;