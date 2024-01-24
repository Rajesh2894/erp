--liquibase formatted sql
--changeset nilima:V20180712184001__AL_TB_OBJECTION_MAST1.sql
CREATE TABLE TB_ADVANCE_REQ (
  adv_id bigint(12) NOT NULL COMMENT 'primary key',
  adv_no varchar(50) NOT NULL COMMENT 'Payment Advance no',
  adv_entrydate date NOT NULL COMMENT 'Date on which Advance entry is made',
  adv_type bigint(12) NOT NULL COMMENT 'advance type (ATY->prefix)',
  dp_deptid bigint(12) DEFAULT NULL COMMENT 'Department Id',
  vm_vendorid bigint(12) NOT NULL COMMENT 'Vendor id',
  ah_headid bigint(12) DEFAULT NULL COMMENT 'Head Id',
  ah_headdesc varchar(50) DEFAULT NULL,
  adv_ref_no varchar(45) DEFAULT NULL COMMENT 'advance Reference no',
  adv_amount decimal(12,2) NOT NULL COMMENT 'Advance Amount',
  adv_particulars varchar(200) NOT NULL COMMENT 'Advance Particulare',
  adv_billno varchar(50) DEFAULT NULL COMMENT 'Billi No for advance',
  adv_billdate date DEFAULT NULL COMMENT 'Billi date for advance',
  adv_status char(1) DEFAULT NULL COMMENT 'Advance Status',
  orgid bigint(12) NOT NULL COMMENT 'orgid',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'client machine''s login name | ip address | physical address',
  updated_by bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  updated_date datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'client machine?s login name | ip address | physical address',
  PRIMARY KEY (adv_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

