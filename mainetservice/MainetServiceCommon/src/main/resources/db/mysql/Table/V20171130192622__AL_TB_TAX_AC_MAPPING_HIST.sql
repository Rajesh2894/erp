--liquibase formatted sql
--changeset nilima:V20171130192622__AL_TB_TAX_AC_MAPPING_HIST.sql
CREATE TABLE TB_TAX_AC_MAPPING_HIST (
  taxb_id_H bigint(20) COMMENT 'primary key',
  taxb_id bigint(20) COMMENT '',
  tax_id bigint(20) COMMENT 'foregin key (TB_TAX_MAS)',
  orgid bigint(20)  COMMENT 'orgnisation id',
  created_by bigint(20)  COMMENT 'user id who created the record',
  created_date datetime  COMMENT 'record creation date',
  updated_by int(11)  COMMENT 'user id who update the data',
  updated_date datetime  COMMENT 'date on which data is going to update',
  SAC_HEAD_ID bigint(15) COMMENT 'foregin key (TB_AC_SECONDARYHEAD_MASTER)',
  taxb_active char(1)  COMMENT 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.',
  lg_ip_mac varchar(100)  COMMENT 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd varchar(100)  COMMENT 'updated client machine?s login name | ip address | physical address',
  H_STATUS CHAR(1)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;